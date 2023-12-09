



/* Inqwise JSAPI */

var jsapi = {
	accounts : {},
	opinions : {
		getList : function(params) {
			
			var obj = {
				opinions : {
					getList : {
						folderId : null,
						top : 100,
						from : undefined,
						to : undefined,
						accountId : params.accountId,
						opinionTypeId : 1 /*survey*/
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
		        	if(data.opinions.getList != undefined) {
			        	if(data.opinions.getList.error != undefined) {
							
			        		errorHandler({
								error : data.opinions.getList.error	
							});
			        		
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.opinions.getList);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.opinions.getList);
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
		getDetails : function(params) {
			
			var obj = {
				opinions : {
					getDetails : {
						opinionId : params.opinionId
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
		        	if(data.opinions.getDetails != undefined) {
			        	if(data.opinions.getDetails.error != undefined) {
							
			        		errorHandler({
								error : data.opinions.getDetails.error	
							});
			        		
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.opinions.getDetails);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.opinions.getDetails);
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
		copy : function(params) {
			
			var obj = {
				opinions : {
					copy : {
						name : params.name,
						title : params.title,
						opinionType : 1,
						folderId : null,
						opinionId : params.opinionId
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
		        	if(data.opinions.copy.error != undefined) {
		        		
		        		errorHandler({
							error : data.opinions.copy.error	
						});
		        		
		        		if(params.error != undefined 
								&& typeof params.error == 'function') {
							params.error(data.opinions.copy);
						}
					} else {
						if(params.success != undefined 
								&& typeof params.success == 'function') {
							params.success(data.opinions.copy);
						}
					}
		        },
		        error: function (XHR, textStatus, errorThrow) {
		            // error
		        }
		    });
			
		},
		rename : function(params) {
			
			var obj = {
				opinions : {
					rename : {
						name : params.name,
						title : params.title,
						opinionId : params.opinionId
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
		        	if(data.opinions.rename.error != undefined) {
		        		
		        		errorHandler({
							error : data.opinions.rename.error	
						});
		        		
		        		if(params.error != undefined 
								&& typeof params.error == 'function') {
							params.error(data.opinions.rename);
						}
					} else {
						if(params.success != undefined 
								&& typeof params.success == 'function') {
							params.success(data.opinions.rename);
						}
					}
		        },
		        error: function (XHR, textStatus, errorThrow) {
		            // error
		        }
		    });
			
		},
		deleteOpinions : function(params) {
			
			var obj = {
				opinions : {
					deleteOpinions : {
						list : params.list,
						accountId : params.accountId,
						opinionTypeId : 1 /*survey*/
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
		        	if(data.opinions.deleteOpinions != undefined) {
			        	if(data.opinions.deleteOpinions.error != undefined) {
							
			        		errorHandler({
								error : data.opinions.deleteOpinions.error	
							});
			        		
			        		if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.opinion.deleteOpinions);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.opinions.deleteOpinions);
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
	},
	collectors : {},
	responses : {},
	invoices : {},
	payments : {}
};