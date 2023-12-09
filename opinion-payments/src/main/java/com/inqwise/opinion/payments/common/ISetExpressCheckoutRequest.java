package com.inqwise.opinion.payments.common;

import java.util.List;

public interface ISetExpressCheckoutRequest extends IPayActionRequest {
	String getCurrencyCode();
	String getBuyerMail();
	String getOrderDescription();
	String getBillingAgreementText();
	String getReturnUrl();
	String getCancelUrl();
	List<IPaymentDetailsItem> getPaymentDetailsList();
	IBillingAgreement getBillingAgreement();
	String getGeoCountryCode();
	String getSessionId();
	String getDetails();
	String getClientIp();
	String getExpressCheckoutBaseUrl();
	List<Long> getChargeIds();
}
