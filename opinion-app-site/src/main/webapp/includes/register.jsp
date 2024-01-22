<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%
IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
String lang = p.getCultureCode().substring(0, 2);

String absoluteURL = p.getRootAbsoluteUrl();
String absoluteSecureURL = p.getAbsoluteSecureUrl();
String applicationURL = p.getApplicationUrl();
	
String planId = request.getParameter("plan_id");
String inviteId = request.getParameter("invite_id");
%>

<h1><% if(planId == null) { %> Sign up for your FREE account <% } else { %> Sign Up <% } %></h1>
<div>
	<div class="wrapper-content-left">
		<!--
		<div style="padding-bottom: 24px;">
			Sign Up Inqwise Today and Create Online Surveys for FREE!<br/> Complete this form to register for an Inqwise account.
		</div>
		-->
		<form id="form_registration" style="padding: 0px; margin: 0px;" onsubmit="return validateForm(this)">
			
			<!--
			<div class="row">
				<div class="cell" style="width: 130px;">
					<span>Name:</span>
				</div>
				<div class="cell">
					<input id="input_display_name" name="input_display_name" type="text" autocomplete="off" spellcheck="false" maxlength="50" tabindex="1" /> 
				</div>
			</div>
			 -->
			 
			<div class="params">
				<div class="param-name">
					<span>* Email:</span>
				</div>
				<div class="param-value">
					<div><input id="input_email" name="input-email" type="text" autocomplete="off" spellcheck="false" style="width: 200px;" tabindex="2" /></div>
					<div><label id="status_input_email"></label></div>
				</div>
			</div>
			<div class="params" style="padding-bottom: 0px;">
				<div class="param-name">
					<span>* Password:</span>
				</div>
				<div class="param-value">
					<div><input id="input_password" name="input-password" type="password" autocomplete="off" maxlength="12" tabindex="3" /></div>
					<div><label id="status_input_password"></label></div>
					<div style="padding-bottom: 5px;"><em style="color: #999">(Password must be between 6-12 characters.)</em></div>
				</div>
			</div>
			<!--
			<div class="params">
				<div class="param-name">
					<span>* Confirm Password:</span>
				</div>
				<div class="param-value">
					<div><input id="input_confirm_password" name="input-confirm-password" type="password" autocomplete="off" maxlength="12" tabindex="4" /></div>
					<div><label id="status_input_confirm_password"></label></div>
				</div>
			</div>
			-->
			<!--
			<div class="row" style="padding-bottom: 0px; display: none;">
				<div class="cell" style="width: 130px; line-height: 15px;">
					<span>* Word Verification:</span>
				</div>
				<div class="cell">
					<div style="margin-bottom: 12px;">
						<div style="height: 70px;" id="image_captcha" class="loading"></div>
					</div>
					<div style="margin-bottom: 12px; color: #333;">
						<div style="color: #5c5c5c">Please enter the word in the image above.<br/>
						Can't read the word above? <a href="javascript:reloadCaptcha();" title="Try different word.">Try different word</a></div>
					</div>
					<div>
						<div style="float:left;">
							<div><input id="input_captcha" name="input-captcha" type="text" autocomplete="off" spellcheck="false" tabindex="5" /></div>
							<div><label id="status_input_captcha"></label></div>
							<div style="padding-bottom: 24px;"><em style="color: #999">(Letters are not case-sensitive.)</em></div>
						</div>
					</div>
				</div>
			</div>
			-->
			
			<div style="padding-left: 130px; clear: both;">
				By clicking &quot;Sign Up&quot;, you agree to our <a href="<%=absoluteURL %>/terms" target="_blank" title="Terms of Use">Terms of Use</a> <br/>and <a href="<%=absoluteURL %>/privacy" target="_blank" title="Privacy Policy">Privacy Policy</a>. 
			</div>
			<div style="clear: both;">
				<div style="margin-left: 130px;">
					<ul class="error-message"></ul>
				</div>
			</div>
			<div style="height: 20px; overflow: hidden;"></div>
			<div class="params">
				<div class="param-name">&nbsp;</div>
				<div class="param-value">
					<a href="javascript:;" class="button-green" title="Sign Up" id="button_register" tabindex="6"><span>Sign Up</span></a>
				</div>
			</div>
			
		</form>
	</div>
	<!-- 
	<div class="wrapper-content-middle-right">
		<h3 class="ui-header-light">Or sign up with your Facebook account</h3>
		<p style="margin: 0;padding: 12px 0 12px;">Now you can link your accounts and sign up to Inqwise using your Facebook account.</p>
		<a href="#" id="button_facebook_login" title="Sign Up with Facebook" class="button-white" style="display: none"><span><b class="icon-facebook">&nbsp;</b>Sign Up with Facebook</span></a>
		<div style="padding: 12px 0">By creating an account using your Facebook account, you agree to our <a href="<%=absoluteURL %>/terms" target="_blank" title="Terms of Use">Terms of Use</a> and <a href="<%=absoluteURL %>/privacy" target="_blank" title="Privacy Policy">Privacy Policy</a>.</div>
	</div>
	-->
	<!-- 
	<div class="wrapper-content-right">
		<h3 class="ui-header">We Respect Your Privacy</h3>
		<div style="padding: 12px 0 24px 8px">We respect your privacy and do not tolerate spam and will never sell, rent, lease or give away your information (name, address, email, etc.) to any third party.</div>
	</div>
	-->
</div>

<script type="text/javascript">

function validateForm(el) {
	with (el) {
		return false;
	}
};

var isCaptchaEnabled = true;
function reloadCaptcha() {
	if(isCaptchaEnabled) {

		isCaptchaEnabled = false;
		
		$('#input_captcha').val("");
		
		var captchaSrc = "<%=applicationURL %>/captcha.gif"; 
	    var timestamp = $.getTimestamp();
	
		$('#image_captcha')
			.empty()
			.addClass('loading');
		
	    var img = new Image();
		
		// wrap our new image in jQuery, then:
		$(img).load(function () {
	      // set the image hidden by default    
	      $(this).hide();
	    
	      $('#image_captcha')
	        .removeClass('loading')
	        .append(this);
	    
	      	// fade our image in to create a nice effect
	      	$(this).fadeIn();
	
	      	isCaptchaEnabled = true;
	    })
	    .error(function () {
	    	// error
	    	isCaptchaEnabled = true;
	    })
	    .attr('src', captchaSrc+"?"+timestamp);

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
		case "UsernameAlreadyExist" :
			$("<li>A user already exists for the username or email specified.<br/> Please register again using a different username.</li>").appendTo('.error-message');
			break;
		case "CaptchaTextWrong" :
			$("<li>The characters you entered didn't match the word verification.<br/> Please try again.</li>").appendTo('.error-message');
			reloadCaptcha();
			break;
		case "GeneralError" :
			$("<li>Service temporary is unavailable.</li>").appendTo('.error-message');
			break;
	}
}


var planId = <%=planId %>;
var inviteId = <%=(null == inviteId ? "null" : "\"" + inviteId + "\"") %>;
var loader = null;

var login = function(params) {

	var obj = {
		login : {
			email : params.email,
			password : params.password,
			isPersist : params.isPersist,
			providerId : params.providerId,
			returnUrl : params.returnUrl,
			planId : params.planId
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

var registerAndLogin = function(params) {
	
	var obj = {
		registerAndLogin : {
			displayName : params.displayName,
			email : params.email,
			password : params.password,
			confirmPassword : params.confirmPassword,
			captcha : "",
			isAcceptToReceiveNewsletters : true,
			isAgreeToPrivacyPolicy : true,
			ref : params.ref,
			planId : params.planId,
			inviteId : params.inviteId
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
			if(data.registerAndLogin != undefined) {
				if(data.registerAndLogin.error != undefined) {
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.registerAndLogin);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.registerAndLogin);
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
	
	// load captcha image
	//reloadCaptcha();
	
	var defaultFocus = function() {
		$('#input_email').focus();
	};

	defaultFocus(); // Set default focus

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
			},
			{
				element : $('#input_password'),
				status : $('#status_input_password'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'rangelength', pattern : [6,12] }
				]
			}/*,
			{
				element : $('#input_confirm_password'),
				status : $('#status_input_confirm_password'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					{ method : 'rangelength', pattern : [6,12] },
					{ method : 'equalTo',  element : $('#input_password'), message : 'Please enter the same value as password again.' }
				]
			}*/
			/*
			,{
				element : $('#input_captcha'),
				status : $('#status_input_captcha'),
				validate : function() {
					return false;
				},
				rules : [
					{ method : 'required', message : 'This field is required.' }
				]
			}
			*/
		],
		submitElement : $('#button_register'),
		messages : null,
		accept : function () {

			// clear errors if exists
			clearErrors();
			
			if(navigator.cookieEnabled === true) {

				loader.show();
				
				registerAndLogin({
					email : $.removeHTMLTags($('#input_email').val()).replace(/\r/g, ""),
					password : $.removeHTMLTags($('#input_password').val()).replace(/\r/g, ""),
					confirmPassword : $.removeHTMLTags($('#input_password').val()).replace(/\r/g, ""), //$.removeHTMLTags($('#input_confirm_password').val()).replace(/\r/g, ""),
					captcha : "", //$.removeHTMLTags($('#input_captcha').val()).replace(/\r/g, ""),
					isAcceptToReceiveNewsletters : true,
					isAgreeToPrivacyPolicy : true,
					ref : $.getUrlParam('ref'),
					planId : planId,
					inviteId : inviteId,
					success : function(data) {
						clearErrors();
						location.href = "<%=absoluteSecureURL %>/" + data.redirectUrl;
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
        	if(!$('#button_register').is(':focus')) {
				v.validate();
			}
        }
        
	});
	
});
</script>