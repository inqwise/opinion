package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.common.accounts.IAccountBillingSettingsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IChangeBalanceRequest;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class AccountsDataAccess {

	private static final String USER_ID_PARAM = "$user_id";
	private static final String PRODUCT_ID_PARAM = "$product_id";
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String INCLUDE_NON_ACTIVE_PARAM = "$include_non_active";
	private static final String TOP_PARAM = "$top";
	private static final String COMMENTS_PARAM = "$comments";
	private static final String TIMEZONE_ID_PARAM = "$timezone_id";
	private static final String ACCOUNT_NAME_PARAM = "$account_name";
	private static final String IS_ACTIVE_PARAM = "$is_active";
	private static final String SERVICE_PACKAGE_ID_PARAM = "$service_package_id";
	private static final String OWNER_ID_PARAM = "$owner_id";
	private static final String FROM_INSERT_DATE_PARAM = "$from_insert_date";
	private static final String TO_INSERT_DATE_PARAM = "$to_insert_date";
	private static final String BACKOFFICE_USER_ID_PARAM = "$backoffice_user_id";
	private static final String AMOUNT_PARAM = "$amount";
	private static final String ACCOUNT_OPERATION_TYPE_ID_PARAM = "$accop_type_id";
	private static final String SOURCE_GUID_PARAM = "$source_guid";
	private static final String SESSION_ID_PARAM = "$session_id";
	private static final String GEO_COUNTRY_CODE_PARAM = "$geo_country_code";
	private static final String CLIENT_IP_PARAM = "$client_ip";
	private static final String SERVICE_PACKAGE_EXPIRY_DATE_PARAM = "$service_package_expiry_date";
	private static final String BUSINESS_COMPANY_NAME_PARAM = "$business_company_name";
	private static final String BUSINESS_FIRST_NAME_PARAM = "$business_first_name";
	private static final String BUSINESS_LAST_NAME_PARAM = "$business_last_name";
	private static final String BUSINESS_ADDRESS1_PARAM = "$business_address1";
	private static final String BUSINESS_ADDRESS2_PARAM = "$business_address2";
	private static final String BUSINESS_CITY_PARAM = "$business_city";
	private static final String BUSINESS_STATE_ID_PARAM = "$business_state_id";
	private static final String BUSINESS_POSTAL_CODE_PARAM = "$business_postal_code";
	private static final String BUSINESS_COUNTRY_ID_PARAM = "$business_country_id";
	private static final String BUSINESS_PHONE1_PARAM = "$business_phone1";
	private static final String MAX_USERS_PARAM = "$max_users";
	private static final String EXPIRY_DATE_PARAM = "$expiry_date";
	private static final String ACCOUNT_ID_LIST_PARAM = "$account_id_list";
	private static final String SOURCE_ID_PARAM = "$source_id";
	private static final String COUNTRY_ID_PARAM = "$country_id";
	private static final String NEW_OWNER_ID_PARAM = "$new_owner_id";
	private static final String MIN_DEPOSIT_AMOUNT_PARAM = "$min_deposit_amount";
	private static final String MAX_DEPOSIT_AMOUNT_PARAM = "$max_deposit_amount";
	private static final String INCLUDE_DEPOSIT_BOUNDS_PARAM = "$include_deposit_bounds";

	public static JSONArray getAccounts(Long userId, int productId,
			boolean includeNonActive, Integer top, Date fromDate,
			Date toDate, Long[] accountIds) throws DAOException {
		
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(PRODUCT_ID_PARAM, productId),
					new SqlParam(INCLUDE_NON_ACTIVE_PARAM, includeNonActive),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(FROM_INSERT_DATE_PARAM, fromDate),
					new SqlParam(TO_INSERT_DATE_PARAM, toDate),
					new SqlParam(ACCOUNT_ID_LIST_PARAM, StringUtils.join(accountIds, ",")),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getAccounts", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
        	List<JSONObject> list = DSL.using(connection).fetch(resultSet)
			.map(r -> {
				JSONObject obj = new JSONObject();
				
				for(var field : r.fields()) {
					obj.put(field.getName(), r.getValue(field));
				}
				return obj;
			});
        	
            return new JSONArray(list);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
		
	}

	public static void getAccount(Long accountId, IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getAccount", params);
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

	public static void getAccountDetails(long accountId,
			IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);       	
        	call = factory.GetProcedureCall("getAccountDetails", params);
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
	
	public static void setAccountDetails(IAccountDetailsChangeRequest userDetails) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, userDetails.getId()),
					new SqlParam(COMMENTS_PARAM, userDetails.getComments()),
					new SqlParam(TIMEZONE_ID_PARAM, userDetails.getTimezoneId()),
					new SqlParam(ACCOUNT_NAME_PARAM, userDetails.getName()),
					new SqlParam(IS_ACTIVE_PARAM, userDetails.isActive()),
					new SqlParam(OWNER_ID_PARAM, userDetails.getOwnerId()),
					new SqlParam(INCLUDE_DEPOSIT_BOUNDS_PARAM, null != userDetails.getDebositBounds()),
					new SqlParam(MAX_DEPOSIT_AMOUNT_PARAM, (null == userDetails.getDebositBounds() ? null : userDetails.getDebositBounds().getMaxDepositAmount())),
					new SqlParam(MIN_DEPOSIT_AMOUNT_PARAM, (null == userDetails.getDebositBounds() ? null : userDetails.getDebositBounds().getMinDepositAmount())),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setAccountDetails", params);
        	connection = call.getConnection();
            call.execute();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static OperationResult<Double> getFreeBalance(long accountId,
			Long userId) throws DAOException {
		OperationResult<Double> result = null;
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(USER_ID_PARAM, userId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getFreeBalance", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            if(resultSet.next()){
            	result = new OperationResult<Double>(resultSet.getDouble("free_balance"));
            } else {
            	result = new OperationResult<Double>(ErrorCode.NoResults);
            }
            return result;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static void changeBalance(IChangeBalanceRequest request, IResultSetCallback callback) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
					new SqlParam(BACKOFFICE_USER_ID_PARAM, request.getBackofficeUserId()),
					new SqlParam(COMMENTS_PARAM, request.getComments()),
					new SqlParam(AMOUNT_PARAM, request.getAmount()),
					new SqlParam(ACCOUNT_OPERATION_TYPE_ID_PARAM, request.getAccountOperationTypeId()),
					new SqlParam(SOURCE_GUID_PARAM, request.getSourceGuid()),
					new SqlParam(SESSION_ID_PARAM, request.getSessionId()),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, request.getGeoCountryCode()),
					new SqlParam(CLIENT_IP_PARAM, request.getClientIp()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("changeBalance", params);
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

	public static void changeServicePackage(long accountId, int servicePackageId,
			Date servicePackageExpiryDate, Integer maxUsers) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(SERVICE_PACKAGE_ID_PARAM, servicePackageId),
					new SqlParam(SERVICE_PACKAGE_EXPIRY_DATE_PARAM, servicePackageExpiryDate),
					new SqlParam(MAX_USERS_PARAM, maxUsers),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setAccountServicePackage", params);
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

	public static JSONArray getAccountsWithExpiredServicePackages(Date expiryDate) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(EXPIRY_DATE_PARAM, expiryDate),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getAccountsWithExpiredServicePackages", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            List<JSONObject> list = DSL.using(connection).fetch(resultSet)
			.map(r -> {
				JSONObject obj = new JSONObject();
				
				for(var field : r.fields()) {
					obj.put(field.getName(), r.getValue(field));
				}
				return obj;
			});
        	
            return new JSONArray(list);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void getAccountBusinessDetails(long accountId,
			IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);       	
        	call = factory.GetProcedureCall("getAccountBusinessDetails", params);
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
	
	public static void setAccountBusinessDetails(IAccountBusinessDetailsChangeRequest request) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, request.getId()),
					new SqlParam(BUSINESS_COMPANY_NAME_PARAM, request.getCompanyName()),
					new SqlParam(BUSINESS_FIRST_NAME_PARAM, request.getFirstName()),
					new SqlParam(BUSINESS_LAST_NAME_PARAM, request.getLastName()),
					new SqlParam(BUSINESS_ADDRESS1_PARAM, request.getAddress1()),
					new SqlParam(BUSINESS_ADDRESS2_PARAM, request.getAddress2()),
					new SqlParam(BUSINESS_CITY_PARAM, request.getCity()),
					new SqlParam(BUSINESS_STATE_ID_PARAM, request.getStateId()),
					new SqlParam(BUSINESS_POSTAL_CODE_PARAM, request.getPostalCode()),
					new SqlParam(BUSINESS_COUNTRY_ID_PARAM, request.getCountryId()),
					new SqlParam(BUSINESS_PHONE1_PARAM, request.getPhone1()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setAccountBusinessDetails", params);
        	connection = call.getConnection();
            call.execute();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void attachUserAccount(int sourceId, long accountId,
			Long userId, Long backofficeUserId, IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(BACKOFFICE_USER_ID_PARAM, backofficeUserId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("attachUserAccount", params);
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

	public static void detachUserAccount(int sourceId, long accountId,
			Long userId, Long backofficeUserId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(BACKOFFICE_USER_ID_PARAM, backofficeUserId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("detachUserAccount", params);
        	connection = call.getConnection();
            call.execute();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void createUserAccount(int sourceId, int productId,
			long userId, Long backofficeUserId, String accountName,
			String clientIp, int servicePackageId,
			Integer countryId, IResultSetCallback callback) throws DAOException {
		
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(SOURCE_ID_PARAM, sourceId),
					new SqlParam(PRODUCT_ID_PARAM, productId),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(BACKOFFICE_USER_ID_PARAM, backofficeUserId),
					new SqlParam(ACCOUNT_NAME_PARAM, accountName),
					new SqlParam(CLIENT_IP_PARAM, clientIp),
					new SqlParam(SERVICE_PACKAGE_ID_PARAM, servicePackageId),
					new SqlParam(COUNTRY_ID_PARAM, countryId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("createUserAccount", params);
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

	public static void changeAccountOwner(long accountId, long newOwnerId,
			Long backofficeUserId, int sourceId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
					new SqlParam(NEW_OWNER_ID_PARAM, newOwnerId),
					new SqlParam(BACKOFFICE_USER_ID_PARAM, backofficeUserId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("changeAccountOwner", params);
        	connection = call.getConnection();
            call.execute();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void changeAccountBillingSettings(
			IAccountBillingSettingsChangeRequest request) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, request.getId()),
					new SqlParam(MAX_DEPOSIT_AMOUNT_PARAM, (null == request.getDebositBounds() ? null : request.getDebositBounds().getMaxDepositAmount())),
					new SqlParam(MIN_DEPOSIT_AMOUNT_PARAM, (null == request.getDebositBounds() ? null : request.getDebositBounds().getMinDepositAmount())),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setAccountBillingSettings", params);
        	connection = call.getConnection();
            call.execute();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
