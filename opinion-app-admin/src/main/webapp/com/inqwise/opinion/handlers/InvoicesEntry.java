package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataGridException;
import net.casper.data.model.CDataRowSet;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.BulkOperationResults;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.AccountsOperationsType;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.BillType;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.IInvoice;
import com.inqwise.opinion.library.common.pay.IInvoiceCreateRequest;
import com.inqwise.opinion.library.common.pay.IOpenInvoiceRequest;
import com.inqwise.opinion.library.common.pay.IUpdateInvoiceRequest;
import com.inqwise.opinion.library.common.pay.InvoiceItemType;
import com.inqwise.opinion.library.common.pay.InvoiceStatus;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.AccountsOperationsManager;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.managers.InvoicesManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.systemFramework.GeoIpManager;
import com.inqwise.opinion.opinion.common.IPostmasterContext;

public class InvoicesEntry extends Entry {

	private static final List<Integer> DRAFT_ACCOUNT_OPERATIONS_TYPE_IDS = Arrays.asList(AccountsOperationsType.Fund.getValue(), AccountsOperationsType.Refund.getValue());
	private static final List<Integer> DRAFT_CHARGES_STATUS_IDS = Arrays.asList(ChargeStatus.Paid.getValue(), ChargeStatus.Canceled.getValue());
	private static final int DRAFT_MAX_INVOICE_ITEMS = 10000;
	private static final int DEFAULT_INVOICE_PERIOD_IN_DAYS = 30;
	static ApplicationLog logger = ApplicationLog.getLogger(InvoicesEntry.class);
	protected InvoicesEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getInvoiceDetails(JSONObject input) throws IOException, JSONException, CDataGridException{
		JSONObject output;
		long invoiceId = input.getLong("invoiceId");
		OperationResult<IInvoice> invoiceResult = InvoicesManager.getInvoice(invoiceId, null, null);
		Date fromDate = JSONHelper.optDate(input, "fromDate");
		Date toDate = JSONHelper.optDate(input, "toDate");
		IInvoice invoice;
		if(invoiceResult.hasError()){
			output = invoiceResult.toJson();
		} else {
			invoice = invoiceResult.getValue();
			
			output = new JSONObject();
			output.put("notes", invoice.getNotes());
			output.put("invoiceId", invoice.getId());
			output.put("modifyDate", JSONHelper.getDateFormat(invoice.getModifyDate(), "MMM dd, yyyy"));
			output.put("insertDate", JSONHelper.getDateFormat(invoice.getInsertDate(), "MMM dd, yyyy"));
			output.put("invoiceDate", JSONHelper.getDateFormat(invoice.getInvoiceDate(), "MMM dd, yyyy"));
			output.put("fromDate", JSONHelper.getDateFormat(invoice.getInvoiceFromDate(), "MMM dd, yyyy"));
			output.put("toDate", JSONHelper.getDateFormat(invoice.getInvoiceToDate(), "MMM dd, yyyy"));
			output.put("statusId", invoice.getStatus().getValue());
			output.put("amount", invoice.getAmount());
			output.put("balance", invoice.getBalance());
			output.put("companyName", JSONHelper.getNullable(invoice.getCompanyName()));
			output.put("firstName", JSONHelper.getNullable(invoice.getFirstName()));
			output.put("lastName", JSONHelper.getNullable(invoice.getLastName()));
			output.put("address1", JSONHelper.getNullable(invoice.getAddress1()));
			output.put("address2", JSONHelper.getNullable(invoice.getAddress2()));
			output.put("city", JSONHelper.getNullable(invoice.getCity()));
			output.put("stateId", JSONHelper.getNullable(invoice.getStateId()));
			output.put("postalCode", JSONHelper.getNullable(invoice.getPostalCode()));
			output.put("phone1", JSONHelper.getNullable(invoice.getPhone1()));
			output.put("countryId", JSONHelper.getNullable(invoice.getCountryId()));
			output.put("countryName", JSONHelper.getNullable(invoice.getCompanyName()));
			output.put("stateName", JSONHelper.getNullable(invoice.getStateName()));
			
			if(invoice.getStatus() == InvoiceStatus.Draft){
				output.put("charges", getCharges(invoice));
				output.put("transactions", getTransactions(invoice, fromDate, toDate));
			} else {
				Map<InvoiceItemType, CDataCacheContainer> invoiceItems = InvoicesManager.getInvoiceItems(invoiceId);
				output.put("charges", getCharges(invoiceItems));
				output.put("transactions", getTransactions(invoiceItems, invoice.getTotalCredit(), invoice.getTotalDebit()));
			}
		}
		
		return output;
	}
	
	private JSONObject getTransactions(IInvoice invoice, Date fromDate, Date toDate) throws JSONException, CDataGridException {
		
		if(null == fromDate || invoice.getStatus() == InvoiceStatus.Open){
			fromDate = invoice.getInvoiceFromDate();
		}
		
		if(null == toDate || invoice.getStatus() == InvoiceStatus.Open){
			toDate = invoice.getInvoiceToDate();
		}

		toDate = DateConverter.shiftToTheEndOfTheDay(toDate);
		MutableDouble totalCredit = new MutableDouble();
		MutableDouble totalDebit = new MutableDouble();
 		CDataCacheContainer ds = AccountsOperationsManager.getAccountOperations(DRAFT_MAX_INVOICE_ITEMS, invoice.getForAccountId(),
				DRAFT_ACCOUNT_OPERATIONS_TYPE_IDS,
				null, null, fromDate, toDate, true);
		JSONArray ja = getTransactionsList(ds, totalCredit, totalDebit);
		JSONObject output = new JSONObject();
		output.put("list", ja);
		output.put("totalDebit", BigDecimal.valueOf(totalDebit.doubleValue()).setScale(2, RoundingMode.HALF_UP));
		output.put("totalCredit", BigDecimal.valueOf(totalCredit.doubleValue()).setScale(2, RoundingMode.HALF_UP));
		
		return output;
	}

	private JSONObject getCharges(IInvoice invoice) throws CDataGridException, JSONException {
		return ChargesEntry.getCharges(DRAFT_MAX_INVOICE_ITEMS, invoice.getForAccountId(), invoice.getId(), null, DRAFT_CHARGES_STATUS_IDS, false, true, false, BillType.Invoice.getValue(), null);
	}
	
	public JSONObject addCharges(JSONObject input) throws IOException, JSONException{
		
		JSONObject output;
		IOperationResult result = null;
		Long invoiceId = JSONHelper.optLong(input, "invoiceId");
		long accountId = input.getLong("accountId");
		List<Long> chargesIds = JSONHelper.toListOfLong(JSONHelper.optJsonArray(input, "charges"));
		
		if(null == chargesIds || chargesIds.size() == 0){
			logger.warn("addChargesToInvoice: chargesIds is null or empty");
			result = new BaseOperationResult(ErrorCode.ArgumentMandatory, "chargesIds is mandatory");
		}
		
		IAccountBusinessDetails businessDetails = null;
		if(null == result && null == invoiceId){
			OperationResult<IAccountBusinessDetails> detailsResult = AccountsManager.getAccountBusinessDetails(accountId);
			if(detailsResult.hasError()){
				result = detailsResult;
			} else {
				businessDetails = detailsResult.getValue();
				result = BaseOperationResult.validateWithoutLog(businessDetails.hasBusinessAddress(), ErrorCode.InvalidOperation, "Business address is mandatory");
			}
		}
		
		IInvoice invoice = null;
		if(null == result){
			OperationResult<IInvoice> invoiceResult;
			if(null == invoiceId){
				long createdByUserId = getContext().getUserId().getValue();
				
				Calendar tmpInvoiceDate = Calendar.getInstance();
				if(1 == tmpInvoiceDate.get(Calendar.WEEK_OF_MONTH)){
					tmpInvoiceDate.set(Calendar.MONTH, tmpInvoiceDate.get(Calendar.MONTH) - 1);
				}
				Date toDate = DateConverter.shiftToTheEndOfTheMonthOrToday(tmpInvoiceDate.getTime());
				
				tmpInvoiceDate.set(Calendar.DAY_OF_MONTH, 1);
				Date fromDate = DateConverter.trim(tmpInvoiceDate);
				invoiceResult = createInvoice(createdByUserId, accountId, fromDate, toDate, businessDetails);
			} else {
				invoiceResult = InvoicesManager.getInvoice(invoiceId, null, InvoiceStatus.Draft.getValue());
			}
			
			if(invoiceResult.hasError()){
				result = invoiceResult;
			} else {
				invoice = invoiceResult.getValue();
			}
		}
		
		if(null == result){
			BaseOperationResult addResult = ChargesManager.addChargesToBill(chargesIds, invoice.getId(), BillType.Invoice, accountId, getContext().getUserId().getValue());
			
			if(addResult.hasError()){
				result = addResult;
			}
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		if(null != invoice){
			output.put("invoiceId", invoice.getId());
		}
		
		return output;
	}
	
	private OperationResult<IInvoice> createInvoice(final long createdByUserId, final long accountId,
			final Date fromDate, final Date toDate, final IAccountBusinessDetails businessDetails) {
		return InvoicesManager.createInvoice(new IInvoiceCreateRequest() {
			
			@Override
			public Long getUserId() {
				return createdByUserId;
			}
			
			@Override
			public Long getForAccountId() {
				return accountId;
			}

			@Override
			public Date getInvoiceFromDate() {
				return fromDate;
			}

			@Override
			public Date getInvoiceToDate() {
				return toDate;
			}

			@Override
			public String getCompanyName() {
				return businessDetails.getCompanyName();
			}

			@Override
			public String getFirstName() {
				return businessDetails.getFirstName();
			}

			@Override
			public String getLastName() {
				return businessDetails.getLastName();
			}

			@Override
			public String getAddress1() {
				return businessDetails.getAddress1();
			}

			@Override
			public String getAddress2() {
				return businessDetails.getAddress2();
			}

			@Override
			public String getCity() {
				return businessDetails.getCity();
			}

			@Override
			public Integer getStateId() {
				return businessDetails.getStateId();
			}

			@Override
			public String getPostalCode() {
				return businessDetails.getPostalCode();
			}

			@Override
			public int getCountryId() {
				return businessDetails.getCountryId();
			}

			@Override
			public String getPhone1() {
				return businessDetails.getPhone1();
			}
		});
	}
	
	public JSONObject getInvoices(JSONObject input) throws IOException, CDataGridException, JSONException{
		JSONObject output;
		Long accountId = JSONHelper.optLong(input, "accountId");
		int top = JSONHelper.optInt(input, "top", 100);
		boolean includeDue = input.optBoolean("includeDue");
		
		Integer statusId = JSONHelper.optInt(input, "statusId");
		CDataCacheContainer ds = InvoicesManager.getInvoices(top, accountId, statusId, includeDue);
		CDataRowSet rowSet = ds.getAll();
		JSONArray ja = new JSONArray();
		while(rowSet.next()){
			JSONObject jo = new JSONObject();
			jo.put("invoiceId", rowSet.getLong("invoice_id"));
			jo.put("modifyDate", JSONHelper.getDateFormat(rowSet.getDate("modify_date"), "MMM dd, yyyy"));
			jo.put("statusId", rowSet.getInt("invoice_status_id"));
			
			if(null == accountId){
				jo.put("accountId", rowSet.getLong("for_account_id"));
				jo.put("accountName", rowSet.getString("account_name"));
			}
			jo.put("invoiceDate", JSONHelper.getDateFormat(rowSet.getDate("invoice_date"), "MMM dd, yyyy"));
			jo.put("fromDate", JSONHelper.getDateFormat(rowSet.getDate("invoice_from_date"), "MMM dd, yyyy"));
			jo.put("toDate", JSONHelper.getDateFormat(rowSet.getDate("invoice_to_date"), "MMM dd, yyyy"));
			jo.put("insertDate", JSONHelper.getDateFormat(rowSet.getDate("insert_date"), "MMM dd, yyyy"));
			jo.put("amount", rowSet.getDouble("amount"));
			ja.put(jo);
		}
		
		output = new JSONObject().put("list", ja);
		
		return output;
	}
	
	
	public JSONObject deleteInvoices(JSONObject input) throws IOException, JSONException{
		JSONObject output;
		List<Long> invoicesIds = JSONHelper.toListOfLong(JSONHelper.optJsonArray(input, "invoices"));
		
		BulkOperationResults<IOperationResult> results = new BulkOperationResults<>();
		for (Long invoiceId : invoicesIds) {
			results.add(InvoicesManager.deleteInvoice(invoiceId, getContext().getUserId().getValue()));
		}
		
		if(results.hasFailures()){
			output = results.getFirstFail().toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject removeCharges(JSONObject input) throws JSONException, IOException{
		JSONObject output;
		List<Long> chargesIds = JSONHelper.toListOfLong(JSONHelper.optJsonArray(input, "charges"));
		
		BulkOperationResults<IOperationResult> results = new BulkOperationResults<>();
		
		for (Long chargeId : chargesIds) {
			results.add(ChargesManager.removeChargeFromBill(chargeId, getContext().getUserId().getValue()));
		}
		
		if(results.hasFailures()){
			output = results.getFirstFail().toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject updateInvoice(final JSONObject input) throws JSONException, IOException, CDataGridException{
		
		IOperationResult result = null;
		JSONObject output;
		
		final Date invoiceFromDate = JSONHelper.optDate(input, "fromDate");
		final Date invoiceToDate = JSONHelper.optDate(input, "toDate");
		final long modifyUserId = getContext().getUserId().getValue();
		final long invoiceId = input.getLong("invoiceId");
		Integer statusId = JSONHelper.optInt(input, "statusId");
		
		if(null == result){
			BaseOperationResult updateResult = InvoicesManager.updateInvoice(new IUpdateInvoiceRequest(){

				@Override
				public long getInvoiceId() {
					return invoiceId;
				}

				@Override
				public Date getInvoiceFromDate() {
					return invoiceFromDate;
				}

				@Override
				public Long getModifyUserId() {
					return modifyUserId;
				}
					
				@Override
				public Integer getStateId() {
					return JSONHelper.optInt(input, "stateId", null, 0);
				}
				
				@Override
				public String getPostalCode() {
					return JSONHelper.optStringTrim(input, "postalCode", null);
				}
				
				@Override
				public String getPhone1() {
					return JSONHelper.optStringTrim(input, "phone1", null);
				}
				
				@Override
				public String getLastName() {
					return JSONHelper.optStringTrim(input, "lastName", null);
				}
				
				@Override
				public String getFirstName() {
					return JSONHelper.optStringTrim(input, "firstName", null);
				}
				
				@Override
				public int getCountryId() {
					return JSONHelper.optInt(input, "countryId");
				}
				
				@Override
				public String getCompanyName() {
					return JSONHelper.optStringTrim(input, "companyName", null);
				}
				
				@Override
				public String getCity() {
					return JSONHelper.optStringTrim(input, "city", null);
				}
				
				@Override
				public String getAddress2() {
					return JSONHelper.optStringTrim(input, "address2", null);
				}
				
				@Override
				public String getAddress1() {
					return JSONHelper.optStringTrim(input, "address1", null);
				}

				@Override
				public Date getInvoiceToDate() {
					return invoiceToDate;
				}

				@Override
				public String getNotes() {
					return JSONHelper.optStringTrim(input, "notes", null);
				}	
				});
			
			if(updateResult.hasError()){
				result = updateResult;
			} 
		}
		
		if(null == result && statusId == InvoiceStatus.Open.getValue()){
			BaseOperationResult completeResult = completeInvoiceGeneration(invoiceId, invoiceFromDate, invoiceToDate);
			if(completeResult.hasError()){
				result = completeResult;
			}
		}
		
		if(null == result){
			output = new JSONObject().put("invoiceId", invoiceId);
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	private BaseOperationResult completeInvoiceGeneration(long invoiceId,
			Date fromDate, Date toDate) throws CDataGridException, IOException {
		
		BaseOperationResult result = null;
		IInvoice invoice = null;
		OperationResult<IInvoice> invoiceResult = InvoicesManager.getInvoice(invoiceId, null, InvoiceStatus.Draft.getValue());
		if(invoiceResult.hasError()){
			result = invoiceResult;
		} else {
			invoice = invoiceResult.getValue();
		}
		
		if(null == result){
			CDataRowSet chargesRowSet = ChargesManager.getCharges(DRAFT_MAX_INVOICE_ITEMS, invoice.getForAccountId(), invoiceId, BillType.Invoice.getValue(), null, DRAFT_CHARGES_STATUS_IDS, false, true, null);
			
			CDataCacheContainer acountOperationsDs = AccountsOperationsManager.getAccountOperations(DRAFT_MAX_INVOICE_ITEMS, invoice.getForAccountId(),
					DRAFT_ACCOUNT_OPERATIONS_TYPE_IDS,
					null, null, fromDate, toDate, true);
			CDataRowSet accountOperationsRowSet = acountOperationsDs.getAll();
			
			double totalCredit = 0.0;
			double totalDebit = 0.0;
			
			//Charges
			List<Long> chargeIds = new ArrayList<Long>();
			while(chargesRowSet.next()){
				chargeIds.add(chargesRowSet.getLong("charge_id"));
			}
			
			//AccountOperations
			List<Long> accountOperationsIds = new ArrayList<Long>();
			while (accountOperationsRowSet.next()) {
				accountOperationsIds.add(accountOperationsRowSet.getLong("accop_id"));
				Double amount = accountOperationsRowSet.getDouble("amount");
				if(null !=  amount){
					if(amount < 0)
					{
						totalDebit -= amount;
					} else if (amount > 0){
						
						totalCredit += amount;
					} 
				}
			}
			
			long backOfficeUserId = getContext().getUserId().getValue();
			String ipAddress = getContext().getClientIp();
			String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
			UUID productGuid = ProductsManager.getCurrentProduct().getGuid();
			if(totalCredit != 0.0){
				totalCredit = BigDecimal.valueOf(totalCredit).setScale(2, RoundingMode.HALF_UP).doubleValue();
			}
			if(totalDebit != 0.0){
				totalDebit = BigDecimal.valueOf(totalDebit).setScale(2, RoundingMode.HALF_UP).doubleValue();
			}
			
			result = completeInvoiceGeneration(invoiceId, invoice.getForAccountId(), chargeIds, accountOperationsIds, backOfficeUserId, ipAddress, geoCountryCode, productGuid, totalCredit, totalDebit);
		}
		
		return result;
	}

	private BaseOperationResult completeInvoiceGeneration(final long invoiceId,
			final Long accountId, final List<Long> chargesIds,
			final List<Long> accountOperationsIds, final long backOfficeUserId,
			final String clientIp, final String geoCountryCode, final UUID sourceGuid,
			final double totalCredit, final double totalDebit) {
		
		
		return InvoicesManager.openInvoice(new IOpenInvoiceRequest(){

			@Override
			public long getInvoiceId() {
				return invoiceId;
			}

			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public List<Long> getChargesIds() {
				return chargesIds;
			}

			@Override
			public List<Long> getAccountOperationsIds() {
				return accountOperationsIds;
			}

			@Override
			public Long getBackOfficeUserId() {
				return backOfficeUserId;
			}

			@Override
			public String getClientIp() {
				return clientIp;
			}

			@Override
			public String getGeoCountryCode() {
				return geoCountryCode;
			}

			@Override
			public UUID getSourceGuid() {
				return sourceGuid;
			}

			@Override
			public double getTotalDebit() {
				return totalDebit;
			}

			@Override
			public double getTotalCredit() {
				return totalCredit;
			}
			
		});
	}
	
	private JSONObject getCharges(
			Map<InvoiceItemType, CDataCacheContainer> invoiceItems) throws CDataGridException, JSONException {
		JSONObject output = new JSONObject();
		JSONArray ja = new JSONArray();
		CDataCacheContainer chargesDs = invoiceItems.get(InvoiceItemType.Charge);
		if(null != chargesDs){
			CDataRowSet rowSet = chargesDs.getAll();
			while(rowSet.next()){
				Long chargeId = rowSet.getLong("charge_id");
				BigDecimal amount = BigDecimal.valueOf(rowSet.getDouble("amount"));
				int chargeStatusId = rowSet.getInt("charge_status_id");
				JSONObject jCharge = new JSONObject();
				jCharge.put("chargeId", chargeId);
				jCharge.put("statusId", chargeStatusId);
				jCharge.put("name", rowSet.getString("charge_name"));
				jCharge.put("description", rowSet.getString("charge_description"));
				jCharge.put("chargeDate", JSONHelper.getDateFormat(rowSet.getDate("insert_date"), "MMM dd, yyyy"));
				jCharge.put("amount", amount);
				ja.put(jCharge);
			}
		}
		output.put("list", ja);
		return output;
	}
	
	private JSONObject getTransactions(
			Map<InvoiceItemType, CDataCacheContainer> invoiceItems, Double totalCredit, Double totalDebit) throws CDataGridException, JSONException {
		
		JSONArray ja;
		CDataCacheContainer transactionsDs = invoiceItems.get(InvoiceItemType.AccountOperation);
		
		if(null != transactionsDs){
			ja = getTransactionsList(transactionsDs, null, null);
		} else {
			ja = new JSONArray();
		}
		
		JSONObject output = new JSONObject();
		output.put("list", ja);
		output.put("totalDebit", totalDebit);
		output.put("totalCredit", totalCredit);
		
		
		return output;
	}
	
	public JSONArray getTransactionsList(CDataCacheContainer ds, MutableDouble totalCredit, MutableDouble totalDebit)
			throws CDataGridException, JSONException {
		JSONArray ja = new JSONArray();
		CDataRowSet rowSet = ds.getAll();
		
		while(rowSet.next()){
			JSONObject jTransaction = new JSONObject();
			jTransaction.put("transactionId", rowSet.getLong("accop_id"));
			jTransaction.put("typeId", rowSet.getInt("accop_type_id"));
			Double amount = rowSet.getDouble("amount");
			if(null !=  amount){
				jTransaction.put("amount", amount);
				if(amount < 0)
				{
					if (null != totalDebit) {
						totalDebit.subtract(amount);
					}
					jTransaction.put("debit", -1.0 * amount);
				} else {
					jTransaction.put("debit", 0);
				}
				
				if (amount > 0){
					if (null != totalCredit) {
						totalCredit.add(amount);
					}
					jTransaction.put("credit", amount);
					
				} else {
					jTransaction.put("credit", 0);
				}
			}
			jTransaction.put("balance", rowSet.getDouble("balance"));
			jTransaction.put("modifyDate", JSONHelper.getDateFormat(rowSet.getDate("modify_date"), "MMM dd, yyyy"));
			jTransaction.put("referenceId", rowSet.getLong("reference_id"));
			jTransaction.put("comments", rowSet.getString("comments"));
			jTransaction.put("creditCard", rowSet.getString("credit_card_number"));
			jTransaction.put("creditCardTypeId", rowSet.getInt("credit_card_type_id"));
			
			ja.put(jTransaction);
			
		}
		return ja;
	}
	
	public JSONObject createEmptyInvoice(JSONObject input) throws JSONException, IOException{
		JSONObject output;
		IOperationResult result = null;
		long accountId = input.getLong("accountId");
		
		IAccountBusinessDetails businessDetails = null;
		if(null == result){
			OperationResult<IAccountBusinessDetails> detailsResult = AccountsManager.getAccountBusinessDetails(accountId);
			if(detailsResult.hasError()){
				result = detailsResult;
			} else {
				businessDetails = detailsResult.getValue();
				result = BaseOperationResult.validateWithoutLog(businessDetails.hasBusinessAddress(), ErrorCode.InvalidOperation, "Business address is mandatory");
			}
		}
		
		IInvoice invoice = null;
		if(null == result){
			OperationResult<IInvoice> invoiceResult;
			
			long createdByUserId = getContext().getUserId().getValue();
			Calendar tmpInvoiceDate = Calendar.getInstance();
			if(1 == tmpInvoiceDate.get(Calendar.WEEK_OF_MONTH)){
				tmpInvoiceDate.set(Calendar.MONTH, tmpInvoiceDate.get(Calendar.MONTH) - 1);
			}
			Date toDate = DateConverter.shiftToTheEndOfTheMonthOrToday(tmpInvoiceDate.getTime());
			
			tmpInvoiceDate.set(Calendar.DAY_OF_MONTH, 1);
			Date fromDate = DateConverter.trim(tmpInvoiceDate);
			invoiceResult = createInvoice(createdByUserId, accountId, fromDate, toDate, businessDetails);
			
			if(invoiceResult.hasError()){
				result = invoiceResult;
			} else {
				invoice = invoiceResult.getValue();
			}
		}
		
		if(null == result){
			output = new JSONObject().put("invoiceId", invoice.getId());
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
}
