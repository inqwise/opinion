package com.inqwise.opinion.library.managers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeReferenceType;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.pay.IChargeCreateRequest;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.common.servicePackages.IServicePackageCreateRequest;
import com.inqwise.opinion.library.common.servicePackages.IServicePackageUpdateRequest;
import com.inqwise.opinion.library.dao.ServicePackageDataAccess;
import com.inqwise.opinion.library.entities.ServicePackageEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ServicePackagesManager {
	
	static ApplicationLog logger = ApplicationLog.getLogger(ServicePackagesManager.class);
	
	private static Object _servicePackagesCacheLocker = new Object(); 
	private static LoadingCache<Integer, OperationResult<IServicePackage>> _servicePackagesCache;
	private static LoadingCache<Integer, OperationResult<IServicePackage>> getServicePackagesCache(){
		if(null == _servicePackagesCache){
			synchronized (_servicePackagesCacheLocker) {
				if(null == _servicePackagesCache){
					_servicePackagesCache = CacheBuilder.newBuilder().
							maximumSize(100).
							expireAfterWrite(30, TimeUnit.MINUTES).
							build(new CacheLoader<Integer, OperationResult<IServicePackage>>(){
								@Override
								public OperationResult<IServicePackage> load(
										Integer servicePackageId) throws Exception {
									return getServicePackageInternal(servicePackageId, null);
								}
							});
				}
			}
		}
		
		return _servicePackagesCache;
	}
	
	public static OperationResult<List<IServicePackage>> getServicePackages(Integer productId, boolean includeNonActive){
		OperationResult<List<IServicePackage>> result = new OperationResult<List<IServicePackage>>();
		
		try{
			final List<IServicePackage> items = new ArrayList<IServicePackage>();
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IServicePackage item = new ServicePackageEntity(reader);
							items.add(item);							
						}
					}
				}
			};
			
			ServicePackageDataAccess.getServicePackages(callback, productId, includeNonActive);
			
			if(items.size() == 0){
				result.setError(ErrorCode.NoResults);
			} else {
				result.setValue(items);
			}
			
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getServicePackages() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<IServicePackage> getServicePackage(int servicePackageId, Integer productId){
		OperationResult<IServicePackage> result;
		try {
			result = getServicePackagesCache().get(servicePackageId);
			if(result.hasValue() && null != productId && result.getValue().getProductId() != productId){
				result.setError(ErrorCode.NotExist);
			}
		} catch (ExecutionException e) {
			UUID errorTicket = logger.error(e, "getAccount: Unexpected error occured");
			result = new OperationResult<IServicePackage>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	private static OperationResult<IServicePackage> getServicePackageInternal(int servicePackageId, Integer productId){
		final OperationResult<IServicePackage> result = new OperationResult<IServicePackage>();
		
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IServicePackage item = new ServicePackageEntity(reader);
							result.setValue(item);							
						}
					}
				}
			};
			
			ServicePackageDataAccess.getServicePackage(callback, servicePackageId, productId);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			} 
			
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getServicePackage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static OperationResult<ICharge> createCharge(
			final IServicePackage servicePackage, final int countOfPeriods, final IAccount account,
			final long userId, final boolean isUpgrade) throws JSONException {
		
		final double more11MonthsDiscount = 0.17;
		final double amount = servicePackage.getAmount() * countOfPeriods;
		
		double discount = 0.0d;
		if(countOfPeriods > 11){
			discount = amount * more11MonthsDiscount;
		}
		
		final JSONObject postPayActionData = new JSONObject().put(IServicePackage.JsonNames.COUNT_OF_MONTHS, countOfPeriods);
		
		final double totalAmount = (discount > 0.0d ? 
										BigDecimal.valueOf(amount - discount)
											.setScale(0, RoundingMode.FLOOR)
											.doubleValue()
										: amount);
		return ChargesManager.createCharge(new IChargeCreateRequest() {
			
			public Long getUserId() {
				return userId;
			}
			
			public int getStatusId() {
				return ChargeStatus.Unpaid.getValue();
			}
			
			public Integer getReferenceTypeId() {
				return ChargeReferenceType.ServicePackage.getValue();
			}
			
			public Long getReferenceId() {
				return Long.valueOf(servicePackage.getId());
			}
	
			public String getPostPayAction() {
				return "com.inqwise.opinion.automation.actions.UpgradePlanAction";
			}
			
			public String getName() {
				return String.format("%s%s plan", (isUpgrade ? "Upgrade " : ""), servicePackage.getName());
			}
			
			public Date getExpiryDate() {
				return ChargesManager.generateDefaultExpirationDate();
			}
			
			public String getDescription() {
				return String.format("%s $%.2f / %s Month%s", getName(), totalAmount, countOfPeriods, (countOfPeriods > 1 ? "s": ""));
			}
			
			public Integer getBillTypeId() {
				return null;
			}
			
			public Long getBillId() {
				return null;
			}
			
			public String getAmountCurrency() {
				return "USD";
			}
			
			public double getAmount() {
				return totalAmount;
			}
			
			public long getAccountId() {
				return account.getId();
			}

			public JSONObject getPostPayActionData() {
				return postPayActionData;
			}
		});
	}

	public static BaseOperationResult updateServicePackage(
			IServicePackageUpdateRequest request) {
		BaseOperationResult result = new BaseOperationResult();
		
		try{
			ServicePackageDataAccess.updateServicePackage(request);
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "updateServicePackage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
		
	}
	
	public static BaseOperationResult createServicePackage(
			IServicePackageCreateRequest request) {
		BaseOperationResult result = new BaseOperationResult();
		
		try{
			ServicePackageDataAccess.newServicePackage(request);
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "createServicePackage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
		
	}
}
