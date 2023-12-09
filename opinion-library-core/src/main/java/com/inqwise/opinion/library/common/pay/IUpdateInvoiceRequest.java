package com.inqwise.opinion.library.common.pay;

import java.util.Date;

public interface IUpdateInvoiceRequest {
	long getInvoiceId();
	Long getModifyUserId();
	Date getInvoiceFromDate();
	Date getInvoiceToDate();
	String getCompanyName();
	String getFirstName();
	String getLastName();
	String getAddress1();
	String getAddress2();
	String getCity();
	Integer getStateId();
	String getPostalCode();
	int getCountryId();
	String getPhone1();
	String getNotes();
}
