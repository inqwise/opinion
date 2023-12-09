package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.cms.common.IPortal;

public class PortalEntity implements IPortal{

	private int id;
	private String name;
	
	public PortalEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("portal_id"));
		setName(reader.getString("portal_name"));
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
