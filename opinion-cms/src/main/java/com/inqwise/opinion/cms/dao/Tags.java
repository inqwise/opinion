package com.inqwise.opinion.cms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.ITag;

public class Tags extends DAOBase{
	private static final String BLOG_ID_PARAM = "$blog_id";
	private static final String TAG_ID_PARAM = "$tag_id";
	private static final String TAG_NAME_PARAM = "$tag_name";

	public static void getBlogTagsRecordSet(IResultSetCallback callback, int blogId) throws DAOException {
		CallableStatement call = null;
		Connection connection = null;
		ResultSet resultSet = null;
		
		SqlParam[] params =  
		{
				new SqlParam(BLOG_ID_PARAM, blogId),
        };
		
		Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
    	try {
			call = factory.GetProcedureCall("getBlogTags", params);
			connection = call.getConnection();
			ResultSet reader = call.executeQuery();
			// Total
			callback.call(reader, 1);
			reader.close();
			
			// Tags
			if(call.getMoreResults()){
				callback.call(call.getResultSet(), 2);
			}
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static int set(Integer tagId, String tagName) throws DAOException {
		
		Connection connection = null;	
    	CallableStatement call = null;
    	ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(TAG_ID_PARAM, tagId),
				new SqlParam(TAG_NAME_PARAM, tagName),
        };
		
		try {
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("setTag", params);
            connection = call.getConnection();
            resultSet = call.executeQuery();
            
            if(resultSet.next()){
            	tagId = resultSet.getInt("tag_id");
            } else {
            	throw new Exception("No Results");
            }
            
            resultSet.close();
            return tagId;
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static void delete(int tagId) throws DAOException{
		Connection connection = null;	
    	CallableStatement call = null;
    	ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(TAG_ID_PARAM, tagId),
        };
		
		try {
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("deleteTag", params);
            connection = call.getConnection();
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
