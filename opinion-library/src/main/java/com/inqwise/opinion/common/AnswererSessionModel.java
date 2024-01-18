package com.inqwise.opinion.common;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class AnswererSessionModel {

	private Boolean isUnplained;
	private String index;
	private String answerSessionId;
	private String countryName;
	private String clientIp;
	private String targetUrl;
	private Date finishDate;
	private Date insertDate;
	private Long timeTakenSec;
	private Long id;
	private Long collectorId;
	private String collectorName;
	private Boolean isCollectorExist;

	private AnswererSessionModel(Builder builder) {
		this.isUnplained = builder.isUnplained;
		this.index = builder.index;
		this.answerSessionId = builder.answerSessionId;
		this.countryName = builder.countryName;
		this.clientIp = builder.clientIp;
		this.targetUrl = builder.targetUrl;
		this.finishDate = builder.finishDate;
		this.insertDate = builder.insertDate;
		this.timeTakenSec = builder.timeTakenSec;
		this.id = builder.id;
		this.collectorId = builder.collectorId;
		this.collectorName = builder.collectorName;
		this.isCollectorExist = builder.isCollectorExist;
	}

	public static final class Keys{

		public static final String IS_UNPLAINED = "is_unplained";
		public static final String INDEX = "index";
		public static final String ANSWER_SESSION_ID = "answer_session_id";
		public static final String COUNTRY_NAME = "country_name";
		public static final String CLIENT_IP = "client_ip";
		public static final String TARGET_URL = "target_url";
		public static final String FINISH_DATE = "finish_date";
		public static final String INSERT_DATE = "insert_date";
		public static final String TIME_TAKEN_SEC = "time_taken_sec";
		public static final String ID = "id";
		public static final String COLLECTOR_ID = "collector_id";
		public static final String COLLECTOR_NAME = "collector_name";
		public static final String IS_COLLECTOR_EXIST = "is_collector_exist";
		
	}

	public AnswererSessionModel(JSONObject json) {
		isUnplained = json.optBooleanObject(Keys.IS_UNPLAINED);
		index = json.optString(Keys.INDEX);
		answerSessionId = json.optString(Keys.ANSWER_SESSION_ID);
		countryName = json.optString(Keys.COUNTRY_NAME);
		clientIp = json.optString(Keys.CLIENT_IP);
		targetUrl = json.optString(Keys.TARGET_URL);
		
		var finishDateInMs = json.optLongObject(Keys.FINISH_DATE);
		if(null != finishDateInMs) {
			finishDate = new Date(finishDateInMs);
		}
		
		var insertDateInMs = json.optLongObject(Keys.INSERT_DATE);
		if(null != insertDateInMs) {
			insertDate = new Date(insertDateInMs);
		}
		
		timeTakenSec = json.optLongObject(Keys.TIME_TAKEN_SEC);
		collectorId = json.optLongObject(Keys.COLLECTOR_ID);
		id = json.optLongObject(Keys.ID);
		collectorName = json.optString(Keys.COLLECTOR_NAME);
		isCollectorExist = json.optBooleanObject(Keys.IS_COLLECTOR_EXIST);
	}
	
	public Boolean getIsUnplained() {
		return isUnplained;
	}

	public String getIndex() {
		return index;
	}

	public String getAnswerSessionId() {
		return answerSessionId;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getClientIp() {
		return clientIp;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public Long getTimeTakenSec() {
		return timeTakenSec;
	}

	public Long getId() {
		return id;
	}

	public Long getCollectorId() {
		return collectorId;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public Boolean getIsCollectorExist() {
		return isCollectorExist;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.IS_UNPLAINED, isUnplained);
		json.put(Keys.INDEX, index);
		json.put(Keys.ANSWER_SESSION_ID, answerSessionId);
		json.put(Keys.COUNTRY_NAME, countryName);
		json.put(Keys.CLIENT_IP, clientIp);
		json.put(Keys.TARGET_URL, targetUrl);
		json.put(Keys.FINISH_DATE, finishDate);
		json.put(Keys.INSERT_DATE, insertDate);
		json.put(Keys.TIME_TAKEN_SEC, timeTakenSec);
		json.put(Keys.ID, id);
		json.put(Keys.COLLECTOR_ID, collectorId);
		json.put(Keys.COLLECTOR_NAME, collectorName);
		json.put(Keys.IS_COLLECTOR_EXIST, isCollectorExist);
		return json;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(AnswererSessionModel answererSessionModel) {
		return new Builder(answererSessionModel);
	}

	public static final class Builder {
		private Boolean isUnplained;
		private String index;
		private String answerSessionId;
		private String countryName;
		private String clientIp;
		private String targetUrl;
		private Date finishDate;
		private Date insertDate;
		private Long timeTakenSec;
		private Long id;
		private Long collectorId;
		private String collectorName;
		private Boolean isCollectorExist;

		private Builder() {
		}

		private Builder(AnswererSessionModel answererSessionModel) {
			this.isUnplained = answererSessionModel.isUnplained;
			this.index = answererSessionModel.index;
			this.answerSessionId = answererSessionModel.answerSessionId;
			this.countryName = answererSessionModel.countryName;
			this.clientIp = answererSessionModel.clientIp;
			this.targetUrl = answererSessionModel.targetUrl;
			this.finishDate = answererSessionModel.finishDate;
			this.insertDate = answererSessionModel.insertDate;
			this.timeTakenSec = answererSessionModel.timeTakenSec;
			this.id = answererSessionModel.id;
			this.collectorId = answererSessionModel.collectorId;
			this.collectorName = answererSessionModel.collectorName;
			this.isCollectorExist = answererSessionModel.isCollectorExist;
		}

		public Builder withIsUnplained(Boolean isUnplained) {
			this.isUnplained = isUnplained;
			return this;
		}

		public Builder withIndex(String index) {
			this.index = index;
			return this;
		}

		public Builder withAnswerSessionId(String answerSessionId) {
			this.answerSessionId = answerSessionId;
			return this;
		}

		public Builder withCountryName(String countryName) {
			this.countryName = countryName;
			return this;
		}

		public Builder withClientIp(String clientIp) {
			this.clientIp = clientIp;
			return this;
		}

		public Builder withTargetUrl(String targetUrl) {
			this.targetUrl = targetUrl;
			return this;
		}

		public Builder withFinishDate(Date finishDate) {
			this.finishDate = finishDate;
			return this;
		}

		public Builder withInsertDate(Date insertDate) {
			this.insertDate = insertDate;
			return this;
		}

		public Builder withTimeTakenSec(Long timeTakenSec) {
			this.timeTakenSec = timeTakenSec;
			return this;
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withCollectorId(Long collectorId) {
			this.collectorId = collectorId;
			return this;
		}

		public Builder withCollectorName(String collectorName) {
			this.collectorName = collectorName;
			return this;
		}

		public Builder withIsCollectorExist(Boolean isCollectorExist) {
			this.isCollectorExist = isCollectorExist;
			return this;
		}

		public AnswererSessionModel build() {
			return new AnswererSessionModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("isUnplained", isUnplained).add("index", index)
				.add("answerSessionId", answerSessionId).add("countryName", countryName).add("clientIp", clientIp)
				.add("targetUrl", targetUrl).add("finishDate", finishDate).add("insertDate", insertDate)
				.add("timeTakenSec", timeTakenSec).add("id", id).add("collectorId", collectorId)
				.add("collectorName", collectorName).add("isCollectorExist", isCollectorExist).toString();
	}

}
