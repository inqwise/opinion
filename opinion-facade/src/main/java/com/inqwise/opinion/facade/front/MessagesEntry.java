package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.MessagesModel;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.MessagesManager;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;

public class MessagesEntry extends Entry implements IPostmasterObject {

	static ApplicationLog logger = ApplicationLog.getLogger(MessagesEntry.class);
	public MessagesEntry(IPostmasterContext context){
		super(context);
	}
	
	public JSONObject getList(JSONObject input) throws JSONException, IOException{
		JSONObject output = null;
		IOperationResult result = null;
		Long userId = null;
		
		int top = JSONHelper.optInt(input, "top", 100);
		Date from = JSONHelper.optDate(input, "from");
		Date to = JSONHelper.optDate(input, "to");
		boolean includeClosed =  JSONHelper.optBoolean(input, "includeClosed", false);
		boolean includeNotActive = false;
		
		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}
		
		if(null == result) {
			
			List<MessagesModel> list = MessagesManager.getMessages(userId, from, to, includeClosed, includeNotActive, top, false);
			
			Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			JSONArray ja = new JSONArray();
			for(var messagesModel : list){
				JSONObject item = new JSONObject();
				item.put("id", messagesModel.getMessageId());
				item.put("name", messagesModel.getMessageName());
				item.put("content", messagesModel.getMessageContent());
				item.put("publishDate", formatter.format(messagesModel.getActivateDate()));
								
				ja.put(item);
			}
			
			output = new JSONObject().put("list", ja);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject close(JSONObject input) throws JSONException, NullPointerException, IOException{
		JSONObject output;
		IOperationResult result = null;
		Long userId = null;
		long messageId = input.getLong("id");
		
		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}
		
		if(null == result){
			BaseOperationResult updateResult = MessagesManager.closeMessage(messageId, userId, userId);
			if(updateResult.hasError()){
				result = updateResult;
			}		
		}
		
		if(null == result) {
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject closeList(JSONObject input) throws JSONException, NullPointerException, IOException{
		JSONObject output;
		IOperationResult result = null;
		Long userId = null;
		JSONArray idList = JSONHelper.optJsonArray(input, "list");
		if(null == idList){
			result = new BaseOperationResult(ErrorCode.ArgumentNull, "'list' is mandatory");
		}
		
		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}
		
		if(null == result){
			
		}
		
		if(null == result){
			for(int i = 0 ; i < idList.length() ; i++){
				Long messageId = idList.optLong(i);
				if(messageId > 0){
					BaseOperationResult closeResult = MessagesManager.closeMessage(messageId, userId, userId);
					if(closeResult.hasError()){
						result = closeResult;
						break;
					}
				}
			}
		}
		
		if(null == result) {
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject deleteList(JSONObject input) throws JSONException, NullPointerException, IOException{
		JSONObject output;
		IOperationResult result = null;
		Long userId = null;
		JSONArray idList = JSONHelper.optJsonArray(input, "list");
		if(null == idList){
			result = new BaseOperationResult(ErrorCode.ArgumentNull, "'list' is mandatory");
		}
		
		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}
		
		if(null == result){
			
		}
		
		if(null == result){
			for(int i = 0 ; i < idList.length() ; i++){
				Long messageId = idList.optLong(i);
				if(messageId > 0){
					BaseOperationResult closeResult = MessagesManager.excludeMessage(messageId, userId);
					if(closeResult.hasError()){
						result = closeResult;
						break;
					}
				}
			}
		}
		
		if(null == result) {
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
}
