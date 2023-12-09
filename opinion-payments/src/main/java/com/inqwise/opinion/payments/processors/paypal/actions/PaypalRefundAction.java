package com.inqwise.opinion.payments.processors.paypal.actions;

import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.RefundTransactionReq;
import urn.ebay.api.PayPalAPI.RefundTransactionRequestType;
import urn.ebay.api.PayPalAPI.RefundTransactionResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AckCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.RefundType;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.payments.actions.Refund;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.RefundTypes;
import com.inqwise.opinion.payments.common.errorHandle.PayErrorCode;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.processors.paypal.PaypalProcessor;

public class PaypalRefundAction extends Refund {
	
	public PaypalRefundAction(PaypalProcessor processor) {
		super(processor);
	}

	static final ApplicationLog Logger = ApplicationLog.getLogger(PaypalRefundAction.class);
	
	@Override
	protected PayOperationResult<IPayActionResponse> processAction() {
		return Refund();
	}

	private PayOperationResult<IPayActionResponse> Refund() {
		PayOperationResult<IPayActionResponse> result = new PayOperationResult<IPayActionResponse>();
		//Logger.info("Start Refund for credit card of type '%s', number ends with '%s'", getRequest().getCreditCardType(), getRequest().getLast4DigitsOfCreditCardNumber());
		
		RefundTransactionReq request = new RefundTransactionReq();
		RefundTransactionRequestType refundRequest = new RefundTransactionRequestType();
		
		refundRequest.setTransactionID(getRequest().getProcessorTransactionId());
		refundRequest.setRefundType(RefundType.fromValue(getRequest().getRefundType().toString()));
		
		if(getRequest().getRefundType() == RefundTypes.Partial){
			//Start BasicAmount
			BasicAmountType basicAmount = new BasicAmountType();
			basicAmount.setCurrencyID(CurrencyCodeType.fromValue(getRequest().getCurrencyCode()));
			basicAmount.setValue(String.valueOf(getRequest().getAmount()));
			refundRequest.setAmount(basicAmount );
			//End BasicAmount
		}
		
		refundRequest.setMemo(getRequest().getMemo());
		request.setRefundTransactionRequest(refundRequest );
		PayPalAPIInterfaceServiceService service = getProcessor().getService();
		try {
			RefundTransactionResponseType refundResponse = service.refundTransaction(request);
			if(null != refundResponse){
				String strRequest = service.getLastRequest();
				String strResponse = service.getLastResponse();
				if(Logger.IsDebugEnabled()){
					Logger.debug("PayPalAPIInteraceServiceService info: LastRequest '%s', LastResponse '%s'", strRequest, strResponse);
				}
				if(refundResponse.getAck() == AckCodeType.SUCCESS){
					Logger.info("Refund Successful for payTransactionId '%s', Refund type '%s', refund transactionId '%s'", getRequest().getPayTransactionId(), getRequest().getRefundType(), refundResponse.getRefundTransactionID());
					
					setFeeRefundAmount(Double.parseDouble(refundResponse.getFeeRefundAmount().getValue()));
					setGrossRefundAmount(Double.parseDouble(refundResponse.getGrossRefundAmount().getValue()));
					setNetRefundAmount(Double.parseDouble(refundResponse.getNetRefundAmount().getValue()));
					setTotalRefundAmount(Double.parseDouble(refundResponse.getTotalRefundedAmount().getValue()));
					setRefundDate(PaypalProcessor.identifyDate(refundResponse.getTimestamp()));
					setRefundTransactionId(refundResponse.getRefundTransactionID());
					setRefundStatus(PaypalProcessor.identifyPaymentStatus(refundResponse.getRefundInfo().getRefundStatus()));
					setPendingReason(PaypalProcessor.identifyPendingReason(refundResponse.getRefundInfo().getPendingReason()));
					setAccountOperationId(saveRefund(this));
					result.setValue(this);
				} else {
					String strError = getErrorsString(refundResponse.getErrors());
					UUID ticketId = UUID.randomUUID();
					Logger.warn("Refund Successful for payTransactionId '%s', Refund type '%s', Count of errors: %s, Error(s): %s, ticketId: '%s'", getRequest().getPayTransactionId(), getRequest().getRefundType(), refundResponse.getErrors().size(), strError, ticketId.toString());
					ErrorType paypalError = selectError(refundResponse.getErrors());
					result.setError(getProcessor().identifyErrorCode(paypalError), ticketId, paypalError.getLongMessage());
				}
			}
		} catch (Exception e) {
			UUID errorKey = Logger.error(e, "Paypal.Refund : Unexpected error occured");
			result.setError(PayErrorCode.GeneralError, errorKey);
		}
		
		return result;
	}

	private String getErrorsString(List<ErrorType> errors) {
		JSONArray arr = new JSONArray();
		for (ErrorType error : errors) {
			arr.put(new JSONObject(error));
		}
		return arr.toString();
	}
	
	private ErrorType selectError(List<ErrorType> errors) {
		
		ErrorType result = errors.get(0);
		
		return result;
	}

	@Override
	public Long getUserId() {
		return getRequest().getUserId();
	}	
}
