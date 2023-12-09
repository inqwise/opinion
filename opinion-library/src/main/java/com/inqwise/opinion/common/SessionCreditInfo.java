package com.inqwise.opinion.opinion.common;

public class SessionCreditInfo {
	private long accountId;
	private Long sessionAmountBalance;
	private int servicePackageId;
	
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setSessionAmountBalance(Long sessionAmountBalance) {
		this.sessionAmountBalance = sessionAmountBalance;
	}
	public Long getSessionAmountBalance() {
		return sessionAmountBalance;
	}
	public void setServicePackageId(int servicePackageId) {
		this.servicePackageId = servicePackageId;
	}
	public int getServicePackageId() {
		return servicePackageId;
	}
}
