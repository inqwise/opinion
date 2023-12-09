;
(function($) {
	collectorDetails = function(givenOptions) {
		
		var options = $.extend( {
			opinionId : null,
			collectorId : null,
			servletUrl : null,
			absoluteUrl : null,
			applicationUrl : null,
			success : null
		}, givenOptions);
		
		var opinionName = "";
		
		// close collector
		var closeCollector = function(collectorId, statusId, closeMessage) {
			
			if(statusId == 1) {
				
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
			        		   
			        		   updateCollectorStatus({
			        			   	accountId : accountId,
			       					collectorId : collectorId,
			       					statusId : 2,
			       					closeMessage : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
			       					isGenerateNewGuid : false,
			       					success : function() {
			       						
			       						$('#link_collector_close')
										.text(getCollectorStatus(2, 1))
										.attr({ 'title' : contentDictionary.titles.openCollector, 'status_id' : 2 });
									
										modal.close();
			       						
			       					},
			       					error: function() {
			       						
			       					}
			        		   });
			        		   
			        	   }, 
			        	   color: "blue" 
			           },
			           { 
			        	   label : "Cancel", 
			        	   fire : function() { 
			        		   modal.close(); 
			        	   }, 
			        	   color: "white" 
			           }
					],
					overlayAll: true,
					complete : function() {
						I.text(((closeMessage == undefined) ? contentDictionary.messages.defaultCloseMessage : closeMessage));
					}
				});
			}

			if(statusId == 2) {
				
				var modal2 = new lightFace({
					title : "Are you sure you want to start collecting responses?",
					message : "This collector is currently closed.<br/> You can re-open this collector now.",
					actions : [
			           { 
			        	   label : "Open Collector", 
			        	   fire : function() {
			        	   	
			        		   updateCollectorStatus({
			        			   accountId : accountId,
			       					collectorId : collectorId,
			       					statusId : 1,
			       					closeMessage : null,
			       					isGenerateNewGuid : false,
			       					success : function() {
			       						
			       						$('#link_collector_close')
										.text(getCollectorStatus(1, 1))
										.attr({ 'title' : contentDictionary.titles.closeCollector, 'status_id' : 1 });
									
			       						modal2.close();
			       						
			       					},
			       					error: function() {
			       						
			       					}
			        		   });
			        	   	
							
			        	   }, 
			        	   color: "blue" 
			           },
			           { 
			        	   label : "Cancel", 
			        	   fire : function() { 
			        		   modal2.close(); 
			        	   }, 
			        	   color: "white" 
			           }
					],
					overlayAll : true
				});
				
			}
		};
		
		var init = function() {
			
			getCollectorDetails({
				accountId : accountId,
				collectorId : options.collectorId,
				success : function(data) {
					
					collectorUrl = data.collectUrl;
					opinionName = data.opinionName;
					
					$('#label_collector_name').text(data.name);
					
					$('#breadcrumb_survey_name')
						.show()
						.text(opinionName)
						.attr({ 'title' : opinionName });
					
					$('#label_survey_name')
						.text(opinionName)
						.attr({ 'title' : opinionName });
					
					$('#breadcrumb_collector_name')
						.show()
						.text(data.name)
						.attr({ 'title' : data.name });
					
					// rename
					$('#link_rename_collector_name').click(function() {
						
						var I = $("<div>" +
							"<div><input type=\"text\" id=\"text_rename_collector_name\" name=\"rename_collector_name\" maxlength=\"254\" autocomplete=\"off\" placeholder=\"Name the collector\" style=\"width: 225px;\" /></div>" +
							"<div><label id=\"status_rename_collector_name\"></label></div>" +
						"</div>");
						
							var prevData = null;
							var collectorName = null;
							var dialog = $('#label_collector_name').lightDialog({
								message : I,
								actions : [
									{
										label : "Save",
										fire : function() {
										
											changeCollectorName({
												accountId : accountId,
												name : $.trim(collectorName.val()),
												collectorId : options.collectorId,
												success : function() {
													
													$('#breadcrumb_collector_name')
														.text($.trim(collectorName.val()))
														.attr({ 'title' : $.trim(collectorName.val()) });
													
												}
											});
											
											$('#link_rename_collector_name').show();
											
											// cancel
											dialog.hide()
											
										}
									},
									{
										label : "Cancel",
										fire : function() {
											
											$("#label_collector_name").text(prevData);
											$('#link_rename_collector_name').show();
											// cancel
											dialog.hide()
										},
										color : "white"
									}
								],
								complete : function() {
									
									prevData = $("#label_collector_name").text();
									collectorName = I.find("#text_rename_collector_name");
									collectorName
									.val($("#label_collector_name").text())
									.select()
									.keyup(function(event) {
										$("#label_collector_name").text($.trim(collectorName.val()) != "" ? $.trim(collectorName.val()) : "Name the collector");
									}).keydown(function(event) {
										
										// enter
										if (event.which == 13) {
											
											changeCollectorName({
												accountId : accountId,
												name : $.trim(collectorName.val()) != "" ? $.trim(collectorName.val()) : "Name the collector",
												collectorId : options.collectorId,
												success : function() {
													
													$('#breadcrumb_collector_name')
														.text($.trim(collectorName.val()) != "" ? $.trim(collectorName.val()) : "Name the collector")
														.attr({ 'title' : $.trim(collectorName.val()) != "" ? $.trim(collectorName.val()) : "Name the collector" });
													
												}
											});
											
											$('#link_rename_collector_name').show();
											
											// cancel
											dialog.hide()
											
											//event.preventDefault();
										}
										
										// esc
										if(event.which == 27){
											$("#label_collector_name").text(prevData);
											$('#link_rename_collector_name').show();
											// cancel
											dialog.hide()
										}
										
										// extra
										if(event.which == 8) {
											if($.trim(collectorName.val()) != "") {
												$("#label_collector_name").text($.trim(collectorName.val()));
											}
										}
									});
									
								}
							});
							
							$('#link_rename_collector_name').hide();
						
					});
					
					
					
					
					
					// callback on success
					if(options.success != null 
						&& typeof (options.success) == 'function') {
						options.success(data);
					}
					
					
					
					
					
					
					// if(data.sourceTypeId == 2) {
					if(data.sourceTypeId == 2) {
						if(data.statusId == 1 
								|| data.statusId == 4) {
							
							// disableDelete();
							
						} else {
							
							// enableDelete();
							
							var d = $("<li class=\"first-item\"><a href=\"javascript:;\" title=\"Delete\" id=\"link_collector_delete\">Delete</a></li>").appendTo('#list_collector_actions');
							d.find('#link_collector_delete').click(function() {
								
								var modal = new lightFace({
									title : "Deleting collector",
									message : "Are you sure you want to delete this collector?",
									actions : [
							           { 
							        	   label : "Delete", 
							        	   fire : function() { 
							        		   
							        		   deleteCollectors({
							        			  accountId : accountId,
							        			  list : [options.collectorId],
							        			  success : function() {
							        				  
							        				  location.href = options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors";
							        				  //modal.close();
							        				  
							        			  },
							        			  error: function() {
							        				  
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
								
								
							});
							
						}
					} else {
						
						// enableDelete();
						
						var d = $("<li class=\"first-item\"><a href=\"javascript:;\" title=\"Delete\" id=\"link_collector_delete\">Delete</a></li>").appendTo('#list_collector_actions');
						d.find('#link_collector_delete').click(function() {
							
							var modal = new lightFace({
								title : "Deleting collector",
								message : "Are you sure you want to delete this collector?",
								actions : [
						           { 
						        	   label : "Delete", 
						        	   fire : function() {
						        		   
						        		  deleteCollectors({
						        			  accountId : accountId,
						        			  list : [options.collectorId],
						        			  success : function() {
						        				  
						        				  location.href = options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors";
						        				  
						        			  },
						        			  error: function() {
						        				  
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
							
							
						});
						
					}
					
					
					if(data.sourceTypeId == 1) {
						
						if(data.statusId == 1) {
							var c = $("<li><span class=\"light\">Status:</span>&nbsp;<a href=\"javascript:;\" id=\"link_collector_close\" title=\"" + contentDictionary.titles.closeCollector + "\" status_id=\"" + data.statusId + "\" >" + getCollectorStatus(data.statusId, data.sourceTypeId) + "</a></li>").appendTo('#list_collector_actions');
							c.find('#link_collector_close').click(function() {
								closeCollector(data.collectorId, parseInt($(this).attr('status_id')), data.closeMessage);
							});
						} else if (data.statusId == 2) {
							var c = $("<li><span class=\"light\">Status:</span>&nbsp;<a href=\"javascript:;\" id=\"link_collector_close\" title=\"" + contentDictionary.titles.openCollector + "\" status_id=\"" + data.statusId + "\" >" + getCollectorStatus(data.statusId, data.sourceTypeId) + "</a></li>").appendTo('#list_collector_actions');
							c.find('#link_collector_close').click(function() {
								closeCollector(data.collectorId, parseInt($(this).attr('status_id')), data.closeMessage);
							});
						}
						
					} else {
						
						// statusId == 3 -> Awaiting payment
						if (data.statusId == 3) {
							var c = $("<li><span class=\"light\">Status:</span>&nbsp;<a href=\"javascript:;\" id=\"link_collector_pending_purchase\" title=\"" + getCollectorStatus(data.statusId) + " $" + (data.charge.amountDue).formatCurrency(2, '.', ',') + "\" status_id=\"" + data.statusId + "\" >" + getCollectorStatus(data.statusId) + " $" + (data.charge.amountDue).formatCurrency(2, '.', ',') + "</a></li>").appendTo('#list_collector_actions');
							c.find('#link_collector_pending_purchase').click(function() {
								
								var chargeId = data.charge.chargeId;
								
								payCharge({
									accountId: accountId,
									chargeId : chargeId,
									success : function(data) {
										
										if(data.transactionId != undefined) {
											// do refresh
											location.href = options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors/" + options.collectorId;
											
										}
										
									},
									error : function(data) {

										if(data.error == "NoEnoughFunds") {
											
											if (data.amountToFund != undefined && data.chargeId != undefined) {
												
												if(data.balance == 0) {
													
													// redirect to 
													location.href = options.absoluteUrl + "/make-payment?charges=" + data.chargeId; // + "&return_url=<%=absoluteURL %>/upgrade";
													
												} else {
													
													var modal = new lightFace({
														title : "Your account balance is running low.",
														message : $.format("Your account balance is running low. You have only <b>{0}</b>.<br/> To complete purchase, please make a payment of <b>{1}</b>.", "$" + (data.balance).formatCurrency(2, '.', ','), "$" + (data.amountToFund).formatCurrency(2, '.', ',')),
														actions : [
													        { 
													        	label : "Make a payment", 
													        	fire : function() {
													        		// redirect to make a payment
													        		location.href = options.absoluteUrl + "/make-payment?charges=" + chargeId + "&return_url=" + options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors/" + options.collectorId;
													        		
													        		modal.close();
													        	}, 
													        	color : "blue" 
													        },
													        { 
													        	label : "Cancel", 
													        	fire : function() {
													        		
													        		modal.close(); 
													        	}, 
													        	color : "white" 
													        }
														],
														overlayAll : true
													});
												
												}
												
											}
											
										}
										
									}
								});
								
							});
							
							
						} else {
							
							
							// all other statuses
							$("<li class=\"first-item\"><span class=\"light\">Status:</span>&nbsp;" + getCollectorStatus(data.statusId) + "</li>").appendTo('#list_collector_actions');
							
						}
													
						
					}
					
					// type
					$("<li><span class=\"light\">Type:</span>&nbsp;" + getCollectorType(data.sourceTypeId) + "</li>").appendTo('#list_collector_actions');
					
					
					
					
					
				},
				error: function() {
					
					//console.log("error...");
					
				}
			});
			
		};
		
		init();
		
	};
})(jQuery);