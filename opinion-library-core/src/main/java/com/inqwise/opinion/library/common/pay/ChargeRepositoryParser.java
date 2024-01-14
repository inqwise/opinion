package com.inqwise.opinion.library.common.pay;

import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;

import com.inqwise.opinion.library.common.pay.ChargeModel.Builder;

public class ChargeRepositoryParser {
	public ChargeModel parse(JSONObject json) {
		var builder = ChargeModel.builder()
				.withId(json.getLong("charge_id"))
				.withAmount(json.optDoubleObject("amount"))
				.withName(json.optString("charge_name"))
				.withDescription(json.optString("charge_description"))
				.withCreateDate(Date.from(((java.sql.Date)json.opt("insert_date")).toInstant()));
		
		Optional.ofNullable(json.optIntegerObject("charge_status_id"))
		.map(ChargeStatus::fromInt)
		.ifPresent(builder::withStatus);
		
		return builder.build();
	}
}
