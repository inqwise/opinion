package com.inqwise.opinion.automation.workflows;

import java.rmi.RemoteException;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.events.OpinionCreatedEventArgs;
import com.inqwise.opinion.automation.managers.EventTypesManager;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.managers.OpinionsManager;

public class OpinionCreatedEventWorkflow extends Workflow<OpinionCreatedEventArgs> {

	@Override
	protected IOperationResult processWorkflow(OpinionCreatedEventArgs eventArgs)
			throws RemoteException {

		OperationResult<IOpinion> opinionResult = OpinionsManager.getOpinion(eventArgs.getOpinionId(), null);
		if(opinionResult.hasValue()){
			IOpinion opinion = opinionResult.getValue();
			IAccount account = opinion.getAccount();
			String body = String.format("Opinion: #%s (%s) '%s'\nAccount: #%s %s\nPreview: %s\n", 
					eventArgs.getOpinionId(), 
					OpinionType.fromInt(eventArgs.getOpinionTypeId()),
					opinion.getName(),
					opinion.getAccountId(),
					account.getName(),
					opinion.getPreviewUrl(false));
			EventTypesManager.getInstance().sendEventToSubscribers(EventType.OpinionCreated, body);
		}
		
		return AutomationBaseOperationResult.Ok;
	}

}
