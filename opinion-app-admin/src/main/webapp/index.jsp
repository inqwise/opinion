<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ page import="com.opinion.cms.CmsSystem" %>
<%@ page import="com.opinion.cms.common.ILanguage" %>
<%@ page import="com.opinion.cms.managers.LanguagesManager" %>
<%@ page import="com.opinion.library.common.errorHandle.OperationResult" %>

<%@ page import="java.util.Calendar" %>
<%@ page import="java.net.URL" %>

<%
	
	String lang = null;
	lang = request.getParameter("lang");
	ILanguage language = null;
	OperationResult<ILanguage> languageResult = LanguagesManager.getLanguageByCultureCode(lang, CmsSystem.DEFAULT_CULTURE_CODE);
	if(!languageResult.hasError()){
		language = languageResult.getValue();
	}

	URL absoluteURL = request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath() + "/" + language.getAdaptedCultureCode()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath() + "/" + language.getAdaptedCultureCode());
	URL applicationURL = request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath());
	
	// Get current year
	Calendar cal=Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	
	String returnUrl = request.getParameter("ret");
	if(null == returnUrl){
		returnUrl = "";
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>BackOffice - Inqwise</title>
		<meta name="robots" content="noindex, nofollow" />
		<meta name="author" content="Inqwise" />
		
		<link rel="icon" href="<%=applicationURL%>/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="<%=applicationURL%>/favicon.ico" type="image/x-icon" /> 
		
		<style type="text/css">
	    body, html {height: 100%; background: #fff !important; }
		#outer {height: 100%; width: 100%; overflow: visible; display: table; #position: relative; } 
		.copyright-container{position:absolute;bottom:0;right:0;color:#888}
		.login-wrapper{width:910px;height:384px;margin:0 auto;}
		.left-wing{float:left;width:450px;height:384px;position:relative}
		.right-wing{float:left;padding:0px 0px 0px 10px;height:384px;width:450px;overflow:hidden;}
	    </style>
	    
		<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/global.css" />
		<link rel="stylesheet" type="text/css" href="<%=applicationURL%>/css/retina.css" />

        <script type="text/javascript" src="<%=applicationURL%>/scripts/jquery/1.8.2/jquery.min.js"></script>
		<!-- <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script> -->
		<!-- <script type="text/javascript">window.jQuery || document.write("<script type=\"text/javascript\" src=\"<%=applicationURL%>/scripts/jquery/1.8.2/jquery.min.js\"><\/script>")</script> -->
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/utils.js"></script>
		<!--[if lt IE 8]>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/json2.js"></script>
		<![endif]-->
		<script type="text/javascript">
		var servletUrl = "<%=applicationURL%>/servlet/DataPostmaster";

		// authentication
		;(function($) {
			authentication = function(givenOptions) {
				
				var options = $.extend( {
					servletUrl : null,
					mustAuthenticated : false,
					absoluteUrl : null,
					applicationUrl : null
				}, givenOptions);
				
				var userInfo = {};
				
				var validate = function() {
					
					if($.cookie('sib') != null 
							&& $.cookie('uib') != null) {
						
						// validate user
						$.ajax({
							type: "GET",
							url: options.servletUrl,
						  	async: false,
						  	data: { 
								rq: JSON.stringify({ 
									validateUser : {}
								}), 
								timestamp: $.getTimestamp() 
							},
						  	dataType: "json",
						  	success: function(data) {
						  		if(data.validateUser.error) {
						  			if(options.mustAuthenticated) {
						  				location.href = options.applicationUrl;
						  			} else {
						  				//
						  			}
								} else {
									if(options.mustAuthenticated) {
										
										userInfo = data.validateUser;
										
									} else {
										location.href = options.absoluteUrl + "/dashboard";
									}
								}
						  	},
							error: function (XHR, textStatus, errorThrow) {
								// error 
								// connection
							}
						});
							
					} else {
						if(options.mustAuthenticated) {
							location.href = options.applicationUrl;
						} else {
							
						}
					}
				};
				
				validate();
				
				return {
					userInfo : userInfo
				};
			};
		})(jQuery);
		
		var auth = new authentication({
			servletUrl : servletUrl,
			absoluteUrl : "<%=absoluteURL %>"
		});
		
		</script>
		
	</head>
	<body>
	
		<div id="outer">
			<div style="display: table-cell; vertical-align: middle; #position: absolute; #top: 50%; #left: 50%;">
				<div class="login-wrapper" style="#position: relative; #top: -50%; #left: -50%;">
					<div class="left-wing">
						<div class="copyright-container">Copyright &copy; 2011-<%=year %> Inqwise Ltd. All rights reserved.</div>
					</div>
					<div class="right-wing">
						<h1 style="line-height: 22px;">BackOffice Secure Login</h1>
						<div>
							<div style="padding-bottom: 20px;">
								You have attempted to access a page that requires an Inqwise login.<br />If you are already a user of the system, please login below.
								<!-- <p>Fields indicated with an * are required.</p> -->
							</div>
							<form style="padding: 0px; margin: 0px;" id="form_login" name="form_login" method="post" accept-charset="UTF-8" onsubmit="return validateForm(this)" action="<%=request.getContextPath() %>/frontdoor?shva=1&amp;redirect=<%=request.getParameter("redirect") %>&amp;referer=<%=request.getHeader("referer") %>&amp;lang=en-us">
							<input type="hidden" name="input-return-url" id="input_return_url" value="<%=returnUrl %>" />
							<div>
								<div class="params">
									<div class="param-name"><span>* Email / Username:</span></div>
									<div class="param-value">
										<div><input type="text" id="text_email" name="email" style="width: 200px;" maxlength="50" autocapitalize="off" spellcheck="false" /></div>
										<div><label id="status_email"></label></div>
									</div>
								</div>
								<div class="params">
									<div class="param-name"><span>* Password:</span></div>
									<div class="param-value">
										<div><input type="password" id="text_password" name="password" maxlength="12" autocapitalize="off" /></div>
										<div><label id="status_password"></label></div>
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
										<a id="button_login" title="Sign In" class="button-blue" href="javascript:;"><span>Sign In</span></a>
									</div>
								</div>
							</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript" src="<%=applicationURL%>/scripts/lightloader/1.0.1/lightloader.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/validator/validator-1.2.8.js" charset="utf-8"></script>
		
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
					
					/*
					$('#text_email, #text_password').on("focus", function(e){ 
						clearErrors();
					});
					*/
					
					break;
				case "PasswordExpiry" :
					$("<li>Password is expired.</li>").appendTo('.error-message');
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
							{ method : 'required', message : 'This field is required.' }
							/*, { method : 'email', message : 'Please enter a valid email address.' }*/
						] 
					},
					{
						element : $('#text_password'),
						status : $('#status_password'),
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
						
						var obj = {
							login : {
								username : $.removeHTMLTags($.trim($('#text_email').val())),
								password : $.removeHTMLTags($.trim($('#text_password').val())),
								isPersist : true,
								
							}
						};
			
						$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
							if(data.login.error != undefined) {
								loader.hide();
								errorMessenger(data.login.error);
							} else {
								clearErrors();
								document.form_login.submit();
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
		        	if(!$('#button_login').is(':focus')) {
						v.validate();
					}
		        }
			});
			
		});
		</script>
		
	</body>
</html>