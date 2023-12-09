package com.inqwise.opinion.automation.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.listeners.TriggerListenerSupport;

public class QuartzTriggerListener extends TriggerListenerSupport {

	private String name;
	public QuartzTriggerListener(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
		CompletedExecutionInstruction triggerInstructionCode) {
		
	}
	
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		// do something with the event
	}
	
	public void triggerMisfired(Trigger trigger) {
		//trigger.getJobDataMap()
	}
	
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// do something with the event
		return false;
	}
}
