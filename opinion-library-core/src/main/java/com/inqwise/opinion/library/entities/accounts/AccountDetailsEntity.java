package com.inqwise.opinion.library.entities.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccountDetails;

public class AccountDetailsEntity implements IAccountDetails {

	private long id;
	private Long ownerId;
	private int productId;
	private String name;
	private int servicePackageId;
	private boolean isActive;
	private float balance;
	private String comments;
	private Integer timezoneId;
	private Date insertDate;
	private String servicePackageName;
	private String ownerUserName;
	private String ownerDisplayName;
	private Date servicePackageExpiryDate;
	private Integer maxUsers;
	
	public AccountDetailsEntity(ResultSet reader) throws SQLException {
		setId(reader.getLong("account_id"));
		setOwnerId(ResultSetHelper.optLong(reader, "owner_id"));
		setProductId(reader.getInt("product_id"));
		setName(ResultSetHelper.optString(reader, "account_name"));
		setServicePackageId(reader.getInt("service_package_id"));
		setActive(ResultSetHelper.optBool(reader, "is_active", true));
		setInsertDate(ResultSetHelper.optDate(reader,"insert_date"));
		setBalance(reader.getFloat("balance"));
		setComments(ResultSetHelper.optString(reader, "comments"));
		setTimezoneId(ResultSetHelper.optInt(reader, "timezone_id"));
		setOwnerUserName(reader.getString("owner_user_name"));
		setOwnerDisplayName(ResultSetHelper.optString(reader, "owner_user_name"));
		setServicePackageName(ResultSetHelper.optString(reader, "sp_name"));
		setServicePackageExpiryDate(ResultSetHelper.optDate(reader, "service_package_expiry_date"));
		setMaxUsers(ResultSetHelper.optInt(reader, "max_users"));
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public long getId() {
		return id;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	
	public Long getOwnerId() {
		return ownerId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	
	public int getProductId() {
		return productId;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

	public void setServicePackageId(int servicePackageId) {
		this.servicePackageId = servicePackageId;
	}

	
	public int getServicePackageId() {
		return servicePackageId;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	public boolean isActive() {
		return isActive;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	
	public float getBalance() {
		return balance;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public String getComments() {
		return comments;
	}

	public void setTimezoneId(Integer timezoneId) {
		this.timezoneId = timezoneId;
	}

	
	public Integer getTimezoneId() {
		return timezoneId;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerDisplayName(String ownerDisplayName) {
		this.ownerDisplayName = ownerDisplayName;
	}

	public String getOwnerDisplayName() {
		return ownerDisplayName;
	}

	public Date getServicePackageExpiryDate() {
		return servicePackageExpiryDate;
	}

	public void setServicePackageExpiryDate(Date servicePackageExpiryDate) {
		this.servicePackageExpiryDate = servicePackageExpiryDate;
	}

	public Integer getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(Integer maxUsers) {
		this.maxUsers = maxUsers;
	}

}
