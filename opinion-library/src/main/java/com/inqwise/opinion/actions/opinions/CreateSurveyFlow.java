package com.inqwise.opinion.opinion.actions.opinions;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.actions.collectors.CollectorsActionsFactory;
import com.inqwise.opinion.opinion.actions.collectors.ICreateSurveysCollectorRequest;
import com.inqwise.opinion.opinion.actions.opinions.ICreateSurveyRequest.IRequestExtension;
import com.inqwise.opinion.opinion.common.AccountOpinionInfo;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.IPoll;
import com.inqwise.opinion.opinion.common.opinions.ISurvey;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.common.servicePackage.IServicePackageSettings;
import com.inqwise.opinion.opinion.dao.AccountsDataAccess;
import com.inqwise.opinion.opinion.entities.ServicePackageSettingsEntity;
import com.inqwise.opinion.opinion.managers.SheetsManager;

class CreateSurveyFlow extends CreateOpinionFlow<ICreateSurveyRequest> {
	private static final String DEFAULT_FINISH_BUTTON_TITLE = "Finish";
	private static final String DEFAULT_NEXT_BUTTON_TITLE = "Next";
	private static final String DEFAULT_START_BUTTON_TITLE = "Start";
	private static final String DEFAULT_PREVIOUS_BUTTON_TITLE = "Prev";
	
	private AccountOpinionInfo accountOpinionInfo;
	
	protected IRequestExtension getRequestExtension() {
		return (IRequestExtension) super.getRequestExtension();
	}
	
	@Override
	protected void afterProcess(ICreateSurveyRequest request, Long opinionId) throws Exception {
		super.afterProcess(request, opinionId);
		
		if(request.isCreateEmptySheet()){
			// create empty sheet
			BaseOperationResult sheetResult = SheetsManager.createDefaultSheet(request.getAccountId(), request.getActionGuid(), request.getUserId(), opinionId, getTranslationId());
			if(sheetResult.hasError()){
				logger.error("Unable to create default sheet. Details: '%s'", sheetResult.toString());
			}
		}
	}
	
	@Override
	public BaseOperationResult collect(
			ICreateSurveyRequest request) throws DAOException {
		
		BaseOperationResult result = super.collect(request);
		
		// Get count of opinions
		if(null == result){
			OperationResult<AccountOpinionInfo> accountInfoResult = AccountsDataAccess
					.getAccountShortStatistics(request.getAccountId(), null, null);
			if (accountInfoResult.hasError()) {
				result = accountInfoResult.toErrorResult();
			} else {
				accountOpinionInfo = accountInfoResult.getValue();
			}
		}
		
		// Get settings
		IServicePackageSettings settings = null;
		if (null == result) {
			OperationResult<IServicePackageSettings> settingsResult = ServicePackageSettingsEntity
					.getServicePackageSettings(getAccount().getServicePackageId());
			if(settingsResult.hasError()){
				result = settingsResult.toErrorResult();
			} else {
				settings = settingsResult.getValue();
			}
		}

		// Check MaxOpinions limit
		if(null == result && null != settings.getMaxOpinions()){
			if(accountOpinionInfo.getCountOfOpinions() >= settings.getMaxOpinions()){
				logger.info("User reach his Max opinions limit. accountType: '%s', maxOpinions: '%s'", getAccount().getServicePackageId(), settings.getMaxOpinions());
				result = new OperationResult<IOpinion>(ErrorCode.MaxOpinionsReached);
			}
		}
		
		return result;
	}

	@Override
	protected BaseOperationResult validateRequest(ICreateSurveyRequest request) throws DAOException {
		// Validate
		
		BaseOperationResult result = super.validateRequest(request);
		
		// name
		if(null == result){
			result = check(null != request.getName(), ErrorCode.ArgumentNull, "argument 'name' is mandatory.");
		}
		
		// accountId
		if(null == result){
			result = check(0 < request.getAccountId(), ErrorCode.ArgumentNull, "argument 'accountId' is mandatory.");
		}
		
		return result;
	}

	@Override
	protected OpinionType getOpinionType() {
		return OpinionType.Survey;
	}

	@Override
	public String getFinishMessage() {
		return (null == getRequestExtension() ? null : getRequestExtension().getFinishMessage());
	}

	@Override
	public String getStartMessage() {
		return (null == getRequestExtension() ? null : getRequestExtension().getStartMessage());
	}

	@Override
	public String getStartButtonTitle() {
		return (null == getRequestExtension() ? DEFAULT_START_BUTTON_TITLE : getRequestExtension().getStartButtonTitle());
	}

	@Override
	public String getFinishButtonTitle() {
		return (null == getRequestExtension() ? DEFAULT_FINISH_BUTTON_TITLE : getRequestExtension().getFinishButtonTitle());
	}

	@Override
	public String getPreviousButtonTitle() {
		return (null == getRequestExtension() ? DEFAULT_PREVIOUS_BUTTON_TITLE : getRequestExtension().getPreviousButtonTitle());
	}

	@Override
	public String getNextButtonTitle() {
		return (null == getRequestExtension() ? DEFAULT_NEXT_BUTTON_TITLE : getRequestExtension().getNextButtonTitle());
	}

	@Override
	public Boolean isUseQuestionNumbering() {
		return (null == getRequestExtension() ? Boolean.TRUE : getRequestExtension().isUsePageNumbering());
	}

	@Override
	public Boolean isUsePageNumbering() {
		return (null == getRequestExtension() ? Boolean.TRUE : getRequestExtension().isUsePageNumbering());
	}

	@Override
	public String getLogoUrl() {
		return (null == getRequestExtension() ? null : getRequestExtension().getLogoUrl());
	}

	@Override
	protected BaseOperationResult createDefaultCollector(final String actionGuid, final long userId, final long opinionId) {
		return CollectorsActionsFactory.getInstance().create(new ICreateSurveysCollectorRequest() {
			
			@Override
			public long getUserId() {
				return userId;
			}
			
			@Override
			public Long getOpinionId() {
				return opinionId;
			}
			
			@Override
			public String getName() {
				return null; // Default name will be assigned
			}
			
			@Override
			public Integer getCollectorSourceId() {
				return null; // Default id will be assigned
			}
			
			@Override
			public String getActionGuid() {
				return actionGuid;
			}
		});			
	}

	@Override
	public String getRedirectUrl() {
		return (null == getRequestExtension() ? null : getRequestExtension().getRedirectUrl());
	}

	@Override
	public Integer getFinishBehaviourTypeId() {
		return (null == getRequestExtension() ? null : getRequestExtension().getFinishBehaviourTypeId());
	}
	
	@Override
	public Integer getThemeId() {
		return (null == getRequestExtension() ? ISurvey.DEFAULT_THEME_ID : getRequestExtension().getThemeId());
	}

	@Override
	public Integer getLabelPlacementId() {
		return (null == getRequestExtension() ? null : getRequestExtension().getLabelPlacementId());
	}

	@Override
	public Boolean isShowPaging() {
		return (null == getRequestExtension() ? null : getRequestExtension().isShowPaging());
	}

	@Override
	public String getAlreadyCompletedMessage() {
		return (null == getRequestExtension() ? null : getRequestExtension().getAlreadyCompletedMessage());
	}
}
