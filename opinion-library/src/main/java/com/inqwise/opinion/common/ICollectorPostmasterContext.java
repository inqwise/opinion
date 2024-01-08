package com.inqwise.opinion.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.http.HttpClientSession;

public interface ICollectorPostmasterContext{

	String getClientIp();
	HttpClientSession getSession() throws IOException;
	URL getAbsoluteURL() throws MalformedURLException;
	URL getApplicationURL() throws MalformedURLException;
	//HttpServletRequest getRequest();
	
	public abstract IHttpAnswererSession getAnswererSession(
			String guid);

	public abstract void removeAnswererSession(String guid);
	
	public abstract String getRefererUrl();

}