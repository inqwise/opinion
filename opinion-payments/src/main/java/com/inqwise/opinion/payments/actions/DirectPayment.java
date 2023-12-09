package com.inqwise.opinion.payments.actions;

import java.util.Date;

import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.events.PaymentEventArgs;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.payments.common.IDirectPaymentRequest;
import com.inqwise.opinion.payments.common.IDirectPaymentResponse;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PaymentStatus;
import com.inqwise.opinion.payments.common.PaymentsError;
import com.inqwise.opinion.payments.common.errorHandle.PayErrorCode;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.dao.PayDataAccess;
import com.inqwise.opinion.payments.dao.interfaces.ICreditCardPaymentArgs;
import com.inqwise.opinion.payments.processors.Processor;

public abstract class DirectPayment<TProcessor extends Processor> extends PayAction<IDirectPaymentRequest, TProcessor> implements IDirectPaymentResponse, ICreditCardPaymentArgs {

	public DirectPayment(TProcessor processor) {
		super(processor);
	}

	private double amount;
	private String amountCurrency;
	private PaymentStatus paymentStatus;
	private Date paymentDate;
	private String processorTransactionId;
	private Long accountOperationId;

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public double getAmount() {
		return amount;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	@Override
	public String getAmountCurrency() {
		return amountCurrency;
	}

	public void setPaymentStatus(PaymentStatus status) {
		this.paymentStatus = status;
	}

	@Override
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setProcessorTransactionId(String processorTransactionId) {
		this.processorTransactionId = processorTransactionId;
	}

	@Override
	public String getProcessorTransactionId() {
		return processorTransactionId;
	}

	public void setAccountOperationId(Long accountOperationId) {
		this.accountOperationId = accountOperationId;
	}

	@Override
	public Long getAccountOperationId() {
		return accountOperationId;
	}
	
	@Override
	protected void makePostActions(IPayActionResponse value) {
		try{
			IDirectPaymentRequest request = getRequest();
			PaymentEventArgs args = new PaymentEventArgs(request.getSourceId(), request.getUserId(), request.getAccountId(), null, getAccountOperationId());
			EventsServiceClient.getInstance().fire(args);
		} catch (Exception ex) {
			Logger.error(ex, "DirectPaymentAction:makePostActions: failed to firePaymentEvent");
		}
		super.makePostActions(value);
	}

	@Override
	public Long getUserId() {
		return getRequest().getUserId();
	}

	@Override
	public long getAccountId() {
		return getRequest().getAccountId();
	}

	@Override
	public int getProcessorTypeId() {
		return getProcessor().getProcessorType().getValue();
	}

	@Override
	public String getLast4DigitsOfCreditCardNumber() {
		return getRequest().getLast4DigitsOfCreditCardNumber();
	}

	@Override
	public Date getRequestDate() {
		return getRequest().getTimeStamp();
	}
	
	@Override
	public int getPaymentStatusId() {
		return getPaymentStatus().getValue();
	}

	@Override
	public String getGeoCountryCode() {
		return getRequest().getGeoCountryCode();
	}

	@Override
	public String getClientIp() {
		return getRequest().getClientIp();
	}

	@Override
	public int getSourceId() {
		return getRequest().getSourceId();
	}

	@Override
	public String getSessionId() {
		return getRequest().getSessionId();
	}

	@Override
	public String getDetails() {
		return getRequest().getDetails();
	}

	@Override
	public Long getBackofficeUserId() {
		return getRequest().getBackofficeUserId();
	}

	@Override
	public Long getParentId() {
		return null;
	}

	@Override
	public PayActionTypes getActionType() {
		return PayActionTypes.DirectPayment;
	}
	
	@Override
	public Integer getCreditCardTypeId() {
		return getRequest().getCreditCardType().getValueOrNullWhenUndefined();
	}

	protected Long savePayment(ICreditCardPaymentArgs args) {
		try {
			setAccountOperationId(PayDataAccess.setPaymentTransaction(args));
			return getAccountOperationId();
		} catch (DAOException e) {
			throw new PaymentsError(String.format("savePaymentDetails : Unexpected error occured. userId: '%s', creditCard: '%s', transactionId: '%s'", args.getUserId(), args.getLast4DigitsOfCreditCardNumber(), args.getProcessorTransactionId()), e);
		}
	}
	
	@Override
	protected PayOperationResult<IPayActionResponse> validateAction() {
		
		PayOperationResult<IPayActionResponse> result = super.validateAction();
		if(null == result && getRequest().getAmount() <= 0){
			result = new PayOperationResult<IPayActionResponse>(PayErrorCode.InvalidAmount);
		} 
		return result;
	}
}
