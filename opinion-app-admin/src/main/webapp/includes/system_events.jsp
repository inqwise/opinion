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
	<h1><a href="<%=absoluteURL %>/setup" title="Setup">Setup</a>&nbsp;&rsaquo;&nbsp;System Events</h1>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/setup" title="?----"><span>?----</span></a></li>
					<li><a href="<%=absoluteURL %>/jobs" title="Jobs"><span>Jobs</span></a></li>
					<li><a href="<%=absoluteURL %>/system-events" title="System Events" class="selected"><span>System Events</span></a></li>
					<li><a href="<%=absoluteURL %>/plans" title="Plans"><span>Plans</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both;padding-top: 20px;">
		<div id="table_system_events"></div>
	</div>
</div>

<script type="text/javascript">
var getSystemEvents = function(params) {
	var obj = {
		control : {
			getSystemEvents : {}
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
        	if(data.control.getSystemEvents.error != undefined) {
				
        		errorHandler({
					error : data.control.getSystemEvents.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.control.getSystemEvents);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.control.getSystemEvents);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var tableSystemEvents = null;
var systemEvents = [];
var renderTableSystemEvents = function() {
	
	$('#table_system_events').empty();
	tableSystemEvents = $('#table_system_events').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'name', label : 'Event Name', width: 220, sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/system-event?event_id=" + record.id  + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key : 'description', label : 'Description', sortable : true },
			{ key : 'recipients', label : 'Recipients', sortable : true },
            { key : 'modifyDate', label : 'Modify Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.modifyDate) {
					var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
					var right = record.modifyDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : systemEvents, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};


$(function() {
	
	getSystemEvents({
		success : function(data) {
			systemEvents = data.list;
			renderTableSystemEvents();
		},
		error : function() {
			systemEvents = [];
			renderTableSystemEvents();
		}
	});
	
});

</script>