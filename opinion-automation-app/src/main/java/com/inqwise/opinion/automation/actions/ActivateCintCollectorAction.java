package com.inqwise.opinion.automation.actions;

import java.util.UUID;

import com.cint.CintApiService;
import com.cint.OrderChangeStatusRequest;
import com.cint.OrderDetailsRequest;
import com.cint.common.IOrder;
import com.cint.common.OrderEventType;
import com.cint.common.errorHandle.CintBaseOperationResult;
import com.cint.common.errorHandle.CintOperationResult;
import com.inqwise.opinion.automation.common.ChargePostPayActionArgs;
import com.inqwise.opinion.automation.common.IEventAction;
import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.managers.EventTypesManager;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.collectors.IPanelSurveysCollector;
import com.inqwise.opinion.opinion.managers.CollectorsManager;

public class ActivateCintCollectorAction implements IEventAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4865371161220385111L;

	private static final ApplicationLog logger = ApplicationLog.getLogger(ActivateCintCollectorAction.class);
	
	private ChargePostPayActionArgs args;
	public ActivateCintCollectorAction(ChargePostPayActionArgs args) {
		this.args = args;
	}
	
	@Override
	public IOperationResult run() {
		logger.info("ActivateCintCollectorAction started");
		IOperationResult result = null;
		ICollector collector = null;
		OperationResult<ICollector> collectorResult = CollectorsManager.get(args.getChargeReferenceId(), null);
		if(collectorResult.hasError()){
			logger.error("Unable to get collector '#%s' of charge: '#%s', error: '%s'. Activate Cint action skipped.", args.getChargeReferenceId(), args.getChargeId(), collectorResult.toString());
			result = BaseOperationResult.Ok;
		} else {
			collector = collectorResult.getValue();
		}
		
		if(null == result && collector.getCollectorStatus() == CollectorStatus.PendingPayment){
			BaseOperationResult changeStatusResult = collector.changeStatus(CollectorStatus.Verify, null);
			if(changeStatusResult.hasError()){
				logger.error("Action failed : Failed to change collector status. collector '#%s', status: '%s'.", collector.getId(), CollectorStatus.Verify);
				result = changeStatusResult;
			}
		}
		
		if(null == result && collector instanceof IPanelSurveysCollector){
			IPanelSurveysCollector panelSurveysCollector = (IPanelSurveysCollector) collector;
			CintApiService service = new CintApiService();
			OrderChangeStatusRequest request = new OrderChangeStatusRequest();
			request.setLocationId(panelSurveysCollector.getExternalId());
			request.setStatus(OrderEventType.Release);
			CintBaseOperationResult changeOrderStatusResult = service.call(request);
			if(changeOrderStatusResult.hasError()){
				switch(changeOrderStatusResult.getError()){
				case StateIsInvalid:
					sendInvalidStatusApplicationEvent(args, panelSurveysCollector, OrderEventType.Release);
					result = AutomationBaseOperationResult.Ok; // This error required to fix manually. 
					break;
				default:
					UUID errorId = logger.error("Action failed : Failed to change status of Cint order. collector '#%s' of charge: '#%s', error: '%s'.Action Failed", args.getChargeReferenceId(), args.getChargeId(), collectorResult.toString());
					result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
					break;
				}
				
			}
		}
		
		if(null == result){
			result = AutomationBaseOperationResult.Ok;
		}
		
		return result;
	}

	private static void sendInvalidStatusApplicationEvent(ChargePostPayActionArgs args, IPanelSurveysCollector collector, OrderEventType state) {
		CintApiService service = new CintApiService();
		OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
		orderDetailsRequest.setLocationId(collector.getExternalId());
		CintOperationResult<IOrder> detailsResult = service.call(orderDetailsRequest);
		IOrder order = detailsResult.getValue();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Failed to change Cint order to '").append(state).append("'\n");
		sb.append("ChargeId: ").append(args.getChargeId()).append("\n");
		sb.append("CollectorId: ").append(collector.getId()).append("\n");
		sb.append("CollectorStatus: ").append(collector.getCollectorStatus()).append("\n");
		sb.append("OpinionId: ").append(collector.getOpinionId()).append("\n");
		sb.append("OrderState: ").append(order.getState()).append("\n");
		sb.append("OrderNumber: ").append(order.getOrderNumber()).append("\n");
		
		EventTypesManager.getInstance().sendEventToSubscribers(EventType.AutomationActionFailed, sb.toString());
		
	}
}
