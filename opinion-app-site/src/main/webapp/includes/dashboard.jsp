

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.opinion.dao.*" %>
<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.xnap.commons.i18n.I18n" %>
<%@ page import="org.xnap.commons.i18n.I18nFactory" %>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);
//Translations
Locale locale1 = new Locale(lang);
I18n i18n = I18nFactory.getI18n(this.getClass(), "messages", locale1);

String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>

<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/chosen/chosen.css" />
<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/datepicker/datepicker.daterange.css" />

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1><%= p.getHeader() %></h1>
			</td>
			<td class="cell-right">
				<a href="<%=absoluteURL %>/surveys/create" title="Create Survey" class="button-green" id="button_create_survey" style="display: none"><span><i class="icon-add-white">&nbsp;</i>Create Survey</span></a>
			</td>
		</tr>
	</table>
	<div>
		
		<div id="container_welcome" style="display: none;">
			<div style="clear: both;">
				<h3 class="ui-header-light">Getting started with Inqwise</h3>
				<div style="padding-top: 12px;">Begin by clicking "Create Survey", which is first among the options.</div>
			</div>
			<div style="padding-top: 12px;">
				<a href="<%=absoluteURL %>/surveys/create" title="<%=i18n.tr("Create Survey") %>" class="button-green"><span><i class="icon-add-white">&nbsp;</i><%=i18n.tr("Create Survey") %></span></a><span style="margin-left: 6px;">Make it easy, Create your first survey!</span>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
			</div>
		</div>
		
		<div id="container_activity" style="display: none;clear: both;">
			<div>
				<h3 class="ui-header-light">Responses Activity</h3>
				<div style="clear: both;padding-top: 10px;">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td valign="top">
								<div class="params">
									<div class="param-value" style="width: 200px; margin-right: 6px;">
										<select id="select_surveys" style="width:100%">
											<option value="0">All surveys</option>
										</select>
									</div>
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
				</ul>
				<div id="chart" style="min-width: 450px; height: 140px; margin: 0 auto;"></div>
			</div>
			<div style="clear: both; padding-top: 20px;">
				<h3 class="ui-header-light">Recent Surveys</h3>
				<div style="padding-top: 12px;">
				Below is a list of the 5 recently modified surveys ordered by modify date.
				</div>
				<div style="padding-top: 12px; clear: both; display: block;">
					<div id="table_recent_surveys"></div>
				</div>
			</div>
		</div>
	
		<!-- 
		<h3 class="ui-header">Account Details<a href="<%=absoluteURL %>/account" title="Edit Account Details">Edit</a></h3>
		<div style="padding: 0px;">
			<ul class="ll">
				<li>Current Plan: <b style="color: #333" id="label_plan"></b><span id="label_upgrade" style="margin-left: 6px;display: none"><a href="<%=absoluteURL %>/upgrade" title="Upgrade" id="link_upgrade">Upgrade</a></span></li>
				<li id="section_plan_expiration_date" style="display: none">Plan Expiration Date: <b style="color: #333" id="label_plan_expiration_date"></b></li>
				<li>Users: <b style="color: #333">1</b></li>
				<li class="panel-sessions-balance" style="display: none;">Remaining Responses: <b id="label_sessions_balance" style="color: #333"></b></li>
				<li class="panel-last-sessions-credit-date" style="display: none;">Responses Updated On: <b id="label_last_sessions_credit_date" style="color: #333"></b></li>
				<li class="panel-next-sessions-credit-date" style="display: none;">Next sessions credit will be in: <b id="label_next_sessions_credit_date" style="color: #333"></b> and your balance will be up to: <b id="label_next_sessions_credit_amount" style="color: #333"></b> sessions credits.</li>
			</ul>
		</div>
		 -->
		
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/dropdown_button.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/chosen/chosen.jquery.min.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/datepicker/datepicker.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/datepicker/datepicker.daterange.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/highcharts/modules/data.js"></script>
<script type="text/javascript">
var chart = null;
</script>

<script type="text/javascript">




/*

var lastChartActivityButton = null;
var initChart = function() {

	$('#section_total_respondent_activity').show();
	// results view events
	$('#select_chart_view').change(function() {
		setChart();
	});
	$('#button_chart_update_view').click(function() {
		getActivityChart(lastGraphBy);
	});

	$('ul#list_chart_activity li a').each(function(i, el) {
		if(i == 0) {
			$(el)
				.addClass('ui-button-active');
			lastChartActivityButton = $(el);
		}
		
		$(el).click(function() {
			if(lastChartActivityButton != null && lastChartActivityButton != $(this)) {
				lastChartActivityButton.removeClass('ui-button-active');
			}
			lastChartActivityButton = $(this);

			// do action
			$(this).addClass('ui-button-active');
			lastGraphBy = parseInt($(this).attr('graph_by'));
			getActivityChart(lastGraphBy);
		});
	});
	
	getActivityChart(lastGraphBy);
	
};
*/




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

var getList = function(params) {
	
	var obj = {
		opinions : {
			getList : {
				accountId : params.accountId,
				folderId : null,
				top : params.top,
				from : undefined,
				to : undefined,
				opinionTypeId : 1,
				orderByRecent: params.orderByRecent
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
        	if(data.opinions.getList != undefined) {
	        	if(data.opinions.getList.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.getList.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getList);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getList);
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

var updateAccountSettings = function(params) {

	var obj = {
		accounts : {
			updateAccountSettings : {
				accountId : params.accountId,
				showWelcomeMessage : params.showWelcomeMessage
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
			if(data.accounts.updateAccountSettings != undefined) {
				if(data.accounts.updateAccountSettings.error != undefined) {
					
					errorHandler({
						error : data.accounts.updateAccountSettings.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.accounts.updateAccountSettings);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.accounts.updateAccountSettings);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
    
};


/*
var getAccountServicePackageDetails = function() {
	
	var obj = {
		opinions: { 
			getAccountServicePackageDetails: {}
		}			
	};
	$.getJSON(servletUrl, { rq: JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
		if(data.opinions.getAccountServicePackageDetails.error != undefined) {
			
			if(data.opinions.getAccountServicePackageDetails.error == "NoPackageSelected"){
				
				var obj = {
					checkFreePackage : {}
				};
				
				$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
					if(data.checkFreePackage.error != undefined) {
						
						errorHandler({
							error : data.checkFreePackage.error	
						});
						
					} else {
						location.href = data.checkFreePackage.redirectUrl;
					}
				});	
			}
			
			errorHandler({
				error : data.opinions.getAccountServicePackageDetails.error	
			});
			
		} else {

			var settings = data.opinions.getAccountServicePackageDetails;
			
			
			if(auth.userInfo.plan != undefined) {
				$('#label_plan').text(auth.userInfo.plan.title);
				if(auth.userInfo.plan.isFree) {
					if(auth.userInfo.permissions.makePayment) {
						$('#label_upgrade').show();
					}
				} else {
					
					$('#section_plan_expiration_date').show();
					$('#label_plan_expiration_date').text(auth.userInfo.plan.planExpirationDate);
					
				}
			}
			
			if(settings.showWelcomeMessage) {
				
				var modal = new lightFace({
					title : "Welcome to the Inqwise.com",
					message : $("#message_welcome").html(),
					actions : [
						{ 
							label : "Close", 
							fire : function() { 

							// change flag
							updateAccountSettings({
								accountId : accountId,
								showWelcomeMessage : false,
								success : function() {
									
								},
								error: function() {
									
								}
							});
							
							modal.close();
						}, 
						color: "white" }
					],
					overlayAll : true
				});
				
				
			}
			
			
			
			if(settings.sessionsBalance != undefined) {
				$('.panel-sessions-balance').show();
				$('#label_sessions_balance').text(settings.sessionsBalance);
			}
			
			if(settings.lastSessionsCreditDate != undefined){
				$('.panel-last-sessions-credit-date').show();
				$('#label_last_sessions_credit_date').text(settings.lastSessionsCreditDate);
			}

			if(settings.nextSessionsCreditDate != undefined && settings.nextSessionsCreditAmount > 0){
				$('.panel-next-sessions-credit-date').show();
				$('#label_next_sessions_credit_date').text(settings.nextSessionsCreditDate);
				$('#label_next_sessions_credit_amount').text(settings.nextSessionsCreditAmount);
			}

			if(settings.visits != undefined){
				$('.panel-responses_statistics').show();
				// $('.lbl-visits-count').text(settings.visits);
				//$('.lbl-completed-responses-count').text(settings.complitedResponses);
				if(settings.completedPercentage != undefined) {
					// $('.lbl-completed-sessions-percentage').text(settings.completedPercentage);
				}
			}

							
							
							
							
							
							
			getRecentItemsList();

			
		}
	});
};
*/








/*
var now = new Date();
var before = new Date();
	before.setDate(now.getDate()-30);

var formatedDateRange = null;
var dateRange = [before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)]; //[before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)];
var newDateRange = [before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)]; //[before.format(dateFormat.masks.longDate),  now.format(dateFormat.masks.longDate)]; //[before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)];

var getDateRange = function() {
	var tmp = [];
	$.each(dateRange, function(n, v){
		tmp.push(v);
	});
	return tmp;
};

var getNewDateRange = function() {
	var tmp = [];
	$.each(newDateRange, function(n, v){
		tmp.push(v);
	});
	return tmp;
};

var parseDate = function(input, format) {
	format = format || 'yyyy-mm-dd'; // default format
	var parts = input.match(/(\d+)/g), 
	     i = 0, fmt = {};
	// extract date-part indexes from the format
	format.replace(/(yyyy|dd|mm)/g, function(part) { fmt[part] = i++; });
	return new Date(parts[fmt['yyyy']], parts[fmt['mm']]-1, parts[fmt['dd']]);
};

var formatDateRange = function(range) {
	var a = parseDate(range[0]);// new Date(range[0]);
	var b = parseDate(range[1]); // new Date(range[1]);
	return {
		fromDate : a.format(dateFormat.masks.mediumDate),
		toDate : b.format(dateFormat.masks.mediumDate)
	}
};
*/


/*
var parseDate = function(input, format) {
	format = format || 'yyyy-mm-dd'; // default format
	var parts = input.match(/(\d+)/g), 
	     i = 0, fmt = {};
	// extract date-part indexes from the format
	format.replace(/(yyyy|dd|mm)/g, function(part) { fmt[part] = i++; });
	return new Date(parts[fmt['yyyy']], parts[fmt['mm']]-1, parts[fmt['dd']]);
};
*/

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;

var defaultState = {
	fromDate : null,
	toDate : null
};

function buildChart(data){
	
	var started = [];
	var completed = [];
	var partial = [];
	var disqualified = [];
	
	for(var i = 0; i < data.charts.completed.length; i++) {
		started.push([Date.parse(data.charts.completed[i][0]), data.charts.completed[i][1] + data.charts.partial[i][1]]);
		completed.push([Date.parse(data.charts.completed[i][0]), data.charts.completed[i][1]]);
		partial.push([Date.parse(data.charts.partial[i][0]), data.charts.partial[i][1]]);
		disqualified.push([Date.parse(data.charts.partial[i][0]), 0]);
	}
	
	$('.label-started').text($.addCommas((data.charts.totals.completed + data.charts.totals.partial)));
	$('.label-completed').text($.addCommas(data.charts.totals.completed));
	$('.label-partial').text($.addCommas(data.charts.totals.partial));
	
	if((data.charts.totals.completed + data.charts.totals.partial) != 0) {
		$('.label-completion-rate').text(((data.charts.totals.completed / (data.charts.totals.completed + data.charts.totals.partial)) * 100).toFixed(2) + "%");
	} else {
		$('.label-completion-rate').text("0.00%");
	}
	
	//Date.UTC(+('20' + match[3]), match[1] - 1, +match[2])
	
	createChart(started, completed, partial, disqualified);
}

function createChart(started, completed, partial, disqualified) {
	
	var colors = Highcharts.getOptions().colors;
	var options = {
		chart: {
			/*type : 'area',*/
			renderTo: 'chart',
			style: {
				/*fontFamily: "open sans",*/
				/*margin: "auto",*/
				fontFamily: "arial,sans-serif",
				fontSize: "12px"
           	}
			/*,
           	events: {
                load: function(event) {
                    getChartData();
                }
            }
			*/
		},
		credits: {
		      enabled: false
		},
		title : {
			text : null
		},
		subtitle : {
			text : null
		},
		xAxis: {
			type : 'datetime',
			dateTimeLabelFormats: {
				month: '%e. %b',
				year: '%b'
			},
			labels : {
				style : {
					fontFamily: "arial,sans-serif",
					fontSize: '12px'
				},
				y :24
			}
			/*lineColor: 'red',*/
		    /*tickColor: 'red'*/
            /*tickInterval: 7 * 24 * 3600 * 1000, // one week*/
            /*
            tickWidth: 0,
            gridLineWidth: 1
            */
		},
		yAxis: [{ 
			title: { 
				text : null
			},
			labels : {
				style : {
					fontFamily: "arial,sans-serif",
					fontSize: '12px'
				},
				formatter: function() {
                    return Highcharts.numberFormat(this.value, 0);
                }
			},
			min: 0,
			gridLineColor: '#ccc'
		    /*minorGridLineColor: 'rgba(255,255,255,0.07)'*/
		},{
			linkedTo :0,
			gridLineWidth: 0,
			opposite : true,
			title: {
				text: null
			},
			labels : {
				style : {
					fontFamily: "arial,sans-serif",
					fontSize: '12px'
				},
				align : 'right',
				/*x: -3,*/
				/*y: 16,*/
				formatter: function() {
                    return Highcharts.numberFormat(this.value, 0);
                }
			}
		}],
		legend: {
            enabled: false
        },
		tooltip: {
			shared: true,
			crosshairs: true,
			style: {
                /*fontWeight: 'bold',*/
                fontFamily: "arial,sans-serif",
                fontSize: '12px'
            },
            borderRadius: 0
			/*
			formatter: function() {
				return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat('%e. %b', this.x) + ": " + this.y + " m";
			}
			*/
		},
		/*
		plotOptions: {
			series: {
                fillOpacity: 0.3
            }
        },
        */
		series:[{
			name: "Started",
			type : "area",
			fillOpacity: 0.1,
			data: started,
			color: '#3b5998',
			marker: {
                symbol: 'circle',
                radius: 3
            }
		},{
			name: "Completed",
			type : "area",
            /*fillColor : '#ebeef4',*/
            fillOpacity: 0.1,
			data:completed,
			color : '#8bbc21',
			marker: {
                symbol: 'circle',
                radius: 3
            }
		},{
			name: "Partial (Incomplete)",
			type : "area",
			fillOpacity: 0.1,
			data:partial,
			color : "#e78a3b",
			/*color: '#8bbc21', //'#57a610'*/
			marker: {
                symbol: 'circle',
                radius: 3
            }
		},{
			name : "Disqulified",
			type : "area",
			fillOpacity: 0.1,
			data: disqualified,
			color: '#a856a1',
			marker: {
                symbol: 'circle',
                radius: 3
            }
		}]
	};
	
	chart = new Highcharts.Chart(options);
	
	showStatuses($('#button_status').attr('data-value'), $('#button_comparison_status').attr('data-value'));
	
}

function showStatuses(status, comparison_status) {
	
	if(typeof chart != 'undefined') {
		var series = chart.series;
		
		// started
		if(status == 1 && (comparison_status == 0 || comparison_status == 1)) {
			if (!series[0].visible) {
				series[0].show();
			}
			series[1].hide();
			series[2].hide();
			series[3].hide();
		} else if(status == 1 && comparison_status == 2) {
			if (!series[0].visible) {
				series[0].show();
			}
			if (!series[1].visible) {
				series[1].show();
			}
			series[2].hide();
			series[3].hide();
		} else if(status == 1 && comparison_status == 3) {
			if (!series[0].visible) {
				series[0].show();
			}
			series[1].hide();
			if (!series[2].visible) {
				series[2].show();
			}
			series[3].hide();
		} else if (status == 1 && comparison_status == 4) {
			if (!series[0].visible) {
				series[0].show();
			}
			series[1].hide();
			series[2].hide();
			if (!series[3].visible) {
				series[3].show();
			}
		}
		
		// completed
		if(status == 2 && (comparison_status == 0 || comparison_status == 2)) {
			series[0].hide();
			if (!series[1].visible) {
				series[1].show();
			}
			series[2].hide();
			series[3].hide();
		} else if (status == 2 && comparison_status == 1) {
			if (!series[0].visible) {
				series[0].show();
			}
			if (!series[1].visible) {
				series[1].show();
			}
			series[2].hide();
			series[3].hide();
		} else if(status == 2 && comparison_status == 3) {
			series[0].hide();
			if (!series[1].visible) {
				series[1].show();
			}
			if (!series[2].visible) {
				series[2].show();
			}
			series[3].hide();
		} else if(status == 2 && comparison_status == 4) {
			series[0].hide();
			if (!series[1].visible) {
				series[1].show();
			}
			series[2].hide();
			if (!series[3].visible) {
				series[3].show();
			}
		}
		
		// partial (incomplete)
		if(status == 3 && (comparison_status == 0 || comparison_status == 3)) {
			series[0].hide();
			series[1].hide();
			if (!series[2].visible) {
				series[2].show();
			}
			series[3].hide();
		} else if(status == 3 && comparison_status == 1) {
			if (!series[0].visible) {
				series[0].show();
			}
			series[1].hide();
			if (!series[2].visible) {
				series[2].show();
			}
			series[3].hide();
		} else if(status == 3 && comparison_status == 2) {
			series[0].hide();
			if (!series[1].visible) {
				series[1].show();
			}
			if (!series[2].visible) {
				series[2].show();
			}
			series[3].hide();
		} else if(status == 3 && comparison_status == 4) {
			series[0].hide();
			series[1].hide();
			if (!series[2].visible) {
				series[2].show();
			}
			if (!series[3].visible) {
				series[3].show();
			}
		}
		
		
		// disqualified
		if(status == 4 && (comparison_status == 0 || comparison_status == 4)) {
			series[0].hide();
			series[1].hide();
			series[2].hide();
			if (!series[3].visible) {
				series[3].show();
			}
		} else if(status == 4 && comparison_status == 1) { // started
			if (!series[0].visible) {
				series[0].show();
			}
			series[1].hide();
			series[2].hide();
			if (!series[3].visible) {
				series[3].show();
			}
		} else if(status == 4 && comparison_status == 2) { // completed
			series[0].hide();
			if (!series[1].visible) {
				series[1].show();
			}
			series[2].hide();
			if (!series[3].visible) {
				series[3].show();
			}
		} else if(status == 4 && comparison_status == 3) { // partial
			series[0].hide();
			series[1].hide();
			if (!series[2].visible) {
				series[2].show();
			}
			if (!series[3].visible) {
				series[3].show();
			}
		}
		
		
		
	}
	
};


var opinionId = 0;
var surveys = [];
var pWin;

var recentSurveys = [];
var tableRecentSurveys = null;
var renderTableRecentSurveys = function() {
	
	$('#table_recent_surveys').empty();
	$('#table_recent_surveys').dataTable({
		tableColumns : [
			{ key : 'opinionId', label : '#', sortable : true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'opinionId', label : '', width: 12, formatter : function(cell, value, record, source) {
				return $("<a href=\"" + record.previewUrl + "\" title=\"Preview\" class=\"row-button\"><i class=\"row-icon-preview\">&nbsp;</i></a>").click(function() {
					
					var name = "pWin";
					this.target = name;
					
					if (pWin) 
						pWin.close();
					
					pWin = window.open('', name, 'resizable=1,scrollbars=1,status=1,menubar=1');
					
				}).tipsy({
					gravity: 'sw', 
					title: function() { 
						return this.getAttribute('original-title'); 
					}
				});
			}},
			{ key : 'name', label : 'Name', sortable : true, formatter : function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/surveys/" + record.opinionId + "/" + (record.started != 0 ? "overall-results" : "edit") + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key: 'started', label: 'Responses', sortable: true, sortBy : { key : 'started', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				if(record.started != 0) {
					return $("<a href=\"<%=absoluteURL %>/surveys/" + record.opinionId + "/overall-results\" title=\"" + record.started + "\">" + $.addCommas(record.started) + "</a>");
				}
				return record.started;
			}},
			{ key: 'completed', label: 'Completed', help : "A Completed responses indicates that the respondents clicked Finish on the last page of the survey.", sortable: true, sortBy : { key : 'completed', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.completed);
			}},
			{ key : 'completionRate', label : 'Completion Rate', help : "A Completion Rate is the number of completed responses divided by the number of total responses (completed + partial + disqualified) and expressed in percentage.<br/><br/><table><tr><td rowspan=\"2\">Completion Rate =&nbsp;</td><td align=\"center\" style=\"border-bottom: 1px solid #000\">Completed responses</td></tr><tr><td align=\"center\">(Completed + Partial + Disqualified)</td></tr></table><br/><strong>Example:</strong> If you have five completed responses from 1000 responses, then your Completion Rate is 0.5%.", sortable : true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				var completionRate;
				if(record.started != 0) {
					completionRate = ((record.completed / record.started) * 100).toFixed(2) + "%";
				} else {
					completionRate = "0.00%";
				}
				return completionRate;
            }},
			{ key: 'partial', label: 'Partial', help: "A Partial responses indicates that the respondents entered at least one answer, but they did not click Finish on the last page of the survey.", sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
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
            { key : 'lastResponseDate', label : 'Last Response Date', sortable : true, sortBy : { dataType: "date" }, formatter: function(cell, value, record, source) {
				if(record.lastResponseDate) {
					var left = record.lastResponseDate.substring(record.lastResponseDate.lastIndexOf(" "), " ");
					var right = record.lastResponseDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
            { key: 'modifyDate', label: 'Modify Date', sortable: true, sortBy : { dataType: "date" }, formatter: function(cell, value, record, source) {
				if(record.modifyDate) {
					var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
					var right = record.modifyDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		], 
		dataSource : recentSurveys,
		pagingStart : 5,
		disablePaging : true,
		disableFiltering : true
	});
	
};

function fillSurveys() {
	
	if(surveys.length != 0) {
		
		
		$('#button_create_survey').show();
		$('#container_activity').show();
		
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
				
				getActivityChart({
					accountId : accountId,
					opinionId : (opinionId != 0 ? opinionId : undefined),
					fromDate: defaultState.fromDate,
					toDate : defaultState.toDate,
					graphBy : 3,
					success : function(data) {
						buildChart(data);
					},
					error: function() {
						alert("ERR");
					}
				});
				
			},
			ready : function(data) {
				
				defaultState.fromDate = (data.fromDate != null ? data.fromDate.format(dateFormat.masks.isoDate) + " 00:00" : undefined);
				defaultState.toDate = data.toDate.format(dateFormat.masks.isoDate) + " 23:59";
				
				getActivityChart({
					accountId : accountId,
					opinionId : (opinionId != 0 ? opinionId : undefined),
					fromDate: defaultState.fromDate,
					toDate : defaultState.toDate,
					graphBy : 3,
					success : function(data) {
						buildChart(data);
					},
					error: function() {
						alert("ERR");
					}
				});
					
			}
		});
		
		
		
		// fill surveys
		var p = $('#select_surveys');
		p.bind('change', function() {
			
			opinionId = parseInt($(this).val());
			
			getActivityChart({
				accountId : accountId,
				opinionId : (opinionId != 0 ? opinionId : undefined), 
				fromDate: defaultState.fromDate,
				toDate : defaultState.toDate,
				graphBy : 3,
				success : function(data) {
					buildChart(data);
				},
				error: function() {
					alert("ERR");
				}
			});
			
		});
		
        var q = p[0].options;
        for(var z = 0; z < surveys.length; z++) {
    		var k = new Option(surveys[z].name, surveys[z].opinionId);
        	
            try {
                q.add(k)
            } catch (ex) {
                q.add(k, null)
            }
    	}
        
        
     	// surveys
        $('#select_surveys').chosen({});
        
     	
        // status
        $('#button_status').dropdownButton({
			actions : [
			    {
			    	label : "Started",
			    	legend : '#3b5998',
			    	value : 1,
			    	active : true,
					click : function(button) {
						//
					}
			    },
			    {
			    	label : "Completed",
			    	legend : '#8bbc21',
			    	value : 2,
					click : function(button) {
						//
					}
			    },
			    {
			    	label : "Partial (Incomplete)",
			    	legend : "#e78a3b",
			    	value : 3,
					click : function(button) {
						//
					}
			    },
			    {
			    	label : "Disqualified",
			    	legend : '#a856a1',
			    	value : 4,
			    	click : function(button) {
						//
					}
			    }
			],
			change : function(value) {
				showStatuses($('#button_status').attr('data-value'), $('#button_comparison_status').attr('data-value'));
			}
        });
        
        // comparison_status
        $('#button_comparison_status').dropdownButton({
			actions : [
			    {
			    	label : "None",
			    	value : 0,
			    	active : true,
					click : function(button) {
						//
					}
			    },
			    {
			    	label : "Started",
			    	legend : '#3b5998',
			    	value : 1,
					click : function(button) {
						//
					}
			    },
			    {
			    	label : "Completed",
			    	legend : '#8bbc21',
			    	value : 2,
					click : function(button) {
						//
					}
			    },
			    {
			    	label : "Partial (Incomplete)",
			    	legend : "#e78a3b",
			    	value : 3,
					click : function(button) {
						//
					}
			    },
			    {
			    	label : "Disqualified",
			    	legend : '#a856a1',
			    	value : 4,
			    	click : function(button) {
						//
					}
			    }
			],
			change : function() {
				showStatuses($('#button_status').attr('data-value'), $('#button_comparison_status').attr('data-value'));
			}
        });
        
        
        
        /*
        // update view
        $('#button_chart_update_view').click(function(e) {
        	
        	getActivityChart({
				opinionId : (opinionId != 0 ? opinionId : undefined), 
				fromDate: defaultState.fromDate,
				toDate : defaultState.toDate,
				graphBy : 3,
				success : function(data) {
					buildChart(data);
				},
				error: function() {
					//
				}
			});
        	
        	e.preventDefault();
        });
        */
        
        
        
        
        
        // recent surveys
        var u = surveys;
        var c = "modifyDate";
        
        u.sort(function (a, b) {
        	var x = Date.parse(a[c]);
            var y = Date.parse(b[c]);
            if (isNaN(x) || x === "") {
                x = Date.parse("01/01/1970 00:00:00")
            }
            if (isNaN(y) || y === "") {
                y = Date.parse("01/01/1970 00:00:00")
            }
            return y - x;
        });
        
        
        
        if(surveys.length > 5) {
	        for(var i = 0; i < 5; i++) {
	        	recentSurveys.push(u[i]);
	        }
        } else {
        	recentSurveys = u;
        }
        
        
        renderTableRecentSurveys();
		
        
		
	} else {
		
		$('#container_welcome').show();
		
	}
	
};

$(function() {
	
	getList({
		accountId : accountId,
		top: 100,
		success : function(data) {
			surveys = data.list;
			fillSurveys();
		},
		error : function() {
			surveys = [];
			fillSurveys();
		}
	});
	
});
</script>