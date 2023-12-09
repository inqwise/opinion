package com.inqwise.opinion.handlers;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.Format;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.JobsServiceClient;
import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.opinion.common.IPostmasterContext;

public class ControlEntry extends Entry {

	protected ControlEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject getSystemEvents(JSONObject input) throws MalformedURLException, JSONException, RemoteException, NotBoundException{
		JSONObject output;
		IOperationResult result = null;
		EventType[] events = null;
		
		AutomationOperationResult<EventType[]> systemEventsResult = EventsServiceClient.getInstance().getEventTypes();
		if(systemEventsResult.hasError()){
			result = systemEventsResult;
		} else {
			events = systemEventsResult.getValue();
		}
		
		if(null == result){
			output = new JSONObject();
			JSONArray ja = new JSONArray();
			Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			for (EventType eventType : events) {
				JSONObject eventJo = new JSONObject();
				eventJo.put("id", eventType.getId());
				eventJo.put("name", eventType.getName());
				eventJo.put("recipients", eventType.getRecipients());
				eventJo.put("description", eventType.getDescription());
				eventJo.put("modifyDate", formatter.format(eventType.getModifyDate()));
				ja.put(eventJo);
			}
			output.put("list", ja);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getSystemEventDetails(JSONObject input) throws MalformedURLException, JSONException, RemoteException, NotBoundException{
		JSONObject output;
		IOperationResult result = null;
		EventType eventType = null;
		
		AutomationOperationResult<EventType> systemEventsResult = EventsServiceClient.getInstance().getEventType(input.getInt("eventId"));
		if(systemEventsResult.hasError()){
			result = systemEventsResult;
		} else {
			eventType = systemEventsResult.getValue();
		}
		
		if(null == result){
			Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			JSONObject eventJo = new JSONObject();
			eventJo.put("id", eventType.getId());
			eventJo.put("name", eventType.getName());
			eventJo.put("recipients", eventType.getRecipients());
			eventJo.put("description", eventType.getDescription());
			eventJo.put("modifyDate", formatter.format(eventType.getModifyDate()));
			output = eventJo;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject updateSystemEvent(JSONObject input) throws MalformedURLException, JSONException, RemoteException, NotBoundException{
		JSONObject output;
		IOperationResult result = null;
		int id = input.getInt("eventId");
		String recipients = JSONHelper.optString(input, "recipients", null, "");
		
		EventType eventType = new EventType();
		eventType.setId(id);
		eventType.setRecipients(recipients);
		result = EventsServiceClient.getInstance().setEventType(eventType);
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject getJobs(JSONObject input) throws MalformedURLException, JSONException, RemoteException, NotBoundException{
		JSONObject output;
		IOperationResult result = null;
		JobSettings[] jobs = null;
		
		AutomationOperationResult<JobSettings[]> jobsResult = JobsServiceClient.getInstance().getJobs();
		if(jobsResult.hasError()){
			result = jobsResult;
		} else {
			jobs = jobsResult.getValue();
		}
		
		if(null == result){
			output = new JSONObject();
			JSONArray ja = new JSONArray();
			Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			for (JobSettings job : jobs) {
				JSONObject eventJo = new JSONObject();
				eventJo.put("id", job.getId());
				eventJo.put("assemblyName", job.getJobAssemblyName());
				if(null == job.getLastExecutionDate()){
					eventJo.put("lastExecutionDate", JSONObject.NULL);
				} else {
					eventJo.put("lastExecutionDate", formatter.format(job.getLastExecutionDate()));
				}
				eventJo.put("description", job.getDescription());

				if(null == job.getPlanedExecutionDate()){
					eventJo.put("planedExecutionDate", JSONObject.NULL);
				} else {
					eventJo.put("planedExecutionDate", formatter.format(job.getPlanedExecutionDate()));
				}

				eventJo.put("status", job.getStatus().toString());
				if(null != job.getLastExecutionStatus()){
					eventJo.put("lastExecutionStatus", job.getLastExecutionStatus().toString());
				}
				eventJo.put("scheduleAt", job.getScheduleAt());
				eventJo.put("scheduleTask", job.getScheduleTask().toString());
				ja.put(eventJo);
			}
			output.put("list", ja);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject executeJob(JSONObject input) throws MalformedURLException, JSONException, RemoteException, NotBoundException{
		JSONObject output;
		IOperationResult result = null;
		int jobId = input.getInt("jobId");
		
		AutomationBaseOperationResult executeResult = JobsServiceClient.getInstance().executeJob(jobId);
		if(executeResult.hasError()){
			result = executeResult;
		} 
		
		if(null == result){
			output = AutomationBaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
}
