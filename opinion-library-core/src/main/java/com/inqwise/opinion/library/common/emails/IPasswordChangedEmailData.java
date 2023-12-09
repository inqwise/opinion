package com.inqwise.opinion.library.common.emails;

public interface IPasswordChangedEmailData extends ISingleEmailSignature, IEmailData {
	String getPassword();
}
