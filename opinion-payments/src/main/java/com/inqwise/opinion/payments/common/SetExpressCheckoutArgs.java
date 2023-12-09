package com.inqwise.opinion.payments.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.inqwise.opinion.payments.dao.interfaces.ISetExpressCheckoutArgs;

public class SetExpressCheckoutArgs<TProcessor> implements ISetExpressCheckoutArgs, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5599363936366505251L;
	private Long accountId;
	private Long backofficeUserId;
	private String cancelUrl;
	private String clientIp;
	private String amountCurrency;
	private String geoCountryCode;
	private String returnUrl;
	private String sessionId;
	private int sourceId;
	private Date requestDate;
	private Long userId;
	private String details;
	private int processorTypeId;
	private String token;
	private double amount;
	private String expressCheckoutUrl;
	List<Long> chargeIds;
	
	public SetExpressCheckoutArgs() {
	}
	
	public SetExpressCheckoutArgs(ISetExpressCheckoutRequest request) {
		accountId = request.getAccountId();
		backofficeUserId = request.getBackofficeUserId();
		cancelUrl = request.getCancelUrl();
		clientIp = request.getClientIp();
		amountCurrency = request.getCurrencyCode();
		geoCountryCode = request.getGeoCountryCode();
		returnUrl = request.getReturnUrl();
		sessionId = request.getSessionId();
		sourceId = request.getSourceId();
		requestDate = request.getTimeStamp();
		userId = request.getUserId();
		details = request.getDetails();
		expressCheckoutUrl = request.getExpressCheckoutBaseUrl();
		chargeIds = request.getChargeIds();
	}
	
	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public long getAccountId() {
		return accountId;
	}

	@Override
	public int getProcessorTypeId() {
		return processorTypeId;
	}

	@Override
	public Date getRequestDate() {
		return requestDate;
	}

	@Override
	public double getAmount() {
		return amount;
	}

	@Override
	public String getAmountCurrency() {
		return amountCurrency;
	}

	@Override
	public String getGeoCountryCode() {
		return geoCountryCode;
	}

	@Override
	public String getClientIp() {
		return clientIp;
	}

	@Override
	public int getSourceId() {
		return sourceId;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String getDetails() {
		return details;
	}

	@Override
	public Long getBackofficeUserId() {
		return backofficeUserId;
	}

	@Override
	public Long getParentId() {
		return null;
	}

	@Override
	public String getToken() {
		return token;
	}
	
	@Override
	public String getCancelUrl() {
		return cancelUrl;
	}

	@Override
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	@Override
	public String getReturnUrl() {
		return returnUrl;
	}

	@Override
	public List<Long> getChargeIds(){
		return chargeIds;
	}
	
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public void setBackofficeUserId(Long backofficeUserId) {
		this.backofficeUserId = backofficeUserId;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public void setGeoCountryCode(String geoCountryCode) {
		this.geoCountryCode = geoCountryCode;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setProcessorTypeId(int processorTypeId) {
		this.processorTypeId = processorTypeId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String getExpressCheckoutUrl() {
		return expressCheckoutUrl;
	}
}