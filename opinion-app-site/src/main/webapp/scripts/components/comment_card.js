


function commentCard() { return this.constructor(); }
commentCard.prototype = {
	applicationUrl: "http://localhost:8080/opinion-opinion",
	win:null,
	opinionId: null,
	defaultWidth: 536,
	defaultHeight: 240,
	
	show: function() {
		// if(this.win != null) { this.win.close(); this.win = null; }
		this.win = window.open(this.applicationUrl + '/comment_card.jsp?opinionUrl=' + this.opinionUrl + '&screenWidth=' + screen.width + '&screenHeight=' + screen.height + '&time=' + new Date().getTime() + '&referer=' + escape(document.location.href) + '&page=' + escape(document.referrer), 'name', 'height=' + this.defaultHeight + ', width=' + this.defaultWidth + ', top=' + (screen.height / 2 - this.defaultHeight / 2) + ', left=' + (screen.width / 2 - this.defaultWidth / 2) + ', toolbar=no, status=yes, resizable=yes');
		
		if (window.focus) { this.win.focus(); }
	},
	isClosed: function() {
		if(this.win != null) {
			if(this.win.closed) return true;
		}
		return false;
	},
	get: function(opinionUrl) {
		this.opinionUrl = opinionUrl;
		if(this.opinionUrl != null) {
			this.show();
		}
	},
	constructor: function() {
		
		
		
	}
};

cc = new commentCard();
