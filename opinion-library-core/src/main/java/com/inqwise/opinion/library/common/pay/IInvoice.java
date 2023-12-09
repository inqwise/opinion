package com.inqwise.opinion.library.common.pay;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface IInvoice {

	public abstract InvoiceStatus getStatus();

	public abstract Date getModifyDate();

	public abstract Long getForAccountId();

	public abstract Date getInsertDate();

	public abstract long getId();

	public Date getInvoiceDate();
	
	public BigDecimal getBalance();
	
	public BigDecimal getAmount();

	public abstract String getStateName();

	public abstract String getCountryName();

	public abstract Integer getCountryId();

	public abstract String getPhone1();

	public abstract String getPostalCode();

	public abstract Integer getStateId();

	public abstract String getCity();

	public abstract String getAddress2();

	public abstract String getAddress1();

	public abstract String getLastName();

	public abstract String getFirstName();

	public abstract String getCompanyName();

	public abstract Date getInvoiceToDate();

	public abstract Date getInvoiceFromDate();

	public abstract String getNotes();

	public abstract Double getTotalDebit();

	public abstract Double getTotalCredit();

}
