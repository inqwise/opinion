package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class UserOperationModel {

	private Long usopId;
	private Integer usopTypeId;
	private String usopTypeValue;
	private String userName;
	private Long userId;
	private String clientIp;
	private Integer productId;
	private Date insertDate;
	private String countryName;

	private UserOperationModel(Builder builder) {
		this.usopId = builder.usopId;
		this.usopTypeId = builder.usopTypeId;
		this.usopTypeValue = builder.usopTypeValue;
		this.userName = builder.userName;
		this.userId = builder.userId;
		this.clientIp = builder.clientIp;
		this.productId = builder.productId;
		this.insertDate = builder.insertDate;
		this.countryName = builder.countryName;
	}
	
	public static final class Keys{

		public static final String USOP_ID = "usop_id";
		public static final String USOP_TYPE_ID = "usop_type_id";
		public static final String USOP_TYPE_VALUE = "usop_type_value";
		public static final String USER_NAME = "user_name";
		public static final String USER_ID = "user_id";
		public static final String CLIENT_IP = "client_ip";
		public static final String PRODUCT_ID = "product_id";
		public static final String INSERT_DATE = "insert_date";
		public static final String COUNTRY_NAME = "country_name";
	}
	
	public UserOperationModel(JSONObject json) {
		usopId = json.optLongObject(Keys.USOP_ID);
		usopTypeId = json.optIntegerObject(Keys.USOP_TYPE_ID);
		usopTypeValue = json.optString(Keys.USOP_TYPE_VALUE);
		userName = json.optString(Keys.USER_NAME);
		userId = json.optLongObject(Keys.USER_ID);
		clientIp = json.optString(Keys.CLIENT_IP);
		productId = json.optIntegerObject(Keys.PRODUCT_ID);
		insertDate = (Date) json.opt(Keys.INSERT_DATE);
		countryName = json.optString(Keys.COUNTRY_NAME);
	}
	
	public Long getUsopId() {
		return usopId;
	}
	
	public Integer getUsopTypeId() {
		return usopTypeId;
	}
	
	public String getUsopTypeValue() {
		return usopTypeValue;
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
		json.put(Keys.USOP_ID, usopId);
		json.put(Keys.USOP_TYPE_ID, usopTypeId);
		json.put(Keys.USOP_TYPE_VALUE, usopTypeValue);
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
		private Long usopId;
		private Integer usopTypeId;
		private String usopTypeValue;
		private String userName;
		private Long userId;
		private String clientIp;
		private Integer productId;
		private Date insertDate;
		private String countryName;

		private Builder() {
		}

		private Builder(UserOperationModel userOperationModel) {
			this.usopId = userOperationModel.usopId;
			this.usopTypeId = userOperationModel.usopTypeId;
			this.usopTypeValue = userOperationModel.usopTypeValue;
			this.userName = userOperationModel.userName;
			this.userId = userOperationModel.userId;
			this.clientIp = userOperationModel.clientIp;
			this.productId = userOperationModel.productId;
			this.insertDate = userOperationModel.insertDate;
			this.countryName = userOperationModel.countryName;
		}

		public Builder withUsopId(Long usopId) {
			this.usopId = usopId;
			return this;
		}

		public Builder withUsopTypeId(Integer usopTypeId) {
			this.usopTypeId = usopTypeId;
			return this;
		}

		public Builder withUsopTypeValue(String usopTypeValue) {
			this.usopTypeValue = usopTypeValue;
			return this;
		}

		public Builder withUserName(String userName) {
			this.userName = userName;
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

		public UserOperationModel build() {
			return new UserOperationModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("usopId", usopId).add("usopTypeId", usopTypeId)
				.add("usopTypeValue", usopTypeValue).add("userName", userName).add("userId", userId)
				.add("clientIp", clientIp).add("productId", productId).add("insertDate", insertDate)
				.add("countryName", countryName).toString();
	}

}
