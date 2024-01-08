package com.inqwise.opinion.cint.exception;

public class ClientActionRequiredException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6979166664108550285L;

	public ClientActionRequiredException(String message) {
		super(message);
	}

	public ClientActionRequiredException(String message, Throwable exception) {
		super(message, exception);
	}
}
