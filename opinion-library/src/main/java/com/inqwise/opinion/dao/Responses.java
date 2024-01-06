package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.common.IResponseRequest;

public class Responses extends DAOBase {
	static ApplicationLog logger = ApplicationLog.getLogger(Responses.class);
	
	public static OperationResult<Long> setResponse(IResponseRequest request, Integer geoCountryId)  throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<Long> result;
		try {
			
			SqlParam[] params = {
				new SqlParam("$translation_id", request.getTranslationId()), 
				new SqlParam("$opinion_id", request.getOpinionId()),
				new SqlParam("$parent_id", request.getParentId()),
				new SqlParam("$parent_type_id", request.getParentTypeId()),
				new SqlParam("$control_id", request.getControlId()),
				new SqlParam("$option_id", request.getOptionId()),
				new SqlParam("$control_content", request.getControlContent()),
				new SqlParam("$answer_text", request.getAnswerText()),
				new SqlParam("$answer_value", request.getAnswerValue()),
				new SqlParam("$client_ip", request.getClientIp()),
				new SqlParam("$comment", request.getComment()),
				new SqlParam("$page_number", request.getPageNumer()),
				new SqlParam("$control_index", request.getControlIndex()),
				new SqlParam("$user_id", request.getUserId()),
				new SqlParam("$response_type_id", request.getResponseTypeId()),
				new SqlParam("$control_type_id", request.getControlTypeId()),
				new SqlParam("$collector_id", request.getCollectorId()),
				new SqlParam("$opinion_version_id", request.getOpinionVersionId()),
				new SqlParam("$guid", request.getGuid()),
				new SqlParam("$guid_type_id", request.getGuidTypeId()),

				new SqlParam("$os_name", request.getOsName()),          
				new SqlParam("$os_platform", request.getOsPlatform()),     
				new SqlParam("$os_language", request.getOsLanguage()),      
				new SqlParam("$os_time_zone", request.getOsTimeZone()),     
				new SqlParam("$screen_width", request.getScreenWidth()),     
				new SqlParam("$screen_height", request.getScreenHeight()),    
				new SqlParam("$screen_color", request.getScreenColorDepth()),     
				new SqlParam("$browser_app_name", request.getBrowserAppName()), 
				new SqlParam("$browser_version", request.getBrowserVersion()),  
				new SqlParam("$is_cookie_enabled", request.getIsCookieEnabled()),
				new SqlParam("$is_java_installed", request.getIsJavaInstalled()),
				new SqlParam("$flash_version", request.getFlashVersion()),    
				new SqlParam("$answer_session_id", request.getAnswerSessionId()),
				new SqlParam("$browser_name", request.getBrowserName()),
				
				new SqlParam("$sheet_id", request.getSheetId()),    
				new SqlParam("$target_page_number", request.getTargetPageNumber()),
				new SqlParam("$target_sheet_id", request.getTargetSheetId()),
				new SqlParam("$control_input_type_id", request.getControlInputTypeId()),
				new SqlParam("$is_selected", request.getIsSelected()),
				new SqlParam("$select_type_id", request.getSelectTypeId()),
				new SqlParam("$option_kind_id", request.getOptionKindId()),
				new SqlParam("$option_index", request.getOptionIndex()),
				new SqlParam("$is_create_new_session", request.isCreateNewSession()),
				new SqlParam("$opinion_account_id", request.getOpinionAccountId()),
				new SqlParam("$opinion_account_service_package_id", request.getOpinionAccountServicePackageId()),
				new SqlParam("$is_unplanned", request.isUnpanned()),
				new SqlParam("$respondent_id", request.getRespondentId()),
				new SqlParam("$geo_country_id", geoCountryId),
				new SqlParam("$target_url", request.getTargetUrl()),
				new SqlParam("$control_key", request.getControlKey()),
				};

		
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	
        	call = factory.GetProcedureCall("setResponse", params);
        	connection = call.getConnection();

            resultSet = call.executeQuery();
            if(resultSet.next()){
            	final Long responseId = Long.valueOf(resultSet.getLong("response_id"));
            	result = new OperationResult<Long>(responseId);
            } else{
            	result = new OperationResult<Long>(ErrorCode.NoResults);
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
