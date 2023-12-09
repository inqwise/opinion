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
	String chargeId = request.getParameter("charge_id");
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">			
				<h1><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" id="header_account_name"></a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-charges?account_id=<%=accountId %>" title="Charges">Charges</a>&nbsp;&rsaquo;&nbsp;Charge #<%=chargeId %></h1>
			</td>
			<td class="cell-right ui-selector-right">
				<ul class="lw" id="list_charge_actions" style="display: inline-block;"></ul>
			</td>
		</tr>
	</table>
	<div style="clear: both; Xpadding-top: 24px;">
		<div class="wrapper-content-left">
			<h3 class="ui-header-light">Charge Details</h3>
			
			<!-- 
			<div style="padding: 12px 0 0 0">
				Listed below are the details of the charge, including related transactions.
			</div>
			-->
			
			<div>
				<ul class="ll" style="margin-left: 0px;margin-bottom: 0px;">
					<li>Charge Id: <b><%=chargeId %></b></li>
					<li>Charge Date: <b id="label_charge_create_date"></b></li>
					<li>Amount: <b id="label_charge_amount"></b></li>
					<li>Status: <b id="label_charge_status"></b></li>
					<li>Charge Name: <b id="label_charge_name"></b></li>
					<li>Description: <b id="label_charge_description"></b></li>
				</ul>
			</div>
			
			<div style="clear: both; padding-top: 24px;">
				
				<div id="placeholder_payments" style="display: none;">
					<h3 class="ui-header-light">Payments</h3>
					<div style="padding: 12px 0">
						No payment has been made for this charge.
					</div>
					<div style="padding-bottom: 24px;">
						<a id="button_pay_charge" class="button-green" title="Pay Now"><span>Pay Now</span></a>
					</div>
				</div>
				
				<div id="placeholder_transactions">
					<h3 class="ui-header-light">Transactions related to this charge</h3>
					<div style="padding-top: 12px;">
						<table class="ti">
							<thead>
								<tr>
									<th>Transaction Date</th>
									<th>Transaction Id</th>
									<th>Type</th>
									<th class="align-right" style="width:74px;">Amount</th>
								</tr>
							</thead>
							<tbody id="table_charge_transactions_body"></tbody>
							<tfoot>
								<tr>
									<td class="align-right">&nbsp;</td>
									<td class="align-right">&nbsp;</td>
								</tr>
							</tfoot>
						</table>
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

var getChargeDetails = function(params) {

	var obj = {
		charges : {
			getChargeDetails : {
				accountId : params.accountId,
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
			if(data.charges.getChargeDetails != undefined) {
				if(data.charges.getChargeDetails.error != undefined) {
					
					errorHandler({
						error : data.charges.getChargeDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.charges.getChargeDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.charges.getChargeDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getTransactions = function(params) {

	var obj = {
		accounts : {
			getTransactions : {
				accountId : params.accountId,
				billId : params.billId,
				referenceTypeId : params.referenceTypeId
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
        	if(data.accounts.getTransactions.error != undefined) {
				
        		errorHandler({
					error : data.accounts.getTransactions.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.getTransactions);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.getTransactions);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var cancelCharge = function(params) {

	var obj = {
		charges : {
			cancelCharge : {
				accountId : params.accountId,
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
        	if(data.charges.cancelCharge.error != undefined) {
				
        		errorHandler({
					error : data.charges.cancelCharge.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.charges.cancelCharge);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.charges.cancelCharge);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var accountId = <%= accountId %>;
var chargeId = <%=chargeId %>;

var getChargeStatus = function(statusId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = "Unpaid";
			break;
		case 2 : 
			s = "Paid";
			break;
		case 3 :
			s = "Void";
			break;
		case 4 :
			s = "Refunded";
			break;
		case 5 :
			s = "Credited";
			break;
		case 6 :
			s = "Pending";
			break;
		case 7 :
			s = "Canceled";
			break;
	}
	return s;
};

var getTransactionType = function(typeId) {
	var s = "";
	switch(typeId) {
		case 3 : 
			s = "Charge";
			break;
		case 7 : 
			s = "Charge canceled";
			break;
	}
	return s;
};
 
$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			
			getChargeDetails({
				accountId : accountId,
				chargeId : chargeId,
				success : function(data) {
					
					$('#label_charge_create_date').text(data.insertDate);
					
					$('#label_charge_name').text(data.name);
					$('#label_charge_description').html(data.description);
					$('#label_charge_status').text(getChargeStatus(data.statusId));
					$('#label_charge_amount').text("$" + (data.amount).formatCurrency(2, '.', ','));
					
					
					// transactions
					getTransactions({
						accountId : accountId,
						billId : data.chargeId,
						referenceTypeId : 1,
						success : function(transactions) {
							if(transactions.list.length != 0) {
								for(var t = 0; t < transactions.list.length; t++) {
									$("<tr>" +
										"<td>" + transactions.list[t].modifyDate + "</td>" +
										"<td>" + transactions.list[t].transactionId + "</td>" +
										"<td>" + getTransactionType(transactions.list[t].typeId) + "</td>" +
										"<td class=\"align-right\">$" + (transactions.list[t].amount).formatCurrency(2, '.', ',') + "</td>" +
									"</tr>").appendTo("#table_charge_transactions_body");
								}
							} else {
								$("<tr class=\"nop\"><td colspan=\"4\"><b>No records found.</b></td></tr>").appendTo("#table_charge_transactions_body");
							}
						},
						error : function(error) {
							
							$("<tr class=\"nop\"><td colspan=\"4\"><b>No records found.</b></td></tr>").appendTo("#table_charge_transactions_body");
							
							alert(JSON.stringify(error));
						}
					});
					
					
					// actions
					var d = $("<li><a href=\"javascript:;\" title=\"Cancel\" id=\"link_charge_cancel\" class=\"button-white\"><span>Cancel</span></a></li>").appendTo('#list_charge_actions');
					d.find('#link_charge_cancel').click(function() {
						if(!$(this).hasClass('ui-button-disabled')) {
							
							
							cancelCharge({
								accountId : accountId,
								chargeId : chargeId,
								success : function(data) {
									
									alert("SUCC -> ");
									
								},
								error: function(error) {
									
									alert("ERR -> " + JSON.stringify(error));
									
								}
							});
							
						}
					});
					
					
					
					
					
				},
				error: function(error) {
					//
				}
			});
			
			
		},
		error : function() {
			
		}
	});
	
});		
</script>