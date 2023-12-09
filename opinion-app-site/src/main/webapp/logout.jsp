

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.library.systemFramework.ApplicationConfiguration"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%


URL applicationURL = request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath());

Cookie[] cookies = request.getCookies();
if (cookies != null) {
    for (int i = 0; i < cookies.length; i++) {
    	
    	response.setContentType("text/html");
        Cookie cookie = cookies[i];
        
        /*
        out.println(cookie.getName());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setComment("EXPIRING COOKIE at " + System.currentTimeMillis());
		*/
        
        Cookie cookieToRemove = new Cookie(cookie.getName(), "");
        cookieToRemove.setMaxAge(0);
        cookieToRemove.setPath("/");
        if(null != ApplicationConfiguration.Opinion.getSessionDomain()){
        	cookieToRemove.setDomain(ApplicationConfiguration.Opinion.getSessionDomain());
		}
        cookieToRemove.setComment("EXPIRING COOKIE at " + System.currentTimeMillis());
        
        response.addCookie(cookieToRemove);
        
    }
   
    response.sendRedirect(applicationURL.toString());
}
		
%>