
var partial = 0;
var completed = 0;
var total = 0;

var response = {
	controls : {
		list : []
	}	
};

;(function(jQuery) {
	pollOverallResults = function(givenOptions) {

		var options = $.extend({
			opinionId : null,
			callback : null
		}, givenOptions);
		
		
		var animateBars = function () {
			$(".container-control-includes").find("div.bar").each(function (i, el) {
				var val = Math.max($(el).attr("data-value") * $(el).parents(".outer-bar").width() / 100, 2) + "px";
				//$(el).animate({ "width" : val });
				$(el).css({ "width" : val });
			});
		};
		
		var j = function() {
			
			$('li.result-control .label-control-number').each(function(i, el) {
				$(el).html((1 + i) + ".&nbsp;");
			});
			
		};
		
		var init = function() {
			
			
			
			getResponseResults({
				opinionId : options.opinionId,
				success : function(data) {
					
					partial = data.partial;
					completed = data.completed;
					total = partial + completed;
					
					// set results left
					response.controls.list = data.controls.list;
					
					if(data.controls.list.length != 0) {
						
						for (var i = 0; i < data.controls.list.length; i++) {
							
							// controls
							$("#placeholder_results ul.list-result-controls").append("<li/>");
							var v;
							v = $("#placeholder_results ul.list-result-controls > li:last");
							v.addClass("result-control c" + data.controls.list[i].controlId);
							
							/*
							if(i%2) {
								v.addClass('highlight');
							}
							*/
							
							v.resultControl({
								opinionId : options.opinionId,
								controlId : data.controls.list[i].controlId,
								controlTypeId : data.controls.list[i].controlTypeId,
								controlIndex : i
							});
							v.attr({ "controlid" : data.controls.list[i].controlId, "controlindex" : i });
							
						}
						
						j();
						
						// animate
	                	//setTimeout(animateBars, 400);
	                	animateBars();
	                	
	                	
	                	
	                	// callback
						if(options.callback != undefined 
								&& typeof options.callback == 'function')
							options.callback(data);
						
						
						
					} else {
						
						// No results
						$("#placeholder_results ul.list-result-controls").append("<li>No results</li>");
					}
					
				},
				error: function(error) {
					//
				}
			});
			
		};
	
		init();
	}
})(jQuery);