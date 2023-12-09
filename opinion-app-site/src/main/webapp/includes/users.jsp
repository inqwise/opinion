
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>
<%@ page import="com.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="com.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.opinion.library.common.countries.ICountry" %>
<%@ page import="com.opinion.library.managers.CountriesManager" %>
<%@ page import="com.opinion.library.common.countries.ITimeZone" %>
<%@ page import="com.opinion.library.common.countries.IStateProvince" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1><a href="<%=absoluteURL %>/account" title="Account">Account</a>&nbsp;&rsaquo;&nbsp;Users</h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/account" title="Details"><span>Details</span></a></li>
						<li id="tab_billing_profile"><a href="<%=absoluteURL %>/billing-profile" title="Billing Profile"><span>Billing Profile</span></a></li>
						<li id="tab_transaction_history"><a href="<%=absoluteURL %>/transaction-history" title="Transaction History"><span>Transaction History</span></a></li>
						<li id="tab_invoices"><a href="<%=absoluteURL %>/invoices" title="Receipts &amp; Invoices"><span>Receipts &amp; Invoices</span></a></li>
						<li id="tab_upgrade"><a href="<%=absoluteURL %>/upgrade" title="Upgrade"><span>Upgrade</span></a></li>
						<li><a href="<%=absoluteURL %>/users" title="Users" class="selected"><span>Users</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			
			<div style="color: #333">If you manage this account with others, see who has access to sign in. <br/>Invite others to create their own login email and password to access this account by clicking "Invite other users".</div>
			<div style="padding-top: 20px; clear: both">
				<div>
					<a id="button_invite_other_users" class="button-green" title="Invite other users" href="#"><span><i class="icon-add-white">&nbsp;</i>Invite other users</span></a>
					<div id="form_invite_other_users" style="background: #eee; border: 1px solid #ccc; padding: 10px; display: none;">
						<h3>Invite others to access this account</h3>
						<div style="padding-top: 12px; color: #333">
							First, send invitations to people to access this Inqwise account. Next, your invitee accepts your invitation and creates a personal login to Inqwise. Finally, we''ll notify you when your invitee responds.<br/>
							If they accept, you must confirm and grant the invitee access to your account through the Access page.
						</div>
						<div style="padding-top: 20px;">
							<div class="params">
								<div class="param-name">* Email address</div>
								<div class="param-value">
									<div><input type="text" id="text_invite_user_email" name="invite_user_email" maxlength="254" autocomplete="off" /></div>
									<div><label id="status_invite_user_email"></label></div>
								</div>
							</div>
							<div class="params">
								<div class="param-name">Name (optional)</div>
								<div class="param-value">
									<input type="text" id="text_invite_user_name" />
								</div>
							</div>
							
							<!--
							<div class="params">
								<div class="param-name">* Access level</div>
								<div class="param-value">
									<div>
										<select id="select_invite_user_access_level" name="invite_user_access_level" autocomplete="off">
											<option value="">Choose an access level</option>
											<option value="1">Administrative access</option>
											<option value="2">Standard access</option>
											<option value="3">Read only access</option>
										</select>
									</div>
									<div><label id="status_invite_user_access_level"></label></div>
								</div>
							</div>
							-->
								
							<div style="clear: both; padding-top: 12px;">
								<div class="params">
									<div class="param-name"></div>
									<div class="param-value">
										<a id="button_send_invitation" href="#" class="button-blue" title="Send Invitation"><span>Send Invitation</span></a>
									</div>
									<div class="param-value" style="line-height: 18px;">
										<a id="button_cancel_invite_other_users" href="#" title="Cancel" style="margin-left: 6px;">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div id="section_pending_invitations" style="display: none; padding-top: 12px">
					<h3 class="ui-header-light">Pending invitations</h3>
					<div style="padding-top: 12px;">
						<div id="table_pending_invitations"></div>
					</div>
				</div>
			</div>
			<div style="padding-top: 20px; clear: both">
				<h3 class="ui-header-light">Users with account access</h3>
				<div style="padding-top: 12px;">
					<div id="table_users"></div>
				</div>
			</div>
			
		</div>
	</div>
</div>


<!--
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.7/angular.min.js"></script>
<script src="<%=applicationURL%>/scripts/angular/angular-route.js"></script>
<script src="<%=applicationURL%>/scripts/app.js"></script>
    
<div ng-app="app">
  	
   	<ul>
       	<li><a href="#edit">Edit</a></li>
       	<li><a href="#settings">Settings</a></li>
       	<li><a href="#reordering">Reordering</a></li>
    </ul>
   	
   	<div ng-view></div>
  	
</div>
-->


<link rel="stylesheet" href="<%=applicationURL%>/css/datatable/datatable.css" type="text/css" />

<script type="text/javascript" src="<%=applicationURL%>/scripts/datatable/datatable.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/menu_button.js"></script>
<script type="text/javascript">
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
				if(data.accounts.getAccountDetails.error != undefined) {
					
					errorHandler({
						error : data.accounts.getAccountDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.accounts.getAccountDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.accounts.getAccountDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getUsers = function(params) {

	var obj = {
		users : {
			getUsers : {
				accountId : params.accountId
			}
		}
	};

	$.ajax({
        /*url: servletUrl,*/
        url : apiUrl,
        data: { 
        	rq : JSON.stringify(obj),
        	timestamp : $.getTimestamp()  
        },
        /*dataType: "json",*/
        dataType: "jsonp",
		jsonp: "callback",
        success: function (data, status) {
			if(data.users.getUsers != undefined) {
				if(data.users.getUsers.error != undefined) {
					
					errorHandler({
						error : data.users.getUsers.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.users.getUsers);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.users.getUsers);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getInvites = function(params) {

	var obj = {
		invites : {
			getInvites : {
				accountId : params.accountId
			}
		}
	};

	$.ajax({
        /*url: servletUrl,*/
        url : apiUrl,
        data: { 
        	rq : JSON.stringify(obj),
        	timestamp : $.getTimestamp()  
        },
        /*dataType: "json",*/
        dataType: "jsonp",
		jsonp: "callback",
        success: function (data, status) {
			if(data.invites.getInvites != undefined) {
				if(data.invites.getInvites.error != undefined) {
					
					errorHandler({
						error : data.invites.getInvites.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.invites.getInvites);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.invites.getInvites);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var sendInvite = function(params) {

	var obj = {
		invites : {
			sendInvite : {
				accountId : params.accountId,
				email : params.email,
				name : params.name,
				inviteId : params.inviteId
			}
		}
	};

	$.ajax({
        /*url: servletUrl,*/
        url : apiUrl,
        data: { 
        	rq : JSON.stringify(obj),
        	timestamp : $.getTimestamp()  
        },
        /*dataType: "json",*/
        dataType: "jsonp",
		jsonp: "callback",
        success: function (data, status) {
			if(data.invites.sendInvite != undefined) {
				if(data.invites.sendInvite.error != undefined) {
					
					errorHandler({
						error : data.invites.sendInvite.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.invites.sendInvite);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.invites.sendInvite);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var deleteInvite = function(params) {

	var obj = {
		invites : {
			deleteInvite : {
				accountId : params.accountId,
				inviteId : params.inviteId
			}
		}
	};

	$.ajax({
        /*url: servletUrl,*/
        url : apiUrl,
        data: { 
        	rq : JSON.stringify(obj),
        	timestamp : $.getTimestamp()  
        },
        /*dataType: "json",*/
        dataType: "jsonp",
		jsonp: "callback",
        success: function (data, status) {
			if(data.invites.deleteInvite != undefined) {
				if(data.invites.deleteInvite.error != undefined) {
					
					errorHandler({
						error : data.invites.deleteInvite.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.invites.deleteInvite);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.invites.deleteInvite);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};


var getAccessLevel = function(level) {
	var s = "";
	switch(level) {
		case 1 : 
			s = "Administrative access";
			break;
		case 2 : 
			s = "Standard access";
			break;
		case 3 : 
			s = "Read-only access";
			break;
	}
	return s;
};


var pendingInvitations = [];
var tablePendingInvitations = null;
var renderTablePendingInvitations = function() {
	
	$('#table_pending_invitations').empty();
	tablePendingInvitations = $('#table_pending_invitations').dataTable({
		tableColumns : [
			{ key : "email", label : "Invited user", sortable: true, formatter : function(cell, value, record) {
				return (record.name != undefined ? record.name + " <span style=\"color: #999\">" + record.email + "</span>" : record.email);
			} },
			{ key : "inviteDate", label : "Invited on", sortable : true, formatter : function(cell, value, record) {
				return record.inviteDate + " <span style=\"color: #999\"></span>";
				// var date1 = new Date()
				// var date2 = new Date(record.inviteDate) 
				// ? "Expired" : "")
			} },
			/*
			{ key : "accessLevel", label : "Access Level", help : "<b>Your access level determines what you can see or do in the account</b><br/><br/><b>Difference between levels:</b><ul class=\"ls\"><li>Administrative access - Manage all aspects of the account and give people access.</li><li>Standard access - Make changes to surveys.</li><li>Read-only access - View surveys and run reports.</li></ul>", sortable : true, formatter : function(cell, value, record) {
				return getAccessLevel(record.accessLevel);
			} },
			*/
			{ key : "actions", label : "Actions", formatter : function(cell, value, record) {
				
				var action = $("<div data-value=\"" + record.inviteId + "\"></div>");
				action.menuButton({
					label : "More actions",
					disabled : false,
					actions : [
					    {
					    	label : "Delete invitation",
					    	value : 1,
							click : function(button) {
								
								deleteInvite({
									accountId : accountId,
									inviteId : button.attr('data-value'),
									success : function(data) {
										
										getInvites({
											accountId : accountId,
											success : function(data) {

												if(data.list.length != 0) {
							
													$('#section_pending_invitations').show();
							
													pendingInvitations = data.list;
													renderTablePendingInvitations();
							
												} else {
													$('#section_pending_invitations').hide();
												}
						
											},
											error: function(error) {
												console.log(error);
											}
										});
										
									},
									error: function(error) {
										console.log(JSON.stringify(error));
									}
								});
								
							}
					    },
					    {
					    	label : "Resend invitation",
					    	value : 2,
					    	active : true,
							click : function(button) {
								
								sendInvite({
									accountId : accountId,
									inviteId : button.attr('data-value'),
									success : function(data) {
										
										getInvites({
											accountId : accountId,
											success : function(data) {

												if(data.list.length != 0) {
							
													$('#section_pending_invitations').show();
							
													pendingInvitations = data.list;
													renderTablePendingInvitations();
							
												} else {
													$('#section_pending_invitations').hide();
												}
						
											},
											error: function(error) {
												console.log(error);
											}
										});
										
									},
									error: function(error) {
										console.log(JSON.stringify(error));
									}
								});
								
							}
					    }
					]
		        });
				
				return action;
				
			} }
		],
		dataSource : pendingInvitations, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};

var users = [];
var tableUsers = null;
var renderTableUsers = function() {
	
	$('#table_users').empty();
	tableUsers = $('#table_users').dataTable({
		tableColumns : [
			{ key : "userName", label : "User", sortable: true },
			{ key : "lastLogginDate", label : "Last loggin date", sortable : true }
			/*
			{ key : "accessLevel", label : "Access Level", help : "<b>Your access level determines what you can see or do in the account</b><br/><br/><b>Difference between levels:</b><ul class=\"ls\"><li>Administrative access - Manage all aspects of the account and give people access.</li><li>Standard access - Make changes to surveys.</li><li>Read-only access - View surveys and run reports.</li></ul>", sortable : true, formatter : function(cell, value, record) {
				return getAccessLevel(record.accessLevel);
			} },
			*/
			/*
			{ key : "actions", label : "Actions" }
			*/
		],
		dataSource : users, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};

$(function() {
	
	getAccountDetails({
		accountId : accountId,
		success : function(data) {
		
			// owner access only
			if(data.isOwner) {
				
				$("#tab_billing_profile, #tab_transaction_history").show();
				
				// invitations
				getInvites({
					accountId : accountId,
					success : function(data) {
						if(data.list.length != 0) {
							
							$('#section_pending_invitations').show();
							
							pendingInvitations = data.list;
							renderTablePendingInvitations();
							
						} else {
							$('#section_pending_invitations').hide();
						}
					},
					error: function(error) {
						console.log(error);
					}
				});
				
				// users
				getUsers({
					accountId : accountId,
					success : function(data) {
						users = data.list;
						renderTableUsers();
					},
					error: function(error) {
						console.log(error);
					}
				});
				
				$("#button_invite_other_users").click(function(e) {
					e.preventDefault();
					$(this).hide();
					$("#form_invite_other_users").show();
					
					var v = null;
					v = new validator({
						elements : [
						    {
								element : $('#text_invite_user_email'),
								status : $('#status_invite_user_email'),
								rules : [
									{ method : 'required', message : 'This field is required.' },
									{ method : 'email' }
								]
							}
							/*
							,
							{
								element : $('#select_invite_user_access_level'),
								status : $('#status_invite_user_access_level'),
								rules : [
									{ method : 'required', message : 'This field is required.' }
								] 
							}
							*/
						],
						submitElement : $('#button_send_invitation'),
						messages : null,
						accept : function () {
							
							sendInvite({
								accountId : accountId,
								email : $.trim($('#text_invite_user_email').val()),
								name : $.trim($('#text_invite_user_name').val()),
								success : function(data) {
									
									getInvites({
										accountId : accountId,
										success : function(data) {
											if(data.list.length != 0) {
							
												$('#section_pending_invitations').show();
							
												pendingInvitations = data.list;
												renderTablePendingInvitations();
							
											} else {
												$('#section_pending_invitations').hide();
											}
										},
										error: function(error) {
											console.log(error);
										}
									});
									
								},
								error: function(error) {
									
									var modal = new lightFace({
										title : "You have reached the maximum number of allowed users.",
										message : "You have reached the maximum number of allowed users.",
										actions : [
											{ 
												label : "OK", 
												fire : function() { 
													modal.close(); 
												}, 
												color : "blue" 
											}
										],
										overlayAll : true
									});
									
									// You have reached the maximum number of users allowed under your account.
									// To allow more users please upgdare your account to
									console.log(JSON.stringify(error));
									
									
								}
							});
							
						},
						error: function() {
							
						}
					});
					
					$("#text_invite_user_email").focus();
					
				});
				
				$("#button_cancel_invite_other_users").click(function(e) {
					e.preventDefault();
					
					$("#form_invite_other_users").hide();
					$("#button_invite_other_users").show();
					
				});
				
				if(auth.userInfo.plan != undefined) {
					
					if(auth.userInfo.permissions.makePayment) {
						$('#tab_upgrade').show();
					}
					
					if(auth.userInfo.plan.isFree) {
						
					}
				}
				
			} else {
				
				
			}
			
		}
	});
	
});
</script>