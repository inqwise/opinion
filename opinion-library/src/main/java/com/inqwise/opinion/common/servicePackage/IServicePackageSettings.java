package com.inqwise.opinion.opinion.common.servicePackage;

public interface IServicePackageSettings {
	int getServicePackageTypeId();
	Integer getMaxOpinions();
	Integer getMaxCollectors();
	Integer getMaxControlsPerOpinion();
	Long getMaxResponsesPerOpinion();
	Long getInitCountSessions();
	Long getSuppliedCountSessions();
	Integer getSupplyIntervalInDays();
}
