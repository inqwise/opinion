package com.inqwise.opinion.marketplace.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.marketplace.common.IService;

public class ServiceEntity implements IService{

	private long id;
	private String name;
	private String description;
	private Date modifyDate;
	
	public ServiceEntity(ResultSet reader) throws SQLException {
		id = reader.getLong("service_id");
		name = reader.getString("service_name");
		description = ResultSetHelper.optString(reader, "service_description");
		modifyDate = ResultSetHelper.optDate(reader, "modify_date");
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
