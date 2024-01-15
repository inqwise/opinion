package com.inqwise.opinion.library.common;

import java.util.Date;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class InvoicesModel {
	
	private Long invoiceId;
	private Date modifyDate;
	private Integer invoiceStatusId;
	private Date invoiceDate;
	private Date insertDate;
	private Date invoiceFromDate;
	private Date invoiceToDate;
	private Double amount;

	private InvoicesModel(Builder builder) {
		this.invoiceId = builder.invoiceId;
		this.modifyDate = builder.modifyDate;
		this.invoiceStatusId = builder.invoiceStatusId;
		this.invoiceDate = builder.invoiceDate;
		this.insertDate = builder.insertDate;
		this.invoiceFromDate = builder.invoiceFromDate;
		this.invoiceToDate = builder.invoiceToDate;
		this.amount = builder.amount;
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
	}
	
	public InvoicesModel(JSONObject json) {
		invoiceId = json.optLongObject(Keys.INVOICE_ID);
		modifyDate = (Date) json.opt(Keys.MODIFY_DATE);
		invoiceStatusId = json.optIntegerObject(Keys.INVOICE_STATUS_ID);
		invoiceDate = (Date) json.opt(Keys.INVOICE_DATE);
		insertDate = (Date) json.opt(Keys.INSERT_DATE);
		invoiceFromDate = (Date) json.opt(Keys.INVOICE_FROM_DATE);
		invoiceToDate = (Date) json.opt(Keys.INVOICE_TO_DATE);
		amount = json.optDoubleObject(Keys.AMOUNT);
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
		return json;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(InvoicesModel invoicesModel) {
		return new Builder(invoicesModel);
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

		private Builder() {
		}

		private Builder(InvoicesModel invoicesModel) {
			this.invoiceId = invoicesModel.invoiceId;
			this.modifyDate = invoicesModel.modifyDate;
			this.invoiceStatusId = invoicesModel.invoiceStatusId;
			this.invoiceDate = invoicesModel.invoiceDate;
			this.insertDate = invoicesModel.insertDate;
			this.invoiceFromDate = invoicesModel.invoiceFromDate;
			this.invoiceToDate = invoicesModel.invoiceToDate;
			this.amount = invoicesModel.amount;
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

		public InvoicesModel build() {
			return new InvoicesModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("invoiceId", invoiceId).add("modifyDate", modifyDate)
				.add("invoiceStatusId", invoiceStatusId).add("invoiceDate", invoiceDate).add("insertDate", insertDate)
				.add("invoiceFromDate", invoiceFromDate).add("invoiceToDate", invoiceToDate).add("amount", amount)
				.toString();
	}

}
