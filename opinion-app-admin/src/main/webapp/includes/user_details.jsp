
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>
<%@page import="com.opinion.library.common.errorHandle.OperationResult"%>
<%@page import="com.opinion.library.common.countries.ICountry"%>
<%@page import="com.opinion.library.managers.CountriesManager"%>
<%@page import="com.opinion.library.common.countries.IStateProvince"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String userId = request.getParameter("user_id");
%>

<div>
	<h1><a href="<%=absoluteURL%>/users" title="Users">Users</a>&nbsp;&rsaquo;&nbsp;<span id="header_user_name"></span></h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL%>/user-details?user_id=<%=userId%>" class="selected" title="User Details"><span>User Details</span></a></li>
						<li><a href="<%=absoluteURL%>/user-messages?user_id=<%=userId%>" title="Messages"><span>Messages</span></a></li>
						<li><a href="<%=absoluteURL%>/user-accounts?user_id=<%=userId%>" title="Related Accounts"><span>Related Accounts</span></a></li>
						<li><a href="<%=absoluteURL%>/user-history?user_id=<%=userId%>" title="History"><span>History</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both;padding-top: 20px;">
			<div class="wrapper-content-left">
				
				<div class="params">
					<div class="param-name">User Id:</div>
					<div class="param-value">
						<span id="label_user_id" style="color: #000"></span>
					</div>
				</div>
				<div class="params">
					<div class="param-name">Username:</div>
					<div class="param-value">
						<span id="label_user_name" style="color: #000"></span>
					</div>
				</div>
				<div class="params">
					<div class="param-name">Provider:</div>
					<div class="param-value">
						<span id="label_provider" style="color: #000"></span>
					</div>
				</div>
				<div class="params">
					<div class="param-name">First Name:</div>
					<div class="param-value">
						<input type="text" id="text_first_name" name="first_name" size="25" maxlength="32" autocomplete="off" />
					</div>
				</div>
				<div class="params">
					<div class="param-name">Last Name:</div>
					<div class="param-value">
						<input type="text" id="text_last_name" name="last_name" size="30" maxlength="32" autocomplete="off" />
					</div>
				</div>
				<div class="params">
					<div class="param-name">Display Name:</div>
					<div class="param-value">
						<input type="text" id="text_display_name" name="display_name" size="30" maxlength="32" />
					</div>
				</div>
				<div class="params">
					<div class="param-name">Email:</div>
					<div class="param-value">
						<input type="text" id="text_email" name="email" size="35" maxlength="50" />
					</div>
				</div>
				<div class="params">
					<div class="param-name">Address Line 1:</div>
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
					<div class="param-name">City:</div>
					<div class="param-value">
						<div><input type="text" size="25" maxlength="40" id="text_city" name="city" autocomplete="off" /></div>
						<div><label id="status_city"></label></div>
					</div>
				</div>
				<div class="params">
					<div class="param-name">Country:</div>
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
										<option value="<%=country.getId() %>"><%=country.getName() %></option>
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
					<div class="param-name">ZIP Code:</div>
					<div class="param-value">
						<input type="text" size="10" maxlength="10" id="text_zip" name="zip" autocomplete="off" />
						<div class="param-value-desc"><em>(5 or 9 digits)</em></div>
					</div>
				</div>
				<div class="params">
					<div class="param-name" style="width: 130px;">Phone 1:</div>
					<div class="param-value">
						<input type="text" id="text_phone1" name="phone1" autocomplete="off" />
					</div>
				</div>
				<div class="params">
					<div class="param-name">Register Date:</div>
					<div class="param-value">
						<span id="label_register_date" style="color: #000"></span>
					</div>
				</div>
				<div class="params">
					<div class="param-name"></div>
					<div class="param-value">
						<div class="ui-form">
							<label><span><input type="checkbox" id="checkbox_send_news_letters" autocomplete="off" /></span>Allow to receive newsletters</label>
						</div>
					</div>
				</div>
				<div class="params">
					<div class="param-name">Gateway Id:</div>
					<div class="param-value">
						<span id="label_gateway_id" style="color: #000">N/A</span>
					</div>
				</div>
				<div class="params" style="height: 72px;">
					<div class="param-name">Comments:</div>
					<div class="param-value">
						<textarea style="width: 314px; height: 64px;" maxlength="1000" autocomplete="off" name="comments" id="textarea_comments"></textarea>
					</div>
				</div>
				<div style="height: 24px; overflow; hidden;"></div>
				<div class="params">
					<div class="param-name">&nbsp;</div>
					<div class="param-value">
						<a id="button_update" title="Update" class="button-blue" href="javascript:;"><span>Update</span></a>
					</div>
				</div>
				
			</div>
			<div class="wrapper-content-middle-right">
				
				<div id="container_reset_password" style="display: none;">
					<h3 class="ui-header-light">Reset password</h3>
					<div style="padding-top: 12px">
						<div>
							<div class="params">
								<div class="param-name">* New Password:</div>
								<div class="param-value">
									<div><input type="password" id="text_new_password" name="new_password" maxlength="12" autocomplete="off" /></div>
									<div><label id="status_new_password"></label></div>
									<div style="padding-bottom: 10px;"><em style="color: #999">(Password must be between 6-12 characters.)</em></div>
								</div>
							</div>
							<div class="params">
								<div class="param-name"><span>* Confirm New Password:</span></div>
								<div class="param-value">
									<div><input id="text_confirm_new_password" type="password" name="confirm_new_password" maxlength="12" autocomplete="off" autocapitalize="off" /></div>
									<div><label id="status_confirm_new_password"></label></div>
								</div>
							</div>
							<div class="params" style="height: 42px;">
								<div class="param-name"></div>
								<div class="param-value">
									<div class="ui-form">
										<div style="margin: 0 0 6px"><label><span><input type="checkbox" id="checkbox_user_must_change_password" checked="checked" autocomplete="off" /></span>User must change password after login</label></div>
										<div style="margin: 0 0 6px"><label><span><input type="checkbox" id="checkbox_send_notification_reset_password" autocomplete="off" /></span>Send user notification about reset change</label></div>
									</div>
								</div>
							</div>
							<div style="height: 24px; overflow; hidden;"></div>
							<div class="params">
								<div class="param-name"></div>
								<div class="param-value">
									<a id="button_reset_password" title="Reset Password" class="button-white" href="javascript:;"><span>Reset Password</span></a>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
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

var setUserDetails = function(params) {

	var obj = {
		users : {
			setUserDetails : {
				userId : params.userId,
				firstName : params.firstName,
				lastName : params.lastName,
				email : params.email,
				phone1 : params.phone1,
				address1 : params.address1,
				address2 : params.address2,
				city : params.city,
				countryId : params.countryId,
				stateId : params.stateId,
				postalCode : params.postalCode,
				displayName : params.displayName,
				sendNewsLetters : params.sendNewsLetters,
				comments : params.comments
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
			if(data.users.setUserDetails != undefined) {
				if(data.users.setUserDetails.error != undefined) {
					
					errorHandler({
						error : data.users.setUserDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.users.setUserDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.users.setUserDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
    
};

var resetPassword = function(params) {

	var obj = {
		users : {
			resetPassword : {
				userId : params.userId,
				newPassword : params.newPassword,
				mustChangePassword : params.mustChangePassword,
				sendConfirmEmail : params.sendConfirmEmail
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
			if(data.users.resetPassword != undefined) {
				if(data.users.resetPassword.error != undefined) {
					
					errorHandler({
						error : data.users.resetPassword.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.users.resetPassword);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.users.resetPassword);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
    
};

var userId = <%= userId %>;
$(function() {
	
	getUserDetails({
		userId : userId,
		success : function(data) {
			
			if(!data.isExternal) {
				$('#container_reset_password').show();
			}
			
			
			$('#label_user_id').text(data.userId);
			$('#label_user_name').text(data.userName);
			$('#text_display_name').val(data.displayName);
			
			// breadcrumbs
			$('#level1').text(data.userName);
			$('#header_user_name').text(data.userName);
			
			if(data.provider != undefined) {
				$('#label_provider').text(data.provider);
			}
			
			$('#label_register_date').text(data.insertDate);
			$('#checkbox_send_news_letters').prop('checked', data.sendNewsLetters);

			$('#text_first_name').val(data.firstName);
			$('#text_last_name').val(data.lastName);

			$('#text_phone1').val(data.phone1);
			$('#text_address1').val(data.address1);
			$('#text_address2').val(data.address2);
			$('#text_city').val(data.city);
			
			$('#text_email').val(data.email);
			$('#label_gateway_id').text(data.gatewayId);
			$('#textarea_comments').val(data.comments);
			
			if(data.countryId) {
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


			


			
			var defaultFocus = function() {
				$('#text_first_name').focus();
			};
	
			defaultFocus(); // Set default focus

			
			$('#button_update').click(function() {

				setUserDetails({
					userId : userId,
					firstName : $.trim($('#text_first_name').val().replace(/\r/g, "")),
					lastName : $.trim($('#text_last_name').val().replace(/\r/g, "")),
					email : $('#text_email').val(),
					address1 : $('#text_address1').val(),
					address2 : $('#text_address2').val(),
					city : $('#text_city').val(),
					countryId : $('#select_country').val(),
					stateId : ($('#select_country').val() == "232" ? $('#select_state').val() : ($('#select_country').val() == "39" ? $('#select_province').val() : "" )),
					postalCode : $('#text_zip').val(),
					phone1 : $('#text_phone1').val(),
					displayName : $('#text_display_name').val(),
					sendNewsLetters : $('#checkbox_send_news_letters').prop('checked'),
					comments : $('#textarea_comments').val(),
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
						//alert(JSON.stringify(error));
					}
				});
			
			});
			
			
			
			
			
			
			
			
			
			// reset password
			new validator({
				elements : [
					{
						element : $('#text_new_password'),
						status : $('#status_new_password'),
						rules : [
							{ method : 'required', message : 'This field is required.' },
							{ method : 'rangelength', pattern : [6,12] }
						]
					},
					{
						element : $('#text_confirm_new_password'),
						status : $('#status_confirm_new_password'),
						rules : [
							{ method : 'required', message : 'This field is required.' },
							{ method : 'rangelength', pattern : [6,12] },
							{ method : 'equalTo',  element : $('#text_new_password'), message : 'Please enter the same value as new password again.' }
						]
					}
				],
				submitElement : $('#button_reset_password'),
				messages : null,
				accept : function () {
					
					resetPassword({
						userId : userId,
						newPassword : $.removeHTMLTags($.trim($('#text_new_password').val())),
						mustChangePassword : $('#checkbox_user_must_change_password').prop('checked'),
						sendConfirmEmail : $('#checkbox_send_notification_reset_password').prop('checked'),
						success : function(data) {
							
							alert("OK");
							
						},
						error: function() {
							//
						}
					});
					
				}
			});
			
			
			

		}
	});
});
</script>