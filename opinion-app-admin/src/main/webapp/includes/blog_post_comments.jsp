
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.opinion.cms.common.IPage"%>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String postId = request.getParameter("post_id");
	
%>

<div>
	<h1><a href="<%=absoluteURL%>/blog-posts" title="Posts">Posts</a>&nbsp;&rsaquo;&nbsp;Comments</h1>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/blog-post?post_id=<%=postId %>" title="Edit"><span>Edit</span></a></li>
					<li><a href="<%=absoluteURL %>/blog-post-comments?post_id=<%=postId %>" class="selected" title="Comments"><span>Comments</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
		<div style="padding-top: 20px;">
			<div id="table_post_comments"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
var getComments = function(params) {
	
	var obj = {
		blogs : {
			getComments : {
				postId : params.postId
			}
		}
	};
	
	$.ajax({
        url: servletUrl,
        data: { 
        	rq : JSON.stringify(obj),
        	timestamp : $.getTimestamp()  
        },
        dataType: "json",
        success: function (data, status) {
        	if(data.blogs.getComments != undefined) {
	        	if(data.blogs.getComments.error != undefined) {
	        		errorHandler({
						error : data.blogs.getComments.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.blogs.getComments);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.blogs.getComments);
					}
				}
        	} else {
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error();
				}
        	}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var tablePostComments = null;
var postComments = [];
var renderTablePostComments = function() {
	$('#table_post_comments').empty();
	tablePostComments = $('#table_post_comments').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'comment_content', label : 'Content' },
			{ key : 'author_name', label : 'Author Name' },
			{ key : 'author_email', label : 'Author Email' },
			{ key : 'is_active', label : 'Status' },
			{ key : 'commentDate', label : 'Comment Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.commentDate) {
					var left = record.commentDate.substring(record.commentDate.lastIndexOf(" "), " ");
					var right = record.commentDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : postComments, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {

					console.log("delete comments");
									
				}
			}
		]
	});
};

var postId = <%=postId%>;
$(function() {
	
	getComments({
		postId : postId,
		success : function(data) {
			
			console.log(JSON.stringify(data));
			
			postComments = data.list;
			renderTablePostComments();
		},
		error: function(error) {
			
			console.log(JSON.stringify(error));
			
			postComments = [];
			renderTablePostComments();
			
		}
	});
	
});

</script>