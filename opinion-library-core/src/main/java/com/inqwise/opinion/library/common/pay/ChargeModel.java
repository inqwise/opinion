package com.inqwise.opinion.library.common.pay;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;
import com.inqwise.opinion.library.common.InvoiceItemModel;

public class ChargeModel implements InvoiceItemModel {
	
	private Long id;
	private ChargeStatus status;
	private Double amount;
	private String name;
	private String description;
	private Date createDate;
	private Long accountId;
	private String accountName;
	private String postPayAction;
	private Long referenceId;
	private String postPayActionData;
	private ChargeReferenceType reference;

	private ChargeModel(Builder builder) {
		this.id = builder.id;
		this.status = builder.status;
		this.amount = builder.amount;
		this.name = builder.name;
		this.description = builder.description;
		this.createDate = builder.createDate;
		this.accountId = builder.accountId;
		this.accountName = builder.accountName;
		this.postPayAction = builder.postPayAction;
		this.referenceId = builder.referenceId;
		this.postPayActionData = builder.postPayActionData;
		this.reference = builder.reference;
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
		public static final String POST_PAY_ACTION = "post_pay_action";
		public static final String REFERENCE_TYPE = "reference_type";
		public static final String REFERENCE_ID = "reference_id";
		public static final String POST_PAY_ACTION_DATA = "post_pay_action_data";
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
		postPayAction = json.optString(Keys.POST_PAY_ACTION);
		
		var referenceType = json.optIntegerObject(Keys.REFERENCE_TYPE);
		if(null != referenceType) {
			reference = ChargeReferenceType.fromInt(referenceType);
		}
		referenceId = json.optLongObject(Keys.REFERENCE_ID);
		postPayActionData = json.optString(Keys.POST_PAY_ACTION_DATA);
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

	public String getPostPayAction() {
		return postPayAction;
	}

	public ChargeReferenceType getReferenceType() {
		return reference;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public String getPostPayActionData() {
		return postPayActionData;
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
		json.put(Keys.POST_PAY_ACTION, postPayAction);
		
		if(null != reference) {
			json.put(Keys.REFERENCE_TYPE, reference.getValue());
		}
		
		json.put(Keys.REFERENCE_ID, referenceId);
		json.put(Keys.POST_PAY_ACTION_DATA, postPayActionData);
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
		private String postPayAction;
		private Long referenceId;
		private String postPayActionData;
		private ChargeReferenceType reference;

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
			this.postPayAction = chargeModel.postPayAction;
			this.referenceId = chargeModel.referenceId;
			this.postPayActionData = chargeModel.postPayActionData;
			this.reference = chargeModel.reference;
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

		public Builder withPostPayAction(String postPayAction) {
			this.postPayAction = postPayAction;
			return this;
		}

		public Builder withReferenceId(Long referenceId) {
			this.referenceId = referenceId;
			return this;
		}

		public Builder withPostPayActionData(String postPayActionData) {
			this.postPayActionData = postPayActionData;
			return this;
		}

		public Builder withReference(ChargeReferenceType reference) {
			this.reference = reference;
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
				.add("accountId", accountId).add("accountName", accountName).add("postPayAction", postPayAction)
				.add("referenceId", referenceId).add("postPayActionData", postPayActionData).add("reference", reference)
				.toString();
	}

}
