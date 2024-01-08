package com.inqwise.opinion.common.servicePackage;

public interface IOpinionServicePackageSettingsUpdateRequest {

	int getServicePackageId();
	Integer getMaxOpinions();
	Integer getMaxCollectors();
	Integer getMaxControlsPerOpinion();
	Long getMaxResponsesPerOpinion();
	Long getInitCountSessions();
	Long getSuppliedCountSessions();
	Integer getSupplyIntervalInDays();
}
