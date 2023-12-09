package com.cint.common;

import com.cint.common.errorHandle.CintBaseOperationResult;
import com.cint.core.RequestType;

public interface IRequestVoid {
	String getUrl();
	CintBaseOperationResult validate();
	RequestType getRequestType();
	String toXml();
	boolean isRequiredSignature();
}
