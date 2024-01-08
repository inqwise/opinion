package com.inqwise.opinion.dao;

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
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;

public class ThemesDataAccess extends DAOBase {

	private static final String THEME_ID_PARAM = "$theme_id";
	private static final String THEME_NAME_PARAM = "$theme_name";
	private static final String OPINION_TYPE_ID_PARAM = "$opinion_type_id";
	private static final String CSS_CONTENT_PARAM = "$css_content";
	private static final String OWNER_ID_PARAM = "$owner_id";
	private static final String IS_TEMPLATE_PARAM = "$is_template";
	private static final String TOP_PARAM = "$top";
	private static final String OPINION_GUID_PARAM = "$opinion_guid";
	private static final String COLLECTOR_GUID_PARAM = "$collector_uuid";
	private static final String TRANSLATION_ID_PARAM = "$translation_id";
	

	public static JSONArray getThemes(Long accountId, Integer top, int opinionTypeId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(OWNER_ID_PARAM, accountId),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(OPINION_TYPE_ID_PARAM, opinionTypeId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getThemes", params);     
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

	public static void getTheme(IResultSetCallback callback, Integer themeId, Long accountId, String opinionGuid, String collectorGuid, Long translationId) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params =  
		{
				new SqlParam(THEME_ID_PARAM, themeId),
				new SqlParam(OWNER_ID_PARAM, accountId),
				new SqlParam(OPINION_GUID_PARAM, opinionGuid),
				new SqlParam(COLLECTOR_GUID_PARAM, collectorGuid),
				new SqlParam(TRANSLATION_ID_PARAM, translationId),
        };
        
		try {
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getTheme", params);
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

	public static void insertTheme(IResultSetCallback callback, String name,
			int opinionTypeId, String cssContent,
			Long accountId, boolean isTemplate) throws DAOException {

		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params =  
		{
				new SqlParam(THEME_NAME_PARAM, name),
				new SqlParam(OPINION_TYPE_ID_PARAM, opinionTypeId),
				new SqlParam(CSS_CONTENT_PARAM, cssContent),
				new SqlParam(OWNER_ID_PARAM, accountId),
				new SqlParam(IS_TEMPLATE_PARAM, isTemplate),
        };
        
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("insertTheme", params);
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

	public static void updateTheme(IResultSetCallback callback,
			Integer themeId, String cssContent, Long accountId) throws DAOException {

		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params =  
		{
				new SqlParam(THEME_ID_PARAM, themeId),
				new SqlParam(CSS_CONTENT_PARAM, cssContent),
				new SqlParam(OWNER_ID_PARAM, accountId),
        };
        
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("updateThemeCssContent", params);
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

	public static void deleteTheme(int themeId, long accountId) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		SqlParam[] params =  
		{
				new SqlParam(THEME_ID_PARAM, themeId),
				new SqlParam(OWNER_ID_PARAM, accountId),
        };
        
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("deleteTheme", params);
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
