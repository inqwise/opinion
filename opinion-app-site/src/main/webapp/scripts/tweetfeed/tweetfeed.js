(function ($) {
    $.fn.tweetfeed = function (options) {
        var defaults = {
            limit: 5,
            username: 'envato'
        };
        var options = $.extend(defaults, options);

        function parse_date(date_str) {
            return Date.parse(date_str.replace(/^([a-z]{3})( [a-z]{3} \d\d?)(.*)( \d{4})$/i, '$1,$2$4$3'));
        }

        function relative_time(date) {
            var relative_to = (arguments.length > 1) ? arguments[1] : new Date();
            var delta = parseInt((relative_to.getTime() - date) / 1000, 10);
            var r = '';
            if (delta < 60) {
                r = delta + ' seconds ago';
            } else if (delta < 120) {
                r = 'a minute ago';
            } else if (delta < (45 * 60)) {
                r = (parseInt(delta / 60, 10)).toString() + ' minutes ago';
            } else if (delta < (2 * 60 * 60)) {
                r = 'an hour ago';
            } else if (delta < (24 * 60 * 60)) {
                r = '' + (parseInt(delta / 3600, 10)).toString() + ' hours ago';
            } else if (delta < (48 * 60 * 60)) {
                r = 'a day ago';
            } else {
                r = (parseInt(delta / 86400, 10)).toString() + ' days ago';
            }
            return 'about ' + r;
        }
        return this.each(function (options) {
            var c_tar = $(this);
            var $tweetFeed;
            $.getJSON("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=" + defaults.username + "&count=" + defaults.limit + "&callback=?", c_tar, function (data) {
                
            	//$('.tweetLoading').html('');
                
                $.each(data, function (i, item) {
                    if (i == 0) {
                        $tweetFeed = $("<ul class=\"ll\">").appendTo(c_tar);
                    }
                    $tweetFeed.append("<li><h6>" + item.text.replace(/#(.*?)(\s|$)/g, "<span class=\"hash\">#$1 </span>").replace(/(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig, "<a href=\"$&\" target=\"_blank\">$&</a> ").replace(/@(.*?)(\s|\(|\)|$)/g, "<a href=\"http://twitter.com/$1\" target=\"_blank\">@$1 </a>$2") + " - " + "<a href=\"" + "http://twitter.com/" + defaults.username + "/status/" + item.id_str + "\" target=\"_blank\">" + relative_time(parse_date(item.created_at)) + "</a></h6></li>");
                });
            });
        });
    }
})(jQuery);