package com.inqwise.opinion.payments.common;

public interface IDoExpressCheckoutResponse extends IPayActionResponse {
	String getRedirectUrl();
	Long getAccountOperationId();
	PaymentStatus getPaymentStatus();
}
