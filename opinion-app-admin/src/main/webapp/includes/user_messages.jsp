
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String userId = request.getParameter("user_id");
%>

<div>
	<h1><a href="<%=absoluteURL %>/users" title="Users">Users</a>&nbsp;&rsaquo;&nbsp;<span id="header_user_name"></span></h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL%>/user-details?user_id=<%=userId%>" title="User Details"><span>User Details</span></a></li>
						<li><a href="<%=absoluteURL%>/user-messages?user_id=<%=userId%>" title="Messages" class="selected"><span>Messages</span></a></li>
						<li><a href="<%=absoluteURL%>/user-accounts?user_id=<%=userId%>" title="Related Accounts"><span>Related Accounts</span></a></li>
						<li><a href="<%=absoluteURL%>/user-history?user_id=<%=userId%>" title="History"><span>History</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			<div id="table_messages"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
var getUserDetails = function(params) {

	var obj = {
		users : {
			getUserDetails : {
				userId : params.userId
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
			if(data.users.getUserDetails != undefined) {
				if(data.users.getUserDetails.error != undefined) {
					
					errorHandler({
						error : data.users.getUserDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.users.getUserDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.users.getUserDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};


/* jsapi */
var jsapi = {
	messages : {
		getList : function(params) {
			
			var obj = {
				messages : {
					getList : {
						userId : params.userId
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
		        	if(data.messages.getList != undefined) {
			        	if(data.messages.getList.error != undefined) {
			        		errorHandler({
								error : data.messages.getList.error	
							});
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.messages.getList);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.messages.getList);
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
		},
		create : function(params) {
			
			var obj = {
				messages : {
					create : {
						userId : params.userId,
						name : params.name,
						content : params.content
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
		        	if(data.messages.create != undefined) {
			        	if(data.messages.create.error != undefined) {
			        		errorHandler({
								error : data.messages.create.error	
							});
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.messages.create);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.messages.create);
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
		},
		update : function(params) {
			
			var obj = {
				messages : {
					update : {
						userId : params.userId,
						id : params.id
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
		        	if(data.messages.update != undefined) {
			        	if(data.messages.update.error != undefined) {
			        		errorHandler({
								error : data.messages.update.error	
							});
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.messages.update);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.messages.update);
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
		},
		publish : function(params) {
			
			var obj = {
				messages : {
					publish : {
						userId : params.userId,
						id : params.id,
						publishDate : params.publishDate
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
		        	if(data.messages.publish != undefined) {
			        	if(data.messages.publish.error != undefined) {
			        		errorHandler({
								error : data.messages.publish.error	
							});
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.messages.publish);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.messages.publish);
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
		},
		deleteList : function(params) {
			
			var obj = {
				messages : {
					deleteList : {
						userId : params.userId,
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
		        	if(data.messages.deleteList != undefined) {
			        	if(data.messages.deleteList.error != undefined) {
			        		errorHandler({
								error : data.messages.deleteList.error	
							});
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.messages.deleteList);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.messages.deleteList);
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
		}
	}
};






var tableMessages = null;
var messages = [];
var renderTableMessages = function() {
	
	$('#table_messages').empty();
	tableMessages = $('#table_messages').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'name', label : 'Subject', sortable : true },
			{ key : 'content', label : 'Message', sortable : true },
			{ key : 'closeDate', label : 'Close Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.closeDate) {
					var left = record.closeDate.substring(record.closeDate.lastIndexOf(" "), " ");
					var right = record.closeDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
			{ key : 'publishDate', label : 'Publish Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.publishDate) {
					var left = record.publishDate.substring(record.publishDate.lastIndexOf(" "), " ");
					var right = record.publishDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
            { key : 'excludeDate', label : 'Exclude Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.excludeDate) {
					var left = record.excludeDate.substring(record.excludeDate.lastIndexOf(" "), " ");
					var right = record.excludeDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
			{ key : 'modifyDate', label : 'Modify Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.modifyDate) {
					var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
					var right = record.modifyDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : messages, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{
				label : "New Message",
				color: "green",
				icon : "add-white",
				fire : function() {
					
					var v = null;
					var M = $("<div>" +
						"<div style=\"padding: 0 0 6px 0\">Subject:</div>" +
						"<div class=\"row\">" +
							"<div class=\"cell\">" +
								"<div><input type=\"text\" id=\"text_message_subject\" name=\"text_message_subject\" autocomplete=\"off\" style=\"width: 225px\" /></div>" +
								"<div><label id=\"status_message_subject\"></label></div>" +
							"</div>" +
						"</div>" +
						"<div style=\"padding: 0 0 6px 0; clear: both\">Message:</div>" +
						"<div class=\"row\" style=\"height: 72px;\">" +
							"<div class=\"cell\">" +
								"<div><textarea id=\"textarea_message\" name=\"textarea_message\" placeholder=\"Write a message...\" autocomplete=\"off\" style=\"width: 314px; height: 64px;\"></textarea></div>" +
								"<div><label id=\"status_message\"></label></div>" +
							"</div>" +
						"</div>" +
					"</div>");
					

					
					var E = M.find('#text_message_subject');
					var R = M.find('#status_message_subject');
					
					var I = M.find('#textarea_message');
					var B = M.find('#status_message');

					
					var modal = new lightFace({
						title : "New Message",
						message : M,
						actions : [
							{ 
						    	label : "Post", 
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
						overlayAll: true,
						complete : function() {
							
							E.focus();
							
							v = new validator({
								elements : [
									{
										element : E,
										status : R,
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									},
									{
										element : I,
										status : B,
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : null,
								messages : null,
								accept : function () {
									
									
									jsapi.messages.create({
										userId : userId,
										name : E.val(),
										content : I.val(),
										success : function(data) {
											if(data.id) {
												
												jsapi.messages.getList({
													userId : userId,
													success : function(data) {
														messages = data.list;
														renderTableMessages();
													},
													error : function() {
														messages = [];
														renderTableMessages();
													}
												});
												
												modal.close();
												
											}
										},
										error: function(error) {
											alert(JSON.stringify(error));
										}
									});
									
								},
								error : function() {
								
								}
							});
							
						}
					});
					
				}
			},
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {

					if(records.length > 0) {
						
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].id);
						});
						
						var modal = new lightFace({
							title : "Deleting messages",
							message : "Are you sure you want to delete selected messages?",
							actions : [
								{ 
									label : "Delete", 
									fire : function() {
										
										jsapi.messages.deleteList({
											userId : userId,
											list : list,
											success : function() {
												
												jsapi.messages.getList({
													userId : userId,
													success : function(data) {
														messages = data.list;
														renderTableMessages();
													},
													error : function() {
														messages = [];
														renderTableMessages();
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
				label : "Publish",
				disabled : true,
				condition : 1,
				fire : function(records, source) {
						
					// alert(records[0].id);
					jsapi.messages.publish({
						userId : userId,
						id : records[0].id,
						success : function(data) {
							
							jsapi.messages.getList({
								userId : userId,
								success : function(data) {
									messages = data.list;
									renderTableMessages();
								},
								error : function() {
									messages = [];
									renderTableMessages();
								}
							});
							
						},
						error: function(error) {
							alert(JSON.stringify(error));
						}
					});
					
					
				}
			}
		]
	});
	
};

var userId = <%= userId %>;

$(function() {

	getUserDetails({
		userId : userId,
		success : function(data) {

			$('#level1').text(data.email);
			$('#header_user_name').text(data.userName);
			
			jsapi.messages.getList({
				userId : userId,
				success : function(data) {
					messages = data.list;
					renderTableMessages();
				},
				error : function() {
					messages = [];
					renderTableMessages();
				}
			});
			
		}
	});
	
});
</script>
