package com.inqwise.opinion.cms.common.errorHandle;

import java.util.UUID;

public class CmsOperationResult<T> extends BaseCmsOperationResult{	
	
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
	
	public CmsOperationResult(T value){
		this(value, -1L);
	}
	
	public CmsOperationResult(T value, Long transactionId) {
		super(transactionId);
		this.value = value;
	}

	public CmsOperationResult(CmsErrorCode error){
		super(error, UUID.randomUUID());
	}
	
	public CmsOperationResult(CmsErrorCode error, UUID errorId){
		super(error, errorId);
	}
	
	public CmsOperationResult(CmsErrorCode error, UUID errorId, String description){
		super(error, errorId, description);
	}
	
	public CmsOperationResult(CmsErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		super(error, errorId, descriptionFormat, args);
	}
	
	public CmsOperationResult() {
		error = CmsErrorCode.NoError;
	}

	public String toString(){
		return hasError() ? error.toString() : value.toString();
	}
	
	@Override
	public void setError(CmsErrorCode error, UUID errorId, String description) {
		value = null;
		super.setError(error, errorId, description);
	}
	
	@Override
	public void setError(BaseCmsOperationResult result, boolean isOverwrite) {
		value = null;
		super.setError(result, isOverwrite);
	}

	public void setError(CmsErrorCode error) {
		setError(error, UUID.randomUUID(), null);
	}
}