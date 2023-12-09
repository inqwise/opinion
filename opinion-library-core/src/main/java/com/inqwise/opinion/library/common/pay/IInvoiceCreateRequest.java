package com.inqwise.opinion.library.common.pay;

import java.util.Date;

public interface IInvoiceCreateRequest {

	Long getForAccountId();
	Long getUserId();
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
}
