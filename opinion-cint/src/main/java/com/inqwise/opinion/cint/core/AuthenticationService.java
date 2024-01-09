package com.inqwise.opinion.cint.core;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class AuthenticationService {
	private static final ApplicationLog Logger = ApplicationLog.getLogger(AuthenticationService.class);
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final String AUTHORIZATION_SIGNATURE_KEY = "Authorization";
	private Map<String, String> headers = new HashMap<String, String>();
	private String authString = Constants.EMPTY_STRING;
	
	public Map<String, String> getCintHeaders(String apiToken, String tokenSecret,
			String url, String messageBody) throws UnsupportedEncodingException, GeneralSecurityException {
		
		authString = "CDS " + generateAuthString(url, messageBody, apiToken, tokenSecret);
		if(Logger.IsDebugEnabled()){
			Logger.debug("Signature: '%s'", authString);
		}
		headers.put(AUTHORIZATION_SIGNATURE_KEY, authString);
		
		return headers;

	}

	private static String calculateRFC2104HMAC(String data, String key) throws GeneralSecurityException {
		byte[] hmacData = null;
		
		try {

			// Get an hmac_sha1 key from the raw key bytes
			byte[] keyBytes = key.getBytes("UTF-8");			
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1_ALGORITHM);
			 
			// Get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			hmacData = mac.doFinal(data.getBytes("UTF-8")); 
	        return Base64.getEncoder().encodeToString(hmacData); 
		} catch (UnsupportedEncodingException e) {  
		        throw new GeneralSecurityException(e); 
		} 
	} 
	
	private static String generateAuthString(String requestUri,
					String messageBody, String apiKey, String secret) throws GeneralSecurityException, UnsupportedEncodingException
	{
		String signatureBaseString =
				String.format("%s %s", requestUri, messageBody); //#1
		String signature = calculateRFC2104HMAC(signatureBaseString, secret);
		return Base64.getEncoder().encodeToString(String.format("%s:%s", apiKey, signature).getBytes("UTF-8")); //#2
	}
}

