package com.inqwise.opinion.cint.exception;

public class BaseException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4822089138534405945L;

	public BaseException(String msg){
		super(msg);
	}
	
	public BaseException(String msg,Throwable exception){
		super(msg,exception);
	}	
}
