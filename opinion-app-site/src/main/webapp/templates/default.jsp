
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%@ page import="java.util.List" %>
<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.inqwise.opinion.cms.common.IStylesheet" %>
<%@ page import="com.inqwise.opinion.cms.common.IScript" %>

<%
IPage p = (IPage)request.getAttribute("p");
String absoluteURL = p.getRootAbsoluteUrl();
String absoluteSecureURL = p.getAbsoluteSecureUrl();

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

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%=lang%>" lang="<%=lang%>" dir="<%=(lang.equals("he") || lang.equals("ar") ? "rtl" : "ltr")%>">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%= p.getWindowTitle() %> - Inqwise</title>
		<meta name="description" content="<%=p.getDescription() %>" />
		<meta name="keywords" content="<%=StringUtils.join(p.getKeywords(), ",")%>" />
		<meta property="og:title" content="<%= p.getWindowTitle() %> - Inqwise" />
		<meta property="og:description" content="<%=p.getDescription() %>" />
		<meta property="og:image" content="<%=applicationURL %>/images/100x100.png" />
		<meta http-equiv="content-language" content="<%=lang%>" />
		
		<link rel="icon" href="<%=applicationURL%>/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="<%=applicationURL%>/favicon.ico" type="image/x-icon" />
		<link rel="home" href="<%=applicationURL %>" />
		<link rel="index" href="<%=applicationURL %>/sitemap.xml" />
		<link rel="canonical" href="<%=applicationURL + request.getAttribute("jakarta.servlet.forward.request_uri") %>" />		
		<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/global.css" />
		<%
			for(IStylesheet css : p.getCssList()){
				out.println(p.getHtmlTag(css));
			}
		%>
		
		<script type="text/javascript" src="<%=applicationURL%>/scripts/jquery/1.8.2/jquery.min.js"></script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/utils.js"></script>
		<!--[if lt IE 8]>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/json2.js"></script>
		<![endif]-->
		<%
			for(IScript script : p.getScripts()){
				out.println(p.getHtmlTag(script));
			}
		%>
		
		<script type="text/javascript">
		var servletUrl = "<%=applicationURL%>/servlet/DataPostmaster/<%=p.getCultureCode()%>";

		// authentication
		;(function($) {
			authentication = function(givenOptions) {
				
				var options = $.extend( {
					servletUrl : null,
					mustAuthenticated : false,
					absoluteUrl : null,
					absoluteSecureUrl : null,
					applicationUrl : null
				}, givenOptions);
				
				var userInfo = {};
				
				var validate = function() {
					
					$.cookie("sid", null, { path : "/" });
					$.cookie("uid", null, { path : "/" });
					$.cookie("sid", null, { path : "/", domain : "www.inqwise.com" });
					$.cookie("uid", null, { path : "/", domain : "www.inqwise.com" });
					
					if($.cookie('sid') != null 
							&& $.cookie('uid') != null) {

						$.ajax({
							type: "GET",
							url: options.servletUrl,
						  	async: false,
						  	data: { 
								rq: JSON.stringify({ 
									validateUser : {
										isBeforeLogin: true
									}
								}), 
								timestamp: $.getTimestamp() 
							},
						  	dataType: "json",
						  	success: function(data) {
						  		if(data.validateUser.error) {
						  			if(options.mustAuthenticated) {
						  				if("NoPackageSelected" == data.validateUser.error){
											location.href = options.absoluteUrl + "/pricing";
										} else {
											location.href = options.absoluteSecureUrl + "/login";
										}
						  			} else {
						  				
						  			}
								} else {
									if(options.mustAuthenticated) {
										userInfo = data.validateUser;
									} else {
										location.href = options.absoluteSecureUrl + "/dashboard";
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
							location.href = options.applicationUrl;
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
		
		var auth = new authentication({
			servletUrl : servletUrl,
			absoluteUrl : "<%=absoluteURL %>",
			absoluteSecureUrl : "<%= absoluteSecureURL%>"
		});
		
		</script>
		
	</head>
	<body id="top">
		<div class="wrapper">
			<div class="wrapper-content">
				<div class="wrapper-header">
					<div class="header-container">
						<div class="header-toolbar">
							<div class="header-toolbar-left-panel">
								<div class="global-sprite logo">1</div>
								<!-- 
								<div class="logo-container">
									<a href="<%=(lang.equals("en") ? applicationURL : absoluteURL)%>" title="Survey Software" class="logo">Online Survey Software</a>
								</div>
								-->
							</div>
							<div class="header-toolbar-right-side">
								<div class="login-bar">
									<ul class="lg">
										<li class="first-item"><a href="<%=absoluteSecureURL%>/register" title="Create free account">Create free account</a></li><li class="last-item"><a href="<%=absoluteSecureURL%>/login" title="Sign in">Sign in</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="header-navigation">
							<ul class="header-navigation-tabs">
								<li class="first-item"><a class="<%= ("index".equals(uri) ? "selected" : "" ) %>" href="<%=(lang.equals("en") ? applicationURL : absoluteURL)%>" title="Home"><span>Home</span></a></li>
								<!-- <li><a class="<%= ("examples".equals(uri) ? "selected" : "" ) %>" href="<%=absoluteURL%>/examples" title="Examples"><span>Examples</span></a></li> -->
								<!-- <li><a class="<%= (("templates".equals(uri) || "survey-templates".equals(uri) || "form-templates".equals(uri) || "invitation-templates".equals(uri) || "registration-templates".equals(uri) || "tracking-templates".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL%>/templates" title="Templates"><span>Templates</span></a></li> -->
								<li><a class="<%= ("features".equals(uri) ? "selected" : "" ) %>" href="<%=absoluteURL%>/features" title="Features"><span>Features</span></a></li>
								<li><a class="<%= ("pricing".equals(uri) ? "selected" : "" ) %>" href="<%=absoluteURL%>/pricing" title="Plans &amp; Pricing"><span>Plans &amp; Pricing</span></a></li>
								<li><a class="<%= (("help-center".equals(uri) || "articles".equals(uri) || "topics".equals(uri) || "faq".equals(uri) || "faq-search".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL %>/help-center" title="Help Center"><span>Help Center</span></a></li>
								<%if(lang.equals("en")){%><li><a class="<%= (("blog".equals(uri) || "post".equals(uri) || "posts".equals(uri) || "categories".equals(uri) || "tags".equals(uri) || "blog-search".equals(uri)) ? "selected" : "" ) %>" href="<%=absoluteURL %>/blog" title="Blog"><span>Blog</span></a></li><%} %>
							</ul>
						</div>
						
					</div>
				</div>
				<div class="content-container">
					<div class="breadcrumbs">
					<%
					
					out.print("<a href=\"" + (lang.equals("en") ? applicationURL : absoluteURL) + "\" title=\"Home\">Home</a>");
					
					StringBuilder sb = new StringBuilder();
					if(p.hasParent()){
						sb.append("<span class=\"child-item\">" + p.getTitle() + "</span>");
						IPage actualPage = p.getParent();
						while(actualPage.hasParent()){
							sb.insert(0, "<a href=\"" + actualPage.getAbsoluteUrl()
									+ "\" title=\"" + actualPage.getTitle() + "\" class=\"child-item\" style=\"color: #324E8D;\">" + actualPage.getTitle() + "</a>");
							actualPage = actualPage.getParent();
						}
						
						if(actualPage != p){
							sb.insert(0, "<a href=\"" + actualPage.getAbsoluteUrl() + "\" title=\"" + actualPage.getTitle() + "\" class=\"child-item\" style=\"color: #324E8D;\">" + actualPage.getTitle() + "</a>");
						}
					} else {
						sb.append("<span class=\"child-item\">" + p.getTitle() + "</span>");
					}
					
					out.print(sb.toString());
					%>
					</div>
					
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
					<div style="clear: both; padding-top: 20px;">
						<!-- <a href="#top" title="Back to Top">Top</a> --> &nbsp;
					</div>
					
				</div>
			</div>		
			<div class="wrapper-footer">
				<div class="footer-container">
					<div>
						<div class="footer-navigation-left-panel">
							<ul class="ld">
								<li class="first-item"><a href="<%=absoluteURL%>/terms" title="Terms of Use">Terms of Use</a></li><li><a href="<%=absoluteURL%>/privacy" title="Privacy Policy">Privacy Policy</a></li><li><a href="<%=absoluteURL%>/help-center" title="Help Center">Help Center</a></li><li><a href="<%=absoluteURL %>/careers" title="Careers">Careers</a></li><li><a href="<%=absoluteURL%>/about" title="About Us">About Us</a></li><li><a href="<%=absoluteURL%>/contact" title="Contact Us">Contact Us</a></li><li class="last-item"><a href="<%=absoluteURL%>/sitemap" title="Sitemap">Sitemap</a></li>
							</ul>
						</div>
						<div class="footer-navigation-right-panel">
							<%if(lang.equals("en")){%>
							<ul class="ld">
								<li class="last-item"><a title="RSS Feeds" class="rss-feed" href="<%=absoluteURL%>/rss-feeds">RSS Feeds</a></li>
							</ul>
							<%} %>
						</div>
					</div>
					<div class="footer-copyright">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td valign="top">Copyright &copy; 2011-<%=year %> Inqwise Ltd. All rights reserved.</td>
								<td valign="top" style="text-align: right">
									<a href="http://aws.amazon.com/" title="Powered by Amazon Web Services" target="_blank"><img alt="Powered by Amazon Web Services" src="<%=applicationURL%>/images/powered_by_amazon_web_services1.png" height="50" /></a>
									<img src="<%=applicationURL%>/images/RapidSSL_SEAL-90x50.gif" alt="Secured by RapidSSL" />
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<%if(p.isEnableGoogleAnalytics() && ApplicationConfiguration.General.isEnableGoogleAnalytics()){ %>
		<script type="text/javascript">
		var _gaq = _gaq || [];
		_gaq.push(['_setAccount', 'UA-2521304-11']);
		_gaq.push(['_trackPageview']);
		(function() {
		var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		})();
		</script>
		<%} %>
		
	</body>
</html>