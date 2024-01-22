

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.inqwise.opinion.library.systemFramework.ApplicationConfiguration.Opinion.Collector"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.inqwise.opinion.cms.common.IPage" %>

<%

	String opinionId = null;
	opinionId = request.getParameter("opinion_id");
		
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();

%>

<style type="text/css">
.frame-preview {
	-moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background: none repeat scroll 0 0 #FFFFFF;
    padding-left: 10px;
    display: table-cell;
    vertical-align: top;
}
.col-3 {
	Xfloat: left;
	Xbackground-color: red;
	width : 450px;
}
.block-preview {
	Xoverflow-x: hidden;
    Xoverflow-y: auto;
    
    Xborder-color: #ccc;
    Xborder-image: none;
    Xborder-style: solid;
    Xborder-width: 1px;
    
    min-width: 450px;
    Xbackground: red;
}
.sidebar {
	min-height: 300px;
	background-color: #fff;
	position: fixed;
	
}
.frame-edit {
	display: table-cell;
	vertical-align: top;
	Xfloat: left;
	Xmargin-right: 460px;
	min-width: 450px;
	min-height: 300px;
	width: 450px;
	Xbackground-color: red;
}
</style>

<link rel="stylesheet" href="<%=applicationURL%>/css/minicolors/minicolors.css" type="text/css" />

<div>
	<table class="placeholder-header">
		<tr>
			<td class="cell-left">
				<h1 style="padding-bottom: 0 !important;display: inline"><a href="<%=absoluteURL %>/surveys" title="Surveys">Surveys</a>&nbsp;&rsaquo;&nbsp;<span id="label_survey_name"></span></h1><a href="javascript:;" id="link_rename_survey_name" title="Rename" style="margin-left: 6px;">Rename</a>
			</td>
			<td class="cell-right">
				<a href="<%=absoluteURL %>/surveys/create" title="Create Survey" class="button-green"><span><i class="icon-add-white">&nbsp;</i>Create Survey</span></a>
			</td>
		</tr>
	</table>
	<div style="clear: both; height: 16px; padding-top: 4px; padding-bottom: 20px;">
		<ul class="ln">
			<li class="first-item"><a href="javascript:;" title="Copy" id="link_survey_copy">Copy</a></li>
			<li><a href="javascript:;" title="Delete" id="link_survey_delete">Delete</a></li>
			<li><a id="link_export" title="Export" style="display:none">Export</a></li>
			<li><a href="javascript:;" title="Distribute / Embed" id="link_survey_publish">Distribute / Embed</a></li>
			<li><a id="link_preview" title="Preview">Preview</a></li>
		</ul>
	</div>
	<div class="content-middle-tabs-section">
		<div class="content-middle-tabs">
			<ul class="content-middle-tabs-container">
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/edit" title="Edit"><span>Edit</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/settings" title="Settings"><span>Settings</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/reordering" title="Reordering"><span>Reordering</span></a></li>
				<li id="tab_rules"><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/rules" title="Rules"><span>Rules<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/branding" class="selected" title="Branding"><span>Branding<i class="icon-beta"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/collectors" title="Collectors"><span>Collectors</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/overall-results" title="Overall Results"><span>Overall Results<i class="icon-new"></i></span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/responses" title="Responses"><span>Responses</span></a></li>
				<li><a href="<%=absoluteURL %>/surveys/<%=opinionId %>/statistics" title="Statistics"><span>Statistics</span></a></li>
			</ul>
		</div>
		<div class="content-middle-related">
			
		</div>
	</div>
	<div style="clear: both;padding-top: 20px; min-height: 300px;">
		<div style="display: table">
			<div class="frame-edit">
				<div class="ui-tabs-section">
					<div>
						<ul class="ui-tabs" id="tabs">
							<li><a href="#form_themes" title="Select Theme">Select Theme</a></li>
							<li><a href="#form_edit_theme" title="Use Custom Settings">Use Custom Settings</a></li>
						</ul>
					</div>
				</div>
				<div style="clear: both; display: none; min-height: 350px" id="form_themes">
					<div>
						<div style="padding: 12px 0 0 0;">
							<ul class="themes" id="themes"></ul>
						</div>
					</div>
					<div style="clear: both;">
						<div style="padding-top: 12px;">To apply selected theme please click below on button "Apply Theme" or "Preview" for preview your selected theme. If your didn't apply any theme, then by default your theme will be "Default".</div>
						<div style="height: 24px; overflow: hidden;"></div>
						<div class="row">
							<div class="cell"><a href="javascript:;" id="button_apply_theme" title="Apply Theme" class="button-blue"><span>Apply Theme</span></a></div>
							<div class="cell" style="margin-left: 6px;"><a onclick="preview(this)" id="button_preview_theme" title="Preview" class="button-white"><span>Preview</span></a></div>
						</div>
					</div>
				</div>
				<div style="clear: both; display: none; min-height: 350px" id="form_edit_theme" class="black-view">
					<div style="padding: 12px 0 0 0">
						<div>Based on theme:</div> 
						<div style="padding-top: 6px;"><select id="select_based_on_theme"></select></div>
					</div>
					<div style="padding-top: 10px;">
						<a class="button-blue" id="button_save" title="Save"><span>Save</span></a>
						<a class="button-blue" id="button_save_as" title="Save As..."><span>Save As...</span></a>
						<!--
						<a class="button-white" id="button_download" title="Download"><span><b class="icon-down">&nbsp;</b>Download</span></a>
						<a class="button-white" id="button_import" title="Import"><span><b class="icon-import">&nbsp;</b>Import</span></a>
						-->
					</div>
					
					<div style="padding-top: 10px;">
						
						<div style="clear: both;" id="list_theme_properties">
							<div class="accordion" data-form="ui-body-a">
								<h3><a>Body</a></h3>
								<div>
									
									<div class="params" style="display: none;">
										<div class="param-name">Width:</div>
										<div class="param-value"><input type="text" class="number" data-type="width" data-name="a-body-width" /></div>
									</div>
									<div class="params" style="display: none;">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-body-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-body-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-body-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-body-margin-left" />
										</div>
									</div>
									<div class="params" style="display: none;">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-body-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-body-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-body-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-body-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Font family:</div>
										<div class="param-value"><input type="text" data-type="font-family" data-name="a-body-font-family" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font size:</div>
										<div class="param-value"><input type="text" class="number" data-type="font-size" data-name="a-body-font-size" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font weight:</div>
										<div class="param-value">
											<select data-type="font-weight" data-name="a-body-font-weight">
												<option value="normal">Normal</option>
												<option value="bold">Bold</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Line height:</div>
										<div class="param-value"><input type="text" class="number" data-type="line-height" data-name="a-body-line-height" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-body-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-body-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-body-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-body-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-body-text-shadow-color" autocomplete="off" />
										</div>
									</div>
									<div class="params" style="display: table-cell;">
										<div class="param-name">Background:</div>
										<div class="param-value">
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-body-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-body-background-color"></div>
													<a class="more" data-name="a-body-background">+</a>
												</div>
												<div class="start-end" data-name="a-body-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-body-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-body-background-end" autocomplete="off" />
												</div>
											</div>
										</div>
									</div>
									
									<div class="params" style="display: none">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-body-border-width" autocomplete="off" /></div>
									</div>
									<div class="params" style="display: none">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-body-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params" style="display: none">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-body-border-color" autocomplete="off" /></div>
									</div>
									<div class="params" style="display: none">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-body-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-body-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-body-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-body-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
									<div class="params" style="display: none">
										<div class="param-name">Box shadow:</div>
										<div class="param-value">
											<input type="text" class="colorwell ui-droppable" data-type="box-shadow" data-name="a-body-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-body-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-body-box-shadow-size" data-alternative-name="box-shadow-size" autocomplete="off" />
										</div>
									</div>
									
									
								</div>
							</div>
							<div class="accordion" data-form="ui-wrapper-a">
								<h3><a>Wrapper</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Width:</div>
										<div class="param-value"><input type="text" class="number" data-type="width" data-name="a-wrapper-width" /></div>
									</div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-wrapper-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-wrapper-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-wrapper-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-wrapper-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-wrapper-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-wrapper-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-wrapper-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-wrapper-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-wrapper-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-wrapper-background-color"></div>
													<a class="more" data-name="a-wrapper-background">+</a>
												</div>
												<div class="start-end" data-name="a-wrapper-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-wrapper-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-wrapper-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-wrapper-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-wrapper-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-wrapper-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-wrapper-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-wrapper-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-wrapper-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-wrapper-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Box shadow:</div>
										<div class="param-value">
											<input type="text" class="colorwell ui-droppable" data-type="box-shadow" data-name="a-wrapper-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-wrapper-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-wrapper-box-shadow-size" data-alternative-name="box-shadow-size" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							<div class="accordion" data-form="ui-header-a">
								<h3><a>Header</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Min-Height:</div>
										<div class="param-value"><input type="text" class="number" data-type="min-height" data-name="a-header-min-height" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-header-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-header-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-header-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-header-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-header-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-header-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-header-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-header-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-header-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-header-background-color"></div>
													<a class="more" data-name="a-header-background">+</a>
												</div>
												<div class="start-end" data-name="a-header-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-header-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-header-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-header-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-header-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-header-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-header-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-header-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-header-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-header-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
									
									<div class="params">
										<div class="param-name">Box shadow:</div>
										<div class="param-value">
											<input type="text" class="colorwell ui-droppable" data-type="box-shadow" data-name="a-header-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-header-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-header-box-shadow-size" data-alternative-name="box-shadow-size" autocomplete="off" />
										</div>
									</div>
									
								</div>
							</div>
							
							<div class="accordion" data-form="ui-logo-a">
								<h3><a>Logo</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-logo-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-logo-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-logo-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-logo-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-logo-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-logo-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-logo-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-logo-padding-left" />
										</div>
									</div>
								</div>
							</div>
																			
							<div class="accordion" data-form="ui-content-a">
								<h3><a>Content</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-content-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-content-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-content-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-content-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-content-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-content-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-content-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-content-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-content-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-content-background-color"></div>
													<a class="more" data-name="a-content-background">+</a>
												</div>
												<div class="start-end" data-name="a-content-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-content-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-content-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-content-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-content-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-content-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-content-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-content-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-content-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-content-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
									
									<div class="params">
										<div class="param-name">Box shadow:</div>
										<div class="param-value">
											<input type="text" class="colorwell ui-droppable" data-type="box-shadow" data-name="a-content-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-content-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-content-box-shadow-size" data-alternative-name="box-shadow-size" autocomplete="off" />
										</div>
									</div>
									
								</div>
							</div>
							
							<div class="accordion" data-form="ui-survey-heading-a">
								<h3><a>Survey Heading</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-survey-heading-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-survey-heading-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-survey-heading-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-survey-heading-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-survey-heading-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-survey-heading-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-survey-heading-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-survey-heading-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Font family:</div>
										<div class="param-value"><input type="text" data-type="font-family" data-name="a-survey-heading-font-family" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font size:</div>
										<div class="param-value"><input type="text" class="number" data-type="font-size" data-name="a-survey-heading-font-size" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font weight:</div>
										<div class="param-value">
											<select data-type="font-weight" data-name="a-survey-heading-font-weight">
												<option value="normal">Normal</option>
												<option value="bold">Bold</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Line height:</div>
										<div class="param-value"><input type="text" class="number" data-type="line-height" data-name="a-survey-heading-line-height" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-survey-heading-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-survey-heading-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-survey-heading-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-survey-heading-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-survey-heading-text-shadow-color" autocomplete="off" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-survey-heading-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-survey-heading-background-color"></div>
													<a class="more" data-name="a-survey-heading-background">+</a>
												</div>
												<div class="start-end" data-name="a-survey-heading-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-survey-heading-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-survey-heading-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-survey-heading-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-survey-heading-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-survey-heading-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-survey-heading-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-survey-heading-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-survey-heading-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-survey-heading-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="accordion" data-form="ui-page-a">
								<h3><a>Page</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-page-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-page-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-page-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-page-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-page-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-page-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-page-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-page-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-page-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-page-background-color"></div>
													<a class="more" data-name="a-page-background">+</a>
												</div>
												<div class="start-end" data-name="a-page-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-page-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-page-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-page-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-page-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-page-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-page-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							<div class="accordion" data-form="ui-page-heading-a">
								<h3><a>Page Heading</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-page-heading-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-page-heading-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-page-heading-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-page-heading-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-page-heading-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-page-heading-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-page-heading-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-page-heading-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Font family:</div>
										<div class="param-value"><input type="text" data-type="font-family" data-name="a-page-heading-font-family" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font size:</div>
										<div class="param-value"><input type="text" class="number" data-type="font-size" data-name="a-page-heading-font-size" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font weight:</div>
										<div class="param-value">
											<select data-type="font-weight" data-name="a-page-heading-font-weight">
												<option value="normal">Normal</option>
												<option value="bold">Bold</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Line height:</div>
										<div class="param-value"><input type="text" class="number" data-type="line-height" data-name="a-page-heading-line-height" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-page-heading-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-page-heading-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-page-heading-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-page-heading-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-page-heading-text-shadow-color" autocomplete="off" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-page-heading-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-page-heading-background-color"></div>
													<a class="more" data-name="a-page-heading-background">+</a>
												</div>
												<div class="start-end" data-name="a-page-heading-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-page-heading-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-page-heading-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-page-heading-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-page-heading-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-page-heading-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-page-heading-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-heading-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-heading-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-heading-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							<div class="accordion" data-form="ui-page-description-a">
								<h3><a>Page Description</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-page-description-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-page-description-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-page-description-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-page-description-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-page-description-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-page-description-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-page-description-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-page-description-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Font family:</div>
										<div class="param-value"><input type="text" data-type="font-family" data-name="a-page-description-font-family" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font size:</div>
										<div class="param-value"><input type="text" class="number" data-type="font-size" data-name="a-page-description-font-size" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font weight:</div>
										<div class="param-value">
											<select data-type="font-weight" data-name="a-page-description-font-weight">
												<option value="normal">Normal</option>
												<option value="bold">Bold</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Line height:</div>
										<div class="param-value"><input type="text" class="number" data-type="line-height" data-name="a-page-description-line-height" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-page-description-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-page-description-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-page-description-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-page-description-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-page-description-text-shadow-color" autocomplete="off" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-page-description-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-page-description-background-color"></div>
													<a class="more" data-name="a-page-description-background">+</a>
												</div>
												<div class="start-end" data-name="a-page-description-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-page-description-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-page-description-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-page-description-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-page-description-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-page-description-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-page-description-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-description-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-description-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-page-description-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							<div class="accordion" data-form="ui-page-actions-a">
								<h3><a>Page Actions</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-page-actions-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-page-actions-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-page-actions-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-page-actions-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-page-actions-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-page-actions-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-page-actions-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-page-actions-padding-left" />
										</div>
									</div>
								</div>
							</div>
							<div class="accordion" data-form="ui-progress-bar-a">
								<h3><a>Progress Bar</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-progress-bar-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-progress-bar-background-color"></div>
													<a class="more" data-name="a-progress-bar-background">+</a>
												</div>
												<div class="start-end" data-name="a-progress-bar-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-progress-bar-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-progress-bar-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-progress-bar-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-progress-bar-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-progress-bar-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-progress-bar-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-progress-bar-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-progress-bar-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-progress-bar-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
									
									-- Indicator --
									
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-progress-indicator-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-progress-indicator-background-color"></div>
													<a class="more" data-name="a-progress-indicator-background">+</a>
												</div>
												<div class="start-end" data-name="a-progress-indicator-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-progress-indicator-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-progress-indicator-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-progress-indicator-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-progress-indicator-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-progress-indicator-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-progress-indicator-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
									
								</div>
							</div>
							<div class="accordion" data-form="ui-question-a">
								<h3><a>Question</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-question-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-question-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-question-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-question-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-question-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-question-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-question-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-question-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-question-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-question-background-color"></div>
													<a class="more" data-name="a-question-background">+</a>
												</div>
												<div class="start-end" data-name="a-question-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-question-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-question-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-question-color" autocomplete="off" /></div>
									</div>
									
									<div class="params">
										<div class="param-name">Alternative Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-question-alternative-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-question-alternative-background-color"></div>
													<a class="more" data-name="a-question-alternative-background">+</a>
												</div>
												<div class="start-end" data-name="a-question-alternative-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-question-alternative-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-question-alternative-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									
									<div class="params">
										<div class="param-name">Alternative color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-question-alternative-color" autocomplete="off" /></div>
									</div>
									
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-question-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-question-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-question-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-question-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="accordion" data-form="ui-question-heading-a">
								<h3><a>Question Heading</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-question-heading-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-question-heading-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-question-heading-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-question-heading-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-question-heading-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-question-heading-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-question-heading-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-question-heading-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Font family:</div>
										<div class="param-value"><input type="text" data-type="font-family" data-name="a-question-heading-font-family" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font size:</div>
										<div class="param-value"><input type="text" class="number" data-type="font-size" data-name="a-question-heading-font-size" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font weight:</div>
										<div class="param-value">
											<select data-type="font-weight" data-name="a-question-heading-font-weight">
												<option value="normal">Normal</option>
												<option value="bold">Bold</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Line height:</div>
										<div class="param-value"><input type="text" class="number" data-type="line-height" data-name="a-question-heading-line-height" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-question-heading-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-question-heading-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-question-heading-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-question-heading-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-question-heading-text-shadow-color" autocomplete="off" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-question-heading-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-question-heading-background-color"></div>
													<a class="more" data-name="a-question-heading-background">+</a>
												</div>
												<div class="start-end" data-name="a-question-heading-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-question-heading-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-question-heading-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-question-heading-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-question-heading-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-question-heading-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-question-heading-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-heading-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-heading-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-heading-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="accordion" data-form="ui-question-content-a">
								<h3><a>Question Content</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-question-content-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-question-content-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-question-content-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-question-content-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-question-content-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-question-content-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-question-content-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-question-content-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-question-content-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-question-content-background-color"></div>
													<a class="more" data-name="a-question-content-background">+</a>
												</div>
												<div class="start-end" data-name="a-question-content-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-question-content-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-question-content-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-question-content-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-question-content-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-question-content-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-question-content-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-content-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-content-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-question-content-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="accordion" data-form="ui-button-a">
								<h3><a>Button</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-button-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-button-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-button-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-button-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-button-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-button-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-button-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-button-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Font family:</div>
										<div class="param-value"><input type="text" data-type="font-family" data-name="a-button-font-family" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font size:</div>
										<div class="param-value"><input type="text" class="number" data-type="font-size" data-name="a-button-font-size" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Font weight:</div>
										<div class="param-value">
											<select data-type="font-weight" data-name="a-button-font-weight">
												<option value="normal">Normal</option>
												<option value="bold">Bold</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Line height:</div>
										<div class="param-value"><input type="text" class="number" data-type="line-height" data-name="a-button-line-height" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-button-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-button-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-button-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-button-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-button-text-shadow-color" autocomplete="off" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-button-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-button-background-color"></div>
													<a class="more" data-name="a-button-background">+</a>
												</div>
												<div class="start-end" data-name="a-button-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-button-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-button-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-button-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-button-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-button-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-button-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-button-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-button-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-button-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
									<div>Hover ---</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-button-hover-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-button-hover-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-button-hover-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-button-hover-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-button-hover-text-shadow-color" autocomplete="off" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-button-hover-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-button-hover-background-color"></div>
													<a class="more" data-name="a-button-hover-background">+</a>
												</div>
												<div class="start-end" data-name="a-button-hover-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-button-hover-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-button-hover-background-end" autocomplete="off" />
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-button-hover-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-button-hover-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-button-hover-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-button-hover-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-button-hover-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-button-hover-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-button-hover-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="accordion" data-form="ui-link-a">
								<h3><a>Link</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-link-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-link-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-link-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-link-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-body-text-shadow-color" autocomplete="off" />
										</div>
									</div>
									<div>Hover ---</div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-link-hover-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-link-hover-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-link-hover-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-link-hover-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-link-hover-text-shadow-color" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="accordion" data-form="ui-status-a">
								<h3><a>Error</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-status-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-status-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-status-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-status-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-status-text-shadow-color" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="accordion" data-form="ui-asterisk-a">
								<h3><a>Asterisk</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Text color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="color" data-name="a-asterisk-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Text shadow:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="text-shadow" data-name="a-asterisk-text-shadow-x" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-asterisk-text-shadow-y" autocomplete="off" />
											<input type="text" class="number" data-type="text-shadow" data-name="a-asterisk-text-shadow-radius" autocomplete="off" />
											<input type="text" class="colorwell ui-droppable" data-type="text-shadow" data-name="a-asterisk-text-shadow-color" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="accordion" data-form="ui-footer-a">	
								<h3><a>Footer</a></h3>
								<div>
									<div class="params">
										<div class="param-name">Min-Height:</div>
										<div class="param-value"><input type="text" class="number" data-type="min-height" data-name="a-footer-min-height" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Margin:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="margin" data-name="a-footer-margin-top" />
											<input type="text" class="number" data-type="margin" data-name="a-footer-margin-right" />
											<input type="text" class="number" data-type="margin" data-name="a-footer-margin-bottom" />
											<input type="text" class="number" data-type="margin" data-name="a-footer-margin-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Padding:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="padding" data-name="a-footer-padding-top" />
											<input type="text" class="number" data-type="padding" data-name="a-footer-padding-right" />
											<input type="text" class="number" data-type="padding" data-name="a-footer-padding-bottom" />
											<input type="text" class="number" data-type="padding" data-name="a-footer-padding-left" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Background:</div>
										<div class="param-value">
											
											<div class="params">
												<div class="param-value">
													<input type="text" class="colorwell ui-droppable" data-type="background" data-name="a-footer-background-color" autocomplete="off" />
													<div class="slider" data-type="background" data-name="a-footer-background-color"></div>
													<a class="more" data-name="a-footer-background">+</a>
												</div>
												<div class="start-end" data-name="a-footer-background" style="display: none;">
													<label class="first">Start</label>
													<input type="text" class="colorwell ui-droppable" data-type="start" data-name="a-footer-background-start" autocomplete="off" />
													<label>End</label>
													<input type="text" class="colorwell ui-droppable" data-type="end" data-name="a-footer-background-end" autocomplete="off" />	
												</div>
											</div>
											
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border width:</div>
										<div class="param-value"><input type="text" class="number" data-type="border-width" data-name="a-footer-border-width" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border style:</div>
										<div class="param-value">
											<select data-type="border-style" data-name="a-footer-border-style" autocomplete="off">
												<option value="none">None</option>
												<option value="solid">Solid</option>
												<option value="dotted">Dotted</option>
												<option value="dashed">Dashed</option>
												<option value="double">Double</option>
											</select>
										</div>
									</div>
									<div class="params">
										<div class="param-name">Border color:</div>
										<div class="param-value"><input type="text" class="colorwell ui-droppable" data-type="border-color" data-name="a-footer-border-color" autocomplete="off" /></div>
									</div>
									<div class="params">
										<div class="param-name">Border radius:</div>
										<div class="param-value">
											<input type="text" class="number" data-type="border-radius" data-name="a-footer-border-radius-top-left" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-footer-border-radius-top-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-footer-border-radius-bottom-right" autocomplete="off" />
											<input type="text" class="number" data-type="border-radius" data-name="a-footer-border-radius-bottom-left" autocomplete="off" />
										</div>
									</div>
									<div class="params">
										<div class="param-name">Box shadow:</div>
										<div class="param-value">
											<input type="text" class="colorwell ui-droppable" data-type="box-shadow" data-name="a-footer-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-footer-box-shadow-color" autocomplete="off" />
											<input type="text" class="number" data-type="box-shadow" data-name="a-footer-box-shadow-size" data-alternative-name="box-shadow-size" autocomplete="off" />
										</div>
									</div>
								</div>
							</div>
							
							
						</div>
						
						
					</div>
				</div>
			</div>
			<div class="frame-preview col-3">
				&nbsp;
				<div class="block-preview sidebar">
					
					<div id="panel_colors" style="display: none;">
						<div style="padding-bottom: 10px;">
							<a id="undo" class="button-white" title="Undo"><span>Undo</span></a>
							<a id="redo" class="button-white" title="Redo"><span>Redo</span></a>
							<a id="inspector-button" class="button-white" title="Inspector"><span>Inspector <strong>off</strong></span></a>
						</div>
						<div style="clear: both;">
							<div id="quickswatch" style="float: left; padding-right: 10px; height:74px;">
								<h2 style="font-weight: normal;">Drag a color onto an element below</h2>
								<div class="colors">
									<div class="color-drag" style="background-color: #FFFFFF"></div>
									<div class="color-drag" style="background-color: #F2F2F2"></div>
									<div class="color-drag" style="background-color: #E6E6E6"></div>
									<div class="color-drag" style="background-color: #CCCCCC"></div>
									<div class="color-drag" style="background-color: #808080"></div>
									<div class="color-drag" style="background-color: #4D4D4D"></div>
									<div class="color-drag" style="background-color: #000000"></div>
									<div class="color-drag" style="background-color: #C1272D"></div>
									<div class="color-drag" style="background-color: #ED1C24"></div>
									<div class="color-drag" style="background-color: #F7931E"></div>
									<div class="color-drag" style="background-color: #FFCC33"></div>
									<div class="color-drag" style="background-color: #FCEE21"></div>
									<div class="color-drag" style="background-color: #D9E021"></div>
									<div class="color-drag" style="background-color: #8CC63F"></div>
									<div class="color-drag" style="background-color: #009245"></div>
									<div class="color-drag" style="background-color: #006837"></div>
									<div class="color-drag" style="background-color: #00A99D"></div>
									<div class="color-drag" style="background-color: #33CCCC"></div>
									<div class="color-drag" style="background-color: #33CCFF"></div>
									<div class="color-drag" style="background-color: #29ABE2"></div>
									<div class="color-drag" style="background-color: #0071BC"></div>
									<div class="color-drag" style="background-color: #2E3192"></div>
									<div class="color-drag" style="background-color: #662D91"></div>
									<div class="color-drag" style="background-color: #93278F"></div>
									<div class="color-drag" style="background-color: #D4145A"></div>
									<div class="color-drag" style="background-color: #ED1E79"></div>
									<div class="color-drag" style="background-color: #C7B299"></div>
									<div class="color-drag" style="background-color: #736357"></div>
									<div class="color-drag" style="background-color: #C69C6D"></div>
									<div class="color-drag" style="background-color: #8C6239"></div>
									<div class="color-drag" style="background-color: #603813"></div>
								</div>
								<div id="sliders">
									<span>Lightness</span><div id="lightness_slider"></div>
									<span>Saturation</span><div id="saturation_slider"></div>
								</div>
							</div>
							<div id="most-recent-colors" style="float: left; width: 150px; height:74px">
								<div class="picker">
									<h2 style="font-weight: normal;">Recent Colors</h2>
									<!--
									<div class="compact">
										<a id="recent-color-picker" href="#">colors...</a>
		                <input type="text" class="colorwell-toggle" value="#FFFFFF" data-name="recent" style="display: none" />
									</div>
									-->
								</div>
								<!-- <div class="clear"></div> -->
								<div class="colors" style="padding-top: 10px;">
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
									<div class="color-drag disabled" style="background-color: #ddd"></div>
								</div>
							</div>
						</div>
						
					</div>
					<div id="frame_block" class="ui-transparent" style="clear: both;border: 1px solid #ccc;height: 300px;">
						<iframe id="frame" style="border: none; height:100%;width:99.9%"></iframe>
					</div>
				</div>
				
			</div>
		</div>
		
		<div id="style" style="display: none;">
/*
* Inqwise Git Build: SHA1: 9130927464f2d3e0300a9fb36ebfd69448cf3675 <> Date: Wed Feb 20 11:08:42 2013 -0800
* http://www.inqwise.com
*
* Copyright 2011, 2013 Inqwise, Corp. and other contributors
* Released under the MIT license.
*
*/

.theme-body { 
	background: #ffffff /*{a-body-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-body-background-start}*/), to( #ffffff /*{a-body-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-body-background-start}*/, #ffffff /*{a-body-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-body-background-start}*/, #ffffff /*{a-body-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-body-background-start}*/, #ffffff /*{a-body-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-body-background-start}*/, #ffffff /*{a-body-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-body-background-start}*/, #ffffff /*{a-body-background-end}*/); 
	font-family: arial,sans-serif /*{a-body-font-family}*/; 
	color: #606060 /*{a-body-color}*/; 
	font-size: 11px /*{a-body-font-size}*/; 
	font-weight: normal /*{a-body-font-weight}*/;
	margin: 0px /*{a-body-margin-top}*/ 0px /*{a-body-margin-right}*/ 0px /*{a-body-margin-bottom}*/ 0px /*{a-body-margin-left}*/;
	padding: 0px /*{a-body-padding-top}*/ 0px /*{a-body-padding-right}*/ 0px /*{a-body-padding-bottom}*/ 0px /*{a-body-padding-left}*/; 
	line-height: 16px /*{a-body-line-height}*/; 
	width: auto /*{a-body-width}*/;
	border-width: 0px /*{a-body-border-width}*/; 
	border-style: none /*{a-body-border-style}*/; 
	border-color: #000000 /*{a-body-border-color}*/;
	-webkit-border-radius: 0px /*{a-body-border-radius-top-left}*/ 0px /*{a-body-border-radius-top-right}*/ 0px /*{a-body-border-radius-bottom-right}*/ 0px /*{a-body-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-body-border-radius-top-left}*/ 0px /*{a-body-border-radius-top-right}*/ 0px /*{a-body-border-radius-bottom-right}*/ 0px /*{a-body-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-body-border-radius-top-left}*/ 0px /*{a-body-border-radius-top-right}*/ 0px /*{a-body-border-radius-bottom-right}*/ 0px /*{a-body-border-radius-bottom-left}*/;	
	text-shadow: 0px /*{a-body-text-shadow-x}*/ 0px /*{a-body-text-shadow-y}*/ 0px /*{a-body-text-shadow-radius}*/ #000000 /*{a-body-text-shadow-color}*/;
	-moz-box-shadow: 0 1px 0px /*{a-body-box-shadow-size}*/ rgba(0,0,0,0) /*{a-body-box-shadow-color}*/;
	-webkit-box-shadow: 0 1px 0px /*{a-body-box-shadow-size}*/ rgba(0,0,0,0) /*{a-body-box-shadow-color}*/;
	box-shadow: 0 1px 0px /*{a-body-box-shadow-size}*/ rgba(0,0,0,0) /*{a-body-box-shadow-color}*/; 
}
.theme-body.oposite-direction { direction: rtl; }
.theme-body ul {
    list-style: none outside none;
} 
.theme-body ul, 
.theme-body ol, 
.theme-body li, 
.theme-body form, 
.theme-body input, 
.theme-body button, 
.theme-body textarea, 
.theme-body p {
    margin: 0;
    padding: 0;
}
.theme-body a { 
	color: #324e8d /*{a-link-color}*/;
	text-shadow: 0px /*{a-link-text-shadow-x}*/ 0px /*{a-link-text-shadow-y}*/ 0px /*{a-link-text-shadow-radius}*/ #000000 /*{a-link-text-shadow-color}*/;
	outline-style: none;
}
.theme-body a:hover { 
	text-decoration:underline; 
	color: #f7931e /*{a-link-hover-color}*/;
	text-shadow: 0px /*{a-link-hover-text-shadow-x}*/ 0px /*{a-link-hover-text-shadow-y}*/ 0px /*{a-link-hover-text-shadow-radius}*/ #000000 /*{a-link-hover-text-shadow-color}*/; 
}
.theme-body .asterisk {
	color: #ba0a1c /*{a-asterisk-color}*/;
	text-shadow: 0px /*{a-asterisk-text-shadow-x}*/ 0px /*{a-asterisk-text-shadow-y}*/ 0px /*{a-asterisk-text-shadow-radius}*/ #000000 /*{a-asterisk-text-shadow-color}*/;
} 
.theme-body .status { 
	color: #ba0a1c /*{a-status-color}*/;
	text-shadow: 0px /*{a-status-text-shadow-x}*/ 0px /*{a-status-text-shadow-y}*/ 0px /*{a-status-text-shadow-radius}*/ #000000 /*{a-status-text-shadow-color}*/;
}
.theme-button {
	margin: 0px /*{a-button-margin-top}*/ 0px /*{a-button-margin-right}*/ 0px /*{a-button-margin-bottom}*/ 0px /*{a-button-margin-left}*/; 
	padding: 1px /*{a-button-padding-top}*/ 6px /*{a-button-padding-right}*/ 1px /*{a-button-padding-bottom}*/ 6px /*{a-button-padding-left}*/; 
	background: #e5e5e5 /*{a-button-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #f9f9f9 /*{a-button-background-start}*/), to( #e5e5e5 /*{a-button-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #f9f9f9 /*{a-button-background-start}*/, #e5e5e5 /*{a-button-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #f9f9f9 /*{a-button-background-start}*/, #e5e5e5 /*{a-button-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #f9f9f9 /*{a-button-background-start}*/, #e5e5e5 /*{a-button-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #f9f9f9 /*{a-button-background-start}*/, #e5e5e5 /*{a-button-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #f9f9f9 /*{a-button-background-start}*/, #e5e5e5 /*{a-button-background-end}*/); 
	border-width: 1px /*{a-button-border-width}*/; 
	border-style: solid /*{a-button-border-style}*/; 
	border-color: #999999 /*{a-button-border-color}*/;
	-webkit-border-radius: 0px /*{a-button-border-radius-top-left}*/ 0px /*{a-button-border-radius-top-right}*/ 0px /*{a-button-border-radius-bottom-right}*/ 0px /*{a-button-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-button-border-radius-top-left}*/ 0px /*{a-button-border-radius-top-right}*/ 0px /*{a-button-border-radius-bottom-right}*/ 0px /*{a-button-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-button-border-radius-top-left}*/ 0px /*{a-button-border-radius-top-right}*/ 0px /*{a-button-border-radius-bottom-right}*/ 0px /*{a-button-border-radius-bottom-left}*/;
	font-family: arial,sans-serif /*{a-button-font-family}*/;
	font-size: 11px /*{a-button-font-size}*/;
	font-weight: bold /*{a-button-font-weight}*/;
	line-height: 16px /*{a-button-line-height}*/;
	text-shadow: 0px /*{a-button-text-shadow-x}*/ 1px /*{a-button-text-shadow-y}*/ 0px /*{a-button-text-shadow-radius}*/ #ffffff /*{a-button-text-shadow-color}*/ !important;
	display:inline-block;
	text-decoration:none;
	text-align:center;
	cursor:pointer;
}
.theme-button:hover {
	background: #dddddd /*{a-button-hover-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #e5e5e5 /*{a-button-hover-background-start}*/), to( #dddddd /*{a-button-hover-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #e5e5e5 /*{a-button-hover-background-start}*/, #dddddd /*{a-button-hover-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #e5e5e5 /*{a-button-hover-background-start}*/, #dddddd /*{a-button-hover-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #e5e5e5 /*{a-button-hover-background-start}*/, #dddddd /*{a-button-hover-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #e5e5e5 /*{a-button-hover-background-start}*/, #dddddd /*{a-button-hover-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #e5e5e5 /*{a-button-hover-background-start}*/, #dddddd /*{a-button-hover-background-end}*/); 
	border-width: 1px /*{a-button-hover-border-width}*/; 
	border-style: solid /*{a-button-hover-border-style}*/; 
	border-color: #999999 /*{a-button-hover-border-color}*/;
	-webkit-border-radius: 0px /*{a-button-hover-border-radius-top-left}*/ 0px /*{a-button-hover-border-radius-top-right}*/ 0px /*{a-button-hover-border-radius-bottom-right}*/ 0px /*{a-button-hover-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-button-hover-border-radius-top-left}*/ 0px /*{a-button-hover-border-radius-top-right}*/ 0px /*{a-button-hover-border-radius-bottom-right}*/ 0px /*{a-button-hover-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-button-hover-border-radius-top-left}*/ 0px /*{a-button-hover-border-radius-top-right}*/ 0px /*{a-button-hover-border-radius-bottom-right}*/ 0px /*{a-button-hover-border-radius-bottom-left}*/;
	text-shadow: 0px /*{a-button-hover-text-shadow-x}*/ 1px /*{a-button-hover-text-shadow-y}*/ 0px /*{a-button-hover-text-shadow-radius}*/ #ffffff /*{a-button-hover-text-shadow-color}*/ !important;
	text-decoration:none;
}
.theme-button span {
	text-decoration: none;
	padding: 1px 0 2px;
	display: inline-block;
	margin: 0;
	line-height: 13px;
}
.theme-button,
.theme-button span {
	color: #333333 /*{a-button-color}*/;
}
.theme-button:hover span {
	color: #333333 /*{a-button-hover-color}*/;
	text-decoration:none;
}
.theme-wrapper { 
	width: auto /*{a-wrapper-width}*/;
	margin: 0px /*{a-wrapper-margin-top}*/ 0px /*{a-wrapper-margin-right}*/ 0px /*{a-wrapper-margin-bottom}*/ 0px /*{a-wrapper-margin-left}*/; 
	padding: 0px /*{a-wrapper-padding-top}*/ 10px /*{a-wrapper-padding-right}*/ 0px /*{a-wrapper-padding-bottom}*/ 10px /*{a-wrapper-padding-left}*/; 
	background: #ffffff /*{a-wrapper-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-wrapper-background-start}*/), to( #ffffff /*{a-wrapper-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-wrapper-background-start}*/, #ffffff /*{a-wrapper-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-wrapper-background-start}*/, #ffffff /*{a-wrapper-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-wrapper-background-start}*/, #ffffff /*{a-wrapper-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-wrapper-background-start}*/, #ffffff /*{a-wrapper-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-wrapper-background-start}*/, #ffffff /*{a-wrapper-background-end}*/); 
	border-width: 0px /*{a-wrapper-border-width}*/; 
	border-style: none /*{a-wrapper-border-style}*/; 
	border-color: #000000 /*{a-wrapper-border-color}*/;
	-webkit-border-radius: 0px /*{a-wrapper-border-radius-top-left}*/ 0px /*{a-wrapper-border-radius-top-right}*/ 0px /*{a-wrapper-border-radius-bottom-right}*/ 0px /*{a-wrapper-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-wrapper-border-radius-top-left}*/ 0px /*{a-wrapper-border-radius-top-right}*/ 0px /*{a-wrapper-border-radius-bottom-right}*/ 0px /*{a-wrapper-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-wrapper-border-radius-top-left}*/ 0px /*{a-wrapper-border-radius-top-right}*/ 0px /*{a-wrapper-border-radius-bottom-right}*/ 0px /*{a-wrapper-border-radius-bottom-left}*/;
	-moz-box-shadow: 0 1px 0px /*{a-wrapper-box-shadow-size}*/ rgba(0,0,0,0) /*{a-wrapper-box-shadow-color}*/;
	-webkit-box-shadow: 0 1px 0px /*{a-wrapper-box-shadow-size}*/ rgba(0,0,0,0) /*{a-wrapper-box-shadow-color}*/;
	box-shadow: 0 1px 0px /*{a-wrapper-box-shadow-size}*/ rgba(0,0,0,0) /*{a-wrapper-box-shadow-color}*/;
}
.theme-logo {
	margin: 0px /*{a-logo-margin-top}*/ 0px /*{a-logo-margin-right}*/ 0px /*{a-logo-margin-bottom}*/ 0px /*{a-logo-margin-left}*/;
	padding: 10px /*{a-logo-padding-top}*/ 0px /*{a-logo-padding-right}*/ 0px /*{a-logo-padding-bottom}*/ 0px /*{a-logo-padding-left}*/;
}
.theme-header { 
	min-height: 80px /*{a-header-min-height}*/; 
	margin: 0px /*{a-header-margin-top}*/ 0px /*{a-header-margin-right}*/ 0px /*{a-header-margin-bottom}*/ 0px /*{a-header-margin-left}*/; 
	padding: 0px /*{a-header-padding-top}*/ 0px /*{a-header-padding-right}*/ 0px /*{a-header-padding-bottom}*/ 0px /*{a-header-padding-left}*/; 
	background: #ffffff /*{a-header-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-header-background-start}*/), to( #ffffff /*{a-header-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-header-background-start}*/, #ffffff /*{a-header-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-header-background-start}*/, #ffffff /*{a-header-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-header-background-start}*/, #ffffff /*{a-header-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-header-background-start}*/, #ffffff /*{a-header-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-header-background-start}*/, #ffffff /*{a-header-background-end}*/); 
	border-width: 0px /*{a-header-border-width}*/; 
	border-style: none /*{a-header-border-style}*/; 
	border-color: #000000 /*{a-header-border-color}*/;
	-webkit-border-radius: 0px /*{a-header-border-radius-top-left}*/ 0px /*{a-header-border-radius-top-right}*/ 0px /*{a-header-border-radius-bottom-right}*/ 0px /*{a-header-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-header-border-radius-top-left}*/ 0px /*{a-header-border-radius-top-right}*/ 0px /*{a-header-border-radius-bottom-right}*/ 0px /*{a-header-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-header-border-radius-top-left}*/ 0px /*{a-header-border-radius-top-right}*/ 0px /*{a-header-border-radius-bottom-right}*/ 0px /*{a-header-border-radius-bottom-left}*/;
	-moz-box-shadow: 0 1px 0px /*{a-header-box-shadow-size}*/ rgba(0,0,0,0) /*{a-header-box-shadow-color}*/;
	-webkit-box-shadow: 0 1px 0px /*{a-header-box-shadow-size}*/ rgba(0,0,0,0) /*{a-header-box-shadow-color}*/;
	box-shadow: 0 1px 0px /*{a-header-box-shadow-size}*/ rgba(0,0,0,0) /*{a-header-box-shadow-color}*/;
}
.theme-content { 
	margin: 0px /*{a-content-margin-top}*/ 0px /*{a-content-margin-right}*/ 0px /*{a-content-margin-bottom}*/ 0px /*{a-content-margin-left}*/; 
	padding: 0px /*{a-content-padding-top}*/ 0px /*{a-content-padding-right}*/ 0px /*{a-content-padding-bottom}*/ 0px /*{a-content-padding-left}*/; 
	background: #ffffff /*{a-content-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-content-background-start}*/), to( #ffffff /*{a-content-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-content-background-start}*/, #ffffff /*{a-content-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-content-background-start}*/, #ffffff /*{a-content-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-content-background-start}*/, #ffffff /*{a-content-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-content-background-start}*/, #ffffff /*{a-content-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-content-background-start}*/, #ffffff /*{a-content-background-end}*/); 
	border-width: 0px /*{a-content-border-width}*/; 
	border-style: none /*{a-content-border-style}*/; 
	border-color: #000000 /*{a-content-border-color}*/;
	-webkit-border-radius: 0px /*{a-content-border-radius-top-left}*/ 0px /*{a-content-border-radius-top-right}*/ 0px /*{a-content-border-radius-bottom-right}*/ 0px /*{a-content-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-content-border-radius-top-left}*/ 0px /*{a-content-border-radius-top-right}*/ 0px /*{a-content-border-radius-bottom-right}*/ 0px /*{a-content-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-content-border-radius-top-left}*/ 0px /*{a-content-border-radius-top-right}*/ 0px /*{a-content-border-radius-bottom-right}*/ 0px /*{a-content-border-radius-bottom-left}*/;
	-moz-box-shadow: 0 1px 0px /*{a-content-box-shadow-size}*/ rgba(0,0,0,0) /*{a-content-box-shadow-color}*/;
	-webkit-box-shadow: 0 1px 0px /*{a-content-box-shadow-size}*/ rgba(0,0,0,0) /*{a-content-box-shadow-color}*/;
	box-shadow: 0 1px 0px /*{a-content-box-shadow-size}*/ rgba(0,0,0,0) /*{a-content-box-shadow-color}*/;
}
.theme-survey-heading { 
	margin: 0px /*{a-survey-heading-margin-top}*/ 0px /*{a-survey-heading-margin-right}*/ 0px /*{a-survey-heading-margin-bottom}*/ 0px /*{a-survey-heading-margin-left}*/; 
	padding: 0px /*{a-survey-heading-padding-top}*/ 0px /*{a-survey-heading-padding-right}*/ 0px /*{a-survey-heading-padding-bottom}*/ 0px /*{a-survey-heading-padding-left}*/; 
	background: #ffffff /*{a-survey-heading-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-survey-heading-background-start}*/), to( #ffffff /*{a-survey-heading-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-survey-heading-background-start}*/, #ffffff /*{a-survey-heading-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-survey-heading-background-start}*/, #ffffff /*{a-survey-heading-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-survey-heading-background-start}*/, #ffffff /*{a-survey-heading-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-survey-heading-background-start}*/, #ffffff /*{a-survey-heading-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-survey-heading-background-start}*/, #ffffff /*{a-survey-heading-background-end}*/); 
	border-width: 0px /*{a-survey-heading-border-width}*/; 
	border-style: none /*{a-survey-heading-border-style}*/; 
	border-color: #000000 /*{a-survey-heading-border-color}*/;
	-webkit-border-radius: 0px /*{a-survey-heading-border-radius-top-left}*/ 0px /*{a-survey-heading-border-radius-top-right}*/ 0px /*{a-survey-heading-border-radius-bottom-right}*/ 0px /*{a-survey-heading-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-survey-heading-border-radius-top-left}*/ 0px /*{a-survey-heading-border-radius-top-right}*/ 0px /*{a-survey-heading-border-radius-bottom-right}*/ 0px /*{a-survey-heading-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-survey-heading-border-radius-top-left}*/ 0px /*{a-survey-heading-border-radius-top-right}*/ 0px /*{a-survey-heading-border-radius-bottom-right}*/ 0px /*{a-survey-heading-border-radius-bottom-left}*/;
	font-family: arial,sans-serif /*{a-survey-heading-font-family}*/; 
	color: #333333 /*{a-survey-heading-color}*/; 
	font-size: 16px /*{a-survey-heading-font-size}*/; 
	font-weight: bold /*{a-survey-heading-font-weight}*/;
	line-height: 22px /*{a-survey-heading-line-height}*/;
	text-shadow: 0px /*{a-survey-heading-text-shadow-x}*/ 0px /*{a-survey-heading-text-shadow-y}*/ 0px /*{a-survey-heading-text-shadow-radius}*/ #000000 /*{a-survey-heading-text-shadow-color}*/;
}
.theme-page {
	margin: 0px /*{a-page-margin-top}*/ 0px /*{a-page-margin-right}*/ 0px /*{a-page-margin-bottom}*/ 0px /*{a-page-margin-left}*/; 
	padding: 24px /*{a-page-padding-top}*/ 0px /*{a-page-padding-right}*/ 24px /*{a-page-padding-bottom}*/ 0px /*{a-page-padding-left}*/; 
	background: #ffffff /*{a-page-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-page-background-start}*/), to( #ffffff /*{a-page-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-page-background-start}*/, #ffffff /*{a-page-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-page-background-start}*/, #ffffff /*{a-page-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-page-background-start}*/, #ffffff /*{a-page-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-page-background-start}*/, #ffffff /*{a-page-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-page-background-start}*/, #ffffff /*{a-page-background-end}*/); 
	border-width: 0px /*{a-page-border-width}*/; 
	border-style: none /*{a-page-border-style}*/; 
	border-color: #000000 /*{a-page-border-color}*/;
	-webkit-border-radius: 0px /*{a-page-border-radius-top-left}*/ 0px /*{a-page-border-radius-top-right}*/ 0px /*{a-page-border-radius-bottom-right}*/ 0px /*{a-page-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-page-border-radius-top-left}*/ 0px /*{a-page-border-radius-top-right}*/ 0px /*{a-page-border-radius-bottom-right}*/ 0px /*{a-page-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-page-border-radius-top-left}*/ 0px /*{a-page-border-radius-top-right}*/ 0px /*{a-page-border-radius-bottom-right}*/ 0px /*{a-page-border-radius-bottom-left}*/;
}
.theme-page-heading { 
	margin: 0px /*{a-page-heading-margin-top}*/ 0px /*{a-page-heading-margin-right}*/ 0px /*{a-page-heading-margin-bottom}*/ 0px /*{a-page-heading-margin-left}*/; 
	padding: 0px /*{a-page-heading-padding-top}*/ 0px /*{a-page-heading-padding-right}*/ 0px /*{a-page-heading-padding-bottom}*/ 0px /*{a-page-heading-padding-left}*/; 
	background: #ffffff /*{a-page-heading-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-page-heading-background-start}*/), to( #ffffff /*{a-page-heading-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-page-heading-background-start}*/, #ffffff /*{a-page-heading-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-page-heading-background-start}*/, #ffffff /*{a-page-heading-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-page-heading-background-start}*/, #ffffff /*{a-page-heading-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-page-heading-background-start}*/, #ffffff /*{a-page-heading-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-page-heading-background-start}*/, #ffffff /*{a-page-heading-background-end}*/); 
	border-width: 0px /*{a-page-heading-border-width}*/; 
	border-style: none /*{a-page-heading-border-style}*/; 
	border-color: #000000 /*{a-page-heading-border-color}*/;
	-webkit-border-radius: 0px /*{a-page-heading-border-radius-top-left}*/ 0px /*{a-page-heading-border-radius-top-right}*/ 0px /*{a-page-heading-border-radius-bottom-right}*/ 0px /*{a-page-heading-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-page-heading-border-radius-top-left}*/ 0px /*{a-page-heading-border-radius-top-right}*/ 0px /*{a-page-heading-border-radius-bottom-right}*/ 0px /*{a-page-heading-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-page-heading-border-radius-top-left}*/ 0px /*{a-page-heading-border-radius-top-right}*/ 0px /*{a-page-heading-border-radius-bottom-right}*/ 0px /*{a-page-heading-border-radius-bottom-left}*/;
	font-family: arial,sans-serif /*{a-page-heading-font-family}*/; 
	color: #333333 /*{a-page-heading-color}*/; 
	font-size: 11px /*{a-page-heading-font-size}*/; 
	font-weight: bold /*{a-page-heading-font-weight}*/;
	line-height: 16px /*{a-page-heading-line-height}*/;
	text-shadow: 0px /*{a-page-heading-text-shadow-x}*/ 0px /*{a-page-heading-text-shadow-y}*/ 0px /*{a-page-heading-text-shadow-radius}*/ #000000 /*{a-page-heading-text-shadow-color}*/;
}
.theme-page-description { 
	margin: 0px /*{a-page-description-margin-top}*/ 0px /*{a-page-description-margin-right}*/ 0px /*{a-page-description-margin-bottom}*/ 0px /*{a-page-description-margin-left}*/; 
	padding: 0px /*{a-page-description-padding-top}*/ 0px /*{a-page-description-padding-right}*/ 24px /*{a-page-description-padding-bottom}*/ 0px /*{a-page-description-padding-left}*/; 
	background: #ffffff /*{a-page-description-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-page-description-background-start}*/), to( #ffffff /*{a-page-description-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-page-description-background-start}*/, #ffffff /*{a-page-description-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-page-description-background-start}*/, #ffffff /*{a-page-description-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-page-description-background-start}*/, #ffffff /*{a-page-description-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-page-description-background-start}*/, #ffffff /*{a-page-description-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-page-description-background-start}*/, #ffffff /*{a-page-description-background-end}*/); 
	border-width: 0px /*{a-page-description-border-width}*/; 
	border-style: none /*{a-page-description-border-style}*/; 
	border-color: #000000 /*{a-page-description-border-color}*/;
	-webkit-border-radius: 0px /*{a-page-description-border-radius-top-left}*/ 0px /*{a-page-description-border-radius-top-right}*/ 0px /*{a-page-description-border-radius-bottom-right}*/ 0px /*{a-page-description-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-page-description-border-radius-top-left}*/ 0px /*{a-page-description-border-radius-top-right}*/ 0px /*{a-page-description-border-radius-bottom-right}*/ 0px /*{a-page-description-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-page-description-border-radius-top-left}*/ 0px /*{a-page-description-border-radius-top-right}*/ 0px /*{a-page-description-border-radius-bottom-right}*/ 0px /*{a-page-description-border-radius-bottom-left}*/;
	font-family: arial,sans-serif /*{a-page-description-font-family}*/; 
	color: #606060 /*{a-page-description-color}*/; 
	font-size: 11px /*{a-page-description-font-size}*/; 
	font-weight: normal /*{a-page-description-font-weight}*/;
	line-height: 16px /*{a-page-description-line-height}*/;
	text-shadow: 0px /*{a-page-description-text-shadow-x}*/ 0px /*{a-page-description-text-shadow-y}*/ 0px /*{a-page-description-text-shadow-radius}*/ #000000 /*{a-page-description-text-shadow-color}*/;
}
.theme-page-actions { 
	margin: 0px /*{a-page-actions-margin-top}*/ 0px /*{a-page-actions-margin-right}*/ 0px /*{a-page-actions-margin-bottom}*/ 0px /*{a-page-actions-margin-left}*/; 
	padding: 0px /*{a-page-actions-padding-top}*/ 0px /*{a-page-actions-padding-right}*/ 0px /*{a-page-actions-padding-bottom}*/ 0px /*{a-page-actions-padding-left}*/;
	overflow: auto; 
}

.theme-page-actions .page-actions {float: left}
.theme-page-actions .page-progress {float: right;text-align : right;}
.oposite-direction .theme-page-actions .page-actions {float: right}
.oposite-direction .theme-page-actions .page-progress {float: left;text-align : left;} 
.theme-progress-bar {
	width: 100px;
	background: #ffffff /*{a-progress-bar-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-progress-bar-background-start}*/), to( #ffffff /*{a-progress-bar-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-progress-bar-background-start}*/, #ffffff /*{a-progress-bar-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-progress-bar-background-start}*/, #ffffff /*{a-progress-bar-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-progress-bar-background-start}*/, #ffffff /*{a-progress-bar-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-progress-bar-background-start}*/, #ffffff /*{a-progress-bar-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-progress-bar-background-start}*/, #ffffff /*{a-progress-bar-background-end}*/); 
	border-width: 1px /*{a-progress-bar-border-width}*/; 
	border-style: solid /*{a-progress-bar-border-style}*/; 
	border-color: #999999 /*{a-progress-bar-border-color}*/;
	-webkit-border-radius: 0px /*{a-progress-bar-border-radius-top-left}*/ 0px /*{a-progress-bar-border-radius-top-right}*/ 0px /*{a-progress-bar-border-radius-bottom-right}*/ 0px /*{a-progress-bar-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-progress-bar-border-radius-top-left}*/ 0px /*{a-progress-bar-border-radius-top-right}*/ 0px /*{a-progress-bar-border-radius-bottom-right}*/ 0px /*{a-progress-bar-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-progress-bar-border-radius-top-left}*/ 0px /*{a-progress-bar-border-radius-top-right}*/ 0px /*{a-progress-bar-border-radius-bottom-right}*/ 0px /*{a-progress-bar-border-radius-bottom-left}*/;
}
.theme-progress-bar .outer-bar {
	height: 16px;
	position: relative;
	margin: 0 1px;
}
.theme-progress-bar .bar {
	bottom: 1px; 
	position: absolute; 
	top: 1px;
}
.theme-progress-bar .bar-inner {
	background: #3b5998 /*{a-progress-indicator-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #3b5998 /*{a-progress-indicator-background-start}*/), to( #3b5998 /*{a-progress-indicator-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #3b5998 /*{a-progress-indicator-background-start}*/, #3b5998 /*{a-progress-indicator-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #3b5998 /*{a-progress-indicator-background-start}*/, #3b5998 /*{a-progress-indicator-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #3b5998 /*{a-progress-indicator-background-start}*/, #3b5998 /*{a-progress-indicator-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #3b5998 /*{a-progress-indicator-background-start}*/, #3b5998 /*{a-progress-indicator-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #3b5998 /*{a-progress-indicator-background-start}*/, #3b5998 /*{a-progress-indicator-background-end}*/);
	-webkit-border-radius: 0px /*{a-progress-indicator-border-radius-top-left}*/ 0px /*{a-progress-indicator-border-radius-top-right}*/ 0px /*{a-progress-indicator-border-radius-bottom-right}*/ 0px /*{a-progress-indicator-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-progress-indicator-border-radius-top-left}*/ 0px /*{a-progress-indicator-border-radius-top-right}*/ 0px /*{a-progress-indicator-border-radius-bottom-right}*/ 0px /*{a-progress-indicator-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-progress-indicator-border-radius-top-left}*/ 0px /*{a-progress-indicator-border-radius-top-right}*/ 0px /*{a-progress-indicator-border-radius-bottom-right}*/ 0px /*{a-progress-indicator-border-radius-bottom-left}*/;
	height: 100%;
	width: 100%;
}
.bar-label { padding-top: 5px; }

.theme-page-actions .container-previous,
.theme-page-actions .container-next,
.theme-page-actions .container-finish,
.theme-page-actions .container-paging {
	display: table-cell;
}
.theme-page-actions .container-previous { padding-right: 5px; }
.theme-body.oposite-direction .theme-page-actions .container-previous { padding-right: 0; padding-left: 5px; }
.theme-page-actions .container-paging { padding-left: 5px; }
.theme-body.oposite-direction .container-paging { padding-left: 0; padding-right: 5px; }
.theme-question { 
	margin: 0px /*{a-question-margin-top}*/ 0px /*{a-question-margin-right}*/ 0px /*{a-question-margin-bottom}*/ 0px /*{a-question-margin-left}*/ !important; 
	padding: 0px /*{a-question-padding-top}*/ 0px /*{a-question-padding-right}*/ 24px /*{a-question-padding-bottom}*/ 0px /*{a-question-padding-left}*/ !important; 
	background: #ffffff /*{a-question-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-question-background-start}*/), to( #ffffff /*{a-question-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-question-background-start}*/, #ffffff /*{a-question-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-question-background-start}*/, #ffffff /*{a-question-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-question-background-start}*/, #ffffff /*{a-question-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-question-background-start}*/, #ffffff /*{a-question-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-question-background-start}*/, #ffffff /*{a-question-background-end}*/); 
	border-width: 0px /*{a-question-border-width}*/; 
	border-style: none /*{a-question-border-style}*/; 
	border-color: #000000 /*{a-question-border-color}*/;
	-webkit-border-radius: 0px /*{a-question-border-radius-top-left}*/ 0px /*{a-question-border-radius-top-right}*/ 0px /*{a-question-border-radius-bottom-right}*/ 0px /*{a-question-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-question-border-radius-top-left}*/ 0px /*{a-question-border-radius-top-right}*/ 0px /*{a-question-border-radius-bottom-right}*/ 0px /*{a-question-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-question-border-radius-top-left}*/ 0px /*{a-question-border-radius-top-right}*/ 0px /*{a-question-border-radius-bottom-right}*/ 0px /*{a-question-border-radius-bottom-left}*/;
	clear: both;
}
.theme-question:before,
.theme-question:after { content: ""; display: table; }
.theme-question:after { clear: both; }
.theme-question ul.list-date { float: left; }
.theme-question ul.list-date li { float: left; padding-right: 5px; }
.theme-body.oposite-direction .theme-question ul.list-date { float: right; }
.theme-body.oposite-direction .theme-question ul.list-date li { float: right; padding-left: 5px; padding-right:0;}
.theme-question ul.list-time { direction : ltr; float: left; padding-left: 5px; }
.theme-question ul.list-time li { float: left; padding-right: 5px; }
.theme-body.oposite-direction .theme-question ul.list-time { float: right; text-align: right; }
.theme-body.oposite-direction .theme-question ul.list-time select { direction: rtl; }
.theme-question input, 
.theme-question select, 
.theme-question textarea, 
.theme-question option {
    outline-style: none;
}
.theme-question input[type="email"], 
.theme-question input[type="text"], 
.theme-question input[type="password"], 
.theme-question textarea {
	border-color: #666666 #cccccc #cccccc;
    border-style: solid;
    border-width: 1px;
    margin: 0;
    outline-style: none;
    padding: 2px;
}
.theme-question input[type="email"], 
.theme-question input[type="text"], 
.theme-question input[type="password"], 
.theme-question textarea,
.theme-question select {
	font-family: arial,sans-serif /*{a-body-font-family}*/;
	font-size: 11px /*{a-body-font-size}*/; 
}
.theme-question textarea {
	height: 64px;
    width: 314px;
}
.theme-question label {
    color: #333333 /*{a-question-color}*/;
}
.theme-question label span {
	Xdisplay: table-cell;
	width: 20px;
}
.theme-question label input[type="radio"],
.theme-question label input[type="checkbox"] {
	margin:0;
    padding: 0;
    vertical-align: text-top;
}
.theme-question .choice .choice-image {}
.theme-question .choice .choice-text {}
.theme-question .choice .choice-text span {
	display: table-cell;
}
.theme-question .choice .choice-text strong {
	display: table-cell;
}
.theme-question .choice-other label span { 
	display: table-cell;
}
.theme-question .choice-other label strong { 
	display: table-cell; 
}
.theme-question .choice, 
.theme-question .choice-other {
	padding: 3px 0;
}
.theme-question .choice strong,
.theme-question .choice-other strong { 
	font-weight: normal; 
}
.theme-question .choice label,
.theme-question .choice-other label {
	display: table;
	cursor: pointer;
}
.theme-question .container-other, 
.theme-question .container-additional-details-or-comments {
	padding: 5px 0 0 20px;
}
.theme-body.oposite-direction .theme-question .container-other,
.theme-body.oposite-direction .theme-question .container-additional-details-or-comments {
	padding: 5px 20px 0 0px;
}
.theme-body.oposite-direction .theme-question label input[type="radio"],
.theme-body.oposite-direction .theme-question label input[type="checkbox"] {
	Xmargin: 0px 0px 0px 6px;
}
.theme-question table.table-matrix {
	border-collapse:separate;
	border-spacing:1px;
	color: #333333 /*{a-question-color}*/;
}
.theme-question table.table-matrix th {
	font-weight:normal;
	padding: 5px 5px 5px 5px;
	text-align:left;
}
.theme-body.oposite-direction .theme-question table.table-matrix th {
	text-align:right;
}
.theme-question table.table-matrix td {
	text-align:center;
	padding: 5px 5px 5px 5px;
}
.theme-question table.table-matrix tr.active, 
.theme-question table.table-matrix tr.table-matrix-header th {
	background: #efefef /*{a-question-alternative-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #efefef /*{a-question-alternative-background-start}*/), to( #efefef /*{a-question-alternative-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #efefef /*{a-question-alternative-background-start}*/, #efefef /*{a-question-alternative-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #efefef /*{a-question-alternative-background-start}*/, #efefef /*{a-question-alternative-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #efefef /*{a-question-alternative-background-start}*/, #efefef /*{a-question-alternative-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #efefef /*{a-question-alternative-background-start}*/, #efefef /*{a-question-alternative-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #efefef /*{a-question-alternative-background-start}*/, #efefef /*{a-question-alternative-background-end}*/);
	color: #333333 /*{a-question-alternative-color}*/;
}
.theme-question table.table-matrix tr.table-matrix-header th {
	text-align: center;
}
.theme-question table.table-scale {
	border-collapse: separate;
	border-spacing: 1px;
	color: #333333 /*{a-question-color}*/;
}
.theme-body.oposite-direction .theme-question table.table-scale {
	direction: ltr;
}
.theme-question table.table-scale th {
	font-weight: normal;
	padding: 5px;
	Xtext-align: left;
}
.theme-body.oposite-direction .theme-question table.table-scale th { 
	Xtext-align: right;
}
.theme-question table.table-scale td {
	text-align: center;
	padding: 5px;
}
.theme-question table.table-scale td label {
	margin: 0px;
}
.theme-question table.table-scale td label input {
	margin: 0px;
}
.theme-question table.table-scale tr.table-scale-header th {
	text-align: center;
	vertical-align: bottom;
}
.theme-question table.table-scale tr.table-scale-row td {
	vertical-align: top;
}
.theme-question table.table-scale tr.table-scale-row th {
	vertical-align: top;
}
.theme-question .q-label {
	padding: 0 0 5px 0;
}
.theme-question .q-help {
	padding: 0 0 5px 0;
}
.theme-question .q-image {
	padding: 0 0 5px 0;
}
.theme-question .q-comment {
	padding: 0 0 5px 0;
}
.theme-question .q-comment-container {
	padding: 5px 0 0 0;
}
.theme-question-heading { 
	margin: 0px /*{a-question-heading-margin-top}*/ 0px /*{a-question-heading-margin-right}*/ 0px /*{a-question-heading-margin-bottom}*/ 0px /*{a-question-heading-margin-left}*/; 
	padding: 0px /*{a-question-heading-padding-top}*/ 0px /*{a-question-heading-padding-right}*/ 0px /*{a-question-heading-padding-bottom}*/ 0px /*{a-question-heading-padding-left}*/; 
	background: #ffffff /*{a-question-heading-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-question-heading-background-start}*/), to( #ffffff /*{a-question-heading-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-question-heading-background-start}*/, #ffffff /*{a-question-heading-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-question-heading-background-start}*/, #ffffff /*{a-question-heading-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-question-heading-background-start}*/, #ffffff /*{a-question-heading-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-question-heading-background-start}*/, #ffffff /*{a-question-heading-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-question-heading-background-start}*/, #ffffff /*{a-question-heading-background-end}*/); 
	border-width: 0px /*{a-question-heading-border-width}*/; 
	border-style: none /*{a-question-heading-border-style}*/; 
	border-color: #000000 /*{a-question-heading-border-color}*/;
	-webkit-border-radius: 0px /*{a-question-heading-border-radius-top-left}*/ 0px /*{a-question-heading-border-radius-top-right}*/ 0px /*{a-question-heading-border-radius-bottom-right}*/ 0px /*{a-question-heading-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-question-heading-border-radius-top-left}*/ 0px /*{a-question-heading-border-radius-top-right}*/ 0px /*{a-question-heading-border-radius-bottom-right}*/ 0px /*{a-question-heading-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-question-heading-border-radius-top-left}*/ 0px /*{a-question-heading-border-radius-top-right}*/ 0px /*{a-question-heading-border-radius-bottom-right}*/ 0px /*{a-question-heading-border-radius-bottom-left}*/;
	font-family: arial,sans-serif /*{a-question-heading-font-family}*/; 
	color: #333333 /*{a-question-heading-color}*/; 
	font-size: 11px /*{a-question-heading-font-size}*/; 
	font-weight: bold /*{a-question-heading-font-weight}*/;
	line-height: 16px /*{a-question-heading-line-height}*/;
	text-shadow: 0px /*{a-question-heading-text-shadow-x}*/ 0px /*{a-question-heading-text-shadow-y}*/ 0px /*{a-question-heading-text-shadow-radius}*/ #000000 /*{a-question-heading-text-shadow-color}*/;
}
.theme-question-content { 
	margin: 10px /*{a-question-content-margin-top}*/ 0px /*{a-question-content-margin-right}*/ 0px /*{a-question-content-margin-bottom}*/ 0px /*{a-question-content-margin-left}*/; 
	padding: 0px /*{a-question-content-padding-top}*/ 0px /*{a-question-content-padding-right}*/ 0px /*{a-question-content-padding-bottom}*/ 0px /*{a-question-content-padding-left}*/; 
	background: #ffffff /*{a-question-content-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-question-content-background-start}*/), to( #ffffff /*{a-question-content-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-question-content-background-start}*/, #ffffff /*{a-question-content-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-question-content-background-start}*/, #ffffff /*{a-question-content-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-question-content-background-start}*/, #ffffff /*{a-question-content-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-question-content-background-start}*/, #ffffff /*{a-question-content-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-question-content-background-start}*/, #ffffff /*{a-question-content-background-end}*/); 
	border-width: 0px /*{a-question-content-border-width}*/; 
	border-style: none /*{a-question-content-border-style}*/; 
	border-color: #000000 /*{a-question-content-border-color}*/;
	-webkit-border-radius: 0px /*{a-question-content-border-radius-top-left}*/ 0px /*{a-question-content-border-radius-top-right}*/ 0px /*{a-question-content-border-radius-bottom-right}*/ 0px /*{a-question-content-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-question-content-border-radius-top-left}*/ 0px /*{a-question-content-border-radius-top-right}*/ 0px /*{a-question-content-border-radius-bottom-right}*/ 0px /*{a-question-heading-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-question-content-border-radius-top-left}*/ 0px /*{a-question-content-border-radius-top-right}*/ 0px /*{a-question-content-border-radius-bottom-right}*/ 0px /*{a-question-content-border-radius-bottom-left}*/;
}
.theme-question-content:before,
.theme-question-content:after { content: ""; display: table; }
.theme-question-content:after { clear: both; }
.label-placement-1 .theme-question-heading,
.label-placement-1 .theme-question-content {
	Xfloat: left;
	display: table-cell;
	vertical-align: top;
}
.label-placement-1 .theme-question-heading {
	width: 120px;
	padding-right: 10px;
	text-align: left;
}
.oposite-direction .label-placement-1 .theme-question-heading {
	width: 120px;
	padding-left: 10px;
	padding-right: 0px;
	text-align: right;
}
.label-placement-1 .theme-question-content {
	margin-top: 0px;
}
.label-placement-1 .theme-page-actions {
	margin-left: 130px;
}
.oposite-direction .label-placement-1 .theme-page-actions {
	margin-right: 130px;
	margin-left: 0px;
}
.label-placement-2 .theme-question-heading,
.label-placement-2 .theme-question-content {
	Xfloat: left;
	display: table-cell;
	vertical-align: top;
}
.label-placement-2 .theme-question-heading {
	width: 120px;
	padding-right: 10px;
	text-align: right;
}
.oposite-direction .label-placement-2 .theme-question-heading {
	width: 120px;
	padding-left: 10px;
	padding-right: 0px;
	text-align: left;
}
.label-placement-2 .theme-question-content {
	margin-top: 0px;
}
.label-placement-2 .theme-page-actions {
	margin-left: 130px;
}
.oposite-direction .label-placement-2 .theme-page-actions {
	margin-right: 130px;
	margin-left: 0px;
}
.theme-footer { 
	min-height: 80px /*{a-footer-min-height}*/; 
	margin: 0px /*{a-footer-margin-top}*/ 0px /*{a-footer-margin-right}*/ 0px /*{a-footer-margin-bottom}*/ 0px /*{a-footer-margin-left}*/; 
	padding: 0px /*{a-footer-padding-top}*/ 0px /*{a-footer-padding-right}*/ 0px /*{a-footer-padding-bottom}*/ 0px /*{a-footer-padding-left}*/; 
	background: #ffffff /*{a-footer-background-color}*/; 
	background-image: -webkit-gradient(linear, left top, left bottom, from( #ffffff /*{a-footer-background-start}*/), to( #ffffff /*{a-footer-background-end}*/)); /* Saf4+, Chrome */ 
	background-image: -webkit-linear-gradient( #ffffff /*{a-footer-background-start}*/, #ffffff /*{a-footer-background-end}*/); /* Chrome 10+, Saf5.1+ */ 
	background-image:    -moz-linear-gradient( #ffffff /*{a-footer-background-start}*/, #ffffff /*{a-footer-background-end}*/); /* FF3.6 */ 
	background-image:     -ms-linear-gradient( #ffffff /*{a-footer-background-start}*/, #ffffff /*{a-footer-background-end}*/); /* IE10 */ 
	background-image:      -o-linear-gradient( #ffffff /*{a-footer-background-start}*/, #ffffff /*{a-footer-background-end}*/); /* Opera 11.10+ */ 
	background-image:         linear-gradient( #ffffff /*{a-footer-background-start}*/, #ffffff /*{a-footer-background-end}*/); 
	border-width: 0px /*{a-footer-border-width}*/; 
	border-style: none /*{a-footer-border-style}*/; 
	border-color: #000000 /*{a-footer-border-color}*/;
	-webkit-border-radius: 0px /*{a-footer-border-radius-top-left}*/ 0px /*{a-footer-border-radius-top-right}*/ 0px /*{a-footer-border-radius-bottom-right}*/ 0px /*{a-footer-border-radius-bottom-left}*/;
	-moz-border-radius: 0px /*{a-footer-border-radius-top-left}*/ 0px /*{a-footer-border-radius-top-right}*/ 0px /*{a-footer-border-radius-bottom-right}*/ 0px /*{a-footer-border-radius-bottom-left}*/;
	border-radius: 0px /*{a-footer-border-radius-top-left}*/ 0px /*{a-footer-border-radius-top-right}*/ 0px /*{a-footer-border-radius-bottom-right}*/ 0px /*{a-footer-border-radius-bottom-left}*/;
	-moz-box-shadow: 0 1px 0px /*{a-footer-box-shadow-size}*/ rgba(0,0,0,0) /*{a-footer-box-shadow-color}*/;
	-webkit-box-shadow: 0 1px 0px /*{a-footer-box-shadow-size}*/ rgba(0,0,0,0) /*{a-footer-box-shadow-color}*/;
	box-shadow: 0 1px 0px /*{a-footer-box-shadow-size}*/ rgba(0,0,0,0) /*{a-footer-box-shadow-color}*/;
}
		</div>
		
		
	</div>
</div>


<script type="text/javascript" src="<%=applicationURL%>/scripts/jqueryui/1.9.1/jquery-ui.min.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_details.js"></script>

<script type="text/javascript" src="<%=applicationURL%>/scripts/color/color.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/minicolors/minicolors.js"></script>
<script type="text/javascript" src="<%=applicationURL%>/scripts/components/theme_editor.js"></script>


<script type="text/javascript">

var pWin;
function preview(element) {
	var name = "pWin"; 
	if (element) 
		element.target = name;
	
	if (pWin) 
		pWin.close();
	
	pWin = window.open('', name, 'resizable=1,scrollbars=1,status=1,menubar=1');
	
};


var guid = null;
var _themeId = null;
var selectedThemeId = null;
var selectedThemeName = null;
var selectedIsTemplate = false;
var selectedTheme = null;
var lastTheme = null;
var previewUrl = null;
var previewSecureUrl = null;

var themes = [];

var loadTheme = function(id, select) {
	
	for(var x = 0; x < themes.length; x++) {
		if(themes[x].themeId == id) {

			selectedTheme = x;
			selectedThemeId = id;
			
			selectedThemeName = themes[x].name;
			selectedIsTemplate = themes[x].isTemplate;
			
			if(selectedIsTemplate) {
				$("#button_save").hide();
			} else {
				$("#button_save").show();
			}
			
			
			// TODO:
			//selectedThemeCss = themes[x].css;
			
			//#style is where the initial CSS file is put
		    //copy it to #styleblock so its in the scope of the iframe
		    // TR.styleBlock = TR.iframe.find( "#styleblock" );
		    // TR.styleBlock.text( $("#style").text() );
		    
		    if(select) {
			    // change select_based_on_theme
			    $("#select_based_on_theme").val( id );
		    }
		    
			// for preview link
			$('#button_preview_theme').attr({"href" : previewUrl + "/" + id });
		    
			getTheme({
				accountId : accountId,
		    	themeId : selectedThemeId,
		    	success : function(data) {
		    		
		    		// TODO:
				    // theme editor proccess set
				    styleBlock.text(data.styleBlock);
				    editor.initStyleArray();
				    editor.updateFormValues();
		    		
		    	},
		    	error: function(error) {
		    		console.log(JSON.stringify(error));
		    	}
		    });
		    
		}
	}
	
	
};

var isFirstTime = false;
var lastBasedTheme = null;
var fillThemes = function() {
	
	// empty
	$("#themes").empty();
	
	
	// set previous selected based theme
	//if($("#select_based_on_theme").val() != null) {
	//	lastBasedTheme = $("#select_based_on_theme").val();
	//}
	// empty
	$("#select_based_on_theme").empty();
	
	// fill
	for(var i = 0; i < themes.length; i++) {
		
		var h = $("<li class=\"item\" theme=\"" + themes[i].themeId + "\">" +
			"<label><input type=\"radio\" name=\"theme\" id=\"radio_theme_" + themes[i].themeId + "\" />" + themes[i].name +  "</label>" +
			(themes[i].isTemplate == true ? "" : "<a href=\"#\" title=\"Delete this theme\" class=\"delete-theme\" data-value=\"" + themes[i].themeId + "\">Delete</a>") +
		"</li>")
		.hover(function () {
			$(this).addClass('hover');
		}, function () {
			$(this).removeClass('hover');
		});
		
		h.click(function(event) {
			
			var target = $( event.target );
			if(target.is("a")) {
				
				event.preventDefault();
				
				var deleteThemeId = target.attr("data-value");
				
				var modal = new lightFace({
					title : "Deleting theme",
					message : "Are you sure you want to delete this theme?",
					actions : [
				        { 
				        	label : "Delete", 
				        	fire : function() {
				        		
				        		deleteTheme({
				        			accountId : accountId,
				        			themeId : deleteThemeId,
				        			success : function(data) {
				        				
							    		getThemes({
							    			accountId : accountId,
							    			opinionId : <%=opinionId%>,
							    			opinionTypeId : 1,
							    			success : function(data) {
							    				
							    				themes = data.themes;
							    				
							    				getDetails({
							    					accountId : accountId,
							    					opinionId : <%=opinionId%>,
							    					success : function(_data) {
							    						
							    						selectedTheme = null;
								    					selectedThemeId = _data.themeId;
								    					
								    					
								    					fillThemes();
								    					
							    					}
							    				});
							    				
							    			},
							    			error: function(error) {
							    				themes = [];
							    			}
							    		});
				        				
				        			},
				        			error: function(error) {
				        				console.log(error);
				        			}
				        		});
				        		
				        		modal.close();
				        	}, 
				        	color : "blue" 
				        },
				        { 
				        	label : "Cancel", 
				        	fire : function() {
				        		
				        		modal.close(); 
				        	}, 
				        	color : "white" 
				        }
					],
					overlayAll : true
				});
				
			} else {
			
				if(lastTheme != null && lastTheme != $(this)) {
					lastTheme.removeClass('selected');
				}
				lastTheme = $(this);
				$(this).addClass('selected').find('input:radio').prop('checked', true);
	
				loadTheme(parseInt($(this).attr('theme')), true);
			
			}

		});
		
		
		h.appendTo('#themes');
		
		
		
		
		
		// fill  -> select_based_on_theme
		var basedOnTheme = $("#select_based_on_theme")[0].options;
		var opt = new Option(themes[i].name, themes[i].themeId);
        try {
        	basedOnTheme.add(opt);
        } catch (ex) {
        	basedOnTheme.add(opt, null);
        }
        
        // set default theme
		if(selectedTheme == null) {
			if(h.attr('theme') == selectedThemeId) {
				lastTheme = h;
				
				h.addClass('selected').find('input:radio').prop('checked', true);
				
				loadTheme(h.attr('theme'), true);
			}
		}
		
	}
    
    // fix for last selected based theme ->
    //if(lastBasedTheme != null) {
    //	$("#select_based_on_theme").val(lastBasedTheme);
    //}
	
	if(!isFirstTime) {
    
		// events
		$("#select_based_on_theme").change(function() {
			loadTheme($(this).val());
		});
		
		// apply theme
		$('#button_apply_theme').click(function() {
			
			setTheme({
				accountId : accountId,
				themeId : selectedThemeId,
				opinionId : <%=opinionId%>,
				opinionTypeId : 2,
				success : function() {
					
					var modal = new lightFace({
						title : "Changes saved.",
						message : "Your changes were successfully saved.",
						actions : [
							{ 
								label : "OK", 
								fire : function() { 
									modal.close(); 
								}, 
								color: "blue"
							}
						],
						overlayAll : true
					});
					
				},
				error: function() {
					//
				}
			});
			
		});
	
	
		isFirstTime = true;
	
	}
	

};


/*
function objectToString(o){
    var parse = function(_o){
        var a = [], t;
        for(var p in _o){
            if(_o.hasOwnProperty(p)){
                t = _o[p];
                if(t && typeof t == "object"){
                    a[a.length]= p + ":{ " + arguments.callee(t).join(", ") + "}";
                }
                else {
                    if(typeof t == "string"){
                        a[a.length] = [ p+ ": \"" + t.toString() + "\"" ];
                    }
                    else{
                        a[a.length] = [ p+ ": " + t.toString()];
                    }
                }
            }
        }
        
        return a;
    };
    return "{" + parse(o).join(", ") + "}";
};
*/




var getThemes = function(params) {
	
	var obj = {
		themes : {
			getThemes : {
				accountId : params.accountId,
				opinionId : params.opinionId,
				opinionTypeId : params.opinionTypeId
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
        	if(data.themes.getThemes != undefined) {
	        	if(data.themes.getThemes.error != undefined) {
					
	        		errorHandler({
						error : data.themes.getThemes.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.themes.getThemes);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.themes.getThemes);
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

var deleteTheme = function(params) {
	
	var obj = {
		themes : {
			deleteTheme : {
				accountId : params.accountId,
				themeId : params.themeId
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
        	if(data.themes.deleteTheme != undefined) {
	        	if(data.themes.deleteTheme.error != undefined) {
					
	        		errorHandler({
						error : data.themes.deleteTheme.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.themes.deleteTheme);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.themes.deleteTheme);
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

var getTheme = function(params) {
	
	var obj = {
		themes : {
			getTheme : {
				accountId : params.accountId,
				themeId : params.themeId
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
        	if(data.themes.getTheme != undefined) {
	        	if(data.themes.getTheme.error != undefined) {
					
	        		errorHandler({
						error : data.themes.getTheme.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.themes.getTheme);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.themes.getTheme);
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

var setTheme = function(params) {
	
	var obj = {
		opinions : {
			setTheme : {
				accountId : params.accountId,
				themeId : params.themeId,
				opinionId : params.opinionId,
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
        	if(data.opinions.setTheme != undefined) {
	        	if(data.opinions.setTheme.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.setTheme.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.setTheme);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.setTheme);
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

var updateTheme = function(params) {
	
	var obj = {
		themes : {
			updateTheme : {
				accountId : params.accountId,
				themeId : params.themeId,
				name : params.name,
				opinionId : params.opinionId,
				opinionTypeId : params.opinionTypeId,
				styleBlock : params.styleBlock
			}
		}
	};

	$.ajax({
		type : "POST",
        /*url: servletUrl,*/
        url : apiUrl,
        dataType: "json",
        xhrFields : {
			 withCredentials: true
		},
        data: { 
        	rq : JSON.stringify(obj),
        	timestamp : $.getTimestamp()  
        },
        success: function (data, status) {
        	if(data.themes.updateTheme != undefined) {
	        	if(data.themes.updateTheme.error != undefined) {
					
	        		errorHandler({
						error : data.themes.updateTheme.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.themes.updateTheme);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.themes.updateTheme);
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



















var editor = null;
function iframeLoadCallback() {
	isIFrameReady = true;
};

var getDetails = function(params) {
	
	var obj = {
		opinions : {
			getDetails : {
				accountId : params.accountId,
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
        	if(data.opinions.getDetails != undefined) {
	        	if(data.opinions.getDetails.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.getDetails.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinions.getDetails);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.getDetails);
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

var copy = function(params) {
	
	var obj = {
		opinions : {
			copy : {
				accountId : params.accountId,
				name : params.name,
				title : params.title,
				opinionType : 1,
				folderId : null,
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
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var rename = function(params) {
	
	var obj = {
		opinions : {
			rename : {
				accountId : params.accountId,
				name : params.name,
				title : params.title,
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
        	if(data.opinions.rename.error != undefined) {
        		
        		errorHandler({
					error : data.opinions.rename.error	
				});
        		
        		if(params.error != undefined 
						&& typeof params.error == 'function') {
					params.error(data.opinions.rename);
				}
			} else {
				if(params.success != undefined 
						&& typeof params.success == 'function') {
					params.success(data.opinions.rename);
				}
			}
        },
        error: function (XHR, textStatus, errorThrow) {
            // error
        }
    });
	
};

var deleteOpinions = function(params) {
	
	var obj = {
		opinions : {
			deleteOpinions : {
				accountId : params.accountId,
				list : params.list,
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
        	if(data.opinions.deleteOpinions != undefined) {
	        	if(data.opinions.deleteOpinions.error != undefined) {
					
	        		errorHandler({
						error : data.opinions.deleteOpinions.error	
					});
	        		
	        		if(params.error != undefined 
							&& typeof params.error == 'function') {
						params.error(data.opinion.deleteOpinions);
					}
				} else {
					if(params.success != undefined 
							&& typeof params.success == 'function') {
						params.success(data.opinions.deleteOpinions);
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

var exportTo = function(params) {
	
	var data = {
		accountId : params.accountId,
	    format: params.format,
	    opinionId : params.opinionId,
	    name: params.name,
	    exportType : params.exportType
	};
	
	window.location = "<%=applicationURL%>/servlet/export?rq=" + JSON.stringify(data) + "&accountId=" + params.accountId;
	
};


var opinionId = <%=opinionId%>;
var opinionTypeId = 1; // survey

//inspector fix for cross-domain
if (!window.location.href.match(/localhost/gi)) {
	document.domain = getDomain();
}

function getDomain() { 
	
	var domain = "";
	var hostname = window.location.hostname;
    var arrHostname = hostname.split('.');
    if(arrHostname.length == 4 && /^\d+$/.test(hostname.replace(/\./g, ''))) {
    	domain = hostname;
    } else {
    	if(arrHostname.length == 1) {
    		domain = hostname;
    	} else {
	        var currentdomain = [arrHostname[arrHostname.length - 2], arrHostname[arrHostname.length - 1]].join('.');
	        domain = currentdomain;
    	}
    }
    return domain;
    
}

function reloadThemes(reset) {
	
	getThemes({
		accountId : accountId,
		opinionId : <%=opinionId%>,
		opinionTypeId : 1,
		success : function(data) {
			
			themes = data.themes;
			
			if(reset != undefined && reset == true) {
				
				getDetails({
					accountId : accountId,
					opinionId : <%=opinionId%>,
					success : function(_data) {
						
						selectedTheme = null;
    					selectedThemeId = _data.themeId;
    					
    					fillThemes();
    					
					}
				});
				
			} else {
				
				fillThemes();
				
			}
			
			
		},
		error: function(error) {
			themes = [];
		}
	});
	
};

//var accountId = $.cookie("aid"); // auth.userInfo.accountId;

$(function() {
	
	new surveyDetails({ 
		opinionId : <%=opinionId%>,
		absoluteUrl : "<%=absoluteURL %>",
		applicationUrl : "<%=applicationURL%>",
		collectorUrl : "<%=Collector.getUrl()%>",
		callback : function(data) {
			
			guid = data.guid;
			selectedThemeId = data.themeId;
			_themeId = data.themeId;
			previewUrl = data.previewUrl;
			previewSecureUrl = data.previewSecureUrl;
			
		    $('#frame').load(function(){
		    	
		    	isIFrameReady = true;
		    	
	    		// declare editor
				editor = new themeEditor({
			    	complete : function() {
			    		
			    		// ready -> editor is loaded
			    		getThemes({
			    			accountId : accountId,
			    			opinionId : <%=opinionId%>,
			    			opinionTypeId : 1,
			    			success : function(data) {
			    				
			    				themes = data.themes;
			    				fillThemes();
			    				
			    			},
			    			error: function(error) {
			    				themes = [];
			    			}
			    		});
			    		
			    	}
			    });
	    		
		   	})
		   	.error(function () {
	    		// error
		    })
		    .attr("src", previewSecureUrl + "?noStyle=true");
			
		}
	});
	
});
</script>