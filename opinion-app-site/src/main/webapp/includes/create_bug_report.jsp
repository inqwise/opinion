
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.opinion.cms.common.IPage"%>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>


<div>
	<h1>Create a bug report</h1>
	<div>
		<div class="wrapper-content-left">
			
			<div style="padding-bottom: 14px;">
				Create a bug report.<br/>
				Before reporting a bug, please be sure it is a bug and not a request for support.
				<p>Fields indicated with an * are required.</p>
			</div>
			<div style="clear: both">
				<div class="params">
					<div class="param-name">* Title:</div>
					<div class="param-value">
						<div><input id="input_title" name="input-title" type="text" autocomplete="off" placeholder="Brief one line summary of the bug" style="width: 225px;" /></div>
						<div><label id="status_input_title"></label></div>
					</div>
				</div>
				<div class="params" style="height: 72px;">
					<div class="param-name">* Description:</div>
					<div class="param-value">
						<div><textarea id="textarea_description" name="textarea-description" autocomplete="off" placeholder="Detailed description of the problem" style="width: 314px; height: 64px;"></textarea></div>
						<div><label id="status_textarea_description"></label></div>
					</div>
				</div>
				<div class="params">
					<div class="param-name">* Tags:</div>
					<div class="param-value">
						<div><input id="input_tags" name="input-tags" type="text" autocomplete="off" placeholder="e.g. survey, questions" style="width: 225px;" /></div>
						<div><label id="status_input_tags"></label></div>
					</div>
				</div>
				<div class="params" style="height: 192px;">
					<div class="param-name">* Steps to Reproduce:</div>
					<div class="param-value">
						<div><textarea id="textarea_steps_to_reproduce" name="textarea-steps-to-reproduce" autocomplete="off" placeholder="Detailed step-by-step instructions to reproduce the issue including URL's, IDs, etc." style="width: 314px; height: 184px;"></textarea></div>
						<div><label id="status_textarea_steps_to_reproduce"></label></div>
					</div>
				</div>
				<div class="params" style="height: 72px;">
					<div class="param-name">* Expected Behavior:</div>
					<div class="param-value">
						<div><textarea id="textarea_expected_behavior" name="textarea-expected-behavior" autocomplete="off" placeholder="What do you expect to happen when these steps are followed?" style="width: 314px; height: 64px;"></textarea></div>
						<div><label id="status_textarea_expected_behavior"></label></div>
					</div>
				</div>
				<div class="params" style="height: 72px;">
					<div class="param-name">* Actual Behavior:</div>
					<div class="param-value">
						<div><textarea id="textarea_actual_behavior" name="textarea-actual-behavior" autocomplete="off" placeholder="What actually happens when you follow these steps that is contrary to your expectation?" style="width: 314px; height: 64px;"></textarea></div>
						<div><label id="status_textarea_actual_behavior"></label></div>
					</div>
				</div>
				<div style="height: 24px; overflow: hidden; clear: both;"></div>
				<div class="params">
					<div class="param-name">&nbsp;</div>
					<div class="param-value">
						<a id="button_create" title="Create" class="button-blue" href="javascript:;"><span>Create</span></a>
					</div>
					<div class="param-value" style="line-height: 21px;">
						<a href="<%=absoluteURL %>/create-bug-report" title="Cancel" style="margin-left: 6px;">Cancel</a>
					</div>
				</div>
			</div>
			
		</div>
		<div class="wrapper-content-middle"></div>
		<div class="wrapper-content-right"></div>
	</div>
</div>

<script type="text/javascript">
$(function() {

	var defaultFocus = function() {
		$('#input_title').focus();
	};

	defaultFocus(); // set default focus
	
	// validate fields
	var v = null;
	v = new validator({
		elements : [
			{
				element : $('#input_title'),
				status : $('#status_input_title'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				] 
			},
			{
				element : $('#textarea_description'),
				status : $('#status_textarea_description'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				]
			},
			{
				element : $('#input_tags'),
				status : $('#status_input_tags'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				] 
			},
			{
				element : $('#textarea_steps_to_reproduce'),
				status : $('#status_textarea_steps_to_reproduce'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				] 
			},
			{
				element : $('#textarea_expected_behavior'),
				status : $('#status_textarea_expected_behavior'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				] 
			},
			{
				element : $('#textarea_actual_behavior'),
				status : $('#status_textarea_actual_behavior'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				] 
			}
		],
		submitElement : $('#button_create'),
		messages : null,
		accept : function () {
		
			loader.show();
			
			var obj = {
				createBugReport : {
					title : $('#input_title').val(),
					description : $('#textarea_description').val(),
					tags :  $('#input_tags').val(),
					stepsToReproduce : $('#textarea_steps_to_reproduce').val(),
					expectedBehavior : $('#textarea_expected_behavior').val(),
					actualBehavior : $('#textarea_actual_behavior').val()
				}
			};
	
			$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
				loader.hide();
				if(data.createBugReport.error != undefined) {
					
					errorHandler({
						error : data.createBugReport.error	
					});
					
				} else {
					location.href = "<%=absoluteURL %>/bugs";		
				}
			});
			
		},
		error : function() {
			//		
		}
	});
	
	
});
</script>