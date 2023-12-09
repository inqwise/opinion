/*!
 * Client
 * Copyright (c) 2011 Inqwise
 */
(function () {
    var p = {
        init: function () {
            this.browser = this.browser();
            this.screen = this._screen();
            this.os = this.os();
        },
        timeZone: function () {
            var f = function (a) {
                var b = parseInt(a);
                a -= parseInt(a);
                a *= 60;
                var c = parseInt(a);
                a -= parseInt(a);
                a *= 60;
                var d = parseInt(a);
                var e = b;
                if (b == 0) {
                    e = "00";
                } else if (b > 0) {
                    e = (b < 10) ? "+0" + b : "+" + b;
                } else {
                    e = (b > -10) ? "-0" + Math.abs(b) : b;
                }
                c = (c < 10) ? "0" + c : c;
                return e + ":" + c;
            };
            var g = new Date();
            var h = new Date(g.getFullYear(), 0, 1, 0, 0, 0, 0);
            var i = new Date(g.getFullYear(), 6, 1, 0, 0, 0, 0);
            var j = h.toGMTString();
            var k = new Date(j.substring(0, j.lastIndexOf(" ") - 1));
            j = i.toGMTString();
            var l = new Date(j.substring(0, j.lastIndexOf(" ") - 1));
            var m = (h - k) / (1000 * 60 * 60);
            var n = (i - l) / (1000 * 60 * 60);
            if (m == n) {} else {
                var o = m - n;
                if (o >= 0) 
                	m = n;
            }
            return f(m);
        },
        os: function () {
            var a = "Unknown OS";
            var b = window.navigator.platform;
            var c = navigator.userAgent;
            if (c.indexOf("Win") != -1) {
                if ((c.indexOf("Windows NT 5.1") != -1) || (c.indexOf("Windows XP") != -1)) a = "Win XP";
                else if ((c.indexOf("Windows NT 7.0") != -1) || (c.indexOf("Windows NT 6.1") != -1)) a = "Win 7/Server 2008";
                else if ((c.indexOf("Windows NT 6.0") != -1)) a = "Win Vista/Server 2008";
                else if (c.indexOf("Windows ME") != -1) a = "Win ME";
                else if ((c.indexOf("Windows NT 4.0") != -1) || (c.indexOf("WinNT4.0") != -1) || (c.indexOf("WinNT") != -1)) a = "Win NT";
                else if ((c.indexOf("Windows NT 5.2") != -1)) a = "Win Server 2003";
                else if ((c.indexOf("Windows NT 5.0") != -1) || (c.indexOf("Windows 2000") != -1)) a = "Win 2000";
                else if ((c.indexOf("Windows 98") != -1) || (c.indexOf("Win98") != -1)) a = "Win 98";
                else if ((c.indexOf("Windows 95") != -1) || (c.indexOf("Win95") != -1) || (c.indexOf("Windows_95") != -1)) a = "Win 95";
                else if ((c.indexOf("Win16") != -1)) a = "Win 3.1";
                else a = "Win Ver. Unknown";
                if ((c.indexOf("WOW64") != -1) || (c.indexOf("x64") != -1) || (c.indexOf("Win64") != -1) || (c.indexOf("IA64") != -1)) a = a + " (x64)";
                else a = a + " (x32)"
            } else if (c.indexOf("Mac") != -1) a = "MacOS";
            else if (c.indexOf("X11") != -1) a = "UNIX";
            else if (c.indexOf("Linux") != -1) a = "Linux";
            return {
                name: a,
                version: null,
                platform: b,
                cpuClass: ((navigator.cpuClass) ? navigator.cpuClass : ""),
                oscpu: window.navigator.oscpu,
                isOnline: window.navigator.onLine,
                systemLanguage: ((window.navigator.systemLanguage != undefined) ? window.navigator.systemLanguage : window.navigator.language),
                timeZone: this.timeZone()
            }
        },
        _screen: function () {
            return {
                width: screen.width,
                height: screen.height,
                color: ((screen.pixelDepth != undefined) ? screen.pixelDepth : screen.colorDepth)
            }
        },
		searchString: function (d) {
			for (var i = 0; i < d.length; i++)	{
				var s = d[i].string;
				var p = d[i].prop;
				this.versionSearchString = d[i].versionSearch || d[i].identity;
				if (s) {
					if (s.indexOf(d[i].subString) != -1)
						return d[i].identity;
				}
				else if (p)
					return d[i].identity;
			}
		},
		searchVersion : function(d) {
			var i = d.indexOf(this.versionSearchString);
			if (i == -1) return;
			return parseFloat(d.substring(i+this.versionSearchString.length+1));
		},
		dataBrowser: [
			{
				string: navigator.userAgent,
				subString: "Chrome",
				identity: "Chrome"
			},
			{ 	string: navigator.userAgent,
				subString: "OmniWeb",
				versionSearch: "OmniWeb/",
				identity: "OmniWeb"
			},
			{
				string: navigator.vendor,
				subString: "Apple",
				identity: "Safari",
				versionSearch: "Version"
			},
			{
				prop: window.opera,
				identity: "Opera"
			},
			{
				string: navigator.vendor,
				subString: "iCab",
				identity: "iCab"
			},
			{
				string: navigator.vendor,
				subString: "KDE",
				identity: "Konqueror"
			},
			{
				string: navigator.userAgent,
				subString: "Firefox",
				identity: "Firefox"
			},
			{
				string: navigator.vendor,
				subString: "Camino",
				identity: "Camino"
			},
			{	
				string: navigator.userAgent,
				subString: "Netscape",
				identity: "Netscape"
			},
			{
				string: navigator.userAgent,
				subString: "MSIE",
				identity: "Explorer",
				versionSearch: "MSIE"
			},
			{
				string: navigator.userAgent,
				subString: "Gecko",
				identity: "Mozilla",
				versionSearch: "rv"
			},
			{ 		// for older Netscapes (4-)
				string: navigator.userAgent,
				subString: "Mozilla",
				identity: "Netscape",
				versionSearch: "Mozilla"
			}
		],
        browser: function () {
            var i = function () {
                var a = false;
                if (navigator.plugins && navigator.plugins.length) {
                    for (x = 0; x < navigator.plugins.length; x++) {
                        if (navigator.plugins[x].name.indexOf('Java(TM)') != -1) {
                            a = true;
                            break;
                        }
                    }
                }
                if (navigator.javaEnabled()) {
                    a = true;
                }
                return {
                    isInstalled: a
                }
            };
            var j = function () {
                var c = false;
                u = 10;
                h = '0.0';
                if (navigator.plugins && navigator.plugins.length) {
                    for (x = 0; x < navigator.plugins.length; x++) {
                        if (navigator.plugins[x].name.indexOf('Shockwave Flash') != -1) {
                            h = navigator.plugins[x].description.split('Shockwave Flash ')[1];
                            c = true;
                            break;
                        }
                    }
                } else if (window.ActiveXObject) {
                    for (x = 2; x <= u; x++) {
                        try {
                            oFlash = eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash." + x + "');");
                            if (oFlash) {
                                c = true;
                                h = x + '.0';
                            }
                        } catch (e) {}
                    }
                }
                return {
                    isInstalled: c,
                    version: h
                }
            };
            return {
				name : this.searchString(this.dataBrowser) || "An unknown browser",
                appName: window.navigator.appName,
                vendor: ((window.navigator.vendor) ? window.navigator.vendor : ""),
                appCodeName: window.navigator.appCodeName,
                appVersion: window.navigator.appVersion,
                product: window.navigator.product,
                buildNumber: ((window.opera) ? window.opera.buildNumber() : ((window.navigator.productSub) ? window.navigator.productSub : "")),
                userAgent: window.navigator.userAgent,
                cookieEnabled: window.navigator.cookieEnabled,
                version: this.searchVersion(navigator.userAgent)
						|| this.searchVersion(navigator.appVersion)
						|| "an unknown version",
                java: i(),
                flash: j()
            }
        }
    };
    p.init();
    window.$.client = {
        os: p.os,
        browser: p.browser,
        screen: p.screen
    }
})();