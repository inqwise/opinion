
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.opinion.cms.common.IPage"%>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1>Ticket List</h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/tickets" class="selected" title="Tickets"><span>Tickets</span></a></li>
						<li><a href="<%=absoluteURL %>/articles" title="KB Articles"><span>KB Articles</span></a></li>
						<li><a href="<%=absoluteURL %>/topics" title="Topics"><span>Topics</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both;padding-top: 24px;">
			<div id="table_tickets"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
var tableTickets = null;


$(function() {
	//getPayments({
	//	success : function(data) {			
			tableTickets = $('#table_tickets').dataTable({
				tableColumns : [
					{ key : 'ticketId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
					{ key : 'subject', label : 'Subject', sortable: true, formatter : function(cell, value, record, source) {
						return $("<a href=\"<%=absoluteURL %>/ticket?ticket_id=" + record.ticketId  + "\" title=\"" + record.subject + "\">" + record.subject + "</a>");
					}},
					{ key : 'userName', label : 'Submitted By', sortable: true, width: 200, formatter : function(cell, value, record, source) {
						return $("<a href=\"<%=absoluteURL %>/user-details?user_id=" + record.userId  + "\" title=\"" + record.userName + "\">" + record.userName + "</a>");
					}},
					{ key : 'ticketType', label : 'Ticket Type', sortable: true, width: 200 },
					{ key : 'status', label : 'Status', sortable: true, width: 100 },
					{ key : 'priority', label : 'Priority', sortable: true, width: 100 },
					{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
						if(record.insertDate) {
							var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
							var right = record.insertDate.replace(left, "");
							return left + "<b class=\"hours\">" + right + "</b>";
						} 
		            }}
				],
				dataSource : [{
					ticketId : 1,
					subject : "When do you release the overall report?",
					userName : "basil@inqwise.com",
					userId : 3,
					ticketType : "Software",
					status : "Open", // Close
					priority : "High",
					insertDate : "May 16, 2012 01:05:12"
				}], 
				pagingStart : 10,
				show : [5, 10, 25, 50, 100],
				selectable : true,
				actions : [
					{ 
						label : "Delete",
						disabled : true,
						fire : function(records, source) {
	
							// TODO: only for testing
							if(records.length > 0) {
	
								var list = [];
								$(records).each(function(index) {
									list.push(records[index].ticketId);
								});
	
								/*
								deleteOpinions(list, function() {
									tableSurveys.deleteRecords(records);
								});
								*/
							}
							
						}
					}
				]
			});
	//	}
	//});
});
</script>