<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	
	String absoluteSecureURL = p.getAbsoluteSecureUrl();
	String applicationURL = p.getApplicationUrl();
%>

<h1><%= p.getHeader() %></h1>
<div>
<h3 class="ui-header-light">Save Time with a Template</h3>
<p>Inqwise's online survey tool lets you choose from pre-built survey templates that make getting started fast and easy.<br/>
All template questions can be customized to fit your survey needs.</p>
	<div>
		<div style="float: left; width: 220px;">
			<ul>
				<li><a href="<%=absoluteURL %>/templates/surveys/customer-satisfaction-survey" title="Surveys">Surveys</a></li>
				<li><a href="<%=absoluteURL %>/templates/forms/mailing-list" title="Forms">Forms</a></li>
				<li><a href="<%=absoluteURL %>/templates/invitations/birthday-party" title="Invitations">Invitations</a></li>
				<li><a href="<%=absoluteURL %>/templates/registrations/retreat-registration" title="Registrations">Registrations</a></li>
				<li class="active"><a href="<%=absoluteURL %>/templates/lead-generation/sales-lead-form" title="Lead Generation">Lead Generation</a></li>
				<li><a href="<%=absoluteURL %>/templates/tracking/bug-tracker" title="Tracking">Tracking</a></li>
			</ul>
		</div>
		<div style="float: left; width: 220px; padding-left: 10px;">
			<ul>
				<li class="active"><a href="<%=absoluteURL %>/templates/lead-generation/sales-lead-form" title="Sales Lead Form">Sales Lead Form</a></li>
			</ul>
		</div>
		<div style="float: left; width: 450px; padding-left: 10px;">
			
			
		</div>
	</div>
</div>