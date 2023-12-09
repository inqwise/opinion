package com.inqwise.opinion.payments.common;

import java.util.Date;
import java.util.UUID;

public interface IPayActionRequest {
	Date getTimeStamp();
	Long getUserId();
	Long getAccountId();
	Long getBackofficeUserId();
	int getSourceId();
}
