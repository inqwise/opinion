package com.inqwise.opinion.automation.jobs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Trigger;

import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.jobs.IJobExecutorCallback;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class QuartzJobWrapper implements Job {
	
	public static final String CALLBACK_KEY = "callback";
	public static final String SETTINGS_KEY = "settings";
	private static ApplicationLog logger = ApplicationLog.getLogger(QuartzJobWrapper.class);
	private com.inqwise.opinion.automation.common.jobs.Job job;
	JobSettings settings;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {	
			if(null == job){
				JobDataMap map = arg0.getMergedJobDataMap();
				settings = (JobSettings)map.get(SETTINGS_KEY);
				IJobExecutorCallback callback = (IJobExecutorCallback)map.get(CALLBACK_KEY);
				job = generateJob(settings, callback);
			}
			
			Date nextFireTime = null;
			List<? extends Trigger> triggers = arg0.getScheduler().getTriggersOfJob(arg0.getJobDetail().getKey());
			for (Trigger trigger : triggers) {
				if(null != trigger.getNextFireTime() && (null == nextFireTime || nextFireTime.after(trigger.getNextFireTime()))){
					nextFireTime = trigger.getNextFireTime();
				}
			}
			
			job.run(nextFireTime);
		} catch (Throwable t){
			logger.error(t, "execute : unexpected error occured. Assembly: '%s'", settings.getJobAssemblyName());
			throw new JobExecutionException(true);
		}
	}
	
	private static com.inqwise.opinion.automation.common.jobs.Job generateJob(JobSettings settings, IJobExecutorCallback callback) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> clazz = Class.forName(settings.getJobAssemblyName()); 
		Constructor<?> ctor = clazz.getConstructor(JobSettings.class, IJobExecutorCallback.class);
		com.inqwise.opinion.automation.common.jobs.Job job = (com.inqwise.opinion.automation.common.jobs.Job)ctor.newInstance(settings, callback);
		logger.info("generateJob : job #%s generated", settings.getId());
		return job; 
	}
}
