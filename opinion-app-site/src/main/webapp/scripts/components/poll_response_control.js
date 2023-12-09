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
		     				
		     				"<div class=\"container-control-comment\" style=\"display: none\">" + 
		     					"<div class=\"label-control-comment\"></div>" + 
		     					"<div class=\"row-textarea\">" + 
		     						"<div class=\"cell\">" + 
		     							"<div class=\"tip\">" +
		     								"<div class=\"tip-top\"></div>" +
		     								"<label class=\"tip-control-comment\"></label>" +
		     							"</div>" +
		     						"</div>" + 
		     					"</div>" + 
		     				"</div>" + 
     					"</td>" +
     				"</tr>" +
     			"</table>" +
     		"</div>");
    
    	switch (D) {
			case 1 : {
				break;
			}
			case 2 : {
				
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
                        	
                        	/*
                        	if (u.isEnableNote) {
                            	if(u.note != null) {
                            		c.find('.label-control-note').html(u.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")).show();
                            	}
                            }
                        	*/
                        	
                        	if (u.isEnableComment) {
                                var e = c.find('.container-control-comment');
                                e.show();
                                var j = e.find('.label-control-comment').text(u.comment);
                                var f = e.find('.tip-control-comment');
                                if(u.answerComment != undefined) {
                                	f.text(u.answerComment);
                                }
                        	}
                        	
                        	if (u.options.list.length != 0) {
                        		
                        		var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        		
                        		
                        		var isSkipped = true;
                        		
                        		var P = null;
                        		for (var i = 0; i < u.options.list.length; i++) {
                        			if(u.options.list[i].optionKindId == 0) {
                        				
                        				
                        				/*
                        				var percentage, count;
                        				if(u.options.list[i].statistics != null) {
                            				count = u.options.list[i].statistics[u.controlId].count;
                            				percentage = Math.round(count / u.options.list[i].statistics[u.controlId].sum * 100);
                            			} else {
                            				count = 0;
                            				percentage = 0;
                            			}
                        				*/
                        				
                        				if(u.options.list[i].selectTypeId != 0) {
                        				
                        					$("<tr class=\"" + (u.options.list[i].selectTypeId ? "highlight-item" : "hide-item") + " row-result\">" +
                    							"<td class=\"last-item\">" +
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
                    							/*
                    							"<td class=\"column-bar\">" +
                    								"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
                    							"</td>" +
                    							"<td class=\"column-statistic\">" +
                    								"<span>" + percentage + "%</span>" +
                    							"</td>" +
                    							"<td class=\"column-statistic\">" +
                    								"<b>" + count + "</b>" +
                    							"</td>" +
                    							*/
                    						"</tr>").appendTo(t);
                        				}
                        				
                        				
                        			}
                        			if(u.options.list[i].optionKindId == 1) {
                        				P = u.options.list[i];
                        			}
                        			
                        			// isSkipped
                        			if(u.options.list[i].selectTypeId != 0) {
                        				isSkipped = false;
                        			}
                        			
                        			
                        		}
                        		
                        		// other
                        		if(P != null) {
                        			
                        			
                        			/*
                        			var percentage, count;
                    				if(P.statistics != null) {
                        				count = P.statistics[u.controlId].count;
                        				percentage = Math.round(count / P.statistics[u.controlId].sum * 100);
                        			} else {
                        				count = 0;
                        				percentage = 0;
                        			}
                    				*/
                        			
                        			if(P.selectTypeId != 0) {
                        			
	                        			$("<tr class=\"" + (P.selectTypeId == 1 ? "highlight-item" : "hide-item") + " row-result\">" +
	                        				"<td class=\"last-item\">" +
	                        					"<div class=\"row-choice-other " + (P.selectTypeId == 1 ? "selected-item" : "") + "\">" + P.text + "</div>" +
	                        					"<div style=\"padding-left: 40px;display:" + (P.answerValue != undefined ? "block" : "none") + "\"><label class=\"tip\">" + (P.answerValue != undefined ? P.answerValue : "") + "</label></div>" +
	                        				"</td>" +
	                        				/*
	                        				"<td class=\"column-bar\">" +
	                        					"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
	                        				"</td>" +
	                        				"<td class=\"column-statistic\">" +
	                        					"<span>" + percentage + "%</span>" +
	                        				"</td>" +
	                        				"<td class=\"column-statistic\">" +
	                        					"<b>" + count + "</b>" +
		                    				"</td>" +
		                    				*/
	                        			"</tr>").appendTo(t);
	                        			
                        			}
                        			
                        		}
                        		
                        		
                        		
                        		if(isSkipped) {
                        			
                        			$("<tr class=\"row-result\"><td class=\"last-item\"><em style=\"color: #999\">The respondent skipped this question.</em></td></tr>").appendTo(t);
                        			
                        		}
                        		
                        		
                        		
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