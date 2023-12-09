package com.inqwise.opinion.library.common.accounts;

import java.util.Date;

public interface IAccountView {
	String getOwnerName();
	String getServicePackageName();
	public abstract String getName();

	public abstract long getId();

	public abstract int getServicePackageId();

	public abstract int getProductId();

	public abstract Long getOwnerId();

	public abstract Date getInsertDate();

	public abstract boolean isActive();
	
	public abstract Date addDateOffset(Date date);
	
	public abstract Date removeDateOffset(Date date);

	public abstract Integer getTimezoneOffset();
}
