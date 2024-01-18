package com.inqwise.opinion.common.opinions;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class OpinionModel {
	
	private Long id;
	private OpinionType type;
	private String name;
	private Date modifyDate;
	private Long cntStartedOpinions;
	private Long cntFinishedOpinions;
	private String guid;
	private Date lastStartDate;
	private Double avgTimeTakenSec;
	private Long accountId;
	private Integer countControls;

	private OpinionModel(Builder builder) {
		this.id = builder.opinionId;
		this.type = builder.type;
		this.name = builder.name;
		this.modifyDate = builder.modifyDate;
		this.cntStartedOpinions = builder.cntStartedOpinions;
		this.cntFinishedOpinions = builder.cntFinishedOpinions;
		this.guid = builder.guid;
		this.lastStartDate = builder.lastStartDate;
		this.avgTimeTakenSec = builder.avgTimeTakenSec;
		this.accountId = builder.accountId;
		this.countControls = builder.countControls;
	}

	public static final class Keys{

		public static final String OPINION_ID = "id";
		public static final String TYPE_ID = "type_id";
		public static final String NAME = "name";
		public static final String MODIFY_DATE = "modify_date";
		public static final String CNT_STARTED_OPINIONS = "cnt_started_opinions";
		public static final String CNT_FINISHED_OPINIONS = "cnt_finished_opinions";
		public static final String GUID = "guid";
		public static final String LAST_START_DATE = "last_start_date";
		public static final String AVG_TIME_TAKEN_SEC = "avg_time_taken_sec";
		public static final String ACCOUNT_ID = "account_id";
		public static final String COUNT_CONTROLS = "count_controls";
		
	}

	public OpinionModel(JSONObject json) {
		id = json.optLongObject(Keys.OPINION_ID);
		
		var typeId = json.optIntegerObject(Keys.TYPE_ID);
		if(null != typeId) {
			type = OpinionType.fromInt(typeId);
		}
		name = json.optString(Keys.NAME);
		
		var modifyDateInMs = json.optLongObject(Keys.MODIFY_DATE);
		if(null != modifyDateInMs) {
			modifyDate = new Date(modifyDateInMs);
		}
		cntStartedOpinions = json.optLongObject(Keys.CNT_STARTED_OPINIONS);
		cntFinishedOpinions = json.optLongObject(Keys.CNT_FINISHED_OPINIONS);
		guid = json.optString(Keys.GUID);
		
		var lastStartDateInMs = json.optLongObject(Keys.LAST_START_DATE);
		if(null != lastStartDateInMs) {
			lastStartDate = new Date(lastStartDateInMs);
		}
		avgTimeTakenSec = json.optDoubleObject(Keys.AVG_TIME_TAKEN_SEC);
		accountId = json.optLongObject(Keys.ACCOUNT_ID);
		countControls = json.optIntegerObject(Keys.COUNT_CONTROLS);
	}
	
	public Long getId() {
		return id;
	}

	public OpinionType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public Long getCntStartedOpinions() {
		return cntStartedOpinions;
	}

	public Long getCntFinishedOpinions() {
		return cntFinishedOpinions;
	}

	public String getGuid() {
		return guid;
	}

	public Date getLastStartDate() {
		return lastStartDate;
	}

	public Double getAvgTimeTakenSec() {
		return avgTimeTakenSec;
	}

	public Long getAccountId() {
		return accountId;
	}

	public Integer getCountControls() {
		return countControls;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.OPINION_ID, id);
		json.put(Keys.TYPE_ID, type);
		json.put(Keys.NAME, name);
		json.put(Keys.MODIFY_DATE, modifyDate);
		json.put(Keys.CNT_STARTED_OPINIONS, cntStartedOpinions);
		json.put(Keys.CNT_FINISHED_OPINIONS, cntFinishedOpinions);
		json.put(Keys.GUID, guid);
		json.put(Keys.LAST_START_DATE, lastStartDate);
		json.put(Keys.AVG_TIME_TAKEN_SEC, avgTimeTakenSec);
		json.put(Keys.ACCOUNT_ID, accountId);
		json.put(Keys.COUNT_CONTROLS, countControls);
		return json;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(OpinionModel opinionModel) {
		return new Builder(opinionModel);
	}

	public static final class Builder {
		private Long opinionId;
		private OpinionType type;
		private String name;
		private Date modifyDate;
		private Long cntStartedOpinions;
		private Long cntFinishedOpinions;
		private String guid;
		private Date lastStartDate;
		private Double avgTimeTakenSec;
		private Long accountId;
		private Integer countControls;

		private Builder() {
		}

		private Builder(OpinionModel opinionModel) {
			this.opinionId = opinionModel.id;
			this.type = opinionModel.type;
			this.name = opinionModel.name;
			this.modifyDate = opinionModel.modifyDate;
			this.cntStartedOpinions = opinionModel.cntStartedOpinions;
			this.cntFinishedOpinions = opinionModel.cntFinishedOpinions;
			this.guid = opinionModel.guid;
			this.lastStartDate = opinionModel.lastStartDate;
			this.avgTimeTakenSec = opinionModel.avgTimeTakenSec;
			this.accountId = opinionModel.accountId;
			this.countControls = opinionModel.countControls;
		}

		public Builder withOpinionId(Long opinionId) {
			this.opinionId = opinionId;
			return this;
		}

		public Builder withType(OpinionType type) {
			this.type = type;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withModifyDate(Date modifyDate) {
			this.modifyDate = modifyDate;
			return this;
		}

		public Builder withCntStartedOpinions(Long cntStartedOpinions) {
			this.cntStartedOpinions = cntStartedOpinions;
			return this;
		}

		public Builder withCntFinishedOpinions(Long cntFinishedOpinions) {
			this.cntFinishedOpinions = cntFinishedOpinions;
			return this;
		}

		public Builder withGuid(String guid) {
			this.guid = guid;
			return this;
		}

		public Builder withLastStartDate(Date lastStartDate) {
			this.lastStartDate = lastStartDate;
			return this;
		}

		public Builder withAvgTimeTakenSec(Double avgTimeTakenSec) {
			this.avgTimeTakenSec = avgTimeTakenSec;
			return this;
		}

		public Builder withAccountId(Long accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder withCountControls(Integer countControls) {
			this.countControls = countControls;
			return this;
		}

		public OpinionModel build() {
			return new OpinionModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("opinionId", id).add("type", type).add("name", name)
				.add("modifyDate", modifyDate).add("cntStartedOpinions", cntStartedOpinions)
				.add("cntFinishedOpinions", cntFinishedOpinions).add("guid", guid).add("lastStartDate", lastStartDate)
				.add("avgTimeTakenSec", avgTimeTakenSec).add("accountId", accountId).add("countControls", countControls)
				.toString();
	}

}
