package com.inqwise.opinion.cms.common.faq;

public interface INewQuestionEmailData {
	String getNoreplyEmail();
	String getEmail();
	
	String getAutorName();
	String getQuestion();
	String getAutorEmail();
	String getFeedbackCaption();
	Long getUserId();
}
