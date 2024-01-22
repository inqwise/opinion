
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%@ page import="com.inqwise.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.inqwise.opinion.library.common.countries.ICountry" %>
<%@ page import="com.inqwise.opinion.library.managers.CountriesManager" %>
<%@ page import="com.inqwise.opinion.library.common.countries.IStateProvince" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String accountId = request.getParameter("account_id");
%>

<div>
	<h1 style="padding-bottom: 0 !important"><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<span id="header_account_name"></span></h1>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln" id="list_account_details"></ul>
	</div>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" title="Account Details"><span>Account Details</span></a></li>
					<li><a href="<%=absoluteURL %>/account-users?account_id=<%=accountId %>" title="Users"><span>Users</span></a></li>
					<li><a href="<%=absoluteURL %>/account-surveys?account_id=<%=accountId %>" title="Surveys"><span>Surveys</span></a></li>
					<li><a href="<%=absoluteURL %>/account-collectors?account_id=<%=accountId %>" title="Collectors"><span>Collectors</span></a></li>
					<li><a href="<%=absoluteURL %>/account-billing?account_id=<%=accountId %>" title="Billing" class="selected"><span>Billing</span></a></li>
					<li><a href="<%=absoluteURL %>/account-transaction-history?account_id=<%=accountId %>" title="Transaction History"><span>Transaction History</span></a></li>
					<li><a href="<%=absoluteURL %>/account-make-payment?account_id=<%=accountId %>" title="Make a Payment"><span>Make a Payment</span></a></li>
					<li><a href="<%=absoluteURL %>/account-charges?account_id=<%=accountId %>" title="Charges"><span>Charges</span></a></li>
					<li><a href="<%=absoluteURL %>/account-recurring?account_id=<%=accountId %>" title="Recurring"><span>Recurring</span></a></li>
					<li><a href="<%=absoluteURL %>/account-uninvoiced-list?account_id=<%=accountId %>" title="UnInvoiced List"><span>UnInvoiced List</span></a></li>
					<li><a href="<%=absoluteURL %>/account-invoices?account_id=<%=accountId %>" title="Invoices"><span>Invoices</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both; padding-top: 20px;">
		<div class="wrapper-content-left">
		
			<div style="padding-bottom: 20px;">
				<div class="params">
					<div class="param-name">Billing Id:</div>
					<div class="param-value">
						<span id="label_account_id" style="color: #000"></span>
					</div>
				</div>
				<div class="params">
					<div class="param-name">Taxable:</div>
					<div class="param-value">
						<select autocomplete="off">
							<option selected="selected" value="0">No</option>
							<option value="1">Yes</option>
						</select>
					</div>
				</div>
				<div class="params">
					<div class="param-name">* Currency Code:</div>
					<div class="param-value">
						<div>
							<select id="select_currency_code" autocomplete="off">
								<option>USD</option>
							</select>
						</div>
						<div><label id="status_currency_code"></label></div>
					</div>
				</div>
				<div class="params">
					<div class="param-name">* Min Deposit:</div>
					<div class="param-value">
						<div><span style="margin-right: 6px;">$</span><input type="text" size="6" maxlength="10" id="text_min_deposit" name="min_deposit" value="10" /></div>
						<div><label id="status_min_deposit"></label></div>
					</div>
				</div>
				<div class="params">
					<div class="param-name">* Max Deposit:</div>
					<div class="param-value">
						<div><span style="margin-right: 6px;">$</span><input type="text" size="6" maxlength="10" id="text_max_deposit" name="max_deposit" value="1000" /></div>
						<div><label id="status_max_deposit"></label></div>
					</div>
				</div>
				<div style="height: 24px; overflow: hidden;clear: both;"></div>
				<div class="params">
					<div class="param-name"></div>
					<div class="param-value">
						<a href="#" id="button_update_billing_settings" class="button-blue" title="Update"><span>Update</span></a>
					</div>
				</div>
			</div>
			
			<div style="clear: both;">
				<h3 class="ui-header-light">Business Information</h3>
				<div style="padding-top: 12px;padding-bottom: 24px;">
					This address appears on the invoices you can get through your account.<br/> Your business address is not required to match the billing address that's associated with your form of payment.
					<!-- The contact information entered here will appear on the monthly invoice. -->
				</div>
				
				<div>
				
					<div class="params">
						<div class="param-name">Business Name:</div>
						<div class="param-value">
							<div><input type="text" size="25" maxlength="100" id="text_business_name" name="business_name" autocomplete="off" /></div>
							<div class="param-value-desc"><em>(Optional)</em></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">* First Name:</div>
						<div class="param-value">
							<div><input type="text" size="25" maxlength="32" id="text_first_name" name="first_name" autocomplete="off" /></div>
							<div><label id="status_first_name"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">* Last Name:</div>
						<div class="param-value">
							<div><input type="text" size="30" maxlength="32" id="text_last_name" name="last_name" autocomplete="off" /></div>
							<div><label id="status_last_name"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">* Address Line 1:</div>
						<div class="param-value">
							<div><input type="text" size="25" maxlength="100" id="text_address1" name="address1" autocomplete="off" /></div>
							<div><label id="status_address1"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">Address Line 2:</div>
						<div class="param-value">
							<div><input type="text" size="25" maxlength="100" id="text_address2" name="address2" autocomplete="off" /></div>
							<div style="padding-bottom: 10px;"><em style="color: #999">(Optional)</em></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">* City:</div>
						<div class="param-value">
							<div><input type="text" size="25" maxlength="40" id="text_city" name="city" autocomplete="off" /></div>
							<div><label id="status_city"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">* Country:</div>
						<div class="param-value">
							<div>
								<select id="select_country" name="country" autocomplete="off">
									<option value=""></option>
								<%
									OperationResult<List<ICountry>> countriesResult = CountriesManager.getCountries();
														if(!countriesResult.hasError()){
															List<ICountry> countries = countriesResult.getValue();
															for(ICountry country : countries){
								%>
											<option value="<%=country.getId() %>" iso2="<%=country.getIso2()%>"><%=country.getName() %></option>
											<%
										}
									}
								%>
								</select>
							</div>
							<div><label id="status_country"></label></div>
						</div>
					</div>
					<div class="params" id="params_state" style="display: none">
						<div class="param-name">* State:</div>
						<div class="param-value">
							<div>
								<select id="select_state" name="state" autocomplete="off">
									<option value=""></option>
									<%
									OperationResult<List<IStateProvince>> statesResult = CountriesManager.getStatesProvinces(232);
										if(!statesResult.hasError()){
											List<IStateProvince> states = statesResult.getValue();
											for(IStateProvince state : states){
									%>
												<option value="<%=state.getId() %>"><%=state.getName() %></option>
												<%
											}
										}
									%>
								</select>
							</div>
							<div><label id="status_state"></label></div>
						</div>
					</div>
					<div class="params" id="params_province" style="display: none">
						<div class="param-name">* Province:</div>
						<div class="param-value">
							<div>
								<select id="select_province" name="province" autocomplete="off">
									<option value=""></option>
									<%
									OperationResult<List<IStateProvince>> provincesResult = CountriesManager.getStatesProvinces(39);
										if(!provincesResult.hasError()){
											List<IStateProvince> provinces = provincesResult.getValue();
											for(IStateProvince province : provinces){
									%>
												<option value="<%=province.getId() %>"><%=province.getName() %></option>
												<%
											}
										}
									%>
								</select>
							</div>
							<div><label id="status_province"></label></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name">* ZIP Code:</div>
						<div class="param-value">
							<div><input type="text" size="10" maxlength="10" id="text_zip" name="zip" autocomplete="off" /></div>
							<div><label id="status_zip"></label></div>
							<div class="param-value-desc"><em>(5 or 9 digits)</em></div>
						</div>
					</div>
					<div class="params">
						<div class="param-name" style="width: 130px;">Phone:</div>
						<div class="param-value">
							<div><input type="text" id="text_phone1" name="phone1" autocomplete="off"></div>
							<div class="param-value-desc"><em>(Optional)</em></div>
						</div>
					</div>
				
					
				</div>
				
				
			</div>
			
			
			<div style="height: 24px; overflow: hidden;clear: both;"></div>
			<div class="params">
				<div class="param-name"></div>
				<div class="param-value">
					<a href="javascript:;" id="button_update" class="button-blue" title="Update"><span>Update</span></a>
				</div>
			</div>
			
			
		</div>
		<div class="wrapper-content-middle">
			&nbsp;
		</div>
		<div class="wrapper-content-right">
			&nbsp;
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

var getBusinessDetails = function(params) {

	var obj = {
		accounts : {
			getBusinessDetails : {
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
        	if(data.accounts.getBusinessDetails != undefined) {
				if(data.accounts.getBusinessDetails.error != undefined) {
					
					errorHandler({
						error : data.accounts.getBusinessDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.accounts.getBusinessDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.accounts.getBusinessDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var updateBillingSettings = function(params) {

	var obj = {
		accounts : {
			updateBillingSettings : {
				accountId : params.accountId,
				currencyCode : params.currencyCode,
				minDeposit : params.minDeposit,
				maxDeposit : params.maxDeposit
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
        	if(data.accounts.updateBillingSettings != undefined) {
				if(data.accounts.updateBillingSettings.error != undefined) {
					
					errorHandler({
						error : data.accounts.updateBillingSettings.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.accounts.updateBillingSettings);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.accounts.updateBillingSettings);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var updateBusinessDetails = function(params) {

	var obj = {
		accounts : {
			updateBusinessDetails : {
				accountId : params.accountId,
				companyName : params.companyName,
				firstName : params.firstName,
				lastName : params.lastName,
				address1 : params.address1,
				address2 : params.address2,
				city : params.city,
				countryId : params.countryId,
				stateId : params.stateId,
				postalCode : params.postalCode,
				phone1 : params.phone1
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
        	if(data.accounts.updateBusinessDetails != undefined) {
				if(data.accounts.updateBusinessDetails.error != undefined) {
					
					errorHandler({
						error : data.accounts.updateBusinessDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.accounts.updateBusinessDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.accounts.updateBusinessDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getAccountStatus = function(statusId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = "Enabled"; // Your account is now enabled.
			break;
		case 2 : 
			s = "Disabled"; // Your account is disabled. 
			break;
		case 3 : 
			s = "Expired"; // Your account has expired.
			break;
		case 4 :
			s = "Suspended"; // Your account is suspended.
			break;
	}
	return s;
};

var accountId = <%=accountId%>;

$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {
			
			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			// details
			// TODO: change flag isActive to statusId
			$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getAccountStatus(data.isActive ? 1 : 2) + "</li>").appendTo('#list_account_details');
			
			
			
			
			
			
			
			
			
			
			
			// billing id
			$('#label_account_id').text(data.accountId);
			// min deposit - default 10
			//data.minDeposit = 5;
			if(data.minDeposit != undefined) {
				$("#text_min_deposit").val(data.minDeposit);
			}
			// max deposit - default - 1000
			if(data.maxDeposit != undefined) {
				$("#text_max_deposit").val(data.maxDeposit);
			}
			
			// update billing settings
			var v1 = null;
			v1 = new validator({
				elements : [
				    {
						element : $('#text_min_deposit'),
						status : $('#status_min_deposit'),
						rules : [
							{ method : 'required', message : 'This field is required.' },
							{ method : 'currency', message : 'Please enter a valid currency value.' },
							{ method : 'min', pattern : 0.01, message : 'Please enter a value greater than or equal to {0}.' },
							{ method : 'lessThan' , element : $('#text_max_deposit') , message : "Must be less than {0}" }
						]
					}, {
						element : $('#text_max_deposit'),
						status : $('#status_max_deposit'),
						rules : [
							{ method : 'required', message : 'This field is required.' },
							{ method : 'currency', message : 'Please enter a valid currency value.' },
							{ method : 'min', pattern : 0.01, message : 'Please enter a value greater than or equal to {0}.' },
							{ method : 'greaterThan' , element : $('#text_min_deposit') , message : "Must be greater than {0}" }
						] 
					}
				],
				submitElement : $('#button_update_billing_settings'),
				messages : null,
				accept : function () {
					
					updateBillingSettings({
						accountId : accountId,
						minDeposit : $('#text_min_deposit').val(),
						maxDeposit : $('#text_max_deposit').val(),
						success : function() {
							
							var modal = new lightFace({
								title : "Changes saved.",
								message : "Your changes were successfully saved.",
								actions : [
								           { label : "OK", fire : function() { modal.close(); }, color : "blue" }
								],
								overlayAll : true
							});
							
						},
						error: function(error) {
							console.log(JSON.stringify(error));
						}
					});
					
				},
				error: function() {
					
				}
			});
			
			
			
			
			getBusinessDetails({
				accountId : accountId,
				success : function(data) {
					
					$('#text_business_name').val(data.companyName);
					
					$('#text_first_name').val(data.firstName);
					$('#text_last_name').val(data.lastName);
	
					$('#text_address1').val(data.address1);
					$('#text_address2').val(data.address2);
					$('#text_city').val(data.city);
	
					if(data.countryId != 0) {
						$('#select_country option[value="' + data.countryId + '"]').prop('selected', true);
						if(data.countryId == 232) {
							$('#params_state').show();
							$('#params_province').hide();
							$('#select_state option[value="' + data.stateId + '"]').prop('selected', true);
						} else if (data.countryId == 39) {
							$('#params_state').hide();
							$('#params_province').show();
							$('#select_province option[value="' + data.stateId + '"]').prop('selected', true);
						} else {
							$('#params_state').hide();
							$('#params_province').hide();
						}
					}
	
					$('#text_zip').val(data.postalCode);
					$('#text_phone1').val(data.phone1);
					
					
					// validate fields
					var v = null;
					v = new validator({
						elements : [
							{
								element : $('#text_first_name'),
								status : $('#status_first_name'),
								rules : [
									{ method : 'required', message : 'This field is required.' }
								] 
							},
							{
								element : $('#text_last_name'),
								status : $('#status_last_name'),
								rules : [
									{ method : 'required', message : 'This field is required.' }
								]
							},
							{
								element : $('#text_address1'),
								status : $('#status_address1'),
								rules : [
									{ method : 'required' }
								]
							},
							{
								element : $('#text_city'),
								status : $('#status_city'),
								rules : [
									{ method : 'required' }
								]
							},
							{
								element : $('#select_country'),
								status : $('#status_country'),
								rules : [
									{ method : 'required' }
								]
							},
							{
								element : $('#select_state'),
								status : $('#status_state'),
								validate : function() {
									return ($('#select_country').val() == "232");
								},
								rules : [
									{ method : 'required' }
								]
							},
							{
								element : $('#select_province'),
								status : $('#status_province'),
								validate : function() {
									return ($('#select_country').val() == "39");
								},
								rules : [
									{ method : 'required' }
								]
							},
							{
								element : $('#text_zip'),
								status : $('#status_zip'),
								rules : [
									{ method : 'required' },
									{ method : 'rangelength', pattern : [5,9] }
								]
							}
						],
						submitElement : $('#button_update'),
						messages : null,
						accept : function () { 
							
							updateBusinessDetails({
								accountId : accountId,
								companyName : $.trim($('#text_business_name').val().replace(/\r/g, "")),
								firstName : $.trim($('#text_first_name').val().replace(/\r/g, "")),
								lastName : $.trim($('#text_last_name').val().replace(/\r/g, "")),
								address1 : $('#text_address1').val(),
								address2 : $('#text_address2').val(),
								city : $('#text_city').val(),
								countryId : $('#select_country').val(),
								stateId : ($('#select_country').val() == "232" ? $('#select_state').val() : ($('#select_country').val() == "39" ? $('#select_province').val() : "" )),
								postalCode : $('#text_zip').val(),
								phone1 : $('#text_phone1').val(),
								success : function(data) {
									
									var modal = new lightFace({
										title : "Changes saved.",
										message : "Your changes were successfully saved.",
										actions : [
										           { label : "OK", fire : function() { modal.close(); }, color : "blue" }
										],
										overlayAll : true
									});
									
								},
								error : function(error) {
									alert(JSON.stringify(error));
								}
							});	
							
						}
					});
					
					
				},
				error: function() {
					//	
				}
			});
			
			// country
			$('#select_country').change(function() {
				if($(this).val() == "232") {
					// us
					$('#params_state').show();
					$('#params_province').hide();
				} else if ($(this).val() == "39") {
					// canada
					$('#params_state').hide();
					$('#params_province').show();
				} else { 
					
					$('#params_state').hide();
					$('#params_province').hide();
				}
			});
			
		
		}
	});
	
});
</script>