package com.inqwise.opinion.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.opinion.common.ICreateResult;
import com.inqwise.opinion.opinion.common.IOption;
import com.inqwise.opinion.opinion.common.IOptionRequest;

public class Options extends DAOBase{

	public final static String VALUE_PARAM = "$option_value";
	public final static String TEXT_PARAM = "$option_text";
	public final static String ORDER_ID_PARAM = "$order_id";
	public final static String CONTROL_ID_PARAM = "$control_id";
	public final static String OPTION_ID_PARAM = "$option_id";
	public final static String TRANSLATION_ID_PARAM = "$translation_id";
	public final static String ACCOUNT_ID_PARAM = "$account_id";
	public final static String JOINED_IDS_PARAM = "$ids";
	public final static String IS_ENABLE_ADDITIONAL_DETAILS_PARAM = "$is_enable_additional_info";
	public final static String ADDITIONAL_DETAILS_TITLE_PARAM = "$additional_details_title";
	private static final String ANSWER_SESSION_ID_PARAM = "$answer_session_id";
	public static final String OPTION_KIND_ID_PARAM = "$option_kind_id";
	private static final String OPINION_ID_PARAM = "$opinion_id";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String LINK_PARAM = "$link";
	private static final String LINK_TYPE_ID_PARAM = "$link_type_id";
	
	public static BaseOperationResult delete(Long optionId, Long accountId, long userId) throws DAOException {
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(OPTION_ID_PARAM, optionId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("deleteOption", params);
			connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()) {
            	
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	
            	switch(errorCode){
            	case 0:
            		result = new BaseOperationResult(ErrorCode.NoError);
            		break;
            	case 4: // no have permissions
            		result = new BaseOperationResult(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("delete() : errorCode not supported. Value: " + errorCode);
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
	
	public static OperationResult<ICreateResult> setOption(IOptionRequest request) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<ICreateResult> result;
		
		SqlParam[] params = {
				new SqlParam(VALUE_PARAM, request.getValue()),
				new SqlParam(TEXT_PARAM, request.getText()),
				new SqlParam(TRANSLATION_ID_PARAM, request.getTranslationId()),
				new SqlParam(CONTROL_ID_PARAM, request.getControlId()),
				new SqlParam(ORDER_ID_PARAM, request.getOrderId()),
				new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
				new SqlParam(IS_ENABLE_ADDITIONAL_DETAILS_PARAM, request.getIsEnableAdditionalDetails()),
				new SqlParam(ADDITIONAL_DETAILS_TITLE_PARAM, request.getAdditionalDetailsTitle()),
				new SqlParam(OPTION_KIND_ID_PARAM, request.getOptionKindId()),
				new SqlParam(OPINION_ID_PARAM, request.getOpinionId()),
				new SqlParam(USER_ID_PARAM, request.getUserId()),
				new SqlParam(LINK_PARAM, request.getLink()),
				new SqlParam(LINK_TYPE_ID_PARAM, request.getLinkTypeId()),
	        };
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	
        	call = factory.GetProcedureCall("setOption", params);

        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()){
            	final Long optionId = Long.valueOf(resultSet.getLong("option_id"));
            	final Integer orderId = Integer.valueOf(resultSet.getInt("order_id"));
            	result = new OperationResult<ICreateResult>(new ICreateResult(){

					@Override
					public Long getId() {
						return optionId;
					}

					@Override
					public Integer getOrderId() {
						return orderId;
					}});
            } else{
            	result = new OperationResult<ICreateResult>(ErrorCode.NoResults);
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
	
	public static BaseOperationResult updateOption(Long optionId, String value, String text, Long translationId,
													Long accountId, Boolean isEnableAdditionalDetails,
													String additionalDetailsTitle, long userId,
													String link, Integer linkTypeId) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(OPTION_ID_PARAM, optionId),
				new SqlParam(VALUE_PARAM, value),
				new SqlParam(TEXT_PARAM, text),
				new SqlParam(TRANSLATION_ID_PARAM, translationId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(IS_ENABLE_ADDITIONAL_DETAILS_PARAM, isEnableAdditionalDetails),
				new SqlParam(ADDITIONAL_DETAILS_TITLE_PARAM, additionalDetailsTitle),
				new SqlParam(USER_ID_PARAM, userId),
				new SqlParam(LINK_PARAM, link),
				new SqlParam(LINK_TYPE_ID_PARAM, linkTypeId),
	        };
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	
        	call = factory.GetProcedureCall("updateOption", params);

        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()){
            	result = new BaseOperationResult();
            } else{
            	result = new BaseOperationResult(ErrorCode.NoResults);
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

	public static BaseOperationResult orderOptions(String joinedIds, Long accountId, long userId) throws DAOException {
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(JOINED_IDS_PARAM, joinedIds),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("orderOptions", params);
			connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()) {
            	
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	
            	switch(errorCode){
            	case 0:
            		result = new BaseOperationResult(ErrorCode.NoError);
            		break;
            	case 4: // no have permissions
            		result = new BaseOperationResult(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("orderOptions() : errorCode not supported. Value: " + errorCode);
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
