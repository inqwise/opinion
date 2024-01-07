package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import com.inqwise.opinion.common.charts.TimePointRange;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;

public class Charts extends DAOBase {

	static ApplicationLog logger = ApplicationLog.getLogger(Charts.class);
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String OPINION_ID_PARAM = "$opinion_id";
	private static final String COLLECTOR_ID_PARAM = "$collector_id";
	private static final String FROM_DATE_PARAM = "$from_date";
	private static final String TO_DATE_PARAM = "$to_date";
	private static final String TIME_POINT_RANGE_PARAM = "$time_point_range";

	public static void getActivityChartData(Long accountId, Long opinionId, Long collectorId, Date fromDate, Date toDate, TimePointRange timePointRange, IResultSetCallback callback) throws DAOException {
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
		SqlParam[] params = {
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(OPINION_ID_PARAM, opinionId),
				new SqlParam(COLLECTOR_ID_PARAM, collectorId),
				new SqlParam(TIME_POINT_RANGE_PARAM, timePointRange.getValue()),
				new SqlParam(FROM_DATE_PARAM, fromDate),
				new SqlParam(TO_DATE_PARAM, toDate),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.OpinionReplication);
			call = factory.GetProcedureCall("getActivityChartData", params);
            connection = call.getConnection();
            resultSet = call.executeQuery();
            
            // min_date, max_date
            callback.call(resultSet, 0);
            
            // charts data
            if(call.getMoreResults()){
            	resultSet.close();
            	resultSet = call.getResultSet();
            	callback.call(resultSet, 1);
            }
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
    }

}
