/**
 * 
 */
package com.inqwise.opinion.opinion.entities;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.entities.BaseEntity;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.ICreateResult;
import com.inqwise.opinion.opinion.common.IOption;
import com.inqwise.opinion.opinion.common.IOptionRequest;
import com.inqwise.opinion.opinion.common.LinkType;
import com.inqwise.opinion.opinion.common.OptionKind;
import com.inqwise.opinion.opinion.common.IControl.JsonNames;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.dao.Options;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

/**
 * @author Alex
 *
 */
public class OptionEntity extends BaseEntity implements IOption {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3653670533504690678L;

	public static ApplicationLog logger = ApplicationLog.getLogger(OptionEntity.class);
	
	private Long id;
	private String text;
	private String value;
	private Long translationId;
	private Long controlId;
	private Integer orderId;
	private Boolean isEnableAdditionalDetails;
	private String additionalDetailsTitle;
	private Boolean isSelected;
	private String answerComment;
	private List<Long> answerSelectedControlIds; /*for matrix*/
	private OptionKind optionKind;
	private String answerValue;
	private Integer answerSelectTypeId;
	private String  link;
	private LinkType linkType;
	
	public OptionEntity(ResultSet reader) throws Exception {
		setId(ResultSetHelper.optLong(reader, "option_id"));
		setText(ResultSetHelper.opt(reader, "option_text", String.class));
		setValue(ResultSetHelper.opt(reader, "option_value", String.class));
		setTranslationId(ResultSetHelper.optLong(reader, "translation_id"));
		setControlId(ResultSetHelper.optLong(reader, "control_id"));
		setOrderId(ResultSetHelper.opt(reader, "order_id", Integer.class));
		setIsEnableAdditionalDetails(ResultSetHelper.opt(reader, "is_enable_additional_info", Boolean.class));
		setAdditionalDetailsTitle(ResultSetHelper.optString(reader, "additional_details_title"));
		setIsSelected(ResultSetHelper.optBool(reader, "answer_is_selected"));
		setAnswerSelectTypeId(ResultSetHelper.optInt(reader, "answer_select_type_id"));
		setAnswerComment(ResultSetHelper.optString(reader, "answer_comment"));
		String strIds = ResultSetHelper.optString(reader, "answer_selected_control_ids");
		if(null != strIds && getIsSelected()){
			answerSelectedControlIds = new ArrayList<Long>();
			for(String strId : StringUtils.split(strIds, ',')){
				answerSelectedControlIds.add(Long.valueOf(strId));
			}
			setAnswerValue(ResultSetHelper.optString(reader, "answer_value"));
		}
		setOptionKind(OptionKind.fromInt(ResultSetHelper.optInt(reader, "option_kind_id", OptionKind.Simple.getValue())));
		setLink(ResultSetHelper.optString(reader, "link"));
		setLinkType(LinkType.fromInt(ResultSetHelper.optInt(reader, "link_type_id", 0)));
	}

	public OptionEntity() {
		
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setTranslationId(Long translationId) {
		this.translationId = translationId;
	}

	public Long getTranslationId() {
		return translationId;
	}

	public void setControlId(Long controlId) {
		this.controlId = controlId;
	}

	public Long getControlId() {
		return controlId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public BaseOperationResult delete(Long accountId, long userId) {
		return delete(getId(), accountId, userId);
	}
	
	public static BaseOperationResult delete(Long optionId, Long accountId, long userId) {
		
		BaseOperationResult result;
		try {
			result = Options.delete(optionId, accountId, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "delete() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
		
		return result;
	}
	
	public static BaseOperationResult createOptions(List<IOptionRequest> requests){
		BaseOperationResult results = null;
		List<OperationResult<IOption>> listOfOptions = new ArrayList<OperationResult<IOption>>();
		for(IOptionRequest request : requests){
			OperationResult<IOption> result = create(request);
			listOfOptions.add(result);
		}
		return results;
	}

	public static OperationResult<IOption> create(IOptionRequest request) {
		OperationResult<IOption> result = null;
		try {
			OptionEntity option = new OptionEntity();
			BaseOperationResult importResult = option.importRequest(request);
			
			if(importResult.hasError()){
				result = importResult.toErrorResult();
			}
			
			if(null == result){
				OperationResult<ICreateResult> setOptionResult = Options.setOption(request);
				if(setOptionResult.hasError()){
					result = setOptionResult.toErrorResult();
				} else {
					option.setId(setOptionResult.getValue().getId());
					option.setOrderId(setOptionResult.getValue().getOrderId());
					result = new OperationResult<IOption>(option);
				}
			}
		} catch (Exception e) {
			UUID errorId = logger.error(e, "Failed  to setOption. Request details: '%s'", request);
			result = new OperationResult<IOption>(ErrorCode.GeneralError, errorId, e.toString());
		}
		return result;
	}

	public BaseOperationResult importRequest(IOptionRequest request) {
				
		BaseOperationResult result = BaseOperationResult.Ok;
		
		// Validate
		//
		// accountId
		result = validate(null != request.getAccountId(), ErrorCode.ArgumentNull, "argument 'accountId' is mandatory.");
		
		if(!result.hasError()){
			// opinionId
			result = validate(null != request.getOpinionId(), ErrorCode.ArgumentNull, "argument 'opinionId' is mandatory.");
		}
		
		if(!result.hasError()){
			// controlId
			result = validate(null != request.getControlId(), ErrorCode.ArgumentNull, "argument 'controlId' is mandatory.");
			if(!result.hasError()){
				setControlId(request.getControlId());
			}
		}
		
		if(!result.hasError()){
			// optionKind
			result = validate(null != request.getOptionKindId(), ErrorCode.ArgumentNull, "argument 'optionKindId' is mandatory.");
			if(!result.hasError()){
				setOptionKind(OptionKind.fromInt(request.getOptionKindId()));
			}
		}
		
		if(!result.hasError() && getOptionKind() != OptionKind.DefaultUnselectable){
			// value
			result = validate(null != request.getValue(), ErrorCode.ArgumentNull, "argument 'value' is mandatory.");
			if(!result.hasError()){
				setValue(request.getValue());
			}
		}
		
		if(!result.hasError()){
			setText(request.getText());
			setIsEnableAdditionalDetails(request.getIsEnableAdditionalDetails());
			setTranslationId(request.getTranslationId());
			setAdditionalDetailsTitle(request.getAdditionalDetailsTitle());
		}
		
		return result;
	}
	
	public static BaseOperationResult update(Long optionId, String text, String value, Long translationId, Long accountId,
											Boolean isEnableAdditionalDetails, String additionalDetailsTitle, long userId,
											String link, Integer linkTypeId) {
		
		BaseOperationResult result;
		try {
			result = Options.updateOption(optionId, value, text, translationId, accountId,
											isEnableAdditionalDetails, additionalDetailsTitle, userId, link, linkTypeId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "update() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
		
		return result;
	}

	public static BaseOperationResult order(String optionIds, Long accountId, long userId) {
		
		BaseOperationResult result;
		try {
			result = Options.orderOptions(optionIds, accountId, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "order() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
		
		return result;
	}

	public JSONObject toJson() throws JSONException {
		JSONObject jo = new JSONObject()
			.put(JsonNames.OPTION_ID, getId())
			.put(JsonNames.TEXT, getText())
			.put(JsonNames.VALUE, getValue());
			if(null != getTranslationId() && getTranslationId() != IOpinion.DEFAULT_TRANSLATION_ID){
				jo.put(JsonNames.TRANSLATION_ID, getTranslationId());
			}
			jo.put(JsonNames.ADDITIONAL_DETAILS_TITLE, getAdditionalDetailsTitle())
			.put(JsonNames.IS_ENABLE_ADDITIONAL_DETAILS, getIsEnableAdditionalDetails())
			.put(JsonNames.ANSWER_COMMENT, getAnswerComment())
			.put(JsonNames.ANSWER_VALUE, getAnswerValue())
			.put(JsonNames.OPTION_KIND_ID, getOptionKind().getValue())
			.put(JsonNames.LINK, getLink())
			.put(JsonNames.LINK_TYPE_ID, getLinkType().getValueOrNullWhenUndefined());
		
		if(null != getIsSelected()){
			jo.put(JsonNames.SELECT_TYPE_ID, getIsSelected() ? 1 : 0);
		}
		
		if(null != getAnswerSelectedControlIds()){
			jo.put(JsonNames.ANSWER_SELECTED_CONTROLS_IDS, getAnswerSelectedControlIds());
		}
		
		return jo;
	}

	public void setIsEnableAdditionalDetails(Boolean isEnableAdditionalDetails) {
		this.isEnableAdditionalDetails = isEnableAdditionalDetails;
	}

	public Boolean getIsEnableAdditionalDetails() {
		return isEnableAdditionalDetails;
	}

	public void setAdditionalDetailsTitle(String additionalDetailsTitle) {
		this.additionalDetailsTitle = additionalDetailsTitle;
	}

	public String getAdditionalDetailsTitle() {
		return additionalDetailsTitle;
	}
	
	public OperationResult<IOption> copyTo(final Long controlId, final Long translationId,
											final Integer orderId, final Long accountId,
											final Long opinionId, final long userId){
		
		final OptionEntity o = this;
		IOptionRequest request =  new IOptionRequest() {
			
			@Override
			public String getValue() {
				return o.getValue();
			}
			
			@Override
			public Long getTranslationId() {
				return null == translationId ? o.getTranslationId() : translationId;
			}
			
			@Override
			public String getText() {
				return o.getText();
			}
			
			@Override
			public Integer getOrderId() {
				return null == orderId ? o.getOrderId() : orderId;
			}
			
			@Override
			public Boolean getIsEnableAdditionalDetails() {
				return o.getIsEnableAdditionalDetails();
			}
			
			@Override
			public Long getControlId() {
				return null == controlId ? o.getControlId() : controlId;
			}
			
			@Override
			public String getAdditionalDetailsTitle() {
				return o.getAdditionalDetailsTitle();
			}
			
			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public Integer getOptionKindId() {
				return o.getOptionKind().getValue();
			}

			@Override
			public Long getOpinionId() {
				return opinionId;
			}

			@Override
			public long getUserId() {
				return userId;
			}

			@Override
			public String getLink() {
				return o.getLink();
			}

			@Override
			public Integer getLinkTypeId() {
				return o.getLinkType().getValue();
			}
		};
		
		return create(request);
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setAnswerComment(String answerComment) {
		this.answerComment = answerComment;
	}

	public String getAnswerComment() {
		return answerComment;
	}
	
	public List<Long> getAnswerSelectedControlIds(){
		return answerSelectedControlIds;
	}

	private void setOptionKind(OptionKind optionKind) {
		this.optionKind = optionKind;
	}

	public OptionKind getOptionKind() {
		return optionKind;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerSelectTypeId(Integer answerSelectTypeId) {
		this.answerSelectTypeId = answerSelectTypeId;
	}

	public Integer getAnswerSelectTypeId() {
		return answerSelectTypeId;
	}

	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = new JSONObject();
		
		output.put(JsonNames.TEXT, getText());
		output.put(JsonNames.VALUE, getValue());
		output.put(JsonNames.ADDITIONAL_DETAILS_TITLE, getAdditionalDetailsTitle());
		output.put(JsonNames.IS_ENABLE_ADDITIONAL_DETAILS, getIsEnableAdditionalDetails());
		output.put(JsonNames.OPTION_KIND_ID, getOptionKind().getValue());
		output.put(JsonNames.LINK, getLink());
		output.put(JsonNames.LINK_TYPE_ID, getLinkType().getValueOrNullWhenUndefined());
		
		return output;
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
