package com.inqwise.opinion.library.common.emails;

import com.inqwise.opinion.library.common.servicePackages.IServicePackage;

public interface IPackageExpiredEmailData extends ISingleEmailSignature, IEmailData {
	IServicePackage getExpiredServicePackage();
	String getBaseUrl();
	long getUserId();
	String getCultureCode();
}
