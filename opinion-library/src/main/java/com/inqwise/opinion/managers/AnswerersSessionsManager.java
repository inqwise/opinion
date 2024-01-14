package com.inqwise.opinion.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.inqwise.opinion.common.AnswererSessionModel;
import com.inqwise.opinion.common.AnswererSessionRepositoryParser;
import com.inqwise.opinion.common.IAnswererSession;
import com.inqwise.opinion.dao.AnswerersSessionsDataAccess;
import com.inqwise.opinion.entities.analizeResults.AnswererSessionEntity;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class AnswerersSessionsManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(AnswerersSessionsManager.class);

	public static List<AnswererSessionModel> getAnswerersSessions(Long opinionId, Long accountId, Long collectorId, String respondentId, boolean includeUnplanned, Long fromIndex, Integer top, AtomicLong total){		
		var arr = AnswererSessionEntity.getAnswerersSessions(opinionId, accountId, collectorId, respondentId, includeUnplanned, fromIndex, top, total);
		var toList = JSONHelper.toListOfModel(arr, new AnswererSessionRepositoryParser()::parse); 
		return toList;
	}

	private static Object _sessionsCacheLocker = new Object(); 
	private static LoadingCache<Integer, OperationResult<IAnswererSession>> _sessionsCache;
	private static LoadingCache<Integer, OperationResult<IAnswererSession>> getSessionsCache(){
		if(null == _sessionsCache){
			synchronized (_sessionsCacheLocker) {
				if(null == _sessionsCache){
					_sessionsCache = CacheBuilder.newBuilder().
							maximumSize(1000).
							expireAfterWrite(30, TimeUnit.SECONDS).
							build( new CacheLoader<Integer, OperationResult<IAnswererSession>>() {
								public OperationResult<IAnswererSession> load(Integer hash) throws Exception {
									return null;
								}
							});
				}
			}
		}
		
		return _sessionsCache;
	}
	
	public static OperationResult<IAnswererSession> getAnswererSession(Long id, final String answererSessionId, Long accountId){
		OperationResult<IAnswererSession> result = null;
		if(null == id && null == accountId){
			try {
				result = getSessionsCache().get(answererSessionId.hashCode(), new Callable<OperationResult<IAnswererSession>>() {

					@Override
					public OperationResult<IAnswererSession> call()
							throws Exception {
						return getAnswererSessionInternal(null, answererSessionId, null);
					}

					
				});
			} catch (ExecutionException e) {
				UUID errorId = logger.error(e, "getAnswererSession() : Unexpected error occured.");
				result = new OperationResult<IAnswererSession>(ErrorCode.GeneralError, errorId);
			}
		} else {
			result = getAnswererSessionInternal(id, answererSessionId, accountId);
		}
		
		return result;
	}
	
	private static OperationResult<IAnswererSession> getAnswererSessionInternal(Long id, String answererSessionId, Long accountId){
		return AnswererSessionEntity.getAnswererSession(id, answererSessionId, accountId);
	}

	public static OperationResult<IAnswererSession> setAnswererSessionGrade(
			long id, Long accountId, Integer grade,
			String comment, Long userId) {
		
		BaseOperationResult result = null;
		OperationResult<IAnswererSession> sessionResult = getAnswererSession(id, null, accountId);
		AnswererSessionEntity session = null;
		if(sessionResult.hasError()){
			result = sessionResult;
		} else {
			try {
				session = (AnswererSessionEntity) sessionResult.getValue();
			} catch (Exception e) {
				UUID errorId = logger.error(e, "setAnswererSessionGrade() : Unexpected error occured.");
				result = new OperationResult<String>(ErrorCode.GeneralError, errorId);
			}
		}
		
		if(null == result){
			result = session.updateGrade(accountId, grade, comment, userId);
		}
		
		
		if(result.hasError()){
			return result.toErrorResult();
		} else {
			return sessionResult;
		}
		
	}
	
	public static OperationResult<IAnswererSession> setAnswererSessionStarred(
			long id, Long accountId, boolean starred, Long userId) {
		
		BaseOperationResult result = null;
		OperationResult<IAnswererSession> sessionResult = getAnswererSession(id, null, accountId);
		AnswererSessionEntity session = null;
		if(sessionResult.hasError()){
			result = sessionResult;
		} else {
			try {
				session = (AnswererSessionEntity) sessionResult.getValue();
			} catch (Exception e) {
				UUID errorId = logger.error(e, "setAnswererSessionStarred() : Unexpected error occured.");
				result = new OperationResult<String>(ErrorCode.GeneralError, errorId);
			}
		}
		
		if(null == result){
			result = session.updateStarred(accountId, starred, userId);
		}
		
		
		if(result.hasError()){
			return result.toErrorResult();
		} else {
			return sessionResult;
		}
	}
	
	public static BaseOperationResult deleteAnswererSession(long id, Long accountId, Long userId){
		
		BaseOperationResult result;
		try {
			result = AnswerersSessionsDataAccess.deleteAnswerSession(id, accountId, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "deleteAnswererSession() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<List<BaseOperationResult>> deleteAnswererSessions(
			List<Long> ids, Long accountId, Long userId) {
		List<BaseOperationResult> resultsList = new ArrayList<BaseOperationResult>();
		
		for (Long id : ids) {
			BaseOperationResult deleteResult = deleteAnswererSession(id, accountId, userId);
			if(deleteResult.hasError()){
				resultsList.add(deleteResult);
			} else {
				resultsList.add(new BaseOperationResult(id));
			}
		}
		
		return new OperationResult<List<BaseOperationResult>>(resultsList);
	}

	public static OperationResult<IAnswererSession> getLast(Long collectorId,
			String respondentId, ErrorCode errorWhenCompleted, boolean includeUnplanned) {
		
		OperationResult<IAnswererSession> result = null;
		if(null == respondentId){
			UUID errorId = logger.error("GetLast : respondentId is null");
			result = new OperationResult<IAnswererSession>(ErrorCode.ArgumentNull, errorId, "respondentId");
		}
		
		
		IAnswererSession session = null;
		
		if(null == result){
			OperationResult<IAnswererSession> sessionResult = AnswererSessionEntity.getLastAnswererSession(collectorId, respondentId, includeUnplanned);
			if(sessionResult.hasError()){
				result = sessionResult.toErrorResult();
			} else {
				session = sessionResult.getValue();
			}
			
		}
		
		if(null == result){
			if(null != errorWhenCompleted && null != session.getFinishDate()){
				logger.warn("Duplicated respondent #'%s'", respondentId);
				result = new OperationResult<IAnswererSession>(errorWhenCompleted);
			}
		}
		
		if(null == result){
			result = new OperationResult<IAnswererSession>(session);
		}
		return result;
	}
}
