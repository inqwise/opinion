package com.inqwise.opinion.payments.actions;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.payments.common.IPayAction;
import com.inqwise.opinion.payments.common.IPayActionRequest;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.errorHandle.PayBaseOperationResult;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.processors.Processor;

public abstract class PayAction<TRequest extends IPayActionRequest, TProcessor extends Processor> implements IPayAction {

	static final ApplicationLog Logger = ApplicationLog.getLogger(PayAction.class);
	private TRequest request;
	private TProcessor processor;
	
	public PayAction(TProcessor processor) {
		setProcessor(processor);
	}
	
	@Override
	public PayOperationResult<IPayActionResponse> process() {
		PayOperationResult<IPayActionResponse> result = null;
		PayBaseOperationResult collectResult = collect();
		if(null != collectResult){
			result = collectResult.toErrorResult();
		}
		
		if(null == result){
			result = processAction();
			if(!result.hasError()){
				makePostActions(result.getValue());
			}
		}
		return result;
	}
	
	protected void makePostActions(IPayActionResponse value) {
		
	}

	protected abstract PayOperationResult<IPayActionResponse> processAction();
	
	@SuppressWarnings("unchecked")
	@Override
	public void setRequest(IPayActionRequest request) {
		this.request = (TRequest)request;
	}

	public TRequest getRequest() {
		return request;
	}

	public TProcessor getProcessor() {
		return processor;
	}

	private void setProcessor(TProcessor processor) {
		this.processor = processor;
	}

	protected PayOperationResult<IPayActionResponse> validateAction(){
		return null;
	}
	
	protected PayBaseOperationResult collect(){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IPayAction withRequest(IPayActionRequest request) {
		this.request = (TRequest)request;
		return this;
	}
	
	public int getTransactionTypeId() {
		return getActionType().getValue();
	}
	
}
