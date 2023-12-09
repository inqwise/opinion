package com.inqwise.opinion.opinion.managers;

import java.sql.ResultSet;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.collectors.ICollectorSource;
import com.inqwise.opinion.opinion.dao.CollectorSourcesDataAccess;
import com.inqwise.opinion.opinion.entities.CollectorSourceEntity;

public class CollectorSourcesManager {

	private static ApplicationLog logger = ApplicationLog.getLogger(CollectorSourcesManager.class);
			
	public static OperationResult<ICollectorSource> getCollectorSource(
			int collectorSourceId) {
		final OperationResult<ICollectorSource> result = new OperationResult<ICollectorSource>();
		
		try{
		IResultSetCallback callback = new IResultSetCallback() {
			
			@Override
			public void call(ResultSet reader, int generationId) throws Exception {
				if(reader.next()){
					result.setValue(new CollectorSourceEntity(reader));
				}
			}
		};
		
		CollectorSourcesDataAccess.getCollectorSourceReader(collectorSourceId, callback );
		
		if(!(result.hasError() || result.hasValue())){
			logger.warn("getCollectorSource : no results for collectorSource #%s", collectorSourceId);
			result.setError(ErrorCode.NoResults);
		}
		
		} catch (Throwable ex){
			UUID errorId = logger.error(ex, "getCollectorSource : unexpected error occured");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
}
