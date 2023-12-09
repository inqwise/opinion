package com.inqwise.opinion.payments.dao.interfaces;

import java.util.Date;

public interface IPaymentArgs extends IBasePaymentArgs {

	public abstract Date getPaymentDate();

	public abstract int getPaymentStatusId();

	public abstract String getProcessorTransactionId();

	public abstract int getTransactionTypeId();

}