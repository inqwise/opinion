package com.inqwise.opinion.handlers;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataGridException;
import net.casper.data.model.CDataRowSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeReferenceType;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.SurveyStatistics;
import com.inqwise.opinion.opinion.common.collectors.CollectorSourceType;
import com.inqwise.opinion.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.collectors.ICollector.IMessagesExtension;
import com.inqwise.opinion.opinion.common.collectors.ICollector.JsonNames;
import com.inqwise.opinion.opinion.common.collectors.ICollector.ResultSetNames;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.ISurvey;
import com.inqwise.opinion.opinion.managers.CollectorsManager;
import com.inqwise.opinion.opinion.managers.OpinionsManager;
import com.inqwise.opinion.library.managers.ProductsManager;

public class CollectorsEntry extends Entry {

	static ApplicationLog logger = ApplicationLog.getLogger(CollectorsEntry.class);
	static Format mdyhFormatter = new SimpleDateFormat("MMM dd, yyyy");
	protected CollectorsEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getCollectors(JSONObject input) throws CDataGridException, JSONException{
		JSONObject output;
		int top = JSONHelper.optInt(input, "top", 100);
		Long opinionId = JSONHelper.optLong(input, "opinionId");
		Long accountId = JSONHelper.optLong(input, "accountId");
		boolean includeExpired = JSONHelper.optBoolean(input, "includeExpired", true);
		Date fromDate = JSONHelper.optDate(input, "fromDate");
		Date toDate = JSONHelper.optDate(input, "toDate");
		LinkedHashMap<Long, List<JSONObject>> collectorsByAccountId = null;  
		if(null == accountId){
			collectorsByAccountId = new LinkedHashMap<>();
		}
		
		CDataCacheContainer ds = CollectorsManager.getMeny(opinionId, accountId, includeExpired, top, fromDate, toDate, null, ICollector.ResultSetNames.LAST_START_DATE);
		JSONArray ja = new JSONArray();
		
		
		
		//int accountsResult = AccountsManager.getAccounts(null, null, null, true, null, null, )
		
		CDataRowSet rowSet = ds.getAll();
		
		Format formatter = new SimpleDateFormat(
				"MMM dd, yyyy HH:mm:ss");
		while(rowSet.next()){
			JSONObject jo = new JSONObject();
			jo.put(ICollector.JsonNames.COLLECTOR_ID, rowSet.getLong(ICollector.ResultSetNames.COLLECTOR_ID));
			jo.put(ICollector.JsonNames.OPINION_ID, rowSet.getLong(ICollector.ResultSetNames.OPINION_ID));
			Long actualAccountId = rowSet.getLong(ICollector.ResultSetNames.ACCOUNT_ID);
			jo.put(ICollector.JsonNames.ACCOUNT_ID, actualAccountId);
			jo.put(ICollector.JsonNames.COLLECTOR_UUID, rowSet.getString(ICollector.ResultSetNames.COLLECTOR_UUID));
			jo.put(ICollector.JsonNames.MODIFY_DATE, mdyhFormatter.format(rowSet.getDate(ICollector.ResultSetNames.MODIFY_DATE)));
			jo.put(ICollector.JsonNames.CREATE_DATE, mdyhFormatter.format(rowSet.getDate(ICollector.ResultSetNames.CREATE_DATE)));
			if(null != rowSet.getDate(ICollector.ResultSetNames.EXPIRATION_DATE)){
				jo.put(ICollector.JsonNames.EXPIRATION_DATE, mdyhFormatter.format(rowSet.getDate(ICollector.ResultSetNames.EXPIRATION_DATE)));
			}
			jo.put(ICollector.JsonNames.NAME, rowSet.getString(ICollector.ResultSetNames.COLLECTOR_NAME));
			
			String opinionName = rowSet.getString(ICollector.ResultSetNames.OPINION_NAME);
			String opinionShortName = (opinionName.length() > 20 ? opinionName.substring(0, 18) + ".." : opinionName);
			jo.put(ICollector.JsonNames.OPINION_NAME, opinionName);
			jo.put("opinionShortName", opinionShortName);
			jo.put(ICollector.JsonNames.OPINION_TYPE_NAME, rowSet.getString(ICollector.ResultSetNames.OPINION_TYPE_NAME));
			jo.put(ICollector.JsonNames.STATUS_ID, rowSet.getInt(ICollector.ResultSetNames.COLLECTOR_STATUS_ID));
			jo.put(ICollector.JsonNames.SOURCE_ID, rowSet.getInt(ICollector.ResultSetNames.COLLECTOR_SOURCE_ID));
			jo.put(ICollector.JsonNames.SOURCE_NAME, rowSet.getString(ICollector.ResultSetNames.COLLECTOR_SOURCE_NAME));
			jo.put(ICollector.JsonNames.SOURCE_TYPE_ID, rowSet.getInt(ICollector.ResultSetNames.COLLECTOR_SOURCE_TYPE_ID));
			long cntStarted = rowSet.getLong(ResultSetNames.CNT_STARTED_OPINIONS);
			long cntCompleted = rowSet.getLong(ResultSetNames.CNT_FINISHED_OPINIONS);
			jo.put(JsonNames.STARTED_RESPONSES, cntStarted);
			jo.put(JsonNames.FINISHED_RESPONSES, cntCompleted);
			jo.put(JsonNames.PARTIAL_RESPONSES, cntStarted - cntCompleted);
			jo.put(JsonNames.COMPLETION_RATE, (cntStarted > 0 ? Math.round((cntCompleted * 1d / cntStarted * 1d) * 100.0) : 0));
			if(null != rowSet.getDate(ICollector.ResultSetNames.LAST_START_DATE)){
				jo.put(ICollector.JsonNames.LAST_RESPONSE_DATE, formatter.format(rowSet.getDate(ICollector.ResultSetNames.LAST_START_DATE)));
			}
			if(null != rowSet.getDouble(ICollector.ResultSetNames.AVG_TIME_TAKEN_SEC)){
				jo.put(ICollector.JsonNames.TIME_TAKEN, JSONHelper.getTimeSpanSec(Math.round(rowSet.getDouble(ICollector.ResultSetNames.AVG_TIME_TAKEN_SEC))));
			}
			ja.put(jo);
			
			// Collect accountIds with referenced JsonObjects for later use
			if(null == accountId){
				List<JSONObject> collectors = collectorsByAccountId.get(actualAccountId);
				if(null == collectors){
					collectors = new ArrayList<>();
					collectorsByAccountId.put(actualAccountId, collectors);
				}
				
				collectors.add(jo);
			}
		}
		
		// Get details for previously collected accounts
		Long[] accountIds = null;
		if(null == accountId){
			accountIds = getArrayFromKeys(collectorsByAccountId);
			IProduct product = ProductsManager.getProductByGuid(ApplicationConfiguration.Opinion.getProductGuid()).getValue();
			CDataCacheContainer dsAccounts = AccountsManager.getAccounts(null, product.getId(), top, true, null, null, accountIds);
			if(dsAccounts.size() > 0){
				fillAccountDetails(dsAccounts, collectorsByAccountId);
			}
		}
		
		output = new JSONObject();
		output.put("list", ja);
		
		return output;
	}
	
	private void fillAccountDetails(CDataCacheContainer dsAccounts,
			LinkedHashMap<Long, List<JSONObject>> collectorsByAccountId) throws JSONException, CDataGridException {
		CDataRowSet rowSet = dsAccounts.getAll();
		while(rowSet.next()){
			List<JSONObject> joList = collectorsByAccountId.get(rowSet.getLong(IAccount.ResultSetNames.ACCOUNT_ID));
			for (JSONObject jo : joList) {
				jo.put(ICollector.JsonNames.ACCOUNT_NAME, rowSet.getString(IAccount.ResultSetNames.ACCOUNT_NAME));
			}
		}
	}

	private Long[] getArrayFromKeys(
			LinkedHashMap<Long, List<JSONObject>> collectorsByAccountId) {
		return collectorsByAccountId.keySet().toArray(new Long[collectorsByAccountId.size()]);
	}

	public JSONObject getCollectorDetails(JSONObject input) {
		JSONObject output = new JSONObject();
		try {

			BaseOperationResult result = null;
			Long collectorId = JSONHelper.optLong(input, "collectorId");
			
			ICollector collector = null;
			if (null == result) {
				OperationResult<ICollector> getCollectorResult = CollectorsManager
						.get(collectorId, null);
				if (getCollectorResult.hasError()) {
					result = getCollectorResult;
				} else {
					collector = getCollectorResult.getValue();
				}
			}

			IOpinion opinion = null;
			if (null == result) {
				OperationResult<IOpinion> surveyResult = OpinionsManager
						.getOpinion(collector.getOpinionId(), null);
				if (surveyResult.hasError()) {
					result = surveyResult;
				} else {
					opinion = surveyResult.getValue();
				}
			}

			if (null == result) {
				output = collector.toJson(null);
				output.put("opinionName", opinion.getName());
				output.put("opinionId", opinion.getId());
				if(opinion instanceof ISurvey){
					output.put("previewUrl", ((ISurvey)opinion).getPreviewUrl(false));
				}
				
				if(collector instanceof ICollector.IMessagesExtension){
					output.put("closeMessage", ((IMessagesExtension) collector).getCloseMessage());
				}
				
				output.put(IOpinion.JsonNames.OPINION_TYPE_ID, opinion.getOpinionType().getValue());
			} else {
				output = result.toJson();
			}
			
			if (null == result && collector.getCollectorStatus() == CollectorStatus.PendingPurchase) {
				OperationResult<SurveyStatistics> statisticsResult = OpinionsManager.getSurveyShortStatistics(collector.getOpinionId());
				if(statisticsResult.hasError()){
					result = statisticsResult;
				} else {
					output.put("countOfControls", statisticsResult.getValue().getCountOfControls());
				}
			}
			
			if (null == result && collector.getCollectorSourceType() == CollectorSourceType.BuyRespondents) {
				CDataCacheContainer dataSet = ChargesManager.getChargesByReferenceId(null, collectorId, ChargeReferenceType.Collector.getValue());
				CDataRowSet rowSet = dataSet.getAll();
				if(rowSet.next()){
					JSONObject chargeJO = new JSONObject();
					chargeJO.put("chargeId",rowSet.getLong("charge_id"));
					if(collector.getCollectorStatus() == CollectorStatus.PendingPayment){
						chargeJO.put("amountDue", rowSet.getDouble("amount"));
					}
					output.put("charge", chargeJO);
				}				
			}

		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"getCollectorDetails() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId);
			} catch (JSONException e) {
				logger.error(t,
						"getCollectorDetails() : Unable to put to Json object");
			}
		}
		return output;
	}
	
	public JSONObject updateCollectorStatus(JSONObject input) {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;
		try {
			Long userId = getContext().getUserId().getValue();
			
			if(null == result){
				Long collectorId = JSONHelper.optLong(input, "collectorId");
				int statusId = input.getInt("statusId");
				String closeMessage = JSONHelper.optString(input, "closeMessage",
						null, "");
				boolean isGenerateNewGuid = input.getBoolean("isGenerateNewGuid");
				UUID guid = null;
				if (isGenerateNewGuid) {
					guid = UUID.randomUUID();
				}
				
				result = CollectorsManager.modifyStatus(collectorId,
						statusId, null, closeMessage, guid, userId);
			}
			
			if(result.hasError()) {
				output = result.toJson();
			} else {
				output = BaseOperationResult.JsonOk;
			}

		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"updateCollectorStatus() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId);
			} catch (JSONException e) {
				logger.error(t,
						"updateCollectorStatus() : Unable to put to Json object");
			}
		}
		return output;
	}
	
	public JSONObject deleteCollector(JSONObject input) {
		JSONObject output = null;

		try {

			BaseOperationResult result = null;
			Long userId = getContext().getUserId().getValue();
			
			if(null == result){
				Long collectorId = JSONHelper.optLong(input,
						JsonNames.COLLECTOR_ID);
				BaseOperationResult deleteResult = CollectorsManager
						.delete(collectorId, null, userId);
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
					"deleteCollector() : Unexpected error occured.");
			try {
				output = new JSONObject();
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,
						"deleteCollector() : Unable to put to Json object");
			}
		}

		return output;
	}
	
	public JSONObject deleteCollectors(JSONObject input) {
		JSONObject output = null;
		try {

			BaseOperationResult result = null;
			Long userId = getContext().getUserId().getValue();
			
			List<BaseOperationResult> deleteResults = null;
			if(null == result){
				JSONArray collectorIdsJsonArray = input.optJSONArray("list");
				List<Long> collectorIds = JSONHelper.toListOfLong(collectorIdsJsonArray);
				OperationResult<List<BaseOperationResult>> deleteResult = CollectorsManager.deleteMeny(
						collectorIds, null, userId);
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
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"deleteCollectors() : Unexpected error occured.");
			try {
				output = new JSONObject();
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,
						"deleteCollectors() : Unable to put to Json object");
			}
		}

		return output;
	}
}
