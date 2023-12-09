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
	
	String accountId = request.getParameter("account_id");
%>

<div>
	<h1 style="padding-bottom: 0 !important"><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<span id="header_account_name"></span></h1>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln" id="list_account_details"></ul>
	</div>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" title="Account Details"><span>Account Details</span></a></li>
					<li><a href="<%=absoluteURL %>/account-users?account_id=<%=accountId %>" title="Users"><span>Users</span></a></li>
					<li><a href="<%=absoluteURL %>/account-surveys?account_id=<%=accountId %>" title="Surveys" class="selected"><span>Surveys</span></a></li>
					<li><a href="<%=absoluteURL %>/account-collectors?account_id=<%=accountId %>" title="Collectors"><span>Collectors</span></a></li>
					<li><a href="<%=absoluteURL %>/account-billing?account_id=<%=accountId %>" title="Billing"><span>Billing</span></a></li>
					<li><a href="<%=absoluteURL %>/account-transaction-history?account_id=<%=accountId %>" title="Transaction History"><span>Transaction History</span></a></li>
					<li><a href="<%=absoluteURL %>/account-make-payment?account_id=<%=accountId %>" title="Make a Payment"><span>Make a Payment</span></a></li>
					<li><a href="<%=absoluteURL %>/account-charges?account_id=<%=accountId %>" title="Charges"><span>Charges</span></a></li>
					<li><a href="<%=absoluteURL %>/account-recurring?account_id=<%=accountId %>" title="Recurring"><span>Recurring</span></a></li>
					<li><a href="<%=absoluteURL %>/account-uninvoiced-list?account_id=<%=accountId %>" title="UnInvoiced List"><span>UnInvoiced List</span></a></li>
					<li><a href="<%=absoluteURL %>/account-invoices?account_id=<%=accountId %>" title="Invoices"><span>Invoices</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both; padding-top: 20px;">
		<div id="table_surveys"></div>
	</div>
</div>

<script type="text/javascript">
var pWin;

var getAccountDetails = function(params) {
	
	var obj = {
		accounts : {
			getAccountDetails : {
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
			if(data.accounts.getAccountDetails != undefined) {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.getAccountDetails);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getList = function(params) {
	
	var obj = {
		opinions : {
			getList : {
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

var copy = function(params) {
	
	var obj = {
		opinions : { 
			copy : {
				opinionId : params.opinionId,
				name : params.name,
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
			if(data.opinions.copy != undefined) {
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
						params.error(data.opinions.deleteOpinions);
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

var tableSurveys = null;
var surveys = [];
var renderTableSurveys = function() {
	
	$('#table_surveys').empty();
	tableSurveys = $('#table_surveys').dataTable({
		tableColumns : [
			{ key : 'opinionId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'opinionId', label : '', width: 12, formatter : function(cell, value, record, source) {
				return $("<a href=\"" + record.previewUrl + "\" title=\"Preview\" class=\"row-button\"><i class=\"row-icon-preview\">&nbsp;</i></a>").click(function() {
					
					var name = "pWin";
					this.target = name;
					
					if (pWin) 
						pWin.close();
					
					pWin = window.open('', name, 'resizable=1,scrollbars=1,status=1,menubar=1');
					
				}).tipsy({
					gravity: 'sw', 
					title: function() { 
						return this.getAttribute('original-title'); 
					}
				});
			}},
			{ key : 'name', label : 'Name', sortable : true, formatter : function(cell, value, record, source) {
				return $("<a href=\"#\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key: 'started', label: 'Responses', sortable: true, sortBy : { key : 'started', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.started);
			}},
			{ key: 'completed', label: 'Completed', sortable: true, sortBy : { key : 'completed', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.completed);	
			}},
			{ key : 'completionRate', label : 'Completion Rate', sortable : true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				var completionRate;
				if(record.started != 0) {
					completionRate = ((record.completed / record.started) * 100).toFixed(2) + "%";
				} else {
					completionRate = "0.00%";
				}
				return completionRate;
            }},
			{ key: 'partial', label: 'Partial', sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.partial);
			}},
			{ key: 'disqualified', label: 'Disqualified', sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				var disqualified = 0;
				return $.addCommas(disqualified);
			}},
            { key: 'timeTaken', label: 'Avg. Time Taken', sortable: false, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
            	if(record.timeTaken != undefined) {
            		var timeTaken = (record.timeTaken.days != 0 ? record.timeTaken.days + (record.timeTaken.days > 1 ? " days, " : " day, ") : "") +
            		(record.timeTaken.hours != 0 ? record.timeTaken.hours + (record.timeTaken.hours > 1 ? " hours, " : " hour, ") : "") + 
            		(record.timeTaken.minutes != 0 ? record.timeTaken.minutes + (record.timeTaken.minutes > 1 ? " mins, " : " min, ") : "") +
            		(record.timeTaken.seconds != 0 ? record.timeTaken.seconds + (record.timeTaken.seconds > 1 ? " secs" : " sec") : "");
            		
            		if(record.timeTaken.days == 0 
   						&& record.timeTaken.hours == 0
   						&& record.timeTaken.minutes == 0
   						&& record.timeTaken.seconds == 0) {
               			timeTaken = "Less than sec";
               		}
            		
            		return timeTaken;
            	}
            }},
			{ key: 'modifyDate', label: 'Modify Date', sortable: true, sortBy : { dataType: "date" }, formatter: function(cell, value, record, source) {
				if(record.modifyDate) {
					var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
					var right = record.modifyDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
            { key: 'cntControls', label: 'Questions', sortable: true, sortBy : { dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } }
		],
		totals : [
		   [
				{ key : "name", calculate : false, formatter : function(totals) {
					return "Total - all surveys";
				}},
				{ key : "started", formatter : function(totals) {
		    		return $.addCommas(totals.started);
		    	}},
		    	{ key : "completed", formatter : function(totals) {
		    		return $.addCommas(totals.completed);
		    	}},
		    	{ key : "completionRate", formatter : function(totals) { 
		    		var completionRate;
		    		if((totals.completed + totals.partial) != 0) {
		    			completionRate = ((totals.completed / totals.started) * 100).toFixed(2) + "%";
		    		} else {
		    			completionRate = "0.00%";
		    		}
		    		return completionRate; 
		    	}},
		    	{ key : "partial", formatter: function(totals) {
		    		return $.addCommas(totals.partial);	
		    	}},
		    	{ key : "disqualified", calculate : false, formatter : function(totals) {
		    		return "0";
		    	}},
		    	{ key : "timeTaken", calculate : false, formatter : function(totals) {
		    		return "--";
		    	}}
		   ]
		],
		dataSource : surveys, 
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
							list.push(records[index].opinionId);
						});
						
						var modal = new lightFace({
							title : "Deleting surveys",
							message : "Are you sure you want to delete selected surveys?",
							actions : [
								{ 
									label : "Delete", 
									fire : function() {
										
										deleteOpinions({
											list : list,
											accountId : accountId,
											success : function() {
												
												getList({
													accountId : accountId,
													success : function(data) {
														surveys = data.list;
														renderTableSurveys();
													},
													error : function() {
														surveys = [];
														renderTableSurveys();
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
			},
			{
				label : "More actions",
				disabled : true,
				condition : 1,
				actions : [
				    {
				    	label : "Copy to another account",
				    	fire : function(records, source) {
				    		
				    		var v = null;
							var M = $("<div>" +
								"<div style=\"padding: 0 0 12px 0\">Enter a name you'll use to reference this survey.</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"input_copy_survey_name\" name=\"input_copy_survey_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
										"<div><label id=\"status_input_copy_survey_name\"></label></div>" +
									"</div>" +
								"</div>" +
								"<div style=\"padding: 0 0 12px 0\">Account Id</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"input_copy_to_account_id\" name=\"input_copy_to_account_id\" maxlength=\"100\" autocomplete=\"off\" /></div>" +
										"<div><label id=\"status_input_copy_to_account_id\"></label></div>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#input_copy_survey_name');
							var B = M.find('#status_input_copy_survey_name');
							var T = M.find('#input_copy_to_account_id');
							var A = M.find('#status_input_copy_to_account_id');
							
							var modal = new lightFace({
								title : "Copying survey to another account",
								message : M,
								actions : [
								   { 
									   label : "Copy", 
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
									
									// set default values
									I.val(records[0].name);
									
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
											},
											{
												element : T,
												status : A,
												rules : [
													{ method : 'required', message : 'This field is required.' },
													{ method : 'digits', message : 'Please enter only digits.' }
												]
											}
										],
										submitElement : null,
										messages : null,
										accept : function () {
											
											copy({
												opinionId : records[0].opinionId,
												name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												accountId : parseInt(T.val()),
												success : function(data) {
													
													// refresh table
													getList({
														accountId : accountId,
														success : function(data) {
															surveys = data.list;
															renderTableSurveys();
														},
														error : function() {
															surveys = [];
															renderTableSurveys();
														}
													});
													
													modal.close(); 
													
													// location.href = "https://www.inqwise.com/en-us/surveys/" + data.opinionId + "/edit";
												},
												error: function() {
													//
												}
											});
											
										},
										error: function() {
											// error
										}
									});
								}
							});
							
							// end
							
				    		
				    	}
				    }
				]
			},
			{
				label : "Export results",
				icon : "down",
				disabled : true,
				condition : 1,
				actions : [
					{ 
						label : "Excel (*.XLSX)",
						fire : function(records, source) {
							
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
									I.val("Results_" + records[0].opinionId + "_" + now.format("yyyymmdd_HHMM")).select();
									
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
												format: 'excel',
												opinionId : records[0].opinionId,
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
							
						}
					},
					{ 
						label : "CSV",
						fire : function(records, source) {
							
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
									I.val("Results_" + records[0].opinionId + "_" + now.format("yyyymmdd_HHMM")).select();
									
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
												format: 'csv',
												opinionId : records[0].opinionId,
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
							
						}
					}
				]
			}
		]
	});
	
};

var getAccountStatus = function(statusId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = "Enabled"; // Your account is now enabled.
			break;
		case 2 : 
			s = "Disabled"; // Your account is disabled. 
			break;
		case 3 : 
			s = "Expired"; // Your account has expired.
			break;
		case 4 :
			s = "Suspended"; // Your account is suspended.
			break;
	}
	return s;
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

var accountId = <%=accountId%>;

$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			// details
			// TODO: change flag isActive to statusId
			$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getAccountStatus(data.isActive ? 1 : 2) + "</li>").appendTo('#list_account_details');
			
			
			getList({
				accountId : accountId,
				success : function(data) {
					surveys = data.list;
					renderTableSurveys();
				},
				error : function() {
					surveys = [];
					renderTableSurveys();
				}
			});
		
		}
	});
	
});
</script>