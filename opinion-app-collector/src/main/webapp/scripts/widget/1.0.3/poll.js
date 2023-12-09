/*
* Inqwise
*
* Copyright 2011, 2013 Inqwise, Ltd. and other contributors
*
*/
;
(function () {
        
	var protocol = (("https:" == document.location.protocol) ? "https://" : "http://");
	function loadScript(url, callback) {
        var script = document.createElement("script");
        script.type = "text/javascript";
        if (script.readyState) { //IE
            script.onreadystatechange = function () {
                if (script.readyState == "loaded" || script.readyState == "complete") {
                    script.onreadystatechange = null;
                    callback();
                }
            };
        } else { //Others
            script.onload = function () {
                callback();
            };
        }
        script.src = url;
        document.getElementsByTagName("head")[0].appendChild(script);
    }
	
	if (window.jQuery === undefined || window.jQuery.fn.jquery !== '1.8.2') {
		loadScript(protocol + "ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js", function () {
	
			/*
	    	if (window.console) {
	    		window.console.log("jquery loaded");
	    	}
	    	*/
			
	    	//jQuery.noConflict();
	    	window.$ = jQuery.noConflict();
	    	
	    	
	    	if(!JSON) {
				loadScript(protocol + "yandex.st/json2/2011-10-19/json2.min.js", function () {
					init();
				});
			} else {
				init();
			}
	    	
		});
	} else {
		if(!JSON) {
			loadScript(protocol + "yandex.st/json2/2011-10-19/json2.min.js", function () {
				init();
			});
		} else {
			init();
		}
	}
	
	function findScript(target) {
		return $('script').filter(function() { return this.src.match(target); });
	};
	
	function evalConfig(html) {
		if (html) {
			// ignore CDATA in script tags
			var inner = html.replace(/^[\s\xA0]*(\/\/\s?\<\!\[CDATA\[)?/, '').replace(/(\/\/\s?\]\]\>)?[\s\xA0]*$/, '');
			if (/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]+$/.test(inner.replace(/\\./g, '@').replace(/"[^"\\\n\r]*"/g, ''))) {
				try {
					return eval( '(' + inner + ')' );
				} catch(err) {  }
			}
		}

		return {};
	};
	
	
	// global
	var servletUrl = "";
	var messages = {};
	
	var getDetails = function(params) {
		
		var obj = {
			opinions : {
				getDetails : { 
					guidTypeId : params.guidTypeId,
					guid : params.guid,
					modeId : params.modeId,
					themeId : params.themeId,
					ref : params.ref,
					target : params.target
				}
			}
		};
		
		$.ajax({ 
			 type: "GET", 
			 url : servletUrl,  
			 /*dataType : "json",*/
			 dataType: "jsonp",
			 jsonp: "callback",
			 data : { 
			 	rq : JSON.stringify(obj), 
			 	timestamp : $.getTimestamp()
			 },
			 success : function(data){
				 if(data.opinions.getDetails != undefined) {
					if(data.opinions.getDetails.error != undefined) {
						if(params.error != undefined 
								&& typeof params.error == "function") {
							params.error(data.opinions.getDetails);
						}
						
					} else {
						if(params.success != undefined 
								&& typeof params.success == "function") {
							params.success(data.opinions.getDetails);
						}
					}
				 }
			 }, 
			 error: function (XHR, textStatus, errorThrow) {
				 // error
			 }
		});
		
	};
	
	var _data = {}; 
	var _el = null;
	
	var defaultValidator = null;
	var validateGroup = []; // fix for matrix/likert
	var validateElements = {
		elements : []
	};
	
	var getControls = function(params) {
		
		var obj = {
			opinions : {
				getControls : {
					guidTypeId : params.guidTypeId,
					guid : params.guid,
					modeId : params.modeId,
					lastSelected : 0
				}
			}
		};
		
		$.ajax({ 
			 type: "GET", 
			 url : servletUrl,  
			 /*dataType : "json",*/
			 dataType: "jsonp",
			 jsonp: "callback",
			 data : { 
				rq : JSON.stringify(obj), 
				timestamp : $.getTimestamp()
			 },
			 success : function(data){
				 if(data.opinions.getControls != undefined) {
					if(data.opinions.getControls.error != undefined) {
						if(params.error != undefined 
								&& typeof params.error == "function") {
							params.error(data.opinions.getControls);
						}
					} else {
						if(params.success != undefined 
								&& typeof params.success == "function") {
							params.success(data.opinions.getControls);
						}
					}
				 }
			 },
			 error: function (XHR, textStatus, errorThrow) {
				 // error
			 }
		});
		
	};
	
	var getResults = function(params) {
		
		var obj = {
			responses : {
				getResults : { 
					guidTypeId : params.guidTypeId,
					guid : params.guid,
					modeId : params.modeId
				}
			}
		};
		
		$.ajax({ 
			 type: "GET", 
			 url : servletUrl,  
			 /*dataType : "json",*/
			 dataType: "jsonp",
			 jsonp: "callback",
			 data : { 
			 	rq : JSON.stringify(obj), 
			 	timestamp : $.getTimestamp()
			 },
			 success : function(data){
				 if(data.responses.getResults != undefined) {
					if(data.responses.getResults.error != undefined) {
						if(params.error != undefined 
								&& typeof params.error == "function") {
							params.error(data.responses.getResults);
						}
						
					} else {
						if(params.success != undefined 
								&& typeof params.success == "function") {
							params.success(data.responses.getResults);
						}
					}
				 }
			 }, 
			 error: function (XHR, textStatus, errorThrow) {
				 // error
			 }
		});
		
	};
	
	var responseType = {
		startOpinion : 1,
		finishOpinion : 2,
		nextSheet : 3,
		prevSheet : 4,
		control: 5,
		option : 6,
		authorize : 7
	};
	
	var isSendClientInfo = false;
	
	var createResponse = function(params) {
		
		var obj = {};
		
		// ? isSendClientInfo
		if(isSendClientInfo) {
			
			obj = {
				opinions : {
					createResponse : { 
						guidTypeId : params.guidTypeId,
						guid : params.guid,
						modeId : params.modeId,
						responseTypeId : params.responseTypeId,
						responseData : params.responseData,
						os : {
							name: $.client.os.name,
							platform: $.client.os.platform,
							language: $.client.os.systemLanguage,
							timeZone: $.client.os.timeZone
						},
						screen : {
							width : $.client.screen.width,
							height: $.client.screen.height,
							color: $.client.screen.color
						},
						browser : {
							name : $.client.browser.name,
							appName: $.client.browser.appName,
							version : $.client.browser.version,
							isCookieEnabled : $.client.browser.cookieEnabled,
							isJavaInstalled : $.client.browser.java.isInstalled,
							isFlashInstalled : $.client.browser.flash.isInstalled,
							flashVersion : $.client.browser.flash.version
						},
						ref : document.referrer,
						target : location.href
					}
				}
			};
			
		} else {
			
			obj = {
				opinions : {
					createResponse : { 
						guidTypeId : params.guidTypeId,
						guid : params.guid,
						modeId : params.modeId,
						responseTypeId : params.responseTypeId,
						responseData : params.responseData,
						ref : document.referrer,
						target : location.href
					}
				}
			};

		}
		
		$.ajax({ 
			 type: "GET", 
			 url : servletUrl,  
			 /*dataType : "json",*/
			 dataType: "jsonp",
			 jsonp: "callback",
			 data : { 
				rq : JSON.stringify(obj), 
				timestamp : $.getTimestamp()
			 },  
			 async : (params.async || false),
			 success : function(data){
				if(data.opinions.createResponse != undefined) {
					if(data.opinions.createResponse.error != undefined) {				
						if(params.error != undefined 
								&& typeof params.error == 'function') {
							params.error(data.opinions.createResponse);
						}
					} else {
						if(params.success != undefined 
							&& typeof params.success == 'function') {
							params.success(data.opinions.createResponse);
						}
					}
				 }
			 }  
		});

		if(isSendClientInfo) {
			isSendClientInfo = false;
		}
		
	};
	
	function fromConfig(config, callback) {
		
		// fix
		if(config.guidTypeId == undefined) {
			config.guidTypeId = 1;
		}
		
		// fix
		if(config.modeId == undefined) {
			config.modeId = 1;
		}
		
		// variables
		servletUrl = (config.collectorUrl != undefined ? config.collectorUrl : "http://localhost:8080/opinion-opinion-collector") + "/servlet/DataPostmaster";
		
		// TODO:
		getDetails({
			guidTypeId : config.guidTypeId,
			guid : (config.guid != undefined ? config.guid : ""),
			modeId : config.modeId,
			themeId : (config.themeId != undefined ? config.themeId : null),
			ref : document.referrer,
			target : location.href,
			success : function(data) {
				
				isSendClientInfo = data.isSendClientInfo;
				
				// global messages
				messages = {
					thanks : "Thank you for voting.",
					completed : "Poll has already been completed",
					notFound : "Poll not found",
					closed : "Poll Closed",
					defaultNoneSelectedOption : "Please choose",
					other : "Other, (please specify)"
				};
				
				$.extend(data, config);
				
				// set globals;
				
				_data = data;
				
				// callback data
				callback(config, data);
				
			},
			error: function(error) {
				
				// callback error
				// alert(JSON.stringify(error.error));
				
				switch(error.error) {
					case "NoResults": {
						
						// not found
						
						break;
					}
					case "NotExist" : {
						
						// not found
						
						break;
					}
					case "ArgumentNull" : {
						
						// not found
						
						break;
					}
				}
				
				
				
			}
		});
		
		
	};
	
	function getSheetControls(el, config, data, callback) {
		
		getControls({
			guidTypeId : config.guidTypeId,
			guid : (config.guid != undefined ? config.guid : ""),
			modeId : config.modeId,
			lastSelected : 0,
			success : function(controls) {
				
				el.find("ul.list-controls").empty();
				
				// clear previous validateElements
				validateElements.elements = [];
				
				
				if(controls.list.length != 0) {
					
				
					var controlCount = 0;
					
					var c = $("<li class=\"theme-question ui-question-a\" data-theme=\"a\" data-form=\"ui-question-a\">" +
						"<div>" +
							"<div><span class=\"label-control-status status\"></span></div>" +
							"<div class=\"theme-question-heading ui-question-heading-a\" data-theme=\"a\" data-form=\"ui-question-heading-a\">" +
								"<span class=\"label-control-mandatory asterisk\" style=\"display: none;\">*&nbsp;</span>" +
								"<span class=\"header-control-content\"></span>" +
							"</div>" +
							"<div class=\"theme-question-content ui-question-content-a\" data-theme=\"a\" data-form=\"ui-question-content-a\">" +
								"<div class=\"container-embed-code q-image\" style=\"display: none\"></div>" +
								"<div class=\"container-control-notes label-control-note q-help\" style=\"display: none;\"></div>" +
								"<ul class=\"container-control-includes\"></ul>" +
								"<div class=\"container-control-other\"></div>" +
							"</div>" +
						"</div>" +
					"</li>").appendTo(el.find("ul.list-controls"));
					
					var u = controls.list[0];
					
					var link = null;
					var F = u.controlId;
					var g = c.find('.container-control-includes');
					var otherContainer = c.find(".container-control-other");
					
					c.find('.header-control-content').html(u.content.replace(/\n/g, "<br/>"));
					
					
					
					// alert(_data.highlightRequiredQuestions);
					
					
					// mandatory
					if(u.isMandatory) {
						
						if(_data.highlightRequiredQuestions) {
							c.find('.label-control-mandatory').show();
						}
						
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
			                            		var k = $("<li class=\"choice\"><label><span><input type=\"radio\" class=\"radio-option\" id=\"" + u.options.list[i].optionId + "\" name=\"" + F + "\" optionindex=\"" + i + "\" optiontext=\"" + u.options.list[i].text + "\" optionkindid=\"" + u.options.list[i].optionKindId + "\" value=\"" + u.options.list[i].value + "\" autocomplete=\"off\" /></span>" + u.options.list[i].text + "</label></li>");
			                                	if(u.options.list[i].selectTypeId != undefined) {
			                                		if(u.options.list[i].selectTypeId != 0) {
			                                			k.find('input:radio').prop('checked', true);
			                                		}
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
													guidTypeId : _data.guidTypeId,
													guid : _data.guid,
													modeId : _data.modeId,
													responseTypeId : responseType.option,
													async : true,
													responseData : a
												});
												
			                                    
			                                });
			                            }
			                        	
			                        	if(P != null) {
			                        		var l = $("<div class=\"choice-other\"><label><span><input type=\"radio\" class=\"radio-other\" name=\"" + F + "\" optionid=\"" + P.optionId + "\" optionkindid=\"" + P.optionKindId +"\" optiontext=\"" + ((P.text != "" && P.text != null) ? P.text : "Other, (please specify)") + "\" autocomplete=\"off\" /></span>" + ((P.text != "" && P.text != null) ? P.text : "Other, (please specify)") + "</label><div class=\"container-other\"><input type=\"text\" class=\"text-field input-control-other\" /></div></div>");
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
			                                		if(P.selectTypeId != 0) {
			                                			m.prop('checked', true);
			                                    		n.val(P.answerValue);
			                                		}
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
														guidTypeId : _data.guidTypeId,
														guid : _data.guid,
														modeId : _data.modeId,
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
			                            var o = $("<div><select class=\"dropdown-" + F + "\" name=\"" + F + "\"></select></div>");
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
													guidTypeId : _data.guidTypeId,
													guid : _data.guid,
													modeId : _data.modeId,
													responseTypeId : responseType.option,
													async : true,
													responseData : b
												});
												
			                                });
			                            }
			                            
			                            // default text
			                            if(R != null) {
                                        	var t = new Option((R.text != null ? R.text : messages.defaultNoneSelectedOption), 0);
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
			                                    		if(Y[z].selectTypeId != 0) {
			                                    			L = (z + 1);
			                                    		}
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
			                        			var k = $("<li class=\"choice\"><label><span><input type=\"checkbox\" class=\"checkbox-" + u.options.list[i].optionId + "\" id=\"" + u.options.list[i].optionId + "\" name=\"" + F + "\" optionindex=\"" + i + "\" optiontext=\"" + u.options.list[i].text + "\" value=\"" + u.options.list[i].value + "\" autocomplete=\"off\" /></span>" + u.options.list[i].text + "</label></li>");
			                                	if(u.options.list[i].selectTypeId != undefined) {
			                                		if(u.options.list[i].selectTypeId != 0) {
			                                			k.find('input:checkbox').prop('checked', true);
			                                		}
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
													guidTypeId : _data.guidTypeId,
													guid : _data.guid,
													modeId : _data.modeId,
													responseTypeId : responseType.option,
													async : true,
													responseData : a
												});
												
			                                });
			                            }
			                        	
			                        	
			                        	if(P != null) {
			                        		
											var l = $("<div class=\"choice-other\"><label><span><input type=\"checkbox\" class=\"checkbox-other\" optionid=\"" + P.optionId + "\" name=\"" + F + "\" optionkindid=\"" + P.optionKindId + "\" optiontext=\"" + ((P.text != "" && P.text != null) ? P.text : "Other, (please specify)") + "\" autocomplete=\"off\" /></span>" + ((P.text != "" && P.text != null) ? P.text : "Other, (please specify)") + "</label><div class=\"container-other\"><input type=\"text\" class=\"text-field input-control-other\" /></div></div>");
											l.appendTo(otherContainer);
											var z = l.find('.checkbox-other');
											var n = l.find('.input-control-other');
											
											if (config.guidTypeId == 1 && config.modeId == 1) {
												if(P.selectTypeId != undefined) {
													if(P.selectTypeId != 0) {
														z.prop('checked', true);
			                                    		n.val(P.answerValue);
													}
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
															guidTypeId : _data.guidTypeId,
															guid : _data.guid,
															modeId : _data.modeId,
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
														guidTypeId : _data.guidTypeId,
														guid : _data.guid,
														modeId : _data.modeId,
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
			                		c.find(".container-embed-code")
			                			.html("<img src=" + u.link + " />")
			                			.show();
			                	}
			                	
			                	// embed code
			                	if(u.linkTypeId == 2) {
			                    	c.find(".container-embed-code")
			                			.html(u.link)
			                			.show();
			                	}
			                }
							
							break;
							
						}
					}
					
					
					if(validateElements.elements.length != 0) {
						// declare validator if needed
						defaultValidator = new validator({
							elements : validateElements.elements
						});
					} else {
						defaultValidator = null;
					}
				
				}
				
				
				
				
				// actions
				//alert("ACTIONS");
				
				
				el.find(".container-vote").show();
				
				
				if(data.allowResults) {
					el.find(".container-view-results").show();
				}
				
				//alert("RUN -> CALL");
				
				
				
				if(callback != undefined 
						&& typeof callback == 'function') {
					callback();
				}
				
				
				
				
			},
			error: function(error) {
				
				// alert("ERR CONTROLS");
				
				if(callback != undefined 
						&& typeof callback == 'function') {
					callback();
				}
				
				
			}
		});
		
	};
	
	function displayResults(el, config, data, showBack) {
		
		getResults({
			guidTypeId : data.guidTypeId,
			guid : data.guid,
			modeId : data.modeId,
			success : function(results) {
				
				
				
				el.find(".theme-page").empty();
				
				$("<div>" +
					"<div>" +
						"<div class=\"theme-question ui-question-a\" data-theme=\"a\" data-form=\"ui-question-a\">" +
							"<div class=\"theme-question-heading ui-question-heading-a\" data-theme=\"a\" data-form=\"ui-question-heading-a\">" +
								"<span class=\"header-control-content\">TEST</span>" +
							"</div>" +
							"<div class=\"theme-question-content ui-question-content-a\" data-theme=\"a\" data-form=\"ui-question-content-a\">" +
								"<div class=\"container-control-results\"></div>" +
								"<div class=\"container-control-total-votes choice\" style=\"display: none;\">" +
									"<label>Total Votes:</label> " +
									"<span class=\"label-total-votes\"></span>" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<div class=\"theme-page-actions ui-page-actions-a\" data-theme=\"a\" data-form=\"ui-page-actions-a\">" +
						"<div class=\"container-back\" style=\"display: none;\">" +
							"<a data-form=\"ui-button-a\" data-theme=\"a\" class=\"theme-button ui-button-a button-back\" href=\"#\" title=\"" + data.messages.back + "\"><span>" + data.messages.back + "</span></a>" +
						"</div>" +
					"</div>" +
				"</div>").appendTo(el.find(".theme-page"));
				
				
				
				var animateBars = function () {
		    		el.find(".container-control-results").find("div.bar").each(function (i, elem) {
						var val = Math.max($(elem).attr("data-value") * $(elem).parents(".outer-bar").width() / 100, 2) + "px";
						$(elem).animate({ "width" : val });
					});
				};
				
				
				el.find(".header-control-content").html(results.controls.list[0].content.replace(/\n/g, "<br/>"));
				
				switch(results.controls.list[0].controlTypeId) {
					case 2 : {
						
						// choice
						
						if (results.controls.list[0].options.list.length != 0) {
							var P = null;
		                    var j = 0;
		                    
		                    var totalVotes = 0;
		                    
		                	for (var i = 0; i < results.controls.list[0].options.list.length; i++) {
		                		
		                		if(results.controls.list[0].options.list[i].optionKindId == 0) { //  || data.controls.list[0].options.list[i].optionKindId == 1
		                			
		                			var percentage, count;
		                			if(results.controls.list[0].options.list[i].statistics != undefined) {
		                				count = results.controls.list[0].options.list[i].statistics[results.controls.list[0].controlId].count;
		                				// percentage = Math.round(count / results.controls.list[0].options.list[i].statistics[results.controls.list[0].controlId].sum * 100);
		                				percentage = Math.round(results.controls.list[0].options.list[i].statistics[results.controls.list[0].controlId].percentage);
		                			} else {
		                				count = 0;
		                				percentage = 0;
		                			}
		                			
		                			
		                			totalVotes += count;
		                			
		                			
		                			var h = $("<div class=\"choice\">" +
		                				"<div>" +
			                				"<div>" +
			                					"<label>" + results.controls.list[0].options.list[i].text + "</label>" +
			                				"</div>" +
			                				"<div>" +
			                					"<span>" + percentage + "%</span>" + ( data.resultsTypeId == 3 ? "<span>&nbsp;(" + count + " votes)</span>" : "" ) +
			                				"</div>" +
		                				"</div>" +
		                				"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
		                			"</div>").appendTo(el.find(".container-control-results"));
		                			
		                		}
		                		if(results.controls.list[0].options.list[i].optionKindId == 1) {
		                			P = results.controls.list[0].options.list[i];
		                		}
		                		
		                	}
		                	
		                	// other
		            		if(P != null) {
		            			
		            			var percentage, count;
		        				if(P.statistics != undefined) {
		            				count = P.statistics[results.controls.list[0].controlId].count;
		            				// percentage = Math.round(count / P.statistics[results.controls.list[0].controlId].sum * 100);
		            				percentage = Math.round(P.statistics[results.controls.list[0].controlId].percentage);
		            			} else {
		            				count = 0;
		            				percentage = 0;
		            			}
		        				
		        				
		        				totalVotes += count;
		        				
		        				
		        				var z = $("<div class=\"choice\">" +
		        						"<div>" +
			                				"<div>" +
			                					"<label>" + P.text + "</label>" +
			                				"</div>" +
			                				"<div>" +
			                					"<span>" + percentage + "%</span>" + ( data.resultsTypeId == 3 ? "<span>&nbsp;(" + count + " votes)</span>" : "" ) +
			                				"</div>" +
		                				"</div>" +
		                				"<div class=\"outer-bar\"><div class=\"bar\" data-value=\"" + percentage + "\"><div class=\"bar-inner\"></div></div></div>" +
		                			"</div>").appendTo(el.find(".container-control-results"));
		        				
		            		}
		                	
		                	
		                	
		                	
		                	// total votes
		                	el.find(".label-total-votes").text(totalVotes);
		                	
		                	if(data.resultsTypeId == 3) {
		                		el.find(".container-control-total-votes").show();
		                	}
		                	
		                	// animate
		                	setTimeout(animateBars, 400);
		                	
						}
								
								
						
						break;
					}
				}
				
				
				
				
				if(data.guidTypeId == 1 && data.modeId == 1) {
				
					if(data.allowMultipleResponses) {
						
						// isSendClientInfo = true;
						
						el.find(".container-back").show();
					}
					
					if(!data.allowMultipleResponses && showBack) {
					
						// isSendClientInfo = true;
						
						el.find(".container-back").show();
						
					}
				} else {
					
					// isSendClientInfo = true;
					
					el.find(".container-back").show();
					
				}
				
				
				// back to poll
				el.find(".button-back").click(function(event) {
					
					// re-bind
					//bind(el, config, data);
					
					//alert(JSON.stringify(config));
					
					getDetails({
						guidTypeId : config.guidTypeId,
						guid : (config.guid != undefined ? config.guid : ""),
						modeId : config.modeId,
						themeId : (config.themeId != undefined ? config.themeId : null),
						ref : document.referrer,
						target : location.href,
						success : function(data) {
							
							isSendClientInfo = data.isSendClientInfo;
							
							/*
							// global messages
							messages = {
								thanks : "Thank you for taking the poll.",
								completed : "Poll has already been completed",
								notFound : "Poll not found",
								closed : "Poll Closed"
							};
							*/
							
							
							$.extend(data, config);
							
							// set globals;
							
							_data = data;
							
							
							
							// callback data
							//callback(config, data);
							
							render(el, config, data);
							
						},
						error: function(error) {
							
							// callback error
							
							switch(error.error) {
								case "NoResults": {
									
									// not found
									
									break;
								}
								case "NotExist" : {
									
									// not found
									
									break;
								}
								case "ArgumentNull" : {
									
									// not found
									
									break;
								}
							}
							
							
							
						}
					});
					
					
					event.preventDefault();
					
				});
				
				
				
				
				// inspector
				initInspector();
				
				
				
			},
			error: function(error) {
				alert("ERR");
			}
		});
		
	};
	
	function bind(el, config, data) {
		
		el.find(".theme-page").empty();
		
		$("<div>" +
			"<div>" +
				"<ul class=\"list-controls\"></ul>" +
			"</div>" +
			"<div class=\"theme-page-actions ui-page-actions-a\" data-theme=\"a\" data-form=\"ui-page-actions-a\">" +
				"<div class=\"container-vote\" style=\"display: none;\">" +
					"<a href=\"#\" title=\"" + data.messages.vote +"\" class=\"theme-button ui-button-a button-vote\" data-theme=\"a\" data-form=\"ui-button-a\"><span>" + data.messages.vote + "</span></a>" +
				"</div>" +
				"<div class=\"container-view-results\" style=\"display: none;\">" +
					"&nbsp;or&nbsp;<a href=\"#\" title=\"" + data.messages.viewResults + "\" class=\"button-view-results\">" + data.messages.viewResults + "</a>" +
				"</div>" +
			"</div>" +
		"</div>").appendTo(el.find(".theme-page"));
		
		// vote
		el.find(".button-vote").click(function(event) {
			
			var finish = function() {
				
				if(data.guidTypeId == 1 && data.modeId == 1) {
					
					createResponse({
						guidTypeId : data.guidTypeId,
						guid : data.guid,
						modeId : data.modeId,
						responseTypeId : responseType.finishOpinion,
						async : true,
						responseData : {},
						success : function() {
							
							// logic
							if(data.allowResults) {
								
								// show results
								displayResults(el, config, data);
								
							} else {
								
								el.find(".theme-page").empty();
								// completed
								$("<div>" +
										"<div data-form=\"ui-question-a\" data-theme=\"a\" class=\"theme-question ui-question-a\">" +
											"<div data-form=\"ui-question-heading-a\" data-theme=\"a\" class=\"theme-question-heading ui-question-heading-a\">" + data.title + "</div>" +
											"<div data-form=\"ui-question-content-a\" data-theme=\"a\" class=\"theme-question-content ui-question-content-a\">" + messages.thanks + "</div>" +
										"</div>" +
										"<div class=\"theme-page-actions ui-page-actions-a\" data-theme=\"a\" data-form=\"ui-page-actions-a\" style=\"display: none;\">" +
											"<a data-form=\"ui-button-a\" data-theme=\"a\" class=\"theme-button ui-button-a\" title=\"Sign Up FREE\" href=\"http://www.inqwise.com/en-us/register?ref=collector\" target=\"_blank\"><span>Sign Up FREE</span></a> It's quick and easy to get started." +
										"</div>" +
								"</div>").appendTo(el.find(".theme-page"));
								
								// inspector
								initInspector();
								
							}
							
							
						},
						error: function(error) {
							//
						}
					});
					
				} else {
					
					// show demo results
					if(data.allowResults) {
						
						// show results
						displayResults(el, config, data);
						
					} else {
						
						el.find(".theme-page").empty();
						// completed
						$("<div>" +
								"<div data-form=\"ui-question-a\" data-theme=\"a\" class=\"theme-question ui-question-a\">" +
									"<div data-form=\"ui-question-heading-a\" data-theme=\"a\" class=\"theme-question-heading ui-question-heading-a\">" + data.title + "</div>" +
									"<div data-form=\"ui-question-content-a\" data-theme=\"a\" class=\"theme-question-content ui-question-content-a\">" + messages.thanks + "</div>" +
								"</div>" +
								"<div class=\"theme-page-actions ui-page-actions-a\" data-theme=\"a\" data-form=\"ui-page-actions-a\" style=\"display: none;\">" +
									"<a data-form=\"ui-button-a\" data-theme=\"a\" class=\"theme-button ui-button-a\" title=\"Sign Up FREE\" href=\"http://www.inqwise.com/en-us/register?ref=collector\" target=\"_blank\"><span>Sign Up FREE</span></a> It's quick and easy to get started." +
								"</div>" +
						"</div>").appendTo(el.find(".theme-page"));
						
						// inspector
						initInspector();
						
					}
					
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
			
			
			
			
			event.preventDefault();
			
		});
		
		el.find(".button-view-results").click(function(event) {
			
			// view results
			displayResults(el, config, data, true);
			
			event.preventDefault();
			
		});
		
		// get controls
		getSheetControls(el, config, data, function() {
			
			// inspector
			initInspector();
			
		});
		
	};
	
	function retrieve(el, config, data) {
		
		var hackSession = ""; // = $.getTimestamp();
		
		
		el.empty();
		// html
		var s = $("<div class=\"theme-wrapper ui-wrapper-a\" data-theme=\"a\" data-form=\"ui-wrapper-a\">" +
				"<div class=\"theme-header ui-header-a\" data-theme=\"a\" data-form=\"ui-header-a\">" +
					"<div class=\"theme-logo ui-logo-a\" data-theme=\"a\" data-form=\"ui-logo-a\" id=\"logo\" style=\"display: none;\"></div>" +
				"</div>" +
				"<div class=\"theme-content ui-content-a\" data-theme=\"a\" data-form=\"ui-content-a\">" +
					"<div>" +
						"<div class=\"theme-page ui-page-a\" data-theme=\"a\" data-form=\"ui-page-a\"></div>" +
					"</div>" +
				"</div>" +
				"<div class=\"theme-footer ui-footer-a\" data-theme=\"a\" data-form=\"ui-footer-a\">Powered by <a title=\"Online Survey Tool\" href=\"http://www.inqwise.com\" target=\"_blank\">Inqwise</a></div>" +
			"</div>");
		
		s.appendTo(el);
		
		
		
		
		if(data.isRtl) {
			el.addClass("oposite-direction");
		}
		
		if(data.noStyle) { // != undefined) {
			// TODO:
		} else {
		
			// style
			var style = $("<style type=\"text/css\" />").appendTo(el);
			if (style[0].styleSheet !== undefined && style[0].styleSheet.cssText !== undefined) { 
			    style[0].styleSheet.cssText = data.styleBlock; //IE
			} else {
			    style.text(data.styleBlock);
			}
			
		}
		
		if(config.embed != undefined) {
			if(!config.embed) {
				document.title = data.title;
			}
		}
		
		// google analytics
		_gaq.push(["_set", "title", data.name]);
		_gaq.push(['_trackPageview']);
		
		/*
		// logo
		if(data.logoUrl) {
			el.find('#logo')
				.show()
				.append("<img src=\"" + data.logoUrl + "\" alt=\"" + data.name + "\"/>");
		}
		*/
		
		if(!data.isClosed) {
			
			
			if (data.answersAreFinished) {
				
				if(data.allowResults) {
					
					// show results
					displayResults(el, config, data);
					
				} else {
					
					// completed
					var s = $("<div>" +
							"<div data-form=\"ui-question-a\" data-theme=\"a\" class=\"theme-question ui-question-a\">" +
								"<div data-form=\"ui-question-heading-a\" data-theme=\"a\" class=\"theme-question-heading ui-question-heading-a\">" + data.title + "</div>" +
								"<div data-form=\"ui-question-content-a\" data-theme=\"a\" class=\"theme-question-content ui-question-content-a\">" + messages.completed + "</div>" +
							"</div>" +
							"<div class=\"theme-page-actions ui-page-actions-a\" data-theme=\"a\" data-form=\"ui-page-actions-a\" style=\"display: none\">" +
								"<a data-form=\"ui-button-a\" data-theme=\"a\" class=\"theme-button ui-button-a\" title=\"Sign Up FREE\" href=\"http://www.inqwise.com/en-us/register?ref=collector\" target=\"_blank\"><span>Sign Up FREE</span></a> It's quick and easy to get started." +
							"</div>" +
					"</div>").appendTo(el.find(".theme-page"));	
					
				}
				
			} else {
				
				// bind
				bind(el, config, data);
				
			}
			
			/*
			if (window.console) {
	    		window.console.log("This poll is open");
	    	}
			*/
			
		} else {
			
			// close message
			var s = $("<div>" +
					"<div data-form=\"ui-question-a\" data-theme=\"a\" class=\"theme-question ui-question-a\">" +
						"<div data-form=\"ui-question-heading-a\" data-theme=\"a\" class=\"theme-question-heading ui-question-heading-a\">" + data.title + "</div>" +
						"<div data-form=\"ui-question-content-a\" data-theme=\"a\" class=\"theme-question-content ui-question-content-a\">" + data.closeMessage.replace(/\n/gi, "<br/>").replace(/%0A/gi, "<br/>") + "</div>" +
					"</div>" +
					"<div class=\"theme-page-actions ui-page-actions-a\" data-theme=\"a\" data-form=\"ui-page-actions-a\" style=\"display: none;\">" +
						"<a data-form=\"ui-button-a\" data-theme=\"a\" class=\"theme-button ui-button-a\" title=\"Sign Up FREE\" href=\"http://www.inqwise.com/en-us/register?ref=collector\" target=\"_blank\"><span>Sign Up FREE</span></a> It's quick and easy to get started." +
					"</div>" +
			"</div>").appendTo(el.find(".theme-page"));
			
			/*
			if (window.console) {
	    		window.console.log("This poll is closed");
	    	}
	    	*/
			
		}
		
		
		// inspector
		initInspector();
		
		
	};
	
	var enableInspector = false;
	function initInspector() {
		if(enableInspector) {
			if(parent.editor != undefined) {
				parent.editor.initInspector();
			}
		}
	};
	
	function render(el, config, data) {
		
		if(config.guidTypeId == 1) {
			
			if(config.modeId == 1) {
				
				retrieve(el, config, data);
				
			} else if (config.modeId == 0) {
				
				if(!config.noStyle) {
					// collector preview
					$('#notification_box').show();
				}
				
				retrieve(el, config, data);
			}
			
		} else {
			
			// inspector
			if(config.noStyle) {
				
				/*
				if (!window.location.href.match(/localhost/gi)) {
				    document.domain = config.domain; // inqwise.com
				}
				*/
				
				enableInspector = true;
				
			}
			
			// draft
			if(!config.noStyle) {
				$('#notification_box').show();
			}
			
			retrieve(el, config, data);
			
			/*
			if (window.console) {
	    		window.console.log("draft");
	    	}
			*/
			
			
			
			
		}
	};
	
	function init() {
		
		
		
		// extends
		jQuery.extend({
			num : function(v) {
				var n = parseInt(v);
				return n == null || isNaN(n) ? 0 : n;
			},
			toUniqArr : function(arr) {
				var r = [], d = {};
				for ( var i = 0, l = arr.length; i < l; i++) {
					var id = arr[i];
					if (!done[id]) {
						done[id] = true;
						r.push(arr[i]);
					}
				}
				return r;
			},
			isArray : function(v) {
				return typeof (v) == 'object' && v != null
						&& typeof (v.length) == 'number';
			},
			isNullOrEmpty : function(o) {
				if (o == null && o == '')
					return true;
				return false
			},
			isNull : function(o) {
				if (o == null && o == undefined) {
					return true;
				}
				return false
			},
			addCommas : function(num) {
				num += '';
				x = num.split('.');
				x1 = x[0];
				x2 = x.length > 1 ? '.' + x[1] : '';
				var rgx = /(\d+)(\d{3})/;
				while (rgx.test(x1))
					x1 = x1.replace(rgx, '$1' + ',' + '$2');
				return x1 + x2;
			},
			getUrlParam : function(name) {
				name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
				var res = (new RegExp("[\\?&]" + name + "=([^&#]*)"))
						.exec(window.location.href);
				return res ? decodeURIComponent(res[1]) : '';
			},
			removeHTMLTags : function(s){
				s = s.replace(/&(lt|gt);/g, function (strMatch, p1){
				 	return (p1 == "lt")? "<" : ">";
				});
				return s.replace(/<\/?[^>]+(>|$)/g, "");
			},
			getTimestamp : Date.now || function() {
			  	return + (new Date).getTime();
			},
			pad : function (val, len) {
				val = String(val);
				len = len || 2;
				while (val.length < len) val = "0" + val;
				return val;
			}
		});
		
		/*
		(function($) {
			$.each( {
				focus : 'focusin',
				blur : 'focusout'
			}, function(original, fix) {
				$.event.special[fix] = {
					setup : function() {
						if ($.browser.msie)
							return false;
						this.addEventListener(original, $.event.special[fix].handler,
								true);
					},
					teardown : function() {
						if ($.browser.msie)
							return false;
						this.removeEventListener(original,
								$.event.special[fix].handler, true);
					},
					handler : function(e) {
						arguments[0] = $.event.fix(e);
						arguments[0].type = fix;
						return $.event.handle.apply(this, arguments);
					}
				};
			});
		})(jQuery);
		*/
		
		;(function($) {
			validator = function(givenOptions) {
					var successList = [];
					var disallowedList = [];
					var accepted = false;
					
					var options = $.extend({
						elements : [],
						errorList : null,
						errorTemplate : null,
						submitElement : null,
						accept : null,
						error : null
					}, givenOptions);
					
					var messages = {
						required: 'This field is required.',
						remote: 'Please fix this field.',
						email: 'Please enter a valid email address.',
						url: 'Please enter a valid URL.',
						date: 'Please enter a valid date.',
						dateISO: 'Please enter a valid date (ISO).',
						number: 'Please enter a valid number.',
						digits: 'Please enter only digits.',
						creditcard: 'Please enter a valid credit card number.',
						equalTo: 'Please enter the same value again.',
						maxlength: 'Please enter no more than {0} characters.',
						minlength: 'Please enter at least {0} characters.',
						rangelength: 'Please enter a value between {0} and {1} characters long.',
						range: 'Please enter a value between {0} and {1}.',
						max: 'Please enter a value less than or equal to {0}.',
						min: 'Please enter a value greater than or equal to {0}.',
						equal: 'The value is not equal {0}.'
					};
		
					var methods = function(method, el, value, pattern, parent) {
						switch (method) {
							case 'required' : 
								switch( el[0].nodeName.toLowerCase() ) {
									case 'select':
										// could be an array for select-multiple or a string, both are fine this way
										return (value != "0") && value.length > 0;
									case 'input':
										if ( checkable(el) )
											return getLength(value, el) > 0;
									default:
										return $.trim(value).length > 0;
								}
							case 'digits':
								return /^\d+$/.test(value);
							case 'number' :
								return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
							case 'email':
								return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value);
							case 'emailISO':
								return /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/i.test(value);
							case 'url' :
								return /^(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/i.test(value);
								//return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
							case 'minlength' :
								var p = pattern ? pattern : 0;
								if(getLength($.trim(value), el) >= p) {
									return true
								}
								return false;
							case 'maxlength' : 
								var p = pattern ? pattern : 0;
								if(getLength($.trim(value), el) <= p)
									return true
								return false;
							case 'min' : 
								if(parseFloat(value) >= parseFloat(pattern))
									return true;
								return false;
							case 'max' : 
								if(parseFloat(value) <= parseFloat(pattern)) 
									return true;
								return false;
							case 'rangelength' : 
								
								var length = getLength($.trim(value), el);
								var min = pattern[0] ? pattern[0] : 0;
								var max = pattern[1] ? pattern[1] : 0;
								if(( length >= min && length <= max ))
									return true;
								return false;
								
							case 'range' : 
								
								var min = pattern[0] ? pattern[0] : 0;
								var max = pattern[1] ? pattern[1] : 0;
								
								if(value >= min && value <= max)
									return true;
								return false;
								
							case 'date' :
								return !/Invalid|NaN/.test(new Date(value));
							case 'dateISO' :
								return /^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(value);
							case 'creditcard' :
								
								// accept only digits and dashes
								if (/[^0-9-]+/.test(value))
									return false;
								var nCheck = 0,
									nDigit = 0,
									bEven = false;
		
								value = value.replace(/\D/g, "");
		
								for (var n = value.length - 1; n >= 0; n--) {
									var cDigit = value.charAt(n);
									var nDigit = parseInt(cDigit, 10);
									if (bEven) {
										if ((nDigit *= 2) > 9)
											nDigit -= 9;
									}
									nCheck += nDigit;
									bEven = !bEven;
								}
		
								return (nCheck % 10) == 0;
							case 'equalTo' :
								if(parent) {
									// equal to element
									var target = parent;
									
									/*
			                   		 q.unbind('change')
			                   		 .bind('change', function () {
			                   		 */
									
									target
										.unbind('blur')
										.bind('blur', function() {
											// target blur
											validate(el);
										});
									
									if($.trim((value != null ? (value.replace(/\r/g, "")) : value)).length != 0) {
										if(value == target.val())
											return true;
									}
								} else {
									if(pattern != undefined) {
										if(pattern instanceof RegExp) {
											if($.trim((value != null ? (value.replace(/\r/g, "")) : value)).length != 0) {
												var exp = new RegExp(pattern);
												if(exp.test(value)) 
													return true;
											}
										} else if (typeof pattern == 'function') {
											if(pattern())
												return true;
										}
									}
								}
								return false;
								
							case 'notEqualTo' :
								if(parent) {
									// equal to element
									var target = parent;
									
									/*
			                   		 q.unbind('change')
			                   		 .bind('change', function () {
			                   		 */
									
									target
										.unbind('blur')
										.bind('blur', function() {
											// target blur
											validate(el);
										});
									
									if($.trim((value != null ? (value.replace(/\r/g, "")) : value)).length != 0) {
										if(value != target.val())
											return true;
									}
								} else {
									if(pattern != undefined) {
										if(pattern instanceof RegExp) {
											if($.trim((value != null ? (value.replace(/\r/g, "")) : value)).length != 0) {
												var exp = new RegExp(pattern);
												if(!exp.test(value)) 
													return true;
											}
										} else if(typeof pattern == 'function') {
											if(!pattern())
												return true;
										}
									}
								}
								return false;
								
							default:
								// custom methods
								return (value == pattern);
						}
					};
					
					var isExist = function (name) {
						if (successList.length > 0) {
							for (var i = 0; i < successList.length; i++) {
								if (successList[i] == name) {
									return true;
								}
							}
						}
						return false;
					};
		
					var removeElement = function (name) {
						if (successList.length > 0) {
							for (var i = 0; i < successList.length; i++) {
								if (successList[i] == name) {
									successList.remove(i);
									break;
								}
							}
						}
					};
					
					var checkable = function( element ) {
						return /radio|checkbox/i.test(element[0].type);
					};
					
					var clean = function( selector ) {
						return $( selector )[0];
					};
					
					var getLength = function(value, element) {
						switch( element[0].nodeName.toLowerCase() ) {
						case 'select':
							return $("option:selected", element).length;
						case 'input':
							if( checkable( element) )
								return findByName(element[0].name).filter(':checked').length;
							
						}
						return value.length;
					};
		
					
					
					var lastActive = null;
					var lastElement = null;
					var elements = options.elements;
		
					var groups = [];
					
					var getGroup = function(groupName) {
						for(var i = 0; i < groups.length; i++) {
							if(groups[i].groupName == groupName) {
								return groups[i];
							}
						}
						return null;
					};
					
					var getElement = function(el) {
						for(var i = 0; i < elements.length; i++) {
							if(elements[i].element) {
								if(elements[i].element.attr('name') == el.attr('name')) {
									return elements[i];
								}
							}
						}
						return null;
					};
		
					var findByName = function(name) {
						// select elements by name
						return $(document.getElementsByName(name)).map(function(index, element) {
							return element.name == name && element || null;
						});
					};
					
					// start here
					if (elements.length > 0) {
						
						for(var i = 0; i < elements.length; i++) {
							
							// handling names
							if(elements[i].name) {
								
								var name = elements[i].name;
								var el = findByName(name);
								
								elements[i].element = $(el);
								elements[i]
									.element
									.removeClass('invalid valid predefined')
									.validateDelegate(":text, :password, :file, select, textarea", "focusin focusout keyup", function(event) {
										delegate($(this), event.type);
									})
									.validateDelegate(":radio, :checkbox, select, option", "click change", function(event) {				
										delegate($(this), event.type); // .replace(/^validate/, "")
									});
								
								// errors
								var errorLabel = null;
								if(options.errorTemplate) {
									errorLabel = options.errorTemplate()
										.addClass('status validator-' + name)
										.hide();
								} else {
									errorLabel = $("<label class=\"status validator-" + name + "\" style=\"display: none\"></label>");
								}
								
								// errorList
								if(options.errorList) {
									if(options.errorList.placeholder) {
										if(!isExist(name)) {
											if(options.errorList.errorTemplate) {
												options.errorList.errorTemplate()
													.addClass('status validator-' + name)
													.hide()
													.appendTo(options.errorList.placeholder);
											} else {
												errorLabel
													.appendTo(options.errorList.placeholder);
											}
										}
									}
								} else {
									if(!isExist(name)) {
										if(elements[i].status) {
											elements[i].status
												.addClass('status validator-' + name)
												.hide();
										} else {
											if(elements[i].showError != undefined) {
												if(elements[i].showError) {
													elements[i].element.after(errorLabel);
												}
											} else {
												elements[i].element.after(errorLabel);
											}
										}
									}
								}
								
								//
								successList.push(name);
								  
							}
							
							// handling groups
							if(elements[i].group) {
								
								var groupName = null;
								var lastGroupElement = null;
								
								elements[i].group = findByName(elements[i].group);
								elements[i].group.each(function(y, el) {
									
									// find last group element
									if(y == (elements[i].group.length - 1)) {
										lastGroupElement = $(el);
										groupName = lastGroupElement.attr('name');
									}
									
									// helper for get element from some group by groupName
									// check performace
									groups.push({ 
										groupName : groupName, 
										group : elements[i].group, 
										rules : elements[i].rules, 
										error : elements[i].error, 
										success : elements[i].success 
									});
									
									// for each element of the group --> check all group
									$(el)
										.removeClass('invalid valid predefined')
										.validateDelegate(":text, :password, :file, select, textarea", "focusin focusout keyup", function(event) {
											var group = getGroup($(this).attr('name'));
											delegate($(this), event.type, group);
										})
										.validateDelegate(":radio, :checkbox, select, option", "click change", function(event) {				
											var group = getGroup($(this).attr('name'));
											delegate($(this), event.type, group);
										});
								});
								
								// push to success list
								successList.push(groupName);
								
								
								// errors
								var errorLabel = null;
								if(options.errorTemplate) {
									errorLabel = options.errorTemplate()
										.addClass('status validator-' + groupName)
										.hide();
								} else {
									errorLabel = $("<label class=\"status validator-" + groupName + "\" style=\"display: none\"></label>");
								}
								
								// errorList
								if(options.errorList) {
									if(options.errorList.placeholder) {
										if(options.errorList.errorTemplate) {
											options.errorList.errorTemplate()
												.addClass('status validator-' + groupName)
												.hide()
												.appendTo(options.errorList.placeholder);
										} else {
											errorLabel
												.appendTo(options.errorList.placeholder);
										}
									}
								} else {
									if(elements[i].status) {
										elements[i].status
											.addClass('status validator-' + groupName)
											.hide();
									} else {
										if(elements[i].showError != undefined) {
											if(elements[i].showError) {
												lastGroupElement.after(errorLabel);
											}
										} else {
											lastGroupElement.after(errorLabel);
										}
									}
								}
							}
							
						}
					}
					
					var validateForm = function () {
		
						disallowedList = [];
						
						
						
						
						if (elements.length > 0) {
							for(var i = 0; i < elements.length; i++) {
							
								
								// check elements
								if(elements[i].element) {
								
									var t = elements[i];
									var v = true;
									var el = t.element;
									
									if(t.validate) {
										if(typeof t.validate == 'function') {
											v = t.validate();
										} else {
											v = t.validate();
										}
									}
									
									if(v) {
										el.addClass("predefined");
									}
									
									validate(el);
									
								}
								
								// check groups
								if(elements[i].group) {
								
									elements[i].group.addClass("predefined");
									
									validateGroup(elements[i])
									
								}
								
							}
						}
						
					};
					
					// validate group
					var validateGroup = function(group) {
						
						var f = group;
						if(f) {
						
							var group = f.group;
							var rules = f.rules;
							
							
							
							var isCheckable = false;
							var groupName = null;
							
							var k = 0;
							var r = 0;
							
							
							
							
							var check = function(el) {
								
								var u = 0;
								for(var i = 0; i < rules.length; i++) {
									if(methods(rules[i].method, el, el.val(), rules[i].pattern, rules[i].element)) {
										// valid
										u++;
									} else {
										break;
									}
								}
								
								if(isCheckable) {
									if(u != rules.length) {
										
										k++;
										r = u;
									}
									
								} else {
									// others
									if(u != rules.length) {
										
										//el.css({'background':'red'});
										
										el
											.addClass('invalid')
											.removeClass('valid');
										
										k++;
										
										r = u;
										
									} else {
										
										//el.css({'background':'white'});
										
										el
											.removeClass('invalid')
											.addClass('valid');
										
									}
								
								}
								
							};
						
							if(rules) {
		
								group.each(function(y, el) {
									if(y == (group.length - 1)) {
										groupName = $(el).attr('name');
									}
									
									if(checkable($(el))) {
										if(isCheckable != true)
											isCheckable = true;
									}
									
									check($(el));
									
									
									
								});
								
								var errorLabel = $('.validator-' + groupName);
								
								if(isCheckable) {
									// radio, checkbox
									if(k == group.length) {
									
										// error
										if (isExist(groupName) != true) 
												successList.push(groupName);
											
										disallowedList.push(groupName);
										
										
										group
											.addClass('invalid')
											.removeClass('valid');
											
										
										// message
										var message = (rules[r].message ? rules[r].message : messages[rules[r].method]);
										if(rules[r].pattern) {
											var pattern = rules[r].pattern;
											if ( pattern.constructor != Array ) {
												pattern = [ pattern ];
											}
											$.each(pattern, function(i, n) {
												message = message.replace(new RegExp("\\{" + i + "\\}", "g"), n);
											});
										}
										
										
										// fix for showError
										if(!(f.showError != undefined && !f.showError)) {
											
												errorLabel
													.text(message)
													.show();
												
										}
										
										// error callback
										if(f.error) {
											if(typeof f.error == 'function')
												f.error(message);
										}
										
									} else {
									
										// succ
										if (isExist(groupName) == true)
												removeElement(groupName);
									
									
										group
											.removeClass('invalid')
											.addClass('valid');
									
										// success
										
										
										errorLabel
											.text('')
											.hide();
											
										
										if(f.success) {
											if(typeof f.success == 'function')
												f.success();
										}
										
										
									}
									
								} else {
								
									// text, textarea, select	
									if(k != 0) {
									
										if (isExist(groupName) != true) 
												successList.push(groupName);
											
										disallowedList.push(groupName);
										
										// message
										var message = (rules[r].message ? rules[r].message : messages[rules[r].method]);
										if(rules[r].pattern) {
											var pattern = rules[r].pattern;
											if ( pattern.constructor != Array ) {
												pattern = [ pattern ];
											}
											$.each(pattern, function(i, n) {
												message = message.replace(new RegExp("\\{" + i + "\\}", "g"), n);
											});
										}
										
										// fix for showError
										if(!(f.showError != undefined && !f.showError)) {
											
											errorLabel
												.text(message)
												.show();
																						
										}
										
										// error callback
										if(f.error) {
											if(typeof f.error == 'function')
												f.error(message);
										}
												
									} else {
									
										if (isExist(groupName) == true)
												removeElement(groupName);
									
									
										// success
										
										errorLabel
											.text('')
											.hide();
												
										
										if(f.success) {
											if(typeof f.success == 'function')
												f.success();
										}
									}
								}
							}
						}
					};
					
					// validate element
					var validate = function(element) {
						
						var t = getElement(element);
						
						if(t) {
							
							var el = t.element;
							var name = el.attr('name');
							var rules = t.rules;
							var v = true;
							
							
							
							if(t.validate) {
								if(typeof t.validate == 'function') {
									v = t.validate();
								} else {
									v = t.validate();
								}
							} 
							
							var errorLabel = $('.validator-' + name);
							
							if(v) {
							
							
							
								
								// seek rules
								if(rules) {
								
									var u = 0;
									var G = function() {
										for(var y = 0; y < rules.length; y++) {
											if(methods(rules[y].method, el, el.val(), rules[y].pattern, rules[y].element)) {
												// hide error
												u++;
											} else {
												break;
											}
										}
									}
									G();
									
									if(u != rules.length) {
										
										// show error
										if (isExist(name) != true) 
											successList.push(name);
										
										disallowedList.push(name);	
										
										//el.css({'background':'red'});
										el
											.addClass('invalid')
											.removeClass('valid');
										
										
										
										
										
										var message = ( rules[u].message ? rules[u].message : messages[rules[u].method] );
										
										if(rules[u].pattern) {
											var pattern = rules[u].pattern;
											if ( pattern.constructor != Array )
												pattern = [ pattern ];
											
											$.each(pattern, function(i, n) {
												message = message.replace(new RegExp("\\{" + i + "\\}", "g"), n);
											});
										}
											
										// fix for show error
										if(!(t.showError != undefined 
											&& !t.showError)) {
																					
											errorLabel	
												.text(message)
												.show();
												
										}
										
										if(t.error) {
											if(typeof t.error == 'function')
												t.error(message);
										}
										
									} else {
										
										if (isExist(name) == true)
											removeElement(name);
											
										//el.css({'background':'white'});
										el
											.removeClass('invalid')
											.addClass('valid');
											
											
										errorLabel
											.text('')
											.hide();
											
											
										if(t.success) {
											if(typeof t.success == 'function')
												t.success();
										}
									}
								}
							
							} else {
							
								if (isExist(name) == true)
									removeElement(name);
								
								el
									.removeClass('invalid')
									.addClass('valid');
								
								
								errorLabel
									.text('')
									.hide();
								
											
							}
							
						}
					};
					
					// delegate events
					var delegate = function(element, eventType, group) {	
						
						if(eventType == "focusin") {
							if(lastActive != null && lastActive != element) {
								//lastActive.css({'background' : 'white'});
							}
							lastActive = element;
							
							if(element.hasClass('predefined')) {
								if(group) {
									validateGroup(group)
								} else {
									validate(element);
								}
							}
						}
						
						if(eventType == "focusout") {
							if(element.val().length != 0) {
								element.addClass("predefined");
							}
							
							if(element.hasClass('predefined')) {
								if(group) {
									validateGroup(group)
								} else {
									validate(element);
								}
							}
						}
						
						if(eventType == "keyup") {
							if(element.hasClass('predefined')) {
								if(group) {
									validateGroup(group)
								} else {
									validate(element);
								}
							}
						}
						
						// click on selects, radiobuttons and checkboxes
						if(eventType == "click" || eventType == "change") {
							if(element.hasClass('predefined')) {
								if(group) {
									validateGroup(group)
								} else {
									validate(element);
								}
							}
						}
					};
					
					// submit element event
					if (options.submitElement != undefined) {
						options.submitElement
						.unbind('click')
						.click(function (event) {
							checkAll();
							event.preventDefault();
						});
					}
					
					var checkAll = function(callback) {
					
						// validate
						validateForm();
						
						if (disallowedList.length != 0) {
							disallowedList.reverse();
							
							// set focus on the first invalid and not hidden element
							findByName(disallowedList[disallowedList.length - 1])
								.filter('.invalid:first')
								.filter(':visible')
								.focus();
								
							if(options.errorList) {
								if(options.errorList.placeholder) {
									if(!options.errorList.placeholder.is(':visible'))
										options.errorList.placeholder.show();
								}
							}
							
							if(options.error != undefined 
								&& typeof options.error == 'function')
								options.error();
							
							// for external callback
							if(callback) {
								if(callback.error != undefined 
									&& typeof callback.error == 'function')
									callback.error();
							}
						}
						
						if (options.accept != undefined 
							&& typeof options.accept == 'function') {
							if (successList.length == 0)
								options.accept();
						}
						
						// for external callback
						if(callback) {
							if(callback.accept != undefined 
								&& typeof callback.accept == 'function') {
								if (successList.length == 0)
									callback.accept();
							}
						}
					};
					
					return {
						validate: function(callback) {
							checkAll(callback);
						}
					};
					
			};
		})(jQuery);

		
		
		// provides cross-browser focusin and focusout events
		// IE has native support, in other browsers, use event caputuring (neither bubbles)

		// provides delegate(type: String, delegate: Selector, handler: Callback) plugin for easier event delegation
		// handler is only called when $(event.target).is(delegate), in the scope of the jquery-object for event.target
		;(function($) {
			// only implement if not provided by jQuery core (since 1.4)
			// TODO verify if jQuery 1.4's implementation is compatible with older jQuery special-event APIs
			if (!jQuery.event.special.focusin && !jQuery.event.special.focusout && document.addEventListener) {
				$.each({
					focus: 'focusin',
					blur: 'focusout'
				}, function( original, fix ){
					
					$.event.special[fix] = {
						setup:function() {
							this.addEventListener( original, handler, true );
						},
						teardown:function() {
							this.removeEventListener( original, handler, true );
						},
						handler: function(e) {
							arguments[0] = $.event.fix(e);
							arguments[0].type = fix;
							return $.event.handle.apply(this, arguments);
						}
					};
					function handler(e) {
						e = $.event.fix(e);
						e.type = fix;
						return $.event.handle.call(this, e);
					}
				});
			};
			$.extend($.fn, {
				validateDelegate: function(delegate, type, handler) {
					return this.bind(type, function(event) {
						var target = $(event.target);
						if (target.is(delegate)) {
							return handler.apply(target, arguments);
						}
					});
				}
			});
		})(jQuery);
		
		Array.prototype.remove = function (from, to) {
		    if ($.isArray(this)) {
		        var rest = this.slice((to || from) + 1 || this.length);
		        this.length = from < 0 ? this.length + from : from;
		        return this.push.apply(this, rest);
		    }
		};
		
		
		(function () {
		    var p = {
		        init: function () {
		            this.browser = this.browser();
		            this.screen = this._screen();
		            this.os = this.os();
		        },
		        timeZone: function () {
		            var f = function (a) {
		                var b = parseInt(a);
		                a -= parseInt(a);
		                a *= 60;
		                var c = parseInt(a);
		                a -= parseInt(a);
		                a *= 60;
		                var d = parseInt(a);
		                var e = b;
		                if (b == 0) {
		                    e = "00";
		                } else if (b > 0) {
		                    e = (b < 10) ? "+0" + b : "+" + b;
		                } else {
		                    e = (b > -10) ? "-0" + Math.abs(b) : b;
		                }
		                c = (c < 10) ? "0" + c : c;
		                return e + ":" + c;
		            };
		            var g = new Date();
		            var h = new Date(g.getFullYear(), 0, 1, 0, 0, 0, 0);
		            var i = new Date(g.getFullYear(), 6, 1, 0, 0, 0, 0);
		            var j = h.toGMTString();
		            var k = new Date(j.substring(0, j.lastIndexOf(" ") - 1));
		            j = i.toGMTString();
		            var l = new Date(j.substring(0, j.lastIndexOf(" ") - 1));
		            var m = (h - k) / (1000 * 60 * 60);
		            var n = (i - l) / (1000 * 60 * 60);
		            if (m == n) {} else {
		                var o = m - n;
		                if (o >= 0) 
		                	m = n;
		            }
		            return f(m);
		        },
		        os: function () {
		            var a = "Unknown OS";
		            var b = window.navigator.platform;
		            var c = navigator.userAgent;
		            if (c.indexOf("Win") != -1) {
		                if ((c.indexOf("Windows NT 5.1") != -1) || (c.indexOf("Windows XP") != -1)) a = "Win XP";
		                else if ((c.indexOf("Windows NT 7.0") != -1) || (c.indexOf("Windows NT 6.1") != -1)) a = "Win 7/Server 2008";
		                else if ((c.indexOf("Windows NT 6.0") != -1)) a = "Win Vista/Server 2008";
		                else if (c.indexOf("Windows ME") != -1) a = "Win ME";
		                else if ((c.indexOf("Windows NT 4.0") != -1) || (c.indexOf("WinNT4.0") != -1) || (c.indexOf("WinNT") != -1)) a = "Win NT";
		                else if ((c.indexOf("Windows NT 5.2") != -1)) a = "Win Server 2003";
		                else if ((c.indexOf("Windows NT 5.0") != -1) || (c.indexOf("Windows 2000") != -1)) a = "Win 2000";
		                else if ((c.indexOf("Windows 98") != -1) || (c.indexOf("Win98") != -1)) a = "Win 98";
		                else if ((c.indexOf("Windows 95") != -1) || (c.indexOf("Win95") != -1) || (c.indexOf("Windows_95") != -1)) a = "Win 95";
		                else if ((c.indexOf("Win16") != -1)) a = "Win 3.1";
		                else a = "Win Ver. Unknown";
		                if ((c.indexOf("WOW64") != -1) || (c.indexOf("x64") != -1) || (c.indexOf("Win64") != -1) || (c.indexOf("IA64") != -1)) a = a + " (x64)";
		                else a = a + " (x32)"
		            } else if (c.indexOf("Mac") != -1) a = "MacOS";
		            else if (c.indexOf("X11") != -1) a = "UNIX";
		            else if (c.indexOf("Linux") != -1) a = "Linux";
		            return {
		                name: a,
		                version: null,
		                platform: b,
		                cpuClass: ((navigator.cpuClass) ? navigator.cpuClass : ""),
		                oscpu: window.navigator.oscpu,
		                isOnline: window.navigator.onLine,
		                systemLanguage: ((window.navigator.systemLanguage != undefined) ? window.navigator.systemLanguage : window.navigator.language),
		                timeZone: this.timeZone()
		            }
		        },
		        _screen: function () {
		            return {
		                width: screen.width,
		                height: screen.height,
		                color: ((screen.pixelDepth != undefined) ? screen.pixelDepth : screen.colorDepth)
		            }
		        },
				searchString: function (d) {
					for (var i = 0; i < d.length; i++)	{
						var s = d[i].string;
						var p = d[i].prop;
						this.versionSearchString = d[i].versionSearch || d[i].identity;
						if (s) {
							if (s.indexOf(d[i].subString) != -1)
								return d[i].identity;
						}
						else if (p)
							return d[i].identity;
					}
				},
				searchVersion : function(d) {
					var i = d.indexOf(this.versionSearchString);
					if (i == -1) return;
					return parseFloat(d.substring(i+this.versionSearchString.length+1));
				},
				dataBrowser: [
					{
						string: navigator.userAgent,
						subString: "Chrome",
						identity: "Chrome"
					},
					{ 	string: navigator.userAgent,
						subString: "OmniWeb",
						versionSearch: "OmniWeb/",
						identity: "OmniWeb"
					},
					{
						string: navigator.vendor,
						subString: "Apple",
						identity: "Safari",
						versionSearch: "Version"
					},
					{
						prop: window.opera,
						identity: "Opera"
					},
					{
						string: navigator.vendor,
						subString: "iCab",
						identity: "iCab"
					},
					{
						string: navigator.vendor,
						subString: "KDE",
						identity: "Konqueror"
					},
					{
						string: navigator.userAgent,
						subString: "Firefox",
						identity: "Firefox"
					},
					{
						string: navigator.vendor,
						subString: "Camino",
						identity: "Camino"
					},
					{	
						string: navigator.userAgent,
						subString: "Netscape",
						identity: "Netscape"
					},
					{
						string: navigator.userAgent,
						subString: "MSIE",
						identity: "Explorer",
						versionSearch: "MSIE"
					},
					{
						string: navigator.userAgent,
						subString: "Gecko",
						identity: "Mozilla",
						versionSearch: "rv"
					},
					{ 		// for older Netscapes (4-)
						string: navigator.userAgent,
						subString: "Mozilla",
						identity: "Netscape",
						versionSearch: "Mozilla"
					}
				],
		        browser: function () {
		            var i = function () {
		                var a = false;
		                if (navigator.plugins && navigator.plugins.length) {
		                    for (x = 0; x < navigator.plugins.length; x++) {
		                        if (navigator.plugins[x].name.indexOf('Java(TM)') != -1) {
		                            a = true;
		                            break;
		                        }
		                    }
		                }
		                if (navigator.javaEnabled()) {
		                    a = true;
		                }
		                return {
		                    isInstalled: a
		                }
		            };
		            var j = function () {
		                var c = false;
		                u = 10;
		                h = '0.0';
		                if (navigator.plugins && navigator.plugins.length) {
		                    for (x = 0; x < navigator.plugins.length; x++) {
		                        if (navigator.plugins[x].name.indexOf('Shockwave Flash') != -1) {
		                            h = navigator.plugins[x].description.split('Shockwave Flash ')[1];
		                            c = true;
		                            break;
		                        }
		                    }
		                } else if (window.ActiveXObject) {
		                    for (x = 2; x <= u; x++) {
		                        try {
		                            oFlash = eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash." + x + "');");
		                            if (oFlash) {
		                                c = true;
		                                h = x + '.0';
		                            }
		                        } catch (e) {}
		                    }
		                }
		                return {
		                    isInstalled: c,
		                    version: h
		                }
		            };
		            return {
						name : this.searchString(this.dataBrowser) || "An unknown browser",
		                appName: window.navigator.appName,
		                vendor: ((window.navigator.vendor) ? window.navigator.vendor : ""),
		                appCodeName: window.navigator.appCodeName,
		                appVersion: window.navigator.appVersion,
		                product: window.navigator.product,
		                buildNumber: ((window.opera) ? window.opera.buildNumber() : ((window.navigator.productSub) ? window.navigator.productSub : "")),
		                userAgent: window.navigator.userAgent,
		                cookieEnabled: window.navigator.cookieEnabled,
		                version: this.searchVersion(navigator.userAgent)
								|| this.searchVersion(navigator.appVersion)
								|| "an unknown version",
		                java: i(),
		                flash: j()
		            }
		        }
		    };
		    p.init();
		    window.$.client = {
		        os: p.os,
		        browser: p.browser,
		        screen: p.screen
		    }
		})();
		
		// ready
		$(document).ready(function($) {
			
			var target = /(^https?:\/\/[^\/]*(\:\d+)?)?\/.*scripts\/widget\/1.0.3\/poll\.js/; 
			findScript(target).each(function() {
				
				var config = evalConfig($(this).html());
				var el = $("<div class=\"theme-body ui-body-a\" data-theme=\"a\" data-form=\"ui-body-a\"/>").addClass("poll poll-" + config.guid);
				var that = this;
				$(that).before(el).remove();
				
				_el = el;
				
				fromConfig(config, function(config, data) {
					// do render
					render(el, config, data);
				});
				
				
				
			});
			
		});
		
		
	}

})();
var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-2521304-12']);
//_gaq.push(['_setDomainName', 'inqwise.com']);
//_gaq.push(['_trackPageview']);

(function() {
	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();