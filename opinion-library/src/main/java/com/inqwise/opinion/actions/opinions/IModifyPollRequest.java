package com.inqwise.opinion.actions.opinions;

public interface IModifyPollRequest extends IModifyOpinionRequest {
	public String getVoteButtonTitle();
	public String getPreviousButtonTitle();
	public String getResultsButtonTitle();
}
