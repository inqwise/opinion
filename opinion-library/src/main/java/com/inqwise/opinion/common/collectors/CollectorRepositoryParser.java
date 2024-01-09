package com.inqwise.opinion.common.collectors;

import org.json.JSONObject;

public class CollectorRepositoryParser {
	public CollectorModel parse(JSONObject json) {
		return CollectorModel.builder()
				.withId(json.optLongObject(ICollector.ResultSetNames.COLLECTOR_ID))
				.build();
	}
	
}
