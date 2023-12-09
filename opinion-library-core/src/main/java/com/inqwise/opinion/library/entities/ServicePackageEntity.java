package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;

public class ServicePackageEntity implements IServicePackage {

	private int id;
	private String name;
	private int  productId;
	private Date insertDate;
	private String description;
	private double amount;
	private boolean is_default;
	private Integer defaultUsagePeriod;
	private Integer maxAccountUsers;
	
	public ServicePackageEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("sp_id"));
		setName(reader.getString("sp_name"));
		setProductId(reader.getInt("product_id"));
		setInsertDate(ResultSetHelper.optDate(reader, "insert_date"));
		setDescription(ResultSetHelper.optString(reader, "description"));
		setAmount(reader.getDouble("amount"));
		setIsDefault(reader.getBoolean("is_default"));
		setDefaultUsagePeriod(ResultSetHelper.optInt(reader, "default_usage_period"));
		setMaxAccountUsers(ResultSetHelper.optInt(reader, "max_account_users"));
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductId() {
		return productId;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isDefault() {
		return is_default;
	}

	public void setIsDefault(boolean is_default) {
		this.is_default = is_default;
	}

	public Integer getDefaultUsagePeriod() {
		return defaultUsagePeriod;
	}

	public void setDefaultUsagePeriod(Integer defaultUsagePeriod) {
		this.defaultUsagePeriod = defaultUsagePeriod;
	}

	public Date calculateExpiryDate() {
		return calculateExpiryDate(1);
	}

	public Date calculateExpiryDate(Date fromDate, int countOf) {
		Date result = null;
		if(null != getDefaultUsagePeriod()){
			Calendar calculatedDate = Calendar.getInstance();
			if(null != fromDate){
				calculatedDate.setTime(fromDate);
			}
			calculatedDate.add(Calendar.DAY_OF_YEAR, getDefaultUsagePeriod() * countOf);
			result = DateConverter.trim(calculatedDate);
		}
		
		return result;
	}

	public Integer getMaxAccountUsers() {
		return maxAccountUsers;
	}

	public void setMaxAccountUsers(Integer maxAccountUsers) {
		this.maxAccountUsers = maxAccountUsers;
	}

	public Date calculateExpiryDate(int countOf) {
		return calculateExpiryDate(null, countOf);
	}
}
