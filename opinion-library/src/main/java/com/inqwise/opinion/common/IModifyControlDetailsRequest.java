package com.inqwise.opinion.common;

public interface IModifyControlDetailsRequest {
	
	public String getContent();
	public String getNote();
	public Boolean getIsMandatory();
	public Integer getInputTypeId();
	public String getComment();
	public Long getTranslationId();
	public Integer getInputSizeTypeId();
	public String getYearTitle();
	public String getMonthTitle();
	public String getDayTitle();
	public String getHourTitle();
	public String getMinuteTitle();
	public String getTimezoneTitle();
	public Long getAccountId();
	public Long getControlId();
	public Integer getFromScale();
	public Integer getToScale();
	public String getFromScaleTitle();
	public String getToScaleTitle();
	public String getLink();
	public Integer getLinkTypeId();
	public long getUserId();
	public String getKey();
}
