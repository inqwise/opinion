package com.inqwise.opinion.opinion.facade.front;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.casper.data.model.CDataGridException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;

enum Status {
	added, updated, deleted
};

public class SurveysEntry extends OpinionsEntry {
	protected SurveysEntry(IPostmasterContext context) {
		super(context);
	}
	
	static ApplicationLog logger = ApplicationLog.getLogger(SurveysEntry.class);
	
	@Override
	public JSONObject getList(JSONObject input) throws IOException, JSONException, CDataGridException, NullPointerException, ExecutionException {
		return getMany(input, OpinionType.Survey.getValue());
	}
	
	@Override
	public JSONObject create(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException {
		return create(input, OpinionType.Survey.getValue());
	}
}
