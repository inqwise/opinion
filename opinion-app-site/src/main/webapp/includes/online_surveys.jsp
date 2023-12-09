
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.opinion.dao.*" %>
<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
%>

<h1><%= p.getHeader() %></h1>
<div>
	<div class="promo-1">
		<div class="container" style="padding-top: 0px; padding-bottom: 24px;">
			<h2>Online Surveys<br/> Made Easy!</h2>
			<p>Create online surveys - fast, easy, and free.</p>
			<a href="<%=absoluteURL %>/register?ref=online-surveys" title="Sign Up" class="button-yellow"><span>Sign Up</span></a>
		</div>
	</div>
	<div style="clear: both;">
		<div style="overflow: hidden; padding: 24px 0;">
			<div style="float: left; width:680px; ">
				<div>
					<div class="bucket">
						<h2>Create Surveys</h2>
						<ul class="ls">
							<li><b>Design</b> professional, branded surveys with our rich drag-and-drop survey editor.</li>
							<li><b>Build</b> unlimited surveys in minutes.</li>
						</ul>
					</div>
					<div style="padding-left: 10px;" class="bucket">
						<h2>Collect Responses</h2>
						<ul class="ls">
							<li><b>Share</b> a link to your survey through email, Facebook, Twitter or your blog.</li>
							<li><b>Embed</b> your survey directly into your web page.</li>
						</ul>
					</div>
					<div style="padding-left: 10px;" class="bucket">
						<h2>Analyze Results</h2>
						<ul class="ls">
							<li><b>Sort and filter</b> real-time data in an online table.</li>
							<li><b>View</b> automatically generated charts of your data.</li>
						</ul>
					</div>
				</div>
				<div style="clear: both;padding-top: 24px;">
				
					<!--
					<div style="width: 450px; float: left">
						<h3 class="ui-header-light">Online Survey Software. Fast, Simple &amp; Powerful.</h3>
						<p style="padding: 12px 0 24px;margin:0">Inqwise is an online questionnaire tool that makes it easy to build online surveys. Whether you're looking for to do an advanced survey or gather customer feedback. Inqwise can help you create online surveys to collect and analyze data. With our online survey tool, you can create surveys and forms, deploy them online and analyze them to get the answers you're looking for.</p>
					</div>
					<div style="width: 220px; float: left; padding-left: 10px;">
						<h3 class="ui-header">Customer Testimonials</h3>
						<div style="padding: 12px 0 0 8px;">
							Excellent product, I have to really thank you for this tool. Allowed me to reach respondents faster, conveniently, maintain anonymity and ensure data was ready for timely analysis and transfer.
							<br><br>
							<b style="color: #333">Willy K. Ng'etich</b><br>
							<b style="color: #333">Cape Peninsula University of Technology</b>
						</div>
					</div>
					-->
					
				</div>
			</div>
			<div style="width: 220px; float: left; padding-left: 10px;">
				
				<!--
				<h3 class="ui-header">Release Notes</h3>
				<div style="padding: 12px 0 0 8px;">
					<span class="light">Feb 18, 2013</span>
					<div style="padding: 6px 0 24px;">This release includes major features, bug fixes and enhancements related to stability, performance and security.</div>
				</div>
				-->
				
			</div>
		</div>
	</div>
</div>