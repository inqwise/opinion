package com.inqwise.opinion.automation.common.events;

import java.util.UUID;

import javax.xml.bind.annotation.XmlType;

import com.inqwise.opinion.automation.common.FireEventArgs;

public class PayProcessorTxnCompletedEventArgs extends FireEventArgs {

	private String processorTransactionId;
	private double paymentAmount;
	private String paymentCurrencyCode;
	private int processorTypeId;
	private String receiverEmail;
		
	public PayProcessorTxnCompletedEventArgs(int sourceGuid) {
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

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
}
