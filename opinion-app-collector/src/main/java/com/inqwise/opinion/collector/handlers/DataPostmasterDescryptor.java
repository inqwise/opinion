package com.inqwise.opinion.collector.handlers;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.msgpack.MessagePack;

import com.inqwise.opinion.common.ICollectorPostmasterContext;
import com.inqwise.opinion.common.IHttpAnswererSession;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.facade.collector.Entry;
import com.inqwise.opinion.facade.collector.OpinionsEntry;
import com.inqwise.opinion.facade.collector.ResponsesEntry;
import com.inqwise.opinion.facade.collector.SurveysEntry;
import com.inqwise.opinion.http.HttpAnswererSession;
import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.http.HttpClientSessionUserArgs;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.infrastructure.systemFramework.cryptography.StringEncrypter;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class DataPostmasterDescryptor implements ICollectorPostmasterContext {

	final String SESSION_ID_COOKIE_KEY = "sid";
	final String UNIQUE_ID_COOKIE_KEY = "uid";
	final String passPhrase = "3b4b9507"; 
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	PrintWriter out = null;
	
	static SimpleDateFormat fixedformat = new SimpleDateFormat("MMMM dd, yyyy");
	static ApplicationLog logger = ApplicationLog.getLogger(DataPostmasterDescryptor.class);
	static EmailProvider emailProvider;
	
	public DataPostmasterDescryptor(PrintWriter out,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext context) {
		this.out = out;
		this.request = request;
		this.response = response;
	}

	Entry _opinions;
	public Entry getOpinions(){
		if(_opinions == null){
			_opinions = new OpinionsEntry(this);
		}
		return _opinions;
	}
	
	Entry _surveys;
	public Entry getSurveys(){
		if(_surveys == null){
			_surveys = new SurveysEntry(this);
		}
		return _surveys;
	}
	
	Entry _responses;
	public Entry getResponses(){
		if(null == _responses){
			_responses = new ResponsesEntry(this);
		}
		return _responses;
	}
	
	private void invokeMethods(Object actualObject, JSONObject source, JSONArray methodNames, String methodPath, JSONObject output) throws JSONException
	{
		try{
			for (int i = 0; i < methodNames.length(); i++) {
				String methodName = methodNames.optString(i);
				JSONObject methodObject = source.optJSONObject(methodName);
				BaseOperationResult result = null;
				
				Method m = identifyMethod(actualObject.getClass().getMethods(), methodName);
				try{
					if(null == m){
						UUID warnId = UUID.randomUUID();
						logger.warn("invokeMethods() : Requested method not found. MethodName: '%s', MethodPath: '%s', Id: '%s'. Signature of the method must be: methodName(JSONObject) or methodName()", methodName, methodPath, warnId.toString()); 
						result = new BaseOperationResult(ErrorCode.MethodNotFound, warnId, "Method not found");
					}
					
					if(null == result){
						Object returnValue;
						if(m.getParameterTypes().length == 0){
							returnValue = m.invoke(actualObject);
						} else {
							returnValue = m.invoke(actualObject, methodObject);
						}
						
						if(returnValue instanceof IPostmasterObject){
							actualObject = returnValue;
							JSONObject currentOutput = new JSONObject();
							output.put(methodName, currentOutput);
							String currentMethodPath = methodPath == null ? methodName : methodPath + "." + methodName;
							invokeMethods(actualObject, methodObject, methodObject.names(), currentMethodPath, currentOutput);
						} else if(m.getReturnType() != void.class){
							output.put(methodName, returnValue);
						} else {
							output.put(methodName, "done");
						}
					}
				} catch(Throwable t){
					UUID errorId = logger.error(t, "invokeMethods() : Unexpected error occured.");
					result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
				}
				
				if(null != result){
					output.put(methodName, result.toJson());
				}
			}
		}
		catch(Throwable t){
			UUID errorId = logger.error(t, "invokeMethods() : Unexpected error occured in main loop.");
			output = BaseOperationResult.toJsonGeneralError(t, errorId);
		}
	}
	
	private Method identifyMethod(Method[] methods, String methodName){
		
		for(Method method : methods){
			Class<?>[] parameterTypes = method.getParameterTypes();
			if((method.getName().equalsIgnoreCase(methodName) || method.getName().equalsIgnoreCase("get" + methodName)) && (parameterTypes.length == 0 || (parameterTypes.length == 1 && parameterTypes[0] == JSONObject.class))){
				return method;
			} 
		}
		return null;
	}
	
	public String getClientIp() {
		return getClientIp(request);
	}
	
	public static String getClientIp(HttpServletRequest request){
		String remoteAddr = request.getRemoteAddr();
		String xForwardedForHeader = request.getHeader(NetworkHelper.X_FORWARDED_FOR_HEADER_NAME);
		remoteAddr = NetworkHelper.identifyClientIp(remoteAddr, xForwardedForHeader);
		return remoteAddr;    
	}
	
	public IHttpAnswererSession getAnswererSession(String guid){
		
		return HttpAnswererSession.identifySession(request, response, guid, null);
	}
	
	public void removeAnswererSession(String guid){
		HttpAnswererSession.removeSession(request, response, guid);
	}
	
	public HttpClientSession getSession() {
		
		String sessionId = null;
		String encryptedUserArgs = null;
		String clientIp = getClientIp(request);
		StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
		
		/*
		 * Cookies
		 */
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookie.getMaxAge() != 0){
					String cookieName = cookie.getName();
					if (cookieName.equals(SESSION_ID_COOKIE_KEY)) {
						sessionId = cookie.getValue();
					} else if (cookieName.equals(UNIQUE_ID_COOKIE_KEY)) {
						encryptedUserArgs = cookie.getValue();
					}
				}
			}
		}
		
		try {
			String decryptedUserArgsBase64 = (null == encryptedUserArgs) ? null : desEncrypter.decrypt(encryptedUserArgs.replaceAll(" ", "+"));
			if(null == decryptedUserArgsBase64) {
				return new HttpClientSession(ErrorCode.NotSignedIn, "Not signed in");
			} else {
				HttpClientSessionUserArgs userArgs = null;
				MessagePack msgpack = new MessagePack();
				 // Deserialize
				userArgs = msgpack.read(Base64.decodeBase64(decryptedUserArgsBase64), HttpClientSessionUserArgs.class);
				
				return new HttpClientSession( sessionId, userArgs.getUserId(),
						clientIp, userArgs.getProductId(), userArgs.getClientIp());
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "getSession() : Unexpected error occured.");
			return new HttpClientSession(errorId, t.toString());
		}
	}
	
	public void addUserIdToSession(Long userId, boolean isPersist){
		addToSession(UNIQUE_ID_COOKIE_KEY, userId.toString(), true, isPersist);
	}
	
	public void addSessionIdToSession(UUID sessionId, boolean isPersist){
		addToSession(SESSION_ID_COOKIE_KEY, sessionId.toString(), false, isPersist);
	}
	
	private void addToSession(String key, String value, Boolean encripted, boolean isPersist){
		
		int cookieExpire = isPersist ? (60 * 60 * 24) * 30 /* 30 days */ : -1;
		StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
		if(encripted){
			value = desEncrypter.encrypt(value);
		}
		
		Cookie uniqueCookie = new Cookie(key, value);
		uniqueCookie.setMaxAge(cookieExpire);
		uniqueCookie.setPath("/");
		if(null != ApplicationConfiguration.Opinion.getSessionDomain()){
			uniqueCookie.setDomain(ApplicationConfiguration.Opinion.getSessionDomain());
		}
		response.addCookie(uniqueCookie);
	}
	
	public URL getAbsoluteURL() throws MalformedURLException {
		Locale locale = Locale.US;
		return request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath() + "/" + locale.toString().replace("_", "-").toLowerCase()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath() + "/" + locale.toString().replace("_", "-").toLowerCase());
	}

	public URL getApplicationURL() throws MalformedURLException{
		return request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath());
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	private Long getUserId(){
		HttpClientSession session;
		session = getSession();
		return session.hasError() ? null : session.getUserId();
	}

	public void invokeMethods(JSONObject source, JSONArray methodNames,
			String methodPath, JSONObject output) throws JSONException {
		invokeMethods(this, source, methodNames, methodPath, output);
		
	}

	private String refererUrl; 
	@Override
	public synchronized String getRefererUrl() {
		if(null == refererUrl){
			refererUrl = StringUtils.trimToNull(request.getHeader("referer"));
		}
		
		return refererUrl;
	}

}
