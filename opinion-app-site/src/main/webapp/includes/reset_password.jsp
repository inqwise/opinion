<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);

String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();
%>


<h1><%= p.getHeader() %></h1>
<div>
	<div class="wrapper-content-left">
		
		<div id="form_post">
			<div style="padding-bottom: 24px;">
				Please enter the password reset code that was sent to you.<br/> This is not the same as your password.
			</div>
			<div class="params">
				<div class="param-name"><span>* Password reset code:</span></div>
				<div class="param-value">
					<div><input id="input_password_reset_code" name="input_password_reset_code" type="text" maxlength="50" autocomplete="off" spellcheck="false" style="width: 200px;" /></div>
					<div><label id="status_input_password_reset_code"></label></div>
				</div>
			</div>
			<div style="margin-left: 130px; clear: both;">
				<ul class="error-message"></ul>
			</div>
			<div style="height: 24px; overflow: hidden; clear: both;"></div>
			<div class="params">
				<div class="param-name">&nbsp;</div>
				<div class="param-value">
					<a id="button_reset_password" title="Submit Code" class="button-blue" href="javascript:;"><span>Submit Code</span></a>
				</div>
			</div>
		</div>
		<div id="form_accept" style="display: none;">
			<h2>Your reset password confirmation done!</h2>
			<p>A new temporary password have been sent to your email address we have on record.<br/>Please check your email.</p>
		</div>
		
	</div> 
</div>

<script type="text/javascript">

var clearErrors = function() {
	$('.error-message').empty();
};

var errorMessenger = function(err) {
	clearErrors();
	switch(err) {
		case "ArgumentWrong" :
			$("<li>Reset code is wrong.</li>").appendTo('.error-message');
			break;
		case "GeneralError" :
			$("<li>Confirm reset password temporary is unavailable.</li>").appendTo('.error-message');
			break;
	}
};

var loader = null;

$(function() {

	loader = new lightLoader();
	
	var resetCodeFromUrlParam = $.getUrlParam("reset_code");
	if(resetCodeFromUrlParam != "") {
		$('#input_password_reset_code').val(resetCodeFromUrlParam);
	}

	var defaultFocus = function() {
		$('#input_password_reset_code').focus();
	};
	
	defaultFocus(); // set default focus
	
	// validate fields
	var v = null;
	v = new validator({
		elements : [
			{
				element : $('#input_password_reset_code'),
				status : $('#status_input_password_reset_code'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'minlength', pattern : 3 }
				]
			}
		],
		submitElement : $('#button_reset_password'),
		messages : null,
		accept : function () {

			// clear errors if exists
			clearErrors();
			// loader
			loader.show();
		
			var obj = {
				confirmResetPassword : {
					passwordResetCode : $.removeHTMLTags($('#input_password_reset_code').val()).replace(/\r/g, "")
				}
			};
			
			$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp : $.getTimestamp() }, function(data) {
				if(data.confirmResetPassword.error != undefined){
					loader.hide();
					errorMessenger(data.confirmResetPassword.error);
					
				} else {
					clearErrors();
					loader.hide();

					$('#form_post').hide();
					$('#form_accept').show();
				}
			});
			
		},
		error : function() {
			//
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
        	if(!$('#button_reset_password').is(':focus')) {
				v.validate();
			}
        }
        
	});
	
});

</script>