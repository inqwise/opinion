
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
	String absoluteURL = p.getRootAbsoluteUrl();
	
	String absoluteSecureURL = p.getAbsoluteSecureUrl();
	String applicationURL = p.getApplicationUrl();
%>

<h1><%= p.getHeader() %></h1>
<div>
	<!-- <div>30-day money back guarantee. With no hidden fees.</div> -->
	<div class="wrapper-content-left">
		<table cellpadding="0" cellspacing="0" border="0" class="plans" style="width: 100%">
			<tbody>
				<tr>
					<td class="features" style="vertical-align: bottom">
						<h3>Features</h3>
					</td>
					<td valign="top" class="plan selected">
						<div class="header">
							<h2>Free</h2>
							<div class="plan-price">$<span>0</span> /forever</div>
							<div style="padding-bottom: 12px;">&nbsp;</div> <!-- No credit card required -->
							<a class="button-white" href="<%=absoluteURL %>/register?ref=pricing" title="Sign Up"><span>Sign Up</span></a>
						</div>
					</td>
					<td valign="top" class="plan">
						<div class="header">
							<h2>PRO</h2>
							<div class="plan-price">$<span>7.95</span> /mo</div>
							<div style="padding-bottom: 12px;">&nbsp;</div>
							<!-- <div style="padding-bottom: 12px;">or <strong>$79.00 /yr</strong></div> -->
							<a class="button-yellow" href="<%=absoluteURL %>/register?ref=pricing&amp;plan_id=3" title="Sign Up"><span>Sign Up</span></a>
						</div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">No Contracts to Sign</div></td>
					<td class="plan selected"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
					<td class="plan">
						<div class="field"><b class="icon-v">&nbsp;</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Number of responses <b>(Sign Up Bonus)</b></div></td>
					<td class="plan selected"><div class="field"><b>5000</b></div></td>
					<td class="plan">
						<div class="field">&nbsp;</div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Number of responses per month</div></td>
					<td class="plan selected"><div class="field"><b>100</b></div></td>
					<td class="plan">
						<div class="field"><b>Unlimited</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Number of surveys</div></td>
					<td class="plan selected"><div class="field"><b>Unlimited</b></div></td>
					<td class="plan">
						<div class="field"><b>Unlimited</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Questions per survey</div></td>
					<td class="plan selected"><div class="field"><b>Unlimited</b></div></td>
					<td class="plan">
						<div class="field"><b>Unlimited</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Pages per survey</div></td>
					<td class="plan selected"><div class="field"><b>Unlimited</b></div></td>
					<td class="plan">
						<div class="field"><b>Unlimited</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Multiple collectors</div></td>
					<td class="plan selected"><div class="field"><b>Unlimited</b></div></td>
					<td class="plan">
						<div class="field"><b>Unlimited</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Branding + Logo</div></td>
					<td class="plan selected"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
					<td class="plan">
						<div class="field"><b class="icon-v">&nbsp;</b></div>
					</td>
				</tr>
				<!--
				<tr>
					<td class="features"><div class="field" style="color: #BA0A1C">Skip logic</div></td>
					<td class="plan selected"><div class="field">&nbsp;</div></td>
					<td class="plan"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
				</tr>
				-->
				<tr>
					<td class="features"><div class="field">Finish Options<br/> (Custom Thank You Message, <br/>Redirect to custom URL after survey complete)</div></td>
					<td class="plan selected"><div class="field">&nbsp;<br/>&nbsp;<br/>&nbsp;</div></td>
					<td class="plan">
						<div class="field"><b class="icon-v">&nbsp;</b><br/>&nbsp;<br/>&nbsp;</div>
					</td>
				</tr>
				<!--
				<tr>
					<td class="features"><div class="field" style="color: #BA0A1C">Email survey invitations</div></td>
					<td class="plan selected"><div class="field">&nbsp;</div></td>
					<td class="plan"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
				</tr>
				-->
				<tr>
					<td class="features"><div class="field">Account users</div></td>
					<td class="plan selected"><div class="field"><b>1</b></div></td>
					<td class="plan">
						<div class="field"><b>1</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Real-time results</div></td>
					<td class="plan selected"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
					<td class="plan">
						<div class="field"><b class="icon-v">&nbsp;</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Basic reports</div></td>
					<td class="plan selected"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
					<td class="plan">
						<div class="field">&nbsp;</div>
					</td>
				</tr>
				<!--
				<tr>
					<td class="features"><div class="field" style="color: #BA0A1C">Advanced reports, Filters</div></td>
					<td class="plan selected"><div class="field">&nbsp;</div></td>
					<td class="plan"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
				</tr>
				-->
				<tr>
					<td class="features"><div class="field">Export data to Excel (*.XLSX), CSV</div></td>
					<td class="plan selected"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
					<td class="plan">
						<div class="field"><b class="icon-v">&nbsp;</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">All languages support (Unicode)</div></td>
					<td class="plan selected"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
					<td class="plan">
						<div class="field"><b class="icon-v">&nbsp;</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">Enhanced security (SSL/HTTPS)</div></td>
					<td class="plan selected"><div class="field"><b class="icon-v">&nbsp;</b></div></td>
					<td class="plan">
						<div class="field"><b class="icon-v">&nbsp;</b></div>
					</td>
				</tr>
				<tr>
					<td class="features"><div class="field">24/7 Priority support</div></td>
					<td class="plan selected"><div class="field">&nbsp;</div></td>
					<td class="plan">
						<div class="field"><b class="icon-v">&nbsp;</b></div>
					</td>
				</tr>
				<tr>
					<td class="features empty" style="vertical-align: top"><div class="field"><br/><a href="<%=absoluteURL %>/features" title="All features">All features</a></div></td>
					<td class="plan empty selected" style="vertical-align: top">
						<div class="field"></div>
					</td>
					<td class="plan empty" style="vertical-align: top">
						<div class="field" ></div>
					</td>
				</tr>
			</tbody>
		</table>
		<div style="clear: both; padding-top: 24px;">
		<!-- * All amounts shown are in US dollars.<br/> -->
		* All plans come with email support.<br/>
		Questions? Concerns? Contact us day or night at <a href="mailto:support@inqwise.com" title="support@inqwise.com">support@inqwise.com</a>
		</div>
	</div>
	<div class="wrapper-content-middle">
		<h3 class="ui-header-light">Students, Non-profits &amp; Government</h3>
		<p style="margin: 0;padding: 12px 0 24px;">Inqwise has special pricing available for students, non-profits &amp; government institutions. Contact <a href="mailto:sales@inqwise.com" title="sales@inqwise.com">sales@inqwise.com</a> for more information.</p>
	</div>
</div>
<div style="clear: both; padding-top: 24px;">
	<div style="width: 450px; float: left;">
		<div id="form_post">
			<h3 class="ui-header-light">Confused about which plan to choose?</h3>
			<p>Let us know what you need and we’ll help you out.</p>
			<div>
				<div class="row">
					<div class="cell" style="width: 130px;">
						<span>* Your email address:</span>
					</div>
					<div class="cell">
						<div><input id="text_email" name="email" type="text" autocomplete="off" spellcheck="false" style="width: 225px;" /></div>
						<div><label id="status_email"></label></div>
					</div>
				</div>
				<div class="row" style="height: 70px;">
					<div class="cell" style="width: 130px;"></div>
					<div class="cell">
						<div><textarea id="textarea_comment" name="comment" autocomplete="off" style="width: 314px; height: 64px;"></textarea></div>
						<div><label id="status_comment"></label></div>
					</div>
				</div>
				
				<div style="height: 20px; overflow: hidden; clear: both;"></div>
				<div class="row">
					<div class="cell" style="width: 130px;">
						
					</div>
					<div class="cell">
						<a href="javascript:;" class="button-white" title="Send" id="button_send"><span>Send</span></a>
					</div>
				</div>
			</div>
		</div>
		<div id="form_accept" style="display: none;">
			<h2>Thank you for your request!</h2>
			<p>We will contact you shortly.</p>
		</div>
	</div>
	<div style="float: left; width: 220px; padding-left: 10px;">
		<h3 class="ui-header-light">Can I upgrade my account?</h3>
		<p style="padding: 12px 0 24px 0px; margin:0;">Yes. You can upgrade your account any time. If you have any questions about upgrading, please contact us at <a href="mailto:sales@inqwise.com" title="sales@inqwise.com">sales@inqwise.com</a> and we will be more than happy to help.</p>
		<h3 class="ui-header-light">Do I have to sign a contract?</h3>
		<p style="padding: 12px 0 24px 0px; margin:0;">For our PRO, or Enterprise accounts, a contract is not required. You can upgrade and downgrade whenever you need with those accounts.<br/> They are month-to-month accounts and you can move from paying account to free account whenever you need.</p>
		<h3 class="ui-header-light">How long will you store my data?</h3>
		<p style="padding: 12px 0 24px 0px; margin:0;">As long as you want!<br/>We won’t delete your data even if you just have a free account. We are awesome that way.</p>
		<h3 class="ui-header-light">Is my data secure and private?</h3>
		<p>Yes, your data is secure and private. Your collected data is yours and we never use it or share it any way shape or form. Please see our <a href="<%=absoluteURL %>/privacy" title="Privacy Policy">Privacy Policy</a>, and <a href="<%=absoluteURL %>/terms" title="Terms of Use">Terms of Use</a> for further info.</p>
	</div>
	<div style="float: left; width: 220px; padding-left: 10px;">
		<h3 class="ui-header-light">What types of payment do you accept?</h3>
		<p style="padding: 12px 0; margin:0;">Credit card (including MasterCard, Visa, Discover or American Express as well as PayPal) via our secure online order form.</p>
		<img src="<%=applicationURL %>/images/paypal.png" alt="Payment types we accept" />
	</div>
</div>

<script type="text/javascript">
var loader = null;

$(function() { 
	
	loader = new lightLoader();
	
	new validator({
		elements : [
	        {
				element : $('#text_email'),
				status : $('#status_email'),
				rules : [
					{ method : 'required', message : 'This field is required.' },
					/* { method : 'emailISO', message : 'Please enter a valid email address.' } */
					{ method : 'email', message : 'Please enter a valid email address.' }
				]
	        }
		],
		submitElement : $('#button_send'),
		messages : null,
		accept : function () {
		
			// loader
			loader.show();
	
			var obj = {
				sendCustomPlanRequest : {
					email : $('#text_email').val(),
					notes : $('#textarea_comment').val()
				}
			};
	
			$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
				loader.hide();
				if(data.sendCustomPlanRequest.error != undefined) {
					//alert(data.sendCustomPlanRequest.errorDescription);
				} else {

					$('#form_post').hide();
					$('#form_accept').show();
					
				}
			});	
		}
	});
	
});
</script>