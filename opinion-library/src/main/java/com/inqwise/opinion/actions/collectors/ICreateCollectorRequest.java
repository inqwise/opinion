package com.inqwise.opinion.actions.collectors;

import java.util.Date;

interface ICreateCollectorRequest {
	/*
	 * Default collector name if null
	 */
	String getName();
	Long getOpinionId();
	String getActionGuid();
	long getUserId();
	/*
	 * Default CollectorSourceId if null
	 */
	Integer getCollectorSourceId();
	
	public interface IRequestExtension {
		Date getEndDate();
		Boolean getIsAllowMultiplyResponses();
		Long getResponseQuota();
		Long getTranslationId();
		Boolean isEnablePrevious();
		Boolean isEnableRssUpdates();
		Boolean isEnableFinishedSessionEmailNotification();
		Date getExpirationDate();
		Integer getCollectorStatusId();
	}
}


