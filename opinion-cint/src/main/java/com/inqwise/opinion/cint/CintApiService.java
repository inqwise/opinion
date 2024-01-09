package com.inqwise.opinion.cint;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.inqwise.opinion.cint.common.IRequest;
import com.inqwise.opinion.cint.common.IRequestVoid;
import com.inqwise.opinion.cint.common.errorHandle.CintBaseOperationResult;
import com.inqwise.opinion.cint.common.errorHandle.CintErrorCode;
import com.inqwise.opinion.cint.common.errorHandle.CintOperationResult;
import com.inqwise.opinion.cint.core.BaseService;
import com.inqwise.opinion.cint.core.ResponseValue;
import com.inqwise.opinion.cint.exception.ClientActionRequiredException;
import com.inqwise.opinion.cint.exception.HttpErrorException;
import com.inqwise.opinion.cint.exception.InvalidCredentialException;
import com.inqwise.opinion.cint.exception.InvalidResponseDataException;
import com.inqwise.opinion.cint.exception.MissingCredentialException;
import com.inqwise.opinion.cint.exception.SSLConfigurationException;
import com.inqwise.opinion.cint.systemFramework.CintConfig;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class CintApiService extends BaseService{
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	static ApplicationLog logger = ApplicationLog.getLogger(CintApiService.class);
	
	public CintBaseOperationResult call(final IRequestVoid request){
		CintBaseOperationResult result = new CintBaseOperationResult();
		CintBaseOperationResult validateResult = request.validate();
		if(null != validateResult){
			result.setError(validateResult);
		}
		
		if(!result.hasError()){
			call(request, result);
		}
		
		return result;
	}

	private ResponseValue call(final IRequestVoid request, CintBaseOperationResult result) {
		try {
			return super.call(request.getUrl(), request.toXml(), request.getRequestType(), request.isRequiredSignature());
		} catch (HttpErrorException e) {
			UUID ticketId;
			switch(e.getResponseCode()){
			case 404:
				ticketId = logger.error(e, "call : requested resource not found. Url: '%s'", request.getUrl());
				result.setError(CintErrorCode.NoResults, ticketId);
				break;
			case 409:
				ticketId = logger.error(e, "call : state is invalid. Url: '%s'", request.getUrl());
				result.setError(CintErrorCode.StateIsInvalid, ticketId);
				break;
			default:
				ticketId = logger.error(e, "call : unexpected HttpErrorException occured");
				result.setError(CintErrorCode.GeneralError, ticketId);
				break;
			}
			
		} catch (InvalidResponseDataException e) {
			UUID ticketId = logger.error(e, "call : unexpected InvalidResponseDataException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		} catch (ClientActionRequiredException e) {
			UUID ticketId = logger.error(e, "call : unexpected ClientActionRequiredException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		} catch (MissingCredentialException e) {
			UUID ticketId = logger.error(e, "call : unexpected MissingCredentialException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		} catch (SSLConfigurationException e) {
			UUID ticketId = logger.error(e, "call : unexpected SSLConfigurationException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		} catch (InvalidCredentialException e) {
			UUID ticketId = logger.error(e, "call : unexpected InvalidCredentialException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		} catch (FileNotFoundException e) {
			UUID ticketId = logger.error(e, "call : unexpected FileNotFoundException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		} catch (InterruptedException e) {
			UUID ticketId = logger.error(e, "call : unexpected InterruptedException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		} catch (IOException e) {
			UUID ticketId = logger.error(e, "call : unexpected IOException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		} catch (GeneralSecurityException e) {
			UUID ticketId = logger.error(e, "call : unexpected GeneralSecurityException occured");
			result.setError(CintErrorCode.GeneralError, ticketId);
		}
		
		return null;
	}
	
	public <T> CintOperationResult<T> call(final IRequest<T> request){
		CintOperationResult<T> result = new CintOperationResult<T>();
		ResponseValue responseValue = null;
		
		CintBaseOperationResult validateResult = request.validate();
		if(null != validateResult){
			result = validateResult.toErrorResult();
		} else {
			responseValue = call(request, result);
		}
		
		Document doc;
		if(!result.hasError()){
			switch(responseValue.getType()){
			case Location:
				try {
					T response = request.parseResponse(responseValue.getValue());
					result.setValue(response);
				} catch (Exception e) {
					UUID ticketId = logger.error(e, "call : failed to parse the received value");
					result.setError(CintErrorCode.GeneralError, ticketId);
				}
				break;
			case Xml:
				try {
					doc = loadXMLFrom(responseValue.getValue());
					T response = request.parseResponse(doc);
					result.setValue(response);
				} catch (Exception e) {
					UUID ticketId = logger.error(e, "call : failed to parse the received Xml");
					result.setError(CintErrorCode.GeneralError, ticketId);
				}
				break;
			default:
				UUID ticketId = logger.error("call : received not implemented responseValue. type; '%s', value: '%s'", responseValue.getType(), responseValue.getValue());
				result.setError(CintErrorCode.NotImplemented, ticketId);
				break;
			}
		}
		
	    return result;		
	}
	
	private static Document loadXMLFrom(String xml) throws Exception { 
        InputSource is= new InputSource(new StringReader(xml)); 
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        factory.setNamespaceAware(true); 
        DocumentBuilder builder = null; 
        builder = factory.newDocumentBuilder(); 
        Document doc = builder.parse(is); 
        return doc; 
    } 
	
	public static CintBaseOperationResult verifySignature(String input, String signature){
		
		byte[] hmacData = null;
		
		try {
			// Get an hmac_sha1 key from the raw key bytes
			byte[] keyBytes = CintConfig.getSecret().getBytes("UTF-8");			
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1_ALGORITHM);
			 
			// Get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			hmacData = mac.doFinal(input.getBytes("UTF-8")); 
			String currentSignature = Base64.getEncoder().encodeToString(hmacData);
	        if(!currentSignature.equals(signature)){
	        	logger.warn("verifySignature : received invalid quote. '%s', receivedSignature: '%s', correctSignature: '%s'", input, signature, currentSignature);
	        	return new CintBaseOperationResult(CintErrorCode.QuoteInvalid);
	        } 
	        
	        return null;
		} catch (Exception e) {
			UUID ticketId = logger.error(e, "verifySignature : Unexpected error occured");
			return new CintBaseOperationResult(CintErrorCode.GeneralError, ticketId);
		} 
	}
	
}
