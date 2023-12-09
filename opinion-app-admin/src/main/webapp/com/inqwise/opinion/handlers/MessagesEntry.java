package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataGridException;
import net.casper.data.model.CDataRowSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.MessagesManager;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.managers.OpinionsManager;

public class MessagesEntry extends Entry {
static ApplicationLog logger = ApplicationLog.getLogger(MessagesEntry.class);
	
	protected MessagesEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject getList(JSONObject input) throws JSONException, CDataGridException{
		JSONObject output;
		Long userId = JSONHelper.optLong(input, "userId");
		int top = JSONHelper.optInt(input, "top", 100);
		Date from = JSONHelper.optDate(input, "from");
		Date to = JSONHelper.optDate(input, "to");
		boolean includeClosed = JSONHelper.optBoolean(input, "includeClosed", true);
		boolean includeNotActive = JSONHelper.optBoolean(input, "includeUnpublished", true);
		
		CDataCacheContainer messagesDataSet = MessagesManager.getMessages(userId, from, to, includeClosed, includeNotActive, top, true);
		CDataRowSet rowSet = messagesDataSet.getAll();
		Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
		JSONArray ja = new JSONArray();
		while(rowSet.next()){
			JSONObject item = new JSONObject();
			item.put("id", rowSet.getLong("message_id"));
			item.put("name", rowSet.getString("message_name"));
			item.put("content", rowSet.getString("message_content"));
			item.put("userId", rowSet.getLong("user_id"));
			item.put("userName", rowSet.getString("user_name"));
			item.put("modifyDate", formatter.format(rowSet.getDate("modify_date")));
			if(null != rowSet.getObject("close_date")){
				item.put("closeDate", formatter.format(rowSet.getDate("close_date")));
			}
			
			if(null != rowSet.getObject("activate_date")){
				item.put("publishDate", formatter.format(rowSet.getDate("activate_date")));
			}
			
			if(null != rowSet.getObject("exclude_date")){
				item.put("excludeDate", formatter.format(rowSet.getDate("exclude_date")));
			}
			
			ja.put(item);
		}
		
		output = new JSONObject().put("list", ja);
		
		return output;
	}
	
	public JSONObject create(JSONObject input) throws JSONException, NullPointerException, IOException{
		JSONObject output;
		String name = JSONHelper.optStringTrim(input, "name");
		String content = JSONHelper.optStringTrim(input, "content");
		long userId = input.getLong("userId");
		OperationResult<Long> insertResult = MessagesManager.insertMessage(name, content, userId, getContext().getUserId().getValue());
		if(insertResult.hasError()){
			output = insertResult.toJson();
		} else {
			output = new JSONObject().put("id", insertResult.getValue());
		}
		return output;
	}
	
	public JSONObject update(JSONObject input) throws JSONException, NullPointerException, IOException{
		JSONObject output;
		long messageId = input.getLong("id");
		String name = JSONHelper.optStringTrim(input, "name");
		String content = JSONHelper.optStringTrim(input, "content");
		
		BaseOperationResult updateResult = MessagesManager.updateMessage(messageId, name, content, getContext().getUserId().getValue());
		if(updateResult.hasError()){
			output = updateResult.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		return output;
	}
	
	public JSONObject deleteList(JSONObject input) throws JSONException, NullPointerException, IOException{
		
		BaseOperationResult result = null;
		JSONArray messageIdsJa = null;
		
		if(null == result){
			messageIdsJa = input.optJSONArray("list");
			if(null == messageIdsJa){
				result = new BaseOperationResult(ErrorCode.ArgumentNull, "'list' is mandatory");
			}
		}
		
		if(null == result){
			for(int i = 0 ; i < messageIdsJa.length() ; i++){
				Long messageId = messageIdsJa.optLong(i);
				if(messageId > 0){
					result = MessagesManager.deleteMessage(messageId);
					if(result.hasError()){
						break;
					}
				}
			}
		}
		
		if(null == result || !result.hasError()){
			return BaseOperationResult.JsonOk;
		} else {
			return result.toJson();
		}
	} 
	
	public JSONObject publish(JSONObject input) throws NullPointerException, IOException, JSONException{
		JSONObject output;
		long messageId = input.getLong("id");
		Date publishDate = JSONHelper.optDate(input, "publishDate", new Date());
		
		BaseOperationResult updateResult = MessagesManager.activateMessage(messageId, publishDate, getContext().getUserId().getValue());
		if(updateResult.hasError()){
			output = updateResult.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		return output;
	}
	
	public JSONObject close(JSONObject input) throws JSONException, NullPointerException, IOException{
		JSONObject output;
		long messageId = input.getLong("id");
		
		BaseOperationResult updateResult = MessagesManager.closeMessage(messageId, getContext().getUserId().getValue(), null);
		if(updateResult.hasError()){
			output = updateResult.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		return output;
	}
}
