<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

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
String absoluteSecureURL = p.getAbsoluteSecureUrl();
String applicationURL = p.getApplicationUrl();
String returnUrl = request.getParameter("ret");
if(null == returnUrl){
	returnUrl = "";
}
%>

<h1><%= p.getHeader() %></h1>
<div class="wrapper-content-left">
	<form style="padding: 0px; margin: 0px;" id="form_login" name="form_login" method="post" accept-charset="UTF-8" onsubmit="return validateForm(this)" action="<%=request.getContextPath() %>/frontdoor?shva=1&amp;redirect=<%=request.getParameter("redirect") %>&amp;referer=<%=request.getHeader("referer") %>&amp;lang=<%=p.getAdaptedCultureCode() %>">
		<input type="hidden" name="input-return-url" id="input_return_url" value="<%=returnUrl %>" />
		<div style="clear: both">
			<div class="params">
				<div class="param-name"><span>* <%=i18n.tr("Email / Username") %>:</span></div>
				<div class="param-value">
					<div><input name="input-email" id="input_email" type="text" style="width: 200px;" maxlength="50" autocapitalize="off" spellcheck="false" /></div>
					<div><label id="status_input_email"></label></div>
				</div>
			</div>
			<div class="params">
				<div class="param-name"><span>* <%=i18n.tr("Password") %>:</span></div>
				<div class="param-value">
					<div><input id="input_password" type="password" maxlength="12" name="input-password" autocapitalize="off" /><a href="<%=absoluteSecureURL%>/forgot-password" title="<%=i18n.tr("Forgot your password?") %>" style="margin-left: 6px;"><%=i18n.tr("Forgot your password?") %></a></div>
					<div style="clear: both;"><label id="status_input_password"></label></div>
				</div>
			</div>
			<div class="params">
				<div class="param-name"></div>
				<div class="param-value">
					<div style="Xpadding-left: 130px; clear: both;" class="ui-form">
						<div class="row-choice" style="margin: 0 0 5px">
							<label><span><input id="checkbox_keep_me_sign_in" type="checkbox" checked="checked" autocomplete="off" /></span><%=i18n.tr("Keep me signed in.<br/> (Clear the check box if you're on a shared computer.)") %></label>
					    </div>
					    <div><%=i18n.tr("Don't have an account?") %> <a href="<%=absoluteSecureURL%>/register?ref=login" title="<%=i18n.tr("Sign Up")%>"><%=i18n.tr("Sign Up") %></a></div>
					</div>	
				</div>
			</div>
			
			<div style="clear: both;">
				<div style="margin-left: 130px;">
					<ul class="error-message"></ul>
				</div>
			</div>
			<div style="height: 20px; overflow: hidden; clear: both;"></div>
			<div class="params">
				<div class="param-name">&nbsp;</div>
				<div class="param-value">
					<a id="button_login" title="<%=i18n.tr("Sign In") %>" class="button-blue" href="javascript:;"><span><%=i18n.tr("Sign In") %></span></a>
				</div>
			</div>
		</div>
	</form>
</div>
<!-- 
<div class="wrapper-content-middle-right">
	<h3 class="ui-header-light"><%=i18n.tr("Or sign in with your Facebook account") %></h3>
	<p style="margin: 0;padding: 12px 0 12px;"><%=i18n.tr("Now you can link your accounts and sign in to Inqwise using your Facebook account.") %></p>
	<a href="#" id="button_facebook_login" title="<%=i18n.tr("Sign In with Facebook") %>" class="button-white" style="display: none"><span><b class="icon-facebook">&nbsp;</b><%=i18n.tr("Sign In with Facebook") %></span></a>
</div>
-->

<script type="text/javascript">

function validateForm(el) {
	with (el) {
		return false;
	}
};

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
		case "PasswordExpiry" :
			// do redirect to change password
			location.href = "<%=absoluteSecureURL%>/change-password?email=" + $('#input_email').val();
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
		case "NoPackageSelected" :

			var obj = {
				checkFreePackage : {}
			};
			
			$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
				loader.show();
				if(data.checkFreePackage.error != undefined) {
					// error
				} else {
					location.href = data.checkFreePackage.redirectUrl;
				}
			});	

			break;
	}
};

var loader = null;


var login = function(params) {

	var obj = {
		login : {
			email : params.email,
			password : params.password,
			isPersist : params.isPersist,
			providerId : params.providerId,
			returnUrl : params.returnUrl
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
			if(data.login != undefined) {
				if(data.login.error != undefined) {
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.login);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.login);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

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
					{ method : 'required', message : 'This field is required.' }
					/*, { method : 'email', message : 'Please enter a valid email address.' }*/
				] 
			},
			{
				element : $('#input_password'),
				status : $('#status_input_password'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'rangelength', pattern : [6,12] }
				]
			}
		],
		submitElement : $('#button_login'),
		messages : null,
		accept : function () {
			
			// clear errors if exists
			clearErrors();
			
			if(navigator.cookieEnabled === true) {
				// loader
				loader.show();
				
				login({
					email : $.removeHTMLTags($.trim($('#input_email').val())),
					password : $.removeHTMLTags($.trim($('#input_password').val())),
					isPersist : $('#checkbox_keep_me_sign_in').prop('checked'),
					success : function(data) {
						clearErrors();
						document.form_login.submit();
					},
					error: function(error) {
						loader.hide();
						errorMessenger(error.error);
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
	
	
	/*
	// social login
	login({
		providerId : 2,
		isPersist : true,
		returnUrl : "<%=returnUrl %>",
		success : function(data) {
			if(data.loginUrl != undefined) {
				$('#button_facebook_login').attr("href", data.loginUrl).show();
			}
		},
		error: function(error) {
			alert(JSON.stringify(error));
		}
	});
	*/
	
	// default button
	$(document).bind('keydown', function(e) {
		var code;
        if (!e) var e = window.event;
        if (e.keyCode) code = e.keyCode;
        else if (e.which) code = e.which;

     	// enter
        if(code == 13) {
        	if(!$('#button_login').is(':focus')) {
				v.validate();
			}
        }
	});

});
</script>