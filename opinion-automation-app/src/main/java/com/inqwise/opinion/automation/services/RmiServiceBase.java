package com.inqwise.opinion.automation.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class RmiServiceBase extends UnicastRemoteObject {

	protected RmiServiceBase() throws RemoteException {
		super();
	}
	
	
	
}
