
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
%>

<div>
	<h1><a href="<%=absoluteURL %>/account" title="Account">Account</a>&nbsp;&rsaquo;&nbsp;Invoices</h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/account" title="Details"><span>Details</span></a></li>
						<li id="tab_billing_profile"><a href="<%=absoluteURL %>/billing-profile" title="Billing Profile"><span>Billing Profile</span></a></li>
						<li id="tab_transaction_history"><a href="<%=absoluteURL %>/transaction-history" title="Transaction History"><span>Transaction History</span></a></li>
						<li id="tab_invoices"><a href="<%=absoluteURL %>/invoices" title="Receipts &amp; Invoices" class="selected"><span>Receipts &amp; Invoices</span></a></li>
						<li id="tab_upgrade"><a href="<%=absoluteURL %>/upgrade" title="Upgrade / Change Plan"><span>Upgrade / Change Plan</span></a></li>
						<li id="tab_users"><a href="<%=absoluteURL %>/users" title="Users"><span>Users</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			<div id="table_invoices"></div>
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

var getInvoices = function(params) {

	var obj = {
		invoices : {
			getInvoices : {
				accountId : params.accountId,
				statusId : params.statusId
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
        	if(data.invoices.getInvoices.error != undefined) {
				
        		errorHandler({
					error : data.invoices.getInvoices.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.invoices.getInvoices);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.invoices.getInvoices);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

//var accountId = auth.userInfo.accountId;

var invoices = [];
var tableInvoices = null;

var renderTableInvoices = function() {
	
	$('#table_invoices').empty();
	tableInvoices = $('#table_invoices').dataTable({
		tableColumns : [
			{ key : 'invoiceId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'invoiceDate', label : 'Invoice Date', sortable: true, sortBy : { dataType: "date" }, width: 86 },
			{ key : 'fromDate', label : 'Billing Period', sortable: true, sortBy : { dataType: "date" }, width: 160, formatter: function(cell, value, record, source) {
				return record.fromDate + " - " + record.toDate;
			}},
			{ key : 'invoiceId', label : 'Invoice', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/invoices/" + record.invoiceId  + "\" title=\"Invoice #" + record.invoiceId + "\">Invoice #" + record.invoiceId + "</a>");
			}},
			{ key : 'amount', label : 'Amount', sortable : true, width: 74, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "$" + (record.amount).formatCurrency(2, '.', ',');	
			}}
		],
		dataSource : invoices,  
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
				
				$("#tab_billing_profile, #tab_transaction_history, #tab_invoices").show();
				
				getInvoices({
					accountId : accountId,
					statusId : 2, // Open
					success : function(data) {
						invoices = data.list;
						renderTableInvoices();
					},
					error : function(error) {
						invoices = [];
						renderTableInvoices();
					}
				
				});
				
				if(auth.userInfo.plan != undefined) {
					if(auth.userInfo.plan.isFree) {
						if(auth.userInfo.permissions.makePayment) {
							$('#tab_upgrade').show();
						}
					}
				}
				
			}
	
		},
		error: function() {
			
		}
	});
	
});
</script>