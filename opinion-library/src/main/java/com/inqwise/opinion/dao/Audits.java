package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.UUID;

import com.inqwise.opinion.common.audit.IAuditRequest;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;

public class Audits extends DAOBase {

	public static BaseOperationResult setOpinionAudit(IAuditRequest request, UUID actionGuid)  throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		try {
			
			SqlParam[] params = {
				new SqlParam("$audit_type_id", request.getAuditType().getValue()), 
				new SqlParam("$opinion_id", request.getOpinionId()),
				new SqlParam("$user_id", request.getUserId()),
				new SqlParam("$audit_message", request.getAuditMessage()),
				new SqlParam("$audit_data", request.getAuditData()),
				new SqlParam("$action_guid", actionGuid),
				new SqlParam("$sheet_id", request.getSheetId()),
				new SqlParam("$control_id", request.getControlId()),
				new SqlParam("$collector_id", request.getCollectorId()),
				new SqlParam("$translation_id", request.getTranslationId()),
				};

		
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	
        	call = factory.GetProcedureCall("setOpinionAudit", params);
        	connection = call.getConnection();
            call.execute();
            return BaseOperationResult.Ok;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
