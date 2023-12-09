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
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">			
				<h1>Collectors</h1>
			</td>
			<td class="cell-right">
				
			</td>
		</tr>
	</table>
	<div style="clear: both;">
		<div id="table_collectors"></div>
	</div>
</div>

<script type="text/javascript">

var getCollectors = function(params) {
	
	var obj = {
		collectors : {
			getCollectors : {
				top : 100,
				from : undefined,
				to : undefined,
				includeExpired : params.includeExpired
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
        	if(data.collectors.getCollectors != undefined) {
	        	if(data.collectors.getCollectors.error != undefined) {
					
	        		errorHandler({
						error : data.collectors.getCollectors.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.getCollectors);
					}
				} else {
					if(params.success != undefined  
							&& typeof params.success == 'function') {
						params.success(data.collectors.getCollectors);
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

var getCollectorType = function(typeId) {
	var s = "";
	switch(typeId) {
		case 1 :
			s = "Direct link";
			break;
		case 2 : 
			s = "CINT panel";
			break;
	}
	return s;
};

var getCollectorStatus = function(statusId, typeId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = (typeId == 1 ? "Open" : "The panel is live"); 
			break;
		case 2 : 
			s = (typeId == 1 ? "Closed" : "Panel has been completed");
			break;
		case 3 :
			s = "Awaiting payment";
			break;
		case 4 : 
			s = "Panel is being verified";
			break;
		case 5 :
			s = "The panel is live"; // 1
			break;
		case 6 : 
			s = "Panel has been completed"; // 2
			break;
		case 7 : 
			s = "Pending"; // Hold -> Pending
			break;
		case 8 : 
			s = "Canceled";
			break;
	}
	return s;
};

var tableCollectors = null;
var collectors = [];
var renderTableCollectors = function() {
	
	$('#table_collectors').empty();
	tableCollectors = $('#table_collectors').dataTable({
		tableColumns : [
			{ key : 'collectorId', label : '#', sortable : true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'name', label : 'Name', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-collector-details?account_id=" + record.accountId + "&collector_id=" + record.collectorId + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key : 'sourceTypeId', label : 'Type', sortable : true, formatter: function(cell, value, record, source) {
				return getCollectorType(record.sourceTypeId);
			}},
			{ key : 'statusId', label : 'Status', sortable : true, formatter: function(cell, value, record, source) {
				return getCollectorStatus(record.statusId, record.sourceTypeId);
			}},
			{ key : 'started', label : 'Responses', sortable : true, sortBy : { dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.started);
			}},
			{ key: 'completed', label: 'Completed', sortable: true, sortBy : { key : 'completed', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.completed);	
			}},
			{ key : 'completionRate', label : 'Completion Rate', sortable : true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				var completionRate;
				if(record.started != 0) {
					completionRate = ((record.completed / record.started) * 100).toFixed(2) + "%";
				} else {
					completionRate = "0.00%";
				}
				return completionRate;
            }},
			{ key: 'partial', label: 'Partial', sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.partial);	
			}},
			{ key: 'disqualified', label: 'Disqualified', sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				var disqualified = 0;
				return $.addCommas(disqualified);
			}},
            { key: 'timeTaken', label: 'Avg. Time Taken', sortable: false, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
            	if(record.timeTaken != undefined) {
            		var timeTaken = (record.timeTaken.days != 0 ? record.timeTaken.days + (record.timeTaken.days > 1 ? " days, " : " day, ") : "") +
            		(record.timeTaken.hours != 0 ? record.timeTaken.hours + (record.timeTaken.hours > 1 ? " hours, " : " hour, ") : "") + 
            		(record.timeTaken.minutes != 0 ? record.timeTaken.minutes + (record.timeTaken.minutes > 1 ? " mins, " : " min, ") : "") +
            		(record.timeTaken.seconds != 0 ? record.timeTaken.seconds + (record.timeTaken.seconds > 1 ? " secs" : " sec") : "");
            		
            		if(record.timeTaken.days == 0 
   						&& record.timeTaken.hours == 0
   						&& record.timeTaken.minutes == 0
   						&& record.timeTaken.seconds == 0) {
               			timeTaken = "Less than sec";
               		}
            		
            		return timeTaken;
            	}
            }},
			{ key : 'accountId', label : 'Account', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-collectors?account_id=" + record.accountId  + "\" title=\"" + record.accountName + "\">" + record.accountName + "</a>");
			}},
			{ key : 'opinionId', label : 'Survey', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-"+ record.opinionTypeName.toLowerCase() +"s?account_id=" + record.accountId + "\" title=\"[" + record.opinionTypeName + "] " + record.opinionName + "\">" + record.opinionName + "</a>");
			}},
			{ key : 'lastResponseDate', label : 'Last Response Date', sortable : true, sortBy : { dataType: "date" }, formatter: function(cell, value, record, source) {
				if(record.lastResponseDate) {
					var left = record.lastResponseDate.substring(record.lastResponseDate.lastIndexOf(" "), " ");
					var right = record.lastResponseDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		totals : [
		   [
				{ key : "name", calculate : false, formatter : function(totals) {
					return "Total - all collectors";
				}},
				{ key : "started", formatter : function(totals) {
		    		return $.addCommas(totals.started);
		    	}},
		    	{ key : "completed", formatter : function(totals) {
		    		return $.addCommas(totals.completed);
		    	}},
		    	{ key : "completionRate", formatter : function(totals) { 
		    		var completionRate;
		    		if((totals.completed + totals.partial) != 0) {
		    			completionRate = ((totals.completed / totals.started) * 100).toFixed(2) + "%";
		    		} else {
		    			completionRate = "0.00%";
		    		}
		    		return completionRate; 
		    	}},
		    	{ key : "partial", formatter: function(totals) {
		    		return $.addCommas(totals.partial);	
		    	}},
		    	{ key : "disqualified", calculate : false, formatter : function(totals) {
		    		return "0";
		    	}},
		    	{ key : "timeTaken", calculate : false, formatter : function(totals) {
		    		return "--";
		    	}}
		   ]
		],
		dataSource : collectors, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};

$(function() {
	
	getCollectors({
		success : function(data) {
			collectors = data.list;
			renderTableCollectors();
		},
		error : function() {
			collectors = [];
			renderTableCollectors();
		}
	});
	
});

</script>