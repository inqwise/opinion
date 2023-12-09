package com.inqwise.opinion.opinion.entities.analizeResults;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

public class StatisticalUnit {

	private static final String COUNT = "count";
	private static final String SUM = "sum";
	private static final String PERCENTAGE = "percentage";
	private Long count;
	private Double percentage;
	private Long sum;
	
	public StatisticalUnit(JSONObject input) {
		setCount(JSONHelper.optLong(input, COUNT));
		setPercentage(JSONHelper.optDouble(input, PERCENTAGE));
		setSum(JSONHelper.optLong(input, SUM));
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}
	
	public JSONObject toJson(boolean onlyPercentage) throws JSONException{
		JSONObject jo = new JSONObject();
		jo.put(PERCENTAGE, getPercentage());
		
		if(!onlyPercentage){
			jo.put(SUM, getSum());
			jo.put(COUNT, getCount());
		}
		
		return jo;
	}
}
