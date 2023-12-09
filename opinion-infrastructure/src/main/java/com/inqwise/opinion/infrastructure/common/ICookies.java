package com.inqwise.opinion.infrastructure.common;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

public interface ICookies extends AutoCloseable {
	
	@Override
	public void close();
	
	public String optString(String name);
	public void put(String name, String value, int maxAge) throws UnsupportedEncodingException;
	public void expiry(String name);
	public ICookie firstOrDefault(String name);

	void put(String name, String value, int maxAge, String domain)
			throws UnsupportedEncodingException;
}
