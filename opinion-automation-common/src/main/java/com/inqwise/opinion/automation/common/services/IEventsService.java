package com.inqwise.opinion.automation.common.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.inqwise.opinion.automation.common.FireEventArgs;
import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;

public interface IEventsService extends Remote {
	// EventType
	AutomationOperationResult<EventType[]> getEventTypes() throws RemoteException;
	AutomationBaseOperationResult setEventType(EventType eventType) throws RemoteException;
	AutomationOperationResult<EventType> getEventType(int id) throws RemoteException;
	// End EventType
	
	// Events
	AutomationBaseOperationResult fire(FireEventArgs args) throws RemoteException;
}
