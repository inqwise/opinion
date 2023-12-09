package com.inqwise.opinion.payments.common;

public interface IDoNonReferencedCreditRequest {

	String getCreditCardNumber();

	Integer getExpMonth();

	Integer getExpYear();

	String getCvv();

	String getComment();

	double getItemAmount();

	double getShippingAmount();

	double getTaxAmount();

	CreditCardTypes getCreditCardType();

	String getCurrencyCode();

}
