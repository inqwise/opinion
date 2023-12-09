;
(function($) {
	collectorView = function(givenOptions) {
		
		var options = $.extend({
			opinionId : null,
			servletUrl : null,
			absoluteUrl : null,
			sourceUrl : null,
			collectorUrl : null,
			data : {},
			qrCodeUrl : null,
			cint : null
		}, givenOptions);
		
		var sendToEmail = function(){
			
			var obj = {
				collectors : {
					sendCollectorLinkToMyEmail : {
						accountId : accountId,
						collectorId : options.data.collectorId
					}
				}
			};
			
			$.getJSON(options.servletUrl, { rq : JSON.stringify(obj), timestamp : $.getTimestamp() }, function(data) {
				if(data.collectors.sendCollectorLinkToMyEmail.error != undefined) {
					
					errorHandler({
						error : data.collectors.sendCollectorLinkToMyEmail.error	
					});
					
				} else {

					var modal = new lightFace({
						title : "Send to Email.",
						message : "The link to your survey was sent successfully. Please check your email.",
						actions : [
						           { label : "OK", fire : function() { modal.close() }, color: "blue" }
						],
						overlayAll : true
					});
					
				}
			});
		};
		
		var getQRCode = function(size, collectorUrl, tags, name) {
			
			$('#image_qr_code')
				.empty()
				.addClass('loading');
			
			var img = new Image();
			
			// wrap our new image in jQuery, then:
			$(img).load(function () {
		      // set the image hidden by default    
		      $(this).hide();
		    
		      // with the holding div #loader, apply:
		      $('#image_qr_code') 
		        .removeClass('loading')
		        .append(this);
		    
		      	// fade our image in to create a nice effect
		      	$(this).fadeIn();
		    })
		    .error(function () {
		      // notify the user that the image could not be loaded
		    })
		    .attr('src', "https://chart.googleapis.com/chart?chs=" + size + "&cht=qr&chl=" + collectorUrl + "?pop=30" + (tags != "" ? "&" + tags : ""));
			
			// update download button link
			$('#link_download_qr_code').attr('href', options.qrCodeUrl + "?name=" + name + "&size=" + size + "&tags=" + (tags != "" ? tags : "") + "&chart_url=" + collectorUrl + "?pop=30");
			
		};
		
		var init = function() {
			
			
			if(options.data.sourceTypeId == 1) {
				
				
				$('#placeholder_collector_details_invite_your_own_respondents').show();
				$('#placeholder_short_url').addClass('sidebar');
				
				// new sidebar({});
				
				
				// direct link
				$('#input_direct_link')
				.val(options.data.collectorUrl)
				.focus(function(){
				    // select input field contents
				    $(this).select();
				})
				.mouseup(function(e){
				    e.preventDefault();
				});
				
				
				
				var embedCode = "<script type=\"text/javascript\" src=\"" + options.collectorUrl +"/scripts/widget/1.1.2/survey.js\">\n" +
				"{\n" +
				"    \"guid\" : \"" + options.data.guid + "\",\n" +
				"    \"collectorUrl\" : \"" + options.sourceUrl + "\",\n" +
				"    \"url\" : \"" + options.data.collectorUrl + "\"\n" +
				"}\n" +
				"</script>\n" +
				"<noscript>\n" +
				"    <p><a href=\"" + options.data.collectorUrl + "\" title=\"" + options.data.opinionName + "\">Online Survey @ Inqwise.com</a></p>\n" +
				"    <p><a href=\"http://www.inqwise.com\" title=\"Online Surveys\" target=\"_blank\">Online Survey Software</a> by Inqwise</p>\n" +
				"</noscript>";
				
				$('#input_embed')
				.val(embedCode)
				.focus(function(){
				    $(this).select();
				})
				.mouseup(function(e){
				    e.preventDefault();
				});
				
				
				
				
				// html code / inline
				$('#input_html_code_inline')
				.val("<iframe frameborder=\"0\" width=\"100%\" height=\"600\" scrolling=\"auto\" allowtransparency=\"true\" src=\"" + options.data.collectorUrl + "\"></iframe>")
				.focus(function(){
				    $(this).select();
				})
				.mouseup(function(e){
				    e.preventDefault();
				});
				
				
				
				// social
				
				// facebook
				$('#link_facebook_share_link').attr("href", "https://www.facebook.com/sharer/sharer.php?s=100&p[url]=" + options.data.collectorUrl + "&p[title]=" + options.data.opinionName + "&p[summary]=&p[images][0]=" + (options.data.opinionLogoUrl != undefined ? options.data.opinionLogoUrl : "http://c7.inqwise.com/images/100x100.png"));
				
				// twitter
				$('#link_twitter_share_link').attr("href", "https://twitter.com/intent/tweet?original_referer=&source=tweetbutton&text=" + options.data.opinionName + "&url=" + options.data.collectorUrl + "&via=inqwise");
				
				// digg this
				$('#link_digg_this').attr("href", "http://digg.com/submit?url=" + options.data.collectorUrl + "&title=" + options.data.opinionName);
				
				// reddit
				$('#link_submit_to_reddit').attr("href", "http://reddit.com/submit?url=" + options.data.collectorUrl + "&title=" + options.data.opinionName);
				
				
				
				var htmlLink = "<a href=\"" + options.data.collectorUrl + "\">Fill out my survey!</a>";
				
				$('#input_html_link')
				.val(htmlLink)
				.focus(function(){
				    $(this).select();
				})
				.mouseup(function(e){
				    e.preventDefault();
				});
				
				
				// send to email
				$('#button_send_to_email').click(function() {
					sendToEmail();
				});
				
				// qr code
				$('#select_qr_code_size').change(function() {
					var a = $(this).find('option:selected');
					getQRCode(a.val(), options.data.collectorUrl, "", options.data.opinionName);
				});
				
				var defaultSize = $('#select_qr_code_size').val();
				getQRCode(defaultSize, options.data.collectorUrl, "", options.data.opinionName);
				
				
				
				// guid
				$('#input_wordpress_shortcode')
				.val("[inqwise guid=\""+ options.data.guid +"\"]") // ssl=\"true\"
				.focus(function(){
				    $(this).select();
				})
				.mouseup(function(e){
				    e.preventDefault();
				});
				
				
				var popupLink = "<a href=\"" + options.data.collectorUrl + "\" onclick=\"window.open(this.href, null, 'height=542, width=680, toolbar=0, location=0, status=1, scrollbars=1, resizable=1'); return false\">Please fill out my survey.</a>";
				
				$('#input_popup_link')
				.val(popupLink)
				.focus(function(){
				    $(this).select();
				})
				.mouseup(function(e){
				    e.preventDefault();
				});
				
				$("#link_popup_preview").attr("href", options.data.collectorUrl);
				
			}
			
			// CINT
			if(options.data.sourceTypeId == 2) {
				
				$('#placeholder_collector_details_purchase_respondents').show();
				
				
				
				
				if(options.data.statusId == 7) {
					
					loader.show();
					
					$('#holder_cint').cintSampler({
						apiKey : options.cint.apiKey,
						baseUrl : options.cint.baseUrl,
						proxyPath : options.cint.proxyPath,
						language : options.cint.language,
						surveyTitle : {
							value : options.data.opinionName,
							editable : false
						},
						surveyUrl : {
							value : options.data.collectorUrl, 
							editable : false
						},
						numberOfQuestions : {
							value : options.data.countOfControls,
							editable : false
						},
						collectorId : options.data.collectorId,
						sourceId : options.data.sourceId, // ?
						orderPurchase : {
							success : function(data) {
								
								// ask user
								if(data.transactionId != undefined) {
									// do refresh
									location.href = options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors/" + options.data.collectorId;
								
								}
								
							},
							error: function(data) {
								
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
											        		location.href = options.absoluteUrl + "/make-payment?charges=" + data.chargeId + "&return_url=" + options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors/" + options.data.collectorId;
											        		
											        		modal.close();
											        	}, 
											        	color : "blue" 
											        },
											        { 
											        	label : "Cancel", 
											        	fire : function() {
											        		// do refresh
											        		location.href = options.absoluteUrl + "/surveys/" + options.opinionId + "/collectors/" + options.data.collectorId;
											        		
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
						}
					});
					
					
					// CINT Hacking
					$('#holder_cint').bind("rebind", function() {
						
						$('#cds-widget_footer').addClass('sidebar');
						
						
						$('#cds-tabs-1').before($("#intro_cint"));
						
						
						var panelPrice = $('#cds-widget_footer').find('.cds-price');
						panelPrice.before($('#header_cint_price'));
						panelPrice.after($('#purchase'));
						
						// sidebar for CINT price
						//new sidebar({});
						loader.hide();
									
					});
		
					$('#button_purchase').click(function() {
						
						// show message are you sure?
						$("#buy").trigger('click');
						
						
					});	
					
					
					
				} else {
					
					
					$('#placeholder_collector_details_purchase_respondents_statistic').show();
					
					
					
					
					/*
					if(options.data.statusId == 3) {
						
						var f = $("<a href=\"javascript:;\" id=\"link_collector_pending_purchase_2\" title=\"" + getCollectorStatus(data.collectors.getCollectorDetails.statusId) + " $" + data.collectors.getCollectorDetails.invoice.amountDue + "\" status_id=\"" + options.statusId + "\" >" + getCollectorStatus(data.collectors.getCollectorDetails.statusId) + " $" + data.collectors.getCollectorDetails.invoice.amountDue + "</a>").appendTo('#label_panel_status');
						f.click(function() {
							//payInvoice({
							//	invoiceId : 
							//});
						});
					
					} else {
						
						// all other statuses
						$('#label_panel_status').text(getCollectorStatus(options.data.statusId));
						
					}
					*/
					
					
					/*
					// get order details for cint panel
					getOrderDetails({
						externalId : options.data.externalId,
						success : function(data) {
							
							// cint panel order number
							$('#label_panel_id').text(data.orderNumber);
							$('#label_panel_completed').text((data.totalNumberOfCompletes != null ? data.totalNumberOfCompletes : 0));
							$('#label_panel_partial').text((data.actualNumberOfCompletes != null ? data.actualNumberOfCompletes : 0));
							
						},
						error : function() {
							//
						}
					});
					*/
					
					
					$('#label_panel_completed').text(options.data.countOfCompleted);
					$('#label_panel_partial').text(options.data.partial);
					
					
					
					
				
				
				}
				
			}
			
			
			
		};
		
		// cint get order details
		var getOrderDetails = function(params) {

			var obj = {
				cint : {
					getOrderDetails : {
						externalId : params.externalId
					}
				}
			};

			$.ajax({
		        url: options.servletUrl,
		        data: { 
		        	rq : JSON.stringify(obj),
		        	timestamp : $.getTimestamp()  
		        },
		        dataType: "json",
		        success: function (data, status) {
					if(data.cint.getOrderDetails != undefined) {
						if(data.cint.getOrderDetails.error != undefined) {
							
							errorHandler({
								error : data.cint.getOrderDetails.error	
							});
							
							if(params.error != undefined 
									&& typeof params.error == 'function') {
								params.error(data.cint.getOrderDetails);
							}
						} else {
							if(params.success != undefined 
									&& typeof params.success == 'function') {
								params.success(data.cint.getOrderDetails);
							}
						}
					}
		        },
		        error: function (XHR, textStatus, errorThrow) {
		            // error
		        }
		    });
		};
		
		init();
		
	};
})(jQuery);