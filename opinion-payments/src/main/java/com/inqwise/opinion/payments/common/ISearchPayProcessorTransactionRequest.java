package com.inqwise.opinion.payments.common;

import java.util.Date;

public interface ISearchPayProcessorTransactionRequest {

	Date getStartDate();
	Date getEndDate();
	String getTransactionId();

}
