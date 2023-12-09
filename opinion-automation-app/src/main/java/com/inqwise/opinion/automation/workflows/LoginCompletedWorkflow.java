package com.inqwise.opinion.automation.workflows;

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.events.LoginEventArgs;
import com.inqwise.opinion.automation.managers.EventTypesManager;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUserDetails;
import com.inqwise.opinion.library.common.users.LoginProvider;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class LoginCompletedWorkflow extends Workflow<LoginEventArgs> {
	ApplicationLog logger = ApplicationLog.getLogger(LoginCompletedWorkflow.class);
	
	private static Object _loginsCacheLocker = new Object(); 
	private static LoadingCache<Long, LoginEventArgs> _loggedInUserCache;
	private static LoadingCache<Long, LoginEventArgs> getLoginCache(){
		if(null == _loggedInUserCache){
			synchronized (_loginsCacheLocker) {
				if(null == _loggedInUserCache){
					_loggedInUserCache = CacheBuilder.newBuilder().
							maximumSize(100).
							expireAfterAccess(1, TimeUnit.MINUTES).
							build( new CacheLoader<Long, LoginEventArgs>() {
								public LoginEventArgs load(Long accountId) throws Exception {
									return null;
								}
							});
				}
			}
		}
		
		return _loggedInUserCache;
	}

	@Override
	protected IOperationResult processWorkflow(final LoginEventArgs eventArgs)
			throws RemoteException {
		IOperationResult result = null;
		
		IProduct product = null;
		if(null == result){
			OperationResult<IProduct> productResult = ProductsManager.getProductById(eventArgs.getProductId());
			if(productResult.hasError()){
				result = productResult;
			} else {
				product = productResult.getValue();
			}
		}
		
		IUserDetails user = null;
		if(null == result) {
			OperationResult<IUserDetails> userResult = UsersManager.getUserDetails(eventArgs.getUserId());
			if(userResult.hasValue()){
				user = userResult.getValue();
			}
		}
		
		if(null == result && null != user && (user.getCountOfLogins() > 1 || eventArgs.isAutoLogin() || user.getProvider() != LoginProvider.Inqwise)) {
			final AtomicBoolean missedInCache = new AtomicBoolean(false); 
			
			try {
				if(eventArgs.isAutoLogin()){
					getLoginCache().get(eventArgs.getUserId(), new Callable<LoginEventArgs>() {
	
						@Override
						public LoginEventArgs call() throws Exception {
							missedInCache.set(true);
							return eventArgs;
						}
					});
					
					if(!missedInCache.get()){
						logger.debug("Auto login user #%s found in cache", eventArgs.getUserId());
					}
				}
			} catch (ExecutionException e) {
				UUID errorId = logger.error(e, "processWorkflow: Failed to get LoginArgs from cache");
				result = new AutomationOperationResult<>(AutomationErrorCode.GeneralError, errorId);
			}
			
			if(null == result){
				if(missedInCache.get()) {
					StringBuilder sb = new StringBuilder();
					if(eventArgs.isAutoLogin()) sb.append("Auto-Login to ");
					if(user.getProvider() != LoginProvider.Inqwise) sb.append(user.getProvider()).append("-Login to ");
					sb.append("Product: ").append(product.getName()).append("\n");
					
					
					sb.append("UserId: ").append(eventArgs.getUserId()).append("\n");
					sb.append("UserName: ").append(eventArgs.getUserName()).append("\n");
					sb.append("CountryName: ").append(eventArgs.getCountryName()).append("\n");
					
					EventTypesManager.getInstance().sendEventToSubscribers(EventType.Login, sb.toString());
					
					if(eventArgs.isAutoLogin()){
						int sourceId = ProductsManager.getProductById(eventArgs.getSourceId()).getValue().getId();
						com.inqwise.opinion.library.managers.UsersOperationsManager.insertAutoLogin(user.getUserId(), eventArgs.getClientIp(), eventArgs.getCountryCode(), eventArgs.getSessionId(), sourceId);
					}
				}
			}
		}
		return result;
	}
}
