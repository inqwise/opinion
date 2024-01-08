package com.inqwise.opinion.entities.controls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.common.ControlType;
import com.inqwise.opinion.common.IControl;
import com.inqwise.opinion.common.IControlRequest;
import com.inqwise.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

public class DateTimeControlEntity extends AdvancedControlEntity {
	
	private String yearTitle;
	private String monthTitle;
	private String dayTitle;
	private String hourTitle;
	private String minuteTitle;
	private String timezoneTitle;
	private JSONObject answerValue;
	
	public DateTimeControlEntity(ResultSet reader)
			throws Exception {
		super(reader, ControlType.Date);
		
	}

	public DateTimeControlEntity() {
	}

	@Override
	public void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		super.fill(reader, controlType);
		
		setYearTitle(ResultSetHelper.optString(reader, "year_title"));
		setMonthTitle(ResultSetHelper.optString(reader, "month_title"));
		setDayTitle(ResultSetHelper.optString(reader, "day_title"));
		setHourTitle(ResultSetHelper.optString(reader, "hour_title"));
		setMinuteTitle(ResultSetHelper.optString(reader, "minute_title"));
		setTimezoneTitle(ResultSetHelper.optString(reader, "timezone_title"));
		setAnswerValue(ResultSetHelper.optJsonObject(reader, "answer_value"));
	}
		
	@Override
	public BaseOperationResult importRequest(IControlRequest request) {
		
		BaseOperationResult result = validate(null != request.getContent(), ErrorCode.ArgumentNull, "argument 'content' is mandatory.");
		
		if(!result.hasError()){
			setYearTitle(request.getYearTitle());
			setMonthTitle(request.getMonthTitle());
			setDayTitle(request.getDayTitle());
			setHourTitle(request.getHourTitle());
			setMinuteTitle(request.getMinuteTitle());
			setTimezoneTitle(request.getTimezoneTitle());
			
			result = super.importRequest(request);
		}
		return result;
	}
	
	@Override
	public OperationResult<Long> copyTo(final Long opinionId, final Long parentId,
			final Integer parentTypeId, final Long translationId, final Integer orderId,
			final String content, final String actionGuid, final long userId, final long accountId) {
		final DateTimeControlEntity c = this; 
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
				return c.getOrderId();
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
				return null;
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
				return c.getComment();
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
				return c.getDayTitle();
			}

			@Override
			public String getHourTitle() {
				return c.getHourTitle();
			}

			@Override
			public String getMinuteTitle() {
				return c.getMinuteTitle();
			}

			@Override
			public String getMonthTitle() {
				return null;
			}

			@Override
			public String getTimezoneTitle() {
				return c.getTimezoneTitle();
			}

			@Override
			public String getYearTitle() {
				return c.getYearTitle();
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

	public void setYearTitle(String yearTitle) {
		this.yearTitle = yearTitle;
	}

	public String getYearTitle() {
		return yearTitle;
	}

	public void setMonthTitle(String monthTitle) {
		this.monthTitle = monthTitle;
	}

	public String getMonthTitle() {
		return monthTitle;
	}

	public void setDayTitle(String dayTitle) {
		this.dayTitle = dayTitle;
	}

	public String getDayTitle() {
		return dayTitle;
	}

	public void setHourTitle(String hourTitle) {
		this.hourTitle = hourTitle;
	}

	public String getHourTitle() {
		return hourTitle;
	}

	public void setMinuteTitle(String minuteTitle) {
		this.minuteTitle = minuteTitle;
	}

	public String getMinuteTitle() {
		return minuteTitle;
	}

	public void setTimezoneTitle(String timezoneTitle) {
		this.timezoneTitle = timezoneTitle;
	}

	public String getTimezoneTitle() {
		return timezoneTitle;
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonObject = super.toJson();
		jsonObject.put(IControl.JsonNames.YEAR_TITLE, getYearTitle())
		.put(IControl.JsonNames.MONTH_TITLE, getMonthTitle())
		.put(IControl.JsonNames.DAY_TITLE, getDayTitle())
		.put(IControl.JsonNames.HOURS_TITLE, getHourTitle())
		.put(IControl.JsonNames.MINUTES_TITLE, getMinuteTitle())
		.put(IControl.JsonNames.TIMEZONE_TITLE, getTimezoneTitle())
		.put(IControl.JsonNames.ANSWER_VALUE_KEY, getAnswerValue());
		return jsonObject;
	}
	
	@Override
	protected BaseOperationResult validateModifyRequest(
			IModifyControlDetailsRequest request) {

		//validate(null != request.getYearTitle(), ErrorCode.ArgumentNull, "`yearTitle` is mandatory");
		
		return super.validateModifyRequest(request);
	}
	
	public void setAnswerValue(JSONObject answerValue) {
		this.answerValue = answerValue;
	}

	public JSONObject getAnswerValue() {
		return answerValue;
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = super.getExportJson();
		output.put(IControl.JsonNames.YEAR_TITLE, getYearTitle());
		output.put(IControl.JsonNames.MONTH_TITLE, getMonthTitle());
		output.put(IControl.JsonNames.DAY_TITLE, getDayTitle());
		output.put(IControl.JsonNames.HOURS_TITLE, getHourTitle());
		output.put(IControl.JsonNames.MINUTES_TITLE, getMinuteTitle());
		output.put(IControl.JsonNames.TIMEZONE_TITLE, getTimezoneTitle());
		return output;
	}
}
