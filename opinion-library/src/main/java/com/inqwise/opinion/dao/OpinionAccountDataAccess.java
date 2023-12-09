package com.inqwise.opinion.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.IOpinionAccount;
import com.inqwise.opinion.opinion.common.SessionCreditInfo;

public class OpinionAccountDataAccess extends DAOBase {

	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String SERVICE_PACKAGE_ID_PARAM = "$service_package_id";
	private static final String JOB_ID_PARAM = "$job_id";
	private static final String WELCOME_FLAG_PARAM = "$show_welcome_message";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String AMOUNT_PARAM = "$amount";
	private static final String UNLIMITED_BALANCE_PARAM = "$unlimited";

	public static void getAccount(long accountId, int servicePackageId, IResultSetCallback callback) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params = {
			new SqlParam(ACCOUNT_ID_PARAM, accountId),
			new SqlParam(SERVICE_PACKAGE_ID_PARAM, servicePackageId),
		};
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getAccountOpinionSettings", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			callback.call(resultSet, 0);
			
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static OperationResult<List<SessionCreditInfo>> setSessionsCredit(
			int jobId) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		OperationResult<List<SessionCreditInfo>> result = null;
		
		SqlParam[] params = {
			new SqlParam(JOB_ID_PARAM, jobId),
		};
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("setSessionsCredits", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			List<SessionCreditInfo> sessionCreditInfoList = new ArrayList<SessionCreditInfo>();
			
			while (resultSet.next()) {
				SessionCreditInfo info = new SessionCreditInfo();
				info.setAccountId(resultSet.getLong("account_id"));
				info.setSessionAmountBalance(ResultSetHelper.optLong(resultSet, "new_sessions_balance"));
				info.setServicePackageId(resultSet.getInt("service_package_id"));
				sessionCreditInfoList.add(info);
			} 
			
			if(sessionCreditInfoList.size() == 0) {
				result = new OperationResult<List<SessionCreditInfo>>(ErrorCode.NoResults);
			} else {
				result = new OperationResult<List<SessionCreditInfo>>(sessionCreditInfoList);
			}
			
			return result;
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void updateWelcomeMessageFlag(Long accountId, boolean flag,
			long userId) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		SqlParam[] params = {
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(WELCOME_FLAG_PARAM, flag),
				new SqlParam(USER_ID_PARAM, userId),
		};
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("setAccountWelcomeMessageFlag", params);
			connection = call.getConnection();
			call.execute();
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static OperationResult<Long> changeSessionsBalance(long accountId,
			int servicePackageId, Integer amount, Boolean unlimitedBalance) throws DAOException {
		OperationResult<Long> result = new OperationResult<Long>();
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		SqlParam[] params = {
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(SERVICE_PACKAGE_ID_PARAM, servicePackageId),
				new SqlParam(AMOUNT_PARAM, amount),
				new SqlParam(UNLIMITED_BALANCE_PARAM, unlimitedBalance),
		};
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("changeAccountSessionsBalance", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			if(resultSet.next()){
				result.setValue(ResultSetHelper.optLong(resultSet, "sessions_balance"));
			} else {
				result.setError(ErrorCode.NoResults);
			}
			
			return result;
			
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
