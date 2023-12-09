package com.inqwise.opinion.automation.common.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.jobs.JobSettings;

public interface IJobsService extends Remote {

	AutomationBaseOperationResult executeJob(int jobId) throws RemoteException;

	AutomationOperationResult<JobSettings[]> getJobs() throws RemoteException;

}
