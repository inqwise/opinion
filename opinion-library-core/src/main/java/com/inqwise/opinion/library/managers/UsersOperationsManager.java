package com.inqwise.opinion.library.managers;

import java.util.Date;
import java.util.UUID;

import net.casper.data.model.CDataCacheContainer;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.dao.UsersOperationsDataAccess;

public class UsersOperationsManager {
	static ApplicationLog logger = ApplicationLog.getLogger(UsersOperationsManager.class);
	
	public static CDataCacheContainer getUserOperations(int top, Long userId,
			Integer[] usersOperationsTypeIds, Date fromDate, Date toDate, Integer sourceId) {
		
		try {
			return UsersOperationsDataAccess.getUserOperations(top, userId,
					usersOperationsTypeIds,fromDate, toDate, sourceId);
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
