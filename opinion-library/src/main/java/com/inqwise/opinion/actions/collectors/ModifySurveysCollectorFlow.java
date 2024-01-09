package com.inqwise.opinion.actions.collectors;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;

class ModifySurveysCollectorFlow extends ModifyCollectorFlow<IModifySurveysCollectorRequest>  {
	
	private IModifySurveysCollectorRequest.ISecurityExtension securityExtension;
	private IModifySurveysCollectorRequest.IRedirectExtension redirectExtension;
	private IModifySurveysCollectorRequest.IIdentityExtension identityExtension;

	@Override
	protected IModifySurveysCollectorRequest getRequest() {
		return (IModifySurveysCollectorRequest) super.getRequest();
	}
	
	@Override
	public String getPassword() {
		return null == securityExtension ? null : securityExtension.getPassword();
	}

	@Override
	public Boolean isHidePassword() {
		return null == securityExtension ? null : securityExtension.isHidePassword();
	}

	@Override
	public Boolean isEnablePrevious() {
		return getRequest().isEnablePrevious();
	}



	@Override
	public Boolean isEnableRssUpdates() {
		return getRequest().isEnableRssUpdates();
	}

	@Override
	public String getReturnUrl() {
		return null == redirectExtension ? null : redirectExtension.getReturnUrl();
	}

	@Override
	public boolean skipReturnUrl() {
		return null == redirectExtension;
	}

	@Override
	public String getScreenOutUrl() {
		return null == redirectExtension ? null : redirectExtension.getScreenOutUrl();
	}

	@Override
	public boolean skipScreenOutUrl() {
		return null == redirectExtension;
	}

	@Override
	public String getReferer() {
		return null == identityExtension ? null : identityExtension.getReferer();
	}

	@Override
	public boolean skipReferer() {
		return null == identityExtension;
	}

	@Override
	public boolean skipPassword() {
		return null == securityExtension;
	}

	@Override
	public String getOpinionClosedUrl() {
		return null == redirectExtension ? null : redirectExtension.getSurveyClosedUrl();
	}

	@Override
	public boolean skipOpinionClosedUrl() {
		return null == redirectExtension;
	}	

	@Override
	public BaseOperationResult process(IModifySurveysCollectorRequest request) {
		if (request instanceof IModifySurveysCollectorRequest.IIdentityExtension) {
			identityExtension = (IModifySurveysCollectorRequest.IIdentityExtension) request;
		}
		
		if (request instanceof IModifySurveysCollectorRequest.IRedirectExtension) {
			redirectExtension = (IModifySurveysCollectorRequest.IRedirectExtension) request;
		}
		
		if (request instanceof IModifySurveysCollectorRequest.ISecurityExtension) {
			securityExtension = (IModifySurveysCollectorRequest.ISecurityExtension) request;
		}
		return super.process(request);
	}
	
	@Override
	protected BaseOperationResult validateRequest(IModifySurveysCollectorRequest request) {
		
		BaseOperationResult result = super.validateRequest(request);
		//if(null == result) result = check(null != request.isEnablePrevious(), ErrorCode.ArgumentNull, "`enablePrevious` is mandatory");
		if(null == result) result = check(null != request.isEnableRssUpdates(), ErrorCode.ArgumentNull, "`enableRssUpdates` is mandatory");
		
		return result;
	}

}
