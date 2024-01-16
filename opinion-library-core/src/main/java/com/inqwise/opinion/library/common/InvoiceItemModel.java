package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class InvoiceItemModel {
	
	private Long chargeId;
	private Double amount;
	private Integer chargeStatusId;
	private String chargeName;
	private String chargeDescription;
	private Date insertDate;
	private Long accopId;
	private Integer accopTypeId;
	private Double balance;
	private Date modifyDate;
	private Long referenceId;
	private String comments;
	private String creditCardNumber;
	private Integer creditCardTypeId;

	private InvoiceItemModel(Builder builder) {
		this.chargeId = builder.chargeId;
		this.amount = builder.amount;
		this.chargeStatusId = builder.chargeStatusId;
		this.chargeName = builder.chargeName;
		this.chargeDescription = builder.chargeDescription;
		this.insertDate = builder.insertDate;
		this.accopId = builder.accopId;
		this.accopTypeId = builder.accopTypeId;
		this.balance = builder.balance;
		this.modifyDate = builder.modifyDate;
		this.referenceId = builder.referenceId;
		this.comments = builder.comments;
		this.creditCardNumber = builder.creditCardNumber;
		this.creditCardTypeId = builder.creditCardTypeId;
	}

	public static final class Keys{

		public static final String CHARGE_ID = "charge_id";
		public static final String AMOUNT = "amount";
		public static final String CHARGE_STATUS_ID = "charge_status_id";
		public static final String CHARGE_NAME = "charge_name";
		public static final String CHARGE_DESCRIPTION = "charge_description";
		public static final String INSERT_DATE = "insert_date";
		public static final String ACCOP_ID = "accop_id";
		public static final String ACCOPP_TYPE_ID = "accop_type_id";
		public static final String BALANCE = "balance";
		public static final String MODIFY_DATE = "modify_date";
		public static final String REFERENCE_ID = "reference_id";
		public static final String COMMENTS = "comments";
		public static final String CREDIT_CARD_NUMBER = "credit_card_number";
		public static final String CREDIT_CARD_TYPE_ID = "credit_card_type_id";	
	}

	public InvoiceItemModel(JSONObject json) {
		chargeId = json.optLongObject(Keys.CHARGE_ID);
		amount = json.optDoubleObject(Keys.AMOUNT);
		chargeStatusId = json.optIntegerObject(Keys.CHARGE_STATUS_ID);
		chargeName = json.optString(Keys.CHARGE_NAME);
		chargeDescription = json.optString(Keys.CHARGE_DESCRIPTION);
		insertDate = (Date) json.opt(Keys.INSERT_DATE);
		accopId = json.optLongObject(Keys.ACCOP_ID);
		accopTypeId = json.optIntegerObject(Keys.ACCOPP_TYPE_ID);
		balance = json.optDoubleObject(Keys.BALANCE);
		modifyDate = (Date) json.opt(Keys.MODIFY_DATE);
		referenceId = json.optLongObject(Keys.REFERENCE_ID);
		comments = json.optString(Keys.COMMENTS);
		creditCardNumber = json.optString(Keys.CREDIT_CARD_NUMBER);
		creditCardTypeId = json.optIntegerObject(Keys.CREDIT_CARD_TYPE_ID);
	}
	
	public Long getChargeId() {
		return chargeId;
	}

	public Double getAmount() {
		return amount;
	}

	public Integer getChargeStatusId() {
		return chargeStatusId;
	}

	public String getChargeName() {
		return chargeName;
	}

	public String getChargeDescription() {
		return chargeDescription;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public Long getAccopId() {
		return accopId;
	}
	
	public Integer getAccopTypeId() {
		return accopTypeId;
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

	
	public Integer getCreditCardTypeId() {
		return creditCardTypeId;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.CHARGE_ID, chargeId);
		json.put(Keys.AMOUNT, amount);
		json.put(Keys.CHARGE_STATUS_ID, chargeStatusId);
		json.put(Keys.CHARGE_NAME, chargeName);
		json.put(Keys.CHARGE_DESCRIPTION, chargeDescription);
		json.put(Keys.INSERT_DATE, insertDate);
		json.put(Keys.ACCOP_ID, accopId);
		json.put(Keys.ACCOPP_TYPE_ID, accopTypeId);
		json.put(Keys.BALANCE, balance);
		json.put(Keys.MODIFY_DATE, modifyDate);
		json.put(Keys.REFERENCE_ID, referenceId);
		json.put(Keys.COMMENTS, comments);
		json.put(Keys.CREDIT_CARD_NUMBER, creditCardNumber);
		json.put(Keys.CREDIT_CARD_TYPE_ID, creditCardTypeId);
		return json;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(InvoiceItemModel invoiceItemModel) {
		return new Builder(invoiceItemModel);
	}

	public static final class Builder {
		private Long chargeId;
		private Double amount;
		private Integer chargeStatusId;
		private String chargeName;
		private String chargeDescription;
		private Date insertDate;
		private Long accopId;
		private Integer accopTypeId;
		private Double balance;
		private Date modifyDate;
		private Long referenceId;
		private String comments;
		private String creditCardNumber;
		private Integer creditCardTypeId;

		private Builder() {
		}

		private Builder(InvoiceItemModel invoiceItemModel) {
			this.chargeId = invoiceItemModel.chargeId;
			this.amount = invoiceItemModel.amount;
			this.chargeStatusId = invoiceItemModel.chargeStatusId;
			this.chargeName = invoiceItemModel.chargeName;
			this.chargeDescription = invoiceItemModel.chargeDescription;
			this.insertDate = invoiceItemModel.insertDate;
			this.accopId = invoiceItemModel.accopId;
			this.accopTypeId = invoiceItemModel.accopTypeId;
			this.balance = invoiceItemModel.balance;
			this.modifyDate = invoiceItemModel.modifyDate;
			this.referenceId = invoiceItemModel.referenceId;
			this.comments = invoiceItemModel.comments;
			this.creditCardNumber = invoiceItemModel.creditCardNumber;
			this.creditCardTypeId = invoiceItemModel.creditCardTypeId;
		}

		public Builder withChargeId(Long chargeId) {
			this.chargeId = chargeId;
			return this;
		}

		public Builder withAmount(Double amount) {
			this.amount = amount;
			return this;
		}

		public Builder withChargeStatusId(Integer chargeStatusId) {
			this.chargeStatusId = chargeStatusId;
			return this;
		}

		public Builder withChargeName(String chargeName) {
			this.chargeName = chargeName;
			return this;
		}

		public Builder withChargeDescription(String chargeDescription) {
			this.chargeDescription = chargeDescription;
			return this;
		}

		public Builder withInsertDate(Date insertDate) {
			this.insertDate = insertDate;
			return this;
		}

		public Builder withAccopId(Long accopId) {
			this.accopId = accopId;
			return this;
		}

		public Builder withAccopTypeId(Integer accopTypeId) {
			this.accopTypeId = accopTypeId;
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

		public Builder withCreditCardTypeId(Integer creditCardTypeId) {
			this.creditCardTypeId = creditCardTypeId;
			return this;
		}

		public InvoiceItemModel build() {
			return new InvoiceItemModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("chargeId", chargeId).add("amount", amount)
				.add("chargeStatusId", chargeStatusId).add("chargeName", chargeName)
				.add("chargeDescription", chargeDescription).add("insertDate", insertDate).add("accopId", accopId)
				.add("accopTypeId", accopTypeId).add("balance", balance).add("modifyDate", modifyDate)
				.add("referenceId", referenceId).add("comments", comments).add("creditCardNumber", creditCardNumber)
				.add("creditCardTypeId", creditCardTypeId).toString();
	}

}
