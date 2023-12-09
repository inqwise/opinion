
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
	<h1><%=p.getHeader() %></h1>
	<div>
		<div>
			<div class="content-middle-tabs-section">
				<div class="content-middle-tabs">
					<ul class="content-middle-tabs-container">
						<li><a href="<%=absoluteURL %>/articles" title="Articles"><span>Articles</span></a></li>
						<li><a href="<%=absoluteURL %>/topics" class="selected" title="Topics"><span>Topics</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			<div id="table_topics"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
var getTopics = function(params) {
	
	var obj = {
		kb : {
			getTopics : {
				
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
        	if(data.kb.getTopics != undefined) {
	        	if(data.kb.getTopics.error != undefined) {
	        		errorHandler({
						error : data.kb.getTopics.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.getTopics);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.getTopics);
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

var tableTopics = null;
var topics = [];
function renderTableTopics() {
	$('#table_topics').empty();
	tableTopics = $('#table_topics').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'name', label : 'Topic', sortable: true, formatter: function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/topic?topic_id=" + record.id  + "\" title=\"" + record.name + "\">" + record.name + "</a>");
			}},
			{ key : 'createDate', label : 'Create Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.createDate) {
					var left = record.createDate.substring(record.createDate.lastIndexOf(" "), " ");
					var right = record.createDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
            { key : 'modifyDate', label : 'Modify Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.modifyDate) {
					var left = record.modifyDate.substring(record.modifyDate.lastIndexOf(" "), " ");
					var right = record.modifyDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }}
		],
		dataSource : topics, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{
				label : "New Topic",
				icon : "add-white",
				color : "green",
				fire : function() {
					location.href = "topic-new";
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

						
					}
					
				}
			}
		]
	});	
}	

$(document).ready(function() {
	
	getTopics({
		success : function(data) {
			topics = data.list;
			renderTableTopics();
		},
		error: function(error) {
			topics = [];
			renderTableTopics();
		}
	});
	
});
</script>