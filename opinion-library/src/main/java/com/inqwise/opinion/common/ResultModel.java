package com.inqwise.opinion.common;

import java.util.Date;

import org.json.JSONObject;
import com.google.common.base.MoreObjects;

public class ResultModel {

	private String answerSessionId;
	private String collectorName;
	private Long collectorId;
	private Date participantInsertDate;
	private String clientIp;
	private Boolean isCompleted;
	private String controlKey;
	private String controlContent;
	private Long controlId;
	private String answerValue;

	private ResultModel(Builder builder) {
		this.answerSessionId = builder.answerSessionId;
		this.collectorName = builder.collectorName;
		this.collectorId = builder.collectorId;
		this.participantInsertDate = builder.participantInsertDate;
		this.clientIp = builder.clientIp;
		this.isCompleted = builder.isCompleted;
		this.controlKey = builder.controlKey;
		this.controlContent = builder.controlContent;
		this.controlId = builder.controlId;
		this.answerValue = builder.answerValue;
	}
	
	public static final class Keys{

		public static final String ANSWER_SESSION = "answer_session";
		public static final String COLLECTOR_NAME = "collector_name";
		public static final String COLLECTOR_ID = "collector_id";
		public static final String PARTICIPANT_INSERT_DATE = "participant_insert_date";
		public static final String CLIENT_IP = "client_ip";
		public static final String IS_COMPLETED = "is_completed";
		public static final String CONTROL_KEY = "control_key";
		public static final String CONTROL_CONTENT = "control_content";
		public static final String CONTROL_ID = "control_id";
		public static final String ANSWER_VALUE = "answer_value";
		
	}
	
	public ResultModel(JSONObject json) {
		answerSessionId = json.optString(Keys.ANSWER_SESSION);
		collectorName = json.optString(Keys.COLLECTOR_NAME);
		collectorId = json.optLongObject(Keys.COLLECTOR_ID);
		
		var participantInsertDateInMs = json.optLongObject(Keys.PARTICIPANT_INSERT_DATE);
		if(null != participantInsertDateInMs) {
			participantInsertDate = new Date(participantInsertDateInMs);
		}
		
		clientIp = json.optString(Keys.CLIENT_IP);
		isCompleted = json.optBooleanObject(Keys.IS_COMPLETED);
		controlKey = json.optString(Keys.CONTROL_KEY);
		controlContent = json.optString(Keys.CONTROL_CONTENT);
		controlId = json.optLongObject(Keys.CONTROL_ID);
		answerValue = json.optString(Keys.ANSWER_VALUE);
	}
	
	public String getAnswerSessionId() {
		return answerSessionId;
	}
	
	public String getCollectorName() {
		return collectorName;
	}
	
	public Long getCollectorId() {
		return collectorId;
	}
	
	public Date getParticipantInsertDate() {
		return participantInsertDate;
	}
	
	public String getClientIp() {
		return clientIp;
	}
	
	public Boolean getIsCompleted() {
		return isCompleted;
	}
	
	public String getControlKey() {
		return controlKey;
	}
	
	public String getControlContent() {
		return controlContent;
	}
	
	public Long getControlId() {
		return controlId;
	}
	
	public String getAnswerValue() {
		return answerValue;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.ANSWER_SESSION, answerSessionId);
		json.put(Keys.COLLECTOR_NAME, collectorName);
		json.put(Keys.COLLECTOR_ID, collectorId);
		json.put(Keys.PARTICIPANT_INSERT_DATE, participantInsertDate);
		json.put(Keys.CLIENT_IP, clientIp);
		json.put(Keys.IS_COMPLETED, isCompleted);
		json.put(Keys.CONTROL_KEY, controlKey);
		json.put(Keys.CONTROL_CONTENT, controlContent);
		json.put(Keys.CONTROL_ID, controlId);
		json.put(Keys.ANSWER_VALUE, answerValue);
		return json;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(ResultModel resultModel) {
		return new Builder(resultModel);
	}

	public static final class Builder {
		private String answerSessionId;
		private String collectorName;
		private Long collectorId;
		private Date participantInsertDate;
		private String clientIp;
		private Boolean isCompleted;
		private String controlKey;
		private String controlContent;
		private Long controlId;
		private String answerValue;

		private Builder() {
		}

		private Builder(ResultModel resultModel) {
			this.answerSessionId = resultModel.answerSessionId;
			this.collectorName = resultModel.collectorName;
			this.collectorId = resultModel.collectorId;
			this.participantInsertDate = resultModel.participantInsertDate;
			this.clientIp = resultModel.clientIp;
			this.isCompleted = resultModel.isCompleted;
			this.controlKey = resultModel.controlKey;
			this.controlContent = resultModel.controlContent;
			this.controlId = resultModel.controlId;
			this.answerValue = resultModel.answerValue;
		}

		public Builder withAnswerSessionId(String answerSessionId) {
			this.answerSessionId = answerSessionId;
			return this;
		}

		public Builder withCollectorName(String collectorName) {
			this.collectorName = collectorName;
			return this;
		}

		public Builder withCollectorId(Long collectorId) {
			this.collectorId = collectorId;
			return this;
		}

		public Builder withParticipantInsertDate(Date participantInsertDate) {
			this.participantInsertDate = participantInsertDate;
			return this;
		}

		public Builder withClientIp(String clientIp) {
			this.clientIp = clientIp;
			return this;
		}

		public Builder withIsCompleted(Boolean isCompleted) {
			this.isCompleted = isCompleted;
			return this;
		}

		public Builder withControlKey(String controlKey) {
			this.controlKey = controlKey;
			return this;
		}

		public Builder withControlContent(String controlContent) {
			this.controlContent = controlContent;
			return this;
		}

		public Builder withControlId(Long controlId) {
			this.controlId = controlId;
			return this;
		}

		public Builder withAnswerValue(String answerValue) {
			this.answerValue = answerValue;
			return this;
		}

		public ResultModel build() {
			return new ResultModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("answerSessionId", answerSessionId)
				.add("collectorName", collectorName).add("collectorId", collectorId)
				.add("participantInsertDate", participantInsertDate).add("clientIp", clientIp)
				.add("isCompleted", isCompleted).add("controlKey", controlKey).add("controlContent", controlContent)
				.add("controlId", controlId).add("answerValue", answerValue).toString();
	}

}
