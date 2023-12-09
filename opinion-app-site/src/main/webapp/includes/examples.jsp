<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	
	String absoluteSecureURL = p.getAbsoluteSecureUrl();
	String applicationURL = p.getApplicationUrl();
%>

<h1>Examples!</h1>
<div>
	<div class="wrapper-content-left">
		<!-- <h3 class="ui-header-light">Some Ideas on How to Use Inqwise</h3> -->
		
		
		<h3 class="ui-header-light">Customer Satisfaction Survey</h3>
		<p>You don't need a marketing research team to gather detailed information about your customers. You can get structured feedback on the quality of your products, services and web sites with this Inqwise powered customer satisfaction survey. You can use our template as is or customize it with your own questions and flavor. After you brand it with your own logo and color scheme, you can email your users and customers the link to the form so they can conveniently fill out the survey on their own time. <br/><br/><a href="<%=absoluteURL %>/templates/surveys/customer-satisfaction-survey" title="View template">View template</a></p>
		
		<h3 class="ui-header-light">Market Research Survey</h3>
		<p>Get to know your customer, grow your business. The only way to keep your customers is to know exactly what they want. Guesswork and gut instinct won't cut it, so business owners and marketers turn to surveys to gather data about their target consumers. Marketers unlock the power of surveys to research a target market, understand buying habits, get product feedback, measure customer awareness, gain new customers, and so much more. <br/><br/><a href="<%=absoluteURL %>/templates/surveys/market-research-survey" title="View template">View template</a></p>
		
		<!--
		<h3 class="ui-header-light">Job Satisfaction Survey</h3>
		<p>Encourage an effective workforce with job satisfaction surveys. Support a happy, productive workplace by using surveys to ask about employee satisfaction and employee engagement. Do your employees feel their work is meaningful and their objectives clear? What motivates them to do their best work? Job satisfaction surveys help you make a meaningful connection between employees’ criteria for job satisfaction and your business goals, so you can engage your workforce in a truly satisfying way. <br/><br/><a href="<%=absoluteURL %>/templates/surveys/job-satisfaction-survey" title="View template">View template</a></p>
		-->
		
		<h3 class="ui-header-light">Mailing List</h3>
		<p>If you want to keep your users updated about the latest adventures regarding your company, product or service, use this mailing list form powered by Inqwise. You can then quickly embed the form into your web site so you can start building that valuable army of supporters. When you're ready to send out your email newsletter, just export the list to Excel or CSV and use your favorite mailing list program to get your message out. <br/><br/><a href="<%=absoluteURL %>/templates/forms/mailing-list" title="View template">View template</a></p> 
		
		<h3 class="ui-header-light">Contact Form</h3>
		<p>Use a contact form to make it easier for your web site visitors to send you feedback, questions and comments without having to expose your email address to spammers. Because your users won't have to open their mail client to send you their ideas and requests, it reduces the number of barriers between you and them. Feel free brand the contact form with your own logo and redirect them to our hosted version or embed it into your web site so they don't even have to leave your site. <br/><br/><a href="<%=absoluteURL %>/templates/forms/contact-form" title="View template">View template</a></p>
		
		<h3 class="ui-header-light">Event Registration</h3>
		<p>For your next workshop or conference, try using event registration form to make organizing your next event a breeze. Feel free to change the logo and customize the form to collect the appropriate information and interests from your guests. You can use it to coordinate the food, seating and any other accommodations needed by your attendees. <br/><br/><a href="<%=absoluteURL %>/templates/registrations/event-planner" title="View template">View template</a></p>
		
		<h3 class="ui-header-light">Party Invitation</h3>
		<p>For your next party or reception, save some paper and utilize the flexibility of invitation form to handle the RSVPs. Use one of our many themes or create one from scratch using our Theme Designer to match the invitation to your style and personality. You can then email a link to the invitation to your friends and family and have them give you immediate feedback without having to wait for a reply through regular mail. Because Inqwise allows you to add standard HTML to your forms, you can even embed a Google Map to your event right into the form. <br/><br/><a href="<%=absoluteURL %>/templates/invitations/wedding-invitation" title="View template">View template</a></p>
		
		<h3 class="ui-header-light">Bug Tracker</h3>
		<p>Quickly deploy an organized focal point for collecting feedback and bugs for your projects using this bug tracker. Now you can focus on fixing your users' problems rather than writing code to handle the logistics of collecting them. In addition to gathering the information you need to debug the issue, our file upload field conveniently allows your users to submit screenshots of the problem, so you can more quickly assess the situation. <br/><br/><a href="<%=absoluteURL %>/templates/tracking/bug-tracker" title="View template">View template</a></p>
	</div> 
</div>