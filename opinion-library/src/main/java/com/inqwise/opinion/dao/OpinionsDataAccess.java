/**
 * 
 */
package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;
/**
 * @author basil
 *
 */
public class OpinionsDataAccess extends DAOBase {

	
	static final String ACCOUNT_ID_PARAM = "$account_id";
	static final String OPINION_ID_PARAM = "$opinion_id";
	static final String NAME_PARAM = "$opinion_name";
	static final String TITLE_PARAM = "$opinion_title";
	static final String DESCRIPTION_PARAM = "$description";
	static final String ACTION_GUID_PARAM = "$action_guid";
	static final String TRANSLATION_ID_PARAM = "$translation_id";
	static final String THEME_ID_PARAM = "$theme_id";
	static final String USER_ID_PARAM = "$user_id";
	static final String TOP_PARAM = "$top";
	static final String FROM_DATE_PARAM = "$from_date";
	static final String TO_DATE_PARAM = "$to_date";
	static final String OPINION_TYPE_ID_PARAM = "$opinion_type_id";
	
	static final String LOGO_URL_PARAM = "$logo_url";
	static final String GUID_PARAM = "$guid";
	static final String START_MESSAGE_PARAM = "$start_message";
	static final String FINISH_MESSAGE_PARAM = "$finish_message";
	static final String TRANSLATION_NAME_PARAM = "$translation_name";
	static final String ORDER_BY_RECENT_PARAM = "$order_by_recent";
	static final String SHOW_PROGRESS_BAR_PARAM = "$show_progress_bar";
	static final String REDIRECT_URL_PARAM = "$redirect_url";
	static final String CULTURE_PARAM = "$culture";
	static final String START_BUTTON_TITLE_PARAM = "$start_button_title";
	static final String FINISH_BUTTON_TITLE_PARAM = "$finish_button_title";
	static final String PREVIOUS_BUTTON_TITLE_PARAM = "$previous_button_title";
	static final String NEXT_BUTTON_TITLE_PARAM = "$next_button_title";
	static final String IS_HIGHLIGHT_REQUIRED_QUESTIONS_PARAM = "$is_highlight_required_questions";
	static final String USE_PAGE_NUMBERING_PARAM = "$use_page_numbering";
	static final String USE_QUESTION_NUMBERING_PARAM = "$use_question_numbering";
	static final String IS_RTL_PARAM = "$is_rtl";
	static final String ORDER_BY_PARAM = "$order_by";
	public static final String FINISH_BEHAVIOUR_TYPE_ID_PARAM = "$finish_behaviour_type_id";
	public static final String LABEL_PLACEMENT_ID_PARAM = "$label_placement_id";
	public static final String SHOW_PAGING_PARAM = "$show_paging";
	public static final String HIDE_POWERED_BY_PARAM = "$disable_powered_by";
	public static final String ALREADY_COMPLETED_MESSAGE_PARAM = "$already_completed_message";
	public static final String REQUIRED_QUESTION_ERROR_MESSAGE = "$required_question_error_message";
	
	public static BaseOperationResult deleteOpinion(Long opinionId, Long accountId, long userId) throws DAOException {

		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(OPINION_ID_PARAM, opinionId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("deleteOpinion", params);
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
        			throw new OperationNotSupportedException("deleteOpinion() : errorCode not supported. Value: " + errorCode);
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

	public static BaseOperationResult setOpinionName(Long opinionId,
			Long accountId, String name, String title, String description, String actionGuid, Long translationId, long userId) throws DAOException {
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(OPINION_ID_PARAM, opinionId),
				new SqlParam(TRANSLATION_ID_PARAM, translationId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(NAME_PARAM, name),
				new SqlParam(TITLE_PARAM, title),
				new SqlParam(DESCRIPTION_PARAM, description),
				new SqlParam(ACTION_GUID_PARAM, actionGuid),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("setOpinionName", params);
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
        			throw new OperationNotSupportedException("setOpinionName() : errorCode not supported. Value: " + errorCode);
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

	public static BaseOperationResult setThemeId(Long opinionId,
			Long accountId, int themeId, String actionGuid, long userId) throws DAOException {
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(OPINION_ID_PARAM, opinionId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(THEME_ID_PARAM, themeId),
				new SqlParam(ACTION_GUID_PARAM, actionGuid),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("setOpinionThemeId", params);
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
        			throw new OperationNotSupportedException("setThemeId() : errorCode not supported. Value: " + errorCode);
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

	public static JSONArray getOpinions(Long accountId,
			int top, Date from, Date to,
			Integer opinionTypeId, long translationId, String orderBy) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(FROM_DATE_PARAM, from),
					new SqlParam(TO_DATE_PARAM, to),
					new SqlParam(OPINION_TYPE_ID_PARAM, opinionTypeId),
					new SqlParam(TRANSLATION_ID_PARAM, translationId),
					new SqlParam(ORDER_BY_PARAM, orderBy),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getOpinions", params);     
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
	
	public static void insertOpinion(IInsertOpinionParams params) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		/*
		SqlParam[] sqlParams = {
				new SqlParam(USER_ID_PARAM, params.getUserId()),
				new SqlParam(NAME_PARAM, params.getName()),
				new SqlParam(ACCOUNT_ID_PARAM, params.getAccountId()),
				new SqlParam(THEME_ID_PARAM, params.getThemeId()),
				new SqlParam(TRANSLATION_NAME_PARAM, params
						.getTranslationName()),
				new SqlParam(TRANSLATION_ID_PARAM, params
						.getTranslationId()),
				new SqlParam(CULTURE_PARAM, params.getCulture()),
				new SqlParam(ACTION_GUID_PARAM, params.getActionGuid()),
				new SqlParam(TITLE_PARAM, params.getTitle()),
				new SqlParam(DESCRIPTION_PARAM, params.getDescription()),
				new SqlParam(IS_RTL_PARAM, params.isRtl()),
				new SqlParam(OPINION_TYPE_ID_PARAM, params.getOpinionTypeId()),
				new SqlParam(IS_HIGHLIGHT_REQUIRED_QUESTIONS_PARAM, params
						.getIsHighlightRequestedQuestions()),
				new SqlParam(OpinionsDataAccess.START_MESSAGE_PARAM, params.getStartMessage()),
				new SqlParam(OpinionsDataAccess.FINISH_MESSAGE_PARAM, params.getFinishMessage()),
				new SqlParam(OpinionsDataAccess.START_BUTTON_TITLE_PARAM, params
						.getStartButtonTitle()),
				new SqlParam(OpinionsDataAccess.FINISH_BUTTON_TITLE_PARAM, params
						.getFinishButtonTitle()),
				new SqlParam(OpinionsDataAccess.PREVIOUS_BUTTON_TITLE_PARAM, params
						.getPreviousButtonTitle()),
				new SqlParam(OpinionsDataAccess.NEXT_BUTTON_TITLE_PARAM, params
						.getNextButtonTitle()),
				new SqlParam(OpinionsDataAccess.USE_PAGE_NUMBERING_PARAM, params.isUsePageNumbering()),
				new SqlParam(OpinionsDataAccess.USE_QUESTION_NUMBERING_PARAM, params.isUseQuestionNumbering()),
				new SqlParam(OpinionsDataAccess.LOGO_URL_PARAM, params.getLogoUrl()),
        };
		*/
		
		try{
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCallFromSqlProc("setOpinion", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            params.insertOpinionCompleted(resultSet);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static void getOpinion(IResultSetCallback callback, Long opinionId, String opinionGuid, Long accountId, Long translationId) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(OPINION_ID_PARAM, opinionId),
				new SqlParam(TRANSLATION_ID_PARAM, translationId),
				new SqlParam(GUID_PARAM, opinionGuid),
        };
		
		try{
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getOpinion", params);     
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
	
	public static void updateOpinion(IUpdateOpinionParams params) throws DAOException {

		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			call = factory.GetProcedureCallFromSqlProc("updateOpinion", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			
			params.updateOpinionCompleted(resultSet);
			
			

		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}

	}

	public static JSONArray getTemplatesDataSet() throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					//new SqlParam(ORDER_BY_PARAM, orderBy),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getOpinionTemplates", params);     
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
	
}
