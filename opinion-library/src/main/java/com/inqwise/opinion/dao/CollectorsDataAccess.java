package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang3.StringUtils;
import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.IDeletedCollectorDetails;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.managers.CollectorsManager;

public class CollectorsDataAccess extends DAOBase {
	
	public static ApplicationLog logger = ApplicationLog.getLogger(CollectorsDataAccess.class);

	public static final String TRANSLATION_ID_PARAM = "$translation_id";
	public static final String RESPONSE_QUOTA_PARAM = "$response_quota";
	public static final String IS_HIDE_PASSWORD_PARAM = "$is_hide_password";
	public static final String PASSWORD_PARAM = "$password";
	public static final String IS_ALLOW_MULTIPLY_RESPONSES_PARAM = "$is_allow_multiply_responses";
	public static final String END_DATE_PARAM = "$end_date";
	public static final String COLLECTOR_ID_PARAM = "$collector_id";
	public static final String OPINION_ID_PARAM = "$opinion_id";
	public static final String NAME_PARAM = "$name";
	public static final String ACCOUNT_ID_PARAM = "$account_id";
	public static final String COLLECTOR_UUID_PARAM = "$collector_uuid";
	public static final String COLLECTOR_STATUS_ID_PARAM = "$collector_status_id";
	public static final String CLOSE_MESSAGE_PARAM = "$close_message";
	public static final String COLLECTOR_NAME_PARAM = "$collector_name";
	public static final String ACTION_GUID_PARAM = "$action_guid";
	public static final String SERVICE_PACKAGE_ID_PARAM = "$service_package_id";
	public static final String IS_ENABLE_PREVIOUS_PARAM = "$is_enable_previous";
	public static final String IS_ENABLE_RSS_UPDATES_PARAM = "$is_enable_rss_updates";
	public static final String IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION_PARAM = "$is_enable_finished_session_email_notification";
	public static final String USER_ID_PARAM = "$user_id";
	public static final String COLLECTOR_SOURCE_ID_PARAM = "$collector_source_id";
	public static final String COLLECTOR_EXTERNAL_ID_PARAM = "$collector_external_id";
	public static final String INCLUDE_EXPIRED_PARAM = "$include_expired";
	public static final String EXPIRATION_DATE_PARAM = "$expiration_date";
	public static final String RETURN_URL_PARAM = "$return_url";
	public static final String SKIP_RETURN_URL_PARAM = "$skip_return_url";
	public static final String SCREEN_OUT_URL_PARAM = "$screen_out_url";
	public static final String SKIP_SCREEN_OUT_URL_PARAM = "$skip_screen_out_url";
	public static final String REFERER_PARAM = "$referer";
	public static final String SKIP_REFERER_PARAM = "$skip_referer";
	public static final String SKIP_CLOSE_MESSAGE_PARAM = "$skip_close_message";
	public static final String SKIP_END_DATE_PARAM = "$skip_end_date";
	public static final String SKIP_PASSWORD_PARAM = "$skip_password";
	public static final String SURVEY_CLOSED_URL_PARAM = "$survey_closed_url";
	public static final String SKIP_SURVEY_CLOSED_URL_PARAM = "$skip_survey_closed_url";
	public static final String TOP_PARAM = "$top";
	public static final String FROM_INSERT_DATE_PARAM = "$from_insert_date";
	public static final String TO_INSERT_DATE_PARAM = "$to_insert_date";
	public static final String OPINION_TYPE_ID_PARAM = "$opinion_type_id";
	public static final String SKIP_RESPONSE_QUOTA = "$skip_response_quota";
	public static final String RESULTS_TYPE_ID_PARAM = "$results_type_id";
	public static final String ORDER_BY_PARAM = "$order_by";

	public static void insertCollector(IInsertCollectorParams params)  throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCallFromSqlProc("setCollector", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            params.insertCollectorCompleted(resultSet);
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static void updateCollector(IUpdateCollectorParams params)  throws DAOException{
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
        	call = factory.GetProcedureCallFromSqlProc("updateCollector", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            params.updateCollectorCompleted(resultSet);
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static BaseOperationResult updateCollectorStatus(Long collectorId, int statusId, Long accountId, String closeMessage, UUID newCollectorUuid, Long userId)  throws DAOException{
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(COLLECTOR_ID_PARAM, collectorId),
				new SqlParam(COLLECTOR_STATUS_ID_PARAM, statusId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(COLLECTOR_UUID_PARAM, newCollectorUuid),
				new SqlParam(CLOSE_MESSAGE_PARAM, closeMessage),
				new SqlParam(USER_ID_PARAM, userId),
				};

		try {
        	
        	call = factory.GetProcedureCall("updateCollectorStatus", params);
        	
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()) {
            	
            	int errorCode = (null == resultSet.getObject(ERROR_CODE)) ? 0 : resultSet.getInt(ERROR_CODE);
            	
            	switch(errorCode){
            	case 0:
            		result = BaseOperationResult.Ok;
            		break;
            	case 4: // doesn't have permissions
            		result = new BaseOperationResult(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("updateCollector() : errorCode not supported. Value: " + errorCode);
            	}
            	
            } else {
            	result = new OperationResult<String>(ErrorCode.NoResults);
            }
            
            return result;
            
            
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static BaseOperationResult updateCollectorName(Long collectorId, Long accountId, String collectorName, String actionGuid, long userId)  throws DAOException{
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(COLLECTOR_ID_PARAM, collectorId),
				new SqlParam(COLLECTOR_NAME_PARAM, collectorName),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(ACTION_GUID_PARAM, actionGuid),
				new SqlParam(USER_ID_PARAM, userId),
				};

		try {
        	
        	call = factory.GetProcedureCall("updateCollectorName", params);
        	
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()) {
            	
            	int errorCode = (null == resultSet.getObject(ERROR_CODE)) ? 0 : resultSet.getInt(ERROR_CODE);
            	
            	switch(errorCode){
            	case 0:
            		result = BaseOperationResult.Ok;
            		break;
            	case 4: // doesn't have permissions
            		result = new BaseOperationResult(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("updateCollector() : errorCode not supported. Value: " + errorCode);
            	}
            	
            } else {
            	result = new OperationResult<String>(ErrorCode.NoResults);
            }
            
            return result;
            
            
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static OperationResult<IDeletedCollectorDetails> deleteCollector(final Long id, Long accountId, long userId) throws DAOException {

		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<IDeletedCollectorDetails> result = new OperationResult<IDeletedCollectorDetails>();
		
		SqlParam[] params = {
				new SqlParam(COLLECTOR_ID_PARAM, id),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("deleteCollector", params);
			connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()) {
            	
            	int errorCode = ResultSetHelper.optInt(resultSet, ERROR_CODE);
            	
            	switch(errorCode){
            	case 0:
            		final String externalId = ResultSetHelper.optString(resultSet, "collector_external_id");
            		final CollectorStatus status = CollectorStatus.fromInt(ResultSetHelper.optInt(resultSet, "collector_status_id"));
            		IDeletedCollectorDetails collectorDetails = new IDeletedCollectorDetails() {
						
						@Override
						public long getCollectorId() {
							return id;
						}

						@Override
						public String getExternalId() {
							return externalId;
						}

						@Override
						public CollectorStatus getStatus() {
							return status;
						}
					};
					
					result.setValue(collectorDetails);
            		break;
            	case 4: // no have permissions
            		result.setError(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("deleteCollector() : errorCode not supported. Value: " + errorCode);
            	}
            	
            } else {
            	result.setError(ErrorCode.NoResults);
            }
            
            return result;
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
		
	}
	
	public static void getCollector(IResultSetCallback callback, Long collectorId, String collectorUuid, Long accountId) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(COLLECTOR_ID_PARAM, collectorId),
				new SqlParam(COLLECTOR_UUID_PARAM, collectorUuid),
        };
		
		try{
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getCollector", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            callback.call(resultSet, 0);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static BaseOperationResult setCollectorExternalId(long collectorId,
			String externalId, Integer statusId, Long accountId,
			Date expirationDate, Long userId, Long responseQuota) throws DAOException {
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		BaseOperationResult result;
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(COLLECTOR_ID_PARAM, collectorId),
				new SqlParam(COLLECTOR_EXTERNAL_ID_PARAM, externalId),
				new SqlParam(COLLECTOR_STATUS_ID_PARAM, statusId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(EXPIRATION_DATE_PARAM, expirationDate),
				new SqlParam(USER_ID_PARAM, userId),
				new SqlParam(RESPONSE_QUOTA_PARAM, responseQuota),
	        };
		
		try {
			call = factory.GetProcedureCall("setCollectorExternalId", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();  
			if(resultSet.next()) {
            	
            	int errorCode = (null == resultSet.getObject(ERROR_CODE)) ? 0 : resultSet.getInt(ERROR_CODE);
            	
            	switch(errorCode){
            	case 0:
            		result = BaseOperationResult.Ok;
            		break;
            	case 4: // doesn't have permissions
            		result = new BaseOperationResult(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("updateCollector() : errorCode not supported. Value: " + errorCode);
            	}
            	
            } else {
            	result = new OperationResult<String>(ErrorCode.NoResults);
            }
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
		
		return result;
	}
	
	public static JSONArray getCollectors(Long opinionId, Long accountId, boolean includeExpired, int top,
			Date from, Date to, Integer[] collectorsStatusIds, String orderBy) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(OPINION_ID_PARAM, opinionId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(INCLUDE_EXPIRED_PARAM, includeExpired),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(FROM_INSERT_DATE_PARAM, from),
					new SqlParam(TO_INSERT_DATE_PARAM, to),
					new SqlParam(COLLECTOR_STATUS_ID_PARAM, StringUtils.join(collectorsStatusIds, ",")),
					new SqlParam(ORDER_BY_PARAM, orderBy),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getCollectors", params);     
        	logger.debug("getCollectors.call= %s" , call);
			connection = call.getConnection();
            resultSet = call.executeQuery();
            List<JSONObject> list = DSL.using(connection).fetch(resultSet)
        			.map(r -> {
        				JSONObject obj = new JSONObject();
        				
        				for(var field : r.fields()) {
        					obj.put(field.getName(), r.getValue(field));
        				}
        				return obj;
        			});
                	
                    return new JSONArray(list);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static void getShortStatisticsReader(long collectorId, IResultSetCallback callback) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params = {
			new SqlParam(COLLECTOR_ID_PARAM, collectorId),
		};
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getCollectorShortStatistics", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			callback.call(resultSet, 0);
			
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}

