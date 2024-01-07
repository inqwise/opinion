package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class UsersOperationsDataAccess {
	private static final String USERS_OPERATIONS_TYPE_IDS_PARAM = "$usop_type_ids";
	private static final String TOP_PARAM = "$top";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String CLIENT_IP_PARAM = "$client_ip";
	private static final String COUNTRY_CODE_PARAM = "$geo_country_code";
	private static final String SESSION_ID_PARAM = "$session_id";
	private static final String SOURCE_ID_PARAM = "$source_id";
	private static final String FROM_DATE_PARAM = "$from_date";
	private static final String TO_DATE_PARAM = "$to_date";

	public static JSONArray getUserOperations(int top, Long userId, Integer[] usersOperationsTypeIds, Date fromDate, Date toDate, Integer sourceId) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(USERS_OPERATIONS_TYPE_IDS_PARAM, StringUtils.join(usersOperationsTypeIds, ",")),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(FROM_DATE_PARAM, fromDate),
					new SqlParam(TO_DATE_PARAM, toDate),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getUserOperations", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            List<JSONObject> list = DSL.using(connection).fetch(resultSet)
			.map(r -> {
				JSONObject obj = new JSONObject();
				
				for(var field : r.fields()) {
					obj.put(field.getName(), r.getValue(field));
				}
				return obj;
			});
        	
            return new JSONArray(list);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void insertAutoLogin(long userId, String clientIp,
			String countryCode, String sessionId, int sourceId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(CLIENT_IP_PARAM, clientIp),
					new SqlParam(COUNTRY_CODE_PARAM, countryCode),
					new SqlParam(SESSION_ID_PARAM, sessionId),
					new SqlParam(SOURCE_ID_PARAM, sourceId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setAutoLogin", params);     
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
}
