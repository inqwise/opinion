var SelectText, ajaxLoadingLeft, ajaxObjects, baseChartColor, baseUrl, blockToHl, caseExit, chartColorsComp, chartColorsPie, chartColorsPie2, chartsData, chartsId, chartsObject, checkCompetitorsPhase, checkLocalCurrency, compCriteriums, competitorToCheck, competitorUrl, competitorUrls, convertSerializedArrayToHash, criteriumsLoading, critsOnSocket, currentCompetitorNbr, currentScoring, currentWebsite, desktopScreenshot, extractLast, faqSuggestUrl, fetchedData, fetchs, fetchsData, finalScoring, getParameterByName, hashDiff, i, isExtension, isInsideReview, isMobile, isPaidReview, jsLoadEnd, keySuggestUrl, lcDetailsLoad, maxTop, mustacheCompTemplate, mustacheCritTemplate, postBackUrl, projectDataUrl, puChanel, pusher, radius, re_weburl, reportId, reportSaveUrl, savingToken, scrollTo, shortUrl, socialMediaCat, split, stripAccent, strokeSize, todoAutoCheckUrl, todoSaveUrl, triggerTime, urlToFetch, validateURL, whlBody, whlHighlighted, whlOverlay, whlParam, widthHeight, wooBubble, wooCounter, wooFlyingMenu, wooFormBackup, wooLang, wooLangTmp, wooLcCounters;
wooCounter = null, wooLangTmp = $("body").attr("lang"), wooLang = void 0 === wooLangTmp || "" === wooLangTmp ? "en" : wooLangTmp, wooFlyingMenu = null, wooBubble = null, maxTop = null, radius = 44, widthHeight = 96, strokeSize = 8, currentScoring = 0, finalScoring = 0, isPaidReview = !1, isInsideReview = !1, isExtension = !1, isMobile = !1, desktopScreenshot = "", chartsId = 0, chartsData = [], chartsObject = [], baseChartColor = "#315d86", chartColorsPie = [baseChartColor, "#ddd", "#aaa", "#999"], chartColorsPie2 = [baseChartColor, "#6a93ba", "#ddd", "#aaa"], chartColorsComp = ["#b1e5e6", "#569596", "#2a494a", "#888888"], criteriumsLoading = [], currentWebsite = null, ajaxLoadingLeft = 0, savingToken = null, critsOnSocket = [], checkCompetitorsPhase = !1, competitorUrls = [], compCriteriums = [], currentCompetitorNbr = 0, reportId = 0, urlToFetch = {}, fetchedData = {}, pusher = null, puChanel = !1, wooFormBackup = null, wooLcCounters = [], mustacheCritTemplate = "", mustacheCompTemplate = "", socialMediaCat = [], baseUrl = "/" + wooLang + "/", projectDataUrl = baseUrl + "project/data", keySuggestUrl = baseUrl + "project/ajaxkeywordssuggest", lcDetailsLoad = baseUrl + "project/lcdetails", todoAutoCheckUrl = baseUrl + "todo/todoac", todoSaveUrl = baseUrl + "todo/save", faqSuggestUrl = baseUrl + "faqs/ajaxsearch", reportSaveUrl = baseUrl + "report/save", shortUrl = baseUrl + "report/shorturl", postBackUrl = "/postback.php", fetchs = [], fetchsData = [], socialMediaCat = [], ajaxObjects = null, competitorUrl = null, competitorToCheck = !1, stripAccent = function (t) {
    var e, n, o;
    for (o = [{
        re: /[\xC0-\xC6]/g,
        ch: "A"
    }, {
        re: /[\xE0-\xE6]/g,
        ch: "a"
    }, {
        re: /[\xC8-\xCB]/g,
        ch: "E"
    }, {
        re: /[\xE8-\xEB]/g,
        ch: "e"
    }, {
        re: /[\xCC-\xCF]/g,
        ch: "I"
    }, {
        re: /[\xEC-\xEF]/g,
        ch: "i"
    }, {
        re: /[\xD2-\xD6]/g,
        ch: "O"
    }, {
        re: /[\xF2-\xF6]/g,
        ch: "o"
    }, {
        re: /[\xD9-\xDC]/g,
        ch: "U"
    }, {
        re: /[\xF9-\xFC]/g,
        ch: "u"
    }, {
        re: /[\xD1]/g,
        ch: "N"
    }, {
        re: /[\xF1]/g,
        ch: "n"
    }], e = 0, n = o.length; n > e;) t = t.replace(o[e].re, o[e].ch), e++;
    return t
}, split = function (t) {
    return t.split(/,\s*/)
}, extractLast = function (t) {
    return split(t).pop()
}, convertSerializedArrayToHash = function (t) {
    var e, n;
    for (n = {}, e = 0; t.length > e;) n[t[e].name] = t[e].value, e++;
    return n
}, hashDiff = function (t, e) {
    var n, o;
    n = "";
    for (o in t) t[o] === e[o] ? delete e[o] : 1 === t[o] && (e[o] = "0");
    for (o in e) e[o] && (n += "&" + o + "=" + encodeURIComponent(e[o]));
    return n
}, getParameterByName = function (t) {
    var e, n, o;
    return t = t.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]"), n = "[\\?&]" + t + "=([^&#]*)", e = RegExp(n), o = e.exec(window.location.search), null == o ? "" : decodeURIComponent(o[1].replace(/\+/g, " "))
}, scrollTo = function (t, e) {
    var n, o;
    return o = $(t).offset().top, n = $("html,body"), $.browser.safari && (n = $("body")), n.stop().animate({
        scrollTop: o
    }, e)
}, window.scrollToObj = function (t) {
    return scrollTo(t, 500)
}, SelectText = function (t) {
    var e, n, o, i;
    return e = document, i = e.getElementById(t), e.body.createTextRange ? (n = document.body.createTextRange(), n.moveToElementText(i), n.select()) : window.getSelection ? (o = window.getSelection(), n = document.createRange(), n.selectNodeContents(i), o.removeAllRanges(), o.addRange(n)) : void 0
}, re_weburl = RegExp("^(?:(?:https?|ftp)://)?(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?$", "i"), validateURL = function (t) {
    return re_weburl.test(t)
}, $("#login-container").on("click", function (t) {
    var e;
    return e = $(this), e.hasClass("open") ? void 0 : (t.preventDefault(), e.addClass("open").find(">div").animate({
        width: "422"
    }, 400), e.find("input").each(function () {
        var t;
        return t = $(this), "" !== t.val() ? t.removeClass("label-default").prev("label").addClass("hidden") : void 0
    }))
}), $("#lang-container>span, #logged-container>span").on("click htmlClick", function (t) {
    var e;
    return e = $(this).parent(), t.preventDefault(), t.stopPropagation(), $("html").off("click"), e.hasClass("open") ? e.find("nav").slideUp(400, function () {
        return e.removeClass("open")
    }) : (e.addClass("open").find("nav").slideDown(400), $("html").on("click", function (t) {
        return e.find(">span").trigger("htmlClick"), t.preventDefault(), t.stopPropagation()
    }))
}), $("#lang-container nav a, #logged-container nav a").click(function (t) {
    return t.stopPropagation()
}), $(".input-label").on("focus", function () {
    return $(this).hasClass("label-default") ? $(this).attr("value", "").removeClass("label-default").prev(".over-input-label").fadeOut(100) : void 0
}), $(".input-label").on("focusout", function () {
    return 0 === $(this).attr("value").length || 3 > $(this).attr("value").length && "password" !== $(this).attr("type") ? $(this).attr("value", "").addClass("label-default").prev(".over-input-label").fadeIn(100) : void 0
}), $("form.to-validate").on("submit", function (t) {
    var e, n, o;
    return n = $(this).removeClass("error"), e = n.find("input.required").removeClass("error"), o = !0, e.each(function () {
        var t;
        return t = $(this), "" === t.val() ? (n.addClass("error"), t.addClass("error"), o = !1) : void 0
    }), o ? void 0 : t.preventDefault()
}), $("#social-sharing-box>a").on("click", function () {
    var t, e, n;
    return n = $(this), e = n.parent().attr("class"), t = n.attr("class"), _gaq.push(["_trackSocial", t, e])
}), $(".show-links a").live("click", function (t) {
    var e;
    return t.preventDefault(), e = $(this).parent(".show-links").toggleClass("open").prev("table, ol, ul, .max5").find(".over-max:not(.task-content), .over-max.ui-accordion-content-active"), e.length > 20 ? e.toggle() : e.slideToggle(200)
}), $(".woo-tooltip, .simple-tooltip").tooltip({
    delay: {
        show: 400,
        hide: 100
    }
}).on("click", function (t) {
    return "#" === $(this).attr("href") ? t.preventDefault() : void 0
}), $(".woo-popover").popover({
    delay: {
        show: 400,
        hide: 100
    },
    trigger: "hover",
    content: function () {
        return $(this).next(".popover-data").html()
    }
}, whlParam = getParameterByName("whl"), function () {
    if (whlParam) {
        switch (whlBody = $("body"), whlHighlighted = [], triggerTime = 2e3, caseExit = !1, whlParam) {
        case "competitors":
            caseExit = !0, blockToHl = $("#generate-report"), whlHighlighted.push(blockToHl), setTimeout(function () {
                return blockToHl.find(".icon-plus").trigger("click")
            }, triggerTime);
            break;
        case "notification":
            caseExit = !0, blockToHl = $("#email-notification"), whlHighlighted.push(blockToHl), whlHighlighted.push($("#email_settings")), setTimeout(function () {
                return scrollTo(blockToHl)
            }, triggerTime);
            break;
        case "start-project":
            caseExit = !0, whlHighlighted.push($("#report-priorities .convert")), whlHighlighted.push($("#flying #project")), whlHighlighted.push($("#flying #todos")), whlHighlighted.push($("#flying #settings"));
            break;
        case "star":
            caseExit = !0, blockToHl = $("#reports-list .entry:first-child"), whlHighlighted.push(blockToHl), setTimeout(function () {
                return blockToHl.find("i.icon-star-empty").tooltip("show")
            }, triggerTime)
        }
        if (caseExit) {
            whlBody.addClass("overlayed").append('<div id="whl-overlay"></div>'), whlOverlay = whlBody.find("#whl-overlay");
            for (i in whlHighlighted) whlHighlighted[i].addClass("whl-highlighted");
            return whlOverlay.on("click", function () {
                return whlBody.removeClass("overlayed"), whlOverlay.fadeOut(150, function () {
                    for (i in whlHighlighted) whlHighlighted[i].removeClass("whl-highlighted");
                    return whlOverlay.off("click").remove()
                })
            })
        }
    }
}()), jsLoadEnd = function () {
    return $(window).trigger("scroll"), $(".hide-on-load").removeClass("hide-on-load"), checkLocalCurrency()
}, $(function () {
    return jsLoadEnd()
}), checkLocalCurrency = function () {}, "u" === $("body").attr("lc") && $.ajax({
    url: "/lc-check.php",
    async: !0,
    dataType: "json",
    success: function (t) {
        var e, n;
        return t.updPrices ? (e = $("body").hasClass("home"), n = "/en/register/priceslist", e && (n = "/en/home/priceslist"), $.ajax({
            url: n,
            async: !0,
            dataType: "json",
            success: function (t) {
                var n;
                return n = e ? $("#multiuser-table, #pricing-table").find(".price .orange") : $("#translations-for-form p.price, #translations-for-form .due-title span"), n.each(function () {
                    var n, o, i, a;
                    return a = $(this), n = t[a.text()], n && "" !== n ? (e && (i = a.parent(), o = i.text(), i.after('<p class="subprice">' + o + "</p>")), a.text(n)) : void 0
                })
            }
        })) : void 0
    }
});






var calculTopBars, calculTopPriorities, checkCompetitorCharts, checkEmptyModule, checkWooChart, cleanWooCharts, currentText, days2Sub, formatMomentJs, getColorCode, getCompetitorsScore, getCountryCode, getRelatedScore, getScoreIn, getURLParameter, initCompetitorCharts, initTopBarTrial, initWooCounter, localizations, months2Sub, newString, setInsideRank, splitted, twitterAge, updateAllWooHighChartsHeight, updateWooCounter, updateWooHighCharts, usePiecon, wooGeoCharts, wooHighCharts, wooMap, wooMaps, years2Sub, _checkWooChart;
usePiecon = !0, initWooCounter = function () {
    var t, e, n, a;
    return t = Raphael("holder", widthHeight, widthHeight), e = {
        stroke: "#fff",
        "stroke-width": strokeSize
    }, n = $("#score-value"), t.customAttributes.arc = function (t, e) {
        var a, i, o, r, s, l, c, d;
        return s = void 0, i = 360 / e * t, a = (90 - i) * Math.PI / 180, c = widthHeight / 2 + radius * Math.cos(a), d = widthHeight / 2 - radius * Math.sin(a), r = "hsb(".concat(.0444 + .2361 * (t / e), ", 1 , .75)"), o = "" + Math.round(t) / 10, l = o.split("."), l[1] ? n.html(l[0] + "<span class='decimal'>." + l[1] + "</span>") : n.html(l[0]), s = e === t ? [
            ["M", widthHeight / 2, widthHeight / 2 - radius],
            ["A", radius, radius, 0, 1, 1, widthHeight / 2, widthHeight / 2 - radius]
        ] : [
            ["M", widthHeight / 2, widthHeight / 2 - radius],
            ["A", radius, radius, 0, +(i > 180), 1, c, d]
        ], {
            path: s,
            stroke: r
        }
    }, a = n.text(), window.wooCounter = a ? t.path().attr(e).attr({
        arc: [10 * a, 1e3, radius]
    }).translate(1, 0) : t.path().attr(e).attr({
        arc: [1e3, 1e3, radius]
    }).translate(1, 0), usePiecon ? (Piecon.setOptions({
        color: "#315D86",
        background: "#ddd",
        shadow: "#fff",
        fallback: !1
    }), Piecon.setProgress(Math.round(a))) : void 0
}, setInsideRank = function (t) {
    var e, n;
    return isInsideReview ? (e = "e", t >= 70 ? e = "aplus" : t >= 60 ? e = "a" : t >= 50 ? e = "b" : t >= 40 ? e = "c" : t >= 30 && (e = "d"), n = e.replace("plus", '<span class="plus">+</span>'), $("#score-inside").addClass("rank-" + e).find("#rank-value").html(n)) : void 0
}, updateWooCounter = function () {
    return isInsideReview ? void 0 : $("span.crit-score-pond").each(function () {
        var t;
        return t = parseFloat($(this).text()), currentScoring += 10 * t, wooCounter.animate({
            arc: [currentScoring, 1e3, radius]
        }, 1e3, "<>"), $(this).remove(), usePiecon ? Piecon.setProgress(Math.round(currentScoring / 10)) : void 0
    })
}, checkEmptyModule = function () {
    return $("#report-content>.module.hidden, #right-panel:not(.light-report) #report-content>.module.module-empty").remove(), $("#report-content>.module:not(.hidden)").each(function () {
        var t, e;
        return e = $(this), t = e.children(".teasing-box, .criterium:not(.hidden)"), 1 === e.children().length || 0 === t.length ? (e.remove(), $("#review-navigation>a[href=#" + e.attr("id") + "]").remove()) : void 0
    }), $("#review-navigation>a:not(.hidden)").each(function () {
        var t, e;
        return e = $(this), t = $(e.attr("href")), 0 === t.length || t.hasClass("hidden") || t.hasClass("module-empty") ? e.remove() : void 0
    })
}, getURLParameter = function (t) {
    var e, n;
    return n = decodeURI(RegExp(t + "=" + "(.+?)(&|$)").exec(location.search)), n || (n = ","), e = n.split(","), e[1]
}, checkWooChart = function (t) {
    var e, n;
    return cleanWooCharts(), t ? n = ".criterium-competitor-container .chartify-me" : $("body").hasClass("review") || $("body").hasClass("extension") || $("body").hasClass("mobreview") || $("body").hasClass("pdf") ? (e = !0, n = ".criterium-content .chartify-me") : n = ".active .active>.chartify-me,      .active .active tr td>.chartify-me,      #keywords-table .chartify-me,      .graphics-container>.chartify-me,      .ga-data-table .chartify-me,      .graphics-container .tab-pane.active>.chartify-me,      .debug-chart-container .chartify-me", $(n).each(function () {
        return _checkWooChart($(this))
    })
}, _checkWooChart = function (t) {
    var e, n, a, i, o, r, s, l, c, d, u, p, h, f, v, m, g, b;
    if (r = [], i = 0, o = "unknow", e = "generate-chart-id-" + chartsId, n = null, a = null, f = 99999999, p = 0, t.parents("#criterium-top_ranking_keywords").length > 0) return t.remove(), void 0;
    if (chartsId++, t.attr("id", e).removeClass("chartify-me"), t.hasClass("pie-chart-container") || t.hasClass("big-pie-chart-container") || t.hasClass("bar-chart-container") || t.hasClass("line-chart-container") || t.hasClass("trend-chart-container") || t.hasClass("revert-trend-chart-container") || t.hasClass("littletrend-chart-container") || t.hasClass("revert-littletrend-chart-container") || t.hasClass("comparative-comp-chart-container") || t.hasClass("nbar-comp-chart-container") || t.hasClass("bar-comp-chart-container") || t.hasClass("genderage-chart-container") || t.hasClass("column-chart-container")) t.find(".value").each(function () {
        var t, e, n, o, s;
        return s = $(this), e = s.attr("title"), n = s.attr("stack"), o = !1, t = [], s.find("li").each(function () {
            var e, n, r, s, l, c, d;
            return l = $(this), c = "null" === l.text() ? null : parseFloat(l.text()), r = l.attr("class"), s = !1, o = o ? o : c, null == a && (0 > c ? a = !1 : 10 > c ? a = 0 : null != c && (f > c && (f = c), c > p && (p = c))), void 0 !== r && r.match("date-") && (r = r.replace("date-", ""), d = r.slice(0, 4), n = r.slice(4, 6), e = 8 === r.length ? r.slice(6, 8) : 0, s = Date.UTC(d, n - 1, e)), s ? t.push([s, c]) : t.push(c), i += null != c ? c : 0
        }), s.hasClass("stacked") ? r.push({
            name: e,
            data: t,
            stack: n
        }) : r.push({
            name: e,
            data: t,
            y: o
        })
    }), n = t.find(".max").text(), null == a && (a = p / f > 21 ? 0 : !1);
    else if (t.hasClass("geo-chart-container")) v = parseInt(t.find(".nbr-rows").text()), u = t.find("tr:first-child th").first().text(), d = t.find("tr:nth-child(2) th").first().text(), o = "world", i = v, r = new google.visualization.DataTable, r.addRows(v), r.addColumn("string", u), r.addColumn("number", d), t.find("table").each(function () {
        var t;
        return t = $(this), r.setValue(parseInt(t.find("tr:first-child td:nth-child(2)").text()), parseInt(t.find("tr:first-child td:nth-child(3)").text()), t.find("tr:first-child td:nth-child(4)").text()), r.setValue(parseInt(t.find("tr:nth-child(2) td:nth-child(2)").text()), parseInt(t.find("tr:nth-child(2) td:nth-child(3)").text()), parseFloat(t.find("tr:nth-child(2) td:nth-child(4)").text()))
    });
    else {
        if (!t.hasClass("absolute-geo-chart-container")) return t.remove(), void 0;
        v = parseInt(t.find(".nbr-rows").text()), u = t.find("tr:first-child th").first().text(), d = t.find("tr:nth-child(2) th").first().text(), o = t.find(".region").text(), i = v, r = new google.visualization.DataTable, r.addRows(v), r.addColumn("string", u), r.addColumn("number", d), s = 0, t.find("table").each(function () {
            var t;
            return t = $(this), r.setValue(s, 0, t.find(".name").text()), r.setValue(s, 1, parseInt(t.find(".val").text())), s++
        })
    } if (chartsData[chartsId] = r, t.hasClass("pie-chart-container")) o = "pie", n = i;
    else if (t.hasClass("big-pie-chart-container")) o = "bpie", n = i;
    else if (t.hasClass("bar-chart-container")) o = "bar";
    else if (t.hasClass("line-chart-container")) o = "spline";
    else if (t.hasClass("trend-chart-container")) o = "trend";
    else if (t.hasClass("genderage-chart-container")) o = "genderage";
    else if (t.hasClass("revert-trend-chart-container")) {
        for (s in r) {
            r[s].y = 1e3 / r[s].y;
            for (c in r[s].data) null != r[s].data[c] && (r[s].data[c] = 1e3 / r[s].data[c])
        }
        o = "trend"
    } else if (t.hasClass("littletrend-chart-container")) o = "ltrend";
    else if (t.hasClass("revert-littletrend-chart-container")) {
        for (s in r) {
            r[s].y = 1e3 / r[s].y;
            for (c in r[s].data) r[s].data[c] = 1e3 / r[s].data[c]
        }
        o = "ltrend"
    } else {
        if (t.hasClass("geo-chart-container") || t.hasClass("absolute-geo-chart-container")) return wooGeoCharts(e, r, o, t), void 0;
        if (t.hasClass("comparative-comp-chart-container")) o = "comparative";
        else if (t.hasClass("bar-comp-chart-container")) o = "bar";
        else if (t.hasClass("nbar-comp-chart-container")) {
            for (s in r)
                for (c in r[s].data) r[s].data[c] = 1 / r[s].data[c];
            o = "nbar"
        } else t.hasClass("column-chart-container") && (o = "column")
    } if (t.hasClass("comparative-comp-chart-container") || t.hasClass("bar-comp-chart-container") || t.hasClass("nbar-comp-chart-container")) {
        if (b = t.parents(".criterium-competitor-container").find(".main-chart-container"), b.length > 0) return updateWooHighCharts("add", b, r), t.remove(), void 0;
        t.addClass("main-chart-container")
    }
    return "pie" !== o || 0 !== i ? (m = t.hasClass("chart-no-decimal"), h = parseFloat(t.attr("data-min-range")), t.hasClass("percent-chart") && (n = 100.9), g = !1, t.hasClass("special-small-chart") && (g = !0), l = !1, t.closest("#generate-chart-id-0").length > 0 && (l = !0), wooHighCharts(o, e, r, n, a, m, h, g, l)) : void 0
}, wooGeoCharts = function (t, e, n, a) {
    var i, o, r, s;
    return s = 490, r = 300, a.hasClass("absolute-geo-chart-container") && (s = 680, r = 305), i = {
        width: s,
        height: r,
        region: n,
        datalessRegionColor: "#dddddd",
        colorAxis: {
            colors: ["#999999", "#315d86"]
        },
        enableRegionInteractivity: !1,
        legend: {
            textStyle: {
                color: "#333333",
                fontSize: 11,
                fontName: "Open Sans"
            }
        }
    }, o = new google.visualization.GeoChart(document.getElementById(t)), o.draw(e, i)
}, wooHighCharts = function (t, e, n, a, i, o, r, s, l) {
    var c, d, u, p;
    switch (t) {
    case "pie":
        return chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                defaultSeriesType: t,
                height: 100
            },
            colors: null != l ? window.chartColorsPie : window.chartColorsPie2,
            legend: {
                align: "left",
                verticalAlign: "top",
                layout: "vertical",
                margin: 0,
                floating: !0,
                x: 108,
                y: -17,
                labelFormatter: function () {
                    return this.name + "  ( " + this.y + "% )"
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: !1,
                    cursor: "default",
                    shadow: !1,
                    borderColor: null,
                    showInLegend: !0,
                    center: [38, 38],
                    size: 96,
                    dataLabels: {
                        enabled: !1
                    }
                }
            },
            series: [{
                type: t,
                data: n
            }]
        });
    case "bpie":
        return chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                defaultSeriesType: "pie",
                height: 300
            },
            plotOptions: {
                pie: {
                    shadow: !1,
                    borderColor: null,
                    showInLegend: !0,
                    dataLabels: {
                        enabled: !1
                    }
                }
            },
            series: [{
                type: "pie",
                data: n
            }]
        });
    case "nbar":
        return (!a || "" === a || 50 > a) && (a = null), c = 90 + 30 * n.length, chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                defaultSeriesType: "bar",
                height: c
            },
            xAxis: {
                title: {
                    text: null
                },
                labels: {
                    enabled: !1
                },
                minPadding: .05,
                maxPadding: .05,
                lineWidth: 0,
                tickWidth: 0
            },
            yAxis: {
                title: {
                    text: null
                },
                min: 0,
                max: a,
                opposite: !0,
                endOnTick: !1,
                maxPadding: .02,
                labels: {
                    overflow: "justify",
                    formatter: function () {
                        return 0 === this.value ? "∞" : Math.round(1 / this.value)
                    }
                }
            },
            tooltip: {
                formatter: function () {
                    return this.y > 1 ? "" + this.series.name + ": " + this.y : "" + this.series.name + ": " + 1 / this.y
                }
            },
            legend: {
                borderWidth: null
            },
            plotOptions: {
                bar: {
                    groupPadding: 0,
                    borderColor: null,
                    id: e,
                    shadow: !1,
                    dataLabels: {
                        enabled: !0,
                        color: "#fff",
                        align: "right",
                        x: -12,
                        overflow: "justify",
                        formatter: function () {
                            return this.y > 1 ? this.y : 1 / this.y + "th"
                        }
                    }
                }
            },
            series: n
        });
    case "bar":
        if (!a || "" === a || 50 > a) a = null;
        else if (100 === a)
            for (d in n) 6 > n[d].y && n.splice(d, 1);
        return c = 90 + 30 * n.length, chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                defaultSeriesType: t,
                height: c,
                spacingTop: 0
            },
            xAxis: {
                title: {
                    text: null
                },
                labels: {
                    enabled: !1
                },
                minPadding: .05,
                maxPadding: .05,
                lineWidth: 0,
                tickWidth: 0
            },
            yAxis: {
                title: {
                    text: null
                },
                min: 0,
                max: a,
                opposite: !0,
                endOnTick: !1,
                maxPadding: .02,
                labels: {
                    overflow: "justify",
                    enabled: !1
                }
            },
            legend: {
                borderWidth: null,
                reversed: !0
            },
            plotOptions: {
                bar: {
                    groupPadding: 0,
                    borderColor: null,
                    id: e,
                    shadow: !1,
                    pointWidth: 24,
                    dataLabels: {
                        enabled: !0,
                        color: "#fff",
                        align: "right",
                        x: -10,
                        overflow: "justify",
                        formatter: function () {
                            var t;
                            return t = this.y, 100 === a && (t += "%"), t
                        }
                    }
                }
            },
            series: n
        });
    case "comparative":
        return chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                defaultSeriesType: "bar",
                height: 700
            },
            colors: window.chartColorsComp,
            xAxis: {
                title: {
                    text: null
                },
                minPadding: .05,
                maxPadding: .05,
                lineWidth: 0,
                tickWidth: 0,
                categories: socialMediaCat,
                labels: {
                    formatter: function () {
                        switch (this.value) {
                        case "fb_likes":
                            return '<img src="/assets/img/css/facebook_l.png" />&nbsp;';
                        case "fb_shares":
                            return '<img src="/assets/img/css/facebook_s.png" />&nbsp;';
                        case "fb_comm":
                            return '<img src="/assets/img/css/facebook_c.png" />&nbsp;';
                        case "tw_bl":
                            return '<img src="/assets/img/css/twitter_b.png" />&nbsp;';
                        case "li_shares":
                            return '<img src="/assets/img/css/linkedin_s.png" />&nbsp;';
                        case "digg":
                            return '<img src="/assets/img/css/digg_e.png" />&nbsp;';
                        case "delicious":
                            return '<img src="/assets/img/css/delicious_b.png" />&nbsp;';
                        case "stumbled_upon":
                            return '<img src="/assets/img/css/stumbleupon.png" />&nbsp;';
                        case "plus_one":
                            return '<img src="/assets/img/css/google_p.png" />&nbsp;';
                        default:
                            return this.value
                        }
                    },
                    useHTML: !0
                }
            },
            yAxis: {
                title: {
                    text: null
                },
                min: 0,
                opposite: !0,
                endOnTick: !1,
                maxPadding: .02,
                max: 230,
                min: -120,
                gridLineColor: null,
                plotLines: [{
                    color: "#A0C5E5",
                    width: 1,
                    value: 0
                }],
                labels: {
                    formatter: function () {
                        return 0 === this.value ? "" + window.currentWebsite : ""
                    },
                    style: {
                        fontSize: "11px",
                        color: window.baseChartColor
                    }
                }
            },
            legend: {
                borderWidth: null
            },
            plotOptions: {
                bar: {
                    animation: !1,
                    borderColor: null,
                    id: e,
                    shadow: !1,
                    dataLabels: {
                        enabled: !0,
                        overflow: "justify",
                        formatter: function () {
                            var t;
                            return t = parseInt(this.y), isNaN(t) || 0 === t ? "" : 200 === t ? ">" + this.y + "%" : t > 0 ? "+" + this.y + "%" : t + "%"
                        }
                    }
                }
            }
        });
    case "spline":
        return i === !1 && (i = null), chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                defaultSeriesType: t,
                height: s === void 0 || s === !1 ? 325 : 160,
                marginRight: 0,
                marginLeft: 36
            },
            xAxis: {
                type: "datetime",
                dateTimeLabelFormats: {
                    day: "%e %b"
                },
                lineColor: null,
                lineWidth: 0,
                tickWidth: 0,
                minPadding: .05,
                maxPadding: .05
            },
            yAxis: {
                alternateGridColor: "#f7f7f7",
                lineColor: null,
                lineWidth: 0,
                minPadding: .05,
                maxPadding: .1,
                minRange: r !== void 0 ? r : .01,
                endOnTick: !1,
                min: i,
                max: "" === a ? null : a,
                allowDecimals: o !== void 0 ? !o : !1,
                labels: {
                    formatter: function () {
                        var t;
                        return t = this.value, 100.9 === a && (t += "%"), t >= 1e3 && 1e6 > t ? (t = Math.floor(t / 100) / 10, t += "k") : t >= 1e6 && (t = Math.floor(t / 1e5) / 10, t += "M"), t
                    }
                }
            },
            legend: {
                enabled: n.length > 1,
                align: "left",
                verticalAlign: "top",
                margin: 0,
                floating: !0,
                x: 36
            },
            tooltip: {
                enabled: !0
            },
            plotOptions: {
                spline: {
                    groupPadding: 0,
                    borderColor: null,
                    stickyTracking: !1,
                    marker: {
                        enabled: !0,
                        symbol: "circle",
                        radius: 2,
                        states: {
                            hover: {
                                radius: 3
                            }
                        }
                    },
                    shadow: !1,
                    connectNulls: !0
                }
            },
            series: n
        });
    case "genderage":
        return chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                type: "column",
                height: 325
            },
            xAxis: {
                categories: ["13-17", "18-24", "25-34", "35-44", "45-54", "55-64", "65+"],
                lineColor: null,
                lineWidth: 0,
                tickWidth: 0,
                minPadding: .05,
                maxPadding: .05
            },
            yAxis: {
                alternateGridColor: "#f7f7f7",
                lineColor: null,
                lineWidth: 0,
                minPadding: .05,
                maxPadding: .1,
                endOnTick: !1,
                labels: {
                    formatter: function () {
                        return Math.abs(this.value)
                    }
                }
            },
            legend: {
                align: "left",
                verticalAlign: "top",
                margin: 0,
                floating: !0,
                x: 36
            },
            tooltip: {
                enabled: !0,
                formatter: function () {
                    var t;
                    return t = "<b>" + this.x + "</b>", $.each(this.points, function (e, n) {
                        return t += "<br/>" + n.series.name + " : " + Math.abs(n.y)
                    }), t
                },
                shared: !0
            },
            plotOptions: {
                column: {
                    stacking: "normal",
                    groupPadding: .2,
                    pointWidth: 25,
                    borderColor: null,
                    shadow: !1
                }
            },
            series: n
        });
    case "ltrend":
    case "trend":
        return u = "ltrend" === t, p = "ltrend" === t ? 32 : 20, chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                defaultSeriesType: "areaspline",
                height: p,
                marginRight: 0,
                marginLeft: 0,
                marginTop: 0,
                marginBottom: 1
            },
            xAxis: {
                type: "datetime",
                labels: {
                    enabled: !1
                },
                gridLineWidth: 0,
                lineColor: "#999",
                lineWidth: u ? 0 : 1,
                tickWidth: 0,
                minPadding: .01,
                maxPadding: .08
            },
            yAxis: {
                labels: {
                    enabled: !1
                },
                gridLineWidth: 0,
                lineColor: "#999",
                lineWidth: u ? 0 : 1,
                minPadding: .01,
                maxPadding: .08,
                endOnTick: !1
            },
            legend: {
                enabled: !1
            },
            tooltip: {
                enabled: !1
            },
            plotOptions: {
                areaspline: {
                    groupPadding: 0,
                    borderColor: null,
                    id: e,
                    shadow: !1,
                    marker: {
                        enabled: !1
                    },
                    states: {
                        hover: {
                            enabled: !1
                        }
                    },
                    fillOpacity: .2,
                    pointInterval: 864e5
                }
            },
            series: n
        });
    case "column":
        return chartsObject[e] = new Highcharts.Chart({
            chart: {
                renderTo: e,
                defaultSeriesType: t,
                height: s === void 0 || s === !1 ? 325 : 160,
                marginRight: 0,
                marginLeft: 36
            },
            xAxis: {
                type: "datetime",
                dateTimeLabelFormats: {
                    day: "%e %b"
                },
                lineColor: null,
                lineWidth: 0,
                tickWidth: 0,
                minPadding: .05,
                maxPadding: .05
            },
            yAxis: {
                alternateGridColor: "#f7f7f7",
                lineColor: null,
                lineWidth: 0,
                minPadding: .05,
                maxPadding: .1,
                minRange: r !== void 0 ? r : .1,
                endOnTick: !1,
                min: i,
                max: a !== void 0 && "" !== a ? a : void 0,
                allowDecimals: o !== void 0 ? !o : !1,
                labels: {
                    formatter: function () {
                        var t, e, n, a;
                        return a = .01 >= this.value ? 0 : this.value, t = void 0, e = void 0, n = void 0, t = Math.floor(a / 60), e = Math.floor(a) % 60, n = Math.floor(60 * (a - 60 * t - e)), t > 0 ? t + "h" : e > 0 ? e + "m" : n > 0 ? n + "s" : a
                    }
                }
            },
            legend: {
                enabled: n.length > 1,
                align: "left",
                verticalAlign: "top",
                margin: 0,
                floating: !0,
                x: 36
            },
            tooltip: {
                enabled: !0,
                formatter: function () {
                    var t, e, n, a, i;
                    return i = .01 >= this.y ? 0 : this.y, t = void 0, n = void 0, a = void 0, t = Math.floor(i / 60), n = Math.floor(i) % 60, a = Math.floor(60 * (i - 60 * t - n)), e = "", e += t > 0 ? " " + t + "h" : "", e += n > 0 ? " " + n + "m" : "", e += a > 0 ? " " + a + "s" : "", 0 === e.length && (e = "0"), "" + Highcharts.dateFormat("%e. %b %Y", this.x) + " : " + e
                }
            },
            series: n,
            plotOptions: {
                column: {
                    borderWidth: 0,
                    shadow: !1,
                    color: "#C4392F"
                }
            }
        });
    default:
        console.log("- Unrecongnised chart type -"), console.log("type : " + t), console.log("id : " + e), console.log(n), console.log("----------------------------\n")
    }
}, updateWooHighCharts = function (t, e, n) {
    switch (t) {
    case "add":
        return chartsObject[e.attr("id")].addSeries(n[0])
    }
}, updateAllWooHighChartsHeight = function () {
    var t, e, n, a, i;
    i = [];
    for (e in chartsObject) t = chartsObject[e].chartHeight, a = chartsObject[e].chartWidth, n = chartsObject[e].series.length, 70 + 30 * n > t ? i.push(chartsObject[e].setSize(a, 90 + 30 * n, !1)) : i.push(void 0);
    return i
}, initCompetitorCharts = function () {
    return chartsData.specialChartData = [], $(".criterium-competitor-container").removeClass("hidden"), $("#criterium-social_impact .criterium-competitor-container:not(.main-chart-parent)").addClass("main-chart-parent").prepend('<div class="chart-container comparative-comp-chart-container chartify-me"><ul class="value" title="' + window.currentWebsite + '"></ul></div>'), 0 === socialMediaCat.length && $("#criterium-social_impact .criterium-content .competitor-to-process>p").each(function () {
        var t, e;
        return e = $(this), t = e.find("span:first-child").attr("class").replace("big-right-aligned icon ", ""), chartsData.specialChartData[t] = parseInt(e.find("span:last-child").text()), socialMediaCat.push(t)
    }), $("#criterium-adwords_traffic .criterium-competitor-container").length > 0 && $("#criterium-adwords_traffic .criterium-competitor-container:not(.main-chart-parent)").addClass("main-chart-parent").prepend('<div class="chart-container bar-comp-chart-container chartify-me"><ul class="value" title="' + window.currentWebsite + '"><li>' + chartsObject[$("#criterium-adwords_traffic .criterium-content .chart-container").attr("id")].series[0].data[0].y + "</li></ul></div>"), $("#criterium-backlinks_counter .criterium-competitor-container").length > 0 ? $("#criterium-backlinks_counter .criterium-competitor-container:not(.main-chart-parent)").addClass("main-chart-parent").prepend('<div class="chart-container bar-comp-chart-container chartify-me"><ul class="value" title="' + window.currentWebsite + '"><li>' + chartsObject[$("#criterium-backlinks_counter .criterium-content .chart-container").attr("id")].series[0].data[0].y + "</li></ul></div>") : void 0
}, checkCompetitorCharts = function () {
    return $(".competitor-hide").remove(), $(".criterium-check-comp .criterium-competitor-container .competitor-to-process").each(function () {
        var t, e, n, a, i, o, r, s, l;
        switch (o = $(this), a = $(this).parents(".criterium-check-comp"), o.append('<ul class="value" title="' + o.parents(".competitors-data").find(".competitor-head h4").text() + '"></ul>'), s = o.find("ul.value"), a.attr("id")) {
        case "criterium-social_impact":
            o = o.addClass("chart-container comparative-comp-chart-container chartify-me");
            for (n in chartsData.specialChartData) e = o.find(">p span." + n), e.length > 0 && (l = e.siblings("span:last-child"), t = parseInt(chartsData.specialChartData[n]), r = parseInt(l.text()), i = 0, 0 === t ? 0 !== r && (i = 1e5) : i = Math.round(100 * (r / t)), i -= 100, i > 200 && (i = 200), s.append("<li>" + i + "</li>"));
            return o.find(">p").remove();
        case "criterium-adwords_traffic":
            return o.addClass("chart-container bar-comp-chart-container chartify-me"), s.append(o.find(".pie-chart-container ul:first-child").html()), o.find(">div").remove();
        case "criterium-backlinks_counter":
            return o.addClass("chart-container bar-comp-chart-container chartify-me"), s.append(o.find(".bar-chart-container ul:first-child").html()), o.find(">div").remove()
        }
    }), checkWooChart(!0)
}, cleanWooCharts = function () {
    return "0" === $("#criterium-adwords_traffic .criterium-content .pie-chart-container ul:first-child li").text() ? $("#criterium-adwords_traffic").remove() : void 0
}, getCountryCode = function (t) {
    return countries[t] !== void 0 ? countries[t].toLowerCase() : null
}, getColorCode = function (t, e) {
    return t = 10 * Math.floor(t * e / 10), mapColors[t]
}, wooMaps = function () {
    return $(".woomap").each(function (t, e) {
        return wooMap($(e))
    })
}, wooMap = function (t) {
    var e, n, a, i, o, r, s, l, c, d, u, p;
    if (!t.hasClass("yuhuu-mapped")) {
        for (a = t.find(".country-info"), c = [], s = 0, i = 0; a.length > i;) o = $(a[i]), n = getCountryCode(o.find(".name").html()), l = o.find(".percent").html(), null != n && (c[n] = l, s = Math.max(s, l)), ++i;
        e = [];
        for (r in c) e[r] = getColorCode(c[r], 100 / s);
        for (t.predata = c, t.max = s, t.html(""), t.vectorMap({
            map: "world_en",
            backgroundColor: "#fff",
            color: "#E4E4E4",
            colors: e,
            borderColor: "#fff",
            borderWidth: 1,
            borderOpacity: 1,
            hoverColor: "#294c6e",
            selectedColor: "#294c6e",
            enableZoom: !1,
            onLabelShow: function (e, n, a) {
                return l = "~ 0", t.predata[a] !== void 0 && (l = t.predata[a]), n.text(n.text() + " : " + l + "%")
            }
        }), t.bind("regionClick.jqvmap", function (t) {
            return t.preventDefault()
        }), t.after("<span class='scale'></span>"), d = t.next(".scale"), d.append("<span class='scale-start'>" + Math.floor(t.max / 10) + "</span>"), d.append("<span class='scale-items'></span>"), p = d.find(".scale-items"), i = 0; 100 >= i;) p.append("<span class='scale-item'></span>"), u = p.find(".scale-item:last-child"), u.css("background", mapColors[i]), i += 10;
        return d.append("<span class='scale-end'>" + Math.ceil(t.max) + "</span>"), t.height(t.height() + 40 + "px"), t.addClass("yuhuu-mapped")
    }
}, calculTopBars = function () {
    var t, e, n, a, i, o, r;
    return $("#dashboard-bars").length > 0 ? (i = $("#green-bar-counter"), o = $("#orange-bar-counter"), r = $("#red-bar-counter"), t = $(".criterium.result-1").length, e = $(".criterium.result-2").length, n = $(".criterium.result-3").length, a = t + e + n, i.find(".count").text(t), o.find(".count").text(e), r.find(".count").text(n), a > 0 ? (i.find(".percent").stop().animate({
        width: 100 / a * t + "%"
    }, 500), o.find(".percent").stop().animate({
        width: 100 / a * e + "%"
    }, 500), r.find(".percent").stop().animate({
        width: 100 / a * n + "%"
    }, 500)) : $("#green-bar-counter .percent, #orange-bar-counter .percent, #red-bar-counter .percent").css("width", "0%")) : void 0
}, calculTopPriorities = function () {
    var t, e, n, a, i, o, r, s;
    for (o = [], $(".criterium.result-2, .criterium.result-3").each(function () {
        var t, e, n;
        return n = $(this), n.find(".criterium-quicktips").length > 0 && !n.find(".criterium-quicktips").is(":empty") ? (t = n.hasClass(".criterium.result-2") ? .7 : 1, e = parseFloat(n.find(".prio-score").text()) * t, o.push({
            imp: e,
            id: n.attr("id"),
            tips: n.find(".criterium-quicktips").text()
        })) : void 0
    }), $(".prio-score, .criterium-quicktips").remove(), o.sort(function (t, e) {
        return t.imp - e.imp
    }), o.reverse(), i = 0; 5 > i;) o[i] && $("#report-priorities ol").append('<a href="#' + o[i].id + '"><li id="quick-win-' + (i + 1) + '" quick-wins"><i></i>' + o[i].tips + "</li></a>"), i++;
    return e = $("#get-manycontact-quick-wins"), a = !$("#criterium-conversion_form").hasClass("result-1"), r = $("#report-priorities ol"), t = r.find(">a[href=#criterium-conversion_form]"), n = 0 === $("#criterium-technologies").find("span.tech-manycontacts, span.tech-hello_bar").length, e.length > 0 && a && n && (t.length > 0 ? t.replaceWith(e.html()) : (s = Math.floor(5 * Math.random()), 0 === s ? r.prepend(e.html()) : r.find(">a:nth-child(" + s + ")").before(e.html()), r.find(">a:last-child").remove())), $("#report-priorities ol a").on("click", function (t) {
        return t.preventDefault(), scrollTo($($(this).attr("href")).trigger("click"), 500)
    }), $("#report-priorities").delay(300).slideDown(600)
}, initTopBarTrial = function (t) {
    var e;
    return e = $("#top-bar-trial"), $("#top").delay(t, "animate").queue("animate", function (t) {
        return $(this).animate({
            "margin-top": e.outerHeight()
        }, 200), e.slideDown(200), t()
    }).dequeue("animate"), $("#top-bar-register").click(function () {
        return _gaq.push(["_trackEvent", "Report", "TopBarRegisterClick", currentWebsite])
    })
}, getScoreIn = function (t, e, n, a) {
    return t = t.replace(/^http(s)?:\/\/(www\.)?/, ""), n = n ? "&paid=1" : "", $.ajax({
        type: "GET",
        data: "key=9718067451b92665a02b0c460d502fa2e6c0e4b4" + n + "&callback=?",
        url: "http://score.woorank.com/site/" + t,
        async: !0,
        context: e,
        dataType: "jsonp",
        success: function (t) {
            var e;
            return e = t.rank, e = a ? Math.round(e) : Math.round(10 * e) / 10, $(this).text(e)
        }
    })
}, getCompetitorsScore = function () {
    return $("#dashboard-competitors .competitors .url").each(function () {
        return getScoreIn($(this).text(), $(this).siblings(".score"), !0, !0)
    })
}, getRelatedScore = function () {
    return $("#criterium-related_websites tbody td:nth-child(3)").each(function () {
        var t;
        return t = $(this).siblings(":nth-child(2)").find("a").attr("href"), getScoreIn(t, $(this), $("#dashboard-get-pdf").length > 0, !1)
    })
}, formatMomentJs = function (t) {
    var e, n;
    return n = new Date, e = n.getTimezoneOffset(), null == t && (t = "LLL"), $(".moment").each(function () {
        var n, a;
        return a = $(this), n = moment(a.text(), "YYYY-MM-DD HH:mm:ss").subtract("m", e), a.hasClass("no-hour") && (t = "MMM D"), a.text(n.format(t)).removeClass("moment")
    }), $(".moment-month").each(function () {
        var t, e;
        return e = $(this), t = moment(e.text(), "YYYY-MM-DD"), e.text(t.format("MMMM")).removeClass("moment-month")
    })
}, twitterAge = $("#criterium-twitter_account .part.text:nth-child(2)>p:last-child>span.right-aligned-content"), twitterAge.length > 0 && !$("#criterium-twitter_account").hasClass("momented") && ($("#criterium-twitter_account").addClass("momented"), currentText = twitterAge.text(), splitted = currentText.replace(/[^\d,]/g, "").split(","), years2Sub = 0, months2Sub = 0, days2Sub = 0, 3 === splitted.length && (years2Sub = parseInt(splitted.shift())), 2 === splitted.length && (months2Sub = parseInt(splitted.shift())), 1 === splitted.length && (days2Sub = parseInt(splitted.shift())), years2Sub + months2Sub + days2Sub > 0 && (localizations = moment.relativeTime, newString = "", years2Sub > 1 ? newString += localizations.yy.replace("%d", years2Sub) : years2Sub > 0 && (newString += localizations.y), newString += "" === newString ? "" : ", ", months2Sub > 1 ? newString += localizations.MM.replace("%d", months2Sub) : months2Sub > 0 && (newString += localizations.M), newString += "" === newString ? "" : ", ", days2Sub > 1 ? newString += localizations.dd.replace("%d", days2Sub) : days2Sub > 0 && (newString += localizations.d), newString = localizations.past.replace("%s", newString), twitterAge.text(newString))), $(function () {
    var t;
    return t = function (t) {
        var e, n, a, i;
        return null == t && (t = 0), i = $("#top").offset().top, a = $("#left-nav").offset().top - 18, n = $("#left-nav>#flying, #left-nav.ext-view").first(), e = 30, window.isExtension && (n = $("#left-nav"), e = 0, a += 18), $(window).on("scroll", function () {
            var o;
            if (!(window.scrollY > a)) return window.isExtension ? n.removeClass("flyingMenu") : n.css("position", "relative").css("top", "0");
            if (window.isExtension ? n.addClass("flyingMenu") : n.css("position", "fixed").css("top", e + i + "px"), n.find(".nav-section.active nav").hasClass("inpage") || window.isExtension) {
                if (o = t, $(".module").each(function () {
                    var t;
                    return t = $(this).offset().top, window.scrollY + 20 >= t ? o++ : void 0
                }), o > t) return n.find("a").removeClass("current"), n.find("a:nth-child(" + o + ")").addClass("current");
                if (!n.find("a.score").hasClass("current")) return n.find("a").removeClass("current"), n.find("a.score").addClass("current")
            }
        }), n.find("nav.inpage>a").on("click", function (t) {
            var e, n;
            return t.preventDefault(), e = $(this).attr("href"), n = isNaN(parseInt(e)) ? $(e) : $(".module").eq(e), scrollTo(n, 500)
        })
    }, $("form.ajax-generate-report").on("submit", function (t) {
        var e, n, a;
        return a = $(this), e = !1, a.find("input.required").each(function () {
            return "" === $(this).val() ? e = !0 : void 0
        }), a.hasClass("ajax-valid") || e || a.hasClass("waiting-for-ajax") ? a.hasClass("ajax-valid") ? void 0 : (t.preventDefault(), !1) : (t.preventDefault(), a.find(".label-default").val(""), n = a.addClass("waiting-for-ajax").serialize(), $.ajax({
            type: "POST",
            data: n + "&ajax=1",
            cache: !1,
            url: a.attr("action"),
            context: a,
            dataType: "json",
            success: function (t) {
                return "ok" === t.status ? a.addClass("ajax-valid").attr("action", t.url).trigger("submit") : "suggest" === t.status ? (a.removeClass("waiting-for-ajax").find("#generate-report-input").val(t.url), a.trigger("submit")) : "rr" === t.status || "errorMessage" === t.status ? window.location.replace(t.url) : a.removeClass("waiting-for-ajax").find("#generate-report-input").addClass("required")
            }
        }), !1)
    }), $("form.ajax-generate-report .gift-token").click(function () {
        var t;
        return t = $("<input type='hidden' name='giftToken' />"), t.val("1"), $("form.ajax-generate-report").append(t)
    }), $("#generate-report .comp-button").on("click", function (t) {
        var e, n, a, i;
        return t.preventDefault(), i = $(this), a = i.parents(".inputs"), i.find("i").toggleClass("hidden"), a.hasClass("open") ? (n = 420, e = 372, $("#generate-report").hasClass("small-comps") && (n = 346, e = 298), i.parents(".inputs").animate({
            width: "" + n
        }, 400, function () {
            return $("#inp-comp-1:not(.label-default), #inp-comp-2:not(.label-default), #inp-comp-3:not(.label-default)").val("").trigger("focusout")
        }).find("#gen-inputs-container>div:first-child").animate({
            width: "" + n
        }, 350).find("input#generate-report-input").animate({
            width: "" + e
        }, 375)) : (n = 744, $("#generate-report").hasClass("small-comps") && (n = 670), i.parents(".inputs").animate({
            width: "" + n
        }, 400).find("#gen-inputs-container>div:first-child").animate({
            width: "204"
        }, 375).find("input#generate-report-input").animate({
            width: "156"
        }, 350)), a.toggleClass("open"), $("#comp-i").val("1")
    }), window.isInsideReview = $("#dashboard-content").hasClass("inside-page"), window.ispaidReview = $("#dashboard-content").hasClass("paid-review"), window.currentWebsite = $("#dashboard-site h1 a").text(), window.isExtension ? t() : window.isMobile || t(1), isInsideReview || (initWooCounter(), getCompetitorsScore()), $("#right-panel.light-report").length > 0 && ($("#right-panel.light-report .module.module-empty").each(function () {
        var t, e;
        return e = $(this), t = e.attr("id"), e.removeClass("hidden").removeClass("module-empty").addClass("lightversion").show().append($("#teasing-box ." + t))
    }), $("body.review .teasing-box").on("click", function () {
        var t, e;
        return e = $(this), t = e.attr("class").replace(" teasing-box", ""), _gaq.push(["_trackEvent", "LightReport", "TeasingBoxClick", t]), window.location = "/" + wooLang + "/user/plan"
    })), $("#dashboard").hasClass("generating-report") ? ispaidReview || isExtension || isMobile || initTopBarTrial(6e3) : (checkEmptyModule(), checkWooChart(!1), wooMaps(), isMobile && ($("#report-content").show(), $(".loading_sentence").remove(), clearInterval(loading_sentence_li_i_interval), activeMenuItem()), isInsideReview || (initCompetitorCharts(), checkCompetitorCharts(!0), getRelatedScore()), calculTopPriorities(), calculTopBars(), isMobile ? _gaq.push(["_trackEvent", "IpadReport", "ReviewDate", $("#dashboard-site .generated-time .value-title").attr("title")]) : (_gaq.push(["_trackEvent", "Report", "ReviewDate", $("#dashboard-site .generated-time .value-title").attr("title")]), ispaidReview || isExtension || initTopBarTrial(3e3))), $(".criterium").live("click", function (t) {
        var e, n;
        return !t.target.attributes.href && (t.preventDefault(), n = $(this), n.toggleClass("open-crit").find(".criterium-advice").slideToggle(100), n.hasClass("open-crit")) ? (e = n.attr("id"), e = e.replace("criterium-", ""), _gaq.push(["_trackEvent", "Report", "OpenAdvice", e])) : void 0
    }), $("#report-priorities .show-more-task a").on("click", function (t) {
        return t.preventDefault(), $("#report-priorities .show-more-task i").toggleClass("hidden"), $(this).off("click")
    }), $("#short-url a#short-label").on("click", function (t) {
        var e;
        return t.preventDefault(), e = $(this), e.hasClass("load") ? void 0 : (e.addClass("load").fadeTo(100, .5), _gaq.push(["_trackEvent", "Report", "RequestShortUrl", currentWebsite]), $.ajax({
            type: "POST",
            data: "url=" + currentWebsite,
            cache: !1,
            url: shortUrl,
            dataType: "html",
            context: $(this).parent("#short-url"),
            success: function (t) {
                var n, a;
                return e = $(this), n = e.find("pre"), a = e.find(".copy-tip"), e.find("#short-label").remove(), n.html(t.replace("http://", "<span>http://</span>")).trigger("click"), e.removeClass("no-short-url"), -1 !== navigator.appVersion.indexOf("Mac") ? a.find(".other").remove() : a.find(".mac").remove(), a.delay(800).fadeIn(500)
            }
        }))
    }), $("#short-url pre, #short-url a.copy").on("click", function () {
        return SelectText("short-val")
    }), $("#dashboard .date-dropdown .dropdown-value").on("change", function () {
        return _gaq.push(["_trackEvent", "Report", "changeReviewDate", currentWebsite]), window.location = "/" + wooLang + "/review/" + currentWebsite + "/" + $(this).val()
    }), formatMomentJs(), updateAllWooHighChartsHeight()
});