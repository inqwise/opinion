package com.inqwise.opinion.actions.opinions;

public interface ICreateSurveyRequest extends ICreateOpinionRequest {
	
	public boolean isCreateEmptySheet();
	
	interface IRequestExtension extends ICreateOpinionRequest.IRequestExtension {
		public String getFinishMessage();
		public String getStartMessage();
		public String getStartButtonTitle();
		public String getFinishButtonTitle();
		public String getPreviousButtonTitle();
		public String getNextButtonTitle();
		public Boolean isUseQuestionNumbering();
		public Boolean isUsePageNumbering();
		public String getLogoUrl();
		public String getRedirectUrl();
		public Integer getFinishBehaviourTypeId();
		public Integer getLabelPlacementId();
		public Boolean isShowPaging();
		public String getAlreadyCompletedMessage();
	}
}
