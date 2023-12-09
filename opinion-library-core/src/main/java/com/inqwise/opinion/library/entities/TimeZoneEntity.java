package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.library.common.countries.ITimeZone;

public class TimeZoneEntity implements ITimeZone {

	private int id;
	private float gmt;
	private String name; 
	public TimeZoneEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("timezone_id"));
		setGmt(reader.getFloat("GMT"));
		setName(reader.getString("timezone_name"));
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public void setGmt(float gmt) {
		this.gmt = gmt;
	}
	 
	public float getGmt() {
		return gmt;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
