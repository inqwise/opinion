package com.inqwise.opinion.library.common.invites;

import java.util.Date;

public interface IInviteSettingsChangeRequest {

	long getInviteId();
	Long getAccountId();
	String getEmail();
	String getInviteName();
	Date getInviteDate();

}
