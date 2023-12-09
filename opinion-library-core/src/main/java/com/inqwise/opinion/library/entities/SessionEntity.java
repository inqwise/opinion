package com.inqwise.opinion.library.entities;

import java.util.UUID;

import com.inqwise.opinion.library.common.ISession;
import com.inqwise.opinion.library.common.users.IUser;

public class SessionEntity implements ISession{
	
	private IUser user;
	public IUser getUser() {
		return user;
	}
	
	private UUID sessionId;
	public UUID getSessionId() {
		return sessionId;
	}

	private String clientIp;
	public String getClientIp() {
		return this.clientIp;
	}
	
	private boolean persist;
	public boolean isPersist() {
		return persist;
	}
	
	public static SessionEntity Create(IUser user, String clientIp, boolean persist)
	{
		return new SessionEntity(generateSessionId(), user, clientIp, persist);
	}
	
	public SessionEntity(UUID sessionId, IUser user, String clientIp, boolean persist)
	{
		this.user = user;
		this.clientIp = clientIp;
		this.sessionId = sessionId;
		this.persist = persist;
	}
	
	public static UUID generateSessionId()
	{
		return UUID.randomUUID();
	}
}
