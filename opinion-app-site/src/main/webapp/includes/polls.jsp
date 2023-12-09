
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>

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
				<h1>Polls</h1>
			</td>
			<td class="cell-right">
				<ul class="gac">
					<li>
						<a id="link_import" title="Import" class="button-green fileinput-button" style="display: none; margin-left: 6px;">
							<span><i class="icon-import-white-small">&nbsp;</i></span>
							<input id="fileupload" type="file" name="files[]" multiple>
						</a>
					</li>
				</ul>
			</td>
		</tr>
	</table>
	<div>
		<div style="clear: both;">
Below is a list of all your polls. To view the details or change the properties of a poll, just click the name.<br/>
To create a new poll or copy an existing, click "Create Poll".
		</div>
		<div style="padding-top: 20px; clear: both">
			<div id="table_polls"></div>
		</div>
	</div>
</div>


<script type="text/javascript">
var pWin;

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

var polls = [];
var tablePolls = null;
var renderTablePolls = function() {
	
	$('#table_polls').empty();
	tablePolls = $('#table_polls').dataTable({
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
				return $("<a href=\"<%=absoluteURL %>/polls/" + record.opinionId + "/" + (record.started != 0 ? "overall-results" : "edit") + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key: 'started', label: 'Responses', sortable: true, sortBy : { key : 'started', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				if(record.started != 0) {
					return $("<a href=\"<%=absoluteURL %>/polls/" + record.opinionId + "/overall-results\" title=\"" + record.started + "\">" + record.started + "</a>");
				}
				return record.started;
			}},
			{ key: 'completed', label: 'Completed', sortable: true, sortBy : { key : 'completed', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key: 'partial', label: 'Partial', sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'completionRate', label : 'Completion Rate', sortable : true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return record.completionRate + "%";
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
			{ key: 'modifyDate', label: 'Modify Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.modifyDate) {
					var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
					var right = record.modifyDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		totals : [
		   [
				{ key : "name", calculate : false, formatter : function(totals) {
					return "Total - all polls";
				}},
		    	{ key : "started" },
		    	{ key : "completed" },
		    	{ key : "partial" },
		    	{ key : "completionRate", formatter : function(totals) { 
		    		var completionRate;
		    		if((totals.completed + totals.partial) != 0) {
		    			completionRate = Math.round(totals.completed / (totals.completed + totals.partial) * 100) + "%";
		    		} else {
		    			completionRate = "0%";
		    		}
		    		return completionRate; 
		    	}},
		    	{ key : "timeTaken", calculate : false, formatter : function(totals) {
		    		return "--";
		    	}}
		   ]
		],
		dataSource : polls, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{
				label : "Create Poll",
				color: "green",
				icon : "add-white",
				fire : function() {
					location.href = "<%=absoluteURL %>/polls/create";
				}
			},
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
							title : "Deleting polls",
							message : "Are you sure you want to delete selected polls?",
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
														polls = data.list;
														renderTablePolls();
													},
													error : function() {
														polls = [];
														renderTablePolls();
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
						label : "Rename", 
						fire : function(records, source) {
							
							var v = null;
							var M = $("<div>" +
								"<div style=\"padding: 0 0 12px 0\">Enter a new poll name.</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"text_rename_poll_name\" name=\"rename_poll_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
										"<div><label id=\"status_rename_poll_name\"></label></div>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#text_rename_poll_name');
							var B = M.find('#status_rename_poll_name');
							
							var modal = new lightFace({
								title : "Rename poll",
								message : M,
								actions : [
								   { 
									   label : "Save", 
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
									I.val(records[0].name).select();
									
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
											
											rename({
												name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												/*title : $.removeHTMLTags(I.val()).replace(/\r/g, ""),*/
												opinionId : records[0].opinionId,
												success : function(data) {
													
													getList({
														accountId : accountId,
														success : function(data) {
															polls = data.list;
															renderTablePolls();
														},
														error : function() {
															polls = [];
															renderTablePolls();
														}
													});
													
													// refresh
													modal.close(); 
													
													// location.href = "<%=absoluteURL %>/polls/" + data.opinionId + "/edit";
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
							
						}
					},
					{ 
						label : "Copy", 
						fire : function(records, source) {
							
							var v = null;
							var M = $("<div>" +
								"<div style=\"padding: 0 0 12px 0\">Enter a name you'll use to reference this poll.</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"input_copy_poll_name\" name=\"input_copy_poll_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
										"<div><label id=\"status_input_copy_poll_name\"></label></div>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#input_copy_poll_name');
							var B = M.find('#status_input_copy_poll_name');
							
							var modal = new lightFace({
								title : "Copying poll",
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
									I.val("Copy of " + records[0].name);
									
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
											
											copy({
												name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												/*title : $.removeHTMLTags(I.val()).replace(/\r/g, ""),*/
												opinionType : 1,
												folderId : null,
												opinionId : records[0].opinionId,
												success : function(data) {
													
													getList({
														accountId : accountId,
														success : function(data) {
															polls = data.list;
															renderTablePolls();
														},
														error : function() {
															polls = [];
															renderTablePolls();
														}
													});
													
													modal.close(); 
													
													// location.href = "<%=absoluteURL %>/polls/" + data.opinionId + "/edit";
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
							
							
						}
					},
					{
						label : "Export",
						active : auth.userInfo.permissions.importExportOpinion,
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
								title : "Export poll",
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
									
									// set default values
									I.val(records[0].name).select();
									
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
												opinionId : records[0].opinionId,
												name : $.removeHTMLTags(I.val().replace(/[/\\:?<>|\"]+/g, "")).replace(/\r/g, ""),
												exportType : "opinion"
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




var getList = function(params) {
	
	var obj = {
		opinions : {
			getList : {
				folderId : null,
				top : 100,
				from : undefined,
				to : undefined,
				accountId : params.accountId,
				opinionTypeId : 2 /*polls*/
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

var deleteOpinions = function(params) {
	
	var obj = {
		opinions : {
			deleteOpinions : {
				list : params.list,
				accountId : params.accountId,
				opinionTypeId : 2 /*survey*/
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

//var accountId = auth.userInfo.accountId;

$(function() {
	
	getList({
		accountId : accountId,
		success : function(data) {
			polls = data.list;
			renderTablePolls();
		},
		error : function() {
			polls = [];
			renderTablePolls();
		}
	});

});
</script>