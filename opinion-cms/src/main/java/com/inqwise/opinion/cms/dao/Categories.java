package com.inqwise.opinion.cms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.ICategory;

public class Categories extends DAOBase {

	private static final String BLOG_ID_PARAM = "$blog_id";
	private static final String ENTITY_TYPE_ID_PARAM = "$entity_type_id";
	private static final String CATEGORY_ID_PARAM = "$category_id";
	private static final String CATEGORY_NAME_PARAM = "$category_name";
	
	public static OperationResult<List<ICategory>> getCategories(IDataFillable<OperationResult<List<ICategory>>> callback, Integer entityTypeId, int blogId) throws DAOException{
		CallableStatement call = null;
		Connection connection = null;
		ResultSet resultSet = null;
		
		SqlParam[] params =  
		{
				new SqlParam(ENTITY_TYPE_ID_PARAM, entityTypeId),
				new SqlParam(BLOG_ID_PARAM, blogId),
        };
		
		Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
    	try {
			call = factory.GetProcedureCall("getCategories", params);
			connection = call.getConnection();
			return callback.fill(call.executeQuery());
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static int set(Integer categoryId, String categoryName) throws DAOException{
		Connection connection = null;	
    	CallableStatement call = null;
    	ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(CATEGORY_ID_PARAM, categoryId),
				new SqlParam(CATEGORY_NAME_PARAM, categoryName),
        };
		
		try {
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("setCategory", params);
            connection = call.getConnection();
            resultSet = call.executeQuery();
            
            if(resultSet.next()){
            	categoryId = resultSet.getInt("category_id");
            } else {
            	throw new Exception("No Results");
            }
            
            resultSet.close();
            return categoryId;
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static void delete(int categoryId) throws DAOException{
		Connection connection = null;	
    	CallableStatement call = null;
    	ResultSet resultSet = null;
		
		SqlParam[] params = {
				new SqlParam(CATEGORY_ID_PARAM, categoryId),
        };
		
		try {
			Database factory = DAOFactory.getInstance(CmsConfiguration.getDatabaseName());
			call = factory.GetProcedureCall("deleteCategory", params);
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
