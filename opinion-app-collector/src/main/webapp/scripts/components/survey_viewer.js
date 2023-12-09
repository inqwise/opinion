/*!
 * Survey Viewer
 * Copyright (c) 2011 Inqwise
 *
 */

(function(jQuery) {
	surveyViewer = function(givenOptions) {
		
		var options = $.extend({
			survey : null,
			guidTypeId : null,
			guid : null,
			modeId : null,
			elements : null,
			responseType : null,
			events : null
		}, givenOptions);
		
		var updatePageNumber = function() {
			options.elements.label_StartPageNumber.text((options.survey.sheets.lastSelected + 1));
			options.elements.label_EndPageNumber.text(options.survey.sheets.list.length);
			
			// header-page-title
			if(options.survey.sheets.list[options.survey.sheets.lastSelected].title != null) {
				$('.header-page-title').show();
				$('#label_page_number').html(options.survey.usePageNumbering ? (options.survey.sheets.lastSelected + 1) + ".&nbsp;" : "");
				$('#label_page_title').text((options.survey.sheets.list[options.survey.sheets.lastSelected].title != null ? options.survey.sheets.list[options.survey.sheets.lastSelected].title : ""));
			} else {
				$('.header-page-title').hide();
			}
			
			if(options.survey.sheets.list[options.survey.sheets.lastSelected].description != null) {
				$('#paragraph_page_description')
					.show()
					.html(options.survey.sheets.list[options.survey.sheets.lastSelected].description);
			} else {
				$('#paragraph_page_description').hide();
			}
			
		};
		
		function j(t) {
			
            var s = false,
                r;
            t = parseInt(options.survey.controls.startIndex);
            s = t;
            $("ul.list-controls li.control").each(function (i, el) {
            	r = $(this).find(".label-control-number");
                r.each(function () {
                    //$(this).text((s < 10) ? "0" + s : s);
                    $(this).html(s + ".&nbsp;");
                });
                if (r.length > 0 && s !== false) {
                    s++;
                }
            });
        };
		
		var getControls = function(sheetId, index, callback) {
		
			options.events.controls.getControls({
				sheetId : sheetId,
				index : index,
				success : function() {
					
					$("#placeholder_page_controls ul.list-controls").empty();
				
					if (options.survey.controls.list.length != 0) {
						
						// clear previous validateElements
						validateElements.elements = [];
						
						
						var controlCount = 0;
						for (var i = 0; i < options.survey.controls.list.length; i++) {
							if (options.survey.controls.list[i].parentId == sheetId) {
								
								var c = options.survey.controls.list[i];
								
								$("#placeholder_page_controls ul.list-controls").append("<li/>");
								var v;
								v = $("#placeholder_page_controls ul.list-controls > li:last");
								v.addClass("control");
								v.control({
									opinionId : options.survey.surveyId,
									controlId : c.controlId,
									controlTypeId : c.controlTypeId,
									controlIndex : i,
									controlCount : controlCount,
									complete : function(count) {
										controlCount += count;
									}
								});
								v.attr({ "controlid" : c.controlId, "controlindex" : i });
								
							}
						}
						
						
						
						// TODO:
						// alert(controlCount);
						
						
						if(options.survey.useQuestionNumbering) {
							j();
						} else {
							$('.column-control-number').hide();
						}
						
						
						
						if(validateElements.elements.length != 0) {
							// declare validator if needed
							defaultValidator = new validator({
								elements : validateElements.elements
							});
						} else {
							defaultValidator = null;
						}
						
					} 
					
					if(options.survey.isEnablePrevious && options.survey.sheets.lastSelected != 0 && options.survey.sheets.lastSelected != ((options.survey.sheets.list.length - 1))) {
						options.elements.container_Previous.show();
					} else if (options.survey.isEnablePrevious && options.survey.sheets.lastSelected == ((options.survey.sheets.list.length - 1)) && options.survey.sheets.lastSelected != 0) {
						options.elements.container_Previous.show();
					} else {
						options.elements.container_Previous.hide();
					}
					
					if (options.survey.sheets.lastSelected == ((options.survey.sheets.list.length - 1))) {
						options.elements.container_Next.hide();
						
						options.elements.container_Finish.show();
						
						// set key 13 on submit button
						$(document)
							.unbind('keydown')
							.keydown(function (event) {
							
								if (event.which == 13) {
									options.elements.button_FinishSurvey.trigger('click');
									event.preventDefault();
								}
								
						});
						
						
					} else {
						
						options.elements.container_Next.show();
						
						// set key 13 on submit button
						$(document)
							.unbind('keydown')
							.keydown(function(event) {
							
							if (event.which == 13) {
								
								options.elements.button_NextPage.trigger('click');
								event.preventDefault();
							
							}
								
						});
						
						options.elements.container_Finish.hide();
					}
					
					updatePageNumber(); // 1 of ...
					
					if(callback != undefined && typeof callback == 'function')
						callback();
					
				}
			
			
			
			
			
			});
			
		};
		
		var f = function() {
			
			if(options.survey.sheets.list.length > 0) {
				
				// TODO: remove this request
				// start
				//if(options.guidTypeId == 1 && options.modeId == 1 && !options.survey.isSessionStart) {
				//	options.events.responses.createResponse({
				// 		responseTypeId : options.responseType.startOpinion,
				//		async : true,
				//		responseData : null
				//	});
				//}
				
				
				// prev
				options.elements.button_PreviousPage.click(function() {
					
					options.elements.button_PreviousPage.focus();

					if(options.survey.sheets.lastSelected > 0) {
						var currentSelected = options.survey.sheets.lastSelected;
						options.survey.sheets.lastSelected--;
						
						if(options.guidTypeId == 1 && options.modeId == 1) {
							var sheetId = options.survey.sheets.list[options.survey.sheets.lastSelected].sheetId;
							var lastSelected = options.survey.sheets.lastSelected;
							var obj = {
								sheetIndex : currentSelected,
								sheetId : options.survey.sheets.list[currentSelected].sheetId,
								targetIndex : lastSelected,
								targetId : sheetId
							};
							
							options.events.responses.createResponse({
								responseTypeId : options.responseType.prevSheet,
								async : true,
								responseData : obj,
								success : function() {
									
									// Get Controls
									getControls(options.survey.sheets.list[options.survey.sheets.lastSelected].sheetId, options.survey.sheets.lastSelected, function() {
										// anchor top
										location.href = "#top";
									});
									
								}
							});
							
						} else {
							
							// Get Controls
							getControls(options.survey.sheets.list[options.survey.sheets.lastSelected].sheetId, options.survey.sheets.lastSelected, function() {
								// anchor top
								location.href = "#top";
							});
							
						}
						
					}
					
				});
				
				// next
				options.elements.button_NextPage.click(function() {
					
					
					
					options.elements.button_NextPage.focus();
					
					var nextPage = function() {
						
						
						var currentSelected = options.survey.sheets.lastSelected;
						options.survey.sheets.lastSelected++;
						
						if(options.survey.sheets.lastSelected <= ((options.survey.sheets.list.length - 1))) {
							
							if(options.guidTypeId == 1 && options.modeId == 1) {
								var sheetId = options.survey.sheets.list[options.survey.sheets.lastSelected].sheetId;
								var lastSelected = options.survey.sheets.lastSelected;
								var obj = {
									sheetIndex : currentSelected,
									sheetId : options.survey.sheets.list[currentSelected].sheetId,
									targetIndex : lastSelected,
									targetId : sheetId
								};
								
								options.events.responses.createResponse({
									responseTypeId : options.responseType.nextSheet,
									async : true,
									responseData : obj,
									success : function() {
							
										// Get Controls
										getControls(options.survey.sheets.list[options.survey.sheets.lastSelected].sheetId, options.survey.sheets.lastSelected, function() {
											// anchor top
											location.href = "#top";
										});
										
									}
								});
								
							} else {
								
								// Get Controls
								getControls(options.survey.sheets.list[options.survey.sheets.lastSelected].sheetId, options.survey.sheets.lastSelected, function() {
									// anchor top
									location.href = "#top";
								});
								
								
							}
							
						}
						
						
						
					};
					
					
					
					if(defaultValidator) {
						
						defaultValidator.validate({
							accept : function() {
								nextPage();	
							},
							error: function() {
								//
							}
						});
					} else {
						nextPage();
					}
					
					
					
				});
				
				// finish
				options.elements.button_FinishSurvey.click(function() {
					
					options.elements.button_FinishSurvey.focus(); // fix
					
					var finishSurvey = function() {
						
						
						
						if(options.guidTypeId == 1 && options.modeId == 1) {
							
							var sheetId = options.survey.sheets.list[options.survey.sheets.lastSelected].sheetId;
							var lastSelected = options.survey.sheets.lastSelected;
							var obj = {
								sheetIndex : lastSelected,
								sheetId : sheetId
							};
							
							options.events.responses.createResponse({
								responseTypeId : options.responseType.finishOpinion,
								async : true,
								responseData : obj,
								success : function() {
									
									if(options.survey.returnUrl != undefined) {
										// do redirect
										location.href = options.survey.returnUrl;
									}
									
								}
							});
							
						}
						
						options.elements.panel_Survey.hide();
						options.elements.panel_SurveyFinishMessage.show();
						
						$(document).unbind('keydown');
						
						// anchor top
						location.href = "#top";
						
						
					};
					
					
					if(defaultValidator) {
						defaultValidator.validate({
							accept : function() {
								finishSurvey();
							},
							error: function() {
								//
							}
						});
					} else {
						finishSurvey();
					}
					
				});
				
				getControls(options.survey.sheets.list[options.survey.sheets.lastSelected].sheetId, options.survey.sheets.lastSelected, function() {
					options.elements.panel_SurveyStartMessage.hide();
					options.elements.panel_Survey.show();
				});
			}
		};
		
		var bindPaging = function() {
			
			if(options.survey.isEnableStartMessage && !options.survey.isSessionStart) {
				options.elements.paragraph_SurveyStartMessage.html(options.survey.startMessage.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>"));				
				options.elements.button_StartSurvey.click(function() {
					
					options.elements.button_StartSurvey.focus();
					// anchor top
					location.href = "#top";
					
					f();
				});
				options.elements.panel_SurveyStartMessage.show();
				
				// set key 13 on submit button
				$(document)
					.unbind('keydown')
					.keydown(function (event) {
					
					/*
					var code;
					if (!e) var e = window.event;
					if (e.keyCode) code = e.keyCode;
					else if (e.which) code = e.which;
				
					// enter
					if (code == 13) {
						options.elements.button_StartSurvey.trigger('click');
					}
					*/
						
						if (event.which == 13) {
							options.elements.button_StartSurvey.trigger('click');
							event.preventDefault();
						}
						
						
				});
				
			} else {
				f();
			}
			
		};
		
		var init = function() {
			
			
			// init
			document.title = options.survey.name;
			//$('meta[property="og:title"]').attr('content', options.survey.name);
			options.elements.header_SurveyTitle.text(options.survey.name);
			
			// logo
			if(options.survey.logoUrl) {
				$('#logo')
					.show()
					.append("<img src=\"" + options.survey.logoUrl + "\" alt=\"" + options.survey.name + "\"/>");
			}
			
			if(options.survey.isPasswordRequired) {
				
				elements.panel_Survey.hide();
				elements.panel_SurveyRequiresPassword.show();

				$('#input_password').focus();
				internalValidator = new validator({
					elements : [
						{
							name : 'input-password',
							status : $('#status_input_password'),
							rules : [
								{ method : 'required', message : 'This field is required.' },
								{ method : 'rangelength', pattern : [6,12] }
							]
						}
					],
					submitElement : $('#button_continue'),
					messages : null,
					accept : function () {

						options.events.responses.createResponse({
							responseTypeId : options.responseType.authorize,
							async : false,
							responseData : null,
							password : $.removeHTMLTags($('#input_password').val()).replace(/\r/g, ""),
							success : function() {
							
								// clear errors
								$('#error_message_invalid_password').hide();

								elements.panel_SurveyRequiresPassword.hide();
								
								if(options.survey.sheets.list.length != 0) {
									bindPaging();
								} else {
									// no pages
								}
							
							},
							error : function(error) {
								if(error == "InvalidPassword") {
									$('#error_message_invalid_password').show();	
								}
							}
						});
						
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
			        	if(!$('#button_continue').is(':focus')) {
			        		internalValidator.validate();
						}
			        }
			        
				});

				// ip protected					
				//elements.panel_SurveyNotAuthorized.show();
			} else {
			
				if(options.survey.sheets.list.length != 0) {
					bindPaging();
				} else {
					// no pages
				}
				
			}
			
		};
		
		init();
		
	}
})(jQuery);