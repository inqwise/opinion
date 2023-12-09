package com.inqwise.opinion.library.entities.pay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.pay.IInvoice;
import com.inqwise.opinion.library.common.pay.InvoiceStatus;

public class InvoiceEntity implements IInvoice {

	private long id;
	private Date insertDate;
	private Long forAccountId;
	private String notes;
	private Date modifyDate;
	private InvoiceStatus status;
	private Date invoiceDate;
	private Date invoiceFromDate;
	private Date invoiceToDate;
	private BigDecimal balance;
	private BigDecimal amount;
	private String companyName;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private Integer stateId;
	private String postalCode;
	private String phone1;
	private Integer countryId;
	private String countryName;
	private String stateName;
	private Double totalCredit;
	private Double totalDebit;
	
	
	public InvoiceEntity(ResultSet reader) throws SQLException {
		setId(reader.getLong("invoice_id"));
		setInsertDate(ResultSetHelper.optDate(reader, "insert_date"));
		setForAccountId(ResultSetHelper.optLong(reader, "for_account_id"));
		setNotes(ResultSetHelper.optString(reader, "notes"));
		setModifyDate(ResultSetHelper.optDate(reader, "modify_date"));
		setStatus(InvoiceStatus.fromInt(reader.getInt("invoice_status_id")));
		setBalance(ResultSetHelper.getDecimal(reader, "balance"));
		setInvoiceDate(ResultSetHelper.optDate(reader, "invoice_date"));
		setAmount(ResultSetHelper.getDecimal(reader, "amount"));
		setInvoiceFromDate(ResultSetHelper.optDate(reader, "invoice_from_date"));
		setInvoiceToDate(ResultSetHelper.optDate(reader, "invoice_to_date"));
		setCompanyName(ResultSetHelper.optString(reader, "company_name"));
		setFirstName(ResultSetHelper.optString(reader, "first_name"));
		setLastName(ResultSetHelper.optString(reader, "last_name"));
		setAddress1(ResultSetHelper.optString(reader, "address1"));
		setAddress2(ResultSetHelper.optString(reader, "address2"));
		setCity(ResultSetHelper.optString(reader, "city"));
		setStateId(ResultSetHelper.optInt(reader, "state_id"));
		setPostalCode(ResultSetHelper.optString(reader, "postal_code"));
		setPhone1(ResultSetHelper.optString(reader, "phone1"));
		setCountryId(ResultSetHelper.optInt(reader, "country_id"));
		setCountryName(ResultSetHelper.optString(reader, "country_name"));
		setStateName(ResultSetHelper.optString(reader, "state_name"));
		setTotalCredit(ResultSetHelper.optDouble(reader, "total_credit"));
		setTotalDebit(ResultSetHelper.optDouble(reader, "total_debit"));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Long getForAccountId() {
		return forAccountId;
	}

	public void setForAccountId(Long forAccountId) {
		this.forAccountId = forAccountId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public Date getInvoiceFromDate() {
		return invoiceFromDate;
	}


	public void setInvoiceFromDate(Date invoiceFromDate) {
		this.invoiceFromDate = invoiceFromDate;
	}


	public Date getInvoiceToDate() {
		return invoiceToDate;
	}


	public void setInvoiceToDate(Date invoiceToDate) {
		this.invoiceToDate = invoiceToDate;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public Integer getStateId() {
		return stateId;
	}


	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public String getPhone1() {
		return phone1;
	}


	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}


	public Integer getCountryId() {
		return countryId;
	}


	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Double getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(Double totalCredit) {
		this.totalCredit = totalCredit;
	}

	public Double getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(Double totalDebit) {
		this.totalDebit = totalDebit;
	}

}

