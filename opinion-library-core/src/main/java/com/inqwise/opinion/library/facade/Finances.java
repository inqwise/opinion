package com.inqwise.opinion.library.facade;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.payments.PayActionsFactory;
import com.inqwise.opinion.payments.PayProcessorsFactory;
import com.inqwise.opinion.payments.common.IDirectPaymentRequest;
import com.inqwise.opinion.payments.common.IDirectPaymentResponse;
import com.inqwise.opinion.payments.common.IPayAction;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.IPayProcessor;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;

public class Finances {
	/*
	public static OperationResult<IDirectPaymentResponse> directPaymentIntoAccount(IDirectPaymentRequest request){
		IPayAction action = PayActionsFactory.getInstance().getCreditCardAction(PayActionTypes.DirectPayment, request.getCreditCardType());
		action.setRequest(request);
		PayOperationResult<IPayActionResponse> payResult = action.process();
		if(payResult.hasError()){
			
		}
	}
	
	public static BaseOperationResult setFundsIntoAccount()
	*/
}
