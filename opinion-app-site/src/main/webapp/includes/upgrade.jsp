
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>
<%@ page import="com.opinion.library.systemFramework.ApplicationConfiguration" %>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);

String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1><a href="<%=absoluteURL %>/account" title="Account">Account</a>&nbsp;&rsaquo;&nbsp;<%= p.getHeader() %></h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/account" title="Account Settings"><span>Account Settings</span></a></li>
						<li id="tab_billing_profile"><a href="<%=absoluteURL %>/billing-profile" title="Billing Profile"><span>Billing Profile</span></a></li>
						<li id="tab_transaction_history"><a href="<%=absoluteURL %>/transaction-history" title="Transaction History"><span>Transaction History</span></a></li>
						<li id="tab_upgrade"><a href="<%=absoluteURL %>/upgrade" title="Upgrade" class="selected"><span>Upgrade</span></a></li>
						<li id="tab_users"><a href="<%=absoluteURL %>/users" title="Users"><span>Users</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			
			<div id="form_upgrade_options">
				<h3 class="ui-header-light">Upgrade Options</h3>
				<div style="padding-top: 12px;">
					<div class="params">
						<div class="param-name">Current Plan:</div>
						<div class="param-value">
							<b id="label_plan" style="color: #333"></b>
						</div>
					</div>
					<div class="params" id="section_plan_expiration_date" style="display: none; overflow: hidden">
						<div class="param-name">Plan Expiration Date:</div>
						<div class="param-value">
							<div><b class="label_plan_expiration_date" style="color: #333"></b></div>
							<div style="padding-bottom: 10px;"><span>You will be downgraded to the free Basic plan when your PRO plan ends on <span class="label_plan_expiration_date">Sep 30, 2013</span></span></div>
						</div>
					</div>
					<div class="params" style="overflow: hidden;">
						<div class="param-name">Upgrade to...</div>
						<div class="param-value">
							<div class="ui-form" style="margin-top: -3px;">
							
								<!--
								<div class="row-choice">
									<label style="color: #999"><span><input type="radio" autocomplete="off" name="upgrade_plan" value="0" disabled="disabled" /></span>Basic $0 /forever</label>
								</div>
								-->
								<div class="row-choice">
									<label style="font-weight: bold;">
										<span><input type="radio" autocomplete="off" name="upgrade_plan" value="3" checked="checked" /></span>PRO $7.95 /mo or $79.00 /yr<br/>
									<div style="padding-top: 5px; font-weight: normal">
										<ul class="ls">
											<li>Unlimited number of responses</li>
											<li>Finish Options<br/> (Custom Thank You Message, <br/>Redirect to custom URL after survey complete)</li>
										</ul>
									</div>
									</label>
								</div>
								
								<!--
								<div class="row-choice">
									<label style="font-weight: bold;"><span><input type="radio" autocomplete="off" name="upgrade_plan" value="4" /></span>Enterprise $19.95 /mo or $199.00 /yr</label>
								</div>
								-->
							</div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">Bill me:</div>
						<div class="param-value">
							<select autocomplete="off" id="select_count_of_months"></select>
						</div>
						<div class="param-value">
							<span style="margin-left: 6px; display: none;" id="label_yearly_discount">and <b style="color: #333">save 17%</b></span>
						</div>
					</div>
					<div class="params">
						<div class="param-name">Price:</div>
						<div class="param-value">
							<b id="label_price" style="color: #333"></b>
						</div>
					</div>
					
					<div style="clear: both; height: 24px; overflow: hidden;"></div>
					<div class="params">
						<div class="param-name"></div>
						<div class="param-value">
							<a id="button_upgrade_plan" title="Upgrade" class="button-blue"><span>Upgrade</span></a>
						</div>
					</div>
				</div>
			</div>
			
			<div id="form_upgrade_confirmation" style="display: none">
				<h3 class="ui-header-light">Account Upgrade Confirmation</h3>
				<div style="padding-top: 12px;padding-bottom: 12px;">
					You have successfully upgraded to a PRO account.<br/>
					As a PRO member, you now have access to our PRO features.
				</div>
				<a href="<%=absoluteURL %>/account" title="View My Account">View My Account</a>
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


/*
var packages = [
	{ id: 3, name: "PRO", price: 7.95, discount: 0.17 },
	{ id: 4, name: "Enterprise", price: 19.95, discount : 0.17 }
];
*/

var upgradePackage = function(params) {

	var obj = {
		accounts : {
			upgradePackage : {
				accountId: params.accountId,
				packageId : params.packageId,
				countOfMonths : params.countOfMonths
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
			if(data.accounts.upgradePackage != undefined) {
				if(data.accounts.upgradePackage.error != undefined) {
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.accounts.upgradePackage);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.accounts.upgradePackage);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};


var amount = 7.95;
function getYearlyPrice(amount) {
	// var amount = amount || 7.95;
	var discount = 0.17;
	return Math.floor((amount*12) - (amount*12)*discount);
}

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;

$(function() {
	
	getAccountDetails({
		accountId : accountId,
		success : function(data) {
			
			// owner access only
			if(data.isOwner) {
				
				$("#tab_billing_profile, #tab_transaction_history").show();
				
				if(auth.userInfo.plan != undefined) {
					
					$('#label_plan').text(auth.userInfo.plan.title);
					
					if(auth.userInfo.permissions.makePayment) {
						$('#tab_upgrade').show();
						//$('#tab_users').show();
					}
					
					if(auth.userInfo.plan.isFree) {
						
					} else {
						
						$('#section_plan_expiration_date').show();
						$('.label_plan_expiration_date').text(auth.userInfo.plan.planExpirationDate);
						
					}

				}

				var billingFrequencies = [12, 1, 2, 3, 6];
				
				var p = $('#select_count_of_months').empty();
				var q = p[0].options;
				for(var i = 0; i < billingFrequencies.length; i++) {
					 var k = new Option((billingFrequencies[i] == 12 ? "yearly $" + getYearlyPrice(amount).formatCurrency(2, '.', ',') : (billingFrequencies[i] == 1 ? "monthly $" + (amount).formatCurrency(2, '.', ',') : (billingFrequencies[i] + " months $" + (amount*billingFrequencies[i]).formatCurrency(2, '.', ',')))), billingFrequencies[i]);
				 	 try {
				         q.add(k)
				     } catch (ex) {
				         q.add(k, null)
				     }
				}
				p[0].selectedIndex = 1;
				
				
				p.bind("change keyup", function() {
					var value = $(this).val();
					if(value == 12) {
						$('#label_yearly_discount').show();
						$('#label_price').text("$" + getYearlyPrice(amount).formatCurrency(2, '.', ',') + " /yr");
					} else if(value == 1) {
						$('#label_yearly_discount').hide();
						$('#label_price').text("$" + (amount).formatCurrency(2, '.', ',') + " /mo");
					} else if(value == 2) {	
						$('#label_yearly_discount').hide();
						$('#label_price').text("$" + (amount*2).formatCurrency(2, '.', ',') + " /2 months");
					} else if(value == 3) {	
						$('#label_yearly_discount').hide();
						$('#label_price').text("$" + (amount*3).formatCurrency(2, '.', ',') + " /3 months");
					} else if(value == 6) {
						$('#label_yearly_discount').hide();
						$('#label_price').text("$" + (amount*6).formatCurrency(2, '.', ',') + " /6 months");
					}
				});
				
				
				p.trigger("change");
				
				
				// upgrade
				$('#button_upgrade_plan').click(function(e) {
					
					loader.show();
					
					upgradePackage({
						accountId : accountId,
						packageId : 3,
						countOfMonths : parseInt($('#select_count_of_months').val()),
						success : function(data) {
							
							$("#form_upgrade_options").hide();
							$("#form_upgrade_confirmation").show();
							
							//console.log(JSON.stringify(data));
							
							loader.hide();
							
							// ask user
							/*
							if(data.transactionId != undefined) {
								// do refresh
								location.href = "<%=absoluteURL %>/account"; // account
							}
							*/
							
						},
						error: function(data) {
							
							if(data.error == "NoEnoughFunds") {
									
								if (data.amountToFund != undefined && data.chargeId != undefined) {
									
									if(data.balance == 0) {
										
										// redirect to 
										location.href = "<%=absoluteURL %>/make-payment?charges=" + data.chargeId; // + "&return_url=<%=absoluteURL %>/upgrade";
										
									} else {
										
										loader.hide();
									
										var modal = new lightFace({
											title : "Your account balance is running low.",
											message : $.format("Your account balance is running low. You have only <b>{0}</b>.<br/> To complete purchase, please make a payment of <b>{1}</b>.", "$" + (data.balance).formatCurrency(2, '.', ','), "$" + (data.amountToFund).formatCurrency(2, '.', ',')),
											actions : [
										        { 
										        	label : "Make a payment", 
										        	fire : function() {
										        		// redirect to make a payment
										        		location.href = "<%=absoluteURL %>/make-payment?charges=" + data.chargeId; // + "&return_url=<%=absoluteURL %>/upgrade";
										        		
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
					
					e.preventDefault();
					
				});
				
			}
			
		},
		error: function() {
			
		}
	});
	
});
</script>