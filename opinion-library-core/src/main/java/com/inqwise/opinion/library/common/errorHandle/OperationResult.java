package com.inqwise.opinion.library.common.errorHandle;

import java.util.UUID;

public class OperationResult<T> extends BaseOperationResult {	
	
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
	
	public OperationResult(T value){
		this(value, -1L);
	}
	
	public OperationResult(T value, Long transactionId) {
		super(transactionId);
		this.value = value;
	}

	public OperationResult(ErrorCode error){
		super(error, UUID.randomUUID());
	}
	
	public OperationResult(ErrorCode error, UUID errorId){
		super(error, errorId);
	}
	
	public OperationResult(ErrorCode error, UUID errorId, String description){
		super(error, errorId, description);
	}
	
	public OperationResult(ErrorCode error, String description){
		super(error, description);
	}
	
	public OperationResult(ErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		super(error, errorId, descriptionFormat, args);
	}
	
	public OperationResult() {
		error = ErrorCode.NoError;
	}

	public String toString(){
		return hasError() ? error.toString() : value.toString();
	}
	
	@Override
	public void setError(ErrorCode error, UUID errorId, String description) {
		value = null;
		super.setError(error, errorId, description);
	}
	
	@Override
	public void setError(BaseOperationResult result, boolean isOverwrite) {
		value = null;
		super.setError(result, isOverwrite);
	}

	public void setError(ErrorCode error) {
		setError(error, UUID.randomUUID(), null);
	}
}