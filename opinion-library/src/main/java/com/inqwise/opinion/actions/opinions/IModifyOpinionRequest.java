package com.inqwise.opinion.actions.opinions;

interface IModifyOpinionRequest {
	public long getOpinionId();
	public Long getAccountId();
	public Long getTranslationId();
	public boolean isHighlightRequiredQuestions();
	public boolean isRtl();
	public long getUserId();
	public Boolean isHidePoweredBy();
}
