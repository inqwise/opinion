package com.inqwise.opinion.opinion.actions.collectors;

import java.util.HashMap;
import java.util.Set;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.actions.ActionFlow;
import com.inqwise.opinion.opinion.actions.ActionsFactory;

public final class CollectorsActionsFactory extends ActionsFactory {
	
	private static Class<?> identifyInterface(Object obj, Set<Object> classes){
		for (Object object : classes) {
			Class<?> clazz = (Class<?>)object;
			if(clazz.isInstance(obj)){
				return clazz;
			}
		}
		
		return null;
	}
	
	public <T extends ICreateCollectorRequest> OperationResult<Long> create(T request){
		HashMap<Object, InstanceGenerator> availableFlows = getAvailableFlows(ActionType.Create, this);
		InstanceGenerator instanceGenerator = availableFlows.get(identifyInterface(request, availableFlows.keySet()));
		CreateCollectorFlow<ICreateCollectorRequest> instance = instanceGenerator.createInstance(CreateCollectorFlow.class);
		return instance.process(request);
	}
	
	public <T extends IModifyCollectorRequest> BaseOperationResult modify(T request){
		HashMap<Object, InstanceGenerator> availableFlows = getAvailableFlows(ActionType.Modify, this);
		return availableFlows.get(identifyInterface(request, availableFlows.keySet())).createInstance(ModifyCollectorFlow.class).process(request);
	}
	
	private static class InstanceHolder {
		private static final CollectorsActionsFactory instance = new CollectorsActionsFactory();
	}
	
	public static CollectorsActionsFactory getInstance(){
		return InstanceHolder.instance;
	}

	@Override
	protected void fillAvailableFlows(
			HashMap<Object, InstanceGenerator> availableFlows,
			ActionType actionType) {

		// add CreatePollCollectorFlow
		availableFlows.put(ICreatePollsCollectorRequest.class,
		new InstanceGenerator() {
			
			@Override
			public <T, R extends IOperationResult, W extends ActionFlow<T, R>> W createInstance(
					Class<W> clazz) {
				return (W)new CreatePollsCollectorFlow();
			}
		});
		
		// add CreateSurveyCollectorFlow
		availableFlows.put(ICreateSurveysCollectorRequest.class,
		new InstanceGenerator() {
			
			@Override
			public <T, R extends IOperationResult, W extends ActionFlow<T, R>> W createInstance(
					Class<W> clazz) {
				return (W) new CreateSurveysCollectorFlow();
			}
		});
		
		// add ModifyPollCollectorFlow
		availableFlows.put(IModifyPollsCollectorRequest.class,
		new InstanceGenerator() {
			
			@Override
			public <T, R extends IOperationResult, W extends ActionFlow<T, R>> W createInstance(
					Class<W> clazz) {
				return (W) new ModifyPollsCollectorFlow();
			}
		});
		
		// add ModifySurveyCollectorFlow
		availableFlows.put(IModifySurveysCollectorRequest.class,
		
		new InstanceGenerator() {
			@Override
			public <T, R extends IOperationResult, W extends ActionFlow<T, R>> W createInstance(
					Class<W> clazz) {
				return (W) new ModifySurveysCollectorFlow();
			}
		});
		
	}
}
