
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
	
	String planId = request.getParameter("plan_id");
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">			
				<h1><a href="<%=absoluteURL %>/setup" title="Setup">Setup</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/plans" title="Plans">Plans</a>&nbsp;&rsaquo;&nbsp;<span id="header_package_name"></span></h1>
			</td>
			<td class="cell-right">
				
			</td>
		</tr>
	</table>
	<div>
	
		<div class="params">
			<div class="param-name">Plan Id:</div>
			<div class="param-value">
				<span id="label_package_id" style="color: #000"></span>
			</div>
		</div>
		<div class="params">
			<div class="param-name">* Plan Name:</div>
			<div class="param-value">
				<div><input type="text" id="text_package_name" name="package_name" size="30" maxlength="32" /></div>
				<div><label id="status_package_name"></label></div>
			</div>
		</div>
		<div class="params" style="height: 72px;">
			<div class="param-name">Description:</div>
			<div class="param-value">
				<textarea style="width: 314px; height: 64px;" maxlength="1000" autocomplete="off" name="description" id="textarea_description"></textarea>
			</div>
		</div>
		
		<div class="params" id="params_usage_period" style="display: none;">
			<div class="param-name">* Usage Period:</div>
			<div class="param-value ui-form">
				<div class="param-choice">
					<label><span><input type="radio" autocomplete="off" id="radio_unlimited_usage_period" name="usage_period" /></span>Unlimited</label>
				</div>
				<div class="param-choice">
					<div class="param-value">
						<label><span><input type="radio" autocomplete="off" id="radio_custom_usage_period" name="usage_period" /></span>Custom:</label>
					</div>
					<div class="param-value">
						<div style="margin-left: 6px;">
							<div class="params">
								<div class="param-value">
									<div><input type="text" id="text_package_usage_period" name="package_usage_period" size="3" maxlength="4" /></div>
									<div><label id="status_package_usage_period"></label></div>
									<div class="param-value-desc"><em>(Days)</em></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="params">
			<div class="param-name">* Number of Responses:</div>
			<div class="param-value ui-form">
				<div class="param-choice">
					<label><span><input type="radio" autocomplete="off" id="radio_unlimited_credit_responses" name="number_of_responses" /></span>Unlimited</label>
				</div>
				<div class="param-choice">
					<div class="param-value">
						<label><span><input type="radio" autocomplete="off" id="radio_credit_responses" name="number_of_responses" /></span>Custom:</label>
					</div>
					<div class="param-value">
						<div style="margin-left: 6px;">
							<div class="params">
								<div class="param-value">
									<div><input type="text" id="text_responses" name="responses" size="6" maxlength="10" autocomplete="off" /></div>
									<div><label id="status_responses"></label></div>
								</div>
							</div>
						</div>
					</div>
					
					<div id="section_custom_number_of_responses" style="clear: both; margin: 0 0 0 18px; display: none;">
						<div style="overflow: hidden;">
							<div class="params">
								<div class="param-value ui-form">
									<div class="param-choice">
										<label><span><input type="radio" name="renewal_period" checked="checked" id="radio_renew_responses_every_30_days" autocomplete="off" /></span>Renew responses every 30 days</label>
									</div>
									<div class="param-choice">
										<div class="param-value">
											<label><span><input type="radio" name="renewal_period" id="radio_renew_responses_custom" autocomplete="off" /></span>Custom:</label>
										</div>
										<div class="param-value">
											<div style="margin-left: 6px;">
												<div><input type="text" id="text_credit_responses_period" name="credit_responses_period" size="6" maxlength="10" autocomplete="off" disabled="disabled" /></div>
												<div><label id="status_credit_responses_period"></label></div>
												<div class="param-value-desc"><em>(Days)</em></div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="params ui-form">
								<div class="param-value">
									<label><span><input type="checkbox" autocomplete="off" id="checkbox_enable_first_credit_responses"></span>Enable starting responses</label>
								</div>
								<div class="param-value">
									<div style="margin-left: 6px;">
										<div><input type="text" id="text_first_credit_responses" name="first_credit_responses" size="6" maxlength="10" autocomplete="off" disabled="disabled" /></div>
										<div><label id="status_first_credit_responses"></label></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
		
		<div class="params">
			<div class="param-name">* Account Users:</div>
			<div class="param-value">
				<div><input type="text" id="text_account_users" name="account_users" size="6" maxlength="10" autocomplete="off" /></div>
				<div><label id="status_account_users"></label></div>
			</div>
		</div>
		
		<div class="params">
			<div class="param-name">* Amount:</div>
			<div class="param-value">
				<div><span style="margin-right: 6px;">$</span><input type="text" size="6" maxlength="10" id="text_amount" name="amount" autocomplete="off" /></div>
				<div><label id="status_amount"></label></div>
			</div>
		</div>
		<div class="params" style="height: 126px">
			<div class="param-name">Permissions:</div>
			<div class="param-value">
				<div style="width: 166px;height:120px;overflow-x: hidden;overflow-y: auto;border-color: #666666 #CCCCCC #CCCCCC; border-style: solid; border-width: 1px; background: #fff">
					<ul id="list_permissions"></ul>
				</div>
			</div>
		</div>
		<div class="params">
			<div class="param-name"></div>
			<div class="param-value ui-form">
				<label><span><input type="checkbox" id="checkbox_inherit_permissions" autocomplete="off" /></span>Inherit from parent</label>
			</div>
		</div>
		<div class="params">
			<div class="param-name">Create Date:</div>
			<div class="param-value">
				<span id="label_create_date" style="color: #000"></span>
			</div>
		</div>
		
		<div style="height: 24px; overflow: hidden;"></div>
		<div class="params">
			<div class="param-name">&nbsp;</div>
			<div class="param-value">
				<a id="button_update" title="Update" class="button-blue" href="javascript:;"><span>Update</span></a>
			</div>
			<div class="param-value">
				<a href="<%=absoluteURL %>/plan?plan_id=<%=planId %>" title="Cancel" style="margin-left: 6px;">Cancel</a>
			</div>
		</div>
		
	</div>
</div>

<script type="text/javascript">
var getPackageDetails = function(params) {

	var obj = {
		products : {
			getPackageDetails : {
				packageId : params.packageId
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
			if(data.products.getPackageDetails != undefined) {
				if(data.products.getPackageDetails.error != undefined) {
					
					errorHandler({
						error : data.products.getPackageDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.products.getPackageDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.products.getPackageDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getPackageSettings = function(params) {

	var obj = {
		products : {
			getPackageSettings : {
				packageId : params.packageId
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
			if(data.products.getPackageSettings != undefined) {
				if(data.products.getPackageSettings.error != undefined) {
					
					errorHandler({
						error : data.products.getPackageSettings.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.products.getPackageSettings);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.products.getPackageSettings);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var updatePackageSettings = function(params) {

	var obj = {
		products : {
			updatePackageSettings : {
				packageId : params.packageId,
				packageName : params.packageName,
				description : params.description,
				usagePeriod : params.usagePeriod,
				creditResponses : params.creditResponses,
				creditResponsesPeriod : params.creditResponsesPeriod,
				firstCreditResponses : params.firstCreditResponses,
				permissions : params.permissions,
				amount : params.amount,
				accountUsers : params.accountUsers
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
			if(data.products.updatePackageSettings != undefined) {
				if(data.products.updatePackageSettings.error != undefined) {
					
					errorHandler({
						error : data.products.updatePackageSettings.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.products.updatePackageSettings);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.products.updatePackageSettings);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var packageId = <%= planId %>;



var isPermissionChanged = false;
var permissionsToReset = [];
var resetProperty = function(property) {
	permissionsToReset.push(property);
};

var permissions = [];
var findInPermissions = function(k) {
	var u = false;
	for(var i = 0; i < permissions.length; i++) {
		if(permissions[i].key == k.key) {
			u = true;
			break;
		}
	}
	return u;
};
var addProperty = function(property) {
	if(permissions.length == 0) {
		permissions.push(property);
	} else {
		for(var i = 0; i < permissions.length; i++) {
			if(findInPermissions(property)) {
				if(permissions[i].key == property.key) {
					permissions[i] = property;
				}
			} else {
				permissions.push(property);
			}
		}
	}
};

var isDefault = false;
$(function() {
	
	getPackageSettings({
		packageId : packageId,
		success : function(data) {
			
			$('#label_package_id').text(data.packageId);
			$('#header_package_name').text(data.packageName);
			
			
			$('#text_package_name').val(data.packageName);
			$('#textarea_description').val(data.description);
			
			
			// usage period
			isDefault = data.isDefault;
			if(!data.isDefault) {
				$('#params_usage_period').show();
				
				$('#text_package_usage_period').prop("disabled", (null == data.defaultUsagePeriod)).val(data.defaultUsagePeriod);
				
				$('#radio_unlimited_usage_period').prop("checked", (null == data.defaultUsagePeriod));
				$('#radio_custom_usage_period').prop("checked", !(null == data.defaultUsagePeriod));
				
				$("input[name='usage_period']").change(function() {
					if($(this).attr("id") == "radio_custom_usage_period") {
						$('#text_package_usage_period').prop("disabled", false).focus();
					} else {
						$('#text_package_usage_period').prop("disabled", true);
					}
				});
				
			}
			
			$('#text_account_users').val(data.accountUsers);
			$('#text_amount').val(data.amount);
			$('#label_create_date').text(data.insertDate);
			
			
			// number of responses
			$('#radio_unlimited_credit_responses').prop("checked", (null == data.creditResponses));
			$('#radio_credit_responses').prop("checked", !(null == data.creditResponses));
			
			$('#text_responses').prop("disabled", (null == data.creditResponses)).val(data.creditResponses);
			if(null != data.creditResponses) {
				$('#section_custom_number_of_responses').show();	
			}
			
			$("input[name='number_of_responses']").change(function() {
				if($(this).attr("id") == "radio_credit_responses") {
					$('#text_responses').prop("disabled", false).focus();
					$('#section_custom_number_of_responses').show();
				} else {
					$('#text_responses').prop("disabled", true);
					$('#section_custom_number_of_responses').hide();
				}
			});
			
			
			//$('#checkbox_enable_first_credit_responses').prop("disabled", (null == data.creditResponses)).prop("checked", !(null == data.firstCreditResponses || null == data.creditResponses));
			$('#checkbox_enable_first_credit_responses')
				.prop("checked", !(null == data.firstCreditResponses || null == data.creditResponses))
				.change(function() {
					
					if($(this).is(':checked')) {
						$('#text_first_credit_responses').prop("disabled", false);
					} else {
						$('#text_first_credit_responses').prop("disabled", true);
					}
					
				});
			
			$('#text_first_credit_responses').prop("disabled", (null == data.firstCreditResponses)).val(data.firstCreditResponses);
			
			
			
			
			
			
			
			
			$('#radio_renew_responses_every_30_days').prop("checked", (null == data.creditResponsesPeriod || null == data.creditResponses));
			$('#radio_renew_responses_custom').prop("checked", !(null == data.creditResponsesPeriod || null == data.creditResponses));
			$('#text_credit_responses_period').prop("disabled", (null == data.creditResponsesPeriod || null == data.creditResponses)).val(data.creditResponsesPeriod);
			
			$("input[name='renewal_period']").change(function() {
				if($(this).attr("id") == "radio_renew_responses_custom") {
					$('#text_credit_responses_period').prop("disabled", false).focus();
				} else {
					$('#text_credit_responses_period').prop("disabled", true);
				}
			});
			
			
			
			
			
			
			for(var i = 0; i < data.permissions.length; i++) {
				var option = $("<li class=\"ui-label\" style=\"padding: 4px;\"><input type=\"checkbox\" " + (data.permissions[i].value == true ? "checked=\"checked\"" : "") + " id=\"" + data.permissions[i].key + "\" class=\"ui-label-checkbox\" inherited=\"" + data.permissions[i].inherited + "\" value=\"" + data.permissions[i].value + "\" parent_value=\"" + data.permissions[i].parentValue + "\" /><label for=\"" + data.permissions[i].key + "\">" + data.permissions[i].name + "</label></li>");
				
				// inherited
				if(data.permissions[i].inherited == true) { 
					option.find('input:checkbox').prop("disabled", true);
					option.find('label').click(function() {
						$('#' +$(this).attr('for')).prop("disabled", false);
					});
				}
				
				option.find('input:checkbox')
					.change(function() {
						
						// fix 1
						if($("#checkbox_inherit_permissions").is(':checked')) {
							$("#checkbox_inherit_permissions").prop("checked", false);
						}
						
						// fix 2 
						isPermissionChanged = true;
						
						addProperty({
							key : $(this).attr("id"),
							value : $(this).is(':checked')
						});
					
				});
				
				option.appendTo('#list_permissions');
			}
			
			$('#checkbox_inherit_permissions').change(function() {
				
				isPermissionChanged = true;
				
				if($(this).is(':checked')) {
					
					permissions = [];
					permissionsToReset = [];
					
					// show by parent inherited
					$('#list_permissions li').each(function(i, el) {
						var E = $(el).find('input:checkbox');
							E.prop("disabled", true)
							 .prop("checked", /^true$/i.test(E.attr("parent_value")));
							
							// if inherited false add to permissions with value = null
							// add to inherited array with value = null;
							if(!/^true$/i.test(E.attr("inherited"))) {
								resetProperty({
									key : E.attr("id"),
									value : null
								});
							}
							
					});
				} else {
					
					permissions = [];
					permissionsToReset = [];
					
					// show by value and inherited
					$('#list_permissions li').each(function(i, el) {
						var E = $(el).find('input:checkbox');
							E.prop("disabled", /^true$/i.test(E.attr("inherited")))
							 .prop("checked", /^true$/i.test(E.attr("value")));
							
					});
				}
			});
			
			// validate fields
			var v = null;
			v = new validator({
				elements : [
					{
						element : $('#text_package_name'),
						status : $('#status_package_name'),
						rules : [
							{ method : 'required', message : 'This field is required.' }
						]
					},
					{
						element : $('#text_responses'),
						status : $('#status_responses'),
						validate : function() {
							return !$('#radio_unlimited_credit_responses').is(":checked");
						},
						rules : [
							{ method : 'required', message : 'This field is required.' },
							{ method : 'digits', message : 'Please enter only digits.' }
						]
					},
					{
						element : $('#text_account_users'),
						status : $('#status_account_users'),
						rules : [
							{ method : 'required', message : 'This field is required.' },
							{ method : 'digits', message : 'Please enter only digits.' }
						]
					},
					{
						element : $('#text_amount'),
						status : $('#status_amount'),
						rules : [
							{ method : 'required', message : 'This field is required.' },
							/* { method : 'number', message : 'Please enter a valid number.' } */
							{ method : 'currency', message : 'Please enter a valid currency value.' }
						]
					}
				],
				submitElement : $('#button_update'),
				messages : null,
				accept : function () {
					
					updatePackageSettings({
						packageId : packageId,
						packageName : $.trim($('#text_package_name').val()),
						description : $('#textarea_description').val(),
						creditResponses : $('#radio_unlimited_credit_responses').is(':checked') ? null : Number($.trim($('#text_responses').val())),
						creditResponsesPeriod : $('#radio_renew_responses_every_30_days').is(':checked') ? null : Number($.trim($('#text_credit_responses_period').val())),
						usagePeriod : (!isDefault ? $('#radio_unlimited_usage_period').is(':checked') ? null : $('#text_package_usage_period').val() : null),
						firstCreditResponses : $('#radio_unlimited_credit_responses').is(':checked') ? null : ($('#checkbox_enable_first_credit_responses').is(":checked") ? Number($.trim($('#text_first_credit_responses').val())) : null),
						permissions : (isPermissionChanged ? ($('#checkbox_inherit_permissions').is(':checked') ? permissionsToReset : permissions) : undefined),
						amount : Number($.trim($('#text_amount').val())),
						accountUsers : Number($.trim($('#text_account_users').val())),
						success : function(data) {
							
							if(isPermissionChanged) {
								isPermissionChanged = false;
								permissions = [];
								permissionsToReset = [];
							}
							
							var modal = new lightFace({
								title : "Changes saved.",
								message : "Your changes were successfully saved.",
								actions : [
									{ 
										label : "OK", 
										fire : function() { 
											modal.close(); 
										}, 
										color : "blue" 
									}
								],
								overlayAll : true
							});
							
						},
						error : function(error) {
							alert(JSON.stringify(error));			
						}
					});
					
				},
				error : function() {
					//
				}
			});
			
		},
		error : function(error) {
			//
		}
	});
	
});
</script>