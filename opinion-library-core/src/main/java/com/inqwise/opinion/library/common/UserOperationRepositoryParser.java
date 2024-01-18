package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

public class UserOperationRepositoryParser {
	public UserOperationModel parse(JSONObject json) {
		var builder = UserOperationModel.builder()
				.withId(json.optLongObject("usop_id"))
				.withUserName(json.getString("user_name"))
				.withUserId(json.optLongObject("user_id"))
				.withClientIp(json.getString("client_ip"))
				.withProductId(json.optIntegerObject("product_id"))
				.withInsertDate((Date) json.opt("insert_date"))
				.withCountryName(json.getString("country_name"));
		
		builder.withType(UserOperationType.builder()
				.withId(json.optIntegerObject("usop_type_id"))
				.withName(json.optString("usop_type_value"))
				.build());
		
		return builder.build();
	}
}