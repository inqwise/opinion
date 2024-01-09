package com.inqwise.opinion.actions.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.actions.ActionFlow;
import com.inqwise.opinion.dao.CollectorsDataAccess;
import com.inqwise.opinion.dao.IUpdateCollectorParams;

public abstract class ModifyCollectorFlow<TRequest extends IModifyCollectorRequest> extends  ActionFlow<TRequest, BaseOperationResult> implements IUpdateCollectorParams {
	
	protected static ApplicationLog logger = ApplicationLog.getLogger(ModifyCollectorFlow.class);
			
	private BaseOperationResult updateCollectorResult;
	private TRequest request;
	private IModifyCollectorRequest.ILimitExtension limitExtension;
	private IModifySurveysCollectorRequest.IMessagesExtension messagesExtension;
	
	protected TRequest getRequest(){
		return request;
	}
	
	@Override
	public String getCloseMessage() {
		return null == messagesExtension ? null : messagesExtension.getCloseMessage();
	}
	
	@Override
	public boolean skipCloseMessage() {
		return null == messagesExtension;
	}

	@Override
	public Long getAccountId() {
		return getRequest().getAccountId();
	}
	
	@Override
	public Long getCollectorId() {
		return getRequest().getCollectorId();
	}

	@Override
	public Date getEndDate() {
		return null == limitExtension ? null : limitExtension.getEndDate();
	}
	
	@Override
	public boolean skipEndDate() {
		return null == limitExtension;
	}
	
	@Override
	public boolean skipResponseQuota() {
		return null == limitExtension;
	}
	
	@Override
	public Long getTranslationId() {
		return getRequest().getTranslationId();
	}
	
	@Override
	public long getUserId() {
		return getRequest().getUserId();
	}
	
	@Override
	public Boolean isEnableFinishedSessionEmailNotification() {
		return getRequest().isEnableFinishedSessionEmailNotification();
	}
	
	@Override
	public Boolean isAllowMultiplyResponses() {
		return getRequest().getIsAllowMultiplyResponses();
	}
	
	@Override
	public Integer getResultsTypeId() {
		return null;
	}

	@Override
	public Long getResponseQuota() {
		return null == limitExtension ? null : limitExtension.getResponseQuota();
	}
	
	@Override
	public void updateCollectorCompleted(ResultSet reader) throws SQLException {
		if(reader.next()) {
        	
        	int errorCode = (null == reader.getObject(DAOBase.ERROR_CODE)) ? 0 : reader.getInt(DAOBase.ERROR_CODE);
        	
        	switch(errorCode){
        	case 0:
        		updateCollectorResult = BaseOperationResult.Ok;
        		break;
        	case 4: // no have permissions
        		updateCollectorResult = new BaseOperationResult(ErrorCode.NotPermitted);
        		break;
        	default: // Unsupported Error
    			throw new UnsupportedOperationException("ModifyCollectorFlow() : errorCode not supported. Value: " + errorCode);
        	}
        	
        } else {
        	updateCollectorResult = new BaseOperationResult(ErrorCode.NoResults);
        }
	}
	
	@Override
	public BaseOperationResult process(TRequest request) {
		if (request instanceof IModifyCollectorRequest.ILimitExtension) {
			limitExtension = (IModifyCollectorRequest.ILimitExtension) request;
		}

		if (request instanceof IModifyCollectorRequest.IMessagesExtension) {
			messagesExtension = (IModifyCollectorRequest.IMessagesExtension) request;
		}
		
		BaseOperationResult result = validateRequest(request);
		
		if(null == result){
			try {
				this.request = request;
				CollectorsDataAccess.updateCollector(this);
			} catch (DAOException e) {
				UUID errorId = logger.error(e, "ModifyCollectorFlow : unexpected error occured");
				result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
			}
		}
		
		if(null == result){
			result = updateCollectorResult;
		}
		
		return result;
	}

	protected BaseOperationResult validateRequest(TRequest request) {
		BaseOperationResult result = null;
		if(null == result) result = check(null != request.getAccountId(), ErrorCode.ArgumentNull, "`accountId` is manatory");
		if(null == result) result = check(null != request.getCollectorId(), ErrorCode.ArgumentNull, "`collectorId` is mandatory");
		if(null == result) result = check(null != request.getName(), ErrorCode.ArgumentNull, "`name` is mandatory");
		if(null == result) result = check(null != request.isEnableFinishedSessionEmailNotification(), ErrorCode.ArgumentNull, "`enableFinishedSessionEmailNotification` is mandatory");
		
		return result;
	}	
}
