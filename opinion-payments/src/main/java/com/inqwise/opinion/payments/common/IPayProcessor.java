package com.inqwise.opinion.payments.common;


public interface IPayProcessor {
	public PayActionTypes[] getSupportedActionTypes();
	public CreditCardTypes[] getSupportedCreditCardTypes();
	public IPayAction getAction(PayActionTypes actionType);
}
