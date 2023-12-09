package com.inqwise.opinion.automation.managers;

import com.inqwise.opinion.automation.common.IEventAction;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.npstrandberg.simplemq.Message;
import com.npstrandberg.simplemq.MessageInput;
import com.npstrandberg.simplemq.MessageQueue;
import com.npstrandberg.simplemq.MessageQueueService;

public class ActionsManager {
	static final ApplicationLog logger = ApplicationLog.getLogger(ActionsManager.class);
	private MessageQueue queue;
	private boolean done = false;
	private static ActionsManager instance;
	public static ActionsManager getInstance(){
		if(null == instance){
			synchronized (ActionsManager.class) {
				if(null == instance){
					instance = new ActionsManager();
				}
			}
		}
		
		return instance;
	}
	
	private ActionsManager() {
		
		queue = MessageQueueService.getMessageQueue("EPActions", true);
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				processQueueActions();
			}
		});
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
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
				try{
					queue = null;
				} catch (Throwable t){
					logger.error(t, "finishThread() : put to queue null failed.");
				}
			}
		}
	}
	
	private void processQueueActions(){
		try {
			while (!done) {
				try{
					Message message = queue.receive();
					if(null == message) {
						synchronized (ActionsManager.class) {
							while(null != queue && queue.messageCount() <= 0){
								ActionsManager.class.wait(3000);
							}
						}
					} else {
						if(internalProcessAction(message.getObject())){
							queue.delete(message);
						}
					}
				} catch(Throwable t){
					logger.error(t, "processQueueActions : Unexpected error occured");
				}
			}
		} catch (Throwable t){
			logger.error(t, "executeSend() : Fatal error occured.");
		}
	}
	
	public void processAction(IEventAction action){
		MessageInput message = new MessageInput();
		message.setObject(action);
		queue.send(message);
		synchronized (ActionsManager.class) {
			ActionsManager.class.notify();
		}
		
	}
	
	private boolean internalProcessAction(Object obj){
		IEventAction action = null;
		try{
			action = (IEventAction)obj;
			logger.info("internalProcessAction : action received: '%s'", action);
			IOperationResult actionResult = action.run();
			
			if(logger.IsDebugEnabled()){
				logger.debug("internalProcessAction : finished to run action: '%s'", action);
			}
			
			return actionResult.getErrorCode() == 0;
			
		} catch(Throwable t){
			logger.error(t, "internalProcessAction : Unexpected error occured. action: '%s'", action);
			return false;
		}
	}
}
