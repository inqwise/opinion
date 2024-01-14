package com.inqwise.opinion.common;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class CountriesStatisticsModel {

	private Integer countryId;
	private String countryName;
	private String iso2;
	private Long timeTakenSec;
	private Long cntStarted;
	private Long cntCompleted;

	private CountriesStatisticsModel(Builder builder) {
		this.countryId = builder.countryId;
		this.countryName = builder.countryName;
		this.iso2 = builder.iso2;
		this.timeTakenSec = builder.timeTakenSec;
		this.cntStarted = builder.cntStarted;
		this.cntCompleted = builder.cntCompleted;
	}

	public static final class Keys{
		public static final String COUNTRY_ID = "country_id";
		public static final String COUNTRY_NAME = "country_name";
		public static final String ISO2 = "iso2";
		public static final String TIME_TAKEN_SEC = "time_taken_sec";
		public static final String CNT_STARTED = "cnt_started";
		public static final String CNT_COMPLETED = "cnt_completed";	
	}

	public CountriesStatisticsModel(JSONObject json) {
			countryId = json.optIntegerObject(Keys.COUNTRY_ID);
			countryName = json.optString(Keys.COUNTRY_NAME);
			iso2 = json.optString(Keys.ISO2);
			timeTakenSec = json.optLongObject(Keys.TIME_TAKEN_SEC);
			cntStarted = json.optLongObject(Keys.CNT_STARTED);
			cntCompleted = json.optLongObject(Keys.CNT_COMPLETED);

		}

		public Integer getCountryId() {
			return countryId;
		}

		public String getCountryName() {
			return countryName;
		}

		public String getIso2() {
			return iso2;
		}

		public Long getTimeTakenSec() {
			return timeTakenSec;
		}

		public Long getCntStarted() {
			return cntStarted;
		}

		public Long getCntCompleted() {
			return cntCompleted;
		}

		public JSONObject toJson() {
			var json = new JSONObject();
			json.put(Keys.COUNTRY_ID, countryId);
			json.put(Keys.COUNTRY_NAME, countryName);
			json.put(Keys.ISO2, iso2);
			json.put(Keys.TIME_TAKEN_SEC, timeTakenSec);
			json.put(Keys.CNT_STARTED, cntStarted);
			json.put(Keys.CNT_COMPLETED, cntCompleted);
			return json;
		}
		
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(CountriesStatisticsModel countriesStatisticsModel) {
		return new Builder(countriesStatisticsModel);
	}

	public static final class Builder {
		private Integer countryId;
		private String countryName;
		private String iso2;
		private Long timeTakenSec;
		private Long cntStarted;
		private Long cntCompleted;

		private Builder() {
		}

		private Builder(CountriesStatisticsModel countriesStatisticsModel) {
			this.countryId = countriesStatisticsModel.countryId;
			this.countryName = countriesStatisticsModel.countryName;
			this.iso2 = countriesStatisticsModel.iso2;
			this.timeTakenSec = countriesStatisticsModel.timeTakenSec;
			this.cntStarted = countriesStatisticsModel.cntStarted;
			this.cntCompleted = countriesStatisticsModel.cntCompleted;
		}

		public Builder withCountryId(Integer countryId) {
			this.countryId = countryId;
			return this;
		}

		public Builder withCountryName(String countryName) {
			this.countryName = countryName;
			return this;
		}

		public Builder withIso2(String iso2) {
			this.iso2 = iso2;
			return this;
		}

		public Builder withTimeTakenSec(Long timeTakenSec) {
			this.timeTakenSec = timeTakenSec;
			return this;
		}

		public Builder withCntStarted(Long cntStarted) {
			this.cntStarted = cntStarted;
			return this;
		}

		public Builder withCntCompleted(Long cntCompleted) {
			this.cntCompleted = cntCompleted;
			return this;
		}

		public CountriesStatisticsModel build() {
			return new CountriesStatisticsModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("countryId", countryId).add("countryName", countryName)
				.add("iso2", iso2).add("timeTakenSec", timeTakenSec).add("cntStarted", cntStarted)
				.add("cntCompleted", cntCompleted).toString();
	}

}
