package com.inqwise.opinion.automation.managers;

import java.util.Hashtable;

import com.inqwise.opinion.automation.common.FireEventArgs;
import com.inqwise.opinion.automation.common.IFireEventWorkflow;
import com.inqwise.opinion.automation.common.events.ChargeStatusChangedEventArgs;
import com.inqwise.opinion.automation.common.events.LoginEventArgs;
import com.inqwise.opinion.automation.common.events.OpinionCreatedEventArgs;
import com.inqwise.opinion.automation.common.events.ParticipantCompletedEventArgs;
import com.inqwise.opinion.automation.common.events.PayProcessorRefundCompletedEventArgs;
import com.inqwise.opinion.automation.common.events.PayProcessorTxnCompletedEventArgs;
import com.inqwise.opinion.automation.common.events.PaymentEventArgs;
import com.inqwise.opinion.automation.common.events.RegistrationEventArgs;
import com.inqwise.opinion.automation.workflows.ChargeStatusChangedEventWorkflow;
import com.inqwise.opinion.automation.workflows.LoginCompletedWorkflow;
import com.inqwise.opinion.automation.workflows.OpinionCreatedEventWorkflow;
import com.inqwise.opinion.automation.workflows.ParticipantCompletedWorkflow;
import com.inqwise.opinion.automation.workflows.PayProcessorRefundCompletedEventWorkflow;
import com.inqwise.opinion.automation.workflows.PayProcessorTxnCompletedWorkflow;
import com.inqwise.opinion.automation.workflows.PaymentEventWorkflow;
import com.inqwise.opinion.automation.workflows.RegistrationEventWorkflow;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

final class EventsWorkflowFactory {
	static final ApplicationLog logger = ApplicationLog.getLogger(EventsWorkflowFactory.class);
	private static EventsWorkflowFactory instance;
	public static EventsWorkflowFactory getInstance(){
		if(null == instance){
			synchronized (EventsWorkflowFactory.class) {
				if(null == instance){
					instance = new EventsWorkflowFactory();
				}
			}
		}
		
		return instance;
	}
	
	private Hashtable<Class<? extends FireEventArgs>, IFireEventWorkflow> workflowsSet;
	
	private EventsWorkflowFactory() {
		workflowsSet = new Hashtable<Class<? extends FireEventArgs>, IFireEventWorkflow>();
		workflowsSet.put(ChargeStatusChangedEventArgs.class, new ChargeStatusChangedEventWorkflow());
		workflowsSet.put(RegistrationEventArgs.class, new RegistrationEventWorkflow());
		workflowsSet.put(PaymentEventArgs.class, new PaymentEventWorkflow());
		workflowsSet.put(PayProcessorTxnCompletedEventArgs.class, new PayProcessorTxnCompletedWorkflow());
		workflowsSet.put(PayProcessorRefundCompletedEventArgs.class, new PayProcessorRefundCompletedEventWorkflow());
		workflowsSet.put(OpinionCreatedEventArgs.class, new OpinionCreatedEventWorkflow());
		workflowsSet.put(ParticipantCompletedEventArgs.class, new ParticipantCompletedWorkflow());
		workflowsSet.put(LoginEventArgs.class, new LoginCompletedWorkflow());
	}
	
	public IFireEventWorkflow getWorkflow(FireEventArgs args){
		IFireEventWorkflow workflow = workflowsSet.get(args.getClass());
		if(null == workflow){
			logger.warn("getWorkflow: Unable to find workflow for eventArgs of type '%s'", args.getClass());
		}
		return workflow;
	}
}
