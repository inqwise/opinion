package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class UserOperationModel {

	private Long id;
	private UserOperationType type;
	private Long userId;
	private String clientIp;
	private Integer productId;
	private Date insertDate;
	private String countryName;
	private String userName;

	private UserOperationModel(Builder builder) {
		this.id = builder.id;
		this.type = builder.type;
		this.userId = builder.userId;
		this.clientIp = builder.clientIp;
		this.productId = builder.productId;
		this.insertDate = builder.insertDate;
		this.countryName = builder.countryName;
		this.userName = builder.userName;
	}

	public static final class Keys{

		public static final String ID = "usop_id";
		public static final String USER_NAME = "user_name";
		public static final String USER_ID = "user_id";
		public static final String CLIENT_IP = "client_ip";
		public static final String PRODUCT_ID = "product_id";
		public static final String INSERT_DATE = "insert_date";
		public static final String COUNTRY_NAME = "country_name";
		public static final String TYPE = "type";
	}
	
	public UserOperationModel(JSONObject json) {
		id = json.optLongObject(Keys.ID);
		type = new UserOperationType(json.getJSONObject(Keys.TYPE));
		userName = json.optString(Keys.USER_NAME);
		userId = json.optLongObject(Keys.USER_ID);
		clientIp = json.optString(Keys.CLIENT_IP);
		productId = json.optIntegerObject(Keys.PRODUCT_ID);
		
		var insertDateInMs = json.optLongObject(Keys.INSERT_DATE);
		if(null != insertDateInMs) {
			insertDate = new Date(insertDateInMs);
		}
		
		countryName = json.optString(Keys.COUNTRY_NAME);
	}
	
	public Long getId() {
		return id;
	}
	
	public UserOperationType getType() {
		return type;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public String getClientIp() {
		return clientIp;
	}
	
	public Integer getProductId() {
		return productId;
	}
	
	public Date getInsertDate() {
		return insertDate;
	}
	
	public String getCountryName() {
		return countryName;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.ID, id);
		json.put(Keys.USER_NAME, userName);
		json.put(Keys.USER_ID, userId);
		json.put(Keys.CLIENT_IP, clientIp);
		json.put(Keys.PRODUCT_ID, productId);
		json.put(Keys.INSERT_DATE, insertDate);
		json.put(Keys.COUNTRY_NAME, countryName);
		return json;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(UserOperationModel userOperationModel) {
		return new Builder(userOperationModel);
	}

	public static final class Builder {
		private Long id;
		private UserOperationType type;
		private Long userId;
		private String clientIp;
		private Integer productId;
		private Date insertDate;
		private String countryName;
		private String userName;

		private Builder() {
		}

		private Builder(UserOperationModel userOperationModel) {
			this.id = userOperationModel.id;
			this.type = userOperationModel.type;
			this.userId = userOperationModel.userId;
			this.clientIp = userOperationModel.clientIp;
			this.productId = userOperationModel.productId;
			this.insertDate = userOperationModel.insertDate;
			this.countryName = userOperationModel.countryName;
			this.userName = userOperationModel.userName;
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withType(UserOperationType type) {
			this.type = type;
			return this;
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

		public Builder withInsertDate(Date insertDate) {
			this.insertDate = insertDate;
			return this;
		}

		public Builder withCountryName(String countryName) {
			this.countryName = countryName;
			return this;
		}

		public Builder withUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public UserOperationModel build() {
			return new UserOperationModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("type", type).add("userId", userId)
				.add("clientIp", clientIp).add("productId", productId).add("insertDate", insertDate)
				.add("countryName", countryName).add("userName", userName).toString();
	}
	
}
