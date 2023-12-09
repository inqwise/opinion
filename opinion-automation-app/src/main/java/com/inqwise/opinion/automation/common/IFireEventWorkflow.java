package com.inqwise.opinion.automation.common;

import java.rmi.RemoteException;

import com.inqwise.opinion.infrastructure.common.IOperationResult;

public interface IFireEventWorkflow {

	IOperationResult process(FireEventArgs fireEventArgs) throws RemoteException;

}
