package com.inqwise.opinion.library.common.users;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public interface IUserDetails {

	public long getUserId();
	
	public abstract String getComments();

	public abstract String getPhone1();

	public abstract String getPostalCode();

	public abstract Integer getStateId();
	
	public abstract String getStateName();

	public abstract int getCountryId();
	
	public abstract String getCountryName();

	public abstract String getCity();

	public abstract String getAddress2();

	public abstract String getAddress1();

	public abstract String getLastName();

	public abstract String getFirstName();

	public abstract String getTitle();

	public JSONObject toJson() throws JSONException;

	public abstract Long getGatewayId();

	public abstract Date getInsertDate();

	public abstract String getUsername();

	public abstract boolean isSendNewsLetters();

	public abstract String getEmail();

	public abstract String getDisplayName();

	public String getCultureCode();

	public LoginProvider getProvider();

	public String getUserExternalId();
	
	public int getCountOfLogins();
}
