
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
	<h1><a href="<%=absoluteURL%>/blog-posts" title="Posts">Posts</a>&nbsp;&rsaquo;&nbsp;Post</h1>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/blog-post?post_id=<%=postId %>" class="selected" title="Edit"><span>Edit</span></a></li>
					<li><a href="<%=absoluteURL %>/blog-post-comments?post_id=<%=postId %>" title="Comments"><span>Comments</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
		<div style="padding-top: 20px;">
			<div class="params">
				<div class="param-name">* Title:</div>
				<div class="param-value">
					<input id="text_title" name="title" type="text" autocomplete="off" />
					<div><label id="status_title"></label></div>
				</div>
			</div>
			<div class="params">
				<div class="param-name">* URI:</div>
				<div class="param-value">
					<input id="text_uri" name="uri" type="text" autocomplete="off" />
					<div><label id="status_uri"></label></div>
				</div>
			</div>
			<div class="params">
				<div class="param-name">* Post Date:</div>
				<div class="param-value">
					<input id="text_post_date" name="post_date" type="text" autocomplete="off" />
					<div><label id="status_post_date"></label></div>
				</div>
			</div>
			<div class="params" style="height: 192px;">
				<div class="param-name">Content:</div>
				<div class="param-value">
					<textarea id="textarea_content" style="width: 314px; height: 184px;" autocomplete="off"></textarea>
				</div>
			</div>
			<div class="params">
				<div class="param-name">* Categories:</div>
				<div class="param-value">
					...
				</div>
			</div>
			<div class="params">
				<div class="param-name">* Tags:</div>
				<div class="param-value">
					...
				</div>
			</div>
			<div class="params">
				<div class="param-name">Modify Date:</div>
				<div class="param-value">
					<span id="label_modify_date"></span>
				</div>
			</div>
			<div class="params">
				<div class="param-name">Status:</div>
				<div class="param-value">
					<div class="ui-form"><label><span><input id="checkbox_active" type="checkbox" /></span>Active</label></div>
				</div>
			</div>
			<div class="params" style="padding-top: 20px;">
				<div class="param-name"></div>
				<div class="param-value">
					<a href="#" class="button-blue"><span>Save Changes</span></a>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
var getPost = function(params) {
	
	var obj = {
		blogs : {
			getPost : {
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
        	if(data.blogs.getPost != undefined) {
	        	if(data.blogs.getPost.error != undefined) {
	        		errorHandler({
						error : data.blogs.getPost.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.blogs.getPost);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.blogs.getPost);
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

var updatePost = function(params) {
	
	var obj = {
		blogs : {
			updatePost : {
				postId : params.postId,
				title : params.title,
				urlTitle : params.url,
				content : params.content,
				postDate : params.postDate,
				isActive : params.isActive,
				tags : params.tags,
				categories : params.categories
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
        	if(data.blogs.searchPosts != undefined) {
	        	if(data.blogs.searchPosts.error != undefined) {
	        		errorHandler({
						error : data.blogs.searchPosts.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.blogs.searchPosts);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.blogs.searchPosts);
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

var postId = <%=postId%>;
$(function() {
	
	getPost({
		postId : postId,
		success : function(data) {
			
			console.log(JSON.stringify(data));
			
			$('#text_title').val(data.title);
			$('#text_uri').val(data.urlTitle);
			$('#text_post_date').val(data.postDate);
			$('#textarea_content').val(data.content);
			
			
			console.log(data.tags);
			console.log(data.categories);
			
			$('#label_modify_date').text(data.modifyDate);
			
		},
		error: function(error) {
			console.log(JSON.stringify(error));
		}
	});
	
});
</script>