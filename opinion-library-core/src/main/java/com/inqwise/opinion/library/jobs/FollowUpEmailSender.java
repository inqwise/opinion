package com.inqwise.opinion.library.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.automation.common.jobs.IJobExecutorCallback;
import com.inqwise.opinion.automation.common.jobs.Job;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.emails.IFollowUpEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUserView;
import com.inqwise.opinion.library.managers.EmailsManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class FollowUpEmailSender extends Job {

	public FollowUpEmailSender(JobSettings settings,
			IJobExecutorCallback callback) {
		super(settings, callback);
	}

	@Override
	protected IOperationResult process() {
		IOperationResult result = null;
		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, -1);
			Date toDate = c.getTime();
			c.add(Calendar.DAY_OF_YEAR, -1);
			Date fromDate = c.getTime(); 
			
			IProduct product = null;
			OperationResult<IProduct> productResult = ProductsManager.getProductByGuid(ApplicationConfiguration.Opinion.getProductGuid());
			if(productResult.hasError()){
				result = productResult;
			} else {
				product = productResult.getValue();
			}
			
			List<IUserView> users = null;
			if(null == result){
				OperationResult<List<IUserView>> usersResult = UsersManager.getUsers(10000, productResult.getValue().getId(), fromDate, toDate, null);
				if(usersResult.hasError()){
					result = usersResult;
				} else {
					users = usersResult.getValue();
				}
			}
			
			if(null == result){
				for (IUserView user : users) {
					if(user.getSendNewsLetters()){
						sendFollowUpEmail(product, user);
					} else {
						logger.debug("sendNewsLetters flag is disabled for user #" + user.getUserId());
					}
				}
				result = BaseOperationResult.Ok;
			}
			
			if(null != result && result.getErrorCode() == ErrorCode.NoResults.ordinal()){
				logger.info("No users to send");
				result = BaseOperationResult.Ok;
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "FollowUpEmailSender.process : Unexpected error occcured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	private void sendFollowUpEmail(final IProduct product, final IUserView user) {
		EmailsManager.sendFollowUpEmail(new IFollowUpEmailData() {
			
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

			public long getUserId() {
				return user.getUserId();
			}
		});
	}
}
