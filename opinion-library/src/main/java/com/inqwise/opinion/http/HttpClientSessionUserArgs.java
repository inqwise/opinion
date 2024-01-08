package com.inqwise.opinion.http;

import org.msgpack.annotation.Message;

@Message
public class HttpClientSessionUserArgs {
	private long userId;
	private String clientIp;
	private int productId;
	public HttpClientSessionUserArgs(long userId, int productId,
			String clientIp) {
		setUserId(userId).setProductId(productId).setClientIp(clientIp);
	}
	
	public HttpClientSessionUserArgs() {
	
	}
	
	public long getUserId() {
		return userId;
	}
	public HttpClientSessionUserArgs setUserId(long userId) {
		this.userId = userId;
		return this;
	}
	public String getClientIp() {
		return clientIp;
	}
	public HttpClientSessionUserArgs setClientIp(String clientIp) {
		this.clientIp = clientIp;
		return this;
	}
	public int getProductId() {
		return productId;
	}
	public HttpClientSessionUserArgs setProductId(int productId) {
		this.productId = productId;
		return this;
	}
}
