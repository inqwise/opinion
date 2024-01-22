
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.inqwise.opinion.cms.common.IPage"%>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1>Invoice List</h1>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/invoice-list" class="selected" title="Invoice List"><span>Invoice List</span></a></li>
					<li><a href="<%=absoluteURL %>/uninvoiced-list" title="UnInvoiced List"><span>UnInvoiced List</span></a></li>
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
var getInvoices = function(params) {

	var obj = {
		invoices : {
			getInvoices : {
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
				return $("<a href=\"<%=absoluteURL %>/account-invoice?account_id=" + record.accountId + "&invoice_id=" + record.invoiceId + "\" title=\"Invoice #" + record.invoiceId + "\">Invoice #" + record.invoiceId + "</a>");
			}},
			{ key : 'accountId', label : 'Account', sortable : true, width: 160, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-invoices?account_id=" + record.accountId  + "\" title=\"" + record.accountName + "\">" + record.accountName + "</a>");
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
								
								getInvoices({
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
		    /*
			{ 
				label : "Recalculate", // Recalculate item rates
				fire : function(records, source) {
				
					// TODO: only for testing
					if(records.length > 0) {
		
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].invoiceId);
						});
						
						var update = function() {
							getInvoices({
								statusId : null,
								success : function(data) {
									invoices = data.list;
									tableInvoices.updateRecords(invoices);
								},
								error : function(error) {
									invoices = [];
									tableInvoices.updateRecords(invoices);
								}
							
							});
						};
						
						recalculateInvoices({
							list : list,
							success : function(data) {
								update();
							},
							error: function(error) {
								update();
							}
						});
						
						
					}
					
				}
			}
		    */
		]
	});
};

$(function() {
	
	getInvoices({
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
	
});

</script>