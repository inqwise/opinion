package com.inqwise.opinion.opinion.common;

import java.util.Date;


public class AccountOpinionInfo {

	private int countOfCollectors;
	private int countOfOpinions;
	private int countOfStartedSessions;
	private int countOfFinishedSessions;
	private Date lastStartedDate;
	private Date lastFinishedDate;
	
	public AccountOpinionInfo(int countOfCollectors, int countOfOpinions, int countOfStartedSessions, int countOfFinishedSessions, Date lastStartedDate, Date lastFinishedDate) {
		this.setCountOfCollectors(countOfCollectors);
		this.setCountOfOpinions(countOfOpinions);
		setCountOfStartedSessions(countOfStartedSessions);
		setCountOfFinishedSessions(countOfFinishedSessions);
		setLastFinishedDate(lastFinishedDate);
		setLastStartedDate(lastStartedDate);
	}

	public void setCountOfCollectors(int countOfCollectors) {
		this.countOfCollectors = countOfCollectors;
	}

	public int getCountOfCollectors() {
		return countOfCollectors;
	}

	public void setCountOfOpinions(int countOfOpinions) {
		this.countOfOpinions = countOfOpinions;
	}

	public int getCountOfOpinions() {
		return countOfOpinions;
	}

	public void setCountOfStartedSessions(int countOfStartedSessions) {
		this.countOfStartedSessions = countOfStartedSessions;
	}

	public int getCountOfStartedSessions() {
		return countOfStartedSessions;
	}

	public void setCountOfFinishedSessions(int countOfFinishedSessions) {
		this.countOfFinishedSessions = countOfFinishedSessions;
	}

	public int getCountOfFinishedSessions() {
		return countOfFinishedSessions;
	}

	public void setLastStartedDate(Date lastStartedDate) {
		this.lastStartedDate = lastStartedDate;
	}

	public Date getLastStartedDate() {
		return lastStartedDate;
	}

	public void setLastFinishedDate(Date lastFinishedDate) {
		this.lastFinishedDate = lastFinishedDate;
	}

	public Date getLastFinishedDate() {
		return lastFinishedDate;
	}


}
