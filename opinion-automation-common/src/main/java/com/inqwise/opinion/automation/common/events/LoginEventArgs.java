package com.inqwise.opinion.automation.common.events;

import java.util.UUID;

import com.inqwise.opinion.automation.common.FireEventArgs;

public class LoginEventArgs extends FireEventArgs {

	/**
	 * 
	 */
	private static final long serialVersionUID = -737459300916877836L;

	private int productId;
	private String userName;
	private Long userId;
	private String countryName;
	private String email;
	private boolean autoLogin;
	private String countryCode;
	public String getCountryCode() {
		return countryCode;
	}

	public String getClientIp() {
		return clientIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	private String clientIp;
	private String sessionId;
	
	public LoginEventArgs(int sourceId, int productId, String userName, Long userId,
			String countryName, String email, String countryCode, String sessionId, String clientIp, boolean autoLogin) {
		super(sourceId);
		
		this.productId = productId;
		this.countryName = countryName;
		this.email = email;
		this.userId = userId;
		this.userName = userName;
		this.autoLogin = autoLogin;
		this.countryCode = countryCode;
		this.sessionId = sessionId;
		this.clientIp = clientIp;
	}
	
	public boolean isAutoLogin() {
		return autoLogin;
	}

	public int getProductId() {
		return productId;
	}

	public String getUserName() {
		return userName;
	}

	public Long getUserId() {
		return userId;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getEmail() {
		return email;
	}

}
