
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="com.opinion.cms.managers.BlogManager" %>
<%@ page import="com.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.opinion.cms.common.blog.IPost" %>
<%@ page import="com.opinion.cms.managers.TagsManager" %>
<%@ page import="com.opinion.cms.common.ITag" %>
<%@ page import="com.opinion.cms.common.ICategory" %>

<%@ page import="org.apache.commons.lang3.time.DateUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.Format" %>
<%@ page import="java.util.Dictionary" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.ParseException"%>
<%@page import="com.opinion.cms.common.IPage"%>
<%@page import="com.opinion.cms.common.blog.Blogs"%>
<%@page import="com.opinion.cms.managers.CategoriesManager"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	String tagName = StringUtils.trimToNull(request.getParameter("tag_name"));
	
	Format displayDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
	
	DateFormat archiveFormatter = new SimpleDateFormat("MMMM yyyy");
	DateFormat urlArchiveFormatter = new SimpleDateFormat("yyyy/MM");
	DateFormat urlArchiveFullFormatter = new SimpleDateFormat("yyyy/MM/dd");
%>



<h1>
	<%
		if(null != tagName) {
	%>
				<a href="<%=absoluteURL%>/blog/tags" title="Tags">Tags</a>&nbsp;&rsaquo;&nbsp;<%=tagName%>
			<%
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
			if(null != tagName) {
				
				OperationResult<List<IPost>> postsResult = BlogManager.searchPosts(tagName, null, null, null, 100, Blogs.OfficialInqwiseBlog);
				if(!postsResult.hasError()){
					List<IPost> posts = postsResult.getValue();
					for(IPost post : posts){
		%>
							<div style="padding-bottom: 24px; clear: both">
							<div>
								<span class="light"><%=displayDateFormatter.format(post.getPostDate())%></span>
							</div>
							<div style="padding-top: 12px;">
								<h3><a href="<%=absoluteURL + "/blog/" + urlArchiveFullFormatter.format(post.getPostDate()) + "/" + post.getUrlTitle()%>" title="<%=post.getTitle()%>"><%=post.getTitle()%></a></h3>
								<div>
									<%=post.getContent()%>
									<p><br/></p>
									<div class="fb-like" data-href="<%=absoluteURL + "/blog/" + urlArchiveFullFormatter.format(post.getPostDate()) + "/" + post.getUrlTitle()%>" data-send="false" data-layout="button_count" data-width="450" data-show-faces="false" data-font="tahoma"></div>
									<!-- <p>Under category:</p> -->
									<p>Tags: 
									<%
										StringBuilder sbTagsHtml = new StringBuilder();
																	for(String tag : post.getTags()){
																		sbTagsHtml.append("<a href=\"");
																		sbTagsHtml.append(absoluteURL);
																		sbTagsHtml.append("/blog/tags/");
																		sbTagsHtml.append(URLEncoder.encode(tag, "UTF-8"));
																		sbTagsHtml.append("\" title=\"");
																		sbTagsHtml.append(tag);
																		sbTagsHtml.append("\">");
																		sbTagsHtml.append(tag);
																		sbTagsHtml.append("</a>, ");
																	}
																	
																	sbTagsHtml.delete(sbTagsHtml.length()-2, sbTagsHtml.length());
									%>
										 
									<%=sbTagsHtml.toString()%>
									</p>
									
									<div>
										<em><a href="<%=absoluteURL + "/blog/" + urlArchiveFullFormatter.format(post.getPostDate()) + "/" + post.getUrlTitle()%>#comments" title="<%=post.getCountOfComments()%> Comment<%=post.getCountOfComments() == 1 ? "" : "s"  %>"><%=post.getCountOfComments()%> Comment<%=post.getCountOfComments() == 1 ? "" : "s"  %></a></em>
									</div>
								</div>
							</div>
						</div>
						
						<%
													}
														}
														
													} else {
														
														OperationResult<List<ITag>> tagsResult = TagsManager.getBlogTags(Blogs.OfficialInqwiseBlog);
														if(!tagsResult.hasError()){
															List<ITag> tags = tagsResult.getValue();
															for(ITag tag : tags){
												%>
							<a href="<%=absoluteURL%>/blog/tags/<%=URLEncoder.encode(tag.getName(), "UTF-8")%>" title="<%=tag.getName()%>"><%=tag.getName()%></a>
						<%
							}
								}
								
							}
						%>
		&nbsp;
	</div>
	<div class="wrapper-content-middle">
		<h3 class="ui-header">Recent Posts</h3>
		<div style="Xpadding: 12px 0 8px 8px;">
			<ul class="ll">
<%
	OperationResult<List<IPost>> postsResult = BlogManager.getPosts(10, Blogs.OfficialInqwiseBlog);
if(!postsResult.hasError()){
	List<IPost> posts = postsResult.getValue();
	for(IPost recentPost : posts){
%>
				<li><a href="<%=absoluteURL + "/blog/" + urlArchiveFullFormatter.format(recentPost.getPostDate()) + "/" + recentPost.getUrlTitle()%>" title="<%=recentPost.getTitle()%>" class="arrow-grey"><%=recentPost.getTitle()%></a></li>
<%
	}
}
%>				
			</ul>
		</div>
		<h3 class="ui-header">Categories</h3>
		<div style="Xpadding: 12px 0 0 8px;">
			<ul class="ll">
<%
	OperationResult<List<ICategory>> categoriesResult = CategoriesManager.getCategories(Blogs.OfficialInqwiseBlog);
if(!categoriesResult.hasError()){
	List<ICategory> categories = categoriesResult.getValue();
	for(ICategory category : categories){
%>
				<li><a href="<%=absoluteURL%>/blog/categories/<%=URLEncoder.encode(category.getName(), "UTF-8")%>" title="<%=category.getName()%>" class="arrow-grey"><%=category.getName()%></a></li>
<%
	}
}
%>
			</ul>
		</div>
		<h3 class="ui-header">Tags</h3>
		<div style="padding: 12px 0 24px 8px;">
			<ul class="lf">
<%
	OperationResult<List<ITag>> tagsResult = TagsManager.getBlogTags(Blogs.OfficialInqwiseBlog);
if(!tagsResult.hasError()){
	List<ITag> tags = tagsResult.getValue();
	for(ITag tag : tags){
%>
		<li class="tag-<%=tag.getCountOfLinkedItems()%>"><a href="<%=absoluteURL%>/blog/tags/<%=URLEncoder.encode(tag.getName(), "UTF-8")%>" title="<%=tag.getName()%>"><%=tag.getName()%></a></li>
<%
	}
}
%>
			</ul>
		</div>
		<h3 class="ui-header">Archives</h3>
		<div style="Xpadding: 12px 0 0 8px;">
			<ul class="ll">
<%
	if(!postsResult.hasError()){
	List<IPost> posts = postsResult.getValue();
	LinkedHashSet<Date> hs = new LinkedHashSet<Date>();
	Dictionary<Date, Integer> countsSet = new Hashtable<Date, Integer>();
	
	for(IPost post : posts){
		Date date = DateUtils.truncate(post.getPostDate(), Calendar.MONTH);
		if(hs.add(date)){
	countsSet.put(date, 1);
		} else {
	countsSet.put(date, countsSet.get(date) + 1);
		}
	}
	for(Date date : hs){
		String formattedDate = archiveFormatter.format(date);
%>
				<li><a href="<%=absoluteURL %>/blog/<%=urlArchiveFormatter.format(date) %>" title="<%=formattedDate %> (<%=countsSet.get(date) %>)" class="arrow-grey"><%=formattedDate %> (<%=countsSet.get(date) %>)</a></li>
<%
	}
}
%>
			</ul>
		</div>
	</div>
	<div class="wrapper-content-right">
		<h3 class="ui-header">Search</h3>
		<div style="padding: 12px 0 24px 8px;">
			<div class="row">
				<div class="cell">
					<input id="input_search" type="text" class="text-field" style="width: 144px;" placeholder="Search this Blog" onkeydown="catchSearch(event)" />
				</div>
				<div class="cell" style="padding-left: 6px;">
					<a class="button-blue" title="Search" href="javascript:validateSearch();"><span>Search</span></a>
				</div>
			</div>
		</div>
		
		<h3 class="ui-header">Subscribe to RSS</h3>
		<div style="padding: 12px 0 24px 8px;">
			Subscribing to RSS feeds allows you to receive custom, up-to-date information from Inqwise Blog.
			<p><a href="<%=absoluteURL %>/blog/rss" title="RSS" class="rss">RSS</a></p>
		</div>
	</div>
</div>

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id; js.async = true;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
<script type="text/javascript">
var catchSearch = function(e){var code;if(!e)var e=window.event;if(e.keyCode)code=e.keyCode;else if(e.which)code=e.which;if(code==13)validateSearch();}
var validateSearch = function(){
	var q = $.trim($('#input_search').val());
	if(q.length>0) {
		location.href="<%=absoluteURL %>/blog/search/" + escape(q);
	}
};
</script>