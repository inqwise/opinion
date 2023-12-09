(function (a) {
    a.fn.opWidget = function (c, d) {
        var b = this;
        this.each(function (e) {
            if (c[d[0]]) {
                b = c[d[0]].apply(this, Array.prototype.slice.call(d, 1))
            } else {
                if (typeof d[0] === "object" || !d[0]) {
                    c.init.apply(this, d)
                }
            }
        });
        return (b != undefined ? b : this)
    };
    a.isTouchPhone = function () {
        return window.orientation == "0"
    };
    a.touchstart = (jQuery.isTouchPhone() ? "touchstart" : "mousedown");
    a.touchmove = (jQuery.isTouchPhone() ? "touchmove" : "mousemove");
    a.touchend = (jQuery.isTouchPhone() ? "touchend" : "mouseup");
    a.addEventListener = function (c, b, d) {
        if (c.attachEvent) {
            c.attachEvent("on" + b, d)
        } else {
            c.addEventListener(b, d, false)
        }
    };
    if (!a.ObjectPlanet) {
        a.ObjectPlanet = {}
    }
    a.ObjectPlanet.stringEndsWith = function (b, c) {
        if (typeof b == "string" && typeof c == "string" && b.length > 0 && c.length > 0) {
            return b.lastIndexOf(c) >= 0 && b.lastIndexOf(c) + c.length == b.length
        }
        return false
    };
    a.ObjectPlanet.KEY = {
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
    a.ObjectPlanet.unitLess = function (b, c) {
        if (c == undefined) {
            c = 0
        }
        if (b != undefined && (a.ObjectPlanet.stringEndsWith(b, "px") || a.ObjectPlanet.stringEndsWith(b, "pt") || a.ObjectPlanet.stringEndsWith(b, "em") || a.ObjectPlanet.stringEndsWith(b, "mm"))) {
            return b.substring(0, b.length - 2) - 0
        }
        if (b != undefined && a.ObjectPlanet.stringEndsWith(b, "%")) {
            return b.substring(0, b.length - 1) - 0
        }
        return c
    };
    a.ObjectPlanet.setCookie = function (b, h, i) {
        var f = "";
        if (i != null && i - 0 == i) {
            var c = 1000;
            var j = 60 * c;
            var e = 60 * j;
            var g = 24 * e;
            var d = new Date().valueOf() + (i * g);
            f = "; expires=" + new Date(d).toGMTString()
        }
        document.cookie = b + "=" + h + f
    };
    a.ObjectPlanet.getCookie = function (d) {
        var e = document.cookie.split(";");
        for (var c = 0; e != null && c < e.length; c++) {
            var b = e[c].substr(0, e[c].indexOf("="));
            var f = e[c].substr(e[c].indexOf("=") + 1);
            if (a.trim(d) == a.trim(b)) {
                return f
            }
        }
        return null
    };
    a.ObjectPlanet.isValidEmail = function (c) {
        var b = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
        return b.test(c)
    };
    a.ObjectPlanet.spaces = function (b) {
        if (b == null) {
            return b
        }
        return b.replace(/ (?= )/g, "&nbsp;")
    };
    a.ObjectPlanet.wait = function (b, d, c) {
        if (!b()) {
            setTimeout(function () {
                a.ObjectPlanet.wait(b, d, c)
            }, c != null ? c : 5)
        } else {
            d()
        }
    };
    if (!a.ObjectPlanet.html) {
        a.ObjectPlanet.html = {}
    }
    a.ObjectPlanet.html.tagBody = "(?:[^\"'>]|\"[^\"]*\"|'[^']*')*";
    a.ObjectPlanet.html.tagOrComment = new RegExp("<(?:!--(?:(?:-*[^->])*--+|-?)|script\\b" + a.ObjectPlanet.html.tagBody + ">[\\s\\S]*?<\/script\\s*|style\\b" + a.ObjectPlanet.html.tagBody + ">[\\s\\S]*?</style\\s*|/?[a-z]" + a.ObjectPlanet.html.tagBody + ")>", "gi");
    a.ObjectPlanet.html.removeTags = function (b) {
        if (typeof b != "string") {
            return b
        }
        var c;
        do {
            c = b;
            b = b.replace(a.ObjectPlanet.html.tagOrComment, "")
        } while (b !== c);
        return b.replace(/</g, "&lt;")
    };
    a.fn.opOriginalVal = a.fn.val;
    a.fn.val = function (b) {
        if (b != null && this.length > 0 && this[0] != null) {
            this[0].currentValue = b
        }
        if (b != undefined) {
            if (typeof b == "string") {
                b = (b + "").replace(/&lt;/g, "<")
            }
            return a.fn.opOriginalVal.apply(this, [b])
        }
        var c = a.fn.opOriginalVal.apply(this);
        if (c != null && !a(this).attr("readonly") && !a(this).attr("encoded")) {
            c = a.ObjectPlanet.html.removeTags(c)
        }
        return c
    };
    a.ObjectPlanet.decode = function (c) {
        try {
            return decodeURIComponent(c)
        } catch (b) {
            return c
        }
    };
    if (a.ObjectPlanet.css == null) {
        a.ObjectPlanet.css = {}
    }
    a.ObjectPlanet.css.iOS = function (b) {
        if (a.ObjectPlanet.handheld != true) {
            return b
        }
        var c = b == "inherit" ? 1 : a.ObjectPlanet.unitLess(b);
        if (a.ObjectPlanet.stringEndsWith(b, "px") || a.ObjectPlanet.stringEndsWith(b, "pt")) {
            c /= 16
        }
        return Math.min(2.5, c * 1.5) + "em"
    };
    a.ObjectPlanet.css.getValues = function (f, c) {
        if (f == null) {
            return ""
        }
        var e = "";
        if (c == null || c.width == true) {
            if (f.width != null) {
                e += " width: " + f.width + ";"
            }
        }
        if (c == null || c.font == true) {
            if (f["font-family"] != null) {
                e += " font-family: " + f["font-family"] + ";"
            }
            if (f["font-size"] != null) {
                e += " font-size: " + a.ObjectPlanet.css.iOS(f["font-size"]) + ";"
            }
            if (f["font-weight"] != null) {
                e += " font-weight: " + f["font-weight"] + ";"
            }
            if (f.color != null) {
                e += " color: " + f.color + ";"
            }
            if (f["line-height"] != null) {
                e += " line-height: " + f["line-height"] + ";"
            }
        }
        if (c == null || c.background == true) {
            if (f["background-color"] != null && f["background-color"].indexOf("rgb") >= 0) {
                e += " background-color: " + f["background-color"] + ";"
            } else {
                if (f["background-color"] != null && f["background-color"].indexOf(",") > 0) {
                    var g = f["background-color"];
                    var h = g.substring(0, g.indexOf(","));
                    var d = g.substring(g.indexOf(",") + 1);
                    e += " background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%," + h + "), color-stop(100%," + d + "));";
                    e += " background-image: -moz-linear-gradient(" + h + "," + d + ");";
                    e += " background-image: -o-linear-gradient(" + h + "," + d + ");";
                    e += " background-image: -ms-linear-gradient(" + h + "," + d + ");";
                    e += " background-color: " + h + ";"
                } else {
                    if (f["background-color"] != null) {
                        e += " background-color: " + f["background-color"] + ";"
                    }
                }
            }
        }
        if (c == null || c.border == true) {
            if (a.ObjectPlanet.handheld != true && f["border-color"] != null) {
                e += " border-color: " + f["border-color"] + ";"
            }
            if (a.ObjectPlanet.handheld == true && f["border-color"] != null) {
                e += " border-color: #aaa;"
            }
            if (f["border-width"] != null) {
                e += " border-width: " + f["border-width"] + ";"
            }
            if (f["border-style"] != null) {
                e += " border-style: " + f["border-style"] + ";"
            }
            if (f["border-radius"] != null) {
                var b = f["border-radius"];
                e += " -webkit-border-radius: " + b + "; -moz-border-radius: " + b + "; border-radius: " + b + ";"
            }
        }
        if ((c == null || c.shadow == true) && f.shadow != null) {
            e += " -webkit-box-shadow: " + f.shadow + ";";
            e += " -moz-box-shadow: " + f.shadow + ";";
            e += " box-shadow: " + f.shadow + ";"
        }
        if (c == null || c.padding == true) {
            if (f.padding != null) {
                e += " padding: " + f.padding + ";"
            }
        }
        return e
    };
    a.ObjectPlanet.css.get = function (c) {
        if (c == null) {
            return ""
        }
        var b = "";
        b += ".op-web-theme-page {" + a.ObjectPlanet.css.getValues(c.page) + " }\n";
        b += ".op-web-theme-font {" + a.ObjectPlanet.css.getValues(c.survey, {
            font: true
        }) + " }\n";
        b += ".op-web-theme-survey {" + a.ObjectPlanet.css.getValues(c.survey, {
            width: true,
            padding: true
        }) + " margin-left: auto; margin-right: auto; ";
        if (c.survey.width != null) {
            b += "max-width: " + c.survey.width + "; "
        }
        b += "}\n";
        b += ".op-web-theme-survey-background {" + a.ObjectPlanet.css.getValues(c.survey, {
            background: true
        }) + " }\n";
        b += ".op-web-theme-survey-border {" + a.ObjectPlanet.css.getValues(c.survey, {
            border: true,
            shadow: true
        }) + " }\n";
        b += ".op-web-theme-heading {" + a.ObjectPlanet.css.getValues(c.heading) + " }\n";
        b += ".op-web-theme-introduction {" + a.ObjectPlanet.css.getValues(c.introduction) + " }\n";
        b += ".op-web-theme-question-text {" + a.ObjectPlanet.css.getValues(c["question-text"]) + " }\n";
        b += ".op-web-theme-question-response {" + a.ObjectPlanet.css.getValues(c.response) + " }\n";
        b += ".op-web-theme-thankyou {" + a.ObjectPlanet.css.getValues(c.thankYou) + " }\n";
        b += ".op-web-theme-submit-button {" + a.ObjectPlanet.css.getValues(c["submit-button"]) + " }\n";
        b += ".op-web-theme-input {" + a.ObjectPlanet.css.getValues(c.input) + " }\n";
        b += ".op-web-theme-select {" + a.ObjectPlanet.css.getValues(c.input, {
            font: true,
            background: true
        }) + " }\n";
        b += ".op-web-theme-section-heading {" + a.ObjectPlanet.css.getValues(c["section-heading"]) + " }\n";
        b += ".op-web-theme-section-intro {" + a.ObjectPlanet.css.getValues(c["section-intro"]) + " }\n";
        if (c.validation != null) {
            b += ".op-web-theme-validation {" + a.ObjectPlanet.css.getValues(c.validation, {
                font: true
            }) + " }\n";
            b += ".op-web-theme-validation-marker {" + a.ObjectPlanet.css.getValues(c.validation, {
                background: true
            }) + " }\n"
        } else {
            b += ".op-web-theme-validation { color: #933; }\n";
            b += ".op-web-theme-validation-marker { background-color: rgba(255,0,0,0.2); }\n"
        }
        return b
    };
    Array.prototype.shuffle = function () {
        var c = this.length,
            b, e, d;
        if (c == 0) {
            return this
        }
        while (--c) {
            b = Math.floor(Math.random() * (c + 1));
            e = this[c];
            d = this[b];
            this[c] = d;
            this[b] = e
        }
        return this
    };
    a.ObjectPlanet.style = function (b, d) {
        var c = null;
        if (b && b.style && b.style[d] != null && b.style[d].length > 0) {
            c = b.style[d]
        }
        if (c == null) {
            c = a(b).css(d)
        }
        if (c == null && document.defaultView.getComputedStyle != null) {
            c = document.defaultView.getComputedStyle(b, null)[d]
        }
        return c
    };
    a.fn.highlight = function (c) {
        if (this.cover != null) {
            return
        }
        c = typeof c == "number" ? c : a.ObjectPlanet.SPEED * 4;
        if (a((this).css("position") == "static")) {
            a(this).css("position", "relative")
        }
        this.cover = a('<div class="highlight-cover"></div>').css({
            position: "absolute",
            "border-radius": "5px",
            width: a(this).width() + "px",
            height: a(this).height() + "px",
            top: "0px",
            left: "0px",
            opacity: "0.4",
            "background-color": "#fff"
        }).appendTo(this).get(0);
        var b = this;
        a(this.cover).animate({
            opacity: "0"
        }, c, function () {
            a(b.cover).remove();
            delete b.cover
        })
    };
    a.ObjectPlanet.globalErrorHandler = function (c) {
        if (a.fn.progressIndicator != null) {
            a(document).find(".op-progress-back").each(function () {
                a(this.parentNode).progressIndicator("stop")
            })
        }
        if (c != null && typeof c.url == "string" && c.url.indexOf("objectplanet.opinio") >= 0) {
            a(a.ObjectPlanet.statusLabel).text("An error occured, we will try to fix it soon!");
            var b = c.file != null ? ("file: " + c.file + "\n") : "";
            b += "error: " + c.error + "\nurl: " + c.url + "\nline: " + c.line;
            if (a.ObjectPlanet.session != null) {
                b += "\nlogin: " + a.ObjectPlanet.session.login
            }
            if (c.json && c.json.surveyName != null) {
                b += "\nsurvey: " + c.json.surveyName
            }
            if (c.json && c.json._id != null && c.json._id.$oid != null) {
                b += "\nsid: " + c.json._id.$oid
            }
            if (c.search == null) {
                c.search = document.location.search
            }
            if (c.search != null) {
                b += "\n" + c.search
            }
            b += "\n" + navigator.userAgent;
            if (a.ObjectPlanet.reportedErrors == null) {
                a.ObjectPlanet.reportedErrors = {}
            }
            if (a.ObjectPlanet.reportedErrors[b] == null) {
                a.ObjectPlanet.reportedErrors[b] = b;
                if (a.ObjectPlanet.globalErrorHandler.noEmail != true) {
                    if (a.serverRequest != null) {
                        a.serverRequest({
                            action: "error.send",
                            error: b
                        })
                    } else {
                        a.getJSON((c.base != null ? c.base : "") + "/a?action=error.send&error=" + encodeURIComponent(b))
                    }
                }
            }
        }
    };
    if (a.ObjectPlanet.color == null) {
        a.ObjectPlanet.color = {}
    }
    a.ObjectPlanet.color.hexToRgbStr = function (c) {
        var b = a.ObjectPlanet.color.hexToRgb(c);
        return b[0] + "," + b[1] + "," + b[2]
    };
    a.ObjectPlanet.color.hexToRgb = function (h) {
        h = h.replace(/^\s*#|\s*$/g, "");
        if (h.length == 3) {
            h = h.replace(/(.)/g, "$1$1")
        }
        try {
            var f = parseInt(h.substring(0, 2), 16);
            var e = parseInt(h.substring(2, 4), 16);
            var c = parseInt(h.substring(4, 6), 16);
            return [f, e, c]
        } catch (d) {
            return [0, 0, 0]
        }
    };
    a.ObjectPlanet.color.rgbToHex = function (c) {
        var b = c[2] | (c[1] << 8) | (c[0] << 16);
        return "#" + b.toString(16)
    };
    a.ObjectPlanet.color.toHex = function (c) {
        if (c.charAt(0) === "#") {
            return c
        }
        var f = /(.*?)rgb\((\d+), (\d+), (\d+)\)/.exec(c);
        var g = parseInt(f[2]);
        var e = parseInt(f[3]);
        var b = parseInt(f[4]);
        var d = b | (e << 8) | (g << 16);
        return f[1] + "#" + d.toString(16)
    };
    a.ObjectPlanet.color.adjust = function (d, e) {
        if (typeof d == "string" && d.charAt(0) == "r") {
            d = a.ObjectPlanet.color.toHex(d)
        }
        if (typeof d == "string" && d.length > 1 && d.charAt(0) == "#") {
            d = d.substring(1)
        }
        var i = parseInt(d, 16);
        var h = (i >> 16) + e;
        h = Math.max(Math.min(h, 255), 0);
        var c = ((i >> 8) & 255) + e;
        c = Math.max(Math.min(c, 255), 0);
        var f = (i & 255) + e;
        f = Math.max(Math.min(f, 255), 0);
        return (f | (c << 8) | (h << 16)).toString(16)
    };
    a.ObjectPlanet.color.adjustLightness = function (d, c) {
        var b = a.ObjectPlanet.color.hexToHsl(a.ObjectPlanet.color.nameToHex(d));
        b[2] = Math.min(1, Math.max(b[2] + c, 0));
        return a.ObjectPlanet.color.hslToHex(b)
    };
    a.ObjectPlanet.color.hexToHsl = function (e) {
        var n = a.ObjectPlanet.color.hexToRgb(e);
        var c = n[0] / 255;
        var k = n[1] / 255;
        var o = n[2] / 255;
        var p = Math.max(c, k, o),
            i = Math.min(c, k, o);
        var j, q, f = (p + i) / 2;
        if (p == i) {
            j = q = 0
        } else {
            var m = p - i;
            q = f > 0.5 ? m / (2 - p - i) : m / (p + i);
            switch (p) {
            case c:
                j = (k - o) / m + (k < o ? 6 : 0);
                break;
            case k:
                j = (o - c) / m + 2;
                break;
            case o:
                j = (c - k) / m + 4;
                break
            }
            j /= 6
        }
        return [j, q, f]
    };
    a.ObjectPlanet.color.hslToHex = function (n) {
        var j = n[0],
            o = n[1],
            i = n[2];
        var c, k, m;
        if (o == 0) {
            c = k = m = i
        } else {
            function f(h, g, b) {
                if (b < 0) {
                    b += 1
                }
                if (b > 1) {
                    b -= 1
                }
                if (b < 1 / 6) {
                    return h + (g - h) * 6 * b
                }
                if (b < 1 / 2) {
                    return g
                }
                if (b < 2 / 3) {
                    return h + (g - h) * (2 / 3 - b) * 6
                }
                return h
            }
            var d = i < 0.5 ? i * (1 + o) : i + o - i * o;
            var e = 2 * i - d;
            c = f(e, d, j + 1 / 3);
            k = f(e, d, j);
            m = f(e, d, j - 1 / 3)
        }
        return a.ObjectPlanet.color.rgbToHex([c * 255, k * 255, m * 255])
    };
    a.ObjectPlanet.color.COLOR_NAMES = {
        aliceblue: "#f0f8ff",
        antiquewhite: "#faebd7",
        aqua: "#00ffff",
        aquamarine: "#7fffd4",
        azure: "#f0ffff",
        beige: "#f5f5dc",
        bisque: "#ffe4c4",
        black: "#000000",
        blanchedalmond: "#ffebcd",
        blue: "#0000ff",
        blueviolet: "#8a2be2",
        brown: "#a52a2a",
        burlywood: "#deb887",
        cadetblue: "#5f9ea0",
        chartreuse: "#7fff00",
        chocolate: "#d2691e",
        coral: "#ff7f50",
        cornflowerblue: "#6495ed",
        cornsilk: "#fff8dc",
        crimson: "#dc143c",
        cyan: "#00ffff",
        darkblue: "#00008b",
        darkcyan: "#008b8b",
        darkgoldenrod: "#b8860b",
        darkgray: "#a9a9a9",
        darkgrey: "#a9a9a9",
        darkgreen: "#006400",
        darkkhaki: "#bdb76b",
        darkmagenta: "#8b008b",
        darkolivegreen: "#556b2f",
        darkorange: "#ff8c00",
        darkorchid: "#9932cc",
        darkred: "#8b0000",
        darksalmon: "#e9967a",
        darkseagreen: "#8fbc8f",
        darkslateblue: "#483d8b",
        darkslategray: "#2f4f4f",
        darkslategrey: "#2f4f4f",
        darkturquoise: "#00ced1",
        darkviolet: "#9400d3",
        deeppink: "#ff1493",
        deepskyblue: "#00bfff",
        dimgray: "#696969",
        dimgrey: "#696969",
        dodgerblue: "#1e90ff",
        firebrick: "#b22222",
        floralwhite: "#fffaf0",
        forestgreen: "#228b22",
        fuchsia: "#ff00ff",
        gainsboro: "#dcdcdc",
        ghostwhite: "#f8f8ff",
        gold: "#ffd700",
        goldenrod: "#daa520",
        gray: "#808080",
        grey: "#808080",
        green: "#008000",
        greenyellow: "#adff2f",
        honeydew: "#f0fff0",
        hotpink: "#ff69b4",
        indianred: "#cd5c5c",
        indigo: "#4b0082",
        ivory: "#fffff0",
        khaki: "#f0e68c",
        lavender: "#e6e6fa",
        lavenderblush: "#fff0f5",
        lawngreen: "#7cfc00",
        lemonchiffon: "#fffacd",
        lightblue: "#add8e6",
        lightcoral: "#f08080",
        lightcyan: "#e0ffff",
        lightgoldenrodyellow: "#fafad2",
        lightgray: "#d3d3d3",
        lightgrey: "#d3d3d3",
        lightgreen: "#90ee90",
        lightpink: "#ffb6c1",
        lightsalmon: "#ffa07a",
        lightseagreen: "#20b2aa",
        lightskyblue: "#87cefa",
        lightslategray: "#778899",
        lightslategrey: "#778899",
        lightsteelblue: "#b0c4de",
        lightyellow: "#ffffe0",
        lime: "#00ff00",
        limegreen: "#32cd32",
        linen: "#faf0e6",
        magenta: "#ff00ff",
        maroon: "#800000",
        mediumaquamarine: "#66cdaa",
        mediumblue: "#0000cd",
        mediumorchid: "#ba55d3",
        mediumpurple: "#9370db",
        mediumseagreen: "#3cb371",
        mediumslateblue: "#7b68ee",
        mediumspringgreen: "#00fa9a",
        mediumturquoise: "#48d1cc",
        mediumvioletred: "#c71585",
        midnightblue: "#191970",
        mintcream: "#f5fffa",
        mistyrose: "#ffe4e1",
        moccasin: "#ffe4b5",
        navajowhite: "#ffdead",
        navy: "#000080",
        oldlace: "#fdf5e6",
        olive: "#808000",
        olivedrab: "#6b8e23",
        orange: "#ffa500",
        orangered: "#ff4500",
        orchid: "#da70d6",
        palegoldenrod: "#eee8aa",
        palegreen: "#98fb98",
        paleturquoise: "#afeeee",
        palevioletred: "#db7093",
        papayawhip: "#ffefd5",
        peachpuff: "#ffdab9",
        peru: "#cd853f",
        pink: "#ffc0cb",
        plum: "#dda0dd",
        powderblue: "#b0e0e6",
        purple: "#800080",
        red: "#ff0000",
        rosybrown: "#bc8f8f",
        royalblue: "#4169e1",
        saddlebrown: "#8b4513",
        salmon: "#fa8072",
        sandybrown: "#f4a460",
        seagreen: "#2e8b57",
        seashell: "#fff5ee",
        sienna: "#a0522d",
        silver: "#c0c0c0",
        skyblue: "#87ceeb",
        slateblue: "#6a5acd",
        slategray: "#708090",
        slategrey: "#708090",
        snow: "#fffafa",
        springgreen: "#00ff7f",
        steelblue: "#4682b4",
        tan: "#d2b48c",
        teal: "#008080",
        thistle: "#d8bfd8",
        tomato: "#ff6347",
        turquoise: "#40e0d0",
        violet: "#ee82ee",
        wheat: "#f5deb3",
        white: "#ffffff",
        whitesmoke: "#f5f5f5",
        yellow: "#ffff00",
        yellowgreen: "#9acd32"
    };
    a.ObjectPlanet.color.nameToHex = function (c) {
        var b = a.ObjectPlanet.color.COLOR_NAMES;
        return c != null && b[c.toLowerCase()] != null ? b[c.toLowerCase()] : c
    }
})(jQuery);
(function (b) {
    b.inputBox = function (e) {
        var d = e != null && e.start != null ? e.start : "<input";
        d += ' class="';
        if (e != null && e.classes != null) {
            d += e.classes + " "
        }
        d += 'borderBox textbox"';
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
            d += '<label class="textbox-label">' + e.label + "</label></div>"
        }
        return d
    };
    b.fn.inputBox = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (e) {
            var d = b(this).prop("options", e != null ? e : {}).get(0);
            if (this.options.reset == undefined) {
                this.options.reset = true
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
            b(this.input).prop("self", this).bind("blur", c.reset).bind("keydown", c.setCurrentValue).bind("keyup", c.triggerChange);
            b(this.label).bind("mouseenter", function (f) {
                b(d.input).trigger(f)
            }).bind("click", function (f) {
                b(d.input).trigger("focus")
            })
        }
    };
    var c = {
        reset: function () {
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
            b(this).bind("keydown", c.adjustImmediate).bind("keyup change", c.adjust).bind("paste", function () {
                var e = this;
                setTimeout(function () {
                    a.adjust.apply(e)
                }, 5)
            }).prop("self", this)
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
        init: function (e) {
            var d = b(this).inputBox(e).get(0);
            if (this.options && this.options.horizontal == null) {
                this.options.horizontal = true
            }
            if (this.options && this.options.vertical == null) {
                this.options.vertical = true
            }
            if (this.options && this.options.useMaxWidth == null) {
                this.options.useMaxWidth = true
            }
            b(this.input).bind("keydown keyup", c.adjust).prop("self", this);
            b(this.input).bind("paste", function (f) {
                setTimeout(function () {
                    a.adjust.apply(d)
                }, 5)
            })
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
        lineCount: function (l, d, b) {
            var f = 0,
                m = l.split("\n");
            if (b == null) {
                f = m.length
            }
            for (var e = 0; b - 0 > 0 && e < m.length; e++) {
                f++;
                var g = m[e].split(/(\s)/);
                for (var c = 0, h = 0; g.length > 1 && c < g.length; c++) {
                    var k = a.ObjectPlanet.text.width(g[c], d);
                    if (a.browser.msie) {
                        k *= 1.02
                    }
                    h += k;
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
jQuery.ObjectPlanet.SECTION_MOVE_SPEED = 200;
(function (a) {
    a.ObjectPlanet.survey = function (c, d) {
        window.onerror = function (j, i, h) {
            a.ObjectPlanet.globalErrorHandler({
                error: j,
                url: i,
                line: h,
                search: c,
                file: "survey.js",
                base: d
            })
        };
        var f = c != null ? c : document.location.search;
        var e = f != null && f.indexOf("&fill=true") > 0;
        var b = f != null && f.indexOf("&framed=true") > 0;
        var g = f != null && f.length > 1 ? f.substring(1) : null;
        if (g != null && g.indexOf("&") > 0) {
            g = g.substring(0, g.indexOf("&"))
        }
        a.ObjectPlanet.iPhone = (navigator.userAgent.match(/(iPhone|iPod)/g) ? true : false);
        a.ObjectPlanet.iPad = (navigator.userAgent.match(/(iPad)/g) ? true : false);
        a.ObjectPlanet.android = (navigator.userAgent.match(/(Android)/g) ? true : false);
        a.ObjectPlanet.handheld = a.ObjectPlanet != null && (a.ObjectPlanet.iPhone == true || a.ObjectPlanet.iPad == true || a.ObjectPlanet.android == true);
        if (window.parent.postMessage && a.ObjectPlanet.getCookie(g) == "true") {
            window.parent.postMessage("alreadyResponded", "*")
        }
        if (a.browser.mozilla) {
            window.addEventListener("message", function (h) {
                if (h.data == "opened") {
                    var j = a(document.body).find(".op-web-survey-iframe-container").get(0);
                    if (a(j).outerHeight() != a(document.body).height()) {
                        var i = a(j).outerHeight() - a(j).height();
                        a(j).css("min-height", a(document.body).height() - i + "px")
                    }
                }
            }, false)
        }
        a.getJSON((d != null ? d : "") + "s?" + g + "&json=true", function (r) {
            var i = a('head meta[property="murvey:sid"]').get(0);
            var p = a('head meta[property="murvey:skey"]').get(0);
            if (r && i && i.content && p && p.content) {
                r.session = {
                    sid: i.content,
                    skey: p.content
                }
            }
            if (r && r.appearance && r.appearance.css) {
                try {
                    a("#surveyTheme").html(a.ObjectPlanet.css.get(r.appearance.css));
                    a("#surveyThemeFile").remove()
                } catch (n) {}
            }
            if (b == true && a.ObjectPlanet.handheld != true) {
                a(document.body).css({
                    margin: "0px",
                    padding: "0px",
                    height: "100%",
                    "overflow-x": "hidden"
                });
                var k = (r != null && r.publish != null && r.publish.popup != null && (r.publish.popup.heading != null && r.publish.popup.heading.def != "" || r.publish.popup.introduction != null && r.publish.popup.introduction.def != ""));
                var l = a("<div></div>").framedSurvey(r, {
                    requestOn: k,
                    iframed: true
                }).appendTo("body").get(0);
                if (k == true) {
                    var m = a.fn.framedSurvey.getSurveySize(r, true);
                    a(l).css({
                        width: m.width,
                        "margin-left": "auto",
                        "margin-right": "auto",
                        top: "40%"
                    });
                    a(l).css("margin-top", "-" + a(l).outerHeight() / 2 + "px")
                } else {
                    a(l).css({
                        width: "100%",
                        height: "100%",
                        left: "0px",
                        top: "0px",
                        "margin-left": "0px"
                    })
                } if (window.parent != null && window.parent.postMessage != null) {
                    window.parent.postMessage("opened", "*")
                }
                a(window).bind("resize", function (s) {
                    a(l).framedSurvey("adjustSize").prop("resized", true)
                })
            } else {
                if (e == true || a.ObjectPlanet.handheld == true) {
                    var h = a('<div class="op-web-survey-iframe-container op-web-theme-survey-background op-web-survey-borderbox"></div>').get(0);
                    var l = a(a.webSurvey(r)).webSurvey(r).addClass("op-web-theme-survey").css("width", "100%").appendTo(h).get(0);
                    a(document.body).css({
                        margin: "0px",
                        padding: "0px"
                    }).append(h);
                    a(l).webSurvey("adjust").webSurvey("adjustSectionTable", h);
                    if (window.parent != null && window.parent.postMessage != null) {
                        window.parent.postMessage("opened", "*")
                    }
                    if (a.ObjectPlanet.iPhone == true) {
                        a(l).css({
                            "padding-left": "5px",
                            "padding-right": "5px"
                        })
                    }
                    a(h).css("min-height", "100%");
                    a(document.body).css({
                        height: "100%",
                        "overflow-x": "hidden"
                    });
                    if (a(h).outerHeight() < a(document.body).height() && a.browser.opera) {
                        a(h).css("height", "100%")
                    }
                    a(l).webSurvey("paint");
                    if (l.json != null && l.json.sections != null && l.json.sections.section != null && l.json.sections.section.length > 0) {
                        var q = a(document.body).height();
                        if (a.browser.msie == true) {
                            var o = a.ObjectPlanet.unitLess(a(l).css("padding-top"));
                            o += a.ObjectPlanet.unitLess(a(l).css("padding-bottom"));
                            q -= o
                        }
                        a(l).css({
                            position: "relative",
                            "min-height": q + "px"
                        });
                        a(l.buttonContainer).css({
                            position: "absolute",
                            bottom: a(l).css("padding-bottom")
                        });
                        a(l.buttonContainer).css("width", a.ObjectPlanet.handheld == true ? "100%" : a(l).width() + "px")
                    }
                } else {
                    var j = a('<div class="op-web-survey-page op-web-theme-page op-web-survey-borderbox"><a href="https://www.murvey.com" target="_blank" class="op-web-survey-poweredby op-web-survey-borderbox">Powered by Murvey<sup>tm</sup> online surveys</a></div>').appendTo(document.body).get(0);
                    var l = a(a.webSurvey(r)).css({
                        position: "relative"
                    }).addClass("op-web-theme-survey op-web-theme-survey-border op-web-theme-survey-background").webSurvey(r).get(0);
                    a(l).prependTo(j).webSurvey("adjust");
                    a(document.body).css("height", "100%");
                    if (l.sections && l.sections.length > 0) {
                        a(l).webSurvey("adjustMinHeight");
                        a(window).bind("resize", function () {
                            a(l).webSurvey("adjustMinHeight")
                        })
                    }
                }
            }
        })
    }
})(jQuery);
(function (a) {
    a.ObjectPlanet.popupSurvey = function (g, f) {
        var e = f && f.preventResponse == true;
        var d = g && g.surveySetup && g.surveySetup.multipleResponses && g.surveySetup.multipleResponses.on == true;
        if (!e && !d && g != null && g._id != null && g._id.$oid != null && a.ObjectPlanet.getCookie(g._id.$oid) == "true") {
            return
        }
        var c = new Date().valueOf();
        if (g != null && g.surveySetup != null && (g.surveySetup.start > c || g.surveySetup.stop < c) && (f == null || f.preventResponse != true)) {
            return
        }
        if ((f == null || f.random != false) && g != null && g.publish != null && g.publish.popup != null && (f == null || f.preventResponse != true)) {
            if (g.publish.popup.random === 0) {
                return
            }
            if (g.publish.popup.random != null && Math.floor(Math.random() * g.publish.popup.random) != 0) {
                return
            }
        }
        if (f == null) {
            f = {}
        }
        if (f.requestOn != false) {
            f.requestOn = (g != null && g.publish != null && g.publish.popup != null && (g.publish.popup.heading != null && g.publish.popup.heading.def != "" || g.publish.popup.introduction != null && g.publish.popup.introduction.def != ""))
        }
        var b = a('<div class="op-web-survey-popup-background"><div></div></div>').bind("click", function (j) {
            if (b.submitted == true || j.target === b && --b.closeClick <= 0 && b.hasResponses != true) {
                if (window.removeEventListener != null) {
                    window.removeEventListener("onmessage", b.onMessage, false)
                }
                a(b).remove()
            }
        }).prop("closeClick", 1).get(0);
        if (g && g._id && g._id.$oid && a.ObjectPlanet.testMode != true && (f == null || f.preventResponse != true)) {
            b.closeClick = 2;
            b.onMessage = function (n) {
                if (n.data == "opened") {
                    var l = a.extend(a.fn.framedSurvey.getSurveySize(g), {
                        position: "absolute"
                    });
                    if (f.requestOn) {
                        l.width = "680px"
                    }
                    if (!f.requestOn) {
                        l.height = "80%"
                    }
                    var m = a.ObjectPlanet.unitLess(l.width);
                    var j = a(window).width() - 60;
                    if (a.ObjectPlanet.stringEndsWith(l.width, "px")) {
                        l["margin-left"] = -Math.min(j, m) / 2 + "px"
                    }
                    if (a.ObjectPlanet.stringEndsWith(l.width, "mm")) {
                        l["margin-left"] = -Math.min(j, m) / 2 + "mm"
                    }
                    if (a.ObjectPlanet.stringEndsWith(l.width, "%")) {
                        l["margin-left"] = -Math.min(j, m) / 2 + "%"
                    }
                    a(b.iframe).css(a.extend(l, {
                        "max-width": j + "px"
                    }));
                    b.iframe.loadingDone = true
                } else {
                    if (n.data == "alreadyResponses" || n.data == "no") {
                        if (window.removeEventListener != null) {
                            window.removeEventListener("message", b.onMessage, false)
                        }
                        a(b).remove();
                        if (n.data == "no") {
                            a.ObjectPlanet.setCookie(g._id.$oid, "true", 1095)
                        }
                    } else {
                        if (n.data == "closeFrame") {
                            if (window.removeEventListener != null) {
                                window.removeEventListener("message", b.onMessage, false)
                            }
                            a(b).remove()
                        } else {
                            if (n.data == "submitted") {
                                var k = g.surveySetup && g.surveySetup.multipleResponses && g.surveySetup.multipleResponses.on == true;
                                if (!k) {
                                    a.ObjectPlanet.setCookie(g._id.$oid, "true", 1095)
                                }
                                b.submitted = true
                            } else {
                                if (n.data == "surveyResponse") {
                                    b.hasResponses = true
                                }
                            }
                        }
                    }
                }
            };
            if (window.attachEvent != null) {
                window.attachEvent("onmessage", b.onMessage)
            }
            if (window.attachEvent == null && window.addEventListener != null) {
                window.addEventListener("message", b.onMessage, false)
            }
            var i = f && f.base != null ? f.base : "";
            var h = i.charAt(i.length - 1) == "/" ? i + "s?" : i + "/s?";
            b.iframe = a('<iframe class="op-web-survey-iframe" frameborder="0" src="' + h + g._id.$oid + '&framed=true"></iframe>').get(0);
            a(b.iframe).css({
                width: "0px",
                height: "0px"
            }).appendTo(b);
            a(b).appendTo("body")
        } else {
            b.frame = a(b).find("> div").framedSurvey(g, f).get(0);
            a(b.frame.close).bind("click", function () {
                a(b).remove()
            });
            if (b.frame.request != null) {
                a(b.frame.request.no).bind("click", function () {
                    a(b).remove()
                })
            }
            if (b.frame.request && b.frame.request.later != null) {
                a(b.frame.request.later).bind("click", function () {
                    a(b).remove()
                })
            }
            a(b).appendTo("body");
            if (f.requestOn == true) {
                a(b.frame).css("top", Math.max(25, Math.round(a(window).height() * 0.4 - a(b.frame).outerHeight() / 2)) + "px")
            } else {
                a(b.frame).css("height", "80%");
                a(b.frame.survey).webSurvey("adjust").webSurvey("adjustSectionTable");
                if (g != null && g.sections != null && g.sections.section != null && g.sections.section.length > 0) {
                    a(b.frame).framedSurvey("setSectionButtons").css("background-color", "rgba(0,0,0,0.6)")
                }
            }
        } if (f && f.opacity != null) {
            a(b).css("opacity", f.opacity)
        }
        return b
    }
})(jQuery);
(function (b) {
    b.fn.framedSurvey = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (f, e) {
            var d = b(this).addClass("op-web-survey-popup-frame op-web-survey-borderbox").html('<span class="op-web-survey-popup-close"></span><div class="op-web-survey-popup-box"></div>').prop("iframed", e && e.iframed == true).prop("options", e).prop("json", f).get(0);
            b(this).addClass(e && e.requestOn == true ? "op-web-survey-popup-frame-message" : "op-web-survey-popup-frame-survey");
            this.box = b(this).find(".op-web-survey-popup-box").get(0);
            this.close = b(this).find(".op-web-survey-popup-close").bind("click", function () {
                if (d.close.label != null && !b(d.survey.thankyou).is(":visible")) {
                    b(d.close.label).toggle()
                } else {
                    a.message.apply(d, ["closeFrame"])
                }
            }).get(0);
            this.close.icon = b('<canvas class="op-web-survey-popup-close-icon"></canvas>').appendTo(this.close).get(0);
            if (this.close.icon.getContext != null) {
                a.paintCloseIcon(this.close.icon)
            } else {
                b(this.close.icon).remove();
                this.close.icon = b('<span class="op-web-survey-popup-close-icon">x</span>').appendTo(this.close).get(0)
            }
            var c = {
                preventResponse: e && e.preventResponse == true
            };
            this.survey = b(b.webSurvey(f, c)).webSurvey(f, c).get(0);
            b(this.survey).addClass("op-web-theme-survey").css({
                width: "100%",
                "max-width": "100%",
                position: "static"
            });
            this.container = b('<div class="op-web-survey-iframe op-web-survey-iframe-container op-web-theme-survey-background op-web-survey-borderbox"></div>').css({
                "overflow-y": "auto"
            }).html(this.survey).get(0);
            if (this.iframed == true) {
                b(this.survey).bind("change", function (g) {
                    d.hasResponses = true;
                    if (d.close.label == null) {
                        d.close.label = b('<div class="op-web-survey-iframe-closeLabel">Close</div>').hide().appendTo(d.close).bind("click", function (h) {
                            a.message.apply(d, ["closeFrame"])
                        }).get(0)
                    }
                    a.message.apply(d, ["surveyResponse"])
                })
            }
            if (e && e.requestOn == true) {
                this.request = b('<div class="op-web-survey-popup-request op-web-survey-popup-request-gradient op-web-survey-borderbox"><label class="heading">' + (f.publish.popup.heading ? f.publish.popup.heading.def : "") + '</label><div class="intro">' + (f.publish.popup.introduction ? f.publish.popup.introduction.def.replace(/\n/g, "<br>") : "") + '</div><div class="buttons"><label class="later">' + (f.publish.popup.later ? f.publish.popup.later.def : "Later") + '</label><label class="yes">' + (f.publish.popup.yes ? f.publish.popup.yes.def : "Yes") + '</label><label class="no">' + (f.publish.popup.no ? f.publish.popup.no.def : "No") + "</label></div></div>").get(0);
                this.request.heading = b(this.request).find("label.heading").get(0);
                this.request.intro = b(this.request).find("div.intro").get(0);
                this.request.no = b(this.request).find("label.no").bind("click", function (g) {
                    a.message.apply(d, ["no"])
                }).get(0);
                this.request.later = b(this.request).find("label.later").bind("click", function (g) {
                    a.message.apply(d, ["closeFrame"])
                }).toggle(f.publish.popup.later != null && f.publish.popup.later.def != "").get(0);
                this.request.yes = b(this.request).find("label.yes").bind("click", function (i) {
                    var j = b.ObjectPlanet.unitLess(b(d).css("margin-top"));
                    b(d).css({
                        top: (b(d).position().top + j) + "px",
                        "margin-top": "0px"
                    });
                    b(d).width(b(d).width()).height(b(d).height());
                    b(d.request).hide();
                    b(d.container).show().css("overflow-y", "hidden");
                    b(d.box).css("height", "100%");
                    b(d.survey).webSurvey("adjust");
                    var g = false;
                    b(d).bind("webkitTransitionEnd oTransitionEnd transitionend", function () {
                        if (!g) {
                            g = true;
                            if (d.iframed != true) {
                                b(d).css({
                                    height: "80%",
                                    left: "50%",
                                    top: "10%"
                                })
                            }
                            b(d.container).css("overflow-y", "auto");
                            b(d.survey).webSurvey("adjustSectionTable", d.container)
                        }
                    });
                    if (d.testMode == true) {
                        b(d.container).css("overflow-y", "auto")
                    }
                    b(d).toggleClass("op-web-survey-popup-frame-transition", !d.testMode);
                    var h = b.fn.framedSurvey.getSurveySize(f);
                    if (d.iframed == true) {
                        h.height = "100%";
                        h.top = h.left = "0px";
                        h["margin-left"] = h["margin-right"] = "auto"
                    }
                    b(d).removeClass("op-web-survey-popup-frame-message").addClass("op-web-survey-popup-frame-survey").css(h);
                    if (d.survey.hasSections == true) {
                        b(d).css({
                            "background-color": "rgba(0,0,0,0.6)"
                        })
                    }
                    if (f && f.sections && f.sections.section && f.sections.section.length > 0) {
                        b(d.survey).webSurvey("adjustSectionTable", d.container);
                        a.setSectionButtons.apply(d)
                    }
                }).get(0)
            }
            if (e == null || e.requestOn != true) {
                b(this.box).css("height", "100%")
            }
            if (this.request != null) {
                b(this.box).append(b(this.request).toggle(e && e.requestOn))
            }
            b(this.box).append(b(this.container).toggle(e == null || e.requestOn != true));
            if (this.iframed != true) {
                b(this).css(b.fn.framedSurvey.getSurveySize(f, e && e.requestOn == true))
            }
        },
        message: function (c) {
            if (window.parent && window.parent.postMessage != null && this.iframed == true) {
                window.parent.postMessage(c, "*")
            }
        },
        adjustSize: function () {
            if (this.options != null && this.options.requestOn == true && this.iframed == true) {
                b(this).css(b.fn.framedSurvey.getSurveySize(this.json, true)).css({
                    "margin-left": "auto",
                    "margin-right": "auto",
                    left: "0px",
                    top: "40%",
                    "margin-top": -b(this).outerHeight() / 2 + "px"
                })
            }
            if (this.json != null && this.json.sections != null && this.json.sections.section != null && this.json.sections.section.length > 0) {
                a.setSectionButtons.apply(this);
                b(this.survey).webSurvey("adjustSectionTable")
            }
        },
        setSectionButtons: function () {
            var c = b(this.survey.buttonContainer).height() + 12;
            b(this).css({
                "padding-bottom": c + "px"
            });
            b(this.survey.buttonContainer).css({
                position: "absolute",
                left: "0px",
                bottom: -(c - 6) + "px"
            });
            if (this.survey.progress != null) {
                this.survey.progress.popupWithSections = true
            }
            b(this.survey).webSurvey("setProgressColor")
        },
        paintCloseIcon: function (c) {
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
        }
    };
    b.fn.framedSurvey.getSurveySize = function (e, i) {
        var d = e && e.appearance && e.appearance.css != null ? e.appearance.css : null;
        var c = {
            w: b(window).width(),
            h: b(window).height()
        };
        var f = d && d.survey && d.survey.width != null ? d.survey.width : "650px";
        var h = -(b.ObjectPlanet.unitLess(f) + 30) / 2 + "px";
        if (b.ObjectPlanet.stringEndsWith(f, "px")) {
            f = b.ObjectPlanet.unitLess(f) + 30 + "px"
        }
        if (b.ObjectPlanet.stringEndsWith(f, "mm")) {
            h = -b.ObjectPlanet.unitLess(f) / 2 + "mm"
        }
        if (b.ObjectPlanet.stringEndsWith(f, "%")) {
            h = -b.ObjectPlanet.unitLess(f) / 2 + "%"
        }
        var g = {
            width: i ? "500px" : f,
            height: i ? "auto" : (Math.round(c.h * 0.8) + "px"),
            left: Math.round(c.w * 0.5) + "px",
            "margin-left": i ? "-250px" : h,
            top: i ? "auto" : "10%"
        };
        return g
    }
})(jQuery);
(function (b) {
    b.webSurvey = function (o, r) {
        var p = '<div class="web-survey op-web-survey-borderbox op-web-theme-font" style="position: relative;">';
        var f = new Date().valueOf();
        var e = "";
        var i = r != null && r.preventResponse == true;
        var s = o && o.surveySetup && o.surveySetup.multipleResponses && o.surveySetup.multipleResponses.on == true;
        if (!i && !s && o != null && o._id && b.ObjectPlanet.getCookie(o._id.$oid) == "true" && !o.session) {
            p += '<p class="op-web-survey-already-responded">You can only respond once to this survey</p>'
        } else {
            if (!i && o && o.surveySetup && o.surveySetup.start > f) {
                p += '<label class="op-web-survey-info op-web-theme-font">This survey has not yet started</label>'
            } else {
                if (!i && o && o.surveySetup && o.surveySetup.stop != 0 && o.surveySetup.start != 0 && o.surveySetup.stop < f) {
                    p += '<label class="op-web-survey-info op-web-theme-font">This survey has ended</label>'
                } else {
                    if (!i && o && o.surveySetup && o.surveySetup.stop == 0 && o.surveySetup.start == 0) {
                        p += '<label class="op-web-survey-info op-web-theme-font">This survey is not active</label>'
                    } else {
                        var q = (o && o.surveyTexts && o.surveyTexts.surveyHeading ? b.ObjectPlanet.spaces(o.surveyTexts.surveyHeading.def) : "");
                        var n = (o && o.surveyTexts && o.surveyTexts.surveyIntro ? b.ObjectPlanet.spaces(o.surveyTexts.surveyIntro.def.replace(/\n/g, "<br>")) : "");
                        var h = q == null || q.length == 0 ? ' style="display: none;"' : "";
                        var m = n == null || n.length == 0 ? ' style="display: none;"' : "";
                        if (r && r.filter == true) {
                            h = m = ' style="display: none;"'
                        }
                        var l = o && o.surveyTexts && o.surveyTexts.submit ? o.surveyTexts.submit.def : "Submit";
                        var j = (o && o.surveyTexts && o.surveyTexts.thankYouNote ? b.ObjectPlanet.spaces(o.surveyTexts.thankYouNote.def.replace(/\n/g, "<br>")) : "");
                        var k = b.ObjectPlanet.handheld == true ? ' style="width: 100%;"' : "";
                        var g = b.ObjectPlanet.handheld == true ? ' style="font-size: 1em;"' : "";
                        p += '<table class="op-web-survey-table" cellspacing="0" cellpadding="0"' + k + "><tr><td>";
                        p += '<div class="section introContainer">';
                        p += '<div class="op-web-theme-heading op-web-survey-overflow-x-no textSelect"' + h + ">" + q + "</div>";
                        p += '<div class="op-web-theme-introduction op-web-survey-overflow-x-no textSelect"' + m + ">" + n + "</div>";
                        p += "</div>";
                        p += '<div class="questionContainer"></div>';
                        p += '<div class="section thankyouContainer">';
                        p += '<div class="op-web-theme-thankyou op-web-survey-overflow-x-no textSelect" style="display: none;">' + j + "</div>";
                        p += "</div>";
                        p += "</td></tr></table>";
                        p += '<div class="op-web-survey-button-container-stuffer" style="visibility: hidden; display: none;"></div>';
                        p += '<table class="op-web-survey-button-container" style="width: 100%;" cellspacing="0" cellpadding="0"><tr>';
                        p += '<td style="text-align: right;"><button class="op-web-survey-submit-button op-web-theme-submit-button"' + g + ">" + l + "</button></td>";
                        p += "</tr></table>";
                        e = ' style="display: none;"'
                    }
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
        return p + d + "</div>"
    };
    b.webSurvey.VALIDATION = "\u2605";
    b.fn.webSurvey = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (f, e) {
            var d = b(this).prop("options", e ? e : {}).prop("json", f).get(0);
            this.enlist = b(this).find(".op-web-survey-enlist").get(0);
            b(this.enlist).find("button").bind("click", function () {
                window.open("https://www.murvey.com")
            });
            if (a.hasResponded.apply(this)) {
                return
            }
            this.table = b(this).find(".op-web-survey-table").get(0);
            if (this.table != null) {
                this.table.row = b(this.table).find("tr:eq(0)").get(0)
            }
            this.heading = b(this).find(".op-web-theme-heading").get(0);
            this.introduction = b(this).find(".op-web-theme-introduction").get(0);
            this.introContainer = b(this).find(".introContainer").get(0);
            this.questionContainer = b(this).find("div.questionContainer").get(0);
            this.stuffer = b(this).find(".op-web-survey-button-container-stuffer").get(0);
            this.buttonContainer = b(this).find(".op-web-survey-button-container").get(0);
            this.submit = b(this).find("button.op-web-survey-submit-button").bind("click", c.submitAnswer).prop("self", this).prop("value", "submit").get(0);
            this.thankyou = b(this).find(".op-web-theme-thankyou").get(0);
            this.thankyouContainer = b(this).find(".thankyouContainer").get(0);
            if (f && f.sections != null && f.sections.section != null && f.sections.section.length > 0) {
                this.hasSections = true;
                a.initSectionControls.apply(this)
            }
            if (e && e.filter == true) {
                b(this.submit).hide()
            }
            a.setQuestions.apply(this, [f, e && e.filter == true ? {
                filter: true
            } : null]);
            this.SECTION_MOVE_SPEED = b.ObjectPlanet.SECTION_MOVE_SPEED;
            if (b(this.heading).text() == "" && b(this.introduction).text() == "" && this.hasSections == true) {
                var g = f.sections.section[0];
                if (g && g.questions && g.questions.length > 0 && g.questions[0] == 0) {
                    var h = this.SECTION_MOVE_SPEED;
                    this.SECTION_MOVE_SPEED = 0;
                    a.nextSection.apply(this);
                    this.SECTION_MOVE_SPEED = h
                }
            }
            b(window).bind("orientationchange", function (i) {
                a.adjustSectionTable.apply(d)
            })
        },
        initSectionControls: function () {
            b(this.submit).text(a.getButtonText.apply(this, ["start"])).prop("value", "start");
            if (this.back == null && this.progress == null) {
                var e = this.json && this.json.surveyTexts && this.json.surveyTexts.back ? this.json.surveyTexts.back.def : "Back";
                var d = b(this).find(".op-web-survey-button-container tr").get(0);
                var f = b.ObjectPlanet.handheld == true ? ' style="display: none; font-size: 1em;"' : ' style="display: none;"';
                this.back = b('<td><button class="op-web-survey-back-button op-web-theme-submit-button"' + f + ">" + e + "</button></td>").prependTo(d).find("button").bind("click", c.prevSection).prop("self", this).get(0);
                this.progress = b("<table></table>").progressBar({
                    count: this.json.questions != null ? this.json.questions.length : 1
                }).get(0);
                b('<td style="width: 100%;"></td>').append(this.progress).prependTo(d)
            }
        },
        hasResponded: function () {
            var e = this.json;
            var d = e && e.surveySetup && e.surveySetup.multipleResponses && e.surveySetup.multipleResponses.on == true;
            return (this.options.preventResponse != true && e && e._id && !e.session && !d && b.ObjectPlanet.getCookie(e._id.$oid) == "true")
        },
        setQuestions: function (h, f) {
            for (var g = 0, e = ""; h && h.questions instanceof Array && g < h.questions.length; g++) {
                e += b.questionForm(h.questions[g], (g + 1), f)
            }
            this.questions = [];
            for (var g = 0, d = b(e); h != null && h.questions instanceof Array && g < h.questions.length; g++) {
                this.questions[g] = d.eq(g).questionForm(h.questions[g], (g + 1), f).get(0)
            }
            if (h != null && h.sections != null && h.sections.section instanceof Array) {
                a.createSections.apply(this, [h]);
                a.setSectionState.apply(this)
            } else {
                b(this.introContainer).find(".op-web-survey-question").remove();
                b(this.table).css({
                    width: "100%",
                    left: "0px"
                }).find("> tbody > tr").html("<td></td>").find("td").append(b(this.introContainer).show()).append(b(this.questionContainer).html(d).show()).append(this.thankyouContainer);
                if (this.sections != null) {
                    delete this.sections
                }
            }
        },
        createSections: function (g) {
            if (this.table == null) {
                return
            }
            this.sections = [this.introContainer];
            b(this.introContainer).find(".op-web-survey-question").remove();
            b(this.table.row).html("<td></td>").find("td").html(this.introContainer);
            var d = g.surveyTexts && g.surveyTexts.surveyHeading && g.surveyTexts.surveyHeading.includeInSections == true;
            var k = g.surveyTexts && g.surveyTexts.surveyHeading ? b.ObjectPlanet.spaces(g.surveyTexts.surveyHeading.def) : "";
            b(this.questionContainer).html("");
            for (var f = 0; f < g.sections.section.length; f++) {
                var h = g.sections.section[f];
                if (h != null) {
                    this.sections[f + 1] = b('<div class="section"></div>').hide().get(0);
                    if (d == true) {
                        b('<div class="op-web-theme-heading op-web-survey-overflow-x-no textSelect">' + k + "</div>").appendTo(this.sections[f + 1])
                    }
                    var m = b.ObjectPlanet.spaces(h.heading != null && h.heading.def != null ? h.heading.def : "");
                    this.sections[f + 1].heading = b('<div class="op-web-survey-section-heading op-web-theme-section-heading">' + m + "</div>").toggle(m != "").get(0);
                    b(this.sections[f + 1]).append(this.sections[f + 1].heading);
                    var m = b.ObjectPlanet.spaces(h.intro != null && h.intro.def != null ? h.intro.def.replace(/\n/g, "<br>") : "");
                    this.sections[f + 1].intro = b('<div class="op-web-survey-section-intro op-web-theme-section-intro">' + m + "</div>").toggle(m != "").get(0);
                    b(this.sections[f + 1]).append(this.sections[f + 1].intro)
                }
                for (var e = 0; h != null && h.questions instanceof Array && e < h.questions.length; e++) {
                    b(this.sections[f + 1]).append(this.questions[h.questions[e]])
                }
            }
            var h = g.sections.section;
            var l = -1;
            for (var f = 0; f < h.length; f++) {
                if (h[f] != null && h[f].questions != null && h[f].questions.length > 0 && h[f].questions[0] != null) {
                    l = h[f].questions[0];
                    break
                }
            }
            if (l == -1 || l > 0) {
                for (var f = 0; f < (l == -1 ? g.questions.length : l); f++) {
                    b(this.introContainer).append(this.questions[f])
                }
            }
            b(this.thankyou).show();
            this.sections[this.sections.length] = b(this.thankyouContainer).hide().get(0);
            if (d == true && b(this.thankyouContainer).find(".op-web-theme-heading").size() == 0) {
                b(this.thankyouContainer).prepend('<div class="op-web-theme-heading op-web-survey-overflow-x-no textSelect">' + k + "</div>")
            }
            b(this).css("overflow-x", "hidden");
            for (var f = 1; f < this.sections.length; f++) {
                b("<td></td>").html(this.sections[f]).appendTo(this.table.row)
            }
            if (this.back == null) {
                a.initSectionControls.apply(this)
            }
        },
        setHeading: function (e) {
            b(this.heading).html(e != null ? e : "").toggle(e != null && e != "");
            for (var d = 0; this.sections != null && d < this.sections.length; d++) {
                b(this.sections[d]).find("> .op-web-theme-heading").html(e != null ? e : "")
            }
        },
        includeHeadingInSections: function (d) {
            for (var e = 1; this.sections != null && e < this.sections.length; e++) {
                if (d == true && b(this.sections[e]).find(".op-web-theme-heading").size() == 0) {
                    var f = this.json.surveyTexts && this.json.surveyTexts.surveyHeading ? b.ObjectPlanet.spaces(this.json.surveyTexts.surveyHeading.def) : "";
                    b(this.sections[e]).prepend('<div class="op-web-theme-heading op-web-survey-overflow-x-no textSelect">' + f + "</div>")
                }
                b(this.sections[e]).find(".op-web-theme-heading").toggle(d == true)
            }
        },
        adjust: function () {
            var d = b(this).width();
            for (var e = 0; this.questions != null && e < this.questions.length; e++) {
                if (this.questions[e] != null && this.questions[e].fields != null) {
                    b(this.questions[e].fields).fieldsForm("adjust")
                }
                if (this.questions[e] != null && d > 0 && b.ObjectPlanet.handheld != true) {
                    b(this.questions[e]).find(".op-web-survey-question-response").css("max-width", d + "px")
                }
            }
        },
        adjustMinHeight: function (d) {
            if (d == null) {
                d = window
            }
            var e = b.ObjectPlanet.unitLess(b(this.parentNode).css("padding-top"));
            e += b.ObjectPlanet.unitLess(b(this.parentNode).css("padding-bottom"));
            b(this).css("min-height", (b(d).height() - 25 - e) + "px");
            b(this.stuffer).css("height", b(this.buttonContainer).outerHeight() + "px").show();
            b(this.buttonContainer).css({
                position: "absolute",
                bottom: b(this).css("padding-bottom")
            });
            b(this.buttonContainer).css("width", b(this).width() + "px");
            a.adjustSectionTable.apply(this)
        },
        tablePos: function () {
            var d = this.sections != null && this.sections.length > 1 ? this.sections.length : 1;
            if (d == 1) {
                return 0
            }
            return -(d * b(this).outerWidth()) / d * this.currentSection
        },
        adjustSectionTable: function () {
            if (this.table == null) {
                return
            }
            var d = this.sections != null && this.sections.length > 1 ? this.sections.length : 1;
            if (b.ObjectPlanet.iPhone == true) {
                var f = d > 1 ? 10 : 0;
                b(this.table).css({
                    width: d > 1 ? d * b(this).outerWidth() + "px" : "100%"
                });
                a.paint.apply(this)
            } else {
                if (b.ObjectPlanet.handheld == true) {
                    var f = d > 1 ? b(this).outerWidth() - b(this).width() : 0;
                    b(this.table).css({
                        width: d > 1 ? d * b(this).outerWidth() + "px" : "100%"
                    })
                } else {
                    var f = d > 1 ? b(this).outerWidth() - b(this).width() : 0;
                    b(this.table).css({
                        width: d > 1 ? d * b(this).outerWidth() : b(this).width()
                    })
                }
            }
            b(this.table).css("left", a.tablePos.apply(this) + "px");
            b(this.table.row).find("> td").css({
                width: 100 / d + "%",
                "padding-right": f + "px",
                "padding-left": "0px"
            });
            var g = document.location.search;
            var e = g != null && g.indexOf("&fill=true") > 0;
            if ((b.ObjectPlanet.handheld == true || e == true) && d > 1) {
                b(this.stuffer).show()
            }
        },
        paint: function () {
            for (var d = 0; b.ObjectPlanet && b.ObjectPlanet.iPhone && this.questions != null && d < this.questions.length; d++) {
                if (this.questions[d] != null && this.questions[d].rating != null) {
                    b(this.questions[d].rating.handheldRange).phoneRange("paint")
                }
            }
        },
        setTexts: function (d) {
            b(this.thankyouContainer).find(".op-web-theme-heading").remove();
            a.setHeading.apply(this, [d && d.surveyTexts && d.surveyTexts.surveyHeading ? b.ObjectPlanet.spaces(d.surveyTexts.surveyHeading.def) : ""]);
            b(this.introduction).html(d && d.surveyTexts && d.surveyTexts.surveyIntro ? b.ObjectPlanet.spaces(d.surveyTexts.surveyIntro.def.replace(/\n/g, "<br>")) : "").show();
            b(this.thankyou).html(d && d.surveyTexts && d.surveyTexts.thankYouNote ? b.ObjectPlanet.spaces(d.surveyTexts.thankYouNote.def.replace(/\n/g, "<br>")) : "").hide();
            b(this.heading).toggle(b(this.heading).text() != "");
            b(this.introduction).toggle(b(this.introduction).text() != "");
            if (d && d.sections && d.sections.section && d.sections.section.length > 0 && d.sections.section[0].questions && d.sections.section[0].questions[0] == 0) {
                b(this.submit).text(a.getButtonText.apply(this, ["start"])).prop("value", "start")
            } else {
                if (d && d.sections && d.sections.section && d.sections.section.length > 0 && d.sections.section[0].questions && d.sections.section[0].questions[0] > 0) {
                    b(this.submit).text(a.getButtonText.apply(this, ["next"])).prop("value", "next")
                } else {
                    b(this.submit).text(a.getButtonText.apply(this, ["submit"])).prop("value", "submit")
                }
            }
            b(this.back).text(a.getButtonText.apply(this, ["back"]))
        },
        reset: function (d) {
            this.json = d;
            if (d == null || d.sections == null) {
                b(this.progress).css("visibility", "hidden");
                b(this.back).hide();
                this.submit.value = "submit";
                if (this.sections != null) {
                    delete this.sections
                }
                if (this.currentSection != null) {
                    delete this.currentSection
                }
            }
            a.setTexts.apply(this, [d]);
            a.setQuestions.apply(this, [d]);
            a.setSectionState.apply(this);
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
            } if (this.json.sections != null) {
                a.createSections.apply(this, [this.json]);
                b(this.progress).progressBar("setCount", this.questions.length)
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
            if (this.json.sections != null) {
                if (e <= this.progress.value) {
                    this.progress.value--
                }
                b(this.progress).progressBar("setCount", this.questions.length)
            }
        },
        move: function (e) {
            if (e == null || typeof e.from != "number" || e.from < 0 || e.from >= this.questions.length) {
                return
            }
            if (typeof e.to != "number" || e.to < 0 || e.to >= this.questions.length) {
                return
            }
            if (this.questions == null || this.questions == null || this.questions.length <= 1) {
                return
            }
            if (this.sections == null) {
                var k = this.questions[e.from];
                if (e.to == 0) {
                    b(k).insertBefore(this.questions[e.to])
                } else {
                    if (e.to > e.from) {
                        b(k).insertAfter(this.questions[e.to])
                    } else {
                        b(k).insertAfter(this.questions[e.to - 1])
                    }
                }
            } else {
                if (typeof e.section == "number") {
                    var d = b(this.questions[e.from]).closest(".section").get(0);
                    for (var g = 1, j = 0; g <= e.section; g++) {
                        j += b(this.sections[g]).find(".op-web-survey-question").size();
                        if (d == this.sections[g]) {
                            j--
                        }
                    }
                    var h = e.to - j;
                    if (b(this.sections[e.section + 1]).find(".op-web-survey-question").size() == 0) {
                        b(this.sections[e.section + 1]).append(this.questions[e.from])
                    } else {
                        if (h > 0) {
                            var f = b(this.sections[e.section + 1]).find(".op-web-survey-question").eq(h - 1).get(0);
                            if (d == this.sections[e.section + 1]) {
                                f = b(this.sections[e.section + 1]).find(".op-web-survey-question").eq(h).get(0);
                                b(this.questions[e.from]).insertAfter(f)
                            } else {
                                b(this.questions[e.from]).insertAfter(f)
                            }
                        } else {
                            var f = b(this.sections[e.section + 1]).find(".op-web-survey-question").eq(h).get(0);
                            b(this.questions[e.from]).insertBefore(f)
                        }
                    }
                }
            }
            this.questions.splice(e.to, 0, this.questions.splice(e.from, 1)[0]);
            for (var g = 0; g < this.questions.length; g++) {
                b(this.questions[g].questionNumber).text((g + 1) + ".")
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
            a.scrollToInvalid.apply(this)
        },
        scrollToInvalid: function () {
            for (var d = 0; this.questions != null && d < this.questions.length; d++) {
                if (!b(this.questions[d]).questionForm("hasValidResponse")) {
                    this.questions[d].scrollIntoView();
                    break
                }
            }
        },
        nextSection: function () {
            if (this.sections == null || this.currentSection > this.sections.length - 1) {
                return
            }
            if (this.currentSection == null) {
                this.currentSection = 0
            }
            var g = true;
            if (this.currentSection > 0 && this.currentSection < this.sections.length - 2) {
                var h = this.json.sections.section[this.currentSection - 1].questions;
                for (var e = 0; h != null && e < h.length; e++) {
                    if (b(this.questions[h[e]]).questionForm("hasValidResponse") == false) {
                        g = false;
                        b(this.questions[h[e]]).questionForm("showValidation", true)
                    }
                }
            } else {
                if (this.json.sections && this.json.sections.section && this.json.sections.section.length > 0) {
                    var f = this.json.sections.section[0];
                    for (var e = 0; f && f.questions && f.questions.length > 0 && e < f.questions[0]; e++) {
                        if (b(this.questions[e]).questionForm("hasValidResponse") == false) {
                            g = false;
                            b(this.questions[e]).questionForm("showValidation", true)
                        }
                    }
                }
            } if (g == true) {
                b(this.sections[++this.currentSection]).show();
                var d = this;
                a.setSectionState.apply(this);
                if (b.ObjectPlanet.handheld == true) {
                    a.scrollTop.apply(this);
                    b(this.sections[d.currentSection - 1]).hide();
                    a.adjustSectionTable.apply(this)
                } else {
                    b(this.table).animate({
                        left: a.tablePos.apply(this) + "px"
                    }, this.SECTION_MOVE_SPEED, function () {
                        a.scrollTop.apply(d);
                        b(d.sections[d.currentSection - 1]).hide();
                        a.adjustSectionTable.apply(d)
                    })
                }
            } else {
                a.scrollToInvalid.apply(this)
            }
        },
        prevSection: function () {
            if (this.sections == null || this.currentSection < 0) {
                return
            }
            this.currentSection--;
            var d = this;
            if (this.currentSection >= 0) {
                b(this.sections[this.currentSection]).show()
            }
            if (b.ObjectPlanet.handheld == true) {
                b(this.table).css({
                    left: a.tablePos.apply(this) + "px"
                });
                a.scrollTop.apply(this);
                a.setSectionState.apply(this);
                if (this.currentSection < this.sections.length) {
                    b(this.sections[this.currentSection + 1]).hide()
                }
                a.adjustSectionTable.apply(d)
            } else {
                b(this.table).animate({
                    left: a.tablePos.apply(this) + "px"
                }, this.SECTION_MOVE_SPEED, function () {
                    a.scrollTop.apply(d);
                    a.setSectionState.apply(d);
                    if (d.currentSection < d.sections.length) {
                        b(d.sections[d.currentSection + 1]).hide()
                    }
                    a.adjustSectionTable.apply(d)
                })
            }
        },
        scrollTop: function () {
            if (this.parentNode != null && this.parentNode.scrollTop > 0) {
                this.parentNode.scrollTop = 0
            } else {
                if (this.parentNode != null && this.parentNode.parentNode != null && this.parentNode.parentNode.scrollTop > 0) {
                    this.parentNode.parentNode.scrollTop = 0
                }
            }
        },
        showSection: function (g, l) {
            if (g == null) {
                return
            }
            if (this.sections != null && g.question >= 0 && this.questions != null && g.question < this.questions.length && this.json.sections && this.json.sections.section != null) {
                for (var h = 0, k = 0; h < this.json.sections.section.length; h++) {
                    for (var f = 0; f < this.json.sections.section[h].questions.length; f++) {
                        if (g.question == this.json.sections.section[h].questions[f]) {
                            this.currentSection = h + 1
                        }
                    }
                }
                if (this.json.sections.section[0].questions[0] > 0 && g.question < this.json.sections.section[0].questions[0]) {
                    this.currentSection = Math.max(0, this.currentSection - 1)
                }
            } else {
                if (this.sections != null && g.introduction == true) {
                    this.currentSection = 0
                } else {
                    if (this.sections != null && g.thankyou == true) {
                        this.currentSection = this.sections.length - 1
                    } else {
                        if (this.sections != null && g.section >= 0) {
                            this.currentSection = Math.min(g.section, this.sections.length - 1);
                            if (this.sections[this.currentSection] != null) {
                                this.sections[this.currentSection].scrollIntoView();
                                b(this.sections[this.currentSection].heading).highlight(b.ObjectPlanet.SPEED * 4);
                                b(this.sections[this.currentSection].intro).highlight(b.ObjectPlanet.SPEED * 4)
                            }
                            var d = b(this).closest(".contentContainer").get(0);
                            if (d != null) {
                                d.scrollTop -= 25
                            }
                        }
                    }
                }
            }
            var e = this;
            if (this.sections != null) {
                b(this.sections[this.currentSection]).show()
            }
            b(this.table).animate({
                left: a.tablePos.apply(this) + "px"
            }, this.SECTION_MOVE_SPEED, function () {
                for (var j = 0; e.sections != null && j < e.sections.length; j++) {
                    if (j != e.currentSection) {
                        b(e.sections[j]).hide()
                    }
                }
                a.setSectionState.apply(e);
                a.adjustSectionTable.apply(e);
                if (l != null) {
                    l.apply(e)
                }
            })
        },
        getButtonText: function (d) {
            var e = this.json && this.json.surveyTexts ? this.json.surveyTexts : null;
            var f = "Submit";
            if (d == "start") {
                f = (e && e.start && e.start.def && e.start.def != "" ? b.ObjectPlanet.spaces(e.start.def) : "Start")
            }
            if (d == "back") {
                f = (e && e.back && e.back.def && e.back.def != "" ? b.ObjectPlanet.spaces(e.back.def) : "Back")
            }
            if (d == "next") {
                f = (e && e.next && e.next.def && e.next.def != "" ? b.ObjectPlanet.spaces(e.next.def) : "Next")
            }
            if (d == "submit") {
                f = (e && e.submit && e.submit.def && e.submit.def != "" ? b.ObjectPlanet.spaces(e.submit.def) : "Submit")
            }
            if (typeof f == "string") {
                f = f.replace(/&lt;/g, "<")
            }
            return f
        },
        setSectionState: function () {
            if (this.sections == null || this.sections.length == 0) {
                if (!b(this.thankyou).is(":visible")) {
                    b(this.heading).toggle(b(this.heading).text() != "");
                    b(this.introduction).toggle(b(this.introduction).text() != "");
                    b(this.back).hide();
                    b(this.progress).css("visibility", "hidden")
                }
                return
            }
            if (this.currentSection == null) {
                this.currentSection = 0
            }
            this.currentSection = Math.min(this.currentSection, this.sections.length - 1);
            var g = this.sections != null && this.currentSection > 0 && this.currentSection < this.sections.length - 1;
            b(this.back).toggle(g);
            b(this.progress).css("visibility", g ? "visible" : "hidden");
            b(this.progress).progressBar("setCount", this.questions.length);
            b(this.submit).toggle(this.sections == null || this.currentSection < this.sections.length - 1);
            if (this.currentSection == 0 && this.json.sections != null) {
                if (this.json.sections.section[0].questions[0] > 0) {
                    b(this.submit).text(a.getButtonText.apply(this, ["next"])).prop("value", "next")
                } else {
                    b(this.submit).text(a.getButtonText.apply(this, ["start"])).prop("value", "start")
                }
                b(this.progress).progressBar("set", this.json.sections.section[0].questions[0])
            } else {
                if (this.currentSection == this.sections.length - 1 && this.json.sections != null) {
                    b(this.submit).hide()
                } else {
                    if (this.currentSection == this.sections.length - 2 && this.json.sections != null) {
                        b(this.submit).text(a.getButtonText.apply(this, ["submit"])).prop("value", "submit");
                        b(this.progress).progressBar("set", this.questions.length)
                    } else {
                        if (this.currentSection < this.sections.length && this.json.sections != null) {
                            b(this.submit).text(a.getButtonText.apply(this, ["next"])).prop("value", "next");
                            for (var d = 1, f = 0; d <= this.currentSection; d++) {
                                var e = this.json.sections.section[d - 1].questions;
                                if (e.length > 0) {
                                    f = Math.max(e[e.length - 1] + 1, f)
                                }
                            }
                            b(this.progress).progressBar("set", f);
                            if (this.currentSection == 1 && b(this.heading).text() == "" && b(this.introduction).text() == "") {
                                b(this.back).hide()
                            }
                        }
                    }
                }
            }
            a.setProgressColor.apply(this)
        },
        setProgressColor: function (g) {
            var f = b.ObjectPlanet.style(this, "background-color");
            if (this.progress && this.progress.popupWithSections == true) {
                f = "#ddd"
            }
            if (this.progress && !this.progress.popupWithSections) {
                try {
                    if (g && g.survey && g.survey["background-color"] != null) {
                        f = g.survey["background-color"]
                    } else {
                        if (this.json.appearance && this.json.appearance.css && this.json.appearance.css.survey && this.json.appearance.css.survey["background-color"] != null) {
                            f = this.json.appearance.css.survey["background-color"]
                        } else {
                            if (this.json.appearance == null) {
                                f = "#9dacad"
                            }
                        }
                    } if (f != null && f.indexOf("rgb") == 0) {
                        f = b.ObjectPlanet.color.toHex(f)
                    } else {
                        if (f != null && f.indexOf(",") > 0) {
                            f = f.substring(f.indexOf(",") + 1)
                        }
                    }
                    f = b.ObjectPlanet.color.nameToHex(f);
                    var d = b.ObjectPlanet.color.hexToHsl(f);
                    f = b.ObjectPlanet.color.adjustLightness(f, d[2] > 0.5 ? -0.25 : 0.25)
                } catch (e) {
                    if (window.getComputedStyle != null) {
                        f = window.getComputedStyle(this).backgroundColor
                    }
                }
            }
            if (this.progress != null) {
                b(this.progress).find("td").not(this.progress.label).css({
                    "background-color": f,
                    opacity: "0.2"
                }).filter(".filled").css("opacity", "1");
                if (this.progress && this.progress.popupWithSections == true) {
                    b(this.progress.label).css("color", "white")
                }
                b(this.progress).css("visibility", this.progress.value > 0 && !b(this.thankyou).is(":visible") ? "visible" : "hidden")
            }
        },
        submitAnswer: function () {
            b(this.submit).attr("disabled", "disabled");
            var d = this;
            var e = function () {
                b(this.submit).removeAttr("disabled");
                if (this.sections && this.sections.length > 1) {
                    a.nextSection.apply(d)
                } else {
                    b(this.introduction).add(this.questions).add(this.submit).add(this.back).hide();
                    b(this.thankyou).add(this.thankyouContainer).show();
                    b(this.enlist).toggle(!this.options.previewMode)
                }
                b(this).trigger("survey.submit");
                var g = this.options && this.options.preventResponse == true;
                var f = this.json && this.json.surveySetup && this.json.surveySetup.multipleResponses && this.json.surveySetup.multipleResponses.on == true;
                if (!g && !f && this.json && this.json._id && this.json._id.$oid) {
                    b.ObjectPlanet.setCookie(this.json._id.$oid, "true", 1095)
                }
                if (window.parent != null && window.parent.postMessage != null) {
                    window.parent.postMessage("submitted", "*")
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
            if (this.self.submit.value == "next" || this.self.submit.value == "start") {
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
    b.fn.progressBar = function () {
        return this.opWidget(a, arguments)
    };
    var c = 60;
    var a = {
        init: function (d) {
            b(this).addClass("op-web-survey-progress");
            this.count = d != null && d.count != null ? d.count : 0;
            b(this).html("<tr>" + a.td(this.count) + '<td class="label"></td></tr>');
            this.label = b(this).find("td.label").get(0)
        },
        max: function (d) {
            while (d > c) {
                d = Math.round(d / 2)
            }
            return d
        },
        td: function (f) {
            f = a.max(f);
            for (var e = 0, d = ""; e < f; e++) {
                d += "<td></td>"
            }
            return d
        },
        set: function (f) {
            this.value = f;
            var g = b(this).find("td").removeClass("filled").css("background-color", "");
            var e = Math.round(a.max(this.count) * f / this.count);
            for (var d = 0; d < e; d++) {
                g.eq(d).addClass("filled")
            }
            b(this.label).text(Math.round(f / this.count * 100) + "%")
        },
        setCount: function (f) {
            for (var e = 0, d = ""; e < f; e++) {
                d += "<td></td>"
            }
            b(this).find("tr").html(a.td(f) + '<td class="label"></td>');
            b(this).prop("label", b(this).find("td.label").get(0)).prop("count", f).progressBar("set", this.value)
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
        } if (d && d.filter == true && e && e.comment) {
            if (e.rating != null || e.choice != null || e.dropdown != null || e.fields != null) {
                c += b.commentForm(e ? e.comment : null, d)
            }
        } else {
            if (e && e.comment) {
                c += b.commentForm(e ? e.comment : null, d)
            }
        }
        return c
    };
    b.fn.questionForm = function () {
        return this.opWidget(a, arguments)
    };
    b.questionForm.hasValidation = function (c) {
        if (c != null && c.rating != null) {
            return b.ratingForm.hasValidation(c.rating)
        }
        if (c != null && c.choice != null) {
            return b.choiceForm.hasValidation(c.choice)
        }
        if (c != null && c.dropdown != null) {
            return b.dropdownForm.hasValidation(c.dropdown)
        }
        if (c != null && c.fields != null) {
            return b.fieldsForm.hasValidation(c.fields)
        }
        return false
    };
    var a = {
        init: function (e, f, d) {
            var c = b(this).prop("json", e).prop("current", null).get(0);
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
            if (this.star != null) {
                delete this.star
            }
            a.setValidationStar.apply(this)
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
                var d = b.ObjectPlanet.spaces(c.label.def).replace(/\n/g, "<br>");
                b(this.comment.label).html(d).toggle(d != "")
            }
        },
        setValidationStar: function () {
            if (a.hasValidation.apply(this) == true) {
                if (this.star == null) {
                    var d = this;
                    this.star = b('<span class="op-web-survey-validation-star op-web-theme-validation" style="font-weight: normal;">' + b.webSurvey.VALIDATION + "</span>").get(0);
                    b(this.questionTextContainer).after(this.star);
                    b(this).bind("change", function (e) {
                        b(d.star).toggle(d.current != d.fields && !a.hasValidResponse.apply(d))
                    })
                }
            }
            if (this.star != null) {
                var c = this.current != this.fields && a.hasValidation.apply(this) && !a.hasValidResponse.apply(this);
                b(this.star).toggle(c)
            }
        },
        hasValidation: function () {
            return b.questionForm.hasValidation(this.json)
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
            a.setValidationStar.apply(this)
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
    b.ratingForm = function (g, f) {
        var d = b.ObjectPlanet.iPhone ? " op-handheld-rating" : "";
        var e = '<div class="op-web-survey-question-response op-web-theme-question-response rating' + d + '">';
        return e + b.ratingFormHtml(g, f) + "</div>"
    };
    b.ratingFormHtml = function (o, q) {
        var l = o && typeof o.maxRating == "number" ? o.maxRating - 0 : 5;
        var e = (o && o.labels && o.labels.length >= 1 && typeof o.labels[0].def == "string" ? b.ObjectPlanet.spaces(b.trim(o.labels[0].def)) : "Bad");
        var f = (o && o.labels && o.labels.length >= 2 && l <= o.labels.length && typeof o.labels[l - 1].def == "string" ? b.ObjectPlanet.spaces(b.trim(o.labels[l - 1].def)) : "Good");
        var k = (o && o.naLabel && typeof o.naLabel.def == "string" ? b.ObjectPlanet.spaces(b.trim(o.naLabel.def)) : "N/A");
        var g = (o && o.validation && o.validation.required && o.validation.required.message && o.validation.required.message.def != "" ? b.ObjectPlanet.spaces(o.validation.required.message.def) : "");
        var p = "";
        if (b.ObjectPlanet.iPhone == true) {
            p += '<canvas class="op-handheld-rating-range"></canvas>';
            p += '<table class="op-handheld-rating-labels">';
            if (o != null && o.showNA == true) {
                p += '<caption><label><input type="radio" value="na">' + k + "</label></caption>"
            }
            p += '<tr><td class="op-handheld-rating-label op-handheld-rating-label-min">' + e + "</td>";
            p += '<td class="op-handheld-rating-label op-handheld-rating-label-max">' + f + "</td></tr>";
            p += "</table>";
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
            p += '<label class="good textSelect">' + f + "</label>";
            var d = (o != null && o.showNA == true ? "" : ' style="display: none;"');
            p += '<label class="op-web-survey-question-rating-na na hand"' + d + ">";
            p += '<input class="hand" type="' + m + '" name="radio" value="na"' + n + ">";
            p += '<span class="textSelect">' + k + "</span></label>";
            p += '<label class="validation op-web-survey-validation-message op-web-theme-validation" style="display: none;">' + g + "</label>";
            if (q && q.filter == true) {
                p += '<label class="noAnswer break hand"><span style="visibility: hidden;">' + e;
                p += '</span><input class="noAnswer hand" type="checkbox" value="noAnswer" checked="checked">Include empty answers</label>'
            }
            p += "</form>"
        }
        return p
    };
    b.ratingForm.hasValidation = function (d) {
        return d != null && d.validation != null && d.validation.required != null && d.validation.required.on == true
    };
    b.fn.ratingForm = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (f, e) {
            var d = b(this).prop("json", f).get(0);
            if (b.ObjectPlanet.iPhone == true) {
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
            if (b.ObjectPlanet.iPhone == true) {
                this.na = b(this).find("caption input").bind("change", function (g) {
                    b(d.handheldRange).prop("current", 0).phoneRange("paint")
                }).get(0)
            }
            b(this).bind("change", c.hideValidation);
            if (b.ObjectPlanet.iPhone == true) {
                b(this.handheldRange).bind("change", function (g) {
                    if (a.hasValidResponse.apply(d)) {
                        b(d.validation).hide()
                    }
                    b(d.na).removeAttr("checked")
                })
            }
        },
        hasValidation: function () {
            return b.ratingForm.hasValidation(this.json)
        },
        hasValidResponse: function () {
            if (a.hasValidation.apply(this) == true) {
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
            if ((d == undefined || d == true) && a.hasValidation.apply(this) && !a.hasValidResponse.apply(this)) {
                b(this.range).add(this.naContainer).addClass("op-web-theme-validation-marker");
                var e = this.json.validation.required.message && this.json.validation.required.message.def ? this.json.validation.required.message.def : "";
                b(this.validation).html(b.ObjectPlanet.spaces(e)).toggle(e != "")
            }
        },
        getResponse: function () {
            var d = {};
            if (this.handheldRange != null) {
                d.value = b(this.na).is(":checked") ? "na" : b(this.handheldRange).phoneRange("getCurrent")
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
    b.choiceFormHtml = function (k, m) {
        var l = "<form>";
        var g = k != null && k.multipleOn ? "checkbox" : "radio";
        if (m && m.filter == true) {
            g = "checkbox"
        }
        var h = m && m.filter ? ' checked="checked"' : "";
        var e = k != null && k.columns != null && k.columns.value != null ? Math.min(9, Math.max(k.columns.value, 1)) : 1;
        if ((m == null || m.filter != true) && k && k.randomize && k.randomize.on == true && k.options instanceof Array && k.options.length > 1) {
            var j = k.options[k.options.length - 1];
            if (j && j.other && j.other.on == true) {
                k.copy = k.options.slice(0, k.options.length - 1);
                for (var f = 0; f < k.copy.length; f++) {
                    k.copy[f].index = f
                }
                k.copy.shuffle();
                k.copy[k.options.length - 1] = j;
                j.index = k.options.length - 1
            } else {
                k.copy = k.options.slice();
                for (var f = 0; f < k.copy.length; f++) {
                    k.copy[f].index = f
                }
                k.copy.shuffle()
            }
        }
        if (b.ObjectPlanet.iPhone) {
            l += '<table class="options" cellspacing="0" cellpadding="0" style="width: 100%;">'
        }
        if (!b.ObjectPlanet.iPhone) {
            l += '<table class="options" cellspacing="0" cellpadding="0">'
        }
        for (var f = 0; k && k.options && (k.options.length > 1 || (k.options[0] && k.options[0].def != "")) && f < k.options.length; f++) {
            if (f % e == 0 || (m != null && (m.filter == true || b.ObjectPlanet.iPhone == true))) {
                l += "<tr>"
            }
            var d = b.ObjectPlanet.iPhone == true ? '<td style="padding: 0px;">' : "<td>";
            l += d + b.choiceFormOption(k.copy != null ? k.copy : k.options, f, g, m) + "</td>";
            if (f % e == e - 1 || (m != null && (m.filter == true || b.ObjectPlanet.iPhone == true))) {
                l += "</tr>"
            }
        }
        l += "</table>";
        if (m && m.filter == true) {
            l += '<label style="margin-top: 5px;" class="hand"><span><input class="noAnswer hand" type="checkbox" checked="checked"></span>Include empty answers</label>'
        }
        l += '<label class="validation op-web-survey-validation-message op-web-theme-validation" style="display: none;"></label>';
        return l + "</form>"
    };
    b.choiceFormOption = function (p, g, k, q) {
        var e = b.ObjectPlanet.iPhone ? "op-handheld-choice-option " : "";
        var h = e != "" && g == 0 ? "op-handheld-choice-option-first " : "";
        var n = e != "" && p && g == p.length - 1 ? "op-handheld-choice-option-last " : "";
        var f = e != "" && n == "" ? "op-handheld-choice-option-border " : "";
        var j = e != "" ? " noSelect" : " textSelect";
        var o = '<label class="' + e + h + n + f + " op-web-survey-question-choice-option option hand" + j + '">';
        var m = p[g].other && p[g].other.on ? "other" : p[g].def;
        var l = q && q.filter ? ' checked="checked"' : "";
        o += '<span><input index="' + g + '"class="hand option" type="' + k + '" name="radio" value="' + m + '"' + l + "></span>";
        o += b.ObjectPlanet.spaces(p[g].def);
        if (p[g].other && p[g].other.on && (q == null || q.filter != true)) {
            var d = b.ObjectPlanet.iPhone == true ? ' style="margin-left: -14px; margin-top: 3px; width: 100%; display: block;"' : "";
            o += '<input class="other op-web-theme-input" type="text" encoded="true"' + d + ">"
        }
        return o + "</label>"
    };
    b.choiceForm.hasValidation = function (f) {
        var g = f != null && f.options instanceof Array && f.options.length > 0;
        if (g == true && f.options.length == 1) {
            g = f.options[0] != null && f.options[0].def != null && f.options[0].def != ""
        }
        var e = f != null && f.validation && f.validation.min && f.validation.min.value > 0;
        var d = f != null && f.validation && f.validation.max && f.validation.max.value > 0;
        return g && (e == true || d == true)
    };
    b.fn.choiceForm = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (g, e) {
            var d = b(this).prop("json", g).addClass("op-web-survey-question-choice").get(0);
            this.table = b(this).find("table.options").get(0);
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
                if (this.other != null) {
                    this.other.check = b(this.other).find("input[type=radio]").get(0);
                    if (this.other.check == null) {
                        this.other.check = b(this.other).find("input[type=checkbox]").get(0)
                    }
                    this.other.input = b(this.other).find("input[type=text]").get(0);
                    if (e == null || e.filter != true) {
                        b(this.other).bind("click", c.turnOnOther)
                    }
                }
            }
            b(this.choice).bind("click", function (h) {
                if (e && e.filter == true) {
                    return
                }
                if (b(h.target).attr("index") == d.selected) {
                    b(h.target).removeAttr("checked");
                    b(d).removeProp("selected").trigger("change")
                } else {
                    d.selected = b(h.target).attr("index")
                }
            });
            b(this).find("form").bind("submit", function (h) {
                return false
            });
            this.validation = b(this).find("label.validation").get(0);
            if (b.ObjectPlanet.iPhone == true) {
                b(this).find(".op-handheld-choice-option").bind(b.touchstart, c.touchStart).bind(b.touchmove, c.touchMove).bind(b.touchend, c.touchEnd).prop("self", this).find("input[type=radio]").attr("disabled", "disabled").end().find("input[type=checkbox]").attr("disabled", "disabled")
            }
        },
        hasValidation: function () {
            return b.choiceForm.hasValidation(this.json)
        },
        hasValidResponse: function () {
            if (a.hasValidation.apply(this) == false) {
                return true
            }
            if (this.json.validation && this.json.validation.min && this.json.validation.min.value > 0 && b(this).find("input:checked").size() < this.json.validation.min.value) {
                return false
            }
            if (this.json.validation && this.json.validation.max && this.json.validation.max.value > 0 && b(this).find("input:checked").size() > this.json.validation.max.value) {
                return false
            }
            return true
        },
        showValidation: function (d) {
            b(this.table).removeClass("op-web-theme-validation-marker");
            b(this).find("label.option").removeClass("op-web-theme-validation-marker");
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
                if (b.ObjectPlanet.iPhone == true) {
                    b(this).find("label.option").addClass("op-web-theme-validation-marker")
                } else {
                    b(this.table).addClass("op-web-theme-validation-marker")
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
            if (b.ObjectPlanet.iPhone != true) {
                b(this).find("input.other").focus();
                if (d.target == this.self.other.input) {
                    return false
                }
            }
        },
        hideValidation: function () {
            if (a.hasValidResponse.apply(this.self)) {
                b(this.self.validation).hide();
                b(this.self.table).removeClass("op-web-theme-validation-marker")
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
            if (d.touchMove != null) {
                delete d.touchMove
            }
            b(d).trigger("change")
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
    b.dropdownForm.hasValidation = function (e) {
        var f = e != null && e.options instanceof Array && e.options.length > 0;
        var d = e != null && e.validation != null && e.validation.required != null && e.validation.required.on == true;
        return f == true && d == true
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
        hasValidation: function () {
            return b.dropdownForm.hasValidation(this.json)
        },
        hasValidResponse: function () {
            var d = a.hasValidation.apply(this);
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
    b.fieldsRow = function (g, f, j) {
        var e = g && g.label && typeof g.label.def == "string" ? b.ObjectPlanet.spaces(g.label.def) : "";
        var d = '<tr class="op-web-survey-question-field">';
        var k = j != true ? ' style="padding-right: 0px;"' : "";
        d += '<td class="label"' + k + ">";
        if (f && f.filter == true) {
            d += '<input class="field" type="checkbox" checked="checked">'
        }
        d += '<span class="label textSelect">' + e + '</span></td><td class="input">';
        var i = g && g.width != null && (f == null || f.filter != true) ? ' style="width: ' + g.width + ';"' : "";
        if (b.ObjectPlanet.iPhone == true) {
            d += '<input class="op-web-theme-input op-web-survey-borderbox" type="text" encoded="true" style="display: block; width: 100%; ">'
        } else {
            if (g != null && g.rows > 1 && (f == null || f.filter != true)) {
                d += '<textarea class="op-web-theme-input op-web-survey-borderbox" type="text" encoded="true" rows="' + g.rows + '"' + i + "></textarea>"
            } else {
                d += '<input class="op-web-theme-input op-web-survey-borderbox" type="text" encoded="true"' + i + ">"
            }
        } if (f && f.filter == true) {
            d += '<canvas class="not notInactive hand" style="width: 16px; height: 16px;"></canvas>'
        }
        var h = b.ObjectPlanet.iPhone ? "display: none; position: absolute; top: 0.45em; font-weight: normal; " : "display: none; font-weight: normal; ";
        d += '<span class="op-web-survey-validation-star op-web-theme-validation" style="' + h + '">' + b.webSurvey.VALIDATION + "</span>";
        d += '<label class="validation op-web-survey-validation-message op-web-theme-validation" style="display: none;"></label>';
        return d + "</td></tr>"
    };
    b.fieldsForm.hasValidation = function (e) {
        for (var d = 0; e != null && d < e.length; d++) {
            if (e[d] != null && e[d].validation && e[d].validation.required && e[d].validation.required.on == true) {
                return true
            }
        }
        return false
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
            e.star = b(e).find("span.op-web-survey-validation-star").get(0);
            e.validation = b(e).find("label.validation").get(0);
            e.label = b(e).find("td.label span").get(0);
            e.label.td = b(e).find("td.label").get(0);
            if (d != null && d.rows > 1 && b(this).find("textarea").size() > 0) {
                e.input = b(e).find("textarea").autoSizeTextArea({
                    horizontal: false,
                    minHeight: true
                }).get(0)
            } else {
                e.input = b(e).find("input[type=text]").get(0)
            }
            e.filter = b(e).find("input[type=checkbox]").get(0);
            e.input.td = b(e).find("td.input").get(0);
            b(e.star).toggle(e.json != null && e.json.validation != null && e.json.validation.required != null && e.json.validation.required.on == true);
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
            b(e.input).bind("keydown keypress keyup", c.ensureNumbersOnly).bind("keyup change", c.hideValidation).prop("field", e);
            return e
        },
        adjust: function () {
            if (b(this).is(":visible")) {
                for (var e = 0, f = false; e < this.fields.length && f == false; e++) {
                    if (b(this.fields[e].label).text() != "") {
                        f = true
                    }
                }
                if (f == false) {
                    b(this.fields).find(".label").css("padding-right", "0px")
                }
                if (f != false) {
                    b(this.fields).find(".label").css("padding-right", "")
                }
                if (this.parentWidth == null) {
                    this.parentWidth = b(this).closest(".web-survey").get(0)
                }
                var k = b(this.parentWidth).width() - b(this).outerWidth() + b(this).width();
                for (var e = 0, j = k / 2; e < this.fields.length; e++) {
                    var d = b.ObjectPlanet.text.width(b(this.fields[e].label).text(), this);
                    if (d > j) {
                        b(this.fields[e].label.td).css("white-space", "normal").width(j)
                    }
                    if (d <= j) {
                        b(this.fields[e].label.td).css("white-space", "").width("")
                    }
                }
                for (var e = 0, h = k; e < this.fields.length; e++) {
                    var g = b(this.fields[e].star).is(":visible") ? (b(this.fields[e].star).width() + 6) : 0;
                    h = Math.min(h, k - b(this.fields[e].label).width() - g)
                }
                for (var e = 0; e < this.fields.length; e++) {
                    b(this.fields[e].input).css("max-width", h + "px")
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
        hasValidation: function () {
            return b.fieldsForm.hasValidation(this.json)
        },
        hasFieldValidResponse: function (d) {
            return a._hasFieldValidResponse(this.json[d], b(this.fields[d].input).val())
        },
        _hasFieldValidResponse: function (e, f) {
            var d = true;
            if (e != null && e.validation != null) {
                if (e.validation.required && e.validation.required.on == true) {
                    d = d && f != ""
                }
                if ((e.type == "integer" || e.type == "decimal") && f != "") {
                    if (e.validation.min && e.validation.min.value && e.validation.min.value != "") {
                        d = d && (f - 0) >= e.validation.min.value
                    }
                    if (e.validation.max && e.validation.max.value && e.validation.max.value != "") {
                        d = d && (f - 0) <= e.validation.max.value
                    }
                } else {
                    if (e.type == "email" && f != "") {
                        d = d && b.ObjectPlanet.isValidEmail(f)
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
        hideValidation: function (d) {
            b(this.field.validation).hide();
            b(this).removeClass("op-web-theme-validation-marker");
            b(this.field.star).toggle(!a._hasFieldValidResponse(this.field.json, b(this).val()))
        }
    }
})(jQuery);
(function (b) {
    b.fn.excludeIcon = function () {
        return this.opWidget(a, arguments)
    };
    var a = {
        init: function (c) {
            b(this).addClass("op-icon").excludeIcon("paint")
        },
        paint: function () {
            if (this.getContext == null) {
                return
            }
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
        var e = (g && g.label && typeof g.label.def == "string" ? b.ObjectPlanet.spaces(g.label.def).replace(/\n/g, "<br>") : "");
        var h = e == "" ? ' style="display: none;"' : "";
        d += '<label class="textSelect' + c + '"' + h + ">";
        if (f && f.filter == true) {
            d += '<input class="hand" type="checkbox" checked="checked">'
        }
        if (f && f.filter == true) {
            e = "Include free-text comment"
        }
        d += e;
        d += "</label>";
        var i = f && f.filter == true ? ' style="display: none;"' : "";
        d += '<textarea class="op-web-survey-borderbox op-web-theme-input" encoded="true"' + i + "></textarea>";
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