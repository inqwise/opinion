;
(function ($) {
    $.fn.favorit = function (o) {
        var a = null;
        var b = $(this),
            options = $.extend({
                favorit: false,
                change: undefined
            }, o);
        var c = function (f) {
			if (a.hasClass('selected')) {
				a.removeClass('selected')
			} else {
				a.addClass('selected')
			}
			if (f) {
				if (options.change != undefined 
						&& typeof options.change == 'function') {
					options.change({
						favorit: a.hasClass('selected')
					})
				}
			}
		};
        var d = function () {
			a = $("<a class=\"favorit " + (options.favorit ? "selected" : "") + "\"/>");
			a.click(function () {
				c(true)
			}).appendTo(b)
		};
        d();
        return {
            set: function (p) {
                c()
            }
        }
    }
})(jQuery);