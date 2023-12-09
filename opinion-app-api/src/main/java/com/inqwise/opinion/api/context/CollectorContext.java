package com.inqwise.opinion.api.context;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.msgpack.MessagePack;
import org.restexpress.Request;
import org.restexpress.Response;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.infrastructure.systemFramework.cryptography.StringEncrypter;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.opinion.common.ICollectorPostmasterContext;
import com.inqwise.opinion.opinion.common.IHttpAnswererSession;
import com.inqwise.opinion.opinion.common.IPostmasterObject;
import com.inqwise.opinion.opinion.facade.collector.Entry;
import com.inqwise.opinion.opinion.facade.collector.OpinionsEntry;
import com.inqwise.opinion.opinion.facade.collector.ResponsesEntry;
import com.inqwise.opinion.opinion.facade.collector.SurveysEntry;
import com.inqwise.opinion.opinion.http.HttpClientSession;
import com.inqwise.opinion.opinion.http.HttpClientSessionUserArgs;

public class CollectorContext implements ICollectorPostmasterContext {

	public static int[] DEFAULT_PORTS = new int[] {80, 443};
	
	final String SESSION_ID_COOKIE_KEY = "sid";
	final String UNIQUE_ID_COOKIE_KEY = "uid";
	final String passPhrase = "3b4b9507"; 
	
	static ApplicationLog logger = ApplicationLog.getLogger(CollectorContext.class);
	
	private Request request;
	private Response response;
	private Cookies cookies;
	private URL url;
	private String sessionId;
	private String sessionKey;
	
	public CollectorContext(Request request, Response response, Cookies cookies, String sessionId, String sessionKey) throws MalformedURLException {
		this.request = request;
		this.response = response;
		this.cookies = cookies;
		this.url = new URL(request.getUrl());
		this.sessionId = sessionId;
		this.sessionKey = sessionKey;
	}

	private Entry _opinions;
	public Entry getOpinions(){
		if(_opinions == null){
			_opinions = new OpinionsEntry(this);
		}
		return _opinions;
	}
	
	private Entry _surveys;
	public Entry getSurveys(){
		if(_surveys == null){
			_surveys = new SurveysEntry(this);
		}
		return _surveys;
	}
	
	private Entry _responses;
	public Entry getResponses(){
		if(null == _responses){
			_responses = new ResponsesEntry(this);
		}
		return _responses;
	}
	
	@Override
	public String getClientIp() {
		return NetworkHelper.identifyClientIp(request.getRemoteAddress().getHostString(), request.getHeader(NetworkHelper.X_FORWARDED_FOR_HEADER_NAME));
	}

	
	private HttpClientSession session;
	@Override
	public synchronized HttpClientSession getSession() throws IOException {
		
		if(null == session){
			String sessionId = null;
			String encryptedUserArgs = null;
			String clientIp = getClientIp();
			StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
			
			sessionId = cookies.optString(SESSION_ID_COOKIE_KEY);
			encryptedUserArgs = cookies.optString(UNIQUE_ID_COOKIE_KEY);
			
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
		return session;
	}

	@Override
	public URL getAbsoluteURL() throws MalformedURLException {
		return null;
	}

	@Override
	public URL getApplicationURL() throws MalformedURLException {
		return null;
	}
	
	@Override
	public void removeAnswererSession(String guid) {
		HttpAnswererSession2.removeSession(cookies, guid);
	}

	@Override
	public IHttpAnswererSession getAnswererSession(String guid) {
		IHttpAnswererSession session = HttpAnswererSession2.identifySession(cookies, guid, null, sessionKey, sessionId);
		return session;
	}

	public void invokeMethods(JSONObject source, JSONArray methodNames,
			String methodPath, JSONObject output) throws JSONException {
		invokeMethods(this, source, methodNames, methodPath, output);
		
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

	@Override
	public String getRefererUrl() {
		return request.getHeader("referer");
	}
}
