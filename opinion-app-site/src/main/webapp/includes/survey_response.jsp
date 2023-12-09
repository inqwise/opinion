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

<link rel="stylesheet" href="<%=applicationURL%>/css/sidebar/sidebar.css" type="text/css" />

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important;display: inline"><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/responses" title="Responses">Responses</a>&nbsp;&rsaquo;&nbsp;<span id="label_participant_name"></span></h1>
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
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/responses/<%=responseId %>" class="selected" title="Details"><span>Details</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="clear: both;overflow:hidden;padding-top: 20px; min-height: 500px;" class="sidebar-relative">
		
		<div class="wrapper-content-left">		
			<div style="clear: both; height: 24px;" class="ui-navigation-panel">
				<div>
					<div class="left-side">
						<div id="button_pages"></div>
					</div>
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
			
			<div style="clear: both;">
				<div style="clear: both; padding-top: 12px;">
					<div id="placeholder_results">
						<ul class="list-result-controls"></ul>
					</div>
				</div>
			</div>
	
			<div style="clear: both;" class="ui-navigation-panel">
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
		<div class="wrapper-content-middle-right">
			<div style="width: 450px;" class="sidebar">
				<div style="float: left; width: 220px;">
					<div>
						<h3 class="ui-header">Response Details</h3>
						<div style="padding: 12px 0 12px 8px">
							<ul class="ll" style="margin: 0px;">
								<li>Participant: <b style="color: #333">Anonymous</b></li>
								<li>Response Id: <b id="label_response_id" style="color: #333"></b></li>
								<li>Status: <b id="label_status" style="color: #333"></b></li>
								<li>Start Date: <b id="label_start_date" style="color: #333"></b></li>
								<li>Finish Date: <b id="label_finish_date" style="color: #333"></b></li>
								<li>Time Taken: <b id="label_time_taken" style="color: #333"></b></li>
								<li>Location: <b id="label_location" style="color: #333"></b></li>
								<li>IP Address: <b id="label_ip_address" style="color: #333"></b></li>
								<li>Collector: <b id="label_collector" style="color: #333"></b></li>
								<li>OS: <b id="label_os" style="color: #333"></b></li>
								<li>Browser: <b id="label_browser" style="color: #333"></b></li>
							</ul>
						</div>
					</div>
				</div>
				<div style="float: left; width: 220px; padding-left: 10px;">
					&nbsp;
					<!--
					<h3 class="ui-header">Organize Questions</h3>
					<div style="padding: 12px 0 12px 8px; clear: both;">
						<div>Select the questions you would like to see on report.</div>
						
						<div>
							<label><span><input type="checkbox" autocomplete="off"/></span>Show only</label>
						</div>
						<div style="overflow-y:auto; max-height: 200px; border: 1px solid #999; background: #fff; margin-top: 10px;">
							<ul id="list_option_controls"></ul>
						</div>
					</div>
					-->
				</div>
			</div>
		</div>
	</div>
	
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/sidebar/sidebar.js"></script>

<!-- <script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/menu_button.js"></script> -->
<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/dropdown_button.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_response_control.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_response_details.js"></script>

<script type="text/javascript">

var response = {
	id: null,
	sessionId: null,
	controls : {
		list : []
	}	
};

var contentDictionary = {
	titles : {
		title_Month : "Month",
		title_Day : "Day",
		title_Year : "Year",
		title_Hours : "Hours",
		title_Minutes : "Minutes",
		title_TimeZone : "Time Zone",
		title_DefaultNoneSelectedOption : "Please choose one of the following",
		title_Other : "Other (please specify)",
		title_DefaultAdditionalDetails : "Additional details or comments"
	}
};

var getSheets = function(params) {

	var obj = {
		controls : {
			getSheets: {
				accountId : params.accountId,
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
			if(data.controls.getSheets != undefined) {
				if(data.controls.getSheets.error != undefined) {
					
					errorHandler({
						error : data.controls.getSheets.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.getSheets);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.getSheets);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var getStartIndexOfSheet = function(params) {
	
	var obj = { 
		controls : { 
			getStartIndexOfSheet : {
				accountId : params.accountId,
				sheetId : params.sheetId
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
			if(data.controls.getStartIndexOfSheet != undefined) {
				if(data.controls.getStartIndexOfSheet.error != undefined) {
					
					errorHandler({
						error : data.controls.getStartIndexOfSheet.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.getStartIndexOfSheet);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.getStartIndexOfSheet);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var getResponseDetails = function(params) {
	
	var obj = {
		responses : { 
			getAnswererSessionDetails : {
				accountId : params.accountId,
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
				accountId : params.accountId,
				opinionId : params.opinionId,
				sheetId : params.sheetId,
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
				accountId : params.accountId,
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
				accountId : params.accountId,
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
				accountId : params.accountId,
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

var copy = function(params) {
	
	var obj = {
		opinions : {
			copy : {
				accountId : params.accountId,
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
				accountId : params.accountId,
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
				accountId : params.accountId,
				list : params.list,
				opinionTypeId : 1 /*survey*/
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


//var accountId = $.cookie("aid"); // auth.userInfo.accountId;
var sheets = [];

$(function() {

	new surveyDetails({ 
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
			
			// sidebar
			new sidebar({});
			
		}
	});

});

</script>