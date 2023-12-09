package com.inqwise.opinion.cms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.IPortal;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class PortalsDataAccess {

	private static final String PORTAL_NAME_PARAM = "$portal_name";

	public static void getPortalsReader(IDataFillable<IPortal> callback,
			String portalName) throws DAOException {
		CallableStatement call = null;
		Connection connection = null;
		ResultSet resultSet = null;
		
		SqlParam[] params =  
		{
				new SqlParam(PORTAL_NAME_PARAM, portalName),
        };
		
		Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
    	try {
			call = factory.GetProcedureCall("getPortals", params);
			connection = call.getConnection();
			callback.fill(call.executeQuery());
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
		
	}

}
