package com.inqwise.opinion.opinion.entities.controls;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.opinion.common.Arrange;
import com.inqwise.opinion.opinion.common.ControlType;
import com.inqwise.opinion.opinion.common.IControlRequest;
import com.inqwise.opinion.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.opinion.common.LinkType;

public abstract class AdvancedControlEntity extends ControlEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ApplicationLog logger = ApplicationLog.getLogger(AdvancedControlEntity.class);
	private String note;
	private String comment;
	private Boolean isMandatory;
	private Integer inputTypeId;
	private Arrange arrange;
	private String  link;
	private LinkType linkType;
	private String answerComment;
	
	public AdvancedControlEntity(ResultSet reader, ControlType controlType)
			throws Exception {
		super(reader, controlType);
	}

	public AdvancedControlEntity() {
	}

	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		setArrange(Arrange.fromInt(reader.getInt("arrange_id")));
		setNote(ResultSetHelper.optString(reader, "note"));
		setComment(ResultSetHelper.optString(reader, "comment"));
		setIsMandatory(reader.getBoolean("is_mandatory"));
		setInputTypeId(Integer.valueOf(reader.getInt("input_type_id")));
		setLink(ResultSetHelper.optString(reader, "link"));
		setLinkType(LinkType.fromInt(ResultSetHelper.optInt(reader, "link_type_id", 0)));
		setAnswerComment(ResultSetHelper.optString(reader, "answer_comment"));
		super.fill(reader, controlType);
	}
	
	@Override
	public BaseOperationResult importRequest(IControlRequest request) {
		BaseOperationResult result = super.importRequest(request);
				
		if(!result.hasError()){
			setArrange(null == request.getArrangeId() ? Arrange.Unknown : Arrange.fromInt(request.getArrangeId()));
			setNote(request.getNote());
			setIsMandatory(request.getIsMandatory());
			setComment(request.getComment()); 
			setIsMandatory(request.getIsMandatory());
			setInputTypeId(request.getInputTypeId());
			setLink(request.getLink());
			setLinkType(LinkType.fromInt(request.getLinkTypeId()));
		}
		return result;
	}

	public void setArrange(Arrange arrange) {
		this.arrange = arrange;
	}

	public Arrange getArrange() {
		return arrange;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}
	
	public void setInputTypeId(Integer inputTypeId) {
		this.inputTypeId = inputTypeId;
	}

	public Integer getInputTypeId() {
		return inputTypeId;
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jo = super.toJson();
		
		jo.put(JsonNames.ARRANGE_ID, getArrange().getValueOrNullWhenUnknown())
		.put(JsonNames.NOTE, getNote());
		if(null != getIsMandatory() && getIsMandatory()){
			jo.put(JsonNames.IS_MANDATORY, getIsMandatory());
		}
		
		jo.put(JsonNames.INPUT_TYPE_ID, getInputTypeId())
		.put(JsonNames.COMMENT, getComment())
		.put(JsonNames.LINK, getLink())
		.put(JsonNames.LINK_TYPE_ID, getLinkType().getValueOrNullWhenUndefined());
		
		return jo;
	}
	
	protected JSONObject getBaseJson() throws JSONException{
		return super.toJson();
	}
	
	@Override
	protected BaseOperationResult validateModifyRequest(
			IModifyControlDetailsRequest request) {
		
		validate(null != request.getInputTypeId(), ErrorCode.ArgumentNull, "`inputTypeId` is manatory");
		validate(null != request.getIsMandatory(), ErrorCode.ArgumentNull, "`isManatory` is mandatory");
		return hasError() ? this : super.validateModifyRequest(request);
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

	public LinkType getLinkType() {
		return linkType;
	}
	
	@Override
	public JSONObject toJson(boolean withAnswer) throws JSONException {
		JSONObject jo = super.toJson(withAnswer);
		if(withAnswer){
			jo.put(JsonNames.ANSWER_COMMENT_KEY, getAnswerComment());
		}
		return jo;
	}

	public void setAnswerComment(String answerComment) {
		this.answerComment = answerComment;
	}

	public String getAnswerComment() {
		return answerComment;
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = super.getExportJson();
		output.put(JsonNames.NOTE, getNote());
		output.put(JsonNames.COMMENT, getComment());
		if(null != getIsMandatory() && getIsMandatory()){
			output.put(JsonNames.IS_MANDATORY, getIsMandatory());
		}
		output.put(JsonNames.INPUT_TYPE_ID, getInputTypeId());
		output.put(JsonNames.ARRANGE_ID, getArrange().getValueOrNullWhenUnknown());
		output.put(JsonNames.LINK, getLink());
		output.put(JsonNames.LINK_TYPE_ID, getLinkType().getValueOrNullWhenUndefined());
		return output;
	}
}

