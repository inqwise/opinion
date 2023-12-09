package com.inqwise.opinion.opinion.common;

import java.util.Date;

public interface IOpinionAccount {

	public abstract int getNextSupplySessionsCredit();

	public abstract int getSupplyDaysInterval();

	public abstract Date getLastSessionsCreditDate();

	public abstract Long getSessionsBalance();

	public abstract boolean isShowWelcomeMessage();

	public abstract Date getNextSessionsCreditDate();

	public abstract int getServicePackageId();

	public abstract long getAccountId();

}
