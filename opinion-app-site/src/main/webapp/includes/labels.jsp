
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%@ page import="com.opinion.cms.common.IPage" %>
<%@ page import="com.opinion.library.systemFramework.ApplicationConfiguration" %>
<%@ page import="com.opinion.library.common.errorHandle.OperationResult" %>
<%@ page import="com.opinion.library.common.countries.ICountry" %>
<%@ page import="com.opinion.library.managers.CountriesManager" %>
<%@ page import="com.opinion.library.common.countries.ITimeZone" %>
<%@ page import="com.opinion.library.common.countries.IStateProvince" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1>Labels</h1>
	<div style="clear: both; Xpadding-top: 24px;">
		<div id="table_labels"></div>
	</div>
</div>

<script type="text/javascript">
var labels = [];
var tableLabels = null;
var renderTableLabels = function() {
	
	$('#table_labels').empty();
	tableLabels = $('#table_labels').dataTable({
		tableColumns : [
			{ key : 'labelId', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'label', label : 'Label', sortable : true, formatter : function(cell, value, record, source) {
				return $("<span>" + record.label + "</span>");
			}},
			{ key : 'description', label : 'Description', sortable : true }
		],
		dataSource : labels, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{
				label : "New label",
				color: "green",
				icon : "add-white",
				fire : function() {
					
				}
			},
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {

					if(records.length > 0) {
	
						var list = [];
						$(records).each(function(index) {
							list.push(records[index].labelId);
						});
						
						var modal = new lightFace({
							title : "Deleting labels",
							message : "Are you sure you want to delete selected labels?",
							actions : [
								{ 
									label : "Delete", 
									fire : function() {
										
										// list
										// modal.close();
										
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
			},
			{
				label : "More actions",
				disabled : true,
				condition : 1,
				actions : [
					{ 
						label : "Rename",
						fire : function(records, source) {
							
						}
					}
				]
			}
		]
	});
};

$(function() {
	
	
	labels = [
		{ labelId : 1, label : "Important", color: 'red', description: "" }        
	];
	renderTableLabels();
	
});
</script>