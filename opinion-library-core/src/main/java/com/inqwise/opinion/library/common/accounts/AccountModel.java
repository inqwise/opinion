package com.inqwise.opinion.library.common.accounts;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class AccountModel {

	private Long accountId;
	private String servicePackageName;
	private String accountName;
	private Long ownerId;
	private Date insertDate;
	private Boolean isActive;

	private AccountModel(Builder builder) {
		this.accountId = builder.accountId;
		this.servicePackageName = builder.servicePackageName;
		this.accountName = builder.accountName;
		this.ownerId = builder.ownerId;
		this.insertDate = builder.insertDate;
		this.isActive = builder.isActive;
	}

	public static final class Keys{

		public static final String ACCOUNT_ID = "account_id";
		public static final String SERVICE_PACKAGE_NAME = "service_package_name";
		public static final String ACCOUNT_NAME = "account_name";
		public static final String OWNER_ID = "owner_id";
		public static final String INSERT_DATE = "insert_date";
		public static final String IS_ACTIVE = "is_active";
		
	}

	public AccountModel(JSONObject json) {
		accountId = json.optLongObject(Keys.ACCOUNT_ID);
		servicePackageName = json.optString(Keys.SERVICE_PACKAGE_NAME);
		accountName = json.optString(Keys.ACCOUNT_NAME);
		ownerId = json.optLongObject(Keys.OWNER_ID);
		insertDate = (Date) json.opt(Keys.INSERT_DATE);
		isActive = json.optBooleanObject(Keys.IS_ACTIVE);
	}

	public Long getAccountId() {
		return accountId;
	}
	
	public String getServicePackageName() {
		return servicePackageName;
	}

	public String getAccountName() {
		return accountName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.ACCOUNT_ID, accountId);
		json.put(Keys.SERVICE_PACKAGE_NAME, servicePackageName);
		json.put(Keys.ACCOUNT_NAME, accountName);
		json.put(Keys.OWNER_ID, ownerId);
		json.put(Keys.INSERT_DATE, insertDate);
		json.put(Keys.IS_ACTIVE, isActive);
		return json;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(AccountModel accountModel) {
		return new Builder(accountModel);
	}

	public static final class Builder {
		private Long accountId;
		private String servicePackageName;
		private String accountName;
		private Long ownerId;
		private Date insertDate;
		private Boolean isActive;

		private Builder() {
		}

		private Builder(AccountModel accountModel) {
			this.accountId = accountModel.accountId;
			this.servicePackageName = accountModel.servicePackageName;
			this.accountName = accountModel.accountName;
			this.ownerId = accountModel.ownerId;
			this.insertDate = accountModel.insertDate;
			this.isActive = accountModel.isActive;
		}

		public Builder withAccountId(Long accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder withServicePackageName(String servicePackageName) {
			this.servicePackageName = servicePackageName;
			return this;
		}

		public Builder withAccountName(String accountName) {
			this.accountName = accountName;
			return this;
		}

		public Builder withOwnerId(Long ownerId) {
			this.ownerId = ownerId;
			return this;
		}

		public Builder withInsertDate(Date insertDate) {
			this.insertDate = insertDate;
			return this;
		}

		public Builder withIsActive(Boolean isActive) {
			this.isActive = isActive;
			return this;
		}

		public AccountModel build() {
			return new AccountModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("accountId", accountId)
				.add("servicePackageName", servicePackageName).add("accountName", accountName).add("ownerId", ownerId)
				.add("insertDate", insertDate).add("isActive", isActive).toString();
	}
	
}
