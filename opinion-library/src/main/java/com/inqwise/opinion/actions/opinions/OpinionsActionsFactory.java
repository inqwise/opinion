package com.inqwise.opinion.actions.opinions;

import java.util.HashMap;
import java.util.Set;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.actions.ActionFlow;
import com.inqwise.opinion.actions.ActionsFactory;

public final class OpinionsActionsFactory extends ActionsFactory {

	private static Class<?> identifyInterface(Object obj, Set<Object> classes){
		
		for (Object object : classes) {
			Class<?> clazz = (Class<?>)object;
			if(clazz.isInstance(obj)){
				return clazz;
			}
		}
		
		return null;
	} 
	
	public <TRequest extends ICreateOpinionRequest> OperationResult<Long> create(TRequest request){
		HashMap<Object, InstanceGenerator> availableFlows = getAvailableFlows(ActionType.Create, this);
		Object key = identifyInterface(request, availableFlows.keySet());
		return availableFlows.get(key).createInstance(CreateOpinionFlow.class).process(request);
	}
	
	public <TRequest extends IModifyOpinionRequest> BaseOperationResult modify(TRequest request){
		HashMap<Object, InstanceGenerator> availableFlows = getAvailableFlows(ActionType.Modify, this);
		return availableFlows.get(identifyInterface(request, availableFlows.keySet())).createInstance(ModifyOpinionFlow.class).process(request);
	}
	
	private static class InstanceHolder {
		private static final OpinionsActionsFactory instance = new OpinionsActionsFactory();
	}
	
	public static OpinionsActionsFactory getInstance(){
		return InstanceHolder.instance;
	}

	@Override
	protected void fillAvailableFlows(
			HashMap<Object, InstanceGenerator> availableFlows,
			ActionType actionType) {

		// add CreatePollFlow
		availableFlows.put(ICreatePollRequest.class,
		new InstanceGenerator() {
			
			@Override
			public <TRequest, TResult extends IOperationResult, TAction extends ActionFlow<TRequest, TResult>> TAction createInstance(
					Class<TAction> clazz) {
				return (TAction) new CreatePollFlow();
			}
		});
		
		// add CreateSurveyFlow
		availableFlows.put(ICreateSurveyRequest.class,
		new InstanceGenerator() {
			
			@Override
			public <TRequest, TResult extends IOperationResult, TAction extends ActionFlow<TRequest, TResult>> TAction createInstance(
					Class<TAction> clazz) {
				return (TAction) new CreateSurveyFlow();
			}
		});
		
		// add ModifyPollFlow
		availableFlows.put(IModifyPollRequest.class,
		new InstanceGenerator() {
			
			@Override
			public <TRequest, TResult extends IOperationResult, TAction extends ActionFlow<TRequest, TResult>> TAction createInstance(
					Class<TAction> clazz) {
				return (TAction) new ModifyPollFlow();
			}
		});
		
		// add ModifySurveyFlow
		availableFlows.put(IModifySurveyRequest.class,
		new InstanceGenerator() {
			
			@Override
			public <TRequest, TResult extends IOperationResult, TAction extends ActionFlow<TRequest, TResult>> TAction createInstance(
					Class<TAction> clazz) {
				return (TAction) new ModifySurveyFlow();
			}
		});
	}

}
