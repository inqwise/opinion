package com.inqwise.opinion.payments.dao.interfaces;

import java.util.Date;
import java.util.UUID;

interface IBasePaymentArgs {

	public abstract Long getUserId();

	public abstract long getAccountId();

	public abstract int getProcessorTypeId();

	public abstract Date getRequestDate();

	public abstract double getAmount();

	public abstract String getAmountCurrency();

	public abstract String getGeoCountryCode();

	public abstract String getClientIp();

	public abstract int getSourceId();

	public abstract String getSessionId();

	public abstract String getDetails();

	public abstract Long getBackofficeUserId();

	public abstract Long getParentId();

}