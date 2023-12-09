
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.opinion.cms.common.IPage" %>

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
	<div style="clear: both;">
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/edit" title="Edit"><span>Edit</span></a></li>
						<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/settings" title="Settings"><span>Settings</span></a></li>
						<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/reordering" class="selected" title="Reordering"><span>Reordering</span></a></li>
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
			
			<div style="clear: both; padding-top: 20px; overflow: hidden;" class="sidebar-relative">
				
				<div class="wrapper-content-left">
					<div style="clear: both;">
						<h3 class="ui-header-light">Order survey flow.</h3>
						<div style="padding-top: 12px;">
						
							<div class="se">
								<div class="reordering-container">
									<div id="panel-actions" class="panel-actions"></div>
									<div style="clear: both;">
										<ul class="reordering"></ul>
									</div>
								</div>
							</div>
							
						</div>
					</div>
				</div>
				<div class="wrapper-content-middle">
					<div style="clear: both; width: 222px;" class="sidebar">
						<h3 class="ui-header">Options</h3>
						<div style="padding: 12px 0 12px 8px; clear: both;">
							<div>Hide questions for easier page reordering.</div>
							<div style="padding-top: 12px;"><label><input type="checkbox" id="checkbox_hide_questions" autocomplete="off" />Hide questions</label></div>
						</div>
					</div>
				</div>
				<div class="wrapper-content-right">&nbsp;</div>
			</div>
			
		</div>
	</div>
</div>


<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>
<script type="text/javascript">window.jQuery.ui || document.write("<script type=\"text/javascript\" src=\"<%=applicationURL%>/scripts/jqueryui/1.9.1/jquery-ui.min.js\"><\/script>")</script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/sidebar/sidebar.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_reordering.js"></script>
<script type="text/javascript">

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

var controlCopyTo = function(params) {
	
	var obj = {
		controls: {
			controlCopyTo: {
				accountId : params.accountId,
				controlId : params.controlId,
				opinionId : params.opinionId,
				sheetId : params.sheetId,
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

var getControls = function(params) {
	
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

var hideQuestions = false;

//var accountId = $.cookie("aid");

$(function() {

	new surveyDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function() {
			
			$('#checkbox_hide_questions').change(function() {
				// hide questions
				var a = $(this);
				if(a.prop('checked')) {
					$('ul.reordering-controls').hide();
					hideQuestions = true;
				} else {
					$('ul.reordering-controls').show();
					hideQuestions = false;
				}
			});

			// reordering
			new reordering({ 
				opinionId : <%=opinionId %>,
				editUrl : "<%=absoluteURL %>/surveys/<%=opinionId %>/edit"
			});
			
			new sidebar({});
			
		}
	});
	
});
</script>
