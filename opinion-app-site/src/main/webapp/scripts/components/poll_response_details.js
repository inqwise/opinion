/*
 * Copyright (c) 2013 Inqwise
 *
 */

;(function(jQuery) {
	responseDetails = function(givenOptions) {
		
		var options = $.extend({
			opinionId : null,
			answererSessionId : null,
			absoluteUrl : null
		}, givenOptions);
		
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
				$(el).html((1 + i) + ".&nbsp;");
			});
			
		};
		
		var init = function() {
			
			getResponseDetails({
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
						        		   list : [response.id],
						        		   success : function() {
						        			   
						        			   modal.close();
						        			   location.href = options.absoluteUrl + "/polls/" + options.opinionId + "/responses";
						        			   
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
					
					// get response results
					getResponseResults({
						opinionId : options.opinionId,
						answererSessionId : options.answererSessionId,
						success : function(data) {
							
							// set 
							response.controls.list = data.controls.list;
							
							if(data.controls.list.length != 0) {
								for (var i = 0; i < data.controls.list.length; i++) {
									
									// controls
									$("#placeholder_results ul.list-result-controls").append("<li/>");
									var v;
									v = $("#placeholder_results ul.list-result-controls > li:last");
									v.addClass("result-control c" + data.controls.list[i].controlId);
									if(i%2) {
										v.addClass('highlight');
									}
									v.resultControl({
										controlId : data.controls.list[i].controlId,
										controlTypeId : data.controls.list[i].controlTypeId,
										controlIndex : i
									});
									v.attr({ "controlid" : data.controls.list[i].controlId, "controlindex" : i });
									
								}
								
								
								j();
								
								// animate
			                	//setTimeout(animateBars, 400);
								
								animateBars();
								
								/*
								$('.tag-statistic, .tag-statistic-line').each(function(i, el) {
									var data = $(el).html();
									var percentage = data.split(" ")[0].replace("%","");	// parsed percentage
									$(el).text(Math.round(parseInt(percentage)) + "%");
								});
								*/
								
								
								/*
								// show all answers
								$("#checkbox_show_all_answers").change(function() {
									if($(this).is(':checked')) {
										$('.hide-item').show();
									} else {
										$('.hide-item').hide();
									}
								});
								*/
								
							}
							
							// get related responses
							getResponses({
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
															location.href = options.absoluteUrl + "/polls/" + options.opinionId + "/responses/" + responses[d - 1].sessionId;
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
														.attr({ "href" : options.absoluteUrl + "/polls/" + options.opinionId + "/responses/" + responses[w - 1].sessionId });
												} else {
													$('.ui-navigation-panel-button-previous')
														.addClass('ui-button-disabled')
												}
												
												// next
												if(responses[w + 1] != undefined) {
													$('.ui-navigation-panel-button-next')
														.removeClass('ui-button-disabled')
														.attr({ "href" : options.absoluteUrl + "/polls/" + options.opinionId + "/responses/" + responses[w + 1].sessionId });
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
									
								}
							});
							
							
						},
						error: function(error) {
							//
						}
					});
					
					
				},
				error: function(error) {
					alert("ERR");
				}
			});
			
		};
		
		init();
	}
})(jQuery);