package com.inqwise.opinion.payments.common;

import java.util.Map;

public interface IDoExpressCheckoutRequest extends IPayActionRequest {
	
	public abstract String getClientIp();

	public abstract int getSourceId();
	
	//List<IPaymentDetailsItem> getPaymentDetailsList();

	//String getPayerId();

	//String getToken();

	//double getAmount();

	//int getItemQuantity();

	//String getCurrencyCode();
	
	public abstract Map<String, String> getParams();
}
