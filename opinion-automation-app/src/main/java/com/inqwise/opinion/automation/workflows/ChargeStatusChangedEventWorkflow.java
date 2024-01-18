package com.inqwise.opinion.automation.workflows;

import java.lang.reflect.Constructor;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import com.inqwise.opinion.automation.actions.SendChargeStatusChangedSystemEventAction;
import com.inqwise.opinion.automation.common.ActionException;
import com.inqwise.opinion.automation.common.ChargePostPayActionArgs;
import com.inqwise.opinion.automation.common.IEventAction;
import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.events.ChargeStatusChangedEventArgs;
import com.inqwise.opinion.automation.managers.ActionsManager;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.pay.ChargeModel;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.managers.ChargesManager;

public class ChargeStatusChangedEventWorkflow extends Workflow<ChargeStatusChangedEventArgs> {

	static ApplicationLog logger = ApplicationLog.getLogger(ChargeStatusChangedEventWorkflow.class);
			
	@Override
	public AutomationBaseOperationResult processWorkflow(ChargeStatusChangedEventArgs args) throws RemoteException {
		AutomationBaseOperationResult result = null;
		List<IEventAction> actions = null;
		
		AutomationOperationResult<List<IEventAction>> actionsResult = identifyActions(args);
		if(actionsResult.hasError()){
			result = actionsResult;
		} else {
			actions = actionsResult.getValue();
		}
		
		if(null == result){ 
			if(actions.size() == 0){
				logger.warn("InvoiceStatusChangedEventWorkflow : No actions found for charge #%s", args.getChargeId());
			} else {
				for (IEventAction action : actions) {
					ActionsManager.getInstance().processAction(action);
				}
			}
			
			result = AutomationBaseOperationResult.Ok;
		}
		
		return result;
	}

	public static AutomationOperationResult<List<IEventAction>> identifyActions(ChargeStatusChangedEventArgs args) throws RemoteException {
		
		// 1. get charges and detect serviceId's;
		// 2. get services with activation actions;
		// 3. execute activation actions;
		AutomationOperationResult<List<IEventAction>> result = new AutomationOperationResult<>();
		try{
			
			ChargeStatus status = ChargeStatus.fromInt(args.getStatusId());
			List<IEventAction> list = new ArrayList<IEventAction>();
			if(status == ChargeStatus.Paid){
				identifyPaidChargeActions(args.getChargeId(), args.getSourceId(), list);
			} else {
				list.add(new SendChargeStatusChangedSystemEventAction(args));
			}
			result.setValue(list);
		} catch (Throwable ex){
			UUID errorId = logger.error(ex, "identifyActions : unexpected error occured.");
			result.setError(AutomationErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static void identifyPaidChargeActions(long chargeId, int sourceId, List<IEventAction> list) throws ActionException {
		List<ChargeModel> chargeList = ChargesManager.getPostPayActions(chargeId);
		for(var chargeModel : chargeList){
			IEventAction action = identifyAction(chargeModel, sourceId);
			list.add(action);
		}
	}

	private static IEventAction identifyAction(final ChargeModel chargeModel,final int sourceId) throws ActionException {
		
		final long chargeId = chargeModel.getId();
		String postPayAction = chargeModel.getPostPayAction();
		final Integer referenceTypeId = chargeModel.getReferenceType().getValue();
		final Long referenceId = chargeModel.getReferenceId();
		String strPostPayActionData = chargeModel.getPostPayActionData();
		JSONObject postPayActionData = null;
		try {
			postPayActionData = (null == strPostPayActionData ? null : new JSONObject(strPostPayActionData));
		} catch(Exception ex){
			logger.error(ex, "identifyAction: Failed to parse postPayActionData");
		}
		
		ChargePostPayActionArgs actionArgs = new ChargePostPayActionArgs();
		actionArgs.setChargeId(chargeId);
		actionArgs.setSourceId(sourceId);
		actionArgs.setReferenceTypeId(referenceTypeId);
		actionArgs.setChargeReferenceId(referenceId);
		actionArgs.setCountOf(JSONHelper.optInt(postPayActionData, IServicePackage.JsonNames.COUNT_OF_MONTHS, 1));
				
		IEventAction action;
		try{
			Class<?> clazz = Class.forName(postPayAction); 
			Constructor<?> ctor = clazz.getConstructor(ChargePostPayActionArgs.class); 
			action = (IEventAction)ctor.newInstance(new Object[] { actionArgs }); 
		} catch(Exception ex){
			throw new ActionException(String.format("identifyAction: Failed to generate instance. postAction:'%s', chargeId:'%s'", postPayAction, chargeId), ex);
		}
		
		return action;
	}
}
