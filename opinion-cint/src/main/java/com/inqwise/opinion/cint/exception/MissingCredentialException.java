package com.cint.exception;

public class MissingCredentialException extends BaseException {

	private static final long serialVersionUID = 115175960753296706L;

	public MissingCredentialException(String message) {
		super(message);
	}

	public MissingCredentialException(String message, Throwable exception) {
		super(message, exception);
	}

}
