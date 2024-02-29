package com.inqwise.opinion.handlers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.kb.IArticle;
import com.inqwise.opinion.cms.managers.ArticleManager;
import com.inqwise.opinion.cms.managers.PagesFactory;
import com.inqwise.opinion.cms.managers.TemplatePagesManager;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class PagesEntry extends Entry {
	static ApplicationLog logger = ApplicationLog.getLogger(PaymentsEntry.class);
	public PagesEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject getPages(JSONObject input) throws JSONException {
		JSONObject output = null;
		BaseOperationResult result = null;
		
		List<IPage> pages = null;
		OperationResult<List<IPage>> articlesResult = PagesFactory.getPagesManager();
		if (articlesResult.hasError()) {
			result = articlesResult;
		} else {
			articles = articlesResult.getValue();
		}

		if (null == result) {
			
			// output = new JSONObject().put("list", new JSONArray(articles));
			JSONArray ja = new JSONArray();
			for (IArticle article : articles) {
				JSONObject jo = new JSONObject();
				jo.put("id", article.getId());
				jo.put("title", article.getTitle());
				jo.put("topicId", article.getTopicId());
				jo.put("topicName", article.getTopicName());
				jo.put("createDate", JSONHelper.getDateFormat(article.getCreateDate(), "MMM dd, yyyy HH:mm:ss"));
				jo.put("modifyDate", JSONHelper.getDateFormat(article.getModifyDate(), "MMM dd, yyyy HH:mm:ss"));
				jo.put("popular", article.getPopular());
				jo.put("active", article.getActive());
				ja.put(jo);
			}
			
			output = new JSONObject().put("list", ja); 
			
		} else {
			output = result.toJson();
		}

		return output;
	}
}
