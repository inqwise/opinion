package com.inqwise.opinion.payments.actions;

import java.util.Date;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.IRefundFullRequest;
import com.inqwise.opinion.payments.common.IRefundResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PaymentStatus;
import com.inqwise.opinion.payments.common.PaymentsError;
import com.inqwise.opinion.payments.common.PendingReason;
import com.inqwise.opinion.payments.common.errorHandle.PayErrorCode;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.dao.IRefundArgs;
import com.inqwise.opinion.payments.dao.PayDataAccess;
import com.inqwise.opinion.payments.processors.paypal.PaypalProcessor;

public abstract class Refund extends PayAction<IRefundFullRequest, PaypalProcessor> implements IRefundResponse, IRefundArgs {
	
	
	private double feeRefundAmount;
	private double grossRefundAmount;
	private double netRefundAmount;
	private double totalRefundAmount;
	private Date refundDate;
	private String refundTransactionId;
	private PaymentStatus refundStatus;
	private PendingReason pendingReason;
	private long accountOperationId;

	public Refund(PaypalProcessor processor) {
		super(processor);
	}

	@Override
	protected void makePostActions(IPayActionResponse value) {
		try{
			IRefundFullRequest request = getRequest();
			//RefundEventArgs args = new RefundEventArgs(request.getSourceGuid(), request.getUserId(), request.getAccountId());
			//EventsProvider.getInstance().fireRefundEvent(args);
		} catch (Exception ex) {
			Logger.error(ex, "DirectPaymentAction:makePostActions: failed to firePaymentEvent");
		}
		super.makePostActions(value);
	}
	
	protected long saveRefund(IRefundArgs args){
		try {
			return PayDataAccess.setRefundTransaction(args);
		} catch (DAOException e) {
			throw new PaymentsError(String.format("saveRefund : Unexpected error occured. parentTransactionId: '%s', processorTransactionId: '%s'", args.getParentId(), args.getProcessorTransactionId()), e);
		}
	}

	public double getFeeRefundAmount() {
		return feeRefundAmount;
	}

	public void setFeeRefundAmount(double feeRefundAmount) {
		this.feeRefundAmount = feeRefundAmount;
	}

	public double getGrossRefundAmount() {
		return grossRefundAmount;
	}

	public void setGrossRefundAmount(double grossRefundAmount) {
		this.grossRefundAmount = grossRefundAmount;
	}

	public double getNetRefundAmount() {
		return netRefundAmount;
	}

	public void setNetRefundAmount(double netRefundAmount) {
		this.netRefundAmount = netRefundAmount;
	}

	public double getTotalRefundAmount() {
		return totalRefundAmount;
	}

	public void setTotalRefundAmount(double totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundTransactionId() {
		return refundTransactionId;
	}

	public void setRefundTransactionId(String refundTransactionId) {
		this.refundTransactionId = refundTransactionId;
	}

	public PaymentStatus getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(PaymentStatus refundStatus) {
		this.refundStatus = refundStatus;
	}

	public PendingReason getPendingReason() {
		return pendingReason;
	}

	public void setPendingReason(PendingReason pendingReason) {
		this.pendingReason = pendingReason;
	}

	public long getAccountOperationId() {
		return accountOperationId;
	}

	public void setAccountOperationId(long accountOperationId) {
		this.accountOperationId = accountOperationId;
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
	public Date getRequestDate() {
		return getRequest().getTimeStamp();
	}

	@Override
	public double getAmount() {
		return getRequest().getAmount();
	}

	@Override
	public String getAmountCurrency() {
		return getRequest().getCurrencyCode();
	}

	@Override
	public int getPaymentStatusId() {
		return getRefundStatus().getValue();
	}

	@Override
	public String getProcessorTransactionId() {
		return getRefundTransactionId();
	}

	@Override
	public int getSourceId() {
		return getRequest().getSourceId();
	}

	@Override
	public String getDetails() {
		return getRequest().getMemo();
	}

	@Override
	public long getBackofficeUserId() {
		return getRequest().getBackofficeUserId();
	}

	@Override
	public long getParentId() {
		return getRequest().getPayTransactionId();
	}

	@Override
	public PayActionTypes getActionType() {
		return PayActionTypes.Refund;
	}
	
	@Override
	public int getTransactionTypeId() {
		return getActionType().getValue();
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
