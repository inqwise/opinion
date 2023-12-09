package com.cint.systemFramework;

import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration;

public class CintConfig extends BaseApplicationConfiguration {
	private static final String API_TOKEN_KEY = "cint.api-token";
	private static final String SECRET_KEY = "cint.secret";
	private static final String END_POINT_KEY = "cint.end-point";
	private static final String TRUST_ALL_CONNECTION_KEY = "cint.trust-all-connection";
	private static final String CONNECTION_TIMEOUT_KEY = "cint.connection-timeout";
	private static final String RETRY_KEY = "cint.retry";
	private static final String READ_TIMEOUT_KEY = "cint.read-timeout";
	private static final String MAX_CONECTION_KEY = "cint.max-conection";
	
	public static String getApiToken(){
		return getValue(API_TOKEN_KEY, true);
	}
	
	public static String getSecret(){
		return getValue(SECRET_KEY, true);
	}
	
	public static String getEndPoint(){
		return getValue(END_POINT_KEY, true);
	}

	public static boolean isTrustAllConnection() {
		return Boolean.parseBoolean(getValue(TRUST_ALL_CONNECTION_KEY, "true"));
	}
	
	public static int getConnectionTimeout() {
		return Integer.parseInt(getValue(CONNECTION_TIMEOUT_KEY, "3000"));
	}
	
	public static int getMaxRetry() {
		return Integer.parseInt(getValue(RETRY_KEY, "2"));
	}
	
	public static int getReadTimeout() {
		return Integer.parseInt(getValue(READ_TIMEOUT_KEY, "120000"));
	}
	
	public static int getMaxHttpConnection() {
		return Integer.parseInt(getValue(MAX_CONECTION_KEY, "100"));
	}
}
