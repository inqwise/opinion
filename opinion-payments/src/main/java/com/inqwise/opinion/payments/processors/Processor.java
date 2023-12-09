package com.inqwise.opinion.payments.processors;

import com.inqwise.opinion.payments.common.IPayProcessor;
import com.inqwise.opinion.payments.common.PayProcessorTypes;

public abstract class Processor implements IPayProcessor {

	public abstract PayProcessorTypes getProcessorType();
	
}
