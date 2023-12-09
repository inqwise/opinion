package com.inqwise.opinion.automation.workflows;

import java.rmi.RemoteException;

import com.inqwise.opinion.automation.common.FireEventArgs;
import com.inqwise.opinion.automation.common.IFireEventWorkflow;
import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.infrastructure.common.IOperationResult;

abstract class Workflow<T extends FireEventArgs> implements  IFireEventWorkflow {

	public Workflow() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public IOperationResult process(FireEventArgs fireEventArgs) throws RemoteException{
		return processWorkflow((T)fireEventArgs);
	}
	
	protected abstract IOperationResult processWorkflow(T eventArgs) throws RemoteException;
}
