package com.inqwise.opinion.payments.common;

import java.util.UUID;

public interface IDirectPaymentRequest extends IPayActionRequest {

	double getAmount();
	String getCurrencyCode();
	String getFirstName();
	String getLastName();
	String getAddress1();
	String getAddress2();
	String getCityName();
	String getStateOrProvince();
	String getCountryCode();
	String getPostalCode();
	CreditCardTypes getCreditCardType();
	String getCreditCardNumber();
	Integer getExpDateMonth();
	Integer getExpDateYear();
	String getCVV2();
	String getClientIp();
	PaymentActionCodeTypes getPaymentAction();
	String getLast4DigitsOfCreditCardNumber();
	String getGeoCountryCode();
	String getSessionId();
	String getDetails();
	
}
