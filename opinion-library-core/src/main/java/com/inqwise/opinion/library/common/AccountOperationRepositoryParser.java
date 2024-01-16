package com.inqwise.opinion.library.common;

import org.json.JSONObject;

public class AccountOperationRepositoryParser {
	public AccountOperationModel parse(JSONObject json) {
		return AccountOperationModel.builder()
				.build();
	}
}