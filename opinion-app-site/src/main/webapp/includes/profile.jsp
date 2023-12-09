
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>
<%@ page import="com.opinion.library.systemFramework.ApplicationConfiguration" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1><a href="<%=absoluteURL %>/account" title="Account">Account</a>&nbsp;&rsaquo;&nbsp;Profile</h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/account" title="Details"><span>Details</span></a></li>
						<li><a href="<%=absoluteURL %>/billing-profile" title="Billing Profile"><span>Billing Profile</span></a></li>
						<li><a href="<%=absoluteURL %>/transaction-history" title="Transaction History"><span>Transaction History</span></a></li>
						<li><a href="<%=absoluteURL %>/invoices" title="Receipts &amp; Invoices"><span>Receipts &amp; Invoices</span></a></li>
						<!-- <li><a href="<%=absoluteURL %>/users" title="Users"><span>Users</span></a></li> -->
						<li><a href="<%=absoluteURL %>/upgrade" title="Upgrade / Change Plan"><span>Upgrade / Change Plan</span></a></li>
						<li><a href="<%=absoluteURL %>/profile" title="Profile" class="selected"><span>Profile</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 24px;">
			
			...
			
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
					
					errorHandler({
						error : data.accounts.getAccountDetails.error	
					});
					
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


//var accountId = auth.userInfo.accountId;

$(function() {
	
	getAccountDetails({
		accountId : accountId,
		success : function(data) {
			
		},
		error: function() {
			
		}
	});
	
});
</script>