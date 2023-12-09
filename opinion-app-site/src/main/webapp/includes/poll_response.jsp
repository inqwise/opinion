
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>

<%

	String opinionId = request.getParameter("opinion_id");
	String responseId = request.getParameter("response_id");
		
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();

%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important;display: inline"><a href="<%=absoluteURL %>/polls/<%=opinionId %>/responses" title="Responses">Responses</a>&nbsp;&rsaquo;&nbsp;<span id="label_participant_name"></span></h1>
			</td>
			<td class="cell-right">
				&nbsp;
			</td>
		</tr>
	</table>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln">
			<li class="first-item"><a href="javascript:;" title="Delete" id="link_response_delete">Delete</a></li>
		</ul>
	</div>
	<div class="content-middle-tabs-section">
		<div class="content-middle-tabs">
			<ul class="content-middle-tabs-container">
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/responses/<%=responseId %>" class="selected" title="Details"><span>Details</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 20px; clear: both;">
		<div class="wrapper-content-left">
			
			<div style="clear: both;" class="ui-navigation-panel">
				<div>
					<div class="left-side"></div>
					<div class="right-side">
						<div class="container-navigation-actions">
							<div class="cell"><a href="#" title="Previous" class="ui-navigation-panel-button-previous button-white"><span><i class="icon-previous">&nbsp;</i></span></a><a href="#" title="Next" class="ui-navigation-panel-button-next button-white"><span><i class="icon-next">&nbsp;</i></span></a></div>
							<div class="cell" style="margin: 0 6px;"><span class="label"><b class="label-response-index"></b> of <b class="label-responses-count"></b></span></div>
							<div class="cell" style="margin-left: 6px;"><a title="Go" class="button-white ui-navigation-panel-button-go"><span>Go</span></a></div>
							<div class="cell" style="margin-left: 6px;"><input type="text" autocomplete="off" class="ui-navigation-panel-input-goto" /></div>
							<div class="cell"><span class="label">Go to:</span></div>
						</div>
					</div>
				</div>
			</div>
			
			<div style="clear: both; overflow: hidden; Xmargin-top: 24px;">
				<div style="float: left; width: 220px;">
					<h3 class="ui-header-light">Response Details</h3>
					<ul class="ll" style="margin-left: 0px;">
						<li>Voter: <b style="color: #333">Anonymous</b></li>
						<li>Response Id: <b id="label_response_id" style="color: #333"></b></li>
						<li>Status: <b id="label_status" style="color: #333"></b></li>
						<li>Location: <b id="label_location" style="color: #333"></b></li>
						<li>IP Address: <b id="label_ip_address" style="color: #333"></b></li>
						<li>Collector: <b id="label_collector" style="color: #333"></b></li>
						<li>OS: <b id="label_os" style="color: #333"></b></li>
						<li>Browser: <b id="label_browser" style="color: #333"></b></li>
					</ul>
				</div>
				<div style="float: left;width: 220px; padding-left: 10px;">
					<h3 class="ui-header-light">&nbsp;</h3>
					<ul class="ll" style="margin-left: 0px;">
						<li>Start Date: <b id="label_start_date" style="color: #333"></b></li>
						<li>Finish Date: <b id="label_finish_date" style="color: #333"></b></li>
						<li>Time Taken: <b id="label_time_taken" style="color: #333"></b></li>
					</ul>
				</div>
			</div>
			<div style="clear: both;">
				<h3 class="ui-header-light">Results</h3>
				<div style="clear: both; padding-top: 12px;">
					<div id="placeholder_results">
						<ul class="list-result-controls"></ul>
					</div>
				</div>
			</div>
			
			<div style="clear: both; padding-top: 24px;" class="ui-navigation-panel">
				<div class="left-side"></div>
				<div class="right-side">
					<div class="container-navigation-actions">
						<div class="cell"><a href="#" title="Previous" class="ui-navigation-panel-button-previous button-white"><span><i class="icon-previous">&nbsp;</i></span></a><a href="#" title="Next" class="ui-navigation-panel-button-next button-white"><span><i class="icon-next">&nbsp;</i></span></a></div>
						<div class="cell" style="margin: 0 6px;"><span class="label"><b class="label-response-index"></b> of <b class="label-responses-count"></b></span></div>
						<div class="cell" style="margin-left: 6px;"><a title="Go" class="button-white ui-navigation-panel-button-go"><span>Go</span></a></div>
						<div class="cell" style="margin-left: 6px;"><input type="text" autocomplete="off" class="ui-navigation-panel-input-goto" /></div>
						<div class="cell"><span class="label">Go to:</span></div>
					</div>
				</div>
			</div>
			
		</div>
		<div class="wrapper-content-middle">
			&nbsp;
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_response_control.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_response_details.js"></script>
<script type="text/javascript">


var response = {
	id : null,
	controls : {
		list : []
	}	
};
	
var getResponseDetails = function(params) {
	
	var obj = {
		responses : { 
			getAnswererSessionDetails : {
				opinionId: params.opinionId,
				answererSessionId : params.answererSessionId
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
        	if(data.responses.getAnswererSessionDetails.error != undefined) {
        		
        		errorHandler({
					error : data.responses.getAnswererSessionDetails.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.responses.getAnswererSessionDetails);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.responses.getAnswererSessionDetails);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getResponseResults = function(params) {
	
	var obj = {
		responses : { 
			getAnswererResults : {
				opinionId : params.opinionId,
				answererSessionId : params.answererSessionId,
				includePartial : true // TODO:
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
        	if(data.responses.getAnswererResults.error != undefined) {
        		
        		errorHandler({
					error : data.responses.getAnswererResults.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.responses.getAnswererResults);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.responses.getAnswererResults);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getResponses = function(params) {
	
	var obj = {
		responses : { 
			getResponses : {
				opinionId: params.opinionId
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

var opinionId = <%=opinionId%>;

$(function() {
	
	new pollDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function() {
			
			new responseDetails({
				opinionId : <%=opinionId%>,
				answererSessionId : "<%=responseId%>",
				absoluteUrl : "<%=absoluteURL%>"
			});
			
		}
	});
	
});

</script>