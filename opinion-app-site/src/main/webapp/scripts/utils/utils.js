

$.format = function(source, params) {
	if (arguments.length == 1)
		return function() {
			var args = $.makeArray(arguments);
			args.unshift(source);
			return $.format.apply(this, args);
		};
	if (arguments.length > 2 && params.constructor != Array) {
		params = $.makeArray(arguments).slice(1);
	}
	if (params.constructor != Array) {
		params = [ params ];
	}
	$.each(params, function(i, n) {
		source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
	});
	return source;
};

jQuery.extend( {
	
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
	},
	replaceURLWithHTMLLinks : function(text, isBlank) {
		var exp = /(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/gim;
		//var exp = /(\b(?:(?:ht|f)tps?:\/\/|www)[^<>\]]+?(?![^<>\]]*([>]|<\/))(?=[\s!,?\]]|$))/gm;
		//var exp = /(\b(?:(?:ht|f)tps?:\/\/|www)[^<>\]]+?(?![^<>\]]*([>]|<\/))(?=[\s!,?\]]|$))/gim;
	    return text.replace(exp,"<a href=\"$1\" " + (isBlank != undefined ? "target=\"_blank\"" : "") + ">$1</a>"); 
	},
	replaceEmailWithHTMLLinks : function(text) {
		//Change email addresses to mailto:: links
	    var exp = /(([a-zA-Z0-9\-\_\.])+@[a-zA-Z\_]+?(\.[a-zA-Z]{2,10})+)/gim; // /(([a-zA-Z0-9_\-\.]+)@[a-zA-Z_]+?(?:\.[a-zA-Z]{2,6}))+/gim; // /(\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6})/gim;
	    return text.replace(exp, "<a href=\"mailto:$1\">$1</a>");
	}
});

;
( function($) {
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
	/*
	$.extend($.fn, {
		delegate : function(type, delegate, handler) {
			return this.bind(type, function(event) {
				var target = $(event.target);
				if (target.is(delegate)) {
					return handler.apply(target, arguments);
				}
			});
		},
		triggerEvent : function(type, target) {
			return this.triggerHandler(type, [ $.event.fix( {
				type : type,
				target : target
			}) ]);
		}
	})
	*/
})(jQuery);

/**
 * Create a cookie with the given name and value and other optional parameters.
 * 
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 * 
 * @example $.cookie('the_cookie', 'the_value', {expires: 7, path: '/', domain:
 *          'jquery.com', secure: true});
 * @desc Create a cookie with all available options.
 * 
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 * 
 * @example $.cookie('the_cookie', null);
 * @desc Delete a cookie by passing null as value.
 * 
 * @param String
 *            name The name of the cookie.
 * @param String
 *            value The value of the cookie.
 * @param Object
 *            options An object literal containing key/value pairs to provide
 *            optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date
 *         from now on in days or a Date object. If a negative value is
 *         specified (e.g. a date in the past), the cookie will be deleted. If
 *         set to null or omitted, the cookie will be a session cookie and will
 *         not be retained when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default:
 *         path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie
 *         (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be
 *         set and the cookie transmission will require a secure protocol (like
 *         HTTPS).
 * @type undefined
 * 
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */

/**
 * Get the value of a cookie with the given name.
 * 
 * @example $.cookie('the_cookie');
 * @desc Get the value of a cookie.
 * 
 * @param String
 *            name The name of the cookie.
 * @return The value of the cookie.
 * @type String
 * 
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */

jQuery.cookie = function(name, value, options) {
	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires
				&& (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime()
						+ (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires
															// attribute,
															// max-age is not
															// supported by IE
		}
		var path = options.path ? '; path=' + options.path : '';
		var domain = options.domain ? '; domain=' + options.domain : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [ name, '=', encodeURIComponent(value), expires,
				path, domain, secure ].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for ( var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie
							.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};

jQuery.timer = function(interval, callback) {
	/**
	 *
	 * timer() provides a cleaner way to handle intervals  
	 *
	 *@usage
	 * $.timer(interval, callback);
	 *
	 *
	 * @example
	 * $.timer(1000, function (timer) {
	 * 	alert("hello");
	 * 	timer.stop();
	 * });
	 * @desc Show an alert box after 1 second and stop
	 * 
	 * @example
	 * var second = false;
	 *	$.timer(1000, function (timer) {
	 *		if (!second) {
	 *			alert('First time!');
	 *			second = true;
	 *			timer.reset(3000);
	 *		}
	 *		else {
	 *			alert('Second time');
	 *			timer.stop();
	 *		}
	 *	});
	 * @desc show an alert box after 1 second and show another after 3 seconds
	 *
	 * 
	 */
	var interval = interval || 100;
	if (!callback)
		return false;

	_timer = function(interval, callback) {
		this.stop = function() {
			clearInterval(self.id);
		};

		this.internalCallback = function() {
			callback(self);
		};

		this.reset = function(val) {
			if (self.id)
				clearInterval(self.id);

			var val = val || 100;
			this.id = setInterval(this.internalCallback, val);
		};

		this.interval = interval;
		this.id = setInterval(this.internalCallback, this.interval);

		var self = this;
	};

	return new _timer(interval, callback);
};

( function($) {

	$.fn.extend( {
		/**
		 * Apply the mousewheel event to the elements in the jQuery object.
		 * The handler function should be prepared to take the event object
		 * and a param called 'delta'. The 'delta' param is a number
		 * either > 0 or < 0. > 0 = up and < 0 = down.
		 *
		 * @example $("p").mousewheel(function(event, delta){
		 *   if (delta > 0)
		 *     // do something on mousewheel scroll up
		 *   else if (delta < 0)
		 *     // do something on mousewheel scroll down
		 * });
		 *
		 */
		mousewheel : function(f) {
			if (!f.guid)
				f.guid = $.event.guid++;
			if (!$.event._mwCache)
				$.event._mwCache = [];

			return this.each( function() {
				if (this._mwHandlers)
					return this._mwHandlers.push(f);
				else
					this._mwHandlers = [];

				this._mwHandlers.push(f);

				var s = this;

				this._mwHandler = function(e) {
					e = $.event.fix(e || window.event);
					var delta = 0, returnValue = true;

					if (e.wheelDelta)
						delta = e.wheelDelta / 120;
					if (e.detail)
						delta = -e.detail / 3;
					if (window.opera)
						delta = -e.wheelDelta;

					for ( var i = 0; i < s._mwHandlers.length; i++)
						if (s._mwHandlers[i])
							if (s._mwHandlers[i].call(s, e, delta) === false) {
								returnValue = false;
								e.preventDefault();
								e.stopPropagation();
							}

					return returnValue;
				};

				if (this.addEventListener)
					if ($.browser.mozilla)
						this.addEventListener('DOMMouseScroll',
								this._mwHandler, false);
					else
						this.addEventListener('mousewheel', this._mwHandler,
								false);
				else
					this.onmousewheel = this._mwHandler;

				$.event._mwCache.push($(this));
			});
		},

		/**
		 * This method removes one or all applied mousewheel events from the elements.
		 * You can remove a single handler function by passing it as the first param.
		 * If you do not pass anything, it will remove all handlers.
		 */
		unmousewheel : function(f) {
			return this.each( function() {
				if (f && this._mwHandlers) {
					for ( var i = 0; i < this._mwHandlers.length; i++)
						if (this._mwHandlers[i]
								&& this._mwHandlers[i].guid == f.guid)
							delete this._mwHandlers[i];
				} else {
					if (this.addEventListener)
						if ($.browser.mozilla)
							this.removeEventListener('DOMMouseScroll',
									this._mwHandler, false);
						else
							this.removeEventListener('mousewheel',
									this._mwHandler, false);
					else
						this.onmousewheel = null;

					this._mwHandlers = this._mwHandler = null;
				}
			});
		}
	});

	// Clean-up
	$(window).one('unload', function() {
		var els = $.event._mwCache || [];
		for ( var i = 0; i < els.length; i++)
			els[i].unmousewheel();
	});

})(jQuery);

jQuery.fn.mousehold = function(timeout, f) {
	if (timeout && typeof timeout == 'function') {
		f = timeout;
		timeout = 100;
	}
	if (f && typeof f == 'function') {
		var timer = 0;
		var fireStep = 0;
		return this.each( function() {
			jQuery(this).mousedown( function() {
				fireStep = 1;
				var ctr = 0;
				var t = this;
				timer = setInterval( function() {
					ctr++;
					f.call(t, ctr);
					fireStep = 2;
				}, timeout);
			})

			clearMousehold = function() {
				clearInterval(timer);
				if (fireStep == 1)
					f.call(this, 1);
				fireStep = 0;
			}

			jQuery(this).mouseout(clearMousehold);
			jQuery(this).mouseup(clearMousehold);
		})
	}
};

(function ($) {
    $.fn.extend({
        inputMask: function (a) {
            var b = $(this),
                options = $.extend({
                    defaultValue: null
                }, a);
            if (options.defaultValue != null) {
                if (b.val() == "") {
                    b.val(options.defaultValue).addClass('mask')
                } else if (b.val() == options.defaultValue) {
                    b.addClass('mask')
                }
                b.attr({
                    'title': options.defaultValue,
                    'mask': options.defaultValue
                }).focusin(function () {
                    if ($(this).val() == options.defaultValue) {
                        $(this).val('').removeClass('mask')
                    }
                }).focusout(function () {
                    if ($(this).val() == "") {
                        $(this).val(options.defaultValue).addClass('mask')
                    }
                }).blur(function () {
                    if ($(this).val() == "" || $(this).val() == $(this).attr('mask')) {
                        $(this).val($(this).attr('mask')).addClass('mask')
                    } else if ($(this).val() != $(this).attr('mask')) {
                        $(this).removeClass('mask')
                    }
                })
            }
        }
    })
})(jQuery);

(function($) {

	var t = function (n) {
        var i, r, t;
        if (!n.match(/^https?:[\/]{2}/i) && n.length > 0) {
            for (i = !1, r = ["http://", "https://"], t = 0; t < r.length; t++)
                if (r[t].toUpperCase().indexOf(n.toUpperCase()) == 0) {
                    i = !0;
                    break
                }
            return i
        }
        return !0
    };
    $.fn.urlInputBox = function () {
        this.keyup(function () {
            var i = $(this);
            if (!t(i.val())) {
                //if (isValueSameAsPalceHolder(i.val(), i)) return;
                i.val("http://" + i.val())
            }
        }), this.focusout(function () {
            var t = $(this);
            t.val().match(/\s+$/) != null && t.val(n.trim(t.val()))
        });
        return this;
    },
    $.fn.urlTextArea = function () {
        this.keyup(function () {
            var r = $(this),
                i, u, f, e;
            //if (!isValueSameAsPalceHolder(r.val(), r)) {
                i = r.val().split("\n"), u = !1;
                for (f in i) e = i[f], t(e) || (u = !0, i[f] = "http://" + e);
                u && r.val(i.join("\n"))
            //}
        });
        return this;
    },
    $.fn.emptySelect = function() {
    	return this.each(function(){
    		if (this.tagName=='SELECT') this.options.length = 0;
    	});
    },
    $.fn.loadSelect = function(optionsDataArray) {
	    return this.emptySelect().each(function(){
	    	if (this.tagName=='SELECT') {
		        var selectElement = this;
		        $.each(optionsDataArray,function(index,optionData){
		        	var option = new Option(optionData.caption,optionData.value);
		        	if ($.browser.msie) {
		        		selectElement.add(option);
		        	}	else {
		        		selectElement.add(option,null);
		        	}
		        });
	    	}
	    });
    }
})(jQuery);

Array.prototype.remove = function(from, to) {
	if($.isArray(this)) {
		var rest = this.slice((to || from) + 1 || this.length);
		this.length = from < 0 ? this.length + from : from;
		return this.push.apply(this, rest);
	}
};

Number.prototype.formatCurrency = function(c, d, t){
	var n = this, c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "," : d, t = t == undefined ? "." : t, s = n < 0 ? "-" : "", i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", j = (j = i.length) > 3 ? j % 3 : 0;
	return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
};
