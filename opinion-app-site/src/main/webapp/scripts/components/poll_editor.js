

function getDefaultOptions(opinionId, controlTypeId) {
	var obj;
	switch(controlTypeId) {
		case 2: 
			
			// multiple choice
			// template for default options
			obj = {
				list : [{ 
					optionId : null,
					optionKindId : 0,
					status : "added",
					linkTypeId : 1,
					text : "Choice 1",
					value : "Choice 1",
					isEnableAdditionalDetails : false,
					additionalDetailsTitle : null,
					orderId : 0,
					opinionId : opinionId
				},{
					optionId : null,
					optionKindId : 0,
					status : "added",
					text : "Choice 2",
					value : "Choice 2",
					isEnableAdditionalDetails : false,
					additionalDetailsTitle : null,
					orderId : 1,
					opinionId : opinionId
				}]
			};
			
			break;
		case 4:
			
			// matrix columns
			obj = {
				list : [{ 
					optionId : null,
					optionKindId : 0,
					status : "added",
					linkTypeId : 1,
					text : "Column 1",
					value : "Column 1",
					isEnableAdditionalDetails : false,
					additionalDetailsTitle : null,
					orderId : 0,
					opinionId : opinionId
				}]
			};
			
			break;
	}
	return obj
};

(function($) {
	$.fn.randomize = function(childElem) {
	  return this.each(function() {
	      var $this = $(this);
	      var elems = $this.children(childElem);
	
	      elems.sort(function() { return (Math.round(Math.random())-0.5); });  
	
	      $this.remove(childElem);  
	
	      for(var i=0; i < elems.length; i++)
	        $this.append(elems[i]);      
	
	  });    
	}
})(jQuery);

(function(jQuery) {
	pollEditor = function(givenOptions) {

		var options = $.extend( {
			opinionId : null
		}, givenOptions);
		
		
		
		
		
		var iframe = null;
		var s = null; // iframe.contents()
		
		
		
		var result = null;
		var link = null;
		
		var viewControl = function(data) {
			
			
			// pseudo switch
			if(data.controlTypeId == 2) {
				$("#select_question_types option[value=2][input_type_id=" + data.inputTypeId + "]").attr("selected", "selected");
			} else { 
				$("#select_question_types option[value=" + data.controlTypeId + "]").attr("selected", "selected"); 
			}
			
			
			
			
			
			
			//console.log("HERE");
			
			// one time tick controls
			// content
			
			
			// clear control
			$("#placeholder_control").empty();
			//$("#iframe_preview").empty();
			
			
			switch(data.controlTypeId) {
				case 2 : {
					
					
					// choice
					var a = s.find('.container-control-includes');
                        a.empty();
                        
                    var containerControlOther = s.find('.container-control-other');
                    	containerControlOther.empty();
					
                   
                   // new separation 	
                   switch(data.inputTypeId) {
	                   case 0: {
	                	   
	                	   // radio
	                	   
	                	   var r = $("<div>" +
	       						"<div style=\"padding-left: 130px;clear: both;\">" +
	       							"<ul class=\"options-list\"></ul>" +
	       							"<div style=\"padding: 0 0 6px 20px; clear: both;\">" +
	       								"<a class=\"button-add-option\" title=\"Add Choice\">Add Choice</a>" +
	       							"</div>" +
	       						"</div>" +
	       						"<div class=\"params\" id=\"params_other\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value ui-form\">" +
	       								"<div class=\"row-choice\" style=\"padding-left: 20px;\">" +
	       									"<label><span><input type=\"checkbox\" id=\"checkbox_other_option\" class=\"checkbox-is-enable-other\" autocomplete=\"off\" /></span>Add \"Other\" option</label>" +
	       									"<div style=\"padding: 10px 0 0 20px; display: none;\" class=\"control-container-other\">" +
	       										"<input type=\"text\" class=\"input-other-title\" autocomplete=\"off\" maxlength=\"254\" placeholder=\"Other, (please specify)\" />" +
	       									"</div>" +
	       								"</div>" +
	       							"</div>" +
	       						"</div>" +
	       						/*
	       						"<div class=\"params\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value\">" +
	       								"<div class=\"ui-label\">" +
	       									"<input type=\"checkbox\" id=\"checkbox_random_order\" class=\"ui-label-checkbox\" autocomplete=\"off\" /><label for=\"checkbox_random_order\">Display answer choices at random order</label>" +
	       								"</div>" +
	       							"</div>" +
	       						"</div>" +
	       						*/
	       						"<div style=\"height: 24px; overflow: hidden;clear: both;\"></div>" +
	       						"<div class=\"params\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value\">" +
	       								"<a class=\"button-blue button-save\" title=\"Save\"><span>Save</span></a>" +
	       							"</div>" +
	       							/*
	       							"<div class=\"param-value\">" +
	       								"<a class=\"button-cancel\" title=\"Cancel\" style=\"margin-left: 6px;\">Cancel</a>" +
	       							"</div>" +
	       							*/
	       						"</div>" +
	       					"</div>").appendTo("#placeholder_control");
	                	   
	                	   var d = r.find('.control-container-other');
	                	   
	                	   var clearIndexes = function() {
	                		   r.find("ul.options-list li").each(function(i, el) {
		                           	$(el).attr("index", i);
	   								$("ul.container-control-includes li:eq(" + i + ")").attr("index", i).removeAttr("new_index");
	                           });
	                	   };
	                	   
	                	   function sortView(){
	                		   
	                		   var myList = $('ul.container-control-includes');
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
		   	                        
	                			   //console.log("Start position: " + ui.item.startPos);
	                			   //console.log("New position: " + ui.item.index());
	                			   //var start_pos = ui.item.startPos;
	                			   //var end_pos = ui.item.index();
	                			   
	                			   r.find("ul.options-list li").each(function(i, el) {
	                				   $("ul.container-control-includes").find("li[index=" + $(el).attr("index") + "]").attr("new_index", i);
	                			   });
	                			   
	                			   sortView();
	                			   
	                		   }
	                	   });
	                	   
	                	   
	                	   var otherPreview = null;
	                	   var otherPreviewText = null;
	                	   
	                	   
	                	   // other
	                	   otherPreview = $("<div class=\"choice-other\" style=\"display: none\">" +
	                	   		"<label><span><input type=\"radio\" name=\"option\" class=\"radio-other\" /></span><strong>Other, (please specify)</strong></label>" +
	                	   		"<div class=\"preview-container-other container-other\">" +
	                	   			"<input type=\"text\" class=\"text-other\" />" +
	                	   		"</div>" +
	                	   	"</div>").appendTo(containerControlOther);
	                	   
	                	   
	                	   var radioOther = otherPreview.find(".radio-other");
                           var textOther = otherPreview.find(".text-other");
                           radioOther.change(function () {
                               if ($(this).is(":checked")) {
                            	   textOther.focus();
                               }
                           });
                           textOther.focusin(function () {
                               if (!radioOther.is(":checked")) {
                            	   radioOther.prop("checked", true);
                               }
                           });
	                	   
	                	   otherPreviewText = otherPreview.find("strong");
	                	   
	                	   
	                	   var A = [];
	                	   var D = function (b, c, d, e, f, g) {
   								
								var h = $("<li class=\"option\" index=\"" + r.find("ul.options-list li").length + "\">" + 
									"<div class=\"params\">" +
										"<div class=\"tab-move-option\"></div>" +
										"<div class=\"param-value\">" +
											"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-option-text\" placeholder=\"Choice\" />" +
										"</div>" +
										"<div class=\"param-value\" style=\"margin-left: 6px;\">" +
											"<span title=\"Remove\" class=\"button-remove-option\"></span>" +
										"</div>" +
									"</div>" + 
								"</li>");
   								
   								// preview
   								var o = null;
   								
   								
   								// radio
   		                    	o = $("<li index=\"" + $('ul.container-control-includes li').length + "\" class=\"choice\"><label><div style=\"overflow: hidden\" class=\"choice-image\"></div><div class=\"choice-text\"><span><input type=\"radio\" name=\"option\" /></span><strong>" + (d != "" ? d : "Choice") + "</strong></div></label></li>");
   		                    	o.appendTo(a);
   		                    	
   		                    	
   			                    if (c != null) {
   			                        h.attr({
   			                            "optionid" : c
   			                        });
   			                    }
   			                    h.appendTo(w);
   								
   								h.find(".input-option-text")
   									.bind("keyup", function() {
   										o.find("strong").text($(this).val());
   									})
   									.bind("keydown", function(e) {
   										var code = e.keyCode || e.which;
   										if (code == '9') {
   											// if -> h last in list create new option
   											if($('ul.options-list li').length == ($(this).closest("li").index() + 1)) {
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
   			                	l.val(((b.text != "" && b.text != null) ? b.text : "Other, (please specify)")).attr('optionid', b.optionId);
   			                	
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
   							.val("Other, (please specify)")
   							.bind("keyup", function() {
   								otherPreviewText.text($(this).val());
   							});
   							
   			                
   			                
   							// options
   			                if(data.control.options != undefined) {
   				                if(data.control.options.list.length != 0) {
   					                for (var i = 0; i < data.control.options.list.length; i++) {
   					                	
   				                    	if(data.control.options.list[i].optionKindId == 0) {
   				                    		D(false, data.control.options.list[i].optionId, data.control.options.list[i].text, data.control.options.list[i].value, false, null)
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
   			                
   			                var C = function() {
	   		        			
	   		        			var j = 0;
	   		                    var z = [];
	   		                    
	   		                    r.find("ul.options-list li").each(function (i, a) {
	   		                        if ($(a).attr("optionid") != undefined) {
	   		                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
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
	   		                        } else {
	   		                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
	   		                            var e = {
	   		                                controlId: result.controlId, // control id
	   		                                optionId: null,
	   		                                optionKindId : 0,
	   		                                status: "added",
	   		                                text: b,
	   		                                value: b,
	   		                                isEnableAdditionalDetails: false,
	   		                                additionalDetailsTitle: null,
	   		                                orderId: i,
	   		                                opinionId: options.opinionId
	   		                            };
	   		                            z.push(e);
	   		                        }
	   		                        j++;
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
	   		                                controlId: result.controlId,
	   		                                optionId: null,
	   		                                optionKindId: 1,
	   		                                status: "added",
	   		                                text: b,
	   		                                value: b,
	   		                                isEnableAdditionalDetails: false,
	   		                                additionalDetailsTitle: null,
	   		                                orderId: j,
	   		                                opinionId: options.opinionId
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
										element : $('#textarea_question'),
										status : $('#status_question'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									result.content = $(".input-content").val();
		   		    				result.opinionTitle = $(".input-content").val();
		   		    				result.isMandatory = $("#checkbox_mandatory").prop("checked");
									
		   		    				//result.isEnableNote = $("#checkbox_include_note").prop("checked");
		   		    				result.note = ($("#checkbox_include_note").prop("checked") && $(".textarea-note").val() != "" ? $(".textarea-note").val() : undefined);
									
		   		    				
		   		    				// link
		   		    				if($("#checkbox_link").prop("checked")) {
		   		                    	if($(".select-link-type").val() == 1) {
		   		                    		// link to image
		   		                    		if($(".input-image").val() != "") {
		   		                    			result.linkTypeId = 1;
		   		                    			result.link = $(".input-image").val();
		   		                    		} else {
		   		                    			result.linkTypeId = 0;
		   		                    			result.link = null;
		   		                    		}
		   		                    	}
		   		                    	
		   		                    	if($(".select-link-type").val() == 2) {
		   		                    		// embed code
		   		                    		if($('.textarea-embed-code').val() != "") {
		   		                    			result.linkTypeId = 2;
		   		                    			result.link = $('.textarea-embed-code').val();
		   		                    		} else {
		   		                    			result.linkTypeId = 0;
		   		                    			result.link = null;
		   		                    		}
		   		                    	}
		   		                    } else {
		   		                    	result.linkTypeId = 0;
		   		                    	result.link = null;
		   		                    }
		   		    				
		   		    				result.controlTypeId = 2;
		   		    				result.inputTypeId = 0; // radio
		   		    				
		   		    				// options
		   		    				result.options = {
		   		                        list: C(),
		   		                        reorder: B()
		   		                    };
		   		    				
		   		    				// update
		   		    				updateControlDetails({
		   		    					control : result,
		   		    					success : function(newData) {
		   		    						
		   		    						// merge data -> result
		   		    						$.extend(result, newData.result);
		
		   		    						setTimeout(function() {
		   		    							G();
		   		    						}, 150);
		   		    						
		   		    						
		   		    						
		   		    						
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
		   		    					error: function(error) {
		   		    						alert("ERR -> update options");
		   		    					}
		   		    				});
									
								}
	   		        		});
	
	                	   
	                	   break;
	                   }
	                   case 1: {
	                	   
	                	   // dropdown
	                	   
	                	   var r = $("<div>" +
	       						"<div style=\"padding-left: 130px;clear: both;\">" +
       								"<div class=\"container-default-non-selected-option-text\" style=\"padding-left:20px; padding-bottom: 6px; min-height: 22px;\">" +
       									"<input type=\"text\" autocomlete=\"off\" class=\"input-default-non-selected-option-text\" placeholder=\"Dropdown label...\" />" +
       								"</div>" +
	       							"<ul class=\"options-list\"></ul>" +
	       							"<div style=\"padding: 0 0 12px 20px; clear: both;\">" +
	       								"<a class=\"button-add-option\" title=\"Add Choice\">Add Choice</a>" +
	       							"</div>" +
	       						"</div>" +
	       						/*
	       						"<div class=\"params\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value\">" +
	       								"<div class=\"ui-label\">" +
	       									"<input type=\"checkbox\" id=\"checkbox_random_order\" class=\"ui-label-checkbox\" autocomplete=\"off\" /><label for=\"checkbox_random_order\">Display answer choices at random order</label>" +
	       								"</div>" +
	       							"</div>" +
	       						"</div>" +
	       						*/
	       						"<div style=\"height: 24px; overflow: hidden;clear: both;\"></div>" +
	       						"<div class=\"params\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value\">" +
	       								"<a class=\"button-blue button-save\" title=\"Save\"><span>Save</span></a>" +
	       							"</div>" +
	       							/*
	       							"<div class=\"param-value\">" +
	       								"<a class=\"button-cancel\" title=\"Cancel\" style=\"margin-left: 6px;\">Cancel</a>" +
	       							"</div>" +
	       							*/
	       						"</div>" +
	       					"</div>").appendTo("#placeholder_control");
	                	   
	                	   var S = r.find('.input-default-non-selected-option-text');
	                	   
	                	   
	                	   var updatePreview = function() {
	                		 
	                		   a.empty();
	                		   
	                		   // select for dropdown
	                		   var select = null;
		                	   select = $("<select/>").appendTo(a);
		                	   

	                		   
	                		   var e = select[0].options;
	                		   
	                		   var k = new Option(S.val());
	                		   //k.setAttribute("index", e.length);
	                		   
	                		   try {
	                			   e.add(k);
	                		   } catch (ex) {
	                			   e.add(k, null)
	                		   }
	                		   
	                		   
	                		   
	                		   
	                		   // update items
	                		   r.find('ul.options-list li').each(function(i, el) {
		                           
	                			   //$(el).attr("index", i);
	                			   
	                			   var optionValue = new Option($(el).find("input").val());
		                		   //o.setAttribute("index", e.length);
	                			   
	                			   try {
		                			   e.add(optionValue);
		                		   } catch (ex) {
		                			   e.add(optionValue, null)
		                		   }
		                		   
	                		   });
		                       
		                	   
		                	   
		                	   /*
		                	   if (window.console) {
		           	    			window.console.log("-->> update view");
		                	   }
		                	   */
		                	   
		                	   // r.find('ul.options-list li').each(function(i, el) {
	                		   
	                	   };
	                	   
	                	   var clearIndexes = function() {
		   						r.find('ul.options-list li').each(function(i, el) {
		                           	$(el).attr("index", i);
		                           	
		                           	
		                           	/*
		                           	$("ul.container-control-includes select option:eq(" + i + ")").attr("index", i).removeAttr("new_index");
		                           	*/
		                           	
		   						});
		   						
		   						
		   						updatePreview();
		   						
	                	   };
	                	   
	                	   
	                	   /*
	                	   function sortView(){
	                		   
	                		   var myList = $('ul.container-control-includes select');
	                		   var listItems = myList.children('option').get();
	                		   listItems.sort(function(a,b){
	                			   
	                			   var compA = $(a).attr("new_index");
	                			   var compB = $(b).attr("new_index");
	                			   
	                			   return ((compA ? compA : 0) - (compB ? compB : 0));
	                			   //return (compA < compB) ? -1 : (compA > compB) ? 1 : 0;
	                			   
	                		   });
	                		   $(myList).append(listItems);
	                	   };
	                	   */
	                	   
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
		   	                        
	                			   //console.log("Start position: " + ui.item.startPos);
	                			   //console.log("New position: " + ui.item.index());
	                			   //var start_pos = ui.item.startPos;
	                			   //var end_pos = ui.item.index();
	                			   
	                			   /*
	                			   r.find('ul.options-list li').each(function(i, el) {
	                				   $("ul.container-control-includes select").find("option[index=" + $(el).attr("index") + "]").attr("new_index", i);
	                			   });
	                			   
	                			   sortView();
	                			   */
	                			   
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
		   	                    		"<div class=\"param-value\" style=\"margin-left: 6px;\">" +
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
		   									if($('ul.options-list li').length == ($(this).closest("li").index() + 1)) {
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
	                	   
	                	   // options
	                	   if(data.control.options != undefined) {
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
	                	   
	                	   
	                	   var C = function() {
	   	        			
	                		   var j = 0;
	                		   var z = [];
	   	                    
	                		   r.find("ul.options-list li").each(function (i, a) {
	                			   if ($(a).attr("optionid") != undefined) {
	                				   var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
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
	                			   } else {
	                				   var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
	                				   var e = {
	                						   controlId: result.controlId, // control id
	                						   optionId: null,
	                						   optionKindId : 0,
	                						   status: "added",
	                						   text: b,
	                						   value: b,
	                						   isEnableAdditionalDetails: false,
	                						   additionalDetailsTitle: null,
	                						   orderId: i,
	                						   opinionId: options.opinionId
	                				   };
	                				   z.push(e);
	                			   }
	                			   j++;
	                		   });
	   	                    
	                		   
	                		   // dropdown label - default selected
	                		   //var S = r.find('.input-default-non-selected-option-text')
	                		   /*if($.trim(S.val()) != "") {*/
	                		   
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
	                						   controlId: result.controlId,
	                						   optionId: null,
	                						   optionKindId: 2,
	                						   status: "added",
	                						   text: b,
	                						   value: b,
	                						   isEnableAdditionalDetails: false,
	                						   additionalDetailsTitle: null,
	                						   orderId : 0,
	                						   opinionId: options.opinionId
	                				   };
	                				   z.push(e);
	                			   }
	                			   
	                		   /*} else {*/
	                			   
	                			   /*
	                			   if(R != null) {
	                				   if(S.attr('optionid') != undefined) {
	                					   var a = {
	                							   optionId: parseInt(S.attr('optionid')),
	                							   status: "deleted"
	                					   };
	                					   A.push(a);
	                				   }
	                			   }
	                			   */
	                			   
	                		   /*}*/
	   	                    
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
										element : $('#textarea_question'),
										status : $('#status_question'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									result.content = $(".input-content").val();
			                		   result.opinionTitle = $(".input-content").val();
			                		   result.isMandatory = $("#checkbox_mandatory").prop("checked");
			                		   
									   //result.isEnableNote = $("#checkbox_include_note").prop("checked");
			                		   result.note = ($("#checkbox_include_note").prop("checked") && $(".textarea-note").val() != "" ? $(".textarea-note").val() : undefined);
			   	    				
			                		   // link
			                		   if($("#checkbox_link").prop("checked")) {
			                			   if($(".select-link-type").val() == 1) {
			                				   // link to image
			                				   if($(".input-image").val() != "") {
			                					   result.linkTypeId = 1;
			                					   result.link = $(".input-image").val();
			                				   } else {
			                					   result.linkTypeId = 0;
			                					   result.link = null;
			                				   }
			                			   }
			                			   
			                			   if($(".select-link-type").val() == 2) {
			                				   // embed code
			                				   if($('.textarea-embed-code').val() != "") {
			                					   result.linkTypeId = 2;
			                					   result.link = $('.textarea-embed-code').val();
			                				   } else {
			                					   result.linkTypeId = 0;
			                					   result.link = null;
			                				   }
			                			   }
			                		   } else {
			                			   result.linkTypeId = 0;
			                			   result.link = null;
			                		   }
			                		   
			                		   
			                		   result.controlTypeId = 2;
			                		   result.inputTypeId = 1; // dropdown
			                		   
			                		   // options
			                		   result.options = {
			                				   list: C(),
			                				   reorder: B()
			                		   };
			   	    				
			                		   // update
			                		   updateControlDetails({
			                			   control : result,
			                			   success : function(newData) {
			                				   // merge data -> result
			                				   $.extend(result, newData.result);

			                				   setTimeout(function() {
			                					   G();
			                				   }, 150);
			   	    							
			                				   
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
			                			   error: function(error) {
			                				   alert("ERR -> update options");
			                			   }
			                		   });
									
								}
	   		        		});
	                	   
	                	   
	                	   break;
	                   }
	                   case 2: {
	                	   
	                	   // checkbox
	                	   
	                	   var r = $("<div>" +
	       						"<div style=\"padding-left: 130px;clear: both;\">" +
	       							"<ul class=\"options-list\"></ul>" +
	       							"<div style=\"padding: 0 0 6px 20px; clear: both;\">" +
	       								"<a class=\"button-add-option\" title=\"Add Choice\">Add Choice</a>" +
	       							"</div>" +
	       						"</div>" +
	       						"<div class=\"params\" id=\"params_other\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value ui-form\">" +
	       								"<div class=\"row-choice\" style=\"padding-left: 20px;\">" +
	       									"<label><span><input type=\"checkbox\" id=\"checkbox_other_option\" class=\"checkbox-is-enable-other\" autocomplete=\"off\" /></span>Add \"Other\" option</label>" +
	       									"<div style=\"padding: 10px 0 0 20px; display: none;\" class=\"control-container-other\">" +
	       										"<input type=\"text\" class=\"input-other-title\" autocomplete=\"off\" maxlength=\"254\" placeholder=\"Other, (please specify)\" />" +
	       									"</div>" +
	       								"</div>" +
	       							"</div>" +
	       						"</div>" +
	       						/*
	       						"<div class=\"params\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value\">" +
	       								"<div class=\"ui-label\">" +
	       									"<input type=\"checkbox\" id=\"checkbox_random_order\" class=\"ui-label-checkbox\" autocomplete=\"off\" /><label for=\"checkbox_random_order\">Display answer choices at random order</label>" +
	       								"</div>" +
	       							"</div>" +
	       						"</div>" +
	       						*/
	       						"<div style=\"height: 24px; overflow: hidden;clear: both;\"></div>" +
	       						"<div class=\"params\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value\">" +
	       								"<a class=\"button-blue button-save\" title=\"Save\"><span>Save</span></a>" +
	       							"</div>" +
	       							/*
	       							"<div class=\"param-value\">" +
	       								"<a class=\"button-cancel\" title=\"Cancel\" style=\"margin-left: 6px;\">Cancel</a>" +
	       							"</div>" +
	       							*/
	       						"</div>" +
	       					"</div>").appendTo("#placeholder_control");
	                	   
	                	   var d = r.find('.control-container-other');
	                	   
	                	   var clearIndexes = function() {
	                		   r.find('ul.options-list li').each(function(i, el) {
	                			   $(el).attr("index", i);
	                			   $("ul.container-control-includes li:eq(" + i + ")").attr("index", i).removeAttr("new_index");
	                           });
	                	   };
	                	   
	                	   function sortView(){
	                		   
	                		   var myList = $('ul.container-control-includes');
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
	                				   $("ul.container-control-includes").find("li[index=" + $(el).attr("index") + "]").attr("new_index", i);
	                			   });
	   	                        
	                			   sortView();
	                			   
	                		   }
	                	   });
	                	   
	                	   var otherPreview = null;
	                	   var otherPreviewText = null;
	                	   
	                	   // other
	                	   otherPreview = $("<div class=\"choice-other\" style=\"display: none\">" +
	                	   		"<label><span><input type=\"checkbox\" class=\"checkbox-other\" /></span><strong>Other, (please specify)</strong></label>" +
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
	                	   var D = function (b, c, d, e, f, g) {
	                		   
	                		   var h = $("<li class=\"option\" index=\"" + r.find("ul.options-list li").length + "\">" + 
		   	                    	"<div class=\"params\">" +
		   	                    		"<div class=\"tab-move-option\"></div>" +
		   	                    		"<div class=\"param-value\">" +
		   	                    			"<input type=\"text\" value=\"" + d + "\" autocomlete=\"off\" class=\"input-option-text\" placeholder=\"Choice\" />" +
		   	                    		"</div>" +
		   	                    		"<div class=\"param-value\" style=\"margin-left: 6px;\">" +
		   	                    			"<span title=\"Remove\" class=\"button-remove-option\"></span>" +
		   	                    		"</div>" +
		   	                    	"</div>" + 
		   	                    "</li>");
	   						
		   						// preview
		   						var o = null;
		   						
		                       	// checkbox
		   						o = $("<li index=\"" + $('ul.container-control-includes li').length + "\" class=\"choice\"><label><div style=\"overflow: hidden\" class=\"choice-image\"></div><div class=\"choice-text\"><span><input type=\"checkbox\"/></span><strong>" + (d != "" ? d : "Choice") + "</strong></div></label></li>");
		   						o.appendTo(a);
		                       	
		   						
		   	                    if (c != null) {
		   	                        h.attr({
		   	                            "optionid" : c
		   	                        });
		   	                    }
		   	                    h.appendTo(w);
		   						
		   	                    
		   						h.find(".input-option-text")
		   							.bind("keyup", function() {
		   								o.find("strong").text($(this).val());
		   							})
		   							.bind("keydown", function(e) {
		   								var code = e.keyCode || e.which;
		   								if (code == '9') {
		   									// if -> h last in list create new option
		   									if($('ul.options-list li').length == ($(this).closest("li").index() + 1)) {
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
			                	l.val(((b.text != "" && b.text != null) ? b.text : "Other, (please specify)")).attr('optionid', b.optionId);
									
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
			                
			                // other
							r.find('.checkbox-is-enable-other')
							.change(function () {
								if ($(this).is(':checked')) {
									
									d.show();
									otherPreview.show();
									
									/*
									if(Q == null) {
										var l = r.find('.input-other-title');
											l.val("Other, (please specify)").removeAttr('optionid');
										
									}
									*/
									
								} else {
									d.hide();
									otherPreview.hide();
								}
							});
							
							// other input
							r.find(".input-other-title")
							.val("Other, (please specify)")
							.bind("keyup", function() {
								otherPreviewText.text($(this).val());
							});
							
	                	   
							
							// options
			                if(data.control.options != undefined) {
				                if(data.control.options.list.length != 0) {
					                for (var i = 0; i < data.control.options.list.length; i++) {
					                	
				                    	if(data.control.options.list[i].optionKindId == 0) {
				                    		D(false, data.control.options.list[i].optionId, data.control.options.list[i].text, data.control.options.list[i].value, false, null)
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
			        		
			        		var C = function() {
			        			
			        			var j = 0;
			                    var z = [];
			                    
			                    r.find("ul.options-list li").each(function (i, a) {
			                        if ($(a).attr("optionid") != undefined) {
			                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
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
			                        } else {
			                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
			                            var e = {
			                                controlId: result.controlId, // control id
			                                optionId: null,
			                                optionKindId : 0,
			                                status: "added",
			                                text: b,
			                                value: b,
			                                isEnableAdditionalDetails: false,
			                                additionalDetailsTitle: null,
			                                orderId: i,
			                                opinionId: options.opinionId
			                            };
			                            z.push(e);
			                        }
			                        j++;
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
			                                controlId: result.controlId,
			                                optionId: null,
			                                optionKindId: 1,
			                                status: "added",
			                                text: b,
			                                value: b,
			                                isEnableAdditionalDetails: false,
			                                additionalDetailsTitle: null,
			                                orderId: j,
			                                opinionId: options.opinionId
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
										element : $('#textarea_question'),
										status : $('#status_question'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									result.content = $(".input-content").val();
				    				result.opinionTitle = $(".input-content").val();
				    				result.isMandatory = $("#checkbox_mandatory").prop("checked");
				    				
									//result.isEnableNote = $("#checkbox_include_note").prop("checked");
				    				result.note = ($("#checkbox_include_note").prop("checked") && $(".textarea-note").val() != "" ? $(".textarea-note").val() : undefined);
				    				
				    				// link
				    				if($("#checkbox_link").prop("checked")) {
				                    	if($(".select-link-type").val() == 1) {
				                    		// link to image
				                    		if($(".input-image").val() != "") {
				                    			result.linkTypeId = 1;
				                    			result.link = $(".input-image").val();
				                    		} else {
				                    			result.linkTypeId = 0;
				                    			result.link = null;
				                    		}
				                    	}
				                    	
				                    	if($(".select-link-type").val() == 2) {
				                    		// embed code
				                    		if($('.textarea-embed-code').val() != "") {
				                    			result.linkTypeId = 2;
				                    			result.link = $('.textarea-embed-code').val();
				                    		} else {
				                    			result.linkTypeId = 0;
				                    			result.link = null;
				                    		}
				                    	}
				                    } else {
				                    	result.linkTypeId = 0;
				                    	result.link = null;
				                    }
				    				
				    				
				    				result.controlTypeId = 2;
				    				result.inputTypeId = 2; // checkbox
				    				
				    				// options
				    				result.options = {
				                        list: C(),
				                        reorder: B()
				                    };
				    				
				    				// update
				    				updateControlDetails({
				    					control : result,
				    					success : function(newData) {
				    						
				    						// merge data -> result
				    						$.extend(result, newData.result);

				    						setTimeout(function() {
				    							G();
				    						}, 150);
				    						
				    						
				    						
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
				    					error: function(error) {
				    						alert("ERR -> update options");
				    					}
				    				});
									
								}
	   		        		});

	                	   
	                	   break;
	                   }
	                   case 4: {
	                	   
	                	   // ranking
	                	   
	                	   var r = $("<div>" +
	       						"<div style=\"padding-left: 130px;clear: both;\">" +
	       							"<ul class=\"options-list\"></ul>" +
	       							"<div style=\"padding: 0 0 12px 20px; clear: both;\">" +
	       								"<a class=\"button-add-option\" title=\"Add Choice\">Add Choice</a>" +
	       							"</div>" +
	       						"</div>" +
	       						/*
	       						"<div class=\"params\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value\">" +
	       								"<div class=\"ui-label\">" +
	       									"<input type=\"checkbox\" id=\"checkbox_random_order\" class=\"ui-label-checkbox\" autocomplete=\"off\" /><label for=\"checkbox_random_order\">Display answer choices at random order</label>" +
	       								"</div>" +
	       							"</div>" +
	       						"</div>" +
	       						*/
	       						"<div style=\"height: 24px; overflow: hidden;clear: both;\"></div>" +
	       						"<div class=\"params\">" +
	       							"<div class=\"param-name\"></div>" +
	       							"<div class=\"param-value\">" +
	       								"<a class=\"button-blue button-save\" title=\"Save\"><span>Save</span></a>" +
	       							"</div>" +
	       							/*
	       							"<div class=\"param-value\">" +
	       								"<a class=\"button-cancel\" title=\"Cancel\" style=\"margin-left: 6px;\">Cancel</a>" +
	       							"</div>" +
	       							*/
	       						"</div>" +
	       					"</div>").appendTo("#placeholder_control");
	       					
	       					
	       					var updateCount = function() {
	                           	var list = a.find("select"); 
	                           	list.each(function(i, el) {
	                           		
	                           		$(el).empty();
	                           		for(var y = 0; y < list.length; y++) {
	                           			var rankSelect = $(el)[0].options;
	                           			var opt = new Option((y + 1), (y + 1));
	                           	        try {
	                           	        	rankSelect.add(opt)
	                           	        } catch (ex) {
	                           	        	rankSelect.add(opt, null)
	                           	        }
	                           		}
	                           		
	                           	});
	                        };
	       					
	       					var clearIndexes = function() {
	       						r.find('ul.options-list li').each(function(i, el) {
	                               	$(el).attr("index", i);
	                               	$("ul.container-control-includes li:eq(" + i + ")").attr("index", i).removeAttr("new_index");
	                            });
	       					};
	       					
	       					function sortView(){
	       						
	                       	    var myList = $('ul.container-control-includes');
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
	       	                        	$("ul.container-control-includes").find("li[index=" + $(el).attr("index") + "]").attr("new_index", i);
	       	                        });
	       							
	       							// sort
	       	                        sortView();
	       	                        
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
	       	                    		"<div class=\"param-value\" style=\"margin-left: 6px;\">" +
	       	                    			"<span title=\"Remove\" class=\"button-remove-option\"></span>" +
	       	                    		"</div>" +
	       	                    	"</div>" + 
	       	                    "</li>");
	       						
	       						
	       						// preview
	       						var o = $("<li index=\"" + $('ul.container-control-includes li').length + "\"><select></select><label>" + (d != "" ? d : "Choice") + "</label></li>");
	       	                    
	       						
	       	                    if (c != null) {
	       	                        h.attr({
	       	                            'optionid': c
	       	                        })
	       	                    }
	       	                    h.appendTo(w);
	       	                    
	       	                    // append preview
	       	                    o.appendTo(a);
	       						
	       	                    
	       	                    // update count
	       	                    //
	       	                    //
	       	                    
	       	                    updateCount();
	       	                    
	       	                    
	       						h.find(".input-option-text")
	       	                    	.bind("keyup", function() {
	       								o.find("label").text($(this).val());
	       							})
	       							.bind("keydown", function(e) {
	       								var code = e.keyCode || e.which;
	       								   if (code == '9') {
	       									   if($('ul.options-list li').length == ($(this).closest("li").index() + 1)) {
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
	       	                    
	       	                    h.find('.button-remove-option').click(function () {
	       	                        if (h.attr('optionid') != undefined) {
	       	                            
	       	                        	var a = {
	       	                                optionId: parseInt(h.attr('optionid')),
	       	                                status: "deleted"
	       	                            };
	       	                            A.push(a);
	       	                            
	       	                            
	       	                            h.remove();
	       	                            
	       	                            o.remove();
	       	                            
	       	                            
	       			                    // update count
	       			                    //
	       			                    //
	       	                            
	       	                            updateCount();
	       	                            
	       	                        } else {
	       	                            
	       	                        	h.remove();
	       	                            
	       	                            o.remove();
	       	                            
	       	                            

	       	    	                    // update count
	       	    	                    //
	       	    	                    //
	       	                            
	       	                            updateCount();
	       	                        }
	       							
	       							
	       							// clear indexes
	                                clearIndexes();
	       							
	       	                    });
	       	                };
	       					
	               			// add option
	               			r.find('.button-add-option')
	       	                .unbind('click')
	       	                .click(function (e) {
	                               D(true, null, "", "", false, null);
	       						// clear indexes
	       						clearIndexes();
	                           });
	       	                
	               			// options
	       	                if(data.control.options != undefined) {
	       		                if(data.control.options.list.length != 0) {
	       			                for (var i = 0; i < data.control.options.list.length; i++) {
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
	       	        		
	       	        		var C = function() {
	       	        			
	       	        			var j = 0;
	       	                    var z = [];
	       	                    
	       	                    r.find("ul.options-list li").each(function (i, a) {
	       	                        if ($(a).attr("optionid") != undefined) {
	       	                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
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
	       	                        } else {
	       	                            var b = $.removeHTMLTags($(a).find(".input-option-text").val()).replace(/\r/g, "");
	       	                            var e = {
	       	                                controlId: result.controlId, // control id
	       	                                optionId: null,
	       	                                optionKindId : 0,
	       	                                status: "added",
	       	                                text: b,
	       	                                value: b,
	       	                                isEnableAdditionalDetails: false,
	       	                                additionalDetailsTitle: null,
	       	                                orderId: i,
	       	                                opinionId: options.opinionId
	       	                            };
	       	                            z.push(e);
	       	                        }
	       	                        j++;
	       	                    });
	       	                    
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
										element : $('#textarea_question'),
										status : $('#status_question'),
										rules : [
											{ method : 'required', message : 'This field is required.' }
										]
									}
								],
								submitElement : r.find(".button-save"),
								accept : function () {
									
									result.content = $(".input-content").val();
		       	    				result.opinionTitle = $(".input-content").val();
		       	    				result.isMandatory = $("#checkbox_mandatory").prop("checked");
		       	    				
									//result.isEnableNote = $("#checkbox_include_note").prop("checked");
		       	    				result.note = ($("#checkbox_include_note").prop("checked") && $(".textarea-note").val() != "" ? $(".textarea-note").val() : undefined);
		       	    				
		       	    				// link
		       	    				if($("#checkbox_link").prop("checked")) {
		       	                    	if($(".select-link-type").val() == 1) {
		       	                    		// link to image
		       	                    		if($(".input-image").val() != "") {
		       	                    			result.linkTypeId = 1;
		       	                    			result.link = $(".input-image").val();
		       	                    		} else {
		       	                    			result.linkTypeId = 0;
		       	                    			result.link = null;
		       	                    		}
		       	                    	}
		       	                    	
		       	                    	if($(".select-link-type").val() == 2) {
		       	                    		// embed code
		       	                    		if($('.textarea-embed-code').val() != "") {
		       	                    			result.linkTypeId = 2;
		       	                    			result.link = $('.textarea-embed-code').val();
		       	                    		} else {
		       	                    			result.linkTypeId = 0;
		       	                    			result.link = null;
		       	                    		}
		       	                    	}
		       	                    } else {
		       	                    	result.linkTypeId = 0;
		       	                    	result.link = null;
		       	                    }
		       	    				
		       	    				
		       	    				
		       	    				// result type
		       	    				// result.inputTypeId = ($('#select_question_types option:selected').attr("input_type_id") != undefined ? parseInt($('#select_question_types option:selected').attr("input_type_id")) : 0); // from dropdown
		       	    				
		       	    				result.controlTypeId = 2; // 11
		       	    				result.inputTypeId = 4; // 4
		       	    				
		       	    				// options
		       	    				result.options = {
		       	                        list: C(),
		       	                        reorder: B()
		       	                    };
		       	    				
		       	    				// update
		       	    				updateControlDetails({
		       	    					control : result,
		       	    					success : function(newData) {
		       	    						
		       	    						// merge data -> result
		       	    						$.extend(result, newData.result);

		       	    						setTimeout(function() {
		       	    							G();
		       	    						}, 150);
		       	    						
		       	    						
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
		       	    					error: function(error) {
		       	    						alert("ERR -> update options");
		       	    					}
		       	    				});
									
								}
	   		        		});
	       	        		
	                	   
	                	   break;
	                   }
                   }
					
					
                   break;
				}
				case 10 : {
					
					// rating/scale
					var a = s.find('.container-control-includes');
	                    a.empty();
	                    
					var containerControlOther = s.find('.container-control-other');
					containerControlOther.empty();
					
					
					var r = $("<div>" +
						"<div class=\"params\">" +
							"<div class=\"param-name\"></div>" +
							"<div class=\"param-value\">from:</div>" +
							"<div class=\"param-value\">" +
								"<select class=\"dropdown-from-scale\">" +
									"<option value=\"0\">0</option>" +
									"<option value=\"1\">1</option>" +
								"</select>" +
							"</div>" +
							"<div class=\"param-value\">to:</div>" +
							"<div class=\"param-value\">" +
								"<select class=\"dropdown-to-scale\">" +
									"<option value=\"5\">5</option>" +
									"<option value=\"7\">7</option>" +
									"<option value=\"9\">9</option>" +
									"<option disabled=\"true\">---</option>" +
									"<option value=\"3\">3</option>" +
									"<option value=\"4\">4</option>" +
									"<option value=\"5\">5</option>" +
									"<option value=\"6\">6</option>" +
									"<option value=\"7\">7</option>" +
									"<option value=\"8\">8</option>" +
									"<option value=\"9\">9</option>" +
									"<option value=\"10\">10</option>" +
								"</select>" +
							"</div>" +
						"</div>" +
						"<div class=\"params\">" +
							"<div class=\"param-name\"></div>" +
							"<div class=\"param-value\">" +
								"<div><span class=\"label-from-scale\">0</span>:<input type=\"text\" class=\"input-from-title\" maxlength=\"254\" autocomplete=\"off\"></div>" +
								"<div style=\"padding-bottom: 12px;\"><em style=\"color: #999\">(e.g. Poor, Disagree)</em></div>" +
							"</div>" +
						"</div>" +
						"<div class=\"params\">" +
							"<div class=\"param-name\"></div>" +
							"<div class=\"param-value\">" +
								"<div><span class=\"label-to-scale\">0</span>:<input type=\"text\" class=\"input-to-title\" maxlength=\"254\" autocomplete=\"off\"></div>" +
								"<div style=\"padding-bottom: 12px;\"><em style=\"color: #999\">(e.g. Excellent, Agree)</em></div>" +
							"</div>" +
						"</div>" +
						"<div class=\"params\">" +
							"<div class=\"param-name\"></div>" +
							"<div class=\"param-value ui-form\">" +
								"<div class=\"row-choice\">" +
									"<label><span><input type=\"checkbox\" id=\"checkbox_not_applicable\" class=\"checkbox-na\" autocomplete=\"off\" /></span>N/A</label>" +
									"<div style=\"padding: 10px 0 0 20px; display: none;\" class=\"container-na\">" +
										"<input type=\"text\" class=\"input-na\" autocomplete=\"off\" maxlength=\"254\" placeholder=\"N/A\" />" +
									"</div>" +
								"</div>" +
							"</div>" +
						"</div>" +
						"<div style=\"height: 24px; overflow: hidden;clear: both;\"></div>" +
						"<div class=\"params\">" +
							"<div class=\"param-name\"></div>" +
							"<div class=\"param-value\">" +
								"<a class=\"button-blue button-save\" title=\"Save\"><span>Save</span></a>" +
							"</div>" +
							/*
							"<div class=\"param-value\">" +
								"<a class=\"button-cancel\" title=\"Cancel\" style=\"margin-left: 6px;\">Cancel</a>" +
							"</div>" +
							*/
						"</div>" +
					"</div>").appendTo("#placeholder_control");
					
					
					
					
					
					
					// preview
					
					var p = $("<div><label class=\"label-from\"></label><span class=\"range\"></span><label class=\"label-to\"></label><label class=\"preview-container-na\" style=\"display: none;\"><input type=\"radio\" name=\"radio\"/><span class=\"label-na\">N/A</span></label></div>").appendTo(a);					
					var b = [5, 7, 9, 0, 3, 4, 5, 6, 7, 8, 9, 10];
					
					var buildRange = function(from, to) {
						var e = p.find(".range").empty();
						for (var y = from; y < (b[to] + 1); y++) {
							var j = $("<label><span>" + y + "</span><input type=\"radio\" name=\"radio\" /></label>");
							j.appendTo(e);
						}
					};
					
					
					
					
					
					
					var R = r.find('.label-from-scale');
	                var S = r.find('.label-to-scale');
	                var T = r.find('.dropdown-from-scale')[0];
	                var U = r.find('.dropdown-to-scale')[0];
					
					
					
					
					
					
					
					
					
					
					
					var V = function (a) {
	                    T.selectedIndex = a;
	                    R.text(T.options[T.selectedIndex].text);
	                };
	                var W = function (a) {
	                    U.selectedIndex = a;
	                    S.text(U.options[U.selectedIndex].text);
	                };
					
					r.find('.dropdown-from-scale')
					.unbind('change')
					.change(function () {
                        var from = ($(this)[0].selectedIndex != undefined ? $(this)[0].selectedIndex : 0);
                        
						V(from);
						
						buildRange(from, r.find('.dropdown-to-scale')[0].selectedIndex);
						
                    });
                    r.find('.dropdown-to-scale')
                    .unbind('change')
                    .change(function () {
                        var to = ($(this)[0].selectedIndex != undefined ? $(this)[0].selectedIndex : 0);
						
                        W(to);
						
						buildRange(r.find('.dropdown-from-scale')[0].selectedIndex, to);
						
                    });
                    
                    
                    if(data.control.fromScale != undefined 
                    		&& data.control.toScale != undefined) {
                    	V(data.control.fromScale);
                    	W(data.control.toScale);
                    	buildRange(r.find('.dropdown-from-scale')[0].selectedIndex, r.find('.dropdown-to-scale')[0].selectedIndex);
                    } else {
                    	V(1);
                    	W(0);
                    	buildRange(1, 0);
                    }
                    
                    
					
					
					
					// from
                    var c = r.find('.input-from-title')
					.bind("keyup", function() {
						p.find(".label-from").text($(this).val());
					});
						
                    if (data.control.fromTitle != undefined) {
                        c.val((data.control.fromTitle != "" ? data.control.fromTitle : ""));
						p.find(".label-from").text((data.control.fromTitle != "" ? data.control.fromTitle : ""));
                    }
					
					// to
                    var d = r.find('.input-to-title')
					.bind("keyup", function() {
						p.find(".label-to").text($(this).val());
					});
					// set data
					if (data.control.toTitle != undefined) {
                        d.val((data.control.toTitle != "" ? data.control.toTitle : ""));
						p.find(".label-to").text((data.control.toTitle != "" ? data.control.toTitle : ""));
                    }
                    
                    
                    
                    
                    // N/A
					r.find(".checkbox-na")
					.change(function () {
						if ($(this).is(':checked')) {
							r.find(".container-na").show();
							p.find(".preview-container-na").show();
							
						} else {
							r.find(".container-na").hide();
							p.find(".preview-container-na").hide();
						}
					});
					
					
					
					// N/A input
					r.find(".input-na")
					.val("N/A")
					.bind("keyup", function() {
						p.find(".label-na").text($(this).val());
					});
					
					
					// set N/A data
					if(data.control.isNa) {
						r.find(".checkbox-na").prop("checked", true).trigger("change");
						r.find(".input-na").val(data.control.na).trigger("keyup"); // ?
					}
                    
                    
					
					var v = null;
		        		v = new validator({
						elements : [
							{
								element : $('#textarea_question'),
								status : $('#status_question'),
								rules : [
									{ method : 'required', message : 'This field is required.' }
								]
							}
						],
						submitElement : r.find(".button-save"),
						accept : function () {
							
							result.content = $(".input-content").val(); // $.removeHTMLTags($(".input-content").val()).replace(/\r/g, "");
							result.opinionTitle = $(".input-content").val();
		    				result.isMandatory = $("#checkbox_mandatory").prop("checked");
		    				
							//result.isEnableNote = $("#checkbox_include_note").prop("checked");
		    				result.note = ($("#checkbox_include_note").prop("checked") && $(".textarea-note").val() != "" ? $(".textarea-note").val() : undefined);
							
		    				// link
		    				if($("#checkbox_link").prop("checked")) {
		                    	if($(".select-link-type").val() == 1) {
		                    		// link to image
		                    		if($(".input-image").val() != "") {
		                    			result.linkTypeId = 1;
		                    			result.link = $(".input-image").val();
		                    		} else {
		                    			result.linkTypeId = 0;
		                    			result.link = null;
		                    		}
		                    	}
		                    	
		                    	if($(".select-link-type").val() == 2) {
		                    		// embed code
		                    		if($('.textarea-embed-code').val() != "") {
		                    			result.linkTypeId = 2;
		                    			result.link = $('.textarea-embed-code').val();
		                    		} else {
		                    			result.linkTypeId = 0;
		                    			result.link = null;
		                    		}
		                    	}
		                    } else {
		                    	result.linkTypeId = 0;
		                    	result.link = null;
		                    }
		    				
		    				
		    				result.controlTypeId = 10;
		    				result.inputTypeId = 0;
		    				
		    				
		    				// add
		    				result.fromScale = T.selectedIndex;
		    				result.fromTitle = r.find(".input-from-title").val(); // remove html
		    				result.toScale = U.selectedIndex;
		    				result.toTitle = r.find(".input-to-title").val(); // remove html
		    				result.isNa = r.find(".checkbox-na").prop("checked");
		    				result.na = r.find(".input-na").val();
		    				
		    				
		    				
		    				// alert(JSON.stringify(result));
		    				
		    				
		    				// update
		    				updateControlDetails({
		    					control : result,
		    					success : function(newData) {
		    						// merge data -> result
		    						$.extend(result, newData.result);
		    						
		    						
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
		    					error: function(error) {
		    						alert("ERR -> update rating/scale");
		    					}
		    				});
							
						}
		        	});
                    
					
					break;
				}
				
			}
			
			
			
			
			
		};
		
		var lR = function(linkTypeId) {
        	if(link) {
            	
            	// image
            	if(linkTypeId == 1) {
            		var currentLink = $('.input-image').val();
            		s.find('.container-control-embed')
            			.empty()
            			.show();
            		
            		if($.trim(currentLink) != "") {
            			
            			/*
            			s.find('.container-control-embed')
            				.html("<img src=" + $('.input-image').val() + " />");
            			*/
            			
            			var img = new Image();
		                $(img).load(function(){
		                	//$('.container-media').append($(this));
		                	s.find('.container-control-embed').append($(this));
		                })
		                .attr({ src : $('.input-image').val() })
		                .error(function(){
		                  // do something if image cannot load
		                });
            			
            			
            		}
            		
            	}
            	
            	// embed code
            	if(linkTypeId == 2) {
            		s.find('.container-control-embed')
            			.empty()
            			.html($('.textarea-embed-code').val())
            			.show();
            	}
            }
		};
		
		
		
		var init = function() {
			
			
			
			
			// .bind( "blur change keyup", function(){
			$('#select_question_types').bind("change keyup", function(){
				
				var controlTypeId = parseInt($(this).val());
				var inputTypeId = ($('#select_question_types option:selected').attr("input_type_id") != undefined ? parseInt($('#select_question_types option:selected').attr("input_type_id")) : 0);
				
				if(result != null) {
					viewControl({
						controlTypeId : controlTypeId,
						inputTypeId : inputTypeId,
						control : result
					});
				} else {
					
					alert("result null");
					
					viewControl({
						controlTypeId : controlTypeId,
						inputTypeId : inputTypeId,
						control : {}
					});
				}
				
			});
			
			
			
			// no change drop down
			
			
			
			
			
			s = $("<div class=\"demo-question\">" +
					"<div class=\"container-control-content demo-question-heading\">" +
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
					"</div>" +
				"</div>");
			
			s.appendTo("#iframe_preview");
			
			
			
			
			
			
			
			// events
			// input-content events
			$('.input-content')
			.unbind("keyup")
			.bind("keyup", function() {
				$('#iframe_preview').find('.header-control-content').html($(this).val().replace(/\n/g, "<br/>"));
			});
			
			// mandatory
			$('#checkbox_mandatory').change(function() {
				if($(this).prop("checked")) {
					$('#iframe_preview').find('.label-mandatory').show();
				} else {
					$('#iframe_preview').find('.label-mandatory').hide();
				}
			});
			
			// link
			$("#checkbox_link").change(function() {
				if ($(this).prop("checked")) {
					$(".container-link").show();
					s.find('.container-control-embed').show();
				} else {
					$(".container-link").hide();
					s.find('.container-control-embed').hide();
					
					//$(".input-image").val("");
            		//$(".textarea-embed-code").val("");
				}
			});
			
			// set focus out on textarea-embed-code to show in preview embed
			
			// link type
			$(".select-link-type")
    		.unbind('change')
    		.change(function() {
    			
    			if($(this).val() == 1) {
    				$('.container-embed-code').hide();
    				$('.container-image').show();
    				
    				lR(1);
    			}
    			if($(this).val() == 2) {
    				$('.container-image').hide();
    				$('.container-embed-code').show();
    				
    				lR(2);
    			}
    		});
			
			
			/*
			// dropbox
			$("#button_dropbox").click(function(){
				
				Dropbox.choose({
					linkType: "direct",
					multiselect: false,
					success: function(files) {
						// Required. Called when a user selects an item in the Chooser
		                
						
						//alert("Here's the file link:" + files[0].link);
						
						
						var imgPath = files[0].link;
		                
						$(".input-image").val(imgPath);
						
						
						lR(1);
						
						// and render to view
						
						

						
						
					},
					cancel: function() {
						// Called when the user closes the dialog
		                // without selecting a file and does not include any parameters.
					}
				});
				
			});
			*/
			
			
		
			
			
			// note
			$("#checkbox_include_note").change(function() {
				if($(this).prop("checked")) {
					$(".container-note").show();
					$("#iframe_preview").find('.label-note').show();
				} else {
					$(".container-note").hide();
					$("#iframe_preview").find('.label-note').hide();
				}
			});
			
			$(".textarea-note")
			.unbind("keyup")
			.bind("keyup", function() {
				$("#iframe_preview").find('.label-note').html($(this).val().replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>"));
			});
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			/*
			getSheets({
				opinionId : options.opinionId,
				success : function(data) {
					
					poll.sheets.list = [];
					for(var i = 0; i < data.list.length; i++) {
						var sheet = data.list[i];
						poll.sheets.list.push(sheet);
					}
					
					if(poll.sheets.list.length != 0) {
						*/
						
						getControls({
							opinionId : options.opinionId, // poll.sheets.list[0].sheetId,
							success : function(data) {
								
								/*
								poll.controls.list = [];
								for(var i = 0; i < data.list.length; i++) {
									poll.controls.list.push(data.list[i]);
								}
								*/
								
								
								if(data.list.length != 0) {
									//if(poll.controls.list.length != 0) {
									
									
									result = data.list[0]; 
									//result = poll.controls.list[0];
									
									
									
									
									
									
									// one time tick controls
									// content
				        			if(result.content != undefined) {
				        				// r.find('.input-content').val(data.content);
				        				$('.input-content').val(result.content).trigger("blur").trigger("keyup");
				        			}
				        			
				        			// mandatory
				        			if (result.isMandatory) {
				        				$("#checkbox_mandatory").prop("checked", true).trigger("change");
				        			} else {
				        				//$("#checkbox_mandatory").prop("checked", false).trigger("change");
				        			}
				        			
				        			// link
				        			var lo = $('.select-link-type')[0];
				        			if(result.link) {
				        				
				        				link = result.link;
				        				
				        				$("#checkbox_link").prop('checked', true).change();
		                            		
	                            		// link to image
	                            		if(result.linkTypeId == 1) {
	                            			
	                						$('.input-image').val(result.link);
	                						
	                						lo.selectedIndex = 0;
                        					$('.select-link-type').change();
	                					}
	                					
	                            		// embed code
	                            		if(result.linkTypeId == 2) {
	                						$('.textarea-embed-code').val(result.link);
	                						
	                						lo.selectedIndex = 1;
                        					$('.select-link-type').change();
	                            		}
		                            	
		                            } else {
		                            	
		                            	
		                            	
		                            	//$("#checkbox_link").prop('checked', false).change();
		                            	
		                            	// show default
		                            	//lo.selectedIndex = 0;
		                    			//$('.select-link-type').change();
		                            	 
		                            }
				        			
				        			// include note - help text
				        			if(result.note != undefined) {
				        				
				        				$('.textarea-note')
				        					//.val(((result.note != "" && result.note != null) ? result.note : ""))
				        					.val(result.note).trigger("blur").trigger("keyup");
				        				
				        				//$('.label-note').html((result.note != "" ? result.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>") : "You can enter some information about this question here.")); //.show()
				        				
				        				$('#checkbox_include_note').prop("checked", true).trigger("change");
				        			}
				        			
									viewControl({
										controlTypeId : result.controlTypeId,
										inputTypeId : result.inputTypeId,
										control : result
									});
									
								}
								
							},
							error : function() {
								
								createControl({
									opinionId : options.opinionId,
									orderId : null,
									parentId : options.opinionId,
									parentTypeId : 1, // 1 poll, sheet 2, control 3
									controlTypeId : 2,
									content : "Enter your question here.",
									opinionTitle : "Enter your question here.",
									isEnableNote: false,
									note : null,
									isMandatory : false,
									isEnableComment : false,
									comment : null,
									inputTypeId : 0,
									inputSizeTypeId : 1,
									isEnableOther : false,
									options : getDefaultOptions(options.opinionId, 2),
									success : function(data) {
										
										result = data;
										
										// new default control
										viewControl({
											controlTypeId : 2, // multiple choice
											inputTypeId : 0,
											control : data
										});
										
										
										if(data.content != undefined) {
											$('.input-content')
											.val(data.content)
											.focus()
											.select();
											/*.trigger("blur").trigger("keyup");*/
										}
										
										
										
									},
									error: function(error) {
										if (window.console) {
					    					window.console.log(error);
					    				}
									}
								});
								
							
								
							
							
							}
						});
						
						
						
					/*	
					}
					
				}
			});
			*/
			
		};
		
		init();
		
		
	};
})(jQuery);