package com.inqwise.opinion.payments;

import com.inqwise.opinion.automation.common.jobs.IJobExecutorCallback;
import com.inqwise.opinion.automation.common.jobs.Job;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.infrastructure.common.IOperationResult;

class PayTransactionsCheckerJob extends Job {

	protected PayTransactionsCheckerJob(JobSettings settings, IJobExecutorCallback callback) {
		super(settings, callback);
	}

	@Override
	protected IOperationResult process() {
		return null;
	}

}
