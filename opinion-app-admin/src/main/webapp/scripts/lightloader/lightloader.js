;(function($) {
	lightLoader = function(givenOptions) {
		
		var options = $.extend( {
			message : "Please wait..."
		}, givenOptions);
		
		var control = null;
		var overlay = null;
		var box = null;
		
		var initialize = function() {
			
			$('.lightloader-control').remove();
			
			control = $("<div class=\"lightloader-control\">" +
				"<div class=\"lightloader-message\">Please wait... </div>" +
				"<div class=\"lightloader-overlay\"></div>" +
			"</div>").appendTo(document.body);
			
			box = control.find('.lightloader-message');
			overlay = control.find('.lightloader-overlay');
			
		};
		
		var show = function(message) {
			box
				.text(message)
				.show();
				
			overlay.show();
			
		};
		
		var hide = function() {
			box.hide();
			overlay.hide();
		};
		
		initialize();
		
		return {
			show : function(message) {
				show(message != undefined ? message : options.message);
			},
			hide : function() {
				hide();
			}
		}
	}
})(jQuery);