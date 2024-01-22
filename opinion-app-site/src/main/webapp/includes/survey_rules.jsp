
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
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/rules" class="selected" title="Rules"><span>Rules</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/branding" title="Branding"><span>Branding</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/collectors" title="Collectors"><span>Collectors</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/overall-results" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 20px; clear: both;">
		<div class="ui-tabs-section">
			<div>
				<ul class="ui-tabs" id="tabs">
					<li><a href="#form_page_rules" title="Page Rules">Page Rules</a></li>
					<li><a href="#form_survey_rules" title="Survey Rules">Survey Rules</a></li>
				</ul>
			</div>
		</div>
		<div style="clear: both; display: none;" id="form_page_rules">
			<div style="padding: 10px 0;">Skip to a specific page based on these rules.</div>
			<div id="page_rules_error" class="error-box" style="display: none">Your survey has no valid questions. Add other types of questions to your survey to create rules.</div>
			<ul class="page-rules"></ul>
			<div>
				<a href="#" title="Add rule" id="button_add_page_rule" style="display: none">+ Add rule</a>
			</div>
			<div style="padding-top: 20px">
				<a href="#" title="Save" class="button-blue" id="button_save_page_rules" style="display: none"><span>Save</span></a>
			</div>
		</div>
		<div style="clear: both; display: none;" id="form_survey_rules">
			<div style="padding: 10px 0;">After the survey is submitted, override the default action based on these rules.</div>
			<div id="survey_rules_error" class="error-box" style="display: none">Your survey has no valid questions. Add other types of questions to your survey to create rules.</div>
			<div id="survey_rules_warning" class="warning-box" style="display: none">Survey rules are only available to Paid accounts. You'll need to <a href="<%=absoluteURL %>/upgrade" title="Upgrade">upgrade</a> to use them.</div>
			<ul class="survey-rules"></ul>
			<div>
				<a href="#" title="Add rule" id="button_add_survey_rule" style="display: none">+ Add rule</a>
			</div>
			<div style="padding-top: 20px">
				<a href="#" title="Save" class="button-blue" id="button_save_survey_rules" style="display: none"><span>Save</span></a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/business-rules/conditions-builder.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/business-rules/actions-builder.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/business-rules/rule-engine.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>
<!-- <script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_rules.js"></script> -->

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

var getSheetsAndControls = function(params) {

	var obj = {
		controls : {
			getSheetsAndControls: { 
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
        	if(data.controls.getSheetsAndControls != undefined) {
	        	if(data.controls.getSheetsAndControls.error != undefined) {
					
	        		errorHandler({
						error : data.controls.getSheetsAndControls.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.getSheetsAndControls);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.getSheetsAndControls);
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
            
        }
    });	 
};





var sheets = [];


function getOperatorsByControlTypeId(controlTypeId) {
	
	var operators = [];
	
	switch(controlTypeId) {
		case 2:
			// options
			operators = [
				/*{ label : "is present", name : "present", fieldType : "none" },
				{ label : "is blank", name : "blank", fieldType : "none" },*/
				{ label : "is equal to", name : "equalTo", fieldType : "select" },
				{ label : "is not equal to", name : "notEqualTo", fieldType : "select" }          
			];
			
			break;
		case 3:
			// text
			operators = [
				/*{ label : "is present", name : "present", fieldType : "none" },
				{ label : "is blank", name : "blank", fieldType : "none" },*/
				{ label : "is equal to", name : "equalTo", fieldType : "text" },
				{ label : "is not equal to", name : "notEqualTo", fieldType : "text" },
				{ label : "includes", name : "includes", fieldType : "text" },
				{ label : "matches regex", name : "matchesRegex", fieldType : "text" }
			];
			
			break;
	}
	
	return operators;
	
};

function getDefaultConditionValueByControl(control) {
	var defaultValue = "";
	switch(control.controlTypeId) {
		case 2: 
			// options
			if(control.options.list.length != 0) {
				defaultValue = control.options.list[0].optionId;
			}
			break;
		case 3:
			defaultValue = "";
			break;
	}
	return defaultValue;
};

function removeFromArray(array, from, to) {
	if ($.isArray(array)) {
        var rest = array.slice((to || from) + 1 || array.length);
        array.length = from < 0 ? array.length + from : from;
        return array.push.apply(array, rest);
    }	
};






/*
var __pageRules = [
	{
		conditions: {"all":[{"name":"14144","operator":"equalTo","value":"Basil"},{"name":"14143","operator":"equalTo","value":"23139"}]},
		actions : [{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"finish"}]}] //[{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"2591"}]}]
	},
	{
		conditions : {"all":[{"name":"14145","operator":"equalTo","value":"23142"}]},
		actions : [{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"2590"}]}]
	}
];
*/

/*
//Check Rules -> CHECKBOX
var __pageRules = [
	{
		conditions : {"all":[{"name":"14143","operator":"equalTo","value":"23138"}, {"name":"14143","operator":"equalTo","value":"23139"}]},
		actions :  [{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"2591"}]}]
   	},
   	{
   		conditions : {"all":[{"name":"14145","operator":"equalTo","value":"23142"}]},
   		actions : [{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"2590"}]}]
   	}
];
*/

/*
// Check Rules -> DROPDOWN
var __pageRules = [
	{
		conditions : {"all":[{"name":"14141","operator":"equalTo","value":"23135"}]},
		actions : [{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"2587"}]}]
	}
];
*/                 
                   
/*
// Check Rules -> TEXT
var __pageRules = [
	{
		conditions : {"all":[{"name":"14138","operator":"equalTo","value":"Basil"}]},
		actions : [{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"2584"}]}]
	}
];
*/

/*
// Check Rules -> RADIO
var __pageRules = [
	{ 
		conditions : {"all":[{"name":"14134","operator":"equalTo","value":"23119"},{"name":"14137","operator":"equalTo","value":"23127"}]},
		actions : [{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"2579"}]}]
	}
];
*/




var __pageRules = [];
/*
//flickr
var __pageRules = [
	{
		conditions : {"all":[{"name":"14159","operator":"equalTo","value":"23150"}]},
		actions : [{"name":"action-select","value":"skipTo","fields":[{"name":"fieldId","value":"2596"}]}]
	}
];
*/
var pageRules = [];

function updatePageRuleNumbering() {
	$("ul.page-rules li").each(function(i, el) {
		$(el).find(".rule-number").text(i + 1);
	});
	
	if(pageRules.length != 0) {
		$("#button_save_page_rules").show();
	} else {
		$("#button_save_page_rules").hide();
	}
};

function addPageRule(conditionsData, actionsData) {
	
	var conditions,
	actions;
	
	var rule = $("<li>" +
		"<div class=\"page-rule\">" +
			"<div class=\"rule-number\">0</div>" +
			"<div class=\"rule-conditions\"></div>" +
			"<div><strong>Then do the following actions:</strong></div>" +
			"<div class=\"rule-actions\" style=\"margin-top: 6px;\"></div>" +
			"<div><a href=\"#\"class=\"delete-rule\" title=\"Delete this rule\">Delete this rule</a></div>" +
		"</div>" +
	"</li>").appendTo("ul.page-rules");
	
	rule.find(".delete-rule").click(function(event) {
		
		// remove from array
		removeFromArray(pageRules, $(this).closest("li").index());
		
		// remove from html
		$(this).closest("li").remove();
		
		updatePageRuleNumbering();
		
		event.preventDefault();
	});
	
	conditions = rule.find(".rule-conditions");
    actions = rule.find(".rule-actions");
    

    pageRules.push({
    	conditions : conditions,
    	actions : actions
    });
    
    updatePageRuleNumbering();
    
    
    
 	// conditions
    _fields = [];
 	
    _defaultConditionsData = [];
    
    // actions
    _actionOptions = [];
    
    _defaultActionsData = [];
    
    var sheetCount = 0;
    var controlCount = 0;
    var defaultControl = null;
    
    
    function matchControl(control) {
    	var obj = null;
    	if(control.controlTypeId == 2 
    			|| control.controlTypeId == 3) {
    		obj = control;
    	}
    	return obj;
	}
    
    for(var i = 0; i < sheets.length; i++) {
    	for(var y = 0; y < sheets[i].controls.length; y++) {
    		
    		// radio, checkbox, select, text, textarea
    		if(sheets[i].controls[y].controlTypeId == 2 
    			|| sheets[i].controls[y].controlTypeId == 3) {
    			
    			if(defaultControl == null) {
    				defaultControl = sheets[i].controls[y];
    			}

        		// for conditions
        		var obj = {
       				label : (controlCount + 1) + ". " + sheets[i].controls[y].content,
           			name : sheets[i].controls[y].controlId,
           			operators : getOperatorsByControlTypeId(sheets[i].controls[y].controlTypeId)
        		};
        		
        		// options
        		if(sheets[i].controls[y].options != undefined) {
        			
        			obj.options = [];
        			
        			var otherOption = null;
        			for(var x = 0; x < sheets[i].controls[y].options.list.length; x++) {
        				
        				if(sheets[i].controls[y].options.list[x].optionKindId == 0) {
    	    				obj.options.push({
    	    					label : sheets[i].controls[y].options.list[x].text,
    	    					name : sheets[i].controls[y].options.list[x].optionId
    	    				});
        				}
        				
        				// other option
        				if(sheets[i].controls[y].options.list[x].optionKindId == 1) {
        					otherOption = sheets[i].controls[y].options.list[x];
        				}
        				
        			}
        			
        			if(otherOption != null) {
        				obj.options.push({
        					label : otherOption.text,
        					name : otherOption.optionId
        				});
        			}
        			
        		}
        		
        		_fields.push(obj);
        		
        		// defaults
        		if(defaultControl != null) {
        			
        			if(defaultControl.controlId == sheets[i].controls[y].controlId) {
	        			// conditions
	        			var defaultCondition = {
	           				name : defaultControl.controlId,
	           				operator : "equalTo",
	           				value : getDefaultConditionValueByControl(defaultControl)
	           			};
	        			
	        			_defaultConditionsData.push(defaultCondition);
	        			
	        			// actions
	        			var defaultAction = { 
	     					name: "action-select", 
	     					value: "skipTo", 
	     					fields: []
	     				};
	        			
	        			_defaultActionsData.push(defaultAction);
        			
        			}
        			
        		}
    			
    		}
    		
    		// count only countable controls
    		if(sheets[i].controls[y].controlTypeId != 11 
    			&& sheets[i].controls[y].controlTypeId != 12) {
    			controlCount++;
    		}
    		
    	}
    	
    	
    	// for actions
		var actionOption = {
			label : "Page " + (sheetCount + 1) + (sheets[i].title != undefined ? ": " + sheets[i].title : ""),
      		name : sheets[i].sheetId
		};
		
		_actionOptions.push(actionOption);
		
		
		sheetCount++;
    	
    }
    
    // additional actions
	_actionOptions.push({
		label : "End of survey",
 		name : "finish"
	});
	_actionOptions.push({
		label : "Disqualification page",
 		name : "disqualification"
	});
   
	
    
    initializeConditions();
    initializeActions();
    
    function initializeConditions() {
    	// questions / fields
        conditions.conditionsBuilder({
        	fields : _fields,
        	data : (conditionsData != undefined ? conditionsData : { "all" : _defaultConditionsData })
        });
    };
    
    function initializeActions() {
    	
    	actions.actionsBuilder({
    		fields : [
    		   	{
    		   		label: "Skip to", 
    		   		name: "skipTo", 
    		   		fields: [
						{ 
							label: "Field", 
							name: "fieldId", 
							fieldType: "select", 
							options : _actionOptions
						}
					]
    		   	}
    		],
    		data: (actionsData != undefined ? actionsData : _defaultActionsData)
    	});
    	
    };
	
};




var __surveyRules = [];

/*
var __surveyRules = [
	{
		conditions: {"all":[{"name":"14155","operator":"equalTo","value":"Basil"}]},
		actions : [{"name":"action-select","value":"showMessage","fields":[{"name":"fieldId","value":"Thank you for taking the survey!\nYour response is important to us and we very thank you.\n\nQuestions? Concerns? Contact us day or night: support@inqwise.com\nThe Inqwise Team"}]}] // [{"name":"action-select","value":"redirectTo","fields":[{"name":"fieldId","value":"http://www.google.com"}]}]
 	}                     
];
*/

var surveyRules = [];
function updateSurveyRuleNumbering() {
	$("ul.survey-rules li").each(function(i, el) {
		$(el).find(".rule-number").text(i + 1);
	});
	if(surveyRules.length != 0) {
		$("#button_save_survey_rules").show();
	} else {
		$("#button_save_survey_rules").hide();
	}
};

function addSurveyRule(conditionsData, actionsData) {
	
	var conditions,
	actions;
	
	var rule = $("<li>" +
		"<div class=\"survey-rule\">" +
			"<div class=\"rule-number\">0</div>" +
			"<div class=\"rule-conditions\"></div>" +
			"<div><strong>Then do the following actions:</strong></div>" +
			"<div class=\"rule-actions\" style=\"margin-top: 6px;\"></div>" +
			"<div><a href=\"#\"class=\"delete-rule\" title=\"Delete this rule\">Delete this rule</a></div>" +
		"</div>" +
	"</li>").appendTo("ul.survey-rules");
	
	rule.find(".delete-rule").click(function(event) {
		
		// remove from array
		removeFromArray(surveyRules, $(this).closest("li").index());
		
		// remove from html
		$(this).closest("li").remove();
		
		updateSurveyRuleNumbering();
		
		event.preventDefault();
	});
	
	conditions = rule.find(".rule-conditions");
    actions = rule.find(".rule-actions");
    
    surveyRules.push({
    	conditions : conditions,
    	actions : actions
    });
    
    updateSurveyRuleNumbering();
    
    
 	// conditions
    _fields = [];
 	
    _defaultConditionsData = [];
    
    // actions
    _actionOptions = [];
    
    _defaultActionsData = [];
    
    var sheetCount = 0;
    var controlCount = 0;
    var defaultControl = null;
    
    
    for(var i = 0; i < sheets.length; i++) {
    	for(var y = 0; y < sheets[i].controls.length; y++) {
    		
    		// radio, checkbox, select, text, textarea
    		if(sheets[i].controls[y].controlTypeId == 2 
    			|| sheets[i].controls[y].controlTypeId == 3) {
    			
    			if(defaultControl == null) {
    				defaultControl = sheets[i].controls[y];
    			}
    			
    			// for conditions
        		var obj = {
       				label : (controlCount + 1) + ". " + sheets[i].controls[y].content,
           			name : sheets[i].controls[y].controlId,
           			operators : getOperatorsByControlTypeId(sheets[i].controls[y].controlTypeId)
        		};
        		
        		// options
        		if(sheets[i].controls[y].options != undefined) {
        			
        			obj.options = [];
        			
        			var otherOption = null;
        			for(var x = 0; x < sheets[i].controls[y].options.list.length; x++) {
        				
        				if(sheets[i].controls[y].options.list[x].optionKindId == 0) {
    	    				obj.options.push({
    	    					label : sheets[i].controls[y].options.list[x].text,
    	    					name : sheets[i].controls[y].options.list[x].optionId
    	    				});
        				}
        				
        				// other option
        				if(sheets[i].controls[y].options.list[x].optionKindId == 1) {
        					otherOption = sheets[i].controls[y].options.list[x];
        				}
        				
        			}
        			
        			if(otherOption != null) {
        				obj.options.push({
        					label : otherOption.text,
        					name : otherOption.optionId
        				});
        			}
        			
        		}
        		
        		_fields.push(obj);
        		
        		// defaults
        		if(defaultControl != null) {
        			
        			if(defaultControl.controlId == sheets[i].controls[y].controlId) {
	        			// conditions
	        			var defaultCondition = {
	           				name : defaultControl.controlId,
	           				operator : "equalTo",
	           				value : getDefaultConditionValueByControl(defaultControl)
	           			};
	        			
	        			_defaultConditionsData.push(defaultCondition);
	        			
	        			
	        			/*
	        			// actions
	        			var defaultAction = { 
	     					name: "action-select", 
	     					value: "redirectTo", 
	     					fields: []
	     				};
	        			*/
	        			
	        			_defaultActionsData.push({ 
	     					name: "action-select", 
	     					value: "showMessage", 
	     					fields: [
	     					   { name : "fieldId", value : "Thank you for taking the survey!" }
	     					]
	     				});
        			
        			}
        			
        		}
    		}
    	
    		// count only countable controls
    		if(sheets[i].controls[y].controlTypeId != 11 
    			&& sheets[i].controls[y].controlTypeId != 12) {
    			controlCount++;
    		}
    		
    	}
    	
    	sheetCount++;
    }
    
    initializeConditions();
    initializeActions();
    
    function initializeConditions() {
    	// questions / fields
        conditions.conditionsBuilder({
        	fields : _fields,
        	data : (conditionsData != undefined ? conditionsData : { "all" : _defaultConditionsData })
        });
    };
    
	function initializeActions() {
    	
    	actions.actionsBuilder({
    		fields : [
				{
				   	label: "Show Message",
				   	name: "showMessage", 
				   	fields: [
						{ 
							label: "Field", 
							name: "fieldId", 
							fieldType: "textarea"
						}
					]
				},
    		   	{
    		   		label: "Redirect to custom URL", 
    		   		name: "redirectTo", 
    		   		fields: [
						{ 
							label: "Field", 
							name: "fieldId", 
							fieldType: "text"
						}
					]
    		   	}
    		],
    		data: (actionsData != undefined ? actionsData : _defaultActionsData)
    	});
    	
    };
    

};

function checkIfOpinionHasMatchControls() {
	var isMatch = false;
    for(var i = 0; i < sheets.length; i++) {
    	for(var y = 0; y < sheets[i].controls.length; y++) {
        	if(sheets[i].controls[y].controlTypeId == 2 
        			|| sheets[i].controls[y].controlTypeId == 3) {
        		isMatch = true;
        		break;
        	}		
    	}
    }
    return isMatch;
}

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;

$(function() {

	new surveyDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function(data) {
			
			// tabs
			var lastTab = null;
			$('#tabs li').each(function(i, el) {
				if(i == 0) {
					lastTab = $(el);
					lastTab.addClass("active");
					$(lastTab.find("a").attr("href")).show();
				}
				$(el).click(function(event) {
					if(lastTab != null && lastTab != $(this)) {
						lastTab.removeClass("active");
						$(lastTab.find("a").attr("href")).hide();
					}
					lastTab = $(this);
					lastTab.addClass("active");
					$(lastTab.find("a").attr("href")).show();
					
					event.preventDefault();
				});
			});

			// getSheetsAndControls
			getSheetsAndControls({
				accountId : accountId,
				opinionId : <%=opinionId%>,
				success : function(data) {
					
					sheets = data.list;
					
					// check if opinion has match controls
					if(checkIfOpinionHasMatchControls()) {
					
						// page rules
							
						if(__pageRules.length != 0) {
							for(var i = 0; i < __pageRules.length; i++) {
								addPageRule(__pageRules[i].conditions, __pageRules[i].actions);	
							}
						} 
						
						/*
						else {
							// no data
							addPageRule();
						}
						*/
						
						$("#button_add_page_rule")
						.show()
						.click(function(event) {
							event.preventDefault();
							addPageRule();
						});
						
						$("#button_save_page_rules").click(function(event) {
							event.preventDefault();
							for(var i = 0; i < pageRules.length; i++) {
								console.log(JSON.stringify(pageRules[i].conditions.conditionsBuilder("data")));
								console.log(JSON.stringify(pageRules[i].actions.actionsBuilder("data")));
							}
						});
						
						// survey rules
						if(auth.userInfo.permissions.customFinishBehaviour) {
							
							// survey rules
							if(__surveyRules.length != 0) {
								for(var i = 0; i < __surveyRules.length; i++) {
									addSurveyRule(__surveyRules[i].conditions, __surveyRules[i].actions);	
								}
							}
							
							$("#button_add_survey_rule")
							.show()
							.click(function(event) {
								event.preventDefault();
								addSurveyRule();
							});
							
							$("#button_save_survey_rules").click(function(event) {
								event.preventDefault();
								for(var i = 0; i < surveyRules.length; i++) {
									console.log(JSON.stringify(surveyRules[i].conditions.conditionsBuilder("data")));
									console.log(JSON.stringify(surveyRules[i].actions.actionsBuilder("data")));
								}
							});
							
						} else {
							$("#survey_rules_warning").show();
						}
						
					} else {
						
						// show error
						$("#page_rules_error").show();
						
						if(auth.userInfo.permissions.customFinishBehaviour) {
							$("#survey_rules_error").show();
						} else {
							$("#survey_rules_warning").show();
						}
						
					}
					
				},
				error: function(error) {
					console.log(error);
				}
			});
					
		}
	});
	
});
</script>