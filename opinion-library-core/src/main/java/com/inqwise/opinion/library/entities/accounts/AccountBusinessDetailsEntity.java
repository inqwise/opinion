package com.inqwise.opinion.library.entities.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccountBusinessDetails;

public class AccountBusinessDetailsEntity implements IAccountBusinessDetails {

	private long id;
	private String companyName;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private Integer stateId;
	private String postalCode;
	private String phone1;
	private Integer countryId;
	private String countryName;
	private String stateName;
	
	public AccountBusinessDetailsEntity(ResultSet reader) throws SQLException {
		setId(reader.getLong("account_id"));
		setCompanyName(ResultSetHelper.optString(reader, "business_company_name"));
		setFirstName(ResultSetHelper.optString(reader, "business_first_name"));
		setLastName(ResultSetHelper.optString(reader, "business_last_name"));
		setAddress1(ResultSetHelper.optString(reader, "business_address1"));
		setAddress2(ResultSetHelper.optString(reader, "business_address2"));
		setCity(ResultSetHelper.optString(reader, "business_city"));
		setStateId(ResultSetHelper.optInt(reader, "business_state_id"));
		setPostalCode(ResultSetHelper.optString(reader, "business_postal_code"));
		setPhone1(ResultSetHelper.optString(reader, "business_phone1"));
		setCountryId(ResultSetHelper.optInt(reader, "business_country_id"));
		setCountryName(ResultSetHelper.optString(reader, "country_name"));
		setStateName(ResultSetHelper.optString(reader, "state_name"));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public JSONObject toJson() throws JSONException {
		return new JSONObject()
		.put("companyName", JSONHelper.getNullable(getCompanyName()))
		.put("firstName", JSONHelper.getNullable(getFirstName()))
		.put("lastName", JSONHelper.getNullable(getLastName()))
		.put("address1", JSONHelper.getNullable(getAddress1()))
		.put("address2", JSONHelper.getNullable(getAddress2()))
		.put("city", JSONHelper.getNullable(getCity()))
		.put("stateId", JSONHelper.getNullable(getStateId()))
		.put("postalCode", JSONHelper.getNullable(getPostalCode()))
		.put("phone1", JSONHelper.getNullable(getPhone1()))
		.put("countryId", JSONHelper.getNullable(getCountryId()))
		.put("countryName", JSONHelper.getNullable(getCompanyName()))
		.put("stateName", JSONHelper.getNullable(getStateName()));
	}

	public boolean hasBusinessAddress() {
		return null != countryId;
	}

}
