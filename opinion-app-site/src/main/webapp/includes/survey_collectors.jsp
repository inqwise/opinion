
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
	<div class="content-middle-tabs-section">
		<div class="content-middle-tabs">
			<ul class="content-middle-tabs-container">
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/edit" title="Edit"><span>Edit</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/settings" title="Settings"><span>Settings</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/reordering" title="Reordering"><span>Reordering</span></a></li>
				<li id="tab_rules"><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/rules" title="Rules"><span>Rules<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/branding" title="Branding"><span>Branding<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/collectors" class="selected" title="Collectors"><span>Collectors</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/overall-results" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	
	<!--
	<div class="panel-collector-create" style="display: none;clear: both; padding-top: 24px;">
		<div>We refer to the method that you use to collect responses as a "collector".<br/> 
		While most people use only a single collector, you may want to use multiple collectors if you are sending your survey to different groups of people.<br/>
		Each collector can have its own unique settings and restrictions, and can be closed and opened independently.</div>
		<div style="padding-top: 12px;">
			<a href="<%=absoluteURL %>/surveys/<%=opinionId %>/collectors/create" class="button-yellow" title="Create Collector"><span>Create Collector</span></a>
		</div>
		<br/>
		<br/>
	</div>
	-->
	
	<div style="padding-top: 20px;">
		<div>Below is a list of the collectors you are currently using to collect responses. To view the details or change the properties of an existing collector, just click the name.<br/> To collect more responses for this survey from a different group of people, click "Add Collector".</div>
		<div style="padding-top: 10px;">
			<div>
				<div id="table_collectors"></div>
			</div>
		</div>
	</div>
	
</div>


<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>
<script type="text/javascript">

var opinionId = <%=opinionId%>;

var contentDictionary = {
	titles : {
		closeCollector : "Close Collector",
		openCollector : "Open Collector"
	},
	messages : {
		defaultCloseMessage : "This survey is currently closed.  Please contact the author of this survey for further assistance."
	}
};

/*
 * A Disqualified response indicates that the respondent answered a question that disqualified them due to disqualification logic placed on the survey.
 */

var collectors = [];
var tableCollectors = null;
var renderTableCollectors = function() {
	
	$('#table_collectors').empty();
	tableCollectors = $('#table_collectors').dataTable({ 
		tableColumns : [
			{ key : 'collectorId', label : '#', sortable : true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'name', label : 'Name', sortable : true, formatter: function(cell, value, record, source) {
				
				var editable = $("<div class=\"editable\"><a href=\"<%=absoluteURL %>/surveys/<%=opinionId %>/collectors/" + record.collectorId + "\" title=\"" + record.name + "\">" + record.name + "</a><div class=\"editable-icon\"></div></div>").data(record);
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
								
								changeCollectorName({
									accountId : accountId,
									name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
									collectorId : givenData.collectorId,
									success : function(data) {
										
										getCollectors({
											accountId : accountId,
											opinionId : opinionId,
											success : function(data) {
												collectors = data.list;
												renderTableCollectors();
											},
											error: function(error) {
												collectors = [];
												renderTableCollectors();
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
				
			}},
			{ key : 'sourceTypeId', label : 'Type', sortable : true, formatter: function(cell, value, record, source) {
				return getCollectorType(record.sourceTypeId);	
			}},
			{ key : 'statusId', label : 'Status', sortable : true, formatter: function(cell, value, record, source) {
				
				var q = "";
				
				if(record.sourceTypeId == 1) { 
				
					q = $("<a href=\"javascript:;\" title=\"" + (record.statusId == 1 ? contentDictionary.titles.closeCollector : contentDictionary.titles.openCollector) + "\" collector_id=\"" + record.collectorId + "\" status_id=\"" + record.statusId + "\" close_message=\"" + (record.closeMessage != undefined ? record.closeMessage : "") + "\" >" + (record.statusId == 1 ? getCollectorStatus(1, record.sourceTypeId) : getCollectorStatus(2, record.sourceTypeId)) + "</a>")
					.tipsy({
						gravity: 's', 
						title: function() { 
							return this.getAttribute('original-title'); 
						}
					})
					.click(function() {
	
						var collectorId = parseInt($(this).attr('collector_id'));
						var statusId = parseInt($(this).attr('status_id'));
						var closeMessage = $(this).attr('close_message');
	
						if(statusId == 1) {	
	
							var M = $("<div>" +
								"<div style=\"padding: 0 0 12px 0\">Closing this collector will prevent anyone who is using this collector<br/> from entering responses and will interrupt any responses that are in progress.</div>" +
								"<div class=\"row\" style=\"height: 84px;\">" +
									"<div class=\"cell\">" +
										"<textarea id=\"textarea_close_collector_message\" name=\"textarea_close_collector_message\" autocomplete=\"off\" style=\"width: 314px; height: 74px;\"></textarea>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#textarea_close_collector_message');
								
							var modal = new lightFace({
								title : "Are you sure you want to stop collecting responses?",
								message : M,
								actions : [
						           { 
						        	   label : "Stop Collecting Now", 
						        	   fire : function() {
						        	   
						        		   updateCollectorStatus({
						        			   	accountId : accountId,
								   				collectorId : collectorId,
								   				statusId : 2,
								   				closeMessage : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
								   				isGenerateNewGuid : false,
								   				success : function() {
								   					
								   					$(q)
													.text(getCollectorStatus(2, 1))
													.attr({ 'title' : contentDictionary.titles.openCollector, 'status_id' : 2, 'close_message' : $.removeHTMLTags(I.val()).replace(/\r/g, "") });
				
													record.statusId = 2;
	
													modal.close();
								   					
								   				},
								   				error: function() {
								   					
								   				}
						        		   });
						        	   	
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
									I.text(((closeMessage == "") ? contentDictionary.messages.defaultCloseMessage : closeMessage));
								}
							});
	
						}
	
						if(statusId == 2) {
	
							var modal2 = new lightFace({
								title : "Are you sure you want to start collecting responses?",
								message : "This collector is currently closed.<br/> You can re-open this collector now.",
								actions : [
						           { 
						        	   label : "Open Collector", 
						        	   fire : function() {

						        		   updateCollectorStatus({
						        			   	accountId : accountId,
								   				collectorId : collectorId,
								   				statusId : 1,
								   				closeMessage : null,
								   				isGenerateNewGuid : false,
								   				success : function() {
								   					
								   					$(q)
													.text(getCollectorStatus(1, 1))
													.attr({ 'title' : contentDictionary.titles.closeCollector, 'status_id' : 1 });
													record.statusId = 1;
	
													modal2.close();
								   					
								   				},
								   				error: function() {
								   					
								   				}
						        		   });
										
							       		}, 
							       		color: "blue" 
							       	},
							       	{ 
							       		label : "Cancel", 
							       		fire : function() { 
							       			modal2.close(); 
							       		}, 
							       		color: "white" 
							       	}
								],
								overlayAll : true
							});
							
						}
						
					});
					
				} else {
					
					if (record.statusId == 3) {
					
						if(record.charge != undefined) {
							
							q = $("<a href=\"javascript:;\" collector_id=\"" + record.collectorId + "\" charge_id=\"" + record.charge.chargeId + "\" title=\"" + getCollectorStatus(record.statusId) + " $" + (record.charge.amountDue).formatCurrency(2, '.', ',') + "\">" + getCollectorStatus(record.statusId) + " $" + (record.charge.amountDue).formatCurrency(2, '.', ',') + "</a>");
							q.click(function() {
								
								var collectorId = parseInt($(this).attr('collector_id'));
								var chargeId = parseInt($(this).attr('charge_id'));
								
								// ask are you sure
								payCharge({
									accountId : accountId,
									chargeId : chargeId,
									success : function(data) {
										
										if(data.transactionId != undefined) {
											// do refresh
											location.href = "<%=absoluteURL %>/surveys/<%=opinionId%>/collectors/" + collectorId;
											
										}
										
									},
									error : function(data) {
										
										if(data.error == "NoEnoughFunds") {
											
											if (data.amountToFund != undefined && data.chargeId != undefined) {
											
												if(data.balance == 0) {
													
													// redirect to 
													location.href = "<%=absoluteURL %>/make-payment?charges=" + data.chargeId; // + "&return_url=<%=absoluteURL %>/upgrade";
													
												} else {
													
													var modal = new lightFace({
														title : "Your account balance is running low.",
														message : $.format("Your account balance is running low. You have only <b>{0}</b>.<br/> To complete purchase, please make a payment of <b>{1}</b>.", "$" + (data.balance).formatCurrency(2, '.', ','), "$" + (data.amountToFund).formatCurrency(2, '.', ',')),
														actions : [
													        { 
													        	label : "Make a payment", 
													        	fire : function() {
													        		// redirect to make a payment
													        		location.href = "<%=absoluteURL %>/make-payment?charges=" + chargeId + "&return_url=<%=absoluteURL %>/surveys/<%=opinionId%>/collectors/" + collectorId;
													        		
													        		modal.close();
													        	}, 
													        	color : "blue" 
													        },
													        { 
													        	label : "Cancel", 
													        	fire : function() {
													        		
													        		modal.close(); 
													        	}, 
													        	color : "white" 
													        }
														],
														overlayAll : true
													});	
												
												}
												
											}
											
										}
										
									}
								});
								
							});
						}
					
					} else {
						
						// all other statuses
						q = getCollectorStatus(record.statusId);
						
					}
					
				} 
				
				return q;
			}},
			{ key : 'started', label : 'Responses', sortable : true, sortBy : { dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				if(record.started != 0) {
					return $("<a href=\"<%=absoluteURL %>/surveys/" + record.opinionId + "/overall-results\" title=\"" + record.started + "\">" + $.addCommas(record.started) + "</a>");
				}
				return record.started;
			}},
			{ key: 'completed', label: 'Completed', help : "A Completed responses indicates that the respondents clicked Finish on the last page of the survey.", sortable: true, sortBy : { key : 'completed', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $.addCommas(record.completed);
			}},
			{ key : 'completionRate', label : 'Completion Rate', help : "A Completion Rate is the number of completed responses divided by the number of total responses (completed + partial + disqualified) and expressed in percentage.<br/><br/><table><tr><td rowspan=\"2\">Completion Rate =&nbsp;</td><td align=\"center\" style=\"border-bottom: 1px solid #000\">Completed responses</td></tr><tr><td align=\"center\">(Completed + Partial + Disqualified)</td></tr></table><br/><strong>Example:</strong> If you have five completed responses from 1000 responses, then your Completion Rate is 0.5%.", sortable : true, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				var completionRate;
				if(record.started != 0) {
					completionRate = ((record.completed / record.started) * 100).toFixed(2) + "%";
				} else {
					completionRate = "0.00%";
				}
				return completionRate;
            }},
			{ key: 'partial', label: 'Partial', help: "A Partial responses indicates that the respondents entered at least one answer, but they did not click Finish on the last page of the survey.", sortable: true, sortBy : { key : 'partial', dataType: "number" }, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell,value, record, source) {
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
			{ key : 'lastResponseDate', label : 'Last Response Date', sortable : true, sortBy : { dataType: "date" }, formatter: function(cell, value, record, source) {
				if(record.lastResponseDate) {
					var left = record.lastResponseDate.substring(record.lastResponseDate.lastIndexOf(" "), " ");
					var right = record.lastResponseDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		], 
		totals : [
		   [
				{ key : "name", calculate : false, formatter : function(totals) {
					return "Total - all collectors";
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
            noData: "You don't have any collectors for this survey. Click \"+ Add Collector\" above to create one."
        },
		dataSource : collectors, 
		pagingStart: 10,
		selectable : true,
		actions : [
			{
				label : "Add Collector",
				color: "green",
				icon : "add-white",
				fire : function() {
					location.href = "<%=absoluteURL %>/surveys/<%=opinionId %>/collectors/create";
				}
			},
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {
					if(records.length > 0) {
						
						var list = [];
						$(records).each(function(index) {
							if(!(records[index].sourceTypeId == 2 && records[index].statusId == 1)) {
								list.push(records[index].collectorId);
							}
						});
						
						var modal = new lightFace({
							title : "Deleting collectors",
							message : "Are you sure you want to delete selected collector?",
							actions : [
					           { 
					        	   label : "Delete", 
					        	   fire : function() {
					        		
										deleteCollectors({
											accountId : accountId,
											list : list,
											success : function() {
												
												getCollectors({
													accountId : accountId,
													opinionId : opinionId,
													success : function(data) {
														
														collectors = data.list;
														renderTableCollectors();
														
													},
													error: function(error) {
														
														collectors = [];
														renderTableCollectors();
														
													}
												});
												
												modal.close();
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
								"<div style=\"padding: 0 0 12px 0\">Enter a new collector name.</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"text_rename_collector_name\" name=\"rename_collector_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
										"<div><label id=\"status_rename_collector_name\"></label></div>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#text_rename_collector_name');
							var B = M.find('#status_rename_collector_name');
							
							var modal = new lightFace({
								title : "Rename collector",
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
											
											changeCollectorName({
												accountId : accountId,
												name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
												collectorId : records[0].collectorId,
												success : function(data) {
													
													getCollectors({
														accountId : accountId,
														opinionId : opinionId,
														success : function(data) {
															
															collectors = data.list;
															renderTableCollectors();
															
														},
														error: function(error) {
															
															collectors = [];
															renderTableCollectors();
															
														}
													});
													
													// refresh
													modal.close(); 
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
					}
				]
			}
		]
	});
};

var updateCollectorStatus = function(params) {
	
	var obj = { 
		collectors : { 
			updateCollectorStatus : {
				accountId : params.accountId,
				collectorId : params.collectorId,
				statusId : params.statusId,
				closeMessage : params.closeMessage,
				isGenerateNewGuid : params.isGenerateNewGuid
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
			if(data.collectors.updateCollectorStatus != undefined) {
				if(data.collectors.updateCollectorStatus.error != undefined) {
					
					errorHandler({
						error : data.collectors.updateCollectorStatus.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.updateCollectorStatus);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.updateCollectorStatus);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var getCollectors = function(params) {
	
	var obj = { 
		collectors : { 
			getCollectors : {
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
			if(data.collectors.getCollectors != undefined) {
				if(data.collectors.getCollectors.error != undefined) {
					
					errorHandler({
						error : data.collectors.getCollectors.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.getCollectors);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.getCollectors);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var changeCollectorName = function(params) {
	
	var obj = {
		collectors : {
			changeCollectorName : {
				accountId : params.accountId,
				name : params.name,
				collectorId : params.collectorId
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
        	if(data.collectors.changeCollectorName.error != undefined) {
        		
        		errorHandler({
					error : data.collectors.changeCollectorName.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.collectors.changeCollectorName);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.collectors.changeCollectorName);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var deleteCollectors = function(params) {
	
	var obj = { 
   		collectors : { 
			deleteCollectors : {
				accountId : params.accountId,
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
        	if(data.collectors.deleteCollectors != undefined) {
				if(data.collectors.deleteCollectors.error != undefined) {
					
					errorHandler({
						error : data.collectors.deleteCollectors.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.deleteCollectors);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.deleteCollectors);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var getCollectorType = function(typeId) {
	var s = "";
	switch(typeId) {
		case 1 :
			s = "Direct link";
			break;
		case 2 : 
			s = "CINT panel";
			break;
	}
	return s;
};

var getCollectorStatus = function(statusId, typeId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = (typeId == 1 ? "Open" : "The panel is live"); 
			break;
		case 2 : 
			s = (typeId == 1 ? "Closed" : "Panel has been completed");
			break;
		case 3 :
			s = "Awaiting payment";
			break;
		case 4 : 
			s = "Panel is being verified";
			break;
		case 5 :
			s = "The panel is live"; // 1
			break;
		case 6 : 
			s = "Panel has been completed"; // 2
			break;
		case 7 : 
			s = "Pending"; // Hold -> Pending
			break;
		case 8 : 
			s = "Canceled";
			break;
	}
	return s;
};

var payCharge = function(params) {

	var obj = {
		payments : {
			payCharge : {
				accountId: params.accountId,
				chargeId : params.chargeId
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
			if(data.payments.payCharge != undefined) {
				if(data.payments.payCharge.error != undefined) {
					
					errorHandler({
						error : data.payments.payCharge.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.payments.payCharge);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.payments.payCharge);
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

$(function() {
	
	new surveyDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function() {
			
			getCollectors({
				accountId : accountId,
				opinionId : opinionId,
				success : function(data) {
					
					collectors = data.list;
					renderTableCollectors();
					
				},
				error: function(error) {
					
					collectors = [];
					renderTableCollectors();
					
				}
			});
			
			
		}
	});
	
});
</script>