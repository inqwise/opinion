package com.inqwise.opinion.opinion.common.analizeResults;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.opinion.common.ParentType;

public interface IAnalizeControl {

	public abstract JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException;

	public abstract ParentType getParentType();

	public abstract Object getParentId();

	public abstract Long getControlId();
	
	public abstract Integer getCountOfSkip();
	
	public abstract Integer getCountOfAnswers();
	
	public abstract boolean isHidden();
	
	public abstract String getKey();
	
	public abstract boolean isNumerable();

}