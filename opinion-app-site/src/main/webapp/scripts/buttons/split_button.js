
var lastCombo = null;
;
(function ($) {
    $.fn.extend({
    	splitButton: function (o) {
    		
        	var a = $(this),
            options = $.extend({
            	label : "Click",
            	icon : null,
            	click : null,
            	active : 0,
            	actions : [],
            	pop : null
            }, o);
        	
        	var lastItem = null;
        	var lastIndex = 0;
        	//var c = null;
        	
        	var init = function() {
        		
        		a.addClass('ui-split-button');
        		
        		var buttonContainer = $("<div class=\"ui-split-button-buttonset\"></div>").appendTo(a);
        		//var lastCombo = null;
        			
        		var c = $("<button class=\"button-" + (options.color != undefined ? options.color : "white") + "\"><span>" + (options.icon != undefined ? "<i class=\"icon-" + options.icon + "\">&nbsp;</i>" : "") + "" + options.label + "</span></button>").click(function (e) {
                    
        			if(options.click != undefined 
        				&& typeof options.click == 'function') {
        				options.click();
        			}
        			
                	e.preventDefault();
                    
                }).appendTo(buttonContainer);
                
        		var drop = $("<button class=\"button-" + (options.color != undefined ? options.color : "white") + "\" title=\"Select an action\"><span>Select an action</span><i></i></button>")
        		.click(function() {
        			var o = $(this);
        			if(!o.hasClass('ui-button-selected')) {
                		if(lastCombo != null && lastCombo != o) {
                    		lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').closest(".ui-split-button").find('.ui-combo-popup').removeClass('active');
                    	}
                    	lastCombo = o;
    					o.addClass('ui-button-selected').addClass('ui-button-active').closest(".ui-split-button").find('.ui-combo-popup').addClass('active');
    					
    					// has pop
                        if(options.pop != undefined 
                        	&& typeof options.pop == 'function') {

                        	z.empty();
                        	z.append(options.pop({
                        		close: function() {
                        			if(lastCombo != null) {
                    					if(lastCombo.hasClass('ui-button-selected')) {
                    						lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').closest(".ui-split-button").find('.ui-combo-popup').removeClass('active');
                    					}
                    				}
                        		}
                        	}));
                        	
                    	}
    					
    				} else {
    					lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').closest(".ui-split-button").find('.ui-combo-popup').removeClass('active');
    				}
        			
        		})
        		.appendTo(buttonContainer);
            	
            	var z = $("<div class=\"ui-combo-popup\"></div>").appendTo(a);
            	
            	// actions
            	if(options.actions != undefined) {
					
            		if(options.actions.length != 0) {
	            		var A = $("<ul class=\"ml\"></ul>").appendTo(z);
	            		
	            		for(var d = 0; d < options.actions.length; d++) {
	            			
	            			var G = $("<li/>");
	            			var v = $("<a action=\"" + d + "\">" + options.actions[d].label + "</a>").click(function() {
	            				
	            				var m = $(this);
	            				
	            				var sa = m.attr('action');
	            				
	            				if(options.actions[sa].click != undefined 
	                            	&& typeof options.actions[sa].click == 'function') {
	                            	
	                            	options.actions[sa].click();
	                            	
	                            	// hide combo popup
	                            	lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').closest(".ui-split-button").find('.ui-combo-popup').removeClass('active');
	                            }
	            				
	            			}).appendTo(G);
	            			
	            			
	            			G.appendTo(A);
	            		}
            		}
            	}
                
            	
            	$('.ui-split-button button.button-white, .ui-combo-popup').click(function() { return false; });
                jQuery(document).click(function(){
    				if(lastCombo != null) {
    					if(lastCombo.hasClass('ui-button-selected')) {
    						lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').closest(".ui-split-button").find('.ui-combo-popup').removeClass('active');
    					}
    				}
    			});
            	
        	};
        	
        	init();
        	
        }
    });
})(jQuery);