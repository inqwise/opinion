package com.inqwise.opinion.marketplace.managers;

import java.sql.ResultSet;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.marketplace.common.IService;
import com.inqwise.opinion.marketplace.common.errorHandle.MarketErrorCode;
import com.inqwise.opinion.marketplace.common.errorHandle.MarketOperationResult;
import com.inqwise.opinion.marketplace.dao.ServicesDataAccess;
import com.inqwise.opinion.marketplace.entities.ServiceEntity;

public class ServicesManager {
	static ApplicationLog logger = ApplicationLog.getLogger(ServicesManager.class);
	
	public static MarketOperationResult<IService> getService(long serviceId){
		final MarketOperationResult<IService> result = new MarketOperationResult<IService>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IService service = new ServiceEntity(reader);
							result.setValue(service);							
						}
					}
				}
			};
			
			ServicesDataAccess.getService(serviceId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(MarketErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getService() : Unexpected error occured.");
			result.setError(MarketErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
