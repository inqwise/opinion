function CdsLoader() {
    this.basePath = a();

    function a() {
        var e = null;
        if (e == null) {
            var d = null;
            var b = document.getElementsByTagName("script");
            if (b.length == 0) {
                return null
            }
            var c = b[b.length - 1];
            if (c === undefined) {
                return null
            }
            if (c.getAttribute.length !== undefined) {
                d = c.src
            } else {
                d = c.getAttribute("src", - 1)
            }
            if (d == null || d == "") {
                return null
            }
            e = d.substring(0, d.indexOf("/", 14)) + "/"
        }
        return e
    }
}function BrowserProps() {
    this.locale = a();

    function a() {
        var b = null;
        if (navigator) {
            if (navigator.language) {
                b = navigator.language
            } else {
                if (navigator.browserLanguage) {
                    b = navigator.browserLanguage
                } else {
                    if (navigator.systemLanguage) {
                        b = navigator.systemLanguage
                    } else {
                        if (navigator.userLanguage) {
                            b = navigator.userLanguage
                        }
                    }
                }
            }
        }
        if (b != null && b.length > 2) {
            b = b.substring(0, 2)
        }
        return b
    }
}(function (a) {
    a.extend(a.fn, {
        delayedObserver: function (b, c) {
            return this.each(function () {
                var d = a(this);
                d.data("oldval", d.formVal()).data("delay", b || 0.5).data("condition", function () {
                    return (a(this).data("oldval") == a(this).formVal())
                }).data("callback", c)[d.fieldEvent(b)](function () {
                    if (d.data("condition").apply(d)) {
                        return
                    } else {
                        if (d.data("timer")) {
                            clearTimeout(d.data("timer"))
                        }
                        d.data("timer", setTimeout(function () {
                            d.data("callback")(d, d.formVal())
                        }, d.data("delay") * 1000));
                        d.data("oldval", d.formVal())
                    }
                })
            })
        },
        fieldEvent: function (f) {
            var b = a(this);
            var d = b[0] || b,
                c = "change";
            if (d.type == "radio" || d.type == "checkbox") {
                c = "click"
            } else {
                if (f && (d.type == "text" || d.type == "textarea" || d.type == "password")) {
                    c = "keyup"
                }
            }
            return c
        },
        formVal: function () {
            var b = a(this);
            if (b.is("form")) {
                return this.serialize()
            }
            if (b.attr("type") == "checkbox" || b.attr("type") == "radio") {
                return this.filter("input:checked").val() || ""
            } else {
                return this.val()
            }
        },
        spinner: function (b) {
            return this.each(function () {
                var c = "cds_spinner";
                var d = "#" + c;
                var e = a(d);
                if (!e.exists()) {
                    b = typeof (b) === "undefined" ? "csd-ui-spinner" : b;
                    a(this).after(a("<div>").attr("id", c).addClass(b))
                }
                a(d).ajaxStart(function () {
                    a(this).show()
                }).ajaxStop(function () {
                    a(this).hide()
                });
                return this
            })
        },
        exists: function () {
            return (this.length > 0)
        }
    })
})(jQuery);
var cdsLoader = new CdsLoader();
(function (a) {
    a.fn.cintSampler = function (b) {
        if (arguments.length == 2) {
            return a.cintSampler.postPopulateField.apply(a(this), arguments)
        }
        return this.each(function () {
            a.cintSampler.init(a(this), b)
        })
    };
    a.cintSampler = a.cintSampler || {};
    a.extend(a.cintSampler, {
        container: null,
        options: {
            baseUrl: cdsLoader.basePath,
            proxyPath: "",
            apiKey: "",
            onErrorEventHandler: null,
            surveyTitle: {
                value: "",
                editable: true
            },
            surveyUrl: {
                value: "http://",
                editable: true
            },
            numberOfQuestions: {
                value: "1",
                editable: true
            },
            mode: null,
            language: null,
            country: null,
            debug: false,
            collectorId : null,
            orderPurchase : {}
        },
        debug: function (b) {
            if (this.options.debug) {
                if (typeof (console) === "undefined") {
                    console = {
                        log: function (c) {}
                    }
                }
                console.log(b)
            }
        },
        presetInitialState: function () {},
        fieldsToObserve: function () {
            return ["country_id", "region_select", "age_range_select", "education_level_select", "gender_select", "occupation_status_select", "number_of_questions", "number_of_completes_select"]
        },
        fieldErrorClass: function () {
            return "cds-error"
        },
        loadPrerequisites: function (b) {
            a.getScript("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js", function () {
                a.getScript("http://ajax.microsoft.com/ajax/jquery.validate/1.8/jquery.validate.min.js", function () {
                    b()
                })
            })
        },
        bindPrePopulatedFields: function () {
            a.cintSampler.container.find("#survey_url, #survey_title").change(function () {
                a.cintSampler.updateOverview(a(this).attr("id"))
            })
        },
        setPrePopulatedFields: function () {
            a.cintSampler.setValueAndState(a.cintSampler.options.surveyTitle);
            a.cintSampler.setValueAndState(a.cintSampler.options.surveyUrl);
            a.cintSampler.setValueAndState(a.cintSampler.options.numberOfQuestions)
        },
        init: function (d, p) {
            a.extend(this.options, p || {});
            this.container = d;
            this.container.addClass("cds-ui-widget-marker");
            l(this.options);
            c();
            this.container.bind("rebind", function () {
                f(a.cintSampler.options);
                o(p);
                m();
                k();
                e();
                b()
            });
            this.loadPrerequisites(a.cintSampler.load);
            a(window).unload(function () {
                a(this).empty();
                this.options = null
            });

            function l(q) {
                q.surveyTitle.id = "#survey_title";
                q.surveyUrl.id = "#survey_url";
                q.numberOfQuestions.id = "#number_of_questions";
                q.baseUrl = n(q.baseUrl)
            }function c() {
                a.ajaxSetup({
                    cache: true,
                    ifModified: true
                });
                i()
            }function i() {
                a(document).ajaxError(function (t, u, r, q) {
                    var s = "Cint Sampler Plugin Error: ";
                    if (typeof (u) !== "undefined") {
                        s += u.status + " " + u.statusText
                    }
                    if (typeof (q) !== "undefined") {
                        s += q.message
                    }
                    a.cintSampler.debug(s);
                    a.cintSampler.container.html(s);
                    if (a.isFunction(a.cintSampler.options.onErrorEventHandler)) {
                        a.cintSampler.options.onErrorEventHandler(s)
                    }
                })
            }function o(r) {
                var q = s(r);
                if (q != null && q !== "en") {
                    var t = (q === "sv") ? "se" : q;
                    a.getScript(document.location.protocol + "//ajax.aspnetcdn.com/ajax/jquery.validate/1.8/localization/messages_" + t + ".js", function () {
                        if (q === "sv") {
                            jQuery.extend(jQuery.validator.messages, {
                                min: jQuery.validator.format("Ange ett v&auml;rde som &auml;r st&ouml;rre eller lika med {0}."),
                                max: jQuery.validator.format("Ange ett v&auml;rde som &auml;r mindre eller lika med {0}."),
                            })
                        }
                    })
                }function s(u) {
                    if (u.language != null) {
                        return u.language
                    }
                    var v = new BrowserProps();
                    return v.locale
                }
            }function b() {
                a.cintSampler.presetInitialState();
                var q = a.cintSampler.container.find("#cds-tabs");
                q.tabs();
                q.find("#cds-gender-selection,#cds-age-selection,#cds-edu-selection,#cds-completes-selection").buttonset();
                q.find(".cds-navigation").find("button, input:submit, a").button();
                q.find("#cds-next-button-1,#cds-next-button-2,#cds-back-button-1,#cds-back-button-2,#cds-back-button-2,#cds-skip-button-1").click(function () {
                    q.tabs("select", a(this).attr("href").replace(/^[^#]*/, ""));
                    return false
                })
            }function e() {
                a.cintSampler.container.find("#buy").click(function () {
                	a.cintSampler.submit()
                });
                a.cintSampler.bindPrePopulatedFields();
                g();
                h();
                j()
            }function m() {
                var q = a.cintSampler.fieldsToObserve();
                a.each(q, function (r, s) {
                    a("#" + s).delayedObserver(0.25, function (t, u) {
                        a.cintSampler.handleOnChange(t, u)
                    })
                })
            }function g() {
                var q = "ui-state-active";
                var r = "ui-state-focus";
                var t = ".ui-state-default";
                var s = function () {
                        a(this).addClass(q);
                        a(this).addClass(r);
                        a(this).blur(function () {
                            a(this).removeClass(q);
                            a(this).removeClass(r)
                        })
                    };
                a(t).click(s).focus(s)
            }function h() {
                a.validator.addClassRules({
                    positive_number: {
                        min: 1,
                        digits: true
                    },
                    number_of_completes: {
                        min: 10,
                        digits: true
                    },
                    percent: {
                        min: 1,
                        max: 100,
                        digits: true
                    }
                });
                a.cintSampler.mainForm().validate({
                    errorContainer: "#cds_error_messages",
                    errorLabelContainer: "ul.cds-error-list",
                    errorClass: a.cintSampler.fieldErrorClass(),
                    wrapper: "li",
                    ignore: []
                })
            }function j() {
                a.cintSampler.container.find(".cds-ui-container").spinner("cds-ui-spinner")
            }function f(q) {
                var s = ["proxyPath", "apiKey"];
                a.each(s, function (t, u) {
                    if (!r(q[u])) {
                        a.cintSampler.options.onErrorEventHandler("Error: required parameter '" + u + "' is not set.")
                    }
                });

                function r(t) {
                    return t !== undefined && a.trim(t) !== ""
                }
            }function n(q) {
                return a.trim(q).replace(/(.*[^\/])$/, "$1/")
            }function k() {
                a.cintSampler.setPrePopulatedFields()
            }
        },
        postPopulateField: function (d, c) {
            var b = a.cintSampler.options[d];
            if (b) {
                b.value = c;
                b.editable = false;
                a.cintSampler.setValueAndState(b)
            }
        },
        setValueAndState: function (e) {
            var f = a.cintSampler.container.find(e.id);
            d(f, e.value);
            b(e, f);

            function b(h, i) {
                if (!h.editable && !(h.editable === undefined)) {
                    i.hide();
                    var g = c(i);
                    g.html(h.value)
                }
            }function d(h, i) {
                var g = h.val();
                h.val(i);
                if (g !== i) {
                    a.cintSampler.handleOnChange(h, i)
                }
            }function c(i) {
                var h = i.attr("id") + "_label";
                var g = a.cintSampler.container.find("#" + h);
                if (g.size() === 0) {
                    g = a("<strong>").attr("id", h).addClass("cds-field-value word-wrap cds-read-only");
                    i.after(g)
                }
                return g
            }
        },
        updateregions: function (c) {
            var e = a.cintSampler.container.find("#region_select");
            var b = e.find("option:first").text();
            e.html(f(c, b));
            g(e);
            e.change();

            function f(l, h) {
                var j = d("", h);
                for (var k = 0; k < l.length; k++) {
                    j += d(l[k].id, l[k].name)
                }
                return j
            }function d(h, i) {
                return '<option value="' + h + '">' + i + "</option>"
            }function g(h) {
                var i = a.cintSampler.container.find("#country_id").val();
                if (i === "") {
                    h.attr("disabled", "disabled")
                } else {
                    h.removeAttr("disabled")
                }
            }
        },
        updateprice: function (b) {
            a.cintSampler.debug(b);
            a.cintSampler.container.find(".cds-price").html(b.body)
        },
        mainForm: function () {
            return this.container.find("#cds_form")
        },
        updateOverview: function (d) {
            var b = a.cintSampler.container.find("#" + d + " option:selected").text();
            var c = (b !== "" && b != null) ? b : a.cintSampler.container.find("#" + d).val();
            a.cintSampler.container.find("#overview_" + d).html(c)
        },
        handleRadioButtonEvent: function (c, b) {
            a.cintSampler.container.find("#overview_" + c).html(b);
            a.cintSampler.handlePriceUpdate(a.cintSampler.container.find("#" + c), b)
        },
        handleCountryChange: function () {
            var b = a.cintSampler.container.find("#country_id").val();
            if (b === "") {
                a.cintSampler.updateregions([])
            } else {
                a.cintSampler.container.find("#region_select").val("");
                a.ajax({
                    url: a.cintSampler.insertApiKey(a.cintSampler.options.baseUrl + "countries/" + b + "/regions.js"),
                    global: false,
                    dataType: "jsonp",
                    data: a.cintSampler.appendData(null),
                    success: function (c) {
                        a.cintSampler.updateregions(c)
                    }
                })
            }
        },
        quote_path: function () {
            return "quote.js"
        },
        handlePriceUpdate: function (d, f) {
            var b = d.attr("id");
            a.cintSampler.debug("update price: " + b + " value: " + f);
            var e = true;
            a.each(a.cintSampler.fieldsToValidateForPriceUpdate(), function (h, g) {
                var i = a("#" + g);
                if (i.length > 0) {
                    var j = i.valid();
                    e = e && j
                }
            });
            if (e) {
                a.ajax({
                    url: a.cintSampler.insertApiKey(a.cintSampler.options.baseUrl + a.cintSampler.quote_path()),
                    global: false,
                    dataType: "jsonp",
                    data: a.cintSampler.appendData(c(a.cintSampler.mainForm())),
                    success: function (g) {
                        a.cintSampler.updateprice(g)
                    }
                })
            }function c(g) {
                var h = a.cintSampler.serializeForm(g);
                a.cintSampler.debug("data: '" + h + "'");
                return h
            }
        },
        fieldsToValidateForPriceUpdate: function () {
            return []
        },
        handleOnChange: function (c, d) {
            var b = c.attr("id");
            a.cintSampler.updateOverview(b);
            if (b == "country_id") {
                a.cintSampler.handleCountryChange()
            }
            if (a.inArray("#" + b, [this.options.surveyTitle.id, this.options.surveyUrl.id]) === -1) {
                a.cintSampler.handlePriceUpdate(c, d)
            }
        },
        widget_path: function () {
            return "widget/v2.js"
        },
        load: function () {
            a.ajax({
                url: a.cintSampler.insertApiKey(a.cintSampler.options.baseUrl + a.cintSampler.widget_path()),
                global: false,
                dataType: "jsonp",
                data: a.cintSampler.appendData(a.cintSampler.getCountry()),
                success: function (b) {
                    a.cintSampler.container.html(b.html)
                }
            })
        },
        serializeForm: function (b) {
            var c = "";
            if (!(a.cintSampler.container.find("#country_id").val() === "")) {
                c = b.serialize();
                a.cintSampler.debug("submit data: '" + c + "'")
            }
            return c
        },
        submit: function () {
            var c = a.cintSampler.container.find("#buy");
            if (c.button("option", "disabled") === true) {
                return false
            }
            c.button({
                disabled: true
            });
            
            var b = a.cintSampler.mainForm();
            if (b.valid()) {
            	
            	var obj = {
            		cint : {
            			orderPurchase : {
            				collectorId : a.cintSampler.options.collectorId,
            				sourceId : a.cintSampler.options.sourceId,
                    		data : a.cintSampler.appendData(a.cintSampler.serializeForm(b))
            			}
            		}
            	}
            	
            	loader.show();
            	
            	a.ajax({
            		url : a.cintSampler.options.proxyPath,
            		data: { 
                    	rq : JSON.stringify(obj),
                    	timestamp : $.getTimestamp()  
                    },
                    dataType: "json",
                    type: "GET",
                    success: function (data) {
                    	
                    	loader.hide();
                    	
                    	if(data.cint.orderPurchase.error != undefined) {
                    		if(a.cintSampler.options.orderPurchase.error != undefined 
                    			&& typeof a.cintSampler.options.orderPurchase.error == 'function') {
                    			a.cintSampler.options.orderPurchase.error(data.cint.orderPurchase);
                    		}
                    	} else {
                    		if(a.cintSampler.options.orderPurchase.success != undefined 
                    			&& typeof a.cintSampler.options.orderPurchase.success == 'function') {
                    			a.cintSampler.options.orderPurchase.success(data.cint.orderPurchase);
                    		}
                    	}
                    	
                    }
            	});
            	
            	
            	/*
            	 * ifModified: false,
                    cache: false,
            	 */
            	
            	
            	
            	
            	/*
                a.ajax({
                    url: a.cintSampler.options.proxyPath,
                    data: a.cintSampler.appendData(a.cintSampler.serializeForm(b)),
                    dataType: "json",
                    ifModified: false,
                    cache: false,
                    type: "POST",
                    success: function (d) {
                        if (d.success) {
                            window._gaq.push(["cintlink._trackPageview", "/orders/complete"]);
                            a.cintSampler.container.html(d.body)
                        } else {
                            a.cintSampler.gotoTab(0);
                            c.button({
                                disabled: false
                            });
                            a.cintSampler.container.find("#cds_error_messages").html(d.body).show()
                        }
                    }
                })
                */
                
            } else {
                //a.cintSampler.gotoTab(0);
                c.button({
                    disabled: false
                })
            }
        },
        gotoTab: function (b) {
            a.cintSampler.container.find("#cds-tabs").tabs("select", b)
        },
        appendData: function (c) {
            var d = new Array();
            if (c != null) {
                d.push(c)
            }
            var b = a.cintSampler.setLocale(a.cintSampler.setApiKey(null));
            if (b != null) {
                d.push(a.param(b))
            }
            return d.join("&")
        },
        setApiKey: function (b) {
            if (a.cintSampler.options.mode === "test") {
                return a.extend(b || {}, {
                    api_key: a.cintSampler.options.apiKey
                })
            }
            return b
        },
        setLocale: function (b) {
            if (a.cintSampler.options.language != null) {
                return a.extend(b || {}, {
                    locale: a.cintSampler.options.language
                })
            }
            return b
        },
        getCountry: function () {
            if (a.cintSampler.options.country != null) {
                return a.param({
                    country: a.cintSampler.options.country
                })
            }
            return null
        },
        insertApiKey: function (b) {
            if (a.cintSampler.options.mode !== "test") {
                return b.replace(/^(.*\/\/)(.*)/, "$1" + a.cintSampler.options.apiKey + ".$2")
            }
            return b
        },
        cintAssetsBasePath: function () {
            if (document.location.protocol == "https:") {
                return this.options.baseUrl.replace(/^(.*\/\/)(.*)/, "$1www.$2")
            } else {
                return this.options.baseUrl
            }
        },
        updateApiKey: function (b) {
            a.cintSampler.options.apiKey = b
        }
    })
})(jQuery);
(function (a) {
    a.extend(a.cintSampler, {
        quote_path: function () {
            return "quote/v2.js"
        },
        widget_path: function () {
            return "widget/v3.js"
        }
    });
    a(document).bind("cdsPurchaseEnabled", function () {
        var b = a.cintSampler.container.find("#cds-tabs #buy");
        if (a.cintSampler.container.find("#quote_feasible").length > 0) {
            b.button("enable")
        } else {
            b.button("disable")
        }
    })
})(jQuery);
(function (a) {
    a.extend(a.cintSampler, {
        quote_path: function () {
            return "quote/v2.js"
        },
        widget_path: function () {
            return "widget/v4.js"
        }
    });
    a(document).bind("rebind", function () {
        var e = '<div class="ui-widget ui-widget-header"></div>';
        var g = '<div class="cds-base-font" style="position:absolute; display:none; z-index:99999;"></div>';
        var b = a(g);
        var d = a(g);
        var b;
        a("body").append(b);
        a("body").append(d);

        function f(q, l) {
            var p = q[0];
            var n = q[1];
            var k = p + "-" + n;
            var m = a.cintSampler.container.find("#age_range_slider").slider("option", "max");
            var i = a.cintSampler.container.find("#age_range_slider").slider("option", "min");
            if (n == m) {
                k = p + "+"
            }
            a("#age_range_display").text(k);
            d.html(a(e).text(p));
            b.html(a(e).text(n == m ? "+" : n));
            var j = a.cintSampler.container.find("#age_range_slider");
            var h = j.width();
            var r = j.outerWidth();

            function o(v, t) {
                var u = j.offset();
                var s = (t - i) / (m - i);
                u.left = ((u.left + (r - h) / 2) + h * s) - (v.width() / 2);
                u.top = u.top - 5 - v.height();
                v.offset(u)
            }
            o(b, n);
            o(d, p);
            if (l) {
                a("#age_range_select").val(k).change();
                b.fadeOut();
                d.fadeOut()
            }
        }
        var c = false;
        a.cintSampler.container.find("#age_range_slider").slider({
            min: 15,
            max: 80,
            values: [15, 80],
            range: true,
            slide: function (i, h) {
                b.show();
                d.show();
                f(h.values, false)
            },
            change: function (i, h) {
                f(h.values, true)
            },
            create: function (i, h) {
                f([15, 80], true)
            }
        });
        a.cintSampler.container.find("#number_of_completes_slider").slider({
            min: 10,
            max: 1000,
            step: 10,
            value: 10,
            slide: function (i, h) {
                a.cintSampler.container.find("#number_of_completes_select").val(h.value);
                a.cintSampler.container.find("#number_of_completes_select").valid()
            },
            change: function (i, h) {
                if (!c) {
                    a("#number_of_completes_select").keyup()
                }
            }
        });
        a.cintSampler.container.find("#number_of_completes_select").bind("input textInput propertychange", function () {
            var h = a(this).val();
            c = true;
            a.cintSampler.container.find("#number_of_completes_slider").slider("value", h);
            c = false
        })
    })
})(jQuery);
(function (a) {
    a.extend(a.cintSampler, {
        quote_path: function () {
            return "quote/v3.js"
        },
        widget_path: function () {
            return "widget/v5.js"
        },
        presetInitialState: function () {
            a("#profiling").accordion({
                autoHeight: false,
                collapsible: true,
                animated: false
            })
        },
        parseVersionString: function (f) {
            if (typeof (f) != "string") {
                return false
            }
            var b = f.split(".");
            var d = parseInt(b[0]) || 0;
            var e = parseInt(b[1]) || 0;
            var c = parseInt(b[2]) || 0;
            return {
                major: d,
                minor: e,
                patch: c
            }
        },
        doLoadPrerequisites: function (d) {
            var c = this;
            var b = this.prereqScripts.shift();
            if (b === undefined) {
                d()
            } else {
                if ("https:" == document.location.protocol) {
                    b = b.replace(/^http:/, "https:")
                }
                a.getScript(b, function () {
                    c.doLoadPrerequisites(d)
                })
            }
        },
        loadPrerequisites: function (c) {
            this.prereqScripts = [];
            if (a.ui) {
                var b = this.parseVersionString(a.ui.version);
                if (!(b.major == 1 && b.minor == 8 && b.patch > 11)) {
                    alert("jQuery UI >1.8.11 is required, and this page has already initialized an earlier version.");
                    throw ("Incompatible jQuery UI versions")
                }
            } else {
                //this.prereqScripts.push("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js") // 1.9.2
            	this.prereqScripts.push("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.24/jquery-ui.min.js")
            }
            if (a.validator === undefined) {
                this.prereqScripts.push("http://ajax.microsoft.com/ajax/jquery.validate/1.9/jquery.validate.min.js")
            }
            /*
            if (a.ech === undefined || a.ech.multiselect === undefined) {
                this.prereqScripts.push(this.cintAssetsBasePath() + "javascripts/jquery.multiselect.js")
            }
            */
            this.doLoadPrerequisites(c)
        },
        fieldsToObserve: function () {
            var b = ["country_id", "region_select", "age_range_select", "education_level_select", "gender_select", "occupation_status_select", "number_of_questions", "number_of_completes_select"];
            b = b.concat(a.map(a("select.cds-serialization-exclude"), function (d, c) {
                return d.id
            }));
            return b
        },
        updateOverview: function (e) {
            if (e.match(/^(education_level|occupation_status|GQ)/)) {
                a.cintSampler.updateProfilingOverview(e);
                return
            }
            var c = a.cintSampler.container.find("#" + e + " option:selected");
            var d = "";
            if (c.length == 0) {
                d = a.cintSampler.container.find("#" + e).val()
            } else {
                var g = a.cintSampler.container.find("#" + e + " option").size();
                if (c.length == g) {
                    d = "All"
                } else {
                    var b = a.map(c, function (i, h) {
                        return a(i).text()
                    });
                    d = b.join(", ")
                }
            }
            var f = a.cintSampler.container.find("#overview_" + e);
            if (d == "All") {
                f.closest("li").hide();
                f.hide()
            } else {
                f.html(d);
                f.closest("li").show();
                f.show();
                if (e == "survey_url") {
                    f.attr("href", d)
                }
            }
        },
        translate: function (b) {
            return a.cintSampler.l10n_dictionary[b] || b
        },
        updateProfilingOverview: function (d) {
            var f = a.cintSampler.container.find("#" + d);
            var b = a.cintSampler.container.find("div#cds-overview_profiling");
            var h = b.find("ul.cds-overview");
            var c = d.replace(/_select$/, "");
            var e = f.find("option:selected");
            h.find("#overview_" + c).remove();
            if (f.find("option").length != e.length) {
                var k = a.map(e, function (m, l) {
                    return a(m).text()
                });
                var j = k.join(", ");
                var i = f.siblings("label").html();
                var g = "<li id='overview_{name}'><span class='cds-overview-field-label'>{label}</span><strong class='cds-field-value'>{value}</strong></li>";
                g = g.replace(/{name}/g, c).replace(/{label}/g, i).replace(/{value}/g, j);
                h.append(g)
            }
            if (h.find("li").length > 0) {
                b.show()
            } else {
                b.hide()
            }
        },
        serializeForm: function (b) {
            var c = "";
            if (!(a.cintSampler.container.find("#country_id").val() === "")) {
                c = b.find("*:not(.cds-serialization-exclude input, select.cds-serialization-exclude)").serialize();
                c += a.cintSampler.serializeGlobalVars(b);
                a.cintSampler.debug("submit data: '" + c + "'")
            }
            return c
        },
        serializeGlobalVars: function (e) {
            var c = [];
            var b = "-";
            a.each(e.find("select.cds-serialization-exclude"), function (f, g) {
                var h = a(g).val();
                if (h != null && h.length < a(g).find("option").length) {
                    c = c.concat(h)
                }
            });
            var d = "";
            if (c.length > 0) {
                d = "&GV=" + c.join(b)
            }
            return d
        },
        setValueAndState: function (e) {
            var f = a.cintSampler.container.find(e.id);
            d(f, e.value);
            b(e, f);

            function b(h, i) {
                if (!h.editable && !(h.editable === undefined)) {
                    if (i.closest("#cds-tabs-1").size() > 0) {
                        i.closest("li").hide();
                        i.hide();
                        var g = c(i);
                        g.html(h.value)
                    } else {
                        if (i.closest("#cds-tabs-3").size() > 0) {
                            i.prop("readonly", true)
                        }
                    }
                }
            }function d(h, i) {
                var g = h.val();
                h.val(i);
                if (g !== i) {
                    a.cintSampler.handleOnChange(h, i)
                }
            }function c(i) {
                var h = i.attr("id") + "_label";
                var g = a.cintSampler.container.find("#" + h);
                if (g.size() === 0) {
                    g = a("<strong>").attr("id", h).addClass("cds-field-value word-wrap cds-read-only");
                    i.after(g)
                }
                return g
            }
        }
    });
    a(document).bind("rebind", function () {
    	/*
        a.cintSampler.container.find("select.cds-multiselect").multiselect({
            selectedText: function (d, e, c) {
                var f = "";
                if (d == e) {
                    f = a.cintSampler.translate("prompt_all")
                } else {
                    if (d == 1) {
                        f = c[0].title
                    } else {
                        f = d + " " + a.cintSampler.translate("selected_items_count")
                    }
                }
                return f
            },
            checkAllText: a.cintSampler.translate("check_all"),
            uncheckAllText: a.cintSampler.translate("uncheck_all"),
            noneSelectedText: a.cintSampler.translate("none_selected"),
            beforeclose: function () {
                if (a(this).multiselect("getChecked").length == 0) {
                    a(this).multiselect("checkAll")
                }
                return true
            },
            close: function () {
                a(this).trigger("change")
            },
            classes: "cds-serialization-exclude cds-multiselect-widget cds-base-font",
            position: {
                my: "top",
                at: "bottom"
            }
        });
        */
        a("ul.ui-accordion-content > li > label").click(function (c) {
            a(c.target).siblings("button").click()
        });
        var b = null;
        a(".cds-help-div").appendTo("body");
        a.cintSampler.container.find(".cds-popup-help").live("mouseenter", function (c) {
            clearTimeout(b);
            b = setTimeout(function () {
                a(".cds-help-div").hide();
                var e = a(c.target);
                var d = a("#" + e.attr("data-helpdiv"));
                d.show();
                d.position({
                    my: "left top",
                    at: "right bottom",
                    of: c.target,
                    collision: "fit flip",
                    offset: "5 5"
                })
            }, 500)
        });
        a.cintSampler.container.find(".cds-popup-help").live("mouseleave", function (c) {
            clearTimeout(b);
            a(".cds-help-div").hide()
        });
        a("#country_id").change(function (c) {
            if (a.inArray(a(c.target).val(), ["15", "26", "28"]) > -1) {
                a("#GQ205_item").show()
            } else {
                a("#GQ205_item").hide()
            }
        })
    })
})(jQuery);
(function (a) {
    a.extend(a.cintSampler.options, {
        contactName: {
            editable: true,
            value: ""
        },
        contactEmail: {
            editable: true,
            value: ""
        }
    });
    a.extend(a.cintSampler, {
        handleOnChange: function (c, d) {
            var b = c.attr("id");
            a.cintSampler.updateOverview(b);
            if (b == "country_id") {
                a.cintSampler.handleCountryChange()
            }
            if (a.inArray("#" + b, [this.options.surveyTitle.id, this.options.surveyUrl.id, this.options.contactName.id, this.options.contactEmail.id]) === -1) {
                a.cintSampler.handlePriceUpdate(c, d)
            }
        },
        quote_path: function () {
            return "quote/v4.js"
        },
        widget_path: function () {
            return "widget/v6.js"
        },
        bindPrePopulatedFields: function () {
            a.cintSampler.container.find("#survey_url, #survey_title").change(function () {
                a.cintSampler.updateOverview(a(this).attr("id"))
            })
        },
        fieldsToObserve: function () {
            var b = ["country_id", "region_select", "age_range_select", "education_level_select", "gender_select", "occupation_status_select", "number_of_questions", "number_of_completes_select", "incidence_rate"];
            b = b.concat(a.map(a("select.cds-serialization-exclude"), function (d, c) {
                return d.id
            }));
            return b
        },
        fieldsToValidateForPriceUpdate: function () {
            return ["incidence_rate"]
        },
        setPrePopulatedFields: function () {
            a.cintSampler.options.contactName.id = "#contact_name";
            a.cintSampler.options.contactEmail.id = "#contact_email";
            a.cintSampler.setValueAndState(a.cintSampler.options.surveyTitle);
            a.cintSampler.setValueAndState(a.cintSampler.options.surveyUrl);
            a.cintSampler.setValueAndState(a.cintSampler.options.numberOfQuestions);
            a.cintSampler.setValueAndState(a.cintSampler.options.contactName);
            a.cintSampler.setValueAndState(a.cintSampler.options.contactEmail)
        },
        postPopulateField: function (d, c) {
            var b = a.cintSampler.options[d];
            b.value = c;
            a.cintSampler.setValueAndState(b)
        },
        serializeForm: function (b) {
            var c = "";
            if (!(a.cintSampler.container.find("#country_id").val() === "")) {
                c = b.find("*:not(.cds-serialization-exclude input, select.cds-serialization-exclude)").serialize();
                c += a.cintSampler.serializeStandardDemographic("select#education_level_select");
                c += a.cintSampler.serializeStandardDemographic("select#occupation_status_select");
                c += a.cintSampler.serializeGlobalVars(b);
                a.cintSampler.debug("submit data: '" + c + "'")
            }
            return c
        },
        serializeStandardDemographic: function (c) {
            var d = a(c).val();
            var b = "";
            if (d != null && d.length < a(c).find("option").length) {
                b = "&" + a(c).serialize()
            }
            return b
        },
        serializeGlobalVars: function (e) {
            var c = [];
            var b = "-";
            a.each(e.find("select.cds-serialization-exclude[id^=GQ]"), function (f, g) {
                var h = a(g).val();
                if (h != null && h.length < a(g).find("option").length) {
                    c = c.concat(h)
                }
            });
            var d = "";
            if (c.length > 0) {
                d = "&GV=" + c.join(b)
            }
            return d
        }
    });
    a(document).bind("rebind", function () {
        a("#cds-screen-out-header").click(function () {
            //a("#cds-screen-out-info").slideToggle()
        	a("#cds-screen-out-info").toggle();
        });
        a(".cds-error-list label").live("click", function (b) {
            input_id = a(this).attr("for");
            if (/^contact/.test(input_id)) {
                a("a[href='#cds-tabs-3']").click()
            } else {
                a("a[href='#cds-tabs-1']").click()
            }
        })
    })
})(jQuery);