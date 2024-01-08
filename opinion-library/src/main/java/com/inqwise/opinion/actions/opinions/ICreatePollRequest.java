package com.inqwise.opinion.actions.opinions;



public interface ICreatePollRequest extends ICreateOpinionRequest {
	interface IRequestExtension extends ICreateOpinionRequest.IRequestExtension {
		public String getVoteButtonTitle();
		public String getResultsButtonTitle();
		public String getPreviousButtonTitle();
	}
}
