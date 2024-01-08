package com.inqwise.opinion.common;


public interface IResponseRequest {

	Long getTranslationId();

	Long getOpinionId();

	Long getParentId();

	Integer getParentTypeId();

	Long getControlId();

	Long getOptionId();

	String getControlContent();

	String getAnswerText();

	String getAnswerValue();

	String getClientIp();

	String getComment();

	Integer getPageNumer();

	Integer getControlIndex();

	Long getUserId();

	Integer getResponseTypeId();

	Integer getControlTypeId();

	Long getCollectorId();

	Integer getOpinionVersionId();

	String getGuid();

	Integer getGuidTypeId();

	String getOsName();

	String getOsPlatform();

	String getOsTimeZone();

	String getOsLanguage();

	Integer getScreenWidth();

	Integer getScreenHeight();

	Integer getScreenColorDepth();

	String getBrowserAppName();

	String getBrowserVersion();

	Boolean getIsCookieEnabled();

	Boolean getIsJavaInstalled();

	String getFlashVersion();

	String getAnswerSessionId();

	String getBrowserName();

	Long getSheetId();

	Integer getTargetPageNumber();

	Long getTargetSheetId();

	Integer getControlInputTypeId();

	/*
	 * 1 - singleSelect, 2 - multiSelect (default: 1)
	 */
	Integer getSelectTypeId();

	Integer getOptionKindId();

	Integer getOptionIndex();

	Boolean getIsSelected();
	
	public Integer getAccountServicePackageId();

	boolean isCreateNewSession();

	Long getOpinionAccountId();

	Integer getOpinionAccountServicePackageId();

	Boolean isUnpanned();

	String getRespondentId();

	String getTargetUrl();

	String getControlKey();

}
