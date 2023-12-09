package com.inqwise.opinion.payments.common;


public interface ISetExpressCheckoutResponse extends IPayActionResponse {
	public abstract String getToken();
	public abstract String getExpressCheckoutUrl();
}
