package com.inqwise.opinion.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.sheet.ISheet;
import com.inqwise.opinion.opinion.common.sheet.ISheetRequest;
import com.inqwise.opinion.opinion.common.sheet.StartSheetIndexData;

public final class Sheets extends DAOBase{
	static ApplicationLog logger = ApplicationLog.getLogger(Sheets.class);
	
	private final static String SHEET_ID_PARAM = "$sheet_id";
	private final static String PAGE_NUMBER_PARAM = "$page_number";
	private final static String OPINION_ID_PARAM = "$opinion_id";
	private final static String ACCOUNT_ID_PARAM = "$account_id";
	private final static String JOINED_IDS_PARAM = "$ids";
	private static final String TRANSLATION_ID_PARAM = "$translation_id";
	private static final String TITLE_PARAM = "$title";
	private static final String DESCRIPTION_PARAM = "$description";
	private static final String ACTION_GUID_PARAM = "$action_guid";
	private static final String USER_ID_PARAM = "$user_id";
	
	public static OperationResult<List<ISheet>> getSheets(Long opinionId, Long translationId, Long accountId, IDataFillable<ISheet> data) throws DAOException{
		List<ISheet> sheets = new ArrayList<ISheet>();
		BaseOperationResult result = fillSheets(null, opinionId, null, translationId, accountId, data, sheets);
		if(result.hasError()){
			return result.toErrorResult();
		} else {
			return new OperationResult<List<ISheet>>(sheets);
		}
	}
	
	public static BaseOperationResult getSheet(Long opinionId, Integer pageNumber, Long translationId, Long accountId, IDataFillable<ISheet> data) throws DAOException{
		OperationResult<ISheet> result = fillSheets(null, opinionId, pageNumber, translationId, accountId, data, null);
		if(result.hasError()){
			return result.toErrorResult();
		} else {
			try {
				return new OperationResult<ISheet>(result.getValue());
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
	}
	
	public static OperationResult<ISheet> getSheet(Long sheetId, Long translationId, Long accountId, IDataFillable<ISheet> data) throws DAOException{
		OperationResult<ISheet> result = fillSheets(sheetId, null, null, translationId, accountId, data, null);
		if(result.hasError()){
			return result.toErrorResult();
		} else {
			try {
				return new OperationResult<ISheet>(result.getValue());
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
	}
	
	private static OperationResult<ISheet> fillSheets(Long id, Long opinionId, Integer pageNumber, Long translationId, Long accountId, IDataFillable<ISheet> data, List<ISheet> sheets) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<ISheet> result = null;
		Connection connection = null;
		ISheet sheet = null;
		
		SqlParam[] params = {
				new SqlParam(SHEET_ID_PARAM, id),
				new SqlParam(OPINION_ID_PARAM, opinionId),
				new SqlParam(PAGE_NUMBER_PARAM, pageNumber),
				new SqlParam(TRANSLATION_ID_PARAM, translationId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getSheets", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            while (resultSet.next()) {
            	sheet = data.fill(resultSet);
            	if(null != sheets){
            		sheets.add(sheet);
            	}
            } 
            
            if(null == sheet) {
            	result = new OperationResult<ISheet>(ErrorCode.NoResults);
            } else 
            {
            	result = new OperationResult<ISheet>(sheet);
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
	
	public static OperationResult<Long> setSheet(ISheetRequest request) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<Long> result;
		
		SqlParam[] params = {
				new SqlParam(OPINION_ID_PARAM, request.getOpinionId()),
				new SqlParam(PAGE_NUMBER_PARAM, request.getPageNumber()),
				new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
				new SqlParam(TRANSLATION_ID_PARAM, request.getTranslationId()),
				new SqlParam(TITLE_PARAM, request.getTitle()),
				new SqlParam(DESCRIPTION_PARAM, request.getDescription()),
				new SqlParam(ACTION_GUID_PARAM, request.getActionGuid()),
				new SqlParam(USER_ID_PARAM, request.getUserId()),
	        };
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	
        	call = factory.GetProcedureCall("setSheet", params);

        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()) {
            	
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	
            	switch(errorCode){
            	case 0:
            		result = new OperationResult<Long>(ResultSetHelper.optLong(resultSet, "sheet_id"));
            		break;
            	case 4: // no have permissions
            		result = new OperationResult<Long>(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("setSheet() : errorCode not supported. Value: " + errorCode);
            	}
            	
            } else {
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
	public static BaseOperationResult updateSheet(Long sheetId, Long accountId,
			Long translationId, String title, String description, long userId) throws DAOException {
		
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(SHEET_ID_PARAM, sheetId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(TRANSLATION_ID_PARAM, translationId),
				new SqlParam(TITLE_PARAM, title),
				new SqlParam(DESCRIPTION_PARAM, description),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("updateSheet", params);
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
        			throw new OperationNotSupportedException("updateSheet() : errorCode not supported. Value: " + errorCode);
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
	
	public static BaseOperationResult deleteSheet(Long id, Long accountId, long userId) throws DAOException {
		
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		SqlParam[] params = {
				new SqlParam(SHEET_ID_PARAM, id),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(USER_ID_PARAM, userId),
	        };
		
		try {
			call = factory.GetProcedureCall("deleteSheet", params);
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
        			throw new OperationNotSupportedException("deleteSheet() : errorCode not supported. Value: " + errorCode);
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

	public static BaseOperationResult order(String joinedIds, Long accountId, long userId) throws DAOException {
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
			call = factory.GetProcedureCall("orderSheets", params);
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
        			throw new OperationNotSupportedException("orderSheets() : errorCode not supported. Value: " + errorCode);
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
	
	public static OperationResult<StartSheetIndexData> getStartIndexOfSheet(Long id) throws DAOException {
		
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<StartSheetIndexData> result;
		
		SqlParam[] params = {
				new SqlParam(SHEET_ID_PARAM, id),
	        };
		
		try {
			call = factory.GetProcedureCall("getStartIndexOfSheet", params);
			connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()) {
            	result = new OperationResult<StartSheetIndexData>(new StartSheetIndexData(resultSet.getInt("inx"), resultSet.getInt("numerator")) );
            } else {
            	result = new OperationResult<StartSheetIndexData>(ErrorCode.NoResults);
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

