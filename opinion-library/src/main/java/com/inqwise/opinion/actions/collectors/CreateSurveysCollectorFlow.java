package com.inqwise.opinion.actions.collectors;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.actions.collectors.ICreateSurveysCollectorRequest.IRequestExtension;
import com.inqwise.opinion.common.AccountOpinionInfo;
import com.inqwise.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.common.servicePackage.IServicePackageSettings;
import com.inqwise.opinion.dao.AccountsDataAccess;
import com.inqwise.opinion.entities.ServicePackageSettingsEntity;

class CreateSurveysCollectorFlow extends CreateCollectorFlow<ICreateSurveysCollectorRequest> {

	private ICreateSurveysCollectorRequest.IPasswordExtension passwordExtension;
	protected ICreateSurveysCollectorRequest.IPasswordExtension getPasswordExtension() {
		return passwordExtension;
	}

	protected ICreateSurveysCollectorRequest.IRequestExtension getRequestExtension() {
		return (IRequestExtension) super.getRequestExtension();
	}
	  
	@Override
	public Boolean getIsHidePassword() {
		return (null == getRequestExtension() ? null : getPasswordExtension().getIsHidePassword());
	}
	
	@Override
	public String getPassword() {
		return (null == getRequestExtension() ? null : getPasswordExtension().getPassword());
	}
	
	@Override
	public String getReferer() {
		return null == collectorSource ? null : collectorSource.getReferer();
	}
	
	@Override
	public String getReturnUrl() {
		return null == collectorSource ? null : collectorSource.getReturnUrl();
	}
	
	@Override
	public String getScreenOutUrl() {
		return null == collectorSource ? null : collectorSource.getScreenOutUrl();
	}
	
	@Override
	public Boolean isEnablePrevious() {
		return (null == requestExtension ? false : requestExtension.isEnablePrevious());
	}
	
	@Override
	public String getOpinionClosedUrl() {
		return null == collectorSource ? null : collectorSource.getClosedUrl();
	}
	
	protected CreateSurveysCollectorFlow() {
	}
	
	@Override
	protected BaseOperationResult validateRequest(
			ICreateSurveysCollectorRequest request) throws DAOException {
		
		BaseOperationResult result = super.validateRequest(request);
		return result;
	}
	
	@Override
	public OperationResult<Long> process(ICreateSurveysCollectorRequest request) {
		if (request instanceof ICreateSurveysCollectorRequest.IPasswordExtension) {
			passwordExtension = (ICreateSurveysCollectorRequest.IPasswordExtension) request;
		}
		return super.process(request);
	}
	
	@Override
	protected BaseOperationResult collect(
			ICreateSurveysCollectorRequest request) throws DAOException {
	
		BaseOperationResult result = super.collect(request);
		
		// Get count of opinions
		AccountOpinionInfo accountOpinionInfo = null;
		if(null == result){
			OperationResult<AccountOpinionInfo> accountInfoResult = AccountsDataAccess
					.getAccountShortStatistics(getAccountId(), null, null);
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
					.getServicePackageSettings(getServicePackageId());
			if(settingsResult.hasError()){
				result = settingsResult.toErrorResult();
			} else {
				settings = settingsResult.getValue();
			}
		}
		
		// Check MaxCollectors limit
		if(null == result && null != settings.getMaxCollectors()){
			if(accountOpinionInfo.getCountOfCollectors() >= settings.getMaxCollectors()){
				logger.info("User reach his Max collectors limit. accountType: '%s', maxCollectors: '%s'", getServicePackageId(), settings.getMaxCollectors());
				result = new OperationResult<Long>(ErrorCode.MaxCollectorsReached);
			}
		}
		
		return result;
	}
		
	@Override
	public OpinionType getOpinionType() {
		return OpinionType.Survey;
	}
}
