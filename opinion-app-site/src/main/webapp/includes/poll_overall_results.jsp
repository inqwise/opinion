
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%

	String opinionId = request.getParameter("opinion_id");
		
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();

%>

<link rel="stylesheet" href="<%=applicationURL%>/css/sidebar/sidebar.css" type="text/css" />

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
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/overall-results" class="selected" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 20px;">
		<div class="wrapper-content-left sidebar-relative">
			<div>
				<h3 class="ui-header-light">Results</h3>
				<div style="clear: both; padding-top: 12px;">
					<div>
						<div id="placeholder_results">
							<ul class="list-result-controls"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="wrapper-content-middle-right">
			<div style="width: 450px;" class="sidebar">
				<div style="float: left; width: 220px;">
					<div>
						<h3 class="ui-header">Statistics <a href="<%=absoluteURL %>/polls/<%=opinionId %>/statistics" title="View">View</a></h3>
						<div style="padding: 12px 0 0 8px; clear: both;">
							<!-- Countries: <b>4</b><br/>
							Started : <b style="color: #333" id="label_responses_started"></b><br/>
							Completed : <b style="color: #333" id="label_responses_completed"></b><br/>
							Partial (Incomplete): <b style="color: #333" id="label_responses_partial"></b><br/>
							Completion Rate : <b style="color: #333" id="label_responses_completion_rate"></b>
							Avg. Time Taken : <b style="color: #333" id="label_responses_average_time_taken">3 mins</b> -->
							<ul class="ll" style="margin: 0px;">
								<li>Started: <b id="label_responses_started" style="color: #333">0</b></li>
								<li>Completed: <b id="label_responses_completed" style="color: #333">0</b></li>
								<li>Partial (Incomplete): <b id="label_responses_partial" style="color: #333">0</b></li>
								<li>Completion Rate: <b id="label_responses_completion_rate" style="color: #333">0%</b></li>
								<li>Avg. Time Taken: <b id="label_responses_average_time_taken" style="color: #333"></b></li>
								<!-- <li>Countries: <b id="label_responses_countries" style="color: #333"></b></li> -->
							</ul>
						</div>
					</div>
					<!--
					<h3 class="ui-header">Filter</h3>
					<div style="padding: 12px 0 0 8px; clear: both;">
						...filter
					</div>
					-->
				</div>
				<div style="float: left; width: 220px; padding-left: 10px;">
					<h3 class="ui-header">Export Results</h3>
					<div style="clear: both; padding: 12px 0 0 8px">
						<div>To export your results, select your preferred download format <br/>and click "Export".</div>
						<div class="row" style="padding-top: 12px;">
							<!-- <div class="cell">Export to:</div> -->
							<div class="cell">
								<select id="select_export_format">
									<option value="excel">Excel (*.XLSX)</option>
									<option value="csv">CSV</option>
								</select>
							</div>
							<div class="cell" style="padding-left: 6px;">
								<a id="button_export" class="button-white" title="Export"><span><b class="icon-down">&nbsp;</b>Export</span></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/sidebar/sidebar.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_overall_results_control.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_overall_results.js"></script>

<script type="text/javascript">

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

var getAnswererResultsText = function(params) {
	
	var obj = {
		responses : { 
			getAnswererResultsText : {
				opinionId : params.opinionId,
				controlId : params.controlId,
				optionId : params.optionId,
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
        	if(data.responses.getAnswererResultsText.error != undefined) {
        		
        		errorHandler({
					error : data.responses.getAnswererResultsText.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.responses.getAnswererResultsText);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.responses.getAnswererResultsText);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};






/*
var demographicColors = ["#f2f7e6","#e6f1ce","#dbeab9","#d1e5a6","#c7df92","#beda80","#b2d36b","#a7cd56","#9ec844","#8fbf27"];

var data = {
	countries : [
		{ country : "russia", started: 8, completed : 7, partial : 1 },
		{ country : "mexico", started: 1, completed : 0, partial: 1 },
		{ country : "india", started: 12, completed : 7, partial : 5 },
		{ country : "estonia", started: 3, completed: 3, partial : 3 },
		{ country : "usa", started: 7, completed: 1, partial : 6 },
		{ country : "australia", started: 9, completed: 4, partial : 5 },
		{ country : "israel", started: 1, completed: 1, partial : 1 }
	],
	max : 12,
	total : 31
};
*/
// Math.round(1 / 39 * 100)

/*
var findCountry = function(country) {
	for(var i = 0; i < data.countries.length; i++) {
		if(data.countries[i].country == country) {
			return data.countries[i]; 
		}
	}
	return null;
};

var initWorld = function() {
	
	// countries
	var W = Raphael("svg", 680, 390);
	
	
	W.setStart();


	for (var country in countries) {
        var c = W.path(countries[country]);
        
        
        c.attr({
            fill: "#f5f5f5", // #fff
            stroke: "#bbb", // #29447E
            "stroke-width": 1,
			"stroke-linejoin": "round" // "cursor" : "pointer",
        }).hover(function(){
        	// alert(this.data("country").name);
			this.attr({
				stroke: "#666"
			});
		}, function(){
			this.attr({
				stroke: "#bbb"
			});
		}).data( "country", { name : country });
        
        
        
        
        
        var d = findCountry(country);
        if(d != null) {
        	c.attr({ 
        		//fill : demographicColors[Math.round((Math.round(d.started / data.max * 100) / 11))],
        		fill : "#57a610", // #64AD21 // #8fbf27
        		"fill-opacity" : (Math.round((Math.round(d.started / data.max * 100) / 10)) == 10 ? "1" : "0." + Math.round((Math.round(d.started / data.max * 100) / 10))),
        		stroke : "#65ad22" // "#26437B" // 65ad22 // #8fbf27
        	}).hover(function(){
        		this.attr({
					stroke: "#5C8718"
				});
			}, function(){
				this.attr({
					stroke: "#65ad22" // "#8fbf27"
				});
			});
        	
        	
        	
        	
        	
        	
        	
        	//$("<li>" + d.started + " -> " + Math.round((Math.round(d.started / data.max * 100) / 11)) + " - " + d.country + " - " + data.max +  "</li>").appendTo("#list_countries");
        }
        
        
        
        
        // min
        $(".demographic-min").text((data.countries.length != 0 ? 1 : 0));
        // max
        $(".demographic-max").text(data.max);
        
    }
	
	var world = W.setFinish();
	
};
*/

var exportTo = function(params) {
	
	var data = {
	    format: params.format,
	    opinionId : params.opinionId,
	    name: params.name,
	    exportType : params.exportType
	};
	
	window.location = "<%=applicationURL%>/servlet/export?rq=" + JSON.stringify(data);
	
};

$(function() {
	
	new pollDetails({
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function() {
			
			$('#button_export').click(function(e) {
				
				
				var v = null;
				var M = $("<div>" +
					"<div style=\"padding: 0 0 12px 0\">Enter a filename.</div>" +
					"<div class=\"row\">" +
						"<div class=\"cell\">" +
							"<div><input type=\"text\" id=\"text_filename\" name=\"rename_filename\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
							"<div><label id=\"status_filename\"></label></div>" +
						"</div>" +
					"</div>" +
				"</div>");
				var I = M.find('#text_filename');
				var B = M.find('#status_filename');
				
				var modal = new lightFace({
					title : "Export results",
					message : M,
					actions : [
					   { 
						   label : "Export", 
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
						
						var now = new Date();
						
						// set default values
						I.val("Results_<%=opinionId%>_" + now.format("yyyymmdd_HHMM")).select();
						
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
								
								exportTo({
									format: $("#select_export_format").val(),
									opinionId : <%=opinionId%>,
									name : $.removeHTMLTags(I.val().replace(/[/\\:?<>|\"]+/g, "")).replace(/\r/g, "")
								});
								
								modal.close();
								
							},
							error: function() {
								// error
							}
						});
					}
				
				});
				
				e.preventDefault();
				
			});
			
			// overall results
			new pollOverallResults({
				opinionId : <%=opinionId%>,
				callback : function(data) {
					
					// sidebar
					new sidebar({});
					
					// statistics
					$('#label_responses_started').text(data.completed + data.partial);
					$('#label_responses_completed').text(data.completed);
					$('#label_responses_partial').text(data.partial);
					if((data.completed + data.partial) != 0) {
						$('#label_responses_completion_rate').text(Math.round(data.completed / (data.completed + data.partial) * 100) + "%");
					} else {
						$('#label_responses_completion_rate').text("0%");
					}
					//$('#label_responses_average_time_taken').text();
					
				}
			});
			
		}
	});
	
	
});

</script>