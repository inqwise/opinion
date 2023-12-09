package com.inqwise.opinion.opinion.actions;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;

public abstract class ActionFlow<TRequest, TResult extends IOperationResult> {

	protected ActionFlow() {
	}
	
	protected BaseOperationResult check(boolean validCondition, ErrorCode errorCode, String errorDescription){
		BaseOperationResult result = null;
		if(!validCondition){
			result = new BaseOperationResult(errorCode, errorDescription);
		}
		return result;
	}
	
	public abstract TResult process(TRequest request);
}
