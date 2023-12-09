;
(function (l) {
    sidebar = function (k) {
        var b = $(window);
        var c = $('.sidebar');
        var d = $('.wrapper-footer');
        if (c.position() != null) {
            var e = c.position().top;
            var f = c.position().left;
            var g = c.height(); // need to fix height
            var h = d.position().top;
            var i = 0;
            c.addClass('fixed');
            var j = function () {
                h = d.position().top;
                scrollTop = b.scrollTop(), i = Math.max(0, e - scrollTop), i = Math.min(i, (h - scrollTop) - g);
                c.css({
                    'top': i
                    /*'left': f*/
                    /*'height' : c.prop('scrollHeight')*/
                });
                
                //console.log(c.prop('scrollHeight'));
            };
            b.scroll(function (a) {
                scrollTop = b.scrollTop(), i = Math.max(0, e - scrollTop), i = Math.min(i, (h - scrollTop) - g);
                c.css({
                    'top': i
                    /*'left': f*/
                })
            });
            b.resize(function () {
                j()
            });
            // fix
            $('.sidebar-relative').resize(function () {
                j()
            });
            j()
        }
    }
})(jQuery);

/*
;
(function(jQuery) {
	sidebar = function(k) {

		$window = $(window),
		$sidebar = $(".sidebar"),
		$footer = $(".wrapper-footer");
		
		if($sidebar.position() != null) {
			
			sidebarTop = $sidebar.position().top,
			sidebarLeft = $sidebar.position().left,
			sidebarHeight = $sidebar.height(),
			footerTop = $footer.position().top,
			$sidebar.addClass('fixed');
			
			var refresh = function() {
				footerTop = $footer.position().top;
				scrollTop = $window.scrollTop(),
			    topPosition = Math.max(0, sidebarTop - scrollTop),
			    topPosition = Math.min(topPosition, (footerTop - scrollTop) - sidebarHeight);
			    $sidebar.css({'top' : topPosition, 'left' : sidebarLeft });
			};
	
			$window.scroll(function(event) {
			    scrollTop = $window.scrollTop(),
			    topPosition = Math.max(0, sidebarTop - scrollTop),
			    topPosition = Math.min(topPosition, (footerTop - scrollTop) - sidebarHeight);
			    $sidebar.css({'top' : topPosition, 'left' : sidebarLeft});
			});
			
			
			// TODO: TEST
			$(window).resize(function(){ refresh(); });
			$('.sidebar-relative').resize(function() { 
				refresh();
			});
		
			refresh();
		
		}
	}
})(jQuery);
*/