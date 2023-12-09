<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="com.opinion.cms.managers.BlogManager" %>
<%@ page import="com.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.opinion.cms.common.blog.IPost" %>
<%@ page import="com.opinion.cms.common.ITag" %>
<%@ page import="com.opinion.cms.common.ICategory" %>
<%@ page import="com.opinion.cms.managers.TagsManager" %>
<%@ page import="com.opinion.cms.managers.CategoriesManager" %>
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
<%@page import="java.text.ParseException"%>
<%@page import="com.opinion.cms.common.IPage"%>
<%@page import="com.opinion.cms.common.blog.IComment"%>
<%@page import="com.opinion.cms.common.blog.Blogs"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	String strArchiveDate = StringUtils.trimToNull(request.getParameter("date"));
	Format displayDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
	Format displayDateAndTimeFormatter = new SimpleDateFormat("MMM dd, yyyy HH:mm");
	Integer archiveTotalMonth = null;
	
	DateFormat archiveFormatter = new SimpleDateFormat("MMMM yyyy");
	DateFormat urlArchiveFormatter = new SimpleDateFormat("yyyy/MM");
	DateFormat urlArchiveFullFormatter = new SimpleDateFormat("yyyy/MM/dd");
	if(null != strArchiveDate){
		try{
	Calendar archiveDate = Calendar.getInstance();
	archiveDate.setTime(urlArchiveFullFormatter.parse(strArchiveDate));
	archiveTotalMonth = archiveDate.get(Calendar.YEAR) * 12 + archiveDate.get(Calendar.MONTH) + 1;
		} catch(ParseException ex) {}
	}
	
	
	
	String postUrlTitle = request.getParameter("name");
	OperationResult<IPost> postResult = BlogManager.getPostByUrlTitle(postUrlTitle, archiveTotalMonth);
	IPost post = null;
	if(!postResult.hasError()){
		post = postResult.getValue();
	}
%>

<%
	if(!postResult.hasError()){
		p.setWindowTitle(post.getTitle());
%>


<h1 style="width: 450px;"><%=post.getTitle()%></h1>
<%
	}
%>
<div>
	<div class="wrapper-content-left">
<%
	if(!postResult.hasError()){
%>
		<div style="clear: both">
			<div>
				<span class="light"><%=displayDateFormatter.format(post.getPostDate())%></span>
			</div>
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
				<p><br/></p>
				
				<a name="comments"></a>
				<div>
					<h3 class="ui-header-light"><%=post.getCountOfComments()%> Comment<%=post.getCountOfComments() == 1 ? "" : "s"  %>:</h3>
					<div style="padding-top: 12px;">
					<%
					if(post.getCountOfComments() > 0){
						OperationResult<List<IComment>> commentsResult = BlogManager.getComments(post.getId());
						if(!commentsResult.hasError()){
							List<IComment> comments = commentsResult.getValue();
							for(IComment comment : comments){
								%>
								<div style="clear: both; padding-bottom: 24px;">
									<div><%= ((null != comment.getAutorUrl() && comment.getAutorUrl().length() != 0) ? "<a href=\"" + comment.getAutorUrl() + "\" title=\"" + comment.getAutorName() + "\" target=\"_blank\"><b>" + comment.getAutorName() + "</b></a>" : "<b style=\"color: #333\">" + comment.getAutorName() + "</b>" ) %> says:</div>
									<div style="padding-bottom: 4px;"><span class="light"><%= displayDateAndTimeFormatter.format(comment.getCommentDate()) %></span></div>
									<div class="comment">
										<div class="comment-top"></div>
										<label><%=comment.getContent() %></label>
									</div>
								</div>	
								<%
							}
						}
					}
					%>
					</div>
				</div>
			</div>
		</div>
		
		<div style="padding-top: 24px;">
			<div id="form_post">
				<h3 class="ui-header-light">Leave a Comment</h3>
				<div style="padding-bottom: 14px;">
					<p>Your email address will not be published.<br/>Comment moderation is enabled. Your comment may take some time to appear.</p>
					<p>Fields indicated with an * are required.</p>
				</div>
				<div>
					<div class="row">
						<div class="cell" style="width: 130px;"><span>* Name:</span></div>
						<div class="cell">
							<div><input id="input_name" name="input-name" type="text" maxlength="50" autocomplete="off" /></div>
							<div><label id="status_input_name"></label></div>
						</div>
					</div>
					<div class="row">
						<div class="cell" style="width: 130px;"><span>* Email:</span></div>
						<div class="cell">
							<div><input id="input_email" name="input-email" type="text" style="width: 200px;" maxlength="50" autocomplete="off" /></div>
							<div><label id="status_input_email"></label></div>
						</div>
					</div>
					<div class="row">
						<div class="cell" style="width: 130px;"><span>Website:</span></div>
						<div class="cell">
							<div><input id="input_website_url" name="input-website-url" type="text" style="width: 200px;" maxlength="50" autocomplete="off" /></div>
							<div><label id="status_input_website_url"></label></div>
						</div>
					</div>
					<div class="row" style="height: 72px;">
						<div class="cell" style="width: 130px;"><span>* Comment:</span></div>
						<div class="cell">
							<div>
								<textarea id="textarea_comment" name="textarea-comment" autocomplete="off" maxlength="255" style="width: 314px; height: 64px;"></textarea>
							</div>
							<div><label id="status_textarea_comment"></label></div>
						</div>
					</div>
				</div>
				<div style="height: 24px; overflow: hidden; clear: both;"></div>
				<div class="row">
					<div class="cell" style="width: 130px;">
						
					</div>
					<div class="cell">
						<a href="javascript:;" class="button-white" title="Post Comment" id="button_post_comment"><span>Post Comment</span></a>
					</div>				
				</div>
			</div>
			<div id="form_accept" style="display: none;">
				<h2>Thank you for your comment!</h2>
				<p>Your comment is awaiting moderation.</p>
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
		var loader = null;
		
		$(function() {

			loader = new lightLoader();
			
			$("#input_website_url").urlInputBox();
			
			new validator({
				elements : [
		            {
						element : $('#input_name'),
						status : $('#status_input_name'),
						rules : [
							{ method : 'required', message : 'This field is required.' }
						]
			        },
			        {
						element : $('#input_email'),
						status : $('#status_input_email'),
						rules : [
							{ method : 'required', message : 'This field is required.' },
							/* { method : 'emailISO', message : 'Please enter a valid email address.' } */
							{ method : 'email', message : 'Please enter a valid email address.' }
						]
			        },
			        {
						element : $('#input_website_url'),
						status : $('#status_input_website_url'),
						rules : [
							{ method : 'url', message : 'Please enter a valid url.' }
						],
						validate : function() {
							return ($('#input_website_url').val().length != 0);
						}
			        },
			        {
						element : $('#textarea_comment'),
						status : $('#status_textarea_comment'),
						rules : [
							{ method : 'required', message : 'This field is required.' }
						]
			        }
				],
				submitElement : $('#button_post_comment'),
				messages : null,
				accept : function () {
					// loader
					loader.show();
					var obj = {
						postComment : {
							name : $('#input_name').val(),
							email : $('#input_email').val(),
							websiteUrl : $('#input_website_url').val(),
							comment : $('#textarea_comment').val(),
							postId : <%=post.getId()%>
						}
					};

					$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
						loader.hide();
						if(data.postComment.error != undefined) {
							
						} else {

							$('#form_post').hide();
							$('#form_accept').show();
							
						}
					});	
				}
				
			});

						
		});
		
		</script>
		
		
<%
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
	
	for(IPost _post : posts){
		Date date = DateUtils.truncate(_post.getPostDate(), Calendar.MONTH);
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

<script type="text/javascript">
var catchSearch = function(e){var code;if(!e)var e=window.event;if(e.keyCode)code=e.keyCode;else if(e.which)code=e.which;if(code==13)validateSearch();}
var validateSearch = function(){
	var q=$('#input_search').val();
	if(q.length>0) {
		location.href="<%=absoluteURL %>/blog/search/" + escape(q);
	}
};
</script>