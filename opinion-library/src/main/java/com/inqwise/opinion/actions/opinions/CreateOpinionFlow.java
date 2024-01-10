package com.inqwise.opinion.actions.opinions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.inqwise.opinion.actions.ActionFlow;
import com.inqwise.opinion.actions.opinions.ICreateOpinionRequest.IRequestExtension;
import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.events.OpinionCreatedEventArgs;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.dao.IInsertOpinionParams;
import com.inqwise.opinion.dao.OpinionsDataAccess;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

abstract class CreateOpinionFlow<TRequest extends ICreateOpinionRequest> extends ActionFlow<TRequest, OperationResult<Long>> implements IInsertOpinionParams {

	private static final String DEFAULT_CULTURE_VALUE = "en_US";
	
	private OperationResult<Long> insertOpinionResult;
	private Long opinionId;
	private TRequest request;
	private IRequestExtension requestExtension;
	private IAccountView account;
	
	protected IRequestExtension getRequestExtension() {
		return requestExtension;
	}
		
	protected TRequest getRequest() {
		return request;
	}

	protected CreateOpinionFlow() {	
	}

	protected static ApplicationLog logger = ApplicationLog.getLogger(CreateOpinionFlow.class);
	
	@Override
	public OperationResult<Long> process(TRequest request){
		if(request instanceof IRequestExtension){
			requestExtension = (IRequestExtension)request;
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
				BaseOperationResult collectParamsResult = collect(request);
				
				if(null != collectParamsResult && collectParamsResult.hasError()){
					result = collectParamsResult.toErrorResult();
				} 
			}
			
			// insert opinion. callback: insertOpinionCompleted
			if(null == result){
				OpinionsDataAccess.insertOpinion(this);
				
				// check for no results
				if(null == insertOpinionResult){
					UUID errorId = logger.error("CreateOpinionFlow : no results from DB");
					result.setError(ErrorCode.GeneralError, errorId);
				} else {
					result = insertOpinionResult;
				}
			}
			
			// after process section
			if(null != result && !result.hasError()){
				try {
					afterProcess(request, opinionId);
				} catch (Throwable t){
					logger.error(t, "CreateOpinionFlow : unexpected error occured in afterProcess function. Continue");
				}
			}
			
		} catch (DAOException ex){
			UUID errorId = logger.error(ex, "CreateOpinionFlow : unexpected error occured");
			result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	@Override
	public void insertOpinionCompleted(ResultSet reader) throws SQLException {
		if(reader.next()){
			opinionId = Long.valueOf(reader.getLong("opinion_id"));
			insertOpinionResult = new OperationResult<Long>(opinionId);
		}
	}
	
	protected BaseOperationResult collect(TRequest request) throws DAOException{
		
		BaseOperationResult result = null;
		
		if(null == result){
			OperationResult<IAccountView> accountResult = AccountsManager.getAccount(request.getAccountId());
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		return result;
	}
	
	protected IAccountView getAccount() {
		return account;
	}

	protected abstract OpinionType getOpinionType();
	
	protected BaseOperationResult validateRequest(TRequest request) throws DAOException{
		BaseOperationResult result = null;
		
		return result;
	}
	
	protected void afterProcess(TRequest request, Long opinionId) throws Exception{
		
		// create default collector
		BaseOperationResult collectorResult = createDefaultCollector(request.getActionGuid(), request.getUserId(), opinionId);
		if(collectorResult.hasError()){
			logger.error("Unable to create default collector. Details: '%s'", collectorResult.toString());
		}
		
		// send OpinionCreated event
		int productId = ProductsManager.getCurrentProduct().getId();
		EventsServiceClient.getInstance().fire(new OpinionCreatedEventArgs(productId)
												.setOpinionId(opinionId)
												.setOpinionTypeId(getOpinionType().getValue()));
	}
	
	protected abstract BaseOperationResult createDefaultCollector(final String actionGuid, final long userId, final long opinionId);

	@Override
	public Long getAccountId() {
		return getRequest().getAccountId();
	}
	
	@Override
	public String getActionGuid() {
		return getRequest().getActionGuid();
	}
	
	@Override
	public String getCulture() {
		return (null == getRequestExtension() || null == getRequestExtension().getCulture() ? DEFAULT_CULTURE_VALUE : getRequestExtension().getCulture());
	}
	
	@Override
	public String getDescription() {
		return getRequest().getDescription();
	}
	
	@Override
	public Boolean getIsHighlightRequestedQuestions() {
		return (null == getRequestExtension() ? Boolean.TRUE : getRequestExtension().getIsHighlightRequestedQuestions());
	}
	
	@Override
	public String getName() {
		return getRequest().getName();
	}
	
	@Override
	public int getOpinionTypeId() {
		return getOpinionType().getValue();
	}
	
	@Override
	public String getTitle() {
		return getRequest().getTitle();
	}
	
	@Override
	public Long getTranslationId() {
		return (null == getRequestExtension() ? IOpinion.DEFAULT_TRANSLATION_ID : getRequestExtension().getTranslationId());
	}
	
	@Override
	public long getUserId() {
		return getRequest().getUserId();
	}
	
	@Override
	public String getTranslationName() {
		return (null == getRequestExtension() ? ApplicationConfiguration.Opinion.GetDefaultTranslationName() : getRequestExtension().getTranslationName());
	}
	
	@Override
	public boolean isRtl() {
		return (null == getRequestExtension() ? false : getRequestExtension().isRtl());
	}
	
	@Override
	public Boolean isUseQuestionNumbering() {
		return null;
	}

	@Override
	public Boolean isUsePageNumbering() {
		return null;
	}

	@Override
	public String getLogoUrl() {
		return null;
	}
	
	@Override
	public String getFinishMessage() {
		return null;
	}

	@Override
	public String getStartMessage() {
		return null;
	}

	@Override
	public String getStartButtonTitle() {
		return null;
	}

	@Override
	public Boolean isHidePoweredBy() {
		return (null == getRequestExtension() ? Boolean.FALSE : getRequestExtension().isHidePoweredBy());
	}
}
