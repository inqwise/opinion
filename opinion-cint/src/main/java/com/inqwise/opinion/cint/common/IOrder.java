package com.inqwise.opinion.cint.common;

import java.util.List;

public interface IOrder {

	public abstract String getReferenceNumber();

	public abstract Integer getActualNumberOfCompletes();

	public abstract Double getPricePerComplete();

	public abstract Double getActualPrice();

	public abstract Integer getTotalQuotaFulls();

	public abstract Integer getTotalScreenOuts();

	public abstract Integer getTotalNumberOfCompletes();

	public abstract Integer getTotalNumberOfInvitations();

	public abstract String getCurrency();

	public abstract double getQuote();

	public abstract List<IVariable> getVariables();

	public abstract List<IOccupationStatus> getOccupationStatuses();

	public abstract List<IEducationLevel> getEducationLevels();

	public abstract IGender getGender();

	public abstract Integer getMaxAge();

	public abstract Integer getMinAge();

	public abstract ICountry getCountry();

	public abstract String getContactCompany();

	public abstract String getContactEmail();

	public abstract String getContactName();

	public abstract int getNumberOfCompletes();

	public abstract Integer getNumberOfQuestions();

	public abstract String getSurveyUrl();

	public abstract String getSurveyTitle();

	public abstract String getOrderNumber();

	public abstract OrderState getState();

	public abstract List<ILink> getLinks();
	
}
