package com.inqwise.opinion.common;

import java.util.Date;

import org.json.JSONObject;

public class ResultRepositoryParser {
	public ResultModel parse(JSONObject json) {
		return ResultModel.builder()
				.withAnswerSessionId(json.optString("answer_session_is"))
				.withCollectorName(json.optString("collector_name"))
				.withCollectorId(json.optLongObject("collector_id"))
				.withParticipantInsertDate((Date) json.opt("participant_insert_date"))
				.withClientIp(json.optString("client_ip"))
				.withIsCompleted(json.optBooleanObject("is_completed"))
				.withControlKey(json.optString("control_key"))
				.withControlContent(json.optString("control_content"))
				.withControlId(json.optLongObject("control_id"))
				.withAnswerValue(json.optString("answer_value"))
				.build();
	}
}