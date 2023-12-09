package com.inqwise.opinion.library.common.pay;

import java.util.UUID;

public interface IChargePayRequest {

	long getAccountId();
	long getChargeId();
	Long getUserId();
	double getAmount();
	int getSourceId();
	String getSessionId();
	String getGeoCountryCode();
	String getClientIp();
	//UUID getProductGuid();
}