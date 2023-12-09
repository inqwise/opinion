package com.inqwise.opinion.automation;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public abstract class RmiClient {
	protected static Registry registry;
	static {
		try {
			//System.setProperty("java.rmi.server.hostname", ApplicationConfiguration.getEndpoint());
			registry = LocateRegistry.getRegistry(ApplicationConfiguration.getEndpoint());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
