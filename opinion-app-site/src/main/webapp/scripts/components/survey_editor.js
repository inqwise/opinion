/*!
 * Survey Editor
 * Copyright (c) 2011 - 2014 Inqwise, Ltd
 *
 */

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

function getDefaultSubControls(opinionId) {
	
	var obj = {
		list : [
			{
				opinionId : opinionId,
				controlTypeId : 9,
				parentTypeId : 3,
				content : "Row 1",
				orderId : 0,
				status : "added"
			}
		]
	};
	
	return obj;
	
};




var useQuestionNumbering = true;
(function(jQuery) {
	surveyEditor = function(givenOptions) {

		//var sheets = null;

		var options = $.extend({
			name : null,
			opinionId : null,
			data : null,
			survey : null
		}, givenOptions);

		//sheets = options.survey.sheets;
		controls = options.survey.controls;

		var sheets = [];
		
		var removeSheetIfExist = function(sheetId) {
			for(var i = 0; i < sheets.length; i++) {
				if(sheets[i].sheetId == sheetId) {
					sheets.remove(i); // remove by index
					break;
				}
			}
		};
		
		var addSheetIfNotExits = function(data) {
			
			var found = false;
			for(var i = 0; i < sheets.length; i++) {
				if(sheets[i].sheetId == data.sheetId) {
					found = true;
					break;
				}
			}
			
			if(!found) {
				sheets.push(data);
			} else {
				updateSheetDetails(data);
			}
			
		};
		
		var updateSheetDetails = function(data) {
			
			for(var i = 0; i < sheets.length; i++) {
				if(sheets[i].sheetId == data.sheetId) {
					
					// update
					sheets[i] = data;
					
					break;
				}
			}
			
		};
		
		var getSheetDetails = function(sheetId) {
			
			var obj = null;
			for(var i = 0; i < sheets.length; i++) {
				if(sheets[i].sheetId == sheetId) {
					obj = sheets[i];
					break;
				}
			}
			return obj;
			
		};
		
		
		var currentSheetId = null;

		
		
		var lastTab = null;
		
		var refreshSheets = function() {
			$('#sortable li').each(function(i, el) {
				var tab = $(el);
				tab.find(".title").text("Page " + (i + 1));
			});
		};
		
		var clearSelectedSheet = function() {
			
			// show paging
			//if(sheets.list.length == 1) {
			if($('#sortable li').length == 1) {
				$('.container-paging').hide();
			} else {
				if(options.data.showPaging != undefined) {
					if(options.data.showPaging) {
						$('.container-paging').show();
					} else {
						$('.container-paging').hide();
					}
				} else {
					$('.container-paging').show();
				}
			}
			
			// demo paging
			$('.label-end-page-number').text($('#sortable li').length);
			
		};

		var selectSheet = function(index) {
			
			if ($('#sortable li').length != 0) {
				
				clearSelectedSheet();
				
				var sheetId = ($.getUrlParam("sheet_id").length != 0 ? $.getUrlParam("sheet_id") : null);
				if(sheetId != null) {
					
					$('#sortable li').each(function(i, el) {
						var tab = $(el);
						if(tab.attr("data-value") == sheetId) {
							index = i;
							return;
						}
					});
					
				}
				
				$('#sortable li').each(function(i, el) {
					var tab = $(el);
					if(index == i) {
						tab.trigger("click");
						return;
					}
				});

			}
			
		};

		
		var t = 0;
		var refreshActions = function() {
			
			$("#sortable li")
			.unbind('click')
			.click(function() {
				
				var tab = $(this);
				
				
				//console.log(lastTab + "_________" + tab);
				//console.log(tab.attr("data-value") + "___________" + currentSheetId);
				
				if(lastTab != null && lastTab.attr("data-value") != tab.attr("data-value")) {
					lastTab.removeClass('active');
				}
				
				//tab.addClass('active');
				//lastTab = tab;
				
				
				var j = $('.sortable-section');
				var b = tab.offset();
	            var c = j.offset();
	            var e = b.left - c.left;
	            var f = c.left + j.outerWidth() - (b.left + tab.outerWidth());
	            
	            //console.log(e + "______" + f);
	            
				
	            if(e < 0) {
	            	slideTabs("right", -e);
	            } else if (f < 0) { 
	            	slideTabs("left", -f); 
	            }
	            
	            
	            
	            
	            if(tab.attr("data-value") != currentSheetId) {
	            
	            
		            // action
		            clearSelectedSheet();
		            
					//if(sheets.list.length == 1) {
		            if($("#sortable li").length == 1) {
						$('.container-previous').hide();
						$('.container-next').hide();
						$('.container-finish').show();
					} else if (tab.index() == ($("#sortable li").length - 1)){
						$('.container-previous').show();
						$('.container-next').hide();
						$('.container-finish').show();
					} else if (tab.index() == 0) {
						$('.container-previous').hide();
						$('.container-next').show();
						$('.container-finish').hide();
					} else {
						$('.container-previous').show();
						$('.container-next').show();
						$('.container-finish').hide();
					}
					
		            // demo start page number
					if(options.data.usePageNumbering) {
						$('#label_page_number').html((tab.index() + 1) + ".&nbsp;");
					}
					$('.label-start-page-number').text((tab.index() + 1));
					
		            
		            //console.log(tab.index());
		            
		            
		            
		            
		            // update
		            currentSheetId = tab.attr("data-value");
		            
					
					
					
					
					
					setSheet(tab.attr("data-value"));
					
					
		            getControlsA(tab.attr("data-value"), tab.index(), function() {
		            	//console.log("done");
		            });
					
		            
		            
		            
		            // set active
		            tab.addClass('active');
					lastTab = tab;
				
	            }
				
				
				 
			});
			 
			$("#sortable li span.action")
			.unbind('click')
			.click(function() {
				
				 var element = $(this);
				 var tab = element.closest("li");
				 if(tab.hasClass('active')) {
					
					var dropMenu = $("<div class=\"drop-menu\">" +
						"<ul>" +
							"<li data-name=\"move-left\"><a>Move left</a></li>" +
							"<li data-name=\"move-right\"><a>Move right</a></li>" +
							"<li class=\"separator\"></li>" +
							"<li data-name=\"duplicate\"><a>Duplicate</a></li>" +
							"<li data-name=\"delete\"><a>Delete</a></li>" +
					 	"</ul>" +
					 "</div>").css({ left : tab.offset().left, top : tab.offset().top + 24 }).appendTo("body");
					
					if(tab.index() == 0 && $('#sortable li').length == 1) {
						dropMenu.find("li").each(function(i, el) {
							if($(el).attr("data-name") == "move-left" || $(el).attr("data-name") == "move-right" || $(el).attr("data-name") == "delete") {
								$(el).addClass('disabled');
							}
						});
					} else if(tab.index() == 0 && $('#sortable li').length != 1) {
						dropMenu.find("li").each(function(i, el) {
							if($(el).attr("data-name") == "move-left") {
								$(el).addClass('disabled');
							}
						});
					} else if(tab.index() == ($('#sortable li').length - 1)) {
						dropMenu.find("li").each(function(i, el) {
							if($(el).attr("data-name") == "move-right") {
								$(el).addClass('disabled');
							}
						});
					} 
					
					
					
					
					
					dropMenu.find("li").click(function(e) {
						var item = $(this);
						if(!item.hasClass('disabled')) {
							
							// move-right
							if(item.attr("data-name") == "move-right") {
								
								var current = tab;
								current
									.next()
									.after(current);
								
								
								//console.log("move right");
								
								
								
								
								var ids = [];
								$('#sortable li').each(function(i, el) {
									ids.push(parseInt($(el).attr("data-value")));
								});
								
								
								//console.log(ids);
								
								
								reorderSheets({
									accountId : accountId,
									sheetIds : ids,
									success : function() {
										refreshSheets();
									},
									error: function() {
										
									}
								});
								
								
								current.trigger("click");
								
							}
							
							// move-left
							if(item.attr("data-name") == "move-left") {
								
								var current = tab;
								current
									.prev()
									.before(current);
								
								//console.log("move left");
								
								
								
								var ids = [];
								$('#sortable li').each(function(i, el) {
									ids.push(parseInt($(el).attr("data-value")));
								});
								
								
								//console.log(ids);
								
								
								reorderSheets({
									accountId : accountId,
									sheetIds : ids,
									success : function() {
										refreshSheets();
									},
									error: function() {
										
									}
								});
								
								
								current.trigger("click");
								
							}
							
							// duplicate
							if(item.attr("data-name") == "duplicate") {
								
								
								sheetCopyTo({
									accountId : accountId,
									sheetId : tab.attr("data-value"),
									pageNumber : (tab.index() + 2),
									success : function(data) {
										
										// duplicate sheet
										//console.log(JSON.stringify(data));
										
										addSheetIfNotExits(data);
										
										
										var newItem = $("<li class=\"ui-state-default\" data-value=\"" + data.sheetId + "\"><span class=\"title\">Page " + (lastTab.index() + 1) + "</span><span class=\"action\"></span></li>");
										
										lastTab.after(newItem);
										
										
										t = $('.sortable-section').outerWidth() - $('#sortable').width();
										refreshActions();
										
										
										refreshSheets();
										
									
										newItem.trigger('click');
										
										
										
									},
									error: function(error) {
										// 
									}
								});
								
								
								
								
								/*
								_duplicateSheet(tab.index(), function() {
									console.log("to do -> after duplication");
									refreshSheets(function() {
										getSheet(true);
										//selectSheet(i + 1);
									});
								});
								*/
								
								
								
								
							}
							
							// delete
							if(item.attr("data-name") == "delete") {
								
								
								//console.log(tab.attr("data-value"));
								
								deleteSheet({
									accountId : accountId,
									sheetId : tab.attr("data-value"),
									success : function(data) {
										
										
										removeSheetIfExist(tab.attr("data-value"));
										
										
										if(tab.index() != 0) {
											
											var prev = tab.prev();
											tab.remove();
										
											t = $('.sortable-section').outerWidth() - $('#sortable').width();
										
											// active prev tab after delete
											prev.trigger('click');
											
										} else {
											
											var next = tab.next();
											tab.remove();
											
											t = $('.sortable-section').outerWidth() - $('#sortable').width();
										
											// active prev tab after delete
											next.trigger('click');
											
										}
										
										refreshSheets();
										
										clearSelectedSheet();
										
										
									},
									error: function(error) {
										console.log(JSON.stringify(error));
									}
								});
								
								
								/*
								deleteSheet(sheets.list[index].sheetId, function() {
									
									console.log("before delete -> " + m.length);
									
									m.remove(index);
									survey.sheets.list.remove(index);
									
									console.log("after delete -> " + m.length);
									
									if(callback != undefined 
											&& typeof callback == 'function') {
										callback();
									}
									
								});
								*/
								
								
								
								/*
								_deleteSheet(tab.index(), function() { 
									
									
									console.log("delete");
									
									
									
									if(tab.index() != 0) {
										var prev = tab.prev();
									
										tab.remove();
									
										t = $('.sortable-section').outerWidth() - $('#sortable').width(); // j.width()-b
									
										// active prev tab after delete
										prev.trigger('click');
									} else {
										var next = tab.next();
										tab.remove();
										
										t = $('.sortable-section').outerWidth() - $('#sortable').width(); // j.width()-b
									
										// active prev tab after delete
										next.trigger('click');
									}
									
									
									clearSelectedSheet();
									
									
								});
								*/
								
								
							}
							
							
							dropMenu.remove();
						}
					});
					
					 
					 $(document).mouseup(function (e){
				    	var container = dropMenu;
				    	if (!container.is(e.target) // if the target of the click isn't the container...
				        	&& container.has(e.target).length === 0) // ... nor a descendant of the container
				    		{
				        		container.remove();
				    		}
					 });
					 
					 
				 }
			});
			
			
			//console.log($('#sortable').width());
			
		};
		
		
		
		
		var slideTabs = function (a, b) {
			
			var c = parseInt($('#sortable').css("margin-left"));
	        var d = c;
	    	
			a == "right" ? d += b : a == "left" && (d -= b), d >= 0 ? (d = 0) : d <= t && (d = t);
			
			if(a == "right" && ($('#sortable').width() < $('.sortable-section').outerWidth())) {
				d = 0;
			}
			
			$('#sortable').animate({
	            "margin-left": d
	        }, 200);
			
		};
		
		$('.add-page').click(function() {
			
			// console.log("create page -> " + (lastTab != null ? (lastTab.index() + 1) : 1));
			
			createSheet({
				accountId : accountId,
				opinionId : options.opinionId,
				pageNumber : (lastTab != null ? (lastTab.index() + 2) : 1), // + 2
				success : function(data) {
					
					//console.log("add tab + refreshActions");
					
					
					addSheetIfNotExits(data);
					
					
					var newItem = $("<li class=\"ui-state-default\" data-value=\"" + data.sheetId + "\"><span class=\"title\">Page " + (lastTab != null ? (lastTab.index() + 1) : 1) + "</span><span class=\"action\"></span></li>");
					
					
					if(lastTab != null) {
						lastTab.after(newItem);
					} else {
						newItem.appendTo("#sortable");
						
						//sheets.push(data.list[i]);
					}
					
					
					t = $('.sortable-section').outerWidth() - $('#sortable').width();
					refreshActions();
					
					
					
					refreshSheets();
				
					newItem.trigger('click');
					
				},
				error: function() {
					console.log("ERROR");
				}
			});
			
		});
		
		$('.scroll-right').click(function() {
			slideTabs("left", parseInt($('#sortable').width() - $('.sortable-section').outerWidth()));
		});
		
		$('.scroll-left').click(function() {
			slideTabs("right", parseInt($('#sortable').width() - $('.sortable-section').outerWidth()));
		});
		
		$('.all-pages').click(function() {
			
			var element = $(this);
			
			var listMenu = $("<div class=\"list-menu\">" +
				"<ul>Items</ul>" +
			"</div>").css({ left : element.offset().left, top : element.offset().top + 24 }).appendTo("body");
			
			
			var list = listMenu.find("ul").empty();
			$('#sortable li').each(function(i, el) {
				var tab = $(el);
				var menuItem = $("<li><a>Page " + (i + 1) + "</a></li>").appendTo(list);
				if(tab.hasClass("active")) {
					menuItem.addClass("active");
				}
				menuItem.click(function() {
					$("#sortable li:eq(" + $(this).index() + ")").trigger('click');
					
					listMenu.remove();
					
				});
			});
			
			if(listMenu.height() > 254) {
				listMenu.css({ "height" : 254, "overflow" : "auto" });
			}
			
			$(document).mouseup(function (e){
		    	var container = listMenu;
		    	if (!container.is(e.target) // if the target of the click isn't the container...
		        	&& container.has(e.target).length === 0) // ... nor a descendant of the container
		    		{
		        		container.remove();
		    		}
			 });
			
		});
		
		
		// check it
		var setSheet = function(sheetId) {
			
			// demo survey heading
			$('.demo-survey-heading').text(options.name);
			$('#placeholder_sheet_details').empty();
			
			// <b class=\"label-sheet-number\">" + (currentSheetIndex + 1) + "</b>.&nbsp;
			var d = $("<div>" +
				"<div class=\"row\">" +
					"<div class=\"cell\"><input type=\"text\" class=\"input-sheet-title text-field\" autocomplete=\"off\" placeholder=\"Untitled page\" maxlength=\"255\" style=\"width: 225px;\" /></div>" +
				"</div>" +
				"<div class=\"row\" style=\"height: 56px;\">" +
					"<div class=\"cell\"><textarea class=\"textarea-sheet-description\" autocomplete=\"off\" placeholder=\"You can include any text or info that will help people fill this out\" style=\"width: 314px; height: 46px;\" ></textarea></div>" +
				"</div>" +
			"</div>").appendTo('#placeholder_sheet_details');
			
			var inputSheetTitle = d.find('.input-sheet-title');
			var textAreaSheetDescription = d.find('.textarea-sheet-description');
			
			inputSheetTitle
			.val('')
			.focusout(function () {
				
				var o = $(this);
				var v = o.val();
				var m = o.attr('placeholder');
				var p = o.attr('prev');
				
				if(v != "") {
					if(v != m 
							&& v != p) {
						updateSheet({
							accountId : accountId,
							sheetId : currentSheetId,
							title : v,
							description : (textAreaSheetDescription.val() != textAreaSheetDescription.attr('placeholder') ? textAreaSheetDescription.val() : ""),
							success : function(data) {
								
								updateSheetDetails(data);
								
								// update prev
								o.attr('prev', v);
							},
							error: function(error) {
								
							}
						});
					} else {
						// clear
						if(v == m) {
							updateSheet({
								accountId : accountId,
								sheetId : currentSheetId,
								title : "",
								description : (textAreaSheetDescription.val() != textAreaSheetDescription.attr('placeholder') ? textAreaSheetDescription.val() : ""),
								success : function(data) {
									
									updateSheetDetails(data);
									
									o.attr('prev', ''); 
								},
								error: function(error) {
									
								}
							});
						}
					}
					
				} else {
					if(o.attr('prev') != undefined) {
						if(o.attr('prev') != "") {
							updateSheet({
								accountId : accountId,
								sheetId : currentSheetId,
								title : "",
								description : (textAreaSheetDescription.val() != textAreaSheetDescription.attr('placeholder') ? textAreaSheetDescription.val() : ""),
								success : function(data) { 
									
									updateSheetDetails(data);
									
									o.attr('prev', ''); 
								},
								error: function(error) {
									
								}
							});
						}
					}
				}
			})
			.unbind("keyup")
			.bind("keyup", function() {
				
				if($(this).val() != "") {
				
					$('.header-page-title')
					.show();
					
					$('#label_page_title')
					.text($(this).val());
				
				
					$('.demo-page-description').show();
				
				} else {
					
					$('.header-page-title')
					.hide();
					
					$('#label_page_title')
					.text("");
					
					if(textAreaSheetDescription.val() == "") {
						$('.demo-page-description').hide();
					}
				}
				
			});
			
			textAreaSheetDescription
			.val('')
			.focusout(function () {
				
				var o = $(this);
				var v = o.val();
				var m = o.attr('placeholder');
				var p = o.attr('prev');
				
				if(v != "") {
					if(v != m 
							&& v != p) {
						updateSheet({
							accountId : accountId,
							sheetId : currentSheetId,
							title : (inputSheetTitle.val() != inputSheetTitle.attr('placeholder') ? inputSheetTitle.val() : ""),
							description : v,
							success : function(data) {
								
								updateSheetDetails(data);
								
								// update prev
								o.attr('prev', v);
							},
							error: function(error) {
								
							}
						});
					} else {
						// clear
						if(v == m) {
							updateSheet({
								accountId : accountId,
								sheetId : currentSheetId,
								title : (inputSheetTitle.val() != inputSheetTitle.attr('placeholder') ? inputSheetTitle.val() : ""),
								description : "",
								success : function(data) { 
									
									updateSheetDetails(data);
									
									o.attr('prev', ''); 
								},
								error: function(error) {
									
								}
							});
						}
					}
					
				} else {
					if(o.attr('prev') != undefined) {
						if(o.attr('prev') != "") {
							updateSheet({
								accountId : accountId,
								sheetId : currentSheetId,
								title : (inputSheetTitle.val() != inputSheetTitle.attr('placeholder') ? inputSheetTitle.val() : ""),
								description : "",
								success : function(data) { 
									
									updateSheetDetails(data);
									
									o.attr('prev', ''); 
								},
								error: function(error) {
									
								}
							});
						}
					}
				}
				
			})
			.unbind("keyup")
			.bind("keyup", function() {
				
				if($(this).val() != "") {
				
					$('.demo-page-description')
					.html(converter.makeHtml($(this).val()))
					//.html($.replaceEmailWithHTMLLinks($.replaceURLWithHTMLLinks($(this).val().replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>"), true)))
					.show();
				
				} else {
					
					$('.demo-page-description')
					.html("");
					
					if(inputSheetTitle.val() == "") {
						$('.demo-page-description').hide();
					}
				}
				
			});
			
			
			//console.log(sheetId);
			//console.log(getSheetDetails(sheetId).title + "_______" + getSheetDetails(sheetId).description);
			
			var sheetDetails = getSheetDetails(sheetId);
			
			
			
			// set sheet title
			if(sheetDetails.title != undefined) {
				inputSheetTitle
					.val(sheetDetails.title)
					.attr('prev', sheetDetails.title)
					.blur();
				
				$('.header-page-title')
				.show();
				
				$('#label_page_title')
				.text(sheetDetails.title);
				
				$('.demo-page-description').show();
				
			} else {
				
				$('.header-page-title')
				.hide();
				
				$('#label_page_title')
				.text("");
				
				if(textAreaSheetDescription.val() == "") {
					$('.demo-page-description').hide();
				}
				
			}
			
			// set sheet description
			if(sheetDetails.description != undefined) {
				
				textAreaSheetDescription
				.val(sheetDetails.description)
				.attr('prev', sheetDetails.description)
				.blur();
				
				$('.demo-page-description')
				.html(converter.makeHtml(sheetDetails.description))
				//.html($.replaceEmailWithHTMLLinks($.replaceURLWithHTMLLinks(sheetDetails.description.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>"), true)))
				.show();
				
			} else {
				
				$('.demo-page-description')
				.html("");
				
				if(inputSheetTitle.val() == "") {
					$('.demo-page-description').hide();
				}
				
				
			}
		
		};
		
		var refreshReorder = function() {

			var l = $('ul.questions').find('li.question');
			l.removeClass('disabled-sort-up disabled-sort-down');
			
			// refresh function
			if(l.length > 1) {
				l.first()
				 .addClass('disabled-sort-up');
				
				l.last()
				 .addClass('disabled-sort-down');
			} else {
				l.addClass('disabled-sort-up disabled-sort-down');
			}
			
		};
		
		// control types
		//var f = function() {

			
			
			/*
			$('.button-add-question').click(function(e) {
				
				alert("OK");
				
				e.preventDefault();
			});
			*/
			
			/*
			for ( var i = 0; i < controlTypes.length; i++) {
				// <b class=\"icon-question-type\">&nbsp;</b>
				var C = $("<li class=\"control\" controltypeid=\"" + controlTypes[i].controlType + "\" title=\"Drag or click to add\">" + controlTypes[i].title + "</li>");
				$("ul.placeholder-controls").append(C);
			}

			
			$("ul.placeholder-controls li.control").mousedown(function(A, B) {
				A.preventDefault()
			});
			$("ul.placeholder-controls li.control").click(function () {
				c(false, $(this), true);
                return false
            });
			
			//$("ul.placeholder-controls li.control").draggable();
			$("ul.placeholder-controls li.control").draggable({
				opacity : 0.7,
				delay : 150,
				cursor : "move",
				zIndex : 1000,
				cursorAt : {
					left : 12,
					top : 12
				},
				helper : function(r) {
					if ($(this).hasClass("disabled")) {
						return false;
					}
					return $(this).clone().removeClass("top bottom mid single").addClass("single");
				},
				connectToSortable : "#placeholder_questions ul.questions",
				start : function() {
					$("div.question-visual-container").removeClass("question-visual-container").addClass("question-hidden-container");
					$("div.question-edit-mode:visible").hide();
                    $("div.question-view-mode:hidden").show();
				}
			});
			
			*/
			
		//};
		
		// control order
		var j = function(t) {
			
			
			/*
            var s = false,
                r;
            t = parseInt(controls.startIndex);
            s = t;
            */
			
            /*
            $("ul.questions li.question").each(function (i, el) {
                r = $(this).find(".number");
                r.each(function () {
                    //$(this).text((s < 10) ? "0" + s : s);
                    $(this).text(s);
                    $(el).attr({'orderid':(s < 10) ? (s - 1) : (s - 1)});
                });
                if (r.length > 0 && s !== false) {
                    s++;
                }
            });
            */
            
			
			// set orderid
            $('ul.questions').each(function(i, el) {
				var c = $(el);
				c.children('li.question').each(function(y, child) {
					$(child).attr({'orderid': y});
				});
			});
            
            // demo
            $('ul.list-controls').each(function(i, el) {
            	var c = $(el);
            	c.children('li.demo-question').each(function(y, child) {
					$(child).attr({'orderid': y});
				});
            });
            
            /*
            // set numbering
            $('li.question .number').each(function(i, el) {
				$(el).text(controls.startNumerator + i);
			});
            */
            
            $('li.question .label-control-number').each(function(i, el) {
				$(el).html(controls.startNumerator + i + ".&nbsp;");
			});
			
			$('li.question .label-edit-control-number').each(function(i, el) {
				$(el).html(controls.startNumerator + i + ".&nbsp;");
			});
            
            $('li.demo-question .label-control-number').each(function(i, el) {
				$(el).html(controls.startNumerator + i + ".&nbsp;");
			});
            
            
        };

        function sortDemoView(){
 		   
 		   var myList = $('ul.list-controls'); //s.find('ul.container-control-includes');
 		   var listItems = myList.children('li.demo-question').get();
 		   listItems.sort(function(a,b){			
 			   
 			   var compA = parseInt($(a).attr("new_index"));
 			   var compB = parseInt($(b).attr("new_index"));
 			   
 			   return ((compA ? compA : 0) - (compB ? compB : 0));

 		   });
 		   
 		   $(myList).append(listItems);
 		   
 		   
 		  j();
 		   
 	   };
 	   
 	   var scrollTop = function () {
           if (this.parentNode != null && this.parentNode.scrollTop > 0) {
               this.parentNode.scrollTop = 0;
           } else {
               if (this.parentNode != null && this.parentNode.parentNode != null && this.parentNode.parentNode.scrollTop > 0) {
                   this.parentNode.parentNode.scrollTop = 0
               }
           }
       };
        
 	   var refreshQuestionsSorting = function() {
			
			$("ul.questions").sortable({
				placeholder : "controls-list-drag",
				handle: '.tab-move-control', // ".tab-move-control",
				opacity : 0.7,
				axis: "y",
				cursor : "pointer",
				scroll : true,
				update : function(t, u) {
				
					/*
					if ($(u.item).hasClass("question")) {
						var s = 0, r = $(u.item).next();
					    while (r.length > 0) {
					    	
					        if (parseInt(r.find(".number:first").text()) > 0) {
					            s = parseInt(r.find(".number:first").text());
					            break
					        }
					        
					        r = r.next()
					    }
					    j(s);
					    
					}
					*/
			
				},
				stop : function(event, ui) {
					
					// ordering
					$("ul.questions li.question").each(function(i, el) {
     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
     			   	});
					
					
					refreshReorder();
					
					sortDemoView();
					
					//console.log("reorder --> " + $(ui.item).attr('controlid') + "________" + $(ui.item).attr('orderid') + "______" + currentSheetId);
					
					reorderControl({
						accountId : accountId,
						controlId: $(ui.item).attr('controlid'),
						sheetId : currentSheetId,
						orderId : $(ui.item).attr('orderid')
					});
					
					
					
					
					// set reordering
					// -> $(ui.item).attr('controlid');
					// -> $(ui.item).attr('orderid');
					
				}
			});
			
			
			refreshReorder();
			
		};
		
		// updated
		var getControlsA = function(sheetId, index, callback) {

			lastEditableControl = null; // global variable
			
			var retrieve = function() {
				
				if(callback != undefined 
						&& typeof callback == 'function')
					callback();
				
				
				// hide empty
				$("#empty").hide();
				$("ul.questions").empty();
				
				// clear demo
				$('.list-controls').empty();
				
				if (controls.list.length != 0) {
					
					$('#questions').show();
					
					for (var i = 0; i < controls.list.length; i++) {
						if (controls.list[i].parentId == sheetId) {
							
							
							//console.log("control...." + i);
							
							$("ul.questions").append("<li/>");
							
							var v;
							v = $("ul.questions > li:last");
							
							v.addClass("question");
							v.attr({'controlid' : controls.list[i].controlId, 'controltypeid' : controls.list[i].controlTypeId});
							v.Acontrol({
								opinionId : options.opinionId,
								controlId : controls.list[i].controlId,
								controlTypeId : controls.list[i].controlTypeId,
								inputTypeId : controls.list[i].inputTypeId,
								control : controls.list[i],
								isEditable : false,
								onSave : function(o, w) {
									
									alert("SAVE -->>  pipe");
									
									//l(o, w);
								},
								onCopyControls : function(_controls, _newOrderId) {
									_copyControls(_controls, _newOrderId);
								},
								onCopy : function(o, toSheetId, _newOrderId) {
									_copyControl(o, (toSheetId != undefined ? toSheetId : currentSheetId), _newOrderId);
								},
								onDelete : function(o) {
									
									if($("ul.questions li.question").length == 0) {
				    					$("#empty").show();
				    					$("#questions").hide();
				    				}
				    				
				    				j();
				    				refreshQuestionsSorting();
				    	            
								},
								onCreate : function(params) {
									recreateControl(params);
								},
								onInsert : function(_orderId, _controlTypeId, _inputTypeId) {
									insertControl(_orderId, _controlTypeId, _inputTypeId);
								},
								onReorder : function(_el) {
									// reordering
									$("ul.questions li.question").each(function(i, el) {
				     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
				     			   	});
					
									refreshReorder();
														
									sortDemoView();
									
									reorderControl({
										accountId : accountId,
										controlId: _el.attr('controlid'),
										sheetId : currentSheetId,
										orderId : _el.attr('orderid')
									});
									
								}
							});
							
						}
					}
					
					j();
					refreshQuestionsSorting();
					
					
				} else {
					
					//console.log("No controls");
					
				}
				
				resetEditor();
				
			};
			
			var get = function() {
				
				getControlsBySheet({
					accountId : accountId,
					sheetId : sheetId,
					success : function(data) {
						
						survey.controls.list = [];
						
						for(var i = 0; i < data.list.length; i++) {
							survey.controls.list.push(data.list[i]);
						}
						
						retrieve();
						
					},
					error: function() {
						
						survey.controls.list = [];
						
						retrieve();
						
					}
				});
				
			};
			
			if(index > 0) {
				
				getStartIndexOfSheet({
					accountId : accountId,
					sheetId : sheetId,
					success : function(data) {
						
						survey.controls.startIndex = data.startIndex;
						survey.controls.startNumerator = data.startNumerator;
						
						get();
						
					},
					error: function() {
						
					}
				});
				
			} else {
				
				survey.controls.startIndex = 1;
				survey.controls.startNumerator = 1;
				
				get();
				
			}
			
		
		};
		
		// copy controls 
		var _copyControls = function(controls, newOrderId) {
			
			controlCopyTo({
				accountId : accountId,
				opinionId : options.opinionId,
				/*controlId : controls[f],*/
				list : controls,
				sheetId : currentSheetId,
				parentId : currentSheetId,
				translationId : 0,
				orderId : (newOrderId != undefined ? newOrderId : undefined),
				success : function(data) {
					
					if(data.list.length != 0) {
						
						for(var i = 0; i < data.list.length; i++) {
							
							
		    				$("#empty").hide();
		    				$("#questions").show();
		    				
							
							
							//$("ul.questions").append("<li/>");
							
							
							/*
							var v;
							v = $("ul.questions > li:last");
							v.addClass("question");
							*/
							
							
							var v = $("<li class=\"question\"></li>");
						
						
							//console.log("edit -> orderid -> " + newOrderId);
						
							if(newOrderId != undefined) {
								if(newOrderId == 0) {
									$("ul.questions li.question")
				    				.first()
				    				.before(v);
								} else {
									var found = false;
									$("ul.questions li.question").each(function (i, el) {
										var current = $(el);
										if(current.attr("orderid") == newOrderId) {	
											found = true;
											// before
											current
												.before(v);
										}
									});
									if(!found) {
										$("ul.questions li.question:last").after(v);
					        		}
								}
							} else {
								//v.insertAfter(o);
								$("ul.questions").append(v);
							}
							
							
							
							v.attr({ 'controlid' : data.list[i].controlId, 'controltypeid' : data.list[i].controlTypeId });
							v.Acontrol({
								opinionId : options.opinionId,
								currentSheetId : currentSheetId,
								controlId : data.list[i].controlId,
								controlTypeId : data.list[i].controlTypeId,
								inputTypeId : data.list[i].inputTypeId,
								control : data.list[i],
								demoOrderId : (newOrderId != undefined ? newOrderId + i : undefined),
								isEditable : false,
								onSave : function(o, w) {
								
									alert("SAVE -> pipe");
								
								},
								onCopyControls : function(_controls, _newOrderId) {
									_copyControls(_controls, _newOrderId);
								},
								onCopy : function(o, toSheetId, _newOrderId) {
									_copyControl(o, (toSheetId != undefined ? toSheetId : currentSheetId), _newOrderId);
								},
								onDelete : function() {
								
				    				if($("ul.questions li.question").length == 0) {
				    					$("#empty").show();
				    					$("#questions").hide();
				    				}
			    				
				    				j();
				    				refreshQuestionsSorting();
			    	            
								},
								onCreate : function(params) {
									recreateControl(params);
								},
								onInsert : function(_orderId, _controlTypeId, _inputTypeId) {
									insertControl(_orderId, _controlTypeId, _inputTypeId);
								},
								onReorder : function(_el) {
									// reordering
									$("ul.questions li.question").each(function(i, el) {
				     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
				     			   	});
								
									refreshReorder();
				
									sortDemoView();
								
									reorderControl({
										accountId : accountId,
										controlId: _el.attr('controlid'),
										sheetId : currentSheetId,
										orderId : _el.attr('orderid')
									});
								
								}
							});
							
							
							
							// clear newOrderId after first loop
							newOrderId = undefined;
						
							
							
						}
						
						
						// for counting
						j();
						refreshQuestionsSorting();

						
						
					}
					
				},
				error: function() {
					//console.log("copy controls -> to error...");
				}
			});
			
		};
		
		var _copyControl = function(o, toSheetId, newOrderId) {
			
			controlCopyTo({
				accountId : accountId,
				opinionId : options.opinionId,
				controlId : o.attr('controlid'),
				sheetId : (toSheetId == currentSheetId ? toSheetId : undefined),
				parentId : (toSheetId != currentSheetId ? toSheetId : undefined),
				translationId : 0,
				orderId : (newOrderId != undefined ? newOrderId : (parseInt(o.attr('orderid')) + 1)),
				success : function(data) {
					
					if(toSheetId == currentSheetId) {
						
						
						var v = $("<li class=\"question\"></li>");
						
						if(newOrderId != undefined) {
							if(newOrderId == 0) {
								$("ul.questions li.question")
			    				.first()
			    				.before(v);
							} else {
								var found = false;
								$("ul.questions li.question").each(function (i, el) {
								
									var current = $(el);
									if(current.attr("orderid") == newOrderId) {
										
										found = true;
										
										// before
										current
											.before(v);
							            
									}
								});
								
								if(!found) {
									$("ul.questions li.question:last").after(v);
				        		}
								
							}
						} else {
							v.insertAfter(o);
						}
						
						
						v.attr({ 'controlid' : data.controlId, 'controltypeid' : data.controlTypeId });
						v.Acontrol({
							opinionId : options.opinionId,
							currentSheetId : currentSheetId,
							controlId : data.controlId,
							controlTypeId : data.controlTypeId,
							inputTypeId : data.inputTypeId,
							control : data,
							demoOrderId : (newOrderId != undefined ? newOrderId : (parseInt(o.attr('orderid')) + 1)), // parseInt(o.attr('orderid'))// (newOrderId != undefined ? newOrderId : (parseInt(o.attr('orderid')) + 1))
							isEditable : false,
							onSave : function(o, w) {
								
								alert("SAVE -> pipe");
								
							},
							onCopyControls : function(_controls, _newOrderId) {
								_copyControls(_controls, _newOrderId);
							},
							onCopy : function(o, toSheetId, _newOrderId) {
								_copyControl(o, (toSheetId != undefined ? toSheetId : currentSheetId), _newOrderId);
							},
							onDelete : function() {
								
			    				if($("ul.questions li.question").length == 0) {
			    					$("#empty").show();
			    					$("#questions").hide();
			    				}
			    				
			    				j();
			    				refreshQuestionsSorting();
			    	            
							},
							onCreate : function(params) {
								recreateControl(params);
							},
							onInsert : function(_orderId, _controlTypeId, _inputTypeId) {
								insertControl(_orderId, _controlTypeId, _inputTypeId);
							},
							onReorder : function(_el) {
								// reordering
								$("ul.questions li.question").each(function(i, el) {
			     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
			     			   	});
								
								refreshReorder();
				
								sortDemoView();
								
								reorderControl({
									accountId : accountId,
									controlId: _el.attr('controlid'),
									sheetId : currentSheetId,
									orderId : _el.attr('orderid')
								});
								
							}
						});
						
						// for counting
						j();
						refreshQuestionsSorting();
					
					}
					
				},
				error: function(error) {
					//console.log(JSON.stringify(error));
				}
			});
			
		};
		
		var recreateControl = function(params) {
		
			createControl({
				accountId : accountId,
				opinionId : options.opinionId,
				orderId : params.control.orderId,
				parentId : currentSheetId, // check
				parentTypeId : 2, // 1 poll, sheet 2, control 3
				controlTypeId : params.control.controlTypeId,
				inputTypeId : params.control.inputTypeId,
				inputSizeTypeId : 1,
				content : params.control.content,
				isMandatory : params.control.isMandatory,
				note : params.control.note,
				linkTypeId : params.control.linkTypeId,
				link : params.control.link,
				options: params.control.options,
				subControls: params.control.subControls,
				isEnableComment : params.control.isEnableComment,
				comment : params.control.comment,
				fromScale : params.control.fromScale,
				fromTitle : params.control.fromTitle,
				toScale : params.control.toScale,
				toTitle : params.control.toTitle,
				monthTitle : params.control.monthTitle,
				dayTitle : params.control.dayTitle,
				yearTitle : params.control.yearTitle,
				hoursTitle : params.control.hoursTitle,
				minutesTitle : params.control.minutesTitle,
				success : function(data) {
					
					if(params.success != undefined 
						&& typeof params.success == 'function') {
							params.success(data);
						}
					
				},
				error: function(error) {
					
					if(params.error != undefined 
						&& typeof params.error == 'function') {
							params.error(error);
						}
					
				}
			});
			
		};
		
		var insertControl = function(newOrderId, newControlTypeId, newInputTypeId) {
			
			createControl({
				accountId : accountId,
				opinionId : options.opinionId,
				orderId : parseInt(newOrderId) + 1,
				parentId : currentSheetId,
				parentTypeId : 2, // 1 poll, sheet 2, control 3
				controlTypeId : (newControlTypeId != undefined ? newControlTypeId : 2),
				inputTypeId : (newInputTypeId != undefined ? newInputTypeId : 0),
				inputSizeTypeId : 1,
				content : (newControlTypeId == 11 ? "" : (newControlTypeId == 12 ? "Heading" : "Enter your question here")),
				isMandatory : false,
				options : ((newControlTypeId == 2 || newControlTypeId == 4) ? getDefaultOptions(options.opinionId, newControlTypeId) : undefined),
				subControls : (newControlTypeId == 4 ? getDefaultSubControls(options.opinionId) : undefined),
				fromScale : (newControlTypeId == 10 ? 1 : undefined),
				toScale : (newControlTypeId == 10 ? 0 : undefined),
				monthTitle : (newControlTypeId == 6 ? messages.month : undefined),
				dayTitle : (newControlTypeId == 6 ? messages.day : undefined),
				success : function(data) {
					
					var v = $("<li class=\"question\"></li>");
					
					$("ul.questions li.question").each(function (i, el) {
						var current = $(el);
						if(current.attr("orderid") == newOrderId) {
							// after
							current
								.after(v);
				            
						}
					});
					
					v.attr({ 'controlid' : data.controlId, 'controltypeid' : data.controlTypeId });
					v.Acontrol({
						opinionId : options.opinionId,
						currentSheetId : currentSheetId,
						controlId : data.controlId,
						controlTypeId : data.controlTypeId,
						inputTypeId : data.inputTypeId,
						control : data,
						demoOrderId : parseInt(newOrderId) + 1,
						isEditable : true,
						onSave : function(o, w) {
							
							alert("SAVE -> pipe");
							//l(o, w);
						},
						onCopyControls : function(_controls, _newOrderId) {
							_copyControls(_controls, _newOrderId);
						},
						onCopy : function(o, toSheetId, _newOrderId) {
							_copyControl(o, (toSheetId != undefined ? toSheetId : currentSheetId), _newOrderId);
						},
						onDelete : function() {
							
		    				if($("ul.questions li.question").length == 0) {
		    					$("#empty").show();
		    					$("#questions").hide();
		    				}
		    				
		    				j();
		    				refreshQuestionsSorting();
		    	            
						},
						onCreate : function(params) {
							recreateControl(params);
						},
						onInsert : function(_orderId, _controlTypeId, _inputTypeId) {
							insertControl(_orderId, _controlTypeId, _inputTypeId);
						},
						onReorder : function(_el) {
							// reordering
							$("ul.questions li.question").each(function(i, el) {
		     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
		     			   	});
							
							refreshReorder();
			
							sortDemoView();
							
							reorderControl({
								accountId : accountId,
								controlId: _el.attr('controlid'),
								sheetId : currentSheetId,
								orderId : _el.attr('orderid')
							});
							
						}
					});
					
					// for counting
					j();
					refreshQuestionsSorting();
					
				},
				error: function(error) {
					alert("ERR");
				}
			});
			
		};
		
		
		// insert question
		$('#questions')
		.find('.button-insert-question')
		.splitButton({
			label : "Insert question",
			icon : "add",
			click : function() {
				
				createControl({
					accountId : accountId,
					opinionId : options.opinionId,
					orderId : 0,
					parentId : currentSheetId,
					parentTypeId : 2, // 1 poll, sheet 2, control 3
					controlTypeId : 2, // multiple choice
					inputTypeId : 0, // radio
					inputSizeTypeId : 1,
					content : "Enter your question here",
					isMandatory : false,
					options : getDefaultOptions(options.opinionId, 2),
					success : function(data) {
					
						var v = $("<li class=\"question\"></li>");
					
						// before
						$("ul.questions li.question")
							.first()
							.before(v);
					
						v.attr({ 'controlid' : data.controlId, 'controltypeid' : data.controlTypeId });
						v.Acontrol({
							opinionId : options.opinionId,
							currentSheetId : currentSheetId,
							controlId : data.controlId,
							controlTypeId : data.controlTypeId,
							inputTypeId : data.inputTypeId,
							control : data,
							demoOrderId : 0, //-1,
							isEditable : true,
							onSave : function(o, w) {
							
								alert("SAVE -> pipe");

							},
							onCopyControls : function(_controls, _newOrderId) {
								_copyControls(_controls, _newOrderId);
							},
							onCopy : function(o, toSheetId, _newOrderId) {
								_copyControl(o, (toSheetId != undefined ? toSheetId : currentSheetId), _newOrderId);
							},
							onDelete : function(o) {
							
								if($("ul.questions li.question").length == 0) {
			    					$("#empty").show();
			    					$("#questions").hide();
			    				}
		    				
			    				j();
			    				refreshQuestionsSorting();
		    	            
							},
							onCreate : function(params) {
								recreateControl(params);
							},
							onInsert : function(_orderId, _controlTypeId, _inputTypeId) {
								insertControl(_orderId, _controlTypeId, _inputTypeId);
							},
							onReorder : function(_el) {
								// reordering
								$("ul.questions li.question").each(function(i, el) {
			     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
			     			   	});
							
								refreshReorder();
			
								sortDemoView();
								
								reorderControl({
									accountId : accountId,
									controlId: _el.attr('controlid'),
									sheetId : currentSheetId,
									orderId : _el.attr('orderid')
								});
								
								
							}
						});
					
						// for counting
						j();
						refreshQuestionsSorting();
					
					},
					error: function() {
						alert("ERR");
					}
				});
				
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
										_copyControls(obj.controls, 0);
									});
									
								} else {
									
									
									var __controlTypeId = parseInt($(this).attr("value"));
									var __inputTypeId = parseInt($(this).attr("input_type_id"));
									
									
									createControl({
										accountId : accountId,
										opinionId : options.opinionId,
										orderId : 0,
										parentId : currentSheetId,
										parentTypeId : 2, // 1 poll, sheet 2, control 3
										controlTypeId : __controlTypeId,
										inputTypeId : __inputTypeId,
										inputSizeTypeId : 1,
										content : (__controlTypeId == 11 ? "" : (__controlTypeId == 12 ? "Heading" : "Enter your question here")),
										isMandatory : false,
										options : ((__controlTypeId == 2 || __controlTypeId == 4) ? getDefaultOptions(options.opinionId, __controlTypeId) : undefined),
										subControls : (__controlTypeId == 4 ? getDefaultSubControls(options.opinionId) : undefined),
										fromScale : (__controlTypeId == 10 ? 1 : undefined),
										toScale : (__controlTypeId == 10 ? 0 : undefined),
										monthTitle : (__controlTypeId == 6 ? messages.month : undefined),
										dayTitle : (__controlTypeId == 6 ? messages.day : undefined),
										success : function(data) {
									
											var v = $("<li class=\"question\"></li>");
									
											// before
											$("ul.questions li.question")
												.first()
												.before(v);
									
											v.attr({ 'controlid' : data.controlId, 'controltypeid' : data.controlTypeId });
											v.Acontrol({
												opinionId : options.opinionId,
												currentSheetId : currentSheetId,
												controlId : data.controlId,
												controlTypeId : data.controlTypeId,
												inputTypeId : data.inputTypeId,
												control : data,
												demoOrderId : 0, // -1
												isEditable : true,
												onSave : function(o, w) {
											
													alert("SAVE -> pipe");
													//l(o, w);
												},
												onCopyControls : function(_controls, _newOrderId) {
													_copyControls(_controls, _newOrderId);
												},
												onCopy : function(o, toSheetId, _newOrderId) {
													_copyControl(o, (toSheetId != undefined ? toSheetId : currentSheetId), _newOrderId);
												},
												onDelete : function(o) {
											
													if($("ul.questions li.question").length == 0) {
								    					$("#empty").show();
								    					$("#questions").hide();
								    				}
						    				
								    				j();
								    				refreshQuestionsSorting();
						    	            
												},
												onCreate : function(params) {
													recreateControl(params);
												},
												onInsert : function(_orderId, _controlTypeId, _inputTypeId) {
													insertControl(_orderId, _controlTypeId, _inputTypeId);
												},
												onReorder : function(_el) {
													// reordering
													$("ul.questions li.question").each(function(i, el) {
								     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
								     			   	});
											
													refreshReorder();
							
													sortDemoView();
												
													reorderControl({
														accountId : accountId,
														controlId: _el.attr('controlid'),
														sheetId : currentSheetId,
														orderId : _el.attr('orderid')
													});
												
												
												}
											});
									
											// for counting
											j();
											refreshQuestionsSorting();
									
										},
										error: function() {
											alert("ERR");
										}
									});
									
								}
								
								lastCombo.close();
								
								e.preventDefault();
								
							});
				
				return M;
				
			}
		});
		
		// split button
		$('#empty')
		.find('.button-add-question')
		.splitButton({
			label : "Add question",
			icon : "add",
			click : function() {
			
				$('#empty').hide();
				$('#questions').show();
				
				
				// create default control
				createControl({
					accountId : accountId,
					opinionId : options.opinionId,
					/*orderId : 0,*/
					parentId : currentSheetId,
					parentTypeId : 2, // 1 poll, sheet 2, control 3
					controlTypeId : 2, // multiple choice
					inputTypeId : 0, // radio
					inputSizeTypeId : 1,
					content : "Enter your question here",
					isMandatory : false,
					options : getDefaultOptions(options.opinionId, 2), // default options for multiple choice only
					success : function(data) {
					
						$("ul.questions").append("<li/>");
					
						var v;
						v = $("ul.questions > li:last");
						v.addClass("question");
					
						v.attr({ 'controlid' : data.controlId, 'controltypeid' : data.controlTypeId });
						v.Acontrol({
							opinionId : options.opinionId,
							currentSheetId : currentSheetId,
							controlId : data.controlId,
							controlTypeId : data.controlTypeId,
							inputTypeId : data.inputTypeId,
							control : data,
							isEditable : true,
							onSave : function(o, w) {
								alert("SAVE -> pipe");
								//l(o, w);
							},
							onCopyControls : function(_controls, _newOrderId) {
								_copyControls(_controls, _newOrderId);
							},
							onCopy : function(o, toSheetId, _newOrderId) {
								_copyControl(o, (toSheetId != undefined ? toSheetId : currentSheetId), _newOrderId);
							},
							onDelete : function(o) {
							
								if($("ul.questions li.question").length == 0) {
			    					$("#empty").show();
			    					$("#questions").hide();
			    				}
		    				
			    				j();
			    				refreshQuestionsSorting();
							
							},
							onCreate : function(params) {
								recreateControl(params);
							},
							onInsert : function(_orderId, _controlTypeId, _inputTypeId) {
								insertControl(_orderId, _controlTypeId, _inputTypeId);
							},
							onReorder : function(_el) {
								// reordering
								$("ul.questions li.question").each(function(i, el) {
			     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
			     			   	});
							
								refreshReorder();
			
								sortDemoView();
											
								reorderControl({
									accountId : accountId,
									controlId: _el.attr('controlid'),
									sheetId : currentSheetId,
									orderId : _el.attr('orderid')
								});
								
							}
						});
					
						// for counting
						j();
						refreshQuestionsSorting();
					
					
					},
					error: function(error) {
						alert("ERR");
					}
				});
				
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
										_copyControls(obj.controls);
									});
									
								} else {	
									
									$('#empty').hide();
									$('#questions').show();
									
									
									
									var __controlTypeId = parseInt($(this).attr("value"));
									var __inputTypeId = parseInt($(this).attr("input_type_id"));
									
									
									createControl({
										accountId : accountId,
										opinionId : options.opinionId,
										/*orderId : 0,*/
										parentId : currentSheetId,
										parentTypeId : 2, // 1 poll, sheet 2, control 3
										controlTypeId : __controlTypeId,
										inputTypeId : __inputTypeId,
										inputSizeTypeId : 1,
										content : (__controlTypeId == 11 ? "" : (__controlTypeId == 12 ? "Heading" : "Enter your question here")),
										isMandatory : false,
										options : ((__controlTypeId == 2 || __controlTypeId == 4) ? getDefaultOptions(options.opinionId, __controlTypeId) : undefined),
										subControls : (__controlTypeId == 4 ? getDefaultSubControls(options.opinionId) : undefined),
										fromScale : (__controlTypeId == 10 ? 1 : undefined),
										toScale : (__controlTypeId == 10 ? 0 : undefined),
										monthTitle : (__controlTypeId == 6 ? messages.month : undefined),
										dayTitle : (__controlTypeId == 6 ? messages.day : undefined),
										success : function(data) {
									
											$("ul.questions").append("<li/>");
									
											var v;
											v = $("ul.questions > li:last");
											v.addClass("question");
									
											v.attr({ 'controlid' : data.controlId, 'controltypeid' : data.controlTypeId });
											v.Acontrol({
												opinionId : options.opinionId,
												currentSheetId : currentSheetId,
												controlId : data.controlId,
												controlTypeId : data.controlTypeId,
												inputTypeId : data.inputTypeId,
												control : data,
												isEditable : true,
												onSave : function(o, w) {
													alert("SAVE -> pipe");
													//l(o, w);
												},
												onCopyControls : function(_controls, _newOrderId) {
													_copyControls(_controls, _newOrderId);
												},
												onCopy : function(o, toSheetId, _newOrderId) {
													_copyControl(o, (toSheetId != undefined ? toSheetId : currentSheetId), _newOrderId);
												},
												onDelete : function(o) {
											
													if($("ul.questions li.question").length == 0) {
								    					$("#empty").show();
								    					$("#questions").hide();
								    				}
						    				
								    				j();
								    				refreshQuestionsSorting();
											
												},
												onCreate : function(params) {
													recreateControl(params);
												},
												onInsert : function(_orderId, _controlTypeId, _inputTypeId) {
													insertControl(_orderId, _controlTypeId, _inputTypeId);
												},
												onReorder : function(_el) {
													// reordering
													$("ul.questions li.question").each(function(i, el) {
								     				   $("ul.list-controls").find("li.demo-question[orderid=" + $(el).attr("orderid") + "]").attr("new_index", i);
								     			   	});
											
													refreshReorder();
							
													sortDemoView();
																			
													reorderControl({
														accountId : accountId,
														controlId: _el.attr('controlid'),
														sheetId : currentSheetId,
														orderId : _el.attr('orderid')
													});
												
												}
											});
									
											// for counting
											j();
											refreshQuestionsSorting();
									
									
										},
										error: function(error) {
											alert("ERR");
										}
									});
									
								}
								
								
								lastCombo.close();
								
								e.preventDefault();
								
							});
				
				return M;
				
			}
		});
		
		
		
		
		// set from settings
		
		// next 
		$('.button-next-page')
		.attr("title", options.data.messages.next)
		.find("span")
		.text(options.data.messages.next);
		
		// prev
		$('.button-previous-page')
		.attr("title", options.data.messages.back)
		.find("span")
		.text(options.data.messages.back);
		
		// finish
		$('.button-finish-survey')
		.attr("title", options.data.messages.finish)
		.find("span")
		.text(options.data.messages.finish);

		
		useQuestionNumbering = options.data.useQuestionNumbering;
		
		
		
		// reset editor
		var resetEditor = function() {
			
			if ($("ul.questions li.question").length == 0) {
				$("#empty").show();
				$('#questions').hide();
			}
			
			// demo scrollTop
			$('.block-preview').scrollTop(0);
			
		};

		refreshQuestionsSorting();
		
		
		getSheets({
			accountId : accountId,
			opinionId : options.opinionId,
			success : function(data) {
				
					for(var i = 0; i < data.list.length; i++) {
					
						var tab = $("<li class=\"ui-state-default\" data-value=\"" + data.list[i].sheetId + "\"><span class=\"title\">Page " + (i + 1) + "</span><span class=\"action\"></span></li>");
						
						tab.appendTo("#sortable");
						
						sheets.push(data.list[i]);
						
					}
					
					$("#sortable").sortable({
						cursor : "move",
						axis: "x",
						update : function(event, ui) {
							
						},
						stop : function(event, ui) {
							
							var ids = [];
							$('#sortable li').each(function(i, el) {
								ids.push(parseInt($(el).attr("data-value")));
							});
							
							//console.log(ids);
							
							reorderSheets({
								accountId : accountId,
								sheetIds : ids,
								success : function() {
									refreshSheets();
								},
								error: function() {
									
								}
							});
							
							// trigger tab click
							ui.item.trigger("click");
							
						}
					});
					$("#sortable").disableSelection();
					
					
					t = $('.sortable-section').outerWidth() - $('#sortable').width();
					refreshActions();
					
					
					//selectSheet(sheets.lastSelected != 0 ? (sheets.lastSelected - 1) : 0);
					selectSheet(0);
					
				
			},
			error: function(error) {
				
				//console.log("must create sheet");
				//console.log(JSON.stringify(error));
				
				
				$('.add-page').trigger("click");
				
			}
		});
		
		
		
		
		/*
		// collapse / expand
		
		_prevLeft = framePreview.position().left;
		_prevWidth = framePreview.width();

	    $('.frame-collapse').click(function() {
	    	
	    	var collapse = $(this);
	    	
	    	
	    	
	    	//console.log("collapse" + _prevLeft + "________" + _prevWidth);
	    	
	    	//prevLeft = framePreview.offset().left;
	    	//prevWidth = framePreview.width();
	    	
	    	
	    	//console.log("collapse -> " + prevLeft);
	    	
	    	//framePreview.css({ 'left' : ((framePreview.offset().left + framePreview.width()) - 460), "width" : 450  },100);
	    	framePreview.css({ 'left' : (_prevLeft + _prevWidth) - 450, "width" : 450  }).addClass("collapsed");
	    	blockPreview.width(450 - 2);
	    	
	    	
	    	//frameEdit.css("width", ((framePreview.offset().left + framePreview.width()) - 480) - 10);
	    	frameEdit.css("width", (_prevWidth));
	    	
	    	
	    	
	    	
	    	// update tabs
	    	t = $('.sortable-section').outerWidth() - $('#sortable').width(); // j.width()-b
	    	
			collapse.hide();
			
			
			
			$('.frame-expand')
			.show()
			.click(function() {
				var expand = $(this);
				
				//console.log("expand -> " + prevLeft);
				
				
				
				//console.log("expand" + _prevLeft + "________" + _prevWidth);
				
				
				
				//framePreview.css({ 'left' : prevLeft - 10, "width" : prevWidth  });
				
				framePreview.css({ 'left' : _prevLeft, "width" : _prevWidth  }).removeClass("collapsed");
				blockPreview.width(_prevWidth - 2);
				
				
				
				frameEdit.css("width", 450);
				
				
				
				//prevOffsetLeft = framePreview.offset().left;
				//prevPositionLeft = framePreview.position().left;
				
				
				//console.log("prev position left" + prevPositionLeft);
				
				
				// update tabs
		    	t = $('.sortable-section').outerWidth() - $('#sortable').width(); // j.width()-b
		    	
				
				expand.hide();
				collapse.show();
			});
	    	
	    	
	    });
	    */
	    
			
			
		var frameEdit = $('.frame-edit');
	    var framePreview = $(".frame-preview");
	    var blockPreview = $(".block-preview");
		
		
		
		var offsetLeft = framePreview.offset().left;
		
		
		/*
		// listen for collapsible events
		if(typeof collapsible != 'undefined') {
			
			// listen to event expand and collapse
			collapsible.onExpand = function() {
				
				if(expanded) {
		    		framePreview.css({
		    			'width' : (($(window).width() - 690) - 20)
		    		});
				    blockPreview.css({
			    		'width' : (($(window).width() - 690) - 20) - 2
			    	});
				} else {
		    		framePreview.css({
		    			'width' : (($(window).width() - 480) - 20)
		    		});
				    blockPreview.css({
			    		'width' : (($(window).width() - 480) - 20) - 2
			    	});
				}
				
			};
			
		}
		*/
	   
	    
	    
	    
	    
	    
	    // set width
	    framePreview.css({
	    	'width' : (($(window).width() - offsetLeft) - 20)
	    });
	    blockPreview.css({
    		'width' : (($(window).width() - offsetLeft) - 20) - 2
    	});
		
	    //blockPreview.height(($(window).height() - framePreview.offset().top) < 300 ? 300 : ($(window).height() - framePreview.offset().top) - 2);
	    
	    blockPreview.height(300);
	    
	    frameEdit.css({ "min-height" : ($(window).height() - framePreview.offset().top) < 300 ? 300 : ($(window).height() - framePreview.offset().top) });
	    
	    
	    
	    
	    
	    // sticky
	    var sidebarHeight = $('.sidebar').height();
		var e = $('.col-3').position().top;
		var h = $('.wrapper-footer').offset().top - 36;
		
		
		$('.frame-edit').resize(function() {
			h = $('.wrapper-footer').offset().top - 36;
		});
		
		scrollTop = $(window).scrollTop(), z = Math.max(0, e - scrollTop);
		
		z = Math.min(z, (h - scrollTop) - sidebarHeight);
		
		$('.sidebar').css({
			'top' : z/*,
			'height' : $(window).height() - z*/
		});
		
		$(window).resize(function() {
			
			/*
			if(typeof expanded != 'undefined') {
		    	framePreview.css({
		    		'width' : (($(window).width() - 690) - 20)
		    	});
			    blockPreview.css({
			   		'width' : (($(window).width() - 690) - 20) - 2
			   	});
			} else {
		    	framePreview.css({
		    		'width' : (($(window).width() - 480) - 20)
		    	});
			    blockPreview.css({
			   		'width' : (($(window).width() - 480) - 20) - 2
			   	});
			}
			*/
			
			scrollTop = $(window).scrollTop(), y = Math.max(0, e - scrollTop);
			
			//y = Math.min(y, (h - scrollTop) - sidebarHeight);
			
			$('.sidebar').css({
		        'top': y/*,
		        'height' : $(window).height() - y*/
			});
			
		}).scroll(function (a) {
			
			scrollTop = $(window).scrollTop(), i = Math.max(0, e - scrollTop); //, i = Math.min(i, ($(window).height() - scrollTop) - 80);
			
			i = Math.min(i, (h - scrollTop) - sidebarHeight);
			
			$('.sidebar').css({
		        'top': i/*,
		        'height' : $(window).height() - i*/
			});
			
		});
		
		
		
		
		// labelPlacement
		if(options.data.labelPlacement != undefined) {	
			if(options.data.labelPlacement != 0) {
				$(".demo-page").addClass("label-placement-" + options.data.labelPlacement);
			}
		}
		
		// isRtl
		if(options.data.isRtl != undefined) {
			if(options.data.isRtl) {
				$(".demo-body").addClass("oposite-direction");
			}
		}
		
	    
	    
		
	}
	
})(jQuery);