package com.inqwise.opinion.payments.common;

public interface IPaymentDetailsItem {
	String getItemDescription();
	Integer getItemQuantity();
	String getItemName();
	double getItemAmount();
	Double getSalesTax();
	double getAmount();
}
