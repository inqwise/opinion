package com.cint.exception;

public class InvalidResponseDataException extends BaseException{
	   
	private static final long serialVersionUID = 2593306036565857811L;

	public InvalidResponseDataException(String msg){
		super(msg);
	}
		
	public InvalidResponseDataException(String msg,Throwable exception){
		super(msg, exception);
	}
	    
}