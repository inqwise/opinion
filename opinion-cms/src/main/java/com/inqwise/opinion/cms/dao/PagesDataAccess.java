package com.inqwise.opinion.cms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.IPageGeneratorCallback;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class PagesDataAccess {
	private static final String LANGUAGE_ID_PARAM = "$language_id";

	private static final String PAGE_ID_PARAM = "$page_id";
	private static final String PAGE_URI_PARAM = "$page_uri";
	private static final String PORTAL_ID_PARAM = "$portal_id";

	public static BaseOperationResult getPages(IPageGeneratorCallback callback, Integer pageId,
			String pageUri, Integer parentId, int languageId, int portalId) throws DAOException{
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(PAGE_ID_PARAM, pageId),
				new SqlParam(PAGE_URI_PARAM, pageUri),
				new SqlParam(LANGUAGE_ID_PARAM, languageId),
				new SqlParam(PORTAL_ID_PARAM, portalId),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("getPages", params);
            connection = call.getConnection();
            
            // page
            resultSet = call.executeQuery();
            callback.create(resultSet);
            resultSet.close();

            // page resources            
            if(call.getMoreResults()){
            	resultSet = call.getResultSet();
            	callback.fillResources(resultSet);
            }
            
			return BaseOperationResult.Ok;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
