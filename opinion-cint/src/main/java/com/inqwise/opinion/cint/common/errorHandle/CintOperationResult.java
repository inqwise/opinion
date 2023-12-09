package com.cint.common.errorHandle;

import java.util.UUID;

public class CintOperationResult<T> extends CintBaseOperationResult{	
	
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
	
	public CintOperationResult(T value){
		this(value, -1L);
	}
	
	public CintOperationResult(T value, Long transactionId) {
		super(transactionId);
		this.value = value;
	}

	public CintOperationResult(CintErrorCode error){
		super(error, UUID.randomUUID());
	}
	
	public CintOperationResult(CintErrorCode error, UUID errorId){
		super(error, errorId);
	}
	
	public CintOperationResult(CintErrorCode error, UUID errorId, String description){
		super(error, errorId, description);
	}
	
	public CintOperationResult(CintErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		super(error, errorId, descriptionFormat, args);
	}
	
	public CintOperationResult() {
		error = CintErrorCode.NoError;
	}

	public String toString(){
		return hasError() ? error.toString() : value.toString();
	}
	
	@Override
	public void setError(CintErrorCode error, UUID errorId, String description) {
		value = null;
		super.setError(error, errorId, description);
	}
	
	@Override
	public void setError(CintBaseOperationResult result, boolean isOverwrite) {
		value = null;
		super.setError(result, isOverwrite);
	}

	public void setError(CintErrorCode error) {
		setError(error, UUID.randomUUID(), null);
	}
}