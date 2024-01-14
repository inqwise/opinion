package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.SurveyStatistics;
import com.inqwise.opinion.common.collectors.CollectorModel;
import com.inqwise.opinion.common.collectors.CollectorSourceType;
import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.collectors.ICollector.IMessagesExtension;
import com.inqwise.opinion.common.collectors.ICollector.JsonNames;
import com.inqwise.opinion.common.emails.ICollectLinkEmailData;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.ISurvey;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeReferenceType;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.managers.CollectorsManager;
import com.inqwise.opinion.managers.OpinionEmailsManager;
import com.inqwise.opinion.managers.OpinionsManager;

public class CollectorsEntry extends Entry implements IPostmasterObject {
	
	static ApplicationLog logger = ApplicationLog.getLogger(CollectorsEntry.class);
	static Format mdyhFormatter = new SimpleDateFormat("MMM dd, yyyy");
	
	public CollectorsEntry(IPostmasterContext context) {
		super(context);
	
	}
	
	public JSONObject getCollectorTypes(JSONObject input) throws JSONException{
		JSONObject output = new JSONObject();
		JSONArray jaTypes = new JSONArray();
		output.put("list", jaTypes);
		
		// typeId is collector type - deff. between collector for own respondents or purchase respondents
		// sourceId is provider identification number for example CINT
		jaTypes.put(new JSONObject().put("typeId", 1).put("sourceId", 1)).put(new JSONObject().put("typeId", 2).put("sourceId", 2).put("serviceId", 301));
		
		return output;
	}
	
	public JSONObject changeCollectorName(JSONObject input) {
		JSONObject output = null;

		try {

			BaseOperationResult result = null;
			OperationResult<Long> userIdResult = getUserId();
			Long userId = null;
			if(userIdResult.hasError()){
				result = userIdResult;
			} else {
				userId = userIdResult.getValue();
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
				Long collectorId = JSONHelper.optLong(input, ICollector.JsonNames.COLLECTOR_ID);
				String name = JSONHelper.optString(input, ICollector.JsonNames.NAME);
				result = CollectorsManager
						.modifyName(collectorId, accountId, name, null /* actionGuid */, userId);
			}
			
			if(result.hasError()){
				output = result.toJson();
			} else {
				output = BaseOperationResult.JsonOk;
			}
			
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"changeCollectorName() : Unexpected error occured.");
			try {
				output = BaseOperationResult.toJsonGeneralError(t, errorId);
			} catch (JSONException e) {
				logger.error(t,
						"changeCollectorName() : Unable to put to Json object");
			}
		}

		return output;
	}
	
	public JSONObject createCollector(JSONObject input) {
		JSONObject output = null;

		try {

			BaseOperationResult result = null;
			Long userId = null;
			if(IsSignedIn()){
				userId = getUserId().getValue();
			} else {
				logger.warn("createCollector : Not Signed in.");
				result = new BaseOperationResult(ErrorCode.NotSignedIn);
			}
			
			ICollector collector = null;
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
				OperationResult<ICollector> createResult = createCollector(input,
						account, userId);
				if (createResult.hasError()) {
					result = createResult;
				} else {
					collector = createResult.getValue();
				}
			}
			
			if(null == result){
				output = collector.toJson(account.getTimezoneOffset());
			} else {
				output = result.toJson();
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"createCollector() : Unexpected error occured.");
			try {
				output = new JSONObject();
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,
						"createCollector() : Unable to put to Json object");
			}
		}

		return output;
	}
	
	
	
	private OperationResult<ICollector> createCollector(final JSONObject input,
			final IAccount account, final Long userId) throws JSONException {

		OperationResult<ICollector> result = null;
		Long opinionId = JSONHelper.optLong(input,
				JsonNames.OPINION_ID);
		
		String name = JSONHelper.optString(input, JsonNames.NAME);
		Integer collectorSourceId = JSONHelper.optInt(input, JsonNames.SOURCE_ID);
		
		if (null == result) {
			result = CollectorsManager.create(opinionId, account.getId(), name, collectorSourceId, userId);
		}
		return result;
	}

	public JSONObject deleteCollectors(JSONObject input) {
		JSONObject output = null;
		try {

			BaseOperationResult result = null;
			Long userId = null;
			if(IsSignedIn()){
				userId = getUserId().getValue();
			} else {
				logger.warn("deleteCollectors : Not Signed in.");
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
				JSONArray collectorIdsJsonArray = input.optJSONArray("list");
				List<Long> collectorIds = JSONHelper.toListOfLong(collectorIdsJsonArray);
				OperationResult<List<BaseOperationResult>> deleteResult = CollectorsManager.deleteMeny(
						collectorIds, accountId, userId);
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
	
	public JSONObject getCollectorDetails(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();
		IOperationResult result = validateSignIn();
		Long collectorId = JSONHelper.optLong(input, JsonNames.COLLECTOR_ID);
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		ICollector collector = null;
		if (null == result) {
			OperationResult<ICollector> getCollectorResult = CollectorsManager
					.get(collectorId, account.getId());
			if (getCollectorResult.hasError()) {
				result = getCollectorResult;
			} else {
				collector = getCollectorResult.getValue();
			}
		}

		IOpinion opinion = null;
		if (null == result) {
			OperationResult<IOpinion> opinionResult = OpinionsManager
					.getOpinion(collector.getOpinionId(), account.getId());
			if (opinionResult.hasError()) {
				result = opinionResult;
			} else {
				opinion = opinionResult.getValue();
			}
		}

		if (null == result) {
			output = collector.toJson(account.getTimezoneOffset());
			output.put("opinionName", opinion.getName());
			if(opinion instanceof ISurvey){
				output.put("opinionLogoUrl", ((ISurvey) opinion).getLogoUrl());
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
		
		if (null == result && collector.getCollectorStatus() == CollectorStatus.PendingPayment) {
			var list = ChargesManager.getChargesByReferenceId(account.getId(), collectorId, ChargeReferenceType.Collector.getValue());
			if(list.size() == 1) {
				var model = list.get(0);
				JSONObject chargeJO = new JSONObject();
				chargeJO.put("chargeId", model.getId());
				chargeJO.put("amountDue", model.getAmount());
				output.put("charge", chargeJO);
			}				
		}
		return output;
	}


	public JSONObject deleteCollector(JSONObject input) {
		JSONObject output = null;

		try {

			BaseOperationResult result = null;
			Long userId = null;
			if(IsSignedIn()){
				userId = getUserId().getValue();
			} else {
				logger.warn("deleteCollector : Not Signed in.");
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
				Long collectorId = JSONHelper.optLong(input,
						JsonNames.COLLECTOR_ID);
				BaseOperationResult deleteResult = CollectorsManager
						.delete(collectorId, accountId, userId);
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

	public JSONObject getCollectors(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException{
		JSONObject output;
		
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
			
			int top = JSONHelper.optInt(input, "top", 100);
			Long opinionId = JSONHelper.optLong(input, "opinionId");
			Date fromDate = JSONHelper.optDate(input, "fromDate");
			Date toDate = JSONHelper.optDate(input, "toDate");
			
			List<CollectorModel> cList = CollectorsManager.getMeny(opinionId, account.getId(), false, top, fromDate, toDate, null, null);
			JSONArray ja = new JSONArray();
			Format formatter = new SimpleDateFormat(
					"MMM dd, yyyy HH:mm:ss");
			for (var collectorModel : cList){
				JSONObject jo = new JSONObject();
				Long collectorId = collectorModel.getId();
				jo.put(JsonNames.COLLECTOR_ID, collectorId);
				jo.put(JsonNames.OPINION_ID, collectorModel.getOpinionId());
				long actualAccountId = collectorModel.getAccountId();
				jo.put(JsonNames.COLLECTOR_UUID, collectorModel.getCollectorUuid());
				jo.put(JsonNames.NAME, collectorModel.getCollectorName());
				Integer statusId = collectorModel.getCollectorStatusId();
				jo.put(JsonNames.STATUS_ID, statusId);
				jo.put(JsonNames.SOURCE_TYPE_ID, collectorModel.getCollectorSourceTypeId());

				long cntStarted = collectorModel.getCntStartedOpinions();
				long cntCompleted = collectorModel.getCntFinishedOpinions();
				jo.put(JsonNames.STARTED_RESPONSES, cntStarted);
				jo.put(JsonNames.FINISHED_RESPONSES, cntCompleted);
				jo.put(JsonNames.PARTIAL_RESPONSES, cntStarted - cntCompleted);
				jo.put(JsonNames.COMPLETION_RATE, (cntStarted > 0 ? Math.round((cntCompleted * 1d / cntStarted * 1d) * 100.0) : 0));
				
				if(null != collectorModel.getLastStartDate()){
					jo.put(JsonNames.LAST_RESPONSE_DATE, formatter.format(collectorModel.getLastStartDate()));
				}
				jo.put(JsonNames.CLOSE_MESSAGE, collectorModel.getCloseMessage());
				
				if(null != collectorModel.getAvgTimeTakenSec()){
					jo.put(ICollector.JsonNames.TIME_TAKEN, JSONHelper.getTimeSpanSec(Math.round(collectorModel.getAvgTimeTakenSec())));
				}
				
				if (statusId == CollectorStatus.PendingPayment.getValue()) {
					var list = ChargesManager.getChargesByReferenceId(actualAccountId, collectorId, ChargeReferenceType.Collector.getValue());
					list.stream().findFirst().ifPresent(model -> {
						JSONObject chargeJO = new JSONObject();
						chargeJO.put(ICharge.JsonNames.CHARGE_ID, model.getId());
						chargeJO.put(ICharge.JsonNames.AMOUNT_DUE, model.getAmount());
						jo.put("charge", chargeJO);
					});
				}
				
				ja.put(jo);
			}
			output = new JSONObject();
			output.put("list", ja);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getCollectorSettings(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException {

		return getCollectorDetails(input);
	}
	
	public JSONObject sendCollectorLinkToMyEmail(JSONObject input){
		JSONObject output;
		try{
			BaseOperationResult result = null;
			IUser user = null;
			if(IsSignedIn()){
				user = getLoggedInUser().getValue();
			} else {
				logger.warn("sendCollectorLinkToMyEmail : Not Signed in.");
				result = new BaseOperationResult(ErrorCode.NotSignedIn);
			}
			
			Long accountId = null;
			ICollector collector = null;
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
				accountId = account.getId();
				Long collectorId = JSONHelper.optLong(input, "collectorId"); 
				
				OperationResult<ICollector> collectorResult = CollectorsManager.get(collectorId, accountId);
				if(collectorResult.hasError()){
					result = collectorResult;
				} else {
					collector = collectorResult.getValue();
				}
			}
			
			IProduct product = null;
			if(null == result){
				product = ProductsManager.getCurrentProduct();
				if(null == product){
					result = new BaseOperationResult(ErrorCode.NoResults);
				}
			}
			
			IOpinion opinion = null;
			if(null == result){
				OperationResult<IOpinion> opinionResult = collector.getOpinion(accountId);
				if(opinionResult.hasError()){
					result = opinionResult;
				} else {
					opinion = opinionResult.getValue();
				}
			}
			
			
			if(null == result){
				final ICollector finalCollector = collector;
				final IUser finalUser = user;
				final IProduct finalProduct = product;
				final IOpinion finalOpinion = opinion;
				
				// send email
				BaseOperationResult emailResult = OpinionEmailsManager.sendCollectLinkEmail(new ICollectLinkEmailData() {
					
					@Override
					public String getNoreplyEmail() {
						return finalProduct.getNoreplyEmail();
					}
					
					@Override
					public String getSupportEmail() {
						return finalProduct.getSupportEmail();
					}
					
					@Override
					public String getFeedbackShortCaption() {
						return finalProduct.getFeedbackShortCaption();
					}
					
					@Override
					public String getFeedbackCaption() {
						return finalProduct.getFeedbackCaption();
					}
					
					@Override
					public String getEmail() {
						return finalUser.getEmail();
					}
					
					@Override
					public int getCurrentYear() {
						return Calendar.getInstance().get(Calendar.YEAR);
					}
					
					@Override
					public String getApplicationUrl() {
						return ApplicationConfiguration.Opinion.getUrl();
					}
					
					@Override
					public ICollector getCollector() {
						return finalCollector;
					}

					@Override
					public IOpinion getOpinion() {
						return finalOpinion;
					}
				});
				
				if(emailResult.hasError()){
					result = emailResult;
				}
			}
			
			if(null == result){
				output = BaseOperationResult.JsonOk;
			} else {
				output = result.toJson();
			}
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t,
					"shareCollectorLinkToMyEmail() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger
						.error(t,
								"shareCollectorLinkToMyEmail() : Unable to put to Json object");
			}
		}
		
		return output;
	}

	public JSONObject updateCollector(JSONObject input) throws NullPointerException, IOException, JSONException, ExecutionException {
		JSONObject output = null;
		
		BaseOperationResult result = null;
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("updateCollector : Not Signed in.");
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
			BaseOperationResult updateResult = CollectorsManager.modify(input, userId, account.getId());;
			if (updateResult.hasError()) {
				result = updateResult;
			} 
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject updateCollectorStatus(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;
		
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("updateCollectorStatus : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		Long accountId = null;
		Long collectorId = null;
		int statusId = 0;
		String closeMessage = null;
		UUID guid = null;
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
			accountId = account.getId();
			collectorId = JSONHelper.optLong(input, "collectorId");
			statusId = input.getInt("statusId");
			closeMessage = JSONHelper.optString(input, "closeMessage",
					null, "");
			boolean isGenerateNewGuid = input.getBoolean("isGenerateNewGuid");
			
			if (isGenerateNewGuid) {
				guid = UUID.randomUUID();
			}
			
			CollectorStatus status = CollectorStatus.fromInt(statusId);
			if(!(status == CollectorStatus.Closed || status == CollectorStatus.Open)){
				result = new BaseOperationResult(ErrorCode.ArgumentOutOfRange, "StatusId is invalid");
			}
		}
		
		ICollector collector = null;
		if(null == result){
			OperationResult<ICollector> collectorResult = CollectorsManager.get(collectorId, accountId);
			if(collectorResult.hasError()){
				result = collectorResult;
			} else {
				collector = collectorResult.getValue();
			}
		}
		
		if(null == result && collector.getCollectorSourceType() != CollectorSourceType.Default){
			result = new BaseOperationResult(ErrorCode.InvalidOperation, "Invalid collectorSorceType");
		}
		
		if(null == result) {
			result = CollectorsManager.modifyStatus(collectorId,
					statusId, accountId, closeMessage, guid, userId);
		}
		
		if(result.hasError()) {
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}

		return output;
	}

}
