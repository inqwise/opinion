
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
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
	<h1>Users</h1>
	<div>
		
		<div>
			<div style="clear: both;">
				<div id="table_users"></div>
			</div>
		</div>
		
	</div>
</div>

<script type="text/javascript">
var getUsers = function(params) {
	var obj = {
		users : {
			getUsers : {
				fromDate : params.fromDate,
				toDate : params.toDate
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
        	if(data.users.getUsers.error != undefined) {
				
        		errorHandler({
					error : data.users.getUsers.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.users.getUsers);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.users.getUsers);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var tableUsers = null;
var users = [];
var renderTableUsers = function() {
	
	$('#table_users').empty();
	tableUsers = $('#table_users').dataTable({
		tableColumns : [
			{ key : 'userId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'userName', label : 'Username', sortable : true, formatter : function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/user-details?user_id=" + record.userId  + "\" title=\"" + record.userName + "\">" + record.userName + "</a>");
			}},
			{ key : 'countryName', label : 'Country', sortable : true, width: 124 },
			{ key : 'provider', label : 'Provider', sortable : true, width: 110, formatter : function(cell, value, record, source) {
				return (record.provider != undefined ? record.provider : "");
			}},
			{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.insertDate) {
					var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
					var right = record.insertDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : users, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};


$(function() {
	
	getUsers({
		/* fromDate : newDateRange[0] + ' 00:00',
		toDate : newDateRange[1] + ' 23:59', */
		success : function(data) {
			users = data.list;
			renderTableUsers();
		},
		error : function() {
			users = [];
			renderTableUsers();
		}
	});
	
});
</script>