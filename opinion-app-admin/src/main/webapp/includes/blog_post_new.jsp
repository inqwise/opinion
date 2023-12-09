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

<h1><a href="<%=absoluteURL%>/blog-posts" title="Posts">Posts</a>&nbsp;&rsaquo;&nbsp;<%=p.getHeader() %></h1>
<div>
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
	<div class="params" style="height: 192px;">
		<div class="param-name">Content:</div>
		<div class="param-value">
			<textarea id="textarea_content" style="width: 314px; height: 184px;" autocomplete="off"></textarea>
		</div>
	</div>
	<div class="params">
		<div class="param-name">* Categories:</div>
		<div class="param-value">
			<select id="select_categories" autocomplete="off"></select>
		</div>
	</div>
	<div class="params">
		<div class="param-name">* Tags:</div>
		<div class="param-value">
			<select id="select_tags" autocomplete="off"></select>
		</div>
	</div>
	<div class="params">
		<div class="param-name">Status:</div>
		<div class="param-value">
			<div class="ui-form"><label><span><input id="checkbox_active" type="checkbox" checked="checked" /></span>Active</label></div>
		</div>
	</div>
	<div class="params" style="padding-top: 20px;">
		<div class="param-name"></div>
		<div class="param-value">
			<a id="button_submit" href="javascript:;" class="button-blue"><span>Submit</span></a>
		</div>
	</div>
</div>

<script type="text/javascript">
var getCategories = function(params) {
	
	var obj = {
		blogs : {
			getCategories : {
				
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
        	if(data.blogs.getCategories != undefined) {
	        	if(data.blogs.getCategories.error != undefined) {
	        		errorHandler({
						error : data.blogs.getCategories.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.blogs.getCategories);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.blogs.getCategories);
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

var getTags = function(params) {
	
	var obj = {
		blogs : {
			getTags : {
				
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
        	if(data.blogs.getTags != undefined) {
	        	if(data.blogs.getTags.error != undefined) {
	        		errorHandler({
						error : data.blogs.getTags.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.blogs.getTags);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.blogs.getTags);
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

var createPost = function(params) {

	var obj = {
		blogs : {
			createPost : {
				title : params.title,
				urlTitle : params.uri,
				content : params.content,
				categories : params.categories,
				tags : params.tags,
				isActive : params.isActive
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
        	if(data.blogs.createPost != undefined) {
	        	if(data.blogs.createPost.error != undefined) {
	        		errorHandler({
						error : data.blogs.createPost.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.blogs.createPost);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.blogs.createPost);
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

$(document).ready(function() {

	function defaultFocus() {
		$('#text_title').focus();
	}
	
	defaultFocus();
	
	// fill categories
	getCategories({
		success : function(data) {
			if(data.list) {
				
				var selectCategories = $("#select_categories");
				_.each(data.list, function(item) {
					$("<option value=\"" + item.id + "\">" + item.name + "</option>").appendTo(selectCategories);
				});
				
			}
		},
		error: function(error) {
			
		}
	});
	
	// fill tags
	getTags({
		success : function(data) {
			if(data.list) {
				
				var selectTags = $("#select_tags");
				_.each(data.list, function(item) {
					$("<option value=\"" + item.id + "\">" + item.name + "</option>").appendTo(selectTags);
				});
				
			}
		},
		error: function(error) {
			
		}
	});
	
	// validator
	var v = null;
	v = new validator({
		elements : [
			{
				element : $('#text_title'),
				status : $('#status_title'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				] 
			},
			{
				element : $('#text_uri'),
				status : $('#status_uri'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				]
			}
		],
		submitElement : $('#button_submit'),
		messages : null,
		accept : function () {
			
			createPost({
				title : $.trim($('#text_title').val()),
				uri : $.trim($('#text_uri').val()),
				content : $.trim($('#textarea_content').val().replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")),
				categories : [1,2], // [parseInt($('#select_categories option:selected').val())], // array
				tags : [23,8], // [parseInt($('#select_tags option:selected').val())], // arrays
				isActive : $('#checkbox_active').prop('checked'),
				success : function(data) {
					
					console.log(data);
					
					location.href = "blog-posts";
					
				},
				error: function(error) {
					console.log(error);
				}
			});
			
			
		}
	});
	
	function buildUri(str) {
		return str.replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '')
				  .replace(/ +/gi, '-')
				  .replace(/^_+/, '')
				  .replace(/_+$/, '')
				  .toLowerCase();
	}
	
	$('#text_title').on('keyup', function() {
		var that = $(this);
		$('#text_uri').val(buildUri(that.val()));
	});
	
	
});
</script>