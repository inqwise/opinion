


/*
elements : [
		{ 
			element : $('#input_year'), 
			rules : [ 
				{ method : 'required', message : "", status : null }, 
				{ method : 'digits', message : "", status : null }, 
				{ method : 'min', pattern : 4, message : "", status: null } 
			] 
		},
		{ 
			element : $('#input_password_test'), 
			rules : [ 
				{ method : 'required', message : "", status : null }, 
				{ method : 'rangelength', pattern : [6,12], message : "", status : null }
			] 
		},
		{
			element : $('#input_confirm_password_test'),
			rules : [
				{ method : 'required', message : "", status : null }, 
				{ method : 'rangelength', pattern : [6,12], message : "", status : null },
				{ method : 'equalto', pattern : $('#input_password_test'), message : "" } 
			]
		}
	],
*/



jQuery.fn.validator = function (givenOptions) {
    if (arguments.length == 0) return [];
    var args = arguments[0] || {};
    var successList = [];
    var accepted = false;
	
	var options = $.extend({
		elements : []
	}, givenOptions);
	
	

    var messages = {
        required: 'This field is required.',
        remote: 'Please fix this field.',
        email: 'Please enter a valid email address.',
        url: 'Please enter a valid URL.',
        date: 'Please enter a valid date.',
        dateISO: 'Please enter a valid date (ISO).',
        dateDE: 'Bitte geben Sie ein gültiges Datum ein.',
        number: 'Please enter a valid number.',
        numberDE: 'Bitte geben Sie eine Nummer ein.',
        digits: 'Please enter only digits.',
        creditcard: 'Please enter a valid credit card number.',
        equalTo: 'Please enter the same value again.',
        accept: 'Please enter a value with a valid extension.',
        maxlength: 'Please enter no more than {0} characters.',
        minlength: 'Please enter at least {0} characters.',
        rangelength: 'Please enter a value between {0} and {1} characters long.',
        range: 'Please enter a value between {0} and {1}.',
        max: 'Please enter a value less than or equal to {0}.',
        min: 'Please enter a value greater than or equal to {0}.',
        password: 'Must be between 6-12 characters.',
        notEqual: 'The value is not equal {0}'
    };

    var validateByMethod = function (method, value, errorElement, element) {
        switch (method) {
            case 'digits':
                if ((/^\d+$/.test(value)) != true) {
                    // remove items from success list
                    if (isExist(element) != true) successList.push(element);
                    if (errorElement != null) {
                        errorElement
                            .children()
                            .html(String((args.messages != undefined ? (args.messages.digits != undefined ? args.messages.digits : messages.digits) : messages.digits)));
                        errorElement.show();
                        
                        disallowedList.push(element);
                    }
                } else { errorElement.hide(); }

                break;
			case 'number' :
				if((/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value)) != true) {
					// remove items from success list
                    if (isExist(element) != true) successList.push(element);
                    if (errorElement != null) {
                        errorElement
                            .children()
                            .html(String((args.messages ? (args.messages.number != undefined ? args.messages.number : messages.number) : messages.number)));
						
                        errorElement.show();
                        
                        disallowedList.push(element);
                    }
				} else { errorElement.hide(); }
				
				break;
            case 'email':
                if ((/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value)) != true) {
                    // remove items from success list
                    if (isExist(element) != true) successList.push(element);
                    if (errorElement != null) {
                        errorElement
                            .children()
                            .html(String((args.messages ? (args.messages.email != undefined ? args.messages.email : messages.email) : messages.email)));
						
                        errorElement.show();
                        
                        disallowedList.push(element);
                    }
                } else { errorElement.hide(); }

                break;
			case 'url' :
				if((/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value)) != true) {
					// remove items from success list
                    if (isExist(element) != true) successList.push(element);
                    if (errorElement != null) {
                        errorElement
                            .children()
                            .html(String((args.messages ? (args.messages.url != undefined ? args.messages.url : messages.url) : messages.url)));
						
                        errorElement.show();
                        
                        disallowedList.push(element);
                    }
				} else { errorElement.hide(); }
				
				break;
            case 'password':
                if ((value.length < 6) || (value.length > 12)) {
                    // remove items from success list
                    if (isExist(element) != true) successList.push(element);
                    if (errorElement != null) {
                        errorElement
                            .children()
                            .html(String((args.messages ? (args.messages.password != undefined ? args.messages.password : messages.password) : messages.password)));
						
                        errorElement.show();
                        
                        disallowedList.push(element);
                    }
                } else { errorElement.hide(); }
                break;
            case 'notequal':
                if (value.toLowerCase() == element.attr('pattern').toLowerCase()) {
                    if (isExist(element) != true)
                        successList.push(element);
                    if (errorElement != null) {
					
                        errorElement
                            .children()
                            .html(String((args.messages != undefined ? (args.messages.notEqual != undefined ? args.messages.notEqual : messages.notEqual) : messages.notEqual)));
						
                        errorElement.show();
                        
                        disallowedList.push(element);
                    }
                } else { errorElement.hide(); }
                break;
				
			case 'minlength' :
			
				break;
			case 'maxlength' : 
				
				break;
			case 'rangelength' : 
				var length = getLength($.trim(value), element);
				var pattern = element.attr('pattern').toLowerCase().split(',');
				if(!( length >= parseInt(pattern[0]) && length <= parseInt(pattern[1]) )) {
					if (isExist(element) != true)
                        successList.push(element);
                    if (errorElement != null) {
                        errorElement
                            .children()
                            .html(String((args.messages != undefined ? (args.messages.rangelength != undefined ? args.messages.rangelength : messages.rangelength) : messages.rangelength)));
						
                        errorElement.show();
                        
                        disallowedList.push(element);
                    }
				} else { 
					
					errorElement.hide(); 
					
					
					var equalTo = element.attr('equalto') != undefined ? element.attr('equalto') : null;
					if(equalTo) {
						var target = $('#' + equalTo);
						
						target
							.unbind('blur')
							.bind('blur', function() {
								// target blur
								validateByMethod(method, element.val().replace(/\r/g, ""), errorElement, element);
							});
						
						if(element.val() == target.val()) {
							errorElement.hide(); 
						} else {
							if (isExist(element) != true)
								successList.push(element);
							if (errorElement != null) {
								errorElement
									.children()
									.html(String((args.messages != undefined ? (args.messages.equalTo != undefined ? args.messages.equalTo : messages.equalTo) : messages.equalTo)));
								
								errorElement.show();
								
								disallowedList.push(element);
							}
							
						}
					}
					
					
					
				}
				break;
			case 'min' : 
			
				break;
			case 'max' : 
			
				break;
			case 'range' : 
				//if(( value >= param[0] && value <= param[1] ))
				break;
			case 'date' :
				
				break;
			case 'dateISO' :
				
				break;
			case 'creditcard' :
				
				
				/*
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
				*/
			
			
			
			
				break;
			case 'equalto' :
				
				break;
            default:
        }
    };

    var removeHTMLTags = function (strInputCode) {
        strInputCode = strInputCode.replace(/&(lt|gt);/g, function (strMatch, p1) {
            return (p1 == "lt") ? "<" : ">";
        });
        return strInputCode.replace(/<\/?[^>]+(>|$)/g, "");
    };

    var isExist = function (obj) {
        if (successList.length > 0) {
            for (var i = 0; i < successList.length; i++) {
                if (successList[i].attr('id') == obj.attr('id')) return true;
            }
        }

        return false;
    };

    var removeElement = function (obj) {
        if (successList.length > 0) {
            for (var i = 0; i < successList.length; i++) {
                if (successList[i].attr('id') == obj.attr('id')) successList.remove(i);
            }
        }
    };
	
	var checkable = function( element ) {
		return /radio|checkbox/i.test(element[0].type);
	};
	
	var getLength = function(value, element) {
		switch( element[0].nodeName.toLowerCase() ) {
		case 'select':
			return $("option:selected", element).length;
		case 'input':
			/*
			if( checkable( element) )
				return this.findByName(element.name).filter(':checked').length;
			*/
		}
		return value.length;
	};

    var elements = $(this);
	var lastActive = null;
	var lastElement = null;
	
	
    if (elements.length > 0) {
        elements.each(function (i, el) {
            if ($(el).attr('validation') != undefined) {
                var errorTooltip = $("<span class=\"field-error-message cell\" id=\"field_error_message_" + $(el).attr('id') + "\" ><span></span></span>");
                
                $(el).after($(errorTooltip));
                $(errorTooltip).hide();

                successList.push($(el));
            }
        });
    }


    var disallowedList = [];
	

    var validateForm = function () {

        disallowedList = [];
		elements.each(function (i, el) {
		
			var element = $(el);
			var errorElement = $('#field_error_message_' + element.attr('id'));

			if (element.attr("type") == "checkbox"
				|| element.attr("type") == "radio") {

				if (element.is(':checked')) {
					if (isExist(element) == true)
						removeElement(element);

					errorElement.hide();
				} else {
					// remove items from success list
					if (isExist(element) != true) successList.push(element);
					errorElement.children().html(String(args.messages ? (args.messages.required != undefined ? args.messages.required : messages.required) : messages.required));

					if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
					else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

					errorElement.show();
					
					
					//
					element.addClass("predefined");
					
					disallowedList.push(element);
				}

			} else {

				if ($.trim((element.val() != null ? (element.val().replace(/\r/g, "")) : element.val())).length > 0) {
					if (isExist(element) == true)
						removeElement(element);

					errorElement.hide();


					/*
					// compare with
					var compareWith = element.attr('comparewith') != undefined ? element.attr('comparewith') : null;
					if(compareWith != null) {
					//alert(compareWith)
					}
					*/

					var method = element.attr('method') != undefined ? element.attr('method') : null;
					if (method != null)
						validateByMethod(method, element.val().replace(/\r/g, ""), errorElement, element);

				} else {

					// remove items from success list
					if (isExist(element) != true) successList.push(element);
					errorElement.children().html(String(args.messages ? (args.messages.required != undefined ? args.messages.required : messages.required) : messages.required));

					if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
					else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

					errorElement.show();
					
					//
					element.addClass("predefined");
					
					disallowedList.push(element);
				}
			}

		});
		
    };
	
	
	
	
	
	
	var validate = function(element) {
		
		var errorElement = $('#field_error_message_' + element.attr('id'));
		
		if (element.attr("type") == "checkbox" || element.attr("type") == "radio") {

			if (element.is(':checked')) {
				if (isExist(element) == true)
					removeElement(element);

				errorElement.hide();
			} else {
				// Remove items from success list
				if (isExist(element) != true) successList.push(element);
				errorElement.children().html(String(args.messages ? (args.messages.required != undefined ? args.messages.required : messages.required) : messages.required));

				if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
				else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

				errorElement.show();
				
				
				disallowedList.push(element);
			}

		} else {
			
			if ($.trim((element.val() != null ? (element.val().replace(/\r/g, "")) : element.val())).length != 0) {
				if (isExist(element) == true)
					removeElement(element);

				errorElement.hide();

				/*
				// compare with
				var compareWith = element.attr('comparewith') != undefined ? element.attr('comparewith') : null;
				if(compareWith != null) {
				//alert(compareWith)
				}
				*/

				var method = element.attr('method') != undefined ? element.attr('method') : null;
				if (method != null) {
					
					validateByMethod(method, element.val().replace(/\r/g, ""), errorElement, element);
				}
				
				
				

			} else {

				// remove items from success list
				if (isExist(element) != true) successList.push(element);
				errorElement.children().html(String(args.messages ? (args.messages.required != undefined ? args.messages.required : messages.required) : messages.required));

				if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
				else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

				errorElement.show();
				
				
				disallowedList.push(element);
			}
		}
		
	};
	
	var clean = function( selector ) {
		return $( selector )[0];
	};
	
	var delegate = function(element, eventType) {	
		
		if(eventType == "focusin") {
			if(lastActive != null && lastActive != element) {
				//lastActive.css({'background' : 'white'});
			}
			lastActive = element;
			
			if(element.hasClass('predefined')) {
				validate(element);
			}
		}
		
		if(eventType == "focusout") {
			if(element.val().length != 0) {
				element.addClass("predefined");
			}
			
			if(element.hasClass('predefined')) {
				validate(element);
			}
		}
					
		if(eventType == "keyup") {
			if(element.hasClass('predefined')) {
				validate(element);
			}
		}
		
		// click on selects, radiobuttons and checkboxes
		if(eventType == "click" || eventType == "change") {
			if(element.hasClass('predefined')) {
				validate(element);
			}
		}
		
		
		
	};
	
	
	/*
    var validateRequired = function (element) {
        var errorElement = $('#field_error_message_' + element.attr('id'));
        if ($.trim(element.val().replace(/\r/g, "")).length > 0) {
            errorElement.hide();
        } else { errorElement.show(); }
    };

    var validateSpecific = function (element) {
        var errorElement = $('#field_error_message_' + element.attr('id'));
        var method = element.attr('method') != undefined ? element.attr('method') : null;
        if (method != null) validateByMethod(method, element.val().replace(/\r/g, ""), errorElement, element);
    };
	*/
	

    if (args.submitElement != undefined) {
        args.submitElement.click(function () {

            validateForm();

            if (disallowedList.length != 0) {
                disallowedList.reverse();
                disallowedList[disallowedList.length - 1].focus(); // set focus on the first not succseded input
            }

            if (args.acceptCallback != undefined && args.acceptCallback != null) {
                if (successList.length == 0) {
                    args.acceptCallback();
                }
            }

        });
    };
	
	elements.each(function(i, el) {
		$(el)
			.validateDelegate(":text, :password, :file, select, textarea", "focusin focusout keyup", function(event) {
				//alert("on" + event.type.replace(/^validate/, ""))
				//validateForm($(this), event.type.replace(/^validate/, ""));
				delegate($(this), event.type.replace(/^validate/, ""));
			})
			.validateDelegate(":radio, :checkbox, select, option", "click change", function(event) {
				//validateForm($(this), event.type.replace(/^validate/, ""));
				delegate($(this), event.type.replace(/^validate/, ""));
			});
	});
	
	
	
	
	
	
	
	
	/*
	if (elements.length > 0) {
        elements.each(function (i, el) {
            if ($(el).attr('validation') != undefined) {
                var errorTooltip = $("<span class=\"field-error-message cell\" id=\"field_error_message_" + $(el).attr('id') + "\" ><span></span></span>");
                
                $(el).after($(errorTooltip));
                $(errorTooltip).hide();

                successList.push($(el));
            }
        });
    }
	*/
	
	
	
	/*
	var elementsList = [];
	
	if(options.elements.length != 0) {
		for(var i = 0; i < options.elements.length; i++) {
			
			
			
			// check rules + patterns
			
			// messages
			
			// create error
			var errorTooltip = $("<span class=\"field-error-message cell\" id=\"field_error_message_" + $(options.elements[i]).attr('id') + "\" style=\"display: none\"><span></span></span>");
			
			
			
			options.elements[i].element.after(errorTooltip);
			
			
			
			successList.push(options.elements[i].element);
			
			
		}
	}
	*/
	
	
	
};

function externalError(element, errorMessage) {
    var element = $(element);
    var errorElement = $('#field_error_message_' + element.attr('id'));
    if (errorElement.hasClass('field-error-message')) {
        errorElement.children().html(String(errorMessage != undefined ? errorMessage : "The error message is not defined."));
        errorElement.show();
        element.focus();
    }
}


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
