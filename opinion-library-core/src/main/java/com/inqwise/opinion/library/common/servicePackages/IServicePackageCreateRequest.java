package com.inqwise.opinion.library.common.servicePackages;

public interface IServicePackageCreateRequest {

	String getName();
	Integer getProductId();
	Double getAmount();
	String getDescription();
	Integer getDefaultUsagePeriod();

}
