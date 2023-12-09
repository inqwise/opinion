package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.common.servicePackages.IServicePackageCreateRequest;
import com.inqwise.opinion.library.common.servicePackages.IServicePackageUpdateRequest;

public class ServicePackageDataAccess {

	private static final String SERVICE_PACKAGE_DEFAULT_USAGE_PERIOD_PARAM = "$default_usage_period";
	private static final String SERVICE_PACKAGE_DESCRIPTION_PARAM = "$description";
	private static final String SERVICE_PACKAGE_AMOUNT_PARAM = "$amount";
	private static final String SERVICE_PACKAGE_PRODUCT_ID_PARAM = "$product_id";
	private static final String SERVICE_PACKAGE_NAME_PARAM = "$sp_name";
	private static final String PRODUCT_ID_PARAM = SERVICE_PACKAGE_PRODUCT_ID_PARAM;
	private static final String INCLUDE_NON_ACTIVE_PARAM = "$include_non_active";
	private static final String SERVICE_PACKAGE_ID_PARAM = "$service_package_id";

	public static void getServicePackages(IResultSetCallback callback,
			Integer productId, boolean includeNonActive) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(PRODUCT_ID_PARAM, productId),
					new SqlParam(INCLUDE_NON_ACTIVE_PARAM, includeNonActive),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getServicePackages", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            // contact
            callback.call(resultSet, 0);
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static void getServicePackage(IResultSetCallback callback,
			int servicePackageId, Integer productId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(SERVICE_PACKAGE_ID_PARAM, servicePackageId),
					new SqlParam(PRODUCT_ID_PARAM, productId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getServicePackage", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            // 1. resultSet
            callback.call(resultSet, 0);
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void updateServicePackage(IServicePackageUpdateRequest request) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(SERVICE_PACKAGE_ID_PARAM, request.getId()),
					new SqlParam(SERVICE_PACKAGE_NAME_PARAM, request.getName()),
					new SqlParam(SERVICE_PACKAGE_PRODUCT_ID_PARAM, null),
					new SqlParam(SERVICE_PACKAGE_AMOUNT_PARAM, request.getAmount()),
					new SqlParam(SERVICE_PACKAGE_DESCRIPTION_PARAM, request.getDescription()),
					new SqlParam(SERVICE_PACKAGE_DEFAULT_USAGE_PERIOD_PARAM, request.getDefaultUsagePeriod()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setServicePackage", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
		
	}
	
	public static void newServicePackage(IServicePackageCreateRequest request) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(SERVICE_PACKAGE_ID_PARAM, null),
					new SqlParam(SERVICE_PACKAGE_NAME_PARAM, request.getName()),
					new SqlParam(SERVICE_PACKAGE_PRODUCT_ID_PARAM, request.getProductId()),
					new SqlParam(SERVICE_PACKAGE_AMOUNT_PARAM, request.getAmount()),
					new SqlParam(SERVICE_PACKAGE_DESCRIPTION_PARAM, request.getDescription()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setServicePackage", params);
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
