package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider;
import com.inqwise.opinion.infrastructure.systemFramework.GeoIpManager;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.common.users.IUserView;
import com.inqwise.opinion.library.common.users.LoginProvider;
import com.inqwise.opinion.library.dao.AccountsDataAccess;
import com.inqwise.opinion.library.dao.UsersDataAccess;
import com.inqwise.opinion.library.entities.accounts.AccountEntity;


public class UserEntity implements IUser, IUserView{

	static ApplicationLog logger = ApplicationLog.getLogger(UserEntity.class);
	
	private Long userId;
	private String userName;
	private String displayName;
	private String email;
	private Boolean sendNewsletters;
	private Hashtable<Integer, List<IAccount>> accountsByProduct;
	private String countryName;
	private Integer countryId;
	private Date insertDate;
	private LoginProvider provider;
	private String userExternalId;
	private Long gatewayId;
	
	public UserEntity(ResultSet reader) throws SQLException{
		userId = Long.valueOf(reader.getLong("user_id"));
		userName = reader.getString("user_name");
		displayName = reader.getString("display_name");
		email = reader.getString("email");
		sendNewsletters = reader.getBoolean("send_newsletters");
		setCountryId(ResultSetHelper.optInt(reader, "country_id"));
		setCountryName(ResultSetHelper.optString(reader, "country_name"));
		setInsertDate(ResultSetHelper.optDate(reader, "insert_date"));
		accountsByProduct = new Hashtable<Integer, List<IAccount>>();
		setProvider(LoginProvider.fromInt(ResultSetHelper.optInt(reader, "provider_id", LoginProvider.Inqwise.getValue())));
		setUserExternalId(ResultSetHelper.optString(reader, "user_external_id"));
		setGatewayId(ResultSetHelper.optLong(reader, "gateway_id"));
	}
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	public String getEmail(){
		return email;
	}
	
	public Boolean getSendNewsLetters(){
		return sendNewsletters;
	}
	
	// Override
	
	/**
     * The user ID is unique for each User. So this should compare Users by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
	public boolean equals(Object other) {
		return other instanceof UserEntity && userId != null && userId.equals(((UserEntity)other).userId);
	}
	
	/**
     * The user ID is unique for each User. So Users with same ID should return same hashcode.
     * @see java.lang.Object#hashCode()
     */
	public int hashCode() {
		return userId != null ? this.getClass().hashCode() + userId.hashCode() : super.hashCode();
	}
	
	public BaseOperationResult resetPassword(String password, String clientIp, int sourceId, Long backofficeUserId, Date passwordExpiryDate, String comments){
		
		BaseOperationResult result;
		try{
			
			// get geoCountry
			String countryCode = GeoIpManager.getInstance().getCountryCode(clientIp);
			
			// reset password
			if(UsersDataAccess.resetPassword(userId, password, clientIp, countryCode, sourceId, backofficeUserId, passwordExpiryDate, comments)){
				result = BaseOperationResult.Ok;
			} else {
				logger.error("Failed to reset password for user " + userId);
				result = new BaseOperationResult(ErrorCode.ArgumentWrong);
			}
			
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "resetPassword() : unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public BaseOperationResult sendHtmlMail(String subject, String content){
		BaseOperationResult result;
		try {
			EmailProvider.getInstance().sendHtml(null, getEmail(), subject, content);
			result = new BaseOperationResult(ErrorCode.NoError);
		} catch (Exception e) {

			UUID errorId = logger.error(e, "sendMail({0}, {1}) : Unexpected error occured.", subject, content);
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public void addAccount(IAccount account) {
		List<IAccount> accounts = accountsByProduct.get(account.getProductId());
		if(null == accounts){
			accounts = new ArrayList<IAccount>();
			accountsByProduct.put(account.getProductId(), accounts);
		}
		accounts.add(account);
	}
	
	public void setNoAccounts(int productId){
		accountsByProduct.put(productId, new ArrayList<IAccount>());
	}
	
	public OperationResult<List<IAccount>> getAccounts(int productId) {
		OperationResult<List<IAccount>> result = new OperationResult<List<IAccount>>();
		List<IAccount> accounts = accountsByProduct.get(productId);
		if(null == accounts){
			//TODO: load accounts
			logger.error("User without accounts. userId: '%s', productId: '%s'", getUserId(), productId);
			throw new NullPointerException("Load accounts not implemented");
			//accounts = new ArrayList<IAccount>();
			//accountsByProduct.put(productId, accounts);
		} else {
			result.setValue(accounts);
		}
		return result;
	}

	public static OperationResult<IUser> getUser(String userName, Long userId, LoginProvider provider, String userExternalId) {
		final OperationResult<IUser> result = new OperationResult<IUser>();
		
		try {
			
			IResultSetCallback callback = new IResultSetCallback() {
				public void call(ResultSet reader, int generationId) throws Exception {
					while (reader.next()) {
						switch(generationId){
						case 0: //user
							if(result.hasValue()){
								throw new Exception("getUser(name) : More than one record receaved");
							} else {
								result.setValue(new UserEntity(reader));
							}
							break;
						default:
							throw new OperationNotSupportedException("getUser(name) : generationId not supported. Value: '" + generationId);
						}
		            }
				}				
			};
			
			UsersDataAccess.getUserReader(userName, userId, callback, null == provider ? null : provider.getValue(), userExternalId);

			if(!result.hasValue()){
				result.setError(ErrorCode.UserNotExist);
			}
			
		} catch(DAOException e) {
			e.printStackTrace();
			UUID errorId = logger.error(e, "getUser() : Unexpacted error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public boolean hasUnitializedAccountsList(int productId) {
		return null == accountsByProduct.get(productId);
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public String getCultureCode() {
		return "en-us";
	}

	public IAccount getDefaultAccount(int productId) {
		List<IAccount> accounts = accountsByProduct.get(productId);
		if(null == accounts){
			//TODO: load accounts
			logger.error("User without accounts. userId: '%s', productId: '%s'", getUserId(), productId);
			throw new NullPointerException("Load accounts not implemented");
		} 
		
		return getDefaultAccount(accounts);
	}

	private IAccount getDefaultAccount(List<IAccount> accounts) {
		IAccount firstAccount = accounts.get(0);
		for (IAccount account : accounts) {
			if(null != account.getOwnerId() && 0 == Long.compare(account.getOwnerId(), getUserId())){
				return account;
			}
		}
		
		return firstAccount;
	}

	public LoginProvider getProvider() {
		return provider;
	}

	public void setProvider(LoginProvider provider) {
		this.provider = provider;
	}

	public String getUserExternalId() {
		return userExternalId;
	}

	public void setUserExternalId(String userExternalId) {
		this.userExternalId = userExternalId;
	}

	public Long getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Long gatewayId) {
		this.gatewayId = gatewayId;
	}
	
	public BaseOperationResult attachAccount(int sourceId, long accountId, Long backofficeUserId){
		final OperationResult<IAccount> result = new OperationResult<IAccount>();
		try {
			IResultSetCallback callback = new IResultSetCallback() {
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							AccountEntity account = new AccountEntity(reader);
							result.setValue(account);							
						}
					}
				}
			};
			AccountsDataAccess.attachUserAccount(sourceId, accountId, getUserId(), backofficeUserId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
			
			if(result.hasValue()){
				IAccount account = result.getValue();
				List<IAccount> accounts = accountsByProduct.get(account.getProductId());
				if(null == accounts){
					accounts = new ArrayList<IAccount>();
					accountsByProduct.put(account.getProductId(), accounts);
				}
				accounts.add(account);
			}
			
		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex, "attachAccount : unexpected error occured");
			result.setError(ErrorCode.GeneralError, errorTicket);
		}
		return result;
	}
	
	public BaseOperationResult detachAccount(int sourceId, long accountId, Long backofficeUserId){
		BaseOperationResult result;
		try {
			AccountsDataAccess.detachUserAccount(sourceId, accountId, getUserId(), backofficeUserId);
			
			result = BaseOperationResult.Ok;
		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex, "attachAccount : unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorTicket);
		}
		return result;
	}
	
	public OperationResult<IAccount> createAccount(int sourceId, int productId, String accountName, String clientIp, int servicePackageId, Long backofficeUserId){
		final OperationResult<IAccount> result = new OperationResult<IAccount>();
		try {
			
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							AccountEntity account = new AccountEntity(reader);
							result.setValue(account);							
						}
					}
				}
			};
			
			AccountsDataAccess.createUserAccount(sourceId, productId, getUserId(), backofficeUserId, accountName, clientIp, servicePackageId, getCountryId(), callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
			
			if(result.hasValue()){
				IAccount account = result.getValue();
				List<IAccount> accounts = accountsByProduct.get(account.getProductId());
				if(null == accounts){
					accounts = new ArrayList<IAccount>();
					accountsByProduct.put(account.getProductId(), accounts);
				}
				accounts.add(account);
			}
			
		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex, "createAccount : unexpected error occured");
			result.setError(ErrorCode.GeneralError, errorTicket);
		}
		return result;
	}

	@Override
	public OperationResult<IAccount> getAccount(int productId, Long accountId,
			boolean defaultIfNotFound) {
		
		OperationResult<IAccount> result = new OperationResult<IAccount>();
		List<IAccount> accounts = accountsByProduct.get(productId);
		
		if(null == accounts){
			UUID errorId = logger.error("User without accounts. userId: '%s', productId: '%s'", getUserId(), productId);
			result.setError(ErrorCode.NotAllowedForProduct, errorId);
		}
		
		if(!result.hasError()) { 
			if(null == accountId) {
				result.setValue(getDefaultAccount(accounts));
			} else {
				for (IAccount account : accounts) {
					if(0 == Long.compare(account.getId(), accountId)){
						result.setValue(account);
					}
				}
			}
		}
		
		if(!result.hasError() && !result.hasValue()){
			UUID errorId = logger.error("User #%s not contains account #%s for product '%s'", getUserId(), productId, accountId);
			result.setError(ErrorCode.InvalidAccount, errorId);
		}
		
		return result;
	}
}
