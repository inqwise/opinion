package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.users.IUserDetails;
import com.inqwise.opinion.library.common.users.LoginProvider;

public class UserDetailsEntity implements IUserDetails {
	
	private long userId;
	private String title;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private int countryId;
	private Integer stateId;
	private String postalCode;
	private String phone1;
	private String comments;
	private String countryName;
	private String stateName;
	private String displayName;
	private String email;
	private boolean sendNewsletters;
	private String username;
	private Date insertDate;
	private Long gatewayId;
	private LoginProvider provider;
	private String userExternalId;
	private int countOfLogins;
	
	public UserDetailsEntity(ResultSet reader) throws SQLException {
		setTitle(reader.getString("title"));
		setFirstName(reader.getString("first_name"));
		setLastName(reader.getString("last_name"));
		setAddress1(reader.getString("address1"));
		setAddress2(reader.getString("address2"));
		setCity(reader.getString("city"));
		setCountryId(reader.getInt("country_id"));
		setStateId(ResultSetHelper.optInt(reader, "state_id"));
		setPostalCode(reader.getString("postal_code"));
		setPhone1(reader.getString("phone1"));
		setComments(reader.getString("comments"));
		setUserId(reader.getLong("user_id"));
		setUsername(reader.getString("user_name"));
		setDisplayName(reader.getString("display_name"));
		setEmail(reader.getString("email"));
		setSendNewsletters(reader.getBoolean("send_newsletters"));
		setInsertDate(reader.getDate("insert_date"));
		setProvider(LoginProvider.fromInt(ResultSetHelper.optInt(reader, "provider_id", LoginProvider.Inqwise.getValue())));
		setUserExternalId(ResultSetHelper.optString(reader, "user_external_id"));
		setCountOfLogins(reader.getInt("count_of_logins"));
		setGatewayId(ResultSetHelper.optLong(reader, "gateway_id"));
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress2() {
		return address2;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setReferenceId(long referenceId) {
		this.setUserId(referenceId);
	}

	public long getReferenceId() {
		return getUserId();
	}

	public JSONObject toJson() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("countryId", getCountryId())
		.put("address1", getAddress1())
		.put("address2", getAddress2())
		.put("city", getCity())
		.put("comments", getComments())
		.put("firstName", getFirstName())
		.put("lastName", getLastName())
		.put("phone1", getPhone1())
		.put("postalCode", getPostalCode())
		.put("stateId", getStateId())
		.put("title", getTitle())
		.put("displayName", getDisplayName())
		.put("email", getEmail())
		.put("userName", getUsername())
		.put("sendNewsLetters", isSendNewsLetters());
		
		return result;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setSendNewsletters(boolean sendNewsletters) {
		this.sendNewsletters = sendNewsletters;
	}

	public boolean isSendNewsLetters() {
		return sendNewsletters;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setGatewayId(Long gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Long getGatewayId() {
		return gatewayId;
	}
	
	public String getCultureCode() {
		return "en-us";
	}
	
	public LoginProvider getProvider() {
		return provider;
	}

	public void setProvider(LoginProvider provider) {
		this.provider = provider;
	}

	public String getUserExternalId() {
		return userExternalId;
	}

	public void setUserExternalId(String userExternalId) {
		this.userExternalId = userExternalId;
	}

	public int getCountOfLogins() {
		return countOfLogins;
	}

	public void setCountOfLogins(int countOfLogins) {
		this.countOfLogins = countOfLogins;
	}
}
