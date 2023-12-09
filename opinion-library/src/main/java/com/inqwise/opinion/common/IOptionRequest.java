package com.inqwise.opinion.opinion.common;

public interface IOptionRequest {
	public String getValue();
	public String getText();
	public Long getTranslationId();
	public Integer getOrderId();
	public Long getControlId();
	public Long getAccountId();
	public Boolean getIsEnableAdditionalDetails();
	public String getAdditionalDetailsTitle();
	public Integer getOptionKindId();
	public Long getOpinionId();
	public long getUserId();
	public String getLink();
	public Integer getLinkTypeId();
}
