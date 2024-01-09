package com.inqwise.opinion.cint.common;

public interface IOrderEventNew extends IOrderEvent {

	public abstract IGlobalVariable getGlobalVariable();

	public abstract String getAgeRange();

	public abstract String getOccupationStatus();

	public abstract String getEducation();

	public abstract String getGender();

	public abstract String getRegion();

	public abstract String getCountry();

	public abstract String getContactName();

	public abstract String getContactCompany();

	public abstract int getNumberOfCompletes();

	public abstract Integer getNumberOfQuestions();

	public abstract String getContactEmail();

	public abstract double getQuote();

	public abstract String getSurveyUrl();

	public abstract String getSurveyTitle();

	public abstract String getCurrency();

}
