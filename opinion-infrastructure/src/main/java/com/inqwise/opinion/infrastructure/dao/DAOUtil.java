/**
 * 
 */
package com.inqwise.opinion.infrastructure.dao;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;


/**
 * Utility class for DAO's. This class contains commonly used DAO logic which is been refactored in
 * single static methods. As far it contains a PreparedStatement values setter, several quiet close
 * methods and a MD5 hasher which conforms under each MySQL own md5() function.
 * 
 * @author basil
 *
 */
public final class DAOUtil {

	// Constructors
	
	private DAOUtil() {
        // Utility class, hide constructor.
    }

	// Actions
	
	public static void setSqlProcValues(CallableStatement preparedStatement, Object instance) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		
		Method[] methods = instance.getClass().getMethods(); 
		for (Method method : methods) {
			SqlProcParameter parameter = method.getAnnotation(SqlProcParameter.class);
			if(null != parameter){
				String name = identifyParameterName(parameter, method);
				Object value = method.invoke(instance);
				setValue(preparedStatement, new SqlParam(name, value));
			}
		}
	}
	
	public static SqlParams prepareSqlParams(Object instance, Class<?> clazzWithAnnotations) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		SqlParams result = new SqlParams();
		
		Method[] methods = clazzWithAnnotations.getMethods(); 
		for (Method method : methods) {
			SqlProcParameter parameter = method.getAnnotation(SqlProcParameter.class);
			if(null != parameter){
				String name = identifyParameterName(parameter, method);
				Object value = method.invoke(instance);
				result.put(name, value);
			}
		}
		
		return result;
	}
	
	private static String identifyParameterName(SqlProcParameter parameter,
			Method method) {
		String result;
		
		if(parameter.name() == StringUtils.EMPTY){
			result = "$" + StringUtils.join(method.getName().replaceFirst("get", "").split("(?=\\p{Upper})"),'_').toLowerCase();
		} else {
			result = parameter.name(); 
		}
		return result;
	}

	/**
     * Set the given PreparedStatement with the given parameter values.
     * @param preparedStatement The PreparedStatement to set the given parameter values in.
     * @param values The parameter values to be set in the given PreparedStatement.
     * @throws SQLException If something fails during setting the PreparedStatement values.
     */
    public static void setValues(CallableStatement preparedStatement, Object... values)
        throws SQLException
    {
    	int countOfExpected = preparedStatement.getParameterMetaData().getParameterCount() + 1;
    	if(countOfExpected != (null == values ? 1 : values.length + 1)){
    		throw new SQLException("The count of expected parameters not equal to provided. Expected: " + countOfExpected + ". Provided: "+ (null == values ? 1 : (values.length + 1)));
    	}
    	
    	DatabaseMetaData dbmd = preparedStatement.getConnection().getMetaData();
        
        
        if(null != values){
	        for (int i = 0; i < values.length; i++) {
	            if(values[i] instanceof SqlParam){
	            	SqlParam param = (SqlParam)values[i];
	            	setValue(preparedStatement, param);
	        	} else {
	            	preparedStatement.setObject(i + 1, values[i]);
	            }
	        }
        }
    }

	public static void setValue(CallableStatement preparedStatement,
			SqlParam param) throws SQLException {
		try{
			Object value = (param.getValue() instanceof UUID) ?  param.getValue().toString() : param.getValue();
			preparedStatement.setObject(param.getName(), value);//, param.getSqlType(), param.getScale());
			
		} catch(NullPointerException ex){
			throw new SQLException(String.format("Parameter '%s' not exist in procedure '%s'", param.getName(), preparedStatement.toString()), ex);
		}
	}
            
    /**
     * Quietly close the Connection. Any errors will be printed to the stderr.
     * @param connection The Connection to be closed quietly.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                
            }
        }
    }

    /**
     * Quietly close the Statement. Any errors will be printed to the stderr.
     * @param statement The Statement to be closed quietly.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     * @param resultSet The ResultSet to be closed quietly.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                
            }
        }
    }

    /**
     * Generate MD5 hash for the given String. MD5 is kind of an one-way encryption. Very useful for
     * hashing passwords before saving in database. This function generates exactly the same hash as
     * MySQL's own md5() function should do.
     * @param string The String to generate the MD5 hash for.
     * @return The 32-char hexadecimal MD5 hash of the given String.
     */
    public static String hashMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            // Unexpected exception. "MD5" is just hardcoded and supported.
            throw new RuntimeException("MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            // Unexpected exception. "UTF-8" is just hardcoded and supported.
            throw new RuntimeException("UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xff) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xff));
        }
        return hex.toString();
    }

	
}
