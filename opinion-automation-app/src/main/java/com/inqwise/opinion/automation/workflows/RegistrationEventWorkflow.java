package com.inqwise.opinion.automation.workflows;

import java.util.Calendar;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.events.RegistrationEventArgs;
import com.inqwise.opinion.automation.managers.EventTypesManager;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.emails.IRegisterEmailData;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUserDetails;
import com.inqwise.opinion.library.entities.UserDetailsEntity;
import com.inqwise.opinion.library.managers.EmailsManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class RegistrationEventWorkflow extends Workflow<RegistrationEventArgs>{

	ApplicationLog logger = ApplicationLog.getLogger(RegistrationEventWorkflow.class);
	
	@Override
	public IOperationResult processWorkflow(RegistrationEventArgs eventArgs) {
		
		IOperationResult result = null;
		StringBuilder sb = new StringBuilder();
		sb.append("UserId: ").append(eventArgs.getUserId()).append("\n");
		sb.append("UserName: ").append(eventArgs.getUserName()).append("\n");
		sb.append("CountryName: ").append(eventArgs.getCountryName()).append("\n");
		
		EventTypesManager.getInstance().sendEventToSubscribers(EventType.RegistrationOccured, sb.toString());
		
		IProduct product = null;
		if(null == result){
			OperationResult<IProduct> productResult = ProductsManager.getProductById(eventArgs.getProductId());
			if(productResult.hasError()){
				result = productResult;
			} else {
				product = productResult.getValue();
			}
		}
		
		if(null == result){
			EmailsManager.sendRegisterEmail(createRegisterEmailData(eventArgs, product));
			result = AutomationBaseOperationResult.Ok;
		}
		
		return result;
	}

	private IRegisterEmailData createRegisterEmailData(
			final RegistrationEventArgs eventArgs,
			final IProduct product) {
		return new IRegisterEmailData() {

			public String getUserName() {
				return eventArgs.getUserName();
			}
			
			public String getEmail() {
				return eventArgs.getEmail();
			}

			public String getFeedbackCaption() {
				return product.getFeedbackCaption();
			}

			public String getApplicationUrl() {
				return ApplicationConfiguration.Opinion.getUrl();
			}

			public int getCurrentYear() {
				return Calendar.getInstance().get(Calendar.YEAR);
			}

			public String getSupportEmail() {
				return product.getSupportEmail();
			}

			public String getFeedbackShortCaption() {
				return product.getFeedbackShortCaption();
			}

			public String getNoreplyEmail() {
				return product.getNoreplyEmail();
			}
		};
	}

}
