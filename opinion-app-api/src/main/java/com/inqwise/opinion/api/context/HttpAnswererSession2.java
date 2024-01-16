package com.inqwise.opinion.api.context;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.api.common.AnswererSessionArgs;
import com.inqwise.opinion.api.dao.cache.AnswerersSessionsCacheAccess;
import com.inqwise.opinion.common.IHttpAnswererSession;
import com.inqwise.opinion.infrastructure.common.ICookie;
import com.inqwise.opinion.infrastructure.common.ICookies;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.cryptography.StringEncrypter;

public class HttpAnswererSession2 implements IHttpAnswererSession {
	
	private static final String PASSWORD_RESTRICTION_MARKER_SECRET_KEY = "!";
	private static final String ANSWERER_SESSION_PREFIX = "pg_";
	private static final String LAST_VIEWED_SHEET_INDEX_KEY = "si";
	private static final String FINISH_DATE_KEY = "fd";
	private static final String IS_CLIENT_INFO_RECEIVED_KEY = "ci";
	private static final String SESSION_ID_KEY = "id";
	public static final int SECONDS_IN_YEAR = (60 * 60 * 24) * 365;
	public static final int SECONDS_IN_MONTH = (60 * 60 * 24) * 30;
	public static final int SECONDS_IN_DAY = (60 * 60 * 24);
	public static final int SECONDS_IN_WEEK = (60 * 60 * 24) * 7;
	public static final int SECONDS_IN_HOUR = (60 * 60);
	private static final String PASSWORD_RESTRICTION_MARKER_KEY = "pr";
	static ApplicationLog logger = ApplicationLog.getLogger(HttpAnswererSession2.class);
	
	private ICookies cookies;
	private Integer maxAge;
	private String cookieKey;
	private JSONObject sessionJo;
	private StringEncrypter desEncrypter = null;
	private AnswererSessionArgs args;
	private AnswererSessionArgs originalArgs;
	private boolean forceUpdate = false;
	private boolean isNew;
	
	private HttpAnswererSession2(ICookies cookies, String sessionId, String cookieKey, boolean isNew, Integer maxAge) {
		this.sessionJo = new JSONObject();
		this.cookieKey = cookieKey;
		this.cookies = cookies;
		this.maxAge = maxAge;
		this.isNew = isNew;
		
		if(null != sessionId){
			args = AnswerersSessionsCacheAccess.get(sessionId);
		}
		
		if(null == args){
			if(!isNew){
				// expired or finished
				isNew = true;
			}
			args = new AnswererSessionArgs();
			args.setSessionId(UUID.randomUUID().toString().replaceAll("-", ""));
			try{
				save();
			} catch (UnsupportedEncodingException e) {
				logger.error(e, "Unable to save cache");
			}
		} 
		
		if(isNew){
			try {
				saveCookies();
			} catch (UnsupportedEncodingException e) {
				logger.error(e, "Unable to save cookie");
			}
		}
	}

	//private static ICookie createCookie(String name, JSONObject sessionJo, int maxAge){
	//	return new Cookie(name, sessionJo.toString()).withPath("/").withMaxAge(maxAge);
	//}
	
	public static IHttpAnswererSession identifySession(ICookies cookies, String guid, Integer maxAge, String sessionKey, String sessionId){
		
		if(null == maxAge){
			maxAge = SECONDS_IN_YEAR;
		}
		
		String cookieKey = ANSWERER_SESSION_PREFIX.concat(guid);
		if(null != sessionKey && !cookieKey.equalsIgnoreCase(sessionKey)){
			logger.error("identifySession: sessionKey not as expected. received: '%s', expected: '%s', sessionId: ''%s", sessionKey, cookieKey, sessionId);
			throw new Error("Invalid sessionKey. SessionId");
		}
		
		ICookie cookie = cookies.firstOrDefault(cookieKey);
		String value = null;
		
		if(null != cookie){
			value = cookie.getValue();
		}
				
		boolean isNew = true;
		
		if(null != value){
			try {
				JSONObject answererSessionJo = convertCookieToJson(value);
				if(null == sessionId){
					sessionId = answererSessionJo.optString(SESSION_ID_KEY);
				}
				isNew = false;
			} catch (JSONException e) {
				logger.error(e, "identifySession() : Failed to convert cookieValue to JsonObject. Value: '%s', key: '%s', cookie: 'maxAge=%s'", value, cookieKey, cookie.getMaxAge());
			} catch (UnsupportedEncodingException e) {
				logger.error(e, "identifySession() : UnsupportedEncodingException. Value: '%s', key: '%s'", value, cookieKey);
			}
		}
		return new HttpAnswererSession2(cookies, sessionId, cookieKey, isNew, maxAge);
	}

	private static JSONObject convertCookieToJson(String value)
			throws UnsupportedEncodingException {
		return new JSONObject(URLDecoder.decode(value, StandardCharsets.UTF_8.displayName()));
	}
	
	public void setLastViewedSheetIndex(Integer lastViewedSheetIndex) throws JSONException {
		args.setLastViewedSheetIndex(lastViewedSheetIndex);
		if(null == originalArgs){
			originalArgs = args.clone();
		}
	}

	public Integer getLastViewedSheetIndex() {
		return (null == args.getLastViewedSheetIndex() && null == args.getFinishDate()) ? 0 : args.getLastViewedSheetIndex();
	}

	public void setFinishDate(Date finishDate) throws JSONException {
		args.setFinishDate(finishDate);
		forceUpdate = true;
	}

	public Date getFinishDate() {
		return args.getFinishDate();
	}
	
	public boolean isOpinionFinished(){
		return null != args.getFinishDate();
	}
	
	public boolean getIsClientInfoReceived(){
		return null == args.getIsClientInfoReceived() ? false : args.getIsClientInfoReceived();
	}
	
	public void setIsClientInfoReceived(boolean isClientInfoReceived) throws JSONException{
		args.setIsClientInfoReceived(isClientInfoReceived);
		if(null == originalArgs){
			originalArgs = args.clone();
		}
	}
	
	public String getSessionId(){
		return args.getSessionId();
	}
	
	private void saveCookies() throws UnsupportedEncodingException{
		if(isOpinionFinished()){
			sessionJo.put(FINISH_DATE_KEY, args.getFinishDate().getTime());
		} else {
			sessionJo.put(SESSION_ID_KEY, args.getSessionId());
		}
		
		cookies.put(cookieKey, sessionJo.toString(), maxAge);
	}
	
	public void save() throws UnsupportedEncodingException {
		AnswerersSessionsCacheAccess.update(args.getSessionId(), (forceUpdate ? null : originalArgs), args);
		
		//cookies.put(cookieKey, sessionJo);
		/*
		cookie.setValue(URLEncoder.encode(sessionJo.toString(), StandardCharsets.UTF_8.displayName()));
		response.addCookie(cookie);
		*/
	}

	public static void removeSession(ICookies cookies, String guid) {
		String cookieKey = ANSWERER_SESSION_PREFIX.concat(guid);
		ICookie cookie = cookies.firstOrDefault(cookieKey);
		cookies.expiry(cookieKey);
		if(null != cookie){
			try {
				String sessionId = convertCookieToJson(cookie.getValue()).optString(SESSION_ID_KEY);
				if(null != sessionId){
					AnswerersSessionsCacheAccess.remove(sessionId);
				}
			} catch (Exception ex) {}
		}
	}

	public void setPasswordRestrictionMarker() throws JSONException{
		args.setIsPasswordRequired(false);
		if(null == originalArgs){
			originalArgs = args.clone();
		}
	}
	
	public boolean havePasswordRestrictionMarker(){
		boolean result;
		if(null == args.getIsPasswordRequired() || isOpinionFinished()){
			result = false;
		} else {
			result = true;
		}
		
		return result;
	}

	@Override
	public String getKey() {
		return cookieKey;
	}
}
