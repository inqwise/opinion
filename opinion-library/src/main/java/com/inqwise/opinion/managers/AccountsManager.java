package com.inqwise.opinion.managers;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.javatuples.Pair;
import org.javatuples.Tuple;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.common.AccountOpinionInfo;
import com.inqwise.opinion.common.IOpinionAccount;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.dao.AccountsDataAccess;
import com.inqwise.opinion.dao.OpinionAccountDataAccess;
import com.inqwise.opinion.entities.OpinionAccountEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class AccountsManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(AccountsManager.class);
	
	private static Object _accountsCacheLocker = new Object(); 
	private static LoadingCache<Pair<Long, Integer>, OperationResult<IOpinionAccount>> _accountsCache;
	private static LoadingCache<Pair<Long, Integer>, OperationResult<IOpinionAccount>> getAccountsCache(){
		if(null == _accountsCache){
			synchronized (_accountsCacheLocker) {
				if(null == _accountsCache){
					_accountsCache = CacheBuilder.newBuilder().
							maximumSize(1000).
							expireAfterWrite(20, TimeUnit.SECONDS).
							build( new CacheLoader<Pair<Long, Integer>, OperationResult<IOpinionAccount>>() {
								public OperationResult<IOpinionAccount> load(Pair<Long, Integer> args) throws Exception {
									return getOpinionAccountInternal(args.getValue0(), args.getValue1());
								}
							});
				}
			}
		}
		
		return _accountsCache;
	}
	
	public static OperationResult<IOpinionAccount> getOpinionAccount(IAccount account){
		
		return getOpinionAccount(account.getId(), account.getServicePackageId());
	}
	
	public static OperationResult<IOpinionAccount> getOpinionAccount(long accountId, int servicePackageId){
		OperationResult<IOpinionAccount> result = null;
		try {
			result = getAccountsCache().get(new Pair<Long, Integer>(accountId, servicePackageId));
		} catch (ExecutionException e) {
			UUID errorId = logger.error(e, "getOpinionAccount() : Unexpected error occured.");
			result = new OperationResult<IOpinionAccount>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	private static OperationResult<IOpinionAccount> getOpinionAccountInternal(final long accountId, final int servicePackageId)
	{
		final OperationResult<IOpinionAccount> result = new OperationResult<IOpinionAccount>();
		
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				@Override
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(result.hasValue()){
							throw new Error("getOpinionAccount : More that one results returned for accountId " + accountId);
						} else {
							result.setValue(new OpinionAccountEntity(reader));
						}
					}
				}
			};
			OpinionAccountDataAccess.getAccount(accountId, servicePackageId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				UUID errorId = logger.error("getOpinionAccount() : No results for account " + accountId);
				result.setError(ErrorCode.NoResults, errorId);
			} 
		} catch (Exception e){
			UUID errorId = logger.error(e, "getOpinionAccount() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<AccountOpinionInfo> getAccountShortStatistics(long accountId, Integer daysBack, Long opinionId){
		OperationResult<AccountOpinionInfo> result;
		try {
			result =  AccountsDataAccess.getAccountShortStatistics(accountId, daysBack, opinionId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "getAccountShortStatistics() : Unexpected error occured.");
			result = new OperationResult<AccountOpinionInfo>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static BaseOperationResult updateWelcomeMessageFlag(Long accountId,
			boolean flag, long userId) {
		BaseOperationResult result;
		try {
			OpinionAccountDataAccess.updateWelcomeMessageFlag(accountId, flag, userId);
			result = BaseOperationResult.Ok;
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "updateWelcomeMessageFlag() : Unexpected error occured.");
			result = new OperationResult<AccountOpinionInfo>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<Long> changeSessionsBalance(long accountId, int servicePackageId, Integer amount, Boolean unlimitedBalance){
		OperationResult<Long> result;
		try {
			return OpinionAccountDataAccess.changeSessionsBalance(accountId, servicePackageId, amount, unlimitedBalance);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "changeSessionsBalance() : Unexpected error occured.");
			result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
}
