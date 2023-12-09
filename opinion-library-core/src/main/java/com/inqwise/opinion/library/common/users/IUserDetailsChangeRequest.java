package com.inqwise.opinion.library.common.users;

public interface IUserDetailsChangeRequest {

	public abstract long getUserId();
	public abstract String getComments();
	public abstract String getPhone1();
	public abstract String getPostalCode();
	public abstract Integer getStateId();
	public abstract int getCountryId();
	public abstract String getCity();
	public abstract String getAddress2();
	public abstract String getAddress1();
	public abstract String getLastName();
	public abstract String getFirstName();
	public abstract String getTitle();
	public abstract String getDisplayName();
	public abstract String getEmail();
	public abstract boolean isSendNewsLetters();
}
