package com.inqwise.opinion.cms.managers;

import java.sql.ResultSet;
import java.util.UUID;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.IPortal;
import com.inqwise.opinion.cms.dao.PortalsDataAccess;
import com.inqwise.opinion.cms.entities.PortalEntity;
import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public final class PortalsManager {
	
	private static ApplicationLog logger = ApplicationLog.getLogger(PortalsManager.class);
	
	private static IPortal currentPortal;
	private static Object lockObj = new Object();
	public static IPortal getCurrentPortal(){
		if(null == currentPortal){
			synchronized (lockObj) {
				if(null == currentPortal){
					fillCurrentPortal(); 
				}
			}
		}
		
		return currentPortal;
	}
	
	private static BaseOperationResult fillCurrentPortal() {
		String currentPortalName = CmsConfiguration.getCurrentPortalName();
		final EntityBox<PortalEntity> portalBox = new EntityBox<PortalEntity>();
		IDataFillable<IPortal> callback = new IDataFillable<IPortal>() {
			
			@Override
			public IPortal fill(ResultSet reader) throws Exception {
				
				if(portalBox.hasValue()){
					throw new Exception("fillCurrentPortal : More that one records received.");
				}
				
				if(reader.next()){
					portalBox.setValue(new PortalEntity(reader));
				}
				
				return portalBox.getValue();
			}
		};
		
		BaseOperationResult result;
		try{
			PortalsDataAccess.getPortalsReader(callback, currentPortalName);
			if(portalBox.hasValue()){
				currentPortal = portalBox.getValue();
				result = BaseOperationResult.Ok;
			} else {
				UUID errorId = logger.error("No Results for requested portalName '%s'.", currentPortalName);
				result = new BaseOperationResult(ErrorCode.NoResults, errorId);
			}
		} catch (Exception ex){
			UUID errorId = logger.error(ex, "Failed to fillCurrentPortal. PortalName: '%s'", currentPortalName);
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId); 
		}
		
		return result;
	}
}
