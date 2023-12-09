package com.cint.exception;

public class SSLConfigurationException extends BaseException{
	
	private static final long serialVersionUID = 742963055876815780L;

	public SSLConfigurationException(String message) {
		super(message);
	}

	public SSLConfigurationException(String message, Throwable exception) {
		super(message, exception);
	}
}
