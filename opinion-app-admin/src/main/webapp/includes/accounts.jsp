
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
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
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">			
				<h1>Accounts</h1>
			</td>
			<td class="cell-right">
				
			</td>
		</tr>
	</table>
	<div>
		<div>
			<div style="clear: both; Xpadding-top: 12px;">
				
				<!--
				<div style="height: 28px;">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td></td>
							<td valign="top">
								<div class="datepicker-text" id="datepicker_text">
									<span class="datepicker-date-range-container"><b id="datepicker_date_range_value"></b></span>
									<div style="clear: both; display: none; position: absolute; z-index: 100; background: #fff; border: 1px solid #555; right:0; margin-right: -1px;" id="datepicker_editor">
										<div style="overflow: hidden;">
											<div style="height: 152px;width: 588px;Xbackground: #f2f2f2;">
												<div id="datepicker1"></div>
											</div>
											<div style="clear: both; padding: 10px;">
												<div class="params">
													<div class="param-name">Date Range:</div>
													<div class="param-value">
														<select id="datepicker_select_date_range" autocomplete="off">
															<option value="0">Custom</option>
															<option value="1">Today</option>
															<option value="2">Yesterday</option>
															<option value="3">Last Week</option>
															<option value="4">Last Month</option>
														</select>
													</div>
												</div>
												<div id="form_datepicker_custom_date_range" style="clear: both">
													<div class="params">
														<div class="param-name">From:</div>
														<div class="param-value">
															<input type="text" id="datepicker_text_from_date" size="12" maxlength="12" />
														</div>
														<div class="param-value" style="margin: 0 6px;">To:</div>
														<div class="param-value">
															<input type="text" id="datepicker_text_to_date" size="12" maxlength="12" />
														</div>
													</div>
												</div>
												<div class="params" style="clear: both;">
													<div class="param-name">&nbsp;</div>
													<div class="param-value">
														<a href="javascript:;" class="button-blue" title="Apply" id="datepicker_button_apply"><span>Apply</span></a>&nbsp;&nbsp;<a href="javascript:;" title="Cancel" id="datepicker_button_cancel">Cancel</a>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
				-->
				<div id="table_accounts"></div>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript" src="<%=applicationURL%>/scripts/datepicker/datepicker.js" charset="utf-8"></script>

<script type="text/javascript">
var getAccounts = function(params) {
	
	var obj = {
		accounts : {
			getAccounts : {
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
        	if(data.accounts.getAccounts.error != undefined) {
				
        		errorHandler({
					error : data.accounts.getAccounts.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.accounts.getAccounts);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.accounts.getAccounts);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};





/*
var now = new Date();
var before = new Date();
	before.setDate(now.getDate()-30);

var formatedDateRange = null;
var dateRange = [before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)]; //[before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)];
var newDateRange = [before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)]; //[before.format(dateFormat.masks.longDate),  now.format(dateFormat.masks.longDate)]; //[before.format(dateFormat.masks.isoDate),  now.format(dateFormat.masks.isoDate)];

var getDateRange = function() {
	var tmp = [];
	$.each(dateRange, function(n, v){
		tmp.push(v);
	});
	return tmp;
};

var getNewDateRange = function() {
	var tmp = [];
	$.each(newDateRange, function(n, v){
		tmp.push(v);
	});
	return tmp;
};

var parseDate = function(input, format) {
	format = format || 'yyyy-mm-dd'; // default format
	var parts = input.match(/(\d+)/g), 
	     i = 0, fmt = {};
	// extract date-part indexes from the format
	format.replace(/(yyyy|dd|mm)/g, function(part) { fmt[part] = i++; });
	return new Date(parts[fmt['yyyy']], parts[fmt['mm']]-1, parts[fmt['dd']]);
};

var formatDateRange = function(range) {
	var a = parseDate(range[0]);// new Date(range[0]);
	var b = parseDate(range[1]); // new Date(range[1]);
	return {
		fromDate : a.format(dateFormat.masks.mediumDate),
		toDate : b.format(dateFormat.masks.mediumDate)
	};
};
*/

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

var tableAccounts = null;
var accounts = [];
var renderTableAccounts = function() {
	
	$('#table_accounts').empty();
	tableAccounts = $('#table_accounts').dataTable({
		tableColumns : [
			{ key : 'accountId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'accountName', label : 'Account', sortable : true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/account-details?account_id=" + record.accountId  + "\" title=\"" + record.accountName + "\">" + record.accountName + "</a>");
			}},
			{ key : 'servicePackageName', label : 'Current Plan', sortable: true, width: 100 },
			{ key : 'isActive', label : 'Status', sortable: true, width: 80, formatter: function(cell, value, record, source) {
            	return getAccountStatus(record.isActive ? 1 : 2); // Expired / Suspended
	        }},
			{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.insertDate) {
					var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
					var right = record.insertDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				}
            }}
		],
		dataSource : accounts, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};



$(function() {
	
	
	/*
	var p = null;
	$('#datepicker_date_range_value').text(formatDateRange(getDateRange()).fromDate + " - " + formatDateRange(getDateRange()).toDate);
	$('.datepicker-date-range-container').click(function() {
		
		p = $(this);
		if(!p.hasClass('active')) {

			

			// Show
			p.addClass('active');
			$('#datepicker_editor').show();


			$('#datepicker1').DatePickerSetDate(getNewDateRange(), true);




			
			var fromDate = new Date(formatDateRange(getNewDateRange()).fromDate);
			var toDate = new Date(formatDateRange(getNewDateRange()).toDate);
			
			$('#datepicker_text_from_date').val(formatDateRange(getNewDateRange()).fromDate);
			$('#datepicker_text_from_date').unbind('focusin focusout keyup').bind('focusin focusout keyup', function() {

				var value = $.trim($(this).val());
				if (value == null || value == '') {
					$(this).addClass('error');
				}

				if(!/Invalid|NaN/.test(new Date(value))) {
					var d = new Date(Date.parse(value));
			        var year = d.getFullYear();
			        if(String(year).length == 4) {
						if((year >= (now.getFullYear() - 100) && year <= now.getFullYear())) {
							fromDate = d;
							if((fromDate <= toDate)) {
								formatedDateRange = [fromDate.format(dateFormat.masks.isoDate),  toDate.format(dateFormat.masks.isoDate)];
								$('#datepicker1').DatePickerSetDate([fromDate.format(dateFormat.masks.isoDate),  toDate.format(dateFormat.masks.isoDate)], true);
								$(this).removeClass('error');
							} else {
								$(this).addClass('error');
							}
						} else {
							$(this).addClass('error');
						}						
				    } else {
				    	$(this).addClass('error');
					}
				} else {
					$(this).addClass('error');
				}
				
			});
			
			$('#datepicker_text_to_date').val(formatDateRange(getNewDateRange()).toDate);
			$('#datepicker_text_to_date').unbind('focusin focusout keyup').bind('focusin focusout keyup', function() {
				
				var value = $.trim($(this).val());
				if (value == null || value == '') {
					$(this).addClass('error');
				}

				if(!/Invalid|NaN/.test(new Date(value))) {
					var d = new Date(Date.parse(value));
			        var year = d.getFullYear();
			        if(String(year).length == 4) {
						if((year >= (now.getFullYear() - 100) && year <= now.getFullYear())) {
							toDate = d;
							if((fromDate <= toDate) && (toDate <= now)) {
								formatedDateRange = [fromDate.format(dateFormat.masks.isoDate),  toDate.format(dateFormat.masks.isoDate)];
								$('#datepicker1').DatePickerSetDate([fromDate.format(dateFormat.masks.isoDate),  toDate.format(dateFormat.masks.isoDate)], true);
								$(this).removeClass('error');
							} else {
								$(this).addClass('error');
							}
						} else {
							$(this).addClass('error');
						}						
				    } else {
				    	$(this).addClass('error');
					}
				} else {
					$(this).addClass('error');
				}
				
			});


			$('#form_datepicker_custom_date_range').show();
			$('#datepicker_select_date_range')[0].selectedIndex = 0;
			$('#datepicker_select_date_range')
				.unbind('change')
				.change(function() {

					var a = new Date();
					var b = new Date();


					if($(this).val() == 0) {
						$('#form_datepicker_custom_date_range').show();

						if(formatedDateRange != null) {
							// from 
							$('#datepicker_text_from_date').val(formatDateRange(formatedDateRange).fromDate);
							// to 
							$('#datepicker_text_to_date').val(formatDateRange(formatedDateRange).toDate);
						}
						
					}
					
					// today
					if($(this).val() == 1) {
						$('#form_datepicker_custom_date_range').hide();
						
						var formated = [a.format(dateFormat.masks.isoDate),  a.format(dateFormat.masks.isoDate)];
						formatedDateRange = formated; 
						
						$('#datepicker1').DatePickerSetDate([a.format(dateFormat.masks.isoDate),  a.format(dateFormat.masks.isoDate)], true);
									
					}

					// yesterday
					if($(this).val() == 2) {
						$('#form_datepicker_custom_date_range').hide();
						
						b.setDate(a.getDate()-1);
						var formated = [b.format(dateFormat.masks.isoDate),  b.format(dateFormat.masks.isoDate)];
						formatedDateRange = formated;
						
						$('#datepicker1').DatePickerSetDate([b.format(dateFormat.masks.isoDate),  b.format(dateFormat.masks.isoDate)], true);
					}

					// last week
					if($(this).val() == 3) {
						$('#form_datepicker_custom_date_range').hide();
						
						b.setDate(a.getDate()-7);
						var formated = [b.format(dateFormat.masks.isoDate),  a.format(dateFormat.masks.isoDate)];
						formatedDateRange = formated; 
						
						$('#datepicker1').DatePickerSetDate([b.format(dateFormat.masks.isoDate),  a.format(dateFormat.masks.isoDate)], true);
					}

					// last month
					if($(this).val() == 4) {
						$('#form_datepicker_custom_date_range').hide();
						
						b.setDate(a.getDate()-30);
						var formated = [b.format(dateFormat.masks.isoDate),  a.format(dateFormat.masks.isoDate)];
						formatedDateRange = formated; 
						
						$('#datepicker1').DatePickerSetDate([b.format(dateFormat.masks.isoDate),  a.format(dateFormat.masks.isoDate)], true);
					}
					
					
				});



			
			
			
			
			
						
		} else {
			
			p.removeClass('active');
			$('#datepicker_editor').hide();
			
		}
		
	});

	$('#datepicker1').DatePicker({
		flat: true,
		date: getDateRange(),
		current: getDateRange()[1],
		calendars: 3,
		mode: 'range',
		starts: 1,
		onRender: function(date) {
			return {
				disabled: (date.valueOf() > now.valueOf())
			}
		},
		onChange: function(formated) {
			formatedDateRange = formated;
		}
	});

	// apply button event
	$('#datepicker_button_apply').unbind('click').click(function() {

		if(formatedDateRange != null) {
			newDateRange = [];
			newDateRange = formatedDateRange;
		}

		$('#datepicker_date_range_value').text(formatDateRange(newDateRange).fromDate + " - " + formatDateRange(newDateRange).toDate);

		
		
		// get new updated data
		getAccounts({
			fromDate : newDateRange[0] + ' 00:00',
			toDate : newDateRange[1] + ' 23:59',
			success: function(data) {
				accounts = data.list;
				renderTableAccounts();
			},
			error : function() {
				accounts = [];
				renderTableAccounts();
			}
		});
		
		
		
		
		//$('#datepicker_text').removeClass('active');
		p.removeClass('active');
		$('#datepicker_editor').hide();

	});

	// cancel button event
	$('#datepicker_button_cancel').unbind('click').click(function() {
		formatedDateRange = [];
		formatedDateRange = getDateRange();
		
		$('#datepicker1').DatePickerSetDate(getDateRange(), true);

		$('#form_datepicker_custom_date_range').show();
		$('#datepicker_select_date_range')[0].selectedIndex = 0;

		if(formatedDateRange != null) {
			// from 
			$('#datepicker_text_from_date').val(formatDateRange(formatedDateRange).fromDate);
			// to 
			$('#datepicker_text_to_date').val(formatDateRange(formatedDateRange).toDate);
		}
		
	});

	$('.datepicker-text').click(function(){ return false; });
	
	jQuery(document).click(function(){
		if(p != undefined) {
			if(p.hasClass('active')) {
				p.removeClass('active');
				$('#datepicker_editor').hide();
			}
		}
	});
	*/
	
	
	
	
	// default request
	getAccounts({
		/* fromDate : newDateRange[0] + ' 00:00',
		toDate : newDateRange[1] + ' 23:59', */
		success: function(data) {
			accounts = data.list;
			renderTableAccounts();
		},
		error : function() {
			accounts = [];
			renderTableAccounts();
		}
	});
	
	
	
});
</script>