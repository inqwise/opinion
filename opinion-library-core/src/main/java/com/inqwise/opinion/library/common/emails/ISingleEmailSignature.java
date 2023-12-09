package com.inqwise.opinion.library.common.emails;

public interface ISingleEmailSignature {

	String getEmail();
	String getFeedbackCaption();
	int getCurrentYear();
	String getSupportEmail();
	String getFeedbackShortCaption();
	String getApplicationUrl();
}