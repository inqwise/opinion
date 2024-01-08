package com.inqwise.opinion.common.charts;

import java.util.Date;

public class ActivityChartDataItem {

	private Long countOfFinishedOpinions;
	private Long countOfStartedOpinions;
	private Date date;
	
	public ActivityChartDataItem(Date date) {
		this.date = date; 
		countOfFinishedOpinions = 0L;
		countOfStartedOpinions = 0L;
	}

	public void setCountOfFinishedOpinions(Long countOfFinishedOpinions) {
		this.countOfFinishedOpinions = countOfFinishedOpinions;
	}
	
	public Long getCountOfFinishedOpinions() {
		return countOfFinishedOpinions;
	}

	public void setCountOfStartedOpinions(Long countOfStartedOpinions) {
		this.countOfStartedOpinions = countOfStartedOpinions;
	}

	public Long getCountOfStartedOpinions() {
		return countOfStartedOpinions;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
