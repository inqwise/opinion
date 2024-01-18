package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.common.AccountOpinionInfo;
import com.inqwise.opinion.common.IOpinionAccount;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.charts.ActivityChartDataItem;
import com.inqwise.opinion.common.charts.TimePointRange;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.IOpinion.JsonNames;
import com.inqwise.opinion.common.opinions.OpinionModel;
import com.inqwise.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.common.opinions.OpinionsOrderBy;
import com.inqwise.opinion.entities.OpinionEntity;
import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.managers.AccountsManager;
import com.inqwise.opinion.managers.ChartsManager;
import com.inqwise.opinion.managers.CollectorsManager;
import com.inqwise.opinion.managers.OpinionsManager;

public class OpinionsEntry extends Entry implements IPostmasterObject {

	static ApplicationLog logger = ApplicationLog.getLogger(OpinionsEntry.class);
	
	protected static final String DASHBOARD_PAGE_NAME = "/dashboard";
	public final static int DAYS_BACK_DEFAULT_VALUE = 30;
	
	public OpinionsEntry(IPostmasterContext context){
		super(context);
	}
	
	public JSONObject getList(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {

		Integer opinionTypeId = JSONHelper.optInt(input, IOpinion.JsonNames.OPINION_TYPE_ID);
		return getMany(input, opinionTypeId);
	}

	protected JSONObject getMany(JSONObject input, Integer opinionTypeId)
			throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = null;
		IOperationResult result = null;
		IAccount account = null;
		OpinionsOrderBy orderBy = null;
		
		int top = JSONHelper.optInt(input, "top", 100);
		Date from = JSONHelper.optDate(input, "from");
		Date to = JSONHelper.optDate(input, "to");
		
		result = validateSignIn();
		
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result) {
			if(input.optBoolean("orderByRecent", false)){
				orderBy = OpinionsOrderBy.ModifyDate;
			}
		
			List<OpinionModel> list = OpinionsManager.getOpinions(account.getId(), top, from, to, opinionTypeId, IOpinion.DEFAULT_TRANSLATION_ID, orderBy );
			
			Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			JSONArray ja = new JSONArray();
			for(var opinionModel : list){
				JSONObject item = new JSONObject();
				
				item.put(IOpinion.JsonNames.OPINION_ID, opinionModel.getId());
				if(null == opinionTypeId){
					item.put(IOpinion.JsonNames.OPINION_TYPE_ID, opinionModel.getType());
				}
				item.put(IOpinion.JsonNames.NAME, opinionModel.getName());
				item.put(IOpinion.JsonNames.MODIFY_DATE, formatter.format(opinionModel.getModifyDate()));
				long cntStarted = opinionModel.getCntStartedOpinions();
				long cntCompleted = opinionModel.getCntFinishedOpinions();
				item.put(IOpinion.JsonNames.CNT_STARTED_OPINIONS, cntStarted);
				item.put(IOpinion.JsonNames.CNT_FINISHED_OPINIONS, cntCompleted);
				item.put(IOpinion.JsonNames.CNT_PARTIAL_OPINIONS, cntStarted - cntCompleted);
				item.put(IOpinion.JsonNames.COMPLETION_RATE, (cntStarted > 0 ? Math.round((cntCompleted * 1d / cntStarted * 1d) * 100.0) : 0));
				item.put(IOpinion.JsonNames.PREVIEW_URL, String.format(OpinionEntity.PREVIEW_URL_FORMAT, ApplicationConfiguration.Opinion.Collector.getUrl(), opinionModel.getType().getValue(), opinionModel.getGuid()));
				if(null != opinionModel.getLastStartDate()){
					item.put(IOpinion.JsonNames.LAST_RESPONSE_DATE, formatter.format(opinionModel.getLastStartDate()));
				}
				if(null != opinionModel.getAvgTimeTakenSec()){
					item.put(IOpinion.JsonNames.TIME_TAKEN, JSONHelper.getTimeSpanSec(Math.round(opinionModel.getAvgTimeTakenSec())));
				}
				ja.put(item);
			}
			
			output = new JSONObject().put("list", ja);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getDetails(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {

		//StopWatch sw = new StopWatch();
		//sw.start();
		
		JSONObject returnValue = new JSONObject();
		BaseOperationResult result = null;
		HttpClientSession session = getContext().getSession();

		if (session.hasError()) {
			result = session;
		}

		// StopWatch
		//sw.stop();
		// point 1
		//if(sw.getTime() >100)
		//logger.warn("getDetails point 1:  %s", sw.toString());
		//sw.reset();
		//sw.start();
		// StopWatch
				
		IOpinion opinion = null;
		IAccount account = null;
		
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if (null == result) {
			OperationResult<IOpinion> getSurveyResult = OpinionsManager
					.getOpinion(input.getLong(IOpinion.JsonNames.OPINION_ID), account.getId());
			if (getSurveyResult.hasError()) {
				result = getSurveyResult;
			} else {
				opinion = getSurveyResult.getValue();
			}
		}

		/*
		// StopWatch
		sw.stop();
		// point 2
		if(sw.getTime() >100)
		logger.warn("getDetails point 2:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		*/
		
		IUser user = null;
		if (null == result && null != opinion.getLastModifyUserId()) {
			OperationResult<IUser> userResult = UsersManager
					.getUser(null, opinion.getLastModifyUserId(), null, null);
			if (userResult.hasError()) {
				result = userResult;
			} else {
				user = userResult.getValue();
			}
		}

		/*
		// StopWatch
		sw.stop();
		// point 3
		if(sw.getTime() >100)
		logger.warn("getDetails point 3:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		 */
		
		Long collectorId = null;
		if (null == result) {
			var list = CollectorsManager.getMeny(opinion.getId(), account.getId(), false, 2, null, null, null, null);
			
			if(list.size() == 1){
				collectorId = list.get(0).getId();
			}
		}

		/*
		// StopWatch
		sw.stop();
		// point 4
		if(sw.getTime() >100)
		logger.warn("getDetails point 4:  %s", sw.toString());
		sw.reset();
		sw.start();
		// StopWatch
		 */
		
		if (null == result) {

			returnValue = opinion.getJson(account.getTimezoneOffset());
			returnValue.put(JsonNames.PREVIEW_URL, opinion.getPreviewUrl(false));
			returnValue.put(JsonNames.PREVIEW_SECURE_URL, opinion.getPreviewUrl(true));
			
			if (null != user) {
				returnValue.put("lastModifiedByUser", user.getUserName());
			}
			
			returnValue.put(ICollector.JsonNames.COLLECTOR_ID, collectorId);
			
		} else {
			returnValue = result.toJson();
		}

		return returnValue;
	}
	
	protected JSONObject create(JSONObject input, int opinionTypeId) throws JSONException, IOException, NullPointerException, ExecutionException{
		JSONObject output = new JSONObject();

		BaseOperationResult result = null;

		String name = null;
		String description = null;
		String title = null;
		
		
		Long userId = null;
		OperationResult<Long> userResult = getUserId();
		if(userResult.hasError()){
			result = userResult;
		} else {
			userId = userResult.getValue();
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if (null == result) {
			if (null == account) {
				UUID errorId = logger.error("account is null");
				result = new BaseOperationResult(ErrorCode.GeneralError,
						errorId);
			}
		}
		
		Long opinionId = null;
		if (null == result) {
			
			name = input.getString(IOpinion.JsonNames.NAME);
			description = JSONHelper.optString(input, IOpinion.JsonNames.DESCRIPTION, null, "");
			opinionTypeId = input.getInt(IOpinion.JsonNames.OPINION_TYPE_ID);
			String actionGuid = UUID.randomUUID().toString();
			title = JSONHelper.optString(input, IOpinion.JsonNames.TITLE, null, "");
			OperationResult<Long> createResult = OpinionsManager.createOpinion(name, description, account.getId(), actionGuid, userId, OpinionType.fromInt(opinionTypeId), title);
			
			if (null == createResult){
				result = new OperationResult<Long>(ErrorCode.ArgumentOutOfRange, IOpinion.JsonNames.OPINION_TYPE_ID);
			} else if (createResult.hasError()) {
				result = createResult;
			} else {
				opinionId = createResult.getValue();
			}
		}

		if (null == result) {
			output.put(IOpinion.JsonNames.OPINION_ID, opinionId);

		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject create(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {

		int opinionTypeId = input.getInt(IOpinion.JsonNames.OPINION_TYPE_ID);
		return create(input, opinionTypeId);
	}
	
	public JSONObject rename(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = null;

		BaseOperationResult result = null;
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("rename : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			final Long accountId = account.getId();
			Long opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
			String name = JSONHelper.optString(input, IOpinion.JsonNames.NAME);
			String title = JSONHelper.optString(input, IOpinion.JsonNames.TITLE);
			String description = JSONHelper.optString(input, IOpinion.JsonNames.DESCRIPTION);
			Long translationId = JSONHelper.optLong(input, IOpinion.JsonNames.TRANSLATION_ID, IOpinion.DEFAULT_TRANSLATION_ID);
			result = OpinionsManager
					.changeOpinionName(opinionId, accountId, name, title,
							description, null /* actionGuid */, translationId, userId);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		return output;
	}
	
	public JSONObject copy(JSONObject input) throws Exception {

		JSONObject output;
		BaseOperationResult result = null;
		
		Long opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
		String opinionGuid = JSONHelper.optString(input, IOpinion.JsonNames.GUID);
		String translationName = JSONHelper.optString(input, IOpinion.JsonNames.TRANSLATION_NAME);
		Long translationId = JSONHelper.optLong(input, IOpinion.JsonNames.TRANSLATION_ID, IOpinion.DEFAULT_TRANSLATION_ID);
		String name = JSONHelper.optString(input, IOpinion.JsonNames.NAME);
		
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("copy : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		Long copyOpinionId = null;
		if(null == result){
			OperationResult<Long> copyResult;
			if(null != opinionGuid){
				copyResult = OpinionsManager.copyOpinion(
					opinionGuid, translationId, translationName,
					name, userId, account.getId());
			}
			else {
				copyResult = OpinionsManager.copyOpinion(
					opinionId, translationId, translationName,
					name, userId, account.getId(), account.getId());
			}
			if(!copyResult.hasError()){
				copyOpinionId = copyResult.getValue();
			}
		}

		if(null == result){
			output = new JSONObject()
			.put("opinionId", copyOpinionId);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject deleteOpinion(JSONObject input) {
		JSONObject output = null;

		try {
			BaseOperationResult result = null;
			Long userId = null;
			if(IsSignedIn()){
				userId = getUserId().getValue();
			} else {
				logger.warn("deleteOpinion : Not Signed in.");
				result = new BaseOperationResult(ErrorCode.NotSignedIn);
			}
			
			IAccount account = null;
			if(null == result) {
				OperationResult<IAccount> accountResult = getAccount();
				if(accountResult.hasError()){
					result = accountResult;
				} else {
					account = accountResult.getValue();
				}
			}
			
			if(null == result){
				final Long accountId = account.getId();
				Long opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
				BaseOperationResult deleteResult = OpinionsManager.deleteOpinion(
						opinionId, accountId, userId);
				if (deleteResult.hasError()) {
					result = deleteResult;
				}
			}
			
			if(null == result){
				output = BaseOperationResult.JsonOk;
			} else {
				output = result.toJson();
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"deleteOpinion() : Unexpected error occured.");
			try {
				output = new JSONObject();
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,
						"deleteOpinion() : Unable to put to Json object");
			}
		}

		return output;
	}
	
	public JSONObject deleteOpinions(JSONObject input) throws NullPointerException, IOException, JSONException, ExecutionException {
		JSONObject output = null;
		
		BaseOperationResult result = null;
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("deleteOpinions : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		List<BaseOperationResult> deleteResults = null;
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			final Long accountId = account.getId();
			JSONArray opinionIdsJsonArray = input.optJSONArray("list");
			List<Long> opinionIds = JSONHelper.toListOfLong(opinionIdsJsonArray);
			OperationResult<List<BaseOperationResult>> deleteResult = OpinionsManager.deleteOpinions(
					opinionIds, accountId, userId);
			if (deleteResult.hasError()) {
				result = deleteResult;
			} else {
				deleteResults = deleteResult.getValue();
			}
		}
		
		if(null == result){
			JSONArray jsonResults = new JSONArray();
			for (BaseOperationResult singleResult : deleteResults) {
				jsonResults.put(singleResult.hasError() ? singleResult.toJson() : singleResult.getTransactionId());
			}
			output = new JSONObject().put("list", jsonResults);
		} else {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject setTheme(JSONObject input) throws NullPointerException, IOException, JSONException, ExecutionException{
		JSONObject output = null;
		BaseOperationResult result = null;
		Long userId = null;
		
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("setTheme : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			Integer themeId = JSONHelper.optInt(input, JsonNames.THEME_ID);
			Long accountId = account.getId();
			Long opinionId = JSONHelper.optLong(input, JsonNames.OPINION_ID); 
						
			BaseOperationResult changeThemeResult = OpinionsManager.changeTheme(opinionId, themeId, accountId,
																				null /* actionGuid */, userId);
			if(changeThemeResult.hasError()){
				result = changeThemeResult;
			}
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getActivityChart(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		
		JSONObject output;
		IOperationResult result = validateSignIn();
		List<ActivityChartDataItem> items = null;
		TimePointRange timePointRange = null;
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			Long opinionId = JSONHelper.optLong(input, "opinionId");
			Long collectorId = JSONHelper.optLong(input, "collectorId");
			Date fromDate = account.removeDateOffset(JSONHelper.optDate(input, "fromDate"));
			Date toDate = account.removeDateOffset(JSONHelper.optDate(input, "toDate"));
			timePointRange = TimePointRange.fromInt(JSONHelper.optInt(input, "tpRangeId"));
			
			OperationResult<List<ActivityChartDataItem>> itemsResult = ChartsManager.getActivityChart(account.getId(), opinionId, collectorId, timePointRange, fromDate, toDate);
			if(itemsResult.hasError()){
				result = itemsResult;
			} else {
				items = itemsResult.getValue();
			}
		}
		
		if(null == result){
			
			//Format formatter;
			Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			/*
			switch(timePointRange){
			case Month:
				formatter = new SimpleDateFormat("dd-MMM-yyyy");
				break;
			case Week:
				formatter = new SimpleDateFormat("dd-MMM-yyyy");
				break;
			default:
			case Day:
				formatter = new SimpleDateFormat("dd-MMM-yyyy");
				break;
			case Hour:
				formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
				break;
			}
			*/
			JSONArray finishedArr = new JSONArray();
			JSONArray startedArr = new JSONArray();
			long totalFinished = 0;
			long totalStarted = 0;
			
			for(ActivityChartDataItem item : items){
				finishedArr.put(new Object[] { formatter.format(account.addDateOffset(item.getDate())), item.getCountOfFinishedOpinions()});
				startedArr.put(new Object[] {formatter.format(account.addDateOffset(item.getDate())), item.getCountOfStartedOpinions()});
				totalFinished += item.getCountOfFinishedOpinions();
				totalStarted += item.getCountOfStartedOpinions();
			}
				
			output = new JSONObject();
			
			JSONObject charts = new JSONObject();
			
			charts.put("completed", finishedArr);
			charts.put("partial", startedArr);
			charts.put("totals", new JSONObject().put("completed", totalFinished).put("partial", totalStarted));
			
			output.put("charts", charts);
		} else {
			output = result.toJson();
		}
		return output;
	}
	
	public JSONObject getAccountServicePackageDetails(JSONObject input) {
		JSONObject output = new JSONObject();
		int daysBack = JSONHelper.optInt(input, "daysBack", DAYS_BACK_DEFAULT_VALUE);
		Long opinionId = JSONHelper.optLong(input, "opinionId");
		
		try {
			IOperationResult result = null;
			IUser user = null;
			OperationResult<IUser> userResult = getLoggedInUser();
			if(userResult.hasError()){
				result = userResult;
			} else {
				user = userResult.getValue();
			}
			
			IAccount account = null;
			if(null == result) {
				OperationResult<IAccount> accountResult = getAccount();
				if(accountResult.hasError()){
					result = accountResult;
				} else {
					account = accountResult.getValue();
				}
			}
			
			if(null == result){
				
				OperationResult<IOpinionAccount> settingsResult = AccountsManager.getOpinionAccount(account);
				if(settingsResult.hasError()){
					result = settingsResult;
				} else {
					IOpinionAccount settings = settingsResult.getValue();
					output.put("sessionsBalance", settings.getSessionsBalance());
					if(settings.isShowWelcomeMessage() && (DateUtils.addDays(user.getInsertDate(), 1).after(new Date()))){
						output.put("showWelcomeMessage", settings.isShowWelcomeMessage());
					} else {
						output.put("showWelcomeMessage", false);
					}
					
					if(null != settings.getNextSessionsCreditDate()){
						output.put("nextSessionsCreditDate", DateFormatUtils.format(account.addDateOffset(settings.getNextSessionsCreditDate()), "MMM dd, yyyy"));
					}
					
					if(null != settings.getLastSessionsCreditDate() && null != settings.getSessionsBalance() && settings.getNextSupplySessionsCredit() > settings.getSessionsBalance()){
						output.put("lastSessionsCreditDate", DateFormatUtils.format(account.addDateOffset(settings.getLastSessionsCreditDate()), "MMM dd, yyyy"));
						output.put("nextSessionsCreditAmount", settings.getNextSupplySessionsCredit());
					}
				}
			}
			
			AccountOpinionInfo accountInfo = null;
			if(null == result){
				OperationResult<AccountOpinionInfo> accountInfoResult = AccountsManager.getAccountShortStatistics(account.getId(), daysBack, opinionId);
				if(accountInfoResult.hasError()){
					result = accountInfoResult;
				} else {
					accountInfo = accountInfoResult.getValue();
					output.put("complitedResponses", accountInfo.getCountOfFinishedSessions());
					output.put("visits", accountInfo.getCountOfStartedSessions());
					if(accountInfo.getCountOfFinishedSessions() > 0){
						output.put("completedPercentage", Math.round(100.0 / accountInfo.getCountOfStartedSessions() * accountInfo.getCountOfFinishedSessions()));
					} else {
						output.put("completedPercentage", 0);
					}
				}
			}
			
			if(null != result){
				output = result.toJson();
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"getAccountServicePackageDetails() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger
						.error(t,
								"getAccountServicePackageDetails() : Unable to put to Json object");
			}
		}

		return output;
	}
	
	public JSONObject getSettings(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {

		JSONObject output;
		IOpinion opinion = null;
		IOperationResult result = validateSignIn();
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if (null == result) {
			Long id = input.getLong(IOpinion.JsonNames.OPINION_ID);
			Long accountId = account.getId();
			OperationResult<IOpinion> opinionResult = OpinionsManager.getOpinion(id, accountId);
			if (opinionResult.hasError()) {
				result = opinionResult;
			} else {
				opinion = opinionResult.getValue();
			}
		}
		
		if(null == result){
			output = opinion.getSettingsJson();
		} else {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject updateSettings(final JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();

		IOperationResult result = validateSignIn();
		
		Long userId = null;
		if(null == result){
			userId = getUserId().getValue();
		}
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if (null == result) {
			
			Long accountId = account.getId();
			
			BaseOperationResult modifyResult = OpinionsManager.modifyOpinion(input, accountId, userId);
			if(modifyResult.hasError()){
				result = modifyResult;
			}
		}

		if (null == result) {
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	/*
	public JSONObject setAnswererSesionGrade(JSONObject input) {
		JSONObject output = null;
		try {
			BaseOperationResult result = null;
			Long userId = null;
			if(IsSignedIn()){
				userId = getUserId().getValue();
			} else {
				logger.warn("changeCollectorName : Not Signed in.");
				result = new BaseOperationResult(ErrorCode.NotSignedIn);
			}
			
			if(null == result){
				// TODO: get account more intelligently
				final Long accountId = getDefaultAccount().getId();
				Integer grade = JSONHelper.optInt(input, "grade");
				String comment = JSONHelper.optString(input, "note", null, "");
				long id = input.optLong("id");
				OperationResult<IAnswererSession> sessionResult = AnswerersSessionsManager
						.setAnswererSessionGrade(id, accountId, grade, comment, userId);
	
				if (sessionResult.hasError()) {
					result = sessionResult;
				}
			}

			if (null == result) {
				output = BaseOperationResult.JsonOk;
			} else {
				output = result.toJson();
			}

		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"setAnswererSesionGrade() : Unexpected error occured.");
			try {
				output = BaseOperationResult.toJsonGeneralError(t, errorId);
			} catch (JSONException e) {
				logger
						.error(t,
								"setAnswererSesionGrade() : Unable to put to Json object");
			}
		}

		return output;
	}
	*/

	
	
	/*
	public JSONObject setAnswererSesionStarred(JSONObject input) {
		JSONObject output = null;
		try {
			BaseOperationResult result = null;
			Long userId = null;
			if(IsSignedIn()){
				userId = getUserId().getValue();
			} else {
				logger.warn("setAnswererSesionStarred : Not Signed in.");
				result = new BaseOperationResult(ErrorCode.NotSignedIn);
			}
			
			if(null == result){
				// TODO: get account more intelligently
				final Long accountId = getDefaultAccount().getId();
				boolean starred = input.getBoolean("starred");
				long id = input.optLong("id");
				OperationResult<IAnswererSession> sessionResult = AnswerersSessionsManager
						.setAnswererSessionStarred(id, accountId, starred, userId);
	
				if (sessionResult.hasError()) {
					result = sessionResult;
				}
			}

			if (null == result) {
				output = BaseOperationResult.JsonOk;
			} else {
				output = result.toJson();
			}

		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"setAnswererSesionStarred() : Unexpected error occured.");
			try {
				output = BaseOperationResult.toJsonGeneralError(t, errorId);
			} catch (JSONException e) {
				logger
						.error(t,
								"setAnswererSesionStarred() : Unable to put to Json object");
			}
		}

		return output;
	}
	*/
}
