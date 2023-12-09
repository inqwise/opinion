;
(function (l) {
    sidebar = function (k) {
        var b = $(window);
        var c = $('.sidebar');
        var d = $('.wrapper-footer');
        if (c.position() != null) {
            var e = c.position().top;
            var f = c.position().left;
            var g = c.height();
            var h = d.position().top;
            var i = 0;
            c.addClass('fixed');
            var j = function () {
                h = d.position().top;
                scrollTop = b.scrollTop(), i = Math.max(0, e - scrollTop), i = Math.min(i, (h - scrollTop) - g);
                c.css({
                    'top': i,
                    'left': f
                })
            };
            b.scroll(function (a) {
                scrollTop = b.scrollTop(), i = Math.max(0, e - scrollTop), i = Math.min(i, (h - scrollTop) - g);
                c.css({
                    'top': i,
                    'left': f
                })
            });
            b.resize(function () {
                j()
            });
            $('.sidebar-relative').resize(function () {
                j()
            });
            j()
        }
    }
})(jQuery);