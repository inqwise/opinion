
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
	
	String userId = request.getParameter("user_id");
%>

<div>
	<h1><a href="<%=absoluteURL %>/users" title="Users">Users</a>&nbsp;&rsaquo;&nbsp;<span id="header_user_name"></span></h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL%>/user-details?user_id=<%=userId%>" title="User Details"><span>User Details</span></a></li>
						<li><a href="<%=absoluteURL%>/user-messages?user_id=<%=userId%>" title="Messages"><span>Messages</span></a></li>
						<li><a href="<%=absoluteURL%>/user-accounts?user_id=<%=userId%>" title="Related Accounts"><span>Related Accounts</span></a></li>
						<li><a href="<%=absoluteURL%>/user-history?user_id=<%=userId%>" title="History" class="selected"><span>History</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			<div id="table_user_history"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
var getUserDetails = function(params) {

	var obj = {
		users : {
			getUserDetails : {
				userId : params.userId
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
			if(data.users.getUserDetails != undefined) {
				if(data.users.getUserDetails.error != undefined) {
					
					errorHandler({
						error : data.users.getUserDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.users.getUserDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.users.getUserDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getUserHistory = function(params) {
	
	var obj = {
		users : {
			getHistory : {
				userId : params.userId
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

var getOperationType = function(typeId) {
	var s = "";
	switch(typeId) {
		case 1 :
			s = "Login";
			break;
		case 2 : 
			s = "Reset password";
			break;
		case 3 : 
			s = "Register";
			break;
		case 4 : 
			s = "Change password";
			break;
		case 5 : 
			s = "Payment";
			break;
		case 6 : 
			s = "Auto Login";
			break;
	}
	return s;
};

var getSourceName = function(sourceId) {
	var s = "";
	switch(sourceId) {
		case 1 :
			s = "Opinion";
			break;
		case 3 : 
			s = "BackOffice";
			break;
		case 5 :
			s = "Api";
			break;
	}
	return s;
};

var tableUserHistory = null;
var userHistory = [];
var renderTableUserHistory = function() {
	
	tableUserHistory = $('#table_user_history').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'typeId', label : 'Operation Type', sortable : true, formatter: function(cell, value, record, source) {
				return getOperationType(record.typeId);	
			}},
			{ key: 'sourceId', label: 'Source', sortable: true,  width: 86, formatter: function(cell, value, record, source) {
				return getSourceName(record.sourceId);	
			}},
			{ key: 'countryName', label: 'Country', sortable: true,  width: 124 },
            { key: 'clientIp', label: 'IP Address', sortable: true,  width: 86 },
			{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.insertDate) {
					var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
					var right = record.insertDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : userHistory, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};

var userId = <%= userId %>;

$(function() {

	getUserDetails({
		userId : userId,
		success : function(data) {

			$('#level1').text(data.email);
			$('#header_user_name').text(data.userName);
			
			getUserHistory({
				userId : userId,
				success : function(data) {
					userHistory = data.list;
					renderTableUserHistory();
				},
				error : function() {
					userHistory = [];
					renderTableUserHistory();
				}
			});
			
			
		}
	});
	
});
</script>
