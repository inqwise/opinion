package com.inqwise.opinion.handlers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.cms.common.kb.IArticle;
import com.inqwise.opinion.cms.common.kb.ITopic;
import com.inqwise.opinion.cms.managers.ArticleManager;
import com.inqwise.opinion.cms.managers.TopicManager;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class KnowledgeBaseEntry extends Entry {

	static ApplicationLog logger = ApplicationLog
			.getLogger(KnowledgeBaseEntry.class);

	protected KnowledgeBaseEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getArticles(JSONObject input) throws JSONException {
		JSONObject output = null;
		BaseOperationResult result = null;
		
		Integer topicId = JSONHelper.optInt(input, "topicId");

		List<IArticle> articles = null;
		OperationResult<List<IArticle>> articlesResult = ArticleManager.getArticles(topicId);
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

	public JSONObject getArticle(JSONObject input) throws JSONException {
		JSONObject output;
		BaseOperationResult result = null;
		
		Integer id = input.getInt("id");

		IArticle article = null;
		OperationResult<IArticle> articleResult = ArticleManager.getArticle(id, null);
		if(articleResult.hasError()){
			result = articleResult;
		} else {
			article = articleResult.getValue();
		}
		
		if(null == result){
			
			//JSONObject jo = new JSONObject(article);
			
			JSONObject jo = new JSONObject();
			jo.put("id", article.getId());
			jo.put("title", article.getTitle());
			jo.put("uri", article.getUri());
			jo.put("content", article.getContent());
			jo.put("topicId", article.getTopicId());
			jo.put("topicName", article.getTopicName());
			jo.put("createDate", JSONHelper.getDateFormat(article.getCreateDate(), "MMM dd, yyyy HH:mm:ss"));
			jo.put("modifyDate", JSONHelper.getDateFormat(article.getModifyDate(), "MMM dd, yyyy HH:mm:ss"));
			jo.put("popular", article.getPopular());
			jo.put("active", article.getActive());
			
			output = jo; 
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject setArticle(JSONObject input) throws JSONException {
		JSONObject output = null;
		BaseOperationResult result = null;

		String title = JSONHelper.optString(input, "title");
		String uri = JSONHelper.optString(input, "uri");
		String content = JSONHelper.optString(input, "content");
		/*Integer[] topics = JSONHelper.toArrayOfInt(JSONHelper.optJsonArray(input, "topics"));*/
		Integer topicId = JSONHelper.optInt(input, "topicId");
		Boolean popular = JSONHelper.optBoolean(input, "popular");
		Boolean active = JSONHelper.optBoolean(input, "active");
		
		OperationResult<Integer> articleResult = ArticleManager.setArticle(title, uri, content, topicId, popular, active);
		if (articleResult.hasError()) {
			output = articleResult.toJson();
		} else {
			output = new JSONObject().put("id", articleResult.getValue());
		}

		return output;
	}

	public JSONObject updateArticle(JSONObject input) throws JSONException {
		JSONObject output = null;
		BaseOperationResult result = null;
		
		Integer id = JSONHelper.optInt(input, "id");
		String title = JSONHelper.optString(input, "title");
		String uri = JSONHelper.optString(input, "uri");
		String content = JSONHelper.optString(input, "content");
		/*Integer[] topics = JSONHelper.toArrayOfInt(JSONHelper.optJsonArray(input, "topics"));*/
		Integer topicId = JSONHelper.optInt(input, "topicId");
		Boolean popular = JSONHelper.optBoolean(input, "popular");
		Boolean active = JSONHelper.optBoolean(input, "active");

		OperationResult<Integer> articleResult = ArticleManager.updateArticle(id, title, uri, content, topicId, popular, active);
		if(articleResult.hasError()) {
			output = articleResult.toJson();
		} else {
			output = new JSONObject().put("id", articleResult.getValue());
		}
		
		return output;
	}

	public JSONObject deleteArticles(JSONObject input) throws JSONException {
		JSONObject output;
		BaseOperationResult result = null;
		List<Integer> list = JSONHelper.toListOfInt(JSONHelper.optJsonArray(input, "list"));
		
		for (Integer id : list) {
			BaseOperationResult deleteResult = ArticleManager.deleteArticle(id);
			if(deleteResult.hasError()){
				result = deleteResult;
				break;
			}
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject getTopics(JSONObject input) throws JSONException {
		JSONObject output;
		BaseOperationResult result = null;
		
		Integer id = JSONHelper.optInt(input, "id");
		String uri = JSONHelper.optString(input, "uri");

		List<ITopic> topics = null;
		OperationResult<List<ITopic>> topicsResult = TopicManager.getTopics(id, uri);
		if (topicsResult.hasError()) {
			result = topicsResult;
		} else {
			topics = topicsResult.getValue();
		}

		if (null == result) {
			//output = new JSONObject().put("list", new JSONArray(topics));
			
			JSONArray ja = new JSONArray();
			for (ITopic topic : topics) {
				JSONObject jo = new JSONObject();
				jo.put("id", topic.getId());
				jo.put("name", topic.getName());
				jo.put("createDate", JSONHelper.getDateFormat(topic.getCreateDate(), "MMM dd, yyyy HH:mm:ss"));
				jo.put("modifyDate", JSONHelper.getDateFormat(topic.getModifyDate(), "MMM dd, yyyy HH:mm:ss"));
				//jo.put("active", topic.getActive());
				ja.put(jo);
			}
			
			output = new JSONObject().put("list", ja);
						
		} else {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject getTopic(JSONObject input) throws JSONException {
		JSONObject output;
		BaseOperationResult result = null;
		
		Integer id = JSONHelper.optInt(input, "id");
		String uri = JSONHelper.optString(input, "uri");

		ITopic topic = null;
		OperationResult<ITopic> topicResult = TopicManager.getTopic(id, uri);
		if(topicResult.hasError()){
			result = topicResult;
		} else {
			topic = topicResult.getValue();
		}
		
		if(null == result){
			
			//JSONObject jo = new JSONObject(article);
			
			JSONObject jo = new JSONObject();
			jo.put("id", topic.getId());
			jo.put("name", topic.getName());
			jo.put("uri", topic.getUri());
			jo.put("createDate", JSONHelper.getDateFormat(topic.getCreateDate(), "MMM dd, yyyy HH:mm:ss"));
			jo.put("modifyDate", JSONHelper.getDateFormat(topic.getModifyDate(), "MMM dd, yyyy HH:mm:ss"));
			//jo.put("active", article.getActive());
			
			output = jo; 
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject setTopic(JSONObject input) throws JSONException {
		JSONObject output = null;
		BaseOperationResult result = null;

		String name = JSONHelper.optString(input, "name");
		String uri = JSONHelper.optString(input, "uri");
		
		OperationResult<Integer> topicResult = TopicManager.setTopic(name, uri);
		if (topicResult.hasError()) {
			output = topicResult.toJson();
		} else {
			output = new JSONObject().put("id", topicResult.getValue());
		}

		return output;
	}

	public JSONObject updateTopic(JSONObject input) throws JSONException {
		JSONObject output;
		BaseOperationResult result = null;

		Integer id = JSONHelper.optInt(input, "id");
		String name = JSONHelper.optString(input, "name");
		String uri = JSONHelper.optString(input, "uri");

		BaseOperationResult topicResult = TopicManager.updateTopic(id, name, uri);
		if (topicResult.hasError()) {
			output = topicResult.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}

		return output;
	}

	public JSONObject deleteTopics(JSONObject input) throws JSONException {
		JSONObject output = null;

		return output;
	}

}
