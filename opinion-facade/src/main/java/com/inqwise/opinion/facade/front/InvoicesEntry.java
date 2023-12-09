package com.inqwise.opinion.opinion.facade.front;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
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
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.IInvoice;
import com.inqwise.opinion.library.common.pay.InvoiceItemType;
import com.inqwise.opinion.library.common.pay.InvoiceStatus;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.InvoicesManager;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;

public class InvoicesEntry extends Entry implements IPostmasterObject {

	static ApplicationLog logger = ApplicationLog.getLogger(InvoicesEntry.class);
	public InvoicesEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getInvoiceDetails(JSONObject input) throws IOException, JSONException, CDataGridException, NullPointerException, ExecutionException{
		JSONObject output = null;
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
		
		IInvoice invoice = null;
		long invoiceId = 0;
		if(null == result){
			invoiceId = input.getLong("invoiceId");
			OperationResult<IInvoice> invoiceResult = InvoicesManager.getInvoice(invoiceId, account.getId(), InvoiceStatus.Open.getValue());
			
			if(invoiceResult.hasError()){
				result = invoiceResult;
			} else {
				invoice = invoiceResult.getValue();
			}
		}
		
		if(null == result) {
			IAccountBusinessDetails businessDetails = AccountsManager.getAccountBusinessDetails(account.getId()).getValue();
			
			output = businessDetails.toJson();
			output.put("invoiceId", invoice.getId());
			output.put("invoiceDate", JSONHelper.getDateFormat(account.addDateOffset(invoice.getInvoiceDate()), "MMM dd, yyyy"));
			output.put("fromDate", JSONHelper.getDateFormat(account.addDateOffset(invoice.getInvoiceFromDate()), "MMM dd, yyyy"));
			output.put("toDate", JSONHelper.getDateFormat(account.addDateOffset(invoice.getInvoiceToDate()), "MMM dd, yyyy"));
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
			
			Map<InvoiceItemType, CDataCacheContainer> invoiceItems = InvoicesManager.getInvoiceItems(invoiceId);
			output.put("charges", getCharges(invoiceItems, account));
			output.put("transactions", getTransactions(invoiceItems, invoice.getTotalCredit(), invoice.getTotalDebit(), account));
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	private JSONObject getTransactions(
			Map<InvoiceItemType, CDataCacheContainer> invoiceItems, Double totalCredit, Double totalDebit, IAccount account) throws CDataGridException, JSONException {
		
		JSONArray ja;
		CDataCacheContainer transactionsDs = invoiceItems.get(InvoiceItemType.AccountOperation);
		if(null != transactionsDs){
			ja = getTransactionsList(transactionsDs, account);
		} else {
			ja = new JSONArray();
		}
		
		JSONObject output = new JSONObject();
		output.put("list", ja);
		output.put("totalDebit", totalDebit);
		output.put("totalCredit", totalCredit);
		
		return output;
	}

	private JSONObject getCharges(
			Map<InvoiceItemType, CDataCacheContainer> invoiceItems, IAccount account) throws CDataGridException, JSONException {
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
				jCharge.put("chargeDate", JSONHelper.getDateFormat(account.addDateOffset(rowSet.getDate("insert_date")), "MMM dd, yyyy"));
				jCharge.put("amount", amount);
				ja.put(jCharge);
			}
		}
		output.put("list", ja);
		return output;
	}

	public JSONArray getTransactionsList(CDataCacheContainer ds, IAccount account)
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
					jTransaction.put("debit", -1.0 * amount);
				} else {
					jTransaction.put("debit", 0);
				}
				
				if (amount > 0){
					jTransaction.put("credit", amount);
					
				} else {
					jTransaction.put("credit", 0);
				}
			}
			
			jTransaction.put("balance", rowSet.getDouble("balance"));
			jTransaction.put("modifyDate", JSONHelper.getDateFormat(account.addDateOffset(rowSet.getDate("modify_date")), "MMM dd, yyyy"));
			jTransaction.put("referenceId", rowSet.getLong("reference_id"));
			jTransaction.put("comments", rowSet.getString("comments"));
			jTransaction.put("creditCard", rowSet.getString("credit_card_number"));
			jTransaction.put("creditCardTypeId", rowSet.getInt("credit_card_type_id"));
			
			ja.put(jTransaction);
			
		}
		return ja;
	}
	
	public JSONObject getInvoices(JSONObject input) throws IOException, CDataGridException, JSONException, NullPointerException, ExecutionException{
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
			
			CDataCacheContainer ds = InvoicesManager.getInvoices(top, account.getId(), InvoiceStatus.Open.getValue(), false);
			CDataRowSet rowSet = ds.getAll();
			JSONArray ja = new JSONArray();
			while(rowSet.next()){
				JSONObject jo = new JSONObject();
				jo.put("invoiceId", rowSet.getLong("invoice_id"));
				jo.put("modifyDate", JSONHelper.getDateFormat(account.addDateOffset(rowSet.getDate("modify_date")), "MMM dd, yyyy"));
				jo.put("statusId", rowSet.getInt("invoice_status_id"));
				jo.put("invoiceDate", JSONHelper.getDateFormat(account.addDateOffset(rowSet.getDate("invoice_date")), "MMM dd, yyyy"));
				jo.put("insertDate", JSONHelper.getDateFormat(account.addDateOffset(rowSet.getDate("insert_date")), "MMM dd, yyyy"));
				jo.put("fromDate", JSONHelper.getDateFormat(account.addDateOffset(rowSet.getDate("invoice_from_date")), "MMM dd, yyyy"));
				jo.put("toDate", JSONHelper.getDateFormat(account.addDateOffset(rowSet.getDate("invoice_to_date")), "MMM dd, yyyy"));
				jo.put("amount", rowSet.getDouble("amount"));
				ja.put(jo);
			}
			
			output = new JSONObject().put("list", ja);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
}