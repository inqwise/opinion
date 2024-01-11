package com.inqwise.opinion.library.common.accounts;

import java.util.Date;

import org.json.JSONObject;

public class AccountRepositoryParser {
	public AccountRepositoryParser() {}
	public AccountModel parse(JSONObject json) {
		return AccountModel.builder()
				.withAccountId(json.optLongObject(IAccount.ResultSetNames.ACCOUNT_ID))
				.withServicePackageName(json.optString(IAccount.ResultSetNames.SERVICE_PACKAGE_NAME))
				.withAccountName(json.optString(IAccount.ResultSetNames.ACCOUNT_NAME))
				.withOwnerId(json.optLongObject(IAccount.ResultSetNames.OWNER_ID))
				.withInsertDate((Date) json.opt(IAccount.ResultSetNames.INSERT_DATE))
				.withIsActive(json.optBooleanObject(IAccount.ResultSetNames.IS_ACTIVE))
				.build();
	}
		
}
