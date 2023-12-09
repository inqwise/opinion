package com.inqwise.opinion.payments.common.transactions;

import java.util.Date;

import com.inqwise.opinion.payments.common.CreditCardTypes;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PayProcessorTypes;
import com.inqwise.opinion.payments.common.PaymentStatus;

public interface IPayTransaction {

	public abstract String getDetails();

	public abstract String getProcessorTransactionId();

	public abstract PaymentStatus getStatus();

	public abstract Date getProcessorTransactionDate();

	public abstract String getAmountCurrencySymbol();

	public abstract double getAmount();

	public abstract double getRequestAmount();

	public abstract Date getRequestDate();

	public abstract Integer getCreditCardNumber();

	public abstract PayProcessorTypes getProcessorType();

	public abstract long getAccountId();

	public abstract Long getUserId();

	public abstract Date getInsertDate();

	public abstract CreditCardTypes getCreditCardType();

	public abstract PayActionTypes getTransactionType();

	public abstract Long getParentId();

	public abstract long getId();

}
