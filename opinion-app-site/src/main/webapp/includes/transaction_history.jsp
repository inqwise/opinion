
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
						<li id="tab_transaction_history"><a href="<%=absoluteURL %>/transaction-history" title="Transaction History" class="selected"><span>Transaction History</span></a></li>
						<li id="tab_upgrade"><a href="<%=absoluteURL %>/upgrade" title="Upgrade"><span>Upgrade</span></a></li>
						<li id="tab_users"><a href="<%=absoluteURL %>/users" title="Users"><span>Users</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both;padding-top: 20px;">
			
			<h3 class="ui-header-light">Current balance</h3>
			<div style="padding-top: 12px;">
				<div><b style="color: #333" id="label_current_balance">0.00</b> credit remaining</div>
				<div id="label_last_successful_payment" style="display: none"></div>
			</div>
			
			<div style="clear: both; padding-top: 20px;">
				<div id="placeholder_apply_promotional_code" style="display: none">
					<h3>Apply promotion code</h3>
					<div>
						<div>
							<div class="params">
								<div class="param-name">* Promotion Code:</div>
								<div class="param-value">
									<div><input type="text" size="30" maxlength="30" id="text_promotion_code" name="promotion_code" autocomplete="off" /></div>
									<div><label id="status_promotion_code"></label></div>
								</div>
							</div>
						</div>
						<ul class="la">
							<li>Once the promotion code amount is used up, we'll continue serving your package and will start charging the form of payment you provided.</li>
							<li>An expiration date often applies, so keep in mind that you only have a certain amount of time to redeem your credit.</li>
						</ul>
						<div>
							<div class="params">
								<div class="param-value">
									<a href="javascript:;" title="Submit" class="button-blue" id="button_apply_promotion_code_submit"><span>Submit</span></a>
								</div>
								<div class="param-value" style="padding-left: 8px; line-height: 18px;">
									<a href="javascript:;" title="Cancel" id="button_apply_promotion_code_cancel">Cancel</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="placeholder_billing_modification" style="display: none;"></div>
			</div>
			
			<div id="table_transactions"></div>
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

var getTransactions = function(params) {

	var obj = {
		accounts : {
			getTransactions : { 
				accountId : params.accountId,
				groupBy : params.groupBy,
				transactionTypes : params.transactionTypes
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

var getCreditCardType = function(creditCardTypeId) {
	var s = "";
	switch(creditCardTypeId) {
		case 1 : 
			s = "Visa";
			break;
		case 2 :
			s = "MasterCard";
			break;
		case 3 :
			s = "Discover";
			break;
		case 4 :
			s = "American Express";
			break;
	}
	return s;
};

var tableTransactions = null;
var transactions = [];

var renderTableTransactions = function() {
	
	$('#table_transactions').empty();
	tableTransactions = $('#table_transactions').dataTable({
		tableColumns : [
			{ key : 'transactionId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'modifyDate', label : 'Date', sortable: true, sortBy : { dataType: "date" }, width: 86 },
			{ key : 'typeId', label : 'Description', sortable : true, formatter: function(cell, value, record, source) {
				var s = "";
				switch(record.typeId) {
					case 1 :
						s = "Starting balance";
						break;
					case 2 : 
						s = "Payment: " + (record.creditCard != undefined ? getCreditCardType(record.creditCardTypeId) + " ****" + record.creditCard : "PayPal"); // record.comments
						break;
					case 3 :
						s = "Charge: <a href=\"<%=absoluteURL %>/charges/" + record.referenceId + "\" title=\"" + record.chargeDescription + "\">" + record.chargeDescription + "</a>";
						break;
					case 4 : 
						s = "Credit - " + record.comments;
						break;
					case 5 :
						s = "Debit - " + record.comments;
						break;
					case 6 : 
						s = "Refund $" + (record.credit).formatCurrency(2, '.', ',') +  " - " + record.comments;
						break;
					case 7 :
						s = "Charge canceled: <a href=\"<%=absoluteURL %>/charges/" + record.referenceId + "\" title=\"Charge #" + record.referenceId + "\">Charge #" + record.referenceId + "</a>";
						break;
					case 9 :
						s = "Promotion code: QAAS-DTUD-HCAD-LKGH-E6JK";
						break;
					case 10 :
						s = "Activation fee";
						break;
					default :
						s = record.typeId;
						break;
				}
				return s;
			}},
			{ key : 'debit', label : 'Debit', sortable : true, width: 74, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return (record.debit != undefined ? (record.debit != 0 ? "$" + (record.debit).formatCurrency(2, '.', ',') : "") : "");
			}},
			{ key : 'credit', label : 'Credit', sortable : true, width: 74, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return (record.credit != undefined ? (record.credit != 0 ? "$" + (record.credit).formatCurrency(2, '.', ',') : "" )  : "");
			}},
			{ key : 'balance', label : 'Balance', sortable : true, width: 74, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "$" + (record.balance).formatCurrency(2, '.', ',');	
			}}
		],
		dataSource : transactions,
		groupBy : "transactions",
		groupColumns : [
			{ key : "fromDate", colspan : 3, formatter: function(cell, value, record, source) {
				
				var invoices = "";
				/*
				if(record.invoices != undefined) {
					for(var i = 0; i < record.invoices.length; i++) {
						invoices += "<a href=\"<%=absoluteURL %>/invoices/" + record.invoices[i].invoiceId + "\" title=\"#" + record.invoices[i].invoiceId + "\">#" + record.invoices[i].invoiceId + "</a>" + (i != (record.invoices.length - 1) ? ", " : "");
					}
				}
				*/
				return "<b>" + record.fromDate + " - " + record.toDate + "</b> " + invoices;
			}},
			{ key : "debit", style : { cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "<b>" + (record.debit != undefined ? "$" + (record.debit).formatCurrency(2, '.', ',') : "") + "</b>";
			}},
			{ key : "credit", style : { cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "<b>" + (record.credit != undefined ? "$" + (record.credit).formatCurrency(2, '.', ',') : "") + "</b>";
			}},
			{ key : "balance", style : { cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "<b>" + (record.balance != undefined ? "$" + (record.balance).formatCurrency(2, '.', ',') : "") + "</b>";
			}}
		],
		/*
		pagingStart : 5,
		show : [5, 10, 25, 50, 100],
		*/
		disablePaging : true,
		disableFiltering : true,
		actions : [
			{
				label : "Make a payment",
				color: "blue",
				disabled : !auth.userInfo.permissions.makePayment,
				fire : function() {
					location.href = "<%=absoluteURL %>/make-payment";
				}
			}
		],
		filters : [
			{
				title : "Filter:",
				options : [
					{ caption : "All Transactions", value : "" },
					{ caption : "Payments", value : 2 },
					{ caption : "Charges", value : 3 },
					{ caption : "Adjustments", value : 1 }, // 4, 5, 6, 7, 9, 10
					{ caption : "Refunds", value : 6 }
				],
				change : function(el) {
					
					
					var filterTransactions = ($(el).val() == "" ? null : $(el).val());
					var transactionTypes = null;
					if(filterTransactions != null) {
						transactionTypes = [filterTransactions]; //[filterTransactions];
					}
					
					getTransactions({
						accountId : accountId,
						groupBy : "month",
						transactionTypes : transactionTypes,
						success : function(data) {
							
							transactions = data.list;
							//renderTableTransactions();
							tableTransactions.updateRecords(transactions);
							
							getAccountDetails({
								accountId : accountId,
								success : function(data) {
									balance = data.balance;
									$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
								},
								error : function() {
									//
								}
							});
							
						},
						error : function(error) {
							transactions = [];
							//renderTableTransactions();
							tableTransactions.updateRecords(transactions);
						}
					});
					
					
					
				}
			}
		]
	});
	
};

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;
var balance = 0;

$(function() {
	
	getAccountDetails({
		accountId : accountId,
		success : function(data) {
			
			// owner access only
			if(data.isOwner) {
				
				$("#tab_billing_profile, #tab_transaction_history").show();
				
				balance = data.balance;
				$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
				
				
				getTransactions({
					accountId : accountId,
					groupBy : "month",
					success : function(data) {
						transactions = data.list;
						if(data.lastFundTransaction != undefined) {
							$('#label_last_successful_payment')
								.show()
								.text("Last successful payment " + data.lastFundTransaction.modifyDate + " ($" + (data.lastFundTransaction.amount).formatCurrency(2, '.', ',') + " " + (data.lastFundTransaction.creditCard != undefined ? getCreditCardType(data.lastFundTransaction.creditCardTypeId) + " ****" + data.lastFundTransaction.creditCard : "PayPal") + ")"); // data.lastFundTransaction.comments
						}
						renderTableTransactions();
					},
					error : function(error) {
						transactions = [];
						renderTableTransactions();
					}
				});
				
				if(auth.userInfo.plan != undefined) {
					
					if(auth.userInfo.permissions.makePayment) {
						$('#tab_upgrade').show();
						//$('#tab_users').show();
					}
					
					if(auth.userInfo.plan.isFree) {
						
					}
					
				}
				
			}
			
		},
		error: function() {
			
		}
	});
	
});
</script>