package com.inqwise.opinion.library.common.accounts;

import org.json.JSONException;
import org.json.JSONObject;

public interface IAccountBusinessDetails {

	public abstract String getStateName();

	public abstract String getCountryName();

	public abstract Integer getCountryId();

	public abstract String getPhone1();

	public abstract String getPostalCode();

	public abstract Integer getStateId();

	public abstract String getCity();

	public abstract String getAddress2();

	public abstract String getAddress1();

	public abstract String getLastName();

	public abstract String getFirstName();

	public abstract String getCompanyName();

	public abstract long getId();

	public abstract JSONObject toJson() throws JSONException;

	public abstract boolean hasBusinessAddress();

}
