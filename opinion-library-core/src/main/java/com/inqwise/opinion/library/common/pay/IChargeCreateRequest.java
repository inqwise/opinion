package com.inqwise.opinion.library.common.pay;

import java.util.Date;

import org.json.JSONObject;

public interface IChargeCreateRequest {

	Long getBillId();

	Integer getBillTypeId();

	String getName();

	String getDescription();

	double getAmount();
	
	Long getUserId();

	Integer getReferenceTypeId();

	Long getReferenceId();

	long getAccountId();

	String getPostPayAction();
	
	int getStatusId();

	Date getExpiryDate();

	String getAmountCurrency();
	
	JSONObject getPostPayActionData();
}
