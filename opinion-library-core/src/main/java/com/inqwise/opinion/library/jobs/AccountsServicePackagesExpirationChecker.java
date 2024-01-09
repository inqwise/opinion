package com.inqwise.opinion.library.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;

import com.inqwise.opinion.automation.common.jobs.IJobExecutorCallback;
import com.inqwise.opinion.automation.common.jobs.Job;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.emails.IPackageBeforeExpirationEmailData;
import com.inqwise.opinion.library.common.emails.IPackageExpiredEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.EmailsManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class AccountsServicePackagesExpirationChecker extends Job {

	public AccountsServicePackagesExpirationChecker(JobSettings settings, IJobExecutorCallback callback) {
		super(settings, callback);
	}

	private IProduct product;
	private IServicePackage defaultServicePackage;
	
	@Override
	protected IOperationResult process() {
		
		BaseOperationResult result;
		try {
			Date today = DateConverter.trim(new Date());
			Date yesterday = DateUtils.addDays(today, 2);
			var arr = AccountsManager.getAccountsWithExpiredServicePackages(yesterday);
			for (int i = 0; i < arr.length(); i++) {
				var json = arr.getJSONObject(i);

				int productId = json.getInt("product_id");
				if(null == product || product.getId() != productId){
					product = ProductsManager.getProductById(productId).getValue();
					defaultServicePackage = product.getDefaultServicePackage();
				}
				
				long accountId = json.getLong("account_id");
				long userId = json.getLong("user_id");
				Date expiryDate = (Date)json.get("expiry_date");
				int currentServicePackageId = json.getInt("service_package_id");
				IServicePackage currentServicePackage = product.getServicePackage(currentServicePackageId);
				
				IAccountView account = AccountsManager.getAccount(accountId).getValue();
				IUser owner = UsersManager.getUser(userId, productId).getValue();
				
				if(expiryDate.before(today)){
					IOperationResult updateResult = AccountsManager.updateServicePackage(accountId, defaultServicePackage.getId(), defaultServicePackage.calculateExpiryDate(), defaultServicePackage.getMaxAccountUsers());
					if(!updateResult.hasError()){
						logger.info("AccountsServicePackagesExpirationChecker : Downgraded account #%s servicePackageId to %s (default) by reason: Expiration", accountId, defaultServicePackage.getId());
						// Send expiration email 
						sendPackageExpiredEmail(currentServicePackage, owner, ApplicationConfiguration.Opinion.getSecureUrl());
					}
				} else {
					// Send 1 Day before expiration reminder email
					sendPackageOneDayBeforeExpirationEmail(currentServicePackage, owner, account.addDateOffset(expiryDate), account.getTimezoneOffset(), ApplicationConfiguration.Opinion.getSecureUrl());
				}
			}
			result = BaseOperationResult.Ok;
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "AccountsServicePackagesExpirationJob.process : Unexpected error occcured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public void sendPackageOneDayBeforeExpirationEmail(final IServicePackage currentServicePackage, final IUser user, final Date servicePackageExpirationDate, final Integer timezoneOffset, final String servicePackageUpgradeUrl) {
		EmailsManager.sendPackageOneDayBeforeExpirationEmail(new IPackageBeforeExpirationEmailData() {
			
			public String getNoreplyEmail() {
				return product.getNoreplyEmail();
			}
			
			public String getSupportEmail() {
				return product.getSupportEmail();
			}
			
			public String getFeedbackShortCaption() {
				return product.getFeedbackShortCaption();
			}
			
			public String getFeedbackCaption() {
				return product.getFeedbackCaption();
			}
			
			public String getEmail() {
				return user.getEmail();
			}
			
			public int getCurrentYear() {
				return Calendar.getInstance().get(Calendar.YEAR);
			}
			
			public String getApplicationUrl() {
				return ApplicationConfiguration.Opinion.getUrl();
			}

			public IServicePackage getServicePackage() {
				return currentServicePackage;
			}

			public Date getExpirationDate() {
				return servicePackageExpirationDate;
			}

			public String getBaseUrl() {
				return servicePackageUpgradeUrl;
			}

			public long getUserId() {
				return user.getUserId();
			}

			public Integer getTimezoneOffset() {
				return timezoneOffset;
			}

			public String getCultureCode() {
				return user.getCultureCode();
			}
		});
	}

	public void sendPackageExpiredEmail(final IServicePackage currentServicePackage, final IUser user, final String servicePackageUpgradeUrl) {
		EmailsManager.sendPackageExpiredEmail(new IPackageExpiredEmailData() {
			
			public String getNoreplyEmail() {
				return product.getNoreplyEmail();
			}
			
			public String getSupportEmail() {
				return product.getSupportEmail();
			}
			
			public String getFeedbackShortCaption() {
				return product.getFeedbackShortCaption();
			}
			
			public String getFeedbackCaption() {
				return product.getFeedbackCaption();
			}
			
			public String getEmail() {
				return user.getEmail();
			}
			
			public int getCurrentYear() {
				return Calendar.getInstance().get(Calendar.YEAR);
			}
			
			public String getApplicationUrl() {
				return ApplicationConfiguration.Opinion.getUrl();
			}

			public IServicePackage getExpiredServicePackage() {
				return currentServicePackage;
			}

			public String getBaseUrl() {
				return servicePackageUpgradeUrl;
			}

			public long getUserId() {
				return user.getUserId();
			}
			
			public String getCultureCode() {
				return user.getCultureCode();
			}
		});
	}

}
