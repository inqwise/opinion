
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
	<div>
		<h1><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/collectors" title="Collectors">Collectors</a>&nbsp;&rsaquo;&nbsp;Add Collector</h1>
		<h3 class="ui-header-light">How Would You Like to Collect Responses?</h3>
		<div style="padding-top: 12px;">Define Your Target.<br/> Select the method you would like to use to collect responses.</div>
		<div style="padding-top: 12px;">
			<div id="collector_types"></div>
		</div>
		<div style="clear: both;padding-top: 12px;">
			
			<div style="clear: both; display: none;" id="form_invite_your_own_respondents">
				<div>Get responses from your customers, friends &amp; followers.</div>
			</div>
			
			<div style="clear: both; display: none;" id="form_purchase_respondents">
				<div>Buy a target audience from millions of panelists.</div>
			</div>
			
			<div style="padding-top: 24px;">
				<div class="params">
					<div class="param-name">* Collector Name:</div>
					<div class="param-value">
						<div><input type="text" id="text_collector_name" name="collector_name" maxlength="254" autocomplete="off" placeholder="Name the new collector" style="width: 225px;" /></div>
						<div><label id="status_collector_name"></label></div>
					</div>
				</div>
				<div style="height: 24px; overflow: hidden;clear: both"></div>
				<div class="params">
					<div class="param-name"></div>
					<div class="param-value"><a class="button-blue" title="Create" id="button_create_collector"><span>Create</span></a></div>
					<div class="param-value" style="line-height: 21px;"><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/collectors" title="Cancel" style="margin-left: 6px;">Cancel</a></div>
				</div>
			</div>
			
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/buttons.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>
<script type="text/javascript">

var createCollector = function(params) {
	
	var obj = {
		collectors : {
			createCollector : { 
				accountId : params.accountId,
				opinionId : params.opinionId,
				name : params.name,
				sourceId : params.sourceId,
				serviceId : params.serviceId
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
        	if(data.collectors.createCollector != undefined) {
				if(data.collectors.createCollector.error != undefined) {
					
					errorHandler({
						error : data.collectors.createCollector.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.createCollector);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.createCollector);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var getCollectorTypes = function(params) {
	
	var obj = {
		collectors : {
			getCollectorTypes : {
				accountId : params.accountId
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
        	if(data.collectors.getCollectorTypes != undefined) {
				if(data.collectors.getCollectorTypes.error != undefined) {
					
					errorHandler({
						error : data.collectors.getCollectorTypes.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.getCollectorTypes);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.getCollectorTypes);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};


var collectorTypes = [];
var selectedCollector = null;

var collectorTypesContent = [
	{
		title : "Invite Your Own Respondents",
		description : "Get responses from your customers, friends &amp; followers.",
		typeId : 1,
		type : "link"
	}, 
	{
		title : "Purchase Respondents",
		description : "Buy a target audience from millions of panelists.",
		typeId : 2,
		type : "group"
	}
];

var getCollectorTypeContentByTypeId = function(typeId) {
	for(var i = 0; i < collectorTypesContent.length; i++ ) {
		if(collectorTypesContent[i].typeId == typeId)
			return collectorTypesContent[i];
	}
};


var buttonsCollectorTypes = null;
var init = function() {

	var defaultFocus = function() {
		$('#text_collector_name').focus();
	};

	defaultFocus(); // set default focus
	
	new validator({
		elements : [{
			element : $('#text_collector_name'),
			status : $('#status_collector_name'),
			rules : [
				{ method : 'required', message : 'This field is required.' }
			]
		}],
		submitElement : $('#button_create_collector'),
		messages : null,
		accept : function () {
			
			createCollector({
				accountId : accountId,
				opinionId : <%=opinionId%>,
				name : $.removeHTMLTags($('#text_collector_name').val()),
				sourceId : selectedCollector.sourceId,
				serviceId : selectedCollector.serviceId,
				success : function(data) {
					location.href = "<%=absoluteURL%>/surveys/<%=opinionId %>/collectors/" + data.collectorId;
				},
				error : function(error) {
					
				}
			});
			
		},
		error : function() {
			// 
		}
	});
	
	getCollectorTypes({
		accountId : accountId,
		success : function(data) {
			collectorTypes = data.list;
			if(collectorTypes.length != 0) {
				
				buttonsCollectorTypes = $('#collector_types').buttons({
					active : 0,
					dataSource : collectorTypes,
					formatter : function(record, index) {
						//return $("<a class=\"button-white\" title=\"" + getCollectorTypeContentByTypeId(record.typeId).title + "\"><i class=\"icon-" + getCollectorTypeContentByTypeId(record.typeId).type + "\"></i><span>" + getCollectorTypeContentByTypeId(record.typeId).title + "</span></a>");
						return $("<a class=\"button-white\" title=\"" + getCollectorTypeContentByTypeId(record.typeId).title + "\"><span>" + getCollectorTypeContentByTypeId(record.typeId).title + "</span></a>");
					},
					change : function(record, index) {
						
						selectedCollector = {
							sourceId : record.sourceId,
							serviceId : record.serviceId
						};
						
						// default collector
						if(record.typeId == 1) {
							
							$('#form_purchase_respondents').hide();
							$('#form_invite_your_own_respondents').show();
						}
						
						// purchase collector
						if(record.typeId == 2) {
							
							if(auth.userInfo.permissions.purchaseRespondents) {
								
								$('#form_invite_your_own_respondents').hide();
								$('#form_purchase_respondents').show();
								
							} else {
								
								// rolling
								buttonsCollectorTypes.setActive(0);
								
								var modal = new lightFace({
									title : "Coming Soon!",
									message : "Get ready to use an exciting new feature.<br/> Email us at <a href=\"mailto:support@inqwise.com?subject=Purchase Respondents\" title=\"support@inqwise.com\">support@inqwise.com</a> and be the first to find out more.",
									actions : [
										{ 
											label : "Close", 
											fire : function() { 
												modal.close();
											}, 
											color: "white" 
										}
									],
									overlayAll : true
								});
								
							}
							
						}
						
					}
				});
				
			} else {
				alert("No collector types");
			}
			
		},
		error : function(error) {
			//
		}
	});
	
	
	// default button
	$(document).bind('keydown', function(e) {
		var code;
        if (!e) var e = window.event;
        if (e.keyCode) code = e.keyCode;
        else if (e.which) code = e.which;

     	// enter
        if(code == 13) {
        	
        	if($('#text_collector_name').is(':focus')) {
        		$('#button_create_collector').trigger('click');
        	}
        	
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

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;

$(function() {
	
	new surveyDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function() {
			init();		
		}
	});
	
});
</script>