<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String accountId = request.getParameter("account_id");
	String collectorId = request.getParameter("collector_id");
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important; display: inline"><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" id="header_account_name"></a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-collectors?account_id=<%=accountId %>" title="Collectors">Collectors</a>&nbsp;&rsaquo;&nbsp;<span id="header_collector_name"></span></h1>
			</td>
			<td class="cell-right ui-selector-right">
				<ul class="lw" id="list_collector_actions" style="display: inline-block;"></ul>
			</td>
		</tr>
	</table>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln" id="list_collector_details"></ul>
	</div>
	<div style="clear: both;">
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/account-collector-details?account_id=<%=accountId %>&collector_id=<%=collectorId %>" title="Details" class="selected"><span>Details</span></a></li>
					<li><a href="<%=absoluteURL %>/account-collector-settings?account_id=<%=accountId %>&collector_id=<%=collectorId %>" title="Settings"><span>Settings</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both; padding-top: 24px;">
		
		<div id="placeholder_collector_details_invite_your_own_respondents" style="clear: both; display: none;">
			
			<div class="wrapper-content-left">
				<div>
					<h3 class="ui-header-light">Direct Link</h3>
					<div style="Xpadding-top: 12px;">
						<p>To send your survey link by email, tweet, or status, copy the link below and paste it into your message. When others receive your message, they can click the link and access your survey.</p>
						<div class="row" style="padding-bottom: 0px;">
							<div class="cell">
								<div class="edge" style="width: 428px;">
									<div class="edge-top"></div>
									<div class="edge-bottom"></div>
									<textarea class="code-snippet" id="input_direct_link" readonly="readonly" autocomplete="off" style="height: 32px;"></textarea>
								</div>
							</div>
						</div>
						<div style="clear: both; padding-top: 8px;"><em style="color: #999">Copy, paste and email the survey link above to your audience.</em></div>
					</div>
				</div>
				
				<div style="height: 48px;">&nbsp;</div>
			</div>
		</div>
		
		<div id="placeholder_collector_details_purchase_respondents" style="clear: both; display: none;">
			
			<div id="placeholder_collector_details_purchase_respondents_statistic" style="display: none;">
				<div class="wrapper-content-left">
					<h3 class="ui-header-light">Collector Details</h3>
					<div style="padding: 12px 0 0">
						
						<!--
						<div class="params">
							<div class="param-name">Panel Id:</div>
							<div class="param-value"><span id="label_panel_id" style="color: #000"></span></div>
						</div>
						<div class="params">
							<div class="param-name">Panel Status:</div>
							<div class="param-value"><span id="label_panel_status"></span></div>
						</div>
						-->
						
						<div class="params">
							<div class="param-name">Completed:</div>
							<div class="param-value"><span id="label_panel_completed" style="color: #000">0</span></div>
						</div>
						<div class="params">
							<div class="param-name">Partial:</div>
							<div class="param-value"><span id="label_panel_partial" style="color: #000">0</span></div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
</div>


<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/split_button.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/collector_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/collector_view.js"></script>
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
			s = "The panel is live"; // open
			break;
		case 6 : 
			s = "Panel has been completed"; // closed
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

var getOrderDetails = function(params) {

	var obj = {
		cint : {
			getOrderDetails : {
				externalId : params.externalId
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
			if(data.cint.getOrderDetails != undefined) {
				if(data.cint.getOrderDetails.error != undefined) {
					
					errorHandler({
						error : data.cint.getOrderDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.cint.getOrderDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.cint.getOrderDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var accountId = <%=accountId%>;
var collectorId = <%=collectorId%>;
var messages = {
	closeCollector : "Close Collector",
	openCollector : "Open Collector",
	defaultCloseMessage : "This survey is currently closed.  Please contact the author of this survey for further assistance."
};

$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			
			new collectorDetails({
				collectorId : collectorId,
				accountId : accountId,
				absoluteUrl : absoluteUrl,
				elements : [],
				success : function(data) {
					
					new collectorView({
						collector : data,
						servletUrl : servletUrl,
						absoluteUrl : absoluteUrl
					});
					
				}
			});
			
			
			
		}
	});
	
});
</script>