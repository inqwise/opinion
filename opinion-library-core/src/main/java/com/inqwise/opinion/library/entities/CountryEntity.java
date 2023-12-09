package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.library.common.countries.ICountry;

public class CountryEntity extends BaseEntity implements ICountry{

	private int id;
	private String iso2;
	private String name;
	
	public CountryEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("country_id"));
		setIso2(reader.getString("iso2"));
		setName(reader.getString("country_name"));
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getIso2() {
		return iso2;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
