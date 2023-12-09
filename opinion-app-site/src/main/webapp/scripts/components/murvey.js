(function (b) {
    b.fn.opWidget = function (d, e) {
        var c = this;
        this.each(function (f) {
            if (d[e[0]]) {
                c = d[e[0]].apply(this, Array.prototype.slice.call(e, 1))
            } else {
                if (typeof e[0] === "object" || !e[0]) {
                    d.init.apply(this, e)
                }
            }
        });
        return (c != undefined ? c : this)
    };
    b.isTouchPhone = function () {
        return window.orientation == "0"
    };
    b.touchstart = (jQuery.isTouchPhone() ? "touchstart" : "mousedown");
    b.touchmove = (jQuery.isTouchPhone() ? "touchmove" : "mousemove");
    b.touchend = (jQuery.isTouchPhone() ? "touchend" : "mouseup");
    b.addEventListener = function (d, c, e) {
        if (d.attachEvent) {
            d.attachEvent("on" + c, e)
        } else {
            d.addEventListener(c, e, false)
        }
    };
    if (!b.ObjectPlanet) {
        b.ObjectPlanet = {}
    }
    b.ObjectPlanet.stringEndsWith = function (c, d) {
        if (d && d.length > 0) {
            return c.lastIndexOf(d) >= 0 && c.lastIndexOf(d) + d.length == c.length
        }
        return false
    };
    b.ObjectPlanet.KEY = {
        UP: 38,
        DOWN: 40,
        LEFT: 37,
        RIGHT: 39,
        SPACE: 32,
        BACKSPACE: 8,
        DELETE: 46,
        TAB: 9,
        PRINT: 44,
        ENTER: 13,
        ESC: 27,
        DOT: 190,
        COMMA: 188
    };
    b.ObjectPlanet.unitLess = function (c, d) {
        if (d == undefined) {
            d = 0
        }
        if (c != undefined && (b.ObjectPlanet.stringEndsWith(c, "px") || b.ObjectPlanet.stringEndsWith(c, "pt") || b.ObjectPlanet.stringEndsWith(c, "em"))) {
            return c.substring(0, c.length - 2) - 0
        }
        return d
    };
    b.ObjectPlanet.setCookie = function (c, i, j) {
        var g = "";
        if (j != null && j - 0 == j) {
            var d = 1000;
            var k = 60 * d;
            var f = 60 * k;
            var h = 24 * f;
            var e = new Date().valueOf() + (j * h);
            g = "; expires=" + new Date(e).toGMTString()
        }
        document.cookie = c + "=" + i + g
    };
    b.ObjectPlanet.getCookie = function (e) {
        var f = document.cookie.split(";");
        for (var d = 0; f != null && d < f.length; d++) {
            var c = f[d].substr(0, f[d].indexOf("="));
            var g = f[d].substr(f[d].indexOf("=") + 1);
            if (b.trim(e) == b.trim(c)) {
                return g
            }
        }
        return null
    };
    b.ObjectPlanet.isValidEmail = function (d) {
        var c = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
        return c.test(d)
    };
    b.ObjectPlanet.spaces = function (c) {
        if (c == null) {
            return c
        }
        return c.replace(/ (?= )/g, "&nbsp;")
    };
    b.ObjectPlanet.wait = function (c, e, d) {
        if (!c()) {
            setTimeout(function () {
                b.ObjectPlanet.wait(c, e, d)
            }, d != null ? d : 5)
        } else {
            e()
        }
    };
    if (!b.ObjectPlanet.html) {
        b.ObjectPlanet.html = {}
    }
    b.ObjectPlanet.html.tagBody = "(?:[^\"'>]|\"[^\"]*\"|'[^']*')*";
    b.ObjectPlanet.html.tagOrComment = new RegExp("<(?:!--(?:(?:-*[^->])*--+|-?)|script\\b" + b.ObjectPlanet.html.tagBody + ">[\\s\\S]*?<\/script\\s*|style\\b" + b.ObjectPlanet.html.tagBody + ">[\\s\\S]*?</style\\s*|/?[a-z]" + b.ObjectPlanet.html.tagBody + ")>", "gi");
    b.ObjectPlanet.html.removeTags = function (c) {
        if (typeof c != "string") {
            return c
        }
        var d;
        do {
            d = c;
            c = c.replace(b.ObjectPlanet.html.tagOrComment, "")
        } while (c !== d);
        return c.replace(/</g, "&lt;")
    };
    var a = b.fn.val;
    b.fn.val = function (c) {
        if (c != undefined) {
            return a.apply(this, [c])
        }
        var d = a.apply(this);
        if (d != null && !b(this).attr("readonly") && !b(this).attr("encoded")) {
            d = b.ObjectPlanet.html.removeTags(d)
        }
        return d
    };
    b.ObjectPlanet.decode = function (d) {
        try {
            return decodeURIComponent(d)
        } catch (c) {
            return d
        }
    };
    if (b.ObjectPlanet.css == null) {
        b.ObjectPlanet.css = {}
    }
    b.ObjectPlanet.css.iOS = function (c) {
        if (b.ObjectPlanet.stringEndsWith(c, "px")) {
            return (b.ObjectPlanet.unitLess(c) + 5) + "px"
        }
        if (b.ObjectPlanet.stringEndsWith(c, "pt")) {
            return (b.ObjectPlanet.unitLess(c) + 5) + "pt"
        }
        return c
    };
    b.ObjectPlanet.css.getValues = function (g, d) {
        if (g == null) {
            return ""
        }
        var f = "";
        if (d == null || d.width == true) {
            if (g.width != null) {
                f += " width: " + g.width + ";"
            }
        }
        if (d == null || d.font == true) {
            if (b.ObjectPlanet.handheld != true && g["font-family"] != null) {
                f += " font-family: " + g["font-family"] + ";"
            }
            if (b.ObjectPlanet.handheld != true && g["font-size"] != null) {
                f += " font-size: " + g["font-size"] + ";"
            }
            if (b.ObjectPlanet.handheld == true) {
                f += " font-family: Helvetica;"
            }
            if (b.ObjectPlanet.handheld == true && g["font-size"] != null) {
                f += " font-size: " + b.ObjectPlanet.css.iOS(g["font-size"]) + ";"
            }
            if (b.ObjectPlanet.handheld == true && g["font-size"] == null) {
                f += " font-size: 16px;"
            }
            if (g["font-weight"] != null) {
                f += " font-weight: " + g["font-weight"] + ";"
            }
            if (g.color != null) {
                f += " color: " + g.color + ";"
            }
            if (g["line-height"] != null) {
                f += " line-height: " + g["line-height"] + ";"
            }
        }
        if (d == null || d.background == true) {
            if (g["background-color"] != null && g["background-color"].indexOf(",") > 0) {
                var h = g["background-color"];
                var i = h.substring(0, h.indexOf(","));
                var e = h.substring(h.indexOf(",") + 1);
                f += " background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%," + i + "), color-stop(100%," + e + "));";
                f += " background-image: -moz-linear-gradient(" + i + "," + e + ");";
                f += " background-image: -o-linear-gradient(" + i + "," + e + ");";
                f += " background-image: -ms-linear-gradient(" + i + "," + e + ");";
                f += " background-color: " + i + ";"
            } else {
                if (g["background-color"] != null) {
                    f += " background-color: " + g["background-color"] + ";"
                }
            }
        }
        if (d == null || d.border == true) {
            if (b.ObjectPlanet.handheld != true && g["border-color"] != null) {
                f += " border-color: " + g["border-color"] + ";"
            }
            if (b.ObjectPlanet.handheld == true && g["border-color"] != null) {
                f += " border-color: #aaa;"
            }
            if (g["border-width"] != null) {
                f += " border-width: " + g["border-width"] + ";"
            }
            if (g["border-style"] != null) {
                f += " border-style: " + g["border-style"] + ";"
            }
            if (g["border-radius"] != null) {
                var c = g["border-radius"];
                f += " -webkit-border-radius: " + c + "; -moz-border-radius: " + c + "; border-radius: " + c + ";"
            }
        }
        if ((d == null || d.shadow == true) && g.shadow != null) {
            f += " -webkit-box-shadow: " + g.shadow + ";";
            f += " -moz-box-shadow: " + g.shadow + ";";
            f += " box-shadow: " + g.shadow + ";"
        }
        if (d == null || d.padding == true) {
            if (g.padding != null) {
                f += " padding: " + g.padding + ";"
            }
        }
        return f
    };
    b.ObjectPlanet.css.get = function (d) {
        if (d == null) {
            return ""
        }
        var c = "";
        c += ".op-web-theme-page {" + b.ObjectPlanet.css.getValues(d.page) + " }\n";
        c += ".op-web-theme-font {" + b.ObjectPlanet.css.getValues(d.survey, {
            font: true
        }) + " }\n";
        c += ".op-web-theme-survey {" + b.ObjectPlanet.css.getValues(d.survey, {
            width: true,
            padding: true
        }) + " margin-left: auto; margin-right: auto; ";
        if (d.survey.width != null) {
            c += "max-width: " + d.survey.width + "; "
        }
        c += "}\n";
        c += ".op-web-theme-survey-background {" + b.ObjectPlanet.css.getValues(d.survey, {
            background: true
        }) + " }\n";
        c += ".op-web-theme-survey-border {" + b.ObjectPlanet.css.getValues(d.survey, {
            border: true,
            shadow: true
        }) + " }\n";
        c += ".op-web-theme-heading {" + b.ObjectPlanet.css.getValues(d.heading) + " }\n";
        c += ".op-web-theme-introduction {" + b.ObjectPlanet.css.getValues(d.introduction) + " }\n";
        c += ".op-web-theme-question-text {" + b.ObjectPlanet.css.getValues(d["question-text"]) + " }\n";
        c += ".op-web-theme-question-response {" + b.ObjectPlanet.css.getValues(d.response) + " }\n";
        c += ".op-web-theme-thankyou {" + b.ObjectPlanet.css.getValues(d.thankYou) + " }\n";
        c += ".op-web-theme-submit-button {" + b.ObjectPlanet.css.getValues(d["submit-button"]) + " }\n";
        c += ".op-web-theme-input {" + b.ObjectPlanet.css.getValues(d.input) + " }\n";
        c += ".op-web-theme-select {" + b.ObjectPlanet.css.getValues(d.input, {
            font: true,
            background: true
        }) + " }\n";
        c += ".op-web-theme-validation { color: #ee1111; }\n";
        c += ".op-web-theme-validation-marker { background-color: rgba(255,0,0,0.2); -webkit-border-radius: 5px; }\n";
        return c
    };
    Array.prototype.shuffle = function () {
        var d = this.length,
            c, f, e;
        if (d == 0) {
            return this
        }
        while (--d) {
            c = Math.floor(Math.random() * (d + 1));
            f = this[d];
            e = this[c];
            this[d] = e;
            this[c] = f
        }
        return this
    };
    b.ObjectPlanet.style = function (c, e) {
        var d = null;
        if (c && c.style && c.style[e] != null && c.style[e].length > 0) {
            d = c.style[e]
        }
        d = d == null ? b(c).css(e) : d;
        d = d == null ? document.defaultView.getComputedStyle(c, null)[e] : d;
        return d
    };
    b.fn.highlight = function (d) {
        if (this.cover != null) {
            return
        }
        d = typeof d == "number" ? d : b.ObjectPlanet.SPEED * 4;
        if (b((this).css("position") == "static")) {
            b(this).css("position", "relative")
        }
        this.cover = b('<div class="highlight-cover"></div>').css({
            position: "absolute",
            "border-radius": "5px",
            width: b(this).width() + "px",
            height: b(this).height() + "px",
            top: "0px",
            left: "0px",
            opacity: "0.4",
            "background-color": "#fff"
        }).appendTo(this).get(0);
        var c = this;
        b(this.cover).animate({
            opacity: "0"
        }, d, function () {
            b(c.cover).remove();
            delete c.cover
        })
    };
    b.ObjectPlanet.globalErrorHandler = function (d) {
        if (b.fn.progressIndicator != null) {
            b(document).find(".progressIndicator-back").each(function () {
                b(this.parentNode).progressIndicator("stop")
            })
        }
        if (d != null && typeof d.url == "string" && d.url.indexOf("objectplanet.opinio") >= 0) {
            b("#statusLabel").text("An error occured, we will try to fix it soon!");
            var c = d.file != null ? ("file: " + d.file + "\n") : "";
            c += "error: " + d.error + "\nurl: " + d.url + "\nline: " + d.line;
            if (b.ObjectPlanet.session != null) {
                c += "\nlogin: " + b.ObjectPlanet.session.login
            }
            if (d.json && d.json.surveyName != null) {
                c += "\nsurvey: " + d.json.surveyName
            }
            if (d.json && d.json._id != null && d.json._id.$oid != null) {
                c += "\nsid: " + d.json._id.$oid
            }
            if (d.search == null) {
                d.search = document.location.search
            }
            if (d.search != null) {
                c += "\n" + d.search
            }
            c += "\n" + navigator.userAgent;
            if (b.ObjectPlanet.reportedErrors == null) {
                b.ObjectPlanet.reportedErrors = {}
            }
            if (b.ObjectPlanet.reportedErrors[c] == null) {
                b.ObjectPlanet.reportedErrors[c] = c;
                if (b.serverRequest != null) {
                    b.serverRequest({
                        action: "error.send",
                        error: c
                    })
                } else {
                    b.getJSON((d.base != null ? d.base : "") + "/a?action=error.send&error=" + encodeURIComponent(c))
                }
            }
        }
    }
})(jQuery);
(function (a) {
    a.ObjectPlanet.survey = function (b, c) {
        window.onerror = function (h, g, f) {
            a.ObjectPlanet.globalErrorHandler({
                error: h,
                url: g,
                line: f,
                search: b,
                file: "survey.js",
                base: c
            })
        };
        var e = (b != null ? b : document.location.search);
        var d = e != null && e.indexOf("&fill=true") > 0;
        if (e != null && e.length > 1) {
            e = e.substring(1)
        }
        if (e != null && e.indexOf("&") > 0) {
            e = e.substring(0, e.indexOf("&"))
        }
        a.ObjectPlanet.iOS = (navigator.userAgent.match(/(iPad|iPhone|iPod)/g) ? true : false);
        a.ObjectPlanet.android = (navigator.userAgent.match(/(Android)/g) ? true : false);
        a.ObjectPlanet.handheld = a.ObjectPlanet != null && (a.ObjectPlanet.iOS == true || a.ObjectPlanet.android == true);
        if (window.parent.postMessage && a.ObjectPlanet.getCookie(e) == "true") {
            window.parent.postMessage("true", "*")
        }
        if (a.browser.mozilla) {
            window.addEventListener("message", function (f) {
                if (f.data == "opened") {
                    var h = a(document.body).find(".op-web-survey-iframe-container").get(0);
                    if (a(h).outerHeight() != a(document.body).height()) {
                        var g = a(h).outerHeight() - a(h).height();
                        a(h).css("min-height", a(document.body).height() - g + "px")
                    }
                }
            }, false)
        }
        a.getJSON((c != null ? c : "") + "s?" + e + "&json=true", function (l) {
            var i = a('head meta[property="murvey:sid"]').get(0);
            var f = a('head meta[property="murvey:skey"]').get(0);
            if (l && i && i.content && f && f.content) {
                l.session = {
                    sid: i.content,
                    skey: f.content
                }
            }
            if (l && l.appearance && l.appearance.css) {
                try {
                    a("#surveyTheme").html(a.ObjectPlanet.css.get(l.appearance.css));
                    a("#surveyThemeFile").remove()
                } catch (k) {}
            }
            if (d == true || a.ObjectPlanet.handheld == true) {
                var h = a('<div class="op-web-survey-iframe-container op-web-theme-survey-background op-web-survey-borderbox"></div>');
                var m = a(a.webSurvey(l)).webSurvey(l).addClass("op-web-theme-survey").css("width", "100%").appendTo(h).get(0);
                a(document.body).css({
                    margin: "0px",
                    padding: "0px"
                }).append(h);
                a(m).webSurvey("adjust");
                if (window.parent != null && window.parent.postMessage != null) {
                    window.parent.postMessage("opened", "*")
                }
                if (a.ObjectPlanet.iOS == true) {
                    a(m).css({
                        "padding-left": "5px",
                        "padding-right": "5px"
                    })
                }
                a(h).css("min-height", "100%");
                setTimeout(function () {
                    a(document.body).css("height", "100%");
                    if (a(h).outerHeight() < a(document.body).height() && a.browser.opera) {
                        a(h).css("height", "100%")
                    }
                    a(m).webSurvey("paint")
                }, 10)
            } else {
                var j = a('<div class="op-web-survey-page op-web-theme-page op-web-survey-borderbox"><a href="https://www.murvey.com" target="_blank" class="op-web-survey-poweredby op-web-survey-borderbox">Powered by Murvey<sup>tm</sup> online surveys</a></div>').appendTo(document.body).get(0);
                var g = a(a.webSurvey(l)).addClass("op-web-theme-survey op-web-theme-survey-border op-web-theme-survey-background").webSurvey(l);
                g.prependTo(j).webSurvey("adjust");
                a(document.body).css("height", "100%")
            }
        })
    }
})(jQuery);
(function (b) {
    b.ObjectPlanet.popupSurvey = function (k, n) {
        if (k != null && k._id != null && k._id.$oid != null && b.ObjectPlanet.getCookie(k._id.$oid) == "true" && (n == null || n.preventResponse != true)) {
            return
        }
        var e = new Date().valueOf();
        if (k != null && k.surveySetup != null && (k.surveySetup.start > e || k.surveySetup.stop < e) && (n == null || n.preventResponse != true)) {
            return
        }
        if ((n == null || n.random != false) && k != null && k.publish != null && k.publish.popup != null && (n == null || n.preventResponse != true)) {
            if (k.publish.popup.random === 0) {
                return
            }
            if (k.publish.popup.random != null && Math.floor(Math.random() * k.publish.popup.random) != 0) {
                return
            }
        }
        var h = (k != null && k.publish != null && k.publish.popup != null && (k.publish.popup.heading != null && k.publish.popup.heading.def != "" || k.publish.popup.introduction != null && k.publish.popup.introduction.def != ""));
        if (n != null && n.requestOn == false) {
            h = false
        }
        var f = b('<div class="op-web-survey-popup-background"><div class="op-web-survey-popup-frame op-web-survey-borderbox"><span class="op-web-survey-popup-close"></span><div class="op-web-survey-popup-box"></div></div></div>').get(0);
        f.frame = b(f).find(".op-web-survey-popup-frame").addClass(h ? "op-web-survey-popup-frame-message" : "op-web-survey-popup-frame-survey").get(0);
        f.close = b(f.frame).find(".op-web-survey-popup-close").bind("click", function () {
            b(f).remove()
        }).get(0);
        f.close.icon = b(a()).appendTo(f.close).get(0);
        f.box = b(f.frame).find(".op-web-survey-popup-box").get(0);
        b(f).bind("click", function (o) {
            if (o.target === f) {
                b(f).remove()
            }
        });
        if (h == true) {
            f.request = b('<div class="op-web-survey-popup-request op-web-survey-popup-request-gradient op-web-survey-borderbox"><label class="heading">' + k.publish.popup.heading.def + '</label><div class="intro">' + k.publish.popup.introduction.def.replace(/\n/g, "<br>") + '</div><div class="buttons"><label class="later">' + k.publish.popup.later.def + '</label><label class="yes">' + k.publish.popup.yes.def + '</label><label class="no">' + k.publish.popup.no.def + "</label></div></div>").get(0);
            f.request.heading = b(f.request).find("label.heading").get(0);
            f.request.intro = b(f.request).find("div.intro").get(0);
            f.request.later = b(f.request).find("label.later").toggle(k.publish.popup.later != null && k.publish.popup.later.def != "").bind("click", function () {
                b(f).remove()
            }).get(0);
            f.request.yes = b(f.request).find("label.yes").get(0);
            f.request.no = b(f.request).find("label.no").bind("click", function () {
                b(f).remove();
                if (k && k._id && k._id.$oid && b.ObjectPlanet.testMode != true && (n == null || n.preventResponse != true)) {
                    b.ObjectPlanet.setCookie(k._id.$oid, "true", 1095)
                }
            }).get(0);
            b(f.request.yes).bind("click", function () {
                b(f.frame).width(b(f.frame).outerWidth());
                b(f.frame).height(b(f.frame).outerHeight());
                b(f.request).hide();
                b(f.container).show();
                b(f.box).css("height", "100%");
                b(f.survey).webSurvey("adjust");
                var o = false;
                b(f.frame).bind("webkitTransitionEnd oTransitionEnd transitionend", function () {
                    if (!o) {
                        b(this).css({
                            height: "80%",
                            left: "50%",
                            top: "10%"
                        });
                        o = true;
                        if (b(f.container).is("iframe") && f.container.contentWindow.postMessage) {
                            f.container.contentWindow.postMessage("opened", "*")
                        }
                    }
                });
                b(f.frame).toggleClass("op-web-survey-popup-frame-transition", !f.frame.testMode);
                var q = k && k.appearance && k.appearance.css != null ? k.appearance.css : null;
                var p = {
                    w: b(window).width(),
                    h: b(window).height()
                };
                var r = q && q.survey && q.survey.width != null ? q.survey.width : "650px";
                if (b.ObjectPlanet.stringEndsWith(r, "px")) {
                    r = b.ObjectPlanet.unitLess(r) + 30 + "px"
                }
                var s = {
                    width: r,
                    height: Math.round(p.h * 0.8) + "px",
                    left: Math.round(p.w * 0.5) + "px",
                    "margin-left": -Math.round(b.ObjectPlanet.unitLess(r) / 2) + "px",
                    top: Math.round(p.h * 0.1) + "px"
                };
                if (r != null && r.length > 1 && r.charAt(r.length - 1) == "%") {
                    s["margin-left"] = -(r.substring(0, r.length - 1) / 2) + "%"
                }
                b(f.frame).removeClass("op-web-survey-popup-frame-message").addClass("op-web-survey-popup-frame-survey").css(s)
            })
        }
        var g = k && k.appearance && k.appearance.css != null ? k.appearance.css : null;
        var i = {
            w: b(window).width(),
            h: b(window).height()
        };
        var d = g && g.survey && g.survey.width != null ? g.survey.width : "650px";
        if (b.ObjectPlanet.stringEndsWith(d, "px")) {
            d = b.ObjectPlanet.unitLess(d) + 30 + "px"
        }
        var j = {
            width: h ? "500px" : d,
            height: h ? "auto" : "80%",
            left: "50%",
            top: h ? "auto" : "10%",
            "margin-left": h ? "-250px" : -b.ObjectPlanet.unitLess(d) / 2 + "px"
        };
        if (d != null && d.length > 1 && d.charAt(d.length - 1) == "%" && !h) {
            j["margin-left"] = -(d.substring(0, d.length - 1) / 2) + "%"
        }
        b(f.frame).css(j);
        var c = n != null && n.base != null ? n.base : "";
        if (k == null || k._id == null || k._id.$oid == null || b.ObjectPlanet.testMode == true || (n && n.preventResponse == true)) {
            f.survey = b(b.webSurvey(k, {
                preventResponse: true
            })).webSurvey(k, {
                preventResponse: true
            }).get(0);
            b(f.survey).addClass("op-web-theme-survey").css({
                width: "100%",
                "max-width": "100%"
            });
            f.container = b("<div></div>").addClass("op-web-survey-iframe op-web-survey-iframe-container op-web-theme-survey-background op-web-survey-borderbox").css("overflow-y", "auto").html(f.survey).get(0)
        } else {
            var m = c.charAt(c.length - 1) == "/" ? c + "s?" : c + "/s?";
            f.container = b('<iframe class="op-web-survey-iframe" frameborder="0" src="' + m + k._id.$oid + '&fill=true"></iframe>').get(0);
            var l = function (o) {
                if (o.data == "submit") {
                    b.ObjectPlanet.setCookie(k._id.$oid, "true", 1095)
                } else {
                    if (o.data == "true") {
                        b(f).remove()
                    } else {
                        if (o.data == "opened") {
                            b(f).show();
                            if (h) {
                                b(f.frame).css("top", Math.max(25, Math.round(i.h * 0.4 - b(f.frame).outerHeight() / 2)) + "px")
                            }
                        }
                    }
                }
            };
            if (window.attachEvent) {
                window.attachEvent("onmessage", l)
            } else {
                window.addEventListener("message", l, false)
            }
        } if (!h) {
            b(f.box).css("height", "100%")
        }
        b(f.box).append(b(f.request).toggle(h));
        b(f.box).append(b(f.container).toggle(!h));
        b(f).appendTo("body").toggle(!b(f.container).is("iframe"));
        if (h) {
            b(f.frame).css("top", Math.max(25, Math.round(i.h * 0.4 - b(f.frame).outerHeight() / 2)) + "px")
        } else {
            if (b(f.container).is("div")) {
                b(f.survey).webSurvey("adjust")
            }
        }
        return f
    };
    var a = function () {
        var c = b('<canvas class="op-web-survey-popup-close-icon"></canvas>').get(0);
        if (c.getContext != null) {
            c.width = c.height = 16;
            var d = c.getContext("2d");
            d.strokeStyle = "white";
            d.lineCap = "round";
            d.lineWidth = 2.25;
            var e = 5;
            d.moveTo(e, e);
            d.lineTo(c.width - e, c.height - e);
            d.moveTo(c.width - e, e);
            d.lineTo(e, c.height - e);
            d.stroke()
        } else {
            c = b('<span class="op-web-survey-popup-close-icon">x</span>').get(0)
        }
        return c
    }
})(jQuery);
(function (b) {
    b.webSurvey = function (l, o) {
        var m = '<div class="op-web-survey-borderbox op-web-theme-font">';
        var f = new Date().valueOf();
        var e = "";
        if ((o == null || o.preventResponse != true) && l != null && l._id && b.ObjectPlanet.getCookie(l._id.$oid) == "true" && !l.session) {
            m += '<p class="op-web-survey-already-responded">You can only respond once to this survey</p>'
        } else {
            if (l && l.surveySetup && l.surveySetup.start > f && (o == null || o.preventResponse != true)) {
                m += '<label class="op-web-survey-info op-web-theme-font">This survey has not yet started</label>'
            } else {
                if (l && l.surveySetup && l.surveySetup.stop < f && (o == null || o.preventResponse != true)) {
                    m += '<label class="op-web-survey-info op-web-theme-font">This survey has ended</label>'
                } else {
                    var n = (l && l.surveyTexts && l.surveyTexts.surveyHeading ? b.ObjectPlanet.spaces(l.surveyTexts.surveyHeading.def) : "");
                    var k = (l && l.surveyTexts && l.surveyTexts.surveyIntro ? b.ObjectPlanet.spaces(l.surveyTexts.surveyIntro.def.replace(/\n/g, "<br>")) : "");
                    var g = n == null || n.length == 0 ? ' style="display: none;"' : "";
                    var i = k == null || k.length == 0 ? ' style="display: none;"' : "";
                    if (o && o.filter == true) {
                        g = i = ' style="display: none;"'
                    }
                    var h = l && l.surveyTexts && l.surveyTexts.submit ? l.surveyTexts.submit.def : "Submit";
                    m += '<div class="op-web-theme-heading op-web-survey-overflow-x-no textSelect"' + g + ">" + n + "</div>";
                    m += '<div class="op-web-theme-introduction op-web-survey-overflow-x-no textSelect"' + i + ">" + k + "</div>";
                    m += '<div class="questionContainer"></div>';
                    m += '<table class="buttonContainer" style="width: 100%;" cellspacing="0" cellpadding="0"><tr>';
                    m += '<td style="text-align: right;"><button class="op-web-survey-submit-button op-web-theme-submit-button">' + h + "</button></td>";
                    m += "</tr></table>";
                    var j = (l && l.surveyTexts && l.surveyTexts.thankYouNote ? b.ObjectPlanet.spaces(l.surveyTexts.thankYouNote.def.replace(/\n/g, "<br>")) : "");
                    m += '<div class="op-web-theme-thankyou op-web-survey-overflow-x-no textSelect" style="display: none;">' + j + "</div>";
                    e = ' style="display: none;"'
                }
            }
        }
        var d = '<div class="op-web-survey-enlist" ' + e + ">";
        d += '<div class="heading">Murvey</div>';
        d += "<div>Murvey.com is a cloud-based web service that lets you create and publish online surveys ";
        d += "within minutes. The service is free and no sign-up is needed.</div>";
        if (b.ObjectPlanet.handheld != true) {
            d += '<div class="button-container"><button class="signup">Start using Murvey now</button></div>'
        }
        d += "</div>";
        return m + d + "</div>"
    };
    b.fn.webSurvey = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (e, d) {
            this.options = d ? d : {};
            this.json = e;
            this.enlist = b(this).find(".op-web-survey-enlist").get(0);
            b(this.enlist).find("button").bind("click", function () {
                window.open("https://www.murvey.com")
            });
            if (a.hasResponded.apply(this)) {
                return
            }
            this.heading = b(this).find(".op-web-theme-heading").get(0);
            this.introduction = b(this).find(".op-web-theme-introduction").get(0);
            this.questionContainer = b(this).find("div.questionContainer").get(0);
            this.submit = b(this).find("button.op-web-survey-submit-button").bind("click", c.submitAnswer).prop("self", this).get(0);
            this.thankyou = b(this).find(".op-web-theme-thankyou").get(0);
            if (e && e.sections != null && e.sections.section != null && e.sections.section.length > 0) {
                a.initSectionControls.apply(this)
            }
            if (d && d.filter == true) {
                b(this.submit).hide()
            }
            a.setQuestions.apply(this, [e, d && d.filter == true ? {
                filter: true
            } : null])
        },
        initSectionControls: function () {
            b(this.submit).text(this.json.surveyTexts.start ? this.json.surveyTexts.start.def : "Start").prop("value", "next");
            if (this.back == null && this.progress == null) {
                var e = this.json.surveyTexts.back ? this.json.surveyTexts.back.def : "Back";
                var d = b(this).find(".buttonContainer tr").get(0);
                this.back = b('<td><button class="op-web-survey-back-button op-web-theme-submit-button" style="display: none;">' + e + "</button></td>").prependTo(d).find("button").bind("click", c.prevSection).prop("self", this).get(0);
                var f = b.progressBar({
                    count: this.json.questions != null ? this.json.questions.length : 1
                });
                this.progress = b('<td style="width: 100%;">' + f + "</td>").prependTo(d).find(".op-web-survey-progress").progressBar().get(0)
            }
        },
        hasResponded: function () {
            return (this.options.preventResponse != true && this.json && this.json._id && !this.json.session && b.ObjectPlanet.getCookie(this.json._id.$oid) == "true")
        },
        setQuestions: function (k, g) {
            for (var h = 0, e = ""; k && k.questions instanceof Array && h < k.questions.length; h++) {
                e += b.questionForm(k.questions[h], (h + 1), g)
            }
            this.questions = [];
            for (var h = 0, d = b(e); k != null && k.questions instanceof Array && h < k.questions.length; h++) {
                this.questions[h] = d.eq(h).questionForm(k.questions[h], (h + 1), g).get(0)
            }
            if (k != null && k.sections != null && k.sections.section instanceof Array) {
                b(this.questionContainer).html("");
                this.sections = [];
                for (var h = 0; h < k.sections.section.length; h++) {
                    var l = k.sections.section[h];
                    this.sections[h] = b('<div class="section"></div>').get(0);
                    if (l && l.intro != null && l.intro.def != null) {
                        var m = b.ObjectPlanet.spaces(l.intro.def.replace(/\n/g, "<br>"));
                        this.sections[h].intro = b('<div class="op-web-survey-section-intro op-web-theme-section-intro">' + m + "</div>").get(0);
                        b(this.sections[h]).append(this.sections[h].intro)
                    }
                    for (var f = 0; l != null && l.questions instanceof Array && f < l.questions.length; f++) {
                        b(this.sections[h]).append(this.questions[l.questions[f]])
                    }
                    if (l && l.outro != null && l.outro.def != null) {
                        var m = b.ObjectPlanet.spaces(l.outro.def.replace(/\n/g, "<br>"));
                        this.sections[h].outro = b('<div class="op-web-survey-section-outro op-web-theme-section-outro">' + m + "</div>").get(0);
                        b(this.sections[h]).append(this.sections[h].outro)
                    }
                }
                if (this.back == null) {
                    a.initSectionControls.apply(this)
                }
                a.setSectionState.apply(this)
            } else {
                b(this.questionContainer).html(d)
            }
        },
        adjust: function () {
            for (var d = 0; this.questions != null && d < this.questions.length; d++) {
                if (this.questions[d] != null && this.questions[d].fields != null) {
                    b(this.questions[d].fields).fieldsForm("adjust")
                }
            }
        },
        paint: function () {
            for (var d = 0; b.ObjectPlanet && b.ObjectPlanet.handheld && this.questions != null && d < this.questions.length; d++) {
                if (this.questions[d] != null && this.questions[d].rating != null) {
                    b(this.questions[d].rating.handheldRange).phoneRange("paint")
                }
            }
        },
        setTexts: function (d) {
            b(this.heading).html(d && d.surveyTexts && d.surveyTexts.surveyHeading ? b.ObjectPlanet.spaces(d.surveyTexts.surveyHeading.def) : "");
            b(this.introduction).html(d && d.surveyTexts && d.surveyTexts.surveyIntro ? b.ObjectPlanet.spaces(d.surveyTexts.surveyIntro.def.replace(/\n/g, "<br>")) : "").show();
            b(this.submit).text(d && d.surveyTexts && d.surveyTexts.submit ? b.ObjectPlanet.spaces(d.surveyTexts.submit.def.replace(/\n/g, "<br>")) : "Submit");
            b(this.thankyou).html(d && d.surveyTexts && d.surveyTexts.thankYouNote ? b.ObjectPlanet.spaces(d.surveyTexts.thankYouNote.def.replace(/\n/g, "<br>")) : "").hide();
            b(this.heading).toggle(b(this.heading).text() != "");
            b(this.introduction).toggle(b(this.introduction).text() != "")
        },
        reset: function (d) {
            this.json = d;
            a.setTexts.apply(this, [d]);
            a.setQuestions.apply(this, [d]);
            b(this.submit).show()
        },
        addQuestion: function (g, e) {
            var h = e == undefined ? this.questions.length + 1 : e + 1;
            var d = b(b.questionForm(g, h)).questionForm(g, h).get(0);
            if (e == undefined || e == this.questions.length) {
                this.questions[this.questions.length] = d;
                b(d).appendTo(this.questionContainer)
            } else {
                this.questions.splice(e, 0, b(d).insertBefore(this.questions[e]).get(0));
                for (var f = 0; f < this.questions.length; f++) {
                    var j = "";
                    if (this.questions[f] && this.questions[f].json && this.questions[f].json.questionText && this.questions[f].json.questionText.def != null) {
                        j = b.ObjectPlanet.spaces(this.questions[f].json.questionText.def.replace(/\n/g, "<br>"))
                    }
                    if (this.questions[f] != null) {
                        b(this.questions[f].questionNumber).text(f + 1 + ".");
                        b(this.questions[f].questionText).html(j)
                    }
                }
                b(this.questions[e]).highlight(b.ObjectPlanet.SPEED * 4)
            }
        },
        deleteQuestion: function (e) {
            b(this.questions[e - 1]).remove();
            this.questions.splice(e - 1, 1);
            for (var d = e - 1; d < this.questions.length; d++) {
                var f = b.ObjectPlanet.spaces(this.questions[d].text);
                b(this.questions[d].questionNumber).text((d + 1) + ".");
                b(this.questions[d].questionText).html(f)
            }
        },
        move: function (d) {
            if (d == null || typeof d.from != "number" || d.from < 0 || d.from >= this.questions.length) {
                return
            }
            if (typeof d.to != "number" || d.to < 0 || d.to >= this.questions.length || d.to == d.from) {
                return
            }
            if (this.questions == null || this.questions == null || this.questions.length <= 1) {
                return
            }
            var f = this.questions[d.from];
            if (d.to == 0) {
                b(f).insertBefore(this.questions[d.to])
            } else {
                if (d.to > d.from) {
                    b(f).insertAfter(this.questions[d.to])
                } else {
                    b(f).insertAfter(this.questions[d.to - 1])
                }
            }
            this.questions.splice(d.to, 0, this.questions.splice(d.from, 1)[0]);
            for (var e = 0; e < this.questions.length; e++) {
                b(this.questions[e].questionNumber).text((e + 1) + ".")
            }
        },
        getResponse: function () {
            var e = {
                answers: []
            };
            if (this.json && this.json._id) {
                e.surveyId = this.json._id.$oid
            }
            if (this.json && this.json.session) {
                e.session = this.json.session
            }
            if (this.json && this.json.responseTicket != null) {
                e.responseTicket = this.json.responseTicket
            }
            for (var d = 0; d < this.questions.length; d++) {
                e.answers[d] = b(this.questions[d]).questionForm("getResponse")
            }
            return e
        },
        hasValidResponse: function () {
            for (var d = 0, e = true; this.questions != null && d < this.questions.length; d++) {
                e = e && b(this.questions[d]).questionForm("hasValidResponse")
            }
            return e
        },
        showValidation: function () {
            for (var d = 0; this.questions != null && d < this.questions.length; d++) {
                b(this.questions[d]).questionForm("showValidation")
            }
        },
        nextSection: function () {
            if (this.sections == null || this.currentSection >= this.sections.length - 1) {
                return
            }
            if (this.currentSection == null) {
                this.currentSection = -1
            }
            var e = true;
            if (this.currentSection >= 0) {
                var f = this.json.sections.section[this.currentSection].questions;
                for (var d = 0; f != null && d < f.length; d++) {
                    if (b(this.questions[f[d]]).questionForm("hasValidResponse") == false) {
                        e = false;
                        b(this.questions[f[d]]).questionForm("showValidation", true)
                    }
                }
            }
            if (e == true) {
                b(this.questionContainer).html(this.sections[++this.currentSection])
            }
            a.setSectionState.apply(this)
        },
        prevSection: function () {
            if (this.sections == null || this.currentSection < 0) {
                return
            }
            b(this.questionContainer).html(--this.currentSection == -1 ? "" : this.sections[this.currentSection]);
            a.setSectionState.apply(this)
        },
        showSection: function (e) {
            if (e == null) {
                return
            }
            if (e.question >= 0 && this.questions != null && e.question < this.questions.length && this.json.sections && this.json.sections.section != null) {
                for (var f = 0, h = -1; f < this.json.sections.section.length; f++) {
                    var g = this.json.sections.section[f].questions;
                    for (var d = 0; d < g.length; d++) {
                        if (e.question == g[d]) {
                            h = f
                        }
                    }
                }
                if (h >= 0) {
                    this.currentSection = h;
                    b(this.questionContainer).html(this.sections[this.currentSection])
                }
            } else {
                if (e.introduction == true) {
                    b(this.questionContainer).html("");
                    this.currentSection = -1
                } else {
                    if (this.sections != null && e.section >= 0 && e.section < this.sections.length) {
                        this.currentSection = e.section;
                        b(this.questionContainer).html(this.sections[this.currentSection])
                    }
                }
            }
            a.setSectionState.apply(this)
        },
        setSectionState: function () {
            if (this.currentSection == null) {
                this.currentSection = -1
            }
            b(this.introduction).toggle(this.currentSection < 0);
            b(this.back).toggle(this.currentSection >= 0);
            b(this.submit).prop("value", this.sections && this.currentSection == this.sections.length - 1 ? "submit" : "next");
            if (this.currentSection < 0) {
                b(this.submit).text(this.json && this.json.surveyTexts && this.json.surveyTexts.start ? this.json.surveyTexts.start.def : "Start");
                b(this.progress).progressBar("set", 0)
            } else {
                if (this.currentSection == this.sections.length - 1) {
                    b(this.submit).text(this.json && this.json.surveyTexts && this.json.surveyTexts.submit ? this.json.surveyTexts.submit.def : "Submit");
                    b(this.progress).progressBar("set", this.questions.length)
                } else {
                    b(this.submit).text(this.json && this.json.surveyTexts && this.json.surveyTexts.next ? this.json.surveyTexts.next.def : "Next");
                    for (var d = 0, e = 0; d <= this.currentSection; d++) {
                        e += this.json.sections.section[d].questions.length
                    }
                    b(this.progress).progressBar("set", e)
                }
            }
        },
        submitAnswer: function () {
            b(this.submit).attr("disabled", "disabled");
            var d = this;
            var e = function () {
                b(this.submit).removeAttr("disabled");
                b(this.introduction).add(this.questions).add(this.submit).add(this.back).add(this.progress).hide();
                if (this.sections && this.sections.length > 0) {
                    b(this.sections[this.sections.length - 1]).hide()
                }
                b(this.thankyou).show();
                b(this.enlist).toggle(!this.options.previewMode);
                b(this).trigger("survey.submit");
                if (this.options.preventResponse != true && this.json && this.json._id) {
                    b.ObjectPlanet.setCookie(this.json._id.$oid, "true", 1095);
                    if (typeof window.parent.postMessage == "function") {
                        window.parent.postMessage("submit", "*")
                    }
                }
            };
            if (this.options.preventResponse == true) {
                e.apply(d)
            } else {
                if (!a.hasResponded.apply(this)) {
                    b.ajax({
                        url: b.OP_BASE != undefined ? (b.OP_BASE + "r") : "r",
                        type: "POST",
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        data: {
                            response: JSON.stringify(b(this).webSurvey("getResponse"))
                        },
                        success: function (f) {
                            e.apply(d)
                        },
                        error: function (g, i, h) {
                            var f = "file: survey.js (submit.ajax)\n\n";
                            f += "\nxml.readyState: " + g.readyState;
                            f += "\nxml.status: " + g.status;
                            f += "\nxml.statusText: " + g.statusText;
                            if (g.responseText != null) {
                                f += "\nxml.responseText: " + g.responseText
                            }
                            if (g.statusCode != null) {
                                f += "\nxml.statusCode: " + g.statusCode()
                            }
                            if (g.getResponseHeader != null) {
                                f += "\nxml.responseHeader: " + g.getResponseHeader()
                            }
                            f += "\ntextStatus: " + i;
                            f += "\nerrorThrown: " + h;
                            if (d.json && d.json._id && d.json._id.$oid != null) {
                                f += "\nsurvey ID: " + d.json._id.$oid
                            }
                            if (d.json && d.json.surveyName != null) {
                                f += "\nsurvey name: " + d.json.surveyName
                            }
                            f += "\nbrowser: " + navigator.userAgent;
                            b.getJSON("/a?action=error.send&error=" + encodeURIComponent(f))
                        }
                    })
                }
            }
        }
    };
    var c = {
        submitAnswer: function (d) {
            if (this.self.submit.value == "next") {
                a.nextSection.apply(this.self)
            } else {
                if (a.hasValidResponse.apply(this.self)) {
                    a.submitAnswer.apply(this.self)
                } else {
                    a.showValidation.apply(this.self)
                }
            }
        },
        prevSection: function () {
            a.prevSection.apply(this.self)
        }
    }
})(jQuery);
(function (b) {
    b.progressBar = function (d) {
        var c = '<table class="op-web-survey-progress"><tr>';
        for (var e = 0; d && d.count != null && e < d.count; e++) {
            c += "<td></td>"
        }
        return c + '<td class="label"></td></tr></table>'
    };
    b.fn.progressBar = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function () {
            this.label = b(this).find("td.label").get(0);
            this.count = b(this).find("td").size() - 1
        },
        set: function (d) {
            var e = b(this).find("td").removeClass("filled");
            for (var c = 0; c < Math.min(d, this.count); c++) {
                e.eq(c).addClass("filled")
            }
            b(this.label).text(d > 0 ? (Math.round(d / this.count * 100) + "%") : "")
        }
    }
})(jQuery);
(function (b) {
    b.questionForm = function (d, e, c) {
        return '<div class="op-web-survey-question">' + b.questionFormHtml(d, e, c) + "</div>"
    };
    b.questionFormHtml = function (e, f, d) {
        if (d == null) {
            d = {}
        }
        if (b.ObjectPlanet.handheld == true) {
            d.handheld = true
        }
        var g = (e && e.questionText && typeof e.questionText.def == "string" ? b.ObjectPlanet.spaces(e.questionText.def.replace(/\n/g, "<br>")) : "");
        var h = d && d.filter == true ? ' style="padding-right: 2em;"' : "";
        var c = '<div class="op-web-survey-question-text op-web-survey-borderbox op-web-theme-question-text textSelect"' + h + "><span>";
        c += (f ? f + "." : "");
        c += "</span><label>" + g + "</label>";
        if (d && d.filter == true) {
            c += '<input class="hand" type="checkbox" checked="checked">'
        }
        c += "</div>";
        if (e && e.rating) {
            c += b.ratingForm(e.rating, d)
        } else {
            if (e && e.choice) {
                c += b.choiceForm(e.choice, d)
            } else {
                if (e && e.dropdown) {
                    c += b.dropdownForm(e.dropdown, d)
                } else {
                    if (e && e.fields) {
                        c += b.fieldsForm(e.fields, d)
                    }
                }
            }
        } if (e && e.comment) {
            c += b.commentForm(e ? e.comment : null, d)
        }
        return c
    };
    b.fn.questionForm = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (e, f, d) {
            var c = this;
            this.json = e;
            this.current = null;
            this.text = (e && e.questionText && typeof e.questionText.def == "string" ? e.questionText.def.replace(/\n/g, "<br>") : "");
            this.questionTextContainer = b(this).find(".op-web-survey-question-text").get(0);
            this.questionNumber = b(this.questionTextContainer).find("span").get(0);
            this.questionText = b(this.questionTextContainer).find("label").get(0);
            if (d && d.filter == true) {
                this.filter = b(this).find(".op-web-survey-question-text input").bind("click", function () {
                    if (c.comment != null) {
                        b(c.comment.filter).toggleClass("hand").parent().toggleClass("hand");
                        if (b(this).is(":checked")) {
                            b(c.comment.filter).removeAttr("disabled")
                        } else {
                            b(c.comment.filter).attr("disabled", "disabled")
                        }
                    }
                    if (c.fields != null) {
                        if (b(this).is(":checked")) {
                            b(c.fields).find(".op-web-survey-question-field input[type=checkbox]").removeAttr("disabled").css("cursor", "pointer")
                        } else {
                            b(c.fields).find(".op-web-survey-question-field input[type=checkbox]").attr("disabled", "disabled").css("cursor", "default")
                        }
                    }
                }).get(0)
            }
            if (d == null) {
                d = {}
            }
            if (b.ObjectPlanet.handheld == true) {
                d.handheld = true
            }
            if (this.json && this.json.rating) {
                this.current = this.rating = b(this).find("> div.rating").ratingForm(this.json.rating, d).get(0)
            } else {
                if (this.json && this.json.choice) {
                    this.current = this.choice = b(this).find("> div.op-web-survey-question-choice").choiceForm(this.json.choice, d).get(0)
                } else {
                    if (this.json && this.json.dropdown) {
                        this.current = this.dropdown = b(this).find("> div.op-web-survey-question-dropdown").dropdownForm(this.json.dropdown).get(0)
                    } else {
                        if (this.json && this.json.fields) {
                            this.current = this.fields = b(this).find(".op-web-survey-question-fields").fieldsForm(this.json.fields, d).fieldsForm("adjust").get(0)
                        }
                    }
                }
            } if (this.comment != null) {
                delete this.comment
            }
            if (this.json && this.json.comment) {
                this.comment = b(this).find("> div.op-web-survey-question-comment").commentForm(this.json.comment).get(0)
            }
        },
        toggleComment: function (c) {
            if (this.comment == null && c == true) {
                var d = b.commentForm();
                this.comment = b(d).commentForm().appendTo(this).get(0)
            }
            b(this.comment).toggle(c == true)
        },
        setComment: function (c) {
            if (this.comment != null) {
                b(this.comment.label).html(b.ObjectPlanet.spaces(c.label.def).replace(/\n/g, "<br>"))
            }
        },
        hasValidResponse: function () {
            if (this.current != null && this.current === this.rating) {
                return b(this.rating).ratingForm("hasValidResponse")
            }
            if (this.current != null && this.current === this.choice) {
                return b(this.choice).choiceForm("hasValidResponse")
            }
            if (this.current != null && this.current === this.dropdown) {
                return b(this.dropdown).dropdownForm("hasValidResponse")
            }
            if (this.current != null && this.current === this.fields) {
                return b(this.fields).fieldsForm("hasValidResponse")
            }
            return true
        },
        showValidation: function (c) {
            if (this.current != null && this.current === this.rating) {
                b(this.rating).ratingForm("showValidation", c)
            }
            if (this.current != null && this.current === this.choice) {
                b(this.choice).choiceForm("showValidation", c)
            }
            if (this.current != null && this.current === this.dropdown) {
                b(this.dropdown).dropdownForm("showValidation", c)
            }
            if (this.current != null && this.current === this.fields) {
                b(this.fields).fieldsForm("showValidation", c)
            }
        },
        getResponse: function () {
            var c = {};
            if (this.current != null && this.current === this.rating) {
                c.rating = b(this.rating).ratingForm("getResponse")
            } else {
                if (this.current != null && this.current === this.choice) {
                    c.choice = b(this.choice).choiceForm("getResponse")
                } else {
                    if (this.current != null && this.current === this.dropdown) {
                        c.dropdown = b(this.dropdown).dropdownForm("getResponse")
                    } else {
                        if (this.current != null && this.current === this.fields) {
                            c.fields = b(this.fields).fieldsForm("getResponse")
                        }
                    }
                }
            } if (this.comment != null) {
                c.comment = b(this.comment).commentForm("getResponse")
            }
            return c
        }
    }
})(jQuery);
(function (b) {
    b.ratingForm = function (f, e) {
        var g = e != null && e.handheld == true ? " op-handheld-rating" : "";
        var d = '<div class="op-web-survey-question-response op-web-theme-question-response rating' + g + '">';
        return d + b.ratingFormHtml(f, e) + "</div>"
    };
    b.ratingFormHtml = function (o, q) {
        var l = o && typeof o.maxRating == "number" ? o.maxRating - 0 : 5;
        var e = (o && o.labels && o.labels.length >= 1 && typeof o.labels[0].def == "string" ? b.ObjectPlanet.spaces(b.trim(o.labels[0].def)) : "Bad");
        var f = (o && o.labels && o.labels.length >= 2 && l <= o.labels.length && typeof o.labels[l - 1].def == "string" ? b.ObjectPlanet.spaces(b.trim(o.labels[l - 1].def)) : "Good");
        var k = (o && o.naLabel && typeof o.naLabel.def == "string" ? b.ObjectPlanet.spaces(b.trim(o.naLabel.def)) : "N/A");
        var g = (o && o.validation && o.validation.required && o.validation.required.message && o.validation.required.message.def != "" ? b.ObjectPlanet.spaces(o.validation.required.message.def) : "");
        var p = "";
        if (q != null && q.handheld == true) {
            p += '<canvas class="op-handheld-rating-range"></canvas>';
            p += '<div class="op-handheld-rating-labels">';
            p += '<label class="op-handheld-rating-label op-handheld-rating-label-min">' + e + "</label>";
            p += '<label class="op-handheld-rating-label op-handheld-rating-label-max">' + f + "</label>";
            p += "</div>";
            p += '<br><label class="validation op-web-survey-validation-message op-web-theme-validation" style="display: none;">' + g + "</label>"
        } else {
            p += '<form><label class="bad textSelect">' + e + "</label>";
            p += '<span class="op-web-survey-question-rating-range">';
            var j = o && o.maxRating ? o.maxRating : 5;
            var m = q && q.filter == true ? "checkbox" : "radio";
            var n = q && q.filter == true ? ' checked="checked"' : "";
            for (var h = 1; h <= j; h++) {
                p += '<label class="hand textSelect">' + h + '<input class="hand" type="' + m + '" name="radio" value="' + h + '"' + n + "></label>"
            }
            p += "</span>";
            var d = (o != null && o.showNA == true ? "" : ' style="display: none;"');
            p += '<label class="good textSelect">' + f + "</label>";
            p += '<label class="op-web-survey-question-rating-na na hand">';
            p += '<input class="hand" type="' + m + '" name="radio" value="na"' + d + n + ">";
            p += '<span class="textSelect"' + d + ">" + k + "</span></label>";
            p += '<label class="validation op-web-survey-validation-message op-web-theme-validation" style="display: none;">' + g + "</label>";
            if (q && q.filter == true) {
                p += '<label class="noAnswer break hand"><span style="visibility: hidden;">' + e;
                p += '</span><input class="noAnswer hand" type="checkbox" value="noAnswer" checked="checked">Include empty answers</label>'
            }
            p += "</form>"
        }
        return p
    };
    b.fn.ratingForm = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (f, e) {
            var d = b(this).prop("json", f).get(0);
            if (e != null && e.handheld == true) {
                this.handheldRange = b(this).find(".op-handheld-rating-range").phoneRange(f).get(0)
            }
            this.bad = b(this).find("label.bad").get(0);
            this.good = b(this).find("label.good").get(0);
            this.na = b(this).find(".op-web-survey-question-rating-na input").get(0);
            this.naContainer = b(this.na).closest("label");
            this.naLabel = b(this).find("label.na span").get(0);
            this.range = b(this).find("> form span:eq(0)").get(0);
            this.validation = b(this).find("label.validation").get(0);
            this.noAnswer = b(this).find("input.noAnswer").get(0);
            b(this).bind("change", c.hideValidation);
            if (e && e.handheld == true) {
                b(this.handheldRange).bind("change", function (g) {
                    if (a.hasValidResponse.apply(d)) {
                        b(d.validation).hide()
                    }
                })
            }
        },
        hasValidResponse: function () {
            if (this.json != null && this.json.validation && this.json.validation.required && this.json.validation.required.on == true) {
                if (this.handheldRange != null) {
                    return b(this.handheldRange).phoneRange("getCurrent") > 0
                } else {
                    return b(this).find("input[type=radio]").is(":checked")
                }
            }
            return true
        },
        showValidation: function (d) {
            b(this.range).add(this.naContainer).removeClass("op-web-theme-validation-marker");
            b(this.validation).hide();
            if ((d == undefined || d == true) && this.json.validation && this.json.validation.required && !a.hasValidResponse.apply(this)) {
                b(this.range).add(this.naContainer).addClass("op-web-theme-validation-marker");
                var e = this.json.validation.required.message && this.json.validation.required.message.def ? this.json.validation.required.message.def : "";
                b(this.validation).html(b.ObjectPlanet.spaces(e)).toggle(e != "")
            }
        },
        getResponse: function () {
            var d = {};
            if (this.handheldRange != null) {
                d.value = b(this.handheldRange).phoneRange("getCurrent")
            }
            if (this.range != null) {
                d.value = b(this).find("input:checked").val()
            }
            if (d.value != "na") {
                d.value = d.value != undefined ? (d.value - 0) : ""
            }
            if (d.value == 0) {
                d.value = ""
            }
            return d
        },
        setRange: function (e) {
            e = (e - 0 == e ? e : 5);
            for (var f = 1, d = ""; f <= e; f++) {
                d += ("<label>" + f + '<input type="radio" name="radio" value="' + f + '"></label>')
            }
            b(this.range).html(d)
        }
    };
    var c = {
        hideValidation: function () {
            b(this.validation).hide();
            b(this.range).add(this.naContainer).removeClass("op-web-theme-validation-marker")
        }
    }
})(jQuery);
(function (b) {
    b.fn.phoneRange = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (d) {
            var c = b(this).css("user-select", "none").prop("range", d != null && d.maxRating != null ? d.maxRating : 5).prop("current", 0).get(0);
            b.addEventListener(this, b.touchstart, function (e) {
                c.touchDown = true;
                c.current = a.value.apply(c, [e]);
                a.paint.apply(c);
                b(c).trigger("change")
            });
            b.addEventListener(this, b.touchmove, function (e) {
                if (c.touchDown == true) {
                    c.current = a.value.apply(c, [e]);
                    a.paint.apply(c);
                    b(c).trigger("change")
                }
            });
            b.addEventListener(this, b.touchend, function (e) {
                delete c.touchDown
            });
            b(window).bind("orientationchange", function (e) {
                a.paint.apply(c)
            })
        },
        value: function (d) {
            var c = d.touches != null ? d.touches[0].pageX : d.offsetX;
            return Math.min(this.range, Math.round(c / this.width * this.range))
        },
        getCurrent: function () {
            return this.current > 0 ? this.current : 0
        },
        paint: function () {
            this.width = b(this).width();
            this.height = this.width / 5;
            var e = this.getContext("2d");
            e.fillStyle = e.createLinearGradient(0, 0, 0, this.height);
            e.fillStyle.addColorStop(0, "#bbb");
            e.fillStyle.addColorStop(1, "#eee");
            e.moveTo(1, this.height - (this.height / 3));
            e.lineTo(1, this.height - 1);
            e.lineTo(this.width - 1, this.height - 1);
            e.lineTo(this.width - 1, 2);
            e.lineTo(this.width - this.width / 20, 2);
            e.lineTo(1, this.height - (this.height / 3));
            e.fill();
            e.save();
            e.clip();
            if (this.current > 0) {
                e.beginPath();
                e.fillStyle = e.createLinearGradient(0, 0, 0, this.height);
                e.fillStyle.addColorStop(0, "#bdc9db");
                e.fillStyle.addColorStop(1, "#5e7898");
                e.moveTo(0, 0);
                e.lineTo(this.current / this.range * this.width, 0);
                e.lineTo(this.current / this.range * this.width, this.height);
                e.lineTo(0, this.height);
                e.lineTo(0, 0);
                e.fill()
            }
            e.beginPath();
            e.restore();
            e.lineWidth = 2;
            e.strokeStyle = e.createLinearGradient(0, 0, 0, this.height);
            e.strokeStyle.addColorStop(0, "#ddd");
            e.strokeStyle.addColorStop(1, "#888");
            e.moveTo(2, this.height - (this.height / 3));
            e.lineTo(2, this.height - 1);
            e.lineTo(this.width - 1, this.height - 1);
            e.lineTo(this.width - 1, 2);
            e.lineTo(this.width - this.width / 20, 2);
            e.lineTo(2, this.height - (this.height / 3));
            e.stroke();
            e.lineWidth = 1;
            e.clip();
            for (var d = 1; d < this.range; d++) {
                var c = d / this.range * this.width;
                e.moveTo(c, 0);
                e.lineTo(c, this.height)
            }
            e.stroke()
        }
    }
})(jQuery);
(function (b) {
    b.choiceForm = function (f, e) {
        var d = '<div class="op-web-survey-question-response op-web-theme-question-response op-web-survey-question-choice">';
        return d + b.choiceFormHtml(f, e) + "</div>"
    };
    b.choiceFormHtml = function (h, e) {
        var d = "<form>";
        var j = h != null && h.multipleOn ? "checkbox" : "radio";
        if (e && e.filter == true) {
            j = "checkbox"
        }
        var l = e && e.filter ? ' checked="checked"' : "";
        var g = h != null && h.columns != null && h.columns.value != null ? Math.min(9, Math.max(h.columns.value, 1)) : 1;
        if ((e == null || e.filter != true) && h && h.randomize && h.randomize.on == true && h.options instanceof Array && h.options.length > 1) {
            var k = h.options[h.options.length - 1];
            if (k && k.other && k.other.on == true) {
                h.copy = h.options.slice(0, h.options.length - 1);
                for (var f = 0; f < h.copy.length; f++) {
                    h.copy[f].index = f
                }
                h.copy.shuffle();
                h.copy[h.options.length - 1] = k;
                k.index = h.options.length - 1
            } else {
                h.copy = h.options.slice();
                for (var f = 0; f < h.copy.length; f++) {
                    h.copy[f].index = f
                }
                h.copy.shuffle()
            }
        }
        d += '<table cellspacing="0" cellpadding="0">';
        for (var f = 0; h && h.options && (h.options.length > 1 || (h.options[0] && h.options[0].def != "")) && f < h.options.length; f++) {
            if (f % g == 0 || (e != null && (e.filter == true || e.handheld == true))) {
                d += "<tr>"
            }
            d += "<td>" + b.choiceFormOption(h.copy != null ? h.copy : h.options, f, j, e) + "</td>";
            if (f % g == g - 1 || (e != null && (e.filter == true || e.handheld == true))) {
                d += "</tr>"
            }
        }
        d += "</table>";
        if (e && e.filter == true) {
            d += '<label style="margin-top: 5px;" class="hand"><span><input class="noAnswer hand" type="checkbox" checked="checked"></span>Include empty answers</label>'
        }
        d += '<label class="validation op-web-survey-validation-message op-web-theme-validation" style="display: none;"></label>';
        return d + "</form>"
    };
    b.choiceFormOption = function (o, f, j, p) {
        var d = p != null && p.handheld == true ? "op-handheld-choice-option " : "";
        var g = d != "" && f == 0 ? "op-handheld-choice-option-first " : "";
        var m = d != "" && o && f == o.length - 1 ? "op-handheld-choice-option-last " : "";
        var e = d != "" && m == "" ? "op-handheld-choice-option-border " : "";
        var h = d != "" ? " noSelect" : " textSelect";
        var n = '<label class="' + d + g + m + e + " op-web-survey-question-choice-option option hand" + h + '">';
        var l = o[f].other && o[f].other.on ? "other" : o[f].def;
        var k = p && p.filter ? ' checked="checked"' : "";
        n += '<span><input index="' + f + '"class="hand option" type="' + j + '" name="radio" value="' + l + '"' + k + "></span>";
        n += b.ObjectPlanet.spaces(o[f].def);
        if (o[f].other && o[f].other.on && (p == null || p.filter != true)) {
            n += '<input class="other op-web-theme-input" type="text" encoded="true">'
        }
        return n + "</label>"
    };
    b.fn.choiceForm = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (g, e) {
            this.json = g;
            var d = b(this).addClass("op-web-survey-question-choice").get(0);
            if (this.json && this.json.copy instanceof Array) {
                this.copy = [];
                for (var f = 0; f < this.json.copy.length; f++) {
                    this.copy[f] = this.json.copy[f].index;
                    delete this.json.copy[f].index
                }
                delete this.json.copy
            }
            this.choice = [];
            for (var f = 0; g && g.options && (g.options.length > 1 || (g.options[0] && g.options[0].def != "")) && f < g.options.length; f++) {
                this.choice[f] = b(this).find("input").eq(f).bind("change click", c.hideValidation).prop("self", this).get(0)
            }
            if (g && g.options && g.options[g.options.length - 1] && g.options[g.options.length - 1].other) {
                this.other = b(this).find("label.option:last").prop("self", this).get(0);
                if (e == null || e.filter != true) {
                    b(this.other).bind("click", c.turnOnOther)
                }
            }
            b(this.choice).bind("click", function (h) {
                if (e && e.filter == true) {
                    return
                }
                if (b(h.target).attr("index") == d.selected) {
                    b(h.target).removeAttr("checked");
                    b(d).removeProp("selected")
                } else {
                    d.selected = b(h.target).attr("index")
                }
            });
            b(this).find("form").bind("submit", function (h) {
                return false
            });
            this.validation = b(this).find("label.validation").get(0);
            if (e && e.handheld == true) {
                this.handheld = true;
                b(this).find(".op-handheld-choice-option").bind(b.touchstart, c.touchStart).bind(b.touchmove, c.touchMove).bind(b.touchend, c.touchEnd).prop("self", this).find("input[type=radio]").attr("disabled", "disabled").end().find("input[type=checkbox]").attr("disabled", "disabled")
            }
        },
        hasValidResponse: function () {
            if (this.json.validation && this.json.validation.min && this.json.validation.min.value > 0 && b(this).find("input:checked").size() < this.json.validation.min.value) {
                return false
            }
            if (this.json.validation && this.json.validation.max && this.json.validation.max.value > 0 && b(this).find("input:checked").size() > this.json.validation.max.value) {
                return false
            }
            return true
        },
        showValidation: function (d) {
            b(this).find("label span").removeClass("op-web-theme-validation-marker");
            b(this.validation).hide();
            if ((d == undefined || d == true) && this.json && this.json.validation && !a.hasValidResponse.apply(this)) {
                if (this.json.validation.min && b(this).find(":checked").size() < this.json.validation.min.value && this.json.validation.min.message && this.json.validation.min.message.def) {
                    b(this.validation).html(b.ObjectPlanet.spaces(this.json.validation.min.message.def))
                }
                if (this.json.validation.max && b(this).find(":checked").size() > this.json.validation.max.value && this.json.validation.max.message && this.json.validation.max.message.def) {
                    b(this.validation).html(b.ObjectPlanet.spaces(this.json.validation.max.message.def))
                }
                if (b(this.validation).text() != "") {
                    b(this.validation).show()
                }
                if (this.handheld == true) {
                    b(this).find("label.option").addClass("op-web-theme-validation-marker").css("-webkit-border-radius", "0px");
                    b(this).find("label.option:first").css("-webkit-border-radius", "5px 5px 0px 0px");
                    b(this).find("label.option:last").css("-webkit-border-radius", "0px 0px 5px 5px")
                } else {
                    b(this).find("label span").addClass("op-web-theme-validation-marker").css("-webkit-border-radius", "0px");
                    b(this).find("label span:first").css("-webkit-border-radius", "5px 5px 0px 0px");
                    b(this).find("label span:last").css("-webkit-border-radius", "0px 0px 5px 5px")
                }
            }
        },
        getResponse: function () {
            var d = {
                options: []
            };
            for (var e = 0; e < this.choice.length; e++) {
                if (this.copy instanceof Array && e < this.copy.length) {
                    d.options[this.copy[e]] = {
                        checked: b(this.choice[e]).is(":checked") == true
                    }
                } else {
                    d.options[e] = {
                        checked: b(this.choice[e]).is(":checked") == true
                    }
                }
            }
            if (b(this.choice[this.choice.length - 1]).is(":checked") && b(this.choice[this.choice.length - 1]).val() == "other") {
                var f = encodeURIComponent(b(this).find("label.option:last input:last").val());
                d.options[d.options.length - 1].value = f
            }
            return d
        }
    };
    var c = {
        turnOnOther: function (d) {
            if (this.self.handheld != true) {
                b(this).find("input.other").focus()
            }
        },
        hideValidation: function () {
            if (a.hasValidResponse.apply(this.self)) {
                b(this.self.validation).hide();
                b(this.self).find("label span").removeClass("op-web-theme-validation-marker")
            }
        },
        touchStart: function (d) {
            b(this).addClass("op-handheld-choice-option-active")
        },
        touchMove: function (d) {
            if (d.originalEvent && d.originalEvent.touches != null) {
                b(this).closest(".op-web-survey-question-choice").prop("touchMove", true)
            }
        },
        touchEnd: function (e) {
            var d = b(this).closest(".op-web-survey-question-choice").get(0);
            if (d.touchMove != true && d.json && d.json.multipleOn == true) {
                b(this).toggleClass("op-handheld-choice-option-chosen", !b(this).hasClass("op-handheld-choice-option-chosen"));
                if (b(this).hasClass("op-handheld-choice-option-chosen")) {
                    b(this).find("input").attr("checked", "checked")
                }
                if (!b(this).hasClass("op-handheld-choice-option-chosen")) {
                    b(this).find("input").removeAttr("checked")
                }
            } else {
                if (d.touchMove != true) {
                    b(d).find(".op-handheld-choice-option").removeClass("op-handheld-choice-option-chosen").find("input").removeAttr("checked");
                    b(this).addClass("op-handheld-choice-option-chosen").find("input").attr("checked", "checked")
                }
            }
            b(this).removeClass("op-handheld-choice-option-active");
            if (a.hasValidResponse.apply(d)) {
                b(d.validation).hide();
                b(d).find("label.option").removeClass("op-web-theme-validation-marker")
            }
            delete d.touchMove
        }
    }
})(jQuery);
(function (b) {
    b.dropdownForm = function (e, d) {
        return ('<div class="op-web-survey-question-response op-web-theme-question-response op-web-survey-question-dropdown">' + b.dropdownFormHtml(e, d) + "</div>")
    };
    b.dropdownFormHtml = function (j, m) {
        var d = j == null || j.label == null || b.trim(j.label.def) == "";
        var l = (j == null || j.options == null || j.options.length == 0 || (j.options.length == 1 && (j.options[0] == null || j.options[0].def == null || b.trim(j.options[0].def) == "")));
        if (m != null && m.filter == true) {
            for (var e = 0, k = ""; j && j.options && e < j.options.length; e++) {
                var h = j.options[e].value != undefined ? j.options[e].value : j.options[e].def;
                k += '<label class="option break hand">';
                k += '<input class="option hand" type="checkbox" checked="checked" value="' + h + '">';
                k += b.ObjectPlanet.spaces(j.options[e].def) + "</label>"
            }
            if (j && j.options && j.options.length > 0) {
                k += '<label style="margin-top: 5px; display: inline-block;" class="hand">';
                k += '<input class="noAnswer hand" type="checkbox" checked="checked">Include empty answers</label>'
            }
            return k
        } else {
            if (d == true && l == true) {
                return ""
            } else {
                var n = j && j.multipleOn == true ? ' multiple" multiple="multiple"' : '"';
                var k = '<select class="op-web-theme-select' + n + ">";
                if (j && j.label && j.label.def && j.multipleOn != true) {
                    k += '<option class="label">' + b.ObjectPlanet.spaces(j.label.def) + "</option>"
                }
                if (j && j.randomize && j.randomize.on == true) {
                    j.copy = j.options.slice().shuffle()
                }
                for (var e = 0; j && j.options && e < j.options.length; e++) {
                    var f = j.copy instanceof Array && e < j.copy.length ? j.copy[e] : j.options[e];
                    if (f != null) {
                        var h = f.value != undefined ? f.value : f.def;
                        var g = b.ObjectPlanet.spaces(f.def);
                        k += '<option value="' + h + '">' + g + "</option>"
                    }
                }
                return k += '</select><label class="validation op-web-survey-validation-message op-web-theme-validation" style="display: none;"></label>'
            }
        }
    };
    b.fn.dropdownForm = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (e) {
            if (e && e.copy instanceof Array) {
                delete e.copy
            }
            this.json = e;
            if (b(this).find("select").size() > 0) {
                this.validation = b(this).find("label.validation").get(0);
                this.select = b(this).find("select").prop("self", this).bind("change", c.hideValidation).get(0);
                this.options = [];
                var d = this;
                b(this).find("option").each(function (f) {
                    d.options[f] = this
                })
            }
        },
        hasValidResponse: function () {
            var d = this.json.validation && this.json.validation.required && this.json.validation.required.on == true;
            if (b(this.select).attr("multiple") == "multiple" && d == true) {
                if (b(this.select).val() == null) {
                    return false
                }
            } else {
                if (this.select != null && this.json.label && this.json.label.def != null && this.json.label.def != "" && d == true) {
                    return this.select.selectedIndex > 0
                }
            }
            return true
        },
        showValidation: function (d) {
            b(this.validation).hide();
            b(this.select).removeClass("op-web-theme-validation-marker");
            if ((d == undefined || d == true) && this.json.validation && this.json.validation.required && !a.hasValidResponse.apply(this)) {
                var e = this.json.validation.required.message && this.json.validation.required.message.def ? this.json.validation.required.message.def : "";
                b(this.validation).html(b.ObjectPlanet.spaces(e)).toggle(e != "");
                b(this.select).addClass("op-web-theme-validation-marker")
            }
        },
        getResponse: function () {
            var e = {
                options: []
            };
            if (b(this.select).attr("multiple") == "multiple") {
                for (var f = 0, d = b(this.select).val(); d != null && f < d.length; f++) {
                    e.options[f] = {
                        value: d[f]
                    }
                }
            } else {
                if (this.select != null && this.json.label && this.json.label.def && this.json.label.def.length > 0 && this.select.selectedIndex > 0) {
                    e.options[0] = {
                        value: b(this).find("option").eq(this.select.selectedIndex).val()
                    }
                } else {
                    if (this.select != null && this.select.selectedIndex != -1 && (this.json.label == null || this.json.label.def == null || this.json.label.def.length == 0)) {
                        e.options[0] = {
                            value: b(this).find("option").eq(this.select.selectedIndex).val()
                        }
                    }
                }
            }
            return e
        }
    };
    var c = {
        hideValidation: function () {
            b(this.self.validation).hide();
            b(this.self.select).removeClass("op-web-theme-validation-marker")
        }
    }
})(jQuery);
(function (b) {
    b.fieldsForm = function (e, d) {
        return ('<table cellpadding="0" cellspacing="0" class="op-web-survey-question-response op-web-theme-question-response op-web-survey-question-fields fieldsForm op-web-survey-borderbox">' + b.fieldsFormHtml(e, d) + "</table>")
    };
    b.fieldsFormHtml = function (h, f) {
        var d = "";
        if (h instanceof Array && h.length >= 0) {
            for (var g = 0, j = false; g < h.length && j == false; g++) {
                var e = h[g] && h[g].label && typeof h[g].label.def == "string" ? h[g].label.def : "";
                if (e != "") {
                    j = true
                }
            }
            for (var g = 0; g < h.length; g++) {
                d += b.fieldsRow(h[g], f, j)
            }
        } else {
            d += b.fieldsRow()
        }
        return d
    };
    b.fieldsRow = function (g, f, i) {
        var e = g && g.label && typeof g.label.def == "string" ? b.ObjectPlanet.spaces(g.label.def) : "";
        var d = '<tr class="op-web-survey-question-field">';
        var j = i != true ? ' style="padding-right: 0px;"' : "";
        d += '<td class="label textSelect"' + j + ">";
        if (f && f.filter == true) {
            d += '<input class="field" type="checkbox" checked="checked">'
        }
        d += e + "</td><td>";
        var h = g && g.width != null && (f == null || f.filter != true) ? ' style="width: ' + g.width + ';"' : "";
        if (b.ObjectPlanet.handheld == true) {
            d += '<input class="op-web-theme-input borderBox" type="text" encoded="true" style="display: block; margin-top: 8px; margin-bottom: 14px; margin-left: 0px; ">'
        } else {
            if (g != null && g.rows > 1 && (f == null || f.filter != true)) {
                d += '<textarea class="op-web-theme-input borderBox" type="text" encoded="true" rows="' + g.rows + '"' + h + "></textarea>"
            } else {
                d += '<input class="op-web-theme-input borderBox" type="text" encoded="true"' + h + ">"
            }
        } if (f && f.filter == true) {
            d += '<canvas class="not notInactive hand" style="width: 16px; height: 16px;"></canvas>'
        }
        d += '<label class="validation op-web-survey-validation-message op-web-theme-validation" style="display: none;"></label>';
        return d + "</td></tr>"
    };
    b.fn.fieldsForm = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (f, e) {
            this.json = f;
            this.fields = [];
            var d = this;
            if (e && e.filter == true) {
                this.filter = true
            }
            b(this).find(".op-web-survey-question-field").each(function (g) {
                d.fields[g] = a.configureField.apply(d, [this, f && g < f.length ? f[g] : null])
            })
        },
        configureField: function (e, d) {
            e.json = d;
            e.validation = b(e).find("label.validation").get(0);
            e.label = b(e).find(".label").get(0);
            if (d != null && d.rows > 1 && b(this).find("textarea").size() > 0) {
                e.input = b(e).find("textarea").autoSizeTextArea({
                    horizontal: false,
                    minHeight: true
                }).get(0)
            } else {
                e.input = b(e).find("input[type=text]").get(0)
            }
            e.filter = b(e).find("input[type=checkbox]").get(0);
            if (b(e).find(".not").size() > 0) {
                e.not = b(e).find(".not").excludeIcon().bind("click", function () {
                    b(e.not).toggleClass("notActive", !b(e.not).hasClass("notActive"));
                    b(e.not).toggleClass("notInactive", !b(e.not).hasClass("notInactive")).excludeIcon("paint");
                    b(e).fieldsForm("setExcludeLabel", e).trigger("change")
                }).floatLabel({
                    label: "Exclude empty",
                    free: true
                }).get(0)
            }
            if (e.not != null) {
                b(e.input).bind("keyup", function (f) {
                    a.setExcludeLabel.apply(e, [e])
                })
            }
            b(e.input).bind("keydown keypress keyup", c.ensureNumbersOnly).bind("keypress change", c.hideValidation).prop("field", e);
            return e
        },
        adjust: function () {
            if (b(this).is(":visible")) {
                for (var d = 0, e = false; d < this.fields.length && e == false; d++) {
                    if (b(this.fields[d].label).text() != "") {
                        e = true
                    }
                }
                if (e == false) {
                    b(this.fields).find(".label").css("padding-right", "0px")
                }
                if (e != false) {
                    b(this.fields).find(".label").css("padding-right", "")
                }
            }
        },
        hasValidResponse: function () {
            var d = true;
            for (var e = 0; e < this.fields.length && d == true; e++) {
                d = d && a.hasFieldValidResponse.apply(this, [e])
            }
            return d
        },
        hasFieldValidResponse: function (f) {
            var d = true;
            var e = this.json[f];
            var g = b(this.fields[f].input).val();
            if (e.validation) {
                if (e.validation.required && e.validation.required.on == true) {
                    d = d && g != ""
                }
                if ((e.type == "integer" || e.type == "decimal") && g != "") {
                    if (e.validation.min && e.validation.min.value && e.validation.min.value != "") {
                        d = d && (g - 0) >= e.validation.min.value
                    }
                    if (e.validation.max && e.validation.max.value && e.validation.max.value != "") {
                        d = d && (g - 0) <= e.validation.max.value
                    }
                } else {
                    if (e.type == "email" && g != "") {
                        d = d && b.ObjectPlanet.isValidEmail(g)
                    }
                }
            }
            return d
        },
        showValidation: function (d) {
            for (var f = 0; f < this.fields.length; f++) {
                b(this.fields[f].validation).hide();
                b(this.fields[f].input).removeClass("op-web-theme-validation-marker");
                if ((d == undefined || d == true) && !a.hasFieldValidResponse.apply(this, [f])) {
                    var e = this.json[f].validation.message && this.json[f].validation.message.def != "" ? this.json[f].validation.message.def : "";
                    if (e != "") {
                        b(this.fields[f].validation).html(b.ObjectPlanet.spaces(e)).show()
                    }
                    b(this.fields[f].input).addClass("op-web-theme-validation-marker")
                }
            }
        },
        getResponse: function () {
            var d = [];
            for (var e = 0; e < this.fields.length; e++) {
                d[e] = {
                    value: encodeURIComponent(b(this.fields[e].input).val())
                };
                var f = this.json && this.json[e].type ? this.json[e].type : "text";
                if (f == "integer" || f == "decimal") {
                    d[e].value = (b.trim(d[e].value) != "" ? d[e].value - 0 : "");
                    if (isNaN(d[e].value)) {
                        d[e].value = ""
                    }
                }
            }
            return d
        },
        setExcludeLabel: function (e) {
            var f = b(e.not).hasClass("notActive") ? "Include " : "Exclude ";
            var d = f + (b(e.input).val() != "" ? b(e.input).val() : "empty");
            b(e.not).floatLabel("setLabel", d)
        },
        validNumericKey: function (d) {
            return (d == 0 || (d >= 48 && d <= 57) || (d >= 96 && d <= 105) || d == b.ObjectPlanet.KEY.BACKSPACE || d == b.ObjectPlanet.KEY.TAB || d == b.ObjectPlanet.KEY.LEFT || d == b.ObjectPlanet.KEY.RIGHT || d == b.ObjectPlanet.KEY.DELETE || d == b.ObjectPlanet.KEY.ENTER)
        }
    };
    var c = {
        ensureNumbersOnly: function (f) {
            var d = f.keyCode;
            var e = this.field.json && (this.field.json.type == "integer" || this.field.json.type == "decimal");
            if (e && this.field.json.type == "decimal" && b(this).val().indexOf(".") == -1) {
                return a.validNumericKey(d) || d == 190 || (b(this).val() == "" && (d == 189 || d == 45))
            } else {
                if (e) {
                    return a.validNumericKey(d) || (b(this).val() == "" && (d == 189 || d == 45))
                }
            }
        },
        hideValidation: function () {
            b(this.field.validation).hide();
            b(this.field.input).removeClass("op-web-theme-validation-marker")
        }
    }
})(jQuery);
(function (b) {
    b.fn.excludeIcon = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (c) {
            b(this).moduleIcon().excludeIcon("paint")
        },
        paint: function () {
            this.width = b(this).width();
            this.height = b(this).height();
            var d = {
                x: this.width / 2,
                y: this.height / 2
            };
            var c = Math.min(d.x, d.y) - 2;
            var e = this.getContext("2d");
            e.strokeStyle = b(this).hasClass("notInactive") ? "black" : "#aa2222";
            e.lineWidth = 2;
            e.arc(d.x, d.y, c, 0, Math.PI * 2);
            e.moveTo(d.x + c * Math.cos(Math.PI * 0.25), d.y + c * Math.sin(Math.PI * 0.25));
            e.lineTo(d.x + c * Math.cos(Math.PI * 1.25), d.y + c * Math.sin(Math.PI * 1.25));
            e.stroke()
        }
    }
})(jQuery);
(function (b) {
    b.commentForm = function (g, f) {
        var d = '<div class="op-web-survey-question-response op-web-theme-question-response op-web-survey-question-comment">';
        var c = f && f.filter == true ? " hand" : "";
        d += '<label class="textSelect' + c + '">';
        if (f && f.filter == true) {
            d += '<input class="hand" type="checkbox" checked="checked">'
        }
        var h = f && f.filter == true ? ' style="display: none;"' : "";
        var e = (g && g.label && typeof g.label.def == "string" ? b.ObjectPlanet.spaces(g.label.def).replace(/\n/g, "<br>") : "");
        if (f && f.filter == true) {
            e = "Include free-text comment"
        }
        d += e;
        d += "</label>";
        d += '<textarea class="op-web-survey-borderbox op-web-theme-input" encoded="true"' + h + "></textarea>";
        return d + "</div>"
    };
    b.fn.commentForm = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (c) {
            this.label = b(this).find("label").get(0);
            this.textarea = b(this).find("textarea").autoSizeTextArea({
                horizontal: false,
                minHeight: true
            }).get(0);
            this.filter = b(this).find("input[type=checkbox]").get(0)
        },
        getResponse: function () {
            return {
                value: encodeURIComponent(b(this.textarea).val())
            }
        }
    }
})(jQuery);
(function (b) {
    b.fn.inputBox = function () {
        return this.opWidget(a, arguments)
    };
    b.inputBox = function (e) {
        var d = e != null && e.start != null ? e.start : "<input";
        d += ' class="';
        if (e != null && e.classes != null) {
            d += e.classes + " "
        }
        d += "borderBox textbox op-web-input-box op-web-input-box-font";
        if (e != null && e.large == true) {
            d += " op-web-font-input-large"
        }
        if (e != null && e.disabled == true) {
            d += " op-web-content-color-disabled"
        }
        d += '"';
        if (e != null && e.attributes != null) {
            d += " " + e.attributes
        }
        d += ">";
        if (e != null && e.content != null) {
            d += e.content
        }
        if (e != null && e.end != null) {
            d += e.end
        }
        if (e != null && e.label != null) {
            d = '<div style="width: 100%; display: inline-block; position: relative;">' + d;
            d += '<label class="textbox-label op-web-input-box-label">' + e.label + "</label></div>"
        }
        return d
    };
    b.fn.opOriginalVal = b.fn.val;
    b.fn.val = function (d) {
        if (d != null && this.length > 0 && this[0] != null) {
            this[0].currentValue = d
        }
        return b.fn.opOriginalVal.apply(this, [d])
    };
    var a = {
        init: function (e) {
            var d = b(this).prop("options", e != null ? e : {}).get(0);
            if (this.options.reset == undefined) {
                this.options.reset = true
            }
            if (this.options.disabled == undefined) {
                this.options.disabled = true
            }
            this.label = b(this).find("label").get(0);
            this.input = this.label != null ? b(this).find(":input").get(0) : this;
            b(this.input).bind("mouseup keyup", function (f) {
                if ((f.type == "mouseup" || f.keyCode == b.ObjectPlanet.KEY.TAB) && d.options.defaultText != null && b(this).val() == d.options.defaultText) {
                    this.selectionStart = 0;
                    this.selectionEnd = d.options.defaultText.length
                }
            });
            this.input.currentValue = b(this.input).val();
            b(this.input).prop("self", this).bind("click focus", c.enable).bind("blur", c.disable).bind("keydown", c.setCurrentValue).bind("keyup", c.triggerChange);
            b(this.label).bind("mouseenter", function (f) {
                b(d.input).trigger(f)
            }).bind("click", function (f) {
                b(d.input).trigger("focus")
            })
        }
    };
    var c = {
        enable: function () {
            b(this).removeClass("op-web-content-color-disabled")
        },
        disable: function () {
            if (this.self.options && this.self.options.defaultText && this.value == this.self.options.defaultText && this.self.options.disabled == true) {
                b(this).addClass("op-web-content-color-disabled")
            }
            if (this.value == "" && this.self.options != null && this.self.options.reset == true && this.self.options.defaultText != null) {
                this.value = this.self.options.defaultText
            }
        },
        setCurrentValue: function () {
            this.currentValue = b(this).val()
        },
        triggerChange: function () {
            if (b(this).val() != this.currentValue) {
                b(this).trigger("change")
            }
        }
    }
})(jQuery);
(function (b) {
    b.autoSizeInput = {
        outer: function (f, g) {
            var h = b.autoSizeInput.style(g, "border-left-width");
            var d = b.autoSizeInput.style(g, "border-right-width");
            var e = b.autoSizeInput.style(g, "padding-left");
            var i = b.autoSizeInput.style(g, "padding-right");
            return Math.round(b.ObjectPlanet.text.width(f, g) + h + e + i + d + 2)
        }
    };
    b.autoSizeInput.style = function (d, e) {
        var f = b.ObjectPlanet.style(d, e);
        return f != undefined ? f.replace("px", "") - 0 : 0
    }, b.fn.autoSizeInput = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (d) {
            b(this).bind("keydown", c.adjustImmediate).bind("keyup change", c.adjust).get(0).self = this
        },
        adjust: function (d) {
            d = d ? d : b(this).val();
            if (d == "" && b(this).attr("placeholder") != "") {
                d = b(this).attr("placeholder")
            }
            b(this).css("width", a.width.apply(this, [d]) + "px")
        },
        width: function (d) {
            if (b(this).hasClass("borderBox")) {
                return b.autoSizeInput.outer(d, this)
            }
            return b.ObjectPlanet.text.width(d, this) + 2
        }
    };
    var c = {
        adjustImmediate: function (e) {
            if (e.originalEvent) {
                var d = e.originalEvent.keyIdentifier;
                if (d && d.charAt(0) == "U" && d.charAt(1) == "+" && d != "U+0009") {
                    var f = String.fromCharCode(parseInt("0x" + d.substring(2)));
                    if (e.shiftKey == false && f != null) {
                        f = f.toLowerCase()
                    }
                    a.adjust.apply(this.self, [b(this).val() + f])
                }
            }
        },
        adjust: function () {
            a.adjust.apply(this)
        }
    }
})(jQuery);
(function (b) {
    b.autoSizeTextArea = function (d) {
        d = d ? d : {};
        d.start = "<textarea";
        d.end = "</textarea>";
        if (d.attributes == null) {
            d.attributes = 'rows="1"'
        }
        return b.inputBox(d)
    };
    b.autoSizeTextArea.outer = function (d, f, h) {
        var j = b.autoSizeInput.style(f, "border-top-width");
        var e = b.autoSizeInput.style(f, "border-bottom-width");
        var g = b.autoSizeInput.style(f, "padding-top");
        var i = b.autoSizeInput.style(f, "padding-bottom");
        return b.ObjectPlanet.text.height(d, f, h) + j + g + i + e
    };
    b.fn.autoSizeTextArea = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (d) {
            b(this).inputBox(d);
            if (this.options && this.options.horizontal == null) {
                this.options.horizontal = true
            }
            if (this.options && this.options.vertical == null) {
                this.options.vertical = true
            }
            if (this.options && this.options.useMaxWidth == null) {
                this.options.useMaxWidth = true
            }
            b(this.input).bind("keydown keyup", c.adjust).prop("self", this)
        },
        adjust: function (e) {
            e = e ? e : b(this.input).val();
            if (this.options && this.options.horizontal == true) {
                b(this.input).css("width", (b.autoSizeInput.outer(e, this.input) + 16) + "px")
            }
            if (this.options && this.options.vertical == true) {
                if (this.options.minHeight == true && this.minHeight == null) {
                    this.minHeight = b.ObjectPlanet.unitLess(b(this.input).css("height"))
                }
                var d = b.autoSizeTextArea.outer(e, this.input, this.options.useMaxWidth ? b(this.input).width() : null);
                if (this.minHeight != null) {
                    d = Math.max(d, this.minHeight)
                }
                b(this.input).css("height", d + "px")
            }
        }
    };
    var c = {
        adjust: function (d) {
            var e = b(this).val();
            if (d.type == "keydown") {
                this.currentValue = e;
                if (d.originalEvent && d.originalEvent.keyIdentifier == "Enter") {
                    e += "\n"
                }
            }
            a.adjust.apply(this.self, [e])
        }
    }
})(jQuery);
(function (a) {
    a.ObjectPlanet.text = {
        size: [],
        width: function (h, g) {
            a.ObjectPlanet.text._ensureContext(g);
            if (h == null || h.length == 0) {
                return 0
            }
            var e = h.match(/\n/g);
            var d = e != null ? e.length + 1 : 1;
            if (a.ObjectPlanet.text.context != null) {
                for (var c = h.split("\n"), f = 0, b = 0; f < c.length; f++) {
                    b = Math.max(b, a.ObjectPlanet.text.context.measureText(c[f]).width)
                }
            }
            return b
        },
        height: function (h, c, b) {
            var i = a.ObjectPlanet.text.lineCount(h + "", c, b);
            var j = a.ObjectPlanet.style(c, "font-size").replace("px", "") - 0;
            if (a.ObjectPlanet.text.size[j] == null) {
                a.ObjectPlanet.text.size[j] = {}
            }
            var f = a.ObjectPlanet.text.size[j];
            var d = a.ObjectPlanet.style(c, "font-family");
            if (f[d] == null) {
                f[d] = {}
            }
            f = f[d];
            var g = a.ObjectPlanet.style(c, "line-height");
            if (f[g] == null) {
                a.ObjectPlanet.text._ensureContainer();
                var e = a('<label style="border-width: 0px; padding: 0px; display: inline-block;">m<br>m<br>m<br>m</label>').css({
                    "font-size": j + "px",
                    "font-family": d,
                    "line-height": g
                }).appendTo(a.ObjectPlanet.text.container).get(0);
                f[g] = a(e).height() / 4;
                a(e).remove()
            }
            return f[g] * i
        },
        lineCount: function (k, d, b) {
            var f = 0,
                l = k.split("\n");
            if (b == null) {
                f = l.length
            }
            for (var e = 0; b - 0 > 0 && e < l.length; e++) {
                f++;
                var g = l[e].split(/(\s)/);
                for (var c = 0, h = 0; g.length > 1 && c < g.length; c++) {
                    h += a.ObjectPlanet.text.width(g[c], d);
                    if (h > b) {
                        f++;
                        h = a.ObjectPlanet.text.width(g[c], d)
                    }
                }
            }
            return f
        },
        _ensureContainer: function () {
            if (a.ObjectPlanet.text.container == null) {
                a.ObjectPlanet.text.container = a("<div></div>").css({
                    position: "absolute",
                    left: "-210px",
                    top: "-210px",
                    "background-color": "orange",
                    border: "0px",
                    padding: "0px",
                    width: "200px",
                    height: "200px",
                    "box-sizing": "border-box",
                    "-webkit-box-sizing": "border-box",
                    "-moz-box-sizing": "border-box"
                }).appendTo("body").get(0)
            }
        },
        _ensureContext: function (b) {
            if (a.ObjectPlanet.text.context == null) {
                var d = a("<canvas></canvas>").get(0);
                if (d.getContext != null) {
                    a.ObjectPlanet.text.context = d.getContext("2d")
                }
            }
            if (b != null && a.ObjectPlanet.text.context != null) {
                a.ObjectPlanet.text.context.font = a(b).css("font-weight") + " " + a(b).css("font-size") + " " + a(b).css("font-family")
            }
        }
    }
})(jQuery);