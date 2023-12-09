package com.inqwise.opinion.opinion.common.opinions;



public interface ISurvey extends IOpinion, ISheetsContainer {

	Integer DEFAULT_THEME_ID = 1;
	
	public String getFinishMessage();

	public String getStartMessage();
	
	public Boolean getShowProgressBar();
	
	public String getRedirectUrl();
	
	public String getStartButtonTitle();

	public String getFinishButtonTitle();

	public String getPreviousButtonTitle();

	public String getNextButtonTitle();

	public Boolean isUseQuestionNumbering();
	public Boolean isUsePageNumbering();
	public String getLogoUrl();

	public abstract String getAlreadyCompletedMessage();
}
