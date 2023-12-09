package com.inqwise.opinion.payments.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.payments.common.CreditCardTypes;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PayProcessorTypes;
import com.inqwise.opinion.payments.common.PaymentStatus;
import com.inqwise.opinion.payments.common.transactions.IPayTransaction;

public class TransactionEntity implements IPayTransaction {

	private long id;
	private Long parentId;
	private PayActionTypes transactionType;
	private CreditCardTypes creditCardType;
	private Date insertDate;
	private Long userId;
	private long accountId;
	private PayProcessorTypes processorType;
	private Integer creditCardNumber;
	private Date requestDate;
	private double requestAmount;
	private double amount;
	private String amountCurrencySymbol;
	private Date processorTransactionDate;
	private PaymentStatus status;
	private String processorTransactionId;
	private String details;
	
		
	public TransactionEntity(ResultSet reader) throws SQLException {
		setId(reader.getLong("id"));
		setParentId(ResultSetHelper.optLong(reader, "parent_id"));
		setTransactionType(PayActionTypes.fromInt(reader.getInt("pay_transaction_type_id")));
		setCreditCardType(CreditCardTypes.fromInt(ResultSetHelper.optInt(reader, "credit_card_type_id")));
		setInsertDate(ResultSetHelper.optDate(reader, "insert_date"));
		setUserId(ResultSetHelper.optLong(reader, "user_id"));
		setAccountId(reader.getLong("account_id"));
		setProcessorType(PayProcessorTypes.fromInt(reader.getInt("processor_type_id")));
		setCreditCardNumber(ResultSetHelper.optInt(reader, "credit_card_number"));
		setRequestDate(ResultSetHelper.optDate(reader, "request_date"));
		setRequestAmount(reader.getDouble("requested_amount"));
		setAmount(reader.getDouble("amount"));
		setAmountCurrencySymbol(ResultSetHelper.optString(reader, "amount_currency"));
		setProcessorTransactionDate(ResultSetHelper.optDate(reader, "processor_transaction_date"));
		setStatus(PaymentStatus.fromInt(reader.getInt("transaction_status_id")));
		setProcessorTransactionId(ResultSetHelper.optString(reader, "processor_transaction_id"));
		setDetails(ResultSetHelper.optString(reader, "details"));
	}


	@Override
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	@Override
	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	@Override
	public PayActionTypes getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(PayActionTypes transactionType) {
		this.transactionType = transactionType;
	}


	@Override
	public CreditCardTypes getCreditCardType() {
		return creditCardType;
	}


	public void setCreditCardType(CreditCardTypes creditCardType) {
		this.creditCardType = creditCardType;
	}


	@Override
	public Date getInsertDate() {
		return insertDate;
	}


	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}


	@Override
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	@Override
	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	@Override
	public PayProcessorTypes getProcessorType() {
		return processorType;
	}


	public void setProcessorType(PayProcessorTypes processorType) {
		this.processorType = processorType;
	}


	@Override
	public Integer getCreditCardNumber() {
		return creditCardNumber;
	}


	public void setCreditCardNumber(Integer creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}


	@Override
	public Date getRequestDate() {
		return requestDate;
	}


	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}


	@Override
	public double getRequestAmount() {
		return requestAmount;
	}


	public void setRequestAmount(double requestAmount) {
		this.requestAmount = requestAmount;
	}


	@Override
	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	@Override
	public String getAmountCurrencySymbol() {
		return amountCurrencySymbol;
	}


	public void setAmountCurrencySymbol(String amountCurrencySymbol) {
		this.amountCurrencySymbol = amountCurrencySymbol;
	}


	@Override
	public Date getProcessorTransactionDate() {
		return processorTransactionDate;
	}


	public void setProcessorTransactionDate(Date processorTransactionDate) {
		this.processorTransactionDate = processorTransactionDate;
	}


	@Override
	public PaymentStatus getStatus() {
		return status;
	}


	public void setStatus(PaymentStatus status) {
		this.status = status;
	}


	@Override
	public String getProcessorTransactionId() {
		return processorTransactionId;
	}


	public void setProcessorTransactionId(String processorTransactionId) {
		this.processorTransactionId = processorTransactionId;
	}


	@Override
	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}

}
