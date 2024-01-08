package com.inqwise.opinion.actions.opinions;


interface ICreateOpinionRequest {
	public long getAccountId();
	public String getActionGuid();
	public long getUserId();
	public String getName();
	public String getDescription();
	public String getTitle();
	
	interface IRequestExtension {
		public Integer getThemeId();
		public Long getTranslationId();
		public String getTranslationName();
		public String getCulture();
		public boolean isRtl();
		public Boolean getIsHighlightRequestedQuestions();
		public Boolean isHidePoweredBy();
	}
}
