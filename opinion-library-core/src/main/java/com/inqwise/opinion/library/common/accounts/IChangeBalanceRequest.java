package com.inqwise.opinion.library.common.accounts;

import java.util.UUID;

public interface IChangeBalanceRequest {

	long getAccountId();

	Long getBackofficeUserId();

	String getComments();

	double getAmount();

	int getAccountOperationTypeId();

	UUID getSourceGuid();

	String getSessionId();

	String getGeoCountryCode();

	String getClientIp();

}
