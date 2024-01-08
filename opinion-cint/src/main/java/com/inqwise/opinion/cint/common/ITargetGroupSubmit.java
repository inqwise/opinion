package com.inqwise.opinion.cint.common;

import java.util.List;

public interface ITargetGroupSubmit {

	public abstract List<String> getRegions();

	public abstract List<String> getVariables();

	public abstract List<String> getOccupationStatuses();

	public abstract List<String> getEducationLevels();

	public abstract void setGenderId(String value);

	public abstract String getGenderId();

	public abstract void setMaxAge(Integer value);

	public abstract Integer getMaxAge();

	public abstract void setMinAge(Integer value);

	public abstract Integer getMinAge();

	public abstract void setCountryId(String value);

	public abstract String getCountryId();

	public abstract void setEducationLevels(List<String> educationLevels);

	public abstract void setOccupationStatuses(List<String> occupationStatuses);

}
