package com.inqwise.opinion.library.common.invites;

import java.util.Date;

import com.inqwise.opinion.library.common.emails.IInviteEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;

public interface IInvite {
	final class JsonNames {

		public static final String INVITE_ID = "inviteId";
		public static final String INVITE_NAME = "name";
		public static final String INVITE_EMAIL = "email";
		public static final String INVITE_DATE = "inviteDate";
		public static final String EXTERNAL_ID = "externalId";
		
	}
	
	final class ResultSetNames {
		public static final String INVITE_ID = "invite_id";
		public static final String INVITE_NAME = "invite_name";
		public static final String INVITE_EMAIL = "invite_email";
		public static final String INVITE_DATE = "invite_date";
		public static final String EXTERNAL_ID = "invite_external_id";
		public static final String ACCOUNT_ID = "invite_account_id";
		public static final String MODIFY_DATE = "modify_date";
		
	}

	BaseOperationResult send(IInviteEmailData data);

	public abstract String getExternalId();

	public abstract long getAccountId();

	public abstract Date getModifyDate();

	public abstract Date getInviteDate();

	public abstract String getEmail();

	public abstract String getName();

	public abstract long getId();
}
