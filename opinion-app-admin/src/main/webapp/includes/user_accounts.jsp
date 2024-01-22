
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
						<li><a href="<%=absoluteURL%>/user-messages?user_id=<%=userId%>" title="Messages"><span>Messages</span></a></li>
						<li><a href="<%=absoluteURL%>/user-accounts?user_id=<%=userId%>" title="Related Accounts" class="selected"><span>Related Accounts</span></a></li>
						<li><a href="<%=absoluteURL%>/user-history?user_id=<%=userId%>" title="History"><span>History</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			
			<div style="clear: both;">
				<div style="float: left; width: 50%">
					<div style="margin-right: 5px;">
						<h3 class="ui-header-light">Owned by User</h3>
						
						<div id="placeholder_new_account" style="display: none; background: #FFF9D7; border: 1px solid #E2C822; padding: 10px; margin: 0 0 10px">
							<h3>New Account</h3>
							<div style="padding-top: 12px;">
								<div>
									<div class="params">
										<div class="param-name">* Account Name:</div>
										<div class="param-value">
											<div><input type="text" id="text_new_account_name" name="new_account_name" maxlength="32" size="30" autocomplete="off" placeholder="Enter account name" /></div>
											<div><label id="status_new_account_name"></label></div>
										</div>
									</div>
								</div>
								<div style="clear: both; padding-top: 12px;">
									<div class="params">
										<div class="param-name"></div>
										<div class="param-value">
											<a href="javascript:;" title="Submit" class="button-blue" id="button_new_account_create"><span>Create</span></a>
										</div>
										<div class="param-value" style="line-height: 18px;">
											<a href="javascript:;" title="Cancel" id="button_new_account_cancel" style="margin-left: 6px;">Cancel</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div style="padding-top: 12px">
							<div id="table_accounts"></div>
						</div>
					</div>
				</div>
				<div style="float: left; width: 50%">
					<div style="margin-left: 5px;">
						<h3 class="ui-header-light">Attached / Shared Accounts</h3>
						<div style="padding-top: 12px">
							<div id="table_attached_accounts"></div>
						</div>
					</div>
				</div>
			</div>
			
			
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

var getAccounts = function(params) {

	var obj = {
		accounts : {
			getAccounts : {
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
        	if(data.accounts.getAccounts.error != undefined) {
				
        		errorHandler({
					error : data.accounts.getAccounts.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.getAccounts);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.getAccounts);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var createAccount = function(params) {

	var obj = {
		users : {
			createAccount : {
				productId : params.productId,
				userId : params.userId,
				accountName : params.accountName
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
        	if(data.users.createAccount.error != undefined) {
				
        		errorHandler({
					error : data.users.createAccount.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.users.createAccount);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.users.createAccount);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var detachAccount = function(params) {

	var obj = {
		users : {
			detachAccount : {
				userId : params.userId,
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
        	if(data.users.detachAccount.error != undefined) {
				
        		errorHandler({
					error : data.users.detachAccount.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.users.detachAccount);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.users.detachAccount);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var attachAccount = function(params) {

	var obj = {
		users : {
			attachAccount : {
				userId : params.userId,
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
        	if(data.users.attachAccount.error != undefined) {
				
        		errorHandler({
					error : data.users.attachAccount.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.users.attachAccount);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.users.attachAccount);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
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

var tableAccounts = null;
var accounts = [];
var renderTableAccounts = function() {
	
	$('#table_accounts').empty();
	tableAccounts = $('#table_accounts').dataTable({
		tableColumns : [
			{ key : 'accountId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'accountName', label : 'Account', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-details?account_id=" + record.accountId  + "\" title=\"" + record.accountName + "\">" + record.accountName + "</a>");
			}},
			{ key : 'servicePackageName', label : 'Plan', sortable: true, width: 120 },
			{ key : 'isActive', label : 'Status', sortable: true, width: 80, formatter: function(cell, value, record, source) {
            	return (record.isActive ? "Enabled" : "Disabled"); // Expired / Suspended
	        }},
			{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.insertDate) {
					var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
					var right = record.insertDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : accounts, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		actions : [		           
		    {
		    	 label : "New Account",
		    	 color: "green",
		    	 icon : "add-white",
		    	 fire : function() {
		    		 
		    		 $('#placeholder_new_account').show();
		    		 
		    		// clear prev values
					$('#text_new_account_name')
		    		.val("")
		    		.focus();
		    		
					var v = null;
					v = new validator({
						elements : [
							{
								element : $('#text_new_account_name'),
								status : $('#status_new_account_name'),
								rules : [
									{ method : 'required', message : 'This field is required.' }
								]
							}
						],
						submitElement : $('#button_new_account_create'),
						messages : null,
						accept : function () {
							
							createAccount({
								productId : 1,
								userId : <%= userId %>,
								accountName : $('#text_new_account_name').val(),
								success : function(data) {
									
									$('#placeholder_new_account').hide();
									
									getAccounts({
										userId : <%= userId %>,
										success : function(data) {
											
											accounts = [];
											
											for(var i = 0; i < data.list.length; i++) {
												if(data.list[i].ownerId == <%= userId %>) {
													accounts.push(data.list[i]);
												}
											}
											
											renderTableAccounts();
											
										},
										error : function() {
											
											accounts = [];
											renderTableAccounts();
											
										}
									});
									
								},
								error: function() {
									
								}
							});
							
						}
					});
					
					
					
					// cancel
					$('#button_new_account_cancel')
					.unbind('click')
					.click(function() {
						$('#placeholder_new_account').hide();
					});
		    		 
		    	 }
		    }
		]
	});
	
};


var tableAttachedAccounts = null;
var attachedAccounts = [];
var renderTableAttachedAccounts = function() {
	
	$('#table_attached_accounts').empty();
	tableAccounts = $('#table_attached_accounts').dataTable({
		tableColumns : [
			{ key : 'accountId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'accountName', label : 'Account', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-details?account_id=" + record.accountId  + "\" title=\"" + record.accountName + "\">" + record.accountName + "</a>");
			}},
			{ key : 'servicePackageName', label : 'Plan', sortable: true, width: 120 },
			{ key : 'isActive', label : 'Status', sortable: true, width: 80, formatter: function(cell, value, record, source) {
            	return (record.isActive ? "Enabled" : "Disabled"); // Expired / Suspended
	        }},
			{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.insertDate) {
					var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
					var right = record.insertDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : attachedAccounts, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{
				 label : "Attach Account",
				 pop : function() {
			
					 var M = $("<div class=\"column-params\">" +
								"<div class=\"column-param-name\">Account #: </div>" +
								"<div class=\"column-param-value\"><input type=\"text\" id=\"text_attach_account_id\" /></div>" +
								"<div class=\"column-param-name\"><a href\"#\" id=\"button_attach\" class=\"button-blue\" title=\"Attach\"><span>Attach</span></a></div>" +
							"</div>");
					 
					 // actions
					 M.find("#button_attach").click(function(e) {
						
						attachAccount({
							userId : <%= userId %>,
							accountId : parseInt(M.find("#text_attach_account_id").val()),
							success : function(data) {
								
								getAccounts({
									userId : <%= userId %>,
									success : function(data) {
										
										attachedAccounts = [];
										
										for(var i = 0; i < data.list.length; i++) {
											if(data.list[i].ownerId != <%= userId %>) {
												attachedAccounts.push(data.list[i]);
											}
										}
										
										renderTableAttachedAccounts();
										
									},
									error : function() {
										
										attachedAccounts = [];
										renderTableAttachedAccounts();
										
									}
								});
								
							},
							error: function(error) {
								
							}
						});
						
						 
						e.preventDefault(); 
					 });
					
					 // text_attach_account_id
					 //console.log("get account....");
					 
					 
					 /*
					 getAccounts({
						success : function(data) {
							
							
							console.log("accounts -> " + data.list.length);
							
							var e = M.find("#select_accounts")[0].options;
					        for (var i = 0; i < data.list.length; i++) {
					            var k = new Option(data.list[i].accountId, data.list[i].accountId);
					            try {
					                e.add(k);
					            } catch (ex) {
					                e.add(k, null);
					            }
					        }
					        
							//accounts = data.list;
							//renderTableAccounts();
						},
						error : function() {
							//accounts = [];
							//renderTableAccounts();
						}
					});
					*/
					
					
					return M;
					 
				 }
			},
		    {
		    	 label : "Detach Account",
		    	 disabled : true,
				 condition : 1, /*(accounts.length > 1 ? 1 : -1),*/
				 fire : function(records, source) {

					var modal = new lightFace({
						title : "Detach account",
						message : "Are you sure you want to detach selected account?",
						actions : [
							{ 
								label : "Detach", 
								fire : function() {
									
									detachAccount({
										userId : <%= userId %>,
										accountId : records[0].accountId,
										success : function(data) {
											
											getAccounts({
												userId : <%= userId %>,
												success : function(data) {
													
													attachedAccounts = [];
													
													for(var i = 0; i < data.list.length; i++) {
														if(data.list[i].ownerId != <%= userId %>) {
															attachedAccounts.push(data.list[i]);
														}
													}
													
													renderTableAttachedAccounts();
													
												},
												error : function() {
													
													attachedAccounts = [];
													renderTableAttachedAccounts();
													
												}
											});
											
											modal.close();
											
											
										},
										error: function(error) {
											
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
		]
	});
	
};



$(function() {

	getUserDetails({
		userId : <%= userId %>,
		success : function(data) {

			$('#level1').text(data.email);
			$('#header_user_name').text(data.userName);

			getAccounts({
				userId : data.userId,
				success : function(data) {
					
					for(var i = 0; i < data.list.length; i++) {
						
						if(data.list[i].ownerId == <%= userId %>) {
							accounts.push(data.list[i]);
						} else {
							attachedAccounts.push(data.list[i]);
						}
						
					}
					
					renderTableAccounts();
					renderTableAttachedAccounts();
					
				},
				error : function() {
					
					accounts = [];
					attachedAccounts = [];
					
					renderTableAccounts();
					renderTableAttachedAccounts();
					
				}
			});
			
		}
	});
	
});
</script>



