package com.inqwise.opinion.opinion.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.cryptography.StringEncrypter;
import com.inqwise.opinion.opinion.common.IHttpAnswererSession;

public class HttpAnswererSession implements IHttpAnswererSession {
	
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
	static ApplicationLog logger = ApplicationLog.getLogger(HttpAnswererSession.class);
	
	private HttpServletResponse response;
	private String guid;
	private JSONObject sessionJo;
	private Cookie cookie;
	private StringEncrypter desEncrypter = null;
	private boolean isNew;
	
	private HttpAnswererSession(Cookie cookie, HttpServletResponse response, String guid, JSONObject answererSessionJo, boolean isNew) {
		if(null == answererSessionJo){
			answererSessionJo = new JSONObject();
		}
		this.sessionJo = answererSessionJo;
		this.guid = guid;
		this.response = response;
		this.cookie = cookie;
		this.isNew = isNew;
		if(isNew){
			try {
				sessionJo.putOpt(SESSION_ID_KEY, UUID.randomUUID().toString());
				save();
			} catch (JSONException e) {
				logger.error(e, "Failed to put new sessionId to json");
			} catch (UnsupportedEncodingException e) {
				logger.error(e, "Failed to encode new sessionId");
			}
		}
	}

	private static Cookie findCookie(HttpServletRequest request, String guid, int maxAge){
		Cookie foundCookie = null;
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			for(Cookie cookie : cookies){
				if(cookie.getMaxAge() != 0 && cookie.getName().equalsIgnoreCase(ANSWERER_SESSION_PREFIX.concat(guid))){
					if(null == foundCookie){
						foundCookie = cookie;
						foundCookie.setMaxAge(maxAge);
						foundCookie.setPath("/");
						//break;
					} else {
						logger.warn("findCookie: found more that one cookie with name '%s', value: '%s'", ANSWERER_SESSION_PREFIX, cookie.getValue());
					}
				}
			}
		}
		
		return foundCookie;
	}
	
	private static Cookie findCookieOrDefault(HttpServletRequest request, String guid, int maxAge){
		Cookie cookie = findCookie(request, guid, maxAge);
		if(null == cookie){
			cookie = createCookie(guid, maxAge);
		}
		return cookie;
	}
	
	private static Cookie createCookie(String guid, int maxAge){
		Cookie	cookie = new Cookie(ANSWERER_SESSION_PREFIX.concat(guid), null);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	public static IHttpAnswererSession identifySession(HttpServletRequest request, HttpServletResponse response, String guid, Integer maxAge){
		if(null == maxAge){
			maxAge = SECONDS_IN_YEAR;
		}
		
		JSONObject answererSessionJo = null;
		Cookie cookie = findCookieOrDefault(request, guid, maxAge);
		
		String value = cookie.getValue();
		boolean isNew = true;
		
		//if(null == StringUtils.trimToNull(value)){
		if(null == value){
			answererSessionJo = new JSONObject();
		} else {
			try {
				answererSessionJo = new JSONObject(URLDecoder.decode(value, StandardCharsets.UTF_8.displayName()));// in some reason - in Production server received unescaped value
				isNew = false;
			} catch (JSONException e) {
				logger.error(e, "identifySession() : Failed to convert cookieValue to JsonObject. Value: '%s', Guid: '%s', QueryString: '%s'", value, guid, request.getQueryString());
				answererSessionJo = new JSONObject();
				cookie = createCookie(guid, maxAge);
			} catch (UnsupportedEncodingException e) {
				logger.error(e, "identifySession() : UnsupportedEncodingException. Value: '%s', Guid: '%s', QueryString: '%s'", value, guid, request.getQueryString());
				answererSessionJo = new JSONObject();
				cookie = createCookie(guid, maxAge);
			}
		}
		return new HttpAnswererSession(cookie, response, guid, answererSessionJo, isNew);
	}

	public void setLastViewedSheetIndex(Integer lastViewedSheetIndex) throws JSONException {
		sessionJo.put(LAST_VIEWED_SHEET_INDEX_KEY, lastViewedSheetIndex);
	}

	public Integer getLastViewedSheetIndex() {
		return JSONHelper.optInt(sessionJo, LAST_VIEWED_SHEET_INDEX_KEY, 0);
	}

	public void setFinishDate(Date finishDate) throws JSONException {
		if(null == finishDate){
			sessionJo.put(FINISH_DATE_KEY, finishDate);
		} else {
			sessionJo.put(FINISH_DATE_KEY, finishDate.getTime());
		}
	}

	public Date getFinishDate() {
		Long dateInTicks = JSONHelper.optLong(sessionJo, FINISH_DATE_KEY);
		return null == dateInTicks ? null : new Date(dateInTicks);
	}
	
	public boolean isOpinionFinished(){
		return !sessionJo.isNull(FINISH_DATE_KEY);
	}
	
	public boolean getIsClientInfoReceived(){
		return sessionJo.optBoolean(IS_CLIENT_INFO_RECEIVED_KEY);
	}
	
	public void setIsClientInfoReceived(boolean isClientInfoReceived) throws JSONException{
		sessionJo.put(IS_CLIENT_INFO_RECEIVED_KEY, isClientInfoReceived);
	}
	
	public String getSessionId(){
		return JSONHelper.optString(sessionJo, SESSION_ID_KEY);
	}
	
	public void save() throws UnsupportedEncodingException{
		if(isOpinionFinished()){
			if(!sessionJo.isNull(LAST_VIEWED_SHEET_INDEX_KEY)){
				sessionJo.remove(LAST_VIEWED_SHEET_INDEX_KEY);
			}
			sessionJo.remove(SESSION_ID_KEY);
			sessionJo.remove(IS_CLIENT_INFO_RECEIVED_KEY);
			sessionJo.remove(PASSWORD_RESTRICTION_MARKER_KEY);
		}
		cookie.setValue(URLEncoder.encode(sessionJo.toString(), StandardCharsets.UTF_8.displayName()));
		response.addCookie(cookie);
	}

	public static void removeSession(HttpServletRequest request, HttpServletResponse response, String guid) {
		Cookie cookie = findCookieOrDefault(request, guid, -1);
		cookie.setValue("");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	private StringEncrypter getSessionStringEncrypter(){
		if(null == desEncrypter){
			desEncrypter = new StringEncrypter(getSessionId());
		}
		
		return desEncrypter;
	}
	
	public void setPasswordRestrictionMarker() throws JSONException{
		
		sessionJo.put(PASSWORD_RESTRICTION_MARKER_KEY, getSessionStringEncrypter().encrypt(PASSWORD_RESTRICTION_MARKER_SECRET_KEY));
	}
	
	public boolean havePasswordRestrictionMarker(){
		boolean result;
		String encryptedMarker = sessionJo.optString(PASSWORD_RESTRICTION_MARKER_KEY);
		if(null == encryptedMarker || isOpinionFinished()){
			result = false;
		} else {
			String actualMarker = getSessionStringEncrypter().decrypt(encryptedMarker);
			result = PASSWORD_RESTRICTION_MARKER_SECRET_KEY.equals(actualMarker);
		}
		
		return result;
	}

	@Override
	public String getKey() {
		return guid;
	}
}
