package com.inqwise.opinion.automation.services;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.UUID;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.automation.common.services.IJobsService;
import com.inqwise.opinion.automation.managers.JobsManager;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class JobsService extends RmiServiceBase implements IJobsService {

	ApplicationLog logger = ApplicationLog.getLogger(JobsService.class);
	public JobsService() throws RemoteException {
		super();
	}
	
	@Override
	public AutomationOperationResult<JobSettings[]> getJobs() throws RemoteException{
		AutomationOperationResult<JobSettings[]> result = new AutomationOperationResult<JobSettings[]>();
		try{
			Collection<JobSettings> values = JobsManager.getInstance().getSettings().values();
			result.setValue((JobSettings[]) values.toArray(new JobSettings[values.size()]));
		} catch (Throwable t){
			UUID errorId = logger.error(t, "getJobs : Unexpected error occured.");
			result.setError(AutomationErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	@Override
	public AutomationBaseOperationResult executeJob(int jobId) throws RemoteException {
		AutomationBaseOperationResult result = null;
		try{
			result = JobsManager.getInstance().executeJob(jobId);
		} catch (Throwable t){
			UUID errorId = logger.error(t, "executeJob : Unexpected error occured.");
			result.setError(AutomationErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
}
