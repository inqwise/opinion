package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public final class MessagesDataAccess {

	private static final String USER_ID_PARAM = "$user_id";
	private static final String FROM_MODIFY_DATE_PARAM = "$from_modify_date";
	private static final String TOP_PARAM = "$top";
	private static final String TO_MODIFY_DATE_PARAM = "$to_modify_date";
	private static final String INCLUDE_CLOSED_PARAM = "$include_closed";
	private static final String INCLUDE_NOT_ACTIVATED_PARAM = "$include_not_activated";
	private static final String MESSAGE_NAME_PARAM = "$message_name";
	private static final String MESSAGE_CONTENT_PARAM = "$message_content";
	private static final String MODIFY_USER_ID_PARAM = "$modify_user_id";
	private static final String MESSAGE_ID_PARAM = "$message_id";
	private static final String CLOSE_USER_ID_PARAM = "$close_user_id";
	private static final String ACTIVATE_DATE_PARAM = "$activate_date";
	private static final String INCLUDE_EXCLUDED_PARAM = "$include_excluded";

	public static void insertMessage(String messageName, String messageContent,
			long userId, long modifyUserId, IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(MESSAGE_NAME_PARAM, messageName),
					new SqlParam(MESSAGE_CONTENT_PARAM, messageContent),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(MODIFY_USER_ID_PARAM, modifyUserId),
					new SqlParam(MESSAGE_ID_PARAM, null),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("msg_setMessage", params);
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

	public static void activateMessage(long messageId, Date activateDate,
			long modifyUserId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(MESSAGE_ID_PARAM, messageId),
					new SqlParam(ACTIVATE_DATE_PARAM, activateDate),
					new SqlParam(MODIFY_USER_ID_PARAM, modifyUserId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("msg_activateMessage", params);
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

	public static void closeMessage(long messageId, long closeUserId, Long userId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(MESSAGE_ID_PARAM, messageId),
					new SqlParam(CLOSE_USER_ID_PARAM, closeUserId),
					new SqlParam(USER_ID_PARAM, userId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("msg_closeMessage", params);
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

	public static void deleteMessage(long messageId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(MESSAGE_ID_PARAM, messageId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("msg_deleteMessage", params);
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

	public static void updateMessage(long messageId, String messageName,
			String messageContent, long modifyUserId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(MESSAGE_NAME_PARAM, messageName),
					new SqlParam(MESSAGE_CONTENT_PARAM, messageContent),
					new SqlParam(MESSAGE_ID_PARAM, messageId),
					new SqlParam(MODIFY_USER_ID_PARAM, modifyUserId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("msg_setMessage", params);
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

	public static JSONArray getMessages(Long userId,
			Date fromModifyDate, Date toModifyDate, boolean includeClosed,
			boolean includeNotActivated, int top, boolean includeExcluded) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(FROM_MODIFY_DATE_PARAM, fromModifyDate),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(TO_MODIFY_DATE_PARAM, toModifyDate),
					new SqlParam(INCLUDE_CLOSED_PARAM, includeClosed),
					new SqlParam(INCLUDE_NOT_ACTIVATED_PARAM, includeNotActivated),
					new SqlParam(INCLUDE_EXCLUDED_PARAM, includeExcluded),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("msg_getMessages", params);     
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

	public static void excludeMessage(long messageId, Long userId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(MESSAGE_ID_PARAM, messageId),
					new SqlParam(USER_ID_PARAM, userId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("msg_excludeMessage", params);
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
