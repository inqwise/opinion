package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.BillType;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;

public class ChargesEntry extends Entry implements IPostmasterObject {

	static ApplicationLog logger = ApplicationLog.getLogger(ChargesEntry.class);
	
	public ChargesEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getChargeDetails(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException{
		JSONObject output;
		IOperationResult result = null;
		IAccount account = null;
		
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if (null == result) {
			long chargeId = input.getLong("chargeId");
			OperationResult<ICharge> chargeResult = ChargesManager.getCharge(chargeId, account.getId());
			
			ICharge charge;
			if(chargeResult.hasError()){
				output = chargeResult.toJson();
			} else {
				charge = chargeResult.getValue();
				output = new JSONObject();
				
				output.put("chargeId", charge.getId());
				output.put("modifyDate", JSONHelper.getDateFormat(account.addDateOffset(charge.getModifyDate()), "MMM dd, yyyy"));
				output.put("insertDate", JSONHelper.getDateFormat(account.addDateOffset(charge.getInsertDate()), "MMM dd, yyyy"));
				output.put("name", charge.getName());
				output.put("description", charge.getDescription());
				output.put("statusId", charge.getStatus().getValue());
				output.put("amount", charge.getAmount());
				output.put("balance", charge.getBalance());
			}
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getCharges(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException{
		JSONObject output;
		
		IOperationResult result = null;
		int top = JSONHelper.optInt(input, "top", 100);
		Long billId = JSONHelper.optLong(input, "billId");
		Integer statusId = JSONHelper.optInt(input, "statusId");
		JSONArray idArray = JSONHelper.optJsonArray(input, "charges");
		Boolean invoiced = JSONHelper.optBoolean(input, "invoiced");
		boolean isCalculateAmounts = JSONHelper.optBoolean(input, "isCalculateAmounts", (null == statusId || statusId == ChargeStatus.Unpaid.getValue()));
		List<Integer> statusIds = JSONHelper.toListOfInt(JSONHelper.optJsonArray(input, "statuses"));
		IAccount account = null;
		Long userId = null;
		
		if(null == result){
			OperationResult<Long> userIdResult = getUserId();
			if(userIdResult.hasError()){
				result = userIdResult;
			} else {
				userId = userIdResult.getValue();
			}
		}
		
		List<Long> chargesIds = null;
		Integer billTypeId = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
	
		if(null == result){
			if(null != idArray){
				chargesIds = JSONHelper.toListOfLong(idArray);
			}
			
			if(null != billId){
				billTypeId = JSONHelper.optInt(input, "billTypeId", BillType.Invoice.getValue());
			}
		}
		
		if(null == result){
			output = getCharges(top, billId, statusId, invoiced,
					isCalculateAmounts, statusIds, account, userId, chargesIds,
					billTypeId);
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public static JSONObject getCharges(int top,
			Long billId, Integer statusId, Boolean invoiced,
			boolean isCalculateAmounts, List<Integer> statusIds,
			IAccount account, Long userId, List<Long> chargesIds,
			Integer billTypeId) throws JSONException {
		
		IOperationResult result = null;
		JSONObject output;
		BigDecimal balance = BigDecimal.ZERO;
		if(null == result && isCalculateAmounts){
			OperationResult<Double> freeBalanceResult = AccountsManager.getFreeBalance(account.getId(), userId);
			if(freeBalanceResult.hasError()){
				result = freeBalanceResult;
			} else {
				balance = BigDecimal.valueOf(freeBalanceResult.getValue());
			}
		}
		
		if(null == result){
			var list = ChargesManager.getCharges(top, account.getId(), billId, billTypeId,
					statusId, statusIds, false, invoiced, chargesIds);
			
			JSONArray ja = new JSONArray();
			BigDecimal totalAmountDue = BigDecimal.ZERO;
			for (var model : list) {
				if(model.getStatus() == ChargeStatus.Unpaid){
					totalAmountDue = totalAmountDue.add(BigDecimal.valueOf(model.getAmount()));
				}
				JSONObject jCharge = new JSONObject();
				jCharge.put("chargeId", model.getId());
				jCharge.put("statusId", chargeStatusId);
				jCharge.put("name", rowSet.getString("charge_name"));
				jCharge.put("description", rowSet.getString("charge_description"));
				jCharge.put("chargeDate", JSONHelper.getDateFormat(account.addDateOffset(rowSet.getDate("insert_date")), "MMM dd, yyyy"));
				jCharge.put("amount", model.getAmount());
				ja.put(jCharge);
			}
			
			output = new JSONObject();
			output.put("list", ja);
			if(isCalculateAmounts) {
				output.put("amountDue", totalAmountDue);
				BigDecimal amountToFund = totalAmountDue.subtract(balance);
				output.put("amountToFund", amountToFund.max(BigDecimal.ZERO));
			}
		} else {
			output = result.toJson();
		}
		return output;
	}
	
}
