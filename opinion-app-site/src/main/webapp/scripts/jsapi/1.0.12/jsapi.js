/* jsapi */
var jsapi = {
	messages : {
		getList : function(params) {
			
			var obj = {
				messages : {
					getList : {
						userId : params.userId
					}
				}
			};
			
			$.ajax({
		        url: servletUrl,
		        data: { 
		        	rq : JSON.stringify(obj),
		        	timestamp : $.getTimestamp()  
		        },
		        dataType: "json",
		        success: function (data, status) {
		        	if(data.messages.getList != undefined) {
			        	if(data.messages.getList.error != undefined) {
			        		errorHandler({
								error : data.messages.getList.error	
							});
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.messages.getList);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.messages.getList);
							}
						}
		        	} else {
		        		if(params.error != undefined 
								&& typeof params.error == 'function') {
							params.error();
						}
		        	}
		        },
		        error: function (XHR, textStatus, errorThrow) {
		            // error
		        }
		    });
		},
		update : function(params) {
			
			var obj = {
				messages : {
					update : {
						userId : params.userId,
						messageId : params.messageId
					}
				}
			};
			
			$.ajax({
		        url: servletUrl,
		        data: { 
		        	rq : JSON.stringify(obj),
		        	timestamp : $.getTimestamp()  
		        },
		        dataType: "json",
		        success: function (data, status) {
		        	if(data.messages.update != undefined) {
			        	if(data.messages.update.error != undefined) {
			        		errorHandler({
								error : data.messages.update.error	
							});
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.messages.update);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.messages.update);
							}
						}
		        	} else {
		        		if(params.error != undefined 
								&& typeof params.error == 'function') {
							params.error();
						}
		        	}
		        },
		        error: function (XHR, textStatus, errorThrow) {
		            // error
		        }
		    });
		},
		deleteList : function(params) {
			
			var obj = {
				messages : {
					deleteList : {
						userId : params.userId,
						list : params.list
					}
				}
			};
			
			$.ajax({
		        url: servletUrl,
		        data: { 
		        	rq : JSON.stringify(obj),
		        	timestamp : $.getTimestamp()  
		        },
		        dataType: "json",
		        success: function (data, status) {
		        	if(data.messages.deleteList != undefined) {
			        	if(data.messages.deleteList.error != undefined) {
			        		errorHandler({
								error : data.messages.deleteList.error	
							});
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.messages.deleteList);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.messages.deleteList);
							}
						}
		        	} else {
		        		if(params.error != undefined 
								&& typeof params.error == 'function') {
							params.error();
						}
		        	}
		        },
		        error: function (XHR, textStatus, errorThrow) {
		            // error
		        }
		    });
		}
	}
};