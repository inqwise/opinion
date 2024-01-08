package com.inqwise.opinion.common.collectors;

import java.util.Date;

public interface IPanelSurveysCollector extends ICollector {
	public abstract Integer getCollectorSourceId();
	public abstract Date getExpirationDate();
	public abstract String getReferer();
	public abstract String getScreenOutUrl();
	public abstract String getReturnUrl();
	public abstract CollectorSourceType getCollectorSourceType();
	public abstract String getOpinionClosedUrl();
	public abstract String getExternalId();
	public abstract Long getResponseQuota();
	
}
