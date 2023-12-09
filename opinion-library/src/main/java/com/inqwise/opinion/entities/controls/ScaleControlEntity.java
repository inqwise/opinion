package com.inqwise.opinion.opinion.entities.controls;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.ControlType;
import com.inqwise.opinion.opinion.common.IControlRequest;
import com.inqwise.opinion.opinion.common.IModifyControlDetailsRequest;

public class ScaleControlEntity extends AdvancedControlEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8512017537587447062L;
	private Integer from;
	private Integer to;
	private String fromTitle;
	private String toTitle;
	private String answerValue;
	
	public ScaleControlEntity(ResultSet reader, ControlType controlType)
			throws Exception {
		super(reader, ControlType.Scale);
	}

	public ScaleControlEntity() {
	}

	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		
		setFrom(ResultSetHelper.optInt(reader, "from_scale"));
		setTo(ResultSetHelper.optInt(reader, "to_scale"));
		setFromTitle(ResultSetHelper.optString(reader, "from_scale_title"));
		setToTitle(ResultSetHelper.optString(reader, "to_scale_title"));
		setAnswerValue(ResultSetHelper.optString(reader, "answer_value"));
		super.fill(reader, controlType);
	}
	
	@Override
	protected BaseOperationResult validateModifyRequest(
			IModifyControlDetailsRequest request) {
		validate(null != request.getFromScale(), ErrorCode.ArgumentNull, "`fromScale` is mandatory");
		validate(null != request.getToScale(), ErrorCode.ArgumentNull, "`toScale` is mandatory");
		return hasError() ? this : super.validateModifyRequest(request);
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jo = super.toJson();
		jo.put(JsonNames.FROM_SCALE, getFrom());
		jo.put(JsonNames.TO_SCALE, getTo());
		jo.put(JsonNames.FROM_TITLE, getFromTitle());
		jo.put(JsonNames.TO_TITLE, getToTitle());
		jo.put(JsonNames.ANSWER_VALUE_KEY, getAnswerValue());
		return jo;
	}
	
	@Override
	public OperationResult<Long> copyTo(final Long opinionId, final Long parentId,
			final Integer parentTypeId, final Long translationId, final Integer orderId,
			String content, final String actionGuid, final long userId, final long accountId) {
		
		final ScaleControlEntity c = this;
		
		IControlRequest request = new IControlRequest() {
			
			@Override
			public String getYearTitle() {
				return null;
			}
			
			@Override
			public Long getTranslationId() {
				return null == translationId ? c.getTransactionId() : translationId;
			}
			
			@Override
			public String getToScaleTitle() {
				return c.getToTitle();
			}
			
			@Override
			public Integer getToScale() {
				return c.getTo();
			}
			
			@Override
			public String getTimezoneTitle() {
				return null;
			}
			
			@Override
			public Integer getParentTypeId() {
				return null == parentTypeId ? c.getParentType().getValue() : parentTypeId;
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
			public String getMonthTitle() {
				return null;
			}
			
			@Override
			public String getMinuteTitle() {
				return null;
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
				return null;
			}
			
			@Override
			public String getHourTitle() {
				return null;
			}
			
			@Override
			public String getFromScaleTitle() {
				return c.getFromTitle();
			}
			
			@Override
			public Integer getFromScale() {
				return c.getFrom();
			}
			
			@Override
			public String getDayTitle() {
				return null;
			}
			
			@Override
			public Integer getControlTypeId() {
				return c.getControlType().getValue();
			}
			
			@Override
			public String getContent() {
				return c.getContent();
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
	public BaseOperationResult importRequest(IControlRequest request) {
		this.setFrom(request.getFromScale());
		this.setFromTitle(request.getFromScaleTitle());
		this.setTo(request.getToScale());
		this.setToTitle(request.getToScaleTitle());
		BaseOperationResult result = super.importRequest(request);
				
		return result;
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = super.getExportJson();
		output.put(JsonNames.FROM_SCALE, getFrom());
		output.put(JsonNames.TO_SCALE, getTo());
		output.put(JsonNames.FROM_TITLE, getFromTitle());
		output.put(JsonNames.TO_TITLE, getToTitle());
		
		return output;
	}
}
