package com.inqwise.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.common.OptionKind;

public class AnalizeOtherOptionEntity extends AnalizeOptionEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -929081030945952414L;

	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnalizeOtherOptionEntity.class);
	
	private String answerValue;
	//private JSONObject answerSelectedControlsStatistics;
	Map<Long, StatisticalUnit> statisticalMap;
	
	@Override
	protected void fill(ResultSet reader, OptionKind optionKind)
			throws SQLException {
		super.fill(reader, optionKind);
		if(getIsSelected()){
			setAnswerValue(ResultSetHelper.optString(reader, "answer_value"));
		}
		
		String strStat = ResultSetHelper.optString(reader, "control_obj_arr");
		if(null != strStat){
			try {
				JSONObject answerSelectedControlsStatistics = new JSONObject(strStat);
				
				statisticalMap = new HashMap<Long, StatisticalUnit>();
				
				for (Iterator<String> iterator = answerSelectedControlsStatistics.keys(); iterator
						.hasNext();) {
					String strControlId = iterator.next();
					JSONObject joStatisticalUnit = answerSelectedControlsStatistics.getJSONObject(strControlId);
					statisticalMap.put(Long.valueOf(strControlId), new StatisticalUnit(joStatisticalUnit));
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerValue() {
		return answerValue;
	}
	
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject optionJo = super.toJson(onlyPercentageInStatistics);
		optionJo.put("answerValue", getAnswerValue());
		JSONObject joStatistics = getJsonStatisticalMap(statisticalMap, onlyPercentageInStatistics);
		optionJo.put("statistics", joStatistics);
		return optionJo;
	}
}
