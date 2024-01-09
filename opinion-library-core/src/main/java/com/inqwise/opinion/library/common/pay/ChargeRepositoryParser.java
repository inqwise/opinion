package com.inqwise.opinion.library.common.pay;

import java.util.Optional;

import org.json.JSONObject;

public class ChargeRepositoryParser {
	public ChargeModel parse(JSONObject json) {
		var builder = ChargeModel.builder()
				.withId(json.getLong("charge_id"))
				.withAmount(json.optDoubleObject("amount"));
		
		Optional.ofNullable(json.optIntegerObject("charge_status_id"))
		.map(ChargeStatus::fromInt)
		.ifPresent(builder::withStatus);
		
		return builder.build();
	}
}
