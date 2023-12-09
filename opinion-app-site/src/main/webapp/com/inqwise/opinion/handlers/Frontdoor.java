package com.inqwise.opinion.handlers;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

/**
 * Servlet implementation class Frontdoor
 */
public class Frontdoor extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static ApplicationLog logger = ApplicationLog.getLogger(Frontdoor.class);
	static int[] DEFAULT_PORTS = new int[] {80, 443};
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Frontdoor() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		URL applicationUrl = ArrayUtils.contains(DEFAULT_PORTS, request.getServerPort()) ? new URL(request.getScheme(), request.getServerName(), request.getContextPath()) : new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath());
		String referer = StringUtils.trimToNull(request.getHeader("referer"));
		String returnUrl = StringUtils.trimToNull(request.getParameter("input-return-url"));
		
		String redirectUrl = null;
		
		if(null == referer || !(null == ApplicationConfiguration.Opinion.getSessionDomain() || StringUtils.contains(referer, ApplicationConfiguration.Opinion.getSessionDomain()))){
			redirectUrl = (null == returnUrl ? applicationUrl.toString() : returnUrl);
			logger.warn("Login: Received post request from unexpected referer '%s'. Redirected to '%s'", referer, redirectUrl);
		}
		
		if(null == redirectUrl){
			//Object originalUrlObj = request.getAttribute("javax.servlet.forward.request_uri");
			//if(null == originalUrlObj || !originalUrlObj.toString().endsWith("/logout")){
				redirectUrl = Login(request, response, applicationUrl, returnUrl);
			//} else {
			//	redirectUrl = Logout(request, response, applicationUrl, redirectUrl);
			//}
		}
		
		response.sendRedirect(redirectUrl);
	}

	private String Logout(HttpServletRequest request, HttpServletResponse response, URL applicationURL, String redirectUrl) throws IOException{
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (int i = 0; i < cookies.length; i++) {
		    	
		    	response.setContentType("text/html");
		        Cookie cookie = cookies[i];
		        if(cookie.getMaxAge() != 0){
			        Cookie cookieToRemove = new Cookie(cookie.getName(), "");
			        cookieToRemove.setMaxAge(0);
			        cookieToRemove.setPath("/");
			        if(null != ApplicationConfiguration.Opinion.getSessionDomain()){
			        	cookieToRemove.setDomain(ApplicationConfiguration.Opinion.getSessionDomain());
					}
			        cookieToRemove.setComment("EXPIRING COOKIE at " + System.currentTimeMillis());
		        
			        response.addCookie(cookieToRemove);
		        }
		    }
		}
		
		return (null == redirectUrl ? applicationURL.toString() : redirectUrl);
	}
	
	private String Login(HttpServletRequest request, HttpServletResponse response, URL applicationURL, String returnUrl)
			throws MalformedURLException, IOException {
		
		
		String redirectUrl = null;
		
		String sid = null;
		String uid = null;
		
		if(null == redirectUrl){
			
			Cookie[] cookies = request.getCookies();
		    if (cookies != null) {
		    	for (int i = 0; i < cookies.length; i++) {
		            Cookie cookie = cookies[i];
		            if(cookie.getMaxAge() != 0){
			            String cookieName = cookie.getName();
			            
			            if (cookieName.equalsIgnoreCase("sid")){
			           		if(null == sid) {
			            		sid = cookie.getValue();
			           		} else {
			           			logger.error("login: Muti cookie found. name: 'sid', value: '%s', previousValue: '%s'", cookie.getValue(), sid);
			           			sid = cookie.getValue();
			           		}
			           	}
			            
			            if (cookieName.equalsIgnoreCase("uid")){
			            	if(null == uid) {
			            		uid = cookie.getValue();
			            	} else {
			            		logger.error("login: Muti cookie found. name: 'uid', value: '%s', previousValue: '%s'", cookie.getValue(), uid);
			            		uid = cookie.getValue();
			            	}
			           	}
		            }
		        }
		    }
		}
		
		if(null == redirectUrl){
		    if(null != sid && !sid.equals("null") && null != uid && !uid.equals("null")) {
		    	if(null == returnUrl || returnUrl.equals("")){
		    		redirectUrl = applicationURL + "/" + request.getParameter("lang") + "/dashboard";
		    	} else {
		    		redirectUrl = returnUrl;
		    	}
		    } else {
		    	logger.warn("login: One of the cookie keys not saved. sid:'%s', uid:'%s'", sid, uid);
		    	redirectUrl = applicationURL + "/" + request.getParameter("lang") + "/login";
		    }
		}
		
		return redirectUrl;
	}

}
