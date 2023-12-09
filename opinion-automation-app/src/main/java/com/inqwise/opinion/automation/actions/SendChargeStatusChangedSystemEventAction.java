package com.inqwise.opinion.automation.actions;

import com.inqwise.opinion.automation.common.IEventAction;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.events.ChargeStatusChangedEventArgs;
import com.inqwise.opinion.automation.managers.EventTypesManager;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.pay.ChargeStatus;

public class SendChargeStatusChangedSystemEventAction implements IEventAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7679608574257712356L;
	private static final ApplicationLog logger = ApplicationLog.getLogger(SendChargeStatusChangedSystemEventAction.class);
	private ChargeStatusChangedEventArgs args;
	
	public SendChargeStatusChangedSystemEventAction(ChargeStatusChangedEventArgs args) {
		this.args = args;
	}
	
	@Override
	public IOperationResult run() {
		
		ChargeStatus status = ChargeStatus.fromInt(args.getStatusId());
		switch (status) {
			case Canceled:
			case Credit:
			case Refund:
			case Void:
				StringBuilder sb = new StringBuilder();
				sb.append("Status of charge #").append(args.getChargeId()).append("\n");
				sb.append("changed to: ").append(status);
				EventTypesManager.getInstance().sendEventToSubscribers(EventType.ChargeStatusChanged, sb.toString());
				break;
			default:
				break;
		}
		return BaseOperationResult.Ok;
	}

}
