
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%

	String opinionId = request.getParameter("opinion_id");
	String collectorId = request.getParameter("collector_id");
		
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();

%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important;display: inline"><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors" title="Collectors">Collectors</a>&nbsp;&rsaquo;&nbsp;<span id="label_collector_name"></span></h1><a href="javascript:;" id="link_rename_collector_name" title="Rename" style="margin-left: 6px;">Rename</a>
			</td>
			<td class="cell-right">
				<a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors/create" title="Add Collector" class="button-green"><span><i class="icon-add-white">&nbsp;</i>Add Collector</span></a>
			</td>
		</tr>
	</table>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln" id="list_collector_actions"></ul>
	</div>
	<div class="content-middle-tabs-section">
		<div class="content-middle-tabs">
			<ul class="content-middle-tabs-container">
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors/<%=collectorId %>" title="Details"><span>Details</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors/<%=collectorId %>/settings" class="selected" title="Settings"><span>Settings</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 24px;">
		<div class="wrapper-content-left">
			<div>
				<h3 class="ui-header-light">Multiple Responses</h3>
				<div style="padding-top: 10px;">
					<div class="params">
						<div class="param-value">
							<select id="dropdown_multiple_responses" autocomplete="off"><option value="0">One response per computer</option><option value="1">Allow multiple responses</option></select>
						</div>
					</div>
				</div>
			</div>
			<div style="padding-top: 24px; clear: both">
				<h3 class="ui-header-light">Results</h3>
				<div style="padding-top: 12px;" class="ui-form">
					<ul>
						<li class="row-choice">
							<label><span><input type="radio" name="results" value="1" autocomplete="off" /></span>Hide all results</label>
						</li>
						<li class="row-choice">
							<label><span><input type="radio" name="results" value="2" autocomplete="off" /></span>Show only percentages</label>
						</li>
						<li class="row-choice">
							<label><span><input type="radio" name="results" value="3" checked="checked" autocomplete="off" /></span>Display poll results and vote totals</label>		
						</li>
					</ul>
				</div>
			</div>
			<div style="padding-top: 24px; clear: both">
				<h3 class="ui-header-light">Email Notifications</h3>
				<div style="padding-top: 4px;" class="ui-form">
					<div class="row-choice">
						<label><span><input type="checkbox" name="checkbox_enable_email_notification" id="checkbox_enable_email_notification" autocomplete="off" /></span>Get responses sent to your inbox</label>
					</div>
				</div>
			</div>
		</div>
		<div class="wrapper-content-middle-right">
			<div>
				<h3 class="ui-header-light">Collector closing</h3>
				<div style="padding-top: 4px;" class="ui-form">
					<div class="row-choice">
						<label><span><input type="checkbox" name="checkbox_close_after_a_certain_date" id="checkbox_close_after_a_certain_date" autocomplete="off"  /></span>Close after a certain date</label>
					</div>
					<div id="container_close_after_a_certain_date" style="display: none; padding-top: 12px;">
						<div class="row">
							<div class="cell"><select id="dropdown_close_after_certain_months" autocomplete="off"></select></div>
							<div class="cell" style="margin-left: 6px;"><select id="dropdown_close_after_certain_days" autocomplete="off"></select></div>
							<div class="cell" style="margin-left: 6px;"><select id="dropdown_close_after_certain_years" autocomplete="off"></select></div>
						</div>
						<div class="row">
							<div class="cell"><select id="dropdown_close_after_certain_hours" autocomplete="off"></select></div>
							<div class="cell" style="margin-left: 6px;"><select id="dropdown_close_after_certain_minutes" autocomplete="off"></select></div>
						</div>
						<div style="padding-bottom: 10px;"><em style="color: #999">* Time is in your chosen timezone</em></div>
					</div>
					<div class="row-choice">
						<label><span><input type="checkbox" name="checkbox_close_after_a_quota_reached" id="checkbox_close_after_a_quota_reached" autocomplete="off" /></span>Close after a quota reached</label>
					</div>
					<div id="container_quota_reached" style="display: none; padding-top: 12px;">
						<div class="row">
							<div class="cell">
								<div><input id="input_quota_reached" name="input-quota-reached" type="text" autocomplete="off" value="0" style="width: 86px;" /></div>
								<div><label id="status_input_quota_reached"></label></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div style="padding-top: 24px; clear: both;">
				<h3 class="ui-header-light">Close default message</h3>
				<div style="padding-top: 12px;">
					<div><textarea style="width: 314px; height: 64px;" autocomplete="off" name="close_default_message" id="textarea_close_default_message"></textarea></div>
					<div><label id="status_close_default_message"></label></div>
				</div>
			</div>
		</div>
		
		<div style="clear: both;">
			<div style="height: 24px; overflow: hidden;clear: both;"></div>
			<div class="params">
				<div class="param-value">
					<a href="javascript:;" id="button_save_settings" title="Save Changes" class="button-blue"><span>Save Changes</span></a>
				</div>
				<div class="param-value" style="padding-left: 6px; line-height: 21px;">
					<a href="javascript:;" onClick="history.go(-1)" title="Cancel">Cancel</a>
				</div>
			</div>
		</div>
		
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_collector_details.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_collector_settings.js" charset="utf-8"></script>
<script type="text/javascript">

var messages = {
	closeCollector : "Close Collector",
	openCollector : "Open Collector",
	defaultCloseMessage : "This poll is currently closed. Please contact the author of this poll for further assistance.",
	monthNames : ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"] 
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

var getCollectorDetails = function(params) {
	
	var obj = {
		collectors : {
			getCollectorDetails : {
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
			if(data.collectors.getCollectorDetails != undefined) {
				if(data.collectors.getCollectorDetails.error != undefined) {
					
					errorHandler({
						error : data.collectors.getCollectorDetails.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.getCollectorDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.getCollectorDetails);
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

var updateCollector = function(params) {
	 
	 var obj = {
		collectors : {
			updateCollector : {
				collectorId : params.collectorId,
				name : params.name,
				closeMessage : params.closeMessage,
				closeAfterQuotaReached : params.closeAfterQuotaReached, 
				quotaReached : params.quotaReached,
				closeAfterCertainDate : params.closeAfterCertainDate,
				certainDate : params.certainDate,
				referer : params.referer,
				isPasswordRequired : params.isPasswordRequired,
				hidePassword : params.hidePassword,
				password : params.password,
				allowMultipleResponses : params.allowMultipleResponses,
				enablePrevious : params.enablePrevious,
				enableRssUpdates : params.enableRssUpdates,
				enableEmailNotification : params.enableEmailNotification,
				enableSSLEncription : params.enableSSLEncription,
				resultsTypeId : params.resultsTypeId
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
       		if(data.collectors.updateCollector != undefined) {
				if(data.collectors.updateCollector.error != undefined) {
					
					errorHandler({
						error : data.collectors.updateCollector.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.updateCollector);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.updateCollector);
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


$(function() {
	
	new pollDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function(data) {
			
			new collectorDetails({
				opinionId : <%=opinionId%>,
				collectorId : <%=collectorId%>,
				absoluteUrl : "<%=absoluteURL %>",
				applicationUrl : "<%=applicationURL%>",
				callback : function(data) {
					
					new collectorSettings({
						data : data
					});
					
				}
			});
			
			
		}
	});
	
	
});
</script>