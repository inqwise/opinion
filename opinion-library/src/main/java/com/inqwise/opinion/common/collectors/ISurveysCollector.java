package com.inqwise.opinion.common.collectors;


public interface ISurveysCollector extends ICollector, ICollector.IEndDateExtension, ICollector.IMultiplyResponsesExtension, ICollector.IPasswordExtension, ICollector.IMessagesExtension, ICollector.IMultiPageExtension {
	public abstract Boolean isEnableRssUpdates();
	public abstract Integer getCollectorSourceId();
	public abstract String getReferer();
	public abstract String getScreenOutUrl();
	public abstract String getReturnUrl();
	public abstract String getOpinionClosedUrl();
}
