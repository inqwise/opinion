package com.inqwise.opinion.managers;

import java.util.UUID;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.ServicePackagesManager;
import com.inqwise.opinion.common.servicePackage.IOpinionServicePackageSettingsUpdateRequest;
import com.inqwise.opinion.common.servicePackage.IServicePackageSettings;
import com.inqwise.opinion.dao.ServicePackageSettings;
import com.inqwise.opinion.entities.ServicePackageSettingsEntity;

public class OpinionServicePackagesManager {
	
	static ApplicationLog logger = ApplicationLog.getLogger(OpinionServicePackagesManager.class);
	
	public static OperationResult<IServicePackageSettings> getServicePackageSettings(int servicePackageId){
		return ServicePackageSettingsEntity.getServicePackageSettings(servicePackageId);
	}
	
	public static BaseOperationResult updateServicePackageSettings(IOpinionServicePackageSettingsUpdateRequest request){
		BaseOperationResult result = new BaseOperationResult();
		try{
			ServicePackageSettings.updateServicePackageSettings(request);
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "updateServicePackageSettings() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	} 
}
