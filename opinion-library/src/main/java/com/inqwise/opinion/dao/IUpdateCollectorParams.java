package com.inqwise.opinion.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.dao.SqlProc;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;

@SqlProc
public interface IUpdateCollectorParams {
	
	public void updateCollectorCompleted(ResultSet reader) throws SQLException;
	
	@SqlProcParameter(name=CollectorsDataAccess.ACCOUNT_ID_PARAM)
	Long getAccountId();
	
	@SqlProcParameter(name=CollectorsDataAccess.COLLECTOR_ID_PARAM)
	Long getCollectorId();

	@SqlProcParameter(name=CollectorsDataAccess.END_DATE_PARAM)
	Date getEndDate();

	@SqlProcParameter(name=CollectorsDataAccess.IS_ALLOW_MULTIPLY_RESPONSES_PARAM)
	Boolean isAllowMultiplyResponses();

	@SqlProcParameter(name=CollectorsDataAccess.PASSWORD_PARAM)
	String getPassword();

	@SqlProcParameter(name=CollectorsDataAccess.IS_HIDE_PASSWORD_PARAM)
	Boolean isHidePassword();

	@SqlProcParameter(name=CollectorsDataAccess.RESPONSE_QUOTA_PARAM)
	Long getResponseQuota();

	@SqlProcParameter(name=CollectorsDataAccess.TRANSLATION_ID_PARAM)
	Long getTranslationId();

	@SqlProcParameter(name=CollectorsDataAccess.IS_ENABLE_PREVIOUS_PARAM)
	Boolean isEnablePrevious();

	@SqlProcParameter(name=CollectorsDataAccess.IS_ENABLE_RSS_UPDATES_PARAM)
	Boolean isEnableRssUpdates();

	@SqlProcParameter(name=CollectorsDataAccess.IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION_PARAM)
	Boolean isEnableFinishedSessionEmailNotification();

	@SqlProcParameter(name=CollectorsDataAccess.USER_ID_PARAM)
	long getUserId();

	@SqlProcParameter(name=CollectorsDataAccess.RETURN_URL_PARAM)
	String getReturnUrl();

	@SqlProcParameter(name=CollectorsDataAccess.SKIP_RETURN_URL_PARAM)
	boolean skipReturnUrl();

	@SqlProcParameter(name=CollectorsDataAccess.SCREEN_OUT_URL_PARAM)
	String getScreenOutUrl();

	@SqlProcParameter(name=CollectorsDataAccess.SKIP_SCREEN_OUT_URL_PARAM)
	boolean skipScreenOutUrl();

	@SqlProcParameter(name=CollectorsDataAccess.REFERER_PARAM)
	String getReferer();

	@SqlProcParameter(name=CollectorsDataAccess.SKIP_REFERER_PARAM)
	boolean skipReferer();

	@SqlProcParameter(name=CollectorsDataAccess.CLOSE_MESSAGE_PARAM)
	String getCloseMessage();

	@SqlProcParameter(name=CollectorsDataAccess.SKIP_CLOSE_MESSAGE_PARAM)
	boolean skipCloseMessage();

	@SqlProcParameter(name=CollectorsDataAccess.SKIP_END_DATE_PARAM)
	boolean skipEndDate();

	@SqlProcParameter(name=CollectorsDataAccess.SKIP_PASSWORD_PARAM)
	boolean skipPassword();

	@SqlProcParameter(name=CollectorsDataAccess.SURVEY_CLOSED_URL_PARAM)
	String getOpinionClosedUrl();

	@SqlProcParameter(name=CollectorsDataAccess.SKIP_SURVEY_CLOSED_URL_PARAM)
	boolean skipOpinionClosedUrl();
	
	@SqlProcParameter(name=CollectorsDataAccess.SKIP_RESPONSE_QUOTA)
	boolean skipResponseQuota();
	
	@SqlProcParameter(name=CollectorsDataAccess.RESULTS_TYPE_ID_PARAM)
	Integer getResultsTypeId();
}
