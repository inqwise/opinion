package com.inqwise.opinion.payments.processors.paypal;

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
import com.inqwise.opinion.payments.common.PayProcessorTypes;

public class PaypalIpnListener {

	private static final ApplicationLog Logger = ApplicationLog.getLogger(PaypalIpnListener.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6071404608765095406L;

	private void parseRequest(Map<String, String[]> requestParams, int sourceId) {
		
		try{
			StringBuffer strBuffer = new StringBuffer("cmd=_notify-validate");
			for (Entry<String, String[]> param : requestParams.entrySet()) {
				String paramName = param.getKey();
				String paramValue = param.getValue()[0];
				try {
					strBuffer.append("&").append(paramName).append("=")
							.append(URLEncoder.encode(paramValue, "utf-8"));
				} catch (UnsupportedEncodingException e) {
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
				for (Entry<String, String[]> param : requestParams.entrySet()) {
					sb.append(String.format("Key:'%s'", param.getKey()));
					String[] values = param.getValue();
					if(values!=null && values.length > 0) {
						sb.append(String.format(", Value:'%s'.", values[0]));
					}
				}
				Logger.info("PaypalIpnListener:parseRequest: Details: %s", sb);
			}
			
			// check notification validation
			if (res.equals("VERIFIED")) {
				processNotification(requestParams, sourceId);
				
			} else if (res.equals("INVALID")) {
				Logger.warn("PaypalIpnListener:parseRequest: Received request is invalid by paypal");
			} else {
				Logger.error("PaypalIpnListener:parseRequest: Paypal response format is uncorrect");
			}
		} catch (Exception ex){
			Logger.error(ex, "PaypalIpnListener:parseRequest: Unexpected error occured.");
		}
	}

	private void processNotification(Map<String, String[]> parameterMap, int sourceId) {
		
		PaymentStatusCodeType paymentStatus = PaymentStatusCodeType.fromValue(parameterMap.get("payment_status")[0]);
		switch(paymentStatus){
		case COMPLETED:
			completingTransaction(parameterMap, sourceId);
			break;
		case REFUNDED:
			completingRefund(parameterMap, sourceId);
			break;
		case PENDING:
			pendingTransaction(parameterMap);
			break;
		case DENIED:
			denyTransaction(parameterMap);
			break;
		case REVERSED:
			reverseTransaction(parameterMap);
			break;
		case CANCELEDREVERSAL:
			cancelReversalTransaction(parameterMap);
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
		
	private void cancelReversalTransaction(Map<String, String[]> parameterMap) {
		//TODO:
		throw new Error("Unimplemented method.");
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
	}

	private void reverseTransaction(Map<String, String[]> parameterMap) {
		//TODO:
		throw new Error("Unimplemented method.");
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
	}

	private void denyTransaction(Map<String, String[]> parameterMap) {
		//TODO:
		throw new Error("Unimplemented method.");
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
	}

	private void pendingTransaction(Map<String, String[]> parameterMap) {
		//TODO:
		throw new Error("Unimplemented method.");
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
	}

	private void completingTransaction(Map<String, String[]> parameterMap, int sourceId) {
		
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
		
		String txnId = parameterMap.get("txn_id")[0];
		double paymentAmount = Double.parseDouble(parameterMap.get("mc_gross")[0]);
		String paymentCurrency = parameterMap.get("mc_currency")[0];
		String receiverEmail = parameterMap.get("receiver_email")[0];
		
		PayProcessorTxnCompletedEventArgs args = new PayProcessorTxnCompletedEventArgs(sourceId);
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

	private void completingRefund(Map<String, String[]> parameterMap, int sourceId) {
		
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
		
		String txnId = parameterMap.get("txn_id")[0];
		String parentTxnId = parameterMap.get("parent_txn_id")[0];
		double paymentAmount = Double.parseDouble(parameterMap.get("mc_gross")[0]);
		String paymentCurrency = parameterMap.get("mc_currency")[0];
		
		PayProcessorRefundCompletedEventArgs args = new PayProcessorRefundCompletedEventArgs(sourceId);
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
