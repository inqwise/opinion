package com.inqwise.opinion.payments.common.errorHandle;

import java.util.UUID;

public class PayOperationResult<T> extends PayBaseOperationResult{	
	
	private T value;
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
	
	public PayOperationResult(T value){
		this(value, -1L);
	}
	
	public PayOperationResult(T value, Long transactionId) {
		super(transactionId);
		this.value = value;
	}

	public PayOperationResult(PayErrorCode error){
		super(error, UUID.randomUUID());
	}
	
	public PayOperationResult(PayErrorCode error, UUID errorId){
		super(error, errorId);
	}
	
	public PayOperationResult(PayErrorCode error, UUID errorId, String description){
		super(error, errorId, description);
	}
	
	public PayOperationResult(PayErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		super(error, errorId, descriptionFormat, args);
	}
	
	public PayOperationResult() {
		error = PayErrorCode.NoError;
	}

	public String toString(){
		return hasError() ? error.toString() : value.toString();
	}
	
	@Override
	public void setError(PayErrorCode error, UUID errorId, String description) {
		value = null;
		super.setError(error, errorId, description);
	}
	
	@Override
	public void setError(PayBaseOperationResult result, boolean isOverwrite) {
		value = null;
		super.setError(result, isOverwrite);
	}

	public void setError(PayErrorCode error) {
		setError(error, UUID.randomUUID(), null);
	}
}