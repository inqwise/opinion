package com.inqwise.opinion.opinion.facade.front;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetailsChangeRequest;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.AccessValue;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IAccess;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.parameters.PermissionsKeys;
import com.inqwise.opinion.library.common.parameters.VariablesCategories;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.pay.IChargePayRequest;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.common.users.IUserDetails;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.managers.ParametersManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.library.systemFramework.GeoIpManager;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;
import com.inqwise.opinion.opinion.http.HttpClientSession;
import com.inqwise.opinion.payments.PayProcessorsFactory;
import com.inqwise.opinion.payments.common.CreditCardTypes;
import com.inqwise.opinion.payments.common.IBillingAgreement;
import com.inqwise.opinion.payments.common.IDirectPaymentRequest;
import com.inqwise.opinion.payments.common.IDirectPaymentResponse;
import com.inqwise.opinion.payments.common.IPayAction;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.IPaymentDetailsItem;
import com.inqwise.opinion.payments.common.ISetExpressCheckoutRequest;
import com.inqwise.opinion.payments.common.ISetExpressCheckoutResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PaymentActionCodeTypes;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;

public class PaymentsEntry extends Entry implements IPostmasterObject{

	private static final String USD_CURRENCY_CODE = "USD";
	static ApplicationLog logger = ApplicationLog.getLogger(PaymentsEntry.class);
	
	public PaymentsEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject directPayment(final JSONObject input) throws JSONException{
		JSONObject output;
		
		try{
			IOperationResult result = null;
			final Date timestamp = new Date();
			final String ipAddress = getContext().getClientIp();
			final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
			final double makeFundAmount = input.optDouble("amount");
			List<Long> chargesIds = JSONHelper.toListOfLong(JSONHelper.optJsonArray(input, "charges"));
			
			String stateCode = JSONHelper.optString(input, "stateId", null, "");
			String postalCode = JSONHelper.optString(input, "postalCode", null, "");
			String lastName = JSONHelper.optString(input, "lastName");
			String firstName = JSONHelper.optString(input, "firstName");
			String countryCode = JSONHelper.optString(input, "countryCode");
			String address1 = JSONHelper.optStringTrim(input, "address1", null);
			String address2 = JSONHelper.optStringTrim(input, "address2", null);
			String city = input.getString("city");
			boolean isAddressChanged = input.getBoolean("isAddressChanged");
			
			long userId = 0;
			IAccount account = null;
			IUser user = null;
			String sessionId = null;
			
			OperationResult<IUser> userResult = getLoggedInUser();
			if(userResult.hasError()){
				result = userResult;
			} else {
				user = userResult.getValue();
				userId = user.getUserId();
			}
			
			if(null == result) {
				sessionId = getContext().getSession().getSessionId();
				OperationResult<IAccount> accountResult = getAccount();
				if(accountResult.hasError()){
					result = accountResult;
				} else {
					account = accountResult.getValue();
				}
			}
			
			if(null == result){
				if(!account.getOwnerId().equals(user.getUserId())){
					result = new BaseOperationResult(ErrorCode.NotPermitted);
				}
			}

			HashMap<String, IVariableSet> permissions = null;
			if(null == result){
				String servicePackageKey = ParametersManager.getReferenceKey(EntityType.ServicePackage, account.getServicePackageId());
				OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(account.getId(), EntityType.Account, new Integer[] {VariablesCategories.Permissions.getValue()}, new String[] {servicePackageKey});
				if(variablesResult.hasError()){
					result = variablesResult.toErrorResult();
				} else {
					permissions = variablesResult.getValue();
				}
			}
			
			if(null == result){
				IAccess makePaymentAccess = permissions.get(PermissionsKeys.MakePayment.getValue()).getActual();
				if(makePaymentAccess.getValue() == AccessValue.Denied){
					result = new OperationResult<>(ErrorCode.NotPermitted);
				}
			}
			
			IDirectPaymentResponse transactionResponse = null;
			if(null == result && makeFundAmount > 0){
			
				final String creditCardNumber = input.getString("creditCardNumber");
				final String last4Digits = creditCardNumber.length() > 4 ? StringUtils.right(creditCardNumber, 4) : "";
				
				PayOperationResult<IPayActionResponse> actionResult = makeFund(
						input, timestamp, creditCardNumber, last4Digits,
						geoCountryCode, userId, account.getId(), sessionId, makeFundAmount,
						stateCode, postalCode, lastName, firstName, countryCode,
						city, address1, address2);
				
				if(actionResult.hasError()){
					result = actionResult;
					
				} else {
					transactionResponse = (IDirectPaymentResponse) actionResult.getValue();
				}
			}
			
			Long chargePayTransactionId = null;
			if(null == result && null != chargesIds){
				BaseOperationResult chargePayResult = payCharges(chargesIds,
						geoCountryCode, userId, account.getId(), sessionId,
						ProductsManager.getCurrentProduct().getGuid());
				if(chargePayResult.hasError()){
					result = chargePayResult;
				} else {
					chargePayTransactionId = chargePayResult.getTransactionId();
				}
			}
			
			if(null == result && isAddressChanged){
				Integer stateId = JSONHelper.optInt(input, "stateId");
				int countryId = input.getInt("countryId");
				setBusinessAddress(account.getId(), stateId, postalCode, lastName, firstName, countryId, city, address1, address2, null, null);
			}
			
			if(null == result){
				output = new JSONObject();
				if(null != transactionResponse){
					output.put("fundTransactionId", transactionResponse.getAccountOperationId());
				}
				output.put("chargePayTransactionId", chargePayTransactionId);
			} else {
				output = result.toJson();
			}
			
		} catch (Throwable t) {
			UUID errorId = logger.error(t,"directPayment() : Unexpected error occured.");
			output = BaseOperationResult.toJsonGeneralError(t, errorId);
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

	public JSONObject payCharge(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException{
		JSONObject output = null;
		IOperationResult result = null;
		final String ipAddress = getContext().getClientIp();
		final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
		final long chargeId = input.getLong("chargeId");
		long userId = 0;
		long accountId = 0;
		IUser user = null;
		String sessionId = null;
		
		OperationResult<IUser> userResult = getLoggedInUser();
		if(userResult.hasError()){
			result = userResult;
		} else {
			user = userResult.getValue();
			userId = user.getUserId();
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
			if(!account.getOwnerId().equals(user.getUserId())){
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		if(null == result){
			accountId = account.getId();
			sessionId = getContext().getSession().getSessionId();
		}
		
		ICharge charge = null;
		if(null == result){
			// get Invoice
			OperationResult<ICharge> chargeResult = ChargesManager.getCharge(chargeId, accountId);
			if(chargeResult.hasError()){
				result = chargeResult;
			} else {
				charge = chargeResult.getValue();
			}
		}
		
		//ChargeStatus chargeStatus = null;
		if(null == result){
			if(charge.getStatus() == ChargeStatus.Unpaid){
				if(charge.getAmountToFund() > 0){
					output = new JSONObject();
					output.put("chargeId", charge.getId());
					output.put("amountToFund", charge.getAmountToFund());
					output.put("amountDue", charge.getAmount());
					output.put("balance", charge.getBalance());
					output.put("error", ErrorCode.NoEnoughFunds);
				} else {
					OperationResult<ChargeStatus> payChargeResult = payCharge(chargeId,
							geoCountryCode, userId, accountId, sessionId,
							charge.getAmount(), ProductsManager.getCurrentProduct().getGuid());
					
					if(payChargeResult.hasError()){
						result = payChargeResult;
					} else {
						//chargeStatus = payChargeResult.getValue();
						output = new JSONObject();
						output.put("transactionId", payChargeResult.getTransactionId());
					}
				}
			} else {
				UUID errorId = logger.error("payCharge: Received pay action for Charge #%s with status '%s'", charge.getId(), charge.getStatus());
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
			final String sessionId, UUID productGuid){
		
		BaseOperationResult result = null;
		
		for (Long chargeId : chargesIds) {
			OperationResult<ChargeStatus> payChargeResult = payCharge(chargeId, geoCountryCode, userId, accountId, sessionId, productGuid);
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
			final String geoCountryCode, final long userId, final long accountId,
			final String sessionId, final UUID productGuid){
		
		OperationResult<ChargeStatus> result = null;
		ICharge charge = null;
		if(null == result){
			OperationResult<ICharge> chargeResult = ChargesManager.getCharge(chargeId, accountId);
			if(chargeResult.hasError()){
				result = chargeResult.toErrorResult();
			} else {
				charge = chargeResult.getValue();
			}
		}
		
		if(null == result){
			result = payCharge(chargeId, geoCountryCode, userId, accountId, sessionId, charge.getAmount(), productGuid);
		}
		
		return result;
	}
	
	private OperationResult<ChargeStatus> payCharge(final long chargeId,
			final String geoCountryCode, final long userId, final long accountId,
			final String sessionId, final double amount, final UUID productGuid) {
		OperationResult<ChargeStatus> result = null;
		
		if(null == result){
			
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
		}
		return result;
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
				return USD_CURRENCY_CODE;
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
				return address2;
			}
			
			@Override
			public String getAddress1() {
				return address1;
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
	
	public JSONObject getPaymentDetails(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException{
		
		IOperationResult result = null;
		IAccount account = null;
		IAccountBusinessDetails businessDetails = null;
		boolean hasBusinessAddress = false;
		Long userId = null;
		JSONObject output;
		
		OperationResult<Long> userResult = getUserId();
		if(userResult.hasError()){
			result = userResult;
		} else {
			userId = userResult.getValue();
		}
		
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			if(!account.getOwnerId().equals(userId)){
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		if(null == result){
			
			OperationResult<IAccountBusinessDetails> businessDetailsResult = AccountsManager.getAccountBusinessDetails(account.getId());
			if(businessDetailsResult.hasError()){
				result = businessDetailsResult;
			} else {
				businessDetails = businessDetailsResult.getValue();
				hasBusinessAddress = businessDetails.hasBusinessAddress();
			}
		}
		
		IUserDetails userDetails = null;
		if(null == result && !hasBusinessAddress){
			OperationResult<IUserDetails> userDetailsResult = UsersManager.getUserDetails(userId);
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
	
	public JSONObject setExpressCheckout(JSONObject input) throws IOException, NullPointerException, ExecutionException{
		
		
		
		IOperationResult result = null;
		IAccount account = null;
		IUser user = null;
		JSONObject output;
		String clientIp = null;
		String geoCountryCode = null;
		double makeFundAmount = 0;
		List<Long> chargesIds = null;
		HttpClientSession session = null;
		
		OperationResult<IUser> userResult = getLoggedInUser();
		if(userResult.hasError()){
			result = userResult;
		} else {
			user = userResult.getValue();;
		}
		
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			if(!account.getOwnerId().equals(user.getUserId())){
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		if(null == result){
			session = getContext().getSession();
			clientIp = getContext().getClientIp();
			geoCountryCode = GeoIpManager.getInstance().getCountryCode(clientIp);
			
			makeFundAmount = input.optDouble("amount");
			chargesIds = JSONHelper.toListOfLong(JSONHelper.optJsonArray(input, "charges"));
		}
		
		HashMap<String, IVariableSet> permissions = null;
		if(null == result){
			String servicePackageKey = ParametersManager.getReferenceKey(EntityType.ServicePackage, account.getServicePackageId());
			OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(account.getId(), EntityType.Account, new Integer[] {VariablesCategories.Permissions.getValue()}, new String[] {servicePackageKey});
			if(variablesResult.hasError()){
				result = variablesResult.toErrorResult();
			} else {
				permissions = variablesResult.getValue();
			}
		}
		
		if(null == result){
			IAccess makePaymentAccess = permissions.get(PermissionsKeys.MakePayment.getValue()).getActual();
			if(makePaymentAccess.getValue() == AccessValue.Denied){
				result = new OperationResult<>(ErrorCode.NotPermitted);
			}
		}
		
		ISetExpressCheckoutResponse setExpressCheckoutResponse = null;
		if(null == result && makeFundAmount > 0) {
			ISetExpressCheckoutRequest request = createSetExpressCheckoutRequest(user.getUserId(), account.getId(), session.getSessionId(), geoCountryCode, clientIp, user.getEmail(), ProductsManager.getCurrentProduct().getId(), "Inqwise services", makeFundAmount, "/en-us/make-payment", "/en-us/account", chargesIds);
			
			IPayAction action = PayProcessorsFactory.getInstance().getProcessor(PayActionTypes.SetExpressCheckout)
					.getAction(PayActionTypes.SetExpressCheckout);
			action.setRequest(request);
			PayOperationResult<IPayActionResponse> actionResult = action.process();
			
			if(actionResult.hasError()){
				result = actionResult;
			} else {
				setExpressCheckoutResponse = (ISetExpressCheckoutResponse) actionResult.getValue();
			}
		}
		
		if(null == result){
			output = new JSONObject();
			output.put("redirectUrl", setExpressCheckoutResponse.getExpressCheckoutUrl());
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	private ISetExpressCheckoutRequest createSetExpressCheckoutRequest(final Long userId, final long accountId, final String sessionId, final String countryCode, final String clientIp, final String buyerEmail, final int sourceId, final String itemName, final double itemAmount, final String returnPath, final String cancelPath, final List<Long> chargeIds) {
		final Date timestamp = new Date();
		final ArrayList<IPaymentDetailsItem> paymentItems = new ArrayList<IPaymentDetailsItem>();
		paymentItems.add(new IPaymentDetailsItem() {
			
			@Override
			public Double getSalesTax() {
				return 0d;
			}
			
			@Override
			public Integer getItemQuantity() {
				return 1;
			}
			
			@Override
			public String getItemName() {
				return itemName;
			}
			
			@Override
			public String getItemDescription() {
				return null;
			}
			
			@Override
			public double getItemAmount() {
				return itemAmount;
			}
			
			@Override
			public double getAmount() {
				return itemAmount;
			}
		});
		
		ISetExpressCheckoutRequest request = new ISetExpressCheckoutRequest() {
			
			@Override
			public Long getUserId() {
				return userId;
			}
			
			@Override
			public Date getTimeStamp() {
				return timestamp;
			}
			
			@Override
			public int getSourceId() {
				return sourceId;
			}
			
			@Override
			public Long getBackofficeUserId() {
				return null;
			}
			
			@Override
			public Long getAccountId() {
				return accountId;
			}
			
			@Override
			public String getSessionId() {
				return sessionId;
			}
			
			@Override
			public String getReturnUrl() {
				return ApplicationConfiguration.Opinion.getSecureUrl() + returnPath;
			}
			
			@Override
			public List<IPaymentDetailsItem> getPaymentDetailsList() {
				return paymentItems;
			}
			
			@Override
			public String getOrderDescription() {
				return null;
			}
			
			@Override
			public String getGeoCountryCode() {
				return countryCode;
			}
			
			@Override
			public String getDetails() {
				return null;
			}
			
			@Override
			public String getCurrencyCode() {
				return USD_CURRENCY_CODE;
			}
			
			@Override
			public String getClientIp() {
				return clientIp;
			}
			
			@Override
			public String getCancelUrl() {
				return ApplicationConfiguration.Opinion.getSecureUrl() + cancelPath;
			}
			
			@Override
			public String getBuyerMail() {
				return buyerEmail;
			}
			
			@Override
			public String getBillingAgreementText() {
				return null;
			}
			
			@Override
			public IBillingAgreement getBillingAgreement() {
				return null;
			}

			@Override
			public String getExpressCheckoutBaseUrl() {
				return ApplicationConfiguration.Api.getSecureUrl() + "/pay/expresscheckout/";
			}

			@Override
			public List<Long> getChargeIds() {
				return chargeIds;
			}
		};
		return request;
	}
}
