package com.inqwise.opinion.library.common;

import java.util.Collection;
import java.util.UUID;

import com.inqwise.opinion.library.common.servicePackages.IServicePackage;

public interface IProduct {

	public abstract String getNoreplyEmail();

	public abstract String getSupportEmail();

	public abstract String getFeedbackShortCaption();

	public abstract String getFeedbackCaption();

	public abstract UUID getGuid();

	public abstract String getDescription();

	public abstract String getName();

	public abstract int getId();

	public abstract String getAdminEmail();

	public abstract String getSalesEmail();

	public abstract String getContactUsEmail();

	public abstract IServicePackage getServicePackage(
			int servicePackageId);
	
	public abstract Collection<IServicePackage> getServicePackages();

	public abstract IServicePackage getServicePackageOrNull(
			int servicePackageId);

	public abstract IServicePackage getDefaultServicePackage();
}
