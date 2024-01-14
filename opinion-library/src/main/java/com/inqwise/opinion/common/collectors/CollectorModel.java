package com.inqwise.opinion.common.collectors;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class CollectorModel {
	
	private Long id;
	private Long opinionId;
	private Long accountId;
	private String collectorUuid;
	private Date modifyDate;
	private Date createDate;
	private Date expirationDate;
	private String collectorName;
	private String opinionName;
	private String opinionTypeName;
	private Integer collectorStatusId;
	private Integer collectorSourceId;
	private String collectorSourceName;
	private Integer collectorSourceTypeId;
	private Long cntStartedOpinions;
	private Long cntFinishedOpinions;
	private Date lastStartDate;
	private Double avgTimeTakenSec;

	private CollectorModel(Builder builder) {
		this.id = builder.id;
		this.opinionId = builder.opinionId;
		this.accountId = builder.accountId;
		this.collectorUuid = builder.collectorUuid;
		this.modifyDate = builder.modifyDate;
		this.createDate = builder.createDate;
		this.expirationDate = builder.expirationDate;
		this.collectorName = builder.collectorName;
		this.opinionName = builder.opinionName;
		this.opinionTypeName = builder.opinionTypeName;
		this.collectorStatusId = builder.collectorStatusId;
		this.collectorSourceId = builder.collectorSourceId;
		this.collectorSourceName = builder.collectorSourceName;
		this.collectorSourceTypeId = builder.collectorSourceTypeId;
		this.cntStartedOpinions = builder.cntStartedOpinions;
		this.cntFinishedOpinions = builder.cntFinishedOpinions;
		this.lastStartDate = builder.lastStartDate;
		this.avgTimeTakenSec = builder.avgTimeTakenSec;
	}

	public static final class Keys{
		public static final String ID = "id";
		public static final String OPINION_ID = "opinion_id";
		public static final String ACCOUNT_ID = "account_id";
		public static final String COLLECTOR_UUID = "collector_uuid";
		public static final String MODIFY_DATE = "modify_date";
		public static final String CREATE_DATE = "create_date";
		public static final String EXPIRATION_DATE = "expiration_date";
		public static final String COLLECTOR_NAME = "collector_name";
		public static final String OPINION_NAME = "opinion_name";
		public static final String OPINION_TYPE_NAME = "opinion_type_name";
		public static final String COLLECTOR_STATUS_ID = "collector_status_id";
		public static final String COLLECTOR_SOURCE_ID = "collector_source_id";
		public static final String COLLECTOR_SOURCE_NAME = "collector_source_id";
		public static final String COLLECTOR_SOURCE_TYPE_ID = "collector_source_type_id";
		public static final String CNT_STARTED_OPINIONS = "cnt_started_opinions";
		public static final String CNT_FINISHED_OPINIONS = "cnt_finished_opinions";
		public static final String LAST_START_DATE = "last_start_date";
		public static final String AVG_TIME_TAKEN_SEC = "avg_time_taken_sec";
		
		private Keys(Builder builder) {
		}
		public static Builder builder() {
			return new Builder();
		}
		public static Builder builderFrom(Keys keys) {
			return new Builder(keys);
		}
		public static final class Builder {
			private Builder() {
			}

			private Builder(Keys keys) {
			}

			public Keys build() {
				return new Keys(this);
			}
		}
		
	}	
	
	public CollectorModel(JSONObject json) {
		id = json.optLongObject(Keys.ID);
		opinionId = json.optLongObject(Keys.OPINION_ID);
		accountId = json.optLongObject(Keys.ACCOUNT_ID);
		collectorUuid = json.optString(Keys.COLLECTOR_UUID);
		modifyDate = (Date) json.opt(Keys.MODIFY_DATE);
		createDate = (Date) json.opt(Keys.CREATE_DATE);
		expirationDate = (Date) json.opt(Keys.EXPIRATION_DATE);
		collectorName = json.optString(Keys.COLLECTOR_NAME);
		opinionName = json.optString(Keys.OPINION_NAME);
		opinionTypeName = json.getString(Keys.OPINION_TYPE_NAME);
		collectorStatusId = json.optIntegerObject(Keys.COLLECTOR_STATUS_ID);
		collectorSourceId = json.optIntegerObject(Keys.COLLECTOR_SOURCE_ID);
		collectorSourceName = json.optString(Keys.COLLECTOR_SOURCE_NAME);
		collectorSourceTypeId = json.optIntegerObject(Keys.COLLECTOR_SOURCE_TYPE_ID);
		cntStartedOpinions = json.optLongObject(Keys.CNT_STARTED_OPINIONS);
		cntFinishedOpinions = json.optLongObject(Keys.CNT_FINISHED_OPINIONS);
		lastStartDate = (Date) json.opt(Keys.LAST_START_DATE);
		avgTimeTakenSec = json.optDouble(Keys.AVG_TIME_TAKEN_SEC);
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getOpinionId() {
		return opinionId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public String getCollectorUuid() {
		return collectorUuid;
	}

	public Date getModifyDate() {
		 return modifyDate;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public String getOpinionName() {
		return opinionName;
	}

	public String getOpinionTypeName() {
		return opinionTypeName;
	}
	
	public Integer getCollectorStatusId() {
		return collectorStatusId;
	}
	
	public Integer getCollectorSourceId() {
		return collectorSourceId;
	}

	public String getCollectorSourceName() {
		return collectorSourceName;
	}

	public Integer getCollectorSourceTypeId() {
		return collectorSourceTypeId;
	}
	
	public Long getCntStartedOpinions() {
		return cntStartedOpinions;
	}

	public Long getCntFinishedOpinions() {
		return cntFinishedOpinions;
	}
	
	public Date getLastStartDate() {
		return lastStartDate;
	}

	public Double getAvgTimeTakenSec() {
		return avgTimeTakenSec;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.ID, id);
		json.put(Keys.OPINION_ID, opinionId);
		json.put(Keys.ACCOUNT_ID, accountId);
		json.put(Keys.COLLECTOR_UUID, collectorUuid);
		json.put(Keys.MODIFY_DATE, modifyDate.getTime());
		json.put(Keys.CREATE_DATE, createDate.getTime());
		json.put(Keys.EXPIRATION_DATE, expirationDate.getTime());
		json.put(Keys.COLLECTOR_NAME, collectorName);
		json.put(Keys.OPINION_NAME, opinionName);
		json.put(Keys.OPINION_TYPE_NAME, opinionTypeName);
		json.put(Keys.COLLECTOR_STATUS_ID, collectorStatusId);
		json.put(Keys.COLLECTOR_SOURCE_ID, collectorSourceId);
		json.put(Keys.COLLECTOR_SOURCE_NAME, collectorSourceName);
		json.put(Keys.COLLECTOR_SOURCE_TYPE_ID, collectorSourceTypeId);
		json.put(Keys.CNT_STARTED_OPINIONS, cntStartedOpinions);
		json.put(Keys.CNT_FINISHED_OPINIONS, cntFinishedOpinions);
		json.put(Keys.LAST_START_DATE, lastStartDate.getTime());
		json.put(Keys.AVG_TIME_TAKEN_SEC, avgTimeTakenSec);
		return json;
		

	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(CollectorModel collectorModel) {
		return new Builder(collectorModel);
	}

	public static final class Builder {
		private Long id;
		private Long opinionId;
		private Long accountId;
		private String collectorUuid;
		private Date modifyDate;
		private Date createDate;
		private Date expirationDate;
		private String collectorName;
		private String opinionName;
		private String opinionTypeName;
		private Integer collectorStatusId;
		private Integer collectorSourceId;
		private String collectorSourceName;
		private Integer collectorSourceTypeId;
		private Long cntStartedOpinions;
		private Long cntFinishedOpinions;
		private Date lastStartDate;
		private Double avgTimeTakenSec;

		private Builder() {
		}

		private Builder(CollectorModel collectorModel) {
			this.id = collectorModel.id;
			this.opinionId = collectorModel.opinionId;
			this.accountId = collectorModel.accountId;
			this.collectorUuid = collectorModel.collectorUuid;
			this.modifyDate = collectorModel.modifyDate;
			this.createDate = collectorModel.createDate;
			this.expirationDate = collectorModel.expirationDate;
			this.collectorName = collectorModel.collectorName;
			this.opinionName = collectorModel.opinionName;
			this.opinionTypeName = collectorModel.opinionTypeName;
			this.collectorStatusId = collectorModel.collectorStatusId;
			this.collectorSourceId = collectorModel.collectorSourceId;
			this.collectorSourceName = collectorModel.collectorSourceName;
			this.collectorSourceTypeId = collectorModel.collectorSourceTypeId;
			this.cntStartedOpinions = collectorModel.cntStartedOpinions;
			this.cntFinishedOpinions = collectorModel.cntFinishedOpinions;
			this.lastStartDate = collectorModel.lastStartDate;
			this.avgTimeTakenSec = collectorModel.avgTimeTakenSec;
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withOpinionId(Long opinionId) {
			this.opinionId = opinionId;
			return this;
		}

		public Builder withAccountId(Long accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder withCollectorUuid(String collectorUuid) {
			this.collectorUuid = collectorUuid;
			return this;
		}

		public Builder withModifyDate(Date modifyDate) {
			this.modifyDate = modifyDate;
			return this;
		}

		public Builder withCreateDate(Date createDate) {
			this.createDate = createDate;
			return this;
		}

		public Builder withExpirationDate(Date expirationDate) {
			this.expirationDate = expirationDate;
			return this;
		}

		public Builder withCollectorName(String collectorName) {
			this.collectorName = collectorName;
			return this;
		}

		public Builder withOpinionName(String opinionName) {
			this.opinionName = opinionName;
			return this;
		}

		public Builder withOpinionTypeName(String opinionTypeName) {
			this.opinionTypeName = opinionTypeName;
			return this;
		}

		public Builder withCollectorStatusId(Integer collectorStatusId) {
			this.collectorStatusId = collectorStatusId;
			return this;
		}

		public Builder withCollectorSourceId(Integer collectorSourceId) {
			this.collectorSourceId = collectorSourceId;
			return this;
		}

		public Builder withCollectorSourceName(String collectorSourceName) {
			this.collectorSourceName = collectorSourceName;
			return this;
		}

		public Builder withCollectorSourceTypeId(Integer collectorSourceTypeId) {
			this.collectorSourceTypeId = collectorSourceTypeId;
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

		public Builder withLastStartDate(Date lastStartDate) {
			this.lastStartDate = lastStartDate;
			return this;
		}

		public Builder withAvgTimeTakenSec(Double avgTimeTakenSec) {
			this.avgTimeTakenSec = avgTimeTakenSec;
			return this;
		}

		public CollectorModel build() {
			return new CollectorModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("opinionId", opinionId).add("accountId", accountId)
				.add("collectorUuid", collectorUuid).add("modifyDate", modifyDate).add("createDate", createDate)
				.add("expirationDate", expirationDate).add("collectorName", collectorName)
				.add("opinionName", opinionName).add("opinionTypeName", opinionTypeName)
				.add("collectorStatusId", collectorStatusId).add("collectorSourceId", collectorSourceId)
				.add("collectorSourceName", collectorSourceName).add("collectorSourceTypeId", collectorSourceTypeId)
				.add("cntStartedOpinions", cntStartedOpinions).add("cntFinishedOpinions", cntFinishedOpinions)
				.add("lastStartDate", lastStartDate).add("avgTimeTakenSec", avgTimeTakenSec).toString();
	}
	
}
