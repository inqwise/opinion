package com.inqwise.opinion.cint.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;

import com.inqwise.opinion.cint.exception.ClientActionRequiredException;
import com.inqwise.opinion.cint.exception.HttpErrorException;
import com.inqwise.opinion.cint.exception.InvalidCredentialException;
import com.inqwise.opinion.cint.exception.InvalidResponseDataException;
import com.inqwise.opinion.cint.exception.MissingCredentialException;
import com.inqwise.opinion.cint.exception.SSLConfigurationException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

/**
 * wrapper class for APIService.
 */
public abstract class BaseService {

	private static final ApplicationLog Logger = ApplicationLog.getLogger(BaseService.class);
	
	protected String lastRequest = null;
	protected ResponseValue lastResponse = null;

	
	public String getLastRequest() {
		return lastRequest;
	}

	public void setLastRequest(String lastRequest) {
		this.lastRequest = lastRequest;
	}

	public ResponseValue getLastResponse() {
		return lastResponse;
	}

	public void setLastResponse(ResponseValue lastResponse) {
		this.lastResponse = lastResponse;
	}

	/**
	 * Wrapper call for APIservice.makeRequest(), used by InvoiceService class.
	 * 
	 */
	protected ResponseValue call(String url, String messageBody, RequestType requestType, boolean isRequiredSignature)
			throws HttpErrorException, InterruptedException,
			InvalidResponseDataException, ClientActionRequiredException,
			MissingCredentialException, SSLConfigurationException,
			InvalidCredentialException, FileNotFoundException, IOException, GeneralSecurityException {
		
		if(Logger.IsDebugEnabled()){
			Logger.debug("call : url: '%s', body: '%s'", url, messageBody);
		}
		
		APIService apiService = new APIService();
		lastRequest = messageBody;
		ResponseValue response = apiService.makeRequest(url, requestType, messageBody, isRequiredSignature);
		lastResponse=response;
		return response;
	}
}