package com.inqwise.opinion.opinion.entities.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.opinion.actions.collectors.CollectorsActionsFactory;
import com.inqwise.opinion.opinion.actions.collectors.IModifyPollsCollectorRequest;
import com.inqwise.opinion.opinion.actions.collectors.IModifySurveysCollectorRequest;
import com.inqwise.opinion.opinion.common.ResultsPermissionType;
import com.inqwise.opinion.opinion.common.collectors.IPollsCollector;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;

public class PollsCollectorEntity extends CollectorEntity implements
		IPollsCollector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7061562850778125967L;

	private ResultsPermissionType resultsType;
	
	public PollsCollectorEntity(ResultSet reader) throws SQLException {
		super(reader);
		
		setResultsType(ResultsPermissionType.fromInt(reader.getInt(ResultSetNames.RESULTS_TYPE_ID)));
	}
	
	private interface IModifyRequest extends IModifyPollsCollectorRequest, IModifyPollsCollectorRequest.ILimitExtension, IModifyPollsCollectorRequest.ISecurityExtension, IModifyPollsCollectorRequest.IMessagesExtension{}
	
	@Override
	protected BaseOperationResult modify(final JSONObject input,
			final long userId, final IAccount account) {
		final boolean closeAfterCertainDate = input
				.optBoolean(JsonNames.CLOSE_AFTER_CERTAIN_DATE);
		final boolean isPasswordRequired = input
				.optBoolean(JsonNames.IS_PASSWORD_REQUIRED);
		
		IModifyRequest request = new IModifyRequest() {
	
			@Override
			public Long getTranslationId() {
				return JSONHelper.optLong(input,
						JsonNames.TRANSLATION_ID, IOpinion.DEFAULT_TRANSLATION_ID);
			}
	
			@Override
			public Long getResponseQuota() {
				return (isEnableResponseQuota() ? JSONHelper
						.optLong(input, JsonNames.RESPONSE_QUOTA) : null);
			}
	
			@Override
			public String getName() {
				return JSONHelper.optString(input, JsonNames.NAME, null,
						"");
			}
	
			@Override
			public Boolean getIsAllowMultiplyResponses() {
				return JSONHelper.optBoolean(input,
						JsonNames.IS_ALLOW_MULTIPLY_RESPONSES);
			}
	
			@Override
			public Date getEndDate() {
				return closeAfterCertainDate ? account.removeDateOffset(JSONHelper.optDate(input,
						JsonNames.END_DATE)) : null;
			}
	
			@Override
			public Long getCollectorId() {
				return JSONHelper.optLong(input, JsonNames.COLLECTOR_ID);
			}
	
			@Override
			public Long getAccountId() {
				return account.getId();
			}
	
			@Override
			public Boolean isEnableFinishedSessionEmailNotification() {
				return JSONHelper
						.optBoolean(
								input,
								JsonNames.IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION);
			}
	
			@Override
			public long getUserId() {
				return userId;
			}

			public boolean isEnableResponseQuota() {
				return JSONHelper.optBoolean(input,
						JsonNames.IS_ENABLE_RESPONSE_QUOTA, false);
			}
			
			@Override
			public String getPassword() {
				return isPasswordRequired ? JSONHelper.optString(input,
						JsonNames.PASSWORD, null, "") : null;
			}
	
			@Override
			public Boolean isHidePassword() {
				return JSONHelper.optBoolean(input,
						JsonNames.IS_HIDE_PASSWORD);
			}
			
			@Override
			public String getCloseMessage() {
				return JSONHelper.optString(input, JsonNames.CLOSE_MESSAGE, null, "");
			}

			@Override
			public Integer getResultsTypeId() {
				return JSONHelper.optInt(input, JsonNames.RESULTS_TYPE_ID);
			}
		};
		
		return CollectorsActionsFactory.getInstance().modify(request);
	}

	@Override
	public JSONObject toJson(Integer timezoneOffset) throws JSONException {
		JSONObject jo = super.toJson(timezoneOffset);
		
		jo.put(JsonNames.PASSWORD, JSONHelper.getNullable(getPassword()))
		.put(JsonNames.IS_HIDE_PASSWORD, JSONHelper.getNullable(isHidePassword()))
		.put(JsonNames.IS_PASSWORD_REQUIRED, null != getPassword())
		.put(JsonNames.END_DATE, JSONHelper.getDateFormat(DateConverter.addDateOffset(getEndDate(), timezoneOffset),"yyyy-MM-dd HH:mm"))
		.put(JsonNames.IS_ENABLE_RESPONSE_QUOTA, null != getResponseQuota())
		.put(JsonNames.RESPONSE_QUOTA, JSONHelper.getNullable(getResponseQuota()))
		.put(JsonNames.COLLECTOR_UUID, JSONHelper.getNullable(getUuid()))
		.put(JsonNames.IS_ALLOW_MULTIPLY_RESPONSES, JSONHelper.getNullable(isAllowMultiplyResponses()))
		.put(JsonNames.CLOSE_AFTER_CERTAIN_DATE, null != getEndDate())
		.put(JsonNames.RESULTS_TYPE_ID, getResultsType().getValue());
		;
		
		return jo;
	}

	public ResultsPermissionType getResultsType() {
		return resultsType;
	}

	public void setResultsType(ResultsPermissionType resultsType) {
		this.resultsType = resultsType;
	}
}
