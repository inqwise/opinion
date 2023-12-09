
(function($) {
  $.fn.checkbox = function(options) {
	options = options || {}; 
	var defaultOpt = { 
		checkboxCls   	: options.checkboxCls || 'checkbox' , radioCls : options.radioCls || 'radio' ,	
		checkedCls 		: options.checkedCls  || 'checked'  , selectedCls : options.selectedCls || 'selected' , 
		hideCls  	 	: 'checkbox-hide'
	};
    return this.each(function() {
    	var $this = $(this);
    	var wrapTag = $this.attr('type') == 'checkbox' ? '<div class="'+defaultOpt.checkboxCls+'">' : '<div class="'+defaultOpt.radioCls+'">';
    	// for checkbox
    	if( $this.attr('type') == 'checkbox') {
    		$this.addClass(defaultOpt.hideCls).wrap(wrapTag).change(function() {
    			if( $(this).is(':checked') ) { 
    				$(this).parent().addClass(defaultOpt.checkedCls); 
    			} 
    			else {	$(this).parent().removeClass(defaultOpt.checkedCls); 	}
    		});
    		
    		if( $this.is(':checked') ) {
				$this.parent().addClass(defaultOpt.checkedCls);    		
    		}
    	} 
    	else if( $this.attr('type') == 'radio') {

    		$this.addClass(defaultOpt.hideCls).wrap(wrapTag).change(function() {
    			// radio button may contain groups! - so check for group
   				$('input[name="'+$(this).attr('name')+'"]').each(function() {
   	    			if( $(this).is(':checked') ) { 
   	    				$(this).parent().addClass(defaultOpt.selectedCls); 
   	    			} else {
   	    				$(this).parent().removeClass(defaultOpt.selectedCls);     	    			
   	    			}
   				});
    		});
    		
    		if( $this.is(':checked') ) {
				$this.parent().addClass(defaultOpt.selectedCls);    		
    		}    		
    	}
    });
  }
})(jQuery);