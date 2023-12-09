var pWin;
( function($) {
	surveyDetails = function(givenOptions) {
		
		var options = $.extend( {
			opinionId : null,
			absoluteUrl : null,
			applicationUrl : null,
			collectorUrl : null,
			callback : null
		}, givenOptions);
		
		var init = function() {
			
			getDetails({
				accountId : accountId,
				opinionId : options.opinionId,
				success : function(data) {
					
					$('#label_survey_name').text(data.name);
					
					$('#breadcrumb_survey_name')
						.show()
						.text(data.name)
						.attr({ 'title' : data.name });
					
					
					$('#label_create_date').text(data.createDate);
					$('#label_modify_date').text(data.modifyDate);
					$('#label_last_modified_by_user').text(data.lastModifiedByUser);
					
					// rename
					$('#link_rename_survey_name').click(function() {
						
						
						/*
						var I = $("<div>" +
								"<div class=\"params\">" +
									"<div class=\"param-name\">* Survey Name:</div>" +
										"<div class=\"param-value\">" +
											"<div><input type=\"text\" id=\"text_rename_survey_name\" name=\"rename_survey_name\" maxlength=\"254\" autocomplete=\"off\" placeholder=\"Name the survey\" /></div>" +
											"<div><label id=\"status_rename_survey_name\"></label></div>" +
										"</div>" +
									"</div>" +
								"</div>");
								
						*/
						
						var I = $("<div>" +
							"<div><input type=\"text\" id=\"text_rename_survey_name\" name=\"rename_survey_name\" maxlength=\"254\" autocomplete=\"off\" placeholder=\"Name the survey\" style=\"width: 225px\" /></div>" +
							"<div><label id=\"status_rename_survey_name\"></label></div>" +
						"</div>");
							
							var prevData = null;
							var surveyName = null;
							var dialog = $('#label_survey_name').lightDialog({
								message : I,
								actions : [
									{
										label : "Save",
										fire : function() {
										
											rename({
												accountId : accountId,
												name : $.trim(surveyName.val()),
												title : $.trim(surveyName.val()),
												opinionId : options.opinionId,
												success : function() {
													
													$('#breadcrumb_survey_name')
														.text($.trim(surveyName.val()))
														.attr({ 'title' : $.trim(surveyName.val()) });
													
												}
											});
											
											$('#link_rename_survey_name').show();
											
											// cancel
											dialog.hide()
											
										}
									},
									{
										label : "Cancel",
										fire : function() {
											
											$("#label_survey_name").text(prevData);
											$('#link_rename_survey_name').show();
											// cancel
											dialog.hide()
										},
										color : "white"
									}
								],
								complete : function() {
									
									prevData = $("#label_survey_name").text();
									surveyName = I.find("#text_rename_survey_name");
									surveyName
										.val($("#label_survey_name").text())
										.select()
										.keyup(function(event) {
											$("#label_survey_name").text($.trim(surveyName.val()) != "" ? $.trim(surveyName.val()) : "Name the survey");
										}).keydown(function(event) {
											
											// enter
											if (event.which == 13) {
												
												rename({
													accountId : accountId,
													name : $.trim(surveyName.val()) != "" ? $.trim(surveyName.val()) : "Name the survey",
													title : $.trim(surveyName.val()) != "" ? $.trim(surveyName.val()) : "Name the survey",
													opinionId : options.opinionId,
													success : function() {
														
														$('#breadcrumb_survey_name')
															.text($.trim(surveyName.val()) != "" ? $.trim(surveyName.val()) : "Name the survey")
															.attr({ 'title' : $.trim(surveyName.val()) != "" ? $.trim(surveyName.val()) : "Name the survey" });
														
														
													}
												});
												
												$('#link_rename_survey_name').show();
												
												// cancel
												dialog.hide()
												
												//event.preventDefault();
											}
											
											// esc
											if(event.which == 27){
												
												$("#label_survey_name").text(prevData);
												$('#link_rename_survey_name').show();
												// cancel
												dialog.hide()
												
											}
											
											// extra
											if(event.which == 8) {
												if($.trim(surveyName.val()) != "") {
													$("#label_survey_name").text($.trim(surveyName.val()));
												}
											}
										});
											
									
								}
							});
							
							$('#link_rename_survey_name').hide();
						
					});
					
					// copy
					$('#link_survey_copy').click(function() {
						
						var v = null;
						var M = $("<div>" +
							"<div style=\"padding: 0 0 12px 0\">Enter a name you'll use to reference this survey.</div>" +
							"<div class=\"row\">" +
								"<div class=\"cell\">" +
									"<div><input type=\"text\" id=\"input_copy_survey_name\" name=\"input_copy_survey_name\" maxlength=\"100\" autocomplete=\"off\" style=\"width: 323px;\" /></div>" +
									"<div><label id=\"status_input_copy_survey_name\"></label></div>" +
								"</div>" +
							"</div>" +
						"</div>");
						var I = M.find('#input_copy_survey_name');
						var B = M.find('#status_input_copy_survey_name');
						
						var modal = new lightFace({
							title : "Copying survey",
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
											accountId : accountId,
											name : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
											title : $.removeHTMLTags(I.val()).replace(/\r/g, ""),
											opinionType : 1,
											folderId : null,
											opinionId : options.opinionId,
											success : function(data) {
												if(data.opinionId != undefined) {
													
													modal.close();
													// after do redirect
													location.href = options.absoluteUrl + "/surveys/" + data.opinionId + "/edit";
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
						
					});
					
					// delete
					$('#link_survey_delete').click(function() {
						
						var modal = new lightFace({
							title : "Deleting survey",
							message : "Are you sure you want to delete this survey?",
							actions : [
					           { 
					        	   label : "Delete", 
					        	   fire : function() { 
									   
					        		   deleteOpinions({
					        			   accountId : accountId,
					        			   list : [options.opinionId],
					        			   success : function(data) {
					        				   
					        				   modal.close();
					        				   location.href = options.absoluteUrl + "/surveys";
					        				   
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
								title : "Export survey",
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
												accountId : accountId,
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
						$('#link_survey_publish').attr({ "href" : options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors/" + data.collectorId });
					} else {
						$('#link_survey_publish').attr({ "href" : options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors" });
					}
					
					// preview
					$('#link_preview')
						.click(function(){
							
							var name = "pWin";
							this.target = name;
							
							if (pWin) 
								pWin.close();
							
							pWin = window.open('', name, 'resizable=1,scrollbars=1,status=1,menubar=1');
							
						})
						//.attr({ "href" : options.collectorUrl + "/d/1/" + data.guid + "/0"});
						.attr({ "href" : data.previewUrl });
					
					
					if(options.callback != undefined 
							&& typeof options.callback == 'function')
						options.callback(data);
					
					
				},
				error: function(error) {
					//
				}
			});

			
		};

		init();
	};
})(jQuery);