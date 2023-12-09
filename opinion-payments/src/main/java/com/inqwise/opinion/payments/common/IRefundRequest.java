package com.inqwise.opinion.payments.common;

public interface IRefundRequest extends IPayActionRequest {

	String getCurrencyCode();

	Double getAmount();

	long getPayTransactionId();

	RefundTypes getRefundType();

	String getMemo();
	
}
