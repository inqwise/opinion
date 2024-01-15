package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.MessagesModel;
import com.inqwise.opinion.library.common.MessagesRepositoryParser;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.MessagesDataAccess;

public final class MessagesManager {
	
	static ApplicationLog logger = ApplicationLog.getLogger(MessagesManager.class);
	
	public static OperationResult<Long> insertMessage(String messageName, String messageContent, long userId, long modifyUserId){
		
		final OperationResult<Long> result = new OperationResult<Long>();
		
		try {
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					if(reader.next()){
						if(0 == generationId){
							result.setValue(reader.getLong("new_message_id"));
						}
					} else {
						result.setError(ErrorCode.NoResults);
					}
				}
			};
			
			MessagesDataAccess.insertMessage(messageName, messageContent, userId, modifyUserId, callback);
		} catch (Exception ex) {
			UUID errorId = logger.error(ex, "insertMessage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult activateMessage(long messageId, Date activateDate, long modifyUserId){
		final BaseOperationResult result = new BaseOperationResult();
		
		try {
			MessagesDataAccess.activateMessage(messageId, activateDate, modifyUserId);
		} catch (Exception ex) {
			UUID errorId = logger.error(ex, "activateMessage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult closeMessage(long messageId, long closeUserId, Long userId){
		final BaseOperationResult result = new BaseOperationResult();
		
		try {
			MessagesDataAccess.closeMessage(messageId, closeUserId, userId);
		} catch (Exception ex) {
			UUID errorId = logger.error(ex, "closeMessage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult deleteMessage(long messageId){
		final BaseOperationResult result = new BaseOperationResult();
		
		try {
			MessagesDataAccess.deleteMessage(messageId);
		} catch (Exception ex) {
			UUID errorId = logger.error(ex, "deleteMessage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult updateMessage(long messageId, String messageName, String messageContent, long modifyUserId){
		final BaseOperationResult result = new BaseOperationResult();
		
		try {
			MessagesDataAccess.updateMessage(messageId, messageName, messageContent, modifyUserId);
		} catch (Exception ex) {
			UUID errorId = logger.error(ex, "updateMessage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static List<MessagesModel> getMessages(Long userId, Date fromModifyDate, Date toModifyDate, boolean includeClosed, boolean includeNotActivated, int top, boolean includeExcluded){
		try {
			var arr = MessagesDataAccess.getMessages(userId, fromModifyDate, toModifyDate, includeClosed, includeNotActivated, top, includeExcluded);
			var toList = JSONHelper.toListOfModel(arr, new MessagesRepositoryParser()::parse);
			return toList;
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
	
	public static BaseOperationResult excludeMessage(long messageId, Long userId){
		final BaseOperationResult result = new BaseOperationResult();
		
		try {
			MessagesDataAccess.excludeMessage(messageId, userId);
		} catch (Exception ex) {
			UUID errorId = logger.error(ex, "excludeMessage() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
}
