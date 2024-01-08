package com.inqwise.opinion.cint.common;

import java.util.List;

public interface ICountry {
	String getId();
	String getName();
	List<IRegion> getRegions();
}
