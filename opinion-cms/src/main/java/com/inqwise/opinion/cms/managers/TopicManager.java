package com.inqwise.opinion.cms.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.cms.common.kb.ITopic;
import com.inqwise.opinion.cms.dao.kb.Topics;
import com.inqwise.opinion.cms.entities.TopicEntity;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class TopicManager {
	
	static ApplicationLog logger = ApplicationLog.getLogger(TopicManager.class);
	
	public static OperationResult<List<ITopic>> getTopics(Integer id, String uri){
		OperationResult<List<ITopic>> result = null;
		final List<ITopic> topics = new ArrayList<ITopic>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				@Override
				public void call(ResultSet reader, int generationId) throws Exception {
					if(generationId == 1){
						while(reader.next()){
							topics.add(new TopicEntity(reader));
						}
					}
				}
			};
			
			Topics.get(callback, id, uri);
			
			result = new OperationResult<List<ITopic>>(topics);
			
		} catch(Exception ex) {
			UUID errorTicket = logger.error(ex, "getTopics : Unexpected error occured");
			result = new OperationResult<List<ITopic>>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	public static OperationResult<ITopic> getTopic(Integer id, String uri) {

		final OperationResult<ITopic> result = new OperationResult<ITopic>();
		try {
			
			IResultSetCallback callback = new IResultSetCallback() {

				@Override
				public void call(ResultSet reader, int generationId)
						throws Exception {
					if (generationId == 1) {
						if (reader.next()) {
							TopicEntity topic = new TopicEntity(reader);
							result.setValue(topic);
						}
					}
				}
			};
			
			Topics.get(callback, id, uri);
			
			if (!result.hasError() && !result.hasValue()) {
				result.setError(ErrorCode.NoResults);
			}

		} catch (Exception ex) {
			UUID errorId = logger.error(ex,
					"getTopic() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}

		return result;
	}
	
	public static OperationResult<Integer> setTopic(String name, String uri){
		OperationResult<Integer> result = new OperationResult<Integer>();
		try{
			result.setValue(Topics.set(null, name, uri));
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "setTopic : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	public static BaseOperationResult updateTopic(Integer id, String name, String uri){
		BaseOperationResult result = new BaseOperationResult();
		try{
			Topics.set(id, name, uri);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "updateTopic : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static BaseOperationResult deleteTopic(Integer id){
		BaseOperationResult result = new BaseOperationResult();
		try{
			Topics.delete(id);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "deleteTopic : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
}
