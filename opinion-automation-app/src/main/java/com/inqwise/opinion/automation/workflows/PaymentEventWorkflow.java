package com.inqwise.opinion.automation.workflows;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.automation.actions.PayChargeAction;
import com.inqwise.opinion.automation.common.ActionException;
import com.inqwise.opinion.automation.common.FireEventArgs.ClientArgs;
import com.inqwise.opinion.automation.common.IEventAction;
import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.events.PaymentEventArgs;
import com.inqwise.opinion.automation.managers.ActionsManager;
import com.inqwise.opinion.automation.managers.EventTypesManager;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.ProductsManager;

public class PaymentEventWorkflow extends Workflow<PaymentEventArgs> {

	ApplicationLog logger = ApplicationLog.getLogger(PaymentEventWorkflow.class);
	
	@Override
	public AutomationBaseOperationResult processWorkflow(PaymentEventArgs eventArgs) {
		AutomationBaseOperationResult result = new AutomationBaseOperationResult();
		try {
			payCharges(eventArgs);
			processActivateActions(eventArgs.getChargeIds(), eventArgs.getClient().getSourceId());
			sendEventEmail(eventArgs);
		} catch (Throwable ex){
			UUID errorId = logger.error(ex, "identifyActions : unexpected error occured.");
			result.setError(AutomationErrorCode.GeneralError, errorId);
		}
		return AutomationBaseOperationResult.Ok;
	}

	private void processActivateActions(List<Long> chargesIds, int sourceId) throws ActionException {
		if(null != chargesIds) {
			List<IEventAction> actions = new ArrayList<IEventAction>();
			for (long chargeId : chargesIds) {
				ChargeStatusChangedEventWorkflow.identifyPaidChargeActions(chargeId, sourceId, actions);
			}
			
			if(actions.size() == 0){
				logger.warn("PaymentEventWorkflow : No actions found for charges '%s'", StringUtils.join(chargesIds, ","));
			} else {
				for (IEventAction action : actions) {
					ActionsManager.getInstance().processAction(action);
				}
			}
		}
	}

	private void payCharges(PaymentEventArgs eventArgs) {
		if(null != eventArgs.getChargeIds()){
			ClientArgs client = eventArgs.getClient();
			
			for (Long chargeId : eventArgs.getChargeIds()) {
				new PayChargeAction(chargeId).withClientIp(client.getClientIp()).withGeoCountryCode(client.getGeoCountryCode()).withSessionId(client.getSessionId()).withSourceId(client.getSourceId()).run();
			}
		}
	}

	private void sendEventEmail(PaymentEventArgs eventArgs) {
		StringBuilder sb = new StringBuilder();
		sb.append("UserId: ").append(eventArgs.getUserId()).append("\n");
		sb.append("AccountId: ").append(eventArgs.getAccountId()).append("\n");
		
		OperationResult<IProduct> productResult = ProductsManager.getProductById(eventArgs.getSourceId());
		if(!productResult.hasError()){
			sb.append("Occured in ").append(productResult.getValue().getName()).append("\n");
		}
		
		EventTypesManager.getInstance().sendEventToSubscribers(EventType.PaymentOccured, sb.toString());
	}
}
