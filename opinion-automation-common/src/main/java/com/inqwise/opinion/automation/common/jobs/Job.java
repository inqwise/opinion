package com.inqwise.opinion.automation.common.jobs;

import java.util.Date;
import java.util.UUID;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

public abstract class Job {

	protected final static ApplicationLog logger = ApplicationLog.getLogger(Job.class);
	private JobSettings settings;
	private IJobExecutorCallback callback;
	
	public JobSettings getSettings(){
		return settings;
	}
	
	protected Job(JobSettings settings, IJobExecutorCallback callback) {
		this.settings = settings;
		this.callback = callback;
		
		settings.setStatus(JobStatus.Started);
	}
	
	public IOperationResult run(Date nextPlanedExecutionDate) {
		logger.debug("run : Enter");
		settings.setPlanedExecutionDate(nextPlanedExecutionDate);
		IOperationResult result = null;
		
		JobStatus previousStatus = null;
		
		if(settings.getStatus() == JobStatus.Started) {
			synchronized (settings){
				if(settings.getStatus() == JobStatus.Started) {
					logger.info("Start to run Job '%s'", this);
					
					try {
						previousStatus = settings.getStatus();
						settings.setStatus(JobStatus.InProcess);
						
						IOperationResult processResult = process();
						if(processResult.hasError()){
							result = processResult;
						}
						
						settings.setStatus(previousStatus);
						
						if(null == result){
							settings.setLastExecutionStatus(JobStatus.Success);
						} else {
							settings.setLastExecutionStatus(JobStatus.Failed);
						}
						
						settings.setLastExecutionDate(new Date());
						
						callback.ProcessCompleted(getId(), settings.getLastExecutionStatus(), settings.getLastExecutionDate());
						result = AutomationBaseOperationResult.Ok;
						
					} catch (Throwable t){
						settings.setStatus(previousStatus);
						UUID errorId = logger.error(t, "Failed to process job");
						result = new AutomationBaseOperationResult(AutomationErrorCode.GeneralError, errorId);
					}
					
					logger.info("Finished to run Job '%s' with status: '%s', next planed execution date: '%s'",
							this, settings.getLastExecutionStatus(),
							JSONHelper.getDateFormat(settings.getPlanedExecutionDate(), "dd-MMM-yy HH:mm"));
				}
			}
		}
		
		logger.debug("run : Exit");
		return result;
	}
	
	private int getId() {
		return settings.getId();
	}

	protected abstract IOperationResult process();
	
	public Date getPlanedExecutionDate() {
		return getSettings().getPlanedExecutionDate();
	}

	public void setStatus(JobStatus status) {
		getSettings().setStatus(status);
	}
}
