package com.inqwise.opinion.opinion.facade.front;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.casper.data.model.CDataGridException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;

public class PollsEntry extends OpinionsEntry {

	public PollsEntry(IPostmasterContext context) {
		super(context);
	}

	@Override
	public JSONObject getList(JSONObject input) throws IOException, JSONException, CDataGridException, NullPointerException, ExecutionException {
		return getMany(input, OpinionType.Poll.getValue());
	}
	
	@Override
	public JSONObject create(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException {
		return create(input, OpinionType.Poll.getValue());
	}
}
