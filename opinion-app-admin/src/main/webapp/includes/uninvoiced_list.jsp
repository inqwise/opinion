
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.opinion.cms.common.IPage"%>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1>UnInvoiced List</h1>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/invoice-list" title="Invoice List"><span>Invoice List</span></a></li>
					<li><a href="<%=absoluteURL %>/uninvoiced-list" title="UnInvoiced List" class="selected"><span>UnInvoiced List</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="padding-top: 20px;">
		<div id="table_uninvoiced_list"></div>
	</div>
</div>

<script type="text/javascript">
var getCharges = function(params) {

	var obj = {
		charges : {
			getCharges : {
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

var charges = [];
var tableUnInvoicedList = null;
var renderTableCharges = function() {
	$('#table_uninvoiced_list').empty();
	tableUnInvoicedList = $('#table_uninvoiced_list').dataTable({
		tableColumns : [
			{ key : 'chargeId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'chargeDate', label : 'Charge Date', sortable: true, sortBy : { dataType: "date" }, width: 126 },
            { key : 'name', label : 'Charge Name', sortable : true, formatter : function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-charge?account_id=" + record.accountId + "&charge_id=" + record.chargeId  + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key : 'accountId', label : 'Account', sortable : true, width: 160, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-uninvoiced-list?account_id=" + record.accountId  + "\" title=\"" + record.accountName + "\">" + record.accountName + "</a>");
			}},
			{ key : 'amount', label : 'Amount', sortable : true, width: 74, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "$" + (record.amount).formatCurrency(2, '.', ',');
			}}
		],
		dataSource : charges, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};

$(function() {
	
	getCharges({
		statusId : 2,
		invoiced : false,
		success : function(data) {
			charges = data.list;
			renderTableCharges();
		},
		error: function() {
			charges = [];
			renderTableCharges();
		}
	});
	
});
</script>