package com.inqwise.opinion.payments.processors.paypal.actions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import urn.ebay.apis.eBLBaseComponents.PaymentStatusCodeType;

import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.events.PayProcessorRefundCompletedEventArgs;
import com.inqwise.opinion.automation.common.events.PayProcessorTxnCompletedEventArgs;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.payments.actions.Ipn;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PayProcessorTypes;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.processors.paypal.PaypalProcessor;

public class PaypalIpnAction extends Ipn<PaypalProcessor> {

	static final ApplicationLog Logger = ApplicationLog.getLogger(PaypalIpnAction.class);
	
	public PaypalIpnAction(PaypalProcessor processor) {
		super(processor);
	}

	@Override
	public PayActionTypes getActionType() {
		return PayActionTypes.Ipn;
	}

	@Override
	protected PayOperationResult<IPayActionResponse> processAction() {
		processIpn();
		return null;
	}
	
	protected Map<String, String> getParams(){
		return getRequest().getParams();
	}
	
	private void processIpn() {
		
		try{
			StringBuffer strBuffer = new StringBuffer("cmd=_notify-validate");
			for (Entry<String, String> param : getRequest().getParams().entrySet()) {
				String paramName = param.getKey();
				String paramValue = param.getValue();
				try {
					strBuffer.append("&").append(paramName).append("=")
							.append(URLEncoder.encode(paramValue, "utf-8"));
				} catch (UnsupportedEncodingException e) {
					Logger.error("");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 
			// post back to PayPal system to validate
			// NOTE: change http: to https: in the following URL to verify using SSL (for increased security).
			// using HTTPS requires either Java 1.4 or greater, or Java Secure Socket Extension (JSSE)
			// and configured for older versions.
			//URL u = new URL("https://www.paypal.com/cgi-bin/webscr");
			
			URL u = new URL(PaypalProcessor.getInstance().getRedirectUrl());
			HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();
			uc.setDoOutput(true);
			uc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			uc.setRequestProperty("Host", "www.paypal.com");
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			pw.println(strBuffer.toString());
			pw.close();
		 
			BufferedReader in = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));
			String res = in.readLine();
			in.close();
		 
			
			if(Logger.IsInfoEnabled()){
				StringBuilder sb = new StringBuilder();
				for (Entry<String, String> param : getParams().entrySet()) {
					sb.append(String.format("Key:'%s'", param.getKey()));
					String value = param.getValue();
					sb.append(String.format(" Value:'%s'.", value));
				}
				Logger.info("PaypalIpnListener:parseRequest: Details: %s", sb);
			}
			
			// check notification validation
			if (res.equals("VERIFIED")) {
				processNotification();
				
			} else if (res.equals("INVALID")) {
				Logger.warn("PaypalIpnListener:parseRequest: Received request is invalid by paypal");
			} else {
				Logger.error("PaypalIpnListener:parseRequest: Paypal response format is uncorrect");
			}
		} catch (Exception ex){
			Logger.error(ex, "PaypalIpnListener:parseRequest: Unexpected error occured.");
		}
	}

	private void processNotification() {
		
		PaymentStatusCodeType paymentStatus = PaymentStatusCodeType.fromValue(getParams().get("payment_status"));
		switch(paymentStatus){
		case COMPLETED:
			completingTransaction();
			break;
		case REFUNDED:
			completingRefund();
			break;
		case PENDING:
			pendingTransaction();
			break;
		case DENIED:
			denyTransaction();
			break;
		case REVERSED:
			reverseTransaction();
			break;
		case CANCELEDREVERSAL:
			cancelReversalTransaction();
			break;
		default:
			Logger.error("processNotification : Unimplemented notification paymentStatus '%s' received. Check log for more details", paymentStatus);
			break;
		}
		
		/*
		IpnTransactionType transactionType = IpnTransactionType.fromString(parameterMap.get("txn_type")[0]);
		switch(transactionType){
		case WebAccept:
			break;
		default:
			Logger.error("processNotification : Unimplemented notification '%s' received. Check log for more details", transactionType);
			break;
		*/
	}
		
	private void cancelReversalTransaction() {
		//TODO:
		throw new Error("Unimplemented method.");
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
	}

	private void reverseTransaction() {
		//TODO:
		throw new Error("Unimplemented method.");
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
	}

	private void denyTransaction() {
		//TODO:
		throw new Error("Unimplemented method.");
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
	}

	private void pendingTransaction() {
		//TODO:
		throw new Error("Unimplemented method.");
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
	}

	private void completingTransaction() {
		
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
		
		String txnId = getParams().get("txn_id");
		double paymentAmount = Double.parseDouble(getParams().get("mc_gross"));
		String paymentCurrency = getParams().get("mc_currency");
		String receiverEmail = getParams().get("receiver_email");
		
		PayProcessorTxnCompletedEventArgs args = new PayProcessorTxnCompletedEventArgs(getRequest().getSourceId());
		args.setProcessorTransactionId(txnId);
		args.setPaymentAmount(paymentAmount);
		args.setPaymentCurrencyCode(paymentCurrency);
		args.setReceiverEmail(receiverEmail);
		args.setProcessorTypeId(PayProcessorTypes.Paypal.getValue());
		
		try {
			EventsServiceClient.getInstance().fire(args);
		} catch (Exception e) {
			Logger.error(e, "completingTransaction: Unexpected error occured");
		}
		
		/*
		// assign posted variables to local variables
		String itemName = request.getParameter("item_name");
		String itemNumber = request.getParameter("item_number");
		
		String paymentAmount = request.getParameter("mc_gross");
		String paymentCurrency = request.getParameter("mc_currency");
		String txnId = request.getParameter("txn_id");
		String receiverEmail = request.getParameter("receiver_email");
		String payerEmail = request.getParameter("payer_email");
		*/
	}

	private void completingRefund() {
		
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
		
		String txnId = getParams().get("txn_id");
		String parentTxnId = getParams().get("parent_txn_id");
		double paymentAmount = Double.parseDouble(getParams().get("mc_gross"));
		String paymentCurrency = getParams().get("mc_currency");
		
		PayProcessorRefundCompletedEventArgs args = new PayProcessorRefundCompletedEventArgs(getRequest().getSourceId());
		args.setProcessorTransactionId(txnId);
		args.setPaymentAmount(paymentAmount);
		args.setPaymentCurrencyCode(paymentCurrency);
		args.setProcessorTypeId(PayProcessorTypes.Paypal.getValue());
		args.setProcessorParentTransactionId(parentTxnId);
		
		try {
			EventsServiceClient.getInstance().fire(args);
		} catch (Exception e) {
			Logger.error(e, "completingRefund: Unexpected error occured");
		}
	}
		

}
