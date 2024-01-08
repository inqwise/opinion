package com.inqwise.opinion.cint.common;

import java.util.List;

public interface IRespondent {

	public abstract List<IVariable> getVariables();

	public abstract String getStatus();

	public abstract IDemographic getRegion();

	public abstract IDemographic getCountry();

	public abstract IDemographic getEducationLevel();

	public abstract IDemographic getOccupationStatus();

	public abstract IDemographic getGender();

	public abstract Integer getAge();

	public abstract String getId();

}
