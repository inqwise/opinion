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

<div>
	<h1><a href="<%=absoluteURL %>/setup" title="Setup">Setup</a>&nbsp;&rsaquo;&nbsp;Jobs</h1>
	<div>
		<div class="content-middle-tabs-section">
			<div class="content-middle-tabs">
				<ul class="content-middle-tabs-container">
					<li><a href="<%=absoluteURL %>/setup" title="?----"><span>?----</span></a></li>
					<li><a href="<%=absoluteURL %>/jobs" title="Jobs" class="selected"><span>Jobs</span></a></li>
					<li><a href="<%=absoluteURL %>/system-events" title="System Events"><span>System Events</span></a></li>
					<li><a href="<%=absoluteURL %>/plans" title="Plans"><span>Plans</span></a></li>
				</ul>
			</div>
			<div class="content-middle-related">
				
			</div>
		</div>
	</div>
	<div style="clear: both;padding-top: 20px;">
		<div id="table_jobs"></div>
	</div>
</div>

<script type="text/javascript">
var getJobs = function(params) {
	
	var obj = {
		control : {
			getJobs : {}
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
        	if(data.control.getJobs.error != undefined) {
				
        		errorHandler({
					error : data.control.getJobs.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.control.getJobs);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.control.getJobs);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var executeJob = function(params) {
	
	var obj = {
		control : {
			executeJob : {
				jobId : params.jobId
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
        	if(data.control.executeJob.error != undefined) {
				
        		errorHandler({
					error : data.control.executeJob.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.control.executeJob);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.control.executeJob);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var tableJobs = null;
var jobs = [];
var renderTableJobs = function() {
	
	$('#table_jobs').empty();
	tableJobs = $('#table_jobs').dataTable({
		tableColumns : [
			{ key : 'id', label : '#', sortable: true, width: 46, style : { header: { 'text-align' : 'right' }, cell : { 'text-align' : 'right' } } },
			{ key : 'assemblyName', label : 'Assembly Name', width: 340, sortable : true },
			{ key : 'description', label : 'Description', sortable : true },
			{ key : 'lastExecutionDate', label : 'Last Exec. Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.lastExecutionDate) {
					var left = record.lastExecutionDate.substring(record.lastExecutionDate.lastIndexOf(" "), " ");
					var right = record.lastExecutionDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
            { key : 'planedExecutionDate', label : 'Planned Exec. Date', sortable: true, sortBy : { dataType: "date" }, width: 126, formatter: function(cell, value, record, source) {
				if(record.planedExecutionDate) {
					var left = record.planedExecutionDate.substring(record.planedExecutionDate.lastIndexOf(" "), " ");
					var right = record.planedExecutionDate.replace(left, "");
					return left + "<b class=\"hours\">" + right + "</b>";
				} 
            }},
            { key : 'scheduleTask', label : 'Schedule Task', width: 120, sortable : true },
            { key : 'status', label : 'Status', width: 60, sortable : true },
            { key : 'status', label : '', width: 60, sortable : false, formatter: function(cell, value, record, source) {
				if(record.status == "Started") {
					var q = $("<a href=\"javascript:;\" job_id=\"" + record.id + "\" title=\"run\">run</a>");
					q.click(function() {
						var jobId = parseInt($(this).attr('job_id'));
						
						executeJob({
							jobId : jobId,
							success : function() {
								
								var modal = new lightFace({
									title : "Job is successful running.",
									message : "Job is successful running.",
									actions : [
							           { 
							        	   label : "OK", 
							        	   fire : function() { 
							        		   modal.close(); 
							        	   }, 
							        	   color : "blue" 
							           }
									],
									overlayAll : true
								});
								
							},
							error: function() {
								
								alert("Job is faild.")
								
							}
						});
						
					});
					
					return q;
					
				} 
            }}
		],
		dataSource : jobs, 
		pagingStart : 10,
		show : [5, 10, 25, 50, 100]
	});
	
};

$(function() {
	
	getJobs({
		success : function(data) {
			jobs = data.list;
			renderTableJobs();
		},
		error : function() {
			jobs = [];
			renderTableJobs();
		}
	});
	
});

</script>