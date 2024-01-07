package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.common.invites.IInviteCreateRequest;
import com.inqwise.opinion.library.common.invites.IInviteSettingsChangeRequest;

public class InvitesDataAccess {

	private static final String ACCOUNT_ID_PARAM = "$invite_account_id";
	private static final String INVITE_ID_PARAM = "$invite_id";
	private static final String INVITE_EXTERNAL_ID_PARAM = "$invite_external_id";
	private static final String INVITE_EMAIL_PARAM = "$invite_email";
	private static final String INVITE_NAME_PARAM = "$invite_name";
	private static final String INVITE_DATE_PARAM = "$invite_date";

	public static void insertInvite(IInviteCreateRequest request,
			IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
					new SqlParam(INVITE_EXTERNAL_ID_PARAM, request.getExternalId()),
					new SqlParam(INVITE_EMAIL_PARAM, request.getEmail()),
					new SqlParam(INVITE_NAME_PARAM, request.getInviteName()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("invites_insertInvite", params);
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

	public static boolean updateInvite(IInviteSettingsChangeRequest request) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(INVITE_ID_PARAM, request.getInviteId()),
					new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
					new SqlParam(INVITE_EMAIL_PARAM, request.getEmail()),
					new SqlParam(INVITE_NAME_PARAM, request.getInviteName()),
					new SqlParam(INVITE_DATE_PARAM, request.getInviteDate()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("invites_updateInvite", params);
        	connection = call.getConnection();
        	call.execute();
            return true;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static boolean deleteInvite(long inviteId, long accountId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(INVITE_ID_PARAM, inviteId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("invites_deleteInvite", params);
        	connection = call.getConnection();
        	call.execute();
            return true;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void getInviteResultSet(Long inviteId, Long accountId,
			String externalId, IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(INVITE_ID_PARAM, inviteId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(INVITE_EXTERNAL_ID_PARAM, externalId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("invites_getInvite", params);
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

	public static JSONArray getInvites(long accountId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("invites_getInvites", params);     
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

}
