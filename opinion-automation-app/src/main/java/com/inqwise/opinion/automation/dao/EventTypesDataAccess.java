package com.inqwise.opinion.automation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.automation.common.eventTypes.EventType;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;

public class EventTypesDataAccess {
	private static final String EVENT_TYPE_ID_PARAM = "$event_type_id";
	private static final String EVENT_TYPE_NAME_PARAM = "$event_type_name";
	private static final String EVENT_TYPE_DESCRIPTION_PARAM = "$event_type_description";
	private static final String EVENT_TYPE_RECIPIENTS_PARAM = "$event_type_recipients";

	public static void fillEventTypes(IResultSetCallback callback) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("autm_getEventTypes", params);     
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
	
	public static void setEventType(EventType eventType) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(EVENT_TYPE_ID_PARAM, eventType.getId()),
					new SqlParam(EVENT_TYPE_NAME_PARAM, eventType.getName()),
					new SqlParam(EVENT_TYPE_DESCRIPTION_PARAM, eventType.getDescription()),
					new SqlParam(EVENT_TYPE_RECIPIENTS_PARAM, eventType.getRecipients()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("autm_setEventType", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
