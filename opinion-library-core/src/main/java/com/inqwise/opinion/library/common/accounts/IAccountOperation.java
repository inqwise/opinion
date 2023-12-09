package com.inqwise.opinion.library.common.accounts;

import java.math.BigDecimal;
import java.util.Date;

public interface IAccountOperation {

	public interface JsonNames{

		String AMOUNT = "amount";}
	
	public abstract int getSourceId();

	public abstract Long getBackofficeUserId();

	public abstract AccountOperationsReferenceType getReferenceType();

	public abstract Long getReferenceId();

	public abstract String getComments();

	public abstract Date getModifyDate();

	public abstract String getClientIp();

	public abstract BigDecimal getBalance();

	public abstract BigDecimal getAmount();

	public abstract int getProductId();

	public abstract long getAccountId();

	public abstract long getUserId();

	public abstract AccountsOperationsType getOperationType();

	public abstract long getId();

}
