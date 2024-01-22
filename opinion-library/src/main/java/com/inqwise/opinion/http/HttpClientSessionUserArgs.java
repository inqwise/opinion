package com.inqwise.opinion.http;

import org.json.JSONObject;
import com.google.common.base.MoreObjects;

public class HttpClientSessionUserArgs {
	private Long userId;
	private String clientIp;
	private Integer productId;
	private HttpClientSessionUserArgs(Builder builder) {
		this.userId = builder.userId;
		this.clientIp = builder.clientIp;
		this.productId = builder.productId;
	}
	static final class Keys {
		public static final String USER_ID = "user_id";
		public static final String CLIENT_ID = "client_id";
		public static final String PRODUCT_ID = "product_id";
	}
	
	public HttpClientSessionUserArgs(JSONObject json) {
		userId = json.optLongObject(Keys.USER_ID);
		clientIp = json.optString(Keys.CLIENT_ID);
		productId = json.optIntegerObject(Keys.PRODUCT_ID);
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

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("userId", userId).add("clientIp", clientIp)
				.add("productId", productId).toString();
	}
	
	public JSONObject toJson() {
		var json = new JSONObject();
		
		if(null != userId) {
			json.put(Keys.USER_ID, userId);
		}
		
		if(null != productId) {
			json.put(Keys.PRODUCT_ID, productId);
		} 
		
		if(null != clientIp) {
			json.put(Keys.CLIENT_ID, clientIp);
		}
		return json;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(HttpClientSessionUserArgs httpClientSessionUserArgs) {
		return new Builder(httpClientSessionUserArgs);
	}

	public static final class Builder {
		private Long userId;
		private String clientIp;
		private Integer productId;

		private Builder() {
		}

		private Builder(HttpClientSessionUserArgs httpClientSessionUserArgs) {
			this.userId = httpClientSessionUserArgs.userId;
			this.clientIp = httpClientSessionUserArgs.clientIp;
			this.productId = httpClientSessionUserArgs.productId;
		}

		public Builder withUserId(Long userId) {
			this.userId = userId;
			return this;
		}

		public Builder withClientIp(String clientIp) {
			this.clientIp = clientIp;
			return this;
		}

		public Builder withProductId(Integer productId) {
			this.productId = productId;
			return this;
		}

		public HttpClientSessionUserArgs build() {
			return new HttpClientSessionUserArgs(this);
		}
	}
	
}
