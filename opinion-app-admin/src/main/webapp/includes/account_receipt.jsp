
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%@ page import="com.inqwise.opinion.library.common.errorHandle.OperationResult" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String accountId = request.getParameter("account_id");
	String receiptId = request.getParameter("receipt_id");
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">			
				<h1><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" id="header_account_name"></a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-invoices?account_id=<%=accountId %>" title="Invoices">Invoices</a>&nbsp;&rsaquo;&nbsp;Receipt #<%=receiptId %></h1>
			</td>
			<td class="cell-right">
				
			</td>
		</tr>
	</table>
	<div>
		<div style="clear: both;">
			<div class="wrapper-content-left">
				
				
				<div style="padding-top: 12px">
					<div>
						Inqwise, Ltd.<br/>
						4G/9 Brener<br/>
						Bat Yam 59486<br/>
						Israel
					</div>
				</div>
				
				<div style="padding-top: 12px">
					<b>VAT Number</b> 514943901
				</div>
				
				<div style="clear: both; overflow: hidden;">
					<div style="float: left; width: 220px;">
						<h3 class="ui-header-light">Invoice Details</h3>
						<ul class="ll" style="margin-left: 0px;">
							<li>Receipt Id: <b style="color: #333"><%=receiptId %></b></li>
							<li>Payment Date: <b style="color: #333" id="label_invoice_date"></b></li>
							<li>Billing Id: <b style="color: #333" id="label_account_id"></b></li>
							<li>Form of payment: <b style="color: #333" id="label_form_of_payment"></b></li>
						</ul>
					</div>
					<div style="float: left;width: 220px; padding-left: 10px;">
						<h3 class="ui-header-light"></h3>
						<!--
						<ul style="margin-left: 0px;" class="ll">
							<li></li>
						</ul>
						-->
					</div>
				</div>
				
				<div style="padding-bottom: 20px;">
					<h3 class="ui-header-light">Bill To:</h3>
					<div style="padding-top: 12px;" id="label_invoice_bill_to"></div>
				</div>
				
				<div style="clear: both; padding-top: 24px;">
					<h3 class="ui-header-light">Payments</h3>
					<div style="padding-top: 12px;">
						<table class="ti">
							<thead>
								<tr>
									<th>Transaction Date</th>
									<th>Transaction Id</th>
									<th>Description</th>
									<th class="align-right" style="width:74px;">Amount</th>
								</tr>
							</thead>
							<tbody id="table_invoice_transactions_body"></tbody>
							<tfoot>
								<tr>
									<td class="align-right" colspan="3">&nbsp;</td>
									<td class="align-right cell-sub-total">&nbsp;</td>
								</tr>
								<tr>
									<td class="align-right label-total" colspan="3"><b>Total:</b></td>
									<td class="align-right cell-total" id="label_invoice_payments">$0.00</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			
			</div>
			<div class="wrapper-content-middle">&nbsp;</div>
			<div class="wrapper-content-right">&nbsp;</div>
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

var getInvoiceDetails = function(params) {

	var obj = {
		invoices : {
			getInvoiceDetails : {
				accountId : params.accountId,
				invoiceId : params.invoiceId
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
			if(data.invoices.getInvoiceDetails != undefined) {
				if(data.invoices.getInvoiceDetails.error != undefined) {
					
					errorHandler({
						error : data.invoices.getInvoiceDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.invoices.getInvoiceDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.invoices.getInvoiceDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var accountId = <%= accountId %>;
var invoiceId = <%=receiptId%>;

var getInvoiceStatus = function(statusId) {
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
		case 2 : 
			s = "Payment";
			break;
		default :
			s = typeId;
			break;
	}
	return s;
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


$(function() {
	
	
	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			
			
			
			
			
			
			
			
			$('#label_account_id').text(data.accountId);
			
	
	
			getInvoiceDetails({
				accountId : accountId,
				invoiceId : invoiceId,
				success : function(data) {
					
					
					
					// details
					
					
					$('#label_invoice_date').text(data.invoiceDate);
					$('#label_account_id').text(accountId);
					
					/*
					$('#label_invoice_status_id').text(getInvoiceStatus(data.statusId));
					*/
					
					// bill to
					$("<div>" +
						(data.companyName != null ? data.companyName + "<br/>" : "") +
						data.firstName + " " + data.lastName + "<br/>" +
						data.address1 + "<br/>" +
						(data.address2 != null ? data.address2 + "<br/>" : "") +
						data.city + (data.stateId != null ? ", " + data.stateId : "") + "<br/>" +
						data.postalCode + "<br/>" +
						data.countryId + "<br/>" +
						(data.phone1 != null ? data.phone1 : "") +
					"</div>").appendTo("#label_invoice_bill_to");
					
					// transactions
					if(data.transactions.list.length != 0) {
						for(var t = 0; t < data.transactions.list.length; t++) {
							$("<tr>" +
								"<td>" + data.transactions.list[t].modifyDate + "</td>" +
								"<td>" + data.transactions.list[t].transactionId + "</td>" +
								"<td>" + getTransactionType(data.transactions.list[t].typeId) + ": "  + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" +
								/*"<td>" + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" + */
								"<td class=\"align-right\">$" + (data.transactions.list[t].amount).formatCurrency(2, '.', ',') + "</td>" +
							"</tr>").appendTo("#table_invoice_transactions_body");
						}
					} else {
						$("<tr class=\"nop\"><td colspan=\"4\"><b>No records found.</b></td></tr>").appendTo("#table_invoice_transactions_body");
					}
					
					// total payments / refunds
					$('#label_invoice_payments').text("$" + (data.transactions.totalCredit).formatCurrency(2, '.', ','));
					
					
				},
				error : function(error) {
					//
				}
			});
		
		},
		error : function(error) {
			//
		}
	});
	
});
</script>