<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
<%@page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>

<%@ page import="java.net.*" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>


<%
	String opinionId = request.getParameter("opinion_id");
		
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/datepicker/datepicker.daterange.css" />
<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/flags/flags.css" />

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important;display: inline"><a href="<%=absoluteURL %>/surveys" title="Surveys">Surveys</a>&nbsp;&rsaquo;&nbsp;<span id="label_survey_name"></span></h1><a href="javascript:;" id="link_rename_survey_name" title="Rename" style="margin-left: 6px;">Rename</a>
			</td>
			<td class="cell-right">
				<a href="<%=absoluteURL %>/surveys/create" title="Create Survey" class="button-green"><span><i class="icon-add-white">&nbsp;</i>Create Survey</span></a>
			</td>
		</tr>
	</table>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln">
			<li class="first-item"><a href="javascript:;" title="Copy" id="link_survey_copy">Copy</a></li>
			<li><a href="javascript:;" title="Delete" id="link_survey_delete">Delete</a></li>
			<li><a id="link_export" title="Export" style="display:none">Export</a></li>
			<li><a href="javascript:;" title="Distribute / Embed" id="link_survey_publish">Distribute / Embed</a></li>
			<li><a id="link_preview" title="Preview">Preview</a></li>
		</ul>
	</div>
	<div class="content-middle-tabs-section">
		<div class="content-middle-tabs">
			<ul class="content-middle-tabs-container">
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/edit" title="Edit"><span>Edit</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/settings" title="Settings"><span>Settings</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/reordering" title="Reordering"><span>Reordering</span></a></li>
				<li id="tab_rules"><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/rules" title="Rules"><span>Rules<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/branding" title="Branding"><span>Branding<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/collectors" title="Collectors"><span>Collectors</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/overall-results" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/statistics" class="selected" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="clear: both; padding-top: 20px;">
		
		<div>
			<h3 class="ui-header-light">Responses Activity</h3>
			<div style="clear: both;padding-top: 10px;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td valign="top">
							<div class="params">
								<div class="param-value">
									<div id="button_status"></div>
								</div>
								<div class="param-value" style="margin-left: 6px; margin-right: 6px;">vs</div>
								<div class="param-value">
									<div id="button_comparison_status"></div>
								</div>
							</div>	
						</td>
						<td valign="top">
							<div style="float: right; position: relative; text-align: left" id="daterange"></div>
						</td>
					</tr>
				</table>
			</div>
			<ul class="insights-statistics">
				<li>
					<div>
						<div>Responses</div>
						<div class="insights-statistics-figure label-started">0</div>
					</div>
				</li>
				<li>
					<div>
						<div>Completed</div>
						<div class="insights-statistics-figure label-completed">0</div>
					</div>
				</li>
				<li>
					<div>
						<div>Completion Rate<!-- <span class="glossary-tip"><a><sup>?</sup></a></span>--></div>
						<div class="insights-statistics-figure label-completion-rate">0.00%</div>
					</div>
				</li>
				<li>
					<div>
						<div>Partial</div>
						<div class="insights-statistics-figure label-partial">0</div>
					</div>
				</li>
				<li>
					<div>
						<div>Disqualified</div>
						<div class="insights-statistics-figure label-disqualified">0</div>
					</div>
				</li>
				<li>
					<div>
						<div>Avg. Time Taken</div>
						<div class="insights-statistics-figure label-avg-time-taken">00:00:00</div>
					</div>
				</li>
			</ul>
			<div id="chart" style="min-width: 450px; height: 140px; margin: 0 auto;"></div>
		</div>
		<div style="clear: both;padding-top: 20px;">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td style="width: 450px;" valign="top">
						<h3 class="ui-header-light">Respondents Geo-location</h3>
						<div style="padding-top: 10px;">
							<div id="vmap" style="width: 450px; height: 300px;"></div>
							<div class="demographic">
								<div class="demographic-min">0</div>
								<div class="demographic-bar">
									<div class="bar-inner"></div>
									<div class="bar-colors">
										<div class="color opacity-10"></div>
										<div class="color opacity-20"></div>
										<div class="color opacity-30"></div>
										<div class="color opacity-40"></div>
										<div class="color opacity-50"></div>
										<div class="color opacity-60"></div>
										<div class="color opacity-70"></div>
										<div class="color opacity-80"></div>
										<div class="color opacity-90"></div>
										<div class="color"></div>
									</div>
								</div>
								<div class="demographic-max">0</div>
							</div>
						</div>
					</td>
					<td valign="top" style="padding-left: 10px;">
						<h3 class="ui-header-light">Countries</h3>
						<div style="clear: both; padding-top: 12px;">
							<div id="table_countries"></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/underscore/underscore-min.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/dropdown_button.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/datepicker/datepicker.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/datepicker/datepicker.daterange.js"></script>


<script type="text/javascript" src="<%=applicationURL%>/scripts/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/highcharts/modules/data.js"></script>
<!--
<script type="text/javascript" src="<%=applicationURL%>/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/js/highcharts/modules/data.js"></script>
-->

<script type="text/javascript">
var chart = null;
</script>


<script type="text/javascript" src="<%=applicationURL%>/scripts/vmap/jqvmap/jquery.vmap.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/vmap/jqvmap/maps/jquery.vmap.world.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/vmap/jqvmap/data/jquery.vmap.sampledata.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_statistics.js"></script>
<script type="text/javascript">

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
				accountId : params.accountId,
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

var exportTo = function(params) {
	
	var data = {
		accountId : params.accountId,
	    format: params.format,
	    opinionId : params.opinionId,
	    name: params.name,
	    exportType : params.exportType
	};
	
	window.location = "<%=applicationURL%>/servlet/export?rq=" + JSON.stringify(data) + "&accountId=" + params.accountId;
	
};

var getActivityChart = function(params) {

	var obj = {
		opinions: { 
		    getActivityChart: {
		    	accountId : params.accountId,
		    	opinionId : params.opinionId,
				fromDate : params.fromDate,
				toDate : params.toDate,
				tpRangeId : params.graphBy
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
			if(data.opinions.getActivityChart != undefined) {
				if(data.opinions.getActivityChart.error != undefined) {
					
					errorHandler({
						error : data.opinions.getActivityChart.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getActivityChart);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getActivityChart);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
    
};

var getCountriesStatistics = function(params) {
	
	var obj = {
		responses: { 
			getCountriesStatistics: {
				accountId : params.accountId,
		    	opinionId : params.opinionId,
		    	collectorId : params.collectorId,
				fromDate : params.fromDate,
				toDate : params.toDate,
				includePartial : true
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
			if(data.responses.getCountriesStatistics != undefined) {
				if(data.responses.getCountriesStatistics.error != undefined) {
					errorHandler({
						error : data.responses.getCountriesStatistics.error	
					});
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.responses.getCountriesStatistics);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.responses.getCountriesStatistics);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var defaultState = {
	fromDate : null,
	toDate : null
};

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;

$(function() { 

	new surveyDetails({
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function(data) { 
			
			// statistics
			var statistics = new surveyStatistics({
				opinionId : <%=opinionId%>
			});
			
			// daterange
			$("#daterange").dateRange({
				ranges : [
				    { description: "Custom", value : { from : 0, to : 0 }, isCustom : true },
					{ description: "Today", value : { from : 0 }, isDefault : true },
					{ description: "Yesterday", value: { from : -1 } },
					{ description: "Last 7 days", value : { from : -7, to : 0 } },
					{ description: "Last 14 days", value : { from : -14, to : 0 } },
					{ description: "Last 30 days", value: { from : -30, to : 0 } },
					{ description: "All time", value : { to : 0 } }
				],
				change : function(data) {
				
					defaultState.fromDate = (data.fromDate != null ? data.fromDate.format(dateFormat.masks.isoDate) + " 00:00" : undefined);
					defaultState.toDate = data.toDate.format(dateFormat.masks.isoDate) + " 23:59";
					
					statistics.get({
						fromDate : defaultState.fromDate,
						toDate : defaultState.toDate 
					});
					
					
				},
				ready : function(data) {
					
					defaultState.fromDate = (data.fromDate != null ? data.fromDate.format(dateFormat.masks.isoDate) + " 00:00" : undefined);
					defaultState.toDate = data.toDate.format(dateFormat.masks.isoDate) + " 23:59";
						
					statistics.get({
						fromDate : defaultState.fromDate,
						toDate : defaultState.toDate 
					});
				
				}
			});
			
		}
	});
	
});
</script>
