
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.inqwise.opinion.cms.common.IPage"%>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<h1><a href="<%=absoluteURL%>/articles" title="KB Articles">KB Articles</a>&nbsp;&rsaquo;&nbsp;<%=p.getHeader() %></h1>
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
		<div class="param-name">* Topic:</div>
		<div class="param-value">
			<select id="select_topics" autocomplete="off"></select>
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
			<a id="button_submit" href="javascript:;" class="button-blue"><span>Submit</span></a>
		</div>
		<div class="param-value">
			<a style="margin-left: 6px;" title="Cancel" href="articles">Cancel</a>
		</div>
	</div>
</div>

<script type="text/javascript">
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

var setArticle = function(params) {

	var obj = {
		kb : {
			setArticle : {
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
        	if(data.kb.setArticle != undefined) {
	        	if(data.kb.setArticle.error != undefined) {
	        		errorHandler({
						error : data.kb.setArticle.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.setArticle);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.setArticle);
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
	
	// fill topics
	getTopics({
		success : function(data) {
			if(data.list) {
				
				var selectTopics = $("#select_topics");
				_.each(data.list, function(topic) {
					$("<option value=\"" + topic.id + "\">" + topic.name + "</option>").appendTo(selectTopics);
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
			
			setArticle({
				title : $.trim($('#text_title').val()),
				uri : $.trim($('#text_uri').val()),
				content : $.trim($('#textarea_content').val().replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")),
				/*topics : [parseInt($('#select_topics option:selected').val())],*/
				topicId : parseInt($('#select_topics option:selected').val()),
				popular : $('#checkbox_popular').prop('checked'),
				active : $('#checkbox_active').prop('checked'),
				success : function(data) {
					location.href = "articles";
				},
				error: function(error) {
					console.log(error);
				}
			});
			
			
		}
	});
	
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
	
});
</script>