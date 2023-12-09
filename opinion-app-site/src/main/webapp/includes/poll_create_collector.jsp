
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>

<%
	String opinionId = request.getParameter("opinion_id");
	
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<div>
		<h1><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors" title="Collectors">Collectors</a>&nbsp;&rsaquo;&nbsp;Add Collector</h1>
		<!-- <h3 class="ui-header-light">How Would You Like to Collect Responses?</h3>
		<div style="padding-top: 12px;">Define Your Target.<br/> Select the method you would like to use to collect responses.</div> -->
		<div style="display: none;Xpadding-top: 12px;">
			<div id="collector_types"></div>
		</div>
		<div style="clear: both;">
			
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
					<div class="param-value" style="line-height: 21px;"><a href="<%=absoluteURL %>/polls/<%=opinionId %>/collectors" title="Cancel" style="margin-left: 6px;">Cancel</a></div>
				</div>
			</div>
			
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/buttons.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_details.js"></script>
<script type="text/javascript">

var createCollector = function(params) {
	
	var obj = {
		collectors : {
			createCollector : { 
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
			getCollectorTypes : {}
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
				opinionId : <%=opinionId%>,
				name : $.removeHTMLTags($('#text_collector_name').val()),
				sourceId : 1, // selectedCollector.sourceId,
				serviceId : null, // selectedCollector.serviceId,
				success : function(data) {
					location.href = "<%=absoluteURL%>/polls/<%=opinionId %>/collectors/" + data.collectorId;
				},
				error : function(error) {
					
				}
			});
			
		},
		error : function() {
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
        	
        	/*
        	if(!$('#button_create').is(':focus')) {
				v.validate();
			}
        	*/
        	
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

$(function() {
	
	new pollDetails({ 
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