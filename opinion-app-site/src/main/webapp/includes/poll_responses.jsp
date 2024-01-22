
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%

	String opinionId = null;
	opinionId = request.getParameter("opinion_id");
		
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();

%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important;display: inline"><a href="<%=absoluteURL %>/polls" title="Polls">Polls</a>&nbsp;&rsaquo;&nbsp;<span id="label_poll_name"></span></h1><a href="javascript:;" id="link_rename_poll_name" title="Rename" style="margin-left: 6px;">Rename</a>
			</td>
			<td class="cell-right">
				<a href="<%=absoluteURL %>/polls/create" title="Create Poll" class="button-green"><span><i class="icon-add-white">&nbsp;</i>Create Poll</span></a>
			</td>
		</tr>
	</table>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln">
			<li class="first-item"><a href="#" title="Copy" id="link_poll_copy">Copy</a></li>
			<li><a href="#" title="Delete" id="link_poll_delete">Delete</a></li>
			<li><a id="link_export" title="Export" style="display:none">Export</a></li>
			<li><a href="#" title="Embed" id="link_poll_embed">Embed</a></li>
			<li><a href="#" title="Preview" id="link_poll_preview">Preview</a></li>
		</ul>
	</div>
	<div class="content-middle-tabs-section">
		<div class="content-middle-tabs">
			<ul class="content-middle-tabs-container">
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/edit" title="Edit"><span>Edit</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/settings" title="Settings"><span>Settings</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/branding" title="Branding"><span>Branding<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors" title="Collectors"><span>Collectors</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/overall-results" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/responses" class="selected" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 20px; clear: both;">
		<div>
			<div id="table_responses"></div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js"></script>
<script type="text/javascript">

var getResponses = function(params) {
	
	var obj = {
		responses : { 
			getResponses : {
				opinionId: params.opinionId,
				collectorId : params.collectorId
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
        	if(data.responses.getResponses.error != undefined) {
        		
        		errorHandler({
					error : data.responses.getResponses.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.responses.getResponses);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.responses.getResponses);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var deleteResponses = function(params) {
	
	var obj = { 
		responses : { 
			deleteResponses : { 
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
        	if(data.responses.deleteResponses.error != undefined) {
        		
        		errorHandler({
					error : data.responses.deleteResponses.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.responses.deleteResponses);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.responses.deleteResponses);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var collectorsList = [];
var getCollectorsFilter = function() {
	var filter = [];
	filter.push({ caption : "All Collectors", value : "" });
	for(var i = 0; i < collectorsList.length; i++) {
		filter.push({ caption : collectorsList[i].name, value : collectorsList[i].collectorId });
	}
	return filter;
};

var responses = [];
var tableResponses = null;
var renderTableResponses = function() {
	
	$('#table_responses').empty();
	tableResponses = $('#table_responses').dataTable({ 
		tableColumns : [
            { key: 'id', label: '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
            { key: 'name', label: 'Voter', sortable: true, formatter : function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/polls/<%=opinionId %>/responses/" + record.sessionId + "\" title=\"Anonymous\">Anonymous</a>");
            }},
            { key: 'collectorId', label: 'Collector', sortable: true, formatter : function(cell, value, record, source) {
            	return record.collectorIsDeleted ? record.collectorName : "<a href=\"<%=absoluteURL %>/polls/<%=opinionId %>/collectors/" + record.collectorId + "\" title=\"" + record.collectorName + "\">" + record.collectorName + "</a>";
            }},
            { key: 'countryName', label: 'Location', sortable: true,  width: 124, formatter : function(cell, value, record, source) {
            	return (record.countryName != undefined ? record.countryName : "N/A");	
            }},
            { key: 'ipAddress', label: 'IP Address', sortable: true,  width: 86 },
            { key: 'status', label: 'Status', sortable: true,  width: 106, formatter : function(cell, value, record, source) {
				return ( record.status == 0 ? "Partial (Incomplete)" : "Completed" );
            }},
            { key: 'startDate', label: 'Start Date', sortable: true, sortBy : { dataType : "date" },  width: 126, formatter: function(cell, value, record, source) {
				if(record.startDate) {
					var left = record.startDate.substring(record.startDate.lastIndexOf(" "), " ");
					var right = record.startDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
            { key: 'finishDate', label: 'Finish Date', sortable: true, sortBy : { dataType : "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.finishDate) {
					var left = record.finishDate.substring(record.finishDate.lastIndexOf(" "), " ");
					var right = record.finishDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
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
            }}
        ], 
		dataSource : responses, 
		pagingStart: 10,
		selectable :  true,
		actions : [
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {
					if(records.length > 0) {
						
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].id);
						});
						
						var modal = new lightFace({
							title : "Deleting responses",
							message : "Are you sure you want to delete selected responses?",
							actions : [
								{ 
									label : "Delete", 
									fire : function() {
										
										deleteResponses({
											list : list,
											success : function() {
												
												getResponses({
													opinionId : opinionId,
													success : function(data) {
														
														collectorsList = data.collectorsList;
														responses = data.list;
														
														renderTableResponses();
													},
													error: function() {
														
														collectorsList = [];
														responses = [];
														
														renderTableResponses();
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
		],
		filters : [
			{
				title : "Filter:",
				options : getCollectorsFilter(),
				change : function(el) {
					
					var collectorId = ($(el).val() == "" ? null : $(el).val());
					
					getResponses({
						opinionId : opinionId,
						collectorId : collectorId,
						success : function(data) {
							
							collectorsList = data.collectorsList;
							responses = data.list;
							
							// TODO:
							tableResponses.updateRecords(responses);
							
						},
						error: function() {
							
							collectorsList = [];
							responses = [];
							
							// TODO:
							tableResponses.updateRecords(responses);
							
						}
					});
					
					
				}
			}
		]
	});
};

var getDetails = function(params) {
	
	var obj = {
		opinions : {
			getDetails : {
				opinionId : params.opinionId
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
			if(data.opinions.getDetails != undefined) {
				if(data.opinions.getDetails.error != undefined) {
					
					errorHandler({
						error : data.opinions.getDetails.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var copy = function(params) {
	
	var obj = {
		opinions : {
			copy : {
				name : params.name,
				title : params.title,
				opinionType : 1,
				folderId : null,
				opinionId : params.opinionId
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
        	if(data.opinions.copy.error != undefined) {
        		
        		errorHandler({
					error : data.opinions.copy.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.opinions.copy);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.opinions.copy);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var rename = function(params) {
	
	var obj = {
		opinions : {
			rename : {
				name : params.name,
				title : params.title,
				opinionId : params.opinionId
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
        	if(data.opinions.rename.error != undefined) {
        		
        		errorHandler({
					error : data.opinions.rename.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.opinions.rename);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.opinions.rename);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var deleteOpinions = function(params) {
	
	var obj = {
		opinions : {
			deleteOpinions : {
				list : params.list,
				accountId : params.accountId,
				opinionTypeId : 2 /*poll*/
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
        	if(data.opinions.deleteOpinions != undefined) {
	        	if(data.opinions.deleteOpinions.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.deleteOpinions.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinion.deleteOpinions);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.deleteOpinions);
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

var exportTo = function(params) {
	
	var data = {
	    format: params.format,
	    opinionId : params.opinionId,
	    name: params.name,
	    exportType : params.exportType
	};
	
	window.location = "<%=applicationURL%>/servlet/export?rq=" + JSON.stringify(data);
	
};

var opinionId = <%=opinionId%>;

$(function() {
	
	new pollDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function() {
			
			getResponses({
				opinionId : opinionId,
				success : function(data) {
					
					collectorsList = data.collectorsList;
					responses = data.list;
					
					renderTableResponses();
				},
				error: function() {
					
					collectorsList = [];
					responses = [];
					
					renderTableResponses();
				}
			});
			
		}
	});
	
});
</script>