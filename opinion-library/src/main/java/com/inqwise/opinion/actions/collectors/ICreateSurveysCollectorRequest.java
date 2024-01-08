package com.inqwise.opinion.actions.collectors;

public interface ICreateSurveysCollectorRequest extends ICreateCollectorRequest {

	interface IRequestExtension extends ICreateCollectorRequest.IRequestExtension {
		
	}
	
	interface IPasswordExtension {
		String getPassword();
		Boolean getIsHidePassword();
	}
	
	interface IIdentityExtension{
		String getReferer();
	}
	
	interface IRedirectExtension {
		String getReturnUrl();
		String getScreenOutUrl();
		String getSurveyClosedUrl();
	}
}
