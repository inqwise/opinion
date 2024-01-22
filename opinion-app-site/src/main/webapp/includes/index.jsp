<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.xnap.commons.i18n.I18n" %>
<%@ page import="org.xnap.commons.i18n.I18nFactory" %>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);

String absoluteURL = p.getRootAbsoluteUrl();
String absoluteSecureURL = p.getAbsoluteSecureUrl();
String applicationURL = p.getApplicationUrl();
%>

<h1><%= p.getHeader() %></h1>
