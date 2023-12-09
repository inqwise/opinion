package com.inqwise.opinion.opinion.actions.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.AccessValue;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IAccess;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.parameters.PermissionsKeys;
import com.inqwise.opinion.library.common.parameters.VariablesCategories;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ParametersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.opinion.actions.ActionFlow;
import com.inqwise.opinion.opinion.common.collectors.CollectorSourceType;
import com.inqwise.opinion.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.opinion.common.collectors.ICollectorSource;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.dao.CollectorsDataAccess;
import com.inqwise.opinion.opinion.dao.IInsertCollectorParams;
import com.inqwise.opinion.opinion.managers.CollectorSourcesManager;
import com.inqwise.opinion.opinion.managers.OpinionsManager;

abstract class CreateCollectorFlow<TRequest extends ICreateCollectorRequest> extends ActionFlow<TRequest, OperationResult<Long>> implements IInsertCollectorParams {

	protected Long collectorId;
	protected OperationResult<Long> insertCollectorResult;
	private TRequest request;
	protected ICollectorSource collectorSource;
	protected ICreateCollectorRequest.IRequestExtension requestExtension;
	private Integer collectorStatusId;
	private IOpinion opinion;
	
	
	protected IOpinion getOpinion() {
		return opinion;
	}

	protected ICreateCollectorRequest.IRequestExtension getRequestExtension() {
		return requestExtension;
	}

	private IAccountView account;
	
	protected TRequest getRequest() {
		return request;
	}

	protected static ApplicationLog logger = ApplicationLog.getLogger(CreateCollectorFlow.class);
	
	protected CreateCollectorFlow() {
		
	}
	
	@Override
	public long getAccountId() {
		return opinion.getAccountId();
	}
	
	@Override
	public String getActionGuid() {
		return getRequest().getActionGuid();
	}
	
	@Override
	public Integer getCollectorSourceId() {
		return getRequest().getCollectorSourceId();
	}
	
	@Override
	public Integer getCollectorStatusId() {
		return (null == collectorStatusId ? CollectorStatus.Open.getValue() : collectorStatusId);
	}
	
	@Override
	public UUID getCollectorUuid() {
		return UUID.randomUUID();
	}
	
	@Override
	public Date getEndDate() {
		return (null == requestExtension ? null : requestExtension.getEndDate());
	}
	
	@Override
	public Date getExpirationDate() {
		return (null == requestExtension ? null : requestExtension.getExpirationDate());
	}
	
	@Override
	public Boolean getIsAllowMultiplyResponses() {
		return (null == requestExtension ? null : requestExtension.getIsAllowMultiplyResponses());
	}
	
	@Override
	public String getName() {
		return (null == getRequest().getName() ? ApplicationConfiguration.Opinion.getDefaultCollectorName() : getRequest().getName());
	}
	
	@Override
	public Long getOpinionId() {
		return getRequest().getOpinionId();
	}
	
	
	public abstract OpinionType getOpinionType();
	
	
	@Override
	public int getOpinionTypeId() {
		return getOpinionType().getValue();
	}
	
	@Override
	public Boolean getIsHidePassword() {
		return null; // Default value
	}
	
	@Override
	public String getPassword() {
		return null; // Default value
	}
	
	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.dao.IInsertCollectorParams#getReferer()
	 */
	@Override
	public String getReferer() {
		return null; // Default value
	}
	
	@Override
	public Long getResponseQuota() {
		return (null == requestExtension ? null : requestExtension.getResponseQuota());
	}
	
	@Override
	public String getReturnUrl() {
		return null; //Default value
	}
	
	@Override
	public String getScreenOutUrl() {
		return null; // Default value
	}
	
	@Override
	public Integer getServicePackageId() {
		return account.getServicePackageId();
	}
	
	@Override
	public String getOpinionClosedUrl() {
		return null; // Default value
	}
	
	@Override
	public Long getTranslationId() {
		return (null == requestExtension ? IOpinion.DEFAULT_TRANSLATION_ID : requestExtension.getTranslationId());
	}
	
	@Override
	public long getUserId() {
		return getRequest().getUserId();
	}
	
	@Override
	public Boolean isEnableFinishedSessionEmailNotification() {
		return (null == requestExtension ? false : requestExtension.isEnableFinishedSessionEmailNotification());
	}
	
	@Override
	public Boolean isEnablePrevious() {
		return (null == requestExtension ? null : requestExtension.isEnablePrevious());
	}
	
	@Override
	public Boolean isEnableRssUpdates() {
		return (null == requestExtension ? null : requestExtension.isEnableRssUpdates());
	}
	
	@Override
	public Integer getResultsTypeId() {
		return null;
	}
	
	@Override
	public OperationResult<Long> process(TRequest request) {
		
		if(request instanceof ICreateCollectorRequest.IRequestExtension){
			requestExtension = (ICreateCollectorRequest.IRequestExtension)request;
		}
		
		OperationResult<Long> result = null;
		
		try {
			// validate request
			BaseOperationResult validationResult = validateRequest(request);
			if(null != validationResult){
				result = validationResult.toErrorResult();
			}
			
			// collect additional data
			if(null == result){
				this.request = request;
				BaseOperationResult collectResult = collect(request);
				if(null != collectResult){
					result = collectResult.toErrorResult();
				}
			}
			
			if(null == result){
				
				// insertCollector, callback: insertCollectorCompleted 
				CollectorsDataAccess.insertCollector(this);
				
				// check for no results
				if(null == insertCollectorResult){
					UUID errorId = logger.error("CreateCollectorFlow : no results from DB");
					result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
				} else {
					result = insertCollectorResult;
				}
			}
		} catch (DAOException ex){
			UUID errorId = logger.error(ex, "CreateCollectorFlow : unexpected error occured");
			result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	@Override
	public void insertCollectorCompleted(ResultSet reader) throws SQLException {
		if(reader.next()){
        	int errorCode = ResultSetHelper.optInt(reader, DAOBase.ERROR_CODE, 0);
        	
        	switch(errorCode){
        	case 0:
        		collectorId = ResultSetHelper.optLong(reader, "collector_id");
            	insertCollectorResult = new OperationResult<Long>(collectorId);
        		break;
        	case 4: // no have permissions
        		insertCollectorResult = new OperationResult<Long>(ErrorCode.NotPermitted);
        		break;
        	default: // Unsupported Error
    			throw new Error("CreateCollectorFlow() : errorCode not supported. Value: " + errorCode);
        	}
        } 
	}

	protected BaseOperationResult collect(TRequest request) throws DAOException{
		
		BaseOperationResult result = null;
		
		if(null == result){
			if(null == requestExtension || null == requestExtension.getCollectorStatusId()){
				if(null != request.getCollectorSourceId()) {
					OperationResult<ICollectorSource> sourceResult = CollectorSourcesManager.getCollectorSource(getCollectorSourceId());
					if(sourceResult.hasError()){
						result = sourceResult.toErrorResult();
					} else {
						collectorSource = sourceResult.getValue();
						collectorStatusId = collectorSource.getInitialStatus().getValue();
					}
				}
			} else {
				collectorStatusId = requestExtension.getCollectorStatusId();
			}
		}
		
		if(null == result){
			OperationResult<IOpinion> opinionResult = OpinionsManager.getOpinion(getRequest().getOpinionId(), null);
			if(opinionResult.hasError()){
				result = opinionResult;
			} else {
				opinion = opinionResult.getValue();
			}
		}
		
		if(null == result){
			OperationResult<IAccountView> accountResult = AccountsManager.getAccount(opinion.getAccountId());
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
			
		HashMap<String, IVariableSet> permissions = null;
		if(null == result && null != collectorSource && collectorSource.getType() == CollectorSourceType.BuyRespondents){
			String servicePackageKey = ParametersManager.getReferenceKey(EntityType.ServicePackage, getServicePackageId());
			OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(getAccountId(), EntityType.Account, new Integer[] {VariablesCategories.Permissions.getValue()}, new String[] {servicePackageKey});
			if(variablesResult.hasError()){
				result = variablesResult.toErrorResult();
			} else {
				permissions = variablesResult.getValue();
			}
		}
		
		if(null == result && null != collectorSource && collectorSource.getType() == CollectorSourceType.BuyRespondents){
			IAccess purchaseRespondentsAccess = permissions.get(PermissionsKeys.BuyRespondents.getValue()).getActual();
			if(purchaseRespondentsAccess.getValue() == AccessValue.Denied){
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		return result;
	}
	
	protected BaseOperationResult validateRequest(TRequest request) throws DAOException {
		
		BaseOperationResult result = null;
		// Validate
		//
		if(null == result){
			// opinionId
			result = check(null != request.getOpinionId(), ErrorCode.ArgumentNull, "argument 'opinionId' is mandatory.");			
		}
		return result;
	}
}
