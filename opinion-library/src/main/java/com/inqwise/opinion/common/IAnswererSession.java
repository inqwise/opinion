package com.inqwise.opinion.opinion.common;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public interface IAnswererSession {

	public final class ResultSetNames {

		public static final String IS_UNPLAINED = "is_unplanned";
		public static final String TARGET_URL = "target_url";
		public static final String TIME_TAKEN_SEC = "time_taken_sec";
		public static final String IS_COLLECTOR_EXIST = "collector_is_exist";
		public static final String COUNTRY_NAME = "country_name";
		public static final String COUNTRY_ID = "country_id";
		public static final String RESPONDENT_ID = "respondent_id";
		public static final String COLLECTOR_NAME = "collector_name";
		public static final String IS_STARRED = "starred";
		public static final String ID = "id";
		public static final String GARADE = "grade";
		public static final String MODIFY_USER_ID = "modify_user_id";
		public static final String FINISH_DATE = "finish_date";
		public static final String ANSWER_SESSION_ID = "answer_session_id";
		public static final String FLASH_VERSION = "flash_version";
		public static final String IS_JAVA_INSTALLED = "is_java_installed";
		public static final String IS_COOKIE_ENABLED = "is_cookie_enabled";
		public static final String BROWSER_VERSION = "browser_version";
		public static final String BROWSER_NAME = "browser_name";
		public static final String BROWSER_APP_NAME = "browser_app_name";
		public static final String SCREEN_COLOR = "screen_color";
		public static final String SCREEN_HEIGHT = "screen_height";
		public static final String SCREEN_WIDTH = "screen_width";
		public static final String OS_TIME_ZONE = "os_time_zone";
		public static final String OS_LANGUAGE = "os_language";
		public static final String OS_PLATFORM = "os_platform";
		public static final String OS_NAME = "os_name";
		public static final String GUID_TYPE_ID = "guid_type_id";
		public static final String GUID = "guid";
		public static final String COLLECTOR_ID = "collector_id";
		public static final String USER_ID = "user_id";
		public static final String COMMENT = "comment";
		public static final String CLIENT_IP = "client_ip";
		public static final String INSERT_DATE = "insert_date";
		public static final String OPINION_ID = "opinion_id";
		public static final String INDEX = "inx";
		
	}

	public abstract JSONObject toJson() throws JSONException;

	public abstract Date getInsertDate();

	public abstract String getClientIp();

	public abstract Date getFinishDate();

	public abstract String getAnswerSessionId();
	
	public abstract Integer getGrade();
	
	public abstract String getComment();
	
	public abstract long getId();
	
	public abstract boolean isStarred();
	
	public abstract Long getCollectorId();
	
	public abstract Integer getCountryId();
	
	public abstract String getRespondentId();

	public abstract String getCollectorName();
	
	public abstract String getCountryName();
	
	public abstract boolean isCollectorExist();
	
	public abstract Integer getTimeTakenSec();

	public abstract boolean isUnplanned();
	
	public abstract String getTargetUrl();

}