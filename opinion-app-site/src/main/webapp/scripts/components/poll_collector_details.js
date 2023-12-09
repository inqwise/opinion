;
(function($) {
	collectorDetails = function(givenOptions) {
		
		var options = $.extend( {
			opinionId : null,
			collectorId : null,
			absoluteUrl : null,
			applicationUrl : null,
			callback : null
		}, givenOptions);
		
		//var opinionName = "";
		var collectorUrl = "";
		
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
				        	   		collectorId: collectorId, 
				        	   		statusId : 2, 
				        	   		closeMessage : $.removeHTMLTags(I.val()).replace(/\r/g, ""), 
				        	   		isGenerateNewGuid : false, 
				        	   		success : function() {
										
				        	   			$('#link_collector_close')
										.text(getCollectorStatus(2, 1))
										.attr({ 'title' : "Open collector", 'status_id' : 2 });
	
										modal.close();
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
						I.text(((closeMessage == undefined) ? messages.defaultCloseMessage : closeMessage));
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
				        	   		collectorId : collectorId,
				        	   		statusId : 1, 
				        	   		closeMessage : null, 
				        	   		isGenerateNewGuid : false, 
				        	   		success : function() {
										
				        	   			$('#link_collector_close')
										.text(getCollectorStatus(1, 1))
										.attr({ 'title' : "Close collector", 'status_id' : 1 });
	
										modal2.close();
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
				collectorId : options.collectorId,
				success : function(data) {
					
					
					
					collectorUrl = data.collectUrl;
					//opinionName = data.opinionName;
					
					$('#label_collector_name').text(data.name);
					
					
					/*
					$('#breadcrumb_survey_name')
						.show()
						.text(opinionName)
						.attr({ 'title' : opinionName });
					
					$('#label_survey_name')
						.text(opinionName)
						.attr({ 'title' : opinionName });
					*/
					
					
					$('#breadcrumb_collector_name')
						.show()
						.text(data.name)
						.attr({ 'title' : data.name });
					
					
					
					// rename
					$('#link_rename_collector_name').click(function() {
						
						/*
						var I = $("<div>" +
								"<div class=\"params\">" +
									"<div class=\"param-name\">* Collector Name:</div>" +
										"<div class=\"param-value\">" +
											"<div><input type=\"text\" id=\"text_rename_collector_name\" name=\"rename_collector_name\" maxlength=\"254\" autocomplete=\"off\" placeholder=\"Name the collector\" /></div>" +
											"<div><label id=\"status_rename_collector_name\"></label></div>" +
										"</div>" +
									"</div>" +
								"</div>");
						*/
						
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
					
					
					// delete
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
					        			   list : [options.collectorId],
					        			   success : function(data) {
					        				   
					        				   // redirect
					        				   location.href = options.absoluteUrl + "/polls/" + options.opinionId + "/collectors";
					        				   modal.close();
					        				   
					        			   },
					        			   error: function(error) {
					        				   //
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
					
					
					
					// status - open/close
					if(data.statusId == 1) {
						var c = $("<li><span class=\"light\">Status:</span>&nbsp;<a href=\"javascript:;\" id=\"link_collector_close\" title=\"" + messages.closeCollector + "\" status_id=\"" + data.statusId + "\" >" + getCollectorStatus(data.statusId, 1) + "</a></li>").appendTo('#list_collector_actions');
						c.find('#link_collector_close').click(function() {
							closeCollector(data.collectorId, parseInt($(this).attr("status_id")), data.closeMessage);
						});
					} else if (data.statusId == 2) {
						var c = $("<li><span class=\"light\">Status:</span>&nbsp;<a href=\"javascript:;\" id=\"link_collector_close\" title=\"" + messages.openCollector + "\" status_id=\"" + data.statusId + "\" >" + getCollectorStatus(data.statusId, 1) + "</a></li>").appendTo('#list_collector_actions');
						c.find('#link_collector_close').click(function() {
							closeCollector(data.collectorId, parseInt($(this).attr("status_id")), data.closeMessage);
						});
					}
					
					
					
					
					
					
					
					
					// return data in callback
					if(options.callback != null 
						&& typeof (options.callback) == 'function') {
						options.callback(data);
					}
					
					
				},
				error: function(error) {
					alert("ERR");
				}
			});
			
		};
		
		
		init();
		
	};
})(jQuery);