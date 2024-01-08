package com.inqwise.opinion.actions.opinions;

import org.json.JSONObject;

interface IImportOpinionRequest {
	public long getAccountId();
	public String getActionGuid();
	public long getUserId();
	public JSONObject getArgs();
}
