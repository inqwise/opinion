package com.inqwise.opinion.opinion.managers;

import java.util.UUID;

import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.opinion.common.GeneralSettingKeys;
import com.inqwise.opinion.opinion.dao.GeneralSettings;

public class SettingsManager {
	private final static ApplicationLog logger = ApplicationLog.getLogger(SettingsManager.class);
	
	public static OperationResult<String> getGeneralValue(GeneralSettingKeys key){
		return getGeneralValue(key.getValue());
	}
	
	public static OperationResult<String> getGeneralValue(String key){
		try {
			return GeneralSettings.getValue(key);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "getGeneralValue('%s') : unexpected error occured.", key);
			return new OperationResult<String>(ErrorCode.GeneralError, errorId);
		} 
	}
}
