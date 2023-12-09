
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%@ page import="com.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.opinion.library.common.countries.ICountry" %>
<%@ page import="com.opinion.library.managers.CountriesManager" %>
<%@ page import="com.opinion.library.common.countries.IStateProvince" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	
	String accountId = request.getParameter("account_id");
	String invoiceId = request.getParameter("invoice_id");
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">			
				<h1><a href="<%=absoluteURL %>/accounts" title="Accounts">Accounts</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-details?account_id=<%=accountId %>" id="header_account_name"></a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/account-invoices?account_id=<%=accountId %>" title="Invoices">Invoices</a>&nbsp;&rsaquo;&nbsp;Invoice #<%=invoiceId %></h1>
			</td>
			<td class="cell-right">
				
			</td>
		</tr>
	</table>
	<div style="clear: both; Xpadding-top: 24px;">
		<div class="wrapper-content-left">
		
		
			<!--
			<div>
				<img src="<%=applicationURL%>/images/logo13wf.png" alt="Inqwise" />
			</div>
			-->
			
			<div style="padding-top: 12px">
				<div>
					Inqwise, Ltd.<br/>
					4G/9 Brener<br/>
					Bat Yam 59486<br/>
					Israel
				</div>
			</div>
			
			<div style="padding-top: 12px">
				<b>VAT Number</b> 514943901
			</div>
		
			<div style="padding-bottom: 20px;">
				<h3 class="ui-header-light">Invoice Details</h3>
				<div style="padding-top: 12px;">
					<div class="params">
						<div class="param-name">Invoice Id:</div>
						<div class="param-value">
							<b style="color: #000"><%=invoiceId %></b>
						</div>
					</div>
					
					<div id="section_invoice_date" style="display: none">
						<div class="params">
							<div class="param-name">Invoice Date:</div>
							<div class="param-value">
								<b id="label_invoice_date" style="color: #000"></b>
							</div>
						</div>
					</div>
					
					<div id="section_billing_period_view" style="display: none">
						<div class="params">
							<div class="param-name">Billing Period:</div>
							<div class="param-value">
								<b id="label_invoice_billing_period" style="color: #000"></b>
							</div>
						</div>
					</div>
					
					<div id="section_billing_period_edit" style="display: none">
						<div class="params">
							<div class="param-name">Billing Period:</div>
							<div class="param-value">
								<input type="text" id="datepicker_invoice_from_date" maxlength="12" style="width: 82px;" autocomplete="off" />
							</div>
							<div class="param-value" style="padding-left: 6px;">
								<input type="text" id="datepicker_invoice_to_date" maxlength="12" style="width: 82px;" autocomplete="off" />
							</div>
						</div>
					</div>
					
					<div class="params">
						<div class="param-name">Billing Id:</div>
						<div class="param-value">
							<b id="label_account_id" style="color: #000"></b>
						</div>
					</div>
					<div class="params">
						<div class="param-name">Status:</div>
						<div class="param-value">
							<b style="color: #000" id="label_invoice_status"></b>
						</div>
					</div>
				</div>
			</div>
			
			
			
			
			<div id="section_bill_to_edit" style="padding-bottom: 24px; display: none;">
				<h3 class="ui-header-light">Bill To:</h3>
				<div style="padding-top: 12px;">
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
			
			<div id="section_bill_to_view" style="padding-bottom: 20px; display: none;">
				<h3 class="ui-header-light">Bill To:</h3>
				<div style="padding-top: 12px;" id="label_invoice_bill_to"></div>
			</div>
			
			<div id="section_charges_edit" style="clear: both; padding-top: 24px; display: none;">
				<div>
					<div id="table_invoice_charges"></div>
				</div>
				<div>
					<table class="ti">
						<tfoot>
							<tr>
								<td class="align-right" colspan="5">&nbsp;</td>
								<td class="align-right cell-sub-total">&nbsp;</td>
							</tr>
							<tr>
								<td class="align-right label-total" colspan="5"><b>Total Charges:</b></td>
								<td class="align-right cell-total" style="width: 74px;" id="label_invoice_total">$0.00</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			
			<div id="section_charges_view" style="clear: both; display: none;">
				<h3 class="ui-header-light">Charges</h3>
					<div style="padding-top: 12px;">
					<table class="ti">
						<thead>
							<tr>
								<th>Date</th>
								<th>Item</th>
								<th>Description</th>
								<th>Charge #</th>
								<th class="align-right" style="width:74px;">Amount</th>
							</tr>
						</thead>
						<tbody id="table_invoice_charges_body"></tbody>
						<tfoot>
							<tr>
								<td class="align-right" colspan="4">&nbsp;</td>
								<td class="align-right cell-sub-total">&nbsp;</td>
							</tr>
							<tr>
								<td class="align-right label-total" colspan="4"><b>Total Charges:</b></td>
								<td class="align-right cell-total" id="label_invoice_total_charges">$0.00</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			
			<div style="clear: both; padding-top: 24px;">
				<h3 class="ui-header-light">Payments</h3>
				<div style="padding-top: 12px;">
					<table class="ti">
						<thead>
							<tr>
								<th>Transaction Date</th>
								<th>Transaction Id</th>
								<th>Description</th>
								<th class="align-right" style="width:74px;">Amount</th>
							</tr>
						</thead>
						<tbody id="table_invoice_transactions_body"></tbody>
						<tfoot>
							<tr>
								<td class="align-right" colspan="3">&nbsp;</td>
								<td class="align-right cell-sub-total">&nbsp;</td>
							</tr>
							<tr>
								<td class="align-right label-total" colspan="3"><b>Total:</b></td>
								<td class="align-right cell-total" id="label_invoice_payments">$0.00</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			
			<div id="section_notes_edit" style="display: none;">
				<div style="clear: both; padding-top: 48px;">
					<div style="height: 72px;" class="params">
						<div class="param-name">Notes:</div>
						<div class="param-value">
							<textarea id="textarea_notes" name="notes" autocomplete="off" maxlength="1000" style="width: 314px; height: 64px;"></textarea>
						</div>
					</div>
				</div>
				<div style="height: 24px; overflow: hidden;clear: both;"></div>
				<div class="params">
					<div class="param-name"></div>
					<div class="param-value">
						<a id="button_update" class="button-blue" title="Save as Draft"><span>Save as Draft</span></a>
					</div>
					<div class="param-value" style="padding-left: 6px;">
						<a id="button_open" class="button-green" title="Open"><span>Open</span></a>
					</div>
				</div>
			</div>
			
			<div id="section_notes_view" style="display: none;">
				<div style="clear: both; padding-top: 48px;">
					* May include estimated US sales tax, VAT and GST<br/>
					** This is not a VAT invoice<br/>
					All charges and prices are in US Dollars<br/><br/>
					All services are sold by Inqwise, Ltd.<br/>
					The above charges include charges incurred by your account as well as by all accounts you are responsible for through Consolidated Billing.<br/><br/>
					Thank you for using Inqwise.<br/>
					Sincerely,<br/>
					The Inqwise Team
				</div>
			</div>
		
		</div>
		
	</div>
</div>


<script type="text/javascript" src="<%=applicationURL%>/scripts/datepicker/datepicker.js"></script>
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

var getInvoiceDetails = function(params) {

	var obj = {
		invoices : {
			getInvoiceDetails : {
				accountId : params.accountId,
				invoiceId : params.invoiceId,
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
			if(data.invoices.getInvoiceDetails != undefined) {
				if(data.invoices.getInvoiceDetails.error != undefined) {
					
					errorHandler({
						error : data.invoices.getInvoiceDetails.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.invoices.getInvoiceDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.invoices.getInvoiceDetails);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var updateInvoice = function(params) {

	var obj = {
		invoices : {
			updateInvoice : {
				accountId : params.accountId,
				invoiceId : params.invoiceId,
				statusId : params.statusId,
				invoiceDate : params.invoiceDate,
				companyName : params.companyName,
				firstName : params.firstName,
				lastName : params.lastName,
				address1 : params.address1,
				address2 : params.address2,
				city : params.city,
				countryId : params.countryId,
				stateId : params.stateId,
				postalCode : params.postalCode,
				phone1 : params.phone1,
				notes : params.notes,
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
			if(data.invoices.updateInvoice != undefined) {
				if(data.invoices.updateInvoice.error != undefined) {
					
					errorHandler({
						error : data.invoices.updateInvoice.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.invoices.updateInvoice);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.invoices.updateInvoice);
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
				billId : params.invoiceId
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

var removeCharges = function(params) {

	var obj = {
		invoices : {
			removeCharges : {
				charges : params.charges
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
        	if(data.invoices.removeCharges.error != undefined) {
				
        		errorHandler({
					error : data.invoices.removeCharges.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.invoices.removeCharges);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.invoices.removeCharges);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var getInvoiceStatus = function(statusId) {
	var s = "";
	switch(statusId) {
		case 1 :
			s = "Draft";
			break;
		case 2 : 
			s = "Open";
			break;
	}
	return s;
};

var getTransactionType = function(typeId) {
	var s = "";
	switch(typeId) {
		case 2 : 
			s = "Payment";
			break;
		default :
			s = typeId;
			break;
	}
	return s;
};

var getCreditCardType = function(creditCardTypeId) {
	var s = "";
	switch(creditCardTypeId) {
		case 1 : 
			s = "Visa";
			break;
		case 2 :
			s = "MasterCard";
			break;
		case 3 :
			s = "Discover";
			break;
		case 4 :
			s = "American Express";
			break;
	}
	return s;
};


var accountId = <%= accountId %>;
var invoiceId = <%=invoiceId%>;
var charges = [];
var tableCharges = null;
var renderTableCharges = function() {
	
	$('#table_invoice_charges').empty();
	tableCharges = $('#table_invoice_charges').dataTable({
		tableColumns : [
			{ key : 'chargeDate', label : 'Date' },
			{ key : 'name', label : 'Item' },
			{ key : 'description', label : 'Description' },
			{ key : 'chargeId', label : 'Charge #', width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter : function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-charge?account_id=<%= accountId %>&charge_id=" + record.chargeId + "\" title=\"" + record.chargeId + "\">" + record.chargeId + "</a>");
			}},
			{ key : 'amount', label : 'Amount', width: 74, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } }, formatter: function(cell, value, record, source) {
				return "$" + (record.amount).formatCurrency(2, '.', ',');
			}}
		],
		dataSource : charges,
		pagingStart : 100,
		disablePaging : true,
		disableFiltering : true,
		selectable : true,
		actions : [
			{ 
				label : "Move to Uninvoiced List", 
				fire : function(records, source) {
				
					if(records.length > 0) {
				
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].chargeId);
						});
						
						removeCharges({
							charges : list,
							success : function() {
								
								getCharges({
									accountId : accountId,
									invoiceId : invoiceId,
									success : function(data) {
										charges = data.list;
										renderTableCharges();
									},
									error : function(error) {
										charges = [];
										renderTableCharges();
									}
								});
								
							},
							error: function() {
								alert("ERR");
							}
						});
						
					}
					
				}
			}
			           
		]
	});
	
};

$(function() {

	getAccountDetails({
		accountId : accountId,
		success : function(data) {

			$('#level1').text(data.accountName);
			$('#header_account_name').text(data.accountName);
			
			
			
			$('#label_account_id').text(data.accountId);
			
			
			getInvoiceDetails({
				accountId : accountId,
				invoiceId : invoiceId,
				success : function(data) {
					
					
					// details
					
					$('#label_invoice_date').text(data.invoiceDate);
					$('#label_invoice_billing_period').text(data.fromDate + " - " + data.toDate);
					$('#label_invoice_status').text(getInvoiceStatus(data.statusId));
					
					
					if(data.statusId == 1) {
						
						// draft
						
						
						$('#section_billing_period_edit').show();
						$('#section_bill_to_edit').show();
						$('#section_charges_edit').show();
						$('#section_notes_edit').show();

						
						
						
						/*
						var invoiceDate = null;
						if(data.invoiceDate == null) {
							//var now = new Date();
							//invoiceDate = now.format(dateFormat.masks.isoDate);
							invoiceDate = new Date();
						} else {
							invoiceDate = new Date(Date.parse(data.invoiceDate));
						}
						*/
						
						
						//var invoiceDate = new Date(Date.parse(data.invoiceDate));
						// var d = new Date(Date.parse(value));
						//alert(data.invoiceDate + "__________" + invoiceDate.format(dateFormat.masks.isoDate));
						//alert(data.insertDate);
						//alert(now.format(dateFormat.masks.isoDate) + "___________________________" + now.format(dateFormat.masks.mediumDate));
						
						
						
						/*
						$('#datepicker_invoice_date').val(invoiceDate.format(dateFormat.masks.isoDate));
						$('#datepicker_invoice_date').DatePicker({
							date: invoiceDate.format(dateFormat.masks.isoDate),
							current: invoiceDate.format(dateFormat.masks.isoDate),
							starts: 1,
							position: 'right',
							onBeforeShow: function(){
								$('#datepicker_invoice_date').DatePickerSetDate($('#datepicker_invoice_date').val(), true);
							},
							onChange: function(formated, dates){
								
								// alert(formated);
								// $('input').DatePickerSetDate(date, shiftTo);
								
								$('#datepicker_invoice_date').val(formated);
								$('#datepicker_invoice_date').DatePickerHide();
							}
						});
						*/
						
						
						
						
						
						
						
						
						// fromDate
						var fromDate = new Date(Date.parse(data.fromDate));
						$('#datepicker_invoice_from_date').val(fromDate.format(dateFormat.masks.mediumDate));
						$('#datepicker_invoice_from_date').DatePicker({
							date: fromDate.format(dateFormat.masks.isoDate),
							current: fromDate.format(dateFormat.masks.isoDate),
							starts: 1,
							position: 'right',
							onBeforeShow: function(){
								var lastDate = new Date($('#datepicker_invoice_from_date').val());
								$('#datepicker_invoice_from_date').DatePickerSetDate(lastDate, true);
							},
							onChange: function(formated, dates){
								
								var newDate = new Date(formated);
								
								$('#datepicker_invoice_from_date').val(newDate.format(dateFormat.masks.mediumDate));
								$('#datepicker_invoice_from_date').DatePickerHide();
								
								// refresh
								getInvoiceDetails({
									accountId : accountId,
									invoiceId : invoiceId,
									fromDate : new Date($('#datepicker_invoice_from_date').val()).format(dateFormat.masks.isoDate) + " 00:00",
									toDate : new Date($('#datepicker_invoice_to_date').val()).format(dateFormat.masks.isoDate) + " 23:59",
									success : function(data) {
										
										// transactions
										$('#table_invoice_transactions_body').empty();
										if(data.transactions.list.length != 0) {
											for(var t = 0; t < data.transactions.list.length; t++) {
												$("<tr>" +
													"<td>" + data.transactions.list[t].modifyDate + "</td>" +
													"<td>" + data.transactions.list[t].transactionId + "</td>" +
													"<td>" + getTransactionType(data.transactions.list[t].typeId) + ": "  + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" +
													/*"<td>" + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" + */
													"<td class=\"align-right\">$" + (data.transactions.list[t].amount).formatCurrency(2, '.', ',') + "</td>" +
												"</tr>").appendTo("#table_invoice_transactions_body");
											}
										} else {
											$("<tr class=\"nop\"><td colspan=\"4\"><b>No records found.</b></td></tr>").appendTo("#table_invoice_transactions_body");
										}
										
										// total payments / refunds
										$('#label_invoice_payments').text("$" + (data.transactions.totalCredit).formatCurrency(2, '.', ','));
										
									},
									error : function() {
										
										alert("ERR");
										
									}
								});
								
							}
						});
						
						// toDate
						var toDate = new Date(Date.parse(data.toDate));
						$('#datepicker_invoice_to_date').val(toDate.format(dateFormat.masks.mediumDate));
						$('#datepicker_invoice_to_date').DatePicker({
							date: toDate.format(dateFormat.masks.isoDate),
							current: toDate.format(dateFormat.masks.isoDate),
							starts: 1,
							position: 'right',
							onBeforeShow: function(){
								var lastDate = new Date($('#datepicker_invoice_to_date').val());
								$('#datepicker_invoice_to_date').DatePickerSetDate(lastDate, true);
							},
							onChange: function(formated, dates){
								
								var newDate = new Date(formated);
								
								$('#datepicker_invoice_to_date').val(newDate.format(dateFormat.masks.mediumDate));
								$('#datepicker_invoice_to_date').DatePickerHide();
								
								// refresh
								getInvoiceDetails({
									accountId : accountId,
									invoiceId : invoiceId,
									fromDate : new Date($('#datepicker_invoice_from_date').val()).format(dateFormat.masks.isoDate) + " 00:00",
									toDate : new Date($('#datepicker_invoice_to_date').val()).format(dateFormat.masks.isoDate) + " 23:59",
									success : function(data) {
										
										// transactions
										$('#table_invoice_transactions_body').empty();
										if(data.transactions.list.length != 0) {
											for(var t = 0; t < data.transactions.list.length; t++) {
												$("<tr>" +
													"<td>" + data.transactions.list[t].modifyDate + "</td>" +
													"<td>" + data.transactions.list[t].transactionId + "</td>" +
													"<td>" + getTransactionType(data.transactions.list[t].typeId) + ": "  + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" +
													/*"<td>" + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" + */
													"<td class=\"align-right\">$" + (data.transactions.list[t].amount).formatCurrency(2, '.', ',') + "</td>" +
												"</tr>").appendTo("#table_invoice_transactions_body");
											}
										} else {
											$("<tr class=\"nop\"><td colspan=\"4\"><b>No records found.</b></td></tr>").appendTo("#table_invoice_transactions_body");
										}
										
										// total payments / refunds
										$('#label_invoice_payments').text("$" + (data.transactions.totalCredit).formatCurrency(2, '.', ','));
										
									},
									error : function() {
										
										alert("ERR");
										
									}
								});
								
							}
						});
						
						
						// bill to
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
						
						
						
						// charges
						if(data.charges.list.length != 0) {
							charges = data.charges.list;
							renderTableCharges();
						} else {
							charges = [];
							renderTableCharges();
						}
						
						
						
						$('#label_invoice_total').text("$" + (data.amount).formatCurrency(2, '.', ',')); // total charges
						$('#textarea_notes').val(data.notes);
						
						
						
						// transactions
						if(data.transactions.list.length != 0) {
							for(var t = 0; t < data.transactions.list.length; t++) {
								$("<tr>" +
									"<td>" + data.transactions.list[t].modifyDate + "</td>" +
									"<td>" + data.transactions.list[t].transactionId + "</td>" +
									"<td>" + getTransactionType(data.transactions.list[t].typeId) + ": "  + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" +
									/*"<td>" + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" + */
									"<td class=\"align-right\">$" + (data.transactions.list[t].amount).formatCurrency(2, '.', ',') + "</td>" +
								"</tr>").appendTo("#table_invoice_transactions_body");
							}
						} else {
							$("<tr class=\"nop\"><td colspan=\"4\"><b>No records found.</b></td></tr>").appendTo("#table_invoice_transactions_body");
						}
						
						// total payments / refunds
						$('#label_invoice_payments').text("$" + (data.transactions.totalCredit).formatCurrency(2, '.', ','));
						
						
						// validate
						$('#button_update').click(function() {
							
							updateInvoice({
								invoiceId : invoiceId,
								invoiceDate : null, // $('#datepicker_invoice_date').val() + " 00:00",
								statusId : 1,
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
								notes : $('#textarea_notes').val(),
								fromDate : $('#datepicker_invoice_from_date').val() + " 00:00",
								toDate : $('#datepicker_invoice_to_date').val() + " 23:59",
								success : function(data) {
									
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
								error: function() {
									alert("ERR");
								}
							});
							
						});
						
						
						
						
						// open invoice -> Mark as Open
						$('#button_open').click(function() {
							
							
							var modal = new lightFace({
								title : "Opening Invoice",
								message : "Are you sure you want to open this invoice?",
								actions : [
									{ 
										label : "Open", 
										fire : function() {
											
											updateInvoice({
												invoiceId : invoiceId,
												invoiceDate : null, // $('#datepicker_invoice_date').val() + " 00:00",
												statusId : 2,
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
												notes : $('#textarea_notes').val(),
												fromDate : $('#datepicker_invoice_from_date').val() + " 00:00",
												toDate : $('#datepicker_invoice_to_date').val() + " 23:59",
												success : function(data) {
													
													if(data.invoiceId != undefined) {
														// refresh page
														location.href = "<%=absoluteURL %>/account-invoice?account_id=<%=accountId %>&invoice_id=" + data.invoiceId;
													}
													
												},
												error: function() {
													alert("ERR");
												}
											});
											
											//modal.close(); 
										}, 
										color : "blue" 
									},
									{ 
										label : "Cancel", 
										fire : function() { 
											modal.close(); 
										}, 
										color : "white" 
									}
								],
								overlayAll : true
							});
							
							
						});
						
						
						
					} else if(data.statusId == 2) {
						
						// open
						$('#section_invoice_date').show();
						$('#section_billing_period_view').show();
						$('#section_bill_to_view').show();
						$('#section_charges_view').show();
						$('#section_notes_view').show();
						
						
						
						// details
						
						
						
						
						
						
						
						// bill to
						$("<div>" +
							(data.companyName != null ? data.companyName + "<br/>" : "") +
							data.firstName + " " + data.lastName + "<br/>" +
							data.address1 + "<br/>" +
							(data.address2 != null ? data.address2 + "<br/>" : "") +
							data.city + (data.stateId != null ? ", " + data.stateId : "") + "<br/>" +
							data.postalCode + "<br/>" +
							data.countryId + "<br/>" +
							(data.phone1 != null ? data.phone1 : "") +
						"</div>").appendTo("#label_invoice_bill_to");
			
						
						// charges
						if(data.charges.list.length != 0) {
							for(var i = 0; i < data.charges.list.length; i++) {
								$("<tr>" +
									"<td>" + data.charges.list[i].chargeDate + "</td>" +
									"<td>" + data.charges.list[i].name + "</td>" +
									"<td>" + (data.charges.list[i].description != undefined ? data.charges.list[i].description : "") + "</td>" +
									"<td>" + data.charges.list[i].chargeId + "</a></td>" +
									"<td class=\"align-right\">$" + (data.charges.list[i].amount).formatCurrency(2, '.', ',') + "</td>" +
								"</tr>").appendTo("#table_invoice_charges_body");
							}
						} else {
							$("<tr class=\"nop\"><td colspan=\"5\"><b>No records found.</b></td></tr>").appendTo("#table_invoice_charges_body");
						}
						
						// total charges
						$('#label_invoice_total_charges').text("$" + (data.amount).formatCurrency(2, '.', ',')); // subTotal + VAT (TAX or Discount)
						
						
						// transactions
						if(data.transactions.list.length != 0) {
							for(var t = 0; t < data.transactions.list.length; t++) {
								$("<tr>" +
									"<td>" + data.transactions.list[t].modifyDate + "</td>" +
									"<td>" + data.transactions.list[t].transactionId + "</td>" +
									"<td>" + getTransactionType(data.transactions.list[t].typeId) + ": "  + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" +
									/*"<td>" + (data.transactions.list[t].creditCard != undefined ? getCreditCardType(data.transactions.list[t].creditCardTypeId) + " ****" + data.transactions.list[t].creditCard : "") + "</td>" + */
									"<td class=\"align-right\">$" + (data.transactions.list[t].amount).formatCurrency(2, '.', ',') + "</td>" +
								"</tr>").appendTo("#table_invoice_transactions_body");
							}
						} else {
							$("<tr class=\"nop\"><td colspan=\"4\"><b>No records found.</b></td></tr>").appendTo("#table_invoice_transactions_body");
						}
						
						// total payments / refunds
						$('#label_invoice_payments').text("$" + (data.transactions.totalCredit).formatCurrency(2, '.', ','));
						
						
					}
					
					
				},
				error : function(error) {
					//
				}
			});
		
		}
	});

});
</script>
