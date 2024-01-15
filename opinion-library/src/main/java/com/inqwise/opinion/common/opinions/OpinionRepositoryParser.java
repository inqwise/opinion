package com.inqwise.opinion.common.opinions;

import java.util.Date;

import org.json.JSONObject;

public class OpinionRepositoryParser {
	public OpinionModel parse(JSONObject json) {
		return OpinionModel.builder()
				.withOpinionId(json.optLongObject(IOpinion.ResultSetNames.OPINION_ID))
				.withOpinionTypeId(json.optIntegerObject(IOpinion.ResultSetNames.OPINION_TYPE_ID))
				.withName(json.optString(IOpinion.ResultSetNames.NAME))
				.withModifyDate((Date) json.opt(IOpinion.ResultSetNames.MODIFY_DATE))
				.withCntStartedOpinions(json.optLongObject(IOpinion.ResultSetNames.CNT_STARTED_OPINIONS))
				.withCntFinishedOpinions(json.optLongObject(IOpinion.ResultSetNames.CNT_FINISHED_OPINIONS))
				.withGuid(json.optString(IOpinion.ResultSetNames.GUID))
				.withLastStartDate((Date) json.opt(IOpinion.ResultSetNames.LAST_START_DATE))
				.withAvgTimeTakenSec(json.optDoubleObject(IOpinion.ResultSetNames.AVG_TIME_TAKEN_SEC))
				.build();
	}
}
