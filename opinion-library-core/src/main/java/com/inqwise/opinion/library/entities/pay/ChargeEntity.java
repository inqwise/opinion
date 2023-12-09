package com.inqwise.opinion.library.entities.pay;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.pay.BillType;
import com.inqwise.opinion.library.common.pay.ChargeReferenceType;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICharge;

public class ChargeEntity implements ICharge {

	private long id;
	private String name;
	private String description;
	private Date insertDate;
	private Date modifyDate;
	private Long billId;
	private BillType billTypeId;
	private Long serviceId;
	private Long referenceId;
	private ChargeReferenceType referenceType;
	private BigDecimal amount;
	private BigDecimal balance;
	private Date expiryDate;
	private ChargeStatus status;
	private BigDecimal amountToFund;
	private long accountId;
	
	public ChargeEntity(ResultSet reader) throws SQLException {
		setId(reader.getLong("charge_id"));
		setName(reader.getString("charge_name"));
		setDescription(ResultSetHelper.optString(reader, "charge_description"));
		setInsertDate(ResultSetHelper.optDate(reader, "insert_date"));
		setModifyDate(ResultSetHelper.optDate(reader, "modify_date"));
		setBillId(ResultSetHelper.optLong(reader, "bill_id"));
		setBillTypeId(BillType.fromInt(ResultSetHelper.optInt(reader, "bill_type_id")));
		setReferenceId(ResultSetHelper.optLong(reader, "reference_id"));
		setReferenceType(ChargeReferenceType.fromInt(ResultSetHelper.optInt(reader, "reference_type_id")));
		setAmount(ResultSetHelper.getDecimal(reader, "amount"));
		setBalance(ResultSetHelper.getDecimal(reader, "balance"));
		setExpiryDate(ResultSetHelper.optDate(reader, "expiry_date"));
		setStatus(ChargeStatus.fromInt(ResultSetHelper.optInt(reader, "charge_status_id")));
		setAccountId(reader.getLong("for_account_id"));
		
		if(status == ChargeStatus.Unpaid && balance.compareTo(amount) < 0){
			setAmountToFund(amount.subtract(balance).max(BigDecimal.ZERO));
		} else {
			setAmountToFund(BigDecimal.ZERO);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public BillType getBillTypeId() {
		return billTypeId;
	}

	public void setBillTypeId(BillType billTypeId) {
		this.billTypeId = billTypeId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public double getAmount() {
		return amount.doubleValue();
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance.doubleValue();
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public ChargeStatus getStatus() {
		return status;
	}

	public void setStatus(ChargeStatus status) {
		this.status = status;
	}

	public double getAmountToFund() {
		return amountToFund.doubleValue();
	}

	public void setAmountToFund(BigDecimal amountToFund) {
		this.amountToFund = amountToFund;
	}

	public ChargeReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ChargeReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
}
