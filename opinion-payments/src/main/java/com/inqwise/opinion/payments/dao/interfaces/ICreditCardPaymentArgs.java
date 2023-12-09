package com.inqwise.opinion.payments.dao.interfaces;


public interface ICreditCardPaymentArgs extends IPaymentArgs {

	String getLast4DigitsOfCreditCardNumber();
	Integer getCreditCardTypeId();
	
}