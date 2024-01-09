package com.inqwise.opinion.actions.collectors;


class ModifyPollsCollectorFlow extends ModifyCollectorFlow<IModifyPollsCollectorRequest> {

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public Boolean isHidePassword() {
		return null;
	}

	@Override
	public Boolean isEnablePrevious() {
		return null;
	}

	@Override
	public Boolean isEnableRssUpdates() {
		return null;
	}

	@Override
	public String getReturnUrl() {
		return null;
	}

	@Override
	public boolean skipReturnUrl() {
		return true;
	}

	@Override
	public String getScreenOutUrl() {
		return null;
	}

	@Override
	public boolean skipScreenOutUrl() {
		return true;
	}

	@Override
	public String getReferer() {
		return null;
	}

	@Override
	public boolean skipReferer() {
		return true;
	}

	@Override
	public boolean skipPassword() {
		return true;
	}

	@Override
	public String getOpinionClosedUrl() {
		return null;
	}

	@Override
	public boolean skipOpinionClosedUrl() {
		return true;
	}
	
	@Override
	public Integer getResultsTypeId() {
		return getRequest().getResultsTypeId();
	}
}
