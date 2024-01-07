package com.inqwise.opinion.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.SqlProc;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;

@SqlProc
public interface IInsertCollectorParams {

	void insertCollectorCompleted(ResultSet reader) throws SQLException;
	
	@SqlProcParameter(name=CollectorsDataAccess.NAME_PARAM)
	String getName();

	@SqlProcParameter(name=CollectorsDataAccess.OPINION_ID_PARAM)
	Long getOpinionId();

	@SqlProcParameter(name=CollectorsDataAccess.END_DATE_PARAM)
	Date getEndDate();

	@SqlProcParameter(name=CollectorsDataAccess.IS_ALLOW_MULTIPLY_RESPONSES_PARAM)
	Boolean getIsAllowMultiplyResponses();

	@SqlProcParameter(name=CollectorsDataAccess.PASSWORD_PARAM)
	String getPassword();

	@SqlProcParameter(name=CollectorsDataAccess.IS_HIDE_PASSWORD_PARAM)
	Boolean getIsHidePassword();

	@SqlProcParameter(name=CollectorsDataAccess.RESPONSE_QUOTA_PARAM)
	Long getResponseQuota();

	@SqlProcParameter(name=CollectorsDataAccess.TRANSLATION_ID_PARAM)
	Long getTranslationId();

	@SqlProcParameter(name=CollectorsDataAccess.COLLECTOR_UUID_PARAM)
	UUID getCollectorUuid();

	@SqlProcParameter(name=CollectorsDataAccess.COLLECTOR_STATUS_ID_PARAM)
	Integer getCollectorStatusId();

	@SqlProcParameter(name=CollectorsDataAccess.IS_ENABLE_PREVIOUS_PARAM)
	Boolean isEnablePrevious();

	@SqlProcParameter(name=CollectorsDataAccess.IS_ENABLE_RSS_UPDATES_PARAM)
	Boolean isEnableRssUpdates();

	@SqlProcParameter(name=CollectorsDataAccess.IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION_PARAM)
	Boolean isEnableFinishedSessionEmailNotification();
	
	@SqlProcParameter(name=CollectorsDataAccess.ACTION_GUID_PARAM)
	String getActionGuid();

	@SqlProcParameter(name=CollectorsDataAccess.USER_ID_PARAM)
	long getUserId();

	@SqlProcParameter(name=CollectorsDataAccess.COLLECTOR_SOURCE_ID_PARAM)
	Integer getCollectorSourceId();

	@SqlProcParameter(name=CollectorsDataAccess.EXPIRATION_DATE_PARAM)
	Date getExpirationDate();

	@SqlProcParameter(name=CollectorsDataAccess.RETURN_URL_PARAM)
	String getReturnUrl();

	@SqlProcParameter(name=CollectorsDataAccess.SCREEN_OUT_URL_PARAM)
	String getScreenOutUrl();

	@SqlProcParameter(name=CollectorsDataAccess.REFERER_PARAM)
	String getReferer();

	@SqlProcParameter(name=CollectorsDataAccess.SURVEY_CLOSED_URL_PARAM)
	String getOpinionClosedUrl();

	@SqlProcParameter(name=CollectorsDataAccess.OPINION_TYPE_ID_PARAM)
	int getOpinionTypeId();

	@SqlProcParameter(name=CollectorsDataAccess.ACCOUNT_ID_PARAM)
	long getAccountId();
	
	@SqlProcParameter(name=CollectorsDataAccess.SERVICE_PACKAGE_ID_PARAM)
	Integer getServicePackageId();
	
	@SqlProcParameter(name=CollectorsDataAccess.RESULTS_TYPE_ID_PARAM)
	Integer getResultsTypeId();
}
