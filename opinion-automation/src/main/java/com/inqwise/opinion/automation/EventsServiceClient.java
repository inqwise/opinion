package com.inqwise.opinion.automation;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.inqwise.opinion.automation.common.FireEventArgs;
import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.services.IEventsService;

public class EventsServiceClient extends RmiClient implements IEventsService {

	private static EventsServiceClient instance;
	private static Object instanceLocker = new Object();
	
	public static EventsServiceClient getInstance() throws MalformedURLException, RemoteException, NotBoundException{
		
		if (null == instance) {
			synchronized (instanceLocker) {
				if (null == instance) {
					instance = new EventsServiceClient();
				}
			}
		}
		return instance;
	}
	
	private IEventsService service;
	private EventsServiceClient() throws MalformedURLException, RemoteException, NotBoundException{
		service = (IEventsService) registry.lookup(IEventsService.class.getSimpleName());
	}
	
	@Override
	public AutomationOperationResult<EventType[]> getEventTypes() throws RemoteException {
		try {
			return service.getEventTypes();
		} catch (ConnectException e) {
			instance = null;
			throw(e);
		}
	}
	@Override
	public AutomationBaseOperationResult setEventType(EventType eventType) throws RemoteException {
		try {
			return service.setEventType(eventType);
		} catch (ConnectException e) {
			instance = null;
			throw(e);
		}
	}
	@Override
	public AutomationOperationResult<EventType> getEventType(int id)
			throws RemoteException {
		try {
			return service.getEventType(id);
		} catch (ConnectException e) {
			instance = null;
			throw(e);
		}
	}
	@Override
	public AutomationBaseOperationResult fire(FireEventArgs args)
			throws RemoteException {
		try {
			return service.fire(args);
		} catch (ConnectException e) {
			instance = null;
			throw new ConnectException("Failed to connect to Automation. automation.endpoint=" + ApplicationConfiguration.getEndpoint(), e);
		}
	}
	
	public AutomationBaseOperationResult fireSaved(FireEventArgs args) {
		try {
			return fire(args);
		} catch (Throwable e) {
			return new AutomationBaseOperationResult(AutomationErrorCode.CommunicationFailed, e.toString());
		}
	}
}
