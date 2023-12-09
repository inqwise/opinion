package com.inqwise.opinion.payments.processors.paypal.actions;

import java.io.IOException;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.xml.sax.SAXException;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.payments.actions.DoExpressCheckout;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.errorHandle.PayBaseOperationResult;
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

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.GetExpressCheckoutDetailsResponseDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentInfoType;

public class PaypalDoExpressCheckoutAction extends
		DoExpressCheckout<PaypalProcessor> {

	static final ApplicationLog Logger = ApplicationLog
			.getLogger(PaypalDoExpressCheckoutAction.class);

	private GetExpressCheckoutDetailsResponseDetailsType details;

	public PaypalDoExpressCheckoutAction(PaypalProcessor processor) {
		super(processor);
	}

	@Override
	protected PayOperationResult<IPayActionResponse> processAction() {
		PayOperationResult<IPayActionResponse> result = null;
		try {
			PayBaseOperationResult detailsResult = collectDetails();
			if (detailsResult.hasError()) {
				result = detailsResult.toErrorResult();
			} else {
				result = doExpressCheckout();
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e,
					"doExpressCheckout: Unexpected error occured");
			result = new PayOperationResult<IPayActionResponse>(
					PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}

	private PayOperationResult<IPayActionResponse> doExpressCheckout()
			throws SSLConfigurationException, InvalidCredentialException,
			HttpErrorException, InvalidResponseDataException,
			ClientActionRequiredException, MissingCredentialException,
			OAuthException, IOException, InterruptedException,
			ParserConfigurationException, SAXException {
		PayOperationResult<IPayActionResponse> result = new PayOperationResult<IPayActionResponse>();

		PayPalAPIInterfaceServiceService service = getProcessor().getService();

		if (!result.hasError()) {
			DoExpressCheckoutPaymentRequestType doCheckoutPaymentRequestType = new DoExpressCheckoutPaymentRequestType();
			DoExpressCheckoutPaymentRequestDetailsType doExpressDetails = new DoExpressCheckoutPaymentRequestDetailsType();
			doExpressDetails.setToken(getToken());
			doExpressDetails.setPayerID(getPayerId());
			doExpressDetails.setPaymentAction(PaymentActionCodeType.SALE);

			// double itemTotalAmt = 0.00;
			// double orderTotalAmt = 0.00;
			// String amt = request.getAmount();
			// String quantity = request.getItemQuantity();
			// itemTotalAmt = getAmount();
			// orderTotalAmt += itemTotalAmt;
			// PaymentDetailsType paymentDetails = new PaymentDetailsType();
			// BasicAmountType orderTotal = new BasicAmountType();

			// orderTotal.setValue(Double.toString(orderTotalAmt));
			// orderTotal.setCurrencyID(CurrencyCodeType.fromValue(getAmountCurrency()));
			// paymentDetails.setOrderTotal(orderTotal);

			// BasicAmountType itemTotal = new BasicAmountType();
			// itemTotal.setValue(Double.toString(itemTotalAmt));

			// itemTotal.setCurrencyID(CurrencyCodeType.fromValue(getAmountCurrency()));
			// paymentDetails.setItemTotal(itemTotal);

			/*
			 * List<PaymentDetailsItemType> paymentItems = new
			 * ArrayList<PaymentDetailsItemType>(); for (PaymentDetailsType
			 * itemRequest : details.getPaymentDetails()) {
			 * PaymentDetailsItemType paymentItem = new
			 * PaymentDetailsItemType();
			 * paymentItem.setName(itemRequest.getItemName());
			 * paymentItem.setQuantity(itemRequest.getItemQuantity());
			 * BasicAmountType amount = new BasicAmountType();
			 * amount.setValue(String.valueOf(itemRequest.getAmount()));
			 * amount.setCurrencyID
			 * (CurrencyCodeType.fromValue(getCurrencyCode()));
			 * paymentItem.setAmount(amount); paymentItems.add(paymentItem); }
			 * paymentDetails.setPaymentDetailsItem(paymentItems);
			 */

			// List<PaymentDetailsType> payDetailType = new
			// ArrayList<PaymentDetailsType>();
			// payDetailType.add(details.getPaymentDetails());
			doExpressDetails.setPaymentDetails(details.getPaymentDetails());

			doCheckoutPaymentRequestType
					.setDoExpressCheckoutPaymentRequestDetails(doExpressDetails);
			DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
			doExpressCheckoutPaymentReq
					.setDoExpressCheckoutPaymentRequest(doCheckoutPaymentRequestType);

			DoExpressCheckoutPaymentResponseType resp = service
					.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);

			if (resp == null) {
				UUID errorKey = Logger.error("doExpressCheckout : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				// session.setAttribute("lastReq", service.getLastRequest());
				// session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {

					result.setValue(this);
					PaymentInfoType paymentInfo = resp.getDoExpressCheckoutPaymentResponseDetails().getPaymentInfo().get(0);
										
					setProcessorTransactionId(paymentInfo.getTransactionID());
					setAmount(NumberUtils.toDouble(paymentInfo.getGrossAmount().getValue()));
					setAmountCurrency(paymentInfo.getGrossAmount().getCurrencyID().toString());
					setPaymentStatus(PaypalProcessor.identifyPaymentStatus(paymentInfo.getPaymentStatus()));
					setPaymentDate(PaypalProcessor.identifyDate(paymentInfo.getPaymentDate()));
										
					savePayment(this);
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn(
							"doExpressCheckout: validation failed. error(s): %s",
							errors);
					PayErrorCode errorCode;
					if (resp.getErrors().size() > 0) {
						errorCode = getProcessor().identifyErrorCode(
								resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(),
							errors.toString());
				}
			}
		}

		return result;
	}

	private PayBaseOperationResult collectDetails()
			throws SSLConfigurationException, InvalidCredentialException,
			IOException, HttpErrorException, InvalidResponseDataException,
			ClientActionRequiredException, MissingCredentialException,
			InterruptedException, OAuthException, ParserConfigurationException,
			SAXException {
		PayBaseOperationResult result = new PayBaseOperationResult();

		PayPalAPIInterfaceServiceService service = getProcessor().getService();

		GetExpressCheckoutDetailsRequestType getDetailsRequestType = new GetExpressCheckoutDetailsRequestType();
		getDetailsRequestType.setToken(getToken());
		GetExpressCheckoutDetailsReq getDetailsRequest = new GetExpressCheckoutDetailsReq();
		getDetailsRequest
				.setGetExpressCheckoutDetailsRequest(getDetailsRequestType);
		GetExpressCheckoutDetailsResponseType detailsResp = service
				.getExpressCheckoutDetails(getDetailsRequest);
		if (detailsResp == null) {
			UUID errorKey = Logger.error("doExpressCheckout : No results");
			result.setError(PayErrorCode.NoResults, errorKey);
		} else {
			if (detailsResp.getAck().toString().equalsIgnoreCase("SUCCESS")) {

				details = detailsResp
						.getGetExpressCheckoutDetailsResponseDetails();
				
			} else {
				JSONArray errors = new JSONArray(detailsResp.getErrors());
				Logger.warn(
						"doExpressCheckout: getDetails failed. error(s): %s",
						errors);
				PayErrorCode errorCode;
				if (detailsResp.getErrors().size() > 0) {
					errorCode = getProcessor().identifyErrorCode(
							detailsResp.getErrors().get(0));
				} else {
					errorCode = PayErrorCode.GeneralError;
				}
				result.setError(errorCode, UUID.randomUUID(), errors.toString());
			}
		}

		return result;
	}

	@Override
	protected PayBaseOperationResult parseQueryString() {
		PayBaseOperationResult result = null;
		if (null == getRequest().getParams()) {
			result = new PayBaseOperationResult(PayErrorCode.ArgumentNull,
					"Params");
		}

		if (null == result) {
			setToken(getRequest().getParams().get("token"));
			setPayerId(getRequest().getParams().get("PayerID"));

			if (null == getToken()) {
				result = new PayBaseOperationResult(PayErrorCode.ArgumentNull,
						"token");
			}

			if (null == result && null == getPayerId()) {
				result = new PayBaseOperationResult(PayErrorCode.ArgumentNull,
						"payerId");
			}
		}

		return result;
	}

}
