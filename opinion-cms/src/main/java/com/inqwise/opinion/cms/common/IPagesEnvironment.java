package com.inqwise.opinion.cms.common;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IPagesEnvironment {
	public URL getAbsoluteURL();
	public URL getAbsoluteSecureURL();
	
	public URL getApplicationURL();
	public URL getApplicationSecureURL();
	public URL getServerURL();
	
	public ILanguage getLanguage();
	public HttpServletRequest getRequest();
	public HttpServletResponse getResponse();
	public IPage getPage(int pageId);
	public abstract IPortal getPortal();
}
