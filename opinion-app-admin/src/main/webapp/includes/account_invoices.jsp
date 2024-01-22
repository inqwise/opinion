
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
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
					<li><a href="<%=absoluteURL %>/account-transaction-history?account_id=<%=accountId %>" title="Transaction History"><span>Transaction History</span></a></li>
					<li><a href="<%=absoluteURL %>/account-make-payment?account_id=<%=accountId %>" title="Make a Payment"><span>Make a Payment</span></a></li>
					<li><a href="<%=absoluteURL %>/account-charges?account_id=<%=accountId %>" title="Charges"><span>Charges</span></a></li>
					<li><a href="<%=absoluteURL %>/account-recurring?account_id=<%=accountId %>" title="Recurring"><span>Recurring</span></a></li>
					<li><a href="<%=absoluteURL %>/account-uninvoiced-list?account_id=<%=accountId %>" title="UnInvoiced List"><span>UnInvoiced List</span></a></li>
					<li><a href="<%=absoluteURL %>/account-invoices?account_id=<%=accountId %>" title="Invoices" class="selected"><span>Invoices</span></a></li>
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

var deleteInvoices = function(params) {

	var obj = {
		invoices : {
			deleteInvoices : {
				invoices : params.invoices
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
        	if(data.invoices.deleteInvoices.error != undefined) {
				
        		errorHandler({
					error : data.invoices.deleteInvoices.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.invoices.deleteInvoices);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.invoices.deleteInvoices);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var createEmptyInvoice = function(params) {
	
	var obj = {
		invoices : {
			createEmptyInvoice : {
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
        	if(data.invoices.createEmptyInvoice.error != undefined) {
				
        		errorHandler({
					error : data.invoices.createEmptyInvoice.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.invoices.createEmptyInvoice);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.invoices.createEmptyInvoice);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

/*
var recalculateInvoices = function(params) {

	var obj = {
		accounts : {
			recalculateInvoices : {
				list : params.list
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
        	if(data.accounts.recalculateInvoices.error != undefined) {
				if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.recalculateInvoices);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.recalculateInvoices);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};
*/

var getInvoiceStatus = function(statusId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = "Draft";
			break;
		case 2 : 
			s = "Open";
			break;
	}
	return s;
};

/*
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
 */

var invoices = [];
var tableInvoices = null;
var filterStatusId = null;

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
				return $("<a href=\"<%=absoluteURL %>/account-invoice?account_id=" + accountId  + "&invoice_id=" + record.invoiceId + "\" title=\"Invoice #" + record.invoiceId + "\">Invoice #" + record.invoiceId + "</a>");
			}},
			{ key : 'statusId', label : 'Status', sortable : true, width: 86, formatter: function(cell, value, record, source) {
				return getInvoiceStatus(record.statusId);	
			}},
			{ key : 'amount', label : 'Amount', sortable : true, width: 74, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "$" + (record.amount).formatCurrency(2, '.', ',');		
			}}
		],
		dataSource : invoices,  
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
           {
        	   label : "Create Invoice",
        	   icon : "add-white",
				color : "green",
        	   fire : function() {
        		   
        		   createEmptyInvoice({
   						accountId : accountId,
   						success : function(data) {
   						
   							if(data.invoiceId != undefined) {
   								location.href = "<%=absoluteURL %>/account-invoice?account_id=" + accountId  + "&invoice_id=" + data.invoiceId;
   							}
   						
   						},
   						error: function() {
   							alert("ERR");
   						}
   					});
        		   
        	   }
           },
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {
				
					if(records.length > 0) {
				
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].invoiceId);
						});
				
						
						deleteInvoices({
							invoices : list,
							success : function() {
								
								// tableProducts.deleteRecords(records);
								
								getInvoices({
									accountId : accountId,
									statusId : null,
									success : function(data) {
										invoices = data.list;
										renderTableInvoices();
									},
									error : function(error) {
										invoices = [];
										renderTableInvoices();
									}
								
								});
								
							},
							error: function() {
								alert("ERR");
							}
						});
						
						
					}
					
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

var accountId = <%=accountId %>;

$(function() {
	
	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			// details
			// TODO: change flag isActive to statusId
			$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getAccountStatus(data.isActive ? 1 : 2) + "</li>").appendTo('#list_account_details');
			
			
			// invoices
			getInvoices({
				accountId : accountId,
				statusId : null,
				success : function(data) {
					invoices = data.list;
					renderTableInvoices();
				},
				error : function(error) {
					invoices = [];
					renderTableInvoices();
				}
			
			});
		
		}
	});
	
});

</script>