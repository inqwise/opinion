package com.inqwise.opinion.library.common.emails;

public interface IResetPasswordEmailData extends ISingleEmailSignature, IEmailData {
	String getResetPasswordLink();
}
