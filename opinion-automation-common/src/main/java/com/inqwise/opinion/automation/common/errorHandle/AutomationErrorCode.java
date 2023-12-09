package com.inqwise.opinion.automation.common.errorHandle;

import com.inqwise.opinion.infrastructure.common.IErrorCode;

public enum AutomationErrorCode implements IErrorCode {
	NoError,
	GeneralError,
	NoResults,
	ArgumentIsNull, 
	CommunicationFailed,	
}
