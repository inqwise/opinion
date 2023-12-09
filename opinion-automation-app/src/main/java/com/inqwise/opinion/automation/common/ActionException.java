package com.inqwise.opinion.automation.common;

public class ActionException extends Exception {

	public ActionException(String message, Throwable ex) {
		super(message, ex);
	}
	
	public ActionException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7586075224271916193L;

}
