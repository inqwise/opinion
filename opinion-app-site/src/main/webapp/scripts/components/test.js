function toggleTableBoxes() {
    var n = $("table.grid :checkbox");
    $(this).is(":checked") ? (n.next().addClass("on"), n.prop("checked", !0)) : (n.next().removeClass("on"), n.prop("checked", !1)), n.first().trigger("change")
}

function emptyImage(n, t) {
    var i = $(n);
    i.attr("src", containerPrefix + "/content/images/1.gif"), i.css("border", "1px solid #ccc"), t && t.length > 0 ? i.height(t) : i.css("width", "158px")
}

function enableAjaxSorting() {
    constrainCheckboxWidth(), $('table.grid[paginationUrl][paginationAjax="true"]').each(function () {
        var n = $(this),
            i = n.attr("paginationUrl"),
            t = n.attr("paginationContainer");
        $("th a", n).each(function () {
            var u = $(this),
                o = u.attr("href"),
                r = $.deparam.querystring(o),
                f, e;
            !r.sort || (u.attr("href", "javascript:void(0);"), f = {
                sort: r.sort
            }, !r.sort || (f.sortdir = r.sortdir), e = $.param.querystring(i, f), u.click(function () {
                $.get(e, function (i) {
                    if (!t) {
                        var r = $(n).parent();
                        $(n).replaceWith(i), r.trigger("onsort")
                    } else $(t).html(i).trigger("onsort");
                    enableAjaxSorting(), enablePagination(), initInfoHovers()
                }).error(function () {
                    showErrorMessage(error, wr.G_Error)
                })
            }))
        })
    })
}

function enablePagination() {
    constrainCheckboxWidth(), $("table.grid:not([alreadypaginated])").each(function () {
        var n = $(this),
            t = n.attr("paginationTotalPages"),
            i = n.attr("paginationCurrentPage"),
            r = n.attr("paginationUrl"),
            u = $.trim($("tfoot", n).text()),
            s, f, e, o;
        u.length > 1 && (t == null && (t = 0, $("tfoot a", n).each(function () {
            var s = $(this),
                u = s.attr("href"),
                e = u.lastIndexOf("page="),
                o = parseInt(u.substring(e + 5)),
                i, f;
            t = t > o ? t : o, r == null ? (r = u.substring(0, e), n.attr("paginationUrl", r)) : r.indexOf("sort=") < 0 && (i = $.deparam.querystring(u), !i.sort || (f = {
                sort: i.sort
            }, !i.sort || (f.sortdir = i.sortdir), r = $.param.querystring(r, f), n.attr("paginationUrl", r)))
        })), i == null && (s = u.indexOf("<"), f = u.indexOf(">", s), f = f == -1 ? u.length : f, i = $.trim(u.substring(s + 1, f)), n.attr("paginationCurrentPage", i)), i = parseInt(i), t = i < t ? t : i, e = $("#pagHolder").children().first().clone(), e.attr("id", "pagCont"), $("tfoot td", n).html(e), o = $("#pagBox", n), o.val(i), o.focusout(gridPaginate), o.keypress(new onEnterPressExecute(gridPaginate).eventHandler), $("#totalPag", n).text(t), $("#pagCont a", n).click(function () {
            var t = $("#pagBox", n),
                i = $(this).attr("increment");
            t.val(parseInt(n.attr("paginationCurrentPage")) + parseInt(i)), gridPaginate.call(t)
        }), i == t ? $("#pagNext", n).hide() : i == 1 && $("#pagPrev", n).hide(), t > 1 && e.show(), n.attr("alreadypaginated", "true"))
    })
}

function gridPaginate() {
    var u = $(this),
        r = u.val(),
        t = $(this).parents("table.grid"),
        f = t.parent(),
        e = t.attr("paginationAjax"),
        o = t.attr("paginationUrl"),
        s = t.attr("paginationCurrentPage"),
        i = t.attr("paginationContainer"),
        n;
    r != s && (showActivityIndicator(!0), n = o, n[n.length - 1] != "&" && (n = n + "&"), n = n + "page=" + r, e ? $.get(n, function (n) {
        i ? $(i).html(n) : t.replaceWith(n), enablePagination(), initInfoHovers(), enableAjaxSorting(), showActivityIndicator(!1), scrollToTop(), i ? $(i).parent().animate({
            scrollTop: 0
        }, "fast") : f.animate({
            scrollTop: 0
        }, "fast")
    }) : window.location.href = n)
}

function validatorSetup() {
    function n(n, t, i) {
        var r = $.trim(n).split("\n"),
            u, f;
        for (u in r)
            if ($.trim(r[u]).length > 0 && (f = $.validator.methods.url.call(this, r[u], t, i), !f)) return !1;
        return !0
    }

    function t(n, t, i) {
        return isValueSameAsPalceHolder(n, t) ? !1 : $.validator.methods.required.call(this, n, t, i)
    }

    function i(n, t, i) {
        return isValueSameAsPalceHolder(n, t) ? !0 : $.validator.methods.url.call(this, n, t, i)
    }

    function r(n, t, i) {
        return isValueSameAsPalceHolder(n, t) ? !0 : $.validator.methods.email.call(this, n, t, i)
    }

    function f(n, t) {
        return isValueSameAsPalceHolder(n, t) ? !0 : u.test($.trim(n))
    }
    var u = /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$|^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/;
    $.validator.addMethod("urlListValidation", n), $.validator.addMethod("requiredNoWatermark", t, $.validator.messages.required), $.validator.addMethod("urlNoWatermark", i, $.validator.messages.url), $.validator.addMethod("emailNoWatermark", r, $.validator.messages.email), $.validator.addMethod("elementWithZeroOrEmptyValue", function (n) {
        return !(n == "" || n == "0")
    }), $.validator.addMethod("ipNoWatermark", f, $.validator.messages.ip), $.validator.addClassRules({
        wmMaxLength60: {
            maxlength: 60
        },
        wmMaxLength500: {
            maxlength: 500
        },
        wmMaxLength100: {
            maxlength: 100
        },
        wmRequired: {
            requiredNoWatermark: !0
        },
        wmUrl: {
            urlNoWatermark: !0
        },
        wmEmail: {
            emailNoWatermark: !0
        },
        urlValidation: {
            requiredNoWatermark: !0,
            urlNoWatermark: !0
        },
        emailValidation: {
            requiredNoWatermark: !0,
            emailNoWatermark: !0
        },
        urlListValidation: {
            requiredNoWatermark: !0,
            urlListValidation: !0,
            minlength: 10
        },
        requiredIfValueZeroOrEmpty: {
            elementWithZeroOrEmptyValue: !0
        },
        wmIp: {
            ipNoWatermark: !0
        }
    })
}

function validationErrorPlacement(n, t) {
    var i = t.attr("id");
    i !== undefined && $('.validationErrorPlaceholder[for~="' + i + '"]').html(n)
}

function onEnterPressExecute(n, t, i) {
    this.eventHandler = function (r) {
        t != null && i != null && ($.trim($(this).val()) == "" ? t.removeClass(i) : t.addClass(i)), r.which == 13 && n.call(this)
    }
}

function clickButtonOnEnter(n, t) {
    var r = $(n),
        u = function () {
            r.click()
        }, f, i;
    if (t.constructor.toString().indexOf("Array") == -1) i = $(t), i.keyup(new onEnterPressExecute(u, r, "orangeBorder").eventHandler);
    else
        for (f in t) i = $(t[f]), $(i).keyup(new onEnterPressExecute(u, r, "orangeBorder").eventHandler)
}

function getCheckedItemsInTable(n) {
    var t = [];
    return $("table.grid :checked").each(function () {
        var i = $(this);
        t.push(i.attr(n))
    }), t
}

function enableHintText() {
    hasPlaceholderSupport() || $("input[placeholder], textarea[placeholder]").each(function () {
        var n = $(this);
        n.Watermark(n.attr("placeholder"))
    })
}

function isValueSameAsPalceHolder(n, t) {
    return hasPlaceholderSupport() ? !1 : $(t).attr("placeholder") == undefined ? !1 : n == $(t).attr("placeholder")
}

function enableAutofocus() {
    hasAutofocusSupport() || $("input[autofocus], textarea[autofocus]").each(function () {
        var n = $(this);
        n.focus()
    })
}

function hasAutofocusSupport() {
    var n = document.createElement("input");
    return "autofocus" in n
}

function triggerCheckboxChange() {
    var n = $("table.grid :checkbox");
    n.first().trigger("change"), constrainCheckboxWidth()
}

function setupGridCheckButtonEnabler(n, t) {
    var i = function () {
        var i = $("input:checked", n).length > 0;
        i ? $(t).removeAttr("disabled") : $(t).attr("disabled", "disabled")
    };
    return $(n).bind("update", i), i
}

function setupGridCheckButtonEnablerDisableFor2OrMore(n, t) {
    var i = function () {
        var i = $("input:checked", n).length == 1;
        i ? $(t).removeAttr("disabled") : $(t).attr("disabled", "disabled")
    };
    return $(n).bind("update", i), i
}

function turnOnCheckboxes() {
    $("input[type='checkbox'] + label, input[type='checkbox'] + label + label").live("click", function () {
        var n = $(this),
            t = n.prev("input[type=checkbox]");
        (t.length == 0 && (n = n.prev("label"), t = n.prev("input[type=checkbox]")), t.attr("readonly")) || (n.toggleClass("on"), n.attr("for") || (t.prop("checked", n.hasClass("on")), t.trigger("change")))
    }), constrainCheckboxWidth()
}

function constrainCheckboxWidth() {
    $("table.grid td input[type='checkbox'] + label").first().parent().addClass("width20")
}

function initTreeView() {
    var n = $("#site-nav-minor");
    $("li[expand] > button.tree-toggle-button.collapsed", n).each(function () {
        $(this).click()
    })
}

function calcPercentageChange(n, t) {
    return t == 0 ? 0 : Math.round((n / t - 1) * 100)
}

function serviceProxy(n) {
    var t = this;
    this.serviceUrl = n, this.invoke = function (n, i, r, u, f, e) {
        var o = JSON.stringify(i),
            s = t.serviceUrl + n;
        $.ajax({
            url: s,
            data: o,
            type: "POST",
            processData: !1,
            contentType: "application/json",
            timeout: 13500,
            dataType: "text",
            success: function (n) {
                var t, i, u;
                if (r) {
                    if (t = JSON.parse(n), f) {
                        r(t);
                        return
                    }
                    i = !1;
                    for (u in t) {
                        i = !0, r(t[u]);
                        break
                    }
                    i || r(t)
                }
            },
            error: function (n) {
                if (u) {
                    handleAjaxError(n, u, e);
                    return
                }
            }
        })
    }, this.invokeGet = function (n, i, r, u, f, e) {
        var o = t.serviceUrl + n;
        $.ajax({
            url: o,
            data: i,
            type: "GET",
            dataType: "text",
            timeout: 13500,
            success: function (n) {
                var t, i, u;
                if (r) {
                    if (t = JSON.parse(n), f) {
                        r(t);
                        return
                    }
                    i = !1;
                    for (u in t) {
                        i = !0, r(t[u]);
                        break
                    }
                    i || r(t)
                }
            },
            error: function (n) {
                if (u) {
                    handleAjaxError(n, u, e);
                    return
                }
            }
        })
    }
}

function reportError(n, t) {
    if (t != "abort" && t != "No Transport" && (n.status != 0 || n.readyState != 0)) {
        var i = null;
        n.Message != null && (i = n.Message), showErrorMessage(i, wr.G_Error)
    }
}

function setupDeveloperConsole() {
    window.console || (console = {}, console.log = console.log || function () {}, console.warn = console.warn || function () {}, console.error = console.error || function () {}, console.info = console.info || function () {})
}

function handleAjaxError(n, t, i) {
    if (n.readyState == 4) {
        if (n.responseText) {
            var r;
            try {
                r = JSON.parse(n.responseText)
            } catch (u) {
                r = n.responseText
            }
            r ? t(r) : t({
                Message: null
            })
        }
    } else n.statusText == "timeout" && (i != null ? i() : showErrorMessage(wr.G_TimeoutError, wr.G_TimeoutErrorTitle))
}

function $id(n) {
    return $(document.getElementById(n))
}

function showActivityIndicator(n) {
    n != null && n && (activityIndicatorModal = !0), activityIndicatorInitialized || activityIndicatorModal && $("#activityIndicator").jqm({
        overlay: 20,
        modal: !0,
        overlayClass: "jqmLoadingOverlay"
    }), activityIndicatorModal ? $("#activityIndicator").jqmShow() : $("#activityIndicator").show()
}

function hideActivityIndicator() {
    activityIndicatorModal ? $("#activityIndicator").jqmHide() : $("#activityIndicator").hide()
}

function unsetModalActivityIndicator() {
    activityIndicatorModal = !1
}

function showHtmlMessage(n, t) {
    n != null && t != null && ($("#dgExErrorTitle").text(t), $("#dgExErrorMessage").html(n), $("#dgExError").jqm({
        overlay: 5,
        modal: !0
    }), $("#dgExError").jqmShow())
}

function showErrorMessageMarkUpBusy() {
    $("#dgExErrorMessage").text(wr.G_Throttling), $("#dgExErrorTitle").text(wr.G_UnknownError), $("#dgExError").jqm({
        overlay: 5,
        modal: !0
    }), $("#dgExError").jqmShow()
}

function showErrorMessage(n, t, i) {
    $("#dgExErrorMessage").empty(), t != null ? $("#dgExErrorTitle").text(t) : $("#dgExErrorTitle").text(wr.G_UnknownError), n != null ? i ? $("#dgExErrorMessage").html(n) : $("#dgExErrorMessage").text(n) : $("#dgExErrorMessage").text(wr.G_UnkownErrorDetails), $("#dgExError").jqm({
        overlay: 5,
        modal: !0
    }), $("#dgExError").jqmShow()
}

function genKpiMarkup(n, t, i) {
    if (n == null) return wr.G_NotAvailable;
    if (t == null) return u = $.validator.format('<img src="{0}/content/images/arrowunknown.png"></img>', containerPrefix), $.validator.format(kpiFormat, f, u);
    var f = calcPercentageChange(n[i], t[i]),
        u = "";
    return u = f > 0 ? $.validator.format('<img src="{0}/content/images/arrowincreasing.png"></img>', containerPrefix) : f < 0 ? $.validator.format('<img src="{0}/content/images/arrowdecreasing.png"></img>', containerPrefix) : $.validator.format('<img src="{0}/content/images/arrowunknown.png"></img>', containerPrefix), $.validator.format(kpiFormat, f, u)
}

function setupKpi(n, t) {
    n.html(t)
}

function swapQueryString(n, t, i) {
    var r = $.deparam.querystring(n);
    return r[t] = i, $.param.querystring(n, r)
}

function appendUrlParameter(n, t, i) {
    return n == null || n.length == 0 ? n : n.indexOf("?") > -1 ? $.validator.format("{0}&{1}={2}", n, t, i) : $.validator.format("{0}?{1}={2}", n, t, i)
}

function getSvc() {
    return new serviceProxy(containerPrefix + "/svc/Webmaster.svc/")
}

function showSparklines(n) {
    n || (n = "60px"), $(".sparkline:not(.sparkline-drawn)").show().sparkline("html", {
        width: n,
        fillColor: ""
    }).addClass("sparkline-drawn")
}

function disableFormSubmit() {
    $("form.disable-submit").submit(function () {
        return !1
    })
}

function showPopupModalDialog(n, t, i) {
    $(n).remove();
    var r = $(t);
    r.hide(), $("body").append(r), enablePagination(), $(n).jqm($.extend({
        overlay: 10,
        modal: !0
    }, i || {})).jqmShow()
}

function autoSelect(n) {
    var i, t;
    n != null && (n.childNodes.length == 1 && n.childNodes[0].nodeName == "#text" || n.tagName == "INPUT" && n.type == "text") && (n.tagName == "TEXTAREA" || n.tagName == "INPUT" && n.type == "text" ? n.select() : window.getSelection ? (i = window.getSelection(), t = document.createRange(), t.selectNode(n.firstChild), i.removeAllRanges(), i.addRange(t)) : (document.selection.empty(), t = document.body.createTextRange(), t.moveToElementText(n), t.select()))
}

function initInfoHovers() {
    $("img.info-pop-up:not([done])").each(function () {
        var t = $(this),
            n = t.next();
        t.mouseover(function () {
            var i, f;
            $("img.info-pop-up").next().hide(), i = isRTL, isRTL ? t.offset().left - n.width() - 20 < 0 && (i = !i) : t.offset().left + n.width() + 20 > $(window).width() && (i = !i), n.removeClass("flipped"), $("img[flipped]", n).each(function () {
                var n = $(this);
                n.attr("src", n.attr("src").replace("-fli.png", ".png")).removeAttr("flipped")
            }), i && (n.addClass("flipped"), $("img:not([flipped])", n).each(function () {
                var n = $(this);
                n.attr("src", n.attr("src").replace(".png", "-fli.png")).attr("flipped", "y")
            }));
            var r = t.position(),
                u = r.left + (i ? -n.width() + 12 : -15),
                e = r.top - 12 + $("#page-content").scrollTop();
            t.offset().top + n.height() > $(window).height() - 20 ? (f = r.top + $("#page-content").scrollTop() - n.height(), f > 10 ? (u = u + (i ? -35 : 25), e = f, n.find(".box-caption-bottom").show(), n.find(".box-caption").hide()) : (n.find(".box-caption-bottom").hide(), n.find(".box-caption").show())) : (n.find(".box-caption-bottom").hide(), n.find(".box-caption").show()), n.css({
                top: e,
                left: u
            }).show()
        }), n.mouseleave(function () {
            n.hide()
        }), t.attr("done", "done")
    })
}

function cancelEvent(n) {
    n.stopPropagation()
}

function initHeader() {
    $("#account-name").click(function (n) {
        n.stopPropagation();
        var i = $(this).width(),
            t = $("#signoutHover");
        t.width(i), t.toggle()
    }), $("body").click(function () {
        $("#signoutHover").hide()
    })
}

function getCalendarName(n) {
    var t = {
        GregorianCalendar: "gregorian",
        UmAlQuraCalendar: "islamic",
        HijriCalendar: "islamic",
        ThaiBuddhistCalendar: "thai"
    };
    return t[n] ? t[n] : "gregorian"
}

function initCalendars() {
    var r = getCalendarName(window.calendarName),
        t = calendar.ShortDatePattern,
        n, i;
    t = t.replace(/M/g, "m"), n = "local", $.calendars.picker.regional[n] = {
        renderer: $.calendars.picker.defaultRenderer,
        prevText: "",
        prevStatus: wr.G_Prev,
        prevJumpText: "",
        prevJumpStatus: "",
        nextText: "",
        nextStatus: wr.G_Next,
        nextJumpText: "",
        nextJumpStatus: "",
        currentText: "",
        currentStatus: "",
        todayText: "",
        todayStatus: "",
        clearText: "",
        clearStatus: "",
        closeText: "",
        closeStatus: "",
        yearStatus: "",
        monthStatus: "",
        weekText: "",
        weekStatus: "",
        dayStatus: "",
        defaultStatus: "",
        isRTL: calendar.IsRightToLeft
    }, $.calendars.picker.setDefaults($.calendars.picker.regional[n]), $.calendars.calendars[r].prototype.regional[n] = {
        epochs: ["BCE", "CE"],
        monthNames: calendar.MonthNames,
        monthNamesShort: calendar.AbbreviatedMonthGenitiveNames,
        dayNames: calendar.DayNames,
        dayNamesShort: calendar.AbbreviatedDayNames,
        dayNamesMin: calendar.ShortestDayNames,
        dateFormat: t,
        firstDay: calendar.FirstDayOfWeek,
        isRTL: calendar.IsRightToLeft
    }, i = $.calendars.instance(r, n), window.calendarObject = i, $('.calendar-input input[type="text"]').each(function () {
        var n = $(this).attr("minvalue"),
            t = $(this).attr("maxvalue");
        n == "" && (n = null), t == "" && (t = null), $(this).calendarsPicker({
            calendar: i,
            onSelect: function (n) {
                $(this).trigger("inputDateChanged", n)
            },
            changeMonth: !1,
            showAnim: "fade",
            showSpeed: "fast",
            minDate: n,
            maxDate: t
        })
    }), $(".calendar-date-range-picker").calendarRangeSelector({})
}

function initEmptyTables() {
    $('table[emptytable]:not([emptytablesetup="true"])').each(function () {
        var o, n, t;
        $(this).attr("emptytablesetup", "true"), o = $(this).find("thead>tr:first-child th").length, n = $(this).children("tfoot"), n.length == 0 && (n = $("<tfoot/>").appendTo($(this)));
        var i = $(this),
            l = i.attr("emptytable") == "true",
            a = $(this).attr("emptycompact") == "true",
            r = $("<tr />", {
                "class": "empty " + (l ? "" : " none") + (a ? " compact" : "")
            }).appendTo(n),
            s = $("<td />", {
                colspan: o
            }).appendTo(r),
            h = $.trim($(this).attr("emptytitle")),
            c = $.trim($(this).attr("emptybutton")),
            u = $.trim($(this).attr("emptyurl")),
            f = $.trim($(this).attr("emptyfocus")),
            e = $.trim($(this).attr("emptyevent"));
        h.length > 0 && $("<div />").appendTo(s).text(h), c.length > 0 && (u.length > 0 || f.length > 0 || e.length > 0) && (t = $("<input />", {
            type: "button",
            value: c,
            disabled: readOnlyMode ? "disabled" : null
        }).appendTo(s), readOnlyMode || (f.length > 0 ? t.click(function () {
            $(f).focus()
        }) : u.length > 0 ? t.click(function () {
            window.location = u
        }) : e.length > 0 && t.click(function () {
            i.trigger(e)
        }))), i.bind("update", function () {
            var n = $(this).find("tbody>tr").length;
            n > 0 ? r.hide() : r.show()
        })
    })
}

function loadShared() {
    $.ajaxSetup({
        error: function (n) {
            handleAjaxError(n, reportError)
        }
    }), $(document).ajaxStart(function () {
        showActivityIndicator()
    }).ajaxStop(function () {
        hideActivityIndicator()
    }), $("#toggleAll").change(toggleTableBoxes), initTreeView(), validatorSetup(), turnOnCheckboxes(), enableHintText(), enableAutofocus(), enablePagination(), enableAjaxSorting(), showSparklines(), $.fn.addSite.userProfileFormInit("#profileDialogForm", "#saveProfile"), disableFormSubmit(), initInfoHovers(), initHeader(), initCalendars(), initEmptyTables(), $("input.urlValidation, input.wmUrl").urlInputBox(), fixIeSelectWidth()
}

function textAreaWithLineLimit(n) {
    return function (t) {
        var i = $(this).val().split("\n").length;
        if (t.keyCode == 13 && i >= n) return !1
    }
}

function formatNumber(n) {
    return n == 0 ? "0" : $.formatNumber(n, {
        format: formattingNumberFormat,
        locale: locale
    })
}

function scrollToTop() {
    $("#page-content").animate({
        scrollTop: 0
    }, "fast")
}

function fixPlaceholdersBeforeInFormSubmit(n) {
    if (!hasPlaceholderSupport()) {
        var t = n.find("input[placeholder], textarea[placeholder]");
        t.each(function () {
            $(this).attr("placeholder") != undefined && $(this).attr("placeholder") == $(this).val() && $(this).val("")
        })
    }
}

function getLocalTimezoneOffset(n) {
    var t = n.getTimezoneOffset();
    return Math.floor(t / 60)
}

function formatXaxis(n) {
    var i = new Date(n),
        r = monthDayPattern.replace(/M+/g, function (n) {
            return n.length > 1 ? "M" : "m"
        });
    return calendarObject.formatDate(r, calendarObject.newDate(i.getUTCFullYear(), i.getUTCMonth() + 1, i.getUTCDate()))
}

function getStandartChartOptions() {
    var n, t, i;
    return monthDayPattern = calendar.MonthDayPattern, n = {
        mode: "time",
        minTickSize: [1, "day"],
        monthNames: calendar.AbbreviatedMonthGenitiveNames
    }, monthDayPattern.length > 1 && monthDayPattern.indexOf("MMM") == -1 && (n.tickFormatter = formatXaxis), t = isRTL && $.browser.msie && $.browser.version < 9, i = {
        colors: ["#215dd3", "#009bda", "#339a00", "#ef2a24", "#727272"],
        xaxis: n,
        yaxis: {
            minTickSize: 1,
            tickFormatter: function (n) {
                return formatNumber(n).replace(" ", "&nbsp;")
            }
        },
        grid: {
            hoverable: !0,
            autoHighlight: !1,
            color: "#949596",
            tickColor: "#eaecee",
            borderWidth: 1
        },
        series: {
            lines: {
                show: !0,
                lineWidth: 2
            },
            points: {
                show: !1,
                radius: 2.5,
                symbol: function (n, t, i, r) {
                    n.arc(t, i, r, 0, 2 * Math.PI, !1), n.closePath(), n.fillStyle = "#e66400", n.fill(), n.beginPath()
                }
            },
            shadowSize: 2
        },
        legend: {
            show: !1
        },
        crosshair: {
            mode: t ? "" : "x",
            color: "#f6c5a1"
        },
        webmasterlegend: {
            enabled: !0,
            showPoints: !t
        },
        webmastergrid: {
            show: !0,
            color: "#ffffff",
            lineWidth: 3
        }
    }, i
}

function splitChartData(n, t, i) {
    var f, u, e, r;
    for (f in n)
        for (u = n[f], e = u[0] * 1e3, r = 0; r < i; ++r) u[r + 1] !== null && t[r].push([e, u[r + 1]])
}

function setupChart(n, t, i, r, u, f, e, o) {
    var a, c = function () {
            var c = window.calendarObject,
                e = 0,
                o = 0,
                y, p, w, s;
            try {
                y = c.parseDate(c.local.dateFormat, $(u).val()).toJSDate(), p = c.parseDate(c.local.dateFormat, $(f).val()).toJSDate(), e = y.getTime(), o = p.getTime()
            } catch (nt) {}
            e > o && (w = o, o = e, e = w);
            var h = null,
                l = null,
                d = 0,
                v = 1,
                b = !1,
                k = [
                    [],
                    [],
                    [],
                    [],
                    []
                ],
                g = i.filter(":checked");
            g.each(function () {
                var n = parseInt($(this).attr("index")),
                    t, u, i, f, s;
                k[n] = {
                    data: r[n],
                    label: $(this).parent().find("span").text()
                }, r[n].length > 0 && (h = Math.max(h, r[n][0][0]), l = Math.max(h, r[n][r[n].length - 1][0]));
                for (t in r[n]) t = parseInt(t), u = r[n][t], i = u[0] >= e && u[0] <= o, !i && t + 1 < r[n].length && (f = r[n][t + 1], i = f[0] >= e && f[0] <= o), !i && t > 0 && (s = r[n][t - 1], i = s[0] >= e && s[0] <= o), i && (v = Math.max(v, u[1]), b = !0)
            }), e < h && (e = h), o > l && (o = l), s = $.extend({}, t), s.xaxis.min = e, s.xaxis.max = o, s.yaxis.min = d, s.yaxis.max = v * 1.05, b ? (n.html(""), a = $.plot(n, k, s)) : n.html($.validator.format('<div class="charts-na"><div>{0}</div></div>', wr.G_NA))
        }, s, l, h;
    return c(), s = null, l = function (n) {
        s && clearTimeout(s), s = setTimeout(function () {
            s = null, c()
        }, n)
    }, $(window).resize(function () {
        l(10)
    }), $(o).click(function () {
        n.html(""), l(500)
    }), h = null, i.change(function () {
        h && clearTimeout(h), h = setTimeout(function () {
            h = null, c()
        }, 10)
    }), $(e).bind("dateChanged", function () {
        c()
    }), a
}

function fixIeSelectWidth() {
    !$.browser.msie || $.browser.version >= 9 || $(".line-item .half-width select, .line-item .auto-size-width select").bind("focus mouseover", function () {
        $(this).addClass("autowidth").removeClass("clicked"), $(this).parent().addClass("autosize")
    }).bind("click", function () {
        $(this).toggleClass("clicked")
    }).bind("mouseout", function () {
        $(this).hasClass("clicked") || ($(this).removeClass("autowidth"), $(this).parent().removeClass("autosize"))
    }).bind("blur", function () {
        $(this).removeClass("autowidth clicked"), $(this).parent().removeClass("autosize")
    })
}

function showAdCenterPopup() {
    var n = $.validator.format(wr.AdCenter_OfferDetails, apCfg.AzureStorage.AdCenterCurrentCouponBatchExpiry);
    showHtmlMessage(n, wr.AdCenter_OfferDetailsLabel)
}

function getRequestVerificationToken(n) {
    return $(n).find('input[type="hidden"][name="__RequestVerificationToken"]').val()
}

function createExternalUrl(n) {
    return $("<a>", {
        target: "_blank",
        "class": "url-text",
        href: n
    }).text(n).append($("<span>", {
        "class": "trend-icon blink-icon-popup"
    }))
}

function startPostRequestWithJson(n, t, i, r, u) {
    n = $.extend(n, {
        __RequestVerificationToken: getRequestVerificationToken(u)
    }), $.ajax({
        url: t,
        data: n,
        type: "POST",
        success: i,
        error: r
    })
}

function createHip(n) {
    var t;
    return t = {
        error: 0,
        left: "10",
        showInstruction: !1,
        showMenu: !0,
        showError: !1,
        instructionsInside: !1,
        inputWidth: 250,
        done: !1,
        holder: "ispHIPHIP",
        scriptHolder: "ispHIPScript",
        count: 0,
        type: "visual",
        cssSet: {
            cssCdHIPMenu: "hip-menu",
            cssCdHIPInput: "hip-input",
            cssCdHIPLink: "hip-link",
            cssCdHIPError: "hip-error",
            cssCdHIPErrorImg: "hip-error-img"
        },
        postLoad: function () {
            var i, r;
            $("input.hip-input").css({
                "border-width": "1px",
                padding: "5px 2px 3px 2px"
            }), $("input.hip-input").closest("table").css({
                "margin-right": "6px"
            }), i = $("div.hip-menu div :nth-child(3)"), $("div.hip-menu div :first-child").text(n.refresh).attr("title", n.refreshTooltip), i.length == 1 && (t.type == "audio" ? (i.text(n.visual).attr("title", n.visualTooltip), $(".cssWLSPHIPAudio").attr("title", n.audioTooltip)) : t.type == "visual" && i.text(n.audio).attr("title", n.audioTooltip)), r = $("#serverCode").val(), t.error != "0" || r != "0" ? ($("#idError").show(), $("input.hip-input").focus(), $("#serverCode").val("0")) : $("#idError").hide()
        },
        reloadHIP: function () {},
        hipVerify: function (n, i) {
            if (t.clientValidation(), t.error == "0") {
                $("#Solution").val(n), $("#Token").val(i), $("#Type").val(t.type);
                return
            }
        }
    }
}

function testForIE6() {
    var n, t;
    try {
        n = $.browser.msie && parseFloat($.browser.version) < 7, n && (t = $.validator.format(wr.Upgrade_Description, ['<a href="', apCfg.WebConfig.DownloadInternetExplorer, '">', wr.Upgrade_Link, "</a>"].join("")), showErrorMessage(t, wr.Upgrade_Title, !0))
    } catch (i) {
        console.log(i)
    }
}
var formValidateOptions, noCleanValidateOptions, hasPlaceholderSupport, activityIndicatorInitialized, activityIndicatorModal, kpiFormat, formattingNumberFormat, monthDayPattern;
(function (n) {
    function i() {
        var i = n("#siteurl").val();
        n("#siteUrlSuggestion").hide(), t = r(i), t != i && (n("#siteUrlSuggestion").html(n.validator.format(wr.AddSite_AddingPageWarning, t, "javascript:void(0);")), n("#siteUrlSuggestion").show())
    }

    function r(n) {
        var r = /(.*\/)(([^\/]+.(htm|xml|html|xhtml|shtml|phtml|php|asp|aspx|jsp|cgi|pl|py)([\/?]|$))).*/i,
            t = n.replace(r, function (n, t) {
                return t
            }),
            i = t.indexOf("?");
        return i >= 0 && (t = t.substring(0, i)), t
    }

    function u(t) {
        console.log("updateEmailOptInStatus private method called"), n("#communicationsOptIn", t).is(":checked") ? (n("input", t + " .emailFrequencyRadioButtons").removeAttr("disabled"), n("input[name=emailFrequency]", t).is(":checked") == !1 && n("#emailFrequencyWeekly", t).prop("checked", !0)) : n("input", t + " .emailFrequencyRadioButtons").attr("disabled", "disabled")
    }
    var t = "";
    n.fn.addSite = function () {
        n("#offsetHours").val(getLocalTimezoneOffset(new Date)), n.fn.addSite.userProfileFormInit("#addSiteForm", "#AddSite"), n("#profileFormFields").length == 1 && n.fn.addSite.populateProfileForm("#addSiteForm"), n("#siteurl").keyup(i), n("#siteUrlSuggestion a").live("click", function () {
            n("#siteUrlSuggestion").hide(), n("#siteurl").val(t)
        }), i()
    }, n.fn.addSite.userProfileFormInit = function (t, i) {
        n(t).validate(formValidateOptions), n(i, t).click({
            parentFormId: t
        }, function (t) {
            n.fn.addSite.saveProfileForm(t.data.parentFormId)
        }), n("#communicationsOptIn", t).click({
            parentFormId: t
        }, function (n) {
            u(n.data.parentFormId)
        })
    }, n.fn.addSite.showProfileDialog = function () {
        n("#profileDialog").jqm({
            overlay: 10,
            modal: !0
        }).jqmShow(), n.fn.addSite.populateProfileForm("#profileDialogForm")
    }, n.fn.addSite.saveProfileForm = function (t) {
        var u, r, s, i, f, e, o, h, c;
        n(t).valid() ? (u = [], n("input[name=alert]:not(:checked)", t).each(function () {
            u.push(n(this).val())
        }), r = n("#siteurl").val(), s = u.join(","), n("input[name=alerts]", t).val(s), i = n(t).attr("action"), r != null && r.length > 0 && (i = appendUrlParameter(i, "url", encodeURIComponent(r))), f = n("#sitemapurl").val(), f != null && f.length > 0 && (i = appendUrlParameter(i, "hasSitemap", "1")), e = n("#sitetraffic").val(), e != null && e != 0 && (i = appendUrlParameter(i, "hasSiteTraffic", "1")), showActivityIndicator(!0), n.post(i, n(t).serialize(), function (i) {
            if (t == "#profileDialogForm") n("#profileDialog").jqmHide();
            else {
                var r = i.IsSiteAlreadyVerified ? "/home/mysites" : "/configure/verify/ownership/?url=" + i.SiteUrl;
                document.location = containerPrefix + r
            }
            showActivityIndicator(!1)
        })) : (o = n("select.error, input.error", t), o.length > 0 && (h = t == "#profileDialogForm" ? ".contentScroll" : "#page-content", c = o.first().offset().top, n(h).animate({
            scrollTop: c
        }, 1e3)))
    }, n.fn.addSite.populateProfileForm = function (t) {
        n.getJSON(containerPrefix + "/Home/GetUserProfile", function (i) {
            var r, u, f, e;
            n("#sitemapurl", t).val(""), n("#sitetraffic", t).val(""), n("#firstName", t).val(i.FirstName), n("#lastName", t).val(i.LastName), n("#email", t).val(i.Email), n("#company", t).val(i.Company), n("#contactphone", t).val(i.ContactPhone), n("#jobrole", t).val(i.JobRole), n("#city", t).val(i.City), n("#state", t).val(i.State), n("#zip", t).val(i.Zip), n("#country", t).val(i.Country), n("#companysize", t).val(i.CompanySize), n("#industry", t).val(i.Industry), n("#isAgency", t).prop("checked", i.IsAgency), i.CommunicationsOptIn ? (n("#communicationsOptIn", t).prop("checked", !0), n("input", t + " .emailFrequencyRadioButtons").removeAttr("disabled")) : (n("#communicationsOptIn", t).prop("checked", !1), n("input", t + " .emailFrequencyRadioButtons").attr("disabled", "disabled")), n("input[value=" + i.EmailFrequency + "]", t).prop("checked", !0), i.Alerts == null && (i.Alerts = ""), r = i.Alerts.split(","), u = n(t + " input[name=alert]"), u.prop("checked", !0);
            for (f in r) e = u.filter("[value=" + r[f] + "]"), e.prop("checked", !1)
        })
    }
})(jQuery), formValidateOptions = {
    onsubmit: !1,
    onfocusout: !1,
    focusCleanup: !0,
    errorPlacement: validationErrorPlacement
}, noCleanValidateOptions = $.extend(!0, {}, formValidateOptions), noCleanValidateOptions.focusCleanup = !1, hasPlaceholderSupport = function () {
    var n = document.createElement("input"),
        t = "placeholder" in n;
    return function () {
        return t
    }
}(), setupDeveloperConsole(), activityIndicatorInitialized = !1, activityIndicatorModal = !1, kpiFormat = "<span>{0}</span>% {1}", formattingNumberFormat = "#,###", monthDayPattern = "", $(document).ready(loadShared),
function (n) {
    n.fn.calendarRangeSelector = function () {
        var i = n(this),
            o = i.find(".calendar-end-date").val(),
            r = i.find(".calendar-preset"),
            u = i.find(".calendar-input-start-date"),
            f = i.find(".calendar-input-end-date"),
            e = function (t) {
                n.cookie("cp", t, {
                    expires: 365,
                    path: containerPrefix
                })
            }, s = function (t, i) {
                n.cookie("ccs", t, {
                    expires: 1,
                    path: containerPrefix
                }), n.cookie("cce", i, {
                    expires: 1,
                    path: containerPrefix
                })
            };
        r.change(function () {
            var t = n(this).find("option:selected").attr("period");
            e(t), n(this).val() != "" && (u.val(n(this).val()), f.val(o), i.trigger("dateChanged"))
        }), i.find('.calendar-input input[type="text"]').bind("inputDateChanged", function () {
            r.val() != "" && (r.val(""), e("")), s(u.val(), f.val()), i.trigger("dateChanged")
        })
    }
}(jQuery),
function (n) {
    function r(i) {
        function e(n) {
            for (var c = [], t, l = i.getData(), r, a, u = 0; u < l.length; ++u) {
                for (r = l[u], t = 0; t < r.data.length; ++t)
                    if (r.data[t][0] > n.x) break;
                var o, s, h = 0,
                    f = r.data[t - 1],
                    e = r.data[t];
                f == null ? (o = e != null ? e[0] : 0, s = e != null ? e[1] : 0, h = t) : e == null ? (o = f[0], s = f[1], h = t - 1) : (o = f[0], s = f[1], h = t - 1), a = r.datapoints.pointsize, c[c.length] = {
                    datapoint: [o, s],
                    dataIndex: h,
                    series: r,
                    seriesIndex: u
                }
            }
            return c
        }
        var r = [],
            o, u = !1,
            f = !1;
        i.hooks.bindEvents.push(function (i, o) {
            function h(n) {
                var t = i.getAxes(),
                    u = i.getPlotOffset(),
                    f = o.offset(),
                    r = {
                        pageX: n.pageX,
                        pageY: n.pageY
                    }, e = n.pageX - f.left - u.left,
                    s = n.pageY - f.top - u.top;
                return t.xaxis.used && (r.x = t.xaxis.c2p(e)), t.yaxis.used && (r.y = t.yaxis.c2p(s)), r
            }

            function s(n) {
                return e(h(n))
            }

            function a() {
                if (document.onselectstart !== undefined && (document.onselectstart = t.onselectstart), document.ondrag !== undefined && (document.ondrag = t.ondrag), u = !1, f) {
                    f = !1;
                    var r = i.getData();
                    i.getOptions().webmastercrawldelay.onChange(r[0].data)
                }
                return !1
            }

            function c(n, t) {
                var o, f, u, e;
                if (i.getOptions().webmastercrawldelay.highlightRadius != 0)
                    for (o = i.getData(), f = 0; f < n.length; ++f) u = n[f], e = r[f], (e == null || e.seriesIndex != u.seriesIndex || e.dataIndex != u.dataIndex || t) && (e != null && i.unhighlight(e.series, e.datapoint), r[f] = u, u.series.highlightColor = i.getOptions().webmastercrawldelay.highlightColors[f], u.series.highlightRadius = i.getOptions().webmastercrawldelay.highlightRadius, i.highlight(u.series, u.datapoint))
            }

            function l(n, t) {
                var r = i.getData(),
                    u, e, o, n;
                n.length == 1 && r.length > 0 && (u = n[0], u.dataIndex >= 0 && u.dataIndex < r[0].data.length && (e = h(t), o = Math.min(i.getOptions().webmastercrawldelay.max, Math.max(Math.floor(e.y + .5), i.getOptions().webmastercrawldelay.min)), r[0].data[u.dataIndex][1] = o, i.setData([r[0].data]), i.draw(), n = s(t), c(n, !0), f = !0))
            }
            i.getOptions().webmastercrawldelay != null && i.getOptions().webmastercrawldelay.enabled && (o.mousedown(function (r) {
                if (r.which == 1) {
                    document.body.focus(), document.onselectstart !== undefined && t.onselectstart == null && (t.onselectstart = document.onselectstart, document.onselectstart = function () {
                        return !1
                    }), document.ondrag !== undefined && t.ondrag == null && (t.ondrag = document.ondrag, document.ondrag = function () {
                        return !1
                    });
                    var f = s(r);
                    l(f, r), u = !0, i.getOptions().webmastercrawldelay.onChangeBegin();
                    n(document).one("mouseup", a)
                }
            }), o.mousemove(function (n) {
                var t = s(n);
                c(t), u && l(t, n)
            }), o.mouseout(function () {
                for (var t, n = 0; n < r.length; ++n) t = r[n], t != null && (i.unhighlight(t.series, t.datapoint), r[n] = null)
            }))
        })
    }
    var i = {
        webmastercrawldelay: {
            enabled: !1,
            autoHighlight: !0,
            highlightColors: ["#1b95ff", "#43f8f8"],
            highlightRadius: 2,
            onChange: function () {},
            onChangeBegin: function () {},
            min: 1,
            max: 10
        }
    }, t = {};
    n.plot.plugins.push({
        init: r,
        options: i,
        name: "webmastercrawldelay",
        version: "1.0"
    })
}(jQuery),
function (n) {
    function i(n) {
        n.hooks.draw.push(function (n, t) {
            var i = n.getOptions().webmastergrid,
                r;
            i.show && (r = n.getPlotOffset(), t.save(), t.translate(r.left, r.top), t.strokeStyle = i.color, t.lineWidth = i.lineWidth, t.lineJoin = "round", t.beginPath(), t.moveTo(n.width(), 0), t.lineTo(n.width(), n.height()), t.moveTo(0, 0), t.lineTo(n.width(), 0), t.stroke(), t.restore())
        })
    }
    var t = {
        webmastergrid: {
            show: !1,
            color: "rgba(170, 0, 0, 0.80)",
            lineWidth: 1
        }
    };
    n.plot.plugins.push({
        init: i,
        options: t,
        name: "webmastergrid",
        version: "1.0"
    })
}(jQuery),
function (n) {
    function i(t) {
        function e() {
            var s = t.getPlaceholder(),
                h = t.getData(),
                i, c, o, e, l;
            for (s.find(".legend").remove(), i = [], i.push('<tr><th class="dateLabel" colspan="2">&nbsp;</td></tr>'), e = 0; e < h.length; ++e)(c = h[e], o = c.label, o) && i.push('<tr><td class="legendLabel">' + o + '</td><td class="legend-label-value" index="' + e + '"></td></tr>');
            i.length != 0 && (l = "<table>" + i.join("") + "</table>", r = n('<div class="legend">' + l + "</div>").appendTo(s), u = t.getPlaceholder().find(".legend-label-value"), f = t.getPlaceholder().find(".dateLabel"))
        }

        function o(n) {
            for (var s = [], i, c = t.getData(), r, u, f, e = 0; e < c.length; ++e) {
                for (r = c[e], i = 0; i < r.data.length; ++i)
                    if (r.data[i][0] > n.x) break;
                var l = i < r.data.length,
                    a = n.x,
                    o = 0,
                    h = 0;
                l && (u = r.data[i - 1], f = r.data[i], u == null ? (o = f != null ? f[1] : 0, h = i) : f == null ? (o = u[1], h = i - 1) : o = u[1] + (f[1] - u[1]) * (n.x - u[0]) / (f[0] - u[0])), s[s.length] = {
                    datapoint: [a, o],
                    dataIndex: h,
                    series: r,
                    seriesIndex: e,
                    visible: l
                }
            }
            return s
        }
        var i = [],
            u, f, r;
        t.hooks.bindEvents.push(function (t, s) {
            if (t.getOptions().webmasterlegend != null && t.getOptions().webmasterlegend.enabled) {
                var h = t.getOptions().webmasterlegend.showPoints;
                e(), r.hide(), s.mousemove(function (e) {
                    var v = t.getAxes(),
                        b = t.getPlotOffset(),
                        k = s.offset(),
                        y = {
                            pageX: e.pageX,
                            pageY: e.pageY
                        }, d = e.pageX - k.left - b.left,
                        g = e.pageY - k.top - b.top,
                        p, a, c, l, w;
                    for (v.xaxis.used && (y.x = v.xaxis.c2p(d)), v.yaxis.used && (y.y = v.yaxis.c2p(g)), p = o(y), a = 0; a < p.length; ++a) c = p[a], l = i[a], (l == null || l.seriesIndex != c.seriesIndex || l.visible != c.visible || l.dataIndex != c.dataIndex || l.datapoint[0] != c.datapoint[0]) && (l != null && (h && t.unhighlight(l.series, l.datapoint), i[a] = null), c.visible && (i[a] = c, h && t.highlight(c.series, c.datapoint), w = n.plot.formatDate(new Date(c.datapoint[0]), "%b %d ", t.getOptions().xaxis.monthNames), monthDayPattern.length > 1 && monthDayPattern.indexOf("MMM") == -1 && (w = formatXaxis(c.datapoint[0])), n(f).text(w), n(u).filter('[index="' + a + '"]').html(v.yaxis.tickFormatter(c.datapoint[1], v.yaxis))));
                    r.css("position", "fixed").css("top", y.pageY + 10 + "px").css("left", y.pageX + 15 + "px")
                }), s.mouseout(function () {
                    for (var u, n = 0; n < i.length; ++n) u = i[n], u != null && (h && t.unhighlight(u.series, u.datapoint), i[n] = null);
                    r.hide()
                }), s.mouseover(function () {
                    var n = t.getData(),
                        i = !1,
                        u;
                    for (u in n)
                        if (n[u].data.length > 0) {
                            i = !0;
                            break
                        }
                    i && r.show()
                })
            }
        })
    }
    var t = {
        webmasterlegend: {
            enabled: !1,
            showPoints: !0
        }
    };
    n.plot.plugins.push({
        init: i,
        options: t,
        name: "webmasterlegend",
        version: "1.0"
    })
}(jQuery), $(document).ready(testForIE6),
function (n) {
    var i = function (n, t, i) {
        return {
            SiteUrl: n,
            MessageType: t,
            MessageId: i
        }
    }, t = function (n) {
            hideActivityIndicator(), reportError(n)
        }, u = function (n, r, u, f) {
            showActivityIndicator(!0);
            var e = i(n, r, u);
            getSvc().invoke("DeleteUserMessages", {
                messages: [e]
            }, function () {
                hideActivityIndicator(), f(n, r, u)
            }, t)
        }, f = function (n, i) {
            showActivityIndicator(!0), getSvc().invoke("DeleteUserMessages", {
                messages: n
            }, function () {
                hideActivityIndicator(), i(n)
            }, t)
        }, e = function (n, r, u, f) {
            showActivityIndicator(!0);
            var e = i(n, r, u);
            console.log(e), getSvc().invoke("ArchiveUserMessages", {
                messages: [e]
            }, function () {
                hideActivityIndicator(), f(n, r, u)
            }, t)
        }, o = function (n, i) {
            showActivityIndicator(!0), getSvc().invoke("ArchiveUserMessages", {
                messages: n
            }, function () {
                hideActivityIndicator(), i(n)
            }, t)
        }, s = function (n, r, u, f) {
            showActivityIndicator(!0);
            var e = i(n, r, u);
            getSvc().invoke("DeleteArchivedUserMessages", {
                messages: [e]
            }, function () {
                hideActivityIndicator(), f(n, r, u)
            }, t)
        }, h = function (n, i) {
            showActivityIndicator(!0), getSvc().invoke("DeleteArchivedUserMessages", {
                messages: n
            }, function () {
                hideActivityIndicator(), i(n)
            }, t)
        }, r = function (t, i, r, f, o, h) {
            showActivityIndicator(!0), n.post(n.validator.format(i, encodeURI(r), encodeURI(f), encodeURI(o)), function (i) {
                showPopupModalDialog("#messageDialog", i), hideActivityIndicator(), n("#messagearchive").click(function () {
                    n("#messageDialog").jqmHide().remove(), e(r, f, o, h)
                }), n("#messagedelete").click(function () {
                    n("#messageDialog").jqmHide().remove(), t ? s(r, f, o, h) : u(r, f, o, h)
                })
            })
        };
    n.fn.messages = function (t) {
        var s = typeof t == "string",
            e = Array.prototype.slice.call(arguments, 1);
        s && t == "show" && (e.splice(0, 0, !1), r.apply(null, e));
        var c = setupGridCheckButtonEnabler("table.wmtable", "#removeParam"),
            l = setupGridCheckButtonEnabler("table.wmtable", "#archiveParam"),
            a = function () {
                n(this).removeClass("messages-unread");
                var i = n(this).parent().parent().find(":checkbox");
                r(t.archive, t.messageDialogUrl, i.attr("siteurl"), i.attr("messagetype"), i.attr("messageid"), function () {
                    i.parent().parent().remove()
                })
            }, u = function (t) {
                var r = [],
                    u = n("table.wmtable :checked").parent().parent();
                n("table.wmtable :checked").each(function () {
                    var t = n(this),
                        u = i(t.attr("siteurl"), t.attr("messagetype"), t.attr("messageid"));
                    r.push(u)
                }), r.length > 0 && t(r, function () {
                    n(u).remove()
                })
            };
        n("table.wmtable tbody a").click(a), n("table.wmtable :checkbox").change(c).change(l), n("#filterSite").change(function () {
            n("#filters").submit()
        }), n("#filterType").change(function () {
            n("#filters").submit()
        }), t.archive ? n("#removeParam").click(function () {
            u(h)
        }) : (n("#removeParam").click(function () {
            u(f)
        }), n("#archiveParam").click(function () {
            u(o)
        })), t.showMessage != null && n.fn.messages("show", t.messageDialogUrl, "", t.showMessage, "")
    }
}(jQuery),
function (n) {
    n.fn.msnVideoGallery = function (t) {
        var i = {}, u, o = t.galleries,
            f = t.uuids,
            r = 0,
            s = !1,
            e = !1,
            h = 9e3;
        i.swapVideos = function (t, c, l) {
            var a, v;
            clearTimeout(u), u = setTimeout(i.swapVideos, h), r >= o.length && (r = 0), n("div.vBox").removeClass("selectedVBox").show(), $id("vGall" + r).addClass("selectedVBox"), n("div.tGall").hide(), $id("tGall" + r).show(), o[r] == "s" ? (a = $id("bImage"), a.html(f[r]), $id("bVideo").hide(), a.show()) : ($id("bVideo").show(), $id("bImage").hide(), v = n("#bVideo_content .flashInstall"), v.length > 0 ? $id("bVideo").hide() : typeof MsnVideo2 != "undefined" && (MsnVideo2.sendMessage({
                type: "loadVideo",
                param: {
                    uuid: f[r]
                }
            }), l && e && MsnVideo2.sendMessage({
                type: "playVideo"
            }), s || (MsnVideo2.addMessageReceiver({
                eventType: "playbackStatusChanged",
                funcCb: i.onPlaybackStatusChanged
            }), s = !0))), r++
        }, i.cancelSwap = function () {
            clearTimeout(u)
        }, i.changeSelection = function () {
            clearTimeout(u), r = n(this).attr("num"), i.swapVideos(!0, !0, !0)
        }, i.onPlaybackStatusChanged = function (n) {
            n.param.status.indexOf("video") > -1 && (i.cancelSwap(), e = !0)
        }, i.playVideo = function () {
            var u = document.location.href.indexOf("vid="),
                n, o, t;
            if (u > -1)
                for (u += 4, n = document.location.href.indexOf("?"), n = n == -1 ? document.location.href.length : n, o = document.location.href.substring(u, n), t = 0; t < f.length; t++)
                    if (f[t] == o) {
                        r = t, e = !0, i.swapVideos(!0, !0, !0);
                        break
                    }
        }, i.go = function () {
            $id("bVideo").click(i.cancelSwap), Msn.Video.createWidget("bVideo", "Player", 396, 223, {
                configName: "ToolboxA",
                configCsid: "ALTENUS_BingWebMaster",
                wmode: "transparent"
            }, "Player1", null, null, "http://img.widgets.video.s-msn.com"), n("div.vBox").click(i.changeSelection), u = setTimeout(i.swapVideos, 1e3), setTimeout(i.playVideo, 1100)
        }, i.go()
    }
}(jQuery),
function (n) {
    n.fn.msnPlayer = function () {}, n.fn.msnPlayer.CreateWidget = function (n, t) {
        Msn.Video.createWidget(n, "Player", 396, 223, {
            configName: "",
            configCsid: "ALTENUS_BingWebMaster",
            wmode: "transparent",
            "player.c": "v",
            "player.v": t
        }, "Player1", null, null, "http://img.widgets.video.s-msn.com")
    }
}(jQuery),
function (n) {
    function t() {
        function s() {
            for (var u = n('div.siteUrl[verified="True"]', n("tbody")), i = [], r = 0; r < u.length; r++) i.push(u[r].id), i.length >= 10 && (t.push(i), i = []);
            i.length > 0 && t.push(i), t.length > 0 && (t.reverse(), h())
        }

        function h() {
            var i = t.pop();
            n.ajax({
                url: r.KpiUrl,
                type: "POST",
                data: {
                    siteUrls: i,
                    startDate: n("#kpiPeriodSelectorFrom").val(),
                    endDate: n("#kpiPeriodSelectorTo").val()
                },
                traditional: !0,
                timeout: 13500,
                success: function (n) {
                    d(n)
                },
                error: reportError
            })
        }

        function d(n) {
            k(n), t.length > 0 && h()
        }

        function k(t) {
            n("tr", n("<div/>").html(t)).each(function () {
                var t = n(this),
                    r = t.attr("site"),
                    i = $id(r).parent().parent();
                n("td:gt(3)", i).remove(), i.append(t.children())
            }), e()
        }

        function w() {
            var i = n(this).parent(),
                t = n(".siteUrl", i);
            return t.length > 0 && (showActivityIndicator(!0), window.location.href = t.attr("verified") === "True" ? containerPrefix + "/home/dashboard/?url=" + t.attr("id") : containerPrefix + "/configure/verify/ownership/?url=" + t.attr("id")), !1
        }

        function g(t, i) {
            var u, r;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), u = {}, r = 0; r < i.length; ++r) u[i[r]] = !0;
            n("table.wmtable :checkbox").each(function () {
                u[n(this).attr("site")] && n(this).parent().parent().remove()
            }), triggerCheckboxChange(), c(), n("table.grid").trigger("update")
        }

        function c() {
            var t = n("#siteList table");
            t.attr("emptytitle", t.attr("oldemptytable")).attr("emptybutton", t.attr("oldemptybutton")).attr("emptytablesetup", ""), n("tr.empty", t).remove(), initEmptyTables()
        }

        function y(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function v(n) {
            console.log("removeData", n), showActivityIndicator(!0);
            var t = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/");
            t.invoke("RemoveSites", {
                url: n
            }, function (t) {
                g(t, n)
            }, y)
        }

        function a() {
            var t = n("div.metabox").parent().parent();
            t.attr("colspan", "4"), t.nextAll().detach()
        }

        function e() {
            n("a.cancelClick").click(cancelEvent), n("td:gt(0)", n("tr", "table.wmtable")).click(w), n("table.wmtable :checkbox").change(p)
        }

        function l() {
            var t = n("#siteSearch").val();
            t != f && (f = t, setTimeout(function () {
                if (f != i) {
                    i = f;
                    var t = n("#siteList table");
                    t.attr("oldemptytable") == null && t.attr("oldemptytable", t.attr("emptytitle")).attr("oldemptybutton", t.attr("emptybutton")), i.length > 0 ? (t.attr("emptytitle", n.validator.format(t.attr("nosearchresults"), n("<div/>").text(i).text())).attr("emptybutton", "").attr("emptytablesetup", ""), n("tr.empty", t).remove(), initEmptyTables()) : c(), u != null && u.abort(), u = n.ajax({
                        url: r.SiteSearchUrl,
                        type: "POST",
                        data: {
                            partial: i,
                            startDate: n("#kpiPeriodSelectorFrom").val(),
                            endDate: n("#kpiPeriodSelectorTo").val()
                        },
                        timeout: 13500,
                        success: b,
                        error: reportError
                    })
                }
            }, 200))
        }

        function b(t) {
            u = null, n("#siteList table tbody").html(t), n("table.grid").trigger("update"), n("#pagCont").hide(), triggerCheckboxChange(), e()
        }

        function nt() {
            var t = n("#compactView:checked").length > 0;
            n.cookie("cv", t, {
                expires: 365,
                path: containerPrefix
            }), t ? n("img.thumbnail").hide() : n("img.thumbnail").show()
        }
        var o = {}, r = null,
            it = "",
            tt = "",
            t = [],
            i = "",
            f = "",
            p = setupGridCheckButtonEnabler("table.wmtable", "#removeParam"),
            u;
        return o.go = function (t) {
            r = t, a(), n("#addParamForm").validate(noCleanValidateOptions), n("#compactView").change(nt), n("#siteSearch").keyup(l), e(), n("#removeParam").click(function () {
                var t = [];
                n("table.wmtable :checked").each(function () {
                    var i = n(this),
                        r = i.attr("site");
                    t.push(r)
                }), t.length > 0 && v(t)
            }), n("#addParam").click(function () {
                var t = n("#addParamForm");
                t.valid() && (showActivityIndicator(!0), fixPlaceholdersBeforeInFormSubmit(t), window.location = [r.AddSiteUrl, "&addurl=", encodeURIComponent(n("#addSite").val())].join(""))
            }), n("#kpiPeriodSelectorContainer").bind("dateChanged", function () {
                s()
            }), clickButtonOnEnter("#addParam", "#addSite"), s();
            var i = n.deparam.querystring();
            i.profile == 1 && n.fn.addSite.showProfileDialog()
        }, o
    }
    n.homeController = function () {
        return new t
    }
}(jQuery),
function (n) {
    function r(r, u) {
        var f, h, c, e, o, s, l;
        r.stopPropagation(), f = n("#siteSelectorHover"), h = u && u.callback, t != h && f.is(":visible") && i(), c = n(this).width(), u && u.width && (c = u.width), t = h, e = parseInt(c) + 70, o = parseInt(n("#siteGrid").css("max-width")), o > 0 && e > o && (e = o), e < 350 && (e = 350), f.css("min-width", e), f.toggle(), u ? f.offset(u.offset) : (s = n("#siteSelector").offset(), l = s.top + n("#siteSelector").height(), isRTL ? f.offset({
            right: s.right,
            top: l
        }) : f.offset({
            left: s.left,
            top: l
        })), n("#siteSelectorSearch").focus()
    }

    function i() {
        n("#siteSelectorHover").hide()
    }

    function u(n) {
        n.stopPropagation()
    }

    function f() {
        var t = n("#siteSelectorSearch").val();
        n("#siteGrid div").each(function () {
            var i = n(this);
            i.text().indexOf(t) > -1 ? i.parent().parent().show() : i.parent().parent().hide()
        })
    }

    function e(r) {
        var u = n(this);
        t ? (r.stopPropagation(), i(), t(u.attr("url"), u.text())) : n("#existingSite").text() != u.text() && (showActivityIndicator(!0), window.location = swapQueryString(window.location.href, "url", u.attr("url")))
    }

    function o() {
        n("#siteSelector").click(r), n("body").click(i), n("#siteSelectorSearch").keyup(f).click(u), n("#searchIcon").click(u), n("#siteGrid div").click(e)
    }
    var t = null;
    n.fn.siteSelector = function (n, t, i) {
        i ? r(t, i) : o()
    }
}(jQuery),
function (n) {
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
    n.fn.urlInputBox = function () {
        this.keyup(function () {
            var i = n(this);
            if (!t(i.val())) {
                if (isValueSameAsPalceHolder(i.val(), i)) return;
                i.val("http://" + i.val())
            }
        }), this.focusout(function () {
            var t = n(this);
            t.val().match(/\s+$/) != null && t.val(n.trim(t.val()))
        })
    }, n.fn.urlTextArea = function () {
        this.keyup(function () {
            var r = n(this),
                i, u, f, e;
            if (!isValueSameAsPalceHolder(r.val(), r)) {
                i = r.val().split("\n"), u = !1;
                for (f in i) e = i[f], t(e) || (u = !0, i[f] = "http://" + e);
                u && r.val(i.join("\n"))
            }
        })
    }
}(jQuery),
function (n) {
    n.fn.webmasterApi = function () {
        function i(t) {
            hideActivityIndicator(), unsetModalActivityIndicator(), n("#deleteKey").attr("disabled", !1), n("#apiKey").text(t)
        }

        function r() {
            showActivityIndicator(!0), getSvc().invoke("GenerateApiKey", {}, i, t)
        }

        function u() {
            hideActivityIndicator(), unsetModalActivityIndicator(), n("#deleteKey").attr("disabled", !0), n("#apiKey").text(wr.Api_NotGenerated)
        }

        function f() {
            showActivityIndicator(!0), getSvc().invoke("DeleteApiKey", {}, u, t)
        }
        var t = function (n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        };
        n("#generateKey").click(r), n("#deleteKey").click(f)
    }
}(jQuery),
function (n) {
    function t(t) {
        function i(t, i, r, u, f, e, o, s) {
            s = s || function () {}, n(u).live("click", function () {
                n(i).show(), n(u).hide(), n(e).focus()
            }), n(f).live("click", function () {
                if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode) {
                    var h = n.trim(n(e).val()),
                        c = isValueSameAsPalceHolder(h, n(e));
                    h.length > 0 && !c ? (n(f).attr("disabled", "disabled"), showActivityIndicator(!0), n.post(o, n(r).serialize(), function (i) {
                        n(t).html(i), showActivityIndicator(!1), clickButtonOnEnter(f, e), initInfoHovers(), disableFormSubmit(), n("input.urlValidation, input.wmUrl").urlInputBox(), s()
                    })) : (n(i).hide(), n(u).show())
                }
            }), clickButtonOnEnter(f, e), s()
        }

        function u() {
            var i = n("#diagnosticSubmit"),
                t = n("#diagnosticsSelector");
            t.val(""), n("#diagnosticWidgetForm").validate(formValidateOptions), i.click(function () {
                var u = n("#diagnosticsUrl").val(),
                    r = t.val(),
                    i;
                n("#diagnosticWidgetForm").valid() && (i = "", i = r.indexOf("diagnostics/link/explorer") != -1 ? n.validator.format("{0}&targeturl={1}", r, encodeURI(u)) : n.validator.format("{0}&paramurl={1}", r, encodeURI(u)), window.location = i)
            })
        }

        function f() {
            n("table.adCenterIntegration").adCenter(), n("#periodSelectorContainer").bind("dateChanged", function () {
                showActivityIndicator(!1), n.post(t.keywordTableUrl, {
                    startDate: n("#periodSelectorFrom").val(),
                    endDate: n("#periodSelectorTo").val()
                }, function (t) {
                    hideActivityIndicator(), n("#searchKeywordWidget").replaceWith(t), n("table.adCenterIntegration").adCenter(), initInfoHovers()
                })
            })
        }

        function e() {
            n("#messageswidget").click(function () {
                window.location = t.messagesUrl
            }), n("#messageswidget a").click(function (i) {
                jQuery.browser.msie && (i.cancelBubble = !0), i.stopPropagation(), n(this).removeClass("messages-unread");
                var r = n(this);
                n.fn.messages("show", t.messageDialogUrl, n(this).attr("siteurl"), n(this).attr("messagetype"), n(this).attr("messageid"), function () {
                    r.remove(), n("#messageswidget a").length == 0 && n("#messageswidget").remove()
                })
            })
        }

        function o() {
            window.location = t.blockUrlLink
        }

        function s() {
            n("#periodSelectorContainer").bind("dateChanged", function () {
                n.post(t.siteActivityWidgetUrl, {
                    startDate: n("#periodSelectorFrom").val(),
                    endDate: n("#periodSelectorTo").val()
                }, function (t) {
                    n("#siteactivitywidget").replaceWith(t), showSparklines(), initInfoHovers()
                })
            })
        }

        function h() {
            n('#sitemapwidget table.grid a[si="0"]').each(function () {
                var i = n(this),
                    r = n(":nth-child(3)", i.parent().parent().parent());
                n("body").sitemaps({
                    IndexCountUrl: t.sitemapsIndexCountUrl
                }, "getSitemapIndexCount", i.text(), r)
            })
        }
        var r = {};
        return r.go = function () {
            n("#blockUrlsPageLoad").click(o), i("#sitemapwidget", "#weSitemap", "#addswform", "#weSitemapSubmit1", "#weSitemapSubmit2", "#addSitemap", containerPrefix + "/configure/sitemaps/addwidget?url=" + encodeURI(url), h), u(), i("#ignoreTheseParameterswidget", "#weIgnoreTheseParameters", "#addqpform", "#weIgnoreParametersSubmit1", "#weIgnoreParametersSubmit2", "#addIgnoredParameter", containerPrefix + "/configure/ignore/addwidget?url=" + encodeURI(url)), i("#submiturlswidget", "#weSubmitUrls", "#addsuwform", "#weSubmitUrlsSubmit1", "#weSubmitUrlsSubmit2", "#submitUrl", t.addSubmitUrlsWidget), e(), f(), s()
        }, r
    }
    n.widgetController = function (n) {
        return new t(n)
    }
}(jQuery),
function (n) {
    function t() {
        function u(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function l(n) {
            u(n)
        }

        function a(u) {
            var f, o, s;
            hideActivityIndicator(), unsetModalActivityIndicator(), n("table.wmtable :checkbox").each(function () {
                n(this).attr("qp") == u.Url && n(this).parent().parent().remove()
            }), f = "", o = "", o = u.EntityType == e ? wr.G_Directory : wr.G_Page, f = u.RequestType == t ? wr.BlockUrls_Cache : wr.BlockUrls_URLAndCache, s = n("<tr/>"), s.append(n('<td class="width20"/>').append(n("<input>", {
                type: "checkbox",
                qp: u.Url,
                et: u.EntityType,
                bt: u.RequestType
            })).append(n("<label/>"))).append(n('<td class="leftalign" />').append(createExternalUrl(u.Url))).append(n("<td/>").text(o)).append(n("<td/>").text(f)).append(n("<td/>").text(u.Email)).append(n("<td/>").text(apCfg.RemovedUrlsProcessor.UrlRemovalExpirationDeadline)).append(n("<td/>").text(u.DateFormatted)), n("#urlRemovalList tbody").prepend(s), n("#inputBlockUrl").val(""), triggerCheckboxChange(), n("table.wmtable :checkbox").change(i), n("table.wmtable :checkbox").change(r), n("#urlRemovalList table.wmtable").trigger("update")
        }

        function s(n, i, r) {
            var u, f;
            console.log("blockUrl", n, i), blockTypePermOrTemp = o, u = c, r && (u = t), showActivityIndicator(!0), f = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/"), f.invoke("SubmitUrlRemoval", {
                url: url,
                urlRemoval: {
                    Url: n,
                    EntityType: i,
                    RequestType: u,
                    BlockTypePermOrTemp: blockTypePermOrTemp
                }
            }, a, l)
        }

        function v(u) {
            var o, f, c;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), o = {}, f = 0; f < u.length; ++f) o[u[f].Url] = !0;
            for (n("table.wmtable :checkbox").each(function () {
                o[n(this).attr("qp")] && n(this).parent().parent().remove()
            }), f = 0; f < u.length; f++) {
                var l = "",
                    s = "",
                    h = "";
                h = u[f].EntityType == e ? wr.G_Directory : wr.G_Page, s = u[f].RequestType == t ? wr.BlockUrls_Cache : wr.BlockUrls_URLAndCache, c = n("<tr/>"), c.append(n('<td class="width20"/>').append(n("<input>", {
                    type: "checkbox",
                    qp: u[f].Url,
                    et: u[f].EntityType,
                    bt: u[f].RequestType
                })).append(n("<label/>"))).append(n('<td class="leftalign" />').append(createExternalUrl(u[f].Url))).append(n("<td/>").text(h)).append(n("<td/>").text(s)).append(n("<td/>").text(u[f].Email)).append(n("<td/>").text(apCfg.RemovedUrlsProcessor.UrlRemovalExpirationDeadline)).append(n("<td/>").text(u[f].DateFormatted)), n("#urlRemovalList tbody").prepend(c)
            }
            n("table.wmtable :checkbox").change(i), n("table.wmtable :checkbox").change(r), triggerCheckboxChange(), n("#urlRemovalList table.wmtable").trigger("update")
        }

        function y(t) {
            var r, i;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), r = {}, i = 0; i < t.length; ++i) r[t[i].Url] = !0;
            n("table.wmtable :checkbox").each(function () {
                r[n(this).attr("qp")] && n(this).parent().parent().remove()
            }), triggerCheckboxChange(), n("#urlRemovalList table.wmtable").trigger("update")
        }

        function p(n, t) {
            for (var r = [], e, f, i = 0; i < n.length; i++) e = {
                Url: n[i].urlBlocked,
                RequestType: n[i].blockType,
                EntityType: n[i].entityType,
                BlockTypePermOrTemp: o
            }, r.push(e);
            showActivityIndicator(!0), f = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/"), t ? f.invoke("ReverseUrlRemovals", {
                url: url,
                urlList: r
            }, y(r), u) : f.invoke("ExtendTempUrlRemovals", {
                url: url,
                urlList: r
            }, v, u)
        }

        function h(t) {
            var i = [];
            n("table.wmtable :checked").each(function () {
                var t = n(this),
                    r = t.attr("qp"),
                    u = t.attr("et"),
                    f = t.attr("bt"),
                    e = {
                        urlBlocked: r,
                        entityType: u,
                        blockType: f
                    };
                i.push(e)
            }), i.length > 0 && p(i, t)
        }
        var f = {}, w = 0,
            e = 1,
            c = 1,
            t = 0,
            o = 0,
            i = setupGridCheckButtonEnabler("table.wmtable", "#extendBlock"),
            r = setupGridCheckButtonEnabler("table.wmtable", "#stopBlocking");
        return f.go = function () {
            n("table.wmtable :checkbox").change(i), n("table.wmtable :checkbox").change(r), n("#submitBlockUrl").click(function () {
                if ($id("blockUrlForm").valid()) {
                    var t = n("#entitySelected").val(),
                        i = n("#inputBlockUrl").val(),
                        r = !1;
                    s(i, t, r)
                }
            }), n("#submitBlockUrlCache").click(function () {
                if ($id("blockUrlForm").valid()) {
                    var t = n("#entitySelected").val(),
                        i = n("#inputBlockUrl").val(),
                        r = !0;
                    s(i, t, r)
                }
            }), n("#stopBlocking").click(function () {
                var n = !0;
                h(n)
            }), n("#extendBlock").click(function () {
                var n = !1;
                h(n)
            }), $id("blockUrlForm").validate(formValidateOptions)
        }, f
    }
    n.blockUrlsController = function () {
        return new t
    }
}(jQuery),
function (n) {
    n.fn.crawlControl = function (t) {
        function y(n) {
            r(), reportError(n)
        }
        for (var f = new Date, e = (24 + getLocalTimezoneOffset(f)) % 24, s = n(".crawl-control-chart > div:first-child"), i = t.crawlRate, r = function () {
                window.readOnlyMode || n("#save").attr("disabled", !1)
            }, a = function () {
                n("#preset").val() != "" && n("#preset").val(""), r()
            }, v = function (n) {
                if (n != null) {
                    i = [];
                    for (var t = 0; t < 24; ++t) i[t] = n[(24 + t - e) % 24][1];
                    r()
                }
            }, h = function (i) {
                for (var h = [], o, u, c, l, y, r = 0; r < 24; ++r) h.push([parseInt(r), i[(r + e) % 24]]);
                for (o = [], r = 0; r < 11; ++r) o[r] = [r, ""];
                for (u = [], r = 0; r <= 24; ++r) c = r % (t.has24Hours ? 24 : 12), u[r] = [r, c.toString()];
                !t.has24Hours && t.AMDesignator.length > 0 && (u[0] = [0, '<span class="nowrap crawl-control-ampm">12&nbsp;' + t.AMDesignator + "</span>"], u[24] = [24, '<span class="nowrap crawl-control-ampm">12&nbsp;' + t.AMDesignator + "</span>"]), !t.has24Hours && t.PMDesignator.length > 0 && (u[12] = [12, '<span class="nowrap crawl-control-ampm">12&nbsp;' + t.PMDesignator + "</span>"]), l = function () {
                    var t = [];
                    return t.push({
                        xaxis: {
                            from: f.getHours(),
                            to: f.getHours() + 1
                        }
                    }), t
                }, y = {
                    colors: [window.readOnlyMode ? "#adadad" : "#71ccee"],
                    xaxis: {
                        mode: "number",
                        min: 0,
                        max: 24,
                        minTickSize: 1,
                        tickSize: 1,
                        tickFormatter: function (n) {
                            return formatNumber(n)
                        },
                        ticks: u
                    },
                    yaxis: {
                        mode: "number",
                        min: 0,
                        max: 10,
                        minTickSize: 1,
                        tickSize: 1,
                        tickFormatter: function (n) {
                            return formatNumber(n)
                        },
                        ticks: o
                    },
                    series: {
                        lines: {
                            show: !1,
                            fill: !1
                        },
                        bars: {
                            show: !0,
                            lineWidth: 0,
                            barWidth: 1,
                            fill: !0,
                            fillColor: window.readOnlyMode ? "#adadad" : "#00adee",
                            align: "left"
                        }
                    },
                    grid: {
                        tickColor: "#ffffff",
                        backgroundColor: "#e6e6e6",
                        borderWidth: 0,
                        markings: l,
                        markingsColor: "rgba(100,100,100,0.2)",
                        aboveData: !0
                    },
                    webmastercrawldelay: {
                        enabled: !window.readOnlyMode && !t.widget,
                        onChange: v,
                        onChangeBegin: a,
                        highlightRadius: 2,
                        autoHighlight: !1
                    }
                }, n.plot(s, [h], y)
            }, c = function () {
                n("#save").attr("disabled", "disabled");
                var r = {
                    Site: url,
                    CrawlBoostAvailable: t.boostAvailable,
                    CrawlBoostEnabled: !! n("#crawlboost").is(":checked"),
                    CrawlRate: i
                };
                getSvc().invoke("SaveCrawlSettings", {
                    url: url,
                    settings: r
                }, function () {}, y)
            }, p = function () {
                var u, r, t;
                if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode) {
                    for (u = !1, r = [], t = 0; t < 24; ++t) r[t] = t < i.length && i[t] != null ? i[t] : 5, r[t] > 3 && (u = !0);
                    if (i = r, !u) {
                        n("#forceSaveDialog").jqm({
                            overlay: 10,
                            modal: !0
                        }).jqmShow();
                        return
                    }
                    c(), n("#save").attr("disabled", "disabled")
                }
            }, w = function () {
                var f, u, t;
                if (n(this).val() != "") {
                    for (f = n(this).val().split(","), u = [], t = 0; t < 24; ++t) u[t] = parseInt(f[(24 + t - e) % 24]);
                    i = u, h(u), r()
                }
            }, l = [], o, u = 0; u < 24; ++u) l[u] = parseInt(t.crawlRate[(24 + u + e) % 24]);
        o = l.join(","), n("#preset").find("option").each(function () {
            n(this).val() == o && n("#preset").val(o)
        }), n("#localtime").text(f.toLocaleTimeString()), h(t.crawlRate), n("#preset").change(w), n("#crawlboost").change("change", r), n("#save").click(p), n("#forcesave").click(function () {
            n("#forceSaveDialog").jqmHide(), c()
        }), t.widget && s.click(function () {
            window.location = t.crawlControlUrl
        })
    }
}(jQuery),
function (n) {
    function t() {
        function c(n, i) {
            b(), t.SiteUrl = n, t.All = i
        }

        function b() {
            n("#submitDeepLinkBlock").click(o), n("#extendBlock").click(s), n("#removeBlock").click(k), n("#blockDeepLinkForm").validate(formValidateOptions), i()
        }

        function i() {
            n("table.wmtable :checkbox").change(w), n("table.wmtable :checkbox").change(l)
        }

        function o() {
            var f, i, e, o, r, s;
            n("#blockDeepLinkForm").valid() && (f = n("#inputDeepLink").val(), i = n("#inputParentLink").val(), e = n("#dropdownMarket").val(), i || (i = t.All), o = {
                DeepLinkUrl: f,
                SearchUrl: i,
                Market: e,
                ExpiryDate: new Date
            }, r = [], r.push(o), s = {
                deepLinks: JSON.stringify(r)
            }, u(s, v))
        }

        function s() {
            var n = f(),
                t = {
                    deepLinks: JSON.stringify(n)
                };
            u(t, y)
        }

        function k() {
            var n = f(),
                t = {
                    deepLinks: JSON.stringify(n)
                };
            a(t, p)
        }

        function f() {
            var t = [];
            return n("table.wmtable :checkbox:checked").each(function () {
                var i = {
                    DeepLinkUrl: n(this).attr("dl"),
                    SearchUrl: n(this).attr("bu"),
                    Market: n(this).attr("bmc")
                };
                t.push(i)
            }), t
        }

        function u(n, i, r) {
            var u = "/webmaster/configure/DeepLink/UpdateLinkBlock?url=" + t.SiteUrl;
            startPostRequestWithJson(n, u, i, r, "#blockDeepLinkForm")
        }

        function a(n, i, r) {
            var u = "/webmaster/configure/DeepLink/DeleteLinkBlock?url=" + t.SiteUrl;
            startPostRequestWithJson(n, u, i, r, "#blockDeepLinkForm")
        }

        function v(r) {
            var u, f;
            hideActivityIndicator(), unsetModalActivityIndicator(), r && r.length == 1 && (u = r[0].SearchUrl, u = u && u !== t.All ? createExternalUrl(r[0].SearchUrl) : wr.G_All, f = n("<tr/>"), f.append(n('<td class="width20"/>').append(n("<input>", {
                type: "checkbox",
                dl: r[0].DeepLinkUrl,
                bu: r[0].SearchUrl,
                bmc: r[0].Market
            })).append("<label>")).append(n('<td class="leftalign deepLinkUrl" />').append(createExternalUrl(r[0].DeepLinkUrl))).append(n('<td class="leftalign blockedUrl" />').append(u)).append(n('<td class="blockedMarket"/>').text(r[0].MarketFormatted)).append(n('<td class="daysToExpire"/>').text(r[0].DaysToExpireFormatted)).append(n('<td class="blockedDate"/>').text(r[0].SubmitDateFormatted)), n("#deepLinkBlockList tbody").prepend(f), h(), i(), n("#deepLinkBlockList table.wmtable").trigger("update"))
        }

        function y(t) {
            var u, o, f;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), o = t.length, u = 0; u < o; u++) f = r(t[u].DeepLinkUrl, t[u].SearchUrl, t[u].Market), n(f.find(".blockedDate")[0]).text(t[u].SubmitDateFormatted), n(f.find(".daysToExpire")[0]).text(t[u].DaysToExpireFormatted);
            i(), e(), n("#deepLinkBlockList table.wmtable").trigger("update")
        }

        function p(t) {
            var u, f, o;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), f = t.length, u = 0; u < f; u++) o = r(t[u].DeepLinkUrl, t[u].SearchUrl, t[u].Market), n(o).remove();
            i(), e(), n("#deepLinkBlockList table.wmtable").trigger("update")
        }

        function r(t, i, r) {
            var u = ["table.wmtable :checkbox[dl='", t, "'][bu='", i, "'][bmc='", r, "']"];
            return n(u.join("")).parent().parent()
        }

        function h() {
            n("#inputDeepLink").val(""), n("#inputParentLink").val(""), n("#dropdownMarket").val(0), enableHintText()
        }

        function e() {
            n("table.wmtable :checkbox:checked").each(function () {
                n(this).attr("checked", !1)
            }), triggerCheckboxChange()
        }
        var t = {
            SiteUrl: "",
            All: "all"
        }, w = setupGridCheckButtonEnabler("table.wmtable", "#extendBlock"),
            l = setupGridCheckButtonEnabler("table.wmtable", "#removeBlock");
        return t.initialize = c, t
    }
    n.blockDeepLinksController = function () {
        return new t
    }
}(jQuery),
function (n) {
    n.fn.disavowSelector = function () {
        function t(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function e(t) {
            var i = {};
            i[t.DisavowedUrl] = !0, n("table.wmtable :checkbox").each(function () {
                i[n(this).attr("qp")] && t.EntityType == n(this).attr("et") && n(this).parent().parent().remove()
            })
        }

        function o(t) {
            var f, o;
            hideActivityIndicator(), unsetModalActivityIndicator(), e(t), f = "", t.EntityType == i ? f = wr.G_Page : t.EntityType == r ? f = wr.G_Directory : t.EntityType == u && (f = wr.G_Domain), o = "", o = readOnlyMode ? n.validator.format('<tr><td><span class="url-text">{0}</span></td><td>{1}</td><td>{2}</td></tr>', t.DisavowedUrl, f, t.DateFormatted) : n.validator.format('<tr><td><input type="checkbox" qp="{0}" et="{1}" ><label></label></td><td>{2}</td><td><span class="url-text">{3}</span></td><td>{4}</td></tr>', t.DisavowedUrl, t.EntityType, t.DisavowedUrl, f, t.DateFormatted), n("#urlDisavowList tbody").prepend(o), n("#inputDisavowUrl").val(""), triggerCheckboxChange(), n("#urlDisavowList table.grid").trigger("update")
        }

        function s(n, i) {
            console.log("disavowUrl", n, i), showActivityIndicator(!0);
            var r = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/");
            r.invoke("AddDisavowedLink", {
                url: url,
                disavowUrl: n,
                entityType: i
            }, o, t)
        }

        function h(t, i) {
            var u, f, r;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), u = {}, f = {}, r = 0; r < t.length; ++r) f[t[r]] = !0, u[t[r]] = i[r];
            n("table.wmtable :checkbox").each(function () {
                f[n(this).attr("qp")] && u[n(this).attr("qp")] == n(this).attr("et") && n(this).parent().parent().remove()
            }), triggerCheckboxChange(), n("#urlDisavowList table.grid").trigger("update")
        }

        function c(n, i) {
            console.log("stopdisavow", n, i), showActivityIndicator(!0);
            var r = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/");
            r.invoke("RemoveDisavowLink", {
                url: url,
                urlsToProcess: n,
                entitiesToProcess: i
            }, h(n, i), t)
        }

        function l() {
            n("#urlDisavowList table.wmtable :checkbox").live("change", f), n("#disavowUrlButton").click(function () {
                if ($id("disavowUrlForm").valid()) {
                    var t = n("#entitySelected").val(),
                        i = n("#inputDisavowUrl").val();
                    s(i, t)
                }
            }), n("#stopDisavowing").click(function () {
                var t = [],
                    i = [];
                n("table.wmtable :checked").each(function () {
                    var r = n(this),
                        u = r.attr("qp"),
                        f = r.attr("et");
                    t.push(u), i.push(f)
                }), t.length > 0 && i.length > 0 && c(t, i)
            }), $id("disavowUrlForm").validate(formValidateOptions), clickButtonOnEnter("#disavowUrlButton", "#inputDisavowUrl")
        }
        var a = {}, i = 0,
            r = 1,
            u = 2,
            f = setupGridCheckButtonEnabler("#urlDisavowList table.wmtable", "#stopDisavowing");
        l()
    }
}(jQuery),
function (n) {
    n.fn.geotargeting = function () {
        function u(t) {
            console.log("addParamSuccess", t), hideActivityIndicator(), unsetModalActivityIndicator(), i(), n("#url").val(""), n("#url").prop("readonly", !1), n("#paramList table.wmtable input[type=checkbox]").each(function () {
                n(this).attr("url") == t.Url && n(this).attr("urltype") == t.Type && n(this).parent().parent().remove()
            });
            var r = n("<tr/>");
            r.append(n("<td/>").append(n("<input>", {
                type: "checkbox",
                url: t.Url,
                urltype: t.Type
            })).append(n("<label/>"))).append(n("<td/>").append(n("<span>", {
                "class": "url-text"
            }).text(t.Url))).append(n("<td/>").text(t.TypeFormatted)).append(n("<td/>").text(t.Region)).append(n("<td/>").text(t.DateFormatted)), n("#paramList table.wmtable tbody").prepend(r), n("#paramList table.wmtable").trigger("update")
        }

        function f(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), reportError(n)
        }

        function e(i, r) {
            var f, u;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), f = {}, u = 0; u < r.length; ++u) f[r[u].Url + "|" + r[u].Type] = !0;
            n("#paramList table.wmtable input[type=checkbox]").each(function () {
                var t = n(this).attr("url") + "|" + n(this).attr("urltype");
                f[t] && n(this).parent().parent().remove()
            }), t(), n("#paramList table.wmtable").trigger("update")
        }

        function o(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }
        var t = setupGridCheckButtonEnabler("#paramList table.wmtable", "#removeParam"),
            i = function () {
                n("#country").prop("disabled", !1), n("#url").prop("disabled", !1), n("#urltype").prop("disabled", !1), n("#addParam").prop("disabled", !1)
            }, r = function () {
                n("#country").prop("disabled", !0), n("#url").prop("disabled", !0), n("#urltype").prop("disabled", !0), n("#addParam").prop("disabled", !0)
            };
        n("#addParam").click(function () {
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode && n("#addParamForm").valid()) {
                var t = {
                    Url: n("#url").val(),
                    Type: n("#urltype").val(),
                    Region: n("#country").val()
                };
                showActivityIndicator(!0), r(), getSvc().invoke("AddCountryRegionSettings", {
                    url: url,
                    settings: t
                }, u, f)
            }
        }), n("#removeParam").click(function () {
            var t = [];
            n("#paramList table.wmtable :checked").each(function () {
                var i = n(this),
                    r = {
                        Url: i.attr("url"),
                        Type: i.attr("urltype")
                    };
                t.push(r)
            }), console.log(t), t.length > 0 && (showActivityIndicator(!0), getSvc().invoke("RemoveCountryRegionSettings", {
                url: url,
                settings: t
            }, function (n) {
                e(n, t)
            }, o))
        }), n("#addParamForm").validate(formValidateOptions), n("#paramList table.wmtable :checkbox").live("change", t), clickButtonOnEnter("#addParam", ["#url"]), n("#urltype").change(function () {
            n(this).val() == "2" ? (n("#url").val(url), n("#url").prop("readonly", !0)) : n(this).val() == "3" && n('#urltype option[value="3"]').attr("locked") == "1" ? (n("#url").val(url), n("#url").prop("readonly", !0)) : n("#url").prop("readonly", !1)
        })
    }
}(jQuery),
function (n) {
    function t() {
        function h(t) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i && ($id(t.Param).remove(), i = !1);
            var r = "";
            r = readOnlyMode ? n.validator.format("<tr><td>{0}</td><td>{1}</td></tr>", t.Param, t.DateFormatted) : n.validator.format('<tr><td><input type="checkbox" qp="{0}"><label></label></td><td>{1}</td><td>{2}</td></tr>', t.Param, t.Param, t.DateFormatted), n("#qpList tbody").prepend(r), n("#inputQueryParam").val(""), n("#qpList table.grid").trigger("update"), triggerCheckboxChange(), n("table.wmtable :checkbox").change(u)
        }

        function c(n) {
            i = !1, o(n)
        }

        function e(t) {
            if (!(t.length < 1)) {
                if (t.length > f) {
                    reportError({
                        Message: n.validator.format(wr.QueryParam_CharLimit, f)
                    });
                    return
                }
                showActivityIndicator(!0);
                var i = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/");
                i.invoke("AddQueryParameter", {
                    url: url,
                    queryParam: t
                }, h, c)
            }
        }

        function l(t, i) {
            var u, r;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), u = {}, r = 0; r < i.length; ++r) u[i[r]] = !0;
            n("table.wmtable :checkbox").each(function () {
                u[n(this).attr("qp")] && n(this).parent().parent().remove()
            }), triggerCheckboxChange(), n("#qpList table.grid").trigger("update")
        }

        function o(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function a(n) {
            o(n)
        }

        function v(n) {
            console.log("removeQueryParam", n), showActivityIndicator(!0);
            var t = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/");
            t.invoke("RemoveQueryParameters", {
                url: url,
                queryParameters: n
            }, function (t) {
                l(t, n)
            }, a)
        }

        function s(n) {
            i = !0, e(n)
        }

        function y(t) {
            jQuery.browser.msie && (t.cancelBubble = !0), t.stopPropagation(), n("#spanForDisabledQpHide").show(100).delay(200)
        }
        var r = {}, t = [],
            i = !1,
            u = setupGridCheckButtonEnabler("table.wmtable", "#removeParam"),
            f = 100;
        return r.go = function () {
            n("table.wmtable :checkbox").change(u), n("#more").click(y), n(document).click(function () {
                n("#spanForDisabledQpHide").hide()
            }), n("#submitQueryParam").click(function () {
                if ($id("ignoreParaForm").valid()) {
                    var t = n("#inputQueryParam").val();
                    e(t)
                }
            }), n("#spanForDisabledQp a").click(function () {
                var t = n(this).attr("id");
                s(t)
            }), n("#spanForDisabledQpHide a").click(function () {
                var t = n(this).attr("id");
                s(t)
            }), n("#removeParam").click(function () {
                t = [], n("table.wmtable :checked").each(function () {
                    var i = n(this),
                        r = i.attr("qp");
                    t.push(r)
                }), t.length > 0 && v(t)
            }), clickButtonOnEnter("#submitQueryParam", "#inputQueryParam"), $id("ignoreParaForm").validate(formValidateOptions)
        }, r
    }
    n.ignoretheseparametersController = function () {
        return new t
    }
}(jQuery),
function (n) {
    n.fn.sitemaps = function (t, i) {
        function p(t) {
            for (var r = {}, i = 0; i < t.length; ++i) r[t[i].Url] = !0;
            n("#sitemapsTable table.grid input[type=checkbox]").each(function () {
                r[n(this).attr("url")] && n(this).parent().parent().remove()
            }), triggerCheckboxChange()
        }

        function e(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), f(), reportError(n)
        }

        function y(t, i) {
            hideActivityIndicator(), unsetModalActivityIndicator(), f(), p(n.map(i, function (n) {
                return {
                    Url: n
                }
            })), n("table.grid").trigger("update")
        }

        function w(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n), f()
        }

        function o(i, r, u) {
            var f = {};
            f.updateEl = i, f.indexUrl = r, f.retryCount = u, n.ajax({
                url: [t.IndexCountUrl, "&indexUrl=", escape(r)].join(""),
                type: "GET",
                dataType: "text",
                timeout: 13500,
                context: f,
                success: k,
                error: b
            })
        }

        function b() {
            this.retryCount > 4 ? this.updateEl.html([wr.G_Unknown, '<span class="none">', 0, "</span>"].join("")) : (u.push([this.updateEl, this.indexUrl, this.retryCount + 1]), h())
        }

        function k(n) {
            if (n != null) {
                var t = JSON.parse(n);
                this.updateEl.html([t.Formatted, '<span class="none">', t.Count, "</span>"].join(""))
            }
            h()
        }

        function h() {
            if (u.length > 0) {
                var n = u.pop();
                o(n[0], n[1], n[2])
            } else s = 0
        }

        function d() {
            var t, i, u;
            n(this).attr("disabled") == "disabled" || window.readOnlyMode || (t = n("#addParamForm"), t.valid() && (i = t.serialize(), showActivityIndicator(!0), r(), u = n("#addSitemap").val(), n.post(t.attr("target"), i, function (t) {
                n("div.tableheaderbox").replaceWith(t), showActivityIndicator(!1), c();
                var i = n("#submittedFeed tbody").html();
                p([{
                    Url: u
                }]), n("#sitemapsTable table.grid tbody").prepend(i), n("#sitemapsTable table.grid").trigger("update"), constrainCheckboxWidth()
            }).error(e)))
        }

        function g() {
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode) {
                var i = getCheckedItemsInTable("url");
                i.length > 0 && (showActivityIndicator(!0), r(), n.ajax({
                    url: t.RemoveUrl,
                    type: "POST",
                    data: {
                        feedUrls: i
                    },
                    traditional: !0,
                    timeout: 13500,
                    success: function (n) {
                        y(n, i)
                    },
                    error: w
                }))
            }
        }

        function nt() {
            n(this).attr("disabled") == "disabled" || window.readOnlyMode || (showActivityIndicator(!0), n.post(t.ResubmitIndexUrl, function (t) {
                n("#sitemapsTable").html(t), showActivityIndicator(!1)
            }).error(e))
        }

        function tt() {
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode) {
                var i = getCheckedItemsInTable("url");
                i.length > 0 && (r(), showActivityIndicator(!0), n.ajax({
                    url: t.ResubmitUrl,
                    type: "POST",
                    data: {
                        feedUrls: i
                    },
                    traditional: !0,
                    success: function (t) {
                        n("#sitemapsTable").html(t), initInfoHovers(), enablePagination(), enableAjaxSorting(), disableFormSubmit(), n("input.urlValidation, input.wmUrl").urlInputBox(), window.sitemapsController != null && window.sitemapsController.getAccurateSitemapIndexCounts(), hideActivityIndicator(), unsetModalActivityIndicator(), f(), n("table.grid").trigger("update")
                    },
                    error: e
                }))
            }
        }

        function l() {
            n('#sitemapsTable table.grid span[si="0"]').each(function () {
                var i = n(this),
                    f = t.Detail ? ":nth-child(3)" : ":nth-child(4)",
                    r = n(f, i.parent().parent());
                s < 4 ? (o(r, i.attr("url"), 0), s++) : u.push([r, i.attr("url"), 0])
            })
        }

        function c() {
            clickButtonOnEnter("#addParam", "#addSitemap"), n("#addParam").click(d), n("#addParamForm").validate(noCleanValidateOptions), window.readOnlyMode && r(), disableFormSubmit(), n("input.urlValidation, input.wmUrl").urlInputBox()
        }

        function rt() {
            n("#removeParam").click(g), n("#resubmitParam").click(tt), n("#resubmitIndex").click(nt), n("#sitemapsTable table.grid :checkbox").live("change", it).live("change", ut), n("#sitemapsTable table.grid").parent().bind("onsort", function () {
                initInfoHovers(), l()
            }), c()
        }
        var it = setupGridCheckButtonEnabler("#sitemapsTable table.grid", "#removeParam"),
            ut = setupGridCheckButtonEnabler("#sitemapsTable table.grid", "#resubmitParam"),
            u = [],
            s = 0,
            f = function () {
                n("#addSitemap").attr("disabled", null), n("#addParam").attr("disabled", null);
                var t = n("#sitemapsTable table.grid :checked").length > 0;
                n("#resubmitParam").attr("disabled", t ? null : "disabled"), n("#removeParam").attr("disabled", t ? null : "disabled")
            }, r = function () {
                n("#addSitemap").attr("disabled", "disabled"), n("#addParam").attr("disabled", "disabled"), n("#resubmitParam").attr("disabled", "disabled"), n("#removeParam").attr("disabled", "disabled")
            }, a, v;
        return (this.getAccurateSitemapIndexCounts = l, i == "getSitemapIndexCount") ? (a = arguments[2], v = arguments[3], o(v, a, 0), this) : (rt(), this.getAccurateSitemapIndexCounts(), this)
    }
}(jQuery),
function (n) {
    n.fn.submiturls = function () {
        function r(t) {
            for (var r = {}, i = 0; i < t.length; ++i) r[t[i].Url] = !0;
            n("#paramList table.wmtable input[type=checkbox]").each(function () {
                r[n(this).attr("url")] && n(this).parent().parent().remove()
            })
        }

        function u(i) {
            var e, u, f;
            hideActivityIndicator(), unsetModalActivityIndicator(), t(), n("#urls").val(""), r(i);
            for (e in i) u = i[e], f = n("<tr/>"), f.append(n('<td class="width20"/>').append(n("<input>", {
                type: "checkbox",
                url: u.Url
            })).append(n("<label/>"))).append(n('<td class="leftalign" />').append(createExternalUrl(u.Url))).append(n("<td/>").text(u.DateFormatted)), n("#paramList table.wmtable tbody").prepend(f);
            n("#dailyQuota").text(parseInt(n("#dailyQuota").text()) - i.length), n("#monthlyQuota").text(parseInt(n("#monthlyQuota").text()) - i.length), n("#paramList table.wmtable").trigger("update")
        }

        function f(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), t();
            var i = null;
            n.Message != null && (i = n.Message.replace(/\n/g, "<br/>")), showErrorMessage(i, wr.G_Error, !0)
        }

        function s(i, u) {
            hideActivityIndicator(), unsetModalActivityIndicator(), t(), r(n.map(u, function (n) {
                return {
                    Url: n
                }
            })), n("#paramList table.wmtable").trigger("update")
        }

        function h(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n), t()
        }
        var e = setupGridCheckButtonEnabler("#paramList table.wmtable", "#removeParam"),
            o = setupGridCheckButtonEnabler("#paramList table.wmtable", "#resubmitParam"),
            t = function () {
                n("#urls").attr("disabled", null), n("#addParam").attr("disabled", null);
                var t = n("#paramList table.wmtable :checked").length > 0;
                n("#resubmitParam").attr("disabled", t ? null : "disabled"), n("#removeParam").attr("disabled", t ? null : "disabled")
            }, i = function () {
                n("#urls").attr("disabled", "disabled"), n("#addParam").attr("disabled", "disabled"), n("#resubmitParam").attr("disabled", "disabled"), n("#removeParam").attr("disabled", "disabled")
            };
        n("#addParam").click(function () {
            var r, e, t, o;
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode && n("#addParamForm").valid()) {
                for (r = n("#urls").val().split("\n"), e = [], t = 0; t < r.length; ++t) o = n.trim(r[t]), o != "" && e.push(o);
                showActivityIndicator(!0), i(), getSvc().invoke("SubmitUrls", {
                    url: url,
                    urlList: e
                }, u, f)
            }
        }), n("#resubmitParam").click(function () {
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode) {
                var t = getCheckedItemsInTable("url");
                t.length > 0 && (showActivityIndicator(!0), i(), getSvc().invoke("SubmitUrls", {
                    url: url,
                    urlList: t
                }, u, f))
            }
        }), n("#removeParam").click(function () {
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode) {
                var t = getCheckedItemsInTable("url");
                t.length > 0 && (showActivityIndicator(!0), i(), getSvc().invoke("RemoveSubmittedUrls", {
                    url: url,
                    urlList: t
                }, function (n) {
                    s(n, t)
                }, h))
            }
        }), n("#addParamForm").validate(n.merge({
            onkeyup: !1,
            focusCleanup: !0,
            errorPlacement: function () {}
        }, formValidateOptions)), n("#paramList table.wmtable :checkbox").live("change", e).live("change", o), window.readOnlyMode && i(), n("#urls").urlTextArea()
    }
}(jQuery),
function (n) {
    n.fn.users = function () {
        function u(t) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), n("#email").val("");
            var u = t.Expired ? wr.G_Expired : t.DelegatorEmail == null ? wr.Users_AddedBySystem : t.DelegatorEmail,
                r = n("<tr/>");
            r.append(n("<td/>").append(n("<input>", {
                type: "checkbox",
                site: t.Site,
                email: t.Email
            })).append(n("<label/>"))).append(n("<td/>").text(t.Email)).append(n("<td/>").append(n("<span>", {
                "class": "url-text"
            }).text(t.Site))).append(n("<td/>").text(t.RolesFormatted)).append(n("<td/>").text(t.DateFormatted)).append(n("<td/>").text(u)), n("#userList table.wmtable tbody").prepend(r)
        }

        function f(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), reportError(n)
        }

        function e(i, r) {
            var f, u;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), f = {}, u = 0; u < r.length; ++u) f[r[u].Site + "|" + r[u].Email] = !0;
            n("#userList table.wmtable input[type=checkbox]").each(function () {
                var t = n(this).attr("site") + "|" + n(this).attr("email");
                f[t] && n(this).parent().parent().remove()
            }), t()
        }

        function o(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }
        var t = setupGridCheckButtonEnabler("#userList table.wmtable", "#removeParam"),
            i = function () {
                n("#email").attr("disabled", null), n("#url").attr("disabled", null), n("#role").attr("disabled", null), n("#key").attr("disabled", null), n("#addParam").attr("disabled", null)
            }, r = function () {
                n("#email").attr("disabled", "disabled"), n("#url").attr("disabled", "disabled"), n("#role").attr("disabled", "disabled"), n("#key").attr("disabled", "disabled"), n("#addParam").attr("disabled", "disabled")
            };
        n("#addParam").click(function () {
            if (n(this).attr("disabled") != "disabled" && n("#addParamForm").valid()) {
                var i = n.trim(n("#email").val()),
                    e = n.trim(n("#url").val()),
                    t = n("#role").val(),
                    o = n("#key").val(),
                    s = t == "admin",
                    h = t == "readonly";
                showActivityIndicator(!0), r(), getSvc().invoke("AddSiteRoles", {
                    url: url,
                    delegatedUrl: e,
                    userEmail: i,
                    isAdministrator: s,
                    isReadOnly: h,
                    authenticationCode: o
                }, u, f)
            }
        }), n("#removeParam").click(function () {
            var t = [];
            n("#userList table.wmtable :checked").each(function () {
                var i = n(this),
                    r = {
                        Site: i.attr("site"),
                        Email: i.attr("email")
                    };
                t.push(r)
            }), t.length > 0 && (showActivityIndicator(!0), getSvc().invoke("RemoveSiteRoles", {
                url: url,
                siteRoles: t
            }, function (n) {
                e(n, t)
            }, o))
        }), n("#addParamForm").validate(formValidateOptions), n("#userList table.wmtable :checkbox").live("change", t), clickButtonOnEnter("#addParam", ["#url", "#email"])
    }
}(jQuery),
function (n) {
    n.fn.verify = function (t) {
        function i() {
            var i = n("#hosters").val();
            i != "0" && (i = "how+to+add+CNAME+record+to+" + i, window.open(n.validator.format(t.SerpFormat, i), "dns"))
        }

        function r() {
            showActivityIndicator(!0), getSvc().invoke("CheckSiteVerification", {
                url: t.SiteUrl
            }, u, f)
        }

        function u(i) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i.IsVerified && !t.IsVerified ? document.location.href = t.DashboardUrl : i.IsVerified || (n("#successStatus").hide(), n("#currentSite").show(), n("#verificationHelp").show(), n("#failedStatus").show())
        }

        function f(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function e() {
            n("#hosters").change(i), n("#metaSnippet").click(function () {
                autoSelect(this)
            }), n("#verifySite").click(r)
        }
        e()
    }
}(jQuery),
function (n) {
    n.fn.adCenter = function () {
        function e(i) {
            var f = [],
                e = 0,
                o = 0;
            n("td:first-child span.keyword:first-child", i).each(function () {
                var i = n.trim(n(this).text()),
                    r = u(i);
                if (r != null) return e++, !0;
                o++, f.push(i), f.length >= 20 && (t.push(f), f = [])
            }), f.length > 0 && t.push(f), t.length > 0 && (t.reverse(), r())
        }

        function r() {
            var n = t.pop(),
                i;
            n != null && n.length > 0 && (i = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/"), i.invoke("GetAdCenterKeywordKpis", {
                keywords: n
            }, o, s))
        }

        function o(n) {
            for (var u = 0; u < n.length; u++) i.push(n[u]);
            t.length > 0 && r()
        }

        function s(t) {
            console.error(t), n("td:first-child span:nth-child(2)", this).hide()
        }

        function u(t) {
            var r = null;
            return n.each(i, function (n, i) {
                if (i.Keyword == t) return r = i, !1
            }), r
        }

        function h(t) {
            var i = u(t);
            if (i == null) {
                n(".adKpiContent span").text(f);
                return
            }
            n("#mlavgcpc").text(i.MainLineAds.AverageCpc), n("#mlavgbid").text(i.MainLineAds.AverageBid), n("#sbavgcpc").text(i.SidebarAds.AverageCpc), n("#sbavgbid").text(i.SidebarAds.AverageBid)
        }

        function c(t) {
            n("td:first-child span:nth-child(2)", t).each(function () {
                var t = n("#adCenterPop");
                n(this).mouseover(function () {
                    var i = n(this).position(),
                        r = i.left + 2,
                        f = i.top + 23 + n("#page-content").scrollTop(),
                        u, e;
                    n(this).offset().top + t.height() > n(window).height() - 65 ? (u = i.top + n("#page-content").scrollTop() - t.height(), u > 10 && (r = r + 25, f = u, t.find(".box-arrow-bottom").show(), t.find(".box-arrow").hide())) : (t.find(".box-arrow-bottom").hide(), t.find(".box-arrow").show()), t.css({
                        top: f,
                        left: r + "px"
                    }).show(), e = n(this).prev().text(), h(e)
                }).mouseleave(function () {
                    var n = t.position().top;
                    setTimeout(function () {
                        t.is(":hover") || n != t.position().top || t.hide()
                    }, 100)
                }), t.mouseleave(function () {
                    t.hide()
                })
            })
        }

        function l(t) {
            var i = "<span class='graybox cursor-default'>$</span>";
            n("tbody td:first-child", t).each(function () {
                if (n(this).find("span.keyword").length == 0) {
                    var t = n.validator.format("<span class='keyword'>{0}</span> {1}", n(this).html(), i);
                    n(this).html(t)
                }
            })
        }
        var t = [],
            i = [],
            f = "$0.05";
        n("#adCenterPop").length == 1 && (l(this), c(this), e(this))
    }, n.fn.adCenter.offerDetails = function () {
        var t = n.validator.format(wr.AdCenter_OfferDetails, apCfg.AzureStorage.AdCenterCurrentCouponBatchExpiry);
        showHtmlMessage(t, wr.AdCenter_OfferDetailsLabel)
    }
}(jQuery),
function (n) {
    n.fn.crawlInformation = function (t) {
        function i() {
            if (n.trim(n(this).text()) != "0") {
                var i = n("a", n(this)).attr("issueType"),
                    r = n.validator.format("{0}&issueType={1}", t.crawlInformationDetailUrl, i);
                document.location = r
            }
        }
        n("#crawlInfoWidget td.pointer").hover(function () {
            n(this).hasClass("orange-bottom-border") || n(this).addClass("grey-bottom-border")
        }, function () {
            n("#crawlInfoWidget .grey-bottom-border").removeClass("grey-bottom-border")
        }), n("#" + t.selectedIssueType).addClass("orange-bottom-border"), n("#crawlInfoWidget td.pointer").click(i)
    }
}(jQuery),
function (n) {
    n.fn.showInboundLinks = function (t) {
        showActivityIndicator(!0), n.post(inboundLinksDialogUrl + encodeURI(t), function (n) {
            showPopupModalDialog("#linksDialog", n), hideActivityIndicator()
        })
    }
}(jQuery),
function (n) {
    n.fn.indexexplorer = function (t) {
        function f(n) {
            n.addClass("active"), o()
        }
        var s = null,
            st = function () {
                n("#index-explorer-tree").hide(), n("#index-explorer-folder").hide(), n("#index-explorer-page").hide(), n("#index-explorer-tree-default").show(), n("#index-explorer-folder-default").show(), n("#index-explorer-page-default").show(), n("#status-no-data").hide(), n("#status-loading").show(), n("#status-error").hide()
            }, e = function () {
                n("#index-explorer-page").hide(), n("#index-explorer-page-default").show()
            }, r = function () {
                var t = n("#folder").val();
                return t.length > 0 && !hasPlaceholderSupport() && n("#folder").attr("placeholder") != undefined && n("#folder").attr("placeholder") == n("#folder").val() && (t = ""), {
                    httpCode: n("#httpCode").val(),
                    discovery: n("#discovery").val(),
                    crawlDate: n("#crawlDate").val(),
                    itUrl: t,
                    malware: n("#malware").is(":checked"),
                    robotstxt: n("#robotstxt").is(":checked"),
                    traffic: n("#traffic").is(":checked")
                }
            }, a = function () {
                n("#navigationContainer").hide(), n("#navigationContainerDefault").show(), n("#index-explorer-tree").hide(), n("#index-explorer-tree-default").show(), n("#status-no-data").show(), n("#status-loading").hide(), n("#status-error").hide()
            }, ft = function () {
                n("#navigationContainerDefault").hide(), n("#navigationContainer").empty().show(), n("#navigation").detach().appendTo(n("#navigationContainer")), n("#index-explorer-tree").show(), n("#index-explorer-tree-default").hide(), scrollToTop();
                var t = n(".index-explorer-tree-list > ul > li:not(.more)");
                t.length < 4 && t.each(function () {
                    l(n(this))
                })
            }, ut = function (i) {
                var r = n.extend({}, i);
                n("#index-explorer-tree").load(t.treeUrl, r, function (t, i) {
                    i == "success" ? n.trim(t).length == 0 ? a() : ft() : (n("#index-explorer-tree").hide(), n("#index-explorer-tree-default").show(), n("#status-no-data").hide(), n("#status-loading").hide(), n("#status-error").show())
                })
            }, u = function (i) {
                var u = r();
                u.itUrl.length == 0 && (u.itUrl = t.rootUrl), i && i.length > 0 && (u.itUrl = i), e(), ut(u), h(u), n("#index-explorer-page").empty()
            }, rt = function () {
                var u = r(),
                    f, i;
                u.itUrl = n(this).attr("iturl"), u.offset = n(this).attr("offset"), u.level = n(this).attr("level"), f = n(this), i = n(this).parent(), f.text(wr.G_Loading), i.load(t.treeChildrenUrl, u, function (n, t) {
                    t == "success" ? (i.children().appendTo(i.parent()), i.remove()) : f.text(wr.IndexExplorer_More)
                })
            }, it = function () {
                n("#index-explorer-folder").hide(), n("#index-explorer-folder-default").show()
            }, tt = function () {
                n("#index-explorer-folder").show(), n("#index-explorer-folder-default").hide(), scrollToTop()
            }, h = function (i) {
                var r = n.extend({}, i);
                n("#index-explorer-folder").load(t.folderUrl, r, function (t, r) {
                    r == "success" ? (s = i.itUrl, n.trim(t).length == 0 ? it() : tt()) : (n("#index-explorer-folder").hide(), n("#index-explorer-folder-default").show())
                })
            }, c = function (n) {
                if (e(), s != n) {
                    var t = r();
                    t.itUrl = n, h(t)
                }
            }, ot = function () {
                var f = r(),
                    u, i;
                f.itUrl = n(this).attr("iturl"), f.offset = n(this).attr("offset"), u = n(this), i = n(this).parent(), u.text(wr.G_Loading), i.load(t.folderNextUrl, f, function (n, t) {
                    t == "success" ? (i.children().appendTo(i.parent()), i.remove()) : u.text(wr.IndexExplorer_More)
                })
            }, g = function () {
                n("#index-explorer-page").hide(), n("#index-explorer-page-default").show()
            }, d = function () {
                n("#index-explorer-page").show(), n("#index-explorer-page-default").hide(), scrollToTop()
            }, et = function (i) {
                var r = n.extend({}, i);
                n("#index-explorer-page").load(t.pageUrl, r, function (t, i) {
                    i == "success" ? n.trim(t).length == 0 ? g() : d() : (n("#index-explorer-page").hide(), n("#index-explorer-page-default").show())
                })
            }, k = function () {
                var t = r();
                t.itUrl = n(this).attr("iturl"), et(t)
            }, l = function (i) {
                var f, e, u;
                i.data("loaded") ? i.hasClass("open") ? (i.data("empty") || i.next().hide(), i.removeClass("open")) : (i.data("empty") || i.next().show(), i.addClass("open")) : (f = r(), f.itUrl = i.attr("iturl"), f.offset = 0, f.level = 1, e = n("<ul />", {
                    "class": "folders"
                }), u = n("<li />").hide(), u.append(e), i.after(u), e.load(t.treeChildrenUrl, f, function (t, r) {
                    r == "success" ? (i.addClass("open"), i.data("loaded", !0), n.trim(t).length == 0 ? (i.data("empty", !0), u.remove()) : u.show()) : u.remove()
                }))
            }, b = function () {
                var t = n(this);
                l(t), n("#index-explorer-tree").find(".active").removeClass("active"), t.addClass("active"), c(n(this).attr("iturl"))
            }, w = function () {
                var t = n(this);
                n("#index-explorer-tree").find(".active").removeClass("active"), t.addClass("active"), c(n(this).attr("iturl"))
            }, p = function () {
                e(), u(n(this).attr("iturl"))
            }, i = function () {
                n("#httpCode").val("Any"), n("#discovery").val("Any"), n("#crawlDate").val("Any"), n("#folder").val(""), n("#malware").prop("checked", !1).next().removeClass("on"), n("#robotstxt").prop("checked", !1).next().removeClass("on"), n("#traffic").prop("checked", !1).next().removeClass("on"), n(".index-explorer-preset").removeClass("active")
            }, o = function () {
                n("#indexexplorerform").valid() && u()
            }, y = function () {
                hideActivityIndicator(), unsetModalActivityIndicator(), showHtmlMessage(wr.IndexExplorer_RecrawlSuccess, wr.G_UrlsSubmitted)
            }, v = function (n) {
                hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
            }, nt = function () {
                var t = n(this).attr("iturl");
                showActivityIndicator(!0), getSvc().invoke("SubmitUrls", {
                    url: url,
                    urlList: [t]
                }, y, v)
            };
        n("#showCustomOptions").click(function () {
            n(this).hide(), n("#hideCustomOptions").show(), n("#customOptions").show()
        }), n("#hideCustomOptions").click(function () {
            n(this).hide(), n("#showCustomOptions").show(), n("#customOptions").hide()
        }), n("#resetfilters").click(function () {
            i(), u()
        }), n("#applyfilters").click(o), n("#presetLanding").click(function () {
            i(), n("#traffic").prop("checked", !0).next().addClass("on"), f(n(this))
        }), n("#preset301").click(function () {
            i(), n("#httpCode").val("Code301"), f(n(this))
        }), n("#preset404").click(function () {
            i(), n("#httpCode").val("Code4xx"), f(n(this))
        }), n("#presetMalware").click(function () {
            i(), n("#malware").prop("checked", !0).next().addClass("on"), f(n(this))
        }), n("#presetRobot").click(function () {
            i(), n("#robotstxt").prop("checked", !0).next().addClass("on"), f(n(this))
        }), n("#navigationContainerDefault a.index-explorer-navigational-link").click(function () {
            u(t.rootUrl)
        }), n(".index-explorer-navigational-link").live("click", p), clickButtonOnEnter("#applyfilters", "#folder"), n("#index-explorer-tree .index-explorer-more").live("click", rt), n(".index-explorer-expandable").live("click", b), n(".index-explorer-viewable").live("click", w), n("#index-explorer-folder .index-explorer-more").live("click", ot), n("#index-explorer-folder .index-explorer-page-link").live("click", k), n("#index-explorer-page .index-explorer-recrawl").live("click", nt), clickButtonOnEnter("#applyfilters", ["#folder"]), n("#indexexplorerform").validate(formValidateOptions), n("#indexexplorerform select, #indexexplorerform input").change(function () {
            n(".index-explorer-preset").removeClass("active")
        }), u()
    }
}(jQuery),
function (n) {
    n.fn.searchKeyword = function (t) {
        var i = n.extend({}, t);
        i.isKeyword = !0, i.Title = wr.Keyword_TrafficDetailsForTitle, i.Description = wr.Keyword_DetailsIntro, i.PageDetailsUrl = t.pagequerytrafficdetailsUrl, i.PopupDeatilsUrl = t.querypagedetailsUrl, i.TableContainer = "#keywordsTableContainer", i.DataRangeChanged = function () {
            showActivityIndicator(!1), n("#keywordsTableContainer").load(i.keywordTableUrl, {
                startDate: n("#keywordRangeFrom").val(),
                endDate: n("#keywordRangeTo").val()
            }, function () {
                hideActivityIndicator(), n("table.adCenterIntegration").adCenter(), initEmptyTables(), initInfoHovers()
            })
        }, n.fn.searchDetails(i)
    }
}(jQuery),
function (n) {
    n.fn.malware = function (t) {
        function i(n) {
            var t = JSON.parse(n.responseText);
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(t)
        }

        function r() {
            n("#reviewRequestContainer").remove(), n.ajax({
                url: t.historyDataUrl,
                type: "GET",
                success: function (t) {
                    n("#historyContainer").html(t), enablePagination(), enableAjaxSorting()
                },
                error: i
            })
        }

        function u() {
            showActivityIndicator(!0), n.getJSON(t.requestReviewUrl, r).error(i)
        }
        n("#requestreview").click(u)
    }
}(jQuery),
function (n) {
    n.fn.pageTraffic = function (t) {
        var i = n.extend({}, t);
        i.isKeyword = !1, i.Title = wr.Page_TrafficDetailsForTitle, i.Description = wr.Page_PageDetailsIntro, i.PageDetailsUrl = t.PageQueryTrafficDetailsJsonUrl, i.PopupDeatilsUrl = t.PageTrafficDetailsTableUrl, i.TableContainer = "#pageTrafficTableContainer", i.DataRangeChanged = function () {
            showActivityIndicator(!1), n("#pageTrafficTableContainer").load(i.fullPageTrafficTableUrl, {
                startDate: n("#keywordRangeFrom").val(),
                endDate: n("#keywordRangeTo").val()
            }, function () {
                hideActivityIndicator(), initInfoHovers()
            })
        }, n.fn.searchDetails(i)
    }
}(jQuery),
function (n) {
    n.fn.reportsAndData = function (t) {
        n(this).pageTraffic(t), n(this).searchKeyword(t), n(this).seoReport(t), n(this).crawlInformation(t), n("#keywordRangeContainer").live("dateChanged", function () {
            initInfoHovers()
        })
    }
}(jQuery),
function (n) {
    n.fn.seoReport = function (t) {
        function i() {
            var i = n(this).attr("for"),
                r = n.validator.format("{0}&ruleId={1}", t.seoRuleDetailUrl, i);
            document.location = r
        }

        function r() {
            var i = n(this).text(),
                r = n.validator.format("{0}&paramUrl={1}", t.seoAnalyzerUrl, encodeURIComponent(i));
            document.location = r
        }
        n("#seoRulesTableContainer a.ruleDetailLink").click(i), n("#failingPagesTableContainer td > a").click(r)
    }
}(jQuery),
function (n) {
    n.fn.searchDetails = function (t) {
        function r() {
            var r = n(this).attr("for"),
                t, f;
            n("#popupDialogTitleText").text(i.Title), n("#popupDialogTitleParameter").text(r), n("#popupDialogDescription").text(i.Description), n("#ptdDialog").jqm({
                overlay: 10,
                modal: !0
            }).jqmShow(), t = {
                startDate: n("#keywordRangeFrom").val(),
                endDate: n("#keywordRangeTo").val()
            }, i.isKeyword ? t.query = r : t.pageUrl = r, n("#dialogTableContainer").load(i.PopupDeatilsUrl, t, function () {
                hideActivityIndicator(), initEmptyTables(), n("#dialogTableContainer a.rowExpand").bind("click", u)
            }), f = n(this), f.hasClass("viewClicked") || f.addClass("viewClicked")
        }

        function u() {
            var u = n("#popupDialogTitleParameter").text(),
                r = n(this).attr("for"),
                e = i.isKeyword ? u : r,
                o = i.isKeyword ? r : u,
                f = "#dialogTableContainer td[for='" + r + "']",
                t = n(f);
            t.length == 0 ? (n(this).parent().parent().after(['<tr><td></td><td colspan="4" for="', r, '"></td><td colspan="2"></td><tr>'].join("")), t = n(f), t.load(i.PageDetailsUrl, {
                query: e,
                pageUrl: o,
                startDate: n("#keywordRangeFrom").val(),
                endDate: n("#keywordRangeTo").val()
            }, function () {
                hideActivityIndicator()
            })) : t.is(":visible") ? t.parent().slideUp("fast") : t.parent().slideDown("fast")
        }
        var i = t;
        n("#keywordRangeContainer").live("dateChanged", i.DataRangeChanged), n("table.adCenterIntegration").adCenter(), n(i.TableContainer + " a.viewtrafficDetails").live("click", r)
    }
}(jQuery),
function (n) {
    n.fn.siteActivityReports = function (t) {
        var r = getStandartChartOptions(),
            u = n("#chart"),
            f = n('.charts-legend input[type="checkbox"]'),
            i = [
                [],
                [],
                [],
                [],
                []
            ],
            e;
        splitChartData(t.data, i, 5), e = setupChart(u, r, f, i, "#keywordRangeFrom", "#keywordRangeTo", "#keywordRangeContainer", "#site-nav-toggle-button")
    }
}(jQuery),
function (n) {
    n.fn.fetchasbot = function (t) {
        function y() {
            var i = n(this).attr("url");
            return showActivityIndicator(!1), n.post(t.detailsUrl, {
                fetchedUrl: i
            }, function (t) {
                hideActivityIndicator(), n("#fetchedStatus").html(t), n(".prettyprint").length > 0 && prettyPrint()
            }), !1
        }

        function p(t) {
            for (var r = {}, i = 0; i < t.length; ++i) r[t[i].Url] = !0;
            n("#paramList table.wmtable input[type=checkbox]").each(function () {
                r[n(this).attr("url")] && n(this).parent().parent().remove()
            })
        }

        function l(t) {
            hideActivityIndicator(), unsetModalActivityIndicator(), n("#url").val(""), p([t]);
            var r = n("<tr/>");
            r.append(n('<td class="width20"/>').append(n("<input>", {
                type: "checkbox",
                url: t.Url
            })).append(n("<label/>"))).append(n('<td class="leftalign" />').append(createExternalUrl(t.Url))).append(t.Fetched ? n("<td/>").append(n("<a/>", {
                "class": "fetch-as-bot-completed",
                href: "javascript:void",
                url: t.Url
            }).text(wr.G_Completed)) : n("<td/>").text(t.Expired ? wr.G_TimeoutErrorTitle : wr.G_Pending)), n("#paramList table.wmtable tbody").prepend(r), n("#paramList table.wmtable").trigger("update"), --i, i == 0 && (e(), s())
        }

        function o(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n), --i, i == 0 && (e(), s())
        }

        function w(t) {
            for (var e = {}, r, f, i = 0; i < t.length; ++i) e[t[i].Url] = t[i];
            for (r = [], n("#paramList table.wmtable tbody tr").each(function () {
                var u = n(this).children("td:nth-child(3)"),
                    f = n.trim(u.text()),
                    t, i;
                f == wr.G_Pending && (t = n(this).find(":checkbox").attr("url"), i = e[t], t && i && (i.Expired ? u.text(wr.G_TimeoutErrorTitle) : i.Fetched && (u.html("").append(n("<a/>", {
                    "class": "fetch-as-bot-completed",
                    href: "javascript:void",
                    url: t
                }).text(wr.G_Completed)), r.push(t))))
            }), f = !1, i = 0; i < t.length; ++i) t[i].Fetched || t[i].Expired || (f = !0);
            f ? (h != null && clearTimeout(h), h = setTimeout(function () {
                s()
            }, a)) : u == 1 && r.length > 0 && n("#paramList table.wmtable tbody tr").find('a[url="' + r[0] + '"]').trigger("click")
        }

        function s() {
            getSvc().invoke("GetFetchedUrls", {
                url: url
            }, w, reportError)
        }

        function c(n) {
            showActivityIndicator(!0), r(), i = 1, u = i, getSvc().invoke("FetchUrl", {
                url: url,
                urlToFetch: n
            }, l, o)
        }

        function b(t) {
            var r, i;
            for (e(), hideActivityIndicator(), unsetModalActivityIndicator(), r = {}, i = 0; i < t.length; ++i) r[t[i]] = !0;
            n("table.wmtable :checkbox").each(function () {
                r[n(this).attr("url")] && n(this).parent().parent().remove()
            }), triggerCheckboxChange(), n("#paramList table.wmtable").trigger("update")
        }
        var a = 2e3,
            h = null,
            i = 0,
            u = 0,
            f = setupGridCheckButtonEnabler("#paramList table.wmtable", "#removeParam"),
            v = setupGridCheckButtonEnabler("#paramList table.wmtable", "#resubmitParam"),
            e = function () {
                n("#url").attr("disabled", null), n("#addParam").attr("disabled", null);
                var t = n("#paramList table.wmtable :checked").length > 0;
                n("#resubmitParam").attr("disabled", t ? null : "disabled")
            }, r = function () {
                n("#url").attr("disabled", "disabled"), n("#addParam").attr("disabled", "disabled"), n("#resubmitParam").attr("disabled", "disabled")
            };
        n("#addParam").click(function () {
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode && n("#addParamForm").valid()) {
                var t = n.trim(n("#url").val());
                c(t)
            }
        }), n("#removeParam").click(function () {
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode) {
                var t = [];
                n("#paramList table.wmtable :checked").each(function () {
                    var i = n(this);
                    t.push(i.attr("url"))
                }), console.log(t), t.length > 0 && (showActivityIndicator(!0), r(), getSvc().invoke("RemoveFetchUrls", {
                    url: url,
                    urlToRemove: t
                }, b(t), o))
            }
        }), n("#resubmitParam").click(function () {
            var t, f;
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode && (t = [], n("#paramList table.wmtable :checked").each(function () {
                var i = n(this);
                t.push(i.attr("url"))
            }), t.length > 0)) {
                showActivityIndicator(!0), r(), i = t.length, u = i;
                for (f in t) getSvc().invoke("FetchUrl", {
                    url: url,
                    urlToFetch: t[f]
                }, l, o)
            }
        }), n("#addParamForm").validate(formValidateOptions), n("#paramList table.wmtable :checkbox").live("change", f).live("change", v), n("#paramList table.wmtable :checkbox").live("change", f).live("change", f), n("#paramList table.wmtable .fetch-as-bot-completed").live("click", y), window.readOnlyMode && r(), clickButtonOnEnter("#addParam", "#url"), t.paramUrl.length > 0 && c(t.paramUrl)
    }
}(jQuery),
function (n) {
    n.fn.keywordResearch = function (t) {
        function s(n, t) {
            var r, i;
            t.relatedKeywordSparklineData = n, r = {};
            for (i in n) r[n[i].Query] = t.strict ? n[i].BroadImpressionsSparkDataFormatted : n[i].ImpressionsSparkDataFormatted;
            t.relatedKeywordSparklineDataMap = r, l(t)
        }

        function h(n) {
            console.log("loadRelatedKeywordSparklineDataFailure", n), reportError(n)
        }

        function c(n) {
            console.log("loadRelatedKeywordSparklineDataTimeout", n), n.relatedKeywordSparklineDataTimeoutCount++, r(n)
        }

        function r(n) {
            getSvc().invoke("GetKeywordSparklineData", {
                queries: n.relatedKeywordDataQueries,
                country: n.country,
                language: n.language,
                startDate: n.startDate,
                endDate: n.endDate
            }, function (t) {
                s(t, n)
            }, function (t) {
                h(t, n)
            }, !1, n.relatedKeywordSparklineDataTimeoutCount > 5 ? null : function () {
                c(n)
            })
        }

        function u(n, t, i) {
            var f, r;
            if (!(i >= t.length)) {
                for (f = Math.min(i + 2, t.length), r = i; r < f; ++r) {
                    var e = t[r],
                        o = e.visualElement,
                        s = e.value;
                    o.text(s), o.sparkline("html", {
                        width: "60px",
                        fillColor: ""
                    })
                }
                setTimeout(function () {
                    u(n, t, f)
                }, 1)
            }
        }

        function l(t) {
            var i = t.relatedKeywordSparklineDataMap,
                r = [],
                f = n("table.grid.keywords-with-sparklines tbody tr");
            f.each(function () {
                var t = n(this).find("td"),
                    u = n("a.keyword", t[0]).text(),
                    f = n(t[2]),
                    e = f.children("span"),
                    o = i[u] || "";
                i[u] && r.push({
                    visualElement: e,
                    value: o
                })
            }), u(t, r, 0)
        }
        var i = n("#searchform"),
            f = function () {
                var t = [];
                n("table.grid.keywords-with-sparklines tbody tr td:first-child").each(function () {
                    t.push(n(this).text())
                }), r({
                    relatedKeywordDataQueries: t,
                    strict: n("#strict").is(":checked"),
                    startDate: n("#dateRangeFrom").val(),
                    endDate: n("#dateRangeTo").val(),
                    country: n("#country").val(),
                    language: n("#language").val(),
                    relatedKeywordSparklineDataTimeoutCount: 0
                }), n("table.adCenterIntegration").adCenter()
            }, a = function (t) {
                n("#keywordscontainer").html(t), showActivityIndicator(!1), enableAjaxSorting(), initInfoHovers(), f()
            }, v = function (n, t, i, r, u) {
                t == "timeout" ? u < 5 ? e(r, u + 1) : showErrorMessage(wr.G_TimeoutError, wr.G_TimeoutErrorTitle) : handleAjaxError(n, reportError)
            }, e = function (i, r) {
                n.ajax({
                    type: "POST",
                    url: t.dataUrl,
                    data: i,
                    success: a,
                    error: function (n, t, u) {
                        return v(n, t, u, i, r)
                    },
                    timeout: 13500
                })
            }, o;
        n("#search").click(function () {
            n(this).attr("disabled") != "disabled" && i.valid() && (showActivityIndicator(!0), fixPlaceholdersBeforeInFormSubmit(i), i.submit())
        }), i.validate(formValidateOptions), n.trim(n("#keywords").val()).length > 0 && i.valid() && (showActivityIndicator(!0), o = i.serialize(), e(o, 0)), n("#historyDialog").jqm({
            overlay: 10,
            modal: !0
        }), n("#showhistory").click(function () {
            n("#historyDialog").jqmShow()
        }), n("#historyContainer a").click(function () {
            n("#keywords").val(n(this).text()), n("#search").trigger("click"), n("#historyDialog").jqmHide()
        }), n("a.keyword").live("click", function () {
            n("#keywords").val(n(this).text()), n("#search").trigger("click")
        }), n("#keywords").keydown(textAreaWithLineLimit(t.lineLimit)), n("#keywordscontainer").bind("onsort", function () {
            console.log("on sort"), f()
        })
    }
}(jQuery),
function (n) {
    n.fn.linkexplorer = function () {
        n("#linkExplorerForm").validate(formValidateOptions), n("#exploreUrl").click(function () {
            n("#linkExplorerForm").valid() && (showActivityIndicator(!0), fixPlaceholdersBeforeInFormSubmit(n("#linkExplorerForm")), n("#linkExplorerForm").submit())
        }), clickButtonOnEnter("#exploreUrl", ["#targetUrl", "#filterBySite", "#anchorText", "#additionalQuery"]), n("#requery").live("click", function () {
            n("#targetUrl").val(n(this).text()), n("#exploreUrl").trigger("click")
        }), n("#historyDialog").jqm({
            overlay: 10,
            modal: !0
        }), n("#showhistory").click(function () {
            n("#historyDialog").jqmShow()
        }), n("#historyContainer a").click(function () {
            n("#targetUrl").val(n(this).text()), n("#exploreUrl").trigger("click"), n("#historyDialog").jqmHide()
        })
    }
}(jQuery),
function (n) {
    function t() {
        function u(t) {
            var i = t,
                r, u;
            if (i.length < 1) {
                $id("output").html(wr.MarkupValidation_NoUrlsEntered);
                return
            }
            return r = n.validator.format("{0}/diagnostics/markup/processor/?url={1}", containerPrefix, url), u = {
                SiteUrl: i
            }, showActivityIndicator(!0), n.ajax({
                url: r,
                type: "POST",
                data: u,
                dataType: "json",
                success: function (n) {
                    if (n != null) {
                        if (n.Throttling == "Error") return e(), !0;
                        if (n.MarkupValidation_BadUrl) return f(n.MarkupValidation_BadUrl), !0;
                        o(n)
                    } else s()
                }
            }), !0
        }

        function f(n) {
            showErrorMessage(n, wr.G_Error)
        }

        function e() {
            hideActivityIndicator(), unsetModalActivityIndicator(), showErrorMessageMarkUpBusy()
        }

        function o(i) {
            t = [];
            var f = !1,
                u = {};
            if (u[wr.MarkupValidation_FormatNameMicrodata] = apCfg.WebConfig.MarkUpValidationLinkHTMLMicrodata, u[wr.MarkupValidation_FormatNameHtmlMarkupMicroformat] = apCfg.WebConfig.MarkUpValidationLinkMicroformats, u[wr.MarkupValidation_FormatNameHtmlMarkupRDFa] = apCfg.WebConfig.MarkUpValidationLinkRDFa, u[wr.MarkupValidation_FormatNameHtmlMarkupSchemaDotOrg] = apCfg.WebConfig.MarkUpValidationLinkSchemadotorg, u[wr.MarkupValidation_FormatNameOpenGraphJsonHeader] = apCfg.WebConfig.MarkUpValidationLinkOpenGraph, n.each(i, function (n, i) {
                var o, e;
                if (i.length > 1 && (t.push('<div class="markup-validator-bold marginTop15">'), t.push('<a href="' + u[n] + '" target="_blank">'), t.push(n), t.push("</a>"), t.push("</div>")), i.length > 0)
                    for (f = !0, o = JSON.parse(i), e = 0; e < o.Entities.length; e++) r(o.Entities[e])
            }), hideActivityIndicator(), unsetModalActivityIndicator(), !f) {
                $id("output").html(wr.MarkupValidation_NoDataGot);
                return
            }
            $id("output").html(t.join(""))
        }

        function r(n) {
            var u = n.Properties,
                o, i, s, f, e;
            if (typeof u != "undefined") {
                for (o = n.Type, t.push('<ul class="listStyleNone marginTop15">'), t.push("<li>"), t.push('<span class="markup-validator-bold">'), t.push(wr.MarkupValidation_Entity), t.push(" "), t.push(o), t.push("</span>"), t.push("</li>"), i = 0; i < u.length; i++)
                    if (!(u[i].Type.length < 0)) {
                        if (s = u[i].Type, u[i].Entities != null)
                            for (f = 0; f < u[i].Entities.length; f++)
                                if (u[i].Value == null)
                                    if (u[i].Entities[f] == null) continue;
                                    else r(u[i].Entities[f]);
                        if (u[i].Value != null)
                            for (e = 0; e < u[i].Value.length; e++) t.push("<li>"), t.push(s), t.push(": " + u[i].Value[e] + "</li>")
                    }
                t.push("</ul>")
            }
        }

        function s(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function h(n) {
            u(n)
        }
        var i = {}, t = [];
        return i.go = function () {
            $id("markupValidateForm").validate(formValidateOptions), $id("markupValidate").click(function () {
                var n = $id("markupUrl").val();
                $id("markupValidateForm").valid() && h(n)
            }), $id("markupUrl").val().length > 0 && !isValueSameAsPalceHolder(n("#markupUrl").val(), n("#markupUrl")) && $id("markupValidate").trigger("click"), clickButtonOnEnter("#markupValidate", "#markupUrl")
        }, i
    }
    n.markupValidatorController = function () {
        return new t
    }
}(jQuery),
function (n) {
    n.fn.seoAnalyzer = function (t) {
        function i(t) {
            var r = t.data != null,
                u = n("#site-nav-minor").width(),
                i;
            (r && n("#site-nav").hasClass("on") || !r && !n("#site-nav").hasClass("on")) && (u = 0), i = n("#sandboxIframe").position(), isRTL && (i.left = 0);
            var o = n(window).width(),
                s = n(window).height(),
                f = o - (i.left + u + 75),
                e = s - (i.top + 100);
            r ? n("#sandboxIframe").animate({
                width: f
            }, 400).height(e) : n("#sandboxIframe").width(f).height(e)
        }

        function r() {
            if (n("#analyzeButtonForm").valid()) {
                var i = n("#seoUrl").val();
                document.location = t.seoAnalyzerUrl + "&paramUrl=" + encodeURIComponent(i)
            }
        }

        function u() {
            n(window).width() < 1220 && t.autoHideLeftNav == "true" && n("#site-nav").hasClass("on") && n("#site-nav-toggle-button").click(), n("#analyzeButtonForm").validate(formValidateOptions), n("body > article").css({
                "padding-bottom": "5px"
            }), n("#site-nav-toggle-button").bind("click", {
                siteNavToggle: !0
            }, i), n(window).resize(i).trigger("resize"), clickButtonOnEnter("#analyzeButton", "#seoUrl"), n("#analyzeButton").click(r)
        }
        u()
    }
}(jQuery),
function (n) {
    n.fn.seoSandbox = function (t) {
        var r = {
            iframeContents: null,
            markerImages: null,
            seoFloatingPopup: null,
            lastActiveMarker: null,
            isIframeReady: !1,
            setupMarkers: function () {
                r.positionMarkerImages(!0)
            },
            positionMarkerImages: function (t) {
                r.iframeContents == null && (r.iframeContents = n("#analyzedPageIframe").contents()), r.markerImages.each(function () {
                    var e = n(this).attr("violationIndex"),
                        o = n(this).attr("seoSeverity"),
                        u, s, f;
                    e != null && o != null && (u = n("#seo_" + e, r.iframeContents).filter(":visible"), u.length == 1 && (s = i.getMarkerSource("plus", o), f = u.offset(), f != null && n(this).attr("src", s).attr("state", "1").css({
                        position: "absolute",
                        top: f.top + u.height() / 2 - 12 + "px",
                        left: f.left - 24 + "px"
                    }), t && n(this).bind("mouseenter", {
                        violatingElement: u
                    }, r.markerMouseEnter).bind("mouseleave", {
                        violatingElement: u
                    }, r.markerMouseLeave).bind("click", {
                        violatingElement: u
                    }, r.markerClick)))
                }), r.isIframeReady = !0
            },
            markerClick: function (t) {
                var f = t.data.violatingElement,
                    u = n(this).attr("seoSeverity"),
                    e = n(this).attr("state");
                e == "0" ? (i.setMarkerSourceAndState(n(this), "plus", u), f.removeClass(u + "ElemBorder"), r.seoFloatingPopup.hide("slow")) : (i.setMarkerSourceAndState(n(this), "minus", u), f.addClass(u + "ElemBorder"), r.seoFloatingPopup.show("slow"))
            },
            markerMouseLeave: function (n) {
                var t = n.data.violatingElement
            },
            markerMouseEnter: function (t) {
                var u = t.data.violatingElement,
                    e = n(this).attr("seoSeverity"),
                    l = n(this).attr("seorule"),
                    h = n(this).attr("violationindex"),
                    c, f, o, s;
                (r.lastActiveMarker != h || r.seoFloatingPopup.css("display") == "none") && (r.lastActiveMarker != null && (c = r.iframeContents.find("#seo_" + r.lastActiveMarker), f = r.iframeContents.find("#seoMarkerNodes img[violationindex='" + r.lastActiveMarker + "']"), f.length == 1 && (o = f.attr("seoSeverity"), c.removeClass(o + "ElemBorder"), i.setMarkerSourceAndState(f, "plus", o))), i.setMarkerSourceAndState(n(this), "minus", e), r.seoDetailsExpandCollapse(!1, !0), r.lastActiveMarker = h, u.addClass(e + "ElemBorder"), r.setupViolationPanel(r.seoFloatingPopup, r.iframeContents, l, e, u), r.seoFloatingPopup.show(), s = u.offset(), r.seoFloatingPopup.css({
                        position: "absolute",
                        top: s.top + 3 + u.height() + "px",
                        left: s.left - 1 + "px"
                    }))
            },
            setupViolationPanel: function (t, u, f, e, o) {
                var h, c;
                t.attr("class", ""), t.attr("class", "seoAnalyzer seoViolationMarkerPanel " + e + "Severity");
                var y = n("#floatTitle", u),
                    p = n("#floatruleDescription", u),
                    l = n("#floatViolatingTag", u),
                    w = n("#floatErrorMsg", u),
                    a = n("#floatAction", u),
                    v = n("#floatExplanation", u),
                    s = "";
                switch (e) {
                case "high":
                    s = wr.Seo_Severityhigh;
                    break;
                case "moderate":
                    s = wr.Seo_Severitymoderate;
                    break;
                case "low":
                    s = wr.Seo_Severitylow
                }
                y.text(s), i.seoPageFailureDetails != null && (p.text(i.seoPageFailureDetails[f].Description), w.text(r.htmlDecodeString(i.seoPageFailureDetails[f].ErrorMessage)), h = r.htmlDecodeString(i.seoPageFailureDetails[f].Action), c = r.htmlDecodeString(i.seoPageFailureDetails[f].SeoExplanation), h == null || h == "" ? a.closest("p").hide() : (a.closest("p").show(), a.text(h)), c == null || c == "" ? v.closest("p").hide() : (v.closest("p").show(), v.text(c))), o != null ? (l.show(), l.text(r.getViolatingTag(o))) : l.hide()
            },
            getViolatingTag: function (n) {
                var t = n.clone(),
                    i, r, u;
                return t.removeAttr("id").removeAttr("class").removeAttr("seoRuleId"), i = t.attr("oldId"), i !== undefined && t.attr("id", i).removeAttr("oldId"), r = t.attr("oldClass"), r !== undefined && t.attr("class", r).removeAttr("oldClass"), u = t.wrap("<div>").parent().html(), Encoder.htmlDecode(u)
            },
            htmlDecodeString: function (t) {
                return n("<div/>").html(t).text()
            },
            collapsableLinkClickHander: function () {
                n(this).attr("state") == "0" ? r.seoDetailsExpandCollapse(!0, !1) : r.seoDetailsExpandCollapse(!1, !1)
            },
            seoDetailsExpandCollapse: function (n, t) {
                var u = r.iframeContents.find("#seoDetails"),
                    i = r.iframeContents.find("#collapsableLink");
                n ? (i.text(wr.Seo_Collapse), i.attr("state", 1), u.show("slow")) : (i.text(wr.Seo_Expand), i.attr("state", 0), t ? u.hide() : u.hide("slow"))
            },
            filterMarkers: function (n) {
                if (r.seoFloatingPopup != null && r.seoFloatingPopup.hide(), r.markerImages != null) {
                    r.markerImages.hide(), r.iframeContents.find(".highElemBorder, .ModerateElemBorder, .lowElemBorder").removeClass("highElemBorder ModerateElemBorder lowElemBorder");
                    var t = null;
                    switch (n) {
                    case "all":
                        t = r.markerImages;
                        break;
                    case "high":
                        t = r.markerImages.filter("img[seoseverity='High']");
                        break;
                    case "moderate":
                        t = r.markerImages.filter("img[seoseverity='Moderate']");
                        break;
                    case "low":
                        t = r.markerImages.filter("img[seoseverity='Low']");
                        break;
                    default:
                        t = r.markerImages.filter("img[seorule='" + n + "']")
                    }
                    t.show()
                }
            },
            go: function () {
                r.iframeContents = n("#analyzedPageIframe").contents(), n("#seoMarkerNodes", r.iframeContents).append(n("#seoFloat").clone()), r.seoFloatingPopup = n("#seoFloat", r.iframeContents), r.markerImages = n("#seoMarkerNodes img", r.iframeContents), setTimeout(function () {
                    r.setupMarkers(), n("#collapsableLink", r.iframeContents).click(r.collapsableLinkClickHander), r.filterMarkers("all"), n("#seoAnalyzerVeil", r.iframeContents).hide("slow"), n("#renderWarning").show(), console.log("Page analysis Iframe is ready")
                }, 1e3)
            }
        }, u = {
                iframeContents: null,
                sourceSpanTags: null,
                filterMarkers: function (t) {
                    var r, f;
                    u.sourceSpanTags.css({
                        background: "",
                        border: "",
                        outline: ""
                    }), r = null;
                    switch (t) {
                    case "all":
                        r = u.sourceSpanTags;
                        break;
                    case "high":
                        r = u.sourceSpanTags.filter("[seoseverity='High']");
                        break;
                    case "moderate":
                        r = u.sourceSpanTags.filter("[seoseverity='Moderate']");
                        break;
                    case "low":
                        r = u.sourceSpanTags.filter("[seoseverity='Low']");
                        break;
                    default:
                        r = u.sourceSpanTags.filter("[seorule='" + t + "']")
                    }
                    r.css("background", "yellow").css("border", "1px solid red"), f = n.map(r, function (t) {
                        return parseInt(n(t).attr("violationindex"))
                    }), i.currentSourceTabMarkerIndex = -1, i.sourceTabMarkers = f
                },
                go: function () {
                    "25" in i.seoPageFailureDetails || document.getElementById("pageSourceIframe").contentWindow.prettyPrint(), u.iframeContents = n("#pageSourceIframe").contents(), u.sourceSpanTags = n("span.seoSourceMarker", u.iframeContents), u.filterMarkers("all"), console.log("Page Source Iframe is ready")
                }
            }, i = {
                seoPageFailureDetails: null,
                updateTimer: null,
                updateInterval: 1e3,
                maxFetchAttempts: 15,
                fetchAttemptCounter: 0,
                sourceTabMarkers: null,
                currentSourceTabMarkerIndex: -1,
                analyzedPageIframeHeight: 0,
                pageSourceIframeHeight: 0,
                markerArrowsClicked: function () {
                    var r = n(this).attr("action"),
                        t;
                    i.sourceTabMarkers.length > 0 && (r == "down" && i.currentSourceTabMarkerIndex < i.sourceTabMarkers.length - 1 ? i.currentSourceTabMarkerIndex++ : r == "up" && i.currentSourceTabMarkerIndex > 0 && i.currentSourceTabMarkerIndex--, t = n.validator.format("span.seoSourceMarker[violationindex='{0}']", i.sourceTabMarkers[i.currentSourceTabMarkerIndex]), i.scrollIframeContent(u.iframeContents, t), u.sourceSpanTags.css({
                        border: "",
                        outline: ""
                    }), n(t, u.iframeContents).css("outline", "1px solid red"))
                },
                scrollIframeContent: function (n, t) {
                    var i = n.find(t).offset();
                    n.find("html,body").stop().animate({
                        scrollTop: i.top - 50,
                        scrollLeft: i.left
                    }, 1e3)
                },
                showHideRuleDetail: function () {
                    var u = n(this).attr("seoSeverity"),
                        f = n(this).attr("state"),
                        e = n(this).attr("seorule"),
                        t = n("#seoFloat");
                    f == "0" ? i.hideSeoFloat(n(this), !0) : (r.setupViolationPanel(t, n("body"), e, u, null), i.setMarkerSourceAndState(n(this), "minus", u), t.stop().show("slow"), t.position({
                        my: "left top",
                        at: "left bottom",
                        of: n(this),
                        offset: "-1 3"
                    }))
                },
                hideSeoFloat: function (t, r) {
                    var f = t.attr("seoSeverity"),
                        u = n("#seoFloat");
                    i.setMarkerSourceAndState(t, "plus", f), r ? u.hide("slow") : u.hide()
                },
                getMarkerSource: function (t, i) {
                    var r = window.location.protocol + "//" + window.location.host;
                    return n.validator.format("{0}/webmaster/content/images/{1}_{2}_severity.png", r, t, i)
                },
                setMarkerSourceAndState: function (n, t, r) {
                    var f = i.getMarkerSource(t, r),
                        u = 1;
                    t == "minus" && (u = 0), n.attr("src", f).attr("state", u)
                },
                rowClicked: function () {
                    var t = n(this).attr("id").split("_")[1],
                        o = n(this).attr("seoseverity"),
                        e;
                    n("input[name='violationFilter']").removeAttr("checked"), n("tr", n(this).parent()).removeClass("selectedRow"), n(this).addClass("selectedRow"), n("#clearSelection").show();
                    var a = n("#analyzedPageMessage td"),
                        v = n("#sourcePageMessage td"),
                        y = i.getMarkerSource("plus", o);
                    n("img.analyzedPageImgTag", "#iFrametabContainer").attr("src", y).attr("state", "1").attr("seorule", t).attr("seoseverity", o), n("#seoFloat").hide();
                    var f = Encoder.htmlEncode(n("td", n(this)).filter(":nth-child(2)").text()),
                        s = f,
                        h = f,
                        c = n.validator.format("[seorule='{0}']", t),
                        l = !0;
                    r.markerImages.filter(c).length > 0 && n("[seoruleid=" + t + "]:visible", r.iframeContents).length > 0 && (l = !1), r.markerImages != null && l && (s = n.validator.format("{0} <b>{1}</b>", f, wr.Seo_RuleNotVisible)), u.sourceSpanTags != null && u.sourceSpanTags.filter(c).length == 0 && (h = n.validator.format("{0} <b>{1}</b>", f, wr.Seo_RuleNotVisible)), a.last().html(s), v.last().html(h), r.filterMarkers(t), u.filterMarkers(t), n("#iFrametabContainer .selected-rule-box").is(":visible") || (e = !1, n("#iFrametabContainer .selected-rule-box").slideDown("slow", function () {
                        if (!e) {
                            var t = n("#analyzedPageRuleBox").outerHeight(!0) > n("#pageSourceRuleBox").outerHeight(!0) ? n("#analyzedPageRuleBox").outerHeight(!0) : n("#pageSourceRuleBox").outerHeight(!0);
                            i.analyzedPageIframeHeight = n("#analyzedPageIframe").outerHeight(), i.pageSourceIframeHeight = n("#pageSourceIframe").outerHeight(), n("#analyzedPageIframe").outerHeight(i.analyzedPageIframeHeight - t + 5), n("#pageSourceIframe").outerHeight(i.pageSourceIframeHeight - t + 5), e = !0
                        }
                    }))
                },
                clearFilter: function () {
                    n("#rulesTable tbody tr").removeClass("selectedRow"), n("#seoFloat").hide(), n("#iFrametabContainer .selected-rule-box").each(function () {
                        var t = n(this);
                        t.is(":visible") ? t.slideUp("slow") : t.css({
                            display: "none"
                        })
                    }), i.analyzedPageIframeHeight > 0 && n("#analyzedPageIframe").outerHeight(i.analyzedPageIframeHeight), i.pageSourceIframeHeight > 0 && n("#pageSourceIframe").outerHeight(i.pageSourceIframeHeight);
                    var t = "all";
                    r.filterMarkers(t), u.filterMarkers(t), n("#clearSelection").hide()
                },
                resizeIframeWindows: function () {
                    var f = n("#iFrametabContainer .tabs-body").offset(),
                        t = n(window).width() - 310,
                        u = n(window).height() - f.top;
                    n(".tab_text").width(t + 5), n("#analyzedPageIframe").width(t).height(u - 20), n("#pageSourceIframe").width(t).height(u), n("#actualPageIframe").width(t).height(u), r.isIframeReady && r.positionMarkerImages(!1), i.resizeRedirectsTableContainer()
                },
                resizeRedirectsTableContainer: function () {
                    var t = n("#redirectsTableContainer").offset(),
                        i = n(window).height() - t.top;
                    n("#redirectsTableContainer").height(i)
                },
                setIframeSources: function (i) {
                    var r = n.validator.format("{0}&id={1}", t.renderedAnalyzedPageUrl, i),
                        u = n.validator.format("{0}&id={1}", t.renderedSourcePageUrl, i);
                    n("#analyzedPageIframe").attr("src", r), n("#pageSourceIframe").attr("src", u)
                },
                setOriginaliFrameSrc: function () {
                    n("#actualPageIframe").attr("url") != n("#actualPageIframe").attr("src") && n("#actualPageIframe").attr("src", n("#actualPageIframe").attr("url"))
                },
                resetIframeSources: function () {
                    n("#analyzedPageIframe").attr("src", ""), n("#actualPageIframe").attr("src", ""), n("#pageSourceIframe").attr("src", "")
                },
                analyzeUrlPoll: function (f) {
                    i.updateTimer = setTimeout(function () {
                        n.get(t.pageAnalysisResultUrl, {
                            id: f
                        }, function (t) {
                            if (t.Status == "Success") console.log(i.fetchAttemptCounter), i.seoPageFailureDetails = t.AnalysisDetails, i.populateRulesFilterTable(), n("#rulesFilterContainer").show(), n("#seoResultTable").show(), t.redirectChain != null && t.redirectChain.length > 1 && (n("#redirectsContainer").show(), i.populateRedirectsTable(t.redirectChain)), n(window).resize(i.resizeIframeWindows).trigger("resize"), n("#redirectsContainer").is(":visible") && n("#redirectsContainer").height() < 100 && n("#suggestionsShow").show(), i.setIframeSources(f), n("#analyzedPageIframe").load(r.go), n("#pageSourceIframe").load(u.go), n("#actualPageIframe").attr("url", t.requestUrl), setTimeout(function () {
                                n("#seoActivityIndicator").hide()
                            }, 2e3);
                            else if (t.Status == "UrlNotProcessed" && i.fetchAttemptCounter > 0) i.fetchAttemptCounter--, i.analyzeUrlPoll(f);
                            else {
                                if (n("#seoActivityIndicator").hide(), console.log("FetchAttempt count:" + i.fetchAttemptCounter), t.ErrMsg == null) return;
                                t.redirectChain != null && t.redirectChain.length > 1 && i.displayRedirectsTableOnError(t.redirectChain);
                                var e = '<label class="error" for="seoUrl" generated="false">{0}</label>';
                                n("div.validationErrorPlaceholder").html(n.validator.format(e, t.ErrMsg)), console.log("Error:" + t.ErrMsg)
                            }
                        })
                    }, i.updateInterval)
                },
                analyzeUrl: function () {
                    var r = t.requestId;
                    r != "" && i.validateParentIframe() && (n("#seoActivityIndicator").show(), i.fetchAttemptCounter = i.maxFetchAttempts, i.analyzeUrlPoll(r))
                },
                validateParentIframe: function () {
                    function r(n) {
                        return n.match(/:\/\/(.[^/?]+)/)[1]
                    }
                    var n, i;
                    return top.location == location ? (console.log("No parent iframe"), !1) : document.referrer ? (n = r(document.referrer), console.log(n), i = new RegExp(t.SeoSandboxParentIframeHost), n.match(i) == null ? (console.log("Invalid parent iframe"), !1) : !0) : (console.log("No referrer"), !1)
                },
                populateRulesFilterTable: function () {
                    if (i.seoPageFailureDetails != null) {
                        var t = [],
                            r = "",
                            u = "<tr seoseverity='{0}' id='ruleId_{1}'><td class='lpad row{0}'>{2}</td><td class='lpad'>{3}</td></tr>";
                        n.each(i.seoPageFailureDetails, function (i, f) {
                            r = n.validator.format(u, f.SeverityEnumString, i, f.ErrorCountFormatted, n("<div/>").text(f.Description).html()), t.push(r)
                        }), t.length > 0 ? (n("#rulesTable tbody").html(t.join("")), n("#rulesTable tbody tr").bind("click", i.rowClicked)) : (n("#rulesTable").attr("emptytable", "true"), n("#rulesTable").attr("emptytablesetup", "false"), initEmptyTables())
                    }
                },
                toggleRulesFilterTable: function () {
                    n("#suggestionsShow").toggle(function () {
                        n("#rulesFilterContainer").hide(), n("#suggestionsShow").text(wr.G_Show), i.resizeRedirectsTableContainer()
                    }, function () {
                        n("#rulesFilterContainer").show(), n("#suggestionsShow").text(wr.G_Hide), i.resizeRedirectsTableContainer()
                    })
                },
                populateRedirectsTable: function (t) {
                    var i = [],
                        r = "",
                        u = "<tr><td class='lpad'>{0}</td><td class='lpad'>{1}</td></tr>";
                    n.each(t, function (t, f) {
                        r = n.validator.format(u, f.StatusCode, f.Url), i.push(r)
                    }), i.length > 0 && n("#redirectsTable tbody").html(i.join(""))
                },
                toggleRedirects: function () {
                    n("#redirectsShow").toggle(function () {
                        n("#redirectsTable").show(), n("#redirectsShow").text(wr.G_Hide)
                    }, function () {
                        n("#redirectsTable").hide(), n("#redirectsShow").text(wr.G_Show)
                    })
                },
                displayRedirectsTableOnError: function (t) {
                    n("#seoResultTable").before(n("#redirectsContainer")), n("#redirectsContainer").show(), n("#redirectsShow").hide(), n("#redirectsTable").css({
                        width: "50%"
                    }), n("#redirectsTable").show(), i.populateRedirectsTable(t)
                },
                tabInitialize: function () {
                    var t = n("div.tabs-header a");
                    t.click(function () {
                        t.removeClass("on"), n(this).addClass("on"), n("div.tabs-body > div").removeClass("on"), n("div.tabs-body > div[name=" + n(this).attr("href") + "]").addClass("on"), n("#iFrametabContainer .analyzedPageImgTag").each(function () {
                            n(this).attr("state") == "0" && i.hideSeoFloat(n(this), !1)
                        }), n(this).attr("href") == "#tab3" && i.setOriginaliFrameSrc()
                    })
                },
                go: function () {
                    i.tabInitialize(), r.isIframeReady = !1, n("#iFrametabContainer .analyzedPageImgTag").click(i.showHideRuleDetail), n("#buttonContainer .seoArrowButton").mouseenter(function () {
                        n(this).addClass("seoArrowButtonHover")
                    }).mouseleave(function () {
                        n(this).removeClass("seoArrowButtonHover")
                    }).mousedown(function () {
                        n(this).addClass("seoArrowButtonClick")
                    }).mouseup(function () {
                        n(this).removeClass("seoArrowButtonClick")
                    }).click(i.markerArrowsClicked), n("#seoResultTable").hide(), n("#clearSelection").click(i.clearFilter), i.toggleRulesFilterTable(), i.toggleRedirects(), i.analyzeUrl()
                }
            };
        i.go()
    }
}(jQuery),
function (n) {
    n.fn.sitemove = function () {
        function f(i) {
            console.log("addParamSuccess", i), hideActivityIndicator(), unsetModalActivityIndicator(), t(), n("#sourceurl").val(""), n("#targeturl").val(""), n("#scope").val("2"), n("#sourceurl").prop("readonly", !1), n("#paramList table.wmtable span[sourceurl]").each(function () {
                n(this).attr("sourceurl") == i.SourceUrl && n(this).parent().parent().remove()
            });
            var r = n("<tr/>");
            r.append(n("<td/>")).append(n("<td/>").append(createExternalUrl(i.SourceUrl))).append(n("<td/>").append(createExternalUrl(i.TargetUrl))).append(n("<td/>").text(i.TypeString)).append(n("<td/>").text(i.ScopeString)).append(n("<td/>").text(i.Email)).append(n("<td/>").text(i.DateFormatted)), n("#paramList table.wmtable tbody").prepend(r), n("#paramList table.wmtable").trigger("update")
        }

        function e(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), t(), reportError(n)
        }

        function i(n, t) {
            if (t.length < 1) return t;
            if (t[t.length - 1] != "/" && (t += "/"), n == "0") {
                var i = t.indexOf("://");
                t = i > 0 ? t.substring(0, i + 3) + "*." + t.substring(i + 3) : "*." + t
            }
            return t += "*"
        }
        var t = function () {
            n("#sourceurl").prop("disabled", !1), n("#targeturl").prop("disabled", !1), n("#scope").prop("disabled", !1), n("#addParam").prop("disabled", !1)
        }, u = function () {
                n("#sourceurl").prop("disabled", !0), n("#targeturl").prop("disabled", !0), n("#scope").prop("disabled", !0), n("#addParam").prop("disabled", !0)
            }, r;
        n("#sitemoveconfirm").click(function () {
            if ((n("#sitemoveconfirmdialog").jqmHide(), n(this).attr("disabled") != "disabled" && !window.readOnlyMode) && n("#addParamForm").valid()) {
                var t = {
                    SourceUrl: n("#sourceurl").val(),
                    TargetUrl: n("#targeturl").val(),
                    Scope: n("#scope").val()
                };
                showActivityIndicator(!0), u(), getSvc().invoke("SubmitSiteMove", {
                    url: url,
                    settings: t
                }, f, e)
            }
        }), n("#addParam").click(function () {
            if (n("#addParamForm").valid()) {
                n("#confirmtype").text(n("#movetypeglobal").is(":checked") ? wr.SiteMove_TypeGlobal : wr.SiteMove_TypeLocal), n("#confirmscope").text(n("#scope > option:selected").text()), n("#confirmsource").text(n("#sourceurl").val()), n("#confirmtarget").text(n("#targeturl").val());
                var t = i(n("#scope").val(), n("#sourceurl").val()),
                    r = i(n("#scope").val(), n("#targeturl").val());
                n("#confirmexamplesource").text(t), n("#confirmexampletarget").text(r), n("#sitemoveconfirmdialog").jqmShow()
            }
        }), n("#sitemoveconfirmdialog").jqm({
            overlay: 10,
            modal: !0
        }), n("#addParamForm").validate(formValidateOptions), clickButtonOnEnter("#addParam", ["#sourceurl", "#targeturl"]), n("#scope").change(function () {
            n(this).val() == "0" ? (n("#sourceurl").val(url), n("#sourceurl").prop("readonly", !0)) : n(this).val() == "1" && n('#scope option[value="1"]').attr("locked") == "1" ? (n("#sourceurl").val(url), n("#sourceurl").prop("readonly", !0)) : n("#sourceurl").prop("readonly", !1)
        }), r = function (t, i) {
            n("#targeturl").val(t), n("#targetsitelink").text(i)
        }, n("#targetsitelink").click(function (t) {
            n("#movetypeglobal").is(":checked") || n("#movetypeglobal").click();
            var i = n("#targetsitelink").offset(),
                u, f = i.top + n("#targetsitelink").height();
            u = isRTL ? {
                right: i.right,
                top: f
            } : {
                left: i.left,
                top: f
            }, n("#siteSelector").siteSelector(null, t, {
                callback: r,
                offset: u,
                width: 350
            })
        }), n.browser.msie && n("#targetsitelinkcontainer").click(function (n) {
            n.stopPropagation()
        })
    }
}(jQuery),
function (n) {
    n.fn.diagnostics = function (t) {
        function r(t, i, r, u) {
            var e = n(i).val(),
                f;
            n(r).valid() && (f = null, f = n.validator.format("{0}&{1}={2}", t, u, encodeURIComponent(e)), window.location = f)
        }
        var u = function () {
            r(t.linkExplorerUrl, "#exploreUrl", "#linkExploreForm", "targetUrl")
        }, f = function () {
                r(t.fetchBingbotUrl, "#fetchUrl", "#botFetchForm", "paramurl")
            }, e = function () {
                r(t.markupValidatorUrl, "#markupUrl", "#markupValidateForm", "paramurl")
            }, o = function () {
                r(t.seoAnalyzerUrl, "#seoUrl", "#seoAnalyzeForm", "paramurl")
            }, i;
        n("#linkExplore").click(u), n("#botFetch").click(f), n("#markupValidate").click(e), n("#seoAnalyze").click(o), n("#linkExploreForm").validate(formValidateOptions), n("#botFetchForm").validate(formValidateOptions), n("#markupValidateForm").validate(formValidateOptions), n("#seoAnalyzeForm").validate(formValidateOptions), clickButtonOnEnter("#linkExplore", "#exploreUrl"), clickButtonOnEnter("#botFetch", "#fetchUrl"), clickButtonOnEnter("#markupValidate", "#markupUrl"), clickButtonOnEnter("#seoAnalyze", "#seoUrl"), i = n("#keywordresearchsearchform"), i.validate(formValidateOptions), n("#keywordresearchsearch").click(function () {
            i.valid() && (showActivityIndicator(!0), fixPlaceholdersBeforeInFormSubmit(i), i.submit())
        }), n("#keywords").keydown(textAreaWithLineLimit(t.keywordsLineLimit))
    }
}(jQuery),
function (n) {
    n.fn.verifybingbot = function () {
        n("#addParamForm").validate(n.extend(!0, {}, formValidateOptions, {
            onsubmit: !0
        })), n("#addParam").click(function () {
            n("#addParamForm").valid() && (showActivityIndicator(!0), fixPlaceholdersBeforeInFormSubmit(n("#addParamForm")), n("#addParamForm").submit())
        }), clickButtonOnEnter("#addParam", ["#ip"])
    }
}(jQuery),
function (n) {
    n.fn.developerSelector = function (t) {
        function g() {
            showActivityIndicator(!1), n("#developersTableContainer").load(t.developersReportTableUrl, {
                startDate: n("#keywordRangeFrom").val(),
                endDate: n("#keywordRangeTo").val(),
                appid: t.appId
            }, function () {
                hideActivityIndicator()
            })
        }

        function d() {
            var i = n(this).parent().parent().parent().find(":checkbox"),
                r;
            return console.log(n(this)), console.log(i), r = i.attr("appID"), document.location.href = [t.developersEditAppIdUrl, "?appId=", r].join(""), !1
        }

        function k() {
            var i, r;
            console.log(n(this)), i = n(this).parent().parent().parent().find(":checkbox"), console.log(i), r = i.attr("appID"), document.location.href = [t.developersReportAppIdUrl, "?appId=", r].join("")
        }

        function b() {
            document.location.href = t.developersShowAppIdUrl
        }

        function u(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function f(t) {
            var f, r;
            if (n("#DevelopersSubmitForm").validate(formValidateOptions), $id("DevelopersSubmitForm").valid()) {
                f = null, t && (f = $id("appId").text());
                var e = $id("appIdName").val(),
                    o = $id("appIdDesc").val(),
                    s = $id("appIdCompName").val(),
                    h = $id("appIdCountryRegion").val(),
                    c = $id("appIdEmailAddress").val(),
                    i, l = $id("appIdWebsite").val();
                i = $id("promotionOffers").is(":checked") ? "on" : "off", console.log(i), t ? (showActivityIndicator(!0), r = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/"), r.invoke("SubmitEditAppIdDataToSr", {
                    appId: f,
                    appName: e,
                    appDescription: o,
                    companyName: s,
                    countryRegion: h,
                    contactEmail: c,
                    emailPromotion: i,
                    website: l
                }, p, u)) : (showActivityIndicator(!0), r = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/"), r.invoke("SubmitAppIdDataToSr", {
                    appName: e,
                    appDescription: o,
                    companyName: s,
                    countryRegion: h,
                    contactEmail: c,
                    emailPromotion: i,
                    website: l
                }, w, u))
            }
        }

        function w() {
            n("#thankyouPage").jqm({
                overlay: 5,
                modal: !0
            }).jqmShow()
        }

        function p() {
            e()
        }

        function e() {
            hideActivityIndicator(), unsetModalActivityIndicator(), n("#submitEditAppid").hide(), n("#submitAppid").hide(), n("#cancelAppid").hide(), document.location.href = t.developersShowAppIdUrl
        }

        function y() {
            e()
        }

        function v() {
            f(!0)
        }

        function a() {
            document.location.href = t.developersCreateAppIdUrl
        }

        function l() {
            f(!1)
        }

        function c() {
            if (r = [], n("table.wmtable :checked").each(function () {
                var t = n(this),
                    i = t.attr("appID");
                r.push(i)
            }), r.length > 0) {
                var t = "";
                for (i = 0; i < r.length; i++) t = t + "<tr><td>" + r[i] + "</td></tr>";
                n("#deleteConfirmPage tbody").prepend(t), n("#deleteConfirmPage").jqm({
                    overlay: 5,
                    modal: !0
                }).jqmShow()
            }
        }

        function h() {
            r = [], triggerCheckboxChange(), n("#deleteConfirmPage").hide(), n("#delTable > tbody").empty()
        }

        function s() {
            r = [], document.location.href = t.developersShowAppIdUrl
        }

        function o() {
            n("#deleteConfirmPage").hide(), showActivityIndicator(!0);
            var t = new serviceProxy(containerPrefix + "/svc/Webmaster.svc/");
            t.invoke("DeleteAppIds", {
                appIds: r
            }, s, u)
        }

        function tt() {
            var n = document.getElementById("appIdsName"),
                i = n.options[n.selectedIndex].value,
                r = n.options[n.selectedIndex].text;
            console.log(r + i), document.location.href = [t.developersReportAppIdUrl, "?appId=", i].join("")
        }

        function it() {
            n("#appIdsName").live("change", function () {
                tt()
            }), n("#appIdDevelopersList table.wmtable :checkbox").live("change", nt), n('table.wmtable tbody  a[action="edit"]').click(d), n('table.wmtable tbody a[action="appId"]').click(k), n("#cancelAppid").click(b), n("#submitEditAppid").click(v), n("#addAppId").click(a), n("#submitAppid").click(l), n("#deleteAppId").click(c), n("#deleteConfirm").click(o), n("#deleteCancel").click(h), n("#thankYouOk").click(y);
            var r = getStandartChartOptions(),
                u = n("#chart"),
                f = n('.charts-legend input[type="checkbox"]'),
                i = [
                    [],
                    []
                ],
                e;
            splitChartData(t.data, i, 2), e = setupChart(u, r, f, i, "#keywordRangeFrom", "#keywordRangeTo", "#keywordRangeContainer", "#site-nav-toggle-button"), n("#keywordRangeContainer").live("dateChanged", g)
        }
        var rt = {}, r = [],
            nt = setupGridCheckButtonEnabler("#appIdDevelopersList table.wmtable", "#deleteAppId");
        it()
    }
}(jQuery),
function (n) {
    n.fn.adminappidsreport = function () {
        n("#submitParam").click(function () {
            n("#addParamForm").submit()
        })
    }
}(jQuery),
function (n) {
    n.fn.articleCategories = function (t) {
        function r() {
            n("#helpCategoriesTableContainer tbody tr").removeClass("row-selected");
            var r = n(this).parent(),
                i = n("td > input", r),
                u = n("td:nth-child(2)", r).text(),
                f = i.attr("categoryId"),
                e = i.attr("culture"),
                o = i.attr("sortOrder"),
                s = i.attr("landingArticleId");
            n("#title").val(u), n("#SortOrder").val(o), n("#LandingArticleId").val(s), n("#Culture").val(e), n("#categoryId").val(f), n("#submitParam").val("Update Selected"), n("#clearSelection").show(), n("#categoryForm").attr("action", t.updateCatoryUrl), r.addClass("row-selected")
        }

        function u() {
            n("#categoryForm").valid() && n("#categoryForm").submit()
        }

        function f() {
            n("#title").val(""), n("#SortOrder").val(""), n("#LandingArticleId").val(""), n("#helpCategoriesTableContainer tbody tr").removeClass("row-selected"), n("#submitParam").val("Add"), n("#clearSelection").hide(), n("#categoryForm").attr("action", t.addCategoryUrl)
        }

        function e() {
            var t = i.filter(":checked");
            n("#deleteCategory").prop("disabled", t.length == 0)
        }

        function o() {
            n.ajax({
                type: "POST",
                url: t.deleteCatoryUrl,
                data: s(),
                contentType: "application/json;",
                dataType: "json",
                success: function () {
                    window.location.reload(!0)
                }
            })
        }

        function s() {
            var t = [],
                r = i.filter(":checked");
            return n.each(r, function () {
                var i = {
                    categoryid: n(this).attr("categoryid"),
                    culture: n(this).attr("culture")
                };
                t.push(i)
            }), JSON.stringify(t)
        }

        function h() {
            n("#helpCategoriesTableContainer tbody td:not(:first-child)").click(r), n("#submitParam").click(u), n("#clearSelection").click(f), n("#categoryForm").attr("action", t.addCategoryUrl), n("#title").val(""), n("#SortOrder").val(""), n("#deleteCategory").prop("disabled", !0), i.change(e), n("#deleteCategory").click(o), n("#categoryForm").validate(formValidateOptions)
        }
        var i = n("#helpCategoriesTableContainer input[type='checkbox']");
        h()
    }
}(jQuery),
function (n) {
    n.fn.articledetail = function (t) {
        function i() {
            window.open(t.previewUrl, "helppreview")
        }

        function r() {
            n("#helpArticleForm input[type='text']").each(function () {
                n(this).val() == n(this).attr("placeholder") && n(this).val("")
            }), (n("#state").val() == 1 || n("#helpArticleForm").valid()) && (n("#helpArticleForm").attr("action", t.saveDraftUrl), n("#helpArticleForm").submit())
        }

        function u() {
            n("#helpArticleForm").attr("action", t.discardDraftUrl), n("#helpArticleForm").submit()
        }

        function f() {
            n("#helpArticleForm").attr("action", t.publishUrl), n("#helpArticleForm").submit()
        }

        function e() {
            n("#helpArticleForm").attr("action", t.unPublishUrl), n("#helpArticleForm").submit()
        }

        function o() {
            n("#helpArticleForm").attr("action", t.deleteUrl), n("#helpArticleForm").submit()
        }

        function s() {
            document.location.href = t.cancelUrl
        }

        function h() {
            var t = n("#state").val();
            n(".filter-block input").removeAttr("disabled");
            switch (t) {
            case "1":
                n("#discardDraft").hide(), n("#publishArticle").prop("disabled", !0), n("#helpArticleForm input[type='text']").prop("readOnly", !0), n("#helpArticleForm select").prop("readOnly", !0), n("#helpArticleForm textarea").prop("readOnly", !0);
                break;
            case "2":
                n("#discardDraft").hide(), n("#unPublishArticle").hide()
            }
        }

        function c() {
            n.validator.addClassRules({
                helpTextRequired: {
                    required: function () {
                        var t = n("#RedirectUrl");
                        return t.val().length == 0 ? !0 : isValueSameAsPalceHolder(t.val(), t)
                    }
                }
            })
        }

        function l() {
            h(), c(), n("#preview").click(i), n("#saveDraft").click(r), n("#discardDraft").click(u), n("#publishArticle").click(f), n("#unPublishArticle").click(e), n("#deleteArticle").click(o), n("#cancel").click(s), n("#CategoryId").val(t.selectedCategory), n("#helpArticleForm").validate(formValidateOptions)
        }
        l()
    }
}(jQuery),
function (n) {
    n.fn.articlesreport = function (t) {
        function u() {
            var t = n("td > input", n(this).parent()).attr("url");
            document.location.href = t
        }

        function f() {
            var f = i.filter(":checked"),
                t = !0,
                r = !0,
                u = !1;
            n.each(f, function () {
                u = !0, n(this).attr("state") == "1" && (t = !1), n(this).attr("state") == "2" && (r = !1)
            }), u == !1 && (t = !1, r = !1), n("#publishArticle").prop("disabled", !t), n("#unPublishArticle").prop("disabled", !r), n("#deleteArticle").prop("disabled", !u)
        }

        function e() {
            document.location.href = t.addNewArticleUrl
        }

        function o() {
            r(t.publishBulkUrl)
        }

        function s() {
            r(t.unpublishBulkUrl)
        }

        function h() {
            r(t.deleteBulkUrl)
        }

        function r(t) {
            n.ajax({
                type: "POST",
                url: t,
                data: c(),
                contentType: "application/json;",
                dataType: "json",
                success: function () {
                    window.location.reload(!0)
                }
            })
        }

        function c() {
            var t = [],
                r = i.filter(":checked");
            return n.each(r, function () {
                var i = {
                    articleid: n(this).attr("articleid"),
                    version: n(this).attr("version"),
                    culture: n(this).attr("culture")
                };
                t.push(i)
            }), JSON.stringify(t)
        }

        function l() {
            i.prop("checked", !1), n("#addArticle").prop("disabled", !1), n("#publishArticle").prop("disabled", !0), n("#unPublishArticle").prop("disabled", !0), n("#deleteArticle").prop("disabled", !0), n("#helpArticlesTableContainer tbody td:not(:nth-child(1), :nth-child(2))").click(u), i.change(f), n("#addArticle").click(e), n("#publishArticle").click(o), n("#unPublishArticle").click(s), n("#deleteArticle").click(h)
        }
        var i = n("#helpArticlesTableContainer input[type='checkbox']");
        l()
    }
}(jQuery),
function (n) {
    n.fn.adminblockusers = function () {
        function f(t) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), n("table.wmtable :checkbox").each(function () {
                n(this).attr("userid") == t.UserId && n(this).attr("email") == t.Email && n(this).parent().parent().remove()
            }), n("#email").val(""), n('input[type="checkbox"][name="role"]', "#addParamForm").each(function () {
                n(this).next().removeClass("on"), n(this).prop("checked", !1)
            });
            var r = n("<tr/>");
            r.append(n("<td/>").append(n("<input>", {
                type: "checkbox",
                userid: t.UserId,
                email: t.Email
            })).append(n("<label/>"))).append(n("<td/>").text(t.Email)).append(n("<td/>").text(t.UserId)), n("#userList table.wmtable tbody").prepend(r)
        }

        function e(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), reportError(n)
        }

        function o(i, r) {
            var f, u;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), f = {}, u = 0; u < r.length; ++u) f[r[u].UserId + "|" + r[u].Email] = !0;
            n("#userList table.wmtable input[type=checkbox]").each(function () {
                var t = n(this).attr("userid") + "|" + n(this).attr("email");
                f[t] && n(this).parent().parent().remove()
            }), t()
        }

        function s(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }
        var t = setupGridCheckButtonEnabler("#userList table.wmtable", "#removeParam"),
            h = !1,
            r = null,
            i = function () {
                n("#email").attr("disabled", null), n("#addParam").attr("disabled", null)
            }, u = function () {
                n("#email").attr("disabled", "disabled"), n("#addParam").attr("disabled", "disabled")
            };
        n("#addParam").click(function () {
            if (n(this).attr("disabled") != "disabled" && n("#addParamForm").valid()) {
                var t = n.trim(n("#email").val()),
                    i = n('input[type="checkbox"][name="role"]:checked', "#addParamForm"),
                    s = r,
                    o = [];
                i.each(function () {
                    o.push(n(this).val())
                }), showActivityIndicator(!0), u(), getSvc().invoke("BlockUser", {
                    email: t
                }, f, e)
            }
        }), n("#editParam").click(function () {
            n(this).attr("disabled") != "disabled" && n("#addParam").click()
        }), n("#removeParam").click(function () {
            var i = [];
            n("#userList table.wmtable :checked").each(function () {
                var t = n(this),
                    r = {
                        Email: t.attr("email"),
                        UserId: t.attr("userid")
                    };
                i.push(r)
            }), i.length > 0 && (showActivityIndicator(!0), getSvc().invoke("UnblockUser", {
                blockedUsers: i
            }, function (n) {
                o(n, i)
            }, s)), t()
        }), n("#addParamForm").validate(formValidateOptions), n("#userList table.wmtable :checkbox").live("change", t), clickButtonOnEnter("#addParam", ["#email"])
    }
}(jQuery),
function (n) {
    n.fn.admindebugsitemaps = function (t) {
        function i(i) {
            showActivityIndicator(!0), n.getJSON(t.ResubmitUrl, {
                feedUrl: i
            }, function () {
                hideActivityIndicator(), unsetModalActivityIndicator()
            }).error(function (n) {
                hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
            })
        }
        n("#submitParam").click(function () {
            n("#addParamForm").valid() && n("#addParamForm").submit()
        }), n("#resubmitParam").click(function () {
            n("#addParamForm").valid() && i(n("#feedurl").val())
        }), n("#addParamForm").validate(formValidateOptions), clickButtonOnEnter("#submitParam", ["#feedurl", "#siteurl"])
    }
}(jQuery),
function (n) {
    n.fn.adminfetchurl = function (t, i) {
        var r = function () {
            n("#fetchedContent").load(t.updateUrl)
        };
        if (i == "update") {
            setTimeout(r, 1e3);
            return
        }
        if (i == "prettify") {
            n(".prettyprint").length > 0 && prettyPrint();
            return
        }
        n("#submitParam").click(function () {
            n("#addParamForm").submit()
        }), t.updateUrl != "" && setTimeout(r, 1e3)
    }
}(jQuery),
function (n) {
    n.fn.adminglobalusers = function () {
        function e(t) {
            hideActivityIndicator(), unsetModalActivityIndicator(), u(), f(), n("table.wmtable :checkbox").each(function () {
                n(this).attr("userid") == t.UserId && n(this).attr("email") == t.Email && n(this).parent().parent().remove()
            }), n("#email").val(""), n('input[type="checkbox"][name="role"]', "#addParamForm").each(function () {
                n(this).next().removeClass("on"), n(this).prop("checked", !1)
            });
            var i = n("<tr/>");
            i.append(n("<td/>").append(n("<input>", {
                type: "checkbox",
                userid: t.UserId,
                email: t.Email,
                roles: t.Roles
            })).append(n("<label/>"))).append(n("<td/>").append(n("<a>", {
                href: "javascript:void(0)"
            }).text(t.Email))).append(n("<td/>").text(t.UserId)).append(n("<td/>").text(t.RolesFormatted)), n("#userList table.wmtable tbody").prepend(i)
        }

        function o(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), u(), reportError(n)
        }

        function h(t, i) {
            var f, u;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), f = {}, u = 0; u < i.length; ++u) f[i[u].UserId + "|" + i[u].Email] = !0;
            n("#userList table.wmtable input[type=checkbox]").each(function () {
                var t = n(this).attr("userid") + "|" + n(this).attr("email");
                f[t] && n(this).parent().parent().remove()
            }), r()
        }

        function c(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }
        var r = setupGridCheckButtonEnabler("#userList table.wmtable", "#removeParam"),
            t = !1,
            i = null,
            u = function () {
                n("#email").attr("disabled", null), n("#addParam").attr("disabled", null), n("#editParam").attr("disabled", null)
            }, s = function () {
                n("#email").attr("disabled", "disabled"), n("#addParam").attr("disabled", "disabled"), n("#editParam").attr("disabled", "disabled")
            }, f = function () {
                t && (i = null, t = !1, n("#addParam").show(), n("#editParam").hide(), n("#addCaption").show(), n("#editCaption").hide(), n("#userList table.wmtable tr.row-selected").removeClass("row-selected"))
            };
        n("#addParam").click(function () {
            if (n(this).attr("disabled") != "disabled" && n("#addParamForm").valid()) {
                var u = n.trim(n("#email").val()),
                    f = n('input[type="checkbox"][name="role"]:checked', "#addParamForm"),
                    h = i,
                    r = [];
                f.each(function () {
                    r.push(n(this).val())
                }), showActivityIndicator(!0), s(), t ? getSvc().invoke("UpdateGlobalRoles", {
                    roles: [{
                        Email: u,
                        UserId: h
                    }],
                    access: r
                }, function (n) {
                    e(n[0])
                }, o) : getSvc().invoke("AddGlobalRoles", {
                    email: u,
                    access: r
                }, e, o)
            }
        }), n("#editParam").click(function () {
            n(this).attr("disabled") != "disabled" && n("#addParam").click()
        }), n("#removeParam").click(function () {
            var t = [];
            n("#userList table.wmtable :checked").each(function () {
                var i = n(this),
                    r = {
                        Email: i.attr("email"),
                        UserId: i.attr("userid")
                    };
                t.push(r)
            }), t.length > 0 && (showActivityIndicator(!0), getSvc().invoke("RemoveGlobalRoles", {
                roles: t
            }, function (n) {
                h(n, t)
            }, c))
        }), n("#addParamForm").validate(formValidateOptions), n("#userList table.wmtable :checkbox").live("change", r), n("#userList table.wmtable a").live("click", function () {
            var r, f, u, e;
            if (n("#addParam").attr("disabled") != "disabled") {
                r = n(this).parent().parent().find('input[type="checkbox"]'), n("#email").val(r.attr("email")), n("#addParam").hide(), n("#editParam").show(), n("#addCaption").hide(), n("#editCaption").show(), f = r.attr("roles").split(","), u = {};
                for (e in f) u[f[e]] = !0;
                n('input[type="checkbox"][name="role"]', "#addParamForm").each(function () {
                    var t = u[n(this).val()];
                    console.log(u, n(this).val(), t), t ? n(this).next().addClass("on") : n(this).next().removeClass("on"), n(this).prop("checked", t)
                }), i = r.attr("userid"), t = !0, n("#userList table.wmtable tr.row-selected").removeClass("row-selected"), n(this).parent().parent().addClass("row-selected"), scrollToTop()
            }
        }), n("#email").keyup(function () {
            f()
        }), clickButtonOnEnter("#addParam", ["#email"])
    }
}(jQuery),
function (n) {
    n.fn.helpImage = function (t) {
        function i() {
            n("#imageForm").valid() && (n("#imageForm").attr("action", t.saveImageUrl), n("#imageForm").submit())
        }

        function r() {
            n("#imageForm").attr("action", t.deleteImageUrl), n("#imageForm").submit()
        }

        function u() {
            n("#imageForm").attr("action", t.cancelImageUrl), n("#imageForm").submit()
        }

        function f() {
            n("#save").prop("disabled", !1), n("#delete").prop("disabled", !1), n("#cancel").prop("disabled", !1), n("#save").click(i), n("#delete").click(r), n("#cancel").click(u), n("#imageForm").validate(formValidateOptions), t.imageExists && (n(".fileInputSelector .fields-required-marker").hide(), n("#httpFile").removeClass("required"))
        }
        f()
    }
}(jQuery),
function (n) {
    n.fn.helpImages = function (t) {
        function r() {
            var t = n("td > input", n(this).parent()).attr("url");
            document.location.href = t
        }

        function u() {
            var t = i.filter(":checked");
            n("#deleteimages").prop("disabled", t.length == 0)
        }

        function f() {
            document.location.href = t.addImageUrl
        }

        function e() {
            n.ajax({
                type: "POST",
                url: t.deleteImageUrl,
                data: o(),
                contentType: "application/json;",
                dataType: "json",
                success: function () {
                    window.location.reload(!0)
                }
            })
        }

        function o() {
            var t = [],
                r = i.filter(":checked");
            return n.each(r, function () {
                var i = {
                    imageId: n(this).attr("imageId"),
                    culture: n(this).attr("culture")
                };
                t.push(i)
            }), JSON.stringify(t)
        }

        function s() {
            i.prop("checked", !1), n("#addImage").prop("disabled", !1), n("#deleteimages").prop("deleteImages", !0), n("#helpImagesTableContainer tbody td:not(:first-child)").click(r), i.change(u), n("#addImage").click(f), n("#deleteimages").click(e)
        }
        var i = n("#helpImagesTableContainer input[type='checkbox']");
        s()
    }
}(jQuery),
function (n) {
    n.fn.admininvalidatecache = function () {
        n("#submitParam").click(function () {
            n("#addParamForm").submit()
        })
    }
}(jQuery),
function (n) {
    var t = {}, r = {}, i;
    n.fn.adminmessaging = function (u, f) {
        function v() {
            t = {}, n("tbody", "#messageParamList").empty()
        }

        function y(i) {
            var u = n("#messageRowTemplate").html(),
                r = n("<tr/>", {
                    parameterName: i.Name
                });
            r.append(n("<td/>").append(n("<a>", {
                href: "javascript:void(0)"
            }).text(i.Name))).append(n("<td/>").append(n("<input>", {
                type: "text",
                parameterName: i.Name
            }).val(i.Value))), n("#messageParamList tbody").append(r), t[i.Name] = i.Value
        }

        function o() {
            var s = n("#message").val(),
                u = s.match(/%%[a-z0-9_]+%%/gi),
                e, r, i, o, f;
            if (u == null) n("tfoot", "#messageParamList").show(), t = {}, n("tbody", "#messageParamList").empty();
            else {
                for (n("tfoot", "#messageParamList").hide(), e = {}, r = 0; r < u.length; ++r) i = u[r].substr(2, u[r].length - 4), t[i] == null ? y({
                    Name: i,
                    Value: ""
                }) : (o = n('tr[parameterName="' + i + '"]', "#messageParamList"), n("input", o).val(t[i])), e[i] = !0;
                for (f in t) e[f] || (delete t[f], n('tr[parameterName="' + f + '"]', "#messageParamList").remove())
            }
        }

        function s() {
            var r = n("#messagepreset").val(),
                t, i;
            n("#custommessage").is(":checked") ? (n("#messagepreset").hide(), n("#message").prop("disabled", !1)) : (n("#messagepreset").show(), n("#message").prop("disabled", !0), t = n("#messagepreset").children('option[value="' + r + '"]'), i = t.attr("key"), n("#message").val(n("#" + i + "Detail").html())), v(), o()
        }

        function h(r) {
            var f = n.trim(n("#siteurl").val()),
                u;
            return isValueSameAsPalceHolder(f, n("#siteurl")) && (f = ""), u = {
                SiteUrl: f,
                MessageType: n("#messagetype").val(),
                Subject: n("#customsubject").is(":checked") ? n.trim(n("#subject").val()) : n("#subjectpreset").val(),
                Body: n("#custommessage").is(":checked") ? n.trim(n("#message").val()) : n("#messagepreset").val(),
                Params: JSON.stringify(t),
                State: r ? 1 : 0,
                Market: n.trim(n("#market").val())
            }, i != null && (i.State != 0 && (u.State = i.State), u = n.extend(i, u)), u
        }

        function c(t) {
            var i = n("<tr/>");
            i.append(n("<td/>").append(n("<input>", {
                type: "checkbox",
                messageid: t.MessageId,
                category: t.Category,
                state: t.State
            })).append(n("<label/>"))).append(n("<td/>").text(t.Subject)).append(n("<td/>").append(n("<span>", {
                "class": "url-text"
            }).text(t.SiteUrl != null && t.SiteUrl != "") ? t.SiteUrl : wr.AdminMessaging_AllDomains)).append(n("<td/>").text(u.messageStates[t.State])).append(n("<td/>").text(u.messageTypes[t.MessageType])).append(n("<td/>").text(t.DateFormatted)), n("#messageList table.wmtable tbody").prepend(i)
        }

        function p(t) {
            hideActivityIndicator(), unsetModalActivityIndicator(), console.log("addParamSuccess data=", t), n("table.wmtable :checkbox").each(function () {
                n(this).attr("messageid") == t.MessageId && n(this).parent().parent().remove()
            }), c(t), n("#messageDialog").jqmHide()
        }

        function w(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function b(t, i) {
            var u, r;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), u = {}, r = 0; r < i.length; ++r) u[i[r].MessageId] = !0;
            n("#messageList table.wmtable input[type=checkbox]").each(function () {
                var t = n(this).attr("messageid");
                u[t] && n(this).parent().parent().remove()
            });
            for (r in t) c(t[r]);
            e()
        }

        function k(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function l(t) {
            if (n("#addParamForm").valid()) {
                var r = h(t);
                console.log("AddMessage", r), showActivityIndicator(!0), getSvc().invoke(i == null ? "AddAdminMessages" : "UpdateAdminMessages", {
                    message: r
                }, p, w)
            }
        }

        function a() {
            n("#save").click(function () {
                n(this).attr("disabled") != "disabled" && l(!1)
            }), n("#send").click(function () {
                n(this).attr("disabled") != "disabled" && l(!0)
            }), n("#preview").click(function () {
                if (n(this).attr("disabled") != "disabled") {
                    showActivityIndicator(!0);
                    var t = h(!1);
                    t.__RequestVerificationToken = getRequestVerificationToken("#addParamForm"), n.post(u.previewDialogUrl, t, function (t) {
                        n("#messageDialog").jqmHide(), showPopupModalDialog("#messagePreviewDialog", t, {
                            onHide: function (t) {
                                t.w.hide(), t.o && t.o.remove(), n("#messageDialog").jqmShow()
                            }
                        }), hideActivityIndicator()
                    }).error(function () {
                        showErrorMessage(null, wr.G_Error)
                    })
                }
            }), n("#customsubject").change(function () {
                n(this).prop("checked") ? (n("#subjectpreset").hide(), n("#subject").show()) : (n("#subjectpreset").show(), n("#subject").hide())
            }), n("#custommessage").change(s);
            var i = null;
            n("#message").keyup(function () {
                i != null && clearTimeout(i), i = setTimeout(o, 500)
            }), n("#messagepreset").change(s), n("input", "#messageParamList").live("keyup", function () {
                t[n(this).attr("parametername")] = n(this).val()
            }), s()
        }
        if (f == "setparameters") {
            r = u;
            return
        }
        var e = setupGridCheckButtonEnabler("#messageList table.wmtable", "#removeParam");
        n("#addParam").click(function () {
            n(this).attr("disabled") != "disabled" && (showActivityIndicator(!0), n.post(u.addDialogUrl, function (n) {
                showPopupModalDialog("#messageDialog", n), hideActivityIndicator(), a(), i = null
            }))
        }), n("#removeParam").click(function () {
            var t = [];
            n("#messageList table.wmtable :checked").each(function () {
                var i = n(this),
                    r = {
                        MessageId: i.attr("messageid")
                    };
                t.push(r)
            }), t.length > 0 && (showActivityIndicator(!0), getSvc().invoke("DeleteAdminMessages", {
                messages: t
            }, function (n) {
                b(n, t)
            }, k)), e()
        }), n("#messageList tbody tr td").live("click", function () {
            if (!(n('input[type="checkbox"]', n(this)).length > 0)) {
                var f = n('input[type="checkbox"]', n(this).parent()),
                    e = f.attr("messageid"),
                    s = f.attr("state"),
                    c = f.attr("category");
                r = null, showActivityIndicator(!0), n.post(n.validator.format(u.editDialogUrl, encodeURI(e)), function (n) {
                    showPopupModalDialog("#messageDialog", n), hideActivityIndicator(), a(), r != null && (t = r, o(), i = h(!1), i.MessageId = e, i.State = s, i.Category = c)
                })
            }
        }), n("#messageList table.wmtable :checkbox").live("change", e)
    }
}(jQuery),
function (n) {
    n.fn.adminResetQuota = function () {
        n("#resetParam").click(function () {
            n("#addParamForm").valid() && n("#addParamForm").submit()
        }), n("#addParamForm").validate(formValidateOptions), clickButtonOnEnter("#resetParam", ["#siteurl"])
    }
}(jQuery),
function (n) {
    n.fn.adminsubmiturls = function () {
        function r(i, r) {
            var f, u;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), t(), n("#urls").val(""), f = [], f.push(r + ":"), u = 0; u < i.length; ++u) f.push('<a href="' + i[u] + '" target="blank">' + i[u] + "</a>");
            n("#resultArea").html(f.join("<br/>"))
        }

        function u(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), t(), reportError(n)
        }
        var t = function () {
            n("#urls").attr("disabled", null), n("#addParam").attr("disabled", null)
        }, i = function () {
                n("#urls").attr("disabled", "disabled"), n("#addParam").attr("disabled", "disabled")
            };
        n("#addParam").click(function () {
            var t, f;
            if (n(this).attr("disabled") != "disabled" && !window.readOnlyMode && n("#addParamForm").valid()) {
                var e = n("#urls").val().split("\n"),
                    o = [],
                    s = n('input[type="radio"]:checked', "#addParamForm").val();
                for (t = 0; t < e.length; ++t) f = n.trim(e[t]), f != "" && o.push(f);
                showActivityIndicator(!0), i(), getSvc().invoke("AdminSubmitUrls", {
                    target: s,
                    urlList: o
                }, function (n) {
                    r(n, s)
                }, u)
            }
        }), n("#addParamForm").validate(n.merge({
            onkeyup: !1,
            focusCleanup: !0,
            errorPlacement: function () {}
        }, formValidateOptions)), n("#urls").urlTextArea()
    }
}(jQuery),
function (n) {
    n.fn.admintermofuse = function (t) {
        function u(t) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), n("#tou").val("");
            var r = n("<tr/>");
            r.append(n("<td/>").append(n("<span>", {
                feature: t.FeatureName,
                version: t.Version,
                isactive: t.IsActive ? "1" : "0"
            }).text(t.FeatureName))).append(n("<td/>").text(t.Version)).append(n("<td/>").text(t.IsActive ? wr.AdminTermOfUse_StatusActive : wr.AdminTermOfUse_StatusInactive)).append(n("<td/>").text(t.DateFormatted)), n("#touList table.wmtable tbody").prepend(r)
        }

        function f(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), reportError(n)
        }

        function e(t, r, u) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), n("#touList table.wmtable span").each(function () {
                if (n(this).attr("feature") == r) {
                    var t = n(n(this).parent().parent().children("td")[2]);
                    n(this).attr("version") == u ? t.text(wr.AdminTermOfUse_StatusActive) : t.text(wr.AdminTermOfUse_StatusInactive)
                }
            })
        }

        function o(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), i(), reportError(n)
        }
        var i = function () {
            n("#tou").attr("disabled", null), n("#featuretoadd").attr("disabled", null), n("#upload").attr("disabled", null)
        }, r = function () {
                n("#tou").attr("disabled", "disabled"), n("#featuretoadd").attr("disabled", "disabled"), n("#upload").attr("disabled", "disabled")
            };
        n("#upload").click(function () {
            if (n(this).attr("disabled") != "disabled" && n("#addParamForm").valid()) {
                var t = n("#tou").val(),
                    i = n("#featuretoadd").val();
                showActivityIndicator(!0), r(), getSvc().invoke("UploadTou", {
                    featureName: i,
                    text: t
                }, u, f)
            }
        }), n("#touList tbody tr").click(function () {
            var f = n("td:first span", n(this)),
                i = f.attr("feature"),
                u = f.attr("version");
            showActivityIndicator(!0), n.post(n.validator.format(t.dialogUrl, encodeURI(i), encodeURI(u)), function (t) {
                showPopupModalDialog("#touViewDialog", t), hideActivityIndicator(), n("#activate").click(function () {
                    n("#touViewDialog").jqmHide().remove(), showActivityIndicator(!0), r(), getSvc().invoke("ActivateTou", {
                        tou: {
                            FeatureName: i,
                            Version: u
                        }
                    }, function (n) {
                        e(n, i, u)
                    }, o)
                })
            })
        }), n("#addParamForm").validate(n.merge({
            onkeyup: !1,
            focusCleanup: !0,
            errorPlacement: function () {}
        }, formValidateOptions)), n("#feature").change(function () {
            n("#filterForm").submit()
        })
    }
}(jQuery),
function (n) {
    n.fn.adminusers = function () {
        function h(e) {
            var o, h;
            console.log("addParamSuccess", e), hideActivityIndicator(), unsetModalActivityIndicator(), s(), t && (o = {}, o[i + "|" + u + "|" + r] = !0, n("#userList table.wmtable input[type=checkbox]").each(function () {
                var t = n(this).attr("userid") + "|" + n(this).attr("url") + "|" + n(this).attr("verificationurl");
                o[t] && n(this).parent().parent().remove()
            })), f(), n("#email").val(""), h = n("<tr/>"), h.append(n("<td/>").append(n("<input>", {
                type: "checkbox",
                email: e.Email,
                userid: e.UserId,
                url: e.Site,
                verificationurl: e.VerificationSite,
                role: e.Role,
                authenticationcode: e.AuthenticationCode,
                delegatedcode: e.DelegatedCode,
                delegatedcodeowneremail: e.DelegatedCodeOwnerEmail,
                delegatorid: e.DelegatorId,
                delegatoremail: e.DelegatorEmail
            })).append(n("<label/>"))).append(n("<td/>").append(n("<a>", {
                href: "javascript:void(0)"
            }).text(e.Email))).append(n("<td/>").append(n("<span>", {
                "class": "url-text"
            }).text(e.Site))).append(n("<td/>").append(n("<span>", {
                "class": "url-text"
            }).text(e.VerificationSite))).append(n("<td/>").text(e.RolesFormatted)).append(n("<td/>").text(e.DelegatedCodeOwnerEmail != null ? e.DelegatedCodeOwnerEmail : "")).append(n("<td/>").text(e.DelegatorEmail != null ? e.DelegatorEmail : "")).append(n("<td/>").text(e.AuthenticationCode != null ? e.AuthenticationCode : "")).append(n("<td/>").text(e.Expired ? wr.G_Expired : "")).append(n("<td/>").text(e.DateFormatted)), n("#userList table.wmtable tbody").prepend(h)
        }

        function c(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), s(), reportError(n)
        }

        function a(t, i) {
            var u, r;
            for (hideActivityIndicator(), unsetModalActivityIndicator(), u = {}, r = 0; r < i.length; ++r) u[i[r].Site + "|" + i[r].Email + "|" + i[r].VerificationSite] = !0;
            n("#userList table.wmtable input[type=checkbox]").each(function () {
                var t = n(this).attr("url") + "|" + n(this).attr("email") + "|" + n(this).attr("verificationurl");
                u[t] && n(this).parent().parent().remove()
            }), e()
        }

        function v(n) {
            hideActivityIndicator(), unsetModalActivityIndicator(), reportError(n)
        }

        function y(t) {
            t.Site = n.trim(t.Site), t.VerificationSite = n.trim(t.VerificationSite), t.VerificationSite == "" && (t.VerificationSite = t.Site), t.Email = n.trim(t.Email), t.DelegatedCode = n.trim(t.DelegatedCode), t.DelegatedCode == "" && (t.DelegatedCode = null), t.DelegatedCodeOwnerEmail = n.trim(t.DelegatedCodeOwnerEmail), t.DelegatedCodeOwnerEmail == "" && (t.DelegatedCodeOwnerEmail = null), t.DelegatorEmail = n.trim(t.DelegatorEmail), t.DelegatorEmail == "" && (t.DelegatorEmail = null), t.DelegatorId = n.trim(t.DelegatorId), t.DelegatorId == "" && (t.DelegatorId = null), t.Roles == "None" && (t.Roles = "")
        }
        var e = setupGridCheckButtonEnabler("#userList table.wmtable", "#removeParam"),
            t = !1,
            i = null,
            o = null,
            u = null,
            r = null,
            f = function () {
                t && (i = null, o = null, u = null, r = null, t = !1, n("#addParam").show(), n("#editParam").hide(), n("#userList table.wmtable tr.row-selected").removeClass("row-selected"))
            }, s = function () {
                n("#email").attr("disabled", null), n("#url").attr("disabled", null), n("#role").attr("disabled", null), n("#verificationurl").attr("disabled", null), n("#delegatorid").attr("disabled", null), n("#delegatoremail").attr("disabled", null), n("#delegatedcode").attr("disabled", null), n("#delegatedcodeemail").attr("disabled", null), n("#authenticationcode").attr("disabled", null), n("#addParam").attr("disabled", null)
            }, l = function () {
                n("#email").attr("disabled", "disabled"), n("#url").attr("disabled", "disabled"), n("#role").attr("disabled", "disabled"), n("#verificationurl").attr("disabled", "disabled"), n("#delegatorid").attr("disabled", "disabled"), n("#delegatoremail").attr("disabled", "disabled"), n("#delegatedcode").attr("disabled", "disabled"), n("#delegatedcodeemail").attr("disabled", "disabled"), n("#authenticationcode").attr("disabled", "disabled"), n("#addParam").attr("disabled", "disabled")
            };
        n("#addParam").click(function () {
            var u, f;
            n(this).attr("disabled") != "disabled" && n("#addParamForm").valid() && (u = {
                Email: n("#email").val(),
                Site: n("#url").val(),
                VerificationSite: n("#verificationurl").val(),
                DelegatedCode: n("#delegatedcode").val(),
                DelegatedCodeOwnerEmail: n("#delegatedcodeemail").val(),
                DelegatorEmail: n("#delegatoremail").val(),
                DelegatorId: n("#delegatorid").val(),
                AuthenticationCode: n("#authenticationcode").val(),
                Roles: n("#role").val()
            }, console.log(u), y(u), showActivityIndicator(!0), l(), t ? (f = n.extend({}, u), f.UserId = i, f.VerificationSite = r, getSvc().invoke("UpdateSiteRolesAdmin", {
                siteRoles: [f],
                fields: u
            }, function (n) {
                h(n[0])
            }, c)) : getSvc().invoke("AddSiteRolesAdmin", {
                fields: u
            }, h, c))
        }), n("#removeParam").click(function () {
            var t = [];
            n("#userList table.wmtable :checked").each(function () {
                var i = n(this),
                    r = {
                        Site: i.attr("url"),
                        Email: i.attr("email"),
                        UserId: i.attr("userid"),
                        VerificationSite: i.attr("verificationurl")
                    };
                t.push(r)
            }), console.log(t), t.length > 0 && (showActivityIndicator(!0), getSvc().invoke("RemoveSiteRolesAdmin", {
                url: url,
                siteRoles: t
            }, function (n) {
                a(n, t)
            }, v))
        }), n("#addParamForm").validate(formValidateOptions), n("#userList table.wmtable :checkbox").live("change", e), clickButtonOnEnter("#addParam", ["#url", "#email", "#verificationurl", "#delegatorid", "#delegatoremail", "#delegatedcode", "#delegatedcodeemail"]), n("#userList table.wmtable tbody a").live("click", function () {
            if (n("#addParam").attr("disabled") != "disabled") {
                var f = n(this).parent().parent().find('input[type="checkbox"]');
                n("#email").val(f.attr("email")), n("#url").val(f.attr("url")), n("#verificationurl").val(f.attr("verificationurl")), n("#authenticationcode").val(f.attr("authenticationcode")), n("#delegatedcode").val(f.attr("delegatedcode")), n("#delegatedcodeemail").val(f.attr("delegatedcodeowneremail")), n("#delegatorid").val(f.attr("delegatorid")), n("#delegatoremail").val(f.attr("delegatoremail")), n("#role").val(f.attr("role") ? f.attr("role") : "None"), n("#addParam").hide(), n("#editParam").show(), i = f.attr("userId"), o = f.attr("email"), u = f.attr("url"), r = f.attr("verificationurl"), t = !0, n("#userList table.wmtable tr.row-selected").removeClass("row-selected"), n(this).parent().parent().addClass("row-selected"), scrollToTop()
            }
        }), n("#editParam").click(function () {
            n(this).attr("disabled") != "disabled" && n("#addParam").click()
        }), n("#email").keyup(function () {
            f()
        }), n("#url").keyup(function () {
            f()
        }), n("#applyFilter").click(function () {
            n("#filterForm").submit()
        }), n("#toggleFilter").click(function () {
            n("#filter").val() == "all" ? n("#filter").val("").prop("disabled", !0) : n("#filter").val("all").prop("disabled", !1), n("#filterForm").submit()
        })
    }
}(jQuery),
function (n) {
    n.fn.contentRemoval = function () {
        function u() {
            if (n("#errorBox").hide(), n("#cachedPageText").removeClass("error"), n("#activityIndicator").css("top", "120"), n("#contentRemovalForm").valid()) {
                showActivityIndicator(!0);
                var t = n("#contentRemovalForm").serialize();
                n.getJSON(n("#contentRemovalForm").attr("action"), t, function (t) {
                    if (!t.Success) {
                        n("#errorBox").text(t.ErrorMessage).show(), t.CacheRefreshRequired && (n("#type").val(i).change(), n("#cachedPageText").addClass("error")), hideActivityIndicator();
                        return
                    }
                    n("#contentRemovalForm")[0].reset(), n("#errorBox").hide(), hideActivityIndicator(), r(), f(t)
                })
            }
        }

        function f(t) {
            var i = "<tr><td class='centeralign'>{0}</td><td><a href='{1}' target='_blank' class='url-text'>{1}<span class='trend-icon blink-icon-popup'></span></a></td><td>{2}</td><td>{3}</td><td class='centeralign'>{4}</td></tr>";
            i = n.validator.format(i, t.Date, t.Url, t.RemovalType, t.Status, t.HttpResponse), n("table.grid tbody").prepend(i), n("table.grid tr.empty").addClass("none")
        }

        function r() {
            n("#type").val() == i ? n("#cachedTextContainer").show() : (n("#cachedTextContainer").hide(), n("#cachedPageText").val(""))
        }

        function e() {
            n.validator.addClassRules({
                requiredIfRemovalTypeCache: {
                    required: {
                        depends: function () {
                            return n("#type").val() == i
                        }
                    }
                }
            })
        }

        function o() {
            e(), r(), n("#type").change(r), n("#submitHipUrl").click(u), n("#contentRemovalForm").validate(formValidateOptions)
        }
        var i = 2;
        o()
    }
}(jQuery),
function (n) {
    n.fn.mstranslator = function (t) {
        function o() {
            n.get(t.markupUrl, function (n) {
                r = n, r != null && r.length > 0 && i()
            })
        }

        function i() {
            console.log("Outputting the markup in the text area and injecting it into the DOM 3"), n("#WidgetFloaterPanels").remove(), n("#MicrosoftTranslatorWidget").remove();
            var u = n("input[name='theme']:checked").val(),
                e = n("input[name='ctEnabled']:checked").val(),
                o = n("input[name='mode']:checked").val(),
                s = l(),
                t = n.validator.format(r, u, e, o, s),
                i = t.indexOf("<script "),
                h = t.substring(0, i),
                c = t.substring(i),
                a = n(c);
            n("#markupbox").val(t), n("#preview").empty().html(h).append(a).parent().hide(), f == -1 && (f = window.setInterval(function () {
                n("#WidgetLauncher").length >= 1 && (window.clearInterval(f), n("#previewContainer").show(), it(), f = -1)
            }, 100))
        }

        function v(t) {
            n("#vtab2>ul>li").eq(t).click()
        }

        function et() {
            var i = n("#vtab2>ul>li"),
                r = t.isAuthenticated || t.code != null && t.code != "" ? 2 : 0;
            i.click(function () {
                i.removeClass("selected"), n(this).addClass("selected");
                var t = i.index(n(this));
                n("#vtab2 >div").hide().eq(t).show()
            }).eq(r).click()
        }

        function h() {
            console.log("Clearing cookies"), n.cookie("bwt_tw_tth", null, {
                path: "/"
            }), n.cookie("bwt_tw_tmd", null, {
                path: "/"
            }), n.cookie("bwt_tw_tct", null, {
                path: "/"
            }), n.cookie("bwt_tw_tsl", null, {
                path: "/"
            }), n.cookie("bwt_tw_tsu", null, {
                path: "/"
            }), n.cookie("bwt_tw_tia", null, {
                path: "/"
            }), n.cookie("bwt_tw_tctmk", null, {
                path: "/"
            })
        }

        function ft() {
            console.log(t), h();
            var i = n("input[name='theme']:checked").val(),
                r = n("input[name='mode']:checked").val(),
                u = n("input[name='ctEnabled']:checked").val(),
                f = l(),
                e = n("#siteUrl").val(),
                o = t.isAuthenticated;
            console.log("Setting cookies"), n.cookie("bwt_tw_tth", i, {
                path: "/"
            }), n.cookie("bwt_tw_tmd", r, {
                path: "/"
            }), n.cookie("bwt_tw_tct", u, {
                path: "/"
            }), n.cookie("bwt_tw_tsl", f, {
                path: "/"
            }), n.cookie("bwt_tw_tsu", e, {
                path: "/"
            }), n.cookie("bwt_tw_tia", o, {
                path: "/"
            })
        }

        function ut() {
            console.log("Reading cookies"), t.theme = n.cookie("bwt_tw_tth"), t.mode = n.cookie("bwt_tw_tmd"), t.ctEnabled = n.cookie("bwt_tw_tct"), t.siteLanguage = n.cookie("bwt_tw_tsl"), t.siteUrl = n.cookie("bwt_tw_tsu"), t.isAuthenticated = n.cookie("bwt_tw_tia"), console.log("Options after reading cookies %o", t)
        }

        function rt() {
            t.code != null && t.code != "" ? ut() : (console.log("URL contains no code query parameter. Calling clearing cookies"), h(), o())
        }

        function nt() {
            if (t.theme && (n("input[name='theme'][value='" + t.theme + "']").prop("checked", !0), c()), t.mode) {
                var i = n("input[name='mode'][value='" + t.mode + "']");
                i.prop("checked", !0), p()
            }
            t.siteLanguage && t.siteLanguage != "" && n("#siteLanguage option[value='" + t.siteLanguage + "']").prop("selected", !0), t.ctEnabled && (n("input[name='ctEnabled'][value='" + t.ctEnabled + "']").prop("checked", !0), w()), t.siteUrl && n("#siteUrl").val(t.siteUrl)
        }

        function s() {
            var f = 0,
                e;
            return n("#siteUrl").val() && n("#siteUrl").valid() ? (n("input[name='ctSteps'][value='1']").prop("checked", !0), f++) : (n("input[name='ctSteps'][value='1']").prop("checked", !1), f--), t.isAuthenticated && (n("input[name='ctSteps'][value='2']").prop("checked", !0), f++), t.code && f++, f == 3 ? (console.log("All Steps validated."), e = n.cookie("bwt_tw_tctmk"), e == null || e == "" ? (console.log("No existing ctf cookies found. Going ahead with the ctf markup request."), tt()) : (console.log("Ctf cookies found. markup was not fetched. We'll be loading the markup from cookie"), r = e, n(".ctfCodeGenerated").show(), u = !0, n("input[name='ctSteps'][value='3']").prop("checked", !0), i()), !0) : (n(".ctfCodeGenerated").hide(), u = !1, !1)
        }

        function tt() {
            n.get(t.ctfMarkupUrl, {
                code: t.code,
                siteUrl: n("#siteUrl").val()
            }, function (t) {
                console.log("CTF request result from the server %o", t), t != null && t.length > 0 ? (r = t, console.log("Setting tctmk cookie after getting the ctf markup"), n.cookie("bwt_tw_tctmk", r, {
                    path: "/"
                }), n(".ctfCodeGenerated").show(), u = !0, n("input[name='ctSteps'][value='3']").prop("checked", !0), i(), n("#markupbox").css("overflow-y", "auto")) : (u = !1, n("input[name='ctSteps'][value='3']").prop("checked", !1), o())
            })
        }

        function c() {
            var t = n("input[name='theme']:checked").val() == "Dark";
            t ? n("#previewBackground").removeClass("dark-background") : n("#previewBackground").addClass("dark-background")
        }

        function p() {
            var t = n("input[name='mode']:checked").val() == "Manual";
            t && typeof Microsoft != "undefined" && typeof Microsoft.Translator != "undefined" && Microsoft.Translator.FloaterOnClose()
        }

        function w() {
            var t = n("input[name='ctEnabled']:checked").val() == "True";
            t ? (n(".ctf-steps").removeClass("disabled-section"), n("#siteUrl").prop("disabled", !1), s() || i()) : (n(".ctf-steps").addClass("disabled-section"), n("#siteUrl").prop("disabled", !0), n(".ctfCodeGenerated").hide(), u == !0 && (h(), o(), u = !1), n("input[name='ctSteps']").prop("checked", !1), y.resetForm(), i())
        }

        function ot() {
            n("body").attr("adjustalign", "false"), n("#sb_foot").attr("translate", "no"), n("#sw_hdr").attr("translate", "no"), n("#sw_abar").attr("translate", "no"), n("#fbpgbt").attr("translate", "no")
        }

        function g() {
            n(".ctf-steps").hasClass("disabled-section") || n("#translatorForm").submit()
        }

        function d() {
            n(".ctf-steps").hasClass("disabled-section") || n("#siteUrl").valid() && t.isAuthenticated && (ft(), console.log("Cookies Info saved {0} " + t.azureDmUrl), document.location.href = t.azureDmUrl)
        }

        function k(n) {
            if (n.which == 13) {
                n.preventDefault();
                return
            }(n.type == "keypress" || u == !1) && s()
        }

        function b() {
            var t = n(this);
            t.select(), t.mouseup(function () {
                return t.unbind("mouseup"), !1
            })
        }

        function l() {
            var i = n("#siteLanguage").val();
            return i == null && (t.siteLanguage == "" ? (i = t.parentCulture, e = t.parentCulture) : i = t.siteLanguage), i
        }

        function a(r) {
            n.each(r, function (t, i) {
                n("#siteLanguage").append(n("<option />").val(i.Code).text(i.Name))
            });
            var u;
            t.siteLanguage == "" ? (u = "en", n("#siteLanguage option").each(function () {
                if (n(this).attr("value") == t.parentCulture) {
                    u = t.parentCulture;
                    return
                }
            })) : u = t.siteLanguage, n("#siteLanguage option[value='" + u + "']").prop("selected", !0), e != "" && e != u && i()
        }

        function it() {
            n("#siteLanguage option").length == 0 && typeof Microsoft != "undefined" && typeof Microsoft.Translator != "undefined" && Microsoft.Translator.Widget.GetLanguagesForTranslate(t.parentCulture, function (n) {
                typeof n == "undefined" ? Microsoft.Translator.Widget.GetLanguagesForTranslate("en-us", function (n) {
                    typeof n != "undefined" && a(n)
                }) : a(n)
            }, function (n) {
                console.log("Error Fetching list of languages %s !", n)
            })
        }

        function st() {
            y = n("#translatorForm").validate(formValidateOptions), rt(), nt(), et(), s(), ot(), n(".customize-options input:radio").change(i), n("input[name='theme']").change(c), n("input[name='mode']").change(p), n("#siteLanguage").change(i), n("input[name='ctEnabled']").change(w), n("#signInLink a").click(g), n("#associateInLink a").click(d), n("#markupbox").focus(b), n("#siteUrl").bind("blur mouseleave keypress", k), n("#settingsContinue").click(function () {
                v(1)
            }), n("#themeContinue").click(function () {
                v(2)
            })
        }
        var r = "",
            y = null,
            u = !1,
            f = -1,
            e = "";
        st()
    }
}(jQuery)