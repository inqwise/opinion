package com.inqwise.opinion.automation.managers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.inqwise.opinion.automation.common.IFireEventWorkflow;
import com.inqwise.opinion.automation.common.FireEventArgs;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class EventsManager {
	static final ApplicationLog logger = ApplicationLog.getLogger(EventsManager.class);
	private BlockingQueue<FireEventArgs> queue = null;
	private boolean done = false;
	
	private static EventsManager instance;
	public static EventsManager getInstance(){
		if(null == instance){
			synchronized (EventsManager.class) {
				if(null == instance){
					instance = new EventsManager();
				}
			}
		}
		
		return instance;
	}
	
	public EventsManager() {
		try{
			queue = new LinkedBlockingQueue<FireEventArgs>();
			Thread t = new Thread( new Runnable() {
				
				@Override
				public void run() {
					processFireEvents();
				}
			});
			t.setPriority(Thread.MIN_PRIORITY);
			t.start();
		} catch(Throwable t){
			logger.error(t, "ctor() : start executeSend thread failed.");
		}
	}
	
	private void processFireEvents(){
		try{
		while (!done) {
			FireEventArgs args = queue.take();
			if(null == args) {
				synchronized (queue) {
					while(queue.isEmpty()){
						queue.wait(3000);
					}
				}
			} else {
				internalFireEvent(args);
			}
		}
		} catch(Throwable t){
			logger.error(t, "processFireEvents : Fatal error occured. Process interrupted");
		}
	}
	
	public void fireEvent(FireEventArgs args) throws InterruptedException{
		synchronized (queue) {
			queue.put(args);
			queue.notify();
		}
	}
	
	protected void finalize() throws Throwable
	{
		finish();
		super.finalize();
	} 

	public void finish(){
		if(!done){
			done = true;
			if(null != queue){
				queue = null;
			}
		}
	}
	
	private static boolean internalFireEvent(FireEventArgs args){
		try{
			logger.debug("Starting to process event from queue.");
			IFireEventWorkflow workflow = EventsWorkflowFactory.getInstance().getWorkflow(args);
			workflow.process(args);
			return true;
		} catch(Throwable t){
			logger.error(t, "internalFireEvent() : FaUnexpected error occured.");
			return false;
		}
	}
}
