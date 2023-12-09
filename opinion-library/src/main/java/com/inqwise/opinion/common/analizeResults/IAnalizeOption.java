package com.inqwise.opinion.opinion.common.analizeResults;

import org.json.JSONException;
import org.json.JSONObject;

public interface IAnalizeOption {

	public abstract JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException;

	public abstract Object getControlId();

}