package com.inqwise.opinion.payments.common;

public class PaymentsError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PaymentsError(String message, Throwable e){
		super(message, e);
	}

}
