
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>

<%@ page import="com.inqwise.opinion.library.common.errorHandle.OperationResult"%>

<%@ page import="com.inqwise.opinion.cms.common.blog.IPost"%>
<%@ page import="com.inqwise.opinion.cms.managers.BlogManager"%>
<%@ page import="java.text.DateFormat"%>


<%@page import="com.inqwise.opinion.cms.common.ILanguage"%>
<%@page import="com.inqwise.opinion.cms.managers.LanguagesManager"%>
<%@page import="com.inqwise.opinion.cms.CmsSystem"%>
<%@page import="com.inqwise.opinion.cms.common.blog.Blogs"%>

<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
<%
	ServletContext context = getServletContext();
	Date now = new Date();

	String lang = null;
  	lang = request.getParameter("lang");
  	ILanguage language = null;
  	OperationResult<ILanguage> languageResult = LanguagesManager.getLanguageByCultureCode(lang, CmsSystem.DEFAULT_CULTURE_CODE);
  	if(!languageResult.hasError()){
  		language = languageResult.getValue();
  	}
  	  	
  	URL absoluteURL = request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath() + "/" + language.getAdaptedCultureCode()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath() + "/" + language.getAdaptedCultureCode());
  	URL applicationURL = request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath());
  	SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
	Calendar cal = Calendar.getInstance();
%>
<channel>
	<title>Official Inqwise Blog</title>
	<link><%=absoluteURL%>/blog</link>
	<description>Blog provided by Inqwise.</description>
	<language><%=language.getAdaptedCultureCode()%></language>
	<copyright>Copyright <%=cal.get(Calendar.YEAR)%>, Inqwise.</copyright>
	<pubDate><%=dateFormat.format(Calendar.getInstance().getTime())%></pubDate>
	<lastBuildDate><%=dateFormat.format(Calendar.getInstance().getTime())%></lastBuildDate>
	<category>Blog</category>
	<generator>In house</generator>
	<docs><%=absoluteURL%>/blog/rss</docs>
	<ttl>60</ttl>
	
	<atom:link href="<%=absoluteURL%>/blog/rss" rel="self" type="application/rss+xml" />
	
<%
		OperationResult<List<IPost>> postsResult = BlogManager.getPosts(100, Blogs.OfficialInqwiseBlog);
	if(!postsResult.hasError()){
		List<IPost> posts = postsResult.getValue();
		DateFormat postFormat = new SimpleDateFormat("yyyy/MM/dd/");
		for(IPost post : posts){
			out.println("<item>" +
			"<title>" + post.getTitle() + "</title>" +
			"<link>" + absoluteURL + "/blog/" + postFormat.format(post.getPostDate()) + post.getUrlTitle() + "</link>" +
			"<description>" + StringEscapeUtils.escapeHtml4(post.getContent()) + "</description>" + 
			"<pubDate>" + dateFormat.format(post.getPostDate()) + "</pubDate>" +
			"<guid>" + absoluteURL + "/blog/" + postFormat.format(post.getPostDate()) + post.getUrlTitle() + "</guid>" +	
			"</item>");		
		}
	}
	%>
</channel>
</rss>



