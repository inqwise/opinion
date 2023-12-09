package com.inqwise.opinion.library.common.accounts;

public interface IAccountBusinessDetailsChangeRequest {
	
	public abstract long getId();
	public abstract String getPhone1();

	public abstract String getPostalCode();

	public abstract Integer getStateId();

	public abstract int getCountryId();
	
	public abstract String getCity();

	public abstract String getAddress2();

	public abstract String getAddress1();

	public abstract String getLastName();

	public abstract String getFirstName();
	
	public abstract String getCompanyName();
}
