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
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/overall-results" class="selected" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 20px; min-height: 500px;">
		<div class="wrapper-content-left sidebar-relative">
			<div>
				<div style="height: 24px;">
					<div id="button_pages"></div>
				</div>
				<div style="clear: both; padding-top: 12px;">
					<div id="placeholder_results">
						<ul class="list-result-controls"></ul>
					</div>
				</div>
			</div>
		</div>
		<div class="wrapper-content-middle-right">
			<div style="width: 450px;" class="sidebar">
				<div style="float: left; width: 220px;">
					<div>
						<h3 class="ui-header">Summary</h3>
						<div style="padding: 12px 0 0 8px; clear: both;">
							<ul class="ll" style="margin: 0px;">
								<li>Responses: <b id="label_responses_started" style="color: #333">0</b></li>
								<li>Completed: <b id="label_responses_completed" style="color: #333">0</b></li>
								<li>Completion Rate: <b id="label_responses_completion_rate" style="color: #333">0.00%</b></li>
								<li>Partial (Incomplete): <b id="label_responses_partial" style="color: #333">0</b></li>
								<li>Disqualified: <b id="label_responses_disqualified" style="color: #333">0</b></li>
								<li>Avg. Time Taken: <b id="label_responses_average_time_taken" style="color: #333"></b></li>
							</ul>
						</div>
					</div>
				</div>
				<div style="float: left; width: 220px; padding-left: 10px;">
					
					<!--
					<div style="clear: both; Xpadding-top: 24px;">
						<h3 class="ui-header">Organize Questions</h3>
						<div style="padding: 12px 0 0 8px; clear: both;">
							<div>Select the questions you would like to see on report.</div>
							<div>
								<label><span><input type="checkbox" autocomplete="off"/></span>Show only</label>
							</div>
							<div style="overflow-y:auto; max-height: 200px; border: 1px solid #999; background: #fff; margin-top: 10px;">
								<ul id="list_option_controls"></ul>
							</div>
						</div>
					</div>
					-->
					
					<div style="clear: both; Xpadding-top: 24px;">
						<h3 class="ui-header">Export Results</h3>
						<div style="clear: both; padding: 12px 0 12px 8px">
							<div>To export your results, select your preferred download format <br/>and click "Export".</div>
							<div class="row" style="padding-top: 12px;">
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
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/sidebar/sidebar.js"></script>
<!-- <script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/menu_button.js"></script> -->
<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/dropdown_button.js"></script>

<!--
<script type="text/javascript" src="<%=applicationURL%>/scripts/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/highcharts/modules/data.js"></script>
-->

<script type="text/javascript" src="<%=applicationURL%>/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/js/highcharts/modules/data.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_overall_results_control.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_overall_results.js"></script>

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

var getAnswererResultsText = function(params) {
	
	var obj = {
		responses : { 
			getAnswererResultsText : {
				accountId : params.accountId,
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

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;
var sheets = [];

$(function() {

	new surveyDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback: function(data) {
			
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
									accountId : accountId,
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
			new surveyOverallResults({
				opinionId : <%=opinionId%>
			});
			
			// sidebar
			new sidebar({});
			
		}
	});

	
});
</script>