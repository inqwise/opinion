
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
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
%>

<div>
	<h1>Dashboard</h1>
	<div>
		
		<div style="height: 120px"></div>
	
		<div style="clear: both; overflow: auto;">
			<div style="float: left; width: 50%">
				<div style="margin-right: 5px;">
					<h3 class="ui-header-light">Today At a Glance</h3>
					<div style="padding-top: 12px">
						<div id="table_users"></div>
					</div>
				</div>
			</div>
			<div style="float: left; width: 50%">
				<div style="margin-left: 5px;">
					<h3 class="ui-header-light">Active Users</h3>
					<div style="padding-top: 12px">
						<div id="table_active_users"></div>
					</div>
				</div>
			</div>
		</div>
			
	</div>
</div> 

<script type="text/javascript">
var getUsers = function(params) {
	
	var obj = {
		users : {
			getUsers : { 
				fromDate : params.fromDate 
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
		pagingStart : 100,
		disablePaging : true,
		disableFiltering : true
	});
};

var tableActiveUsers = null;
var activeUsers = [];
var renderTableActiveUsers = function() {
	
	tableUserHistory = $('#table_active_users').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'userId', label : 'Username', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/user-details?user_id=" + record.userId  + "\" title=\"" + record.userName + "\">" + record.userName + "</a>");	
			}},
			{ key : 'typeId', label : 'Operation Type', sortable : true, formatter: function(cell, value, record, source) {
				return record.typeName;	
			}},
			{ key: 'countryName', label: 'Country', sortable: true,  width: 124 },
            { key: 'clientIp', label: 'IP Address', sortable: true,  width: 86 },
			{ key : 'insertDate', label : 'Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.insertDate) {
					var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
					var right = record.insertDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : activeUsers, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};

var now = new Date();
var before = new Date();
	before.setDate(now.getDate()-1);
	
var dateRange = [before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)];
	
$(function() {

	getUsers({
		fromDate : dateRange[0]  + ' 00:00',
		success : function(data) {
			users = data.list;
			renderTableUsers();
		},
		error : function(error) {
			users = [];
			renderTableUsers();
		}
	});
	
	getUserHistory({
		typeIds: [1, 6],
		productId: 1,
		success : function(data) {
			activeUsers = data.list;
			renderTableActiveUsers();
		},
		error : function(error) {
			activeUsers = [];
			renderTableActiveUsers();
		}
	});
	
});

var getUserHistory = function(params) {
	
	var obj = {
		users : {
			getHistory : {
				typeIds : params.typeIds,
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
        	if(data.users.getHistory != undefined) {
	        	if(data.users.getHistory.error != undefined) {
					
	        		errorHandler({
						error : data.users.getHistory.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.users.getHistory);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.users.getHistory);
					}
				}
        	} else {
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error();
				}
        	}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

</script>