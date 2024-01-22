
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.inqwise.opinion.cms.common.IPage"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1>Bugs</h1>
			</td>
			<td class="cell-right">
				<a href="<%=absoluteURL %>/create-bug-report" title="Report a Bug" class="button-green"><span><i class="icon-add-white">&nbsp;</i>Report a Bug</span></a>
			</td>
		</tr>
	</table>
	<div>
		<div class="wrapper-content-left">
			<h3 class="ui-header-light">Bugs and Known Issues</h3>
			<p>All bugs that we receive from our customers, will be published on this page.</p>
			<a href="<%=absoluteURL %>/create-bug-report" title="Create a bug report">Create a bug report</a>
			
			
			<!-- 
			Status: New
			Priority: None
			Updated: Today at 6:01pm
			Repro
			Spes to Reproduce
			Expected Behavior
			Actual Behavior 
			-->
			
		</div>
		<div class="wrapper-content-middle">
			
		</div>
	</div>
</div>