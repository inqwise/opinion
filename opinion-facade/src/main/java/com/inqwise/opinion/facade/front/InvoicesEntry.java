package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.InvoiceItemModel;
import com.inqwise.opinion.library.common.InvoiceModel;
import com.inqwise.opinion.library.common.accounts.AccountOperationModel;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeModel;
import com.inqwise.opinion.library.common.pay.IInvoice;
import com.inqwise.opinion.library.common.pay.InvoiceItemType;
import com.inqwise.opinion.library.common.pay.InvoiceStatus;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.InvoicesManager;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;

public class InvoicesEntry extends Entry implements IPostmasterObject {

	static ApplicationLog logger = ApplicationLog.getLogger(InvoicesEntry.class);
	public InvoicesEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getInvoiceDetails(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException{
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
			
			var invoiceItems = InvoicesManager.getInvoiceItems(invoiceId);
			output.put("charges", getCharges(invoiceItems, account));
			output.put("transactions", getTransactions(invoiceItems, invoice.getTotalCredit(), invoice.getTotalDebit(), account));
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	private JSONObject getTransactions(
			Map<InvoiceItemType, List<? extends InvoiceItemModel>> invoiceItems, Double totalCredit, Double totalDebit, IAccount account) throws JSONException {
		
		JSONArray ja;
		var transactionsDs = invoiceItems.get(InvoiceItemType.AccountOperation);
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
			Map<InvoiceItemType, List<? extends InvoiceItemModel>> invoiceItems, IAccount account) throws JSONException {
		JSONObject output = new JSONObject();
		JSONArray ja = new JSONArray();
		var list = invoiceItems.get(InvoiceItemType.Charge);
		if(null != list){
			for(var invoiceItemModel : list){
				var chargeModel = (ChargeModel)invoiceItemModel;
				Long chargeId = chargeModel.getId();
				BigDecimal amount = BigDecimal.valueOf(chargeModel.getAmount());
	
				JSONObject jCharge = new JSONObject();
				jCharge.put("chargeId", chargeId);
				jCharge.put("statusId", chargeModel.getStatus().getValue());
				jCharge.put("name", chargeModel.getName());
				jCharge.put("description", chargeModel.getDescription());
				jCharge.put("chargeDate", JSONHelper.getDateFormat(account.addDateOffset(chargeModel.getCreateDate()), "MMM dd, yyyy"));
				jCharge.put("amount", amount);
				ja.put(jCharge);
			}
		}
		output.put("list", ja);
		return output;
	}

	public JSONArray getTransactionsList(List<? extends InvoiceItemModel> list, IAccount account)
			throws JSONException {
		JSONArray ja = new JSONArray();
		
		for(var invoiceItemModel : list){
			var operationModel = (AccountOperationModel)invoiceItemModel;
			JSONObject jTransaction = new JSONObject();
			jTransaction.put("transactionId", operationModel.getId());
			jTransaction.put("typeId", operationModel.getType().getValue());
			Double amount = operationModel.getAmount();
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
			
			jTransaction.put("balance", operationModel.getBalance());
			jTransaction.put("modifyDate", JSONHelper.getDateFormat(account.addDateOffset(operationModel.getModifyDate()), "MMM dd, yyyy"));
			jTransaction.put("referenceId", operationModel.getReferenceId());
			jTransaction.put("comments", operationModel.getComments());
			jTransaction.put("creditCard", operationModel.getCreditCardNumber());
			jTransaction.put("creditCardTypeId", operationModel.getCreditCardType().getValue());
			
			ja.put(jTransaction);
			
		}
		return ja;
	}
	
	public JSONObject getInvoices(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException{
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
			
			List<InvoiceModel> list = InvoicesManager.getInvoices(top, account.getId(), InvoiceStatus.Open.getValue(), false);
			
			JSONArray ja = new JSONArray();
			for(var invoicesModel : list){
				JSONObject jo = new JSONObject();
				jo.put("invoiceId", invoicesModel.getInvoiceId());
				jo.put("modifyDate", JSONHelper.getDateFormat(account.addDateOffset(invoicesModel.getModifyDate()), "MMM dd, yyyy"));
				jo.put("statusId", invoicesModel.getInvoiceStatusId());
				jo.put("invoiceDate", JSONHelper.getDateFormat(account.addDateOffset(invoicesModel.getInvoiceDate()), "MMM dd, yyyy"));
				jo.put("insertDate", JSONHelper.getDateFormat(account.addDateOffset(invoicesModel.getInsertDate()), "MMM dd, yyyy"));
				jo.put("fromDate", JSONHelper.getDateFormat(account.addDateOffset(invoicesModel.getInvoiceFromDate()), "MMM dd, yyyy"));
				jo.put("toDate", JSONHelper.getDateFormat(account.addDateOffset(invoicesModel.getInvoiceToDate()), "MMM dd, yyyy"));
				jo.put("amount", invoicesModel.getAmount());
				ja.put(jo);
			}
			
			output = new JSONObject().put("list", ja);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
}