
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="com.inqwise.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.inqwise.opinion.library.common.countries.ICountry" %>
<%@ page import="com.inqwise.opinion.library.managers.CountriesManager" %>
<%@ page import="com.inqwise.opinion.library.common.countries.ITimeZone" %>
<%@ page import="com.inqwise.opinion.library.common.countries.IStateProvince" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1><a href="<%=absoluteURL %>/account" title="Account">Account</a>&nbsp;&rsaquo;&nbsp;Cancel Account</h1>
	<div>
		<div class="wrapper-content-left">
			
			<!--
			<h3 class="ui-header-light">Account Cancellation Request</h3>
			<div style="padding-bottom: 14px; padding-top: 12px;">
				We're sorry to hear that you wish to cancel your account. Before you go, please describe your reason why you are canceling your account.
				<p>Fields indicated with an * are required.</p>
			</div>
			-->
			
			<div>
				<div style="padding: 0px 0 14px 0">
					Canceling your account will disable access to your account and remove all data that you've created or shared on Inqwise.
					<p>Fields indicated with an * are required.</p>
				</div>
			</div>
			<div>
			
				<!--
				<div class="params">
					<div class="param-name">Account Id:</div>
					<div class="param-value">
						<b id="label_account_id" style="color: #333"></b>
					</div>
				</div>
				-->
				<div class="params" style="height: 72px;">
					<div class="param-name"><span>* Reason for cancellation:</span></div>
					<div class="param-value">
						<div><textarea style="width: 314px; height: 64px;" placeholder="Briefly describe your reason for cancellation" autocomplete="off" name="reason-for-canceling-account" id="textarea_reason_for_canceling_account"></textarea></div>
						<div><label id="status_reason_for_canceling_account"></label></div>
					</div>
				</div>
				
				<div style="height: 24px; overflow: hidden;"></div>
				<div class="params">
					<div class="param-name">&nbsp;</div>
					<div class="param-value">
						<a id="button_confirm" title="Confirm" class="button-blue" href="javascript:;"><span>Confirm</span></a>
					</div>
					<div class="param-value" style="line-height: 18px;">
						<a style="margin-left: 6px; " title="Cancel" href="<%=absoluteURL %>/account">Cancel</a>
					</div>
				</div>
			</div>
				
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
			
			$('#label_account_id').text(data.accountId);
			
		},
		error: function(error) {
			//
		} 
	});
	
});
</script>