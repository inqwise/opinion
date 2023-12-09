package com.inqwise.opinion.library.common.accounts;

import java.util.Date;

public interface IAccountDetails {

	public abstract Integer getTimezoneId();

	public abstract String getComments();

	public abstract float getBalance();

	public abstract boolean isActive();

	public abstract int getServicePackageId();

	public abstract String getName();

	public abstract int getProductId();

	public abstract Long getOwnerId();

	public abstract long getId();
	
	public abstract Date getInsertDate();

	public abstract String getOwnerDisplayName();

	public abstract String getOwnerUserName();
	
	public abstract Date getServicePackageExpiryDate();
	
	public abstract Integer getMaxUsers();
}
