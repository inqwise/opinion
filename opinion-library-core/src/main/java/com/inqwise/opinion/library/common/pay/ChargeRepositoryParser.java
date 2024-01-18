package com.inqwise.opinion.library.common.pay;

import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;

public class ChargeRepositoryParser {
	public ChargeModel parse(JSONObject json) {
		var builder = ChargeModel.builder()
				.withId(json.getLong("charge_id"))
				.withAmount(json.optDoubleObject("amount"))
				.withName(json.optString("charge_name"))
				.withDescription(json.optString("charge_description"))
				.withPostPayAction(json.optString("post_pay_action"))
				.withReferenceId(json.optLongObject("reference_id"))
				.withPostPayActionData(json.optString("post_pay_action_data"))
				.withCreateDate(Date.from(((java.sql.Date)json.opt("insert_date")).toInstant()));
		
		Optional.ofNullable(json.optIntegerObject("charge_status_id"))
		.map(ChargeStatus::fromInt)
		.ifPresent(builder::withStatus);
		
		Optional.ofNullable(json.optIntegerObject("reference_type_id"))
		.map(ChargeReferenceType::fromInt)
		.ifPresent(builder::withReference);
		
		return builder.build();
	}
}
