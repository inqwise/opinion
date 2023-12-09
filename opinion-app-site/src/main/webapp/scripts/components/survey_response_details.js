/*!
 * Response Details
 * Copyright (c) 2011 Inqwise
 *
 */

;(function(jQuery) {
	responseDetails = function(givenOptions) {
		
		var options = $.extend({
			opinionId : null,
			answererSessionId : null,
			absoluteUrl : null
		}, givenOptions);
		
		
		var startIndex = 1;
		var startNumerator = 1;
		
		var responses = [];
		
		var animateBars = function () {
			$(".container-control-includes").find("div.bar").each(function (i, el) {
				var val = Math.max($(el).attr("data-value") * $(el).parents(".outer-bar").width() / 100, 2) + "px";
				//$(el).animate({ "width" : val });
				$(el).css({ "width" : val });
			});
		};
		
		var j = function() {
			
			$('li.result-control .label-control-number').each(function(i, el) {
				$(el).html(startNumerator + i + ".&nbsp;");
			});
			
		};
		
		var init = function() {
			
			getResponseDetails({
				accountId : accountId,
				opinionId : options.opinionId,
				answererSessionId : options.answererSessionId,
				success : function(data) {
					
					response.id = data.id;
					
					$("#label_participant_name").text("Anonymous #" + data.id);
					
					$("#label_response_id").text(data.id);
					$("#label_status").text((data.status == 0 ? "Partial (Incomplete)" : "Completed"));
					$("#label_location").text((data.countryName != null ? data.countryName : "N/A"));
					$("#label_ip_address").text(data.ipAddress);
					$("#label_collector").text(data.collectorName);
					$("#label_os").text(data.osName);
					$("#label_browser").text(data.browserName);
					
					$("#label_start_date").text(data.startDate);
					
					if(data.finishDate != null) {
						$("#label_finish_date").text(data.finishDate);
					}
					
					if(data.timeTaken != undefined) {
						var timeTaken = (data.timeTaken.days != 0 ? data.timeTaken.days + (data.timeTaken.days > 1 ? " days, " : " day, ") : "") +
						(data.timeTaken.hours != 0 ? data.timeTaken.hours + (data.timeTaken.hours > 1 ? " hours, " : " hour, ") : "") + 
						(data.timeTaken.minutes != 0 ? data.timeTaken.minutes + (data.timeTaken.minutes > 1 ? " mins, " : " min, ") : "") +
						(data.timeTaken.seconds != 0 ? data.timeTaken.seconds + (data.timeTaken.seconds > 1 ? " secs" : " sec") : ""); 
						
						if(data.timeTaken.days == 0 
							&& data.timeTaken.hours == 0
							&& data.timeTaken.minutes == 0
							&& data.timeTaken.seconds == 0) {
	            			timeTaken = "Less than sec";
	            		}
						
						$("#label_time_taken").text(timeTaken);
					}
					
					// delete
					$('#link_response_delete').click(function() {
						
						var modal = new lightFace({
							title : "Deleting response",
							message : "Are you sure you want to delete this response?",
							actions : [
					           { 
					        	   label : "Delete", 
					        	   fire : function() {
				        	   		
						        	   deleteResponses({
						        		   accountId : accountId,
						        		   list : [response.id],
						        		   success : function() {
						        			   
						        			   modal.close();
						        			   location.href = options.absoluteUrl + "/surveys/" + options.opinionId + "/responses";
						        			   
						        		   },
						        		   error: function() {
						        			   //
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
						
					});
					
					
					
					
					
					var filterBySheet = function(sheetId) {
				
						var get = function() {
					
							// get response results
							getResponseResults({
								accountId : accountId,
								opinionId : options.opinionId,
								answererSessionId : options.answererSessionId,
								success : function(data) {
							
									response.controls.list = data.controls.list;
							
														
									$("#placeholder_results ul.list-result-controls").empty();
									
								
									function getControlsBySheetId(_sheetId) {
										var controls = [];
										for (var i = 0; i < data.controls.list.length; i++) {
											if(data.controls.list[i].parentId == _sheetId) {
												controls.push(data.controls.list[i]);
											}
										}
										return controls;
									}
									
									for(var z = 0; z < sheets.length; z++) {
										
										if(sheetId != undefined) {
							
											if(sheets[z].sheetId == sheetId) {
											
												var page = $("<li><div class=\"result-page-title\">Page " + (z + 1) + (sheets[z].title != undefined ? ": " + sheets[z].title : "") + "</div><ul></ul></li>").appendTo("ul.list-result-controls");
												var list = page.find("ul");
								
												var controls = getControlsBySheetId(sheets[z].sheetId);
								
												if(controls.length != 0) {							

													for(var i = 0; i < controls.length; i++) {
														var v = $("<li/>").appendTo(list);
														v.addClass("result-control c" + controls[i].controlId);
														v.resultControl({
															opinionId : options.opinionId,
															controlId : controls[i].controlId,
															controlTypeId : controls[i].controlTypeId,
															controlIndex : i
														});
														v.attr({ "controlid" : controls[i].controlId, "controlindex" : i });
													}
							
												} else {
													list.append("<li class=\"result-control-empty\">There are no questions on this page.</li>");
												}
											
												break;
											}
											
										} else {
											
											var page = $("<li><div class=\"result-page-title\">Page " + (z + 1) + (sheets[z].title != undefined ? ": " + sheets[z].title : "") + "</div><ul></ul></li>").appendTo("ul.list-result-controls");
											var list = page.find("ul");
														
											var controls = getControlsBySheetId(sheets[z].sheetId);
						
											if(controls.length != 0) {									

												for(var i = 0; i < controls.length; i++) {
													var v = $("<li/>").appendTo(list);
													v.addClass("result-control c" + controls[i].controlId);
													v.resultControl({
														opinionId : options.opinionId,
														controlId : controls[i].controlId,
														controlTypeId : controls[i].controlTypeId,
														controlIndex : i
													});
													v.attr({ "controlid" : controls[i].controlId, "controlindex" : i });
												}
							
											} else {
												list.append("<li class=\"result-control-empty\">There are no questions on this page.</li>");
											}
											
										}
										
										
										
										
										
										
										// show - organize questions
										//$("#list_option_controls").empty();
										//$("<li><div>Page " + (z + 1) + (sheets[z].title != undefined ? ": " + sheets[z].title : "") + "</div></li>").appendTo('#list_option_controls');
										
									}
							
									/*
									var optionNumber = 0;
									for (var i = 0; i < data.controls.list.length; i++) {
								
										// controls
										$("#placeholder_results ul.list-result-controls").append("<li/>");
										var v;
										v = $("#placeholder_results ul.list-result-controls > li:last");
										v.addClass('result-control c' + data.controls.list[i].controlId);
										if(i%2) {
											v.addClass('highlight');
										}
										v.resultControl({
											controlId : data.controls.list[i].controlId,
											controlTypeId : data.controls.list[i].controlTypeId,
											controlIndex : i
										});
										v.attr({ "controlid" : data.controls.list[i].controlId, 'controlindex' : i });
								
								
								
										
										// options
										var option = $("<li class=\"ui-label\" style=\"padding: 4px;\">" +
												"<input type=\"checkbox\" checked=\"checked\" id=\"" + data.controls.list[i].controlId + "\" parentid=\"" + data.controls.list[i].controlId + "\" class=\"checkbox-option ui-label-checkbox\" /><label for=\"" + data.controls.list[i].controlId + "\">" + (data.controls.list[i].controlTypeId == 11 ? "[" + data.controls.list[i].key + "]" : (optionNumber += 1) + ".&nbsp;" + data.controls.list[i].content) + "</label>" +
											"</li>");
									
										option
										.find('input:checkbox')
										.change(function() {
											if($(this).is(':checked')) {
												$('.list-result-controls > .c' + $(this).attr('parentid')).show();
											} else {
												$('.list-result-controls > .c' + $(this).attr('parentid')).hide();
											}
										});
										option.appendTo('#list_option_controls');
										
								
									}
									*/
							
									j();
							
									animateBars();
							
									/*}*/
							
							
							
									/*
									// organize questions
									// actions
									$('#link_select_all_controls').click(function() {
										$('.checkbox-option').prop('checked', true);
										$('.list-result-controls > li.result-control').show();
									});
									$('#link_select_none_controls').click(function() {
										$('.checkbox-option').prop('checked', false);
										$('.list-result-controls > li.result-control').hide();
									});
									*/
							
									// get related responses
									getResponses({
										accountId : accountId,
										opinionId : options.opinionId,
										success : function(data) {
									
											responses = data.list;
									
											var filter = function(event) {

												if (event.keyCode == '13' 
													|| event.keyCode == undefined
													|| event.keyCode == 0) {
									
												    // enter
												    var v = $('.ui-navigation-panel-input-goto:first').val();
													if(v != null 
															&& v != "") {
									
														if(!isNaN(parseInt(v))) {
									
															// find if exist page
															var d = parseInt(v);
															if(d > 0) {
																if(responses.length >= d) {
																	location.href = options.absoluteUrl + "/surveys/" + options.opinionId + "/responses/" + responses[d - 1].sessionId;
																} else {
																	// error
																	F.addClass("input-error");
																	$.timer(300, function(t) {
																		F.removeClass("input-error");
																		F.val(j + 1);
																		t.stop();
																	});
																}
															} else {
																// error
																F.addClass("input-error");
																$.timer(300, function(t) {
																	F.removeClass("input-error");
																	F.val(j + 1);
																	t.stop();
																});
															}
														} else {
															// error
															F.addClass("input-error");
															$.timer(300, function(t) {
																F.removeClass("input-error");
																F.val(j + 1);
																t.stop();
															});
													
														}
													}
												}
											};
									
											if(responses.length != 0) {
												var j = 0;
												for(var w = 0; w < responses.length; w++) {
													if(responses[w].sessionId == options.answererSessionId) {
									
														// prev
														if(responses[w - 1] != undefined) {
															$('.ui-navigation-panel-button-previous')
																.removeClass('ui-button-disabled')
																.attr({ "href" : options.absoluteUrl + "/surveys/" + options.opinionId + "/responses/" + responses[w - 1].sessionId });
														} else {
															$('.ui-navigation-panel-button-previous')
																.addClass('ui-button-disabled')
														}
												
														// next
														if(responses[w + 1] != undefined) {
															$('.ui-navigation-panel-button-next')
																.removeClass('ui-button-disabled')
																.attr({ "href" : options.absoluteUrl + "/surveys/" + options.opinionId + "/responses/" + responses[w + 1].sessionId });
														} else { 
															$('.ui-navigation-panel-button-next')
																.addClass('ui-button-disabled');
														}
									
														j = w;
														$('.label-response-index').text((w + 1));
														$('.ui-navigation-panel-input-goto').val((w + 1));
													}
												}
										
												// handle event
												var F = $('.ui-navigation-panel-input-goto');
												F.each(function(i, el) {
													$(el).keyup(function(event) {
														F.val($(this).val());
														filter(event);
													});
												});
												$('.ui-navigation-panel-button-go').click(filter);
										
												// responses count
												$('.label-responses-count').text(responses.length);
											}
									
									
										},
										error : function(error) {
											// 
										}
									});
									
							
								},
								error: function(error) {
									//
								}
							});
							
					
						};
				
						if(sheetId != undefined) {
					
							getStartIndexOfSheet({
								accountId : accountId,
								sheetId : sheetId,
								success : function(_data) {
							
									startIndex = _data.startIndex;
									startNumerator = _data.startNumerator;
							
									get();
							
								},
								error: function() {
							
								}
							});
						} else {
					
							startIndex = 1;
							startNumerator = 1;
					
							get();
					
						}
				
					};
			
					// sheets
					getSheets({
						accountId : accountId,
						opinionId : options.opinionId,
						success : function(data) {
					
							sheets = data.list;
					
							var _actions = [];
							_actions.push({
						    	label : "All Pages",
						    	value : 0,
						    	active : true,
								click : function(button) {
									//console.log(button);
								}
						    });
					
							for(var s = 0; s < sheets.length; s++) {
								_actions.push({
									label : "Page " + (s + 1), /* + ": " + sheets[s].title,*/
									value : sheets[s].sheetId,
									click : function(button) {
										//console.log(button);
									}
								});
							}
					
							$("#button_pages")
							.dropdownButton({
								disabled : false,
								actions : _actions,
								change : function(value) {
							
									// selected page
									if(value != 0) {
										filterBySheet(value);
									} else {
										filterBySheet();
									}
							
								}
					        });
					
							// all pages
							filterBySheet();
					
						},
						error: function() {
							sheets = [];
						}
					});
					
					
				},
				error : function(error) {
					//
				}
			});
			
			
		};
		
		init();
	}
})(jQuery);

