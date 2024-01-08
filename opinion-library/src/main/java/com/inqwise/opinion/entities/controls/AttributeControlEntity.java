package com.inqwise.opinion.entities.controls;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.ControlType;
import com.inqwise.opinion.common.IControl;
import com.inqwise.opinion.common.IControlRequest;
import com.inqwise.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.common.ParentType;
import com.inqwise.opinion.common.IControl.JsonNames;
import com.inqwise.opinion.common.opinions.IOpinion;

public class AttributeControlEntity extends ControlEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5297211313501184915L;
	
	private String answerValue;
	
	public AttributeControlEntity(ResultSet reader) throws Exception {
		super(reader, ControlType.Attribute);
	}
	
	public AttributeControlEntity() {
	}

	@Override
	public OperationResult<Long> copyTo(final Long opinionId, final Long parentId,
			Integer parentTypeId, Long translationId, final Integer orderId,
			final String content, final String actionGuid, final long userId, final long accountId) {
		final AttributeControlEntity c = this;
		IControlRequest request = new IControlRequest() {
			
			@Override
			public Long getTranslationId() {
				return IOpinion.DEFAULT_TRANSLATION_ID;
			}
			
			@Override
			public Long getParentId() {
				return null == parentId ? c.getParentId() : parentId;
			}
			
			@Override
			public Integer getOrderId() {
				return null == orderId ? c.getOrderId() : orderId;
			}
			
			@Override
			public Long getOpinionId() {
				return null == opinionId ? c.getOpinionId() : opinionId;
			}
			
			@Override
			public String getNote() {
				return null;
			}
			
			@Override
			public Boolean getIsMandatory() {
				return c.getIsMandatory();
			}
			
			@Override
			public Integer getInputTypeId() {
				return null;
			}
			
			@Override
			public Integer getInputSizeTypeId() {
				return null;
			}
			
			@Override
			public Integer getParentTypeId() {
				return c.getParentType().getValue();
			}
			
			@Override
			public Integer getControlTypeId() {
				return c.getControlType().getValue();
			}
			
			@Override
			public String getContent() {
				return null;
			}
			
			@Override
			public String getComment() {
				return null;
			}
			
			@Override
			public Integer getArrangeId() {
				return null;
			}
			
			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public String getDayTitle() {
				return null;
			}

			@Override
			public String getHourTitle() {
				return null;
			}

			@Override
			public String getMinuteTitle() {
				return null;
			}

			@Override
			public String getMonthTitle() {
				return null;
			}

			@Override
			public String getTimezoneTitle() {
				return null;
			}

			@Override
			public String getYearTitle() {
				return null;
			}

			@Override
			public Integer getFromScale() {
				return null;
			}

			@Override
			public String getFromScaleTitle() {
				return null;
			}

			@Override
			public Integer getToScale() {
				return null;
			}

			@Override
			public String getToScaleTitle() {
				return null;
			}

			@Override
			public String getLink() {
				return null;
			}

			@Override
			public Integer getLinkTypeId() {
				return null;
			}

			@Override
			public String getActionGuid() {
				return actionGuid;
			}

			@Override
			public long getUserId() {
				return userId;
			}
			
			@Override
			public boolean isHidden() {
				return c.isHidden();
			}

			@Override
			public String getKey() {
				return c.getKey();
			}

			@Override
			public boolean isNumerable() {
				return false;
			}
		};
		return createControl(request);
	}
	
	protected Boolean getIsMandatory() {
		return null;
	}

	@Override
	protected BaseOperationResult validateModifyRequest(
			IModifyControlDetailsRequest request) {
		
		validate(null != request.getAccountId(), ErrorCode.ArgumentNull, "`accountId` is manatory");
		validate(null != request.getControlId(), ErrorCode.ArgumentNull, "`controlId` is mandatory");
		return this;
	}
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		
		setAnswerValue(ResultSetHelper.optString(reader, "answer_value"));
		super.fill(reader, controlType);
	}
	
	@Override
	public JSONObject toJson(boolean withAnswer) throws JSONException {
		JSONObject jo = super.toJson(withAnswer);
		if(withAnswer){
			jo.put(JsonNames.ANSWER_VALUE_KEY, getAnswerValue());
		}
		return jo;
	}

	public BaseOperationResult importRequest(IControlRequest request) {
		
		BaseOperationResult result = BaseOperationResult.Ok;
		
		// Validate
		//
		
		if(!result.hasError()){
			// parentId
			result = validate(null != request.getParentId(), ErrorCode.ArgumentNull, "argument 'parentId' is mandatory.");
			if(!result.hasError()){
				setParentId(request.getParentId());
			}
		}
		
		if(!result.hasError()){
			// parentTypeId
			result = validate(null != request.getParentTypeId(), ErrorCode.ArgumentNull, "argument 'parentTypeId' is mandatory.");
			if(!result.hasError()){
				setParentType(ParentType.fromInt(request.getParentTypeId()));
			}
		}
		
		if(!result.hasError()){
			// opinionId
			result = validate(null != request.getOpinionId(), ErrorCode.ArgumentNull, "argument 'opinionId' is mandatory.");
			if(!result.hasError()){
				setOpinionId(request.getOpinionId());
			}
		}
		
		if(!result.hasError()){
			// controlType
			result = validate(null != request.getControlTypeId(), ErrorCode.ArgumentNull, "argument 'controlType' is mandatory.");
			if(!result.hasError()){
				setControlType(ControlType.fromInt(request.getControlTypeId()));
			}
		}
		
		if(!result.hasError()){
			setTranslationId(request.getTranslationId());
			setHidden(request.isHidden());
		}
		
		
		
		return result;
	}
	
	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerValue() {
		return answerValue;
	}
}
