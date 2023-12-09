package com.inqwise.opinion.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.ControlType;
import com.inqwise.opinion.opinion.common.IControl;

public abstract class AnalizeAdvancedControlEntity extends AnalizeControlEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String IS_MANDATORY = "isMandatory";
	public static final String COMMENT = "comment";
	public static final String ANSWER_COMMENT_KEY = "answerComment";
	private static final String HAS_COMMENTS = "hasComments";
	
	private Integer inputTypeId;
	private String note;
	private String answerComment;
	private Boolean isMandatory;
	private String comment;
	private Boolean hasComments;
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		setInputTypeId(ResultSetHelper.optInt(reader, "input_type_id"));
		setNote(ResultSetHelper.optString(reader, "note"));
		setAnswerComment(ResultSetHelper.optString(reader, "answer_comment"));
		setIsMandatory(reader.getBoolean("is_mandatory"));
		setComment(ResultSetHelper.optString(reader, "comment"));
		setHasComments(ResultSetHelper.optBool(reader, "has_comments"));
		super.fill(reader, controlType);
	}
	
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject jo = super.toJson(onlyPercentageInStatistics);
		jo.put(IControl.JsonNames.INPUT_TYPE_ID, getInputTypeId())
		.put("note", getNote())
		.put("answerComment", getAnswerComment())
		.put(COMMENT, getComment());
		if(null != getIsMandatory() && getIsMandatory()){
			jo.put(IS_MANDATORY, getIsMandatory());
		}
		if(null != hasComments() && hasComments()){
			jo.put(HAS_COMMENTS, hasComments());
		}
		
		return jo;
	}
	
	public void setInputTypeId(Integer inputTypeId) {
		this.inputTypeId = inputTypeId;
	}

	public Integer getInputTypeId() {
		return inputTypeId;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setAnswerComment(String answerComment) {
		this.answerComment = answerComment;
	}

	public String getAnswerComment() {
		return answerComment;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public Boolean hasComments() {
		return hasComments;
	}

	public void setHasComments(Boolean hasComments) {
		this.hasComments = hasComments;
	}
}
