package com.inqwise.opinion.library.common.accounts;

public interface IAccountBillingSettingsChangeRequest {
	public abstract long getId();
	public abstract IDepositBounds getDebositBounds();
	
	public interface IDepositBounds {
		Double getMinDepositAmount();
		Double getMaxDepositAmount();
	}
}
