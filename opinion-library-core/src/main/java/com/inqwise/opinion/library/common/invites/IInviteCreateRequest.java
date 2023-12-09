package com.inqwise.opinion.library.common.invites;

public interface IInviteCreateRequest {

	long getAccountId();
	String getExternalId();
	String getEmail();
	String getInviteName();
	Integer getMaxUsers();
	int getAccountProductId();
}
