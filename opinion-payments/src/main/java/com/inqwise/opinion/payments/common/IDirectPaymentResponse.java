package com.inqwise.opinion.payments.common;

import java.util.Date;

public interface IDirectPaymentResponse extends IPayActionResponse {

	public abstract Date getPaymentDate();

	public abstract PaymentStatus getPaymentStatus();

	public abstract String getAmountCurrency();

	public abstract double getAmount();

	public abstract String getProcessorTransactionId();
	
	public abstract Long getAccountOperationId();

}
