package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 

import com.inqwise.opinion.common.IOpinionAccount;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.infrastructure.common.BulkOperationResults;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.GeoIpManager;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.InvoiceModel;
import com.inqwise.opinion.library.common.accounts.AccountModel;
import com.inqwise.opinion.library.common.accounts.AccountOperationModel;
import com.inqwise.opinion.library.common.accounts.AccountOperationsReferenceType;
import com.inqwise.opinion.library.common.accounts.AccountsOperationsType;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountBillingSettingsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IAccountDetails;
import com.inqwise.opinion.library.common.accounts.IAccountDetailsChangeRequest;
import com.inqwise.opinion.library.common.accounts.IChangeBalanceRequest;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IVariable;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.pay.InvoiceStatus;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.AccountsOperationsManager;
import com.inqwise.opinion.library.managers.InvoicesManager;
import com.inqwise.opinion.library.managers.ParametersManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.ServicePackagesManager;

public class AccountsEntry extends Entry {

	static ApplicationLog logger = ApplicationLog.getLogger(AccountsEntry.class);
	static Format mdyhmsFormatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	final static DateFormat resetConfirmationCodeDateFormat = new SimpleDateFormat("yy-MM-dd");
	
	protected AccountsEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getAccounts(JSONObject input) throws JSONException {
		JSONObject output;
		int top = JSONHelper.optInt(input, "top", 100);
		int productId = JSONHelper.optInt(input, "productId", 1);
		Long userId = JSONHelper.optLong(input, "userId");
		boolean includeNonActive = JSONHelper.optBoolean(input, "includeNonActive", true);
		Date fromDate = JSONHelper.optDate(input, "fromDate");
		Date toDate = JSONHelper.optDate(input, "toDate");
		
		List<AccountModel> accountList = AccountsManager.getAccounts(userId, productId, top, includeNonActive, fromDate, toDate, null);
		JSONArray ja = new JSONArray();
		
		if(accountList.size() > 0){
			output = new JSONObject();
			JSONArray list = new JSONArray();
			for(var accountModel : accountList){
				JSONObject item = new JSONObject();
				item.put(IAccount.JsonNames.ACCOUNT_ID, accountModel.getAccountId());
				item.put(IAccount.JsonNames.SERVICE_PACKAGE_NAME, accountModel.getServicePackageName());
				item.put(IAccount.JsonNames.ACCOUNT_NAME, accountModel.getAccountName());
				item.put(IAccount.JsonNames.OWNER_ID, accountModel.getOwnerId());
				item.put(IAccount.JsonNames.INSERT_DATE, mdyhmsFormatter.format(accountModel.getInsertDate()));
				item.put(IAccount.JsonNames.IS_ACTIVE, accountModel.getIsActive());
				
				list.put(item);
			}
			
			output.put("list", list).put("top", top).put("productId", productId);
		} else {
			output = BaseOperationResult.toJsonError(ErrorCode.NoResults);
		}
		
		return output;
	}
	
	public JSONObject getAccountDetails(JSONObject input) throws JSONException{
		long accountId = input.optLong("accountId");
		Integer servicePackageId = JSONHelper.optInt(input, "packageId");
		JSONObject output = null;
		
		BaseOperationResult result = null;
		OperationResult<IAccountDetails> accountResult = AccountsManager.getAccountDetails(accountId);
		IAccountDetails account = null;
		if(accountResult.hasError()){
			result = accountResult;
		} else {
			account = accountResult.getValue();
			output = new JSONObject()
			.put("accountId", account.getId())
			.put("servicePackageId", account.getServicePackageId())
			.put("servicePackageExpiryDate", JSONHelper.getDateFormat(account.getServicePackageExpiryDate(), "MMM dd, yyyy"))
			.put("accountName", account.getName())
			.put("ownerId", account.getOwnerId())
			.put("ownerUserName", account.getOwnerUserName())
			.put("insertDate", mdyhmsFormatter.format(account.getInsertDate()))
			.put("isActive", account.isActive())
			.put("balance", account.getBalance())
			.put("comments", account.getComments())
			.put("timezoneId", account.getTimezoneId());
			
			if(null == servicePackageId){
				servicePackageId = account.getServicePackageId();
			}
		}
				
		return output;
	}
	
	public JSONObject getTransactions(JSONObject input) throws IOException, JSONException{
		JSONObject output;
		IOperationResult result = null;
		long accountId = input.getLong("accountId");
		int top = JSONHelper.optInt(input, "top", 100);
		Long referenceId = JSONHelper.optLong(input, "billId");
		Integer referenceTypeId = JSONHelper.optInt(input, "referenceTypeId");
		Date fromDate = JSONHelper.optDate(input, "fromDate");
		Date toDate = JSONHelper.optDate(input, "toDate");
		List<Integer> accountOperationTypeIds = JSONHelper.toListOfInt(JSONHelper.optJsonArray(input, "transactionTypes"));
		String groupBy = JSONHelper.optString(input, "groupBy");
		if(null != toDate){
			toDate = DateConverter.shiftToTheEndOfTheDay(toDate);
		}
		
		if(null != referenceId && null == referenceTypeId){
			referenceTypeId = AccountOperationsReferenceType.Charges.getValue();
		}
		
		result = BaseOperationResult.validateWithoutLog(null == groupBy || groupBy.equalsIgnoreCase("month"), ErrorCode.ArgumentWrong, "groupBy");
		
		if (null == result) {
			List<AccountOperationModel> accountOperationList = AccountsOperationsManager.getAccountOperations(top, accountId,accountOperationTypeIds, referenceId,referenceTypeId, fromDate, toDate, true);
			JSONArray ja = new JSONArray();
			TreeMap<Date, JSONObject> groupsMap = null;
			BigDecimal groupDebit = null;
			BigDecimal groupCredit = null;
			List<InvoiceModel> invoiceList = null;
			if (null != groupBy) {
				groupDebit = new BigDecimal(0.0);
				groupCredit = new BigDecimal(0.0);
				invoiceList = InvoicesManager.getInvoices(top, accountId,InvoiceStatus.Open.getValue(), false);
				groupsMap = new TreeMap<>();
			}
		
			JSONObject lastFundJo = null;
			BigDecimal totalDebit = BigDecimal.ZERO;
			BigDecimal totalCredit = BigDecimal.ZERO;
		
			for(var accountOperationModel : accountOperationList){
				JSONObject jTransaction = new JSONObject();
				jTransaction.put("transactionId", accountOperationModel.getId());
				jTransaction.put("typeId", accountOperationModel.getType().getValue());
				jTransaction.put("userId", accountOperationModel.getUserId());
				Double amount = accountOperationModel.getAmount();
				if(null !=  amount){
					jTransaction.put("amount", amount);
					if (amount < 0) {
						totalDebit = totalDebit.subtract(BigDecimal.valueOf(amount));
						jTransaction.put("debit", -1.0 * amount);
					} else {
						jTransaction.put("debit", 0);
					}
					
					if (amount > 0){
						totalCredit = totalCredit.add(BigDecimal.valueOf(amount));
						jTransaction.put("credit", amount);
						
					} else {
						jTransaction.put("credit", 0);
					}
				}
				Double balance = accountOperationModel.getBalance();
				jTransaction.put("balance", balance);
				Date modifyDate = accountOperationModel.getModifyDate();
				jTransaction.put("modifyDate",	JSONHelper.getDateFormat(modifyDate, "MMM dd, yyyy"));
				Long accountOperationReferenceId = accountOperationModel.getReferenceId();
				jTransaction.put("referenceId", accountOperationReferenceId);
				jTransaction.put("comments", accountOperationModel.getComments());
				jTransaction.put("creditCard",accountOperationModel.getCreditCardNumber());
				jTransaction.put("creditCardTypeId",accountOperationModel.getCreditCardType());
				jTransaction.put("chargeDescription",accountOperationModel.getChargeDescription());

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
						&& accountOperationModel.getType() == AccountsOperationsType.Fund) {
					lastFundJo = jTransaction;
				}
			}
			if(null != invoiceList){
				for(var invoiceModel : invoiceList) {
					JSONObject jInvoice = new JSONObject();
					jInvoice.put("invoiceId",invoiceModel.getInvoiceId());
	
					Date groupDate = DateConverter.shiftToTheEndOfTheMonth(invoiceModel.getInvoiceToDate());
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

	public JSONObject credit(final JSONObject input) throws JSONException, IOException{
		
		JSONObject output;
		BaseOperationResult result = null;
		final double amount = input.getDouble("amount");
		final long accountId = input.getLong("accountId");
		final String ipAddress = getContext().getClientIp();
		final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
		final Long backofficeUserId = getContext().getUserId().getValue();
		final String comments = input.getString("comments");
		
		if(amount <= 0){
			result = new BaseOperationResult(ErrorCode.InvalidAmount);
		}
		
		if(null == result){
		
			IChangeBalanceRequest request = new IChangeBalanceRequest() {
				
				@Override
				public UUID getSourceGuid() {
					return ProductsManager.getCurrentProduct().getGuid();
				}
				
				@Override
				public String getSessionId() {
					return null;
				}
				
				@Override
				public String getGeoCountryCode() {
					return geoCountryCode;
				}
				
				@Override
				public String getComments() {
					return comments;
				}
				
				@Override
				public String getClientIp() {
					return getContext().getClientIp();
				}
				
				@Override
				public Long getBackofficeUserId() {
					return backofficeUserId;
				}
				
				@Override
				public double getAmount() {
					return amount;
				}
				
				@Override
				public int getAccountOperationTypeId() {
					return AccountsOperationsType.Credit.getValue();
				}
				
				@Override
				public long getAccountId() {
					return accountId;
				}
			};
			result = AccountsManager.changeBalance(request);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = new JSONObject().put("transactionId", result.getTransactionId());
		}
		
		return output;
	}
	
	public JSONObject debit(final JSONObject input) throws JSONException, IOException{
		
		JSONObject output;
		BaseOperationResult result = null;
		final double amount = input.getDouble("amount");
		final long accountId = input.getLong("accountId");
		final String ipAddress = getContext().getClientIp();
		final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
		final Long backofficeUserId = getContext().getUserId().getValue();
		final String comments = input.getString("comments");
		
		if(amount <= 0){
			result = new BaseOperationResult(ErrorCode.InvalidAmount);
		}
		
		if(null == result){
			IChangeBalanceRequest request = new IChangeBalanceRequest() {
				
				@Override
				public UUID getSourceGuid() {
					return ProductsManager.getCurrentProduct().getGuid();
				}
				
				@Override
				public String getSessionId() {
					return null;
				}
				
				@Override
				public String getGeoCountryCode() {
					return geoCountryCode;
				}
				
				@Override
				public String getComments() {
					return comments;
				}
				
				@Override
				public String getClientIp() {
					return getContext().getClientIp();
				}
				
				@Override
				public Long getBackofficeUserId() {
					return backofficeUserId;
				}
				
				@Override
				public double getAmount() {
					return -1 * amount;
				}
				
				@Override
				public int getAccountOperationTypeId() {
					return AccountsOperationsType.Debit.getValue();
				}
				
				@Override
				public long getAccountId() {
					return accountId;
				}
			};
			result = AccountsManager.changeBalance(request);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = new JSONObject().put("transactionId", result.getTransactionId());
		}
		
		return output;
	}
	
	public JSONObject updateServicePackage(JSONObject input) throws JSONException{
		
		JSONObject output;
		IOperationResult result = null;
		long accountId = input.getLong("accountId");
		int servicePackageId = input.getInt("packageId");
		boolean isCalculateExpiryDate = JSONHelper.optBoolean(input, "calculateExpiryDate", false);
		Date servicePackageExpiryDate = JSONHelper.optDate(input, "expiryDate");
		Integer maxUsers = JSONHelper.optInt(input, "accountUsers");
		
		IServicePackage servicePackage = null;
		if(null == result && (isCalculateExpiryDate || null == maxUsers)){
			OperationResult<IServicePackage> servicePackageResult = ServicePackagesManager.getServicePackage(servicePackageId, null);
			if(servicePackageResult.hasError()){
				result = servicePackageResult;
			} else {
				servicePackage = servicePackageResult.getValue();
				if(isCalculateExpiryDate){
					servicePackageExpiryDate = servicePackage.calculateExpiryDate();
				}
				if(null == maxUsers){
					maxUsers = servicePackage.getMaxAccountUsers();
				}
			}
		}
		
		if(null == result){
			IOperationResult updateAccountResult = AccountsManager.updateServicePackage(accountId, servicePackageId, servicePackageExpiryDate, maxUsers);
			if(updateAccountResult.hasError()){
				result = updateAccountResult;
			}
		}
		
		if(null == result){
			if(isCalculateExpiryDate){
				output = new JSONObject();
				output.put("expiryDate", JSONHelper.getDateFormat(servicePackageExpiryDate, "MMM dd, yyyy"));
			} else {
				output = BaseOperationResult.JsonOk;
			}
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getBusinessDetails(JSONObject input) throws JSONException, IOException{
		JSONObject output;
		
		IOperationResult result = null;
		long accountId = input.getLong("accountId");
		
		result = BaseOperationResult.validateWithoutLog(accountId > 0, ErrorCode.ArgumentMandatory, "accountId");
		
		IAccountBusinessDetails businessDetails = null;
		if(null == result){
			OperationResult<IAccountBusinessDetails> detailsResult = AccountsManager.getAccountBusinessDetails(accountId);
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
	
	public JSONObject updateBusinessDetails(final JSONObject input) throws JSONException{
		JSONObject output;
		
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
				return JSONHelper.optInt(input, "accountId");
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
			output = setResult.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject manualRefund(JSONObject input) throws JSONException, IOException{
		JSONObject output;
		BaseOperationResult result = null;
		final double amount = input.getDouble("amount");
		final long accountId = input.getLong("accountId");
		final String ipAddress = getContext().getClientIp();
		final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
		final Long backofficeUserId = getContext().getUserId().getValue();
		final String comments = input.getString("comments");
		
		if(amount <= 0){
			result = new BaseOperationResult(ErrorCode.InvalidAmount);
		}
		
		if(null == result){
		
			IChangeBalanceRequest request = new IChangeBalanceRequest() {
				
				@Override
				public UUID getSourceGuid() {
					return ProductsManager.getCurrentProduct().getGuid();
				}
				
				@Override
				public String getSessionId() {
					return null;
				}
				
				@Override
				public String getGeoCountryCode() {
					return geoCountryCode;
				}
				
				@Override
				public String getComments() {
					return comments;
				}
				
				@Override
				public String getClientIp() {
					return getContext().getClientIp();
				}
				
				@Override
				public Long getBackofficeUserId() {
					return backofficeUserId;
				}
				
				@Override
				public double getAmount() {
					return amount;
				}
				
				@Override
				public int getAccountOperationTypeId() {
					return AccountsOperationsType.Refund.getValue();
				}
				
				@Override
				public long getAccountId() {
					return accountId;
				}
			};
			result = AccountsManager.changeBalance(request);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = new JSONObject().put("transactionId", result.getTransactionId());
		}
		
		return output;
	}
	
	public JSONObject manualPayment(JSONObject input) throws JSONException, IOException{
		JSONObject output;
		BaseOperationResult result = null;
		final double amount = input.getDouble("amount");
		final long accountId = input.getLong("accountId");
		final String ipAddress = getContext().getClientIp();
		final String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
		final Long backofficeUserId = getContext().getUserId().getValue();
		final String comments = input.getString("comments");
		
		if(amount <= 0){
			result = new BaseOperationResult(ErrorCode.InvalidAmount);
		}
		
		if(null == result){
		
			IChangeBalanceRequest request = new IChangeBalanceRequest() {
				
				@Override
				public UUID getSourceGuid() {
					return ProductsManager.getCurrentProduct().getGuid();
				}
				
				@Override
				public String getSessionId() {
					return null;
				}
				
				@Override
				public String getGeoCountryCode() {
					return geoCountryCode;
				}
				
				@Override
				public String getComments() {
					return comments;
				}
				
				@Override
				public String getClientIp() {
					return getContext().getClientIp();
				}
				
				@Override
				public Long getBackofficeUserId() {
					return backofficeUserId;
				}
				
				@Override
				public double getAmount() {
					return amount;
				}
				
				@Override
				public int getAccountOperationTypeId() {
					return AccountsOperationsType.Fund.getValue();
				}
				
				@Override
				public long getAccountId() {
					return accountId;
				}
			};
			result = AccountsManager.changeBalance(request);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = new JSONObject().put("transactionId", result.getTransactionId());
		}
		
		return output;
	}

	public JSONObject getAccountSettings(JSONObject input) throws JSONException{
		long accountId = input.optLong("accountId");
		Integer servicePackageId = JSONHelper.optInt(input, "packageId");
		JSONObject output = null;
		
		BaseOperationResult result = null;
		OperationResult<IAccountDetails> accountResult = AccountsManager.getAccountDetails(accountId);
		IAccountDetails account = null;
		if(accountResult.hasError()){
			result = accountResult;
		} else {
			account = accountResult.getValue();
			output = new JSONObject()
			.put("accountId", account.getId())
			.put("servicePackageId", account.getServicePackageId())
			.put("servicePackageExpiryDate", JSONHelper.getDateFormat(account.getServicePackageExpiryDate(), "MMM dd, yyyy"))
			.put("accountName", account.getName())
			.put("ownerId", account.getOwnerId())
			.put("ownerUserName", account.getOwnerUserName())
			.put("insertDate", mdyhmsFormatter.format(account.getInsertDate()))
			.put("isActive", account.isActive())
			.put("balance", account.getBalance())
			.put("comments", account.getComments())
			.put("timezoneId", account.getTimezoneId())
			.put("accountUsers", account.getMaxUsers());
			
			if(null == servicePackageId){
				servicePackageId = account.getServicePackageId();
			}
		}
		
		if(null == result){
			OperationResult<IOpinionAccount> settingsResult = com.inqwise.opinion.managers.AccountsManager.getOpinionAccount(accountId, servicePackageId);
			if(!settingsResult.hasError()){
				
				IOpinionAccount settings = settingsResult.getValue();
				output.put("creditResponses", JSONHelper.getNullable(settings.getSessionsBalance()));
				output.put("showWelcomeMessage", settings.isShowWelcomeMessage());
				if(null != settings.getNextSessionsCreditDate()){
					output.put("nextSessionsCreditDate", DateFormatUtils.format(settings.getNextSessionsCreditDate(), "MMM dd, yyyy"));
				}
				
				if(null != settings.getLastSessionsCreditDate() && settings.getNextSupplySessionsCredit() > 0){
					output.put("lastSessionsCreditDate", DateFormatUtils.format(settings.getLastSessionsCreditDate(), "MMM dd, yyyy"));
					output.put("nextSessionsCreditAmount", settings.getNextSupplySessionsCredit());
				}
			}
		}
		
		HashMap<String, IVariableSet> variables = null;
		if(null == result){
			String servicePackageKey = ParametersManager.getReferenceKey(EntityType.ServicePackage, servicePackageId);
			OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(accountId,  EntityType.Account, null, new String[] { servicePackageKey });
			if(variablesResult.hasError()){
				result = variablesResult;
			} else {
				variables = variablesResult.getValue();
			}
		}
		
		if(null == result){
			Set<String> keys = variables.keySet();
			JSONArray variablesJa = new JSONArray();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				IVariableSet variableSet = variables.get(key);
				IVariable variable = variableSet.getActual();
				IVariable previousVariable = null;
				if(variable.getEntityType() == EntityType.Account){
					previousVariable = variableSet.getPrevious(variable.getPriorityId());
				} else {
					previousVariable = variable;
				}
				
				JSONObject variableJo = new JSONObject();
				variableJo.put("key", key);
				variableJo.put("value", variable.getJsonValue());
				variableJo.put("value", variable.getJsonValue());
				variableJo.put("name", variableSet.getName());
				variableJo.put("inherited", variable.getEntityType() != EntityType.Account);
				variableJo.put("parentValue", previousVariable.getJsonValue());
				variablesJa.put(variableJo);
			}
			output.put("permissions", variablesJa);
		}
				
		return output;
	}
	
	public JSONObject updateAccountSettings(final JSONObject input) throws JSONException, IOException{
		BulkOperationResults<IOperationResult> results = new BulkOperationResults<>();
		
		//JSONObject output;
		final long accountId = input.optLong("accountId");
		Integer servicePackageId = JSONHelper.optInt(input, "packageId");
		
		IAccountDetailsChangeRequest request = new IAccountDetailsChangeRequest() {
			
			@Override
			public Boolean isActive() {
				return JSONHelper.optBoolean(input, "isActive");
			}
			
			@Override
			public Integer getTimezoneId() {
				return JSONHelper.optInt(input, "timezoneId");
			}
			
			@Override
			public Long getOwnerId() {
				return JSONHelper.optLong(input, "ownerId");
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
				return new IDepositBounds() {
					
					@Override
					public Double getMinDepositAmount() {
						return JSONHelper.optDouble(input, IAccount.JsonNames.MIN_DEPOSIT_AMOUNT);
					}
					
					@Override
					public Double getMaxDepositAmount() {
						return JSONHelper.optDouble(input, IAccount.JsonNames.MAX_DEPOSIT_AMOUNT);
					}
				};
			}
		};
		results.add(AccountsManager.setAccountDetails(request));
		
		if(!results.hasFailures()){
			JSONArray permissionsJa = JSONHelper.optJsonArray(input, "permissions");
			final long modifyUserId = getContext().getUserId().getValue();
			if(null != permissionsJa && permissionsJa.length() > 0) {
				
				if(null == servicePackageId){
					results.add(new BaseOperationResult(ErrorCode.ArgumentMandatory, "packageId"));
				} else {
					for (int i = 0; i < permissionsJa.length(); i++) {
						JSONObject pairJo = permissionsJa.getJSONObject(i);
						Object value = pairJo.get("value");
						String key = pairJo.getString("key");
						if(null == value || JSONObject.NULL.equals(value)){
							// Remove personal variable
							results.add(ParametersManager.deleteReferences(key, ParametersManager.getReferenceKey(EntityType.Account, accountId), null));
						} else {
							String dependsOnKey = ParametersManager.getReferenceKey(EntityType.ServicePackage, servicePackageId);
							// Insert/Update personal variable 
							results.add(ParametersManager.updateVariable(EntityType.Account, accountId, key, value, modifyUserId, dependsOnKey));
						}
					}
				}
			}
		}
		
		if(!results.hasFailures()){
			JSONObject sessionsBalanceJo = JSONHelper.optJson(input, "sessionsBalance");
			if(null != sessionsBalanceJo){
				int amount = JSONHelper.optInt(sessionsBalanceJo, "amount", 0);
				Boolean unlimitedBalance = JSONHelper.optBoolean(sessionsBalanceJo, "unlimited");
				if(amount != 0 || null != unlimitedBalance){
					if(null == servicePackageId){
						results.add(new BaseOperationResult(ErrorCode.ArgumentMandatory, "packageId"));
					} else {
						results.add(com.inqwise.opinion.managers.AccountsManager.changeSessionsBalance(accountId, servicePackageId, amount, unlimitedBalance));
					}
				}
			}
		}
		
		if(results.hasFailures()){
			return results.getFirstFail().toJson();
		} else {
			return BaseOperationResult.JsonOk;
		}
	}
	
	public JSONObject changeSessionsBalance(final JSONObject input) throws JSONException, IOException {
		
		JSONObject output;
		OperationResult<Long> result = null;
		int amount = JSONHelper.optInt(input, "amount", 0);
		Boolean unlimitedBalance = JSONHelper.optBoolean(input, "unlimited");
		long accountId = input.optLong("accountId");
		int servicePackageId = input.getInt("packageId");
		
		if(amount != 0 || null != unlimitedBalance){
			result = com.inqwise.opinion.managers.AccountsManager.changeSessionsBalance(accountId, servicePackageId, amount, unlimitedBalance);
		} else {
			result = new OperationResult<Long>(ErrorCode.ArgumentWrong);
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = new JSONObject().put("sessionsBalance", JSONHelper.getNullable(result.getValue()));
		}
		
		return output;
	}
	
	public JSONObject changeOwner(final JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException {
		
		JSONObject output;
		BaseOperationResult result = null;
		long accountId = input.optLong("accountId");
		long newOwnerId = input.getInt("ownerId");
		IProduct sourceProduct = ProductsManager.getCurrentProduct();
		
		result = AccountsManager.changeAccountOwner(accountId, newOwnerId,  getContext().getLoggedInUser().getValue().getUserId(), sourceProduct.getId());
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject updateBillingSettings(final JSONObject input){
		JSONObject output;
		final long accountId = input.getLong("accountId");
		output = AccountsManager.changeAccountBillingSettings(new IAccountBillingSettingsChangeRequest(){

			@Override
			public long getId() {
				return accountId;
			}

			@Override
			public IDepositBounds getDebositBounds() {
				return new IDepositBounds() {
					
					@Override
					public Double getMinDepositAmount() {
						return JSONHelper.optDouble(input, IAccount.JsonNames.MIN_DEPOSIT_AMOUNT);
					}
					
					@Override
					public Double getMaxDepositAmount() {
						return JSONHelper.optDouble(input, IAccount.JsonNames.MAX_DEPOSIT_AMOUNT);
					}
				};
			}
		}).toJson();
		
		return output;
	}
}
