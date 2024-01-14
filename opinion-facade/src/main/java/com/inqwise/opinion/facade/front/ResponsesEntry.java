package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.common.AnswererSessionModel;
import com.inqwise.opinion.common.IAnswererSession;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.SurveyStatistics;
import com.inqwise.opinion.common.analizeResults.IAnalizeControl;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.managers.AnswerersSessionsManager;
import com.inqwise.opinion.managers.OpinionsManager;
import com.inqwise.opinion.managers.ResultsManager;

public class ResponsesEntry extends Entry implements IPostmasterObject {
	static ApplicationLog logger = ApplicationLog.getLogger(ResponsesEntry.class);
	
	private static Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	
	public ResponsesEntry(IPostmasterContext context){
		super(context);
	}
	
	public JSONObject getResponses(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();
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
		
		if(null == result){
			Long opinionId = JSONHelper.optLong(input, "opinionId");
			Long collectorId = JSONHelper.optLong(input, "collectorId");
			String respondentId = JSONHelper.optString(input, "respondentId");
			Long fromIndex = JSONHelper.optLong(input, "fromIndex");
			Integer top = JSONHelper.optInt(input, "top");
			int countOfUnplanned = 0;
			AtomicLong total = new AtomicLong(); 
			
			List<AnswererSessionModel> list = AnswerersSessionsManager
					.getAnswerersSessions(opinionId, account.getId(),
							collectorId, respondentId, true,
							fromIndex, top, total);
			
			JSONArray ja = new JSONArray();
			
			Map<Long, String> collectors = new TreeMap<Long, String>();
			
			for(var answererSessionModel : list){
				
				boolean isUnplained = answererSessionModel.getIsUnplained();
				
				if(isUnplained){
					countOfUnplanned ++;
				} else {
					JSONObject jo = new JSONObject();
					jo.put("rowIndex", answererSessionModel.getIndex());
					jo.put("sessionId", answererSessionModel.getAnswerSessionId());
					jo.put("countryName", answererSessionModel.getCountryName());
					jo.put("ipAddress", answererSessionModel.getClientIp());
					jo.put("targetUrl", answererSessionModel.getTargetUrl());
					jo.put("status", null == answererSessionModel.getFinishDate() ? 0 : 1);
					jo.put("startDate", formatter.format(account.addDateOffset(answererSessionModel.getInsertDate())));
					jo.put("finishDate", null == answererSessionModel.getFinishDate() ? JSONObject.NULL : formatter.format(account.addDateOffset(answererSessionModel.getFinishDate())));
					/* jo.put("note", JSONHelper.getNullable(session.getComment())); */
					/* jo.put("grade", JSONHelper.getNullable(session.getGrade())); */
					
					if(null != answererSessionModel.getTimeTakenSec()){
						jo.put("timeTaken", JSONHelper.getTimeSpanSec(answererSessionModel.getTimeTakenSec()));
					}
					jo.put("id", answererSessionModel.getId());
					//jo.put("starred", session.isStarred());
					jo.put("collectorId", answererSessionModel.getCollectorId());
					jo.put("collectorName", answererSessionModel.getCollectorName());
					jo.put("collectorIsDeleted", !answererSessionModel.getIsCollectorExist());
					ja.put(jo);
					
					if(!collectors.containsKey(answererSessionModel.getCollectorId())){
						collectors.put(answererSessionModel.getCollectorId(), answererSessionModel.getCollectorName());
					}
				}
			}
			
			if(ja.length() > 0){
			
				output.put("list", ja);
				output.put("total", total.get());
				
				if(collectors.size() > 0){
					JSONArray collectorsJa = new JSONArray();
					for(Long actualCollectorId : collectors.keySet()){
						JSONObject collectorJo = new JSONObject();
						collectorJo.put(ICollector.JsonNames.COLLECTOR_ID, actualCollectorId);
						collectorJo.put(ICollector.JsonNames.NAME, collectors.get(actualCollectorId));
						collectorsJa.put(collectorJo);
					}
					
					output.put("collectorsList", collectorsJa);
				}
				
				if(countOfUnplanned > 0){
					output.put("unplanned", countOfUnplanned);
				}
			} else {
				output = BaseOperationResult.toJsonError(ErrorCode.NoResults);
			}
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject getAnswererSessionDetails(JSONObject input) throws NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();
		
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
		
		IAnswererSession session = null;
		if(null == result){
			String answererSessionId = input.optString("answererSessionId");
			
			OperationResult<IAnswererSession> answererSessionResult = AnswerersSessionsManager
					.getAnswererSession(null, answererSessionId, account.getId());
			if (answererSessionResult.hasError()) {
				result = answererSessionResult;
			} else {
				session = answererSessionResult.getValue();
			}
		}
		
		if (null == result) {
			output = session.toJson();
		} else {
			output = result.toJson();
		}
			
		return output;
	}

	public JSONObject getAnswererResults(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();
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
		
		Long opinionId = null;
		if(null == result){
			opinionId = JSONHelper.optLong(input, "opinionId");
			String answererSessionId = JSONHelper.optString(input,
					"answererSessionId");
			Long collectorId = JSONHelper.optLong(input, "collectorId");
			boolean includePartial = JSONHelper.optBoolean(input, "includePartial", false);
			
			boolean includePartialAnswers = includePartial;
			boolean includePartialStatistics = includePartial;
			Long sheetId = JSONHelper.optLong(input, "sheetId");
			
			OperationResult<List<IAnalizeControl>> controlsResult = ResultsManager
					.getAnalizeControls(opinionId, account.getId(), answererSessionId, collectorId, includePartialAnswers, includePartialStatistics, true);
			List<IAnalizeControl> controls = null;
			if (controlsResult.hasError()) {
				result = controlsResult;
			} else {
				controls = controlsResult.getValue();
				JSONArray controlsJa = new JSONArray();
				for (IAnalizeControl control : controls) {
					controlsJa.put(control.toJson(false));
				}
				output.put("controls", new JSONObject().put("list", controlsJa));
			}
		}

		SurveyStatistics statistics;
		if(null == result){
			OperationResult<SurveyStatistics> statisticsResult = OpinionsManager.getSurveyShortStatistics(opinionId);
			if(statisticsResult.hasError()){
				result = statisticsResult;
			} else {
				statistics = statisticsResult.getValue();
				output.put("partial", statistics.getCountOfSessions() - statistics.getCountOfFinishedSessions());
				output.put("completed", statistics.getCountOfFinishedSessions());
				output.put("completionRate", (statistics.getCountOfSessions() > 0 ? Math.round((statistics.getCountOfFinishedSessions() * 1d / statistics.getCountOfSessions() * 1d) * 100.0) : 0));
			}
		}
		
		if (null != result) {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject deleteResponses(JSONObject input) throws NullPointerException, ExecutionException, IOException {
		JSONObject output = null;
		
		BaseOperationResult result = null;
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("deleteResponses : Not Signed in.");
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
			JSONArray responseIdsJsonArray = input.optJSONArray("list");
			List<Long> responseIds = JSONHelper.toListOfLong(responseIdsJsonArray);
			OperationResult<List<BaseOperationResult>> deleteResult = AnswerersSessionsManager.deleteAnswererSessions(
					responseIds, accountId, userId);
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
	
	public JSONObject getAnswererResultsText(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();
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
		
		if(null == result){
			Long opinionId = JSONHelper.optLong(input, "opinionId");
			Long optionId = JSONHelper.optLong(input, "optionId");
			Long controlId = JSONHelper.optLong(input, "controlId");
			boolean includePartial = JSONHelper.optBoolean(input, "includePartial", false);
			
			OperationResult<Map<String, String>> freeTextResult = ResultsManager
					.getResultsFreeText(opinionId, controlId, optionId, includePartial, account.getId());
			
			Map<String, String> textMap = null;
			if (freeTextResult.hasError()) {
				result = freeTextResult;
			} else {
				textMap = freeTextResult.getValue();
				JSONArray textJa = new JSONArray();
				for (Map.Entry<String, String> textElement : textMap.entrySet()) {
					textJa.put(new JSONObject().put("sessionId", textElement.getKey()).put("text", textElement.getValue()));
				}
				
				output.put("list", textJa);
			}
		}
		
		if (null != result) {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject getCountriesStatistics(JSONObject input) throws NullPointerException, ExecutionException, IOException, JSONException{
		JSONObject output = null;
		IOperationResult result = null;
		IAccount account = null;
		
		Date from = JSONHelper.optDate(input, "fromDate");
		Date to = JSONHelper.optDate(input, "toDate");
		Long opinionId = JSONHelper.optLong(input, "opinionId");
		Long collectorId = JSONHelper.optLong(input, "collectorId");
		Boolean includePartial = JSONHelper.optBoolean(input, "includePartial", false);
		
		result = validateSignIn();
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			CDataCacheContainer opinionsDataSet = ResultsManager.getCountriesStatistics(opinionId, account.getId(), account.removeDateOffset(from), account.removeDateOffset(to), collectorId, includePartial);
			CDataRowSet rowSet = opinionsDataSet.getAll();
			JSONArray ja = new JSONArray();
			
			while(rowSet.next()){
				JSONObject item = new JSONObject();
				Integer countryId = (rowSet.getInt("country_id"));
				
				if(null == countryId){
					item.put("countryName", "Other");
					item.put("iso2", "oo");
				} else {
					item.put("countryId", countryId);
					item.put("countryName", rowSet.getString("country_name"));
					item.put("iso2", rowSet.getString("iso2").toLowerCase());
				}
				Long timeTakenSec = rowSet.getLong("time_taken_sec");
				Long cntStarted = rowSet.getLong("started");
				Long cntCompleted = rowSet.getLong("completed");
				Long cntPartial = cntStarted - cntCompleted;
				item.put("started", cntStarted);
				item.put("completed", cntCompleted);
				item.put("partial", cntPartial);
				if(null != timeTakenSec){
					item.put("timeTaken", JSONHelper.getTimeSpanSec(timeTakenSec));
				}
				
				if(null != cntStarted){
					item.put("completionRate", (cntStarted > 0 ? Math.round((cntCompleted * 1d / cntStarted * 1d) * 100.0) : 0));
				}
				ja.put(item);
			}
			
			output = new JSONObject().put("list", ja);
		} else {
			output = result.toJson();
		}
		return output;
	}
}
