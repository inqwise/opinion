(function($) {
	pollSettings = function(givenOptions) {
				
		var options = $.extend({
			data : {},
			opinionId : null
		}, givenOptions);
		
		var getHighlightRequiredQuestions = function() {
			var highlightRequiredQuestions = false;
			var radio = $("#container_highlight_required_questions").find('input:radio');
			radio.each(function(i, el) {
				if($(el).is(':checked')) {
					if(i == 0)
						highlightRequiredQuestions = true;
				}
			});
			return highlightRequiredQuestions;
		};
		
		var init = function() {
			
			new validator({
				elements : [
		            {
		            	element : $("#text_vote"),
			            status : $('#status_vote'),
			            rules : [
			                     { method : 'required', message : 'This field is required.' }
			            ]
		            },
		            {
		            	element : $("#text_view_results"),
			            status : $("#status_view_results"),
			            rules : [
			                     { method : 'required', message : 'This field is required.' }
			            ]
		            },
		            {
		            	element : $("#text_back"),
			            status : $('#status_back'),
			            rules : [
			                     { method : 'required', message : 'This field is required.' }
			            ]
		            }
				],
				submitElement : $("#button_save_settings"),
				accept : function() {
					
					loader.show();
					updateSettings({
						opinionId : options.opinionId,
						useCustomStartMessage : false,
						startMessage : "",
						customFinishBehaviour : 0,
						finishMessage : "",
						redirectUrl : "",
						usePageNumbering : false,
						useQuestionNumbering : false,
						showProgressBar : false,
						back : $.removeHTMLTags($("#text_back").val()),
						viewResults : $.removeHTMLTags($("#text_view_results").val()),
						vote : $.removeHTMLTags($("#text_vote").val()),
						highlightRequiredQuestions : getHighlightRequiredQuestions(),
						isRtl : $("#checkbox_is_rtl").is(':checked'),
						logoUrl : "", // $.removeHTMLTags($("#input_logo_url").val()),
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
							//
						}
					});
					
				},
				error : function() {
					//
				}
			});
			
			
			
			var data = options.data;
			
			// back
			if(data.messages.back != null) { 
				$("#text_back").val(data.messages.back);
			} else {
				$("#text_back").val(messages.back);
			}
			
			// view results
			if(data.messages.viewResults != null) {
				$("#text_view_results").val(data.messages.viewResults);
			} else {
				$("#text_view_results").val(messages.viewResults);
			}
			
			// vote
			if(data.messages.vote != null) {
				$("#text_vote").val(data.messages.vote);
			} else {
				$("#text_vote").val(messages.vote);
			}
			
			if(data.highlightRequiredQuestions) {
				var radio = $("#container_highlight_required_questions").find('input:radio');
				radio.each(function(i, el) {
					if(i == 0) {
						$(el).prop('checked', true);
					}
				});
			} else {
				var radio = $("#container_highlight_required_questions").find('input:radio');
				radio.each(function(i, el) {
					if(i == 1) {
						$(el).prop('checked', true);
					}
				});
			}
			
			if(data.isRtl) {
				$("#checkbox_is_rtl").prop('checked', true);
			}
			
			
			/*
			if(data.logoUrl != null) {
				$("#input_logo_url").val(data.logoUrl);
			} else {
				$("#input_logo_url").val("");
			}
			*/
			
			
		};
		
		
		init();
		
	}
})(jQuery);