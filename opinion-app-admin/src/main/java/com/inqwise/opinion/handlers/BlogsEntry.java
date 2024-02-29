package com.inqwise.opinion.handlers;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.cms.common.ICategory;
import com.inqwise.opinion.cms.common.ITag;
import com.inqwise.opinion.cms.common.blog.Blogs;
import com.inqwise.opinion.cms.common.blog.IComment;
import com.inqwise.opinion.cms.common.blog.IPost;
import com.inqwise.opinion.cms.managers.BlogManager;
import com.inqwise.opinion.cms.managers.CategoriesManager;
import com.inqwise.opinion.cms.managers.TagsManager;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.IPostmasterContext;

public class BlogsEntry extends Entry {
	static ApplicationLog logger = ApplicationLog.getLogger(BlogsEntry.class);
	
	protected BlogsEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject searchPosts(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		String tagName = JSONHelper.optString(input, "tagName");
		String categoryName = JSONHelper.optString(input, "categoryName");
		Integer archiveTotalMonth = JSONHelper.optInt(input, "archiveMonth");
		String searchWord = JSONHelper.optString(input, "search");
		Integer top = JSONHelper.optInt(input, "top", 1000);

		List<IPost> posts = null;
		OperationResult<List<IPost>> postsResult = BlogManager.searchPosts(tagName, categoryName, archiveTotalMonth, searchWord, top, Blogs.OfficialInqwiseBlog);
		if(postsResult.hasError()){
			result = postsResult;
		} else {
			posts = postsResult.getValue();
		}
		
		if(null == result){
			JSONArray ja = new JSONArray();
			for (IPost post : posts) {
				JSONObject jo = new JSONObject();
				jo.put("id", post.getId());
				jo.put("countOfComments", post.getCountOfComments());
				jo.put("title", post.getTitle());
				jo.put("countOfTags", post.getTags().size());
				jo.put("countOfCategories", post.getCategories().size());
				jo.put("postDate", JSONHelper.getDateFormat(post.getPostDate(), "MMM dd, yyyy"));
				jo.put("modifyDate", JSONHelper.getDateFormat(post.getModifyDate(), "MMM dd, yyyy HH:mm:ss"));
				
				ja.put(jo);
			}
			
			output = new JSONObject().put("list", ja); 
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getPost(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		int id = input.getInt("postId");
		
		IPost post = null;
		OperationResult<IPost> postResult = BlogManager.getPost(id);
		if(postResult.hasError()){
			result = postResult;
		} else {
			post = postResult.getValue();
		}
		
		if(null == result){
			
			JSONObject jo = new JSONObject(post);
			/*
			jo.put("id", post.getId());
			jo.put("countOfComments", post.getCountOfComments());
			jo.put("title", post.getTitle());
			jo.put("urlTitle", post.getUrlTitle());
			jo.put("content", post.getContent());
			jo.put("tags", new JSONArray(post.getTags()));
			jo.put("categories", new JSONArray(post.getCategories()));
			*/
			
			output = jo; 
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getCategories(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		
		List<ICategory> categories = null;
		OperationResult<List<ICategory>> categoriesResult = CategoriesManager.getCategories(Blogs.OfficialInqwiseBlog);
		if(categoriesResult.hasError()){
			result = categoriesResult;
		} else {
			categories = categoriesResult.getValue();
		}
		
		if(null == result){
			output = new JSONObject().put("list", new JSONArray(categories));
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getTags(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		
		List<ITag> tags = null;
		OperationResult<List<ITag>> tagsResult = TagsManager.getBlogTags(Blogs.OfficialInqwiseBlog);
		if(tagsResult.hasError()){
			result = tagsResult;
		} else {
			tags = tagsResult.getValue();
		}
		
		if(null == result){
			
			output = new JSONObject().put("list", new JSONArray(tags));
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject createPost(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		
		int blogId = Blogs.OfficialInqwiseBlog.getId();
		String title = JSONHelper.optStringTrim(input, "title");
		String urlTitle = JSONHelper.optStringTrim(input, "urlTitle");
		String content = JSONHelper.optStringTrim(input, "content");
		Date postDate = JSONHelper.optDate(input, "postDate");
		Boolean isActive = JSONHelper.optBoolean(input, "isActive");
		Integer[] tags = JSONHelper.toArrayOfInt(JSONHelper.optJsonArray(input, "tags"));
		Integer[] categories = JSONHelper.toArrayOfInt(JSONHelper.optJsonArray(input, "categories"));
		Integer postId = null;
		
		OperationResult<Integer> postResult = BlogManager.createPost(blogId, title, urlTitle, content, postDate, isActive, tags, categories);
		if(postResult.hasError()){
			result = postResult;
		} else {
			postId = postResult.getValue();
		}
		
		if(null == result){
			output = new JSONObject().put("postId", postId);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject updatePost(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		
		int postId = input.getInt("postId");
		String title = JSONHelper.optStringTrim(input, "title");
		String urlTitle = JSONHelper.optStringTrim(input, "urlTitle");
		String content = JSONHelper.optStringTrim(input, "content");
		Date postDate = JSONHelper.optDate(input, "postDate");
		Boolean isActive = JSONHelper.optBoolean(input, "active");
		Integer[] tags = JSONHelper.toArrayOfInt(JSONHelper.optJsonArray(input, "tags"));
		Integer[] categories = JSONHelper.toArrayOfInt(JSONHelper.optJsonArray(input, "categories"));
		
		BaseOperationResult postResult = BlogManager.updatePost(postId, title, urlTitle, content, postDate, isActive, tags, categories);
		if(postResult.hasError()){
			result = postResult;
		} 
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject createCategory(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		String name = JSONHelper.optString(input, "categoryName");
		
		OperationResult<Integer> categoryResult = CategoriesManager.createCategory(name);
		if(categoryResult.hasError()){
			output = categoryResult.toJson();
		} else {
			output = new JSONObject().put("categoryId", categoryResult.getValue());
		}
		
		return output;
	}
	
	public JSONObject updateCategory(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		
		int id = input.getInt("categoryId");
		String name = JSONHelper.optString(input, "categoryName");
		
		BaseOperationResult categoryResult = CategoriesManager.updateCategory(id, name);
		if(categoryResult.hasError()){
			output = categoryResult.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject deleteCategories(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		List<Integer> list = JSONHelper.toListOfInt(JSONHelper.optJsonArray(input, "list"));
		
		for (Integer categoryId : list) {
			BaseOperationResult deleteResult = CategoriesManager.deleteCategory(categoryId);
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
	
	public JSONObject createTag(JSONObject input) throws NullPointerException, JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		String name = JSONHelper.optString(input, "tagName");
		
		OperationResult<Integer> tagResult = TagsManager.createTag(name);
		if(tagResult.hasError()){
			output = tagResult.toJson();
		} else {
			output = new JSONObject().put("tagId", tagResult.getValue());
		}
		
		return output;
	}
	
	public JSONObject updateTag(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		
		int id = input.getInt("tagId");
		String name = JSONHelper.optString(input, "tagName");
		
		BaseOperationResult categoryResult = TagsManager.updateTag(id, name);
		if(categoryResult.hasError()){
			output = categoryResult.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject deleteTags(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		List<Integer> list = JSONHelper.toListOfInt(JSONHelper.optJsonArray(input, "list"));
		
		for (Integer categoryId : list) {
			BaseOperationResult deleteResult = TagsManager.deleteTag(categoryId);
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
	
	public JSONObject getComments(JSONObject input) throws JSONException{
		JSONObject output;
		BaseOperationResult result = null;
		int postId = input.getInt("postId");
		
		List<IComment> comments = null;
		OperationResult<List<IComment>> commentsResult = BlogManager.getComments(postId);
		if(commentsResult.hasError()){
			result = commentsResult;
		} else {
			comments = commentsResult.getValue();
		}
		
		if(null == result){
			output = new JSONObject().put("list", new JSONArray(comments));
		} else {
			output = result.toJson();
		}
		
		return output;
	}
}
