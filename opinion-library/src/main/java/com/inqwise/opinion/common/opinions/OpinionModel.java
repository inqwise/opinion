package com.inqwise.opinion.common.opinions;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class OpinionModel {
	
	private Long opinionId;
	private Integer opinionTypeId;
	private String name;
	private Date modifyDate;
	private Long cntStartedOpinions;
	private Long cntFinishedOpinions;
	private String guid;
	private Date lastStartDate;
	private Double avgTimeTakenSec;

	private OpinionModel(Builder builder) {
		this.opinionId = builder.opinionId;
		this.opinionTypeId = builder.opinionTypeId;
		this.name = builder.name;
		this.modifyDate = builder.modifyDate;
		this.cntStartedOpinions = builder.cntStartedOpinions;
		this.cntFinishedOpinions = builder.cntFinishedOpinions;
		this.guid = builder.guid;
		this.lastStartDate = builder.lastStartDate;
		this.avgTimeTakenSec = builder.avgTimeTakenSec;
	}

	public static final class Keys{

		public static final String OPINION_ID = "opinion_id";
		public static final String OPINION_TYPE_ID = "opinion_type_id";
		public static final String NAME = "name";
		public static final String MODIFY_DATE = "modify_date";
		public static final String CNT_STARTED_OPINIONS = "cnt_started_opinions";
		public static final String CNT_FINISHED_OPINIONS = "cnt_finished_opinions";
		public static final String GUID = "guid";
		public static final String LAST_START_DATE = "last_start_date";
		public static final String AVG_TIME_TAKEN_SEC = "avg_time_taken_sec";
		
	}

	public OpinionModel(JSONObject json) {
		opinionId = json.optLongObject(Keys.OPINION_ID);
		opinionTypeId = json.optIntegerObject(Keys.OPINION_TYPE_ID);
		name = json.optString(Keys.NAME);
		modifyDate = (Date) json.opt(Keys.MODIFY_DATE);
		cntStartedOpinions = json.optLongObject(Keys.CNT_STARTED_OPINIONS);
		cntFinishedOpinions = json.optLongObject(Keys.CNT_FINISHED_OPINIONS);
		guid = json.optString(Keys.GUID);
		lastStartDate = (Date) json.opt(Keys.LAST_START_DATE);
		avgTimeTakenSec = json.optDoubleObject(Keys.AVG_TIME_TAKEN_SEC);
	}
	
	public Long getOpinionId() {
		return opinionId;
	}

	public Integer getOpinionTypeId() {
		return opinionTypeId;
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

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.OPINION_ID, opinionId);
		json.put(Keys.OPINION_TYPE_ID, opinionTypeId);
		json.put(Keys.NAME, name);
		json.put(Keys.MODIFY_DATE, modifyDate);
		json.put(Keys.CNT_STARTED_OPINIONS, cntStartedOpinions);
		json.put(Keys.CNT_FINISHED_OPINIONS, cntFinishedOpinions);
		json.put(Keys.GUID, guid);
		json.put(Keys.LAST_START_DATE, lastStartDate);
		json.put(Keys.AVG_TIME_TAKEN_SEC, avgTimeTakenSec);
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
		private Integer opinionTypeId;
		private String name;
		private Date modifyDate;
		private Long cntStartedOpinions;
		private Long cntFinishedOpinions;
		private String guid;
		private Date lastStartDate;
		private Double avgTimeTakenSec;

		private Builder() {
		}

		private Builder(OpinionModel opinionModel) {
			this.opinionId = opinionModel.opinionId;
			this.opinionTypeId = opinionModel.opinionTypeId;
			this.name = opinionModel.name;
			this.modifyDate = opinionModel.modifyDate;
			this.cntStartedOpinions = opinionModel.cntStartedOpinions;
			this.cntFinishedOpinions = opinionModel.cntFinishedOpinions;
			this.guid = opinionModel.guid;
			this.lastStartDate = opinionModel.lastStartDate;
			this.avgTimeTakenSec = opinionModel.avgTimeTakenSec;
		}

		public Builder withOpinionId(Long opinionId) {
			this.opinionId = opinionId;
			return this;
		}

		public Builder withOpinionTypeId(Integer opinionTypeId) {
			this.opinionTypeId = opinionTypeId;
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

		public OpinionModel build() {
			return new OpinionModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("opinionId", opinionId).add("opinionTypeId", opinionTypeId)
				.add("name", name).add("modifyDate", modifyDate).add("cntStartedOpinions", cntStartedOpinions)
				.add("cntFinishedOpinions", cntFinishedOpinions).add("guid", guid).add("lastStartDate", lastStartDate)
				.add("avgTimeTakenSec", avgTimeTakenSec).toString();
	}

}
