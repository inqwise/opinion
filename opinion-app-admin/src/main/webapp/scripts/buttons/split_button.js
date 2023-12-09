;
(function ($) {
    $.fn.extend({
    	splitButton: function (o) {
    		
        	var a = $(this),
            options = $.extend({
            	active : 0,
            	actions : []
            }, o);
        	
        	
        	var lastItem = null;
        	var lastIndex = 0;
        	var c = null;
        	
        	var init = function() {
        		
        		var buttonContainer = $("<div class=\"ui-split-button-container ui-button-dropdown\"></div>");
        		var lastCombo = null;
        			
        		c = $("<a href=\"javascript:;\" title=\"\" class=\"ui-split-button button-" + (options.color != undefined ? options.color : "white") + "\"><span></span><i class=\"icon-more\">&nbsp;</i></a>").click(function () {
                    
        			var o = $(this);
                    
                	if(!o.hasClass('ui-button-selected')) {
                		if(lastCombo != null && lastCombo != o) {
                    		lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
                    	}
                    	lastCombo = o;
    					o.addClass('ui-button-selected').addClass('ui-button-active').parent().find('.ui-combo-popup').addClass('active');
    				} else {
    					lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
    				}
                    
                }).appendTo(buttonContainer);
                
            	
            	var z = $("<div class=\"ui-combo-popup\"></div>").appendTo(buttonContainer);
            	
            	// actions
            	if(options.actions.length > 0) {
            		
            		var A = $("<ul class=\"ml split-button-list\"></ul>").appendTo(z);
            		for(var d = 0; d < options.actions.length; d++) {
            			
            			var G = $("<li/>");
            			var v = $("<a action=\"" + d + "\">" + options.actions[d].label + "</a>").click(function() {
            				
            				
            				
            				var m = $(this);
            				
            				if(!m.hasClass('checked')) {
            					
            					var sa = m.attr('action');
            					
            					var prevItem = lastItem;
            					var prevIndex = lastIndex;
            					
	                            if(options.actions[sa].click != undefined 
	                            	&& typeof options.actions[sa].click == 'function') {
	                            	
	                            	
	                            	options.actions[sa].click({
	                            		revert : function(index) {
	                            			// alert("setActive -> " + prevIndex);
	                            			
	                            			lastItem.removeClass('checked');
	                            			
	                            			lastItem = prevItem; 
	                        				lastItem.addClass('checked');
	                        				
	                        				lastIndex = prevIndex;
	                        				c
	                        					.attr({ 'title' : options.actions[lastIndex].label })
	                        					.find('span').text(options.actions[lastIndex].label);
	                            			
	                            		}
	                            	});
	                            	
	                            	// hide combo popup
	                            	lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
	                            }
	                            
	                            if(lastItem != null && lastItem != m) {
	            					lastItem.removeClass('checked');
	            				}
	            				
	            				lastItem = m;
	            				
	            				lastItem.addClass('checked');
	            				
	            				lastIndex = sa;
	            				
	            				c
	            					.attr({ 'title' : options.actions[lastIndex].label })
	            					.find('span').text(options.actions[lastIndex].label);
	                            
	                            
            				} else {
            					// close
            					lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
            				}
            				
            			}).appendTo(G);
            			
            			if(options.actions[d].active) {
            				
            				lastItem = v; 
            				lastItem.addClass('checked');
            				
            				lastIndex = d;
            				c
            					.attr({ 'title' : options.actions[d].label })
            					.find('span').text(options.actions[d].label);
            			}
            			
            			G.appendTo(A);
            		}
            		
            	}
                
            	
            	buttonContainer.appendTo(a);
            	
            	$('.ui-button-dropdown a.button-white, .ui-combo-popup').click(function() { return false; });
                jQuery(document).click(function(){
    				if(lastCombo != null) {
    					if(lastCombo.hasClass('ui-button-selected')) {
    						lastCombo.removeClass('ui-button-selected').removeClass('ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
    					}
    				}
    			});
            	
            	
        		
        	};
        	
        	init();
        	
        }
    });
})(jQuery); 