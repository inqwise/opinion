<%@ page import="com.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);
String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>

<h1><%= p.getHeader() %></h1>

...