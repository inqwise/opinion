
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.opinion.library.systemFramework.ApplicationConfiguration"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>

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
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors/<%=collectorId %>" class="selected" title="Details"><span>Details</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors/<%=collectorId %>/settings" title="Settings"><span>Settings</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 24px;clear: both">
		<div class="wrapper-content-left">
			
			<div>
				<h3>Embed Code</h3>
				<div>
					<p>Use this code to integrate your Inqwise poll into your blog post or web page's.</p>
					<div class="row" style="padding-bottom: 0px;">
						<div class="cell">
							<div class="edge" style="width: 428px;">
								<div class="edge-top"></div>
								<div class="edge-bottom"></div>
								<textarea class="code-snippet" id="input_embed" readonly="readonly" autocomplete="off" style="height: 258px;overflow: auto"></textarea>
							</div>
						</div>
					</div>
					<div style="clear: both; padding-top: 8px;"><em style="color: #999">Copy and paste the HTML code above to embed your poll onto your website.</em></div>
				</div>
			</div>
			
			<div style="padding-top: 24px; clear: both">
				<h3 class="ui-header-light">Direct Link</h3>
				<div style="Xpadding-top: 12px;">
					<p>To send your poll by email, tweet, or status, copy the link below and paste it into your message. When others receive your message, they can click the link and access your poll.</p>
					<div class="row" style="padding-bottom: 0px;">
						<div class="cell">
							<div class="edge" style="width: 428px;">
								<div class="edge-top"></div>
								<div class="edge-bottom"></div>
								<textarea class="code-snippet" id="input_direct_link" readonly="readonly" autocomplete="off" style="height: 32px;"></textarea>
							</div>
						</div>
					</div>
					<div style="clear: both; padding-top: 8px;"><em style="color: #999">Copy, paste and email the poll link above to your audience.</em></div>
				</div>
			</div>
			
			<div style="padding-top: 24px; clear: both">
				<h3 class="ui-header-light">Share</h3>
				<div style="padding-top: 12px;">
					<div style="padding-bottom: 6px;"><a href="#" title="Share a Link" id="link_facebook_share_link" class="button-white" target="_blank"><span><b class="icon-facebook">&nbsp;</b>Share a Link</span></a></div>
					<div style="padding-bottom: 6px;"><a href="#" title="Tweet" id="link_twitter_share_link" target="_blank" class="button-white"><span><b class="icon-twitter">&nbsp;</b>Tweet</span></a></div>
					<div style="padding-bottom: 6px;"><a href="#" title="Digg This" id="link_digg_this" target="_blank" class="button-white"><span>Digg This</span></a></div>
					<div><a href="#" title="Submit to Reddit" id="link_submit_to_reddit" target="_blank" class="button-white"><span><b class="icon-reddit">&nbsp;</b>Submit to Reddit</span></a></div>
				</div>
			</div>
			
			<!--
			<div style="padding-top: 24px; clear: both">
				<h3>Guid</h3>
				<div style="padding-top: 12px;">
					<div class="row" style="padding-bottom: 0px;">
						<div class="cell">
							<div class="edge" style="width: 428px;">
								<div class="edge-top"></div>
								<div class="edge-bottom"></div>
								<textarea class="code-snippet" id="input_guid" readonly="readonly" autocomplete="off" style="height: 16px;"></textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
			-->
			
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_collector_details.js?ver=1"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_collector_view.js?ver=1"></script>
<script type="text/javascript">

var messages = {
	closeCollector : "Close Collector",
	openCollector : "Open Collector",
	defaultCloseMessage : "This poll is currently closed. Please contact the author of this poll for further assistance."
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
		collectorUrl : "<%=ApplicationConfiguration.Opinion.Collector.getUrl()%>",
		callback : function() {
	
			new collectorDetails({
				opinionId : <%=opinionId%>,
				collectorId : <%=collectorId%>,
				absoluteUrl : "<%=absoluteURL%>",
				applicationUrl : "<%=applicationURL%>",
				callback : function(data) {
					
					new collectorView({
						sourceUrl : "<%=ApplicationConfiguration.Api.getUrl()%>",
						collectorUrl : "<%=ApplicationConfiguration.Opinion.Collector.getUrl()%>",
						data : data
					});
					
				}
			});
	
		}
	});
	
});
</script>