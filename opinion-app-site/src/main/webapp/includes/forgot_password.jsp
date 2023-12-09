<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.xnap.commons.i18n.I18n" %>
<%@ page import="org.xnap.commons.i18n.I18nFactory" %>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);
//Translations
Locale locale1 = new Locale(lang);
I18n i18n = I18nFactory.getI18n(this.getClass(), "messages", locale1);

String absoluteURL = p.getRootAbsoluteUrl();
String applicationURL = p.getApplicationUrl();	
%>

<h1><%= p.getHeader() %></h1>
<div>
	<div class="wrapper-content-left">
		
		<div id="form_post">
			<div style="padding-bottom: 20px;">
				<%=i18n.tr("Please enter your email address below.<br/> We will send you an email with a link to reset your password.") %>
			</div>
			<div class="params">
				<div class="param-name"><span>* <%=i18n.tr("Email") %>:</span></div>
				<div class="param-value">
					<div><input id="input_email" name="input-email" type="text" style="width: 200px;" maxlength="50" autocomplete="off" spellcheck="false" /></div>
					<div><label id="status_input_email"></label></div>
				</div>
			</div>
			<div style="clear: both; margin-left: 130px;">
				<ul class="error-message"></ul>
			</div>
			<div style="height: 20px; overflow: hidden; clear: both;"></div>
			<div class="params">
				<div class="param-name">&nbsp;</div>
				<div class="param-value">
					<a id="button_forgot_password" title="<%=i18n.tr("Reset Password") %>" class="button-blue" href="javascript:;"><span><%=i18n.tr("Reset Password") %></span></a>
				</div>
			</div>
		</div>
		<div id="form_accept" style="display: none;">
			<h2>Thank you for your request!</h2>
			<p>Password reset information have been sent to your email address we have on record.<br/>
			Please check your email and follow the details.</p>
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
		case "UserNotExist" :
			$("<li>User does not exist or incorrect credentials.</li>").appendTo('.error-message');
			break;
		case "GeneralError" :
			$("<li>Reset password temporary is unavailable.</li>").appendTo('.error-message');
			break;
	}
};

var loader = null;

$(function() {
	
	loader = new lightLoader();

	var defaultFocus = function() {
		$('#input_email').focus();
	};
	
	defaultFocus(); // set default focus

	// validate fields
	var v = null;
	v = new validator({
		elements : [
			{
				element : $('#input_email'),
				status : $('#status_input_email'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'email', message : 'Please enter a valid email address.' }
				] 
			}
		],
		submitElement : $('#button_forgot_password'),
		messages : null,
		accept : function () {

			// clear errors if exists
			clearErrors();
			// loader
			loader.show();

			var obj = {
				resetPassword : {
					userName : $.removeHTMLTags($('#input_email').val()).replace(/\r/g, "")
				}
			};
			
			$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp : $.getTimestamp() }, function(data) {
				loader.hide();
				if(data.resetPassword.error != undefined) {
					
					errorMessenger(data.resetPassword.error);
				} else {
					clearErrors();
					
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
        	if(!$('#button_forgot_password').is(':focus')) {
				v.validate();
			}
        }

        
	});

});
</script>