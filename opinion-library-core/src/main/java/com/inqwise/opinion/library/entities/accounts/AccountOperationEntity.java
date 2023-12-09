package com.inqwise.opinion.library.entities.accounts;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.AccountOperationsReferenceType;
import com.inqwise.opinion.library.common.accounts.AccountsOperationsType;
import com.inqwise.opinion.library.common.accounts.IAccountOperation;

public class AccountOperationEntity implements IAccountOperation {
	
	private long id;
	private AccountsOperationsType operationType;
	private long userId;
	private long accountId;
	private int productId;
	private BigDecimal amount;
	private BigDecimal balance;
	private String clientIp;
	private Date modifyDate;
	private String comments;
	private Long referenceId;
	private AccountOperationsReferenceType referenceType;
	private Long backofficeUserId;
	private int sourceId;
	
	public AccountOperationEntity(ResultSet reader) throws SQLException {
		id = reader.getLong("accop_id");
		operationType = AccountsOperationsType.fromInt(reader.getInt("accop_type_id"));
		userId = ResultSetHelper.optInt(reader, "user_id");
		accountId = reader.getLong("account_id");
		productId = reader.getInt("product_id");
		amount = ResultSetHelper.optBigDecimal(reader, "amount");
		clientIp = ResultSetHelper.optString(reader, "client_ip");
		modifyDate = ResultSetHelper.optDate(reader, "modify_date");
		comments = ResultSetHelper.optString(reader, "comments");
		referenceId = ResultSetHelper.optLong(reader, "reference_id");
		referenceType = AccountOperationsReferenceType.fromInt(ResultSetHelper.optInt(reader, "reference_type_id"));
		backofficeUserId = ResultSetHelper.optLong(reader, "backoffice_user_id");
		balance = ResultSetHelper.optBigDecimal(reader, "balance");
		sourceId = reader.getInt("source_id");
	}
	
	public long getId() {
		return id;
	}

	public AccountsOperationsType getOperationType() {
		return operationType;
	}

	public long getUserId() {
		return userId;
	}

	public long getAccountId() {
		return accountId;
	}

	public int getProductId() {
		return productId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getClientIp() {
		return clientIp;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public String getComments() {
		return comments;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public AccountOperationsReferenceType getReferenceType() {
		return referenceType;
	}

	public Long getBackofficeUserId() {
		return backofficeUserId;
	}

	public int getSourceId() {
		return sourceId;
	}

}
