
package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;

import org.brickred.socialauth.SocialAuthManager;
import org.javatuples.KeyValue;
import org.javatuples.Pair;

import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.events.LoginEventArgs;
import com.inqwise.opinion.automation.common.events.RegistrationEventArgs;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.actions.ExternalLoginAction;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.ISession;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.invites.IInvite;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.common.users.ICompleteLoginResult;
import com.inqwise.opinion.library.common.users.IRegisterDetails;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.common.users.IUserDetails;
import com.inqwise.opinion.library.common.users.IUserDetailsChangeRequest;
import com.inqwise.opinion.library.common.users.IUserView;
import com.inqwise.opinion.library.common.users.LoginProvider;
import com.inqwise.opinion.library.dao.UsersDataAccess;
import com.inqwise.opinion.library.entities.SessionEntity;
import com.inqwise.opinion.library.entities.UserDetailsEntity;
import com.inqwise.opinion.library.entities.UserEntity;
import com.inqwise.opinion.library.entities.accounts.AccountEntity;
import com.inqwise.opinion.library.systemFramework.GeoIpManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;



public final class UsersManager {
	
	static ApplicationLog logger = ApplicationLog.getLogger(UsersManager.class);
	
	private static <T> void fillUser(
			final String userName,
			final OperationResult<T> result,
			final EntityBox<UserEntity> userBox, ResultSet reader)
			throws SQLException, Exception,
			OperationNotSupportedException {
		Integer errorCode = ResultSetHelper.optInt(reader, "error_code", 0);
		switch(errorCode){
		case 0:
			if(userBox.hasValue()){
				throw new Exception("fillUser() : More than one record receaved");
			} else {
				userBox.setValue(new UserEntity(reader));
			}
			break;
		case 1: // Invalid Username or Password
			logger.warn("Invalid Username or Password. UserName: '%s'", userName);
			result.setError(ErrorCode.InvalidUsernameOrPassword);
			break;
		case 2: // Password Expire
			logger.warn("Password Expiry. UserName: '%s'", userName);
			result.setError(ErrorCode.PasswordExpiry);
			break;
		case 3: // UserName is already defined
			logger.warn("Username already exist. UserName: '%s'", userName);
    		result.setError(ErrorCode.UsernameAlreadyExist);
    		break;
    	case 4: // Email is already defined
    		logger.warn("Email already exist. UserName: '%s'", userName);
    		result.setError(ErrorCode.EmailAlreadyExist);
    		break;
    	case 5: // Account is disabled
    		logger.warn("Account disabled. Username: '%s'", userName);
    		result.setError(ErrorCode.AccountDisabled);
    		break;
		default: // Unexpected Error
			throw new OperationNotSupportedException("fillUser() : errorCode not supported. Value: '" + errorCode);
		}
	}
	
	public static OperationResult<ISession> login(final String userName, String password, String newPassword, String clientIp,
			int sourceId, int productId, boolean persist){
		final OperationResult<ISession> result = new OperationResult<ISession>();
		final EntityBox<UserEntity> userBox = new EntityBox<UserEntity>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				public void call(ResultSet reader, int generationId) throws Exception {
					while (reader.next()) {
						switch (generationId) {
						case 0: /* user */
							fillUser(userName, result, userBox, reader);
							break;
						case 1: /* accounts */
							IAccount account = new AccountEntity(reader);
							userBox.getValue().addAccount(account);
							break;
						default:
							throw new OperationNotSupportedException("login() : generationId not supported. Value: '" + generationId);
						}
		            }
				}				
			};
			
			// get geoCountry
			String countryCode = GeoIpManager.getInstance().getCountryCode(clientIp);
			
			// generate sessionId
			UUID sessionId = SessionEntity.generateSessionId();
			UsersDataAccess.login(userName, password, newPassword, clientIp,
											countryCode, sessionId, sourceId, productId, callback);
			
			UserEntity user = null;
			if(!result.hasError()){
				if(userBox.hasValue()){
					user = userBox.getValue();
				} else {
					throw new Exception("login() : No results");
				}
			}
			
			if(!result.hasError()){
				if(user.hasUnitializedAccountsList(productId)){
					logger.warn("User '%s' no have accounts for product '%s'", userName, productId);
					user.setNoAccounts(productId);
				}
			}
			
			List<IAccount> accounts = null;
			if(!result.hasError()){
				OperationResult<List<IAccount>> accountsResult = user.getAccounts(productId);
				if(accountsResult.hasError()){
					result.setError(accountsResult);
				} else {
					accounts = accountsResult.getValue();
					
					// check for account
					if(accounts.size() == 0){
						logger.warn("User '%s' trying to login into product '%s' without account.", userName, productId);
						result.setError(ErrorCode.NotAllowedForProduct);
					}
				}
			}
			
			if(!result.hasError()){
				SessionEntity session = new SessionEntity(sessionId, user, clientIp, persist);
				result.setValue(session);
				
				// Send Login Event
				try {
					EventsServiceClient.getInstance().fire(new LoginEventArgs(sourceId, productId, user.getUserName(), user.getUserId(), GeoIpManager.getInstance().getCountryName(clientIp), user.getEmail(), countryCode, sessionId.toString(), clientIp, false));
				} catch (Exception e) {
					logger.error(e, "failed to fireLoginEvent");
				}
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "login() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<String> getLoginUrl(LoginProvider provider, String returnUrl, String clientIp, int sourceId, int productId, Long gatewayId, Integer servicePackageId, Integer countOfMonths, boolean isPersist, String inviteExternalId){
		OperationResult<String> result = new OperationResult<String>();
		try {
			ExternalLoginAction loginAction = generateExternalLoginAction();
			loginAction.setCountOfMonths(countOfMonths);
			loginAction.setReturnUrl(returnUrl);
			loginAction.setServicePackageId(servicePackageId);
			loginAction.setIsPersistLogin(isPersist);
			loginAction.setInviteExternalId(inviteExternalId);
			result = loginAction.generateLoginUrl(provider, clientIp, sourceId, productId, gatewayId);
			
		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex, "getLoginUrl : unexpected error occured");
			result.setError(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}

	private static ExternalLoginAction generateExternalLoginAction() throws Exception {
		ExternalLoginAction action = new ExternalLoginAction();
		getExternalLoginActionsCache().put(action.getKey(), action);
		
		return action;
	}

	private static Cache<String, ExternalLoginAction> _externalLoginActionsCache;
	private static Cache<String, ExternalLoginAction> getExternalLoginActionsCache(){
		if(null == _externalLoginActionsCache){
			synchronized (SocialAuthManager.class) {
				if(null == _externalLoginActionsCache){
					_externalLoginActionsCache = CacheBuilder.newBuilder().
							maximumSize(100).
							expireAfterWrite(5, TimeUnit.MINUTES).
							build();
				}
			}
		}
		
		return _externalLoginActionsCache;
	}
	
	private static ExternalLoginAction getExternalLoginAction(String key){
		return getExternalLoginAction(key, false);
	}
	
	private static ExternalLoginAction getExternalLoginAction(String key, boolean invalidate) {
		
		Cache<String, ExternalLoginAction> externalLoginActionsCache = getExternalLoginActionsCache();
		ExternalLoginAction result = externalLoginActionsCache.getIfPresent(key);
		if(null != result){
			externalLoginActionsCache.invalidate(key);
		}
		return result;
	}
	
	private static ExternalLoginAction getOrAddExternalLoginAction(String key) throws ExecutionException {
		
		return getExternalLoginActionsCache().get(key, new Callable<ExternalLoginAction>() {

			public ExternalLoginAction call() throws Exception {
				return new ExternalLoginAction();
			}
		});
	}
	
	private static Object _usersByIdAndProductCacheLocker = new Object(); 
	private static LoadingCache<Pair<Long, Integer>, OperationResult<IUser>> _usersByIdAndProductCache;
	private static LoadingCache<Pair<Long, Integer>, OperationResult<IUser>> getUsersByIdAndProductCache(){
		if(null == _usersByIdAndProductCache){
			synchronized (_usersByIdAndProductCacheLocker) {
				if(null == _usersByIdAndProductCache){
					_usersByIdAndProductCache = CacheBuilder.newBuilder().
							maximumSize(1000).
							expireAfterWrite(10, TimeUnit.SECONDS).
							
							build( new CacheLoader<Pair<Long, Integer>, OperationResult<IUser>>() {
								public OperationResult<IUser> load(Pair<Long, Integer> key) throws Exception {
									return getUserInternal(key.getValue0().longValue(), key.getValue1().intValue());
								}
							});
				}
			}
		}
		
		return _usersByIdAndProductCache;
	}
	
	public static OperationResult<IUser> getUser(long userId, int productId) throws ExecutionException {
		return getUsersByIdAndProductCache().get(Pair.with(userId, productId));
	}

	private static OperationResult<IUser> getUserInternal(long userId,
			int productId) {
		final OperationResult<IUser> result = new OperationResult<IUser>();
				
		try {
			
			IResultSetCallback callback = new IResultSetCallback() {
				public void call(ResultSet reader, int generationId) throws Exception {
					while (reader.next()) {
						switch(generationId){
						case 0: //user
							if(result.hasValue()){
								throw new Exception("getUser() : More than one record receaved");
							} else {
								result.setValue(new UserEntity(reader));
							}
							break;
						case 1: //accounts
							AccountEntity account = new AccountEntity(reader);
							// set owner
							if(account.getOwnerId() == result.getValue().getUserId()){
								account.setOwner(result.getValue());
							}
							((UserEntity)result.getValue()).addAccount(account);
							break;
						default:
							throw new OperationNotSupportedException("getUser() : generationId not supported. Value: '" + generationId);
						}
		            }
				}				
			};
			
			UsersDataAccess.getUserByProductReader(userId, productId, callback);

			if(!result.hasValue()){
				result.setError(ErrorCode.UserNotExist);
			}
			
			if(!result.hasError()){
				UserEntity user = (UserEntity)result.getValue();
				if(user.hasUnitializedAccountsList(productId)){
					logger.warn("User #'%s' no have accounts for product '%s'", userId, productId);
					user.setNoAccounts(productId);
				}
			}
			
		} catch(DAOException e) {
			e.printStackTrace();
			UUID errorId = logger.error(e, "getUser() : Unexpacted error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<ICompleteLoginResult> completeLogin(String key, HttpServletRequest request, String clientIp){
		
		OperationResult<ICompleteLoginResult> result = null;
		try{
			if(null == key){
				result = new OperationResult<ICompleteLoginResult>(ErrorCode.ArgumentNull);
			}
			
			if(null == result){
				ExternalLoginAction action = getExternalLoginAction(key, true);
				if(null == action){
					result = new OperationResult<ICompleteLoginResult>(ErrorCode.NoResults);
				} else {
					result = action.completeLogin(request, clientIp);
				}
			}
		} catch(Exception ex){
			UUID errorTicket = logger.error(ex, "completeLogin: Failed to complete login");
			result = new OperationResult<ICompleteLoginResult>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}

	private static Object _usersByIdCacheLocker = new Object(); 
	private static LoadingCache<Long, OperationResult<IUser>> _usersByIdCache;
	private static LoadingCache<Long, OperationResult<IUser>> getUsersByIdCache(){
		if(null == _usersByIdCache){
			synchronized (_usersByIdCacheLocker) {
				if(null == _usersByIdCache){
					_usersByIdCache = CacheBuilder.newBuilder().
							maximumSize(1000).
							expireAfterWrite(20, TimeUnit.SECONDS).
							build( new CacheLoader<Long, OperationResult<IUser>>() {
								public OperationResult<IUser> load(Long userId) throws Exception {
									return UserEntity.getUser(null, userId, null, null);
								}
							});
				}
			}
		}
		
		return _usersByIdCache;
	}
	
	public static OperationResult<IUser> getUser(String userName, Long userId, LoginProvider provider, String userExternalId) {
		
		OperationResult<IUser> result = null;
		try {
			if(null != userId && null == userName && null == provider && null == userExternalId){
				result = getUsersByIdCache().get(userId);
			} else {
				result = UserEntity.getUser(userName, userId, provider, userExternalId);
			}
		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex, "getUser: Unexpected error occured");
			result = new OperationResult<IUser>(ErrorCode.GeneralError, errorTicket);			
		}
		
		return result;
	}
	
	public static OperationResult<IRegisterDetails> register(final String userName, final String password, final String email,
			boolean subscribeToNewsLetters, String clientIp, String displayName, int sourceId, 
			final IProduct product,	Long gatewayId, int servicePackageId, int countOfMonths, String inviteExternalId){
		
		final OperationResult<IRegisterDetails> result = new OperationResult<IRegisterDetails>();
		
		try {
			
			IServicePackage servicePackage = null;
			ICharge charge = null;
			if (!result.hasError()){
				servicePackage = product.getServicePackage(servicePackageId);
			}
			
			IInvite invite = null;
			Long accountId = null;
			if (!result.hasError() && null != inviteExternalId){
				OperationResult<IInvite> inviteResult = InvitesManager.getInvite(inviteExternalId);
				if(inviteResult.hasError()){
					result.setError(inviteResult, KeyValue.with(ErrorCode.NoResults, ErrorCode.InvalidInvite));
				} else {
					invite = inviteResult.getValue();
					if(!invite.getEmail().equalsIgnoreCase(email)){
						result.setError(ErrorCode.InvalidInvite);
					} else {
						accountId = invite.getAccountId();
					}
				}
			}
			
			String countryCode = null;
			final EntityBox<UserEntity> userBox = new EntityBox<UserEntity>();
			if(!result.hasError()){
				IResultSetCallback callback = new IResultSetCallback() {
					
					public void call(ResultSet reader, int generationId) throws Exception {
						switch (generationId) {
						case 0: // user or error
							while(reader.next()){
								fillUser(userName, result, userBox, reader);
							}
							break;
						case 1: // accounts
							while(reader.next()){
								IAccount account = new AccountEntity(reader);
								userBox.getValue().addAccount(account);
							}
							break;
						default:
							throw new OperationNotSupportedException("register() : generationId not supported. Value: '" + generationId);
						}
						
					}
				};
				
				// get geoCountry
				countryCode = GeoIpManager.getInstance().getCountryCode(clientIp);
				
				// register
				UsersDataAccess.register(userName, password, email,
										subscribeToNewsLetters, clientIp, gatewayId,
										displayName, sourceId, countryCode, accountId,
										product.getId(),
										(servicePackage.getAmount() > 0 ? product.getDefaultServicePackage().getId() : servicePackageId),
										callback);
			}
			
			UserEntity user = null;
			if(!result.hasError()){
				user = userBox.getValue();
			}
			
			if(!result.hasError() && null != invite){
				// delete invite
				InvitesManager.deleteInvite(invite.getId(), invite.getAccountId());
			}
			
			// Sent event
			if(!result.hasError()){
				try{
					RegistrationEventArgs args = new RegistrationEventArgs(sourceId, user.getUserId(), user.getUserName(), GeoIpManager.getInstance().getCountryName(clientIp), product.getId(), user.getEmail());
					EventsServiceClient.getInstance().fire(args);
				} catch (Exception e) {
					logger.error(e, "failed to fireRegstrationEvent");
				}
			}
			
			if(!result.hasError()){
				if(user.hasUnitializedAccountsList(product.getId())){
					logger.warn("User '%s' no have accounts for product '%s'", userName, product.getId());
					user.setNoAccounts(product.getId());
				}
			}
			
			// Create charge
			if(!result.hasError()){
				if(servicePackage.getAmount() > 0){
					OperationResult<ICharge> chargeResult = ServicePackagesManager.createCharge(servicePackage, countOfMonths, user.getDefaultAccount(product.getId()), user.getUserId(), false);
					if(chargeResult.hasError()){
						result.setError(chargeResult);
					} else {
						charge = chargeResult.getValue();
					}
				}
			}
			
			if(!result.hasError()){
				
				result.setValue(createRegisterDetails(user, charge));
			}
			
		} catch (Exception e) {
			
			UUID errorId = logger.error(e, "register() : Unexpacted error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	private static IRegisterDetails createRegisterDetails(final IUserView user,
			final ICharge charge) {
		return new IRegisterDetails() {
			
			public IUser getUser() {
				return user;
			}
			
			public ICharge getCharge() {
				return charge;
			}
		};
	}

	public static OperationResult<List<IUserView>> getUsers(int top, int productId, Date fromDate, Date toDate,
															Long accountId) {
		final OperationResult<List<IUserView>> result = new OperationResult<List<IUserView>>();
		final List<IUserView> users = new ArrayList<IUserView>();
		
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						IUserView user = new UserEntity(reader);
						users.add(user);
					}
				}
			};
			
			UsersDataAccess.getUsers(top, productId, callback, fromDate, toDate, accountId);
			
			if(!result.hasError() && users.size() == 0){
				result.setError(ErrorCode.NoResults);
			}
			
			if(!result.hasError()){
				result.setValue(users);
			}
		} catch (Exception e) {
			
			UUID errorId = logger.error(e, "getUsers() : Unexpacted error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<IUserDetails> getUserDetails(long userId){
		final OperationResult<IUserDetails> result = new OperationResult<IUserDetails>();
		try {
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(result.hasValue()){
							throw new Exception("getUserDetails() : More than one record receaved");
						}
						IUserDetails userDetails = new UserDetailsEntity(reader);
						result.setValue(userDetails);
					}
				}
			};
			
			UsersDataAccess.getUserDetails(userId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
			
		} catch (Exception e) {
			UUID errorId = logger.error(e, "getUserDetails() : Unexpacted error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult setUserDetails(IUserDetailsChangeRequest userDetails){
		final BaseOperationResult result = new BaseOperationResult();
		try {
			UsersDataAccess.setUserDetails(userDetails);
		} catch (Exception e) {
			UUID errorId = logger.error(e, "setUserDetails() : Unexpacted error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
}
