<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="org.apache.commons.lang3.StringUtils" %>

<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>
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
	
	String templateName = StringUtils.trimToNull(request.getParameter("template_name"));
	
%>

<h1><%= p.getHeader() %></h1>
<div>
<h3 class="ui-header-light">Save Time with a Template</h3>
<p>Inqwise's online survey tool lets you choose from pre-built survey templates that make getting started fast and easy.<br/>
All template questions can be customized to fit your survey needs.</p>
	<div>
		<div style="float: left; width: 220px;">
			<ul class="side-navigation-tabs">
				<li><a href="<%=absoluteURL %>/templates/surveys/customer-satisfaction-survey" title="Surveys">Surveys</a></li>
				<li><a href="<%=absoluteURL %>/templates/forms/mailing-list" title="Forms">Forms</a></li>
				<li><a href="<%=absoluteURL %>/templates/invitations/birthday-party" title="Invitations">Invitations</a></li>
				<li><a href="<%=absoluteURL %>/templates/registrations/retreat-registration" title="Registrations">Registrations</a></li>
				<li class="active"><a href="<%=absoluteURL %>/templates/tracking/bug-tracker" title="Tracking">Tracking</a></li>
			</ul>
		</div>
		<div style="float: left; width: 220px; padding-left: 10px;">
			<ul class="side-navigation-tabs">
				<li class="active"><a href="<%=absoluteURL %>/templates/tracking/bug-tracker" title="Bug Tracker">Bug Tracker</a></li>
			</ul>
		</div>
		<div style="float: left; width: 450px; padding-left: 10px;">
			
			Bug Tracker
			<div><a href="<%=applicationURL %>/servlet/opinions?guid=ca6cf52e-1b92-491e-9d38-2d4ab0be774a&name=Bug Tracker" title="Use this Template" class="button-white"><span>Use this Template</span></a></div>
			<div style="border: 1px solid #ccc; min-height: 300px; margin: 10px 0">
				
				<div id="template_preview"></div>
				<script type="text/javascript">
				var _inq = _inq || [];
			    $(function() {
			    	var iframe = $("<iframe style=\"border: none; height:100%; width:100%\" frameborder=\"0\" allowtransparency=\"true\" />").appendTo("#template_preview").on("load",function(){
				        
			    		var el = $(this).contents().find("body");
				        
						_inq.push({
							guid : "ca6cf52e-1b92-491e-9d38-2d4ab0be774a",
							collectorUrl : "<%=ApplicationConfiguration.Api.getUrl()%>",
							element : el
						});
						(function() {
						    var po = document.createElement("script"); po.type = "text/javascript"; po.async = true;
						    po.src = "<%=ApplicationConfiguration.Opinion.Collector.getUrl()%>/scripts/widget/1.1.1/survey.js";
						    var s = document.getElementsByTagName("script")[0]; s.parentNode.insertBefore(po, s);
						})();
				        
				    });
			    });
				</script>
				
			</div>
			
		</div>
	</div>
</div>