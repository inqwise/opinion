package com.inqwise.opinion.api.context;

import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.DefaultCookie;

import com.inqwise.opinion.infrastructure.common.ICookie;

public class CookieWrapper implements ICookie {
	private Cookie cookie;
	
	public CookieWrapper(Cookie cookie) {
		this.cookie = cookie;
	}

	@Override
	public String getDomain() {
		return cookie.getDomain();
	}

	@Override
	public String getName() {
		return cookie.getName();
	}

	@Override
	public String getPath() {
		return cookie.getPath();
	}

	@Override
	public String getValue() {
		return cookie.getValue();
	}

	@Override
	public boolean isSecure() {
		return cookie.isSecure();
	}

	@Override
	public Integer getMaxAge() {
		return cookie.getMaxAge();
	}

	@Override
	public void setValue(String value) {
		cookie.setValue(value);
		if(cookie.isDiscard()){
			cookie.setDiscard(false);
		}
	}
	
	@Override
	public String toString() {
		return cookie.toString();
	}
}
