var content = s.find(".theme-content");
			
			// build inner html
			$("<div>" +
				"<div class=\"controls\"></div>" +
				"<div>" +
					"<div class=\"container-back\" style=\"display: none;\">" +
						"<a href=\"#\" title=\"" + data.messages.back + "\" class=\"button-back\">" + data.messages.back + "</a>" +
					"</div>" +
					"<div class=\"container-vote\" style=\"display: none;\">" +
						"<a href=\"#\" title=\"" + data.messages.vote +"\" class=\"theme-button ui-button-a button-vote\" data-theme=\"a\" data-form=\"ui-button-a\"><span>" + data.messages.vote + "</span></a>" +
					"</div>" +
					"<div class=\"container-view-results\" style=\"display: none;\">" +
						"&nbsp;or&nbsp;<a href=\"#\" title=\"" + data.messages.viewResults + "\" class=\"button-view-results\">" + data.messages.viewResults + "</a>" +
					"</div>" +
				"</div>" +
			"</div>").appendTo(content);
			
			
			// results or not
			// if not results getControls
			getControls({
				guidTypeId : (config.guidTypeId != undefined ? config.guidTypeId : 1),
				guid : (config.guid != undefined ? config.guid : ""),
				modeId : (config.modeId != undefined ? config.modeId : 1),
				themeId : (config.themeId != undefined ? config.themeId : null),
				ref : document.referrer,
				target : location.href,
				lastSelected : 0,
				success : function(controls) {
					
					// clear validate elements
					validateElements.elements = [];
					
					
					var c = $("<ul>" +
						"<li>" +
							"<div class=\"theme-question ui-question-a\" data-theme=\"a\" data-form=\"ui-question-a\">" +
								"<div><span class=\"label-control-status status\"></span></div>" +
								"<div class=\"theme-question-heading ui-question-heading-a\" data-theme=\"a\" data-form=\"ui-question-heading-a\">" +
									"<span class=\"label-control-mandatory asterisk\" style=\"display: none;\">*&nbsp;</span><span class=\"header-control-content\">" + controls.list[0].content.replace(/\n/g, "<br/>") + "</span>" +
								"</div>" +
								"<div class=\"theme-question-content ui-question-content-a\" data-theme=\"a\" data-form=\"ui-question-content-a\">" +
									"<div class=\"form-controls\">" +
										"<div class=\"container-control-embed\"></div>" +
										"<div class=\"container-control-notes label-control-note\" style=\"display: none;\"></div>" +
										"<ul class=\"container-control-includes\"></ul>" +
										"<div class=\"container-control-other\"></div>" +
									"</div>" +
									"<div class=\"form-results\" style=\"display: none\">" +
										"<div class=\"container-control-results\"></div>" +
										"<div class=\"container-control-total-votes\">Total Votes: <span class=\"label-total-votes\"></span></div>" +
									"</div>" +
								"</div>" +
							"</div>" +
						"</li>" +
					"</ul>").appendTo(content.find(".controls"));
					
					
					var u = controls.list[0];
					
					var link = null;
					var F = u.controlId;
					var g = c.find('.container-control-includes');
					var otherContainer = c.find(".container-control-other");
					
					// mandatory
					if(u.isMandatory) {
						
			            c.find('.label-control-mandatory').show();
			            
			            validateElements.elements.push({
                    		group : String(F),
                    		status : c.find('.label-control-status'),
                    		rules : [
                    		    { method : 'required', message : 'This question is required.' }
         					]
                    	});
			        	
			        	
					}
					
					// link
					if(u.link) {
			        	link = u.link;
			        }
					
					// note
					if(u.isEnableNote) {
						if(u.note != null) {
							c.find('.label-control-note').html(u.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")).show();
						}
					}
					
					// includes
					switch(u.controlTypeId) {
						case 2 : {
							
							switch(u.inputTypeId) {
								case 0: {
									
									if(u.options.list.length != 0) {
			                            var P = null;
			                            var j = 0;
			                        	for (var i = 0; i < u.options.list.length; i++) {
			                        		if(u.options.list[i].optionKindId == 0) {
			                            		var k = $("<li class=\"row-choice\"><label><input type=\"radio\" class=\"radio-option\" id=\"" + u.options.list[i].optionId + "\" name=\"" + hackSession + F + "\" optionindex=\"" + i + "\" optiontext=\"" + u.options.list[i].text + "\" optionkindid=\"" + u.options.list[i].optionKindId + "\" value=\"" + u.options.list[i].value + "\" autocomplete=\"off\" />" + u.options.list[i].text + "</label></li>");
			                                	if(u.options.list[i].selectTypeId != undefined) {
			                                		k.find('input:radio').prop('checked', true);
			                                	}
			                                	k.appendTo(g);
			                                	j++;
			                        		}
			                        		
			                        		// ?
			                        		if(u.options.list[i].optionKindId == 1) {
			                        			P = u.options.list[i];
			                        		}
			                        		
			                            }
			                        	
			                        	if (config.guidTypeId == 1 && config.modeId == 1) {
			                                var f = g.find('input:radio');
			                                f.change(function () {
			                                    var a = {
			                                        controlId: F,
			                                        controlParentTypeId: u.parentTypeId,
			                                        controlParentId: u.parentId,
			                                        controlTypeId: u.controlTypeId,
			                                        inputTypeId: u.inputTypeId,
			                                        controlIndex : 0, // I.controlIndex,
			                                        controlContent: u.content,
			                                        /* sheetId: survey.sheets.list[survey.sheets.lastSelected].sheetId, */
			                                        /* sheetIndex: survey.sheets.lastSelected, */
			                                        optionId: parseInt($(this).attr('id')),
			                                        optionKindId : parseInt($(this).attr('optionkindid')),
			                                        optionIndex : parseInt($(this).attr('optionindex')),
			                                        answerText: $(this).attr('optiontext'),
			                                        answerValue: $(this).val()
			                                    };
			                                    
												createResponse({
													guidTypeId : config.guidTypeId,
													guid : config.guid,
													modeId : config.modeId,
													responseTypeId : responseType.option,
													async : true,
													responseData : a
												});
												
			                                    
			                                });
			                            }
			                        	
			                        	if(P != null) {
			                        		var l = $("<div class=\"row-choice-other\"><div><label><input type=\"radio\" class=\"radio-other\" name=\"" + hackSession + F + "\" optionid=\"" + P.optionId + "\" optionkindid=\"" + P.optionKindId +"\" optiontext=\"" + ((P.text != "" && P.text != null) ? P.text : "Other, (please specify)") + "\" autocomplete=\"off\" />" + ((P.text != "" && P.text != null) ? P.text : "Other, (please specify)") + "</label></div><div><input type=\"text\" class=\"text-field input-control-other\" /></div></div>");
			                                l.appendTo(otherContainer);
			                                var m = l.find('.radio-other');
			                                var n = l.find('.input-control-other');
			                                
			                                m.unbind("change").change(function () {
			                                    if ($(this).is(':checked')) {
			                                        n.focus()
			                                    }
			                                });
			                                
			                                if (config.guidTypeId == 1 && config.modeId == 1) {
			                                	
			                                	if(P.selectTypeId != undefined) {
			                                    	m.prop('checked', true);
			                                    	n.val(P.answerValue);
			                                    }
			                                	
			                                    n.focusin(function () {
			                                        if (!m.is(':checked')) {
			                                            m.prop('checked', true);
			                                        	m.change();
			                                        }
			                                    }).focusout(function () {
			                                        var a = {
			                                            controlId: F,
			                                            controlParentTypeId: u.parentTypeId,
			                                            controlParentId: u.parentId,
			                                            controlTypeId: u.controlTypeId,
			                                            inputTypeId: u.inputTypeId,
			                                            controlIndex : 0, // I.controlIndex,
			                                            controlContent: u.content,
			                                            /*sheetId: survey.sheets.list[survey.sheets.lastSelected].sheetId,*/
			                                            /*sheetIndex: survey.sheets.lastSelected,*/
			                                            optionId: parseInt(m.attr('optionid')),
			                                            optionKindId : parseInt(m.attr('optionkindid')),
			                                            optionIndex : j,
			                                            answerText: m.attr('optiontext'),
			                                            answerValue: $.removeHTMLTags($(this).val())
			                                        };
			                                        
													createResponse({
														guidTypeId : config.guidTypeId,
														guid : config.guid,
														modeId : config.modeId,
														responseTypeId : responseType.option,
														async : true,
														responseData : a
													});
													
			                                    });
			                                } else {
			                                    n.focusin(function () {
			                                        if (!m.is(':checked')) {
			                                            m.prop('checked', true);
			                                        	m.change();
			                                        }
			                                    });
			                                }
			                                
			                                /*
			                                n.focusin(function () {
			                                    if (!m.is(':checked')) {
			                                        m.prop('checked', true);
			                                    	m.change();
			                                    }
			                                });
			                                */
			                                
			                        	}
			                        	
			                        	
			                        	
									}
									
									break;
								}
								case 1: {
									
									// dropdown (without other)
									
									if (u.options.list.length != 0) {
			                            var o = $("<div><select class=\"dropdown-" + F + "\" name=\"" + hackSession + F + "\"></select></div>");
			                            var p = o.find(".dropdown-" + F);
			                            var q = p[0].options;
			                            
			                            var L = null;
			                            //var P = null;
			                            var R = null;
			                            var Y = [];
			                            var j = 0;
			                            for (var i = 0; i < u.options.list.length; i++) {
			                            	
			                            	// default text
			                            	if(u.options.list[i].optionKindId == 2) {
			                            		R = u.options.list[i];
			                            	}
			                            	
			                            	if(u.options.list[i].optionKindId == 0) {
			                                	Y.push(u.options.list[i]);
			                                	j++;
			                            	}
			                            	
			                            }
			                            
			                            o.appendTo(otherContainer);
			                            
			                            if (config.guidTypeId == 1 && config.modeId == 1) {
			                                p.change(function () {
			                                	var a = $(this).find('option:selected');
			                                	var b = {
			                                        controlId: F,
			                                        controlParentTypeId: u.parentTypeId,
			                                        controlParentId: u.parentId,
			                                        controlTypeId: u.controlTypeId,
			                                        inputTypeId: u.inputTypeId,
			                                        controlIndex : 0, // I.controlIndex,
			                                        controlContent: u.content,
			                                        selectTypeId : 1,
			                                        isSelected : (parseInt(a.attr('optionindex')) != 0),
			                                        /*sheetId: survey.sheets.list[survey.sheets.lastSelected].sheetId,*/
			                                        /*sheetIndex: survey.sheets.lastSelected,*/
			                                        optionId: parseInt(a.attr('optionid')),
			                                        optionKindId : parseInt(a.attr('optionkindid')),
			                                        optionIndex : parseInt(a.attr('optionindex')),
			                                        answerText: a.text(),
			                                        answerValue: a.attr('value')
			                                    };
			                                	
												createResponse({
													guidTypeId : config.guidTypeId,
													guid : config.guid,
													modeId : config.modeId,
													responseTypeId : responseType.option,
													async : true,
													responseData : b
												});
												
			                                });
			                            }
			                            
			                            // default text
			                            if(R != null) {
			                            	var t = new Option((R.text != null ? R.text : "Please choose one of the following"), 0);
			                                t.setAttribute("optionid", R.optionId);
			                                t.setAttribute("optionindex", 0);
			                                t.setAttribute("optionkindid", 2);
			                                
			                                try {
			                                    q.add(t)
			                                } catch (ex) {
			                                    q.add(t, null)
			                                }
			                            }
			                            
			                            // options
			                            if(Y.length != 0) {
			                            	for(var z = 0; z < Y.length; z++) {
			                            		var k = new Option(Y[z].text, Y[z].value);
			                                	k.setAttribute("optionid", Y[z].optionId);
			                                    k.setAttribute("optionindex", (z + 1));
			                                    k.setAttribute("optionkindid", Y[z].optionKindId);
			                                    
			                                    if (config.guidTypeId == 1 && config.modeId == 1) {
			                                    	if(Y[z].selectTypeId != undefined) {
			                                        	L = (z + 1);
			                                        }
			                                	}
			                                    
			                                    try {
			                                        q.add(k)
			                                    } catch (ex) {
			                                        q.add(k, null)
			                                    }
			                            	}
			                            }
			                            
			                            // default selected
			                            if(L != null) {
			                            	p[0].selectedIndex = L;
			                            }
			                            
			                            
			                            
			                            
									}
									
									break;
								}
								case 2: {
									
									// checkbox
									
									if (u.options.list.length != 0) {
			                            var P = null;
			                            var j = 0;
			                        	for (var i = 0; i < u.options.list.length; i++) {
			                        		if(u.options.list[i].optionKindId == 0) {
			                        			var k = $("<li class=\"row-choice\"><label><input type=\"checkbox\" class=\"checkbox-" + u.options.list[i].optionId + "\" id=\"" + u.options.list[i].optionId + "\" name=\"" + hackSession + F + "\" optionindex=\"" + i + "\" optiontext=\"" + u.options.list[i].text + "\" value=\"" + u.options.list[i].value + "\" autocomplete=\"off\" />" + u.options.list[i].text + "</label></li>");
			                                	if(u.options.list[i].selectTypeId != undefined) {
			                                		k.find('input:checkbox').prop('checked', true);
			                                	}
			                                	k.appendTo(g);
			                                	j++;
			                        		}
			                        		
			                        		// ?
			                        		if(u.options.list[i].optionKindId == 1) {
			                        			P = u.options.list[i];
			                        		}
			                        		
			                            }
			                        	
			                        	if (config.guidTypeId == 1 && config.modeId == 1) {
			                        		var f = g.find('input:checkbox');
			                                f.change(function () {
			                                	var a = {
													controlId: F,
													controlParentTypeId: u.parentTypeId,
													controlParentId: u.parentId,
													controlTypeId: u.controlTypeId,
													inputTypeId: u.inputTypeId,
													controlIndex : 0, // I.controlIndex,
													controlContent: u.content,
													selectTypeId : 2,
													isSelected: $(this).is(':checked'),
													/*sheetId: survey.sheets.list[survey.sheets.lastSelected].sheetId,*/
													/*sheetIndex: survey.sheets.lastSelected,*/
													optionId: parseInt($(this).attr('id')),
													optionIndex: parseInt($(this).attr('optionindex')),
													answerText: $(this).attr('optiontext'),
													answerValue: $(this).val()
												};
			                                    
												createResponse({
													guidTypeId : config.guidTypeId,
													guid : config.guid,
													modeId : config.modeId,
													responseTypeId : responseType.option,
													async : true,
													responseData : a
												});
												
			                                });
			                            }
			                        	
			                        	
			                        	if(P != null) {
			                        		
											var l = $("<div class=\"row-choice-other\"><div><label><input type=\"checkbox\" class=\"checkbox-other\" optionid=\"" + P.optionId + "\" name=\"" + hackSession + F + "\" optionkindid=\"" + P.optionKindId + "\" optiontext=\"" + ((P.text != "" && P.text != null) ? P.text : "Other, (please specify)") + "\" autocomplete=\"off\" />" + ((P.text != "" && P.text != null) ? P.text : "Other, (please specify)") + "</label></div><div><input type=\"text\" class=\"text-field input-control-other\" /></div></div>");
											l.appendTo(otherContainer);
											var z = l.find('.checkbox-other');
											var n = l.find('.input-control-other');
											
											if (config.guidTypeId == 1 && config.modeId == 1) {
												if(P.selectTypeId != undefined) {
			                                    	z.prop('checked', true);
			                                    	n.val(P.answerValue);
			                                    }
											}
											
											z.unbind("change").change(function () {
												if ($(this).is(':checked')) {
													n.focus()
												} else {
													// Send selectTypeId : 0
													if (config.guidTypeId == 1 && config.modeId == 1) {
														var a = {
															controlId: F,
															controlParentTypeId: u.parentTypeId,
															controlParentId: u.parentId,
															controlTypeId: u.controlTypeId,
															inputTypeId: u.inputTypeId,
															controlIndex : 0, // I.controlIndex,
															controlContent: u.content,
															selectTypeId: 2,
															isSelected : false,
															/* sheetId: survey.sheets.list[survey.sheets.lastSelected].sheetId,*/
															/*sheetIndex: survey.sheets.lastSelected,*/
															optionId : parseInt(z.attr('optionid')),
															optionKindId : parseInt(z.attr('optionkindid')),
															optionIndex : j,
															answerText: z.attr('optiontext'),
															answerValue: $.removeHTMLTags(n.val())
														};
														
														createResponse({
															guidTypeId : config.guidTypeId,
															guid : config.guid,
															modeId : config.modeId,
															responseTypeId : responseType.option,
															async : true,
															responseData : a
														});
														
													}
												}
											});
											
											if (config.guidTypeId == 1 && config.modeId == 1) {
												n.focusin(function () {
													if (!z.is(':checked')) {
														z.prop('checked', true);
														z.change();
													}
												}).focusout(function () {
													var a = {
														controlId: F,
														controlParentTypeId: u.parentTypeId,
														controlParentId: u.parentId,
														controlTypeId: u.controlTypeId,
														inputTypeId: u.inputTypeId,
														controlIndex : 0, // I.controlIndex,
														controlContent: u.content,
														selectTypeId: 2,
														isSelected : true,
														/*sheetId: survey.sheets.list[survey.sheets.lastSelected].sheetId,*/
														/*sheetIndex: survey.sheets.lastSelected,*/
														optionId : parseInt(z.attr('optionid')),
														optionKindId : parseInt(z.attr('optionkindid')),
														optionIndex : j,
														answerText: z.attr('optiontext'),
														answerValue: $.removeHTMLTags($(this).val())
													};
													
													createResponse({
														guidTypeId : config.guidTypeId,
														guid : config.guid,
														modeId : config.modeId,
														responseTypeId : responseType.option,
														async : true,
														responseData : a
													});
													
												});
												
											} else {
												n.focusin(function () {
													if (!z.is(':checked')) {
														z.prop('checked', true)
														z.change();
													}
												});
											}
			                               
			                        	}
			                        	
			                        	
									}
									
									break;
								}
								
							}
							
							
							if(link) {
			                	
			                	// image
			                	if(u.linkTypeId == 1) {
			                		c.find(".container-control-embed")
			                			.html("<img src=" + u.link + " />")
			                			.show();
			                	}
			                	
			                	// embed code
			                	if(u.linkTypeId == 2) {
			                    	c.find(".container-control-embed")
			                			.html(u.link)
			                			.show();
			                	}
			                }
							
							break;
							
						}
					}
					
					
					
					
					
					// set validator
					
					
					
					
					if(validateElements.elements.length != 0) {
						// declare validator if needed
						defaultValidator = new validator({
							elements : validateElements.elements
						});
					} else {
						defaultValidator = null;
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					// actions
					
					content.find(".container-vote").show();
					content.find(".container-view-results").show();
					content.find(".container-back").hide();
					
					
					// vote
					content.find(".button-vote").click(function(e) {
						
						
						
						var finish = function() {
							
							if(config.guidTypeId == 1 && config.modeId == 1) {
								
								createResponse({
									guidTypeId : config.guidTypeId,
									guid : config.guid,
									modeId : config.modeId,
									responseTypeId : responseType.finishOpinion,
									async : true,
									responseData : {},
									target : location.href,
									success : function() {
										
										/*
										if(options.survey.returnUrl != undefined) {
											// do redirect
											location.href = options.survey.returnUrl;
										}
										*/
										
										/*
										getResults({
											guidTypeId : guidTypeId,
											guid : config.guid,
											success : function(data) {
												displayResults(s, data);
											},
											error: function(error) {
												alert("ERR");
											}
										});
										*/
										
										
										alert("FIN");
										
									},
									error: function(error) {
										// alert("createResponse: -> " + JSON.stringify(error));
									}
								});
								
							} else {
								
								
								// show demo results
								alert(config.guidTypeId + "_____" + config.modeId);
								
							}
							
						};
						
						
						if(defaultValidator) {	
							defaultValidator.validate({
								accept : function() {
									finish();
								},
								error: function() {
									//
								}
							});
						} else {
							finish();
						}
						
						
						/*
						content.find(".form-controls").hide();
						content.find(".form-results").show();
						
						
						content.find(".container-vote").hide();
						content.find(".container-view-results").hide();
						content.find(".container-back").show();
						*/
						
						e.preventDefault();
						
					});
					
					content.find(".button-view-results").click(function(e) {
						
						/*
						s.find(".form-controls").hide();
						s.find(".form-results").show();
						
						s.find(".container-vote").hide();
						s.find(".container-view-results").hide();
						s.find(".container-back").show();
						
						
						getResults({
							guidTypeId : guidTypeId,
							guid : config.guid,
							success : function(data) {
								displayResults(s, data);
							},
							error: function(error) {
								alert("ERR");
							}
						});
						*/
						
						e.preventDefault();
					});
					
					// back to poll
					content.find(".button-back").click(function(e) {
						
						/*
						s.find(".form-results").hide();
						s.find(".form-controls").show();
						
						s.find(".container-vote").show();
						s.find(".container-view-results").show();
						s.find(".container-back").hide();
						
						s.find(".container-control-results").empty();
						*/
						
						e.preventDefault();
					});
					
					
					
					
					
					
					
					
					
					
					
					
					// data.controls = controls;
					if (window.console) {
			    		window.console.log("get controls");
			    	}
					
					
				},
				error: function(error) {
					
				}
			});
			
			
			
			
			
			
			
			
			
			
			
			
			campaignId
			campaignName
			served
			videoClicks
			ctrVideo
			complete
			firstQuartile
			midPoint
			thirdQuartile
			bounceRate
			mute
			pause
			slideStarted
			fittedImage
			percentfitFromServed
			percentSuccessFit
			sliderMissed
			skipButton
			skipSlider
			clickedOnImage
			ctrSkipAd
			timeOnSkipAd
			avgTimeOnSkipAd
			timeSaved
			
			
			
			
			
			adId
			adName
			videoThumbUrl
			tagUrl
			campaignName
			campaignId
			status
			slideStarted
			fittedImage
			clickedOnImage
			ctrImage
			timeSpentOnImage
			
			
			