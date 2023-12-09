package com.inqwise.opinion.api.context;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.COOKIE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.DefaultCookie;
import org.json.CookieList;
import org.json.JSONObject;
import org.restexpress.Request;
import org.restexpress.Response;

import com.inqwise.opinion.infrastructure.common.ICookie;
import com.inqwise.opinion.infrastructure.common.ICookies;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

public class Cookies implements ICookies {
	
	private Set<Cookie> cookies; 
	private Request request;
	private Response response;
	private String path = "/";
	
	private static final CookieDecoder decoder = new CookieDecoder();
	//private CookieEncoder encoder = new CookieEncoder(true);
	
	private String encode(String key, String value){
		CookieEncoder encoder = new CookieEncoder(true);
		encoder.addCookie(key, value);
		return encoder.encode();
	}
	
	private String encode(Cookie cookie){
		CookieEncoder encoder = new CookieEncoder(true);
		encoder.addCookie(cookie);
		return encoder.encode();
	}
	
	private Set<Cookie> decode(String cookieString){
		Set<Cookie> cookies = new HashSet<Cookie>(decoder.decode(cookieString));
		for (Cookie cookie: cookies) {
			cookie.setDiscard(true);
		}
		
		return cookies;
	}
	
	public Cookies(Request request, Response response) {
		this.request = request;
		this.response = response;
		initialize();
	}
	
	private void initialize(){
		String cookieString = request.getHeader(COOKIE);
		if (cookieString != null) {
			cookies = decode(cookieString);
		}
		
		if(null == cookies) cookies = new HashSet<Cookie>();
	}
	
	@Override
	public void close(){
		if (!cookies.isEmpty()) {
			for (Cookie cookie: cookies) {
				if(!cookie.isDiscard()){
					response.addHeader(SET_COOKIE, encode(cookie));
				}
			}
		}
		
		cookies.clear();
		cookies = null;
	}

	@Override
	public String optString(String key) {
		Cookie cookie = firstOrDefaultInternal(key);
		if(null != cookie) return cookie.getValue();
		
		return null;
	}
	
	@Override
	public void put(String name, String value, int maxAge) throws UnsupportedEncodingException {
		Cookie cookie = new DefaultCookie(name, URLEncoder.encode(value, StandardCharsets.UTF_8.displayName()));
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		
		cookies.add(cookie);
	}

	@Override
	public void put(String name, String value, int maxAge, String domain) throws UnsupportedEncodingException {
		Cookie cookie = new DefaultCookie(name, URLEncoder.encode(value, StandardCharsets.UTF_8.displayName()));
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		
		cookies.add(cookie);
	}
	
	@Override
	public void expiry(String name) {
		for (Cookie cookie : all(name)) {
			cookie.setMaxAge(0);
			cookie.setValue("");
			//cookie.setDiscard(true);
		}
	}

	@Override
	public ICookie firstOrDefault(String name) {
		Cookie cookie = firstOrDefaultInternal(name);
		if(null != cookie) return new CookieWrapper(cookie);
		return null;
	}
	
	private Cookie firstOrDefaultInternal(String name){
		Iterator<Cookie> iterator = cookies.iterator();
	    while(iterator.hasNext()) {
	        Cookie cookie = iterator.next();
	        if(null != name && cookie.getName().equalsIgnoreCase(name) && !cookie.getValue().equalsIgnoreCase("")){
				return cookie;
			}
	    }
		
		return null;
	}
	
	private List<Cookie> all(String name){
		List<Cookie> list = new ArrayList<>();
		Iterator<Cookie> iterator = cookies.iterator();
	    while(iterator.hasNext()) {
	        Cookie cookie = iterator.next();
			if(null != name && cookie.getName().equalsIgnoreCase(name) && !cookie.getValue().equalsIgnoreCase("")){
				list.add(cookie);
			}
		}
		
		return list;
	}
	
	public ICookies withPath(String path){
		this.path = path;
		return this;
	}
}
