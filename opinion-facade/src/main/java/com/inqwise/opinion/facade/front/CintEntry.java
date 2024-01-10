package com.inqwise.opinion.facade.front;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.cint.CintApiService;
import com.inqwise.opinion.cint.OrderDetailsRequest;
import com.inqwise.opinion.cint.OrderPurchaseRequest;
import com.inqwise.opinion.cint.common.IOrder;
import com.inqwise.opinion.cint.common.IOrderSubmit;
import com.inqwise.opinion.cint.common.ITargetGroupSubmit;
import com.inqwise.opinion.cint.common.OrderEventType;
import com.inqwise.opinion.cint.common.errorHandle.CintOperationResult;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.collectors.IPanelSurveysCollector;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.Convert;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.GeoIpManager;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeReferenceType;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.pay.IChargeCreateRequest;
import com.inqwise.opinion.library.common.pay.IChargePayRequest;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.managers.CollectorsManager;

public class CintEntry extends Entry implements IPostmasterObject {
	
	static ApplicationLog logger = ApplicationLog.getLogger(CintEntry.class);
	public CintEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject orderPurchase(JSONObject input) throws JSONException{
		JSONObject output = new JSONObject();
		try{
			IOperationResult result = null;
			
			String data = input.optString("data");
			Map<String, List<String>> params = Convert.fromQueryString(data);
			long collectorId = input.getLong("collectorId");
			String strAgeRange = params.get("age_range_select").get(0);
			String[] minMaxAgeArr = StringUtils.split(strAgeRange, "+-");
			Integer minAge = Integer.parseInt(minMaxAgeArr[0]);
			Integer maxAge = null;
			if(minMaxAgeArr.length == 2){
				maxAge = Integer.valueOf(minMaxAgeArr[1]);
			}
			String countryId = params.get("country_id").get(0);
			String genderId = StringUtils.trimToNull(params.get("gender_select").get(0));
			//String strIncidence = params.get("incidence_rate").get(0);
			Integer numberOfCompletes = Integer.valueOf(params.get("number_of_completes_select").get(0));
			int numberOfQuestions = Integer.parseInt(params.get("number_of_questions").get(0));
			String region = StringUtils.trimToNull(params.get("region_select").get(0));
			String signedQuote = params.get("signed_quote").get(0);
			String surveyTitle = params.get("survey_title").get(0);
			String SurveyUrl = params.get("survey_url").get(0);
			List<String> educationLevels = params.get("education_level_select");
			List<String> occupationStatuses = params.get("occupation_status_select");
			String globalVariables = (null == params.get("GV") ? null : params.get("GV").get(0));
			String strQuote = params.get("quote").get(0);
			String orderLocation = null;
			String externalId = null;
			
			String currency = null;
			double amount = 0;
			String chargeDescription = null;
			String chargeName = null;
			Long userId = null;
			final String ipAddress = getContext().getClientIp();
			final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
			Long accountId = null;
			String sessionId = null;
			IUser user = null;
			
			if(IsSignedIn()){
				userId = getUserId().getValue();
				user = getLoggedInUser().getValue();
				sessionId = getContext().getSession().getSessionId();
			} else {
				logger.warn("orderPurchase : Not Siggned in.");
				result = new BaseOperationResult(ErrorCode.NotSignedIn);
			}
			
			IAccount account = null;
			if(null == result) {
				OperationResult<IAccount> accountResult = getAccount();
				if(accountResult.hasError()){
					result = accountResult;
				} else {
					account = accountResult.getValue();
					accountId = account.getId();
				}
			}
			
			if(null == result){
				if(!account.getOwnerId().equals(user.getUserId())){
					result = new BaseOperationResult(ErrorCode.NotPermitted);
				}
			}
			
			if(null == result && collectorId == 0){
				result = new BaseOperationResult(ErrorCode.ArgumentMandatory, "collectorId");
			}
			
			if(null == result){
				result = CintApiService.verifySignature(strQuote, signedQuote);
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
			
			if(null == result && !(collector instanceof IPanelSurveysCollector)){
				logger.warn("orderPurchase : only collectorSourceId '2' supported, received: '%s'", collector);
				result = new BaseOperationResult(ErrorCode.ArgumentOutOfRange, "only sourceId 2 supported");
			}
			
			/*
			ICollectorSource collectorSource = null;
			if(null == result){
				OperationResult<ICollectorSource> collectorSourceResult = CollectorSourcesManager.getCollectorSource(((IPanelSurveysCollector) collector).getCollectorSourceId());
				if(collectorSourceResult.hasError()){
					result = collectorSourceResult;
				} else {
					collectorSource = collectorSourceResult.getValue();
				}
			}
			*/
			
			// Create Order in Cint
			OrderPurchaseRequest orderRequest = null;
			CintApiService api = null;
			if(null == result){
				api = new CintApiService();
				orderRequest = new OrderPurchaseRequest();
				orderRequest.setInitialStatus(OrderEventType.Hold);
				IOrderSubmit order = orderRequest.getOrder();
				order.setNumberOfCompletes(numberOfCompletes);
				order.setNumberOfQuestions(numberOfQuestions);
				order.setSurveyTitle(surveyTitle);
				order.setSurveyUrl(SurveyUrl);
				ITargetGroupSubmit targetGroup = order.getTargetGroup();
				if(null != educationLevels){
					targetGroup.setEducationLevels(educationLevels);
				}
				
				if(null != occupationStatuses){
					targetGroup.setOccupationStatuses(occupationStatuses);
				}
				
				if(null != region){
					targetGroup.getRegions().add(region);
				}
				targetGroup.setCountryId(countryId);
				targetGroup.setGenderId(genderId);
				targetGroup.setMaxAge(maxAge);
				targetGroup.setMinAge(minAge);
				
				if(null != globalVariables){
					String[] variables = StringUtils.split(globalVariables, '-');
					for (String variable : variables) {
						targetGroup.getVariables().add(variable);
					}
				}
				
				CintOperationResult<String> createOrderResult = api.call(orderRequest);
				if(createOrderResult.hasError()){
					result = createOrderResult;
				} else {
					orderLocation = createOrderResult.getValue();
				}
			}
			
			if(null == result){
				externalId = OrderPurchaseRequest.identifyLocationId(orderLocation);
				String[] quoteArr = StringUtils.split(strQuote, " ");
				currency = quoteArr[1];
				amount = Double.parseDouble(quoteArr[0].replace(",", ""));
				chargeName = "Cint panel #" + externalId;
				chargeDescription = orderRequest.getOrderDetails();
				output.put("externalId", externalId);
			
			}
			
			Date expirationDate = null;
			ICharge charge = null;
			if(null == result){
				expirationDate = DateUtils.addDays(DateConverter.trim(Calendar.getInstance().getTime()), 2);
				// Create Charge
				OperationResult<ICharge> chargeResult = createCharge(amount, currency, accountId, ChargeReferenceType.Collector.getValue(), collectorId, userId, chargeName, chargeDescription, expirationDate);
				if(chargeResult.hasError()){
					result = chargeResult;
				} else {
					charge = chargeResult.getValue();
				}
			}
			
			if(null == result){
				//Save location into collector
				// and set collector status to PendingPayment
				BaseOperationResult collectorResult = CollectorsManager.setExternalId(collectorId, externalId, CollectorStatus.PendingPayment, accountId, expirationDate, userId, numberOfCompletes.longValue());
				if(collectorResult.hasError()){
					result = collectorResult;
				}
			}
			
			if(null == result){
				ChargeStatus chargeStatus = null;
				output.put("chargeId", charge.getId());
				if(charge.getAmountToFund() > 0){
					output.put("amountToFund", charge.getAmountToFund());
					output.put("amount", charge.getAmount());
					output.put("balance", charge.getBalance());
					output.put("error", ErrorCode.NoEnoughFunds);
				} else {
					// pay Invoice from balance
					OperationResult<ChargeStatus> payChargeResult = payCharge(charge.getId(),
							geoCountryCode, userId, accountId, sessionId, charge.getAmount(),
							user.getEmail(), user.getCultureCode());
					if(payChargeResult.hasError()){
						result = payChargeResult;
					} else {
						chargeStatus = payChargeResult.getValue();
						output.put("transactionId", payChargeResult.getTransactionId());
					}
					
					// change collector status
					if(null == result && chargeStatus == ChargeStatus.Paid){
						BaseOperationResult updateStatusResult = CollectorsManager.modifyStatus(collectorId, CollectorStatus.Verify.getValue(), accountId, null, null, userId);
						if(updateStatusResult.hasError()){
							result = updateStatusResult;
						}
					}
				}
			}
			
			if(null != result){
				output = result.toJson();
			}
		} catch (Exception e) {
			UUID errorId = logger.error(e,
					"orderPurchase() : Unexpected error occured");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId);
			} catch (JSONException e1) {
				logger.error(e, "orderPurchase() : Failed to put into JSON");
			}
		}
		return output;
	}

	private OperationResult<ChargeStatus> payCharge(final long invoiceId,
			final String geoCountryCode, final long userId, final long accountId,
			final String sessionId, final double amount, final String userEmail,
			final String cultureCode) {
		OperationResult<ChargeStatus> result = null;
		
		if(null == result){
						
			IChargePayRequest chargePayRequest = new IChargePayRequest() {
				
				@Override
				public long getChargeId() {
					return invoiceId;
				}
				
				@Override
				public Long getUserId() {
					return userId;
				}
				
				@Override
				public int getSourceId() {
					return ProductsManager.getCurrentProduct().getId();
				}
				
				@Override
				public String getSessionId() {
					return sessionId;
				}
				
				@Override
				public String getGeoCountryCode() {
					return geoCountryCode;
				}
				
				@Override
				public String getClientIp() {
					return getContext().getClientIp();
				}
				
				@Override
				public double getAmount() {
					return amount;
				}
				
				@Override
				public long getAccountId() {
					return accountId;
				}
			};
			
			result = ChargesManager.payCharge(chargePayRequest, true);
		}
		return result;
	}
	
	private OperationResult<ICharge> createCharge(final double amount, final String currency,
			final Long accountId, final int referenceTypeId, final long collectorId, final long userId,
			final String chargeName, final String chargeDescription, final Date expirationDate) throws JSONException {
		
		
		OperationResult<ICharge> result = null;
		
		if(null == result){
			IChargeCreateRequest chargeRequest = new IChargeCreateRequest() {
				
				@Override
				public Long getUserId() {
					return userId;
				}
				
				@Override
				public String getName() {
					return chargeName;
				}
				
				@Override
				public String getDescription() {
					return chargeDescription;
				}
				
				@Override
				public Integer getBillTypeId() {
					return null;
				}
				
				@Override
				public Long getBillId() {
					return null;
				}
				
				@Override
				public double getAmount() {
					return amount;
				}
				
				@Override
				public String getAmountCurrency() {
					return currency;
				}

				@Override
				public Integer getReferenceTypeId() {
					return referenceTypeId;
				}

				@Override
				public Long getReferenceId() {
					return collectorId;
				}
				
				@Override
				public long getAccountId() {
					return accountId;
				}

				@Override
				public String getPostPayAction() {
					return "com.inqwise.opinion.automation.actions.ActivateCintCollectorAction";
				}

				@Override
				public int getStatusId() {
					return ChargeStatus.Unpaid.getValue();
				}

				@Override
				public Date getExpiryDate() {
					return expirationDate;
				}

				@Override
				public JSONObject getPostPayActionData() {
					return null;
				}
			};
			
			result = ChargesManager.createCharge(chargeRequest);
		}
		
		return result;
	}
	
	public JSONObject getOrderDetails(JSONObject input) throws JSONException{
		JSONObject output;
		String locationId = input.optString("externalId");
		OrderDetailsRequest detailsRequest = new OrderDetailsRequest();
		detailsRequest.setLocationId(locationId);
		
		CintApiService api = new CintApiService();
		CintOperationResult<IOrder> detailsResponse = api.call(detailsRequest);
		if(detailsResponse.hasError()){
			output = detailsResponse.toJson();
		} else {
			output = new JSONObject(detailsResponse.getValue());
		}
		
		return output;
	}
}
