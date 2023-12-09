package com.cint.core;

/**
 * 
 * Class contains http specific configuration parameters
 * 
 */

public class HttpConfiguration {

	private int maxRetry;
	private int readTimeout;
	private int connectionTimeout;
	private int maxHttpConnection;
	private String endPointUrl;

	private boolean trustAll;
	private int retryDelay;
	private String ipAddress;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public HttpConfiguration() {

		this.maxRetry = 2;

		this.readTimeout = 0;

		this.connectionTimeout = 0;

		this.maxHttpConnection = 10;

		this.endPointUrl = null;

		this.trustAll = true;

		this.retryDelay = 1000;
		this.ipAddress = "127.0.0.1";
	}

	public int getMaxHttpConnection() {
		return maxHttpConnection;
	}

	public void setMaxHttpConnection(int maxHttpConnection) {
		this.maxHttpConnection = maxHttpConnection;
	}

	public int getRetryDelay() {
		return retryDelay;
	}

	public void setRetryDelay(int retryDelay) {
		this.retryDelay = retryDelay;
	}

	public boolean isTrustAll() {
		return trustAll;
	}

	public void setTrustAll(boolean trustAll) {
		this.trustAll = trustAll;
	}

	public String getEndPointUrl() {
		return endPointUrl;
	}

	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	public int getMaxRetry() {
		return maxRetry;
	}

	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
}
