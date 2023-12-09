package com.inqwise.opinion.automation.common.jobs;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;

public class JobSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8149078592614470288L;
	private int id;
	private String description;
	private Date lastExecutionDate;
	private String scheduleAt;
	private JobScheduleTask scheduleTask;
	private Date planedExecutionDate;
	private JobStatus status;
	private JobStatus lastExecutionStatus;
	private String jobAssemblyName;
	public JobSettings(ResultSet reader) throws SQLException {
		setId(reader.getInt("job_id"));
		setDescription(ResultSetHelper.optString(reader, "job_description"));
		setLastExecutionDate(ResultSetHelper.optDate(reader, "job_last_execution_date"));
		setScheduleAt(ResultSetHelper.optString(reader, "job_schedule_at"));
		setScheduleTask(JobScheduleTask.fromInt(reader.getInt("job_schedule_task_id")));
		setJobAssemblyName(ResultSetHelper.optString(reader, "assembly_name"));
	}
	
	public JobSettings() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getLastExecutionDate() {
		return lastExecutionDate;
	}
	public void setLastExecutionDate(Date lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}
	public String getScheduleAt() {
		return scheduleAt;
	}
	public void setScheduleAt(String scheduleAt) {
		this.scheduleAt = scheduleAt;
	}
	public JobScheduleTask getScheduleTask() {
		return scheduleTask;
	}
	public void setScheduleTask(JobScheduleTask scheduleTask) {
		this.scheduleTask = scheduleTask;
	}
	
	public Date getPlanedExecutionDate() {
		return planedExecutionDate;
	}
	public void setPlanedExecutionDate(Date planedExecutionDate) {
		this.planedExecutionDate = planedExecutionDate;
	}
	public JobStatus getStatus() {
		return status;
	}
	public void setStatus(JobStatus status) {
		this.status = status;
	}
	public JobStatus getLastExecutionStatus() {
		return lastExecutionStatus;
	}
	public void setLastExecutionStatus(JobStatus lastExecutionStatus) {
		this.lastExecutionStatus = lastExecutionStatus;
	}
	public String getJobAssemblyName() {
		return jobAssemblyName;
	}
	public void setJobAssemblyName(String jobAssemblyName) {
		this.jobAssemblyName = jobAssemblyName;
	}
}
