package com.inqwise.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.common.ControlType;

public class AnalizeFreeTextControlEntity extends AnalizeAdvancedControlEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnalizeFreeTextControlEntity.class);
	
	private String answerValue;
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		setAnswerValue(ResultSetHelper.optString(reader, "answer_value"));
		super.fill(reader, controlType);
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerValue() {
		return answerValue;
	}
	
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject controlJo = super.toJson(onlyPercentageInStatistics);
		controlJo.put("answerValue", getAnswerValue());
		return controlJo;
	}
}
