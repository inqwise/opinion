;
(function($) {
	collectorSettings = function(givenOptions) {
		
		var enableRestrictions = false;
		var dateTimeNow = null;
		var currentDate = {
			day : null,
			month : null,
			year : null,
			hours : null,
			minutes : null
		};
		
		var certainDate = {
			day : null,
			month : null,
			year : null,
			hours : null,
			minutes : null
		};

		var quotaReached = 0;
		var password = "";
		var newPassword = "";	

		var options = $.extend({
			collector: null,
			servletUrl : null,
			monthNames : null,
			collectorId : null
		}, givenOptions);

		var getDateTime = function(dateTime) {
			if(dateTime != undefined) {
				var d = dateTime.split(" ")[0];
				var t = dateTime.split(" ")[1];
				return obj = {
						year : d.split("-")[0],
						month : d.split("-")[1] == 0 ? 1  : (d.split("-")[1] - 1),
						day : d.split("-")[2],
						hours : t.split(":")[0],
						minutes : t.split(":")[1]
				};
			}
			return null;
		};

		var loadDays = function() {
			
			var days = dateTimeNow.getDate();
			
			var month = parseInt($('#dropdown_close_after_certain_months')[0].value);
			var year = parseInt($('#dropdown_close_after_certain_years')[0].value);
			
			$('#dropdown_close_after_certain_days').empty();
			var setCurrentDay = function() {
				var options = $('#dropdown_close_after_certain_days')[0].options;
				for(var u = 0; u < options.length; u++) {
					if(parseInt(options[u].value) == ((certainDate.day != null) ? certainDate.day : currentDate.day)) {
						options.selectedIndex = u;
						break;
					}
				}
			};
			for(var y = 1; y <= daysInMonth(month, year); y++) {
				var optionElem = new Option(y,  y);
				$('#dropdown_close_after_certain_days')[0].add(optionElem, null);
			}
			setCurrentDay();

		};
		
		var daysInMonth = function(month, year) {
			return 32 - new Date(year, month, 32).getDate();
		};

		var loadMonths = function() {
			var setCurrentMonth = function() {
				var options = $('#dropdown_close_after_certain_months')[0].options;
				for(var u = 0; u < options.length; u++) {
					if(parseInt(options[u].value) == ((certainDate.month != null) ? certainDate.month : currentDate.month)) {
						options.selectedIndex = u;
						break;
					}
				}
			}
			for(var x = 0; x < options.monthNames.length; x++) {
				var optionElem = new Option(options.monthNames[x],  x);
				$('#dropdown_close_after_certain_months')[0].add(optionElem, null);
			}
			setCurrentMonth();
			loadDays();
		};
		
		var zeroPad = function(num) {
			var s = '0'+num;
			return s.substring(s.length-2);
		};
		
		var loadTime = function() {
			var setCurrentHours = function() {
				var options = $('#dropdown_close_after_certain_hours')[0].options;
				for(var u = 0; u < options.length; u++) {
					if(parseInt(options[u].value) == ((certainDate.hours != null) ? certainDate.hours : currentDate.hours)) {
						options.selectedIndex = u;
						break;
					}
				}
			};
			for(var q = 0; q < 24; q++) {
				var optionElem = new Option(zeroPad(q),  q);
				$('#dropdown_close_after_certain_hours')[0].add(optionElem, null);
			}
			setCurrentHours();
			
			var setCurrentMinutes = function() {
				var options = $('#dropdown_close_after_certain_minutes')[0].options;
				for(var u = 0; u < options.length; u++) {
					if(parseInt(options[u].value) == ((certainDate.minutes != null) ? certainDate.minutes : currentDate.minutes)) {
						options.selectedIndex = u;
						break;
					}
				}
			};
			for(var w = 0; w < 60; w++) {
				var optionElem = new Option(zeroPad(w),  w);
				$('#dropdown_close_after_certain_minutes')[0].add(optionElem, null);
			}
			setCurrentMinutes();
		};

		var fillCertainDate = function() {
			
			var setCurrentYear = function() {
				var options = $('#dropdown_close_after_certain_years')[0].options;
				for(var u = 0; u < options.length; u++) {
					if(parseInt(options[u].value) == ((certainDate.year != null) ? certainDate.year : currentDate.year)) {
						options.selectedIndex = u;
						break;
					}
				}
			};
			
			for(var i = 0; i < 4; i++) {
				var optionElem = new Option(dateTimeNow.getFullYear() + i,  dateTimeNow.getFullYear() + i);
				$('#dropdown_close_after_certain_years')[0].add(optionElem, null);
			}
			
			setCurrentYear();
			
			loadMonths();
			loadTime();
		};

		var handleElements = function() {
			
			$('#checkbox_close_after_a_certain_date').change(function() {
				if($(this).is(':checked')) {

					$('#container_close_after_a_certain_date').show();

					$('#dropdown_close_after_certain_days').empty();
					$('#dropdown_close_after_certain_months')
						.empty()
						.unbind('change').change(function() {
							loadDays();
						});
					$('#dropdown_close_after_certain_years').empty();
					$('#dropdown_close_after_certain_hours').empty();
					$('#dropdown_close_after_certain_minutes').empty();
					
					fillCertainDate();
					
				} else { 
					$('#container_close_after_a_certain_date').hide(); 
				}
			});

			$('#checkbox_close_after_a_quota_reached').change(function() {
				if($(this).is(':checked')) { 
					$('#container_quota_reached').show();
					$('#input_quota_reached').val(quotaReached);
				} else { 
					$('#container_quota_reached').hide(); 
				}
				
				// fix for validation status
				// need clear validation status when flag are changed
				$('#status_input_quota_reached').hide();
			});

			$('#checkbox_password_protection').change(function() {
				if($(this).is(':checked')) { 
					$('#container_password_protection').show();
				} else { 
					$('#container_password_protection').hide(); 
				}
			});
			
			$('#checkbox_hide_password').change(function() {
				if($(this).is(':checked')) { 
					
					$('#input_password_protection').hide();
					$('#input_fake_password_protection')
						.val($('#input_password_protection').val())
						.show();
					
				} else {
					$('#input_fake_password_protection').hide();
					$('#input_password_protection')
						.val($('#input_fake_password_protection').val())
						.show();
				}
				
			});
			
			/*
			// referer
			$('#checkbox_allow_access_from_specific_referral_only').change(function() {
				if($(this).is(':checked')) {
					$('#container_allow_access_from_specific_referral_only').show();
				} else {
					$('#container_allow_access_from_specific_referral_only').hide();
				}
			});
			*/
			
			/*
			// ip white list
			$('#checkbox_ip_restriction').change(function() {
				if($(this).is(':checked')) {
					$('#container_ip_restriction').show();
				} else {
					$('#container_ip_restriction').hide();
				}
			});
			*/
			
			$('#checkbox_enable_rss_updates').change(function() {
				if($(this).is(':checked')) {
					$('#container_rss_feed').show();
				} else {
					$('#container_rss_feed').hide();
				}
			});

			
			// validate elements
			new validator({
				elements : [
					{
						element : $('#textarea_close_default_message'),
						status : $('#status_close_default_message'),
						rules : [
					         { method : 'required', message : 'This field is required.' }
						]
					},
					{
						element : $('#input_quota_reached'),
						status : $('#status_input_quota_reached'),
						validate : function() {
							return $('#checkbox_close_after_a_quota_reached').is(':checked')
						},
						rules : [
					         { method : 'required', message : 'This field is required.' },
					         { method : 'digits', message : 'Please enter only digits.' }
						]
					},
		            {
		            	element : $('#input_password_protection'),
		            	status : $('#status_input_password_protection'),
		            	validate : function() {
		            		return $('#checkbox_password_protection').is(':checked') && !$('#checkbox_hide_password').is(':checked')
		            	},
		            	rules : [
	            	         { method : 'required', message : 'This field is required.' },
	            	         { method : 'rangelength', pattern : [6,12] }
		            	],
		            	error: function() {
		            		if($('#status_input_fake_password_protection').is(':visible'))
		            			$('#status_input_fake_password_protection').hide();
		            	}
		            },
		            {
		            	element : $('#input_fake_password_protection'),
		            	status : $('#status_input_fake_password_protection'),
		            	validate : function() {
		            		return $('#checkbox_password_protection').is(':checked') && $('#checkbox_hide_password').is(':checked')
		            	},
		            	rules : [
	            	         { method : 'required', message : 'This field is required.' },
	            	         { method : 'rangelength', pattern : [6,12] }
		            	],
		            	error: function() {
		            		if($('#status_input_password_protection').is(':visible'))
		            			$('#status_input_password_protection').hide();
		            	}
		            }
				],
				submitElement : $("#button_save_settings"),
				messages : null,
				accept : function() {
					updateSettings();
				},
				error : function() {
					//
				}
			});

		};

		var getCertainDate = function() {
			
			var year = parseInt($('#dropdown_close_after_certain_years')[0].value);
			var month = parseInt($('#dropdown_close_after_certain_months')[0].value);
			var day = parseInt($('#dropdown_close_after_certain_days')[0].value);
			var hours = parseInt($('#dropdown_close_after_certain_hours')[0].value);
			var minutes = parseInt($('#dropdown_close_after_certain_minutes')[0].value);
			
			return (new Date(year, month, day, hours, minutes)).format("yyyy-mm-dd HH:MM");
		};

		var getEnablePrevious = function() {
			var enablePrevious = false;
			var radio = $('#container_edit_responses').find('input:radio');
			radio.each(function(i, el) {
				if($(el).is(':checked')) {
					if(i == 1)
						enablePrevious = true;
				}
			});
			return enablePrevious;
		};
		
		var setSettings = function(data) {

			if(data.allowMultipleResponses) {
				var items = $('#dropdown_multiple_responses')[0].options;
				for(var i = 0; i < items.length; i++) {
					if(items[i].value == 0) {
						$('#dropdown_multiple_responses')[0].selectedIndex = 1;
						break;
					}
				}
			} else {
				$('#dropdown_multiple_responses')[0].selectedIndex = 0;
			}

			if(data.enablePrevious) {
				var radio = $('#container_edit_responses').find('input:radio');
				radio.each(function(i, el) {
					if(i == 1) {
						$(el).prop('checked', true);
					}
				});
			} else {
				var radio = $('#container_edit_responses').find('input:radio');
				radio.each(function(i, el) {
					if(i == 0) {
						$(el).prop('checked', true);
					}
				});
			}
			
			if(data.closeMessage != undefined) {
				if(data.closeMessage != "") {
					$('#textarea_close_default_message').val(data.closeMessage);
				} else {
					$('#textarea_close_default_message').val(contentDictionary.messages.defaultCloseMessage);
				}
			} else {
				$('#textarea_close_default_message').val(contentDictionary.messages.defaultCloseMessage);
			}
			
			// limitation for other collector sources
			if(data.sourceId == 1) {
				
				enableRestrictions = true;
				
				$('#placeholder_collector_settings_closing').show();
				$('#placeholder_collector_settings_restrictions').show();
			
				if(data.closeAfterCertainDate) {
	
					if(data.certainDate != null) {
						certainDate.year = getDateTime(data.certainDate).year;
						certainDate.month = getDateTime(data.certainDate).month;
						certainDate.day = getDateTime(data.certainDate).day;
						certainDate.hours = getDateTime(data.certainDate).hours;
						certainDate.minutes = getDateTime(data.certainDate).minutes;
					}
					
					$('#checkbox_close_after_a_certain_date')
						.prop('checked', true)
						.change();			
				}
	
				if(data.closeAfterQuotaReached) {
					quotaReached = data.quotaReached;
					$('#checkbox_close_after_a_quota_reached')
						.prop('checked', true)
						.change();
				}
				
				/*
				// referer
				if(data.referer != undefined) {
					if(data.referer != "") {
						$('#checkbox_allow_access_from_specific_referral_only')
							.prop('checked', true)
							.change();
						$('#container_allow_access_from_specific_referral_only').show();
						$('#text_referer').val(data.referer);
					}
				}
				*/
				
				/*
				if(data.ipWhiteList != undefined) {
					if(data.ipWhiteList != "") {
						$('#checkbox_ip_restriction')
							.prop('checked', true)
							.change();
						$('#container_ip_restriction').show();
						$('#textarea_ip_white_list').val(data.ipWhiteList);
					}
				}
				*/
				
				if(data.isPasswordRequired) {
	
					password = data.password;
					
					$('#checkbox_password_protection')
						.prop('checked', true)
						.change();
					
					$('#input_password_protection').val(password);
					$('#input_fake_password_protection').val(password);
					
					if(data.hidePassword) {
						$('#checkbox_hide_password')
							.prop('checked', true)
							.change();
					}
				}
			
			}
			
			if(data.enableRssUpdates) {
				$('#checkbox_enable_rss_updates')
					.prop('checked', true)
					.change();
			}

			if(data.enableEmailNotification) {
				$('#checkbox_enable_email_notification').prop('checked', true);
			}
			
		};

		var updateSettings = function() {
			
			loader.show();
			
			updateCollector({
				accountId : accountId,
				collectorId : options.collector.collectorId,
				name : $('#label_collector_name').text(),
				closeMessage : $('#textarea_close_default_message').val(),
				closeAfterQuotaReached : (enableRestrictions ? $('#checkbox_close_after_a_quota_reached').is(':checked') : undefined), 
				quotaReached : (enableRestrictions ? $('#input_quota_reached').val() : undefined),
				closeAfterCertainDate : (enableRestrictions ? $('#checkbox_close_after_a_certain_date').is(':checked') : undefined),
				certainDate : (enableRestrictions ? (($('#checkbox_close_after_a_certain_date').is(':checked')) ? getCertainDate() : null) : undefined),
				/* referer : (enableRestrictions ? ($('#checkbox_allow_access_from_specific_referral_only').is(':checked') ? $.trim($('#text_referer').val()) : "") : undefined), */
				isPasswordRequired : (enableRestrictions ? $('#checkbox_password_protection').is(':checked') : undefined),
				hidePassword : (enableRestrictions ? $('#checkbox_hide_password').is(':checked') : undefined),
				password : (enableRestrictions ? (($('#checkbox_hide_password').is(':checked')) ? $('#input_fake_password_protection').val() : $('#input_password_protection').val()) : undefined),
				allowMultipleResponses : ((parseInt($('#dropdown_multiple_responses')[0].value) == 1) ? true : false),
				enablePrevious : getEnablePrevious(),
				enableRssUpdates : $('#checkbox_enable_rss_updates').is(':checked'),
				enableEmailNotification : $('#checkbox_enable_email_notification').is(':checked'),
				enableSSLEncription : false,
				success : function() {
					
					loader.hide();
					
					var modal = new lightFace({
						title : "Changes saved.",
						message : "Your changes were successfully saved.",
						actions : [
				           { 
				        	   label : "OK", 
				        	   fire : function() { 
				        		   modal.close(); 
				        	   }, 
				        	   color : "blue" 
				           }
						],
						overlayAll : true
					});
					
				},
				error: function() {
					loader.hide();
				}
			});
			
		};
		
		var init = function() {
		
			dateTimeNow = new Date();
			currentDate.day = dateTimeNow.getDate();
			currentDate.month = dateTimeNow.getMonth();
			currentDate.year = dateTimeNow.getFullYear();
			currentDate.hours = dateTimeNow.getHours();
			currentDate.minutes = dateTimeNow.getMinutes();
			
			handleElements();
			
			setSettings(options.collector);
			
		};
		
		init();
	}
})(jQuery);