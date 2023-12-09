;(function(jQuery) {
	reordering = function(givenOptions) {
	
        var options = $.extend({
            opinionId : null
        }, givenOptions);
		
        var survey = { 
    		sheets: {
    			list: [],
    			lastSelected: 1
    		}
    	};
        
		var listReordering = $('.reordering');
		
		// page order
		var j = function() {
			
			$('ul.reordering li.reorder-page').each(function (i, el) {
				var current = $(el);
					
				current
				.attr({ "orderid" : i, "pagenumber" : (i + 1) })
				.find('.page-number').text((i + 1));
				
				// pagenumber
					
				current.find('.link-add-sheet').attr({ "orderid" : i , "pagenumber" : (i + 1) });
				current.find('.link-copy-sheet').attr({ "orderid" : i, "pagenumber" : (i + 1) });
					
			});
			
			// fix for delete button
			if($('ul.reordering li.reorder-page').length > 1) {
				$('.panel-page-actions .block-delete-sheet').show();
			} else {
				$('.panel-page-actions .block-delete-sheet').hide();
			}
			
		};
		
		// control order
		var h = function() {
			$('ul.reordering-controls').each(function(i, el) {
				var c = $(el);
				if(c.children('li.reorder-control').length == 0) {
					c.find('li.empty-handle').show();
				} else {
					c.find('li.empty-handle').hide();
					c.children('li.reorder-control').each(function(y, child) {
						$(child).attr({'orderid': y});
					});
				}
			});
			
			$('li.reorder-control .control-number').each(function(i, el) {
				
				//$(el).find('.control-number').text((i + 1));
				$(el).text(i + 1);
				
			});
			
		};
		
		// controls sortable
		var m = function() {
			
			$('ul.reordering-controls').each(function(i, n) {
				$(n).sortable({
			        handle: ".control-move",
			        cursor: "move",
			        opacity: 0.7,
			        axis: "y",
			        forcePlaceholderSize: true,
			        placeholder: "reorder-control-highlight",
			        connectWith: "ul.reordering-controls",
			        containment: ".se",
			        helper: function (q, p) {
			            return $(p).clone().addClass("dragging")
			        },
			        start: function (p, q) {
			            $(".split-handles").show().css({
			                top: q.placeholder.position().top - 7,
			                left: parseInt(q.placeholder.position().left) - 7
			            });
			        },
			        change: function (p, q) {
			            $(".split-handles").show().css({
			                top: q.placeholder.position().top - 7,
			                left: parseInt(q.placeholder.position().left) - 7
			            });
			        },
			        deactivate: function (p, q) {
			            $(".split-handles").hide();
			        },
			        stop : function(event, ui) {
			        	
						h();
						
						
						reorderControl({
							accountId : accountId,
							controlId : parseInt($(ui.item).attr('controlid')),
							sheetId : parseInt($(ui.item).parent().attr('parent')),
							orderId : parseInt($(ui.item).attr('orderid')),
							success : function() {
								
							},
							error: function() {
								
							}
						});
						
			        }
			    });
			});
			
		};
		
		var sheetIds = [];
		
		
		var addSinglePage = function(data, orderId) {
			
			var r = $("<li class=\"reorder-page\" sheetid=\"" + data.sheetId + "\" pagenumber=\"" + data.pageNumber + "\">" +
					"<div class=\"reorder-page-inner\">" +
						"<div class=\"reordering-heading\">" +
							"<div class=\"reordering-heading-container\">" +
								"<div class=\"page-move\"></div>" +
								"<div class=\"page-title\">" +
									"<h3><span class=\"page-number\"></span>. " + (data.title != null ? data.title : "Untitled") + "</h3>" +
								"</div>" +
							"</div>" +
							"<div class=\"panel-page-actions\">" +
								"<a href=\"" + options.editUrl + "?sheet_id=" + data.sheetId + "\" title=\"Go to\" sheetid=\"" + data.sheetId + "\" class=\"link-edit-sheet button-white\"><span>Go to</span></a>" +
								"<a title=\"Copy\" pagenumber=\"" + data.pageNumber + "\" sheetid=\"" + data.sheetId + "\" class=\"link-copy-sheet button-white\"><span>Copy</span></a>" +
								"<span class=\"block-delete-sheet\"><a title=\"Delete\" sheetid=\"" + data.sheetId + "\" class=\"link-delete-sheet\"><span>Delete</span></a></span>" +
							"</div>" +
						"</div>" +
						"<div class=\"reordering-outer\">" +
							"<ul parent=\"" + data.sheetId + "\" class=\"reordering-controls ui-sortable\" >" +
								"<li class=\"empty-handle\"><em>Drop question here...</em></li>" +
							"</ul>" +
						"</div>" +
						"<div class=\"reordering-logic\">&nbsp;</div>" +
					"</div>" +
					"<div class=\"reorder-page-actions\">" +
						"<a pagenumber=\"" + data.pageNumber + "\" class=\"link-add-sheet button-white\" title=\"Add Page\"><span><i class=\"icon-add\">&nbsp;</i>Add Page</span></a>" +
					"</div>" +
				"</li>").hide();
			
			
			getControls({
				accountId : accountId,
				sheetId : data.sheetId,
				success : function(controls) {
					
					var I = r.find('ul.reordering-controls');
					if(controls.list.length != 0) {
						
						I.empty();
						for(var x = 0; x < controls.list.length; x++) {
							
							var s = $("<li class=\"reorder-control\" controlid=\"" + controls.list[x].controlId + "\">" +
									"<div class=\"reorder-control-inner\" style=\"" + (controls.list[x].controlTypeId == 11 ? "background: #FFF9D7" : (controls.list[x].controlTypeId == 12 ? "background: #E9ECF8" : "")) + "\">" +	
									"<div class=\"control-move\"></div>" +
									"<div class=\"control-heading\">" +
										(controls.list[x].incalculable != undefined ? "" : "<span class=\"control-number\"></span>. ") +
										(controls.list[x].controlTypeId == 11 ? "[" + (controls.list[x].key != undefined ? controls.list[x].key : "undefined") + "]" : (controls.list[x].controlTypeId == 12 ? (controls.list[x].content != null ? controls.list[x].content : controls.list[x].note) : controls.list[x].content)) + 
									"</div>" +
								"</div>" +
								"<div class=\"reorder-control-actions\">" +
									"<a class=\"button-copy-control button-white\" title=\"Copy\"><span>Copy</span></a>" +
									"<a class=\"button-delete-control\" title=\"Delete\">Delete</a>" +
								"</div>" +
							"</li>").appendTo(I);
							
							
							s.find(".button-copy-control").click(function(event) {
								
								var el = $(this);
								
								controlCopyTo({
									accountId : accountId,
									controlId : parseInt(el.closest("li").attr("controlid")),
									opinionId : options.opinionId,
									sheetId : parseInt(el.closest("li").parent().closest("li").attr("sheetid")),
									translationId : 0,
									orderId : (parseInt(el.closest("li").attr("orderid")) + 1),
									success : function(data) {
									
										// add control
										addControl(data, el.closest("li").parent(), el.closest("li"));
										
									},
									error: function(error) {
										//
									}
								});
								
								event.preventDefault();
								
							});
							
							s.find(".button-delete-control").click(function(event) {
								
								var el = $(this);
								
								var modal = new lightFace({
									title : "Delete this question / snippet?",
									message : "<div style=\"padding: 0 0 12px 0\">Delete a question / snippet will remove all responses, rules or options<br/> that have been gathered for that question / snippet.</div><em style=\"color: #828282\">* This action cannot be undone.</em>",
									actions : [
							           { 
							        	   label : "Delete", 
							        	   fire : function() {
								        	   
							        		   // delete control
							        		   deleteControl({
							        			   accountId : accountId,
													controlId : parseInt(el.closest("li").attr("controlid")),
													success : function(data) {
														
														var current = el.closest("li"); //.fadeOut("slow", function() {
														//current.hide();
														current.remove();
															
															h();
															m();
															
														//});
														
													},
													error: function(error) {
														//
													}
												});
								        	   
								        	   modal.close();
								           },
								           color: "blue" 
							           },
							           { 
							        	   label : "Cancel", 
							        	   fire : function() { 
							        		   modal.close() 
							        	   }, 
							        	   color: "white" 
							           }
									],
									overlayAll : true
								});
								
								event.preventDefault();
								
							});
							
						}
						
						I.append("<li class=\"empty-handle\" style=\"display: none\"><em>Drop question here...</em></li>");
						
						
						h();
						m();
						
					}
					
				},
				error: function() {
					// 
				}
			});
			
			
			if(hideQuestions) {
				r.find('ul.reordering-controls').hide();
			}
			
			if(orderId) {
				$("ul.reordering li.reorder-page").each(function (i, el) {
					var current = $(el);
					if(current.attr("orderid") == orderId) {
						// after
						current
							.after(r);
					}
				});
			} else {
				// before
				$("ul.reordering li.reorder-page")
					.first()
					.before(r);
			}
			
			r.fadeIn("slow", function() { });
			
			// add page event
			r.find('.link-add-sheet').click(function(event) {
				var el = $(this); 
				w(parseInt(el.attr('pagenumber')), el.attr('orderid'));
				
				event.preventDefault();
			});
			
			// copy
			r.find(".link-copy-sheet").click(function(event) {
				
				var el = $(this);
				
				sheetCopyTo({
					accountId : accountId,
					sheetId : el.attr('sheetid'),
					pageNumber : (parseInt(el.attr('pagenumber')) + 1),
					success : function(data) {
						// add page
						addSinglePage(data, el.attr('orderid'));
					},
					error: function(error) {
						// alert("ERROR")
					}
				});
				
				event.preventDefault();
				
			});
			
			// delete
			r.find('.link-delete-sheet').click(function(event) {
				var el = $(this);
				d(parseInt(el.attr('sheetid')));
				
				event.preventDefault();
			});
			
			
			j(); 
			
		};
		
		var addControl = function(data, listElement, element) {
			
			var s = $("<li class=\"reorder-control\" controlid=\"" + data.controlId + "\">" +
					"<div class=\"reorder-control-inner\" style=\"" + (data.controlTypeId == 11 ? "background: #FFF9D7" : (data.controlTypeId == 12 ? "background: #E9ECF8" : "")) + "\">" +
					"<div class=\"control-move\"></div>" +
					"<div class=\"control-heading\">" +
						(data.incalculable != undefined ? "" : "<span class=\"control-number\"></span>. ") +
						(data.controlTypeId == 11 ? "[" + (data.key != undefined ? data.key : "undefined") + "]" : (data.controlTypeId == 12 ? (data.content != null ? data.content : data.note) : data.content)) +
					"</div>" +
				"</div>" +
				"<div class=\"reorder-control-actions\">" +
					"<a class=\"button-copy-control button-white\" title=\"Copy\"><span>Copy</span></a>" +
					"<a class=\"button-delete-control\" title=\"Delete\">Delete</a>" +
				"</div>" +
			"</li>").insertAfter(element);
			
			s.find(".button-copy-control").click(function(event) {
				
				var el = $(this);
				
				controlCopyTo({
					accountId : accountId,
					controlId : parseInt(el.closest("li").attr("controlid")),
					opinionId : options.opinionId,
					sheetId : parseInt(el.closest("li").parent().closest("li").attr("sheetid")),
					translationId : 0,
					orderId : (parseInt(el.closest("li").attr("orderid")) + 1),
					success : function(data) {
					
						// add control
						addControl(data, el.closest("li").parent(), el.closest("li"));
						
					},
					error: function(error) {
						//
					}
				});
				
				event.preventDefault();
				
			});
			
			s.find(".button-delete-control").click(function(event) {
				
				var el = $(this);
				
				var modal = new lightFace({
					title : "Delete this question / snippet?",
					message : "<div style=\"padding: 0 0 12px 0\">Delete a question / snippet will remove all responses, rules or options<br/> that have been gathered for that question / snippet.</div><em style=\"color: #828282\">* This action cannot be undone.</em>",
					actions : [
			           { 
			        	   label : "Delete", 
			        	   fire : function() {
				        	   
			        		   // delete control
			        		   deleteControl({
			        			   accountId : accountId,
									controlId : parseInt(el.closest("li").attr("controlid")),
									success : function(data) {
										
										var current = el.closest("li"); // .fadeOut("slow", function() {
										
										current.remove();
											
											h();
											m();
											
										//});
										
									},
									error: function(error) {
										//
									}
								});
				        	   
				        	   modal.close();
				           },
				           color: "blue" 
			           },
			           { 
			        	   label : "Cancel", 
			        	   fire : function() { 
			        		   modal.close() 
			        	   }, 
			        	   color: "white" 
			           }
					],
					overlayAll : true
				});
				
				event.preventDefault();
				
			});
			
			
			h();
			m();
			
		};
		
		var b = function() {
			
			var n = $("<a title=\"Add Page\" class=\"link-add-sheet button-white\" pagenumber=\"0\"><span><i class=\"icon-add\">&nbsp;</i>Add Page</span></a>").appendTo('#panel-actions');
			n.click(function(event) {
				var el = $(this); 
				w(parseInt(el.attr('pagenumber')));
				
				event.preventDefault();
			}); 
			
			// sorting implementation
			listReordering.sortable({
				handle: ".page-move",
				cursor : "move",
				opacity : 0.7,
				axis: "y",
				placeholder : "reorder-page-highlight",
				/*containment: "#placeholder_elements",*/
				start : function(k, l) {
					$('.split-handles').css( {
						top : l.placeholder.position().top - 10
					}).show();
					$(this).sortable("refreshPositions");
				},
				change : function(k, l) {
					$(".split-handles").css( {
						top : l.placeholder.position().top - 10
					}).show();
				},
				update: function () {
			        //d()
			    },
				deactivate : function(k, l) {
					$('.split-handles').hide();
				},
				stop : function(event, ui) {
					
					j();
					
					sheetIds = [];
					$("ul.reordering li.reorder-page").each(function(index, el) {
						sheetIds.push(parseInt($(el).attr('sheetid')));
					});
					
					reorderSheets({
						accountId : accountId,
						sheetIds : sheetIds,
						success : function() {
							
						},
						error: function() {
							
						}
					});
					
				}
			});
			
			getSheetsAndControls({
				accountId : accountId,
				opinionId : options.opinionId,
				success : function(data) {
					
					survey.sheets.list = [];
					
					for(var i = 0; i < data.list.length; i++) {
						var sheet = data.list[i];
						// push created sheet to sheets.list
						survey.sheets.list.push(sheet);
					}
					
					if(survey.sheets.list.length > 0) {
						
						for(var i = 0; i < survey.sheets.list.length; i++) {
							
							var r = $("<li class=\"reorder-page\" sheetid=\"" + survey.sheets.list[i].sheetId + "\" pagenumber=\"" + survey.sheets.list[i].pageNumber + "\">" +
									"<div class=\"reorder-page-inner\">" +
										"<div class=\"reordering-heading\">" +
											"<div class=\"reordering-heading-container\">" +
												"<div class=\"page-move\"></div>" +
												"<div class=\"page-title\">" +
													"<h3><span class=\"page-number\"></span>. " + (survey.sheets.list[i].title ? survey.sheets.list[i].title : "Untitled") + "</h3>" +
												"</div>" +
											"</div>" +
											"<div class=\"panel-page-actions\">" +
												"<a href=\"" + options.editUrl + "?sheet_id=" + survey.sheets.list[i].sheetId + "\" title=\"Go to\" sheetid=\"" + survey.sheets.list[i].sheetId + "\" class=\"link-edit-sheet button-white\"><span>Go to</span></a>" +
												"<a title=\"Copy\" pagenumber=\"" + survey.sheets.list[i].pageNumber + "\" sheetid=\"" + survey.sheets.list[i].sheetId + "\" class=\"link-copy-sheet button-white\"><span>Copy</span></a>" +
												"<span class=\"block-delete-sheet\"><a title=\"Delete\" sheetid=\"" + survey.sheets.list[i].sheetId + "\" class=\"link-delete-sheet\"><span>Delete</span></a></span>" +
											"</div>" +
										"</div>" +
										"<div class=\"reordering-outer\">" +
											"<ul class=\"reordering-controls\" parent=\"" + survey.sheets.list[i].sheetId + "\">" +
												"<li class=\"empty-handle\"><em>Drop question here...</em></li>" +
											"</ul>" +
										"</div>" +
										"<div class=\"reordering-logic\">&nbsp;</div>" +
									"</div>" +
									"<div class=\"reorder-page-actions\">" +
										"<a title=\"Add Page\" class=\"link-add-sheet button-white\" pagenumber=\"" + survey.sheets.list[i].pageNumber + "\"><span><i class=\"icon-add\">&nbsp;</i>Add Page</span></a>" +
									"</div>" +
							"</li>").appendTo(listReordering);
							
							if(survey.sheets.list[i].controls) {
								
								var I = r.find('ul.reordering-controls');
								if(survey.sheets.list[i].controls.length != 0) {
									
									I.empty();
									
									for(var x = 0; x < survey.sheets.list[i].controls.length; x++) {
										
										var s = $("<li class=\"reorder-control\" controlid=\"" + survey.sheets.list[i].controls[x].controlId + "\">" +
											"<div class=\"reorder-control-inner\" style=\"" + (survey.sheets.list[i].controls[x].controlTypeId == 11 ? "background: #FFF9D7" : (survey.sheets.list[i].controls[x].controlTypeId == 12 ? "background: #E9ECF8" : "")) + "\">" +	
												"<div class=\"control-move\"></div>" +
												"<div class=\"control-heading\">" +
													(survey.sheets.list[i].controls[x].incalculable != undefined ? "" : "<span class=\"control-number\"></span>. ") +
													(survey.sheets.list[i].controls[x].controlTypeId == 11 ? "[" + (survey.sheets.list[i].controls[x].key != undefined ? survey.sheets.list[i].controls[x].key : "undefined") + "]" : (survey.sheets.list[i].controls[x].controlTypeId == 12 ? (survey.sheets.list[i].controls[x].content != null ? survey.sheets.list[i].controls[x].content : survey.sheets.list[i].controls[x].note) : survey.sheets.list[i].controls[x].content)) + 
												"</div>" +
											"</div>" +
											"<div class=\"reorder-control-actions\">" +
												"<a class=\"button-copy-control button-white\" title=\"Copy\"><span>Copy</span></a>" +
												"<a class=\"button-delete-control\" title=\"Delete\">Delete</a>" +
											"</div>" +
										"</li>").appendTo(I);
										
										s.find(".button-copy-control").click(function(event) {
											
											var el = $(this);
											
											controlCopyTo({
												accountId : accountId,
												controlId : parseInt(el.closest("li").attr("controlid")),
												opinionId : options.opinionId,
												sheetId : parseInt(el.closest("li").parent().closest("li").attr("sheetid")),
												translationId : 0,
												orderId : (parseInt(el.closest("li").attr("orderid")) + 1),
												success : function(data) {
												
													// add control
													addControl(data, el.closest("li").parent(), el.closest("li"));
													
												},
												error: function(error) {
													//
												}
											});
											
											event.preventDefault();
											
										});
										
										s.find(".button-delete-control").click(function(event) {
											
											var el = $(this);
											
											var modal = new lightFace({
												title : "Delete this question / snippet?",
												message : "<div style=\"padding: 0 0 12px 0\">Delete a question / snippet will remove all responses, rules or options<br/> that have been gathered for that question / snippet.</div><em style=\"color: #828282\">* This action cannot be undone.</em>",
												actions : [
										           { 
										        	   label : "Delete", 
										        	   fire : function() {
											        	   
										        		   // delete control
										        		   deleteControl({
										        			   accountId : accountId,
																controlId : parseInt(el.closest("li").attr("controlid")),
																success : function(data) {
																	
																	var current = el.closest("li"); //.fadeOut("slow", function() {
																	current.remove();
																		
																		h();
																		m();
																		
																	//});
																	
																},
																error: function(error) {
																	//
																}
															});
											        	   
											        	   modal.close();
											           },
											           color: "blue" 
										           },
										           { 
										        	   label : "Cancel", 
										        	   fire : function() { 
										        		   modal.close() 
										        	   }, 
										        	   color: "white" 
										           }
												],
												overlayAll : true
											});
											
											event.preventDefault();
											
										});
										
									}
									I.append("<li class=\"empty-handle\" style=\"display: none\"><em>Drop question here...</em></li>");
								}
								
								/*
								else {
									I.append("<li class=\"empty-handle\"><em>Drop question here...</em></li>");
								}
								*/
							}
							
							
							// add page event
							r.find('.link-add-sheet').click(function(event) {
								var el = $(this); 
								w(parseInt(el.attr('pagenumber')), el.attr('orderid'));
								
								event.preventDefault();
							});
							
							// copy
							r.find(".link-copy-sheet").click(function(event) {
								
								var el = $(this);
								
								sheetCopyTo({
									accountId : accountId,
									sheetId : el.attr('sheetid'),
									pageNumber : (parseInt(el.attr('pagenumber')) + 1),
									success : function(data) {
										// add page
										addSinglePage(data, el.attr('orderid'));
									},
									error: function(error) {
										// alert("ERROR")
									}
								});
								
								event.preventDefault();
								
							});
							
							// delete
							r.find('.link-delete-sheet').click(function(event) {
								var el = $(this);
								d(parseInt(el.attr('sheetid')));
								
								event.preventDefault();
							});
							
						}
						
						j(); 
						h();
						m();
						
					}
					
					
					
				},
				error: function() {
					
					survey.sheets.list = [];
					
				}
			});
			
		};
		
		// delete sheet
		var d = function(sheetId) {
			
			var u = function(i) {
				
				deleteSheet({
					accountId : accountId,
					sheetId : sheetId,
					success : function() {
						
						$("ul.reordering li.reorder-page").each(function (i, el) {
							var current = $(el);
							if(current.attr("sheetid") == sheetId) {
								
								current.remove();
									
								j();
								h();
								m();
							}
						});
						
					},
					error: function() {
						
					}
				});
				
			};
			
			// use confirmation message for delete page/sheet
			var modal = new lightFace({
				title : "Are you sure you want to delete this page?",
				message : "Deleting this page will remove the following:" +
					"<div style=\"padding: 12px 0 12px 0\">" +
						"<ul class=\"ls\">" +
							"<li>All questions on this page</li>" +
							"<li>All responses for questions on this page</li>" +
							"<li>All rules that refer to this page</li>" +
						"</ul>" +
					"</div>" +
					"<em style=\"color: #999\">* This action cannot be undone.</em>",
				actions : [
		           { 
		        	   label : "Delete", 
		        	   fire : function() { 
			        	   u(sheetId); 
			        	   modal.close(); 
		        	   }, 
		        	   color: "blue" 
		           },
		           { 
		        	   label : "Cancel", 
		        	   fire : function() { 
		        		   modal.close(); 
		        	   }, 
		        	   color: "white" 
		        	}
				],
				overlayAll : true
			});
			
		};
		
		var w = function(pageNumber, orderId) {
			
			createSheet({
				accountId : accountId,
				opinionId : options.opinionId,
				pageNumber : (pageNumber + 1),
				success : function(data) {
					
					var r = $("<li class=\"reorder-page\" sheetid=\"" + data.sheetId + "\" pagenumber=\"" + data.pageNumber + "\">" +
							"<div class=\"reorder-page-inner\">" +
								"<div class=\"reordering-heading\">" +
									"<div class=\"reordering-heading-container\">" +
										"<div class=\"page-move\"></div>" +
										"<div class=\"page-title\">" +
											"<h3><span class=\"page-number\"></span>. Untitled</h3>" +
										"</div>" +
									"</div>" +
									"<div class=\"panel-page-actions\">" +
										"<a href=\"" + options.editUrl + "?sheet_id=" + data.sheetId + "\" title=\"Go to\" sheetid=\"" + data.sheetId + "\" class=\"link-edit-sheet button-white\"><span>Go to</span></a>" +
										"<a title=\"Copy\" pagenumber=\"" + data.pageNumber + "\" sheetid=\"" + data.sheetId + "\" class=\"link-copy-sheet button-white\"><span>Copy</span></a>" +
										"<span class=\"block-delete-sheet\"><a title=\"Delete\" sheetid=\"" + data.sheetId + "\" class=\"link-delete-sheet\"><span>Delete</span></a></span>" +
									"</div>" +
								"</div>" +
								"<div class=\"reordering-outer\">" +
									"<ul parent=\"" + data.sheetId + "\" class=\"reordering-controls ui-sortable\" >" +
										"<li class=\"empty-handle\"><em>Drop question here...</em></li>" +
									"</ul>" +
								"</div>" +
								"<div class=\"reordering-logic\">&nbsp;</div>" +
							"</div>" +
							"<div class=\"reorder-page-actions\">" +
								"<a pagenumber=\"" + data.pageNumber + "\" class=\"link-add-sheet button-white\" title=\"Add Page\"><span><i class=\"icon-add\">&nbsp;</i>Add Page</span></a>" +
							"</div>" +
						"</li>").hide();
					
					if(hideQuestions) {
						r.find('ul.reordering-controls').hide();
					}
					
					if(orderId) {
						$("ul.reordering li.reorder-page").each(function (i, el) {
							var current = $(el);
							if(current.attr("orderid") == orderId) {
								// after
								current
									.after(r);
							}
						});
					} else {
						// before
						$("ul.reordering li.reorder-page")
							.first()
							.before(r);
					}
					
					r.fadeIn("slow", function() { });
					
					// add page event
					r.find('.link-add-sheet').click(function(event) {
						var el = $(this); 
						w(parseInt(el.attr('pagenumber')), el.attr('orderid'));
						
						event.preventDefault();
					});
					
					// copy
					r.find(".link-copy-sheet").click(function(event) {
						
						var el = $(this);
						
						sheetCopyTo({
							accountId : accountId,
							sheetId : el.attr('sheetid'),
							pageNumber : (parseInt(el.attr('pagenumber')) + 1),
							success : function(data) {
								// add page
								addSinglePage(data, el.attr('orderid'));
							},
							error: function(error) {
								// alert("ERROR")
							}
						});
						
						event.preventDefault();
						
					});
					
					// delete
					r.find('.link-delete-sheet').click(function(event) {
						var el = $(this);
						d(parseInt(el.attr('sheetid')));
						
						event.preventDefault();
					});
					
					j(); 
					h();
					m();
					
				},
				error: function() {
					
					
					
				}
			});
			
			
		};
		
		b();
	}
})(jQuery);
