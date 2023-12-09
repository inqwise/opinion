package com.inqwise.opinion.automation.common;

import java.io.Serializable;

import com.inqwise.opinion.infrastructure.common.IOperationResult;

public interface IEventAction extends Serializable {

	IOperationResult run();
}
