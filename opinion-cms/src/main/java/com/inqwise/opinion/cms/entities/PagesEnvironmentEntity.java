package com.inqwise.opinion.cms.entities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;

import com.inqwise.opinion.cms.common.ILanguage;
import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.IPagesEnvironment;
import com.inqwise.opinion.cms.common.IPagesManager;
import com.inqwise.opinion.cms.common.IPortal;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class PagesEnvironmentEntity implements IPagesEnvironment {
	private URL absoluteURL;
	private URL absoluteSecureURL;
	private URL applicationURL;
	private URL applicationSecureURL;
	private URL serverURL;
	
	private ILanguage language;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Hashtable<Integer, IPage> pagesByIdSet = new Hashtable<Integer, IPage>();
	private IPagesManager pagesManager;
	private IPortal portal;
	
	static int[] DEFAULT_PORTS = new int[] {80, 443};
	
	public PagesEnvironmentEntity(HttpServletRequest request, HttpServletResponse response, ILanguage language, IPortal portal, IPagesManager pagesManager) throws MalformedURLException {
		this.setLanguage(language);
		
		setAbsoluteURL(ArrayUtils.contains(DEFAULT_PORTS, request.getServerPort()) ? new URL(request.getScheme(), request.getServerName(), request.getContextPath() + "/" + language.getAdaptedCultureCode()) : new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath() + "/" + language.getAdaptedCultureCode()));
		setAbsoluteSecureURL(ArrayUtils.contains(DEFAULT_PORTS, request.getServerPort()) ? new URL("https", request.getServerName(), request.getContextPath() + "/" + language.getAdaptedCultureCode()) : new URL("https", request.getServerName(), 8443, request.getContextPath() + "/" + language.getAdaptedCultureCode()));
  		
		setApplicationURL(ArrayUtils.contains(DEFAULT_PORTS, request.getServerPort()) ? new URL(request.getScheme(), request.getServerName(), request.getContextPath()) : new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath()));
		setApplicationSecureURL(ArrayUtils.contains(DEFAULT_PORTS, request.getServerPort()) ? new URL("https", request.getServerName(), request.getContextPath()) : new URL("https", request.getServerName(), 8443, request.getContextPath()));
  		
		setServerURL(ArrayUtils.contains(DEFAULT_PORTS, request.getServerPort()) ? new URL("https", request.getServerName(), "") : new URL("https", request.getServerName(), 8443, ""));
		
  		setRequest(request);
  		setResponse(response);
  		this.pagesManager = pagesManager;
  		setPortal(portal);
	}

	public void setAbsoluteURL(URL absoluteURL) {
		this.absoluteURL = absoluteURL;
	}

	public URL getAbsoluteURL() {
		return absoluteURL;
	}
	
	public void setAbsoluteSecureURL(URL absoluteSecureURL) {
		this.absoluteSecureURL = absoluteSecureURL;
	}

	public URL getAbsoluteSecureURL() {
		return absoluteSecureURL;
	}

	public void setApplicationURL(URL applicationURL) {
		this.applicationURL = applicationURL;
	}

	public URL getApplicationURL() {
		return applicationURL;
	}
	
	public void setApplicationSecureURL(URL applicationSecureURL) {
		this.applicationSecureURL = applicationSecureURL;
	}

	public URL getApplicationSecureURL() {
		return applicationSecureURL;
	}

	public void setLanguage(ILanguage language) {
		this.language = language;
	}

	public ILanguage getLanguage() {
		return language;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void addPage(IPage page){
		pagesByIdSet.put(page.getId(), page);
	}
	
	public IPage getPage(int pageId) {
		
		IPage page = pagesByIdSet.get(pageId);
		if(null == page){
			OperationResult<IPage> pageResult = pagesManager.getPage(pageId, this);
			if(!pageResult.hasError()){
				page = pageResult.getValue();
			}
		}
		
		return page;
	}

	public void setPortal(IPortal portal) {
		this.portal = portal;
	}

	@Override
	public IPortal getPortal() {
		return portal;
	}

	public URL getServerURL() {
		return serverURL;
	}

	public void setServerURL(URL serverURL) {
		this.serverURL = serverURL;
	}
}
