package com.inqwise.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.entities.BaseEntity;
import com.inqwise.opinion.common.IOption;
import com.inqwise.opinion.common.LinkType;
import com.inqwise.opinion.common.OptionKind;
import com.inqwise.opinion.common.analizeResults.IAnalizeOption;

public abstract class AnalizeOptionEntity extends BaseEntity implements IAnalizeOption {
	/**
	 * 
	 */
	private static final long serialVersionUID = -860194816542460256L;
	public static final String OPTION_KIND_ID = "optionKindId";
	public static final String VALUE = "value";
	public static final String TEXT = "text";
	public static final String OPTION_ID = "optionId";
	public static final String IS_ENABLE_ADDITIONAL_DETAILS = "isEnableAdditionalDetails";
	public static final String ADDITIONAL_DETAILS_TITLE = "additionalDetailsTitle";

	public static ApplicationLog logger = ApplicationLog.getLogger(AnalizeOptionEntity.class);
	
	private OptionKind optionKind;
	private String value;
	private String text;
	private Long optionId;
	private Long controlId;
	private Boolean isSelected;
	private Integer answerSelectTypeId;
	private Boolean isEnableAdditionalDetails;
	private String additionalDetailsTitle;
	private String  link;
	private LinkType linkType;
	
	public static IAnalizeOption createOptionByKind(OptionKind optionKind, ResultSet reader) throws Exception{
		AnalizeOptionEntity returnValue;
		switch(optionKind){
			case DefaultUnselectable:
				returnValue = new AnalizeDefaultUnselectableOptionEntity();
				break;
			case Simple:
				returnValue = new AnalizeSimpleOptionEntity();
				break;
			case Other:
				returnValue = new AnalizeOtherOptionEntity();
				break;
			default:
				throw new Exception("createOptionByKind() : optionKind not supported. Name: " + optionKind);
		}
		
		if(null != reader){
			returnValue.fill(reader, optionKind);
		}
		
		return returnValue;
	}

	protected void fill(ResultSet reader, OptionKind optionKind) throws SQLException {
		setOptionId(ResultSetHelper.optLong(reader, "option_id"));
		setOptionKind(optionKind);
		setText(ResultSetHelper.optString(reader, "option_text"));
		setValue(ResultSetHelper.optString(reader, "option_value"));
		setControlId(ResultSetHelper.optLong(reader, "control_id"));
		setIsSelected(ResultSetHelper.optBool(reader, "answer_is_selected", false));
		setAnswerSelectTypeId(ResultSetHelper.optInt(reader, "answer_select_type_id"));
		setIsEnableAdditionalDetails(ResultSetHelper.opt(reader, "is_enable_additional_info", Boolean.class));
		setAdditionalDetailsTitle(ResultSetHelper.optString(reader, "additional_details_title"));
		setLink(ResultSetHelper.optString(reader, "link"));
		setLinkType(LinkType.fromInt(ResultSetHelper.optInt(reader, "link_type_id", 0)));
	}

	public void setOptionKind(OptionKind optionKind) {
		this.optionKind = optionKind;
	}

	public OptionKind getOptionKind() {
		return optionKind;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	public Long getOptionId() {
		return optionId;
	}
	
	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.analizeResults.IAnalizeOption#toJson()
	 */
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject optionJo = new JSONObject(onlyPercentageInStatistics);
		optionJo.put("optionId", getOptionId());
		optionJo.put("optionKindId", getOptionKind().getValue());
		optionJo.put("text", getText());
		optionJo.put("value", getValue());
		optionJo.put(ADDITIONAL_DETAILS_TITLE, getAdditionalDetailsTitle());
		optionJo.put(IS_ENABLE_ADDITIONAL_DETAILS, getIsEnableAdditionalDetails());
		optionJo.put(IOption.JsonNames.LINK, getLink());
		optionJo.put(IOption.JsonNames.LINK_TYPE_ID, getLinkType().getValueOrNullWhenUndefined());
		if(null != getIsSelected()){
			optionJo.put("selectTypeId", getIsSelected() ? 1 : 0);
		}
		return optionJo;
	}

	public void setControlId(Long controlId) {
		this.controlId = controlId;
	}

	public Long getControlId() {
		return controlId;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setAnswerSelectTypeId(Integer answerSelectTypeId) {
		this.answerSelectTypeId = answerSelectTypeId;
	}

	public Integer getAnswerSelectTypeId() {
		return answerSelectTypeId;
	}

	public Boolean getIsEnableAdditionalDetails() {
		return isEnableAdditionalDetails;
	}

	public void setIsEnableAdditionalDetails(Boolean isEnableAdditionalDetails) {
		this.isEnableAdditionalDetails = isEnableAdditionalDetails;
	}

	public String getAdditionalDetailsTitle() {
		return additionalDetailsTitle;
	}

	public void setAdditionalDetailsTitle(String additionalDetailsTitle) {
		this.additionalDetailsTitle = additionalDetailsTitle;
	}

	protected JSONObject getJsonStatisticalMap(Map<Long, StatisticalUnit> statisticalMap, boolean onlyPercentage) throws JSONException {
		JSONObject jo = null;
		
		if(null != statisticalMap){
			jo = new JSONObject();
			for (Entry<Long, StatisticalUnit> entry : statisticalMap.entrySet()) {
				jo.put(entry.getKey().toString(), entry.getValue().toJson(onlyPercentage));
			}
		}
		return jo;
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
}
