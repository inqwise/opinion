
var partial = 0;
var completed = 0;
var total = 0;

var response = {
	controls : {
		list : []
	}	
};

function makeGradientColor(color1, color2, percent) {
    var newColor = {};

    function makeChannel(a, b) {
        return(a + Math.round((b-a)*(percent/100)));
    }

    function makeColorPiece(num) {
        num = Math.min(num, 255);   // not more than 255
        num = Math.max(num, 0);     // not less than 0
        var str = num.toString(16);
        if (str.length < 2) {
            str = "0" + str;
        }
        return(str);
    }

    newColor.r = makeChannel(color1.r, color2.r);
    newColor.g = makeChannel(color1.g, color2.g);
    newColor.b = makeChannel(color1.b, color2.b);
    newColor.cssColor = "#" +
    makeColorPiece(newColor.r) +
    makeColorPiece(newColor.g) +
    makeColorPiece(newColor.b);
    return(newColor);
}

var blue = { r : 59, g : 89, b : 152 };
var lightGrey = { r : 235, g : 238, b : 244 };
/*
console.log("COLOR");
console.log(makeGradientColor(blue, lightGrey, 100).cssColor);
console.log(makeGradientColor(blue, lightGrey, 50).cssColor);
console.log(makeGradientColor(blue, lightGrey, 0).cssColor);

console.log(makeGradientColor(blue, lightGrey, (100 / 3) * 1).cssColor);
console.log(makeGradientColor(blue, lightGrey, (100 / 3) * 2).cssColor);
console.log(makeGradientColor(blue, lightGrey, (100 / 3) * 3).cssColor);
*/
function getColors(size) {
    var colors = [];
    for(var i = 0; i < size; i += 1) {
        colors.push(makeGradientColor(blue, lightGrey, (100 / size) * i).cssColor);
    }
    return colors;
}
/*
console.log("GET COLORS");
console.log(getColors(3));
*/
;(function(jQuery) {
	surveyOverallResults = function(givenOptions) {

		var options = $.extend({
			opinionId : null,
			callback : null
		}, givenOptions);
		
		var startIndex = 1;
		var startNumerator = 1;
		
		var animateBars = function () {
			$(".container-control-includes").find("div.bar").each(function (i, el) {
				var val = Math.max($(el).attr("data-value") * $(el).parents(".outer-bar").width() / 100, 2) + "px";
				//$(el).animate({ "width" : val });
				$(el).css({ "width" : val });
			});
		};
		
		var j = function() {
			
			// count questions
			$('li.result-control .label-control-number').each(function(i, el) {
				$(el).html(startNumerator + i + ".&nbsp;");
			});
			
		};
		
		var init = function() {
			
			var filterBySheet = function(sheetId) {
				
				var get = function() {
					
					getResponseResults({
						accountId : accountId,
						opinionId : options.opinionId,
						sheetId : sheetId,
						success : function(data) {
					
							partial = data.partial;
							completed = data.completed;
							total = partial + completed;
					
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
						
						
							// view
							// if sheetId -> show only this page -> else show all pages and questions
							// filter
							// export
						
						
							/*
							var optionNumber = 0;
							for (var i = 0; i < data.controls.list.length; i++) {
						
						
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
					
							/*
							// organize question
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
							
							
							
							// statistics
							$('#label_responses_started').text($.addCommas((data.completed + data.partial)));
							$('#label_responses_completed').text($.addCommas(data.completed));
							$('#label_responses_partial').text($.addCommas(data.partial));
					
							if((data.completed + data.partial) != 0) {
								// ((data.completed / (data.completed + data.partial)) * 100).toFixed(2) + "%";
								$('#label_responses_completion_rate').text(((data.completed / (data.completed + data.partial)) * 100).toFixed(2) + "%");
							} else {
								$('#label_responses_completion_rate').text("0.00%");
							}
							
							//$('#label_responses_average_time_taken').text();
						
						
							/*
		                	// callback
							if(options.callback != undefined 
									&& typeof options.callback == 'function')
								options.callback(data);
							*/	
					
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
			
			
		};
	
		init();
	}
})(jQuery);