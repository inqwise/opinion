package com.inqwise.opinion.api.context;

import com.inqwise.opinion.infrastructure.common.ICookie;

import io.netty.handler.codec.http.cookie.Cookie;

public class CookieWrapper implements ICookie {
	private Cookie cookie;
	
	public CookieWrapper(Cookie cookie) {
		this.cookie = cookie;
	}

	@Override
	public String getDomain() {
		return cookie.domain();
	}

	@Override
	public String getName() {
		return cookie.name();
	}

	@Override
	public String getPath() {
		return cookie.path();
	}

	@Override
	public String getValue() {
		return cookie.value();
	}

	@Override
	public boolean isSecure() {
		return cookie.isSecure();
	}

	@Override
	public long getMaxAge() {
		return cookie.maxAge();
	}

	@Override
	public void setValue(String value) {
		cookie.setValue(value);
//		if(cookie.isDiscard()){
//			cookie.setDiscard(false);
//		}
	}
	
	@Override
	public String toString() {
		return cookie.toString();
	}
}
