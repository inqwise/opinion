package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class GatewaysDataAccess {

	private static final String GATEWAY_ID_PARAM = "$gateway_id";

	public static void getReader(long gatewayId, IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(GATEWAY_ID_PARAM, gatewayId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getGateway", params);
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
