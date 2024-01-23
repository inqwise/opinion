package com.inqwise.opinion.api.context;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.json.JSONObject;

import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.http.HttpClientSessionUserArgs;
import com.inqwise.opinion.infrastructure.common.ICookie;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.cryptography.StringEncrypter;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class ClientSessionDescryptor2 {
	static ApplicationLog logger = ApplicationLog
			.getLogger(ClientSessionDescryptor2.class);
	
	private static final String SESSION_ID_COOKIE_KEY = "sid";
	private static final String UNIQUE_ID_COOKIE_KEY = "uid";
	protected static final String passPhrase = "3b4b9507";
	
	public static HttpClientSession getSession(Cookies cookies, String clientIp) throws UnsupportedEncodingException {
		
		String sessionId = null;
		String encryptedUserArgs = null;
		
		ICookie sessionIdCookie = cookies.firstOrDefault(SESSION_ID_COOKIE_KEY);
		ICookie encryptedUserArgsCookie = cookies.firstOrDefault(UNIQUE_ID_COOKIE_KEY);
		StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
		
		if(null != sessionIdCookie){
			sessionId = URLDecoder.decode(sessionIdCookie.getValue(), StandardCharsets.UTF_8.displayName());
		}
		
		if(null != encryptedUserArgsCookie){
			encryptedUserArgs = URLDecoder.decode(encryptedUserArgsCookie.getValue(), StandardCharsets.UTF_8.displayName());
		}
		
		try {
			String decryptedUserArgsJsonStr = (null == encryptedUserArgs) ? null : desEncrypter.decrypt(encryptedUserArgs.replaceAll(" ", "+"));
			if(null == decryptedUserArgsJsonStr) {
				return new HttpClientSession(ErrorCode.NotSignedIn, "Not signed in");
			} else {
				// Deserialize
				var userArgs = new HttpClientSessionUserArgs(new JSONObject(decryptedUserArgsJsonStr));
				
				return new HttpClientSession( sessionId, userArgs.getUserId(),
						clientIp, userArgs.getProductId(), userArgs.getClientIp());
			}
			
		} catch (Exception t) {
			UUID errorId = logger.error(t, "getSession() : Unexpected error occured.");
			return new HttpClientSession(errorId, t.toString());
		}
	}

	private static void addToSession(String key, String value, Boolean encripted,
			boolean isPersist, Cookies cookies) throws UnsupportedEncodingException {
		
		int cookieExpire = isPersist ? (60 * 60 * 24) * 30  : -1; // 30 days 
		if(encripted){
			StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
			value = desEncrypter.encrypt(value);
		}
		
		if(null == ApplicationConfiguration.Opinion.getSessionDomain()){
			cookies.put(key, value, cookieExpire);
		} else {
			cookies.put(key, value, cookieExpire, ApplicationConfiguration.Opinion.getSessionDomain());
		}
	}
	
	public static void addUserIdToSession(long userId, int productId, String clientIp, boolean untilEndSession, Cookies cookies) throws IOException{
		
		var userArgs = HttpClientSessionUserArgs.builder()
				.withClientIp(clientIp)
				.withProductId(productId)
				.withUserId(userId).build();
		
		 // Serialize
		addToSession(UNIQUE_ID_COOKIE_KEY, userArgs.toJson().toString(), false, untilEndSession, cookies);
	}
	
	public static void addSessionIdToSession(UUID sessionId, boolean isPersist, Cookies cookies) throws UnsupportedEncodingException{
		addToSession(SESSION_ID_COOKIE_KEY, sessionId.toString(), false, isPersist, cookies);
	}
	
	
}
