package com.inqwise.opinion.payments.dao.interfaces;



public interface ISetExpressCheckoutArgs extends IBeginPaymentArgs {

	public abstract String getExpressCheckoutUrl();

	public abstract String getReturnUrl();

	public abstract void setCancelUrl(String cancelUrl);

	public abstract String getCancelUrl();

	public abstract void setAmount(double amount);

	public abstract void setAmountCurrency(String amountCurrency);
}
