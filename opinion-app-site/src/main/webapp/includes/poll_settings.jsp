
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
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/settings" class="selected" title="Settings"><span>Settings</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/branding" title="Branding"><span>Branding<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors" title="Collectors"><span>Collectors</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/overall-results" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="padding-top: 20px;">
		<div class="wrapper-content-left">
		
			<div style="clear: both;">
				<h3 class="ui-header-light">Required Question Highlight</h3>
				<div style="padding-top: 4px;" id="container_highlight_required_questions" class="ui-form">
					<div class="row-choice">
						<label><span><input id="radio_use_asterisk" type="radio" name="required_question_highlight" checked="checked" autocomplete="off" /></span>Use asterisk ( * )  to highlight required questions</label>
					</div>
					<div class="row-choice">
						<label><span><input id="radio_do_not_highlight" type="radio" name="required_question_highlight" autocomplete="off" /></span>Do not highlight required questions</label>
					</div>
				</div>
			</div>
		
			<div style="padding-top: 24px;clear: both;">
				<h3 class="ui-header-light">Navigation Buttons</h3>
				<div style="padding-top: 10px;">
					<div class="params">
						<div class="param-name"><span>* Vote:</span></div>
						<div class="param-value">
							<div><input id="text_vote" name="vote" type="text" maxlength="254" autocomplete="off" /></div>
							<div><label id="status_vote"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name"><span>* View Results:</span></div>
						<div class="param-value">
							<div><input id="text_view_results" name="view_results" type="text" maxlength="254" autocomplete="off" /></div>
							<div><label id="status_view_results"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name"><span>* Back:</span></div>
						<div class="param-value">
							<div><input id="text_back" name="back" type="text" maxlength="254" autocomplete="off" /></div>
							<div><label id="status_back"></label></div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<div class="wrapper-content-middle-right">
			<div>
				<h3 class="ui-header-light">Right-to-Left (RTL) text direction</h3>
				<div style="padding-top: 10px;" class="ui-form">
					<div style="padding-bottom: 6px;">If you're using a Right-to-Left language, such as Hebrew or Arabic, you can enable Right-to-Left text direction support.</div>
					<div class="row-choice">
						<label><span><input type="checkbox" name="checkbox_is_rtl" id="checkbox_is_rtl" autocomplete="off"  /></span>Enable Right-to-Left text direction</label>
					</div>
				</div>
			</div>
			<!--
			<div style="padding-top: 24px;clear: both;">
				<h3 class="ui-header-light">Logo</h3>
				<div style="padding-top: 10px;">
					<div class="params">
						<div class="param-name"><span>Logo Url:</span></div>
						<div class="param-value">
							<div><input id="input_logo_url" name="input-logo-url" type="text" maxlength="2000" autocomplete="off" autocapitalize="off" spellcheck="false" /></div>
							<div><label id="status_input_logo_url"></label></div>
						</div>
					</div>
				</div>
			</div>
			-->
		</div>
		
		<div style="clear: both;">
			<div style="height: 24px; overflow: hidden;clear: both;"></div>
			<div class="params">
				<div class="param-value">
					<a href="javascript:;" id="button_save_settings" title="Save Changes" class="button-blue"><span>Save Changes</span></a>
				</div>
				<div class="param-value" style="padding-left: 6px; line-height: 21px;">
					<a href="javascript:;" onClick="history.go(-1)" title="Cancel">Cancel</a>
				</div>
			</div>
		</div>
		
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_settings.js" charset="utf-8"></script>
<script type="text/javascript">

var messages = {
	back : "Back",
	viewResults : "View Results",
	vote : "Vote"
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

var exportTo = function(params) {
	
	var data = {
	    format: params.format,
	    opinionId : params.opinionId,
	    name: params.name,
	    exportType : params.exportType
	};
	
	window.location = "<%=applicationURL%>/servlet/export?rq=" + JSON.stringify(data);
	
};

var getSettings = function(params) {
	
	var obj = {
		opinions : {
			getSettings : {
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
				opinionId : params.opinionId,
				useCustomStartMessage : params.useCustomStartMessage,
				startMessage : params.startMessage,
				customFinishBehaviour : params.customFinishBehaviour,
				finishMessage : params.finishMessage,
				redirectUrl : params.redirectUrl,
				usePageNumbering : params.usePageNumbering,
				useQuestionNumbering : params.useQuestionNumbering,
				showProgressBar : params.showProgressBar,
				back : params.back,
				viewResults : params.viewResults,
				vote : params.vote,
				highlightRequiredQuestions : params.highlightRequiredQuestions,
				isRtl : params.isRtl,
				logoUrl : params.logoUrl
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

$(function() {
	
	new pollDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function(data) {
			
			getSettings({
				opinionId : <%=opinionId%>,
				success : function(settings) {
					
					new pollSettings({ 
						data : settings,
						opinionId : <%=opinionId%>
					});
					
				},
				error: function(error) {
					
				}
			});
						
		}
	});
	
});
</script>