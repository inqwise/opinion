
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="com.inqwise.opinion.cms.common.IPage"%>
<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<h1><%=p.getHeader() %></h1>
<div>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/pages" class="selected" title="Pages"><span>Pages</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both;padding-top: 20px;">
		<div id="table_pages"></div>
	</div>
</div>

<script type="text/javascript">
var getPages = function(params) {
	
	var obj = {
		pages : {
			getPages : {
				
			}
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
        	if(data.pages.getPages != undefined) {
	        	if(data.pages.getPages.error != undefined) {
	        		errorHandler({
						error : data.pages.getPages.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.pages.getPages);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.pages.getPages);
					}
				}
        	} else {
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error();
				}
        	}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var tablePages = null;
var pages = [];
var renderTablePages = function() {
	$('#table_pages').empty();
	tablePages = $('#table_pages').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'title', label : 'Title', sortable : true },
			{ key : 'window_title', label : 'Window Title', sortable : true },
			{ key : 'header', label : 'Header', sortable : true },
			{ key : 'description', label : 'Description', sortable : true },
			{ key : 'keywords', label : 'Keywords', sortable : true },
			{ key : 'status', label : 'Status', sortable : true, width: 86 },
			{ key : 'insertDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.insertDate) {
					var left = record.insertDate.substring(record.insertDate.lastIndexOf(" "), " ");
					var right = record.insertDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : pages, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{
				label : "New Page",
				icon : "add-white",
				color : "green",
				fire : function() {
					location.href = "page-new";
				}
			},
			{ 
				label : "Delete",
				disabled : true,
				fire : function(records, source) {

					// TODO: only for testing
					if(records.length > 0) {

						var list = [];
						$(records).each(function(index) {
							list.push(records[index].id);
						});

                        console.log("delete pages");

					}					
				}
			}
		]
	});
};

$(document).ready(function() {
	
	getPages({
		success : function(data) {
			console.log(JSON.stringify(data));
			
			pages = data.list;
			renderTablePages();
		},
		error: function(error) {
			console.log(JSON.stringify(error));
			
			pages = [];
			renderTablePages();
		}
	});
	
});
</script>