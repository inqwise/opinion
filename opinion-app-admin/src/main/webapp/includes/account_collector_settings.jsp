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
	String collectorId = request.getParameter("collector_id");
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important; display: inline"><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" id="header_account_name"></a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-collectors?account_id=<%=accountId %>" title="Collectors">Collectors</a>&nbsp;&rsaquo;&nbsp;<span id="header_collector_name"></span></h1>
			</td>
			<td class="cell-right ui-selector-right">
				<ul class="lw" id="list_collector_actions" style="display: inline-block;"></ul>
			</td>
		</tr>
	</table>
	<div style="clear: both; height: 16px; padding-top: 4px;padding-bottom: 20px;">
		<ul class="ln" id="list_collector_details"></ul>
	</div>
	<div style="clear: both;">
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/account-collector-details?account_id=<%=accountId %>&collector_id=<%=collectorId %>" title="Details"><span>Details</span></a></li>
					<li><a href="<%=absoluteURL %>/account-collector-settings?account_id=<%=accountId %>&collector_id=<%=collectorId %>" title="Settings" class="selected"><span>Settings</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both; padding-top: 24px;">
		
		<div class="wrapper-content-left">
				
			<div style="Xpadding-top: 24px; clear: both;">
				<h3 class="ui-header-light">Multiple Responses</h3>
				<div style="padding-top: 10px;">
					<div class="row">
						<div class="cell">
							<select id="dropdown_multiple_responses" autocomplete="off"><option value="0">One response per computer</option><option value="1">Allow multiple responses</option></select>
						</div>
					</div>
				</div>
			</div>
			
			<div style="padding-top: 24px; clear: both;">
				<h3 class="ui-header-light">Responses to be Edited</h3>
				<div style="padding-top: 4px;" id="container_edit_responses">
					<div class="row-choice ui-label">
						<input type="radio" name="responses_to_be_edited" id="radio_responses_to_be_edited_no" class="ui-label-checkbox" /><label for="radio_responses_to_be_edited_no">No, once a page in the survey is submitted, respondents cannot go back and change existing responses.</label>
					</div>
					<div class="row-choice ui-label">
						<input type="radio" name="responses_to_be_edited" id="radio_responses_to_be_edited_yes" checked="checked" class="ui-label-checkbox" /><label for="radio_responses_to_be_edited_yes">Yes, respondents can go back to previous pages in the survey and update existing responses until the survey is finished or until they have exited the survey.<br/> After the survey is finished, the respondent will not be able to re-enter the survey.</label>
					</div>
				</div>
			</div>
				
			<!--		
			<div id="placeholder_collector_settings_custom_redirects" style="padding-top: 24px; clear: both;">
				<h3 class="ui-header-light">Custom Redirects</h3>
				<div>
					...
				</div>
			</div>
			-->
			
			<div id="placeholder_collector_settings_restrictions" style="padding-top: 24px; clear: both; display: none;">
				<h3 class="ui-header-light">Restrictions</h3>
				<div style="padding-top: 4px;">
					<div class="row-choice">
						<label><input type="checkbox" name="checkbox_password_protection" id="checkbox_password_protection" autocomplete="off" />Enable password protection</label>	
					</div>
					<div id="container_password_protection" style="display: none; padding-top: 12px;">
						<div class="row" style="padding-bottom: 0px;">
							<div class="cell">
								<div>
									<input id="input_password_protection" name="input-password-protection" type="text" maxlength="12" autocomplete="off" />
									<input id="input_fake_password_protection" name="input-fake-password-protection" type="password" maxlength="12" autocomplete="off" style="display: none;" />
								</div>
							</div>
							<div class="cell" style="padding-left: 12px;">
								<label><input type="checkbox" id="checkbox_hide_password" autocomplete="off"/>Hide password from others</label>
							</div>
						</div>
						<div><label id="status_input_password_protection"></label></div>
						<div><label id="status_input_fake_password_protection"></label></div>
						<div style="padding-bottom: 10px; clear: both;">
							<em style="color: #999">(Password must be between 6-12 characters.)<br/>Protected surveys will require this password to view and take part in the survey.</em>
						</div>
					</div>
				</div>
				
				
				<!--
				<div>
					<div class="row-choice">
						<label><input type="checkbox" name="allow_access_from_specific_referral_only" id="checkbox_allow_access_from_specific_referral_only" autocomplete="off" />Allow access from specific referral only</label>	
					</div>
					<div id="container_allow_access_from_specific_referral_only" style="display: none;padding-top: 12px;">
						<div><input type="text" id="text_referer" autocomplete="off" maxlength="255" style="width: 200px;" /></div>
						<div style="padding-bottom: 10px; clear: both;">
							<em style="color: #999">e.g. abc.com</em>
						</div>
					</div>
				</div>
				<div>
					<div class="row-choice">
						<label><input type="checkbox" name="ip_restriction" id="checkbox_ip_restriction" autocomplete="off" />IP restriction</label>
					</div>
					<div id="container_ip_restriction" style="display: none;padding-top: 12px;">
						<div><textarea style="width: 314px; height: 64px;" spellcheck="false" autocomplete="off" name="ip_white_list" id="textarea_ip_white_list"></textarea></div>
						<div style="padding-bottom: 10px; clear: both;">
							<em style="color: #999">Enter each IP address on a new line. Enter a * at any point in an IP to register a wildcard (e.g. 201.100.101.*).</em>
						</div>
					</div>
				</div>
				-->
			</div>
			
			<!--
			<div style="padding-top: 24px; clear: both;">
				<h3 class="ui-header-light">Custom Redirect When Complete</h3>
				<div style="padding-top: 12px;">
					<input type="text" autocomplete="off" maxlength="255" style="width: 200px;" />
				</div>
			</div>
			-->
			
			<div style="padding-top: 24px; clear: both; display: none;">
				<h3 class="ui-header-light">RSS Feed</h3>
				<div style="padding-top: 4px;">
					<div class="row-choice">
						<label><input type="checkbox" name="checkbox_enable_rss_updates" id="checkbox_enable_rss_updates" autocomplete="off" />Get automatic updates in an RSS reader</label>
					</div>
					<div id="container_rss_feed" style="display: none;">
						<div class="row" style="padding-bottom: 0px;">
							<div class="cell">
								<input type="text" autocomplete="off" style="width: 326px;"/>
							</div>
						</div>
						<div style="padding-bottom: 10px;"><em style="color: #999">Copy and paste this link into your RSS reader</em></div>
					</div>
				</div>
			</div>
			<div style="padding-top: 24px; clear: both">
				<h3 class="ui-header-light">Email Notifications</h3>
				<div style="padding-top: 4px;">
					<div class="row-choice">
						<label><input type="checkbox" name="checkbox_enable_email_notification" id="checkbox_enable_email_notification" autocomplete="off" />Get responses sent to your inbox</label>
					</div>
					<div class="row" style="display: none;">
						<div class="cell"><input id="input_email_notification" name="input_email_notification" type="text" autocomplete="off" style="width: 225px;" /></div>
					</div>
				</div>
			</div>
			<div style="padding-top: 24px; clear: both; display: none;">
				<h3 class="ui-header-light">SSL encryption</h3>
				<div style="padding-top: 10px;">
					<div class="row" style="height: 44px;">
						<div class="cell">
							<div style="line-height: 1.4em;"><span class="field-warning-message">You can secure your survey and the responses using SSL encryption.<br/> To enable this feature, upgrade to a Pro, or Enterprise plan.</span></div>
						</div>
					</div>
				</div>
			</div>
			
			<div style="height: 12px; overflow: hidden;"></div>
			<div style="height: 24px; overflow: hidden;"></div>
			<div class="row">
				<div class="cell"><a href="javascript:;" id="button_save_changes" title="Save Changes" class="button-blue"><span>Save Changes</span></a></div>
				<div class="cell" style="padding-left: 6px; line-height: 21px;"><a href="javascript:;" onClick="history.go(-1)" title="Cancel">Cancel</a></div>
			</div>
			
		</div>
		<div class="wrapper-content-middle-right">
			<div id="placeholder_collector_settings_closing" style="padding-top: 24px; clear: both;display: none;">
				<h3 class="ui-header-light">Collector closing</h3>
				<div style="padding-top: 4px;">
					<div class="row-choice">
						<label><input type="checkbox" name="checkbox_close_after_a_certain_date" id="checkbox_close_after_a_certain_date" autocomplete="off"  />Close after a certain date</label>
					</div>
					<div id="container_close_after_a_certain_date" style="display: none; padding-top: 12px;">
						<div class="row">
							<div class="cell"><select id="dropdown_close_after_certain_months" autocomplete="off"></select></div>
							<div class="cell" style="margin-left: 6px;"><select id="dropdown_close_after_certain_days" autocomplete="off"></select></div>
							<div class="cell" style="margin-left: 6px;"><select id="dropdown_close_after_certain_years" autocomplete="off"></select></div>
						</div>
						<div class="row">
							<div class="cell"><select id="dropdown_close_after_certain_hours" autocomplete="off"></select></div>
							<div class="cell" style="margin-left: 6px;"><select id="dropdown_close_after_certain_minutes" autocomplete="off"></select></div>
						</div>
						<div style="padding-bottom: 10px;"><em style="color: #999">* Time is in your chosen timezone</em></div>
					</div>
					<div class="row-choice">
						<label><input type="checkbox" name="checkbox_close_after_a_quota_reached" id="checkbox_close_after_a_quota_reached" autocomplete="off" />Close after a quota reached</label>
					</div>
					<div id="container_quota_reached" style="display: none; padding-top: 12px;">
						<div class="row">
							<div class="cell">
								<div>
									<input id="input_quota_reached" name="input-quota-reached" type="text" autocomplete="off" value="0" style="width: 86px;" />
								</div>
								<div><label id="status_input_quota_reached"></label></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div style="padding-top: 24px; clear: both;">
				<h3 class="ui-header-light">Close default message</h3>
				<div style="padding-top: 12px;">
					<div><textarea style="width: 314px; height: 64px;" autocomplete="off" name="close_default_message" id="textarea_close_default_message"></textarea></div>
					<div><label id="status_close_default_message"></label></div>
				</div>
			</div>
		</div>
		
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/split_button.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/collector_details.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/collector_settings.js"></script>
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

var getCollectorType = function(typeId) {
	var s = "";
	switch(typeId) {
		case 1 :
			s = "Direct link";
			break;
		case 2 : 
			s = "CINT panel";
			break;
	}
	return s;
};

var getCollectorStatus = function(statusId, typeId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = (typeId == 1 ? "Open" : "The panel is live"); 
			break;
		case 2 : 
			s = (typeId == 1 ? "Closed" : "Panel has been completed");
			break;
		case 3 :
			s = "Awaiting payment";
			break;
		case 4 : 
			s = "Panel is being verified";
			break;
		case 5 :
			s = "The panel is live";
			break;
		case 6 : 
			s = "Panel has been completed"; 
			break;
		case 7 : 
			s = "Pending"; // Hold
			break;
		case 8 : 
			s = "Canceled";
			break;
	}
	return s;
};

var updateCollectorStatus = function(params) {
	
	var obj = { 
		collectors : { 
			updateCollectorStatus : { 
				collectorId : params.collectorId,
				statusId : params.statusId,
				closeMessage : params.closeMessage,
				isGenerateNewGuid : params.isGenerateNewGuid
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
        	if(data.collectors.updateCollectorStatus != undefined) {
				if(data.collectors.updateCollectorStatus.error != undefined) {
					
					errorHandler({
						error : data.collectors.updateCollectorStatus.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.updateCollectorStatus);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.updateCollectorStatus);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });

};

var deleteCollectors = function(params) {
	
	var obj = { 
   		collectors : { 
			deleteCollectors : { 
				list : params.list
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
        	if(data.collectors.deleteCollectors != undefined) {
				if(data.collectors.deleteCollectors.error != undefined) {
					
					errorHandler({
						error : data.collectors.deleteCollectors.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.deleteCollectors);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.deleteCollectors);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var getCollectorDetails = function(params) {
	
	var obj = { 
		collectors : { 
			getCollectorDetails : { 
				collectorId : params.collectorId
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
        	if(data.collectors.getCollectorDetails != undefined) {
				if(data.collectors.getCollectorDetails.error != undefined) {
					
					errorHandler({
						error : data.collectors.getCollectorDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.collectors.getCollectorDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.collectors.getCollectorDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var accountId = <%=accountId%>;
var collectorId = <%=collectorId%>;
var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
var messages = {
	closeCollector : "Close Collector",
	openCollector : "Open Collector",
	defaultCloseMessage : "This survey is currently closed.  Please contact the author of this survey for further assistance."
};

$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			new collectorDetails({
				collectorId : collectorId,
				accountId : accountId,
				absoluteUrl : absoluteUrl,
				elements : [],
				success : function(data) {
					
					new collectorSettings({
						collector: data,
						servletUrl : servletUrl,
						absoluteUrl : absoluteUrl,
						monthNames : monthNames
					});
					
				}
			});
			
		}
	});
	
});
</script>