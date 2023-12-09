<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>
<%@ page import="com.opinion.library.systemFramework.ApplicationConfiguration" %>

<%@ page import="com.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.opinion.library.common.countries.ICountry" %>
<%@ page import="com.opinion.library.managers.CountriesManager" %>
<%@ page import="com.opinion.library.common.countries.IStateProvince" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1><%= p.getHeader() %></h1>
	<div>
		<div id="table_messages"></div>
	</div>
</div>
<script type="text/javascript">
var tableMessages = null;
var messages = [];
var renderTableMessages = function() {
	
	$('#table_messages').empty();
	tableMessages = $('#table_messages').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'name', label : 'Subject', sortable : true },
			{ key : 'content', label : 'Message', sortable : true },
			{ key : 'publishDate', label : 'Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.publishDate) {
					var left = record.publishDate.substring(record.publishDate.lastIndexOf(" "), " ");
					var right = record.publishDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : messages, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
		    {
				label : "Delete",
				disabled : true,
				fire : function(records, source) {
				
					if(records.length > 0) {
						
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].id);
						});
						
						var modal = new lightFace({
							title : "Deleting messages",
							message : "Are you sure you want to delete selected messages?",
							actions : [
								{ 
									label : "Delete", 
									fire : function() {
										
										jsapi.messages.deleteList({
											list : list,
											success : function() {
												
												jsapi.messages.getList({
													includeClosed : true,
													success : function(data) {
														messages = data.list;
														renderTableMessages();
													},
													error : function() {
														messages = [];
														renderTableMessages();
													}
												});
												
												modal.close();
												
											},
											error : function() {
												//
											}
										});
										
									},
									color: "blue"
								}, 
								{
									label : "Cancel",
									fire: function() {
										modal.close();
									},
									color: "white"
								}
							],
							overlayAll : true
						});
						
					}
				}
			}
		]
	});
	
};
		
$(function() {
	
	jsapi.messages.getList({
		includeClosed : true,
		success : function(data) {
			messages = data.list;
			renderTableMessages();
		},
		error : function() {
			messages = [];
			renderTableMessages();
		}
	});
	
});
</script>