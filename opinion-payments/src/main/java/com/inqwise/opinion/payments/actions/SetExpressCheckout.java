package com.inqwise.opinion.payments.actions;

import java.util.Date;
import java.util.UUID;

import com.inqwise.opinion.payments.common.IPayActionRequest;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.ISetExpressCheckoutRequest;
import com.inqwise.opinion.payments.common.ISetExpressCheckoutResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.SetExpressCheckoutArgs;
import com.inqwise.opinion.payments.common.errorHandle.PayErrorCode;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.dao.cache.PayCacheAccess;
import com.inqwise.opinion.payments.dao.interfaces.IBeginPaymentArgs;
import com.inqwise.opinion.payments.dao.interfaces.ISetExpressCheckoutArgs;
import com.inqwise.opinion.payments.processors.Processor;

public abstract class SetExpressCheckout<TProcessor extends Processor> extends PayAction<ISetExpressCheckoutRequest, TProcessor> implements ISetExpressCheckoutResponse 
{ 
	public SetExpressCheckout(TProcessor processor) {
		super(processor);
	}
	
	@Override
	public void setRequest(IPayActionRequest request) {
		super.setRequest(request);
		args = new SetExpressCheckoutArgs<TProcessor>(getRequest());
		args.setProcessorTypeId(getProcessor().getProcessorType().getValue());
	}
	
	private SetExpressCheckoutArgs<TProcessor> args;
	protected ISetExpressCheckoutArgs getArgs(){
		return args;
	}
	
	public void setAmount(double amount) {
		args.setAmount(amount);
	}

	public double getAmount() {
		return args.getAmount();
	}

	public void setAmountCurrency(String amountCurrency) {
		args.setAmountCurrency(amountCurrency);
	}

	public String getAmountCurrency() {
		return args.getAmountCurrency();
	}

	public Long getUserId() {
		return args.getUserId();
	}

	public long getAccountId() {
		return args.getAccountId();
	}

	public int getProcessorTypeId() {
		return args.getProcessorTypeId();
	}

	public Date getRequestDate() {
		return args.getRequestDate();
	}
	
	public String getGeoCountryCode() {
		return args.getGeoCountryCode();
	}

	public String getClientIp() {
		return args.getClientIp();
	}

	public int getSourceId() {
		return args.getSourceId();
	}

	public String getSessionId() {
		return args.getSessionId();
	}

	public String getDetails() {
		return args.getDetails();
	}

	public Long getBackofficeUserId() {
		return args.getBackofficeUserId();
	}

	public Long getParentId() {
		return args.getParentId();
	}

	public String getToken() {
		return args.getToken();
	}
	
	public void setToken(String token) {
		args.setToken(token);
	}
	
	@Override
	public PayActionTypes getActionType() {
		return PayActionTypes.SetExpressCheckout;
	}
	
	public String getCurrencyCode() {
		return getRequest().getCurrencyCode();
	}
	
	protected void saveBeginPayment(IBeginPaymentArgs args) {
		PayCacheAccess.putBeginPaymentArgs(args);
	}
	
	@Override
	protected PayOperationResult<IPayActionResponse> validateAction() {
		
		PayOperationResult<IPayActionResponse> result = super.validateAction();
		return result;
	}
}
