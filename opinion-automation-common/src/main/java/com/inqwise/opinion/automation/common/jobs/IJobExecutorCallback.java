package com.inqwise.opinion.automation.common.jobs;

import java.util.Date;

public interface IJobExecutorCallback {
	void ProcessCompleted(int jobId, JobStatus status, Date date);
}
