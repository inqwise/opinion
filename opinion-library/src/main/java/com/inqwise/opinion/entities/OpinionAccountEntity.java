package com.inqwise.opinion.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.common.IOpinionAccount;

public class OpinionAccountEntity implements IOpinionAccount {
	private long accountId;
	private Long sessionsBalance;
	private int servicePackageId;
	private Date lastSessionsCreditDate;
	private int supplyDaysInterval;
	private int nextSupplySessionsCredit;
	private Date nextSessionsCreditDate;
	private boolean showWelcomeMessage;
	
	public OpinionAccountEntity(ResultSet reader) throws SQLException{
		setAccountId(reader.getLong("opinion_account_id"));
		setSessionsBalance(ResultSetHelper.optLong(reader, "sessions_balance"));
		setServicePackageId(reader.getInt("service_package_id"));
		setLastSessionsCreditDate(ResultSetHelper.optDate(reader, "last_sessions_credit_date"));
		setSupplyDaysInterval(reader.getInt("sps_supply_days_interval"));
		setNextSupplySessionsCredit(reader.getInt("sps_supplied_count_sessions"));
		setNextSessionsCreditDate(((null == getLastSessionsCreditDate() || (null != getSessionsBalance() && getSessionsBalance() > getNextSupplySessionsCredit())) ? null : DateUtils.addDays(getLastSessionsCreditDate(), getSupplyDaysInterval() + 1)));
		setShowWelcomeMessage(reader.getBoolean("show_welcome_message"));
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Override
	public long getAccountId() {
		return accountId;
	}

	public void setServicePackageId(int servicePackageId) {
		this.servicePackageId = servicePackageId;
	}

	@Override
	public int getServicePackageId() {
		return servicePackageId;
	}

	public void setNextSessionsCreditDate(Date nextSessionsCreditDate) {
		this.nextSessionsCreditDate = nextSessionsCreditDate;
	}

	@Override
	public Date getNextSessionsCreditDate() {
		return nextSessionsCreditDate;
	}

	public void setShowWelcomeMessage(boolean showWelcomeMessage) {
		this.showWelcomeMessage = showWelcomeMessage;
	}

	@Override
	public boolean isShowWelcomeMessage() {
		return showWelcomeMessage;
	}

	public void setSessionsBalance(Long sessionsBalance) {
		this.sessionsBalance = sessionsBalance;
	}

	@Override
	public Long getSessionsBalance() {
		return sessionsBalance;
	}

	public void setLastSessionsCreditDate(Date lastSessionsCreditDate) {
		this.lastSessionsCreditDate = lastSessionsCreditDate;
	}

	@Override
	public Date getLastSessionsCreditDate() {
		return lastSessionsCreditDate;
	}

	public void setSupplyDaysInterval(int supplyDaysInterval) {
		this.supplyDaysInterval = supplyDaysInterval;
	}

	@Override
	public int getSupplyDaysInterval() {
		return supplyDaysInterval;
	}

	public void setNextSupplySessionsCredit(int nextSupplySessionsCredit) {
		this.nextSupplySessionsCredit = nextSupplySessionsCredit;
	}

	@Override
	public int getNextSupplySessionsCredit() {
		return nextSupplySessionsCredit;
	}
}
