
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%@ page import="com.inqwise.opinion.cms.common.IStylesheet" %>
<%@ page import="com.inqwise.opinion.cms.common.IScript" %>
<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="java.net.URLEncoder" %>

<%
IPage p = (IPage)request.getAttribute("p");
String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();

String pageKeywords = StringUtils.join(p.getKeywords(), ',');
String pageDescription = p.getDescription();
String pageTitle = p.getTitle();
String pageHeader = p.getHeader();
String pageContent = p.getContent();
String canonicalURL = p.getCanonicalUrl();
String lang = p.getCultureCode().substring(0, 2);
String uri = p.getUri();
//Get current year
Calendar cal=Calendar.getInstance();
int year = cal.get(Calendar.YEAR);

String opinionId = null;
opinionId = request.getParameter("opinion_id");
String collectorId = null;
collectorId = request.getParameter("collector_id");
String requestUrl = request.getAttribute("jakarta.servlet.forward.request_uri").toString();
Object objQueryString = request.getAttribute("jakarta.servlet.forward.query_string");
String queryString = null;

if(null != objQueryString){
	queryString = StringUtils.trimToNull(objQueryString.toString());
}

if(null != queryString){
	requestUrl = requestUrl + "?" + queryString;
}

if(null != requestUrl){
	requestUrl = URLEncoder.encode(p.getServerUrl() + requestUrl,"utf-8");
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%=lang%>" lang="<%=lang%>">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%= p.getWindowTitle() %> - BackOffice Inqwise</title>
		<meta name="robots" content="noindex, nofollow" />
		<meta name="author" content="Inqwise" />
		<meta http-equiv="content-language" content="<%=lang%>" />
		
		<link rel="icon" href="<%=applicationURL%>/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="<%=applicationURL%>/favicon.ico" type="image/x-icon" />
		 		
		<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/global.css" />
		<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/lightface/lightface.css" />
		<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/datepicker/datepicker.css" />
		<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/datatable/datatable.css" />
		<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/tipsy/tipsy.css" />
		<%
			for(IStylesheet css : p.getCssList()){
				out.print(p.getHtmlTag(css));
			}
		%>

        <script type="text/javascript" src="<%=applicationURL%>/scripts/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript" src="<%=applicationURL%>/js/underscore/underscore-min.js"></script>
		
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/utils.js"></script>
		<!--[if lt IE 8]>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/json2.js"></script>
		<![endif]-->
		
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/date.min.js"></script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/validator/validator-1.2.8.js"></script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/lightface/lightface.min.js"></script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/lightloader/1.0.1/lightloader.min.js"></script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/datatable/datatable.js"></script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/tipsy/tipsy.js"></script>
		<%
			for(IScript script : p.getScripts()){
				out.print(p.getHtmlTag(script));
			}
		%>
		<script type="text/javascript">
		var startTime=(new Date).getTime();
		var servletUrl = "<%=applicationURL%>/servlet/DataPostmaster/<%=p.getCultureCode()%>";
		
		// authentication
		;(function($) {
			authentication = function(givenOptions) {
				
				var options = $.extend( {
					servletUrl : null,
					mustAuthenticated : false,
					absoluteUrl : null,
					applicationUrl : null
				}, givenOptions);
				
				var userInfo = {};
				
				var validate = function() {
					
					if($.cookie('sib') != null 
							&& $.cookie('uib') != null) {
						
						// validate user
						$.ajax({
							type: "GET",
							url: options.servletUrl,
						  	async: false,
						  	data: { 
								rq: JSON.stringify({ 
									validateUser : {}
								}), 
								timestamp: $.getTimestamp() 
							},
						  	dataType: "json",
						  	success: function(data) {
						  		if(data.validateUser.error) {
						  			if(options.mustAuthenticated) {
										location.href = options.applicationUrl + "?ret=<%=requestUrl%>";
						  			} else {
						  				//
						  			}
								} else {
									if(options.mustAuthenticated) {
										
										userInfo = data.validateUser;
										
									} else {
										location.href = options.absoluteUrl + "/dashboard";
									}
								}
						  	},
							error: function (XHR, textStatus, errorThrow) {
								// error 
								// connection
							}
						});
							
					} else {
						if(options.mustAuthenticated) {
							location.href = options.applicationUrl + "?ret=<%=requestUrl%>";
						} else {
							
						}
					}
				};
				
				validate();
				
				return {
					userInfo : userInfo
				};
			};
		})(jQuery);
		
		// Your session is about to expire
		// Do you want to stay logged in?
		// Yes, keep me signed in 
		var auth = new authentication({
			servletUrl : servletUrl,
			mustAuthenticated : true,
			absoluteUrl : "<%=absoluteURL %>",
			applicationUrl : "<%=applicationURL%>"
		});
		
		
		var applicationUri = "<%=applicationURL%>";
		var absoluteUrl = "<%=absoluteURL %>";

		var loader = null;
		$(function() {
			
			loader = new lightLoader();
			
			// display userName
			$('#label_username').text(auth.userInfo.userName);
			
		});
		</script>
	</head>
	<body id="top">
		<div class="<%= ((p.getTemplateId() == 3) ? "wrapper-constructed" : "wrapper") %> cW">
			<div class="wrapper-content">
				<div class="wrapper-header">
					<div class="header-container">
						<div class="header-toolbar">
							<div class="header-toolbar-left-panel">
								<div class="logo-container">
									<a href="<%=absoluteURL%>/dashboard" title="Survey Software" class="logo">Online Survey Software</a>
								</div>
							</div>
							<div class="header-toolbar-right-side">
								<div class="login-bar">
									<ul class="lg">
										<li class="first-item"><span id="label_username"></span></li><li class="last-item"><a href="<%=absoluteURL %>/logout" title="Sign Out">Sign Out</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="header-navigation">
							<ul class="header-navigation-tabs">
								<li class="first-item"><a class="<%= ("dashboard".equals(uri) ? "selected" : "" ) %>" href="<%=absoluteURL%>/dashboard" title="Dashboard"><span>Dashboard</span></a></li>
								<li><a class="<%= (("users".equals(uri) || "create-user".equals(uri) || "user-details".equals(uri) || "user-messages".equals(uri) || "user-history".equals(uri) || "user-accounts".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL%>/users" title="Users"><span>Users</span></a></li>
								<li><a class="<%= (("accounts".equals(uri) || "create-account".equals(uri) || "account-details".equals(uri) || "account-users".equals(uri) || "account-surveys".equals(uri) || "account-polls".equals(uri) || "account-collectors".equals(uri) || "account-collector-details".equals(uri) || "account-collector-settings".equals(uri) || "account-transaction-history".equals(uri) || "account-charges".equals(uri) || "account-charge".equals(uri) || "account-billing".equals(uri) || "account-make-payment".equals(uri) || "account-recurring".equals(uri) || "account-uninvoiced-list".equals(uri) || "account-invoices".equals(uri) || "account-invoice".equals(uri) || "account-receipt".equals(uri) || "account-notes".equals(uri) || "account-history".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL%>/accounts" title="Accounts"><span>Accounts</span></a></li>
								<li><a class="<%= ("surveys".equals(uri) ? "selected" : "" ) %>" href="<%=absoluteURL%>/surveys" title="Surveys"><span>Surveys</span></a></li>
								<li><a class="<%= ("collectors".equals(uri) ? "selected" : "" ) %>" href="<%=absoluteURL%>/collectors" title="Collectors"><span>Collectors</span></a></li>
								<li><a class="<%= (("invoice-list".equals(uri) || "invoice".equals(uri) || "uninvoiced-list".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL%>/invoice-list" title="Billing"><span>Billing</span></a></li>
								<li><a class="<%= (("tickets".equals(uri) || "ticket".equals(uri) || "articles".equals(uri) || "article".equals(uri) || "article-new".equals(uri) || "topics".equals(uri) || "topic".equals(uri) || "topic-new".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL%>/articles" title="Knowledge Base"><span>Knowledge Base</span></a></li>
								<li><a class="<%= (("blog-posts".equals(uri) || "blog-categories".equals(uri) || "blog-tags".equals(uri) || "blog-comments".equals(uri) || "blog-post".equals(uri) || "blog-post-new".equals(uri) || "blog-category-new".equals(uri) || "blog-tag-new".equals(uri) || "blog-post-comments".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL%>/blog-posts" title="Blog"><span>Blog</span></a></li>
								<li><a class="<%= (("pages".equals(uri) || "page-new".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL%>/pages" title="Content"><span>Content</span></a></li>
								<li><a class="<%= (("setup".equals(uri) || "system-events".equals(uri) || "system-event".equals(uri) || "jobs".equals(uri) || "plans".equals(uri) || "plan".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL%>/setup" title="Setup"><span>Setup</span></a></li>
							</ul>
						</div>
					</div>
				</div>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td valign="top">
							<div class="cA1">
								<!--
								<div class="flyover">
									<div class="flyover-bar">
										<div class="cA3">
	
										</div>
									</div>
									<div class="flyover-container">
										<div class="cA5">
											<div style="padding: 10px;" class="_5cw7">
												<div class="cA9">
													<a class="cA4" title="Collapse navigation panel" style="display: none">Collapse navigation panel</a>
													<a class="cA7" title="Lock navigation panel">Lock navigation panel</a>
												</div>
												<div>
													<div class="search-across">
														<input type="text" id="text_search_across" placeholder="Search..." />
														<div id="container_search_across" style="position: relative"></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								-->
							</div>
							<div class="content-container XcO">
								<div class="breadcrumbs">
								<%
								StringBuilder sb = new StringBuilder();
								if(p.hasParent()){
									sb.append("<span class=\"child-item\">" + p.getTitle() + "</span>");
									IPage actualPage = p.getParent();
									while(actualPage.hasParent()){
										
										if(actualPage.getUri().equals("collectors") && !actualPage.getUri().equalsIgnoreCase("create-collector")) {
											sb.insert(0, "<a href=\"" + absoluteURL + "/surveys/" + opinionId + "/collectors/" + collectorId
													+ "\" class=\"child-item\" style=\"color: #324E8D;\" id=\"breadcrumb_collector_name\">&nbsp;</a>");
										}
										
										sb.insert(0, "<a href=\"" + absoluteURL + "/"
												+ ((actualPage.getUri().equals("collectors") || actualPage.getUri().equals("reports") || actualPage.getUri().equals("responses")) ? "surveys/" + opinionId + "/" + actualPage.getUri() : actualPage.getUri() )
												+ "\" title=\"" + actualPage.getTitle() + "\" class=\"child-item\" " + "style=\"color: #324E8D;\"" + ">" + actualPage.getTitle() + "</a>");
										
										actualPage = actualPage.getParent();
									}
									
									if(actualPage != p){
										if(!actualPage.getUri().equalsIgnoreCase("create-survey") && !actualPage.getUri().equalsIgnoreCase("bugs") && !actualPage.getUri().equalsIgnoreCase("create-bug-report")) {
											sb.insert(0, "<a class=\"child-item\" style=\"color: #324E8D;\" id=\"level1\">&nbsp;</a>");
										}
										
										sb.insert(0, "<a href=\"" + actualPage.getAbsoluteUrl() + "\" title=\"" + actualPage.getTitle() + "\">" + actualPage.getTitle() + "</a>");
									}
								} else {
									sb.append("<span style=\"color: #999\">" + p.getTitle() + "</span>");
								}
								
								out.print(sb.toString());
								%>
								</div>
								<div>
									<% if(!p.isComplex()) { %>
									<h1><%= p.getHeader() %></h1>
									<div>
										<%out.print(p.getContent()); %>
									</div>
									<% 
									  } else { 
										  out.print(p.getContent()); 
									  } 
									%>
								</div>
								<div style="clear: both; padding-top: 20px;">
									<!-- <a href="#top" title="Back to Top">Top</a> --> &nbsp;
								</div>
							</div>
						</td>
					</tr>
				</table>
				
			</div>		
			<div class="wrapper-footer XcO">
				<div class="footer-container">
					<div>
						<div class="footer-navigation-left-panel">
							&nbsp;
						</div>
						<div class="footer-navigation-right-panel"></div>
					</div>
					<div class="footer-copyright">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td valign="top">Copyright &copy; 2011-<%=year %> Inqwise Ltd. All rights reserved.</td>
								<td valign="top" style="text-align: right"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
		var errorHandler = function(params) {
			if(params.error == "NotSignedIn") {
				var modal = new lightFace({
					title : "Session expired",
					message : "Your browser session has expired after a period of inactivity, or is no longer valid.<br/>Please relogin to Inqwise and try again.",
					actions : [
						{ 
							label : "Relogin", 
							fire : function() { 
								// redirect
								location.href = "<%=absoluteURL%>";
							}, 
							color: "blue" 
						}
					],
					overlayAll : true
				});
			}
		};
		</script>
		
		<%if(p.isEnableGoogleAnalytics() && ApplicationConfiguration.General.isEnableGoogleAnalytics()){ %>
		<script type="text/javascript">
			var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
			document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
		</script>
		<script type="text/javascript">
			try {
				var pageTracker = _gat._getTracker("UA-2521304-11");
				pageTracker._trackPageview();
			} catch(err) {}
		</script>
		<%}%>
		
	</body>
</html>