package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.ResultSets;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class AccountsOperationsDataAccess {
	private static final String ACCOUNTS_OPERATIONS_TYPE_IDS_PARAM = "$accop_type_ids";
	private static final String REFERENCE_ID_PARAM = "$reference_id";
	private static final String TOP_PARAM = "$top";
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String REFERENCE_TYPE_ID_PARAM = "$reference_type_id";
	private static final String FROM_DATE_PARAM = "$from_date";
	private static final String TO_DATE_PARAM = "$to_date";
	private static final String MONETARY_TRANSACTION_PARAM = "$is_monetary";
	private static final String ACCOUNT_OPERATION_ID_PARAM = "$accop_id";

	public static JSONArray getAccountOperations(int top, long accountId, List<Integer> accountsOperationsTypeIds, Long referenceId, Integer referenceTypeId, Date fromDate, Date toDate, Boolean monetary) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNTS_OPERATIONS_TYPE_IDS_PARAM, StringUtils.join(accountsOperationsTypeIds, ",")),
					new SqlParam(REFERENCE_ID_PARAM, referenceId),
					new SqlParam(REFERENCE_TYPE_ID_PARAM, referenceTypeId),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(FROM_DATE_PARAM, fromDate),
					new SqlParam(TO_DATE_PARAM, toDate),
					new SqlParam(MONETARY_TRANSACTION_PARAM, monetary),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getAccountOperations", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            return ResultSets.parse(connection, resultSet);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void getResultSet(long accountOperationId,
			IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_OPERATION_ID_PARAM, accountOperationId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getAccountOperation", params);
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
}
