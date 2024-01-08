package com.inqwise.opinion.common;

import java.util.Date;

public interface IAccountServicePackage {
	long getAccountId();
	int getServicePackageId();
	Long getSessionsBalance();
	Date getLastSessionsCreditDate();
	int getNextSupplySessionsCredit();
	Date getNextSessionsCreditDate();
}
