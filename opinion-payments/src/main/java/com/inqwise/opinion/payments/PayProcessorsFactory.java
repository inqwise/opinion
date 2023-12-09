package com.inqwise.opinion.payments;

import java.util.Hashtable;

import com.inqwise.opinion.payments.common.CreditCardTypes;
import com.inqwise.opinion.payments.common.IPayProcessor;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PayProcessorTypes;
import com.inqwise.opinion.payments.processors.paypal.PaypalProcessor;

public class PayProcessorsFactory {
	
	private Hashtable<PayProcessorTypes, IPayProcessor> processors;
	
	public IPayProcessor getProcessor(PayActionTypes actionType){
		return getProcessor(actionType, null);
	}
	
	public IPayProcessor getProcessor(PayActionTypes actionType, CreditCardTypes creditCardType){
		//TODO
		return getProcessor(PayProcessorTypes.Paypal);
	}
	
	public IPayProcessor getProcessor(PayProcessorTypes processorType){
		if(!processors.containsKey(processorType)){
			synchronized (processorType) {
				if(!processors.containsKey(processorType)){
					IPayProcessor processor = createProcessorByType(processorType);
					processors.put(processorType, processor);
				}
			}
		}
		return processors.get(processorType);
	}
	
	private static IPayProcessor createProcessorByType(
			PayProcessorTypes processorType) {
		IPayProcessor processor;
		switch (processorType) {
		case Paypal:
			processor = PaypalProcessor.getInstance();
			break;

		default:
			throw new Error("Processor not implemented. ProcessorType: " + processorType);
		}
		return processor;
	}

	private PayProcessorsFactory(){
		processors = new Hashtable<PayProcessorTypes, IPayProcessor>();
	}
	
	private static PayProcessorsFactory _instance;
	public static PayProcessorsFactory getInstance(){
		if(null == _instance){
			synchronized (PayProcessorsFactory.class) {
				if(null == _instance){
					_instance = new PayProcessorsFactory();
				}
			}
		}
		
		return _instance;
	}
}
