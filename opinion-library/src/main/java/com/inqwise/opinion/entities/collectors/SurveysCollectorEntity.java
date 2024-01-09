package com.inqwise.opinion.entities.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.actions.collectors.CollectorsActionsFactory;
import com.inqwise.opinion.actions.collectors.IModifySurveysCollectorRequest;
import com.inqwise.opinion.common.collectors.CollectorSourceType;
import com.inqwise.opinion.common.collectors.ISurveysCollector;
import com.inqwise.opinion.common.opinions.IOpinion;

public class SurveysCollectorEntity extends CollectorEntity implements ISurveysCollector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2989708466376403685L;
	
	/*
	public static OperationResult<ICollector> createCollector(ICreateCollectorRequest request, IAccount account){
		OperationResult<ICollector> result = null;
		
		// Validations
		//
		// Base validation + fill instance
		if(null == result){
			BaseOperationResult validateResult = validateRequest(request);
			if(validateResult.hasError()){
				result = validateResult.toErrorResult();
			}
		}
		
		// Get count of opinions
		AccountOpinionInfo accountOpinionInfo = null;
		if(null == result){
			try {
				OperationResult<AccountOpinionInfo> accountInfoResult = Accounts
						.getAccountShortStatistics(request.getAccountId(), null, null);
				if (accountInfoResult.hasError()) {
					result = accountInfoResult.toErrorResult();
				} else {
					accountOpinionInfo = accountInfoResult.getValue();
				}
			} catch (Exception e) {
				UUID errorId = logger.error(e,
						"getAccountShortStatistics(%s) : Unexpected error occured",
						request.getAccountId());
				result = new OperationResult<ICollector>(ErrorCode.GeneralError,
						errorId);
			}
		}
		
		// Get settings
		IServicePackageSettings settings = null;
		if (null == result) {
			OperationResult<IServicePackageSettings> settingsResult = ServicePackageSettingsEntity
					.getServicePackageSettings(account.getServicePackageId());
			if(settingsResult.hasError()){
				result = settingsResult.toErrorResult();
			} else {
				try {
					settings = settingsResult.getValue();
				} catch (Exception e) {
					UUID errorId = logger.error(e, "createCollector() : Failed to get  value from getServicePackageType");
					result = new OperationResult<ICollector>(ErrorCode.GeneralError, errorId);
				}
			}
		}
		
		// Check MaxCollectors limit
		if(null == result && null != settings.getMaxCollectors()){
			if(accountOpinionInfo.getCountOfCollectors() >= settings.getMaxCollectors()){
				logger.info("User reach his Max collectors limit. accountType: '%s', maxCollectors: '%s'", account.getServicePackageId(), settings.getMaxCollectors());
				result = new OperationResult<ICollector>(ErrorCode.MaxCollectorsReached);
			}
		}
		//
		// End Validations
		
		if(null == result){
			OperationResult<Long> setCollectorResult;
			try {
				setCollectorResult = Collectors.setCollector(request, account.getServicePackageId());
				if(setCollectorResult.hasError()){
					result = setCollectorResult.toErrorResult();
				} else {
					result = getCollectorById(setCollectorResult.getValue(), null, false);
				}
			
			} catch (Exception e) {
				UUID errorId = logger.error(e, "createCollector() : Unexpected error occured");
				result = new OperationResult<ICollector>(ErrorCode.GeneralError, errorId, e.getMessage());
			}
		}
		
		return result;
	}
	*/
	
	private Boolean enablePrevious;
	private String externalId;
	private String returnUrl;
	private String screenOutUrl;
	private String referer;
	private String opinionClosedUrl;
	private Date expirationDate;
	
	public SurveysCollectorEntity(ResultSet reader)
			throws SQLException {
		super(reader);
		setReturnUrl(ResultSetHelper.optString(reader, ResultSetNames.RETURN_URL));
		setScreenOutUrl(ResultSetHelper.optString(reader, ResultSetNames.SCREEN_OUT_URL));
		setReferer(ResultSetHelper.optString(reader, ResultSetNames.REFERER));
		setOpinionClosedUrl(ResultSetHelper.optString(reader, ResultSetNames.OPINION_CLOSED_URL));
		setExternalId(ResultSetHelper.optString(reader, ResultSetNames.COLLECTOR_EXTERNAL_ID));
		setEnablePrevious(ResultSetHelper.optBool(reader, ResultSetNames.IS_ENABLE_PREVIOUS));
		setExpirationDate(ResultSetHelper.optDate(reader, ResultSetNames.EXPIRATION_DATE));
	}
	
	public void setEnablePrevious(Boolean enablePrevious) {
		this.enablePrevious = enablePrevious;
	}
	
	public Boolean isEnablePrevious() {
		return enablePrevious;
	}
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	
	
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getScreenOutUrl() {
		return screenOutUrl;
	}
	public void setScreenOutUrl(String screenOutUrl) {
		this.screenOutUrl = screenOutUrl;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getOpinionClosedUrl() {
		return opinionClosedUrl;
	}
	public void setOpinionClosedUrl(String opinionClosedUrl) {
		this.opinionClosedUrl = opinionClosedUrl;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	@Override
	public JSONObject toJson(Integer timezoneOffset) throws JSONException {
		JSONObject jo = super.toJson(timezoneOffset);
		
		jo.put(JsonNames.IS_ENABLE_PREVIOUS, JSONHelper.getNullable(isEnablePrevious()))
		.put(JsonNames.IS_ENABLE_RSS_UPDATES, JSONHelper.getNullable(isEnableRssUpdates()))
		.put(JsonNames.COLLECTOR_SOURCE_ID, JSONHelper.getNullable(getCollectorSourceId()))
		.put(JsonNames.COLLECTOR_EXTERNAL_ID, getExternalId())
		.put(JsonNames.SOURCE_TYPE_ID, getCollectorSourceType().getValueOrNullWhenUndefined())
		.put(JsonNames.RETURN_URL, getReturnUrl())
		.put(JsonNames.SCREEN_OUT_URL, getScreenOutUrl())
		.put(JsonNames.REFERER, getReferer())
		.put(JsonNames.CLOSED_URL, getOpinionClosedUrl());
		jo.put(JsonNames.PASSWORD, JSONHelper.getNullable(getPassword()))
		.put(JsonNames.IS_HIDE_PASSWORD, JSONHelper.getNullable(isHidePassword()))
		.put(JsonNames.IS_PASSWORD_REQUIRED, null != getPassword())
		.put(JsonNames.END_DATE, JSONHelper.getDateFormat(DateConverter.addDateOffset(getEndDate(), timezoneOffset),"yyyy-MM-dd HH:mm"))
		.put(JsonNames.IS_ENABLE_RESPONSE_QUOTA, null != getResponseQuota())
		.put(JsonNames.RESPONSE_QUOTA, JSONHelper.getNullable(getResponseQuota()))
		.put(JsonNames.COLLECTOR_UUID, JSONHelper.getNullable(getUuid()))
		.put(JsonNames.IS_ALLOW_MULTIPLY_RESPONSES, JSONHelper.getNullable(isAllowMultiplyResponses()))
		.put(JsonNames.CLOSE_AFTER_CERTAIN_DATE, null != getEndDate());
		
		return jo;
	}
	
	private interface IModifyCollectorRequest extends IModifySurveysCollectorRequest, IModifySurveysCollectorRequest.IMessagesExtension, IModifySurveysCollectorRequest.ISecurityExtension,IModifySurveysCollectorRequest.ILimitExtension {}
	
	@Override
	protected BaseOperationResult modify(final JSONObject input,
			final long userId, final IAccount account) {
		final boolean closeAfterCertainDate = input
				.optBoolean(JsonNames.CLOSE_AFTER_CERTAIN_DATE);
		final boolean isPasswordRequired = input
				.optBoolean(JsonNames.IS_PASSWORD_REQUIRED);
		IModifyCollectorRequest request = new IModifyCollectorRequest() {
	
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
			public String getPassword() {
				return isPasswordRequired ? JSONHelper.optString(input,
						JsonNames.PASSWORD, null, "") : null;
			}
	
			@Override
			public String getName() {
				return JSONHelper.optString(input, JsonNames.NAME, null,
						"");
			}
	
			public boolean isEnableResponseQuota() {
				return JSONHelper.optBoolean(input,
						JsonNames.IS_ENABLE_RESPONSE_QUOTA, false);
			}
	
			@Override
			public Boolean isHidePassword() {
				return JSONHelper.optBoolean(input,
						JsonNames.IS_HIDE_PASSWORD);
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
			public Boolean isEnablePrevious() {
				return JSONHelper.optBoolean(input,
						JsonNames.IS_ENABLE_PREVIOUS);
			}
	
			@Override
			public Boolean isEnableRssUpdates() {
				return JSONHelper.optBoolean(input,
						JsonNames.IS_ENABLE_RSS_UPDATES);
			}
	
			@Override
			public long getUserId() {
				return userId;
			}
	
			@Override
			public String getCloseMessage() {
				return JSONHelper.optString(input, JsonNames.CLOSE_MESSAGE, null, "");
			}
		};
		
		return CollectorsActionsFactory.getInstance().modify(request);
	}
}
