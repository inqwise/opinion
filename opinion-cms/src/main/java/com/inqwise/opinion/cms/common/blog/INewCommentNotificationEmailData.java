package com.inqwise.opinion.cms.common.blog;

public interface INewCommentNotificationEmailData {

	String getNoreplyEmail();
	String getEmail();
	
	String getAutorName();
	String getContent();
	String getAutorEmail();
	String getAutorUrl();
	long getCommentId();
	String getFeedbackCaption();
}
