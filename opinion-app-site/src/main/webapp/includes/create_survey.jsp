<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.inqwise.opinion.infrastructure.systemFramework.DateConverter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
%>

<div>
	<h1><a href="<%=absoluteURL %>/surveys" title="Surveys">Surveys</a>&nbsp;&rsaquo;&nbsp;Create a Survey</h1>
	<h3 class="ui-header-light">How Would You Like to Create a Survey?</h3>
	<div style="padding-top: 12px;">Select one of the options below to get started.<br/>If you already have an idea for your survey, just create a new survey from scratch.</div>
	<div style="padding-top: 12px;">
		<div id="survey_options"></div>
	</div>
	<div style="clear: both; padding-top: 12px;">
		
		<div style="clear: both; display: none;" id="form_create_survey_from_scratch">
			<div>Create a new survey from scratch</div>
			<div style="padding-top: 24px;">
				<div class="params">
					<div class="param-name"><span>* Survey Name:</span></div>
					<div class="param-value">
						<div><input type="text" id="text_scratch_survey_name" name="scratch_survey_name" maxlength="254" autocomplete="off" placeholder="Name the new survey" style="width: 225px;" /></div>
						<div><label id="status_scratch_survey_name"></label></div>
					</div>
				</div>
				<!--
				<div class="params" style="height: 72px;">
					<div class="param-name"><span>Description:</span></div>
					<div class="param-value">
						<textarea id="textarea_scratch_survey_description" autocomplete="off" style="width: 314px; height: 64px;"></textarea>
					</div>
				</div>
				<div class="params">
					<div class="param-name"><span>Under Tags:</span></div>
					<div class="param-value">
						<div><input type="text" id="text_scratch_survey_tags" name="scratch_survey_tags" size="46" maxlength="254" autocomplete="off" /></div>
						<div style="padding-bottom: 10px;"><em style="color: #999">e.g. branding, pricing</em></div>
					</div>
				</div>
				-->
				<div style="height: 24px; overflow: hidden; clear: both;"></div>
				<div class="params">
					<div class="param-name">&nbsp;</div>
					<div class="param-value"><a href="javascript:;" class="button-blue" title="Create" id="button_create"><span>Create</span></a></div>
					<div class="param-value" style="line-height: 21px;"><a href="<%=absoluteURL %>/surveys" title="Cancel" style="margin-left: 6px;">Cancel</a></div>
				</div>
			</div>
		</div>
		<div style="clear: both; display: none;" id="form_copy_survey">
			<div>Copy an existing survey to create a new version</div>
			<div style="padding-top: 24px;">
				<div>
					<div class="params">
						<div class="param-name">* Copy a survey:</div>
						<div class="param-value" style="width: 231px">
							<div><select id="select_existing_surveys" name="existing_surveys" style="width: 100%;"><option value="">Select an existing survey</option></select></div>
							<div><label id="status_existing_surveys"></label></div>
						</div>
						<div class="param-value" style="margin-left: 6px;">
							<a href="#" id="link_existing_survey_preview" style="display: none;" title="Preview" class="button-white"><span><i class="icon-preview">&nbsp;</i>Preview</span></a>
						</div>
					</div>
					<div style="display: none" id="section_copy_survey">
						<div class="params">
							<div class="param-name"><span>* Survey Name:</span></div>
							<div class="param-value">
								<div><input type="text" id="text_copy_survey_name" name="copy_survey_name" maxlength="254" autocomplete="off" style="width: 225px;" /></div>
								<div><label id="status_copy_survey_name"></label></div>
							</div>
						</div>
						
						<!--
						<div class="params" style="height: 72px;">
							<div class="param-name"><span>Description:</span></div>
							<div class="param-value">
								<textarea id="textarea_copy_survey_description" autocomplete="off" style="width: 314px; height: 64px;"></textarea>
							</div>
						</div>
						<div class="params">
							<div class="param-name"><span>Under Tags:</span></div>
							<div class="param-value">
								<div><input type="text" id="text_copy_survey_tags" size="46" maxlength="254" autocomplete="off" /></div>
								<div style="padding-bottom: 10px;"><em style="color: #999">e.g. branding, pricing</em></div>
							</div>
						</div>
						-->
						
						<div style="height: 24px; overflow: hidden; clear: both;"></div>
						<div class="params">
							<div class="param-name">&nbsp;</div>
							<div class="param-value"><a href="javascript:;" class="button-blue" title="Copy" id="button_copy"><span>Copy</span></a></div>
							<div class="param-value" style="line-height: 21px;"><a href="<%=absoluteURL %>/surveys" title="Cancel" style="margin-left: 6px;">Cancel</a></div>
						</div>
					</div>
						
				</div>
			</div>
		</div>
		<div style="clear: both; display: none;" id="form_use_survey_template">
			<div>Create a new survey from a pre-built template</div>
			<div style="padding-top: 24px;">
				<!-- 
				<div>
					<div>
						<div class="params">
							<div class="param-name">Under Category:</div>
							<div class="param-value"><select><option value="">Select a category</option></select></div>
						</div>
					</div>
					<div style="clear: both;">
						<div style="float: left; width: 350px;">
							<div class="params">
								<div class="param-name">* Pick a survey template:</div>
								<div class="param-value"><select><option value="">1</option></select></div>
							</div>
							<div><a href="#" title="Preview">Preview</a></div>
						</div>
						<div style="float: left; width: 350px;">
							<div id="row_template_survey_name">
								<div class="params">
									<div class="param-name"><span>Survey Name: *</span></div>
									<div class="param-value">
										<div><input type="text" id="text_template_survey_name" name="template_survey_name" size="46" maxlength="255" autocomplete="off" /></div>
										<div><label id="status_template_survey_name"></label></div>
									</div>
								</div>
								<div class="params" style="height: 72px;">
									<div class="param-name"><span>Description:</span></div>
									<div class="param-value">
										<textarea id="textarea_template_survey_description" autocomplete="off" style="width: 314px; height: 64px;"></textarea>
									</div>
								</div>
								<div class="params">
									<div class="param-name"><span>Under Tags:</span></div>
									<div class="param-value">
										<div><input type="text" id="text_template_survey_tags" size="46" maxlength="254" autocomplete="off" /></div>
										<div style="padding-bottom: 10px;"><em style="color: #999">e.g. branding, pricing</em></div>
									</div>
								</div>
								
								<div style="height: 24px; overflow; hidden;"></div>
								<div class="row">
									<div class="cell"><a href="javascript:;" class="button-blue" title="Create from Template" id="button_use_a_survey_template"><span>Create from Template</span></a></div>
									<div class="cell" style="padding-left: 6px; line-height: 21px;"><a href="<%=absoluteURL %>/surveys" title="Cancel">Cancel</a></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				-->
			</div>
		</div>
		<div style="clear: both; display: none;" id="form_import_survey_from_file">
			<div>Import survey from file</div>
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

<script type="text/javascript" src="<%=applicationURL%>/scripts/buttons/buttons.js"></script>
<script type="text/javascript">

var surveyOptionsContent = [
	{ title : "Create a new survey", description : "", typeId : 1, type : "new" }, 
	{ title : "Copy an existing survey", description : "", typeId : 2, type : "copy" },
	{ title : "Use a pre-built template", description : "", typeId : 3, type : "template" },
	{ title : "Import from file", description : "", typeId : 4, type : "import" }
];

var getSurveyOptionsContentByTypeId = function(typeId) {
	for(var i = 0; i < surveyOptionsContent.length; i++ ) {
		if(surveyOptionsContent[i].typeId == typeId)
			return surveyOptionsContent[i];
	}
};

var getList = function(params) {
	
	var obj = {
		opinions : {
			getList: {
				top : 100,
				from : undefined,
				to : undefined,
				accountId : params.accountId,
				opinionTypeId : 1 /*survey*/
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
				accountId : params.accountId,
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
				accountId : params.accountId,
				name : params.name,
				title : params.title,
				description : params.description,
				tags : [],
				opinionType : 1,
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
var buttonsSurveyOptions = null;

//var accountId = $.cookie("aid");

$(function() {
	
	buttonsSurveyOptions = $('#survey_options').buttons({
		active : 0,
		dataSource : [
			{ typeId : 1 },
			{ typeId : 2 }
			/* { typeId : 3 }
			{ typeId : 4 } */
		],
		formatter : function(record, index) {
			//return $("<a class=\"button-white\" title=\"" + getSurveyOptionsContentByTypeId(record.typeId).title + "\"><i class=\"icon-" + getSurveyOptionsContentByTypeId(record.typeId).type + "\"></i><span></span></a>");
			//return $("<a class=\"button-white\" title=\"" + getSurveyOptionsContentByTypeId(record.typeId).title + "\"><i class=\"icon-" + getSurveyOptionsContentByTypeId(record.typeId).type + "\"></i><span>" + getSurveyOptionsContentByTypeId(record.typeId).title + "</span></a>");
			return $("<a class=\"button-white\" title=\"" + getSurveyOptionsContentByTypeId(record.typeId).title + "\"><span>" + getSurveyOptionsContentByTypeId(record.typeId).title + "</span></a>");
		},
		change : function(record, index) {
			
			// default collector
			if(record.typeId == 1) {
				
				$('#form_copy_survey').hide();
				$('#form_use_survey_template').hide();
				$('#form_import_survey_from_file').hide();
				
				$('#form_create_survey_from_scratch').show();
				
				
				var defaultFocus = function() {
					$('#text_scratch_survey_name').focus();
				};

				defaultFocus(); // set default focus
				
				var v = null;
				v = new validator({
					elements : [
						{
							element : $('#text_scratch_survey_name'),
							status : $('#status_scratch_survey_name'),
							rules : [
								{ method : 'required', message : 'This field is required.' }
							]
						}
					],
					submitElement : $('#button_create'),
					messages : null,
					accept : function () {

						create({
							accountId : accountId,
							name : $.removeHTMLTags($('#text_scratch_survey_name').val()).replace(/\r/g, ""),
							title : $.removeHTMLTags($('#text_scratch_survey_name').val()).replace(/\r/g, ""),
							/* description : $.removeHTMLTags($('#textarea_scratch_survey_description').val()).replace(/\r/g, ""), */
							tags : [],
							opinionType : 1,
							success : function(data) {
								if(data.opinionId != undefined) {
									location.href = "<%=absoluteURL%>/surveys/" + data.opinionId + "/edit";
								}
							},
							error: function() {
								//
							}
						});
						
					
					},
					error : function() {

					}
				});
				
			}
			
			if(record.typeId == 2) {
			
				$('#form_create_survey_from_scratch').hide();
				$('#form_use_survey_template').hide();
				$('#form_import_survey_from_file').hide();
				
				$('#form_copy_survey').show();
				
				
				$('#select_existing_surveys')
					.unbind('change')
					.change(function() {
						var a = $(this).find('option:selected');
						if(a.val() != "") {
						
							var previewUrl = a.attr("preview_url");
							
							$('#link_existing_survey_preview')
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
							
							
							$('#section_copy_survey').show();
							$('#text_copy_survey_name').val("Copy of " + a.text()).select();
							
							var v = null;
							v = new validator({
								elements : [
									{
										element : $('#text_copy_survey_name'),
										status : $('#status_copy_survey_name'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : $('#button_copy'),
								messages : null,
								accept : function () {

									copy({
										accountId : accountId,
										name : $.removeHTMLTags($('#text_copy_survey_name').val()).replace(/\r/g, ""),
										title : $.removeHTMLTags($('#text_copy_survey_name').val()).replace(/\r/g, ""),
										/* description : $.removeHTMLTags($('#textarea_copy_survey_description').val()).replace(/\r/g, ""), */
										tags : [],
										opinionType : 1,
										opinionId : parseInt($('#select_existing_surveys').val()),
										success : function(data) {
											
											//console.log(JSON.stringify(data));
											location.href = "<%=absoluteURL%>/surveys/" + data.opinionId + "/edit";
											
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
							
							$('#link_existing_survey_preview').hide();
							$('#section_copy_survey').hide();
							
						}
						
					});
				
			}
			
			if(record.typeId == 3) {
				
				/*
				$('#form_create_survey_from_scratch').hide();
				$('#form_copy_survey').hide();
				$('#form_use_survey_template').show();
				*/
				
				buttonsSurveyOptions.setActive(0);
				
				var modal = new lightFace({
					title : "Coming Soon!",
					message : "Get ready to use an exciting new feature.<br/> Email us at <a href=\"mailto:support@inqwise.com?subject=Use Survey Templates\" title=\"support@inqwise.com\">support@inqwise.com</a> and be the first to find out more.",
					actions : [
						{ 
							label : "Close", 
							fire : function() { 
								modal.close();
							}, 
							color: "white" 
						}
					],
					overlayAll : true
				});
				
			}
			
			if(record.typeId == 4) {
				
				$('#form_create_survey_from_scratch').hide();
				$('#form_copy_survey').hide();
				$('#form_use_survey_template').hide();
				
				$('#form_import_survey_from_file').show();
				
				// TODO:
				
			}
			
		}
	});
		
	// Describe the survey (optional)
	// Tag the survey (comma separated, optional)

	// copy
	getList({
		accountId : accountId,
		success : function(data) {
			
			var q = $('#select_existing_surveys')[0].options;
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
        	
        	if($('#text_scratch_survey_name').is(':focus')) {
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