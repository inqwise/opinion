package com.inqwise.opinion.payments.processors.paypal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.payments.common.BillingAgreementStatus;
import com.inqwise.opinion.payments.common.CreditCardTypes;
import com.inqwise.opinion.payments.common.IDoExpressCheckoutRequest;
import com.inqwise.opinion.payments.common.IDoNonReferencedCreditRequest;
import com.inqwise.opinion.payments.common.IMassPayItemRequest;
import com.inqwise.opinion.payments.common.IPayAction;
import com.inqwise.opinion.payments.common.IPaymentDetailsItem;
import com.inqwise.opinion.payments.common.ISearchPayProcessorTransactionRequest;
import com.inqwise.opinion.payments.common.ISetExpressCheckoutRequest;
import com.inqwise.opinion.payments.common.ISetExpressCheckoutResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PayProcessorTypes;
import com.inqwise.opinion.payments.common.PaymentStatus;
import com.inqwise.opinion.payments.common.PendingReason;
import com.inqwise.opinion.payments.common.ProcessorError;
import com.inqwise.opinion.payments.common.ReceiverInfoCodeTypes;
import com.inqwise.opinion.payments.common.errorHandle.PayErrorCode;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.processors.Processor;
import com.inqwise.opinion.payments.processors.paypal.actions.PaypalDirectPaymentAction;
import com.inqwise.opinion.payments.processors.paypal.actions.PaypalDoExpressCheckoutAction;
import com.inqwise.opinion.payments.processors.paypal.actions.PaypalIpnAction;
import com.inqwise.opinion.payments.processors.paypal.actions.PaypalSetExpressCheckoutAction;
import com.inqwise.opinion.payments.systemFramework.PayConfiguration;
import com.paypal.core.ConfigManager;

import urn.ebay.api.PayPalAPI.AddressVerifyReq;
import urn.ebay.api.PayPalAPI.AddressVerifyRequestType;
import urn.ebay.api.PayPalAPI.AddressVerifyResponseType;
import urn.ebay.api.PayPalAPI.BAUpdateRequestType;
import urn.ebay.api.PayPalAPI.BAUpdateResponseType;
import urn.ebay.api.PayPalAPI.BillAgreementUpdateReq;
import urn.ebay.api.PayPalAPI.BillOutstandingAmountReq;
import urn.ebay.api.PayPalAPI.BillOutstandingAmountRequestType;
import urn.ebay.api.PayPalAPI.BillOutstandingAmountResponseType;
import urn.ebay.api.PayPalAPI.BillUserReq;
import urn.ebay.api.PayPalAPI.BillUserRequestType;
import urn.ebay.api.PayPalAPI.BillUserResponseType;
import urn.ebay.api.PayPalAPI.DoAuthorizationReq;
import urn.ebay.api.PayPalAPI.DoAuthorizationRequestType;
import urn.ebay.api.PayPalAPI.DoAuthorizationResponseType;
import urn.ebay.api.PayPalAPI.DoCaptureReq;
import urn.ebay.api.PayPalAPI.DoCaptureRequestType;
import urn.ebay.api.PayPalAPI.DoCaptureResponseType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.DoNonReferencedCreditReq;
import urn.ebay.api.PayPalAPI.DoNonReferencedCreditRequestType;
import urn.ebay.api.PayPalAPI.DoNonReferencedCreditResponseType;
import urn.ebay.api.PayPalAPI.DoReauthorizationReq;
import urn.ebay.api.PayPalAPI.DoReauthorizationRequestType;
import urn.ebay.api.PayPalAPI.DoReauthorizationResponseType;
import urn.ebay.api.PayPalAPI.DoUATPAuthorizationReq;
import urn.ebay.api.PayPalAPI.DoUATPAuthorizationRequestType;
import urn.ebay.api.PayPalAPI.DoUATPAuthorizationResponseType;
import urn.ebay.api.PayPalAPI.DoUATPExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoUATPExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoUATPExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.DoVoidReq;
import urn.ebay.api.PayPalAPI.DoVoidRequestType;
import urn.ebay.api.PayPalAPI.DoVoidResponseType;
import urn.ebay.api.PayPalAPI.ExternalRememberMeOptOutReq;
import urn.ebay.api.PayPalAPI.ExternalRememberMeOptOutRequestType;
import urn.ebay.api.PayPalAPI.ExternalRememberMeOptOutResponseType;
import urn.ebay.api.PayPalAPI.GetBalanceReq;
import urn.ebay.api.PayPalAPI.GetBalanceRequestType;
import urn.ebay.api.PayPalAPI.GetBalanceResponseType;
import urn.ebay.api.PayPalAPI.GetBillingAgreementCustomerDetailsReq;
import urn.ebay.api.PayPalAPI.GetBillingAgreementCustomerDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetBillingAgreementCustomerDetailsResponseType;
import urn.ebay.api.PayPalAPI.GetBoardingDetailsReq;
import urn.ebay.api.PayPalAPI.GetBoardingDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetBoardingDetailsResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.GetPalDetailsReq;
import urn.ebay.api.PayPalAPI.GetPalDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetPalDetailsResponseType;
import urn.ebay.api.PayPalAPI.GetTransactionDetailsReq;
import urn.ebay.api.PayPalAPI.GetTransactionDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetTransactionDetailsResponseType;
import urn.ebay.api.PayPalAPI.ManagePendingTransactionStatusReq;
import urn.ebay.api.PayPalAPI.ManagePendingTransactionStatusRequestType;
import urn.ebay.api.PayPalAPI.ManagePendingTransactionStatusResponseType;
import urn.ebay.api.PayPalAPI.MassPayReq;
import urn.ebay.api.PayPalAPI.MassPayRequestItemType;
import urn.ebay.api.PayPalAPI.MassPayRequestType;
import urn.ebay.api.PayPalAPI.MassPayResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.ReverseTransactionReq;
import urn.ebay.api.PayPalAPI.ReverseTransactionRequestType;
import urn.ebay.api.PayPalAPI.ReverseTransactionResponseType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.api.PayPalAPI.TransactionSearchReq;
import urn.ebay.api.PayPalAPI.TransactionSearchRequestType;
import urn.ebay.api.PayPalAPI.TransactionSearchResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AckCodeType;
import urn.ebay.apis.eBLBaseComponents.BillOutstandingAmountRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.BillingAgreementDetailsType;
import urn.ebay.apis.eBLBaseComponents.BillingCodeType;
import urn.ebay.apis.eBLBaseComponents.CompleteCodeType;
import urn.ebay.apis.eBLBaseComponents.CreditCardDetailsType;
import urn.ebay.apis.eBLBaseComponents.CreditCardTypeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.DoNonReferencedCreditRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.ExternalRememberMeOwnerDetailsType;
import urn.ebay.apis.eBLBaseComponents.FMFPendingTransactionActionType;
import urn.ebay.apis.eBLBaseComponents.ItemCategoryType;
import urn.ebay.apis.eBLBaseComponents.MerchantPullPaymentCodeType;
import urn.ebay.apis.eBLBaseComponents.MerchantPullPaymentType;
import urn.ebay.apis.eBLBaseComponents.MerchantPullStatusCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentStatusCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentTransactionSearchResultType;
import urn.ebay.apis.eBLBaseComponents.PendingStatusCodeType;
import urn.ebay.apis.eBLBaseComponents.ReceiverInfoCodeType;
import urn.ebay.apis.eBLBaseComponents.ReverseTransactionRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.TransactionEntityType;
import urn.ebay.apis.eBLBaseComponents.UATPDetailsType;

public class PaypalProcessor extends Processor {
	
	private static final ApplicationLog Logger = ApplicationLog.getLogger(PaypalProcessor.class);
	private static DateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private PayPalAPIInterfaceServiceService service;
	private PaypalProcessor(){
		try{
			setService(new PayPalAPIInterfaceServiceService(PayConfiguration.Paypal.getConfigPath()));
		} catch(Exception ex){
			throw new ProcessorError(ex);
		}
	}
	
	private static PaypalProcessor instance;
	public static synchronized PaypalProcessor getInstance(){
		if(null == instance) instance = new PaypalProcessor();
		return instance;
	}
	
	@Override
	public IPayAction getAction(PayActionTypes actionType) {
		IPayAction result;
		switch(actionType){
		case DirectPayment:
			result = new PaypalDirectPaymentAction(this);
			break;
		case SetExpressCheckout:
			result = new PaypalSetExpressCheckoutAction(this);
			break;
		case Ipn:
			result = new PaypalIpnAction(this);
			break;
		case DoExpressCheckout:
			result = new PaypalDoExpressCheckoutAction(this);
			break;
		default:
			throw new Error(String.format("getAction : Paypal processor receave Unsupported action type. '%s'", actionType));
		}
		return result;
	}
	
	@Override
	public PayActionTypes[] getSupportedActionTypes() {
		return new PayActionTypes[] {PayActionTypes.DirectPayment, PayActionTypes.SetExpressCheckout};
	}
	@Override
	public CreditCardTypes[] getSupportedCreditCardTypes() {
		return new CreditCardTypes[] {CreditCardTypes.Amex, CreditCardTypes.Discover,
									CreditCardTypes.Maestro, CreditCardTypes.MasterCard,
									CreditCardTypes.Solo, CreditCardTypes.Switch, CreditCardTypes.Visa};
	}

	private void setService(PayPalAPIInterfaceServiceService service) {
		this.service = service;
	}

	public PayPalAPIInterfaceServiceService getService() {
		return service;
	}

	public static PaymentStatus identifyPaymentStatus(PaymentStatusCodeType paymentStatusCodeType){
		PaymentStatus status = null;
		if(null == paymentStatusCodeType){
			status = PaymentStatus.Completed;
		}else{
			switch (paymentStatusCodeType) {
			case COMPLETED:
				status = PaymentStatus.Completed;
				break;
			case CANCELEDREVERSAL:
				status = PaymentStatus.CanceledReversal;
				break;
			case COMPLETEDFUNDSHELD:
				status = PaymentStatus.CompletedFundsHeld;
				break;
			case CREATED:
				status = PaymentStatus.Created;
				break;
			case DELAYED:
				status = PaymentStatus.Delayed;
				break;
			case DENIED:
				status = PaymentStatus.Denied;
				break;
			case EXPIRED:
				status = PaymentStatus.Expired;
				break;
			case FAILED:
				status = PaymentStatus.Failed;
				break;
			case INPROGRESS:
				status = PaymentStatus.InProgress;
				break;
			case INSTANT:
				status = PaymentStatus.Instant;
				break;
			case NONE:
				status = PaymentStatus.None;
				break;
			case PARTIALLYREFUNDED:
				status = PaymentStatus.PartiallyRefunded;
				break;
			case PENDING:
				status = PaymentStatus.Pending;
				break;
			case PROCESSED:
				status = PaymentStatus.Processed;
				break;
			case REFUNDED:
				status = PaymentStatus.Refunded;
				break;
			case REVERSED:
				status = PaymentStatus.Reversed;
				break;
			case VOIDED:
				status = PaymentStatus.Voided;
				break;
			default:
				Logger.warn("identifyPaymentStatus: Unable to identify the PaymentStatus: '%s'", paymentStatusCodeType);
				status = PaymentStatus.Undefined;
				break;
			}
		}
		return status;
	}

	public PayErrorCode identifyErrorCode(ErrorType paypalError) {
		return PayErrorCode.fromValue(paypalError.getErrorCode());
	}

	private static SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	public static Date identifyDate(String date) {
		try {
			return timestampFormatter.parse(date);
		} catch (Exception e) {
			Logger.error(e, "Unable to parse date." + date);
			return null;
		}
	}

	@Override
	public PayProcessorTypes getProcessorType() {
		return PayProcessorTypes.Paypal;
	}

	public static PendingReason identifyPendingReason(
			PendingStatusCodeType pendingReason) {
		PendingReason result;
		switch (pendingReason) {
		case ADDRESS:
			result = PendingReason.Address;
			break;
		case AUTHORIZATION:
			result = PendingReason.Authorization;
			break;
		case ECHECK:
			result = PendingReason.Echeck;
			break;
		case INTL:
			result = PendingReason.Intl;
			break;
		case MULTICURRENCY:
			result = PendingReason.MultiCurrency;
			break;
		case NONE:
			result = PendingReason.None;
			break;
		case ORDER:
			result = PendingReason.Order;
			break;
		case OTHER:
			result = PendingReason.Other;
			break;
		case PAYMENTREVIEW:
			result = PendingReason.PaymentReview;
			break;
		case UNILATERAL:
			result = PendingReason.Unilateral;
			break;
		case UPGRADE:
			result = PendingReason.Upgrade;
			break;
		case VERIFY:
			result = PendingReason.Verify;
			break;
		default:
			Logger.warn("identifyPendingReason: Unable to identify the PendingReason: '%s'", pendingReason);
			result = PendingReason.Undefined;
			break;
		}
		return result;
	}
	
	public PayOperationResult<GetTransactionDetailsResponseType> getTransactionDetails(String transactionId){
		PayOperationResult<GetTransactionDetailsResponseType> result = new PayOperationResult<GetTransactionDetailsResponseType>();
		GetTransactionDetailsReq detailsRequest = new GetTransactionDetailsReq();
		GetTransactionDetailsRequestType detailsRequestArgs = new GetTransactionDetailsRequestType();
		detailsRequestArgs.setTransactionID(transactionId);
		detailsRequest.setGetTransactionDetailsRequest(detailsRequestArgs);
		PayPalAPIInterfaceServiceService service = getService();
		try{
			GetTransactionDetailsResponseType resp = service.getTransactionDetails(detailsRequest);
			result.setValue(resp);
			if (resp == null) {
				UUID errorKey = Logger.error("getTransactionDetails : No results for transactionId: " + transactionId);
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				
				/*
				map.put("Ack", resp.getAck());
				map.put("Payer", resp.getPaymentTransactionDetails()
						.getPayerInfo().getPayer());
				map.put("Gross Amount", resp
						.getPaymentTransactionDetails()
						.getPaymentInfo().getGrossAmount().getValue()
						+ " "
						+ resp.getPaymentTransactionDetails()
								.getPaymentInfo().getGrossAmount()
								.getCurrencyID());
				map.put("Invoice ID", resp
						.getPaymentTransactionDetails()
						.getPaymentItemInfo().getInvoiceID());
				map.put("Receiver", resp.getPaymentTransactionDetails()
						.getReceiverInfo().getReceiver());
				result.setValue(resp);
				*/
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "getTransactionDetails : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		
		return result;
	}
	
	public PayOperationResult<ManagePendingTransactionStatusResponseType> managePendingTransactionStatus(String transactionId, FMFPendingTransactionActionType actionType){

		PayOperationResult<ManagePendingTransactionStatusResponseType> result = new PayOperationResult<ManagePendingTransactionStatusResponseType>();
		ManagePendingTransactionStatusReq req = new ManagePendingTransactionStatusReq();
		ManagePendingTransactionStatusRequestType reqType = new ManagePendingTransactionStatusRequestType(
				transactionId,
				actionType);
		req.setManagePendingTransactionStatusRequest(reqType);
		
		try{
			ManagePendingTransactionStatusResponseType resp = service.managePendingTransactionStatus(req);
			
			if (resp == null) {
				UUID errorKey = Logger.error("managePendingTransactionStatus : No results for transactionId: " + transactionId);
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				
				//map.put("Ack", resp.getAck());
				//map.put("Transaction ID", resp.getTransactionID());
				//map.put("Status", resp.getStatus());
				result.setValue(resp);
			}
			
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "managePendingTransactionStatus : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		
		return result;
	}
	
	public PayOperationResult<AddressVerifyResponseType> verifyAddress(String email, String street, String zip){
		PayOperationResult<AddressVerifyResponseType> result = new PayOperationResult<AddressVerifyResponseType>();
		AddressVerifyReq req = new AddressVerifyReq();
		AddressVerifyRequestType reqType = new AddressVerifyRequestType(email,street,zip);
		req.setAddressVerifyRequest(reqType);
		
		try{
			AddressVerifyResponseType resp = service.addressVerify(req);
			if (resp == null) {
				UUID errorKey = Logger.error("verifyAddress('%s','%s','%s') : No results", email, street, zip);
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				
				if (resp.getAck() == AckCodeType.SUCCESS) {
					//map.put("Confirmation Code", resp.getConfirmationCode());
					//map.put("PayPal Token", resp.getPayPalToken());
					//map.put("Street Match", resp.getStreetMatch());
					//map.put("Zip Match", resp.getZipMatch());
					result.setValue(resp);
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("verifyAddress: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "managePendingTransactionStatus : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		
		return result;
	}
	
	public PayOperationResult<List<PaymentTransactionSearchResultType>> searchTransactions(ISearchPayProcessorTransactionRequest request){
		PayOperationResult<List<PaymentTransactionSearchResultType>> result = new PayOperationResult<List<PaymentTransactionSearchResultType>>(); 
		TransactionSearchReq txnreq = new TransactionSearchReq();
		TransactionSearchRequestType type = new TransactionSearchRequestType();
		if (request.getStartDate() != null){
			type.setStartDate(DateFormat.format(request.getStartDate()));
		}

		if (request.getEndDate() != null){
			type.setEndDate(DateFormat.format(request.getEndDate()));
		}

		if(null != request.getTransactionId()){
			type.setTransactionID(request.getTransactionId());
		}

		txnreq.setTransactionSearchRequest(type);
		
		try{
			TransactionSearchResponseType txnresponse = service.transactionSearch(txnreq);
	
			if (txnresponse == null) {
				UUID errorKey = Logger.error("searchTransactions : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (txnresponse.getAck() == AckCodeType.SUCCESS) {
					if (txnresponse.getPaymentTransactions().size() > 0) {
						
						List<PaymentTransactionSearchResultType> txns = txnresponse.getPaymentTransactions();
						result.setValue(txns);
						
						/*
						map.put("Transaction ID" + index,
								result.getTransactionID());
						if(result.getNetAmount() !=null)
						{
							map.put("Net Amount" + index, result
									.getNetAmount().getValue()
									+ " "
									+ result.getNetAmount().getCurrencyID());
						}
						map.put("Payer" + index, result.getPayer());
						map.put("Status" + index, result.getStatus());
						*/
					}
				} else {
					JSONArray errors = new JSONArray(txnresponse.getErrors());
					Logger.warn("searchTransactions: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(txnresponse.getErrors().size() > 0){
						errorCode = identifyErrorCode(txnresponse.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "searchTransactions : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		} 
		
		return result;
	}
	
	public PayOperationResult<GetPalDetailsResponseType> getPalDetails(){
		PayOperationResult<GetPalDetailsResponseType> result = new PayOperationResult<GetPalDetailsResponseType>();
		try{
			GetPalDetailsReq req = new GetPalDetailsReq();
			GetPalDetailsRequestType reqType = new GetPalDetailsRequestType();
			req.setGetPalDetailsRequest(reqType);
			GetPalDetailsResponseType resp = service.getPalDetails(req);
			if (resp == null) {
				UUID errorKey = Logger.error("getPalDetails : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					//map.put("Pal ID", resp.getPal());
					result.setValue(resp);
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("getPalDetails: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "getPalDetails : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}
	
	public PayOperationResult<GetBalanceResponseType> getBalance(boolean returnAllCurrencies){
		PayOperationResult<GetBalanceResponseType> result = new PayOperationResult<GetBalanceResponseType>();
		try{
			GetBalanceReq req = new GetBalanceReq();
			GetBalanceRequestType reqType = new GetBalanceRequestType();
	
			reqType.setReturnAllCurrencies(returnAllCurrencies ? "1" : "0");
			req.setGetBalanceRequest(reqType);
			GetBalanceResponseType resp = service.getBalance(req);
			if (resp == null) {
				UUID errorKey = Logger.error("getBalance : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
					/*
					map.put("Balance", resp.getBalance().getValue() + " "
							+ resp.getBalance().getCurrencyID());
					Iterator<BasicAmountType> iterator = resp
							.getBalanceHoldings().iterator();
					int index = 1;
					while (iterator.hasNext()) {
						BasicAmountType amount = (BasicAmountType) iterator
								.next();
						map.put("Amount" + index, amount.getValue() + " "
								+ amount.getCurrencyID());
						index++;
					}
					*/
					
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("getBalance: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "getBalance : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}
	
	public PayOperationResult<DoAuthorizationResponseType> doAuthorization(String transactionId, CurrencyCodeType currencyCode, double amount){
		PayOperationResult<DoAuthorizationResponseType> result = new PayOperationResult<DoAuthorizationResponseType>();
		try {
			DoAuthorizationReq req = new DoAuthorizationReq();
			BasicAmountType amountArgs = new BasicAmountType(currencyCode, Double.toString(amount));
			DoAuthorizationRequestType reqType = new DoAuthorizationRequestType(
					transactionId, amountArgs);
	
			req.setDoAuthorizationRequest(reqType);
			DoAuthorizationResponseType resp = service.doAuthorization(req);
			if (resp == null) {
				UUID errorKey = Logger.error("doAuthorization : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
					/*
					map.put("Amount", resp.getAmount().getValue() + " "
							+ resp.getAmount().getCurrencyID());
					map.put("Payment Status", resp.getAuthorizationInfo()
							.getPaymentStatus());
					*/
	
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("doAuthorization: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "doAuthorization : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}
	
	public PayOperationResult<DoReauthorizationResponseType> doReauthorization(String authorizationId, CurrencyCodeType currencyCode, double amount){
		PayOperationResult<DoReauthorizationResponseType> result = new PayOperationResult<DoReauthorizationResponseType>();
		try {
			DoReauthorizationReq req = new DoReauthorizationReq();
			BasicAmountType amountArgs = new BasicAmountType(
					currencyCode,
					Double.toString(amount));
			DoReauthorizationRequestType reqType = new DoReauthorizationRequestType(
					authorizationId, amountArgs);
	
			req.setDoReauthorizationRequest(reqType);
			DoReauthorizationResponseType resp = service
					.doReauthorization(req);
	
			if (resp == null) {
				UUID errorKey = Logger.error("doReauthorization : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
					/*
					map.put("Authorization ID", resp.getAuthorizationID());
					map.put("Payment Status", resp.getAuthorizationInfo()
							.getPaymentStatus());
					*/
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("doReauthorization: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "doReauthorization : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}
	
	public PayOperationResult<DoVoidResponseType> doVoid(String authorizationId){
		PayOperationResult<DoVoidResponseType> result = new PayOperationResult<DoVoidResponseType>();
		try {
			DoVoidReq req = new DoVoidReq();
			DoVoidRequestType reqType = new DoVoidRequestType(authorizationId);
			req.setDoVoidRequest(reqType);
			DoVoidResponseType resp = service.doVoid(req);
			if (resp == null) {
				UUID errorKey = Logger.error("doVoid : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
					//map.put("Authorization ID", resp.getAuthorizationID());
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("doVoid: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "doVoid : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		
		return result;
	}
	
	public PayOperationResult<DoCaptureResponseType> doCapture(String authorizationId, CurrencyCodeType currencyCode, double amount, CompleteCodeType complete){
		PayOperationResult<DoCaptureResponseType> result = new PayOperationResult<DoCaptureResponseType>();
		try {
			DoCaptureReq req = new DoCaptureReq();
			BasicAmountType amountArgs = new BasicAmountType(currencyCode, Double.toString(amount));
			DoCaptureRequestType reqType = new DoCaptureRequestType(
					authorizationId, amountArgs, complete);
			req.setDoCaptureRequest(reqType);
			DoCaptureResponseType resp = service.doCapture(req);
			if (resp == null) {
				UUID errorKey = Logger.error("doCapture : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
					/*
					map.put("Authorization ID", resp
							.getDoCaptureResponseDetails()
							.getAuthorizationID());
					map.put("Gross Amount", resp
							.getDoCaptureResponseDetails().getPaymentInfo()
							.getGrossAmount().getValue()
							+ " "
							+ resp.getDoCaptureResponseDetails()
									.getPaymentInfo().getGrossAmount()
									.getCurrencyID());
					*/
	
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("doCapture: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "doCapture : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		
		return result;
	}
	
	public PayOperationResult<DoUATPAuthorizationResponseType> doUatpAuthorization(String uatpNumber,
			TransactionEntityType entityType, CurrencyCodeType currencyCode, double amount,
			int expMonth, int expYear){
		PayOperationResult<DoUATPAuthorizationResponseType> result = new PayOperationResult<DoUATPAuthorizationResponseType>();
		try {
			DoUATPAuthorizationReq req = new DoUATPAuthorizationReq();
			UATPDetailsType details = new UATPDetailsType();
			BasicAmountType amountArgs = new BasicAmountType(
					currencyCode, Double.toString(amount));
			details.setExpMonth(expMonth);
			details.setExpYear(expYear);
			details.setUATPNumber(uatpNumber);
			DoUATPAuthorizationRequestType reqType = new DoUATPAuthorizationRequestType(
					details, amountArgs);
			reqType.setTransactionEntity(entityType);
			req.setDoUATPAuthorizationRequest(reqType);
			DoUATPAuthorizationResponseType resp = service
					.doUATPAuthorization(req);
			if (resp == null) {
				UUID errorKey = Logger.error("doUatpAuthorization : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
					/*
					map.put("Authorization Code",
							resp.getAuthorizationCode());
					map.put("Payment Status", resp.getAuthorizationInfo()
							.getPaymentStatus());
					map.put("Amount", resp.getAmount().getValue() + " "
							+ resp.getAmount().getCurrencyID());
					*/
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("doUatpAuthorization: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "doUatpAuthorization : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}
	
	public PayOperationResult<ReverseTransactionResponseType> reverseTransactionId(String transactionId){
		PayOperationResult<ReverseTransactionResponseType> result = new PayOperationResult<ReverseTransactionResponseType>();
		try {
			ReverseTransactionReq req = new ReverseTransactionReq();
			ReverseTransactionRequestDetailsType reqDetails = new ReverseTransactionRequestDetailsType();
			reqDetails.setTransactionID(transactionId);
			ReverseTransactionRequestType reqType = new ReverseTransactionRequestType(
					reqDetails);
			req.setReverseTransactionRequest(reqType);
			ReverseTransactionResponseType resp = service
					.reverseTransaction(req);
			if (resp == null) {
				UUID errorKey = Logger.error("reverseTransactionId : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
					/*
					map.put("Reverse Transaction ID", resp
							.getReverseTransactionResponseDetails()
							.getReverseTransactionID());
					map.put("Status", resp
							.getReverseTransactionResponseDetails()
							.getStatus());
					*/
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("reverseTransactionId: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "reverseTransactionId : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}
	
	public PayOperationResult<DoNonReferencedCreditResponseType> doNonReferencedCredit(IDoNonReferencedCreditRequest request){
		PayOperationResult<DoNonReferencedCreditResponseType> result = new PayOperationResult<DoNonReferencedCreditResponseType>();
		try {
			DoNonReferencedCreditReq req = new DoNonReferencedCreditReq();
			DoNonReferencedCreditRequestDetailsType reqDetails = new DoNonReferencedCreditRequestDetailsType();
			CreditCardDetailsType cardDetails = new CreditCardDetailsType();
			cardDetails.setCreditCardType(CreditCardTypeType.fromValue(request.getCreditCardType().toString()));
			cardDetails.setCreditCardNumber(request.getCreditCardNumber());
			cardDetails.setExpMonth(request.getExpMonth());
			cardDetails.setExpYear(request.getExpYear());
			cardDetails.setCVV2(request.getCvv());
			reqDetails.setCreditCard(cardDetails);
			reqDetails.setComment(request.getComment());
			reqDetails.setNetAmount(new BasicAmountType(CurrencyCodeType
					.fromValue(request.getCurrencyCode()), String.valueOf(request.getItemAmount())));
			reqDetails.setShippingAmount(new BasicAmountType(
					CurrencyCodeType.fromValue(request.getCurrencyCode()), String.valueOf(request.getShippingAmount())));
			reqDetails.setTaxAmount(new BasicAmountType(CurrencyCodeType
					.fromValue(request.getCurrencyCode()), Double.toString(request.getTaxAmount())));
			double totalAmount = request.getItemAmount()
					+ request.getShippingAmount()
					+ request.getTaxAmount();
			reqDetails.setAmount(new BasicAmountType(CurrencyCodeType
					.fromValue(request.getCurrencyCode()), String.valueOf(totalAmount)));
			DoNonReferencedCreditRequestType reqType = new DoNonReferencedCreditRequestType(
					reqDetails);
			req.setDoNonReferencedCreditRequest(reqType);
			DoNonReferencedCreditResponseType resp = service
					.doNonReferencedCredit(req);
			if (resp == null) {
				UUID errorKey = Logger.error("doNonReferencedCredit : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
					/*
					map.put("Transaction ID", resp
							.getDoNonReferencedCreditResponseDetails()
							.getTransactionID());
					map.put("Amount",
							resp.getDoNonReferencedCreditResponseDetails()
									.getAmount().getValue()
									+ " "
									+ resp.getDoNonReferencedCreditResponseDetails()
											.getAmount().getCurrencyID());
					*/
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("doNonReferencedCredit: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "doNonReferencedCredit : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}
	
	public PayOperationResult<MassPayResponseType> massPay(ReceiverInfoCodeTypes codeType, IMassPayItemRequest... requests){
		PayOperationResult<MassPayResponseType> result = new PayOperationResult<MassPayResponseType>();
		try {
			MassPayReq req = new MassPayReq();
			List<MassPayRequestItemType> massPayItem = new ArrayList<MassPayRequestItemType>();
			
			for (int i = 0; i < requests.length; i++) {
				IMassPayItemRequest request = requests[i];
				BasicAmountType amount = new BasicAmountType(
						CurrencyCodeType.fromValue(request
								.getCurrencyCode()),
						String.valueOf(request.getAmount()));
				
				MassPayRequestItemType item = new MassPayRequestItemType(amount);
				switch(codeType){
				case EmailAddress:
					item.setReceiverEmail(request.getEmail());
					break;
				case PhoneNumber:
					item.setReceiverPhone(request.getPhone1());
					break;
				case UserID:
					item.setReceiverID(request.getReceiverId());
					break;
				default:
					throw new Error(String.format("Unimplemented ReceiverInfoCodeType '%s' received", codeType));
				}
				
				massPayItem.add(item);
			}
			
			MassPayRequestType reqType = new MassPayRequestType(massPayItem);
			reqType.setReceiverType(ReceiverInfoCodeType.fromValue(codeType.toString()));
			req.setMassPayRequest(reqType);
					
			MassPayResponseType resp = service.massPay(req);
			if (resp == null) {
				UUID errorKey = Logger.error("massPay : No results");
				result.setError(PayErrorCode.NoResults, errorKey);
			} else {
				//session.setAttribute("lastReq", service.getLastRequest());
				//session.setAttribute("lastResp", service.getLastResponse());
				if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					result.setValue(resp);
				} else {
					JSONArray errors = new JSONArray(resp.getErrors());
					Logger.warn("massPay: validation failed. error(s): %s", errors);
					PayErrorCode errorCode;
					if(resp.getErrors().size() > 0){
						errorCode = identifyErrorCode(resp.getErrors().get(0));
					} else {
						errorCode = PayErrorCode.GeneralError;
					}
					result.setError(errorCode, UUID.randomUUID(), errors.toString());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "massPay : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		return result;
	}
	
	public class Recurent {
		
		private PayPalAPIInterfaceServiceService service;
		public Recurent(PayPalAPIInterfaceServiceService service) {
			this.service = service;
		}
		
		public PayOperationResult<GetBillingAgreementCustomerDetailsResponseType> getBillingAgreementCustomerDetails(String token){
			PayOperationResult<GetBillingAgreementCustomerDetailsResponseType> result = new PayOperationResult<GetBillingAgreementCustomerDetailsResponseType>();
			try {
				GetBillingAgreementCustomerDetailsReq gReq = new GetBillingAgreementCustomerDetailsReq();
				GetBillingAgreementCustomerDetailsRequestType gRequestType = new GetBillingAgreementCustomerDetailsRequestType();
				gRequestType.setToken(token);
				gReq.setGetBillingAgreementCustomerDetailsRequest(gRequestType);
				GetBillingAgreementCustomerDetailsResponseType txnresponse = service
						.getBillingAgreementCustomerDetails(gReq);
	
				if (txnresponse == null) {
					UUID errorKey = Logger.error("getBillingAgreementCustomerDetails : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (txnresponse.getAck().toString()
							.equalsIgnoreCase("SUCCESS")) {
						result.setValue(txnresponse);
						
						//map.put("Payer Mail",
						//		txnresponse
						//				.getGetBillingAgreementCustomerDetailsResponseDetails()
						//				.getPayerInfo().getPayer());
						
					} else {
						JSONArray errors = new JSONArray(txnresponse.getErrors());
						Logger.warn("getBillingAgreementCustomerDetails: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(txnresponse.getErrors().size() > 0){
							errorCode = identifyErrorCode(txnresponse.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "getBillingAgreementCustomerDetails : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		public PayOperationResult<BAUpdateResponseType> billAgreementUpdate(String referenceId, BillingAgreementStatus status, String description){
			PayOperationResult<BAUpdateResponseType> result = new PayOperationResult<BAUpdateResponseType>();
			try {
				BillAgreementUpdateReq bReq = new BillAgreementUpdateReq();
				BAUpdateRequestType baUpdateRequestType = new BAUpdateRequestType();
				baUpdateRequestType.setReferenceID(referenceId);
				baUpdateRequestType
						.setBillingAgreementStatus(MerchantPullStatusCodeType.fromValue(status.toString()));
				baUpdateRequestType.setBillingAgreementDescription(description);
				bReq.setBAUpdateRequest(baUpdateRequestType);
				BAUpdateResponseType txnresponse = service
						.billAgreementUpdate(bReq);
	
				if (txnresponse == null) {
					UUID errorKey = Logger.error("billAgreementUpdate : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (txnresponse.getAck().toString()
							.equalsIgnoreCase("SUCCESS")) {
						result.setValue(txnresponse);
						
						//map.put("Billing Agreement ID", txnresponse
						//		.getBAUpdateResponseDetails()
						//		.getBillingAgreementID());
						//map.put("Billing Agreement Description", txnresponse
						//		.getBAUpdateResponseDetails()
						//		.getBillingAgreementDescription());
						//map.put("Billing Agreement Status", txnresponse
						//		.getBAUpdateResponseDetails()
						//		.getBillingAgreementStatus());
						
					} else {
						JSONArray errors = new JSONArray(txnresponse.getErrors());
						Logger.warn("billAgreementUpdate: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(txnresponse.getErrors().size() > 0){
							errorCode = identifyErrorCode(txnresponse.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "billAgreementUpdate : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		public PayOperationResult<BillUserResponseType> billUser(String billingAgreementId, String itemName, int itemNumber, double amount, String currencyCode, String memo){
			PayOperationResult<BillUserResponseType> result = new PayOperationResult<BillUserResponseType>();
			try {
				BillUserReq req = new BillUserReq();
				BillUserRequestType reqType = new BillUserRequestType();
				MerchantPullPaymentType merchantPullPayment = new MerchantPullPaymentType();
				merchantPullPayment.setMpID(billingAgreementId);
				merchantPullPayment.setPaymentType(MerchantPullPaymentCodeType.ANY);
				merchantPullPayment.setItemName(itemName);
				merchantPullPayment.setItemNumber(String.valueOf(itemNumber));
				merchantPullPayment.setAmount(new BasicAmountType(
						CurrencyCodeType.fromValue(currencyCode), String.valueOf(amount)));
				merchantPullPayment.setMemo(memo);
				//merchantPullPayment.setTax(new BasicAmountType(CurrencyCodeType
				//		.fromValue(request.getParameter("currencyID")), request
				//		.getParameter("tax")));
				//merchantPullPayment.setShipping(new BasicAmountType(
				//		CurrencyCodeType.fromValue(request
				//				.getParameter("currencyID")), request
				//				.getParameter("shipping")));
				//merchantPullPayment.setHandling(new BasicAmountType(
				//		CurrencyCodeType.fromValue(request
				//				.getParameter("currencyID")), request
				//				.getParameter("handling")));
				//merchantPullPayment.setEmailSubject(request
				//		.getParameter("mailSubject"));
				reqType.setMerchantPullPaymentDetails(merchantPullPayment);
				req.setBillUserRequest(reqType);
				BillUserResponseType resp = service.billUser(req);
				if (resp == null) {
					UUID errorKey = Logger.error("billUser : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("Payer Mail", resp.getBillUserResponseDetails().getPayerInfo().getPayer());
						//map.put("Merchant Pull Status", resp.getBillUserResponseDetails().getMerchantPullInfo().getMpStatus());
						//map.put("Transaction ID", resp.getBillUserResponseDetails().getPaymentInfo().getTransactionID());
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("billUser: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "billUser : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		/*
		public PayOperationResult<DoReferenceTransactionResponseType> doReferenceTransaction(){
			
			PayOperationResult<DoReferenceTransactionResponseType> result = new PayOperationResult<DoReferenceTransactionResponseType>();
			try {
				DoReferenceTransactionReq doReq = new DoReferenceTransactionReq();
				DoReferenceTransactionRequestType doRequestType = new DoReferenceTransactionRequestType();
				DoReferenceTransactionRequestDetailsType doDetailsType = new DoReferenceTransactionRequestDetailsType();
	
				doDetailsType.setPaymentAction(PaymentActionCodeType
						.fromValue(request.getParameter("paymentAction")));
				String pt = request.getParameter("paymentType");
				doDetailsType.setPaymentType(MerchantPullPaymentCodeType
						.fromValue(pt));
	
				PaymentDetailsType paymentDetails = new PaymentDetailsType();
	
				paymentDetails.setButtonSource("Java_SDK_JSP");
				BasicAmountType amount = new BasicAmountType();
				amount.setValue(request.getParameter("amount"));
				amount.setCurrencyID(CurrencyCodeType.fromValue(request
						.getParameter("currencyID")));
				paymentDetails.setOrderTotal(amount);
	
				AddressType shipTo = new AddressType();
				shipTo.setName(request.getParameter("firstName") + " "
						+ request.getParameter("lastName"));
				shipTo.setStreet1(request.getParameter("address1"));
				shipTo.setStreet2(request.getParameter("address2"));
				shipTo.setCityName(request.getParameter("city"));
				shipTo.setStateOrProvince(request.getParameter("state"));
				shipTo.setCountry(CountryCodeType.US);
				shipTo.setPostalCode(request.getParameter("zip"));
				paymentDetails.setShipToAddress(shipTo);
	
				doDetailsType.setPaymentDetails(paymentDetails);
	
				doDetailsType.setReferenceID(request
						.getParameter("referenceID"));
				if (request.getParameter("ReferenceCreditCardDetails") != null
						&& "ON".equalsIgnoreCase(request
								.getParameter("ReferenceCreditCardDetails"))) {
					ReferenceCreditCardDetailsType rType = new ReferenceCreditCardDetailsType();
	
					PersonNameType personNameType = new PersonNameType();
					personNameType.setFirstName(request
							.getParameter("firstName"));
					personNameType
							.setLastName(request.getParameter("lastName"));
					rType.setCardOwnerName(personNameType);
	
					CreditCardNumberTypeType crType = new CreditCardNumberTypeType();
					crType.setCreditCardNumber(request
							.getParameter("creditCardNumber"));
					crType.setCreditCardType(CreditCardTypeType
							.fromValue(request.getParameter("creditCardType")));
					rType.setCreditCardNumberType(crType);
	
					rType.setCVV2(request.getParameter("CVV2"));
					rType.setExpMonth(Integer.parseInt(request
							.getParameter("expMonth")));
					rType.setExpYear(Integer.parseInt(request
							.getParameter("expYear")));
					rType.setStartMonth(Integer.parseInt(request
							.getParameter("startMonth")));
					rType.setStartYear(Integer.parseInt(request
							.getParameter("startYear")));
	
					AddressType billAddr = new AddressType();
					billAddr.setName(request.getParameter("firstName") + " "
							+ request.getParameter("lastName"));
					billAddr.setStreet1(request.getParameter("address1"));
					billAddr.setStreet2(request.getParameter("address2"));
					billAddr.setCityName(request.getParameter("city"));
					billAddr.setStateOrProvince(request.getParameter("state"));
					billAddr.setCountry(CountryCodeType.US);
					billAddr.setPostalCode(request.getParameter("zip"));
					rType.setBillingAddress(billAddr);
	
					doDetailsType.setCreditCard(rType);
				}
	
				doRequestType
						.setDoReferenceTransactionRequestDetails(doDetailsType);
				doReq.setDoReferenceTransactionRequest(doRequestType);
				DoReferenceTransactionResponseType txnresponse = null;
				txnresponse = service.doReferenceTransaction(doReq);
				if (txnresponse == null) {
					UUID errorKey = Logger.error("doReferenceTransaction : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (txnresponse.getAck().toString()
							.equalsIgnoreCase("SUCCESS")) {
						result.setValue(txnresponse);
						
						//map.put("Transaction ID", txnresponse
						//		.getDoReferenceTransactionResponseDetails()
						//		.getTransactionID());
						//map.put("Billing Agreement ID", txnresponse
						//		.getDoReferenceTransactionResponseDetails()
						//		.getBillingAgreementID());
						
						
					} else {
						JSONArray errors = new JSONArray(txnresponse.getErrors());
						Logger.warn("doReferenceTransaction: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(txnresponse.getErrors().size() > 0){
							errorCode = identifyErrorCode(txnresponse.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "doReferenceTransaction : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		public void createRecurringPaymentsProfile(){
			PayOperationResult<CreateRecurringPaymentsProfileResponseType> result = new PayOperationResult<CreateRecurringPaymentsProfileResponseType>();
			try {
				CreateRecurringPaymentsProfileReq req = new CreateRecurringPaymentsProfileReq();
				CreateRecurringPaymentsProfileRequestType reqType = new CreateRecurringPaymentsProfileRequestType();
				// Populate Recurring Payments Profile Details
				RecurringPaymentsProfileDetailsType profileDetails = new RecurringPaymentsProfileDetailsType(
						request.getParameter("billingStartDate")
								+ "T00:00:00:000Z");
				if (request.getParameter("subscriberName") != "") {
					profileDetails.setSubscriberName(request
							.getParameter("subscriberName"));
				} else if (request.getParameter("shippingName") != "") {
					AddressType shippingAddr = new AddressType();
					shippingAddr.setName(request.getParameter("shippingName"));
					shippingAddr.setStreet1(request
							.getParameter("shippingStreet1"));
					shippingAddr.setStreet2(request
							.getParameter("shippingStreet2"));
					shippingAddr
							.setPhone(request.getParameter("shippingPhone"));
					shippingAddr.setCityName(request
							.getParameter("shippingCity"));
					shippingAddr.setStateOrProvince(request
							.getParameter("shippingState"));
					shippingAddr.setCountryName(request
							.getParameter("shippingCountry"));
					shippingAddr.setPostalCode(request
							.getParameter("shippingPostalCode"));
					profileDetails.setSubscriberShippingAddress(shippingAddr);
				}
	
				// Populate schedule details
				ScheduleDetailsType scheduleDetails = new ScheduleDetailsType();
				scheduleDetails.setDescription(request
						.getParameter("profileDescription"));
				if (request.getParameter("maxFailedPayments") != "") {
					scheduleDetails
							.setMaxFailedPayments(Integer.parseInt(request
									.getParameter("maxFailedPayments")));
				}
				if (request.getParameter("autoBillOutstandingAmount") != "") {
					scheduleDetails
							.setAutoBillOutstandingAmount(AutoBillType.fromValue(request
									.getParameter("autoBillOutstandingAmount")));
				}
				if (request.getParameter("initialAmount") != "") {
					ActivationDetailsType activationDetails = new ActivationDetailsType(
							new BasicAmountType(currency,
									request.getParameter("initialAmount")));
					if (request.getParameter("failedInitialAmountAction") != "") {
						activationDetails
								.setFailedInitialAmountAction(FailedPaymentActionType.fromValue(request
										.getParameter("failedInitialAmountAction")));
					}
					scheduleDetails.setActivationDetails(activationDetails);
				}
				if (request.getParameter("trialBillingAmount") != "") {
					int frequency = Integer.parseInt(request
							.getParameter("trialBillingFrequency"));
					BasicAmountType paymentAmount = new BasicAmountType(
							currency,
							request.getParameter("trialBillingAmount"));
					BillingPeriodType period = BillingPeriodType
							.fromValue(request
									.getParameter("trialBillingPeriod"));
					int numCycles = Integer.parseInt(request
							.getParameter("trialBillingCycles"));
	
					BillingPeriodDetailsType trialPeriod = new BillingPeriodDetailsType(
							period, frequency, paymentAmount);
					trialPeriod.setTotalBillingCycles(numCycles);
					if (request.getParameter("trialShippingAmount") != "") {
						trialPeriod.setShippingAmount(new BasicAmountType(
								currency, request
										.getParameter("trialShippingAmount")));
					}
					if (request.getParameter("trialTaxAmount") != "") {
						trialPeriod.setTaxAmount(new BasicAmountType(currency,
								request.getParameter("trialTaxAmount")));
					}
	
					scheduleDetails.setTrialPeriod(trialPeriod);
				}
				if (request.getParameter("billingAmount") != "") {
					int frequency = Integer.parseInt(request
							.getParameter("billingFrequency"));
					BasicAmountType paymentAmount = new BasicAmountType(
							currency, request.getParameter("billingAmount"));
					BillingPeriodType period = BillingPeriodType
							.fromValue(request.getParameter("billingPeriod"));
	
					int numCycles = Integer.parseInt(request
							.getParameter("totalBillingCycles"));
	
					BillingPeriodDetailsType paymentPeriod = new BillingPeriodDetailsType(
							period, frequency, paymentAmount);
					paymentPeriod.setTotalBillingCycles(numCycles);
					if (request.getParameter("shippingAmount") != "") {
						paymentPeriod.setShippingAmount(new BasicAmountType(
								currency, request
										.getParameter("shippingAmount")));
					}
					if (request.getParameter("taxAmount") != "") {
						paymentPeriod.setTaxAmount(new BasicAmountType(
								currency, request
										.getParameter("taxAmount")));
					}
					scheduleDetails.setPaymentPeriod(paymentPeriod);
				}
	
				CreateRecurringPaymentsProfileRequestDetailsType reqDetails = new CreateRecurringPaymentsProfileRequestDetailsType(
						profileDetails, scheduleDetails);
				// Set EC-Token or Credit card details
				if (request.getParameter("token") != "")
					reqDetails.setToken(request.getParameter("token"));
				else if (request.getParameter("creditCardNumber") != "") {
					CreditCardDetailsType cc = new CreditCardDetailsType();
					cc.setCreditCardNumber(request.getParameter("creditCardNumber"));
					cc.setCVV2(request.getParameter("cvv"));
					cc.setExpMonth(Integer.parseInt(request.getParameter("expMonth")));
					cc.setExpYear(Integer.parseInt(request.getParameter("expYear")));
					PayerInfoType payerInfo= new PayerInfoType();
					payerInfo.setPayer(request.getParameter("BuyerEmailId"));
					cc.setCardOwner(payerInfo);
					CreditCardTypeType type = CreditCardTypeType.fromValue(request.getParameter("creditCardType"));
					switch(type){
						case AMEX:
							cc.setCreditCardType(CreditCardTypeType.AMEX);
							break;
						case VISA:
							cc.setCreditCardType(CreditCardTypeType.VISA);
							break;
						case DISCOVER:
							cc.setCreditCardType(CreditCardTypeType.DISCOVER);
							break;
						case MASTERCARD:
							cc.setCreditCardType(CreditCardTypeType.MASTERCARD);
							break;
					}
					
					reqDetails.setCreditCard(cc);
				}
	
				reqType.setCreateRecurringPaymentsProfileRequestDetails(reqDetails);
				req.setCreateRecurringPaymentsProfileRequest(reqType);
				CreateRecurringPaymentsProfileResponseType resp = service
						.createRecurringPaymentsProfile(req);
				if (resp == null) {
					UUID errorKey = Logger.error("createRecurringPaymentsProfile : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						
						//map.put("Profile ID",
						//		resp.getCreateRecurringPaymentsProfileResponseDetails()
						//				.getProfileID());
						//map.put("Transaction ID",
						//		resp.getCreateRecurringPaymentsProfileResponseDetails()
						//				.getTransactionID());
						//map.put("Profile Status",
						//		resp.getCreateRecurringPaymentsProfileResponseDetails()
						//				.getProfileStatus());
						
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("createRecurringPaymentsProfile: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "createRecurringPaymentsProfile : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		public PayOperationResult<GetRecurringPaymentsProfileDetailsResponseType> getRecurringPaymentsProfileDetails(){
			PayOperationResult<GetRecurringPaymentsProfileDetailsResponseType> result = new PayOperationResult<GetRecurringPaymentsProfileDetailsResponseType>();
			try {
				GetRecurringPaymentsProfileDetailsReq req = new GetRecurringPaymentsProfileDetailsReq();
				GetRecurringPaymentsProfileDetailsRequestType reqType = new GetRecurringPaymentsProfileDetailsRequestType(request.getParameter("profileID"));
				req.setGetRecurringPaymentsProfileDetailsRequest(reqType);
				GetRecurringPaymentsProfileDetailsResponseType resp = service
						.getRecurringPaymentsProfileDetails(req);
				if (resp == null) {
					UUID errorKey = Logger.error("getRecurringPaymentsProfileDetails : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("Profile ID", resp.getGetRecurringPaymentsProfileDetailsResponseDetails().getProfileID());
						//map.put("Profile Status", resp.getGetRecurringPaymentsProfileDetailsResponseDetails().getProfileStatus());
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("getRecurringPaymentsProfileDetails: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "getRecurringPaymentsProfileDetails : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		public PayOperationResult<ManageRecurringPaymentsProfileStatusResponseType> manageRecurringPaymentsProfileStatus(){
			PayOperationResult<ManageRecurringPaymentsProfileStatusResponseType> result = new PayOperationResult<ManageRecurringPaymentsProfileStatusResponseType>();
			try {
				ManageRecurringPaymentsProfileStatusReq req = new ManageRecurringPaymentsProfileStatusReq();
				ManageRecurringPaymentsProfileStatusRequestType reqType = new ManageRecurringPaymentsProfileStatusRequestType();
				ManageRecurringPaymentsProfileStatusRequestDetailsType reqDetails = new ManageRecurringPaymentsProfileStatusRequestDetailsType(
						request.getParameter("profileID"),
						StatusChangeActionType.fromValue(request
								.getParameter("action")));
				reqDetails.setNote("change");
				reqType.setManageRecurringPaymentsProfileStatusRequestDetails(reqDetails);
				req.setManageRecurringPaymentsProfileStatusRequest(reqType);
				ManageRecurringPaymentsProfileStatusResponseType resp = service
						.manageRecurringPaymentsProfileStatus(req);
				if (resp == null) {
					UUID errorKey = Logger.error("manageRecurringPaymentsProfileStatus : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("Profile ID",
						//		resp.getManageRecurringPaymentsProfileStatusResponseDetails()
						//				.getProfileID());
						//session.setAttribute("map", map);
						//response.sendRedirect("/merchant-sample/Response.jsp");
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("manageRecurringPaymentsProfileStatus: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "manageRecurringPaymentsProfileStatus : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		public PayOperationResult<UpdateRecurringPaymentsProfileResponseType> updateRecurringPaymentsProfile(){
			PayOperationResult<UpdateRecurringPaymentsProfileResponseType> result = new PayOperationResult<UpdateRecurringPaymentsProfileResponseType>();
			try {
				UpdateRecurringPaymentsProfileReq req = new UpdateRecurringPaymentsProfileReq();
				UpdateRecurringPaymentsProfileRequestType reqType = new UpdateRecurringPaymentsProfileRequestType();
				UpdateRecurringPaymentsProfileRequestDetailsType reqDetails = new UpdateRecurringPaymentsProfileRequestDetailsType(
						request.getParameter("profileID"));
				reqDetails.setNote("change");
				if (request.getParameter("creditCardNumber") != "") {
					CreditCardDetailsType cc = new CreditCardDetailsType();
					cc.setCreditCardNumber(request
							.getParameter("creditCardNumber"));
					cc.setCVV2(request.getParameter("cvv"));
					cc.setExpMonth(Integer.parseInt(request
							.getParameter("expMonth")));
					cc.setExpYear(Integer.parseInt(request
							.getParameter("expYear")));
					PayerInfoType payerInfo= new PayerInfoType();
					payerInfo.setPayer(request.getParameter("BuyerEmailId"));
					cc.setCardOwner(payerInfo);
					CreditCardTypeType type = CreditCardTypeType.fromValue(request.getParameter("creditCardType"));
					switch(type){
						case AMEX:
							cc.setCreditCardType(CreditCardTypeType.AMEX);
							break;
						case VISA:
							cc.setCreditCardType(CreditCardTypeType.VISA);
							break;
						case DISCOVER:
							cc.setCreditCardType(CreditCardTypeType.DISCOVER);
							break;
						case MASTERCARD:
							cc.setCreditCardType(CreditCardTypeType.MASTERCARD);
							break;
					}
					reqDetails.setCreditCard(cc);
				}
				reqDetails.setBillingStartDate(request
						.getParameter("billingStartDate") + "T00:00:00:000Z");
	
				if (request.getParameter("trialBillingAmount") != "") {
					int frequency = Integer.parseInt(request
							.getParameter("trialBillingFrequency"));
					BasicAmountType paymentAmount = new BasicAmountType(
							currency,
							request.getParameter("trialBillingAmount"));
					BillingPeriodType period = BillingPeriodType
							.fromValue(request
									.getParameter("trialBillingPeriod"));
					int numCycles = Integer.parseInt(request
							.getParameter("trialBillingCycles"));
	
					BillingPeriodDetailsType_Update trialPeriod = new BillingPeriodDetailsType_Update();
					trialPeriod.setBillingPeriod(period);
					trialPeriod.setBillingFrequency(frequency);
					trialPeriod.setAmount(paymentAmount);
					trialPeriod.setTotalBillingCycles(numCycles);
					if (request.getParameter("trialShippingAmount") != "") {
						trialPeriod.setShippingAmount(new BasicAmountType(
								currency, request
										.getParameter("trialShippingAmount")));
					}
					if (request.getParameter("trialTaxAmount") != "") {
						trialPeriod.setTaxAmount(new BasicAmountType(currency,
								request.getParameter("trialTaxAmount")));
					}
	
					reqDetails.setTrialPeriod(trialPeriod);
				}
				if (request.getParameter("billingAmount") != "") {
					int frequency = Integer.parseInt(request
							.getParameter("billingFrequency"));
					BasicAmountType paymentAmount = new BasicAmountType(
							currency, request.getParameter("billingAmount"));
					BillingPeriodType period = BillingPeriodType
							.fromValue(request.getParameter("billingPeriod"));
	
					int numCycles = Integer.parseInt(request
							.getParameter("totalBillingCycles"));
	
					BillingPeriodDetailsType_Update paymentPeriod = new BillingPeriodDetailsType_Update();
					paymentPeriod.setBillingPeriod(period);
					paymentPeriod.setBillingFrequency(frequency);
					paymentPeriod.setAmount(paymentAmount);
					paymentPeriod.setTotalBillingCycles(numCycles);
					if (request.getParameter("billingShippingAmount") != "") {
						paymentPeriod.setShippingAmount(new BasicAmountType(
								currency, request
										.getParameter("billingShippingAmount")));
					}
					if (request.getParameter("billingTaxAmount") != "") {
						paymentPeriod.setTaxAmount(new BasicAmountType(
								currency, request
										.getParameter("billingTaxAmount")));
					}
					reqDetails.setPaymentPeriod(paymentPeriod);
				}
				if (request.getParameter("maxFailedPayments") != "") {
					reqDetails.setMaxFailedPayments(Integer.parseInt(request
							.getParameter("maxFailedPayments")));
				}
				if (request.getParameter("profileDescription") != "") {
					reqDetails.setDescription(request
							.getParameter("profileDescription"));
				}
				if (request.getParameter("additionalBillingCycles") != "") {
					reqDetails.setAdditionalBillingCycles(Integer
							.parseInt(request
									.getParameter("additionalBillingCycles")));
				}
				if (request.getParameter("autoBillOutstandingAmount") != "") {
					reqDetails
							.setAutoBillOutstandingAmount(AutoBillType.fromValue(request
									.getParameter("autoBillOutstandingAmount")));
				}
	
				if (request.getParameter("amount") != "") {
					reqDetails.setAmount(new BasicAmountType(currency, request
							.getParameter("amount")));
				}
				if (request.getParameter("shippingAmount") != "") {
					reqDetails.setShippingAmount(new BasicAmountType(currency, request
							.getParameter("shippingAmount")));
				}
				if (request.getParameter("taxAmount") != "") {
					reqDetails.setTaxAmount(new BasicAmountType(currency, request
							.getParameter("taxAmount")));
				}
	
				if (request.getParameter("subscriberName") != "") {
					reqDetails.setSubscriberName(request
							.getParameter("subscriberName"));
				} else if (request.getParameter("shippingName") != "") {
					AddressType shippingAddr = new AddressType();
					shippingAddr.setName(request.getParameter("shippingName"));
					shippingAddr.setStreet1(request
							.getParameter("shippingStreet1"));
					shippingAddr.setStreet2(request
							.getParameter("shippingStreet2"));
					shippingAddr
							.setPhone(request.getParameter("shippingPhone"));
					shippingAddr.setCityName(request
							.getParameter("shippingCity"));
					shippingAddr.setStateOrProvince(request
							.getParameter("shippingState"));
					shippingAddr.setCountryName(request
							.getParameter("shippingCountry"));
					shippingAddr.setPostalCode(request
							.getParameter("shippingPostalCode"));
					reqDetails.setSubscriberShippingAddress(shippingAddr);
				}
	
				reqType.setUpdateRecurringPaymentsProfileRequestDetails(reqDetails);
				req.setUpdateRecurringPaymentsProfileRequest(reqType);
				UpdateRecurringPaymentsProfileResponseType resp = service
						.updateRecurringPaymentsProfile(req);
				if (resp == null) {
					UUID errorKey = Logger.error("updateRecurringPaymentsProfile : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("Profile ID",
						//		resp.getUpdateRecurringPaymentsProfileResponseDetails()
						//				.getProfileID());
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("updateRecurringPaymentsProfile: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "updateRecurringPaymentsProfile : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		*/
		
		public PayOperationResult<BillOutstandingAmountResponseType> billOutstandingAmount(String profileId, Double amount, CurrencyCodeType currencyCode){
			PayOperationResult<BillOutstandingAmountResponseType> result = new PayOperationResult<BillOutstandingAmountResponseType>();
			try {
				BillOutstandingAmountReq req = new BillOutstandingAmountReq();
				BillOutstandingAmountRequestType reqType = new BillOutstandingAmountRequestType();
				BillOutstandingAmountRequestDetailsType reqDetails = new BillOutstandingAmountRequestDetailsType(profileId);
				if (null != amount)
					reqDetails.setAmount(new BasicAmountType(currencyCode, amount.toString()));
				//reqType.setVersion("84");
				reqType.setBillOutstandingAmountRequestDetails(reqDetails);
				req.setBillOutstandingAmountRequest(reqType);
				BillOutstandingAmountResponseType resp = service
						.billOutstandingAmount(req);
				if (resp != null) {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("Profile ID", resp
						//		.getBillOutstandingAmountResponseDetails()
						//		.getProfileID());
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("updateRecurringPaymentsProfile: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "updateRecurringPaymentsProfile : Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
	}
	
	public class Onboard {
		private PayPalAPIInterfaceServiceService service;
		public Onboard(PayPalAPIInterfaceServiceService service) {
			this.service = service;
		}
		/*
		public PayOperationResult<EnterBoardingResponseType> enterBoarding(){
			PayOperationResult<EnterBoardingResponseType> result = new PayOperationResult<EnterBoardingResponseType>();
			try {
				EnterBoardingReq req = new EnterBoardingReq();
				EnterBoardingRequestDetailsType reqDetails = new EnterBoardingRequestDetailsType();
				reqDetails.setProgramCode(request.getParameter("programCode"));
				reqDetails.setProductList(request.getParameter("prodList"));
				if (request.getParameter("accNum") != null) {
					BankAccountDetailsType bankAccountDetails = new BankAccountDetailsType();
					bankAccountDetails.setAccountNumber(request
							.getParameter("accNum"));
					bankAccountDetails.setName(request.getParameter("accName"));
					bankAccountDetails.setType(BankAccountTypeType
							.fromValue(request.getParameter("accType")));
					reqDetails.setBankAccount(bankAccountDetails);
				}
				BusinessInfoType businessInfo = new BusinessInfoType();
				AddressType address = new AddressType();
				address.setName(request.getParameter("name"));
				address.setStreet1(request.getParameter("street"));
				address.setCityName(request.getParameter("city"));
				address.setStateOrProvince(request.getParameter("state"));
				address.setCountryName(request.getParameter("countryCode"));
				address.setCountry(CountryCodeType.fromValue(request
						.getParameter("countryCode")));
				address.setPostalCode(request.getParameter("postalCode"));
				businessInfo.setAddress(address);
				businessInfo.setCategory(BusinessCategoryType.fromValue(request
						.getParameter("businessCategory")));
				businessInfo.setName(request.getParameter("businessName"));
				businessInfo.setType(BusinessTypeType.fromValue(request
						.getParameter("businessType")));
				businessInfo
						.setAverageMonthlyVolume(AverageMonthlyVolumeType
								.fromValue(request
										.getParameter("averageMonthlyVolume")));
				businessInfo.setAveragePrice(AverageTransactionPriceType
						.fromValue(request.getParameter("averageTransPrice")));
				businessInfo
						.setRevenueFromOnlineSales(PercentageRevenueFromOnlineSalesType
								.fromValue(request
										.getParameter("revenuePercentage")));
				reqDetails.setBusinessInfo(businessInfo);
				reqDetails.setMarketingCategory(MarketingCategoryType
						.fromValue(request.getParameter("marketingCategory")));
				BusinessOwnerInfoType businessOwnerInfo = new BusinessOwnerInfoType();
				PayerInfoType payerInfo = new PayerInfoType();
				payerInfo.setPayer(request.getParameter("ownerMail"));
				payerInfo.setAddress(address);
				PersonNameType personName = new PersonNameType();
				personName.setFirstName(request.getParameter("firstName"));
				personName.setLastName(request.getParameter("lastName"));
				payerInfo.setPayerName(personName);
				businessOwnerInfo.setOwner(payerInfo);
				businessOwnerInfo.setHomePhone(request
						.getParameter("ownerPhone"));
				businessOwnerInfo.setSSN(request.getParameter("SSN"));
				reqDetails.setOwnerInfo(businessOwnerInfo);
				EnterBoardingRequestType reqType = new EnterBoardingRequestType(
						reqDetails);
				req.setEnterBoardingRequest(reqType);
				EnterBoardingResponseType resp = service.enterBoarding(req);
				if (resp == null) {
					UUID errorKey = Logger.error("enterBoarding : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("OnBoarding Token", resp.getToken());
	
						//map.put("RedirectUrl",
						//		"<a href=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_partner-onboard-flow&onboarding_token="
						//				+ resp.getToken()
						//				+ ">Redirect to https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_partner-onboard-flow&onboarding_token="
						//				+ resp.getToken() + "</a>");
						
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("enterBoarding: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "enterBoarding: Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		*/
		public PayOperationResult<GetBoardingDetailsResponseType> getBoardingDetails(String onboardingToken){
			PayOperationResult<GetBoardingDetailsResponseType> result = new PayOperationResult<GetBoardingDetailsResponseType>();
			try {
				GetBoardingDetailsReq req = new GetBoardingDetailsReq();
				GetBoardingDetailsRequestType reqType = new GetBoardingDetailsRequestType(onboardingToken);
				req.setGetBoardingDetailsRequest(reqType);
				GetBoardingDetailsResponseType resp = service.getBoardingDetails(req);
				if (resp == null) {
					UUID errorKey = Logger.error("getBoardingDetails : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("Account", resp
						//		.getGetBoardingDetailsResponseDetails()
						//		.getAccountOwner().getPayer());
						//map.put("Bank Account Verification Status", resp
						//		.getGetBoardingDetailsResponseDetails()
						//		.getBankAccountVerificationStatus());
						//map.put("Email Verification Status", resp
						//		.getGetBoardingDetailsResponseDetails()
						//		.getEmailVerificationStatus());
						//map.put("Boarding Status", resp
						//		.getGetBoardingDetailsResponseDetails()
						//		.getStatus());
						//map.put("Program Code", resp
						//		.getGetBoardingDetailsResponseDetails()
						//		.getProgramCode());
						
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("getBoardingDetails: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "enterBoarding: Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
	}
	
	public class Checkout{
		private PayPalAPIInterfaceServiceService service;
		public Checkout(PayPalAPIInterfaceServiceService service) {
			this.service = service;
		}
		
		/*
		public PayOperationResult<ISetExpressCheckoutResponse> setExpressCheckout(ISetExpressCheckoutRequest request){
			PayOperationResult<ISetExpressCheckoutResponse> result = new PayOperationResult<ISetExpressCheckoutResponse>();
			try {
				SetExpressCheckoutRequestType setExpressCheckoutReq = new SetExpressCheckoutRequestType();
				SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();
	
				details.setReturnURL(request.getReturnUrl());
				details.setCancelURL(request.getCancelUrl());
				details.setBuyerEmail(request.getBuyerMail());
				
				if(null != request.getBillingAgreement()){
					List<BillingAgreementDetailsType> billingAgreementDetailsList = new ArrayList<BillingAgreementDetailsType>();
					BillingAgreementDetailsType billingAgreementDetails = new BillingAgreementDetailsType();
					billingAgreementDetails.setBillingType(BillingCodeType.MERCHANTINITIATEDBILLINGSINGLEAGREEMENT);
					billingAgreementDetails.setBillingAgreementDescription(request.getBillingAgreement().getDescription());
					billingAgreementDetailsList.add(billingAgreementDetails);
					details.setBillingAgreementDetails(billingAgreementDetailsList);
				}
				
				double itemTotal = 0.00;
				double orderTotal = 0.00;
				// populate line item details
				
				List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
				for (IPaymentDetailsItem itemRequest : request.getPaymentDetailsList()) {
					PaymentDetailsItemType item = new PaymentDetailsItemType();
					BasicAmountType amt = new BasicAmountType();
					amt.setCurrencyID(CurrencyCodeType.fromValue(request
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
								.fromValue(request.getCurrencyCode()),
								String.valueOf(itemRequest.getSalesTax())));					
					}
					itemTotal += itemRequest.getItemQuantity() * itemRequest.getItemAmount();
					orderTotal += itemTotal;
				}
				
				List<PaymentDetailsType> payDetails = new ArrayList<PaymentDetailsType>();
				PaymentDetailsType paydtl = new PaymentDetailsType();
				paydtl.setPaymentAction(PaymentActionCodeType.SALE);
				
				if (null != request.getOrderDescription()) {
					paydtl.setOrderDescription(request
							.getOrderDescription());
				}
				
				BasicAmountType itemsTotal = new BasicAmountType();
				itemsTotal.setValue(Double.toString(itemTotal));
				itemsTotal.setCurrencyID(CurrencyCodeType.fromValue(request
						.getCurrencyCode()));
				paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType
						.fromValue(request.getCurrencyCode()),
						Double.toString(orderTotal)));
				paydtl.setPaymentDetailsItem(lineItems);
	
				paydtl.setItemTotal(itemsTotal);
				payDetails.add(paydtl);
				details.setPaymentDetails(payDetails);
				
				
				//if (null != request.getBillingAgreementText()) {
				//	BillingAgreementDetailsType billingAgreement = new BillingAgreementDetailsType(
				//			BillingCodeType.fromValue(request
				//					.getParameter("billingType")));
				//	billingAgreement.setBillingAgreementDescription(request
				//			.getBillingAgreementText());
				//	List<BillingAgreementDetailsType> billList = new ArrayList<BillingAgreementDetailsType>();
				//	billList.add(billingAgreement);
				//	details.setBillingAgreementDetails(billList);
				//}
				
				////shipping address
				//details.setReqConfirmShipping(request.getParameter("reqConfirmShipping"));
				//details.setAddressOverride(request.getParameter("addressoverride"));
				//AddressType shipToAddress=new AddressType();
				//shipToAddress.setName(request.getParameter("name"));
				//shipToAddress.setStreet1(request.getParameter("street1"));
				//shipToAddress.setStreet2(request.getParameter("street2"));
				//shipToAddress.setCityName(request.getParameter("city"));
				//shipToAddress.setStateOrProvince(request.getParameter("state"));
				//shipToAddress.setPostalCode(request.getParameter("postalCode"));
				//shipToAddress.setCountry(CountryCodeType.fromValue(request.getParameter("countryCode")));
				//details.setAddress(shipToAddress);
				
				//// shipping display options
				//details.setNoShipping(request.getParameter("noShipping"));
				
				//// PayPal page styling attributes
				//details.setBrandName(request.getParameter("brandName"));
				//details.setCustom(request.getParameter("pageStyle"));
				//details.setCppHeaderImage(request.getParameter("cppheaderimage"));
				//details.setCppHeaderBorderColor(request.getParameter("cppheaderbordercolor"));
				//details.setCppHeaderBackColor(request.getParameter("cppheaderbackcolor"));
				//details.setCppPayflowColor(request.getParameter("cpppayflowcolor"));
				//details.setAllowNote(request.getParameter("allowNote"));
				
				setExpressCheckoutReq
						.setSetExpressCheckoutRequestDetails(details);
	
				SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
				expressCheckoutReq
						.setSetExpressCheckoutRequest(setExpressCheckoutReq);
	
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
						result.setValue(resp);
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
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "setExpressCheckout: Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		*/
		
		public PayOperationResult<GetExpressCheckoutDetailsResponseType> getExpressCheckoutDetails(String token){
			PayOperationResult<GetExpressCheckoutDetailsResponseType> result = new PayOperationResult<GetExpressCheckoutDetailsResponseType>();
			try {
				GetExpressCheckoutDetailsReq req = new GetExpressCheckoutDetailsReq();
				GetExpressCheckoutDetailsRequestType reqType = new GetExpressCheckoutDetailsRequestType(
						token);
				req.setGetExpressCheckoutDetailsRequest(reqType);
				GetExpressCheckoutDetailsResponseType resp = service
						.getExpressCheckoutDetails(req);
				if (resp == null) {
					UUID errorKey = Logger.error("getExpressCheckoutDetails : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("Token", resp
						//		.getGetExpressCheckoutDetailsResponseDetails()
						//		.getToken());
						//map.put("Payer ID", resp
						//		.getGetExpressCheckoutDetailsResponseDetails()
						//		.getPayerInfo().getPayerID());
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("getExpressCheckoutDetails: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "getExpressCheckoutDetails: Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		/*
		Iterator<PaymentInfoType> iterator = resp
				.getDoExpressCheckoutPaymentResponseDetails()
				.getPaymentInfo().iterator();
		int index = 1;
		while (iterator.hasNext()) {
			PaymentInfoType result = (PaymentInfoType) iterator
					.next();
			map.put("Transaction ID" + index,
					result.getTransactionID());
			index++;
		}
		map.put("Billing Agreement ID",
				resp
						.getDoExpressCheckoutPaymentResponseDetails()
						.getBillingAgreementID());
		*/
		
		/*
		public PayOperationResult<DoExpressCheckoutPaymentResponseType> doExpressCheckout(IDoExpressCheckoutRequest request){
			PayOperationResult<DoExpressCheckoutPaymentResponseType> result = new PayOperationResult<DoExpressCheckoutPaymentResponseType>();
			try {
				DoExpressCheckoutPaymentRequestType doCheckoutPaymentRequestType = new DoExpressCheckoutPaymentRequestType();
				DoExpressCheckoutPaymentRequestDetailsType details = new DoExpressCheckoutPaymentRequestDetailsType();
				details.setToken(request.getToken());
				details.setPayerID(request.getPayerId());
				details.setPaymentAction(PaymentActionCodeType.ORDER);
				
				double itemTotalAmt = 0.00;
				double orderTotalAmt = 0.00;
				//String amt = request.getAmount();
				//String quantity = request.getItemQuantity();
				itemTotalAmt = request.getAmount()
						* request.getItemQuantity();
				orderTotalAmt += itemTotalAmt;
				PaymentDetailsType paymentDetails = new PaymentDetailsType();
				BasicAmountType orderTotal = new BasicAmountType();
				orderTotal.setValue(Double.toString(orderTotalAmt));
				orderTotal.setCurrencyID(CurrencyCodeType.fromValue(request
						.getCurrencyCode()));
				paymentDetails.setOrderTotal(orderTotal);
	
				BasicAmountType itemTotal = new BasicAmountType();
				itemTotal.setValue(Double.toString(itemTotalAmt));
	
				itemTotal.setCurrencyID(CurrencyCodeType.fromValue(request
						.getCurrencyCode()));
				paymentDetails.setItemTotal(itemTotal);
	
				List<PaymentDetailsItemType> paymentItems = new ArrayList<PaymentDetailsItemType>();
				for (IPaymentDetailsItem itemRequest : request.getPaymentDetailsList()) {
					PaymentDetailsItemType paymentItem = new PaymentDetailsItemType();
					paymentItem.setName(itemRequest.getItemName());
					paymentItem.setQuantity(itemRequest.getItemQuantity());
					BasicAmountType amount = new BasicAmountType();
					amount.setValue(String.valueOf(itemRequest.getAmount()));
					amount.setCurrencyID(CurrencyCodeType.fromValue(request
							.getCurrencyCode()));
					paymentItem.setAmount(amount);
					paymentItems.add(paymentItem);
				}
				paymentDetails.setPaymentDetailsItem(paymentItems);
	
				List<PaymentDetailsType> payDetailType = new ArrayList<PaymentDetailsType>();
				payDetailType.add(paymentDetails);
				details.setPaymentDetails(payDetailType);
	
				doCheckoutPaymentRequestType
						.setDoExpressCheckoutPaymentRequestDetails(details);
				DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
				doExpressCheckoutPaymentReq
						.setDoExpressCheckoutPaymentRequest(doCheckoutPaymentRequestType);
	
				DoExpressCheckoutPaymentResponseType resp = service
						.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
				
				if (resp == null) {
					UUID errorKey = Logger.error("doExpressCheckout : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString()
							.equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("doExpressCheckout: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "doExpressCheckout: Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		*/
		/*
			map.put("UATP Number", resp.getUATPDetails()
								.getUATPNumber());
			map.put("Expiry Month", resp.getUATPDetails()
					.getExpMonth());
			map.put("Expiry Year", resp.getUATPDetails()
					.getExpYear());
			Iterator<PaymentInfoType> iterator = resp
					.getDoExpressCheckoutPaymentResponseDetails()
					.getPaymentInfo().iterator();
			int index = 1;
			while (iterator.hasNext()) {
				PaymentInfoType result = (PaymentInfoType) iterator
						.next();
				map.put("Transaction ID" + index,
						result.getTransactionID());
				index++;
			}
			map.put("Billing Agreement ID", resp
					.getDoExpressCheckoutPaymentResponseDetails()
					.getBillingAgreementID());
		 */
		public PayOperationResult<DoUATPExpressCheckoutPaymentResponseType> doUatpExpressCheckout(String payerId, String token, double amount, String currencyCode){
			PayOperationResult<DoUATPExpressCheckoutPaymentResponseType> result = new PayOperationResult<DoUATPExpressCheckoutPaymentResponseType>();
			try {
				DoUATPExpressCheckoutPaymentReq req = new DoUATPExpressCheckoutPaymentReq();
				DoUATPExpressCheckoutPaymentRequestType reqType = new DoUATPExpressCheckoutPaymentRequestType();
	
				DoExpressCheckoutPaymentRequestDetailsType checkoutDetails = new DoExpressCheckoutPaymentRequestDetailsType();
				checkoutDetails.setPayerID(payerId);
				checkoutDetails.setToken(token);
				checkoutDetails.setPaymentAction(PaymentActionCodeType.ORDER);
				BasicAmountType amountArgs = new BasicAmountType(
						CurrencyCodeType.fromValue(currencyCode),
						String.valueOf(amount));
				PaymentDetailsType detailsType = new PaymentDetailsType();
				detailsType.setOrderTotal(amountArgs);
				List<PaymentDetailsType> payList = new ArrayList<PaymentDetailsType>();
				payList.add(detailsType);
				checkoutDetails.setPaymentDetails(payList);
				reqType.setDoExpressCheckoutPaymentRequestDetails(checkoutDetails);
				req.setDoUATPExpressCheckoutPaymentRequest(reqType);
				DoUATPExpressCheckoutPaymentResponseType resp = service
						.doUATPExpressCheckoutPayment(req);
	
				if (resp == null) {
					UUID errorKey = Logger.error("doUatpExpressCheckout : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("doUatpExpressCheckout: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "doUatpExpressCheckout: Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		public PayOperationResult<ExternalRememberMeOptOutResponseType> externalRememberMeOptOut(String externalRememberMeId, String ownerIdType, String ownerId){
			PayOperationResult<ExternalRememberMeOptOutResponseType> result = new PayOperationResult<ExternalRememberMeOptOutResponseType>();
			try {
				ExternalRememberMeOptOutReq req = new ExternalRememberMeOptOutReq();
				ExternalRememberMeOptOutRequestType reqType = new ExternalRememberMeOptOutRequestType(externalRememberMeId);
				ExternalRememberMeOwnerDetailsType externalRememberMeOwner = new ExternalRememberMeOwnerDetailsType();
				externalRememberMeOwner
						.setExternalRememberMeOwnerIDType(ownerIdType);
				externalRememberMeOwner.setExternalRememberMeOwnerID(ownerId);
				reqType.setExternalRememberMeOwnerDetails(externalRememberMeOwner);
				req.setExternalRememberMeOptOutRequest(reqType);
				ExternalRememberMeOptOutResponseType resp = service
						.externalRememberMeOptOut(req);
				if (resp == null) {
					UUID errorKey = Logger.error("externalRememberMeOptOut : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("externalRememberMeOptOut: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "externalRememberMeOptOut: Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		
		/*
		public PayOperationResult<ExecuteCheckoutOperationsResponseType> executeCheckoutOperations(){
			PayOperationResult<ExecuteCheckoutOperationsResponseType> result = new PayOperationResult<ExecuteCheckoutOperationsResponseType>();
			try {
				ExecuteCheckoutOperationsReq req = new ExecuteCheckoutOperationsReq();
				SetDataRequestType setDataRequest = new SetDataRequestType();
				List<BillingApprovalDetailsType> billingApprovalList = new ArrayList<BillingApprovalDetailsType>();
				BillingApprovalDetailsType billingApproval = new BillingApprovalDetailsType(
						ApprovalTypeType.fromValue(request
								.getParameter("billingApprovalType")));
				billingApproval.setApprovalSubType(ApprovalSubTypeType
						.fromValue(request
								.getParameter("billingApprovalSubType")));
				OrderDetailsType orderDetails = new OrderDetailsType();
				orderDetails.setMaxAmount(new BasicAmountType(CurrencyCodeType
						.fromValue(request.getParameter("currencyID")), request
						.getParameter("amt")));
				billingApproval.setOrderDetails(orderDetails);
				PaymentDirectivesType paymentDirectives = new PaymentDirectivesType();
				paymentDirectives.setPaymentType(MerchantPullPaymentCodeType
						.fromValue(request.getParameter("paymentType")));
				billingApproval.setPaymentDirectives(paymentDirectives);
				billingApprovalList.add(billingApproval);
				setDataRequest.setBillingApprovalDetails(billingApprovalList);
	
				BuyerDetailType buyerDetail = new BuyerDetailType();
				IdentificationInfoType identificationInfo = new IdentificationInfoType();
				if (request.getParameter("externalRememberMeID") != "") {
					RememberMeIDInfoType rememberMeIDInfo = new RememberMeIDInfoType();
					rememberMeIDInfo.setExternalRememberMeID(request
							.getParameter("externalRememberMeID"));
					identificationInfo.setRememberMeIDInfo(rememberMeIDInfo);
				}
				if (request.getParameter("sessionToken") != "") {
					MobileIDInfoType mobileIDInfo = new MobileIDInfoType();
					mobileIDInfo.setSessionToken(request
							.getParameter("sessionToken"));
					identificationInfo.setMobileIDInfo(mobileIDInfo);
				}
				buyerDetail.setIdentificationInfo(identificationInfo);
				setDataRequest.setBuyerDetail(buyerDetail);
	
				InfoSharingDirectivesType infoSharingDirectives = new InfoSharingDirectivesType();
				infoSharingDirectives.setReqBillingAddress(request
						.getParameter("reqBillingAddress"));
				setDataRequest.setInfoSharingDirectives(infoSharingDirectives);
	
				ExecuteCheckoutOperationsRequestDetailsType reqDetails = new ExecuteCheckoutOperationsRequestDetailsType(
						setDataRequest);
	
				AuthorizationRequestType authRequest = new AuthorizationRequestType(
						Boolean.parseBoolean(request
								.getParameter("isRequested")));
				reqDetails.setAuthorizationRequest(authRequest);
	
				ExecuteCheckoutOperationsRequestType reqType = new ExecuteCheckoutOperationsRequestType(
						reqDetails);
				req.setExecuteCheckoutOperationsRequest(reqType);
				ExecuteCheckoutOperationsResponseType resp = service
						.executeCheckoutOperations(req);
				if (resp == null) {
					UUID errorKey = Logger.error("externalRememberMeOptOut : No results");
					result.setError(PayErrorCode.NoResults, errorKey);
				} else {
					//session.setAttribute("lastReq", service.getLastRequest());
					//session.setAttribute("lastResp", service.getLastResponse());
					if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						result.setValue(resp);
						//map.put("Token", resp
						//		.getExecuteCheckoutOperationsResponseDetails()
						//		.getSetDataResponse().getToken());
					} else {
						JSONArray errors = new JSONArray(resp.getErrors());
						Logger.warn("externalRememberMeOptOut: validation failed. error(s): %s", errors);
						PayErrorCode errorCode;
						if(resp.getErrors().size() > 0){
							errorCode = identifyErrorCode(resp.getErrors().get(0));
						} else {
							errorCode = PayErrorCode.GeneralError;
						}
						result.setError(errorCode, UUID.randomUUID(), errors.toString());
					}
				}
			} catch (Exception e) {
				UUID errorKey = Logger.error(e, "externalRememberMeOptOut: Unexpected error occured");
				result.setError(PayErrorCode.GeneralError, errorKey);
			}
			return result;
		}
		*/
	}
	
	public String getRedirectUrl(){
		return PayConfiguration.Paypal.getServiceUrl();
	}
}

