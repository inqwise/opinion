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
	
	String eventId = request.getParameter("event_id");
%>

<div>
	<h1><a href="<%=absoluteURL %>/setup" title="Setup">Setup</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/system-events" title="System Events">System Events</a>&nbsp;&rsaquo;&nbsp;<span id="header_event_name"></span></h1>
	<div style="clear: both;">
		
		<div class="params">
			<div class="param-name">Event Id:</div>
			<div class="param-value">
				<span id="label_event_id" style="color: #000"></span>
			</div>
		</div>
		<div class="params">
			<div class="param-name">Event Name:</div>
			<div class="param-value">
				<span id="label_event_name" style="color: #000"></span>
			</div>
		</div>
		<div class="params" style="height: 72px;">
			<div class="param-name">Description:</div>
			<div class="param-value">
				<textarea style="width: 314px; height: 64px;" maxlength="1000" autocomplete="off" name="description" id="textarea_description"></textarea>
			</div>
		</div>
		<div class="params" style="height: 72px;">
			<div class="param-name">Recipients:</div>
			<div class="param-value">
				<textarea style="width: 314px; height: 64px;" maxlength="1000" spellcheck="false" autocomplete="off" name="recipients" id="textarea_recipients"></textarea>
			</div>
		</div>
		<div class="params">
			<div class="param-name">Modify Date:</div>
			<div class="param-value">
				<span id="label_modify_date" style="color: #000"></span>
			</div>
		</div>
		
		<div style="height: 24px; overflow: hidden;"></div>
		<div class="params">
			<div class="param-name">&nbsp;</div>
			<div class="param-value">
				<a id="button_update" title="Update" class="button-blue" href="javascript:;"><span>Update</span></a>
			</div>
		</div>
		
	</div>
</div>

<script type="text/javascript">
var getSystemEventDetails = function(params) {

	var obj = {
		control : {
			getSystemEventDetails : {
				eventId : params.eventId
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
			if(data.control.getSystemEventDetails != undefined) {
				if(data.control.getSystemEventDetails.error != undefined) {
					
					errorHandler({
						error : data.control.getSystemEventDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.control.getSystemEventDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.control.getSystemEventDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var updateSystemEvent = function(params) {

	var obj = {
		control : {
			updateSystemEvent : {
				eventId : params.eventId,
				description : params.description,
				recipients : params.recipients
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
			if(data.control.updateSystemEvent != undefined) {
				if(data.control.updateSystemEvent.error != undefined) {
					
					errorHandler({
						error : data.control.updateSystemEvent.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.control.updateSystemEvent);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.control.updateSystemEvent);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var eventId = <%= eventId %>;

$(function() {
	
	getSystemEventDetails({
		eventId : eventId,
		success : function(data) {
			
			$('#label_event_id').text(data.id);
			$('#label_event_name, #header_event_name').text(data.name);
			$('#textarea_description').val(data.description);
			$('#textarea_recipients').val(data.recipients);
			$('#label_modify_date').text(data.modifyDate);
			
			$('#button_update').click(function() {
				updateSystemEvent({
					eventId : eventId,
					description : $('#textarea_description').val(),
					recipients : $('#textarea_recipients').val(),
					success : function(data) {
						
						var modal = new lightFace({
							title : "Changes saved.",
							message : "Your changes were successfully saved.",
							actions : [
							           { label : "OK", fire : function() { modal.close(); }, color : "blue" }
							],
							overlayAll : true
						});
						
					},
					error : function(error) {
						alert(error);
					}
				});
			});
			
			
		},
		error : function(error) {
			alert(error.error + " " + error.errorDescription)
		}
	});
	
});
</script>