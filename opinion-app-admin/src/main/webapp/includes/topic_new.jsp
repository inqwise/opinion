
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<h1><a href="<%=absoluteURL%>/topics" title="Topics">Topics</a>&nbsp;&rsaquo;&nbsp;<%=p.getHeader() %></h1>
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
	<div class="params" style="padding-top: 20px;">
		<div class="param-name"></div>
		<div class="param-value">
			<a id="button_submit" href="javascript:;" class="button-blue"><span>Submit</span></a>
		</div>
	</div>
</div>
<script type="text/javascript">
var setTopic = function(params) {

	var obj = {
		kb : {
			setTopic : {
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
        	if(data.kb.setTopic != undefined) {
	        	if(data.kb.setTopic.error != undefined) {
	        		errorHandler({
						error : data.kb.setTopic.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.setTopic);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.setTopic);
					}
				}
        	} else {
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data);
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
			
			setTopic({
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
	
});
</script>