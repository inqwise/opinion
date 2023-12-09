package com.inqwise.opinion.automation;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

import com.inqwise.opinion.automation.common.services.IEventsService;
import com.inqwise.opinion.automation.common.services.IJobsService;
import com.inqwise.opinion.automation.managers.ActionsManager;
import com.inqwise.opinion.automation.managers.JobsManager;
import com.inqwise.opinion.automation.managers.RmiServicesFactory;
import com.inqwise.opinion.automation.services.EventsService;
import com.inqwise.opinion.automation.services.JobsService;
import com.inqwise.opinion.automation.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class Initializer {
	static ApplicationLog logger = ApplicationLog.getLogger(Initializer.class);
	private static Registry lReg;
	public static void start() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, RemoteException, MalformedURLException, AlreadyBoundException{
		logger.info("Starting jobs");
		JobsManager.getInstance().startJobs();
		logger.info("Starting actions");
		ActionsManager.getInstance(); // initialize
		System.setProperty("java.rmi.server.hostname", ApplicationConfiguration.RMI.getIP());
		logger.info("Starting Rmi Services");
		try {
			lReg = LocateRegistry.createRegistry(ApplicationConfiguration.RMI.getRegistryPort());
        } catch (ExportException ee) {
        	lReg = LocateRegistry.getRegistry(ApplicationConfiguration.RMI.getRegistryPort());
        }
		
		RmiServicesFactory.startService(lReg, JobsService.class, IJobsService.class);
		RmiServicesFactory.startService(lReg, EventsService.class, IEventsService.class);
		
		logger.info("Add Shutdown Hook");
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { Initializer.stop(); }
		});

		logger.info("Started");
	}
	
	public static void stop(){
		// Stop jobs
		JobsManager.getInstance().stopJobs();
		// Stop actions
		ActionsManager.getInstance().finish();
		//Stop services
		RmiServicesFactory.stopAll(lReg);
		//Clear registry
		lReg = null;
	}
	
	public static void main(String[] args) throws Exception {
		start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { Initializer.stop(); }
		});
	}
}
