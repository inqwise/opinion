
(function($) {

$.fn.extend({
    counter: function(givenOptions) {
        return this.each(function() {
            var $this = $(this),
                options = $.extend({
                    maxChars: 100,
					maxCharsWarning: 80,
					messageText: 'Characters left:',
					messageFontSize: '11px',
					messageFontColor: '#999',
					messageFontFamily: 'Arial',
					messageTextAlign: 'right',
					messageWarningColor: '#f00',
					messageAppendMethod: 'insertAfter'
                }, givenOptions);
	
			if(options.maxChars <= 0) return;
			
			// create counter element
			var counterMessage = $("<div class=\"counter-message\">&nbsp;</div>");
			var counterMessageStyle = {
				/*'font-size' : options.messageFontSize,
				'font-family' : options.messageFontFamily,*/
				'color' : options.messageFontColor,
				'text-align' : options.messageTextAlign,
				'width' : $this.width() + 6,
				'opacity' : 0
			};
			counterMessage.css(counterMessageStyle);
			// append counter element to DOM
			counterMessage[options.messageAppendMethod]($this);
			
			// bind events to this element
			$this
				.bind('keydown keyup keypress', doCount)
				.bind('focus paste', function(){setTimeout(doCount, 10);})
				.bind('blur', function(){counterMessage.stop().fadeTo( 'fast', 0);return false;});
			
			function doCount(){
				var val = $this.val(),
					length = val.length
				
				if(length >= options.maxChars) {
					val = val.substring(0, options.maxChars); 				
				};
				
				if(length > options.maxChars){
					// keep scroll bar position
					var originalScrollTopPosition = $this.scrollTop();
					$this.val(val.substring(0, options.maxChars));
					$this.scrollTop(originalScrollTopPosition);
				};
				
				if(length >= options.maxCharsWarning){
					counterMessage.css({"color" : options.messageWarningColor});
				}else {
					counterMessage.css({"color" : options.messageFontColor});
				};
				
				counterMessage.html(options.messageText + "&nbsp;" + $this.val().length + "/" + options.maxChars);
                counterMessage.stop().fadeTo( 'fast', 1);
			};
        });
    }
});

})(jQuery);