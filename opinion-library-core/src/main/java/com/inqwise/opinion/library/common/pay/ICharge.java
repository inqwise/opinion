package com.inqwise.opinion.library.common.pay;

import java.util.Date;

public interface ICharge {

	interface JsonNames{

		String CHARGE_ID = "chargeId";
		String AMOUNT_DUE = "amountDue";}

	interface ResultSetNames {

		String CHARGE_ID = "charge_id";
		String AMOUNT = "amount";}

	public abstract BillType getBillTypeId();

	public abstract Long getBillId();

	public abstract Date getModifyDate();

	public abstract Date getInsertDate();

	public abstract String getDescription();

	public abstract String getName();

	public abstract long getId();

	public abstract ChargeStatus getStatus();

	public abstract Date getExpiryDate();

	public abstract double getBalance();

	public abstract double getAmount();
	
	public abstract double getAmountToFund();

	public abstract Long getReferenceId();

	public ChargeReferenceType getReferenceType();

	public abstract long getAccountId();

}
