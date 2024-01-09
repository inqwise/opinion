package com.inqwise.opinion;

import com.inqwise.opinion.automation.common.jobs.JobScheduleTask;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.automation.common.jobs.JobStatus;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.jobs.CollectorsChecker;
import com.inqwise.opinion.managers.CollectorsManager;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobSettings js = new JobSettings();
		js.setScheduleTask(JobScheduleTask.Daily);
		js.setScheduleAt("00:01");
		js.setStatus(JobStatus.Started);
		CollectorsChecker ch = new CollectorsChecker(js, null);
		OperationResult<ICollector> collectorResult = CollectorsManager.get(160l, null);
		
		IOperationResult checkResult = ch.checkVerifiedCollector(collectorResult.getValue());
		if(checkResult.hasError()){
			System.out.println(checkResult);
		} else {
			System.out.println("Done");
		}

	}

}
