package com.inqwise.opinion.payments;

import java.util.Date;
import java.util.UUID;

import com.inqwise.opinion.payments.common.IPayAction;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.IPayProcessor;
import com.inqwise.opinion.payments.common.IRefundFullRequest;
import com.inqwise.opinion.payments.common.IRefundRequest;
import com.inqwise.opinion.payments.common.IRefundResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.RefundTypes;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.common.transactions.IPayTransaction;
import com.inqwise.opinion.payments.managers.TransactionsManager;

public class PaymentsSystem {

	public PaymentsSystem() {
		
	}
	
	public static PayOperationResult<IRefundResponse> makeRefund(final IRefundRequest request){
		PayOperationResult<IRefundResponse> result = new PayOperationResult<IRefundResponse>();
		
		IPayTransaction transaction = null;
		PayOperationResult<IPayTransaction> transactionResult = TransactionsManager.get(request.getPayTransactionId());
		if(transactionResult.hasError()){
			result.setError(transactionResult);
		} else {
			transaction = transactionResult.getValue();
		}
		
		if(!result.hasError()){
			IRefundFullRequest fullRequest = generateRefundFullRequest(request, transaction);
			
			IPayProcessor processor = PayProcessorsFactory.getInstance().getProcessor(transaction.getProcessorType());
			IPayAction action = processor.getAction(PayActionTypes.Refund);
			action.setRequest(fullRequest);
			PayOperationResult<IPayActionResponse> actionResult = action.process();
			if(actionResult.hasError()){
				result.setError(actionResult);
			} else {
				result.setValue((IRefundResponse) actionResult.getValue());
			}
		}
		
		return result;
	}

	private static IRefundFullRequest generateRefundFullRequest(
			final IRefundRequest request, final IPayTransaction transaction) {
		IRefundFullRequest fullRequest = new IRefundFullRequest() {
			
			@Override
			public Long getUserId() {
				return request.getUserId();
			}
			
			@Override
			public Date getTimeStamp() {
				return request.getTimeStamp();
			}
			
			@Override
			public int getSourceId() {
				return request.getSourceId();
			}
			
			@Override
			public Long getBackofficeUserId() {
				return request.getBackofficeUserId();
			}
			
			@Override
			public Long getAccountId() {
				return request.getAccountId();
			}
			
			@Override
			public RefundTypes getRefundType() {
				return request.getRefundType();
			}
			
			@Override
			public long getPayTransactionId() {
				return transaction.getId();
			}
			
			@Override
			public String getMemo() {
				return request.getMemo();
			}
			
			@Override
			public String getCurrencyCode() {
				return transaction.getAmountCurrencySymbol();
			}
			
			@Override
			public Double getAmount() {
				return request.getAmount();
			}

			@Override
			public String getProcessorTransactionId() {
				return transaction.getProcessorTransactionId();
			}
		};
		
		return fullRequest;
	}

}
