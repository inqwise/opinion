package com.inqwise.opinion.payments.common;

import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;

public interface IPayAction {
	
	public PayOperationResult<IPayActionResponse> process();
	public void setRequest(IPayActionRequest request);
	public PayActionTypes getActionType();
	public IPayAction withRequest(IPayActionRequest request);
}
