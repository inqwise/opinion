package com.inqwise.opinion.library.common.accounts;

public interface IAccountDetailsChangeRequest {
	public abstract Integer getTimezoneId();
	public abstract String getComments();
	public abstract Boolean isActive();
	public abstract String getName();
	public abstract Long getOwnerId();
	public abstract long getId();
	public abstract IDepositBounds getDebositBounds();
	
	public interface IDepositBounds {
		Double getMinDepositAmount();
		Double getMaxDepositAmount();
	}
}
