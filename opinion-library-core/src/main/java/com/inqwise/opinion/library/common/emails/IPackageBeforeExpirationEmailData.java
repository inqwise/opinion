package com.inqwise.opinion.library.common.emails;

import java.util.Date;

import com.inqwise.opinion.library.common.servicePackages.IServicePackage;

public interface IPackageBeforeExpirationEmailData extends ISingleEmailSignature, IEmailData {
	IServicePackage getServicePackage();
	Date getExpirationDate();
	String getBaseUrl();
	long getUserId();
	Integer getTimezoneOffset();
	String getCultureCode();
}
