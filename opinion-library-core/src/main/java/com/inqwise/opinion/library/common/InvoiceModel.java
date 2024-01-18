package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class InvoiceModel {
	
	private Long invoiceId;
	private Date modifyDate;
	private Integer invoiceStatusId;
	private Date invoiceDate;
	private Date insertDate;
	private Date invoiceFromDate;
	private Date invoiceToDate;
	private Double amount;
	private Long accountId;
	private String accountName;
	private Long accopId;
	private Integer accopTypeId;
	private Double balance;
	private Long referenceId;
	private String comments;
	private String creditCardNumber;
	private Integer creditCardTypeId;
	private String chargeDescription;

	private InvoiceModel(Builder builder) {
		this.invoiceId = builder.invoiceId;
		this.modifyDate = builder.modifyDate;
		this.invoiceStatusId = builder.invoiceStatusId;
		this.invoiceDate = builder.invoiceDate;
		this.insertDate = builder.insertDate;
		this.invoiceFromDate = builder.invoiceFromDate;
		this.invoiceToDate = builder.invoiceToDate;
		this.amount = builder.amount;
		this.accountId = builder.accountId;
		this.accountName = builder.accountName;
		this.accopId = builder.accopId;
		this.accopTypeId = builder.accopTypeId;
		this.balance = builder.balance;
		this.referenceId = builder.referenceId;
		this.comments = builder.comments;
		this.creditCardNumber = builder.creditCardNumber;
		this.creditCardTypeId = builder.creditCardTypeId;
		this.chargeDescription = builder.chargeDescription;
	}

	public static final class Keys{

		public static final String INVOICE_ID = "invoice_id";
		public static final String MODIFY_DATE = "modify_date";
		public static final String INVOICE_STATUS_ID = "invoice_status_id";
		public static final String INVOICE_DATE = "invoice_date";
		public static final String INSERT_DATE = "insert_date";
		public static final String INVOICE_FROM_DATE = "invoice_from_date";
		public static final String INVOICE_TO_DATE = "invoice_to_date";
		public static final String AMOUNT = "amount";
		public static final String ACCOUNT_ID = "account_id";
		public static final String ACCOUNT_NAME = "account_name";
		public static final String ACCOP_ID = "accop_id";
		public static final String ACCOP_TYPE_ID = "accop_type_id";
		public static final String BALANCE = "balance";
		public static final String REFERENCE_ID = "reference_id";
		public static final String COMMENTS = "comments";
		public static final String CREDIT_CARD_NUMBER = "credit_card_number";
		public static final String CREDIT_CARD_TYPE_ID = "credit_card_type_id";
		public static final String CHARGE_DESCRIPTION = "charge_description";
	}
	
	public InvoiceModel(JSONObject json) {
		invoiceId = json.optLongObject(Keys.INVOICE_ID);
		modifyDate = (Date) json.opt(Keys.MODIFY_DATE);
		invoiceStatusId = json.optIntegerObject(Keys.INVOICE_STATUS_ID);
		invoiceDate = (Date) json.opt(Keys.INVOICE_DATE);
		insertDate = (Date) json.opt(Keys.INSERT_DATE);
		invoiceFromDate = (Date) json.opt(Keys.INVOICE_FROM_DATE);
		invoiceToDate = (Date) json.opt(Keys.INVOICE_TO_DATE);
		amount = json.optDoubleObject(Keys.AMOUNT);
		accountId = json.optLongObject(Keys.ACCOUNT_ID);
		accountName = json.optString(Keys.ACCOUNT_NAME);
		accopId = json.optLongObject(Keys.ACCOP_ID);
		accopTypeId = json.optIntegerObject(Keys.ACCOP_TYPE_ID);
		balance = json.optDoubleObject(Keys.BALANCE);
		referenceId = json.optLongObject(Keys.REFERENCE_ID);
		comments = json.optString(Keys.COMMENTS);
		creditCardNumber = json.optString(Keys.CREDIT_CARD_NUMBER);
		creditCardTypeId = json.optIntegerObject(Keys.CREDIT_CARD_TYPE_ID);
		chargeDescription = json.optString(Keys.CHARGE_DESCRIPTION);
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public Integer getInvoiceStatusId() {
		return invoiceStatusId;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public Date getInsertDate() {
		return insertDate;
	}
	
	public Date getInvoiceFromDate() {
		return invoiceFromDate;
	}
	
	public Date getInvoiceToDate() {
		return invoiceToDate;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public Long getAccopId() {
		return accopId;
	}

	public Integer getAcoopTypeId() {
		return accopTypeId;
	}

	public Double getBalance() {
		return balance;
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

	public String getChargeDescription() {
		return chargeDescription;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.INVOICE_ID, invoiceId);
		json.put(Keys.MODIFY_DATE, modifyDate);
		json.put(Keys.INVOICE_STATUS_ID, invoiceStatusId);
		json.put(Keys.INVOICE_DATE, invoiceDate);
		json.put(Keys.INSERT_DATE, insertDate);
		json.put(Keys.INVOICE_FROM_DATE, invoiceFromDate);
		json.put(Keys.INVOICE_TO_DATE, invoiceToDate);	
		json.put(Keys.AMOUNT, amount);
		json.put(Keys.ACCOUNT_ID, accountId);
		json.put(Keys.ACCOUNT_NAME, accountName);
		json.put(Keys.ACCOP_ID, accopId);
		json.put(Keys.ACCOP_TYPE_ID, accopTypeId);
		json.put(Keys.BALANCE, balance);
		json.put(Keys.REFERENCE_ID, referenceId);
		json.put(Keys.COMMENTS, comments);
		json.put(Keys.CREDIT_CARD_NUMBER, creditCardNumber);
		json.put(Keys.CREDIT_CARD_TYPE_ID, creditCardTypeId);
		json.put(Keys.CHARGE_DESCRIPTION, chargeDescription);
		return json;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(InvoiceModel invoiceModel) {
		return new Builder(invoiceModel);
	}

	public static final class Builder {
		private Long invoiceId;
		private Date modifyDate;
		private Integer invoiceStatusId;
		private Date invoiceDate;
		private Date insertDate;
		private Date invoiceFromDate;
		private Date invoiceToDate;
		private Double amount;
		private Long accountId;
		private String accountName;
		private Long accopId;
		private Integer accopTypeId;
		private Double balance;
		private Long referenceId;
		private String comments;
		private String creditCardNumber;
		private Integer creditCardTypeId;
		private String chargeDescription;

		private Builder() {
		}

		private Builder(InvoiceModel invoiceModel) {
			this.invoiceId = invoiceModel.invoiceId;
			this.modifyDate = invoiceModel.modifyDate;
			this.invoiceStatusId = invoiceModel.invoiceStatusId;
			this.invoiceDate = invoiceModel.invoiceDate;
			this.insertDate = invoiceModel.insertDate;
			this.invoiceFromDate = invoiceModel.invoiceFromDate;
			this.invoiceToDate = invoiceModel.invoiceToDate;
			this.amount = invoiceModel.amount;
			this.accountId = invoiceModel.accountId;
			this.accountName = invoiceModel.accountName;
			this.accopId = invoiceModel.accopId;
			this.accopTypeId = invoiceModel.accopTypeId;
			this.balance = invoiceModel.balance;
			this.referenceId = invoiceModel.referenceId;
			this.comments = invoiceModel.comments;
			this.creditCardNumber = invoiceModel.creditCardNumber;
			this.creditCardTypeId = invoiceModel.creditCardTypeId;
			this.chargeDescription = invoiceModel.chargeDescription;
		}

		public Builder withInvoiceId(Long invoiceId) {
			this.invoiceId = invoiceId;
			return this;
		}

		public Builder withModifyDate(Date modifyDate) {
			this.modifyDate = modifyDate;
			return this;
		}

		public Builder withInvoiceStatusId(Integer invoiceStatusId) {
			this.invoiceStatusId = invoiceStatusId;
			return this;
		}

		public Builder withInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
			return this;
		}

		public Builder withInsertDate(Date insertDate) {
			this.insertDate = insertDate;
			return this;
		}

		public Builder withInvoiceFromDate(Date invoiceFromDate) {
			this.invoiceFromDate = invoiceFromDate;
			return this;
		}

		public Builder withInvoiceToDate(Date invoiceToDate) {
			this.invoiceToDate = invoiceToDate;
			return this;
		}

		public Builder withAmount(Double amount) {
			this.amount = amount;
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

		public Builder withChargeDescription(String chargeDescription) {
			this.chargeDescription = chargeDescription;
			return this;
		}

		public InvoiceModel build() {
			return new InvoiceModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("invoiceId", invoiceId).add("modifyDate", modifyDate)
				.add("invoiceStatusId", invoiceStatusId).add("invoiceDate", invoiceDate).add("insertDate", insertDate)
				.add("invoiceFromDate", invoiceFromDate).add("invoiceToDate", invoiceToDate).add("amount", amount)
				.add("accountId", accountId).add("accountName", accountName).add("accopId", accopId)
				.add("accopTypeId", accopTypeId).add("balance", balance).add("referenceId", referenceId)
				.add("comments", comments).add("creditCardNumber", creditCardNumber)
				.add("creditCardTypeId", creditCardTypeId).add("chargeDescription", chargeDescription).toString();
	}

}
