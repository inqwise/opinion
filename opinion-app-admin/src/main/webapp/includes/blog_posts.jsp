
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
%>

<div>
	<h1>Posts</h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/blog-posts" class="selected" title="Posts"><span>Posts</span></a></li>
						<li><a href="<%=absoluteURL %>/blog-categories" title="Categories"><span>Categories</span></a></li>
						<li><a href="<%=absoluteURL %>/blog-tags" title="Tags"><span>Tags</span></a></li>
						<li><a href="<%=absoluteURL %>/blog-comments" title="Comments"><span>Comments</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both;padding-top: 20px;">
			<div id="table_blog_posts"></div>
		</div>
	</div>
</div>

<script type="text/javascript">

var searchPosts = function(params) {
	
	var obj = {
		blogs : {
			searchPosts : {
				
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

var deletePosts = function(params) {
	
	var obj = {
		blogs : {
			deletePosts : {
				list : params.list
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
        	if(data.blogs.deletePosts != undefined) {
	        	if(data.blogs.deletePosts.error != undefined) {
	        		errorHandler({
						error : data.blogs.deletePosts.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.blogs.deletePosts);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.blogs.deletePosts);
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

var tableBlogPosts = null;
var blogPosts = [];
var renderTableBlogPosts = function() {
	$('#table_blog_posts').empty();
	tableBlogPosts = $('#table_blog_posts').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'title', label : 'Title', sortable: true, formatter : function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/blog-post?post_id=" + record.id  + "\" title=\"" + record.title + "\">" + record.title + "</a>");
			}},
			{ key : 'countOfComments', label : "Comments", formatter : function(cell, value, record, source) {
				return value != 0 ? $("<a href=\"<%=absoluteURL %>/blog-post-comments?post_id=" + record.id  + "\" title=\"" + record.countOfComments + "\">" + record.countOfComments + "</a>") : record.countOfComments;
			} },
			{ key : 'postDate', label : 'Post Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.postDate) {
					var left = record.postDate.substring(record.postDate.lastIndexOf(" "), " ");
					var right = record.postDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
            { key : 'modifyDate', label : 'Modify Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.modifyDate) {
					var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
					var right = record.modifyDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : blogPosts, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
		    {
		    	label : "New Post",
		    	icon : "add-white",
		    	color : "green",
		    	fire : function() {

                    location.href = "blog-post-new";
		    		
		    	}
		    },
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {

					// TODO: only for testing
					if(records.length > 0) {

						var list = [];
						$(records).each(function(index) {
							list.push(records[index].id);
						});
						
						var modal = new lightFace({
							title : "Deleting posts",
							message : "Are you sure you want to delete selected posts?",
							actions : [
								{ 
									label : "Delete", 
									fire : function() {
										
										console.log("deletePosts");
										
										/*
										deletePosts({
											list : list,
											success : function() {
												
												// reload
												
												modal.close();
												
											},
											error : function() {
												//
											}
										});
										*/
										
									},
									color: "blue"
								}, 
								{
									label : "Cancel",
									fire: function() {
										modal.close();
									},
									color: "white"
								}
							],
							overlayAll : true
						});
						

					}
					
				}
			}
		]
	});
};

$(function() {
	
	searchPosts({
		success : function(data) {
			//console.log(JSON.stringify(data));
			blogPosts = data.list;
			renderTableBlogPosts();
		},
		error: function(error) {
			console.log(JSON.stringify(error));
		}
	});
	
});
</script>