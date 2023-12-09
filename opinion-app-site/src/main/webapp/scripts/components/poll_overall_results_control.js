jQuery.fn.resultControl = function () {
    if (arguments.length == 0) return [];
    var I = arguments[0] || {};
    var v = $(this);
    
    var B = function (D) {
    	
    	var E = $("<div class=\"result-control-container\">" + 
     			"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
     				"<tr class=\"row-control-header\">" +
     					"<td>" +
     						"<b class=\"label-control-number\"></b><b class=\"label-control-mandatory\" style=\"display: none;\">*&nbsp;</b><b class=\"header-control-content\"></b>" + 
     					"</td>" +
     				"</tr>" +
     				"<tr>" +
     					"<td>" + 
		     				"<div class=\"container-control-includes\"></div>" +
     					"</td>" +
     				"</tr>" +
     			"</table>" +
     		"</div>");
    
    	switch (D) {
			case 1 : {
				break;
			}
			case 2 : {
				
				// multiple choice
				
				var c = E;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                        	 .html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	if (u.isMandatory) {
                                c.find('.label-control-mandatory').show()
                            }
                        	
                        	if (u.options.list.length != 0) {
                        		
                        		var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        		
                        		
                        		$("<tr class=\"row-header\">" +
                        				"<td></td>" +
                        				"<td></td>" +
                        				"<td><b>Percent</b></td>" +
                        				"<td class=\"last-item\"><b>Count</b></td>" +
                        			"</tr>").appendTo(t);
                        		
                        		
                        		
                        		var P = null;
                        		for (var i = 0; i < u.options.list.length; i++) {
                        			if(u.options.list[i].optionKindId == 0) {
                        				
                        				var percentage, count;
                        				if(u.options.list[i].statistics != null) {
                            				count = u.options.list[i].statistics[u.controlId].count;
                            				percentage = Math.round(count / u.options.list[i].statistics[u.controlId].sum * 100);
                            			} else {
                            				count = 0;
                            				percentage = 0;
                            			}
                        				
                        				var h = $("<tr class=\"highlight-item row-result\">" +
                        							"<td>" +
                        								"<div class=\"row-choice " + (u.options.list[i].selectTypeId ? "selected-item" : "") + "\">" + u.options.list[i].text + "</div>" +
                        								"<div class=\"container-additional-details-or-comments\" style=\"display:" + (u.options.list[i].answerComment != undefined ? "block" : "none") + "\">" +
                        									"<div class=\"row-desc\">" + (u.options.list[i].additionalDetailsTitle != undefined ? u.options.list[i].additionalDetailsTitle : "Additional details or comments") + "</div>" +
                        									"<div class=\"row-details\">" +
	                        									"<div class=\"tip\">" +
		                	     									"<div class=\"tip-top\"></div>" +
		                	     									"<label>" + (u.options.list[i].answerComment != undefined ? u.options.list[i].answerComment : "") + "</label>" +
		                	     								"</div>" +
                        									"</div>" +
                        								"</div>" +
                        							"</td>" +
                        							"<td class=\"column-bar\">" +
                        								"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
                        							"</td>" +
                        							"<td class=\"column-statistic\">" +
	                    								"<b>" + percentage + "%</b>" +
	                    							"</td>" +
                        							"<td class=\"last-item column-statistic\">" +
	                    								"<span>" + count + "</span>" +
	                    							"</td>" +
                        						"</tr>").appendTo(t);
                        				
                        				
                        				
                        			}
                        			if(u.options.list[i].optionKindId == 1) {
                        				P = u.options.list[i];
                        			}
                        		}
                        		
                        		// other
                        		if(P != null) {
                        			
                        			var percentage, count;
                    				if(P.statistics != null) {
                        				count = P.statistics[u.controlId].count;
                        				percentage = Math.round(count / P.statistics[u.controlId].sum * 100);
                        			} else {
                        				count = 0;
                        				percentage = 0;
                        			}
                    				
                        			var z = $("<tr class=\"highlight-item row-result\">" +
                        				"<td>" +
                        					"<div>" + (count != 0 ? "<a href=\"#\" title=\"" + P.text + "\" class=\"ui-icon-expand expand-responses\">" + P.text + "</a>" : "<span>" + P.text + "</span>") + "</div>" +
                							"<ul class=\"related-responses\" style=\"display: none\"></ul>" +
                        				"</td>" +
                        				"<td class=\"column-bar\">" +
                        					"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
                        				"</td>" +
                        				"<td class=\"column-statistic\">" +
                        					"<b>" + percentage + "%</b>" +
                        				"</td>" +
                        				"<td class=\"last-item column-statistic\">" +
                        					"<span>" + count + "</span>" +
	                    				"</td>" +
                        			"</tr>").appendTo(t);
                        			
                        			var related = $("<tr>" +
                                			"<td colspan=\"4\">" +
                                				"<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%\" class=\"table-related-responses\"></table>" +
                                			"</td>" +
                                		"</tr>").appendTo(t);
                        			
                        			var isRelatedResponses = false;
                                	var relatedResponses = related.find(".table-related-responses"); //z.find("ul.related-responses");
                                	z.find('.expand-responses').click(function(e) {
                                		if(!$(this).hasClass("ui-icon-collapse")) {
                                			
                                			$(this)
                                			.addClass("ui-icon-collapse");
                                			
                                			relatedResponses.show();
                                			
                                			// get related responses
                                			if(!isRelatedResponses) {
                                				
                                				getAnswererResultsText({
                                					opinionId : I.opinionId,
                                					/*controlId : u.controlId,*/
                                					optionId : P.optionId,
                                					success : function(data) {
                                						
                                						for(var r = 0; r < data.list.length; r++) {
                                							
                                							$("<tr>" +
                                								"<td><a href=\"responses/" + data.list[r].sessionId + "\" title=\"\">" + data.list[r].text + "</a></td>" +
                                							"</tr>").appendTo(relatedResponses);
                                							
                                						}
                                						
                                						isRelatedResponses = true;
                                						
                                					},
                                					error: function(error) {
                                						//
                                					}
                                				});
                                				
                                			}
                                			
                                		} else {
                                			$(this)
                                			.removeClass("ui-icon-collapse");
                                			
                                			relatedResponses.hide();
                                		}
                                		
                                		
                                		e.preventDefault();
                                	});
                        			
                        		}
                        		
                        		$("<tr class=\"row-answered\">" +
                        				"<td colspan=\"3\"><b>Answered</b></td>" +
                        				"<td class=\"last-item\"><b class=\"label-answered\">" + u.answered + "</b></td>" +
                        			"</tr>").appendTo(t);
                        		
                        		
                        		$("<tr class=\"row-skipped\">" +
                        				"<td colspan=\"3\"><b>Skipped</b></td>" +
                        				"<td class=\"last-item\"><b class=\"label-skipped\">" + u.skipped + "</b></td>" +
                        			"</tr>").appendTo(t);
                        		
                        		// append table to container
                        		t.appendTo(g);
                        		
                        		
                        	}
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				
				break;
			}
		}
    
    	
    	
    };
    
    B(I.controlTypeId);
};