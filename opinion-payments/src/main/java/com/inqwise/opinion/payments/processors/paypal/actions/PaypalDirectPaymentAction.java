package com.inqwise.opinion.payments.processors.paypal.actions;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import urn.ebay.api.PayPalAPI.DoDirectPaymentReq;
import urn.ebay.api.PayPalAPI.DoDirectPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoDirectPaymentResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AddressType;
import urn.ebay.apis.eBLBaseComponents.CountryCodeType;
import urn.ebay.apis.eBLBaseComponents.CreditCardDetailsType;
import urn.ebay.apis.eBLBaseComponents.CreditCardTypeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoDirectPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PayerInfoType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.PersonNameType;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.payments.actions.DirectPayment;
import com.inqwise.opinion.payments.common.IDirectPaymentRequest;
import com.inqwise.opinion.payments.common.IDirectPaymentResponse;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.errorHandle.PayErrorCode;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.processors.paypal.PaypalProcessor;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

public class PaypalDirectPaymentAction extends DirectPayment<PaypalProcessor> {
	static final ApplicationLog Logger = ApplicationLog.getLogger(PaypalDirectPaymentAction.class);
	
	public PaypalDirectPaymentAction(PaypalProcessor processor) {
		super(processor);
	}
	
	@Override
	protected PayOperationResult<IPayActionResponse> processAction(){
		PayOperationResult<IPayActionResponse> result = null;
		if(null == result){
			result = directPayment();
		}
		
		return result;
	}
	
	private PayOperationResult<IPayActionResponse> directPayment(){
		
		PayOperationResult<IPayActionResponse> result = new PayOperationResult<IPayActionResponse>();
		Logger.info("Start DirectPayment for credit card of type '%s', number ends with '%s'", getRequest().getCreditCardType(), getRequest().getLast4DigitsOfCreditCardNumber());
		
		DoDirectPaymentReq doPaymentReq = new DoDirectPaymentReq();
		DoDirectPaymentRequestType pprequest = new DoDirectPaymentRequestType();
		DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();
		PaymentDetailsType paymentDetails = new PaymentDetailsType();

		BasicAmountType amount = new BasicAmountType();
		amount.setValue(String.valueOf(getRequest().getAmount()));
		amount.setCurrencyID(CurrencyCodeType.fromValue(getRequest().getCurrencyCode()));
		paymentDetails.setOrderTotal(amount);

		AddressType shipTo = new AddressType();
		shipTo.setName(getRequest().getFirstName() + " " + getRequest().getLastName());
		shipTo.setStreet1(getRequest().getAddress1());
		shipTo.setStreet2(getRequest().getAddress2());
		shipTo.setCityName(getRequest().getCityName());
		shipTo.setStateOrProvince(getRequest().getStateOrProvince());
		shipTo.setCountry(CountryCodeType.fromValue(getRequest().getCountryCode()));
		shipTo.setPostalCode(getRequest().getPostalCode());
		paymentDetails.setShipToAddress(shipTo);

		details.setPaymentDetails(paymentDetails);

		CreditCardDetailsType cardDetails = new CreditCardDetailsType();
		cardDetails.setCreditCardType(CreditCardTypeType.fromValue(getRequest().getCreditCardType().toString()));
		cardDetails.setCreditCardNumber(getRequest().getCreditCardNumber());
		cardDetails.setExpMonth(getRequest().getExpDateMonth());
		cardDetails.setExpYear(getRequest().getExpDateYear());
		cardDetails.setCVV2(getRequest().getCVV2());

		PayerInfoType payer = new PayerInfoType();
		PersonNameType name = new PersonNameType();
		name.setFirstName(getRequest().getFirstName());
		name.setLastName(getRequest().getLastName());
		payer.setPayerName(name);
		payer.setPayerCountry(CountryCodeType.fromValue(getRequest().getCountryCode()));
		payer.setAddress(shipTo);

		cardDetails.setCardOwner(payer);

		details.setCreditCard(cardDetails);

		details.setIPAddress(getRequest().getClientIp());
		details.setPaymentAction(PaymentActionCodeType.fromValue(getRequest().getPaymentAction().toString()));
		
		pprequest.setDoDirectPaymentRequestDetails(details);
		
		doPaymentReq.setDoDirectPaymentRequest(pprequest);
		PayPalAPIInterfaceServiceService service = getProcessor().getService();
		try {
			
			DoDirectPaymentResponseType ddresponse = service.doDirectPayment(doPaymentReq);
			if (ddresponse != null) {
				String strRequest = service.getLastRequest();
				String strResponse = service.getLastResponse();
				Logger.debug("PayPalAPIInterfaceServiceService info: LastRequest '%s', LastResponse '%s'", strRequest, strResponse);
				if (ddresponse.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					Logger.info("Transaction Successfuly for credit card of type '%s', number ends with '%s', transactionId '%s'", getRequest().getCreditCardType(), getRequest().getLast4DigitsOfCreditCardNumber(), ddresponse.getTransactionID());
					
					setProcessorTransactionId(ddresponse.getTransactionID());
					setAmount(NumberUtils.toDouble(ddresponse.getAmount().getValue()));
					setAmountCurrency(ddresponse.getAmount().getCurrencyID().toString());
					setPaymentStatus(PaypalProcessor.identifyPaymentStatus(ddresponse.getPaymentStatus()));
					setPaymentDate(PaypalProcessor.identifyDate(ddresponse.getTimestamp()));
										
					// Save to DB
					savePayment(this);
					result.setValue(this);
				} else {
					String strError = getErrorsString(ddresponse.getErrors());
					UUID ticketId = UUID.randomUUID();
					Logger.warn("Transaction Failed for credit card of type '%s', number ends with '%s', Count of errors: %s, Error(s): %s, ticketId: '%s'"
								, getRequest().getCreditCardType(), getRequest().getLast4DigitsOfCreditCardNumber(), ddresponse.getErrors().size(), strError, ticketId.toString());
					ErrorType paypalError = selectError(ddresponse.getErrors());
					result.setError(getProcessor().identifyErrorCode(paypalError), ticketId, paypalError.getLongMessage());
				}
			}
		//} catch (FileNotFoundException e) {
		//	e.printStackTrace();
		//} catch (SAXException e) {
		//	e.printStackTrace();
		//} catch (ParserConfigurationException e) {
		//	e.printStackTrace();
		} catch (SSLConfigurationException e) {
			UUID errorKey = Logger.error(e, "Paypal.DirectPayment : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		} catch (InvalidCredentialException e) {
			UUID errorKey = Logger.error(e, "Paypal.DirectPayment : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		//} catch (UnsupportedEncodingException e) {
		//	e.printStackTrace();
		} catch (HttpErrorException e) {
			UUID errorKey = Logger.error(e, "Paypal.DirectPayment : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		} catch (InvalidResponseDataException e) {
			UUID errorKey = Logger.error(e, "Paypal.DirectPayment : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		} catch (ClientActionRequiredException e) {
			UUID errorKey = Logger.error(e, "Paypal.DirectPayment : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		} catch (MissingCredentialException e) {
			UUID errorKey = Logger.error(e, "Paypal.DirectPayment : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		} catch (OAuthException e) {
			UUID errorKey = Logger.error(e, "Paypal.DirectPayment : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		//} catch (IOException e) {
		//	e.printStackTrace();
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "Paypal.DirectPayment : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		
		return result;
	}

	private ErrorType selectError(List<ErrorType> errors) {
		
		ErrorType result = errors.get(0);
		
		return result;
	}

	private String getErrorsString(List<ErrorType> errors) {
		JSONArray arr = new JSONArray();
		for (ErrorType error : errors) {
			arr.put(new JSONObject(error));
		}
		return arr.toString();
	}

}
