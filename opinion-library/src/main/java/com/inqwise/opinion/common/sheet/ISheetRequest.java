package com.inqwise.opinion.opinion.common.sheet;

public interface ISheetRequest {
	public Long getOpinionId();
	public Integer getPageNumber();
	public Long getAccountId();
	public Long getTranslationId();
	public String getTitle();
	public String getDescription();
	public String getActionGuid();
	public long getUserId();
}