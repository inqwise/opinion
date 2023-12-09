package com.inqwise.opinion.marketplace.common.errorHandle;

import java.util.UUID;

public class MarketOperationResult<T> extends MarketBaseOperationResult{	
	
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
	
	public MarketOperationResult(T value){
		this(value, -1L);
	}
	
	public MarketOperationResult(T value, Long transactionId) {
		super(transactionId);
		this.value = value;
	}

	public MarketOperationResult(MarketErrorCode error){
		super(error, UUID.randomUUID());
	}
	
	public MarketOperationResult(MarketErrorCode error, UUID errorId){
		super(error, errorId);
	}
	
	public MarketOperationResult(MarketErrorCode error, UUID errorId, String description){
		super(error, errorId, description);
	}
	
	public MarketOperationResult(MarketErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		super(error, errorId, descriptionFormat, args);
	}
	
	public MarketOperationResult() {
		error = MarketErrorCode.NoError;
	}

	public String toString(){
		return hasError() ? error.toString() : value.toString();
	}
	
	@Override
	public void setError(MarketErrorCode error, UUID errorId, String description) {
		value = null;
		super.setError(error, errorId, description);
	}
	
	@Override
	public void setError(MarketBaseOperationResult result, boolean isOverwrite) {
		value = null;
		super.setError(result, isOverwrite);
	}

	public void setError(MarketErrorCode error) {
		setError(error, UUID.randomUUID(), null);
	}
}