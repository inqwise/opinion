package com.inqwise.opinion.common;

import org.json.JSONObject;

public class CountriesStatisticsRepositoryParser {
		public CountriesStatisticsRepositoryParser() {}
		public CountriesStatisticsModel parse(JSONObject json) {
			return CountriesStatisticsModel.builder()
					.withCountryId(json.optIntegerObject("country_id"))
					.withCountryName(json.optString("country_name"))
					.withIso2(json.optString("iso2"))
					.withTimeTakenSec(json.optLongObject("time_taken_sec"))
					.withCntStarted(json.optLongObject("started"))
					.withCntCompleted(json.optLongObject("completed"))
					.build();
		}
}