package com.inqwise.opinion.opinion.common;

public interface IControlRequest {
	public Long getAccountId();
	public String getContent();
	public Integer getControlTypeId();
	public Integer getOrderId();
	public Long getParentId();
	public Integer getParentTypeId();
	public Long getTranslationId();
	public Integer getArrangeId();
	public Long getOpinionId() ;
	public String getNote();
	public Boolean getIsMandatory();
	public Integer getInputTypeId();
	public String getComment();
	public Integer getInputSizeTypeId();
	public String getYearTitle();
	public String getMonthTitle();
	public String getDayTitle();
	public String getHourTitle();
	public String getMinuteTitle();
	public String getTimezoneTitle();
	public Integer getFromScale();
	public Integer getToScale();
	public String getFromScaleTitle();
	public String getToScaleTitle();
	public String getLink();
	public Integer getLinkTypeId();
	public String getActionGuid();
	public long getUserId();
	public boolean isHidden();
	public String getKey();
	public boolean isNumerable();
}
