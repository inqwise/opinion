package com.inqwise.opinion.payments.common;

public interface IMassPayItemRequest {

	String getCurrencyCode();

	double getAmount();

	String getReceiverId();

	String getPhone1();

	String getEmail();

}
