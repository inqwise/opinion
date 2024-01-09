package com.inqwise.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.common.OptionKind;

public class AnalizeSimpleOptionEntity extends AnalizeOptionEntity {
	
	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnalizeSimpleOptionEntity.class);
	
	private List<Long> answerSelectedControlIds; /*for matrix*/
	private boolean hasComments;
	private String answerComment;
	//private JSONObject answerSelectedControlsStatistics;
	private Map<Long, StatisticalUnit> statisticalMap;
	
	@Override
	protected void fill(ResultSet reader, OptionKind optionKind)
			throws SQLException {
		super.fill(reader, optionKind);
		setAnswerComment(ResultSetHelper.optString(reader, "answer_comment"));
		String strIds = ResultSetHelper.optString(reader, "answer_selected_control_ids");
		if(null != strIds && getIsSelected()){
			setAnswerSelectedControlIds(new ArrayList<Long>());
			for(String strId : StringUtils.split(strIds, ',')){
				getAnswerSelectedControlIds().add(Long.valueOf(strId));
			}
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
		setHasComments(ResultSetHelper.optBool(reader, "has_comments", false));
	}
	
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject optionJo = super.toJson(onlyPercentageInStatistics);
		if(null != getAnswerSelectedControlIds()){
			optionJo.put("answerSelectedControlsIds", getAnswerSelectedControlIds());
		}
		optionJo.put("answerComment", getAnswerComment());
		optionJo.put("hasComments", hasComments());
		JSONObject joStatistics = getJsonStatisticalMap(statisticalMap, onlyPercentageInStatistics);
		optionJo.put("statistics", joStatistics);
		return optionJo;
	}

	private void setAnswerSelectedControlIds(List<Long> answerSelectedControlIds) {
		this.answerSelectedControlIds = answerSelectedControlIds;
	}

	private List<Long> getAnswerSelectedControlIds() {
		return answerSelectedControlIds;
	}

	public void setAnswerComment(String answerComment) {
		this.answerComment = answerComment;
	}

	public String getAnswerComment() {
		return answerComment;
	}

	public boolean hasComments() {
		return hasComments;
	}

	public void setHasComments(boolean hasComments) {
		this.hasComments = hasComments;
	}
}
