package com.cint.common;

public interface IOrderSubmit {

	public abstract ITargetGroupSubmit getTargetGroup();

	public abstract void setContactCompany(String value);

	public abstract String getContactCompany();

	public abstract void setContactEmail(String value);

	public abstract String getContactEmail();

	public abstract void setContactName(String value);

	public abstract String getContactName();

	public abstract void setNumberOfCompletes(Integer value);

	public abstract Integer getNumberOfCompletes();

	public abstract void setNumberOfQuestions(int value);

	public abstract int getNumberOfQuestions();

	public abstract void setSurveyUrl(String value);

	public abstract String getSurveyUrl();

	public abstract void setSurveyTitle(String value);

	public abstract String getSurveyTitle();
	
}
