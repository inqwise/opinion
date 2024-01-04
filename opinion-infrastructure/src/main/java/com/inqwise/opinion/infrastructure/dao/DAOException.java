/**
 * 
 */
package com.inqwise.opinion.infrastructure.dao;

import java.lang.reflect.Field;
import java.sql.CallableStatement;

import com.mchange.v2.c3p0.impl.NewProxyCallableStatement;

/**
 * This class represents a generic DAO exception. It should wrap any exception of the underlying
 * code, such as SQLExceptions.
 * @author basil
 *
 */
public class DAOException extends Exception {
	
	// Constructors
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a DAOException with the given detail message.
     * @param message The detail message of the DAOException.
     */
	public DAOException(String message) {
		super(message);
	}
	
	/**
     * Constructs a DAOException with the given root cause.
     * @param cause The root cause of the DAOException.
     */
	public DAOException(Throwable cause) {
		super(cause);
	}
	
	/**
     * Constructs a DAOException with the given detail message and root cause.
     * @param message The detail message of the DAOException.
     * @param cause The root cause of the DAOException.
     */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	private static String getMessage(CallableStatement call){
		if(call instanceof com.mchange.v2.c3p0.impl.NewProxyCallableStatement){
			NewProxyCallableStatement proxyCall = (NewProxyCallableStatement)call;
			CallableStatement innerCall;
			try {
				Field f = proxyCall.getClass().getDeclaredField("inner");
				f.setAccessible(true);
				innerCall = (CallableStatement) f.get(proxyCall); //IllegalAccessException
			} catch (Throwable t){
				innerCall = proxyCall;
			}
			return innerCall.toString();
		} else {
			return call.toString();
		}
	}
	
	public DAOException(CallableStatement call, Throwable cause) {
		super(getMessage(call), cause);
	}
}
