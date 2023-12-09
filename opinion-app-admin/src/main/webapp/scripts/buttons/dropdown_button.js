;
(function ($) {
    $.fn.extend({
    	dropdownButton: function (o) {
    		
        	var a = $(this),
            options = $.extend({
            	active : 0,
            	actions : [],
            	change : null
            }, o);
        	
        	
        	var lastItem = null;
        	var lastIndex = 0;
        	var c = null;
        	
        	var init = function() {
        		
        		var buttonContainer = $("<div class=\"ui-button-dropdown\"></div>");
        		var lastCombo = null;
        			
        		c = $("<a href=\"javascript:;\" title=\"\" class=\"button-" + (options.color != undefined ? options.color : "white") + "\"><span><b></b><i class=\"icon-more\">&nbsp;</i></span></a>").click(function () {
                    
        			var o = $(this);
                    
                	if(!o.hasClass('ui-button-selected')) {
                		if(lastCombo != null && lastCombo != o) {
                    		lastCombo.removeClass('ui-button-selected ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
                    	}
                    	lastCombo = o;
    					o.addClass('ui-button-selected').addClass('ui-button-active').parent().find('.ui-combo-popup').addClass('active');
    				} else {
    					lastCombo.removeClass('ui-button-selected ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
    				}
                    
                }).appendTo(buttonContainer);
                
            	
            	var z = $("<div class=\"ui-combo-popup\"></div>").appendTo(buttonContainer);
            	
            	// actions
            	if(options.actions.length > 0) {
            		
            		var A = $("<ul class=\"ml\"></ul>").appendTo(z);
            		for(var d = 0; d < options.actions.length; d++) {
            			
            			var G = $("<li/>");
            			
            			//  + (options.actions[d].legend != undefined ? "<i class=\"legend\" style=\"background-color: " + options.actions[d].legend + "\"></i>" : "") 
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
	                            			
	                            			lastItem.removeClass('checked');
	                            			
	                            			lastItem = prevItem; 
	                        				lastItem.addClass('checked');
	                        				
	                        				lastIndex = prevIndex;
	                        				c
	                        					.attr({ 'title' : options.actions[lastIndex].label })
	                        					.find('b').html((options.actions[lastIndex].legend != undefined ? "<i class=\"legend\" style=\"background-color: " + options.actions[lastIndex].legend + "\"></i>" : "") + options.actions[lastIndex].label);
	                        				
	                            		}
	                            	});
	                            	
	                            	// hide combo popup
	                            	lastCombo.removeClass('ui-button-selected ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
	                            	
	                            }
	                            
	                            if(lastItem != null && lastItem != m) {
	            					lastItem.removeClass('checked');
	            				}
	            				
	            				lastItem = m;
	            				
	            				lastItem.addClass('checked');
	            				
	            				lastIndex = sa;
	            				
	            				c
	            					.attr({ 'title' : options.actions[lastIndex].label })
	            					.find('b').html((options.actions[lastIndex].legend != undefined ? "<i class=\"legend\" style=\"background-color: " + options.actions[lastIndex].legend + "\"></i>" : "") + options.actions[lastIndex].label);
	            				
	            				a.attr("data-value", (options.actions[lastIndex].value != undefined ? options.actions[lastIndex].value : lastIndex));
	            				
	            				if(options.change != undefined 
	            					&& typeof options.change == 'function') {
	            					options.change((options.actions[lastIndex].value != undefined ? options.actions[lastIndex].value : lastIndex));
	            				}
	                            
            				} else {
            					// close
            					lastCombo.removeClass('ui-button-selected ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
            				}
            				
            			}).appendTo(G);
            			
            			if(options.actions[d].active) {
            				
            				lastItem = v; 
            				lastItem.addClass('checked');
            				
            				lastIndex = d;
            				c
            					.attr({ 'title' : options.actions[d].label })
            					.find('b').html((options.actions[d].legend != undefined ? "<i class=\"legend\" style=\"background-color: " + options.actions[d].legend + "\"></i>" : "") + options.actions[d].label);
            				
            				a.attr("data-value", (options.actions[lastIndex].value != undefined ? options.actions[lastIndex].value : lastIndex));
            				
            			}
            			
            			G.appendTo(A);
            		}
            		
            	}
            	
            	buttonContainer.appendTo(a);
            	
            	$('.ui-button-dropdown a.button-white, .ui-combo-popup').click(function() { return false; });
                jQuery(document).click(function(){
    				if(lastCombo != null) {
    					if(lastCombo.hasClass('ui-button-selected')) {
    						lastCombo.removeClass('ui-button-selected ui-button-active').parent().find('.ui-combo-popup').removeClass('active');
    					}
    				}
    			});
        		
        	};
        	
        	init();
        	
        }
    });
})(jQuery); 