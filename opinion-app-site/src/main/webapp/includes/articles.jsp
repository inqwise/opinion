<%@page import="com.opinion.library.common.errorHandle.OperationResult"%>
<%@page import="java.util.List"%>

<%@ page import="com.opinion.cms.common.kb.IArticle" %>
<%@ page import="com.opinion.cms.common.kb.ITopic" %>
<%@ page import="com.opinion.cms.managers.ArticleManager" %>
<%@ page import="com.opinion.cms.managers.TopicManager" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.Format" %>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.opinion.cms.common.IPage"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	Format displayDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
	String articleUri = StringUtils.trimToNull(request.getParameter("article_uri"));
	
	Integer topicId = null;
	IArticle currentArticle = null;
	if(articleUri != null) {
		OperationResult<IArticle> articleResult = ArticleManager.getArticle(null, articleUri);
		if(!articleResult.hasError()) {
			currentArticle = articleResult.getValue();
		}
		if(currentArticle != null) {
			topicId = currentArticle.getTopicId();
		}
	}
	
	
%>

<h1>
<%
if(articleUri != null && currentArticle != null) {
	%>
	<!-- <a href="<%=absoluteURL%>/kb/articles" title="Articles">Articles</a>&nbsp;&rsaquo;&nbsp;<%=currentArticle.getTitle() %> -->
	<%=currentArticle.getTitle() %>
	<%
} else {
	%>
	<%=p.getHeader() %>
	<%
}
%>
</h1>
<div>
	<div class="wrapper-content-left">
	<%
	if(articleUri != null && currentArticle != null) {
		%>
		<div>Last Updated - <%=(currentArticle.getModifyDate() != null ? displayDateFormatter.format(currentArticle.getModifyDate()) : displayDateFormatter.format(currentArticle.getCreateDate())) %></div>
		<%=currentArticle.getContent() %>
		<%
	}
	%>
	&nbsp;
	</div>
	<div class="wrapper-content-middle">
		<%
		if(currentArticle != null) {
			%>
			<h3 class="ui-header">Related Articles</h3>
			<ul class="ll">
			<%
			List<IArticle> articles = null;
			OperationResult<List<IArticle>> articlesResult = ArticleManager.getArticles(topicId);
			if (!articlesResult.hasError()) {
				articles = articlesResult.getValue();
			}
			for(IArticle article : articles) {
				if(article.getActive()) {
				%>
					<li><a href="<%=absoluteURL %>/kb/articles/<%=article.getUri()%>" title="<%=article.getTitle() %>" class="arrow-grey"><%=article.getTitle() %></a></li>
				<%
				}
			}
			%>
			</ul>
			<%
		}
		%>
		<h3 class="ui-header">Topics</h3>
		<ul class="ll">
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
	</div>
	<div class="wrapper-content-right">
		<h3 class="ui-header">Search</h3>
		<div style="padding: 10px 0 20px 8px;">
			<div class="row">
				<div class="cell">
					<input id="input_search" type="text" class="text-field" style="width: 144px;" placeholder="Search Articles" onkeydown="catchSearch(event)" autocomplete="off" />
				</div>
				<div class="cell" style="padding-left: 6px;">
					<a class="button-blue" title="Search" href="javascript:validateSearch();"><span>Search</span></a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var catchSearch = function(e){var code;if(!e)var e=window.event;if(e.keyCode)code=e.keyCode;else if(e.which)code=e.which;if(code==13)validateSearch();}
var validateSearch = function(){
	var q = $.trim($('#input_search').val());
	if(q.length>0) {
		location.href="<%=absoluteURL %>/kb/articles/search/" + escape(q);
	}
};
</script>