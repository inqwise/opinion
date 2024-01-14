package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.BillType;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICancelChargeRequest;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.systemFramework.GeoIpManager;
import com.inqwise.opinion.opinion.common.IPostmasterContext;

public class ChargesEntry extends Entry {
	static ApplicationLog logger = ApplicationLog.getLogger(ChargesEntry.class);
	
	protected ChargesEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject cancelCharge(JSONObject input) throws IOException, JSONException{
		
		JSONObject output;
		long chargeId = input.getLong("chargeId");
		String comments = JSONHelper.optString(input, "comments");
		long backOfficeUserId = getContext().getUserId().getValue();
		
		String ipAddress = getContext().getClientIp();
		String geoCountryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
		int productId = ProductsManager.getCurrentProduct().getId();
		
		BaseOperationResult cancelResult = cancelCharge(chargeId, comments, backOfficeUserId, ipAddress, geoCountryCode, productId);
		
		if(cancelResult.hasError()){
			output = cancelResult.toJson();
		} else {
			output = new JSONObject().put("transactionId", cancelResult.getTransactionId());
		}
		
		return output;
	}

	private BaseOperationResult cancelCharge(final long chargeId, final String comments,
			final long backOfficeUserId, final String ipAddress, final String geoCountryCode,
			final int productId) {
		
		ICancelChargeRequest request = new ICancelChargeRequest() {

			@Override
			public long getChargeId() {
				return chargeId;
			}

			@Override
			public String getComments() {
				return comments;
			}

			@Override
			public Long getBackOfficeUserId() {
				return backOfficeUserId;
			}

			@Override
			public String getClientIp() {
				return ipAddress;
			}

			@Override
			public String getGeoCountryCode() {
				return geoCountryCode;
			}

			@Override
			public int getSourceId() {
				return productId;
			}
		};
		
		return ChargesManager.cancelCharge(request);
	}
	
	public JSONObject getCharges(JSONObject input) throws JSONException, CDataGridException{
		
		IOperationResult result = null;
		JSONObject output;
		Long accountId = JSONHelper.optLong(input, "accountId");
		int top = JSONHelper.optInt(input, "top", 100);
		Long billId = JSONHelper.optLong(input, "billId");
		Integer statusId = JSONHelper.optInt(input, "statusId");
		List<Integer> statusIds = JSONHelper.toListOfInt(JSONHelper.optJsonArray(input, "statuses"));
		JSONArray idArray = JSONHelper.optJsonArray(input, "charges");
		boolean includeExpired = JSONHelper.optBoolean(input, "includeExpired", false);
		Boolean invoiced = JSONHelper.optBoolean(input, "invoiced");
		boolean isCalculateAmounts = JSONHelper.optBoolean(input, "isCalculateAmounts", (null == statusId || statusId == ChargeStatus.Unpaid.getValue()));
		
		Integer billTypeId = null;
		List<Long> chargesIds = null;
		if(null == result){
			if(null != idArray){
				chargesIds = JSONHelper.toListOfLong(idArray);
			}
			
			if(null != billId){
				billTypeId = JSONHelper.optInt(input, "billTypeId", BillType.Invoice.getValue());
			}
			
		}
		
		if(null == result){
			output = getCharges(top, accountId, billId, statusId,
					statusIds, includeExpired, invoiced, isCalculateAmounts,
					billTypeId, chargesIds);
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject getChargeDetails(JSONObject input) throws IOException, JSONException{
		JSONObject output;
		long chargeId = input.getLong("chargeId");
		OperationResult<ICharge> chargeResult = ChargesManager.getCharge(chargeId, null);
		
		ICharge charge;
		if(chargeResult.hasError()){
			output = chargeResult.toJson();
		} else {
			charge = chargeResult.getValue();
			output = new JSONObject();
			
			output.put("chargeId", charge.getId());
			output.put("modifyDate", JSONHelper.getDateFormat(charge.getModifyDate(), "MMM dd, yyyy"));
			output.put("insertDate", JSONHelper.getDateFormat(charge.getInsertDate(), "MMM dd, yyyy"));
			output.put("name", charge.getName());
			output.put("description", charge.getDescription());
			// output.put("dueDate", JSONHelper.getDateFormat(charge.getExpiryDate(), "MMM dd, yyyy"));
			output.put("statusId", charge.getStatus().getValue());
			output.put("amount", charge.getAmount());
			output.put("balance", charge.getBalance());
			output.put("amountToFund", charge.getAmountToFund());
		}
		
		return output;
	}
	
	public static JSONObject getCharges(int top, Long accountId,
			Long billId, Integer statusId, List<Integer> statusIds,
			boolean includeExpired, Boolean invoiced,
			boolean isCalculateAmounts, Integer billTypeId,
			List<Long> chargesIds) throws CDataGridException, JSONException {
		
		IOperationResult result = null;
		JSONObject output;
		BigDecimal balance = BigDecimal.ZERO;
		if(null == result && isCalculateAmounts && null != accountId){
			OperationResult<Double> freeBalanceResult = AccountsManager.getFreeBalance(accountId, null);
			if(freeBalanceResult.hasError()){
				result = freeBalanceResult;
			} else {
				balance = BigDecimal.valueOf(freeBalanceResult.getValue());
			}
		}
		
		if(null == result){
			
			CDataRowSet rowSet = ChargesManager.getCharges(top, accountId, billId, billTypeId,
					statusId, statusIds, includeExpired, invoiced, chargesIds);
			
			
			JSONArray ja = new JSONArray();
			BigDecimal totalAmountDue = BigDecimal.ZERO;
			while(rowSet.next()){
				Long chargeId = rowSet.getLong("charge_id");
				BigDecimal amount = BigDecimal.valueOf(rowSet.getDouble("amount"));
				int chargeStatusId = rowSet.getInt("charge_status_id");
				if(isCalculateAmounts && chargeStatusId == ChargeStatus.Unpaid.getValue()){
					totalAmountDue = totalAmountDue.add(amount);
				}
				JSONObject jCharge = new JSONObject();
				jCharge.put("chargeId", chargeId);
				jCharge.put("statusId", chargeStatusId);
				jCharge.put("name", rowSet.getString("charge_name"));
				jCharge.put("description", rowSet.getString("charge_description"));
				jCharge.put("chargeDate", JSONHelper.getDateFormat(rowSet.getDate("insert_date"), "MMM dd, yyyy"));
				jCharge.put("amount", amount);
				if(null == accountId){
					jCharge.put("accountId", rowSet.getLong("for_account_id"));
					jCharge.put("accountName", rowSet.getString("account_name"));
				}
				ja.put(jCharge);
			}
			
			output = new JSONObject();
			output.put("list", ja);
			if(null != accountId && isCalculateAmounts){
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
