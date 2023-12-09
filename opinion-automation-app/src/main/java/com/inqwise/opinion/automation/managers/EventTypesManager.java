package com.inqwise.opinion.automation.managers;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.errorHandle.AutomationErrorCode;
import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.dao.EventTypesDataAccess;
import com.inqwise.opinion.infrastructure.common.EmailProviderException;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration.Email;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.managers.ProductsManager;

public class EventTypesManager {
	static final ApplicationLog logger = ApplicationLog.getLogger(EventTypesManager.class);
	
	private static EventTypesManager instance;
	public static EventTypesManager getInstance(){
		if(null == instance){
			synchronized (EventsManager.class) {
				if(null == instance){
					instance = new EventTypesManager();
				}
			}
		}
		
		return instance;
	}
	
	private Map<Integer,EventType> eventTypes;
	
	private EventTypesManager() {
		LoadEventTypes();
	}

	private void LoadEventTypes() {
		final Map<Integer,EventType> eventTypes = new Hashtable<>();
		try {
			IResultSetCallback callback = new IResultSetCallback() {
				
				@Override
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						EventType eventType = new EventType(reader);
						eventTypes.put(eventType.getId(), eventType);
					}
				}
			};
		
			EventTypesDataAccess.fillEventTypes(callback);
			this.eventTypes = eventTypes;
			
		} catch (Exception e) {
			logger.error(e, "LoadEventTypes : unexpected error occured");
			throw new Error(e);
		}
	}
	
	public Map<Integer,EventType> getAll(){
		return eventTypes;
	}
	
	public AutomationBaseOperationResult setEventType(EventType eventType){
		AutomationBaseOperationResult result;
		try{
			EventTypesDataAccess.setEventType(eventType);
			LoadEventTypes();
			result = AutomationBaseOperationResult.Ok;
		} catch (Throwable t){
			UUID errorId = logger.error(t, "setEventType : Unexpected error occured");
			result = new AutomationBaseOperationResult(AutomationErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public EventType get(int id){
		return eventTypes.get(id);
	}
	
	public void sendEventToSubscribers(int eventTypeId, String body){
		EventType eventType = get(eventTypeId);
		if(null == eventType){
			logger.warn("sendEventToSubscribers : eventType #%s not exist", eventTypeId);
		} else if(StringUtils.isNotBlank(eventType.getRecipients())){
			String subject = StringUtils.join(new Object[] {"ApplicationEvent - ", eventType.getName(), " #",eventTypeId});
			IProduct product = ProductsManager.getCurrentProduct(); // only for no-reply email
			try {
				EmailProvider.getInstance().sendText(product.getNoreplyEmail(), eventType.getRecipients(), subject, body);
			} catch (EmailProviderException e) {
				logger.error(e, "sendEventToSubscribers: failed to send email.");
			}
		} else {
			if(logger.IsDebugEnabled()){
				logger.debug("No subscribers for eventType #%s '%s'", eventTypeId, eventType.getName());
			}
		}
	}
}
