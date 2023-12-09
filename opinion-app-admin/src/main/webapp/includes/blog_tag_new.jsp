
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

<h1><a href="<%=absoluteURL%>/blog-tags" title="Tags">Tags</a>&nbsp;&rsaquo;&nbsp;<%=p.getHeader() %></h1>
<div>
	<div class="params">
		<div class="param-name">* Tag:</div>
		<div class="param-value">
			<input id="text_tag_name" name="tag_name" type="text" autocomplete="off" />
			<div><label id="status_tag_name"></label></div>
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
var createTag = function(params) {

	var obj = {
		blogs : {
			createTag : {
				tagName : params.tagName
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
        	if(data.blogs.createTag != undefined) {
	        	if(data.blogs.createTag.error != undefined) {
	        		errorHandler({
						error : data.blogs.createTag.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.blogs.createTag);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.blogs.createTag);
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
		$('#text_tag_name').focus();
	}
	
	defaultFocus();
	
	// validator
	var v = null;
	v = new validator({
		elements : [
			{
				element : $('#text_tag_name'),
				status : $('#status_tag_name'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				] 
			}
		],
		submitElement : $('#button_submit'),
		messages : null,
		accept : function () {
			
			createTag({
				tagName : $.trim($('#text_tag_name').val()),
				success : function(data) {
					location.href = "blog-tags";
				},
				error: function(error) {
					console.log(error);
				}
			});
			
		}
	});
	
});
</script>