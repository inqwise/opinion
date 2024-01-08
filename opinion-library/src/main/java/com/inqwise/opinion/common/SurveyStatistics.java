package com.inqwise.opinion.common;

public class SurveyStatistics {

	private int countOfControls;
	private int countOfSessions;
	private int countOfFinishedSessions;
	
	public SurveyStatistics(int countOfControls, int countOfSessions,
			int countOfFinishedSessions) {
		this.setCountOfControls(countOfControls);
		this.setCountOfSessions(countOfSessions);
		this.setCountOfFinishedSessions(countOfFinishedSessions);
	}

	public void setCountOfControls(int countOfControls) {
		this.countOfControls = countOfControls;
	}

	public int getCountOfControls() {
		return countOfControls;
	}

	public void setCountOfSessions(int countOfSessions) {
		this.countOfSessions = countOfSessions;
	}

	public int getCountOfSessions() {
		return countOfSessions;
	}

	public void setCountOfFinishedSessions(int countOfFinishedSessions) {
		this.countOfFinishedSessions = countOfFinishedSessions;
	}

	public int getCountOfFinishedSessions() {
		return countOfFinishedSessions;
	}

}
