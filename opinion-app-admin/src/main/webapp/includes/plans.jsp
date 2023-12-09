
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
	<h1><a href="<%=absoluteURL %>/setup" title="Setup">Setup</a>&nbsp;&rsaquo;&nbsp;Plans</h1>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/setup" title="?----"><span>?----</span></a></li>
					<li><a href="<%=absoluteURL %>/jobs" title="Jobs"><span>Jobs</span></a></li>
					<li><a href="<%=absoluteURL %>/system-events" title="System Events"><span>System Events</span></a></li>
					<li><a href="<%=absoluteURL %>/plans" title="Plans" class="selected"><span>Plans</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both;padding-top: 20px;">
		<div id="table_packages"></div>
	</div>
</div>

<script type="text/javascript">
var getPackages = function(params) {

	var obj = {
		products : {
			getPackages : {
				productId : params.productId
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
			if(data.products.getPackages != undefined) {
				if(data.products.getPackages.error != undefined) {
					
					errorHandler({
						error : data.products.getPackages.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.products.getPackages);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.products.getPackages);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var tablePackages = null;

$(function() {

	getPackages({
		productId : 1,
		success : function(data) {
		
			$('#table_packages').empty();
			tablePackages = $('#table_packages').dataTable({
				tableColumns : [
					{ key : 'packageId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
					{ key : 'packageName', label : 'Plan', sortable: true, width: 220, formatter: function(cell, value, record, source) {
						return $("<a href=\"<%=absoluteURL %>/plan?plan_id=" + record.packageId  + "\" title=\"" + record.packageName + "\">" + record.packageName + "</a>");
					}},
					{ key : 'description', label : 'Description', sortable: true },
					{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
						if(record.insertDate) {
							var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
							var right = record.insertDate.replace(left, "");
							return left + "<b class=\"hours\">" + right + "</b>";
						} 
		            }}
				],
				dataSource : data.list, 
				pagingStart : 10,
				show : [5, 10, 25, 50, 100]
			});
			
		},
		error : function(error) {
			alert(JSON.stringify(error));
		}
	});


});
</script>