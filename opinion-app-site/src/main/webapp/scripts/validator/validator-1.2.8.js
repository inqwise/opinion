;
(function($) {
	validator = function(s) {
            var x = [];
            var B = [];
            var C = false;
            var D = $.extend({
                elements: [],
                errorList: null,
                errorTemplate: null,
                submitElement: null,
                accept: null,
                error: null
            }, s);
            var E = {
                required: 'This field is required.',
                remote: 'Please fix this field.',
                email: 'Please enter a valid email address.',
                url: 'Please enter a valid URL.',
                date: 'Please enter a valid date.',
                dateISO: 'Please enter a valid date (ISO).',
                number: 'Please enter a valid number.',
                currency : 'Please enter a valid currency value.', // 'Must be in US currency format 0.99',
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
            var F = function (b, c, e, f, g) {
                    switch (b) {
                    case 'required':
                        switch (c[0].nodeName.toLowerCase()) {
                        case 'select':
                            return e && e.length > 0;
                        case 'input':
                            if (J(c)) return L(e, c) > 0;
                        default:
                            return $.trim(e).length > 0
                        }
                    case 'digits':
                        return /^\d+$/.test(e);
                    case 'number':
                        return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(e);
                    case 'currency' :
                    	return /^\d{1,9}(\.\d{1,2})?$/.test(e); // return /^((\d{1,5})+\.\d{2})?$|^\$?[\.]([\d][\d]?)$/.test(e);
                    case 'email':
                        return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(e);
                    case 'emailISO':
                        return /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/i.test(e);
                    case 'url':
                        return /^(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/i.test(e);
                    case 'minlength':
                        var p = f ? f : 0;
                        if (L($.trim(e), c) >= p) {
                            return true
                        }
                        return false;
                    case 'maxlength':
                        var p = f ? f : 0;
                        if (L($.trim(e), c) <= p) return true
                        return false;
                    case 'min':
                        if (parseFloat(e.toString().replace(/,/g,"")) >= parseFloat(f.toString().replace(/,/g,""))) return true;
                        return false;
                    case 'max':
                        if (parseFloat(e.toString().replace(/,/g,"")) <= parseFloat(f.toString().replace(/,/g,""))) return true;
                        return false;
                    case 'rangelength':
                        var h = L($.trim(e), c);
                        var j = f[0] ? f[0] : 0;
                        var k = f[1] ? f[1] : 0;
                        if ((h >= j && h <= k)) return true;
                        return false;
                    case 'range':
                        var j = f[0] ? f[0] : 0;
                        var k = f[1] ? f[1] : 0;
                        if (e >= j && e <= k) return true;
                        return false;
                    case 'date':
                        return !/Invalid|NaN/.test(new Date(e));
                    case 'dateISO':
                        return /^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(e);
                    case 'creditcard':
                        if (/[^0-9-]+/.test(e)) return false;
                        var l = 0,
                            o = 0,
                            bEven = false;
                        e = e.replace(/\D/g, "");
                        for (var n = e.length - 1; n >= 0; n--) {
                            var m = e.charAt(n);
                            var o = parseInt(m, 10);
                            if (bEven) {
                                if ((o *= 2) > 9) o -= 9
                            }
                            l += o;
                            bEven = !bEven
                        }
                        return (l % 10) == 0;
                    case 'equalTo':
                    	if (g) {
                    		var q = g;
                    		
                    		/*
	                   		 q.unbind('change')
	                   		 .bind('change', function () {
	                   		 */
                    		
                    		q.unbind('blur')
                    		 .bind('blur', function () {
                    			bd(c)
                    		});
                    		if ($.trim((e != null ? (e.replace(/\r/g, "")) : e)).length != 0) {
                    			if (e == q.val()) 
                    				return true
                    		}
                    	} else {
                    		if (f != undefined) {
                    			if(f instanceof RegExp) {
                    				if ($.trim((e != null ? (e.replace(/\r/g, "")) : e)).length != 0) {
                    					var r = new RegExp(f);
                    					if (r.test(e)) 
                    						return true
                    				}
                    			} else if(typeof f == 'function') {
                    				if (f()) 
                    					return true
                    			}
                    		}
                    	}
                    	return false;
                    case 'notEqualTo':
                    	if (g) {
                    		var q = g;
                    		
                    		/*
	                   		 q.unbind('change')
	                   		 .bind('change', function () {
	                   		 */
                    		
                    		q.unbind('blur')
                    		 .bind('blur', function () {
                    			bd(c)
                    		});
                    		if ($.trim((e != null ? (e.replace(/\r/g, "")) : e)).length != 0) {
                    			if (e != q.val()) 
                    				return true
                    		}
                    	} else {
                    		if (f != undefined) {
                    			if(f instanceof RegExp) {
                    				if ($.trim((e != null ? (e.replace(/\r/g, "")) : e)).length != 0) {
                    					var r = new RegExp(f);
                    					if (!r.test(e)) 
                    						return true
                    				}
                    			} else if(typeof f == 'function') {
                    				if(!f())
                    					return true;
                    			}
                    		}
                    	}
                    	return false;
                    default:
                        return (e == f)
                    }
                };
            var H = function (a) {
                    if (x.length > 0) {
                        for (var i = 0; i < x.length; i++) {
                            if (x[i] == a) {
                                return true
                            }
                        }
                    }
                    return false
                };
            var I = function (a) {
                    if (x.length > 0) {
                        for (var i = 0; i < x.length; i++) {
                            if (x[i] == a) {
                                x.remove(i);
                                break
                            }
                        }
                    }
                };
            var J = function (a) {
                    return /radio|checkbox/i.test(a[0].type)
                };
            var K = function (a) {
                    return $(a)[0]
                };
            var L = function (a, b) {
                    switch (b[0].nodeName.toLowerCase()) {
                    case 'select':
                        return $("option:selected", b).length;
                    case 'input':
                        if (J(b)) return T(b[0].name).filter(':checked').length
                    }
                    return a.length
                };
            var M = null;
            var N = null;
            var O = D.elements;
            var P = [];
            var Q = function (a) {
                    for (var i = 0; i < P.length; i++) {
                        if (P[i].groupName == a) {
                            return P[i]
                        }
                    }
                    return null
                };
            var R = function (a) {
                    for (var i = 0; i < O.length; i++) {
                        if (O[i].element) {
                            if (O[i].element.attr('id') == a.attr('id')) {
                                return O[i]
                            }
                        }
                    }
                    return null
                };
            var T = function (c) {
                    return $(document.getElementsByName(c)).map(function (a, b) {
                        return b.name == c && b || null
                    })
                };
            if (O.length > 0) {
                for (var i = 0; i < O.length; i++) {
                    if (O[i].element) {
                    	
                        var U = O[i].element.attr('name');
                        O[i].element
                        .removeClass('invalid valid predefined')
                        .validateDelegate(":text, :password, :file, select, textarea", "focusin focusout keyup", function (a) {
                            be($(this), a.type)
                        }).validateDelegate(":radio, :checkbox, select, option", "click change", function (a) {
                            be($(this), a.type)
                        });
                        var V = null;
                        if (D.errorTemplate) {
                            V = D.errorTemplate().addClass('status validator-' + U).hide()
                        } else {
                            V = $("<label class=\"status validator-" + U + "\" style=\"display: none\"></label>")
                        }
                        if (D.errorList) {
                            if (D.errorList.placeholder) {
                                if (!H(U)) {
                                    if (D.errorList.errorTemplate) {
                                        D.errorList.errorTemplate().addClass('status validator-' + U).hide().appendTo(D.errorList.placeholder)
                                    } else {
                                        V.appendTo(D.errorList.placeholder)
                                    }
                                }
                            }
                        } else {
                            if (!H(U)) {
                                if (O[i].status) {
                                    O[i].status.addClass('status validator-' + U).hide()
                                } else {
                                    O[i].element.after(V)
                                }
                            }
                        }
                        x.push(U)
                    }
                    if (O[i].group) {
                        var W = null;
                        var X = null;
                        O[i].group.each(function (y, c) {
                            if (y == (O[i].group.length - 1)) {
                                X = $(c);
                                W = X.attr('name')
                            }
                            P.push({
                                groupName: W,
                                group: O[i].group,
                                rules: O[i].rules,
                                error: O[i].error,
                                success: O[i].success
                            });
                            $(c)
                            .removeClass('invalid valid predefined')
                            .validateDelegate(":text, :password, :file, select, textarea", "focusin focusout keyup", function (a) {
                                var b = Q($(this).attr('name'));
                                be($(this), a.type, b)
                            }).validateDelegate(":radio, :checkbox, select, option", "click change", function (a) {
                                var b = Q($(this).attr('name'));
                                be($(this), a.type, b)
                            })
                        });
                        x.push(W);
                        var V = null;
                        if (D.errorTemplate) {
                            V = D.errorTemplate().addClass('status validator-' + W).hide()
                        } else {
                            V = $("<label class=\"status validator-" + W + "\" style=\"display: none\"></label>")
                        }
                        if (D.errorList) {
                            if (D.errorList.placeholder) {
                                if (D.errorList.errorTemplate) {
                                    D.errorList.errorTemplate().addClass('status validator-' + W).hide().appendTo(D.errorList.placeholder)
                                } else {
                                    V.appendTo(D.errorList.placeholder)
                                }
                            }
                        } else {
                            if (O[i].status) {
                                O[i].status.addClass('status validator-' + W).hide()
                            } else {
                                X.after(V)
                            }
                        }
                    }
                }
            }
            var Y = function () {
                    B = [];
                    if (O.length > 0) {
                        for (var i = 0; i < O.length; i++) {
                            if (O[i].element) {
                                var t = O[i];
                                var v = true;
                                var a = t.element;
                                if (t.validate) {
                                    if (typeof t.validate == 'function') {
                                        v = t.validate()
                                    } else {
                                        v = t.validate()
                                    }
                                }
                                if (v) {
                                    a.addClass("predefined")
                                }
                                bd(a)
                            }
                            if (O[i].group) {
                                O[i].group.addClass("predefined");
                                Z(O[i])
                            }
                        }
                    }
                };
            var Z = function (b) {
                    var f = b;
                    if (f) {
                        var b = f.group;
                        var c = f.rules;
                        var d = false;
                        var e = null;
                        var k = 0;
                        var r = 0;
                        var g = function (a) {
                                var u = 0;
                                for (var i = 0; i < c.length; i++) {
                                    if (F(c[i].method, a, a.val(), c[i].pattern, c[i].element)) {
                                        u++
                                    } else {
                                        break
                                    }
                                }
                                if (d) {
                                    if (u != c.length) {
                                        k++;
                                        r = u
                                    }
                                } else {
                                    if (u != c.length) {
                                        a.addClass('invalid').removeClass('valid');
                                        k++;
                                        r = u
                                    } else {
                                        a.removeClass('invalid').addClass('valid')
                                    }
                                }
                            };
                        if (c) {
                            b.each(function (y, a) {
                                if (y == (b.length - 1)) {
                                    e = $(a).attr('name')
                                }
                                if (J($(a))) {
                                    if (d != true) d = true
                                }
                                g($(a))
                            });
                            var h = $('.validator-' + e);
                            if (d) {
                                if (k == b.length) {
                                    if (H(e) != true) x.push(e);
                                    B.push(e);
                                    b.addClass('invalid').removeClass('valid');
                                    var j = (c[r].message ? c[r].message : E[c[r].method]);
                                    if (c[r].pattern) {
                                        var l = c[r].pattern;
                                        if (l.constructor != Array) {
                                            l = [l]
                                        }
                                        $.each(l, function (i, n) {
                                            j = j.replace(new RegExp("\\{" + i + "\\}", "g"), n)
                                        })
                                    }
                                    if (!(f.showError != undefined && !f.showError)) {
                                        h.text(j).show()
                                    }
                                    if (f.error) {
                                        if (typeof f.error == 'function') f.error(j)
                                    }
                                } else {
                                    if (H(e) == true) I(e);
                                    b.removeClass('invalid').addClass('valid');
                                    h.text('').hide();
                                    if (f.success) {
                                        if (typeof f.success == 'function') f.success()
                                    }
                                }
                            } else {
                                if (k != 0) {
                                    if (H(e) != true) x.push(e);
                                    B.push(e);
                                    var j = (c[r].message ? c[r].message : E[c[r].method]);
                                    if (c[r].pattern) {
                                        var l = c[r].pattern;
                                        if (l.constructor != Array) {
                                            l = [l]
                                        }
                                        $.each(l, function (i, n) {
                                            j = j.replace(new RegExp("\\{" + i + "\\}", "g"), n)
                                        })
                                    }
                                    if (!(f.showError != undefined && !f.showError)) {
                                        h.text(j).show()
                                    }
                                    if (f.error) {
                                        if (typeof f.error == 'function') f.error(j)
                                    }
                                } else {
                                    if (H(e) == true) I(e);
                                    h.text('').hide();
                                    if (f.success) {
                                        if (typeof f.success == 'function') f.success()
                                    }
                                }
                            }
                        }
                    }
                };
            var bd = function (a) {
                    var t = R(a);
                    if (t) {
                        var b = t.element;
                        var c = b.attr('name');
                        var d = t.rules;
                        var v = true;
                        if (t.validate) {
                            if (typeof t.validate == 'function') {
                                v = t.validate()
                            } else {
                                v = t.validate()
                            }
                        }
                        var e = $('.validator-' + c);
                        if (v) {
                            if (d) {
                                var u = 0;
                                var G = function () {
                                        for (var y = 0; y < d.length; y++) {
                                            if (F(d[y].method, b, b.val(), d[y].pattern, d[y].element)) {
                                                u++
                                            } else {
                                                break
                                            }
                                        }
                                    }
                                G();
                                if (u != d.length) {
                                    if (H(c) != true) x.push(c);
                                    B.push(c);
                                    b.addClass('invalid').removeClass('valid');
                                    var f = (d[u].message ? d[u].message : E[d[u].method]);
                                    if (d[u].pattern) {
                                        var g = d[u].pattern;
                                        if (g.constructor != Array) g = [g];
                                        $.each(g, function (i, n) {
                                            f = f.replace(new RegExp("\\{" + i + "\\}", "g"), n)
                                        })
                                    }
                                    if (!(t.showError != undefined && !t.showError)) {
                                        e.text(f).show()
                                    }
                                    if (t.error) {
                                        if (typeof t.error == 'function') t.error(f)
                                    }
                                } else {
                                    if (H(c) == true) I(c);
                                    b.removeClass('invalid').addClass('valid');
                                    e.text('').hide();
                                    if (t.success) {
                                        if (typeof t.success == 'function') t.success()
                                    }
                                }
                            }
                        } else {
                            if (H(c) == true) I(c);
                            b.removeClass('invalid').addClass('valid');
                            e.text('').hide()
                        }
                    }
                };
            var be = function (a, b, c) {
                    if (b == "focusin") {
                        if (M != null && M != a) {}
                        M = a;
                        if (a.hasClass('predefined')) {
                            if (c) {
                                Z(c)
                            } else {
                                bd(a)
                            }
                        }
                    }
                    if (b == "focusout") {
                        if (a.val().length != 0) {
                            a.addClass("predefined")
                        }
                        if (a.hasClass('predefined')) {
                            if (c) {
                                Z(c)
                            } else {
                                bd(a)
                            }
                        }
                    }
                    if (b == "keyup") {
                        if (a.hasClass('predefined')) {
                            if (c) {
                                Z(c)
                            } else {
                                bd(a)
                            }
                        }
                    }
                    if (b == "click" || b == "change") {
                        if (a.hasClass('predefined')) {
                            if (c) {
                                Z(c)
                            } else {
                                bd(a)
                            }
                        }
                    }
                };
            if (D.submitElement != undefined) {
                D.submitElement
                	.unbind('click')
                	.click(function (event) {
                    bf();
                    
                    event.preventDefault();
                });
            }
            var bf = function () {
                    Y();
                    if (B.length != 0) {
                        B.reverse();
                        T(B[B.length - 1]).filter('.invalid:first').filter(':visible').focus();
                        if (D.errorList) {
                            if (D.errorList.placeholder) {
                                if (!D.errorList.placeholder.is(':visible')) D.errorList.placeholder.show()
                            }
                        }
                        if (D.error != undefined && typeof D.error == 'function') D.error()
                    }
                    if (D.accept != undefined && typeof D.accept == 'function') {
                        if (x.length == 0) D.accept()
                    }
                };
            return {
                validate: function () {
                    bf()
                }
            }
	};
})(jQuery);

(function ($) {
    if (!jQuery.event.special.focusin && !jQuery.event.special.focusout && document.addEventListener) {
        $.each({
            focus: 'focusin',
            blur: 'focusout'
        }, function (a, b) {
            $.event.special[b] = {
                setup: function () {
                    this.addEventListener(a, handler, true)
                },
                teardown: function () {
                    this.removeEventListener(a, handler, true)
                },
                handler: function (e) {
                    arguments[0] = $.event.fix(e);
                    arguments[0].type = b;
                    return $.event.handle.apply(this, arguments)
                }
            };

            function handler(e) {
                e = $.event.fix(e);
                e.type = b;
                return $.event.handle.call(this, e)
            }
        })
    };
    $.extend($.fn, {
        validateDelegate: function (c, d, e) {
            return this.bind(d, function (a) {
                var b = $(a.target);
                if (b.is(c)) {
                    return e.apply(b, arguments)
                }
            })
        }
    })
})(jQuery);

Array.prototype.remove = function (a, b) {
    if ($.isArray(this)) {
        var c = this.slice((b || a) + 1 || this.length);
        this.length = a < 0 ? this.length + a : a;
        return this.push.apply(this, c)
    }
};