package com.inqwise.opinion.actions.opinions;

public interface IModifySurveyRequest extends IModifyOpinionRequest {
	public String getStartMessage();
	public String getFinishMessage();
	public String getRedirectUrl();
	public boolean isShowProgressBar();
	public String getStartButtonTitle();
	public String getFinishButtonTitle();
	public String getPreviousButtonTitle();
	public String getNextButtonTitle();
	public boolean isUsePageNumbering();
	public boolean isUseQuestionNumbering();
	public String getLogoUrl();
	public Integer getFinishBehaviourTypeId();
	public Integer getLabelPlacementId();
	public Boolean isShowPaging();
	public String getAlreadyCompletedMessage();
	public String getRequiredQuestionErrorMessage();
}