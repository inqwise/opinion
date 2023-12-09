package com.inqwise.opinion.automation.common.events;

import java.util.UUID;

import com.inqwise.opinion.automation.common.FireEventArgs;

public class PayProcessorRefundCompletedEventArgs extends FireEventArgs {
	private String processorTransactionId;
	private double paymentAmount;
	private String paymentCurrencyCode;
	private int processorTypeId;
	private String processorParentTransactionId;
	
	public PayProcessorRefundCompletedEventArgs(int sourceGuid) {
		super(sourceGuid);
	}

	public void setProcessorTransactionId(String value) {
		this.processorTransactionId = value;
	}

	public void setPaymentAmount(double value) {
		this.paymentAmount = value;
	}

	public void setPaymentCurrencyCode(String value) {
		this.paymentCurrencyCode = value;		
	}

	public String getProcessorTransactionId() {
		return processorTransactionId;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public String getPaymentCurrencyCode() {
		return paymentCurrencyCode;
	}

	public int getProcessorTypeId() {
		return processorTypeId;
	}

	public void setProcessorTypeId(int processorTypeId) {
		this.processorTypeId = processorTypeId;
	}

	public String getProcessorParentTransactionId() {
		return processorParentTransactionId;
	}

	public void setProcessorParentTransactionId(
			String processorParentTransactionId) {
		this.processorParentTransactionId = processorParentTransactionId;
	}
}
