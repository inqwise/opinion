package com.inqwise.opinion.common;

import java.util.Date;

import org.json.JSONObject;

public class AnswererSessionRepositoryParser {
	public AnswererSessionRepositoryParser() {}
	public AnswererSessionModel parse(JSONObject json) {
		return AnswererSessionModel.builder()
				.withIsUnplained(json.optBooleanObject(IAnswererSession.ResultSetNames.ANSWER_SESSION_ID))
				.withIndex(json.optString(IAnswererSession.ResultSetNames.INDEX))
				.withAnswerSessionId(json.optString(IAnswererSession.ResultSetNames.ANSWER_SESSION_ID))
				.withCountryName(json.optString(IAnswererSession.ResultSetNames.COUNTRY_NAME))
				.withClientIp(json.optString(IAnswererSession.ResultSetNames.CLIENT_IP))
				.withTargetUrl(json.optString(IAnswererSession.ResultSetNames.TARGET_URL))
				.withFinishDate((Date) json.opt(IAnswererSession.ResultSetNames.FINISH_DATE))
				.withInsertDate((Date) json.opt(IAnswererSession.ResultSetNames.INSERT_DATE))
				.withTimeTakenSec(json.optLongObject(IAnswererSession.ResultSetNames.TIME_TAKEN_SEC))
				.withId(json.optLongObject(IAnswererSession.ResultSetNames.ID))
				.withCollectorId(json.optLongObject(IAnswererSession.ResultSetNames.COLLECTOR_ID))
				.withCollectorName(json.optString(IAnswererSession.ResultSetNames.COLLECTOR_NAME))
				.withIsCollectorExist(json.optBooleanObject(IAnswererSession.ResultSetNames.IS_COLLECTOR_EXIST))
				.build();
	}
}