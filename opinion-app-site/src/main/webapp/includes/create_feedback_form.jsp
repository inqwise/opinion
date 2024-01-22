<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.inqwise.opinion.cms.common.IPage"%>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>


<div>
	<div>
		<h1>Create a New Comment Card</h1>
		<div style="padding-top: 16px;"><p></p></div>
		<form id="form_add_opinion" name="form_add_opinion" method="post" accept-charset="UTF-8" onsubmit="return validateForm(this)" style="padding: 0px; margin: 0px;">
			<h2>How Would You Like to Create a Comment Card?</h2>
			<p>Select one of the options below to get started.<br/>
		If you already have an idea for your comment card, just create a new comment card from scratch.<br/>
			</p>
			<p><br /></p>
			<div class="row">
				<div class="cell" style="width: 22px;"><input type="radio" name="opinion" value="0" checked="checked" class="radio-button" style="padding-left: 0px;" /></div>
				<div class="cell">Create a new comment card from scratch</div>
			</div>
			<div class="row">
				<div class="cell" style="width: 22px;"><input type="radio" name="opinion" value="1"  class="radio-button" /></div>
				<div class="cell">Copy an existing comment card</div>
			</div>
			<div class="row">
				<div class="cell">
					<select disabled="disabled">
						<option>-- Select an existing Comment Cards --</option>
						
					</select>
				</div>
			</div>
			<div class="row"></div>
			<h2>Enter a Name for this Comment Card:</h2>
			<div class="row"></div>
			
			<div class="row">
				<div class="cell" style="width: 130px;"><span>Name: *</span></div>
				<div class="cell"><input type="text" id="name" autocomplete="off" validation="required" style="width: 325px;" /></div>
				
			</div>
			<div class="row" style="height: 70px;">
				<div class="cell" style="width: 130px;"><span>Description:</span></div>
				<div class="cell">
					<textarea id="description" autocomplete="off" style="border: 1px solid rgb(204, 204, 204); border-color:#666666 #ccc #ccc; overflow: hidden; width: 314px; height: 60px; font-size: 11px; color: rgb(51, 51, 51); font-family: arial; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;" ></textarea>
				</div>
			</div>
			<div class="row"></div>
			<div class="row">
				<div class="cell" style="width: 130px;"></div>
				<div class="cell"><a href="javascript:;" class="button-yellow" title="Create" id="submit_form_button"><span>Create</span></a></div>
				<div class="cell" style="margin-left: 12px; line-height: 21px;"><a href="<%=absoluteURL %>" title="Cancel">Cancel</a></div>
			</div>
			
		</form>
	</div>
</div>






