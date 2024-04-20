package com.inqwise.opinion.handlers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;

public class PagesEntry extends Entry {
	static ApplicationLog logger = ApplicationLog.getLogger(PaymentsEntry.class);
	public PagesEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject getPages(JSONObject input) throws JSONException {
		JSONObject output = null;
		BaseOperationResult result = null;
		
		List<IPage> pages = null;
		throw new NotImplementedException("getPages");
//		OperationResult<List<IPage>> pagesResult = null;
//		if (pagesResult.hasError()) {
//			result = pagesResult;
//		} else {
//			pages = pagesResult.getValue();
//		}
//
//		if (null == result) {
//			
//			// output = new JSONObject().put("list", new JSONArray(articles));
//			JSONArray ja = new JSONArray();
//			for (IPage page : pages) {
//				ja.put(page.toJSON());
//			}
//			
//			output = new JSONObject().put("list", ja); 
//			
//		} else {
//			output = result.toJson();
//		}
//
//		return output;
	}
}
