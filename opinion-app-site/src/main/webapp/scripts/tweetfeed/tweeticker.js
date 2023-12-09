(function ($) {
    $.fn.tweeticker = function (options) {
        var defaults = {
            speed: 700,
            pause: 4000,
            showItems: 3
        };
        var options = $.extend(defaults, options);
        moveUp = function (obj2, options) {
            if (options.isPaused) return;
            var obj = obj2.children('ul');
            var clone = obj.children('li:first').clone(true);
            height = obj.children('li:first').height();
            
            obj.animate({
                top: '-=' + height + 'px'
            }, options.speed, "easeOutExpo", function () {
                $(this).children('li:first').remove();
                $(this).css('top', '0px');
            });
            
            obj.children('li:first').fadeOut(options.speed);
            obj.children('li:eq(' + options.showItems + ')').hide().fadeIn(options.speed).show();
            clone.appendTo(obj);
        };
        return this.each(function () {
            var obj = $(this);
            obj.css({
                overflow: 'hidden',
                position: 'relative'
            }).children('ul').css({
                position: 'absolute'
            });
            var interval = setInterval(function () {
                moveUp(obj, options);
            }, options.pause);
            obj.bind("mouseenter", function () {
                options.isPaused = true;
            }).bind("mouseleave", function () {
                options.isPaused = false;
            });
        });
    };
})(jQuery);