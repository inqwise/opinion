package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.users.IUserDetailsChangeRequest;
import com.inqwise.opinion.library.managers.UsersManager;


public final class UsersDataAccess extends DAOBase {
	
	static ApplicationLog logger = ApplicationLog.getLogger(UsersManager.class);
	
    private static final String USERNAME_PARAM = "$user_name";
    private static final String PASSWORD_PARAM = "$password";
    private static final String EMAIL_PARAM = "$email";
    private static final String SUBSCRIBE_TO_NEWS_LETTERS_PARAM = "$send_newsletters";
    private static final String CLIENT_IP_PARAM = "$client_ip";
    private static final String GATEWAY_ID_PARAM = "$gateway_id";
    private static final String DISPLAY_NAME_PARAM = "$display_name";
    private static final String SESSION_ID_PARAM = "$session_id";
    private static final String NEW_PASSWORD_PARAM = "$new_password";
    private static final String SOURCE_ID_PARAM = "$source_id";
    private static final String PRODUCT_ID_PARAM = "$product_id";
    private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String GEO_COUNTRY_CODE_PARAM = "$geo_country_code";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String SERVICE_PACKAGE_ID_PARAM = "$service_package_id";
	private static final String USER_NAME_PARAM = "$user_name";
	private static final String TOP_PARAM = "$top";
	private static final String ADDRESS1_PARAM = "$address1";
	private static final String ADDRESS2_PARAM = "$address2";
	private static final String CITY_PARAM = "$city";
	private static final String COMMENTS_PARAM = "$comments";
	private static final String COUNTRY_ID_PARAM = "$country_id";
	private static final String FIRST_NAME_PARAM = "$first_name";
	private static final String LAST_NAME_PARAM = "$last_name";
	private static final String PHONE1_PARAM = "$phone1";
	private static final String POSTAL_CODE_PARAM = "$postal_code";
	private static final String STATE_ID_PARAM = "$state_id";
	private static final String TITLE_PARAM = "$title";
	private static final String SEND_NEWSLETTERS_PARAM = "$send_newsletters";
	private static final String FROM_INSERT_DATE_PARAM = "$from_insert_date";
	private static final String TO_INSERT_DATE_PARAM = "$to_insert_date";
	private static final String BACKOFFICE_USER_ID_PRAM = "$backoffice_user_id";
	private static final String PASSWORD_EXPIRY_DATE_PARAM = "$password_expiry_date";
	private static final String PROVIDER_ID_PARAM = "$provider_id";
	private static final String USER_EXTERNAL_ID_PARAM = "$user_external_id";
    
	
    public static void getUserByProductReader(long userId, int productId, IResultSetCallback callback) throws DAOException {
    	
    	Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(PRODUCT_ID_PARAM, productId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getUserByProduct", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            // user
            callback.call(resultSet, 0);
            
            // accounts
            if(call.getMoreResults()){
            	resultSet.close();
            	resultSet = call.getResultSet();
            	callback.call(resultSet, 1);
            }
            
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
    }
    
    /**
     * @param <T>
     * @param userName
     * @param password
     * @param clientIp
     * @param sessionId
     * @param data
     * @return
     * @throws DAOException
     */
    public static void login(String userName, String password,
    											String newPassword,
												String clientIp, String geoCountryCode,
												UUID sessionId, int sourceId,
												int productId, 
												IResultSetCallback callback) throws DAOException{

		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(USERNAME_PARAM, userName),
					new SqlParam(PASSWORD_PARAM, DAOUtil.hashMD5(password)),
					new SqlParam(CLIENT_IP_PARAM, clientIp),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, geoCountryCode),
					new SqlParam(SESSION_ID_PARAM, sessionId),
					new SqlParam(NEW_PASSWORD_PARAM, null == newPassword ? null : DAOUtil.hashMD5(newPassword)),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
					new SqlParam(PRODUCT_ID_PARAM, productId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("login", params);
        	connection = call.getConnection();
            // user
        	resultSet = call.executeQuery();
            callback.call(resultSet, 0);
            
            // accounts
            if(call.getMoreResults()){
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
    
    public static boolean resetPassword(long userId, String password,
    							String clientIp, String geoCountryCode,
    							int sourceId, Long backofficeUserId,
    							Date passwordExpiryDate, String comments) throws DAOException{
    	
    	Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(PASSWORD_PARAM, DAOUtil.hashMD5(password)),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(CLIENT_IP_PARAM, clientIp),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, geoCountryCode),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
					new SqlParam(BACKOFFICE_USER_ID_PRAM, backofficeUserId),
					new SqlParam(PASSWORD_EXPIRY_DATE_PARAM, passwordExpiryDate),
					new SqlParam(COMMENTS_PARAM, comments),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("resetPassword", params);
        	connection = call.getConnection();
            call.execute();
            return true;
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
    }
    
	public static void register(
			String userName, String password, String email,
			boolean subscribeToNewsLetters, String clientIp,
			Long gatewayId, String displayName,
			int sourceId, String geoCountryIso2Code,
			Long accountId, int productId, int servicePackageId,
			IResultSetCallback callback) throws DAOException {
		
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			
			SqlParam[] params = {
					new SqlParam(USERNAME_PARAM, userName),
					new SqlParam(PASSWORD_PARAM, DAOUtil.hashMD5(password)),
					new SqlParam(EMAIL_PARAM, email),
					new SqlParam(SUBSCRIBE_TO_NEWS_LETTERS_PARAM, subscribeToNewsLetters),
					new SqlParam(CLIENT_IP_PARAM, clientIp),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, geoCountryIso2Code),
					new SqlParam(GATEWAY_ID_PARAM, gatewayId),
					new SqlParam(DISPLAY_NAME_PARAM, displayName),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(PRODUCT_ID_PARAM, productId),
					new SqlParam(SERVICE_PACKAGE_ID_PARAM, servicePackageId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("register", params);
        	connection = call.getConnection();
        	// user
        	resultSet = call.executeQuery();
            callback.call(resultSet, 0);
            
            // accounts
            if(call.getMoreResults()){
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

	public static void getUserReader(String userName, Long userId,
			IResultSetCallback callback, Integer providerId, String userExternalId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(USER_NAME_PARAM, userName),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(PROVIDER_ID_PARAM, providerId),
					new SqlParam(USER_EXTERNAL_ID_PARAM, userExternalId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getUser", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            // user
            callback.call(resultSet, 0);
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
		
	}

	public static void getUsers(int top, int productId,
			IResultSetCallback callback, Date fromDate, Date toDate, Long accountId) throws DAOException {
		
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(PRODUCT_ID_PARAM, productId),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(FROM_INSERT_DATE_PARAM, fromDate),
					new SqlParam(TO_INSERT_DATE_PARAM, toDate),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getUsers", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            // user
            callback.call(resultSet, 0);
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}	
	}

	public static void getUserDetails(long userId, IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(USER_ID_PARAM, userId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getUserDetails", params);
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

	public static void setUserDetails(IUserDetailsChangeRequest userDetails) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(USER_ID_PARAM, userDetails.getUserId()),
					new SqlParam(ADDRESS1_PARAM, userDetails.getAddress1()),
					new SqlParam(ADDRESS2_PARAM, userDetails.getAddress2()),
					new SqlParam(CITY_PARAM, userDetails.getCity()),
					new SqlParam(COMMENTS_PARAM, userDetails.getComments()),
					new SqlParam(COUNTRY_ID_PARAM, userDetails.getCountryId()),
					new SqlParam(FIRST_NAME_PARAM, userDetails.getFirstName()),
					new SqlParam(LAST_NAME_PARAM, userDetails.getLastName()),
					new SqlParam(PHONE1_PARAM, userDetails.getPhone1()),
					new SqlParam(POSTAL_CODE_PARAM, userDetails.getPostalCode()),
					new SqlParam(STATE_ID_PARAM, userDetails.getStateId()),
					new SqlParam(TITLE_PARAM, userDetails.getTitle()),
					
					new SqlParam(DISPLAY_NAME_PARAM, userDetails.getDisplayName()),
					new SqlParam(EMAIL_PARAM, userDetails.getEmail()),
					new SqlParam(SEND_NEWSLETTERS_PARAM, userDetails.isSendNewsLetters()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setUserDetails", params);
        	connection = call.getConnection();
            call.execute();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void setExternalUser(String userName, String displayName,
			String userExternalId, String email, String clientIp,
			String countryCode, UUID sessionId, int sourceId, int productId,
			IResultSetCallback callback, String firstName, String lastName,
			int servicePackageId, Long gatewayId, int providerId, Long accountId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		
		try {
			SqlParam[] params = {
					new SqlParam(USER_NAME_PARAM, userName),
					new SqlParam(DISPLAY_NAME_PARAM, displayName),
					new SqlParam(FIRST_NAME_PARAM, firstName),
					new SqlParam(LAST_NAME_PARAM, lastName),
					new SqlParam(EMAIL_PARAM, email),
					new SqlParam(SUBSCRIBE_TO_NEWS_LETTERS_PARAM, true),
					new SqlParam(CLIENT_IP_PARAM, clientIp),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, countryCode),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
					new SqlParam(PRODUCT_ID_PARAM, productId),
					new SqlParam(SERVICE_PACKAGE_ID_PARAM, servicePackageId),
					new SqlParam(GATEWAY_ID_PARAM, gatewayId),
					new SqlParam(SESSION_ID_PARAM, sessionId),
					new SqlParam(PROVIDER_ID_PARAM, providerId),
					new SqlParam(USER_EXTERNAL_ID_PARAM, userExternalId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setExternalUser", params);
        	connection = call.getConnection();
        	callback.call(call.executeQuery(), 0);
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
		
	}
}
