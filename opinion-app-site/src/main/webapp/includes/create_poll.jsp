<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.opinion.cms.common.IPage" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1><a href="<%=absoluteURL %>/polls" title="Polls">Polls</a>&nbsp;&rsaquo;&nbsp;Create a Poll</h1>
	<h2>How Would You Like to Create a Poll?</h2>
	<p>Select one of the options below to get started.<br/>If you already have an idea for your poll, just create a new poll from scratch.</p>
	<div style="padding-top: 12px;">
		<div id="poll_options"></div>
	</div>
	<div style="clear: both;padding-top: 12px;">
	
		<div style="clear: both; display: none;" id="form_create_poll_from_scratch">
			<div>Create a new poll from scratch.</div>
			<div style="padding-top: 24px;">
				<div class="params">
					<div class="param-name"><span>* Poll Name:</span></div>
					<div class="param-value">
						<div><input type="text" id="text_scratch_poll_name" name="scratch_poll_name" maxlength="254" autocomplete="off" placeholder="Name the new poll" style="width: 225px;" /></div>
						<div><label id="status_scratch_poll_name"></label></div>
					</div>
				</div>
				<!--
				<div class="params" style="height: 72px;">
					<div class="param-name"><span>Description:</span></div>
					<div class="param-value">
						<textarea id="textarea_scratch_poll_description" autocomplete="off" style="width: 314px; height: 64px;"></textarea>
					</div>
				</div>
				<div class="params">
					<div class="param-name"><span>Under Tags:</span></div>
					<div class="param-value">
						<div><input type="text" id="text_scratch_poll_tags" name="scratch_poll_tags" size="46" maxlength="254" autocomplete="off" /></div>
						<div style="padding-bottom: 10px;"><em style="color: #999">e.g. branding, pricing</em></div>
					</div>
				</div>
				-->
				<div style="height: 24px; overflow: hidden;clear: both;"></div>
				<div class="params">
					<div class="param-name">&nbsp;</div>
					<div class="param-value"><a href="javascript:;" class="button-blue" title="Create" id="button_create"><span>Create</span></a></div>
					<div class="param-value" style="line-height: 21px;"><a href="<%=absoluteURL %>/polls" title="Cancel" style="margin-left: 6px;">Cancel</a></div>
				</div>
			</div>
		</div>
		<div style="clear: both; display: none;" id="form_copy_poll">
			<div>Copy an existing poll to create a new version.</div>
			<div style="padding-top: 24px;">
				<div>
					
					<div class="params">
						<div class="param-name">* Copy a poll:</div>
						<div class="param-value" style="width: 231px">
							<div><select id="select_existing_polls" name="existing_polls" style="width: 100%;"><option value="">Select an existing poll</option></select></div>
							<div><label id="status_existing_polls"></label></div>
						</div>
						<div class="param-value" style="margin-left: 6px;">
							<a href="#" id="link_existing_poll_preview" style="display: none;" title="Preview" class="button-white"><span><i class="icon-preview">&nbsp;</i>Preview</span></a>
						</div>
					</div>
					<div style="display: none" id="section_copy_poll">
						<div class="params">
							<div class="param-name"><span>* Poll Name:</span></div>
							<div class="param-value">
								<div><input type="text" id="text_copy_poll_name" name="copy_poll_name" maxlength="254" autocomplete="off" style="width: 225px;" /></div>
								<div><label id="status_copy_poll_name"></label></div>
							</div>
						</div>
						
						<!--
						<div class="params" style="height: 72px;">
							<div class="param-name"><span>Description:</span></div>
							<div class="param-value">
								<textarea id="textarea_copy_poll_description" autocomplete="off" style="width: 314px; height: 64px;"></textarea>
							</div>
						</div>
						<div class="params">
							<div class="param-name"><span>Under Tags:</span></div>
							<div class="param-value">
								<div><input type="text" id="text_copy_poll_tags" size="46" maxlength="254" autocomplete="off" /></div>
								<div style="padding-bottom: 10px;"><em style="color: #999">e.g. branding, pricing</em></div>
							</div>
						</div>
						-->
						
						<div style="height: 24px; overflow: hidden;clear: both"></div>
						<div class="params">
							<div class="param-name">&nbsp;</div>
							<div class="param-value"><a href="javascript:;" class="button-blue" title="Copy" id="button_copy"><span>Copy</span></a></div>
							<div class="param-value" style="line-height: 21px;"><a href="<%=absoluteURL %>/polls" title="Cancel" style="margin-left: 6px;">Cancel</a></div>
						</div>
					</div>
				
				</div>
			</div>
		</div>
		<div style="clear: both; display: none;" id="form_import_poll_from_file">
			<div>Import poll from file</div>
			<div style="padding-top: 24px;">
				<div class="params">
					<div class="param-name"></div>
					<div class="param-value">
						
						<div>
							<div>
							    <span class="button-white fileinput-button">
							        <span><i class="icon-add">&nbsp;</i>Select files...</span>
							        <!-- The file input field used as target for the file upload widget -->
							        <input id="fileupload" type="file" name="files[]" multiple>
							    </span>
						    </div>
						    <div id="progress" class="progress">
						        <div class="progress-bar progress-bar-success"></div>
						    </div>
						    <div id="files" class="files"></div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
	</div>
</div>

<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/buttons.js" charset="utf-8"></script>
<script type="text/javascript">

var pollOptionsContent = [
	{ title : "Create a new poll", description : "", typeId : 1, type : "new" }, 
	{ title : "Copy an existing poll", description : "", typeId : 2, type : "copy" },
	{ title : "Use a pre-built template", description : "", typeId : 3, type : "template" },
	{ title : "Import from file", description : "", typeId : 4, type : "import" }
];

var getPollOptionsContentByTypeId = function(typeId) {
	for(var i = 0; i < pollOptionsContent.length; i++ ) {
		if(pollOptionsContent[i].typeId == typeId)
			return pollOptionsContent[i];
	}
};

var getList = function(params) {
	
	var obj = {
		opinions : {
			getList: {
				top:100,
				from:undefined,
				to:undefined,
				accountId:null,
				opinionTypeId:2 /*polls*/
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
			if(data.opinions.getList != undefined) {
				if(data.opinions.getList.error != undefined) {
					
					errorHandler({
						error : data.opinions.getList.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getList);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getList);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
};

var create = function(params) {
	
	var obj = {
		opinions : { 
			create : {
				name : params.name,
				title : params.title,
				description : params.description,
				tags : params.tags,
				opinionTypeId : params.opinionType
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
			if(data.opinions.create != undefined) {
				if(data.opinions.create.error != undefined) {
					
					errorHandler({
						error : data.opinions.create.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.create);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.create);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });

};

var copy = function(params) {
	
	var obj = {
		opinions : { 
			copy : {
				name : params.name,
				title : params.title,
				description : params.description,
				tags : [],
				opinionType : params.opinionType,
				opinionId : params.opinionId
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
			if(data.opinions.copy != undefined) {
				if(data.opinions.copy.error != undefined) {
					
					errorHandler({
						error : data.opinions.copy.error	
					});
					
					if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.copy);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.copy);
					}
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });

};

var pWin;

$(function() {
	
	$('#poll_options').buttons({
		active : 0,
		dataSource : [
			{ typeId : 1 },
			{ typeId : 2 }
			/* { typeId : 3}
			{ typeId : 4 } */
		],
		formatter : function(record, index) {
			//return $("<a class=\"button-white\" title=\"" + getPollOptionsContentByTypeId(record.typeId).title + "\"><i class=\"icon-" + getPollOptionsContentByTypeId(record.typeId).type + "\"></i><span>" + getPollOptionsContentByTypeId(record.typeId).title + "</span></a>");
			return $("<a class=\"button-white\" title=\"" + getPollOptionsContentByTypeId(record.typeId).title + "\"><span>" + getPollOptionsContentByTypeId(record.typeId).title + "</span></a>");
		},
		change : function(record, index) {
			
			// from scratch
			if(record.typeId == 1) {
				
				$('#form_copy_poll').hide();
				$('#form_import_poll_from_file').hide();
				
				$('#form_create_poll_from_scratch').show();
				
				var defaultFocus = function() {
					$('#text_scratch_poll_name').focus();
				};

				defaultFocus(); // set default focus
				
				new validator({
					elements : [
						{
							element : $('#text_scratch_poll_name'),
							status : $('#status_scratch_poll_name'),
							rules : [
								{ method : 'required', message : 'This field is required.' }
							]
						}
					],
					submitElement : $('#button_create'),
					messages : null,
					accept : function () {
						
						create({
							name : $.removeHTMLTags($('#text_scratch_poll_name').val()).replace(/\r/g, ""),
							/*title : $.removeHTMLTags($('#text_scratch_poll_name').val()).replace(/\r/g, ""),*/
							/*description : $.removeHTMLTags($('#textarea_scratch_poll_description').val()).replace(/\r/g, ""), */
							tags : [],
							opinionType : 2,
							success : function(data) {
								if(data.opinionId != undefined) {
									location.href = "<%=absoluteURL%>/polls/" + data.opinionId + "/edit";
								}
							},
							error: function() {
								
							}
						});
						
					},
					error: function() {
					
					}
				});
				
			}
			
			// copy poll from exists
			if(record.typeId == 2) {
				
				$('#form_create_poll_from_scratch').hide();
				$('#form_import_poll_from_file').hide();
				
				$('#form_copy_poll').show();
				
				
				$('#select_existing_polls')
					.unbind('change')
					.change(function() {
						var a = $(this).find('option:selected');
						if(a.val() != "") {
						
							var previewUrl = a.attr("preview_url");
							
							$('#link_existing_poll_preview')
								.show()
								.attr({ "href" : previewUrl })
								.unbind('click')
								.click(function() {
									
									var name = "pWin";
									this.target = name;
									
									if (pWin) 
										pWin.close();
									
									pWin = window.open('', name, 'resizable=1,scrollbars=1,status=1,menubar=1');
									
								});
							
							
							
							$('#section_copy_poll').show();
							$('#text_copy_poll_name').val("Copy of " + a.text()).select();
							
							var v = null;
							v = new validator({
								elements : [
									{
										element : $('#text_copy_poll_name'),
										status : $('#status_copy_poll_name'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : $('#button_copy'),
								messages : null,
								accept : function () {

									copy({
										name : $.removeHTMLTags($('#text_copy_poll_name').val()).replace(/\r/g, ""),
										/*title : $.removeHTMLTags($('#text_copy_poll_name').val()).replace(/\r/g, ""),*/
										/*description : $.removeHTMLTags($('#textarea_copy_poll_description').val()).replace(/\r/g, ""),*/
										tags : [],
										opinionType : 2,
										opinionId : parseInt($('#select_existing_polls').val()),
										success : function(data) {
											location.href = "<%=absoluteURL%>/polls/" + data.opinionId + "/edit";
										},
										error: function() {
											//
										}
									});
								
								},
								error : function() {
									// error
								}
							});
							
							
						} else {
							
							$('#link_existing_poll_preview').hide();
							$('#section_copy_poll').hide();
							
						}
						
					});
				
				
			}
			
			if(record.typeId == 4) {
				
				$('#form_create_poll_from_scratch').hide();
				$('#form_copy_poll').hide();
				
				$('#form_import_poll_from_file').show();
				
				// TODO:
				
			}
			
			
		}
	});

	getList({
		success : function(data) {
			
			var q = $('#select_existing_polls')[0].options;
			for(var i = 0; i < data.list.length; i++) {
				 var k = new Option(data.list[i].name, data.list[i].opinionId);
				 k.setAttribute("preview_url", data.list[i].previewUrl);
			 	 try {
			         q.add(k)
			     } catch (ex) {
			         q.add(k, null)
			     }
			}
			
		},
		error: function() {
			//
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
        	
        	if($('#text_scratch_poll_name').is(':focus')) {
        		$('#button_create').trigger('click');
        	}
        	
        	/*
        	if(!$('#button_create').is(':focus')) {
				v.validate();
			}
        	*/
        	
        }
	});
	
});
</script>
