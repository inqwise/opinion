package com.inqwise.opinion.common.analizeResults;

import org.json.JSONException;
import org.json.JSONObject;

public interface IAnalizeOption {

	public abstract JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException;

	public abstract Object getControlId();

}