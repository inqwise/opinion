package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.naming.OperationNotSupportedException;

import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.ResultSets;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;

public class AnswerersSessionsDataAccess extends DAOBase {

	private static final String COMMENT_PARAM = "$comment";
	private static final String GRADE_PARAM = "$grade";
	private static final String ANSWER_SESSION_ID_PARAM = "$answer_session_id";
	private static final String OPINION_ID_PARAM = "$opinion_id";
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String ID_PARAM = "$id";
	private static final String STARRED_PARAM = "$starred";
	private static final String ANSWERER_SESSION_ID_PARAM = "$as_id";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String RESPONDENT_ID_PARAM = "$respondent_id";
	private static final String COLLECTOR_ID_PARAM = "$collector_id";
	private static final String INCLUDE_UNPLANNED_PARAM = "$include_unplanned";
	private static final String FROM_INDEX_PARAM = "$from_index";
	private static final String TOP_PARAM = "$top";

	public static JSONArray getAnswerersSessions(Long opinionId,
			Long accountId, Long collectorId, String respondentId, boolean includeUnplanned, Long fromIndex, Integer top, AtomicLong total) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params = {
				new SqlParam(OPINION_ID_PARAM, opinionId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(RESPONDENT_ID_PARAM, respondentId),
				new SqlParam(COLLECTOR_ID_PARAM, collectorId),
				new SqlParam(INCLUDE_UNPLANNED_PARAM, includeUnplanned),
				new SqlParam(TOP_PARAM, top),
				new SqlParam(FROM_INDEX_PARAM, fromIndex),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getAnswerersSessions", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            JSONArray result = ResultSets.parse(connection, resultSet);
        	
            if(call.getMoreResults()){
            	resultSet = call.getResultSet();
            	if(resultSet.next()){
            		total.set(resultSet.getLong("total"));
            	}
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
	
	public static void getAnswererSessionResultSet(IResultSetCallback callback, Long id, String answerSessionId, Long accountId) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
				
		SqlParam[] params = {
				new SqlParam(ANSWER_SESSION_ID_PARAM, answerSessionId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(ID_PARAM, id),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getAnswerersSession", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if (resultSet.next()) {
            	callback.call(resultSet, 0);
            } 
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static void getLastAnswererSessionResultSet(IResultSetCallback callback, Long collectorId, String respondentId, boolean includeUnplanned) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
				
		SqlParam[] params = {
				new SqlParam(RESPONDENT_ID_PARAM, respondentId),
				new SqlParam(COLLECTOR_ID_PARAM, collectorId),
				new SqlParam(INCLUDE_UNPLANNED_PARAM, includeUnplanned),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getLastAnswerersSession", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if (resultSet.next()) {
            	callback.call(resultSet, 0);
            } 
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static BaseOperationResult updateGrade(Long id,
			Long accountId, Integer grade, String comment, Long userId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		try {
			
			SqlParam[] params = {
				new SqlParam(ID_PARAM, id), 
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(GRADE_PARAM, grade),
				new SqlParam(COMMENT_PARAM, comment),
				new SqlParam(USER_ID_PARAM, userId),
				};

		
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	
        	call = factory.GetProcedureCall("setAnswererSessionGrade", params);
        	connection = call.getConnection();
            call.execute();
            return BaseOperationResult.Ok;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static BaseOperationResult updateStarred(long id, Long accountId,
			boolean starred, Long userId)  throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		try {
			
			SqlParam[] params = {
				new SqlParam(ID_PARAM, id), 
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(STARRED_PARAM, starred),
				new SqlParam(USER_ID_PARAM, userId),
				};

			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	
        	call = factory.GetProcedureCall("setAnswererSessionStarred", params);
        	connection = call.getConnection();
            call.execute();
            return BaseOperationResult.Ok;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static BaseOperationResult deleteAnswerSession(Long answererSessionId, Long accountId, Long userId) throws DAOException {

		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(ANSWERER_SESSION_ID_PARAM, answererSessionId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("deleteAnswererSession", params);
			connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()) {
            	//UUID errorId;
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	
            	switch(errorCode){
            	case 0:
            		result = new BaseOperationResult(ErrorCode.NoError);
            		break;
            	case 4: // no have permissions
            		result = new BaseOperationResult(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("deleteAnswerSession() : errorCode not supported. Value: " + errorCode);
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

}
