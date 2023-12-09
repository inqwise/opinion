
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%@page import="com.opinion.library.common.errorHandle.OperationResult"%>
<%@page import="com.opinion.library.managers.CountriesManager"%>
<%@page import="com.opinion.library.common.countries.ITimeZone"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String accountId = request.getParameter("account_id");
%>

<div>
	<h1 style="padding-bottom: 0 !important;"><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<span id="header_account_name"></span></h1>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln" id="list_account_details"></ul>
	</div>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" class="selected" title="Account Details"><span>Account Details</span></a></li>
					<li><a href="<%=absoluteURL %>/account-users?account_id=<%=accountId %>" title="Users"><span>Users</span></a></li>
					<li><a href="<%=absoluteURL %>/account-surveys?account_id=<%=accountId %>" title="Surveys"><span>Surveys</span></a></li>
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
		
		<div>
		
			<div class="params">
				<div class="param-name">Account Id:</div>
				<div class="param-value">
					<b id="label_account_id" style="color: #000"></b>
				</div>
			</div>
			<div class="params">
				<div class="param-name">* Account Name:</div>
				<div class="param-value">
					<div><input type="text" id="text_account_name" name="account_name" size="30" maxlength="32" /></div>
					<div><label id="status_account_name"></label></div>
				</div>
			</div>
			
			
			
			<div class="params">
				<div class="param-name">Owner Id:</div>
				<div class="param-value">
					<b id="label_owner_id" style="color: #000"></b>
				</div>
			</div>
			
			<!--
			<div class="params">
				<div class="param-name">Owner Username:</div>
				<div class="param-value">
					<a href="" id="label_owner_username"></a>
				</div>
			</div>
			-->
			
			<div class="params">
				<div class="param-name">Owned by:</div>
				<div class="param-value">
					<select id="select_owner" autocomplete="off"></select><a href="#" title="View owner details" id="link_to_owner" style="margin-left: 6px;">View owner details</a>
				</div>
			</div>
			
			<div class="params">
				<div class="param-name">Current Plan:</div>
				<div class="param-value">
					<div id="section_plan">
						<b id="label_package" style="color: #000;"></b><a href="#" title="Edit" id="link_plan_edit" style="margin-left: 6px;">Edit</a>
					</div>
					<div id="section_plan_edit" style="background: #FFF9D7; border: 1px solid #E2C822; padding: 10px; margin: 0px 0 10px; display: none;">
						<div class="params">
							<div class="param-value">
								<select id="select_package" name="package" autocomplete="off"></select>
							</div>
						</div>
						<div style="clear: both; padding-top: 12px;">
							<div class="params">
								<div class="param-value">
									<a href="#" title="Save" class="button-blue" id="button_plan_save"><span>Save</span></a>
								</div>
								<div class="param-value" style="line-height: 18px;">
									<a href="#" title="Cancel" id="button_plan_cancel" style="margin-left: 6px;">Cancel</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="params" id="section_plan_expiration_date" style="display: none; overflow: hidden">
				<div class="param-name">Plan Expiration Date:</div>
				<div class="param-value">
					<div id="section_plan_expiration_date_view">
						<div><b class="label_plan_expiration_date" style="color: #000"></b><a href="#" id="link_plan_expiration_date_edit" title="Edit" style="margin-left: 6px;">Edit</a></div>
						<!-- <div style="padding-bottom: 10px;"><span>You will be downgraded to the free Basic plan when your PRO plan ends on <span class="label_plan_expiration_date"></span></span></div> -->
					</div>
					<div id="section_plan_expiration_date_edit" style="background: #FFF9D7; border: 1px solid #E2C822; padding: 10px; margin: 0px 0 10px; display: none;">
						<div>
							<input type="text" id="datepicker_plan_expiration_date" maxlength="12" style="width: 82px;" autocomplete="off" />
						</div>
						<div style="clear: both; padding-top: 12px;">
							<div class="params">
								<div class="param-value">
									<a href="#" title="Save" class="button-blue" id="button_plan_expiration_date_save"><span>Save</span></a>
								</div>
								<div class="param-value" style="line-height: 18px;">
									<a href="#" title="Cancel" id="button_plan_expiration_date_cancel" style="margin-left: 6px;">Cancel</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="params" id="section_remaining_responses" style="display: none; overflow: hidden;">
				<div class="param-name">Remaining Responses:</div>
				<div class="param-value">
					<div id="section_remaining_responses_view">
						<div><b id="label_remaining_responses" style="color: #000"></b><a href="#" id="link_remaining_responses_edit" title="Edit" style="margin-left: 6px;">Edit</a></div>
					</div>
					<div id="section_remaining_responses_edit" style="background: #FFF9D7; border: 1px solid #E2C822; padding: 10px; margin: 0px 0 10px; display: none;">
						<div class="params">
							<div class="param-value">
								<select id="select_decrease_remaining_responses">
									<option value="0">Increase by</option>
									<option value="1">Decrease by</option>
								</select>
							</div>
							<div class="param-value" style="margin-left: 6px;">
								<div><input type="text" id="text_remaining_reponses" maxlength="12" name="remaining_reponses" style="width: 54px;text-align: right" autocomplete="off" /></div>
								<div><label id="status_remaining_reponses"></label></div>
							</div>
						</div>
						<div style="clear: both; padding-top: 12px;">
							<div class="params">
								<div class="param-value">
									<a href="javascript:;" title="Save" class="button-blue" id="button_remaining_responses_save"><span>Save</span></a>
								</div>
								<div class="param-value" style="line-height: 18px;">
									<a href="#" title="Cancel" id="button_remaining_responses_cancel" style="margin-left: 6px;">Cancel</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="params">
				<div class="param-name">Permissions:</div>
				<div class="param-value">
					<div>
						<div style="width: 166px;height:120px;overflow-x: hidden;overflow-y: auto;border-color: #666666 #CCCCCC #CCCCCC; border-style: solid; border-width: 1px; background: #fff">
							<ul id="list_permissions"></ul>
						</div>
						<div style="padding: 10px 0 10px 0;" class="ui-form">
							<label><span><input type="checkbox" id="checkbox_inherit_permissions" autocomplete="off" /></span>Inherit from parent</label>
						</div>
					</div>
				</div>
			</div>
			
			<div class="params">
				<div class="param-name">* Timezone:</div>
				<div class="param-value">
					<div>
						<select id="select_timezone" name="timezone" autocomplete="off">
							<option value=""></option>
							<%
							OperationResult<List<ITimeZone>> timezonesResult = CountriesManager.getTimeZones();
								if(!timezonesResult.hasError()){
									List<ITimeZone> timezones = timezonesResult.getValue();
									for(ITimeZone timezone : timezones){
										%>
										<option value="<%=timezone.getId() %>"><%=timezone.getName() %></option>
										<%
									}
								}
							%>
						</select>
					</div>
					<div><label id="status_timezone"></label></div>
				</div>
			</div>
			<div class="params">
				<div class="param-name">Create Date:</div>
				<div class="param-value">
					<b id="label_create_date" style="color: #000"></b>
				</div>
			</div>
			<div class="params">
				<div class="param-name">Status:</div>
				<div class="param-value">
					<div class="ui-form">
						<label><span><input type="checkbox" id="checkbox_is_active" autocomplete="off" /></span>Enabled</label>
					</div>
				</div>
			</div>
			
			
			<div style="height: 24px; overflow: hidden;"></div>
			<div class="params">
				<div class="param-name">&nbsp;</div>
				<div class="param-value">
					<a id="button_update" title="Update" class="button-blue" href="javascript:;"><span>Update</span></a>
				</div>
				<div class="param-value">
					<a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" title="Cancel" style="margin-left: 6px;">Cancel</a>
				</div>
			</div>
			
		</div>

	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/datepicker/datepicker.js"></script>
<script type="text/javascript">

var getUsers = function(params) {
	
	var obj = {
		users : {
			getUsers : {
				accountId : params.accountId,
				fromDate : params.fromDate,
				toDate : params.toDate
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
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var changeOwner = function(params) {

	var obj = {
		accounts : {
			changeOwner : {
				ownerId : params.ownerId,
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
        	if(data.accounts.changeOwner.error != undefined) {
				
        		errorHandler({
					error : data.accounts.changeOwner.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.changeOwner);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.changeOwner);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

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

var setAccountDetails = function(params) {

	var obj = {
		accounts : {
			setAccountDetails : {
				accountId : params.accountId,
				accountName : params.accountName,
				packageId : params.packageId,
				timezoneId : params.timezoneId,
				isActive : params.isActive
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
			if(data.accounts.setAccountDetails != undefined) {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.setAccountDetails);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
    
};

var getAccountSettings = function(params) {

	var obj = {
		accounts : {
			getAccountSettings : {
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
        	if(data.accounts.getAccountSettings.error != undefined) {
        		
        		errorHandler({
					error : data.accounts.getAccountSettings.error	
				});
        		
				if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.getAccountSettings);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.getAccountSettings);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var updateAccountSettings = function(params) {

	var obj = {
		accounts : {
			updateAccountSettings : {
				accountId : params.accountId,
				accountName : params.accountName,
				packageId : params.packageId,
				timezoneId : params.timezoneId,
				permissions : params.permissions,
				isActive : params.isActive,
				packageId : params.packageId,
				creditResponses : params.creditResponses,
				accountUsers : params.accountUsers,
				planExpirationDate : params.planExpirationDate // new
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
        	if(data.accounts.updateAccountSettings.error != undefined) {
        		
        		errorHandler({
					error : data.accounts.updateAccountSettings.error	
				});
        		
				if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.updateAccountSettings);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.updateAccountSettings);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};


var changeSessionsBalance = function(params) {
	
	var obj = {
		accounts : {
			changeSessionsBalance : {
				accountId : params.accountId,
				packageId : params.packageId,
				amount : params.amount
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
        	if(data.accounts.changeSessionsBalance.error != undefined) {
				if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.changeSessionsBalance);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.changeSessionsBalance);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var updateServicePackage = function(params) {

	var obj = {
		accounts : {
			updateServicePackage : {
				accountId : params.accountId,
				packageId : params.packageId,
				expiryDate : params.expiryDate,
				calculateExpiryDate : params.calculateExpiryDate
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
        	if(data.accounts.updateServicePackage.error != undefined) {
				if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.updateServicePackage);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.updateServicePackage);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
    
};

var getPackages = function(params) {

	var obj = {
		products : {
			getPackages : {
				productId : params.productId
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
			if(data.products.getPackages != undefined) {
				if(data.products.getPackages.error != undefined) {
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.products.getPackages);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.products.getPackages);
					}
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

var accountId = <%= accountId %>;
var servicePackageId = null;




// permissions
var isPermissionChanged = false;
var permissionsToReset = [];
var resetProperty = function(property) {
	permissionsToReset.push(property);
};

var permissions = [];
var findInPermissions = function(k) {
	var u = false;
	for(var i = 0; i < permissions.length; i++) {
		if(permissions[i].key == k.key) {
			u = true;
			break;
		}
	}
	return u;
};
var addProperty = function(property) {
	if(permissions.length == 0) {
		permissions.push(property);
	} else {
		for(var i = 0; i < permissions.length; i++) {
			if(findInPermissions(property)) {
				if(permissions[i].key == property.key) {
					permissions[i] = property;
				}
			} else {
				permissions.push(property);
			}
		}
	}
};
// permissions



var packages = [];
var account = {};

var init = function() {
	
};



$(function() {

	getAccountSettings({
		accountId : accountId,
		success : function(data) {

			account = data;
			
			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			/*
			$('#level1').text(data.ownerUserName);
			$('#header_account_name').text(data.ownerUserName);
			*/
			
			// details
			// TODO: change flag isActive to statusId
			$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getAccountStatus(data.isActive ? 1 : 2) + "</li>").appendTo('#list_account_details');
			
		
			
			
			// settings
			
			
			$('#label_account_id').text(data.accountId);
			$('#text_account_name').val(data.accountName);
			
			
			
			
			
			
			
			
			
			
			$('#label_owner_id').text(data.ownerId);
			$('#link_to_owner').attr({ "href" : "<%=absoluteURL%>/user-details?user_id=" + data.ownerId });
			
			// owned by
			getUsers({
				accountId : accountId,
				success : function(users) {
					
					var selectedIndex = 0;
					var p = $('#select_owner').empty();
					var q = p[0].options;
			        for (var i = 0; i < users.list.length; i++) {
			        	
			        	var k = new Option(users.list[i].userName, users.list[i].userId);
			            try {
			            	q.add(k);
			            } catch (ex) {
			            	q.add(k, null);
			            }
			            
			            if(users.list[i].userId == data.ownerId) {
			            	selectedIndex = i;
			            }
			            
			        }
			        
			        p[0].selectedIndex = selectedIndex;
			        
			        
			        // change event
			        p.change(function() {
			        	
			        	var modal = new lightFace({
							title : "Change Owner",
							message : "Are you sure you want to change the account owner to " + $("#select_owner option:selected").text() + "?",
							actions : [
								{ 
									label : "Yes, change it!", 
									fire : function() {
										
										changeOwner({
											ownerId : parseInt($("#select_owner").val()),
											accountId : accountId,
											success : function(data) {
												
												//console.log(JSON.stringify(data));
												// demo
												location.href = "account-details?account_id=" + accountId;
												
											}, 
											error: function(error) {
												console.log("ERRR -> CHANGE OWNER " + JSON.stringify(error));
											}
										});
										
										
										modal.close(); 
									}, 
									color : "blue" 
								},
								{ 
									label : "Cancel", 
									fire : function() { 
										
										p[0].selectedIndex = selectedIndex;
										
										modal.close(); 
									},
									color : "white"
								}
							],
							overlayAll : true
						});
			        	
			        });
			        
				},
				error: function() {
					//
				}
			});
				
				
			
			
			/*
			$('#label_owner_username')
				.attr({ "href" : "<%=absoluteURL%>/user-details?user_id=" + data.ownerId })
				.text(data.ownerUserName);
			*/
			
			
			
			
				
			
			
			
			
			
			
			
			
			if(data.servicePackageId) {
				servicePackageId = data.servicePackageId;
			}
			
			//$('#text_users').val(data.accountUsers);
			
			
			
			
			
			// edit plan
			$('#link_plan_edit').click(function(e) {
				
				$('#section_plan').hide();
				$('#section_plan_edit').show();
				
				e.preventDefault();
			});
			
			$('#button_plan_save').click(function(e) {
				
				$('#section_plan_edit').hide();
				$('#section_plan').show();
				
				// if current plan not equal selected plan -> update
				if(servicePackageId != $('#select_package').val()) {
					updateServicePackage({
						accountId : accountId,
						packageId : $('#select_package').val(),
						calculateExpiryDate : ($('#select_package').val() != 1 ? true : false),
						success : function(data) {
							
							// demo
							location.href = "account-details?account_id=" + accountId;
							
						},
						error: function() {
							alert("ERR");
						}
					});
				}
				
				e.preventDefault();
			});
			
			$('#button_plan_cancel').click(function(e) {
				
				$('#section_plan_edit').hide();
				$('#section_plan').show();
				
				e.preventDefault();
			});
			
			
			// plan expiration date
			if(data.servicePackageExpiryDate != null) {
				
				$('#section_plan_expiration_date').show();
				
				$('.label_plan_expiration_date').text(data.servicePackageExpiryDate);
				var expDate = new Date(Date.parse(data.servicePackageExpiryDate));
				
				// edit plan expiration date
				$('#link_plan_expiration_date_edit').click(function(e) {
					
					$('#section_plan_expiration_date_view').hide();
					$('#section_plan_expiration_date_edit').show();
					
					e.preventDefault();
					
				});
				
				$('#button_plan_expiration_date_save').click(function(e) {
					
					updateServicePackage({
						accountId : accountId,
						packageId : data.servicePackageId,
						expiryDate : new Date($('#datepicker_plan_expiration_date').val()).format(dateFormat.masks.isoDate) + " 23:59",
						success : function(data) {
							
							$('#section_plan_expiration_date_edit').hide();
							$('#section_plan_expiration_date_view').show();
							
							expDate = new Date($('#datepicker_plan_expiration_date').val());
							
							// update label
							$('.label_plan_expiration_date').text($('#datepicker_plan_expiration_date').val());
							
						},
						error: function(error) {
							alert("ERR -> " + JSON.stringify(error));
						}
					});
					
					e.preventDefault();
				});
				
				$('#button_plan_expiration_date_cancel').click(function(e) {
					
					$('#section_plan_expiration_date_edit').hide();
					$('#section_plan_expiration_date_view').show();
					
					$('#datepicker_plan_expiration_date').val(expDate.format(dateFormat.masks.mediumDate));
					
					e.preventDefault();
					
				});
				
				
				
				/*
				var now = new Date();
				$('#datepicker_plan_expiration_date')
				.unbind('focusin focusout keyup')
				.bind('focusin focusout keyup', function() {
					
					var value = $.trim($(this).val());
					if (value == null || value == '') {
						$(this).addClass('error');
					}
	
					if(!/Invalid|NaN/.test(new Date(value))) {
						var d = new Date(Date.parse(value));
				        var year = d.getFullYear();
				        if(String(year).length == 4) {
							
				        	console.log($('#datepicker_plan_expiration_date').val());    	
				        	
				        	//  && year >= now.getFullYear()
				        	if((year >= (now.getFullYear() - 100))) {
								
								var newDate = d;
								
								$('#datepicker_plan_expiration_date').DatePickerSetDate(newDate.format(dateFormat.masks.isoDate), true);
								$(this).removeClass('error');
									
							} else {
								$(this).addClass('error');
							}
				        	
					    } else {
					    	$(this).addClass('error');
						}
					} else {
						$(this).addClass('error');
					}
					
				});
				*/
				
				$('#datepicker_plan_expiration_date').val(expDate.format(dateFormat.masks.mediumDate));
				$('#datepicker_plan_expiration_date').DatePicker({
					date: expDate.format(dateFormat.masks.isoDate),
					current: expDate.format(dateFormat.masks.isoDate),
					starts: 1,
					position: 'right',
					onBeforeShow: function(){
						var lastDate = new Date($('#datepicker_plan_expiration_date').val());
						$('#datepicker_plan_expiration_date').DatePickerSetDate(lastDate, true);
					},
					onRender: function(date) {
						return {
							disabled: (date.valueOf() < expDate.valueOf())
						}
					},
					onChange: function(formated, dates){
						
						var newDate = new Date(formated);
						
						$('#datepicker_plan_expiration_date').val(newDate.format(dateFormat.masks.mediumDate));
						$('#datepicker_plan_expiration_date').DatePickerHide();
					}
				});
				
				
				
				
			}
			
			// number of responses
			if(data.creditResponses != null) {
				$('#section_remaining_responses').show();
				$('#label_remaining_responses').text(data.creditResponses);
				
				// edit plan expiration date
				$('#link_remaining_responses_edit').click(function(e) {
					
					$('#section_remaining_responses_view').hide();
					$('#section_remaining_responses_edit').show();
					
					
					// clear previouse value
					$('#text_remaining_reponses').val("");
					
					// validator
					var v = null;
					v = new validator({
						elements : [
							{
								element : $('#text_remaining_reponses'),
								status : $('#status_remaining_reponses'),
								rules : [
									{ method : 'required', message : 'This field is required.' },
									{ method : 'digits', message : 'Please enter only digits.' },
									{ method : 'min', pattern : 1, message : 'Please enter a value greater than or equal to {0}.' }
								]
							}
						],
						submitElement : $('#button_remaining_responses_save'),
						messages : null,
						accept : function () {
							
							var newResponses = Number($('#text_remaining_reponses').val());
							if($('#select_decrease_remaining_responses').val() == "1") {
								// descrease
								newResponses = -newResponses;
							}
															
							changeSessionsBalance({
								accountId : accountId,
								packageId : data.servicePackageId,
								amount : newResponses,
								success : function(data) {
									
									// update responses
									$('#label_remaining_responses').text(data.sessionsBalance);
									
									$('#section_remaining_responses_edit').hide();
									$('#section_remaining_responses_view').show();
									
								},
								error: function(error) {
									alert(JSON.stringify(error));
								}
							});
							
						},
						error: function() {
							// error
						}
					});
					
					e.preventDefault();
					
				});
				
				$('#button_remaining_responses_cancel').click(function(e) {
					
					$('#section_remaining_responses_edit').hide();
					$('#section_remaining_responses_view').show();
					
					e.preventDefault();
					
				});
				
			}
			
			// permissions
			for(var i = 0; i < data.permissions.length; i++) {
				var option = $("<li class=\"ui-label\" style=\"padding: 4px;\"><input type=\"checkbox\" " + (data.permissions[i].value == true ? "checked=\"checked\"" : "") + " id=\"" + data.permissions[i].key + "\" class=\"ui-label-checkbox\" inherited=\"" + data.permissions[i].inherited + "\" value=\"" + data.permissions[i].value + "\" parent_value=\"" + data.permissions[i].parentValue + "\" /><label for=\"" + data.permissions[i].key + "\">" + data.permissions[i].name + "</label></li>");
				
				// inherited
				if(data.permissions[i].inherited == true) { 
					option.find('input:checkbox').prop("disabled", true);
					option.find('label').click(function() {
						$('#' +$(this).attr('for')).prop("disabled", false);
					});
					
				}
				
				option.find('input:checkbox')
					.change(function() {
						
						// fix 1
						if($("#checkbox_inherit_permissions").is(':checked')) {
							$("#checkbox_inherit_permissions").prop("checked", false);
						}
						
						// fix 2 
						isPermissionChanged = true;
						
						addProperty({
							key : $(this).attr("id"),
							value : $(this).is(':checked')
						});
					
				});
				
				option.appendTo('#list_permissions');
			}
			
			// inherited
			$('#checkbox_inherit_permissions').change(function() {
				
				isPermissionChanged = true;
				
				if($(this).is(':checked')) {
					
					permissions = [];
					permissionsToReset = [];
					
					// show by parent inherited
					$('#list_permissions li').each(function(i, el) {
						var E = $(el).find('input:checkbox');
							E.prop("disabled", true)
							 .prop("checked", /^true$/i.test(E.attr("parent_value")));
							
							// if inherited false add to permissions with value = null
							// add to inherited array with value = null;
							if(!/^true$/i.test(E.attr("inherited"))) {
								resetProperty({
									key : E.attr("id"),
									value : null
								});
							}
							
					});
				} else {
					
					permissions = [];
					permissionsToReset = [];
					
					// show by value and inherited
					$('#list_permissions li').each(function(i, el) {
						var E = $(el).find('input:checkbox');
							E.prop("disabled", /^true$/i.test(E.attr("inherited")))
							 .prop("checked", /^true$/i.test(E.attr("value")));
							
					});
				}
			});
			
			
			// get packages
			getPackages({
				productId : 1, // opinion
				success : function(data) {

					
					packages = data.list;
					
					var e = $('#select_package')[0].options;
			        for (var i = 0; i < data.list.length; i++) {
			        	if(data.list[i].packageId == servicePackageId) {
			        		$('#label_package').text(data.list[i].packageName);
			        	}
			        	var k = new Option(data.list[i].packageName, data.list[i].packageId);
			            try {
			                e.add(k);
			            } catch (ex) {
			                e.add(k, null);
			            }
			        }

			        $('#select_package option[value="' + servicePackageId + '"]').prop('selected', true);
			        
			        
			        /*
			        $('#select_package').bind('change keyup', function() {
			        	
			        	/*
			        	for(var x = 0; x < packages.length; x++) {
			        		if($(this).val() == packages[x].packageId) {
			        			
			        			//alert(packages[x].packageId + "_____" + packages[x].isDefault);
			        			
			        			
						        // TODO:
						        //$('#label_usage_period').text(packages[x].defaultUsagePeriodInDays == null ? "Unlimited" : packages[x].defaultUsagePeriodInDays);

								
								// usage period
								// isDefault = packages[x].isDefault;
								if(!packages[x].isDefault) {
									$('#params_usage_period').show();
									
									$('#text_package_usage_period').prop("disabled", (null == packages[x].defaultUsagePeriod)).val(packages[x].defaultUsagePeriod);
									
									$('#radio_unlimited_usage_period').prop("checked", (null == packages[x].defaultUsagePeriod));
									$('#radio_custom_usage_period').prop("checked", !(null == packages[x].defaultUsagePeriod));
									
									$("input[name='usage_period']").change(function() {
										if($(this).attr("id") == "radio_custom_usage_period") {
											$('#text_package_usage_period').prop("disabled", false).focus();
										} else {
											$('#text_package_usage_period').prop("disabled", true);
										}
									});
									
								}
								
								
								
								
			        			
			        			break;
			        		}
			        	}
			        	
			        });
			        */
			        
			        $('#select_package').trigger("change");
			        
					
				},
				error : function(error) {
					alert(JSON.stringify(error));
				}
			});
			
			
			
			// timezone
			if(data.timezoneId) {
				$('#select_timezone option[value="' + data.timezoneId + '"]').prop('selected', true);
			}

			$('#label_create_date').text(data.insertDate);
			$('#checkbox_is_active').prop('checked', data.isActive);
			
			
			


			// validate fields
			var v = null;
			v = new validator({
				elements : [
					{
						element : $('#text_account_name'),
						status : $('#status_account_name'),
						rules : [
							{ method : 'required', message : 'This field is required.' }
						]
					},
					{
						element : $('#select_timezone'),
						status : $('#status_timezone'),
						rules : [
							{ method : 'required', message : 'This field is required.' }
						] 
					}
				],
				submitElement : $('#button_update'),
				messages : null,
				accept : function () {

					updateAccountSettings({
						accountId : accountId,
						accountName : $.trim($('#text_account_name').val()),
						packageId : servicePackageId,
						timezoneId : $('#select_timezone').val(),
						permissions : (isPermissionChanged ? ($('#checkbox_inherit_permissions').is(':checked') ? permissionsToReset : permissions) : undefined),
						isActive : $('#checkbox_is_active').prop('checked'),
						/*creditResponses : $('#text_responses').val(),*/ // $('#radio_unlimited_credit_responses').is(':checked') ? null : $('#text_responses').val()
						/*accountUsers : Number($.trim($('#text_users').val())),*/
						success : function(data) {
							
							
							if(isPermissionChanged) {
								isPermissionChanged = false;
								permissions = [];
								permissionsToReset = [];
								
								// reload permissions
								
							}
							
							
							var modal = new lightFace({
								title : "Changes saved.",
								message : "Your changes were successfully saved.",
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
							
						},
						error : function() {

						}
					});
					
					
				},
				error : function() {

				}
			});
			
		}
	});
	
});
</script>