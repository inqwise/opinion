package com.inqwise.opinion.library.common.pay;

import java.util.List;
import java.util.UUID;

public interface IOpenInvoiceRequest {
	public long getInvoiceId();
	public Long getAccountId();
	public List<Long> getChargesIds();
	public List<Long> getAccountOperationsIds();
	public Long getBackOfficeUserId();
	public String getClientIp();
	public String getGeoCountryCode();
	public UUID getSourceGuid();
	public double getTotalDebit();
	public double getTotalCredit();
}
