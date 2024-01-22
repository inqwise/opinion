
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String topicId = request.getParameter("topic_id");
	
%>

<h1><a href="<%=absoluteURL %>/topics" title="Topics">Topics</a>&nbsp;&rsaquo;&nbsp;Topic</h1>
<div>
	<div class="params">
		<div class="param-name">* Topic:</div>
		<div class="param-value">
			<input id="text_topic_name" name="topic_name" type="text" autocomplete="off" />
			<div><label id="status_topic_name"></label></div>
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
	<div class="params" style="padding-top: 20px;">
		<div class="param-name"></div>
		<div class="param-value">
			<a id="button_submit" href="javascript:;" class="button-blue"><span>Update</span></a>
		</div>
		<div class="param-value">
			<a style="margin-left: 6px;" title="Cancel" href="topics">Cancel</a>
		</div>
	</div>
</div>

<script type="text/javascript">
var getTopic = function(params) {
	
	var obj = {
		kb : {
			getTopic : {
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
        	if(data.kb.getTopic != undefined) {
	        	if(data.kb.getTopic.error != undefined) {
	        		errorHandler({
						error : data.kb.getTopic.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.getTopic);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.getTopic);
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

var updateTopic = function(params) {
	var obj = {
		kb : {
			updateTopic : {
				id : params.id,
				name : params.name,
				uri : params.uri
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
        	if(data.kb.updateTopic != undefined) {
	        	if(data.kb.updateTopic.error != undefined) {
	        		errorHandler({
						error : data.kb.updateTopic.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.updateTopic);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.updateTopic);
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

var topicId = <%=topicId%>;
$(document).ready(function() {
	
	function defaultFocus() {
		$('#text_topic_name').focus();
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
	
	$('#text_topic_name').on('keyup', function() {
		var that = $(this);
		$('#text_uri').val(buildUri(that.val()));
	});
	
	getTopic({
		id : topicId,
		success : function(data) {
			
			$('#text_topic_name').val(data.name);
			$('#text_uri').val(data.uri);
			$('#label_create_date').text(data.createDate);
			$('#label_modify_date').text(data.modifyDate);
			
			// validator
			var v = null;
			v = new validator({
				elements : [
					{
						element : $('#text_topic_name'),
						status : $('#status_topic_name'),
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
					
					updateTopic({
						id : topicId,
						name : $.trim($('#text_topic_name').val()),
						uri : $.trim($('#text_uri').val()),
						success : function(data) {
							location.href = "topics";
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