(function ($) {
    $.fn.extend({

		lightDialog : function(givenOptions) {
			
			var $this = $(this),
			options = $.extend( {
				message : "Please wait...",
				actions : [],
				complete : null
			}, givenOptions);
			
			var box = null;
			var dialog = null;
			
			var complete = function() {
				if(options.complete != undefined 
	    				&& typeof options.complete == 'function')
	    			options.complete();
			};
			
			var draw = function() {
				
				
				// $this
				
				var E = dialog.find('.dialog-actions');
				
				if (options.actions.length > 0) {
	                E.show()
	                 .empty();
	                
					for (var t = 0; t < options.actions.length; t++) {
	                    var c = $("<a href=\"javascript:;\" action=\"" + t + "\" title=\"" + (options.actions[t].label ? options.actions[t].label : "Action" + t) + "\" class=\"button-" + (options.actions[t].color != undefined ? options.actions[t].color : "blue") + "\"><span>" + (options.actions[t].label ? options.actions[t].label : "Action" + t) + "</span></a>").click(function () {
	                        var o = $(this);
	                        var b = o.attr('action');
	                        
	                        if (options.actions[b].fire != undefined 
	                        		&& typeof options.actions[b].fire == 'function') {
	                        	options.actions[b].fire();
	                        }
	                    }).appendTo(E)
	                }
	            } else {
	                E.hide()
	                 .empty();
	            }
				
				$('.dialog-content')
					.empty()
					.html(options.message);
				
				
				complete();
				
			};
			
			var hide = function() {
				
				box.css({ "top" : "-9000px", "left" : "-9000px" });
				$('.dialog').remove();
				
			};
			
			var init = function() {
				
				$('.dialog').remove();
				
				dialog = $("<div class=\"dialog dialog-left\">" +
						"<div class=\"dialog-overlay\">" +
							"<div class=\"dialog-content\"></div>" +
							"<div class=\"dialog-actions\"></div>" +
						"</div>" +
						"<i class=\"dialog-arrow\"></i>" +
					"</div>").appendTo(document.body);
				
				box = dialog.find('.dialog');
				dialog.css({ "top" : $this.offset().top + $this.height(), "left" : $this.offset().left });
				
				
				
				draw();
				
			};
			
			init();
			
			return {
				show : function() {
					// show
				},
				hide : function() {
					hide();
				}
			};
			
		}

    })
})(jQuery);