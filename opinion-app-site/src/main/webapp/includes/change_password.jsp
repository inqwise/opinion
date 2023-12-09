<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.opinion.cms.common.IPage"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>


<h1><%= p.getHeader() %></h1>
<div>
	<div style="padding-bottom: 14px;">
		To change your password, please provide the following information below.<br/>
		Do not use the same password that you use for other online accounts.
		<p>Fields indicated with an * are required.</p>
	</div>
	<form style="padding: 0px; margin: 0px;" id="form_change_password" name="form_change_password" method="post" accept-charset="UTF-8" onsubmit="return validateForm(this)" action="<%=request.getContextPath() %>/frontdoor?shva=1&amp;redirect=<%=request.getParameter("redirect") %>&amp;referer=<%=request.getHeader("referer") %>&amp;lang=<%=p.getAdaptedCultureCode() %>">
		<div style="clear: both;">
			<div class="params">
				<div class="param-name"><span>* Email:</span></div>
				<div class="param-value">
					<div><input id="text_email" type="text" name="email" style="width: 200px;" maxlength="50" autocomplete="off" autocapitalize="off" spellcheck="false" /></div>
					<div><label id="status_email"></label></div>
				</div>
			</div>
			<div class="params">
				<div class="param-name"><span>* Old Password:</span></div>
				<div class="param-value">
					<div><input id="text_password" type="password" name="password" maxlength="12" autocomplete="off" autocapitalize="off" /></div>
					<div><label id="status_password"></label></div>
				</div>
			</div>
			<div class="params" style="padding-bottom: 0px;">
				<div class="param-name"><span>* New Password:</span></div>
				<div class="param-value">
					<div><input id="text_new_password" type="password" name="new_password" maxlength="12" autocomplete="off" autocapitalize="off" /></div>
					<div><label id="status_new_password"></label></div>
					<div style="padding-bottom: 10px;"><em style="color: #999">(Password must be between 6-12 characters.)</em></div>
				</div>
			</div>
			<div class="params">
				<div class="param-name"><span>* Confirm New Password:</span></div>
				<div class="param-value">
					<div><input id="text_confirm_new_password" type="password" name="confirm_new_password" maxlength="12" autocomplete="off" autocapitalize="off" /></div>
					<div><label id="status_confirm_new_password"></label></div>
				</div>
			</div>
			<div style="clear: both;">
				<div style="margin-left: 130px;">
					<ul class="error-message"></ul>
				</div>
			</div>
			<div style="height: 24px; overflow: hidden; clear: both;"></div>
			<div class="params">
				<div class="param-name">&nbsp;</div>
				<div class="param-value">
					<a id="button_change_password" title="Change Password" class="button-blue" href="javascript:;"><span>Change Password</span></a>
				</div>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">

function clearErrors() {
	$('.error-message').empty();
}

function errorMessenger(err) {
	clearErrors();
	switch(err) {
		case "CookiesDisabled" : 
			$("<li>Your browser cookies are disabled. Please enable cookies in your browser and try again.</li>").appendTo('.error-message');
			break;
		case "InvalidUsernameOrPassword" :
			$("<li>The username or password you entered is incorrect.</li>").appendTo('.error-message');
			break;
		case "GeneralError" :
			$("<li>Login temporary is unavailable.</li>").appendTo('.error-message');
			break;
		case "NotAllowedForProduct" :
			$("<li>Your are not allowed for this product.</li>").appendTo('.error-message');
			break;
		case "AccountDisabled" :
			$("<li>Your account has been disabled, please contact with support <a href=\"mailto:support@inqwise.com\" title=\"support@inqwise.com\">support@inqwise.com</a></li>").appendTo('.error-message');
			break;
	}
};

var loader = null;

$(function() {
	
	loader = new lightLoader();
	
	var emailFromUrlParam = $.getUrlParam("email");
	if(emailFromUrlParam != "") {
		$('#text_email').val(emailFromUrlParam);
	}

	var defaultFocus = function() {
		$('#text_email').focus();
	};

	defaultFocus(); // set default focus



	// validate fields
	var v = null;
	v = new validator({
		elements : [
			{
				element : $('#text_email'),
				status : $('#status_email'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'email', message : 'Please enter a valid email address.' }
				] 
			},
			{
				element : $('#text_password'),
				status : $('#status_password'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'rangelength', pattern : [6,12] }
				]
			},
			{
				element : $('#text_new_password'),
				status : $('#status_new_password'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'rangelength', pattern : [6,12] }
				]
			},
			{
				element : $('#text_confirm_new_password'),
				status : $('#status_confirm_new_password'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'rangelength', pattern : [6,12] },
					{ method : 'equalTo',  element : $('#text_new_password'), message : 'Please enter the same value as new password again.' }
				]
			}
		],
		submitElement : $('#button_change_password'),
		messages : null,
		accept : function () {

			// clear errors if exists
			clearErrors();
			
			if(navigator.cookieEnabled === true) {
				// loader
				loader.show();
				
				var obj = {
					login : {
						email : $.removeHTMLTags($.trim($('#text_email').val())),
						password : $.removeHTMLTags($.trim($('#text_password').val())),
						newPassword : $.removeHTMLTags($.trim($('#text_new_password').val())),
						isPersist : false
					}
				};
		
				$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
					if(data.login.error != undefined) {
						loader.hide();
						errorMessenger(data.login.error);
					} else {
						clearErrors();
						document.form_change_password.submit();
					}
				});
			
			} else {
				errorMessenger("CookiesDisabled");
			}
				
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
        	if(!$('#button_change_password').is(':focus')) {
				v.validate();
			}
        }
	});
	
});
</script>