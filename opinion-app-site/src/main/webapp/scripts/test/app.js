

var jsapi = null;
var isAuthenticated = false;
var isEnableFractionalPips = true;
var isActive = false;
var isDemo = false;
var regTicketId = null;
var userStatus = null;
var needToPassTest = false;
var isSuitabilityTestPassed = true;
var regionId = 0;
var accountBaseCurrency = null;
var withdrawalAvailable = null;
var allowWithdrawal = false;
var allowDeposit = false;
var lastPair = null;
var restrictedPair = null;
var lastDealId = null;
var clientPhone = null;
var instrumentType = 1;
var isFirstDeposit = false;

var cultureId = 2;
var setCulture = function(language) {
	switch(language) {
		case "en" :
			cultureId = 2;
			break;
		case "ar" :
			cultureId = 13;
			break;
		case "cn" :
			cultureId = 14;
			break;
		case "fr" :
			cultureId = 43;
			break;
		case "pl" :
			cultureId = 47;
			break;
		default : 
			cultureId = 2;
			break;
	}
};

var step1StatesCountries = [44];			
var states = [{countryId:3,states:[{stateId:71,state:"American Samoa"}]},{countryId:12,states:[{stateId:67,state:"ACT"},{stateId:64,state:"New South Wales"},{stateId:65,state:"Queensland"},{stateId:69,state:"South Australia"},{stateId:70,state:"Tasmania"},{stateId:66,state:"Victoria"},{stateId:68,state:"West Australia/NT"}]},{countryId:38,states:[{stateId:54,state:"Alberta"},{stateId:55,state:"British Columbia"},{stateId:57,state:"New Brunswick"},{stateId:58,state:"Newfoundland and Labrador"},{stateId:59,state:"Northwest Territories"},{stateId:113,state:"Nova Scotia"},{stateId:114,state:"Nunavut"},{stateId:60,state:"Ontario"},{stateId:61,state:"Prince Edward Island"},{stateId:53,state:"Quebec"},{stateId:62,state:"Saskatchewan"},{stateId:63,state:"Yukon"}]},{countryId:44,states:getContent("chineseStates")},{countryId:90,states:[{stateId:73,state:"Guam"}]},{countryId:172,states:[{stateId:51,state:"Puerto Rico"}]},{countryId:225,states:[{stateId:2,state:"Alabama"},{stateId:3,state:"Alaska"},{stateId:4,state:"Arizona	"},{stateId:5,state:"Arkansas"},{stateId:6,state:"California"},{stateId:7,state:"Colorado"},{stateId:8,state:"Connecticut"},{stateId:9,state:"Delaware"},{stateId:10,state:"District of Columbia"},{stateId:11,state:"Florida"},{stateId:12,state:"Georgia"},{stateId:13,state:"Hawaii"},{stateId:14,state:"Idaho"},{stateId:15,state:"Illinois"},{stateId:16,state:"Indiana"},{stateId:17,state:"Iowa"},{stateId:18,state:"Kansas"},{stateId:19,state:"Kentucky"},{stateId:20,state:"Louisiana"},{stateId:21,state:"Maine"},{stateId:22,state:"Maryland"},{stateId:23,state:"Massachusetts"},{stateId:24,state:"Michigan"},{stateId:25,state:"Minnesota"},{stateId:26,state:"Mississippi"},{stateId:27,state:"Missouri"},{stateId:28,state:"Montana"},{stateId:29,state:"Nebraska"},{stateId:30,state:"Nevada"},{stateId:31,state:"New Hampshire"},{stateId:32,state:"New Jersey"},{stateId:33,state:"New Mexico"},{stateId:39,state:"New York"},{stateId:34,state:"North Carolina"},{stateId:35,state:"North Dakota"},{stateId:36,state:"Ohio"},{stateId:37,state:"Oklahoma"},{stateId:38,state:"Oregon"},{stateId:1,state:"Pennsylvania"},{stateId:52,state:"Rhode Island"},{stateId:40,state:"South Carolina"},{stateId:41,state:"South Dakota"},{stateId:42,state:"Tennessee"},{stateId:43,state:"Texas"},{stateId:44,state:"Utah"},{stateId:45,state:"Vermont"},{stateId:46,state:"Virginia"},{stateId:47,state:"Washington"},{stateId:48,state:"West Virginia"},{stateId:49,state:"Wisconsin"},{stateId:50,state:"Wyoming"}]},{countryId:226,states:[{stateId:80,state:"United States Minor Outlying Islands"}]},{countryId:228,states:[{stateId:2,state:"Alabama"},{stateId:3,state:"Alaska"},{stateId:4,state:"Arizona	"},{stateId:5,state:"Arkansas"},{stateId:6,state:"California"},{stateId:7,state:"Colorado"},{stateId:8,state:"Connecticut"},{stateId:9,state:"Delaware"},{stateId:10,state:"District of Columbia"},{stateId:11,state:"Florida"},{stateId:12,state:"Georgia"},{stateId:13,state:"Hawaii"},{stateId:14,state:"Idaho"},{stateId:15,state:"Illinois"},{stateId:16,state:"Indiana"},{stateId:17,state:"Iowa"},{stateId:18,state:"Kansas"},{stateId:19,state:"Kentucky"},{stateId:20,state:"Louisiana"},{stateId:21,state:"Maine"},{stateId:22,state:"Maryland"},{stateId:23,state:"Massachusetts"},{stateId:24,state:"Michigan"},{stateId:25,state:"Minnesota"},{stateId:26,state:"Mississippi"},{stateId:27,state:"Missouri"},{stateId:28,state:"Montana"},{stateId:29,state:"Nebraska"},{stateId:30,state:"Nevada"},{stateId:31,state:"New Hampshire"},{stateId:32,state:"New Jersey"},{stateId:33,state:"New Mexico"},{stateId:39,state:"New York"},{stateId:34,state:"North Carolina"},{stateId:35,state:"North Dakota"},{stateId:36,state:"Ohio"},{stateId:37,state:"Oklahoma"},{stateId:38,state:"Oregon"},{stateId:1,state:"Pennsylvania"},{stateId:52,state:"Rhode Island"},{stateId:40,state:"South Carolina"},{stateId:41,state:"South Dakota"},{stateId:42,state:"Tennessee"},{stateId:43,state:"Texas"},{stateId:44,state:"Utah"},{stateId:45,state:"Vermont"},{stateId:46,state:"Virginia"},{stateId:47,state:"Washington"},{stateId:48,state:"West Virginia"},{stateId:49,state:"Wisconsin"},{stateId:50,state:"Wyoming"}]},{countryId:235,states:[{stateId:77,state:"Virgin Islands"}]}];
var getStates = function(countryId, isFirstStep) {
	for(var i = 0; i < states.length; i++) {
		if(states[i].countryId == countryId && (!isFirstStep || step1StatesCountries.indexOf(countryId) >=0)) {
			return states[i].states;
		}
	}
	return null;
};

var getFormattedDate = function(date){
	var _today = (new Date()).zeroTime();
	_today = Date.fromString(date); 
	return _today.getDate() + '-' + Date.abbrMonthNames[_today.getMonth()] + '-' + _today.getFullYear();
}

var showDailyRenewlFeePopUP= function(){
	var renewalFee = new lightFace({		
		message : getContent("openDealRenewalFee"),		
		actions : [
		   { label : getContent("ok"), fire : function() { renewalFee.close() }, color: "green" }
		],
		overlayAll : true 
	});
};

var showMoreInfoPopUP= function(){
	var moreInfo = new lightFace({		
		message : getContent("openDealMoreInfo"),		
		actions : [
		   { label : getContent("ok"), fire : function() { moreInfo.close() }, color: "green" }
		],
		overlayAll : true
	});						
};

 function getContent (key) {
	return (messages[key] != undefined ? messages[key] : null);
};

var getScreenState = function(){
	if (typeof ezfxCordova == 'undefined') {
		return j.getUrlParam('state');
	} else {
		return  ezfxCordova.getScreenState();
	}
}

var products = {
	2 : getContent("forward"),
	3 : getContent("dayTrading"),
	4 : getContent("limitOrder")
};

var fractionalFormat = function (v, p) {
	if (isEnableFractionalPips) {
		if (v != undefined) {
			v = v.toString();
			var d = false;
			if (p != undefined) {
				var l, r;
				l = p.substr(0, 3);
				r = p.substr(3, 6);
				var c = jsapi.trading.getCurrencyPairSettings(l, r);
				if (c != null) {
					if (c.isFractional) {
						d = true
					}
				}
			}
			if (v != "" && v != "&nbsp;" && v != "N/A" && d) {
				return v.substring(0, (v.length - 1)) + "<b class=\"frac\">" + v.charAt(v.length - 1) + "</b>"
			} else {
				return v
			}
		}
	} else {
		return v
	}
};

var getRelative = function(buy, sell) {
	var currencyPairs = (isAuthenticated != true ? jsapi.marketInfo.settings.marketInfo.currencyPairs : jsapi.trading.tradingInfo.currencyPairs);
	for (var key in currencyPairs) {
		var currencyPair = currencyPairs[key];
		if(currencyPair.nonBaseCurrency == buy && currencyPair.baseCurrency == sell) {
			return currencyPair.baseCurrency;
			break;
		} else if (currencyPair.nonBaseCurrency == sell && currencyPair.baseCurrency == buy) {
			return currencyPair.baseCurrency;
			break;
		}
		
	}
	return null;
};

var getNonBase = function(base) {
	var currencyPairs = (isAuthenticated != true ? jsapi.marketInfo.settings.marketInfo.currencyPairs : jsapi.trading.tradingInfo.currencyPairs);
	for (var key in currencyPairs) {
		var currencyPair = currencyPairs[key];
		if(currencyPair.baseCurrency == base && currencyPair.instrumentType == instrumentType) {
			return currencyPair.nonBaseCurrency;
			break;
		}
	}
	return null;
};

var getPair = function(symbol) {
	var currencyPairs = (isAuthenticated != true ? jsapi.marketInfo.settings.marketInfo.currencyPairs : jsapi.trading.tradingInfo.currencyPairs);
	for (var key in currencyPairs) {
		var currencyPair = currencyPairs[key];
		if(currencyPair.nonBaseCurrency == symbol && currencyPair.instrumentType == instrumentType) {
			return currencyPair.baseCurrency;
			break;
		} else if (currencyPair.baseCurrency == symbol && currencyPair.instrumentType == instrumentType) {
			return currencyPair.nonBaseCurrency;
			break;
		}
	}
	return null;
};

var getCurrencySettings = function(symbol) {
	var currency = null;
	var _currencies = (isAuthenticated != true ? jsapi.marketInfo.settings.marketInfo.currencies : jsapi.trading.tradingInfo.currencies);
	for(var i = 0; i < _currencies.length; i++) {
		if(_currencies[i].symbol == symbol) {
			currency = _currencies[i];
			break;
		}
	}
	return currency;
};

var getCurrencies = function() {
	var currencies = [];
	var _currencies = (isAuthenticated != true ? jsapi.marketInfo.settings.marketInfo.currencies : jsapi.trading.tradingInfo.currencies);
	for(var i = 0; i < _currencies.length; i++) {
		if(_currencies[i].instrumentType == instrumentType) {
			currencies.push(_currencies[i]);
		}
	}
	return currencies;
}

var getCurrencyPairSettings = function (a, b, d) {
	var e;
	var t = d;
	for (var k in t) {
		var c = t[k];
		if ((c.baseCurrency == a && c.nonBaseCurrency == b) || (c.baseCurrency == b && c.nonBaseCurrency == a)) {
			e = c
		}
	}
	if (e != undefined) {
		return e
	}
	return null
};

var relativeTime = function (a) {
	var b = a.split(" ");
	a = b[1] + " " + b[2] + ", " + b[5] + " " + b[3];
	var c = Date.parse(a);
	var d = (arguments.length > 1) ? arguments[1] : new Date();
	var e = parseInt((d.getTime() - c) / 1000);
	e = e + (d.getTimezoneOffset() * 60);
	if (e < 60) {
		return 'less than a minute ago'
	} else if (e < 120) {
		return 'about a minute ago'
	} else if (e < (60 * 60)) {
		return (parseInt(e / 60)).toString() + ' minutes ago'
	} else if (e < (120 * 60)) {
		return 'about an hour ago'
	} else if (e < (24 * 60 * 60)) {
		return 'about ' + (parseInt(e / 3600)).toString() + ' hours ago'
	} else if (e < (48 * 60 * 60)) {
		return '1 day ago'
	} else {
		return (parseInt(e / 86400)).toString() + ' days ago'
	}
};

var closeDeal = function(dealId) {
	
	// with option to close multi
	var dealsToClose = [];
	var list = [];
	//list = typeof (deals) == 'object' ? deals : [deals];
	list = [dealId];
	
	for(var i = 0; i < list.length; i++) {
		var contract = jsapi.trading.rates.ratesInfo.deals[list[i]];
		var deal = jsapi.trading.tradingInfo.deals[list[i]];
		
		if(contract != undefined 
				&& deal != undefined) {
			
			var o = {
				dealId : list[i],
				productType : deal.productId,
				buy : deal.buy,
				sell : deal.sell,
				quoteId : contract.quoteId
			};
			
			// insert deal object into array of dealsToClose
			dealsToClose.push(o);
			
			loader.show();
			
			// close deals or deal
			jsapi.trading.deals.closeDeals({
				deals : dealsToClose,
				success : function(data) {
					
					// success closed deals
					if(data.closedList.length != 0) {
						
						loader.hide();
						
						// thank you close
						for(var x = 0; x < data.closedList.length; x++) {
							
							j('#label_close_deal_thank_you_message').html(String.format(getContent("successClosedDeal"), data.closedList[0].dealId));
							j('#label_close_deal_thank_you_product_id').text(products[data.closedList[0].productType]);
							
							// form
							j('#label_close_deal_thank_you_deal_id').text(data.closedList[0].dealId);
							j('#label_close_deal_thank_you_buy_amount').text(data.closedList[0].buyAmount);
							j('#label_close_deal_thank_you_sell_amount').text(data.closedList[0].sellAmount);
							
							
							if(o.productType == 2 || o.productType == 3) {
								
								j('#row_close_deal_thank_you_open_rate').hide();
								j('#row_close_deal_thank_you_expired_date').hide();
								j('#row_close_deal_thank_you_stop_loss_rate').hide();
								j('#row_close_deal_thank_you_margin').hide();
								
								
								// show day trading deal details
								j('#row_close_deal_thank_you_close_rate').show();
								j('#row_close_deal_thank_you_profit_loss_amount').show();
								
								j('#label_close_deal_thank_you_close_rate').html(fractionalFormat(data.closedList[0].closeRate, data.closedList[0].symbol));
								j('#label_close_deal_thank_you_profit_loss_amount').text(data.closedList[0].closeValue + " " + accountBaseCurrency);
							}
							
							if(o.productType == 4) {
								
								j('#row_close_deal_thank_you_close_rate').hide();
								j('#row_close_deal_thank_you_profit_loss_amount').hide();
								
								// show limit deal details
								j('#row_close_deal_thank_you_open_rate').show();
								j('#row_close_deal_thank_you_expired_date').show();
								j('#row_close_deal_thank_you_stop_loss_rate').show();
								j('#row_close_deal_thank_you_margin').show();
								
								// -->
								j('#label_close_deal_thank_you_open_rate').html(fractionalFormat(data.closedList[0].openRate, data.closedList[0].symbol));
								j('#label_close_deal_thank_you_expired_date').text(data.closedList[0].expiryDate);
								j('#label_close_deal_thank_you_stop_loss_rate').html(fractionalFormat(data.closedList[0].stopLossRate, data.closedList[0].symbol));
								j('#label_close_deal_thank_you_margin').text(data.closedList[0].margin);
								
							}
						
						}
						
						screenManager.show(screens.closeDeal);
						
					}
					
					// failed not closed deals
					if(data.notClosedList.length != 0) {
						
						loader.hide();
						
						if(data.notClosedList[0].error != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : (getContent(data.notClosedList[0].error) != null ? getContent(data.notClosedList[0].error) : getContent("0")),
								actions : [
								   { 
										label : getContent("ok"),
										fire : function() {
											modal.close();
										}, 
										color: "green" }
								],
								overlayAll : true
							});
						}
						
					}
					
				},
				error : function(error) {
					
					loader.hide();
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							   { 
									label : getContent("ok"),
									fire : function() {
										modal.close();
									}, 
									color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
			
		}
		
	}
	
};

var modifyDeal = function(dealId) {
	lastDealId = dealId;
	screenManager.show(screens.modifyDeal);
};

var handle = {
	maintenance: {
        init: function () {
			loader.hide();
            j('#page').addClass('maintenance');
        },
        destroy: function () {}
    },
	login : {
		init : function() {
			
			j('#button_login').unbind('click');
			
			if(j.cookie('usr') != null) {
				j('#input_username').val(j.cookie('usr'));
				j('#checkbox_remember_me').prop('checked', true);
			}
			
            var d = null;
            d = jQuery.validator({
                elements: [{
                    element: j('#input_username'),
                    status: j('#status_username'),
                    rules: [{
                        method : 'required',
                        message : getContent("loginUserNameRequired")
                    }]
                }, {
                    element: j('#input_password'),
                    status: j('#status_password'),
                    rules: [{
                        method : 'required',
                        message : getContent("loginPasswordRequired")
                    }, {
                        method : 'rangelength',
                        pattern : [6, 12],
						message : getContent("loginPasswordRangeLength")
                    }]
                }],
                submitElement: j('#button_login'),
                messages: null,
                accept: function () {
                    loader.show();
                    jsapi.accounts.user.login({
                        userName: j.trim(j('#input_username').val()),
                        password: j.trim(j('#input_password').val()),
                        success: function (c) {
						
                            isAuthenticated = true;
							
							// set cookie session
							j.cookie('sid', c.sessionId, { expires: 365 });
							
							if(j('#checkbox_remember_me').prop('checked')) {
								j.cookie('usr', j.trim(j('#input_username').val()), { expires: 365 });
							} else {
								j.cookie('usr', null);
							}
							
                            j('.before-login').hide();
                            j('.after-login').show();
							
                            jsapi.accounts.user.getUserInfo({
                                success: function (b) {
								
                                    isEnableFractionalPips = b.isEnableFractionalPips;
                                    accountBaseCurrency = b.accountBaseCurrency;
									withdrawalAvailable = b.withdrawalAvailable;
									allowWithdrawal = b.allowWithdrawal;
									allowDeposit = b.allowDeposit;
									isActive = b.isActive;
									isDemo = b.isDemo;
									userStatus = b.userStatus;
									needToPassTest = b.needToPassTest;
									isSuitabilityTestPassed = b.isSuitabilityTestPassed;
									regionId = b.regionId;
									regTicketId = b.regTicketId;
									
									// login
									j('.label-user-id').text(jsapi.accounts.user.userInfo.userId);
									
									// If user is demo show demo label
									if(isDemo) {
										j('.label-demo').show();
									}
									
									switch(regionId) {
										case 1: {
											// Asia Pacific
											j(".risk-warning-copyright").html(getContent("copyrightAsiaPacific"));
											j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_au_phone.pdf");
											
											j('.risk-warning-note').show();
											j('.risk-warning-note-eu').hide();
											break;
										}
										case 11 : {
											// Europe
											j(".risk-warning-copyright").html(getContent("copyrightEurope"));
											j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_eu_phone.pdf");
											
											j('.risk-warning-note').hide();
											j('.risk-warning-note-eu').show();
											break;
										}
										default : {
											// Default
											j(".risk-warning-copyright").html(getContent("copyright"));
											j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_int_phone.pdf");
											
											j('.risk-warning-note').show();
											j('.risk-warning-note-eu').hide();
										}
									}
									
                                    jsapi.trading.getTrading({
                                        success: function (a) {
                                            loader.hide();
                                            if (screenManager.nextScreen != null) {
                                                screenManager.show(screenManager.nextScreen)
                                            } else {
												screenManager.show(screens.trading);
											}
                                        },
                                        error: function (a) {
                                            loader.hide();
                                            
											if(a != errors.applicationNotLoggedIn) {
												var modal = new lightFace({
													title : "",
													message : getContent(a),
													actions : [
													   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
													],
													overlayAll : true
												});
											}
											
                                        }
                                    });
                                },
                                error: function (a) {
                                    loader.hide();
                                    
									if(a != errors.applicationNotLoggedIn) {
										var modal = new lightFace({
											title : "",
											message : getContent(a),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
									}
									
                                }
                            })
                        },
                        error: function (a) {
						
                            loader.hide();
                            
							if(a != errors.applicationNotLoggedIn) {
							
								// clear password
								j('#input_password')
									.val("")
									.removeClass('predefined invalid valid');
							
								var modal = new lightFace({
									title : "",
									message : getContent(a),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
							if(a == "7") {
								// password expiry -> go to change password screen
								screenManager.show(screens.changePassword);
							}
							
                        }
                    })
                },
				error : function(el) {
					//
				}
            });
			
		},
		destroy : function() {
			
			// clear fields ?
			j('#input_username, #input_password')
				.val('')
				.removeClass('predefined invalid valid');
				
		}
	},
	registerDemo : {
		gatewayId : null,
		gatewaySubId : null,
		gatewayLprId : null,
		googleClickId : null,
		kenshooClickId : null,
		googleAdId : null,
		googleKeyword : null,
		init : function() {
		
			var gateway = j.cookie('de4fe6');
			this.googleClickId = j.cookie("GoogleClickId");
			this.kenshooClickId = j.cookie("KenshooClickId");
			var adWordsInfo = j.parseJSON(j.cookie("adWordsInfo"));			
			this.googleAdId = adWordsInfo==null? null : adWordsInfo.adId;
			this.googleKeyword = adWordsInfo==null? null : adWordsInfo.keyword;
			
			var getCookieValueByKey = function(key, value) {
				var pattern = new RegExp(key + "=([^&#]*)", "i");
				var res = pattern.exec(value);
				return res ? decodeURIComponent(res[1]) : null;
			};
			
			if(gateway) {
				this.gatewayId = getCookieValueByKey("TAR", gateway);
				this.gatewaySubId = getCookieValueByKey("SAR", gateway);
				this.gatewayLprId = getCookieValueByKey("LPR_Id", gateway);
			}
			
			var isCaptchaRequired = false;
			var captchaURL = null;
			var refreshCaptcha = function () {
			
				var timeStamp = j.getTimestamp();
				var src = captchaURL + "?timeStamp=" + timeStamp;
				
				// new image object
				var img = new Image();
				j(img).load(function () {
					j(this).css('display','none');
					j('#image_register_demo_captcha')
						.empty()
						.append(this);
					j(this).show();
				}).attr('src',src);
				
				j('#input_register_demo_captcha')
					.val('');
				
			};
			
			// register
			var enableSubmit = true;
			j('#button_register_demo').unbind('click');
			
			// clear elements
			j('#input_register_demo_full_name, #input_register_demo_email, #input_register_demo_phone1, #input_register_demo_user_name, #input_register_demo_password, #input_register_demo_retype_password, #input_register_demo_captcha')
				.val('')
				.removeClass('predefined invalid valid');
				
			j('#checkbox_register_demo_accept_terms, #checkbox_register_demo_is_over_eighteen')
				.prop("checked", false)
				.removeClass('predefined invalid valid');
			
			// validate
			var validator = null;
			validator = jQuery.validator({
				elements : [
					{
						element : j('#input_register_demo_full_name'),
						status : j('#status_register_demo_full_name'),
						rules : [
							{ method : 'required', message : getContent("registerFullNameRequired") },
							{ method : 'equalTo' , pattern : /^([\S]{2,}) ([\S ]{2,})$/, message : getContent("registerFullNameEqualTo") }
						]
					},{
						element : j('#input_register_demo_email'),
						status : j('#status_register_demo_email'),
						rules : [
							{ method : 'required', message : getContent("registerEmailRequired") },
							/* { method : 'emailISO', message : getContent("registerEmailInvalid") } */
							{ method : 'email', message : getContent("registerEmailInvalid") }
						]
					}, {
						element : j('#input_register_demo_phone1'),
						status : j('#status_register_demo_phone1'),
						rules : [
							{ method : 'required', message : getContent("registerPhoneRequired") },
							{ method : 'digits' , message : getContent("registerPhoneOnlyDigits") },
							{ method : 'minlength', pattern : 5, message : getContent("registerPhoneMinLength") }
						]
					}, {
						element : j('#input_register_demo_user_name'),
						status : j('#status_register_demo_user_name'),
						rules : [
							{ method : 'required', message : getContent("registerUserNameRequired") },
							{ method : 'minlength', pattern : 6, message : getContent("registerUserNameMinLength") },
							{ method : 'equalTo', pattern : /^[a-zA-Z0-9_.!-]+$/ , message: getContent("registerUserNameEqualTo") }
						]
					}, {
						element : j('#input_register_demo_password'),
						status : j('#status_register_demo_password'),
						rules : [
							{ method : 'required', message : getContent("registerPasswordRequired") },
							{ method : 'rangelength', pattern : [6,12], message : getContent("registerPasswordRangeLength") },
							{ method : 'equalTo', pattern : /^[a-zA-Z0-9_.!-]+$/, message : getContent("registerPasswordEqualTo") },
							{ method : 'notEqualTo', element : j('#input_register_demo_user_name'), message : getContent("registerPasswordNotEqualTo") }
						]
					}, {
						element : j('#input_register_demo_retype_password'),
						status : j('#status_register_demo_retype_password'),
						rules : [
							{ method : 'required',  message : getContent("registerRetypePasswordRequired") },
							{ method : 'rangelength', pattern : [6,12], message : getContent("registerRetypePasswordRangeLength") },
							{ method : 'equalTo',  element : j('#input_register_demo_password'), message : getContent("registerRetypePasswordEqualTo") }
						]
					}, {
						element : j('#checkbox_register_demo_accept_terms'),
						status : j('#status_register_demo_accept_terms'),
						rules : [
							{ method : 'required', message : getContent("registerAcceptTermsRequired") }
						]
					}, {
						element : j('#checkbox_register_demo_is_over_eighteen'),
						status : j('#status_register_demo_is_over_eighteen'),
						rules: [
							{ method : 'required', message : getContent("registerOverEighteenRequired") }
						]
					},{
						element : j('#input_register_demo_captcha'),
						status : j('#status_register_demo_captcha'),
						validate : function() {
							return isCaptchaRequired;
						},
						rules: [
							{ method : 'required', message : getContent("registerCaptchaRequired") }
						]
					}
				],
				submitElement : j('#button_register_demo'),
				messages : null,
				accept : function() {
					
					if(enableSubmit) {
					
						enableSubmit = false;
						
						var fullName = j.trim(j('#input_register_demo_full_name').val()).split(' '),
							firstName = fullName[0],
							lastName = "";
							
						if(fullName.length > 1) {
							for(var i = 1; i < fullName.length; i++) {
								lastName += (i != fullName.length ? fullName[i] + " " : fullName[i]);
							}
						} else {
							lastName = fullName[fullName.length - 1];
						}
						
						jsapi.register.register({
							firstName: firstName,
							lastName: lastName,
							email: j('#input_register_demo_email').val(),
							phone1: j('#input_register_demo_phone1').val(),
							userName: j('#input_register_demo_user_name').val(),
							password: j('#input_register_demo_password').val(),
							enableReceiveNewsletters: j('#checkbox_register_demo_enable_receive_newsletters').is(':checked'),
							cultureId: cultureId,
							progressStep : 1,
							isOverEighteen: j('#checkbox_register_demo_is_over_eighteen').is(':checked'),
							acceptTerms: true,
							isDemo: true,
							regType : 1, // register type demo / practice
							phoneType : 77, // mobile phone type
							gatewayId: handle.registerDemo.gatewayId,
							gatewaySubId: handle.registerDemo.gatewaySubId,
							gatewayLprId: handle.registerDemo.gatewayLprId,
							referer: null,
							captcha: j('#input_register_demo_captcha').val(),
							googleClickId : handle.registerDemo.googleClickId,
							kenshooClickId : handle.registerDemo.kenshooClickId,
							googleAdId : handle.registerDemo.googleAdId,
							googleKeyword : handle.registerDemo.googleKeyword,
							success : function(data) {
								
								isAuthenticated = true;
								
								// set cookie session
								j.cookie('sid', data.sessionId, { expires: 365 });
								
								// alpha pixel -> lead
								try {
									var cookieVal = (handle.registerDemo.gatewayId || "") +"_"+ (handle.registerDemo.gatewaySubId || "") +"_"+ data.uniqueVisitorId;
									j.cookie("LEADPIXEL", cookieVal);
								} catch(e) {}
								
								// google tag manager -> event -> registerDemo
								dataLayer.push({'event': 'registerDemo'});
								
								j('.before-login').hide();
								j('.after-login').show();
								
								jsapi.accounts.user.getUserInfo({
									success : function(data) {
										
										isEnableFractionalPips = data.isEnableFractionalPips;
										accountBaseCurrency = data.accountBaseCurrency;
										withdrawalAvailable = data.withdrawalAvailable;
										allowWithdrawal = data.allowWithdrawal;
										allowDeposit = data.allowDeposit;
										isActive = data.isActive;
										isDemo = data.isDemo;
										userStatus = data.userStatus;
										needToPassTest = data.needToPassTest;
										isSuitabilityTestPassed = data.isSuitabilityTestPassed;
										regionId = data.regionId;
										regTicketId = data.regTicketId;
										
										// register
										j('.label-user-id').text(jsapi.accounts.user.userInfo.userId);
										
										// If user is demo show demo label
										if(isDemo) {
											j('.label-demo').show();
										}
										
										switch(regionId) {
											case 1: {
												// Asia Pacific
												j(".risk-warning-copyright").html(getContent("copyrightAsiaPacific"));
												j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_au_phone.pdf");
												break;
											}
											case 11 : {
												// Europe
												j(".risk-warning-copyright").html(getContent("copyrightEurope"));
												j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_eu_phone.pdf");
												break;
											}
											default : {
												// Default
												j(".risk-warning-copyright").html(getContent("copyright"));
												j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_int_phone.pdf");
											}
										}
										
										// getTrading
										jsapi.trading.getTrading({
											success : function(data) {
												
												j('#label_register_username').text(jsapi.accounts.user.userInfo.userName);
												screenManager.show(screens.registerComplete);
												
												try {
													if(j.cookie("LEADPIXEL") != null) {
														// send lead pixel
														handle.register.sendLeadPixel();
													}
												} catch(e) {}
												
											},
											error : function(error) {
												
												if(error != errors.applicationNotLoggedIn) {
													var modal = new lightFace({
														title : "",
														message : getContent(error),
														actions : [
														   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
														],
														overlayAll : true
													});
												}
												
											}
										});
										
										
									},
									error : function(error) {
										
										if(error != errors.applicationNotLoggedIn) {
											var modal = new lightFace({
												title : "",
												message : getContent(error),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
										}
										
									}
								});
								
								
							},
							error : function(error) {
							
								enableSubmit = true;
							
								// captcha or username or something another
								if(error == "403") {
									// username is already exists
									var modal = new lightFace({
										title : "",
										message : getContent(error),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
									
									j('#input_register_demo_user_name').val('');
									
								}
								
								if(error == "305" 
									|| error == "304") {
									
									var modal = new lightFace({
										title : "",
										message : getContent(error),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
									
									// refresh captcha
									refreshCaptcha();
								}

                                if(error == "412" || error == "413") {//FirstName or lastname is invalid
                                        var modal = new lightFace({
										    title : "",
										    message : getContent("registerFullNameEqualTo"),
										    actions : [
										       { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										    ],
										    overlayAll : true
									    });
                                        j('#input_register_demo_full_name').val('');
                                    }
								
								if(error == "303") {
									getCaptchaSettings();
								}
									
							}
						});
						
					}
					
				},
				error : function() {
				
				}
			});
			
			// check for exist username
			j('#input_register_demo_user_name')
				.keyup(function(e) {
					if(e.keyCode == '13') {
						return
					}
					j('#status_register_demo_exist_user_name').hide();
				})
				.blur(function() {
				if(j(this).hasClass('valid')) {
					jsapi.accounts.user.checkExistUserName({
						userName : j.trim(j(this).val().toLowerCase()),
						success : function() {
							j('#status_register_demo_exist_user_name').hide();
						},
						error : function() {
							j('#status_register_demo_exist_user_name').show();
						}
					});
				}
			});
			
			var getCaptchaSettings = function() {
				// get register settings
				loader.show();
				jsapi.register.getRegisterSettings({
					success: function (data) {
						
						loader.hide();
						
						if(data.isCaptchaRequired) {
							
							// show captcha
							j('#row_register_demo_captcha').show();
							
							isCaptchaRequired = true;
							
							if (data.captchaFileName != undefined) {
								captchaURL = common.jsapiUrl + "captcha/" + data.captchaFileName;
								refreshCaptcha();

								j('#link_register_demo_refresh_captcha').unbind('click').click(function() {
									refreshCaptcha();
								});
							}
							
						} else {
						
							// show captcha
							j('#row_register_demo_captcha').hide();
							
							isCaptchaRequired = false;
						}
						
					},
					error : function() {
						loader.hide();
					}
				});
				
			}
			
			getCaptchaSettings();
			
			
			var imgUrl = "";
			
			if(j.getUrlParam('l') == "pl") {
				
				// google-conversions
				var google_conversion_id = "1013616679";
				var google_conversion_label = "Jj5hCNGIyQIQp6Cq4wM";
				
				imgUrl = "https://www.googleadservices.com/pagead/conversion/"+ google_conversion_id +"/?label="+ google_conversion_label +"&amp;guid=ON&amp;script=0";
				var img1 = new Image();          
				img1.src = imgUrl;

			}
			
			// yieldManager
			imgUrl = "https://ad.yieldmanager.com/pixel?id=1375246&t=2"; 
			var img2 = new Image();          
			img2.src = imgUrl;
			
			
		},
		destroy : function() {}
	},
	register : {
		regType : 0,
		gatewayId : null,
		gatewaySubId : null,
		gatewayLprId : null,
		googleClickId : null,
		kenshooClickId : null,
        isRegularFlow : false,
		googleAdId : null,
		googleKeyword : null,
		init : function() {
		
			this.regType = (j.getUrlParam('regtype').length != 0 ? parseInt(j.getUrlParam('regtype')) : 0);
			
			var gateway = j.cookie('de4fe6');
			this.googleClickId = j.cookie("GoogleClickId");
			this.kenshooClickId = j.cookie("KenshooClickId");
			var adWordsInfo = j.parseJSON(j.cookie("adWordsInfo"));			
			this.googleAdId = adWordsInfo==null? null : adWordsInfo.adId;
			this.googleKeyword = adWordsInfo==null? null : adWordsInfo.keyword;
			
			var getCookieValueByKey = function(key, value) {
				var pattern = new RegExp(key + "=([^&#]*)", "i");
				var res = pattern.exec(value);
				return res ? decodeURIComponent(res[1]) : null;
			};
			
			if(gateway) {
				this.gatewayId = getCookieValueByKey("TAR", gateway);
				this.gatewaySubId = getCookieValueByKey("SAR", gateway);
				this.gatewayLprId = getCookieValueByKey("LPR_Id", gateway);
			}
			
			if(regTicketId != null) {
				if(jsapi.accounts.user.userInfo.accountType != null) {	
					this.complete();
				} else {	                    		
					this.activate();
				}
			} else {				
                this.register();
			}
			
			
		},

		register : function() {
		
			if(typeof (dataLayer)!="undefined"  ){
				dataLayer.push({page: 'register_step1'});
				dataLayer.push({'event': 'page_ready'});							
			}	
			
			j('#section_register_register').show();
			j('#section_register_activate').hide();
			j('#section_register_complete').hide();
			
			
			j('#button_register_back').show();
			
			j('#tab_register_register').addClass('active');
			j('#tab_register_activate').removeClass('active').unbind('click');
			j('#tab_register_complete').removeClass('active');
			
			var countries = [{"id":249,"country":"Afghanistan","countryCode":"93"},{"id":1,"country":"Albania","countryCode":"355"},{"id":2,"country":"Algeria","countryCode":"213"},{"id":4,"country":"Andorra","countryCode":"376"},{"id":5,"country":"Angola","countryCode":"244"},{"id":6,"country":"Anguilla","countryCode":"1"},{"id":7,"country":"Antarctica","countryCode":"672"},{"id":8,"country":"Antigua And Barbuda","countryCode":"1"},{"id":9,"country":"Argentina","countryCode":"54"},{"id":10,"country":"Armenia","countryCode":"374"},{"id":11,"country":"Aruba","countryCode":"297"},{"id":12,"country":"Australia","countryCode":"61"},{"id":13,"country":"Austria","countryCode":"43"},{"id":14,"country":"Azerbaijan","countryCode":"994"},{"id":15,"country":"Bahamas","countryCode":"1"},{"id":16,"country":"Bahrain","countryCode":"973"},{"id":17,"country":"Bangladesh","countryCode":"880"},{"id":18,"country":"Barbados","countryCode":"1"},{"id":19,"country":"Belarus","countryCode":"375"},{"id":20,"country":"Belgium","countryCode":"32"},{"id":21,"country":"Belize","countryCode":"501"},{"id":22,"country":"Benin","countryCode":"229"},{"id":23,"country":"Bermuda","countryCode":"1"},{"id":24,"country":"Bhutan","countryCode":"975"},{"id":25,"country":"Bolivia","countryCode":"591"},{"id":26,"country":"Bosnia and Herzegovina","countryCode":"387"},{"id":27,"country":"Botswana","countryCode":"267"},{"id":28,"country":"Bouvet Island","countryCode":null},{"id":29,"country":"Brazil","countryCode":"55"},{"id":30,"country":"British Indian Ocean Territory","countryCode":"1"},{"id":31,"country":"Brunei Darussalam","countryCode":"673"},{"id":32,"country":"Bulgaria","countryCode":"359"},{"id":33,"country":"Burkina Faso","countryCode":"226"},{"id":35,"country":"Burundi","countryCode":"257"},{"id":36,"country":"Cambodia","countryCode":"855"},{"id":37,"country":"Cameroon","countryCode":"237"},{"id":38,"country":"Canada","countryCode":"1"},{"id":39,"country":"Cape Verde","countryCode":"238"},{"id":40,"country":"Cayman Islands","countryCode":"1"},{"id":41,"country":"Central African Republic","countryCode":"236"},{"id":42,"country":"Chad","countryCode":"235"},{"id":43,"country":"Chile","countryCode":"56"},{"id":44,"country":"China","countryCode":"86"},{"id":45,"country":"Christmas Island","countryCode":"61"},{"id":46,"country":"Cocos (Keeling) Islands","countryCode":"61"},{"id":47,"country":"Colombia","countryCode":"57"},{"id":48,"country":"Comoros","countryCode":"269"},{"id":49,"country":"Congo","countryCode":"242"},{"id":50,"country":"Congo, the Democratic Republic of the","countryCode":"243"},{"id":51,"country":"Cook Islands","countryCode":"682"},{"id":52,"country":"Costa Rica","countryCode":"506"},{"id":53,"country":"Cote d'Ivoire","countryCode":"225"},{"id":54,"country":"Croatia","countryCode":"385"},{"id":250,"country":"Cuba","countryCode":"53"},{"id":55,"country":"Cyprus","countryCode":"357"},{"id":56,"country":"Czech Republic","countryCode":"420"},{"id":57,"country":"Denmark","countryCode":"45"},{"id":58,"country":"Djibouti","countryCode":"253"},{"id":59,"country":"Dominica","countryCode":"1"},{"id":60,"country":"Dominican Republic","countryCode":"1"},{"id":61,"country":"East Timor","countryCode":"670"},{"id":62,"country":"Ecuador","countryCode":"593"},{"id":63,"country":"Egypt","countryCode":"20"},{"id":64,"country":"El Salvador","countryCode":"503"},{"id":66,"country":"Equatorial Guinea","countryCode":"240"},{"id":67,"country":"Eritrea","countryCode":"291"},{"id":69,"country":"Estonia","countryCode":"372"},{"id":70,"country":"Ethiopia","countryCode":"251"},{"id":71,"country":"Falkland Islands","countryCode":"500"},{"id":72,"country":"Faroe Islands","countryCode":"298"},{"id":73,"country":"Fiji","countryCode":"679"},{"id":74,"country":"Finland","countryCode":"358"},{"id":75,"country":"France","countryCode":"33"},{"id":76,"country":"French Guiana","countryCode":"594"},{"id":77,"country":"French Polynesia","countryCode":"689"},{"id":78,"country":"French Southern Territories","countryCode":null},{"id":125,"country":"FYR Macedonia","countryCode":"389"},{"id":79,"country":"Gabon","countryCode":"241"},{"id":80,"country":"Gambia","countryCode":"220"},{"id":81,"country":"Georgia","countryCode":"995"},{"id":82,"country":"Germany","countryCode":"49"},{"id":83,"country":"Ghana","countryCode":"233"},{"id":84,"country":"Gibraltar","countryCode":"350"},{"id":86,"country":"Greece","countryCode":"30"},{"id":87,"country":"Greenland","countryCode":"299"},{"id":88,"country":"Grenada","countryCode":"1"},{"id":89,"country":"Guadeloupe","countryCode":"590"},{"id":91,"country":"Guatemala","countryCode":"502"},{"id":252,"country":"Guernsey","countryCode":"1481"},{"id":92,"country":"Guinea","countryCode":"224"},{"id":93,"country":"Guinea-Bissau","countryCode":"245"},{"id":94,"country":"Guyana","countryCode":"592"},{"id":246,"country":"Haiti","countryCode":"509"},{"id":95,"country":"Heard and Mc Donald Islands","countryCode":null},{"id":200,"country":"Helena","countryCode":"290"},{"id":253,"country":"Holy See(Vatican City State)","countryCode":"379"},{"id":96,"country":"Honduras","countryCode":"504"},{"id":97,"country":"Hong Kong","countryCode":"852"},{"id":98,"country":"Hungary","countryCode":"36"},{"id":99,"country":"Iceland","countryCode":"354"},{"id":100,"country":"India","countryCode":"91"},{"id":101,"country":"Indonesia","countryCode":"62"},{"id":244,"country":"Iran","countryCode":"98"},{"id":248,"country":"Iraq","countryCode":"964"},{"id":102,"country":"Ireland","countryCode":"353"},{"id":254,"country":"Isle of Man","countryCode":"1624"},{"id":104,"country":"Italy","countryCode":"39"},{"id":105,"country":"Jamaica","countryCode":"1"},{"id":106,"country":"Japan","countryCode":"81"},{"id":255,"country":"Jersey","countryCode":null},{"id":107,"country":"Jordan","countryCode":"962"},{"id":108,"country":"Kazakhstan","countryCode":"7"},{"id":109,"country":"Kenya","countryCode":"254"},{"id":110,"country":"Kiribati","countryCode":"686"},{"id":114,"country":"Kuwait","countryCode":"965"},{"id":115,"country":"Kyrgyzstan","countryCode":"996"},{"id":116,"country":"Lao People","countryCode":"856"},{"id":117,"country":"Latvia","countryCode":"371"},{"id":118,"country":"Lebanon","countryCode":"961"},{"id":119,"country":"Lesotho","countryCode":"266"},{"id":120,"country":"Liberia","countryCode":"231"},{"id":256,"country":"Libyan Arab Jamahiriya","countryCode":"218"},{"id":121,"country":"Liechtenstein","countryCode":"423"},{"id":122,"country":"Lithuania","countryCode":"370"},{"id":123,"country":"Luxembourg","countryCode":"352"},{"id":124,"country":"Macau","countryCode":"853"},{"id":126,"country":"Madagascar","countryCode":"261"},{"id":127,"country":"Malawi","countryCode":"265"},{"id":128,"country":"Malaysia","countryCode":"60"},{"id":129,"country":"Maldives","countryCode":"960"},{"id":130,"country":"Mali","countryCode":"223"},{"id":131,"country":"Malta","countryCode":"356"},{"id":132,"country":"Marshall Islands","countryCode":"692"},{"id":133,"country":"Martinique","countryCode":"596"},{"id":134,"country":"Mauritania","countryCode":"222"},{"id":135,"country":"Mauritius","countryCode":"230"},{"id":136,"country":"Mayotte","countryCode":"269"},{"id":137,"country":"Mexico","countryCode":"52"},{"id":138,"country":"Micronesia, Federated States of","countryCode":"691"},{"id":139,"country":"Moldova, Republic of","countryCode":"373"},{"id":140,"country":"Monaco","countryCode":"377"},{"id":141,"country":"Mongolia","countryCode":"976"},{"id":262,"country":"Montenegro","countryCode":"382"},{"id":142,"country":"Montserrat","countryCode":"1"},{"id":143,"country":"Morocco","countryCode":"212"},{"id":144,"country":"Mozambique","countryCode":"258"},{"id":145,"country":"Myanmar","countryCode":"95"},{"id":146,"country":"Namibia","countryCode":"264"},{"id":147,"country":"Nauru","countryCode":"674"},{"id":148,"country":"Nepal","countryCode":"977"},{"id":149,"country":"Netherlands","countryCode":"31"},{"id":150,"country":"Netherlands Antilles","countryCode":"599"},{"id":151,"country":"New Caledonia","countryCode":"687"},{"id":152,"country":"New Zealand","countryCode":"64"},{"id":153,"country":"Nicaragua","countryCode":"505"},{"id":154,"country":"Niger","countryCode":"227"},{"id":155,"country":"Nigeria","countryCode":"234"},{"id":156,"country":"Niue","countryCode":"683"},{"id":157,"country":"Norfolk Island","countryCode":"672"},{"id":159,"country":"Northern Mariana Islands","countryCode":"1"},{"id":160,"country":"Norway","countryCode":"47"},{"id":161,"country":"Oman","countryCode":"968"},{"id":162,"country":"Pakistan","countryCode":"92"},{"id":163,"country":"Palau","countryCode":"680"},{"id":242,"country":"Palestine","countryCode":"970"},{"id":164,"country":"Panama","countryCode":"507"},{"id":165,"country":"Papua New Guinea","countryCode":"675"},{"id":166,"country":"Paraguay","countryCode":"595"},{"id":167,"country":"Peru","countryCode":"51"},{"id":168,"country":"Philippines","countryCode":"63"},{"id":169,"country":"Pitcairn","countryCode":"649"},{"id":170,"country":"Poland","countryCode":"48"},{"id":171,"country":"Portugal","countryCode":"351"},{"id":173,"country":"Qatar","countryCode":"974"},{"id":174,"country":"Reunion","countryCode":"262"},{"id":175,"country":"Romania","countryCode":"40"},{"id":177,"country":"Russian Federation","countryCode":"7"},{"id":178,"country":"Rwanda","countryCode":"250"},{"id":257,"country":"Saint Helena","countryCode":"290"},{"id":179,"country":"Saint Kitts and Nevis","countryCode":"1"},{"id":180,"country":"Saint Lucia","countryCode":"1"},{"id":181,"country":"Saint Vincent and the Grenadines","countryCode":"1"},{"id":182,"country":"Samoa (Independent)","countryCode":"685"},{"id":183,"country":"San Marino","countryCode":"378"},{"id":184,"country":"Sao Tome and Principe","countryCode":"239"},{"id":185,"country":"Saudi Arabia","countryCode":"966"},{"id":187,"country":"Senegal","countryCode":"221"},{"id":243,"country":"Serbia","countryCode":"381"},{"id":188,"country":"Seychelles","countryCode":"248"},{"id":189,"country":"Sierra Leone","countryCode":"232"},{"id":190,"country":"Singapore","countryCode":"65"},{"id":191,"country":"Slovakia","countryCode":"421"},{"id":192,"country":"Slovenia","countryCode":"386"},{"id":193,"country":"Solomon Islands","countryCode":"677"},{"id":194,"country":"Somalia","countryCode":"252"},{"id":195,"country":"South Africa","countryCode":"27"},{"id":196,"country":"South Georgia and the South Sandwich Islands","countryCode":null},{"id":112,"country":"South Korea (Republic of Korea)","countryCode":null},{"id":198,"country":"Spain","countryCode":"34"},{"id":199,"country":"Sri Lanka","countryCode":"94"},{"id":201,"country":"St. Pierre and Miquelon","countryCode":"508"},{"id":245,"country":"Sudan","countryCode":"249"},{"id":202,"country":"Suriname","countryCode":"597"},{"id":203,"country":"Svalbard and Jan Mayen Islands","countryCode":"79"},{"id":204,"country":"Swaziland","countryCode":"268"},{"id":205,"country":"Sweden","countryCode":"46"},{"id":206,"country":"Switzerland","countryCode":"41"},{"id":247,"country":"Syria","countryCode":"963"},{"id":207,"country":"Taiwan","countryCode":"886"},{"id":208,"country":"Tajikistan","countryCode":"992"},{"id":209,"country":"Tanzania","countryCode":"255"},{"id":210,"country":"Thailand","countryCode":"66"},{"id":211,"country":"Togo","countryCode":"228"},{"id":212,"country":"Tokelau","countryCode":"690"},{"id":213,"country":"Tonga","countryCode":"676"},{"id":215,"country":"Trinidad and Tobago","countryCode":"1"},{"id":216,"country":"Tunisia","countryCode":"216"},{"id":217,"country":"Turkey","countryCode":"90"},{"id":218,"country":"Turkmenistan","countryCode":"993"},{"id":219,"country":"Turks and Caicos Islands","countryCode":"1"},{"id":220,"country":"Tuvalu","countryCode":"688"},{"id":221,"country":"Uganda","countryCode":"256"},{"id":222,"country":"Ukraine","countryCode":"380"},{"id":223,"country":"United Arab Emirates","countryCode":"971"},{"id":224,"country":"United Kingdom","countryCode":"44"},{"id":227,"country":"Uruguay","countryCode":"598"},{"id":229,"country":"Uzbekistan","countryCode":"998"},{"id":230,"country":"Vanuatu","countryCode":"678"},{"id":231,"country":"Vatican City State (Holy See)","countryCode":"39"},{"id":232,"country":"Venezuela","countryCode":"58"},{"id":233,"country":"Viet Nam","countryCode":"84"},{"id":234,"country":"Virgin Islands (British)","countryCode":"1"},{"id":237,"country":"Wallis and Futuna Islands","countryCode":"681"},{"id":238,"country":"Western Sahara","countryCode":"212"},{"id":239,"country":"Yemen","countryCode":"967"},{"id":240,"country":"Zambia","countryCode":"260"},{"id":241,"country":"Zimbabwe","countryCode":"263"}];
			var auCountries = [12,31,101,128,152,165,168,190];
			var validateProductDisclosureStatement = false;
			
			var getCountryCodeByCountryId = function(countryId) {
				var countryCode = 0;
				for (var j = 0; j < countries.length; j++) {					
					if (countries[j].id == countryId) {
						countryCode = countries[j].countryCode;
						break;
					}
				}
				
                return countryCode;
			};

            var validateState = false;
			
			// fill countries
			j('#select_country').empty();
			var e = j('#select_country')[0].options;
			var k = new Option(getContent("select"), "");
			try { e.add(k); } catch (ex) { e.add(k, null); }
	        for (var i = 0; i < countries.length; i++) {
	            var k = new Option(countries[i].country, countries[i].id);
	            try {
	                e.add(k);
	            } catch (ex) {
	                e.add(k, null);
	            }
	        }

             j('#select_country')
	            .unbind('change')
	            .change(function() {
					
		            var a = j(this).find('option:selected');
		            var countrySelected = parseInt(a.val());
					
		            if(!isNaN(countrySelected)) {						

			            var statesByCounty = getStates(countrySelected, true);

			            if(statesByCounty) {
                        				
				            // show states
				            j('#row_register_states').show();
				            validateState = true;
				
				            // fill states
				            j('#select_state').empty();
				            var e = j('#select_state')[0].options;
				            for(var i = 0; i < statesByCounty.length; i++) {
					            var k = new Option(statesByCounty[i].state, statesByCounty[i].stateId);
					            try {
						            e.add(k)
					            } catch (ex) {
						            e.add(k, null)
					            }
				            }
			            }
			            else {
				            // hide experiance questions
				            j('#row_register_states').hide();
				            validateState = false;
			            }
		            }
	            });
	        
			var isCaptchaRequired = false;
			var captchaURL = null;
			var refreshCaptcha = function () {
			
				var timeStamp = j.getTimestamp();
				var src = captchaURL + "?timeStamp=" + timeStamp;
				
				// new image object
				var img = new Image();
				
				// image onload
				j(img).load(function () {
					j(this).css('display','none');
					j('#image_captcha')
						.empty()
						.append(this);
					j(this).show();
				}).attr('src',src);
				
				j('#input_captcha')
					.val('');
				
			};
			
			// register
			var enableSubmit = true;
			j('#button_register').unbind('click');
			
			// clear elements
			j('#select_title, #input_first_name, #input_last_name, #select_country, #select_state, #input_email, #input_phone1, #input_register_user_name, #input_register_password, #input_retype_password, #select_secret_hint_question, #input_secret_hint_answer, #input_captcha')
				.val('')
				.removeClass('predefined invalid valid');
				
			j('#checkbox_is_over_eighteen')
				.prop("checked", false)
				.removeClass('predefined invalid valid');
			
			
			
			var validator = null;
			validator = jQuery.validator({
				elements : [
					{
						element : j('#select_title'),
						status : j('#status_title'),
						rules : [
							{ method : 'required', message : getContent("registerTitleRequired") }
						]
					}, {
						element : j('#input_first_name'),
						status : j('#status_first_name'),
						rules : [
							{ method : 'required', message : getContent("registerFirstNameRequired") },
							{ method : 'equalTo' , pattern : /^[\S -]*[\S -]+$/, message : getContent("registerFirstNameEqualTo") }
						]
					}, {
						element : j('#input_last_name'),
						status : j('#status_last_name'),
						rules : [
							{ method : 'required', message : getContent("registerLastNameRequired") },
							{ method : 'equalTo' , pattern : /^[\S -]*[\S -]+$/, message : getContent("registerLastNameEqualTo") }
						]
					}, {
						element : j('#select_country'),
						status : j('#status_country'),
						rules : [
							{ method : 'required', message : getContent("registerCountryRequired") }
						]
					},{
						element : j('#select_state'),
						status : j('#status_state'),
						rules : [
							{ method : 'required', message : getContent("registerStateRequired") }
						],
						validate : function() { 
							return validateState; 
						}
					},{
						element : j('#input_email'),
						status : j('#status_email'),
						rules : [
							{ method : 'required', message : getContent("registerEmailRequired") },
							/* { method : 'emailISO', message : getContent("registerEmailInvalid") } */
							{ method : 'email', message : getContent("registerEmailInvalid") }
						]
					}, {
						element : j('#input_phone1'),
						status : j('#status_phone1'),
						rules : [
							{ method : 'required', message : getContent("registerPhoneRequired") },
							{ method : 'digits' , message : getContent("registerPhoneOnlyDigits") },
							{ method : 'minlength', pattern : 5, message : getContent("registerPhoneMinLength") }
						]
					}, {
						element : j('#input_register_user_name'),
						status : j('#status_register_user_name'),
						rules : [
							{ method : 'required', message : getContent("registerUserNameRequired") },
							{ method : 'minlength', pattern : 6, message : getContent("registerUserNameMinLength") },
							{ method : 'equalTo', pattern : /^[a-zA-Z0-9_.!-]+$/ , message: getContent("registerUserNameEqualTo") }
						]
					}, {
						element : j('#input_register_password'),
						status : j('#status_register_password'),
						rules : [
							{ method : 'required', message : getContent("registerPasswordRequired") },
							{ method : 'rangelength', pattern : [6,12], message : getContent("registerPasswordRangeLength") },
							{ method : 'equalTo', pattern : /^[a-zA-Z0-9_.!-]+$/, message : getContent("registerPasswordEqualTo") },
							{ method : 'notEqualTo', element : j('#input_register_user_name'), message : getContent("registerPasswordNotEqualTo") }
						]
					}, {
						element : j('#input_retype_password'),
						status : j('#status_retype_password'),
						rules : [
							{ method : 'required',  message : getContent("registerRetypePasswordRequired") },
							{ method : 'rangelength', pattern : [6,12], message : getContent("registerRetypePasswordRangeLength") },
							{ method : 'equalTo',  element : j('#input_register_password'), message : getContent("registerRetypePasswordEqualTo") }
						]
					}, {
						element : j('#select_secret_hint_question'),
						status : j('#status_secret_hint_question'),
						rules : [
							{ method : 'required',  message : getContent("registerSecretHintQuestionRequired") }
						]
					}, {
						element : j('#input_secret_hint_answer'),
						status : j('#status_secret_hint_answer'),
						rules : [
							{ method : 'required', message : getContent("registerSecretHintAnswerRequired") },							
                            { method : 'minlength', pattern : 2, message : getContent("registerSecretHintAnswerMinLength") },
                            { method : 'equalTo', pattern : /^[\S0-9: \/]+$/, message : getContent("registerSecretHintAnswerEqualTo") }
						]
					}, {
						element : j('#checkbox_is_over_eighteen'),
						status : j('#status_is_over_eighteen'),
						rules: [
							{ method : 'required', message : getContent("registerOverEighteenRequired") }
						]
					}, {
						element : j('#input_captcha'),
						status : j('#status_captcha'),
						validate : function() {
							return isCaptchaRequired;
						},
						rules: [
							{ method : 'required', message : getContent("registerCaptchaRequired") }
						]
					}
				],
				submitElement : j('#button_register'),
				messages : null,
				accept : function() {
				
					if(enableSubmit) {
					
						enableSubmit = false;
				
						jsapi.register.register({
							title: j('#select_title').val(),
							firstName: j.trim(j('#input_first_name').val()),
							lastName: j.trim(j('#input_last_name').val()),
							countryId: j('#select_country').val(),
                            stateId: j('#select_state').val(),
							email: j('#input_email').val(),
							countryCode: getCountryCodeByCountryId(j('#select_country').val()),
							phone1: j('#input_phone1').val(),
							userName: j('#input_register_user_name').val(),
							password: j('#input_register_password').val(),
							secretHintQuestionId: j('#select_secret_hint_question').val(),
							secretHintAnswer: j('#input_secret_hint_answer').val(),
							enableReceiveNewsletters: j('#checkbox_enable_receive_newsletters').is(':checked'),
							cultureId: cultureId,
							progressStep : 1,
							isOverEighteen: j('#checkbox_is_over_eighteen').is(':checked'),
							acceptTerms: false,
							isDemo: false,
							regType : handle.register.regType,
							phoneType : j('input[name^=group_phone_type]:checked').val(),
							gatewayId: handle.register.gatewayId,
							gatewaySubId: handle.register.gatewaySubId,
							gatewayLprId: handle.register.gatewayLprId,
							referer: null,
							captcha: j('#input_captcha').val(),
							googleClickId: handle.register.googleClickId,
							kenshooClickId : handle.register.kenshooClickId,
							googleAdId : handle.registerDemo.googleAdId,
							googleKeyword : handle.registerDemo.googleKeyword,
							success : function(data) {
								
								isAuthenticated = true;
				                
								// media mind
								try{
				                    var ebRand = Math.random()+'';
				                    ebRand = ebRand * 1000000;
				                    j.ajax({ url: 'https://bs.serving-sys.com/BurstingPipe/ActivityServer.bs?cn=as&amp;ActivityID=261432&amp;rnd=' + ebRand,dataType: 'script'});
				                } catch(e){}
								
								// set cookie session
								j.cookie('sid', data.sessionId, { expires: 365 });
								
								// alpha pixel -> lead
								try {
									var cookieVal = (handle.register.gatewayId || "") +"_"+ (handle.register.gatewaySubId || "") +"_"+ data.uniqueVisitorId;
									j.cookie("LEADPIXEL", cookieVal);
								} catch(e) {}
								
								// google tag manager -> event -> register
								dataLayer.push({'event': 'register'});
								
								// turn pixel
								var img = new Image();
								// wrap our new image in jQuery, then:
								j(img).load(function () {
								  j('body')
									.append(this);
								})
								.attr("src", "https://r.turn.com/r/beacon?b2=zJ_qaYb_pvMErVkTwMzjLni4RJWnYWyOuwAwW-B8GUjDlmMA_kAP0Y8iBKhZIRKrUFo4VrjujrpblkXSpXCQng&cid=");
								
								
								j('.before-login').hide();
								j('.after-login').show();
								
								jsapi.accounts.user.getUserInfo({
									success : function(data) {
										
										isEnableFractionalPips = data.isEnableFractionalPips;
										accountBaseCurrency = data.accountBaseCurrency;
										withdrawalAvailable = data.withdrawalAvailable;
										allowWithdrawal = data.allowWithdrawal;
										allowDeposit = data.allowDeposit; 
										isActive = data.isActive;
										isDemo = data.isDemo;
										userStatus = data.userStatus;
										needToPassTest = data.needToPassTest;
										isSuitabilityTestPassed = data.isSuitabilityTestPassed;
										regionId = data.regionId;
										regTicketId = data.regTicketId;
										
										// register
										j('.label-user-id').text(jsapi.accounts.user.userInfo.userId);
										
										// If user is demo show demo label
										if(isDemo) {
											j('.label-demo').show();
										}
										
										switch(regionId) {
											case 1: {
												// Asia Pacific
												j(".risk-warning-copyright").html(getContent("copyrightAsiaPacific"));
												j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_au_phone.pdf");
												break;
											}
											case 11 : {
												// Europe
												j(".risk-warning-copyright").html(getContent("copyrightEurope"));
												j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_eu_phone.pdf");
												break;
											}
											default : {
												// Default
												j(".risk-warning-copyright").html(getContent("copyright"));
												j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_int_phone.pdf");
											}
										}
										
										// getTrading
										jsapi.trading.getTrading({
											success : function(data) {
												handle.register.isRegularFlow = true;
												// go to step 2 -> activate
												handle.register.activate();
											},
											error : function(error) {
												
												if(error != errors.applicationNotLoggedIn) {
													var modal = new lightFace({
														title : "",
														message : getContent(error),
														actions : [
														   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
														],
														overlayAll : true
													});
												}
												
											}
										});
									},
									error : function(error) {
										
										if(error != errors.applicationNotLoggedIn) {
											var modal = new lightFace({
												title : "",
												message : getContent(error),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
										}
										
									}
								});
								
							},
							error : function(error) {
							
								enableSubmit = true;
							
								// captcha or username or something another
								if(error == "403") {
									// username is already exists
									var modal = new lightFace({
										title : "",
										message : getContent(error),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
									
									j('#input_register_user_name').val('');
									
								}


                                if(error == "412") {//FirstNameIsInvalid
                                    var modal = new lightFace({
										title : "",
										message : getContent("registerFirstNameEqualTo"),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
                                    j('#input_first_name').val('');
                                }

                                if(error == "413") {//LastNameIsInvalid
                                        var modal = new lightFace({
										title : "",
										message : getContent("registerLastNameEqualTo"),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
                                    j('#input_last_name').val('');
                                }

                                 if(error == "407") {//CityIsInvalid
                                     var modal = new lightFace({
										title : "",
										message : getContent("registerCityEqualTo"),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
                                    j('#input_city').val('');
                                    
                                }

                                if(error == "406") {//AddressIsInvalid
                                     var modal = new lightFace({
										title : "",
										message : getContent("registerAddressEqualTo"),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									}); 
                                    j('#input_address').val('');
                                }

                                if (error == "415") {//SecretAnswerIsInvalid
                                      var modal = new lightFace({
										title : "",
										message : getContent("registerSecretHintAnswerEqualTo"),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
                                    j('#input_secret_hint_answer').val('');
                                }

                                //CityIsInvalid
                                

								
								if(error == "305" 
									|| error == "304") {
									
									var modal = new lightFace({
										title : "",
										message : getContent(error),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
									
									// refresh captcha
									refreshCaptcha();
								}
								
								if(error == "303") {
									getCaptchaSettings();
								}

									
							}
						});
					
					}
				},
				error : function(el) {
					//
				}
			});
			
			// check for exist username
			j('#input_register_user_name')
				.keyup(function(e) {
					if(e.keyCode == '13') {
						return
					}
					j('#status_exist_user_name').hide();
				})
				.blur(function() {
				if(j(this).hasClass('valid')) {
					jsapi.accounts.user.checkExistUserName({
						userName : j.trim(j(this).val().toLowerCase()),
						success : function() {
							j('#status_exist_user_name').hide();
						},
						error : function() {
							j('#status_exist_user_name').show();
						}
					});
				}
			});
			
			var getCaptchaSettings = function() {
				// get register settings
				loader.show();
				jsapi.register.getRegisterSettings({
					success: function (data) {
						
						loader.hide();
						
						if(data.isCaptchaRequired) {
							// show captcha
							j('#row_captcha').show();
							
							isCaptchaRequired = true;
							
							if (data.captchaFileName != undefined) {
								captchaURL = common.jsapiUrl + "captcha/" + data.captchaFileName;
								refreshCaptcha();

								j('#link_refresh_captcha').unbind('click').click(function() {
									refreshCaptcha();
								});
							}
							
						} else {
							
							// show captcha
							j('#row_captcha').hide();
							
							isCaptchaRequired = false;
							
						}
						
					},
					error : function() {
						loader.hide();
					}
				});
				
			}
			
			getCaptchaSettings();
			
			
			
			var imgUrl = "";
			
			if(j.getUrlParam('l') == "pl") {
				
				// google-conversions
				var google_conversion_id = "1013616679";
				var google_conversion_label = "Jj5hCNGIyQIQp6Cq4wM";
				
				imgUrl = "https://www.googleadservices.com/pagead/conversion/"+ google_conversion_id +"/?label="+ google_conversion_label +"&amp;guid=ON&amp;script=0";
				var img1 = new Image();          
				img1.src = imgUrl;

			}
			
			// yieldManager
			imgUrl = "https://ad.yieldmanager.com/pixel?id=1375246&t=2"; 
			var img2 = new Image();          
			img2.src = imgUrl;
			
			
			
		},
		sendLeadPixel : function() {
		
			try {
				var cval = j.cookie("LEADPIXEL");
				var arr = cval.split('_');
				var params = {gatewayId:arr[0],gatewaySubId:arr[1], uniqueVisitorId:arr[2]};
				
				var pixelUrl = "";
				if (params.gatewayId == null) {
				   pixelUrl = "https://alpha.easy-forex.info/~ezforex/track/pixel_EF.php?r=ef_lead&gid=&subid=&uv=" + params.uniqueVisitorId + "|" + 0;
				} else {
				   pixelUrl = "https://alpha.easy-forex.info/~ezforex/track/pixel_EF.php?r=ef_lead&gid=" + params.gatewayId + "&subid=" + (params.gatewaySubId == null ? "" : params.gatewaySubId) + "&uv=" + params.uniqueVisitorId + "|" + 0;
				}
				j('body').append("<iframe width=\"1\" height=\"1\" src=\"" + pixelUrl + "\">");
				 
			} catch(e) {}
			
			j.cookie("LEADPIXEL", null);
			
		},
		sendRegisterPixel : function() {
		
			// UK Countries
			var ukCountries = [65,85,158,186,224,236];
			jsapi.marketInfo.geoLocation.getCountryLocationInfo({
				success : function(data) {
					var countryId = data.geoLocationInfoResult.countryId;
					if(jQuery.inArray(countryId, ukCountries) > -1) {
						try {
							_adftrack = { pm : 90521, id : 1640335 };
							j.ajax({ url: "https://track.adform.net/serving/scripts/trackpoint/async/", dataType: 'script', cache: true });
						} catch(e) {} 
					}
				},
				error: function(error) {} 
			});
		
			try {
				var cval = j.cookie("REGPIXEL");
                var arr = cval.split('_');
                var params = {gatewayId:arr[0],gatewaySubId:arr[1], uniqueVisitorId:arr[2]};
               
                var pixelUrl = "";
				if (params.gatewayId == null) { 
						 pixelUrl = "https://alpha.easy-forex.info/~ezforex/track/pixel_EF.php?r=ef_reg&gid=&subid=&uv=" + params.uniqueVisitorId + "|" + 0;
				} else { 
						 pixelUrl = "https://alpha.easy-forex.info/~ezforex/track/pixel_EF.php?r=ef_reg&gid=" + params.gatewayId + "&subid=" + (params.gatewaySubId == null ? "" : params.gatewaySubId) + "&uv=" + params.uniqueVisitorId + "|" + 0;
				}
                j('body').append("<iframe width=\"1\" height=\"1\" src=\"" + pixelUrl + "\">");
                   
			}catch(e){}
              
            j.cookie("REGPIXEL", null);

		},
		activate :  function() {
					
			if(typeof (dataLayer)!="undefined"){
				var qs ="";
				if(this.isRegularFlow) {
                    this.isRegularFlow = false;
					qs = "?ga_lead=1"									
				} 
				dataLayer.push({page: 'register_step2' + qs});
				dataLayer.push({'event': 'page_ready'});	
			}

			j('#section_register_register').hide();
			j('#section_register_activate').show();
			j('#section_register_complete').hide();
			
			j('#button_register_back').hide();
			
			j('#tab_register_register').removeClass('active');
			j('#tab_register_activate').addClass('active').unbind('click');
			j('#tab_register_complete').removeClass('active');
				
			var defaultAccType = 59;
			var accountBaseCurrencies = [];
			var accountBaseCurrency = null;
			
			var getSelectedPlatforms = function() {
				var p = [];
				j('.checkbox-platform:checked').each(function(i, el) {
					p.push(parseInt(j(el).val()));
				});
				
				return p;
			};
			
			// validate fields
			j('#button_register_continue').unbind('click');
			
			j('#select_account_base_currency')
				.empty()
				.val('')
				.removeClass('predefined invalid valid')
				.change(function() {
					if(j(this).val() != "") {
						accountBaseCurrency = j(this).val();
						j('#status_account_base_currency').hide();
					}
					
				});
			
			var fillAccountBaseCurrencies = function() {
				
				j('#select_account_base_currency')
					.empty()
					.val('')
					.removeClass('predefined invalid valid');
					
				j('#status_account_base_currency').hide();
				
				var temp = [];
				for(var y = 0; y < accountBaseCurrencies.length; y++) {
					if(j('#checkbox_mt4').prop("checked")) {
						if(accountBaseCurrencies[y].isMt4Supported) {
							temp.push(accountBaseCurrencies[y]);
						}
					} else {
						temp.push(accountBaseCurrencies[y]);
					}
				}
				
				var f = j('#select_account_base_currency')[0].options;
				var k = new Option(getContent("select"), "");
				try { f.add(k); } catch (ex) { f.add(k, null); }
				for(var i = 0; i < temp.length; i++) {
					var k = new Option(temp[i].symbol + " (" + temp[i].description + ")", temp[i].symbol);
					k.setAttribute("ismt4supported", temp[i].isMt4Supported);
					try { f.add(k); } catch (ex) { f.add(k, null); }
					
					if(temp[i].symbol == accountBaseCurrency) {
						j('#select_account_base_currency')[0].selectedIndex = (i + 1);
					}
					
				}
				
				if(j('#checkbox_mt4').prop("checked")) {
					if(j('#select_account_base_currency option:selected').attr('ismt4supported') != "true") {
						
						j('#status_account_base_currency')
							.text(getContent("registerAccountBaseCurrencyMT4Required"))
							.show();
						
					}
				}
				
			};
			
			j('#label_mt4_will_be_created').hide();
			j('#checkbox_mt4')
			.unbind('change')
			.change(function() {
				if(j(this).prop("checked")) {
					fillAccountBaseCurrencies();
					j('#label_mt4_will_be_created').show();
				} else {
					fillAccountBaseCurrencies();
					j('#label_mt4_will_be_created').hide();
				}
			});
			
			var activate = function() {
			
				jsapi.register.activateAccount({
					accountBaseCurrency: j('#select_account_base_currency').val(),
					accountTypeId: (j('input[name^=group_account_type]:checked').length == 0 ? defaultAccType : j('input[name^=group_account_type]:checked').val()),
					platforms: getSelectedPlatforms(),
					regTicketId : regTicketId,
					progressStep : 2,
					success : function(data) {
						handle.register.complete();						
					},
					error : function(error) {
						if(error != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : getContent(error),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
					}
				});
			
			};
				
			var f = null;
			f = jQuery.validator({
				elements : [
					{
						element : j('#select_account_base_currency'),
						status : j('#status_account_base_currency'),
						rules : [
							{ 
								method : 'required', 
								message : getContent("registerAccountBaseCurrencyRequired") 
							}
						]
					}
				],
				submitElement: j('#button_register_continue'),
				messages: null,
				accept: function () {
					
					if(j('#checkbox_mt4').prop("checked")) {
						if(j('#select_account_base_currency option:selected').attr('ismt4supported') != "true") {
							
							// TODO:
							var modal = new lightFace({
								title : "",
								message : "Selected account base currency doesn't support by MT4 platform. Please select defferent account base currency.",
								actions : [
									{ 
										label : getContent("ok"), 
										fire : function() { 
											modal.close() 
										}, 
										color: "green" 
									}
								],
								overlayAll : true
							});
							
							
						} else {
							activate();
						}
					} else {				
						activate();
					}
					
				},
				error : function() {
					
				}
			});
			
			// Get default account type
			jsapi.register.getAccountTypes({
				success : function(data) {
					
					// Draw account types
					if(data.accountTypes.length != 0) {
						for(var y = 0; y < data.accountTypes.length; y++) {
							if(data.accountTypes[y].isDefault) {
								defaultAccType = data.accountTypes[y].accountId;
								break;
							}
						}
					}
					
					// get register settings
					loader.show();
					jsapi.register.getRegisterSettings({
						regTicketId : regTicketId,
						success: function (data) {							
							loader.hide();		
							accountBaseCurrencies = data.accountBaseCurrencies;
							accountBaseCurrency = data.accountBaseCurrency;
							
							
							// account type
							if(data.accountType != null) {
								defaultAccType = data.accountType;
							}
							
							j('#list_account_types a input').each(function(i, el) {
								if(j(el).val() == defaultAccType) {
									j(el).prop('checked', true);
								}
							});
							
							j('#list_account_types').accordion( "destroy" );
							j('#list_account_types').accordion({
								collapsible: true,
								active: false, // t
								header : 'a.ui-tab',
								autoheight : false,
								animated: false,
								alwaysOpen: false,
								clearStyle: true,
								change: function(event, ui) {
									if(ui.newHeader != undefined) {
										ui.newHeader.find('input').prop('checked', true);
									}
								}
							});
							
							// platforms
							if(data.platforms) {
							
								var getPlatform = function(value) {
									for(var y = 0; y < data.platforms.length; y++) {
										if(data.platforms[y] == value)
											return true;
									}
									return false;
								};
									
								j('.checkbox-platform').each(function(i, el) {
									if(getPlatform(j(el).val())) {
										j(el).prop('checked', true);
									}
								});
								
							}
							
							
							fillAccountBaseCurrencies();
							
							
							silentScroll(true);
							
						},
						error : function() {
							loader.hide();
						}
					});
					
					
				},
				error : function(error) {
					
				}
			});
			
			try {
				if(j.cookie("LEADPIXEL") != null) {
					// send lead pixel
					handle.register.sendLeadPixel();
				}
			} catch(e) {}
			
			
			
			var imgUrl = "";
			
			//google-conversion
			var google_conversion_id = "989618571";
			var google_conversion_label = "gXquCKXakQIQi8Px1wM";
			
			imgUrl = "https://www.googleadservices.com/pagead/conversion/"+ google_conversion_id +"/?label="+ google_conversion_label +"&amp;guid=ON&amp;script=0"; 
			var img1 = new Image();          
			img1.src = imgUrl; 

			//google-conversion
			google_conversion_id = "1069857991";
			google_conversion_label = "ehJsCKnxPRDH-ZL-Aw";
			
			imgUrl = "https://www.googleadservices.com/pagead/conversion/"+ google_conversion_id +"/?label="+ google_conversion_label +"&amp;guid=ON&amp;script=0"; 
			var img2 = new Image();          
			img2.src = imgUrl;

			//google-conversion
			google_conversion_id = "1022682663";
			google_conversion_label = "LprhCLfXvQEQp8zT5wM";
			
			imgUrl = "https://www.googleadservices.com/pagead/conversion/"+ google_conversion_id +"/?label="+ google_conversion_label +"&amp;guid=ON&amp;script=0"; 
			var img3 = new Image();          
			img3.src = imgUrl; 

			//google-conversion
			google_conversion_id = "1013998204";
			google_conversion_label = "9oCiCNyc7QIQ_MTB4wM";
			
			imgUrl = "https://www.googleadservices.com/pagead/conversion/"+ google_conversion_id +"/?label="+ google_conversion_label +"&amp;guid=ON&amp;script=0"; 
			var img4 = new Image();          
			img4.src = imgUrl;
			
			
			if(j.getUrlParam('l') == "" 
				|| j.getUrlParam('l') == "en" 
				|| j.getUrlParam('l') == "ar") {
				
				//google-conversion
				google_conversion_id = "1013997964";
				google_conversion_label = "S_d2CISz-gMQjMPB4wM";
				
				imgUrl = "https://www.googleadservices.com/pagead/conversion/"+ google_conversion_id +"/?label="+ google_conversion_label +"&amp;guid=ON&amp;script=0"; 
				var img5 = new Image();          
				img5.src = imgUrl;
				
			}

			//yieldManager          
			imgUrl = "https://ad.yieldmanager.com/pixel?id=1375249&t=2"; 
			var img6 = new Image();          
			img6.src = imgUrl;
			
			
		},
		complete : function() {	
        
			if(typeof (dataLayer)!="undefined"  ){
				dataLayer.push({page: 'register_step3'});
				dataLayer.push({'event': 'page_ready'});							
			}	

			j('#section_register_register').hide();
			j('#section_register_activate').hide();
			j('#section_register_complete').show();
			
			j('#button_register_back').hide();
			
			j('#tab_register_register').removeClass('active');
			j('#tab_register_activate')
				.removeClass('active')
				.unbind('click')
				.click(function() {
					handle.register.activate();					
				});
			j('#tab_register_complete').addClass('active');
			
			var validateState = false;
			var auCountries = [12,31,101,128,152,165,168,190];
			var countriesRequestsAdditionalQuestions = [13,20,32,55,56,57,65,69,74,75,82,85,86,98,99,102,104,117,121,122,123,131,149,158,160,170,171,175,186,191,192,198,205,224,236];
			var enableAdditionalQuestions = function(countryId) {
				for(var i = 0; i < countriesRequestsAdditionalQuestions.length; i++)
					if(countriesRequestsAdditionalQuestions[i] == countryId)
						return true;
				return false;
			};

			
			// suitability test logic
			var expAnswersForSuitability = [4];
			var yearsAnswersForSuitability = [1,2];
			var validateAdditionalQuestions = false;
			var validateExcludeQuestions = false;
			var validateExperienceQuestions = false;
			var validateProductDisclosureStatement = false;
			var isContinue = false;
			var isComplianceLogicEnabled = true;
			
			// meaning that dates like 31/09/2011 and 29/02/2011 are not allowed
			var isValidDate = function(dateStr) {
				s = dateStr.split('/');
				d = new Date(+s[2], s[1]-1, +s[0]);
				if (Object.prototype.toString.call(d) === "[object Date]") {
					if (!isNaN(d.getTime()) && d.getDate() == s[0] && d.getMonth() == (s[1] - 1)) {
						return true;
					}
				}
				return false;
			};
			
			var stopRegistration = function(callback) {
				jsapi.register.setRegistrationRestriction({
					regTicketId : regTicketId,
					success : function(data) {
						if(callback != undefined 
							&& typeof callback == 'function')
							callback();
					},
					error : function(error) {
						//
					}
				});
			};
			
			var continueRegistration = function(callback) {
				isContinue = true;
				if(callback != undefined 
					&& typeof callback == 'function')
					callback();
			};
			
			var checkSuitabilityAnswer = function(list, v) {
				for(var t = 0; t < list.length; t++) {
					if(v == list[t]) {
					   return true; 
					}
				}
			};

			// validate form
			var enableSubmit = true;
			j('#button_register_submit').unbind('click');
			j('#status_birth_date').hide();
			
			
			
			
			j('#input_address, #input_city, #register_complete_select_state, #input_zip_code, #select_day, #select_month, #input_year, #select_annual_income, #select_net_worth, #select_occupation_title, #select_occupation_industry, #select_education_level, #select_investment_experience, #select_investment_experience_years, #select_traded_instruments, #select_trades_volumes, #select_trades_frequencies, #select_trades_time_period')
				.val('')
				.removeClass('predefined invalid valid');
			
			var f = null;
			f = jQuery.validator({
				elements : [
					{
						element : j('#input_address'),
						status : j('#status_address'),
						rules : [
							{ method : 'required', message : getContent("registerAddressRequired") },
							{ method : 'equalTo', pattern : /^[\S0-9 _/.-]+$/, message : getContent("registerAddressEqualTo") }
						]
					}, {
						element : j('#input_city'),
						status : j('#status_city'),
						rules : [
							{ method : 'required', message : getContent("registerCityRequired") },
							{ method : 'equalTo', pattern : /^[\S0-9 /.-]+$/, message : getContent("registerCityEqualTo") }
						]
					}, {
						element : j('#register_complete_select_state'),
						status : j('#register_complete_status_state'),
						rules : [
							{ method : 'required', message : getContent("registerStateRequired") }
						],
						validate : function() { 
							return validateState; 
						}
					}, {
						element : j('#input_zip_code'),
						status : j('#status_zip_code'),
						rules : [
							{ method : 'required', message : getContent("registerZipCodeRequired") },
							{ method : 'equalTo', pattern :  /^[a-zA-Z0-9 ]+$/, message : getContent("registerZipCodeEqualTo") }
						]
					}, {
						element : j('#select_day'),
						rules: [
							{ method : 'required', message : 'This field is required.' },
							{ method : 'equalTo', pattern : function() {

								var current = new Date();
								current.setHours(0,0,0,0);
								
								if(j('#input_year').val() != "" && j('#select_month').val() != "" && j('#select_day').val() != "") {								
									
									var year = parseInt(j('#input_year').val());
									var month = parseInt(j('#select_month').val());
									var day = parseInt(j('#select_day').val());
									
									var entered = new Date(Date.UTC(year + 18, month - 1, day, 0, 0, 0));
									if(entered.getTime() >= current.getTime()) {
										return false
									} else {
										j('#select_day, #select_month, #input_year')
												.removeClass('invalid')
												.addClass('valid');

										return true;
									}
									
								} else {
									return false;
								}
									
							}, message : 'at least 18 years old...' }
						],
						error : function(error) {
							j('#status_birth_date').show();
						},
						success : function() {
							if(j('#select_month').hasClass('valid') && j('#input_year').hasClass('valid')) {
								j('#status_birth_date').hide();
							}
						}
					}, {
						element : j('#select_month'),
						rules : [
							{ method : 'required', message : 'This field is required.' },
							{ method : 'equalTo', pattern : function() {

								var current = new Date();
								current.setHours(0,0,0,0);
								
								if(j('#input_year').val() != "" && j('#select_month').val() != "" && j('#select_day').val() != "") {								
									
									var year = parseInt(j('#input_year').val());
									var month = parseInt(j('#select_month').val());
									var day = parseInt(j('#select_day').val());
									
									var entered = new Date(Date.UTC(year + 18, month - 1, day, 0, 0, 0));
									if(entered.getTime() >= current.getTime()) {
										return false
									} else {
										j('#select_day, #select_month, #input_year')
												.removeClass('invalid')
												.addClass('valid');

										return true;
									}
									
								} else {
									return false;
								}
								
								
									
							}, message : 'at least 18 years old...' }
						],
						error : function(error) {
							j('#status_birth_date').show();
						},
						success : function() {
							if(j('#select_day').hasClass('valid') && j('#input_year').hasClass('valid')) {
								j('#status_birth_date').hide();
							}
						}
					}, {
						element : j('#input_year'),
						rules : [
							{ method : 'required', message: 'This field is required.' },
							{ method : 'digits' },
							{ method : 'minlength', pattern : 4 },
							{ method : 'equalTo', pattern : /^(19|20)[\d]{2,2}$/ , message : 'Year must be between 1900 and 2099.' },
							{ method : 'equalTo', pattern : function() {

								var current = new Date();
								current.setHours(0,0,0,0);
								
								if(j('#input_year').val() != "" && j('#select_month').val() != "" && j('#select_day').val() != "") {								
									
									var year = parseInt(j('#input_year').val());
									var month = parseInt(j('#select_month').val());
									var day = parseInt(j('#select_day').val());
									
									var entered = new Date(Date.UTC(year + 18, month - 1, day, 0, 0, 0));
									if(entered.getTime() >= current.getTime()) {
										return false
									} else {
										j('#select_day, #select_month, #input_year')
												.removeClass('invalid')
												.addClass('valid');

										return true;
									}
									
								} else {
									return false;
								}
									
							}, message : 'at least 18 years old...' }
						],
						error : function(error) {
							j('#status_birth_date').show();
						},
						success : function() {
							if(j('#select_day').hasClass('valid') && j('#select_month').hasClass('valid')) {
								j('#status_birth_date').hide();
							}
						}
					}, {
						element : j('#select_annual_income'),
						status : j('#status_annual_income'),
						rules : [
							{ method : 'required', message : getContent("registerAnnualIncomeRequired") }
						]
					}, {
						element : j('#select_net_worth'),
						status : j('#status_net_worth'),
						rules : [
							{ method : 'required', message : getContent("registerNetWorthRequired") }
						]
					}, {
						element : j('#select_occupation_title'),
						status : j('#status_occupation_title'),
						rules : [
							{ method : 'required', message : getContent("registerOccupationTitleRequired") }
						]
					}, {
						element : j('#select_occupation_industry'),
						status : j('#status_occupation_industry'),
						rules : [
							{ method : 'required', message : getContent("registerOccupationIndustryRequired") }
						]
					}, {
						element : j('#select_education_level'),
						status : j('#status_education_level'),
						rules : [
							{ method : 'required', message : getContent("registerEducationLevelRequired") }
						],
						validate : function() { 
							return validateExcludeQuestions 
						}
					}, {
						element : j('#select_investment_experience'),
						status : j('#status_investment_experience'),
						rules : [
							{ method : 'required', message : getContent("registerInvestmentExperienceRequired") }
						]
					}, {
						element : j('#select_investment_experience_years'),
						status : j('#status_investment_experience_years'),
						rules : [
							{ method : 'required', message : getContent("registerInvestmentExperienceYearsRequired") }
						],
						validate : function() { 
							return validateExperienceQuestions; 
						}
					}, {
						element : j('#select_traded_instruments'),
						status : j('#status_traded_instruments'),
						rules : [
							{ method : 'required', message : getContent("registerTradedInstrumentsRequired") }
						],
						validate : function() { 
							return validateAdditionalQuestions 
						}

					}, {
						element : j('#select_trades_volumes'),
						status : j('#status_trades_volumes'),
						rules : [
							{ method : 'required', message : getContent("registerTradesVolumesRequired") }
						],
						validate : function() { 
							return validateAdditionalQuestions 
						}
					}, {
						element : j('#select_trades_frequencies'),
						status : j('#status_trades_frequencies'),
						rules : [
							{ method : 'required', message : getContent("registerTradesFrequenciesRequired") }
						],
						validate : function() { 
							return validateAdditionalQuestions 
						}
					}, {

						element : j('#select_trades_time_period'),
						status : j('#status_trades_time_period'),
						rules : [
							{ method : 'required', message : getContent("registerTradesTimePeriodRequired") }
						],
						validate : function() { 
							return validateAdditionalQuestions 
						}
					}, {
						element : j('#checkbox_accept_terms'),
						status : j('#status_accept_terms'),
						rules : [
							{ method : 'required', message : getContent("registerAcceptTermsRequired") }
						]
					}, {
						element : j('#checkbox_accept_product_disclosure_statement'),
						status : j('#status_accept_product_disclosure_statement'),
						rules: [
							{ method : 'required', message : getContent("registerAcceptProductDisclosureStatementRequired") }
						],
						validate : function() {
							return validateProductDisclosureStatement;
						}
					}
				],
				submitElement: j('#button_register_submit'),
				messages: null,
				accept: function () {
				
					if(enableSubmit) {
					
						enableSubmit = false;
					
						jsapi.register.activateAccount({
							address: j('#input_address').val(),
							city: j('#input_city').val(),
							state : (j('#register_complete_select_state').val() != "" ? j('#register_complete_select_state').val() : undefined),
							zipCode: j('#input_zip_code').val(),
							birthDate: {
								day: j('#select_day').val(),
								month: j('#select_month').val(),
								year: j('#input_year').val()
							},
							annualIncome : (j('#select_annual_income').val() != "" ? j('#select_annual_income').val() : undefined),
							netWorth : (j('#select_net_worth').val() != "" ? j('#select_net_worth').val() : undefined),
							occupationTitle: (j('#select_occupation_title').val() != "" ? j('#select_occupation_title').val() : undefined),
							occupationIndustry: (j('#select_occupation_industry').val() != "" ? j('#select_occupation_industry').val() : undefined),
							investmentExperience : (j('#select_investment_experience').val() != "" ? j('#select_investment_experience').val() : undefined),
							investmentExperienceYears: (validateExperienceQuestions ? (j('#select_investment_experience_years').val() != "" ? j('#select_investment_experience_years').val() : undefined) : undefined),
							educationLevel : (j('#select_education_level').val() != "" ? j('#select_education_level').val() : undefined),
							tradedInstruments : (j('#select_traded_instruments').val() != "" ? j('#select_traded_instruments').val() : undefined),
							tradesVolumes : (j('#select_trades_volumes').val() != "" ? j('#select_trades_volumes').val() : undefined),
							tradesFrequencies : (j('#select_trades_frequencies').val() != "" ? j('#select_trades_frequencies').val() : undefined),
							tradesTimePeriod : (j('#select_trades_time_period').val() != "" ? j('#select_trades_time_period').val() : undefined),
							acceptTerms: j('#checkbox_accept_terms').is(':checked'),
							regTicketId : regTicketId,
							isContinue : isContinue,
							isProductDisclosureAccepted : validateProductDisclosureStatement,
							progressStep : 3,
							success : function(data) {
							
								// alpha pixel reg
								try {
									var cookieVal = (handle.register.gatewayId || "") +"_"+ (handle.register.gatewaySubId || "") +"_"+ data.uniqueVisitorId;
                                    j.cookie("REGPIXEL", cookieVal);
								} catch (e) {}

								// media mind
				                try {
				                    var ebRand = Math.random()+'';
				                    ebRand = ebRand * 1000000;
				                    j.ajax({ url: 'https://bs.serving-sys.com/BurstingPipe/ActivityServer.bs?cn=as&amp;ActivityID=261433&amp;rnd=' + ebRand,dataType: 'script'});
				                } catch(e){}

								// google tag manager -> event -> activateAccount
								dataLayer.push({'event': 'activateAccount'});
								
								// turn pixel
								var img = new Image();
								// wrap our new image in jQuery, then:
								j(img).load(function () {
								  j('body')
									.append(this);
								})
								.attr("src", "https://r.turn.com/r/beacon?b2=8aKQtViQrhkHM0INvfTS7tEib-PAUNouEm5ptYGvB8bDlmMA_kAP0Y8iBKhZIRKr_2OXj1xXDLuuspl2IW7WuQ&cid=");
								
								
								jsapi.accounts.user.getUserInfo({
									success : function(data) {
										
										isEnableFractionalPips = data.isEnableFractionalPips;
										accountBaseCurrency = data.accountBaseCurrency;
										withdrawalAvailable = data.withdrawalAvailable;
										allowWithdrawal = data.allowWithdrawal;
										allowDeposit = data.allowDeposit;
										isActive = data.isActive;
										isDemo = data.isDemo;
										userStatus = data.userStatus;
										needToPassTest = data.needToPassTest;
										isSuitabilityTestPassed = data.isSuitabilityTestPassed;
										regionId = data.regionId;
										regTicketId = data.regTicketId;
										
										// getTrading
										jsapi.trading.getTrading({
											success : function(data) {
												
												j('#label_register_username').text(jsapi.accounts.user.userInfo.userName);
												screenManager.show(screens.registerComplete);
												
												try {
													if (j.cookie("REGPIXEL") != null) {
														handle.register.sendRegisterPixel();
													}
												}catch(e){}
												
												
											},
											error : function(error) {
												
												if(error != errors.applicationNotLoggedIn) {
													var modal = new lightFace({
														title : "",
														message : getContent(error),
														actions : [
														   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
														],
														overlayAll : true
													});
												}
												
											}
										});
									},
									error : function(error) {
										
										if(error != errors.applicationNotLoggedIn) {
											var modal = new lightFace({
												title : "",
												message : getContent(error),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
										}
										
									}
								});
								
							},
							error : function(error) {
							
								enableSubmit = true;
								 
								if(error != errors.applicationNotLoggedIn) {
									var modal = new lightFace({
										title : "",
										message : getContent(error),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
								}

                                  if(error == "407") {//CityIsInvalid
                                     var modal = new lightFace({
										title : "",
										message : getContent("registerCityEqualTo"),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
                                    j('#input_city').val('');
                                    
                                }

                                if(error == "406") {//AddressIsInvalid
                                     var modal = new lightFace({
										title : "",
										message : getContent("registerAddressEqualTo"),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									}); 
                                    j('#input_address').val('');
                                }
							}
						});
					
					}
					
				},
				error : function() {
					
				}
			});
			
			// get register settings
			loader.show();
			jsapi.register.getRegisterSettings({
				regTicketId : regTicketId,
				success: function (data) {
					
					loader.hide();
					
					if(data.isRestricted) {
						isContinue = true;
						//updateRestricted = false;
					}
					
					if(data.countryId) {
						if(!data.state)
                        {
						    var statesByCounty = getStates(data.countryId, false);
						    if(statesByCounty) {
							
							    // show states
							    j('#row_register_complete_states').show();
							    validateState = true;
							
							    // fill states
							    j('#register_complete_select_state').empty();
							    var e = j('#register_complete_select_state')[0].options;
							    for(var i = 0; i < statesByCounty.length; i++) {
								    var k = new Option(statesByCounty[i].state, statesByCounty[i].stateId);
								    try {
									    e.add(k)
								    } catch (ex) {
									    e.add(k, null)
								    }
							    }
						    }
                        }
						
						for(var x = 0; x < auCountries.length; x++) {
							if(auCountries[x] == data.countryId) {
								validateProductDisclosureStatement = true;
								j('#row_product_disclosure_statement').show();
								break;
							}
						}
						
						if(enableAdditionalQuestions(data.countryId)) {
							
							j('.exclude-questions').show();
							validateExcludeQuestions = true;
							
							
							// .unbind('change focusout blur').bind('change focusout blur', function() {
							j('#select_investment_experience')
								.unbind('change')
								.change(function() {
								
								var a = j(this).find('option:selected');
								if(a.val() != "" && a.val() != "4") {

									// Show additional question
									j('.additional-questions').show();
									validateAdditionalQuestions = true;
									
									// Show experience questions
									j('.experience-question').show();
									validateExperienceQuestions = true;
									
									
								} else if(a.val() != "" && a.val() == "4") {
									
									if(!isContinue) {
										if(isComplianceLogicEnabled) {		
											
											var modal = new lightFace({
												title : "",
												message : getContent("riskWarningRegister"),
												actions : [
												   { 
														label : getContent("stopRegister"), 
														fire : function() { 
															stopRegistration(function() { 
																modal.close();
																screenManager.show(screens.quotes);
															}) 
														}, color: "blue" },
												   { label : getContent("continueRegister"), fire: function() { continueRegistration(function() { modal.close(); }) }, color: "green" }
												],
												overlayAll : true
											});
											
										}
									}
									
									j('.additional-questions').hide();
									validateAdditionalQuestions = false;
									
									// Hide experiance questions
									j('.experience-question').hide();
									validateExperienceQuestions = false;
									
									
								} else {
									
									j('.additional-questions').hide();
									validateAdditionalQuestions = false;
									
									// Hide experiance questions
									j('.experience-question').hide();
									validateExperienceQuestions = false;
									
									
								}
							});
							
						} else {
							
							j('#select_investment_experience')
								.unbind('change')
								.change(function() {
								
								var a = j(this).find('option:selected');
								var experience = parseInt(a.val());
	                            
								if(!isNaN(experience) && experience != 4) {
									// show experiance questions
									j('.experience-question').show();
									validateExperienceQuestions = true;
								} else {
									// hide experiance questions
									j('.experience-question').hide();
									validateExperienceQuestions = false;
								}
								
								// suitability
								if(!data.isSuitabilityTestPassed) {
									if(!isNaN(experience)) {
										if(checkSuitabilityAnswer(expAnswersForSuitability, experience)) {
											j('#row_suitabilitytest').show();
										} else {
											j('#row_suitabilitytest').hide();
											var __years = parseInt(j('#select_investment_experience_years').val());
											if(!isNaN(__years)) {
												if(checkSuitabilityAnswer(yearsAnswersForSuitability, __years)) {
													j('#row_suitabilitytest').show();
												}
											}
										}
									} else {
										j('#row_suitabilitytest').hide();
									}
								}
								
							});
							
						}
						
						// show risk warning
						j('#select_investment_experience_years, #select_trades_volumes')
							.unbind('change')
							.change(function() {
							
							var years = parseInt(j('#select_investment_experience_years').val());
							var volume = parseInt(j('#select_trades_volumes').val());
							
							if(years == 1 && (volume == 2 || volume == 1)) {
								if(!isContinue) {
									if(isComplianceLogicEnabled) {
										
										var modal = new lightFace({
											title : "",
											message : getContent("riskWarningRegister"),
											actions : [
											   { 
													label : getContent("stopRegister"), 
													fire : function() { 
														stopRegistration(function() { 
															modal.close();
															screenManager.show(screens.quotes);
														})  
													}, color: "blue" },
											   { label : getContent("continueRegister"), fire: function() { continueRegistration(function() { modal.close(); }) }, color: "green" }
											],
											overlayAll : true
										});
										
									}
								}
							}
							
							// years -> 1-2 years
							if(years == 2 && volume == 1) {
								if(!isContinue) {
									if(isComplianceLogicEnabled) {
										
										var modal = new lightFace({
											title : "",
											message : getContent("riskWarningRegister"),
											actions : [
											   { 
													label : getContent("stopRegister"), 
													fire : function() { 
														stopRegistration(function() { 
															modal.close();
															screenManager.show(screens.quotes);
														})  
													}, color: "blue" },
											   { label : getContent("continueRegister"), fire: function() { continueRegistration(function() { modal.close(); }) }, color: "green" }
											],
											overlayAll : true
										});
										
									}
								}
							}
							
							// suitability
							if(!data.isSuitabilityTestPassed) {
								if(!isNaN(years)) {
									if(checkSuitabilityAnswer(yearsAnswersForSuitability, years)) {
										j('#row_suitabilitytest').show();
									} else {
										j('#row_suitabilitytest').hide();
									}
								} else {
									j('#row_suitabilitytest').hide();
								}
							}
							
						});
					
					}
					
					
					silentScroll(true);
					

				},
				error : function() {
					loader.hide();
				}
			});
			
			
			
			var imgUrl = "";
			
			// yieldManager
			imgUrl = "https://ad.yieldmanager.com/pixel?id=1375250&t=2"; 
			var img1 = new Image();          
			img1.src = imgUrl;
			
			
				
		},
		destroy : function() {
			
		}
	},
	quotes : {
		rows : null,
		prevRates: null,
		freshRates: null,
		init : function() {
			
			// drop last data
			this.rows = null;
			this.prevRates = null;
			this.freshRates = null;
			
			if(isAuthenticated) {
				
				// get rates
				var getTradableRates = function() {
					
					jsapi.trading.rates.getRates({
						success : function(data) {
							handle.quotes.update(data);
						},
						error : function(error) {
							// abandon rates
							jsapi.trading.rates.abandon();
							
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : getContent(error),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
					}, 3000);
					
				};
				
				if(instrumentType == 1) {

					// link customize pairs
					j('#link_customize_pairs')
						.show()
						.unbind('click')
						.click(function() {
							screenManager.show(screens.customizePairs);
						});
						
				} else {

					j('#link_customize_pairs')
						.hide()
						.unbind('click');
				
				}
				
				// bind quotes
				this.bind(jsapi.trading.tradingInfo.currencyPairs);
				
				getTradableRates();
				
			} else {
				
				// get rates
				// before login
				var getMarketInfoRates = function() {
					
					jsapi.marketInfo.rates.getRates({
						success : function(data) {
							handle.quotes.update(data);
						},
						error : function(error) {
							// abandon rates
							jsapi.marketInfo.rates.abandon();
							
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : getContent(error),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
					}, 3000);
					
				};
				
				j('#link_customize_pairs')
					.hide()
					.unbind('click');
				
				
				// bind quotes
				this.bind(jsapi.marketInfo.settings.marketInfo.currencyPairs);
				
				getMarketInfoRates();
			}
			
			
			// turn pixel
			var img = new Image();
			// wrap our new image in jQuery, then:
			j(img).load(function () {
			  j('body')
				.append(this);
			})
			.attr("src", "https://r.turn.com/r/beacon?b2=HasWC_gc3DGpB5em6LZMaj-SUxmvLTT0U5zlbZO-P-DDlmMA_kAP0Y8iBKhZIRKrCY04o0sCFB_1AynpOLIpVA&cid=");
			
			
		},
		destroy : function() {
			
			// abandon rates in destroy
			jsapi.marketInfo.rates.abandon();
			jsapi.trading.rates.abandon();
			
			// clear html
			j('#list_quotes').empty();
			
		},
		bind : function(currencyPairs) {
			
			var y = 0;
			for (var key in currencyPairs) {
				var currencyPair = currencyPairs[key];
				
				// && currencyPair.instrumentType == instrumentType
				if (currencyPair.isPersonal && currencyPair.instrumentType == instrumentType) { 
					
					var keyValue = currencyPair.baseCurrency + currencyPair.nonBaseCurrency;
					var displayCurrencyPair = (instrumentType == 1 ? (currencyPair.baseCurrency + " / " + currencyPair.nonBaseCurrency) : getCurrencySettings(currencyPair.baseCurrency).description);
					
					var row = null;
					row = j("<li " + (y == 0 ? "class=\"first-item\"" : "")  + " >" +
							"<div class=\"first-item\"><span class=\"cur\">" + displayCurrencyPair  + "</span></div>" +
							"<div>" +
								"<a class=\"label-bid\" keyvalue=\"" + currencyPair.baseCurrency + currencyPair.nonBaseCurrency + "\"><span class=\"bid\">&nbsp;</span><span class=\"label-high\">" + getContent("high") + ":<span class=\"high\"></span></span></a>" +
								"<a class=\"last-item label-ask\" keyvalue=\"" + currencyPair.nonBaseCurrency + currencyPair.baseCurrency + "\"><span class=\"ask\">&nbsp;</span><span class=\"label-low\">" + getContent("low") + ":<span class=\"low\"></span></span></a>" +
							"</div>" +
						"</li>").appendTo('#list_quotes');
					
					y++;
					
					// events
					row.find('.label-bid, .label-ask')
						.click(function() {
							
							lastPair = {
								pair : j(this).attr('keyvalue')
							}
							
							if(isAuthenticated) {
								// If user logged in -> go to openDeal
								screenManager.show(screens.openDeal);
							} else {
								// If user not logged in -> go to charts screen
								screenManager.show(screens.charts);
							}
					});
					
					// ?
					var rowElements = row.find('.bid, .ask, .high, .low');
					
					if(this.rows == null) {
						this.rows = {};
					}
					
					this.rows[keyValue] = rowElements;
				}
			}
		},
		update: function(data) {
			this.prevRates = this.freshRates;
			this.freshRates = data.rates;
			
			if(this.rows) {
				for(var currencyPair in this.freshRates) {
					var row = this.rows[currencyPair];
					if (row && row != undefined) {
						var freshRate = this.freshRates[currencyPair];
						var prevRate = this.prevRates == null ? {} : (this.prevRates[currencyPair] != undefined ? this.prevRates[currencyPair] : {});
						
						// ?
						j(row[0]).html(fractionalFormat(freshRate.bid, currencyPair));
						j(row[2]).html(fractionalFormat(freshRate.ask, currencyPair));
						j(row[1]).text(freshRate.high);
						j(row[3]).text(freshRate.low);
						
						
						var elements = j([row[0], row[2]]);
							elements
								.removeClass((prevRate.bid == undefined || prevRate.bid == freshRate.bid) ? 'up down' : (elements.hasClass('down') && prevRate.bid < freshRate.bid) ? 'down' : ((elements.hasClass('up') && prevRate.bid > freshRate.bid) ? 'up' : '')).addClass((prevRate.bid < freshRate.bid) ? 'up' : ((prevRate.bid > freshRate.bid) ? 'down' : ''));
						
						
					}
				}	
			}
		}
	},
	myPositions : {
		rows : {},
		prevDeals : null,
		freshDeals: null,
		prevBalance : null,
		prevTotalMargin : null,
		prevTotalProfitLoss : null,
		init : function() {
			
			var getTradableRates = function() {
				// get rates after trading info
				jsapi.trading.rates.getRates({
					success : function(data) {
						handle.myPositions.update(data);
					},
					error : function(error) {
						// abandon rates
						jsapi.trading.rates.abandon();
						
						if(error != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : getContent(error),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
						
					}
				}, 3000);
			};
			
			if(jsapi.trading.tradingInfo.deals != undefined) {
				for(var dealId in jsapi.trading.tradingInfo.deals) {
					this.add(dealId, jsapi.trading.tradingInfo.deals[dealId], false);
				}
				
				getTradableRates();
				
				// day trading and forwards
				j('#list_deals').accordion( "destroy" );
				j('#list_deals').accordion({
					collapsible: true,
					active: false, 
					header : 'a.ui-tab',
					autoheight : false,
					animated: false,
					alwaysOpen: false,
					clearStyle: true,
					change: function(event, ui) {
						
						if(ui.newHeader.offset() != null) {
							if(isIDevice) {
								//myScroll.scrollToPage(0, ui.newHeader.offset().top, 200);
							} else {
								window.scrollTo(0,ui.newHeader.offset().top);
							}
						}
						
					}
				});
				
				// limit orders
				j('#list_deals_limit_orders').accordion( "destroy" );
				j('#list_deals_limit_orders').accordion({
					collapsible: true,
					active: false, 
					header : 'a.ui-tab',
					autoheight : false,
					animated: false,
					alwaysOpen: false,
					clearStyle: true,
					change: function(event, ui) {
						
						if(ui.newHeader.offset() != null) {
							if(isIDevice) {
								//myScroll.scrollToPage(0, ui.newHeader.offset().top, 200);
							} else {
								window.scrollTo(0,ui.newHeader.offset().top);
							}
						}
						
					}
				});
				
				
			}
			
			
		},
		destroy : function() {
			
			// abandon rates
			jsapi.trading.rates.abandon();
			
			this.rows = {};
			this.prevDeals = null;
			this.freshDeals = null;
			this.prevBalance = null;
			this.prevTotalMargin = null;
			this.prevTotalProfitLoss = null;
			
			// clear html
			j('#list_deals').empty();
			j('#list_deals_limit_orders').empty();
			j('#label_total_balance').empty();
			j('#label_total_profit_loss').empty();
			
		},
		add : function(dealId, deal, refresh) {
			
			// day trading or forwards
			if(deal.productId == 2 || deal.productId == 3) {
				
				var row = j("<a class=\"ui-tab\">" +
							"<span class=\"first-item\">" +
								"<span class=\"ui-label-small\">" + (deal.buyIsBase ? getContent("buy") : getContent("sell")) + "</span>" +
								"<span class=\"deal-pair\">" + (deal.buyIsBase ? (deal.buy + " / " + deal.sell) : (deal.sell + " / " + deal.buy)) + "</span>" +
							"</span>" +
							"<span class=\"last-item\">" +
								"<span class=\"label-deal-profit-loss money\"></span>" +
							"</span>" +
							"<i class=\"expand\"></i>" +
						"</a>" +
						"<div class=\"placeholder\">" +
							"<ul class=\"lf\">" +
								"<li>" +
									"<div class=\"first-item\"><span>" + products[deal.productId] + ":</span></div>" +
									"<div class=\"last-item\">#" + dealId + "</div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("buy") + ":</span></div>" +
									"<div class=\"last-item money\">" + deal.buyAmount + "</div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("sell") + ":</span></div>" +
									"<div class=\"last-item money\">" + deal.sellAmount + "</div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("takeProfit") + ":</span></div>" +
									"<div class=\"last-item\"><span class=\"label-deal-take-profit\"></span></div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("openRate") + ":</span></div>" +
									"<div class=\"last-item\">" + fractionalFormat(deal.openRate, (deal.symbol)) + "</div>" +
								"</li>" +
								"<li style=\"" + (deal.productId == 3 ? "display: block" : "display: none") + "\">" +
									"<div class=\"first-item\"><span>" + getContent("rollingUntil") + ":</span></div>" +
									"<div class=\"last-item\"><span>" + deal.expiryDate + "</span></div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("currentRate") + ":</span></div>" +
									"<div class=\"last-item\"><span class=\"label-deal-current-rate\"></span></div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("stopLoss") + ":</span></div>" +
									"<div class=\"last-item\"><span class=\"label-deal-stop-loss\"></span></div>" +
								"</li>" +
								"<li style=\"" + (deal.productId == 2 ? "display: block" : "display: none") + "\">" +
									"<div class=\"first-item\"><span>" + getContent("forwardDate") + "</span></div>" +
									"<div class=\"last-item\"><span>" + deal.expiryDate + "</span></div>" +
								"</li>" +
							"</ul>" +
							"<ul class=\"lf\" style=\"padding-top: 0px;\">" +
								"<li>" +
									"<div class=\"first-item\"><a class=\"button-blue ui-button-short\" onclick=\"closeDeal(" + dealId + ")\" style=\"margin: 0 10px;\">" + getContent("close") + "</a></div>" +
									"<div class=\"last-item\"><a class=\"button-green ui-button-short\" onclick=\"modifyDeal(" + dealId + ")\">" + getContent("modify") + "</a></div>" +
								"</li>" +
							"</ul>" +
						"</div>").appendTo('#list_deals');
				
				this.rows[dealId] = {
					symbol : deal.symbol,
					row : row,
					instrumentType : deal.instrumentType
				};
				
				if(refresh) {
					
					// day trading and forwards
					j('#list_deals').accordion( "destroy" );
					j('#list_deals').accordion({
						collapsible: true,
						active: false, 
						header : 'a.ui-tab',
						autoheight : false,
						animated: false,
						alwaysOpen: false,
						clearStyle: true,
						change: function(event, ui) {
							
						}
					});
					
					silentScroll(true);
				}
				
			}
			
			// limit orders
			if(deal.productId == 4) {
				var row = j("<a class=\"ui-tab limit-order\">" +
							"<span class=\"first-item\">" +
								"<span class=\"ui-label-small\">" + (deal.buyIsBase ? getContent("buy") : getContent("sell")) + "</span>" +
								"<span class=\"deal-pair\">" + (deal.buyIsBase ? deal.buyAmount : deal.sellAmount) + " / " + (deal.buyIsBase ? (deal.sell) : (deal.buy)) + "</span>" +
							"</span>" +
							"<span class=\"last-item\">" +
								"<span class=\"label-deal-current-rate money\"></span>" +
							"</span>" +
							"<i class=\"expand\"></i>" +
						"</a>" +
						"<div class=\"placeholder\">" +
							"<ul class=\"lf\">" +
								"<li>" +
									"<div class=\"first-item\"><span>" + products[deal.productId] + ":</span></div>" +
									"<div class=\"last-item\">#" + dealId + "</div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("buy") + ":</span></div>" +
									"<div class=\"last-item money\">" + deal.buyAmount + "</div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("sell") + ":</span></div>" +
									"<div class=\"last-item money\">" + deal.sellAmount + "</div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("takeProfit") + ":</span></div>" +
									"<div class=\"last-item\"><span class=\"label-deal-take-profit\"></span></div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("openRate") + ":</span></div>" +
									"<div class=\"last-item\">" + fractionalFormat(deal.openRate, (deal.symbol)) + "</div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("currentRate") + ":</span></div>" +
									"<div class=\"last-item\"><span class=\"label-deal-current-rate\"></span></div>" +
								"</li>" +
								"<li>" +
									"<div class=\"first-item\"><span>" + getContent("stopLoss") + ":</span></div>" +
									"<div class=\"last-item\"><span class=\"label-deal-stop-loss\"></span></div>" +
								"</li>" +
							"</ul>" +
							"<ul class=\"lf\" style=\"padding-top: 0px;\">" +
								"<li>" +
									"<div class=\"first-item\"><a class=\"button-blue ui-button-short\" onclick=\"closeDeal(" + dealId + ")\" style=\"margin: 0 10px;\">" + getContent("close") + "</a></div>" +
									"<div class=\"last-item\"><a class=\"button-green ui-button-short\" onclick=\"modifyDeal(" + dealId + ")\">" + getContent("modify") + "</a></div>" +
								"</li>" +
							"</ul>" +
						"</div>").appendTo('#list_deals_limit_orders');
				
				this.rows[dealId] = {
					symbol : deal.symbol,
					row : row,
					instrumentType : deal.instrumentType
				};
				
				if(refresh) {
					
					// limit orders
					j('#list_deals_limit_orders').accordion( "destroy" );
					j('#list_deals_limit_orders').accordion({
						collapsible: true,
						active: false, 
						header : 'a.ui-tab',
						autoheight : false,
						animated: false,
						alwaysOpen: false,
						clearStyle: true,
						change: function(event, ui) {
						
						}
					});
					
					silentScroll(true);
				}
				
			}
		},
		remove : function(dealId) {
			
			jsapi.trading.removeDeal(dealId);
			
			if(this.rows[dealId] != undefined) {
				this.rows[dealId].row.remove();
				delete this.rows[dealId];
				
				
				silentScroll(true);
				// TODO: destroy accordion + reinit accordion again
				
			}
			
		},
		update : function(data) {
			
			this.prevDeals = this.freshDeals;
			this.freshDeals = data.deals;
			for(var dealId in this.freshDeals) {
				
				// add deal if isAdded
				if(this.freshDeals[dealId].isAdded) {
					if(this.rows[dealId] == undefined) {
						this.add(dealId, this.freshDeals[dealId], true);
						jsapi.trading.addDeal(dealId, this.freshDeals[dealId]);
						
					}
				}
				
				// remove deal if isDeleted
				if(this.freshDeals[dealId].isDeleted) {
					this.remove(dealId);
				}
				
				// update deal details --> current rate, stop-loss rate, take-profit, profit-loss
				if(this.rows[dealId] != undefined) {
					
					// profit loss
					if(this.freshDeals[dealId].pl != undefined) {
						this.rows[dealId].row
							.find('.label-deal-profit-loss')
							.removeClass((parseFloat(this.freshDeals[dealId].pl) < 0) ? "up" : "down")
							.addClass((parseFloat(this.freshDeals[dealId].pl) < 0) ? "down" : "up")
							.html(this.freshDeals[dealId].pl + " " + accountBaseCurrency);
					}
					
					// take profit
					if(this.freshDeals[dealId].tp != undefined) {
						//if(this.prevDeals[dealId].tp != this.freshDeals[dealId].tp) {
							this.rows[dealId].row
								.find('.label-deal-take-profit')
								.html(fractionalFormat(this.freshDeals[dealId].tp, (this.rows[dealId].symbol)));
						//}
					} else {
						if(this.prevDeals != null) {
							if(this.prevDeals[dealId] != undefined) {
								if(this.prevDeals[dealId].tp != undefined) {
									this.rows[dealId].row
										.find('.label-deal-take-profit')
										.empty();
								}
							}
						}
					}
					
					// stop loss
					if(this.freshDeals[dealId].sl != undefined) {
						this.rows[dealId].row
							.find('.label-deal-stop-loss')
							.html(fractionalFormat(this.freshDeals[dealId].sl, (this.rows[dealId].symbol)));
					}
					
					// current rate
					if(this.freshDeals[dealId].cr) {	
						this.rows[dealId].row
							.find('.label-deal-current-rate')
							.html(fractionalFormat(this.freshDeals[dealId].cr, (this.rows[dealId].symbol)));
						
						if(this.prevDeals != null) {
							if(this.prevDeals[dealId] != undefined) {
								this.rows[dealId].row
									.find('.label-deal-current-rate')
									.removeClass((this.freshDeals[dealId].cr != this.prevDeals[dealId].cr) ? ( (this.freshDeals[dealId].cr > this.prevDeals[dealId].cr) ? "down" : ((this.freshDeals[dealId].cr < this.prevDeals[dealId].cr) ? "up" : "") ) : "down up")
									.addClass((this.freshDeals[dealId].cr != this.prevDeals[dealId].cr) ? ( (this.freshDeals[dealId].cr > this.prevDeals[dealId].cr) ? "up" : ((this.freshDeals[dealId].cr < this.prevDeals[dealId].cr) ? "down" : "") ) : "");
							}
						}
					}
					
				}
			}
			
			// update total balance
			var totalBalance = j('#label_total_balance');
			if (this.prevBalance != null 
					&& this.prevBalance != data.balance) {
				totalBalance.text(data.balance + " " + accountBaseCurrency);
            } else if (this.prevBalance == null) { 
            	totalBalance.text(data.balance + " " + accountBaseCurrency);
            }
            this.prevBalance = data.balance;
			
            // update total profit loss
            var totalProfitLoss = j('#label_total_profit_loss');
			if(data.totalProfitLoss != undefined) {
				
				if(this.prevTotalProfitLoss != null 
						&& this.prevTotalProfitLoss != data.totalProfitLoss) {
					
					totalProfitLoss
						.text(data.totalProfitLoss + " " + accountBaseCurrency)
						.removeClass("down up")
						.addClass(((Number(data.totalProfitLoss.replace(/,/g,"")) != 0) ? ((Number(data.totalProfitLoss.replace(/,/g,"")) < 0) ? "down" : "up") : ""));
					
				} else if (this.prevTotalProfitLoss == null) { 
					
					totalProfitLoss
						.text(data.totalProfitLoss + " " + accountBaseCurrency)
						.removeClass("down up")
						.addClass(((Number(data.totalProfitLoss.replace(/,/g,"")) != 0) ? ((Number(data.totalProfitLoss.replace(/,/g,"")) < 0) ? "down" : "up") : ""));
				}
				this.prevTotalProfitLoss = data.totalProfitLoss;
				
			} else {
				
				totalProfitLoss
					.removeClass("up down")
					.text("0.00 " + accountBaseCurrency);
				
			}
			
		}
	},
	charts : {
		loaded : false,
		lastInsideViewer : null,
		lastActiveTab : null,
		types : {
			chart : 1,
			insideviewer : 2
		},
		init : function() {
		
			if(instrumentType == 1) {
				j('.container-charts-instruments').hide();
				j('.container-charts-currencies').show();
			}
			
			if(instrumentType == 2 
			|| instrumentType == 3) {
				j('.container-charts-currencies').hide();
				j('.container-charts-instruments').show();
			}
		
			
			j('ul#list_buttons_chart li a').removeClass('active');
			j('ul#list_buttons_chart li a').each(function(i, el){
				if(i == 0) {
					j(el).addClass('active');
					handle.charts.lastActiveTab = j(el);
				}
				j(el)
					.unbind('click')
					.click(function() {
					if(handle.charts.lastActiveTab != null && handle.charts.lastActiveTab != j(this)) {
						handle.charts.lastActiveTab.removeClass('active');
					}
					handle.charts.lastActiveTab = j(this); 
					
					j(this).addClass('active');
					
					handle.charts.bind(handle.charts.types[j(this).attr('name')]);
					
				});
			});
			
			this.bind(this.types.chart);
			
		},
		bind : function(chartId) {
		
			var pair = null;
			if(lastPair != null) {
				pair = lastPair.pair;
			} else {
				pair = (isAuthenticated ? jsapi.trading.tradingInfo.instrumentTypes[instrumentType].pair : jsapi.marketInfo.settings.marketInfo.instrumentTypes[instrumentType].pair);
			}
			
			var sell = pair.substr(0, 3);
            var buy = pair.substr(3, 3);
			
			
			var getPlotBackground = function(canvas){
				return { 
            		bgWidth: canvas[0].width, 
            		bgHeight: canvas[0].height, 
            		bgColor: "#ffffff", 
            		barWidth: 225, 
            		barHeight: 32, 
            		barCornerRadius: 4 
	            };
			};
			
			var plotInsideViewer = function(data) {
				
				var instrumentPair = "";
				if(data.symbol != undefined) {
					instrumentPair = data.symbol.substring(0, 3) + "/" + data.symbol.substring(3, 6);
				}
				
				
				// popularity
				var popularityContext2D = j('#canvas_inside_viewer_popularity')[0].getContext("2d");
				var plotBackground = getPlotBackground(j('#canvas_inside_viewer_popularity'));
				
				
				var popularity = new insideViewerPlot(popularityContext2D, plotBackground);
				popularity.drawBackground();
				popularity.plotSimpleBar({
					x: 2, 
					y: 2,
					gap: 2, 
					leftRatio: 50, 
					rightRatio: 50,
					leftValue : data.currencyPairRanking,
		            rightValue : data.currencyPairDealsPercent.toString() + "%", // + "%"
		            leftLabel : getContent("ranking"),
		            rightLabel : getContent("percentOfDeals")
				});
				
				// details
				j('#label_inside_viewer_popularity').html(String.format(getContent("popularityDetails")[1] + "<br/>" + getContent("popularityDetails")[2], instrumentPair, data.currencyPairRanking, data.currencyPairDealsPercent.toString(), instrumentPair));
				
				
				
				// direction
				var directionContext2D = j('#canvas_inside_viewer_direction')[0].getContext("2d");			
				plotBackground = getPlotBackground(j('#canvas_inside_viewer_direction'));                        
				
	            var direction = new insideViewerPlot(directionContext2D, plotBackground);
	            direction.drawBackground();
	            direction.plotSimpleBar({
	            	x: 2, 
        			y: 2, 
        			gap: 2,
        			leftRatio: data.buyPercent, // 99 
        			rightRatio: 100 - data.buyPercent, // 1
	            	leftValue : data.buyPercent.toString() + "%", // 99%
	            	rightValue : (100 - data.buyPercent).toString() + "%", // 1%
	            	leftLabel : getContent("buy") + " " + (instrumentType == 1 ? j('#select_charts_buy').val() : getPair(j('#select_charts_instrument').val())),
	            	rightLabel : getContent("buy") + " " + (instrumentType == 1 ? j('#select_charts_sell').val() : j('#select_charts_instrument').val())
	            });
				
				// details
				j('#label_inside_viewer_direction').html(String.format(getContent("directionDetails")[1], data.buyPercent, (instrumentType == 1 ? j('#select_charts_buy').val() : getPair(j('#select_charts_instrument').val()))) + "<br/>" + String.format(getContent("directionDetails")[2], (100 - data.buyPercent), (instrumentType == 1 ? j('#select_charts_sell').val() : j('#select_charts_instrument').val())));
				
				
				
	            // structure
	            var structureContext2D = j('#canvas_inside_viewer_structure')[0].getContext("2d");			
				plotBackground = getPlotBackground(j('#canvas_inside_viewer_structure')); 
				
				var structure = new insideViewerPlot(structureContext2D, plotBackground);
				structure.drawBackground();
        		structure.plotStructure({
        			x: 2, 
                	y: 2, 
                	direction: data.direction, 
                	slr: data.averageStopLossRate, 
                	tpr: data.averageTakeProfitRate, 
                	avgRate: data.averageRate, 
                	currentRate: data.midRate, 
                	slrLabel: getContent("stopLoss"), 
                	tprLabel: getContent("profit"), 
                	avgLabel: getContent("averageRate") 
        		});
				
				// details
				j('#label_inside_viewer_structure').html(String.format(getContent("structureDetails")[1] + "<br/>" + getContent("structureDetails")[2] + "<br/>" + getContent("structureDetails")[3], instrumentPair, data.averageTakeProfitRate, instrumentPair, data.averageRate, instrumentPair, data.averageStopLossRate));
				
				
			};
			
			var getInsideViewer = function(a, b) {
			
				jsapi.marketInfo.insideViewer.getInsideViewerData({	
					buyCurrency: a, 
					sellCurrency: b,
					isAuthenticated : isAuthenticated,
					success: function(data){
					
						lastPair = {
							pair : b + a
						};
						restrictedPair = {
							pair : b + a
						};
					
						
						sell = b;
						buy = a;
						
						plotInsideViewer(data);
						
					},
					error: function(error) {
					
						if(restrictedPair != null) {
							sell = restrictedPair.pair.substr(0, 3);
							buy = restrictedPair.pair.substr(3, 3);
						
							if(instrumentType == 1) {							
								setBuy(buy);
								setSell(sell);
							}
							
							if(instrumentType == 2 
							|| instrumentType == 3) {
								setInstruments(getRelative(buy, sell));
							}
							
						} else {
						
							pair = (isAuthenticated ? jsapi.trading.tradingInfo.instrumentTypes[instrumentType].pair : jsapi.marketInfo.settings.marketInfo.instrumentTypes[instrumentType].pair);
							sell = pair.substr(0, 3);
							buy = pair.substr(3, 3);
							
							if(instrumentType == 1) {
								setBuy(buy);
								setSell(sell);
							}
							
							if(instrumentType == 2 
							|| instrumentType == 3) {
								setInstruments(getRelative(buy, sell));
							}
							
						}
						
						getInsideViewer(buy, sell);
						
						
						if(error != errors.applicationNotLoggedIn) {
							
							var modal = new lightFace({
								title : "",
								message : getContent(error),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
						
					}
				});
			};
			
			var getChart = function(a, b, interval) {
			
				var width, height;
				if(jQuery('html').hasClass('portrait')) {
					width = 300; 
					height = 210;
				} else {
					width = 468;
					height = 210; //368;
				}
				
				jsapi.marketInfo.chart.getChart({
					instrument : (a + b),
					interval : interval,
					width : width,
					height : height,
					success : function(data) {
					
						lastPair = {
							pair : b + a
						};
						
						sell = b;
						buy = a;
						
						j('#image_chart')
							.css({ background : "url(" + data.url + ") no-repeat", width : width, height : height });
						
					},
					error : function(error) {
					
						if(instrumentType == 1) {
						
							setBuy(buy);
							setSell(sell);
							
						}
						
						if(instrumentType == 2 
						|| instrumentType == 3) {
							setInstruments(getRelative(buy, sell));
						}
						
						if(error == "GeneralError") {
							
							var modal = new lightFace({
								title : "",
								message : getContent("chartNotAvailableForCurrencyPair"),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
							
						} else {
						
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : getContent(error),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
						
					}
				});
				
			};
			
			var setChart = function(a, b) {
			
				if(chartId == 1) {
					
					j('#controls_chart').show();
					j('#placeholder_chart').show();
					j('#placeholder_inside_viewer').hide();
					
					getChart(a, b, j('#select_chart_interval').val());
					
				} else {
					
					// inside viewer
					j('#controls_chart').hide();
					j('#placeholder_chart').hide();
					j('#placeholder_inside_viewer').show();
					
					if(handle.charts.lastInsideViewer != null) {
						handle.charts.lastInsideViewer.removeClass('active');
						j('#section_' + handle.charts.lastInsideViewer.attr('name')).hide();
					}
					
					j('ul#list_inside_viewer_buttons li a').each(function(i, el) {
						if(i == 0) {
							j(el).addClass('active');
							j('#section_' + j(el).attr('name')).show();
							handle.charts.lastInsideViewer = j(el);
						}
						
						j(el).unbind('click')
							.click(function() {
								
								if(handle.charts.lastInsideViewer != null) {
									handle.charts.lastInsideViewer.removeClass('active');
									j('#section_' + handle.charts.lastInsideViewer.attr('name')).hide();
								}
								
								var l = j(this);
								l.addClass('active');
								j('#section_' + l.attr('name')).show();
								
								handle.charts.lastInsideViewer = l;
							});
					});
					
					getInsideViewer(a, b);
					
				}
			};
			
			// buy
			var setBuy = function(a, freshContracts) {
				
				var currencies = [];
					currencies = getCurrencies();
				
				var lastSelected = null;
				
				j('#select_charts_buy')
	            	.empty()
	        		.unbind('change')	
	        		.change(function() {
					
						if(j(this).val() != j('#select_charts_sell').val()) {
							setChart(j(this).val(), j('#select_charts_sell').val());
							lastSelected = j(this).val();
						} else {
							setSell(lastSelected, true);
							lastSelected = j(this).val();
						}
						
						silentScroll(false);
						
	            	});
				
	            if(currencies.length != 0) {
	            	var options = j('#select_charts_buy')[0].options;
	            	var x = 0;
	            	var s = null;
	            	for(var i = 0; i < currencies.length; i++) {
						var option = new Option(currencies[i].symbol, currencies[i].symbol);
						try {
							options.add(option);
						} catch (ex) {
							options.add(option, null);
						}
						
						if(currencies[i].symbol == a) {
							s = x;
						}
						x++;
	            	}
	            	
	            	if(s != null) {
	            		j('#select_charts_buy')[0].selectedIndex = s;
	            		lastSelected = j('#select_charts_buy').val();
	            	}
	            }
	            
	            if(freshContracts) {
	            	setChart(j('#select_charts_buy').val(), j('#select_charts_sell').val());
	            }
	            
			};
			
			var setSell = function(b, freshContracts) {
			
				var currencies = [];
					currencies = getCurrencies();
					
				var lastSelected = null;
				
				j('#select_charts_sell')
					.empty()
	        		.unbind('change')
	            	.change(function() {
					
						if(j(this).val() != j('#select_charts_buy').val()) {
							setChart(j('#select_charts_buy').val(), j(this).val());
							lastSelected = j(this).val();
						} else {
							setBuy(lastSelected, true);
							lastSelected = j(this).val();
						}
						
						silentScroll(false);
					
	            	});
				
	            if(currencies.length != 0) {
				
	            	var options = j('#select_charts_sell')[0].options;
	            	var x = 0;
	            	var s = null;
	            	for(var i = 0; i < currencies.length; i++) {            			
						var option = new Option(currencies[i].symbol, currencies[i].symbol);
						try {
							options.add(option);
						} catch (ex) {
							options.add(option, null);
						}
						
						if(currencies[i].symbol == b) {
							s = x;
						}
						x++;
	            	}
					
	            	if(s != null) {
	            		j('#select_charts_sell')[0].selectedIndex = s;
	            		lastSelected = j('#select_charts_sell').val();
	            	}
	            }
	            
	            if(freshContracts) {
	            	setChart(j('#select_charts_buy').val(), j('#select_charts_sell').val());
	            }
	            
			};
			
			var setInstruments = function(defaultSymbol) {
			
				j('#select_charts_instrument')
					.empty()
					.unbind('change')
	            	.change(function() {
					
					var buy = getPair(j(this).val());
					var sell = j(this).val();
					
					setChart(buy, sell);
					
				});
				
				var currencies = [];
					currencies = getCurrencies();
					
				if(currencies.length != 0) {
				
					var options = j('#select_charts_instrument')[0].options;
					var x = 0;
	            	var s = null;
					for(var i = 0; i < currencies.length; i++) {            			
						var option = new Option(currencies[i].description, currencies[i].symbol);
						try {
							options.add(option);
						} catch (ex) {
							options.add(option, null);
						}
						
						if(currencies[i].symbol == defaultSymbol) {
							s = x;
						}
						x++;
							
	            	}
					
					if(s != null) {
	            		j('#select_charts_instrument')[0].selectedIndex = s;
	            	}
					
				}
				
				var buy = getPair(j('#select_charts_instrument').val());
				var sell = j('#select_charts_instrument').val();
				
				setChart(buy, sell);
				
			
			};
			
			if(!isAuthenticated) {
			
				if(instrumentType == 1) {
					setBuy(buy);
					setSell(sell);
					setChart(j('#select_charts_buy').val(), j('#select_charts_sell').val());
				}
			
				if(instrumentType == 2 
				|| instrumentType == 3) {
				
					setInstruments(getRelative(buy, sell));
				}
				
				j('#select_chart_interval')
					.unbind("change")
					.change(function() {
					
						if(instrumentType == 1) {
							setChart(j('#select_charts_buy').val(), j('#select_charts_sell').val());
						}
						
						if(instrumentType == 2 
						|| instrumentType == 3) {
						
							var buy = getPair(j('#select_charts_instrument').val());
							var sell = j('#select_charts_instrument').val();
							
							setChart(buy, sell);
						
						}
				
					});
					
				// trade button event
				j('#button_chart_trade')
					.unbind('click')
					.click(function() {
						
						screenManager.show(screens.openDeal);
					
				});
				
			} else {
				
				jsapi.accounts.user.getUserInfo({
					success: function (b) {
					
						isEnableFractionalPips = b.isEnableFractionalPips;
						accountBaseCurrency = b.accountBaseCurrency;
						withdrawalAvailable = b.withdrawalAvailable;
						allowWithdrawal = b.allowWithdrawal;
						allowDeposit = b.allowDeposit; 
						isActive = b.isActive;
						isDemo = b.isDemo;
						userStatus = b.userStatus;
						needToPassTest = b.needToPassTest;
						isSuitabilityTestPassed = b.isSuitabilityTestPassed;
						regionId = b.regionId;
						regTicketId = b.regTicketId;
						
						
						if(instrumentType == 1) {
							setBuy(buy);
							setSell(sell);
							setChart(j('#select_charts_buy').val(), j('#select_charts_sell').val());
						}
						
						if(instrumentType == 2 
						|| instrumentType == 3) {
							setInstruments(getRelative(buy, sell));
						}
						
						j('#select_chart_interval')
							.unbind('change')
							.change(function() {
							
								if(instrumentType == 1) {
									setChart(j('#select_charts_buy').val(), j('#select_charts_sell').val());
								}
								
								if(instrumentType == 2 
								|| instrumentType == 3) {
								
									var buy = getPair(j('#select_charts_instrument').val());
									var sell = j('#select_charts_instrument').val();
									
									setChart(buy, sell);
									
								}
								
						});
						
						// trade button event
						j('#button_chart_trade')
							.unbind('click')
							.click(function() {
								
								if(!isActive) {
									// if user logged in but not active show confirm message and got to continue register step 2
									if(userStatus == "Lead" 
									|| userStatus == "Lead, Restricted") {
									
										var modal = new lightFace({
											title : "",
											message : getContent("continueRegistration"),
											actions : [
											   { 
													label : getContent("ok"), 
													fire : function() {
														screenManager.show(screens.register);
														modal.close();
													}, 
													color: "green" }
											],
											overlayAll : true
										});
									
									} else {
										
										// if user don't have enough funds
										// screenManager.show(screens.contactUs);
										
										var modal2 = new lightFace({
											title : "",
											message : getContent("dealCouldNotBeCompleted"),
											actions : [
											   { 
													label : getContent("ok"), 
													fire : function() {
														modal2.close();
													}, 
													color: "green" 
												}
											],
											overlayAll : true
										});
										
									}
									return false;
								}
							
								screenManager.show(screens.openDeal);
							
						});
					
					
					},
					error: function (a) {
						
						if(a != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : getContent(a),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
						
					}
					
				});
				
			}
			
		},
		destroy : function() {
			j('#image_chart').empty();
		},
		orientationchange : function() {
			
		}
	},
	openDeal : {
		lastActiveTab : null,
		types : {
			daytrading : 3,
			limit : 4,
			forward : 2
		},
		init : function() {
			
			j('#section_open_deal_open').show();
			
			// currencies
			if(instrumentType == 1) {
			
				j('#label_index').hide();
				j('#label_commodity').hide();
				
				j('.container-instruments').hide();
				j('.container-currencies').show();
			
				j('ul#list_buttons_open_deal li a').removeClass('active');
				j('ul#list_buttons_open_deal li a').each(function(i, el) {
					if(i == 0) {
						j(el).addClass('active');
						handle.openDeal.lastActiveTab = j(el);
					}
					j(el)
						.unbind('click')
						.click(function() {
						if(handle.openDeal.lastActiveTab != null && handle.openDeal.lastActiveTab != j(this)) {
							handle.openDeal.lastActiveTab.removeClass('active');
						}
						handle.openDeal.lastActiveTab = j(this); 
						
						j(this).addClass('active');
						handle.openDeal.bind(handle.openDeal.types[j(this).attr('name')]);
						
					});
				});
			}
			
			// commodities or indices
			if(instrumentType == 2 
			|| instrumentType == 3) {
				
				if(instrumentType == 2) {
					j('#label_index').hide();
					j('#label_commodity').show();
				}
				
				if(instrumentType == 3) {
					j('#label_commodity').hide();
					j('#label_index').show();
				}
				
				j('.container-currencies').hide();
				j('.container-instruments').show();
				
			
				j('ul#list_buttons_open_deal_instuments li a').removeClass('active');
				j('ul#list_buttons_open_deal_instuments li a').each(function(i, el) {
					if(i == 0) {
						j(el).addClass('active');
						handle.openDeal.lastActiveTab = j(el);
					}
					j(el)
						.unbind('click')
						.click(function() {
						if(handle.openDeal.lastActiveTab != null && handle.openDeal.lastActiveTab != j(this)) {
							handle.openDeal.lastActiveTab.removeClass('active');
						}
						handle.openDeal.lastActiveTab = j(this); 
						
						j(this).addClass('active');
						handle.openDeal.bind(handle.openDeal.types[j(this).attr('name')]);
						
					});
				});
			}
			
			
			handle.openDeal.bind(handle.openDeal.types.daytrading);
			
		},
		bind : function(productId) {
			
			var pair = null;
			if(lastPair != null) {
				pair = lastPair.pair;
			} else {
				pair = jsapi.trading.tradingInfo.instrumentTypes[instrumentType].pair;
			}
			
			var sell = pair.substr(0, 3);
            var buy = pair.substr(3, 3);
			
            var lastSettings = {
				rate : null,
				buy : null,
				sell : null,
				margin : null,
				amount : null,
				productId : null,
				forwardDate : null,
				expiryDate : null
			};
            
            var lastProposal = {
				buy : null,
				sell : null,
				currentRate : null,
				forwardRate : null,
				stopLossRate : null,
				tradableQuoteId : null,
				startDate : null,
				endDate : null,
				endTime : null,
				defaultDate: null,
				above : null,
				below : null,
				max : null,
				min : null
			};
			
            var isFreezed = false;
			var isReset = true;
            
            var open = function() {
			
				// forward
				if(productId == 2) {
					if(lastProposal.tradableQuoteId != null) {
					
						loader.show();
						
						// open deal
						jsapi.trading.deals.openDeal({
							buyCurrency : lastSettings.buy,
							sellCurrency : lastSettings.sell,
							amount : lastSettings.amount,
							margin : lastSettings.margin,
							productType : 2,
							forwardDate : j('#input_open_deal_forward_date .date-picker-container-input').text(),
							quoteId : lastProposal.tradableQuoteId,
							success : function(data) {
								
								loader.hide();
								
								j('.instrument-types-placeholder').hide();
								
								j('#section_open_deal_open').hide();
								j('#section_open_deal_thank_you').show();
								
								j('#label_open_deal_thank_you_message').html(String.format(getContent("successOpenDeal"), products[2], data.dealId));
								
								// deal properties
								j('#label_open_deal_thank_you_deal_id').text(data.dealId);
								j('#label_open_deal_thank_you_buy').text(data.buyAmount + " " + data.buyCurr);
								j('#label_open_deal_thank_you_sell').text(data.sellAmount + " " + data.sellCurr);
								
								j('#label_open_deal_thank_you_open_rate').html(fractionalFormat(data.rate, (data.buyCurr + data.sellCurr)));
								j('#label_open_deal_thank_you_stop_loss_rate').html(fractionalFormat(data.slr, (data.buyCurr + data.sellCurr)));
								j('#label_open_deal_thank_you_margin').text(data.margin + " " + data.marginCurr);
								
								// forward date
								j('#row_open_deal_thank_you_forward_date').show();
								j('#label_open_deal_thank_you_forward_date').text(data.dealDate);
								
								
								silentScroll(true);
								
								
							},
							error : function(error) {
								
								loader.hide();
								
								// re-run proposal
								getProposal();
								
								if(error != errors.applicationNotLoggedIn) {
									var modal = new lightFace({
										title : "",
										message : (getContent(error) != null ? getContent(error) : getContent("0")), 
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
								}
								
							}
						});
					}
				}
				
				if(productId == 3) {
					if(lastProposal.tradableQuoteId != null) {
						
						loader.show();
						
						// open deal
						jsapi.trading.deals.openDeal({
							buyCurrency : lastSettings.buy,
							sellCurrency : lastSettings.sell,
							amount : lastSettings.amount,
							margin : lastSettings.margin,
							productType : 3,
							quoteId : lastProposal.tradableQuoteId,
							success : function(data) {
								
								loader.hide();
								
								j('.instrument-types-placeholder').hide();
								
								j('#section_open_deal_open').hide();
								j('#section_open_deal_thank_you').show();
								
								j('#label_open_deal_thank_you_message').html(String.format(getContent("successOpenDeal"), products[3], data.dealId));
								
								// deal properties
								j('#label_open_deal_thank_you_deal_id').text(data.dealId);
								j('#label_open_deal_thank_you_buy').text(data.buyAmount + " " + data.buyCurr);
								j('#label_open_deal_thank_you_sell').text(data.sellAmount + " " + data.sellCurr);
								j('#label_open_deal_thank_you_open_rate').html(fractionalFormat(data.rate, (data.buyCurr + data.sellCurr)));
								j('#row_open_deal_thank_you_rolling_until').show();
								j('#label_open_deal_thank_you_rolling_until').text(data.dealDate);
								j('#label_open_deal_thank_you_stop_loss_rate').html(fractionalFormat(data.slr, (data.buyCurr + data.sellCurr)));
								j('#label_open_deal_thank_you_margin').text(data.margin + " " + data.marginCurr);
								
								
								silentScroll(true);
								
							},
							error : function(error) {
								
								loader.hide();
								
								// re-run proposal
            					getProposal();
            					
								if(error != errors.applicationNotLoggedIn) {
									var modal = new lightFace({
										title : "",
										message : (getContent(error) != null ? getContent(error) : getContent("0")),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
								}
            					
							}
						});
					}
				}
				
				// limit
				if(productId == 4) {
					
					loader.show();
					// open deal
					jsapi.trading.deals.openDeal({
						buyCurrency : lastSettings.buy,
						sellCurrency : lastSettings.sell,
						amount : lastSettings.amount,
						margin : lastSettings.margin,
						productType : 4,
						rate : j('#input_open_deal_limit_rate').val().replace(/ /g,""),
                    	expiryDate : j('#input_open_deal_reserved_until .date-picker-container-input').text(),
						success : function(data) {
							
							loader.hide();
							
							j('.instrument-types-placeholder').hide();
							
							j('#section_open_deal_open').hide();
							j('#section_open_deal_thank_you').show();
							
							j('#label_open_deal_thank_you_message').html(String.format(getContent("successOpenDeal"), products[4], data.dealId));
							
							// deal properties
							j('#label_open_deal_thank_you_deal_id').text(data.dealId);
							j('#label_open_deal_thank_you_buy').text(data.buyAmount + " " + data.buyCurr);
							j('#label_open_deal_thank_you_sell').text(data.sellAmount + " " + data.sellCurr);							
							j('#label_open_deal_thank_you_open_rate').html(fractionalFormat(data.rate, (data.buyCurr + data.sellCurr)));
							j('#label_open_deal_thank_you_stop_loss_rate').html(fractionalFormat(data.slr, (data.buyCurr + data.sellCurr)));
							j('#label_open_deal_thank_you_margin').text(data.margin + " " + data.marginCurr);
							
							
							
							silentScroll(true);
							
							
						},
						error : function(error) {
							
							loader.hide();
							
							// re-run proposal
        					getProposal();
        					
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : (getContent(error) != null ? getContent(error) : getContent("0")),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
        					
						}
					});
				}
			};
            
			// proposal
			var lastAfterHoursSpread = null;
            var setProposal = function(data) {
			
				// after-hours spread
				if(data.afterHoursSpread != undefined) {
					if(data.afterHoursSpread != null) {
						var instrumentPair = "";
						if(data.symbol != undefined) {
							
							instrumentPair = data.symbol.substring(0, 3) + "/" + data.symbol.substring(3, 6);
							
							// check for last
							var isShow = true;
							if(lastAfterHoursSpread != null 
								&& lastAfterHoursSpread == data.afterHoursSpread) {
								isShow = false;
							}
							lastAfterHoursSpread = data.afterHoursSpread;
							
							if(isShow) {
								var modal = new lightFace({
									title : "",
									message : String.format(getContent("afterHours"), instrumentPair, data.afterHoursSpread), // Please note: Due to after-hours market activity or special market conditions, the {0} spread is now {1} pips.
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
					}
				}
			
				// label 
				j('#label_open_deal_debited').html(getContent("yourFreeBalanceIs") + " " + data.freeBalance + " " + accountBaseCurrency + "<br/><b>" + (data.newAccBaseMarginIsCredited ? getContent("credited") : getContent("debited") ) + " " + data.newAccBaseMargin + " " + accountBaseCurrency + "</b>");
				
				// forward set proposal
				if(productId == 2) {
					
					// current rate
					j('#label_open_deal_current_rate').html(fractionalFormat(data.currentRate, (lastSettings.buy + lastSettings.sell)));
					if(lastProposal.currentRate != null) {
						j('#label_open_deal_current_rate')
							.removeClass((data.currentRate != lastProposal.currentRate) ? ( (data.currentRate > lastProposal.currentRate) ? "down" : ((data.currentRate < lastProposal.currentRate) ? "up" : "") ) : "down up")
							.addClass((data.currentRate != lastProposal.currentRate) ? ( (data.currentRate > lastProposal.currentRate) ? "up" : ((data.currentRate < lastProposal.currentRate) ? "down" : "") ) : "");
					}
					
					// stop loss rate
					j('#label_open_deal_stop_loss_rate').html(fractionalFormat(data.stopLossRate, (lastSettings.buy + lastSettings.sell)));
					
					// forward rate
					j('#label_open_deal_forward_rate').html(fractionalFormat(data.forwardRate, (lastSettings.buy + lastSettings.sell)));
					if(lastProposal.forwardRate != null) {
						j('#label_open_deal_forward_rate')
							.removeClass((data.forwardRate != lastProposal.forwardRate) ? ( (data.forwardRate > lastProposal.forwardRate) ? "down" : ((data.forwardRate < lastProposal.forwardRate) ? "up" : "") ) : "down up")
							.addClass((data.forwardRate != lastProposal.forwardRate) ? ( (data.forwardRate > lastProposal.forwardRate) ? "up" : ((data.forwardRate < lastProposal.forwardRate) ? "down" : "") ) : "");
					}
					
					// forward points
					j('#label_open_deal_forward_points').html(fractionalFormat(data.forwardPoints, (lastSettings.buy + lastSettings.sell)));
					
					if(isReset) {
						if(data.defaultDate && lastSettings.forwardDate == null) {
							j('#input_open_deal_forward_date').datePicker({
								startDate : data.startDate,
								endDate : data.endDate,
								defaultDate : data.defaultDate,
								nonBusinessDates : [],
								change : function() {
									getProposal();
								}
							});
						}
						isReset = false;
					}
					
				}
				
				// day trading set proposal
				if(productId == 3) {
					
					// current rate
					j('#label_open_deal_current_rate')
						.html(fractionalFormat(data.currentRate, (lastSettings.buy + lastSettings.sell)));
					
					j('#row_open_deal_rolling_until').html(String.format(getContent("openDealRollingUntil"), "<span class=\"label-green\">" + getFormattedDate(data.endDate) + "</span>", data.endTime));
					if(lastProposal.currentRate != null) {
						j('#label_open_deal_current_rate')
							.removeClass((data.currentRate != lastProposal.currentRate) ? ( (data.currentRate > lastProposal.currentRate) ? "down" : ((data.currentRate < lastProposal.currentRate) ? "up" : "") ) : "down up")
							.addClass((data.currentRate != lastProposal.currentRate) ? ( (data.currentRate > lastProposal.currentRate) ? "up" : ((data.currentRate < lastProposal.currentRate) ? "down" : "") ) : "");
					}
					
					// stop loss rate
					j('#label_open_deal_stop_loss_rate').html(fractionalFormat(data.stopLossRate, (lastSettings.buy + lastSettings.sell)));
					
					if(isReset) {
						isReset = false;
					}
					
				}
				
				// limit set proposal
				if(productId == 4) {
				
					// above or below
					j('#label_open_deal_limit_rate_above').text(data.above);
					j('#label_open_deal_limit_rate_below').text(data.below);
					
					// update -> stop loss
					if(!isFreezed) {
						if(j('#input_open_deal_limit_rate').val().replace(/ /g,"").length != 0) {
							j('#label_open_deal_stop_loss_rate').html(fractionalFormat(data.stopLossRate, (lastSettings.buy + lastSettings.sell)));
						} else {
							// clear stop loss
							j('#label_open_deal_stop_loss_rate').html("");
						}
					}
					
					if(isReset) {
						if(data.defaultDate && lastSettings.expiryDate == null) {
							j('#input_open_deal_reserved_until').datePicker({
								startDate : data.startDate,
								endDate : data.endDate,
								defaultDate : data.defaultDate,
								nonBusinessDates : [],
								change : function() {
									getProposal();
								}
							});
						}
						isReset = false;
					}
					
				}
				
				// set last proposal 
				lastProposal.currentRate = data.currentRate;
				lastProposal.stopLossRate = data.stopLossRate;
				lastProposal.tradableQuoteId = data.tradableQuoteId;
				lastProposal.defaultDate = data.defaultDate;
				lastProposal.startDate = data.startDate;
				lastProposal.endDate = data.endDate;
				lastProposal.endTime = data.endTime;
				lastProposal.above = data.above;
				lastProposal.below = data.below;
				lastProposal.max = data.max;
				lastProposal.min = data.min;
				
			};
            
            var getProposal = function() {
			
				isReset = true;
				
            	lastProposal.currentRate = null;
				lastProposal.forwardRate = null;
				lastProposal.stopLossRate = null;
				lastProposal.tradableQuoteId = null;
				
				// forward
				if(productId == 2) {
					
					j('#label_open_deal_current_rate')
						.empty()
						.removeClass('up down')
						.text(getContent("loading"));
				
					j('#label_open_deal_stop_loss_rate')
						.empty()
						.text(getContent("loading"));
						
					j('#label_open_deal_forward_rate')
						.empty()
						.removeClass('up down')
						.text(getContent("loading"));
						
					j('#label_open_deal_forward_points')
						.empty()
						.text(getContent("loading"));
					
					// currencies
					if(instrumentType == 1) {
						lastSettings.buy = j('#select_open_deal_buy').val();
						lastSettings.sell = j('#select_open_deal_sell').val();
					}
					
					// commodities or indices
					if(instrumentType == 2 
					|| instrumentType == 3) {
						lastSettings.buy = j('#select_open_deal_direction').val() == "BUY" ? j('#select_open_deal_instrument').val() : getNonBase(j('#select_open_deal_instrument').val());
						lastSettings.sell = j('#select_open_deal_direction').val() == "BUY" ? getNonBase(j('#select_open_deal_instrument').val()) : j('#select_open_deal_instrument').val();
					}
					
					lastSettings.amount = parseFloat(j('#select_open_deal_amount').find('option:selected').attr('amountvalue'));
					lastSettings.margin = parseFloat(j('#select_open_deal_margin').find('option:selected').attr('marginvalue'));
					lastSettings.forwardDate = j('#input_open_deal_forward_date .date-picker-container-input').text() != "" ? j('#input_open_deal_forward_date .date-picker-container-input').text() : null;
					
					jsapi.trading.proposal.abandon();
					jsapi.trading.proposal.getProposal({
						operationType : "open",
						productId : 2,
						isInit : true,
						buy : lastSettings.buy,
						sell : lastSettings.sell,
						amount : lastSettings.amount,
						margin : lastSettings.margin,
						forwardDate : lastSettings.forwardDate,
						success : function(data) {
							setProposal(data);
						},
						error : function(error) {
							
							// for error 21
							
							// stop proposal
							jsapi.trading.proposal.abandon();
							// clear entered rate
							lastSettings.rate = null;
							
							// clear current rate
							j('#label_open_deal_current_rate')
								.empty()
								.removeClass('up down');
					
							// clear stop loss
							j('#label_open_deal_stop_loss_rate')
								.empty();
							
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : (getContent(error) != null ? getContent(error) : getContent("0")),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
							
						}
					}, 2000);
				}
				
				// day trading
				if(productId == 3) {
					
					j('#label_open_deal_current_rate')
						.empty()
						.removeClass('up down')
						.text(getContent("loading"));
				
					j('#label_open_deal_stop_loss_rate')
						.empty()
						.text(getContent("loading"));
					
					
					// currencies
					if(instrumentType == 1) {
						lastSettings.buy = j('#select_open_deal_buy').val();
						lastSettings.sell = j('#select_open_deal_sell').val();
					}
					
					// commodities or indices
					if(instrumentType == 2 
					|| instrumentType == 3) {
						lastSettings.buy = j('#select_open_deal_direction').val() == "BUY" ? j('#select_open_deal_instrument').val() : getNonBase(j('#select_open_deal_instrument').val());
						lastSettings.sell = j('#select_open_deal_direction').val() == "BUY" ? getNonBase(j('#select_open_deal_instrument').val()) : j('#select_open_deal_instrument').val();
					}
					
					lastSettings.amount = parseFloat(j('#select_open_deal_amount').find('option:selected').attr('amountvalue'));
					lastSettings.margin = parseFloat(j('#select_open_deal_margin').find('option:selected').attr('marginvalue'));
					
					jsapi.trading.proposal.abandon();
					jsapi.trading.proposal.getProposal({
						operationType : "open",
						productId : 3,
						buy : lastSettings.buy,
						sell : lastSettings.sell,
						isInit : true,
						amount : lastSettings.amount,
						margin : lastSettings.margin,
						success : function(data) {
							setProposal(data);
						},
						error : function(error) {
							
							// for error 21
							
							// stop proposal
							jsapi.trading.proposal.abandon();
							// clear entered rate
							lastSettings.rate = null;
							
							// clear current rate
							j('#label_open_deal_current_rate')
								.empty()
								.removeClass('up down');
					
							// clear stop loss
							j('#label_open_deal_stop_loss_rate')
								.empty();
							
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : (getContent(error) != null ? getContent(error) : getContent("0")),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
					}, 2000);
				}
				
				// limit
				if(productId == 4) {
					
					j('#label_open_deal_stop_loss_rate')
						.empty()
						.text(getContent("loading"));
					
					// for validation
					lastSettings.rate = null;
					
					// currencies
					if(instrumentType == 1) {
						lastSettings.buy = j('#select_open_deal_buy').val();
						lastSettings.sell = j('#select_open_deal_sell').val();
					}
					
					// commodities or indices
					if(instrumentType == 2 
					|| instrumentType == 3) {
						lastSettings.buy = j('#select_open_deal_direction').val() == "BUY" ? j('#select_open_deal_instrument').val() : getNonBase(j('#select_open_deal_instrument').val());
						lastSettings.sell = j('#select_open_deal_direction').val() == "BUY" ? getNonBase(j('#select_open_deal_instrument').val()) : j('#select_open_deal_instrument').val();
					}
					
					lastSettings.amount = parseFloat(j('#select_open_deal_amount').find('option:selected').attr('amountvalue'));
					lastSettings.margin = parseFloat(j('#select_open_deal_margin').find('option:selected').attr('marginvalue'));
					lastSettings.expiryDate = j('#input_open_deal_reserved_until .date-picker-container-input').text() != "" ? j('#input_open_deal_reserved_until .date-picker-container-input').text() : null;
					
					// must clear rate when currencies changed + datepicker reinit
					// rate
					if(!isFreezed) {
					
						// validate -> entered rate
						var newRate = j('#input_open_deal_limit_rate').val().replace(/ /g,"");
						if(newRate.length != 0) {
							if(lastProposal.above != null 
									&& lastProposal.below != null) {
								
								if(parseFloat(lastProposal.above) < parseFloat(newRate) 
										|| parseFloat(lastProposal.below) > parseFloat(newRate)) {
									
									// above ++
									if(parseFloat(lastProposal.above) < parseFloat(newRate)) {
										if(parseFloat(lastProposal.max) > parseFloat(newRate)) {
											// set new rate to get proposal
											lastSettings.rate = newRate;
										} else {
											
											// clear entered rate
											lastSettings.rate = null;
											j('#input_open_deal_limit_rate').val("");
											
											var modal = new lightFace({
												title : "",
												message : String.format(getContent("maxRate"), lastProposal.max),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
											
											
										}
									} 
									
									// below --
									if(parseFloat(lastProposal.below) > parseFloat(newRate)) {
										if(parseFloat(lastProposal.min) < parseFloat(newRate)) {
											// set new rate to get proposal
											lastSettings.rate = newRate;
										} else {
											
											// clear entered rate
											lastSettings.rate = null;
											j('#input_open_deal_limit_rate').val("");
											
											var modal = new lightFace({
												title : "",
												message : String.format(getContent("minRate"), lastProposal.min),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
											
										}
									}
									
								} else {
									
									// clear entered rate
									lastSettings.rate = null;
									j('#input_open_deal_limit_rate').val("");
									
									var modal = new lightFace({
										title : "",
										message : String.format(getContent("enterRateAboveOrBelow"), lastProposal.above, lastProposal.below),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
									
								}
							}
							
						} else {
							lastSettings.rate = null;
						}
					}
					
					jsapi.trading.proposal.abandon();
					jsapi.trading.proposal.getProposal({
						operationType : "open",
						productId : 4,
						isInit : true,
						rate : lastSettings.rate,
						buy : lastSettings.buy,
						sell : lastSettings.sell,
						amount : lastSettings.amount,
						margin : lastSettings.margin,
						success : function(data) {
							setProposal(data);
						},
						error : function(error) {
							
							// stop proposal
							jsapi.trading.proposal.abandon();
							
							// clear entered rate
							lastSettings.rate = null;
							j('#input_open_deal_limit_rate').val("");
							j('#label_open_deal_stop_loss_rate')
								.empty();
							
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : (getContent(error) != null ? getContent(error) : getContent("0")),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
					}, 2000);
				}
				
            };
            
			var setMargins = function(a, b, contact) {
	            
				var margins = jsapi.trading.getMarginSet(a, b, contact);
	            if(margins.length != 0) {
	            	j('#select_open_deal_margin')
		            	.empty()
		            	.unbind('change')
		            	.change(function() {
						
							j('#input_open_deal_forward_date .date-picker-container-input').text("");
							j('#input_open_deal_reserved_until .date-picker-container-input').text(""); 
						
		            		// get proposal
							getProposal();
							
	            	});
	            	
	            	var options = j('#select_open_deal_margin')[0].options;
					var x = 0;
					var s = null;
	            	for(var i = 0; i < margins.length; i++) {
	            		// text, value
	            		var option = new Option(margins[i].margin + " " + margins[i].marginCurrency, margins[i].margin);
	            		option.setAttribute("marginvalue", margins[i].marginValue);
		            	 try {
		            		 options.add(option);
		                } catch (ex) {
		                	options.add(option, null);
		                }
						
						// set default selected
						if(margins[i].isDefault) {
							s = x;
						}
						
						x++;
	            	}
					
					if(s != null) {
						j('#select_open_deal_margin')[0].selectedIndex = s;
					}
	            	
					j('#input_open_deal_forward_date .date-picker-container-input').text("");
					j('#input_open_deal_reserved_until .date-picker-container-input').text("");
					
					// get proposal
	            	getProposal();
	            	
	            }
			};
            
            var setContracts = function(a, b) {
				
				// clear entered rate
				lastSettings.rate = null;
				j('#input_open_deal_limit_rate').val("");
				j('#label_open_deal_stop_loss_rate')
					.empty();
				
	            var amounts = jsapi.trading.getAmountSet(a, b);
	            if(amounts.length != 0) {
	            	
	            	j('#select_open_deal_amount')
		            	.empty()
		            	.unbind('change')
		            	.change(function() {
							
							// set margins
		            		setMargins(a, b, j(this).val());
	            	});
	            	
	            	var options = j('#select_open_deal_amount')[0].options;
					var x = 0;
					var s = null;
	            	for(var i = 0; i < amounts.length; i++) {
	            		// text, value
	            		var option = new Option(amounts[i].amount + " " + amounts[i].amountCurrency, amounts[i].contract);
	            		option.setAttribute("amountvalue", amounts[i].amountValue);
		            	 try {
		            		 options.add(option);
		                } catch (ex) {
		                	options.add(option, null);
		                }
						
						// set default selected
						if(amounts[i].isDefault) {
							s = x;
						}
						
						x++;
	            	}
					
					if(s != null) {
						j('#select_open_deal_amount')[0].selectedIndex = s;
					}
					
	            	
					// set margins
	            	setMargins(a, b, j('#select_open_deal_amount').val());
	            	
	            } else {
	            	
	            	// abandon -> proposal;
            		jsapi.trading.proposal.abandon();
            		
            		var modal = new lightFace({
						title : "",
						message : String.format(getContent("currencyPairNotAvailableForTrading"), a, b),
						actions : [
						   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
						],
						overlayAll : true
					});
            		
					
					
					pair = jsapi.trading.tradingInfo.instrumentTypes[instrumentType].pair
					sell = pair.substr(0, 3);
					buy = pair.substr(3, 3);
					
					
					lastPair = {
						pair : sell + buy
					};
					
					
					// currencies
					if(instrumentType == 1) {
						
						// set defaults
						// fill buy and sell currencies
						setBuy(buy);
						setSell(sell);
						
						// fill contracts -> amounts and margins
						setContracts(buy, sell);
					
					}
					
					// commodities
					if(instrumentType == 2) {
						
						// set defaults
						var currencyPair = jsapi.trading.getCurrencyPairSettings(buy, sell);
						var defaultSymbol = "";
						if(currencyPair != null) {
							defaultSymbol = currencyPair.baseCurrency;
							j('#select_open_deal_direction')[0].selectedIndex = (defaultSymbol == buy ? 0 : 1);
							
							setInstruments(defaultSymbol);
							
						}
						
					}
					
					// indices
					if(instrumentType == 3) {
						
						// set defaults
						var currencyPair = jsapi.trading.getCurrencyPairSettings(buy, sell);
						var defaultSymbol = "";
						if(currencyPair != null) {
							defaultSymbol = currencyPair.baseCurrency;
							j('#select_open_deal_direction')[0].selectedIndex = (defaultSymbol == buy ? 0 : 1);
							
							setInstruments(defaultSymbol);
							
						}
						
					}
	            	
	            }
	            
			}; 
            
			// buy
			var setBuy = function(a, freshContracts) {
			
				var currencies = [];
					currencies = getCurrencies();
				
				var lastSelected = null;
				
				j('#select_open_deal_buy')
					.empty()
					.unbind('change')	
					.change(function() {

						if(j(this).val() != j('#select_open_deal_sell').val()) {
							setContracts(j(this).val(), j('#select_open_deal_sell').val());
							lastSelected = j(this).val();
						} else {
							setSell(lastSelected, true);
							lastSelected = j(this).val();
						}
						
						lastPair = {
							pair : j('#select_open_deal_sell').val() + j('#select_open_deal_buy').val()
						};
						
						silentScroll(false);
							
					});
				
				if(currencies.length != 0) {
					var options = j('#select_open_deal_buy')[0].options;
					var x = 0;
					var s = null;
					for(var i = 0; i < currencies.length; i++) {
						if(!currencies[i].isRestricted) {
							var option = new Option(currencies[i].symbol, currencies[i].symbol);
							try {
								 options.add(option);
							} catch (ex) {
								options.add(option, null);
							}
							
							if(currencies[i].symbol == a) {
								s = x;
							}
							x++;
						}
					}
					
					if(s != null) {
						j('#select_open_deal_buy')[0].selectedIndex = s;
						lastSelected = j('#select_open_deal_buy').val();
					}
				}
				
				if(freshContracts) {
					setContracts(j('#select_open_deal_buy').val(), j('#select_open_deal_sell').val());
				}
				
			};
			
			// sell
			var setSell = function(b, freshContracts) {
				
				var currencies = [];
					currencies = getCurrencies();
					
				var lastSelected = null;
				
				j('#select_open_deal_sell')
					.empty()
	        		.unbind('change')
	            	.change(function() {
	            		if(j(this).val() != j('#select_open_deal_buy').val()) {
	            			setContracts(j('#select_open_deal_buy').val(), j(this).val());
	            			lastSelected = j(this).val();
	            		} else {
	            			setBuy(lastSelected, true)
	            			lastSelected = j(this).val();
	            		}
						
						lastPair = {
							pair : j('#select_open_deal_sell').val() + j('#select_open_deal_buy').val()
						};
						
						silentScroll(false);
						
	            	});
	            
	            if(currencies.length != 0) {
	            	var options = j('#select_open_deal_sell')[0].options;
	            	var x = 0;
	            	var s = null;
	            	for(var i = 0; i < currencies.length; i++) {
	            		if(!currencies[i].isRestricted) {
	            			var option = new Option(currencies[i].symbol, currencies[i].symbol);
			            	 try {
			            		 options.add(option);
			                } catch (ex) {
			                	options.add(option, null);
			                }
			                
			                if(currencies[i].symbol == b) {
			                	s = x;
		            		}
			                x++;
	            		}
	            	}
	            	if(s != null) {
	            		j('#select_open_deal_sell')[0].selectedIndex = s;
	            		lastSelected = j('#select_open_deal_sell').val();
	            	}
	            }
	            
	            if(freshContracts) {
	            	setContracts(j('#select_open_deal_buy').val(), j('#select_open_deal_sell').val());
	            }
				
			};
			
			// instuments
			var setInstruments = function(defaultSymbol) {
			
				j('#select_open_deal_instrument')
					.empty()
					.unbind('change')
	            	.change(function() {
					
					var buy = j('#select_open_deal_direction').val() == "BUY" ? j(this).val() : getNonBase(j('#select_open_deal_instrument').val());
					var sell = j('#select_open_deal_direction').val() == "BUY" ? getNonBase(j('#select_open_deal_instrument').val()) : j(this).val();
					
					lastPair = {
						pair : sell + buy
					};
					
					setContracts(buy, sell);
					
				});
				
				var currencies = [];
					currencies = getCurrencies();
					
				if(currencies.length != 0) {
					var options = j('#select_open_deal_instrument')[0].options;
					var x = 0;
	            	var s = null;
					for(var i = 0; i < currencies.length; i++) {
						
						if(!currencies[i].isRestricted) {
							var option = new Option(currencies[i].description, currencies[i].symbol);
							 try {
								 options.add(option);
							} catch (ex) {
								options.add(option, null);
							}
							
							if(currencies[i].symbol == defaultSymbol) {
			                	s = x;
		            		}
			                x++;
						}
						
					}
					
					if(s != null) {
	            		j('#select_open_deal_instrument')[0].selectedIndex = s;
	            	}
				}
				
				// set default contracts
				var buy = j('#select_open_deal_direction').val() == "BUY" ? j('#select_open_deal_instrument').val() : getNonBase(j('#select_open_deal_instrument').val());
				var sell = j('#select_open_deal_direction').val() == "BUY" ? getNonBase(j('#select_open_deal_instrument').val()) : j('#select_open_deal_instrument').val();
				
				
				lastPair = {
					pair : sell + buy
				};
				
				
				// set contracts
				setContracts(buy, sell);
			
			};
			
			jsapi.accounts.user.getUserInfo({
				success: function (b) {
				
					isEnableFractionalPips = b.isEnableFractionalPips;
					accountBaseCurrency = b.accountBaseCurrency;
					withdrawalAvailable = b.withdrawalAvailable;
					allowWithdrawal = b.allowWithdrawal;
					allowDeposit = b.allowDeposit; 
					isActive = b.isActive;
					isDemo = b.isDemo;
					userStatus = b.userStatus;
					needToPassTest = b.needToPassTest;
					isSuitabilityTestPassed = b.isSuitabilityTestPassed;
					regionId = b.regionId;
					regTicketId = b.regTicketId;
					
					jsapi.trading.getTrading({
						productId : productId,
						success: function (a) {
						
							// currencies
							if(instrumentType == 1) {						
								// fill buy and sell currencies
								setBuy(buy);
								setSell(sell);
								
								// fill contracts -> amounts and margins
								setContracts(buy, sell);
							}
							
							// commodities or indices
							if(instrumentType == 2 
								|| instrumentType == 3) {
								
								j('#select_open_deal_direction')
									.unbind('change')
									.change(function() {
									
									var buy = j(this).val() == "BUY" ? j('#select_open_deal_instrument').val() : getNonBase(j('#select_open_deal_instrument').val());
									var sell = j(this).val() == "BUY" ? getNonBase(j('#select_open_deal_instrument').val()) : j('#select_open_deal_instrument').val();
									
									lastPair = {
										pair : sell + buy
									};
									
									setContracts(buy, sell);
									
								});
								
								// get defaultSymbol
								var currencyPair = jsapi.trading.getCurrencyPairSettings(buy, sell);
								var defaultSymbol = "";
								if(currencyPair != null) {
									defaultSymbol = currencyPair.baseCurrency;
									j('#select_open_deal_direction')[0].selectedIndex = (defaultSymbol == buy ? 0 : 1);
									
									setInstruments(defaultSymbol);
									
								}
								
							}
							
							// button trade
							j('#button_open_deal_trade_now')
								.unbind('click')
								.click(function() {
								
								if(isAuthenticated) {
									if(!isActive) {
										// if user logged in but not active show confirm message and got to continue register step 2
										if(userStatus == "Lead" 
										|| userStatus == "Lead, Restricted") {
											
											var modal = new lightFace({
												title : "",
												message : getContent("continueRegistration"),
												actions : [
												   { 
														label : getContent("ok"), 
														fire : function() {
															screenManager.show(screens.register);
															modal.close();
														}, 
														color: "green" 
													}
												],
												overlayAll : true
											});
											
										} else {
											// if user don't have enough funds
											// screenManager.show(screens.contactUs);
											var modal2 = new lightFace({
												title : "",
												message : getContent("dealCouldNotBeCompleted"),
												actions : [
												   { 
														label : getContent("ok"), 
														fire : function() {
															modal2.close();
														}, 
														color: "green" 
													}
												],
												overlayAll : true
											});
										}
										return false;
									}
								}
								
								// Forward open
								if(productId == 2) {
									setTimeout(function() {
										// abandon -> proposal;
										jsapi.trading.proposal.abandon();
										// validate if you have all information to open deal
										open();
										
									}, 150);
								}
								
								// day trading open
								if(productId == 3) {
									setTimeout(function() {
										// abandon -> proposal;
										jsapi.trading.proposal.abandon();
										// validate if you have all information to open deal
										open();
										
									}, 150);
								}
								
								// Limit only
								if(productId == 4) {
								
									j('#input_open_deal_limit_rate').trigger("focusout");
									
									setTimeout(function() {
										if(lastSettings.rate != null) {
											// abandon -> proposal;
											jsapi.trading.proposal.abandon();
											// validate if you have all information to open deal
											
											open();
										
										} else {
											
											// If rate is null show error message for enter a new limit rate
											var modal = new lightFace({
												title : "",
												message : getContent("enterNewRate"),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
											
										}
									}, 150);
									
								}
								
							});
							
							// only for limit set events
							if(productId == 4) {
								// input limit rate events
								j('#input_open_deal_limit_rate')
									.val('')
									.unbind('keyup')
									.keyup(function() {
										var val = j(this).val();
										if(isNaN(val)){
											 val = val.replace(/[^0-9\.]/g,'');
											 if(val.split('.').length>2) val =val.replace(/\.+$/,"");
										}
										j(this).val(val); 
									})
									.unbind('focusin')
									.focusin(function() {
										isFreezed = true;
									})
									.unbind('focusout')
									.focusout(function () {
										isFreezed = false;
										getProposal();
									});
									
							}
							
							silentScroll(true);
							
							
							
							
						},
						error: function (a) {
							
							if(a != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : getContent(a),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
					});
					
				},
				error: function (a) {
				
					if(a != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(a),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
				
				}
			});
			
            // forward
            if(productId == 2) {
            	j('#row_open_deal_rolling_until').hide();
            	j('#row_open_deal_reserved_until').hide();
            	j('#row_open_deal_limit_rate').hide();
            	
				j('#row_open_deal_stop_loss_rate').show();
				j('#row_open_deal_current_rate').show();
				
				j('#row_open_deal_forward_rate').show();
				j('#row_open_deal_forward_points').show();
				j('#row_open_deal_forward_date').show();
            	
            }
            
            // day trading
            if(productId == 3) {
            	j('#row_open_deal_rolling_until').show();
            	j('#row_open_deal_reserved_until').hide();
            	j('#row_open_deal_limit_rate').hide();
				j('#row_open_deal_forward_rate').hide();
				j('#row_open_deal_forward_points').hide();
				j('#row_open_deal_forward_date').hide();
            	
            	j('#row_open_deal_stop_loss_rate').show();
            	j('#row_open_deal_current_rate').show();
            }
            
            // limit
            if(productId == 4) {
            	j('#row_open_deal_rolling_until').hide();
            	j('#row_open_deal_stop_loss_rate').hide();
            	j('#row_open_deal_current_rate').hide();
				j('#row_open_deal_forward_rate').hide();
				j('#row_open_deal_forward_points').hide();
				j('#row_open_deal_forward_date').hide();
            	
            	j('#row_open_deal_reserved_until').show();
            	j('#row_open_deal_limit_rate').show();
            }
            
			
		},
		destroy : function() {
			
			// abandon proposal
			jsapi.trading.proposal.abandon();
			
			
			
			// unbind events -> clear html
			j('#select_open_deal_buy')
	        	.empty()
	        	.unbind('change');
			
			j('#select_open_deal_sell')
        		.empty()
        		.unbind('change');
			
			j('#select_open_deal_amount')
				.empty()
	    		.unbind('change');
			
			j('#select_open_deal_margin')
				.empty()
	    		.unbind('change');
			
			j('#label_open_deal_current_rate')
				.empty()
				.removeClass('up down');
		
			j('#label_open_deal_stop_loss_rate')
				.empty();
				
			j('#label_open_deal_forward_rate')
				.empty()
				.removeClass('up down');
				
			j('#label_open_deal_forward_points')
				.empty();
			
			
			
			// hide rows
        	j('#row_open_deal_stop_loss_rate').hide();
        	j('#row_open_deal_current_rate').hide();
        	j('#row_open_deal_reserved_until').hide();
        	j('#row_open_deal_limit_rate').hide();
            
			// clear limit fields and labels
			j('#input_open_deal_limit_rate')
				.val('')
				.unbind('focusin')
				.unbind('focusout');
			
			j('#label_open_deal_limit_rate_above').empty();
			j('#label_open_deal_limit_rate_below').empty();
			
			// clear thank you elements
			j('#section_open_deal_thank_you').hide();
			j('#label_open_deal_thank_you_message').empty();
			j('#label_open_deal_thank_you_deal_id').empty();
			j('#label_open_deal_thank_you_buy').empty();
			j('#label_open_deal_thank_you_sell').empty();
			j('#label_open_deal_thank_you_open_rate').empty();			
			j('#label_open_deal_thank_you_rolling_until').empty();
			j('#label_open_deal_thank_you_stop_loss_rate').empty();
			j('#label_open_deal_thank_you_margin').empty();
			
			j('#row_open_deal_thank_you_forward_date').hide();
			j('#row_open_deal_thank_you_rolling_until').hide();
        	
		}
	},
	closeDeal : {
		init : function() { },
		destroy : function() { }
	},
	modifyDeal : {
		init : function() {
			
			j('#section_modify_deal_modify').show();
			
			
			if(!j('#button_modify_deal_accept').hasClass('disabled')) {
				j('#button_modify_deal_accept').addClass('disabled');
			}
			
			
			var dealId = lastDealId;
			
			
			var deal = null;
			var _dealId = null;
			var isStopLossFreezed = false;
			var isTakeProfitFreezed = false;
			var enableAccept = false;
			
			var lastSettings = {
				stopLossRate : null,
				takeProfitRate : null
			};
			
			var lastProposal = {
				currentRate : null,
				stopLossRate : null,
				margin : null,
				takeProfitRate : null,
				takeProfitAmount : null,
				tradableQuoteId : null,
				maxStopLossRate : null,
				minStopLossRate : null,
				maxTakeProfitRate : null,
				minTakeProfitRate : null
			};
			
			var modify = function() {
				
				// show loader
				loader.show();
				jsapi.trading.deals.modifyDeal({
					dealId : _dealId,
					productType : deal.productId,
					stopLoss : lastProposal.stopLossRate,
					takeProfit : lastProposal.takeProfitRate,
					success : function(data) {
						
						loader.hide();
						
						var o = {
							stopLoss : data.slr,
							takeProfit : data.tpr
						};
						
						jsapi.trading.updateDeal(data.dealId, o);
						
						
						
						j('#section_modify_deal_modify').hide();
						j('#section_modify_deal_thank_you').show();
						
						
						// thank you details after modify
						if(data.transId != undefined) {
							j('#label_modify_deal_thank_you_message').html(String.format(getContent("successChangedDealTransaction"), data.dealId, data.transId));
						} else {
							j('#label_modify_deal_thank_you_message').html(String.format(getContent("successChangedDeal"), data.dealId));
						}
						
						j('#label_modify_deal_thank_you_product_id').text(products[data.productType]);
						
						// form
						j('#label_modify_deal_thank_you_deal_id').text(data.dealId);
						j('#label_modify_deal_thank_you_buy_amount').text(data.buyAmount + " " + data.buyCurr); // buy amount
						j('#label_modify_deal_thank_you_sell_amount').text(data.sellAmount + " " + data.sellCurr); // sell amount
						
						if(data.slr != undefined) {
							j('#label_modify_deal_thank_you_stop_loss_rate').html(fractionalFormat(data.slr, data.buyCurr + data.sellCurr));
							j('#label_modify_deal_thank_you_margin').text(data.margin + " " + data.marginCurr);
						}
						
						if(data.tpr != undefined) {
							// take profit rate
							j('#row_modify_deal_thank_you_take_profit_rate').show();
							j('#label_modify_deal_thank_you_take_profit_rate').html(fractionalFormat(data.tpr, data.buyCurr + data.sellCurr));
							
							// take profit amount
							j('#row_modify_deal_thank_you_take_profit_amount').show();
							j('#label_modify_deal_thank_you_take_profit_amount').text(data.tpAmount + " " + data.tpAmountCurr);
						} else {
							j('#row_modify_deal_thank_you_take_profit_rate').hide();
							j('#row_modify_deal_thank_you_take_profit_amount').hide();
						}
						
						silentScroll(true);
						
						
					},
					error : function(error) {
						
						// hide loader
						loader.hide();
						
						if(error != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : (getContent(error) != null ? getContent(error) : getContent("0")),
								actions : [
								   { label : getContent("ok"), fire : function() { 
										getProposal();
										modal.close();
									}, color: "green" }
								],
								overlayAll : true
							});
						}
						
					}
				});
			};
			
			var setProposal = function(data) {
			
				j('#label_modify_deal_debited').html(getContent("yourFreeBalanceIs") + " " + data.freeBalance + " " + accountBaseCurrency + "<br/><b>" + (data.newAccBaseMarginIsCredited ? getContent("credited") : getContent("debited") ) + " " + data.newAccBaseMargin + " " + accountBaseCurrency + "</b>");
				
				// set current rate
				j('#label_modify_deal_current_rate')
					.html(fractionalFormat(data.currentRate, deal.symbol));
				
				if(lastProposal.currentRate != null) {
					j('#label_modify_deal_current_rate')
						.removeClass((data.currentRate != lastProposal.currentRate) ? ( (data.currentRate > lastProposal.currentRate) ? "down" : ((data.currentRate < lastProposal.currentRate) ? "up" : "") ) : "down up")
						.addClass((data.currentRate != lastProposal.currentRate) ? ( (data.currentRate > lastProposal.currentRate) ? "up" : ((data.currentRate < lastProposal.currentRate) ? "down" : "") ) : "");
				}
				
				// set rate direction by deal.buyIsBase direction
				if(deal.buyIsBase) {
					// stop loss rate limitation / direction
					j('#label_modify_deal_stop_loss_direction').text(getContent("below"));
					j('#label_modify_deal_stop_loss_limitation').text(data.minStopLossRate);
					
					// take profit rate limitation / direction
					j('#label_modify_deal_take_profit_direction').text(getContent("above"));
					j('#label_modify_deal_take_profit_limitation').text(data.minTakeProfitRate);
				} else {
					
					// stop loss rate limitation / direction
					j('#label_modify_deal_stop_loss_direction').text(getContent("above"));
					j('#label_modify_deal_stop_loss_limitation').text(data.minStopLossRate);
					
					// take profit rate limitation / direction
					j('#label_modify_deal_take_profit_direction').text(getContent("below"));
					j('#label_modify_deal_take_profit_limitation').text(data.minTakeProfitRate);
				}
				
				// set margin
				if(lastSettings.stopLossRate != null) {
					if(data.margin != null) {
						j('#label_modify_deal_margin').text(data.margin);
					} else { 
						j('#label_modify_deal_margin').empty();
					}
				} else {
					j('#label_modify_deal_margin').empty();
				}
				
				// set take profit amount
				if(lastSettings.takeProfitRate != null) {
					if(data.takeProfitAmount != null) {
						j('#label_modify_deal_take_profit_amount').text(data.takeProfitAmount);
					} else {
						j('#label_modify_deal_take_profit_amount').empty();
					}
				} else {
					j('#label_modify_deal_take_profit_amount').empty();
				}
				
				lastProposal.currentRate = data.currentRate;
				lastProposal.stopLossRate = data.stopLossRate;
				lastProposal.margin = data.margin;
				lastProposal.takeProfitRate = data.takeProfitRate;
				lastProposal.takeProfitAmount = data.takeProfitAmount;
				lastProposal.tradableQuoteId = data.tradableQuoteId;
				lastProposal.maxStopLossRate = data.maxStopLossRate;
				lastProposal.minStopLossRate = data.minStopLossRate;
				lastProposal.maxTakeProfitRate = data.maxTakeProfitRate;
				lastProposal.minTakeProfitRate = data.minTakeProfitRate;
				
				
				
				if(enableAccept) {
					j('#button_modify_deal_accept').removeClass('disabled');
				}
				
				
			};
			
			var getProposal = function() {
				
				var newStopLossRate = j('#input_modify_deal_stop_loss_rate').val().replace(/ /g,"");
				
				lastProposal.takeProfitAmount = null;
				lastProposal.tradableQuoteId = null;
				
				// new stop loss
				if(!isStopLossFreezed) {
					// validate -> entered stopLoss
					if(newStopLossRate.length != 0) {
						if(lastProposal.maxStopLossRate != null && lastProposal.minStopLossRate != null) {
							if(deal.buyIsBase) {
								if(parseFloat(lastProposal.maxStopLossRate) < parseFloat(newStopLossRate) 
										&& parseFloat(lastProposal.minStopLossRate) > parseFloat(newStopLossRate)) {
									// set new stop loss rate to get proposal
									lastSettings.stopLossRate = newStopLossRate;
								} else {
									
									// clear new stop loss
									lastSettings.stopLossRate = null;
									j('#input_modify_deal_stop_loss_rate').val("");
									
									if(parseFloat(lastProposal.maxStopLossRate) > parseFloat(newStopLossRate)) {
										
										var modal = new lightFace({
											title : "",
											message : String.format(getContent("enterStopLossAbove"), lastProposal.maxStopLossRate),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
										
									}
									
									if(parseFloat(lastProposal.minStopLossRate) < parseFloat(newStopLossRate)) {
										
										var modal = new lightFace({
											title : "",
											message : String.format(getContent("enterStopLossBelow"), lastProposal.minStopLossRate),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
										
									}
								}
							} else {
								if(parseFloat(lastProposal.minStopLossRate) < parseFloat(newStopLossRate) 
										&& parseFloat(lastProposal.maxStopLossRate) > parseFloat(newStopLossRate)) {
									// set new stop loss rate to get proposal
									lastSettings.stopLossRate = newStopLossRate;
								} else {
									
									// clear new stop loss
									lastSettings.stopLossRate = null;
									j('#input_modify_deal_stop_loss_rate').val("");
									
									if(parseFloat(lastProposal.minStopLossRate) > parseFloat(newStopLossRate)) {
										
										var modal = new lightFace({
											title : "",
											message : String.format(getContent("enterStopLossAbove"), lastProposal.minStopLossRate),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
										
									}
									
									if(parseFloat(lastProposal.maxStopLossRate) < parseFloat(newStopLossRate)) {
										
										var modal = new lightFace({
											title : "",
											message : String.format(getContent("enterStopLossBelow"), lastProposal.maxStopLossRate),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
										
									}
									
								}
							}
						}						
					} else {
						lastSettings.stopLossRate = null;
					}
				}
				
				// new take profit
				if(!isTakeProfitFreezed) {
					var newTakeProfitRate = j('#input_modify_deal_take_profit_rate').val().replace(/ /g,"");
					if(newTakeProfitRate.length != 0) {
						if(lastProposal.maxTakeProfitRate != null && lastProposal.minTakeProfitRate != null) {
							if(deal.buyIsBase) {
								if(parseFloat(lastProposal.maxTakeProfitRate) > parseFloat(newTakeProfitRate) 
										&& parseFloat(lastProposal.minTakeProfitRate) < parseFloat(newTakeProfitRate)) {
									lastSettings.takeProfitRate = newTakeProfitRate;
								} else {
									
									// clear new take profit
									lastSettings.takeProfitRate = null;
									j('#input_modify_deal_take_profit_rate').val("");
									
									if(parseFloat(lastProposal.maxTakeProfitRate) < parseFloat(newTakeProfitRate)) {
										
										var modal = new lightFace({
											title : "",
											message : String.format(getContent("enterTakeProfitBelow"), lastProposal.maxTakeProfitRate),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
										
									}
									
									if(parseFloat(lastProposal.minTakeProfitRate) > parseFloat(newTakeProfitRate)) {
										
										var modal = new lightFace({
											title : "",
											message : String.format(getContent("enterTakeProfitAbove"), lastProposal.minTakeProfitRate),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
										
									}

								}
								
							} else {
								if(parseFloat(lastProposal.minTakeProfitRate) > parseFloat(newTakeProfitRate) 
										&& parseFloat(lastProposal.maxTakeProfitRate) < parseFloat(newTakeProfitRate)) {
									lastSettings.takeProfitRate = newTakeProfitRate;
								} else {
									
									// clear new take profit
									lastSettings.takeProfitRate = null;
									j('#input_modify_deal_take_profit_rate').val("");
									
									if(parseFloat(lastProposal.minTakeProfitRate) < parseFloat(newTakeProfitRate)) {
										
										var modal = new lightFace({
											title : "",
											message : String.format(getContent("enterTakeProfitBelow"), lastProposal.minTakeProfitRate),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
										
									}
									
									if(parseFloat(lastProposal.maxTakeProfitRate) > parseFloat(newTakeProfitRate)) {
										
										var modal = new lightFace({
											title : "",
											message : String.format(getContent("enterTakeProfitAbove"), lastProposal.maxTakeProfitRate),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
										
									}
								}
							}
						}
						
						
					} else {
						lastSettings.takeProfitRate = null;
					}
				}
				
				
				// abandon and get proposal
				jsapi.trading.proposal.abandon();
				jsapi.trading.proposal.getProposal({
					operationType : "modify",
					productId : deal.productId,
					dealId : _dealId,
					stopLossRate : lastSettings.stopLossRate,
					takeProfitRate : lastSettings.takeProfitRate,
					success : function(data) {
						
						setProposal(data);
						
					},
					error : function(error) {
						
						if(error == "GeneralError") {
							
							// stop proposal
							jsapi.trading.proposal.abandon();
							
							// clear entered values
							lastSettings.stopLossRate = null;
							lastSettings.takeProfitRate = null;
							
							j('#label_modify_deal_current_rate')
								.empty()
								.removeClass('up down')
								.text("N/A");
							
							j('#input_modify_deal_stop_loss_rate').val("");
							j('#label_modify_deal_margin').empty();
							j('#input_modify_deal_take_profit_rate').val("");
							j('#label_modify_deal_take_profit_amount').empty();
							
						}
						
						if(error != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : getContent(error),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
						
						switch(parseInt(error)) {
							case 1: {
								// TODO
								break;
							}
							case 2: {
								// TODO
								break;
							}
							case 21: {
								
								// stop proposal
								jsapi.trading.proposal.abandon();
								
								// clear entered values
								lastSettings.stopLossRate = null;
								lastSettings.takeProfitRate = null;
								
								j('#label_modify_deal_current_rate')
									.empty()
									.removeClass('up down')
									.text("N/A");
								
								j('#input_modify_deal_stop_loss_rate').val("");
								j('#label_modify_deal_margin').empty();
								j('#input_modify_deal_take_profit_rate').val("");
								j('#label_modify_deal_take_profit_amount').empty();
								
								
								getProposal();
								
								break;
							}
							case 27: {
							
								// stop proposal
								jsapi.trading.proposal.abandon();
								
								// clear entered values
								lastSettings.stopLossRate = null;
								lastSettings.takeProfitRate = null;
								
								j('#label_modify_deal_current_rate')
									.empty()
									.removeClass('up down')
									.text("N/A");
								
								j('#input_modify_deal_stop_loss_rate').val("");
								j('#label_modify_deal_margin').empty();
								j('#input_modify_deal_take_profit_rate').val("");
								j('#label_modify_deal_take_profit_amount').empty();
								
								screenManager.show(screens.myPositions);
								
								break;
							}
							case 38: {
							
								// stop proposal
								jsapi.trading.proposal.abandon();
								
								// clear entered values
								lastSettings.stopLossRate = null;
								lastSettings.takeProfitRate = null;
								
								j('#label_modify_deal_current_rate')
									.empty()
									.removeClass('up down')
									.text("N/A");
								
								j('#input_modify_deal_stop_loss_rate').val("");
								j('#label_modify_deal_margin').empty();
								j('#input_modify_deal_take_profit_rate').val("");
								j('#label_modify_deal_take_profit_amount').empty();
								
								break;
							}
						}
						
						
						
					}
				}, 2000);
				
			};
			
			var setDefault = function() {
				
				// set default stop loss
				if(deal.stopLoss != undefined) {
					j('#input_modify_deal_stop_loss_rate').val(deal.stopLoss);
        			lastSettings.stopLossRate = deal.stopLoss;
        		}
				// set default take profit
				if(deal.takeProfit != undefined) {
					j('#input_modify_deal_take_profit_rate').val(deal.takeProfit);
					lastSettings.takeProfitRate = deal.takeProfit;
				}
				
				getProposal();
				
			};
			
			// start here
			if(dealId != undefined) {
			
				jsapi.accounts.user.getUserInfo({
					success: function (b) {
					
						isEnableFractionalPips = b.isEnableFractionalPips;
						accountBaseCurrency = b.accountBaseCurrency;
						withdrawalAvailable = b.withdrawalAvailable;
						allowWithdrawal = b.allowWithdrawal;
						allowDeposit = b.allowDeposit; 
						isActive = b.isActive;
						isDemo = b.isDemo;
						userStatus = b.userStatus;
						needToPassTest = b.needToPassTest;
						isSuitabilityTestPassed = b.isSuitabilityTestPassed;
						regionId = b.regionId;
						regTicketId = b.regTicketId;
						
					
						deal = jsapi.trading.tradingInfo.deals[dealId];
						_dealId = dealId;
						
						if(deal != undefined) {
							
							// product id
							j('#label_modify_deal_product_id')
								.empty()
								.text(products[deal.productId]);
							
							j('#label_modify_deal_message')
								.empty()
								.html((deal.productId == 2 ? String.format(getContent("modifyDealDescription"), dealId, deal.buy, deal.sell, deal.amount, deal.openRate) + ", " + getContent("forwardDate") + " " + deal.expiryDate : String.format(getContent("modifyDealDescription"), dealId, deal.buy, deal.sell, deal.amount, deal.openRate)));
							
							if(deal.productId == 3){								
								j('#label_modify_deal_rolling_until').text(deal.expiryDate);
								j('#row_modify_deal_rolling_until').show()
							}
							if(deal.productId == 2 || deal.productId==4){								
								j('#row_modify_deal_rolling_until').hide()
							}
							// current rate
							j('#label_modify_deal_current_rate')
								.empty()
								.removeClass('up down')
								.text(getContent("loading"));
							
							// stop loss
							j('#input_modify_deal_stop_loss_rate')
								.val('')
								.unbind('keyup')
								.keyup(function() {
									var val = j(this).val();
									if(isNaN(val)){
										 val = val.replace(/[^0-9\.]/g,'');
										 if(val.split('.').length>2) val =val.replace(/\.+$/,"");
									}
									j(this).val(val); 
								})
								.unbind('focusin')
								.focusin(function() {
									isStopLossFreezed = true;
									
									enableAccept = false;
									j('#button_modify_deal_accept').addClass('disabled');
									
								})
								.unbind('focusout')
								.focusout(function() {
									isStopLossFreezed = false;
									getProposal();
							});
							
							// margin
							j('#label_modify_deal_margin')
								.empty();
								
							// take profit -> rate
							j('#input_modify_deal_take_profit_rate')
								.val('')
								.unbind('keyup')
								.keyup(function() {
									var val = j(this).val();
									if(isNaN(val)){
										 val = val.replace(/[^0-9\.]/g,'');
										 if(val.split('.').length>2) val =val.replace(/\.+$/,"");
									}
									j(this).val(val); 
								})
								.unbind('focusin')
								.focusin(function() {
									isTakeProfitFreezed = true;
									
									enableAccept = false;
									j('#button_modify_deal_accept').addClass('disabled');
								})
								.unbind('focusout')
								.focusout(function() {
									isTakeProfitFreezed = false;
									getProposal();
							});
							
							
							// take profit -> profit
							j('#label_modify_deal_take_profit_amount')
								.empty();
							
							
							// calculate
							j('#button_modify_deal_calculate')
							.unbind('click')
							.click(function() {
								
								j('#input_modify_deal_stop_loss_rate, #input_modify_deal_take_profit_rate').trigger("focusout");
								
								setTimeout(function() {
									
									if(lastSettings.stopLossRate != null) {
										// abandon proposal
										jsapi.trading.proposal.abandon();
										
										enableAccept = true;
										getProposal();
										
									} else {
										var modal = new lightFace({
											title : "",
											message : getContent("enterStopLossRate"),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
									}
									
								}, 150);
							});
							
							// accept
							j('#button_modify_deal_accept')
							.unbind('click')
							.click(function(){
								
								if(!j(this).hasClass('disabled')) {
								
									setTimeout(function() {
										
										if(lastSettings.stopLossRate != null) {
											// abandon proposal
											jsapi.trading.proposal.abandon();
											
											modify();
											
										} else {
											var modal = new lightFace({
												title : "",
												message : getContent("enterStopLossRate"),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
										}
										
									}, 150);
								
								}
									
							});
							
							
							// start here
							setDefault();
							
							
						}
					
					
					},
					error: function (a) {
				
						if(a != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : getContent(a),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
					
					}
				});
			
			}
		},
		destroy : function() {
			
			// abandon proposal
			jsapi.trading.proposal.abandon();
			
			// clear thank you elements
			j('#section_modify_deal_thank_you').hide();
			
			// thank you details after modify
			j('#label_modify_deal_thank_you_message').empty();
			j('#label_modify_deal_thank_you_product_id').empty();
			j('#label_modify_deal_thank_you_deal_id').empty();
			j('#label_modify_deal_thank_you_buy_amount').empty();
			j('#label_modify_deal_thank_you_sell_amount').empty();
			j('#label_modify_deal_thank_you_stop_loss_rate').empty();
			j('#label_modify_deal_thank_you_margin').empty();
			
			j('#row_modify_deal_thank_you_take_profit_rate').hide();
			j('#label_modify_deal_thank_you_take_profit_rate').empty();
			j('#row_modify_deal_thank_you_take_profit_amount').hide();
			j('#label_modify_deal_thank_you_take_profit_amount').empty();
			
		}
	},
	customize : {
		lastSelected : null,
		init : function() {
			
			// customize pairs
			this.bind();
			
			j('#button_customize_add')
				.unbind('click')
				.click(function() {
					handle.customize.add();
				});
			
			
			j('#link_customize_reset')
				.unbind('click')
				.click(function() {
					handle.customize.setDefaults();
				});
			
			
		},
		setDefaults : function() {
			
			loader.show();
			
			jsapi.trading.setPersonalCurrencyPairs({ 
				currencyPairs : [],
				success : function(data) {
					loader.hide();
					
					jsapi.trading.setCurrencyPairs(data.currencyPairs);
					
					// refresh customize
					handle.customize.bind();
				},
				error : function(error) {
					loader.hide();
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
			
		},
		order : function() {
			
			var d = [];
			
			j('#list_personal_currency_pairs li')
				.each(function(i, el) {
					d.push(j(el).attr('keyvalue'));
			});
			
			if(d.length != 0) {
				
				var t = jsapi.trading.tradingInfo.currencyPairs;
				var q = [];
				
				/*
				for(var k in t) {
					var c = t[k];
					if(c.isCommodity) {
						if(c.isPersonal) {
							q.push(c.baseCurrency + c.nonBaseCurrency);
						}
					}
				}
				*/
				
				// push by order
				for(var y = 0; y < d.length; y++) {
					q.push(d[y]);
				}
				
				loader.show();
				jsapi.trading.setPersonalCurrencyPairs({ 
					currencyPairs : q,
					success : function(data) {
						loader.hide();
						
						// must to change to get currencyPairs by order
						jsapi.trading.setCurrencyPairs(data.currencyPairs);
						
					},
					error : function(error) {
						
						loader.hide();
						
						if(error != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : getContent(error),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
						
					}
				});
				
			}
			
		},
		add : function() {
			
			var a, b = null;
			a = j('#select_customize_buy').val();
			b = j('#select_customize_sell').val();
			
			var currencyPair = jsapi.trading.getCurrencyPairSettings(a, b);
			if(currencyPair != null) {
				
				var d = [];
				
				j('#list_personal_currency_pairs li')
					.each(function(i, el) {
						d.push(j(el).attr('keyvalue'));
				});
				
				if(d.length > 0 
						&& d.length <= 10) {
					
					var findIn = function(k) {
						var u = false;
						for(var i = 0; i < d.length; i++) {
							if(d[i] == k) {
								
								u = true;
								break;
							}
						}
						return u;
					};
					
					// check for already exists currency pair in list 
					if(findIn(currencyPair.baseCurrency + currencyPair.nonBaseCurrency)) {
						
						var modal = new lightFace({
							title : "",
							message : String.format(getContent("currencyPairIsAlreadyExistsInList"), currencyPair.baseCurrency, currencyPair.nonBaseCurrency),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
						
					} else {
						
						// push to the list
						d.push(currencyPair.baseCurrency + currencyPair.nonBaseCurrency);
						
						var t = jsapi.trading.tradingInfo.currencyPairs;
						var q = [];
						for(var k in t) {
							var c = t[k];
							//if(!c.isCommodity) {
							if(c.instrumentType == 1) {
								if(findIn(c.baseCurrency + c.nonBaseCurrency)) {
									q.push(c.baseCurrency + c.nonBaseCurrency);
								}
							} else {
								if(c.isPersonal) {
									q.push(c.baseCurrency + c.nonBaseCurrency);
								}
							}
						}
						
						loader.show();
						jsapi.trading.setPersonalCurrencyPairs({ 
							currencyPairs : q,
							success : function(data) {
								loader.hide();
								
								// must to change to get currencyPairs by order
								jsapi.trading.setCurrencyPairs(data.currencyPairs);
								
								// refresh customize
								handle.customize.bind();
								
							},
							error : function(error) {
								
								loader.hide();
								
								if(error != errors.applicationNotLoggedIn) {
									var modal = new lightFace({
										title : "",
										message : getContent(error),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
								}
								
							}
						});
						
					}
					
				} else {
					
					var modal = new lightFace({
						title : "",
						message : getContent("maxCurrencyPairs"),
						actions : [
						   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
						],
						overlayAll : true
					});
					
				}
				
				
			} else {
				
				var modal = new lightFace({
					title : "",
					message : String.format(getContent("currencyPairNotAvailableForTrading"), a, b),
					actions : [
					   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
					],
					overlayAll : true
				});
				
			}
			
		},
		bind : function() {
			
			var pair = null;
			if(lastPair != null) {
				pair = lastPair.pair;
			} else {
				pair = jsapi.trading.tradingInfo.instrumentTypes[1].pair;
			}
			
			var sell = pair.substr(0, 3);
            var buy = pair.substr(3, 3);
			
			
			var setBuy = function(a, freshContracts) {
				
				var currencies = [];
					currencies = jsapi.trading.tradingInfo.currencies;
					
				var lastSelected = null;
				
				j('#select_customize_buy')
	            	.empty()
	        		.unbind('change')	
	        		.change(function() {
	            		if(j(this).val() != j('#select_customize_sell').val()) {
	            			lastSelected = j(this).val();
	            		} else {
	            			setSell(lastSelected, true);
	            			lastSelected = j(this).val();
	            		}
	            	});
	            	
	            if(currencies.length != 0) {
	            	var options = j('#select_customize_buy')[0].options;
	            	var x = 0;
	            	var s = null;
	            	for(var i = 0; i < currencies.length; i++) {
						if(currencies[i].instrumentType == 1) {
							var option = new Option(currencies[i].symbol, currencies[i].symbol);
							try {
								 options.add(option);
							} catch (ex) {
								options.add(option, null);
							}
							
							if(currencies[i].symbol == a) {
								s = x;
							}
							x++;
						}
	            	}
	            	
	            	if(s != null) {
	            		j('#select_customize_buy')[0].selectedIndex = s;
	            		lastSelected = j('#select_customize_buy').val();
	            	}
	            }
	            
			};
			
			var setSell = function(b, freshContracts) {
				
				var currencies = [];
					currencies = jsapi.trading.tradingInfo.currencies;
					
				var lastSelected = null;
				
				j('#select_customize_sell')
					.empty()
	        		.unbind('change')
	            	.change(function() {
	            		if(j(this).val() != j('#select_customize_buy').val()) {
	            			lastSelected = j(this).val();
	            		} else {
	            			setBuy(lastSelected, true);
	            			lastSelected = j(this).val();
	            		}
	            	});
	            
	            if(currencies.length != 0) {
	            	var options = j('#select_customize_sell')[0].options;
	            	var x = 0;
	            	var s = null;
	            	for(var i = 0; i < currencies.length; i++) {
						if(currencies[i].instrumentType == 1) {
							var option = new Option(currencies[i].symbol, currencies[i].symbol);
							 try {
								 options.add(option);
							} catch (ex) {
								options.add(option, null);
							}
							
							if(currencies[i].symbol == b) {
								s = x;
							}
							x++;
						}
	            	}
	            	
	            	if(s != null) {
	            		j('#select_customize_sell')[0].selectedIndex = s;
	            		lastSelected = j('#select_customize_sell').val();
	            	}
	            }
	            
			};
			
			
			setBuy(buy);
			setSell(sell);
			
			
			var h = function(isUpdate) {
				var l = j('#list_personal_currency_pairs').find('li');
				l.removeClass('disabled-sort-up disabled-sort-down disabled first-item');
				
				// refresh function
				if(l.length > 1) {
					l.first()
					 .addClass('disabled-sort-up first-item');
					
					l.last()
					 .addClass('disabled-sort-down');
				} else {
					l.addClass('disabled first-item');
				}
				
				if(isUpdate) {
					handle.customize.order();
				}
				
			};
			
			var t = jsapi.trading.tradingInfo.currencyPairs;
			if(t != null) {
			
				// clear prev list
				j('#list_personal_currency_pairs').empty();
				
				var p = 0;
				for (var k in t) {
					var c = t[k];
					
					if (c.instrumentType == 1 
							&& c.isPersonal == true) {
						var s = c.baseCurrency + c.nonBaseCurrency;
						var o = j("<li class=\"currency-pair-" + s + " " + (p == 0 ? "first-item" : "" ) +  "\" keyvalue=\"" + s + "\">" +
								"<div class=\"left-side\"><span>" + c.baseCurrency + " / " + c.nonBaseCurrency + "</span></div>" +
								"<div class=\"right-side\">" +
									"<div class=\"cell-button-up\"><a class=\"button-up button-white ui-button-short\" keyvalue=\"" + s + "\"><i class=\"anchor-order-up\"></i></a></div>" +
									"<div class=\"cell-button-down\"><a class=\"button-down button-white ui-button-short\" keyvalue=\"" + s + "\"><i class=\"anchor-order-down\"></i></a></div>" +
									"<a class=\"button-remove\" keyvalue=\"" + s + "\"><i class=\"anchor-remove\"></i></a>" +
								"</div>" +
						"</li>").appendTo('#list_personal_currency_pairs');
						
						p++;
						
						o.find('.button-remove')
						    .click(function() {
						    	var u = j(this).attr('keyvalue');
								var current = j('.currency-pair-' + u);
								current.fadeOut("slow", function() {
									
									j(this).remove();
									// call for refresh
									h(true);
								});
						});
						
						o.find('.button-down')
							.click(function() {
								var u = j(this).attr('keyvalue');
								var current = j('.currency-pair-' + u);
								
								
								if(handle.customize.lastSelected != null 
										&& handle.customize.lastSelected != current) {
									handle.customize.lastSelected.removeClass('selected');
								}
								handle.customize.lastSelected = current;
								
								current.addClass('selected');
								
								current
									.next()
									.after(current);
								
								// call for refresh
								h(true);
						});
						
						o.find('.button-up')
							.click(function() {
								var u = j(this).attr('keyvalue');
								var current = j('.currency-pair-' + u);
								
								
								if(handle.customize.lastSelected != null 
										&& handle.customize.lastSelected != current) {
									handle.customize.lastSelected.removeClass('selected');
								}
								handle.customize.lastSelected = current;
								
								current.addClass('selected');
								
								current
									.prev()
									.before(current);
								
								// call for refresh
								h(true);
						});
					}
					
					
				}
				
				// refresh
				h(false);
				
			}
			
		},
		destroy : function() {
			
		}
	},
	video : {
		lastVideoFrame : null,
		init: function() {
			
			j.ajax({
				url : "https://gdata.youtube.com/feeds/api/users/easyforexww/uploads?alt=json",
				data : {},
				dataType : "jsonp",
				jsonp : "callback",
				timeout : 15000,
				success : function(data, status) {
					if(data.feed.entry.length != 0) {
						if(data.feed.entry[0].id != undefined) {
						
							var videoUrl = data.feed.entry[0].id.$t;
							videoUrl = videoUrl.substring(videoUrl.lastIndexOf("/"), videoUrl.length);
							
							handle.video.lastVideoFrame = j("<iframe src=\"https://www.youtube.com/embed" + videoUrl + "?controls=0&showinfo=0&showsearch=0&modestbranding=1&wmode=opaque\" frameborder=\"0\"></iframe>")
								.appendTo('#embed_video');
						
							if(jQuery('html').hasClass('portrait')) {
								handle.video.lastVideoFrame.css({ width : 308, height: 173 })
							} else {
								handle.video.lastVideoFrame.css({ width : 468, height: 263 })
							}
							
						}
					}
				},
				error: function(XHR, textStatus, errorThrow) {
					
					var modal = new lightFace({
						title : "",
						message : getContent("requestTimeOut"),
						actions : [
						   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
						],
						overlayAll : true
					});
					
				}
			});
		},
		destroy : function() {
			j('#embed_video').empty();
			this.lastVideoFrame = null;
		},
		orientationchange : function() {
			
			/*
			if(this.lastVideoFrame != null) {
				if(jQuery('html').hasClass('portrait')) {
					handle.video.lastVideoFrame.css({ width : 308, height: 173 })
				} else {
					handle.video.lastVideoFrame.css({ width : 468, height: 263 })
				}
			}
			*/
			
		}
	},
	tweets : {
		init : function () {
			
			j.ajax({
				url : "https://api.twitter.com/1/statuses/user_timeline.json?screen_name=easyforexdr", // "http://twitter.com/statuses/user_timeline/easyforexdr.json",
				data : {
					count : 20
				},
				dataType : "jsonp",
				jsonp : "callback",
				timeout : 15000,
				success : function(data, status) {
					
					j('#list_tweets').empty();
					if(data.length != 0) {					
						for(var i = 0; i < data.length; i++) {
							var text = data[i].text.replace(/((https?|s?ftp|ssh)\:\/\/[^"\s\<\>]*[^.,;'">\:\s\<\>\)\]\!])/g, function(url) {
								  return "<a href=\"" + url + "\" target=\"_blank\">" + url + "</a>";
							});
							
							// " + (( i%2 ) ? "ui-seperator" : "") + "
							j("<li>" +
									"<span>" + text + "</span>" +
									"<div><em>" + relativeTime(data[i].created_at) + "</em></div>" +
							"</li>").appendTo('#list_tweets');
						}
					} else {
						j("<li>No Data</li>").appendTo('#list_tweets');
					}
					
				},
				error: function(XHR, textStatus, errorThrow) {
					
					var modal = new lightFace({
						title : "",
						message : getContent("requestTimeOut"),
						actions : [
						   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
						],
						overlayAll : true
					});
					
				}
			});
			
		},
		destroy : function() {
			
		}
	},
	calendar : {
		daysFromToday : 0,
		lastItem : null,
		lastActiveTab : null,
		types : {
			yesterday : -1,
			today : 0,
			tomorrow : +1
		},
		init : function() {
		
			j('#section_calendar').show();
			j('#section_calendar_details').hide();
			
			
			j('ul#list_buttons_calendar li a').removeClass('active');
			j('ul#list_buttons_calendar li a').each(function(i, el){
				if(i == 1) {
					j(el).addClass('active');
					handle.calendar.lastActiveTab = j(el);
				}
				j(el)
					.unbind('click')
					.click(function() {
					if(handle.calendar.lastActiveTab != null && handle.calendar.lastActiveTab != j(this)) {
						handle.calendar.lastActiveTab.removeClass('active');
					}
					handle.calendar.lastActiveTab = j(this); 
					
					j(this).addClass('active');
					handle.calendar.bind(handle.calendar.types[j(this).attr('name')]);
					
				});
			});
		
			handle.calendar.bind(handle.calendar.types.today);
			
		},
		bind : function(daysFromToday) {
		
			var localOffset = new Date().getTimezoneOffset();
			
			jsapi.marketInfo.calendar.getFinancialCalendarData({
				localOffset : localOffset,
				daysFromToday : daysFromToday,
				success: function(data) {
					
					var periodTitle = "";
					if(daysFromToday == -1) {
						periodTitle = getContent("yesterdaysCalendar");
					}
					if(daysFromToday == 0) {
						periodTitle = getContent("todaysCalendar");
					}
					if(daysFromToday == +1) {
						periodTitle = getContent("tomorrowsCalendar");
					}
					
					
					j('#header_calendar').text(periodTitle);
					
					j('#list_calendar').empty();
					if(data.indicators.length != 0) {
						for(var i = 0; i < data.indicators.length; i++) {
						
							var f = j("<li keyvalue=\"" + i + "\">" +
								"<div class=\"col first-item\"><span class=\"" + (data.indicators[i].importance == 1 ? "icon-high" : data.indicators[i].importance == 2 ? "icon-medium" : "icon-low") + "\"></span><b>" + (data.indicators[i].time != null ? data.indicators[i].time.substring(data.indicators[i].time.lastIndexOf(":"), "") : "") + "</b></div>" +
								"<div class=\"col\"><span>" + data.indicators[i].location + "</span></div>" +
								"<div class=\"col last-item\"><span>" + data.indicators[i].indicatorName + "</span></div>" +
							"</li>").appendTo('#list_calendar');
							
							f.click(function() {
								
								var y = j(this).attr("keyvalue");
								
								j('#section_calendar').hide();
								j('#section_calendar_details').show();
								
								/*
								if(myScroll != undefined) {
									myScroll.scrollToPage(1, 0, 200);
								}
								*/
								
								// details
								j('#label_calendar_indicator_name')
									.removeClass('icon-high icon-medium icon-low')
									.addClass((data.indicators[y].importance == 1 ? "icon-high" : data.indicators[y].importance == 2 ? "icon-medium" : "icon-low"))
									.text(data.indicators[y].indicatorName);
								
								j('#label_calendar_time').text((data.indicators[y].time != null ? data.indicators[y].time.substring(data.indicators[y].time.lastIndexOf(":"), "") : ""));
								j('#label_calendar_calendar').text((data.indicators[y].calendar != null ? data.indicators[y].calendar : ""));
								
								j('#label_calendar_period').text((data.indicators[y].period != null ? data.indicators[y].period : ""));
								j('#label_calendar_unit').text((data.indicators[y].unit != null ? data.indicators[y].unit : ""));
								j('#label_calendar_forecast').text((data.indicators[y].valueForecast != null ? data.indicators[y].valueForecast : ""));
								j('#label_calendar_previous').text((data.indicators[y].valuePrevious != null ? data.indicators[y].valuePrevious : ""));
								j('#label_calendar_actual').text((data.indicators[y].valueActual != null ? data.indicators[y].valueActual : ""));
								j('#label_calendar_description').text((data.indicators[y].description != null ? data.indicators[y].description : ""));
								
								
							});
							
						}
						
					} else {
						j("<li><div class=\"col\">" + getContent("noData") + "</div></li>").appendTo('#list_calendar');
						
					}
				},
				error : function(error) {
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
		},
		destroy : function() {
			
		}
	},
	outlook : {
		lastActiveTab : null,
		types : {
			daily : "DailyOutlook",
			weekly : "WeeklyOutlook"
		},
		init : function() {
			
			j('ul#list_buttons_outlook li a').removeClass('active');
			j('ul#list_buttons_outlook li a').each(function(i, el){
				if(i == 0) {
					j(el).addClass('active');
					handle.outlook.lastActiveTab = j(el);
				}
				j(el)
					.unbind('click')
					.click(function() {
					if(handle.outlook.lastActiveTab != null && handle.outlook.lastActiveTab != j(this)) {
						handle.outlook.lastActiveTab.removeClass('active');
					}
					handle.outlook.lastActiveTab = j(this); 
					
					j(this).addClass('active');
					handle.outlook.bind(handle.outlook.types[j(this).attr('name')]);
					
				});
			});
			
			this.bind(handle.outlook.types.daily);
			
		},
		bind : function(type) {
			
			jsapi.marketInfo.outlook.getOutlookContent({
				culture : "int-en",
				type : type,
				success : function(data) {
					
					j('#container_outlook').empty();
					if(data.outlooks.length != 0) {
						j("<span>" + data.outlooks[0].title + "</span>" +
							/*"<span>" + data.outlooks[0].publishDate + "</span>" +*/ 
							"<div>" + data.outlooks[0].body + "</div>").appendTo('#container_outlook');
					} else {
						j("<div>" + getContent("noData") + "</div>").appendTo('#container_outlook');
					}
					
					
				},
				error : function(error) {
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
			
		},
		destroy : function() {
			
		}
	},
	reutersNews : {
		init : function() {
		
			j('#section_reuters_news').show();
			j('#section_reuters_news_details').hide();
			
			this.bind();
		},
		bind : function() {
			jsapi.marketInfo.reutersNews.getReutersNews({
				success : function(data) {
					
					j('#list_reuters_news').empty();
					if(data.newsList.length != 0) {
						for(var i = 0; i < data.newsList.length; i++) {
							var f = j("<li keyvalue=\"" + data.newsList[i].itemId + "\"><span>" + data.newsList[i].articleDate + "</span><a>" + data.newsList[i].articleHeading + "</a></li>").appendTo("#list_reuters_news");
							f.click(function() {
								
								var itemId = j(this).attr("keyvalue");
								jsapi.marketInfo.reutersNews.getReutersNewsItem({
									itemId : itemId,
									success : function(data) {
										
										
										j('#section_reuters_news').hide();
										j('#section_reuters_news_details').show();
										
										
										// details
										j('#label_reuters_news_date').text(data.article.articleDate);
										j('#label_reuters_news_title').text(data.article.articleHeading);
										j('#label_reuters_news_content').html(data.article.articleBody);
										
									},
									error : function(error) {
										
										if(error != errors.applicationNotLoggedIn) {
											var modal = new lightFace({
												title : "",
												message : getContent(error),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
										}
										
									} 
								});
								
							});
						}
					} else {
						j("<li>" + getContent("noData") + "</li>").appendTo("#list_reuters_news");
					}
					
					
				},
				error : function(error) {
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
			
		},
		destroy : function() {
			
		}
	},
	marketNews : {
		init : function() {
			this.bind();
		},
		bind : function() {
			jsapi.marketInfo.marketNews.getMarketNews({
				identity : null,
				count : 40,
				success : function(data) {
					
					j("#list_market_news").empty();
					if(data.newsItems.length != 0) {
						for(var i = 0; i < data.newsItems.length; i++) {
							j("<li><span>" + data.newsItems[i].publishDate + "</span><b>" + data.newsItems[i].article + "</b></li>").appendTo("#list_market_news");
						}
					} else {
						j("<li>" + getContent("noData") + "</li>").appendTo("#list_market_news");
					}
					
				},
				error : function(error) {
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							    { 
									label : getContent("ok"), 
									fire : function() { 
										if(error == "800") {
											modal.close();
											screenManager.show(screens.quotes);
										} else {
											modal.close() 
										}
									}, 
									color: "green" 
								}
							],
							overlayAll : true,
							close : function() {
								if(error == "800") {
									screenManager.show(screens.quotes);
								}
							}
						});
					}
					
				}
			});
			
		},
		destroy : function() {
		
		}
	},
	technicalAnalysis : {
		init : function() {
		
			j('#section_technical_analysis').show();
			j('#section_technical_analysis_details').hide();
			
			this.bind();
		},
		bind : function() {
		
			jsapi.marketInfo.technicalAnalysis.getTechnicalAnalysis({
				culture : 'int-en',
				success : function(data) {
					
					j("#list_technical_analysis").empty();
					if(data.articles.length != 0) {
						var t = 0;
						for(var i = 0; i < data.articles.length; i++) {
							
							var f = j("<li keyvalue=\"" + data.articles[i].symbol + "\" " + ( t%2 ? "class=\"odd\"" : "") + ">" +
								"<div class=\"col first-item\">" +
									"<span>" + getContent("ticker") + "</span><b>" + data.articles[i].tickerValue + "</b>" +
								"</div>" +
								"<div class=\"col\">" +
									"<span>" + getContent("date") + "</span><b>" + data.articles[i].date + "</b>" +
								"</div>" +
								"<div class=\"col\">" +
									"<span>" + getContent("time") + "</span><b>" + data.articles[i].time + "</b>" +
								"</div>" +
								"<div class=\"middle\">" +
									"<span>" + getContent("title") + "</span><b>" + data.articles[i].title + "</b>" +
								"</div>" +
								"<div class=\"col first-item\">" +
									"<span>" + getContent("oneWeek") + "</span><b><i class=\"" + (data.articles[i].oneWeek < 0 ? "red-arrow" : (data.articles[i].oneWeek == 0 ? "grey-arrow" : "green-arrow")) + "\">&nbsp;</i></b>" +
								"</div>" +
								"<div class=\"col\">" +
									"<span>" + getContent("oneMonth") + "</span><b><i class=\"" + (data.articles[i].oneMonth < 0 ? "red-arrow" : (data.articles[i].oneMonth == 0 ? "grey-arrow" : "green-arrow")) + "\">&nbsp;</i></b>" +
								"</div>" +
								"<div class=\"col\">" +
									"<span>" + getContent("product") + "</span><b>" + data.articles[i].product + "</b>" +
								"</div>" +
							"</li>").appendTo("#list_technical_analysis");
							
							f.click(function() {
								
								var symbol = j(this).attr("keyvalue");
								jsapi.marketInfo.technicalAnalysis.getTechnicalAnalysisDetails({
									culture : 'int-en',
									symbol : symbol,
									success : function(data) {
									
										j('#image_technical_analysis_details').empty();
										
										j('#section_technical_analysis').hide();
										j('#section_technical_analysis_details').show();
										
										
										// details
										j('#label_technical_analysis_details_title').text(data.article.title);
										j('#label_technical_analysis_details_date').text(data.article.date);
										j('#label_technical_analysis_details_time').text(data.article.time);
										
										j('#label_technical_analysis_details_one_week')
											.removeClass('red-arrow green-arrow grey-arrow')
											.addClass((data.article.oneWeek < 0 ? "red-arrow" : (data.article.oneWeek == 0 ? "grey-arrow" : "green-arrow")));
											
										j('#label_technical_analysis_details_one_month')
											.removeClass('red-arrow green-arrow grey-arrow')
											.addClass((data.article.oneMonth < 0 ? "red-arrow" : (data.article.oneMonth == 0 ? "grey-arrow" : "green-arrow")));
										
										j('#label_technical_analysis_details_content').html(data.article.content);
										j('#label_technical_analysis_details_ticker').text(data.article.tickerValue);
										
										
										
										var isLoadEnabled = true;
										if(isLoadEnabled) {
										
											isLoadEnabled = false;
										
											var img = new Image();
											if(jQuery('html').hasClass('portrait')) {
												img.width = 300;
											}
											j(img).load(function () {
											  // set the image hidden by default    
											  j(this).hide();
											  j('#image_technical_analysis_details')
												.append(this);
												// fade our image in to create a nice effect
												j(this).fadeIn();
												isLoadEnabled = true;
											})
											.error(function () {
												isLoadEnabled = true;
											})
											.attr("src", common.jsapiUrl + "tradingcentralimage/" + data.article.fileName);
										
										}
										
										
										
										
									},
									error : function(error) {
										
										if(error != errors.applicationNotLoggedIn) {
											var modal = new lightFace({
												title : "",
												message : getContent(error),
												actions : [
												   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
												],
												overlayAll : true
											});
										}
										
									}
								});
								
							});
							
							t++;
						}
					} else {
						j("<li>" + getContent("noData") + "</li>").appendTo("#list_technical_analysis");
					}
					
				},
				error : function(error) {
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							    { 
									label : getContent("ok"), 
									fire : function() { 
										if(error == "800") {
											modal.close();
											screenManager.show(screens.quotes);
										} else {
											modal.close() 
										}
									}, 
									color: "green" 
								}
							],
							overlayAll : true,
							close : function() {
								if(error == "800") {
									screenManager.show(screens.quotes);
								}
							}
						});
					}
					
				}
			});
			
		},
		destroy : function() {
			j('#section_technical_analysis_details').hide();
			j('#section_technical_analysis').show();
		}
	},
	research : {
		init : function() {
		
			//j("#list_research").accordion({ active: false });
			
			// research accordion fix
			//j('#list_research').accordion( "destroy" );
			j('#list_research').accordion({
				collapsible: true,
				active: false, 
				header : 'a.ui-tab',
				autoheight : false,
				animated: false,
				alwaysOpen: false,
				clearStyle: true,
				change: function(event, ui) {
					
					if(ui.newHeader.hasClass('tweets')) 
						handle.tweets.init();
					
					if(ui.newHeader.hasClass('video'))
						screenManager.show(screens.video);
					
					if(ui.newHeader.hasClass('calendar'))
						screenManager.show(screens.calendar);
					
					if(ui.newHeader.hasClass('outlook'))
						screenManager.show(screens.outlook);
					
					if(ui.newHeader.hasClass('reuters-news'))
						screenManager.show(screens.reutersNews);
					
					if(ui.newHeader.hasClass('technical-analysis'))
						screenManager.show(screens.technicalAnalysis);
					
					if(ui.newHeader.hasClass('market-news'))
						screenManager.show(screens.marketNews);
					
				}
			});
			
		},
		destroy : function() {
			j('#list_research').accordion( "destroy" );
		}
	},
	accountSummary : {
		init : function() {
			this.bind();
		},
		bind : function() {
		
			jsapi.accounts.user.getAccountSummary({
				success : function(data) {
					
					j('#label_account_summary_value_of_open_positions').text(data.valueOfOpenPositions + " " + accountBaseCurrency);
					j('#label_account_summary_total_margins').text(data.totalMargins + " " + accountBaseCurrency);
					j('#label_account_summary_free_balance').text(data.freeBalance + " " + accountBaseCurrency);
					j('#label_account_summary_total_account_value').text(data.totalAccountValue + " " + accountBaseCurrency);
					j('#label_account_summary_total_loans').text(data.totalLoans + " " + accountBaseCurrency);
					
				},
				error: function(error) {
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
			
		},
		destroy : function() {
		
		}
	},
	deposit : {

		init : function() {
		
			// fix
			j('#section_deposit').show();
			j('#section_deposit_thank_you').hide();
			j('#section_deposit_thank_you_pending').hide();
			j('#section_deposit_error').hide();
			
			// if user need to pass suitability test
			if(!isSuitabilityTestPassed) {
				j('.label-deposit-suitability-test').show();
			} else {
				j('.label-deposit-suitability-test').hide();
			}
		
		
			// get deposit result from session
			var depositResult = JSON.parse(j.cookie('drt'));
			
			var isActivationDeposit = depositResult.isActivationDeposit;
			var isShowThankYou = depositResult.isShowThankYou;
			var isShowThankYouPending = depositResult.isShowThankYouPending;
			var isShowError = depositResult.isShowError;
			var isIdent = depositResult.isIdent;
			var transId = depositResult.transId;
			var clearId = depositResult.clearId;
			var processAmount = depositResult.processAmount;
			var baseAmount = depositResult.baseAmount;

            isFirstDeposit = false;
			
			// get data from deposit engine
			j.cookie('drt', JSON.stringify({
				isActivationDeposit : false,
				isShowThankYou : false,
				isShowThankYouPending : false,
				isShowError : false,
				isIdent : false,
				transId : "",
				clearId : "",
				processAmount : "",
				baseAmount : ""
			}));

			if(isActivationDeposit) { // after **redirected** back from processor
				// media mind
				try {
					var ebRand = Math.random()+'';
					ebRand = ebRand * 1000000;
					j.ajax({ url: 'https://bs.serving-sys.com/BurstingPipe/ActivityServer.bs?cn=as&amp;ActivityID=264072&amp;rnd=' + ebRand,dataType: 'script'});
				} catch(e){}
				
				// turn pixel
				var img = new Image();
				// wrap our new image in jQuery, then:
				j(img).load(function () {
				  j('body')
					.append(this);
				})
				.attr("src", "https://r.turn.com/r/beacon?b2=RO-VdYc7UsmspYRp5VrMtGvzRFi_7fLhPiFgO65-kuHDlmMA_kAP0Y8iBKhZIRKrXPZH3ABgws96Y_3NffCJ1A&cid=");
				
                isFirstDeposit = true;
        }
			
			if(isShowThankYou) {
				// google tag manager -> event -> deposit
				if(typeof(dataLayer) !="undefined"){
                    dataLayer.push({'event': 'onDepositThankyou'});
                }
				j('#section_deposit').hide();
				j('#section_deposit_thank_you').show();
				j('#section_deposit_error').hide();
				j('#section_deposit_thank_you_pending').hide();
								
				if(isIdent) {
					j('#label_deposit_thank_you').html(String.format(getContent("depositThankYouIsIdent"), processAmount, transId, clearId));
				} else {
					j('#label_deposit_thank_you').html(String.format(getContent("depositThankYou"), processAmount, baseAmount, transId, clearId));
				}

            


				
			} else if (isShowThankYouPending) {
				
				j('#section_deposit').hide();
				j('#section_deposit_thank_you').hide();
				j('#section_deposit_error').hide();
				j('#section_deposit_thank_you_pending').show();
			
			} else if (isShowError) {
				
				j('#section_deposit').hide();
				j('#section_deposit_thank_you').hide();
				j('#section_deposit_error').show();
				j('#section_deposit_thank_you_pending').hide();
				
			} else {
			
				j('#section_deposit').show();
				j('#section_deposit_thank_you').hide();
				j('#section_deposit_error').hide();
				j('#section_deposit_thank_you_pending').hide();
				
					
				
				j('#input_cardholder_name')
				.val('')
				.removeClass('predefined invalid valid');
			
				j('#input_deposit_amount')
					.val('')
					.removeClass('predefined invalid valid');
					
				j('#input_deposit_amount_deposit')
					.val('')
					.removeClass('predefined invalid valid');
					
				j('#select_deposit_credit_card_types')
					.empty()
					.removeClass('predefined invalid valid');
				
				j('#button_deposit').unbind("click");
				j('#button_deposit_continue').unbind("click");
				
				
				var openProccessor = function(data) {
					
					switch (data.targetId) {
						case 0 : 
						
							j('#section_deposit').hide();
							j('#section_deposit_thank_you').show();
							j('#section_deposit_error').hide();
							j('#section_deposit_thank_you_pending').hide();
											
							if(data.isIdent) {
								j('#label_deposit_thank_you').html(String.format(getContent("depositThankYouIsIdent"), data.processAmount + " " + data.processCurrency, data.transId, data.clearId));
							} else {
								j('#label_deposit_thank_you').html(String.format(getContent("depositThankYou"), data.processAmount + " " + data.processCurrency, data.baseAmount + " " + data.baseCurrency, data.transId, data.clearId));
							}
							break;
						case 1 :
							// redirect
							location.href = data.url; 
							return false;
							break;
						case 3 : 
							// submit form
							j('body').append(data.formHtml);
							j('#frmEpayment').trigger('submit');
							break;
						case 4 :
							// redirect
							location.href = data.url; 
							return false;
							break;
					}
					
				};
				
				
				loader.show();
				
				// get deposit details
				jsapi.accounts.user.getDepositDetails({
					success: function(data) {
					
						loader.hide();
						
						
						// min, max amounts
						var min = data.minAmount;
						var max = data.maxAmount;
						
						// label account base currency
						j('#label_deposit_account_base_currency').text(accountBaseCurrency);
						j('#label_deposit_account_base_currency_deposit').text(accountBaseCurrency);
						
						var maxAmount = 0;
						var creditCardTypeId = 0;
						var lastSelected = null;
						
						j('#list_deposit_credit_card_types')
							.css({ 'left' : 0 })
							.empty();
						/*
						data.creditCardTypes = [
							{ id:7, name:"Visa", maxAmount:10273.5},
{ id:6, name:"MasterCard", maxAmount:10273.5},
{ id:5, name:"JCB", maxAmount:10000},
{ id:18, name:"Maestro", maxAmount:377174.74},
{ id:22, name:"CashU", maxAmount:10273.5},
{ id:23, name:"Maestro", maxAmount:377174.74},
{ id:24, name:"JCB", maxAmount:10000},
{ id:25, name:"Maestro", maxAmount:377174.74},
{ id:26, name:"JCB", maxAmount:10000}
						];
						*/
							
						for (var i = 0; i < data.creditCardTypes.length; i++) {
							var card = j("<li class=\"icon-" + data.creditCardTypes[i].id + "\"><input type=\"radio\" max=\"" + data.creditCardTypes[i].maxAmount + "\" id=\"" + data.creditCardTypes[i].id + "\" name=\"deposit_credit_card_types\" title=\"" + data.creditCardTypes[i].name + "\" /></li>").appendTo('#list_deposit_credit_card_types');
							
							if(i == 0) {
								card.addClass('active');
								var C = card.find(':radio');
								C.prop("checked", true);
								
								maxAmount = C.attr('max');
								creditCardTypeId = C.attr('id');
								lastSelected = C;
							}
							
							card.find(':radio').change(function() {
							
								if(lastSelected != null && lastSelected != j(this)) {
									j(lastSelected).parent().removeClass('active');
								}
								
								maxAmount = j(this).attr('max');
								creditCardTypeId = j(this).attr('id');
								lastSelected = j(this);
								
								j(this).parent().addClass('active');
								
								
								j('#input_deposit_amount')
									.val('')
									.removeClass('predefined invalid valid');
									
								j('#status_deposit_amount').hide();
								j('#input_deposit_amount_deposit')
									.val('')
									.removeClass('predefined invalid valid');
								
								j('#status_deposit_amount_deposit').hide();
								
							});
							
						}
						
						var pageStart = 4;
						if(data.creditCardTypes.length > pageStart) {
						
							var k = 0;
							var f = data.creditCardTypes.length / pageStart;
							for (var w = 0; w < f; w++) {
								k++
							}
							
							var t = 0;
							
							if(data.creditCardTypes.length > 4) {
							
								j('#list_deposit_credit_card_types').css({ 'width' : data.creditCardTypes.length * 59 });
								j('#button_deposit_credit_card_types_right')
									.addClass('active')
									.unbind('click')
									.click(function() {
										if(j(this).hasClass('active')) {
											
											j('#list_deposit_credit_card_types').animate({ "left" : "-=59px" }, "fast"); //.animate({ "left" : "-=236px" }, "fast");
											
											if(!j('#button_deposit_credit_card_types_left').hasClass('active')) {
												j('#button_deposit_credit_card_types_left').addClass('active');
											}
											
											t += 1;
											
											//if(t == (k - 1)) {
											if((t + 4) == data.creditCardTypes.length) {
												j(this).removeClass('active')
											}
											
										}
									});
									
								j('#button_deposit_credit_card_types_left')
									.removeClass('active')
									.unbind('click')
									.click(function() {
										if(j(this).hasClass('active')) {
											
											j('#list_deposit_credit_card_types').animate({ "left" : "+=59px" }, "fast"); //.animate({ "left" : "+=236px" }, "fast");
											
											if(!j('#button_deposit_credit_card_types_right').hasClass('active')) {
												j('#button_deposit_credit_card_types_right').addClass('active');
											}
											
											t -= 1;
											if(t == 0) {
												j(this).removeClass('active');
											}
											
										}
									});
							
							} else {
								j('#button_deposit_credit_card_types_right, #button_deposit_credit_card_types_left')
									.addClass('disabled');
							}
								
						} else {
						
							j('#button_deposit_credit_card_types_right, #button_deposit_credit_card_types_left')
									.addClass('disabled');
							
						}
						
						
						var amount = null;
						var isChangeFromRecurrentToFirst3D = false;
						
						// has credit card flow
						if(data.hasCreditCard) {
						
							j('#form_deposit_no_credit_card').hide();
							j('#form_deposit_has_credit_card').show();
							
							// last4 digits
							j('#label_deposit_last4_digits').text("xxxx-xxxx-" + data.last4Digits);
							
							// link change
							j('#link_deposit_change_credit_card')
								.unbind("click")
								.click(function() {
							
								j('#form_deposit_has_credit_card').hide();
								j('#form_deposit_no_credit_card').show();
								
								if(amount != null) {
									j('#input_deposit_amount').val(amount);
								}
								
								// validator
								var d = null;
								d = jQuery.validator({
									elements : [
										{
											element : j('#input_cardholder_name'),
											status : j('#status_cardholder_name'),
											rules : [
												{ method : 'required', message : getContent("depositCardholderNameRequired") },
												{ method : 'equalTo' , pattern : /^([a-zA-Z]{2,}) ([a-zA-Z ]{2,})$/, message : getContent("depositCardholderNameEqualTo") }
											]
										},
										{
											element : j('#input_deposit_amount'),
											status : j('#status_deposit_amount'),
											rules : [
												{ method : 'required', message : getContent("depositAmountRequired") },
												{ method : 'number', message : getContent("depositAmountNumber") },
												{ method : 'min', pattern : min, message : getContent("depositMinAmountNumber") },
												{ method : 'max', pattern : function() { return Number(maxAmount) }, message : getContent("depositMaxAmountNumber") }
											]
										}
									],
									submitElement : j('#button_deposit_continue'),
									messages : null,
									accept : function() {
										
										loader.show();
										
										// deposit
										jsapi.accounts.user.depositEpay({
											returnUrl : document.location.href,
											lang : j.getUrlParam('l').toLowerCase(),
											transApp : common.TransApp,
											cardHolderName : j('#input_cardholder_name').val(),
											creditCardTypeId : creditCardTypeId,
											amount : j.trim(j('#input_deposit_amount').val()),
											isChangeFromRecurrentToFirst : true,
											isChangeFromRecurrentToFirst3D : isChangeFromRecurrentToFirst3D,
											success : function(data) {
												
												loader.hide();
												openProccessor(data);
												
											},
											error: function(error, data) {
											
												loader.hide();
												if(error != errors.applicationNotLoggedIn) {
												
													if(error == "903") {
														
														var modal = new lightFace({
															title : "",
															message : String.format(getContent(error), data.minAmount, data.maxAmount),
															actions : [
															   { 
																	label : getContent("ok"),
																	fire : function() {
																		modal.close();
																	}, 
																	color: "green" }
															],
															overlayAll : true
														});
													
													} else {
													
														var modal = new lightFace({
															title : "",
															message : getContent(error),
															actions : [
															   { 
																	label : getContent("ok"),
																	fire : function() {
																		modal.close();
																	}, 
																	color: "green" }
															],
															overlayAll : true
														});
													
													}
													
												}
												
											
											}
										});
										
									}
								});
								
							});
							
							// validator
							var d = null;
							d = jQuery.validator({
								elements : [
									{
										element : j('#input_deposit_amount_deposit'),
										status : j('#status_deposit_amount_deposit'),
										rules : [
											{ method : 'required', message : getContent("depositAmountRequired") },
											{ method : 'number', message : getContent("depositAmountNumber") },
											{ method : 'min', pattern : min, message : getContent("depositMinAmountNumber") },
											{ method : 'max', pattern : max, message : getContent("depositMaxAmountNumber") }
										]
									}
								],
								submitElement : j('#button_deposit'),
								messages : null,
								accept : function() {
									
									loader.show();
									
									// deposit
									jsapi.accounts.user.depositEpay({
										returnUrl : document.location.href,
										lang : j.getUrlParam('l').toLowerCase(),
										transApp : common.TransApp,
										amount : j.trim(j('#input_deposit_amount_deposit').val()),
										success : function(data) {
											
											loader.hide();
											openProccessor(data);
											
										},
										error: function(error, data) {
										
											loader.hide();
											
											if(error != errors.applicationNotLoggedIn) {
												if(error == "907") {
													
													amount = data.amount;
													isChangeFromRecurrentToFirst3D = true;
													
													var modal = new lightFace({
														title : "",
														message : getContent(error),
														actions : [
														   { 
																label : getContent("ok"),
																fire : function() {
																
																	// trigger
																	j('#link_deposit_change_credit_card').trigger("click");
																	
																	modal.close();
																}, 
																color: "green" }
														],
														overlayAll : true
													});
													
												} else if(error == "903") { 
													
													var modal = new lightFace({
														title : "",
														message : String.format(getContent(error), data.minAmount, data.maxAmount),
														actions : [
														   { 
																label : getContent("ok"),
																fire : function() {
																	modal.close();
																}, 
																color: "green" }
														],
														overlayAll : true
													});
												
												} else {
												
													var modal = new lightFace({
														title : "",
														message : getContent(error),
														actions : [
														   { 
																label : getContent("ok"),
																fire : function() {
																	modal.close();
																}, 
																color: "green" }
														],
														overlayAll : true
													});
												
												}
												
											}
										
										}
									});
									
									
								}
							});
						
						} else {
						
						
							j('#form_deposit_has_credit_card').hide();
							j('#form_deposit_no_credit_card').show();
							
							// validator
							var d = null;
							d = jQuery.validator({
								elements : [
									{
										element : j('#input_cardholder_name'),
										status : j('#status_cardholder_name'),
										rules : [
											{ method : 'required', message : getContent("depositCardholderNameRequired") },
											{ method : 'equalTo' , pattern : /^([a-zA-Z]{2,}) ([a-zA-Z ]{2,})$/, message : getContent("depositCardholderNameEqualTo") }
										]
									},
									{
										element : j('#input_deposit_amount'),
										status : j('#status_deposit_amount'),
										rules : [
											{ method : 'required', message : getContent("depositAmountRequired") },
											{ method : 'number', message : getContent("depositAmountNumber") },
											{ method : 'min', pattern : min, message : getContent("depositMinAmountNumber") },
											{ method : 'max', pattern : function() { return Number(maxAmount) }, message : getContent("depositMaxAmountNumber") }
										]
									}
								],
								submitElement : j('#button_deposit_continue'),
								messages : null,
								accept : function() {
									
									loader.show();
									
									// deposit
									jsapi.accounts.user.depositEpay({
										returnUrl : document.location.href,
										lang : j.getUrlParam('l').toLowerCase(),
										transApp : common.TransApp,
										cardHolderName : j('#input_cardholder_name').val(),
										creditCardTypeId : creditCardTypeId,
										amount : j.trim(j('#input_deposit_amount').val()),
										success : function(data) {
											
											loader.hide();
											openProccessor(data);
											
										},
										error: function(error, data) {
										
											loader.hide();
											if(error != errors.applicationNotLoggedIn) {
											
												if(error == "903") { 
												
													var modal = new lightFace({
														title : "",
														message : String.format(getContent(error), data.minAmount, data.maxAmount),
														actions : [
														   { 
																label : getContent("ok"),
																fire : function() {
																	modal.close();
																}, 
																color: "green" }
														],
														overlayAll : true
													});
												
												} else {
																								
													var modal = new lightFace({
														title : "",
														message : getContent(error),
														actions : [
														   { 
																label : getContent("ok"),
																fire : function() {
																	modal.close();
																}, 
																color: "green" }
														],
														overlayAll : true
													});
												
												}
												
											}
										
										}
									});
									
								}
							});
							
						}
						
					},
					error : function(error) {
						
						loader.hide();
						
						if(error != errors.applicationNotLoggedIn) {
							
							if(error == "908") {
								
								var modal = new lightFace({
									title : "",
									message : getContent(error),
									actions : [
									   { 
											label : getContent("ok"),
											fire : function() {
												
												// redirect to main website
												location.href = (common.webSiteUrl != undefined ? common.webSiteUrl : "http://www.easy-forex.com/");
												
												//modal.close();
											}, 
											color: "green" }
									],
									overlayAll : true
								});
								
							} else {
								
								var modal = new lightFace({
									title : "",
									message : getContent(error),
									actions : [
									   { 
											label : getContent("ok"),
											fire : function() {
												modal.close();
											}, 
											color: "green" }
									],
									overlayAll : true
								});
							
							}
							
						}
						
					}
				});
				
			}
			
		},
		destroy : function() {
		
			j('#section_deposit').hide();
			j('#section_deposit_thank_you').hide();
			j('#section_deposit_thank_you_pending').hide();
			j('#section_deposit_error').hide();
			
		}
	},
	withdraw : {
		init : function() {
		
			j('#section_withdraw').show();
			j('#section_withdraw_thank_you').hide();
			
			j('#input_withdraw_amount')
				.val('')
				.removeClass('predefined invalid valid');
				
			j('#button_withdraw').unbind("click");
			
		
			jsapi.accounts.user.getUserInfo({
				success: function (b) {
				
					isEnableFractionalPips = b.isEnableFractionalPips;
					accountBaseCurrency = b.accountBaseCurrency;
					withdrawalAvailable = b.withdrawalAvailable;
					allowWithdrawal = b.allowWithdrawal;
					allowDeposit = b.allowDeposit; 
					isActive = b.isActive;
					isDemo = b.isDemo;
					userStatus = b.userStatus;
					needToPassTest = b.needToPassTest;
					isSuitabilityTestPassed = b.isSuitabilityTestPassed;
					regionId = b.regionId;
					regTicketId = b.regTicketId;
					
		
					j('#label_withdraw_withdrawal_available').text(withdrawalAvailable + " " + accountBaseCurrency);
					j('#label_withdraw_account_base_currency').text(accountBaseCurrency);
					
					
					var d = null;
					d = jQuery.validator({
						elements : [
							{
								element : j('#input_withdraw_amount'),
								status : j('#status_withdraw_amount'),
								rules : [
									{ method : 'required', message : getContent("withdrawAmountRequired") },
									{ method : 'number', message : getContent("withdrawAmountNumber") }
								]
							}
						],
						submitElement : j('#button_withdraw'),
						messages : null,
						accept : function() {
							
							jsapi.accounts.user.withdraw({
								amount : j.trim(j('#input_withdraw_amount').val()),
								success : function(data) {
									
									j('#section_withdraw').hide();
									j('#section_withdraw_thank_you').show();
									
									
									j('#label_withdraw_recorded_amount').text(data.recordedAmount + " " + accountBaseCurrency);
									j('#label_withdraw_transaction_id').text(data.transId);
									
								},
								error : function(error) {
									
									if(error != errors.applicationNotLoggedIn) {
										var modal = new lightFace({
											title : "",
											message : getContent(error),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
									}
									
								}
							})
							
						
						},
						error : function(error) {
							
						}
					});
					
				},
				error: function (a) {
					
					if(a != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(a),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
			
		},
		destroy : function() {
		
		}
	},
	closedPositions : {
		lastActiveTab : null,
		dealTypes : {
			forward : 2,
			dayTrading : 3,
			limit : 4
		},
		init : function() {
		
			j('ul#list_buttons_closed_positions li a').removeClass('active');
			j('ul#list_buttons_closed_positions li a').each(function(i, el){
				if(i == 0) {
					j(el).addClass('active');
					handle.closedPositions.lastActiveTab = j(el);
				}
				j(el)
					.unbind('click')
					.click(function() {
					if(handle.closedPositions.lastActiveTab != null && handle.closedPositions.lastActiveTab != j(this)) {
						handle.closedPositions.lastActiveTab.removeClass('active');
					}
					handle.closedPositions.lastActiveTab = j(this); 
					
					j(this).addClass('active');
					handle.closedPositions.bind(handle.closedPositions.dealTypes[j(this).attr('name')]);
					
				});
			});
			
			this.bind(handle.closedPositions.dealTypes.dayTrading);
		},
		bind : function(dealType) {
		
			j('#list_closed_positions').empty();
			jsapi.accounts.user.getClosedPosition({
				fromDate : null,
				toDate: null,
				dealType : dealType,
				count : 50,
				success : function(data) {
				
					j('#label_closed_positions_total_balance').text(data.balance + " " + accountBaseCurrency);
					j('#label_closed_positions_total_profit_loss')
						.text((data.totalProfitLoss != undefined ? data.totalProfitLoss : "0.00") + " " + accountBaseCurrency)
						.removeClass("down up")
						.addClass(((Number(data.totalProfitLoss.replace(/,/g,"")) != 0) ? ((Number(data.totalProfitLoss.replace(/,/g,"")) < 0) ? "down" : "up") : ""));
					if(data.deals.length != 0) {
						
						var t = 0;
						for(var i = 0; i < data.deals.length; i++) {
							j("<li " + ( t%2 ? "class=\"odd\"" : "") + ">" +
								"<div class=\"col first-item\">" +
									"<span><label>" + getContent("openDate") + "</label></span><b>" + data.deals[i].openDate + "</b>" +
									"<span><label>" + getContent("rate") + "</label></span><b>" + data.deals[i].rate + "</b>" +
									"<span><label>" + getContent("closeDate") + "</label></span><b>" + data.deals[i].closeDate + "</b>" +
									(data.deals[i].closeRate != undefined ? "<span><label>" + getContent("closeRate") + "</label></span><b>" + data.deals[i].closeRate + "</b>" : "") +
								"</div>" +
								"<div class=\"col2\">" +
									"<span><label>" + getContent("buy") + "</label></span><b class=\"money\">" + data.deals[i].buyAmount + " " + data.deals[i].buyCurrency + "</b>" +
									"<span><label>" + getContent("sell") + "</label></span><b class=\"money\">" + data.deals[i].sellAmount + " " + data.deals[i].sellCurrency + "</b>" +
									(data.deals[i].closingValue != undefined ? "<span><label>" + getContent("profitLoss") + "</label></span><b class=\"money " + (data.deals[i].closingValue != undefined ? ((Number(data.deals[i].closingValue.replace(/,/g,"")) != 0) ? ((Number(data.deals[i].closingValue.replace(/,/g,"")) < 0) ? "down" : "up") : "") : "") + "\">" + data.deals[i].closingValue + " " + accountBaseCurrency + "</b>" : "") + 
								"</div>" +
							"</li>").appendTo("#list_closed_positions");
							t++;
						}
					} else {
						j("<li>" + getContent("noData") + "</li>").appendTo("#list_closed_positions");
					}
					
				},
				error: function(error) {
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
		},
		destroy : function() {
		
		}
	},
	statement : {
		init : function() {
			this.bind();
		},
		bind : function() {
		
			j('#list_statement').empty();
			jsapi.accounts.user.getStatement({
				fromDate  : null,
				toDate : null,
				count : 50,
				sortIndex : 2,
				sortType : true,
				success : function(data) {
				
					if(data.accountTransactions.length != 0) {
						
						var t = 0;
						for(var i = 0; i < data.accountTransactions.length; i++) {
							j("<li " + ( t%2 ? "class=\"odd\"" : "") + ">" +
								"<div class=\"col first-item\">" +
									"<span>" + getContent("date") + "</span><b>" + data.accountTransactions[i].dealDate + "</b>" +
									"<span>" + getContent("creditDebit") + "</span><b class=\"money\">" + data.accountTransactions[i].amount + "</b>" +
								"</div>" +
								"<div class=\"col\">" +
									"<span>" + getContent("description") + "</span><b>" + data.accountTransactions[i].description + "</b>" +
									"<span>" + getContent("freeBalance") + "</span><b class=\"money\">" + data.accountTransactions[i].freeBalance + " " + accountBaseCurrency + "</b>" +
								"</div>" +
								"<div class=\"col\">" +
									(data.accountTransactions[i].margin == "" ? "" : "<span>" + getContent("marginToRisk") + "</span><b class=\"money\">" + data.accountTransactions[i].margin + " " + data.accountTransactions[i].marginCurrency + "</b>" ) +
								"</div>" +
							"</li>").appendTo("#list_statement");
							
							t++;
						}
					} else {
						j("<li>" + getContent("noData") + "</li>").appendTo("#list_statement");
					}
				
				},
				error : function(error) {
					
					if(error != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(error),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
			
		},
		destroy : function() {
		
		}
	},
	contactUs : {
		lastActiveTab : null,
		lastSubActiveTab : null,
		timeToCall : {
			"morning" : 0,
			"afternoon" : 1,
			"evening" : 2
		},
		init : function() {
			
			jsapi.marketInfo.contactUs.getContactUs({
				success: function (data) {								
					j('#label_office_phone').empty();
					for(var i=0;i<messages.phones.length;i++){
						if(messages.phones[i].officeId==data.officeId){
							j('#label_office_name').text(messages.phones[i].officeName);
							j('#label_office_phone').append("<a style=\"text-decoration: none\" href=\"tel:" + messages.phones[i].phone + "\">" + messages.phones[i].phone + "</a>");
							break;							
						}
					}
					
					clientPhone = data.clientPhone;
					j('#input_call_me_back_phone').val(clientPhone);
					
				}
			});
			
			j('#link_call_me_back')
				.unbind('click')
				.click(function() {
					
					j('#placeholder_call_me_back_view').hide();
					j('#placeholder_call_me_back_form').show();					
					j("#a_call_me_back_0").css('height',j("#a_call_me_back_1").height());
				});
				
			j('#button_call_me_back_close')
				.unbind('click')
				.click(function() {
					
					j('#placeholder_call_me_back_view').show();
					j('#placeholder_call_me_back_form').hide();
					j('#placeholder_call_me_back_thankyou').hide();
					
					handle.contactUs.destroy();
					
					j('#input_call_me_back_phone').val(clientPhone);
					
					handle.contactUs.bind();
					
				});
				
			handle.contactUs.bind();
			
		},
		bind : function() {

			var isGuest = true;
			
			
			j('#list_worldwide_local_numbers').accordion({
				collapsible: true,
				active: false, 
				header : 'a.ui-tab',
				autoheight : false,
				animated: false,
				alwaysOpen: false,
				clearStyle: true
			});
			
			// if user logged in
			if(isAuthenticated) {
			
				j('#placeholder_call_me_back_title').show();
				j('#placeholder_call_me_back_tabs').hide();
				
				j('#row_call_me_back_name').hide();
				j('#row_call_me_back_username').hide();
				j('#row_call_me_back_phone').show();
				
			} else {
			
				j('#placeholder_call_me_back_title').hide();				
				j('#placeholder_call_me_back_tabs').show();
								
				// tabs -> guest or registered user
				j('ul#list_buttons_call_me_back li a').removeClass('active');
				j('ul#list_buttons_call_me_back li a').each(function(i, el){
					if(i == 0) {
						j(el).addClass('active');
						handle.contactUs.lastActiveTab = j(el);
						
						j('#row_call_me_back_name').show();
						j('#row_call_me_back_username').hide();
						j('#row_call_me_back_phone').show();
						
					}
					j(el)
						.unbind('click')
						.click(function() {
						if(handle.contactUs.lastActiveTab != null && handle.contactUs.lastActiveTab != j(this)) {
							handle.contactUs.lastActiveTab.removeClass('active');
						}
						handle.contactUs.lastActiveTab = j(this); 
						
						j(this).addClass('active');
						
						if(j(this).attr('name') == "guest") {
						
							isGuest = true;
							
							j('#row_call_me_back_name').show();
							j('#row_call_me_back_username').hide();
							j('#row_call_me_back_phone').show();
							
						} else {
						
							isGuest = false;
							
							j('#row_call_me_back_name').hide();
							j('#row_call_me_back_username').show();
							j('#row_call_me_back_phone').show();
							
						}
						
					});
				});
			
			}
			
			// tabs -> time to call (morning, afternoon, evening)
			j('ul#list_buttons_call_me_back_time_to_call li a').removeClass('active');
			j('ul#list_buttons_call_me_back_time_to_call li a').each(function(i, el){
				if(i == 0) {
					j(el).addClass('active');
					handle.contactUs.lastSubActiveTab = j(el);
				}
				j(el)
					.unbind('click')
					.click(function() {
					if(handle.contactUs.lastSubActiveTab != null && handle.contactUs.lastSubActiveTab != j(this)) {
						handle.contactUs.lastSubActiveTab.removeClass('active');
					}
					handle.contactUs.lastSubActiveTab = j(this); 
					
					j(this).addClass('active');
					
				});
			});
			
			var enableSubmit = false;
			
			// validate fields
			jQuery.validator({
                elements: [
					{
						element: j('#input_call_me_back_name'),
						status: j('#status_call_me_back_name'),
						validate : function() {
							return (!isAuthenticated && isGuest);
						},
						rules: [
							{ method : 'required', message : getContent("registerUserNameRequired") },
							{ method : 'equalTo' , pattern : /^[a-zA-Z -]*[a-zA-Z -]+$/, message : getContent("registerFirstNameEqualTo") }
						]
					}, {
						element: j('#input_call_me_back_username'),
						status: j('#status_call_me_back_username'),
						validate : function() {
							return (!isAuthenticated && !isGuest);
						},
						rules: [
							{ method : 'required', message : getContent("registerUserNameRequired") },
							{ method : 'minlength', pattern : 6, message : getContent("registerUserNameMinLength") }
						]
					}, {
						element: j('#input_call_me_back_phone'),
						status: j('#status_call_me_back_phone'),
						rules: [
							{ method : 'required', message : getContent("registerPhoneRequired") },
							{ method : 'digits' , message : getContent("registerPhoneOnlyDigits") },
							{ method : 'minlength', pattern : 5, message : getContent("registerPhoneMinLength") }
						]
					}
				],
                submitElement: j('#button_call_me_back'),
                messages: null,
                accept: function () {
				
					var requestCallMeBack = function(params) {
						
						jsapi.marketInfo.contactUs.callMeBack({
							userName: params.userName,
							name: params.name,
							phone: params.phone,
							timeToCall: params.timeToCall,
							success: function(){
								j('#placeholder_call_me_back_form').hide();
								j('#placeholder_call_me_back_thankyou').show();		
							},
							error: function(error){
								
								if(error != errors.applicationNotLoggedIn) {
									var modal = new lightFace({
										title : "",
										message : getContent(error),
										actions : [
										   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
										],
										overlayAll : true
									});
								}
								
							}
						});
						
					};
					
					
					var timeToCall = handle.contactUs.timeToCall[handle.contactUs.lastSubActiveTab.attr('name')];
					
					if(!isAuthenticated && !isGuest) {
						if(enableSubmit) {
							requestCallMeBack({
								userName : j('#input_call_me_back_username').val(),
								phone : j('#input_call_me_back_phone').val(),
								timeToCall: timeToCall
							});
						}
					} else {
						if(isAuthenticated) {
							requestCallMeBack({
								phone : j('#input_call_me_back_phone').val(),
								timeToCall: timeToCall
							});
						} else {					
							requestCallMeBack({
								name : j('#input_call_me_back_name').val(),
								phone : j('#input_call_me_back_phone').val(),
								timeToCall: timeToCall
							});
						}
					}
					
				},
				error : function(el) {
					//
				}
            });
			
			// check for exist or invalid username
			j('#input_call_me_back_username')
				.keyup(function(e) {
					if(e.keyCode == '13') {
						return
					}
					j('#status_call_me_back_invalid_username').hide();
				})
				.blur(function() {
				if(j(this).hasClass('valid')) {
					jsapi.accounts.user.checkExistUserName({
						userName : j.trim(j(this).val().toLowerCase()),
						success : function() {
							enableSubmit = false;
							j('#status_call_me_back_invalid_username').show();
						},
						error : function() {
							enableSubmit = true;
							j('#status_call_me_back_invalid_username').hide();
						}
					});
				}
			});
		
		},
		destroy : function() {
		
			j('#button_call_me_back').unbind('click');
			
			j('#row_call_me_back_name').hide();
			j('#row_call_me_back_username').hide();
			j('#row_call_me_back_phone').hide();
			
			
			j('#status_call_me_back_invalid_username').hide();
			j('#input_call_me_back_name, #input_call_me_back_username, #input_call_me_back_phone')
				.val('')
				.removeClass('predefined invalid valid');
				
				
			j('#placeholder_call_me_back_view').show();
			j('#placeholder_call_me_back_form').hide();
			j('#placeholder_call_me_back_thankyou').hide();
				
		}
	},
	account : {
		init : function() {
		
			// Logout
			j('#button_logout')
			.unbind('click')
			.click(function() {
			
				j.cookie("sid", null);
				
				jsapi.accounts.user.logout({
					success : function() {
						if (typeof ezfxCordova != 'undefined') {
							ezfxCordova.fillContentFromLocalStorage();							 
						} else {
							var currentLocation = document.location.href.replace(/[\#\?].*/,"");
							location.href = currentLocation + (j.getUrlParam('l') != "" ? "?l=" + j.getUrlParam('l') : "") + (j.getUrlParam('i') != "" ? "&i=" + j.getUrlParam('i') : "");
						}
					},
					error : function() {
						var currentLocation = document.location.href.replace(/[\#\?].*/,"");
						location.href = currentLocation + (j.getUrlParam('l') != "" ? "?l=" + j.getUrlParam('l') : "") + (j.getUrlParam('i') != "" ? "&i=" + j.getUrlParam('i') : "");
					}
				});
				
			});
			
			j('#list_account').accordion({
				collapsible: true,
				active: false, 
				header : 'a.ui-tab',
				autoheight : false,
				animated: false,
				alwaysOpen: false,
				clearStyle: true,
				change: function(event, ui) {
				
                /* Removed - US 23021
					if(ui.newHeader.hasClass('call-to-deposit'))
					screenManager.show(screens.callToDeposit);
					*/	
					if(ui.newHeader.hasClass('deposit')) {
						
						if(!allowDeposit) {
							
							if(userStatus == "Lead" 
								|| userStatus == "Lead, Restricted") {
							
								var modal = new lightFace({
									title : "",
									message : getContent("continueRegistrationDeposit"),
									actions : [
									   { 
											label : getContent("ok"), 
											fire : function() {
												screenManager.show(screens.register);
												modal.close();
											}, 
											color: "green" }
									],
									overlayAll : true
								});
							
							} else {
														
								var modal = new lightFace({
									title : "",
									message : getContent("depositNotAvailable"),
									actions : [
									   { 
											label : getContent("ok"),
											fire : function() {
												modal.close();
											}, 
											color: "green" }
									],
									overlayAll : true
								});
							
							}
							
							j('#list_account').accordion("activate", false);
							
						} else {
							screenManager.show(screens.deposit);
						}
						
					}
					
					if(ui.newHeader.hasClass('account-summary'))
						screenManager.show(screens.accountSummary);
					
					if(ui.newHeader.hasClass('withdraw')) {
						if(!allowWithdrawal) {
							
							var modal = new lightFace({
								title : "",
								message : getContent("withdrawalNotAvailable"),
								actions : [
								   { 
										label : getContent("ok"),
										fire : function() {
											modal.close();
										}, 
										color: "green" }
								],
								overlayAll : true
							});
							
							j('#list_account').accordion("activate", false);
							
						} else {
							screenManager.show(screens.withdraw);
						}
						
					}
					
					if(ui.newHeader.hasClass('closed-positions')) 
						screenManager.show(screens.closedPositions);
					
					if(ui.newHeader.hasClass('statement')) 
						screenManager.show(screens.statement);
					
				}
			});
				
			jsapi.accounts.user.getUserInfo({
				success: function (b) {
				
					isEnableFractionalPips = b.isEnableFractionalPips;
					accountBaseCurrency = b.accountBaseCurrency;
					withdrawalAvailable = b.withdrawalAvailable;
					allowWithdrawal = b.allowWithdrawal;
					allowDeposit = b.allowDeposit; 
					isActive = b.isActive;
					isDemo = b.isDemo;
					userStatus = b.userStatus;
					needToPassTest = b.needToPassTest;
					isSuitabilityTestPassed = b.isSuitabilityTestPassed;
					regionId = b.regionId;
					regTicketId = b.regTicketId;
					
			
					j('#label_user_name').text(jsapi.accounts.user.userInfo.userName);
			
				},
				error: function (a) {
					
					if(a != errors.applicationNotLoggedIn) {
						var modal = new lightFace({
							title : "",
							message : getContent(a),
							actions : [
							   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
							],
							overlayAll : true
						});
					}
					
				}
			});
			
		},
		destroy : function() {
			j('#list_account').accordion( "destroy" );
		}
	},
	forgotPassword : {
		init : function() {
		
			j('#input_forgot_username, #input_forgot_email, #input_reset_secret_hint_answer')
				.val('')
				.removeClass('predefined invalid valid');
			
			var d = null;
            d = jQuery.validator({
                elements: [{
                    element: j('#input_forgot_username'),
                    status: j('#status_forgot_username'),
                    rules: [{
                        method: 'required',
                        message: getContent("forgotUserNameRequired")
                    }]
                }, {
					element : j('#input_forgot_email'),
					status : j('#status_forgot_email'),
					rules : [
						{ method : 'required', message : getContent("forgotEmailRequired") },
						/* { method : 'emailISO', message : getContent("forgotEmailInvalid") } */
						{ method : 'email', message : getContent("forgotEmailInvalid") }
					]
				}],
                submitElement: j('#button_forgot_password'),
                messages: null,
                accept: function () {
					loader.show();
					jsapi.accounts.user.forgotPassword({
						userName : j.trim(j('#input_forgot_username').val()),
						email : j.trim(j('#input_forgot_email').val()),
						success : function(data) {
							
							loader.hide();
							
							if(data.secretQuestionId != undefined) {
							
								j('#section_forgot_forgot').hide();
								j('#section_forgot_reset').show();
								
								
								// set
								j('#label_reset_username').text(j('#input_forgot_username').val());
								j('#label_reset_secret_hint_answer').text(getContent("secretHintQuestions")[data.secretQuestionId]); // data.secretQuestion
								
								
								var f = null;
								f = jQuery.validator({
									elements: [{
										element: j('#input_reset_secret_hint_answer'),
										status: j('#status_reset_secret_hint_answer'),
										rules: [
											{ method: 'required', message: getContent("resetSecretHintAnswerRequired") },
											{ method : 'minlength', pattern : 2, message : getContent("registerSecretHintAnswerMinLength") },
											{ method : 'equalTo', pattern : /^[\S0-9: \/]+$/, message : getContent("registerSecretHintAnswerEqualTo") }
										]
									}],
									submitElement: j('#button_reset_password'),
									messages: null,
									accept: function () {
										
										loader.show();
										jsapi.accounts.user.resetPassword({
											userName : j.trim(j('#input_forgot_username').val()),
											secretHintAnswer : j('#input_reset_secret_hint_answer').val(),
											success : function(data) {
												
												loader.hide();
												
												j('#section_forgot_reset').hide();
												j('#section_complete').show();
												j('#button_forgot_password_ok')
													.unbind('click')
													.click(function() {
														screenManager.show(screens.trading);
													});
												
											},
											error: function(error) {
												loader.hide();
												
												if(error != errors.applicationNotLoggedIn) {
													var modal = new lightFace({
														title : "",
														message : getContent(error),
														actions : [
														   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
														],
														overlayAll : true
													});
												}
												
											}
										});
										
									},
									error : function(error) {
										//
									}
								});
							
							} else {
							
								j('#section_forgot_forgot').hide();
								j('#section_complete').show();
								j('#button_forgot_password_ok')
									.unbind('click')
									.click(function() {
										screenManager.show(screens.trading);
									});
							
							}
							
						},
						error : function(error) {
							loader.hide();
                            
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : getContent(error),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
					});
						
				},
				error : function(error) {
					// error
				}
			});
			
		},
		destroy : function() {
			
			j('#input_forgot_username').val('');
			j('#input_forgot_email').val('');
			j('#button_forgot_password').unbind('click');
			j('#button_reset_password').unbind('click');
			
			j('#section_forgot_forgot').show();
			j('#section_forgot_reset').hide();
			j('#section_complete').hide();
			
		}
	},
	changePassword : {
		init : function() {
		
			j('#input_change_username, #input_change_password, #input_change_new_password, #input_change_retype_password')
				.val('')
				.removeClass('predefined invalid valid');
			
			var d = null;
            d = jQuery.validator({
                elements: [{
                    element: j('#input_change_username'),
                    status: j('#status_change_username'),
                    rules: [{
                        method: 'required',
                        message: getContent("changeUserNameRequired")
                    }]
                }, {
                    element: j('#input_change_password'),
                    status: j('#status_change_password'),
                    rules: [
						{ method: 'required', message: getContent("changePasswordRequired") }, 
						{ method: 'rangelength', pattern: [6, 12], message : getContent("changePasswordRangeLength") }
					]
                }, {
					element : j('#input_change_new_password'),
					status : j('#status_change_new_password'),
					rules : [
						{ method : 'required', message : getContent("changeNewPasswordRequired") },
						{ method : 'rangelength', pattern : [6,12], message : getContent("changeNewPasswordRangeLength") },
						{ method : 'equalTo', pattern : /^[a-zA-Z0-9_.!-]+$/, message : getContent("changeNewPasswordEqualTo") },
						{ method : 'notEqualTo', element : j('#input_change_username'), message : getContent("changeNewPasswordNotEqualTo")  }
					]
				},{
					element : j('#input_change_retype_password'),
					status : j('#status_change_retype_password'),
					rules : [
						{ method : 'required',  message : getContent("changeRetypePasswordRequired") },
						{ method : 'rangelength', pattern : [6,12], message : getContent("changeRetypePasswordRangeLength") },
						{ method : 'equalTo',  element : j('#input_change_new_password'), message : getContent("changeRetypePasswordEqualTo") }
					]
				}],
                submitElement: j('#button_change_password'),
                messages: null,
                accept: function () {
					
					loader.show();
					
					jsapi.accounts.user.changePassword({
						userName : j.trim(j('#input_change_username').val()),
						password : j.trim(j('#input_change_password').val()),
						newPassword : j.trim(j('#input_change_new_password').val()),
						retypePassword : j.trim(j('#input_change_retype_password').val()),
						success : function(data) {
						
							isAuthenticated = true;
							
							// set cookie session
							j.cookie('sid', data.sessionId, { expires: 365 });
							
                            
							j('.before-login').hide();
                            j('.after-login').show();
                            jsapi.accounts.user.getUserInfo({
                                success: function (b) {
                                    
									isEnableFractionalPips = b.isEnableFractionalPips;
                                    accountBaseCurrency = b.accountBaseCurrency;
									withdrawalAvailable = b.withdrawalAvailable;
									allowWithdrawal = b.allowWithdrawal;
									allowDeposit = b.allowDeposit;
									isActive = b.isActive;
									isDemo = b.isDemo;
									userStatus = b.userStatus;
									needToPassTest = b.needToPassTest;
									isSuitabilityTestPassed = b.isSuitabilityTestPassed;
									regionId = b.regionId;
									regTicketId = b.regTicketId;
									
									// change password
									j('.label-user-id').text(jsapi.accounts.user.userInfo.userId);
									
									switch(regionId) {
										case 1: {
											// Asia Pacific
											j(".risk-warning-copyright").html(getContent("copyrightAsiaPacific"));
											j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_au_phone.pdf");
											break;
										}
										case 11 : {
											// Europe
											j(".risk-warning-copyright").html(getContent("copyrightEurope"));
											j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_eu_phone.pdf");
											break;
										}
										default : {
											// Default
											j(".risk-warning-copyright").html(getContent("copyright"));
											j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_int_phone.pdf");
										}
									}
									
                                    jsapi.trading.getTrading({
                                        success: function (a) {
                                            loader.hide();
                                            screenManager.show(screens.quotes);
                                        },
                                        error: function (a) {
                                            loader.hide();
                                            
											if(a != errors.applicationNotLoggedIn) {
												var modal = new lightFace({
													title : "",
													message : getContent(a),
													actions : [
													   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
													],
													overlayAll : true
												});
											}
											
                                        }
                                    })
                                },
                                error: function (a) {
                                    loader.hide();
                                    
									if(a != errors.applicationNotLoggedIn) {
										var modal = new lightFace({
											title : "",
											message : getContent(a),
											actions : [
											   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
											],
											overlayAll : true
										});
									}
									
                                }
                            });
						},
						error : function(error) {
							loader.hide();
                            
							if(error != errors.applicationNotLoggedIn) {
								var modal = new lightFace({
									title : "",
									message : getContent(error),
									actions : [
									   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
									],
									overlayAll : true
								});
							}
							
						}
					});
					
				},
				error : function(error) {
					//
				}
			});
			
		},
		destroy : function() {
			
			j('#input_change_username').val('');
			j('#input_change_password').val('');
			j('#input_change_new_password').val('');
			j('#input_change_retype_password').val('');
			
		}
	}
};







var getOrientation = function() {
	var portrait_map = {
		"0": true,
		"180": true
	};
	var isPortrait = true,
		elem = document.documentElement;
	// prefer window orientation to the calculation based on screensize as
	// the actual screen resize takes place before or after the orientation change event
	// has been fired depending on implementation (eg android 2.3 is before, iphone after).
	// More testing is required to determine if a more reliable method of determining the new screensize
	// is possible when orientationchange is fired. (eg, use media queries + element + opacity)
	if (j.support.orientation) {
		// if the window orientation registers as 0 or 180 degrees report
		// portrait, otherwise landscape
		isPortrait = portrait_map[window.orientation];
	} else {
		isPortrait = elem && elem.clientWidth / elem.clientHeight < 1.1;
	}
	return isPortrait ? "portrait" : "landscape";
};
(function (jQuery, window) {
    var win = j(window),
        event_name = "orientationchange",
        special_event,
        get_orientation,
        last_orientation,
        initial_orientation_is_landscape,
        initial_orientation_is_default,
        portrait_map = {
            "0": true,
            "180": true
        };

    // It seems that some device/browser vendors use window.orientation values 0 and 180 to
    // denote the "default" orientation. For iOS devices, and most other smart-phones tested,
    // the default orientation is always "portrait", but in some Android and RIM based tablets,
    // the default orientation is "landscape". The following code attempts to use the window
    // dimensions to figure out what the current orientation is, and then makes adjustments
    // to the to the portrait_map if necessary, so that we can properly decode the
    // window.orientation value whenever get_orientation() is called.
    //
    // Note that we used to use a media query to figure out what the orientation the browser
    // thinks it is in:
    //
    // initial_orientation_is_landscape = $.mobile.media("all and (orientation: landscape)");
    //
    // but there was an iPhone/iPod Touch bug beginning with iOS 4.2, up through iOS 5.1,
    // where the browser *ALWAYS* applied the landscape media query. This bug does not
    // happen on iPad.

    if (j.support.orientation) {

        // Check the window width and height to figure out what the current orientation
        // of the device is at this moment. Note that we've initialized the portrait map
        // values to 0 and 180, *AND* we purposely check for landscape so that if we guess
        // wrong, , we default to the assumption that portrait is the default orientation.
        // We use a threshold check below because on some platforms like iOS, the iPhone
        // form-factor can report a larger width than height if the user turns on the
        // developer console. The actual threshold value is somewhat arbitrary, we just
        // need to make sure it is large enough to exclude the developer console case.

        var ww = window.innerWidth || j(window).width(),
            wh = window.innerHeight || j(window).height(),
            landscape_threshold = 50;

        initial_orientation_is_landscape = ww > wh && (ww - wh) > landscape_threshold;


        // Now check to see if the current window.orientation is 0 or 180.
        initial_orientation_is_default = portrait_map[window.orientation];

        // If the initial orientation is landscape, but window.orientation reports 0 or 180, *OR*
        // if the initial orientation is portrait, but window.orientation reports 90 or -90, we
        // need to flip our portrait_map values because landscape is the default orientation for
        // this device/browser.
        if ((initial_orientation_is_landscape && initial_orientation_is_default) 
			|| (!initial_orientation_is_landscape && !initial_orientation_is_default)) {
            portrait_map = {
                "-90": true,
                "90": true
            };
        }
    }

    j.event.special.orientationchange = j.extend({}, j.event.special.orientationchange, {
        setup: function () {
            // If the event is supported natively, return false so that jQuery
            // will bind to the event using DOM methods.
            if (j.support.orientation && !j.event.special.orientationchange.disabled) {
                return false;
            }

            // Get the current orientation to avoid initial double-triggering.
            last_orientation = get_orientation();

            // Because the orientationchange event doesn't exist, simulate the
            // event by testing window dimensions on resize.
            win.bind("throttledresize", handler);
        },
        teardown: function () {
            // If the event is not supported natively, return false so that
            // jQuery will unbind the event using DOM methods.
            if (j.support.orientation && !j.event.special.orientationchange.disabled) {
                return false;
            }

            // Because the orientationchange event doesn't exist, unbind the
            // resize event handler.
            win.unbind("throttledresize", handler);
        },
        add: function (handleObj) {
            // Save a reference to the bound event handler.
            var old_handler = handleObj.handler;


            handleObj.handler = function (event) {
                // Modify event object, adding the .orientation property.
                event.orientation = get_orientation();

                // Call the originally-bound event handler and return its result.
                return old_handler.apply(this, arguments);
            };
        }
    });

    // If the event is not supported natively, this handler will be bound to
    // the window resize event to simulate the orientationchange event.
    function handler() {
        // Get the current orientation.
        var orientation = get_orientation();

        if (orientation !== last_orientation) {
            // The orientation has changed, so trigger the orientationchange event.
            last_orientation = orientation;
            win.trigger(event_name);
        }
    }

    // Get the current page orientation. This method is exposed publicly, should it
    // be needed, as jQuery.event.special.orientationchange.orientation()
    j.event.special.orientationchange.orientation = get_orientation = function () {
        var isPortrait = true,
            elem = document.documentElement;

        // prefer window orientation to the calculation based on screensize as
        // the actual screen resize takes place before or after the orientation change event
        // has been fired depending on implementation (eg android 2.3 is before, iphone after).
        // More testing is required to determine if a more reliable method of determining the new screensize
        // is possible when orientationchange is fired. (eg, use media queries + element + opacity)
        if (j.support.orientation) {
            // if the window orientation registers as 0 or 180 degrees report
            // portrait, otherwise landscape
            isPortrait = portrait_map[window.orientation];
        } else {
            isPortrait = elem && elem.clientWidth / elem.clientHeight < 1.1;
        }

        return isPortrait ? "portrait" : "landscape";
    };

    j.fn[event_name] = function (fn) {
        return fn ? this.bind(event_name, fn) : this.trigger(event_name);
    };

    j.attrFn[event_name] = true;

}(jQuery, this));

jQuery.extend( jQuery.support, {
	orientation: "orientation" in window
});




var silentScroll = function(refresh) {
	
	// Start out by adding the height of the location bar to the width, so that
	// we can scroll past it
	if (isIDevice) {
		
		
		
		
		
		
		setTimeout(function() {
			// hide iOS address bar
			window.scrollTo(0, 1);
			
		}, 200);
			if(refresh) {
		
				if(screenManager.lastScreen != null) {
					if(myScroll != undefined) {
						myScroll.scrollToPage(1, 0, 200);
					myScroll.refresh();
					}
				}
			}
	
	} else {
		// Upper screen
		window.scrollTo(0,0);
	
	}
	
};

var detectRatio = function() {
	return {
		width : jQuery('html').width(),
		height : jQuery('html').height()
	};	
};

// screens
var screens = {
	login : {
		name : "login",
		title : getContent("screenLogin"),
		element : function() {
			return j('#screen_login');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.login.init();
		},
		unload : function() {
			handle.login.destroy();
		},
		orientationchange : null
	},
	video : {
		name : "video",
		title : getContent("screenVideo"),
		element : function() {
			return j('#screen_video')
		},
		tab : "research",
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.video.init();
		},
		unload : function() {
			handle.video.destroy();
		},
		orientationchange : function() {
			handle.video.orientationchange();
		}
	},
	myPositions : {
		name : "myPositions",
		title : getContent("screenMyPositions"),
		element : function() {
			return j('#screen_my_positions');
		},
		tab : "position",
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.myPositions.init();
		},
		unload : function() {
			handle.myPositions.destroy();
		},
		orientationchange : null
	},
	quotes: {
		name: "quotes",
		title : getContent("screenQuotes"),
		element : function() {
			return j('#screen_quotes');
		},
		tab : "quotes",
		showInstruments : true,
		isAuthenticationRequired : false,
		load : function() {
			handle.quotes.init();
		},
		unload : function() {
			handle.quotes.destroy();
		},
		orientationchange : null
	},
	trading: {
		name : "trading",
		title : getContent("screenTrading"),
		element : function() {
			return j('#screen_quotes');
		},
		tab : "quotes",
		showInstruments : true,
		isAuthenticationRequired : true,
		load : function() {
			handle.quotes.init();
		},
		unload : function() {
			handle.quotes.destroy();
		},
		orientationchange : null
	},
	customizePairs : {
		name : "customizePairs",
		title : getContent("screenCustomizePairs"),
		element : function() {
			return j('#screen_customize_pairs');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.customize.init();
		},
		unload : function() {
			handle.customize.destroy();
		},
		orientationchange : null
	},
	research : {
		name : "research",
		title : getContent("screenResearch"),
		element : function() {
			return j('#screen_research');
		},
		tab : "research",
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.research.init();
		},
		unload : function() {
			handle.research.destroy();
		},
		orientationchange : null
	},
	charts : {
		name : "charts",
		title : getContent("screenCharts"),
		element : function() {
			return j('#screen_charts');
		},
		tab : "charts",
		showInstruments : true,
		isAuthenticationRequired : false,
		load : function() {
			handle.charts.init();
		},
		unload : function() {
			handle.charts.destroy();
		},
		orientationchange : function(){ 
			handle.charts.orientationchange();
		}
	},
	openDeal : {
		name : "openDeal",
		title : getContent("screenOpenDeal"),
		element : function() {
			return j('#screen_open_deal');
		},
		tab : "trade",
		showInstruments : true,
		isAuthenticationRequired : true,
		load : function() {
			handle.openDeal.init();
		},
		unload : function() {
			handle.openDeal.destroy();
		},
		orientationchange : null
	},
	modifyDeal : {
		name : "modifyDeal",
		title : getContent("screenModifyDeal"),
		element : function() {
			return j('#screen_modify_deal');
		},
		tab : "trade",
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.modifyDeal.init();
		},
		unload : function() {
			handle.modifyDeal.destroy();
		},
		orientationchange : null
	},
	closeDeal : {
		name : "closeDeal",
		title : getContent("screenCloseDeal"),
		element : function() {
			return j('#screen_close_deal');
		},
		tab : "trade",
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.closeDeal.init();
		},
		unload : function() {
			handle.closeDeal.destroy();
		},
		orientationchange : null
	},
	registerDemo : {
		name : "registerDemo",
		title : getContent("screenRegister"),
		element : function() {
			return j('#screen_register_demo');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.registerDemo.init();
		},
		unload : function() {
			handle.registerDemo.destroy();
		},
		orientationchange : null
	},
	register : {
		name : "register",
		title : getContent("screenRegister"),
		element : function() {
			return j('#screen_register');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.register.init();
		},
		unload : function() {
			handle.register.destroy();
		},
		orientationchange : null
	},
	registerComplete : {
		name : "register_complete",
		title : getContent("screenRegisterComplete"),
		element : function() {
			return j('#screen_register_complete');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : null,
		unload : null,
		orientationchange : null
	},
	forgotPassword : {
		name : "forgotPassword",
		title : getContent("screenForgotPassword"),
		element : function() {
			return j('#screen_forgot_password');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.forgotPassword.init();
		},
		unload : function() {
			handle.forgotPassword.destroy();
		},
		orientationchange : null
	},
	changePassword : {
		name : "changePassword",
		title : getContent("screenChangePassword"),
		element : function() {
			return j('#screen_change_password');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.changePassword.init();
		},
		unload : function() {
			handle.changePassword.destroy();
		},
		orientationchange : null
	},
	maintenance : {
		name : "maintenance",
		title : getContent("screenMaintenance"),
		element : function() {
			return j('#screen_maintenance');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.maintenance.init();
		},
		unload : function() {
			handle.maintenance.destroy();
		},
		orientationchange : null
	},
	account : {
		name : "account",
		title : getContent("screenAccount"),
		element : function() {
			return j('#screen_account');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.account.init();
		},
		unload : function() {
			handle.account.destroy();
		},
		orientationchange : null
	},
	accountSummary : {
		name : "accountSummary",
		title : getContent("screenAccountSummary"),
		element : function() {
			return j('#screen_account_summary')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.accountSummary.init();
		},
		unload : function() {
			handle.accountSummary.destroy();
		},
		orientationchange : null
	},
	deposit : {
		name : "deposit",
		title : getContent("screenDeposit"),
		element : function() {
			return j('#screen_deposit')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.deposit.init();
		},
		unload : function() {
			handle.deposit.destroy();
		},
		orientationchange : null
	},
	withdraw : {
		name : "withdraw",
		title : getContent("screenWithdraw"),
		element : function() {
			return j('#screen_withdraw')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.withdraw.init();
		},
		unload : function() {
			handle.withdraw.destroy();
		},
		orientationchange : null
	},
	closedPositions : {
		name : "closedPositions",
		title : getContent("screenClosedPositions"),
		element : function() {
			return j('#screen_closed_positions')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.closedPositions.init();
		},
		unload : function() {
			handle.closedPositions.destroy();
		},
		orientationchange : null
	},
	statement : {
		name : "statement",
		title : getContent("screenStatement"),
		element : function() {
			return j('#screen_statement')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.statement.init();
		},
		unload : function() {
			handle.statement.destroy();
		},
		orientationchange : null
	},
	contactUs : {
		name : "contactUs",
		title : getContent("screenContactUs"),
		element : function() {
			return j('#screen_contact_us');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.contactUs.init();
		},
		unload : function() {
			handle.contactUs.destroy();
		},
		orientationchange : null
	},
	callToDeposit : {
		name : "callToDeposit",
		title : getContent("screenContactUs"),
		element : function() {
			return j('#screen_call_to_deposit');
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : null,
		unload : null,
		orientationchange : null
	},
	technicalAnalysis : {
		name : "technicalAnalysis",
		title : getContent("screenTechnicalAnalysis"),
		element : function() {
			return j('#screen_technical_analysis')
		},
		tab : "research",
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.technicalAnalysis.init();
		},
		unload : function() {
			handle.technicalAnalysis.destroy();
		},
		orientationchange : null
	},
	reutersNews : {
		name : "reutersNews",
		title : getContent("screenReutersNews"),
		element : function() {
			return j('#screen_reuters_news')
		},
		tab : "research",
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.reutersNews.init();
		},
		unload : function() {
			handle.reutersNews.destroy();
		},
		orientationchange : null
	},
	marketNews : {
		name : "marketNews",
		title : getContent("screenMarketNews"),
		element : function() {
			return j('#screen_market_news')
		},
		tab : "research",
		showInstruments : false,
		isAuthenticationRequired : true,
		load : function() {
			handle.marketNews.init();
		},
		unload : function() {
			handle.marketNews.destroy();
		},
		orientationchange : null
	},
	calendar : {
		name : "calendar",
		title : getContent("screenCalendar"),
		element : function() {
			return j('#screen_financial_calendar')
		},
		tab : "research",
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.calendar.init();
		},
		unload : function() {
			handle.calendar.destroy();
		},
		orientationchange : null
	},
	outlook : {
		name : "outlook",
		title : getContent("screenOutlook"),
		element : function() {
			return j('#screen_oulook')
		},
		tab : "research",
		showInstruments : false,
		isAuthenticationRequired : false,
		load : function() {
			handle.outlook.init();
		},
		unload : function() {
			handle.outlook.destroy();
		},
		orientationchange : null
	},
	chat : {
		name : "chat", 
		title : getContent("screenChat"),
		element : function() {
			return j('#screen_chat')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : null,
		unload : null,
		orientationchange : null
	},
	about : {
		name : "about",
		title : getContent("screenAbout"),
		element : function() {
			return j('#screen_about')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : null,
		unload : null,
		orientationchange : null
	},
	terms : {
		name : "terms",
		title : getContent("screenTerms"),
		element : function() {
			return j('#screen_terms')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : null,
		unload : null,
		orientationchange : null
	},
	disclaimer : {
		name : "disclaimer",
		title : getContent("screenDisclaimer"),
		element : function() {
			return j('#screen_disclaimer')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : null,
		unload : null,
		orientationchange : null
	},
	privacy : {
		name : "privacy",
		title : getContent("screenPrivacy"),
		element : function() {
			return j('#screen_privacy')
		},
		tab : null,
		showInstruments : false,
		isAuthenticationRequired : false,
		load : null,
		unload : null,
		orientationchange : null
	}
};


var screenManager = {
	nextScreen : null,
	lastScreen : null,
	show : function(screen) {
		
		// Show screen
		if(screen != undefined) {
			
			if(this.lastScreen != null 
					&& this.lastScreen != screen) {
					
				this.lastScreen
					.element()
					.removeClass('active');
				
				// Call unload event if exist
				if(this.lastScreen.unload != undefined 
						&& typeof this.lastScreen.unload == 'function') {
					this.lastScreen.unload();
				}
				
				
			}
			
			// Fix for unload
			if(this.lastScreen != null 
					&& this.lastScreen == screen) {
				
				// Call unload event if exist
				if(this.lastScreen.unload != undefined 
						&& typeof this.lastScreen.unload == 'function') {
					this.lastScreen.unload();
				}
			}
			// hide iOS address bar
			silentScroll(true);
			
			// Check if screen required authentication
			if(screen.isAuthenticationRequired 
					&& !isAuthenticated) {
				
				// Show screen login before
				screens.login
					.element()
					.addClass('active');
				
				// Switch to off all tabs
				j('#list_footer_tabs li').each(function(i, el) {
					var tab = j(el).find('span');
						tab.removeClass('active');
				});
				
				// hide instruments
				j('.instrument-types-placeholder').hide();
				
				if(screens.login.load != undefined 
						&& typeof screens.login.load == 'function')
					screens.login.load();
				
				if(typeof (dataLayer)!="undefined"  ){
                    dataLayer.push({page: 'login'});
                    dataLayer.push({'event': 'page_ready'});
                }
				
				try {
					ezMeasurePerformanceNew.measureData.tabName = 'login';
					ezMeasurePerformanceNew.measureTiming();
                    ezOnlineUser.buildOnlineUserData();
					ezOnlineUser.sendOnlineUserDataNoRepeat();
					
				} catch(e) {}
				
				// Update screen to quie
				this.nextScreen = screen;
				this.lastScreen = screens.login;
				
			} else {
				
				// Show screen
				screen
					.element()
					.addClass('active');
				
				// Switch tabs on footer menu
				if(screen.tab != null) {
					// Switch on the current tab if exists
					j('#list_footer_tabs li').each(function(i, el) {
						var currentTab = j(el).find('span');
						if(currentTab.hasClass(screen.tab)) {
							currentTab.addClass('active');
							if(screenManager.lastScreen != undefined) {
								if(screenManager.lastScreen.tab != null && screenManager.lastScreen.tab != screen.tab) {
									j('#list_footer_tabs li').each(function(z, last) {
										var lastTab = j(last).find('span');
										if(lastTab.hasClass(screenManager.lastScreen.tab)) {
											lastTab.removeClass('active');
										}
									});
								}
								
							}
							
						}
					});
				} else {
					
					// Switch to off all tabs
					j('#list_footer_tabs li').each(function(i, el) {
						var tab = j(el).find('span');
							tab.removeClass('active');
					});
					
				}

				// show instruments
				if(screen.showInstruments) {
					j('.instrument-types-placeholder').show();
				} else {
					j('.instrument-types-placeholder').hide();
				}
				
				if (screen.load != undefined 
						&& typeof screen.load == 'function') 
					screen.load();
				
				this.lastScreen = screen;
				
				// each next screen come here
				if(screen.name != "maintenance") {
					
					try {
						window.History.pushState({screen:screen.name}, screen.title, "?state=" + screen.name + (j.getUrlParam('l') != "" ? "&l=" + j.getUrlParam('l') : "") + (j.getUrlParam('i') != "" ? "&i=" + j.getUrlParam('i') : "") + (j.getUrlParam('mytoken') != "" ? "&mytoken=" + j.getUrlParam('mytoken') : ""));
					} catch(e) {}
					
                    
                    var nonGAScreens = ["register", "register_step2", "register_step3"];

					if(typeof (dataLayer)!="undefined" && nonGAScreens.indexOf(screen.name) == -1){
                        dataLayer.push({page: screen.name});
                        if(isFirstDeposit) {
                            isFirstDeposit = false;
                            dataLayer[0].first_deposit = true;
                            dataLayer.push({'event': 'onFirstDepositCompleted'});
                        }
                        else {
                            dataLayer[0].first_deposit = false;
                        }
                        dataLayer.push({'event': 'page_ready'});
                    }
					try {
						ezMeasurePerformanceNew.measureData.tabName = screen.name;
						if(jsapi.accounts.user.userInfo.userId != null) {
							ezMeasurePerformanceNew.measureData.QoS = jsapi.accounts.user.userInfo.userId;
						}
						ezMeasurePerformanceNew.measureTiming();
                        ezOnlineUser.buildOnlineUserData();
						ezOnlineUser.sendOnlineUserDataNoRepeat();
					} catch(e) {}
					
				}
				
				
			}
			
		} else {
			// screen not exist
			if (window.console) {
				window.console.log("screen not exist");
			}
		}
	},
	hide : function() {
		// Hide screen
		if(this.lastScreen == screen) {
			this.lastScreen = null;			
		}		
	}
};

// To remove
var fakeBody = jQuery( "body" ),
	fbCSS = fakeBody[0].style,
	vendors = ['webkit','moz','o'],
	webos = window.palmGetResource || window.PalmServiceBridge, //only used to rule out scrollTop 
	bb = window.blackberry; //only used to rule out box shadow, as it's filled opaque on BB

var loader = {
	loader : null,
	overlay : null,
	show : function() {
		this.loader = j('.loader');
		this.overlay = j('.overlay');
		
		// ("pageXOffset" in window || "scrollTop" in document.documentElement || "scrollTop" in fakeBody[0]) && 
		// jQuery(window).scrollTop() + 
		
		this.loader.show();
		this.overlay.show();
		
		this.loader.css({
			/*top: (jQuery(window).height() / 2) - (this.loader.height() / 2),*/
			top : ((jQuery(window).height() + (isIDevice ? 60 : 0)) / 2) - (this.loader.height() / 2),
			left : (jQuery(window).width() / 2) - (this.loader.width() / 2)
		});
		
	},
	hide : function() {
		if(this.loader != null 
				&& this.overlay != null) {
			
			this.loader.hide();
			this.overlay.hide();
			
			// reset objects
			this.loader = null;
			this.overlay = null;
		}
		
		
	}
};

// History state
// RESET -> document.location.href = document.location.href.replace(/[\#\?].*/,"");
// Bind to popstate

/*
window.History.debug.enable = false;
j(window).bind("statechange", function(e) {
	var State = window.History.getState();

	// log that this event was fired, and the current URL
	if (window.console && window.console.log) {
		console.log("popstate", State, window.location.href);
	}
  
	
	//if(typeof State.data.screen !== 'undefined') {
	//	if(screenManager.lastScreen != null) {
	//		if(screenManager.lastScreen.name != State.data.screen) {
	//			screenManager.show(screens[State.data.screen]);
	//		}
	//	}
	//}
	
});
*/


var getMarketInfoSettings = function() {

	common.isAuthenticated = false;
	jsapi.marketInfo.settings.getSettings({
		success : function(data) {
			// Show quotes screen
			loader.hide();
			
			var state = getScreenState();
			if(state != "") {
				// var currentLocation = document.location.href.replace(/[\#\?].*/,"");
				// location.href = currentLocation + "?state=" + getScreenState() + "&l="+ j(this).val();
				screenManager.show(screens[state]);
			} else {
				screenManager.show(screens.quotes);
			}
			
		},
		error: function(error) {
			
			loader.hide();
			
			if(error == "GeneralError") {
				// Logger
				if (window.console) {
					window.console.log(getContent(error));
				}
			}
			
		}
	});
	
};

function hideURLbar(){
	window.scrollTo(0,1);
}

// document ready
j(function() {
	
	page = document.getElementById('page');
	instrumentType = (j.getUrlParam('i') != "" ? j.getUrlParam('i') : 1);
	
	// get data from deposit engine
	j.cookie('drt', JSON.stringify({
		isActivationDeposit : (j.getUrlParam('activationPayent') == "1" ? true : false),
		isShowThankYou : (j.getUrlParam('IsShowThankYou') == "1" ? true : false),
		isShowThankYouPending : (j.getUrlParam('IsShowPending') == "1" ? true : false),
		isShowError : (j.getUrlParam('IsShowError') == "1" ? true : false),
		isIdent : (j.getUrlParam('isIdent') == "1" ? true : false),
		transId : j.getUrlParam('transId'),
		clearId : j.getUrlParam('clearId'),
		processAmount : j.getUrlParam('processAmount'),
		baseAmount : j.getUrlParam('baseAmount')
	}));
	
	
	// Main website url
	var webSiteUrl = (common.webSiteUrl != undefined ? common.webSiteUrl : "http://www.easy-forex.com/");
	j("#website-link").attr({ "href" : webSiteUrl + "?keepregular=2" });
	
	// Copyright
	j(".risk-warning-copyright").html(getContent("copyright"));
	j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_int_phone.pdf");

    // clear list
    j('#cu_list_phones').empty();
	j('#ctd_list_phones').empty();
    for(var i = 0; i < messages.phones.length; i++) {
		j('#cu_list_phones').append("<li " + ((i == (messages.phones.length - 1)) ? "class=\"last-item\"" : "") + " ><span class=\"left-side\">" + messages.phones[i].officeName + "</span><span class=\"right-side\"><a style=\"text-decoration: none\" href=\"tel:" + messages.phones[i].phone + "\">" + messages.phones[i].phone + "</a></span></li>");
		j('#ctd_list_phones').append("<li " + ((i == (messages.phones.length - 1)) ? "class=\"last-item\"" : "") + " ><span class=\"left-side\">" + messages.phones[i].officeName + "</span><span class=\"right-side\"><a style=\"text-decoration: none\" href=\"tel:" + messages.phones[i].phone + "\">" + messages.phones[i].phone + "</a></span></li>");
    }
	
	if (isAndroid) {
		
		j('select').addClass("android");
	
		// Android's browser adds the scroll position to the innerHeight, just to
		// make this really fucking difficult. Thus, once we are scrolled, the
		// page height value needs to be corrected in case the page is loaded
		// when already scrolled down. The pageYOffset is of no use, since it always
		// returns 0 while the address bar is displayed.
		//page.style.height = window.innerHeight + 'px';
		
		
		
		//(window.onresize = function() {
			
			
		//	page.style.height = window.innerHeight + 'px';
			
		//})();
		
		/*
		window.onscroll = function() {
			page.style.height = window.innerHeight + 'px';
		}
		*/
		
	}
	
	if (isIDevice) {
	
		// iOS reliably returns the innerWindow size for documentElement.clientHeight
		// but window.innerHeight is sometimes the wrong value after rotating
		// the orientation
		var height = document.documentElement.clientHeight;
		// Only add extra padding to the height on iphone / ipod, since the ipad
		// browser doesn't scroll off the location bar.
		if(!(/crios/gi).test(navigator.appVersion)) {
			if (!fullscreen) height += 60;
		}
		page.style.height = height + 'px';
			
		addEventListener("load", function() {
			setTimeout(hideURLbar, 0);
			setTimeout(hideURLbar, 500);
		}, false);
			
	}
	
	// Orientationchange event
	jQuery(window).bind("orientationchange resize", function(event){
		// Add orientation class to HTML element on flip/resize.
		if(event.orientation){
			if (isIDevice) {
				// iOS reliably returns the innerWindow size for documentElement.clientHeight
				// but window.innerHeight is sometimes the wrong value after rotating
				// the orientation
				var height = document.documentElement.clientHeight;
				// Only add extra padding to the height on iphone / ipod, since the ipad
				// browser doesn't scroll off the location bar.
				if(!(/crios/gi).test(navigator.appVersion)) {
					if (!fullscreen) height += 60;
				}
				page.style.height = height + 'px';
			}
			// orientation
			jQuery('html')
				.removeClass("portrait landscape")
				.addClass(event.orientation);
				
			//
			setTimeout(function() {
				// Hide iOS address bar
				silentScroll(true);
				// Call orientation change event for current screen
				if(screenManager.lastScreen != undefined) {
					if(screenManager.lastScreen.orientationchange != undefined 
							&& typeof screenManager.lastScreen.orientationchange == 'function') {
						screenManager.lastScreen.orientationchange();
					}
				}
				
			}, 120);
		}
	});
	
	// Set orientation
	jQuery('html')
		.removeClass("portrait landscape")
		.addClass(getOrientation());
	
	
	// languages
	if(j.getUrlParam('l') != "") {
        try {
			var options = j('#select_change_language')[0].options;
			var selectedIndex = 0;
			for(var i = 0; i < options.length; i++) {
				if(options[i].value == j.getUrlParam('l')) {
					selectedIndex = i;
				}
			}
			j('#select_change_language')[0].selectedIndex = selectedIndex;
			setCulture(j.getUrlParam('l').toLowerCase());
        } catch(e){}
	}
	
	j('#select_change_language').live('change', function() {
        var currentLocation = document.location.href.replace(/[\#\?].*/,"");
		setCulture(j(this).val().toLowerCase());
        if(typeof ezfxCordova != 'undefined') {
            if (ezfxCordova.currentLanguage != j(this).val()) {
	            ezfxCordova.currentLanguage = j(this).val();
	            window.localStorage.setItem('currentLanguage', ezfxCordova.currentLanguage);
	            if (typeof ezfxCordova.fillContentFromLocalStorage == 'function') {
	            	ezfxCordova.fillContentFromLocalStorage();      	
	            }
	        }
        } else { 
            location.href = currentLocation + "?state=" + screenManager.lastScreen.name + "&l="+ j(this).val() + (j.getUrlParam('i') != "" ? "&i=" + j.getUrlParam('i') : "");
		}		
	});
	
	// jsapi declaration
	jsapi = new easyforex({
		authentication : {
			sessionId : j.cookie('sid'),
			isAuthenticated : (j.cookie('sid') != null ? true : false),
			success : function(data) {
				// TODO
			},
			error : function(error) {
				// TODO
			}
		},
		error : function(error) { 
			
			// If error such as timeout -> show maintenance or another screen
			if(error.error != undefined) {
				if(error.error == "timeout") {
					screenManager.show(screens.maintenance);
					return;
				}
			}
			
			// If application is not loggin and user logged in for trading service -> 
			// means server return error on session id -> go to login
			if(error == errors.applicationNotLoggedIn 
				&& isAuthenticated) {
				
				common.isAuthenticated = false;
				
				isAuthenticated = false;
				isEnableFractionalPips = false;
				isActive = false;
				regTicketId = null;
				userStatus = null;
				needToPassTest = false;
				isSuitabilityTestPassed = true;
				regionId = 4;
				accountBaseCurrency = null;
				withdrawalAvailable = null;
							
				j('.before-login').show();
				j('.after-login').hide();
				
				screenManager.show(screens.login);
				
			}
			
			// Logger
			if (window.console) {
				window.console.log(error);
			}
		} 
	});
	
	if(j.cookie('sid') != null) {
	
		isAuthenticated = true;
		j('.before-login').hide();
		j('.after-login').show();
	
		jsapi.accounts.user.getUserInfo({
			success: function (b) {
			
				isEnableFractionalPips = b.isEnableFractionalPips;
				accountBaseCurrency = b.accountBaseCurrency;
				withdrawalAvailable = b.withdrawalAvailable;
				allowWithdrawal = b.allowWithdrawal;
				allowDeposit = b.allowDeposit; 
				isActive = b.isActive;
				isDemo = b.isDemo;
				userStatus = b.userStatus;
				needToPassTest = b.needToPassTest;
				isSuitabilityTestPassed = b.isSuitabilityTestPassed;
				regionId = b.regionId;
				regTicketId = b.regTicketId;
				
				// init
				j('.label-user-id').text(jsapi.accounts.user.userInfo.userId);
				
				// If user is demo show demo label
				if(isDemo) {
					j('.label-demo').show();
				}
				
				switch(regionId) {
					case 1: {
						// Asia Pacific
						j(".risk-warning-copyright").html(getContent("copyrightAsiaPacific"));
						j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_au_phone.pdf");
						
						j('.risk-warning-note').show();
						j('.risk-warning-note-eu').hide();
						break;
					}
					case 11 : {
						// Europe
						j(".risk-warning-copyright").html(getContent("copyrightEurope"));
						j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_eu_phone.pdf");
						
						j('.risk-warning-note').hide();
						j('.risk-warning-note-eu').show();
						
						break;
					}
					default : {
						// Default
						j(".risk-warning-copyright").html(getContent("copyright"));
						j(".link_terms_and_conditions").attr("href", "uploads/terms_and_conditions_int_phone.pdf");
						
						j('.risk-warning-note').show();
						j('.risk-warning-note-eu').hide();
					}
				}
				
				jsapi.trading.getTrading({
					success: function (a) {
						loader.hide();
						
						var state = getScreenState();

						if(state != "") {
							if(state == "closeDeal" 
								|| state == "modifyDeal") {
								screenManager.show(screens.myPositions);
							} else if (state == "registerComplete" || state == "registerDemo" || state == "login" || state == "forgotPassword") {
								screenManager.show(screens.quotes);
							} else {
								screenManager.show(screens[state]);
							}
						} else {
							screenManager.show(screens.quotes);
						}
						
					},
					error: function (a) {
						loader.hide();
						
						if(a != errors.applicationNotLoggedIn) {
							var modal = new lightFace({
								title : "",
								message : getContent(a),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
						
					}
				});
				
			},
			error: function (a) {
				loader.hide();
				
				
				// Clear cookies
				j.cookie("sid", null);
				getMarketInfoSettings();
				
				if(a != errors.applicationNotLoggedIn) {				
					var modal = new lightFace({
						title : "",
						message : getContent(a),
						actions : [
						   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
						],
						overlayAll : true
					});
				}
				
			}
		});
		
		
	} else {
		
		getMarketInfoSettings();
		
		var defaultPortal= j.cookie('m_defaultPortal')!=null? parseInt(j.cookie('m_defaultPortal')) : null;
				
		if(defaultPortal==null) {
			jsapi.marketInfo.geoLocation.getCountryLocationInfo({
					success : function(data) {
						j.cookie('m_defaultPortal', data.defaultPortalId, { expires: 7 });						
						if(data.defaultPortalId==64){//eu
							j('.risk-warning-note').hide();
							j('.risk-warning-note-eu').show();
						}
						else {
							j('.risk-warning-note').show();
							j('.risk-warning-note-eu').hide();
						}
					},
					error: function(error) {} 
			});		
		}
		else {
			if(defaultPortal==64){//eu
				j('.risk-warning-note').hide();
				j('.risk-warning-note-eu').show();
			}
			else {
				j('.risk-warning-note').show();
				j('.risk-warning-note-eu').hide();
			}
		}		
	}
	
	// change instrument type
	j('.products-box')
	.unbind('click')
	.click(function() {
		if(j('ul.all-products').is(':hidden')) {
			j(this).addClass('expanded');
		} else {
			j(this).removeClass('expanded');
		}
		j('ul.all-products').toggle(j('ul.all-products').is(':hidden'));
	});
	
	instrumentType = (j.getUrlParam('i') != "" ? j.getUrlParam('i') : 1);
	
	j('ul.all-products li').each(function(i, el){			
		
		if((instrumentType - 1) == i) {
			j(el).addClass('active');
			j(".selected-product").text(getContent("instumentTypes")[j(el).attr('instrument')]);
		}
		j(el).unbind('click')
		.click(function() {
		
			if(j(this).attr('instrument') != "4") {
			
				j('.products-box').removeClass('expanded');
			
				j('ul.all-products li').each(function(i, el){
					j(this).removeClass('active');
				});
				
				j(this).addClass('active');
				j(".selected-product").text(getContent("instumentTypes")[j(this).attr('instrument')]);
				
				j('ul.all-products').toggle(j('ul.all-products').is(':hidden'));
					
				try {
					window.History.pushState({screen:screenManager.lastScreen.name}, screenManager.lastScreen.title, "?state=" + screenManager.lastScreen.name + (j.getUrlParam('l') != "" ? "&l=" + j.getUrlParam('l') : "") + "&i=" + j(this).attr('instrument'));
				} catch(e) {}
				
				lastPair = null;
				instrumentType = j(this).attr('instrument');
				
				screenManager.lastScreen.unload();
				screenManager.lastScreen.load();
				
			} else {
			
				j('.products-box').removeClass('expanded');
				j('ul.all-products').toggle(j('ul.all-products').is(':hidden'));
								
				if(jsapi.accounts.user.userInfo.isOptionsEnabled){
					loader.show();
					jsapi.ore.login({
						source: "MobileSite",
						cultureId: cultureId,
						success: function (a) {
							loader.hide();
							location.href = a.url;
						},
						error: function (a) {
							loader.hide();
							var modal = new lightFace({
								title : "",
								message : getContent("optionsCommingSoon"),
								actions : [
								   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
								],
								overlayAll : true
							});
						}
					});	
				}
				else{
					var modal = new lightFace({
						title : "",
						message : getContent("optionsCommingSoon"),
						actions : [
						   { label : getContent("ok"), fire : function() { modal.close() }, color: "green" }
						],
						overlayAll : true
					});
				}
													
			}
		
		});
	});
	
	j('.instrument-types').click(function(){ return false; });
	
	jQuery(document).click(function(){
		if(!j('ul.all-products').is(':hidden')) {
			j('.products-box').trigger('click');
		}
	}); 

});


function insideViewerPlot(context, bgProperties) { return this.constructor(context, bgProperties); }
insideViewerPlot.prototype = {
   context: null,
   title: null,
   backgroundWidth: null,
   backgroundHeight: null,
   backgroundColor: null,
   barWidth: null,
   barHeight: null,
   barCornerRadius: null,

   constructor: function (context, bgProperties) {
       this.context = context;
       this.title = bgProperties.title;
       this.backgroundWidth = bgProperties.bgWidth;
       this.backgroundHeight = bgProperties.bgHeight;
       this.backgroundColor = bgProperties.bgColor;
       this.barWidth = bgProperties.barWidth;
       this.barHeight = bgProperties.barHeight;
       this.barCornerRadius = bgProperties.barCornerRadius;
   },
   drawRoundedRect: function (x, y, width, height, borderWidth, radius, borderStyle, fillStyle) {

       this.context.beginPath();
       this.context.moveTo(x + radius, y);
       this.context.lineTo(x + width - radius, y);
       this.context.quadraticCurveTo(x + width, y, x + width, y + radius);
       this.context.lineTo(x + width, y + height - radius);
       this.context.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
       this.context.lineTo(x + radius, y + height);
       this.context.quadraticCurveTo(x, y + height, x, y + height - radius);
       this.context.lineTo(x, y + radius);
       this.context.quadraticCurveTo(x, y, x + radius, y);

       if (fillStyle != undefined && fillStyle != null) {
           this.context.fillStyle = fillStyle;
           this.context.fill();
       }

       if (borderStyle != undefined && borderStyle != null) {
           this.context.strokeStyle = borderStyle;
           this.context.lineWidth = borderWidth;
           this.context.stroke();
       }

       this.context.closePath();
   },
   drawBackground: function () {
       this.context.fillStyle = this.backgroundColor;
       this.context.fillRect(0, 0, this.backgroundWidth, this.backgroundHeight);
   },
   plotSimpleBar: function(barProperties) {

       var addBarText = function (root, barProperties) {

           var leftWidth = Math.round(root.barWidth * (barProperties.leftRatio / 100) - barProperties.gap);
           var xTxt = barProperties.x + Math.round(leftWidth / 2);
           var yTxt = barProperties.y + Math.round(root.barHeight / 2);

           root.context.font = "bold 15px arial";
           root.context.textBaseline = "middle";
           root.context.textAlign = "center";
           root.context.fillStyle = "#3f5979";
           root.context.fillText(barProperties.leftValue, xTxt, yTxt);

           var rightWidth = Math.round(root.barWidth * (barProperties.rightRatio / 100) - barProperties.gap);

           xTxt = barProperties.x + leftWidth + barProperties.gap + rightWidth / 2;
           root.context.fillStyle = "#529b00";
           root.context.fillText(barProperties.rightValue, xTxt, yTxt);

       };
       
       var fillBar = function (root, barProperties) {

           if (barProperties.leftRatio + barProperties.rightRatio != 100) {
               return;
           }

           if (barProperties.leftRatio > 86) {
               barProperties.leftRatio = 86;
               barProperties.rightRatio = 14;
           }
           else if (barProperties.rightRatio > 86){
               barProperties.rightRatio = 86;
               barProperties.leftRatio = 14;
           }

           var grdLeft = root.context.createLinearGradient(barProperties.x, barProperties.y, barProperties.x, barProperties.y + root.barHeight);
           grdLeft.addColorStop(0, "#fdfefe");
           grdLeft.addColorStop(0.49, "#ecf1f6");
           grdLeft.addColorStop(0.5, "#dde4ed");
           grdLeft.addColorStop(1, "#b8bec5");

           var grdRight = root.context.createLinearGradient(barProperties.x + 2, barProperties.y, barProperties.x + 2, barProperties.y + root.barHeight);
           grdRight.addColorStop(0, "#fbfef9");
           grdRight.addColorStop(0.49, "#dbf3c0");
           grdRight.addColorStop(0.5, "#bfe891");
           grdRight.addColorStop(1, "#9fc179");

           var leftWidth = Math.round(root.barWidth * (barProperties.leftRatio / 100) - barProperties.gap);

           root.context.beginPath();
           root.context.moveTo(barProperties.x + root.barCornerRadius, barProperties.y);

           root.context.lineTo(barProperties.x + leftWidth, barProperties.y);
           root.context.lineTo(barProperties.x + leftWidth, barProperties.y + root.barHeight);
           root.context.lineTo(barProperties.x + root.barCornerRadius, barProperties.y + root.barHeight);

           root.context.quadraticCurveTo(barProperties.x, barProperties.y + root.barHeight, barProperties.x, barProperties.y + root.barHeight - root.barCornerRadius);
           root.context.lineTo(barProperties.x, barProperties.y + root.barCornerRadius);
           root.context.quadraticCurveTo(barProperties.x, barProperties.y, barProperties.x + root.barCornerRadius, barProperties.y);

           root.context.fillStyle = grdLeft;
           root.context.fill();
           root.context.closePath();

           var rightWidth = Math.round(root.barWidth * (barProperties.rightRatio / 100) - barProperties.gap);
           root.context.beginPath();
           root.context.moveTo(barProperties.x + leftWidth + barProperties.gap, barProperties.y);

           root.context.lineTo(barProperties.x + root.barWidth - root.barCornerRadius, barProperties.y);
           root.context.quadraticCurveTo(barProperties.x + root.barWidth, barProperties.y, barProperties.x + root.barWidth, barProperties.y + root.barCornerRadius);
           root.context.lineTo(barProperties.x + root.barWidth, barProperties.y + root.barHeight - root.barCornerRadius);
           root.context.quadraticCurveTo(barProperties.x + root.barWidth, barProperties.y + root.barHeight, barProperties.x + root.barWidth - root.barCornerRadius, barProperties.y + root.barHeight);
           root.context.lineTo(barProperties.x + leftWidth + barProperties.gap, barProperties.y + root.barHeight);
           root.context.lineTo(barProperties.x + leftWidth + barProperties.gap, barProperties.y);

           root.context.fillStyle = grdRight;
           root.context.fill();

           root.context.closePath();
       };

       this.drawRoundedRect(barProperties.x, barProperties.y, this.barWidth, this.barHeight, 1, this.barCornerRadius, "#cccccc");
       fillBar(this, barProperties);
       addBarText(this, barProperties);

       var xTxt = barProperties.x;
       var yTxt = this.backgroundHeight - (this.backgroundHeight - this.barHeight) / 2;

       this.context.font = "normal 11px arial";
       this.context.textBaseline = "middle";
       this.context.textAlign = "left";
       this.context.fillStyle = "#333";
       this.context.fillText(barProperties.leftLabel, xTxt, yTxt);

       xTxt = barProperties.x + this.barWidth;
       this.context.textAlign = "right";
       this.context.fillText(barProperties.rightLabel, xTxt, yTxt);
   },
   plotStructure: function (barProperties) {

       var drawIndexLine = function (root, color, line_width, x, y, height) {

           root.context.beginPath();
           root.context.strokeStyle = color;
           root.context.lineWidth = line_width;
           root.context.moveTo(x, y);
           root.context.lineTo(x, y + height);
           root.context.stroke();
           root.context.closePath();
       };
       
       var getAverageRateItem = function (arrData) {
           var result = null;
           for (var i = 0; i < arrData.length; i++) {
               if (arrData[i].name == "currentRate") {
                   result = arrData[i];
                   break;
               }
           }
           return result;
       };

       var width = 0;
       var xFrom = 0;
       var xSlrTxt = 0, xTprTxt = 0, xAvgRate = 0, yLabel = 0, yBox = 0; xTxt = 0, yBoxRate = 0, rnd10 = 0, tmpX = 0 ;
       var arrData = [];

       this.drawRoundedRect(barProperties.x, barProperties.y, this.barWidth, this.barHeight, 1, this.barCornerRadius, "#cccccc");

       arrData.push({ name: "slr", color: "#cc0000", value: barProperties.slr, x: 0, width: 2 });
       arrData.push({ name: "tpr", color: "#339900", value: barProperties.tpr, x: 0, width: 2 });
       arrData.push({ name: "avgRate", color: "#999999", value: barProperties.avgRate, x: 0, width: 2 });
       arrData.push({ name: "currentRate", color: "#000000", value: barProperties.currentRate, x: 0, width: 2 });

       arrData.sort(function (a, b) {
           return a.value - b.value;
       });

       rnd10 = Math.round(Math.random() * 20);
       arrData[0].x = barProperties.x + 15 + rnd10;
       arrData[3].x = barProperties.x + 225 - 15 - rnd10;
      
       tmpX = Math.round(arrData[1].value * arrData[3].x / arrData[3].value);
       if (tmpX <= arrData[0].x)
           tmpX = arrData[0].x + 2;

       arrData[1].x = tmpX;

       tmpX = Math.round(arrData[2].value * arrData[3].x / arrData[3].value);
       if (tmpX <= arrData[1].x)
           tmpX = arrData[1].x + 2;

       arrData[2].x = tmpX;
      
       var grd1st = this.context.createLinearGradient(barProperties.x, barProperties.y, barProperties.x, barProperties.y + this.barHeight);
       grd1st.addColorStop(0, "#fdfefe");
       grd1st.addColorStop(0.49, "#ecf1f6");
       grd1st.addColorStop(0.5, "#dde4ed");
       grd1st.addColorStop(1, "#b8bec5");                                

       width = arrData[0].x - barProperties.x;
       xFrom = barProperties.x;
       
       this.context.beginPath();
       this.context.moveTo(xFrom + this.barCornerRadius, barProperties.y);

       this.context.lineTo(xFrom + width, barProperties.y);
       this.context.lineTo(xFrom + width, barProperties.y + this.barHeight);
       this.context.lineTo(xFrom + this.barCornerRadius, barProperties.y + this.barHeight);

       this.context.quadraticCurveTo(barProperties.x, barProperties.y + this.barHeight, barProperties.x, barProperties.y + this.barHeight - this.barCornerRadius);
       this.context.lineTo(barProperties.x, barProperties.y + this.barCornerRadius);
       this.context.quadraticCurveTo(barProperties.x, barProperties.y, barProperties.x + this.barCornerRadius, barProperties.y);

       this.context.fillStyle = grd1st;
       this.context.fill();
       this.context.closePath();
       drawIndexLine(this, arrData[0].color, arrData[0].width, arrData[0].x - arrData[0].width/2, barProperties.y, this.barHeight); // profit loss - green
	   
       
       var grd2nd = this.context.createLinearGradient(barProperties.x, barProperties.y, barProperties.x, barProperties.y + this.barHeight);
       grd2nd.addColorStop(0, "#f9fafb");
       grd2nd.addColorStop(0.49, "#bec9d4");
       grd2nd.addColorStop(0.5, "#8ea0b2");
       grd2nd.addColorStop(1, "#768594");    

       width = arrData[1].x - arrData[0].x;
       xFrom = arrData[0].x;
       
       this.context.fillStyle = grd2nd;
       this.context.fillRect(xFrom, barProperties.y, width ,this.barHeight);                
       drawIndexLine(this, arrData[1].color, arrData[1].width, arrData[1].x - arrData[1].width/2, barProperties.y, this.barHeight); // current rate line - black
       
       width = arrData[2].x - arrData[1].x;
       xFrom = arrData[1].x;                
       
       this.context.fillRect(xFrom, barProperties.y, width ,this.barHeight);
       drawIndexLine(this, arrData[2].color, arrData[2].width, arrData[2].x - arrData[2].width / 2, barProperties.y, this.barHeight);
       
       var grd3nd = this.context.createLinearGradient(barProperties.x, barProperties.y, barProperties.x, barProperties.y + this.barHeight);
       grd3nd.addColorStop(0, "#fafcf6");
       grd3nd.addColorStop(0.49, "#c1dea1");
       grd3nd.addColorStop(0.5, "#93c45b");
       grd3nd.addColorStop(1, "#7aa34c");                 
       
       width = arrData[3].x - arrData[2].x;
       xFrom = arrData[2].x;
       
       this.context.fillStyle = grd3nd;
       this.context.fillRect(xFrom, barProperties.y, width ,this.barHeight);
       drawIndexLine(this, arrData[3].color, arrData[3].width, arrData[3].x - arrData[3].width / 2, barProperties.y, this.barHeight); // stop loss line - red

       var grd4th = this.context.createLinearGradient(barProperties.x, barProperties.y, barProperties.x, barProperties.y + this.barHeight);
       grd4th.addColorStop(0, "#fbfef9");
       grd4th.addColorStop(0.49, "#dbf3c0");
       grd4th.addColorStop(0.5, "#bfe891");
       grd4th.addColorStop(1, "#9fc179");      

       width = barProperties.x + this.barWidth - arrData[3].x;
	   xFrom = arrData[3].x;

       this.context.beginPath();
       this.context.moveTo(barProperties.x + this.barWidth - width, barProperties.y);

       this.context.lineTo(barProperties.x + this.barWidth - this.barCornerRadius, barProperties.y);
       this.context.quadraticCurveTo(barProperties.x + this.barWidth, barProperties.y, barProperties.x + this.barWidth, barProperties.y + this.barCornerRadius);
       this.context.lineTo(barProperties.x + this.barWidth, barProperties.y + this.barHeight - this.barCornerRadius);
       this.context.quadraticCurveTo(barProperties.x + this.barWidth, barProperties.y + this.barHeight, barProperties.x + this.barWidth - this.barCornerRadius, barProperties.y + this.barHeight);
       this.context.lineTo(barProperties.x + this.barWidth - width, barProperties.y + this.barHeight);
       this.context.lineTo(barProperties.x + this.barWidth - width, barProperties.y);

       this.context.fillStyle = grd4th;
       this.context.fill();
       this.context.closePath();
        

		/*
       var item = getAverageRateItem(arrData);
		var root = this;
		var imageObj = new Image();
		j(imageObj).attr("src", "images/current_rate_bg.gif");

		j(imageObj).load(function () {
			
			var xImg = 0;
		   if (barProperties.direction == "left") {
				xImg = barProperties.x + 225 - 15 - rnd10; // item.x - parseInt(this.width) / 2;
			} else {
				xImg = barProperties.x + 15 + rnd10;
			}
			
			
			var yImg = barProperties.y + (root.barHeight / 2) - (parseInt(this.height) / 2);
			var yTxt = barProperties.y + (root.barHeight / 2) +1 ;

			//alert(xImg + "__________" + item.value + "________" + item.x);
			
			root.context.drawImage(this, (xImg - parseInt(this.width) / 2), yImg);
			root.context.font = "normal 10px arial";
			root.context.fillStyle = "#000000";
			root.context.textAlign = "center";
			root.context.textBaseline = "middle";
			//root.context.fillText(item.value, item.x, yTxt);
			
			root.context.fillText(item.value, xImg, yTxt);

		});
		*/
		
		var item = getAverageRateItem(arrData);
			                var root = this;
			                var imageObj = new Image();
			                j(imageObj).attr("src", "images/current_rate_bg.gif");

			                j(imageObj).load(function () {
			                    
			                    var xImg = item.x - parseInt(this.width) / 2;
			                    var yImg = barProperties.y + (root.barHeight / 2) - (parseInt(this.height) / 2);
			                    var yTxt = barProperties.y + (root.barHeight / 2) +1 ;

			                    root.context.drawImage(this, xImg, yImg);
			                    root.context.font = "normal 10px arial";
			                    root.context.fillStyle = "#000000";
			                    root.context.textAlign = "center";
			                    root.context.textBaseline = "middle";
			                    root.context.fillText(item.value, item.x, yTxt);

			                });
	   
       xTxt = barProperties.x;
       yLabel = barProperties.y + this.barHeight + 9;
       

       xAvgRate = barProperties.x + this.barWidth / 2;

       if (barProperties.direction == "left") {

           xSlrTxt = barProperties.x + 26;
           xTprTxt = barProperties.x + this.barWidth - 26;
       }
       else {

           xSlrTxt = barProperties.x + this.barWidth - 26;
           xTprTxt = barProperties.x + 26;
       }

       this.context.font = "normal 11px arial";
       this.context.textBaseline = "middle";
       this.context.fillStyle = "#333333";
       this.context.textAlign = "center";
       this.context.fillText(barProperties.slrLabel, xSlrTxt, yLabel);
       this.context.fillText(barProperties.tprLabel, xTprTxt, yLabel);
       this.context.fillText(barProperties.avgLabel, xAvgRate, yLabel);

       yBox = yLabel + 9;

       this.drawRoundedRect(xSlrTxt - 26, yBox, 54, 17, 2, 4, "#cccccc", "#cc0000");
       this.drawRoundedRect(xAvgRate - 27, yBox, 54, 17, 2, 4, "#ccccc", "#999999");
       this.drawRoundedRect(xTprTxt - 27, yBox, 54, 17, 2, 4, "#cccccc", "#339900");

       yBoxRate = yBox + 9.5;

       this.context.font = "bold 11px arial";
       this.context.fillStyle = "#ffffff";
       this.context.textAlign = "center";
       this.context.textBaseline = "middle";
       this.context.fillText(barProperties.slr, xSlrTxt, yBoxRate);
       this.context.fillText(barProperties.tpr, xTprTxt, yBoxRate);
       this.context.fillText(barProperties.avgRate, xAvgRate, yBoxRate);               

   }
};
