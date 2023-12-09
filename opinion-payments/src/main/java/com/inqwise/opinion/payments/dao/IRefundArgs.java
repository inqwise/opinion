package com.inqwise.opinion.payments.dao;

import java.util.Date;
import java.util.UUID;

public interface IRefundArgs {

	long getAccountId();
	int getProcessorTypeId();
	Date getRequestDate();
	double getAmount();
	String getAmountCurrency();
	Date getRefundDate();
	int getPaymentStatusId();
	String getProcessorTransactionId();
	int getSourceId();
	String getDetails();
	long getBackofficeUserId();
	long getParentId();
	int getTransactionTypeId();
	Long getUserId();
	
	
}