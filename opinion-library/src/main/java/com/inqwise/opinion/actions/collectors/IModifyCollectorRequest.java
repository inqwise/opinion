package com.inqwise.opinion.opinion.actions.collectors;

import java.util.Date;

interface IModifyCollectorRequest {

	Long getCollectorId();
	String getName();
	long getUserId();
	Long getTranslationId();
	Long getAccountId();
	Boolean getIsAllowMultiplyResponses();
	Boolean isEnableFinishedSessionEmailNotification();

	interface ILimitExtension {
		Date getEndDate();
		Long getResponseQuota();
	}
	
	interface IMessagesExtension {
		String getCloseMessage();
	}
	
	interface ISecurityExtension {
		String getPassword();
		Boolean isHidePassword();
	}
}
