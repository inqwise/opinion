;
(function($) {
	collectorView = function(givenOptions) {
		
		var options = $.extend({
			collector : null,
			servletUrl : null,
			absoluteUrl : null
		}, givenOptions);
		
		var init = function() {
			
			if(options.collector.sourceTypeId == 1) {
				
				$('#placeholder_collector_details_invite_your_own_respondents').show();
				$('#input_direct_link')
				.val(options.collector.collectorUrl)
				.focus(function(){
				    // select input field contents
				    $(this).select();
				})
				.mouseup(function(e){
				    e.preventDefault();
				});
				
			}
			
			if(options.collector.sourceTypeId == 2) {
				$('#placeholder_collector_details_purchase_respondents').show();
				
				if(options.collector.statusId == 7) {
					// show cint 
				} else {
					
					$('#placeholder_collector_details_purchase_respondents_statistic').show();
					
					/*
					getOrderDetails({
						externalId : options.collector.externalId,
						success : function(data) {
							
							$('#label_panel_id').text(data.orderNumber);
							$('#label_panel_completed').text((data.totalNumberOfCompletes != null ? data.totalNumberOfCompletes : 0));
							$('#label_panel_partial').text((data.actualNumberOfCompletes != null ? data.actualNumberOfCompletes : 0));
							
						},
						error : function() {
							//
						}
					});
					*/
					
					$('#label_panel_completed').text(options.collector.countOfCompleted);
					$('#label_panel_partial').text(options.collector.partial);
					
				}
			}
			
			// statistics
			/*
			 * var percentCompleted = Math.round(charts.totals.completed*100/(charts.totals.completed + charts.totals.partial));
	var percentPartial = Math.round(charts.totals.partial*100/(charts.totals.completed + charts.totals.partial));

	$('#label_total').html((charts.totals.completed + charts.totals.partial));
	$('#label_total_completed').html(charts.totals.completed + " <span style=\"font-weight: normal\">(" + (!isNaN(percentCompleted) ? percentCompleted : 0) + "%)</span>");
	$('#label_total_partial').html(charts.totals.partial + " <span style=\"font-weight: normal\">(" + (!isNaN(percentPartial) ? percentPartial : 0) + "%)</span>");
			 */
			
			


		};
		
		init();
		
	};
})(jQuery);