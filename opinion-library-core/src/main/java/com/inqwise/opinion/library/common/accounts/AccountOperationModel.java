package com.inqwise.opinion.library.common.accounts;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;
import com.inqwise.opinion.library.common.InvoiceItemModel;
import com.inqwise.opinion.payments.common.CreditCardTypes;

public class AccountOperationModel implements InvoiceItemModel {
	
	private static final String KeysCHARGE_DESCRIPTION = null;
	private Long id;
	private AccountsOperationsType type;
	private Long userId;
	private Double amount;
	private Double balance;
	private Date modifyDate;
	private Long referenceId;
	private String comments;
	private String creditCardNumber;
	private CreditCardTypes creditCardType;
	private String chargeDescription;

	private AccountOperationModel(Builder builder) {
		this.id = builder.id;
		this.type = builder.type;
		this.userId = builder.userId;
		this.amount = builder.amount;
		this.balance = builder.balance;
		this.modifyDate = builder.modifyDate;
		this.referenceId = builder.referenceId;
		this.comments = builder.comments;
		this.creditCardNumber = builder.creditCardNumber;
		this.creditCardType = builder.creditCardType;
		this.chargeDescription = builder.chargeDescription;
	}

	public static final class Keys{
		public static final String ID = "id";
		public static final String TYPE_ID = "type_id";
		public static final String USER_ID = "user_id";
		public static final String AMOUNT = "amount";
		public static final String BALANCE = "balance";
		public static final String MODIFY_DATE = "modify_date";
		public static final String REFERENCE_ID = "reference_id";
		public static final String COMMENTS = "comments";
		public static final String CREDIT_CARD_NUMBER = "credit_card_number";
		public static final String CREDIT_CARD_TYPE_ID = "credit_card_type_id";
		public static final String CHARGE_DESCRIPTION = "charge_description";
	}

	public AccountOperationModel(JSONObject json) {
		id = json.optLongObject(Keys.ID);
		
		var typeId = json.optIntegerObject(Keys.TYPE_ID);
		if(null != typeId) {
			type = AccountsOperationsType.fromInt(typeId);
		}
		userId = json.optLongObject(Keys.USER_ID);
		amount = json.optDoubleObject(Keys.AMOUNT);
		balance = json.optDoubleObject(Keys.BALANCE);
		var modifyDateInMs = json.optLongObject(Keys.MODIFY_DATE);
		if(null != modifyDateInMs) {
			modifyDate = new Date(modifyDateInMs);
		}
		referenceId = json.optLongObject(Keys.REFERENCE_ID);
		comments = json.optString(Keys.COMMENTS);
		creditCardNumber = json.optString(Keys.CREDIT_CARD_NUMBER);
		var creditCardTypeId = json.optIntegerObject(Keys.CREDIT_CARD_TYPE_ID);
		if(null != creditCardTypeId) {
			creditCardType = CreditCardTypes.fromInt(creditCardTypeId);
		}
		chargeDescription = json.optString(Keys.CHARGE_DESCRIPTION);
	}

	public Long getId() {
		return id;
	}
	
	public AccountsOperationsType getType() {
		return type;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public Long getReferenceId() {
		return referenceId;
	}
	
	public String getComments() {
		return comments;
	}
	
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	
	public CreditCardTypes getCreditCardType() {
		return creditCardType;
	}
	
	public String getChargeDescription() {
		return chargeDescription;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.ID, id);
		if(null != type) {
			json.put(Keys.TYPE_ID, type.getValue());
		}
		json.put(Keys.USER_ID, userId);
		json.put(Keys.AMOUNT, amount);
		json.put(Keys.BALANCE, balance);
		
		if (null != modifyDate) {
			json.put(Keys.MODIFY_DATE, modifyDate.getTime());
		}
		json.put(Keys.REFERENCE_ID, referenceId);
		json.put(Keys.COMMENTS, comments);
		json.put(Keys.CREDIT_CARD_NUMBER, creditCardNumber);
		if(null != creditCardType) {
			json.put(Keys.CREDIT_CARD_TYPE_ID, creditCardType.getValue());
		}
		json.put(Keys.CHARGE_DESCRIPTION, chargeDescription);
		return json;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(AccountOperationModel accountOperationModel) {
		return new Builder(accountOperationModel);
	}

	public static final class Builder {
		private Long id;
		private AccountsOperationsType type;
		private Long userId;
		private Double amount;
		private Double balance;
		private Date modifyDate;
		private Long referenceId;
		private String comments;
		private String creditCardNumber;
		private CreditCardTypes creditCardType;
		private String chargeDescription;

		private Builder() {
		}

		private Builder(AccountOperationModel accountOperationModel) {
			this.id = accountOperationModel.id;
			this.type = accountOperationModel.type;
			this.userId = accountOperationModel.userId;
			this.amount = accountOperationModel.amount;
			this.balance = accountOperationModel.balance;
			this.modifyDate = accountOperationModel.modifyDate;
			this.referenceId = accountOperationModel.referenceId;
			this.comments = accountOperationModel.comments;
			this.creditCardNumber = accountOperationModel.creditCardNumber;
			this.creditCardType = accountOperationModel.creditCardType;
			this.chargeDescription = accountOperationModel.chargeDescription;
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withType(AccountsOperationsType type) {
			this.type = type;
			return this;
		}

		public Builder withUserId(Long userId) {
			this.userId = userId;
			return this;
		}

		public Builder withAmount(Double amount) {
			this.amount = amount;
			return this;
		}

		public Builder withBalance(Double balance) {
			this.balance = balance;
			return this;
		}

		public Builder withModifyDate(Date modifyDate) {
			this.modifyDate = modifyDate;
			return this;
		}

		public Builder withReferenceId(Long referenceId) {
			this.referenceId = referenceId;
			return this;
		}

		public Builder withComments(String comments) {
			this.comments = comments;
			return this;
		}

		public Builder withCreditCardNumber(String creditCardNumber) {
			this.creditCardNumber = creditCardNumber;
			return this;
		}

		public Builder withCreditCardType(CreditCardTypes creditCardType) {
			this.creditCardType = creditCardType;
			return this;
		}

		public Builder withChargeDescription(String chargeDescription) {
			this.chargeDescription = chargeDescription;
			return this;
		}

		public AccountOperationModel build() {
			return new AccountOperationModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("KeysCHARGE_DESCRIPTION", KeysCHARGE_DESCRIPTION).add("id", id)
				.add("type", type).add("userId", userId).add("amount", amount).add("balance", balance)
				.add("modifyDate", modifyDate).add("referenceId", referenceId).add("comments", comments)
				.add("creditCardNumber", creditCardNumber).add("creditCardType", creditCardType)
				.add("chargeDescription", chargeDescription).toString();
	}

}
