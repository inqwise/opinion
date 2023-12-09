package com.inqwise.opinion.automation.common.errorHandle;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.automation.common.jobs.JobSettings;

@XmlRootElement
@XmlType
@XmlSeeAlso({JobSettings[].class, EventType[].class})
public class AutomationOperationResult<T> extends AutomationBaseOperationResult implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4424990341026677082L;
	private T value;
	
	@XmlElement(name = "value")
	public T getValue() throws NullPointerException{
		if(hasError()){
			throw new NullPointerException("No value when error exist");
		}
		return value;
	}
	
	public boolean hasValue(){
		return null != value;
	}
	
	public void setValue(T value){
		if(hasError()){
			throw new NullPointerException("No value when error exist");
		}
		
		if(value == null){
			throw new NullPointerException("Value is mandatory");
		}
		
		this.value = value;
	}
	
	public AutomationOperationResult(T value){
		this(value, -1L);
	}
	
	public AutomationOperationResult(T value, Long transactionId) {
		super(transactionId);
		this.value = value;
	}

	public AutomationOperationResult(AutomationErrorCode error){
		super(error, UUID.randomUUID());
	}
	
	public AutomationOperationResult(AutomationErrorCode error, UUID errorId){
		super(error, errorId);
	}
	
	public AutomationOperationResult(AutomationErrorCode error, UUID errorId, String description){
		super(error, errorId, description);
	}
	
	public AutomationOperationResult(AutomationErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		super(error, errorId, descriptionFormat, args);
	}
	
	public AutomationOperationResult() throws RemoteException {
		error = AutomationErrorCode.NoError;
	}

	public String toString(){
		return hasError() ? error.toString() : value.toString();
	}
	
	@Override
	public void setError(AutomationErrorCode error, UUID errorId, String description) {
		value = null;
		super.setError(error, errorId, description);
	}
	
	@Override
	public void setError(AutomationBaseOperationResult result, boolean isOverwrite) {
		value = null;
		super.setError(result, isOverwrite);
	}

	public void setError(AutomationErrorCode error) {
		setError(error, UUID.randomUUID(), null);
	}
}