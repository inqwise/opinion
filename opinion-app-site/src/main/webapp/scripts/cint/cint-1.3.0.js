function CdsLoader() {
    function e() {
        var e = null;
        if (e == null) {
            var t = null,
                n = document.getElementsByTagName("script");
            if (n.length == 0) return null;
            var r = n[n.length - 1];
            if (r === undefined) return null;
            r.getAttribute.length !== undefined ? t = r.src : t = r.getAttribute("src", -1);
            if (t == null || t == "") return null;
            e = t.substring(0, t.indexOf("/", 14)) + "/"
        }
        return e
    }
    this.basePath = e()
}
function BrowserProps() {
    function e() {
        var e = null;
        return navigator && (navigator.language ? e = navigator.language : navigator.browserLanguage ? e = navigator.browserLanguage : navigator.systemLanguage ? e = navigator.systemLanguage : navigator.userLanguage && (e = navigator.userLanguage)), e != null && e.length > 2 && (e = e.substring(0, 2)), e
    }
    this.locale = e()
}(function (e) {
    e.extend(e.fn, {
        reset: function () {
            return this.each(function () {
                (typeof this.reset == "function" || typeof this.reset == "object" && !this.reset.nodeType) && this.reset()
            })
        },
        enable: function () {
            return this.each(function () {
                this.disabled = !1
            })
        },
        disable: function () {
            return this.each(function () {
                this.disabled = !0
            })
        },
        messages: function (t) {
            return this.each(function () {
                e(this).hide();
                if (t === undefined || t === "") t = "message";
                var n = e("<span>").attr("id", t);
                e(this).append(n), n = e(this).find("#" + t), e(this).ajaxStart(function () {
                    n.html(""), e(this).hide()
                }), e(this).ajaxStop(function () {
                    e.trim(n.html()) !== "" && e(this).show()
                })
            })
        }
    })
})(jQuery),
function (e) {
    e.extend(e.fn, {
        delayedObserver: function (t, n) {
            return this.each(function () {
                var r = e(this);
                r.data("oldval", r.formVal()).data("delay", t || .5).data("condition", function () {
                    return e(this).data("oldval") == e(this).formVal()
                }).data("callback", n)[r.fieldEvent(t)](function () {
                    if (r.data("condition").apply(r)) return;
                    r.data("timer") && clearTimeout(r.data("timer")), r.data("timer", setTimeout(function () {
                        r.data("callback")(r, r.formVal())
                    }, r.data("delay") * 1e3)), r.data("oldval", r.formVal())
                })
            })
        },
        fieldEvent: function (t) {
            var n = e(this),
                r = n[0] || n,
                i = "change";
            return r.type == "radio" || r.type == "checkbox" ? i = "click" : t && (r.type == "text" || r.type == "textarea" || r.type == "password") && (i = "keyup"), i
        },
        formVal: function () {
            var t = e(this);
            return t.is("form") ? this.serialize() : t.attr("type") == "checkbox" || t.attr("type") == "radio" ? this.filter("input:checked").val() || "" : this.val()
        },
        spinner: function (t) {
            return this.each(function () {
                var n = "cds_spinner",
                    r = "#" + n,
                    i = e(r);
                return i.exists() || (t = typeof t == "undefined" ? "csd-ui-spinner" : t, e(this).after(e("<div>").attr("id", n).addClass(t))), e(r).ajaxStart(function () {
                    e(this).show()
                }).ajaxStop(function () {
                    e(this).hide()
                }), this
            })
        },
        exists: function () {
            return this.length > 0
        }
    })
}(jQuery);
var cdsLoader = new CdsLoader;
(function (e) {
    e.fn.cintSampler = function (t) {
        return arguments.length == 2 ? e.cintSampler.postPopulateField.apply(e(this), arguments) : this.each(function () {
            e.cintSampler.init(e(this), t)
        })
    }, e.cintSampler = e.cintSampler || {}, e.extend(e.cintSampler, {
        container: null,
        options: {
            baseUrl: cdsLoader.basePath,
            proxyPath: "",
            apiKey: "",
            onErrorEventHandler: null,
            surveyTitle: {
                value: "",
                editable: !0
            },
            surveyUrl: {
                value: "http://",
                editable: !0
            },
            numberOfQuestions: {
                value: "1",
                editable: !0
            },
            mode: null,
            language: null,
            country: null,
            debug: !1
        },
        debug: function (e) {
            this.options.debug && (typeof console == "undefined" && (console = {
                log: function (e) {}
            }), console.log(e))
        },
        presetInitialState: function () {},
        fieldsToObserve: function () {
            return ["country_id", "region_select", "age_range_select", "education_level_select", "gender_select", "occupation_status_select", "number_of_questions", "number_of_completes_select"]
        },
        fieldErrorClass: function () {
            return "cds-error"
        },
        loadPrerequisites: function (t) {
            e.getScript("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js", function () {
                e.getScript("http://ajax.microsoft.com/ajax/jquery.validate/1.8/jquery.validate.min.js", function () {
                    t()
                })
            })
        },
        bindPrePopulatedFields: function () {
            e.cintSampler.container.find("#survey_url, #survey_title").change(function () {
                e.cintSampler.updateOverview(e(this).attr("id"))
            })
        },
        setPrePopulatedFields: function () {
            e.cintSampler.setValueAndState(e.cintSampler.options.surveyTitle), e.cintSampler.setValueAndState(e.cintSampler.options.surveyUrl), e.cintSampler.setValueAndState(e.cintSampler.options.numberOfQuestions)
        },
        init: function (t, n) {
            function r(e) {
                e.surveyTitle.id = "#survey_title", e.surveyUrl.id = "#survey_url", e.numberOfQuestions.id = "#number_of_questions", e.baseUrl = d(e.baseUrl)
            }
            function i() {
                e.ajaxSetup({
                    cache: !0,
                    ifModified: !0
                }), s()
            }
            function s() {
                e(document).ajaxError(function (t, n, r, i) {
                    var s = "Cint Sampler Plugin Error: ";
                    typeof n != "undefined" && (s += n.status + " " + n.statusText), typeof i != "undefined" && (s += i.message), e.cintSampler.debug(s), e.cintSampler.container.html(s), e.isFunction(e.cintSampler.options.onErrorEventHandler) && e.cintSampler.options.onErrorEventHandler(s)
                })
            }
            function o(t) {
                function i(e) {
                    if (e.language != null) return e.language;
                    var t = new BrowserProps;
                    return t.locale
                }
                var n = i(t);
                if (n != null && n !== "en") {
                    var r = n === "sv" ? "se" : n;
                    e.getScript(document.location.protocol + "//ajax.aspnetcdn.com/ajax/jquery.validate/1.8/localization/messages_" + r + ".js", function () {
                        n === "sv" && jQuery.extend(jQuery.validator.messages, {
                            min: jQuery.validator.format("Ange ett v&auml;rde som &auml;r st&ouml;rre eller lika med {0}."),
                            max: jQuery.validator.format("Ange ett v&auml;rde som &auml;r mindre eller lika med {0}.")
                        })
                    })
                }
            }
            function u() {
                e.cintSampler.presetInitialState();
                var t = e.cintSampler.container.find("#cds-tabs");
                t.tabs(), t.find("#cds-gender-selection,#cds-age-selection,#cds-edu-selection,#cds-completes-selection").buttonset(), t.find(".cds-navigation").find("button, input:submit, a").button(), t.find("#cds-next-button-1,#cds-next-button-2,#cds-back-button-1,#cds-back-button-2,#cds-back-button-2,#cds-skip-button-1").click(function () {
                    return t.tabs("select", e(this).attr("href").replace(/^[^#]*/, "")), !1
                })
            }
            function a() {
                e.cintSampler.container.find("#buy").click(function () {
                    e.cintSampler.submit()
                }), e.cintSampler.bindPrePopulatedFields(), l(), c(), h()
            }
            function f() {
                var t = e.cintSampler.fieldsToObserve();
                e.each(t, function (t, n) {
                    e("#" + n).delayedObserver(.25, function (t, n) {
                        e.cintSampler.handleOnChange(t, n)
                    })
                })
            }
            function l() {
                var t = "ui-state-active",
                    n = "ui-state-focus",
                    r = ".ui-state-default",
                    i = function () {
                        e(this).addClass(t), e(this).addClass(n), e(this).blur(function () {
                            e(this).removeClass(t), e(this).removeClass(n)
                        })
                    };
                e(r).click(i).focus(i)
            }
            function c() {
                e.validator.addClassRules({
                    positive_number: {
                        min: 1,
                        digits: !0
                    },
                    number_of_completes: {
                        min: 10,
                        digits: !0
                    },
                    percent: {
                        min: 1,
                        max: 100,
                        digits: !0
                    }
                }), e.cintSampler.mainForm().validate({
                    errorContainer: "#cds_error_messages",
                    errorLabelContainer: "ul.cds-error-list",
                    errorClass: e.cintSampler.fieldErrorClass(),
                    wrapper: "li",
                    ignore: []
                })
            }
            function h() {
                e.cintSampler.container.find(".cds-ui-container").spinner("cds-ui-spinner")
            }
            function p(t) {
                function r(t) {
                    return t !== undefined && e.trim(t) !== ""
                }
                var n = ["proxyPath", "apiKey"];
                e.each(n, function (n, i) {
                    r(t[i]) || e.cintSampler.options.onErrorEventHandler("Error: required parameter '" + i + "' is not set.")
                })
            }
            function d(t) {
                return e.trim(t).replace(/(.*[^\/])$/, "$1/")
            }
            function v() {
                e.cintSampler.setPrePopulatedFields()
            }
            e.extend(this.options, n || {}), this.container = t, this.container.addClass("cds-ui-widget-marker"), r(this.options), i(), this.container.bind("rebind", function () {
                p(e.cintSampler.options), o(n), f(), v(), a(), u()
            }), this.loadPrerequisites(e.cintSampler.load), e(window).unload(function () {
                e(this).empty(), this.options = null
            })
        },
        postPopulateField: function (t, n) {
            var r = e.cintSampler.options[t];
            r && (r.value = n, r.editable = !1, e.cintSampler.setValueAndState(r))
        },
        setValueAndState: function (t) {
            function r(e, t) {
                if (!e.editable && e.editable !== undefined) {
                    t.hide();
                    var n = s(t);
                    n.html(e.value)
                }
            }
            function i(t, n) {
                var r = t.val();
                t.val(n), r !== n && e.cintSampler.handleOnChange(t, n)
            }
            function s(t) {
                var n = t.attr("id") + "_label",
                    r = e.cintSampler.container.find("#" + n);
                return r.size() === 0 && (r = e("<strong>").attr("id", n).addClass("cds-field-value word-wrap cds-read-only"), t.after(r)), r
            }
            var n = e.cintSampler.container.find(t.id);
            i(n, t.value), r(t, n)
        },
        updateregions: function (t) {
            function i(e, t) {
                var n = s("", t);
                for (var r = 0; r < e.length; r++) n += s(e[r].id, e[r].name);
                return n
            }
            function s(e, t) {
                return '<option value="' + e + '">' + t + "</option>"
            }
            function o(t) {
                var n = e.cintSampler.container.find("#country_id").val();
                n === "" ? t.attr("disabled", "disabled") : t.removeAttr("disabled")
            }
            var n = e.cintSampler.container.find("#region_select"),
                r = n.find("option:first").text();
            n.html(i(t, r)), o(n), n.change()
        },
        updateprice: function (t) {
            e.cintSampler.debug(t), e.cintSampler.container.find(".cds-price").html(t.body), e("cds-price").fadeTo(200, 0)
        },
        mainForm: function () {
            return this.container.find("#cds_form")
        },
        updateOverview: function (t) {
            var n = e.cintSampler.container.find("#" + t + " option:selected").text(),
                r = n !== "" && n != null ? n : e.cintSampler.container.find("#" + t).val();
            e.cintSampler.container.find("#overview_" + t).html(r)
        },
        handleRadioButtonEvent: function (t, n) {
            e.cintSampler.container.find("#overview_" + t).html(n), e.cintSampler.handlePriceUpdate(e.cintSampler.container.find("#" + t), n)
        },
        handleCountryChange: function () {
            var t = e.cintSampler.container.find("#country_id").val();
            t === "" ? e.cintSampler.updateregions([]) : (e.cintSampler.container.find("#region_select").val(""), e.ajax({
                url: e.cintSampler.insertApiKey(e.cintSampler.options.baseUrl + "countries/" + t + "/regions.js"),
                global: !1,
                dataType: "jsonp",
                jsonpCallback: "link_widget_regions_load",
                data: e.cintSampler.appendData(null),
                success: function (t) {
                    e.cintSampler.updateregions(t)
                }
            }))
        },
        quote_path: function () {
            return "quote.js"
        },
        handlePriceUpdate: function (t, n) {
            function s(t) {
                var n = e.cintSampler.serializeForm(t);
                return e.cintSampler.debug("data: '" + n + "'"), n
            }
            var r = t.attr("id");
            e.cintSampler.debug("update price: " + r + " value: " + n);
            var i = !0;
            e.each(e.cintSampler.fieldsToValidateForPriceUpdate(), function (t, n) {
                var r = e("#" + n);
                if (r.length > 0) {
                    var s = r.valid();
                    i = i && s
                }
            }), i && e.ajax({
                url: e.cintSampler.insertApiKey(e.cintSampler.options.baseUrl + e.cintSampler.quote_path()),
                global: !1,
                dataType: "jsonp",
                data: e.cintSampler.appendData(s(e.cintSampler.mainForm())),
                success: function (t) {
                    e.cintSampler.updateprice(t)
                }
            })
        },
        fieldsToValidateForPriceUpdate: function () {
            return []
        },
        handleOnChange: function (t, n) {
            var r = t.attr("id");
            e.cintSampler.updateOverview(r), r == "country_id" && e.cintSampler.handleCountryChange(), e.inArray("#" + r, [this.options.surveyTitle.id, this.options.surveyUrl.id]) === -1 && e.cintSampler.handlePriceUpdate(t, n)
        },
        widget_path: function () {
            return "widget/v2.js"
        },
        load: function () {
            e.ajax({
                url: e.cintSampler.insertApiKey(e.cintSampler.options.baseUrl + e.cintSampler.widget_path()),
                global: !1,
                dataType: "jsonp",
                data: e.cintSampler.appendData(e.cintSampler.getCountry()),
                jsonpCallback: "link_widget_load",
                success: function (t) {
                    e.cintSampler.container.html(t.html)
                }
            })
        },
        serializeForm: function (t) {
            var n = "";
            return e.cintSampler.container.find("#country_id").val() !== "" && (n = t.serialize(), e.cintSampler.debug("submit data: '" + n + "'")), n
        },
        submit: function () {
            var t = e.cintSampler.container.find("#buy");
            if (t.button("option", "disabled") === !0) return !1;
            t.button({
                disabled: !0
            });
            var n = e.cintSampler.mainForm();
            n.valid() ? e.ajax({
                url: e.cintSampler.options.proxyPath,
                data: e.cintSampler.appendData(e.cintSampler.serializeForm(n)),
                dataType: "json",
                ifModified: !1,
                cache: !1,
                type: "POST",
                success: function (n) {
                    n.success ? (window._gaq.push(["cintlink._trackPageview", "/orders/complete"]), e.cintSampler.container.html(n.body)) : (e.cintSampler.gotoTab(0), t.button({
                        disabled: !1
                    }), e.cintSampler.container.find("#cds_error_messages").html(n.body).show())
                }
            }) : (e.cintSampler.gotoTab(0), t.button({
                disabled: !1
            }))
        },
        gotoTab: function (t) {
            e.cintSampler.container.find("#cds-tabs").tabs("select", t)
        },
        appendData: function (t) {
            var n = new Array;
            t != null && n.push(t);
            var r = e.cintSampler.setLocale(e.cintSampler.setApiKey(null));
            return r != null && n.push(e.param(r)), n.join("&")
        },
        setApiKey: function (t) {
            return e.cintSampler.options.mode === "test" ? e.extend(t || {}, {
                api_key: e.cintSampler.options.apiKey
            }) : t
        },
        setLocale: function (t) {
            return e.cintSampler.options.language != null ? e.extend(t || {}, {
                locale: e.cintSampler.options.language
            }) : t
        },
        getCountry: function () {
            return e.cintSampler.options.country != null ? e.param({
                country: e.cintSampler.options.country
            }) : null
        },
        insertApiKey: function (t) {
            return e.cintSampler.options.mode !== "test" ? t.replace(/^(.*\/\/)(.*)/, "$1" + e.cintSampler.options.apiKey + ".$2") : t
        },
        cintAssetsBasePath: function () {
            return document.location.protocol == "https:" ? this.options.baseUrl.replace(/^(.*\/\/)(.*)/, "$1www.$2") : this.options.baseUrl
        },
        updateApiKey: function (t) {
            e.cintSampler.options.apiKey = t
        }
    })
})(jQuery),
function (e) {
    e.extend(e.cintSampler, {
        quote_path: function () {
            return "quote/v2.js"
        },
        widget_path: function () {
            return "widget/v3.js"
        }
    }), e(document).bind("cdsPurchaseEnabled", function () {
        var t = e.cintSampler.container.find("#cds-tabs #buy");
        e.cintSampler.container.find("#quote_feasible").length > 0 ? t.button("enable") : t.button("disable")
    })
}(jQuery),
function (e) {
    e.extend(e.cintSampler, {
        quote_path: function () {
            return "quote/v2.js"
        },
        widget_path: function () {
            return "widget/v4.js"
        }
    }), e(document).bind("rebind", function () {
        function s(n, s) {
            function d(e, t) {
                var n = c.offset(),
                    r = (t - l) / (f - l);
                n.left = n.left + (p - h) / 2 + h * r - e.width() / 2, n.top = n.top - 5 - e.height(), e.offset(n)
            }
            var o = n[0],
                u = n[1],
                a = o + "-" + u,
                f = e.cintSampler.container.find("#age_range_slider").slider("option", "max"),
                l = e.cintSampler.container.find("#age_range_slider").slider("option", "min");
            u == f && (a = o + "+"), e("#age_range_display").text(a), i.html(e(t).text(o)), r.html(e(t).text(u == f ? "+" : u));
            var c = e.cintSampler.container.find("#age_range_slider"),
                h = c.width(),
                p = c.outerWidth();
            d(r, u), d(i, o), s && (e("#age_range_select").val(a).change(), r.fadeOut(), i.fadeOut())
        }
        var t = '<div class="ui-widget ui-widget-header"></div>',
            n = '<div class="cds-base-font" style="position:absolute; display:none; z-index:99999;"></div>',
            r = e(n),
            i = e(n),
            r;
        e("body").append(r), e("body").append(i);
        var o = !1;
        e.cintSampler.container.find("#age_range_slider").slider({
            min: 15,
            max: 80,
            values: [15, 80],
            range: !0,
            slide: function (e, t) {
                r.show(), i.show(), s(t.values, !1)
            },
            change: function (e, t) {
                s(t.values, !0)
            },
            create: function (e, t) {
                s([15, 80], !0)
            }
        }), e.cintSampler.container.find("#number_of_completes_slider").slider({
            min: 10,
            max: 1e3,
            step: 10,
            value: 10,
            slide: function (t, n) {
                e.cintSampler.container.find("#number_of_completes_select").val(n.value), e.cintSampler.container.find("#number_of_completes_select").valid()
            },
            change: function (t, n) {
                o || e("#number_of_completes_select").keyup()
            }
        }), e.cintSampler.container.find("#number_of_completes_select").bind("input textInput propertychange", function () {
            var t = e(this).val();
            o = !0, e.cintSampler.container.find("#number_of_completes_slider").slider("value", t), o = !1
        })
    })
}(jQuery),
function (e) {
    e.extend(e.cintSampler, {
        quote_path: function () {
            return "quote/v3.js"
        },
        widget_path: function () {
            return "widget/v5.js"
        },
        presetInitialState: function () {
            e("#profiling").accordion({
                autoHeight: false,
                collapsible: true,
                animated: false
            })
        },
        parseVersionString: function (e) {
            if (typeof e != "string") return !1;
            var t = e.split("."),
                n = parseInt(t[0]) || 0,
                r = parseInt(t[1]) || 0,
                i = parseInt(t[2]) || 0;
            return {
                major: n,
                minor: r,
                patch: i
            }
        },
        doLoadPrerequisites: function (t) {
            var n = this,
                r = this.prereqScripts.shift();
            r === undefined ? t() : ("https:" == document.location.protocol && (r = r.replace(/^http:/, "https:")), e.getScript(r, function () {
                n.doLoadPrerequisites(t)
            }))
        },
        loadPrerequisites: function (t) {
            this.prereqScripts = [];
            if (e.ui) {
                var n = this.parseVersionString(e.ui.version);
                if (!(n.major == 1 && n.minor == 8 && n.patch > 11)) throw alert("jQuery UI >1.8.11 is required, and this page has already initialized an earlier version."), "Incompatible jQuery UI versions"
            } else this.prereqScripts.push("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.24/jquery-ui.min.js");
            e.validator === undefined && this.prereqScripts.push("http://ajax.microsoft.com/ajax/jquery.validate/1.9/jquery.validate.min.js"), 
            /*(e.ech === undefined || e.ech.multiselect === undefined) && this.prereqScripts.push(this.cintAssetsBasePath() + "/assets/jquery.multiselect.js"),*/ 
            this.doLoadPrerequisites(t)
        },
        fieldsToObserve: function () {
            var t = ["country_id", "region_select", "age_range_select", "education_level_select", "gender_select", "occupation_status_select", "number_of_questions", "number_of_completes_select"];
            return t = t.concat(e.map(e("select.cds-serialization-exclude"), function (e, t) {
                return e.id
            })), t
        },
        updateOverview: function (t) {
            if (t.match(/^(education_level|occupation_status|GQ)/)) {
                e.cintSampler.updateProfilingOverview(t);
                return
            }
            var n = e.cintSampler.container.find("#" + t + " option:selected"),
                r = "";
            if (n.length == 0) r = e.cintSampler.container.find("#" + t).val();
            else {
                var i = e.cintSampler.container.find("#" + t + " option").size();
                if (n.length == i) r = "All";
                else {
                    var s = e.map(n, function (t, n) {
                        return e(t).text()
                    });
                    r = s.join(", ")
                }
            }
            var o = e.cintSampler.container.find("#overview_" + t);
            r == "All" ? (o.closest("li").hide(), o.hide()) : (o.html(r), o.closest("li").show(), o.show(), t == "survey_url" && o.attr("href", r))
        },
        translate: function (t) {
            return e.cintSampler.l10n_dictionary[t] || t
        },
        updateProfilingOverview: function (t) {
            var n = e.cintSampler.container.find("#" + t),
                r = e.cintSampler.container.find("div#cds-overview_profiling"),
                i = r.find("ul.cds-overview"),
                s = t.replace(/_select$/, ""),
                o = n.find("option:selected");
            i.find("#overview_" + s).remove();
            if (n.find("option").length != o.length) {
                var u = e.map(o, function (t, n) {
                    return e(t).text()
                }),
                    a = u.join(", "),
                    f = n.siblings("label").html(),
                    l = "<li id='overview_{name}'><span class='cds-overview-field-label'>{label}</span><strong class='cds-field-value'>{value}</strong></li>";
                l = l.replace(/{name}/g, s).replace(/{label}/g, f).replace(/{value}/g, a), i.append(l)
            }
            i.find("li").length > 0 ? r.show() : r.hide()
        },
        serializeForm: function (t) {
            var n = "";
            return e.cintSampler.container.find("#country_id").val() !== "" && (n = t.find("*:not(.cds-serialization-exclude input, select.cds-serialization-exclude)").serialize(), n += e.cintSampler.serializeGlobalVars(t), e.cintSampler.debug("submit data: '" + n + "'")), n
        },
        serializeGlobalVars: function (t) {
            var n = [],
                r = "-";
            e.each(t.find("select.cds-serialization-exclude"), function (t, r) {
                var i = e(r).val();
                i != null && i.length < e(r).find("option").length && (n = n.concat(i))
            });
            var i = "";
            return n.length > 0 && (i = "&GV=" + n.join(r)), i
        },
        setValueAndState: function (t) {
            function r(e, t) {
                if (!e.editable && e.editable !== undefined) if (t.closest("#cds-tabs-1").size() > 0) {
                    t.closest("li").hide(), t.hide();
                    var n = s(t);
                    n.html(e.value)
                } else t.closest("#cds-tabs-3").size() > 0 && t.prop("readonly", !0)
            }
            function i(t, n) {
                var r = t.val();
                t.val(n), r !== n && e.cintSampler.handleOnChange(t, n)
            }
            function s(t) {
                var n = t.attr("id") + "_label",
                    r = e.cintSampler.container.find("#" + n);
                return r.size() === 0 && (r = e("<strong>").attr("id", n).addClass("cds-field-value word-wrap cds-read-only"), t.after(r)), r
            }
            var n = e.cintSampler.container.find(t.id);
            i(n, t.value), r(t, n)
        }
    }), e(document).bind("rebind", function () {
        /*
    	e.cintSampler.container.find("select.cds-multiselect").multiselect({
            selectedText: function (t, n, r) {
                var i = "";
                return t == n ? i = e.cintSampler.translate("prompt_all") : t == 1 ? i = r[0].title : i = t + " " + e.cintSampler.translate("selected_items_count"), i
            },
            checkAllText: e.cintSampler.translate("check_all"),
            uncheckAllText: e.cintSampler.translate("uncheck_all"),
            noneSelectedText: e.cintSampler.translate("none_selected"),
            beforeclose: function () {
                return e(this).multiselect("getChecked").length == 0 && e(this).multiselect("checkAll"), !0
            },
            close: function () {
                e(this).trigger("change")
            },
            classes: "cds-serialization-exclude cds-multiselect-widget cds-base-font",
            position: {
                my: "top",
                at: "bottom"
            }
        }), 
        */
        e("ul.ui-accordion-content > li > label").click(function (t) {
            e(t.target).siblings("button").click()
        });
        var t = null;
        e(".cds-help-div").appendTo("body"), e.cintSampler.container.find(".cds-popup-help").live("mouseenter", function (n) {
            clearTimeout(t), t = setTimeout(function () {
                e(".cds-help-div").hide();
                var t = e(n.target),
                    r = e("#" + t.attr("data-helpdiv"));
                r.show(), r.position({
                    my: "left top",
                    at: "right bottom",
                    of: n.target,
                    collision: "fit flip",
                    offset: "5 5"
                })
            }, 500)
        }), e.cintSampler.container.find(".cds-popup-help").live("mouseleave", function (n) {
            clearTimeout(t), e(".cds-help-div").hide()
        }), e("#country_id").change(function (t) {
            e.inArray(e(t.target).val(), ["15", "26", "28"]) > -1 ? e("#GQ205_item").show() : e("#GQ205_item").hide()
        })
    })
}(jQuery),
function (e) {
    e.extend(e.cintSampler.options, {
        contactName: {
            editable: !0,
            value: ""
        },
        contactEmail: {
            editable: !0,
            value: ""
        }
    }), e.extend(e.cintSampler, {
        handleOnChange: function (t, n) {
            var r = t.attr("id");
            e.cintSampler.updateOverview(r), r == "country_id" && e.cintSampler.handleCountryChange(), e.inArray("#" + r, [this.options.surveyTitle.id, this.options.surveyUrl.id, this.options.contactName.id, this.options.contactEmail.id]) === -1 && e.cintSampler.handlePriceUpdate(t, n)
        },
        quote_path: function () {
            return "quote/v4.js"
        },
        widget_path: function () {
            return "widget/v6.js"
        },
        bindPrePopulatedFields: function () {
            e.cintSampler.container.find("#survey_url, #survey_title").change(function () {
                e.cintSampler.updateOverview(e(this).attr("id"))
            })
        },
        fieldsToObserve: function () {
            var t = ["country_id", "region_select", "age_range_select", "education_level_select", "gender_select", "occupation_status_select", "number_of_questions", "number_of_completes_select", "incidence_rate"];
            return t = t.concat(e.map(e("select.cds-serialization-exclude"), function (e, t) {
                return e.id
            })), t
        },
        fieldsToValidateForPriceUpdate: function () {
            return ["incidence_rate"]
        },
        setPrePopulatedFields: function () {
            e.cintSampler.options.contactName.id = "#contact_name", e.cintSampler.options.contactEmail.id = "#contact_email", e.cintSampler.setValueAndState(e.cintSampler.options.surveyTitle), e.cintSampler.setValueAndState(e.cintSampler.options.surveyUrl), e.cintSampler.setValueAndState(e.cintSampler.options.numberOfQuestions), e.cintSampler.setValueAndState(e.cintSampler.options.contactName), e.cintSampler.setValueAndState(e.cintSampler.options.contactEmail)
        },
        postPopulateField: function (t, n) {
            var r = e.cintSampler.options[t];
            r.value = n, e.cintSampler.setValueAndState(r)
        },
        serializeForm: function (t) {
            var n = "";
            return e.cintSampler.container.find("#country_id").val() !== "" && (n = t.find("*:not(.cds-serialization-exclude input, select.cds-serialization-exclude)").serialize(), n += e.cintSampler.serializeStandardDemographic("select#education_level_select"), n += e.cintSampler.serializeStandardDemographic("select#occupation_status_select"), n += e.cintSampler.serializeGlobalVars(t), e.cintSampler.debug("submit data: '" + n + "'")), n
        },
        serializeStandardDemographic: function (t) {
            var n = e(t).val(),
                r = "";
            return n != null && n.length < e(t).find("option").length && (r = "&" + e(t).serialize()), r
        },
        serializeGlobalVars: function (t) {
            var n = [],
                r = "-";
            e.each(t.find("select.cds-serialization-exclude[id^=GQ]"), function (t, r) {
                var i = e(r).val();
                i != null && i.length < e(r).find("option").length && (n = n.concat(i))
            });
            var i = "";
            return n.length > 0 && (i = "&GV=" + n.join(r)), i
        }
    }), e(document).bind("rebind", function () {
        e("#cds-screen-out-header").click(function () {
            //e("#cds-screen-out-info").slideToggle()
        	e("#cds-screen-out-info").toggle();
        }), e(".cds-error-list label").live("click", function (t) {
            input_id = e(this).attr("for"), /^contact/.test(input_id) ? e("a[href='#cds-tabs-3']").click() : e("a[href='#cds-tabs-1']").click()
        })
    })
}(jQuery);