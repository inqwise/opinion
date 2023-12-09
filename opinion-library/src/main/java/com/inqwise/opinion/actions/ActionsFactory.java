package com.inqwise.opinion.opinion.actions;

import java.util.HashMap;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.google.common.collect.Maps;

public abstract class ActionsFactory {
	protected ActionsFactory() {
	}
	
	protected enum ActionType {
		Create, Modify
	}
	
	private volatile HashMap<ActionType, HashMap<Object, InstanceGenerator>> availableFlowsByType = Maps.newHashMap();
	
	protected interface InstanceGenerator{
		public <T, R extends IOperationResult, W extends ActionFlow<T, R>> W createInstance(Class<W> clazz);
	}
	
	protected HashMap<Object, InstanceGenerator> getAvailableFlows(ActionType actionType, ActionsFactory factory) {
		HashMap<Object, InstanceGenerator> availableFlows = null;
		if(null == (availableFlows = availableFlowsByType.get(actionType))){
			synchronized (actionType) {
				if(null == (availableFlows = availableFlowsByType.get(actionType))){
					availableFlows = Maps.newHashMap();
					factory.fillAvailableFlows(availableFlows, actionType);
					availableFlowsByType.put(actionType, availableFlows);
				}
			}
		}
		
		return availableFlows;
	}

	protected abstract void fillAvailableFlows(HashMap<Object, InstanceGenerator> availableFlows, ActionType actionType);
}
