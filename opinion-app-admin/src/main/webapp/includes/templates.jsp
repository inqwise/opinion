<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">			
				<h1>Templates</h1>
			</td>
			<td class="cell-right">
				
			</td>
		</tr>
	</table>
	<div style="clear: both;">
		<div id="table_templates"></div>
	</div>
</div>

<script type="text/javascript">

var getTemplate = function(params) {
	
	var obj = {
		opinions : {
			getTemplate : {
				templateId : params.templateId
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
        	if(data.opinions.getTemplate != undefined) {
	        	if(data.opinions.getTemplate.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.getTemplate.error
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getTemplate);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getTemplate);
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

var updateTemplate = function(params) {
	
	var obj = {
		opinions : {
			updateTemplate : {
				templateId : params.templateId
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
        	if(data.opinions.updateTemplate != undefined) {
	        	if(data.opinions.updateTemplate.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.updateTemplate.error
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.updateTemplate);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.updateTemplate);
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

var activateTemplate = function(params) {
	
	var obj = {
		opinions : {
			activateTemplate : {
				templateId : params.templateId
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
        	if(data.opinions.activateTemplate != undefined) {
	        	if(data.opinions.activateTemplate.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.activateTemplate.error
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.activateTemplate);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.activateTemplate);
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










var getTemplates = function(params) {
	
	var obj = {
		opinions : {
			getTemplates : {
				
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
        	if(data.opinions.getTemplates != undefined) {
	        	if(data.opinions.getTemplates.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.getTemplates.error
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getTemplates);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getTemplates);
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

var deleteTemplates = function(params) {
	
	var obj = {
		opinions : {
			deleteTemplates : {
				list : params.list
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
        	if(data.opinions.deleteTemplates != undefined) {
	        	if(data.opinions.deleteTemplates.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.deleteTemplates.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.deleteTemplates);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.deleteTemplates);
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

var tableTemplates = null;
var templates = [];
var renderTableTemplates = function() {
	
	$('#table_templates').empty();
	tableTemplates = $('#table_templates').dataTable({
		tableColumns : [
			{ key : 'templateId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'name', label : 'Name', sortable : true, formatter : function(cell, value, record, source) {
				return $("<a href=\"#\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key : 'url', label : 'URL', sortable : true },
			{ key : 'description', label : 'Description', sortable : true },
			{ key : 'keywords', label : 'Keywords', sortable : true },
			{ key : 'modifyDate', label: 'Modify Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.modifyDate) {
					var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
					var right = record.modifyDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : templates, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
		   	{
				label : "Delete",
				disabled : true,
				fire : function(records, source) {

					if(records.length > 0) {
						
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].templateId);
						});
						
						var modal = new lightFace({
							title : "Deleting template",
							message : "Are you sure you want to delete selected templates?",
							actions : [
								{ 
									label : "Delete", 
									fire : function() { 
										
										deleteTemplates({
											list : list,
											success : function() {
												
												getTemplates({
													success : function(data) {
														templates = data.list;
														renderTableTemplates();
													},
													error : function() {
														templates = [];
														renderTableTemplates();
													}
												});
												
												modal.close();
												
											},
											error : function() {
												//
											}
										});
										
									},
									color: "blue"
								}, 
								{
									label : "Cancel",
									fire: function() {
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
		]
	});
	
};
		
$(function() {
	
	getTemplates({
		success : function(data) {
			templates = data.list;
			renderTableTemplates();
		},
		error : function() {
			templates = [];
			renderTableTemplates();
		}
	});
	
});
</script>