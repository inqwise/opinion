package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IGateway;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.GatewaysDataAccess;
import com.inqwise.opinion.library.entities.GatewayEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GatewaysManager {
	static ApplicationLog logger = ApplicationLog.getLogger(GatewaysManager.class);

	private static Object _gatewaysCacheLocker = new Object(); 
	private static LoadingCache<Long, OperationResult<IGateway>> _gatewaysCache;
	private static LoadingCache<Long, OperationResult<IGateway>> getGatewaysCache(){
		if(null == _gatewaysCache){
			synchronized (_gatewaysCacheLocker) {
				if(null == _gatewaysCache){
					_gatewaysCache = CacheBuilder.newBuilder().
							maximumSize(100).
							expireAfterWrite(10, TimeUnit.MINUTES).
							build( new CacheLoader<Long, OperationResult<IGateway>>() {
								public OperationResult<IGateway> load(Long gatewayId) throws Exception {
									return getInternal(gatewayId);
								}
							});
				}
			}
		}
		
		return _gatewaysCache;
	}
	
	public static OperationResult<IGateway> get(long id, boolean fromCache){
		OperationResult<IGateway> result = null;
		
		try {
			if(fromCache){
				result = getGatewaysCache().get(id);
			} else {
				result = getInternal(id);
			}
		} catch(Exception ex){
			UUID errorTicket = logger.error(ex, "get: Unexpected error occured");
			result = new OperationResult<IGateway>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	private static OperationResult<IGateway> getInternal(long gatewayId){
		final OperationResult<IGateway> result = new OperationResult<IGateway>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							GatewayEntity gateway = new GatewayEntity(reader);
							result.setValue(gateway);							
						}
					}
				}
			};
			
			GatewaysDataAccess.getReader(gatewayId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "get() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
