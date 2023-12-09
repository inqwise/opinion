var pWin;
(function($) {
	pollDetails = function(givenOptions) {
		
		var options = $.extend( {
			opinionId : null,
			absoluteUrl : null,
			applicationUrl : null,
			collectorUrl : null,
			callback : null
		}, givenOptions);
		
		var init = function() {
			
			getDetails({
				opinionId : options.opinionId,
				success : function(data) {
					
					$('#breadcrumb_survey_name')
						.show()
						.text(data.name); // + url
					
					$('#label_poll_name').text(data.name);
					
					
					// rename
					$('#link_rename_poll_name').click(function() {
						
						/*
						var I = $("<div>" +
								"<div class=\"params\">" +
									"<div class=\"param-name\">* Poll Name:</div>" +
										"<div class=\"param-value\">" +
											"<div><input type=\"text\" id=\"text_rename_poll_name\" name=\"rename_poll_name\" maxlength=\"254\" autocomplete=\"off\" placeholder=\"Name the poll\" /></div>" +
											"<div><label id=\"status_rename_poll_name\"></label></div>" +
										"</div>" +
									"</div>" +
								"</div>");
						*/
						
						var I = $("<div>" +
							"<div><input type=\"text\" id=\"text_rename_poll_name\" name=\"rename_poll_name\" maxlength=\"254\" autocomplete=\"off\" placeholder=\"Name the poll\" style=\"width: 225px;\" /></div>" +
							"<div><label id=\"status_rename_poll_name\"></label></div>" +
						"</div>");
							
							
							var prevData = null;
							var pollName = null;
							var dialog = $('#label_poll_name').lightDialog({
								message : I,
								actions : [
									{
										label : "Save",
										fire : function() {
										
											rename({
												name : $.trim(pollName.val()),
												/* title : $.trim(pollName.val()), */
												opinionId : options.opinionId,
												success : function() {
													
													$('#breadcrumb_survey_name')
														.text($.trim(pollName.val()))
														.attr({ 'title' : $.trim(pollName.val()) });
													
												}
											});
											
											$('#link_rename_poll_name').show();
											
											// cancel
											dialog.hide()
											
										}
									},
									{
										label : "Cancel",
										fire : function() {
											
											$("#label_poll_name").text(prevData);
											$('#link_rename_poll_name').show();
											// cancel
											dialog.hide()
										},
										color : "white"
									}
								],
								complete : function() {
									
									prevData = $("#label_poll_name").text();
									pollName = I.find("#text_rename_poll_name");
									pollName
										.val($("#label_poll_name").text())
										.select()
										.keyup(function(event) {
											$("#label_poll_name").text($.trim(pollName.val()) != "" ? $.trim(pollName.val()) : "Name the poll");
										}).keydown(function(event) {
											
											// enter
											if (event.which == 13) {
												
												rename({
													name : $.trim(pollName.val()) != "" ? $.trim(pollName.val()) : "Name the poll",
													/*title : $.trim(pollName.val()) != "" ? $.trim(pollName.val()) : "Name the poll",*/
													opinionId : options.opinionId,
													success : function() {
														
														$('#breadcrumb_survey_name')
															.text($.trim(pollName.val()) != "" ? $.trim(pollName.val()) : "Name the poll")
															.attr({ 'title' : $.trim(pollName.val()) != "" ? $.trim(pollName.val()) : "Name the poll" });
														
														
													}
												});
												
												$('#link_rename_poll_name').show();
												
												// cancel
												dialog.hide()
												
												//event.preventDefault();
											}
											
											// esc
											if(event.which == 27){
												
												$("#label_poll_name").text(prevData);
												$('#link_rename_poll_name').show();
												// cancel
												dialog.hide()
												
											}
											
											// extra
											if(event.which == 8) {
												if($.trim(pollName.val()) != "") {
													$("#label_poll_name").text($.trim(pollName.val()));
												}
											}
										});
											
									
								}
							});
							
							$('#link_rename_poll_name').hide();
						
					});
					
					
					
					
					
					
					// copy
					$("#link_poll_copy").click(function(e) {
						
						var v = null;
						var M = $("<div>" +
							"<div style=\"padding: 0 0 12px 0\">Enter a name you'll use to reference this poll.</div>" +
							"<div class=\"row\">" +
								"<div class=\"cell\">" +
									"<div><input type=\"text\" id=\"input_copy_poll_name\" name=\"input_copy_poll_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
									"<div><label id=\"status_input_copy_poll_name\"></label></div>" +
								"</div>" +
							"</div>" +
						"</div>");
						var I = M.find('#input_copy_poll_name');
						var B = M.find('#status_input_copy_poll_name');
						
						var modal = new lightFace({
							title : "Copying poll",
							message : M,
							actions : [
					           { 
					        	   label : "Copy", 
					        	   fire : function() {
					        	   
						        	   // check validation
						        	   v.validate();
					        	   
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
							overlayAll : true,
							complete : function() {
								
								// set default values
								I.val("Copy of " + data.name);
								
								// initialize validator on input
								v = new validator({
									elements : [
										{
											element : I,
											status : B,
											rules : [
												{ method : 'required', message : 'This field is required.' },
												{ method : 'rangelength', pattern : [3,100] }
											]
										}
									],
									submitElement : null,
									messages : null,
									accept : function () {
										
										copy({
											name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
											/*title : $.removeHTMLTags(I.val()).replace(/\r/g, ""),*/
											opinionType : 2, // poll
											folderId : null,
											opinionId : options.opinionId,
											success : function(data) {
												if(data.opinionId != undefined) {
													
													modal.close();
													// after do redirect
													location.href = options.absoluteUrl + "/polls/" + data.opinionId + "/edit";
												}
											},
											error: function(error) {
												//
											}
										});
										
									},
									error: function() {
										// error
									}
								});
							}
						});
						
						e.preventDefault();
						
					});
					
					// delete
					$("#link_poll_delete").click(function(e) {
						
						var modal = new lightFace({
							title : "Deleting poll",
							message : "Are you sure you want to delete this poll?",
							actions : [
					           { 
					        	   label : "Delete", 
					        	   fire : function() { 
									   
					        		   deleteOpinions({
					        			   list : [options.opinionId],
					        			   success : function(data) {
					        				   
					        				   modal.close();
					        				   location.href = options.absoluteUrl + "/polls";
					        				   
					        			   },
					        			   error: function() {
					        				   // error
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
						
						
						e.preventDefault();
						
					});
					
					// export
					if(auth.userInfo.permissions.importExportOpinion) {
						
						$("#link_export")
						.show()
						.click(function(e) {
							
							/*
							// TODO:
							exportTo({
								opinionId : options.opinionId,
								name: "filename_" + options.opinionId + "_",
							    exportType : "opinion"
							});
							*/
							
							var v = null;
							var M = $("<div>" +
								"<div style=\"padding: 0 0 12px 0\">Enter a filename.</div>" +
								"<div class=\"row\">" +
									"<div class=\"cell\">" +
										"<div><input type=\"text\" id=\"text_filename\" name=\"rename_filename\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
										"<div><label id=\"status_filename\"></label></div>" +
									"</div>" +
								"</div>" +
							"</div>");
							var I = M.find('#text_filename');
							var B = M.find('#status_filename');
							
							var modal = new lightFace({
								title : "Export poll",
								message : M,
								actions : [
								   { 
									   label : "Export", 
									   fire : function() {
									   		// check validation
											v.validate();
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
								overlayAll : true,
								complete : function() {
									
									// set default values
									I.val(data.name).select();
									
									// initialize validator on input
									v = new validator({
										elements : [
											{
												element : I,
												status : B,
												rules : [
													{ method : 'required', message : 'This field is required.' },
													{ method : 'rangelength', pattern : [3,100] }
												]
											}
										],
										submitElement : null,
										messages : null,
										accept : function () {
											
											exportTo({
												opinionId : options.opinionId,
												name : $.removeHTMLTags(I.val().replace(/[/\\:?<>|\"]+/g, "")).replace(/\r/g, ""),
												exportType : "opinion"
											});
											
											modal.close();
											
										},
										error: function() {
											// error
										}
									});
								}
							
							});
							
							
							e.preventDefault();
							
						});
					}
					
					// distribute
					if(data.collectorId != undefined) {
						$("#link_poll_embed").attr({ "href" : options.absoluteUrl + "/polls/" + options.opinionId + "/collectors/" + data.collectorId });
					} else {
						$("#link_poll_embed").attr({ "href" : options.absoluteUrl + "/polls/" + options.opinionId + "/collectors" });
					}
					
					// preview
					$("#link_poll_preview").click(function(){
						
						var name = "pWin";
						this.target = name;
						
						if (pWin) 
							pWin.close();
						
						pWin = window.open("", name, "resizable=1,scrollbars=1,status=1,menubar=1");
						
					})
					//.attr({ "href" : options.collectorUrl + "/d/1/" + data.guid + "/0"});
					.attr({ "href" : data.previewUrl });
					
					
					
					
					
					
					// callback
					if(options.callback != undefined 
							&& typeof options.callback == 'function')
						options.callback(data);
					
					
				},
				error: function() {
					// error
				}
			});
			
		};
		
		init();
		
	};
})(jQuery);