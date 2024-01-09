package com.inqwise.opinion.common.opinions;

import java.util.List;

import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.IControl;


public interface IPoll extends IOpinion {
	
	Integer DEFAULT_THEME_ID = 10;

	public String getResultsButtonTitle();

	public String getVoteButtonTitle();

	public String getPreviousButtonTitle();

	public OperationResult<List<IControl>> getControls();
}
