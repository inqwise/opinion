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
	<div style="clear: both;">
		
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/edit" title="Edit"><span>Edit</span></a></li>
					<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/settings" class="selected" title="Settings"><span>Settings</span></a></li>
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
			<div class="wrapper-content-left">
				
				
				<!-- 
				<div style="padding-top: 10px;">
					<div class="row" style="height: 44px;">
						<div class="cell">
							<div><span class="field-warning-message">You can create a custom message for your survey participants to see once they have completed the survey or<br/>
							 you can redirect them to a web page of your choice. To enable this feature, upgrade to a Personal, Pro, or Enterprise plan.</span></div>
						</div>
					</div>
				</div>
				-->
				
				
				<div style="clear: both;">
					<h3 class="ui-header-light">Custom Messages</h3>
					<div style="padding-top: 4px;" class="ui-form">
						<div class="row-choice">
							<label><span><input type="checkbox" name="checkbox_use_a_custom_start_message" id="checkbox_use_a_custom_start_message" autocomplete="off" /></span>Use a custom start message</label>
						</div>
						<div id="container_start_message" style="display: none; padding: 8px 0px 0px 19px;">
							<div class="row" style="overflow: hidden;">
								<div class="cell">
									<textarea id="textarea_start_message" name="textarea_start_message" autocomplete="off" maxlength="2000" style="width: 304px; height: 64px;"></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div style="clear: both; padding-top: 24px;display: none;" id="container_finish_options">
					<h3 class="ui-header-light">Finish Options</h3>
					<div style="padding-top: 10px">
						When the participants has finished.
					</div>
					<div class="ui-form">
						<div class="row-choice">
							<label><span><input type="radio" autocomplete="off" name="finish_options" value="0" /></span>Do not display thank you message.<br/> After finishing the survey, respondent will see a default Inqwise page.</label>
						</div>
						<div class="row-choice">
							<label><span><input type="radio" autocomplete="off" name="finish_options" value="1" data-form="container-finish-message" /></span>Display thank you message</label>
							<div class="container-finish-message" style="display: none;padding: 8px 0px 0px 19px;">
								<textarea style="width: 304px; height: 64px;" autocomplete="off" maxlength="1000" id="textarea_finish_message"></textarea>
							</div>
						</div>
						<div class="row-choice">
							<label><span><input type="radio" autocomplete="off" name="finish_options" value="2" data-form="container-redirect-url" /></span>Redirect to custom URL</label>
							<div class="container-redirect-url" style="display: none;padding: 8px 0px 0px 19px;">
								<input type="text" id="text_redirect_url" autocomplete="off" maxlength="1000" placeholder="Enter a URL" /><a id="link_check_redirect_url" title="Check URL" class="ui-link-disabled" target="_blank" style="margin-left: 6px;">Check URL</a>
							</div>
						</div>
					</div>
				</div>
				
				<div style="clear: both; padding-top: 24px">
					<h3 class="ui-header-light">Survey already completed</h3>
					<div style="padding-top: 10px">
						<textarea id="textarea_already_completed_message" name="textarea_already_completed_message" autocomplete="off" maxlength="2000" style="width: 304px; height: 64px;"></textarea>
					</div>
				</div>
				
				<div style="padding-top: 24px; clear: both;">
					<h3 class="ui-header-light">Page and Question Numbering</h3>
					<div style="padding-top: 4px;" class="ui-form">
						<div class="row-choice">
							<label><span><input type="checkbox" id="checkbox_use_page_numbering" autocomplete="off" /></span>Use page numbering</label>
						</div>
						<div class="row-choice">
							<label><span><input type="checkbox" id="checkbox_use_question_numbering" autocomplete="off" /></span>Use question numbering</label>
						</div>
					</div>
				</div>
				
				<div style="padding-top: 24px;clear: both;">
					<h3 class="ui-header-light">Required Questions</h3>
					<div style="padding-top: 10px;">
						<div>Required error message.</div>
						<div style="padding-top: 10px;">
							<input type="text" id="input_required_question_error_message" name="required_question_error_message" maxlength="254" autocomplete="off" style="width: 225px;" />
							<div><label id="status_required_question_error_message"></label></div>
						</div>
					</div>
					<div style="padding-top: 10px;" id="container_highlight_required_questions" class="ui-form">
						<div class="row-choice">
							<label><span><input id="radio_use_asterisk" type="radio" name="required_question_highlight" checked="checked" autocomplete="off" /></span>Use asterisk ( * )  to highlight required questions</label>
						</div>
						<div class="row-choice">
							<label><span><input id="radio_do_not_highlight" type="radio" name="required_question_highlight" autocomplete="off" /></span>Do not highlight required questions</label>
						</div>
					</div>
				</div>
				
				<div style="padding-top: 24px; clear: both">
					<h3 class="ui-header-light">Label Placement</h3>
					<div style="padding-top: 12px">
						<select id="select_label_placement" autocomplete="off">
							<option value="0">Top Aligned</option>
							<option value="1">Left Aligned</option>
							<option value="2">Right Aligned</option>
						</select>
					</div>
				</div>
				
				<div style="padding-top: 24px; overflow: hidden;display: none;">
					<h3 class="ui-header-light">Language Customization</h3>
					<div style="padding-top: 10px;">
						<div class="row" style="height: 32px;">
							<div class="cell">
								<div style="line-height: 15px;"><span style="color: #5C5C5C">Customize specific survey text in addition<br/> to your chosen language.</span></div>
							</div>
						</div>
						<div class="row">
							<div class="cell">
								<select autocomplete="off"><option>No Language Customization</option></select>
							</div>
						</div>
					</div>
				</div>
		
				<div style="padding-top: 24px;clear: both;">
					<h3 class="ui-header-light">Progress Bar</h3>
					<div style="padding-top: 12px;" class="ui-form">
						<label><span><input type="checkbox" id="checkbox_show_progress_bar" autocomplete="off" /></span>Show progress bar</label>
					</div>
				</div>
				
				<div style="padding-top: 24px; clear: both">
					<h3 class="ui-header-light">Paging</h3>
					<div style="padding-top: 12px" class="ui-form">
						<label><span><input type="checkbox" id="checkbox_show_paging" /></span>Show paging</label>
					</div>
				</div>
				
				<div style="padding-top: 24px;clear: both;">
					<h3 class="ui-header-light">Navigation Buttons</h3>
					<div style="padding-top: 10px;">
						<div class="params">
							<div class="param-name"><span>* Start:</span></div>
							<div class="param-value">
								<div><input id="input_start_button" name="input-start-button" type="text" maxlength="254" autocomplete="off" autocapitalize="off" /></div>
								<div><label id="status_input_start_button"></label></div>
							</div>
						</div>
						<div class="params">
							<div class="param-name"><span>* Previous:</span></div>
							<div class="param-value">
								<div><input id="input_previous_button" name="input-previous-button" type="text" maxlength="254" autocomplete="off" autocapitalize="off" /></div>
								<div><label id="status_input_previous_button"></label></div>
							</div>
						</div>
						<div class="params">
							<div class="param-name"><span>* Next:</span></div>
							<div class="param-value">
								<div><input id="input_next_button" name="input-next-button" type="text" maxlength="254" autocomplete="off" autocapitalize="off" /></div>
								<div><label id="status_input_next_button"></label></div>
							</div>
						</div>
						<div class="params">
							<div class="param-name"><span>* Finish:</span></div>
							<div class="param-value">
								<div><input id="input_finish_button" name="input-finish-button" type="text" maxlength="254" autocomplete="off" autocapitalize="off" /></div>
								<div><label id="status_input_finish_button"></label></div>
							</div>
						</div>
					</div>
				</div>
				
				
				<div style="padding-top: 24px;clear: both;display: none;" id="container_hide_powered_by">
					<h3 class="ui-header-light">Powered by Inqwise</h3>
					<div style="padding-top: 12px" class="ui-form">
						<label><span><input type="checkbox" id="checkbox_show_powered_by" autocomplete="off" /></span>Show a "Powered by Inqwise"</label>
					</div>
				</div>
			
			</div>
			<div class="wrapper-content-middle-right">
				<div>
					<h3 class="ui-header-light">Right-to-Left (RTL) text direction</h3>
					<div style="padding-top: 10px;" class="ui-form">
						<div style="padding-bottom: 6px;">If you're using a Right-to-Left language, such as Hebrew or Arabic, you can enable Right-to-Left text direction support.</div>
						<div class="row-choice">
							<label><span><input type="checkbox" name="checkbox_is_rtl" id="checkbox_is_rtl" autocomplete="off" /></span>Enable Right-to-Left (RTL) text direction</label>
						</div>
					</div>
				</div>
				<div style="padding-top: 24px;clear: both;">
					<h3 class="ui-header-light">Logo</h3>
					<div style="padding-top: 10px;">
						<div class="params">
							<div class="param-name"><span>Logo Url:</span></div>
							<div class="param-value">
								<div><input id="input_logo_url" name="input-logo-url" type="text" maxlength="2000" autocomplete="off" placeholder="Logo URL" /></div>
								<div><label id="status_input_logo_url"></label></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div style="clear: both;">
				<div style="height: 24px; overflow: hidden;clear: both;"></div>
				<div class="params">
					<div class="param-value">
						<a href="javascript:;" id="button_save_settings" title="Save" class="button-blue"><span>Save</span></a>
					</div>
					<div class="param-value" style="padding-left: 6px; line-height: 21px;">
						<a href="javascript:;" onClick="history.go(-1)" title="Cancel">Cancel</a>
					</div>
				</div>
			</div>
			
		</div>
		
		 
	</div>
</div>


<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_settings.js"></script>
<script type="text/javascript">

var messages = {
	thanks : "Thank you for taking the survey!",
	start : "Start",
	back : "Prev",
	next : "Next",
	finish : "Finish"
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

var getSettings = function(params) {
	
	var obj = {
		opinions : {
			getSettings : {
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
        	if(data.opinions.getSettings != undefined) {
	        	if(data.opinions.getSettings.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.getSettings.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getSettings);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getSettings);
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

var updateSettings = function(params) {
	
	var obj = {
		opinions : {
			updateSettings : {
				accountId : params.accountId,
				opinionId : params.opinionId,
				useCustomStartMessage : params.useCustomStartMessage,
				startMessage : params.startMessage,
				customFinishBehaviour : params.customFinishBehaviour,
				finishMessage : params.finishMessage,
				redirectUrl : params.redirectUrl,
				completedMessage : params.completedMessage,
				labelPlacement : params.labelPlacement,
				usePageNumbering : params.usePageNumbering,
				useQuestionNumbering : params.useQuestionNumbering,
				showProgressBar : params.showProgressBar,
				showPaging : params.showPaging,
				start : params.start,
				back : params.back,
				next : params.next,
				finish : params.finish,
				highlightRequiredQuestions : params.highlightRequiredQuestions,
				isRtl : params.isRtl,
				logoUrl : params.logoUrl,
				hidePoweredBy : params.hidePoweredBy,
				requiredQuestionErrorMessage : params.requiredQuestionErrorMessage
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
			if(data.opinions.updateSettings != undefined) {
				if(data.opinions.updateSettings.error != undefined) {
					
					errorHandler({
						error : data.opinions.updateSettings.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.updateSettings);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.updateSettings);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};


//var accountId = $.cookie("aid"); // auth.userInfo.accountId;

$(function() { 

	new surveyDetails({
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function(data) { 
			
			getSettings({
				accountId : accountId,
				opinionId : <%=opinionId%>,
				success : function(settings) {
					
					new surveySettings({
						data : settings,
						opinionId : <%=opinionId%>,
						callback : function() {

							
							/*
							$('#textarea_start_message').htmlarea({
								toolbar: [
							        ["bold", "italic", "underline", "strikethrough"],
							        ["orderedList", "unorderedList"],
							        ["indent", "outdent"],
							        ["justifyleft", "justifycenter", "justifyright"]
							    ],
							    css : "<%=applicationURL%>/css/htmlarea/frame.css"
							});
							*/
							
						}
					});
					
				},
				error: function(error) {
					
				}
			});
			
			
		}
	});
	
});
</script>
