package com.inqwise.opinion.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.AccountOpinionInfo;
import com.inqwise.opinion.opinion.common.collectors.ICollector;

public class AccountsDataAccess extends DAOBase {

	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String DAYS_BACK_PARAM = "$days";
	private static final String OPINION_ID_PARAM = "$opinion_id";

	public static OperationResult<AccountOpinionInfo> getAccountShortStatistics(Long accountId, Integer days, Long opinionId) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;

		SqlParam[] params = {
			new SqlParam(ACCOUNT_ID_PARAM, accountId),
			new SqlParam(DAYS_BACK_PARAM, days),
			new SqlParam(OPINION_ID_PARAM, opinionId),
		};
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getAccountShortStatistics", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			
			int countOfCollectors = 0;
			int countOfOpinions = 0;
			int countOfStartedSessions = 0;
			int countOfFinishedSessions = 0;
			Date lastStartedDate = null;
			Date lastFinishedDate = null;
			if (resultSet.next()) {
				countOfCollectors = ResultSetHelper.optInt(resultSet, "cnt_of_collectors", 0);
				countOfOpinions = ResultSetHelper.optInt(resultSet, "cnt_of_opinions", 0);
				countOfStartedSessions = resultSet.getInt("cnt_started_sessions");
				countOfFinishedSessions = resultSet.getInt("cnt_finished_sesions");
				lastStartedDate = ResultSetHelper.optDate(resultSet, "last_finish_date");
				lastFinishedDate = ResultSetHelper.optDate(resultSet, "last_start_date");
			} 
			
			return new OperationResult<AccountOpinionInfo>(new AccountOpinionInfo(countOfCollectors, countOfOpinions, countOfStartedSessions, countOfFinishedSessions, lastStartedDate, lastFinishedDate));
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
