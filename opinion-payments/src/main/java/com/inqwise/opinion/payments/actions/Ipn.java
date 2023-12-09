package com.inqwise.opinion.payments.actions;

import com.inqwise.opinion.payments.common.IIpnRequest;
import com.inqwise.opinion.payments.common.IIpnResponse;
import com.inqwise.opinion.payments.processors.Processor;

public abstract class Ipn<TProcessor extends Processor> extends PayAction<IIpnRequest, TProcessor> implements IIpnResponse {

	public Ipn(TProcessor processor) {
		super(processor);
	}

}
