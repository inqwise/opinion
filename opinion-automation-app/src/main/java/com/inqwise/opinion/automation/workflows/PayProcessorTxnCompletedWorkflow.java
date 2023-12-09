package com.inqwise.opinion.automation.workflows;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.events.PayProcessorTxnCompletedEventArgs;

public class PayProcessorTxnCompletedWorkflow extends Workflow<PayProcessorTxnCompletedEventArgs> {

	@Override
	protected AutomationBaseOperationResult processWorkflow(
			PayProcessorTxnCompletedEventArgs eventArgs) {
		
		return null;
	}

}
