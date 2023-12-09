package com.inqwise.opinion.library.common.parameters;


public interface IVariableSet {
	public String getName();
	<T extends IVariable> T getPrevious(int priorityId);
	<T extends IVariable> T getActual();
}
