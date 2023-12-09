package com.inqwise.opinion.automation.managers;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.jobs.IJobExecutorCallback;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.automation.common.jobs.JobStatus;
import com.inqwise.opinion.automation.dao.JobsDataAccess;
import com.inqwise.opinion.automation.jobs.QuartzJobWrapper;
import com.inqwise.opinion.automation.jobs.QuartzTriggerListener;
import com.inqwise.opinion.automation.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;

public class JobsManager implements IJobExecutorCallback {
static ApplicationLog logger = ApplicationLog.getLogger(JobsManager.class);
	
	private static JobsManager instance;
	public static JobsManager getInstance(){
		if(null == instance){
			synchronized (EventsManager.class) {
				if(null == instance){
					instance = new JobsManager();
				}
			}
		}
		
		return instance;
	}
	
	private Map<Integer, JobSettings> settingsMap;
	private Scheduler scheduler;
	
	private JobsManager() {
		initialize();
	}
	
	public void initialize(){
		try {
			// Grab the Scheduler instance from the Factory
			StdSchedulerFactory sf = new StdSchedulerFactory(ApplicationConfiguration.Quartz.getPropertiesFilePath());
			scheduler = sf.getScheduler();
			scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener("defaultListener"), EverythingMatcher.allTriggers());
			
		} catch (SchedulerException e) {
			logger.error(e, "initialize : Unexpected error occured");
			throw new Error("initialize : Unexpected error occured", e);
		}
	}
	
	public void startJobs(){
		logger.info("startJobs : Execution of the scheduler started");
		
		try {
			scheduler.start();
			
			for(JobSettings settings : getSettings().values()){
				
				try {
					JobDataMap map = new JobDataMap();
					map.put(QuartzJobWrapper.SETTINGS_KEY, settings);
					map.put(QuartzJobWrapper.CALLBACK_KEY, this);
					JobDetail job = newJob(QuartzJobWrapper.class).usingJobData(map)
									.withIdentity("job" + settings.getId(), "group" + settings.getId())
									.build();
					
					Trigger trigger = generateTrigger(settings);
					scheduler.scheduleJob(job,  trigger);
					
					settings.setStatus(JobStatus.Started);
					settings.setPlanedExecutionDate(trigger.getNextFireTime());
					logger.info("startJobs : Scheduling job: '%s', planed executionDate: '%s'", settings.getJobAssemblyName(), JSONHelper.getDateFormat(settings.getPlanedExecutionDate(), "dd-MMM-yy HH:mm"));
				} catch (Exception e) {
					logger.error(e, "startJobs : failed to start job #%s", settings.getId());
				}
			}
			
			logger.info("startJobs : Execution of the scheduler completed");
		} catch (SchedulerException e) {
			logger.error("startJobs : failed to start jobs", e);
		}
	}

	public AutomationBaseOperationResult executeJob(int id){
		
		AutomationBaseOperationResult result;
		JobKey jobKey = new JobKey("job" + id, "group" + id);
		try {
			scheduler.triggerJob(jobKey);
			result = AutomationBaseOperationResult.Ok;
		} catch (SchedulerException e) {
			UUID errorId = logger.error(e, "executeJob : failed to execute job #%s", id);
			result = new AutomationBaseOperationResult(AutomationErrorCode.GeneralError, errorId, e.getMessage());
		}
		
		return result;
	}
	
	private Trigger generateTrigger(JobSettings settings) {
		TriggerBuilder<Trigger> builder = newTrigger()
				.withIdentity("trigger" + settings.getId(), "group" + settings.getId());
		
		
		switch(settings.getScheduleTask()){
		case Daily:
			Date fromDate = new Date();
			if(null != settings.getScheduleAt()){
				String[] scheduleAtParts = StringUtils.split(settings.getScheduleAt(), ':');
				int hour = Integer.parseInt(scheduleAtParts[0]);
				int minute = 0;
				if(scheduleAtParts.length > 1){
					minute = Integer.parseInt(scheduleAtParts[1]);
				}
				
				if(DateUtils.getFragmentInHours(fromDate, Calendar.DAY_OF_YEAR) >= hour && DateUtils.getFragmentInMinutes(fromDate, Calendar.HOUR_OF_DAY) >= minute){
					fromDate = DateUtils.addDays(fromDate, 1);
				}
				
				fromDate = DateUtils.setMinutes(DateUtils.setHours(DateConverter.trim(fromDate), hour), minute);
			}
			builder.startAt(fromDate)
					.withSchedule(calendarIntervalSchedule()
									.withIntervalInDays(1)
									.withMisfireHandlingInstructionFireAndProceed());
			break;
		case CronExpression:
			builder.withSchedule(cronSchedule(settings.getScheduleAt())
									.withMisfireHandlingInstructionFireAndProceed());
			break;
		default:
			throw new UnsupportedOperationException("Unsupported schedule task: " + settings.getScheduleTask());
		}
		
		return builder.build();
	}

	public void stopJobs(){
		logger.info("stopJobs : Shootdown of the scheduler started ");
		try {
			if(!scheduler.isShutdown()){
				scheduler.shutdown();
			}
			
			for(JobSettings settings : getSettings().values()){
				settings.setStatus(JobStatus.Stopped);
			}
			
		} catch (SchedulerException e) {
			logger.error(e, "stopJobs : Unexpected error occured");
			throw new Error("stopJobs : Unexpected error occured", e);
		}
		logger.info("stopJobs : Shootdown of the scheduler completed");
	}
	
	public void LoadJobs(){
		settingsMap = new Hashtable<Integer, JobSettings>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				@Override
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						JobSettings settings = new JobSettings(reader);
						settingsMap.put(settings.getId(), settings);
					}
				}
			};
			
			JobsDataAccess.getJobs(callback);
		} catch (DAOException ex) {
			logger.error(ex, "getJobsFromDb : Unexpected error occured");
			throw new Error("Failed to load jobs", ex);
		}
	}
	
	public Map<Integer, JobSettings> getSettings() {
		if(null == settingsMap){
			synchronized (JobsManager.class) {
				if(null == settingsMap){
					LoadJobs();
				}
			}
		}
		return settingsMap;
	}

	@Override
	public void ProcessCompleted(int jobId, JobStatus status, Date date) {
		if(status == JobStatus.Success){
			try {
				JobsDataAccess.setLastExecutionTime(jobId, date);
			} catch (DAOException e) {
				logger.error(e, "ProcessCompleted: Failed to setLastExecutionTime for job #%s", jobId);
				throw new RuntimeException(e);
			}
		} else {
			JobSettings settings = settingsMap.get(jobId);
			StringBuilder sb = new StringBuilder();
			sb.append("Job #").append(jobId).append("'").append(settings.getJobAssemblyName()).append("' failed.");
			EventTypesManager.getInstance().sendEventToSubscribers(EventType.AutomationActionFailed, sb.toString());
		}		
	}
}
