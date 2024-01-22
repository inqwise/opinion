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

<h1><%= p.getHeader() %></h1>
<div>
	<div class="wrapper-content-left">
		<div style="padding-bottom: 28px;">
			Do you have a topic or question that you think should be added to Answers to our FAQ's pages?<br/>
			If so, please send us your suggestion using the form below.
		</div>
		<div>
			<div class="row" style="height: 72px;">
				<div class="cell" style="width: 130px;"><span>* Your question:</span></div>
				<div class="cell">
					<div>
						<textarea id="textarea_question" name="textarea-question" autocomplete="off" maxlength="255" style="width: 314px; height: 64px;"></textarea>
					</div>
					<div><label id="status_textarea_question"></label></div>
				</div>
			</div>
			<div class="row">
				<div class="cell" style="width: 130px;"><span>* Name:</span></div>
				<div class="cell">
					<div><input id="input_name" name="input-name" type="text" maxlength="50" autocomplete="off" /></div>
					<div><label id="status_input_name"></label></div>
				</div>
			</div>
			<div class="row">
				<div class="cell" style="width: 130px;"><span>* E-mail:</span></div>
				<div class="cell">
					<div><input id="input_email" name="input-email" type="text" style="width: 200px;" maxlength="50" autocomplete="off" /></div>
					<div><label id="status_input_email"></label></div>
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


<script type="text/javascript">
var v = null;
var defaultFocus = function() {
	$('#textarea_question').focus();
};

$(document).ready(function() {

	defaultFocus(); // set default focus
	
	v = new validator({
		elements : [
			{
				element : $('#textarea_question'),
				status : $('#status_textarea_question'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				]
			},
            {
				element : $('#input_name'),
				status : $('#status_input_name'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				]
	        },
	        {
				element : $('#input_email'),
				status : $('#status_input_email'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'emailISO', message : 'Please enter a valid email address.' }
				]
	        }
		],
		submitElement : $('#button_submit'),
		messages : null,
		accept : function () {
			// loader
			loader.show();
			var obj = {
				surveys : {
					sendQuestionRequest : {
						comments : $('#textarea_question').val(),
						email : $('#input_email').val(),
						name : $('#input_name').val()
					}
				}
			};

			$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
				loader.hide();
				if(data.surveys.sendQuestionRequest.error != undefined) {
					
				} else {

					/*
					$('#form_post').hide();
					$('#form_accept').show();
					*/
				}
			});	
		}
		
	});

	// default button
	$(document).bind('keydown', function(e) {
		var code;
        if (!e) var e = window.event;
        if (e.keyCode) code = e.keyCode;
        else if (e.which) code = e.which;

     	// enter
        if(code == 13) {
        	if(!$('#button_submit').is(':focus')) {
            	if(!$('#textarea_question').is(':focus')) {
					v.validate();
            	}
			}
        }
        
	});
	
});
</script>
