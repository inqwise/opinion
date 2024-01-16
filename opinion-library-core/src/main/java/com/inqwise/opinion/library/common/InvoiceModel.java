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

		public InvoiceModel build() {
			return new InvoiceModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("invoiceId", invoiceId).add("modifyDate", modifyDate)
				.add("invoiceStatusId", invoiceStatusId).add("invoiceDate", invoiceDate).add("insertDate", insertDate)
				.add("invoiceFromDate", invoiceFromDate).add("invoiceToDate", invoiceToDate).add("amount", amount)
				.add("accountId", accountId).add("accountName", accountName).toString();
	}

}
