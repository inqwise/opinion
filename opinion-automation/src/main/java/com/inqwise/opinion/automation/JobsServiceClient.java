package com.inqwise.opinion.automation;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.automation.common.services.IJobsService;

public class JobsServiceClient extends RmiClient implements IJobsService {
	
	private static JobsServiceClient instance;
	private static Object instanceLocker = new Object();
	
	public static JobsServiceClient getInstance() throws MalformedURLException, RemoteException, NotBoundException{
		
		if (null == instance) {
			synchronized (instanceLocker) {
				if (null == instance) {
					instance = new JobsServiceClient();
				}
			}
		}
		return instance;
	}
	
	private IJobsService service;
	private JobsServiceClient() throws MalformedURLException, RemoteException, NotBoundException{
		//java.rmi.server.hostname
		service = (IJobsService) registry.lookup(IJobsService.class.getSimpleName());
	}
	
	@Override
	public AutomationBaseOperationResult executeJob(int jobId) throws RemoteException {
		try {
			return service.executeJob(jobId);
		} catch (ConnectException e) {
			instance = null;
			throw(e);
		}
	}
	
	@Override
	public AutomationOperationResult<JobSettings[]> getJobs() throws RemoteException {
		try {
			return service.getJobs();
		} catch (ConnectException e) {
			instance = null;
			throw(e);
		}
	}
}
