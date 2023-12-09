(function($) {
	$.fn.extend({
		inputMask : function(givenOptions) {
			var el = $(this),
            options = $.extend({
            	defaultValue : null
            }, givenOptions);
			
			if(options.defaultValue != null) {
				if(el.val() == "") {
					el.val(options.defaultValue)
						.addClass('mask');
				} else if(el.val() == options.defaultValue) {
					el.addClass('mask');
				}
				
				el
				 .attr({ 'title' : options.defaultValue, 'mask' : options.defaultValue })
				 .focusin(function() {
					 if($(this).val() == options.defaultValue) { 
						 $(this).val('')
						 		.removeClass('mask')
					 }
				 })
				 .focusout(function() {
					 if($(this).val() == "") { 
						 $(this).val(options.defaultValue)
						 		.addClass('mask')
					 }
				 })
				 .blur(function() {
					 if($(this).val() == "" || $(this).val() == $(this).attr('mask')) {
						 $(this)
						 	.val($(this).attr('mask'))
						 	.addClass('mask');
					 } else if($(this).val() != $(this).attr('mask')) {
						 $(this).removeClass('mask')
					 }
				 });
			}
		}
	});
})(jQuery);