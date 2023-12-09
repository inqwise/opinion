<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.opinion.cms.common.IPage"%>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>


<div>
	
	<h1>Organize Works into Folders</h1>
	<div style="clear: both; margin-bottom: 10px; height: 82px;">
		To organize your works into folders, you can select an existing folder from the drop-down list next to each survey title.<br/>To create a folder, click "Create New Folder" and the new folder will be added to the drop-down.
	</div>
	
</div>
