;
(function($) {
	collectorView = function(givenOptions) {
		
		var options = $.extend({
			sourceUrl : null,
			collectorUrl : null,
			data : {}
		}, givenOptions);
		
		var init = function() {
			
			//var data = options.data;
			
			// direct link
			$('#input_direct_link')
			.val(options.data.collectorUrl)
			.focus(function(){
			    $(this).select();
			})
			.mouseup(function(e){
			    e.preventDefault();
			});
			
			
			/*
			"    \"messages\" : {\n"+
			"        \"vote\" : \"Vote\",\n" +
			"        \"viewResults\" : \"View Results\",\n" +
			"        \"back\" : \"Back to Poll\",\n" +
			"        \"totalVotes\" : \"Total Votes\",\n" +
			"        \"thanks\" : \"Thank you for voting!\",\n" +
			"        \"closed\" : \"Voting for this poll has been closed\"\n" +
			"    }\n" +
			 */
			
			
			var embedCode = "<script type=\"text/javascript\" src=\"" + options.collectorUrl +"/scripts/widget/1.0.4/poll.js\">\n" +
			"{\n" +
			"    \"guid\" : \"" + options.data.guid + "\",\n" +
			"    \"collectorUrl\" : \"" + options.sourceUrl + "\",\n" +
			"    \"url\" : \"" + options.data.collectorUrl + "\"\n" +
			"}\n" +
			"</script>\n" +
			"<noscript>\n" +
			"    <p><a href=\"" + options.data.collectorUrl + "\" title=\"" + options.data.opinionName + "\">Online Poll @ Inqwise.com</a></p>\n" +
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
			
			
			
			
			// social

			// facebook
			$('#link_facebook_share_link').attr("href", "https://www.facebook.com/sharer/sharer.php?s=100&p[url]=" + options.data.collectorUrl + "&p[title]=" + options.data.opinionName + "&p[summary]=&p[images][0]=http://c7.inqwise.com/images/100x100.png");
			
			// twitter
			$('#link_twitter_share_link').attr("href", "https://twitter.com/intent/tweet?original_referer=&source=tweetbutton&text=" + options.data.opinionName + "&url=" + options.data.collectorUrl + "&via=inqwise");
			
			// digg this
			$('#link_digg_this').attr("href", "http://digg.com/submit?url=" + options.data.collectorUrl + "&title=" + options.data.opinionName);
			
			// reddit
			$('#link_submit_to_reddit').attr("href", "http://reddit.com/submit?url=" + options.data.collectorUrl + "&title=" + options.data.opinionName);
			
			
			
			
			/*
			// guid
			$('#input_guid')
			.val(options.data.guid)
			.focus(function(){
			    $(this).select();
			})
			.mouseup(function(e){
			    e.preventDefault();
			});
			*/
				
			
		};
		
		init();
		
	};
})(jQuery);