<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>

<h1><%= p.getHeader() %></h1>
<div>
	<div class="wrapper-content-left">
		
	</div>
</div>