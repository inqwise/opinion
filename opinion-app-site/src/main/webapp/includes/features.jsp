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
	<div class="wrapper-content-left">
		<div>
			<h3 class="ui-header-light">All the great survey software features behind Inqwise</h3>
			<div style="padding-top: 12px;">
				<p style="margin:0;">With user-friendly interface you can create even the most complex survey in minutes. Powerful survey software features help you gather the reliable data.<!-- <br><br> Sign up now, it's easy - and free! --></p>
				<!-- <a href="<%=absoluteURL %>/register?ref=features" title="Sign Up" class="button-yellow"><span>Sign Up</span></a> -->
			</div>
		</div>
		<div style="padding-top: 24px;">
			<div>
				<div class="overview">
					<h3 class="ui-header-light">Easy to use editor</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Create a survey using inline survey editor is fast and easy.</p>
				</div>
				<div class="overview far-right">
					<h3 class="ui-header-light">Question Types</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Select from the 10+ most common question types to quickly create online surveys.</p>
				</div>
				<!-- 
				<div class="overview">
					<h3 class="ui-header-light">Survey Templates and Question Library</h3>
					<div style="padding: 12px 0 24px 0">Hundreds of survey templates and thorough question library for easy survey creation.</div> 
				</div>
				<div class="overview">
					<h3 class="ui-header-light">Skip-Logic, Branching</h3>
					<div style="padding: 12px 0 24px 0px">Build rulesets to handle custom pathways for certain users.</div>
				</div>
				-->
				<div class="overview">
					<h3 class="ui-header-light">Professionally Designed Survey Themes</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Choose from over 20 survey themes to create great looking online surveys.</p>
				</div>
				<div class="overview far-right">
					<h3 class="ui-header-light">Custom Themes &amp; Branding Control</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Keep a consistent look &amp; feel of your company or website.</p>
				</div>
				<div class="overview">
					<h3 class="ui-header-light">Customize Surveys with your Brand Logo</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Quickly upload and insert your logo and custom text into survey header.</p>
				</div>
				<div class="overview far-right">
					<h3 class="ui-header-light">Multimedia Support (images, audio, video, Flash)</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Insert music and movie clips, including flash, avi, mp3, quicktime etc.</p>
				</div>
				<div class="overview">
					<h3 class="ui-header-light">Distribute surveys via email, website, blog or QR codes</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Utilize multiple channels to distribute your surveys.</p>
				</div>
				<div class="overview far-right">
					<h3 class="ui-header-light">Social Network Integration (Facebook, Twitter, Reddit)</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Easily distribute your surveys using the most popular social sharing tools.</p>
				</div>
				<div class="overview">
					<h3 class="ui-header-light">Password Protection</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Enable password protected access to surveys.</p>
				</div>
				<div class="overview far-right">
					<h3 class="ui-header-light">Action Alerts</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Be notified immediately when someone answers in a particular way to your questions in your survey.</p>
				</div>
				<div class="overview">
					<h3 class="ui-header-light">Real-time Reports</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Robust real time reporting and analytics.</p>
				</div>
				<div class="overview far-right">
					<h3 class="ui-header-light">Respondent Tracking and Statistics</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Track individual respondents and how they responded.</p>
				</div>
				<div class="overview">
					<h3 class="ui-header-light">Export data to Excel (*.XLSX), CSV</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Easily extract your survey data for further analysis and presentation.</p>
				</div>
				<div class="overview far-right">
					<h3 class="ui-header-light">Multilingual Surveys, Languages Support</h3>
					<p style="padding: 12px 0 24px 0px; margin:0">Support all languages, including double byte character-sets (Chinese/Japanese etc.) and Right-To-Left (RTL) languages like Arabic and Hebrew.</p>
				</div>
			</div>
		</div>
		<!--
		Advanced Reports<br/>Collect data you collect into powerful reporting systems to help you make the most informed decisions.
		Reminder emails Functionality<br/>Send reminder emails to those who have not completed the survey.
		-->
	</div>
	<div class="wrapper-content-middle">
	&nbsp;
	</div>
	<div class="wrapper-content-right">
		<div class="ad">
			<h3 class="ad-heading">Get Started. <br>Create Your Own Surveys</h3>
			<div style="padding-top: 12px; padding-bottom: 12px;">
				<ul class="ls">
					<li>Free account never expires, <br>unlimited surveys!</li>
					<li>PRO account for as low as $7.95</li>
				</ul>
			</div>
			<a class="button-yellow" title="Sign Up FREE" href="<%=absoluteURL %>/register?ref=features"><span>Sign Up FREE</span></a>
		</div>
		<div style="padding-top: 24px;">
			<h3 class="ui-header">Have a question?</h3>
			<p style="padding: 12px 0 24px 8px; margin: 0">Contact us day or night at <a title="support@inqwise.com" href="mailto:support@inqwise.com">support@inqwise.com</a></p>
		</div>
	</div>
</div>