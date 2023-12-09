/**
 * jQuery Validator Plugin 1.2.6
 *
 * http://.com/jquery-plugins/jquery-plugin-validator/
 * http://docs.jquery.com/plugins/validator
 *
 * Copyright (c) 2011 Basil Goldman
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
 
;(function ($) {
    $.extend({
		validator : function(givenOptions) {
			
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
								return value && value.length > 0;
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
						if(value >= pattern)
							return true;
						return false;
					case 'max' : 
						if(value <= pattern) 
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
							if(pattern != undefined 
								&& typeof pattern == 'function') {
								// callback
								if(pattern())
									return true;
							} else {				
								// equal to pattern 
								// validate by pattern value
								if($.trim((value != null ? (value.replace(/\r/g, "")) : value)).length != 0) {
									// regex
									var exp = new RegExp(pattern);
									if(exp.test(value)) 
										return true;
								}
							}
						}
						return false;
					case 'notEqualTo' :
						if(parent) {
							// equal to element
							var target = parent;
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
							if(pattern != undefined 
								&& typeof pattern == 'function') {
								
								if(!pattern())
									return true;
								
							} else {				
								// equal to pattern
								// validate by pattern value
								if($.trim((value != null ? (value.replace(/\r/g, "")) : value)).length != 0) {
									// regex
									var exp = new RegExp(pattern);
									if(exp.test(value)) 
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
						if(elements[i].element.attr('id') == el.attr('id')) {
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
			
			if (elements.length > 0) {
				for(var i = 0; i < elements.length; i++) {
					
					// handling elements
					if(elements[i].element) {
					
						var name = elements[i].element.attr('name');
						
						elements[i]
							.element
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
									elements[i].element.after(errorLabel);
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
								lastGroupElement.after(errorLabel);
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
				options.submitElement.click(function () {
					
					checkAll();
					
				});
			}
			
			var checkAll = function() {
			
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
					
				}
				
				if (options.accept != undefined 
					&& typeof options.accept == 'function') {
					if (successList.length == 0)
						options.accept();
					
				}
			};
			
			return {
				validate: function() {
					checkAll();
				}
			};
			
		}
	})
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
