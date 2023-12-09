jQuery.fn.resultControl = function () {
    if (arguments.length == 0) return [];
    var I = arguments[0] || {};
    var v = $(this);
    
    var B = function (D) {
    	
    	var EH = $("<div class=\"result-control-container\">" +
     			"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
     				"<tr class=\"row-control-header\">" +
     					"<td>" +
     						"<b class=\"header-control-content\"></b>" +  
     					"</td>" +
     				"</tr>" +
     				"<tr>" +
     					"<td>" + 
		     				"<div class=\"container-control-includes\"></div>" + 
     					"</td>" +
     				"</tr>" +
     			"</table>" +
     		"</div>");
    	
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
		     					"<div class=\"label-control-comment row-desc\"></div>" + 
		     					"<div class=\"row-details\">" + 
	     							"<div class=\"tip\">" +
	     								"<div class=\"tip-top\"></div>" +
	     								"<label class=\"tip-control-comment\"></label>" +
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
				break
			}
			case 3 : {
				
				// essay
				
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
                        	
                        	var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        	
                        	if(u.answerValue != undefined) {
                            	$("<tr class=\"highlight-item row-result\">" +
                            			"<td class=\"last-item\">" + u.answerValue + "</td>" +
                            		"</tr>").appendTo(t);
                            } else {
                            	$("<tr class=\"row-result\"><td class=\"last-item\"><em style=\"color: #999\">The respondent skipped this question.</em></td></tr>").appendTo(t);
                            }
                        	
                        	
                        	// append table to container
                    		t.appendTo(g);
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				break
			}
			case 4 : {
				
				// matrix
				
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
                                c.find('.label-control-mandatory').show();
                            }
                        	
                        	if (u.controls.list.length != 0) {
                        		
                        		var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        		
                        		var isSkipped = true;
                        		
                        		for (var i = 0; i < u.controls.list.length; i++) {
                        			
                        			var o = $("<tr class=\"highlight-item row-result\">" +
                							"<td class=\"last-item\">" +
                								"<div class=\"row-choice\">" + u.controls.list[i].content + "</div>" +
                								"<div style=\"padding-left: 40px;\" class=\"row-options\"></div>" +
                							"</td>" +
                						"</tr>"); //.appendTo(t);
                        			
                        			for (var y = 0; y < u.options.list.length; y++) {
                        				
                        				
                                        var actualOption = u.options.list[y];
                                    	var actualSubControl = u.controls.list[i];
                                    	
                                    	if(actualOption.answerSelectedControlsIds != undefined) {
                                        	for(var selectedControlIdIndex in actualOption.answerSelectedControlsIds){
                                        		if(parseInt(actualOption.answerSelectedControlsIds[selectedControlIdIndex]) == parseInt(actualSubControl.controlId)){
                                        			
                                        			// TODO:
                                        			o.appendTo(t);
                                        			
                                        			$("<div class=\"row-option\">" + actualOption.text  + "</div>").appendTo(o.find(".row-options"));
                                        			
                                        			
                                        			// isSkipped
                                        			isSkipped = false;
                                        			
                                        			
                                        		}
                                        	}
                                        }
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
				break
			}
			case 5 : {
				var c = E;
				var F = I.controlId;
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content').html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	/*
                        	if (u.isEnableNote) {
                            	if(u.note != null) {
                            		c.find('.label-control-note').html(u.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")).show();
                            	}
                            }
                        	*/
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				break
			}
			case 6 : {
				
				// date/time
				
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
                                c.find('.label-control-mandatory').show();
                            }
                        	
                        	/*
                        	if (u.isEnableNote) {
                            	if(u.note != null) {
                            		c.find('.label-control-note').html(u.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")).show();
                            	}
                            }
                        	*/
                        	
                        	var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        	
                        	var getMonth = function(value) {
                        		
                        		var months = {
                        				1 : "Jan",
                  				        2 : "Feb",
                  				        3 : "Mar",
                  				        4 : "Apr",
                  				        5 : "May",
                  				        6 : "Jun",
                  				        7 : "Jul",
                  				        8 : "Aug",
                  				        9 : "Sep",
                  				        10 : "Oct",
                  				        11 : "Nov",
                  				        12 : "Dec"
                        		};
                        		
                        		return months[value];
                  				    
                        	};
                        	
                        	var getTimezone = function(value) {
                        		var timeZones = {
             				    	"-12.0" : "(GMT -12:00) Eniwetok, Kwajalein", 
             				       	"-11.0" : "(GMT -11:00) Midway Island, Samoa", 
             				       	"-10.0" : "(GMT -10:00) Hawaii", 
             				       	"-9.0" : "(GMT -9:00) Alaska", 
             				       	"-8.0" : "(GMT -8:00) Pacific Time (US & Canada)", 
             				       	"-7.0" : "(GMT -7:00) Mountain Time (US & Canada)", 
             				       	"-6.0" : "(GMT -6:00) Central Time (US & Canada), Mexico City", 
             				       	"-5.0" : "(GMT -5:00) Eastern Time (US & Canada), Bogota, Lima", 
             				       	"-4.0" : "(GMT -4:00) Atlantic Time (Canada), Caracas, La Paz", 
             				       	"-3.5" : "(GMT -3:30) Newfoundland", 
             				       	"-3.0" : "(GMT -3:00) Brazil, Buenos Aires, Georgetown", 
             				       	"-2.0" : "(GMT -2:00) Mid-Atlantic",
             				       	"-1.0" : "(GMT -1:00 hour) Azores, Cape Verde Islands",
             				       	"0.0" : "(GMT) Western Europe Time, London, Lisbon, Casablanca",
             				       	"1.0" : "(GMT +1:00 hour) Brussels, Copenhagen, Madrid, Paris",
             				       	"2.0" : "(GMT +2:00) Kaliningrad, South Africa",
             				       	"3.0" : "(GMT +3:00) Baghdad, Riyadh, Moscow, St. Petersburg",
             				       	"3.5" : "(GMT +3:30) Tehran",
             				       	"4.0" : "(GMT +4:00) Abu Dhabi, Muscat, Baku, Tbilisi",
             				       	"4.5" : "(GMT +4:30) Kabul",
             				       	"5.0" : "(GMT +5:00) Ekaterinburg, Islamabad, Karachi, Tashkent",
             				       	"5.5" : "(GMT +5:30) Bombay, Calcutta, Madras, New Delhi",
             				       	"5.75" : "(GMT +5:45) Kathmandu",
             				       	"6.0" : "(GMT +6:00) Almaty, Dhaka, Colombo",
             				       	"7.0" : "(GMT +7:00) Bangkok, Hanoi, Jakarta",
             				       	"8.0" : "(GMT +8:00) Beijing, Perth, Singapore, Hong Kong",
             				       	"9.0" : "(GMT +9:00) Tokyo, Seoul, Osaka, Sapporo, Yakutsk",
             				       	"9.5" : "(GMT +9:30) Adelaide, Darwin",
             				       	"10.0" : "(GMT +10:00) Eastern Australia, Guam, Vladivostok",
             				       	"11.0" : "(GMT +11:00) Magadan, Solomon Islands, New Caledonia",
             				       	"12.0" : "(GMT +12:00) Auckland, Wellington, Fiji, Kamchatka"
                        		};
                        		return timeZones[value];
                        	};
                        	
                        	var getDateFormat = function(inputTypeId, value) {
                        		switch (inputTypeId) {
                        			case 0: {
                        				// MMM/DD
		                        		var a = JSON.parse(value);
		                        		return (a.month != undefined ? getMonth(a.month) : "" ) + "/" + (a.day != undefined ? a.day : "");
		                        	}
		                        	case 1: {
		                        		// MMM/DD/YYYY
		                        		var a = JSON.parse(value);
		                        		return (a.month != undefined ? getMonth(a.month) : "") + "/" + (a.day != undefined ? a.day : "") + "/" + (a.year != undefined ? a.year : "");
		                        	}
		                        	case 2: {
		                        		// MMM/DD/YYYY HH:MM
		                        		var a = JSON.parse(value);
		                        		return (a.month != undefined ? getMonth(a.month) : "") + "/" + (a.day != undefined ? a.day : "") + "/" + (a.year != undefined ? a.year : "") + " " + (a.hours != undefined ? a.hours : "") + ":" + (a.minutes != undefined ? a.minutes : "");
		                        	}
		                        	case 3: {
		                        		// DD/MMM
		                        		var a = JSON.parse(value);
		                        		return (a.day != undefined ? a.day : "") + "/" + (a.month != undefined ? getMonth(a.month) : "" );
		                        	}
		                        	case 4: {
		                        		// DD/MMM/YYYY
		                        		var a = JSON.parse(value);
		                        		return (a.day != undefined ? a.day : "") + "/" + (a.month != undefined ? getMonth(a.month) : "") + "/" + (a.year != undefined ? a.year : "");
									}
		                        	case 5: {
		                        		// DD/MMM/YYYY HH:MM
		                        		var a = JSON.parse(value);
		                        		return (a.day != undefined ? a.day : "") + "/" + (a.month != undefined ? getMonth(a.month) : "") + "/" + (a.year != undefined ? a.year : "") + " " + (a.hours != undefined ? a.hours : "") + ":" + (a.minutes != undefined ? a.minutes : "");
									}
		                        	case 6: {
		                        		// MMM
		                        		var a = JSON.parse(value);
		                        		return (a.month != undefined ? getMonth(a.month) : "" );
									}
		                        	case 7: {
		                            	 // DD
		                        		var a = JSON.parse(value);
		                        		return (a.day != undefined ? a.day : "");
		                        	}
		                        	case 8: {
		                            	 // YYYY
		                        		var a = JSON.parse(value);
		                        		return (a.year != undefined ? a.year : "");
		                        	}
		                        	case 9: {
		                            	 // HH:MM
		                        		var a = JSON.parse(value);
		                        		return (a.hours != undefined ? a.hours : "") + ":" + (a.minutes != undefined ? a.minutes : "");
		                        	}
		                        	case 10: {
		                            	 // TIMEZONE
		                        		var a = JSON.parse(value);
		                        		return (a.timeZone != undefined ? getTimezone(a.timeZone) : "");
		                        	}
                        		}
                        	};
                        	
                        	if(u.answerValue != undefined) {
                        		
                        		
                        		$("<tr class=\"highlight-item row-result\">" +
                        				"<td class=\"last-item\">" + getDateFormat(u.inputTypeId, u.answerValue) + "</td>" +
                        			"</tr>").appendTo(t);
                        		
                        		
                        		
                        		
                        		/*
	                        	switch (u.inputTypeId) {
		                        	case 0: {
		                        		
		                        		
		                        		// MMM/DD
	                                	$("<tr class=\"highlight-item row-result\">" +
                                				"<td>" +
		                                			"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.monthTitle != null ? u.monthTitle : contentDictionary.titles.title_Month) + "</div><div class=\"cell-desc-month\">" + (u.dayTitle != null ? u.dayTitle : contentDictionary.titles.title_Day) + "</div></div>" +
		                                			"<div class=\"row\">" +
		                                				"<div class=\"cell\">" +
		                                					"<label class=\"tip month-field\">" + u.answerText.month + "</label>&nbsp;/" +
		                                				"</div>" +
		                                				"<div class=\"cell\" style=\"margin-left: 3px;\">" +
		                                					"<label class=\"tip day-field\">" + u.answerText.day + "</label>" +
		                                				"</div>" +
		                                			"</div>" +
                                				"</td>" +
                                			"</tr>").appendTo(t);
	                                	
		                        		break;
		                        	}
		                        	case 1: {
		                        		
		                        		// MMM/DD/YYYY
	                                	$("<tr class=\"highlight-item row-result\">" +
                            					"<td>" +
	                                				"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.monthTitle != null ? u.monthTitle : contentDictionary.titles.title_Month) + "</div><div class=\"cell-desc-month\">" + (u.dayTitle != null ? u.dayTitle : contentDictionary.titles.title_Day) + "</div><div class=\"cell-desc-month\">" + (u.yearTitle != null ? u.yearTitle : contentDictionary.titles.title_Year) + "</div></div>" +
		                                			"<div class=\"row\">" +
		                                				"<div class=\"cell\">" +
		                                					"<label class=\"tip month-field\">" + u.answerText.month + "</label>&nbsp;/" +
		                                				"</div>" +
		                                				"<div class=\"cell\" style=\"margin-left: 3px;\">" +
		                                					"<label class=\"tip day-field\">" + u.answerText.day + "</label>&nbsp;/" +
		                                				"</div>" +
		                                				"<div class=\"cell\" style=\"margin-left: 3px;\">" +
		                                					"<label class=\"tip year-field\">" + u.answerText.year + "</label>" +
		                                				"</div>" +
		                                			"</div>" +
                                				"</td>" +
                                			"</tr>").appendTo(t);
	                                	
		                        		break
		                        	}
		                        	case 2: {
		                        		
		                        		// MMM/DD/YYYY HH:MM
	                                	$("<tr class=\"highlight-item row-result\">" +
                        						"<td>" +
	                            					"<div class=\"row-desc\">" +
			                                			"<div class=\"cell-desc-month\">" + (u.monthTitle != null ? u.monthTitle : contentDictionary.titles.title_Month) + "</div>" +
			                                			"<div class=\"cell-desc-month\">" + (u.dayTitle != null ? u.dayTitle : contentDictionary.titles.title_Day) + "</div>" +
			                                			"<div class=\"cell-desc-month\">" + (u.yearTitle != null ? u.yearTitle : contentDictionary.titles.title_Year) + "</div>" +
			                                			"<div class=\"cell-desc-month\">" + (u.hoursTitle != null ? u.hoursTitle : contentDictionary.titles.title_Hours) + "</div>" +
			                                			"<div class=\"cell-desc-month\">" + (u.minutesTitle != null ? u.minutesTitle : contentDictionary.titles.title_Minutes) + "</div>" +
		                                			"</div>" +
		                                			"<div class=\"row\">" +
		                                				"<div class=\"cell\">" +
		                                					"<label class=\"tip month-field\">" + u.answerText.month + "</label>&nbsp;/" +
		                                				"</div>" +
		                                				"<div class=\"cell\" style=\"margin-left: 3px;\">" +
		                                					"<label class=\"tip day-field\">" + u.answerText.day + "</label>&nbsp;/" +
		                                				"</div>" +
		                                				"<div class=\"cell\" style=\"margin-left: 3px;\">" +
		                                					"<label class=\"tip year-field\">" + u.answerText.year + "</label>" +
		                                				"</div>" +
		                                				"<div class=\"cell\" style=\"margin-left: 12px;\">" +
		                                					"<label class=\"tip hours-field\">" + u.answerText.hours + "</label>&nbsp;:" +
		                                				"</div>" +
		                                				"<div class=\"cell\" style=\"margin-left: 3px;\">" +
		                                					"<label class=\"tip minutes-field\">" + u.answerText.minutes + "</label>" +
		                                				"</div>" +
		                                			"</div>" +
                                				"</td>" +
                                			"</tr>").appendTo(t);
	                                	
		                        		break
		                        	}
		                        	case 3: {
		                        		
		                        		// DD/MMM
	                                	$("<tr class=\"highlight-item row-result\">" +
                    							"<td>" +
	                        						"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.dayTitle != null ? u.dayTitle : contentDictionary.titles.title_Day) + "</div><div class=\"cell-desc-month\">" + (u.monthTitle != null ? u.monthTitle : contentDictionary.titles.title_Month) + "</div></div>" +
		                                			"<div class=\"row\">" +
		                                				"<div class=\"cell\">" +
		                                					"<label class=\"tip day-field\">" + u.answerText.day + "</label>&nbsp;/" +
		                                				"</div>" +
		                                				"<div class=\"cell\" style=\"margin-left: 3px;\">" +
		                                					"<label class=\"tip month-field\">" + u.answerText.month + "</label>" +
		                                				"</div>" +
		                                			"</div>" +
                                				"</td>" +
                                			"</tr>").appendTo(t);
	                                	
		                        		
		                        		break
		                        	}
		                        	case 4: {
		                        		
		                        		// DD/MMM/YYYY
		                        		$("<tr class=\"highlight-item row-result\">" +
                								"<td>" +
	                    							"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.dayTitle != null ? u.dayTitle : contentDictionary.titles.title_Day) + "</div><div class=\"cell-desc-month\">" + (u.monthTitle != null ? u.monthTitle : contentDictionary.titles.title_Month) + "</div><div class=\"cell-desc-month\">" + (u.yearTitle != null ? u.yearTitle : contentDictionary.titles.title_Year) + "</div></div>" +
			                        				"<div class=\"row\">" +
			                        					"<div class=\"cell\">" +
			                        						"<label class=\"tip day-field\">" + u.answerText.day + "</label>&nbsp;/" +
			                        					"</div>" +
			                        					"<div class=\"cell\" style=\"margin-left: 3px;\">" +
			                        						"<label class=\"tip month-field\">" + u.answerText.month + "</label>&nbsp;/" +
			                        					"</div>" +
			                        					"<div class=\"cell\" style=\"margin-left: 3px;\">" +
			                        						"<label class=\"tip year-field\">" + u.answerText.year + "</label>" +
			                        					"</div>" +
			                        				"</div>" +
	                        					"</td>" +
	                        				"</tr>").appendTo(t);
		                        			
		                        		break
		                        	}
		                        	case 5: {
		                        		
		                        		// DD/MMM/YYYY HH:MM
		                        		$("<tr class=\"highlight-item row-result\">" +
            									"<td>" +
	                								"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.dayTitle != null ? u.dayTitle : contentDictionary.titles.title_Day) + "</div><div class=\"cell-desc-month\">" + (u.monthTitle != null ? u.monthTitle : contentDictionary.titles.title_Month) + "</div><div class=\"cell-desc-month\">" + (u.yearTitle != null ? u.yearTitle : contentDictionary.titles.title_Year) + "</div><div class=\"cell-desc-month\">" + (u.hoursTitle != null ? u.hoursTitle : contentDictionary.titles.title_Hours) + "</div><div class=\"cell-desc-month\">" + (u.minutesTitle != null ? u.minutesTitle : contentDictionary.titles.title_Minutes) + "</div></div>" +
			                        				"<div class=\"row\">" +
			                        					"<div class=\"cell\">" +
			                        						"<label class=\"tip day-field\">" + u.answerText.day + "</label>&nbsp;/" +
			                        					"</div>" +
			                        					"<div class=\"cell\" style=\"margin-left: 3px;\">" +
			                        						"<label class=\"tip month-field\">" + u.answerText.month + "</label>&nbsp;/" +
			                        					"</div>" +
			                        					"<div class=\"cell\" style=\"margin-left: 3px;\">" +
			                        						"<label class=\"tip year-field\">" + u.answerText.year + "</label>" +
			                        					"</div>" +
			                        					"<div class=\"cell\" style=\"margin-left: 12px;\">" +
			                        						"<label class=\"tip hours-field\">" + u.answerText.hours + "</label>&nbsp;:" +
			                        					"</div>" +
			                        					"<div class=\"cell\" style=\"margin-left: 3px;\">" +
			                        						"<label class=\"tip minutes-field\">" + u.answerText.minutes + "</label>" +
			                        					"</div>" +
			                        				"</div>" +
	                        					"</td>" +
	                        				"</tr>").appendTo(t);
		                        		
		                        		break
		                        	}
		                        	case 6: {
		                        		
		                        		// MMM
	                                	$("<tr class=\"highlight-item row-result\">" +
        										"<td>" +
	            									"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.monthTitle != null ? u.monthTitle : contentDictionary.titles.title_Month) + "</div></div>" +
		                                			"<div class=\"row\">" +
		                                				"<div class=\"cell\">" +
		                                					"<label class=\"tip month-field\">" + u.answerText.month + "</label>" +
		                                				"</div>" +
		                                			"</div>" +
                                				"</td>" +
                                			"</tr>").appendTo(t);
		                        		
		                        		break
		                        	}
		                             case 7: {
		                            	 
		                            	 // DD
		                            	 $("<tr class=\"highlight-item row-result\">" +
     											"<td>" +
	         										"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.dayTitle != null ? u.dayTitle : contentDictionary.titles.title_Day) + "</div></div>" +
			                            	 		"<div class=\"row\">" +
			                            	 			"<div class=\"cell\">" +
			                            	 				"<label class=\"tip day-field\">" + u.answerText.day + "</label>" +
			                            	 			"</div>" +
			                            	 		"</div>" +
	                            	 			"</td>" +
	                            	 		"</tr>").appendTo(t);
		                            	 
		                            	 break
		                             }
		                             case 8: {
		                            	 
		                            	 // YYYY
		                            	 $("<tr class=\"highlight-item row-result\">" +
  												"<td>" +
	      											"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.yearTitle != null ? u.yearTitle : contentDictionary.titles.title_Year) + "</div></div>" +
			                            	 		"<div class=\"row\">" +
			                            	 			"<div class=\"cell\">" +
			                            	 				"<label class=\"tip year-field\">" + u.answerText.year + "</label>" +
			                            	 			"</div>" +
			                            	 		"</div>" +
	                            	 			"</td>" +
	                            	 		"</tr>").appendTo(t);
		                            	 
		                            	 break
		                             }
		                             case 9: {
		                            	 
		                            	 // HH:MM
		                            	 $("<tr class=\"highlight-item row-result\">" +
												"<td>" +
	   												"<div class=\"row-desc\"><div class=\"cell-desc-month\">" + (u.hoursTitle != null ? u.hoursTitle : contentDictionary.titles.title_Hours) + "</div><div class=\"cell-desc-month\">" + (u.minutesTitle != null ? u.minutesTitle : contentDictionary.titles.title_Minutes) + "</div></div>" +
			                            	 		"<div class=\"row\">" +
			                            	 			"<div class=\"cell\">" +
			                            	 				"<label class=\"tip hours-field\">" + u.answerText.hours + "</label>&nbsp;:" +
			                            	 			"</div>" +
			                            	 			"<div class=\"cell\" style=\"margin-left: 3px;\">" +
			                            	 				"<label class=\"tip minutes-field\">" + u.answerText.minutes + "</label>" +
			                            	 			"</div>" +
			                            	 		"</div>" +
	                            	 			"</td>" +
	                            	 		"</tr>").appendTo(t);
		                            	 
		                            	 break
		                             }
		                             case 10: {
		                            	 
		                            	 // TIMEZONE
		                                 $("<tr class=\"highlight-item row-result\">" +
												"<td>" +
													"<div class=\"row-desc\"><div class=\"cell-desc\">" + (u.timeZoneTitle != null ? u.timeZoneTitle : contentDictionary.titles.title_TimeZone) + "</div></div>" +
			                                 		"<div class=\"row\">" +
			                                 			"<div class=\"cell\">" +
			                                 				"<label class=\"tip timezone-field\">" + u.answerText.timeZone + "</label>" +
			                                 			"</div>" +
			                                 		"</div>" +
	                                 			"</td>" +
	                                 		"</tr>").appendTo(t);
		                                
		                            	 break
		                             }
	                        	}
	                        	*/
	                        	
                        	} else {
                        		$("<tr class=\"row-result\"><td class=\"last-item\"><em style=\"color: #999\">The respondent skipped this question.</em></td></tr>").appendTo(t);
                        	}
                        	
                        	
                        	// append table to container
                    		t.appendTo(g);
                        	
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				break
			}
			case 7 : {
				var c = E;
				var F = I.controlId;
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content').html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	/*
                        	if (u.isEnableNote) {
                            	if(u.note != null) {
                            		c.find('.label-control-note').html(u.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")).show();
                            	}
                            }
                        	*/
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				break
			}
			case 8 : {
				var c = E;
				var F = I.controlId;
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content').html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	/*
                        	if (u.isEnableNote) {
                            	if(u.note != null) {
                            		c.find('.label-control-note').html(u.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")).show();
                            	}
                            }
                        	*/
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				break
			}
			case 9 : {
				var c = E;
				var F = I.controlId;
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content').html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	/*
                        	if (u.isEnableNote) {
                            	if(u.note != null) {
                            		c.find('.label-control-note').html(u.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")).show();
                            	}
                            }
                        	*/
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				break
			}
			case 10 : {
				
				// scale
				
				var c = E;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				var H = [5, 7, 9, 0, 3, 4, 5, 6, 7, 8, 9, 10];
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                        	 .html(u.content.replace(/\n/g, "<br/>"));
                        	
                        	if (u.isMandatory) {
                                c.find('.label-control-mandatory').show();
                            }
                        	
                        	/*
                        	if (u.isEnableNote) {
                            	if(u.note != null) {
                            		c.find('.label-control-note').html(u.note.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>")).show();
                            	}
                            }
                        	*/
                        	
                        	
                        	var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        	
                        	if(u.answerValue != undefined) {
                        	
	                    		for (var y = u.fromScale; y < (H[u.toScale] + 1); y++) {
	                    			
	                    			/*
	                    			 * var percentage, count;
                        				if(u.options.list[i].statistics != null) {
                            				count = u.options.list[i].statistics[u.controlId].count;
                            				percentage = Math.round(count / u.options.list[i].statistics[u.controlId].sum * 100);
                            			} else {
                            				count = 0;
                            				percentage = 0;
                            			}
	                    			 */
	                    			
	                    			/*
	                    			var percentage, count;
                    				if(u.statistics[y] != undefined) {
                    					
                    					//alert(JSON.stringify(u.statistics[y]) + "_____" + JSON.stringify(u.statistics[y].sum) + "____" + JSON.stringify(u.statistics.count));
                    					
                    					//count = u.statistics.count;
                    					//percentage = Math.round(u.statistics[y].sum / count * 100);
                    					
                    					//count = 0;
                    					//percentage = 0;
                    					
                    					count = u.statistics[y].count;
                        				percentage = Math.round(count / u.statistics.sum * 100);
                    					
                        			} else {
                        				count = 0;
                        				percentage = 0;
                        			}
                        			*/
                    				
	                    			if(y == u.answerValue) {
	                    			
		                    			// highlight-item
		                    			$("<tr class=\"" + (u.answerValue != undefined ? (y == u.answerValue ? "highlight-item" : "hide-item") : "") + " row-result\">" +
		            							"<td class=\"last-item\">" +
		            								"<div class=\"row-choice " + (u.answerValue != undefined ? (y == u.answerValue ? "selected-item" : "") : "") + "\">" + y + " " + (y == 1 ? (u.fromScale != null ? (u.fromTitle != null ? u.fromTitle : "") : "") : (y == (H[u.toScale])) ? (u.toScale != null ? (u.toTitle != null ? u.toTitle : "") : "") : "") + "</div>" +
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
		            							/*"<td class=\"ui-progressbar\"></td>" +
		            							"<td class=\"column-statistic\">" +
		            								"<span class=\"tag-statistic\">" + (u.statistics[y] != undefined ? u.statistics[y].percentage : 0) + "</span>" +
		            							"</td>" +
		            							"<td class=\"column-statistic\">" +
		            								"<b>" + (u.statistics[y] != undefined ? u.statistics[y].sum : 0) + "</b>" +
		            							"</td>" +*/
		            						"</tr>").appendTo(t);
	                    			
	                    			}
	                    		}
	                    		
                        	} else {
                        		$("<tr class=\"row-result\"><td class=\"last-item\"><em style=\"color: #999\">The respondent skipped this question.</em></td></tr>").appendTo(t);
                        		
                        	}
                        	
                        	t.appendTo(g);
                        	
                        	c.appendTo(v);
                        	break;
                        }
					}
				};
				G();
				break
			}
			case 11: {
				
				// Hidden param
				var c = EH;
				var g = c.find('.container-control-includes');
				var F = I.controlId;
				
				var G = function () {
					for (var x = 0; x < response.controls.list.length; x++) {
                        if (response.controls.list[x].controlId == F) {
                        	var u = response.controls.list[x];
                        	
                        	c.find('.header-control-content')
                       	 		.html("[" + (u.key != undefined ? u.key : "undefined") + "]"); // u.content.replace(/\n/g, "<br/>")
                        	
                        	
                        	var t = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" style=\"width: 100%\"></table>");
                        	
                        	if(u.answerValue != undefined) {
                            	$("<tr class=\"highlight-item row-result\">" +
                            			"<td class=\"last-item\">" + u.answerValue + "</td>" +
                            		"</tr>").appendTo(t);
                            } else {
                            	$("<tr class=\"row-result\"><td class=\"last-item\"><em style=\"color: #999\">The respondent skipped this question.</em></td></tr>").appendTo(t);
                            }
                        	
                        	
                        	// append table to container
                    		t.appendTo(g);
                        	
                        	
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