package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class ProductsDataAccess extends DAOBase {
	private static final String PRODUCT_GUID_PARAM = "$product_guid";
	private static final String PRODUCT_ID_PARAM = "$product_id";


	public static void getProductsReader(UUID productGuid, IResultSetCallback callback, Integer productId) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params = {
				new SqlParam(PRODUCT_GUID_PARAM, productGuid),
				new SqlParam(PRODUCT_ID_PARAM, productId),
        };
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getProducts", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            // products
            callback.call(resultSet, 0);
            
            // service packages
            if(call.getMoreResults()){
            	resultSet.close();
            	resultSet = call.getResultSet();
            	callback.call(resultSet, 1);
            }
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	} 
	 
}
