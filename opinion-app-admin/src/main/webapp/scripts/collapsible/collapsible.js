

var expanded = false;

$(function() {
	
	if($.cookie) {
		if($.cookie('flyover') != null) {
			expanded = JSON.parse($.cookie('flyover')).expanded;
		}
	}
	
	scrollTop = $(window).scrollTop(), z = Math.max(0, 80 - scrollTop);
	
	$('.cA1').css({
        'top': z
	});
	
	$('.cA3, .cA5').css({
        'height' : $(window).height() - z
	});
	
	var e = 80; //$('.cA1').position().top;
	
	$(window).resize(function() {
		scrollTop = $(window).scrollTop(), y = Math.max(0, e - scrollTop);
		
		$('.cA1').css({
	        'top': y
		});
		
		$('.cA3, .cA5').css({
	        'height' : $(window).height() - y
		});
		
	}).scroll(function (a) {
		scrollTop = $(window).scrollTop(), i = Math.max(0, e - scrollTop); //, i = Math.min(i, ($(window).height() - scrollTop) - 80);
		
		$('.cA1').css({
	        'top': i
		});
		
		$('.cA3, .cA5').css({
	        'height' : $(window).height() - i
		});
		
	});
	
	if(expanded) {
		$('.cO').addClass('cP');
		$('.cW').addClass('cM');
		
		$('.flyover-container').css({ 'left' : 0 });
		
		$('.cA7').hide();
		$('.cA4').show();
	}
	
	
	// expand
	$('.cA3').click(function() {
			
		$('.cO').addClass('cP');
		$('.cW').addClass('cM');
		
		//$(this).hide();
		$('.flyover-container').css({ 'left' : 0 });
		
		$('.cA7').hide();
		$('.cA4').show();
		
		expanded = true;
		
		if($.cookie) {
			$.cookie('flyover', JSON.stringify({ expanded : expanded }), { path: '/' });
		}
		
	});
	
	// hover
	var leaved = false;
	$('.cA3')
	.hover(function() {
		$('.cA3').addClass('cO2');
	}, function() {
		leaved = true;
		$('.cA3').removeClass('cO2');
	});
	
	$('.cA3')
		.on('mouseenter', function() {
		if(!expanded) {
			leaved = false;
			setTimeout(function() {
				if(!leaved) {
					$('.flyover-container').animate({ 'left' : 0 }, 100);
				}
			}, 650);
		}
	});
	
	$('.cA5')
		.on('mouseleave', function() {
		if(!expanded) {
			setTimeout(function() {
				$('.flyover-container').animate({ 'left' : -230 }, 100);
			}, 150);
		}
	});
	
	// collapse button
	$('.cA4').click(function(e) {
		
		$('.flyover-container').css({ 'left' : -230 });
		
		$('.cO').removeClass('cP');
		$('.cW').removeClass('cM');
		expanded = false;
		
		if($.cookie) {
			$.cookie('flyover', JSON.stringify({ expanded : expanded }), { path: '/' });
		}
		
		/*
		$('.cA3')
		.removeClass('active')
		.show();
		*/
		

		$('.cA4').hide();
		$('.cA7').show();
		
		
		e.preventDefault();
	});
	
	// lock
	$('.cA7').click(function(e) {
		
		$('.cO').addClass('cP');
		$('.cW').addClass('cM');
		expanded = true;
		
		$('.cA7').hide();
		$('.cA4').show();
		
		
		if($.cookie) {
			$.cookie('flyover', JSON.stringify({ expanded : expanded }), { path: '/' });
		}
		
		e.preventDefault();
	});
	
	
});