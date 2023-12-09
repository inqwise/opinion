;
(function ($) {
    $.fn.extend({
    	buttons: function (o) {
        	var a = $(this),
            d = $.extend({
            	active : null,
            	dataSource : [],
            	formatter : null,
            	change : null,
            	groupClass : "ui-buttons-group"
            }, o);
        	
        	var last = null;
        	var lastButton = null;
        	var actions = [];
        	var g = function() {
        		if(d.dataSource.length != 0) {
        			
        			var k = $("<ul " + (d.groupClass != null ? "class=\"" + d.groupClass + "\"" : "") + "></ul>").appendTo(a);
        			
        			var y = 0;
        			for(var i = 0; i < d.dataSource.length; i++) {
        				if(d.formatter != null) {
        					var f = $("<li></li>").appendTo(k);
        					var h = $(d.formatter(d.dataSource[i], i)).appendTo(f);
        					h.attr("data-item", i).click(function() {
        						
        						if(lastButton != null && lastButton != $(this)) {
        							lastButton.removeClass('ui-button-active');
        						}
        						lastButton = $(this);
        						lastButton.addClass('ui-button-active');
        						
        						if(d.change != null 
        	        					&& typeof d.change == 'function') {
        							d.change(d.dataSource[$(this).attr("data-item")], $(this).attr("data-item"));
        						}
        					});
        					
        					actions.push({
        						element : h
        					});
        					
        					if(y == 0)
        						f.addClass('first-item');
        					
        					if(y == (d.dataSource.length - 1))
        						f.addClass('last-item');
        					
        					if(d.active != null) {
	        					if(d.active == i) {
	        						h.addClass('ui-button-active');
	        						last = d.dataSource[i];
	        						lastButton = h;
	        					}
        					}
        					
        					y++;
        				}
        			}
        			
        			if(last != null) {
	        			if(d.change != null 
	        					&& typeof d.change == 'function') {
	        				d.change(last, lastButton.attr("data-item"));
	        			}
        			}
        		}
        	};
        	
        	g();
        	
        	return {
        		setActive : function(index) {
        			for(var i = 0; i < actions.length; i++) {
        				if(i == index) {
        					actions[i].element.trigger('click');
        				}
        			}
        		}
        	}
        	
        }
    });
})(jQuery);