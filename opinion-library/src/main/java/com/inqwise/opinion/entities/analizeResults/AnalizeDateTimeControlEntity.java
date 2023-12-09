package com.inqwise.opinion.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.ControlType;

public class AnalizeDateTimeControlEntity extends AnalizeAdvancedControlEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnalizeDateTimeControlEntity.class);
	
	public static final String TIMEZONE_TITLE = "timeZoneTitle";
	public static final String MINUTES_TITLE = "minutesTitle";
	public static final String HOURS_TITLE = "hoursTitle";
	public static final String DAY_TITLE = "dayTitle";
	public static final String MONTH_TITLE = "monthTitle";
	public static final String YEAR_TITLE = "yearTitle";
	public static final String ANSWER_VALUE_KEY = "answerValue";
	private static final String ANSWER_TEXT_KEY = "answerText";
	
	private String yearTitle;
	private String monthTitle;
	private String dayTitle;
	private String hourTitle;
	private String minuteTitle;
	private String timezoneTitle;
	private String answerValue;
	private String answerText;
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
	throws SQLException, Exception {
		super.fill(reader, controlType);
		
		setYearTitle(ResultSetHelper.optString(reader, "year_title"));
		setMonthTitle(ResultSetHelper.optString(reader, "month_title"));
		setDayTitle(ResultSetHelper.optString(reader, "day_title"));
		setHourTitle(ResultSetHelper.optString(reader, "hour_title"));
		setMinuteTitle(ResultSetHelper.optString(reader, "minute_title"));
		setTimezoneTitle(ResultSetHelper.optString(reader, "timezone_title"));
		setAnswerValue(ResultSetHelper.optString(reader, "answer_value"));
		setAnswerText(ResultSetHelper.optString(reader, "answer_text"));
	}

	public void setYearTitle(String yearTitle) {
		this.yearTitle = yearTitle;
	}

	public String getYearTitle() {
		return yearTitle;
	}

	public void setMonthTitle(String monthTitle) {
		this.monthTitle = monthTitle;
	}

	public String getMonthTitle() {
		return monthTitle;
	}

	public void setDayTitle(String dayTitle) {
		this.dayTitle = dayTitle;
	}

	public String getDayTitle() {
		return dayTitle;
	}

	public void setHourTitle(String hourTitle) {
		this.hourTitle = hourTitle;
	}

	public String getHourTitle() {
		return hourTitle;
	}

	public void setMinuteTitle(String minuteTitle) {
		this.minuteTitle = minuteTitle;
	}

	public String getMinuteTitle() {
		return minuteTitle;
	}

	public void setTimezoneTitle(String timezoneTitle) {
		this.timezoneTitle = timezoneTitle;
	}

	public String getTimezoneTitle() {
		return timezoneTitle;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerValue() {
		return answerValue;
	}
	
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject jsonObject = super.toJson(onlyPercentageInStatistics);
		jsonObject.put(YEAR_TITLE, getYearTitle())
		.put(MONTH_TITLE, getMonthTitle())
		.put(DAY_TITLE, getDayTitle())
		.put(HOURS_TITLE, getHourTitle())
		.put(MINUTES_TITLE, getMinuteTitle())
		.put(TIMEZONE_TITLE, getTimezoneTitle())
		.put(ANSWER_VALUE_KEY, getAnswerValue());
		
		return jsonObject;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public String getAnswerText() {
		return answerText;
	}
}
