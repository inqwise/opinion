
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="com.inqwise.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.inqwise.opinion.library.common.countries.ICountry" %>
<%@ page import="com.inqwise.opinion.library.managers.CountriesManager" %>
<%@ page import="com.inqwise.opinion.library.common.countries.ITimeZone" %>
<%@ page import="com.inqwise.opinion.library.common.countries.IStateProvince" %>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);

String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1><%= p.getHeader() %></h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/account" title="Account Settings" class="selected"><span>Account Settings</span></a></li>
						<li id="tab_billing_profile"><a href="<%=absoluteURL %>/billing-profile" title="Billing Profile"><span>Billing Profile</span></a></li>
						<li id="tab_transaction_history"><a href="<%=absoluteURL %>/transaction-history" title="Transaction History"><span>Transaction History</span></a></li>
						<li id="tab_upgrade"><a href="<%=absoluteURL %>/upgrade" title="Upgrade"><span>Upgrade</span></a></li>
						<li id="tab_users"><a href="<%=absoluteURL %>/users" title="Users"><span>Users</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			<div class="Xwrapper-content-left">
				
				<div class="params">
					<div class="param-name">Account Id:</div>
					<div class="param-value">
						<b id="label_account_id"></b>
					</div>
				</div>
				
				<div id="form_view" style="display: none;">
					<div class="params">
						<div class="param-name">Account Name:</div>
						<div class="param-value">
							<b id="label_account_name"></b>
						</div>
					</div>
					<div class="params" id="section_timezone" style="display: none">
						<div class="param-name">Time zone:</div>
						<div class="param-value">
							<b id="label_account_timezone"></b>
						</div>
					</div>
				</div>
				
				<div id="form_update" style="display: none;">
					<div class="params">
						<div class="param-name">* Account Name:</div>
						<div class="param-value">
							<div><input type="text" id="text_account_name" name="account_name" size="30" maxlength="32" /></div>
							<div><label id="status_account_name"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">Current Plan:</div>
						<div class="param-value">
							<b id="label_plan"></b><span id="label_upgrade" style="margin-left: 6px; display: none;"><a href="<%=absoluteURL %>/upgrade" title="Upgrade" id="link_upgrade">Upgrade</a></span>
						</div>
					</div>
					<div class="params" id="section_plan_expiration_date" style="display: none; overflow: hidden">
						<div class="param-name">Plan Expiration Date:</div>
						<div class="param-value">
							<div><b class="label_plan_expiration_date"></b></div>
							<div style="padding-bottom: 10px;"><span>You will be downgraded to the free Basic plan when your PRO plan ends on <span class="label_plan_expiration_date">Sep 30, 2013</span></span></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name" style="width: 130px;">* Time zone:</div>
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
							<b id="label_create_date"></b>
						</div>
					</div>
					<div style="height: 24px; overflow: hidden;"></div>
					<div class="params">
						<div class="param-name">&nbsp;</div>
						<div class="param-value">
							<a id="button_update" title="Update" class="button-blue" href="javascript:;"><span>Update</span></a>
						</div>
					</div>
				
				</div>
				
			</div>
		</div>
	</div>
</div>

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

var setAccountDetails = function(params) {

	var obj = {
		accounts : {
			setAccountDetails : {
				accountId : params.accountId,
				accountName : params.accountName,
				timezoneId : params.timezoneId
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
				if(data.accounts.setAccountDetails.error != undefined) {
					
					errorHandler({
						error : data.accounts.setAccountDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.accounts.setAccountDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.accounts.setAccountDetails);
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

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;

$(function() {
	
	getAccountDetails({
		accountId : accountId,
		success : function(data) {
		
			// owner access only
			if(data.isOwner) {
				
				$("#tab_billing_profile, #tab_transaction_history").show();
				
				
				$("#form_update").show();
				
				$('#label_account_id').text(data.accountId);
				$('#text_account_name').val(data.accountName);
				
				if(data.timezoneId) {
					$('#select_timezone option[value="' + data.timezoneId + '"]').prop('selected', true);
				}
				
				$('#label_create_date').text(data.insertDate);
				

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

						setAccountDetails({
							accountId : accountId,
							accountName : $.trim($('#text_account_name').val()),
							timezoneId : $('#select_timezone').val(),
							success : function(data) {
							
								var modal = new lightFace({
									title : "Changes saved.",
									message : "Your changes were successfully saved.",
									actions : [
									           { label : "OK", fire : function() { modal.close(); }, color : "blue" }
									],
									overlayAll : true
								});
								
							},
							error : function(error) {
								//
							}
						});
						
					},
					error : function() {

					}
				});
				
				if(auth.userInfo.plan != undefined) {
					
					$('#label_plan').text(auth.userInfo.plan.title);
					
					if(auth.userInfo.permissions.makePayment) {
						$('#tab_upgrade').show();
						//$('#tab_users').show();
						
						$('#label_upgrade').show();
					}
					
					if(auth.userInfo.plan.isFree) {
						
					} else {
						
						$('#section_plan_expiration_date').show();
						$('.label_plan_expiration_date').text(auth.userInfo.plan.planExpirationDate);
						
						
						//jsapi.log(JSON.stringify(auth.userInfo.plan));
						
						
					}

				}
				
			} else {
				
				// view
				
				$("#form_view").show();
				
				
				$('#label_account_id').text(data.accountId);
				$('#label_account_name').text(data.accountName);
				
				if(data.timezoneId) {
					$("#section_timezone").show();
					$("#label_account_timezone").text($("#select_timezone option[value=" + data.timezoneId + "]").text());
				}
				
				
			}
			
		}
	});
	
});
</script>