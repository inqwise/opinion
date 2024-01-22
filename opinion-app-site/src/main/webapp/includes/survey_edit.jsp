
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%

	String opinionId = null;
	opinionId = request.getParameter("opinion_id");
		
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();

%>

<link rel="stylesheet" href="<%=applicationURL%>/css/survey_editor.css" type="text/css" />

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important;display: inline;"><a href="<%=absoluteURL %>/surveys" title="Surveys">Surveys</a>&nbsp;&rsaquo;&nbsp;<span id="label_survey_name"></span></h1><a href="javascript:;" id="link_rename_survey_name" title="Rename" style="margin-left: 6px;">Rename</a>
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
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/edit" class="selected" title="Edit"><span>Edit</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/settings" title="Settings"><span>Settings</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/reordering" title="Reordering"><span>Reordering</span></a></li>
				<li id="tab_rules"><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/rules" title="Rules"><span>Rules<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/branding" title="Branding"><span>Branding<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/collectors" title="Collectors"><span>Collectors</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/overall-results" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="clear: both; padding-top: 20px;">
		<div style="display: table">
			<div class="frame-edit">
				<div class="add-page"></div>
				<div class="all-pages">All pages</div>
				<div class="scroll-right"></div>
				<div class="scroll-left"></div>
				<div style="overflow: hidden; position: relative; width: 300px;">
					<div class="sortable-section">
						<ul id="sortable"></ul>
					</div>
				</div>
				<div style="clear: both; min-height: 300px;">
					<div class="placeholder-questions" id="placeholder_questions" style="padding-top: 12px;">
						<div id="placeholder_sheet_details"></div>
						<div>
							<div id="empty" style="display: none;">
								<div class="empty-box">
									<span class="button-add-question"></span>
								</div>
							</div>
							<div id="questions">
								<div style="padding-bottom: 10px;">
									<span class="button-insert-question"></span>
								</div>
								<ul class="questions"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="frame-preview col-3">
				&nbsp;
				<div class="frame-collapse" title="Collapse preview panel"></div>
				<div class="frame-expand" title="Expand preview panel"></div>
				<div class="block-preview sidebar">
					
					<div class="demo-body ui-body-a">
						<div class="demo-wrapper ui-wrapper-a">
							<div class="demo-header ui-header-a">
								<div id="logo" class="demo-logo ui-logo-a"></div>
							</div>
							<div class="demo-content ui-content-a">
								<div>
									<h1 class="demo-survey-heading ui-survey-heading-a"></h1>
									<div class="demo-page ui-page-a">
										<div>
											<h2 style="display: none" class="demo-page-heading ui-page-heading-a header-page-title"><span id="label_page_number"></span><span id="label_page_title">Page Title</span></h2>
											<div style="display: none" id="paragraph_page_description" class="demo-page-description ui-page-description-a"></div>
											<div>
												<ul class="list-controls"></ul>
											</div>
											<div class="demo-page-actions ui-page-actions-a">
												<div style="display: none;" class="container-previous"><a class="demo-button ui-button-a button-previous-page" title="Prev" href="javascript:;"><span>Prev</span></a></div>
												<div style="display: none" class="container-next"><a class="demo-button ui-button-a button-next-page" title="Next" href="javascript:;"><span>Next</span></a></div>
												<div style="display: none" class="container-finish"><a class="demo-button ui-button-a button-finish-survey" title="Finish" href="javascript:;"><span>Finish</span></a></div>
												<div style="display: none" class="container-paging">Page <b class="label-start-page-number">1</b> of <b class="label-end-page-number">1</b></div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="demo-footer ui-footer-a">Powered by <a target="_blank" href="http://www.inqwise.com" title="Online Survey Tool">Inqwise</a></div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/jqueryui/1.9.1/jquery-ui.min.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/js/showdown/showdown.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/split_button.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>


<script type="text/javascript">
var lastEditableControl = null;
</script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_editor_control.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_editor.js"></script>

<script type="text/javascript">
var getList = function(params) {
	
	var obj = {
		opinions : {
			getList : {
				folderId : null,
				top : 100,
				from : undefined,
				to : undefined,
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
            // error
        }
    });
	
};

var addControlFromAnotherOpinion = function(callback) {
	
	var message = $("<div>" +
		"<div style=\"width: 230px; height: 200px; float: left; overflow-x: hidden; overflow-y: auto; border: 1px solid #999999\">" +
	   		"<ul class=\"list-another-surveys\"></ul>" +
	   "</div>" +
	   "<div style=\"width: 230px; height: 200px; float: left; margin-left: 10px; overflow-x: hidden; overflow-y: auto; border: 1px solid #999999\">" +
	   		"<ul class=\"list-another-survey-questions\"></ul>" +
	   "</div>" +
	"</div>");
	
	var _opinionId = null;
	var _controls = [];
	
	var modal = new lightFace({
		title : "Copy question from other survey",
		message : message,
		actions : [
		    { 
		    	label : "Copy", 
		    	fire : function() { 
		    		
		    		_controls = [];
		    		message.find("ul.list-another-survey-questions li").each(function(i, el) {
		    			if($(el).find("input:checkbox").prop("checked")) {
		    				_controls.push(parseInt($(el).find("input:checkbox").attr("id")));
		    			}
		    		});
		    		
		    		if(callback != undefined 
		    				&& typeof callback == 'function') {
		    			callback({
		    				opinionId : _opinionId,
		    				controls : _controls
		    			});
		    		}
		    		
		    		modal.close();
		    		 
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
			
			var listAnotherSurveys = message.find(".list-another-surveys");
			var listAnotherSurveyQuestions = message.find(".list-another-survey-questions");
			
			getList({
				accountId : accountId,
				success : function(data) {
					
					var lastItem = null;
					
					if(data.list.length != 0) {
						for(var i = 0; i < data.list.length; i++) {
							
							var listItem = $("<li data-value=\"" + data.list[i].opinionId + "\"><span>" + data.list[i].name + "</span></li>").appendTo(listAnotherSurveys);
							
							listItem.click(function() {
								
								if(lastItem != null && lastItem != $(this)) {
									lastItem.removeClass("active");
								}
								
								$(this).addClass("active");
								
								lastItem = $(this);
								
								_opinionId = $(this).attr("data-value");
								
								listAnotherSurveyQuestions.empty();
								
								getSheetsAndControls({
									accountId : accountId,
									opinionId : $(this).attr("data-value"),
									success : function(_data) {
										
										var questionNumber = 0;
										for(var y = 0; y < _data.list.length; y++) {
											
											for(var x = 0; x < _data.list[y].controls.length; x++) {
												
												if(_data.list[y].controls[x].controlTypeId != 11 
														&& _data.list[y].controls[x].controlTypeId != 12) {

													var questionItem = $("<li class=\"ui-label question-bank-question\">" +
														"<input id=\"" + _data.list[y].controls[x].controlId + "\" type=\"checkbox\" value=\"" + _data.list[y].controls[x].controlId + "\" class=\"ui-label\" />" +
														"<label for=\"" + _data.list[y].controls[x].controlId + "\">" + (questionNumber + 1) + ".&nbsp;" + _data.list[y].controls[x].content + "</label>" +
													"</li>").appendTo(listAnotherSurveyQuestions);
													
													if(_data.list[y].controls[x].controlTypeId == 2) {
														
														var options = $("<div style=\"padding-left: 18px;\">" +
															"<a href=\"#\" class=\"ui-icon-expand expand-answer-options\" title=\"View Answer Choices\">View Answer Choices</a>" +
															"<ul class=\"lsa\" style=\"padding-left: 10px; display: none\"></ul>" +
														"</div>").appendTo(questionItem);
														
														var P = null;
														for(var q = 0; q < _data.list[y].controls[x].options.list.length; q++) {
															if(_data.list[y].controls[x].options.list[q].optionKindId == 0) {
																$("<li>" + _data.list[y].controls[x].options.list[q].text + "</li>").appendTo(options.find("ul"));
															}
															if(_data.list[y].controls[x].options.list[q].optionKindId == 1) {
					                                        	P = _data.list[y].controls[x].options.list[q];
					                                        }
														}
														
														if(P != null) {
															$("<li>" + P.text + "</li>").appendTo(options.find("ul"));
														}
														
														options.find(".expand-answer-options").click(function(e) {
															
															var element = $(this);
															if(element.hasClass("ui-icon-collapse")) {
																element.removeClass("ui-icon-collapse");
																element.closest("div").find("ul").hide();
															} else {
																element.addClass("ui-icon-collapse");
																element.closest("div").find("ul").show();
															}
															
															//console.log("expand....");
															e.preventDefault();
														});
														
													}
													
													questionNumber++;
												
												}
												
											}
											
										}
										
									},
									error: function() {
										// error
									}
								});
								
							});
						}
					}
					
				},
				error : function() {
					
				}
			});
			
			
		}
	});
	
	
};

var getPredefinedChoices = function(callback) {
	
	var predefinedChoicesMessage = $("<div>" +
		"<div style=\"clear: both; padding: 0 0 12px 0\">1) Select a category. 2) Customize as you see fit. 3) Add to your field / question.</div>" +
		"<div style=\"width: 230px; height: 200px; float: left; overflow-x: hidden; overflow-y: auto; border: 1px solid #999999\">" +
	   		"<ul class=\"list-predefined-categories\">" +
		   		"<li data-value=\"0\"><span>Yes/No</span></li>" +
	   			"<li data-value=\"1\"><span>True/False</span></li>" +
	   			"<li data-value=\"2\"><span>Like/Dislike</span></li>" +
	   			"<li data-value=\"3\"><span>Gender</span></li>" +
	   			"<li data-value=\"4\"><span>Marital Status</span></li>" +
	   			"<li data-value=\"5\"><span>Age</span></li>" +
	   			"<li data-value=\"6\"><span>Employment</span></li>" +
	   			"<li data-value=\"7\"><span>Income</span></li>" +
	   			"<li data-value=\"8\"><span>Education</span></li>" +
	   			"<li data-value=\"9\"><span>Days of the Week</span></li>" +
	   			"<li data-value=\"10\"><span>Months of the Year</span></li>" +
	   			/*"<li data-value=\"11\"><span>Timezones</span></li>" +*/
	   			"<li data-value=\"12\"><span>U.S. States</span></li>" +
	   			"<li data-value=\"13\"><span>Continents</span></li>" +
	   			"<li data-value=\"14\"><span>How Often</span></li>" +
	   			"<li data-value=\"15\"><span>How Long</span></li>" +
	   			"<li data-value=\"16\"><span>Satisfaction</span></li>" +
	   			"<li data-value=\"17\"><span>Importance</span></li>" +
	   			"<li data-value=\"18\"><span>Agreement</span></li>" +
	   			"<li data-value=\"19\"><span>Comparison</span></li>" +
	   			"<li data-value=\"20\"><span>Would You</span></li>" +
	   		"</ul>" +
	   "</div>" +
	   "<div style=\"width: 230px; height: 200px; float: left; margin-left: 10px;\">" +
	   		"<textarea class=\"textarea-predefined-choices\" maxlength=\"1000\" autocomplete=\"off\" style=\"width: 224px; height: 196px;\" />" +
	   "</div>" +
	"</div>");
	   
	   var modal = new lightFace({
			title : "Import Predefined Choices",
		message : predefinedChoicesMessage,
		actions : [
		    { 
		    	label : "Add Choices", 
		    	fire : function() { 
		    		
		    		var predefinedOptions = predefinedChoicesMessage.find('.textarea-predefined-choices').val().split("\n");
		    		var clearOptions = []; 
		    		for(var y = 0; y < predefinedOptions.length; y++) {
		    			if(predefinedOptions[y].replace(/\n/gi, "") != "") {
		    				clearOptions.push(predefinedOptions[y].replace(/\n/gi, ""));
		    			}
		    		}
		    		
		    		if(clearOptions.length != 0) {
		    			if(callback != undefined 
		    					&& typeof callback == 'function') {
		    				callback(clearOptions);
		    			}
		    		}
		    		
		    		modal.close();
		    		 
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
			
			predefinedChoicesMessage.find('ul.list-predefined-categories li').click(function() {
				
				var predefined = predefinedChoices[$(this).attr("data-value")];
				var text = "";
				for(var i = 0; i < predefined.length; i++) {
					text += predefined[i] + "\n";
				}
				
				predefinedChoicesMessage.find('.textarea-predefined-choices').val(text);
				
			});
			
		}
	});
		   
};

var getPredefinedColumns = function(callback) {
	
	var predefinedColumnsMessage = $("<div>" +
		"<div style=\"clear: both; padding: 0 0 12px 0\">1) Select a category. 2) Customize as you see fit. 3) Add to your field / question.</div>" +
		"<div style=\"width: 230px; height: 200px; float: left; overflow-x: hidden; overflow-y: auto; border: 1px solid #999999\">" +
	   		"<ul class=\"list-predefined-categories\">" +
	   			"<li data-value=\"0\"><span>Agreement</span></li>" +
	   			"<li data-value=\"1\"><span>Comparison</span></li>" +
	   			"<li data-value=\"2\"><span>Importance</span></li>" +
	   			"<li data-value=\"3\"><span>Satisfaction</span></li>" +
	   			"<li data-value=\"4\"><span>Easy/Hard</span></li>" +
	   			"<li data-value=\"5\"><span>Would You</span></li>" +
	   			"<li data-value=\"6\"><span>10 Scale</span></li>" +
	   		"</ul>" +
	   "</div>" +
	   "<div style=\"width: 230px; height: 200px; float: left; margin-left: 10px;\">" +
	   		"<textarea class=\"textarea-predefined-columns\" maxlength=\"1000\" autocomplete=\"off\" style=\"width: 224px; height: 196px;\" />" +
	   "</div>" +
	"</div>");
	   
	   var modal = new lightFace({
			title : "Import Predefined Columns",
		message : predefinedColumnsMessage,
		actions : [
		    { 
		    	label : "Add Columns", 
		    	fire : function() { 
		    		
		    		var predefinedOptions = predefinedColumnsMessage.find('.textarea-predefined-columns').val().split("\n");
		    		var clearOptions = []; 
		    		for(var y = 0; y < predefinedOptions.length; y++) {
		    			if(predefinedOptions[y].replace(/\n/gi, "") != "") {
		    				clearOptions.push(predefinedOptions[y].replace(/\n/gi, ""));
		    			}
		    		}
		    		
		    		if(clearOptions.length != 0) {
		    			
		    			if(callback != undefined 
		    					&& typeof callback == 'function') {
		    				callback(clearOptions);
		    			}
		    			
		    		}
		    		
		    		modal.close();
		    		 
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
			
			predefinedColumnsMessage.find('ul.list-predefined-categories li').click(function() {
				
				var predefined = predefinedColumns[$(this).attr("data-value")];
				var text = "";
				for(var i = 0; i < predefined.length; i++) {
					text += predefined[i] + "\n";
				}
				
				predefinedColumnsMessage.find('.textarea-predefined-columns').val(text);
				
			});
			
		}
	});
		   
};






var controlTypes = [];

var survey = { 
	surveyId: <%=opinionId %>,
	sheets: {
		list: [],
		lastSelected: 1
	},
	controls : {
		list : [],
		startIndex: 1,
		startNumerator : 1
	}
};

var predefinedChoices = [
	["Yes", "No"],
	["True", "False"],
	["Yes. I like this.", "No. I don't like this."],
	["Male", "Female", "Prefer Not to Answer"],
	["Single", "Married", "Divorced"],
	["Under 18", "18-24", "25-34", "35-44", "45-54", "55-64", "65 or Above", "Prefer Not to Answer"],
	["Employed Full-Time", "Employed Part-Time", "Self-employed", "Not employed, but looking for work", "Not employed and not looking for work", "Homemaker", "Retired", "Student", "Prefer Not to Answer"],
	["Under $20,000", "$20,000 - $30,000", "$30,000 - $40,000", "$40,000 - $50,000", "$50,000 - $75,000", "$75,000 - $100,000", "$100,000 - $150,000", "$150,000 or more", "Prefer Not to Answer"],
	["Some High School", "High School Graduate or Equivalent", "Trade or Vocational Degree", "Some College", "Associate Degree", "Bachelor's Degree", "Graduate of Professional Degree", "Prefer Not to Answer"],
	["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
	["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
	[],
	["Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"],
	["Africa","Antarctica","Asia","Australia","Europe","North America","South America"],
	["Everyday","Once a week","2 to 3 times a month","Once a month","Less than once a month"],
	["Less than a month","1-6 months","1-3 years","Over 3 Years","Never used"],
	["Very Satisfied","Satisfied","Neutral","Unsatisfied","Very Unsatisfied","N/A"],
	["Very Important","Important","Neutral","Somewhat Important","Not at all Important","N/A"],
	["Strongly Agree","Agree","Neutral","Disagree","Strongly Disagree","N/A"],
	["Much Better","Somewhat Better","About the Same","Somewhat Worse","Much Worse","Don't Know"],
	["Definitely","Probably","Not Sure","Probably Not","Definitely Not"]
];

var predefinedColumns = [
	["Strongly Disagree","Disagree","Neutral","Agree","Strongly Agree"],
	["Much Worse","Somewhat Worse","About the Same","Somewhat Better","Much Better"],
	["Not at all Important","Somewhat Important","Neutral","Important","Very Important"],
	["Very Unsatisfied","Unsatisfied","Neutral","Satisfied","Very Satisfied"],
	["Very Easy", "Easy", "Okay", "Hard", "Very Hard"],
	["Definitely Not","Probably Not","Not Sure","Probably","Definitely"],
	["1","2","3","4","5","6","7","8","9","10"]
];

var messages = {
	month : "Month",
	day : "Day",
	year : "Year",
	_hours : "Hours",
	_minutes : "Minutes",
	timeZone : "Time Zone",
	defaultNoneSelectedOption : "Please choose",
	other : "Other, (please specify)",
	defaultAdditionalDetails : "Additional details or comments",
	days : new function() {
		var days = [];
		for(var i = 0; i <= 31; i++) {
			var obj = {
				value: i,
				caption: (i == 0 ? "" : i)
			};
			days.push(obj);
		}
		return days;
	},
	months : [
		{value: 0, caption: ""}, 
        {value: 1, caption: "Jan"},
        {value: 2, caption: "Feb"},
        {value: 3, caption: "Mar"},
        {value: 4, caption: "Apr"},
        {value: 5, caption: "May"},
        {value: 6, caption: "Jun"},
        {value: 7, caption: "Jul"},
        {value: 8, caption: "Aug"},
        {value: 9, caption: "Sep"},
        {value: 10, caption: "Oct"},
        {value: 11, caption: "Nov"},
        {value: 12, caption: "Dec"}
    ],
    hours : new function() {
		var hours = [];
		for(var i = 0; i <= 24; i++) {
			var obj = {
				value: (i > 0 ? jQuery.pad((i - 1), 2) : i),
				caption: (i == 0 ? "" : jQuery.pad((i - 1), 2))
			};
			hours.push(obj);
		}
		return hours;
	},
    minutes : new function() {
		var minutes = [];
		for(var i = 0; i <= 60; i++) {
			var obj = {
				value: (i > 0 ? jQuery.pad((i - 1), 2) : i),
				caption: (i == 0 ? "" : jQuery.pad((i - 1), 2))
			};
			minutes.push(obj);
		}
		return minutes;
	},
    timeZones : [
    	{value: 0, caption: ""},
    	{value: "-12.0", caption: "(GMT -12:00) Eniwetok, Kwajalein"}, 
       	{value: "-11.0", caption: "(GMT -11:00) Midway Island, Samoa"}, 
       	{value: "-10.0", caption: "(GMT -10:00) Hawaii"}, 
       	{value: "-9.0", caption: "(GMT -9:00) Alaska"}, 
       	{value: "-8.0", caption: "(GMT -8:00) Pacific Time (US & Canada)"}, 
       	{value: "-7.0", caption: "(GMT -7:00) Mountain Time (US & Canada)"}, 
       	{value: "-6.0", caption: "(GMT -6:00) Central Time (US & Canada), Mexico City"}, 
       	{value: "-5.0", caption: "(GMT -5:00) Eastern Time (US & Canada), Bogota, Lima"}, 
       	{value: "-4.0", caption: "(GMT -4:00) Atlantic Time (Canada), Caracas, La Paz"}, 
       	{value: "-3.5", caption: "(GMT -3:30) Newfoundland"}, 
       	{value: "-3.0", caption: "(GMT -3:00) Brazil, Buenos Aires, Georgetown"}, 
       	{value: "-2.0", caption: "(GMT -2:00) Mid-Atlantic"},
       	{value: "-1.0", caption: "(GMT -1:00 hour) Azores, Cape Verde Islands"},
       	{value: "0.0", caption: "(GMT) Western Europe Time, London, Lisbon, Casablanca"},
       	{value: "1.0", caption: "(GMT +1:00 hour) Brussels, Copenhagen, Madrid, Paris"},
       	{value: "2.0", caption: "(GMT +2:00) Kaliningrad, South Africa"},
       	{value: "3.0", caption: "(GMT +3:00) Baghdad, Riyadh, Moscow, St. Petersburg"},
       	{value: "3.5", caption: "(GMT +3:30) Tehran"},
       	{value: "4.0", caption: "(GMT +4:00) Abu Dhabi, Muscat, Baku, Tbilisi"},
       	{value: "4.5", caption: "(GMT +4:30) Kabul"},
       	{value: "5.0", caption: "(GMT +5:00) Ekaterinburg, Islamabad, Karachi, Tashkent"},
       	{value: "5.5", caption: "(GMT +5:30) Bombay, Calcutta, Madras, New Delhi"},
       	{value: "5.75", caption: "(GMT +5:45) Kathmandu"},
       	{value: "6.0", caption: "(GMT +6:00) Almaty, Dhaka, Colombo"},
       	{value: "7.0", caption: "(GMT +7:00) Bangkok, Hanoi, Jakarta"},
       	{value: "8.0", caption: "(GMT +8:00) Beijing, Perth, Singapore, Hong Kong"},
       	{value: "9.0", caption: "(GMT +9:00) Tokyo, Seoul, Osaka, Sapporo, Yakutsk"},
       	{value: "9.5", caption: "(GMT +9:30) Adelaide, Darwin"},
       	{value: "10.0", caption: "(GMT +10:00) Eastern Australia, Guam, Vladivostok"},
       	{value: "11.0", caption: "(GMT +11:00) Magadan, Solomon Islands, New Caledonia"},
       	{value: "12.0", caption: "(GMT +12:00) Auckland, Wellington, Fiji, Kamchatka"}
	]
};

var deleteSheet = function(params) {

	 var obj = {
		controls : {
			deleteSheet : {
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
			if(data.controls.deleteSheet != undefined) {
				if(data.controls.deleteSheet.error != undefined) {
					
					errorHandler({
						error : data.controls.deleteSheet.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.deleteSheet);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.deleteSheet);
					}
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

var createSheet = function(params) {

	var obj = {
		controls : {
			createSheet : { 
				accountId : params.accountId,
				opinionId : params.opinionId, 
				pageNumber: params.pageNumber 
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
			if(data.controls.createSheet != undefined) {
				if(data.controls.createSheet.error != undefined) {
					
					errorHandler({
						error : data.controls.createSheet.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.createSheet);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.createSheet);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var updateSheet = function(params) {
	
	var obj = {
		controls : {
			changeSheet : {
				accountId : params.accountId,
				sheetId : params.sheetId,
				title : params.title,
				description : params.description
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
			if(data.controls.changeSheet != undefined) {
				if(data.controls.changeSheet.error != undefined) {
					
					errorHandler({
						error : data.controls.changeSheet.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.changeSheet);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.changeSheet);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var sheetCopyTo = function(params) {

	var obj = {
		controls : {
			sheetCopyTo: { 
				accountId : params.accountId,
				sheetId : params.sheetId,
				pageNumber : params.pageNumber
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
			if(data.controls.sheetCopyTo != undefined) {
				if(data.controls.sheetCopyTo.error != undefined) {
					
					errorHandler({
						error : data.controls.sheetCopyTo.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.sheetCopyTo);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.sheetCopyTo);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var reorderSheets = function(params) {
	
	var obj = {
		controls : {
			reorderSheets : {
				accountId : params.accountId,
				sheetIds : params.sheetIds
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
			if(data.controls.reorderSheets != undefined) {
				if(data.controls.reorderSheets.error != undefined) {
					
					errorHandler({
						error : data.controls.reorderSheets.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.reorderSheets);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.reorderSheets);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getControlsBySheet = function(params) {
	
	var obj = {
		controls : {
			getControls : { 
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
			if(data.controls.getControls != undefined) {
				if(data.controls.getControls.error != undefined) {
					
					errorHandler({
						error : data.controls.getControls.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.getControls);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.getControls);
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

var createControl = function(params) {
	
	var obj = {
		controls: {
			createControl: {
				accountId : params.accountId,
				opinionId : params.opinionId,
				orderId : params.orderId,
				parentId : params.parentId,
				parentTypeId : params.parentTypeId, // sheet 2, control 3
				controlTypeId : params.controlTypeId,
				content : params.content,
				isEnableNote: params.isEnableNote,
				note : params.note,
				isMandatory : params.isMandatory,
				isEnableComment : params.isEnableComment,
				comment : params.comment,
				inputTypeId : params.inputTypeId,
				inputSizeTypeId : params.inputSizeTypeId,
				isEnableOther : params.isEnableOther,
				linkTypeId : params.linkTypeId,
				link : params.link,
				options: params.options,
				subControls: params.subControls,
				fromScale : params.fromScale,
				fromTitle : params.fromTitle,
				toScale : params.toScale,
				toTitle : params.toTitle,
				monthTitle : params.monthTitle,
				dayTitle : params.dayTitle,
				yearTitle : params.yearTitle,
				hoursTitle : params.hoursTitle,
				minutesTitle : params.minutesTitle
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
			if(data.controls.createControl != undefined) {
				if(data.controls.createControl.error != undefined) {
					
					errorHandler({
						error : data.controls.createControl.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.createControl);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.createControl);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var deleteControl = function(params) {
	
	var obj = {
		controls: {
			deleteControl: {
				accountId : params.accountId,
				controlId : params.controlId
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
			if(data.controls.deleteControl != undefined) {
				if(data.controls.deleteControl.error != undefined) {
					
					errorHandler({
						error : data.controls.deleteControl.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.deleteControl);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.deleteControl);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });	
};

var updateControlDetails = function(params) {
	
	var obj = { 
		controls : { 
			updateControlDetails : params.control
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
			if(data.controls.updateControlDetails != undefined) {
				if(data.controls.updateControlDetails.error != undefined) {
					
					errorHandler({
						error : data.controls.updateControlDetails.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.updateControlDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.updateControlDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var reorderControl = function(params) {
	
	var obj = {
		controls: {
			reorderControl: {
				accountId : params.accountId,
				controlId : params.controlId,
				sheetId : params.sheetId,
				orderId : params.orderId
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
			if(data.controls.reorderControl != undefined) {
				if(data.controls.reorderControl.error != undefined) {
					
					errorHandler({
						error : data.controls.reorderControl.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.reorderControl);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.reorderControl);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var controlCopyTo = function(params) {
	
	var obj = {
		controls: {
			controlCopyTo: {
				accountId : params.accountId,
				controlId : params.controlId,
				opinionId : params.opinionId,
				list : params.list,
				sheetId : params.sheetId,
				parentId : params.parentId,
				translationId : params.translationId,
				orderId : params.orderId
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
			if(data.controls.controlCopyTo != undefined) {
				if(data.controls.controlCopyTo.error != undefined) {
					
					errorHandler({
						error : data.controls.controlCopyTo.error 	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.controls.controlCopyTo);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.controls.controlCopyTo);
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

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;
var converter = new showdown.Converter();
$(function() {
	
	new surveyDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function(data) {
			
			new surveyEditor({ 
				name : data.name,
				opinionId : <%=opinionId%>,
				data : data,
				survey : survey
			});
			
		}
	});
    
});
</script>