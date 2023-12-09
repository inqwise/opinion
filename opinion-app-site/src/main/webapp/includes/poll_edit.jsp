
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>

<%

	String opinionId = null;
	opinionId = request.getParameter("opinion_id");
		
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
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/edit" class="selected" title="Edit"><span>Edit</span></a></li>
				<li><a href="<%=absoluteURL %>/polls/<%=opinionId %>/settings" title="Settings"><span>Settings</span></a></li>
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
	<div style="padding-top: 20px;overflow:hidden;" class="sidebar-relative">
	
		<div class="wrapper-content-left">
			<div>To get started, enter the question and answer choices.</div>
			<div style="padding-top: 24px;">
				<div>
					<div class="params" style="overflow: hidden">
						<div class="param-name">* Question Text:</div>
						<div class="param-value">
							<div>
								<textarea id="textarea_question" name="question" maxlength="1000" class="input-content" autocomplete="off" placeholder="Enter your question here." style="width: 304px; height: 46px;"></textarea>
							</div>
							<div><label id="status_question"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">* Question Type:</div>
						<div class="param-value">
							<div>
								<select id="select_question_types" name="question_types" autocomplete="off">
									<option value="2" input_type_id="0">Multiple Choice (select only one answer)</option>
									<option value="2" input_type_id="1">Dropdown (choose only one answer)</option>
									<option value="2" input_type_id="2">Multiple Choice (select multiple answers)</option>
									<!-- <option value="10">Rating Scale</option>
									<option value="2" input_type_id="4">Ranking</option> -->
								</select>
							</div>
							<div><label id="status_question_type"></label></div>
						</div>
					</div>
					<div class="params" style="overflow: hidden">
						<div class="param-name"></div>
						<div class="param-value ui-form">
							<div class="row-choice" style="margin-top: 0px;">
								<label><span><input type="checkbox" autocomplete="off" id="checkbox_mandatory"></span>Required question</label>
							</div>
							<div class="row-choice">
								<label><span><input type="checkbox" autocomplete="off" id="checkbox_link"></span>Add Image/Embed Code</label>
								<div class="container-link" style="padding: 10px 0 0 20px;display: none;">
									<div style="padding-bottom: 6px;">
	                					<select class="select-link-type" autocomplete="off">
	                						<option value="1">Link to Image</option>
	                						<option value="2">Embed Code</option>
	                					</select>
	                				</div>
	           						<div class="container-image" style="Xdisplay: none; overflow: hidden;">
										<input class="input-image" type="text" autocomplete="off" style="width: 284px" />
										<!-- <div><a id="button_dropbox" title="Share from Dropbox">Share from Dropbox</a></div> -->
	           						</div>
	           						<div class="container-embed-code" style="display: none; overflow: hidden;">
	           							<textarea class="textarea-embed-code" style="width: 284px; height:64px;" autocomplete="off"></textarea>
	           						</div>
								</div>
							</div>
							<div class="row-choice">
								<label><span><input type="checkbox" autocomplete="off" id="checkbox_include_note"></span>Help text</label>
								<div class="container-note" style="padding: 10px 0 0 20px;display: none;">
									<textarea class="textarea-note" maxlength="1000" autocomplete="off" placeholder="You can enter some information about this question here." style="width: 284px; height: 46px;"></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="placeholder_control"></div>
			</div>
		</div>
		<div style="float: left; padding-left: 10px; min-width:450px;">
			<div style="clear: both;" class="sidebar">
				<div>
					<div style="border: 1px solid #ccc;clear: both;max-width: 450px;width: 448px;">
						<div id="iframe_preview" style="padding: 5px;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>
<script type="text/javascript">window.jQuery.ui || document.write("<script type=\"text/javascript\" src=\"<%=applicationURL%>/scripts/jqueryui/1.9.1/jquery-ui.min.js\"><\/script>")</script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/sidebar/sidebar.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_editor.js"></script>

<!-- <script type="text/javascript" src="https://www.dropbox.com/static/api/1/dropins.js" id="dropboxjs" data-app-key="5sl7udol487b3dx"></script> -->
<!-- <script type="text/javascript" src="<%=applicationURL%>/scripts/dropbox/dropins.js" id="dropboxjs" data-app-key="5sl7udol487b3dx"></script> -->

<script type="text/javascript">

var getControls = function(params) {
		
	var obj = {
		controls : {
			getControls : { 
				sheetId : params.sheetId,
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

var createControl = function(params) {
	
	var obj = {
		controls: {
			createControl: {
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


$(function() {
	
	// sidebar
	new sidebar({});
	
	new pollDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function() {
			
			new pollEditor({
				opinionId : <%=opinionId%>
			});
			
		}
	});
	
	
});
</script>