
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%
	
	String articleId = request.getParameter("article_id");

	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>


<h1><a href="<%=absoluteURL %>/articles" title="KB Articles">KB Articles</a>&nbsp;&rsaquo;&nbsp;Article</h1>
<div class="content-middle-tabs-section">
	<div class="content-middle-tabs">
		<ul class="content-middle-tabs-container">
			<li><a href="<%=absoluteURL %>/article?article_id=<%=articleId %>" class="selected" title="Edit"><span>Edit</span></a></li>
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
	<div class="params" style="height: 192px;">
		<div class="param-name">Content:</div>
		<div class="param-value">
			<textarea id="textarea_content" style="width: 314px; height: 184px;" autocomplete="off"></textarea>
		</div>
	</div>
	<div class="params">
		<div class="param-name">* Topic:</div>
		<div class="param-value">
			<select id="select_topics" autocomplete="off"></select>
		</div>
	</div>
	<div class="params">
		<div class="param-name">Create Date:</div>
		<div class="param-value">
			<span id="label_create_date"></span>
		</div>
	</div>
	<div class="params">
		<div class="param-name">Modify Date:</div>
		<div class="param-value">
			<span id="label_modify_date"></span>
		</div>
	</div>
	<div class="params">
		<div class="param-name">Popular:</div>
		<div class="param-value">
			<div class="ui-form"><label><span><input id="checkbox_popular" type="checkbox" /></span>Popular</label></div>
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
			<a id="button_submit" href="javascript:;" class="button-blue"><span>Update</span></a>
		</div>
		<div class="param-value">
			<a style="margin-left: 6px;" title="Cancel" href="articles">Cancel</a>
		</div>
	</div>
</div>

<script type="text/javascript">
var getArticle = function(params) {
	
	var obj = {
		kb : {
			getArticle : {
				id : params.id
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
        	if(data.kb.getArticle != undefined) {
	        	if(data.kb.getArticle.error != undefined) {
	        		errorHandler({
						error : data.kb.getArticle.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.getArticle);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.getArticle);
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

var updateArticle = function(params) {
	
	var obj = {
		kb : {
			updateArticle : {
				id : params.id,
				title : params.title,
				uri : params.uri,
				content : params.content,
				/*topics : params.topics,*/
				topicId : params.topicId,
				popular : params.popular,
				active : params.active
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
        	if(data.kb.updateArticle != undefined) {
	        	if(data.kb.updateArticle.error != undefined) {
	        		errorHandler({
						error : data.kb.updateArticle.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.updateArticle);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.updateArticle);
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

var getTopics = function(params) {
	
	var obj = {
		kb : {
			getTopics : {
				
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
        	if(data.kb.getTopics != undefined) {
	        	if(data.kb.getTopics.error != undefined) {
	        		errorHandler({
						error : data.kb.getTopics.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.getTopics);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.getTopics);
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

var articleId = <%=articleId%>;
$(document).ready(function() {
	
	function defaultFocus() {
		$('#text_title').focus();
	}
	
	defaultFocus();
	
	function buildUri(str) {
		return str.replace(/&/gi, "and")
				  .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '')
				  .replace(/ +/gi, '-')
				  .replace(/^_+/, '')
				  .replace(/_+$/, '')
				  .toLowerCase();
	}
	
	$('#text_title').on('keyup', function() {
		var that = $(this);
		$('#text_uri').val(buildUri(that.val()));
	});
	
	getArticle({
		id : articleId,
		success : function(data) {
			
			$('#text_title').val(data.title);
			$('#text_uri').val(data.uri); 
			$('#textarea_content').val(data.content);
			
			// topic
			// fill topics
			getTopics({
				success : function(topics) {
					if(topics.list) {
						
						var selectTopics = $("#select_topics");
						_.each(topics.list, function(topic) {
							$("<option value=\"" + topic.id + "\">" + topic.name + "</option>").appendTo(selectTopics);
						});
						
						$("#select_topics option[value=" + data.topicId + "]").attr("selected", "selected");
						
					}
				},
				error: function(error) {
					
				}
			});
			
			
			$('#label_create_date').text(data.createDate);
			$('#label_modify_date').text(data.modifyDate);
			$('#checkbox_popular').prop('checked', data.popular);
			$('#checkbox_active').prop('checked', data.active);
			
			
			
			// update
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
					
					updateArticle({
						id : articleId,
						title : $.trim($('#text_title').val()),
						uri : $.trim($('#text_uri').val()),
						content : $.trim($('#textarea_content').val().replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")),
						/*topics : [parseInt($('#select_topics option:selected').val())],*/
						topicId : parseInt($('#select_topics option:selected').val()),
						popular : $('#checkbox_popular').prop('checked'),
						active : $('#checkbox_active').prop('checked'),
						success : function(data) {
							
							var modal = new lightFace({
								title : "Changes saved.",
								message : "Your changes were successfully saved.",
								actions : [
								    { 
								    	label : "OK", 
								    	fire : function() { 
								    		modal.close(); 
								    	}, 
								    	color : "blue" 
								    }
								],
								overlayAll : true
							});
							
						},
						error: function(error) {
							console.log(error);
						}
					});
					
					
				}
			});
			
		},
		error: function(error) {
			console.log(error);
		}
	});
	
});
</script>