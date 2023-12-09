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
	
	String accountId = request.getParameter("account_id");
%>

<div>
	<h1 style="padding-bottom: 0 !important"><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<span id="header_account_name"></span></h1>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln" id="list_account_details"></ul>
	</div>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" title="Account Details"><span>Account Details</span></a></li>
					<li><a href="<%=absoluteURL %>/account-users?account_id=<%=accountId %>" title="Users"><span>Users</span></a></li>
					<li><a href="<%=absoluteURL %>/account-surveys?account_id=<%=accountId %>" title="Surveys"><span>Surveys</span></a></li>
					<li><a href="<%=absoluteURL %>/account-collectors?account_id=<%=accountId %>" title="Collectors" class="selected"><span>Collectors</span></a></li>
					<li><a href="<%=absoluteURL %>/account-billing?account_id=<%=accountId %>" title="Billing"><span>Billing</span></a></li>
					<li><a href="<%=absoluteURL %>/account-transaction-history?account_id=<%=accountId %>" title="Transaction History"><span>Transaction History</span></a></li>
					<li><a href="<%=absoluteURL %>/account-make-payment?account_id=<%=accountId %>" title="Make a Payment"><span>Make a Payment</span></a></li>
					<li><a href="<%=absoluteURL %>/account-charges?account_id=<%=accountId %>" title="Charges"><span>Charges</span></a></li>
					<li><a href="<%=absoluteURL %>/account-recurring?account_id=<%=accountId %>" title="Recurring"><span>Recurring</span></a></li>
					<li><a href="<%=absoluteURL %>/account-uninvoiced-list?account_id=<%=accountId %>" title="UnInvoiced List"><span>UnInvoiced List</span></a></li>
					<li><a href="<%=absoluteURL %>/account-invoices?account_id=<%=accountId %>" title="Invoices"><span>Invoices</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both; padding-top: 20px;">
		<div id="table_collectors"></div>
	</div>
</div>

<script type="text/javascript">
var getAccountDetails = function(params) {
	
	var obj = {
		accounts : {
			getAccountDetails : {
				accountId : params.accountId
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
			if(data.accounts.getAccountDetails != undefined) {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.getAccountDetails);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getCollectors = function(params) {
	
	var obj = {
		collectors : {
			getCollectors : {
				top : 100,
				from : undefined,
				to : undefined,
				accountId : params.accountId,
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

var deleteCollectors = function(params) {
	
	var obj = { 
   		collectors : { 
			deleteCollectors : { 
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
        	if(data.collectors.deleteCollectors != undefined) {
				if(data.collectors.deleteCollectors.error != undefined) {
					
					errorHandler({
						error : data.collectors.deleteCollectors.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.deleteCollectors);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.deleteCollectors);
					}
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
			s = "The panel is live";
			break;
		case 6 : 
			s = "Panel has been completed";
			break;
		case 7 : 
			s = "Pending"; // Hold
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
				return $("<a href=\"<%=absoluteURL %>/account-collector-details?account_id=<%=accountId %>&collector_id=" + record.collectorId + "\" title=\"" + record.name + "\">" + record.name + "</a>");
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
		    			completionRate = "0%";
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
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {
					if(records.length > 0) {
						
						var list = [];
						$(records).each(function(index) {
							if(!(records[index].sourceTypeId == 2 && records[index].statusId == 1)) {
								list.push(records[index].collectorId);
							}
						});
						
						
						var modal = new lightFace({
							title : "Deleting collectors",
							message : "Are you sure you want to delete selected collector?",
							actions : [
					           { 
					        	   label : "Delete", 
					        	   fire : function() {
					        	
					        		   deleteCollectors({
											list : list,
											success : function() {
												
												getCollectors({
													accountId : accountId,
													success : function(data) {
														collectors = data.list;
														renderTableCollectors();
													},
													error : function() {
														collectors = [];
														renderTableCollectors();
													}
												});
												
												modal.close();
											}
										});	
					        	   
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

var getAccountStatus = function(statusId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = "Enabled"; // Your account is now enabled.
			break;
		case 2 : 
			s = "Disabled"; // Your account is disabled. 
			break;
		case 3 : 
			s = "Expired"; // Your account has expired.
			break;
		case 4 :
			s = "Suspended"; // Your account is suspended.
			break;
	}
	return s;
};

var accountId = <%=accountId%>;

$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			// details
			// TODO: change flag isActive to statusId
			$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getAccountStatus(data.isActive ? 1 : 2) + "</li>").appendTo('#list_account_details');
			
			
			getCollectors({
				accountId : accountId,
				success : function(data) {
					collectors = data.list;
					renderTableCollectors();
				},
				error : function() {
					collectors = [];
					renderTableCollectors();
				}
			});
			
		}
	});
	
});
</script>