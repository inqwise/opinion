package com.inqwise.opinion.common.collectors;


import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.OpinionType;

public interface ICollector {

	final class JsonNames {
		public static final String COUNT_OF_COMPLETED = "countOfCompleted";
		public static final String CLOSE_AFTER_CERTAIN_DATE = "closeAfterCertainDate";
		public static final String COLLECTOR_UUID = "guid";
		public static final String TRANSLATION_ID = "translationId";
		public static final String RESPONSE_QUOTA = "quotaReached";
		public static final String IS_ENABLE_RESPONSE_QUOTA = "closeAfterQuotaReached";
		public static final String IS_ALLOW_MULTIPLY_RESPONSES = "allowMultipleResponses";
		public static final String END_DATE = "certainDate";
		public static final String OPINION_ID = "opinionId";
		public static final String NAME = "name";
		public static final String COLLECTOR_ID = "collectorId";
		public static final String IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION = "enableEmailNotification";
		public static final String COLLECT_URL = "collectorUrl";
		public static final String STATUS_ID = "statusId";
		public static final String COLLECTOR_EXTERNAL_ID = "externalId";
		public static final String COLLECTOR_SOURCE_ID = "sourceId";
		public static final String IS_HIDE_PASSWORD = "hidePassword";
		public static final String PASSWORD = "password";
		public static final String IS_ENABLE_PREVIOUS = "enablePrevious";
		public static final String IS_ENABLE_RSS_UPDATES = "enableRssUpdates";
		public static final String RETURN_URL = "returnUrl";
		public static final String SCREEN_OUT_URL = "screenOutUrl";
		public static final String REFERER = "referer";
		public static final String CLOSE_MESSAGE = "closeMessage";
		public static final String SOURCE_TYPE_ID = "sourceTypeId";
		public static final String CLOSED_URL = "closedUrl";
		public static final String IS_PASSWORD_REQUIRED = "isPasswordRequired";
		public static final String ACCOUNT_ID = "accountId";
		public static final String MODIFY_DATE = "modifyDate";
		public static final String CREATE_DATE = "createDate";
		public static final String EXPIRATION_DATE = "expirationDate";
		public static final String OPINION_NAME = "opinionName";
		public static final String SOURCE_NAME = "sourceName";
		public static final String STARTED_RESPONSES = "started";
		public static final String LAST_RESPONSE_DATE = "lastResponseDate";
		public static final String SOURCE_ID = "sourceId";
		public static final String RESULTS_TYPE_ID = "resultsTypeId";
		public static final String OPINION_TYPE_NAME = "opinionTypeName";
		public static final String FINISHED_RESPONSES = "completed";
		public static final String PARTIAL_RESPONSES = "partial";
		public static final String COMPLETION_RATE = "completionRate";
		public static final String TIME_TAKEN = "timeTaken";
		public static final String ACCOUNT_NAME = "accountName";
	}
	
	final class ResultSetNames {

		public static final String COLLECTOR_NAME = "name";
		public static final String COLLECTOR_ID = "collector_id";
		public static final String COLLECTOR_SOURCE_TYPE_ID = "collector_source_type_id";
		public static final String COLLECTOR_SOURCE_ID = "collector_source_id";
		public static final String IS_ENABLE_RSS_UPDATES = "is_enable_rss_updates";
		public static final String CLOSE_MESSAGE = "close_message";
		public static final String IS_HIDE_PASSWORD = "is_hide_password";
		public static final String PASSWORD = "password";
		public static final String OPINION_TYPE_ID = "opinion_type_id";
		public static final String IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION = "is_enable_finished_session_email_notification";
		public static final String LAST_START_DATE = "last_start_date";
		public static final String LAST_FINISH_DATE = "last_finish_date";
		public static final String CNT_FINISHED_OPINIONS = "cnt_finished_opinions";
		public static final String CNT_STARTED_OPINIONS = "cnt_started_opinions";
		public static final String COLLECTOR_STATUS_ID = "collector_status_id";
		public static final String COLLECTOR_UUID = "collector_uuid";
		public static final String TRANSLATION_ID = "translation_id";
		public static final String RESPONSE_QUOTA = "response_quota";
		public static final String IS_ALLOW_MULTIPLY_RESPONSES = "is_allow_multiply_responses";
		public static final String END_DATE = "end_date";
		public static final String OPINION_ID = "opinion_id";
		public static final String EXPIRATION_DATE = "expiration_date";
		public static final String IS_ENABLE_PREVIOUS = "is_enable_previous";
		public static final String COLLECTOR_EXTERNAL_ID = "collector_external_id";
		public static final String OPINION_CLOSED_URL = "survey_closed_url";
		public static final String REFERER = "referer";
		public static final String SCREEN_OUT_URL = "screen_out_url";
		public static final String RETURN_URL = "return_url";
		public static final String ACCOUNT_ID = "account_id";
		public static final String MODIFY_DATE = "modify_date";
		public static final String CREATE_DATE = "create_date";
		public static final String OPINION_NAME = "opinion_name";
		public static final String COLLECTOR_SOURCE_NAME = "collector_source_name";
		public static final String RESULTS_TYPE_ID = "results_type_id";
		public static final String OPINION_TYPE_NAME = "opinion_type_name";
		public static final String AVG_TIME_TAKEN_SEC = "avg_time_taken_sec";
	} 
	
	public abstract Long getId();

	public abstract String getName();

	public abstract Long getOpinionId();

	public abstract Long getTranslationId();

	public JSONObject toJson(Integer timezoneOffset) throws JSONException;

	public abstract String getUuid();
	
	public abstract CollectorStatus getCollectorStatus();
	
	public abstract Long getCountOfFinishedOpinions();

	public abstract Long getCountOfStartedOpinions();

	public abstract Date getDateOfLastStartedOpinion();

	public abstract Date getDateOfLastFinishedOpinion();

	public abstract Boolean isEnableFinishedSessionEmailNotification();

	public abstract String getCollectUrl();
	
	public abstract CollectorSourceType getCollectorSourceType();

	public abstract OperationResult<IOpinion> getOpinion(Long accountId);

	public abstract BaseOperationResult changeStatus(CollectorStatus status, Long userId);
	
	public abstract BaseOperationResult modify(JSONObject input, long userId,
			long accountId);
	
	public abstract OpinionType getOpinionType();
	
	public abstract Long getResponseQuota();
	
	interface IEndDateExtension {
		public abstract Date getEndDate();
	}
	
	interface IMultiplyResponsesExtension{
		public abstract Boolean isAllowMultiplyResponses();
	}
	
	interface IPasswordExtension {
		public abstract String getPassword();
		public abstract Boolean isHidePassword();
	}
	
	interface IMessagesExtension {
		public abstract String getCloseMessage();
	}
	
	interface IMultiPageExtension {
		public abstract Boolean isEnablePrevious();
	}
}