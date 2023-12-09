package com.inqwise.opinion.opinion.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.servicePackage.IServicePackageSettings;
import com.inqwise.opinion.opinion.dao.ServicePackageSettings;

public class ServicePackageSettingsEntity implements IServicePackageSettings {
	private static ApplicationLog logger = ApplicationLog
			.getLogger(ServicePackageSettingsEntity.class);

	private Integer maxCollectors;
	private Integer maxControlsPerOpinion;
	private Integer maxOpinions;
	private Long maxResponsesPerOpinion;
	private int servicePackageTypeId;
	private Long initCountSessions;
	private Long suppliedCountSessions;
	private Integer supplyIntervalInDays;
	
	public ServicePackageSettingsEntity() {
	}

	public ServicePackageSettingsEntity(ResultSet reader) throws Exception {
		fill(reader);
	}

	protected void fill(ResultSet reader)
			throws SQLException, Exception {
		setMaxCollectors(ResultSetHelper.optInt(reader, "sps_max_collectors"));
		setMaxControlsPerOpinion(ResultSetHelper.optInt(reader, "sps_max_controls_per_opinion"));
		setMaxOpinions(ResultSetHelper.optInt(reader, "sps_max_opinions"));
		setMaxResponsesPerOpinion(ResultSetHelper.optLong(reader, "sps_max_responses_per_opinion"));
		setServicePackageTypeId(reader.getInt("service_package_id"));
		setInitCountSessions(ResultSetHelper.optLong(reader, "sps_init_count_sessions"));
		setSuppliedCountSessions(ResultSetHelper.optLong(reader, "sps_supplied_count_sessions"));
		setSupplyIntervalInDays(ResultSetHelper.optInt(reader, "sps_supply_days_interval"));
	}

	public static OperationResult<IServicePackageSettings> getServicePackageSettings(int servicePackageId){
		OperationResult<IServicePackageSettings> result;
		try{
			IDataFillable<IServicePackageSettings> data = new IDataFillable<IServicePackageSettings>()
			{
				public ServicePackageSettingsEntity fill(ResultSet reader) throws Exception
				{
					return new ServicePackageSettingsEntity(reader);
				}
			};
			
			result = ServicePackageSettings.getServicePackageSettings(servicePackageId, data);
			
		}catch(Exception ex){
			UUID errorId = logger.error(ex, "getServicePackageSettings() : Unexpected error occured.");
			result = new OperationResult<IServicePackageSettings>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	private void setServicePackageTypeId(int servicePackageTypeId){
		this.servicePackageTypeId = servicePackageTypeId;
	}
	
	private void setMaxResponsesPerOpinion(Long maxResponsesPerOpinion){
		this.maxResponsesPerOpinion = maxResponsesPerOpinion;
	}
	private void setMaxOpinions(Integer maxOpinions){
		this.maxOpinions = maxOpinions;
	}
	
	private void setMaxControlsPerOpinion(Integer maxControlsPerOpinion){
		this.maxControlsPerOpinion = maxControlsPerOpinion;
	}
	
	private void setMaxCollectors(Integer maxCollectors) {
		this.maxCollectors = maxCollectors;		
	}

	@Override
	public Integer getMaxCollectors() {
		return maxCollectors;
	}

	@Override
	public Integer getMaxControlsPerOpinion() {
		return maxControlsPerOpinion;
	}

	@Override
	public Integer getMaxOpinions() {
		return maxOpinions;
	}

	@Override
	public Long getMaxResponsesPerOpinion() {
		return maxResponsesPerOpinion;
	}

	@Override
	public int getServicePackageTypeId() {
		return servicePackageTypeId;
	}

	public Long getInitCountSessions() {
		return initCountSessions;
	}

	public void setInitCountSessions(Long initCountSessions) {
		this.initCountSessions = initCountSessions;
	}

	public Long getSuppliedCountSessions() {
		return suppliedCountSessions;
	}

	public void setSuppliedCountSessions(Long suppliedCountSessions) {
		this.suppliedCountSessions = suppliedCountSessions;
	}

	public Integer getSupplyIntervalInDays() {
		return supplyIntervalInDays;
	}

	public void setSupplyIntervalInDays(Integer supplyIntervalInDays) {
		this.supplyIntervalInDays = supplyIntervalInDays;
	}
}
