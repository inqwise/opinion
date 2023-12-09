package com.inqwise.opinion.payments.actions;

import java.util.Date;

import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.FireEventArgs;
import com.inqwise.opinion.automation.common.events.PaymentEventArgs;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.payments.common.IDoExpressCheckoutRequest;
import com.inqwise.opinion.payments.common.IDoExpressCheckoutResponse;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PaymentStatus;
import com.inqwise.opinion.payments.common.PaymentsError;
import com.inqwise.opinion.payments.common.errorHandle.PayBaseOperationResult;
import com.inqwise.opinion.payments.common.errorHandle.PayErrorCode;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.dao.PayDataAccess;
import com.inqwise.opinion.payments.dao.cache.PayCacheAccess;
import com.inqwise.opinion.payments.dao.interfaces.ICompletePaymentArgs;
import com.inqwise.opinion.payments.dao.interfaces.ISetExpressCheckoutArgs;
import com.inqwise.opinion.payments.processors.Processor;

public abstract class DoExpressCheckout<TProcessor extends Processor> extends PayAction<IDoExpressCheckoutRequest, TProcessor> implements IDoExpressCheckoutResponse, ICompletePaymentArgs {

	public DoExpressCheckout(TProcessor processor) {
		super(processor);
	}

	private String token;
	private String payerId;
	private ISetExpressCheckoutArgs beginPaymentArgs;
	private Date paymentDate;
	private int paymentStatusId;
	private String processorTransactionId;
	private Long accountOperationId;
	private PaymentStatus paymentStatus;
	
	protected void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	protected void setProcessorTransactionId(String processorTransactionId) {
		this.processorTransactionId = processorTransactionId;
	}

	protected void setAccountOperationId(Long accountOperationId) {
		this.accountOperationId = accountOperationId;
	}

	@Override
	public double getAmount() {
		return beginPaymentArgs.getAmount();
	}

	@Override
	public String getAmountCurrency() {
		return beginPaymentArgs.getAmountCurrency();
	}

	@Override
	public Long getUserId() {
		return beginPaymentArgs.getUserId();
	}

	@Override
	public long getAccountId() {
		return beginPaymentArgs.getAccountId();
	}

	@Override
	public int getProcessorTypeId() {
		return getProcessor().getProcessorType().getValue();
	}

	@Override
	public Date getRequestDate() {
		return beginPaymentArgs.getRequestDate();
	}
	
	@Override
	public String getGeoCountryCode() {
		return beginPaymentArgs.getGeoCountryCode();
	}

	@Override
	public String getClientIp() {
		return beginPaymentArgs.getClientIp();
	}

	@Override
	public int getSourceId() {
		return beginPaymentArgs.getSourceId();
	}

	@Override
	public String getSessionId() {
		return beginPaymentArgs.getSessionId();
	}

	@Override
	public String getDetails() {
		return beginPaymentArgs.getDetails();
	}

	@Override
	public Long getBackofficeUserId() {
		return beginPaymentArgs.getBackofficeUserId();
	}

	@Override
	public Long getParentId() {
		return beginPaymentArgs.getParentId();
	}

	@Override
	public PayActionTypes getActionType() {
		return PayActionTypes.DoExpressCheckout;
	}
	
	@Override
	public Long getAccountOperationId() {
		return accountOperationId;
	}
	
	public String getPayerId() {
		return payerId;
	}
	
	public void setPayerId(String payerId){
		this.payerId = payerId;
	}
	
	@Override
	public String getRedirectUrl() {
		return beginPaymentArgs.getReturnUrl();
	}

	@Override
	public Date getPaymentDate() {
		return paymentDate;
	}

	@Override
	public int getPaymentStatusId() {
		return paymentStatusId;
	}

	@Override
	public String getProcessorTransactionId() {
		return processorTransactionId;
	}

	public void setAmount(double amount) {
		this.beginPaymentArgs.setAmount(amount);
	}

	public void setAmountCurrency(String amountCurrency) {
		this.beginPaymentArgs.setAmountCurrency(amountCurrency);
	}

	public void setPaymentStatus(PaymentStatus status) {
		this.paymentStatus = status;
	}

	@Override
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	
	@Override
	protected PayOperationResult<IPayActionResponse> processAction() {
		PayOperationResult<IPayActionResponse> result = null;
		/*try {
			getBeginPayment(this);
			return null;
		} catch (Exception ex){
			UUID errorId = Logger.error("processAction: failed to get BeginPayment");
			result = new PayOperationResult<IPayActionResponse>(PayErrorCode.GeneralError, errorId);
		}*/
		return result;
	}
	
	@Override
	protected PayOperationResult<IPayActionResponse> validateAction() {
		
		PayOperationResult<IPayActionResponse> result = super.validateAction();
		return result;
	}
	
	protected abstract PayBaseOperationResult parseQueryString();
	
	@Override
	protected PayBaseOperationResult collect(){
		PayBaseOperationResult result = parseQueryString();
		if(null == result){
			beginPaymentArgs = (ISetExpressCheckoutArgs) PayCacheAccess.getBeginPaymentArgs(getToken());
			if(null == beginPaymentArgs){
				result = new PayBaseOperationResult(PayErrorCode.InvalidToken);
			}
		}
		
		return result;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	protected void makePostActions(IPayActionResponse value) {
		super.makePostActions(value);
		try{
			FireEventArgs args = new PaymentEventArgs(beginPaymentArgs.getSourceId(), beginPaymentArgs.getUserId()
														, beginPaymentArgs.getAccountId(), beginPaymentArgs.getChargeIds()
														, getAccountOperationId())
									.withClient(beginPaymentArgs.getSourceId(), beginPaymentArgs.getSessionId()
												, beginPaymentArgs.getGeoCountryCode(), beginPaymentArgs.getClientIp());
			EventsServiceClient.getInstance().fire(args);
		} catch (Exception ex) {
			Logger.error(ex, "DoExpressCheckoutAction:makePostActions: failed to firePaymentEvent");
		}
		PayCacheAccess.removeBeginPaymentArgs(getToken());
	}
	
	protected Long savePayment(ICompletePaymentArgs args) {
		try {
			setAccountOperationId(PayDataAccess.setPaymentTransaction(args)); 
			return getAccountOperationId();
		} catch (DAOException e) {
			throw new PaymentsError(String.format("savePaymentDetails : Unexpected error occured. userId: '%s', transactionId: '%s'", args.getUserId(), args.getProcessorTransactionId()), e);
		}
	}
}
