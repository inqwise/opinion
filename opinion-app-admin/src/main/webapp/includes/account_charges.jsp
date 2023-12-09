
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
					<li><a href="<%=absoluteURL %>/account-transaction-history?account_id=<%=accountId %>" title="Transaction History"><span>Transaction History</span></a></li>
					<li><a href="<%=absoluteURL %>/account-make-payment?account_id=<%=accountId %>" title="Make a Payment"><span>Make a Payment</span></a></li>
					<li><a href="<%=absoluteURL %>/account-charges?account_id=<%=accountId %>" title="Charges" class="selected"><span>Charges</span></a></li>
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
		<div id="table_charges"></div>
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

var getCharges = function(params) {

	var obj = {
		charges : {
			getCharges : {
				accountId : params.accountId,
				statusId : params.statusId,
				invoiced : params.invoiced
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
        	if(data.charges.getCharges.error != undefined) {
				
        		errorHandler({
					error : data.charges.getCharges.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.charges.getCharges);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.charges.getCharges);
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

/*
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
*/

var accountId = <%= accountId %>;


var charges = [];
var tableCharges = null;
var renderTableCharges = function() {
	
	$('#table_charges').empty();
	tableCharges = $('#table_charges').dataTable({
		tableColumns : [
			{ key : 'chargeId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'chargeDate', label : 'Charge Date', sortable: true, sortBy : { dataType: "date" }, width: 126 },
			{ key : 'name', label : 'Charge Name', sortable : true, formatter : function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-charge?account_id=<%= accountId %>&charge_id=" + record.chargeId  + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key : 'amount', label : 'Amount', sortable: true, width: 74, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "$" + (record.amount).formatCurrency(2, '.', ',');
			}}
		],
		dataSource : charges, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{ 
				label : "Cancel",
				disabled : true,
				condition : 1,
				fire : function(records, source) {
					
					cancelCharge({
						accountId : accountId,
						chargeId : records[0].chargeId,
						success : function(data) {
							
							getCharges({
								accountId : accountId,
								statusId : 2,
								success : function(data) {
									charges = data.list;
									renderTableCharges();
								},
								error: function() {
									charges = [];
									renderTableCharges();
								}
							});
							
						},
						error: function(error) {
							
							alert("ERR -> " + JSON.stringify(error));
							
						}
					});				
					
					/*
					if(records.length > 0) {
	
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].chargeId);
						});
						
					}
					*/
					
				}
			}
		]
	});
	
};


$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			// details
			// TODO: change flag isActive to statusId
			$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getAccountStatus(data.isActive ? 1 : 2) + "</li>").appendTo('#list_account_details');
			
			
			
			// all paid -> charges
			getCharges({
				accountId : accountId,
				statusId : 2,
				success : function(data) {
					charges = data.list;
					renderTableCharges();
				},
				error: function() {
					charges = [];
					renderTableCharges();
				}
			});
			
		
		}
	});
	
});
</script>