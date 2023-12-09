function highlightElement(index) {
	// scroll and hightlight
	var $demoItems = $('.block-preview').find('ul.list-controls').children('li.demo-question');
	var $demoItemsScrollPositions = new Array();

	// cache scroll positions for each item
	$demoItems.each(function(i, el){
		$demoItemsScrollPositions[i] = Math.round($(el).offset().top - $('.block-preview').offset().top) - 10;
	});
	
	$('.block-preview').find('ul.list-controls li.demo-question').each(function(i, el) {
		if(i == index) {
			
			var targetElement = $(el);
			
			$('.block-preview')
			.scrollTo($demoItemsScrollPositions[i] + $('.block-preview')
			.scrollTop(), 
			function() {
				// highlight element
				runEffect(targetElement);	
			});
			
		}
	});
}

function runEffect(el) {
	var selectedEffect = "highlight";
	// most effect types need no options passed by default
	var options = {
		/*color: 'blue'*/
	};
	
	/*
	// some effects have required parameters
	if ( selectedEffect === "scale" ) {
		options = { percent: 0 };
	} else if ( selectedEffect === "transfer" ) {
		options = { to: "#button", className: "ui-effects-transfer" };
	} else if ( selectedEffect === "size" ) {
		options = { to: { width: 200, height: 60 } };
	}
	*/
	
	// run the effect
	el.effect( selectedEffect, options, 500, effectCallback(el) );
};
// callback function to bring a hidden box back
function effectCallback(el) {
	setTimeout(function() {
		el.removeAttr( "style" ); //.hide().fadeIn();
	}, 1000 );
};

$.fn.scrollTo = function( target, options, callback ){
	if(typeof options == 'function' && arguments.length == 2){ callback = options; options = target; }
	var settings = $.extend({
		scrollTarget  : target,
		offsetTop     : 50,
		duration      : 500,
		easing        : 'swing'
	}, options);
	return this.each(function(){
		var scrollPane = $(this);
		var scrollTarget = (typeof settings.scrollTarget == "number") ? settings.scrollTarget : $(settings.scrollTarget);
		var scrollY = (typeof scrollTarget == "number") ? scrollTarget : scrollTarget.offset().top + scrollPane.scrollTop() - settings.offsetTop;
		scrollPane.animate({scrollTop : scrollY }, settings.duration, settings.easing, function(){
			if (typeof callback == 'function') { callback.call(this); }
		});
	});
};


function getTemplate(controlTypeId) {
	var template = null;
	switch(controlTypeId) {
	case 11:
		
		// hidden field / url parameter - variable
		template = $("<div>" +
        	"<div class=\"form-center\">" +
	        	"<div class=\"control-column\">" +
	        		"<div class=\"tab-move-control\"></div>" +
	        		"<div class=\"inline-reorder-control\">" +
	        			"<a class=\"button-up up lil_button\"></a>" +
	        			"<a class=\"button-down down lil_button\"></a>" +
	        		"</div>" +
	        	"</div>" +
		        "<div class=\"control-edit\">" +
		        	"<div>"+
						"<div class=\"control-heading\">URL Parameter Name</div>" +
						"<div class=\"params\" style=\"overflow: hidden\">" +
							"<div class=\"param-value\">" +
								"<div>" +
									"<input type=\"text\" name=\"question\" class=\"input-content\" autocomplete=\"off\" />" +
								"</div>" +
								"<div><label class=\"status-content\"></label></div>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<div class=\"placeholder-control\"></div>" +
				"</div>" +
				"<div class=\"control-view\">" +
					"<div class=\"view-question\">" +
						"<div class=\"view-question-heading\">" +
							"<span class=\"view-header-control-content\"></span>" +
						"</div>" +
					"</div>" +
					"<div class=\"control-actions\">" +
						"<a href=\"#\" class=\"button-white button-edit-question\" title=\"Edit\"><span>Edit</span></a>" +
						"<span class=\"button-copy-question\"></span>" +
						"<a href=\"#\" class=\"button-delete-question\"><span>Delete</span></a>" +
					"</div>" +
				"</div>" +
			"</div>" +
			"<div class=\"control-insert-question\" style=\"padding: 10px 0\">" +
				"<span class=\"button-insert-question\"></span>" +
			"</div>" +
		"</div>");
		
		break;
	case 12:
		
		// heading / descriptive text
		template = $("<div>" +
        	"<div class=\"form-center\">" +
	        	"<div class=\"control-column\">" +
	        		"<div class=\"tab-move-control\"></div>" +
	        		"<div class=\"inline-reorder-control\">" +
	        			"<a class=\"button-up up lil_button\"></a>" +
	        			"<a class=\"button-down down lil_button\"></a>" +
	        		"</div>" +
	        	"</div>" +
		        "<div class=\"control-edit\">" +
		        	"<div>"+
						"<div class=\"control-heading\">Heading Text</div>" +
						"<div class=\"params\" style=\"overflow: hidden\">" +
							/*"<div class=\"param-name\">* Question Text:</div>" +*/
							"<div class=\"param-value\">" +
								"<div>" +
									"<textarea name=\"question\" maxlength=\"1000\" class=\"input-content\" autocomplete=\"off\" placeholder=\"Heading text\" style=\"width: 304px; height: 46px;\"></textarea>" +
								"</div>" +
								"<div><label class=\"status-content\"></label></div>" +
							"</div>" +
						"</div>" +
						"<div class=\"control-heading\">Descriptive Text</div>" +
						"<div class=\"params\" style=\"overflow: hidden\">" +							
							/*"<div class=\"param-name\"></div>" +*/
							"<div class=\"param-value\">" +
								"<div>" +
									"<textarea class=\"textarea-note\" maxlength=\"1000\" autocomplete=\"off\" placeholder=\"Descriptive text\" style=\"width: 304px; height: 46px;\"></textarea>" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<div class=\"placeholder-control\"></div>" +
				"</div>" +
				"<div class=\"control-view\">" +
					"<div class=\"view-question\">" +
						"<div class=\"view-question-heading\">" +
							"<span class=\"view-header-control-content\"></span>" +
						"</div>" +
						"<div class=\"view-question-content\">" +
							"<div class=\"view-label-note\"></div>" +
						"</div>" +
					"</div>" +
					"<div class=\"control-actions\">" +
						"<a href=\"#\" class=\"button-white button-edit-question\" title=\"Edit\"><span>Edit</span></a>" +
						"<span class=\"button-copy-question\"></span>" +
						"<a href=\"#\" class=\"button-delete-question\"><span>Delete</span></a>" +
					"</div>" +
				"</div>" +
			"</div>" +
			"<div class=\"control-insert-question\" style=\"padding: 10px 0\">" +
				"<span class=\"button-insert-question\"></span>" +
			"</div>" +
		"</div>");
		
		
		break;
	default:
		
		template = $("<div>" +
        	"<div class=\"form-center\">" +
	        	"<div class=\"control-column\">" +
	        		"<div class=\"tab-move-control\"></div>" +
	        		"<div class=\"inline-reorder-control\">" +
	        			"<a class=\"button-up up lil_button\"></a>" +
	        			"<a class=\"button-down down lil_button\"></a>" +
	        		"</div>" +
	        	"</div>" +
		        "<div class=\"control-edit\">" +
		        	"<div>"+
						"<div class=\"control-heading\">Question Text</div>" +
						"<div class=\"params\" style=\"overflow: hidden\">" +
							/*"<div class=\"param-name\">* Question Text:</div>" +*/
							"<div class=\"param-value\">" +
								"<div>" +
									"<div style=\"display: table-cell; vertical-align: top\">" +
										"<b class=\"label-edit-control-number\" style=\"color: #333\"></b>" +
									"</div>" +
									"<div style=\"display: table-cell;\">" +
										"<textarea name=\"question\" maxlength=\"1000\" class=\"input-content\" autocomplete=\"off\" placeholder=\"Enter your question here.\" style=\"width: 304px; height: 46px;\"></textarea>" +
									"</div>" +
								"</div>" +
								"<div><label class=\"status-content\"></label></div>" +
							"</div>" +
						"</div>" +
						"<div class=\"control-heading\">Question Type</div>" +
						"<div class=\"params\">" +
							"<div class=\"param-value\">" +
								"<div>" +
								"<select class=\"select-question-types\" name=\"question_types\" autocomplete=\"off\">" +
									"<option value=\"2\" input_type_id=\"0\">Multiple Choice (select only one answer)</option>" +
									"<option value=\"2\" input_type_id=\"2\">Multiple Choice (select multiple answers)</option>" +
									"<option value=\"2\" input_type_id=\"1\">Dropdown (choose only one answer)</option>" +
									"<option value=\"3\" input_type_id=\"0\">Single Textbox</option>" +
									"<option value=\"3\" input_type_id=\"1\">Essay Area</option>" +
									/*"<option value=\"3\" input_type_id=\"3\">Numeric</option>" +
									"<option value=\"3\" input_type_id=\"4\">Email</option>" +
									"<option value=\"4\" input_type_id=\"2\">Fields</option>" +*/
									"<option value=\"4\" input_type_id=\"0\">Matrix Choice (only one answer per row)</option>" +
									"<option value=\"4\" input_type_id=\"1\">Matrix Choice (multiple answers per row)</option>" +
									"<option value=\"10\">Rating Scale</option>" +
									"<option value=\"6\" input_type_id=\"0\">Date/Time</option>" +
								"</select>" +
								"</div>" +
							"</div>" +
						"</div>" +
						"<div class=\"params\" style=\"overflow: hidden\">" +
							"<div class=\"param-value ui-form\">" +
								"<div class=\"row-choice\" style=\"margin-top: 0px;\">" +
									"<label><span><input type=\"checkbox\" autocomplete=\"off\" class=\"checkbox-mandatory\"></span>Required question</label>" +
									"<div class=\"container-mandatory\" style=\"padding: 10px 0 0 20px;display: none;\">" +
										"<input type=\"text\" value=\"This question is required\" />" +
									"</div>" +
								"</div>" +
								"<div class=\"row-choice\">" +
									"<label><span><input type=\"checkbox\" autocomplete=\"off\" class=\"checkbox-link\"></span>Add Image/Embed Code</label>" +
									"<div class=\"container-link\" style=\"padding: 10px 0 0 20px;display: none;\">" +
										"<div style=\"padding-bottom: 6px;\">" +
			            					"<select class=\"select-link-type\" autocomplete=\"off\">" +
			            						"<option value=\"1\">Link to Image</option>" +
			            						"<option value=\"2\">Embed Code</option>" +
			            					"</select>" +
			            				"</div>" +
			       						"<div class=\"container-image\" style=\"overflow: hidden;\">" +
											"<input class=\"input-image\" type=\"text\" autocomplete=\"off\" style=\"width: 284px\" />" +
			       						"</div>" +
			       						"<div class=\"container-embed-code\" style=\"display: none; overflow: hidden;\">" +
			       							"<textarea class=\"textarea-embed-code\" style=\"width: 284px; height:64px;\" autocomplete=\"off\"></textarea>" +
			       						"</div>" +
									"</div>" +
								"</div>" +
								"<div class=\"row-choice\">" +
									"<label><span><input type=\"checkbox\" autocomplete=\"off\" class=\"checkbox-include-note\"></span>Help text</label>" +
									"<div class=\"container-note\" style=\"padding: 10px 0 0 20px;display: none;\">" +
										"<textarea class=\"textarea-note\" maxlength=\"1000\" autocomplete=\"off\" placeholder=\"You can enter some information about this question here.\" style=\"width: 284px; height: 46px;\"></textarea>" +
									"</div>" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<div class=\"placeholder-control\"></div>" +
				"</div>" +
				"<div class=\"control-view\">" +
					"<div class=\"view-question\">" +
						"<div class=\"view-question-heading\">" +
							"<span class=\"label-control-number\"></span>" +
							"<span class=\"view-label-mandatory\">*&nbsp;</span>" +
							"<span class=\"view-header-control-content\"></span>" +
						"</div>" +
						"<div class=\"view-question-content\">" +
							"<div class=\"view-label-note\"></div>" +
							"<ul class=\"view-container-control-includes\"></ul>" +
							"<div class=\"view-container-control-other\"></div>" +
		        			"<div class=\"view-container-control-comment\">" +
		        				"<div class=\"q-comment\"><label class=\"view-label-comment\"></label></div>" +
		        				"<textarea autocomplete=\"off\"></textarea>" +
		        			"</div>" +
						"</div>" +
					"</div>" +
					"<div class=\"control-actions\">" +
						"<a href=\"#\" class=\"button-white button-edit-question\" title=\"Edit\"><span>Edit</span></a>" +
						"<span class=\"button-copy-question\"></span>" +
						"<a href=\"#\" class=\"button-delete-question\"><span>Delete</span></a>" +
					"</div>" +
				"</div>" +
			"</div>" +
			"<div class=\"control-insert-question\" style=\"padding: 10px 0\">" +
				"<span class=\"button-insert-question\"></span>" +
			"</div>" +
		"</div>");
		
		
		break;
	}
	return template;
}

function getDemoTemplate(controlTypeId) {
	
	var demoTemplate = null;
	
	switch(controlTypeId) {
	case 11: 
	
		demoTemplate = $("<li class=\"demo-question hidden-question\"></li>");
	
		break;
	case 12:
		
		demoTemplate = $("<li class=\"demo-question\">" +
				"<div class=\"container-control-content demo-question-heading\">" +
					"<span class=\"header-control-content\"></span>" +
				"</div>" +
				"<div class=\"demo-question-content\">" +
					"<div class=\"container-control-notes\">" +
						"<div class=\"label-note q-help\"></div>" +
					"</div>" +
				"</div>" +
			"</li>");
		
		break;
		
	default:
		
		demoTemplate = $("<li class=\"demo-question\">" +
				"<div class=\"container-control-content demo-question-heading\">" +
					"<span class=\"label-control-number\" style=\"" + (useQuestionNumbering ? "" : "display: none") + "\"></span>" +
					"<span class=\"label-mandatory\" style=\"display: none;\">*&nbsp;</span>" +
					"<span class=\"header-control-content\">Enter your question here</span>" +
				"</div>" +
				"<div class=\"demo-question-content\">" +
					"<div class=\"container-control-embed q-image\" style=\"display: none;\"></div>" +
					"<div class=\"container-control-notes\">" +
						"<div class=\"label-note q-help\" style=\"display: none;\">You can enter some information about this question here.</div>" +
					"</div>" +
					"<ul class=\"container-control-includes\"></ul>" +
					"<div class=\"container-control-other\"></div>" +
        			"<div class=\"container-control-comment q-comment-container\" style=\"display: none\">" +
        				"<div class=\"q-comment\"><label class=\"label-comment\">Please help us understand why you selected this answer</label></div>" +
        				"<textarea autocomplete=\"off\"></textarea>" +
        			"</div>" +
				"</div>" +
			"</li>");
		
		break;
	}
	
	return demoTemplate;
	
}
	
jQuery.fn.Acontrol = function () {
    if (arguments.length == 0) return [];
    var X = arguments[0] || {};
    var vControl = $(this);
    var s = null;
    
    var Y = function (p) {
		
		
		// get relevant template for control
        var o = getTemplate(X.controlTypeId);
		
		// reorder events for up and down
		o.find('.button-up')
		.click(function(e){
			
			var current = vControl;
			current
				.prev()
				.before(current);
				
				// call for refresh buttons
				X.onReorder(vControl);
				
				// hightlight control after sorting
				//runEffect(vControl.find(".form-center"));	
			
			e.preventDefault();
		});
		
		o.find('.button-down')
		.click(function(e){
			
			var current = vControl;
			current
				.next()
				.after(current);
				
				// call for refresh buttons
				X.onReorder(vControl);
				
				// hightlight control after sorting
				//runEffect(vControl.find(".form-center"));
			
			e.preventDefault();
		});
		
		
        var s = null;
        var result;
        
        var viewControl = function(data) {
        	
        	if(data.controlTypeId == 2 || data.controlTypeId == 3 || data.controlTypeId == 4) { 
        		o.find(".select-question-types option[value=" + data.controlTypeId + "][input_type_id=" + data.inputTypeId + "]").attr("selected", "selected");
        	} else {
        		o.find(".select-question-types option[value=" + data.controlTypeId + "]").attr("selected", "selected");
        	}
        	
        	
        	var tempId = $.getTimestamp();
			
			if(data.previousState != undefined 
				&& data.previousState == true) {

				
			} else {
        	
        		// content -> question -> change on change question type
        		if(data.control != undefined) {
					
					if(data.control.controlTypeId == 11) {
						
						// view
						o.find('.view-header-control-content').html("[" + data.control.key + "]");
						
						// edit
						o.find('.input-content')
						.val(data.control.key);
						
					} else {
						
						// view
						o.find('.view-header-control-content').html(data.control.content);
						
						// edit
						o.find('.input-content')
						.val(data.control.content)
						.trigger("keyup");
						
						
						if(data.control.controlTypeId != 12) {
							
							// link
							o.find(".select-link-type")
							.unbind('change')
							.change(function() {
			
								if($(this).val() == 1) {
							
									o.find('.container-embed-code').hide();
									o.find('.container-image').show();
							
									if(o.find('.input-image').val() != "") {
							
										s.find('.container-control-embed')
										.empty()
		                    			.html("<img src=" + o.find('.input-image').val() + " />")
		                    			.show();
								
									} else {
								
										s.find('.container-control-embed')
										.empty()
										.hide();
								
									}
							
								}
						
								if($(this).val() == 2) {
							
									o.find('.container-image').hide();
									o.find('.container-embed-code').show();
							
									if(o.find('.textarea-embed-code').val() != "") {
							
										s.find('.container-control-embed')
										.empty()
		                    			.html(o.find('.textarea-embed-code').val())
		                    			.show();
								
									} else {
								
										s.find('.container-control-embed')
										.empty()
										.hide();
								
									}
							
								}
						
							});
					
					
							var linkSelect = o.find(".select-link-type")[0];
							// link
							if(data.control.link != undefined) {
						
								o.find('.checkbox-link').prop("checked", true).trigger("change");
						
		                    	// image
		                    	if(data.control.linkTypeId == 1) {
									linkSelect.selectedIndex = 0;
							
									o.find(".select-link-type").change();
							
									// values
		                    		o.find('.input-image').val(data.control.link);
									o.find('.textarea-embed-code').val("");
							
									s.find('.container-control-embed')
										.empty()
		                    			.html("<img src=" + data.control.link + " />")
		                    			.show();
		                    	}
                    	
		                    	// embed code
		                    	if(data.control.linkTypeId == 2) {
									linkSelect.selectedIndex = 1;
							
									o.find(".select-link-type").change();
							
									// values
									o.find('.input-image').val("");						
									o.find('.textarea-embed-code').val(data.control.link);
							
		                        	s.find('.container-control-embed')
										.empty()
		                    			.html(data.control.link)
		                    			.show();
		                    	}
						
							} else {
						
								o.find('.checkbox-link').prop("checked", false).trigger("change");
						
								linkSelect.selectedIndex = 0;
						
								o.find(".select-link-type").change();
						
								// values
								o.find('.input-image').val("");						
								o.find('.textarea-embed-code').val("");
						
								s.find('.container-control-embed')
									.empty()
									.hide();
						
							}
							
						}
						
						
						
					}
					
					// required
    				if(data.control.isMandatory != undefined) {
						
						// view
						if(data.control.isMandatory == true) {
							o.find('.view-label-mandatory').show();
						} else {
							o.find('.view-label-mandatory').hide();
						}
						
						// edit
						o.find('.checkbox-mandatory').prop("checked", data.control.isMandatory).trigger("change");
						
    				}
					
					// check it ->> fix
					// note
    				if(data.control.note != undefined) {
						
						// view
						o.find('.view-label-note').html(converter.makeHtml(data.control.note));
						
						// edit
    					o.find(".checkbox-include-note").prop("checked", true).trigger("change");
    					o.find('.textarea-note').val(data.control.note).trigger("keyup");
						
    				} else {
    					
						// view
						o.find('.view-label-note').empty();
						
						// edit
    					o.find(".checkbox-include-note").prop("checked", false).trigger("change");
    					o.find('.textarea-note').val("").trigger("keyup");
						
    				}
					
					
					
    			}
        	
			}
        	
        	var placeholderControl = o.find(".placeholder-control");
        	placeholderControl.empty();
        	
        	
        	// controls
        	switch(data.controlTypeId) {
        		case 2: {
					
					
					var viewMode = function() {
						
						// view
						var viewIncludes = o.find('.view-container-control-includes').empty();
						var viewOther = o.find('.view-container-control-other').empty();
						var viewComment = o.find('.view-container-control-comment').hide();
						
						// comment
						if(data.control.comment != undefined) {
							viewComment.find(".view-label-comment").text(data.control.comment);
							viewComment.show();
						}
					
						switch(data.inputTypeId) {
							case 0: {
								
								// radio
								if(data.control.options != undefined) {
									if(data.control.options.list.length != 0) {
										
										var P = null;
										for(var i = 0; i < data.control.options.list.length; i++) {
	                                        if(data.control.options.list[i].optionKindId == 0) {
												var f = $("<li class=\"choice\">" +
													"<label>" +
														"<div style=\"overflow: hidden\" class=\"choice-image\"></div>" +
														"<div class=\"choice-text\">" +
															"<span><input type=\"radio\" name=\"view_" + data.control.controlId + "\" /></span><strong>" + data.control.options.list[i].text + "</strong>" + 
														"</div>" +
													"</label>" +
												"</li>");
	                                        	f.appendTo(viewIncludes);
	                                        }
	                                        if(data.control.options.list[i].optionKindId == 1) {
	                                        	P = data.control.options.list[i];
	                                        }
										}
										
										// other
										if(P != null) {
											
	                                		var g = $("<div class=\"choice-other\">" +
	                                			"<label><span><input type=\"radio\" class=\"radio-other\" name=\"view_" + data.control.controlId + "\" /></span>" + ((P.text != "" && P.text != null) ? P.text : messages.other) + "</label>" +
	                                			"<div class=\"container-other\">" +
	                                				"<input type=\"text\" class=\"text-field\" />" +
	                                			"</div>" +
	                                		"</div>").appendTo(viewOther);
											
	                                        var h = g.find('.radio-other');
	                                        var j = g.find('.text-field');
	                                        h.change(function () {
	                                            if ($(this).is(':checked')) {
	                                                j.focus();
	                                            }
	                                        });
	                                        j.focusin(function () {
	                                            if (!h.is(':checked')) {
	                                                h.prop('checked', true);
	                                            }
	                                        });
											
										}
										
									}
								}
								
								break;
							}
							case 1: {
								
								// dropdown
								if(data.control.options != undefined) { 
									if(data.control.options.list.length != 0) {
										
	                                	var R = null;
										var Y = [];
										
										for(var i = 0; i < data.control.options.list.length; i++) {
											// dropdown label
	                                    	if(data.control.options.list[i].optionKindId == 2) {
	                                    		R = data.control.options.list[i];
	                                    	}
	                                    	if(data.control.options.list[i].optionKindId == 0) {
	                                        	Y.push(data.control.options.list[i]);
	                                    	}
										}
										
										var d = $("<div><select class=\"dropdown-" + data.control.controlId + "\"></select></div>").appendTo(viewOther);
										var q = d.find(".dropdown-" + data.control.controlId)[0].options;
										
										
										// TODO:
	                                    if(R != null) {
	                                    	var k = new Option((R.text != null ? R.text : messages.defaultNoneSelectedOption), 0);
	                                        try {
	                                            q.add(k);
	                                        } catch (ex) {
	                                            q.add(k, null);
	                                        }
	                                    }
										
	                                    if(Y.length != 0) {
	                                    	for(var z = 0; z < Y.length; z++) {
	                                    		var l = new Option(Y[z].text, Y[z].optionId);
	                                            try {
	                                                q.add(l);
	                                            } catch (ex) {
	                                                q.add(l, null);
	                                            }
	                                    	}
	                                    }
										
										
									}
								}
								
								break;
							}
							case 2: {
								
								// checkbox
								if(data.control.options != undefined) {
									if(data.control.options.list.length != 0) {
										
										var P = null;
										for(var i = 0; i < data.control.options.list.length; i++) {
	                                        if(data.control.options.list[i].optionKindId == 0) {
												var f = $("<li class=\"choice\">" +
													"<label>" +
														"<div style=\"overflow: hidden\" class=\"choice-image\"></div>" +
														"<div class=\"choice-text\">" +
															"<span><input type=\"checkbox\" name=\"view_" + data.control.controlId + "\" /></span><strong>" + data.control.options.list[i].text + "</strong>" + 
														"</div>" +
													"</label>" +
												"</li>");
	                                        	f.appendTo(viewIncludes);
	                                        }
	                                        if(data.control.options.list[i].optionKindId == 1) {
	                                        	P = data.control.options.list[i];
	                                        }
										}
										
										// other
										if(P != null) {
											
	                                		var g = $("<div class=\"choice-other\">" +
	                                			"<label><span><input type=\"checkbox\" class=\"checkbox-other\" name=\"view_" + data.control.controlId + "\" /></span>" + ((P.text != "" && P.text != null) ? P.text : messages.other) + "</label>" +
	                                			"<div class=\"container-other\">" +
	                                				"<input type=\"text\" class=\"text-field\" />" +
	                                			"</div>" +
	                                		"</div>").appendTo(viewOther);
											
	                                        var h = g.find('.checkbox-other');
	                                        var j = g.find('.text-field');
	                                        h.change(function () {
	                                            if ($(this).is(':checked')) {
	                                                j.focus();
	                                            }
	                                        });
	                                        j.focusin(function () {
	                                            if (!h.is(':checked')) {
	                                                h.prop('checked', true);
	                                            }
	                                        });
											
										}
										
									}
								}
								
								break;
							}
						}
						
					};
					
					viewMode();
					
        			
        			// choice
					var a = s.find('.container-control-includes');
                        a.empty();
                        
                    var containerControlOther = s.find('.container-control-other');
                    	containerControlOther.empty();
						
						s.find(".container-control-comment").hide();
                    	
                	// new separation 	
                    switch(data.inputTypeId) {
                    	case 0: {
							
                    		// radio
                    		var r = $("<div>" +
	       						"<div style=\"clear: both;\">" +
									"<div class=\"control-heading\">Answer Choices</div>" +
	       							"<ul class=\"options-list\"></ul>" +
	       							"<div style=\"padding: 0 0 6px 20px; clear: both;\">" +
	       								"<a class=\"button-add-option\" title=\"Add Choice\">Add Choice</a>" +
	       								"<a href=\"#\" class=\"button-import-predefined-choices\" title=\"Import Predifined Choices\">Import Predifined Choices</a>" +
	       							"</div>" +
	       						"</div>" +
	       						"<div class=\"params params-other\">" +
	       							/*"<div class=\"param-name\"></div>" +*/
	       							"<div class=\"param-value ui-form\">" +
	       								"<div class=\"row-choice\" style=\"padding-left: 20px;\">" +
	       									"<label><span><input type=\"checkbox\" class=\"checkbox-is-enable-other checkbox-other-option\" autocomplete=\"off\" /></span>Add \"Other\" option</label>" +
	       									"<div style=\"padding: 10px 0 0 20px; display: none;\" class=\"control-container-other\">" +
	       										"<input type=\"text\" class=\"input-other-title\" autocomplete=\"off\" maxlength=\"254\" placeholder=\"Other, (please specify)\" />" +
	       									"</div>" +
	       								"</div>" +
	       							"</div>" +
	       						"</div>" +
								"<div class=\"control-heading\">Comments</div>" +
								"<div class=\"params\">" +
									"<div class=\"param-value ui-form\">" +
										"<div class=\"row-choice\" style=\"margin: 0px\">" +
											"<label><span><input type=\"checkbox\" class=\"checkbox-is-enable-comment\" autocomplete=\"off\" /></span>Allow Comments</label>" +
											"<div style=\"padding: 10px 0 0 20px; display: none;\" class=\"control-container-comments\">" +
												"<textarea style=\"width: 284px; height: 46px;\" placeholder=\"Please help us understand why you selected this answer\" autocomplete=\"off\" maxlength=\"1000\" class=\"textarea-comments\">Please help us understand why you selected this answer</textarea>" +
											"</div>" +
										"</div>" +
									"</div>" +
								"</div>" +
	       						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
	       						"<div class=\"params\">" +
	       							/*"<div class=\"param-name\"></div>" +*/
	       							"<div class=\"param-value\">" +
	       								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
	       							"</div>" +
	       							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
										"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
									"</div>" +
	       						"</div>" +
	       					"</div>").appendTo(placeholderControl);
							
							
							/*
							// tipsy
							r.find(".glossary-tip").tipsy({
								gravity: 's', 
								title: function() { 
									return this.getAttribute('original-title'); 
								}
							});
							*/
	                	   
	                	   var d = r.find('.control-container-other');
	                	   
	                	   var clearIndexes = function() {
	                		   r.find("ul.options-list li").each(function(i, el) {
		                           	$(el).attr("index", i);
	   								s.find("ul.container-control-includes li:eq(" + i + ")").attr("index", i).removeAttr("new_index");
	                           });
	                	   };
	                	   
	                	   function sortView(){
	                		   
	                		   var myList = s.find('ul.container-control-includes');
	                		   var listItems = myList.children('li').get();
	                		   listItems.sort(function(a,b){			
	                			   
	                			   var compA = parseInt($(a).attr("new_index"));
	                			   var compB = parseInt($(b).attr("new_index"));
	                			   
	                			   return ((compA ? compA : 0) - (compB ? compB : 0));

	                		   });
	                		   
	                		   $(myList).append(listItems);
	                		   
	                	   };
	                	   
	                	   var w = r.find('ul.options-list');
	                	   w.sortable({
	                		   placeholder: "options-list-drag",
	                		   handle: ".tab-move-option",
	                		   opacity: 0.7,
	                		   axis: "y",
	                		   cursor: "pointer",
	                		   scroll: true,
	                		   start: function(event, ui) {
	                			   ui.item.startPos = ui.item.index();
	                		   },
	                		   stop: function(event, ui) {
		   	                       
	                			   r.find("ul.options-list li").each(function(i, el) {
	                				   s.find("ul.container-control-includes").find("li[index=" + $(el).attr("index") + "]").attr("new_index", i);
	                			   });
	                			   
	                			   sortView();
	                			   
	                		   }
	                	   });
	                	   
						   
						   var _lastSelected = null;
						   
	                	   var otherPreview = null;
	                	   var otherPreviewText = null;
	                	   
	                	   // other
	                	   otherPreview = $("<div class=\"choice-other\" style=\"display: none\">" +
	                	   		"<label><span><input type=\"radio\" name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"radio-other\" /></span><strong>" + messages.other + "</strong></label>" +
	                	   		"<div class=\"preview-container-other container-other\">" +
	                	   			"<input type=\"text\" class=\"text-other\" />" +
	                	   		"</div>" +
	                	   	"</div>").appendTo(containerControlOther);
	                	   
	                	   var radioOther = otherPreview.find(".radio-other");
                           var textOther = otherPreview.find(".text-other");
                           radioOther.change(function () {
							   
								if(_lastSelected != null && _lastSelected != $(this)) {
									_lastSelected.closest("li").find(".container-additional-details-or-comments").hide();
								}
								_lastSelected = $(this);
							
                               if ($(this).is(":checked")) {
                            	   textOther.focus();
                               }
							   
                           });
                           textOther.focusin(function () {
                               if (!radioOther.is(":checked")) {
                            	   radioOther.prop("checked", true).trigger("change");
                               }
                           });
	                	   
	                	   otherPreviewText = otherPreview.find("strong");
	                	   
	                	   var A = [];
	                	   var D = function (b, c, d, e, f, g, link) {
   								
								var h = $("<li class=\"option\" index=\"" + r.find("ul.options-list li").length + "\">" + 
									"<div class=\"params\">" +
										"<div class=\"tab-move-option\"></div>" +
										"<div class=\"param-value\">" +
											"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-option-text\" placeholder=\"Choice\" />" +
										"</div>" +
										"<div class=\"param-value\"><a class=\"button-add-choice-image\" title=\"Add/Remove Image\"><i class=\"icon-image2\"></i></a></div>" +
										"<div class=\"param-value\">" +
											"<span title=\"Remove\" class=\"button-remove-option\"></span>" +
										"</div>" +
									"</div>" + 
									/*
		                			"<div class=\"row-choice row-additional-details-or-comments\" style=\"padding: 0 0 10px 20px; margin: 0px;\">" +
		                				"<label><span><input type=\"checkbox\" " + ((f != null) ? (f ? "checked=\"checked\"" : "") : "") + " class=\"checkbox-enable-additional-details-or-comments\" /></span>Enable \"Additional details or comments\"</label>" +
		                				"<div class=\"container-additional-details-or-comments\" style=\"padding: 10px 0 0 20px; " + ((f != null) ? (f ? "display: block" : "display: none") : "display: none") + "\">" +
		                					"<input type=\"text\" class=\"input-additional-details-or-comments\" value=\"" + (g != null ? g : messages.defaultAdditionalDetails) + "\" autocomlete=\"off\" />" +
		                				"</div>" +
		                			"</div>" +
									*/
								"</li>");
   								
   								// preview
   								var o = null;
   								
   								
   								// radio
   		                    	o = $("<li index=\"" + s.find('ul.container-control-includes li').length + "\" class=\"choice\">" +
									"<label>" +
										"<div class=\"choice-image\" style=\"overflow: hidden\"></div>" +
										"<div class=\"choice-text\">" +
											"<span><input type=\"radio\" name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" /></span><strong>" + (d != "" ? d : "Choice") + "</strong>" +
										"</div>" +
									"</label>" +
									/*
									"<div class=\"container-additional-details-or-comments\" style=\"display: none\">" +
										"<div class=\"q-label label-additional-comment\">" + (g != null ? g : messages.defaultAdditionalDetails) + "</div>" +
										"<input type=\"text\" class=\"text-additional-comments\" />" +
									"</div>" +
									*/
								"</li>").appendTo(a);
								
								/*
								// trigger for additional details
								o.find("input:radio").change(function() {
									
									if(_lastSelected != null && _lastSelected != $(this)) {
										_lastSelected.closest("li").find(".container-additional-details-or-comments").hide();
									}
									_lastSelected = $(this);
									
									if($(this).prop("checked") && h.find(".checkbox-enable-additional-details-or-comments").prop("checked")) {
										o.find(".container-additional-details-or-comments").show();
										o.find(".text-additional-comments").focus();
									} else {
										o.find(".container-additional-details-or-comments").hide();
									}
								});
   		                    	*/
   		                    	
   			                    if (c != null) {
   			                        h.attr({
   			                            "optionid" : c
   			                        });
   			                    }
   			                    h.appendTo(w);
								
								// set image link
								if(link != undefined) {
									
									
									/*
									// backward compatibility
									var newObj = null;
									
									try {
										newObj = JSON.parse(link);
									} catch(ex) {
										newObj = {
											link : link
										}
									}
									*/
									
									// set data
									h.find(".button-add-choice-image")
									.data("link", link)
									.addClass("has-data");
									
									// make a preview
									
									var img = $("<img src=\"" + link + "\" />");
									
									/*
									if(newObj.x != undefined 
										&& newObj.y != undefined) {
										
										console.log("set cropped");
										
									} else {
										img.width(newObj.width);
										img.height(newObj.height);
									}
									*/
									
									o.find('.choice-image')
									.empty()
									.append(img);
									
								}
								
								// option image
								h.find(".button-add-choice-image")
								.bind("click", function(e) {
									
									var el = $(this);
									
									var I = $("<div>" +
										"<div><input type=\"text\" class=\"text-image-url\" maxlength=\"254\" autocomplete=\"off\" placeholder=\"Image URL\" style=\"width: 225px\" /></div>" +
									"</div>");
							
										var dialog = el.lightDialog({
											message : I,
											actions : [
												{
													label : "Save",
													fire : function() {
														
														var newLink = I.find('.text-image-url').val();
														
														el
														.data("link", newLink)
														.addClass("has-data");
										
														var img = $("<img src=\"" + newLink + "\" />");
														
														/*
														if(newObj.x != undefined 
															&& newObj.y != undefined) {
										
															console.log("set cropped");
										
														} else {
															img.width(newObj.width);
															img.height(newObj.height);
														}
														*/
														
														o.find('.choice-image')
														.empty()
														.append(img);
														
														
														
														// cancel
														dialog.hide()
													}
												},
												{
													label : "Cancel",
													fire : function() {
											
														// cancel
														dialog.hide()
													},
													color : "white"
												}
											],
											complete : function() {
												
												//if(link != undefined) {
												I.find('.text-image-url').val(el.data("link"));
												//}
												
											},
											hideWhenClickOutside : true
										});
									
									/*
									var el = $(this);
									
									addOptionImage(el.data("link"), function(newObj) {
										
										el
										.data("link", newObj)
										.addClass("has-data");
										
										var img = $("<img src=\"" + newObj.link + "\" />");
										if(newObj.x != undefined 
											&& newObj.y != undefined) {
										
											console.log("set cropped");
										
										} else {
											img.width(newObj.width);
											img.height(newObj.height);
										}
									
										o.find('.choice-image')
										.empty()
										.append(img);
										
									});
									*/
									
									e.preventDefault();
									
								});
								
   								// text
   								h.find(".input-option-text")
									/*
									.focusin(function() {
										// highlight element
										highlightElement(vControl.index());
									})
									*/
   									.bind("keyup", function() {
   										o.find("strong").text($(this).val());
   									})
   									.bind("keydown", function(e) {
   										var code = e.keyCode || e.which;
   										if (code == '9') {
   											// if -> h last in list create new option
   											if(r.find('ul.options-list li').length == ($(this).closest("li").index() + 1)) {
   												D(true, null, "", "", false, null);
   												// clear indexes
   												clearIndexes();
   												
   												return false;
   											}
   										}
   									});
   			                        
   								
   			                    if (b) {
   			                    	h.find(".input-option-text").focus();
   			                    }
   			                    
								/*
								// additional details or comments
			                    h.find('.checkbox-enable-additional-details-or-comments')
								.change(function () {
			                        if ($(this).is(':checked')) {
			                            h.find('.container-additional-details-or-comments').show()
			                        } else {
			                            h.find('.container-additional-details-or-comments').hide()
			                        }
			                    });
								
								// additional comments
								h.find('.input-additional-details-or-comments')
								.keyup(function() {
									o.find('.label-additional-comment').text($(this).val());
								});
								*/
								
								// remove
   			                    h.find('.button-remove-option')
   			                     .bind("click", function () {
   			                        if (h.attr('optionid') != undefined) {
   			                            
   			                        	// remove item form preview
   										o.remove();
   										
   										var a = {
   			                                optionId: parseInt(h.attr('optionid')),
   			                                status: "deleted"
   			                            };
   			                            A.push(a);
   			                            
   			                            h.remove();
   			                            
   			                        } else {
   			                        	
   			                        	// remove item from preview
   			                        	o.remove();
   			                            
   										h.remove();
   										
   			                        }
   			                        
   			                        // clear indexes
   		                            clearIndexes();
   			                        
   			                    });
   							};
   							
   							
   							var R = null;
   							
   							var Q = null;
   			                var O = function(b) {   			                	
   			                	Q = b;
   			                	var l = r.find('.input-other-title');
   			                	l.val(((b.text != "" && b.text != null) ? b.text : messages.other)).attr('optionid', b.optionId);
   			                	
   								otherPreviewText.text(l.val());	
   								
   								r.find('.checkbox-is-enable-other').prop("checked", true).trigger("change");
   			                };
	   	                    
   			                // add option
   			                r.find('.button-add-option')
   			                .unbind('click')
   			                .click(function (e) {
   		                        D(true, null, "", "", false, null);
   		                        // clear indexes
   		                        clearIndexes();
   			                
   			                });
   			                
   			                // predefined
   			                r.find('.button-import-predefined-choices').click(function(e) {
 	                		   
   			                	getPredefinedChoices(function(choices) {
   			                		for(var z = 0; z < choices.length; z++) {
   						    			D(false, null, choices[z], choices[z], false, null);
   						    		}
   			                	});
   			                	
 	                		   e.preventDefault();
 	                	   });
   			                
   			                
   			                // other
   							r.find('.checkbox-is-enable-other')
   							.change(function () {
   								if ($(this).is(':checked')) {
   									
   									d.show();
   									otherPreview.show();
   									
   								} else {
   									d.hide();
   									otherPreview.hide();
   								}
   							});
   							
   							// other input
   							r.find(".input-other-title")
   							.val(messages.other)
   							.bind("keyup", function() {
   								otherPreviewText.text($(this).val());
   							});
   			                
	                	   
   							// options
   			                if(data.control != undefined && data.control.options != undefined) {
   				                if(data.control.options.list.length != 0) {
   					                for (var i = 0; i < data.control.options.list.length; i++) {
   					                	
   				                    	if(data.control.options.list[i].optionKindId == 0) {
   				                    		D(false, data.control.options.list[i].optionId, data.control.options.list[i].text, data.control.options.list[i].value, data.control.options.list[i].isEnableAdditionalDetails, data.control.options.list[i].additionalDetailsTitle, data.control.options.list[i].link)
   				                    	}
   				                    	
   				                    	if(data.control.options.list[i].optionKindId == 1) {
   				                    		if(data.inputTypeId == 0 || data.inputTypeId == 2) {
   												O(data.control.options.list[i]);
   											}
   				                    	}
   				                    }
   				                } else {
   				                	for (var i = 0; i < 2; i++) {
   										D(false, null, "", "", false, null)
   			                        }
   				                }
   			                } else {
   			                	for (var i = 0; i < 2; i++) {
   									D(false, null, "", "", false, null)
   		                        }
   			                }
							
							// is enable comments
							r.find(".checkbox-is-enable-comment")
							.change(function() {
								if($(this).prop("checked")) {
									r.find(".control-container-comments").show();
									s.find(".container-control-comment").show();
								} else {
									r.find(".control-container-comments").hide();
									s.find(".container-control-comment").hide();
								}
							});
							
							// keyup -> comments
							r.find('.textarea-comments')
							.unbind("keyup")
							.keyup(function() {
								s.find('.label-comment').html(converter.makeHtml($(this).val()));
							});
   			                
			        		// comments
			        		if(data.control != undefined) {
			    				if(data.control.comment != undefined) {
			    					r.find(".checkbox-is-enable-comment").prop("checked", true).trigger("change");
			    					r.find('.textarea-comments').val(data.control.comment).trigger("keyup");
			    				}
			    			}
   			                
							
							
							
   			                // for save logic
   			                var B = function () {
   			                    var b = [];
   			                    r.find("ul.options-list li").each(function (i, a) {
   			                        if ($(a).attr("optionid") != undefined) {
   			                            b.push(parseInt($(a).attr("optionid")))
   			                        }
   			                    });
   			                    return b;
   			                };
   			                
   			                var C = function(__controlId) {
	   		        			
	   		        			var j = 0;
	   		                    var z = [];
	   		                    
	   		                    r.find("ul.options-list li").each(function (i, a) {
	   		                        if ($(a).attr("optionid") != undefined) {
	   		                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
	   		                            if(b != "") {
											
											var c = false; // $(a).find('.checkbox-enable-additional-details-or-comments').is(':checked');
											var d = null; // (c ? $.removeHTMLTags($(a).find('.input-additional-details-or-comments').val()).replace(/\r/g, "") : null);
											
											var image = $(a).find(".button-add-choice-image");
											
											var e = {
	   		                                	optionId: parseInt($(a).attr("optionid")),
	   		                                	optionKindId : 0,
	   		                                	status: "updated",
												link : image.data("link"), //JSON.parse(image.attr('data-value')),
												linkTypeId : 1, // link type id -> 1 = image
	   		                                	text: b,
	   		                                	value: b,
				                                isEnableAdditionalDetails: c,
				                                additionalDetailsTitle: d
	   		                            	};
											z.push(e);
											
											// increment only if updated
											j++;
											
										} else {
											var e = {
												optionId : parseInt($(a).attr("optionid")),
												status : "deleted"
											}
											z.push(e);
										}
	   		                        } else {
	   		                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
										if(b != "") {
											
				                            var c = false; // $(a).find('.checkbox-enable-additional-details-or-comments').is(':checked');
				                            var d = null // (c ? $.removeHTMLTags($(a).find('.input-additional-details-or-comments').val()).replace(/\r/g, "") : null);
											
											var image = $(a).find(".button-add-choice-image");
											
		   		                            var e = {
		   		                                controlId: (__controlId != undefined ? __controlId : undefined), //result.controlId, // control id
		   		                                optionId: null,
		   		                                optionKindId : 0,
		   		                                status: "added",
												link : image.data("link"), //JSON.parse(image.attr('data-value')),
												linkTypeId : 1, // link type id -> 1 = image
		   		                                text: b,
		   		                                value: b,
				                                isEnableAdditionalDetails: c,
				                                additionalDetailsTitle: d,
		   		                                orderId: i,
		   		                                opinionId: X.opinionId
		   		                            };
		   		                            z.push(e);
											
											// increment only if added
											j++;
										}
	   		                        }
	   		                        //j++;
	   		                    });
	   		                    
	   		                    // other 
	   		                    if(r.find('.checkbox-is-enable-other').is(':checked')) {
	   		                    	
	   		                    	var l = r.find('.input-other-title'); // input-other-title
	   		                    	if(l.attr('optionid') != undefined && Q != null) {
	   		                    		var b = $.removeHTMLTags(l.val()).replace(/\r/g, "");
	   		                        	var e = {
	   		                                optionId: parseInt(l.attr('optionid')),
	   		                                optionKindId: 1,
	   		                                status: "updated",
	   		                                text: b,
	   		                                value: b,
	   		                                isEnableAdditionalDetails: false,
	   		                                additionalDetailsTitle: null,
	   		                                orderId: j
	   		                            };
	   		                            z.push(e);
	   		                    	} else {
	   		                    		var b = $.removeHTMLTags(l.val()).replace(/\r/g, "");
	   		                        	var e = {
	   		                                controlId: (__controlId != undefined ? __controlId : undefined),
	   		                                optionId: null,
	   		                                optionKindId: 1,
	   		                                status: "added",
	   		                                text: b,
	   		                                value: b,
	   		                                isEnableAdditionalDetails: false,
	   		                                additionalDetailsTitle: null,
	   		                                orderId: j,
	   		                                opinionId: X.opinionId
	   		                            };
	   		                            z.push(e);
	   		                    	}
	   		                        
	   		                    } else {
	   		                    	
	   		                    	if(Q != null) {
	   		                        	var l = r.find('.input-other-title');
	   		                        	if(l.attr('optionid') != undefined) {
	   		                        		var a = {
	   		                                    optionId: parseInt(l.attr('optionid')),
	   		                                    status: "deleted"
	   		                                };
	   		                                A.push(a);
	   		                        	}
	   		                    	}
	   		                    	
	   		                    }
	   		                    
	   		                    for (var y = 0; y < A.length; y++) {
	   		                        z.push(A[y]);
	   		                    }
	   		                    
	   		                    return z;
	   		                    
	   		        		};
	   		        		
	   		        		// update options for option id
	   		        		var G = function() {
	   		        			r.find("ul.options-list li").each(function (i, a) {
	   		        				$(a).attr({ "optionid" : result.options.list[i].optionId });
	   		        			});
	   		        		};
	   		        		
	   		        		// save
	   		        		var v = null;
	   		        		v = new validator({
								elements : [
									{
										element : o.find('.input-content'),
										status : o.find('.status-content'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									if(vControl.attr('controlid') != undefined) {
										
										if(result.controlTypeId != data.controlTypeId) {
										
											
											delete __obj; __obj = { };
										
											__obj.orderId = vControl.attr("orderid");
											__obj.controlTypeId = data.controlTypeId; // multiple choice
											__obj.inputTypeId = data.inputTypeId; // 0 radio
											__obj.content = o.find('.input-content').val();
											__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			__obj.linkTypeId = 1;
			                                			__obj.link = o.find('.input-image').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			__obj.linkTypeId = 2;
			                                			__obj.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                } else {
			                                	__obj.linkTypeId = 0;
			                        			__obj.link = null;
			                                }
										
											__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
			                                __obj.comment = (o.find('.checkbox-is-enable-comment').prop('checked') && o.find('.textarea-comments').val() != "" ? o.find('.textarea-comments').val() : undefined);
										
				   		    				__obj.options = {
				   		                        list: C(), // no controlId
				   		                        reorder: B()
				   		                    };
											
											
							        		deleteControl({
							        			accountId : accountId,
							        			controlId : result.controlId,
							        			success : function() {
        				
													X.onCreate({
														control : __obj, // result,
														success : function(newData) {									
						   		    						
															result = newData;
															
															// merge data -> result
						   		    						//$.extend(result, newData);
															
															// update ids and types
															vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
			   		    						
							   		        				viewControl({
								   		     					controlTypeId : result.controlTypeId,
								   		     					inputTypeId : result.inputTypeId,
								   		     					control : result
								   		     				});
			   		    						
						   		    						o.find('.control-edit').hide();
						   		    			        	o.find('.control-view').show();
															
															
														},
														error: function() {
															console.log("ERRRRRRRR");
														}
													});
        				
							        			},
							        			error: function(error) {

							        			}
							        		});
										
										} else {
											
											//opinionId = X.opinionId;
											result.orderId = vControl.attr("orderid");
											//parentId = X.currentSheetId;
											//parentTypeId = 2; // 1 poll, sheet 2, control 3
											result.controlTypeId = data.controlTypeId; // multiple choice
											result.inputTypeId = data.inputTypeId; // 0 radio
										
											result.content = o.find('.input-content').val();
											result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			result.linkTypeId = 1;
			                                			result.link = o.find('.input-image').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			result.linkTypeId = 2;
			                                			result.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                } else {
			                                	result.linkTypeId = 0;
			                        			result.link = null;
			                                }
										
										
											result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
			                                result.comment = (o.find('.checkbox-is-enable-comment').prop('checked') && o.find('.textarea-comments').val() != "" ? o.find('.textarea-comments').val() : undefined);
										
											// options
				   		    				result.options = {
				   		                        list: C(result.controlId),
				   		                        reorder: B()
				   		                    };
				   		    				
				   		    				result.accountId = accountId;
			   		    				
											// update
				   		    				updateControlDetails({
				   		    					control : result,
				   		    					success : function(newData) {
			   		    						
				   		    						// merge data -> result
				   		    						$.extend(result, newData.result);
			   		    						
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
			   		    						
				   		    						o.find('.control-edit').hide();
				   		    			        	o.find('.control-view').show();
			   		    			        	
				   		    					},
				   		    					error: function() {
				   		    						alert("ERR");
				   		    					}
				   		    				});
											
										}
										
									}
									
								}
	   		        		});
   							
	   		        		// cancel
	   		        		r.find('.button-cancel').click(function(event) {
	   		        			
	   		        			if(result != undefined) {
	   		        				
	   		        				// close to view mode
	   		        				
	   		        				viewControl({
		   		     					controlTypeId : result.controlTypeId,
		   		     					inputTypeId : result.inputTypeId,
		   		     					control : result
		   		     				});
	   		        				
	   		        				o.find('.control-edit').hide();
	   		        	        	o.find('.control-view').show();
	   		        				
	   		        			} else {
	   		        				
	   		        				vControl.remove();
	   		        				// demo
	   		        				s.remove();
	   		        				
	   		        				
	   		        				X.onDelete();
	   		        				
	   		        				/*
	   		        				if($("ul.questions li.question").length == 0) {
	   		        					$("#empty").show();
	   		        					$("#questions").hide();
	   		        				}
	   		        				*/
	   		        				
	   		        			}
	   		        			
	   		        			event.preventDefault();
	   		        		});
   							
	                	   
 	                	   break;
 	                   }
                    	case 1: {
                    		
                    		// dropdown
 	                	   
                    		var r = $("<div>" +
 	       						"<div style=\"clear: both;\">" +
        							"<div class=\"container-default-non-selected-option-text\" style=\"padding-left:20px; padding-bottom: 6px; min-height: 22px;\">" +
        								"<input type=\"text\" autocomlete=\"off\" class=\"input-default-non-selected-option-text\" placeholder=\"Dropdown label...\" />" +
        							"</div>" +
									"<div class=\"control-heading\">Answer Choices</div>" +
 	       							"<ul class=\"options-list\"></ul>" +
 	       							"<div style=\"padding: 0 0 12px 20px; clear: both;\">" +
 	       								"<a class=\"button-add-option\" title=\"Add Choice\">Add Choice</a>" +
 	       								"<a href=\"#\" class=\"button-import-predefined-choices\" title=\"Import Predifined Choices\">Import Predifined Choices</a>" +
 	       							"</div>" +
 	       						"</div>" +
								"<div class=\"control-heading\">Comments</div>" +
								"<div class=\"params\">" +
									"<div class=\"param-value ui-form\">" +
										"<div class=\"row-choice\" style=\"margin: 0px\">" +
											"<label><span><input type=\"checkbox\" class=\"checkbox-is-enable-comment\" autocomplete=\"off\" /></span>Allow Comments</label>" +
											"<div style=\"padding: 10px 0 0 20px; display: none;\" class=\"control-container-comments\">" +
												"<textarea style=\"width: 284px; height: 46px;\" placeholder=\"Please help us understand why you selected this answer\" autocomplete=\"off\" maxlength=\"1000\" class=\"textarea-comments\">Please help us understand why you selected this answer</textarea>" +
											"</div>" +
										"</div>" +
									"</div>" +
								"</div>" +
 	       						"<div style=\"height: 20px; overflow: hidden;clear: both;\"></div>" +
 	       						"<div class=\"params\">" +
 	       							/*"<div class=\"param-name\"></div>" +*/
		 	       					"<div class=\"param-value\">" +
										"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
									"</div>" +
									"<div class=\"param-value\" style=\"line-height: 20px;\">" +
										"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
									"</div>" +
								"</div>" +
							"</div>").appendTo(placeholderControl);
 	                	   
 	                	   var S = r.find('.input-default-non-selected-option-text');
 	                	   
 	                	   var updatePreview = function() {
 	                		 
	                		   a.empty();
	                		   
	                		   // select for dropdown
	                		   var select = null;
		                	   select = $("<select/>").appendTo(a);
	                		   
	                		   var e = select[0].options;
	                		   
	                		   var k = new Option(S.val());
	                		   
	                		   try {
	                			   e.add(k);
	                		   } catch (ex) {
	                			   e.add(k, null)
	                		   }
	                		   
	                		   // update items
	                		   r.find('ul.options-list li').each(function(i, el) {
		                           
	                			   var optionValue = new Option($(el).find("input").val());
	                			   try {
		                			   e.add(optionValue);
		                		   } catch (ex) {
		                			   e.add(optionValue, null)
		                		   }
		                		   
	                		   });
	                		   
	                	   };
	                	   
	                	   var clearIndexes = function() {
		   						r.find('ul.options-list li').each(function(i, el) {
		                           	$(el).attr("index", i);
		   						});
		   						updatePreview();
	                	   };
	                	   
	                	   var w = r.find('ul.options-list');
	                	   w.sortable({
	                		   placeholder: "options-list-drag",
	                		   handle: ".tab-move-option",
	                		   opacity: 0.7,
	                		   axis: "y",
	                		   cursor: "pointer",
	                		   scroll: true,
	                		   start: function(event, ui) {
	                			   ui.item.startPos = ui.item.index();
	                		   },
	                		   stop: function(event, ui) {
	                			   updatePreview();
	                		   }
		   	               });
	                	   
	                	   var A = [];
	                	   var D = function (b, c, d, e, f, g) {
	                		   
		   						var h = $("<li class=\"option\" index=\"" + r.find("ul.options-list li").length + "\">" + 
		   	                    	"<div class=\"params\">" +
		   	                    		"<div class=\"tab-move-option\"></div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-option-text\" placeholder=\"Choice\" />" +
		   	                    		"</div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<span title=\"Remove\" class=\"button-remove-option\"></span>" +
		   	                    		"</div>" +
		   	                    	"</div>" + 
		   	                    "</li>");
		   						
		   	                    if (c != null) {
		   	                        h.attr({
		   	                            "optionid" : c
		   	                        });
		   	                    }
		   	                    h.appendTo(w);
		   						
		   						h.find(".input-option-text")
	   							.bind("keyup", function() {
	   								updatePreview();
	   							})
	   							.bind("keydown", function(e) {
	   								var code = e.keyCode || e.which;
	   								if (code == '9') {
	   									// if -> h last in list create new option
	   									if(r.find('ul.options-list li').length == ($(this).closest("li").index() + 1)) {
	   										D(true, null, "", "", false, null);
	   										// clear indexes
	   										clearIndexes();
	   										
	   										return false;
	   									}
	   								}
	   							});
		   						
		   						if (b) {
		   	                    	h.find(".input-option-text").focus();
		   	                    }
		   	                    
		   	                    h.find('.button-remove-option')
		   	                     .bind("click", function () {
		   	                        if (h.attr('optionid') != undefined) {
		   	                            
		   								var a = {
		   	                                optionId: parseInt(h.attr('optionid')),
		   	                                status: "deleted"
		   	                            };
		   	                            A.push(a);
		   	                            
		   	                            h.remove();
		   	                            
		   	                        } else {
		   								
		   								h.remove();
		   								
		   	                        }
		   	                        
		   	                        // clear indexes
		   	                        clearIndexes();
		   	                        
		   	                     });
	                	   };
	                	   
	                	   var R = null;
	                	   var W = function(b) {
	                		   
	                		   R = b;
	                		   S.val(((b.text != "" && b.text != null) ? b.text : ""));
	                		   S.attr('optionid', b.optionId);
	                		   
	                	   };
	                	   
	                	   // add option
	                	   r.find('.button-add-option')
	                	   .unbind('click')
	                	   .click(function (e) {
	                		   D(true, null, "", "", false, null);
	                		   // clear indexes
	                		   clearIndexes();
	                		   
	                	   });
	                	   
	                	   // predefined
	                	   r.find('.button-import-predefined-choices').click(function(e) {
	                		   
	                		   	getPredefinedChoices(function(choices) {
  			                		for(var z = 0; z < choices.length; z++) {
  						    			D(false, null, choices[z], choices[z], false, null);
  						    		}
  			                		
  			                		// for test only
  			                		updatePreview();
  			                	});
	                		   
	                		   e.preventDefault();
	                	   });
	                	   
	                	   // options
	                	   if(data.control != undefined && data.control.options != undefined) {
	                		   if(data.control.options.list.length != 0) {
	                			   for (var i = 0; i < data.control.options.list.length; i++) {
	                				   if(data.control.options.list[i].optionKindId == 2) {
	                					   W(data.control.options.list[i]);
	                				   }
	                				   
	                				   if(data.control.options.list[i].optionKindId == 0) {
	                					   D(false, data.control.options.list[i].optionId, data.control.options.list[i].text, data.control.options.list[i].value, false, null)
	                				   }
	                			   }
	                		   } else {
	                			   for (var i = 0; i < 2; i++) {
	                				   D(false, null, "", "", false, null)
	                			   }
	                		   }
	                	   } else {
	                		   for (var i = 0; i < 2; i++) {
	                			   D(false, null, "", "", false, null)
	                		   }
	                	   }
						   
						
							// is enable comments
							r.find(".checkbox-is-enable-comment")
							.change(function() {
								if($(this).prop("checked")) {
									r.find(".control-container-comments").show();
									s.find(".container-control-comment").show();
								} else {
									r.find(".control-container-comments").hide();
									s.find(".container-control-comment").hide();
								}
							});
						
							// keyup -> comments
							r.find('.textarea-comments')
							.unbind("keyup")
							.keyup(function() {
								s.find('.label-comment').html(converter.makeHtml($(this).val()));
							});
  			                
		        			// comments
		        			if(data.control != undefined) {
		    					if(data.control.comment != undefined) {
		    						r.find(".checkbox-is-enable-comment").prop("checked", true).trigger("change");
		    						r.find('.textarea-comments').val(data.control.comment).trigger("keyup");
		    					}
		    				}
	                	   
	                	   // for test only
	                	   updatePreview();
	                	   
	                	   // for save logic
	                	   var B = function () {
	                		   var b = [];
	                		   r.find("ul.options-list li").each(function (i, a) {
	                			   if ($(a).attr("optionid") != undefined) {
	                				   b.push(parseInt($(a).attr("optionid")))
	                			   }
	                		   });
	                		   return b;
	                	   };
	                	   
	                	   S.bind("keyup", function() {
	                		   updatePreview();
	                	   });
 	                	   
	                	   var C = function(__controlId) {
		   	        			
	                		   var j = 0;
	                		   var z = [];
	   	                    
	                		   r.find("ul.options-list li").each(function (i, a) {
	                			   if ($(a).attr("optionid") != undefined) {
	                				   var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
									   if(b != "") {
											var e = {
      	                            			optionId: parseInt($(a).attr("optionid")),
      	                            			optionKindId : 0,
      	                            			status: "updated",
      	                            			text: b,
      	                            			value: b,
      	                            			isEnableAdditionalDetails: false,
      	                            			additionalDetailsTitle: null
                   				   			};
											z.push(e);
											
											// increment only if updated
											j++;
											
									   } else {
										   var e = {
											   optionId : parseInt($(a).attr("optionid")),
											   status : "deleted"
										   }
										   z.push(e);
									   }
	                			   } else {
	                				   var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
	                				   if(b != "") {
										   var e = {
											   controlId: (__controlId != undefined ? __controlId : undefined),
											   optionId: null,
											   optionKindId : 0,
											   status: "added",
											   text: b,
											   value: b,
											   isEnableAdditionalDetails: false,
											   additionalDetailsTitle: null,
											   orderId: i,
											   opinionId: X.opinionId
		                				   };
		                				   z.push(e);
										   
										   // increment only if added
										   j++;
	                				   }
	                			   }
	                			   //j++;
	                		   });
	   	                    
                			   var S = r.find('.input-default-non-selected-option-text');
                			   if(S.attr('optionid') != undefined && R != null) {
                				   var b = $.removeHTMLTags(S.val()).replace(/\r/g, "");
                				   var e = {
                						   optionId: parseInt(S.attr('optionid')),
                						   optionKindId: 2,
                						   status: "updated",
                						   text: b,
                						   value: b,
                						   isEnableAdditionalDetails: false,
                						   additionalDetailsTitle: null,
                						   orderId : 0
                				   };
                				   z.push(e);
                			   } else {
                				   var b = $.removeHTMLTags(S.val()).replace(/\r/g, "");
                				   var e = {
                						   controlId: (__controlId != undefined ? __controlId : undefined),
                						   optionId: null,
                						   optionKindId: 2,
                						   status: "added",
                						   text: b,
                						   value: b,
                						   isEnableAdditionalDetails: false,
                						   additionalDetailsTitle: null,
                						   orderId : 0,
                						   opinionId: X.opinionId
                				   };
                				   z.push(e);
                			   }
	   	                    
	                		   for (var y = 0; y < A.length; y++) {
	   	                    		z.push(A[y]);
	                		   }
	                		   
	                		   return z; 
	                	   };
	                	   
	                	   // update options for option id
	                	   var G = function() {
	                		   r.find("ul.options-list li").each(function (i, a) {
	                			   $(a).attr({ "optionid" : result.options.list[i].optionId });
	                		   });
	                	   };
	                	   
	                	   
	                	   var v = null;
	   		        		v = new validator({
								elements : [
									{
										element : o.find('.input-content'),
										status : o.find('.status-content'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									if(vControl.attr('controlid') != undefined) {
										
										if(result.controlTypeId != data.controlTypeId) {
											
											
											delete __obj; __obj = { };
											
											__obj.orderId = vControl.attr("orderid");
											__obj.controlTypeId = data.controlTypeId; // multiple choice
											__obj.inputTypeId = data.inputTypeId; // 1 dropdown
											__obj.content = o.find('.input-content').val();
											__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			__obj.linkTypeId = 1;
			                                			__obj.link = o.find('.input-image').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			__obj.linkTypeId = 2;
			                                			__obj.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                } else {
			                                	__obj.linkTypeId = 0;
			                        			__obj.link = null;
			                                }
											
											__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
											__obj.comment = (o.find('.checkbox-is-enable-comment').prop('checked') && o.find('.textarea-comments').val() != "" ? o.find('.textarea-comments').val() : undefined);
											
											__obj.options = {
												list : C(),
												reorder : B()
											};
											
											
							        		deleteControl({
							        			accountId : accountId,
							        			controlId : result.controlId,
							        			success : function() {
        				
													X.onCreate({
														control : __obj, //result,
														success : function(newData) {									
						   		    						
															result = newData;
															
															// merge data -> result
						   		    						//$.extend(result, newData);
															
															// update ids and types
															vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
			   		    						
							   		        				viewControl({
								   		     					controlTypeId : result.controlTypeId,
								   		     					inputTypeId : result.inputTypeId,
								   		     					control : result
								   		     				});
			   		    						
						   		    						o.find('.control-edit').hide();
						   		    			        	o.find('.control-view').show();
															
															
														},
														error: function() {
															console.log("ERRRRRRRR");
														}
													});
        				
							        			},
							        			error: function(error) {

							        			}
							        		});
										
										} else {
											
											result.orderId = vControl.attr("orderid");
										
											result.controlTypeId = data.controlTypeId; // multiple choice
											result.inputTypeId = data.inputTypeId; // 1 dropdown
										
											result.content = o.find('.input-content').val();
											result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			result.linkTypeId = 1;
			                                			result.link = o.find('.input-image').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			result.linkTypeId = 2;
			                                			result.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                } else {
			                                	result.linkTypeId = 0;
			                        			result.link = null;
			                                }
										
											result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
											result.comment = (o.find('.checkbox-is-enable-comment').prop('checked') && o.find('.textarea-comments').val() != "" ? o.find('.textarea-comments').val() : undefined);
										
											// options
											result.options = {
												list : C(result.controlId),
												reorder : B()
											};
										
											result.accountId = accountId;
										
											// update
											updateControlDetails({
												control : result,
												success : function(newData) {
			
													// merge data -> result
													$.extend(result, newData.result);
			
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
			
													o.find('.control-edit').hide();
										        	o.find('.control-view').show();
        	
												},
												error: function() {
													alert("ERR");
												}
											});
											
										}
										
									}
									
								}
	   		        		});
	                	   
	   		        		// cancel
	   		        		r.find('.button-cancel').click(function(event) {
	   		        			
	   		        			if(result != undefined) {
	   		        				
	   		        				// close to view mode
	   		        				
	   		        				viewControl({
		   		     					controlTypeId : result.controlTypeId,
		   		     					inputTypeId : result.inputTypeId,
		   		     					control : result
		   		     				});
	   		        				
	   		        				o.find('.control-edit').hide();
	   		        	        	o.find('.control-view').show();
	   		        				
	   		        			} else {
	   		        				
	   		        				vControl.remove();
	   		        				// demo
	   		        				s.remove();
	   		        				
	   		        				X.onDelete();
	   		        				
	   		        				/*
	   		        				if($("ul.questions li.question").length == 0) {
	   		        					$("#empty").show();
	   		        					$("#questions").hide();
	   		        				}
	   		        				*/
	   		        				
	   		        			}
	   		        			
	   		        			event.preventDefault();
	   		        		});
                    		
                    		
 	                	   break;
                    	}
                    	case 2 : {
                    		
                    		// checkbox
                    		var r = $("<div>" +
	       						"<div style=\"clear: both;\">" +
									"<div class=\"control-heading\">Answer Choices</div>" +
	       							"<ul class=\"options-list\"></ul>" +
	       							"<div style=\"padding: 0 0 6px 20px; clear: both;\">" +
	       								"<a class=\"button-add-option\" title=\"Add Choice\">Add Choice</a>" +
	       								"<a href=\"#\" class=\"button-import-predefined-choices\" title=\"Import Predifined Choices\">Import Predifined Choices</a>" +
	       							"</div>" +
	       						"</div>" +
	       						"<div class=\"params\" id=\"params_other\">" +
	       							/*"<div class=\"param-name\"></div>" +*/
	       							"<div class=\"param-value ui-form\">" +
	       								"<div class=\"row-choice\" style=\"padding-left: 20px;\">" +
	       									"<label><span><input type=\"checkbox\" id=\"checkbox_other_option\" class=\"checkbox-is-enable-other\" autocomplete=\"off\" /></span>Add \"Other\" option</label>" +
	       									"<div style=\"padding: 10px 0 0 20px; display: none;\" class=\"control-container-other\">" +
	       										"<input type=\"text\" class=\"input-other-title\" autocomplete=\"off\" maxlength=\"254\" placeholder=\"Other, (please specify)\" />" +
	       									"</div>" +
	       								"</div>" +
	       							"</div>" +
	       						"</div>" +
								"<div class=\"control-heading\">Comments</div>" +
								"<div class=\"params\">" +
									"<div class=\"param-value ui-form\">" +
										"<div class=\"row-choice\" style=\"margin: 0px\">" +
											"<label><span><input type=\"checkbox\" class=\"checkbox-is-enable-comment\" autocomplete=\"off\" /></span>Allow Comments</label>" +
											"<div style=\"padding: 10px 0 0 20px; display: none;\" class=\"control-container-comments\">" +
												"<textarea style=\"width: 284px; height: 46px;\" placeholder=\"Please help us understand why you selected this answer\" autocomplete=\"off\" maxlength=\"1000\" class=\"textarea-comments\">Please help us understand why you selected this answer</textarea>" +
											"</div>" +
										"</div>" +
									"</div>" +
								"</div>" +
	       						"<div style=\"height: 20px; overflow: hidden;clear: both;\"></div>" +
	       						"<div class=\"params\">" +
		       						/*"<div class=\"param-name\"></div>" +*/
		 	       					"<div class=\"param-value\">" +
										"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
									"</div>" +
									"<div class=\"param-value\" style=\"line-height: 20px;\">" +
										"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
									"</div>" +
								"</div>" +
							"</div>").appendTo(placeholderControl);
	                	   
	                	   var d = r.find('.control-container-other');
	                	   
	                	   var clearIndexes = function() {
	                		   r.find('ul.options-list li').each(function(i, el) {
	                			   $(el).attr("index", i);
	                			   s.find("ul.container-control-includes li:eq(" + i + ")").attr("index", i).removeAttr("new_index");
	                           });
	                	   };
                    	
	                	   function sortView(){
	                		   
	                		   var myList = s.find('ul.container-control-includes');
	                		   var listItems = myList.children('li').get();
	                		   listItems.sort(function(a,b){
	                			   
	                			   var compA = $(a).attr("new_index");
	                			   var compB = $(b).attr("new_index");
	                			   
	                			   return ((compA ? compA : 0) - (compB ? compB : 0));
	                			   
	                		   });
	                		   $(myList).append(listItems);
	                   	    
	                	   };
	                	   
	                	   var w = r.find('ul.options-list');
	                	   w.sortable({
	                		   placeholder: "options-list-drag",
	                		   handle: ".tab-move-option",
	                		   opacity: 0.7,
	                		   axis: "y",
	                		   cursor: "pointer",
	                		   scroll: true,
	                		   start: function(event, ui) {
	                			   ui.item.startPos = ui.item.index();
	                		   },
	                		   stop: function(event, ui) {
	                			   r.find('ul.options-list li').each(function(i, el) {
	                				   s.find("ul.container-control-includes").find("li[index=" + $(el).attr("index") + "]").attr("new_index", i);
	                			   });
	   	                        
	                			   sortView();
	                			   
	                		   }
	                	   });
                    		
							
	                	   var otherPreview = null;
	                	   var otherPreviewText = null;
	                	   
	                	   // other
	                	   otherPreview = $("<div class=\"choice-other\" style=\"display: none\">" +
	                	   		"<label><span><input type=\"checkbox\" class=\"checkbox-other\" /></span><strong>" + messages.other + "</strong></label>" +
	                	   		"<div class=\"preview-container-other container-other\">" +
                	   				"<input type=\"text\" class=\"text-other\" />" +
                	   			"</div>" +
	                	   	"</div>").appendTo(containerControlOther);
	                	   
	                	   var checkboxOther = otherPreview.find(".checkbox-other");
                           var textOther = otherPreview.find(".text-other");
                           checkboxOther.change(function () {
                               if ($(this).is(":checked")) {
                            	   textOther.focus();
                               }
                           });
                           textOther.focusin(function () {
                               if (!checkboxOther.is(":checked")) {
                            	   checkboxOther.prop("checked", true);
                               }
                           });
	                	   
	                	   otherPreviewText = otherPreview.find("strong");
                    		
	                	   var A = [];
	                	   var D = function (b, c, d, e, f, g, link) {
	                		   
	                		   var h = $("<li class=\"option\" index=\"" + r.find("ul.options-list li").length + "\">" + 
		   	                    	"<div class=\"params\">" +
		   	                    		"<div class=\"tab-move-option\"></div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-option-text\" placeholder=\"Choice\" />" +
		   	                    		"</div>" +
										"<div class=\"param-value\"><a class=\"button-add-choice-image\" title=\"Add/Remove Image\"><i class=\"icon-image2\"></i></a></div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<span title=\"Remove\" class=\"button-remove-option\"></span>" +
		   	                    		"</div>" +
		   	                    	"</div>" + 
		                			"<div class=\"row-choice row-additional-details-or-comments\" style=\"padding: 0 0 10px 20px; margin: 0px;\">" +
		                				"<label><span><input type=\"checkbox\" " + ((f != null) ? (f ? "checked=\"checked\"" : "") : "") + " class=\"checkbox-enable-additional-details-or-comments\" /></span>Enable \"Additional details or comments\"</label>" +
		                				"<div class=\"container-additional-details-or-comments\" style=\"padding: 10px 0 0 20px; " + ((f != null) ? (f ? "display: block" : "display: none") : "display: none") + "\">" +
		                					"<input type=\"text\" class=\"input-additional-details-or-comments\" value=\"" + (g != null ? g : messages.defaultAdditionalDetails) + "\" autocomlete=\"off\" />" +
		                				"</div>" +
		                			"</div>" +
		   	                    "</li>");
	   						
		   						// preview
		   						var o = null;
		   						
		                       	// checkbox
		   						o = $("<li index=\"" + s.find('ul.container-control-includes li').length + "\" class=\"choice\">" +
									"<label>" +
										"<div class=\"choice-image\" style=\"overflow: hidden;\"></div>" +
										"<div class=\"choice-text\">" +
											"<span><input type=\"checkbox\"/></span><strong>" + (d != "" ? d : "Choice") + "</strong>" +
										"</div>" +
									"</label>" +
									"<div class=\"container-additional-details-or-comments\" style=\"display: none\">" +
										"<div class=\"q-label label-additional-comment\">" + (g != null ? g : messages.defaultAdditionalDetails) + "</div>" +
										"<input type=\"text\" class=\"text-additional-comments\" />" +
									"</div>" +
								"</li>").appendTo(a);
								
								// trigger for additional details
								o.find("input:checkbox").change(function() {
									if($(this).prop("checked") && h.find(".checkbox-enable-additional-details-or-comments").prop("checked")) {
										o.find(".container-additional-details-or-comments").show();
										o.find(".text-additional-comments").focus();
									} else {
										o.find(".container-additional-details-or-comments").hide();
									}
								});
								
								
								
		                       	
		   	                    if (c != null) {
		   	                        h.attr({
		   	                            "optionid" : c
		   	                        });
		   	                    }
		   	                    h.appendTo(w);
								
								
								// set image link
								if(link != undefined) {
									
									/*
									// backward compatibility
									var newObj = null;
									
									try {
										newObj = JSON.parse(link);
									} catch(ex) {
										newObj = {
											link : link
										}
									}
									*/
									
									// set data
									h.find(".button-add-choice-image")
									.data("link", link)
									.addClass("has-data");
									
									// make a preview
									var img = $("<img src=\"" + link + "\" />");
									/*
									if(newObj.x != undefined 
										&& newObj.y != undefined) {
										
										console.log("set cropped");
										
									} else {
										img.width(newObj.width);
										img.height(newObj.height);
									}
									*/
									o.find('.choice-image')
									.empty()
									.append(img);
									
								}
		   						
								// option image
								h.find(".button-add-choice-image")
								.bind("click", function(e) {
									
									var el = $(this);
									
									var I = $("<div>" +
										"<div><input type=\"text\" class=\"text-image-url\" maxlength=\"254\" autocomplete=\"off\" placeholder=\"Image URL\" style=\"width: 225px\" /></div>" +
									"</div>");
							
										var dialog = el.lightDialog({
											message : I,
											actions : [
												{
													label : "Save",
													fire : function() {
														
														var newLink = I.find('.text-image-url').val();
														
														el
														.data("link", newLink)
														.addClass("has-data");
										
														var img = $("<img src=\"" + newLink + "\" />");
														
														/*
														if(newObj.x != undefined 
															&& newObj.y != undefined) {
										
															console.log("set cropped");
										
														} else {
															img.width(newObj.width);
															img.height(newObj.height);
														}
														*/
														
														o.find('.choice-image')
														.empty()
														.append(img);
														
														
														
														// cancel
														dialog.hide()
													}
												},
												{
													label : "Cancel",
													fire : function() {
											
														// cancel
														dialog.hide()
													},
													color : "white"
												}
											],
											complete : function() {
												
												//if(link != undefined) {
												I.find('.text-image-url').val(el.data("link"));
												//}
												
											},
											hideWhenClickOutside: true
										});
									
									/*
									var el = $(this);
									
									addOptionImage(el.data("link"), function(newObj) {
										
										el
										.data("link", newObj) // store new data
										.addClass("has-data");
										
										var img = $("<img src=\"" + newObj.link + "\" />");
										if(newObj.x != undefined 
											&& newObj.y != undefined) {
										
											console.log("set cropped");
										
										} else {
											img.width(newObj.width);
											img.height(newObj.height);
										}
									
										o.find('.choice-image')
										.empty()
										.append(img);
										
									});
									*/
									
									e.preventDefault();
									
								});
		   	                    
		   						h.find(".input-option-text")
		   							.bind("keyup", function() {
		   								o.find("strong").text($(this).val());
		   							})
		   							.bind("keydown", function(e) {
		   								var code = e.keyCode || e.which;
		   								if (code == '9') {
		   									// if -> h last in list create new option
		   									if(r.find('ul.options-list li').length == ($(this).closest("li").index() + 1)) {
		   										D(true, null, "", "", false, null);
		   										// clear indexes
		   										clearIndexes();
		   										
		   										return false;
		   									}
		   								}
		   							});
		   	                        
		   						
		   	                    if (b) {
		   	                    	h.find(".input-option-text").focus();
		   	                    }
		   	                    
								// additional details or comments
			                    h.find('.checkbox-enable-additional-details-or-comments')
								.change(function () {
			                        if ($(this).is(':checked')) {
			                            h.find('.container-additional-details-or-comments').show()
			                        } else {
			                            h.find('.container-additional-details-or-comments').hide()
			                        }
			                    });
								
								// additional comments
								h.find('.input-additional-details-or-comments')
								.keyup(function() {
									o.find('.label-additional-comment').text($(this).val());
								});
								
								// remove
		   	                    h.find('.button-remove-option')
		   	                     .bind("click", function () {
		   	                        if (h.attr('optionid') != undefined) {
		   	                            
		   								// checkbox
		   								
		   								o.remove();
		   								
		   								var a = {
		   	                                optionId: parseInt(h.attr('optionid')),
		   	                                status: "deleted"
		   	                            };
		   	                            A.push(a);
		   	                            
		   	                            h.remove();
		   	                            
		   	                        } else {
		   	                        	
		   								// checkbox
		   								
		   								o.remove();
		   								
		   								h.remove();
		   								
		   	                        }
		   	                        
		   	                        // clear indexes
		   	                        clearIndexes();
		   	                        
		   	                    });
		   	                };
		   	                
		   	                var R = null;
							var Q = null;
			                var O = function(b) {
			                	Q = b;
			                	var l = r.find('.input-other-title');
			                	l.val(((b.text != "" && b.text != null) ? b.text : messages.other)).attr('optionid', b.optionId);
									
								otherPreviewText.text(l.val());	
								
								r.find('.checkbox-is-enable-other')
								 .prop("checked", true).trigger("change");
								
			                };
			                
			                // add option
			                r.find('.button-add-option')
			                .unbind('click')
			                .click(function (e) {
		                        D(true, null, "", "", false, null);
		                        // clear indexes
		                        clearIndexes();
		                        
		                    });
			                
			                // predefined
	                	   r.find('.button-import-predefined-choices').click(function(e) {
	                		   
	                		   	getPredefinedChoices(function(choices) {
 			                		for(var z = 0; z < choices.length; z++) {
 						    			D(false, null, choices[z], choices[z], false, null);
 						    		}
 			                	});
	                		   
	                		   e.preventDefault();
	                	   });
			                
			                // other
							r.find('.checkbox-is-enable-other')
							.change(function () {
								if ($(this).is(':checked')) {
									
									d.show();
									otherPreview.show();
									
								} else {
									d.hide();
									otherPreview.hide();
								}
							});
							
							// other input
							r.find(".input-other-title")
							.val(messages.other)
							.bind("keyup", function() {
								otherPreviewText.text($(this).val());
							});
							
							// options
							if(data.control != undefined && data.control.options != undefined) {
				                if(data.control.options.list.length != 0) {
					                for (var i = 0; i < data.control.options.list.length; i++) {
					                	
				                    	if(data.control.options.list[i].optionKindId == 0) {
				                    		D(false, data.control.options.list[i].optionId, data.control.options.list[i].text, data.control.options.list[i].value, data.control.options.list[i].isEnableAdditionalDetails, data.control.options.list[i].additionalDetailsTitle, data.control.options.list[i].link)
				                    	}
				                    	
				                    	if(data.control.options.list[i].optionKindId == 1) {
				                    		if(data.inputTypeId == 0 || data.inputTypeId == 2) {
												O(data.control.options.list[i]);
											}
				                    	}
				                    }
				                } else {
				                	for (var i = 0; i < 2; i++) {
										D(false, null, "", "", false, null)
			                        }
				                }
			                } else {
			                	for (var i = 0; i < 2; i++) {
									D(false, null, "", "", false, null)
		                        }
			                }
							
							
							
							// is enable comments
							r.find(".checkbox-is-enable-comment")
							.change(function() {
								if($(this).prop("checked")) {
									r.find(".control-container-comments").show();
									s.find(".container-control-comment").show();
								} else {
									r.find(".control-container-comments").hide();
									s.find(".container-control-comment").hide();
								}
							});
							
							// keyup -> comments
							r.find('.textarea-comments')
							.unbind("keyup")
							.keyup(function() {
								s.find('.label-comment').html(converter.makeHtml($(this).val()));
							});
   			                
			        		// comments
			        		if(data.control != undefined) {
			    				if(data.control.comment != undefined) {
			    					r.find(".checkbox-is-enable-comment").prop("checked", true).trigger("change");
			    					r.find('.textarea-comments').val(data.control.comment).trigger("keyup");
			    				}
			    			}
							
							
			                
			                // for save logic
			                var B = function () {
			                    var b = [];
			                    r.find("ul.options-list li").each(function (i, a) {
			                        if ($(a).attr("optionid") != undefined) {
			                            b.push(parseInt($(a).attr("optionid")))
			                        }
			                    });
			                    return b;
			                };
			                
			                
			                var C = function(__controlId) {
			        			
			        			var j = 0;
			                    var z = [];
			                    
			                    r.find("ul.options-list li").each(function (i, a) {
			                        if ($(a).attr("optionid") != undefined) {
			                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
			                            if(b != "") {
											
											var c = $(a).find('.checkbox-enable-additional-details-or-comments').is(':checked');
											var d = (c ? $.removeHTMLTags($(a).find('.input-additional-details-or-comments').val()).replace(/\r/g, "") : null);
											var image = $(a).find(".button-add-choice-image");
											
											var e = {
				                                optionId: parseInt($(a).attr("optionid")),
				                                optionKindId : 0,
				                                status: "updated",
												link : image.data("link"),
												linkTypeId : 1, // link type id -> 1 = image
				                                text: b,
				                                value: b,
				                                isEnableAdditionalDetails: c,
				                                additionalDetailsTitle: d
				                            };
				                            z.push(e);	
											
											// increment only if updated
											j++;
											
			                            } else {
											var e = {
												optionId: parseInt($(a).attr("optionid")),
												status : "deleted"
											}
											z.push(e);
			                            }
			                        } else {
			                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
										if(b != "") {
											
				                            var c = $(a).find('.checkbox-enable-additional-details-or-comments').is(':checked');
				                            var d = (c ? $.removeHTMLTags($(a).find('.input-additional-details-or-comments').val()).replace(/\r/g, "") : null);
											var image = $(a).find(".button-add-choice-image");
											
				                            var e = {
				                                controlId: (__controlId != undefined ? __controlId : undefined),
				                                optionId: null,
				                                optionKindId : 0,
				                                status: "added",
												link : image.data("link"),
												linkTypeId : 1, // link type id -> 1 = image
				                                text: b,
				                                value: b,
												isEnableAdditionalDetails: c,
												additionalDetailsTitle: d,
				                                orderId: i,
				                                opinionId: X.opinionId
				                            };
				                            z.push(e);
											
											// increment only if added
											j++;
										}
			                        }
			                        //j++;
			                    });
			                    
			                    // other 
			                    if(r.find('.checkbox-is-enable-other').is(':checked')) {
			                    	
			                    	var l = r.find('.input-other-title'); // input-other-title
			                    	if(l.attr('optionid') != undefined && Q != null) {
			                    		var b = $.removeHTMLTags(l.val()).replace(/\r/g, "");
			                        	var e = {
			                                optionId: parseInt(l.attr('optionid')),
			                                optionKindId: 1,
			                                status: "updated",
			                                text: b,
			                                value: b,
			                                isEnableAdditionalDetails: false,
			                                additionalDetailsTitle: null,
			                                orderId: j
			                            };
			                            z.push(e);
			                    	} else {
			                    		var b = $.removeHTMLTags(l.val()).replace(/\r/g, "");
			                        	var e = {
			                                controlId: (__controlId != undefined ? __controlId : undefined),
			                                optionId: null,
			                                optionKindId: 1,
			                                status: "added",
			                                text: b,
			                                value: b,
			                                isEnableAdditionalDetails: false,
			                                additionalDetailsTitle: null,
			                                orderId: j,
			                                opinionId: X.opinionId
			                            };
			                            z.push(e);
			                    	}
			                        
			                    } else {
			                    	
			                    	if(Q != null) {
			                        	var l = r.find('.input-other-title');
			                        	if(l.attr('optionid') != undefined) {
			                        		var a = {
			                                    optionId: parseInt(l.attr('optionid')),
			                                    status: "deleted"
			                                };
			                                A.push(a);
			                        	}
			                    	}
			                    	
			                    }
			                    
			                    for (var y = 0; y < A.length; y++) {
			                        z.push(A[y]);
			                    }
			                    
			                    return z;
			                    
			        		};
			        		
			        		// update options for option id
			        		var G = function() {
			        			r.find("ul.options-list li").each(function (i, a) {
			        				$(a).attr({ "optionid" : result.options.list[i].optionId });
			        			});
			        		};
	                	   
			        		
			        		var v = null;
	   		        		v = new validator({
								elements : [
									{
										element : o.find('.input-content'),
										status : o.find('.status-content'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									if(vControl.attr('controlid') != undefined) {
									
										if(result.controlTypeId != data.controlTypeId) {
										
											
											delete __obj; __obj = { };
										
											__obj.orderId = vControl.attr("orderid");
											__obj.controlTypeId = data.controlTypeId; // multiple choice
											__obj.inputTypeId = data.inputTypeId; // checkbox
											__obj.content = o.find('.input-content').val();
											__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			__obj.linkTypeId = 1;
			                                			__obj.link = o.find('.input-image').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			__obj.linkTypeId = 2;
			                                			__obj.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                } else {
			                                	__obj.linkTypeId = 0;
			                        			__obj.link = null;
			                                }
										
											__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
											__obj.comment = (o.find('.checkbox-is-enable-comment').prop('checked') && o.find('.textarea-comments').val() != "" ? o.find('.textarea-comments').val() : undefined);
										
											__obj.options = {
												list : C(),
												reorder : B()
											};
											
											
							        		deleteControl({
							        			accountId : accountId,
							        			controlId : result.controlId,
							        			success : function() {
        				
													X.onCreate({
														control : __obj, // result,
														success : function(newData) {									
						   		    						
															result = newData;
															
															// merge data -> result
						   		    						//$.extend(result, newData);
															
															// update ids and types
															vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
			   		    						
							   		        				viewControl({
								   		     					controlTypeId : result.controlTypeId,
								   		     					inputTypeId : result.inputTypeId,
								   		     					control : result
								   		     				});
			   		    						
						   		    						o.find('.control-edit').hide();
						   		    			        	o.find('.control-view').show();
															
															
														},
														error: function() {
															console.log("ERRRRRRRR");
														}
													});
        				
							        			},
							        			error: function(error) {

							        			}
							        		});
										
										
										} else {
											
											
											result.orderId = vControl.attr("orderid");
										
											result.controlTypeId = data.controlTypeId; // multiple choice
											result.inputTypeId = data.inputTypeId; // checkbox
										
											result.content = o.find('.input-content').val();
											result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			result.linkTypeId = 1;
			                                			result.link = o.find('.input-image').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			result.linkTypeId = 2;
			                                			result.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                } else {
			                                	result.linkTypeId = 0;
			                        			result.link = null;
			                                }
										
											result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
											result.comment = (o.find('.checkbox-is-enable-comment').prop('checked') && o.find('.textarea-comments').val() != "" ? o.find('.textarea-comments').val() : undefined);
										
											// options
											result.options = {
												list : C(result.controlId),
												reorder : B()
											};
										
											result.accountId = accountId;
											
											// update
											updateControlDetails({
												control : result,
												success : function(newData) {
			
													// merge data -> result
													$.extend(result, newData.result);
			
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
			
													o.find('.control-edit').hide();
										        	o.find('.control-view').show();
        	
												},
												error: function() {
													alert("ERR");
												}
											});
											
										}
										
									}
									
									
								}
	   		        		});
	                	   
	   		        		
	   		        		// cancel
	   		        		r.find('.button-cancel').click(function(event) {
	   		        			
	   		        			if(result != undefined) {
	   		        				
	   		        				// close to view mode
	   		        				
	   		        				viewControl({
		   		     					controlTypeId : result.controlTypeId,
		   		     					inputTypeId : result.inputTypeId,
		   		     					control : result
		   		     				});
	   		        				
	   		        				o.find('.control-edit').hide();
	   		        	        	o.find('.control-view').show();
	   		        				
	   		        			} else {
	   		        				
	   		        				vControl.remove();
	   		        				// demo
	   		        				s.remove();
	   		        				
	   		        				X.onDelete();
	   		        				
	   		        				/*
	   		        				if($("ul.questions li.question").length == 0) {
	   		        					$("#empty").show();
	   		        					$("#questions").hide();
	   		        				}
	   		        				*/
	   		        				
	   		        			}
	   		        			
	   		        			event.preventDefault();
	   		        		});
	                	   
                    		break;
                    	}
                    	
                    }
                    
        			break;
        		}
        		case 3: {
        			
					
					var viewMode = function() {
						
						var viewIncludes = o.find('.view-container-control-includes').empty();
						var viewOther = o.find('.view-container-control-other').empty();
						o.find('.view-container-control-comment').hide(); // clear
						
						switch(data.inputTypeId) {
							case 0: {
								// view
								$("<input type=\"text\"/>").appendTo(viewOther);
								break;
							} 
							case 1: {
								// view
								$("<textarea></textarea>").appendTo(viewOther);
								break;
							}
							case 2: {
								//console.log("password --------");
								break;
							}
						}
						
					};
					
					viewMode();
					
					
					
        			// single text
					var a = s.find('.container-control-includes');
                        a.empty();
                        
                    var containerControlOther = s.find('.container-control-other');
                    	containerControlOther.empty();
						
						s.find(".container-control-comment").hide();
                    
                    switch(data.inputTypeId) {
	                    case 0: {
							
							
							// edit
	                    	
	                    	// single
                    		var r = $("<div>" +
	       						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
	       						"<div class=\"params\">" +
	       							/*"<div class=\"param-name\"></div>" +*/
	       							"<div class=\"param-value\">" +
	       								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
	       							"</div>" +
	       							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
										"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
									"</div>" +
	       						"</div>" +
	       					"</div>").appendTo(placeholderControl);
                    		
                    		
                    		var otherPreview = null;
 	                	   	var otherPreviewText = null;
 	                	   
 	                	   	// other
 	                	   	otherPreview = $("<input type=\"text\" />").appendTo(containerControlOther);
                    		
 	                	   	var v = null;
	   		        		v = new validator({
								elements : [
									{
										element : o.find('.input-content'),
										status : o.find('.status-content'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									if(vControl.attr('controlid') != undefined) {
										
										if(result.controlTypeId != data.controlTypeId) {
											
											
											
											delete __obj; __obj = { };

											__obj.orderId = vControl.attr("orderid");
											__obj.controlTypeId = data.controlTypeId;
											__obj.inputTypeId = data.inputTypeId;
											__obj.content = o.find('.input-content').val();
											__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			__obj.linkTypeId = 1;
			                                			__obj.link = o.find('.input-image').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			__obj.linkTypeId = 2;
			                                			__obj.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                } else {
			                                	__obj.linkTypeId = 0;
			                        			__obj.link = null;
			                                }
										
											__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
											
											
							        		deleteControl({
							        			accountId : accountId,
							        			controlId : result.controlId,
							        			success : function() {
        				
													X.onCreate({
														control : __obj, // result,
														success : function(newData) {									
						   		    						
															result = newData;
															
															// merge data -> result
						   		    						//$.extend(result, newData);
															
															// update ids and types
															vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
			   		    						
							   		        				viewControl({
								   		     					controlTypeId : result.controlTypeId,
								   		     					inputTypeId : result.inputTypeId,
								   		     					control : result
								   		     				});
			   		    						
						   		    						o.find('.control-edit').hide();
						   		    			        	o.find('.control-view').show();
															
															
														},
														error: function() {
															console.log("ERRRRRRRR");
														}
													});
        				
							        			},
							        			error: function(error) {

							        			}
							        		});

										} else {
											
											
											
											//opinionId = X.opinionId;
											result.orderId = vControl.attr("orderid");
											//parentId = X.currentSheetId;
											//parentTypeId = 2; // 1 poll, sheet 2, control 3
											result.controlTypeId = data.controlTypeId;
											result.inputTypeId = data.inputTypeId; // 0 text
										
											result.content = o.find('.input-content').val();
											result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			result.linkTypeId = 1;
			                                			result.link = o.find('.input-image').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			result.linkTypeId = 2;
			                                			result.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                } else {
			                                	result.linkTypeId = 0;
			                        			result.link = null;
			                                }
										
											result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
										
											result.accountId = accountId;
											
											// update
				   		    				updateControlDetails({
				   		    					control : result,
				   		    					success : function(newData) {
			   		    						
				   		    						// merge data -> result
				   		    						$.extend(result, newData.result);
			   		    						
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
			   		    						
				   		    						o.find('.control-edit').hide();
				   		    			        	o.find('.control-view').show();
			   		    			        	
				   		    					},
				   		    					error: function() {
				   		    						alert("ERR");
				   		    					}
				   		    				});
											
										}
										
									}
									
								}
	   		        		});
	   		        		
	   		        		r.find('.button-cancel').click(function(event) {
	   		        			
	   		        			if(result != undefined) {
	   		        				
	   		        				// close to view mode
	   		        				
	   		        				viewControl({
		   		     					controlTypeId : result.controlTypeId,
		   		     					inputTypeId : result.inputTypeId,
		   		     					control : result
		   		     				});
	   		        				
	   		        				o.find('.control-edit').hide();
	   		        	        	o.find('.control-view').show();
	   		        	        	
	   		        	        	//console.log("change to view mode after cancel --->>>> ");
	   		        				
	   		        			} else {
	   		        				
	   		        				vControl.remove();
	   		        				// demo
	   		        				s.remove();
	   		        				
	   		        				
	   		        				X.onDelete();
	   		        				
	   		        				/*
	   		        				if($("ul.questions li.question").length == 0) {
	   		        					$("#empty").show();
	   		        					$("#questions").hide();
	   		        				}
	   		        				*/
	   		        				
	   		        			}
	   		        			
	   		        			event.preventDefault();
	   		        		});
	                    	
	                    	break;
	                    }
	                    case 1: {
							
	                    	
	                    	// textarea
	                    	var r = $("<div>" +
	       						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
	       						"<div class=\"params\">" +
	       							/*"<div class=\"param-name\"></div>" +*/
	       							"<div class=\"param-value\">" +
	       								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
	       							"</div>" +
	       							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
										"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
									"</div>" +
	       						"</div>" +
	       					"</div>").appendTo(placeholderControl);
                    		
                    		
                    		var otherPreview = null;
 	                	   	var otherPreviewText = null;
 	                	   
 	                	   	// other
 	                	   	otherPreview = $("<textarea></textarea>").appendTo(containerControlOther);
                    		
 	                	   	var v = null;
	   		        		v = new validator({
								elements : [
									{
										element : o.find('.input-content'),
										status : o.find('.status-content'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									if(vControl.attr('controlid') != undefined) {
										
										if(result.controlTypeId != data.controlTypeId) {
											
											
											delete __obj; __obj = { };
											
											__obj.orderId = vControl.attr("orderid");
											__obj.controlTypeId = data.controlTypeId;
											__obj.inputTypeId = data.inputTypeId; // 1 textarea
											__obj.content = o.find('.input-content').val();
											__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			__obj.linkTypeId = 1;
			                                			__obj.link = o.find('.input-image').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			__obj.linkTypeId = 2;
			                                			__obj.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                } else {
			                                	__obj.linkTypeId = 0;
			                        			__obj.link = null;
			                                }
										
											__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
											
											
							        		deleteControl({
							        			accountId : accountId,
							        			controlId : result.controlId,
							        			success : function() {
        				
													X.onCreate({
														control : __obj, // result,
														success : function(newData) {									
						   		    						
															result = newData;
															
															// merge data -> result
						   		    						//$.extend(result, newData);
															
															// update ids and types
															vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
			   		    						
							   		        				viewControl({
								   		     					controlTypeId : result.controlTypeId,
								   		     					inputTypeId : result.inputTypeId,
								   		     					control : result
								   		     				});
			   		    						
						   		    						o.find('.control-edit').hide();
						   		    			        	o.find('.control-view').show();
															
															
														},
														error: function() {
															console.log("ERRRRRRRR");
														}
													});
        				
							        			},
							        			error: function(error) {

							        			}
							        		});
											
											
										} else {
											
											//opinionId = X.opinionId;
											result.orderId = vControl.attr("orderid");
											//parentId = X.currentSheetId;
											//parentTypeId = 2; // 1 poll, sheet 2, control 3
											result.controlTypeId = data.controlTypeId;
											result.inputTypeId = data.inputTypeId; // 1 textarea
										
											result.content = o.find('.input-content').val();
											result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			result.linkTypeId = 1;
			                                			result.link = o.find('.input-image').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			result.linkTypeId = 2;
			                                			result.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                } else {
			                                	result.linkTypeId = 0;
			                        			result.link = null;
			                                }
										
											result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
										
											
											result.accountId = accountId;
											
											// update
				   		    				updateControlDetails({
				   		    					control : result,
				   		    					success : function(newData) {
			   		    						
				   		    						// merge data -> result
				   		    						$.extend(result, newData.result);
			   		    						
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
			   		    						
				   		    						o.find('.control-edit').hide();
				   		    			        	o.find('.control-view').show();
			   		    			        	
				   		    					},
				   		    					error: function() {
				   		    						alert("ERR");
				   		    					}
				   		    				});
											
										}
										
									}
									
								}
	   		        		});
	   		        		
	   		        		r.find('.button-cancel').click(function(event) {
	   		        			
	   		        			if(result != undefined) {
	   		        				
	   		        				// close to view mode
	   		        				
	   		        				viewControl({
		   		     					controlTypeId : result.controlTypeId,
		   		     					inputTypeId : result.inputTypeId,
		   		     					control : result
		   		     				});
	   		        				
	   		        				o.find('.control-edit').hide();
	   		        	        	o.find('.control-view').show();
	   		        	        	
	   		        	        	//console.log("change to view mode after cancel --->>>> ");
	   		        				
	   		        			} else {
	   		        				
	   		        				vControl.remove();
	   		        				// demo
	   		        				s.remove();
	   		        				
	   		        				X.onDelete();
	   		        				
	   		        				/*
	   		        				if($("ul.questions li.question").length == 0) {
	   		        					$("#empty").show();
	   		        					$("#questions").hide();
	   		        				}
	   		        				*/
	   		        				
	   		        			}
	   		        			
	   		        			event.preventDefault();
	   		        		});
	                    	
	                    	break;
	                    }
	                    case 2: {
	                    	
	                    	// password
	                    	var r = $("<div>" +
	       						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
	       						"<div class=\"params\">" +
	       							/*"<div class=\"param-name\"></div>" +*/
	       							"<div class=\"param-value\">" +
	       								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
	       							"</div>" +
	       							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
   										"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
   									"</div>" +
	       						"</div>" +
	       					"</div>").appendTo(placeholderControl);
                    		
                    		
                    		var otherPreview = null;
 	                	   	var otherPreviewText = null;
 	                	   
 	                	   	// other
 	                	   	otherPreview = $("<input type=\"password\" />").appendTo(containerControlOther);
                    		
 	                	   	var v = null;
	   		        		v = new validator({
								elements : [
									{
										element : o.find('.input-content'),
										status : o.find('.status-content'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									if(vControl.attr('controlid') != undefined) {
										
										if(result.controlTypeId != data.controlTypeId) {
											
											
											delete __obj; __obj = { };
											
											__obj.orderId = vControl.attr("orderid");
											__obj.controlTypeId = data.controlTypeId;
											__obj.inputTypeId = data.inputTypeId; // 2 password
											__obj.content = o.find('.input-content').val();
											__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			__obj.linkTypeId = 1;
			                                			__obj.link = o.find('.input-image').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			__obj.linkTypeId = 2;
			                                			__obj.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                } else {
			                                	__obj.linkTypeId = 0;
			                        			__obj.link = null;
			                                }
										
											__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
											
											
											
							        		deleteControl({
							        			accountId : accountId,
							        			controlId : result.controlId,
							        			success : function() {
        				
													X.onCreate({
														control : __obj, // result,
														success : function(newData) {									
						   		    						
															result = newData;
															
															// merge data -> result
						   		    						//$.extend(result, newData);
															
															// update ids and types
															vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
			   		    						
							   		        				viewControl({
								   		     					controlTypeId : result.controlTypeId,
								   		     					inputTypeId : result.inputTypeId,
								   		     					control : result
								   		     				});
			   		    						
						   		    						o.find('.control-edit').hide();
						   		    			        	o.find('.control-view').show();
															
															
														},
														error: function() {
															console.log("ERRRRRRRR");
														}
													});
        				
							        			},
							        			error: function(error) {

							        			}
							        		});
											
											
										} else {
											
											//opinionId = X.opinionId;
											result.orderId = vControl.attr("orderid");
											//parentId = X.currentSheetId;
											//parentTypeId = 2; // 1 poll, sheet 2, control 3
											result.controlTypeId = data.controlTypeId;
											result.inputTypeId = data.inputTypeId; // 2 password
										
											result.content = o.find('.input-content').val();
											result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			result.linkTypeId = 1;
			                                			result.link = o.find('.input-image').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			result.linkTypeId = 2;
			                                			result.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                } else {
			                                	result.linkTypeId = 0;
			                        			result.link = null;
			                                }
										
											result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
										
											result.accountId = accountId;
											
											// update
				   		    				updateControlDetails({
				   		    					control : result,
				   		    					success : function(newData) {
			   		    						
				   		    						// merge data -> result
				   		    						$.extend(result, newData.result);
			   		    						
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
			   		    						
				   		    						o.find('.control-edit').hide();
				   		    			        	o.find('.control-view').show();
			   		    			        	
				   		    					},
				   		    					error: function() {
				   		    						alert("ERR");
				   		    					}
				   		    				});
											
										}
										
									}
									
								}
	   		        		});
	   		        		
	   		        		r.find('.button-cancel').click(function(event) {
	   		        			
	   		        			if(result != undefined) {
	   		        				
	   		        				// close to view mode
	   		        				
	   		        				viewControl({
		   		     					controlTypeId : result.controlTypeId,
		   		     					inputTypeId : result.inputTypeId,
		   		     					control : result
		   		     				});
	   		        				
	   		        				o.find('.control-edit').hide();
	   		        	        	o.find('.control-view').show();
	   		        	        	
	   		        	        	
	   		        	        	//console.log("change to view mode after cancel --->>>> ");
	   		        				
	   		        			} else {
	   		        				
	   		        				vControl.remove();
	   		        				// demo
	   		        				s.remove();
	   		        				
	   		        				X.onDelete();
	   		        				
	   		        				/*
	   		        				if($("ul.questions li.question").length == 0) {
	   		        					$("#empty").show();
	   		        					$("#questions").hide();
	   		        				}
	   		        				*/
	   		        				
	   		        			}
	   		        			
	   		        			event.preventDefault();
	   		        		});
	                    	
	                    	break;
	                    }
                    }
                    
        			break;
        		}
        		case 4: {
        			
        			// matrix
					
					var viewMode = function() {
						
						// view
						var viewIncludes = o.find('.view-container-control-includes').empty();
						var viewOther = o.find('.view-container-control-other').empty();
						o.find('.view-container-control-comment').hide(); // clear
						
						switch(data.inputTypeId) {
							case 0: {
								
								// radio
								if(data.control.subControls != undefined) {
									if (data.control.subControls.list.length != 0) {
										var b = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"table-matrix\"></table>");
		                                var c = $("<tr class=\"table-matrix-header\"></tr>");
		                                var d = $("<td>&nbsp;</td>");
		                                c.append(d);
										
		                                for (var y = 0; y < data.control.options.list.length; y++) {
		                                    var e = $("<th>" + data.control.options.list[y].text + "</th>");
		                                    c.append(e);
		                                }
		                                b.append(c);
										
		                                for (var i = 0; i < data.control.subControls.list.length; i++) {
		                                    var f = $("<tr " + (i % 2 ? "style=\"background: #efefef\"" : "") + "></tr>");
		                                    var g = $("<th>" + data.control.subControls.list[i].content + "</th>");
		                                    f.append(g);
											
		                                    for (var y = 0; y < data.control.options.list.length; y++) {
		                                        var h = null;
		                                        h = $("<td><lable><input type=\"radio\" name=\"view_" + (data.control.controlId + "_" + data.control.subControls.list[i].controlId) + "\" /></label></td>");	                                        
		                                        f.append(h)
		                                    }
											
		                                    b.append(f);
		                                }
		                                viewOther.append(b);
											
									}
								}
								
								break;
							
							}
							case 1: {
							
								// checkbox
								if(data.control.subControls != undefined) {
									if (data.control.subControls.list.length != 0) {
										var b = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"table-matrix\"></table>");
		                                var c = $("<tr class=\"table-matrix-header\"></tr>");
		                                var d = $("<td>&nbsp;</td>");
		                                c.append(d);
										
		                                for (var y = 0; y < data.control.options.list.length; y++) {
		                                    var e = $("<th>" + data.control.options.list[y].text + "</th>");
		                                    c.append(e);
		                                }
		                                b.append(c);
										
		                                for (var i = 0; i < data.control.subControls.list.length; i++) {
		                                    var f = $("<tr " + (i % 2 ? "style=\"background: #efefef\"" : "") + "></tr>");
		                                    var g = $("<th>" + data.control.subControls.list[i].content + "</th>");
		                                    f.append(g);
											
		                                    for (var y = 0; y < data.control.options.list.length; y++) {
		                                        var h = null;
		                                        h = $("<td><lable><input type=\"checkbox\" /></label></td>");
		                                        f.append(h)
		                                    }
											
		                                    b.append(f);
		                                }
		                                viewOther.append(b);
											
									}
								}
							
								break;
							}
						}
						
					};
					
					viewMode();
					
					
					
					var a = s.find('.container-control-includes');
                        a.empty();
                        
                    var containerControlOther = s.find('.container-control-other');
                    	containerControlOther.empty();
						
						s.find(".container-control-comment").hide();
                    	
                    switch(data.inputTypeId) {
	                    case 0: {
	                    	
	                    	// matrix -> radio
	                    	
	                    	var r = $("<div>" +
	                    		"<div style=\"clear: both;\">" +
	                    			"<div class=\"control-heading\">Rows</div>" +
	       							"<ul class=\"rows-list\"></ul>" +
	       							"<div style=\"padding: 0 0 12px 20px; clear: both;\">" +
	       								"<a class=\"button-add-row\" title=\"Add Row\">Add Row</a>" +
	       							"</div>" +
	       						"</div>" +
	                    		"<div style=\"clear: both;\">" +
	                    			"<div class=\"control-heading\">Columns</div>" +
	       							"<ul class=\"columns-list\"></ul>" +
	       							"<div style=\"padding: 0 0 12px 20px; clear: both;\">" +
	       								"<a class=\"button-add-column\" title=\"Add Column\">Add Column</a>" +
	       								"<a href=\"#\" class=\"button-import-predefined-columns\" title=\"Import Predifined Columns\">Import Predifined Columns</a>" +
	       							"</div>" +
	       						"</div>" +
	       						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
	       						"<div class=\"params\">" +
	       							/*"<div class=\"param-name\"></div>" +*/
	       							"<div class=\"param-value\">" +
	       								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
	       							"</div>" +
	       							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
       									"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
       								"</div>" +
	       						"</div>" +
	       					"</div>").appendTo(placeholderControl);
	                    	
	                    	// subcontrols
	                    	var w = r.find('ul.rows-list');
	                	   w.sortable({
	                		   placeholder: "options-list-drag",
	                		   handle: ".tab-move-row",
	                		   opacity: 0.7,
	                		   axis: "y",
	                		   cursor: "pointer",
	                		   scroll: true,
	                		   start: function(event, ui) {
	                			   ui.item.startPos = ui.item.index();
	                		   },
	                		   stop: function(event, ui) {
	                			   clearRowIndexes();
	                		   }
		   	               });
						   
						   // options
	                	   var columnsList = r.find('ul.columns-list');
	                	   columnsList.sortable({
	                		   placeholder: "options-list-drag",
	                		   handle: ".tab-move-column",
	                		   opacity: 0.7,
	                		   axis: "y",
	                		   cursor: "pointer",
	                		   scroll: true,
	                		   start: function(event, ui) {
	                			   ui.item.startPos = ui.item.index();
	                		   },
	                		   stop: function(event, ui) {
	                			   clearColumnIndexes();
	                		   }
		   	               });
	                	   
	                	   var updatePreview = function() {
	                		   
	                		   containerControlOther.empty();
	                		   
	                		   
	                		   var b = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"table-matrix\"></table>");
                               var c = $("<tr class=\"table-matrix-header\"></tr>");
                               var d = $("<td>&nbsp;</td>");
                               c.append(d);
                               
                               r.find('ul.columns-list li').each(function(i, el) {
                               		var e = $("<th>" + $(el).find('input.input-column-text').val() + "</th>");
                                   	c.append(e);
                               });
                               
                               b.append(c);
                               
                               r.find('ul.rows-list li').each(function(i, el) {
                               
                                   var f = $("<tr " + (i % 2 ? "style=\"background: #efefef\"" : "") + "></tr>");
                                   var g = $("<th class=\"table-matrix-row\">" + $(el).find('input.input-row-text').val() + "</th>");
                                   f.append(g);
                                   
                                   r.find('ul.columns-list li').each(function(y, el) {
                                       var h = null;
                                       h = $("<td><lable><input type=\"radio\" name=\"" + (data.control != undefined ? (data.control.controlId + "_" + i) : tempId + "_" + i) + "\" /></label></td>");
                                       f.append(h);
                                   });
                                   
                                   b.append(f);
                                   
                               });
                               
                               containerControlOther.append(b);
	                		   
	                	   };
	                    	
	                    	var clearRowIndexes = function() {
	                    		r.find('ul.rows-list li').each(function(i, el) {
		                           	$(el).attr("index", i);
		   						});
		   						
		   						updatePreview();
	                    	};
	                    	
	                    	var A = [];
	                    	var D = function(b, c, d, e, f, g) {
	                    		
	                    		var h = $("<li class=\"option\" index=\"" + r.find("ul.rows-list li").length + "\">" + 
		   	                    	"<div class=\"params\">" +
		   	                    		"<div class=\"tab-move-row\"></div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-row-text\" placeholder=\"Row choice\" />" +
		   	                    		"</div>" +
		   	                    		"<div class=\"param-value\" style=\"margin-left: 6px;\">" +
		   	                    			"<span title=\"Remove\" class=\"button-remove-row\"></span>" +
		   	                    		"</div>" +
		   	                    	"</div>" + 
		   	                    "</li>");
	                    		
	                    		if (c != null) {
		   	                        h.attr({
		   	                            "subcontrolid" : c
		   	                        });
		   	                    }
		   	                    h.appendTo(w);
		   	                    
			   	                h.find(".input-row-text")
		   						.bind("keyup", function() {
	   								
		   							if(containerControlOther.find('.table-matrix-row').get($(this).closest("li").attr("index")) != undefined) {
	   									$(containerControlOther.find('.table-matrix-row').get($(this).closest("li").attr("index"))).text($(this).val());
	   								}
		   							
	   							})
		   						.bind("keydown", function(e) {
	   								var code = e.keyCode || e.which;
	   								if (code == '9') {
										
	   									// if -> h last in list create new option
	   									if(r.find('ul.rows-list li').length == ($(this).closest("li").index() + 1)) {
	   										
											D(true, null, "", "", false, null);
	   										// clear indexes
	   										clearRowIndexes();
	   										
	   										return false;
	   									}
	   								}
	   							});
		   	                    
		   						if (b) {
		   	                    	h.find(".input-row-text").focus();
		   	                    }
		   	                    
		   	                    h.find('.button-remove-row')
		   	                     .bind("click", function () {
		   	                        if (h.attr('subcontrolid') != undefined) {
		   	                            
		   								var a = {
		   	                                controlId: parseInt(h.attr('subcontrolid')),
		   	                                status: "deleted"
		   	                            };
		   	                            A.push(a);
		   	                            
		   	                            h.remove();
		   	                            
		   	                        } else {
		   								h.remove();
		   	                        }
		   	                        
		   	                        // clear indexes
		   	                        clearRowIndexes();
		   	                        
		   	                     });
		   	                    
		   	                    
	                    	};
	                    	
	                    	var clearColumnIndexes = function() {
	                    		r.find('ul.columns-list li').each(function(i, el) {
		                           	$(el).attr("index", i);
		   						});
		   						
		   						updatePreview();
	                    	};
	                    	
	                    	var B = [];
	                    	var K = function(b, c, d, e, f, g) {
	                    		
	                    		var h = $("<li class=\"option\" index=\"" + r.find("ul.columns-list li").length + "\">" + 
		   	                    	"<div class=\"params\">" +
		   	                    		"<div class=\"tab-move-column\"></div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-column-text\" placeholder=\"Column choice\" />" +
		   	                    		"</div>" +
		   	                    		"<div class=\"param-value\" style=\"margin-left: 6px;\">" +
		   	                    			"<span title=\"Remove\" class=\"button-remove-column\"></span>" +
		   	                    		"</div>" +
		   	                    	"</div>" + 
		   	                    "</li>");
	                    		
	                    		if (c != null) {
		   	                        h.attr({
		   	                            "optionid" : c
		   	                        });
		   	                    }
		   	                    h.appendTo(columnsList);
			   	                
		   	                    h.find(".input-column-text")
	   							.bind("keyup", function() {
	   								
	   								if(containerControlOther.find('.table-matrix-header th').get($(this).closest("li").attr("index")) != undefined) {
	   									$(containerControlOther.find('.table-matrix-header th').get($(this).closest("li").attr("index"))).text($(this).val());
	   								}
	   								
	   							})
	   							.bind("keydown", function(e) {
	   								var code = e.keyCode || e.which;
	   								if (code == '9') {
	   									// if -> h last in list create new option
	   									if(r.find('ul.columns-list li').length == ($(this).closest("li").index() + 1)) {
	   										K(true, null, "", "", false, null);
	   										// clear indexes
	   										clearColumnIndexes();
	   										
	   										return false;
	   									}
	   								}
	   							});
		   	                    
		   	                    if (b) {
		   	                    	h.find(".input-column-text").focus();
		   	                    }
			   	                
		   	                    h.find('.button-remove-column')
		   	                    .bind("click", function () {
		   	                        if (h.attr('optionid') != undefined) {
		   	                            
		   								var a = {
		   	                                optionId: parseInt(h.attr('optionid')),
		   	                                status: "deleted"
		   	                            };
		   	                            B.push(a);
		   	                            
		   	                            h.remove();
		   	                            
		   	                        } else {
		   								h.remove();
		   	                        }
		   	                        
		   	                        // clear indexes
		   	                        clearColumnIndexes();
		   	                        
		   	                     });
			   	                    
	                    	};
	                    	
	                    	// add row
	                	    r.find('.button-add-row')
	                	    .unbind('click')
	                	    .click(function (e) {
	                		    D(true, null, "", "", false, null);
	                		    // clear indexes
	                		    clearRowIndexes();
	                		   
	                	    });
	                	   
	                	    // add column
	                	    r.find('.button-add-column')
	                	    .unbind('click')
	                	    .click(function (e) {
	                		    K(true, null, "", "", false, null);
	                		    // clear indexes
	                		    clearColumnIndexes();
	                		   
	                	    });
	                    	
   			                // predefined columns -> options
   			                r.find('.button-import-predefined-columns').click(function(e) {
   			                	getPredefinedColumns(function(columns) {
   			                		for(var z = 0; z < columns.length; z++) {
   						    			K(false, null, columns[z], columns[z], false, null);
   						    		}
   			                		
   			                		updatePreview();
   			                	});
   			                	e.preventDefault();
   			                });
   			                
   			                
   			                var prebuild = function() {
   			                	
   			                	// columns
                                K(false, null, "", "", false, null);
                                
                                // rows
                                D(false, null, "", "", false, null);
                                
                                
                                updatePreview();
   			                	
   			                };
							
   			                if (data.control != undefined && data.control.subControls != undefined) {
   			                	if(data.control.subControls.list.length != 0) {
									
   			                		// columns
   			                		for (var y = 0; y < data.control.options.list.length; y++) {
   			                			K(false, data.control.options.list[y].optionId, data.control.options.list[y].text, data.control.options.list[y].value, false, null);
	                                }
   			                		
   			                		// rows
   			                		for (var i = 0; i < data.control.subControls.list.length; i++) {
   			                			D(false, data.control.subControls.list[i].controlId, data.control.subControls.list[i].content, "", false, null);
	                                }
   			                		
   			                		updatePreview();
   			                		
   			                	} else {
   			                		
   			                		prebuild();
   			                		
   			                	}
   			                } else {
   			                	
   			                	prebuild();
   			                	
   			                }
							
							// columns
							var getOptionsReorder = function() {
			                    var b = [];
			                    r.find('ul.columns-list li').each(function (i, a) {
			                        if ($(a).attr("optionid") != undefined) {
			                            b.push(parseInt($(a).attr("optionid")))
			                        }
			                    });
			                    return b
							};
							
							var getOptions = function(__controlId) {
								
								var options = [];
								
								r.find('ul.columns-list li').each(function(i, a) {
			                        if ($(a).attr("optionid") != undefined) {
			                            var b = $.removeHTMLTags($(a).find(".input-column-text").val()).replace(/\r/g, "");
			                            if(b != "") {
											var c = {
			                                	optionId: parseInt($(a).attr("optionid")),
			                                	status: "updated",
			                                	text: b,
			                                	value: b
			                            	};
			                            	options.push(c);
										} else {
											// delete empty column
											var c = {
												optionId: parseInt($(a).attr("optionid")),
												status : "deleted"
											}
											options.push(c);
										}
			                        } else {
			                            var b = $.removeHTMLTags($(a).find(".input-column-text").val()).replace(/\r/g, "");
			                            if(b != "") {
											var c = {
			                                	controlId: (__controlId != undefined ? __controlId : undefined),
			                                	optionId: null,
			                                	status: "added",
			                                	text: b,
			                                	value: b,
			                                	orderId: i,
			                                	opinionId: X.opinionId
			                            	};
			                            	options.push(c);
										}
			                        }
								});
			                    for (var y = 0; y < B.length; y++) {
			                        options.push(B[y])
			                    }
								return options;
							};
							
							
							// rows
			                var getSubControlsReorder = function () {
			                    var b = [];
			                    r.find('ul.rows-list li').each(function (i, a) {
			                        if ($(a).attr("subcontrolid") != undefined) {
			                            b.push(parseInt($(a).attr("subcontrolid")))
			                        }
			                    });
			                    return b
			                };
							
 						   var getSubControls = function(__controlId) {
 							   
							   var subControls = [];
							   
 							   r.find('ul.rows-list li').each(function(i, a) {

 		                           if ($(a).attr("subcontrolid") != undefined) {
 		                               var b = $.removeHTMLTags($(a).find(".input-row-text").val()).replace(/\r/g, "");
 		                               if(b != "") {
									   		var c = {
 		                                   		controlId: parseInt($(a).attr("subcontrolid")),
 		                                   		status: "updated",
 		                                   		content: b,
 		                                   		note: "",
 		                                   		isMandatory: false,
 		                                   		isEnableNote: false, // TODO: remove this key
 		                                   		inputTypeId: 0,
 		                                   		comment: "", // TODO: remove this key
 		                                   		isEnableComment: false, // TODO: remove this key
 		                                   		isEnableOther: false, // TODO: remove this key
 		                                   		options: null,
 		                                   		controls: null,
 		                                   		fromScale: 0,
 		                                   		toScale: 0
 		                               		};
 		                               		subControls.push(c);
								   		} else {
											// delete empty row
								   			var c = {
 		                                   		controlId: parseInt($(a).attr("subcontrolid")),
 		                                   		status: "deleted"
								   			}
											subControls.push(c);
								   		}
 		                           } else {
 		                               var b = $.removeHTMLTags($(a).find(".input-row-text").val()).replace(/\r/g, "");
									   if(b != "") {
									   		var c = {
 		                                   		opinionId: X.opinionId,
 		                                   		controlTypeId: 9,
 		                                   		parentId: (__controlId != undefined ? __controlId : undefined),
 		                                   		parentTypeId: 3,
 		                                   		content: b,
 		                                   		groupId: (__controlId != undefined ? __controlId : undefined),
 		                                   		orderId: i,
 		                                   		status: "added"
 		                               		}
 		                               		subControls.push(c);
								   		}
 		                           }

 							   });
							   
							   // add deleted
 		                       for (var y = 0; y < A.length; y++) {
 		                           subControls.push(A[y]);
 		                       }
							   
 		                       return subControls;
							   
 						   };
   			                
   			                var v = null;
	   		        		v = new validator({
								elements : [
									{
										element : o.find('.input-content'),
										status : o.find('.status-content'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									if(vControl.attr('controlid') != undefined) {
										
										if(result.controlTypeId != data.controlTypeId) {
											
											
											delete __obj; __obj = { };
											
											__obj.orderId = vControl.attr("orderid");
											__obj.controlTypeId = data.controlTypeId; // matrix
											__obj.inputTypeId = data.inputTypeId; // radio
											__obj.content = o.find('.input-content').val();
											__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			__obj.linkTypeId = 1;
			                                			__obj.link = o.find('.input-image').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			__obj.linkTypeId = 2;
			                                			__obj.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                } else {
			                                	__obj.linkTypeId = 0;
			                        			__obj.link = null;
			                                }
										
											__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
										
			                                __obj.subControls = {
			                                    list: getSubControls(),
			                                    reorder: getSubControlsReorder()
			                                }
										
											__obj.options = {
			                                    list: getOptions(),
			                                    reorder: getOptionsReorder()
			                                }
											
											
							        		deleteControl({
							        			accountId : accountId,
							        			controlId : result.controlId,
							        			success : function() {
        				
													X.onCreate({
														control : __obj, // result,
														success : function(newData) {									
						   		    						
															result = newData;
															
															// merge data -> result
						   		    						//$.extend(result, newData);
															
															// update ids and types
															vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
			   		    						
							   		        				viewControl({
								   		     					controlTypeId : result.controlTypeId,
								   		     					inputTypeId : result.inputTypeId,
								   		     					control : result
								   		     				});
			   		    						
						   		    						o.find('.control-edit').hide();
						   		    			        	o.find('.control-view').show();
															
															
														},
														error: function() {
															console.log("ERRRRRRRR");
														}
													});
        				
							        			},
							        			error: function(error) {

							        			}
							        		});
											
										} else {
											
											result.orderId = vControl.attr("orderid");
										
											result.controlTypeId = data.controlTypeId; // matrix
											result.inputTypeId = data.inputTypeId; // radio
										
											result.content = o.find('.input-content').val();
											result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			result.linkTypeId = 1;
			                                			result.link = o.find('.input-image').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			result.linkTypeId = 2;
			                                			result.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                } else {
			                                	result.linkTypeId = 0;
			                        			result.link = null;
			                                }
										
											result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
										
											// rows
			                                result.subControls = {
			                                    list: getSubControls(result.controlId),
			                                    reorder: getSubControlsReorder()
			                                }
										
											// columns
											result.options = {
			                                    list: getOptions(result.controlId),
			                                    reorder: getOptionsReorder()
			                                }
										
			                                result.accountId = accountId;
			                                
											// update
											updateControlDetails({
												control : result,
												success : function(newData) {
			
													// merge data -> result
													$.extend(result, newData.result);
			
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
			
													o.find('.control-edit').hide();
										        	o.find('.control-view').show();
        	
												},
												error: function() {
													alert("ERR");
												}
											});
											
										}
										
									}
									
								}
	   		        		});
	                    	
	   		        		// cancel
	   		        		r.find('.button-cancel').click(function(event) {
	   		        			
	   		        			if(result != undefined) {
	   		        				
	   		        				// close to view mode
	   		        				
	   		        				viewControl({
		   		     					controlTypeId : result.controlTypeId,
		   		     					inputTypeId : result.inputTypeId,
		   		     					control : result
		   		     				});
	   		        				
	   		        				o.find('.control-edit').hide();
	   		        	        	o.find('.control-view').show();
	   		        				
	   		        			} else {
	   		        				
	   		        				vControl.remove();
	   		        				// demo
	   		        				s.remove();
	   		        				
	   		        				X.onDelete();
	   		        				
	   		        				/*
	   		        				if($("ul.questions li.question").length == 0) {
	   		        					$("#empty").show();
	   		        					$("#questions").hide();
	   		        				}
	   		        				*/
	   		        				
	   		        			}
	   		        			
	   		        			event.preventDefault();
	   		        		});
	                    	
	                    	break;
	                    }
	                    case 1: {
	                    	
	                    	// matrix checkbox
	                    	
	                    	var r = $("<div>" +
	                    		"<div style=\"clear: both;\">" +
	                    			"<div class=\"control-heading\">Rows</div>" +
	       							"<ul class=\"rows-list\"></ul>" +
	       							"<div style=\"padding: 0 0 12px 20px; clear: both;\">" +
	       								"<a class=\"button-add-row\" title=\"Add Row\">Add Row</a>" +
	       							"</div>" +
	       						"</div>" +
	                    		"<div style=\"clear: both;\">" +
	                    			"<div class=\"control-heading\">Columns</div>" +
	       							"<ul class=\"columns-list\"></ul>" +
	       							"<div style=\"padding: 0 0 12px 20px; clear: both;\">" +
	       								"<a class=\"button-add-column\" title=\"Add Column\">Add Column</a>" +
	       								"<a href=\"#\" class=\"button-import-predefined-columns\" title=\"Import Predifined Columns\">Import Predifined Columns</a>" +
	       							"</div>" +
	       						"</div>" +
	       						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
	       						"<div class=\"params\">" +
	       							/*"<div class=\"param-name\"></div>" +*/
	       							"<div class=\"param-value\">" +
	       								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
	       							"</div>" +
	       							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
       									"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
       								"</div>" +
	       						"</div>" +
	       					"</div>").appendTo(placeholderControl);
	                    	
	                    	
	                    	var w = r.find('ul.rows-list');
	                	   w.sortable({
	                		   placeholder: "options-list-drag",
	                		   handle: ".tab-move-row",
	                		   opacity: 0.7,
	                		   axis: "y",
	                		   cursor: "pointer",
	                		   scroll: true,
	                		   start: function(event, ui) {
	                			   ui.item.startPos = ui.item.index();
	                		   },
	                		   stop: function(event, ui) {
	                			   
	                			   clearRowIndexes();
	                		   }
		   	               });
	                	   
	                	   var columnsList = r.find('ul.columns-list');
	                	   columnsList.sortable({
	                		   placeholder: "options-list-drag",
	                		   handle: ".tab-move-column",
	                		   opacity: 0.7,
	                		   axis: "y",
	                		   cursor: "pointer",
	                		   scroll: true,
	                		   start: function(event, ui) {
	                			   ui.item.startPos = ui.item.index();
	                		   },
	                		   stop: function(event, ui) {
	                			   
	                			   clearColumnIndexes();
	                		   }
		   	               });
	                	   
	                	   var updatePreview = function() {
	                		   
	                		   containerControlOther.empty();
	                		   
	                		   
	                		   var b = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"table-matrix\"></table>");
                               var c = $("<tr class=\"table-matrix-header\"></tr>");
                               var d = $("<td>&nbsp;</td>");
                               c.append(d);
                               
                               r.find('ul.columns-list li').each(function(i, el) {
                               		var e = $("<th>" + $(el).find('input.input-column-text').val() + "</th>");
                                   	c.append(e);
                               });
                               
                               b.append(c);
                               
                               r.find('ul.rows-list li').each(function(i, el) {
                               
                                   var f = $("<tr " + (i % 2 ? "style=\"background: #efefef\"" : "") + "></tr>");
                                   var g = $("<th class=\"table-matrix-row\">" + $(el).find('input.input-row-text').val() + "</th>");
                                   f.append(g);
                                   
                                   r.find('ul.columns-list li').each(function(y, el) {
                                       var h = null;
                                       h = $("<td><lable><input type=\"checkbox\" /></label></td>");
                                       f.append(h);
                                   });
                                   
                                   b.append(f);
                                   
                               });
                               
                               containerControlOther.append(b);
	                		   
	                	   };
	                    	
	                    	var clearRowIndexes = function() {
	                    		r.find('ul.rows-list li').each(function(i, el) {
		                           	$(el).attr("index", i);
		   						});
		   						
		   						updatePreview();
	                    	};
	                    	
	                    	var A = [];
	                    	var D = function(b, c, d, e, f, g) {
	                    		
	                    		var h = $("<li class=\"option\" index=\"" + r.find("ul.rows-list li").length + "\">" + 
		   	                    	"<div class=\"params\">" +
		   	                    		"<div class=\"tab-move-row\"></div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-row-text\" placeholder=\"Row choice\" />" +
		   	                    		"</div>" +
		   	                    		"<div class=\"param-value\" style=\"margin-left: 6px;\">" +
		   	                    			"<span title=\"Remove\" class=\"button-remove-row\"></span>" +
		   	                    		"</div>" +
		   	                    	"</div>" + 
		   	                    "</li>");
	                    		
	                    		if (c != null) {
		   	                        h.attr({
		   	                            "subcontrolid" : c
		   	                        });
		   	                    }
		   	                    h.appendTo(w);
		   	                    
			   	                h.find(".input-row-text")
		   						.bind("keyup", function() {
	   								
		   							if(containerControlOther.find('.table-matrix-row').get($(this).closest("li").attr("index")) != undefined) {
	   									$(containerControlOther.find('.table-matrix-row').get($(this).closest("li").attr("index"))).text($(this).val());
	   								}
		   							
	   							})
		   						.bind("keydown", function(e) {
	   								var code = e.keyCode || e.which;
	   								if (code == '9') {
	   									// if -> h last in list create new option
	   									if(r.find('ul.rows-list li').length == ($(this).closest("li").index() + 1)) {
	   										D(true, null, "", "", false, null);
	   										// clear indexes
	   										clearRowIndexes();
	   										
	   										return false;
	   									}
	   								}
	   							});
		   	                    
		   						if (b) {
		   	                    	h.find(".input-row-text").focus();
		   	                    }
		   	                    
		   	                    h.find('.button-remove-row')
		   	                     .bind("click", function () {
		   	                        if (h.attr('subcontrolid') != undefined) {
		   	                            
		   								var a = {
		   	                                controlId: parseInt(h.attr('subcontrolid')),
		   	                                status: "deleted"
		   	                            };
		   	                            A.push(a);
		   	                            
		   	                            h.remove();
		   	                            
		   	                        } else {
		   								h.remove();
		   	                        }
		   	                        
		   	                        // clear indexes
		   	                        clearRowIndexes();
		   	                        
		   	                     });
		   	                    
		   	                    
	                    	};
	                    	
	                    	var clearColumnIndexes = function() {
	                    		r.find('ul.columns-list li').each(function(i, el) {
		                           	$(el).attr("index", i);
		   						});
		   						
		   						updatePreview();
	                    	};
	                    	
	                    	var B = [];
	                    	var K = function(b, c, d, e, f, g) {
	                    		
	                    		var h = $("<li class=\"option\" index=\"" + r.find("ul.columns-list li").length + "\">" + 
		   	                    	"<div class=\"params\">" +
		   	                    		"<div class=\"tab-move-column\"></div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-column-text\" placeholder=\"Column choice\" />" +
		   	                    		"</div>" +
		   	                    		"<div class=\"param-value\" style=\"margin-left: 6px;\">" +
		   	                    			"<span title=\"Remove\" class=\"button-remove-column\"></span>" +
		   	                    		"</div>" +
		   	                    	"</div>" + 
		   	                    "</li>");
	                    		
	                    		if (c != null) {
		   	                        h.attr({
		   	                            "optionid" : c
		   	                        });
		   	                    }
		   	                    h.appendTo(columnsList);
			   	                
		   	                    h.find(".input-column-text")
	   							.bind("keyup", function() {
	   								
	   								if(containerControlOther.find('.table-matrix-header th').get($(this).closest("li").attr("index")) != undefined) {
	   									$(containerControlOther.find('.table-matrix-header th').get($(this).closest("li").attr("index"))).text($(this).val());
	   								}
	   								
	   							})
	   							.bind("keydown", function(e) {
	   								var code = e.keyCode || e.which;
	   								if (code == '9') {
	   									// if -> h last in list create new option
	   									if(r.find('ul.columns-list li').length == ($(this).closest("li").index() + 1)) {
	   										K(true, null, "", "", false, null);
	   										// clear indexes
	   										clearColumnIndexes();
	   										
	   										return false;
	   									}
	   								}
	   							});
		   	                    
		   	                    if (b) {
		   	                    	h.find(".input-column-text").focus();
		   	                    }
			   	                
		   	                    h.find('.button-remove-column')
		   	                    .bind("click", function () {
		   	                        if (h.attr('optionid') != undefined) {
		   	                            
		   								var a = {
		   	                                optionId: parseInt(h.attr('optionid')),
		   	                                status: "deleted"
		   	                            };
		   	                            B.push(a);
		   	                            
		   	                            h.remove();
		   	                            
		   	                        } else {
		   								h.remove();
		   	                        }
		   	                        
		   	                        // clear indexes
		   	                        clearColumnIndexes();
		   	                        
		   	                     });
			   	                    
	                    	};
	                    	
	                    	// add row
	                	    r.find('.button-add-row')
	                	    .unbind('click')
	                	    .click(function (e) {
	                		    D(true, null, "", "", false, null);
	                		    // clear indexes
	                		    clearRowIndexes();
	                		   
	                	    });
	                	   
	                	    // add column
	                	    r.find('.button-add-column')
	                	    .unbind('click')
	                	    .click(function (e) {
	                		    K(true, null, "", "", false, null);
	                		    // clear indexes
	                		    clearColumnIndexes();
	                		   
	                	    });
	                    	
   			                // predefined columns -> options
   			                r.find('.button-import-predefined-columns').click(function(e) {
   			                	getPredefinedColumns(function(columns) {
   			                		for(var z = 0; z < columns.length; z++) {
   						    			K(false, null, columns[z], columns[z], false, null);
   						    		}
   			                		
   			                		updatePreview();
   			                	});
   			                	e.preventDefault();
   			                });
   			                
   			                
   			                var prebuild = function() {
   			                	
   			                	// columns
                                K(false, null, "", "", false, null);
                                
                                // rows
                                D(false, null, "", "", false, null);
                                
                                updatePreview();
   			                	
   			                };
   			                
   			                if (data.control != undefined && data.control.subControls != undefined) {
   			                	if(data.control.subControls.list.length != 0) {
	   			                	
   			                		// columns
   			                		for (var y = 0; y < data.control.options.list.length; y++) {
   			                			K(false, data.control.options.list[y].optionId, data.control.options.list[y].text, data.control.options.list[y].value, false, null);
	                                }
   			                		
   			                		// rows
   			                		for (var i = 0; i < data.control.subControls.list.length; i++) {
   			                			D(false, data.control.subControls.list[i].controlId, data.control.subControls.list[i].content, "", false, null);
	                                }
   			                		
   			                		updatePreview();
   			                		
   			                	} else {
   			                		
   			                		prebuild();
   			                		
   			                	}
   			                } else {
   			                	
   			                	prebuild();
   			                	
   			                }
							
							// columns
							var getOptionsReorder = function() {
			                    var b = [];
			                    r.find('ul.columns-list li').each(function (i, a) {
			                        if ($(a).attr("optionid") != undefined) {
			                            b.push(parseInt($(a).attr("optionid")))
			                        }
			                    });
			                    return b
							};

							var getOptions = function(__controlId) {
								
								var options = [];
								
								r.find('ul.columns-list li').each(function(i, a) {
			                        if ($(a).attr("optionid") != undefined) {
			                            var b = $.removeHTMLTags($(a).find(".input-column-text").val()).replace(/\r/g, "");
			                            if(b != "") {	
											var c = {
				                                optionId: parseInt($(a).attr("optionid")),
				                                status: "updated",
				                                text: b,
				                                value: b
				                            };
				                            options.push(c);
			                            } else {
			                            	var c = {
				                                optionId: parseInt($(a).attr("optionid")),
				                                status: "deleted"
			                            	}
				                            options.push(c);											
			                            }
			                        } else {
			                            var b = $.removeHTMLTags($(a).find(".input-column-text").val()).replace(/\r/g, "");
										if(b != "") {
				                            var c = {
				                                controlId: (__controlId != undefined ? __controlId : undefined),
				                                optionId: null,
				                                status: "added",
				                                text: b,
				                                value: b,
				                                orderId: i,
				                                opinionId: X.opinionId
				                            };
				                            options.push(c);
										}
			                        }
								});
			                    for (var y = 0; y < B.length; y++) {
			                        options.push(B[y])
			                    }
								return options;
							};

							// rows
			                var getSubControlsReorder = function () {
			                    var b = [];
			                    r.find('ul.rows-list li').each(function (i, a) {
			                        if ($(a).attr("subcontrolid") != undefined) {
			                            b.push(parseInt($(a).attr("subcontrolid")))
			                        }
			                    });
			                    return b
			                };
							
 						   var getSubControls = function(__controlId) {
   
							   var subControls = [];
   
 							   r.find('ul.rows-list li').each(function(i, a) {

 		                           if ($(a).attr("subcontrolid") != undefined) {
 		                               var b = $.removeHTMLTags($(a).find(".input-row-text").val()).replace(/\r/g, "");
 		                               if(b != "") {
										   var c = {
	 		                                   controlId: parseInt($(a).attr("subcontrolid")),
	 		                                   status: "updated",
	 		                                   content: b,
	 		                                   note: "",
	 		                                   isMandatory: false,
	 		                                   isEnableNote: false, // TODO: remove this key
	 		                                   inputTypeId: 0,
	 		                                   comment: "", // TODO: remove this key
	 		                                   isEnableComment: false, // TODO: remove this key
	 		                                   isEnableOther: false, // TODO: remove this key
	 		                                   options: null,
	 		                                   controls: null,
	 		                                   fromScale: 0,
	 		                                   toScale: 0
	 		                               };
	 		                               subControls.push(c);
 		                               } else {
										   var c = {
	 		                                   controlId: parseInt($(a).attr("subcontrolid")),
	 		                                   status: "deleted"
										   }
										   subControls.push(c);
 		                               }
 		                           } else {
 		                               var b = $.removeHTMLTags($(a).find(".input-row-text").val()).replace(/\r/g, "");
									   if(b != "") {
	 		                               var c = {
	 		                                   opinionId: X.opinionId,
	 		                                   controlTypeId: 9,
	 		                                   parentId: (__controlId != undefined ? __controlId : undefined),
	 		                                   parentTypeId: 3,
	 		                                   content: b,
	 		                                   groupId: (__controlId != undefined ? __controlId : undefined),
	 		                                   orderId: i,
	 		                                   status: "added"
	 		                               }
	 		                               subControls.push(c);
									   }
 		                           }

 							   });
   
							   // add deleted
 		                       for (var y = 0; y < A.length; y++) {
 		                           subControls.push(A[y]);
 		                       }
   
 		                       return subControls;
   
 						   };
   			                
   			                var v = null;
	   		        		v = new validator({
								elements : [
									{
										element : o.find('.input-content'),
										status : o.find('.status-content'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									if(vControl.attr('controlid') != undefined) {
										
										if(result.controlTypeId != data.controlTypeId) {
											
											
											delete __obj; __obj = { };
											
											__obj.orderId = vControl.attr("orderid");
											__obj.controlTypeId = data.controlTypeId;
											__obj.inputTypeId = data.inputTypeId;
											__obj.content = o.find('.input-content').val();
											__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			__obj.linkTypeId = 1;
			                                			__obj.link = o.find('.input-image').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			__obj.linkTypeId = 2;
			                                			__obj.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			__obj.linkTypeId = 0;
			                                			__obj.link = null;
			                                		}
			                                	}
			                                } else {
			                                	__obj.linkTypeId = 0;
			                        			__obj.link = null;
			                                }
										
											__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
										
											__obj.subControls = {
												list: getSubControls(),
												reorder: getSubControlsReorder()
											}
										
											__obj.options = {
												list: getOptions(),
												reorder: getOptionsReorder()
											}
											
											
							        		deleteControl({
							        			accountId : accountId,
							        			controlId : result.controlId,
							        			success : function() {
        				
													X.onCreate({
														control : __obj, // result,
														success : function(newData) {									
						   		    						
															result = newData;
															
															// merge data -> result
						   		    						//$.extend(result, newData);
															
															// update ids and types
															vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
			   		    						
							   		        				viewControl({
								   		     					controlTypeId : result.controlTypeId,
								   		     					inputTypeId : result.inputTypeId,
								   		     					control : result
								   		     				});
			   		    						
						   		    						o.find('.control-edit').hide();
						   		    			        	o.find('.control-view').show();
															
															
														},
														error: function() {
															console.log("ERRRRRRRR");
														}
													});
        				
							        			},
							        			error: function(error) {

							        			}
							        		});
											
										} else {
											
											result.orderId = vControl.attr("orderid");
										
											result.controlTypeId = data.controlTypeId;
											result.inputTypeId = data.inputTypeId;
										
											result.content = o.find('.input-content').val();
											result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
										
			                                if(o.find('.checkbox-link').is(':checked')) {
			                                	if(o.find('.select-link-type').val() == 1) {
			                                		// link to image
			                                		if(o.find('.input-image').val() != "") {
			                                			result.linkTypeId = 1;
			                                			result.link = o.find('.input-image').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                	if(o.find('.select-link-type').val() == 2) {
			                                		// embed code
			                                		if(o.find('.textarea-embed-code').val() != "") {
			                                			result.linkTypeId = 2;
			                                			result.link = o.find('.textarea-embed-code').val();
			                                		} else {
			                                			result.linkTypeId = 0;
			                                			result.link = null;
			                                		}
			                                	}
			                                } else {
			                                	result.linkTypeId = 0;
			                        			result.link = null;
			                                }
										
											result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
										
											// rows
											result.subControls = {
												list: getSubControls(result.controlId),
												reorder: getSubControlsReorder()
											}
										
											// columns
											result.options = {
												list: getOptions(result.controlId),
												reorder: getOptionsReorder()
											}
										
											result.accountId = accountId;
										
											// update
											updateControlDetails({
												control : result,
												success : function(newData) {
			
													// merge data -> result
													$.extend(result, newData.result);
			
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
												
													o.find('.control-edit').hide();
										        	o.find('.control-view').show();
        	
												},
												error: function() {
													alert("ERR");
												}
											});
											
										}
									
									}
									
								}
	   		        		});
	   		        		
	   		        		// cancel
	   		        		r.find('.button-cancel').click(function(event) {
	   		        			
	   		        			if(result != undefined) {
	   		        				
	   		        				// close to view mode
	   		        				
	   		        				viewControl({
		   		     					controlTypeId : result.controlTypeId,
		   		     					inputTypeId : result.inputTypeId,
		   		     					control : result
		   		     				});
	   		        				
	   		        				o.find('.control-edit').hide();
	   		        	        	o.find('.control-view').show();
	   		        				
	   		        			} else {
	   		        				
	   		        				vControl.remove();
	   		        				// demo
	   		        				s.remove();
	   		        				
	   		        				X.onDelete();
	   		        				
	   		        				/*
	   		        				if($("ul.questions li.question").length == 0) {
	   		        					$("#empty").show();
	   		        					$("#questions").hide();
	   		        				}
	   		        				*/
	   		        				
	   		        			}
	   		        			
	   		        			event.preventDefault();
	   		        		});
	                    	
	                    	break;
	                    }
                    
                    }
        			
        			
        			break;
        		}
        		case 10: {
					
					
					var viewMode = function() {
						
						// view
						var viewIncludes = o.find('.view-container-control-includes').empty();
						var viewOther = o.find('.view-container-control-other').empty();
						o.find('.view-container-control-comment').hide(); // clear
						
                        var b = [5, 7, 9, 0, 3, 4, 5, 6, 7, 8, 9, 10]
                        var c = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"table-scale\"></table>");
                        var d = $("<tr class=\"table-scale-header\"></tr>");
                        var e = $("<tr class=\"table-scale-row\"></tr>");
                        var f = $("<td>&nbsp;</td>");
                        var g = $("<td>&nbsp;</td>");
						
						
						if (data.control.fromScale != null && data.control.toScale != null) {
							
                            if (data.control.fromTitle != "" && data.control.fromTitle != null) {
                                d.append(f);
                                var h = $("<th>" + data.control.fromTitle + "</th>");
                                e.append(h);
                            }
							
                            for (var y = data.control.fromScale; y < (b[data.control.toScale] + 1); y++) {
                                var i = $("<th>" + y + "</th>");
                                d.append(i);
                                var j = $("<td><label><input type=\"radio\" name=\"view_" + data.control.controlId + "\" /></label></td>");
                                e.append(j);
                            }
							
                            if (data.control.toTitle != "" && data.control.toTitle != null) {
                                d.append(g);
                                var k = $("<th>" + data.control.toTitle + "</th>");
                                e.append(k);
                            }
							
                            c.append(d);
                            c.append(e);
                            viewOther.append(c);
							
						}
						
						
						
					};
					
					viewMode();
					
        			
        			// scale
        			
        			var a = s.find('.container-control-includes');
                    a.empty();
                    
                    var containerControlOther = s.find('.container-control-other');
						containerControlOther.empty();
					
						s.find(".container-control-comment").hide();
        			
                	var r = $("<div>" +
                		"<div style=\"clear: both;\">" +
                			"<div class=\"control-heading\">Scale</div>" +
                			"<div>" +
                				"<div>From:</div>" +
                				"<div><select class=\"select-from-scale\"><option value=\"0\">0</option><option value=\"1\">1</option></select></div>" +
                				"<div>To:</div>" +
                				"<div><select class=\"select-to-scale\"><option value=\"5\">5</option><option value=\"7\">7</option><option value=\"9\">9</option><option disabled=\"true\">---</option><option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option><option value=\"6\">6</option><option value=\"7\">7</option><option value=\"8\">8</option><option value=\"9\">9</option><option value=\"10\">10</option></select></div>" +
                			"</div>" +
   						"</div>" +
   						"<div style=\"clear: both;\">" +
        					"<div>Labels (Optional)</div>" +
        					"<div>" +
        						"<div>" +
        							"<div><span class=\"label-from-scale\">0</span>:</div>" +
        							"<div>" +
        								"<input type=\"text\" class=\"text-field input-from-title\" maxlength=\"50\" autocomplete=\"off\" />" +
        								"<div><em style=\"color: #999\">(e.g. Poor, Disagree)</em></div>" +
        							"</div>" +
        						"</div>" +
        						"<div>" +
        							"<div><span class=\"label-to-scale\">5</span>:</div>" +
        							"<div>" +
        								"<input type=\"text\" class=\"text-field input-to-title\" maxlength=\"50\" autocomplete=\"off\" />" +
        								"<div><em style=\"color: #999\">(e.g. Excellent, Agree)</em></div>" +
        							"</div>" +
        						"</div>" +
        					"</div>" +
        				"</div>" +
   						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
   						"<div class=\"params\">" +
   							/*"<div class=\"param-name\"></div>" +*/
   							"<div class=\"param-value\">" +
   								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
   							"</div>" +
   							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
								"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
							"</div>" +
   						"</div>" +
   					"</div>").appendTo(placeholderControl);
                	
                	
                	var T = r.find('.select-from-scale')[0];
                	var U = r.find('.select-to-scale')[0];
                	
                	
                	
                	var b = [5, 7, 9, 0, 3, 4, 5, 6, 7, 8, 9, 10];
                    
                	var updatePreview = function() {
                		
                		containerControlOther.empty();
                		
                		
                		
                		var c = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"table-scale\"></table>");
                        var d = $("<tr class=\"table-scale-header\"></tr>");
                        var e = $("<tr class=\"table-scale-row\"></tr>");
                        var f = $("<td class=\"poor-header\">&nbsp;</td>");
                        var g = $("<td class=\"excellent-header\">&nbsp;</td>");
                		
                		d.append(f);
                        var h = $("<th class=\"poor\">" + r.find('.input-from-title').val() + "</th>");
                        e.append(h);
                        
                        for (var y = T.selectedIndex; y < (b[U.selectedIndex] + 1); y++) {
                            var i = $("<th>" + y + "</th>");
                            d.append(i);
                            var j = $("<td><label><input type=\"radio\" name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" /></label></td>");
                            e.append(j);
                        }
                		
                		d.append(g);
                        var k = $("<th class=\"excellent\">" + r.find('.input-to-title').val() + "</th>");
                        e.append(k);
                		
                        c.append(d);
                        c.append(e);
                        
                        
                        containerControlOther.append(c);
                			
                	};
                	
                    // typing
                    r.find('.input-from-title')
					.bind("keyup", function() {
						containerControlOther.find('.poor').text($(this).val());
					});
                    
                    r.find('.input-to-title')
					.bind("keyup", function() {
						containerControlOther.find('.excellent').text($(this).val());
					});
                    
                    
                    // set data
                    if (data.control != undefined && data.control.fromTitle != undefined) {
                    	r.find('.input-from-title').val(data.control.fromTitle);
                    }
                    if (data.control != undefined && data.control.toTitle != undefined) {
                    	r.find('.input-to-title').val(data.control.toTitle);
                    }
                    
                    
                    var V = function (a) {
                        T.selectedIndex = a;
                        r.find('.label-from-scale').text(T.options[T.selectedIndex].text);
                        
                        updatePreview();
                        
                    };
                    
                    var W = function (a) {
                        U.selectedIndex = a;
                        r.find('.label-to-scale').text(U.options[U.selectedIndex].text);
                        
                        updatePreview();
                    };
                	
                	r.find('.select-from-scale')
                	.change(function () {
                        var a = ($(this)[0].selectedIndex != undefined ? $(this)[0].selectedIndex : 0);
                        V(a);
                    });
        			
                	r.find('.select-to-scale')
                	.change(function () {
                        var a = ($(this)[0].selectedIndex != undefined ? $(this)[0].selectedIndex : 0);
                        W(a);
                    });
                	
                	
                	if (data.control != undefined && data.control.fromScale != undefined) {
                        V(data.control.fromScale);
                    } else {
                        V(1);
                    }
                    if (data.control != undefined && data.control.toScale != undefined) {
                        W(data.control.toScale);
                    } else {
                        W(0);
                    }
                    
                    
                    updatePreview();
                    
                    
                	var v = null;
		        	v = new validator({
						elements : [
							{
								element : o.find('.input-content'),
								status : o.find('.status-content'),
								rules : [
									{ method : 'required', message : 'This field is required.' }
								]
							}
						],
						submitElement : r.find(".button-save"),
						accept : function () {
							
							if(vControl.attr('controlid') != undefined) {
								
								if(result.controlTypeId != data.controlTypeId) {
									
									
									delete __obj; __obj = { };
									
									__obj.orderId = vControl.attr("orderid");
									__obj.controlTypeId = data.controlTypeId;
									__obj.inputTypeId = data.inputTypeId;
									__obj.content = o.find('.input-content').val();
									__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
								
	                                if(o.find('.checkbox-link').is(':checked')) {
	                                	if(o.find('.select-link-type').val() == 1) {
	                                		// link to image
	                                		if(o.find('.input-image').val() != "") {
	                                			__obj.linkTypeId = 1;
	                                			__obj.link = o.find('.input-image').val();
	                                		} else {
	                                			__obj.linkTypeId = 0;
	                                			__obj.link = null;
	                                		}
	                                	}
	                                	if(o.find('.select-link-type').val() == 2) {
	                                		// embed code
	                                		if(o.find('.textarea-embed-code').val() != "") {
	                                			__obj.linkTypeId = 2;
	                                			__obj.link = o.find('.textarea-embed-code').val();
	                                		} else {
	                                			__obj.linkTypeId = 0;
	                                			__obj.link = null;
	                                		}
	                                	}
	                                } else {
	                                	__obj.linkTypeId = 0;
	                        			__obj.link = null;
	                                }
								
									__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
	
									__obj.fromScale = r.find('.select-from-scale')[0].selectedIndex; // from index
									__obj.fromTitle = r.find('.input-from-title').val();
								
									__obj.toScale = r.find('.select-to-scale')[0].selectedIndex; // to index
									__obj.toTitle = r.find('.input-to-title').val();
									
									
					        		deleteControl({
					        			accountId : accountId,
					        			controlId : result.controlId,
					        			success : function() {
				
											X.onCreate({
												control : __obj, // result,
												success : function(newData) {									
				   		    						
													result = newData;
													
													// merge data -> result
				   		    						//$.extend(result, newData);
													
													// update ids and types
													vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
	   		    						
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
	   		    						
				   		    						o.find('.control-edit').hide();
				   		    			        	o.find('.control-view').show();
													
													
												},
												error: function() {
													console.log("ERRRRRRRR");
												}
											});
				
					        			},
					        			error: function(error) {

					        			}
					        		});
									
									
								} else {
										
									result.orderId = vControl.attr("orderid");
								
									result.controlTypeId = data.controlTypeId;
									result.inputTypeId = data.inputTypeId;
								
									result.content = o.find('.input-content').val();
									result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
								
	                                if(o.find('.checkbox-link').is(':checked')) {
	                                	if(o.find('.select-link-type').val() == 1) {
	                                		// link to image
	                                		if(o.find('.input-image').val() != "") {
	                                			result.linkTypeId = 1;
	                                			result.link = o.find('.input-image').val();
	                                		} else {
	                                			result.linkTypeId = 0;
	                                			result.link = null;
	                                		}
	                                	}
	                                	if(o.find('.select-link-type').val() == 2) {
	                                		// embed code
	                                		if(o.find('.textarea-embed-code').val() != "") {
	                                			result.linkTypeId = 2;
	                                			result.link = o.find('.textarea-embed-code').val();
	                                		} else {
	                                			result.linkTypeId = 0;
	                                			result.link = null;
	                                		}
	                                	}
	                                } else {
	                                	result.linkTypeId = 0;
	                        			result.link = null;
	                                }
								
									result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
	
									// fromScale
									result.fromScale = r.find('.select-from-scale')[0].selectedIndex; // from index
									result.fromTitle = r.find('.input-from-title').val();
								
									// toScale
									result.toScale = r.find('.select-to-scale')[0].selectedIndex; // to index
									result.toTitle = r.find('.input-to-title').val();
								
									
									result.accountId = accountId;
									
									// update
									updateControlDetails({
										control : result,
										success : function(newData) {
			
											// merge data -> result
											$.extend(result, newData.result);
			
			   		        				viewControl({
				   		     					controlTypeId : result.controlTypeId,
				   		     					inputTypeId : result.inputTypeId,
				   		     					control : result
				   		     				});
			
											o.find('.control-edit').hide();
								        	o.find('.control-view').show();
        	
										},
										error: function() {
											alert("ERR");
										}
									});
									
								}
								
							}
							
						}
		        	});
		        	
		        	// cancel
	        		r.find('.button-cancel').click(function(event) {
	        			
	        			if(result != undefined) {
	        				
	        				// close to view mode
	        				
	        				viewControl({
	     						controlTypeId : result.controlTypeId,
	     						inputTypeId : result.inputTypeId,
	     						control : result
	     					});
	        				
	        				o.find('.control-edit').hide();
	        	        	o.find('.control-view').show();
	        				
	        			} else {
	        				
	        				vControl.remove();
	        				// demo
	        				s.remove();
	        				
	        				X.onDelete();
	        				
	        				/*
	        				if($("ul.questions li.question").length == 0) {
	        					$("#empty").show();
	        					$("#questions").hide();
	        				}
	        				*/
	        				
	        			}
	        			
	        			event.preventDefault();
	        		});
        			
        			break;
        		}
				case 11: {
					
					
					var viewMode = function() {
						
						// view
						var viewIncludes = o.find('.view-container-control-includes').empty();
						var viewOther = o.find('.view-container-control-other').empty();
						o.find('.view-container-control-comment').hide(); // clear
						
						// Heading / descriptiove text
						
					};
					
					viewMode();
					
					
					// hidden field / url parameter - variable
        			var a = s.find('.container-control-includes');
                    a.empty();
                    
                    var containerControlOther = s.find('.container-control-other');
                	containerControlOther.empty();
						
						s.find(".container-control-comment").hide();
					
                	var r = $("<div>" +
   						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
   						"<div class=\"params\">" +
   							/*"<div class=\"param-name\"></div>" +*/
   							"<div class=\"param-value\">" +
   								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
   							"</div>" +
   							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
								"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
							"</div>" +
   						"</div>" +
   					"</div>").appendTo(placeholderControl);
					
					
                	var v = null;
		        	v = new validator({
						elements : [
							{
								element : o.find('.input-content'),
								status : o.find('.status-content'),
								rules : [
									{ method : 'required', message : 'This field is required.' }
								]
							}
						],
						submitElement : r.find(".button-save"),
						accept : function () {
							
							if(vControl.attr('controlid') != undefined) {
								
								result.orderId = vControl.attr("orderid");
								
								result.controlTypeId = data.controlTypeId;
								result.inputTypeId = data.inputTypeId;
								
								result.key = o.find('.input-content').val()
								
								result.isMandatory = false;
	
								
								result.accountId = accountId;
								
								// update
								updateControlDetails({
									control : result,
									success : function(newData) {
			
										// merge data -> result
										$.extend(result, newData.result);
			
		   		        				viewControl({
			   		     					controlTypeId : result.controlTypeId,
			   		     					inputTypeId : result.inputTypeId,
			   		     					control : result
			   		     				});
			
										o.find('.control-edit').hide();
							        	o.find('.control-view').show();
        	
									},
									error: function() {
										alert("ERR");
									}
								});							
								
							}
							
						}
		        	});
		        	
		        	// cancel
	        		r.find('.button-cancel').click(function(event) {
	        			
	        			if(result != undefined) {
	        				
	        				// close to view mode
	        				
	        				viewControl({
	     						controlTypeId : result.controlTypeId,
	     						inputTypeId : result.inputTypeId,
	     						control : result
	     					});
	        				
	        				o.find('.control-edit').hide();
	        	        	o.find('.control-view').show();
	        				
	        			} else {
	        				
	        				vControl.remove();
	        				// demo
	        				s.remove();
	        				
	        				X.onDelete();
	        				
	        				/*
	        				if($("ul.questions li.question").length == 0) {
	        					$("#empty").show();
	        					$("#questions").hide();
	        				}
	        				*/
	        				
	        			}
	        			
	        			event.preventDefault();
	        		});
					
					
					break;
				}
				case 12: {
					
					
					var viewMode = function() {
						
						// view
						var viewIncludes = o.find('.view-container-control-includes').empty();
						var viewOther = o.find('.view-container-control-other').empty();
						o.find('.view-container-control-comment').hide(); // clear
						
						// Heading / descriptiove text
						
					};
					
					viewMode();
					
					
					// heading / descriptive text
        			var a = s.find('.container-control-includes');
                    a.empty();
                    
                    var containerControlOther = s.find('.container-control-other');
                	containerControlOther.empty();
					
						s.find(".container-control-comment").hide();
					
                	var r = $("<div>" +
   						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
   						"<div class=\"params\">" +
   							/*"<div class=\"param-name\"></div>" +*/
   							"<div class=\"param-value\">" +
   								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
   							"</div>" +
   							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
								"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
							"</div>" +
   						"</div>" +
   					"</div>").appendTo(placeholderControl);
					
					
					
					
					
					// fix for validation
                    o.find('.textarea-note').on('keydown', function() {
						if(o.find('.status-content').is(':visible')) {
							o.find('.status-content').hide();
						}
                    });
					
					
                	var v = null;
		        	v = new validator({
						elements : [
							{
								element : o.find('.input-content'),
								status : o.find('.status-content'),
								validate : function() {
									return (o.find('.textarea-note').val() == "");
								},
								rules : [
									{ method : 'required', message : 'This field is required.' }
								]
							}
						],
						submitElement : r.find(".button-save"),
						accept : function () {
							
							if(vControl.attr('controlid') != undefined) {
								
								result.orderId = vControl.attr("orderid");
								
								result.controlTypeId = data.controlTypeId;
								result.inputTypeId = data.inputTypeId;
								
								result.content = o.find('.input-content').val();
								result.isMandatory = false;
								result.note = (o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
								
								result.accountId = accountId;
	
								// update
								updateControlDetails({
									control : result,
									success : function(newData) {
			
										// merge data -> result
										$.extend(result, newData.result);
			
		   		        				viewControl({
			   		     					controlTypeId : result.controlTypeId,
			   		     					inputTypeId : result.inputTypeId,
			   		     					control : result
			   		     				});
			
										o.find('.control-edit').hide();
							        	o.find('.control-view').show();
        	
									},
									error: function() {
										alert("ERR");
									}
								});							
								
							}
							
						}
		        	});
		        	
		        	// cancel
	        		r.find('.button-cancel').click(function(event) {
	        			
	        			if(result != undefined) {
	        				
	        				// close to view mode
	        				
	        				viewControl({
	     						controlTypeId : result.controlTypeId,
	     						inputTypeId : result.inputTypeId,
	     						control : result
	     					});
	        				
	        				o.find('.control-edit').hide();
	        	        	o.find('.control-view').show();
	        				
	        			} else {
	        				
	        				vControl.remove();
	        				// demo
	        				s.remove();
	        				
	        				X.onDelete();
	        				
	        				/*
	        				if($("ul.questions li.question").length == 0) {
	        					$("#empty").show();
	        					$("#questions").hide();
	        				}
	        				*/
	        				
	        			}
	        			
	        			event.preventDefault();
	        		});
					
					
					break;
				
				}
        		case 6: {
        			
					var viewMode = function() {
						
						// view
						var viewIncludes = o.find('.view-container-control-includes').empty();
						var viewOther = o.find('.view-container-control-other').empty();
						o.find('.view-container-control-comment').hide(); // clear
					
						switch(data.inputTypeId) {
							
							case 0: {
								
                                // MMM / DD
                            	
                            	var b = $("<li>" +
                    				"<ul class=\"list-date\">" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label>" + (data.control.monthTitle != null ? data.control.monthTitle : messages.month) + "</label></div>" +
                    						"<div><select name=\"view_" + data.control.controlId + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label>" + (data.control.dayTitle != null ? data.control.dayTitle : messages.day) + "</label></div>" +
                    						"<div><select name=\"view_" + data.control.controlId + "\" class=\"days-list\" /></div>" +
                    					"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(viewOther);
                                
								
                                b.find(".months-list").loadSelect(messages.months);
                                b.find(".days-list").loadSelect(messages.days);
								
								break;
							}
							case 1: {
								
                        		// MMM / DD / YYYY
                            	
                            	var b = $("<li>" +
                        			"<ul class=\"list-date\">" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label>" + (data.control.monthTitle != null ? data.control.monthTitle : messages.month) + "</label></div>" +
                    						"<div><select name=\"view_" + data.control.controlId + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label>" + (data.control.dayTitle != null ? data.control.dayTitle : messages.day) + "</label></div>" +
                    						"<div><select name=\"view_" + data.control.controlId + "\" class=\"days-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                							"<div class=\"q-label\"><label>" + (data.control.yearTitle != null ? data.control.yearTitle : messages.year) + "</label></div>" +
                							"<div><input name=\"view_" + data.control.controlId + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                						"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(viewOther);
                                
                                b.find(".months-list").loadSelect(messages.months);
                                b.find(".days-list").loadSelect(messages.days);
								
								
								break;
							
							}
							case 2: {
								
                            	// MMM / DD / YYYY HH:MM
                        		
                            	var b = $("<li>" +
                        			"<ul class=\"list-date\">" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label>" + (data.control.monthTitle != null ? data.control.monthTitle : messages.month) + "</label></div>" +
                    						"<div><select name=\"view_" + data.control.controlId + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label>" + (data.control.dayTitle != null ? data.control.dayTitle : messages.day) + "</label></div>" +
                    						"<div><select name=\"view_" + data.control.controlId + "\" class=\"days-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                							"<div class=\"q-label\"><label>" + (data.control.yearTitle != null ? data.control.yearTitle : messages.year) + "</label></div>" +
                							"<div><input name=\"view_" + data.control.controlId + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                						"</li>" +
                					"</ul>" +
                					"<ul class=\"list-time\">" +
                						"<li>" +
            								"<div class=\"q-label\"><label>" + (data.control.hoursTitle != null ? data.control.hoursTitle : messages.hours) + "</label></div>" +
            								"<div><select name=\"view_" + data.control.controlId + "\" class=\"hours-list\" /></div>" +
            							"</li>" +
            							"<li>" +
											"<div class=\"q-label\">&nbsp;</div>" +
											"<div>:</div>" +
										"</li>" +
            							"<li>" +
        									"<div class=\"q-label\"><label>" + (data.control.minutesTitle != null ? data.control.minutesTitle : messages.minutes) + "</label></div>" +
        									"<div><select name=\"view_" + data.control.controlId + "\" class=\"minutes-list\" /></div>" +
        								"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(viewOther);
                                
                                b.find(".months-list").loadSelect(messages.months);
                                b.find(".days-list").loadSelect(messages.days);
                                b.find(".hours-list").loadSelect(messages.hours);
                                b.find(".minutes-list").loadSelect(messages.minutes);
							
								break;
							}
							case 3: {
								
                            	// DD / MMM
                            	
                            	var b = $("<li>" +
                        			"<ul class=\"list-date\">" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label>" + (data.control.dayTitle != null ? data.control.dayTitle : messages.day) + "</label></div>" +
                    						"<div><select name=\"view_" + data.control.controlId + "\" class=\"days-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                							"<div class=\"q-label\"><label>" + (data.control.monthTitle != null ? data.control.monthTitle : messages.month) + "</label></div>" +
                							"<div><select name=\"view_" + data.control.controlId + "\" class=\"months-list\" /></div>" +
                						"</li>" +
                					"</ul>" +
                    			"</li>").appendTo(viewOther);
                                
                                b.find(".days-list").loadSelect(messages.days);
                                b.find(".months-list").loadSelect(messages.months);
							
								break;
							}
							case 4: {
								
                            	// DD / MMM / YYYY
                        		
                            	var b = $("<li>" +
                            			"<ul class=\"list-date\">" +
                        					"<li>" +
                        						"<div class=\"q-label\"><label>" + (data.control.dayTitle != null ? data.control.dayTitle : messages.day) + "</label></div>" +
                        						"<div><select name=\"view_" + data.control.controlId + "\" class=\"days-list\" /></div>" +
                        					"</li>" +
                        					"<li>" +
                    							"<div class=\"q-label\"><label>" + (data.control.monthTitle != null ? data.control.monthTitle : messages.month) + "</label></div>" +
                    							"<div><select name=\"view_" + data.control.controlId + "\" class=\"months-list\" /></div>" +
                    						"</li>" +
                        					"<li>" +
                    							"<div class=\"q-label\"><label>" + (data.control.yearTitle != null ? data.control.yearTitle : messages.year) + "</label></div>" +
                    							"<div><input name=\"view_" + data.control.controlId + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                    						"</li>" +
                    					"</ul>" +
                        			"</li>").appendTo(viewOther);
                                
                                b.find(".days-list").loadSelect(messages.days);
                                b.find(".months-list").loadSelect(messages.months);
							
								break;
							}
							case 5: {
								
                            	// DD / MMM / YYYY HH:MM
                            	
                            	var b = $("<li>" +
                            			"<ul class=\"list-date\">" +
                        					"<li>" +
                        						"<div class=\"q-label\"><label>" + (data.control.dayTitle != null ? data.control.dayTitle : messages.day) + "</label></div>" +
                        						"<div><select name=\"view_" + data.control.controlId + "\" class=\"days-list\" /></div>" +
                        					"</li>" +
                        					"<li>" +
                    							"<div class=\"q-label\"><label>" + (data.control.monthTitle != null ? data.control.monthTitle : messages.month) + "</label></div>" +
                    							"<div><select name=\"view_" + data.control.controlId + "\" class=\"months-list\" /></div>" +
                    						"</li>" +
                        					"<li>" +
                    							"<div class=\"q-label\"><label>" + (data.control.yearTitle != null ? data.control.yearTitle : messages.year) + "</label></div>" +
                    							"<div><input name=\"view_" + data.control.controlId + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                    						"</li>" +
                    					"</ul>" +
                    					"<ul class=\"list-time\">" +
                    						"<li>" +
                								"<div class=\"q-label\"><label>" + (data.control.hoursTitle != null ? data.control.hoursTitle : messages.hours) + "</label></div>" +
                								"<div><select name=\"view_" + data.control.controlId + "\" class=\"hours-list\" /></div>" +
                							"</li>" +
                							"<li>" +
												"<div class=\"q-label\">&nbsp;</div>" +
												"<div>:</div>" +
											"</li>" +
                							"<li>" +
            									"<div class=\"q-label\"><label>" + (data.control.minutesTitle != null ? data.control.minutesTitle : messages.minutes) + "</label></div>" +
            									"<div><select name=\"view_" + data.control.controlId + "\" class=\"minutes-list\" /></div>" +
            								"</li>" +
                        				"</ul>" +
                        			"</li>").appendTo(viewOther);
                        	
                                b.find(".days-list").loadSelect(messages.days);
                                b.find(".months-list").loadSelect(messages.months);
                                b.find(".hours-list").loadSelect(messages.hours);
                                b.find(".minutes-list").loadSelect(messages.minutes);
								
								break;
							}
							case 6: {
								
                        		// MMM
                        	
                            	var b = $("<li>" +
                            			"<ul class=\"list-date\">" +
                        					"<li>" +
                    							"<div class=\"q-label\"><label>" + (data.control.monthTitle != null ? data.control.monthTitle : messages.month) + "</label></div>" +
                    							"<div><select name=\"view_" + data.control.controlId + "\" class=\"months-list\" /></div>" +
                    						"</li>" +
                    					"</ul>" +
                        			"</li>").appendTo(viewOther);
                                
                                b.find(".months-list").loadSelect(messages.months);
							
								break;
							}
							case 7: {
								
                        		// DD
                        		
                        		var b = $("<li>" +
                            			"<ul class=\"list-date\">" +
                        					"<li>" +
                        						"<div class=\"q-label\"><label>" + (data.control.dayTitle != null ? data.control.dayTitle : messages.day) + "</label></div>" +
                        						"<div><select name=\"view_" + data.control.controlId + "\" class=\"days-list\" /></div>" +
                        					"</li>" +
                    					"</ul>" +
                        			"</li>").appendTo(viewOther);
                        		
                                b.find(".days-list").loadSelect(messages.days);
							
								break;
							}
							case 8: {
								
                                // YYYY
                                
                                var b = $("<li>" +
                            			"<ul class=\"list-date\">" +
                        					"<li>" +
                    							"<div class=\"q-label\"><label>" + (data.control.yearTitle != null ? data.control.yearTitle : messages.year) + "</label></div>" +
                    							"<div><input name=\"view_" + data.control.controlId + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                    						"</li>" +
                    					"</ul>" +
                        			"</li>").appendTo(viewOther);
							
								break;
							}
							case 9: {
								
                        		// HH : MM
                            	
                            	var b = $("<li>" +
                            			"<ul class=\"list-time\">" +
                    						"<li>" +
                								"<div class=\"q-label\"><label>" + (data.control.hoursTitle != null ? data.control.hoursTitle : messages.hours) + "</label></div>" +
                								"<div><select name=\"view_" + data.control.controlId + "\" class=\"hours-list\" /></div>" +
                							"</li>" +
                							"<li>" +
    											"<div class=\"q-label\">&nbsp;</div>" +
    											"<div>:</div>" +
    										"</li>" +
                							"<li>" +
            									"<div class=\"q-label\"><label>" + (data.control.minutesTitle != null ? data.control.minutesTitle : messages.minutes) + "</label></div>" +
            									"<div><select name=\"view_" + data.control.controlId + "\" class=\"minutes-list\" /></div>" +
            								"</li>" +
                        				"</ul>" +
                        			"</li>").appendTo(viewOther);
                                
                                b.find(".hours-list").loadSelect(messages.hours);
                                b.find(".minutes-list").loadSelect(messages.minutes);
							
								break;
							}
							
						}
						
						
					};
					
					viewMode();
					
					
					
        			// date/time
        			
        			var a = s.find('.container-control-includes');
                    a.empty();
                    
                    var containerControlOther = s.find('.container-control-other');
                	containerControlOther.empty();
					
						s.find(".container-control-comment").hide();
        			
                	var r = $("<div>" +
                		"<div style=\"clear: both;\">" +
                			"<div>Date/Time format:</div>" +
                			"<div>" +
                				"<select class=\"select-datetime-format\">" +
                					"<option value=\"0\">MMM/dd - Month/Day</option>" +
                					"<option value=\"1\">MMM/dd/yyyy - Month/Day/Year</option>" +
                					"<option value=\"2\">MMM/dd/yyyy HH:mm - Month/Day/Year Hours:Minutes</option>" +
                					"<option value=\"3\">dd/MMM - Day/Month</option>" +
                					"<option value=\"4\">dd/MMM/yyyy - Day/Month/Year</option>" +
                					"<option value=\"5\">dd/MMM/yyyy HH:mm - Day/Month/Year Hours:Minutes</option>" +
                					"<option value=\"6\">MMM - Month</option>" +
                					"<option value=\"7\">dd - Day</option>" +
                					"<option value=\"8\">yyyy - Year</option>" +
                					"<option value=\"9\">HH:mm - Hours:Minutes</option>" +
                				"</select>" +
                			"</div>" +
   						"</div>" +
   						"<div style=\"clear: both\">" +
   							"<ul class=\"list-labels\"></ul>" +
   						"</div>" +
   						"<div style=\"overflow: hidden; height: 20px;clear: both\"></div>" +
   						"<div class=\"params\">" +
   							/*"<div class=\"param-name\"></div>" +*/
   							"<div class=\"param-value\">" +
   								"<a href=\"#\" title=\"Save\" class=\"button-blue button-save\"><span>Save</span></a>" +
   							"</div>" +
   							"<div class=\"param-value\" style=\"line-height: 20px;\">" +
								"<a href=\"#\" title=\"Cancel\" class=\"button-cancel\"><span>Cancel</span></a>" +
							"</div>" +
   						"</div>" +
   					"</div>").appendTo(placeholderControl);
        			
        			
                	var P = r.find('ul.list-labels');
                	var V = function(formatId) {
                		
                		P.empty();
                		containerControlOther.empty();
                		// set labels and preview
						
						// change select format
						r.find(".select-datetime-format option[value=" + formatId + "]").attr("selected", "selected");
						
                		
                		switch(formatId) {
	                		case 0: {
	                			
	                			// preview
	                			// MMM/dd
	                			var b = $("<li>" +
                    				"<ul class=\"list-date\">" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-month\">" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-day\">" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"days-list\" /></div>" +
                    					"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
	                			
                                
                                b.find(".months-list").loadSelect(messages.months);
                                b.find(".days-list").loadSelect(messages.days);
	                			
	                			// labels
	                			var t = $("<li>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "\" class=\"input-month-field\" />" +
                                	"</div>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "\" class=\"input-day-field\" />" +
                                	"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-month-field').bind("keyup", function() {
	        						containerControlOther.find('.label-month').text($(this).val());
	        					});
	                			
	                			t.find('.input-day-field').bind("keyup", function() {
	        						containerControlOther.find('.label-day').text($(this).val());
	        					});
	                			
	                			
	                			break;
	                		}
	                		case 1: {
	                			
	                			// preview
	                			// MMM/dd/yyyy
	                			
	                			var b = $("<li>" +
                        			"<ul class=\"list-date\">" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-month\">" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-day\">" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day)+ "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"days-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                							"<div class=\"q-label\"><label class=\"label-year\">" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "</label></div>" +
                							"<div><input name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                						"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
                                
                                b.find(".months-list").loadSelect(messages.months);
                                b.find(".days-list").loadSelect(messages.days);
                                
                                
                                // labels
                                var t = $("<li>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "\" class=\"input-month-field\" />" +
                                	"</div>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "\" class=\"input-day-field\" />" +
                                	"</div>" +
                                	"<div>" +
                            			"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "\" class=\"input-year-field\" />" +
                            		"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-month-field').bind("keyup", function() {
	        						containerControlOther.find('.label-month').text($(this).val());
	        					});
	                			
	                			t.find('.input-day-field').bind("keyup", function() {
	        						containerControlOther.find('.label-day').text($(this).val());
	        					});
	                			
	                			t.find('.input-year-field').bind("keyup", function() {
	        						containerControlOther.find('.label-year').text($(this).val());
	        					});
                                
	                			
	                			break;
	                		}
	                		case 2: {
	                			
	                			// preview
	                			// MMM/dd/yyyy HH:mm
	                			
	                			var b = $("<li>" +
                        			"<ul class=\"list-date\">" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-month\">" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-day\">" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"days-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                							"<div class=\"q-label\"><label class=\"label-year\">" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "</label></div>" +
                							"<div><input name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                						"</li>" +
                					"</ul>" +
                					"<ul class=\"list-time\">" +
                						"<li>" +
            								"<div class=\"q-label\"><label class=\"label-hours\">" + (data.control != undefined ? (data.control.hoursTitle != null ? data.control.hoursTitle : messages._hours) : messages._hours) + "</label></div>" +
            								"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"hours-list\" /></div>" +
            							"</li>" +
            							"<li>" +
											"<div class=\"q-label\">&nbsp;</div>" +
											"<div>:</div>" +
										"</li>" +
            							"<li>" +
        									"<div class=\"q-label\"><label class=\"label-minutes\">" + (data.control != undefined ? (data.control.minutesTitle != null ? data.control.minutesTitle : messages._minutes) : messages._minutes) + "</label></div>" +
        									"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"minutes-list\" /></div>" +
        								"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
                                
                                b.find(".months-list").loadSelect(messages.months);
                                b.find(".days-list").loadSelect(messages.days);
                                b.find(".hours-list").loadSelect(messages.hours);
                                b.find(".minutes-list").loadSelect(messages.minutes);
                                
                                
                                // labels
                                var t = $("<li>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month)+ "\" class=\"input-month-field\" />" +
                                	"</div>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "\" class=\"input-day-field\" />" +
                                	"</div>" +
                                	"<div>" +
                            			"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "\" class=\"input-year-field\" />" +
                            		"</div>" +
                            		"<div>" +
                        				"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.hoursTitle != null ? data.control.hoursTitle : messages._hours) : messages._hours) + "\" class=\"input-hours-field\" />" +
                        			"</div>" +
                        			"<div>" +
                    					"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.minutesTitle != null ? data.control.minutesTitle : messages._minutes) : messages._minutes) + "\" class=\"input-minutes-field\" />" +
                    				"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-month-field').bind("keyup", function() {
	        						containerControlOther.find('.label-month').text($(this).val());
	        					});
	                			
	                			t.find('.input-day-field').bind("keyup", function() {
	        						containerControlOther.find('.label-day').text($(this).val());
	        					});
	                			
	                			t.find('.input-year-field').bind("keyup", function() {
	        						containerControlOther.find('.label-year').text($(this).val());
	        					});
	                			
	                			t.find('.input-hours-field').bind("keyup", function() {
	        						containerControlOther.find('.label-hours').text($(this).val());
	        					});
	                			
	                			t.find('.input-minutes-field').bind("keyup", function() {
	        						containerControlOther.find('.label-minutes').text($(this).val());
	        					});
                                
	                			
	                			break;
	                		}
	                		case 3: {
	                			
	                			// preview
	                			// dd/MMM
	                			
	                			var b = $("<li>" +
                    				"<ul class=\"list-date\">" +
                    					"<li>" +
            								"<div class=\"q-label\"><label class=\"label-day\">" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "</label></div>" +
            								"<div><select name=\"" + (data.controlId != undefined ? data.control.controlId : tempId) + "\" class=\"days-list\" /></div>" +
            							"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-month\">" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "</label></div>" +
                    						"<div><select name=\"" + (data.controlId != undefined ? data.control.controlId : tempId) + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
	                			
                                b.find(".days-list").loadSelect(messages.days);
                                b.find(".months-list").loadSelect(messages.months);
	                			
	                			// labels
	                			var t = $("<li>" +
	                				"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "\" class=\"input-day-field\" />" +
                                	"</div>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "\" class=\"input-month-field\" />" +
                                	"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-day-field').bind("keyup", function() {
	        						containerControlOther.find('.label-day').text($(this).val());
	        					});
	                			
	                			t.find('.input-month-field').bind("keyup", function() {
	        						containerControlOther.find('.label-month').text($(this).val());
	        					});
	                			
	                			break;
	                			
	                		}
	                		case 4: {
	                			
	                			// preview
	                			// dd/MMM/yyyy
	                			
	                			var b = $("<li>" +
                        			"<ul class=\"list-date\">" +
                        				"<li>" +
            								"<div class=\"q-label\"><label class=\"label-day\">" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "</label></div>" +
            								"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"days-list\" /></div>" +
            							"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-month\">" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                							"<div class=\"q-label\"><label class=\"label-year\">" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "</label></div>" +
                							"<div><input name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                						"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
                                
                                b.find(".days-list").loadSelect(messages.days);
                                b.find(".months-list").loadSelect(messages.months);
                                
                                // labels
                                var t = $("<li>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "\" class=\"input-day-field\" />" +
                                	"</div>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "\" class=\"input-month-field\" />" +
                                	"</div>" +
                                	"<div>" +
                            			"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "\" class=\"input-year-field\" />" +
                            		"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-day-field').bind("keyup", function() {
	        						containerControlOther.find('.label-day').text($(this).val());
	        					});
	                			
	                			t.find('.input-month-field').bind("keyup", function() {
	        						containerControlOther.find('.label-month').text($(this).val());
	        					});
	                			
	                			t.find('.input-year-field').bind("keyup", function() {
	        						containerControlOther.find('.label-year').text($(this).val());
	        					});
	                			
	                			break;
	                			
	                		}
	                		case 5: {
	                			
	                			// preview
	                			// dd/MMM/yyyy HH:mm
	                			
	                			var b = $("<li>" +
                        			"<ul class=\"list-date\">" +
                        				"<li>" +
            								"<div class=\"q-label\"><label class=\"label-day\">" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "</label></div>" +
            								"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"days-list\" /></div>" +
            							"</li>" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-month\">" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    					"<li>" +
                							"<div class=\"q-label\"><label class=\"label-year\">" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "</label></div>" +
                							"<div><input name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                						"</li>" +
                					"</ul>" +
                					"<ul class=\"list-time\">" +
                						"<li>" +
            								"<div class=\"q-label\"><label class=\"label-hours\">" + (data.control != undefined ? (data.control.hoursTitle != null ? data.control.hoursTitle : messages._hours) : messages._hours) + "</label></div>" +
            								"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"hours-list\" /></div>" +
            							"</li>" +
            							"<li>" +
											"<div class=\"q-label\">&nbsp;</div>" +
											"<div>:</div>" +
										"</li>" +
            							"<li>" +
        									"<div class=\"q-label\"><label class=\"label-minutes\">" + (data.control != undefined ? (data.control.minutesTitle != null ? data.control.minutesTitle : messages._minutes) : messages._minutes) + "</label></div>" +
        									"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"minutes-list\" /></div>" +
        								"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
                                
	                			b.find(".days-list").loadSelect(messages.days);
                                b.find(".months-list").loadSelect(messages.months);
                                b.find(".hours-list").loadSelect(messages.hours);
                                b.find(".minutes-list").loadSelect(messages.minutes);
                                
                                
                                // labels
                                var t = $("<li>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "\" class=\"input-day-field\" />" +
                                	"</div>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "\" class=\"input-month-field\" />" +
                                	"</div>" +
                                	"<div>" +
                            			"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "\" class=\"input-year-field\" />" +
                            		"</div>" +
                            		"<div>" +
                        				"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.hoursTitle != null ? data.control.hoursTitle : messages._hours) : messages._hours) + "\" class=\"input-hours-field\" />" +
                        			"</div>" +
                        			"<div>" +
                    					"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.minutesTitle != null ? data.control.minutesTitle : messages._minutes) : messages._minutes) + "\" class=\"input-minutes-field\" />" +
                    				"</div>" +
                                "</li>").appendTo(P);
	                			
                                t.find('.input-day-field').bind("keyup", function() {
	        						containerControlOther.find('.label-day').text($(this).val());
	        					});
                                
	                			t.find('.input-month-field').bind("keyup", function() {
	        						containerControlOther.find('.label-month').text($(this).val());
	        					});
	                			
	                			t.find('.input-year-field').bind("keyup", function() {
	        						containerControlOther.find('.label-year').text($(this).val());
	        					});
	                			
	                			t.find('.input-hours-field').bind("keyup", function() {
	        						containerControlOther.find('.label-hours').text($(this).val());
	        					});
	                			
	                			t.find('.input-minutes-field').bind("keyup", function() {
	        						containerControlOther.find('.label-minutes').text($(this).val());
	        					});
	                			
	                			break;
	                			
	                		}
	                		case 6: {
	                			
	                			// preview
	                			// MMM
	                			
	                			var b = $("<li>" +
                    				"<ul class=\"list-date\">" +
                    					"<li>" +
                    						"<div class=\"q-label\"><label class=\"label-month\">" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "</label></div>" +
                    						"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"months-list\" /></div>" +
                    					"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
	                			
                                b.find(".months-list").loadSelect(messages.months);
	                			
	                			// labels
	                			var t = $("<li>" +
                                	"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.monthTitle != null ? data.control.monthTitle : messages.month) : messages.month) + "\" class=\"input-month-field\" />" +
                                	"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-month-field').bind("keyup", function() {
	        						containerControlOther.find('.label-month').text($(this).val());
	        					});
	                			
	                			break;
	                			
	                		}
	                		case 7: {
	                			
	                			// preview
	                			// dd
	                			
	                			var b = $("<li>" +
                    				"<ul class=\"list-date\">" +
                    					"<li>" +
            								"<div class=\"q-label\"><label class=\"label-day\">" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "</label></div>" +
            								"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"days-list\" /></div>" +
            							"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
	                			
                                b.find(".days-list").loadSelect(messages.days);
	                			
	                			// labels
	                			var t = $("<li>" +
	                				"<div>" +
                                		"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.dayTitle != null ? data.control.dayTitle : messages.day) : messages.day) + "\" class=\"input-day-field\" />" +
                                	"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-day-field').bind("keyup", function() {
	        						containerControlOther.find('.label-day').text($(this).val());
	        					});
	                			
	                			break;
	                			
	                		}
	                		case 8: {
	                			
	                			// preview
	                			// yyyy
	                			
	                			var b = $("<li>" +
                        			"<ul class=\"list-date\">" +
                    					"<li>" +
                							"<div class=\"q-label\"><label class=\"label-year\">" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "</label></div>" +
                							"<div><input name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" type=\"text\" maxlength=\"4\" size=\"4\" class=\"year-field text-field\" /></div>" +
                						"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
                                
                                // labels
                                var t = $("<li>" +
                                	"<div>" +
                            			"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.yearTitle != null ? data.control.yearTitle : messages.year) : messages.year) + "\" class=\"input-year-field\" />" +
                            		"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-year-field').bind("keyup", function() {
	        						containerControlOther.find('.label-year').text($(this).val());
	        					});
	                			
	                			break;
	                			
	                		}
	                		case 9: {
	                			
	                			// preview
	                			// HH:mm
	                			
	                			var b = $("<li>" +
                					"<ul class=\"list-time\">" +
                						"<li>" +
            								"<div class=\"q-label\"><label class=\"label-hours\">" + (data.control != undefined ? (data.control.hoursTitle != null ? data.control.hoursTitle : messages._hours) : messages._hours) + "</label></div>" +
            								"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"hours-list\" /></div>" +
            							"</li>" +
            							"<li>" +
											"<div class=\"q-label\">&nbsp;</div>" +
											"<div>:</div>" +
										"</li>" +
            							"<li>" +
        									"<div class=\"q-label\"><label class=\"label-minutes\">" + (data.control != undefined ? (data.control.minutesTitle != null ? data.control.minutesTitle : messages._minutes) : messages._minutes) + "</label></div>" +
        									"<div><select name=\"" + (data.control != undefined ? data.control.controlId : tempId) + "\" class=\"minutes-list\" /></div>" +
        								"</li>" +
                    				"</ul>" +
                    			"</li>").appendTo(containerControlOther);
                                
                                b.find(".hours-list").loadSelect(messages.hours);
                                b.find(".minutes-list").loadSelect(messages.minutes);
                                
                                // labels
                                var t = $("<li>" +
                            		"<div>" +
                        				"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.hoursTitle != null ? data.control.hoursTitle : messages._hours) : messages._hours) + "\" class=\"input-hours-field\" />" +
                        			"</div>" +
                        			"<div>" +
                    					"<input type=\"text\" value=\"" + (data.control != undefined ? (data.control.minutesTitle != null ? data.control.minutesTitle : messages._minutes) : messages._minutes) + "\" class=\"input-minutes-field\" />" +
                    				"</div>" +
                                "</li>").appendTo(P);
	                			
	                			t.find('.input-hours-field').bind("keyup", function() {
	        						containerControlOther.find('.label-hours').text($(this).val());
	        					});
	                			
	                			t.find('.input-minutes-field').bind("keyup", function() {
	        						containerControlOther.find('.label-minutes').text($(this).val());
	        					});
	                			
	                			break;
	                			
	                		}
                		}
                		
                	};
                	
                	r.find('.select-datetime-format').change(function () {
                        V(parseInt($(this).val()));
                    });
                	
                	
                	
                	// set format
                	V(data.inputTypeId);
					
                	
        			var v = null;
		        	v = new validator({
						elements : [
							{
								element : o.find('.input-content'),
								status : o.find('.status-content'),
								rules : [
									{ method : 'required', message : 'This field is required.' }
								]
							}
						],
						submitElement : r.find(".button-save"),
						accept : function () {
							
							if(vControl.attr('controlid') != undefined) {
								
								if(result.controlTypeId != data.controlTypeId) {
									
									
									delete __obj; __obj = { };
									
									__obj.orderId = vControl.attr("orderid"); // updated orderid ->
									__obj.controlTypeId = data.controlTypeId;
									__obj.inputTypeId = parseInt(r.find('.select-datetime-format').val());
									__obj.content = o.find('.input-content').val();
									__obj.isMandatory = o.find('.checkbox-mandatory').prop('checked');
								
	                                if(o.find('.checkbox-link').is(':checked')) {
	                                	if(o.find('.select-link-type').val() == 1) {
	                                		// link to image
	                                		if(o.find('.input-image').val() != "") {
	                                			__obj.linkTypeId = 1;
	                                			__obj.link = o.find('.input-image').val();
	                                		} else {
	                                			__obj.linkTypeId = 0;
	                                			__obj.link = null;
	                                		}
	                                	}
	                                	if(o.find('.select-link-type').val() == 2) {
	                                		// embed code
	                                		if(o.find('.textarea-embed-code').val() != "") {
	                                			__obj.linkTypeId = 2;
	                                			__obj.link = o.find('.textarea-embed-code').val();
	                                		} else {
	                                			__obj.linkTypeId = 0;
	                                			__obj.link = null;
	                                		}
	                                	}
	                                } else {
	                                	__obj.linkTypeId = 0;
	                        			__obj.link = null;
	                                }
								
									__obj.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
								
									__obj.monthTitle = P.find('.input-month-field').length ? P.find('.input-month-field').val() : undefined;
									__obj.dayTitle = P.find('.input-day-field').length ? P.find('.input-day-field').val() : undefined;
									__obj.yearTitle = P.find('.input-year-field').length ? P.find('.input-year-field').val() : undefined;
									__obj.hoursTitle = P.find('.input-hours-field').length ? P.find('.input-hours-field').val() : undefined;
									__obj.minutesTitle = P.find('.input-minutes-field').length ? P.find('.input-minutes-field').val() : undefined;
									
									
					        		deleteControl({
					        			accountId : accountId,
					        			controlId : result.controlId,
					        			success : function() {
				
											X.onCreate({
												control : __obj, // result,
												success : function(newData) {									
				   		    						
													result = newData;
													
													// merge data -> result
				   		    						//$.extend(result, newData);
													
													// update ids and types
													vControl.attr({ 'controlid' : result.controlId, 'controltypeid' : result.controlTypeId });
	   		    						
					   		        				viewControl({
						   		     					controlTypeId : result.controlTypeId,
						   		     					inputTypeId : result.inputTypeId,
						   		     					control : result
						   		     				});
	   		    						
				   		    						o.find('.control-edit').hide();
				   		    			        	o.find('.control-view').show();
													
													
												},
												error: function() {
													console.log("ERRRRRRRR");
												}
											});
				
					        			},
					        			error: function(error) {

					        			}
					        		});
								
									
								} else {
										
									result.orderId = vControl.attr("orderid"); // updated orderid ->
								
									result.controlTypeId = data.controlTypeId;
									result.inputTypeId = parseInt(r.find('.select-datetime-format').val());
								
									result.content = o.find('.input-content').val();
									result.isMandatory = o.find('.checkbox-mandatory').prop('checked');
								
	                                if(o.find('.checkbox-link').is(':checked')) {
	                                	if(o.find('.select-link-type').val() == 1) {
	                                		// link to image
	                                		if(o.find('.input-image').val() != "") {
	                                			result.linkTypeId = 1;
	                                			result.link = o.find('.input-image').val();
	                                		} else {
	                                			result.linkTypeId = 0;
	                                			result.link = null;
	                                		}
	                                	}
	                                	if(o.find('.select-link-type').val() == 2) {
	                                		// embed code
	                                		if(o.find('.textarea-embed-code').val() != "") {
	                                			result.linkTypeId = 2;
	                                			result.link = o.find('.textarea-embed-code').val();
	                                		} else {
	                                			result.linkTypeId = 0;
	                                			result.link = null;
	                                		}
	                                	}
	                                } else {
	                                	result.linkTypeId = 0;
	                        			result.link = null;
	                                }
								
									result.note = (o.find('.checkbox-include-note').prop('checked') && o.find('.textarea-note').val() != "" ? o.find('.textarea-note').val() : undefined);
								
									result.monthTitle = P.find('.input-month-field').length ? P.find('.input-month-field').val() : undefined;
									result.dayTitle = P.find('.input-day-field').length ? P.find('.input-day-field').val() : undefined;
									result.yearTitle = P.find('.input-year-field').length ? P.find('.input-year-field').val() : undefined;
									result.hoursTitle = P.find('.input-hours-field').length ? P.find('.input-hours-field').val() : undefined;
									result.minutesTitle = P.find('.input-minutes-field').length ? P.find('.input-minutes-field').val() : undefined;
								
									result.accountId = accountId;
									
									// update
									updateControlDetails({
										control : result,
										success : function(newData) {
			
											// merge data -> result
											$.extend(result, newData.result);
			
			   		        				viewControl({
				   		     					controlTypeId : result.controlTypeId,
				   		     					inputTypeId : result.inputTypeId,
				   		     					control : result
				   		     				});
			
											o.find('.control-edit').hide();
								        	o.find('.control-view').show();
        	
										},
										error: function() {
											alert("ERR");
										}
									});
									
								}
							
							}
							
						}
		        	});
		        	
		        	// cancel
	        		r.find('.button-cancel').click(function(event) {
	        			
	        			if(result != undefined) {
	        				
	        				// close to view mode
	        				
	        				viewControl({
	     						controlTypeId : result.controlTypeId,
	     						inputTypeId : result.inputTypeId,
	     						control : result
	     					});
	        				
	        				o.find('.control-edit').hide();
	        	        	o.find('.control-view').show();
	        				
	        			} else {
	        				
	        				vControl.remove();
	        				// demo
	        				s.remove();
	        				
	        				X.onDelete();
	        				
	        				/*
	        				if($("ul.questions li.question").length == 0) {
	        					$("#empty").show();
	        					$("#questions").hide();
	        				}
	        				*/
	        				
	        			}
	        			
	        			event.preventDefault();
	        		});
        			
        			break;
        			
        		}
        			
        	}
        	
        	
        	
        };
        
		
		
		
		
		
		
		
        
        
        if(X.control != undefined) {
        	result = X.control;
        }
        
        // change question type
        o.find('.select-question-types').bind("change keyup", function(){
			
			var controlTypeId = parseInt($(this).val());
			var inputTypeId = (o.find('.select-question-types option:selected').attr("input_type_id") != undefined ? parseInt(o.find('.select-question-types option:selected').attr("input_type_id")) : 0);
			
			if(result != undefined) {
				viewControl({
					controlTypeId : controlTypeId,
					inputTypeId : inputTypeId,
					control : result,
					previousState : true
				});
			} else {
				
				//console.log("result is undefined ->>>>>> ");
				
				viewControl({
					controlTypeId : controlTypeId,
					inputTypeId : inputTypeId,
					previousState : true
				});
			}
			
		});
        
        
        // set selected control type
		//console.log("DEMO -> VIEW -> controlTypeId -> " + X.controlTypeId);
        
		s = getDemoTemplate(X.controlTypeId);
		
		/*
        s = $("<li class=\"demo-question\">" +
				"<div class=\"container-control-content demo-question-heading\">" +
					(X.controlTypeId == 11 || X.controlTypeId == 12 ? "" : "<span class=\"label-control-number\"></span>") +
					"<span class=\"label-mandatory\" style=\"display: none;\">*&nbsp;</span>" +
					"<span class=\"header-control-content\">Enter your question here</span>" +
				"</div>" +
				"<div class=\"demo-question-content\">" +
					"<div class=\"container-control-embed q-image\" style=\"display: none;\"></div>" +
					"<div class=\"container-control-notes\">" +
						"<div class=\"label-note q-help\" style=\"display: none;\">You can enter some information about this question here.</div>" +
					"</div>" +
					"<ul class=\"container-control-includes\"></ul>" +
					"<div class=\"container-control-other\"></div>" +
        			"<div class=\"container-control-comment q-comment-container\" style=\"display: none\">" +
        				"<div class=\"q-comment\"><label class=\"label-comment\">Please help us understand why you selected this answer</label></div>" +
        				"<textarea autocomplete=\"off\"></textarea>" +
        			"</div>" +
				"</div>" +
			"</li>");
		
        */
        
        
		
		
		//console.log("demo-> orderid -> " + X.demoOrderId);
        
        if(X.demoOrderId != undefined) {
        	if(X.demoOrderId == 0) {
        		// before
    			$("ul.list-controls li.demo-question")
    				.first()
    				.before(s);
        		
        	} else {
        		// after
        		var found = false;
        		$("ul.list-controls li.demo-question").each(function (i, el) {
    				var current = $(el);
    				if(current.attr("orderid") == X.demoOrderId) {
    					found = true;
    					// after
    					current
    						.before(s);
    				}
    			});
        		if(!found) {
        			$("ul.list-controls li.demo-question:last").after(s);
        		}
        	}
        } else {
        	s.appendTo(".list-controls");
        }
		
        
        
        
        
		
		
		
		
		// question text
		o.find('.input-content')
		.unbind("keyup")
		.bind("keyup", function() {
			s.find('.header-control-content').html($(this).val().replace(/\n/g, "<br/>"));
		});
		
		// mandatory
		o.find('.checkbox-mandatory').change(function() {
			if($(this).prop("checked")) {
				s.find('.label-mandatory').show();
			} else {
				s.find('.label-mandatory').hide();
			}
		});
		
		// link
		o.find(".checkbox-link").change(function() {
			if ($(this).prop("checked")) {
				o.find(".container-link").show();
				s.find('.container-control-embed').show();
			} else {
				o.find(".container-link").hide();
				s.find('.container-control-embed').hide();
			}
		});
		
		// note
		o.find(".checkbox-include-note").change(function() {
			if($(this).prop("checked")) {
				o.find(".container-note").show();
				s.find('.label-note').show();
			} else {
				o.find(".container-note").hide();
				s.find('.label-note').hide();
			}
		});
		
		
		/*
		var showLink = function(linkTypeId) {
				
			console.log(linkTypeId + "_________" + result.link);
			
		};
		
		// link type
		o.find(".select-link-type")
		.unbind('change')
		.change(function() {
			
			if($(this).val() == 1) {
				o.find('.container-embed-code').hide();
				o.find('.container-image').show();
				
				showLink(1);
			}
			if($(this).val() == 2) {
				o.find('.container-image').hide();
				o.find('.container-embed-code').show();
				
				showLink(2);
			}
		});
		*/
		
		
		o.find(".textarea-note")
		.unbind("keyup")
		.bind("keyup", function() {
			s.find('.label-note').html(converter.makeHtml($(this).val()));
		});
       
		
		
		
		
		// init
		viewControl({
			controlTypeId : X.controlTypeId,
			inputTypeId : X.inputTypeId,
			control : result
		});
        
        
		
		//console.log(JSON.stringify(X));
        
        // append inner element into parent
        o.appendTo(vControl);
		
		
		
		
		
        // edit
        o.find('.button-edit-question')
        .click(function(event){
        	
        	if (lastEditableControl != null && lastEditableControl != o) {
                lastEditableControl.find('.control-edit').hide();
                lastEditableControl.find('.control-view').show();
                
                lastEditableControl.parent().removeClass('selected');
            }
            lastEditableControl = o;
			
			viewControl({
				controlTypeId : result.controlTypeId,
				inputTypeId : result.inputTypeId,
				control : result
			});
        	
        	o.find('.control-edit').show();
        	o.find('.control-view').hide();
			
			
			// set focus on textarea
			o.find('.input-content').focus();
			
			
			
			
			
			//console.log("----->>>>" + result.controlTypeId);
			
			
			if(result.controlTypeId != 11) {
			
				// scroll and hightlight
				var $demoItems = $('.block-preview').find('ul.list-controls').children('li.demo-question');
				var $demoItemsScrollPositions = new Array();
	
				// cache scroll positions for each item
				$demoItems.each(function(i, el){
					$demoItemsScrollPositions[i] = Math.round($(el).offset().top - $('.block-preview').offset().top) - 10;
				});
			
				$('.block-preview').find('ul.list-controls li.demo-question').each(function(i, el) {
					if(i == vControl.index()) {
					
						var targetElement = $(el);
					
						$('.block-preview')
						.scrollTo($demoItemsScrollPositions[i] + $('.block-preview')
						.scrollTop(), 
						function() {
							// highlight element
							runEffect(targetElement);	
						});
					
					}
				});
			
			}
			
			
			
        	event.preventDefault();
        });
		
		
		// delete
        o.find('.button-delete-question')
        .click(function(event) {
        	
        	if(result != undefined) {
        		
        		deleteControl({
        			accountId : accountId,
        			controlId: result.controlId,
        			success : function() {
        				
        				vControl.remove();
        				// demo
        				s.remove();
        				
        				
        				X.onDelete();
        				
        				/*
        				if($("ul.questions li.question").length == 0) {
        					$("#empty").show();
        					$("#questions").hide();
        				}
        				*/
        				
        			},
        			error: function(error) {

        			}
        		});
        		
        	}
        	
        	event.preventDefault();
        });
		
		// fix to build splitButton after element is appended
		// copy
        o.find('.button-copy-question')
        .splitButton({
        	label : "Copy",
        	click : function() {
        		X.onCopy(vControl);
        	},
        	pop : function(lastCombo) {
        		
        		var _a = $("<div style=\"padding: 10px; width: 320px;\">" +
					"<div>Copy to page:</div>" +
					"<div><select class=\"select-copy-to-pages\"></select></div>" +
					"<div class=\"container-copy-to-flow\">" +
						"<div><select class=\"select-copy-to-question-position\"><option value=\"0\">After</option><option value=\"1\">Before</option></select></div>" +
						"<div>question:</div>" +
						"<div><select class=\"select-copy-to-questions\" style=\"width: 320px\"></select></div>" +
					"</div>" +
					"<div class=\"params\" style=\"padding-bottom: 0px; min-height: 20px; padding-top: 10px;\">" +
						"<div class=\"param-value\"><a class=\"button-blue button-copy-to\" title=\"Copy\"><span>Copy</span></a></div>" +
						"<div class=\"param-value\" style=\"line-height: 20px;\"><a class=\"button-copy-to-cancel\" style=\"margin-left: 6px; cursor: pointer;\" title=\"Cancel\">Cancel</a></div>" +
					"</div>" +
				"</div>");
        		
        		var selectCopyToPages = _a.find('.select-copy-to-pages');
        		
        		var e = selectCopyToPages[0].options;
        		$("#sortable li").each(function(i, el) {
        			var tab = $(el);
        			var k = new Option((tab.index() + 1), tab.attr("data-value"));
         		   	try {
         			   e.add(k);
         		   	} catch (ex) {
         			   e.add(k, null)
         		   	}
        		});
        		
        		/*
        		for(var i = 0; i < survey.sheets.list.length; i++) {
        			var k = new Option(survey.sheets.list[i].pageNumber, survey.sheets.list[i].sheetId);
         		   	try {
         			   e.add(k);
         		   	} catch (ex) {
         			   e.add(k, null)
         		   	}
        		}
        		*/
        		
        		
        		var selectCopyToQuestions = _a.find('.select-copy-to-questions');
        		
        		selectCopyToPages.bind('change', function() {
        			// fill questions per page
            		getControlsBySheet({
            			accountId : accountId,
            			sheetId : $(this).val(),
            			success : function(controlsData) {
            				
            				_a.find('.container-copy-to-flow').show();
            				
            				selectCopyToQuestions.empty();
            				var e = selectCopyToQuestions[0].options;
                    		for(var i = 0; i < controlsData.list.length; i++) {
								if(controlsData.list[i].controlTypeId != 11 && controlsData.list[i].controlTypeId != 12) {
                    				var k = new Option(controlsData.list[i].content, controlsData.list[i].controlId);
                     		   		try {
                     			   		e.add(k);
                     		   		} catch (ex) {
                     			   		e.add(k, null)
                     		   		}
								}
                    		}
            				
            			},
            			error: function(controlsError) {
            				
            				_a.find('.container-copy-to-flow').hide();
            				
            				selectCopyToQuestions.empty();
            			}
            		});
        		});
        		
        		// fill questions per page
        		getControlsBySheet({
        			accountId : accountId,
        			sheetId : selectCopyToPages.val(),
        			success : function(controlsData) {
        				
        				_a.find('.container-copy-to-flow').show();
        				
        				var e = selectCopyToQuestions[0].options;
                		for(var i = 0; i < controlsData.list.length; i++) {
							if(controlsData.list[i].controlTypeId != 11 && controlsData.list[i].controlTypeId != 12) {
                				var k = new Option(controlsData.list[i].content, controlsData.list[i].controlId);
                 		   		try {
                 			   		e.add(k);
                 		   		} catch (ex) {
                 			   		e.add(k, null)
                 		   		}
							}
                		}
                		
        			},
        			error: function(controlError) {
        				
        				_a.find('.container-copy-to-flow').hide();
        				
        				selectCopyToQuestions.empty();
        				
        			}
        		});
        		
        		_a.find('.button-copy-to').click(function(e) {
        			
        			var newOrderId = null; 
        			if(_a.find('.select-copy-to-question-position').val() == 0) {
        				// after
        				newOrderId = (_a.find('.select-copy-to-questions')[0].selectedIndex + 1); 
        			} else {
        				// before
        				newOrderId = (_a.find('.select-copy-to-questions')[0].selectedIndex == 0 ? 0 : _a.find('.select-copy-to-questions')[0].selectedIndex);
        			}
        			
        			X.onCopy(vControl, _a.find('.select-copy-to-pages').val(), (newOrderId != null ? newOrderId : undefined));
        			
        			// close combo
        			lastCombo.close();
        			e.preventDefault();
        		});
        		
        		_a.find('.button-copy-to-cancel').click(function(e) {
        			// close combo
        			lastCombo.close();
        			e.preventDefault();
        		});
        		
        		
        		
        		return _a;
        		
        	}
        });
		
		
		// insert question fix to build splitButton after element is appended
        o.find('.button-insert-question')
		.splitButton({
			label : "Insert question",
			icon : "add",
			click : function() {
				X.onInsert(vControl.attr("orderid"), 2, 0); // default multiple choice
			},
			pop : function(lastCombo) {
				
				var M = $("<div style=\"width: 340px;\">" +
								"<div class=\"multi-menu-col first-item\">" +
									"<ul>" +
										"<li><a value=\"3\" input_type_id=\"0\">Single Textbox</a></li>" +
										"<li><a value=\"3\" input_type_id=\"1\">Essay Area</a></li>" +
										"<li><a value=\"2\" input_type_id=\"0\">Multiple Choice</a></li>" +
										"<li><a value=\"2\" input_type_id=\"2\">Checkbox</a></li>" +
										"<li><a value=\"2\" input_type_id=\"1\">Dropdown</a></li>" +
									"</ul>" +
								"</div>" +
								"<div class=\"multi-menu-col\">" +
									"<ul>" +
										"<li><a value=\"4\" input_type_id=\"0\">Matrix Choice</a></li>" +
										"<li><a value=\"4\" input_type_id=\"1\">Matrix Choice</a></li>" +
										"<li><a value=\"10\">Rating Scale</a></li>" +
										"<li><a value=\"6\" input_type_id=\"0\">Date/Time</a></li>" +
									"</ul>" +
								"</div>" +
								"<div class=\"multi-menu-col\">" +
									"<ul>" +
										"<li><a value=\"12\" input_type_id=\"0\">Heading/Descriptive Text</a></li>" +
										"<li><a value=\"11\" input_type_id=\"0\">Hidden Field/URL Param</a></li>" +
										"<li><a>Copy from other survey</a></li>" +
									"</ul>" +
								"</div>" +
							"</div>");
				
							M.find('a').click(function(e) {
								
								if($(this).attr("value") == null && $(this).attr("input_type_id") == null) {
									
									addControlFromAnotherOpinion(function(obj) {
										X.onCopyControls(obj.controls, parseInt(vControl.attr("orderid")) + 1);
									});
									
								} else {
									
									var __controlTypeId = parseInt($(this).attr("value"));
									var __inputTypeId = parseInt($(this).attr("input_type_id"));
									
									
									X.onInsert(vControl.attr("orderid"), __controlTypeId, __inputTypeId);									
									
								}
								
								lastCombo.close();
								e.preventDefault();
								
							});
				
				return M;
				
			}
		});
		
		// mode
        if(X.isEditable) {
        	
        	if (lastEditableControl != null && lastEditableControl != o) {
                lastEditableControl.find('.control-edit').hide();
                lastEditableControl.find('.control-view').show();
                
                lastEditableControl.parent().removeClass('selected');
            }
            lastEditableControl = o;
        	
			
        	o.find('.control-edit').show();
        	o.find('.control-view').hide();
			
			
			// set select on textarea
			o.find('.input-content')
			.focus()
			.select();
	

			
			// scroll and hightlight
			var $demoItems = $('.block-preview').find('ul.list-controls').children('li.demo-question');
			var $demoItemsScrollPositions = new Array();
	
			// cache scroll positions for each item
			$demoItems.each(function(i, el){
				$demoItemsScrollPositions[i] = Math.round($(el).offset().top - $('.block-preview').offset().top) - 10;
			});
			
			$('.block-preview').find('ul.list-controls li.demo-question').each(function(i, el) {
				if(i == vControl.index()) {
					
					var targetElement = $(el);
					
					$('.block-preview')
					.scrollTo($demoItemsScrollPositions[i] + $('.block-preview')
					.scrollTop(), 
					function() {
						// highlight element
						runEffect(targetElement);	
					});
					
				}
			});
			
        }
        
    };
    
    //Y(X.controlTypeId);
    Y();
};