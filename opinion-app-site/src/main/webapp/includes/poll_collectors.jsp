
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>

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
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors" class="selected" title="Collectors"><span>Collectors</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/overall-results" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 20px;">
		<div>Below is a list of the collectors you are currently using to collect responses. To view the details or change the properties of an existing collector, just click the name.<br/> To collect more responses for this poll from a different group of people, click "Add Collector".</div>
		<div style="padding-top: 20px;">
			<div>
				<div id="table_collectors"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js" charset="utf-8"></script>
<script type="text/javascript">

var messages = {
	closeCollector : "Close Collector",
	openCollector : "Open Collector",
	defaultCloseMessage : "This poll is currently closed. Please contact the author of this poll for further assistance."
};

var getCollectors = function(params) {
	
	var obj = { 
		collectors : { 
			getCollectors : { 
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
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var changeCollectorName = function(params) {
	
	var obj = {
		collectors : {
			changeCollectorName : {
				name : params.name,
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
        	if(data.collectors.changeCollectorName.error != undefined) {
        		
        		errorHandler({
					error : data.collectors.changeCollectorName.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.collectors.changeCollectorName);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.collectors.changeCollectorName);
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

var updateCollectorStatus = function(params) {
	
	var obj = { 
		collectors : { 
			updateCollectorStatus : { 
				collectorId : params.collectorId,
				statusId : params.statusId,
				closeMessage : params.closeMessage,
				isGenerateNewGuid : params.isGenerateNewGuid
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
        	if(data.collectors.updateCollectorStatus != undefined) {
				if(data.collectors.updateCollectorStatus.error != undefined) {
					
					errorHandler({
						error : data.collectors.updateCollectorStatus.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.updateCollectorStatus);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.updateCollectorStatus);
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



var collectors = [];
var tableCollectors = null;
var renderTableCollectors = function() {
	
	$('#table_collectors').empty();
	tableCollectors = $('#table_collectors').dataTable({ 
		tableColumns : [
			{ key : 'collectorId', label : '#', sortable : true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'name', label : 'Name', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/polls/<%=opinionId %>/collectors/" + record.collectorId + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key : 'statusId', label : 'Status', sortable : true, formatter: function(cell, value, record, source) {
				
				var q = "";
				
				if(record.sourceTypeId == 1) { 
				
					q = $("<a href=\"javascript:;\" title=\"" + (record.statusId == 1 ? "Close Collector" : "Open Collector") + "\" collector_id=\"" + record.collectorId + "\" status_id=\"" + record.statusId + "\" close_message=\"" + (record.closeMessage != undefined ? record.closeMessage : "") + "\" >" + (record.statusId == 1 ? getCollectorStatus(1, record.sourceTypeId) : getCollectorStatus(2, record.sourceTypeId)) + "</a>")
					.tipsy({
						gravity: 's', 
						title: function() { 
							return this.getAttribute('original-title'); 
						}
					})
					.click(function() {
	
						var collectorId = parseInt($(this).attr('collector_id'));
						var statusId = parseInt($(this).attr('status_id'));
						var closeMessage = $(this).attr('close_message');
	
						if(statusId == 1) {	
	
							var M = $("<div>" +
								"<div style=\"padding: 0 0 12px 0\">Closing this collector will prevent anyone who is using this collector<br/> from entering responses and will interrupt any responses that are in progress.</div>" +
								"<div class=\"row\" style=\"height: 84px;\">" +
									"<div class=\"cell\">" +
										"<textarea id=\"textarea_close_collector_message\" name=\"textarea_close_collector_message\" autocomplete=\"off\" style=\"width: 314px; height: 74px;\"></textarea>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#textarea_close_collector_message');
								
							var modal = new lightFace({
								title : "Are you sure you want to stop collecting responses?",
								message : M,
								actions : [
						           { 
						        	   label : "Stop Collecting Now", 
						        	   fire : function() {
							        	   	updateCollectorStatus({
							        	   		collectorId: collectorId, 
							        	   		statusId : 2, 
							        	   		closeMessage : $.removeHTMLTags(I.val()).replace(/\r/g, ""), 
							        	   		isGenerateNewGuid : false, 
							        	   		success : function() {
													$(q)
														.text(getCollectorStatus(2, 1))
														.attr({ 'title' : "Open Collector", 'status_id' : 2, 'close_message' : $.removeHTMLTags(I.val()).replace(/\r/g, "") });
					
													record.statusId = 2;
		
													modal.close();
												}	
							        	   	});
								   		}, color: "blue" 
								   },
								   { 
									   label : "Cancel", 
									   fire : function() { 
										   modal.close();
										}, 
										color: "white" 
									}
								],
								overlayAll : true,
								complete : function() {
									I.text(((closeMessage == "") ? messages.defaultCloseMessage : closeMessage));
								}
							});
	
						}
	
						if(statusId == 2) {
	
							var modal2 = new lightFace({
								title : "Are you sure you want to start collecting responses?",
								message : "This collector is currently closed.<br/> You can re-open this collector now.",
								actions : [
						           { 
						        	   label : "Open Collector", 
						        	   fire : function() {

											// update collectro status
							        	   	updateCollectorStatus({
							        	   		collectorId : collectorId,
							        	   		statusId : 1, 
							        	   		closeMessage : null, 
							        	   		isGenerateNewGuid : false, 
							        	   		success : function() {
													$(q)
														.text(getCollectorStatus(1, 1))
														.attr({ 'title' : "Close Collector", 'status_id' : 1 });
													record.statusId = 1;
		
													modal2.close();
												}	
						        	   		});
											
								       }, 
								       color: "blue" 
							       },
							       { 
							    	   label : "Cancel", 
							    	   fire : function() { 
							    		   modal2.close(); 
							    		}, 
							    		color: "white" 
							    	}
								],
								overlayAll : true
							});
							
						}
						
					});
					
				}
				
				return q;
			}},
			{ key : 'started', label : 'Responses', sortable : true, sortBy : { dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				if(record.started != 0) {
					return $("<a href=\"<%=absoluteURL %>/polls/" + record.opinionId + "/overall-results\" title=\"" + record.started + "\">" + record.started + "</a>");
				}
				return record.started;
			}},
			{ key: 'completed', label: 'Completed', sortable: true, sortBy : { key : 'completed', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key: 'partial', label: 'Partial', sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'completionRate', label : 'Completion Rate', sortable : true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return record.completionRate + "%";
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
			{ key : 'lastResponseDate', label : 'Last Response Date', sortable : true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
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
		    	{ key : "started" },
		    	{ key : "completed" },
		    	{ key : "partial" },
		    	{ key : "completionRate", formatter : function(totals) { 
		    		var completionRate;
		    		if((totals.completed + totals.partial) != 0) {
		    			completionRate = Math.round(totals.completed / (totals.completed + totals.partial) * 100) + "%";
		    		} else {
		    			completionRate = "0%";
		    		}
		    		return completionRate; 
		    	}},
		    	{ key : "timeTaken", calculate : false, formatter : function(totals) {
		    		return "--";
		    	}}
		   ]
		],
		dataSource : collectors, 
		pagingStart: 10,
		selectable : true,
		actions : [
		    {
		    	label : "Add Collector",
		    	color: "green",
		    	icon : "add-white",
		    	fire : function() {
		    		location.href = "<%=absoluteURL %>/polls/<%=opinionId %>/collectors/create";
		    	}
		    },
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
												//
												getCollectors({
													opinionId : opinionId,
													success : function(data) {
														
														collectors = data.list;
														renderTableCollectors();
														
													},
													error: function(error) {
														
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
			},
			{
				label : "More actions",
				disabled : true,
				condition : 1,
				actions : [
					{ 
						label : "Rename", 
						fire : function(records, source) {
							
							var v = null;
							var M = $("<div>" +
								"<div style=\"padding: 0 0 12px 0\">Enter a new collector name.</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"text_rename_collector_name\" name=\"rename_collector_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
										"<div><label id=\"status_rename_collector_name\"></label></div>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#text_rename_collector_name');
							var B = M.find('#status_rename_collector_name');
							
							var modal = new lightFace({
								title : "Rename collector",
								message : M,
								actions : [
								   { 
									   label : "Save", 
									   fire : function() {
									   		// check validation
											v.validate();
								   		}, 
								   		color: "blue" 
								   },
								   { 
								   		label : "Cancel", 
								   		fire : function() { 
								   			modal.close(); 
								   		}, 
								   		color: "white" 
								   }
								],
								overlayAll : true,
								complete : function() {
									
									// set default values
									I.val(records[0].name).select();
									
									// initialize validator on input
									v = new validator({
										elements : [
											{
												element : I,
												status : B,
												rules : [
													{ method : 'required', message : 'This field is required.' },
													{ method : 'rangelength', pattern : [3,100] }
												]
											}
										],
										submitElement : null,
										messages : null,
										accept : function () {
											
											changeCollectorName({
												name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												collectorId : records[0].collectorId,
												success : function(data) {
													
													getCollectors({
														opinionId : opinionId,
														success : function(data) {
															
															collectors = data.list;
															renderTableCollectors();
															
														},
														error: function(error) {
															
															collectors = [];
															renderTableCollectors();
															
														}
													});
													
													// refresh
													modal.close(); 
												},
												error: function() {
													//
												}
											});
											
										},
										error: function() {
											// error
										}
									});
								}
							});
							
						}
					}
				]
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

//var accountId = auth.userInfo.accountId;
var opinionId = <%=opinionId%>;

$(function() {
	
	new pollDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function() {
			
			getCollectors({
				opinionId : <%=opinionId%>,
				success : function(data) {
					
					collectors = data.list;
					renderTableCollectors();
					
				},
				error: function(error) {
					
					collectors = [];
					renderTableCollectors();
					
				}
			});
			
		}
	});
	
	
});
</script>