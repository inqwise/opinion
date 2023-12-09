package com.inqwise.opinion.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public final class GeneralSettings extends DAOBase {

	private final static ApplicationLog logger = ApplicationLog.getLogger(GeneralSettings.class);
	private final static String KEY_PARAM = "$key";
	
	public static OperationResult<String> getValue(String key) throws DAOException {
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<String> result;
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getGeneralSettingValueByKey", new SqlParam(KEY_PARAM, key));
            connection = call.getConnection();
            resultSet = call.executeQuery();
            if (resultSet.next()) {
            	result = new OperationResult<String>(resultSet.getString("value"));
            } else {
            	UUID errorId = logger.error("The key '%s' doesn't exist in GeneralSettings.");
            	result = new OperationResult<String>(ErrorCode.NotExist, errorId);
            }
            
			return result;
		
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
    }

}
