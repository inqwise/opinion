package com.inqwise.opinion.automation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;

public class JobsDataAccess extends DAOBase {

	public static void getJobs(IResultSetCallback callback) throws DAOException {
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
				
		try {
			Database factory = DAOFactory.getInstance(Databases.Office);
			call = factory.GetProcedureCall("autm_getJobs");
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
	
	public static void setLastExecutionTime(int jobId, Date lastExecutionTime) throws DAOException{
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
				
		try {
			SqlParam[] params = {
				new SqlParam("$job_id", jobId),
				new SqlParam("$job_last_execution_date", lastExecutionTime),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
			call = factory.GetProcedureCall("autm_setJobLastExecutionTime", params);
            connection = call.getConnection();
            call.execute();
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
