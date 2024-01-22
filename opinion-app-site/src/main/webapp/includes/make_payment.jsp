
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.inqwise.opinion.cms.common.IPage" %>
<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>

<%@ page import="com.inqwise.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.inqwise.opinion.library.common.countries.ICountry" %>
<%@ page import="com.inqwise.opinion.library.managers.CountriesManager" %>
<%@ page import="com.inqwise.opinion.library.common.countries.IStateProvince" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String charges = request.getParameter("charges");
%>

<div>
	<h1><a href="<%=absoluteURL %>/account" title="Account">Account</a>&nbsp;&rsaquo;&nbsp;Make a payment</h1>
	<div>
		<div class="wrapper-content-left">
			
			<div id="form_request" style="display: none">
			
				<div style="display: none; padding-bottom: 20px;" id="section_payment_summary">
					<h3 class="ui-header-light">Payment Summary</h3>
					<div style="padding-top: 12px">
						<table class="ti">
							<thead>
								<tr>
									<th>Description</th>
									<th class="align-right" style="width:74px;">Amount</th>
								</tr>
							</thead>
							<tbody id="table_payment_details"></tbody>
							<tfoot>
								<tr>
									<td class="align-right">&nbsp;</td>
									<td class="align-right cell-sub-total">&nbsp;</td>
								</tr>
								<tr>
									<td class="align-right label-total"><b>Total:</b></td>
									<td class="align-right cell-total" id="label_amount_due">0.00</td>
								</tr>
								<tr>
									<td class="align-right label-total"><b>Current Balance:</b></td>
									<td class="align-right cell-paid" id="label_current_balance">-0.00</td>
								</tr>
								<tr>
									<td class="align-right label-total"><b>Amount to Pay:</b></td>
									<td class="align-right cell-due" id="label_amount_to_fund" style="color: #333; font-weight: bold;">0.00</td>
								</tr>
							</tfoot>	
						</table>
					</div>
				</div>
			
				<div style="padding-bottom: 20px;display: none" id="section_specify_amount">
					<h3 class="ui-header-light">Specify your payment amount</h3>
					<div style="padding-top: 12px;">
						<div class="params">
							<div class="param-name">* Amount:</div>
							<div class="param-value">
								<div><span style="margin-right: 6px;">$</span><input type="text" size="6" maxlength="10" id="text_amount" name="amount" autocomplete="off" /></div>
								<div><em style="color: #999" id="label_min_deposit"></em></div>
								<div><label id="status_amount"></label></div>
							</div>
						</div>
					</div>
				</div>
			
				<div style="clear: both; padding-top: 24px; display: none">
					<!-- <h3 class="ui-header-light">Add a new form of payment</h3> -->
					<div style="Xpadding-top: 12px;padding-bottom: 20px;">
						Fund your account by credit card. After your card is successfully authorized, your payment will be credited almost immediately.
					</div>
				</div>
				
				<div style="clear: both;display: none" id="section_express_checkout">
				
					<h3 class="ui-header-light">Payment details</h3>
					<div style="padding-top: 12px;">
						<div class="params">
							<div class="param-name">Pay using:</div>
							<div class="param-value">
								<img width="73" height="19" alt="PayPal" src="<%=applicationURL %>/images/logoPaypalCYP.gif" />
							</div>
						</div>

						<div class="params" id="params_payment_amount" style="display: none">
							<div class="param-name">Payment amount:</div>
							<div class="param-value">
								<b id="label_express_checkout_payment_amount" style="color: #333"></b>
							</div>
						</div>
						
					</div>
					<div class="params">
						<div class="param-name"></div>
						<div class="param-value">Continue to PayPal to complete your payment<!-- <br/>By clicking on "Continue" button you will be redirected to PayPal Website to complete the payment --></div>
					</div>
					<div class="params">
						<div class="param-name"></div>
						<div class="param-value">
							<a class="button-blue" id="button_express_checkout" title="Continue"><span>Continue</span></a>
						</div>
					</div>
					
				</div>
				
				<div style="clear: both;display: none" id="section_credit_card_details"> 
					<h3 class="ui-header-light">Credit Card Details</h3>
					<div style="padding-top: 12px;">
						<div class="params">
							<div class="param-name">* Card Type:</div>
							<div class="param-value">
								<select id="select_credit_card_type" name="credit_card_type" autocomplete="off">
									<option value="1">Visa</option>
									<option value="2">MasterCard</option>
									<option value="3">Discover</option>
									<option value="4">American Express</option>
									<!-- <option value="5">Switch</option>
									<option value="6">Solo</option>
									<option value="7">Maestro</option> -->
								</select>
							</div>
						</div>
						<div class="params">
							<div class="param-name">* Card Number:</div>
							<div class="param-value">
								<div><input type="text" size="19" maxlength="19" id="text_credit_card_number" name="credit_card_number" autocomplete="off" /></div>
								<div><label id="status_credit_card_number"></label></div>
								<div style="height: 24px; padding: 8px 0">
									<ul class="lz cards">
										<li><i class="visa sp-3f0t1g"><u>visa</u></i></li>
										<li><i class="mastercard sp-3f0t1g"><u>mastercard</u></i></li>
										<li><i class="amex sp-3f0t1g"><u>amex</u></i></li>
										<li><i class="discover sp-3f0t1g"><u>discover</u></i></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="params">
							<div class="param-name">* Expiration Date:</div>
							<div class="param-value">
								<select id="select_exp_date_month" name="exp_date_month" autocomplete="off">
									<option value="1">01</option>
									<option value="2">02</option>
									<option value="3">03</option>
									<option value="4">04</option>
									<option value="5">05</option>
									<option value="6">06</option>
									<option value="7">07</option>
									<option value="8">08</option>
									<option value="9">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
								</select><select id="select_exp_date_year" name="exp_date_year" autocomplete="off" style="margin-left: 6px;"></select>
							</div>
						</div>
						<div class="params">
							<div class="param-name">* CVV2/CSC:</div>
							<div class="param-value">
								<div><input type="text" size="3" maxlength="4" id="text_cvv2_number" name="cvv2_number" autocomplete="off" /><!-- <a href="#" title="What's this?" style="margin-left: 6px;">What's this?</a> --></div>
								<div><label id="status_cvv2_number"></label></div>
								<div style="padding-bottom: 10px;"><em style="color: #999">(On the back of your card, locate the final 3 or 4 digit number)</em></div>
							</div>
						</div>
						<div class="params">
							<div class="param-name">* First Name:</div>
							<div class="param-value">
								<div><input type="text" size="25" maxlength="32" id="text_first_name" name="first_name" autocomplete="off" /></div>
								<div><label id="status_first_name"></label></div>
								<div style="padding-bottom: 10px;"><em style="color: #999">(as it appears on card)</em></div>
							</div>
						</div>
						<div class="params">
							<div class="param-name">* Last Name:</div>
							<div class="param-value">
								<div><input type="text" size="30" maxlength="32" id="text_last_name" name="last_name" autocomplete="off" /></div>
								<div><label id="status_last_name"></label></div>
								<div style="padding-bottom: 10px;"><em style="color: #999">(as it appears on card)</em></div>
							</div>
						</div>
					</div>
					
					<div style="clear: both;padding-top: 20px;">
						<h3 class="ui-header-light">Billing Information</h3>
						<div style="padding-top: 12px;">
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
						</div>
					</div>
					
					<div style="clear: both;">
						<div style="margin-left: 130px;">
							<ul class="error-message"></ul>
						</div>
					</div>
					<div style="height: 24px; overflow: hidden;clear: both;"></div>
					<div class="params">
						<div class="param-name"></div>
						<div class="param-value">
							<a id="button_submit_payment" class="button-blue" title="Submit Payment"><span>Submit Payment</span></a>
						</div>
						<div class="param-value" style="line-height: 18px;">
							<a href="javascript:;" onClick="history.go(-1)" title="Cancel" style="margin-left: 6px; ">Cancel</a>
						</div>
					</div>
				</div>
				
			</div>
			
			<div id="form_review" style="display: none;">
			
				<h3 class="ui-header-light">Review your payment</h3>
				<div style="padding-top: 12px;">
					Please confirm that you would like to make the following payment:
				</div>
				
				<div style="clear: both; padding-top: 12px;">
					<h3 class="ui-header-light">Payment details</h3>
					<div style="padding-top: 12px;">
						<div class="params">
							<div class="param-name">Pay using:</div>
							<div class="param-value">
								<b id="label_credit_card_details"></b>
							</div>
						</div>
						<div class="params">
							<div class="param-name">Payment amount:</div>
							<div class="param-value">
								<b id="label_payment_amount"></b>
							</div>
						</div>
					</div>
				</div>
				
				<div style="height: 24px; overflow: hidden;"></div>
				<div class="params">
					<div class="param-name"></div>
					<div class="param-value">
						<a id="button_make_payment" class="button-blue" title="Make payment of $10.00"><span>Make payment of $10.00</span></a>
					</div>
					<div class="param-value" style="line-height: 18px;">
						<a href="javascript:;" onClick="history.go(-1)" title="Cancel" style="margin-left: 6px;">Cancel</a>
					</div>
				</div>
				
			</div>
			
			<div id="form_accept" style="display: none;">
				<h3 class="ui-header-light">Payment confirmation</h3>
				<div style="padding-top: 12px;padding-bottom: 12px;">
					Thank you for your payment of <b id="label_paid_amount" style="color: #333"></b>.
				</div>
				<a href="<%=absoluteURL %>/transaction-history" title="Transaction History"><span>Transaction History</span></a>
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

var getPaymentDetails = function(params) {

	var obj = {
		payments : {
			getPaymentDetails : {
				userId : params.userId,
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
        	if(data.payments.getPaymentDetails != undefined) {
				if(data.payments.getPaymentDetails.error != undefined) {
					
					errorHandler({
						error : data.payments.getPaymentDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.payments.getPaymentDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.payments.getPaymentDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var getCharges = function(params) {

	var obj = {
		charges : {
			getCharges : {
				accountId : params.accountId,
				charges : params.charges,
				statusId : params.statusId
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
        	if(data.charges.getCharges.error != undefined) {
				
        		errorHandler({
					error : data.charges.getCharges.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.charges.getCharges);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.charges.getCharges);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var getTransaction = function(params) {
	
	var obj = {
		accounts : {
			getTransaction : {
				accountId : params.accountId,
				aoid : params.aoid
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
        	if(data.accounts.getTransaction != undefined) {
				if(data.accounts.getTransaction.error != undefined) {
					
					errorHandler({
						error : data.accounts.getTransaction.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.accounts.getTransaction);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.accounts.getTransaction);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var setExpressCheckout = function(params) {
	
	var obj = {
		payments : {
			setExpressCheckout : {
				accountId : params.accountId,
				amount : params.amount,
				charges : params.charges
			}
		}
	};
	
	$.ajax({
        /*url : servletUrl,*/
        url : apiUrl,
        data : {
			rq : JSON.stringify(obj), 
			timestamp: $.getTimestamp()
        },
        /*dataType: "json",*/
        dataType: "jsonp",
		jsonp: "callback",
        success: function (data, status) {
        	if(data.payments.setExpressCheckout != undefined) {
				if(data.payments.setExpressCheckout.error != undefined) {
					
					errorHandler({
						error : data.payments.setExpressCheckout.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.payments.setExpressCheckout);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.payments.setExpressCheckout);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            //
        }
    });
	
};

var directPayment = function(params) {
	
	var obj = {
		payments : {
			directPayment : {
				userId : params.userId,
				accountId : params.accountId,
				firstName : params.firstName,
				lastName : params.lastName,
				creditCardTypeId : params.creditCardTypeId,
				creditCardNumber : params.creditCardNumber,
				expDateMonth : params.expDateMonth,
				expDateYear : params.expDateYear,
				cvv2Number : params.cvv2Number,
				address1 : params.address1,
				address2 : params.address2,
				city : params.city,
				countryCode : params.countryCode,
				countryId : params.countryId,
				stateId : params.stateId,
				postalCode : params.postalCode,
				amount : params.amount,
				charges : params.charges,
				isAddressChanged : params.isAddressChanged
			}
		}
	};
	
	$.ajax({
        /*url : servletUrl,*/
        url : apiUrl,
        data : {
			rq : JSON.stringify(obj), 
			timestamp: $.getTimestamp()
        },
        /*dataType: "json",*/
        dataType: "jsonp",
		jsonp: "callback",
        success: function (data, status) {
        	if(data.payments.directPayment != undefined) {
				if(data.payments.directPayment.error != undefined) {
					
					errorHandler({
						error : data.payments.directPayment.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.payments.directPayment);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.payments.directPayment);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            //
        }
    });
};

function clearErrors() {
	$('.error-message').empty();
};

function errorMessenger(error) {
	
	clearErrors();
	
	switch(error.error) {
		case "InvalidCCNumerOrCCType" :
			$("<li>Please enter a valid credit card number and type.</li>").appendTo('.error-message');
			break;
		case "BillingCityIsMandatory" :
			$("<li>Please enter a valid city in the billing address.</li>").appendTo('.error-message');
			break;
		case "InvalidPostalCode" :
			$("<li>Please enter a valid postal code in the billing address.</li>").appendTo('.error-message');
			break;
		case "InvalidAmount" :
			$("<li>Bill amount must be greater than 0</li>").appendTo('.error-message');
			break;
		case "InvalidExpiration" :
			$("<li>Please enter a valid credit card expiration date.</li>").appendTo('.error-message');
			break;
		case "AmountExcededTheUpperLimit" :
			$("<li>This transaction cannot be processed. The amount exceeded the upper limit.</li>").appendTo('.error-message');
			break;
		case "GeneralError" :
			$("<li>" + (error.errorDescription != undefined ? error.errorDescription : "There's an error with this transaction.") + "</li>").appendTo('.error-message');
			break;
	}
};


//var accountId = auth.userInfo.accountId;
var userId = null;
var balance = 0;
var charges = [<%= null != charges ? charges : "" %>];



var isFund = true;
var amountToFund = 0;
var minDeposit = 10;
var maxDeposit = 1000;
var hasBusinessAddress = false;
var isExpressCheckout = true;
var accountOperationId = null;

$(function() {
	
	accountOperationId = ($.getUrlParam("aoid") != "" ? $.getUrlParam("aoid") : null);
	
	getAccountDetails({
		accountId : accountId,
		success : function(data) {
			
			userId = data.ownerId;
			balance = data.balance;
			
			
			// min deposit
			if(data.minDeposit != undefined) {
				minDeposit = data.minDeposit;
			}
			
			$("#label_min_deposit").text("(Amount must be at least $" + (minDeposit).formatCurrency(2, '.', ',') + ")");
			
			// max deposit
			if(data.maxDeposit != undefined) {
				maxDeposit = data.maxDeposit;
			}
			
			if(accountOperationId != null) {
				
				getTransaction({
					accountId : accountId,
					aoid : accountOperationId,
					success : function(transaction) {
						
						// show thank you
						$('#form_accept').show();
						$('#label_paid_amount').text("$" + (transaction.amount).formatCurrency(2, '.', ','));
						
					},
					error: function(error) {
						console.log(error);
					}
				});
				
			} else {
			
				$("#form_request").show();
				
				getPaymentDetails({
					userId : userId,
					accountId : accountId,
					success : function(data) {
						
						hasBusinessAddress = data.hasBusinessAddress;
						
						
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
						


						
						// Fill credit card expiration month + years
						// Paypal: +19 years
						var currentDate = new Date();
						var month = currentDate.getMonth();
						$('#select_exp_date_month')[0].selectedIndex = month;
						
						var year = currentDate.getFullYear();
						var q = $("#select_exp_date_year")[0].options;
						for(var i = 0; i < 19; i++) {
							var k = new Option((year + i), (year + i));
							try {
								q.add(k)
							} catch (ex) {
								q.add(k, null)
							}
						}
						
						// get charges
						if(charges.length != 0) {
							
							getCharges({
								accountId : accountId,
								charges : charges,
								statusId : 1, // unpaid
								success : function(data) {
									
									if(data.list.length != 0) {
										
										// specify amount
										$('#section_specify_amount').hide();
										
										// payment details
										$('#section_payment_summary').show();
										
										if(isExpressCheckout) {
											$("#section_express_checkout").show();	
										} else {
											$("#section_credit_card_details").show();
										}
										
										isFund = false;
										
										for(var i = 0; i < data.list.length; i++) {
											
											$("<tr>" +
												"<td>" +
													/*
													"<div>" +
														"<a href=\"<%=absoluteURL %>/charges/" + data.list[i].chargeId + "\" title=\"Charge #" + data.list[i].chargeId + "\">Charge #" + data.list[i].chargeId + "</a>" +
													"</div>" +
													*/
													"<div>" + (data.list[i].description != undefined ? data.list[i].description : "") + "</div>" +
												"</td>" +
												"<td class=\"align-right\">$" + (data.list[i].amount).formatCurrency(2, '.', ',') + "</td>" +
											"</tr>").appendTo("#table_payment_details");
											
										}
										
										// total amount
										$('#label_amount_due').text("$" + (data.amountDue).formatCurrency(2, '.', ','));
										// balance
										$('#label_current_balance').text("$" + (balance).formatCurrency(2, '.', ','));
										// amount to pay
										$('#label_amount_to_fund').text("$" + (data.amountToFund).formatCurrency(2, '.', ','));
										
										amountToFund = data.amountToFund; // Number((data.amountToFund).formatCurrency(2, '.', ','));
										
										
										// show submit payment 
										$('#button_submit_payment')
											.attr({ "title" : "Make payment of $" + (data.amountToFund).formatCurrency(2, '.', ',')})
											.html("<span>Make payment of $" + (data.amountToFund).formatCurrency(2, '.', ',') + "</span>");
										
										
										// express checkout
										$("#params_payment_amount").show();
										$("#label_express_checkout_payment_amount").html("$" + (data.amountToFund).formatCurrency(2, '.', ','));
										
										// show express checkout button
										/*
										$('#button_express_checkout')
											.attr({ "title" : "Make payment of $" + (data.amountToFund).formatCurrency(2, '.', ',')})
											.html("<span>Make payment of $" + (data.amountToFund).formatCurrency(2, '.', ',') + "</span>");
										*/
										
									} else {
										
										//console.log("No charges 2");
										
									}
									
								},
								error : function(error) {
									//
								}
							});
							
						} else {
							
							
							$('#section_specify_amount').show();
							$('#text_amount').focus();
							
							
							if(isExpressCheckout) {
								$("#section_express_checkout").show();	
							} else {
								$("#section_credit_card_details").show();
							}
							
							
						}
						
						
						var inProccess = false;
						
						if(isExpressCheckout) {
							
							// express checkout
							// Amount must be at least $10.00
							var validator1 = null;
							validator1 = new validator({
								elements : [
									{
										element : $('#text_amount'),
										status : $('#status_amount'),
										validate : function() {
											return isFund;
										},
										rules : [
											{ method : 'required', message : 'This field is required.' },
											{ method : 'currency', message : 'Please enter a valid currency value.' },
											{ method : 'min', pattern : minDeposit, message : 'Please enter a value greater than or equal to {0}.' }
										]
									}
								],
								submitElement : $('#button_express_checkout'),
								messages : null,
								accept : function () {
									
									// validate if user allow to make payment
									if(auth.userInfo.permissions.makePayment) {
										
										if(!inProccess) {
										
											inProccess = true;
											
											// loader
											loader.show();
											
											if(isFund) {
												amountToFund = Number($.trim($('#text_amount').val()));
											}
											
											setExpressCheckout({
												accountId : accountId,
												amount : amountToFund, // amount to fund
												charges : charges,
												success : function(data) {
													
													inProccess = false;
													
													if(data.redirectUrl != undefined) {
														location.href = data.redirectUrl; 
													}
													
													
													// clearErrors();
													// stop loader
													//loader.hide();
													
												},
												error: function(error) {
													
													inProccess = false;
													
													// stop loader
													loader.hide();
													
													//errorMessenger(error);
													
													//console.log(JSON.stringify(error));
													
													
												}
											});
											
										}
										
									} else {
										
										var modal = new lightFace({
											title : "You are not allowed to make payments.",
											message : "You are not allowed to make payments.<br/> Please contact our support team for assistance at <a href=\"mailto:support@inqwise.com\" title=\"support@inqwise.com\">support@inqwise.com</a>.",
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
										
									}
									
								}
							});
						
						
						} else {
							
						
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
										element : $('#text_credit_card_number'),
										status : $('#status_credit_card_number'),
										rules : [
											{ method : 'required', message : 'This field is required.' },
											{ method : 'creditcard' }
										]
									},
									{ 
										element : $('#text_cvv2_number'), 
										status : $('#status_cvv2_number'), 
										rules : [ 
											{ method : 'required' },
											{ method : 'minlength', pattern : 3 }
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
									},
									{
										element : $('#text_amount'),
										status : $('#status_amount'),
										validate : function() {
											return isFund;
										},
										rules : [
											{ method : 'required', message : 'This field is required.' },
											/* { method : 'number', message : 'Please enter a valid number.' } */
											{ method : 'currency', message : 'Please enter a valid currency value.' },
											{ method : 'min', pattern : 0.01, message : 'Please enter a value greater than or equal to {0}.' }
										]
									}
								],
								submitElement : $('#button_submit_payment'),
								messages : null,
								accept : function () {
									
									// validate if user allow to make payment
									if(auth.userInfo.permissions.makePayment) {
										
										if(!inProccess) {
										
											inProccess = true;
											
											// loader
											loader.show();
											
											if(isFund) {
												amountToFund = Number($.trim($('#text_amount').val()));
											}
											
											directPayment({
												userId : userId,
												accountId : accountId,
												firstName : $('#text_first_name').val().replace(/\r/g, ""),
												lastName : $('#text_last_name').val().replace(/\r/g, ""),
												creditCardTypeId : $('#select_credit_card_type').val(),
												creditCardNumber : $.trim($('#text_credit_card_number').val()),
												expDateMonth : $('#select_exp_date_month').val(),
												expDateYear : $('#select_exp_date_year').val(),
												cvv2Number : $('#text_cvv2_number').val(),
												address1 : $('#text_address1').val(),
												address2 : $('#text_address2').val(),
												city : $('#text_city').val(),
												countryCode : $('#select_country').find('option:selected').attr("iso2"),
												countryId : $('#select_country').val(),
												stateId : ($('#select_country').val() == "232" ? $('#select_state').val() : ($('#select_country').val() == "39" ? $('#select_province').val() : "" )),
												postalCode : $('#text_zip').val(),
												amount : amountToFund,
												charges : charges,
												isAddressChanged : !hasBusinessAddress,
												success : function(data) {
													
													inProccess = false;
													
													
													
													
													
													
													clearErrors();
													// stop loader
													loader.hide();
													
													
													
													$('#form_request').hide();
													// payment details
													$('#section_payment_summary').hide();
													
													
													
													
													
													
													// show thank you
													$('#form_accept').show();
													
													$('#label_paid_amount').text("$" + (amountToFund).formatCurrency(2, '.', ','));
													
													
													var returnUrl = $.getUrlParam("return_url");
													if(returnUrl != "") {
														location.href = returnUrl;
													}
													
													
													
													
												},
												error : function(error) {
													
													inProccess = false;
													
													// stop loader
													loader.hide();
													
													errorMessenger(error);
													
												}
											});
										
										}
										
									} else {
										
										var modal = new lightFace({
											title : "You are not allowed to make payments.",
											message : "You are not allowed to make payments.<br/> Please contact our support team for assistance at <a href=\"mailto:support@inqwise.com\" title=\"support@inqwise.com\">support@inqwise.com</a>.",
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
										
									}
									
								},
								error : function() {
									//		
								}
							});
						
						
						}
						
						
					},
					error: function(error) {
						
						alert("ERR -> " + JSON.stringify(error));
						
					}
				});
				
				// country
				$('#select_country').bind("change keyup", function() {
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
			
		},
		error : function(error) {
			
		}
	});
	
});
</script>
