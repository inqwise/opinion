package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.accounts.IAccountBillingSettingsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountDetails;
import com.inqwise.opinion.library.common.accounts.IAccountDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.accounts.IChangeBalanceRequest;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.AccountsDataAccess;
import com.inqwise.opinion.library.entities.accounts.AccountBusinessDetailsEntity;
import com.inqwise.opinion.library.entities.accounts.AccountDetailsEntity;
import com.inqwise.opinion.library.entities.accounts.AccountEntity;

public class AccountsManager {
	static ApplicationLog logger = ApplicationLog.getLogger(AccountsManager.class);
	
	public static JSONArray getAccounts(Long userId, int productId,
			Integer top, boolean includeNonActive, Date fromDate, Date toDate, Long[] accountIds) {
		try {
			return AccountsDataAccess.getAccounts(userId, productId, includeNonActive, top, fromDate, toDate, accountIds);
			
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
	
	/*
	public static OperationResult<List<IAccountView>> getAccounts(Long userId, int productId,
			Integer top, boolean includeNonActive, Date fromDate, Date toDate, Long[] accountIds){
		OperationResult<List<IAccountView>> result = new OperationResult<List<IAccountView>>();
		final List<IAccountView> accounts = new ArrayList<IAccountView>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IAccountView account = new AccountEntity(reader);
							accounts.add(account);							
						}
					}
				}
			};
			
			AccountsDataAccess.getAccounts(userId, productId, callback, includeNonActive, top, fromDate, toDate, accountIds);
			
			if(!result.hasError() && accounts.size() == 0){
				result.setError(ErrorCode.NoResults);
			}
			
			if(!result.hasError()){
				result.setValue(accounts);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getAccounts() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	*/

	private static Object _accountsByIdCacheLocker = new Object(); 
	private static LoadingCache<Long, OperationResult<IAccountView>> _accountsByIdCache;
	private static LoadingCache<Long, OperationResult<IAccountView>> getAccountByIdCache(){
		if(null == _accountsByIdCache){
			synchronized (_accountsByIdCacheLocker) {
				if(null == _accountsByIdCache){
					_accountsByIdCache = CacheBuilder.newBuilder().
							maximumSize(100).
							expireAfterWrite(5, TimeUnit.SECONDS).
							build( new CacheLoader<Long, OperationResult<IAccountView>>() {
								public OperationResult<IAccountView> load(Long accountId) throws Exception {
									return getAccountInternal(accountId);
								}
							});
				}
			}
		}
		
		return _accountsByIdCache;
	}
	
	public static OperationResult<IAccountView> getAccount(long accountId) {
		OperationResult<IAccountView> result;
		try {
			result = getAccountByIdCache().get(accountId);
		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex, "getAccount: Unexpected error occured");
			result = new OperationResult<IAccountView>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}

	public static OperationResult<IAccountView> getAccountInternal(
			long accountId) {
		final OperationResult<IAccountView> result = new OperationResult<IAccountView>();
		try{
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
			
			AccountsDataAccess.getAccount(accountId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getAccount() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<IAccountDetails> getAccountDetails(long accountId) {
		final OperationResult<IAccountDetails> result = new OperationResult<IAccountDetails>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							AccountDetailsEntity account = new AccountDetailsEntity(reader);
							result.setValue(account);							
						}
					}
				}
			};
			
			AccountsDataAccess.getAccountDetails(accountId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getAccountDetails() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult setAccountDetails(IAccountDetailsChangeRequest request) {
		final BaseOperationResult result = new BaseOperationResult();
		try{
			AccountsDataAccess.setAccountDetails(request);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "setAccountDetails() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static OperationResult<Double> getFreeBalance(long accountId, Long userId) {
		
		OperationResult<Double> result;
		try{
			result = AccountsDataAccess.getFreeBalance(accountId, userId);
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getFreeBalance() : Unexpected error occured.");
			result = new OperationResult<Double>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult changeBalance(IChangeBalanceRequest request){
		final BaseOperationResult result = new BaseOperationResult();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							long transactionId = reader.getLong("account_operation_id");
							result.setTransactionId(transactionId);							
						}
					}
				}
			};
			
			AccountsDataAccess.changeBalance(request, callback);
			
			if(!result.hasError() && null == result.getTransactionId()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "changeBalance() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static IOperationResult updateServicePackage(long accountId,
			int servicePackageId, Date servicePackageExpiryDate,
			Integer maxUsers) {
		final BaseOperationResult result = new BaseOperationResult();
		try{
			AccountsDataAccess.changeServicePackage(accountId, servicePackageId, servicePackageExpiryDate, maxUsers);
			// Send package expired email
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "setServicePackage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static JSONArray getAccountsWithExpiredServicePackages(Date expiryDate) {
		try {
			return AccountsDataAccess.getAccountsWithExpiredServicePackages(expiryDate);
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
	
	public static OperationResult<IAccountBusinessDetails> getAccountBusinessDetails(long accountId) {
		final OperationResult<IAccountBusinessDetails> result = new OperationResult<IAccountBusinessDetails>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							AccountBusinessDetailsEntity account = new AccountBusinessDetailsEntity(reader);
							result.setValue(account);							
						}
					}
				}
			};
			
			AccountsDataAccess.getAccountBusinessDetails(accountId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getAccountBusinessDetails() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult setAccountBusinessDetails(IAccountBusinessDetailsChangeRequest request) {
		final BaseOperationResult result = new BaseOperationResult();
		try{
			AccountsDataAccess.setAccountBusinessDetails(request);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "setAccountBusinessDetails() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult changeAccountOwner(long accountId, long newOwnerId, Long backofficeUserId, int sourceId){
		final BaseOperationResult result = new BaseOperationResult();
		try{
			AccountsDataAccess.changeAccountOwner(accountId, newOwnerId, backofficeUserId, sourceId);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "changeAccountOwner() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static BaseOperationResult changeAccountBillingSettings(IAccountBillingSettingsChangeRequest request) {
		final BaseOperationResult result = new BaseOperationResult();
		try{
			AccountsDataAccess.changeAccountBillingSettings(request);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "changeAccountBillingSettings() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
