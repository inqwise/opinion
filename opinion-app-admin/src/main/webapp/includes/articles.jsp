

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
						<li><a href="<%=absoluteURL %>/articles" class="selected" title="Articles"><span>Articles</span></a></li>
						<li><a href="<%=absoluteURL %>/topics" title="Topics"><span>Topics</span></a></li>
					</ul>
				</div>
				<div class="content-middle-related">
					
				</div>
			</div>
		</div>
		<div style="clear: both; padding-top: 20px;">
			<div id="table_articles"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
var getArticles = function(params) {
	
	var obj = {
		kb : {
			getArticles : {
				
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
        	if(data.kb.getArticles != undefined) {
	        	if(data.kb.getArticles.error != undefined) {
	        		errorHandler({
						error : data.kb.getArticles.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.getArticles);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.getArticles);
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

var deleteArticles = function(params) {
	
	var obj = {
		kb : {
			deleteArticles : {
				list : params.list
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
        	if(data.kb.deleteArticles != undefined) {
	        	if(data.kb.deleteArticles.error != undefined) {
	        		errorHandler({
						error : data.kb.deleteArticles.error	
					});
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.kb.deleteArticles);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.kb.deleteArticles);
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

var tableArticles = null;
var articles = [];
function renderTableArticles() {
	$('#table_articles').empty();
	tableArticles = $('#table_articles').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'title', label : 'Article', sortable: true, formatter : function(cell, value, record, source) {
				return $("<a href=\"<%=absoluteURL %>/article?article_id=" + record.id  + "\" title=\"" + record.title + "\">" + record.title + "</a>");
			}},
			{ key : 'topicName', label : 'Topic', sortable: true, formatter : function(cell, value, record, source) {
				return record.topicName;
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
            }},
            { key : 'popular', label : "Popular", formatter : function(cell, value, record, source) {
            	return record.popular ? "Yes" : "No";
            }},
            { key : 'active', label : "Status", formatter : function(cell, value, record, source) {
            	return record.active ? "Active" : "Inactive";
            }}
		],
		dataSource : articles, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100],
		selectable : true,
		actions : [
			{
				label : "New Article",
				icon : "add-white",
				color : "green",
				fire : function() {
					location.href = "article-new";
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
						
						var modal = new lightFace({
							title : "Deleting articles",
							message : "Are you sure you want to delete selected articles?",
							actions : [
								{ 
									label : "Delete", 
									fire : function() {
										
										deleteArticles({
											list : list,
											success : function(data) {
												
												// console.log(data);
												getArticles({
													success : function(data) {
														articles = data.list;
														renderTableArticles();
													},
													error: function(error) {
														articles = [];
														renderTableArticles();
													}
												});
												
												modal.close();
												
											},
											error: function(error) {
												console.log(error);
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
}

$(document).ready(function() {
	
	getArticles({
		success : function(data) {
			articles = data.list;
			renderTableArticles();
		},
		error: function(error) {
			articles = [];
			renderTableArticles();
		}
	});
	
});
</script>

