package com.inqwise.opinion.automation.workflows;

import java.rmi.RemoteException;
import java.util.Date;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.events.ParticipantCompletedEventArgs;
import com.inqwise.opinion.automation.managers.EventTypesManager;
import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.managers.CollectorsManager;
import com.inqwise.opinion.managers.ResponsesManager;

public class ParticipantCompletedWorkflow extends Workflow<ParticipantCompletedEventArgs>{

	ApplicationLog logger = ApplicationLog.getLogger(ParticipantCompletedWorkflow.class);
	
	@Override
	protected IOperationResult processWorkflow(
			ParticipantCompletedEventArgs eventArgs) throws RemoteException {
		
		IOperationResult result = null;
		
		OperationResult<ICollector> collectorResult = CollectorsManager.get(eventArgs.getCollectorId(), null);
		
		ICollector collector = null;
		if(collectorResult.hasError()){
			result = AutomationBaseOperationResult.Ok; // No need to continue
		} else {
			collector = collectorResult.getValue();
		}
		
		IProduct product = null;
		if(null == result){
			OperationResult<IProduct> productResult = ProductsManager.getProductById(eventArgs.getSourceId());
			if(productResult.hasError()){
				result = AutomationBaseOperationResult.Ok; // No need to continue
			} else {
				product = productResult.getValue();
			}
		}
		
		IUser user = null;
		if(null == result){
			OperationResult<IUser> userResult = UsersManager.getUser(null, eventArgs.getOpinionOwnerId(), null, null);
			if(userResult.hasError()){
				result = AutomationBaseOperationResult.Ok; // No need to continue
			} else {
				user = userResult.getValue();
			}
		}
		
		if (null == result) {
			boolean closeCollector = false;
			// Check Collector ResponceQuota limit
			if (null != collector.getResponseQuota() && collector.getCountOfFinishedOpinions() >= collector.getResponseQuota()) {
				logger
				.info(
						"Collector responce quota riched. collectorId: '%s', opinionId: '%s'",
						collector.getId(),
						collector.getOpinionId());
		
				closeCollector = true;
			}
			
			ICollector.IEndDateExtension endDateExtension = null;
			if (endDateExtension instanceof ICollector.IEndDateExtension) {
				endDateExtension = (ICollector.IEndDateExtension) endDateExtension;
			}
			
			// Check Collector EndDate
			if (null != endDateExtension && null != endDateExtension.getEndDate() && endDateExtension.getEndDate().before(new Date())) {
				logger
						.info(
								"Collector endDate riched. collectorId: '%s', opinionId: '%s'",
								collector.getId(),
								collector.getOpinionId());
				
				
				closeCollector = true;
			}
			
			if(closeCollector){
				BaseOperationResult changeStatusResult = collector.changeStatus(CollectorStatus.Closed, null);
				if(changeStatusResult.hasError()){
					throw new Error(String.format("Failed to change collector #%s Status", collector.getId()));
				}
			}
		}
		
		IOpinion opinion = null;
		if(null == result){
			OperationResult<IOpinion> opinionResult = collector.getOpinion(null);
			if(opinionResult.hasError()){
				result = opinionResult;
			} else {
				opinion = opinionResult.getValue();
			}
		}
		
		if(null == result && collector.isEnableFinishedSessionEmailNotification()){
			ResponsesManager.emailNotification(eventArgs.getParticipantGuid(), collector, product, user, OpinionType.fromInt(eventArgs.getOpinionTypeId()), opinion);
		}
		
		if(null == result){
			String body = String.format("OpinionId: %s\nOpinionType: %s\nOpinionOwnerId: %s", eventArgs.getOpinionId(), OpinionType.fromInt(eventArgs.getOpinionTypeId()), eventArgs.getOpinionOwnerId());
			EventTypesManager.getInstance().sendEventToSubscribers(EventType.ParticipantCompleted, body);
		}
		
		return result = AutomationBaseOperationResult.Ok;
	}

}
