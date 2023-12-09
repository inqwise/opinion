
;(function($) {
	authentication = function(givenOptions) {
		
		var options = $.extend( {
			servletUrl : null,
			mustAuthenticated : false,
			absoluteUrl : null,
			applicationUrl : null
		}, givenOptions);
		
		var userInfo = {
			userName : null,
			displayName : null
		};
		
		var validate = function() {
			
			if($.cookie('sid') != null 
					&& $.cookie('uid') != null) {
				
				// validate user
				$.ajax({
					type: "GET",
					url: options.servletUrl,
				  	async: false,
				  	data: { 
						rq: JSON.stringify({ 
							validateUser : {}
						}), 
						timestamp: $.getTimestamp() 
					},
				  	dataType: "json",
				  	success: function(data) {
				  		if(data.validateUser.error) {
				  			if(options.mustAuthenticated) {
				  				if("NoPackageSelected" == data.validateUser.error){
									location.href = options.absoluteUrl + "/pricing";
								} else {
									location.href = options.absoluteUrl + "/login";
								}
				  			} else {
				  				
				  			}
						} else {
							if(options.mustAuthenticated) {
								// set userInfo
								userInfo.userName = data.validateUser.userName;
								userInfo.displayName = data.validateUser.displayName;
								
							} else {
								location.href = options.absoluteUrl + "/dashboard";
							}
						}
				  	},
					error: function (XHR, textStatus, errorThrow) {
						// error 
						// connection
					}
				});
					
			} else {
				if(options.mustAuthenticated) {
					location.href = options.applicationUrl;
				} else {
					
				}
			}
		};
		
		validate();
		
		return {
			userInfo : userInfo
		};
	};
})(jQuery);
