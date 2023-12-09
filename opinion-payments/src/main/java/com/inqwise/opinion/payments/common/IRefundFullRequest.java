package com.inqwise.opinion.payments.common;

public interface IRefundFullRequest extends IRefundRequest {
	String getProcessorTransactionId();
}
