package com.inqwise.opinion.managers;

import java.util.UUID;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.common.audit.IAuditRequest;
import com.inqwise.opinion.dao.Audits;

public class AuditsManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(AuditsManager.class);

	public static OperationResult<String> createOpinionAudit(IAuditRequest request){
		return  createOpinionAudit(request, null);
	}
	
	public static OperationResult<String> createOpinionAudit(IAuditRequest request, UUID actionGuid){
		
		OperationResult<String> result = null;
		if(null == actionGuid){
			actionGuid = UUID.randomUUID();
		}
		
		BaseOperationResult setOpinionAuditResult = null;
		if(null == result){
			try {
				setOpinionAuditResult = Audits.setOpinionAudit(request, actionGuid);
				if(setOpinionAuditResult.hasError()){
					result = setOpinionAuditResult.toErrorResult();
				} else {
					result = new OperationResult<String>(actionGuid.toString());
				}
			} catch (DAOException e) {
				UUID errorId = logger.error(e, "createOpinionAudit() : Unexpected error occured.");
				result = new OperationResult<String>(ErrorCode.GeneralError, errorId);
			}
		}
		
		return result;
	}
}
