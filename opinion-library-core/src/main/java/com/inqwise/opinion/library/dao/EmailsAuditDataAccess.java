package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class EmailsAuditDataAccess {

	private static final String EMAILS_AUDIT_KEY_PARAM = "$email_audit_key";
	private static final String EMAILS_AUDIT_CONTENT_PARAM = "$email_audit_content";
	private static final String EMAIL_TYPE_PARAM = "$email_type_id";

	public static void set(String key, String content, int emailTypeId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(EMAILS_AUDIT_KEY_PARAM, key),
					new SqlParam(EMAILS_AUDIT_CONTENT_PARAM, content),
					new SqlParam(EMAIL_TYPE_PARAM, emailTypeId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("setEmailAudit", params);
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

	public static Boolean isKeyExist(String key) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(EMAILS_AUDIT_KEY_PARAM, key),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getEmailAudit", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            return resultSet.next();
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

}
