

/*
 * lightFace for modal
 * Inqwise
 */
;(function($) {
	lightFace = function(givenOptions) {
		
		var $this = $(this),
		options = $.extend( {
			width : 'auto',
			height: 'auto',
			title : "",
			message : "<p>Message not specified.</p>",
			errorMessage: "<p>The requested file could not be found.</p>",
			actions : [],
			fadeDelay : 400,
			fadeDuration : 400,
			zIndex : 9001,
			pad : 100,
			overlayAll : false,
			constrain: false,
			resetOnScroll: true,
			resizeOnOpen: true,
            complete : null,
            abort : null
		}, givenOptions);
		
		
		var control = null;
		var overlay = null;
		var box = null;
		
		var complete = function() {
			// complete callback
			if(options.complete != undefined 
    				&& typeof options.complete == 'function')
    			options.complete();
		};
		
		var draw = function(applyComplete) {
			
			// overlay
			if(options.overlayAll) {
				overlay.show();
			}
			
			/*
			if(applyComplete) {
			
				var T = control.find('.lightface-title');
				if(options.title.length != 0) {
					T.show()
					 .empty()
					 .text(options.title);
				} else {
					T.hide()
					 .empty();
				}
				
				$('.lightface-message')
					.empty()
					.html(options.message);
			
				
			}
			*/
			
			// hide overlay of message
			$('.lightface-content-overlay').hide();
			
			
			
			
			
			// actions
			var E = control.find('.lightface-actions');
			
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
			
			
			
			
			// resize
			// apply complete
			//if(applyComplete) {
	    	//	_resize(function() {
	    			
	    			var T = control.find('.lightface-title');
					if(options.title.length != 0) {
						T.show()
						 .empty()
						 .text(options.title);
					} else {
						T.hide()
						 .empty();
					}
					
					/*
					$('.lightface-message')
						.empty()
						.html(options.message);
	    			*/
	    			
					
	    			//complete();
	    		//});
			//} else {
			//	_resize();
			//}
    		
			
			
			$('.lightface-message')
						.empty()
						.html(options.message);
						
    		
    		box.css({'top': $(window).scrollTop() + ($(window).height() - (box.height()))/2  + 'px', 'left' : $(window).scrollLeft() + ($(window).width() - (box.width()))/2  + 'px'});
    		
    		
    		
    		complete();
    		
    		
    		
		};
		
		// attaches events when opened
		var _attachEvents = function() {
			
			/*
			this.keyEvent = function(e){
				if(this.options.keys[e.key]) this.options.keys[e.key].call(this);
			}.bind(this);
			this.focusNode.addEvent('keyup',this.keyEvent);
			
			this.resizeEvent = this.options.constrain ? function(e) { 
				this._resize(); 
			}.bind(this) : function() { 
				this._position(); 
			}.bind(this);
			window.addEvent('resize',this.resizeEvent);
			
			if(this.options.resetOnScroll) {
				this.scrollEvent = function() {
					this._position();
				}.bind(this);
				window.addEvent('scroll',this.scrollEvent);
			}
			
			return this;
			*/
			
		};
		
		// detaches events upon close
		var _detachEvents = function() {
			
			/*
			this.focusNode.removeEvent('keyup',this.keyEvent);
			window.removeEvent('resize',this.resizeEvent);
			if(this.scrollEvent) window.removeEvent('scroll',this.scrollEvent);
			return this;
			*/
			
		};
		
		// repositions the box
		var _position = function() {
			
			/*
			var windowSize = window.getSize(), 
				scrollSize = window.getScroll(), 
				boxSize = this.box.getSize();
			this.box.setStyles({
				left: scrollSize.x + ((windowSize.x - boxSize.x) / 2),
				top: scrollSize.y + ((windowSize.y - boxSize.y) / 2)
			});
			this._ie6Size();
			return this;
			*/


            box.css({'top': $(window).scrollTop() + ($(window).height() - (box.height()))/2  + 'px', 'left' : $(window).scrollLeft() + ($(window).width() - (box.width()))/2  + 'px'});
			
		};
		
		// resizes the box, then positions it
		var _resize = function(callback) {
			
			/*
			var height = this.options.height;
			if(height == 'auto') {
				//get the height of the content box
				var max = window.getSize().y - this.options.pad;
				if(this.contentBox.getSize().y > max) height = max;
			}
			//this.messageBox.setStyle('height',height);
			this._position();
			*/
			

			
			box.css({'top': ($(window).height() - (box.height()))/2  + 'px', 'left' : ($(window).width() - (box.width()))/2  + 'px'});
			

            _position();


			// callback
			if(callback != undefined 
				&& typeof callback == 'function')
				callback();
			
		};
		
		var open = function() {
			// open
		};
		
		var close = function() {
			// close
			overlay.hide();
			box.css({ "top" : "-9000px", "left" : "-9000px" });
			$('.lightface-control').remove();
			
			// unbind window event
			//$(window).unbind('resize');
            //$(window).unbind('scroll');
		};
		
		var initialize = function() {
			
			this.state = false;
			this.resizeOnOpen = true;
			this.ie6 = typeof document.body.style.maxHeight == "undefined";
			
			
			
			
			$('.lightface-control').remove();
			
			control = $("<div class=\"lightface-control\">" +
				"<div class=\"lightface\">" +
					"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"table-layout:fixed;\">" +
						"<tbody>" +
							"<tr>" +
								"<td class=\"top-left\"></td>" +
								"<td class=\"top-center\"></td>" +
								"<td class=\"top-right\"></td>" +
							"</tr>" +
							"<tr>" +
								"<td class=\"middle-left\"></td>" +
								"<td class=\"middle-center\">" +
									"<div class=\"lightface-content\">" +
										"<h2 class=\"lightface-title\"></h2>" +
										"<div class=\"lightface-message\"></div>" +
										"<div class=\"lightface-actions\"></div>" +
										"<div class=\"lightface-content-overlay\"></div>" +
									"</div>" +
								"</td>" +
								"<td class=\"middle-right\"></td>" +
							"</tr>" +
							"<tr>" +
								"<td class=\"bottom-left\"></td>" +
								"<td class=\"bottom-center\"></td>" +
								"<td class=\"bottom-right\"></td>" +
							"</tr>" +
						"</tbody>" +
					"</table>" +
				"</div>" +
				"<div class=\"lightface-overlay\"></div>" +
			"</div>").appendTo(document.body);
			
			
			
			box = control.find('.lightface');
			overlay = control.find('.lightface-overlay');
			
			
			// resize
    		$(window).resize(function(){
                //draw();
				box.css({'top': $(window).scrollTop() + ($(window).height() - (box.height()))/2  + 'px', 'left' : $(window).scrollLeft() + ($(window).width() - (box.width()))/2  + 'px'});
            });

            // position
            $(window).scroll(function() {
                //_position();
				box.css({'top': $(window).scrollTop() + ($(window).height() - (box.height()))/2  + 'px', 'left' : $(window).scrollLeft() + ($(window).width() - (box.width()))/2  + 'px'});
            });
    		
			// escape
    		document.onkeyup = function(e){	
				if (e == null) { // ie
					keycode = event.keyCode;
				} else { // mozilla
					keycode = e.which;
				}
				
				if(keycode == 27){
					
					if(options.abort != undefined 
            			&& typeof options.abort == 'function') {
            			options.abort();
                	}
					
					close();
				}	
			};
			
			
			draw();
			
		};
		
		initialize();
		
		return {
			open : function() {
				open();
			},
			close : function() {
				close();
			},
			reset : function() {
				alert("OK")
			},
			load : function(o) {
				// 
			},
			destroy : function() {
				// clear events and delete elements
			}
		}
		
	};
})(jQuery);