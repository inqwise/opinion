package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataGridException;
import net.casper.data.model.CDataRowSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.IOpinionTemplate;
import com.inqwise.opinion.opinion.common.opinions.ISurvey;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.common.opinions.OpinionsOrderBy;
import com.inqwise.opinion.opinion.common.opinions.IOpinion.ResultSetNames;
import com.inqwise.opinion.opinion.http.HttpClientSession;
import com.inqwise.opinion.opinion.managers.OpinionsManager;

public class OpinionsEntry extends Entry {

	static ApplicationLog logger = ApplicationLog.getLogger(OpinionsEntry.class);
	
	protected OpinionsEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getList(JSONObject input) throws JSONException, CDataGridException{
		
		JSONObject output;
		Long accountId = JSONHelper.optLong(input, "accountId");
		int top = JSONHelper.optInt(input, "top", 100);
		Date from = JSONHelper.optDate(input, "from");
		Date to = JSONHelper.optDate(input, "to");
		int opinionTypeId = JSONHelper.optInt(input, IOpinion.JsonNames.OPINION_TYPE_ID, OpinionType.Survey.getValue());
		LinkedHashMap<Long, List<JSONObject>> surveysByAccountId = null;  
		if(null == accountId){
			surveysByAccountId = new LinkedHashMap<>();
		}
		
		CDataCacheContainer opinionsDataSet = OpinionsManager.getOpinions(accountId, top, from, to, opinionTypeId, IOpinion.DEFAULT_TRANSLATION_ID, OpinionsOrderBy.ModifyDate);
		CDataRowSet rowSet = opinionsDataSet.getAll();
		Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
		JSONArray ja = new JSONArray();
		while(rowSet.next()){
			JSONObject item = new JSONObject();
			
			item.put(IOpinion.JsonNames.OPINION_ID, rowSet.getLong(IOpinion.ResultSetNames.OPINION_ID));
			item.put(IOpinion.JsonNames.NAME, rowSet.getString(IOpinion.ResultSetNames.NAME));
			item.put(IOpinion.JsonNames.MODIFY_DATE, formatter.format(rowSet.getDate(IOpinion.ResultSetNames.MODIFY_DATE)));
			long cntStarted = rowSet.getLong(ResultSetNames.CNT_STARTED_OPINIONS);
			long cntCompleted = rowSet.getLong(ResultSetNames.CNT_FINISHED_OPINIONS);
			item.put(IOpinion.JsonNames.CNT_STARTED_OPINIONS, cntStarted);
			item.put(IOpinion.JsonNames.CNT_FINISHED_OPINIONS, cntCompleted);
			item.put(IOpinion.JsonNames.CNT_PARTIAL_OPINIONS, cntStarted - cntCompleted);
			item.put(IOpinion.JsonNames.COMPLETION_RATE, (cntStarted > 0 ? Math.round((cntCompleted * 1d / cntStarted * 1d) * 100.0) : 0));
			Long actualAccountId = rowSet.getLong(IOpinion.ResultSetNames.ACCOUNT_ID);
			item.put(IOpinion.JsonNames.ACCOUNT_ID, actualAccountId);
			item.put(IOpinion.JsonNames.PREVIEW_URL, String.format(ISurvey.PREVIEW_URL_FORMAT, ApplicationConfiguration.Opinion.Collector.getUrl(), opinionTypeId, rowSet.getString(IOpinion.ResultSetNames.GUID)));
			if(null != rowSet.getDouble(IOpinion.ResultSetNames.AVG_TIME_TAKEN_SEC)){
				item.put(IOpinion.JsonNames.TIME_TAKEN, JSONHelper.getTimeSpanSec(Math.round(rowSet.getDouble(IOpinion.ResultSetNames.AVG_TIME_TAKEN_SEC))));
			}
			item.put(IOpinion.JsonNames.COUNT_CONTROLS, rowSet.getInt(IOpinion.ResultSetNames.COUNT_CONTROLS));
			ja.put(item);
			
			// Collect accountIds with referenced JsonObjects for later use
			if(null == accountId){
				List<JSONObject> collectors = surveysByAccountId.get(actualAccountId);
				if(null == collectors){
					collectors = new ArrayList<>();
					surveysByAccountId.put(actualAccountId, collectors);
				}
				
				collectors.add(item);
			}
		}
		
		// Get details for previously collected accounts and fill in json
		Long[] accountIds = null;
		if(null == accountId){
			accountIds = getArrayFromKeys(surveysByAccountId);
			IProduct product = ProductsManager.getProductByGuid(ApplicationConfiguration.Opinion.getProductGuid()).getValue();
			CDataCacheContainer dsAccounts = AccountsManager.getAccounts(null, product.getId(), top, true, null, null, accountIds);
			if(dsAccounts.size() > 0){
				fillAccountDetails(dsAccounts, surveysByAccountId);
			}
		}
		
		output = new JSONObject().put("list", ja);
		
		return output;
	}
	
	private void fillAccountDetails(CDataCacheContainer dsAccounts,
			LinkedHashMap<Long, List<JSONObject>> collectorsByAccountId) throws JSONException, CDataGridException {
		CDataRowSet rowSet = dsAccounts.getAll();
		while(rowSet.next()){
			List<JSONObject> joList = collectorsByAccountId.get(rowSet.getLong(IAccount.ResultSetNames.ACCOUNT_ID));
			for (JSONObject jo : joList) {
				jo.put(IOpinion.JsonNames.ACCOUNT_NAME, rowSet.getString(IAccount.ResultSetNames.ACCOUNT_NAME));
			}
		}
	}

	private Long[] getArrayFromKeys(
			LinkedHashMap<Long, List<JSONObject>> objectsByAccountId) {
		return objectsByAccountId.keySet().toArray(new Long[objectsByAccountId.size()]);
	}
	
	public JSONObject deleteOpinions(JSONObject input) throws IOException, JSONException{
		BaseOperationResult result = null;
		
		Long userId = null;
		
		JSONArray opinionIdsJa = null;
		if(null == result){
			userId = getContext().getUserId().getValue(); 
			opinionIdsJa = input.optJSONArray("list");
			if(null == opinionIdsJa){
				result = new BaseOperationResult(ErrorCode.ArgumentNull, "'list' is mandatory");
			}
		}
		
		if(null == result){
			for(int i = 0 ; i < opinionIdsJa.length() ; i++){
				Long opinionId = opinionIdsJa.optLong(i);
				if(opinionId > 0){
					result = OpinionsManager.deleteOpinion(opinionId, null, userId);
					if(result.hasError()){
						break;
					}
				}
			}
		}
		
		if(null == result || !result.hasError()){
			return BaseOperationResult.JsonOk;
		} else {
			return result.toJson();
		}
	}
	
	public JSONObject getDetails(JSONObject input) throws IOException {

		JSONObject returnValue = new JSONObject();
		BaseOperationResult result = null;
		try {
			HttpClientSession session = getContext().getSession();

			if (session.hasError()) {
				result = session;
			}

			IOpinion opinion = null;
			if (null == result) {
				OperationResult<IOpinion> getSurveyResult = OpinionsManager
						.getOpinion(input.getLong("opinionId"), null);
				if (getSurveyResult.hasError()) {
					result = getSurveyResult;
				} else {
					opinion = getSurveyResult.getValue();
				}
			}

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
			
			if (null == result) {
				returnValue = opinion.getJson(0);
				if (null != user) {
					returnValue
							.put("lastModifiedByUser", user.getUserName());
				}
			} else {
				returnValue = result.toJson();
			}

		} catch (Exception e) {
			UUID errorId = logger.error(e,
					"getSurveyDetails() : Unexpected error occured");
			try {
				returnValue.put("error", ErrorCode.GeneralError).put("errorId",
						errorId);
			} catch (JSONException e1) {
				logger.error(e, "getSurveyDetails() : Failed to put into JSON");
			}
		}

		return returnValue;
	}
	
	public JSONObject copy(JSONObject input) throws Exception {

		JSONObject output;
		BaseOperationResult result = null;
		
		Long opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
		String translationName = JSONHelper.optString(input, IOpinion.JsonNames.TRANSLATION_NAME);
		Long translationId = JSONHelper.optLong(input, IOpinion.JsonNames.TRANSLATION_ID, IOpinion.DEFAULT_TRANSLATION_ID);
		String name = JSONHelper.optString(input, IOpinion.JsonNames.NAME);
		Long toAccountId = JSONHelper.optLong(input, IOpinion.JsonNames.ACCOUNT_ID);
		Long userId = getContext().getUserId().getValue();
		
		Long copyOpinionId = null;
		if(null == result){
			OperationResult<Long> copyResult;
			
			copyResult = OpinionsManager.copyOpinion(
				opinionId, translationId, translationName,
				name, userId, null, toAccountId);
			
			
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
	
	public JSONObject updateTemplate(final JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();

		IOperationResult result = null;
		
		Long userId = null;
		if(null == result){
			userId = getContext().getLoggedInUser().getValue().getUserId();
		}
		
		if (null == result) {
			
			Long opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
			String templateName = JSONHelper.optStringTrim(input, IOpinionTemplate.JsonNames.TEMPLATE_NAME);
			String templateDescription = JSONHelper.optStringTrim(input, IOpinionTemplate.JsonNames.TEMPLATE_DESCRIPTION);
			String templateUrlName = JSONHelper.optStringTrim(input, IOpinionTemplate.JsonNames.TEMPLATE_URL_NAME);
			HashSet<String> keywords = null;
			HashSet<Integer> categories = null;
			
			BaseOperationResult updateResult = OpinionsManager.updateTemplate(opinionId, userId, templateName, templateDescription, templateUrlName, keywords, categories);
			if(updateResult.hasError()){
				result = updateResult;
			}
		}

		if (null == result) {
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject activateTemplate(final JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();

		IOperationResult result = null;
		
		Long userId = null;
		if(null == result){
			userId = getContext().getLoggedInUser().getValue().getUserId();
		}
		
		if (null == result) {
			
			Long opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
			boolean active = JSONHelper.optBoolean(input, IOpinionTemplate.JsonNames.ACTIVE, true);
			
			BaseOperationResult activateResult = OpinionsManager.activateTemplate(opinionId, userId, active);
			if(activateResult.hasError()){
				result = activateResult;
			}
		}

		if (null == result) {
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject deleteTemplate(final JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();

		IOperationResult result = null;
		
		Long userId = null;
		if(null == result){
			userId = getContext().getLoggedInUser().getValue().getUserId();
		}
		
		if (null == result) {
			
			Long opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
			
			BaseOperationResult deleteResult = OpinionsManager.deleteTemplate(opinionId, userId);
			if(deleteResult.hasError()){
				result = deleteResult;
			}
		}

		if (null == result) {
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getTemplate(final JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();

		IOperationResult result = null;
		
		Long userId = null;
		if(null == result){
			userId = getContext().getLoggedInUser().getValue().getUserId();
		}
		
		IOpinionTemplate tempalte = null;
		if (null == result) {
			
			Long opinionId = JSONHelper.optLong(input, IOpinion.JsonNames.OPINION_ID);
			
			OperationResult<IOpinionTemplate> templateResult = OpinionsManager.getTemplate(opinionId, userId);
			//if(templateResult.hasError()){
			//	result = templateResult;
			//}
		}

		if (null == result) {
			output = BaseOperationResult.NotImplemented.toJson();
		} else {
			output = result.toJson();
		}
		
		return output;
	}
}
