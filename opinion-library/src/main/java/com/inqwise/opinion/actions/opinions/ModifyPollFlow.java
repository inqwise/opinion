package com.inqwise.opinion.actions.opinions;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;


class ModifyPollFlow extends ModifyOpinionFlow<IModifyPollRequest> {

	@Override
	protected IModifyPollRequest getRequest() {
		return (IModifyPollRequest) super.getRequest();
	}
	
	@Override
	public String getStartMessage() {
		return null;
	}

	@Override
	public String getFinishMessage() {
		return null;
	}

	@Override
	public String getRedirectUrl() {
		return null;
	}

	@Override
	public Boolean isShowProgressBar() {
		return false;
	}

	@Override
	public String getStartButtonTitle() {
		return null;
	}

	@Override
	public String getFinishButtonTitle() {
		return getRequest().getVoteButtonTitle();
	}

	@Override
	public String getPreviousButtonTitle() {
		return getRequest().getPreviousButtonTitle();
	}

	@Override
	public String getNextButtonTitle() {
		return getRequest().getResultsButtonTitle();
	}

	@Override
	public Boolean isUsePageNumbering() {
		return null;
	}

	@Override
	public Boolean isUseQuestionNumbering() {
		return null;
	}

	@Override
	public String GetLogoUrl() {
		return null;
	}

	@Override
	public Integer getFinishBehaviourTypeId() {
		return null;
	}

	@Override
	public BaseOperationResult process(IModifyPollRequest request) {
		return null;
	}

	@Override
	public Integer getLabelPlacementId() {
		return null;
	}

	@Override
	public Boolean isShowPaging() {
		return null;
	}

	@Override
	public String getAlreadyCompletedMessage() {
		return null;
	}
	
	@Override
	public String getRequiredQuestionErrorMessage() {
		return null;
	}
}
