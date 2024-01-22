<%@ page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.xnap.commons.i18n.I18n" %>
<%@ page import="org.xnap.commons.i18n.I18nFactory" %>

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
				Do you have a question that isn't answered on the website?<br/> Fill out this short form below and we will contact you shortly.
			</div>
			<div>
				<div class="params">
					<div class="param-name"><span>* Name:</span></div>
					<div class="param-value">
						<div><input id="input_name" name="input-name" type="text" maxlength="50" autocomplete="off" /></div>
						<div><label id="status_input_name"></label></div>
					</div>
				</div>
				<div class="params">
					<div class="param-name"><span>* Email:</span></div>
					<div class="param-value">
						<div><input id="input_email" name="input-email" type="text" style="width: 200px;" maxlength="50" autocomplete="off" /></div>
						<div><label id="status_input_email"></label></div>
					</div>
				</div>
				<div class="params">
					<div class="param-name"><span>Phone:</span></div>
					<div class="param-value">
						<div><input id="input_phone" name="input-phone" type="text" maxlength="50" autocomplete="off" /></div>
						<div><label id="status_input_phone"></label></div>
					</div>
				</div>
				<div class="params" style="height: 72px;">
					<div class="param-name"><span>* Message:</span></div>
					<div class="param-value">
						<div>
							<textarea id="textarea_comment" name="textarea-comment" autocomplete="off" maxlength="255" style="width: 314px; height: 64px;"></textarea>
						</div>
						<div><label id="status_textarea_comment"></label></div>
					</div>
				</div>
				<div style="height: 20px; overflow: hidden; clear: both;"></div>
				<div class="params">
					<div class="param-name"></div>
					<div class="param-value">
						<a id="button_send" title="Send" class="button-white" href="javascript:;"><span>Send</span></a>
					</div>
				</div>
				
			</div>
		</div>
		<div id="form_accept" style="display: none;">
			<h2>Thank you for your request!</h2>
			<p>We will contact you shortly.</p>
		</div>
	</div>
	<div class="wrapper-content-middle"></div>
	<div class="wrapper-content-right"></div>
</div>

<script type="text/javascript">
var loader = null;
var v = null;

var defaultFocus = function() {
	$('#input_name').focus();
};

$(function() {
	
	loader = new lightLoader();

	defaultFocus(); // set default focus
	
	v = new validator({
		elements : [
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
	        },
	        {
				element : $('#textarea_comment'),
				status : $('#status_textarea_comment'),
				rules : [
					{ method : 'required', message : 'This field is required.' }
				]
	        }
		],
		submitElement : $('#button_send'),
		messages : null,
		accept : function () {
			// loader
			loader.show();
			
			var obj = {
				sendContactUsRequest : {
					email : $('#input_email').val(),
					name : $('#input_name').val(),
					phone : $('#input_phone').val(),
					comments : $('#textarea_comment').val()
				}
			};

			$.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
				
				loader.hide();
				
				if(data.sendContactUsRequest.error != undefined) {
					// alert(data.sendContactUsRequest.errorDescription);
				} else {

					$('#form_post').hide();
					$('#form_accept').show();
					
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
        	if(!$('#button_send').is(':focus')) {
            	if(!$('#textarea_comment').is(':focus')) {
					v.validate();
            	}
			}
        }
        
	});
	
});
</script>
