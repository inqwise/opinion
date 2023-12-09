
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="java.net.*" %>

<%

	URL applicationURL = request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath());

%>

<!DOCTYPE html>
<html>
	<head>
	  <meta charset="utf-8" />

	  <style type="text/css" id="styleblock">
	  </style>
	</head>
	<body style="margin:0;">
	
		<div class="theme-body ui-body-a" data-theme="a" data-form="ui-body-a">
			<div class="theme-wrapper ui-wrapper-a" data-theme="a" data-form="ui-wrapper-a">
				<div class="theme-header ui-header-a" data-theme="a" data-form="ui-header-a">
					<div class="theme-logo ui-logo-a" data-theme="a" data-form="ui-logo-a"></div>
				</div>
				<div class="theme-content ui-content-a" data-theme="a" data-form="ui-content-a">
					<div>
						<div class="theme-question ui-question-a" data-theme="a" data-form="ui-question-a">
							<div>
								<span class="label-control-status status">This question is required</span>
							</div>
							<div class="theme-question-heading ui-question-heading-a" data-theme="a" data-form="ui-question-heading-a">
								<span class="label-control-mandatory asterisk">*&nbsp;</span><span class="header-control-content">What is the best game on facebook</span>
							</div>
							<div class="form-controls">
								<div class="container-control-embed"></div>
								<div class="container-control-notes label-control-note">test description</div>
								<ul class="container-control-includes">
									<li class="row-choice"><label><input type="radio" autocomplete="off" value="Big Story Little Heroes" optionkindid="0" optiontext="Big Story Little Heroes" optionindex="0" name="13724091179021601" id="2985" class="radio-option" />Big Story Little Heroes</label></li>
									<li class="row-choice"><label><input type="radio" autocomplete="off" value="Dragon City" optionkindid="0" optiontext="Dragon City" optionindex="1" name="13724091179021601" id="2986" class="radio-option" />Dragon City</label></li>
									<li class="row-choice"><label><input type="radio" autocomplete="off" value="Tamer Saga" optionkindid="0" optiontext="Tamer Saga" optionindex="2" name="13724091179021601" id="2987" class="radio-option" />Tamer Saga</label></li>
									<li class="row-choice"><label><input type="radio" autocomplete="off" value="DDtank" optionkindid="0" optiontext="DDtank" optionindex="3" name="13724091179021601" id="2988" class="radio-option" />DDtank</label></li>
									<li class="row-choice"><label><input type="radio" autocomplete="off" value="Bubble Island" optionkindid="0" optiontext="Bubble Island" optionindex="4" name="13724091179021601" id="2989" class="radio-option" />Bubble Island</label></li>
									<li class="row-choice"><label><input type="radio" autocomplete="off" value="Epic Coaster" optionkindid="0" optiontext="Epic Coaster" optionindex="5" name="13724091179021601" id="2990" class="radio-option" />Epic Coaster</label></li>
								</ul>
								<div class="container-control-other">
									<div class="row-choice-other">
										<div class="cell">
											<label><input type="radio" autocomplete="off" optiontext="Other, (please specify)" optionkindid="1" optionid="2991" name="13724091179021601" class="radio-other" />Other, (please specify)</label>
										</div>
										<div class="cell">
											<input type="text" class="text-field input-control-other" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div>
						<div>
							<a class="theme-button ui-button-a button-vote" data-theme="a" data-form="ui-button-a" title="Vote"><span>Vote</span></a>&nbsp;or&nbsp;<a class="button-view-results" title="View Results" href="#">View Results</a>
						</div>
					</div>
					
				</div>
				<div class="theme-footer ui-footer-a" data-theme="a" data-form="ui-footer-a"">
				Powered by <a title="Online Survey Tool">Inqwise</a>
				</div>
			</div>
		</div>
	
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" charset="utf-8"></script>
		<script type="text/javascript">window.jQuery || document.write("<script type=\"text/javascript\" src=\"<%=applicationURL%>/scripts/jquery/1.8.2/jquery.min.js\"><\/script>")</script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/utils.js" charset="utf-8"></script>
		<!--[if lt IE 8]>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/json2.js" charset="utf-8"></script>
		<![endif]-->
		
		<script type="text/javascript" src="<%=applicationURL%>/scripts/components/poll_preview.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			
			//alert("READY");
			
		});
		</script>
	
	</body>
</html>