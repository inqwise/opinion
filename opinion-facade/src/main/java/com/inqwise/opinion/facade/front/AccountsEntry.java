package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.opinions.IOpinion.JsonNames;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.GeoIpManager;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.InvoiceModel;
import com.inqwise.opinion.library.common.accounts.AccountOperationModel;
import com.inqwise.opinion.library.common.accounts.AccountOperationsReferenceType;
import com.inqwise.opinion.library.common.accounts.AccountsOperationsType;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountDetails;
import com.inqwise.opinion.library.common.accounts.IAccountDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountOperation;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.pay.IChargePayRequest;
import com.inqwise.opinion.library.common.pay.InvoiceStatus;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.AccountsOperationsManager;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.managers.InvoicesManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.ServicePackagesManager;

public class AccountsEntry extends Entry implements IPostmasterObject {
	public AccountsEntry(IPostmasterContext context) {
		super(context);
	}

	static ApplicationLog logger = ApplicationLog.getLogger(AccountsEntry.class);
	static Format mdyhmsFormatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	
	public JSONObject getAccountDetails(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException{
		
		IOperationResult result = null;
		
		IUser user = null;
		OperationResult<IUser> userResult = getLoggedInUser();
		if(userResult.hasError()){
			result = userResult;
		} else {
			user = userResult.getValue();
		}
		
		IAccount account = null;
		IAccountDetails details = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result) {
			OperationResult<IAccountDetails> accountResult = AccountsManager.getAccountDetails(account.getId());
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				details = accountResult.getValue();
			}
		}
		
		if(null == result) {
			return new JSONObject()
			.put("accountId", details.getId())
			.put("servicePackageId", details.getServicePackageId())
			.put("accountName", details.getName())
			.put("ownerId", details.getOwnerId())
			.put("timezoneId", details.getTimezoneId())
			.put("insertDate", mdyhmsFormatter.format(account.addDateOffset(details.getInsertDate())))
			.put("balance", details.getBalance())
			.put("isActive", details.isActive())
			.put("isOwner", (0 == details.getOwnerId().compareTo(user.getUserId())));
		} else {
			return result.toJson();
		}
		
	}
	
	public JSONObject setAccountDetails(final JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException{
		JSONObject output;
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
			if(!account.getOwnerId().equals(user.getUserId())){
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		if(null == result){
			final long accountId = account.getId();
			
			IAccountDetailsChangeRequest request = new IAccountDetailsChangeRequest() {
				
				@Override
				public Boolean isActive() {
					return null;
				}
				
				@Override
				public Integer getTimezoneId() {
					return JSONHelper.optInt(input, "timezoneId");
				}
				
				@Override
				public Long getOwnerId() {
					return null;
				}
				
				@Override
				public String getName() {
					return JSONHelper.optString(input, "accountName");
				}
				
				@Override
				public long getId() {
					return accountId;
				}
				
				@Override
				public String getComments() {
					return JSONHelper.optStringTrim(input, "comments");
				}

				@Override
				public IDepositBounds getDebositBounds() {
					return null;
				}
			};
			result = AccountsManager.setAccountDetails(request);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject getTransactions(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException{
		JSONObject output;
		IOperationResult result = null;
		IAccount account = null;
		int top = JSONHelper.optInt(input, "top", 100);
		Long referenceId = JSONHelper.optLong(input, "billId");
		Integer referenceTypeId = JSONHelper.optInt(input, "referenceTypeId");
		
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		Date fromDate = null;
		Date toDate = null;
		List<Integer> accountOperationTypeIds = null;
		String groupBy = null;
		
		if(null == result){
			fromDate = account.removeDateOffset(JSONHelper.optDate(input, "fromDate"));
			toDate = account.removeDateOffset(JSONHelper.optDate(input, "toDate"));
			accountOperationTypeIds = JSONHelper.toListOfInt(JSONHelper.optJsonArray(input, "transactionTypes"));
			groupBy = JSONHelper.optString(input, "groupBy");
			if(null != toDate){
				toDate = DateConverter.shiftToTheEndOfTheDay(toDate);
			}
			
			if(null != referenceId && null == referenceTypeId){
				referenceTypeId = AccountOperationsReferenceType.Charges.getValue();
			}
			
			result = BaseOperationResult.validateWithoutLog(null == groupBy || groupBy.equalsIgnoreCase("month"), ErrorCode.ArgumentWrong, "groupBy");
		}
		
		if (null == result) {
			List<AccountOperationModel> accountOperationList = AccountsOperationsManager.getAccountOperations(top, account.getId(),accountOperationTypeIds, referenceId,referenceTypeId, fromDate, toDate, true);
			JSONArray ja = new JSONArray();
			List<InvoiceModel> invoiceList = null;
			TreeMap<Date, JSONObject> groupsMap = null;
			BigDecimal groupDebit = null;
			BigDecimal groupCredit = null;
			if (null != groupBy) {
				groupDebit = new BigDecimal(0.0);
				groupCredit = new BigDecimal(0.0);
				invoiceList = InvoicesManager.getInvoices(top, account.getId(),InvoiceStatus.Open.getValue(), false);
				groupsMap = new TreeMap<>();
			}
			JSONObject lastFundJo = null;
			BigDecimal totalDebit = new BigDecimal(0.0);
			BigDecimal totalCredit = new BigDecimal(0.0);
			for (var operationModel : accountOperationList){
				JSONObject jTransaction = new JSONObject();

				jTransaction.put("transactionId", operationModel.getId());
				if(null != operationModel.getType()) {
					jTransaction.put("typeId", operationModel.getType().getValue());
				}
				Double amount = operationModel.getAmount();
				if (null != amount) {
					jTransaction.put("amount", amount);
					if (amount < 0) {
						totalDebit = totalDebit.subtract(BigDecimal.valueOf(amount));
						jTransaction.put("debit", -1.0 * amount);
					} else {
						jTransaction.put("debit", 0);
					}

					if (amount > 0) {
						totalCredit = totalCredit.add(BigDecimal.valueOf(amount));
						jTransaction.put("credit", amount);

					} else {
						jTransaction.put("credit", 0);
					}
					jTransaction.put("debit", (amount < 0 ? -1.0 * amount : 0));
					jTransaction.put("credit", (amount > 0 ? amount : 0));
				}
				Double balance = operationModel.getBalance();
				jTransaction.put("balance", balance);
				Date modifyDate = account.addDateOffset(operationModel.getModifyDate());
				jTransaction.put("modifyDate",	JSONHelper.getDateFormat(modifyDate, "MMM dd, yyyy"));
				Long accountOperationReferenceId = operationModel.getReferenceId();
				jTransaction.put("referenceId", accountOperationReferenceId);
				jTransaction.put("comments", operationModel.getComments());
				jTransaction.put("creditCard",operationModel.getCreditCardNumber());
				if (null != operationModel.getCreditCardType()) {
					jTransaction.put("creditCardTypeId", operationModel.getCreditCardType().getValue());
				}
				jTransaction.put("chargeDescription",operationModel.getChargeDescription());

				if (null == groupsMap) {
					ja.put(jTransaction);
				} else {
					Date groupDate = DateConverter.shiftToTheEndOfTheMonth(modifyDate);
					JSONObject jGroup = groupsMap.get(groupDate);
					if (null == jGroup) {
						jGroup = createJGroup(groupDate);
						groupDebit = new BigDecimal(0.0);
						groupCredit = new BigDecimal(0.0);
						groupsMap.put(groupDate, jGroup);
					}
					
					if (amount < 0) {
						groupDebit = groupDebit.subtract(BigDecimal.valueOf(amount));
					} else if (amount > 0) {
						groupCredit = groupCredit.add(BigDecimal.valueOf(amount));
					} 
					
					jGroup.put("debit", groupDebit.setScale(2, RoundingMode.HALF_UP));
					jGroup.put("credit", groupCredit.setScale(2, RoundingMode.HALF_UP));
					if(!jGroup.has("balance")){
						jGroup.put("balance", balance);
					}
					
					JSONArray jTransactions = jGroup
							.optJSONArray("transactions");
					if (null == jTransactions) {
						jTransactions = new JSONArray();
						jGroup.put("transactions", jTransactions);
					}
					jTransactions.put(jTransaction);
				}

				if (null == lastFundJo
						&& AccountsOperationsType.Fund == operationModel.getType()) {
					lastFundJo = jTransaction;
				}
			}
			
			if(null != groupBy){
				for (var invoiceModel : invoiceList) {
					JSONObject jInvoice = new JSONObject();
					jInvoice.put("invoiceId",invoiceModel.getInvoiceId());

					Date groupDate = DateConverter
							.shiftToTheEndOfTheMonth(account.addDateOffset((Date)invoiceModel.getInvoiceToDate()));
					JSONObject jGroup = groupsMap.get(groupDate);
					if (null == jGroup) {
						jGroup = createJGroup(groupDate);
						jGroup.put("invoices", new JSONArray());
						groupsMap.put(groupDate, jGroup);
						jGroup.put("balance", JSONObject.NULL);
						jGroup.put("debit", 0.0);
						jGroup.put("credit", 0.0);
						jGroup.put("transactions", new JSONArray());
					}
					
					JSONArray jInvoices = jGroup.optJSONArray("invoices");
					if(null == jInvoices){
						jInvoices = new JSONArray();
						jGroup.put("invoices", jInvoices);
					}
					jInvoices.put(jInvoice);
				}
			}
			
			output = new JSONObject();
			if (null != groupBy) {
				for (JSONObject jGroup : groupsMap.descendingMap().values()) {
					ja.put(jGroup);
				}
			}
			output.put("list", ja);
			if (null != lastFundJo) {
				output.put("lastFundTransaction", lastFundJo);
			}
			output.put("totalDebit",totalDebit.setScale(2, RoundingMode.HALF_UP));
			output.put("totalCredit",totalCredit.setScale(2, RoundingMode.HALF_UP));
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	private JSONObject createJGroup(Date groupDate) throws JSONException {
		JSONObject jGroup = new JSONObject();
		
		Date fromDate = DateConverter.shiftToTheStartOfTheMonth(groupDate);
		Date today = new Date();
		Date toDate;
		if(groupDate.after(today)){
			toDate = DateConverter.shiftToTheEndOfTheDay(today);
		} else {
			toDate = groupDate;
		}
		
		jGroup.put("fromDate", JSONHelper.getDateFormat(fromDate, "MMM dd, yyyy"));
		jGroup.put("toDate", JSONHelper.getDateFormat(toDate, "MMM dd, yyyy"));
		return jGroup;
	}

	public JSONObject upgradePackage(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException{
		
		JSONObject output;
		IOperationResult result = null;
		IAccount account = null;
		IUser user = null;
		int countOfMonths = 0;
		 
		OperationResult<IUser> userResult = getLoggedInUser();
		if(userResult.hasError()){
			result = userResult;
		} else {
			user = userResult.getValue();
		}
		
		IServicePackage servicePackage = null;
		IProduct product = null;
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
		
		if (null == result){
			int servicePackageId = input.getInt("packageId");
			countOfMonths = JSONHelper.optInt(input, "countOfMonths", 1);
			product = ProductsManager.getCurrentProduct();
			servicePackage = product.getServicePackageOrNull(servicePackageId);
			if(null == servicePackage){
				logger.warn("upgradePackage: ServicePackage #%s not exist in Product #%s", servicePackageId, product.getId());
				result = new BaseOperationResult(ErrorCode.ArgumentOutOfRange, "packageId");
			}
		}
		
		ICharge charge = null;
		if(null == result){
			OperationResult<ICharge> chargeResult = ServicePackagesManager.createCharge(servicePackage, countOfMonths, account, user.getUserId(), true);
			if(chargeResult.hasError()){
				result = chargeResult;
			} else {
				charge = chargeResult.getValue();
			}
		}
		boolean noEnoughFunds = false;
		Long transactionId = null;
		if(null == result){
			if(charge.getAmountToFund() > 0){
				noEnoughFunds = true;
			} else {
				String ipAddress = getContext().getClientIp();
				String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
				String sessionId = getContext().getSession().getSessionId();
				IChargePayRequest payChargeRequest = createPayChargeRequest(user.getUserId(), product.getId(), sessionId,
						geoCountryCode, ipAddress, charge.getId(), charge.getAmount(), account.getId());
				OperationResult<ChargeStatus> payChargeResult = ChargesManager.payCharge(payChargeRequest, true);
				if(payChargeResult.hasError()){
					result = payChargeResult;
				} else {
					transactionId = payChargeResult.getTransactionId();
				}
			}
		}
		
		if(null == result){
			if(noEnoughFunds){
				output = BaseOperationResult.toJsonError(ErrorCode.NoEnoughFunds);
				output.put("amountToFund", charge.getAmountToFund());
				output.put("amount", charge.getAmount());
				output.put("balance", charge.getBalance());
			} else {
				output = new JSONObject();
			}
		} else {
			output = result.toJson();
		}
		
		if(null != charge){
			output.put("chargeId", charge.getId());
		}
		
		if(null != transactionId){
			output.put("transactionId", transactionId);
		}
		
		return output;
	}

	private IChargePayRequest createPayChargeRequest(final long userId, final int productId,
			final String sessionId, final String geoCountryCode, final String clientIp,
			final long chargeId, final double amount, final long accountId) {
		IChargePayRequest payChargeRequest = new IChargePayRequest() {
			
			@Override
			public Long getUserId() {
				return userId;
			}
			
			@Override
			public int getSourceId() {
				return productId;
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
				return clientIp;
			}
			
			@Override
			public long getChargeId() {
				return chargeId;
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
		return payChargeRequest;
	}
	
	public JSONObject getBusinessDetails(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException{
		JSONObject output;
		
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
			if(!account.getOwnerId().equals(user.getUserId())){
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		IAccountBusinessDetails businessDetails = null;
		if(null == result){
			OperationResult<IAccountBusinessDetails> detailsResult = AccountsManager.getAccountBusinessDetails(account.getId());
			if(detailsResult.hasError()){
				result = detailsResult;
			} else {
				businessDetails = detailsResult.getValue();
			}
		}
		
		if(null == result){
			output = businessDetails.toJson();
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject updateBusinessDetails(final JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException{
		JSONObject output;
		
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
			if(!account.getOwnerId().equals(user.getUserId())){
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		if(null == result){
			final long accountId = account.getId();
			BaseOperationResult setResult =	AccountsManager.setAccountBusinessDetails(new IAccountBusinessDetailsChangeRequest() {
				
				@Override
				public Integer getStateId() {
					return JSONHelper.optInt(input, "stateId", null, 0);
				}
				
				@Override
				public String getPostalCode() {
					return JSONHelper.optString(input, "postalCode", null, "");
				}
				
				@Override
				public String getPhone1() {
					return JSONHelper.optString(input, "phone1", null, "");
				}
				
				@Override
				public String getLastName() {
					return JSONHelper.optString(input, "lastName", null, "");
				}
				
				@Override
				public long getId() {
					return accountId;
				}
				
				@Override
				public String getFirstName() {
					return JSONHelper.optString(input, "firstName", null, "");
				}
				
				@Override
				public int getCountryId() {
					return JSONHelper.optInt(input, "countryId");
				}
				
				@Override
				public String getCompanyName() {
					return JSONHelper.optString(input, "companyName", null, "");
				}
				
				@Override
				public String getCity() {
					return JSONHelper.optString(input, "city", null, "");
				}
				
				@Override
				public String getAddress2() {
					return JSONHelper.optString(input, "address2", null, "");
				}
				
				@Override
				public String getAddress1() {
					return JSONHelper.optString(input, "address1", null, "");
				}
			});
			
			if(setResult.hasError()){
				result = setResult;
			}
		}
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject updateAccountSettings(JSONObject input) throws NullPointerException, IOException, JSONException, ExecutionException {
		JSONObject output = null;
		BaseOperationResult result = null;
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("updateAccountSettings : Not Signed in.");
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
			if(!account.getOwnerId().equals(userId)){
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		if(null == result){
			Long accountId = account.getId();
			result = com.inqwise.opinion.managers.AccountsManager.updateWelcomeMessageFlag(accountId, input.optBoolean(JsonNames.SHOW_WELCOME_MESSAGE, false), userId);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}

		return output;
	}
	
	public JSONObject getTransaction(JSONObject input) throws NullPointerException, IOException, ExecutionException{
		JSONObject output;
		IOperationResult result = null;
		IAccount account = null;
		IUser user = null;
		IAccountOperation accountOperation = null;
				 
		OperationResult<IUser> userResult = getLoggedInUser();
		if(userResult.hasError()){
			result = userResult;
		} else {
			user = userResult.getValue();
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
			OperationResult<IAccountOperation> operationResult = AccountsOperationsManager.get(JSONHelper.optInt(input, "aoid"));
			if(operationResult.hasError()){
				result = operationResult;
			} else {
				accountOperation = operationResult.getValue();
			}
		}
		
		if(null == result) {
			output = new JSONObject();
			output.put(IAccountOperation.JsonNames.AMOUNT, accountOperation.getAmount().doubleValue());
		} else {
			output = result.toJson();
		}
		
		return output;
		
	}
	
}
