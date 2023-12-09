package com.inqwise.opinion.automation.common.events;

import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.automation.common.FireEventArgs;

public class PaymentEventArgs extends FireEventArgs {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2347803620578241119L;
	private long userId;
	private Long accountId;
	private List<Long> chargeIds;
	private Long accountOperationId;
	
	public PaymentEventArgs(int sourceId, long userId, Long accountId, List<Long> chargeIds, Long accountOperationId) {
		super(sourceId);
		setUserId(userId);
		setAccountId(accountId);
		setAccountOperationId(accountOperationId);
		setChargeIds(chargeIds);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public List<Long> getChargeIds() {
		return chargeIds;
	}

	public void setChargeIds(List<Long> chargeIds) {
		this.chargeIds = chargeIds;
	}

	public Long getAccountOperationId() {
		return accountOperationId;
	}

	public void setAccountOperationId(Long transactionId) {
		this.accountOperationId = transactionId;
	}

}
