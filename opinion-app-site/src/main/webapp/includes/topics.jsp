<%@page import="com.inqwise.opinion.library.common.errorHandle.OperationResult"%>
<%@page import="java.util.List"%>

<%@ page import="com.inqwise.opinion.cms.common.kb.IArticle" %>
<%@ page import="com.inqwise.opinion.cms.common.kb.ITopic" %>
<%@ page import="com.inqwise.opinion.cms.managers.ArticleManager" %>
<%@ page import="com.inqwise.opinion.cms.managers.TopicManager" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.Format" %>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.inqwise.opinion.cms.common.IPage"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	Format displayDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
	String topicUri = StringUtils.trimToNull(request.getParameter("topic_uri"));
	Integer topicId = null;
	
%>

<h1>
<%
	if(null != topicUri) {
		ITopic currentTopic = null;
		OperationResult<ITopic> topicResult = TopicManager.getTopic(null, topicUri);
		if(!topicResult.hasError()) {
			currentTopic = topicResult.getValue();
			topicId = currentTopic.getId();
			%>
			<a href="<%=absoluteURL%>/kb/topics" title="Topics">Topics</a>&nbsp;&rsaquo;&nbsp;<%=currentTopic.getName() %>
			<%
		}
	} else {
	%>
	<%=p.getHeader()%>
	<%
	}
%>
</h1>
<div>
	<div class="wrapper-content-left">
		<%
			if(topicUri != null && topicId != null) {
				List<IArticle> articles = null;
				OperationResult<List<IArticle>> articlesResult = ArticleManager.getArticles(topicId);
				if (!articlesResult.hasError()) {
					articles = articlesResult.getValue();
				}
				for(IArticle article : articles) {
					if(article.getActive()) {
					%>
						<div style="padding-bottom: 20px;">
							<h3><a href="<%=absoluteURL %>/kb/articles/<%=article.getUri()%>" title="<%=article.getTitle() %>"><%=article.getTitle() %></a></h3>
							<p>Last Updated - <%=article.getModifyDate() != null ? displayDateFormatter.format(article.getModifyDate()) : displayDateFormatter.format(article.getCreateDate()) %></p>
							<div>
								<p><%=article.getContent().replaceAll("\\<.*?>","").replaceAll("^(.{100})(.*)$","$1...") %></p>
								<a href="<%=absoluteURL %>/kb/articles/<%=article.getUri()%>">Read more...</a>
							</div>
						</div>
					<%
					}
				}
			} else {
				%>
				<ul class="le">
				<%
					List<ITopic> topics = null;
					OperationResult<List<ITopic>> topicsResult = TopicManager.getTopics(null, null);
					if(!topicsResult.hasError()) {
						topics = topicsResult.getValue();
					}
					for(ITopic topic : topics) {
						%>
						<li><a href="<%=absoluteURL %>/kb/topics/<%=topic.getUri()%>" title="<%=topic.getName() %>" class="arrow-grey"><%=topic.getName() %></a></li>
						<%
					}
				%>
				</ul>	
				<%
			}
		%>
	</div>
	<div class="wrapper-content-middle">
	&nbsp;
	</div>
	<div class="wrapper-content-right">
	&nbsp;
	</div>
</div>