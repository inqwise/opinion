package com.inqwise.opinion.payments.processors.paypal.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.xml.sax.SAXException;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.BillingAgreementDetailsType;
import urn.ebay.apis.eBLBaseComponents.BillingCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ItemCategoryType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

import com.inqwise.opinion.automation.ApplicationConfiguration;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.payments.actions.DirectPayment;
import com.inqwise.opinion.payments.actions.SetExpressCheckout;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.IPaymentDetailsItem;
import com.inqwise.opinion.payments.common.ISetExpressCheckoutResponse;
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

public class PaypalSetExpressCheckoutAction extends SetExpressCheckout<PaypalProcessor> {

	static final ApplicationLog Logger = ApplicationLog.getLogger(PaypalSetExpressCheckoutAction.class);
	
	private String expressCheckoutUrl;
	
	public PaypalSetExpressCheckoutAction(PaypalProcessor processor) {
		super(processor);
	}
	
	@Override
	protected PayOperationResult<IPayActionResponse> processAction() {
		PayOperationResult<IPayActionResponse> result = null;
		try {
			result = setExpressCheckout();
			if(!result.hasError()){
				saveBeginPayment(getArgs());
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "setExpressCheckout: Unexpected error occured");
			result = new PayOperationResult<IPayActionResponse>(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}

	private PayOperationResult<IPayActionResponse> setExpressCheckout()throws SSLConfigurationException, InvalidCredentialException,
											IOException, HttpErrorException, InvalidResponseDataException,
											ClientActionRequiredException, MissingCredentialException,
											InterruptedException, OAuthException, ParserConfigurationException,
											SAXException {
		PayOperationResult<IPayActionResponse> result = new PayOperationResult<IPayActionResponse>();
		SetExpressCheckoutRequestType setExpressCheckoutReq = new SetExpressCheckoutRequestType();
		SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();
		
		details.setReturnURL(getRequest().getExpressCheckoutBaseUrl() + getProcessor().getProcessorType().getValue());
		details.setCancelURL(getRequest().getCancelUrl());
		details.setBuyerEmail(getRequest().getBuyerMail());
		
		if(null != getRequest().getBillingAgreement()){
			List<BillingAgreementDetailsType> billingAgreementDetailsList = new ArrayList<BillingAgreementDetailsType>();
			BillingAgreementDetailsType billingAgreementDetails = new BillingAgreementDetailsType();
			billingAgreementDetails.setBillingType(BillingCodeType.MERCHANTINITIATEDBILLINGSINGLEAGREEMENT);
			billingAgreementDetails.setBillingAgreementDescription(getRequest().getBillingAgreement().getDescription());
			billingAgreementDetailsList.add(billingAgreementDetails);
			details.setBillingAgreementDetails(billingAgreementDetailsList);
		}
		
		double itemTotal = 0.00;
		double orderTotal = 0.00;
		// populate line item details
		
		List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
		for (IPaymentDetailsItem itemRequest : getRequest().getPaymentDetailsList()) {
			PaymentDetailsItemType item = new PaymentDetailsItemType();
			BasicAmountType amt = new BasicAmountType();
			amt.setCurrencyID(CurrencyCodeType.fromValue(getRequest()
					.getCurrencyCode()));
			amt.setValue(String.valueOf(itemRequest.getItemAmount()));
			item.setQuantity(itemRequest.getItemQuantity());
			item.setName(itemRequest.getItemName());
			item.setAmount(amt);
			item.setItemCategory(ItemCategoryType.DIGITAL);
			item.setDescription(itemRequest.getItemDescription());
			lineItems.add(item);
			
			if (null != itemRequest.getSalesTax()) {
				item.setTax(new BasicAmountType(CurrencyCodeType
						.fromValue(getRequest().getCurrencyCode()),
						String.valueOf(itemRequest.getSalesTax())));					
			}
			itemTotal += itemRequest.getItemQuantity() * itemRequest.getItemAmount();
			orderTotal += itemTotal;
		}
		
		setAmount(orderTotal);
		
		List<PaymentDetailsType> payDetails = new ArrayList<PaymentDetailsType>();
		PaymentDetailsType paydtl = new PaymentDetailsType();
		paydtl.setPaymentAction(PaymentActionCodeType.SALE);
		
		if (null != getRequest().getOrderDescription()) {
			paydtl.setOrderDescription(getRequest()
					.getOrderDescription());
		}
		
		BasicAmountType itemsTotal = new BasicAmountType();
		itemsTotal.setValue(Double.toString(itemTotal));
		itemsTotal.setCurrencyID(CurrencyCodeType.fromValue(getRequest()
				.getCurrencyCode()));
		paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType
				.fromValue(getRequest().getCurrencyCode()),
				Double.toString(orderTotal)));
		paydtl.setPaymentDetailsItem(lineItems);

		paydtl.setItemTotal(itemsTotal);
		payDetails.add(paydtl);
		details.setPaymentDetails(payDetails);
		
		/*
		if (null != request.getBillingAgreementText()) {
			BillingAgreementDetailsType billingAgreement = new BillingAgreementDetailsType(
					BillingCodeType.fromValue(request
							.getParameter("billingType")));
			billingAgreement.setBillingAgreementDescription(request
					.getBillingAgreementText());
			List<BillingAgreementDetailsType> billList = new ArrayList<BillingAgreementDetailsType>();
			billList.add(billingAgreement);
			details.setBillingAgreementDetails(billList);
		}
		
		//shipping address
		details.setReqConfirmShipping(request.getParameter("reqConfirmShipping"));
		details.setAddressOverride(request.getParameter("addressoverride"));
		AddressType shipToAddress=new AddressType();
		shipToAddress.setName(request.getParameter("name"));
		shipToAddress.setStreet1(request.getParameter("street1"));
		shipToAddress.setStreet2(request.getParameter("street2"));
		shipToAddress.setCityName(request.getParameter("city"));
		shipToAddress.setStateOrProvince(request.getParameter("state"));
		shipToAddress.setPostalCode(request.getParameter("postalCode"));
		shipToAddress.setCountry(CountryCodeType.fromValue(request.getParameter("countryCode")));
		details.setAddress(shipToAddress);
		
		// shipping display options
		details.setNoShipping(request.getParameter("noShipping"));
		
		// PayPal page styling attributes
		details.setBrandName(request.getParameter("brandName"));
		details.setCustom(request.getParameter("pageStyle"));
		details.setCppHeaderImage(request.getParameter("cppheaderimage"));
		details.setCppHeaderBorderColor(request.getParameter("cppheaderbordercolor"));
		details.setCppHeaderBackColor(request.getParameter("cppheaderbackcolor"));
		details.setCppPayflowColor(request.getParameter("cpppayflowcolor"));
		details.setAllowNote(request.getParameter("allowNote"));
		*/
		setExpressCheckoutReq
				.setSetExpressCheckoutRequestDetails(details);

		SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
		expressCheckoutReq
				.setSetExpressCheckoutRequest(setExpressCheckoutReq);

		PayPalAPIInterfaceServiceService service = getProcessor().getService();
		
		SetExpressCheckoutResponseType resp = service
				.setExpressCheckout(expressCheckoutReq);
		
		if (resp == null) {
			UUID errorKey = Logger.error("setExpressCheckout : No results");
			result.setError(PayErrorCode.NoResults, errorKey);
		} else {
			//session.setAttribute("lastReq", service.getLastRequest());
			//session.setAttribute("lastResp", service.getLastResponse());
			if (resp.getAck().toString()
					.equalsIgnoreCase("SUCCESS")) {
				
				setToken(resp.getToken());
				expressCheckoutUrl = getProcessor().getRedirectUrl() + "?cmd=_express-checkout&token="+ getToken();
				
				result.setValue(this);
				//map.put("Token", resp.getToken());
				//map.put("Redirect URL",
				//		"<a href=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="
				//				+ resp.getToken()
				//				+ ">Redirect To PayPal</a>");
				
			} else {
				JSONArray errors = new JSONArray(resp.getErrors());
				Logger.warn("setExpressCheckout: validation failed. error(s): %s", errors);
				PayErrorCode errorCode;
				if(resp.getErrors().size() > 0){
					errorCode = getProcessor().identifyErrorCode(resp.getErrors().get(0));
				} else {
					errorCode = PayErrorCode.GeneralError;
				}
				result.setError(errorCode, UUID.randomUUID(), errors.toString());
			}
		}
		
		return result;
	}

	public String getExpressCheckoutUrl() {
		return expressCheckoutUrl;
	}

	public void setExpressCheckoutUrl(String expressCheckoutUrl) {
		this.expressCheckoutUrl = expressCheckoutUrl;
	}

}
