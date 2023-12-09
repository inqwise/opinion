package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class ParametersDataAccess {

	private static final String ENTITY_TYPE_ID_PARAM = "$entity_type_id";
	private static final String CATEGORIES_PARAM = "$categories";
	private static final String ENTITY_ID_PARAM = "$entity_id";
	private static final String DEFINITION_ID_PARAM = "$definition_id";
	private static final String VALUE_PARAM = "$ref_value";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String KEY_PARAM = "$definition_key";
	private static final String REFERENCE_KEY_PARAM = "$reference_key";
	private static final String DEPENDS_ON_KEY_PARAM = "$depends_on_key";
	private static final String VIA_REFERENCES_PARAM = "$via_references";

	public static void getVariablesResultSet(long entityId, int entityTypeId,
			Integer[] categories, IResultSetCallback callback, String[] viaReferences) throws DAOException {
		
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params = {
				new SqlParam(ENTITY_ID_PARAM, entityId),
				new SqlParam(ENTITY_TYPE_ID_PARAM, entityTypeId),
				new SqlParam(CATEGORIES_PARAM, StringUtils.join(categories)),
				new SqlParam(VIA_REFERENCES_PARAM, StringUtils.join(viaReferences)),
        };
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("prm_getVariables", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            // definitions
            callback.call(resultSet, 0);
            
            // references
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

	public static void updateVariable(Integer entityTypeId, long entityId,
			String key, Object value, long userId, String dependsOnKey) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params = {
				new SqlParam(ENTITY_TYPE_ID_PARAM, entityTypeId),
				new SqlParam(ENTITY_ID_PARAM, entityId),
				new SqlParam(KEY_PARAM, key),
				new SqlParam(VALUE_PARAM, value),
				new SqlParam(USER_ID_PARAM, userId),
				new SqlParam(DEPENDS_ON_KEY_PARAM, dependsOnKey),
        };
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("prm_setVariable", params);
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
	
	public static void deleteReferences(String key, String referenceKey, String dependsOnKey) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params = {
				new SqlParam(KEY_PARAM, key),
				new SqlParam(REFERENCE_KEY_PARAM, referenceKey),
				new SqlParam(DEPENDS_ON_KEY_PARAM, dependsOnKey),
        };
		
		try {
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("prm_deleteReferences", params);
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
