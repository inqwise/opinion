package com.inqwise.opinion.actions.collectors;

public interface IModifySurveysCollectorRequest extends IModifyCollectorRequest {
	
	Boolean isEnablePrevious();
	Boolean isEnableRssUpdates();
	
	interface IRedirectExtension {
		String getReturnUrl();
		String getScreenOutUrl();
		String getSurveyClosedUrl();
	}
	
	interface IIdentityExtension{
		String getReferer();
	}
}
