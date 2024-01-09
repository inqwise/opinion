package com.inqwise.opinion.cint.common;

import com.inqwise.opinion.cint.common.errorHandle.CintBaseOperationResult;
import com.inqwise.opinion.cint.core.RequestType;

public interface IRequestVoid {
	String getUrl();
	CintBaseOperationResult validate();
	RequestType getRequestType();
	String toXml();
	boolean isRequiredSignature();
}
