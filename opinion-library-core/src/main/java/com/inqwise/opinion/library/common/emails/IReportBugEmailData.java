package com.inqwise.opinion.library.common.emails;

import com.inqwise.opinion.library.common.users.IUser;

public interface IReportBugEmailData extends IEmailData {

	String getBugsEmail();
	String getTitle();
	String getDescription();
	String getTags();
	String getStepsToReproduce();
	String getExpectedBehavior();
	String getActualBehavior();
	String getFeedbackCaption();
	IUser getUser();
}
