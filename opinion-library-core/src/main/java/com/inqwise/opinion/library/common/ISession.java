package com.inqwise.opinion.library.common;

import java.util.UUID;

import com.inqwise.opinion.library.common.users.IUser;

public interface ISession {
	public UUID getSessionId();
	public String getClientIp();
	public IUser getUser();
	public boolean isPersist();
}
