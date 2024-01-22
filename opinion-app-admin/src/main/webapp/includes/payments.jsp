
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
	<h1>Payments</h1>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/payments" class="selected" title="Payments"><span>Payments</span></a></li>
					<li><a href="<%=absoluteURL %>/direct-payment" title="Direct Payment"><span>Direct Payment</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
		<div class="content-include-tabs">
			<!--
			<ul class="content-include-tabs-list">
				<li class="first-item"><a href="<%=absoluteURL %>/details" title="Details" class="selected"><span>Details</span></a></li>
			</ul>
			-->
		</div>	
	</div>
	<div>
		<div id="table_payments"></div>
	</div>
</div>

<script type="text/javascript">
/*
var getPayments = function(params) {

	var obj = {
		getPayments : {}
	};

	$.ajax({
        url: servletUrl,
        data: { 
        	rq : JSON.stringify(obj),
        	timestamp : $.getTimestamp()  
        },
        dataType: "json",
        success: function (data, status) {
			if(data.getPayments.list != undefined) {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.getPayments);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};
*/

var tablePayments = null;
$(document).ready(function() {
	//getPayments({
	//	success : function(data) {			
			tablePayments = $('#table_payments').dataTable({
				tableColumns : [
					{ key : 'paymentId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
					{ key : 'description', label : 'Description', sortable: true },
					{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 124, formatter: function(cell, value, record, source) {
						if(record.insertDate) {
							var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
							var right = record.insertDate.replace(left, "");
							return left + "<b class=\"hours\">" + right + "</b>";
						} 
		            }},
				],
				dataSource : [], 
				pagingStart : 10,
				show : [5, 10, 25, 50, 100]
			});
	//	}
	//});
});
</script>
