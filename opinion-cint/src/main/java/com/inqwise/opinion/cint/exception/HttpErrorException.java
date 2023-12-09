package com.cint.exception;

public class HttpErrorException extends BaseException{
	
	private static final long serialVersionUID = -8822748755002963949L;
	private Integer responseCode;

	public HttpErrorException(String msg){
		super(msg);
	}
	
	public HttpErrorException(String msg, Integer errorCode){
		super(msg);
		responseCode = errorCode;
	}
	
	public HttpErrorException(String msg,Throwable exception){
		super(msg, exception);
	}

	public boolean hasResponseCode(){
		return null != responseCode;
	}
	
	public Integer getResponseCode() {
		return responseCode;
	}
}
