
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="java.net.*" %>

<%

	URL applicationURL = request.getServerPort() != 80 ? new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath()) : new URL(request.getScheme(), request.getServerName(), request.getContextPath());

%>

<!DOCTYPE html>
<html>
	<head>
	  <meta charset="utf-8" />
	  
	  <style type="text/css" id="styleblock">
	  </style>
	</head>
	<body style="margin:0;">
	
		<div class="theme-body ui-body-a" data-theme="a" data-form="ui-body-a">
			<div class="theme-wrapper ui-wrapper-a" data-theme="a" data-form="ui-wrapper-a">
				<div class="theme-header ui-header-a" data-theme="a" data-form="ui-header-a">
					<div class="theme-logo ui-logo-a" data-theme="a" data-form="ui-logo-a"></div>
				</div>
				<div class="theme-content ui-content-a" data-theme="a" data-form="ui-content-a">
					<div>
						<h1 class="theme-survey-heading ui-survey-heading-a" data-theme="a" data-form="ui-survey-heading-a">Survey Heading</h1>
						<div class="theme-page ui-page-a" data-theme="a" data-form="ui-page-a">
							<h2 class="theme-page-heading ui-page-heading-a" data-theme="a" data-form="ui-page-heading-a">Page Heading</h2>
							<div>Page Description</div>
							<div>
								<ul>
									<li>
										<div class="theme-question ui-question-a" data-theme="a" data-form="ui-question-a">
											<div>
												<span class="label-control-status status">This question is required</span>
											</div>
											<div class="theme-question-heading ui-question-heading-a" data-theme="a" data-form="ui-question-heading-a">
												<span>1.&nbsp;</span><span>Text</span>
											</div>
											<div class="form-controls">
												<div class="container-control-embed"></div>
												<div class="container-control-notes label-control-note">test description</div>
												<div>
													<input type="text" />
												</div>
											</div>
										</div>
									</li>
									<li>
										<div class="theme-question ui-question-a" data-theme="a" data-form="ui-question-a">
											<div>
												<span class="label-control-status status"></span>
											</div>
											<div class="theme-question-heading ui-question-heading-a" data-theme="a" data-form="ui-question-heading-a">
												<span>2.&nbsp;</span><span class="label-control-mandatory asterisk">*&nbsp;</span><span>Multiple Choice</span>
											</div>
											<div class="form-controls">
												<div class="container-control-embed"></div>
												<div class="container-control-notes label-control-note">test description</div>
												<ul class="container-control-includes">
													<li class="row-choice"><label><input type="radio" autocomplete="off" value="Big Story Little Heroes" optionkindid="0" optiontext="Big Story Little Heroes" optionindex="0" name="13724091179021601" id="2985" class="radio-option" />Big Story Little Heroes</label></li>
													<li class="row-choice"><label><input type="radio" autocomplete="off" value="Dragon City" optionkindid="0" optiontext="Dragon City" optionindex="1" name="13724091179021601" id="2986" class="radio-option" />Dragon City</label></li>
													<li class="row-choice"><label><input type="radio" autocomplete="off" value="Tamer Saga" optionkindid="0" optiontext="Tamer Saga" optionindex="2" name="13724091179021601" id="2987" class="radio-option" />Tamer Saga</label></li>
													<li class="row-choice"><label><input type="radio" autocomplete="off" value="DDtank" optionkindid="0" optiontext="DDtank" optionindex="3" name="13724091179021601" id="2988" class="radio-option" />DDtank</label></li>
													<li class="row-choice"><label><input type="radio" autocomplete="off" value="Bubble Island" optionkindid="0" optiontext="Bubble Island" optionindex="4" name="13724091179021601" id="2989" class="radio-option" />Bubble Island</label></li>
													<li class="row-choice"><label><input type="radio" autocomplete="off" value="Epic Coaster" optionkindid="0" optiontext="Epic Coaster" optionindex="5" name="13724091179021601" id="2990" class="radio-option" />Epic Coaster</label></li>
												</ul>
												<div class="container-control-other">
													<div class="row-choice-other">
														<div class="cell">
															<label><input type="radio" autocomplete="off" optiontext="Other, (please specify)" optionkindid="1" optionid="2991" name="13724091179021601" class="radio-other" />Other, (please specify)</label>
														</div>
														<div class="cell">
															<input type="text" class="text-field input-control-other" />
														</div>
													</div>
												</div>
											</div>
										</div>
									</li>
									<li>
										<div class="theme-question ui-question-a" data-theme="a" data-form="ui-question-a">
											<div>
												<span class="label-control-status status"></span>
											</div>
											<div class="theme-question-heading ui-question-heading-a" data-theme="a" data-form="ui-question-heading-a">
												<span>3.&nbsp;</span><span>Matrix</span>
											</div>
											<div class="form-controls">
												<div class="container-control-embed"></div>
												<div class="container-control-notes label-control-note">test description</div>
												<div>
													
													<table cellspacing="1" cellpadding="0" border="0" class="table-matrix">
														<tbody>
															<tr class="table-matrix-header">
																<td>&nbsp;</td>
																<th>Extremely dissatisfied</th>
																<th>Moderately dissatisfied</th>
																<th>Slightly dissatisfied</th>
																<th>Neither satisfied nor dissatisfied</th>
																<th>Slightly satisfied</th>
																<th>Moderately satisfied</th>
															</tr>
															<tr>
																<th>Bookmarks management</th>
																<td><lable><input type="radio" value="Extremely dissatisfied" optiontext="Extremely dissatisfied" id="3005" subcontrolcontent="Bookmarks management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1615" name="1614_1615" controlindex="0" /></lable></td>
																<td><lable><input type="radio" value="Moderately dissatisfied" optiontext="Moderately dissatisfied" id="3006" subcontrolcontent="Bookmarks management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1615" name="1614_1615" controlindex="1" /></lable></td>
																<td><lable><input type="radio" value="Slightly dissatisfied" optiontext="Slightly dissatisfied" id="3007" subcontrolcontent="Bookmarks management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1615" name="1614_1615" controlindex="2" /></lable></td>
																<td><lable><input type="radio" value="Neither satisfied nor dissatisfied" optiontext="Neither satisfied nor dissatisfied" id="3008" subcontrolcontent="Bookmarks management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1615" name="1614_1615" controlindex="3" /></lable></td>
																<td><lable><input type="radio" value="Slightly satisfied" optiontext="Slightly satisfied" id="3009" subcontrolcontent="Bookmarks management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1615" name="1614_1615" controlindex="4" /></lable></td>
																<td><lable><input type="radio" value="Moderately satisfied" optiontext="Moderately satisfied" id="3010" subcontrolcontent="Bookmarks management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1615" name="1614_1615" controlindex="5" /></lable></td>
															</tr>
															<tr class="active">
																<th>Omnibox/Address Bar</th>
																<td><lable><input type="radio" value="Extremely dissatisfied" optiontext="Extremely dissatisfied" id="3005" subcontrolcontent="Omnibox/Address Bar" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1616" name="1614_1616" controlindex="0" /></lable></td>
																<td><lable><input type="radio" value="Moderately dissatisfied" optiontext="Moderately dissatisfied" id="3006" subcontrolcontent="Omnibox/Address Bar" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1616" name="1614_1616" controlindex="1" /></lable></td>
																<td><lable><input type="radio" value="Slightly dissatisfied" optiontext="Slightly dissatisfied" id="3007" subcontrolcontent="Omnibox/Address Bar" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1616" name="1614_1616" controlindex="2" /></lable></td>
																<td><lable><input type="radio" value="Neither satisfied nor dissatisfied" optiontext="Neither satisfied nor dissatisfied" id="3008" subcontrolcontent="Omnibox/Address Bar" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1616" name="1614_1616" controlindex="3" /></lable></td>
																<td><lable><input type="radio" value="Slightly satisfied" optiontext="Slightly satisfied" id="3009" subcontrolcontent="Omnibox/Address Bar" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1616" name="1614_1616" controlindex="4" /></lable></td>
																<td><lable><input type="radio" value="Moderately satisfied" optiontext="Moderately satisfied" id="3010" subcontrolcontent="Omnibox/Address Bar" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1616" name="1614_1616" controlindex="5" /></lable></td>
															</tr>
															<tr>
																<th>New Tab Page</th>
																<td><lable><input type="radio" value="Extremely dissatisfied" optiontext="Extremely dissatisfied" id="3005" subcontrolcontent="New Tab Page" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1617" name="1614_1617" controlindex="0" /></lable></td>
																<td><lable><input type="radio" value="Moderately dissatisfied" optiontext="Moderately dissatisfied" id="3006" subcontrolcontent="New Tab Page" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1617" name="1614_1617" controlindex="1" /></lable></td>
																<td><lable><input type="radio" value="Slightly dissatisfied" optiontext="Slightly dissatisfied" id="3007" subcontrolcontent="New Tab Page" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1617" name="1614_1617" controlindex="2" /></lable></td>
																<td><lable><input type="radio" value="Neither satisfied nor dissatisfied" optiontext="Neither satisfied nor dissatisfied" id="3008" subcontrolcontent="New Tab Page" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1617" name="1614_1617" controlindex="3" /></lable></td>
																<td><lable><input type="radio" value="Slightly satisfied" optiontext="Slightly satisfied" id="3009" subcontrolcontent="New Tab Page" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1617" name="1614_1617" controlindex="4" /></lable></td>
																<td><lable><input type="radio" value="Moderately satisfied" optiontext="Moderately satisfied" id="3010" subcontrolcontent="New Tab Page" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1617" name="1614_1617" controlindex="5" /></lable></td>
															</tr>
															<tr class="active">
																<th>Download Management</th>
																<td><lable><input type="radio" value="Extremely dissatisfied" optiontext="Extremely dissatisfied" id="3005" subcontrolcontent="Download Management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1618" name="1614_1618" controlindex="0" /></lable></td>
																<td><lable><input type="radio" value="Moderately dissatisfied" optiontext="Moderately dissatisfied" id="3006" subcontrolcontent="Download Management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1618" name="1614_1618" controlindex="1" /></lable></td>
																<td><lable><input type="radio" value="Slightly dissatisfied" optiontext="Slightly dissatisfied" id="3007" subcontrolcontent="Download Management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1618" name="1614_1618" controlindex="2" /></lable></td>
																<td><lable><input type="radio" value="Neither satisfied nor dissatisfied" optiontext="Neither satisfied nor dissatisfied" id="3008" subcontrolcontent="Download Management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1618" name="1614_1618" controlindex="3" /></lable></td>
																<td><lable><input type="radio" value="Slightly satisfied" optiontext="Slightly satisfied" id="3009" subcontrolcontent="Download Management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1618" name="1614_1618" controlindex="4" /></lable></td>
																<td><lable><input type="radio" value="Moderately satisfied" optiontext="Moderately satisfied" id="3010" subcontrolcontent="Download Management" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1618" name="1614_1618" controlindex="5" /></lable></td>
															</tr>
															<tr>
																<th>Themes**</th>
																<td><lable><input type="radio" value="Extremely dissatisfied" optiontext="Extremely dissatisfied" id="3005" subcontrolcontent="Themes**" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1619" name="1614_1619" controlindex="0" /></lable></td>
																<td><lable><input type="radio" value="Moderately dissatisfied" optiontext="Moderately dissatisfied" id="3006" subcontrolcontent="Themes**" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1619" name="1614_1619" controlindex="1" /></lable></td>
																<td><lable><input type="radio" value="Slightly dissatisfied" optiontext="Slightly dissatisfied" id="3007" subcontrolcontent="Themes**" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1619" name="1614_1619" controlindex="2" /></lable></td>
																<td><lable><input type="radio" value="Neither satisfied nor dissatisfied" optiontext="Neither satisfied nor dissatisfied" id="3008" subcontrolcontent="Themes**" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1619" name="1614_1619" controlindex="3" /></lable></td>
																<td><lable><input type="radio" value="Slightly satisfied" optiontext="Slightly satisfied" id="3009" subcontrolcontent="Themes**" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1619" name="1614_1619" controlindex="4" /></lable></td>
																<td><lable><input type="radio" value="Moderately satisfied" optiontext="Moderately satisfied" id="3010" subcontrolcontent="Themes**" subcontrolparentid="1614" subcontrolparenttypeid="3" subcontrolid="1619" name="1614_1619" controlindex="5" /></lable></td>
															</tr>
														</tbody>
													</table>
													
												</div>
											</div>
										</div>
									</li>
									<li>
										<div class="theme-question ui-question-a" data-theme="a" data-form="ui-question-a">
											<div>
												<span class="label-control-status status"></span>
											</div>
											<div class="theme-question-heading ui-question-heading-a" data-theme="a" data-form="ui-question-heading-a">
												<span>4.&nbsp;</span><span>Scale</span>
											</div>
											<div class="form-controls">
												<div class="container-control-embed"></div>
												<div class="container-control-notes label-control-note">test description</div>
												<div>
													
													<table cellspacing="1" cellpadding="0" border="0" class="table-scale">
														<tbody>
															<tr class="table-scale-header">
																<td>&nbsp;</td>
																<th>1</th>
																<th>2</th>
																<th>3</th>
																<th>4</th>
																<th>5</th>
																<td>&nbsp;</td>
															</tr>
															<tr class="table-scale-row">
																<th>Poor</th>
																<td><label><input type="radio" value="1" optiontext="1" optionid="1" name="1625" /></label></td>
																<td><label><input type="radio" value="2" optiontext="2" optionid="2" name="1625" /></label></td>
																<td><label><input type="radio" value="3" optiontext="3" optionid="3" name="1625" /></label></td>
																<td><label><input type="radio" value="4" optiontext="4" optionid="4" name="1625" /></label></td>
																<td><label><input type="radio" value="5" optiontext="5" optionid="5" name="1625" /></label></td>
																<th>Excellent</th>
															</tr>
														</tbody>
													</table>
													
												</div>
											</div>
										</div>
									</li>
									<li>
										<div class="theme-question ui-question-a" data-theme="a" data-form="ui-question-a">
											<div>
												<span class="label-control-status status"></span>
											</div>
											<div class="theme-question-heading ui-question-heading-a" data-theme="a" data-form="ui-question-heading-a">
												<span>5.&nbsp;</span><span>Multiple Choice + Comments</span>
											</div>
											<div class="form-controls">
												<div class="container-control-embed"></div>
												<div class="container-control-notes label-control-note">test description</div>
												<ul class="container-control-includes">
													<li class="row-choice"><label><input type="radio" autocomplete="off" value="Big Story Little Heroes" optionkindid="0" optiontext="Big Story Little Heroes" optionindex="0" name="13724091179021601" id="2985" class="radio-option" />Big Story Little Heroes</label></li>
													<li class="row-choice"><label><input type="radio" autocomplete="off" value="Dragon City" optionkindid="0" optiontext="Dragon City" optionindex="1" name="13724091179021601" id="2986" class="radio-option" />Dragon City</label></li>
												</ul>
												<div class="container-control-other">
													<div class="row-choice-other">
														<div class="cell">
															<label><input type="radio" autocomplete="off" optiontext="Other, (please specify)" optionkindid="1" optionid="2991" name="13724091179021601" class="radio-other" />Other, (please specify)</label>
														</div>
														<div class="cell">
															<input type="text" class="text-field input-control-other" />
														</div>
													</div>
												</div>
												<div class="container-control-comment">
													<div class="label-control-comment">Please help us understand why you selected this answer</div>
													<div class="row-textarea">
														<div class="cell">
															<textarea autocapitalize="off" autocorrect="off" autocomplete="off" class="textarea-field textarea-control-comment"></textarea>
														</div>
													</div>
												</div>
												
											</div>
										</div>
									</li>
									<li>
										<div class="theme-question ui-question-a" data-theme="a" data-form="ui-question-a">
											<div>
												<span class="label-control-status status"></span>
											</div>
											<div class="theme-question-heading ui-question-heading-a" data-theme="a" data-form="ui-question-heading-a">
												<span>6.&nbsp;</span><span>Multiple Choice + additional comments</span>
											</div>
											<div class="form-controls">
												<div class="container-control-embed"></div>
												<div class="container-control-notes label-control-note">test description</div>
												<ul class="container-control-includes">
													<li class="row-choice">
														<label><input type="checkbox" autocomplete="off" value="Big Story Little Heroes" optionkindid="0" optiontext="Big Story Little Heroes" optionindex="0" name="13724091179021601" id="2985" class="radio-option" />Microsoft</label>
													</li>
													<li class="row-choice">
														<label><input type="checkbox" autocomplete="off" value="Dragon City" optionkindid="0" optiontext="Dragon City" optionindex="1" name="13724091179021601" id="2986" class="radio-option" />Oracle</label>
														<div class="container-additional-details-or-comments container-additional-details-or-comments-3026" style="">
															<div class="row-desc">Additional details or comments</div>
															<div class="row-details"><input type="text" optionvalue="Opton 1" optiontext="Opton 1" optionindex="0" optionid="3026" class="text-field input-additional-details-or-comments"></div>
														</div>
													</li>
												</ul>
												<div class="container-control-other">
													<div class="row-choice-other">
														<div class="cell">
															<label><input type="checkbox" autocomplete="off" optiontext="Other, (please specify)" optionkindid="1" optionid="2991" name="13724091179021601" class="radio-other" />Other, (please specify)</label>
														</div>
														<div class="cell">
															<input type="text" class="text-field input-control-other" />
														</div>
													</div>
												</div>
												<div class="container-control-comment">
													<div class="label-control-comment">Please help us understand why you selected this answer</div>
													<div class="row-textarea">
														<div class="cell">
															<textarea autocapitalize="off" autocorrect="off" autocomplete="off" class="textarea-field textarea-control-comment"></textarea>
														</div>
													</div>
												</div>
												
											</div>
										</div>
									</li>
								</ul>
							</div>
							
							<div>
								<a title="Next" class="theme-button ui-button-a" data-theme="a" data-form="ui-button-a"><span>Next</span></a>
							</div>
						</div>
					</div>
					
				</div>
				<div class="theme-footer ui-footer-a" data-theme="a" data-form="ui-footer-a">
					Powered by <a title="Inqwise">Inqwise</a>
				</div>
			</div>
		</div>
		
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" charset="utf-8"></script>
		<script type="text/javascript">window.jQuery || document.write("<script type=\"text/javascript\" src=\"<%=applicationURL%>/scripts/jquery/1.8.2/jquery.min.js\"><\/script>")</script>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/utils.js" charset="utf-8"></script>
		<!--[if lt IE 8]>
		<script type="text/javascript" src="<%=applicationURL%>/scripts/utils/json2.js" charset="utf-8"></script>
		<![endif]-->
		
		<script type="text/javascript" src="<%=applicationURL%>/scripts/components/survey_preview.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			
			//alert("READY");
			
		});
		</script>
	
	</body>
</html>