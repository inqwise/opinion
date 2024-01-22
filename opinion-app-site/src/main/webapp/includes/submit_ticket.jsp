
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>

<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<h1><%= p.getHeader() %></h1>
<div>
	<div class="wrapper-content-left">
		<div style="padding-bottom: 28px;">
			If you can't find a solution to your problems in our <a href="<%=absoluteURL%>/knowledgebase" title="knowledgebase">knowledgebase</a>, you can submit a ticket by selecting the appropriate department below.
		</div>
		<div>
			<div class="row">
				<div class="cell" style="width: 130px;"><span>* Ticket Type:</span></div>
				<div class="cell">
					<select>
						<option value="0">Select a Type...</option>
						<option value="1">Software</option>
						<option value="2">Billing Issue</option>
						<option value="3">Upgrades</option>
						<option value="4">Other</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="cell" style="width: 130px;"><span>Priority:</span></div>
				<div class="cell">
					<select>
						<option value="1">High</option>
						<option value="2">Medium</option>
						<option value="3">Low</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="cell" style="width: 130px;"><span>* Message Subject:</span></div>
				<div class="cell">
					<div><input id="input_subject" name="subject" type="text" style="width: 200px;" maxlength="50" autocomplete="off" /></div>
					<div><label id="status_subject"></label></div>
				</div>
			</div>
			<div class="row" style="height: 72px;">
				<div class="cell" style="width: 130px;"><span>* Message:</span></div>
				<div class="cell">
					<div>
						<textarea id="textarea_message" name="message" autocomplete="off" maxlength="255" style="width: 314px; height: 64px;"></textarea>
					</div>
					<div><label id="status_message"></label></div>
				</div>
			</div>
			
			<div style="height: 24px; overflow; hidden;"></div>
			<div class="row">
				<div class="cell" style="width: 130px;">
					&nbsp;
				</div>
				<div class="cell">
					<a id="button_submit" title="Submit" class="button-white" href="javascript:;"><span>Submit</span></a>
				</div>
			</div>
		</div>
	</div>
</div>
