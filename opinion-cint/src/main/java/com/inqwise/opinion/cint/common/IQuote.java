package com.inqwise.opinion.cint.common;

import java.util.List;

public interface IQuote {

	public abstract String getCurrency();

	public abstract String getFormattedAmount();

	public abstract double getAmount();

	public abstract Integer getAvailableAnswers();

	public abstract String getFieldPeriod();

	public abstract String getProbability();

	public abstract List<IVariable> getVariables();

	public abstract List<IOccupationStatus> getOccupationStatuses();

	public abstract List<IEducationLevel> getEducationLevels();

	public abstract IGender getGender();

	public abstract Integer getMaxAge();

	public abstract Integer getMinAge();

	public abstract ICountry getCountry();

	public abstract Integer getNumberOfCompletes();

	public abstract Integer getNumberOfQuestions();

}
