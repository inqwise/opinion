package com.inqwise.opinion.common.collectors;

import java.util.Date;

import org.json.JSONObject;

public class CollectorRepositoryParser {
	public CollectorModel parse(JSONObject json) {
		return CollectorModel.builder()
				.withId(json.optLongObject(ICollector.ResultSetNames.COLLECTOR_ID))
				.withOpinionId(json.optLongObject(ICollector.ResultSetNames.OPINION_ID))
				.withAccountId(json.optLongObject(ICollector.ResultSetNames.ACCOUNT_ID))
				.withCollectorUuid(json.optString(ICollector.ResultSetNames.COLLECTOR_UUID))
				.withModifyDate((Date)json.opt(ICollector.ResultSetNames.MODIFY_DATE))
				.withCreateDate((Date) json.opt(ICollector.ResultSetNames.CREATE_DATE))
				.withExpirationDate((Date) json.opt(ICollector.ResultSetNames.EXPIRATION_DATE))
				.withCollectorName(json.optString(ICollector.ResultSetNames.COLLECTOR_NAME))
				.withOpinionName(json.optString(ICollector.ResultSetNames.OPINION_NAME))
				.withOpinionTypeName(json.optString(ICollector.ResultSetNames.OPINION_TYPE_NAME))
				.withCollectorStatusId(json.optIntegerObject(ICollector.ResultSetNames.COLLECTOR_STATUS_ID))
				.withCollectorSourceId(json.optIntegerObject(ICollector.ResultSetNames.COLLECTOR_SOURCE_ID))
				.withCollectorSourceName(json.optString(ICollector.ResultSetNames.COLLECTOR_SOURCE_NAME))
				.withCollectorSourceTypeId(json.optIntegerObject(ICollector.ResultSetNames.COLLECTOR_SOURCE_TYPE_ID))
				.withCntStartedOpinions(json.optLongObject(ICollector.ResultSetNames.CNT_STARTED_OPINIONS))
				.withCntFinishedOpinions(json.optLongObject(ICollector.ResultSetNames.CNT_FINISHED_OPINIONS))
				.withLastStartDate((Date) json.opt(ICollector.ResultSetNames.LAST_START_DATE))
				.withAvgTimeTakenSec(json.optDoubleObject(ICollector.ResultSetNames.AVG_TIME_TAKEN_SEC))
				.build();
	}
	
}
