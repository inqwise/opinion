<%@page import="com.opinion.library.common.errorHandle.OperationResult"%>
<%@page import="java.util.List"%>

<%@ page import="com.opinion.cms.common.kb.IArticle" %>
<%@ page import="com.opinion.cms.common.kb.ITopic" %>
<%@ page import="com.opinion.cms.managers.ArticleManager" %>
<%@ page import="com.opinion.cms.managers.TopicManager" %>

<%@page import="com.opinion.cms.common.faq.IFaq"%>
<%@page import="com.opinion.cms.managers.FaqsManager"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.opinion.cms.common.IPage"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String searchWord = StringUtils.trimToNull(request.getParameter("search_word"));
	
	List<ITopic> topics = null;
	OperationResult<List<ITopic>> topicsResult = TopicManager.getTopics(null, null);
	if(!topicsResult.hasError()) {
		topics = topicsResult.getValue();
	}
	
	List<IArticle> articles = null;
	OperationResult<List<IArticle>> articlesResult = ArticleManager.getArticles(null);
	if (!articlesResult.hasError()) {
		articles = articlesResult.getValue();
	}
	
%>

<h1><%= p.getHeader() %></h1>
<div>
	<div class="wrapper-content-left">
	
		<div style="background: none repeat scroll 0 0 #ECEFF5; border: 1px solid #B1C3DA; padding: 76px 8px 0; display: none">
			We're here for you. Find articles, help, and advice for getting the most out of Inqwise.com.
			<input type="text" placeholder="Have a Question? Ask or enter a search term here." />
			<div class="row">
				<div class="cell">
					<input type="text" id="input_search" style="width: 225px;" autocomplete="off" placeholder="Enter a keyword or question" onkeydown="catchSearch(event)" />
				</div>
				<div class="cell" style="padding-left: 6px;"><a href="javascript:validateSearch();" title="Search" class="button-blue"><span>Search</span></a></div>
			</div>
		</div>
		
		<%
			Integer i = 0;
			for(ITopic topic : topics) {
				%>
				
				<div class="overview <%= (i % 2 != 0) ? "far-right" : "" %>">
					<h3><%=topic.getName() %></h3>
					<ul class="le">
					<%
					List<IArticle> articlesByTopicId = null;
					OperationResult<List<IArticle>> articlesByTopicIdResult = ArticleManager.getArticles(topic.getId());
					if (!articlesByTopicIdResult.hasError()) {
						articlesByTopicId = articlesByTopicIdResult.getValue();
						for(IArticle article : articlesByTopicId) {
							if(article.getActive()) {
							%>
								<li><a href="<%=absoluteURL %>/kb/articles/<%=article.getUri()%>" title="<%=article.getTitle() %>"><%=article.getTitle() %></a></li>
							<%
							}
						}
					}
					%>
					</ul>
				</div>
				
				<%
				i++;
			}
		%>
		
	</div>
	<div class="wrapper-content-middle">
		<h3 class="ui-header">Popular Help Articles</h3>
		<div>
			<ul class="ll">
			<%
				for(IArticle article : articles) {
					if(article.getActive() && article.getPopular()) {
					%>
					<li><a href="<%=absoluteURL %>/kb/articles/<%=article.getUri()%>" title="<%=article.getTitle() %>" class="arrow-grey"><%=article.getTitle() %></a></li>
					<%
					}
				}
			%>
			
<!-- 		
<%
OperationResult<List<IFaq>> faqsResults = FaqsManager.searchFaqs(searchWord, null, 10);
if(!faqsResults.hasError()){
	List<IFaq> faqs = faqsResults.getValue();
	for(IFaq faq : faqs){
%>
				<li><a href="<%=absoluteURL %>/faq/search/<%=URLEncoder.encode(faq.getQuestion()) %>" title="<%=faq.getQuestion() %>" class="arrow-grey"><%=faq.getQuestion() %></a></li>
<%
	}
}
%>
-->
			</ul>
		</div>
		
	</div>
	<div class="wrapper-content-right">
		<h3 class="ui-header">Contact Support</h3>
		<div style="padding: 12px 0 24px 8px;">
			If you cannot find what you are looking for please contact support and we will get back to you with an answer.
			<p><a href="mailto:support@inqwise.com" title="support@inqwise.com">support@inqwise.com</a></p>
		</div>
	</div>
</div>

<script type="text/javascript">
var catchSearch = function(e){var code;if(!e)var e=window.event;if(e.keyCode)code=e.keyCode;else if(e.which)code=e.which;if(code==13)validateSearch();}
var validateSearch = function(){
	var q = $.trim($('#input_search').val());
	if(q.length>0) {
		location.href="<%=absoluteURL %>/faq/search/" + escape(q);
	}
};
</script>
