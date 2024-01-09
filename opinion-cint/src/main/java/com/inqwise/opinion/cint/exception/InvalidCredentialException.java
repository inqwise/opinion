package com.inqwise.opinion.cint.exception;

public class InvalidCredentialException extends BaseException {
	
	private static final long serialVersionUID = 5905473610010845045L;

	public InvalidCredentialException(String msg){
		super(msg);
	}
	
	public InvalidCredentialException(String msg,Throwable exception){
		super(msg, exception);
	}
}