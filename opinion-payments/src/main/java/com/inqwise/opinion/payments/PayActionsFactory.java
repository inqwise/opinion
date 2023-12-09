package com.inqwise.opinion.payments;

import com.inqwise.opinion.payments.common.CreditCardTypes;
import com.inqwise.opinion.payments.common.IPayAction;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PayProcessorTypes;

public class PayActionsFactory {
	private static PayActionsFactory _instance;
	public static PayActionsFactory getInstance(){
		if(null == _instance){
			synchronized (PayProcessorsFactory.class) {
				if(null == _instance){
					_instance = new PayActionsFactory();
				}
			}
		}
		
		return _instance;
	}
	
	public IPayAction getCreditCardAction(PayActionTypes actionType, CreditCardTypes creditCardType){
		//TODO: find suitable processor by creditCard type
		return PayProcessorsFactory.getInstance().getProcessor(PayProcessorTypes.Paypal).getAction(actionType);
	}
}
