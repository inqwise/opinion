package com.inqwise.opinion.library.managers;

import java.util.Date;
import java.util.List;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.UserOperationModel;
import com.inqwise.opinion.library.common.UserOperationRepositoryParser;
import com.inqwise.opinion.library.dao.UsersOperationsDataAccess;

public class UsersOperationsManager {
	static ApplicationLog logger = ApplicationLog.getLogger(UsersOperationsManager.class);
	
	public static List<UserOperationModel> getUserOperations(int top, Long userId,Integer[] usersOperationsTypeIds, Date fromDate, Date toDate, Integer sourceId) {
		try {
			var arr = UsersOperationsDataAccess.getUserOperations(top, userId,usersOperationsTypeIds,fromDate, toDate, sourceId);
			var toList = JSONHelper.toListOfModel(arr, new UserOperationRepositoryParser()::parse);
			return toList;
		} catch (DAOException e) {
			throw new Error(e);
		}
	}

	public static void insertAutoLogin(long userId, String clientIp,
			String countryCode, String sessionId, int sourceId) {
		//UsersOperationsDataAccess
		
		try {
			UsersOperationsDataAccess.insertAutoLogin(userId, clientIp,
					countryCode, sessionId, sourceId);
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
}
