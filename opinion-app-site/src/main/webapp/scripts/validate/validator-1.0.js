
jQuery.fn.validator = function () {
    if (arguments.length == 0) return [];
    var args = arguments[0] || {};
    var successList = [];
    var accepted = false;

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
                        /*element.unbind('keyup').bind('keyup', function() {
                        Valid specific
                        validateSpecific(element);
                        });*/
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

                        if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
                        else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

                        errorElement.show();
                        /*element.unbind('keyup').bind('keyup', function() {
                        Valid specific
                        validateSpecific(element);
                        });*/
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

                        if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
                        else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

                        errorElement.show();
                        /*element.unbind('keyup').bind('keyup', function() {
                        Valid specific
                        validateSpecific(element);
                        });*/
                        disallowedList.push(element);
                    }
                } else { errorElement.hide(); }
                break;
            case 'notequal':
                if (value.toLowerCase() == element.attr('pattern').toLowerCase()) {
                    if (isExist(element) != true)
                        successList.push(element);
                    if (errorElement != null) {


                        alert((args.messages ? (args.messages.notEqual != undefined ? args.messages.notEqual : messages.notEqual) : messages.notEqual))

                        errorElement
                            .children()
                            .html(String((args.messages != undefined ? (args.messages.notEqual != undefined ? args.messages.notEqual : messages.notEqual) : messages.notEqual)));


                        if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
                        else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

                        errorElement.show();
                        /*element.unbind('keyup').bind('keyup', function() {
                        Valid specific
                        validateSpecific(element);
                        });*/
                        disallowedList.push(element);
                    }
                } else { errorElement.hide(); }
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

    var elements = $(this);

    if (elements.length > 0) {
        elements.each(function (i, el) {
            if ($(el).attr('validation') != undefined) {
                var errorTooltip = $.create('span', { 'class': 'field-error-message cell', 'id': 'field_error_message_' + $(el).attr('id') }, []);
                $(errorTooltip).append($.create('span', {}, []));


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
                    // Remove items from success list
                    if (isExist(element) != true) successList.push(element);
                    errorElement.children().html(String(args.messages ? (args.messages.required != undefined ? args.messages.required : messages.required) : messages.required));

                    if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
                    else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

                    errorElement.show();
                    /*element.unbind('keyup').bind('keyup', function() {
                    Valid specific
                    validateRequired(element);
                    });*/
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

                    // Remove items from success list
                    if (isExist(element) != true) successList.push(element);
                    errorElement.children().html(String(args.messages ? (args.messages.required != undefined ? args.messages.required : messages.required) : messages.required));

                    if ($.browser.msie) errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });
                    else errorElement.css({ 'top': (element.offset().top - 2), 'left': element.offset().left + element.width() });

                    errorElement.show();
                    /*element.unbind('keyup').bind('keyup', function() {
                    Valid specific
                    validateRequired(element);
                    });*/
                    disallowedList.push(element);
                }
            }

        });
    };

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

    if (args.submitElement != undefined) {
        args.submitElement.click(function () {

            validateForm();

            if (disallowedList.length != 0) {
                disallowedList.reverse();
                disallowedList[disallowedList.length - 1].focus(); // set focus on the first not succseded input


                //validateForm(); // Bug fix for after focus show tootip
            }

            if (args.acceptCallback != undefined && args.acceptCallback != null) {
                if (successList.length == 0) {
                    args.acceptCallback();
                }
            }

        });
    }


    /*elements.delegate("focusout", ":text, :password, textarea", function() {
    validateForm();
    });*/

    /*jQuery().click(function(e){
    if (!e) var e = window.event;
    var target = e.target != null ? e.target : e.srcElement;
         
    // target point of mouse don't like to css class by specific unbind window events then close box
    if(target.type == undefined) {
	         
    }
    });*/
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