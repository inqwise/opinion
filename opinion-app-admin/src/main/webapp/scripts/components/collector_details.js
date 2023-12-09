;
(function($) {
	collectorDetails = function(givenOptions) {
		
		var options = $.extend({
			collectorId : null,
			accountId : null,
			servletUrl : null,
			absoluteUrl : null,
			applicationUrl : null,
			elements : [],
			messages : {},
			success : null,
			error : null
		}, givenOptions);
		
		var enableDelete = function() {
			$('#item_collector_delete').show();
		};
		
		var disableDelete = function() {
			$('#item_collector_delete').hide();
		};
		
		var updateStatus = function(params) {
			
			var modal = new lightFace({
				title : "Changing status",
				message : "Are you sure you want to change collector status?",
				actions : [
		           { 
		        	   label : "Change", 
		        	   fire : function() {
		        	   	
			        	   // update status
			        	   updateCollectorStatus({
			        		   collectorId : params.collectorId,
			        		   statusId : params.statusId,
			        		   closeMessage : params.closeMessage,
			        		   isGenerateNewGuid : false,
			        		   success : function() {
			        			   
			        			   if(params.success != undefined 
			        					&& typeof params.success == 'function') {
			        				   params.success();
			        			   }
			        			   
			        			   modal.close();
			        		   },
			        		   error : function() {
			        			   
			        			   if(params.error != undefined 
			        					&& typeof params.error == 'function') {
			        				   params.error();
			        			   }
			        			   
			        			   modal.close();
			        			   
			        		   }
			        	   });
						
		           		}, 
		           		color: "blue" 
		           	},
		           	{ 
		        	   label : "Cancel", 
		        	   fire : function() {
		        		   
		        		   if(params.abort != undefined 
	        					&& typeof params.abort == 'function') {
	        				   params.abort();
	        			   }
		        		   
		        		   modal.close(); 
		        	   }, 
		        	   color: "white" 
		           	}
				],
				overlayAll : true,
				abort : function() {
					
					// abort
					if(params.abort != undefined 
    					&& typeof params.abort == 'function') {
    				   params.abort();
					}
					
				}
			});
			
		};
		
		var init = function() {
			
			getCollectorDetails({
				collectorId : options.collectorId,
				success : function(data) {
					
					
					// collectorUrl = data.collectUrl;
					// collectorName = data.name;
					
					$('#header_collector_name').text(data.name);
					
					
					var d = $("<li id=\"item_collector_delete\"><a href=\"javascript:;\" title=\"Delete\" id=\"link_collector_delete\" class=\"button-white\"><span>Delete</span></a></li>").appendTo('#list_collector_actions');
					d.find('#link_collector_delete').click(function() {
						
						if(!$(this).hasClass('ui-button-disabled')) {
							
							var modal = new lightFace({
								title : "Deleting collector",
								message : "Are you sure you want to delete this collector?",
								actions : [
						           { 
						        	   label : "Delete", 
						        	   fire : function() { 
							        	   
						        		   deleteCollectors({
						        			   list : [options.collectorId],
						        			   success : function() {
						        				   
						        				   modal.close();
						        				   
												   location.href = options.absoluteUrl + "/account-collectors?account_id=" + options.accountId;
													
						        			   }
						        		   });
						        		   	
						           		}, 
						           		color: "blue"
						           },
						           { 
						        	   label : "Cancel", 
						        	   fire: function() { 
						        		   modal.close(); 
						        	   }, 
						        	   color: "white" 
						        	}
								],
								overlayAll : true
							});
							
						}
						
						
					});
					
					// disable delete
					if(data.sourceTypeId == 2) {
						if(data.statusId == 1 
								|| data.statusId == 4) {
							disableDelete();
						} else {
							enableDelete();
						}
					} else {
						enableDelete();
					}
					
					// status
					if(data.sourceTypeId == 1) {
						
						var c = $("<li><div id=\"button_collector_status\"></div></li>").appendTo('#list_collector_actions');
						c.find('#button_collector_status')
						.tipsy({
							gravity: 'se', 
							title: function() {
								return "Status"
							}
						})
						.splitButton({
							actions : [
							    {
							    	label : getCollectorStatus(1, data.sourceTypeId),
							    	active : data.statusId == 1,
									click : function(button) {
										
										var modal = new lightFace({
											title : "Are you sure you want to start collecting responses?",
											message : "This collector is currently closed.<br/> You can re-open this collector now.",
											actions : [
									           { 
									        	   label : "Open Collector", 
									        	   fire : function() {
									        	   	
										        	   // update status
										        	   updateCollectorStatus({
										        		   collectorId : options.collectorId,
										        		   statusId : 1,
										        		   closeMessage : null,
										        		   isGenerateNewGuid : false,
										        		   success : function() {
										        			   
										        			   modal.close();
										        		   }
										        	   });
													
									           		}, 
									           		color: "blue" 
									           	},
									           	{ 
									        	   label : "Cancel", 
									        	   fire : function() {
									        		   
									        		   button.revert(); // revert to prev
									        		   
									        		   modal.close(); 
									        	   }, 
									        	   color: "white" 
									           	}
											],
											overlayAll : true,
											abort : function() {
												
												button.revert(); // revert to prev
												
											}
										});
										
									}
							    },
							    {
							    	label : getCollectorStatus(2, data.sourceTypeId),
							    	active : data.statusId == 2,
									click : function(button) {
										
										var M = $("<div>" +
											"<div style=\"padding: 0 0 12px 0\">Closing this collector will prevent anyone who is using this collector<br/> from entering responses and will interrupt any responses that are in progress.</div>" +
											"<div class=\"row\" style=\"height: 84px;\">" +
												"<div class=\"cell\">" +
													"<textarea id=\"textarea_close_collector_message\" name=\"textarea_close_collector_message\" autocomplete=\"off\" style=\"width: 314px; height: 74px;\"></textarea>" +
												"</div>" +
											"</div>" +
										"</div>");
										var I = M.find('#textarea_close_collector_message');
										
										var modal = new lightFace({
											title : "Are you sure you want to stop collecting responses?",
											message : M,
											actions : [
									           { 
									        	   label : "Stop Collecting Now", 
									        	   fire : function() {
									        	   
										        	   // update status
										        	   updateCollectorStatus({
										        		   collectorId : options.collectorId,
										        		   statusId : 2,
										        		   closeMessage : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
										        		   isGenerateNewGuid : false,
										        		   success : function() {
										        			   
										        			   modal.close();
										        		   }
										        	   });
													
									        	   }, 
									        	   color: "blue" 
									        	},
									        	{ 
									        		label : "Cancel", 
									        		fire : function() {
									        			
									        			button.revert(); // revert to prev
									        			
									        			modal.close(); 
									        		}, 
									        		color: "white" 
									        	}
											],
											overlayAll: true,
											complete : function() {
												I.text(((data.closeMessage == undefined) ? messages.defaultCloseMessage : data.closeMessage));
											},
											abort : function() {
												
												button.revert(); // revert to prev
												
											}
										});
										
										
									}
							    }
							]
						});
						
						
					} else {
						
						
						var c = $("<li><div id=\"button_collector_status\"></div></li>").appendTo('#list_collector_actions');
						c.find('#button_collector_status')
						.tipsy({
							gravity: 'se', 
							title: function() { 
								return "Status"
							}
						})
						.splitButton({
							actions : [
								{
									label : getCollectorStatus(7), // Pending
									active : data.statusId == 7,
									click : function(button) {
										
										// update status
										updateStatus({
											collectorId : options.collectorId,
											statusId : 7,
											closeMessage : null,
											isGenerateNewGuid : false,
											success : function() {
											   
												enableDelete();
												
											},
											error : function() {
											   button.revert(); // revert to prev
											},
											abort : function() {
											   button.revert(); // revert to prev
											}
										});
										
									}
								},
							    {
							    	label : getCollectorStatus(3), // Awaiting payment
							    	active : data.statusId == 3,
									click : function(button) {
										
										// update status
										updateStatus({
											collectorId : options.collectorId,
											statusId : 3,
											closeMessage : null,
											isGenerateNewGuid : false,
											success : function() {
						        			   
												enableDelete();
												
											},
											error : function() {
						        			   button.revert(); // revert to prev
											},
											abort : function() {
						        			   button.revert(); // revert to prev
											}
										});
										
									}
							    },
							    {
							    	label : getCollectorStatus(4), // Verified
							    	active : data.statusId == 4,
									click : function(button) {
										
										// update status
										updateStatus({
											collectorId : options.collectorId,
											statusId : 4,
											closeMessage : null,
											isGenerateNewGuid : false,
											success : function() {
						        			   
												disableDelete();
												
											},
											error : function() {
						        			   button.revert(); // revert to prev
											},
											abort : function() {
						        			   button.revert(); // revert to prev
											}
										});
										
									}
							    },
							    {
							    	label : getCollectorStatus(5), // The panel is live -> 1
							    	active : data.statusId == 1,
							    	disable : true, // if disable -> show hidden this action
									click : function(button) {
										
										// update status
										updateStatus({
											collectorId : options.collectorId,
											statusId : 1,
											closeMessage : null,
											isGenerateNewGuid : false,
											success : function() {
						        			   
												disableDelete();
												
											},
											error : function() {
						        			   button.revert(); // revert to prev
											},
											abort : function() {
						        			   button.revert(); // revert to prev
											}
										});
										
									}
							    },
							    {
							    	label : getCollectorStatus(6), // Panel has been completed -> 2
							    	active : data.statusId == 2,
							    	disable : true,
									click : function(button) {
										
										// update status
										updateStatus({
											collectorId : options.collectorId,
											statusId : 2,
											closeMessage : null,
											isGenerateNewGuid : false,
											success : function() {
						        			   
												enableDelete();
												
											},
											error : function() {
						        			   button.revert();
											},
											abort : function() {
						        			   button.revert();
											}
										});

									}
							    },
							    {
							    	label : getCollectorStatus(8),
							    	active : data.statusId == 8,
									click : function(button) {
										
										// update status
										updateStatus({
											collectorId : options.collectorId,
											statusId : 8,
											closeMessage : null,
											isGenerateNewGuid : false,
											success : function() {
						        			   
												enableDelete();
												
											},
											error : function() {
						        			   button.revert(); // revert to prev
											},
											abort : function() {
						        			   button.revert(); // revert to prev
											}
										});
										
									}
							    }
							]
						});
						
					}

					$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getCollectorStatus(data.statusId, data.sourceTypeId) + "</li>").appendTo('#list_collector_details');
					$("<li><span class=\"light\">Type:</span>&nbsp;" + getCollectorType(data.sourceTypeId) + "</li>").appendTo('#list_collector_details');
					
					
					// TODO:
					if(data.charge != undefined) {
						$("<li><span class=\"light\">Charge #:</span>&nbsp;<a href=\"" + options.absoluteUrl + "/account-charge?account_id=" + options.accountId + "&charge_id=" + data.charge.chargeId + "\" title=\"" + data.charge.chargeId + "\">" + data.charge.chargeId + "</a></li>").appendTo('#list_collector_details');
					}
					
					
					
					
					
					
					if(options.success != null 
							&& typeof options.success == 'function') {
						options.success(data);
					}
					
				},
				error : function(error) {
					
					if(options.error != null 
							&& typeof options.error == 'function') {
						options.error(error);
					}
					
				}
			});
			
		};
		
		init();

	};
})(jQuery);