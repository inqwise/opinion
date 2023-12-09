package com.inqwise.opinion.cms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.ILanguage;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class LanguagesDataAccess {

	public static void getLanguages(IDataFillable<ILanguage> callback) throws DAOException {
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
		
		SqlParam[] params = {
				//new SqlParam("page_id", pageId),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("getLanguages", params);
            connection = call.getConnection();
            
            // languages
            resultSet = call.executeQuery();
            callback.fill(resultSet);
            resultSet.close();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
		
	}

}
