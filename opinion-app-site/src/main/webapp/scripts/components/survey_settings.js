
(function($) {
	surveySettings = function(givenOptions) {
		
		var options = $.extend({
			data : {},
			opinionId : null,
			callback : null
		}, givenOptions);
		
		var getHighlightRequiredQuestions = function() {
			var highlightRequiredQuestions = false;
			var radio = $('#container_highlight_required_questions').find('input:radio');
			radio.each(function(i, el) {
				if($(el).is(':checked')) {
					if(i == 0)
						highlightRequiredQuestions = true;
				}
			});
			return highlightRequiredQuestions;
		};
		
		var getCustomFinishBehaviour = function() {
			var customFinishBehaviour = 0;
			$("input:radio[name=finish_options]").each(function(i, el) {
				if($(el).is(':checked')) {
					customFinishBehaviour = $(el).attr("value");
				}
			});
			return customFinishBehaviour;
		};
		
		var init = function() {
			
			$('#checkbox_use_a_custom_start_message').change(function() {
				if($(this).is(':checked')) {
					$('#container_start_message').show();
				} else { 
					$('#container_start_message').hide(); 
				}
			});
			
			// finish
			if(auth.userInfo.permissions.customFinishBehaviour) {
				// events
				var lastForm = null;
				$("input:radio[name=finish_options]").each(function(i, el) {
					$(el).change(function() {
						if(lastForm != null && $(this).attr("data-form") != lastForm) {
							$("." + lastForm).hide();
						}
						lastForm = $(this).attr("data-form");
						$("." + lastForm).show();
					});
				});
			}
			
			new validator({
				elements : [
				    {
				    	element : $('#input_required_question_error_message'),
				    	status : $('#status_required_question_error_message'),
				    	rules : [
				    	         { method : 'required', message : 'This field is required.' }
				    	]
				    },
		            {
		            	element : $('#input_start_button'),
			            status : $('#status_input_start_button'),
			            rules : [
			                     { method : 'required', message : 'This field is required.' }
			            ]
		            },
		            {
		            	element : $('#input_previous_button'),
			            status : $('#status_input_previous_button'),
			            rules : [
			                     { method : 'required', message : 'This field is required.' }
			            ]
		            },
		            {
		            	element : $('#input_next_button'),
			            status : $('#status_input_next_button'),
			            rules : [
			                     { method : 'required', message : 'This field is required.' }
			            ]
		            },
		            {
		            	element : $('#input_finish_button'),
			            status : $('#status_input_finish_button'),
			            rules : [
			                     { method : 'required', message : 'This field is required.' }
			            ]
		            }
				],
				submitElement : $('#button_save_settings'),
				messages : null,
				accept : function() {
					
					loader.show();
					
					updateSettings({
						accountId : accountId,
						opinionId : options.opinionId,
						useCustomStartMessage : $('#checkbox_use_a_custom_start_message').is(':checked'),
						startMessage : $.removeHTMLTags($('#textarea_start_message').val()).length != 0 ? $('#textarea_start_message').val() : "",
						customFinishBehaviour : getCustomFinishBehaviour(),
						finishMessage : $.removeHTMLTags($("#textarea_finish_message").val()).length != 0 ? $("#textarea_finish_message").val() : "",
						redirectUrl : ($("#text_redirect_url").val() != $("#text_redirect_url").attr("mask") ? $.removeHTMLTags($("#text_redirect_url").val()) : ""),
						completedMessage : $.removeHTMLTags($("#textarea_already_completed_message").val()).length != 0 ? $("#textarea_already_completed_message").val() : "",
						labelPlacement : $('#select_label_placement').val(),
						usePageNumbering : $('#checkbox_use_page_numbering').is(':checked'),
						useQuestionNumbering : $('#checkbox_use_question_numbering').is(':checked'),
						showProgressBar : $('#checkbox_show_progress_bar').is(':checked'),
						showPaging : $('#checkbox_show_paging').is(':checked'),
						start : $.removeHTMLTags($('#input_start_button').val()),
						back : $.removeHTMLTags($('#input_previous_button').val()),
						next : $.removeHTMLTags($('#input_next_button').val()),
						finish : $.removeHTMLTags($('#input_finish_button').val()),
						highlightRequiredQuestions : getHighlightRequiredQuestions(),
						isRtl : $('#checkbox_is_rtl').is(':checked'),
						logoUrl : $.removeHTMLTags($('#input_logo_url').val()),
						hidePoweredBy : (auth.userInfo.permissions.customFinishBehaviour ? !$("#checkbox_show_powered_by").is(':checked') : false),
						requiredQuestionErrorMessage : $.trim($.removeHTMLTags($('#input_required_question_error_message').val())),
						success : function(data) {
							
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
								    	color: "blue" 
								    }
								],
								overlayAll : true
							});
							
						},
						error: function(error) {
							
						}
					});
					
				},
				error : function() {
					//
				}
			});
			
			
			
			// data
			var data = options.data;
			
			if(data.useCustomStartMessage) {
				
				//startMessage = data.startMessage;
				$('#textarea_start_message').val(data.messages.startMessage);
				
				$('#checkbox_use_a_custom_start_message')
				.prop('checked', true)
				.change();
			}
			
			if(data.messages.completedMessage) {
				$("#textarea_already_completed_message").val(data.messages.completedMessage);
			} else {
				$("#textarea_already_completed_message").val("You have already completed this survey.\nThank you for your participation.");
			}
			
			// finish message / redirect to url
			if(auth.userInfo.permissions.customFinishBehaviour) {
				
				$("#container_finish_options").show();
				$("#container_hide_powered_by").show();
				
				
				
				
				$("input:radio[name=finish_options][value=" + data.customFinishBehaviour + "]")
				.prop('checked', true)
				.change();
				
				if(data.messages.finishMessage != null) {
					$("#textarea_finish_message").val(data.messages.finishMessage);
				} else {
                    $("#textarea_finish_message").val(messages.thanks);
                }
				
				
				
				$("#text_redirect_url")
				.on("keyup", function() {
					var value = $(this).val();
				    if(/(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/.test(value)) {
				    	$('#link_check_redirect_url')
				    	.attr("href", value)
				    	.removeClass("ui-link-disabled");
				    } else {
				    	$('#link_check_redirect_url')
				    	.removeAttr("href")
				    	.addClass("ui-link-disabled");
				    }
				})
				.urlInputBox();
				
				
				if(data.redirectUrl != null) {
					$("#text_redirect_url").val(data.redirectUrl).trigger("keyup");
				}
				
				if(data.hidePoweredBy) {
					$("#checkbox_show_powered_by").prop("checked", false);
				} else {
					$("#checkbox_show_powered_by").prop("checked", true);
				}
				
			}
			
			if(data.messages.requiredQuestionErrorMessage != null) {
				$('#input_required_question_error_message').val(data.messages.requiredQuestionErrorMessage);
			} else {
				$('#input_required_question_error_message').val("This question is required."); //.val(messages.requiredQuestionErrorMessage);
			}
			
			if(data.labelPlacement != undefined) {
				$("#select_label_placement option[value=" + data.labelPlacement + "]").attr("selected", "selected");
			}
			
			if(data.usePageNumbering) {
				$('#checkbox_use_page_numbering').prop('checked', true);
			}
			
			if(data.useQuestionNumbering) {
				$('#checkbox_use_question_numbering').prop('checked', true);
			}
			
			if(data.showProgressBar) {
				$('#checkbox_show_progress_bar')
				.prop('checked', true)
				.change();
			}
			
			if(data.showPaging != undefined) {
				if(data.showPaging) {
					$('#checkbox_show_paging')
					.prop('checked', true)
					.change();
				}
			} else {
				$('#checkbox_show_paging')
				.prop('checked', true)
				.change();
			}
			
			// start
			if(data.messages.start != null) {
				$('#input_start_button').val(data.messages.start);
			} else {
				$('#input_start_button').val(messages.start);
			}
			
			// back
			if(data.messages.back != null) { 
				$('#input_previous_button').val(data.messages.back);
			} else {
				$('#input_previous_button').val(messages.back);
			}
			
			// next
			if(data.messages.next != null) {
				$('#input_next_button').val(data.messages.next);
			} else {
				$('#input_next_button').val(messages.next);
			}
			
			// finish
			if(data.messages.finish != null) {
				$('#input_finish_button').val(data.messages.finish);
			} else {
				$('#input_finish_button').val(messages.finish);
			}
			
			if(data.highlightRequiredQuestions) {
				var radio = $('#container_highlight_required_questions').find('input:radio');
				radio.each(function(i, el) {
					if(i == 0) {
						$(el).prop('checked', true);
					}
				});
			} else {
				var radio = $('#container_highlight_required_questions').find('input:radio');
				radio.each(function(i, el) {
					if(i == 1) {
						$(el).prop('checked', true);
					}
				});
			}
			
			// rtl
			if(data.isRtl) {
				$('#checkbox_is_rtl').prop('checked', true);
			}
			
			// logo
			if(data.logoUrl != null) {
				$('#input_logo_url').val(data.logoUrl);
			} else {
				$('#input_logo_url').val("");
			}
			
			
			//$('#input_logo_url').urlInputBox();
			
			
			
			
			
			
			
			
			if(options.callback != undefined 
					&& typeof options.callback == 'function') {
				options.callback();
			}
				
			
		};
		
		init();
		
	}
})(jQuery);