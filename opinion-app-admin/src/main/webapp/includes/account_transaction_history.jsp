
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
%>

<div>
	<h1 style="padding-bottom: 0 !important"><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<span id="header_account_name"></span></h1>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln" id="list_account_details"></ul>
	</div>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" title="Account Details"><span>Account Details</span></a></li>
					<li><a href="<%=absoluteURL %>/account-users?account_id=<%=accountId %>" title="Users"><span>Users</span></a></li>
					<li><a href="<%=absoluteURL %>/account-surveys?account_id=<%=accountId %>" title="Surveys"><span>Surveys</span></a></li>
					<li><a href="<%=absoluteURL %>/account-collectors?account_id=<%=accountId %>" title="Collectors"><span>Collectors</span></a></li>
					<li><a href="<%=absoluteURL %>/account-billing?account_id=<%=accountId %>" title="Billing"><span>Billing</span></a></li>
					<li><a href="<%=absoluteURL %>/account-transaction-history?account_id=<%=accountId %>" title="Transaction History" class="selected"><span>Transaction History</span></a></li>
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
		<h3 class="ui-header-light">Current balance</h3>
		<div style="padding-top: 12px;">
			<div><b style="color: #333" id="label_current_balance">0.00</b> credit remaining</div>
			<div id="label_last_successful_payment" style="display: none"></div>
		</div>
		
		<div style="clear: both; padding-top: 20px;">
			<div id="placeholder_credit" style="display: none; background: #FFF9D7; border: 1px solid #E2C822; padding: 10px; margin: 0 0 10px">
				<h3>Add Credit</h3>
				<div style="padding-top: 12px;">
					<div>
						<div class="params">
							<div class="param-name">* Amount to Credit:</div>
							<div class="param-value">
								<div><input type="text" size="12" maxlength="12" id="text_credit_amount" name="credit_amount" autocomplete="off" /></div>
								<div><label id="status_credit_amount"></label></div>
							</div>
						</div>
						<div class="params" style="min-height: 74px;">
							<div class="param-name">Notes / Comments:</div>
							<div class="param-value">
								<div><textarea style="width: 314px; height: 64px;" maxlength="1000" autocomplete="off" name="credit_comments" id="textarea_credit_comments"></textarea></div>
								<div><label id="status_credit_comments"></label></div>
							</div>
						</div>
					</div>
					<div style="clear: both; padding-top: 12px;">
						<div class="params">
							<div class="param-name"></div>
							<div class="param-value">
								<a href="javascript:;" title="Submit" class="button-blue" id="button_credit_submit"><span>Submit</span></a>
							</div>
							<div class="param-value" style="line-height: 18px;">
								<a href="javascript:;" title="Cancel" id="button_credit_cancel" style="margin-left: 6px;">Cancel</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="placeholder_debit" style="display: none; background: #FFF9D7; border: 1px solid #E2C822; padding: 10px; margin: 0 0 10px">
				<h3>Debit</h3>
				<div style="padding-top: 12px;">
					<div>
						<div class="params">
							<div class="param-name">* Amount to Debit:</div>
							<div class="param-value">
								<div><input type="text" size="12" maxlength="12" id="text_debit_amount" name="debit_amount" autocomplete="off" /></div>
								<div><label id="status_debit_amount"></label></div>
								<div style="padding-bottom: 10px;"><em style="color: #999">(The maximum amount for debit is: <b id="label_debit_max"></b>)</em></div>
							</div>
						</div>
						<div class="params" style="min-height: 74px;">
							<div class="param-name">Notes / Comments:</div>
							<div class="param-value">
								<div><textarea style="width: 314px; height: 64px;" maxlength="1000" autocomplete="off" name="debit_comments" id="textarea_debit_comments"></textarea></div>
								<div><label id="status_debit_comments"></label></div>
							</div>
						</div>
					</div>
					<div style="clear: both; padding-top: 12px;">
						<div class="params">
							<div class="param-name"></div>
							<div class="param-value">
								<a href="javascript:;" title="Submit" class="button-blue" id="button_debit_submit"><span>Submit</span></a>
							</div>
							<div class="param-value" style="line-height: 18px;">
								<a href="javascript:;" title="Cancel" id="button_debit_cancel" style="margin-left: 6px;">Cancel</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="placeholder_apply_promotional_code" style="display: none; background: #FFF9D7; border: 1px solid #E2C822; padding: 10px; margin: 0 0 10px">
				<h3>Apply promotion code</h3>
				<div style="padding-top: 12px;">
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
					<div style="padding-top: 12px;">
						<div class="params">
							<div class="param-value">
								<a href="javascript:;" title="Submit" class="button-blue" id="button_apply_promotion_code_submit"><span>Submit</span></a>
							</div>
							<div class="param-value" style="line-height: 18px;">
								<a href="javascript:;" title="Cancel" id="button_apply_promotion_code_cancel" style="margin-left: 6px;">Cancel</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="placeholder_manual_transaction_handling" style="display: none; background: #FFF9D7; border: 1px solid #E2C822; padding: 10px; margin: 0 0 10px">
				<h3>Manual transaction handling</h3>
				<div style="padding-top: 12px;">
					<div>
						<div class="params">
							<div class="param-name">* Transaction Type:</div>
							<div class="param-value">
								<div><select id="select_manual_transaction_handling_transaction_type" name="manual_transaction_handling_transaction_type" autocomplete="off"><option value="9">Refund</option><option value="2">Payment</option></select></div>
							</div>
						</div>
						<div id="section_refund" style="display: none;">
							<div class="params">
								<div class="param-name">Related Transaction #:</div>
								<div class="param-value">
									<div><select id="select_refund_related_transaction" name="refund_related_transaction" autocomplete="off"></select></div>
								</div>
							</div>
							<div class="params">
								<div class="param-name">* Amount to Refund:</div>
								<div class="param-value">
									<div><input type="text" size="12" maxlength="12" id="text_refund_amount" name="refund_amount" autocomplete="off" /></div>
									<div><label id="status_refund_amount"></label></div>
								</div>
							</div>
							<div class="params" style="min-height: 74px;">
								<div class="param-name">Notes / Comments:</div>
								<div class="param-value">
									<div><textarea style="width: 314px; height: 64px;" maxlength="1000" autocomplete="off" name="refund_comments" id="textarea_refund_comments"></textarea></div>
									<div><label id="status_refund_comments"></label></div>
								</div>
							</div>
							<div style="clear: both; padding-top: 12px;">
								<div class="params">
									<div class="param-name"></div>
									<div class="param-value">
										<a href="javascript:;" title="Submit" class="button-blue" id="button_refund_submit"><span>Submit</span></a>
									</div>
									<div class="param-value" style="line-height: 18px;">
										<a href="javascript:;" title="Cancel" id="button_refund_cancel" style="margin-left: 6px;">Cancel</a>
									</div>
								</div>
							</div>
						</div>
						<div id="section_payment" style="display: none;">
							<div class="params">
								<div class="param-name">* Amount of Payment:</div>
								<div class="param-value">
									<div><input type="text" size="12" maxlength="12" id="text_payment_amount" name="payment_amount" autocomplete="off" /></div>
									<div><label id="status_payment_amount"></label></div>
								</div>
							</div>
							<div class="params" style="min-height: 74px;">
								<div class="param-name">Notes / Comments:</div>
								<div class="param-value">
									<div><textarea style="width: 314px; height: 64px;" maxlength="1000" autocomplete="off" name="payment_comments" id="textarea_payment_comments"></textarea></div>
									<div><label id="status_payment_comments"></label></div>
								</div>
							</div>
							<div style="clear: both; padding-top: 12px;">
								<div class="params">
									<div class="param-name"></div>
									<div class="param-value">
										<a href="javascript:;" title="Submit" class="button-blue" id="button_payment_submit"><span>Submit</span></a>
									</div>
									<div class="param-value" style="line-height: 18px;">
										<a href="javascript:;" title="Cancel" id="button_payment_cancel" style="margin-left: 6px;">Cancel</a>
									</div>
								</div>
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

var getTransactions = function(params) {

	var obj = {
		accounts : {
			getTransactions : { 
				accountId: params.accountId,
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

var manualRefund = function(params) {

	var obj = {
		accounts : {
			manualRefund : {
				accountId : params.accountId,
				amount: params.amount,
				comments : params.comments
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
        	if(data.accounts.manualRefund.error != undefined) {
				
        		errorHandler({
					error : data.accounts.manualRefund.error	
				});
        		
        		if(params.error != undefined 
					&& typeof params.error == 'function') {
					params.error(data.accounts.manualRefund);
				}
			} else {
				if(params.success != undefined 
					&& typeof params.success == 'function') {
					params.success(data.accounts.manualRefund);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var manualPayment = function(params) {

	var obj = {
		accounts : {
			manualPayment : {
				accountId : params.accountId,
				amount: params.amount,
				comments : params.comments
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
        	if(data.accounts.manualPayment.error != undefined) {
				
        		errorHandler({
					error : data.accounts.manualPayment.error	
				});
        		
        		if(params.error != undefined 
					&& typeof params.error == 'function') {
					params.error(data.accounts.manualPayment);
				}
			} else {
				if(params.success != undefined 
					&& typeof params.success == 'function') {
					params.success(data.accounts.manualPayment);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var credit = function(params) {

	var obj = {
		accounts : {
			credit : {
				accountId : params.accountId,
				amount: params.amount,
				comments : params.comments
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
        	if(data.accounts.credit.error != undefined) {
				
        		errorHandler({
					error : data.accounts.credit.error	
				});
        		
        		if(params.error != undefined 
					&& typeof params.error == 'function') {
					params.error(data.accounts.credit);
				}
			} else {
				if(params.success != undefined 
					&& typeof params.success == 'function') {
					params.success(data.accounts.credit);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var debit = function(params) {

	var obj = {
		accounts : {
			debit : {
				accountId : params.accountId,
				amount: params.amount,
				comments : params.comments
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
        	if(data.accounts.debit.error != undefined) {
				
        		errorHandler({
					error : data.accounts.debit.error	
				});
        		
        		if(params.error != undefined 
					&& typeof params.error == 'function') {
					params.error(data.accounts.debit);
				}
			} else {
				if(params.success != undefined 
					&& typeof params.success == 'function') {
					params.success(data.accounts.debit);
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
/* var allowRefundTransactions = []; */

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
						s = "Charge: <a href=\"<%=absoluteURL %>/account-charge?account_id=<%=accountId %>&charge_id=" + record.referenceId + "\" title=\"" + record.chargeDescription + "\">" + record.chargeDescription + "</a>";
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
						s = "Charge canceled: <a href=\"<%=absoluteURL %>/account-charge?account_id=<%=accountId %>&charge_id=" + record.referenceId + "\" title=\"Charge #" + record.referenceId + "\">Charge #" + record.referenceId + "</a>";
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
						invoices += "<a href=\"<%=absoluteURL %>/account-invoice?account_id=" + accountId  + "&invoice_id=" + record.invoices[i].invoiceId + "\" title=\"#" + record.invoices[i].invoiceId + "\">#" + record.invoices[i].invoiceId + "</a>" + (i != (record.invoices.length - 1) ? ", " : "");
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
				fire : function() {
					location.href = "<%=absoluteURL %>/account-make-payment?account_id=<%=accountId %>";
				}
			}, 
			{
				label : "More actions",
				actions : [
					{ 
						label : "Add credit", 
						fire : function() { 
						
							
							$('#placeholder_debit').hide();
							$('#placeholder_apply_promotional_code').hide();
							$('#placeholder_billing_modification').hide();
							$('#placeholder_manual_transaction_handling').hide();
							
							// show credit
							$('#placeholder_credit').show();
							
							// clear prev values
							$('#text_credit_amount').val("");
							$('#textarea_credit_comments').val("");
							
							
							var v = null;
							v = new validator({
								elements : [
									{
										element : $('#text_credit_amount'),
										status : $('#status_credit_amount'),
										rules : [
											{ method : 'required', message : 'This field is required.' },
											/* { method : 'number', message : 'Please enter a valid number.' }, */
											{ method : 'currency', message : 'Please enter a valid currency value.' },
											{ method : 'min', pattern : 0.01, message : 'Please enter a value greater than or equal to {0}.' }
										]
									},
									{
										element : $('#textarea_credit_comments'),
										status : $('#status_credit_comments'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : $('#button_credit_submit'),
								messages : null,
								accept : function () {
									
									credit({
										accountId : accountId,
										amount : $.trim($('#text_credit_amount').val()),
										comments : $('#textarea_credit_comments').val(),
										success : function() {
											
											$('#placeholder_credit').hide();
											
											getTransactions({
												accountId : accountId,
												groupBy : "month",
												success : function(data) {
													transactions = data.list;
													renderTableTransactions();
													
													getAccountDetails({
														accountId : accountId,
														success : function(data) {
															balance = data.balance;
															$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
														},
														error : function() {
															
														}
													});
													
												},
												error : function(error) {
													transactions = [];
													renderTableTransactions();
												}
											});
											
										},
										error : function() {
											alert("ERR");
										}
									});
									
								},
								error : function() {
										
								}
							});
							
							// cancel
							$('#button_credit_cancel').unbind('click').click(function() {
								$('#placeholder_credit').hide();
							});
							
						} 
					},
					{
						label : "Debit",
						fire : function() {
							
							
							$('#placeholder_credit').hide();
							$('#placeholder_apply_promotional_code').hide();
							$('#placeholder_billing_modification').hide();
							$('#placeholder_manual_transaction_handling').hide();
							
							// show debit
							$('#placeholder_debit').show();
							
							// clear prev values
							$('#text_debit_amount').val("");
							$('#textarea_debit_comments').val("");
							$('#label_debit_max').text("$" + (balance).formatCurrency(2, '.', ',')); // updated balance
							
							
							var v = null;
							v = new validator({
								elements : [
									{
										element : $('#text_debit_amount'),
										status : $('#status_debit_amount'),
										rules : [
											{ method : 'required', message : 'This field is required.' },
											{ method : 'currency', message : 'Please enter a valid currency value.' },
											{ method : 'min', pattern : 0.01, message : 'Please enter a value greater than or equal to {0}.' },
											{ method : 'max', pattern : (balance).formatCurrency(2, '.', ','), message : 'Please enter a value less than or equal to {0}.'}
										]
									},
									{
										element : $('#textarea_debit_comments'),
										status : $('#status_debit_comments'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : $('#button_debit_submit'),
								messages : null,
								accept : function () {
									
									debit({
										accountId : accountId,
										amount : $.trim($('#text_debit_amount').val()),
										comments : $('#textarea_debit_comments').val(),
										success : function() {
											
											$('#placeholder_debit').hide();
											
											getTransactions({
												accountId : accountId,
												groupBy : "month",
												success : function(data) {
													transactions = data.list;
													renderTableTransactions();
													
													getAccountDetails({
														accountId : accountId,
														success : function(data) {
															balance = data.balance;
															$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
														},
														error : function() {
															
														}
													});
													
												},
												error : function(error) {
													transactions = [];
													renderTableTransactions();
												}
											});
											
										},
										error : function() {
											alert("ERR");
										}
									});
									
								},
								error : function() {
										
								}
							});
							
							
							// cancel
							$('#button_debit_cancel').unbind('click').click(function() {
								$('#placeholder_debit').hide();
							});
							
						}
					},
					{
						label : "Manual transaction handling", 
						fire : function() {
							
							$('#placeholder_credit').hide();
							$('#placeholder_debit').hide();
							$('#placeholder_apply_promotional_code').hide();
							$('#placeholder_billing_modification').hide();
							
							// show manual transaction handling
							$('#placeholder_manual_transaction_handling').show();
							
							
							$('#select_manual_transaction_handling_transaction_type')
								.unbind('change')
								.change(function() {
									
									if($(this).val() == 9) {
										
										// refund
										
										$('#section_payment').hide();
										$('#section_refund').show();
										
										$('#text_refund_amount').val("").removeClass('predefined invalid valid');
										$('#textarea_refund_comments').val("").removeClass('predefined invalid valid');
										
										var v = null;
										v = new validator({
											elements : [
												{
													element : $('#text_refund_amount'),
													status : $('#status_refund_amount'),
													rules : [
														{ method : 'required', message : 'This field is required.' },
														{ method : 'currency', message : 'Please enter a valid currency value.' },
														{ method : 'min', pattern : 0.01, message : 'Please enter a value greater than or equal to {0}.' }
													]
												},
												{
													element : $('#textarea_refund_comments'),
													status : $('#status_refund_comments'),
													rules : [
														{ method : 'required', message : 'This field is required.' }
													]
												}
											],
											submitElement : $('#button_refund_submit'),
											messages : null,
											accept : function () { 
												
												manualRefund({
													accountId : accountId,
													amount : $.trim($('#text_refund_amount').val()),
													comments : $('#textarea_refund_comments').val(),
													success : function() {
														
														$('#placeholder_billing_modification').hide();
														
														getTransactions({
															accountId : accountId,
															groupBy : "month",
															success : function(data) {
																transactions = data.list;
																renderTableTransactions();
																
																getAccountDetails({
																	accountId : accountId,
																	success : function(data) {
																		balance = data.balance;
																		$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
																	},
																	error : function() {
																		
																	}
																});
																
															},
															error : function(error) {
																transactions = [];
																renderTableTransactions();
															}
														});
														
													},
													error: function() {
													
														alert("ERR");
														
													}
												});
												
											}
										});
										
										// cancel
										$('#button_refund_cancel')
											.unbind('click')
											.click(function() {
											$('#placeholder_manual_transaction_handling').hide();
										});
										
									}
									
									if($(this).val() == 2) {
										
										
										// payment
										
										$('#section_payment').show();
										$('#section_refund').hide();
										
										$('#text_payment_amount').val("").removeClass('predefined invalid valid');
										$('#textarea_payment_comments').val("").removeClass('predefined invalid valid');
										
										var v = null;
										v = new validator({
											elements : [
												{
													element : $('#text_payment_amount'),
													status : $('#status_payment_amount'),
													rules : [
														{ method : 'required', message : 'This field is required.' },
														{ method : 'currency', message : 'Please enter a valid currency value.' },
														{ method : 'min', pattern : 0.01, message : 'Please enter a value greater than or equal to {0}.' }
													]
												},
												{
													element : $('#textarea_payment_comments'),
													status : $('#status_payment_comments'),
													rules : [
														{ method : 'required', message : 'This field is required.' }
													]
												}
											],
											submitElement : $('#button_payment_submit'),
											messages : null,
											accept : function () { 
												
												manualPayment({
													accountId : accountId,
													amount : $.trim($('#text_payment_amount').val()),
													comments : $('#textarea_payment_comments').val(),
													success : function() {
														
														$('#placeholder_billing_modification').hide();
														
														getTransactions({
															accountId : accountId,
															groupBy : "month",
															success : function(data) {
																transactions = data.list;
																renderTableTransactions();
																
																getAccountDetails({
																	accountId : accountId,
																	success : function(data) {
																		balance = data.balance;
																		$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
																	},
																	error : function() {
																		
																	}
																});
																
															},
															error : function(error) {
																transactions = [];
																renderTableTransactions();
															}
														});
														
													},
													error: function() {

														alert("ERR");

													}
												});
												
											}
										});
										
										// cancel
										$('#button_payment_cancel')
											.unbind('click')
											.click(function() {
											$('#placeholder_manual_transaction_handling').hide();
										});
										
										
										
									}
									
							});
							
							
							$('#select_manual_transaction_handling_transaction_type')[0].selectedIndex = 0;
							$('#select_manual_transaction_handling_transaction_type').trigger('change');
							
							
							/*
							$('#select_refund_related_transaction').empty();
							if(allowRefundTransactions.length != 0) {
								var e = $('#select_refund_related_transaction')[0].options;
						        var a = new Option("", "");
						        try {
					                e.add(a);
					            } catch (ex) {
					                e.add(a, null);
					            }
								for (var i = 0; i < allowRefundTransactions.length; i++) {
						            //var k = new Option("#" + allowRefundTransactions[i].transactionId + " - Payment: " + (allowRefundTransactions[i].creditCard != undefined ? getCreditCardType(allowRefundTransactions[i].creditCardTypeId) + " ****" + allowRefundTransactions[i].creditCard : "") + " - " + allowRefundTransactions[i].amount, allowRefundTransactions[i].transactionId);
						            var k = new Option(allowRefundTransactions[i].transactionId, allowRefundTransactions[i].transactionId);
						            try {
						                e.add(k);
						            } catch (ex) {
						                e.add(k, null);
						            }
						        }	
							}
							*/
							
						}
					},
					{ 
						label : "Apply a promotional code",
						active : false,
						fire : function() { 
						
							
							$('#placeholder_credit').hide();
							$('#placeholder_debit').hide();
							$('#placeholder_billing_modification').hide();
							$('#placeholder_manual_transaction_handling').hide();
							
							// show apply promotional code
							$('#placeholder_apply_promotional_code').show();
							
							$('#button_apply_promotion_code_submit').unbind('click').click(function() {
								
								
								// validation for promotion code
								
								// do action
								$('#placeholder_apply_promotional_code').hide();
								
								getTransactions({
									accountId : accountId,
									groupBy : "month",
									success : function(data) {
										transactions = data.list;
										renderTableTransactions();
										
										
										getAccountDetails({
											accountId : accountId,
											success : function(data) {
												balance = data.balance;
												$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
											},
											error : function() {
												
											}
										});
										
										
									},
									error : function(error) {
										transactions = [];
										renderTableTransactions();
									}
								});
								
								
								
								
							});
							
							$('#button_apply_promotion_code_cancel').unbind('click').click(function() {
								$('#placeholder_apply_promotional_code').hide();
							});
							
						} 
					},
					{ 
						label : "Request an account billing modification", 
						fire : function() { 
							alert("OK") 
						} 
					}
				]
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
var balance = 0;

$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			// details
			// TODO: change flag isActive to statusId
			$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getAccountStatus(data.isActive ? 1 : 2) + "</li>").appendTo('#list_account_details');
			
			
			
			balance = data.balance;
			$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
			
			
			getTransactions({
				accountId : accountId,
				groupBy : "month",
				success : function(data) {
					transactions = data.list;
					/*
					for(var t = 0; t < data.list.length; t++) {
						if(data.list[t].typeId == 2) {
							allowRefundTransactions.push(data.list[t]);
						}
					}
					*/
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
		
		}
	});

	
});
</script>