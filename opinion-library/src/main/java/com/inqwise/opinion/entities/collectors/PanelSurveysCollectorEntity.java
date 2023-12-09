package com.inqwise.opinion.opinion.entities.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.opinion.actions.collectors.CollectorsActionsFactory;
import com.inqwise.opinion.opinion.actions.collectors.IModifySurveysCollectorRequest;
import com.inqwise.opinion.opinion.common.collectors.IPanelSurveysCollector;
import com.inqwise.opinion.opinion.common.collectors.ICollector.JsonNames;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;

public class PanelSurveysCollectorEntity extends SurveysCollectorEntity implements IPanelSurveysCollector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 623660807451424671L;
	
	public PanelSurveysCollectorEntity(ResultSet reader) throws SQLException {
		super(reader);		
	}
	
	private interface IModifyCollectorRequest extends IModifySurveysCollectorRequest, IModifySurveysCollectorRequest.IMessagesExtension {}
	
	@Override
	public BaseOperationResult modify(final JSONObject input, final long userId, final long accountId) {
		
		IModifyCollectorRequest request = new IModifyCollectorRequest() {
	
			@Override
			public Long getTranslationId() {
				return JSONHelper.optLong(input,
						JsonNames.TRANSLATION_ID, IOpinion.DEFAULT_TRANSLATION_ID);
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
			public Long getCollectorId() {
				return JSONHelper.optLong(input, JsonNames.COLLECTOR_ID);
			}
	
			@Override
			public Long getAccountId() {
				return accountId;
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
	
	@Override
	public JSONObject toJson(Integer timezoneOffset) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put(JsonNames.COLLECTOR_ID, JSONHelper.getNullable(getId()))
		.put(JsonNames.NAME, JSONHelper.getNullable(getName()))
		.put(JsonNames.OPINION_ID, JSONHelper.getNullable(getOpinionId()))
		.put(JsonNames.TRANSLATION_ID, JSONHelper.getNullable(getTranslationId()))
		.put(JsonNames.IS_ENABLE_FINISHED_SESSION_EMAIL_NOTIFICATION, JSONHelper.getNullable(isEnableFinishedSessionEmailNotification()))
		.put(JsonNames.STATUS_ID, getCollectorStatus().getValue())
		.put(JsonNames.COUNT_OF_COMPLETED, getCountOfFinishedOpinions())
		.put(JsonNames.COLLECT_URL, getCollectUrl())
		.put(JsonNames.COLLECTOR_SOURCE_ID, JSONHelper.getNullable(getCollectorSourceId()))
		.put(JsonNames.COLLECTOR_EXTERNAL_ID, getExternalId())
		.put(JsonNames.SOURCE_TYPE_ID, getCollectorSourceType().getValueOrNullWhenUndefined())
		.put(JsonNames.RETURN_URL, getReturnUrl())
		.put(JsonNames.SCREEN_OUT_URL, getScreenOutUrl())
		.put(JsonNames.REFERER, getReferer())
		.put(JsonNames.CLOSED_URL, getOpinionClosedUrl())
		.put(JsonNames.IS_ENABLE_PREVIOUS, isEnablePrevious())
		.put(JsonNames.IS_ALLOW_MULTIPLY_RESPONSES, JSONHelper.getNullable(isAllowMultiplyResponses()));
		
		return jo;
	}
	
	@Override
	public Long getResponseQuota() {
		return super.getResponseQuota();
	}
}
