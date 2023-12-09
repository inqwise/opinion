package com.inqwise.opinion.library.common.servicePackages;

import java.util.Date;

public interface IServicePackage {

	final class JsonNames {
		public static final String COUNT_OF_MONTHS = "countOfMonth";
	}
	
	int getId();
	String getName();
	int getProductId();
	public abstract String getDescription();
	public abstract Date getInsertDate();
	double getAmount();
	boolean isDefault();
	Integer getDefaultUsagePeriod();
	Date calculateExpiryDate();
	Date calculateExpiryDate(int countOf);
	Date calculateExpiryDate(Date fromDate, int countOf);
	Integer getMaxAccountUsers();
	
	
}
