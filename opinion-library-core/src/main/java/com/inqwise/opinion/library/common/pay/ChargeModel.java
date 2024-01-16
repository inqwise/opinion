package com.inqwise.opinion.library.common.pay;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class ChargeModel {
	
	private Long id;
	private ChargeStatus status;
	private Double amount;
	private String name;
	private String description;
	private Date createDate;
	private Long accountId;
	private String accountName;

	private ChargeModel(Builder builder) {
		this.id = builder.id;
		this.status = builder.status;
		this.amount = builder.amount;
		this.name = builder.name;
		this.description = builder.description;
		this.createDate = builder.createDate;
		this.accountId = builder.accountId;
		this.accountName = builder.accountName;
	}

	public static final class Keys {
		public static final String ID = "id";
		public static final String STATUS_ID = "status_id";
		public static final String AMOUNT = "amount";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String CREATE_DATE = "create_date";
		public static final String ACCOUNT_ID = "account_id";
		public static final String ACCOUNT_NAME = "account_name";
	}  
	
	public ChargeModel(JSONObject json) {
		id = json.optLong(Keys.ID);
		var statusId = json.optIntegerObject(Keys.STATUS_ID);
		if(null != statusId) {
			status = ChargeStatus.fromInt(statusId);
		}
		amount = json.optDoubleObject(Keys.AMOUNT);
		name = json.optString(Keys.NAME);
		description = json.optString(Keys.DESCRIPTION);
		createDate = new Date(json.getLong(Keys.CREATE_DATE));
		accountId = json.optLongObject(Keys.ACCOUNT_ID);
		accountName = json.optString(Keys.ACCOUNT_NAME);
	}
	
	public Long getId() {
		return id;
	}
	
	public ChargeStatus getStatus() {
		return status;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreateDate() {
		return createDate;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.ID, id);
		if(null != status) {
			json.put(Keys.STATUS_ID, status.getValue());
		}
		
		json.put(Keys.AMOUNT, amount);
		json.put(Keys.ACCOUNT_ID, accountId);
		json.put(Keys.ACCOUNT_NAME, accountName);
		return json;
	}


	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(ChargeModel chargeModel) {
		return new Builder(chargeModel);
	}

	public static final class Builder {
		private Long id;
		private ChargeStatus status;
		private Double amount;
		private String name;
		private String description;
		private Date createDate;
		private Long accountId;
		private String accountName;

		private Builder() {
		}

		private Builder(ChargeModel chargeModel) {
			this.id = chargeModel.id;
			this.status = chargeModel.status;
			this.amount = chargeModel.amount;
			this.name = chargeModel.name;
			this.description = chargeModel.description;
			this.createDate = chargeModel.createDate;
			this.accountId = chargeModel.accountId;
			this.accountName = chargeModel.accountName;
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withStatus(ChargeStatus status) {
			this.status = status;
			return this;
		}

		public Builder withAmount(Double amount) {
			this.amount = amount;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withCreateDate(Date createDate) {
			this.createDate = createDate;
			return this;
		}

		public Builder withAccountId(Long accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder withAccountName(String accountName) {
			this.accountName = accountName;
			return this;
		}

		public ChargeModel build() {
			return new ChargeModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("status", status).add("amount", amount)
				.add("name", name).add("description", description).add("createDate", createDate)
				.add("accountId", accountId).add("accountName", accountName).toString();
	}

}
