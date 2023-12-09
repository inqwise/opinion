package com.inqwise.opinion.cms.dao.blog;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.blog.IBlog;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class BlogsDataAccess {
	private static final String BLOG_ID_PARAM = "$blog_id";

	public static void getCommentsReader(IDataFillable<IBlog> callback, long blogId) throws DAOException{
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(BLOG_ID_PARAM, blogId),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("getBlogs", params);
            connection = call.getConnection();
            
            // comments
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
