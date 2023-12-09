package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.library.common.countries.IStateProvince;

public class StateProviceEntity implements IStateProvince {

	private int id;
	private int countryId;
	private String name;
	private String abbreviation;
	
	public StateProviceEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("id"));
		setCountryId(reader.getInt("country_id"));
		setName(reader.getString("name"));
		setAbbreviation(reader.getString("abbreviation"));
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

}
