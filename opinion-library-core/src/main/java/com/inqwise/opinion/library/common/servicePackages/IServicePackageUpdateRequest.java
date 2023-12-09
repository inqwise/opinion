package com.inqwise.opinion.library.common.servicePackages;

public interface IServicePackageUpdateRequest {
	
	int getId();
	String getName();
	Double getAmount();
	String getDescription();
	Integer getDefaultUsagePeriod();
}
