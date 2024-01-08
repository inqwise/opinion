package com.inqwise.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.common.ControlType;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

public class AnalizeScaleControlEntity extends AnalizeAdvancedControlEntity {
	
	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnalizeScaleControlEntity.class);
	
	public static final String TO_TITLE = "toTitle";
	public static final String FROM_TITLE = "fromTitle";
	public static final String TO_SCALE = "toScale";
	public static final String FROM_SCALE = "fromScale";
	public static final String ANSWER_VALUE_KEY = "answerValue";
	private Integer from;
	private Integer to;
	private String fromTitle;
	private String toTitle;
	private String answerValue;
	private JSONObject statistics;
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		setFrom(ResultSetHelper.optInt(reader, "from_scale"));
		setTo(ResultSetHelper.optInt(reader, "to_scale"));
		setFromTitle(ResultSetHelper.optString(reader, "from_scale_title"));
		setToTitle(ResultSetHelper.optString(reader, "to_scale_title"));
		setAnswerValue(ResultSetHelper.optString(reader, "answer_value"));
		String strSelectedData = ResultSetHelper.optString(reader, "scale_stat_answer_values");
		//if(null != strSelectedData){
			setStatistics(calculateStatistics(strSelectedData));
		//}
		super.fill(reader, controlType);
	}

	private JSONObject calculateStatistics(String strSelectedData) throws JSONException {
		JSONObject statistics = new JSONObject();
		String[] selectedValues = StringUtils.split(strSelectedData, ',');
				
		JSONObject countPerValue = new JSONObject();
		
		float grandTotal = 0;
		
		if(null != selectedValues){
			for(String selectedValue : selectedValues){
				if(countPerValue.has(selectedValue)){
					countPerValue.put(selectedValue, countPerValue.getInt(selectedValue) + 1);
				} else {
					countPerValue.put(selectedValue, 1);
				}
				grandTotal ++;
			}
		
		
			Iterator keys = countPerValue.keys();
			
			float onePercent = 100f / grandTotal;
			while(keys.hasNext()){
				String key = String.valueOf(keys.next());
				float count = countPerValue.getInt(key);
				float percentage = count * onePercent;
				JSONObject singleStatistics = new JSONObject();
				singleStatistics.put("count", count);
				singleStatistics.put("percentage", String.format("%3.2f", percentage));
				
				statistics.put(key, singleStatistics);
			}
			statistics.put("count", selectedValues.length);
			statistics.put("sum", grandTotal);
		}
		
		
		return statistics;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getFrom() {
		return from;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Integer getTo() {
		return to;
	}

	public void setFromTitle(String fromTitle) {
		this.fromTitle = fromTitle;
	}

	public String getFromTitle() {
		return fromTitle;
	}

	public void setToTitle(String toTitle) {
		this.toTitle = toTitle;
	}

	public String getToTitle() {
		return toTitle;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerValue() {
		return answerValue;
	}
	
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject jo = super.toJson(onlyPercentageInStatistics);
		jo.put(FROM_SCALE, getFrom());
		jo.put(TO_SCALE, getTo());
		jo.put(FROM_TITLE, getFromTitle());
		jo.put(TO_TITLE, getToTitle());
		jo.put(ANSWER_VALUE_KEY, getAnswerValue());
		
		JSONObject joStatistics;
		if(null != getStatistics() && onlyPercentageInStatistics){
			joStatistics = new JSONObject(getStatistics(), new String[]{"percentage"});
			joStatistics = new JSONObject().put("percentage", getStatistics().get("percentage"));
		} else {
			joStatistics = getStatistics();
		}
		jo.put("statistics", JSONHelper.getNullable(joStatistics));
		
		return jo;
	}

	public void setStatistics(JSONObject statistics) {
		this.statistics = statistics;
	}

	public JSONObject getStatistics() {
		return statistics;
	}
}
