package com.inqwise.opinion.entities.controls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.common.ControlType;
import com.inqwise.opinion.common.IControlRequest;
import com.inqwise.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.common.InputSizeType;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

public class FreeTextControlEntity extends AdvancedControlEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5205131262321525785L;
	private InputSizeType inputSizeType;
	private String answerValue; 
	
	public FreeTextControlEntity(ResultSet reader) throws Exception {
		super(reader, ControlType.FreeText);
	}

	public FreeTextControlEntity() {
	}
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		setInputSizeType(InputSizeType.fromInt(ResultSetHelper.opt(reader, "input_size_type_id", int.class)));
		setAnswerValue(ResultSetHelper.optString(reader, "answer_value"));
		super.fill(reader, controlType);
	}

	public void setInputSizeType(InputSizeType inputSizeType) {
		this.inputSizeType = inputSizeType;
	}

	public InputSizeType getInputSizeType() {
		return inputSizeType;
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jo = super.toJson();
		if(null != getInputSizeType()){
			jo.put(JsonNames.INPUT_SIZE_TYPE_ID, getInputSizeType().getValueOrNullWhenUndefined());
		}
		return jo;
	}
	
	@Override
	protected BaseOperationResult validateModifyRequest(
			IModifyControlDetailsRequest request) {
		validate(null != request.getInputSizeTypeId(), ErrorCode.ArgumentNull, "`inputSizeTypeId` is mandatory");
		
		return hasError() ? this : super.validateModifyRequest(request);
	}
	
	@Override
	public BaseOperationResult importRequest(IControlRequest request) {
		
		BaseOperationResult result = BaseOperationResult.Ok;
		
		// Validate
		//
		// input size
		result = validate(null != request.getInputSizeTypeId(), ErrorCode.ArgumentNull, "argument 'inputSizeTypeId' is mandatory.");
		result = validate(null != request.getContent(), ErrorCode.ArgumentNull, "argument 'content' is mandatory.");
		
		if(!result.hasError()){
			setInputSizeType(InputSizeType.fromInt(request.getInputSizeTypeId()));
		}
		
		if(!result.hasError()){
			result = super.importRequest(request);
		}
		
		return result;
	}

	@Override
	public OperationResult<Long> copyTo(final Long opinionId, final Long parentId,
											final Integer parentTypeId, final Long translationId,
											final Integer orderId, final String content,
											final String actionGuid, final long userId, final long accountId) {

		final FreeTextControlEntity c = this;
		IControlRequest request = new IControlRequest() {
			
			@Override
			public Long getTranslationId() {
				return null == translationId ? c.getTranslationId() : translationId;
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
				return c.getNote();
			}
			
			@Override
			public Boolean getIsMandatory() {
				return c.getIsMandatory();
			}
			
			@Override
			public Integer getInputTypeId() {
				return c.getInputTypeId();
			}
			
			@Override
			public Integer getInputSizeTypeId() {
				return c.getInputSizeType().getValue();
			}
			
			@Override
			public Integer getParentTypeId() {
				return null == parentTypeId ? c.getParentType().getValue() : parentTypeId;
			}
			
			@Override
			public Integer getControlTypeId() {
				return c.getControlType().getValue();
			}
			
			@Override
			public String getContent() {
				return null == content ? c.getContent() : content;
			}
			
			@Override
			public String getComment() {
				return null;
			}
			
			@Override
			public Integer getArrangeId() {
				return c.getArrange().getValue();
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
				return c.getLink();
			}

			@Override
			public Integer getLinkTypeId() {
				return c.getLinkType().getValueOrNullWhenUndefined();
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
				return true;
			}
		};
		return createControl(request);
	}

	@Override
	public JSONObject toJson(boolean withAnswer) throws JSONException {
		JSONObject jo = super.toJson(withAnswer);
		if(withAnswer){
			jo.put(JsonNames.ANSWER_VALUE_KEY, getAnswerValue());
		}
		return jo;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerValue() {
		return answerValue;
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = super.getExportJson();
		
		if(null != getInputSizeType()){
			output.put(JsonNames.INPUT_SIZE_TYPE_ID, getInputSizeType().getValueOrNullWhenUndefined());
		}
		
		return output;
	}
}
