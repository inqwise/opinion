package com.inqwise.opinion.automation.common.events;

import java.util.UUID;

import com.inqwise.opinion.automation.common.FireEventArgs;

public class RegistrationEventArgs extends FireEventArgs {

	private long userId;
	private String userName;
	private String countryName;
	private int productId;
	private String email;
	
	public RegistrationEventArgs(int sourceId, long userId, String userName, String countryName, int productId, String email) {
		super(sourceId);
		this.userId = userId;
		this.userName = userName;
		this.countryName = countryName;
		this.productId = productId;
		this.email = email;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserName(){
		return userName;
	}
	
	public String getCountryName(){
		return countryName;
	}
	
	public int getProductId(){
		return productId;
	}
	
	public String getEmail() {
		return email;
	}
}
