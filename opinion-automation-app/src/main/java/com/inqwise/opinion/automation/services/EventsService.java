package com.inqwise.opinion.automation.services;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.inqwise.opinion.automation.common.FireEventArgs;
import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.services.IEventsService;
import com.inqwise.opinion.automation.managers.EventTypesManager;
import com.inqwise.opinion.automation.managers.EventsManager;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class EventsService extends RmiServiceBase implements
		IEventsService {

	ApplicationLog logger = ApplicationLog.getLogger(EventsService.class);
	
	public EventsService() throws RemoteException {
		super();
	}

	@Override
	public AutomationOperationResult<EventType[]> getEventTypes()
			throws RemoteException {
		
		AutomationOperationResult<EventType[]> result = new AutomationOperationResult<>();
		try{
			Collection<EventType> values = EventTypesManager.getInstance().getAll().values();
			result.setValue((EventType[]) values.toArray(new EventType[values.size()]));
		} catch (Throwable t){
			UUID errorId = logger.error(t, "getEventTypes : Unexpected error occured.");
			result.setError(AutomationErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	@Override
	public AutomationBaseOperationResult setEventType(EventType eventType) {
		AutomationBaseOperationResult result;
		try{
			result = EventTypesManager.getInstance().setEventType(eventType);
		} catch (Throwable t){
			UUID errorId = logger.error(t, "setEventType : Unexpected error occured.");
			result = new AutomationOperationResult<Map<Integer, EventType>>(AutomationErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	@Override
	public AutomationOperationResult<EventType> getEventType(int id)
			throws RemoteException {
		AutomationOperationResult<EventType> result = new AutomationOperationResult<>();
		try{
			result.setValue(EventTypesManager.getInstance().get(id));
		} catch (Throwable t){
			UUID errorId = logger.error(t, "getEventType('%s') : Unexpected error occured.", id);
			result.setError(AutomationErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	@Override
	public AutomationBaseOperationResult fire(FireEventArgs args)
			throws RemoteException {
		AutomationBaseOperationResult result = null;
		try {
			if(null == args){
				logger.error("fireEvent : received event without args");
				result = new AutomationBaseOperationResult(AutomationErrorCode.ArgumentIsNull);
			}
			
			if(null == result){
				logger.info("fireEvent : received event: '%s', from source: '%s'", args, args.getSourceId());
				EventsManager.getInstance().fireEvent(args);
				logger.debug("fireEvent : done with event: '%s', from source: '%s'", args, args.getSourceId());
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "fireEvent : Unexpected error occured.");
			result = new AutomationBaseOperationResult(AutomationErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
