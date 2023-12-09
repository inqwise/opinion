
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>
<%@ page import="com.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@page import="com.opinion.library.common.errorHandle.OperationResult"%>
<%@page import="com.opinion.library.common.countries.ICountry"%>
<%@page import="com.opinion.library.managers.CountriesManager"%>
<%@page import="com.opinion.library.common.countries.IStateProvince"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1><a href="<%=absoluteURL %>/account" title="Account">Account</a>&nbsp;&rsaquo;&nbsp;<a href="<%=absoluteURL %>/users" title="Users">Users</a>&nbsp;&rsaquo;&nbsp;User</h1>
			</td>
			<td class="cell-right">
				
			</td>
		</tr>
	</table>
	<div>
		
		<div class="params">
			<div class="param-name">Username:</div>
			<div class="param-value">
				<span id="label_user_name" style="color: #000"></span>
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
			<div class="param-name" style="width: 130px;">Phone 1:</div>
			<div class="param-value">
				<input type="text" id="text_phone1" name="phone1" autocomplete="off" />
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
				<input type="text" size="25" maxlength="100" id="text_address2" name="address2" autocomplete="off" />(optional)
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
			<div class="param-name">State:</div>
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
		<div class="params">
			<div class="param-name">ZIP Code:</div>
			<div class="param-value">
				<input type="text" size="10" maxlength="10" id="text_zip" name="zip" autocomplete="off" />
				<div class="param-value-desc"><em>(5 or 9 digits)</em></div>
			</div>
		</div>
		<div class="params">
			<div class="param-name">Send News Letters:</div>
			<div class="param-value">
				<input type="checkbox" id="checkbox_send_news_letters" />
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
</div>