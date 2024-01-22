
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String invoiceId = request.getParameter("invoice_id");
%>

<div>
	<h1><a href="<%=absoluteURL %>/account" title="Account">Account</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/invoices" title="Invoices">Invoices</a>&nbsp;&rsaquo;&nbsp;Invoice #<%=invoiceId %></h1>
	<div>
		<div style="clear: both; Xpadding-top: 24px;">
			<div class="wrapper-content-left">
				
				<!--
				<div>
					<img src="<%=applicationURL%>/images/logo13wf.png" alt="Inqwise" />
				</div>
				-->
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
							<li>Invoice Id: <b style="color: #333"><%=invoiceId %></b></li>
							<li>Invoice Date: <b style="color: #333" id="label_invoice_date"></b></li>
							<li>Billing Period: <b style="color: #333" id="label_invoice_billing_period"></b></li>
							<li>Billing Id: <b style="color: #333" id="label_account_id"></b></li>
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
				
				
				
				<div style="padding-bottom: 24px;">
					<h3 class="ui-header-light">Bill To:</h3>
					<div style="padding-top: 12px;" id="label_invoice_bill_to"></div>
				</div>
								
				<div style="clear: both;">
					<h3 class="ui-header-light">Charges</h3>
						<div style="padding-top: 12px;">
						<table class="ti">
							<thead>
								<tr>
									<th>Date</th>
									<th>Item</th>
									<th>Description</th>
									<th>Charge #</th>
									<th class="align-right" style="width:74px;">Amount</th>
								</tr>
							</thead>
							<tbody id="table_invoice_charges_body"></tbody>
							<tfoot>
								<tr>
									<td class="align-right" colspan="4">&nbsp;</td>
									<td class="align-right cell-sub-total">&nbsp;</td>
								</tr>
								<tr>
									<td class="align-right label-total" colspan="4"><b>Total Charges:</b></td>
									<td class="align-right cell-total" id="label_invoice_total_charges">$0.00</td>
								</tr>
							</tfoot>
						</table>
					</div>
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
				
				<div style="clear: both; padding-top: 48px;">
					* May include estimated US sales tax, VAT and GST<br/>
					** This is not a VAT invoice<br/>
					All charges and prices are in US Dollars<br/><br/>
					All services are sold by Inqwise, Ltd.<br/>
					The above charges include charges incurred by your account as well as by all accounts you are responsible for through Consolidated Billing.<br/><br/>
					Thank you for using Inqwise.<br/>
					Sincerely,<br/>
					The Inqwise Team
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

//var accountId = auth.userInfo.accountId;
var invoiceId = <%=invoiceId%>;

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
	
	
	getInvoiceDetails({
		invoiceId : invoiceId,
		success : function(data) {
			
			
			
			// details
			
			
			$('#label_invoice_date').text(data.invoiceDate);
			$('#label_invoice_billing_period').text(data.fromDate + " - " + data.toDate);
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
			
			
			
			/*
			$('#text_organization_name').val(data.companyName);
			
			$('#text_first_name').val(data.firstName);
			$('#text_last_name').val(data.lastName);

			$('#text_address1').val(data.address1);
			$('#text_address2').val(data.address2);
			$('#text_city').val(data.city);

			if(data.countryId != 0) {
				$('#select_country option[value="' + data.countryId + '"]').prop('selected', true);
				if(data.countryId == 232) {
					$('#params_state').show();
					$('#params_province').hide();
					$('#select_state option[value="' + data.stateId + '"]').prop('selected', true);
				} else if (data.countryId == 39) {
					$('#params_state').hide();
					$('#params_province').show();
					$('#select_province option[value="' + data.stateId + '"]').prop('selected', true);
				} else {
					$('#params_state').hide();
					$('#params_province').hide();
				}
			}

			$('#text_zip').val(data.postalCode);
			$('#text_phone1').val(data.phone1);
			*/
			
			
			
			
			
			
			
			
			
			
			//$('#label_invoice_sub_total').text((data.subTotal).formatCurrency(2, '.', ','));
			
			
			
			
			
			/*
			$('#label_invoice_remaining_balance').text((data.balance).formatCurrency(2, '.', ','));
			*/
			
			
			
			
			// charges
			if(data.charges.list.length != 0) {
				for(var i = 0; i < data.charges.list.length; i++) {
					$("<tr>" +
						"<td>" + data.charges.list[i].chargeDate + "</td>" +
						"<td>" + data.charges.list[i].name + "</td>" +
						"<td>" + (data.charges.list[i].description != undefined ? data.charges.list[i].description : "") + "</td>" +
						"<td>" + data.charges.list[i].chargeId + "</a></td>" +
						"<td class=\"align-right\">$" + (data.charges.list[i].amount).formatCurrency(2, '.', ',') + "</td>" +
					"</tr>").appendTo("#table_invoice_charges_body");
				}
			} else {
				$("<tr class=\"nop\"><td colspan=\"5\"><b>No records found.</b></td></tr>").appendTo("#table_invoice_charges_body");
			}
			
			// total charges
			$('#label_invoice_total_charges').text("$" + (data.amount).formatCurrency(2, '.', ',')); // subTotal + VAT (TAX or Discount)
			
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
		
	
});
</script>