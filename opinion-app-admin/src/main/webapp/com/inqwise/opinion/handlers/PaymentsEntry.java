package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.GeoIpManager;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.pay.IChargePayRequest;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.common.users.IUserDetails;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.payments.PayProcessorsFactory;
import com.inqwise.opinion.payments.common.CreditCardTypes;
import com.inqwise.opinion.payments.common.IDirectPaymentRequest;
import com.inqwise.opinion.payments.common.IDirectPaymentResponse;
import com.inqwise.opinion.payments.common.IPayAction;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PaymentActionCodeTypes;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;

public class PaymentsEntry extends Entry {
	
	static ApplicationLog logger = ApplicationLog.getLogger(PaymentsEntry.class);
	public PaymentsEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject directPayment(final JSONObject input){
		JSONObject output;
		
		try{
			IOperationResult result = null;
			final Date timestamp = new Date();
			final String creditCardNumber = input.getString("creditCardNumber");
			final String last4Digits = creditCardNumber.length() > 4 ? StringUtils.right(creditCardNumber, 4) : "";
			final String ipAddress = getContext().getClientIp();
			final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
			final Long backofficeUserId = getContext().getUserId().getValue();
			final Long userId = input.optLong("userId");
			final Long accountId = JSONHelper.optLong(input, "accountId");
			List<Long> chargesIds = JSONHelper.toListOfLong(JSONHelper.optJsonArray(input, "charges"));
			double amount = input.optDouble("amount");
			
			String stateCode = JSONHelper.optString(input, "stateId", null, "");
			String postalCode = JSONHelper.optString(input, "postalCode", null, "");
			String lastName = JSONHelper.optString(input, "lastName");
			String firstName = JSONHelper.optString(input, "firstName");
			String countryCode = JSONHelper.optString(input, "countryCode");
			String address1 = JSONHelper.optStringTrim(input, "address1", null);
			String address2 = JSONHelper.optStringTrim(input, "address2", null);
			String city = input.getString("city");
			boolean isAddressChanged = input.getBoolean("isAddressChanged");
			
			PayOperationResult<IPayActionResponse> actionResult = makeFund(
					input, timestamp, creditCardNumber, last4Digits,
					geoCountryCode, userId, accountId, null, amount,
					stateCode, postalCode, lastName, firstName, countryCode,
					city, address1, address2);
			
			IDirectPaymentResponse transactionResponse = null;
			if(actionResult.hasError()){
				result = actionResult;
			} else {
				transactionResponse = (IDirectPaymentResponse) actionResult.getValue();
			}
			
			if(null == result && null != chargesIds){
				BaseOperationResult invoicePayResult = payCharges(chargesIds,
						geoCountryCode, userId, accountId, null);
				if(invoicePayResult.hasError()){
					result = invoicePayResult;
				} 
			}
			
			if(null == result && isAddressChanged){
				Integer stateId = JSONHelper.optInt(input, "stateId");
				int countryId = input.getInt("countryId");
				setBusinessAddress(accountId, stateId, postalCode, lastName, firstName, countryId, city, address1, address2, null, null);
			}
			
			if(null == result){
				output = new JSONObject();
			} else {
				output = result.toJson();
			}
			
			if(null != transactionResponse){
				output.put("fundTransactionId", transactionResponse.getAccountOperationId());
			}
			
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t,"directPayment() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,"directPayment() : Unable to put to Json object");
			}
		}
		
		return output;
	}
	
	private PayOperationResult<IPayActionResponse> makeFund(
			final JSONObject input, final Date timestamp,
			final String creditCardNumber, final String last4Digits,
			final String geoCountryCode, final long userId, final long accountId,
			final String sessionId, final double amount, final String state,
			final String postalCode, final String lastName,
			final String firstName, final String countryCode,
			final String city, final String address1, final String address2) throws JSONException {
		
		IDirectPaymentRequest request = new IDirectPaymentRequest() {
			
			@Override
			public Date getTimeStamp() {
				return timestamp;
			}
			
			@Override
			public String getStateOrProvince() {
				return state;
			}
			
			@Override
			public String getPostalCode() {
				return postalCode;
			}
			
			@Override
			public PaymentActionCodeTypes getPaymentAction() {
				return PaymentActionCodeTypes.Sale;
			}
			
			@Override
			public String getLastName() {
				return lastName;
			}
			
			@Override
			public String getLast4DigitsOfCreditCardNumber() {
				return last4Digits;
			}
			
			@Override
			public String getFirstName() {
				return firstName;
			}
			
			@Override
			public Integer getExpDateYear() {
				return JSONHelper.optInt(input, "expDateYear");
			}
			
			@Override
			public Integer getExpDateMonth() {
				return JSONHelper.optInt(input, "expDateMonth");
			}
			
			@Override
			public String getCurrencyCode() {
				return "USD";
			}
			
			@Override
			public CreditCardTypes getCreditCardType() {
				return CreditCardTypes.fromInt(input.optInt("creditCardTypeId"));
			}
			
			@Override
			public String getCreditCardNumber() {
				return creditCardNumber;
			}
			
			@Override
			public String getCountryCode() {
				return countryCode;
			}
			
			@Override
			public String getClientIp() {
				return getContext().getClientIp();
			}
			
			@Override
			public String getCityName() {
				return city;
			}
			
			@Override
			public String getCVV2() {
				return input.optString("cvv2Number");
			}
			
			@Override
			public double getAmount() {
				return amount;
			}
			
			@Override
			public String getAddress2() {
				return JSONHelper.optString(input, "address2", null, "");
			}
			
			@Override
			public String getAddress1() {
				return JSONHelper.optString(input, "address1", null, "");
			}

			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public String getDetails() {
				return JSONHelper.optString(input, "details", null, "");
			}

			@Override
			public String getGeoCountryCode() {
				return geoCountryCode;
			}

			@Override
			public String getSessionId() {
				return sessionId;
			}

			@Override
			public int getSourceId() {
				return ProductsManager.getCurrentProduct().getId();
			}

			@Override
			public Long getUserId() {
				return userId;
			}

			@Override
			public Long getBackofficeUserId() {
				return null;
			}
		};
		
		IPayAction action = PayProcessorsFactory.getInstance().getProcessor(PayActionTypes.DirectPayment, request.getCreditCardType())
							.getAction(PayActionTypes.DirectPayment);
		action.setRequest(request);
		PayOperationResult<IPayActionResponse> actionResult = action.process();
		return actionResult;
	}
	
	public JSONObject payCharge(JSONObject input) throws JSONException, IOException{
		JSONObject output = null;
		IOperationResult result = null;
		
		final String ipAddress = getContext().getClientIp();
		final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
		final long chargeId = input.getLong("chargeId");
		final long accountId = input.getLong("accountId");
		final Long backofficeUserId = getContext().getUserId().getValue();
		
		ICharge charge = null;
		if(null == result){
			// get Charge
			OperationResult<ICharge> chargeResult = ChargesManager.getCharge(chargeId, accountId);
			if(chargeResult.hasError()){
				result = chargeResult;
			} else {
				charge = chargeResult.getValue();
			}
		}
		
		IAccountView account = null;
		if(null == result){
			OperationResult<IAccountView> accountResult = AccountsManager.getAccount(accountId);
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		IUser user = null;
		if(null == result){
			// get user
			OperationResult<IUser> userResult = UsersManager.getUser(null, account.getOwnerId(), null, null);
			if(userResult.hasError()){
				result = userResult;
			} else {
				user = userResult.getValue();
			}
		}
		
		ChargeStatus chargeStatus = null;
		if(null == result){
			if(charge.getStatus() == ChargeStatus.Unpaid){
				if(charge.getAmountToFund() > 0){
					output = new JSONObject();
					output.put("chargeId", charge.getId());
					output.put("amountToFund", charge.getAmountToFund());
					output.put("amountDue", charge.getAmount());
					output.put("balance", charge.getBalance());
				} else {
					OperationResult<ChargeStatus> payChargeResult = payCharge(chargeId,
							geoCountryCode, backofficeUserId, null, null);
					
					if(payChargeResult.hasError()){
						result = payChargeResult;
					} else {
						chargeStatus = payChargeResult.getValue();
						output = new JSONObject();
						output.put("transactionId", payChargeResult.getTransactionId());
					}
				}
			} else {
				UUID errorId = logger.error("payInvoice: Received pay action for Charge #%s with status '%s'", charge.getId(), charge.getStatus());
				result = new BaseOperationResult(ErrorCode.InvalidOperation, errorId);
			}
		}
		
		if(null != result){
			output = result.toJson();
		}
		
		return output;
	}
	
	private BaseOperationResult payCharges(final List<Long> chargesIds,
			final String geoCountryCode, final long userId, final Long accountId,
			final String sessionId){
		
		BaseOperationResult result = null;
		
		for (Long chargeId : chargesIds) {
			OperationResult<ChargeStatus> payChargeResult = payCharge(chargeId, geoCountryCode, userId, accountId, sessionId);
			if(payChargeResult.hasError()){
				result = payChargeResult;
				break;
			}
		}
		
		if(null == result){
			result = BaseOperationResult.Ok;
		}
		
		return result;
	}
	
	private OperationResult<ChargeStatus> payCharge(final long chargeId,
			final String geoCountryCode, final long userId, final Long accountId,
			final String sessionId) {
		OperationResult<ChargeStatus> result = null;
		double amountDue = 0;
		
		if(null == result){
			OperationResult<ICharge> chargeResult = ChargesManager.getCharge(chargeId, accountId);
			if(chargeResult.hasError()){
				result = chargeResult.toErrorResult();
			} else {
				ICharge charge = chargeResult.getValue();
				amountDue = charge.getAmount();
				if(charge.getAmountToFund() > 0){
					logger.warn("payCharge : no enough funds to pay Charge. chargeId:'%s'", chargeId);
					result = new OperationResult<ChargeStatus>(ErrorCode.NoEnoughFunds);
				}
			}
		}
		
		if(null == result){
			result = payCharge(chargeId, geoCountryCode, userId, accountId,
					sessionId, amountDue);
		}
		return result;
	}

	private OperationResult<ChargeStatus> payCharge(final long chargeId,
			final String geoCountryCode, final long userId,
			final Long accountId, final String sessionId, final double amount) {
		OperationResult<ChargeStatus> result;
		IChargePayRequest chargePayRequest = new IChargePayRequest() {
			
			@Override
			public long getChargeId() {
				return chargeId;
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
		return result;
	}
	
	public JSONObject getPaymentDetails(JSONObject input) throws IOException, JSONException{
		
		IOperationResult result = null;
		long accountId;
		IAccountBusinessDetails businessDetails = null;
		boolean hasBusinessAddress = false;
		JSONObject output;
		
		if(null == result){
			accountId = input.getLong("accountId");
			
			OperationResult<IAccountBusinessDetails> businessDetailsResult = AccountsManager.getAccountBusinessDetails(accountId);
			if(businessDetailsResult.hasError()){
				result = businessDetailsResult;
			} else {
				businessDetails = businessDetailsResult.getValue();
				hasBusinessAddress = businessDetails.hasBusinessAddress();
			}
		}
		
		IUserDetails userDetails = null;
		if(null == result && !hasBusinessAddress){
			OperationResult<IUserDetails> userDetailsResult = UsersManager.getUserDetails(input.getLong("userId"));
			if(userDetailsResult.hasError()){
				result = userDetailsResult;
			} else {
				userDetails = userDetailsResult.getValue();
			}
		}
		
		if(null == result){
			output = new JSONObject();
			output.put("countryId", (hasBusinessAddress ? businessDetails.getCountryId() : userDetails.getCountryId()))
			.put("address1", (hasBusinessAddress ? businessDetails.getAddress1() : userDetails.getAddress1()))
			.put("address2", (hasBusinessAddress ? businessDetails.getAddress2() : userDetails.getAddress2()))
			.put("city", (hasBusinessAddress ? businessDetails.getCity() : userDetails.getCity()))
			.put("firstName", (hasBusinessAddress ? businessDetails.getFirstName() : userDetails.getFirstName()))
			.put("lastName", (hasBusinessAddress ? businessDetails.getLastName() : userDetails.getLastName()))
			.put("phone1", (hasBusinessAddress ? businessDetails.getPhone1() : userDetails.getPhone1()))
			.put("postalCode", (hasBusinessAddress ? businessDetails.getPostalCode() : userDetails.getPostalCode()))
			.put("stateId", (hasBusinessAddress ? businessDetails.getStateId() : userDetails.getStateId()))
			.put("hasBusinessAddress", hasBusinessAddress);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	private void setBusinessAddress(final long accountId, final Integer stateId,
			final String postalCode, final String lastName, final String firstName,
			final int countryId, final String city,	final String address1,
			final String address2, final String phone1, final String companyName) {
		
		AccountsManager.setAccountBusinessDetails(new IAccountBusinessDetailsChangeRequest() {
			@Override
			public Integer getStateId() {
				return stateId;
			}
			
			@Override
			public String getPostalCode() {
				return postalCode;
			}
			
			@Override
			public String getPhone1() {
				return phone1;
			}
			
			@Override
			public String getLastName() {
				return lastName;
			}
			
			@Override
			public long getId() {
				return accountId;
			}
			
			@Override
			public String getFirstName() {
				return firstName;
			}
			
			@Override
			public int getCountryId() {
				return countryId;
			}
			
			@Override
			public String getCompanyName() {
				return companyName;
			}
			
			@Override
			public String getCity() {
				return city;
			}
			
			@Override
			public String getAddress2() {
				return address2;
			}
			
			@Override
			public String getAddress1() {
				return address1;
			}
		});
	}
}
