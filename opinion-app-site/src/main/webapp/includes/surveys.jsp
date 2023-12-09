
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
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);
	
String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">			
				<h1><%= p.getHeader() %></h1>
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
		<!--
		<div style="clear: both;">
Below is a list of all your surveys. To view the details or change the properties of a survey, just click the name.<br/> To create a new survey, click "Create Survey".
		</div>
		-->
		<div id="table_surveys"></div>
	</div>
</div>

<script type="text/javascript">
var pWin;

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

var surveys = [];
var tableSurveys = null;
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
			{ key : 'name', label : "Survey", sortable : true, formatter : function(cell, value, record, source) {
				
				var editable = $("<div class=\"editable\"><a href=\"<%=absoluteURL %>/surveys/" + record.opinionId + "/" + (record.started != 0 ? "overall-results" : "edit") + "\" title=\"" + record.name + "\">" + record.name + "</a><div class=\"editable-icon\"></div></div>").data(record);
				editable.click(function(event) {
					
					var target = $( event.target );
					
					if(target.is("a")) {
						//event.preventDefault();
					} else {
						
						var givenData = $(this).data();
						var newLeft = $(this).closest("td").position().left - 5;
						var newTop = $(this).closest("td").position().top - 5;
						
						$('.cell-editor').remove();
						cellEditor = $("<div class=\"cell-editor\">" +
							"<div class=\"cell-editor-overlay\">" +
								"<div><input type=\"text\" id=\"text_cell_editor_name\" name=\"cell_editor_name\" style=\"margin-right: 6px;\" /><a href=\"#\" class=\"button-blue cell-editor-button-save\" title=\"Save\"><span>Save</span></a><a href=\"#\" class=\"button-white cell-editor-button-cancel\" title=\"Cancel\" style=\"margin-left: 6px;\"><span>Cancel</span></a></div>" +
								"<div><label id=\"status_cell_editor_name\"></label></div>" +
							"</div>" +
						"</div>").appendTo(document.body);
						
						cellEditor.css({
							left : newLeft + "px",
							top : newTop + "px",
							"position" : "absolute"
						});
						
						var I = cellEditor.find("#text_cell_editor_name");
						var B = cellEditor.find("#status_cell_editor_name");
						
						var v = new validator({
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
									accountId : accountId,
									name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
									title : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
									opinionId : givenData.opinionId,
									success : function(data) {
										
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
										
										// and close
										$('.cell-editor').remove();
										
									},
									error: function() {
										//
									}
								});
								
							},
							error: function() {
								
							}
						});
						
						cellEditor.find(".cell-editor-button-save").click(function(event1) {
							
							event1.preventDefault();
							
							v.validate();
							
						});
						
						cellEditor.find(".cell-editor-button-cancel").click(function(event2) {
							event2.preventDefault();
							$('.cell-editor').remove();
						});
						
						// set value
						I.val(givenData.name)
						.focus()
						.keypress(function(event){
						 	if(event.keyCode == 13){
						 		cellEditor.find(".cell-editor-button-save").click();
						  	}
						});
						
						// outside
						$(document).mouseup(function (e){
					    	var container = cellEditor;
					    	if (!container.is(e.target) // if the target of the click isn't the container...
					        	&& container.has(e.target).length === 0) // ... nor a descendant of the container
					    		{
					        		container.remove();
					    		}
						});
						
					}
				});
				
				return editable;
				
				//return $("<a href=\"<%=absoluteURL %>/surveys/" + record.opinionId + "/" + (record.started != 0 ? "overall-results" : "edit") + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key: 'started', label: "Responses", sortable: true, sortBy : { key : 'started', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				if(record.started != 0) {
					return $("<a href=\"<%=absoluteURL %>/surveys/" + record.opinionId + "/overall-results\" title=\"" + record.started + "\">" + $.addCommas(record.started) + "</a>");
				}
				return record.started;
			}},
			{ key: 'completed', label: "Completed", help : "A Completed responses indicates that the respondents clicked Finish on the last page of the survey.", sortable: true, sortBy : { key : 'completed', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.completed);
			}},
			{ key : 'completionRate', label : "Completion Rate", help : "A Completion Rate is the number of completed responses divided by the number of total responses (completed + partial + disqualified) and expressed in percentage.<br/><br/><table><tr><td rowspan=\"2\">Completion Rate =&nbsp;</td><td align=\"center\" style=\"border-bottom: 1px solid #000\">Completed responses</td></tr><tr><td align=\"center\">(Completed + Partial + Disqualified)</td></tr></table><br/><strong>Example:</strong> If you have five completed responses from 1000 responses, then your Completion Rate is 0.5%.", sortable : true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				var completionRate;
				if(record.started != 0) {
					completionRate = ((record.completed / record.started) * 100).toFixed(2) + "%";
				} else {
					completionRate = "0.00%";
				}
				return completionRate;
            }},
			{ key: 'partial', label: "Partial", help: "A Partial responses indicates that the respondents entered at least one answer, but they did not click Finish on the last page of the survey.", sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.partial);	
			}},
			{ key: 'disqualified', label: "Disqualified", sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				var disqualified = 0;
				return $.addCommas(disqualified);
			}},
            { key: 'timeTaken', label: "Avg. Time Taken", sortable: false, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
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
            { key : 'lastResponseDate', label : "Last Response Date", sortable : true, sortBy : { dataType: "date" }, formatter: function(cell, value, record, source) {
				if(record.lastResponseDate) {
					var left = record.lastResponseDate.substring(record.lastResponseDate.lastIndexOf(" "), " ");
					var right = record.lastResponseDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
			{ key: 'modifyDate', label: "Last Modify Date", sortable: true, sortBy : { dataType: "date" }, formatter: function(cell, value, record, source) {
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
		messages: {
            noRecords: "No matching records found.",
            noData: "You don't have surveys. Click \"+ Create Survey\" above to create one."
        },
		dataSource : surveys, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{
				label : "Create Survey",
				color: "green",
				icon : "add-white",
				fire : function() {
					location.href = "<%=absoluteURL %>/surveys/create";
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
						label : "Rename",
						fire : function(records, source) {
							
							var v = null;
							var M = $("<div>" +
								"<div style=\"padding: 0 0 12px 0\">Enter a new survey name.</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"text_rename_survey_name\" name=\"rename_survey_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
										"<div><label id=\"status_rename_survey_name\"></label></div>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#text_rename_survey_name');
							var B = M.find('#status_rename_survey_name');
							
							var modal = new lightFace({
								title : "Rename survey",
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
												accountId : accountId,
												name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												title : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												opinionId : records[0].opinionId,
												success : function(data) {
													
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
													
													// refresh
													modal.close(); 
													
													// location.href = "<%=absoluteURL %>/surveys/" + data.opinionId + "/edit";
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
								"<div style=\"padding: 0 0 12px 0\">Enter a name you'll use to reference this survey.</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"input_copy_survey_name\" name=\"input_copy_survey_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
										"<div><label id=\"status_input_copy_survey_name\"></label></div>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#input_copy_survey_name');
							var B = M.find('#status_input_copy_survey_name');
							
							var modal = new lightFace({
								title : "Copying survey",
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
												accountId : accountId,
												name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												title : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												opinionType : 1,
												folderId : null,
												opinionId : records[0].opinionId,
												success : function(data) {
													
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
													
													// location.href = "<%=absoluteURL %>/surveys/" + data.opinionId + "/edit";
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
								title : "Export survey",
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
												accountId : accountId,
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
												accountId : accountId,
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
					/*
					{
						label : "Google Spreadsheets",
						active : true,
						fire : function(records, source) {
							
							$.ajax({
								type: "GET",
								url : "<%=applicationURL%>/servlet/export",
								data : {
									rq : JSON.stringify({
										format : "csv",
										opinionId : records[0].opinionId,
										name : "Test"
									})
								},
								success : function(data) {
									
									var __form = $("<form method=\"POST\" style=\"display: none\" action=\"https://docs.google.com/spreadsheet/import?authuser=0\" target=\"_blank\">" +
										"<input type=\"hidden\" value=\"0\" name=\"authuser\" />" +
										"<input type=\"hidden\" value=\"inline\" name=\"source\" />" +
										"<input type=\"hidden\" value=\"\" name=\"csv\" />" +
										"<input type=\"hidden\" value=\"\" name=\"title\" />" +
									"</form>");
									
									var now = new Date();
									
									__form.find("input[name=csv]").val(data);
									__form.find("input[name=title]").val("Results_" + records[0].opinionId + "_" + now.format("yyyymmdd_HHMM"));
									__form.appendTo("body");
									
									__form.submit();
									
									
								},
								error: function (XHR, textStatus, errorThrow) {
									 // error
								}
							});
							
						}
					},
					*/
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
												accountId : accountId,
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
//var googleAuth = false;

$(function() {
	
	/*
	var img = new Image();
	$(img).load(function () {
		googleAuth = true;
	    //console.log("SUCC"); 
    })
	.attr("src", "https://docs.google.com/spreadsheet/tq?requireauth=1&" + $.getTimestamp());
	*/
	
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

});	
</script>