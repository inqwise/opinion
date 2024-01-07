package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;

public class CollectorSourcesDataAccess {
	private static final String COLLECTOR_SOURCE_ID_PARAM = "$collector_source_id";

	public static void getCollectorSourceReader(int collectorSourceId, IResultSetCallback callback) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params = {
			new SqlParam(COLLECTOR_SOURCE_ID_PARAM, collectorSourceId),
		};
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getCollectorSource", params);
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
