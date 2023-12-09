/**
 * 
 */
package com.inqwise.opinion.infrastructure.dao;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationConfigurationException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration;
import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration.DAOConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class Database {
	
    private static final Map<String, Database> INSTANCES = new HashMap<String, Database>();
    private static Boolean shootdownHookInitialized = false;
    // Vars
    
    private ComboPooledDataSource dataSource;

    static {
    	/*
    	Runtime.getRuntime().addShutdownHook(new Thread() {
    	    public void run() {
    	    	finish();
    	    }
    	});
    	*/
    	shootdownHookInitialized = true;
    }
    
    public static void finish(){
    	if(shootdownHookInitialized){
	    	synchronized (shootdownHookInitialized) {
	    		if(shootdownHookInitialized){
	    			for (Database db : INSTANCES.values()) {
						db.destroy();
					}
	    			
	    			INSTANCES.clear();
	    		}
			}
    	}
    }
    
	protected void destroy() {
		try {
			ApplicationLog.getLogger(Database.class).info("destroy() : datasource '%s'", dataSource.getDataSourceName());
			DataSources.destroy(dataSource);
		} catch (SQLException e) {
			ApplicationLog.getLogger(Database.class).error(e, "destroy() : Unexpected error occured");
		}
	}

	/**
     * Construct a DAOFactory instance for the given database name.
     * @param name The database name to construct a DAOFactory instance for.
     * @throws DAOConfigurationException If the properties file is missing in the classpath or
     * cannot be loaded, or if a required property is missing in the properties file, or if either
     * the driver cannot be loaded or the datasource cannot be found.
     */
    protected Database(String name) throws DAOConfigurationException {
        
    	DAOConfiguration config = BaseApplicationConfiguration.DAOConfiguration.getSectionConfig(name);
    	
    	try {
        	dataSource = new ComboPooledDataSource(name); 
        	dataSource.setDriverClass(config.getDriver()); 
        	dataSource.setJdbcUrl(config.getUrl()); 
        	dataSource.setUser(config.getUsername()); 
        	dataSource.setPassword(config.getPassword());
        	
        } catch (Exception e) {
            throw new DAOConfigurationException(
                "DataSource '" + config.getUrl() + "' is missing.", e);
        }
    }
    
    protected Database(){}
    
    /**
	 * Returns a DAOFactory instance for the given database name.
	 * @param name The database name to return a DAOFactory instance for.
	 * @return A DAOFactory instance for the given database name.
	 * @throws DAOConfigurationException If the database name is null, or if the properties file is
	 * missing in the classpath or cannot be loaded, or if a required property is missing in the
	 * properties file, or if either the driver cannot be loaded or the datasource cannot be found.
	 */
	private static Database getInstanceInternal(String name) throws DAOConfigurationException {
	    if (name == null) {
	    	try{
	    		name = BaseApplicationConfiguration.DAOConfiguration.getDefaultSectionName();
	    	} catch(ApplicationConfigurationException e){
	    		throw new DAOConfigurationException("Failed to get default database name.", e);
	    	}
	    }
	    Database instance = Database.INSTANCES.get(name);
	    return instance;
	}

	public static void setInstance(String name, Database value){
		Database.INSTANCES.put(name, value);
	}
    
	public static Database getInstance(String name) throws DAOConfigurationException {
		Database instance = getInstanceInternal(name);
        if (instance == null) {
            instance = new Database(name);
            setInstance(name, instance);
        }
        return instance;
    }
	
    // Actions
    
    /**
	 * Returns a connection to the database. Package private so that it can be used inside the DAO
	 * package only.
	 * @return A connection to the database.
	 * @throws SQLException If acquiring the connection fails.
	 */
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	private Class<?> findClassWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass){
		Class<?> result = null;
		if(clazz.isAnnotationPresent(annotationClass)){
			result = clazz;
		} else {
			Class<?>[] interfaces = clazz.getInterfaces();
			for (Class<?> interfaze : interfaces) {
				result = findClassWithAnnotation(interfaze, annotationClass);
				if(null != result) break;
			}
			
			Class<?> superClass = null;
			if(null == result){
				superClass = clazz.getSuperclass();
				if(null != superClass){
					result = findClassWithAnnotation(superClass, annotationClass);
				}
			}
		}
		
		return result;
	}
	
	public CallableStatement GetProcedureCallFromSqlProc(String procedureName, Object instance)
			throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = findClassWithAnnotation(instance.getClass(), SqlProc.class);
		
		if(null == clazz){
			throw new java.lang.UnsupportedOperationException("instance class required to implement @SqlProc annotation");
		}
		
		SqlParam[] params = DAOUtil.prepareSqlParams(instance, clazz).toArray();
		
		return GetProcedureCall(procedureName, params);
	}
	
	public CallableStatement GetProcedureCall(String procedureName, SqlParam... params)
			throws SQLException {
		Connection connection = getConnection();
		
		CallableStatement call;
		try {
			final String prepareCallFormat = "{ CALL %s(%s) }";
	    	String questionsStr = (null == params ? "" : StringUtils.repeat("?,", params.length));
	    	
	    	if(questionsStr.length() > 0){
	    		questionsStr = questionsStr.substring(0, questionsStr.length() - 1);
	    	}
	    	
	    	String prepareCallString = String.format(prepareCallFormat, procedureName, questionsStr);
			call = connection.prepareCall(prepareCallString);
			
			DAOUtil.setValues(call, (Object[])params);
			
		} catch (SQLException e) {
			DAOUtil.close(connection);
			throw e;
		}
		
		return call;
	}

	public CallableStatement GetProcedureCall(String procedureName, Object... params)
			throws SQLException {
		Connection connection = getConnection();
		
		CallableStatement call;
		try {
			final String prepareCallFormat = "{ CALL %s(%s}) }";
	    	String questionsStr = StringUtils.repeat("?,", params.length);
	    	
	    	if(questionsStr.length() > 0){
	    		questionsStr = questionsStr.substring(0, questionsStr.length() - 1);
	    	}
	    	
	    	String prepareCallString = String.format(prepareCallFormat, procedureName, questionsStr);
			call = connection.prepareCall(prepareCallString);
			
			DAOUtil.setValues(call, params);
			
		} catch (SQLException e) {
			DAOUtil.close(connection);
			throw e;
		}
		
		return call;
	}
    
}
